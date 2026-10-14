package GP.SantaCruz.herramientas_de_accesibilidad;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import steps.cuentas.CuentasSteps;
import steps.cuentas.detallecuenta.DetalleCuentaSteps;
import steps.cuentas.solicituddechequera.SolicitudDeChequeraSteps;
import steps.doblefactor.DobleFactorSteps;
import steps.herramientas_de_accesibilidad.CuentasHerramientasAccesibilidadSteps;
import steps.herramientas_de_accesibilidad.HerramientasDeAccesibilidadSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.*;
import static utils.Constantes.uBsc13400488;

public class CuentasHerramientasDeAccesibilidadTest {
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    CuentasSteps cuentasSteps = new CuentasSteps();
    LoginSteps loginSteps = new LoginSteps();
    HerramientasDeAccesibilidadSteps herramientasDeAccesibilidadSteps = new HerramientasDeAccesibilidadSteps();
    CuentasHerramientasAccesibilidadSteps cuentasHerramientasAccesibilidadSteps = new CuentasHerramientasAccesibilidadSteps();

    DetalleCuentaSteps detalleCuentaSteps = new DetalleCuentaSteps();
    DobleFactorSteps dobleFactorSteps = new DobleFactorSteps();
    SolicitudDeChequeraSteps solicitudDeChequeraSteps = new SolicitudDeChequeraSteps();

