package Steps.Pagos;

import Config.Settings;
import Config.ValidarResponse;
import Steps.DobleFactor.DobleFactor;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.tomakehurst.wiremock.common.Json;
import com.jayway.jsonpath.JsonPath;
import constants.RequestMethods;
import core.PandoraRequest;
import core.PandoraResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.GP_Individuos_API.Utils;
import org.junit.Assert;
import steps.RequestSteps;

import java.util.List;

public class PagosSteps {
    RequestSteps requestSteps = new RequestSteps();
    String host = System.getProperty("bankAPI");
    PandoraRequest postRequest = new PandoraRequest(host, RequestMethods.POST);
    Utils utils = new Utils();
    DobleFactor dobleFactor = new DobleFactor();
    ValidarResponse validarResponse = new ValidarResponse();
    private static final String SERVICIO_MERCADO_PAGO = "Mercado Pago";
    private static final String ENTE_CODE_MERCADO_PAGO = "970";
    private static final String USER_LP_MERCADO_PAGO = "00449535691";
    private static final String BELONGS_TO_TEST_AUTOMATION = "TestAutomation";
    private PandoraResponse resp;

    //SECCION PARA VARIABLES DE PRUEBAS DE DEBINES
    String idDebin, createDate,expirationDate;

    //Varables para eliminar servicios
    String adhesionCode;
    //aceptar debin buyer
    String holderBuyer,cuitBuyer,cbuBuyer,bankBuyer,branchBuyer,aliasBuyer,typeBuyer;
    //aceptar debin seller
    String holderSeller,cuitSeller,cbuSeller,bankSeller,branchSeller,aliasSeller,typeSeller;
    //obtenemos datos de pagos
    String enteCode;

