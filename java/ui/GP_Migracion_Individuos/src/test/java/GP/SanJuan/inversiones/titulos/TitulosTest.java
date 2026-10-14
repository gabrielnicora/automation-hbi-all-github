package GP.SanJuan.inversiones.titulos;

import db.ConexionDB;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.inversiones.InversionesSteps;
import steps.inversiones.compraventadedolares.CompraVentaDeDolaresSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import steps.ticket.TicketSteps;
import utils.Constantes;
import utils.Settings;
import utils.TestCaseData;
import utils.UtilsGP;
import steps.inversiones.titulos.TitulosStep;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;


public class TitulosTest {
    LoginSteps loginSteps = new LoginSteps();
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    InversionesSteps inversionesSteps = new InversionesSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    TestCaseData testCaseData = new TestCaseData();
    UtilsGP utilsGP = new UtilsGP();
    TitulosStep titulosStep = new TitulosStep();

    @BeforeClass
    public static void extraerDatosBD(){
        System.out.println("============= BEFORE CLASS ==========================");
        if(Constantes.usuarios == null){
            System.out.println("============= Array de datos vacio extraer desde la BD ==============");
            Settings.dataTestCase = ConexionDB.usuariosDB();
            System.out.println("============= se completo el array de datos ==========================");
        }else {
            System.out.println("================ EL ARRAY CONTIENE DATOS ===================");
        }
    }

