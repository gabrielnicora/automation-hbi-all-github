package api;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import utils.RestAssuredApi;
import utils.Settings;
import utils.UtilsGP;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class DobleFactorAPI {
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

    RestAssuredApi restAssuredApi = new RestAssuredApi();
    LoginAPI loginAPI = new LoginAPI();
    UtilsGP utilsGP = new UtilsGP();
    public static String exchange, codigo2FAaux, codigoMailAux,codigoVerificacionAux = null;

    private Response sendTokenSms(){
        String body = utilsGP.leerContenidoArchivo("./Requests/compatidos/requestVacio.json");
        Headers headers = new Headers(
                new Header("Content-Type","application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );
        return restAssuredApi.sendPostRequest("api/v1/execute/secondFactor.otp.sms.sendToken",headers,body);
    }
    private Response executeUtilCode(){
        String body = utilsGP.leerContenidoArchivo("./Requests/dobleFactor/utilCode.json");
        Headers header = new Headers(
                new Header("Content-Type","application/json"),
                new Header ("Authorization", loginAPI.accessToken)
        );
        return restAssuredApi.sendPostRequest("api/v1/execute/util.codes",header,body);
    }
    private Response emailSendToken(){
        String body = utilsGP.leerContenidoArchivo("./Requests/compatidos/requestVacio.json");
        Headers headers = new Headers(
                new Header("Content-Type","application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );
        return restAssuredApi.sendPostRequest("api/v1/execute/secondFactor.otp.emailSendToken",headers,body);
    }
    private Response preferencesUserDataMobileSendCode(String codArea, String num,String code){
        String body;

        body = utilsGP.leerContenidoArchivo("./Requests/dobleFactor/preferencesUserDataMobilePhoneSendCode.json");

        Headers headers = new Headers(
                new Header("Content-Type","application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );
        body = body.replace("_codArea_",codArea).replace("_numero_",num).replace("_code2FA_",code);

        return restAssuredApi.sendPostRequest("api/v1/execute/preferences.userData.mobilePhone.sendCode",headers,body);
    }
    private Response preferencesUserDataMailSendCode(String mail, String code){
        String body;

        body = utilsGP.leerContenidoArchivo("./Requests/dobleFactor/preferencesUserDataMailSendCode.json");

        Headers headers = new Headers(
                new Header("Content-Type","application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );

        body = body.replace("_email_",mail).replace("_code_",code);

        return restAssuredApi.sendPostRequest("api/v1/execute/preferences.userData.mail.sendCode",headers,body);
    }

    private Response enrollmentWizardPre(){
        String body = utilsGP.leerContenidoArchivo("./Requests/dobleFactor/enrollmentWizardPre.json");
        Headers headers = new Headers(
                new Header("Content-Type","application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );
        return restAssuredApi.sendPostRequest("api/v1/execute/enrollment.wizard.pre",headers,body);
    }

    private Response enrollmentValidationDebitCardPin(){
        String body = utilsGP.leerContenidoArchivo("./Requests/dobleFactor/enrollmentValidationDebitCardPin.json");
        Headers  headers = new Headers(

                new Header("Content-Type","application/json"),
                new Header("Authorization","exchange " + exchange)

        );

        body = body.replace("_exchangeToken_",exchange);

        return restAssuredApi.sendPostRequest("api/v1/execute/enrollment.validation.debitCardPin",headers,body);
    }

    private Response crearUsuarioEnrolamiento(){

        String body = utilsGP.leerContenidoArchivo("./Requests/login/enrollmentDigitalPre.json");

        Headers headers = new Headers(

                new Header("Content-Type","application/json")

        );

        return restAssuredApi.sendPostRequest("api/v1/execute/session.login.step1", headers, body);

    }

    //METODOS A UTILIZAR
    public void sendCode(String code,String num){
        String codArea;
        codArea = num.substring(0,2);
        num = num.substring(2,10);

        preferencesUserDataMobileSendCode(codArea,num,code);
    }
    public void emailSendCode(String email,String code){

      preferencesUserDataMailSendCode(email,code);
    }
    //OBTIENE CODIGO 2FA - UTILIZA EL NUMERO DE LOGUEO
    public String getSecondFactorValidate(){
        int cant2FActualizada, cant2FActual=0;
        int j = 1;
        String codigo = null;
        Response utilCode;

        utilsGP.fixedWait(3000);

        utilCode = executeUtilCode();
        cant2FActual = restAssuredApi.getListFromJson(utilCode,"data.codeList.findAll {it.origin == '" + loginAPI.origin + "'}").size();

        System.out.println("======== ACUTAL :" +  cant2FActual);

        while (j<4){
            sendTokenSms();
            for (int i = 1; i <= 4; i++) {

                utilsGP.fixedWait(2500);

                utilCode= executeUtilCode();
                cant2FActualizada = restAssuredApi.getListFromJson(utilCode,"data.codeList.findAll {it.origin == '"+ loginAPI.origin +"'}").size();

                System.out.println("===SEGUNDOS PARA ACTUALIZAR LISTCODE : "+ i*2 +" SEGUNDOS");
                System.out.println("============= CANTIDAD DE CODIGOS ACTUALIZADA : " + cant2FActualizada);

                if (cant2FActual < cant2FActualizada) {

                    codigo = restAssuredApi.getStringFromJson(utilCode,"data.codeList.findAll {it.origin == '"+ loginAPI.origin +"'}.last().code");
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
    public String getSecondFactorOriginOrignal() throws InterruptedException {
        int cant2FActualizada=0;
        int cant2FActual = 0;
        String code= null;
        Response utilCode;

        Thread.sleep(3000);

        utilCode = executeUtilCode();
        cant2FActual = restAssuredApi.getListFromJson(utilCode,"data.codeList.findAll {it.origin == '" + loginAPI.originOriginal + "'}").size();
        System.out.println("======== ACUTAL :" +  cant2FActual);
        int j = 1;
        while (j<4){
            sendTokenSms();
            for (int i = 1; i <= 4; i++) {
                Thread.sleep(2500);
                utilCode= executeUtilCode();
                cant2FActualizada = restAssuredApi.getListFromJson(utilCode,"data.codeList.findAll {it.origin == '"+ loginAPI.originOriginal +"'}").size();
                System.out.println("===SEGUNDOS PARA ACTUALIZAR LISTCODE : "+ i*2 +" SEGUNDOS");
                System.out.println("============= CANTIDAD DE CODIGOS ACTUALIZADA : " + cant2FActualizada);
                if (cant2FActual < cant2FActualizada) {
                    code = restAssuredApi.getStringFromJson(utilCode,"data.codeList.findAll {it.origin == '"+ loginAPI.originOriginal +"'}.last().code");
                    System.out.println("=============== CODIGO ANTERIOR UTILIZADO : "+ codigo2FAaux +"===================================");
                    if(code.equals(codigo2FAaux)){
                        System.out.println("======== SE REPITE EL CODIGO SE VUELVE ACTUALIZAR EL UTILCODE ==============");
                    }else{
                        System.out.println("====================== CODIGO LIMPIO NO REPETIDO ==========================");
                        codigo2FAaux =  code;
                        break;
                    }
                }
            } if(code == null){
                System.out.println("No se actualizo los codigos volver a pedir token");
                cant2FActual = 0;//restAssuredApi.getListFromJson(secondFactor(),"data.codeList.findAll {it.origin == '"+ origin +"'}").size();
                System.out.println("================= EL NUEVO ACTUAL   "+ cant2FActual);
                j++;
            }else {
                System.out.println("cantidad send token enviados : " + j);
                break;
            }
        }

        System.out.println("=============  CODIGO : " + code);
        return code;
    }

    //OBTIENE CODIGO PARA RECUPERO DE USUARIO - EXTRAE CodeNoFormat
    public String getSecondFactorMailUser() throws InterruptedException {
        String codigo= null;
        int cant2FActual, cant2fActualizada = 0;
        int j = 1;
        Response utilCode;

        utilCode = executeUtilCode();

        cant2FActual = restAssuredApi.getListFromJson(utilCode,"data.codeList.findAll {it.origin == '" + loginAPI.email + "'}").size();

        System.out.println("======== ACUTAL :" +  cant2FActual);

        while (j<4){
        for (int i=0; i<4;i++){

         UtilsGP.fixedWait(2500);

            utilCode = executeUtilCode();
            cant2FActual = restAssuredApi.getListFromJson(utilCode,"data.codeList.findAll {it.origin == '" + loginAPI.email + "'}").size();

            System.out.println("===SEGUNDOS PARA ACTUALIZAR LISTCODE : "+ i*2 +" SEGUNDOS");
            System.out.println("============= CANTIDAD DE CODIGOS ACTUALIZADA : " + cant2fActualizada);

            if (cant2FActual > 0 ) {


                String codeLis = restAssuredApi.getStringFromJson(utilCode,"data.codeList.findAll {it.origin == '" + loginAPI.email + "'}.last().codeNoFormat");

                if (codeLis.startsWith("¡Hola")){
                    codigo = codeLis.split("<br/>")[2];
                    break;
                }else{
                    System.out.println(" ================= NO COINCIEDE EL USUARIO ================= ");
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

    //OBTIENE CODIGO 2FA -UTILIZA MAIL DEL USUARIO LOGUEADO - SE UTILIZA PARA CAMBIO DE TELEFONO - EXTRAE CODE
    public String getSecondFactorOriginMail() throws InterruptedException {
        int cant2FActualizada,cant2FActual = 0;
        int j = 1;
        String codigoMail = null;
        Response utilCode;

        utilCode = executeUtilCode();
        cant2FActual = restAssuredApi.getListFromJson(utilCode,"data.codeList.findAll {it.origin == '" + loginAPI.email + "'}").size();

        System.out.println("======== ACUTAL :" +  cant2FActual);

        while (j<4) {
            emailSendToken();
            for (int i = 1; i <= 4; i++) {
                Thread.sleep(2500);

                utilCode = executeUtilCode();
                cant2FActualizada = restAssuredApi.getListFromJson(utilCode, "data.codeList.findAll {it.origin == '" + loginAPI.email + "'}").size();

                System.out.println("===SEGUNDOS PARA ACTUALIZAR LISTCODE : " + i * 2 + " SEGUNDOS");
                System.out.println("============= ACTUALIZDAA : " + cant2FActualizada);

                if (cant2FActual < cant2FActualizada) {

                    codigoMail = restAssuredApi.getStringFromJson(utilCode,"data.codeList.findAll {it.origin == '"+loginAPI.email+"'}.last().code");
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
                    cant2FActual = 0;//restAssuredApi.getListFromJson(secondFactor(),"data.codeList.findAll {it.origin == '"+ origin +"'}").size();
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

    //OBTIENE CODIGO DE VERIFICACION DE 6 DIGITOS ALFANUMERICOS - NECESITA PASARLE UN NUMERO PARA REALIZRA LA BUSQUEDA 2FA - EXTRAE CAMPO CodeNoFormat
    public String getSecondFactorVerificationCode(String number) throws InterruptedException {
        int cant2FActualizada, cant2FActual = 0;
        int j = 1;
        String codigo2FA, codigoVerificacion = null;
        Response utilCode;

        while (j < 4) {

            System.out.println("======== ACUTAL :" + cant2FActual);

            for (int i = 1; i <= 4; i++) {
                Thread.sleep(2000);

                utilCode = executeUtilCode();
                cant2FActualizada = restAssuredApi.getListFromJson(utilCode, "data.codeList.findAll {it.origin == '" + number + "'}").size();

                System.out.println("===SEGUNDOS PARA ACTUALIZAR LISTCODE : " + i * 2 + " SEGUNDOS");
                System.out.println("============= ACTUALIZDAA : " + cant2FActualizada);

                if (cant2FActual < cant2FActualizada) {
                    codigoVerificacion = restAssuredApi.getStringFromJson(utilCode, "data.codeList.findAll {it.origin == '" + number + "'}.last().codeNoFormat").split(":")[1].trim();
                    System.out.println("=============== CODIGO ANTERIOR UTILIZADO : " + codigoVerificacionAux + "===================================");
                    if (codigoVerificacion.equals(codigoVerificacionAux)) {
                        System.out.println("======== SE REPITE EL CODIGO SE VUELVE ACTUALIZAR EL UTILCODE ==============");
                    } else {
                        System.out.println("====================== CODIGO LIMPIO NO REPETIDO ==========================");
                        codigoVerificacionAux = codigoVerificacion;
                        break;
                    }
                }
            }
                if (codigoVerificacion == null) {
                    System.out.println("No se actualizo los codigos volver a pedir token");
                    cant2FActual = 0;//restAssuredApi.getListFromJson(secondFactor(),"data.codeList.findAll {it.origin == '"+ origin +"'}").size();
                    System.out.println("================= EL NUEVO ACTUAL   " + cant2FActual);
                    j++;
                } else {
                    System.out.println("cantidad send token enviados : " + j);
                    break;
                }
            codigo2FA= getSecondFactorOriginMail();
            sendCode(codigo2FA, number);
        }

        System.out.println("=============  CODIGO : " + codigoVerificacion);
        return codigoVerificacion;
    }

    //OBTIENE CODIGO DE VERIFICACION DE 6 DIGITOS O 2FA - SE PASA UN MAIL PARA LA BUSQUEDA - EXTRAER CAMPO Code
    public String getSecondFactorOriginMail (String email) throws InterruptedException {
            int cant2FActualizada,cant2FActual = 0;
            int j = 1;
            String codigo2FA,codigoMail = null;
            Response utilCode;

            System.out.println("======== ACUTAL :" + cant2FActual);

            while (j < 4) {
                for (int i = 1; i <= 4; i++) {
                    Thread.sleep(2500);
                    utilCode = executeUtilCode();

                    cant2FActualizada =  restAssuredApi.getListFromJson(utilCode, "data.codeList.findAll {it.origin == '" + email + "'}").size();

                    System.out.println("===SEGUNDOS PARA ACTUALIZAR LISTCODE : " + i * 2 + " SEGUNDOS");
                    System.out.println("============= ACTUALIZDAA : " + cant2FActualizada);

                    if (cant2FActual < cant2FActualizada) {
                        codigoMail = restAssuredApi.getStringFromJson(utilCode, "data.codeList.findAll {it.origin == '" + email + "'}.last().code");
                        System.out.println("=============== CODIGO ANTERIOR UTILIZADO : " + codigoMailAux + "===================================");
                        if (codigoMail.equals(codigoMailAux)) {
                            System.out.println("======== SE REPITE EL CODIGO SE VUELVE ACTUALIZAR EL UTILCODE ==============");
                        } else {
                            System.out.println("====================== CODIGO LIMPIO NO REPETIDO ==========================");
                            codigoMailAux = codigoMail;
                            break;
                        }
                        break;
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

                codigo2FA = getSecondFactor();
                emailSendCode(email, codigo2FA);
            }
            System.out.println("=============  CODIGO : " + codigoMail);
            return codigoMail;
        }

    public String getSecondFactor(){
        String segundoFactor;
        String metodoConfigurado = System.getProperty("metodo", METODO_COMUN);
        String metodoEfectivo = resolverMetodoSegundoFactor(
                metodoConfigurado,
                LoginAPI.getUsuarioAutenticado()
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

    ///  Método de segundo factor de Enrolamiento de SMS ///

    public String getSecondFactorValidateEnrolamiento(){
        int cant2FActualizada, cant2FActual=0;
        int j = 1;
        String codigo = null;
        Response utilCode;

        utilsGP.fixedWait(3000);

        utilCode = executeUtilCode();
        cant2FActual = restAssuredApi.getListFromJson(utilCode,"data.codeList.findAll {it.origin == '" + loginAPI.origin + "'}").size();

        System.out.println("======== ACUTAL :" +  cant2FActual);

        while (j<4){
            enrollmentWizardPre();
            for (int i = 1; i <= 4; i++) {

                utilsGP.fixedWait(2500);

                utilCode= executeUtilCode();
                cant2FActualizada = restAssuredApi.getListFromJson(utilCode,"data.codeList.findAll {it.origin == '"+ loginAPI.origin +"'}").size();

                System.out.println("===SEGUNDOS PARA ACTUALIZAR LISTCODE : "+ i*2 +" SEGUNDOS");
                System.out.println("============= CANTIDAD DE CODIGOS ACTUALIZADA : " + cant2FActualizada);

                if (cant2FActual < cant2FActualizada) {

                    codigo = restAssuredApi.getStringFromJson(utilCode,"data.codeList.findAll {it.origin == '"+ loginAPI.origin +"'}.last().code");
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

    ///  Método de segundo factor de Enrolamiento de SMS Tarjeta Debito con PIN ///

    public String getSecondFactorValidateEnrolamientoPinTarjetaDebito(){
        int cant2FActualizada, cant2FActual=0;
        int j = 1;
        String codigo = null;
        Response utilCode, enrolamientoPre;

        utilsGP.fixedWait(3000);

        utilCode = executeUtilCode();
        cant2FActual = restAssuredApi.getListFromJson(utilCode,"data.codeList.findAll {it.origin == '" + loginAPI.origin + "'}").size();

        System.out.println("======== ACUTAL :" +  cant2FActual);

        enrolamientoPre = crearUsuarioEnrolamiento();

        exchange = restAssuredApi.getStringFromJson(enrolamientoPre,"data._exchangeToken");

        while (j<4){
            enrollmentValidationDebitCardPin();
            for (int i = 1; i <= 4; i++) {

                utilsGP.fixedWait(2500);

                utilCode= executeUtilCode();
                cant2FActualizada = restAssuredApi.getListFromJson(utilCode,"data.codeList.findAll {it.origin == '"+ loginAPI.origin +"'}").size();

                System.out.println("===SEGUNDOS PARA ACTUALIZAR LISTCODE : "+ i*2 +" SEGUNDOS");
                System.out.println("============= CANTIDAD DE CODIGOS ACTUALIZADA : " + cant2FActualizada);

                if (cant2FActual < cant2FActualizada) {

                    codigo = restAssuredApi.getStringFromJson(utilCode,"data.codeList.findAll {it.origin == '"+ loginAPI.origin +"'}.last().code");
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
}