    //Seccion de  mapeo de servicios
     private PandoraResponse debinSearch(String fecha){
         Settings.endpoint = "/api/v1/execute/debins.search";
         postRequest.setHeaders("Content-Type", "application/json");
         postRequest.setHeaders("Authorization", Settings.bearerToken);
         postRequest.setPaths(Settings.endpoint);


         String body = utils.leerContenidoArchivo("./Requests/Pagos/debinSearch.json");

         body = body.replace("_fechaFin_", fecha);

         postRequest.setBody(utils.createBodyFromString(body));

         return requestSteps.executeRequest(postRequest);
     }
     private PandoraResponse debinGenerate(String idProductCuenta, String cbuDestino, String cuitDestino){
         Settings.endpoint = "/api/v1/execute/debin.generate";
         postRequest.setHeaders("Content-Type", "application/json");
         postRequest.setHeaders("Authorization", Settings.bearerToken);
         postRequest.setPaths(Settings.endpoint);


         String body = utils.leerContenidoArchivo("./Requests/Pagos/debinGenerate.json");

         body = body.replace("_accountSelector_", idProductCuenta)
                    .replace("_buyCuit_",cuitDestino)
                    .replace("_cbuDestino_",cbuDestino);

         postRequest.setBody(utils.createBodyFromString(body));

         return requestSteps.executeRequest(postRequest);
     }
     private PandoraResponse debinsRecurrencySubscriptionSubscribe(String idProducto,String code2FA){
         Settings.endpoint ="/api/v1/execute/debins.recurrencySubscription.subscribe";
         postRequest.setHeaders("Content-Type", "application/json");
         postRequest.setHeaders("Authorization", Settings.bearerToken);
         postRequest.setPaths(Settings.endpoint);

         String body = utils.leerContenidoArchivo("./Requests/Pagos/debinsRecurrencySubscriptionSubscribe.json");

         body = body.replace("_idProductCuenta_",idProducto)
                 .replace("_code_",code2FA);

         postRequest.setBody(utils.createBodyFromString(body));

         return requestSteps.executeRequest(postRequest);
     }
     private PandoraResponse paymentsAddTaxService(String code2FA){
         Settings.endpoint =  "/api/v1/execute/payments.addTaxService";
         postRequest.setHeaders("Content-Type", "application/json");
         postRequest.setHeaders("Authorization", Settings.bearerToken);
         postRequest.setPaths(Settings.endpoint);

         String body = utils.leerContenidoArchivo("./Requests/Pagos/paymentsAddTaxService.json");

         body = body.replace("_idProductoTarjeta_", Settings.idDebitCard)
                 .replace("_debitCardLabel_",Settings.labelDebitCard)
                 .replace("_code_",code2FA);

         postRequest.setBody(utils.createBodyFromString(body));

         return requestSteps.executeRequest(postRequest);
     }
     private PandoraResponse debinsPendingList(){
         Settings.endpoint = "/api/v1/execute/debins.pending.list";
         postRequest.setHeaders("Content-Type", "application/json");
         postRequest.setHeaders("Authorization", Settings.bearerToken);
         postRequest.setPaths(Settings.endpoint);

         String body = utils.leerContenidoArchivo("./Requests/Pagos/debinsPendingList.json");

         postRequest.setBody(utils.createBodyFromString(body));

         return requestSteps.executeRequest(postRequest);
     }
     private PandoraResponse debinsRetrieveDetails(String idDebin){
         Settings.endpoint = "/api/v1/execute/debins.retrieve.details";
         postRequest.setHeaders("Content-Type", "application/json");
         postRequest.setHeaders("Authorization", Settings.bearerToken);
         postRequest.setPaths(Settings.endpoint);

         String body = utils.leerContenidoArchivo("./Requests/Pagos/debinsRetrieveDetails.json");

         body = body.replace("_idDebin_",idDebin);

         postRequest.setBody(utils.createBodyFromString(body));

         return requestSteps.executeRequest(postRequest);
     }
     private PandoraResponse debinsInformAcceptanceAccept(String idDebin,String createDate,String expirationDate,String code2FA,String cbu){
         Settings.endpoint = "/api/v1/execute/debins.inform.acceptance.accept";
         postRequest.setHeaders("Content-Type", "application/json");
         postRequest.setHeaders("Authorization", Settings.bearerToken);
         postRequest.setPaths(Settings.endpoint);

         String body = utils.leerContenidoArchivo("./Requests/Pagos/debinsInformAcceptanceAccept.json");

         body = body.replace("_idDebin_", idDebin)
                 .replace("_createDate_",createDate)
                 .replace("_expirationDate_",expirationDate)
                 .replace("_code_",code2FA)
                 .replace("_holderBuyer_",holderBuyer)
                 .replace("_cuitBuyer_",cuitBuyer)
                 .replace("_cbuBuyer_",cbuBuyer)
                 .replace("_bankBuyer_",bankBuyer)
                 .replace("_branchBuyer_",branchBuyer)
                 .replace("_aliasBuyer_",aliasBuyer)
                 .replace("_typeBuyer_", typeBuyer)
                 .replace("_holderSeller_",holderSeller)
                 .replace("_cuitSeller_",cuitSeller)
                 .replace("_cbuSeller_",cbuSeller)
                 .replace("_bankSeller_",bankSeller)
                 .replace("_branchSeller_",branchSeller)
                 .replace("_aliasSeller_",aliasSeller)
                 .replace("_typeSeller_", typeSeller)
                 .replace("_idProducto_",Settings.idDebitCard);

         postRequest.setBody(utils.createBodyFromString(body));

         return requestSteps.executeRequest(postRequest);
     }
     private PandoraResponse firstPaymentRequest(){
         Settings.endpoint = "/api/v1/execute/firstPayment.request";
         postRequest.setHeaders("Content-Type", "application/json");
         postRequest.setHeaders("Authorization", Settings.bearerToken);
         postRequest.setPaths(Settings.endpoint);

         String body = utils.leerContenidoArchivo("./Requests/Pagos/firstPaymentRequest.json");

         body = body.replace("_idProductoCuenta_",Settings.idCuentaOrigen)
                 .replace("_idProductoTarjeta_",Settings.idDebitCard)
                 .replace("_conceptCode_",enteCode)
                 .replace("_adhesionCode_",adhesionCode);

         postRequest.setBody(utils.createBodyFromString(body));

         return requestSteps.executeRequest(postRequest);
     }
     private PandoraResponse paymentsQueryAdhesions(){
         Settings.endpoint = "/api/v1/execute/payments.query.adhesions";
         postRequest.setHeaders("Content-Type", "application/json");
         postRequest.setHeaders("Authorization", Settings.bearerToken);
         postRequest.setPaths(Settings.endpoint);

         String body = utils.leerContenidoArchivo("./Requests/Pagos/paymentsQueryAdhesions.json");

         body = body.replace("_idProducto_",Settings.idDebitCard);

         postRequest.setBody(utils.createBodyFromString(body));

         return requestSteps.executeRequest(postRequest);
     }
     private PandoraResponse paymentsUnsubscribeAdheredService(){
         Settings.endpoint = "/api/v1/execute/payments.unsubscribe.adhered.service";
         postRequest.setHeaders("Content-Type", "application/json");
         postRequest.setHeaders("Authorization", Settings.bearerToken);
         postRequest.setPaths(Settings.endpoint);

         String body = utils.leerContenidoArchivo("./Requests/Pagos/paymentsUnsubscribeAdheredService.json");
         Assert.assertTrue("No se pudo armar el request de baja: adhesionCode esta vacio o no fue obtenido",
                 adhesionCode != null && !adhesionCode.trim().isEmpty());

         body =  body.replace("_idProducto_",Settings.idDebitCard)
                 .replace("_adhesionCode_",adhesionCode);

         postRequest.setBody(utils.createBodyFromString(body));

         return requestSteps.executeRequest(postRequest);
     }
     private PandoraResponse firstPaymentAddEntityByCpe(String code){
         Settings.endpoint = "/api/v1/execute/firstPayment.addEntityByCpe";
         postRequest.setHeaders("Content-Type", "application/json");
         postRequest.setHeaders("Authorization", Settings.bearerToken);
         postRequest.setPaths(Settings.endpoint);

         String body = utils.leerContenidoArchivo("./Requests/Pagos/firstPaymentAddEntityByCpe.json");

         body =  body.replace("_idProductoTarjeta_",Settings.idDebitCard)
                 .replace("_code_",code);

         postRequest.setBody(utils.createBodyFromString(body));

         return requestSteps.executeRequest(postRequest);
     }
     public PandoraResponse paymentSyncLink(){
         Settings.endpoint = "/api/v1/execute/payment.sync.link";
         postRequest.setHeaders("Content-Type", "application/json");
         postRequest.setHeaders("Authorization", Settings.bearerToken);
         postRequest.setPaths(Settings.endpoint);

         String body = utils.leerContenidoArchivo("./Requests/Compartidos/requestVacio.json");

         postRequest.setBody(utils.createBodyFromString(body));

         return requestSteps.executeRequest(postRequest);
     }
    public PandoraResponse paymentIdCardLink(){
        Settings.endpoint = "/api/v1/execute/payment.idCard.link";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Compartidos/requestVacio.json");

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse paymentsMadeList(String isExport,String formato){
        Settings.endpoint = "/api/v1/execute/payments.made.list";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Pagos/paymentsMadeList.json");

        body = body.replace("_idProductDebitCard_",Settings.idDebitCard)
                    .replace("_startDate_",utils.fechaTransferenciaMasAntigua("30"))
                    .replace("_endDate_",utils.fechaHoy())
                    .replace("\"_isExport_\"", isExport)
                    .replace("_cdpExportFileFormat_",formato);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }
    private PandoraResponse fileDownload(){
        Settings.endpoint = "/api/v1/execute/files.download";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Pagos/fileDownload.json");

        body = body.replace("\"_idFile_\"",Settings.idFile);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse generateDebinSearch(String cbu, String tipo) {
        Settings.endpoint = "/api/v1/execute/debin.generate.search";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Pagos/debinGenerateSearch.json");
        body = body.replace("_aliasCbu_", cbu)
                .replace("_typeSelector_", tipo);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse generateDebinSearchTypeNull(String cbu) {
        Settings.endpoint = "/api/v1/execute/debin.generate.search";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Pagos/debinGenerateSearchNull.json");
        body = body.replace("_cbu_", cbu);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse generateDebinSearchSearchTrueFalseAliasCbu(String cbu, boolean tipo) {
        Settings.endpoint = "/api/v1/execute/debin.generate.search";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Pagos/debinGenerateSearch.json");

        body = body.replace("_aliasCbu_", String.valueOf(cbu))
                .replace("\"_typeSelector_\"", String.valueOf(tipo));

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }


    //seccion de metodos publicos donde se consumen los servicios

    @Step("Consulta de Debin Historicos")
    public void realizarConsultaDebinHistorico(){
         PandoraResponse response;
         //setea la fecha y hora acutal
         String fecha = utils.fechaHoy();
         //Se consume el servicio de consulta debin historicos
        response = debinSearch(fecha);
        //valida el response con el metodo validate response
        validarResponse.validateResponses(response);
        //Assertion
        int cantidad = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.paginatedData.paginatedList.elementList", List.class).size();
        Allure.addAttachment("Cantidad de debines encontrados",String.valueOf(cantidad));
        Assert.assertTrue("NO se encontraron debines historicos", cantidad>0);
    }
    @Step("Generación de Debin")
    public void generarDebin(String cbuDestino, String cuitDestino){
        PandoraResponse response;
        int intentos = 0;

        //ejecutamos el servicio que genera debin
        response = debinGenerate(Settings.idCuentaOrigen,cbuDestino,cuitDestino);
        //valida el response con el metodo validate response
        validarResponse.validateResponses(response);
        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                response = debinGenerate(Settings.idCuentaOrigen,cbuDestino,cuitDestino);
                //valida el response con el metodo validate response
                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ SE GENERO EL DEBIN CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO GENERAR DEBIN SE INTENTO 4 VECES",intentos==4);
    }
    @Step("Adherir Recurrencia API")
    public void adherirRecurrencia(){
         PandoraResponse response;
         int intentos = 0;
         String codigo;

         //Ejecutamos el codigo doble factor
        codigo =  dobleFactor.getSecondFactor();
        //ejecutamos servicio adherir recurrencia
        response =  debinsRecurrencySubscriptionSubscribe(Settings.idCuentaOrigen, codigo);
        //validamos el codigo de respuesta
        validarResponse.validateResponses(response);
        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                codigo =  dobleFactor.getSecondFactor();
                response = debinsRecurrencySubscriptionSubscribe(Settings.idCuentaOrigen, codigo);
                //validamos el codigo de respuesta
                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ SE GENERO LA ADHESION DE RECURRENCIA CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO AHDERIR RECURRENCIA SE INTENTO 4 VECES",intentos==4);
    }
    @Step("Adherir nuevo servicio api")
    public void adherirSerivicio(){
         PandoraResponse response;
         String codigo;
         int intentos = 0;

        //Ejecutamos el codigo doble factor
        codigo =  dobleFactor.getSecondFactor();
        //ejecutamos el serivico de adhesion de nuevo pago o servicio
        response = paymentsAddTaxService(codigo);
        //validamos el repsonse del servicio
        validarResponse.validateResponses(response);
        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                codigo =  dobleFactor.getSecondFactor();
                response = paymentsAddTaxService(codigo);
                //validamos el codigo de respuesta
                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ SE GENERO LA ADHESION DEL SERVICIO CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO AHDERIR SERVICIO SE INTENTO 4 VECES",intentos==4);
    }
    @Step("Extracción de idDebin ya generado en caso anterior")
    public void obtenerIdDebin(){
         PandoraResponse response;
         int intentos = 0;
         //ejevcutamos el servicio de listados de debines pendientes
        response =  debinsPendingList();
        //validamos el serivicio con la clase validate Response
        validarResponse.validateResponses(response);
        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                response = debinsPendingList();
                //validamos el codigo de respuesta
                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ SE OBTUVO LOS DEBINES PENDIENTES CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO BUSQUEDA DE DEBINES PENDIENTES SE INTENTO 4 VECES",intentos==4);
        //obtenemos el idDebin
        idDebin = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.debins[?(@.status == 'PENDIENTE')].id",List.class).get(0).toString());
    }
    @Step("Obtener datos del Debin - createDate - expirationDate")
    public void obtenerDatosIdDebin(){
        PandoraResponse response;
        int intentos = 0;
        //ejecución del servicio retrieve para poder obtener la informacion del idDebin
        response = debinsRetrieveDetails(idDebin);
        //validamos el serivicio con la clase validate Response
        validarResponse.validateResponses(response);
        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                response = debinsRetrieveDetails(idDebin);
                //validamos el codigo de respuesta
                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ SE OBTUVO LA INFORMACION DEL DEBIN CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO RETRIEVE DEBIN SE INTENTO 4 VECES",intentos==4);
        //obtenemos los valores del createDate y expirationDate
        createDate = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.details.creationDate").toString());
        expirationDate = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.details.expirationDate").toString());
        //BUYERRR
        holderBuyer =  utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.details.buyer.holder").toString());
        cuitBuyer = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.details.buyer.cuit").toString());
        cbuBuyer = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.details.buyer.account.cbu").toString());
        bankBuyer = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.details.buyer.account.bank").toString());
        branchBuyer = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.details.buyer.account.branch").toString());
        aliasBuyer = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.details.buyer.account.alias").toString());
        typeBuyer = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.details.buyer.account.type").toString());
        //SELLER
        holderSeller =  utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.details.seller.holder").toString());
        cuitSeller = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.details.seller.cuit").toString());
        cbuSeller = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.details.seller.account.cbu").toString());
        bankSeller = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.details.seller.account.bank").toString());
        branchSeller = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.details.seller.account.branch").toString());
        aliasSeller = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.details.seller.account.alias").toString());
        typeSeller = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.details.seller.account.type").toString());

    }

    @Step("Aceptar Debin API")
    public void aceptarDebinGenerado(String cbu){
         //Establecemos las variables que necesitamos
        PandoraResponse response;
        String codigo;
        int intentos = 0;
        //Obtenemos el segundo factor
        codigo = dobleFactor.getSecondFactor();
        //ejecutamos el servicio de aceptarDebin
        response = debinsInformAcceptanceAccept(idDebin,createDate, expirationDate,codigo,cbu);
        //validamos el serivicio con la clase validate Response
        validarResponse.validateResponses(response);
        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                codigo =  dobleFactor.getSecondFactor();
                response = debinsInformAcceptanceAccept(idDebin,createDate, expirationDate,codigo,cbu);
                //validamos el codigo de respuesta
                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ SE ACEPTO EL DEBIN CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO ACEPTAR DEBIN SE INTENTO 4 VECES",intentos==4);
    }
    @Step("Obtener datos para pagos")
    public void obtenerDatosPagos(){
         PandoraResponse response;
         String codigo;
         int intentos = 0;
         //doble factor
        codigo = dobleFactor.getSecondFactor();
         //ejevcutamos el servicio para obtener datos
        response = firstPaymentAddEntityByCpe(codigo);
        //validamos el  response
        validarResponse.validateResponses(response);
        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                //doble factor
                codigo = dobleFactor.getSecondFactor();
                //ejevcutamos el servicio para obtener datos
                response = firstPaymentAddEntityByCpe(codigo);
                //validamos el  response
                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ SE EJECUTO EL SERVICIO CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO  OBTNER DATOS PARA REALIZAR PAGO",intentos==4);
        //obtenemos el datos de enteCode - adhesionCode
        enteCode = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.enteConcepts[?(@.description == 'MLIBRE FACT')].conceptCode").toString());
        adhesionCode = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.resultWithoutBase.adhesion.adhesionCode").toString());
    }

    @Step("Pagar servicio de mercado libre")
    public void pagarServicio(){
         PandoraResponse response;
         int intentos = 0;

         //ejecutmaos el servicio de pagos de servicio
        response = firstPaymentRequest();
        //validamos el serivicio con la clase validate Response
        validarResponse.validateResponses(response);
        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                response = firstPaymentRequest();
                //validamos el codigo de respuesta
                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ SE PAGO EL SERVICIO CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO  PAGO DE IMPUESTO SERVICIO INTENTO 4 VECES",intentos==4);
    }

    @Step("Extraer adhesion code para eliminar el servicio")
    public void obtenerAdhesionCodeServicio(){
        obtenerAdhesionCodeServicio(ENTE_CODE_MERCADO_PAGO, USER_LP_MERCADO_PAGO, BELONGS_TO_TEST_AUTOMATION);
    }

    private String buscarAdhesionCode(PandoraResponse response, String enteCodeEsperado, String userLpEsperado, String belongsToEsperado){
        for (JsonNode node : response.getBody().at("/data/adhesions")) {
            String enteCode = node.path("enteCode").asText("");
            String userLp = node.path("userLp").asText("");
            String belongsTo = node.path("belongsTo").asText("");
            String code = node.path("adhesionCode").asText("");

            if (enteCode.equals(enteCodeEsperado)
                    && userLp.equals(userLpEsperado)
                    && belongsTo.equals(belongsToEsperado)
                    && !code.trim().isEmpty()) {
                return code;
            }
        }
        return null;
    }

    @Step("Extraer adhesion code para eliminar el servicio: {enteCodeEsperado} - {userLpEsperado} - {belongsToEsperado}")
    public void obtenerAdhesionCodeServicio(String enteCodeEsperado, String userLpEsperado, String belongsToEsperado){
         PandoraResponse response;
         int intentos = 0;
         adhesionCode = null;
         //ejecutamos el serivicio paymentsQueryAdhesion para saber que servicios tenemos agregados para este usuarios
        response = paymentsQueryAdhesions();
        //validamos el serivicio con la clase validate Response
        validarResponse.validateResponses(response);
        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                response = paymentsQueryAdhesions();
                //validamos el codigo de respuesta
                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ SE OBTUBO INFORMACION DEL LOS SERVICIOS AGENDADOS  CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO  PAYMENTS QUERY ADHESION INTENTO 4 VECES",intentos==4);
        //OBTENEMOS EL ADHESIONCODE
        adhesionCode = buscarAdhesionCode(response, enteCodeEsperado, userLpEsperado, belongsToEsperado);
        if(adhesionCode == null || adhesionCode.trim().isEmpty()){
            Allure.addAttachment("payments.query.adhesions sin adhesion esperada", response.getBody().toPrettyString());
            System.out.println("============ NO SE ENCONTRO LA ADHESION ESPERADA, SE CREA EL SERVICIO PARA PODER ELIMINARLO ====================");
            adherirSerivicio();
            response = paymentsQueryAdhesions();
            validarResponse.validateResponses(response);
            adhesionCode = buscarAdhesionCode(response, enteCodeEsperado, userLpEsperado, belongsToEsperado);
        }
        if(adhesionCode == null || adhesionCode.trim().isEmpty()){
            Allure.addAttachment("payments.query.adhesions response", response.getBody().toPrettyString());
            Assert.fail("No se encontro adhesionCode para eliminar el servicio '" + SERVICIO_MERCADO_PAGO
                    + "' con enteCode '" + enteCodeEsperado
                    + "', userLp '" + userLpEsperado
                    + "' y belongsTo '" + belongsToEsperado
                    + "' en la tarjeta " + Settings.idDebitCard);
        }
        System.out.println("==================== ADHESION CODE "+ adhesionCode);
    }
    @Step("Eliminar Servicio API")
    public void eliminarServicio(){
         PandoraResponse response;
         int intentos = 0;
         //Ejecucion eliminar servicio
        response = paymentsUnsubscribeAdheredService();
        //validamos el codigo de respuesta
        validarResponse.validateResponses(response);
        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                response = paymentsUnsubscribeAdheredService();
                //validamos el codigo de respuesta
                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ SE ELIMINO EL SERVICIO  CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO  ELIMINAR SERIVICIO INTENTO 4 VECES",intentos==4);
    }
    @Step("Sincronizacion de PAGOS LINK-API")
    public void sincronizacionPagosLink(){
         PandoraResponse response;
         int intentos =0;
         //SE ejecuta el servicio para sincronizar los servicios par API LINK
        response = paymentIdCardLink();
        //Evaluamos la respuesta del servicio
        validarResponse.validateResponses(response);
        //response = paymentSyncLink();
        //Evaluamos la respuesta del servicio
        //validarResponse.validateResponses(response);
        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                //SE ejecuta el servicio para sincronizar los servicios par API LINK
                response = paymentIdCardLink();
                //Evaluamos la respuesta del servicio
                validarResponse.validateResponses(response);
                //response = paymentSyncLink();
                //Evaluamos la respuesta del servicio
                //validarResponse.validateResponses(response);
            }else{
                System.out.println("============ SE SINCRONIZO LOS SERVICIOS API-LINK ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO  SINCRONIZACION DE CUENTAS INTENTO 4 VECES",intentos==4);
    }

    @Step("Consultas de comprobante de pagos")
    public void comprobantePagos(String isExport,String formato){
        PandoraResponse response;
         int intentos = 0;
         //Ejecucion consulta de comprobante de pagos
        response = paymentsMadeList(isExport,formato);
        //validamos el codigo de respuesta
        validarResponse.validateResponses(response);
        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                         //Ejecucion consulta de comprobante de pagos
                    response = paymentsMadeList(isExport,formato);
                //validamos el codigo de respuesta
                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ SE EJECUTO EL SERVICIO  CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO  CONSULTA DE COMPROBANTE DE PAGOS INTENTO 4 VECES",intentos==4);
        if(isExport.equals("")){
            Assert.assertTrue("SIN COMPROBANTE DE PAGO REVISAR ", JsonPath.parse(response.getBody().toPrettyString()).read("$.data.paginatedData.paginatedList.elementList", List.class).size()>0);

        }else{
            System.out.println("==================== DESCARGA DE PERIODO DE PAGOS ==========================");
        }
    }

    @Step("Descarga de pagos por PERIODO PDF/XLS")
    public void descargaDeComprobantePeriodo(String fileName){
        PandoraResponse response;
        int intentos = 0;
        //Ejecucion descarga de archivos por periodo
        response = fileDownload();
        //validamos el codigo de respuesta
        validarResponse.validateResponses(response);
        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                //Ejecucion descarga de archivos por periodo
                response = fileDownload();
                //validamos el codigo de respuesta
                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ SE EJECUTO EL SERVICIO  CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO  CONSULTA DE COMPROBANTE DE PAGOS INTENTO 4 VECES",intentos==4);

        Assert.assertTrue("SIN COMPROBANTE DE PAGO REVISAR ", JsonPath.parse(response.getBody().toPrettyString()).read("$.data.fileName").equals(fileName));
     }

    @Step("Validar Response del servicio /debin.generate.search")
    public void validarErroresDebinGenerateSearch(String aliasOCbu, String tipo) {


        resp = generateDebinSearch(aliasOCbu, tipo);

        String code = JsonPath.parse(resp.getBody().toPrettyString()).read("$.code").toString();
        String message = JsonPath.parse(resp.getBody().toPrettyString()).read("$.message").toString();
        String codigoError = "COR020W";
        String codigoError2 = "API946W";

        Assert.assertNotNull("La respuesta no debe ser nula.", resp);
        Assert.assertEquals("El código de estado debería ser 200.", 200, resp.getStatusCode());
        Assert.assertTrue(
                "Transacción realizada",
                code.equals(codigoError) || code.equals(codigoError2)
        );

        System.out.println("+++++++++++++++  MENSAJE DE ERROR +++++++++++++++ ");
        System.out.println("/*/*/*/ STATUS CODE /*/*/*/ " + resp.getStatusCode());
        System.out.println("/*/*/*/ STATUS CODE ERROR /*/*/*/ " + code);
        System.out.println("/*/*/*/ MENSAJE DE ERROR /*/*/*/ " + message);
    }

    @Step("Validar Response del servicio /debin.generate.search")
    public void validarErroresGenerateDebinSearchTipoNull(String aliasOCbu) {

        resp = generateDebinSearchTypeNull(aliasOCbu);

        String code = JsonPath.parse(resp.getBody().toPrettyString()).read("$.code").toString();
        String message = JsonPath.parse(resp.getBody().toPrettyString()).read("$.message").toString();
        String codigoError = "COR020W";
        String codigoError2 = "API946W";

        Assert.assertNotNull("La respuesta no debe ser nula.", resp);
        Assert.assertEquals("El código de estado debería ser 200.", 200, resp.getStatusCode());
        Assert.assertTrue(
                "Transacción realizada",
                code.equals(codigoError) || code.equals(codigoError2)
        );

        System.out.println("+++++++++++++++  MENSAJE DE ERROR +++++++++++++++ ");
        System.out.println("/*/*/*/ STATUS CODE /*/*/*/ " + resp.getStatusCode());
        System.out.println("/*/*/*/ STATUS CODE ERROR /*/*/*/ " + code);
        System.out.println("/*/*/*/ MENSAJE DE ERROR /*/*/*/ " + message);
    }

    @Step("Validar Response del servicio /debin.generate.search")
    public void validarErroresDebinGenerateSearchTrueFalseAliasCbu(String aliasOCbu, boolean tipo) {

        resp = generateDebinSearchSearchTrueFalseAliasCbu(aliasOCbu, tipo);

        String code = JsonPath.parse(resp.getBody().toPrettyString()).read("$.code").toString();
        String message = JsonPath.parse(resp.getBody().toPrettyString()).read("$.message").toString();
        String codigoError = "COR020W";
        String codigoError2 = "API946W";

        Assert.assertNotNull("La respuesta no debe ser nula.", resp);
        Assert.assertEquals("El código de estado debería ser 200.", 200, resp.getStatusCode());
        Assert.assertTrue(
                "Dato ingresado correctamente",
                code.equals(codigoError) || code.equals(codigoError2)
        );

        System.out.println("+++++++++++++++  MENSAJE DE ERROR +++++++++++++++ ");
        System.out.println("/*/*/*/ STATUS CODE /*/*/*/ " + resp.getStatusCode());
        System.out.println("/*/*/*/ STATUS CODE ERROR /*/*/*/ " + code);
        System.out.println("/*/*/*/ MENSAJE DE ERROR /*/*/*/ " + message);
    }
}
