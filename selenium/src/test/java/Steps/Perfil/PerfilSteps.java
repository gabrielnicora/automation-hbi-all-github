package Steps.Perfil;

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
import steps.RequestSteps;

import java.util.Arrays;
import java.util.List;

public class PerfilSteps {
    private static final List<String> TELEFONOS_DE_PRUEBA = Arrays.asList(
            "1156676780",
            "1156676781",
            "1156676782"
    );

    RequestSteps requestSteps = new RequestSteps();
    String host = System.getProperty("bankAPI");
    PandoraRequest postRequest = new PandoraRequest(host, RequestMethods.POST);
    Utils utils = new Utils();
    DobleFactor dobleFactor = new DobleFactor();
    ValidarResponse validarResponse = new ValidarResponse();
    private PandoraResponse resp;
    private String code2FA, idAdheredAcount, mobilePhone, mobileAreaCode, codigoVerificacion = null;
    private boolean ownAccount, isAdhered, estadoContacto, sms, mail, push;
    private double quantityLoan, quantityAccredit, tea, tna, cft, idSalaryAdvance = 0;

    //Seccion de  mapeo de servicios

    private PandoraResponse mobilePhoneUpdate(String codigoVerificacion) {
        Settings.endpoint = "/api/v1/execute/preferences.userData.mobilePhone.update";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Perfil/preferencesUserDataMobilePhoneUpdate.json");

        body = body.replace("_codigoVerificacion_", codigoVerificacion);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse mobilePhoneSendCode(String telefono, String code2FA) {
        Settings.endpoint = "/api/v1/execute/preferences.userData.mobilePhone.sendCode";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Perfil/mobilePhoneSendCode.json");

        body = body.replace("_mobilePhone_", telefono)
                .replace("_code2FA_", code2FA);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse notificationsConfigurationPre(){
        Settings.endpoint = "/api/v1/execute/preferences.notifications.configuration.pre";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Compartidos/requestVacio.json");

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse notificationsConfigurationModifyPre(){
        Settings.endpoint = "/api/v1/execute/preferences.notifications.configuration.modify.pre";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Perfil/preferencesNotificationsConfigurationModifyPre.json");

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    //seccion de metodos publicos donde se consumen los servicios
    //REFACTOR
    @Step("Cambia el numero de telefono actual por uno de reemplazo")
    public String cambioDeNumeroTelefonicoReemplazo(){
        int intentos = 0;
        String telefonoDeReemplazo = seleccionarTelefonoDeReemplazo(Settings.telefono);
        String telefonoDeReemplazoSin11 = telefonoDeReemplazo.substring(2);
        code2FA = dobleFactor.getSecondFactor();

        resp = mobilePhoneSendCode(telefonoDeReemplazoSin11, code2FA);
        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                code2FA = dobleFactor.getSecondFactor();
                resp = mobilePhoneSendCode(telefonoDeReemplazoSin11, code2FA);
                //validamos el codigo de respuesta
                validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ SE CAMBIO EL TELEFONO CORRECTAMENTE 1 ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO DE CAMBIO DE TELEFONO INTENTO 4 VECES 2",intentos==4);
        return telefonoDeReemplazo;
    }

    private String seleccionarTelefonoDeReemplazo(String telefonoActual) {
        for (String telefonoDePrueba : TELEFONOS_DE_PRUEBA) {
            if (!telefonoDePrueba.equals(telefonoActual)) {
                Allure.addAttachment("TELEFONO ACTUAL", telefonoActual);
                Allure.addAttachment("TELEFONO DE REEMPLAZO", telefonoDePrueba);
                return telefonoDePrueba;
            }
        }

        throw new IllegalStateException("No hay un telefono de prueba diferente del telefono actual");
    }

    @Step("Confirma el cambio de telefono")
    public void confirmarCambioDeTelefono(String numeroAconfirmar){
        int intentos = 0;
        codigoVerificacion = dobleFactor.getSecondFactorConfirmation(numeroAconfirmar);
        System.out.println("********El codigo de verificacion es: "+codigoVerificacion);

        resp = mobilePhoneUpdate(codigoVerificacion);
        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                codigoVerificacion = dobleFactor.getSecondFactorConfirmation(numeroAconfirmar);
                resp = mobilePhoneUpdate(codigoVerificacion);
                //validamos el codigo de respuesta
                validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ SE CONFIRMO EL CAMBIO DE CELULAR CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO DE CONFIRMACION DE CELULAR INTENTO 4 VECES",intentos==4);
    }

    @Step("Ingresa a las notificaciones")
    public void ingresarAnotificaciones() {
        int intentos = 0;

        resp = notificationsConfigurationPre();
        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while (intentos < 4) {
            if (Settings.flagServicioReejecucion) {
                resp = notificationsConfigurationPre();
                //validamos el codigo de respuesta
                validarResponse.validateResponses(resp);
            } else {
                System.out.println("============ SE ACCEDIO A LA CONFIGURACION DE LAS NOTIFICACIONES CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO DE ACCEDER A LA CONFIGURACION DE LAS NOTIFICACIONES INTENTO 4 VECES", intentos == 4);
    }

    @Step("Valida el estado de configuracion de las notificaciones para que sean las 3 true")
    public void obtenerEstadoDeNotificacionesMenssage(){
        int intentos = 0;

        resp = notificationsConfigurationModifyPre();
        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                resp = notificationsConfigurationModifyPre();
                //validamos el codigo de respuesta
                validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ SE OBTUVIERON LOS DATOS DE ESTADO DE NOTIFICACIONES CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO DE OBTENER LOS DATOS DE ESTADO DE NOTIFICACIONES INTENTO 4 VECES",intentos==4);

        sms = JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.communicationConfigurations.subscribedSMS");
        mail = JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.communicationConfigurations.subscribedMail");
        push = JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.communicationConfigurations.subscribedPush");

        Allure.addAttachment("ESTADO DE LA NOTIFICACION SMS", String.valueOf(sms));
        Assert.assertTrue("LAS NOTIFICACIONES ESTÁN INACTIVAS PARA SMS",sms);

        Allure.addAttachment("ESTADO DE LA NOTIFICACION MAIL", String.valueOf(mail));
        Assert.assertTrue("LAS NOTIFICACIONES ESTÁN INACTIVAS PARA MAIL",mail);

        Allure.addAttachment("ESTADO DE LA NOTIFICACION PUSH", String.valueOf(push));
        Assert.assertTrue("LAS NOTIFICACIONES ESTÁN INACTIVAS PARA PUSH",push);

    }

}

