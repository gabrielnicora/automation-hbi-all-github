package Steps.Mensajes;

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

import java.util.LinkedHashMap;
import java.util.List;

public class MensajesSteps {
    RequestSteps requestSteps = new RequestSteps();
    String host = System.getProperty("bankAPI");
    PandoraRequest postRequest = new PandoraRequest(host, RequestMethods.POST);
    Utils utils = new Utils();
    DobleFactor dobleFactor = new DobleFactor();
    ValidarResponse validarResponse = new ValidarResponse();
    private PandoraResponse resp;
    private String cantidadMensajes = null;

    //Seccion de  mapeo de servicios

    private PandoraResponse communicationsList(){
        Settings.endpoint = "/api/v1/execute/communications.list";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Mensajes/communicationsList.json");

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }
    private PandoraResponse communicationsList(String mensajeLeido){
        Settings.endpoint = "/api/v1/execute/communications.list";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Mensajes/communicationsListMSG.json");

        body = body.replace("\"_onlyUnread_\"",mensajeLeido);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }


     //seccion de metodos publicos donde se consumen los servicios

    @Step("Validar la cantidad de Mensajes en casilla")
    public void validarCantidadMensajes(){
        int intentos = 0;

        resp = communicationsList();
        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                resp = communicationsList();
                //validamos el codigo de respuesta
                validarResponse.validateResponses(resp);
            }else{
                    System.out.println("============ LA CUENTA TIENE MENSAJES ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO PARA ACCEDER A LOS MENSAJES INTENTO 4 VECES",intentos==4);

        cantidadMensajes = utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.unreadCommunications").toString());
        System.out.println("============ LA CUENTA TIENE " +cantidadMensajes+ " MENSAJES ====================");
        Allure.addAttachment("Cantidad de mensajes no leidos en casilla: ", cantidadMensajes);
    }

    @Step("Validar los mensajes no leidos")
    public void validarMensajesNoLeidos(){
        int intentos = 0;
        resp = communicationsList("true");
        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                resp = communicationsList("true");
                //validamos el codigo de respuesta
                validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ LA CUENTA TIENE MENSAJES ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO PARA ACCEDER A LOS MENSAJES INTENTO 4 VECES",intentos==4);
       List< LinkedHashMap<String,Object>> var = JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.communications",List.class);

        for(LinkedHashMap<String,Object> a : var){
            Assert.assertFalse("un mensaje leido esta en los no leidos", a.get("userRead").equals("false"));
            System.out.println("valor del userRead por iteracion : "+  a.get("userRead"));
            Allure.addAttachment("userRead",String.valueOf(a.get("userRead")));
        }
    }
    @Step("Validar los mensajes  leidos")
    public void validarMensajesLeidos(){
        int intentos = 0;
        resp = communicationsList("false");
        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                resp = communicationsList("false");
                //validamos el codigo de respuesta
                validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ LA CUENTA TIENE MENSAJES ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO PARA ACCEDER A LOS MENSAJES INTENTO 4 VECES",intentos==4);
        List< LinkedHashMap<String,Object>> var = JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.communications",List.class);

        for(LinkedHashMap<String,Object> a : var){
            Assert.assertFalse("un mensaje leido esta en los no leidos", a.get("userRead").equals("true"));
            System.out.println("valor del userRead por iteracion : "+  a.get("userRead"));
            Allure.addAttachment("userRead",String.valueOf(a.get("userRead")));
        }
    }

    @Step("Obtener el id del periodo que de pagos que se mando desde el front de pagos en PDF")
    public void obtenerIdArchivoParaDescargaPagosPDF(){
        int intentos = 0;

        resp = communicationsList();
        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                resp = communicationsList();
                //validamos el codigo de respuesta
                validarResponse.validateResponses(resp);
            }else{
                    System.out.println("============ LA CUENTA TIENE MENSAJES ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO PARA ACCEDER A LOS MENSAJES INTENTO 4 VECES",intentos==4);

        Settings.idFile = utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.attachments..fileList[?(@.fileName == '"+Settings.PAGOS_PDF+"')].idFile", List.class).get(0).toString());
    }
    @Step("Obtener el id del periodo que de pagos que se mando desde el front de pagos en XLS")
    public void obtenerIdArchivoParaDescargaPagosXLS(){
        int intentos = 0;

        resp = communicationsList();
        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                resp = communicationsList();
                //validamos el codigo de respuesta
                validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ LA CUENTA TIENE MENSAJES ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO PARA ACCEDER A LOS MENSAJES INTENTO 4 VECES",intentos==4);

        Settings.idFile = utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.attachments..fileList[?(@.fileName == '"+Settings.PAGOS_XLS+"')].idFile", List.class).get(0).toString());
    }
}
