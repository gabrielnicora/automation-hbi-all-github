package Steps.Compartidos;

import Config.Settings;
import Config.ValidarResponse;
import com.jayway.jsonpath.JsonPath;
import constants.RequestMethods;
import core.PandoraRequest;
import core.PandoraResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.GP_Individuos_API.Utils;
import steps.RequestSteps;


import static Config.Settings.*;

public class CompartidosAPI {

    RequestSteps requestSteps = new RequestSteps();
    String host = System.getProperty("bankAPI");
    PandoraRequest postRequest = new PandoraRequest(host, RequestMethods.POST);
    Utils utils = new Utils();
    ValidarResponse validarResponse = new ValidarResponse();
    public PandoraResponse responseDatosCuenta,responseDatosTarjetas,response;

    private PandoraResponse getWitgetsCreditCards(){
        endpoint = "/api/v1/execute/widgets.creditCards";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Compartidos/requestVacio.json");

        postRequest.setBody(utils.createBodyFromString(body));

        return  requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse getWidgetsAccount(){
        endpoint = "/api/v1/execute/widgets.accounts";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization",  Settings.bearerToken);
        postRequest.setPaths(endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Compartidos/requestVacio.json");

        postRequest.setBody(utils.createBodyFromString(body));

        return  requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse getTermsAndConditionAnonymousForFunctionality(){
        endpoint = "/api/v1/execute/get.temsAndConditions.anonymous.ForFunctionality";
        postRequest.setHeaders("Content-Type", "application/json");
        //postRequest.setHeaders("Authorization",  Settings.bearerToken);
        postRequest.setPaths(endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Compartidos/getTermsAndConditionsAnonymousForFunctionality.json");

        postRequest.setBody(utils.createBodyFromString(body));

        return  requestSteps.executeRequest(postRequest);
    }

    @Step("Carga de servicios para obtencion de datos del usuarios - widgetAccount - widgetCreditCards")
    public void cargaServiciosCompartidos(){
        responseDatosCuenta = getWidgetsAccount();
        validarResponse.validateResponses(responseDatosCuenta);
        responseDatosTarjetas = getWitgetsCreditCards();
        validarResponse.validateResponses(responseDatosTarjetas);
    }
    @Step("Obtener un idProduct de una cuenta - Cuenta: {cuenta}")
    public void obtenerIdDeCuentaOrigen(String cuenta){

            Settings.idCuentaOrigen = utils.quitarCochetesJsonPath(JsonPath.parse(responseDatosCuenta.getBody().toPrettyString()).read("$.data.accounts[?(@.shortLabel == '"+cuenta+"')].idProduct").toString());
            Allure.addAttachment("idCuentaOrigen",Settings.idCuentaOrigen);
    }

    @Step("Mostrar Nro de Cuenta Emisor - Cuenta: {cuenta}")
    public void mostrarNumeroCtaOrigen(String cuenta){
        ctaOrigen = cuenta;
        Allure.addAttachment("Cuenta Origen del Emisor", ctaOrigen);
    }


    @Step("Obtener un idProduct de una cuenta - Cuenta: {cuenta}")
    public void obtenerIdDeCuentaDestino(String cuenta){

            Settings.idCuentaDestino = utils.quitarCochetesJsonPath(JsonPath.parse(responseDatosCuenta.getBody().toPrettyString()).read("$.data.accounts[?(@.shortLabel == '"+cuenta+"')].idProduct").toString());
            Allure.addAttachment("idCuentaDestino",Settings.idCuentaDestino);

    }
    @Step("Obtener el cbu de la cuenta - Cuenta : {cuenta}")
    public  void obtenerCbuCuenta(String cuenta){
        Settings.cbuTarget = utils.quitarCochetesJsonPath(JsonPath.parse(responseDatosCuenta.getBody().toPrettyString()).read("$.data.accounts[?(@.shortLabel == '"+cuenta+"')].cbu").toString());
        Allure.addAttachment("cbuTarget", Settings.cbuTarget);
    }
    @Step("Obtener un idProduct de una tarjeta de Debito - Card: {card}")
    public void obtenerIdDeTarjeta(String card){

        Settings.idDebitCard = utils.quitarCochetesJsonPath(JsonPath.parse(responseDatosTarjetas.getBody().toPrettyString()).read("$.data.creditCards[?(@.label == 'Visa Débito **** **** **** "+card+"')].idProduct").toString());
        Allure.addAttachment("idTarjetaDebito",Settings.idDebitCard);
    }
    @Step("Obtener un idProduct de una tarjeta de Credito - Card: {card}")
    public void obtenerIdDeTarjetaCredito(String card){

        Settings.idCreditCard = utils.quitarCochetesJsonPath(JsonPath.parse(responseDatosTarjetas.getBody().toPrettyString()).read("$.data.creditCards[?(@.number == '**** **** **** "+card+"')].idProduct").toString());
        Allure.addAttachment("idTarjetaDebito", idCreditCard);
    }
    @Step("Obtener un label de una tarjeta de Debito - Card: {card}")
    public void obtenerLabelDeTarjeta(String card){

        Settings.labelDebitCard = utils.quitarCochetesJsonPath(JsonPath.parse(responseDatosTarjetas.getBody().toPrettyString()).read("$.data.creditCards[?(@.label == 'Visa Débito **** **** **** "+card+"')].label").toString());
        Allure.addAttachment("labelTarjetaDebito",Settings.labelDebitCard);
    }
    @Step("Obtener Monto Actual de la cuenta Origen : {cuenta}")
    public void obtenerMontoCuentaActualOrigen(String cuenta){
        if(cuenta.contains("USD")){

            Settings.montoActual = Integer.parseInt(utils.validarNullParametro(utils.quitarCochetesJsonPath(JsonPath.parse(responseDatosCuenta.getBody().toPrettyString()).read("$.data.accounts[?(@.shortLabel == '"+cuenta+"')].labelWithBalance").toString()).split("USD ")[2].split(",")[0].replace(".","")));
            System.out.println("========== MONTO DE LA CUENTA "+cuenta+ " :" + Settings.montoActual);
            Allure.addAttachment("Monto Acutal:", String.valueOf(Settings.montoActual));
        }else{

            Settings.montoActual = Long.parseLong(
                    utils.validarNullParametro(
                            utils.quitarCochetesJsonPath(
                                    JsonPath.parse(responseDatosCuenta.getBody().toPrettyString())
                                            .read("$.data.accounts[?(@.shortLabel == '"+cuenta+"')].labelWithBalance")
                                            .toString()
                            ).split("\\$ ")[2].split(",")[0].replace(".", "")
                    )
            );
            System.out.println("========== MONTO DE LA CUENTA "+cuenta+ " :" + Settings.montoActual);
            Allure.addAttachment("Monto Acutal:", String.valueOf(Settings.montoActual));

        }
    }
    @Step("Obtener Monto Actual de la cuenta Destino : {cuenta}")
    public void obtenerMontoCuentaActualDestino(String cuenta){
        if(cuenta.contains("USD")){

            Settings.montoActualDestino = Integer.parseInt(utils.quitarCochetesJsonPath(JsonPath.parse(responseDatosCuenta.getBody().toPrettyString()).read("$.data.accounts[?(@.shortLabel == '"+cuenta+"')].labelWithBalance").toString()).split("USD ")[2].split(",")[0].replace(".",""));
            System.out.println("========== MONTO DE LA CUENTA "+cuenta+ " :" + Settings.montoActualDestino);
            Allure.addAttachment("Monto Acutal Destino:", String.valueOf(Settings.montoActualDestino));
        }else{

            Settings.montoActualDestino = (int) Long.parseLong(utils.quitarCochetesJsonPath(JsonPath.parse(responseDatosCuenta.getBody().toPrettyString()).read("$.data.accounts[?(@.shortLabel == '"+cuenta+"')].labelWithBalance").toString()).split("\\$ ")[2].split(",")[0].replace(".",""));
            System.out.println("========== MONTO DE LA CUENTA "+cuenta+ " :" + Settings.montoActualDestino);
            Allure.addAttachment("Monto Acutal Destino:", String.valueOf(Settings.montoActualDestino));

        }
    }
    @Step("Obtener Monto Actualizado de la cuenta Origen : {cuenta}")
    public void obtenerMontoActualizadoOrigen(String cuenta){
        PandoraResponse responseDatosCuenta;
        utils.fixedWait(4000);
        responseDatosCuenta = getWidgetsAccount();
        validarResponse.validateResponses(responseDatosCuenta);

        if(cuenta.contains("USD")){

            Settings.montoActualizado = Integer.parseInt(utils.quitarCochetesJsonPath(JsonPath.parse(responseDatosCuenta.getBody().toPrettyString()).read("$.data.accounts[?(@.shortLabel == '"+cuenta+"')].labelWithBalance").toString()).split("USD ")[2].split(",")[0].replace(".",""));
            System.out.println("========== MONTO DE LA CUENTA "+cuenta+ " :" + Settings.montoActualizado);
            Allure.addAttachment("Monto Acutalizado :", String.valueOf(Settings.montoActualizado));
        }else{

            Settings.montoActualizado = Integer.parseInt(utils.quitarCochetesJsonPath(JsonPath.parse(responseDatosCuenta.getBody().toPrettyString()).read("$.data.accounts[?(@.shortLabel == '"+cuenta+"')].labelWithBalance").toString()).split("\\$ ")[2].split(",")[0].replace(".",""));
            System.out.println("========== MONTO DE LA CUENTA "+cuenta+ " :" + Settings.montoActualizado);
            Allure.addAttachment("Monto Acutal:", String.valueOf(Settings.montoActualizado));

        }
     }
    @Step("Obtener Monto Actualizado de la cuenta Destino : {cuenta}")
    public void obtenerMontoActualizadoDestino(String cuenta){
        PandoraResponse responseDatosCuenta;
        responseDatosCuenta = getWidgetsAccount();
        validarResponse.validateResponses(responseDatosCuenta);

        if(cuenta.contains("USD")){

            montoActualizadoDestino = Integer.parseInt(utils.quitarCochetesJsonPath(JsonPath.parse(responseDatosCuenta.getBody().toPrettyString()).read("$.data.accounts[?(@.shortLabel == '"+cuenta+"')].labelWithBalance").toString()).split("USD ")[2].split(",")[0].replace(".",""));
            System.out.println("========== MONTO DE LA CUENTA "+cuenta+ " :" + montoActualizadoDestino);
            Allure.addAttachment("Monto Acutalizado :", String.valueOf(montoActualizadoDestino));
        }else{

            montoActualizadoDestino = Integer.parseInt(utils.quitarCochetesJsonPath(JsonPath.parse(responseDatosCuenta.getBody().toPrettyString()).read("$.data.accounts[?(@.shortLabel == '"+cuenta+"')].labelWithBalance").toString()).split("\\$ ")[2].split(",")[0].replace(".",""));
            System.out.println("========== MONTO DE LA CUENTA "+cuenta+ " :" + montoActualizadoDestino);
            Allure.addAttachment("Monto Acutal:", String.valueOf(montoActualizadoDestino));

        }
    }
    @Step("Obtener el currency de la cuenta : {cuenta}")
    public void obtenerCurrencyCuenta(String cuenta){
        currency = utils.quitarCochetesJsonPath(JsonPath.parse(responseDatosCuenta.getBody().toPrettyString()).read("$.data.accounts[?(@.shortLabel == '"+cuenta+"')].currency").toString());
        System.out.println("========== DIVISA DE LA CUENTA "+cuenta+ " :" + currency);
        Allure.addAttachment("DIVISA:", currency);
    }
    @Step("Obtener label de la cuenta :  {cuenta}")
    public void obtenerLabelCuenta(String cuenta){
        label = utils.quitarCochetesJsonPath(JsonPath.parse(responseDatosCuenta.getBody().toPrettyString()).read("$.data.accounts[?(@.shortLabel == '"+cuenta+"')].label").toString());
        System.out.println("========== DIVISA DE LA CUENTA "+cuenta+ " :" + label);
        Allure.addAttachment("DIVISA:", label);
    }

    //Obtener number
    @Step("Obtener number de la cuenta :  {cuenta}")
    public void obtenerNumberCuenta(String cuenta){
        number = utils.quitarCochetesJsonPath(JsonPath.parse(responseDatosCuenta.getBody().toPrettyString()).read("$.data.accounts[?(@.shortLabel == '"+cuenta+"')].number").toString());
        System.out.println("========== NUMERO DE LA CUENTA "+cuenta+ " :" + number);
        Allure.addAttachment("NUMERO:", number);
    }

    @Step("Obtener los terminos y condiciones de Home Banking")
    public void terminosyCondicionesHB(){
        int intentos = 0;

        response = getTermsAndConditionAnonymousForFunctionality();

        validarResponse.validateResponses(response);
    }
}
