package api;

import io.qameta.allure.Allure;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.codehaus.jettison.json.JSONException;
import org.junit.Assert;
import utils.RestAssuredApi;
import utils.UtilsGP;

public class RecargasAPI {
    RestAssuredApi restAssuredApi = new RestAssuredApi();
    UtilsGP utilsGP = new UtilsGP();
    LoginAPI loginAPI = new LoginAPI();
    CompartidosAPI compartidosAPI = new CompartidosAPI();
    DobleFactorAPI dobleFactorAPI = new DobleFactorAPI();

    private Response deleteAdhesion(String codCompania, String numSube, String perteneceA, String idProducto){
        String body;
        body = utilsGP.leerContenidoArchivo("./Requests/recargas/deleteAdhesion.json");
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );
        body = body.replace("_codigoCompania_",codCompania)
                   .replace("_numSube_",numSube)
                   .replace("_perteneceA_",perteneceA)
                   .replace("_idProducto_",idProducto);

       return restAssuredApi.sendPostRequest("/api/v1/execute/delete.adhesion", headers, body);
    }
    private Response rechargeNewAdhesionWithSecondFactor(String idProduct,String codigoCompania,String numTelefono,String perteneceA,String code2FA){

        String body = utilsGP.leerContenidoArchivo("./Requests/recargas/rechargeNewAdhesionWithSecondFactor.json");

        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );
        body = body.replace("_idProducto_",idProduct)
                .replace("_codCompania_",codigoCompania)
                .replace("_num_",numTelefono)
                .replace("_perteneceA_",perteneceA)
                .replace("_code_",code2FA);

        return restAssuredApi.sendPostRequest("api/v1/execute/recharge.new.adhesionWithSecondFactor", headers, body);
    }

    private Response rechargeListAdhesion(String codeCompany, String idProducto){
        String body;
        body = utilsGP.leerContenidoArchivo("./Requests/recargas/rechargeListAdhesion.json");

        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );

        body = body.replace("_idProducto_",idProducto)
                .replace("_codeCompany_",codeCompany);

        return restAssuredApi.sendPostRequest("/api/v1/execute/recharge.list.adhesions", headers, body);
    }

    //METODOS QUE CONSUMEN LOS SERIVICIOS
    public void eliminarAdhesionServicioAPI(String codigoCompania, String num, String perteneceA, String card) {
        Response res;
        String msg,idProducto;
        boolean flag = false;
        int intentos =0;
        res = compartidosAPI.getwidgetsCreditCards();
        idProducto = utilsGP.quitarCochetesJsonPath(restAssuredApi.getStringFromJson(res,"data.creditCards.findAll {it.label == 'Visa Débito **** **** **** "+card+"'}.idProduct"));

        res = deleteAdhesion(codigoCompania,num,perteneceA,idProducto);
        msg = restAssuredApi.getStringFromJson(res,"message");
        while(intentos<4){
            if(msg.contains("Ocurrió un error inesperado. Intente nuevamente")){
                res = deleteAdhesion(codigoCompania,num,perteneceA,idProducto);
                msg = restAssuredApi.getStringFromJson(res,"message");
            }else{
                flag = true;
                System.out.println("ADHESION ELIMINADA");
                break;
            }
            intentos++;
        }
        if(!flag){
            Assert.fail("================ EL SERVICIO DELETE ADHERSION FALLO 4 VECES REVISAR METODO EN CLASE "+ getClass().getName()  +"");
        }
    }
    public void realizarAdhesionAPI(String codigoCompania, String num, String perteneceA, String card) {
        String idProducto,code2FA,msg;
        Response res;
        boolean flag = false;
        int intentos = 0;
//aqui
        res = compartidosAPI.getwidgetsCreditCards();
        idProducto = utilsGP.quitarCochetesJsonPath(restAssuredApi.getStringFromJson(res,"data.creditCards.findAll {it.label == 'Visa Débito **** **** **** "+card+"'}.idProduct"));

        code2FA = dobleFactorAPI.getSecondFactor();
        //ASSERT PARA VALIDAR CODIGO
        res = rechargeNewAdhesionWithSecondFactor(idProducto,codigoCompania,num,perteneceA,code2FA);
        msg = restAssuredApi.getStringFromJson(res,"data");
        while (intentos < 4){
            if(msg.contains("otp:El código ingresado es inválido")){
                res = rechargeNewAdhesionWithSecondFactor(idProducto,codigoCompania,num,perteneceA,code2FA);
                msg = restAssuredApi.getStringFromJson(res,"data");
            }else{
                flag = true;
                System.out.println("ADHESION REALIZADA CORRECTAMENTE");
                break;
            }
            intentos++;
        }
        if(!flag){
            Assert.fail("================ EL SERVICIO REALIZAR ADHERSION FALLO 4 VECES REVISAR METODO EN CLASE "+ getClass().getName()  +"");
        }
    }

    //CONSULTA ADHESION
    public void consultaAdhesionDeServicioAPI(String codeCompany,String card,String numero){
        Response res;
        String idProducto, servicioAdherido;
        int intentos = 0;

        //Obetenemos el idProduct de la tarjeta de debito
        res = compartidosAPI.getwidgetsCreditCards();
        idProducto = utilsGP.quitarCochetesJsonPath(restAssuredApi.getStringFromJson(res, "data.creditCards.findAll {it.label == 'Visa Débito **** **** **** "+card+"'}.idProduct"));

        //ejecuion de servicio de consulta de adhesion de serivicio
        res = rechargeListAdhesion(codeCompany,idProducto);
        servicioAdherido = utilsGP.quitarCochetesJsonPath(restAssuredApi.getStringFromJson(res,"data.adhesionsList.findAll {it.code == '"+numero+"'}.description"));

        //VERIFICAMOS SI YA EXISTE EL SERVICIO ADHERIDO
        if(!servicioAdherido.isEmpty()){
            System.out.println("======================== YA EXISTE EL SERVICIO PROCDER A ELIMIMAR POR API ====================");
            deleteAdhesion(codeCompany,numero,servicioAdherido,idProducto);
        }else{
            System.out.println("============== NO EXISTE LA ADHESION ===============");
        }
    }
}
