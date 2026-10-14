package utils;

import api.DobleFactorAPI;
import api.ValidarCodeResponses;
import global.practia.jemeinsamstark.web.core.WebDriver;

import io.qameta.allure.Allure;

import io.restassured.RestAssured;

import io.restassured.config.HttpClientConfig;

import io.restassured.http.Header;

import io.restassured.http.Headers;

import io.restassured.path.json.JsonPath;

import io.restassured.response.Response;

import io.restassured.specification.RequestSpecification;

import org.junit.Assert;

import org.openqa.selenium.JavascriptExecutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static global.practia.jemeinsamstark.web.core.ConfigHelper.getAppDefaultWait;



public class RestAssuredApi{
    ValidarCodeResponses validarCodeResponses = new ValidarCodeResponses();
    public static String accessToken = null;
    public static String email = null;
    static String userAgent;


    static{

        userAgent = (String) ((JavascriptExecutor) WebDriver.getInstance()).executeScript("return navigator.userAgent;");

    }


    public RestAssuredApi() {

        int timeOutMilis = getAppDefaultWait()*1000;

        HttpClientConfig httpClientConfig = HttpClientConfig

                .httpClientConfig()

                .setParam("http.socket.timeout", timeOutMilis)

                .setParam("http.connection.timeout",timeOutMilis);

        RestAssured.config = RestAssured.config().httpClient(httpClientConfig);

        RestAssured.baseURI = System.getProperty("bankAPI");

    }

    public Response sendGetRequest(String endpoint) {

        RequestSpecification request = RestAssured.given().header("User-Agent", userAgent).log().all();

        return request.get(endpoint).then().log().all().extract().response();

    }

    public Response sendPostRequest(String endpoint, Headers headers, String body) {

        RequestSpecification request = RestAssured.given()

                .header("User-Agent", userAgent)

                .log().all().relaxedHTTPSValidation()

                .headers(headers)

                .body(body);

        Response res = request.when().post(endpoint).then().log().all().extract().response();
        adjuntarDiagnosticoServicioFallido(endpoint, headers, body, res);
        validarCodeResponses.validateResponses(res,endpoint);
        return res;
    }

    private void adjuntarDiagnosticoServicioFallido(String endpoint, Headers requestHeaders, String requestBody, Response response) {
        String responseBody = obtenerResponseBody(response);
        String code = obtenerCampoJson(responseBody, "code");

        if (esServicioOk(code)) {
            return;
        }

        String diagnostico = "Endpoint: " + endpoint + "\n"
                + "Base URI: " + valorSeguro(RestAssured.baseURI) + "\n"
                + "HTTP status: " + response.getStatusCode() + "\n"
                + "Codigo negocio: " + valorSeguro(code) + "\n"
                + "Authorization presente: " + tieneAuthorization(requestHeaders) + "\n"
                + "Authorization detalle: " + describirAuthorization(requestHeaders) + "\n"
                + "\nREQUEST HEADERS\n"
                + headersToString(requestHeaders, true) + "\n"
                + "\nREQUEST BODY\n"
                + sanitizarTexto(requestBody) + "\n"
                + "\nRESPONSE HEADERS\n"
                + headersToString(response.getHeaders(), false) + "\n"
                + "\nRESPONSE BODY\n"
                + sanitizarTexto(responseBody);

        System.out.println("========== API DEBUG SERVICIO FALLIDO ==========");
        System.out.println(diagnostico);
        Allure.addAttachment("API Debug - " + endpoint, diagnostico);
    }

    private boolean esServicioOk(String code) {
        return "COR000I".equals(code) || "COR013I".equals(code);
    }

    private String obtenerResponseBody(Response response) {
        try {
            return response.getBody().asString();
        } catch (Exception e) {
            return "No se pudo leer response body: " + e.getMessage();
        }
    }

    private String obtenerCampoJson(String body, String campo) {
        try {
            return JsonPath.from(body).getString(campo);
        } catch (Exception e) {
            return "No se pudo leer campo '" + campo + "': " + e.getMessage();
        }
    }

    private boolean tieneAuthorization(Headers headers) {
        return obtenerHeader(headers, "Authorization") != null;
    }

    private String describirAuthorization(Headers headers) {
        String authorization = obtenerHeader(headers, "Authorization");
        if (authorization == null) {
            return "NO ENVIADO";
        }
        if (authorization.trim().isEmpty()) {
            return "VACIO";
        }

        String[] partes = authorization.trim().split("\\s+", 2);
        String esquema = partes.length > 0 ? partes[0] : "sin esquema";
        String token = partes.length > 1 ? partes[1] : "";

        return "esquema=" + esquema
                + ", longitudTotal=" + authorization.length()
                + ", longitudToken=" + token.length()
                + ", formatoBearer=" + String.valueOf(authorization.startsWith("Bearer "))
                + ", formatoExchange=" + String.valueOf(authorization.startsWith("exchange "));
    }

