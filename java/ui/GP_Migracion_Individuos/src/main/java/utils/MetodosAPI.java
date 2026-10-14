package utils;

import api.DobleFactorAPI;
import api.LoginAPI;
import api.PerfilAPI;
import api.ValidarCodeResponses;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.json.Json;

//import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MetodosAPI {

    RestAssuredApi restAssuredApi = new RestAssuredApi();
    LoginAPI loginAPI = new LoginAPI();
    DobleFactorAPI dobleFactorAPI = new DobleFactorAPI();
    UtilsGP utilsGP = new UtilsGP();
    ValidarCodeResponses validarCodeResponses = new ValidarCodeResponses();

    //TransferenciasInmediatasSteps transferenciasinmediatas = new TransferenciasInmediatasSteps();


    public Response getwidgetsCreditCards() {
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", LoginAPI.accessToken)
        );
        return restAssuredApi.sendPostRequest("/api/v1/execute/widgets.creditCards", headers, "{}");
    }

    public void eliminarAdhesionServicioPrepagoAPI(String pass, String user, String compania, String codigoCompania, String numTelefono, String perteneceA, String card) {
        if(restAssuredApi.accessToken == null){
            loginAPI.getBearerToken(user, pass);
        }else{
            System.out.println("USUARIO YA LOGUEADO");
        }

        String idProducto = utilsGP.quitarCochetesJsonPath(restAssuredApi.getStringFromJson(getwidgetsCreditCards(),"data.creditCards.findAll {it.label == 'Visa Débito **** **** **** "+card+"'}.idProduct"));

        getDeleteAdhesion(codigoCompania,compania,numTelefono,perteneceA,idProducto);

    }

    public Response getDeleteAdhesion(String codigoCompania,String compania,String numTelefono,String perteneceA,String idProducto){
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", restAssuredApi.accessToken)
        );
        String body = "{\"entity\":\"" + codigoCompania + "\",\"entityLabel\":\""+compania+"\",\"code\":\"" + numTelefono + "\",\"description\":\"" + perteneceA + "\",\"idProduct\": \"" + idProducto + "\",\"ajax_uuid\": \"9c581d9e-94ca-4635-ab4b-6ac3266b8ebb\",\"xAppVersion\": \"1.64.2\"}";

        return restAssuredApi.sendPostRequest("api/v1/execute/delete.adhesion", headers, body);
    }

    public String obtenerIdTarjetaDebito() throws JSONException {
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", restAssuredApi.accessToken)
        );
        String bodyResponse = (getwidgetsCreditCards()).asString();

        int inicioArray = bodyResponse.indexOf("[");
        int finArray = bodyResponse.lastIndexOf("]");
        int i;

        String subString = bodyResponse.substring(inicioArray, finArray + 1);
        String idProduct = null;

        JSONArray jsonArray = new JSONArray(subString);

        for (i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            //status != 1 mensaje: Esta tarjeta no se encuentra habilitada para operar
            if ((jsonObject.getString("productTypeLabel").equals("Tarjeta de Débito")) && (Integer.parseInt(jsonObject.getString("status")) == 1)) {
                idProduct = jsonObject.getString("idProduct");
                break; // Terminamos la búsqueda
            }
        }
        System.out.println("idProductttt: "+idProduct);
        return idProduct;
    }

    //Fomrato que se obtiene **** **** **** 1016
    public String obtenerDescripcionTarjetaDebito(String idProduct) throws JSONException {
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", LoginAPI.accessToken)
        );
        String bodyResponse = (getwidgetsCreditCards()).asString();

        int inicioArray = bodyResponse.indexOf("[");
        int finArray = bodyResponse.lastIndexOf("]");
        int i;

        String subString = bodyResponse.substring(inicioArray, finArray + 1);
        JSONArray jsonArray = new JSONArray(subString);
        String des = null;

        for (i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getString("idProduct").equals(idProduct)) {
                des = jsonObject.getString("number");
                break; // Terminamos la búsqueda
            }
        }
        return des;
    }

    public void realizarAdhesionServicio(String codigoPago, String rubro, String empresa, String card) throws JSONException, InterruptedException {
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", LoginAPI.accessToken)
        );
        String idCliente = "", idAhesion = "", enteDescription = "", userLp = "",message,body;
        int intentos = 0;
        Response res1 = getwidgetsCreditCards();
        int size = restAssuredApi.getListFromJson(res1,"data.creditCards").size();
        for (int i =0; i<size; i++){
            if(restAssuredApi.getStringFromJson(res1,"data.creditCards.get("+i+").label").equals("Visa Débito **** **** **** " + card + "")){
                idCliente = restAssuredApi.getStringFromJson(res1, "data.creditCards.get("+i+").idProduct");
                break;
            }
        }


        String idProducto = obtenerIdTarjetaDebito();
        String numberDebito = obtenerDescripcionTarjetaDebito(idCliente);

        //restAssuredApi.sendPostRequest("api/v1/execute/payments.getEntities",headers,"{\"debitCard\":\"\",\"categoryCode\":\"17\",\"ajax_uuid\":\"26377f37-0184-4f76-a8ec-eeda5a758c18\",\"xAppVersion\":\"1.67.1\"}");
        //restAssuredApi.sendPostRequest("api/v1/execute/payments.validatePaymentCode",headers,"{\"entityCode\":\"GFX\",\"paymentCode\":\"99992146\",\"ajax_uuid\":\"5898d807-d612-43f4-a51e-c32af3c6eb4c\",\"xAppVersion\":\"1.67.1\"}");

        String code2FA = dobleFactorAPI.getSecondFactor();
        //ARREGLAR DESDE AQUI
        body = "{\"debitCard\":\"" + idCliente + "\",\"entityCode\":\"GFX\",\"paymentCode\":\"" + codigoPago + "\",\"belongsTo\": \"TestAutomation1\",\"debitCardDesc\": \"" + numberDebito + "\",\"categoryDesc\": \"" + rubro + "\",\"entityDesc\":\"" + empresa + "\",\"userLP\": \""+codigoPago+"\",\"handleBaseDebts\": true,\"_otp\": \"OTP_TOKEN@" + code2FA + "\"}";

        Response res = restAssuredApi.sendPostRequest("api/v1/execute/payments.addTaxService", headers, body);

        message = restAssuredApi.getStringFromJson(res,"data");
        while(message.contains("El código ingresado es inválido") && intentos<3){
            code2FA = dobleFactorAPI.getSecondFactor();
             body = "{\"debitCard\":\"" + idCliente + "\",\"entityCode\":\"GFX\",\"paymentCode\":\"" + codigoPago + "\",\"belongsTo\": \"TestAutomation1\",\"debitCardDesc\": \"" + numberDebito + "\",\"categoryDesc\": \"" + rubro + "\",\"entityDesc\":\"" + empresa + "\",\"userLP\": \""+codigoPago+"\",\"handleBaseDebts\": true,\"_otp\": \"OTP_TOKEN@" + code2FA + "\"}";
             res = restAssuredApi.sendPostRequest("api/v1/execute/payments.addTaxService", headers, body);
            message = restAssuredApi.getStringFromJson(res,"data");
            intentos++;
        }

    }
    //PRECONDICION GENERACION DE CREDIN SECCION PAGOS
    private Response listAccountAssociated(){
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", LoginAPI.accessToken)
        );
        String body = "{}";


        return restAssuredApi.sendPostRequest("api/v1/execute/list.account.associated", headers, body);
    }
    private Response firstPaymentRequest(String idProductTD, String idProductDA, String ente, String referencia, String codPago, String adhesionCode){
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", LoginAPI.accessToken)
        );
        String body = "{\"currency\": \"ARS\",\"idProductTD\": \""+idProductTD+"\",\"idProductDA\": \""+idProductDA+"\",\"amount\": 1,\"enteCode\": \"969\",\"enteDescription\": \""+ente+"\",\"reference\": \""+referencia+"\",\"debtID\": \"\",\"conceptDescription\": \"\",\"conceptCode\": \"002\",\"ususarioLP\": \""+codPago+"\",\"subscribe\": true,\"adhesionCode\": \"07\",\"manageBaseDebt\": false,\"ajax_uuid\": \"825046d5-c284-4316-8a23-59cdddaea29e\",\"xAppVersion\": \"1.67.1\"}";

        return restAssuredApi.sendPostRequest("api/v1/execute/firstPayment.request", headers, body);
    }

    private Response firstPaymentAddEntityByCpe(String idProductTD, String codPago, String dfa){
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", LoginAPI.accessToken)
        );
        String body = "{\"idProduct\":\""+idProductTD+"\",\"enteCode\":\"969\",\"paymentCode\":\""+codPago+"\",\"belongsTo\":\"\",\"_otp\":\"OTP_TOKEN@"+dfa+"\",\"ajax_uuid\":\"778beab2-5c2c-4cc3-b5be-c0b1d647ce38\",\"xAppVersion\":\"1.67.1\"}";

        return restAssuredApi.sendPostRequest("api/v1/execute/firstPayment.addEntityByCpe",headers,body);
    }

    private Response widgetsAccount() {
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", LoginAPI.accessToken)
        );
        String body = "{\"ajax_uuid\": \"2155f0c5-f488-408a-9fdc-db6cc55a38b0\",\"xAppVersion\": \"1.64.1\"}";

        return restAssuredApi.sendPostRequest("api/v1/execute/widgets.accounts", headers, body);
    }
    //SECCION INVERSIONES
    private Response fundSubscribe(String cuentaComitente, int fciID, String idProduct, int importe){
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", LoginAPI.accessToken)
        );
        String body="{\"commitentAccount\": \""+cuentaComitente+"\",\"commonInvestmentFundId\": "+fciID+",\"debitProduct\": \""+idProduct+"\",\"amount\": "+importe+",\"quotation\": \"10,679612\"}";

        return restAssuredApi.sendPostRequest("api/v1/execute/fund.subscribe",headers,body);
    }

    //BonosDeSaludTest
    public void eliminarAdhesionBonosDeSalud(String ente, String numAfiliado, String perteneceA) throws InterruptedException {
        Response res = getwidgetsCreditCards();
        String idProducto = restAssuredApi.getStringFromJson(res,"data.creditCards[0].idProduct");

        res = healthBonusDeleteAdhesion(numAfiliado, perteneceA, idProducto, ente);
    }

    public Response healthBonusDeleteAdhesion(String numAfiliado, String perteneceA, String idProducto, String ente){
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", restAssuredApi.accessToken)
        );
        String body = "{\"entity\":\"617\",\"code\":\""+numAfiliado+"\",\"description\":\""+perteneceA+"\",\"idProduct\":\""+idProducto+"\",\"entityLabel\":\""+ente+"\"}";
        return restAssuredApi.sendPostRequest("api/v1/execute/healthBonus.deleteAdhesion", headers, body);
    }
    //BonosDeSaludTest
    public void realizarAdhesionAfiliadoBonosDeSalud(String ente, String numAfiliado, String perteneceA) throws InterruptedException {
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", LoginAPI.accessToken)
        );
        String idProducto = restAssuredApi.getStringFromJson(restAssuredApi.sendPostRequest("/api/v1/execute/widgets.creditCards", headers, "{}"), "data.creditCards[0].idProduct");

        String body = "{\"idProduct\":\"" + idProducto + "\",\"companyCode\":\"617\",\"addUser\":\"{\\\"code\\\":\\\"" + numAfiliado + "\\\",\\\"description\\\":\\\"" + perteneceA + "\\\"}\"}";
        Response res = restAssuredApi.sendPostRequest("api/v1/execute/healthBonus.newAdhesion", headers, body);
        assert (restAssuredApi.getStringFromJson(res,"message")).equals("Transacción realizada con éxito") : restAssuredApi.getStringFromJson(res,"message");
    }


    public void realizarPagoServicio(String ente, String referencia, String codPago, String cuentaPago, String card) throws JSONException, InterruptedException, JsonProcessingException {
        //String idProductTD = obtenerIdTarjetaDebito();//Obtengo el productId asociado a una tarjeta de debito
        String idProductTD = null;
        if(card.equals("")){
            idProductTD = obtenerIdTarjetaDebito();
        }else{
            idProductTD = utilsGP.quitarCochetesJsonPath(restAssuredApi.getStringFromJson( getwidgetsCreditCards(),"data.creditCards.findAll {it.label == 'Visa Débito **** **** **** "+card+"'}.idProduct"));

        }
        String idProductDA = obtenerIdCuentaAsociadaTdDebito(idProductTD,cuentaPago);//Obtengo productId de una cuenta asociada a la TD

        String cod2Fa = dobleFactorAPI.getSecondFactor();
        Response res = firstPaymentAddEntityByCpe(idProductTD,codPago,cod2Fa);
        //SI LLEGASE A FALLAR EL PRIMERO CODIGO 2FA SE CAPTURA UN NUEVO COD y SE PRUEBA AL MENOS UNA VEZ EL SERVICIO
        String data = utilsGP.quitarCochetesJsonPath(restAssuredApi.getStringFromJson(res,"data"));
        System.out.println("DATA : "+ data);
        if(data.contains("otp:El código ingresado es inválido")) {
            String dobleFactor = "";
            for (int i = 0; i < 3; i++) {
                System.out.println("========== RE-EJECUCION DOBLE FACTOR : " + (i + 1) + "==============");
                dobleFactor = dobleFactorAPI.getSecondFactor();
                if (cod2Fa.equals(dobleFactor)) {
                    System.out.println("========== DEVOLVIO EL MISMO CODIGO ============");
                    dobleFactor = dobleFactorAPI.getSecondFactor();
                } else {
                    System.out.println("================== NUEVO COD2FA ===============");
                    break;
                }
            }
            firstPaymentAddEntityByCpe(idProductTD,codPago,dobleFactor);
        }
        //RE-EJECUCION CODIGO 2FA
       firstPaymentRequest(idProductTD,idProductDA,ente,referencia,codPago,"");
    }

    public String obtenerIdCuentaAsociadaTdDebito(String productIdDebito, String cuenta) throws JSONException, JsonProcessingException {
        Response res = listAccountAssociated();
        String idProductDA ="";
        int size = restAssuredApi.getListFromJson(res,"data.debitCardAccounts."+productIdDebito+"").size();
        for(int i = 0;i<size;i++){
            String shortLabel = restAssuredApi.getStringFromJson(res,"data.debitCardAccounts."+productIdDebito+".get("+i+").shortLabel");
            if(shortLabel.equals(cuenta)){
                idProductDA = restAssuredApi.getStringFromJson(res,"data.debitCardAccounts."+productIdDebito+".get("+i+").idProduct");
                System.out.println("========= EL ELEMENTO SE ENCONTRO IDPRODUCT :"+idProductDA);
                break;
            }
        }
        if(idProductDA.equals("")){
            System.out.println("==== NO SE ENCONTRO EL ELEMENTO : "+cuenta+" EN EL ARRAY =====");
        }
        return idProductDA;
    }
    public String extraerCuentaConMonto(String moneda) throws JSONException {
        Response res = widgetsAccount();
        ArrayList<LinkedHashMap<String, Object>> idAdheredAccount = (ArrayList<LinkedHashMap<String, Object>>) restAssuredApi.getListFromJson(res, "data.accounts");

        String cuenta = "";
        for (LinkedHashMap<String, Object> datos : idAdheredAccount) {
            if (datos.get("balance")!=null && moneda.equals(datos.get("currency"))) {
                String val = datos.get("balance").toString();
                cuenta = datos.get("label").toString();
                System.out.println("Cuenta: " + datos.get("label") + " Monto Disponible: " +  Double.parseDouble(val));
                break;
            }
        }
        Assert.assertFalse("Cuentas con balance cero en API ",cuenta.equals(""));
        return cuenta;
    }

    public void generarFCI(String usuario, String pass, String cuentaComitente, int tipoFci, int importe, String cuentaDebito) {
        String idProduct ="";
        Response res;
        //restAssuredApi.getBearerToken(usuario,pass);
        if (restAssuredApi.accessToken == null) {
            loginAPI.getBearerToken(usuario, pass);
        }
        ArrayList<LinkedHashMap<String,String>> datos = (ArrayList<LinkedHashMap<String, String>>) restAssuredApi.getListFromJson(widgetsAccount(),"data.accounts");
        for(LinkedHashMap<String, String> a :datos){
            if(a.get("shortLabel").equals(cuentaDebito)){
                idProduct= a.get("idProduct");
            }
        }
        res = fundSubscribe(cuentaComitente,tipoFci,idProduct,importe);
        int  intentos = 0;
/*      while(intentos<4){
            if(restAssuredApi.getStringFromJson(res,"code") == "COR000I"){
                break;
            }else{
                res = fundSubscribe(cuentaComitente,tipoFci,idProduct,importe);
            }
        }*/
        assert (restAssuredApi.getStringFromJson(res,"message")).equals("Transacción realizada con éxito")  : restAssuredApi.getStringFromJson(res,"message") +"\n" + restAssuredApi.getStringFromJson(res,"data");
    }


}



