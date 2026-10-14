package api;

import io.qameta.allure.Step;
import io.restassured.builder.ResponseBuilder;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import utils.RestAssuredApi;
import utils.Settings;
import utils.UtilsGP;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static global.practia.jemeinsamstark.web.core.ConfigHelper.getAppDefaultWait;

import static utils.Settings.nroDeOperacion;

public class LoginAPI{
    RestAssuredApi restAssuredApi = new RestAssuredApi();
    UtilsGP utilsGP = new UtilsGP();
    public static String exchange,accessToken,origin,email,idSession,originOriginal,emailOriginal,nuevoPersist,name,cuil = null;
    private static volatile String usuarioAutenticado;
    private String responseArmado;
    private Response responseLoginStep1;
    public static String idEnvironment;

    public static String getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    private Response loginStep1(String user){
        return loginStep1(user, false);
    }

    private Response loginStep1(String user, boolean desdeNavegador){

        String body = utilsGP.leerContenidoArchivo("./Requests/login/session.login.step1.json");

        Headers headers = new Headers(

                new Header("Content-Type","application/json")

        );

        body = body.replace("_user_",user);

        return enviarLogin("api/v1/execute/session.login.step1", headers, body, desdeNavegador);

    }
    private Response loginStep2(String pass, String user){
        return loginStep2(pass, user, false);
    }

    private Response loginStep2(String pass, String user, boolean desdeNavegador){

        String body = utilsGP.leerContenidoArchivo("./Requests/login/session.login.step2.json");

        Headers  headers = new Headers(

                new Header("Content-Type","application/json"),
                new Header("Authorization","exchange " + exchange)

        );

        body = body.replace("_password_",pass).replace("_username_",user);

        return enviarLogin("api/v1/execute/session.login.step2", headers, body, desdeNavegador);

    }
    private Response loginStep3(){
        return loginStep3(false);
    }

    private Response loginStep3(boolean desdeNavegador){

        String body = utilsGP.leerContenidoArchivo("./Requests/login/session.login.step3.json");

        Headers  headers = new Headers(

                new Header("Content-Type","application/json"),
                new Header("Authorization","exchange " + exchange)

        );

        return enviarLogin("api/v1/execute/session.login.step3", headers, body, desdeNavegador);

    }
    private Response loginStep4(){
        return loginStep4(false);
    }

    private Response loginStep4(boolean desdeNavegador){

        String body = utilsGP.leerContenidoArchivo("./Requests/login/session.login.step4.json");

        Headers  headers = new Headers(

                new Header("Content-Type","application/json"),
                new Header("Authorization","exchange " + exchange)

        );

        return enviarLogin("api/v1/execute/session.login.step4", headers, body, desdeNavegador);

    }
    private Response utilLoggerReadTransactions(){
        return utilLoggerReadTransactions(false);
    }

    private Response utilLoggerReadTransactions(boolean desdeNavegador){
        String body = utilsGP.leerContenidoArchivo("./Requests/login/utilLoggerReadTransaccion.json");

        Headers headers = new Headers(

                new Header("Content-Type","application/json")

        );

        body = body.replace("_idSession_",idSession);

        return enviarLogin("api/v1/execute/util.logger.read.transactions", headers, body, desdeNavegador);
    }

