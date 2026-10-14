package api;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.Assert;
import utils.RestAssuredApi;
import utils.UtilsGP;

public class PagosAPI {
    RestAssuredApi restAssuredApi = new RestAssuredApi();
    UtilsGP utilsGP = new UtilsGP();
    LoginAPI loginAPI = new LoginAPI();
    CompartidosAPI compartidosAPI = new CompartidosAPI();
    DobleFactorAPI dobleFactorAPI = new DobleFactorAPI();

    //Se mapea el servicio
    private Response paymentsAddTaxService(String idDebitCard,String codPago,String debitCardLabel,String rubro,String empresa,String cod2FA){
        String body;
        body = utilsGP.leerContenidoArchivo("./Requests/pagosServicios/paymentsAddTaxService.json");
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );

        body = body.replace("_IddebitCard_",idDebitCard)
                .replace("_codePago_",codPago)
                .replace("_debitCard_",debitCardLabel)
                .replace("_category_",rubro)
                .replace("_entity_",empresa)
                .replace("_code_",cod2FA);

        return restAssuredApi.sendPostRequest("/api/v1/execute/payments.addTaxService", headers, body);
    }

    private Response paymentsQueryAdhesion(String idProducto){
        String body;
        body = utilsGP.leerContenidoArchivo("./Requests/pagosServicios/paymentsQueryAdhesions.json");
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );

        body = body.replace("_idProducto_", idProducto);

        return restAssuredApi.sendPostRequest("/api/v1/execute/payments.query.adhesions", headers, body);
    }

    private Response paymentsUnsubscribeAdheredService(String idProducto,String adhesionCode,String empresa,String pertenece,String codeServicio){
        String body;
        body = utilsGP.leerContenidoArchivo("./Requests/pagosServicios/paymentsUnsubscribeAdheredService.json");
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );

        body = body.replace("_idProducto_", idProducto)
                .replace("_adhesionCode_",adhesionCode)
                .replace("_empresa_", empresa)
                .replace("_pertenece_", pertenece)
                .replace("_codeServicio_", codeServicio);

        return restAssuredApi.sendPostRequest("/api/v1/execute/payments.unsubscribe.adhered.service", headers, body);
    }
    private Response debinGenerate(String accountSelector,String buyCuit,String cbu){
        String body;
        body = utilsGP.leerContenidoArchivo("./Requests/pagosServicios/debinGenerate.json");
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );
        body = body.replace("_accountSelector_",accountSelector)
                .replace("_buyCuit_", buyCuit)
                .replace("_cbu_",cbu);

        return restAssuredApi.sendPostRequest("/api/v1/execute/debin.generate", headers, body);
    }

    //Creamos el metodo que lo va a consumir al servicio
    public void adherirServicioAPI(String codPago,String rubro,String empresa,String card){
        Response res;
        String idDebitCard,debitCardLabel,codigo2FA;
        int intentos =0;
        res = compartidosAPI.getwidgetsCreditCards();
        idDebitCard = utilsGP.quitarCochetesJsonPath(restAssuredApi.getStringFromJson(res,"data.creditCards.findAll {it.label == 'Visa Débito **** **** **** "+card+"'}.idProduct"));
        debitCardLabel = utilsGP.quitarCochetesJsonPath(restAssuredApi.getStringFromJson(res,"data.creditCards.findAll {it.label == 'Visa Débito **** **** **** "+card+"'}.label"));

        //agrega el impuesto o servicio
        codigo2FA = dobleFactorAPI.getSecondFactor();
        res = paymentsAddTaxService(idDebitCard,codPago,debitCardLabel,rubro,empresa,codigo2FA);
        while(intentos<4){
            if(!ValidarCodeResponses.flagServicioReejecucion){
                codigo2FA = dobleFactorAPI.getSecondFactor();
                res = paymentsAddTaxService(idDebitCard,codPago,debitCardLabel,rubro,empresa,codigo2FA);
            }else{
                ValidarCodeResponses.flagServicioReejecucion = true;
                System.out.println("============ SERVICIO DE PAGO ADHERIDO CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        if(!ValidarCodeResponses.flagServicioReejecucion){
            Assert.fail("================ EL SERVICIO: ADHERIR SERVICIO/PAGO FALLO 4 VECES REVISAR METODO EN CLASE "+ getClass().getName()  +"");
        }

    }

    //ELIMINAR SERIVICO ADHERIDO
    public void eliminarSerivicoAPI(String empresa,String pertenece,String codeServicio,String card){
        Response res;
        String idProducto, adhesionCode;
        int intentos =0;

        //llamamos al servicio para obtener el idProducto
        res = compartidosAPI.getwidgetsCreditCards();
        idProducto = utilsGP.quitarCochetesJsonPath(restAssuredApi.getStringFromJson(res,"data.creditCards.findAll {it.label == 'Visa Débito **** **** **** "+card+"'}.idProduct"));

        // buscar todos los servicios que esten adherido a este idProducto - Extraer adhesionCode
        res = paymentsQueryAdhesion(idProducto);
        adhesionCode = utilsGP.quitarCochetesJsonPath(restAssuredApi.getStringFromJson(res,"data.adhesions.findAll {it.belongsTo == '"+pertenece+"'}.adhesionCode"));

        //ejecutamos el servicio de eliminar Impuesto/Servicio
        res = paymentsUnsubscribeAdheredService(idProducto,adhesionCode,empresa,pertenece,codeServicio);
        while(intentos<4){
            if(!ValidarCodeResponses.flagServicioReejecucion){
                res = paymentsUnsubscribeAdheredService(idProducto,adhesionCode,empresa,pertenece,codeServicio);
            }else{
                ValidarCodeResponses.flagServicioReejecucion = true;
                System.out.println("============ SE ELIMINO EL IMPUESTO/SERVICIO CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO ELIMINAR IMPUESTO/SERVICIO POR EXCESOS DE INTENTOS",intentos==4);
    }

    //GENERAR DEBIN API
    public void generarDebinAPI(String cuenta,String cbuDestino,String cuitDestino){
        Response res;
        String idProducto;
        int intentos = 0;
        //extramos el idProducto de la cuenta que se pasa por parametros que sería el account selector
        res = compartidosAPI.widgetsAccount();
        idProducto = utilsGP.quitarCochetesJsonPath(restAssuredApi.getStringFromJson(res,"data.accounts.findAll {it.shortLabel == '"+cuenta+"'}.idProduct"));

        //LLAMAMOS AL SERIVICO PARA GENERAR EL DEBIN
        res = debinGenerate(idProducto,cuitDestino,cbuDestino);
        while(intentos<4){
            if(!ValidarCodeResponses.flagServicioReejecucion){
                res = debinGenerate(idProducto,cuitDestino,cbuDestino);
            }else{
                ValidarCodeResponses.flagServicioReejecucion = true;
                System.out.println("============ SE GENERO EL DEBIN CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO GENERAR DEBIN POR EXCESOS DE INTENTOS",intentos==4);
    }
}
