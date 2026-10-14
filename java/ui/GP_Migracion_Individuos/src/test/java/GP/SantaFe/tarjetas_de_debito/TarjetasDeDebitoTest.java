package GP.SantaFe.tarjetas_de_debito;

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
import steps.calendarios.CalendarioSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import steps.tarjetasdedebito.TarjetasDeDebitoSteps;
import steps.tarjetasdedebito.consultacompras.ConsultaDeComprasSteps;
import utils.UtilsGP;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;

public class TarjetasDeDebitoTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    TarjetasDeDebitoSteps tarjetasDeDebitoSteps = new TarjetasDeDebitoSteps();
    ConsultaDeComprasSteps consultaDeComprasSteps= new ConsultaDeComprasSteps();
    BotonesGenericosPage  botonesGenericosPage = new BotonesGenericosPage();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    CalendarioSteps calendarioSteps = new CalendarioSteps();
    UtilsGP utilsGP = new UtilsGP();

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0068-BSF: Activación de tarjeta de débito - Validación de completitud de pantalla")
    public void TC0068BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSantafe038);
            menuLateralSteps.clickTarjetaDeDebito();
            tarjetasDeDebitoSteps.validarTituloPageGenerico("Tarjetas de d");
            tarjetasDeDebitoSteps.clickBotonVerMas_Activar("Visa Débito");
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
    @DisplayName("TC-0105-BSF: Tarjeta de Débito - Consulta de compras")
    public void TC0105BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uVolpatomonica);
            menuLateralSteps.clickTarjetaDeDebito();
            tarjetasDeDebitoSteps.validarTituloPageGenerico("Tarjetas de d");
            tarjetasDeDebitoSteps.clickBotonVerMas_Activar("9008");
            tarjetasDeDebitoSteps.clickMasOpcionesGenerico();
            tarjetasDeDebitoSteps.clickConsultarCompras();
            tarjetasDeDebitoSteps.validarTituloPageGenerico("Consulta de compras en comercios");
            consultaDeComprasSteps.clickDesplegableCuenta();
            tarjetasDeDebitoSteps.keyDownToText("5705");
            consultaDeComprasSteps.clickFechaDesde();
            calendarioSteps.retrocederSegunMes(10);
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
        cerrarConexion();
    }
}