    /** Mantiene la autenticación y el uso del token en el mismo contexto del navegador. */
    private Response enviarLogin(String endpoint, Headers headers, String body, boolean desdeNavegador) {
        if (!desdeNavegador) {
            return restAssuredApi.sendPostRequest(endpoint, headers, body);
        }

        WebDriver driver = global.practia.jemeinsamstark.web.core.WebDriver.getInstance();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String appUrl = System.getProperty("appUrl");
        String apiUrl = System.getProperty("bankAPI");
        if (appUrl == null || apiUrl == null) {
            throw new IllegalStateException("Falta configurar appUrl o bankAPI para el login API desde el navegador");
        }
        // El fetch debe salir desde el origen del home banking para poder inyectar la sesión allí.
        String appOrigin = java.net.URI.create(appUrl).resolve("/").toString();
        String currentOrigin = java.net.URI.create(driver.getCurrentUrl()).resolve("/").toString();
        if (!appOrigin.equals(currentOrigin)) {
            driver.navigate().to(appUrl);
        }

        Map<String, String> requestHeaders = new LinkedHashMap<>();
        for (Header header : headers) {
            requestHeaders.put(header.getName(), header.getValue());
        }
        int timeoutSeconds = Math.max(1, getAppDefaultWait());
        driver.manage().timeouts().setScriptTimeout(timeoutSeconds + 5L, TimeUnit.SECONDS);
        Object result = js.executeAsyncScript(
                "var url=arguments[0],headers=arguments[1],body=arguments[2],timeout=arguments[3],done=arguments[arguments.length-1];"
                        + "var finished=false,controller=new AbortController();"
                        + "function finish(value){if(finished)return;finished=true;clearTimeout(timer);done(value);}"
                        + "var timer=setTimeout(function(){controller.abort();finish({error:'timeout'});},timeout);"
                        + "fetch(url,{method:'POST',headers:headers,body:body,signal:controller.signal})"
                        + ".then(function(response){return response.text().then(function(text){finish({status:response.status,body:text});});})"
                        + ".catch(function(){finish({error:'network'});});",
                apiUrl.replaceAll("/+$", "") + "/" + endpoint, requestHeaders, body, timeoutSeconds * 1000L);
        if (!(result instanceof Map)) {
            throw new IllegalStateException("Respuesta inesperada del navegador al ejecutar " + endpoint);
        }
        Map<?, ?> browserResponse = (Map<?, ?>) result;
        if (browserResponse.containsKey("error")) {
            throw new IllegalStateException("No se pudo ejecutar " + endpoint + " desde el navegador: " + browserResponse.get("error"));
        }
        int status = ((Number) browserResponse.get("status")).intValue();
        Response response = new ResponseBuilder().setStatusCode(status).setContentType("application/json")
                .setBody((String) browserResponse.get("body")).build();
        String code;
        try {
            code = response.jsonPath().getString("code");
        } catch (RuntimeException error) {
            throw new IllegalStateException("Respuesta no JSON de " + endpoint + " (HTTP " + status + ")");
        }
        if (code == null) {
            throw new IllegalStateException("Respuesta sin código de " + endpoint + " (HTTP " + status + ")");
        }
        new ValidarCodeResponses().validateResponses(response, endpoint);
        if (status < 200 || status >= 300) {
            throw new IllegalStateException("Error en " + endpoint + ": HTTP " + status + ", código " + code);
        }
        return response;
    }
    /**
     * Realiza el login 1, login 2 y login 3
     *
     * @param user
     * @param pass
     * @return Bearer Token
     */
   public String getBearerToken(String user, String pass){
        Response responseLoginStep1,responseLoginStep3,utilLogger;

        usuarioAutenticado = null;
        responseLoginStep1 = loginStep1(user);
        exchange = restAssuredApi.getStringFromJson(responseLoginStep1,"data._exchangeToken");

        responseArmado = utilsGP.convertResponseToCorrectJSON(loginStep2(pass, user).getBody().asPrettyString());

        name = utilsGP.quitarCochetesJsonPath(JsonPath.from(responseArmado).getString("data.environments.name"));

        cuil = utilsGP.quitarCochetesJsonPath(JsonPath.from(responseArmado).getString("data.environments.productGroupId"));

        responseLoginStep3 = loginStep3();
       try{
           if(restAssuredApi.getStringFromJson(responseLoginStep3,"data.generalConditionsText")!=null){
               System.out.println("================== TyC ACTIVADO ==========================");
               responseLoginStep3 = loginStep4();
           }
       }catch(Exception e){
           System.out.println("============== USUARIO SIN TyC ACTIVADO ==================================");
       }

        idSession = restAssuredApi.getStringFromJson(responseLoginStep3,"data._accessToken");

        accessToken = String.format("Bearer " + idSession);

        utilLogger = utilLoggerReadTransactions();

        origin = restAssuredApi.getStringFromJson(utilLogger,"data.userData.mobileNumber");

        email =  restAssuredApi.getStringFromJson(utilLogger,"data.userData.email");

        usuarioAutenticado = user;
        return accessToken;
    }

