package Steps.Login;

import Config.ValidarResponse;
import com.jayway.jsonpath.JsonPath;
import constants.RequestMethods;
import core.PandoraRequest;
import core.PandoraResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import Config.Settings;
import org.GP_Individuos_API.Utils;
import org.junit.jupiter.api.Assertions;
import steps.RequestSteps;

import static Config.Settings.*;

public class LoginApiSteps {
    private static String usuarioAutenticado;

    Utils utils = new Utils();
    RequestSteps requestSteps = new RequestSteps();
    String host = System.getProperty("bankAPI");
    PandoraRequest postRequest = new PandoraRequest(host, RequestMethods.POST);
    ValidarResponse validarResponse = new ValidarResponse();

    public static String getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    @Step("Login API para obtener BearerToken - usuario : {user} - password : {password}")
    public void loginUsuario(String user,String password) {
        String exchangeToken, bearerToken;

        usuarioAutenticado = null;
        System.out.println();
        PandoraResponse response;

        response = loginStep1(user);
        validarResponse.validateResponses(response);
        Settings.exchangeToken = JsonPath.parse(response.getBody().toString()).read("$.data._exchangeToken").toString();

        response = loginStep2(user, password, Settings.exchangeToken);
        validarResponse.validateResponses(response);


        exchangeToken = JsonPath.parse(response.getBody().toString()).read("$.data._exchangeToken").toString();

        Assertions.assertEquals(exchangeToken, Settings.exchangeToken, "ERROR NO COINCIDEN LOS EXCHANGE TOKENS DEL SETP1 y SETP2");

        response = loginStep3(Settings.exchangeToken);
        validarResponse.validateResponses(response);
        try {
            if (JsonPath.parse(response.getBody().toString()).read("$.data.generalConditionsText")!=null) {
                System.out.println("================== TyC ACTIVIADO ==========================");
                response = loginStep4(Settings.exchangeToken);
                bearerToken = JsonPath.parse(response.getBody().toString()).read("$.data._accessToken").toString();
            }
        }catch (Exception e){
            System.out.println("============ USUARIO SIN TyC ========================");
        }


        bearerToken = JsonPath.parse(response.getBody().toString()).read("$.data._accessToken").toString();

        idSession = bearerToken;

        //obtener datos del emisor

        String telefonoBlureado = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.phoneNumber").toString();

        telefono = telefonoBlureado.substring(telefonoBlureado.length()-3);
        System.out.println("=========== 3 ultimos digitos de telefono : "+ telefono);
        Settings.documentNumber = JsonPath.parse(response.getBody().
                toPrettyString()).read("$.data.documentNumber");
        Settings.name = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().
                toPrettyString()).read("$.data.userFullName").toString());
        Allure.addAttachment("NOMBRE DEL EMISOR ",
                String.valueOf(Settings.name));
        Settings.cuil = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().
                toPrettyString()).read("$.data.environments..productGroupId")
                .toString());
        Allure.addAttachment("CUIL DEL EMISOR ",
                String.valueOf(Settings.cuil));




        //obtener el telefono del usuario
        response = utilLoggerReadTransactions();
        validarResponse.validateResponses(response);

        Settings.telefono = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.userData.mobileNumber").toString();

        Settings.mail = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.userData.email");
        System.out.println("**************ESTE ES EL MAIL DE STEP3: "+Settings.mail);

        Settings.bearerToken = "Bearer "+ idSession;
        usuarioAutenticado = user;
        System.out.println("");
    }
    @Step("Login API para obtener BearerToken - usuario : {user} - password : {password}")
    public void loginUsuario(String user,String password, String ambiente) {
        String exchangeToken, bearerToken;

        usuarioAutenticado = null;
        System.out.println();
        PandoraResponse response;
        PandoraResponse responseLoginStep2;

        response = loginStep1(user);
        validarResponse.validateResponses(response);
        Settings.exchangeToken = JsonPath.parse(response.getBody().toString()).read("$.data._exchangeToken").toString();

        responseLoginStep2 = loginStep2(user, password, Settings.exchangeToken);
        String responseEnvironments = utils.convertResponseToCorrectJSON(responseLoginStep2.getBody().toPrettyString(), "environments", "selectEnvironment");
        String idEnvironment =utils.quitarCochetesJsonPath(JsonPath.parse(responseEnvironments).read("$.data.environments[?(@.name == '" + ambiente + "')].idEnvironment").toString());
        validarResponse.validateResponses(responseLoginStep2);

        exchangeToken = JsonPath.parse(responseLoginStep2.getBody().toString()).read("$.data._exchangeToken").toString();

        Assertions.assertEquals(exchangeToken, Settings.exchangeToken, "ERROR NO COINCIDEN LOS EXCHANGE TOKENS DEL SETP1 y SETP2");

        response = loginStep3(Settings.exchangeToken, idEnvironment);
        validarResponse.validateResponses(response);
        try {
            if (JsonPath.parse(response.getBody().toString()).read("$.data.generalConditionsText")!=null) {
                System.out.println("================== TyC ACTIVIADO ==========================");
                response = loginStep4(Settings.exchangeToken);
                bearerToken = JsonPath.parse(response.getBody().toString()).read("$.data._accessToken").toString();
            }
        }catch (Exception e){
            System.out.println("============ USUARIO SIN TyC ========================");
        }

        bearerToken = JsonPath.parse(response.getBody().toString()).read("$.data._accessToken").toString();

        Settings.bearerToken = bearerToken;

        Settings.bearerToken = "Bearer "+Settings.bearerToken;

        //obtener el telefono del usuario
        //response = getUserData();
        //validarResponse.validateResponses(response);
        String telefonoBlureado = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.phoneNumber").toString();

        Settings.telefono =telefonoBlureado.substring(telefonoBlureado.length()-3);
        usuarioAutenticado = user;
        System.out.println("NRO DE TELEFONO "+Settings.telefono);
    }

    @Step("OBTENER TELEFONO DE USUARIO EMPRESA")
    public void obtenerTelefonoEmpresa(){
        PandoraResponse response;
        response = getUserData();
        validarResponse.validateResponses(response);
        Settings.telefono =JsonPath.parse(response.getBody().toPrettyString()).read("$.data.mobileAreaCode").toString() + JsonPath.parse(response.getBody().toPrettyString()).read("$.data.completeMobilePhone").toString();
        System.out.println("=========== TELEFONO USUARIO EMPRESA : " + telefono);
    }


    private PandoraResponse loginStep1(String user) {
        endpoint = "/api/v1/execute/session.login.step1";
        postRequest.setHeaders("Content-Type","application/json");
        postRequest.setPaths(endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Login/loginStep1.json");
        body = body.replace("_user_", user);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse loginStep2(String user, String pass, String exchangeToken) {
        endpoint = "/api/v1/execute/session.login.step2";
        postRequest.setHeaders("Content-Type","application/json");
        postRequest.setHeaders("Authorization","exchange " + exchangeToken);
        postRequest.setPaths(endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Login/loginStep2.json");
        body = body.replace("_user_", user).replace("_pass_", pass);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse loginStep3(String exchangeToken) {
        endpoint = "/api/v1/execute/session.login.step3";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization","exchange " + exchangeToken);
        postRequest.setPaths(endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Login/loginStep3.json");

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }
    private PandoraResponse utilLoggerReadTransactions(){
        endpoint = "/api/v1/execute/util.logger.read.transactions";
        postRequest.setHeaders("Content-Type","application/json");
        postRequest.setHeaders("Authorization","");
        postRequest.setPaths(endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Login/utilLoggerReadTransaccion.json");

        body = body.replace("_idSession_", idSession);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }
    private PandoraResponse loginStep4(String exchangeToken){
        endpoint = "/api/v1/execute/session.login.step4";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization","exchange " + exchangeToken);
        postRequest.setPaths(endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Login/loginStep4.json");

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }
    private PandoraResponse getUserData(){
        endpoint = "/api/v1/execute/preferences.userData.modify.pre";
        postRequest.setHeaders("Content-Type","application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Compartidos/requestVacio.json");

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }
    private PandoraResponse loginStep3(String exchangeToken, String idEnvironment) {
        endpoint = "/api/v1/execute/session.login.step3";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", "exchange " + exchangeToken);
        postRequest.setPaths(endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Login/loginStep3Empresas.json");

        body = body.replace("_environment_", idEnvironment);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    @Step("Validar servicio /FraudAnalysis/service/analyze con el id_transaction," +
            "metodoValidacionFP y dispositivo (not null)")
    public void validarServicioFraudAnalysis(){

        PandoraResponse response;

        String service, dataUnzipped;


        response = utilLoggerReadTransactions();
        validarResponse.validateResponses(response);


        service = JsonPath.parse(response.getBody().toPrettyString()).
                read("$.data.transactions[?(@.idTransaction=='"+ idTransaccion +"' && @.service=='/FraudAnalysis/service/analyze')].service").
                toString();

        dataUnzipped = JsonPath.parse(response.getBody().toPrettyString()).
                read("$.data.transactions[?(@.idTransaction=='"+ idTransaccion +"' && @.service=='/FraudAnalysis/service/analyze')].dataUnzipped").
                toString();

        response = utilLoggerReadTransactions();
        validarResponse.validateResponses(response);


        service = JsonPath.parse(response.getBody().toPrettyString()).
                read("$.data.transactions[?(@.idTransaction=='"+ idTransaccion +"' && @.service=='/FraudAnalysis/service/analyze')].service").
                toString();

        dataUnzipped = JsonPath.parse(response.getBody().toPrettyString()).
                read("$.data.transactions[?(@.idTransaction=='"+ idTransaccion +"' && @.service=='/FraudAnalysis/service/analyze')].dataUnzipped").
                toString();

        response = utilLoggerReadTransactions();
        validarResponse.validateResponses(response);


        service = JsonPath.parse(response.getBody().toPrettyString()).
                read("$.data.transactions[?(@.idTransaction=='"+ idTransaccion +"' && @.service=='/FraudAnalysis/service/analyze')].service").
                toString();

        dataUnzipped = JsonPath.parse(response.getBody().toPrettyString()).
                read("$.data.transactions[?(@.idTransaction=='"+ idTransaccion +"' && @.service=='/FraudAnalysis/service/analyze')].dataUnzipped").
                toString();

        utils.deserializarParametroAJson(dataUnzipped);





    }
}
