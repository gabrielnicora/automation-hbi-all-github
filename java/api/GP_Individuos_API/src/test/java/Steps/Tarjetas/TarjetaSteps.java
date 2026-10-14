package Steps.Tarjetas;

import Config.Settings;
import Config.ValidarResponse;
import Steps.DobleFactor.DobleFactor;

import com.github.tomakehurst.wiremock.common.Json;
import com.jayway.jsonpath.JsonPath;
import constants.RequestMethods;
import core.PandoraRequest;
import core.PandoraResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static Config.Settings.dobleFactor;

import java.math.BigDecimal;

import org.GP_Individuos_API.Utils;
import org.junit.Assert;
import steps.RequestSteps;

public class TarjetaSteps {
    RequestSteps requestSteps = new RequestSteps();
    String host = System.getProperty("bankAPI");
    PandoraRequest postRequest = new PandoraRequest(host, RequestMethods.POST);
    Utils utils = new Utils();
    DobleFactor dobleFactor = new DobleFactor();
    ValidarResponse validarResponse = new ValidarResponse();
    private PandoraResponse response;

    private double montoMinimo;

    private String cardBrand,cardType,idOrder, cuil,dni,offerDescription,debitConcept,
    debitCurrency,accountNumber,startDate,validDateFrom,validDateTo,shortLabel,idApplication,code= null;

    private BigDecimal buyLimit,dueBuyLimit,bonusAnualChargePeriod,duePaymentAnualRenewal,bonusPercentage;

    private Boolean pep,exteriorResidence,souif;