    /**
     * Realiza el login API desde el navegador y arma el persist:session para su inyección.
     * @param user
     * @param pass
     * @return Map ('bearer', 'persist:session' (sessionStorage key))
     */
    public String getBearerAndPersistSession(String user, String pass){
        Response responseLoginStep3,utilLogger;

        usuarioAutenticado = null;
        responseLoginStep1 = loginStep1(user, true);
        exchange = restAssuredApi.getStringFromJson(responseLoginStep1,"data._exchangeToken");
        if (exchange == null || exchange.trim().isEmpty()) {
            throw new IllegalStateException("El login API desde el navegador no devolvió un exchangeToken");
        }

        responseArmado = utilsGP.convertResponseToCorrectJSON(loginStep2(pass, user, true).getBody().asPrettyString());


        name = utilsGP.quitarCochetesJsonPath(JsonPath.from(responseArmado).getString("data.environments.name"));

        cuil = utilsGP.quitarCochetesJsonPath(JsonPath.from(responseArmado).getString("data.environments.productGroupId"));
        idEnvironment = utilsGP.quitarCochetesJsonPath(JsonPath.from(responseArmado).getString("data.environments.idEnvironment"));

        responseLoginStep3 = loginStep3(true);
        try{
            if(restAssuredApi.getStringFromJson(responseLoginStep3,"data.generalConditionsText")!=null){
                System.out.println("================== TyC ACTIVADO ==========================");
                responseLoginStep3 = loginStep4(true);
            }
        }catch(Exception e){
            System.out.println("============== USUARIO SIN TyC ACTIVADO ==================================");
        }

        idSession = restAssuredApi.getStringFromJson(responseLoginStep3,"data._accessToken");

        if (idSession == null || idSession.trim().isEmpty()) {
            throw new IllegalStateException("El login API desde el navegador no devolvió un accessToken");
        }

        accessToken = String.format("Bearer " + idSession);

        utilLogger = utilLoggerReadTransactions(true);

        origin = restAssuredApi.getStringFromJson(utilLogger,"data.userData.mobileNumber");
        originOriginal = origin;
        System.out.println("================== TELEFONO : " + origin);

        email = restAssuredApi.getStringFromJson(utilLogger,"data.userData.email");
        emailOriginal = email;
        System.out.println("================== EMAIL : " + email);

        nuevoPersist = overridePersistJson(responseLoginStep1,responseArmado, responseLoginStep3);

        usuarioAutenticado = user;
        return nuevoPersist;
    }
    private String overridePersistJson(Response responseLoginStep1,String responseArmado, Response responseLoginStep3) {
        UtilsGP utils = new UtilsGP();
        String persist,activeEnvironment,enabledFunctionalities,userPermissionOverFunctionality,user,permissions,productPermissionOverFunctionality
                ,environmentsEscapado,previousLoginInfoEscapado;

        //-----------------------------INICIO ARMADO DEL ARCHIVO ACTIVEENVIRONMENT.JSON--------------------------------------------------------------------------------//
        //Comienza el armado del archivo ActiveEnvironment.json - Leemos el archivo para su procesamiento
        activeEnvironment = utils.leerContenidoArchivo("./Requests/persistSession/ActiveEnvironment.json");

        //Obtenemos y escapamos los permissions que tiene el usuario a loguear para mandarlo como insertarlo en el activeEnvironment.json
        permissions =  escaparPermissionJson(JsonPath.from(responseLoginStep3.getBody().asPrettyString()).get("data.permissions").toString());

        //Obtenemos y escapamos  json de enabledFunctionalities que tiene el usuario a loguear para mandarlo como insertarlo en el activeEnvironment.json
        enabledFunctionalities = escaparArraySoloParametros(JsonPath.from(responseLoginStep3.getBody().asPrettyString()).get("data.enabledFunctionalities").toString());

        //Obtenemos y escapamos json userPermissionOverFunctionality que tiene el usuario a loguear para mandarlo como insertarlo en el activeEnvironment.json
        userPermissionOverFunctionality = escaparArraySoloParametros(JsonPath.from(responseLoginStep3.getBody().asPrettyString()).get("data.userPermissionOverFunctionality").toString());

        //Obtenemos y escapamos json productPermissionOverFunctionality que tiene el usuario a loguear para mandarlo como insertarlo en el activeEnvironment.json
        productPermissionOverFunctionality = escaparArraySoloParametros(JsonPath.from(responseLoginStep3.getBody().asPrettyString()).get("data.productPermissionOverFunctionality").toString());

        //escapamos el archivo activeEnvironment.json
        activeEnvironment = utils.escaparArchivoJsonToString(activeEnvironment);

        //Armamos el ActiveEnvironment escapado con sus respectivos replaces para ser insertado en el Persist.Json
        activeEnvironment = armadoActiveEnvironmentJson(activeEnvironment,permissions,enabledFunctionalities,userPermissionOverFunctionality,productPermissionOverFunctionality,responseLoginStep3);
        //--------------------------------FIN DEL ARCHIVO ACTIVEENVIRONMENT.JSON PROCESADO---------------------------------------------------------------------//

        //Obtenemos y escapamos el Ambiente que necesitamos loguear para insertarlo en el Persist.Json
        environmentsEscapado = utils.leerContenidoArchivo("./Requests/persistSession/environments.json");
        environmentsEscapado = utils.escaparArchivoJsonToString(environmentsEscapado);
        //ARMADO DE ENVIRONEMNTS ESCAPADO DESDE EL ARCHIVO
        environmentsEscapado =  escaparEnvironmentSeleccionado(responseArmado,environmentsEscapado);

        System.out.println("=== =ENVIRONMENT : " + environmentsEscapado);
        //environmentsEscapado = escaparEnvironmentSeleccionado(JsonPath.from(responseLoginStep2).get("data.environments.findAll {it.idEnvironment == '"+idEnvironment+"'}").toString());

        //---------------------------------------INICIO DEL ARMADO DE ARCHIVO USER.JSON ------------------------------------------------------------------//

        //---------------------------------------INICIO DEL ARMADO DE ARCHIVO USER.JSON ------------------------------------------------------------------//

        //Leemos el archivo para su procesamiento.
        user = utils.leerContenidoArchivo("./Requests/persistSession/user.json");

        //Escapamos el archivo base user.json
        user = utils.escaparArchivoJsonToString(user);

        //Obtenemos y escapamos json del bloque PreviousLoginInfo que tiene el usuario para insertarlo en el archivo user.json
        previousLoginInfoEscapado = escaparPreviosLoginInfo(JsonPath.from(responseLoginStep3.getBody().asPrettyString()).get("data.previousLoginInfo").toString());

        //Armamos el user.json escapado con sus respectivos replaces para ser insertado en el Persist.Json
        user = armadoUserJson(user,previousLoginInfoEscapado,responseLoginStep3,responseLoginStep1);

        //---------------------------------------FIN DEL ARCHIVO USER.JSON PROCESADO---------------------------------------------------------------------//

        //---------------------------------------INICIO DEL ARMADO DE ARCHIVO PERSIST.JSON ------------------------------------------------------------------//

        //leemos el archivo persist.json para su procesamiento
        persist = utils.leerContenidoArchivo("./Requests/persistSession/persist.json");

        System.out.println("============= ARMADO DE PERSIST SESSION DESDE ARCHIVO ====================");

        //Armado del persist.json - ActiveEnvironment.json, environments y user.json
        //Adicional se necesita algunos campos que se obtienen desde el loginStep3
        persist = persist.replace("_activeEnvironments_",activeEnvironment)
                .replace("_idEnvironments_",environmentsEscapado)
                .replace("_user_",user)
                .replace("_userID_",restAssuredApi.getStringFromJson(responseLoginStep3,"data.userId"))
                .replace("_segment_",utils.validarNullParametro(restAssuredApi.getStringFromJson(responseLoginStep3,"data.segment")))
                .replace("\\\" ","\\\"");

        //---------------------------------------FIN DEL ARCHIVO PERSIST.JSON PROCESADO---------------------------------------------------------------------//
        System.out.println("PERSIST : " + persist);

        return persist;


    }
    public int tomarIdImagen(String user) {
        Response responseLoginStep1;
        int idImagenLogueo;

        responseLoginStep1 = loginStep1(user);
        idImagenLogueo = Integer.parseInt(restAssuredApi.getStringFromJson(responseLoginStep1, "data._securitySealId"));

        return idImagenLogueo;
    }
    public String getTelefono(){
        Response response;
        String telefono;
        response = utilLoggerReadTransactions();
        telefono = restAssuredApi.getStringFromJson(response,"data.userData.mobileNumber");
        return telefono;
    }
    public String getMail(){
        Response response;
        String mail;
        response = utilLoggerReadTransactions();
        mail = restAssuredApi.getStringFromJson(response,"data.userData.email");
        return mail;
    }

