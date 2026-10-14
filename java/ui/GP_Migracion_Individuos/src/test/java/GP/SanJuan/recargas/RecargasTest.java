package GP.SanJuan.recargas;

import GP.Tags.Alta;
import GP.Tags.Criticos;
import GP.Tags.Media;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.rules.TestWatcher;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.calendarios.CalendarioSteps;
import steps.doblefactor.DobleFactorSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import steps.pagos.nuevopago.NuevoPagoSteps;
import steps.recargas.RecargasSteps;
import steps.recargas.consultarecargas.ConsultaRecargasSteps;
import steps.ticket.TicketSteps;
import utils.RestAssuredApi;
import utils.UtilsGP;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;

public class RecargasTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    RecargasSteps recargasSteps = new RecargasSteps();
    ConsultaRecargasSteps consultaRecargasSteps = new ConsultaRecargasSteps();
    CalendarioSteps calendario = new CalendarioSteps();
    DobleFactorSteps dobleFactorSteps = new DobleFactorSteps();
    NuevoPagoSteps nuevoPagoSteps = new NuevoPagoSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    TicketSteps ticket = new TicketSteps();
    RestAssuredApi restAssuredApi = new RestAssuredApi();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    UtilsGP utilsGP = new UtilsGP();

    private String testActual;
    private final List<String> tcsAdhierenRecargas = new ArrayList<String>() {
        {
            add("TC0071BSJ");
            //add("TC0072BSJ"); SE COMENTA YA QUE LA PRECONDICIÓN ESTA HECHA MEDIANTE API
        }
    };
    @Rule
    public final TestWatcher testWatcher = new TestWatcher() {
        @Override
        protected void starting(org.junit.runner.Description description) {
            System.out.println("Iniciando prueba: " + description.getMethodName());
            testActual = description.getMethodName();
        }
    };

    //@Test
    @Category({Criticos.class})
    @DisplayName("TC-0071-BSJ: RECARGAS - Realizar recarga a Transporte Publico")
    public void TC0071BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2021);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickCardTransportePublico();
            recargasSteps.clickEmpresa();
            recargasSteps.keyDownToText("SUBE");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.keyDownXParametrosGenerico(0,"");
            recargasSteps.setInputNumero("6061267195495203");
            recargasSteps.setInputPerteneceA("AUTOMATION");
            dobleFactorSteps.codigoSegundoFactorOtroMetodo();
            recargasSteps.clickSelectorMonto();
            recargasSteps.keyDownXParametrosGenerico(0, "");
            recargasSteps.clickAgregar();
            recargasSteps.clickDesplegableDebito();
            recargasSteps.keyDownXParametrosGenerico(0, "");
            recargasSteps.clickContinuar();
            recargasSteps.clickConfirmarGenerico();
            recargasSteps.clickIconoTicket();
            ticket.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //@Test //PARA QUE EL CASO FUNCIONE CORRECTAMENTE LA CUENTA DEBE TENER UNA SÓLA TARJETA DE DÉBITO ASOCIADA
    @Category({Criticos.class})
    @DisplayName("TC-0072-BSJ: Recargas - Adhesión para recarga")
    public void TC0072BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2021);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickCardTransportePublico();
            recargasSteps.clickEmpresa();
            recargasSteps.keyDownToText("SUBE");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.keyDownXParametrosGenerico(0,"");
            recargasSteps.setInputNumero("6061267195495203");
            recargasSteps.setInputPerteneceA("TC0072");
            //dobleFactorSteps.clickSms();
            dobleFactorSteps.clickSms();
            dobleFactorSteps.codigoSegundoFactor();
            recargasSteps.validarRecargaAdherida("6061267195495203 | TC0072B");
            //POSTCONDICION
            recargasSteps.eliminarAdhesaionesdeServicioAPI("","618","6061267195495203","TC0072");
            //TEARDOWN
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }


    @Test
    @Category({Alta.class})
    @DisplayName("TC-0106-BSJ: Recargas - Consulta de recargas")
    public void TC0106BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSanJuan_357);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickConsultasRecargas();
            recargasSteps.validarTituloPageGenerico("Recargas realizadas");
            recargasSteps.clickCardTransportePublico();
            consultaRecargasSteps.clickEnte();
            consultaRecargasSteps.keyDownToText( "SUBE");
            consultaRecargasSteps.clickCanal();
            consultaRecargasSteps.keyDownToText( "Homebanking");
            consultaRecargasSteps.clickNumero();
            consultaRecargasSteps.keyDownToText( "6061");
            consultaRecargasSteps.clickFechaCreacion();
            calendario.clickMesAnterior(5);
            calendario.clickEnDiaXParametro("1");
            calendario.clickBuscar();
            consultaRecargasSteps.validarRecargasRealizadas();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @Category({Media.class})
    @DisplayName("TC-0107-BSJ: Recargas - Consulta de recargas - Boton descarga de comprobante")
    public void TC0107BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uCuyo2021);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickConsultasRecargas();
            recargasSteps.validarTituloPageGenerico("Recargas realizadas");
            recargasSteps.clickCardTransportePublico();
            consultaRecargasSteps.clickEnte();
            recargasSteps.keyDownToText( "SUBE");
            consultaRecargasSteps.clickCanal();
            recargasSteps.keyDownToText( "Homebanking");
            consultaRecargasSteps.clickNumero();
            consultaRecargasSteps.keyDownToText("ABAG");
            consultaRecargasSteps.clickFechaCreacion();
            calendario.retrocederSegunMes(11);
            calendario.clickEnDiaXParametro("12");
            calendario.clickBuscar();
            consultaRecargasSteps.validarDescargarPdfGenerico();
            consultaRecargasSteps.clickDescargarPdfGenerico();
            consultaRecargasSteps.clickVerTicket();
            consultaRecargasSteps.clickIconoDescargar();
            consultaRecargasSteps.validarDescarga("Ticket.pdf");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test
    @Category({Criticos.class})
    @DisplayName("TC-12955-BSJ: Recargas - Realizar recarga a Tarjeta Recargable/Prepagada")
    public void TC12955BSJ() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uSanJuan_357);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickCardtarjetaRecargable();
            recargasSteps.clickEmpresa();
            consultaRecargasSteps.keyDownToText( "VISA REGALO");
            recargasSteps.clickSeleccionarNumero();
            consultaRecargasSteps.keyDownToText( "0014");
            recargasSteps.clickMontoRecarga("1");
            recargasSteps.clickAgregar();
            recargasSteps.clickDesplegableDebito();
            consultaRecargasSteps.keyDownToText( "Caja de Ahorro");
            recargasSteps.clickContinuar();
            recargasSteps.clickConfirmarGenerico();
            recargasSteps.verificarDatosRecargaCuentaOrigen();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test
    @Category({Criticos.class})
    @DisplayName("TC-12956-BSJ: RECARGAS - Eliminar adhesión")
    public void TC12956BSJ() {
        try {
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uSanJuan_357);
            //recargasSteps.realizarAdhesionTelAPI("/api/v1/execute/recharge.new.adhesionWithSecondFactor","605","1133443344",
              //      "TC12956");

            //CASO
            menuLateralSteps.clickRecargas();
            recargasSteps.clickAdhesiones();
            recargasSteps.clickBotonSeleccionar();
            nuevoPagoSteps.keyDownToText("Celular");
            recargasSteps.clickBotonSeleccionar();
            recargasSteps.keyDownToText("Movistar");
            calendario.clickBuscar();
            botonesGenericosSteps.clickIconoEliminar();
            recargasSteps.clickConfirmarGenerico();
            ticket.validarTicket("Tu solicitud se realizo con exito");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12952-BSJ: RECARGAS - Realizar recarga a Telefonía Celular")
    public void TC12952BSJ() {
        try {
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uSanJuan_357);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickCardTelefoniaCelular();
            recargasSteps.clickEmpresa();
            recargasSteps.keyDownToText("Movistar");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.keyDownXParametrosGenerico(0,"");
            recargasSteps.setInputNumero("1144063700");
            recargasSteps.setInputPerteneceA("JUAN");
            recargasSteps.clickSelectorMonto();
            recargasSteps.keyDownToText("$300.00");
            dobleFactorSteps.clickSms();
            dobleFactorSteps.codigoSegundoFactorOtroMetodo();
            recargasSteps.clickAgregar();
            Thread.sleep(1000);
            recargasSteps.clickDesplegableDebito();
            recargasSteps.keyDownToText("Caja de Ahorro $ 15005580528");
            recargasSteps.clickContinuar();
            recargasSteps.clickConfirmarGenerico();
            Thread.sleep(1000);
            recargasSteps.verificarDatosRecargaTelefoniaCelular("Movistar","1144063700","$300.00","No pudimos procesar la operación");
            //POSTCONDICION ELIMINAR ADHESION
            menuLateralSteps.clickCuentas();
            menuLateralSteps.clickRecargas();
            recargasSteps.clickAdhesiones();
            recargasSteps.clickBotonSeleccionar();
            nuevoPagoSteps.keyDownToText("Telefonia Celular");
            recargasSteps.clickBotonSeleccionar();
            recargasSteps.keyDownToText("Movistar");
            calendario.clickBuscar();
            botonesGenericosSteps.clickIconoEliminar();
            recargasSteps.clickConfirmarGenerico();
            ticket.validarTicket("Tu solicitud se realizo con exito");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12954-BSJ: RECARGAS - Realizar recarga a Billetera/Serv. Prepago")
    public void TC12954BSJ() {
        try {
            //CASO
            loginSteps.iniciarSessionAlEscritorio(uSanJuan_357);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickCardBilletera();
            recargasSteps.clickEmpresa();
            recargasSteps.keyDownToText("PIM TU PLATA EN TU CELULAR");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.keyDownXParametrosGenerico(0,"");
            recargasSteps.setInputNumero("1167262379");
            recargasSteps.setInputPerteneceA("TC12954");
            recargasSteps.clickMontoRecarga("1");
            dobleFactorSteps.clickSms();
            dobleFactorSteps.codigoSegundoFactorOtroMetodo();
            recargasSteps.clickAgregar();
            Thread.sleep(1000);
            recargasSteps.clickDesplegableDebito();
            consultaRecargasSteps.keyDownToText( "Caja de Ahorro $ 15005580528");
            recargasSteps.clickContinuar();
            recargasSteps.clickConfirmarGenerico();

            //POSTCONDICION ELIMINAR ADHESION
            recargasSteps.eliminarAdhesionAPI("Sanjuan-357", "https://apitest01.bancosanjuan.com/api/v1/execute/delete.adhesion",
                    "630","1167262379","TC12954","2a67b4afeec287366429bc07d75fa5fe5344dc5c");
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
