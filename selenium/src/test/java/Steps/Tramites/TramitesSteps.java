package Steps.Tramites;

import Config.Settings;
import Config.ValidarResponse;
import constants.RequestMethods;
import core.PandoraRequest;
import core.PandoraResponse;
import io.qameta.allure.Step;
import org.GP_Individuos_API.Utils;
import steps.RequestSteps;

public class TramitesSteps {
    RequestSteps requestSteps = new RequestSteps();
    String host = System.getProperty("bankAPI");
    PandoraRequest postRequest = new PandoraRequest(host, RequestMethods.POST);
    Utils utils = new Utils();
    ValidarResponse validarResponse = new ValidarResponse();
    PandoraResponse response;

    private PandoraResponse onlineProcedureList(){
        Settings.endpoint = "/api/v1/execute/onlineProcedure.list";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);


        String body = utils.leerContenidoArchivo("./Requests/Compartidos/requestVacio.json");


        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }

    @Step("consultar Tramites vigente por usuario")
    public void tramitesOnline(){

        response = onlineProcedureList();
        //validamos que la respuesta del servicio sea satisfactorio
        validarResponse.validateResponses(response);
    }
}
