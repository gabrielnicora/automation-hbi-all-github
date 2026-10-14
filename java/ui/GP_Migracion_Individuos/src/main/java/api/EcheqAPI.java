package api;

import global.practia.jemeinsamstark.web.core.WebDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.JavascriptExecutor;
import utils.Settings;
import utils.UtilsGP;

import java.util.List;

import static api.DobleFactorAPI.codigo2FAaux;
import static global.practia.jemeinsamstark.web.core.ConfigHelper.getAppDefaultWait;

public class EcheqAPI {


    UtilsGP utilsGP = new UtilsGP();
    String idTransaccion;
    public String exchange,accessToken,origin,email,idSession,
            originOriginal,emailOriginal,nuevoPersist,name,cuil = null;
    private String responseArmado;

    static  String userAgent;
    static {
        userAgent = (String) ((JavascriptExecutor)
                WebDriver.getInstance()).executeScript("return navigator.userAgent;");
    }

    public EcheqAPI(){

        int timeOutMilis = getAppDefaultWait()*1000;
        HttpClientConfig httpClientConfig = HttpClientConfig
                .httpClientConfig()
                .setParam("http.socket.timeout", timeOutMilis)
                .setParam("http.connection.timeout",timeOutMilis);
        RestAssured.config = RestAssured.config().httpClient(httpClientConfig);
    }

    public Response sendPostRequest(String endpoint, Headers headers, String body) {
        RequestSpecification request = RestAssured.given()
                .header("User-Agent", userAgent)
                .log().all().relaxedHTTPSValidation()
                .headers(headers)
                .body(body);

        return request.when().post(System.getProperty("baseURIEmp")+endpoint).then().log().all().extract().response();
    }
    public String getStringFromJson(Response response, String path){
        Object value = JsonPath.from(response.getBody().asString()).get(path);
        return value != null ? String.valueOf(value) : null;
    }

    public List getListFromJson(Response response, String path){
        return JsonPath.from(response.getBody().asString()).getList(path);
    }

    //Login API Empresa

    private Response loginStep1Empresa(String user){

        String body = utilsGP.leerContenidoArchivo("./Requests/login/session.login.step1.json");

        Headers headers = new Headers(

                new Header("Content-Type","application/json")

        );

        body = body.replace("_user_",user);

        return sendPostRequest("/api/v1/execute/session.login.step1", headers, body);

    }

    private Response loginStep2Empresa(String pass, String user){

        String body = utilsGP.leerContenidoArchivo("./Requests/login/session.login.step2.json");

        Headers  headers = new Headers(

                new Header("Content-Type","application/json"),
                new Header("Authorization","exchange " + exchange)

        );

        body = body.replace("_password_",pass).replace("_username_",user);

        return sendPostRequest("/api/v1/execute/session.login.step2", headers, body);

    }


    private Response loginStep3Empresa(String ambiente){

        String body = utilsGP.leerContenidoArchivo("./Requests/login/loginStep3Empresas.json");

        Headers  headers = new Headers(

                new Header("Content-Type","application/json"),
                new Header("Authorization","exchange " + exchange)

        );

        body = body.replace("_environment_", ambiente);

        return sendPostRequest("/api/v1/execute/session.login.step3", headers, body);

    }

    private Response loginStep4(){

        String body = utilsGP.leerContenidoArchivo("./Requests/login/session.login.step4.json");

        Headers  headers = new Headers(

                new Header("Content-Type","application/json"),
                new Header("Authorization","exchange " + exchange)
        );

        return sendPostRequest("/api/v1/execute/session.login.step4", headers, body);

    }

