package steps.herramientas_de_accesibilidad;

import global.practia.jemeinsamstark.web.core.WebDriver;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.doblefactor.DoblefactorPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.UtilsGP;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;


public class CuentasHerramientasAccesibilidadSteps extends HerramientasDeAccesibilidadSteps {
    Actions actions = new Actions(WebDriver.getInstance());
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    DoblefactorPage doblefactorPage = new DoblefactorPage();

    @Step("Completar campo Alias")
    public void completarCampoAlias(String texto) throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            actions.sendKeys(Keys.BACK_SPACE).build().perform();
        }
        String aliasGenerado = UtilsGP.generarString(10, texto);
        actions.sendKeys(aliasGenerado).build().perform();
    }

    @Step("Validar pantalla Confirmacion Solicitud Chequera")
    public void validacionPantallaConfirmacionSolicitudChequera() {
        List<String> campos = Arrays.asList(
                "Tipo de cheque",
                "Cantidad de cheques",
                "Cantidad de chequeras"
        );
        for (String campo : campos) {
            scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]", campo)));
            Assert.assertTrue("El campo '" + campo + "' no esta visible", botonesGenericosPage.buscarTexto(campo).isDisplayed());
        }
        botonesGenericosPage.getConfirmar().isEnabled();
        System.out.println("Titulos validados con exito");
    }

    @Step("Validar input segundo factor")
    public void validarInputSegundoFactor() throws InterruptedException {
        Assert.assertTrue(doblefactorPage.setCodigo().isDisplayed());
    }

    @Step("Ingresar a Cuentas")
    public void ingresarACuentas() throws InterruptedException {
        presionarTeclaFin();
        Thread.sleep(1000);
        presionarTecla6();
    }
    @Step("Ingresar a Estado de Cuenta")
    public void ingresarAEstadoDeCuenta() throws InterruptedException {
        presionarTecla3();
    }
    @Step("Ingresar a Mas opciones -> Transferencias")
    public void ingresarATransferencias() throws InterruptedException {
        //Mas opciones
        Thread.sleep(2000);
        presionarTecla4();
        Thread.sleep(2000);
        //Transferencias
        presionarTecla8();
    }
    @Step("Ingresar a Mas opciones -> Transferencias Programadas ")
    public void ingresarATransferenciasProgramadas() throws InterruptedException {
        //Mas opciones
        Thread.sleep(2000);
        presionarTecla4();
        Thread.sleep(2000);
        //Transferencias
        presionarTecla9();
    }
    @Step("Ingresar a Solicitud de baja de cuenta")
    public void ingresarSolicitudBajaCuenta() throws InterruptedException {
        Thread.sleep(2000);
        presionarTecla5();
        presionarTecla6();
    }

    @Step("Ingresar a Solicitar Cuenta Nueva")
    public void ingresarASolicitarCuentaNueva() throws InterruptedException {
        Thread.sleep(2000);
        presionarTecla2();
    }

    @Step("Ingresar a Debitos Automaticos")
    public void ingresarADebitosAutomaticos() throws InterruptedException {
        //Mas opciones
        presionarTecla5();
        //Debitos Automaticos
        presionarTecla7();
    }
    @Step("Ingresar a Consultar Compras")
    public void ingresarAConsultarCompras() throws InterruptedException {
        //Mas opciones
        presionarTecla5();
        //Consultar Compras
        presionarTecla8();
    }
    @Step("Ingresar a Solicitud Adelanto de sueldos")
    public void ingresarASolicitudAdelantoDeSueldo() throws InterruptedException {
        //Mas opciones
        presionarTecla5();
        //Solicitud Adelanto de sueldos
        presionarTecla9();
    }
    @Step("Descargar el resumen de contrato de adelanto de sueldos")
    public void descargarResumenDeContratoAdelantoDesueldos() throws InterruptedException {
        //Descargar resumen contrato
        presionarTecla2();
        //Aceptar
        presionarTecla1();
        presionarTecla1();
        validarFragmentoURL("assets");
    }
    @Step("Ingresar a Mis adelantos")
    public void ingresarAMisAdelantos() throws InterruptedException {
        //Mis adelantos
        presionarTecla3();
    }
    @Step("Ingresar a ver más Cuenta")
    public void ingresarAVerMasCuenta(int cuentaNro) throws InterruptedException {
        Thread.sleep(3000);
        switch (cuentaNro) {
            case 1:
                presionarTecla5();
                break;
            case 2:
                presionarTecla6();
                break;
            case 3:
                presionarTecla7();
                break;
            case 4:
                presionarTecla8();
                break;
            case 5:
                presionarTecla9();
                break;
        }
    }

    @Step("Modificar alias")
    public void modificarAlias() throws InterruptedException {
        //Boton Modificar alias
        presionarTecla4();
        //Input alias
        presionarTecla2();
        presionarTecla1();
        completarCampoAlias("abcdefghijklmnopqrstuvwxyz");
        presionarTeclaFin();
        presionarTeclaFin();
        //Boton guardar
        presionarTecla3();
    }

    @Step("Ingresar a Solicitud de chequera")
    public void ingresarASolicitudDeChequera() throws InterruptedException {
        Thread.sleep(1000);
        presionarTecla5();
        Thread.sleep(3000);
        presionarTecla9();
    }

    @Step("Ingresar a Generar Orden de Extraccion")
    public void ingresarAGenerarOrdenDeExtraccion() throws InterruptedException {
        Thread.sleep(1000);
        presionarTecla3();
        presionarTecla2();
    }

    @Step("Ingresar a Agenda de destinatario")
    public void ingresarAgendaDestinatario() throws InterruptedException {
        Thread.sleep(2000);
        presionarTecla4();
        presionarTecla6();
    }

    @Step("Ingresar a Listado de transacciones")
    public void ingresarAListadoDeTransacciones() throws InterruptedException {
        Thread.sleep(2000);
        presionarTecla4();
        presionarTecla7();
    }

    @Step("Completar formulario Solicitud de chequera")
    public void completarFormularioSolicitudChequera() throws InterruptedException {
        Thread.sleep(2000);
        presionarTecla2();
        //TIPO DE CHEQUE
        presionarTecla1();
        presionarTeclaEspacio();
        Thread.sleep(2000);
        presionarTeclaEnter();
        presionarTeclaFin();
        //CANTIDAD DE CHEQUES
        presionarTecla2();
        presionarTeclaEspacio();
        Thread.sleep(2000);
        presionarTeclaEnter();
        presionarTeclaFin();
        //CANTIDAD DE CHEQUERAS
        presionarTecla3();
        presionarTeclaEspacio();
        Thread.sleep(2000);
        presionarTeclaEnter();
        presionarTeclaFin();
        presionarTeclaFin();
        //BOTON CONTINUAR
        presionarTecla3();
    }

    @Step("Completar formulario Generar Orden de Extraccion con cuenta Origen")
    public void formularioGenerarOrdenExtraccionConCuentaOrigen() throws InterruptedException {
        Thread.sleep(2000);
        presionarTecla2();
        //CUENTA ORIGEN
        presionarTecla1();
        presionarTeclaEspacio();
        presionarTeclaEnter();
        presionarTeclaFin();
        //DOCUMENTO DEL DESTINATARIO
        presionarTecla3();
        completarCampo("12345678");
        presionarTeclaFin();
        //IMPORTE
        presionarTecla4();
        completarCampo("100");
        presionarTeclaFin();
        //REFERENCIA
        presionarTecla5();
        completarCampo("pruebaAutomation");
        presionarTeclaFin();
        presionarTeclaFin();
        //BOTON AGREGAR
        presionarTecla3();
        //BOTON CONTINUAR
        presionarTecla4();
    }
    @Step("Completar formulario Generar Orden de Extraccion Sin cuenta origen")
    public void formularioGenerarOrdenExtraccion() throws InterruptedException {
        Thread.sleep(2000);
        presionarTecla2();
        //DOCUMENTO DEL DESTINATARIO
        presionarTecla2();
        completarCampo("12345678");
        presionarTeclaFin();
        //IMPORTE
        presionarTecla3();
        completarCampo("100");
        presionarTeclaFin();
        //REFERENCIA
        presionarTecla4();
        completarCampo("pruebaAutomation");
        presionarTeclaFin();
        presionarTeclaFin();
        //BOTON AGREGAR
        presionarTecla3();
        //BOTON CONTINUAR
        presionarTecla4();
    }

    @Step("Adherir nuevo destinatario")
    public void adherirNuevoDestinatario() throws InterruptedException, UnsupportedEncodingException {
        Thread.sleep(1000);
        //BOTON ADHERIR NUEVO DESTINATARIO
        presionarTecla2();
        botonesGenericosSteps.validarTituloPageGenerico("Adherir nuevo destinatario");
        //COMPLETAR CBU
        presionarTecla2();
        presionarTecla2();
        completarCampo("3300000620000987478030");
        presionarTeclaFin();
        //BOTON BUSCAR
        presionarTecla3();
        //COMPLETAR PERTENECE A
        presionarTecla3();
        completarCampo("pruebaAutomation");
        presionarTeclaFin();
        //BOTON CONTINUAR
        presionarTecla6();
    }

    @Step("Completar Formulario Solicitar Cuenta Nueva")
    public void completarFormularioSolicitarCuentaNueva() throws InterruptedException, UnsupportedEncodingException {
        presionarTecla4();
        //TIPO CUENTA
        presionarTecla1();
        presionarTeclaEspacio();
        Thread.sleep(1000);
        presionarTeclaEnter();
        presionarTeclaFin();
        //MONEDA DE LA CUENTA
        presionarTecla2();
        presionarTeclaEspacio();
        Thread.sleep(1000);
        presionarTeclaEnter();
        presionarTeclaFin();
        presionarTeclaFin();
        //BOTON CONTINUAR
        presionarTecla5();
        presionarTecla2();
        //EMAIL
        presionarTecla3();
        completarCampo("gp23");
        presionarTeclaFin();
        //TELEFONO
        presionarTecla4();
        completarCampo("1122334455");
        presionarTeclaFin();
        presionarTeclaFin();
        //BOTON CONTINUAR
        presionarTecla3();
    }

    @Step("Completar formulario de baja de cuenta")
    public void completarFormularioBajaDeCuenta() throws InterruptedException {
        presionarTecla2();
        //EMAIL
        presionarTecla1();
        completarCampo("gp23");
        presionarTeclaFin();
        //TELEFONO
        presionarTecla2();
        completarCampo("1122334455");
        presionarTeclaFin();
        presionarTeclaFin();
        //BOTON CONTINUAR
        presionarTecla3();
    }

}