    /**
     * Método para escapar el objeto Json de los permisos del usuario
     *
     * @param permissions objeto que se extrae del login step 3
     * @return permissions objeto procesado con su respectivo escapado para el active Environment.json
     */
    private String escaparPermissionJson(String permissions){
        permissions =  permissions.replace("=",":")
                .replace("{","{\\\"")
                .replace(":","\\\":")
                .replace(",","\\\",\\\"")
                .replace("\\\",",",");

        return permissions;
    }

    /**
     * Método que se encarga de escapar objetos de tipo Arrays que solo tiene parametro pero no tienen valor
     *
     * @param array objeto que se extrae del login step 3
     * @return Objeto procesado - Array escapado
     */
    public String escaparArraySoloParametros(String array){
        array = array.replace("[","[\\\"")
                .replace(",","\\\",\\\"")
                .replace("]","\\\"]");

        return array;
    }
    /**
     * Método que se encarga de escapar Array Json de "PreviousLoginInfo" para ser insertado en el archivo user.json
     *
     * @param previousLoginInfo array que contiene la información del usuario...
     * @return datos del usuario ip,data, hora escapado.
     */
    public String escaparPreviosLoginInfo(String previousLoginInfo){
        previousLoginInfo = previousLoginInfo.replace("[{","[{\\\"")
                .replace(",","\\\",\\\"")
                .replace("=","\\\"=\\\"")
                .replace("0.0\\\"","0")
                .replace("\\\"=\\\"","\\\":\\\"")
                .replace("\\\"0","0")
                .replace("\\\"null\\\"","null")
                .replace("\\\"null", "null")
                .replace("\\\" {","{\\\"")
                .replace("}\\\"","}")
                .replace("idUser\\\":0","idUser\\\":\\\"0");

        return previousLoginInfo;

    }
    /**
     * Método que escapa el array json del ambiente que se quiere loguear
     *
     * @param
     * @return el Array del ambiente escapado
     */
    public String escaparEnvironmentSeleccionado(String responseLoginStep2,String environmentsEscapado){
        String nameEnvironment = JsonPath.from(responseLoginStep2).get("data.environments.find {it.idEnvironment == '"+idEnvironment+"'}.name").toString();
        String environmentType = JsonPath.from(responseLoginStep2).get("data.environments.find {it.idEnvironment == '"+idEnvironment+"'}.environmentType").toString();
        String allowedToAccess = JsonPath.from(responseLoginStep2).get("data.environments.find {it.idEnvironment == '"+idEnvironment+"'}.allowedToAccess").toString();
        String calendarLoginRestrictionsEnabled = JsonPath.from(responseLoginStep2).get("data.environments.find {it.idEnvironment == '"+idEnvironment+"'}.calendarLoginRestrictionsEnabled").toString();
        String ipRestrictionsEnabled = JsonPath.from(responseLoginStep2).get("data.environments.find {it.idEnvironment == '"+idEnvironment+"'}.ipRestrictionsEnabled").toString();
        String productGroupId = JsonPath.from(responseLoginStep2).get("data.environments.find {it.idEnvironment == '"+idEnvironment+"'}.productGroupId").toString();
        //String isActive = JsonPath.from(responseLoginStep2).get("data.environments.find {it.idEnvironment == '"+idEnvironment+"'}.isActive").toString();

        environmentsEscapado = environmentsEscapado.replace("_idEnvironment_",idEnvironment)
                .replace("_name_",nameEnvironment)
                .replace("_environmentType_",environmentType)
                .replace("_allowedToAccess_",allowedToAccess)
                .replace("_calendarLoginRestrictionsEnabled_",calendarLoginRestrictionsEnabled)
                .replace("_ipRestrictionsEnabled_",ipRestrictionsEnabled)
                .replace("_productGroupId_",productGroupId);
                //.replace("_isActive_",isActive);

        return environmentsEscapado;
    }

