package Steps.Inversiones;

import Config.Settings;
import Config.ValidarResponse;
import Steps.DobleFactor.DobleFactor;

import com.github.tomakehurst.wiremock.common.Json;
import com.jayway.jsonpath.JsonPath;
import constants.RequestMethods;
import core.PandoraRequest;
import core.PandoraResponse;
import io.qameta.allure.Step;

import java.util.List;
import java.util.Map;

import org.GP_Individuos_API.Utils;
import org.junit.Assert;
import steps.RequestSteps;

public class InversionesSteps {
    RequestSteps requestSteps = new RequestSteps();
    String host = System.getProperty("bankAPI");
    PandoraRequest postRequest = new PandoraRequest(host, RequestMethods.POST);
    Utils utils = new Utils();
    DobleFactor dobleFactor = new DobleFactor();
    ValidarResponse validarResponse = new ValidarResponse();
    private PandoraResponse resp;

    private String clientNumber,fundId,name,quotationId,forName,instrumentOrdCode, keyLongValue = null;
    private double quotation,quantityPartQuote = 0;

    //Seccion de  mapeo de servicios
    private PandoraResponse fixedTermsMakeSend(String currency, String fixedTermType, String amount, String term, String renewalType){
         Settings.endpoint = "/api/v1/execute/fixedTerms.make.send";
         postRequest.setHeaders("Content-Type", "application/json");
         postRequest.setHeaders("Authorization", Settings.bearerToken);
         postRequest.setPaths(Settings.endpoint);

         String body = utils.leerContenidoArchivo("./Requests/Inversiones/fixedTermsMakeSend.json");
         body = body.replace("_accountId_", Settings.idCuentaOrigen)
                 .replace("_currency_", currency)
                 .replace("_fixedTermType_", fixedTermType)
                 .replace("_renewalType_", renewalType)
                 .replace("\"_amount_\"", amount)
                 .replace("_term_", term);

         postRequest.setBody(utils.createBodyFromString(body));
         return requestSteps.executeRequest(postRequest);
     }

