package Steps.DobleFactor;

import Config.Settings;
import Config.ValidarResponse;
import Steps.Login.LoginApiSteps;
import com.jayway.jsonpath.JsonPath;
import constants.RequestMethods;
import core.PandoraRequest;
import core.PandoraResponse;
import org.GP_Individuos_API.Utils;
import steps.RequestSteps;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class DobleFactor {
    private static final String METODO_COMUN = "comun";
    private static final String METODO_MASTER_CODE = "masterCode";
    private static final Set<String> USUARIOS_MASTER_CODE = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(
                    "20200960948",
                    "ecarina27",
                    "transpull1",
                    "berhometest22",
                    "berhometest15",
                    "isidro11",
                    "transpull4",
                    "berhometest17"
            ))
    );

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
        String codigoValido = null;
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
                    String codigoCandidato = listaCodigos.get(listaCodigos.size()-1).get("code");
                            //restAssuredApi.getStringFromJson(utilCode,"data.codeList.findAll {it.origin == '"+ loginAPI.origin +"'}.last().code");
                    System.out.println("=============== CODIGO ANTERIOR UTILIZADO : "+ Settings.dobleFactor +"===================================");

                    if(codigoCandidato.equals(Settings.dobleFactor)){
                        System.out.println("======== SE REPITE EL CODIGO SE VUELVE ACTUALIZAR EL UTILCODE ==============");
                    }else{
                        System.out.println("====================== CODIGO LIMPIO NO REPETIDO ==========================");
                        codigoValido = codigoCandidato;
                        Settings.dobleFactor = codigoValido;
                        break;
                    }
                }
            } if(codigoValido == null){
                System.out.println("No se actualizo los codigos volver a pedir token");
                cant2FActual = 0;//restAssuredApi.getListFromJson(secondFactor(),"data.codeList.findAll {it.origin == '"+ origin +"'}").size();
                System.out.println("================= EL NUEVO ACTUAL   "+ cant2FActual);
                j++;
            }else {
                System.out.println("cantidad send token enviados : " + j);
                break;
            }
        }

        if (codigoValido == null) {
            throw new IllegalStateException(
                    "No se pudo obtener un codigo de segundo factor nuevo luego de 3 intentos"
            );
        }

        System.out.println("=============  CODIGO : " + codigoValido);
        return codigoValido;
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
        String metodoConfigurado = System.getProperty("metodo", METODO_COMUN);
        String metodoEfectivo = resolverMetodoSegundoFactor(
                metodoConfigurado,
                LoginApiSteps.getUsuarioAutenticado()
        );

        switch (metodoEfectivo){
            case METODO_COMUN:{
                System.out.println("========================= SE SELECCIONO 2FA COMUN =====================");
                segundoFactor = getSecondFactorValidate();
                break;
            }
            case METODO_MASTER_CODE:{
                System.out.println("========================= SE SELECCIONO 2FA MASTERCODE ==================");
                segundoFactor = Settings.masterCode;
                break;
            }
            default:{
                throw new IllegalStateException("Metodo 2FA efectivo no soportado: " + metodoEfectivo);
            }
        }
        return segundoFactor;
    }

    private static String resolverMetodoSegundoFactor(String metodoConfigurado, String usuario) {
        if (METODO_COMUN.equals(metodoConfigurado)) {
            return METODO_COMUN;
        }

        if (!METODO_MASTER_CODE.equals(metodoConfigurado)) {
            throw new IllegalArgumentException(
                    "Metodo 2FA incorrecto: " + metodoConfigurado
                            + ". Valores permitidos: " + METODO_COMUN + ", " + METODO_MASTER_CODE
            );
        }

        if (esUsuarioMasterCode(usuario)) {
            return METODO_MASTER_CODE;
        }

        System.out.println(
                "================ USUARIO NO HABILITADO PARA MASTERCODE; "
                        + "SE UTILIZARA 2FA COMUN ================="
        );
        return METODO_COMUN;
    }

    private static boolean esUsuarioMasterCode(String usuario) {
        return usuario != null
                && USUARIOS_MASTER_CODE.contains(usuario.trim().toLowerCase(Locale.ROOT));
    }
}
