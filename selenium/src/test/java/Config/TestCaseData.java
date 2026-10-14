package Config;

import com.jayway.jsonpath.JsonPath;
import io.qameta.allure.Step;
import org.GP_Individuos_API.Utils;

public class TestCaseData {
    Utils utils = new Utils();
    public String usuario, cuentaOrigen,cuentaDestino,debitCard,password,cbu,alias,tarjetaAOperar,cuitDestino,ambiente,usuario2,password2;
    @Step("Carga de datos necesarios para el TEST")
    public void cargaDeDatosTest(String tc){

            usuario = utils.quitarCochetesJsonPath(JsonPath.parse(Settings.dataTestCase).read("$.data[?(@.tcName == '"+tc+"')].usuario").toString());
            password = utils.quitarCochetesJsonPath(JsonPath.parse(Settings.dataTestCase).read("$.data[?(@.tcName == '"+tc+"')].password").toString());
            cuentaOrigen = utils.quitarCochetesJsonPath(JsonPath.parse(Settings.dataTestCase).read("$.data[?(@.tcName == '"+tc+"')].cuentaOrigen").toString());
            cuentaDestino = utils.quitarCochetesJsonPath(JsonPath.parse(Settings.dataTestCase).read("$.data[?(@.tcName == '"+tc+"')].cuentaDestino").toString());
            cbu = utils.quitarCochetesJsonPath(JsonPath.parse(Settings.dataTestCase).read("$.data[?(@.tcName == '"+tc+"')].cbu").toString());
            alias = utils.quitarCochetesJsonPath(JsonPath.parse(Settings.dataTestCase).read("$.data[?(@.tcName == '"+tc+"')].alias").toString());
            tarjetaAOperar = utils.quitarCochetesJsonPath(JsonPath.parse(Settings.dataTestCase).read("$.data[?(@.tcName == '"+tc+"')].tarjetaAOperar").toString());
            debitCard = utils.quitarCochetesJsonPath(JsonPath.parse(Settings.dataTestCase).read("$.data[?(@.tcName == '"+tc+"')].tarjetaDebito").toString());
            cuitDestino = utils.quitarCochetesJsonPath(JsonPath.parse(Settings.dataTestCase).read("$.data[?(@.tcName == '"+tc+"')].cuitDestino").toString());
            ambiente = utils.quitarCochetesJsonPath(JsonPath.parse(Settings.dataTestCase).read("$.data[?(@.tcName == '"+tc+"')].ambiente").toString());
            usuario2 = utils.quitarCochetesJsonPath(JsonPath.parse(Settings.dataTestCase).read("$.data[?(@.tcName == '"+tc+"')].usuario2").toString());
            password2 = utils.quitarCochetesJsonPath(JsonPath.parse(Settings.dataTestCase).read("$.data[?(@.tcName == '"+tc+"')].password2").toString());
        }
}