    private String obtenerHeader(Headers headers, String nombre) {
        if (headers == null) {
            return null;
        }
        Header header = headers.get(nombre);
        return header == null ? null : header.getValue();
    }

    private String headersToString(Headers headers, boolean sanitizarAuthorization) {
        if (headers == null) {
            return "null";
        }

        StringBuilder builder = new StringBuilder();
        for (Header header : headers) {
            String value = header.getValue();
            if (sanitizarAuthorization && "Authorization".equalsIgnoreCase(header.getName())) {
                value = describirAuthorization(headers);
            }
            builder.append(header.getName()).append(": ").append(valorSeguro(value)).append("\n");
        }
        return builder.toString();
    }

    private String sanitizarTexto(String texto) {
        if (texto == null) {
            return "null";
        }
        return texto
                .replaceAll("(?i)(\"?_password\"?\\s*:\\s*\")[^\"]*(\")", "$1***$2")
                .replaceAll("(?i)(\"?password\"?\\s*:\\s*\")[^\"]*(\")", "$1***$2")
                .replaceAll("(?i)(\"?_accessToken\"?\\s*:\\s*\")[^\"]*(\")", "$1***$2")
                .replaceAll("(?i)(\"?accessToken\"?\\s*:\\s*\")[^\"]*(\")", "$1***$2")
                .replaceAll("(?i)(Bearer\\s+)[A-Za-z0-9._\\-]+", "$1***")
                .replaceAll("(?i)(exchange\\s+)[A-Za-z0-9._\\-]+", "$1***");
    }

    private String valorSeguro(String valor) {
        return valor == null ? "null" : valor;
    }

    /**

     * Busca y devuelve valores de un body

     * @param response restassured Response

     * @param path es una alternativa al uso de XPath para obtener valores de un JSON, para mas informacion <a href='
    https://www.javadoc.io/doc/io.rest-assured/json-path/latest/io/restassured/path/json/JsonPath.html'>aqui</a>

     * @return elemento tipo String

     */

    public String getStringFromJson(Response response, String path){

        return JsonPath.from(response.getBody().asString()).getString(path);

    }

    /**
     * Busca y devuelve valores de un body
     * @param response restassured Response
     * @param path es una alternativa al uso de XPath para obtener valores de un JSON, para mas informacion <a href='https://www.javadoc.io/doc/io.rest-assured/json-path/latest/io/restassured/path/json/JsonPath.html'>aqui</a>
     * @return elemento tipo Integer
     */
    public Integer getIntFromJson(Response response, String path){
        return JsonPath.from(response.getBody().asString()).getInt(path);
    }

    /**
     * Busca y devuelve valores de un body
     * @param response restassured Response
     * @param path es una alternativa al uso de XPath para obtener valores de un JSON, para mas informacion <a href='https://www.javadoc.io/doc/io.rest-assured/json-path/latest/io/restassured/path/json/JsonPath.html'>aqui</a>
     * @return elemento tipo Float
     */
    public Float getFloatFromJson(Response response, String path){
        return JsonPath.from(response.getBody().asString()).getFloat(path);
    }

    /**
     * Busca y devuelve valores de un body
     * @param response restassured Response
     * @param path es una alternativa al uso de XPath para obtener valores de un JSON, para mas informacion <a href='https://www.javadoc.io/doc/io.rest-assured/json-path/latest/io/restassured/path/json/JsonPath.html'>aqui</a>
     * @return elemento tipo Double
     */
    public Double getDoubleFromJson(Response response, String path){
        return JsonPath.from(response.getBody().asString()).getDouble(path);
    }

    /**
     * Busca y devuelve valores de un body
     * @param response restassured Response
     * @param path es una alternativa al uso de XPath para obtener valores de un JSON, para mas informacion <a href='https://www.javadoc.io/doc/io.rest-assured/json-path/latest/io/restassured/path/json/JsonPath.html'>aqui</a>
     * @return elemento tipo Boolean
     */
    public Boolean getBooleanFromJson(Response response, String path){
        return JsonPath.from(response.getBody().asString()).getBoolean(path);
    }

    /**
     * Busca y devuelve valores de un body
     * @param response restassured Response
     * @param path es una alternativa al uso de XPath para obtener valores de un JSON, para mas informacion <a href='https://www.javadoc.io/doc/io.rest-assured/json-path/latest/io/restassured/path/json/JsonPath.html'>aqui</a>
     * @return elemento tipo List
     */
    public List getListFromJson(Response response, String path){
        return JsonPath.from(response.getBody().asString()).getList(path);
    }

    /**
     * Busca y devuelve valores de un body
     * @param response restassured Response
     * @param path es una alternativa al uso de XPath para obtener valores de un JSON, para mas informacion <a href='https://www.javadoc.io/doc/io.rest-assured/json-path/latest/io/restassured/path/json/JsonPath.html'>aqui</a>
     * @return elemento tipo Map
     */
    public Map getMapFromJson(Response response, String path){
        return JsonPath.from(response.getBody().asString()).getMap(path);
    }
}
