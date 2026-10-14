package Steps.Recargas;

import Config.Settings;
import Config.ValidarResponse;
import Steps.DobleFactor.DobleFactor;

import com.github.tomakehurst.wiremock.common.Json;
import com.jayway.jsonpath.JsonPath;
import constants.RequestMethods;
import core.PandoraRequest;
import core.PandoraResponse;
import io.qameta.allure.Step;
import io.restassured.path.json.config.JsonPathConfig;

import java.util.LinkedHashMap;
import java.util.List;

import org.GP_Individuos_API.Utils;
import org.junit.Assert;
import steps.RequestSteps;

public class RecargasStep {
    RequestSteps requestSteps = new RequestSteps();
    String host = System.getProperty("bankAPI");
    PandoraRequest postRequest = new PandoraRequest(host, RequestMethods.POST);
    Utils utils = new Utils();
    DobleFactor dobleFactor = new DobleFactor();
    ValidarResponse validarResponse = new ValidarResponse();

    String datosPDF;

    //Sección de mapeo de servicios privados

    private PandoraResponse rechargeValueList(){
        Settings.endpoint = "/api/v1/execute/recharge.value.list";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Recargas/rechargeValueListSUBE.json");

        body = body.replace("_idProductAccount_", Settings.idCuentaOrigen)
                .replace("_idProductCard_",Settings.idDebitCard);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }
    private PandoraResponse rechargeValueListPERSONAL(){
        Settings.endpoint = "/api/v1/execute/recharge.value.list";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Recargas/rechargeValueListPERSONAL.json");

        body = body.replace("_idProductAccount_", Settings.idCuentaOrigen)
                .replace("_idProductCard_",Settings.idDebitCard);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }
    private PandoraResponse rechargeListAdhesion(String codigoCompany){
        Settings.endpoint = "/api/v1/execute/recharge.list.adhesions";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Recargas/rechargeListAdhesion.json");

        body = body.replace("_idProducto_", Settings.idDebitCard)
                .replace("_codeCompany_",codigoCompany);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }
    private PandoraResponse deleteAdhesion(String codCompania, String numSube, String perteneceA){
        Settings.endpoint = "/api/v1/execute/delete.adhesion";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Recargas/deleteAdhesion.json");

        body = body.replace("_codigoCompania_",codCompania)
                .replace("_numSube_",numSube)
                .replace("_perteneceA_",perteneceA)
                .replace("_idProducto_",Settings.idDebitCard);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }
    private PandoraResponse rechargeNewAdhesionWithSecondFactor(String codigoCompania,String numTelefono,String perteneceA,String code2FA){
        Settings.endpoint = "/api/v1/execute/recharge.new.adhesionWithSecondFactor";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Recargas/rechargeNewAdhesionWithSecondFactor.json");

        body = body.replace("_idProducto_",Settings.idDebitCard)
                .replace("_codCompania_",codigoCompania)
                .replace("_num_",numTelefono)
                .replace("_perteneceA_",perteneceA)
                .replace("_code_",code2FA);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse rechargeValueListDirectTV(){
        Settings.endpoint = "/api/v1/execute/recharge.value.list";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Recargas/rechargeValueListDirecTv.json");

        body = body.replace("_idProductAccount_", Settings.idCuentaOrigen)
                .replace("_idProductCard_",Settings.idDebitCard);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse listMadeRecharges(){
        Settings.endpoint = "/api/v1/execute/list.made.recharges";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Recargas/listMadeRecharges.json");

        body = body.replace("_idProduct_", Settings.idDebitCard)
                    .replace("_endDate_",utils.fechaHoy());

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse pdfMaker(){
        Settings.endpoint = "/api/v1/execute/pdf.maker";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Recargas/pdfMaker.json");

        body = body.replace("_datosPDF_", datosPDF);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }
    //Sección para los steps publicos

    @Step("Realizar Recargas para Transporte publico SUBE")
    public void recargaSube(){
        PandoraResponse response;
        int intentos = 0;
        boolean recarga = false;
        //servicio que realiza  la recarga
        response = rechargeValueList();
        //validamos el codigo response
        validarResponse.validateResponses(response);
        try{
            //sección para validar si la recarga se realizo correctamente
            recarga = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.valuesCharged[0].value1.success");
        }catch (Exception e){
            System.out.println("MENSAJE DE ERROR: "+ e.getMessage());
            System.out.println("===============EL PATH NO SE PUEDE LLEGAR YA QUE NO EJECUTO BIEN EL SERVICIO==================");
        }
        while (intentos < 4){

            if(recarga){
                System.out.println("====================== RECARGA REALIZADA CON EXITO =============");
                break;
            }else if(recarga==false || Settings.flagServicioReejecucion){
                System.out.println("================ RE-EJEUCCION DE SERVICIO =====================");
                //servicio que realiza  la recarga
                response = rechargeValueList();
                //validamos el codigo response
                validarResponse.validateResponses(response);
                try{
                    //sección para validar si la recarga se realizo correctamente
                    recarga = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.valuesCharged[0].value1.success");
                }catch (Exception e){
                    System.out.println("MENSAJE DE ERROR: "+ e.getMessage());
                    System.out.println("===============EL PATH NO SE PUEDE LLEGAR YA QUE NO EJECUTO BIEN EL SERVICIO==================");
                }
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO RECARGA DE SUBE INTENTO 4 VECES",intentos==4);

    }

    @Step("Consulta serivicos Adheridos (SUBE)")
    public void consultarAdhesionServicioAPI(String codeCompany,String numero){
        PandoraResponse response;
        String servicioAdherido;
        int intentos = 0;

        //ejeución de servicio de consulta de adhesion de servicio
        response = rechargeListAdhesion("618");
        //validamos el codigo del response
        validarResponse.validateResponses(response);
        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                //ejeución de servicio de consulta de adhesion de servicio
                response = rechargeListAdhesion("618");
                //validamos el codigo del response
                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ EL SERVICIO SE EJECUTO CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO LIST ADHESION INTENTO 4 VECES",intentos==4);

        //OBTENEMOS LA VARIABLE DE SI ESTA O NO ADHERIDO EL SERVICIO
        servicioAdherido = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.adhesionsList[?(@.code =='"+ numero + "')].description").toString());
        //VERIFICAMOS SI YA EXISTE EL SERVICIO
        if(!servicioAdherido.isEmpty()){
            System.out.println("======================== YA EXISTE EL SERVICIO PROCDER A ELIMIMAR POR API ====================");
            //servicio de eliminar servicio adherido
            response = deleteAdhesion(codeCompany,numero,servicioAdherido);
            //validamos el response
            validarResponse.validateResponses(response);
        }else {
            System.out.println("============== NO EXISTE LA ADHESION ===============");
        }
    }
    @Step("ADHERIR SERVICIO API (SUBE)")
    public void adhesionServicioSUBEAPI(String codigoCompania, String num, String perteneceA){
        PandoraResponse response;
        String codigo;
        int intentos = 0;

        //ejecutamos doble factor
        codigo = dobleFactor.getSecondFactor();
        //Ejecución de servicio para agregar un servicio api
        response = rechargeNewAdhesionWithSecondFactor(codigoCompania,num,perteneceA,codigo);
        //validamos el response
        validarResponse.validateResponses(response);
        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                //ejecutamos doble factor
                codigo = dobleFactor.getSecondFactor();
                //Ejecución de servicio para agregar un servicio api
                response = rechargeNewAdhesionWithSecondFactor(codigoCompania,num,perteneceA,codigo);
                //validamos el response
                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ EL SERVICIO SE EJECUTO CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO Adherir SERVICO API SE INTENTO 4 VECES",intentos==4);
    }
    @Step("ELIMINAR SERVICIO ADHERIDO SUBE")
    public void eliminarAdhesionSUBEAPI(String codCompania,String numSube,String perteneceA){
        PandoraResponse response;
        int intentos = 0;

        //ejecutamos el servicio que elimina la adhesion
        response = deleteAdhesion(codCompania,numSube,perteneceA);
        //validamos el response
        validarResponse.validateResponses(response);
        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                //ejecutamos el servicio que elimina la adhesion
                response = deleteAdhesion(codCompania,numSube,perteneceA);
                //validamos el response
                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ EL SERVICIO SE EJECUTO CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO ELIMINAR SERVICIO ADHERIDO API SE INTENTO 4 VECES",intentos==4);
    }
    @Step("Realizar Recargas Telefonia PERSONAL LIGHT")
    public void recargaPersonalLight(){
        PandoraResponse response;
        int intentos = 0;
        boolean recarga = false;
        //servicio que realiza  la recarga
        response = rechargeValueListPERSONAL();
        //validamos el codigo response
        validarResponse.validateResponses(response);
        try{
            //sección para validar si la recarga se realizo correctamente
            recarga = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.valuesCharged[0].value1.success");
        }catch (Exception e){
            System.out.println("MENSAJE DE ERROR: "+ e.getMessage());
            System.out.println("===============EL PATH NO SE PUEDE LLEGAR YA QUE NO EJECUTO BIEN EL SERVICIO==================");
        }

        while (intentos < 4){

            if(recarga){
                System.out.println("====================== RECARGA REALIZADA CON EXITO =============");
                break;
            }else if(recarga==false || Settings.flagServicioReejecucion){
                System.out.println("================ RE-EJEUCCION DE SERVICIO DE RECARGA =====================");
                utils.fixedWait(5000);
                //servicio que realiza  la recarga
                response = rechargeValueListPERSONAL();
                //validamos el codigo response
                validarResponse.validateResponses(response);
                try{
                    //sección para validar si la recarga se realizo correctamente
                    recarga = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.valuesCharged[0].value1.success");
                }catch (Exception e){
                    System.out.println("MENSAJE DE ERROR: "+ e.getMessage());
                    System.out.println("===============EL PATH NO SE PUEDE LLEGAR YA QUE NO EJECUTO BIEN EL SERVICIO==================");
                }
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO RECARGA DE PERSONAL LIGHT INTENTO 4 VECES",intentos==4);

    }
    @Step("Realizar Recargas de DIRECT TV")
    public void recargaDirectTV(){
        PandoraResponse response;
        int intentos = 0;
        boolean recarga = false;
        //servicio que realiza  la recarga
        response = rechargeValueListDirectTV();
        //validamos el codigo response
        validarResponse.validateResponses(response);
        try{
            //sección para validar si la recarga se realizo correctamente
            recarga = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.valuesCharged[0].value1.success");
        }catch (Exception e){
            System.out.println("MENSAJE DE ERROR: "+ e.getMessage());
            System.out.println("===============EL PATH NO SE PUEDE LLEGAR YA QUE NO EJECUTO BIEN EL SERVICIO==================");
        }

        while (intentos < 4){

            if(recarga){
                System.out.println("====================== RECARGA REALIZADA CON EXITO =============");
                break;
            }else if(recarga==false || Settings.flagServicioReejecucion){
                System.out.println("================ RE-EJEUCCION DE SERVICIO DE RECARGA =====================");
                utils.fixedWait(5000);
                //servicio que realiza  la recarga
                response = rechargeValueListDirectTV();
                //validamos el codigo response
                validarResponse.validateResponses(response);
                try{
                    //sección para validar si la recarga se realizo correctamente
                    recarga = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.valuesCharged[0].value1.success");
                }catch (Exception e){
                    System.out.println("MENSAJE DE ERROR: "+ e.getMessage());
                    System.out.println("===============EL PATH NO SE PUEDE LLEGAR YA QUE NO EJECUTO BIEN EL SERVICIO==================");
                }
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO RECARGA DE PERSONAL LIGHT INTENTO 4 VECES",intentos==4);

    }

    @Step("Consultar las recarges de SUBE")
    public void consultaRecargasRealizadas(){
        PandoraResponse response;
        int intentos = 0;

        //se ejhecuta el servicio de la consulta
        response = listMadeRecharges();

        //validamos el codigo response
        validarResponse.validateResponses(response);
        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                //se ejhecuta el servicio de la consulta
                response = listMadeRecharges();
                //validamos el response
                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ EL SERVICIO SE EJECUTO CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO CONSULTA DE RECARGAS SUBE API SE INTENTO 4 VECES",intentos==4);


        Assert.assertTrue("USUARIO SIN RECARGAS REALIZADAS REVISAR SERVICIO ", JsonPath.parse(response.getBody().toPrettyString()).read("$.data.recharges", List.class).size()>0);

        //datos de primera recarga
        datosPDF = utils.escaparArrayDatos((LinkedHashMap<String, Object>) JsonPath.parse(response.getBody().toPrettyString()).read("$.data.recharges",List.class).get(0)).replace("\\","\\\\").replace("\"","\\\"");
    }
    
    @Step("Descarga el primer archivo pdf de recargas")
    public void descargaPrimerTicketRecarga(){
        PandoraResponse response;
        int intentos = 0;

        //Ejecutamos el servicio para descargar el pdf
        response = pdfMaker();

        //validamos el codigo de respuesta del servicio
        validarResponse.validateResponses(response);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                //Ejecutamos el servicio para descargar el pdf
                 response = pdfMaker();
                //validamos el response
                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ EL SERVICIO SE EJECUTO CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO DESCARGA DE RECARGAS API SE INTENTO 4 VECES",intentos==4);

        Assert.assertTrue("REVISAR SERVICIO NO SE VALIDA EL NOMBRE DE LA DESCARGA DEL TICKET ", JsonPath.parse(response.getBody().toPrettyString()).read("$.data.fileName").toString().equals("Ticket.pdf"));
    }
}