    private PandoraResponse foundSubscribe(int amount){
        Settings.endpoint = "/api/v1/execute/fund.subscribe";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Inversiones/fundSubscribe.json");
        body = body.replace("_commonInvestmentFundName_", name)
                .replace("\"_commonInvestmentFundId_\"", fundId)
                .replace("_commitentAccount_", clientNumber)
                .replace("\"_amount_\"", String.valueOf(amount))
                .replace("\"_quotation_\"", String.valueOf(amount/quotation))
                .replace("_debitProduct_", Settings.idCuentaOrigen);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse investmentFundRescue(int amount){
        Settings.endpoint = "/api/v1/execute/investment.fund.rescue";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Inversiones/investmentFundRescue.json");
        body = body.replace("\"_quantityPartQuote_\"", String.valueOf(quantityPartQuote))
                .replace("\"_quotation_\"", String.valueOf(quotation))
                .replace("_quotationId_", quotationId)
                .replace("_name_", name)
                .replace("\"_fundId_\"", fundId)  //instrumentorgcode
                .replace("_clientNumber_", clientNumber)
                .replace("\"_amount_\"", String.valueOf(amount))
                .replace("_creditProduct_", Settings.idCuentaOrigen)
                .replace("\"_instrumentId_\"", instrumentOrdCode); //crear un campo nuevo instrumentid

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse investmentListFundPre(){
        Settings.endpoint = "/api/v1/execute/investment.listFund.pre";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Compartidos/requestVacio.json");

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse buyDollarTransfer(){
        Settings.endpoint = "/api/v1/execute/buyselldollar.buyorselldollartransfer";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Inversiones/buyDollarTransfer.json");

        body = body.replace("_idProductDolar_", Settings.idCuentaDestino)
                    .replace("_idProductPesos_",Settings.idCuentaOrigen);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse sellDollarTransfer(){
        Settings.endpoint = "/api/v1/execute/buyselldollar.buyorselldollartransfer";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Inversiones/sellDollarTransfer.json");

        body = body.replace("_idProductDolar_", Settings.idCuentaOrigen)
                .replace("_idProductPesos_",Settings.idCuentaDestino);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse listDynamicFormFieldsCommitentAccount(){
        Settings.endpoint = "/api/v1/execute/list.dynamicFormFields.commitentAccount";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Compartidos/requestVacio.json");

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse investmentFundMovements(){
        Settings.endpoint = "/api/v1/execute/investment.fund.movements";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Inversiones/investmentFundMovements.json");

        body = body.replace("\"_fundId_\"", fundId)
                    .replace("_startDate_", utils.fechaTransferenciaMasAntigua("40"))
                    .replace("_endDate_", utils.fechaHoy())
                    .replace("_commitentAccount_", clientNumber);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse investmentMovementDownload(String key){
                Settings.endpoint = "/api/v1/execute/investment.movement.download";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Inversiones/investmentMovementDownload.json");

        body = body.replace("_formName_", forName)
                    .replace("_keyLongValue_", key);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse investmentsDataInnvestor(){
        Settings.endpoint = "/api/v1/execute/investments.data.investor";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Compartidos/requestVacio.json");

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse capitalMarketPrices(){
        Settings.endpoint = "/api/v1/execute/capital.market.prices";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Titulos/CapitalMarketPrices.json");

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

     //seccion de metodos publicos donde se consumen los servicios

    @Step("Generación de plazo fijo")
    public void generarPlazoFijo(String currency, String fixedTermType, String amount, String term, String renewalType){
         int intentos = 0;

         resp = fixedTermsMakeSend(currency, fixedTermType, amount, term, renewalType);
         validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                resp = fixedTermsMakeSend(currency, fixedTermType, amount, term, renewalType);
                //validamos el codigo de respuesta
                validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ SE GENERO EL PLAZO FIJO CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO GENERAR PLAZO FIJO INTENTO 4 VECES",intentos==4);
    }

    @Step("Subscripcion a FCI")
    public void suscribirFCI(int amount){
        int intentos = 0;

        resp = foundSubscribe(amount);
        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                resp = foundSubscribe(amount);
                //validamos el codigo de respuesta
                validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ SE GENERO EL PLAZO FIJO CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO GENERAR PLAZO FIJO INTENTO 4 VECES",intentos==4);
    }

    @Step("Generación de rescate de FCI")
    public void rescatarFCI(int amount){
        int intentos = 0;

        //Realizamos la operatoria que nos devuelve el valor de quantityPartQuote
        quantityPartQuote = amount/quotation;

        resp = investmentFundRescue(amount);
        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                resp = investmentFundRescue(amount);
                //validamos el codigo de respuesta
                validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ SE GENERO EL PLAZO FIJO CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO GENERAR PLAZO FIJO INTENTO 4 VECES",intentos==4);
    }

    @Step("Obtener datos para rescate FCI")
    public void obtenerDatosFCI(String FundName){
        int intentos = 0;

        //OBTENEMOS EL DATA INVESTOR CUENTA COMITENTE
        resp = investmentsDataInnvestor();
        validarResponse.validateResponses(resp);

        clientNumber = utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.accounts[0].number").toString());

        //servicio para sacar TNA y demas campòs necesarios para FCI
        resp = investmentListFundPre();
        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                resp = investmentListFundPre();
                //validamos el codigo de respuesta
                validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ SE RECUPERA LA INFORMACION CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO AL RECUPERAR LA INFORMACION INTENTO 4 VECES",intentos==4);

        //Se guarda en las variables la informacion del JSON, buscada mediante expresiones de jway, del response del servicio investment.listFund.pre.
        quotation = Double.parseDouble(utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.list[?(@.name == '"+FundName+"')].quotation").toString()));
        quotationId = utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.list[?(@.name == '"+FundName+"')].quotationId").toString());
        fundId = utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.list[?(@.name == '"+FundName+"')].idInstrument").toString());
        name = utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.list[?(@.name == '"+FundName+"')].name").toString());
        instrumentOrdCode = utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.list[?(@.name == '"+FundName+"')].instrumentOrdCode").toString());
    }

    @Step("Compra de USD")
    public void compraUSD(){
        int intentos = 0;

        resp = buyDollarTransfer();
        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                resp = buyDollarTransfer();
                //validamos el codigo de respuesta
                validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ SE COMPRARON USD CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO DE COMPRA DE USD INTENTO 4 VECES",intentos==4);
    }

    @Step("Venta de USD")
    public void ventaUSD(){
        int intentos = 0;

        resp = sellDollarTransfer();
        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                resp = sellDollarTransfer();
                //validamos el codigo de respuesta
                validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ SE VENDIERON USD CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO DE VENTA DE USD INTENTO 4 VECES",intentos==4);
    }

    @Step("ingreso a la card")
    public void ingresoALaSeccion(){
        int intentos=0;
        resp = investmentListFundPre();

        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                resp = investmentListFundPre();

                validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ SE VALIDA CORRECTAMENTE LA SECCION ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO INGRESO A LA SECCION FCI INTENTO 4 VECES",intentos==4);
    }

    @Step("ingreso de sección 'BAja de cuenta comitente' ")
    public void ingresoYValidacionBajaCuentaComitente(){
        int intentos=0;
        String telefono,mail;

        resp = listDynamicFormFieldsCommitentAccount();

        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                resp = listDynamicFormFieldsCommitentAccount();

                validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ SE VALIDA CORRECTAMENTE LA SECCION ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO INGRESO A LA SECCION BAJA DE CUENTA COMITENTE INTENTO 4 VECES",intentos==4);

        //validaciones de campos de baja de cuenta comitente por api
        mail = utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.dynamicFields[?(@.order == 1)].placeHolder").toString());
        System.out.println("CAMPLO PLACEHOLDER ==============  " + mail);
        Assert.assertTrue("No se encuentra el campo en la pantalla 'Correo'", (mail.contains("Mail") || mail.equals("Email")));
        telefono =  utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.dynamicFields[?(@.order == 2)].placeHolder").toString());
        Assert.assertTrue("No se encuentra el campo en la pantalla 'Telefono'", (telefono.equals("Telefono") || telefono.equals("Teléfono")));
        Assert.assertTrue("NO ES REQUERIDO REVISAR CAMBIO DE FUNCIONALIDAD",
        JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.dynamicFields[0].required").equals(true));
        Assert.assertTrue("NO ES REQUERIDO REVISAR CAMBIO DE FUNCIONALIDAD",
        JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.dynamicFields[1].required").equals(true));
    }

    @Step("Movimeintos de FCI seleccionado")
    public void movimientosFCI(){
        int intentos = 0;

        resp = investmentFundMovements();

        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                    resp = investmentFundMovements();

                    validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ SE VALIDA EL INGRESO A MOVIIENTOS DE FCI ===================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO INGRESO A LA SECCION MOVIMIENTOS FCI INTENTO 4 VECES",intentos==4);

    //validamos que los que aparezcan los campos que validamos por front y no sean vacios
    Assert.assertTrue("USUARIOS SIN MOVIMIENTOS", JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.movements",List.class).size()>0);

    Assert.assertTrue("Se no tiene status code ", !JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.movements[0].statusCode").toString().isEmpty());
    Assert.assertTrue("Se vnombre del investments ", !JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.movements[0].fund").toString().isEmpty());
    Assert.assertTrue("Se valida que tenga status code ", !JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.movements[0].statusCode").toString().isEmpty());

    //datos para la descargas
    forName= JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.movements[0].requestTemplate").toString();
    keyLongValue = JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.movements[0].keyLongValue").toString();

    }

    @Step("DEscargar el primer movieminto de FCI")
    public void descargaMovimientoFCI(String key){
        int intentos = 0;

        resp = investmentMovementDownload(key);

        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                    resp = investmentMovementDownload(key);

                    validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ SE VALIDA LA DESCARGA CORRECTAMENTE DEL MOVIMEINTO FCI ===================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO DESCARGA DE MOVIMIENTO DE FCI INTENTO 4 VECES",intentos==4);

        //VALIDAMOS QUE SE HAYA DESCARGADO VIENDO QUE LA RESPUESTA TRAIGA UN CONTENT CIFRADO
        Assert.assertTrue("NO SE DESCARGO EL ARCHIVO REVISAR SERVICIO", !JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.content").toString().isEmpty());
    }

    @Step("Validación de campos tabla Tipo Especie")
    public void validarTablaEspecie(){

        String especieDato, ultimoPrecioDato, varDato, precioDeCompraDato, precioDeVentaDato,
                volMontoDato;

        int intentos = 0;

        resp = capitalMarketPrices();
        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                resp = capitalMarketPrices();
                //validamos el codigo de respuesta
                validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ SE GENERO EL PLAZO FIJO CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO GENERAR PLAZO FIJO INTENTO 4 VECES",intentos==4);

        especieDato = String.valueOf(utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.prices.dataPaginated.elementList[0].description").toString()).length());
        ultimoPrecioDato = String.valueOf(utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.prices.dataPaginated.elementList[0].marketData.lastPrice").toString()).length());
        varDato = String.valueOf(utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.prices.dataPaginated.elementList[0].marketData.dailyVariation").toString()).length());
        precioDeCompraDato = String.valueOf(utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.prices.dataPaginated.elementList[0].marketData.purchasePrice").toString()).length());
        precioDeVentaDato = String.valueOf(utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.prices.dataPaginated.elementList[0].marketData.sellingPrice").toString()).length());
        volMontoDato = String.valueOf(utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.prices.dataPaginated.elementList[0].marketData.tradedVolume").toString()).length());

        Map<String, Object> elementEspecie = JsonPath.parse(resp.getBody().toPrettyString())
                .read("$.data.prices.dataPaginated.elementList[0]");
        Assert.assertTrue(
                "No existe el campo 'especie'",
                elementEspecie.containsKey("description"));

        Assert.assertTrue(
                "No trajo datos de Especie",
                especieDato == null || !especieDato.trim().isEmpty());

        Map<String, Object> elementUltimoPrecio = JsonPath.parse(resp.getBody().toPrettyString())
                .read("$.data.prices.dataPaginated.elementList[0].marketData");
        Assert.assertTrue(
                "No existe el campo 'Ultimo Precio'",
                elementUltimoPrecio.containsKey("lastPrice"));
        Assert.assertTrue(
                "No trajo datos de Ultimo Precio",
                ultimoPrecioDato == null || !ultimoPrecioDato.trim().isEmpty());

        Map<String, Object> elementVar = JsonPath.parse(resp.getBody().toPrettyString())
                .read("$.data.prices.dataPaginated.elementList[0].marketData");
        Assert.assertTrue(
                "No existe el campo 'Var%'",
                elementVar.containsKey("dailyVariation"));
        Assert.assertTrue(
                "No trajo datos de Var%",
                varDato == null || !varDato.trim().isEmpty());

        Map<String, Object> elementPrecioDeCompra = JsonPath.parse(resp.getBody().toPrettyString())
                .read("$.data.prices.dataPaginated.elementList[0].marketData");
        Assert.assertTrue(
                "No existe el campo 'Precio De Compra'",
                elementPrecioDeCompra.containsKey("purchasePrice"));
        Assert.assertTrue(
                "No trajo datos de Precio De Compra",
                precioDeCompraDato == null || !precioDeCompraDato.trim().isEmpty());

        Map<String, Object> elementPrecioDeVenta = JsonPath.parse(resp.getBody().toPrettyString())
                .read("$.data.prices.dataPaginated.elementList[0].marketData");
        Assert.assertTrue(
                "No existe el campo 'Precio De Venta'",
                elementPrecioDeVenta.containsKey("sellingPrice"));
        Assert.assertTrue(
                "No trajo datos de Precio De Venta",
                precioDeVentaDato == null || !precioDeVentaDato.trim().isEmpty());

        Map<String, Object> elementVolMonto = JsonPath.parse(resp.getBody().toPrettyString())
                .read("$.data.prices.dataPaginated.elementList[0].marketData");
        Assert.assertTrue(
                "No existe el campo 'Vol.Monto'",
                elementVolMonto.containsKey("tradedVolume"));
        Assert.assertTrue(
                "No trajo datos de Vol.Monto",
                volMontoDato == null || !volMontoDato.trim().isEmpty());
    }




}