    public String getBearerToken(String user, String pass, String ambiente){
        Response responseLoginStep1,responseLoginStep3,utilLogger;


        responseLoginStep1 = loginStep1Empresa(user);
        exchange = getStringFromJson(responseLoginStep1,"data._exchangeToken");

        responseArmado = utilsGP.convertResponseToCorrectJSON(loginStep2Empresa(pass, user).getBody().asPrettyString());

        name = utilsGP.quitarCochetesJsonPath(JsonPath.from(responseArmado).getString("data.environments.name"));

        cuil = utilsGP.quitarCochetesJsonPath(JsonPath.from(responseArmado).getString("data.environments.productGroupId"));

        String IdAmbiente = utilsGP.quitarCochetesJsonPath(JsonPath.from(responseArmado).getString("data.environments.findAll{it.name == '"+ambiente+"'}.idEnvironment"));


        responseLoginStep3 = loginStep3Empresa(IdAmbiente);
        try{
            if(getStringFromJson(responseLoginStep3,"data.generalConditionsText")!=null){
                System.out.println("================== TyC ACTIVADO ==========================");
                responseLoginStep3 = loginStep4();
            }
        }catch(Exception e){
            System.out.println("============== USUARIO SIN TyC ACTIVADO ==================================");
        }

        idSession = getStringFromJson(responseLoginStep3,"data._accessToken");

        accessToken = String.format("Bearer " + idSession);

        //utilLogger = utilLoggerReadTransactions();

        origin = getStringFromJson(responseLoginStep3,"data.phoneNumber");
        System.out.println("==============  Numero de telefono ==============================" + origin);

        //email =  getStringFromJson(utilLogger,"data.userData.email");

        return accessToken;
    }

    //Datos de la cuenta

    public Response widgetsAccountEmpresas() {
        String body;
        body = utilsGP.leerContenidoArchivo("./Requests/compatidos/requestVacio.json");
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", accessToken)
        );