    //Seccion de  mapeo de servicios
    private PandoraResponse creditCardMakePayment(double importe){
        Settings.endpoint = "/api/v1/execute/creditCard.make.payment";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Tarjetas/creditCardMakePayment.json");

        body = body.replace("_debitCardId_", Settings.idDebitCard)
                    .replace("_creditCardId_",Settings.idCreditCard)
                    .replace("_accountId_",Settings.idCuentaOrigen)
                            .replace("\"_amount_\"",String.valueOf(importe));

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }
    private PandoraResponse creditCardRead(){
        Settings.endpoint = "/api/v1/execute/creditCard.read";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Tarjetas/creditCardRead.json");

        body = body.replace("_idProducto_",Settings.idCreditCard);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse creditCardListProductOffer(){
        Settings.endpoint = "/api/v1/execute/creditCard.list.product.offer";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Tarjetas/creditCardListProductOffer.json");

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse creditCardApplicationCreate(){
        Settings.endpoint = "/api/v1/execute/creditCard.application.create";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Tarjetas/CreditCardAplicationCreate.json");

        body = body.replace("_cardBrand_", cardBrand)
                    .replace("_cardType_", cardType)
                    .replace("_idOrder_", idOrder)
                    .replace("_cuil_", cuil)
                    .replace("_dni_", dni)
                    .replace("\"_buyLimit_\"", String.valueOf(buyLimit))
                    .replace("\"_dueBuyLimit_\"", String.valueOf(dueBuyLimit))
                    .replace("\"_bonusPercentage_\"",String.valueOf(bonusPercentage))
                    .replace("\"_bonusAnualChargePeriod_\"", String.valueOf(bonusAnualChargePeriod))
                    .replace("\"_duePaymentAnualRenewal_\"",String.valueOf(duePaymentAnualRenewal))
                    .replace("_offerDescription_", offerDescription)
                    .replace("_debitConcept_", debitConcept)
                    .replace("_debitCurrency_", debitConcept)
                    .replace("_accountNumber_", accountNumber)
                    .replace("_startDate_",startDate)
                    .replace("_validDateFrom_", validDateFrom)
                    .replace("_validDateTo_", validDateTo)
                    .replace("_shortLabel_", shortLabel)
                    .replace("_debitIdProduct_", Settings.idCuentaOrigen);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse creditCardApplicationUpdate(){
        Settings.endpoint = "/api/v1/execute/creditCard.application.update";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Tarjetas/CreditCardAplicationUpdate.json");

        body = body.replace("_cardBrand_", cardBrand)
                    .replace("_cardType_", cardType)
                    .replace("_idOrder_", idOrder)
                    .replace("_cuil_", cuil)
                    .replace("_dni_", dni)
                    .replace("\"_buyLimit_\"", String.valueOf(buyLimit))
                    .replace("\"_dueBuyLimit_\"", String.valueOf(dueBuyLimit))
                    .replace("\"_bonusPercentage_\"",String.valueOf(bonusPercentage))
                    .replace("\"_bonusAnualChargePeriod_\"", String.valueOf(bonusAnualChargePeriod))
                    .replace("\"_duePaymentAnualRenewal_\"",String.valueOf(duePaymentAnualRenewal))
                    .replace("_offerDescription_", offerDescription)
                    .replace("_debitConcept_", debitConcept)
                    .replace("_debitCurrency_", debitConcept)
                    .replace("_accountNumber_", accountNumber)
                    .replace("_startDate_",startDate)
                    .replace("_validDateFrom_", validDateFrom)
                    .replace("_validDateTo_", validDateTo)
                    .replace("_shortLabel_", shortLabel)
                    .replace("_debitIdProduct_", Settings.idCuentaOrigen)
                    .replace("\"_pep_\"", String.valueOf(pep))
                    .replace("\"_exteriorResidence_\"", String.valueOf(exteriorResidence))
                    .replace("\"_souif_\"", String.valueOf(souif));

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }
        private PandoraResponse creditCardApplicationConfirm(){
        Settings.endpoint = "/api/v1/execute/creditCard.application.confirm";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Tarjetas/creditCardApplicationConfirm.json");

        body = body.replace("_cardBrand_", cardBrand)
                    .replace("_cardType_", cardType)
                    .replace("_idOrder_", idOrder)
                    .replace("_cuil_", cuil)
                    .replace("_dni_", dni)
                    .replace("\"_buyLimit_\"", String.valueOf(buyLimit))
                    .replace("\"_dueBuyLimit_\"", String.valueOf(dueBuyLimit))
                    .replace("\"_bonusPercentage_\"",String.valueOf(bonusPercentage))
                    .replace("\"_bonusAnualChargePeriod_\"", String.valueOf(bonusAnualChargePeriod))
                    .replace("\"_duePaymentAnualRenewal_\"",String.valueOf(duePaymentAnualRenewal))
                    .replace("_offerDescription_", offerDescription)
                    .replace("_debitConcept_", debitConcept)
                    .replace("_debitCurrency_", debitConcept)
                    .replace("_accountNumber_", accountNumber)
                    .replace("_startDate_",startDate)
                    .replace("_validDateFrom_", validDateFrom)
                    .replace("_validDateTo_", validDateTo)
                    .replace("_shortLabel_", shortLabel)
                    .replace("_debitIdProduct_", Settings.idCuentaOrigen)
                    .replace("_code_", code)
                    .replace("_applicationId_",idApplication);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    @Step("Realizar pago de tarjeta de credito OTRO IMPORTE - VISA/MASTER")
    public void realizarPagoOtroImporte(){
        int intentos = 0;

        //realizamos el pago de tarjeta de otro importe 1
        response = creditCardMakePayment(1);
        //validamos que la api haya funcionado correctamente
        validarResponse.validateResponses(response);
        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                response = creditCardMakePayment(1);
                //validamos el codigo de respuesta
                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ SE GENERO EL PAGO CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO PAGAR TARJETA OTRO IMPORTE SE INTENTO 4 VECES",intentos==4);
    }

    @Step("Obtener el importe minimo de la tarjeta de credito a pagar")
    public void obtenerMontoMinimoAPagar(){
        int intentos = 0;

        //ejecuttamos el servcio que nos da el importe minimo de la tarjeta a pagar
        response = creditCardRead();
        //validamos que la api haya funcionado correctamente
        validarResponse.validateResponses(response);
        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                response = creditCardRead();
                //validamos el codigo de respuesta
                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ SE GENERO EL PAGO CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO PAGAR TARJETA OTRO IMPORTE SE INTENTO 4 VECES",intentos==4);

        montoMinimo = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.info.minimumPaymentArs");
    }
    @Step("Realizar pago MINIMOde credito OTRO IMPORTE - VISA/MASTER")
    public void realizarPagoMinimo(){
        int intentos = 0;

        //realizamos el pago de tarjeta de otro importe 1
        response = creditCardMakePayment(montoMinimo);
        //validamos que la api haya funcionado correctamente
        validarResponse.validateResponses(response);
        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                response = creditCardMakePayment(montoMinimo);
                //validamos el codigo de respuesta
                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ SE GENERO EL PAGO CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO PAGAR TARJETA OTRO IMPORTE SE INTENTO 4 VECES",intentos==4);
    }

    @Step("Obtener datos del producto tarjeta para solicitar")
    public void obtenerDatosProductoTarjeta(){

        int intentos = 0;

        response = creditCardListProductOffer();

        validarResponse.validateResponses(response);

                //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                response = creditCardListProductOffer();

                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ SE EJECUTO BIEN EL SERVICIO OFERTA DE TARJETA ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO DE PRODUCTO TARJETA SE INTENTO 4 VECES",intentos==4);

        Assert.assertTrue("USUARIO SIN OFERTAS", JsonPath.parse(response.getBody().toPrettyString()).read("$.data.productOffer.offers",List.class).size()>0);

        //DATOS PARA GESTIONAR LA NUEVA TARJETA
        cardBrand = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.productOffer.offers[0].idSubProduct1").toString());
        cardType = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.productOffer.offers[0].idSubProduct2").toString());
        idOrder = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.productOffer.offers[0].id").toString());
        cuil = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.productOffer.offers[0].cuil").toString());
        dni = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.productOffer.offers[0].dni").toString());
        buyLimit = new BigDecimal(utils.validarNullParametro(utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.productOffer.offers[0].dynamicOfferField.buyLimit").toString())));
        dueBuyLimit = new BigDecimal(utils.validarNullParametro(utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.productOffer.offers[0].dynamicOfferField.dueBuyLimit").toString())));
        bonusAnualChargePeriod = new BigDecimal(utils.validarNullParametro(utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.productOffer.offers[0].dynamicOfferField.bonusAnualChargePeriod").toString())));
        duePaymentAnualRenewal = new BigDecimal(utils.validarNullParametro(utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.productOffer.offers[0].dynamicOfferField.duePaymentAnualRenewal").toString())));
        offerDescription = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.productOffer.offers[0].offerDescription").toString());
        debitConcept = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.productOffer.offers[0].debitConcept").toString());
        debitCurrency = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.productOffer.offers[0].debitCurrency").toString());
        accountNumber = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.productOffer.offers[0].accountNumber").toString());
        startDate = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.productOffer.offers[0].startDate").toString());
        validDateFrom = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.productOffer.offers[0].validDateFrom").toString());
        validDateTo = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.productOffer.offers[0].validDateTo").toString());
        shortLabel = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.productOffer.offers[0].shortLabel").toString());
        //bonusPercentage = new BigDecimal(utils.validarNullParametro(utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.productOffer.offers[0].bonusPercentage").toString())));   
    }

    @Step("Generacion de Pedido de Tarjeta")
    public void generacionPedidoDeTarjeta(){
        int intentos = 0;
        String code = null;

     
        response = creditCardApplicationCreate();

        validarResponse.validateResponses(response);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){

               
                 response = creditCardApplicationCreate();

                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ SE GENERO  BIEN EL PEDIDO DE TARJETA ===================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO DE PEDIDO DE TARJETA INTENTO 4 VECES",intentos==4);

        idApplication = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.__applicationId"));
        
    }

    
    @Step("Validacion de Tipo de Personas para solicitud de Tarjetas")
    public void validarTipoDePersonaPedidoTarjeta(boolean expuesta, boolean exterior, boolean uif,String mensaje){
        int intentos = 0;
         
        pep = expuesta;
        exteriorResidence = exterior;
        souif = uif;

        response = creditCardApplicationUpdate();

        validarResponse.validateResponses(response);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){

             response = creditCardApplicationUpdate();

                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ SERVICIO OK  ===================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO DE TARJETA PARA TIPO DE PERSONA INTENTO 4 VECES",intentos==4);

        Assert.assertTrue("NO SE VERIFICA EL TIPO DE PERSONA ", JsonPath.parse(response.getBody().toPrettyString()).read("$.message").toString().contains(mensaje));
    }
    @Step("Generacion de Pedido de Tarjeta")
    public void confirmacionPedidoDeTarjeta(){
        int intentos = 0;


        code= dobleFactor.getSecondFactor();
     
        response = creditCardApplicationConfirm();

        validarResponse.validateResponses(response);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                code= dobleFactor.getSecondFactor();
     
                response = creditCardApplicationConfirm();

                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ SE CONFIRMO BIEN EL PEDIDO DE TARJETA ===================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO DE CONFIRMACION DE TARJETA INTENTO 4 VECES",intentos==4);
    }

    @Step("Validar datos Cuil y DNI que se usan en el request del servicio application/create")
    public void validarCuilYDNI(){

        Allure.addAttachment("Cuil ", String.valueOf(Settings.cuil));
        Allure.addAttachment("DNI ", String.valueOf(Settings.documentNumber));
        Assert.assertTrue("CUIL no coincide :",(Settings.cuil)==cuil );
        Assert.assertTrue("DNI no coincide :",(Settings.documentNumber)==dni );
    }

    @Step("Modificar tipo de Pago")
    public void updateTipoDePago(){
        int intentos = 0;
        String code = null;


        response = creditCardApplicationUpdate();

        validarResponse.validateResponses(response);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){


                response = creditCardApplicationCreate();

                validarResponse.validateResponses(response);
            }else{
                System.out.println("============ SE GENERO  BIEN EL PEDIDO DE TARJETA ===================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO DE PEDIDO DE TARJETA INTENTO 4 VECES",intentos==4);

        idApplication = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.__applicationId"));

    }
}
