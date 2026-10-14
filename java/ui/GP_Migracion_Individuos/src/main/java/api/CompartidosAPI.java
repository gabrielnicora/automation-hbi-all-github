package api;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import utils.RestAssuredApi;
import utils.UtilsGP;

public class CompartidosAPI {
    RestAssuredApi restAssuredApi = new RestAssuredApi();
    UtilsGP utilsGP = new UtilsGP();

    LoginAPI loginAPI = new LoginAPI();
    public Response getwidgetsCreditCards() {
        String body;
        body = utilsGP.leerContenidoArchivo("./Requests/compatidos/requestVacio.json");
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );

        return restAssuredApi.sendPostRequest("/api/v1/execute/widgets.creditCards", headers, body);
    }
    public Response widgetsAccount() {
        String body;
        body = utilsGP.leerContenidoArchivo("./Requests/compatidos/requestVacio.json");
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );

        return restAssuredApi.sendPostRequest("api/v1/execute/widgets.accounts", headers, body);
    }
}