    /**
     * Método que se encarga de armar el archivo activeEnvironment.json con los valores antes procesados
     *
     * @param activeEnvironment
     * @param permissions
     * @param enabledFunctionalities
     * @param userPermissionOverFunctionality
     * @param productPermissionOverFunctionality
     * @param responseLoginStep3
     * @return archivo activeEnvironment.json escapado y procesado para ser insertado en el archivo persist.json
     */
    public String armadoActiveEnvironmentJson(String activeEnvironment,String permissions, String enabledFunctionalities,String userPermissionOverFunctionality,String productPermissionOverFunctionality,Response responseLoginStep3){
        activeEnvironment = activeEnvironment.replace("\\\"_enabledFunctionalities_\\\"","_enabledFunctionalities_")
                .replace("\\\"_userPermissionOverFunctionality_\\\"","_userPermissionOverFunctionality_")
                .replace("\\\"_idEnvironment_\\\"","_idEnvironment_")
                .replace("\\\"_productPermissionOverFunctionality_\\\"","_productPermissionOverFunctionality_")
                .replace("\\\"_permissions_\\\"","_permissions_")
                .replace("_permissions_",permissions)
                .replace("_fullName_",restAssuredApi.getStringFromJson(responseLoginStep3,"data.activeEnvironmentName"))
                .replace("_type_",restAssuredApi.getStringFromJson(responseLoginStep3,"data.activeEnvironmentType"))
                .replace("_idEnvironment_",idEnvironment)
                .replace("_enabledFunctionalities_",enabledFunctionalities)
                .replace("_userPermissionOverFunctionality_",userPermissionOverFunctionality)
                .replace("_productPermissionOverFunctionality_",productPermissionOverFunctionality)
                .replace("\\\"}","}")
                .replace("permissions\\\":\\\"","permissions\\\":")
                .replace("\\\"}","}");

        System.out.println("ACTIVE: " + activeEnvironment);

        return activeEnvironment;
    }

