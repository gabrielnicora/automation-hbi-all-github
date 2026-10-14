package steps.herramientas_de_accesibilidad;

import global.practia.jemeinsamstark.web.core.WebDriver;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.interactions.Actions;
import pages.transferencias.agenda_nuevo_destinatario.AgendaNuevoDestinatarioPage;
import steps.botonesgenericos.BotonesGenericosSteps;

public class TransferenciasHerramientasAccesibilidadSteps extends HerramientasDeAccesibilidadSteps {

    Actions actions = new Actions(WebDriver.getInstance());
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    AgendaNuevoDestinatarioPage agendaNuevoDestinatarioPage = new AgendaNuevoDestinatarioPage();

    @Step("Ingresar a Transferencias")
    public void ingresarATransferencias() throws InterruptedException {
        presionarTeclaFin();
        sleep();
        presionarTecla7();
    }

    @Step("Ingresar a Transferencias Programads")
    public void ingresarATransferenciasProgramadas() throws InterruptedException {
        presionarTecla3();
        presionarTecla4();
    }

    @Step("Ingresar a Transferencias Inmediatas")
    public void ingresarATransferenciasInmediatas() throws InterruptedException {
        presionarTecla5();
        presionarTecla1();
    }

    @Step("Ingresar a Agenda Destinatarios")
    public void ingresarAgendaDestinatarios() throws InterruptedException {
        presionarTecla4();
        presionarTecla7();
        presionarTecla7();

    }

    @Step("Presionar radio Cuenta terceros")
    public void presionarFiltroCuentasTerceros() throws InterruptedException {
        Thread.sleep(1500);
        presionarTecla3();
        presionarTecla2();
        Assert.assertTrue(agendaNuevoDestinatarioPage.getLabelTrueTerceros().isDisplayed());
    }

    @Step("Presionar radio Cuentas Propias")
    public void presionarFiltroCuentasPropias() throws InterruptedException {
        Thread.sleep(1500);
        presionarTecla3();
        presionarTecla1();
        Assert.assertTrue(agendaNuevoDestinatarioPage.getLabelTruePropias().isDisplayed());
    }

    @Step("Ingresar a Historico Transferencias")
    public void ingresarHistoricoTransferencias() throws InterruptedException {
        presionarTecla2();
    }

    @Step("Presionar Mas opciones y luego Transferencias Importe Superior")
    public void ingresarATransferenciasImporteSuperior() throws InterruptedException {
        //Mas opciones
        presionarTecla3();
        Thread.sleep(1000);
        //Transferencias Importe Superior
        presionarTecla5();
    }

    @Step("Presionar Mas opciones y luego Limite de transferencia")
    public void ingresarALimiteDeTransferencia() throws InterruptedException {
        //Mas opciones
        presionarTecla3();
        Thread.sleep(1000);
        //Limite de transferencia
        presionarTecla6();
    }

    @Step("Presionar Mas opciones y luego Gestionar Limite de transferencia")
    public void ingresarAGestionarLimiteDeTransferencia() throws InterruptedException {
        //Mas opciones
        presionarTecla3();
        Thread.sleep(1000);
        //Gestionar Limite de transferencia
        presionarTecla7();
    }

    @Step("Presionar Mas opciones y luego Consultar Credin")
    public void ingresarAConsultarCredin() throws InterruptedException {
        //Mas opciones
        presionarTecla3();
        Thread.sleep(1000);
        //Consultar Credin
        presionarTecla8();
    }

    @Step("Ingresar a generar credin")
    public void ingresarAGenerarCredin() throws InterruptedException {
        presionarTecla2();
    }

    @Step("Seleccionar tarjeta de debito")
    public void seleccionarTarjetaDebito() throws InterruptedException {
        presionarTecla2();
        presionarTecla1();
        presionarTeclaEspacio();
        presionarTeclaEnter();
        presionarTeclaFin();
        presionarTeclaFin();
        //Botn continuar
        presionarTecla3();
    }

    @Step("Completar formulario transferencia")
    public void completarFormularioTransferencia() throws InterruptedException {
        presionarTecla2();
        //TIPO DE TRANSFERENCIA
        presionarTecla2();
        presionarTeclaEspacio();
        presionarTeclaAbajo();
        presionarTeclaEnter();
        presionarTeclaFin();
        //CUENTA ORIGEN
        presionarTecla3();
        presionarTeclaEspacio();
        presionarTeclaEnter();
        presionarTeclaFin();
        //CUENTA DESTINO
        presionarTecla4();
        presionarTeclaEspacio();
        presionarTeclaEnter();
        presionarTeclaFin();
        //IMPORTE
        presionarTecla5();
        completarCampo("200");
        presionarTeclaFin();
        //CONCEPTO
        presionarTecla6();
        presionarTeclaEspacio();
        presionarTeclaEnter();
        presionarTeclaFin();
        //REFERENCIA
        presionarTecla7();
        completarCampo("test");
        presionarTeclaFin();
        presionarTeclaFin();
        //BOTON CONTINUAR
        presionarTecla5();
        //BOTON CONFIRMAR
        presionarTecla2();
    }
}