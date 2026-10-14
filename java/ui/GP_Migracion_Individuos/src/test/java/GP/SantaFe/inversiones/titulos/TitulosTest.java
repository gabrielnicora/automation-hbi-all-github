package GP.SantaFe.inversiones.titulos;

import GP.Tags.Baja;
import GP.Tags.Criticos;
import GP.Tags.Media;
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
import static utils.Constantes.*;

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
    @DisplayName("TC-29416BSF: TC01-(WEB) Inversiones-Mercado de capitales-Visualizar pantalla Panel de Precios")
    public void TC29416BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29416BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            botonesGenericosSteps.clickMasOpcionesGenerico();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Mis tenencias en Mercado de capitales");
            titulosStep.clickOpcionMisTenenciasEnMercados();
            botonesGenericosSteps.validarTituloPageGenerico("Mercado de capitales");
            titulosStep.clickPanelDePrecios();
            botonesGenericosSteps.validarTituloPageGenerico("Mercado de capitales");
            titulosStep.validarPantallaPrecios();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29418-BSF: TC04-(WEB) Inversiones-Mercado de capitales-Visualizar pantalla Mis tenencias")
    public void TC29418BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29418BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            botonesGenericosSteps.clickMasOpcionesGenerico();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Mis tenencias en Mercado de capitales");
            titulosStep.clickPanelDePrecios();
            titulosStep.clickMisTenencias();
            titulosStep.validarPantallaMisTenencias();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-29419-BSF: TC06-(WEB) Acciones - Visualizar la pantalla Solicitud de Orden con los datos de la misma (Botón Operar-SIN especie Homologa).")
    public void TC29419BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29419BSF");
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
    @DisplayName("TC-29420-BSF: TC07-(WEB) Acciones - Visualizar la pantalla Solicitud de Orden con los datos de la misma (Botón Operar-CON especie Homologa).")
    public void TC29420BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29420BSF");
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
    @DisplayName("TC-29421-BSF: TC08-(WEB) Verificar que en la pantalla -Solicitud de Orden- se muestre componente Tab COMPRAR")
    public void TC29421BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29421BSF");
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
    @DisplayName("TC-29422-BSF: TC09-(WEB) Validar comportamiento cuando se  ingresa una cantidad valida de NOMINALES donde Precio Mercado y el Precio Límite son mayores a cero (Tab COMPRAR).")
    public void TC29422BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29422BSF");
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
    @DisplayName("TC-29423-BSF: TC10-(WEB) Validar comportamiento cuando se  ingresa un MONTO donde Precio Mercado y el Precio Límite son mayores a cero (Tab COMPRAR)")
    public void TC29423BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29423BSF");
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
    @DisplayName("TC-29424-BSF: TC12-(WEB)Inversiones- Mercado de capitales- ACCIONES-Posición  Vender- Validar los campos definidos por default. ")
    public void TC29424BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29424BSF");
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
    @DisplayName("TC-29426-BSF: TC14-(WEB) Inversiones-Mercado de capitales-Redirección a la pantalla de Confirmación de Orden al hacer clic en \"\"Continuar\"\"\n" +
            "(Acciones-Panel Lider)")
    public void TC29426BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29426BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            botonesGenericosSteps.clickMasOpcionesGenerico();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Mis tenencias en Mercado de capitales");
            titulosStep.clickPanelDePrecios();
            titulosStep.clickBtnOperar("2");
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
    @DisplayName("TC-29427-BSF: TC15-(WEB) Inversiones-Mercado de capitales-ACCIONES(Panel lider)-Pantalla Confirmacion de Orden-Validar los campos de la pantalla")
    public void TC29427BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29427BSF");
            loginSteps.iniciarSessionFront(testCaseData.usuario,testCaseData.password, Settings.ESCRITORIO);
            menuLateralSteps.clickInversiones();
            botonesGenericosSteps.clickMasOpcionesGenerico();
            botonesGenericosSteps.clickListaDesplegableGenerico2("Mis tenencias en Mercado de capitales");
            titulosStep.clickPanelDePrecios();
            titulosStep.clickBtnOperar("2");
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
    @DisplayName("TC-29428-BSF: TC16-(WEB) - ACCIONES (Panel Lider) - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Compra Exitosa (PESOS). ")
    public void TC29428BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29428BSF");
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
    @DisplayName("TC-29429-BSF: TC17-(WEB) - ACCIONES (Panel Lider) - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (PESOS). ")
    public void TC29429BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29429BSF");
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
    @DisplayName("TC-29430-BSF: TC22-(WEB) - BONOS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Compra Exitosa (PESOS). ")
    public void TC29430BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29430BSF");
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
    @DisplayName("TC-29431-BSF: TC18-(WEB) - ACCIONES (Panel general) - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Compra Exitosa (PESOS). ")
    public void TC29431BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29431BSF");
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
    @DisplayName("TC-29432-BSF: TC19-(WEB) - ACCIONES (Panel general)- Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (PESOS). ")
    public void TC29432BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29432BSF");
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
    @DisplayName("TC-29433-BSF: TC20-(WEB) - CEDEARS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Compra Exitosa (PESOS). ")
    public void TC29433BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29433BSF");
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
    @DisplayName("TC-29434-BSF: TC21-(WEB) - CEDEARS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (PESOS). ")
    public void TC29434BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29434BSF");
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
    @DisplayName("TC-29435-BSF: TC23-(WEB) - BONOS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (PESOS). ")
    public void TC29435BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29435BSF");
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
    @DisplayName("TC-29436-BSF: TC26-(WEB) - ONS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Compra Exitosa (PESOS). ")
    public void TC29436BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29436BSF");
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
    @DisplayName("TC-29437-BSF: TC27-(WEB) - ONS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (PESOS). ")
    public void TC29437BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29437BSF");
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
    @DisplayName("TC-29438-BSF: TC29-(WEB) - ACCIONES (Panel lider)  - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (DOLARES). ")
    public void TC29438BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29438BSF");
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
    @DisplayName("TC-29439-BSF: TC29-(WEB) - ACCIONES (Panel lider)  - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (DOLARES). ")
    public void TC29439BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29439BSF");
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
    @DisplayName("TC-29440-BSF: TC32-(WEB) - CEDEARS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Compra Exitosa (DOLARES). ")
    public void TC29440BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29440BSF");
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
    @DisplayName("TC-29441-BSF: TC29-(WEB) - ACCIONES (Panel lider)  - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (DOLARES). ")
    public void TC29441BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29441BSF");
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
    @DisplayName("TC-29442-BSF: TC34-(WEB) - BONOS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Compra Exitosa (DOLARES). ")
    public void TC29442BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29442BSF");
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
    @DisplayName("TC-29443-BSF: TC35-(WEB) - BONOS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (DOLARES). ")
    public void TC29443BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29443BSF");
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
    @DisplayName("TC-29444-BSF: TC36-(WEB) - LETRAS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Compra Exitosa (DOLARES). ")
    public void TC29444BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29444BSF");
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
    @DisplayName("TC-29445-BSF: TC37-(WEB) - LETRAS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (DOLARES). ")
    public void TC29445BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29445BSF");
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
    @DisplayName("TC-29446-BSF: TC38-(WEB) - ONS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Compra Exitosa (DOLARES). ")
    public void TC29446BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29446BSF");
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
    @DisplayName("TC-29447-BSF: TC39-(WEB) - ONS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (DOLARES). ")
    public void TC29447BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29447BSF");
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
    }@Test
    @DisplayName("TC-29448-BSF: TC24-(WEB) - LETRAS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Compra Exitosa (PESOS). ")
    public void TC29448BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29448BSF");
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
    @DisplayName("TC-29449-BSF: TC25-(WEB) - LETRAS - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Venta Exitosa (PESOS). ")
    public void TC29449BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29449BSF");
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
    @DisplayName("TC-29450-BSF: TC30-(WEB) - ACCIONES (Panel general)  - Inversiones-Mercado de capitales-Vizualizacion de Ticket  Solicitud de Orden de Compra Exitosa (DOLARES). ")
    public void TC29450BSF() {
        try {
            testCaseData.cargaDeDatosTests("TC29450BSF");
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