    /**
     * Método que se encarga de armar el archivo user.json con los valores antes procesados
     *
     * @param user
     * @param previousLoginInfoEscapado
     * @param responseLoginStep3
     * @param responseLoginStep1
     * @return archivo user.json escapado y procesado para ser insertado en el archivo persist.json
     */
    public String armadoUserJson(String user,String previousLoginInfoEscapado,Response responseLoginStep3,Response responseLoginStep1){
        user = user.replace("\\\"_previousLoginInfo_\\\"","_previousLoginInfo_")
                .replace("_defaultAvatarId_",restAssuredApi.getStringFromJson(responseLoginStep3,"data.defaultAvatarId"))
                .replace("_previousLoginInfo_",previousLoginInfoEscapado)
                .replace("_userFullName_",restAssuredApi.getStringFromJson(responseLoginStep3,"data.userFullName"))
                .replace("_email_",restAssuredApi.getStringFromJson(responseLoginStep3,"data.email"))
                .replace("_firstName_",restAssuredApi.getStringFromJson(responseLoginStep3,"data.firstName"))
                .replace("_phoneNumber_",restAssuredApi.getStringFromJson(responseLoginStep3,"data.phoneNumber"))
                .replace("_gender_",restAssuredApi.getStringFromJson(responseLoginStep3,"data.gender"))
                .replace("_documentNumber_",restAssuredApi.getStringFromJson(responseLoginStep3,"data.documentNumber"))
                .replace("_accessToken_",restAssuredApi.getStringFromJson(responseLoginStep3,"data._accessToken"))
                .replace("_securitySeal_",restAssuredApi.getStringFromJson(responseLoginStep1,"data._securitySeal"))
                .replace("_securitySealId_",restAssuredApi.getStringFromJson(responseLoginStep1,"data._securitySealId"))
                .replace("securitySealId\\\":\\\"","securitySealId\\\":")
                .replace("\\\"}","}");
        return user;
    }


}
