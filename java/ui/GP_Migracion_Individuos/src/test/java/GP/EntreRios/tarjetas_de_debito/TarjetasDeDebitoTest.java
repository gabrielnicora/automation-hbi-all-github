package GP.EntreRios.tarjetas_de_debito;

import GP.Tags.Criticos;
import db.ConexionDB;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.junit.experimental.categories.Category;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.calendarios.CalendarioSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import steps.tarjetasdedebito.TarjetasDeDebitoSteps;
import steps.tarjetasdedebito.consultacompras.ConsultaDeComprasSteps;
import utils.Constantes;
import utils.TestCaseData;
import utils.UtilsGP;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.uUattesthb14;

public class TarjetasDeDebitoTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    TarjetasDeDebitoSteps tarjetasDeDebitoSteps = new TarjetasDeDebitoSteps();
    ConsultaDeComprasSteps consultaDeComprasSteps= new ConsultaDeComprasSteps();
    CalendarioSteps calendarioSteps = new CalendarioSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    UtilsGP utilsGP = new UtilsGP();
    TestCaseData testCaseData = new TestCaseData();
    @BeforeClass
    public static void extraerDatosBD(){
        System.out.println("============= BEFORE CLASS ==========================");
        if(Constantes.usuarios == null){
            System.out.println("============= Array de datos vacio extraer desde la BD ==============");
            Constantes.usuarios = ConexionDB.extraerUsuariosDB();
            System.out.println("============= se completo el array de datos ==========================");
        }else {
            System.out.println("================ EL ARRAY CONTIENE DATOS ===================");
        }
    }


    @Test //Faltante de dato: Falta usuario con TD para activar
    @Category({Criticos.class})
    @DisplayName("TC-0068-BERSA: Activación de tarjeta de débito - Validación de completitud de pantalla")
    public void TC0068BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uUattesthb14);
            loginSteps.iniciarSessionAlEscritorioConPass(uUattesthb14,testCaseData.password);
            menuLateralSteps.clickTarjetaDeDebito();
            tarjetasDeDebitoSteps.validarTituloPageGenerico("Tarjetas de d");
            tarjetasDeDebitoSteps.clickBotonVerMas_Activar("7009");
            tarjetasDeDebitoSteps.validarTituloPageGenerico("Activaci");
            tarjetasDeDebitoSteps.validarTituloPageGenerico("Visa D");
            tarjetasDeDebitoSteps.validacionPantallaActivacionTarjeta();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0105-BERSA: Tarjeta de Débito - Consulta de compras")
    public void TC0105BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uUattesthb14);
            loginSteps.iniciarSessionAlEscritorioConPass(uUattesthb14,testCaseData.password);
            menuLateralSteps.clickTarjetaDeDebito();
            tarjetasDeDebitoSteps.validarTituloPageGenerico("Tarjetas de d");
            tarjetasDeDebitoSteps.clickBotonVerMas_Activar("7009");
            tarjetasDeDebitoSteps.clickMasOpcionesGenerico();
            tarjetasDeDebitoSteps.clickConsultarCompras();
            tarjetasDeDebitoSteps.validarTituloPageGenerico("Consulta de compras en comercios");
            consultaDeComprasSteps.clickDesplegableCuenta();
            tarjetasDeDebitoSteps.keyDownXParametrosGenerico(0,"6442");
            consultaDeComprasSteps.clickFechaDesde();
            calendarioSteps.clickMesAnterior(4);
            calendarioSteps.clickEnDiaXParametro("1");
            calendarioSteps.clickBuscar();
            consultaDeComprasSteps.validarTablaConsultaCompras();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @After
    public void cerrarSesionCapturarUPS(){
        if(utilsGP.estaPresente(botonesGenericosPage.getCodigoUps(),2) && botonesGenericosPage.getCodigoUps().getText().startsWith("#")) {
            String ups = botonesGenericosPage.getCodigoUps().getText();
            botonesGenericosSteps.clickVolverGenerico();
            loginSteps.clickSalirDelaSesion();
            loginSteps.waitGP();
            Assert.fail("UPS CODIGO : " + ups);
        }else if(utilsGP.estaPresente(botonesGenericosPage.getSalirSesion(),2))
        {
            loginSteps.clickSalirDelaSesion();
            loginSteps.waitGP();
        }else{
            System.out.println("=================== NO PUDO INICIAR SESION REFRESCAR NAVEGADOR ==============================");
            Allure.addAttachment("INICIO SESSION,","=================== NO PUDO INICIAR SESION REFRESCAR NAVEGADOR ==============================");
            global.practia.jemeinsamstark.web.core.WebDriver.getInstance().navigate().to(System.getProperty("bank"));
            System.out.println("Se Espera que aparezca la pagina de nuevo");
            loginSteps.waitGP();
        }
    }
    @AfterClass
    public static void teardown() throws IOException, InterruptedException {
        global.practia.jemeinsamstark.web.core.WebDriver.closeDriver();
        //cerrarConexion();
    }
}