    @Test
    @DisplayName("TC-29416-BSJ: TC01-(WEB) Inversiones-Mercado de capitales-Visualizar pantalla Panel de Precios")
    public void TC29416BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29416BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            botonesGenericosSteps.clickMasOpcionesGenerico();
            botonesGenericosSteps.clickListaDesplegableGenerico("Mis tenencias en Mercado de capitales");
            titulosStep.clickPanelDePrecios();
            titulosStep.validarPantallaPrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29418-BSJ: TC04-(WEB) Inversiones-Mercado de capitales-Visualizar pantalla Mis tenencias")
    public void TC29418BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29418BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            botonesGenericosSteps.clickMasOpcionesGenerico();
            botonesGenericosSteps.clickListaDesplegableGenerico("Mis tenencias en Mercado de capitales");
            titulosStep.clickPanelDePrecios();
            titulosStep.clickMisTenencias();
            titulosStep.validarPantallaMisTenencias();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29419-BSJ: TC06-(WEB) Acciones - Visualizar la pantalla Solicitud de Orden con los datos de la misma (Botón Operar-SIN especie Homologa).")
    public void TC29419BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29419BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            botonesGenericosSteps.clickMasOpcionesGenerico();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Mis tenencias en Mercado de capitales");
            titulosStep.clickPanelDePrecios();
            titulosStep.validarPantallaSolicitudDeOrdenSinHomologoDolares();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29420-BSJ: TC07-(WEB) Acciones - Visualizar la pantalla Solicitud de Orden con los datos de la misma (Botón Operar-CON especie Homologa).")
    public void TC29420BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29420BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            botonesGenericosSteps.clickMasOpcionesGenerico();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Mis tenencias en Mercado de capitales");
            titulosStep.clickPanelDePrecios();
            titulosStep.validarPantallaSolicitudDeOrdenConHomologoDolares();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29421-BSJ: TC08-(WEB) Verificar que en la pantalla -Solicitud de Orden- se muestre componente Tab COMPRAR")
    public void TC29421BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29421BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            botonesGenericosSteps.clickMasOpcionesGenerico();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Mis tenencias en Mercado de capitales");
            titulosStep.clickPanelDePrecios();
            titulosStep.clickBtnOperar("1");
            titulosStep.validarTabComprar();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29422-BSJ: TC09-(WEB) Validar comportamiento cuando se  ingresa una cantidad valida de NOMINALES donde Precio Mercado y el Precio Límite son mayores a cero (Tab COMPRAR).")
    public void TC29422BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29422BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            botonesGenericosSteps.clickMasOpcionesGenerico();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Mis tenencias en Mercado de capitales");
            titulosStep.clickPanelDePrecios();
            titulosStep.clickBtnOperar("1");
            titulosStep.validarCantidadNominales();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29423-BSJ: TC10-(WEB) Validar comportamiento cuando se  ingresa un MONTO donde Precio Mercado y el Precio Límite son mayores a cero (Tab COMPRAR)")
    public void TC29423BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29423BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            botonesGenericosSteps.clickMasOpcionesGenerico();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Mis tenencias en Mercado de capitales");
            titulosStep.clickPanelDePrecios();
            titulosStep.clickBtnOperar("1");
            titulosStep.validarIngresoMontoComprar();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29424-BSJ: TC12-(WEB)Inversiones- Mercado de capitales- ACCIONES-Posición  Vender- Validar los campos definidos por default. ")
    public void TC29424BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29424BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            botonesGenericosSteps.clickMasOpcionesGenerico();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Mis tenencias en Mercado de capitales");
            titulosStep.clickPanelDePrecios();
            titulosStep.clickBtnOperar("1");
            titulosStep.validarTabVender();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29426-BSJ: TC14-(WEB) Inversiones-Mercado de capitales-Redirección a la pantalla de Confirmación de Orden al hacer clic en \"\"Continuar\"\"\n" +
            "(Acciones-Panel Lider)")
    public void TC29426BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29426BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            botonesGenericosSteps.clickMasOpcionesGenerico();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Mis tenencias en Mercado de capitales");
            titulosStep.clickPanelDePrecios();
            titulosStep.clickBtnOperar("3");
            titulosStep.validarIngresoMontoComprar();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenCompra();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarConfirmacionRealizada();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29427-BSJ: TC15-(WEB) Inversiones-Mercado de capitales-ACCIONES(Panel lider)-Pantalla Confirmacion de Orden-Validar los campos de la pantalla")
    public void TC29427BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29427BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            botonesGenericosSteps.clickMasOpcionesGenerico();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Mis tenencias en Mercado de capitales");
            titulosStep.clickPanelDePrecios();
            titulosStep.clickBtnOperar("3");
            titulosStep.validarIngresoMontoVenta();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenVenta();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarConfirmacionRealizada();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29428-BSJ: TC16-(WEB) - ACCIONES (Panel Lider) - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Compra Exitosa (PESOS). ")
    public void TC29428BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29428BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            titulosStep.clickGestionaTusInversiones();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Acciones");
            titulosStep.clickBtnOperar("2");
            titulosStep.validarIngresoMontoComprar();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenCompra();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarTicketCompraExitosa();
            titulosStep.validarPantallaPanelDePrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29429-BSJ: TC17-(WEB) - ACCIONES (Panel Lider) - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (PESOS). ")
    public void TC29429BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29429BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            botonesGenericosSteps.clickMasOpcionesGenerico();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Mis tenencias en Mercado de capitales");
            titulosStep.clickPanelDePrecios();
            titulosStep.clickBtnOperar("1");
            titulosStep.validarIngresoMontoVenta();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenVenta();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarTicketVentaExitosa();
            titulosStep.validarPantallaPanelDePrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29430-BSJ: TC22-(WEB) - BONOS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Compra Exitosa (PESOS). ")
    public void TC29430BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29430BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            botonesGenericosSteps.clickMasOpcionesGenerico();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Mis tenencias en Mercado de capitales");
            titulosStep.clickPanelDePrecios();
            titulosStep.clickBtnInstrumentos("Bonos públicos");
            titulosStep.clickBtnOperar("2");
            titulosStep.validarIngresoMontoComprar();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenCompra();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarTicketCompraExitosa();
            titulosStep.validarPantallaPanelDePrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29431-BSJ: TC18-(WEB) - ACCIONES (Panel general) - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Compra Exitosa (PESOS). ")
    public void TC29431BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29431BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            titulosStep.clickGestionaTusInversiones();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Acciones");
            titulosStep.clickPanelAcciones();
            botonesGenericosSteps.clickListaDesplegableGenerico("Panel general");
            titulosStep.clickBtnOperar("1");
            titulosStep.validarCartelInformativoAlgoFallo();
            titulosStep.validarIngresoMontoComprar();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenCompra();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarTicketCompraExitosa();
            titulosStep.validarPantallaPanelDePrecios();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29432-BSJ: TC19-(WEB) - ACCIONES (Panel general)- Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (PESOS). ")
    public void TC29432BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29432BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            titulosStep.clickGestionaTusInversiones();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Acciones");
            titulosStep.clickPanelAcciones();
            botonesGenericosSteps.clickListaDesplegableGenerico("Panel general");
            titulosStep.clickBtnOperar("1");
            titulosStep.validarCartelInformativoAlgoFallo();
            titulosStep.validarIngresoMontoVenta();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenVenta();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarTicketVentaExitosa();
            titulosStep.validarPantallaPanelDePrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29433-BSJ: TC20-(WEB) - CEDEARS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Compra Exitosa (PESOS). ")
    public void TC29433BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29433BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            titulosStep.clickGestionaTusInversiones();
            botonesGenericosSteps.clickListaDesplegableGenerico2("CEDEARs");
            titulosStep.clickBtnOperar("1");
            titulosStep.validarCartelInformativoAlgoFallo();
            titulosStep.validarIngresoMontoComprar();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenCompra();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarTicketCompraExitosa();
            titulosStep.validarPantallaPanelDePrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29434-BSJ: TC21-(WEB) - CEDEARS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (PESOS). ")
    public void TC29434BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29434BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            titulosStep.clickGestionaTusInversiones();
            botonesGenericosSteps.clickListaDesplegableGenerico2("CEDEARs");
            titulosStep.clickBtnOperar("1");
            titulosStep.validarCartelInformativoAlgoFallo();
            titulosStep.validarIngresoMontoVenta();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenVenta();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarTicketVentaExitosa();
            titulosStep.validarPantallaPanelDePrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29435-BSJ: TC23-(WEB) - BONOS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (PESOS). ")
    public void TC29435BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29435BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            titulosStep.clickGestionaTusInversiones();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Bonos públicos");
            titulosStep.clickBtnOperar("5");
            titulosStep.validarCartelInformativoAlgoFallo();
            titulosStep.validarIngresoMontoVenta();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenVenta();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarTicketVentaExitosa();
            titulosStep.validarPantallaPanelDePrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29436-BSJ: TC26-(WEB) - ONS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Compra Exitosa (PESOS). ")
    public void TC29436BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29436BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            titulosStep.clickGestionaTusInversiones();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Obligaciones negociables");
            titulosStep.clickBtnOperar("1");
            titulosStep.validarCartelInformativoAlgoFallo();
            titulosStep.validarIngresoMontoComprar();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenCompra();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarTicketCompraExitosa();
            titulosStep.validarPantallaPanelDePrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29437-BSJ: TC27-(WEB) - ONS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (PESOS). ")
    public void TC29437BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29437BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            titulosStep.clickGestionaTusInversiones();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Bonos públicos");
            titulosStep.clickBtnOperar("5");
            titulosStep.validarCartelInformativoAlgoFallo();
            titulosStep.validarIngresoMontoVenta();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenVenta();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarTicketVentaExitosa();
            titulosStep.validarPantallaPanelDePrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29438-BSJ: TC29-(WEB) - ACCIONES (Panel lider)  - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (DOLARES). ")
    public void TC29438BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29438BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            titulosStep.clickGestionaTusInversiones();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Acciones");
            titulosStep.clickCheckMoneda("Dólares");
            titulosStep.clickBtnOperar("1");
            titulosStep.validarCartelInformativoAlgoFallo();
            titulosStep.validarIngresoMontoComprar();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenCompraDolares();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarTicketCompraExitosaDolares();
            titulosStep.validarPantallaPanelDePrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29439-BSJ: TC29-(WEB) - ACCIONES (Panel lider)  - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (DOLARES). ")
    public void TC29439BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29439BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            titulosStep.clickGestionaTusInversiones();
            botonesGenericosSteps.clickListaDesplegableGenerico2("CEDEARs");
            titulosStep.clickCheckMoneda("Dólares");
            titulosStep.clickBtnOperar("2");
            titulosStep.validarCartelInformativoAlgoFallo();
            titulosStep.validarIngresoMontoVenta();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenVentaDolares();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarTicketVentaExitosaDolares();
            titulosStep.validarPantallaPanelDePrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29440-BSJ: TC32-(WEB) - CEDEARS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Compra Exitosa (DOLARES). ")
    public void TC29440BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29440BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            titulosStep.clickGestionaTusInversiones();
            botonesGenericosSteps.clickListaDesplegableGenerico2("CEDEARs");
            titulosStep.clickCheckMoneda("Dólares");
            titulosStep.clickBtnOperar("1");
            titulosStep.validarCartelInformativoAlgoFallo();
            titulosStep.validarIngresoMontoComprar();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenCompraDolares();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarTicketCompraExitosaDolares();
            titulosStep.validarPantallaPanelDePrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29441-BSJ: TC29-(WEB) - ACCIONES (Panel lider)  - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (DOLARES). ")
    public void TC29441BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29441BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            titulosStep.clickGestionaTusInversiones();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Acciones");
            titulosStep.clickCheckMoneda("Dólares");
            titulosStep.clickBtnOperar("1");
            titulosStep.validarCartelInformativoAlgoFallo();
            titulosStep.validarIngresoMontoVenta();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenVentaDolares();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarTicketVentaExitosaDolares();
            titulosStep.validarPantallaPanelDePrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29442-BSJ: TC34-(WEB) - BONOS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Compra Exitosa (DOLARES). ")
    public void TC29442BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29442BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            titulosStep.clickGestionaTusInversiones();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Bonos públicos");
            titulosStep.clickCheckMoneda("Dólares");
            titulosStep.clickBtnOperar("1");
            titulosStep.validarCartelInformativoAlgoFallo();
            titulosStep.validarIngresoMontoComprar();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenCompraDolares();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarTicketCompraExitosaDolares();
            titulosStep.validarPantallaPanelDePrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29443-BSJ: TC35-(WEB) - BONOS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (DOLARES). ")
    public void TC29443BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29443BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            titulosStep.clickGestionaTusInversiones();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Bonos públicos");
            titulosStep.clickCheckMoneda("Dólares");
            titulosStep.clickBtnOperar("1");
            titulosStep.validarCartelInformativoAlgoFallo();
            titulosStep.validarIngresoMontoVenta();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenVentaDolares();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarTicketVentaExitosaDolares();
            titulosStep.validarPantallaPanelDePrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29444-BSJ: TC36-(WEB) - LETRAS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Compra Exitosa (DOLARES). ")
    public void TC29444BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29444BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            titulosStep.clickGestionaTusInversiones();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Letras");
            titulosStep.clickCheckMoneda("Dólares");
            titulosStep.clickBtnOperar("1");
            titulosStep.validarCartelInformativoAlgoFallo();
            titulosStep.validarIngresoMontoComprar();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenCompraDolares();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarTicketCompraExitosaDolares();
            titulosStep.validarPantallaPanelDePrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29445-BSJ: TC37-(WEB) - LETRAS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (DOLARES). ")
    public void TC29445BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29445BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            titulosStep.clickGestionaTusInversiones();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Letras");
            titulosStep.clickCheckMoneda("Dólares");
            titulosStep.clickBtnOperar("1");
            titulosStep.validarCartelInformativoAlgoFallo();
            titulosStep.validarIngresoMontoVenta();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenVentaDolares();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarTicketVentaExitosaDolares();
            titulosStep.validarPantallaPanelDePrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29446-BSJ: TC38-(WEB) - ONS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Compra Exitosa (DOLARES). ")
    public void TC29446BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29446BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            titulosStep.clickGestionaTusInversiones();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Obligaciones negociables - ONs");
            titulosStep.clickCheckMoneda("Dólares");
            titulosStep.clickBtnOperar("1");
            titulosStep.validarCartelInformativoAlgoFallo();
            titulosStep.validarIngresoMontoComprar();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenCompraDolares();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarTicketCompraExitosaDolares();
            titulosStep.validarPantallaPanelDePrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29447-BSJ: TC39-(WEB) - ONS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (DOLARES). ")
    public void TC29447BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29447BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            titulosStep.clickGestionaTusInversiones();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Obligaciones negociables - ONs");
            titulosStep.clickCheckMoneda("Dólares");
            titulosStep.clickBtnOperar("1");
            titulosStep.validarCartelInformativoAlgoFallo();
            titulosStep.validarIngresoMontoVenta();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenVentaDolares();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarTicketVentaExitosaDolares();
            titulosStep.validarPantallaPanelDePrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29448-BSJ: TC24-(WEB) - LETRAS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Compra Exitosa (PESOS). ")
    public void TC29448BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29448BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            titulosStep.clickGestionaTusInversiones();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Letras");
            titulosStep.clickBtnOperar("1");
            titulosStep.validarCartelInformativoAlgoFallo();
            titulosStep.validarIngresoMontoComprar();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenCompraLetras();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarTicketCompraExitosaLetras();
            titulosStep.validarPantallaPanelDePrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29449-BSJ: TC25-(WEB) - LETRAS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (PESOS). ")
    public void TC29449BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29449BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            titulosStep.clickGestionaTusInversiones();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Letras");
            titulosStep.clickBtnOperar("1");
            titulosStep.validarCartelInformativoAlgoFallo();
            titulosStep.validarIngresoMontoVenta();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenVenta();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarTicketVentaExitosa();
            titulosStep.validarPantallaPanelDePrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29450-BSJ: TC30-(WEB) - ACCIONES (Panel general)  - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Compra Exitosa (DOLARES). ")
    public void TC29450BSJ() {
        try {
            testCaseData.cargaDeDatosTests("TC29450BSJ");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            titulosStep.clickGestionaTusInversiones();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Acciones");
            titulosStep.clickCheckMoneda("Dólares");
            titulosStep.clickBtnOperar("1");
            titulosStep.validarCartelInformativoAlgoFallo();
            titulosStep.validarIngresoMontoComprar();
            titulosStep.clickBtnContinuarConfirmar("Continuar");
            titulosStep.validarPantallaConfirmacionDeOrdenCompraDolares();
            titulosStep.clickBtnContinuarConfirmar("Confirmar");
            titulosStep.validarTicketCompraExitosaDolares();
            titulosStep.validarPantallaPanelDePrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }



    @After
    public void cerrarSesionCapturarUPS(){
        if(utilsGP.estaPresente(botonesGenericosPage.getCodigoUps(),2) && botonesGenericosPage.getCodigoUps().getText().startsWith("#")) {
            String ups = botonesGenericosPage.getCodigoUps().getText();
            global.practia.jemeinsamstark.web.core.WebDriver.getInstance().navigate().to(System.getProperty("appUrl"));
            System.out.println("Se Espera que aparezca la pagina de nuevo");
            loginSteps.waitGP();
            Assert.fail("UPS CODIGO : " + ups);
        }else{
            System.out.println("=================== NO PUDO INICIAR SESION REFRESCAR NAVEGADOR ==============================");
            Allure.addAttachment("INICIO SESSION,","=================== NO PUDO INICIAR SESION REFRESCAR NAVEGADOR ==============================");
            global.practia.jemeinsamstark.web.core.WebDriver.getInstance().navigate().to(System.getProperty("appUrl"));
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
