package api;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import utils.RestAssuredApi;
import utils.UtilsGP;

public class bonosDeSaludAPI {
    RestAssuredApi restAssuredApi = new RestAssuredApi();
    UtilsGP utilsGP = new UtilsGP();
    LoginAPI loginAPI = new LoginAPI();
    CompartidosAPI compartidosAPI = new CompartidosAPI();

    private Response healthBonusListAdhesions(String idProducto){
        String body;

        body = utilsGP.leerContenidoArchivo("./Requests/bonosDeSalud/healthBonusListAdhesions.json");

        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );
        body = body.replace("_idProducto_",idProducto);

        return restAssuredApi.sendPostRequest("/api/v1/execute/healthBonus.listAdhesions", headers, body);
    }
    private Response healthBonusNewAdhesion(String idProducto,String codigo){
        String body;

        body = utilsGP.leerContenidoArchivo("./Requests/bonosDeSalud/healthBonusNewAdhesion.json");

        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );
        body = body.replace("_idProducto_",idProducto)
                .replace("_code_",codigo);

        return restAssuredApi.sendPostRequest("/api/v1/execute/healthBonus.newAdhesion", headers, body);
    }
    private Response healthBonusDeleteAdhesion(String idProducto,String codigo){
        String body;

        body = utilsGP.leerContenidoArchivo("./Requests/bonosDeSalud/healthBonusDeleteAdhesion.json");

        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );
        body = body.replace("_idProducto_",idProducto)
                .replace("_code_",codigo);

        return restAssuredApi.sendPostRequest("/api/v1/execute/healthBonus.deleteAdhesion", headers, body);
    }
    public void realizarNuevaAdhesionBonosDeSalud(String tarjetaDebito,String codigo){
        Response response;
        boolean adehsion = false;

        //Obtener idProducto de tarjeta de debito
        response = compartidosAPI.getwidgetsCreditCards();
        String idProducto = utilsGP.quitarCochetesJsonPath(restAssuredApi.getStringFromJson(response,"data.creditCards.findAll {it.label == 'Visa Débito **** **** **** "+tarjetaDebito+"'}.idProduct"));

        //Consultar bonos de salud adherido a la tarjeta
        response = healthBonusListAdhesions(idProducto);
        String code = utilsGP.quitarCochetesJsonPath(restAssuredApi.getStringFromJson(response,"data.adhesionsList.findAll {it.code == '"+codigo+"'}.code"));
        if(code.equals(codigo)){
            System.out.println("BONOS DE SALUD ADHERIDA");
        }else{
            System.out.println("BONOS DE SALUD NO ADHERIDA");
            //ADHERID NUEVO BONO DE SALUD IAPOS
            healthBonusNewAdhesion(idProducto,codigo);
        }
    }
}
