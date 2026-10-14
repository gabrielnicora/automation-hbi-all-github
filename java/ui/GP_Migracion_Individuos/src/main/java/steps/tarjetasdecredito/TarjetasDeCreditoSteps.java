package steps.tarjetasdecredito;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import io.qameta.allure.Step;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.tarjetasdecredito.TarjetasDeCreditoPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.login.LoginSteps;
import utils.UtilsGP;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class TarjetasDeCreditoSteps extends BotonesGenericosSteps {
    TarjetasDeCreditoPage tarjetasDeCreditoPage = new TarjetasDeCreditoPage();
    LoginSteps loginSteps = new LoginSteps();
    WebDriverWait wait = new WebDriverWait(driver, 10);
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    UtilsGP utilsGP = new UtilsGP();

    @Step("Click en Solicitar Tarjeta Nueva")
    public void clickSolicitarTarjetaNueva() {
        tarjetasDeCreditoPage.getSolicitarTarjetaNueva().click();
    }

    @Step("Click en Seguimiento de tarjetas")
    public void clickSeguimientoDeTarjetas() {
        tarjetasDeCreditoPage.getSeguimientoDeTarjetas().click();
    }

    @Step("Click Ver mas x nro de tarjeta: {ultimosCuatroNrosTarjeta}")
    public void clickVerMasXnroDeTarjeta(String ultimosCuatroNrosTarjeta) {
        UtilsGP.fixedWait(3000);
        tarjetasDeCreditoPage.getVerMasXnroTarjeta(ultimosCuatroNrosTarjeta).isDisplayed();
        tarjetasDeCreditoPage.getVerMasXnroTarjeta(ultimosCuatroNrosTarjeta).click();
        UtilsGP.fixedWait(7000);
//        if(botonesGenericosPage.getMensajeEmergente().isPresent()){
//            System.out.println("BOOLEAN : " + botonesGenericosPage.getMensajeEmergente().isPresent());
//            Assert.fail("ERROR MENSAJE EMERGENTE : " + botonesGenericosPage.getMensajeEmergente().getText()+"");
//        }else{
//            System.out.println("BOOLEAN : " + botonesGenericosPage.getMensajeEmergente().isPresent());
//            System.out.println("================ NO SE ENCONTRO MENSAJE EMERGENTE =====================");
//        }
    }
    @Step("Click Ver mas primer card")
    public void clickVerMasPrimerTarjeta() {
        tarjetasDeCreditoPage.getVerMasPrimerTarjeta().click();
    }

    @Step("Validar Titulo Mastercard")
    public void verificarTituloMastercard() {
        Assert.assertTrue("No Visible", tarjetasDeCreditoPage.getTituloMastercard().isDisplayed());
    }

    @Step("Validar Titulo Mastercard")
    public void verificarTituloMastercard(String nroTarjeta) {
        Assert.assertTrue("No Visible", tarjetasDeCreditoPage.getTituloMastercard(nroTarjeta).isDisplayed());
    }

    @Step("Validar Titulo Visa")
    public void verificarTituloVisa(String nroTarjeta) {
        Assert.assertTrue("No Visible", tarjetasDeCreditoPage.getTituloVisa(nroTarjeta).isDisplayed());

    }


    @Step("Validar Limite Disponible")
    public void verificarLimiteDisponible() {
        Assert.assertTrue("No Visible", tarjetasDeCreditoPage.getLimiteDisponible().isDisplayed());
    }

    @Step("Validar Movimientos Periodico Titulo")
    public void verificarMovimientoPeriodico() {
        Assert.assertTrue("No Visible", tarjetasDeCreditoPage.getMovimientosPeriodicos().isDisplayed());
    }

    @Step("Validar Tabla")
    public void verificarTablaMovimientos() {
        Assert.assertTrue("No Visible", tarjetasDeCreditoPage.getTablaMovimientosPeriodicos().isDisplayed());
    }
    @Step("Click en descargar Resumen")
    public void clickDescargarResumen() {
        Assert.assertTrue("NO se encontro el Boton",tarjetasDeCreditoPage.getDescargarResumen().isDisplayed());
        tarjetasDeCreditoPage.getDescargarResumen().click();
    }

    @Step("Click en solicitar adicional")
    public void clickSolicitarAdicional() {
        tarjetasDeCreditoPage.getSolicitarAdicional().click();
    }

    @Step("Click en solicitar baja tarjeta")
    public void clickSolicitarBajaTarjeta() {
        tarjetasDeCreditoPage.getSolicitarBajaTarjeta().click();
    }


    @Step("Validar Solicitar Adicional")
    public void verificarSolicitarAdicional() {
        Assert.assertTrue("No Visible", tarjetasDeCreditoPage.getSolicitarAdicional().isDisplayed());
    }

    @Step("Validar Descargar Resumen")
    public void verificarDescargarResumen() {
        Assert.assertTrue("No Visible", tarjetasDeCreditoPage.getDescargarResumen().isDisplayed());
    }

    @Step("Click Desplegable de Primer Periodo")
    public void clickPrimerPeriodo()
    {
        Assert.assertTrue("No se encontro el primer periodo",tarjetasDeCreditoPage.getPrimerPeriodo().isDisplayed());
        tarjetasDeCreditoPage.getPrimerPeriodo().click();
    }

    @Step("Click Descargar Primer Periodo")
    public void clickDescargaPrimerPeriodo() {
        Assert.assertTrue("No se encontro el icono de descarga",tarjetasDeCreditoPage.getIconoDescargaPeriodo().isDisplayed());
        tarjetasDeCreditoPage.getIconoDescargaPeriodo().click();
    }

    @Step("Seleccionar cuenta debito si se encuentra presente en la pagina")
    public void seleccionarCuentaDebitoSiEstaPresente(int rep, String cuentaDebito) {
        if (tarjetasDeCreditoPage.getDesplegableCuentaDebito().isDisplayed()) {
            tarjetasDeCreditoPage.getDesplegableCuentaDebito().click();
            keyDownXParametrosGenerico(rep, cuentaDebito);
        } else {
            System.out.println("No se encuentra desplegable Cuenta debito");
        }
    }

    @Step("Seleccionar cuenta debito")
    public void seleccionarCuentaDebitoSiEstaPresente(String cuentaDebito) throws AWTException, InterruptedException {
        scrollTo(By.xpath("//span[contains(text(),'Cuenta débito')]//../../../div"));
        try{
            if (tarjetasDeCreditoPage.getDesplegableCuentaDebito().isDisplayed()) {
                    tarjetasDeCreditoPage.getDesplegableCuentaDebito().click();
                    seleccionarDropdownGenerico(cuentaDebito);
            } else {
                System.out.println("No se encuentra desplegable Cuenta debito");
            }
        }catch(Exception e){
                System.out.println("CATCH:::::::::::::::");
        }
        

    }
    @Step("Cuenta debito")
    public void seleccionarCuentaDebito(String cuenta) throws AWTException {
        scrollTo(By.xpath("//input[contains(@name, 'ccount')]/.."));
        tarjetasDeCreditoPage.getDesplegableCuentaDebito().click();
        keyDownXParametrosGenerico(0,"");
    }

    @Step("Seleccionar tarjeta de debito")
    public void seleccionarTarjetaDebito(String cuentaDebito){
        scrollTo(tarjetasDeCreditoPage.getDesplegableTarjetaDebito().getElementBy());
        tarjetasDeCreditoPage.getDesplegableTarjetaDebito().isDisplayed();
        tarjetasDeCreditoPage.getDesplegableTarjetaDebito().click();
        seleccionarDropdownGenerico(cuentaDebito);
    }

    @Step("Seleccionar tarjeta de debito")
    public void seleccionarTarjetaDebitoSiEstaPresente() throws AWTException, InterruptedException {
        scrollTo(By.xpath("//input[@name='debitCards']/.."));
        tarjetasDeCreditoPage.getTarjetaDeDebito().click();
        if (tarjetasDeCreditoPage.getDesplegableTarjetaDebito().isDisplayed()) {
            //tarjetasDeCreditoPage.getDesplegableTarjetaDebito().click();
            keyDownXParametrosGenerico(0, "");
            clickContinuarGenerico();
        } else {
            System.out.println("No esta presente el selector de tarjeta de debito");
        }

    }

    @Step("Completar formulario soliciud adicional")
    public void formularioSolicitudAdicional(String nroTarjeta) throws InterruptedException {
        if(tarjetasDeCreditoPage.getTablaAdicional().isDisplayed()){
            clickCancelarGenerico();
            loginSteps.clickSalirPrestamo();
            clickVerMasXnroDeTarjeta(nroTarjeta);
            clickSolicitarAdicional();
        }
        // Nombre = "Prueba"
        tarjetasDeCreditoPage.getInputNombreForm().clear();
        tarjetasDeCreditoPage.getInputNombreForm().sendKeys("Prueba");

        // Apellido = "Automation"
        tarjetasDeCreditoPage.getInputNombreForm().clear();
        tarjetasDeCreditoPage.getInputApellidoForm().sendKeys("Automation");

        // DNI = "12345678"
        tarjetasDeCreditoPage.getInputNombreForm().clear();
        tarjetasDeCreditoPage.getInputDniForm().sendKeys("12345678");

        // Fecha nacimiento = 1998
        tarjetasDeCreditoPage.getCalendarioFechaNac().click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@aria-labelledby='customImputID']/descendant::select[1]")));
        tarjetasDeCreditoPage.getAnioCalendario().click();
        tarjetasDeCreditoPage.getSelectAnioCalendario("1998").click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath((String.format("//div[contains(@class, 'react-datepicker__day react-datepicker__day--%s')]", "005")))));
        tarjetasDeCreditoPage.getDia("005").click();

        // Nacionalidad = "Argentina"
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='additionalBirthCountry']/..")));
        tarjetasDeCreditoPage.getNacionalidadForm().click();
        tarjetasDeCreditoPage.getInputNacionalidadForm().sendKeys("Argentina");
        keyDownXParametrosGenerico(0, "");

        // Estado Civil = "Soltero/ a"
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='additionalCivilStatus']/..//div[contains(text(), 'Seleccionar')]")));
        tarjetasDeCreditoPage.getInputEstadoCivilForm().click();
        keyDownXParametrosGenerico(0, "");

        // Sexo = "Femenino"
        scrollTo(By.xpath("//span[text()= 'Sexo']"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='additionalGender']/..//div[contains(text(),'Seleccio')]")));
        tarjetasDeCreditoPage.getDesplegableSexoForm().click();
        keyDownXParametrosGenerico(0, "Femenino");

        // Click Agregar Adicional
        scrollTo(By.xpath("//button[@aria-label='Agregar tarjeta adicional']"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@aria-label='Agregar tarjeta adicional']")));
        tarjetasDeCreditoPage.getButtonTarjetaAdicional().click();

    }


    @Step("Completar formulario soliciud adicional")
    public void validarTablaDatosPersonales() {
        Assert.assertEquals("Prueba", tarjetasDeCreditoPage.getValidarName().getText());
        Assert.assertEquals("Automation", tarjetasDeCreditoPage.getValidarSurname().getText());
        Assert.assertEquals("12345678", tarjetasDeCreditoPage.getValidarDocumento().getText());
    }

    @Step("Click Denunciar Robo o Extravio")
    public void clickDenunciarRoboExtravio() {
        tarjetasDeCreditoPage.getDenunciaRoboExtravio().click();
    }

    @Step("Click check Robo")
    public void checkRobo() {
        tarjetasDeCreditoPage.getLabelRobo().click();
    }

    @Step("Completar Email")
    public void completarEmail(String email) {
        tarjetasDeCreditoPage.getInputMail().sendKeys(email);
    }
    @Step("Completar Email BERSA y BSF")
    public void completarEmailBERSAyBSF(String email) {
        tarjetasDeCreditoPage.getInputMailBERSAyBSF().sendKeys(email);
    }

    @Step("CompletarTelefono")
    public void completarTelefono(String telefono) {
        tarjetasDeCreditoPage.getInputTelefono().sendKeys(telefono);
    }

    @Step("click pagar mi tarjeta")
    public void clickPagarTarjeta() {
        tarjetasDeCreditoPage.getPagarMiTarjeta().click();
    }

    @Step("seleccionar tipo de pago")
    public void seleccionarTipoDePago(String tipoPago) throws AWTException {
        scrollTo(By.xpath("//div[contains(text(), 'Ingresar un importe')]"));
        tarjetasDeCreditoPage.getDesplegableTipoDePago().isDisplayed();
        tarjetasDeCreditoPage.getDesplegableTipoDePago().click();
        seleccionarDropdownGenerico(tipoPago);
    }

    @Step("Completar Importe")
    public void setImporte(String importe) throws InterruptedException {
        Thread.sleep(2000);
        tarjetasDeCreditoPage.getInputImporte().sendKeys(importe);
    }

    @Step("click boton pagar")
    public void clickBotonPagar() {
        tarjetasDeCreditoPage.getBotonPagar().isDisplayed();
        tarjetasDeCreditoPage.getBotonPagar().click();
        try{
            if(botonesGenericosPage.getMensajeEmergente().isDisplayed()){
                botonesGenericosPage.getMensajeEmergente().click();
            }
        }catch (Exception e){
            System.out.println("No aparecio el mensaje emergente");
        }
    }

    @Step("Validar campos de Pago de Tarjeta")
    public void validacionDeCamposPagoDeTarjeta() {
        List<String> campos = Arrays.asList(
                "Alias por defecto",
                "Saldo en pesos",
                "Pago m",
                "Vencimiento",
                "Cuenta d",
                "Tipo de pago",
                "Importe a pagar"
        );

        for (String campo : campos) {
            scrollTo(By.xpath("//span[contains(text(), '" + campo + "')]"));
            Assert.assertTrue("El campo '" + campo + "' no está visible", tarjetasDeCreditoPage.getValidacionText(campo).isDisplayed());
        }

        System.out.println("Titulos validados con éxito");
    }

    @Step("click ultimo resumen")
    public void consultaUltimoResumen() {
        tarjetasDeCreditoPage.getUltimoResumen().click();
    }

    @Step("Validacion campos de ultimo resumen")
    public void validacionCamposUltimoResumen() {
        List<String> campos = Arrays.asList(
                "Marca",
                "Nro de Tarjeta",
                "Nro de Cuenta de Cr",
                "Vencimiento Anterior",
                "Vencimiento Actual",
                "Vencimiento Pr",
                "Fecha de cierre",
                "Fecha de cierre pr",
                "Pago M",
                "Movimientos del per"
        );
        for (String campo : campos) {
            scrollTo(By.xpath("//span[contains(text(), '" + campo + "')]"));
            Assert.assertTrue("El campo '" + campo + "' no está visible", tarjetasDeCreditoPage.getValidacionText(campo).isDisplayed());
        }
        System.out.println("Titulos validados con éxito");
    }

    @Step("Validacion campos Seguimiento tarjeta")
    public void validacionCamposSeguimientoTarjeta() {
        List<String> campos = Arrays.asList(
                "Fecha de",
                "Tipo",
                "Marca",
                "de tarjeta",
                "Nombre y Apellido",
                "Estado"
        );
        for (String campo : campos) {
            scrollTo(By.xpath("//span[contains(text(), '" + campo + "')]"));
            Assert.assertTrue("El campo '" + campo + "' no está visible", tarjetasDeCreditoPage.getValidacionText(campo).isDisplayed());
        }
        System.out.println("Titulos validados con éxito");
    }
    @Step("Validacion detalles tarjeta credito")
    public void validacionTarjetaCredito() {
        List<String> campos = Arrays.asList(
                "Saldos consolidados",
                "Límite de compra",
                "Disponible",
                "Límite adelanto en efectivo" ,
                "Disponible adelanto en efectivo",
                "Próximo cierre",
                "Próximo vencimiento",
                "Saldo actual en pesos",
                "Saldo actual en dólares");
        for (String campo : campos) {
            String campo1 = new String(campo.getBytes(),
                    StandardCharsets.UTF_8);
            scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]", campo1)));
            Assert.assertTrue("El campo '" + campo1 + "' no esta visible", tarjetasDeCreditoPage.getValidacionText(campo1).isDisplayed());
        }
        System.out.println("Titulos validados con exito");
    }
    @Step("Validacion campos dentro de detalle Seguimiento tarjeta")
    public void validacionDentroDeTarjeta() {
        List<String> campos = Arrays.asList(
                "Fecha de",
                "Tipo",
                "Marca",
                "de tarjeta",
                "Datos del titular",
                "Nombre y Apellido",
                "Tipo de documento",
                "de documento",
                "Tarjeta"
        );
        for (String campo : campos) {
            scrollTo(By.xpath("//span[contains(text(), '" + campo + "')]"));
            Assert.assertTrue("El campo '" + campo + "' no está visible", tarjetasDeCreditoPage.getValidacionText(campo).isDisplayed());
        }
        System.out.println("Titulos validados con éxito");
    }

    @Step("click detalle tarjeta")
    public void ingresarADetalleSeguimientoTarjeta() {
        tarjetasDeCreditoPage.getIngresarAdetalleTarjeta().click();
    }

    @Step("Validar mensaje no contas con ofertas")
    public void validarMensajeNoContasConOfertas() {
        tarjetasDeCreditoPage.getValidacionText("No contás con oferta de Tarjeta de Crédito en este momento.").isDisplayed();
    }

    @Step("Validar mensaje 'Solicitar turno en sucursal para tramitar alta de tc'")
    public void validarMensajeSolicitaTurnoEnSucursal() {
        tarjetasDeCreditoPage.getValidacionText("dirigite a tu sucursal mas cercana para tramitar el alta de esta tarjeta. Solicitá tu turno acá.").isDisplayed();
    }

    @Step("Click 'Solicitar turno en sucursal para tramitar alta de tc'")
    public void clickSolicitaTurnoEnSucursal() {
        tarjetasDeCreditoPage.getButtonTurno().isDisplayed();
        tarjetasDeCreditoPage.getButtonTurno().click();
    }

    @Step("click checkbox persona expuesta politicamente")
    public void clickCheckboxPersonaExpuesta() {
        scrollTo(By.xpath("//*[contains(@aria-label,'checkbox No chequeado Persona expuesta pol')]"));
        tarjetasDeCreditoPage.getCheckboxPersonaExpuesta().click();
    }

    @Step("click checkbox residencia fiscar en el exterior")
    public void clickCheckboxResidenciaFiscalExterior() {
        scrollTo(By.xpath("//*[contains(@aria-label,'checkbox No chequeado Residencia Fiscal en el exterior')]"));
        tarjetasDeCreditoPage.getCheckboxResidenciaFiscalExterior().click();
    }

    @Step("click checkbox sujeto obligado iuf")
    public void clickCheckboxSujetoObligadoIUF() {
        scrollTo(By.xpath("//*[contains(@aria-label,'checkbox No chequeado Sujeto Obligado UIF')]"));
        tarjetasDeCreditoPage.getCheckboxSujetoObligado().click();
    }

    @Step("Validar mensaje")
    public void validarMensaje(String mensaje) {
        tarjetasDeCreditoPage.getValidacionText(mensaje).isDisplayed();
    }

    @Step("Seleccionar marca de la tarjeta si esta disponible")
    public void seleccionarMarcaTarjeta() {
        if (tarjetasDeCreditoPage.getSelectorMarcaDeTarjeta().isDisplayed()) {
            tarjetasDeCreditoPage.getSelectorMarcaDeTarjeta().click();
            keyDownXParametrosGenerico(0, "");
        } else {
            System.out.println("El seletor de Marca tarjeta no se encuentra presente en la pagina");
        }

    }

    @Step("Validacion detalles tarjeta credito")
    public void validacionTarjetaCreditoVisaYMaster() {
        UtilsGP.fixedWait(6000);
        List<String> campos = Arrays.asList(
                "Límite de compra",
                "Disponible",
                "Límite adelanto en efectivo" ,
                "Disponible adelanto en efectivo",
                "Cierre",
                "Vencimiento",
                "Próximo cierre",
                "Próximo vencimiento",
                "Saldo actual en pesos",
                "Saldo actual en dólares");
        for (String campo : campos) {
            String campo1 = new String(campo.getBytes(),
                    StandardCharsets.UTF_8);
            scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]", campo1)));
            Assert.assertTrue("El campo '" + campo1 + "' no esta visible", tarjetasDeCreditoPage.getValidacionText(campo1).isDisplayed());
        }
        System.out.println("Titulos validados con exito");
    }

    @Step("Click Desplegable -Consulta de Resúmen-")
    public void clickBtnDesplegableResumen(String nombre, String nombre2) {
        scrollTo(By.xpath("//span[contains(text(), '"+ nombre +"')]"));
        botonesGenericosPage.getlistaDesplegableGenerico2(nombre).click();
        botonesGenericosPage.getlistaDesplegableGenerico2(nombre2).click();
    }

    @Step("Validar nombre de Tarjeta")
    public void validarNombreTarjeta(String nombre) {

        tarjetasDeCreditoPage.getTituloVisa(nombre);
    }
}


