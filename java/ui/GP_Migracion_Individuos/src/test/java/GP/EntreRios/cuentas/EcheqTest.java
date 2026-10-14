package GP.EntreRios.cuentas;

import GP.Tags.Alta;
import GP.Tags.Baja;
import GP.Tags.Criticos;
import db.ConexionDB;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.junit.experimental.categories.Category;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.cuentas.CuentasSteps;
import steps.cuentas.echeq.EcheqSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import utils.Constantes;
import utils.TestCaseData;
import utils.UtilsGP;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.uEcarina27;

public class EcheqTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    CuentasSteps cuentasSteps = new CuentasSteps();
    EcheqSteps echeqSteps = new EcheqSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
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

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0025-BERSA: Echeq - Completitud de pantalla con filtro: Estado = Todos")
    public void TC0025BERSA(){
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickEcheq();
            echeqSteps.validarTituloPageGenerico("Echeq");
            echeqSteps.keyDownXParametrosGenerico(13,"Todos");
            echeqSteps.clickBotonBuscar();
            echeqSteps.validarCompletitudPantalla();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0026-BERSA: Echeq - Consulta de Echeq - Verificar detalle de echeq - Estado = Todos")
    public void TC0026BERSA(){
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickEcheq();
            echeqSteps.validarTituloPageGenerico("Echeq");
            echeqSteps.keyDownXParametrosGenerico(13,"Todos");
            echeqSteps.clickBotonBuscar();
            echeqSteps.clickVerDetallePrimerEcheq();
            echeqSteps.validarTituloPageGenerico("Detalle de ECHEQ");
            echeqSteps.validarTituloPageGenerico("Id Echeq");
            echeqSteps.validarTituloPageGenerico("Nro Echeq");
            echeqSteps.validarTituloPageGenerico("Estado");
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
