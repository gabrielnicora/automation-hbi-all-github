package GP.EntreRios.recargas;

import GP.Tags.Alta;
import GP.Tags.Criticos;
import GP.Tags.Media;
import db.ConexionDB;
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
import utils.Constantes;
import utils.MetodosAPI;
import utils.TestCaseData;
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
    DobleFactorSteps dobleFactorSteps = new DobleFactorSteps();
    ConsultaRecargasSteps consultaRecargasSteps = new ConsultaRecargasSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    CalendarioSteps calendario = new CalendarioSteps();
    TicketSteps ticket= new TicketSteps();
    NuevoPagoSteps nuevoPagoSteps = new NuevoPagoSteps();
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

    private String testActual;
    private static String CLARO_ID = "600";
    private static String VISA_RECARGABLE_ID = "609";
    private final List<String> tcsAdhierenRecargas = new ArrayList<String>() {
        {
            add("TC0071BERSA");
            //add("TC0072BERSA");
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

    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0071-BERSA: RECARGAS - Realizar recarga a Transporte Publico")
    public void TC0071BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uTranspull1);
            loginSteps.iniciarSessionAlEscritorioConPass(uTranspull1,testCaseData.password);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickCardTransportePublico();
            recargasSteps.clickEmpresa();
            recargasSteps.keyDownToText("SUBE");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.keyDownXParametrosGenerico(0,"");
            recargasSteps.setInputNumero("6061267195495200");
            recargasSteps.setInputPerteneceA("AUTOMATION");
            //dobleFactorSteps.codigoSegundoFactorOtroMetodo(uTranspull1);
            recargasSteps.clickSelectorMonto();
            recargasSteps.keyDownXParametrosGenerico(0,"");
            recargasSteps.clickAgregar();
            recargasSteps.clickCuentaADebitar();
            recargasSteps.keyDownXParametrosGenerico(0, "");
            recargasSteps.clickContinuar();
            recargasSteps.clickConfirmarGenerico();
            recargasSteps.verificarDatosRecargaCuentaOrigen();
            recargasSteps.clickIconoTicket();
            ticket.validarTicket("Tu solicitud se realizo con exito");
            //TEARDOWN
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0072-BERSA: Recargas - Adhesión para recarga-SUBE")
    public void TC0072BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uEcarina27);
            loginSteps.iniciarSessionAlEscritorioConPass(uEcarina27,testCaseData.password);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickSelectorTarjetaDebito();
            recargasSteps.keyDownToText("8006");
            recargasSteps.clickContinuarGenerico();
            recargasSteps.clickCardTransportePublico();
            recargasSteps.clickEmpresa();
            recargasSteps.keyDownToText("SUBE");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.keyDownXParametrosGenerico(0,"");
            recargasSteps.setInputNumero("6061267195495203");
            recargasSteps.setInputPerteneceA("TC0072");
            //dobleFactorSteps.codigoSegundoFactor(uEcarina27);
            recargasSteps.validarRecargaAdherida("6061267195495203 | TC0072");
            //POSTCONDICION
            recargasSteps.eliminarAdhesaionesdeServicioAPI("618","6061267195495203","TC0072","8006");
            //TEARDOWN
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Alta.class})
    @DisplayName("TC-0106-BERSA: Recargas - Consulta de recargas-SUBE")
    public void TC0106BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uEcarina27);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickConsultasRecargas();
            recargasSteps.validarTituloPageGenerico("Recargas realizadas");
            //Se saca selector de TD (Pediente para mejora!)
            /*ConsultaRecargasSteps consultaRecargasSteps=new ConsultaRecargasSteps();*/
            consultaRecargasSteps.clickTarjetaDebito();
            consultaRecargasSteps.keyDownToText( "3009");
            //recargasSteps.keyDownXParametrosGenerico(2, "3009");
            recargasSteps.clickContinuarGenerico();
            recargasSteps.clickCardTransportePublico();
            consultaRecargasSteps.clickEnte();
            consultaRecargasSteps.keyDownToText("SUBE");
            consultaRecargasSteps.clickCanal();
            consultaRecargasSteps.keyDownToText("Homebanking");
            consultaRecargasSteps.clickNumero();
            consultaRecargasSteps.keyDownXParametrosGenerico(1,"");
            consultaRecargasSteps.clickFechaCreacion();
            CalendarioSteps calendario = new CalendarioSteps();
            calendario.clickMesAnterior(24);
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
    @DisplayName("TC-0107-BERSA: Recargas - Consulta de recargas - Botón descarga de comprobante-SUBE")
    public void TC0107BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uUathb04);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickConsultasRecargas();
            recargasSteps.validarTituloPageGenerico("Recargas realizadas");
            recargasSteps.clickCardTransportePublico();
            consultaRecargasSteps.clickEnte();
            consultaRecargasSteps.keyDownToText( "SUBE");
            consultaRecargasSteps.clickNumero();
            consultaRecargasSteps.keyDownXParametrosGenerico(1, "DANILO");
            consultaRecargasSteps.clickCanal();
            consultaRecargasSteps.keyDownToText( "Homebanking");
            consultaRecargasSteps.clickFechaCreacion();
            calendario.retrocederSegunMes(11);
            calendario.clickEnDiaXParametro("1");
            consultaRecargasSteps.validarFechaDesde("1/11/2023");
            calendario.clickBuscar();
            consultaRecargasSteps.validarNumeroTarjetaSube("6061267159402278");
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
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12955-BERSA: Realizar recarga a Tarjeta Recargable/Prepagada")
    public void TC12955BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uTranspull1);
            loginSteps.iniciarSessionAlEscritorioConPass(uTranspull1,testCaseData.password);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickCardtarjetaRecargable();
            recargasSteps.clickEmpresa();
            consultaRecargasSteps.keyDownToText( "VISA RECARGABLE");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.keyDownXParametrosGenerico(0,"");
            recargasSteps.setInputNumero("4217390000000010");
            recargasSteps.setInputPerteneceA( "JUAN");
            recargasSteps.clickMontoRecarga("1");
            dobleFactorSteps.clickSms();
            //dobleFactorSteps.codigoSegundoFactor(uTranspull1);
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
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12956-BERSA: RECARGAS - Eliminar adhesión")
    public void TC12956BERSA() {
        try {
            //PRECONDICION
            testCaseData.cargaDeDatosTest(uUathb04);
            loginSteps.iniciarSessionAlEscritorioConPass(uUathb04,testCaseData.password);
            recargasSteps.realizarAdhesionAPI(CLARO_ID,"5557504183","PEDRO","6009");

            //CASO
            menuLateralSteps.clickRecargas();
            recargasSteps.clickAdhesiones();
            recargasSteps.clickBotonSeleccionar();
            nuevoPagoSteps.keyDownToText("Celular");
            recargasSteps.clickBotonSeleccionar();
            recargasSteps.keyDownToText("CLARO");
            calendario.clickBuscar();
            recargasSteps.clickIconoEliminarAdhesion("PEDRO");
            recargasSteps.clickConfirmarGenerico();
            ticket.validarTicket("Tu solicitud se realizo con exito");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-12952-BERSA: RECARGAS - Realizar recarga a Telefonía Celular")
    public void TC12952BERSA() {
        try {
            //TEST
            testCaseData.cargaDeDatosTest(uUathb04);
            loginSteps.iniciarSessionAlEscritorioConPass(uUathb04,testCaseData.password);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickCardTelefoniaCelular();
            recargasSteps.clickEmpresa();
            recargasSteps.keyDownToText("CLARO");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.keyDownXParametrosGenerico(0,"");
            recargasSteps.setInputNumero("5557504183");
            recargasSteps.setInputPerteneceA("JUAN");
            recargasSteps.clickSelectorMonto();
            recargasSteps.keyDownToText("$500.00");
            //dobleFactorSteps.codigoSegundoFactorOtroMetodo(uUathb04);
            recargasSteps.clickAgregar();
            recargasSteps.clickDesplegableDebito();
            recargasSteps.keyDownToText("Cuenta Corriente $ 035000023427");
            recargasSteps.clickContinuar();
            recargasSteps.clickConfirmarGenerico();
            Thread.sleep(1000);
            recargasSteps.verificarDatosRecargaTelefoniaCelular("CLARO","5557504183","500.00","No pudimos procesar la operación");
            //POSTCONDICION ELIMINAR ADHESION
            recargasSteps.eliminarAdhesionAPI(uUathb04,"CLARO",CLARO_ID,"5557504183","JUAN","6009");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test
    @Category({Criticos.class})
    @DisplayName("TC-12954-BERSA: RECARGAS - Realizar recarga a Billetera/Serv. Prepago")
    public void TC12954BERSA() {
        try {
            //CASO
            loginSteps.iniciarSessionAlEscritorio(uUathb04);
            menuLateralSteps.clickRecargas();
            recargasSteps.clickCardBilletera();
            recargasSteps.clickEmpresa();
            recargasSteps.keyDownToText("PIM TU PLATA EN TU CELULAR");
            recargasSteps.clickSeleccionarNumero();
            recargasSteps.keyDownXParametrosGenerico(0,"");
            recargasSteps.setInputNumero("1139448145");
            recargasSteps.setInputPerteneceA("TC-12954");
            recargasSteps.clickMontoRecarga("1");
            dobleFactorSteps.clickSms();
            //dobleFactorSteps.codigoSegundoFactorOtroMetodo(uUathb04);
            recargasSteps.clickAgregar();
            Thread.sleep(1000);
            recargasSteps.clickDesplegableDebito();
            consultaRecargasSteps.keyDownToText( "Cuenta Corriente $ 035000023427");
            recargasSteps.clickContinuar();
            recargasSteps.clickConfirmarGenerico();
            recargasSteps.verificarDatosRecargaTelefoniaCelular("PIM TU PLATA EN TU CELULAR","1139448145","1,00","No pudimos procesar la operación");
            //POSTCONDICION ELIMINAR ADHESION
            menuLateralSteps.clickCuentas();
            menuLateralSteps.clickRecargas();
            recargasSteps.clickAdhesiones();
            recargasSteps.clickBotonSeleccionar();
            nuevoPagoSteps.keyDownToText("BILLETERA/SERV PREPAGO");
            recargasSteps.clickBotonSeleccionar();
            recargasSteps.keyDownToText("PIM TU PLATA EN TU CELULAR");
            calendario.clickBuscar();
            botonesGenericosSteps.clickIconoEliminar();
            recargasSteps.clickConfirmarGenerico();
            ticket.validarTicket("Tu solicitud se realizo con exito");

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