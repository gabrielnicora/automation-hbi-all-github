package GP.SantaFe.inversiones;

import GP.Tags.Alta;
import GP.Tags.Baja;
import GP.Tags.Criticos;
import GP.Tags.Media;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.inversiones.InversionesSteps;
import steps.inversiones.compraventadedolares.CompraVentaDeDolaresSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import steps.ticket.TicketSteps;
import utils.UtilsGP;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;


public class CompraYVentaUSDTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    InversionesSteps inversionesSteps = new InversionesSteps();
    CompraVentaDeDolaresSteps compraVentaDeDolaresSteps = new CompraVentaDeDolaresSteps();
    TicketSteps ticketSteps = new TicketSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    UtilsGP utilsGP = new UtilsGP();

    
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0009-BSF: CPA / VTA de Dolares  - Cliente Vende 10 Dólares y Acredita en CA en Pesos")
    public void TC0009BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPrueba12);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText("Vender");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.keyDownToText( "USD");
            compraVentaDeDolaresSteps.clickCuentaDestino();
            compraVentaDeDolaresSteps.keyDownToText("6309");
            compraVentaDeDolaresSteps.setMonto("50");
            compraVentaDeDolaresSteps.clickContinuarGenerico();
            compraVentaDeDolaresSteps.clickCheckTerminosCondicionesGenerico();
            compraVentaDeDolaresSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0069-BSF: Compra de moneda extranjera (USD)")
    public void TC0069BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTester1);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText( "Comprar");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.keyDownToText( "Caja de Ahorro $ 000077914707");
            compraVentaDeDolaresSteps.clickCuentaDestino();
            compraVentaDeDolaresSteps.keyDownToText("USD");
            compraVentaDeDolaresSteps.setMonto("1");
            compraVentaDeDolaresSteps.clickContinuarGenerico();
            compraVentaDeDolaresSteps.clickCheckTerminosCondicionesGenerico();
            compraVentaDeDolaresSteps.clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Media.class})
    @DisplayName("TC- 0177- BSF-  Inversiones- Compra de moneda extranjera - Superar importe maximo permitido de compra de dolares ($201)- Validar mensaje 'No se pudo realizar la operación'")
    public void TC0177BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uVolpatomonica);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText("Comprar");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.keyDownToText( "Caja de Ahorro $ 000012578807");
            compraVentaDeDolaresSteps.clickCuentaDestino();
            compraVentaDeDolaresSteps.keyDownToText("USD");
            compraVentaDeDolaresSteps.setMonto("201");
            compraVentaDeDolaresSteps.clickContinuarGenerico();
            compraVentaDeDolaresSteps.clickCheckTerminosCondicionesGenerico();
            compraVentaDeDolaresSteps.clickConfirmarGenerico();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("No se pudo realizar la operaci");
            compraVentaDeDolaresSteps.validacionMensaje("Superaste el límite mensual permitido para la compra de dólares");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC- 0178- BSF-  Inversiones- Compra de moneda extranjera - Colocar importe 0 de compra de dolar - Validar mensaje 'No se pudo realizar la operación'")
    public void TC0178BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uVolpatomonica);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText("Comprar");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.keyDownToText( "Caja de Ahorro");
            compraVentaDeDolaresSteps.clickCuentaDestino();
            compraVentaDeDolaresSteps.keyDownToText("USD");
            compraVentaDeDolaresSteps.setMonto("0");
            compraVentaDeDolaresSteps.clickContinuarGenerico();
            //compraVentaDeDolaresSteps.clickCheckTerminosCondicionesGenerico();
            //compraVentaDeDolaresSteps.clickConfirmarGenerico();
            compraVentaDeDolaresSteps.validacionMensaje("El monto ingresado debe ser mayor a 0");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC- 0179- BSF-  Inversiones- Compra de moneda extranjera - Seguir flujo de compra de dolar sin aceptar check terminos y condiciones - Validar campo obligatorio")
    public void TC0179BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uVolpatomonica);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText( "Comprar");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.keyDownToText( "Caja de Ahorro");
            compraVentaDeDolaresSteps.clickCuentaDestino();
            compraVentaDeDolaresSteps.keyDownToText("USD");
            compraVentaDeDolaresSteps.setMonto("10");
            compraVentaDeDolaresSteps.clickContinuarGenerico();
            compraVentaDeDolaresSteps.clickConfirmarGenerico();
            compraVentaDeDolaresSteps.validacionMensaje("Leer y aceptar los términos y condiciones es obligatorio para continuar");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC- 0181- BSF-  Inversiones- Compra de moneda extranjera - Validar desplegable Cuenta Origen solo cuentas en Pesos")
    public void TC0181BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uVolpatomonica);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText("Comprar");
            compraVentaDeDolaresSteps.validarDesplegableCuentasEnPesos("cuentaOrigen");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC- 0182- BSF-  Inversiones- Compra de moneda extranjera - Validar desplegable Cuenta Destino solo cuentas en Dolares")
    public void TC0182BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uVolpatomonica);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText("Comprar");
            compraVentaDeDolaresSteps.validarDesplegableCuentasEnDolares("cuentaDestino");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC- 0187- BSF-  Inversiones- Venta de moneda extranjera - Colocar importe 0 de compra de dolar - Validar mensaje 'No se pudo realizar la operación'")
    public void TC0187BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uVolpatomonica);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText( "Vender");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.keyDownToText( "USD");
            compraVentaDeDolaresSteps.clickCuentaDestino();
            compraVentaDeDolaresSteps.keyDownToText("Caja de Ahorro $ 000003554209");
            compraVentaDeDolaresSteps.setMonto("0");
            compraVentaDeDolaresSteps.clickContinuarGenerico();
            compraVentaDeDolaresSteps.validacionMensaje("El monto ingresado debe ser mayor a 0");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC- 0188- BSF-  Inversiones- Venta de moneda extranjera - Seguir flujo de venta de dolar sin aceptar check terminos y condiciones - Validar campo obligatorio")
    public void TC0188BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uVolpatomonica);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText( "Vender");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.keyDownToText( "USD");
            compraVentaDeDolaresSteps.clickCuentaDestino();
            compraVentaDeDolaresSteps.keyDownToText("Caja de Ahorro $ 000003554209");
            compraVentaDeDolaresSteps.setMonto("10");
            compraVentaDeDolaresSteps.clickContinuarGenerico();
            compraVentaDeDolaresSteps.clickConfirmarGenerico();
            compraVentaDeDolaresSteps.validacionMensaje("Leer y aceptar los términos y condiciones es obligatorio para continuar");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC- 0189- BSF-  Inversiones- Venta de moneda extranjera - Validar desplegable Cuenta Destino solo cuentas en Pesos")
    public void TC0189BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uVolpatomonica);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText( "Vender");
            compraVentaDeDolaresSteps.validarDesplegableCuentasEnPesos("cuentaDestino");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC- 0190- BSF-  Inversiones- Venta de moneda extranjera - Validar desplegable Cuenta Origen solo cuentas en Dolares")
    public void TC0190BSF() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uVolpatomonica);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText( "Vender");
            compraVentaDeDolaresSteps.validarDesplegableCuentasEnDolares("cuentaOrigen");
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

