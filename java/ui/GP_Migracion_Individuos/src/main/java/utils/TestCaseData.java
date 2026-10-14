package utils;

import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;

public class TestCaseData {

    UtilsGP utilsGP = new UtilsGP();

    public String usuario,cuentaOrigen,cuentaDestino,tarjetaDebito,password,cbuDestino,
            aliasDestino,cuitDestino,tarjetaCredito, ambiente, usuario2,password2,cuentaComitente;

    @Step("Carga de datos necesarios para el TEST")
    public void cargaDeDatosTests(String tcName){

        usuario = utilsGP.quitarCochetesJsonPath(JsonPath.from(Settings.dataTestCase.toString()).getString("data.findAll {it.tcName == '"+tcName+"'}.usuario"));
        usuario2 = utilsGP.quitarCochetesJsonPath(JsonPath.from(Settings.dataTestCase.toString()).getString("data.findAll {it.tcName == '"+tcName+"'}.usuario2"));
        password = utilsGP.quitarCochetesJsonPath(JsonPath.from(Settings.dataTestCase.toString()).getString("data.findAll {it.tcName == '"+tcName+"'}.password"));
        password2 = utilsGP.quitarCochetesJsonPath(JsonPath.from(Settings.dataTestCase.toString()).getString("data.findAll {it.tcName == '"+tcName+"'}.password2"));
        cuentaOrigen = utilsGP.quitarCochetesJsonPath(JsonPath.from(Settings.dataTestCase.toString()).getString("data.findAll {it.tcName == '"+tcName+"'}.cuentaOrigen"));
        cuentaDestino = utilsGP.quitarCochetesJsonPath(JsonPath.from(Settings.dataTestCase.toString()).getString("data.findAll {it.tcName == '"+tcName+"'}.cuentaDestino"));
        cuentaComitente = utilsGP.quitarCochetesJsonPath(JsonPath.from(Settings.dataTestCase.toString()).getString("data.findAll {it.tcName == '"+tcName+"'}.cuentaComitente"));
        cbuDestino = utilsGP.quitarCochetesJsonPath(JsonPath.from(Settings.dataTestCase.toString()).getString("data.findAll {it.tcName == '"+tcName+"'}.cbuDestino"));
        aliasDestino = utilsGP.quitarCochetesJsonPath(JsonPath.from(Settings.dataTestCase.toString()).getString("data.findAll {it.tcName == '"+tcName+"'}.aliasDestino"));
        tarjetaDebito = utilsGP.quitarCochetesJsonPath(JsonPath.from(Settings.dataTestCase.toString()).getString("data.findAll {it.tcName == '"+tcName+"'}.tarjetaDebito"));
        cuitDestino = utilsGP.quitarCochetesJsonPath(JsonPath.from(Settings.dataTestCase.toString()).getString("data.findAll {it.tcName == '"+tcName+"'}.cuitDestino"));
        tarjetaCredito = utilsGP.quitarCochetesJsonPath(JsonPath.from(Settings.dataTestCase.toString()).getString("data.findAll {it.tcName == '"+tcName+"'}.tarjetaAOperar"));
        ambiente = utilsGP.quitarCochetesJsonPath(JsonPath.from(Settings.dataTestCase.toString()).getString("data.findAll {it.tcName == '"+tcName+"'}.ambiente"));
    }
    @Step("Carga de datos necesarios para el TEST")
    public void cargaDeDatosTest(String name){

        usuario = utilsGP.quitarCochetesJsonPath(JsonPath.from(Constantes.usuarios.toString()).getString("data.findAll {it.usuario == '"+name+"'}.usuario"));
        password = utilsGP.quitarCochetesJsonPath(JsonPath.from(Constantes.usuarios.toString()).getString("data.findAll {it.usuario == '"+name+"'}.password"));

    }
}
