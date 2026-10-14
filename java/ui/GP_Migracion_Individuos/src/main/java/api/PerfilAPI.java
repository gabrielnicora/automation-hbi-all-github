package api;

import io.qameta.allure.Allure;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import utils.RestAssuredApi;
import utils.UtilsGP;

public class PerfilAPI {
    RestAssuredApi restAssuredApi = new RestAssuredApi();
    DobleFactorAPI dobleFactorAPI = new DobleFactorAPI();
    LoginAPI loginAPI = new LoginAPI();
    UtilsGP utilsGP = new UtilsGP();

    private Response preferencesUserDataMailUpdate(String codigo){
        String body;

        body = utilsGP.leerContenidoArchivo("./Requests/perfil/preferencesUserDataMailUpdate.json");

        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );
        body = body.replace("_code_",codigo);

        return restAssuredApi.sendPostRequest("/api/v1/execute/preferences.userData.mail.update", headers, body);
    }
    private Response preferencesUserDataMobilePhoneUpdate(String codigo){
        String body;

        body = utilsGP.leerContenidoArchivo("./Requests/perfil/preferencesUserDataMobileUpdate.json");

        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );
        body = body.replace("_code_",codigo);

        return restAssuredApi.sendPostRequest("/api/v1/execute/preferences.userData.mobilePhone.update", headers, body);
    }

    public void setearEmailOriginalAPI() {
        try {
            String data,codigoVerificacion,codigoDobleFactor;
            int intentos = 0;
            Response res ;

            //OBTIENE DOBLE FACTOR PRIMERO PARA QUE SE ENVIE EL CODIGO DE VERIFICACION DE 6 DIGITOS
            codigoDobleFactor = dobleFactorAPI.getSecondFactor();
            dobleFactorAPI.emailSendCode(LoginAPI.emailOriginal,codigoDobleFactor);

            //OBTIENE EL CODIGO DE VERIFICACION 6 DIGITOS
            codigoVerificacion = dobleFactorAPI.getSecondFactorOriginMail(loginAPI.emailOriginal);

            res = preferencesUserDataMailUpdate(codigoVerificacion);
            data = restAssuredApi.getStringFromJson(res,"data");

            while(intentos<4){
                if(data.contains("El código de validación ya se ha utilizado")){
                    System.out.println("=========== SE REINTENTA EL SERVICIO CON OTRO CODIGO ======================= ");

                    codigoDobleFactor = dobleFactorAPI.getSecondFactor();
                    dobleFactorAPI.emailSendCode(LoginAPI.emailOriginal,codigoDobleFactor);

                    codigoVerificacion = dobleFactorAPI.getSecondFactorOriginMail(loginAPI.emailOriginal);
                    res = preferencesUserDataMailUpdate(codigoVerificacion);

                    data = restAssuredApi.getStringFromJson(res,"data");
                }else{
                    break;
                }
                intentos++;
            }
        }catch (Exception e){
            System.out.println("Mensaje de Error : " + e.getMessage());
            Allure.addAttachment("EXEPCION:",e.getMessage());
        }

    }
    public void setearNumeroTelefonoOriginalAPI() {
        try{
            String data,codigoVerificacion,codigoDobleFactor;
            int intentos = 0;
            Response res ;

            //OBTIENE DOBLE FACTOR PRIMERO PARA QUE SE ENVIE EL CODIGO DE VERIFICACION DE 6 DIGITOS
            codigoDobleFactor = dobleFactorAPI.getSecondFactorOriginMail();
            dobleFactorAPI.sendCode(codigoDobleFactor,loginAPI.originOriginal);

            //OBTIENE EL CODIGO DE VERIFICACION 6 DIGITOS
            codigoVerificacion = dobleFactorAPI.getSecondFactorVerificationCode(loginAPI.originOriginal);

            res = preferencesUserDataMobilePhoneUpdate(codigoVerificacion);
            data = restAssuredApi.getStringFromJson(res,"data");

            while(intentos<4){
                if(data.contains("mobilePhoneCode:El código ingresado ya se ha utilizado")){
                    System.out.println("=========== SE REINTENTTA EL SERVICIO CON OTRO CODIGO ");
                    codigoDobleFactor = dobleFactorAPI.getSecondFactorVerificationCode(loginAPI.originOriginal);
                    res = preferencesUserDataMobilePhoneUpdate(codigoVerificacion);
                    data = restAssuredApi.getStringFromJson(res,"data");
                }else{
                    break;
                }
                intentos++;
            }
        }catch (Exception e){
            System.out.println("Mensaje de Error : " + e.getMessage());
            Allure.addAttachment("EXEPCION:",e.getMessage());
        }
    }
}
