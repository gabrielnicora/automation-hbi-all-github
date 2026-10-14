package pages.tarjetasdecredito;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class TarjetasDeCreditoPage {

    public PandoraElement getSolicitarTarjetaNueva() {
        return new PandoraElement(By.xpath("//a[@href='/creditCards/newCreditCard']"));
    }

    public PandoraElement getSeguimientoDeTarjetas() {
        return new PandoraElement(By.xpath("//a[@href='/creditCard/creditCardTracking']"));
    }

    public PandoraElement getVerMasXnroTarjeta(String ultimosCuatroNrosTarjeta) {
        return new PandoraElement(By.xpath(String.format("//span[contains(text(),'%s')]/ancestor::div[1]/following-sibling::div//a", ultimosCuatroNrosTarjeta)));
    }

    public PandoraElement getTituloMastercard() {
        return new PandoraElement(By.xpath("//h1[contains(@aria-label,'Master')]"));
    }

    public PandoraElement getTituloMastercard(String nroTarjeta) {
        return new PandoraElement(By.xpath("//h1[contains(@aria-label,'"+nroTarjeta+"')]"));
    }


    public PandoraElement getTituloVisa(String nombreTarjeta) {
        return new PandoraElement(By.xpath("//h1[contains(@aria-label,'"+nombreTarjeta+"')]"));
    }

    public PandoraElement getLimiteDisponible() {
        return new PandoraElement(By.xpath("//p[@aria-label='Disponible']"));
    }

    public PandoraElement getDescargarResumen() {
        return new PandoraElement(By.xpath("//span[contains(text(), 'Descargar Res')]"));
    }

    public PandoraElement getSolicitarAdicional() {
        return new PandoraElement(By.xpath("//span[contains(text(), 'Solicitar Adicional')]"));
    }

    public PandoraElement getSolicitarBajaTarjeta() {
        return new PandoraElement(By.xpath("//span[text()='Solicitar baja de tarjeta']"));
    }

    public PandoraElement getMovimientosPeriodicos() {
        return new PandoraElement(By.xpath("//span[contains(text(), 'Movimientos del per')]"));
    }

    public PandoraElement getTablaMovimientosPeriodicos() {
        return new PandoraElement(By.xpath("//div[@aria-label='tabla']"));
    }

    public PandoraElement getPrimerPeriodo() {
        return new PandoraElement(By.xpath("//div[@role='button'][1]"));
    }

    public PandoraElement getIconoDescargaPeriodo() {
        return new PandoraElement(By.xpath("(//div[@aria-label='Icono de descargar'])[1]"));
    }

    public PandoraElement getDesplegableCuentaDebito() {
        return new PandoraElement(By.xpath("//span[contains(text(),'Cuenta débito')]//../../../div"));
    }

    public PandoraElement getDesplegableTarjetaDebito() {
        return new PandoraElement(By.xpath("//div[contains(text(),'Seleccionar')]/../.."));
    }

    public PandoraElement getInputNombreForm() {
        return new PandoraElement(By.xpath("//input[@name='additionalName']"));
    }

    public PandoraElement getInputApellidoForm() {
        return new PandoraElement(By.xpath("//input[@name='additionalSurname']"));
    }

    public PandoraElement getInputDniForm() {
        return new PandoraElement(By.xpath("//input[@name='additionalDni']"));
    }

    public PandoraElement getNacionalidadForm() {
        return new PandoraElement(By.xpath("//input[@name='additionalBirthCountry']/.."));
    }

    public PandoraElement getInputEstadoCivilForm() {
        return new PandoraElement(By.xpath("//input[@name='additionalCivilStatus']/..//div[contains(text(), 'Seleccionar')]"));
    }

    public PandoraElement getInputNacionalidadForm() {
        return new PandoraElement(By.xpath("//input[@name='additionalBirthCountry']/../descendant::input"));
    }

    public PandoraElement getDesplegableSexoForm() {
        return new PandoraElement(By.xpath("//input[@name='additionalGender']/..//div[contains(text(),'Seleccio')]"));
    }

    public PandoraElement getButtonTarjetaAdicional() {
        return new PandoraElement(By.xpath("//button[@aria-label='Agregar tarjeta adicional']"));
    }

    public PandoraElement getValidarName() {
        return new PandoraElement(By.xpath("(//div[@role='row'])[2]/*[1]"));
    }

    public PandoraElement getValidarSurname() {
        return new PandoraElement(By.xpath("(//div[@role='row'])[2]/*[2]"));
    }

    public PandoraElement getValidarDocumento() {
        return new PandoraElement(By.xpath("(//div[@role='row'])[2]/*[3]"));
    }

    public PandoraElement getCalendarioFechaNac() {
        return new PandoraElement(By.xpath("//div[@id='customImputID']"));
    }

    public PandoraElement getSelectAnioCalendario(String anio) {
        return new PandoraElement
                (By.xpath(String.format("//div[@aria-labelledby='customImputID']/descendant::select/option[@value='%s']", anio)));

    }

    public PandoraElement getAnioCalendario() {
        return new PandoraElement(By.xpath("//div[@aria-labelledby='customImputID']/descendant::select[1]"));
    }

    public PandoraElement getDia(String diaSeleccionado) {
        return new PandoraElement(By.xpath(String.format("//div[contains(@class, 'react-datepicker__day react-datepicker__day--%s')]", diaSeleccionado)));
    }

    public PandoraElement getDenunciaRoboExtravio() {
        return new PandoraElement(By.xpath("//span[contains(text(), 'Denunciar robo o')]"));
    }

    public PandoraElement getLabelRobo() {
        return new PandoraElement(By.xpath("//label[@for=\"Robo\"]"));
    }

    public PandoraElement getInputMail() {
        return new PandoraElement(By.id("EMAIL"));
    }

    public PandoraElement getInputMailBERSAyBSF() {
        return new PandoraElement(By.id("Mail"));
    }


    public PandoraElement getInputTelefono() {
        return new PandoraElement(By.xpath("//div[contains(text(), 'Tel')]/../following::input[1]"));
    }

    public PandoraElement getPagarMiTarjeta() {
        return new PandoraElement(By.xpath(" //span[contains(text(), 'Pagar mi ta')]"));
    }

    public PandoraElement getDesplegableTipoDePago() {
        return new PandoraElement(By.xpath("//div[contains(text(), 'Ingresar un importe')]"));
    }

    public PandoraElement getInputImporte() {
        return new PandoraElement(By.xpath("//input[contains(@name, 'amount')]"));
    }

    public PandoraElement getBotonPagar() {
        return new PandoraElement(By.xpath("//button[contains(@aria-label, 'Pagar')]"));
    }

    public PandoraElement getValidacionText(String text) {
        return new PandoraElement(By.xpath(String.format("//*[contains(text(), '%s')]", text)));
    }

    public PandoraElement getUltimoResumen() {
        return new PandoraElement(By.xpath("//span[contains(text(), 'ltimo resumen')]"));
    }

    public PandoraElement getIngresarAdetalleTarjeta() {
        return new PandoraElement(By.xpath("//div[@aria-label='Icono de flecha derecha ']"));
    }

    public PandoraElement getCheckboxPersonaExpuesta() {
        return new PandoraElement(By.xpath("//*[contains(@aria-label,'checkbox No chequeado Persona expuesta pol')]"));
    }

    public PandoraElement getCheckboxResidenciaFiscalExterior() {
        return new PandoraElement(By.xpath("//*[contains(@aria-label,'checkbox No chequeado Residencia Fiscal en el exterior')]"));
    }

    public PandoraElement getCheckboxSujetoObligado() {
        return new PandoraElement(By.xpath("//*[contains(@aria-label,'checkbox No chequeado Sujeto Obligado UIF')]"));
    }
    public PandoraElement getButtonTurno() {
        return new PandoraElement(By.xpath("//a[@href='/appointmentMaker']/.//*[contains(text(), 'tu turno ac')]"));
    }
    public PandoraElement getSelectorMarcaDeTarjeta() {
        return new PandoraElement(By.xpath("//input[@name=\"cardSelector\"]/.."));
    }
    public PandoraElement getVerMasPrimerTarjeta() {
        return new PandoraElement(By.xpath("(//span[contains(text(), 'Ver m')])[1]"));
    }
    public PandoraElement getCancelacionSaldoEnCuotas() {
        return new PandoraElement(By.xpath("//*[contains(text(), 'N DE SALDOS EN CUOTAS')]"));
    }
    public PandoraElement getTablaAdicional() {
        return new PandoraElement(By.xpath("//div[@role='table']"));
    }
    public PandoraElement getTarjetaDeDebito(){return  new PandoraElement(By.xpath("//input[@name='debitCards']/.."));
    }

    public PandoraElement getCardDisplayMensajeDeInformacion() {
        return new PandoraElement(By.xpath("//*[@id='container']/div[1]/div/p"));
    }
}
