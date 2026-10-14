package Steps.BonosDeSalud;

import Config.Settings;
import Config.ValidarResponse;
import Steps.DobleFactor.DobleFactor;
import com.jayway.jsonpath.JsonPath;
import constants.RequestMethods;
import core.PandoraRequest;
import core.PandoraResponse;
import io.qameta.allure.Step;
import org.GP_Individuos_API.Utils;
import org.junit.Assert;
import steps.RequestSteps;

public class BonosDeSaludSteps {
    RequestSteps requestSteps = new RequestSteps();
    String host = System.getProperty("bankAPI");
    PandoraRequest postRequest = new PandoraRequest(host, RequestMethods.POST);
    Utils utils = new Utils();
    DobleFactor dobleFactor = new DobleFactor();
    ValidarResponse validarResponse = new ValidarResponse();
    private PandoraResponse resp;

    //Seccion de  mapeo de servicios

    private PandoraResponse listEntitiesByCategory(){
        Settings.endpoint = "/api/v1/execute/healthBonus.listEntitiesByCategory";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/BonosDeSalud/healtBonusEntiti.json");

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse rechargeValue(){
        Settings.endpoint = "/api/v1/execute/healthBonus.rechargeValue";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/BonosDeSalud/rechargeValue.json");

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

     //seccion de metodos publicos donde se consumen los servicios

    @Step("Comprar bono de salud")
    public void generarDatosBonoSalud(){
        int intentos = 0;

        resp = listEntitiesByCategory();
        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                resp = listEntitiesByCategory();
                //validamos el codigo de respuesta
                validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ SE GENERAN LOS DATOS PARA LA COMPRA EL BONO CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO PARA GENERAR LOS DATOS PARA COMPRAR EL BONO INTENTO 4 VECES",intentos==4);
    }

    @Step("Comprar bono de salud")
    public void comprarBonoSalud(){
         int intentos = 0;

         resp = rechargeValue();
         validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                resp = rechargeValue();
                //validamos el codigo de respuesta
                validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ SE COMPRA EL BONO CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO PARA COMPRAR EL BONO INTENTO 4 VECES",intentos==4);
    }

}
