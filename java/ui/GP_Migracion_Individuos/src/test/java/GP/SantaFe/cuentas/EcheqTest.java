package GP.SantaFe.cuentas;

import GP.Tags.Alta;
import GP.Tags.Criticos;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.cuentas.CuentasSteps;
import steps.cuentas.echeq.EcheqSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import utils.UtilsGP;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.uPerezR;

public class EcheqTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    CuentasSteps cuentasSteps = new CuentasSteps();
    EcheqSteps echeqSteps = new EcheqSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();

    UtilsGP utilsGP = new UtilsGP();
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0025-BSF: Echeq - Completitud de pantalla con filtro: Estado = Todos")
    public void TC0025BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPerezR);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickEcheq();
            echeqSteps.validarTituloPageGenerico("Echeq");
            echeqSteps.keyDownXParametrosGenerico(13, "Todos");
            echeqSteps.clickBotonBuscar();
            echeqSteps.validarCompletitudPantalla();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Alta.class})
    @DisplayName("TC-0026-BSF: Echeq - Consulta de Echeq - Verificar detalle de echeq - Estado = Todos")
    public void TC0026BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPerezR);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickEcheq();
            echeqSteps.validarTituloPageGenerico("Echeq");
            echeqSteps.keyDownXParametrosGenerico(13, "Todos");
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
        cerrarConexion();
    }

}
