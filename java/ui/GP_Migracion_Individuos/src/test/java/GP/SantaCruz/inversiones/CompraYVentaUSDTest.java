package GP.SantaCruz.inversiones;

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
    @DisplayName("TC-0009-BSC: CPA / VTA de Dolares  - Cliente Vende 10 Dólares y Acredita en CA en Pesos")
    public void TC0009BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPruebaqaBSC03);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText( "Vender");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.keyDownToText( "USD");
            compraVentaDeDolaresSteps.clickCuentaDestino();
            compraVentaDeDolaresSteps.keyDownToText("Caja de Ahorro $ 600009140");
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
    @Category({Criticos.class})
    @DisplayName("TC-0069-BSC: Compra de moneda extranjera (USD)")
    public void TC0069BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uPruebaqaBSC03);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText("Comprar");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.keyDownToText( "Caja de Ahorro $ 600009140 ");
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
    @DisplayName("TC- 0177- BSC-  Inversiones- Compra de moneda extranjera - Superar importe maximo permitido de compra de dolares ($201)- Validar mensaje 'No se pudo realizar la operación'")
    public void TC0177BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uBsc13400488);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText( "Comprar");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.keyDownToText( "Cuenta Corriente $ 550001362");
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
    @DisplayName("TC- 0178- BSC-  Inversiones- Compra de moneda extranjera - Colocar importe 0 de compra de dolar - Validar mensaje 'No se pudo realizar la operación'")
    public void TC0178BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uBsc13400488);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText("Comprar");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.keyDownToText( "Cuenta Corriente $ 550001362");
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
    @DisplayName("TC- 0179- BSC-  Inversiones- Compra de moneda extranjera - Seguir flujo de compra de dolar sin aceptar check terminos y condiciones - Validar campo obligatorio")
    public void TC0179BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uBsc13400488);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText( "Comprar");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.keyDownToText( "Cuenta Corriente $ 550001362");
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
    @DisplayName("TC- 0181- BSC-  Inversiones- Compra de moneda extranjera - Validar desplegable Cuenta Origen solo cuentas en Pesos")
    public void TC0181BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uBsc13400488);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText( "Comprar");
            compraVentaDeDolaresSteps.validarDesplegableCuentasEnPesos("cuentaOrigen");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC- 0182- BSC-  Inversiones- Compra de moneda extranjera - Validar desplegable Cuenta Destino solo cuentas en Dolares")
    public void TC0182BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uBsc13400488);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText( "Comprar");
            compraVentaDeDolaresSteps.validarDesplegableCuentasEnDolares("cuentaDestino");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC- 0187- BSC-  Inversiones- Venta de moneda extranjera - Colocar importe 0 de compra de dolar - Validar mensaje 'No se pudo realizar la operación'")
    public void TC0187BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uBsc13400488);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText( "Vender");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.keyDownToText( "USD");
            compraVentaDeDolaresSteps.clickCuentaDestino();
            compraVentaDeDolaresSteps.keyDownToText("Cuenta Corriente $ 550001362");
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
    @DisplayName("TC- 0188- BSC-  Inversiones- Venta de moneda extranjera - Seguir flujo de venta de dolar sin aceptar check terminos y condiciones - Validar campo obligatorio")
    public void TC0188BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uBsc13400488);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText( "Vender");
            compraVentaDeDolaresSteps.clickCuentaOrigen();
            compraVentaDeDolaresSteps.keyDownToText( "USD");
            compraVentaDeDolaresSteps.clickCuentaDestino();
            compraVentaDeDolaresSteps.keyDownToText("Cuenta Corriente $ 550001362");
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
    @DisplayName("TC- 0189- BSC-  Inversiones- Venta de moneda extranjera - Validar desplegable Cuenta Destino solo cuentas en Pesos")
    public void TC0189BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uBsc13400488);
            menuLateralSteps.clickInversiones();
            inversionesSteps.clickCompraVentaDeDolares();
            compraVentaDeDolaresSteps.validarTituloPageGenerico("Compra venta de d");
            compraVentaDeDolaresSteps.clickTipoDeOperacion();
            compraVentaDeDolaresSteps.keyDownToText("Vender");
            compraVentaDeDolaresSteps.validarDesplegableCuentasEnPesos("cuentaDestino");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Baja.class})
    @DisplayName("TC- 0190- BSC-  Inversiones- Venta de moneda extranjera - Validar desplegable Cuenta Origen solo cuentas en Dolares")
    public void TC0190BSC() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uBsc13400488);
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