    @Test
    @DisplayName("TC-0247-BSC: Herramientas de Accesibilidad Cuentas - Consulta y modificación de Alias de CBU ")
    public void TC0247BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            cuentasHerramientasAccesibilidadSteps.ingresarACuentas();
            cuentasHerramientasAccesibilidadSteps.presionarTecla26();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Cuentas");
            cuentasHerramientasAccesibilidadSteps.ingresarAVerMasCuenta(1);
            cuentasHerramientasAccesibilidadSteps.modificarAlias();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Por su seguridad, vamos a validar su identidad");
            cuentasHerramientasAccesibilidadSteps.validarInputSegundoFactor();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0248-BSC: Herramientas de Accesibilidad Cuentas - Solicitud de chequera - Normal -25 cheques - 1 chequera")
    public void TC0248BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickCuentas();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Cuentas");
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            cuentasHerramientasAccesibilidadSteps.ingresarAVerMasCuenta(5);
            cuentasHerramientasAccesibilidadSteps.ingresarASolicitudDeChequera();
            solicitudDeChequeraSteps.validarTituloPageGenerico("Solicitud de Chequera");
            cuentasHerramientasAccesibilidadSteps.completarFormularioSolicitudChequera();
            cuentasHerramientasAccesibilidadSteps.validacionPantallaConfirmacionSolicitudChequera();
            cuentasHerramientasAccesibilidadSteps.validarInputSegundoFactor();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0249-BSC: Herramientas de Accesibilidad Cuentas - Acceder a Generar Orden de Extracción - Completar formulario")
    public void TC0249BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickCuentas();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Cuentas");
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            cuentasHerramientasAccesibilidadSteps.ingresarAGenerarOrdenDeExtraccion();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Generar orden de extracción");
            cuentasHerramientasAccesibilidadSteps.formularioGenerarOrdenExtraccionConCuentaOrigen();
            cuentasHerramientasAccesibilidadSteps.validarInputSegundoFactor();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0250-BSC: Herramientas de Accesibilidad Cuentas - Agenda destinatario - Adherir nuevo destinatario")
    public void TC0250BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickCuentas();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Cuentas");
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            cuentasHerramientasAccesibilidadSteps.ingresarAgendaDestinatario();
            cuentasHerramientasAccesibilidadSteps.adherirNuevoDestinatario();
            cuentasHerramientasAccesibilidadSteps.validarInputSegundoFactor();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    @Test
    @DisplayName("TC-0265-BSC: Herramientas de Accesibilidad Cuentas - Solicitar nueva cuenta - Completar formulario y validar que se redirige a pantalla de confirmacion")
    public void TC0265BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickCuentas();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Cuentas");
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            cuentasHerramientasAccesibilidadSteps.ingresarASolicitarCuentaNueva();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Solicitar nueva cuenta");
            cuentasHerramientasAccesibilidadSteps.completarFormularioSolicitarCuentaNueva();
            cuentasHerramientasAccesibilidadSteps.validarInputSegundoFactor();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0266-BSC: Herramientas de Accesibilidad Cuentas- Listado de transacciones - Validar que se redirige a pantalla de transacciones")
    public void TC0266BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickCuentas();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Cuentas");
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            cuentasHerramientasAccesibilidadSteps.ingresarAListadoDeTransacciones();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Listado de transacciones");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0267-BSC: Herramientas de Accesibilidad Cuentas- Estado de cuenta - Validar que se redirige a pantalla de Estado de cuenta")
    public void TC0267BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickCuentas();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickVerMas1erCuenta();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            cuentasHerramientasAccesibilidadSteps.ingresarAEstadoDeCuenta();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Estado de cuenta");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0268-BSC: Herramientas de Accesibilidad Cuentas - Solicitud de baja de cuenta - Completar formulario y validar que se redirige a pantalla de confirmacion")
    public void TC0268BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickCuentas();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickVerMas1erCuenta();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            cuentasHerramientasAccesibilidadSteps.ingresarSolicitudBajaCuenta();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Baja de cuenta");
            cuentasHerramientasAccesibilidadSteps.completarFormularioBajaDeCuenta();
            cuentasHerramientasAccesibilidadSteps.ingresarAEstadoDeCuenta();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0269-BSC: Herramientas de Accesibilidad Cuentas- Ingresar a Mas opciones - Transferencias -  Validar que se redirige a pantalla de Transferencias Inmediatas")
    public void TC0269BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickCuentas();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Cuentas");
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            cuentasHerramientasAccesibilidadSteps.ingresarATransferencias();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Transferencias");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0270-BSC: Herramientas de Accesibilidad Cuentas- Ingresar a Mas opciones - Transferencias Programadas - Validar que se redirige a pantalla de Transferencias Programadas")
    public void TC0270BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickCuentas();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Cuentas");
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            cuentasHerramientasAccesibilidadSteps.ingresarATransferenciasProgramadas();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Transferencias programadas");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0271-BSC: Herramientas de Accesibilidad Cuentas- Ingresar a Mas opciones - Debitos Automaticos - Validar que se redirige a pantalla de Debitos Automaticos")
    public void TC0271BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickCuentas();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickVerMas1erCuenta();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            cuentasHerramientasAccesibilidadSteps.ingresarADebitosAutomaticos();
            cuentasHerramientasAccesibilidadSteps.validarTituloPageGenerico("Gestión de débitos automáticos");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0272-BSC: Herramientas de Accesibilidad Cuentas- Ingresar a Mas opciones- Consulta Compras - Validar que se redirige a pantalla de Consulta Compras")
    public void TC0272BSC() {
        try {
            loginSteps.iniciarSesion(uBsc13400488);
            menuLateralSteps.clickCuentas();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickVerMas1erCuenta();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            cuentasHerramientasAccesibilidadSteps.ingresarAConsultarCompras();
            cuentasHerramientasAccesibilidadSteps.validarTituloPageGenerico("Consulta de compras en comercios");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0273-BSC: Herramientas de Accesibilidad Cuentas- Ingresar a Mas opciones- Solicitud de Adelanto de Sueldos - Validar que se redirige a pantalla de Solicitud de Adelanto de Sueldos")
    public void TC0273BSC() {
        try {
            loginSteps.iniciarSesion(uPruebaqaBSC03);
            menuLateralSteps.clickCuentas();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickVerMas1erCuenta();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            cuentasHerramientasAccesibilidadSteps.ingresarASolicitudAdelantoDeSueldo();
            cuentasHerramientasAccesibilidadSteps.validarTituloPageGenerico("Adelanto de sueldo");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0274-BSC: Herramientas de Accesibilidad Cuentas - Ingresar a Mas opciones - Solicitud de Adelanto de Sueldos - Resumen de contrato- Validar que se redirige a pantalla de Resumen de contrato")
    public void TC0274BSC() {
        try {
            loginSteps.iniciarSesion(uPruebaqaBSC03);
            menuLateralSteps.clickCuentas();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickVerMas1erCuenta();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            cuentasHerramientasAccesibilidadSteps.ingresarASolicitudAdelantoDeSueldo();
            cuentasHerramientasAccesibilidadSteps.validarTituloPageGenerico("Adelanto de sueldo");
            cuentasHerramientasAccesibilidadSteps.descargarResumenDeContratoAdelantoDesueldos();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @DisplayName("TC-0275-BSC: Herramientas de Accesibilidad Cuentas - Ingresar a Solicitud de Adelanto de Sueldos - Mis adelantos - Validar que se redirige a pantalla de Mis adelantos")
    public void TC0275BSC() {
        try {
            loginSteps.iniciarSesion(uPruebaqaBSC03);
            menuLateralSteps.clickCuentas();
            herramientasDeAccesibilidadSteps.validarTituloPageGenerico("Cuentas");
            cuentasSteps.clickVerMas1erCuenta();
            herramientasDeAccesibilidadSteps.ingresarAHerramientasAccesibilidad();
            cuentasHerramientasAccesibilidadSteps.ingresarASolicitudAdelantoDeSueldo();
            cuentasHerramientasAccesibilidadSteps.ingresarAMisAdelantos();
            cuentasHerramientasAccesibilidadSteps.validarTituloPageGenerico("Adelanto de sueldo");
            cuentasHerramientasAccesibilidadSteps.validarTituloPageGenerico("Solicitá tu adelanto");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @After
    public void teardown() throws IOException, InterruptedException {
        global.practia.jemeinsamstark.web.core.WebDriver.closeDriver();
        cerrarConexion();
    }
}