        return sendPostRequest("/api/v1/execute/widgets.accounts", headers, body);
    }


    //Se mapea el servicio de Consulta de Razón Social

    private Response echeqConsultSocialReason(String dni){
        String body;
        body = utilsGP.leerContenidoArchivo("./Requests/echeq/echeqConsultSocialReason.json");
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", accessToken)
        );

        body = body.replace("_document_",dni);

        return sendPostRequest("/api/v1/execute/echeq.consultSocialReason", headers, body);
    }

    private Response echeqEmit(String cuit, String razonSocial, String idCuentaOrigen, String fecha,
                               String caracter, String code, String echeqType){
        String body;
        body = utilsGP.leerContenidoArchivo("./Requests/echeq/echeqEmit.json");
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", accessToken)
        );

        body = body.replace("_cuitBeneficiario_",cuit)
                .replace("_socialReason_", razonSocial)
                .replace("_idProduct_", idCuentaOrigen)
                .replace("_paymentDate_",fecha)
                .replace("_character_",caracter)
                .replace("_code_", code)
                .replace("_echeqType_",echeqType);

        return sendPostRequest("/api/v1/execute/echeq.emit", headers, body);
    }


    private Response ticketGet(){
        String body;
        body = utilsGP.leerContenidoArchivo("./Requests/compartidos/ticket.json");
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", accessToken)
        );

        body = body.replace("_idTransaccion_", idTransaccion);

        return sendPostRequest("/api/v1/execute/ticket.get", headers, body);
    }

    //Doble Factor Empresa

    public String getSecondFactorValidate(){
        int cant2FActualizada, cant2FActual=0;
        int j = 1;
        String codigo = null;
        Response utilCode;

        utilsGP.fixedWait(3000);

        utilCode = executeUtilCode();
        cant2FActual = getListFromJson(utilCode,"data.codeList.findAll {it.origin == '" + origin + "'}").size();

        System.out.println("======== ACUTAL :" +  cant2FActual);

        while (j<4){
            sendTokenSms();
            for (int i = 1; i <= 4; i++) {

                utilsGP.fixedWait(2500);

                utilCode= executeUtilCode();
                cant2FActualizada = getListFromJson(utilCode,"data.codeList.findAll {it.origin == '"+ origin +"'}").size();

                System.out.println("===SEGUNDOS PARA ACTUALIZAR LISTCODE : "+ i*2 +" SEGUNDOS");
                System.out.println("============= CANTIDAD DE CODIGOS ACTUALIZADA : " + cant2FActualizada);

                if (cant2FActual < cant2FActualizada) {

                    codigo = getStringFromJson(utilCode,"data.codeList.findAll {it.origin == '"+ origin +"'}.last().code");
                    System.out.println("=============== CODIGO ANTERIOR UTILIZADO : "+ codigo2FAaux +"===================================");

                    if(codigo.equals(codigo2FAaux)){
                        System.out.println("======== SE REPITE EL CODIGO SE VUELVE ACTUALIZAR EL UTILCODE ==============");
                    }else{
                        System.out.println("====================== CODIGO LIMPIO NO REPETIDO ==========================");
                        codigo2FAaux =  codigo;
                        break;
                    }
                }
            } if(codigo == null){
                System.out.println("No se actualizo los codigos volver a pedir token");
                cant2FActual = 0;//restAssuredApi.getListFromJson(secondFactor(),"data.codeList.findAll {it.origin == '"+ origin +"'}").size();
                System.out.println("================= EL NUEVO ACTUAL   "+ cant2FActual);
                j++;
            }else {
                System.out.println("cantidad send token enviados : " + j);
                break;
            }
        }

        System.out.println("=============  CODIGO : " + codigo);
        return codigo;
    }


    private Response executeUtilCode(){
        String body = utilsGP.leerContenidoArchivo("./Requests/dobleFactor/utilCode.json");
        Headers header = new Headers(
                new Header("Content-Type","application/json"),
                new Header ("Authorization", accessToken)
        );
        return sendPostRequest("/api/v1/execute/util.codes",header,body);
    }

    private Response sendTokenSms(){
        String body = utilsGP.leerContenidoArchivo("./Requests/compatidos/requestVacio.json");
        Headers headers = new Headers(
                new Header("Content-Type","application/json"),
                new Header("Authorization", accessToken)
        );
        return sendPostRequest("/api/v1/execute/secondFactor.otp.sms.sendToken",headers,body);
    }


    // En esta parte se consumen los métodos realizados arriba

    @Step("Generación de echeq desde Empresas")
    public void generacionDeEcheqAPI (String user, String pass, String ambiente,
                                      String cuit, String cuenta, String cantDeDias,
                                      String caracter){

        //Variables

        String razonSocial, fecha, code = null;
        Response response;

        //Login Empresa

        getBearerToken(user, pass, ambiente);

        if (cantDeDias.equals("0")) {
            Settings.echeqType = "Normal";
        } else {
            Settings.echeqType = "Diferido";
        }

        fecha = utilsGP.sumarFechaHoyPorDias(Integer.parseInt(cantDeDias));


        response = widgetsAccountEmpresas();
        Settings.idCuentaOrigen = utilsGP.quitarCochetesJsonPath(getStringFromJson(response, "data.accounts.findAll{it.shortLabel == '"+cuenta+"'}.idProduct"));
        Allure.addAttachment("ID Cuenta Origen",Settings.idCuentaOrigen);


        //Consultamos la Razón Social

        response = echeqConsultSocialReason(cuit);

        razonSocial = getStringFromJson(response, "data.socialReason");
        Allure.addAttachment("Razon Social",razonSocial);

        // Emisión de echeq


        code = getSecondFactorValidate();

        response = echeqEmit(cuit, razonSocial, Settings.idCuentaOrigen,
                fecha, caracter, code, Settings.echeqType);

        //Obtener ID Transacción

        idTransaccion = getStringFromJson(response, "idTransaction");

        //Se corre servicio del Ticket

        response = ticketGet();

        //Se obtiene el nro del echeq

        Settings.nroDeEcheq = getStringFromJson(response,
                "data._ticket.data.ticket.echeqsNumbers");
        System.out.println("============ Nro de Echeq =========" + Settings.nroDeEcheq);

    }

}
