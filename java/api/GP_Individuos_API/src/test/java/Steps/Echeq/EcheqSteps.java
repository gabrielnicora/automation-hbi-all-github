package Steps.Echeq;

import Config.Settings;
import Config.ValidarResponse;
import Steps.DobleFactor.DobleFactor;
import com.jayway.jsonpath.JsonPath;
import constants.RequestMethods;
import core.PandoraRequest;
import core.PandoraResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.GP_Individuos_API.Utils;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import steps.RequestSteps;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static Config.Settings.*;
import static Config.Settings.endpoint;

public class EcheqSteps {
    RequestSteps requestSteps = new RequestSteps();
    String host = System.getProperty("bankAPI");
    PandoraRequest postRequest = new PandoraRequest(host, RequestMethods.POST);
    Utils utils = new Utils();
    DobleFactor dobleFactor = new DobleFactor();
    ValidarResponse validarResponse = new ValidarResponse();
    //Acá se setea Individuos
    String hostIndividuos = System.getProperty("bankAPI");
    PandoraRequest postRequestIndividuos = new PandoraRequest(hostIndividuos, RequestMethods.POST);
    //variablea a utilizar
    PandoraResponse response;
    String razonSocial;
    String idTransaccion;


    //Mapeo de servicios para realizar el proceso
    private PandoraResponse echeqConsultSocialReason(String cuit){
        Settings.endpoint = "/api/v1/execute/echeq.consultSocialReason";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Echeq/echeqConsultSocialReason.json");

        body = body.replace("_document_",cuit);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse emitEcheq(String cuit, String fecha, String caracter,String code,String echeqType){
        Settings.endpoint = "/api/v1/execute/echeq.emit";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Echeq/echeqEmit.json");

        body = body.replace("_cuitBeneficiario_",cuit)
                    .replace("_socialReason_", razonSocial)
                    .replace("_idProduct_", Settings.idCuentaOrigen)
                    .replace("_paymentDate_",fecha)
                    .replace("_character_",caracter)
                    .replace("_code_", code)
                    .replace("_echeqType_",echeqType);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse echeqCorporateList(String echeqNumber,int page){
        Settings.endpoint = "/api/v1/execute/echeqCorporate.list";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Echeq/echeqCorporateList.json");

        body = body.replace("_echeqNumber_",echeqNumber)
                    .replace("_idProduct_",Settings.idCuentaOrigen)
                    .replace("_page_",String.valueOf(page));

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse generarAnulacionDeEcheq(String code){
        Settings.endpoint = "/api/v1/execute/echeqCorporate.management";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Echeq/echeqCorporateManagement.json");

        body = body.replace("_checkId_",iDEcheq)
                .replace("_nroEcheq_", nroDeEcheq)
                .replace("_code_", code);


        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse echeqCorporateManagementCancel(String checkId,String nroEcheq, String code){
        Settings.endpoint = "/api/v1/execute/echeqCorporate.management";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Echeq/echeqCorporateManangementCancel.json");

        body = body.replace("_chekID_",checkId)
                    .replace("_nroEcheq_",nroEcheq)
                    .replace("_idProduct_",Settings.idCuentaOrigen)
                    .replace("_code_",code);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

//    private PandoraResponse echeqTodos(String estado){
//        Settings.endpoint = "/api/v1/execute/list.echeck";
//        postRequestIndividuos.setHeaders("Content-Type","application/json");
//        postRequestIndividuos.setHeaders("Authorization", Settings.bearerToken);
//        postRequestIndividuos.setPaths(Settings.endpoint);
//
//        String  body = utils.leerContenidoArchivo("./Requests/Echeq/listEcheq.json");
//
//        body = body.replace("_statement_", estado)
//                .replace("_page_",paginated);
//        postRequestIndividuos.setBody(utils.createBodyFromString(body));
//
//        return requestSteps.executeRequest(postRequestIndividuos);
//    }
    private PandoraResponse echeqTodos(String estado, String page, String operationType){
        Settings.endpoint = "/api/v1/execute/list.echeck";
        postRequestIndividuos.setHeaders("Content-Type","application/json");
        postRequestIndividuos.setHeaders("Authorization", Settings.bearerToken);
        postRequestIndividuos.setPaths(Settings.endpoint);

        String  body = utils.leerContenidoArchivo("./Requests/Echeq/listEcheq.json");

        body = body.replace("_statement_", estado)
                .replace("_page_",page)
                .replace("_operationType_",operationType);
        postRequestIndividuos.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequestIndividuos);
    }
    private PandoraResponse echeqActivos(){
        Settings.endpoint = "/api/v1/execute/list.echeck";
        postRequestIndividuos.setHeaders("Content-Type","application/json");
        postRequestIndividuos.setHeaders("Authorization", Settings.bearerToken);
        postRequestIndividuos.setPaths(Settings.endpoint);

        String  body = utils.leerContenidoArchivo("./Requests/Echeq/listEcheqPorNroDeEcheq.json");
        body = body.replace("_echeqNumber_",nroDeEcheq);

        postRequestIndividuos.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequestIndividuos);
    }

    private PandoraResponse ticket(){
        Settings.endpoint = "/api/v1/execute/ticket.get";
        postRequest.setHeaders("Content-Type","application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String  body = utils.leerContenidoArchivo("./Requests/Transferencias/ticket.json");
        body = body.replace("_idTransaccion_", idTransaccion);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
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

    private PandoraResponse loginStep4(String exchangeToken){
        endpoint = "/api/v1/execute/session.login.step4";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization","exchange " + exchangeToken);
        postRequest.setPaths(endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Login/loginStep4.json");

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }
    private PandoraResponse executeUtilCode(){
        Settings.endpoint = "/api/v1/execute/util.codes";
        postRequest.setHeaders("Content-Type","application/json");
        postRequest.setHeaders("Authorization",Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Compartidos/requestVacio.json");

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse sendTokenSms(){
        Settings.endpoint = "/api/v1/execute/secondFactor.otp.sms.sendToken";
        postRequest.setHeaders("Content-Type","application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Compartidos/requestVacio.json");

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse getWidgetsAccount(){
        endpoint = "/api/v1/execute/widgets.accounts";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization",  Settings.bearerToken);
        postRequest.setPaths(endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Compartidos/requestVacio.json");

        postRequest.setBody(utils.createBodyFromString(body));

        return  requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse generaAceptacionDeEcheq(String code) {
        endpoint = "/api/v1/execute/echeq.operation.accept";
        postRequestIndividuos.setHeaders("Content-Type", "application/json");
        postRequestIndividuos.setHeaders("Authorization", Settings.bearerToken);
        postRequestIndividuos.setPaths(endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Echeq/echeqOperationAccept.json");

        body = body.replace("_checkId_",iDEcheq)
                .replace("_nroEcheq_", nroDeEcheq)
                .replace("_code_", code);


        postRequestIndividuos.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequestIndividuos);
    }

    private PandoraResponse generaEndosoDeEcheq(String code, String cuit) {
        endpoint = "/api/v1/execute/echeq.operation.endorse";
        postRequestIndividuos.setHeaders("Content-Type", "application/json");
        postRequestIndividuos.setHeaders("Authorization", Settings.bearerToken);
        postRequestIndividuos.setPaths(endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Echeq/echeqOperationEndorse.json");

        body = body.replace("_checkId_",iDEcheq)
                .replace("_nroEcheq_", nroDeEcheq)
                .replace("_code_", code)
                .replace("_documentNumber_",cuit);


        postRequestIndividuos.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequestIndividuos);
    }

    private PandoraResponse generaEcheqRescatado(String code) {
        endpoint = "/api/v1/execute/echeq.operation.rescue";
        postRequestIndividuos.setHeaders("Content-Type", "application/json");
        postRequestIndividuos.setHeaders("Authorization", Settings.bearerToken);
        postRequestIndividuos.setPaths(endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Echeq/echeqOperationRepudiate.json");

        body = body.replace("_checkId_",iDEcheq)
                .replace("_nroEcheq_", nroDeEcheq)
                .replace("_code_", code);

        postRequestIndividuos.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequestIndividuos);
    }

    private PandoraResponse generaEcheqCustodiado(String code) {
        endpoint = "/api/v1/execute/echeq.operation.keep";
        postRequestIndividuos.setHeaders("Content-Type", "application/json");
        postRequestIndividuos.setHeaders("Authorization", Settings.bearerToken);
        postRequestIndividuos.setPaths(endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Echeq/echeqOperationKeep.json");

        body = body.replace("_checkId_",iDEcheq)
                .replace("_nroEcheq_", nroDeEcheq)
                .replace("_idProduct_", idCuentaOrigen)
                .replace("_code_", code);


        postRequestIndividuos.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequestIndividuos);
    }

    private PandoraResponse generaEcheqRepudiado(String code) {
        endpoint = "/api/v1/execute/echeq.operation.repudiate";
        postRequestIndividuos.setHeaders("Content-Type", "application/json");
        postRequestIndividuos.setHeaders("Authorization", Settings.bearerToken);
        postRequestIndividuos.setPaths(endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Echeq/echeqOperationRepudiate.json");

        body = body.replace("_checkId_",iDEcheq)
                .replace("_nroEcheq_", nroDeEcheq)
                .replace("_code_", code);

        postRequestIndividuos.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequestIndividuos);
    }

    private PandoraResponse generaDevolucionDeEcheq(String dobleFactor) {
        endpoint = "/api/v1/execute/echeq.operation.requestReturnV2";
        postRequestIndividuos.setHeaders("Content-Type", "application/json");
        postRequestIndividuos.setHeaders("Authorization", Settings.bearerToken);
        postRequestIndividuos.setPaths(endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Echeq/" +
                "echeqOperationRequestReturn.json");

        body = body.replace("_checkId_",iDEcheq)
                .replace("_nroEcheq_", nroDeEcheq)
                .replace("_code_", dobleFactor);



        postRequestIndividuos.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequestIndividuos);
    }

    private PandoraResponse generaEcheqDepositado(String code) {
        endpoint = "/api/v1/execute/echeq.operation.deposit";
        postRequestIndividuos.setHeaders("Content-Type", "application/json");
        postRequestIndividuos.setHeaders("Authorization", Settings.bearerToken);
        postRequestIndividuos.setPaths(endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Echeq/echeqOperationDeposit.json");

        body = body.replace("_checkId_",iDEcheq)
                .replace("_nroEcheq_", nroDeEcheq)
                .replace("_idProduct_", idCuentaOrigen)
                .replace("_code_", code);

        postRequestIndividuos.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequestIndividuos);
    }

    //metodos publicos para realizar la operatoria

    public void consultaRazonSocial(String cuit){
        int intentos = 0;

        //ejecutamos el servicio
        response = echeqConsultSocialReason(cuit);
        validarResponse.validateResponses(response);

        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                response = echeqConsultSocialReason(cuit);
                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ SE SINCRONIZO LOS SERVICIOS ECHEQ RASON SOCIAL ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO  SINCRONIZACION DE CUENTAS INTENTO 4 VECES",intentos==4);

        //obtenemos la razon social
        razonSocial = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.socialReason").toString());
        Allure.addAttachment("Razon Social", razonSocial);
        System.out.println("=========== RAZON SOCIAL : " + razonSocial);
    }
    public void emitirEcheq(String cuit,String caracter){
        int intentos=0;
        String fecha, code,dias,echeqType;
        dias = System.getProperty("dias");
        System.out.println("CANTIDAD DE DIAS A PARTIR DE HOY : "+ dias);

        if(dias.equals("0")){
            echeqType = "Normal";
        }else {
            echeqType = "Diferido";
        }
        //obtenemos la fecha de hoy
        fecha= utils.sumarFechaHoyPorDias(Integer.parseInt(dias));

        //obtenemos el segundo factor
        code = dobleFactor.getSecondFactor();
        //ejecutamos el servicio de emitir echeq
       response = emitEcheq(cuit,fecha,caracter,code,echeqType);
        //validamos el response
        validarResponse.validateResponses(response);

        while(intentos < 4){
            if(flagServicioReejecucion){
                //obtenemos el segundo factor
                code = dobleFactor.getSecondFactor();
                //ejecutamos el servicio de emitir echeq
               response = emitEcheq(cuit,fecha,caracter,code,echeqType);
                //validamos el response
                validarResponse.validateResponses(response);
            }else{
                System.out.println("================== SE REALIZO CORRETAMENTE EL ECHEQ ======================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO DE EMITIR ECHEQ SE INTENTO 4 VECES",intentos==4);
    }

    public void anularEcheq(String nroEcheq){
        int intentos=0;
        int page=1;
        String checkID, code;

        //consultamos el echeq a anular
        response = echeqCorporateList(nroEcheq,page);
        //validamos el response
        validarResponse.validateResponses(response);

        //obtenemos el id del echeq
        checkID = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.paginatedData..paginatedList.elementList..checkId").toString());
        //obtenemos un doble factor
        code = dobleFactor.getSecondFactor();
        //ejecutamos la anulacion del echeq
        response = echeqCorporateManagementCancel(checkID, nroEcheq,code);
        //validamos el response
        validarResponse.validateResponses(response);
        while(intentos < 4){
            if(flagServicioReejecucion){
                //obtenemos el segundo factor
                code = dobleFactor.getSecondFactor();
                //ejecutamos la anulacion del echeq
                response = echeqCorporateManagementCancel(checkID, nroEcheq,code);
                //validamos el response
                validarResponse.validateResponses(response);
            }else{
                System.out.println("================== SE REALIZO CORRETAMENTE LA ANULACION ======================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO DE ANULAR ECHEQ SE INTENTO 4 VECES",intentos==4);
    }

//    public String getSecondFactorValidate(){
//        List<Map<String,String>> listaCodigos;
//        int cant2FActualizada, cant2FActual=0;
//        int j = 1;
//        String codigo = null;
//        String regex = ".*" + Settings.telefono + "$";
//        PandoraResponse utilCode;
//
//        utils.fixedWait(3000);
//
//        utilCode = executeUtilCode();
//
//        cant2FActual = JsonPath.parse(utilCode.getBody().toPrettyString()).read("$.data.codeList[?(@.origin =~ /"+regex+"/)]",List.class).size();
//        //cant2FActual =  listaCodigos.size();
//        //restAssuredApi.getListFromJson(utilCode,"data.codeList.findAll {it.origin == '" + loginAPI.origin + "'}").size();
//
//        System.out.println("======== ACUTAL :" +  cant2FActual);
//
//        while (j<4){
//            sendTokenSms();
//            for (int i = 1; i <= 4; i++) {
//
//                utils.fixedWait(2500);
//
//                utilCode= executeUtilCode();
//                //cant2FActualizada = JsonPath.parse(utilCode.getBody().toPrettyString()).read("$.data.codeList[?(@.origin == '"+Settings.telefono+"')]",List.class).size();
//                cant2FActualizada = JsonPath.parse(utilCode.getBody().toPrettyString()).read("$.data.codeList[?(@.origin =~ /"+regex+"/)]",List.class).size();
//                //cant2FActualizada = listaCodigos.size();
//                //restAssuredApi.getListFromJson(utilCode,"data.codeList.findAll {it.origin == '"+ loginAPI.origin +"'}").size();
//
//                System.out.println("===SEGUNDOS PARA ACTUALIZAR LISTCODE : "+ i*2 +" SEGUNDOS");
//                System.out.println("============= CANTIDAD DE CODIGOS ACTUALIZADA : " + cant2FActualizada);
//
//                if (cant2FActual < cant2FActualizada) {
//
//                    listaCodigos = JsonPath.parse(utilCode.getBody().toPrettyString()).read("$.data.codeList[?(@.origin =~ /"+regex+"/)]");
//                    codigo = listaCodigos.get(listaCodigos.size()-1).get("code");
//                    //restAssuredApi.getStringFromJson(utilCode,"data.codeList.findAll {it.origin == '"+ loginAPI.origin +"'}.last().code");
//                    System.out.println("=============== CODIGO ANTERIOR UTILIZADO : "+ Settings.dobleFactor +"===================================");
//
//                    if(codigo.equals(Settings.dobleFactor)){
//                        System.out.println("======== SE REPITE EL CODIGO SE VUELVE ACTUALIZAR EL UTILCODE ==============");
//                    }else{
//                        System.out.println("====================== CODIGO LIMPIO NO REPETIDO ==========================");
//                        Settings.dobleFactor =  codigo;
//                        break;
//                    }
//                }
//            } if(codigo == null){
//                System.out.println("No se actualizo los codigos volver a pedir token");
//                cant2FActual = 0;//restAssuredApi.getListFromJson(secondFactor(),"data.codeList.findAll {it.origin == '"+ origin +"'}").size();
//                System.out.println("================= EL NUEVO ACTUAL   "+ cant2FActual);
//                j++;
//            }else {
//                System.out.println("cantidad send token enviados : " + j);
//                break;
//            }
//        }
//
//        System.out.println("=============  CODIGO : " + codigo);
//        return codigo;
//    }

    @Step("Login API para obtener BearerToken - usuario : {user} - password : {password}")
    public void loginUsuario(String user,String password, String ambiente) {
        String exchangeToken, bearerToken;

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

        Settings.cuitEmisor = JsonPath.parse(response.getBody().toString()).
                read("$.data.userCuitCuil").toString();

        System.out.println("============ Se capturó CUIT EMISOR =========" + cuitEmisor);

        bearerToken = JsonPath.parse(response.getBody().toString()).
                read("$.data._accessToken").toString();

        Settings.bearerToken = bearerToken;

        Settings.bearerToken = "Bearer "+Settings.bearerToken;

        //obtener el telefono del usuario
        //response = getUserData();
        //validarResponse.validateResponses(response);
        String telefonoBlureado = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.phoneNumber").toString();

        Settings.telefono =telefonoBlureado.substring(telefonoBlureado.length()-3);
        System.out.println("NRO DE TELEFONO "+Settings.telefono);
    }

    @Step("Valida Echeq en estado -Todos- ")
    public void validaEcheqs(String estado, String operationType){
        //Realiza la búsqueda

        PandoraResponse response;
        int intentos = 0;

        response = echeqTodos(estado,paginated,operationType);

        validarResponse.validateResponses(response);
        while (intentos<3){
            if(Settings.flagServicioReejecucion){

                response = echeqTodos(estado,paginated,operationType);

                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);

        //Valida cantidad cantidad de transferencias en el array

        List<LinkedHashMap<String,Object>> paginado = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.paginatedData.paginatedList.elementList", List.class);

        for(LinkedHashMap<String,Object> pag : paginado){
            LinkedHashMap<String,String> beneficiario;

            if(pag.get("checkNumber").equals(nroDeEcheq)){
                System.out.println("se encontro el echeq " + pag.get("checkNumber") + " en el estado " +  estado);
                String valueNroEcheq = pag.get("checkNumber").toString();
                beneficiario = (LinkedHashMap<String,String>) pag.get("beneficiary");
                String valueEmisor = beneficiario.get("businessName").toString();
                String valueEstadoEcheq = pag.get("statement").toString();
                String valueFecha = pag.get("paymentDate").toString();
                Allure.addAttachment("Nro de Echeq", String.valueOf(valueNroEcheq));
                Allure.addAttachment("Nombre Emisor", valueEmisor);
                Allure.addAttachment("Estado del Echeq", valueEstadoEcheq);
                Allure.addAttachment("Fecha de emisión", valueFecha);
                break;
            }
        }
        Assert.assertTrue("SIN ELEMENTOS EN EL ESTADO " + estado + " BUSCADO ", paginado.size()!=0);
    }

//    @Step("Generación de Echeq")
//    public void getEcheqActivo(String cuit,String caracter, String dias, String cuenta){
//        int intentos=0;
//        String fecha, code,echeqType;
//
//        System.out.println("CANTIDAD DE DIAS A PARTIR DE HOY : "+ dias);
//
//        if(dias.equals("0")){
//            echeqType = "Normal";
//        }else {
//            echeqType = "Diferido";
//        }
//        //obtenemos la fecha de hoy
//        fecha= utils.sumarFechaHoyPorDias(Integer.parseInt(dias));
//
//        //Se obtiene la Razon Social
//
//        consultaRazonSocial(cuit);
//
//        //Mapear el svc Widget Account y obtener cta origen
//
//        response = getWidgetsAccount();
//
//        Settings.idCuentaOrigen = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.accounts[?(@.shortLabel == '"+cuenta+"')].idProduct").toString());
//        Allure.addAttachment("idCuentaOrigen",Settings.idCuentaOrigen);
//
//        //obtenemos el segundo factor
//        code = getSecondFactorValidate();
//        //ejecutamos el servicio de emitir echeq
//        response = emitEcheq(cuit,fecha,caracter,code,echeqType);
//        //validamos el response
//        validarResponse.validateResponses(response);
//
//        while(intentos < 4){
//            if(flagServicioReejecucion){
//                //obtenemos el segundo factor
//                code = getSecondFactorValidate();
//                //ejecutamos el servicio de emitir echeq
//                response = emitEcheq(cuit,fecha,caracter,code,echeqType);
//                //validamos el response
//                validarResponse.validateResponses(response);
//            }else{
//                System.out.println("================== SE REALIZO CORRETAMENTE EL ECHEQ ======================");
//                break;
//            }
//            intentos++;
//        }
//        Assert.assertFalse("FALLO SERVICIO DE EMITIR ECHEQ SE INTENTO 4 VECES",intentos==4);
//
//        //Obtener el id de la transacción
//
//        idTransaccion = JsonPath.parse(response.getBody().toPrettyString()).
//                read("$.idTransaction").toString();
//
//        //Genera el Ticket
//
//        response = ticket();
//
//        //Se obtiene el nro del echeq
//
//        Settings.nroDeEcheq = JsonPath.parse(response.getBody().toPrettyString()).
//                read("$.data._ticket.data.ticket.echeqsNumbers").
//                toString();
//        System.out.println("============ Se generó Nro de Echeq =========" + Settings.nroDeEcheq);
//
//    }

    @Step("Genera la aceptación del Echeq")
    public void generacionDeAceptaciondeEcheq(String estado, String fechaEcheq){
        //Realiza la búsqueda

       String doblefactor;

        PandoraResponse response;
        int intentos = 0;

        String fecha = fechaEcheq+"T00:00:00.000-03";

        System.out.println("FECHA ============ " + fecha);
        //Aqui iteramos el servicio por pagina para la busqueda del idEche y numero de Echeq con las condiciones necesarias para aceptarlo
        for(int i =1;i<=10;i++){
            response = echeqTodos(estado,String.valueOf(i), "");
            validarResponse.validateResponses(response);
            while (intentos<3){
                if(Settings.flagServicioReejecucion){

                    response = echeqTodos(estado,String.valueOf(i), "");

                    validarResponse.validateResponses(response);
                }else{
                    Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                    break;
                }
                intentos++;
            }
            Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);
            iDEcheq = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("  $.data.paginatedData.paginatedList.elementList[?(@.statement=='"+estado+"' && @.beneficiary.identificationNumber=='"+cuil+"' && @.paymentDate=='"+fecha+"') ].checkId", List.class));
            nroDeEcheq = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString())
                    .read("  $.data.paginatedData.paginatedList.elementList[?(@.statement=='"+estado+"' && @.beneficiary.identificationNumber=='"+cuil+"' && @.paymentDate=='"+fecha+"') ].checkNumber", List.class));
            if(iDEcheq.equals("null")){
                System.out.println("NO ENCONTRADO EN LA PAGINA NUMERO " + i + " BUSCAR EN LA SIGUIENTE");
            }else{
                System.out.println("SE ENCONTRO EL ECHEQ EN LA PAGINA " + i + " ID ECHEQ ==== " + iDEcheq);
                break;
            }

        }

        Assertions.assertEquals(false, iDEcheq.equals("null"));
        //Genera la aceptación del echeq

        //Obtenemos el doble factor

        doblefactor = dobleFactor.getSecondFactor();

        response = generaAceptacionDeEcheq(doblefactor);

        validarResponse.validateResponses(response);
        while (intentos<3){
            if(Settings.flagServicioReejecucion){

                doblefactor = dobleFactor.getSecondFactor();
                response = generaAceptacionDeEcheq(doblefactor);

                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);

        System.out.println("============ SE MUESTRA NRO DE ECHEQ ACEPTADO =========" + nroDeEcheq);
    }

    @Step("Genera la aceptación del Echeq Endosado Previamente")
    public void aceptarEcheqEndosado(String estado){
        //Realiza la búsqueda

        String doblefactor;

        PandoraResponse response;
        int intentos = 0;
        //Genera la aceptación del echeq

        //Obtenemos el doble factor
        doblefactor = dobleFactor.getSecondFactor();

        response = generaAceptacionDeEcheq(doblefactor);

        validarResponse.validateResponses(response);
        while (intentos<3){
            if(Settings.flagServicioReejecucion){

                doblefactor = dobleFactor.getSecondFactor();
                response = generaAceptacionDeEcheq(doblefactor);

                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);

        System.out.println("============ SE MUESTRA NRO DE ECHEQ ACEPTADO =========" + nroDeEcheq);
    }

//    @Step("Valida estado del Echeq")
//    public void validaEstadosEcheq(String estado){
//        //Realiza la búsqueda
//
//
//        PandoraResponse response;
//        int intentos = 0;
//
//        response = echeqActivos();
//
//        validarResponse.validateResponses(response);
//        while (intentos<3){
//            if(Settings.flagServicioReejecucion){
//
//                response = echeqActivos();
//                validarResponse.validateResponses(response);
//            }else{
//                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
//                break;
//            }
//            intentos++;
//        }
//        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);
//
//        //Valida cantidad cantidad de transferencias en el array
//
//
//        String valueEstadoEcheq = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.paginatedData.paginatedList.elementList[0].statement");
//
//        Allure.addAttachment("Estado del Echeq", valueEstadoEcheq);
//
//        Assert.assertTrue("Echeq en estado -Activo-", valueEstadoEcheq.equals("ACTIVO"));
//    }

    @Step("Genera el endoso del Echeq")
    public void generacionDeEndosodeEcheq(String cuit){

        //Genera el endoso del echeq

        String doblefactor;

        PandoraResponse response;
        int intentos = 0;

        //Obtenemos el doble factor
        doblefactor = dobleFactor.getSecondFactor();

        response = generaEndosoDeEcheq(doblefactor, cuit);

        validarResponse.validateResponses(response);
        while (intentos<3){
            if(Settings.flagServicioReejecucion){

                doblefactor = dobleFactor.getSecondFactor();
                response = generaEndosoDeEcheq(doblefactor, cuit);

                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);

        mensaje = JsonPath.parse(response.getBody().toPrettyString()).
                read("$.message").
                toString();
        System.out.println("=================Resultado del Endoso==================" + mensaje);
    }

    @Step("Genera el repudio del echeq")
    public void generacionRepudioDeEcheq(String estado){

        //Realiza la búsqueda

        String doblefactor;

        PandoraResponse response;
        int intentos = 0;

        String fecha = utils.fechaHoySinHora()+"T00:00:00.000-03";

        System.out.println("FECHA ============ " + fecha);
        //Aqui iteramos el servicio por pagina para la busqueda del idEche y numero de Echeq con las condiciones necesarias para aceptarlo
        for(int i =1;i<=10;i++){
            response = echeqTodos(estado,String.valueOf(i), "");
            validarResponse.validateResponses(response);
            while (intentos<3){
                if(Settings.flagServicioReejecucion){

                    response = echeqTodos(estado,String.valueOf(i), "");

                    validarResponse.validateResponses(response);
                }else{
                    Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                    break;
                }
                intentos++;
            }
            Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);
            iDEcheq = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("  $.data.paginatedData.paginatedList.elementList[?(@.statement=='"+estado+"' && @.beneficiary.identificationNumber=='"+cuil+"' && @.paymentDate=='"+fecha+"') ].checkId", List.class));
            nroDeEcheq = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString())
                    .read("  $.data.paginatedData.paginatedList.elementList[?(@.statement=='"+estado+"' && @.beneficiary.identificationNumber=='"+cuil+"' && @.paymentDate=='"+fecha+"') ].checkNumber", List.class));
            if(iDEcheq.equals("null")){
                System.out.println("NO ENCONTRADO EN LA PAGINA NUMERO " + i + " BUSCAR EN LA SIGUIENTE");
            }else{
                System.out.println("SE ENCONTRO EL ECHEQ EN LA PAGINA " + i + " ID ECHEQ ==== " + iDEcheq);
                break;
            }

        }
        //Obtenemos el doble factor

        doblefactor = dobleFactor.getSecondFactor();

        response = generaEcheqRepudiado(doblefactor);

        validarResponse.validateResponses(response);
        while (intentos<3){
            if(Settings.flagServicioReejecucion){

                doblefactor = dobleFactor.getSecondFactor();
                response = generaEcheqRepudiado(doblefactor);

                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);

        mensaje = JsonPath.parse(response.getBody().toPrettyString()).
                read("$.message").
                toString();
        System.out.println("=================Resultado del Echeq Repudio==================" + mensaje);
    }

//    @Step("Valida Echeq en estado -Activo Pendiente- ")
//    public void validaEcheqsActivoPendientes(){
//
//        //Realiza la búsqueda
//
//
//        PandoraResponse response;
//        int intentos = 0;
//
//        response = echeqActivos();
//
//        validarResponse.validateResponses(response);
//        while (intentos<3){
//            if(Settings.flagServicioReejecucion){
//
//                response = echeqActivos();
//                validarResponse.validateResponses(response);
//            }else{
//                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
//                break;
//            }
//            intentos++;
//        }
//        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);
//
//        //Valida cantidad cantidad de transferencias en el array
//
//        String valueEstadoEcheq = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.paginatedData.paginatedList.elementList[0].statement");
//
//        Allure.addAttachment("Estado del Echeq", valueEstadoEcheq);
//
//        Assert.assertTrue("Echeq en estado -Activo Pendiente-", valueEstadoEcheq.equals("ACTIVO-PENDIENTE"));
//    }

    @Step("Genera el custodio del echeq")
    public void generacionCustodioDeEcheq(String estado){

        //Realiza la búsqueda

        String doblefactor;

        PandoraResponse response;
        int intentos = 0;
        System.out.println("============ SE GENERÓ ID ECHEQ =========" + iDEcheq);

        //Genera el custodio del echeq

        //Obtenemos el doble factor

        doblefactor = dobleFactor.getSecondFactor();

        response = generaEcheqCustodiado(doblefactor);

        validarResponse.validateResponses(response);
        while (intentos<3){
            if(Settings.flagServicioReejecucion){

                doblefactor = dobleFactor.getSecondFactor();
                response = generaEcheqCustodiado(doblefactor);

                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);

        System.out.println("====== SE REALIZO CORRECTAMENTE LA CUSTODIA AL ECHEQ NUMERO " + nroDeEcheq);
    }

    @Step("Genera el rescate del echeq")
    public void generacionRescateDeEcheq(String estado){

        //Realiza la búsqueda

        String doblefactor;

        PandoraResponse response;
        int intentos = 0;

        //Genera el custodio del echeq

        //Obtenemos el doble factor

        doblefactor = dobleFactor.getSecondFactor();

        response = generaEcheqRescatado(doblefactor);

        validarResponse.validateResponses(response);
        while (intentos<3){
            if(Settings.flagServicioReejecucion){

                doblefactor = dobleFactor.getSecondFactor();
                response = generaEcheqRescatado(doblefactor);

                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);


        System.out.println("====== SE REALIZO CORRECTAMENTE EL RESCATE AL ECHEQ NUMERO " + nroDeEcheq);
    }


//    @Step("Genera la anulación del echeq")
//    public void generacionDeAnulacionDeEcheq(){
//
//        //Realiza la búsqueda del CheckId
//
//        int intentos=0;
//        int page=1;
//
//        String doblefactor;
//
//        PandoraResponse response;
//
//        response = echeqCorporateList(nroDeEcheq,page);
//
//        validarResponse.validateResponses(response);
//        while (intentos<3){
//            if(Settings.flagServicioReejecucion){
//
//                response = echeqCorporateList(nroDeEcheq,page);
//
//                validarResponse.validateResponses(response);
//            }else{
//                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
//                break;
//            }
//            intentos++;
//        }
//        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);
//
//        //Guarda el ID del Echeq
//
//        //obtenemos el id del echeq
//        iDEcheq = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody()
//                .toPrettyString()).read("$.data" +
//                ".paginatedData..paginatedList.elementList..checkId").toString());
//        System.out.println("============ SE GENERÓ ID ECHEQ =========" + iDEcheq);
//
//        //Genera el repudio del echeq
//
//        //obtenemos el segundo factor
//        doblefactor = getSecondFactorValidate();
//        //ejecutamos el servicio de emitir echeq
//        response = generarAnulacionDeEcheq(doblefactor);
//        //validamos el response
//        validarResponse.validateResponses(response);
//
//        while(intentos < 4){
//            if(flagServicioReejecucion){
//                //obtenemos el segundo factor
//                doblefactor = getSecondFactorValidate();
//                //ejecutamos el servicio de emitir echeq
//                response = generarAnulacionDeEcheq(doblefactor);
//                //validamos el response
//                validarResponse.validateResponses(response);
//            }else{
//                System.out.println("================== SE REALIZO CORRETAMENTE LA ANULACION ECHEQ ======================");
//                break;
//            }
//            intentos++;
//        }
//        Assert.assertFalse("FALLO SERVICIO DE EMITIR ECHEQ SE INTENTO 4 VECES",intentos==4);
//
//        mensaje = JsonPath.parse(response.getBody().toPrettyString()).
//                read("$.message").
//                toString();
//        System.out.println("=================Resultado del Echeq Repudio==================" + mensaje);
//    }

    @Step("Genera la devolución del Echeq")
    public void generacionDevolucionDelEcheq(){

        //Genera la devolución del echeq

        PandoraResponse response;
        String doblefactor;
        int intentos = 0;
        //obtenemos el segundo factor
        doblefactor = dobleFactor.getSecondFactor();

        response = generaDevolucionDeEcheq(doblefactor);

        validarResponse.validateResponses(response);
        while (intentos<3){
            if(Settings.flagServicioReejecucion){

                doblefactor = dobleFactor.getSecondFactor();

                response = generaDevolucionDeEcheq(doblefactor);

                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);

        mensaje = JsonPath.parse(response.getBody().toPrettyString()).
                read("$.message").
                toString();
        System.out.println("=================Resultado de la Devolución==================" + mensaje);
    }

    @Step("Genera el depósito del echeq")
    public void generacionDepositoDeEcheq(String estado){

        //Realiza la búsqueda

        String doblefactor;

        PandoraResponse response;
        int intentos = 0;

        System.out.println("============ SE GENERÓ ID ECHEQ =========" + iDEcheq);
        System.out.println("============ SE GENERÓ NRO ECHEQ =========" + nroDeEcheq);

        //Genera el depósito del echeq

        //Obtenemos el doble factor

        doblefactor = dobleFactor.getSecondFactor();

        response = generaEcheqDepositado(doblefactor);

        validarResponse.validateResponses(response);
        while (intentos<3){
            if(Settings.flagServicioReejecucion){

                doblefactor = dobleFactor.getSecondFactor();
                response = generaEcheqDepositado(doblefactor);

                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);

        mensaje = JsonPath.parse(response.getBody().toPrettyString()).
                read("$.message").
                toString();
        System.out.println("=================Resultado del Echeq Depósito==================" + mensaje);
    }

    @Step("Valida Echeq por default Emitidos")
    public void validaEcheqsPorDefaultEmitidos(String estado, String operationType){
        //Realiza la búsqueda

        PandoraResponse response;
        int intentos = 0;

        response = echeqTodos(estado,paginated,operationType);

        validarResponse.validateResponses(response);
        while (intentos<3){
            if(Settings.flagServicioReejecucion){

                response = echeqTodos(estado,paginated,operationType);

                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);

        String echeq = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.paginatedData.paginatedList.elementList[0].checkNumber").toString();
        String emision = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.paginatedData.paginatedList.elementList[0].originDate").toString();
        String pago = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.paginatedData.paginatedList.elementList[0].paymentDate").toString();
        String cuit = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.paginatedData.paginatedList.elementList[0].beneficiary.identificationNumber").toString();
        String emisor = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.paginatedData.paginatedList.elementList[0].beneficiary.businessName").toString();
        String importe = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.paginatedData.paginatedList.elementList[0].amount").toString();
        String concepto = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.paginatedData.paginatedList.elementList[0].concept").toString();
        String estadoEcheq = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.paginatedData.paginatedList.elementList[0].statement").toString();

        System.out.println("++++ Echeq número ++++ " + echeq);

        Assert.assertTrue("No se encontraron Echeq",
                echeq != null && !echeq.trim().isEmpty());
        Assert.assertTrue("No se encontró dato Emisión",
                emision != null && !emision.trim().isEmpty());
        Assert.assertTrue("No se encontró dato Pago",
                pago != null && !pago.trim().isEmpty());
        Assert.assertTrue("No se encontró dato Cuit",
                cuit != null && !cuit.trim().isEmpty());
        Assert.assertTrue("No se encontró dato Emisor",
                emisor != null && !emisor.trim().isEmpty());
        Assert.assertTrue("No se encontró dato Importe",
                importe != null && !importe.trim().isEmpty());
        Assert.assertTrue("No se encontró dato Concepto",
                concepto != null && !concepto.trim().isEmpty());
        Assert.assertTrue("No se encontró dato Estado echeq",
                estadoEcheq != null && !estadoEcheq.trim().isEmpty());
    }

    @Step("Valida Echeq por default Recibidos")
    public void validaEcheqsPorDefaultRecibidos(String estado, String operationType){
        //Realiza la búsqueda

        PandoraResponse response;
        int intentos = 0;

        response = echeqTodos(estado,paginated,operationType);

        validarResponse.validateResponses(response);
        while (intentos<3){
            if(Settings.flagServicioReejecucion){

                response = echeqTodos(estado,paginated,operationType);

                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);

        String echeq = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.paginatedData.paginatedList.elementList[0].checkNumber").toString();

        System.out.println("++++ Echeq número ++++ " + echeq);

        Assert.assertTrue("No se encontraron Echeq",
                echeq != null && !echeq.trim().isEmpty());
    }
}


