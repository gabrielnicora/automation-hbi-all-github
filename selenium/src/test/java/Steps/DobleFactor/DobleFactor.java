package Steps.DobleFactor;

import Config.Settings;
import Config.ValidarResponse;
import com.jayway.jsonpath.JsonPath;
import constants.RequestMethods;
import core.PandoraRequest;
import core.PandoraResponse;
import org.GP_Individuos_API.Utils;
import org.junit.Assert;
import steps.RequestSteps;

import java.util.List;
import java.util.Map;

public class DobleFactor {
    Utils utils = new Utils();
    RequestSteps requestSteps = new RequestSteps();
    String host = System.getProperty("bankAPI");
    PandoraRequest postRequest = new PandoraRequest(host, RequestMethods.POST);
    ValidarResponse validarResponse = new ValidarResponse();
    public static String codigoMailAux, codigoSMSAux = null;

    private PandoraResponse sendTokenSms(){
        Settings.endpoint = "/api/v1/execute/secondFactor.otp.sms.sendToken";
        postRequest.setHeaders("Content-Type","application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Compartidos/requestVacio.json");

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
    public String getSecondFactorValidate(){
        List<Map<String,String>> listaCodigos;
        int cant2FActualizada, cant2FActual=0;
        int j = 1;
        String codigo = null;
        String regex = ".*" + Settings.telefono + "$";
        PandoraResponse utilCode;

        utils.fixedWait(3000);

        utilCode = executeUtilCode();

        cant2FActual = JsonPath.parse(utilCode.getBody().toPrettyString()).read("$.data.codeList[?(@.origin =~ /"+regex+"/)]",List.class).size();
        //cant2FActual =  listaCodigos.size();
                //restAssuredApi.getListFromJson(utilCode,"data.codeList.findAll {it.origin == '" + loginAPI.origin + "'}").size();

        System.out.println("======== ACUTAL :" +  cant2FActual);

        while (j<4){
            sendTokenSms();
            for (int i = 1; i <= 4; i++) {

                utils.fixedWait(2500);

                utilCode= executeUtilCode();
                //cant2FActualizada = JsonPath.parse(utilCode.getBody().toPrettyString()).read("$.data.codeList[?(@.origin == '"+Settings.telefono+"')]",List.class).size();
                cant2FActualizada = JsonPath.parse(utilCode.getBody().toPrettyString()).read("$.data.codeList[?(@.origin =~ /"+regex+"/)]",List.class).size();
                //cant2FActualizada = listaCodigos.size();
                        //restAssuredApi.getListFromJson(utilCode,"data.codeList.findAll {it.origin == '"+ loginAPI.origin +"'}").size();

                System.out.println("===SEGUNDOS PARA ACTUALIZAR LISTCODE : "+ i*2 +" SEGUNDOS");
                System.out.println("============= CANTIDAD DE CODIGOS ACTUALIZADA : " + cant2FActualizada);

                if (cant2FActual < cant2FActualizada) {

                    listaCodigos = JsonPath.parse(utilCode.getBody().toPrettyString()).read("$.data.codeList[?(@.origin =~ /"+regex+"/)]");
                    codigo = listaCodigos.get(listaCodigos.size()-1).get("code");
                            //restAssuredApi.getStringFromJson(utilCode,"data.codeList.findAll {it.origin == '"+ loginAPI.origin +"'}.last().code");
                    System.out.println("=============== CODIGO ANTERIOR UTILIZADO : "+ Settings.dobleFactor +"===================================");

                    if(codigo.equals(Settings.dobleFactor)){
                        System.out.println("======== SE REPITE EL CODIGO SE VUELVE ACTUALIZAR EL UTILCODE ==============");
                    }else{
                        System.out.println("====================== CODIGO LIMPIO NO REPETIDO ==========================");
                        Settings.dobleFactor =  codigo;
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

    private PandoraResponse emailSendToken(){
        Settings.endpoint = "/api/v1/execute/secondFactor.otp.emailSendToken";
        postRequest.setHeaders("Content-Type","application/json");
        postRequest.setHeaders("Authorization",Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Compartidos/requestVacio.json");

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }

    //OBTIENE CODIGO 2FA -UTILIZA MAIL DEL USUARIO LOGUEADO - SE UTILIZA PARA CAMBIO DE TELEFONO - EXTRAE CODE
    public String getSecondFactorOriginMail(){
        List<Map<String,String>> listaCodigos;
        int cant2FActualizada,cant2FActual = 0;
        int j = 1;
        String codigoMail = null;
        PandoraResponse utilCode;

        utilCode = executeUtilCode();
        cant2FActual = JsonPath.parse(utilCode.getBody().toPrettyString()).read("$.data.codeList[?(@.origin == '"+Settings.mail+"')]",List.class).size();

        System.out.println("======== ACUTAL :" +  cant2FActual);

        while (j<4) {
            emailSendToken();
            for (int i = 1; i <= 4; i++) {
                utils.fixedWait(2500);

                utilCode= executeUtilCode();
                cant2FActualizada = JsonPath.parse(utilCode.getBody().toPrettyString()).read("$.data.codeList[?(@.origin == '"+Settings.mail+"')]",List.class).size();

                System.out.println("===SEGUNDOS PARA ACTUALIZAR LISTCODE : " + i * 2 + " SEGUNDOS");
                System.out.println("============= ACTUALIZDAA : " + cant2FActualizada);

                if (cant2FActual < cant2FActualizada) {

                    listaCodigos = JsonPath.parse(utilCode.getBody().toPrettyString()).read("$.data.codeList[?(@.origin == '"+Settings.mail+"')]",List.class);
                    codigoMail = listaCodigos.get(listaCodigos.size()-1).get("code");
                    System.out.println("=============== CODIGO ANTERIOR UTILIZADO : " + codigoMailAux + "===================================");

                    if (codigoMail.equals(codigoMailAux)) {
                        System.out.println("======== SE REPITE EL CODIGO SE VUELVE ACTUALIZAR EL UTILCODE ==============");
                    } else {
                        System.out.println("====================== CODIGO LIMPIO NO REPETIDO ==========================");
                        codigoMailAux = codigoMail;
                        break;
                    }
                }
            }
            if (codigoMail == null) {
                System.out.println("No se actualizo los codigos volver a pedir token");
                cant2FActual = 0;
                System.out.println("================= EL NUEVO ACTUAL   " + cant2FActual);
                j++;
            } else {
                System.out.println("cantidad send token enviados : " + j);
                break;
            }
        }

        System.out.println("=============  CODIGO : " + codigoMail);
        return codigoMail;
    }

    //OBTIENE CODIGO 2FA -UTILIZA MAIL DEL USUARIO LOGUEADO - SE UTILIZA PARA CAMBIO DE TELEFONO - EXTRAE CODE
    public String getSecondFactorConfirmation(String nuevoTelefono){
        List<Map<String,String>> listaCodigos;
        int cant2FActualizada,cant2FActual = 0;
        int j = 1;
        String codigoConfirmacion = null;
        PandoraResponse utilCode;

        utils.fixedWait(3000);

        utilCode = executeUtilCode();
        cant2FActual = JsonPath.parse(utilCode.getBody().toPrettyString()).read("$.data.codeList[?(@.origin == '"+nuevoTelefono+"')]",List.class).size();

        System.out.println("======== ACUTAL :" +  cant2FActual);

        while (j<4) {
            sendTokenSms();
            for (int i = 1; i <= 4; i++) {
                utils.fixedWait(2500);
                utilCode= executeUtilCode();
                cant2FActualizada = JsonPath.parse(utilCode.getBody().toPrettyString()).read("$.data.codeList[?(@.origin == '"+nuevoTelefono+"')]",List.class).size();

                System.out.println("===SEGUNDOS PARA ACTUALIZAR LISTCODE : " + i * 2 + " SEGUNDOS");
                System.out.println("============= ACTUALIZDAA : " + cant2FActualizada);

                if (cant2FActual < cant2FActualizada) {

                    listaCodigos = JsonPath.parse(utilCode.getBody().toPrettyString()).read("$.data.codeList[?(@.origin == '"+nuevoTelefono+"')]",List.class);
                    codigoConfirmacion = listaCodigos.get(listaCodigos.size()-1).get("codeNoFormat").split(": ")[1];
                    System.out.println("=============== CODIGO ANTERIOR UTILIZADO : " + codigoSMSAux + "===================================");

                    if(codigoConfirmacion.contains("-")){
                        System.out.println("========ES UN CODIGO DE VERIFICACION VALIDO: "+codigoConfirmacion);

                        if(codigoConfirmacion.equals(codigoSMSAux)){
                            System.out.println("======== SE REPITE EL CODIGO SE VUELVE ACTUALIZAR EL UTILCODE ==============");
                        }
                        else {
                            System.out.println("====================== CODIGO LIMPIO NO REPETIDO ==========================");
                            codigoSMSAux = codigoConfirmacion;
                            break;
                        }
                    } else {
                        System.out.println("==========*CODIGO INVALIDO: "+codigoConfirmacion);
                    }
                }
            }
            if (codigoConfirmacion == null) {
                System.out.println("No se actualizo los codigos volver a pedir token");
                cant2FActual = 0;
                System.out.println("================= EL NUEVO ACTUAL   " + cant2FActual);
                j++;
            } else {
                System.out.println("cantidad send token enviados : " + j);
                break;
            }
        }

        System.out.println("=============  CODIGO : " + codigoConfirmacion);
        return codigoConfirmacion;
    }

    public String getSecondFactor(){
        String segundoFactor;
        switch (System.getProperty("metodo")){
            case "comun":{
                System.out.println("========================= SE SELECCIONO 2FA COMUN =====================");
                segundoFactor = getSecondFactorValidate();
                break;
            }
            case "masterCode":{
                System.out.println("========================= SE SELECCIONO 2FA MASTERCODE ==================");
                segundoFactor = Settings.masterCode;
                break;
            }
            default:{
                System.out.println("================ Metodo 2FA incorrecto revisar el parametro ====================");
                segundoFactor = "Metodo 2fa Incorrecto";
                Assert.fail("================ Metodo 2FA incorrecto revisar el parametro ====================");
            }
        }
        return segundoFactor;
    }
}
