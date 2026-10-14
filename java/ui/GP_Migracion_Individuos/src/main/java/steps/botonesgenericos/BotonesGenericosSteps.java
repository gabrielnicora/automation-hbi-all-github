package steps.botonesgenericos;

import global.practia.jemeinsamstark.web.pages.Page;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.bouncycastle.tsp.TSPUtil;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.inversiones.plazosfijos.SolicitarPlazoFijoSteps;
import steps.transferencias.transferenciasinmediatas_programadas.TransferenciasInmediatasSteps;
import utils.UtilsGP;
import pages.transferencias.transferenciasinmediatas.TransferenciasInmediatasPage;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import pages.cuentas.echeq.EcheqPage;

import static org.junit.Assert.assertTrue;


public class BotonesGenericosSteps extends Page {

    private static BotonesGenericosPage botonesGenericos = new BotonesGenericosPage();
    private UtilsGP utilsgp = new UtilsGP();
    private TransferenciasInmediatasPage transferencias = new TransferenciasInmediatasPage();
    private EcheqPage echeqs = new EcheqPage();



    @Step("Click Confirmar Generico")
    public void clickConfirmarGenerico() {
        scrollTo(By.xpath("//button[@aria-label='Confirmar']"));
        botonesGenericos.getConfirmar().click();
    }

    @Step("validar boton Confirmar")
    public void validarBotonConfirmar() {
        scrollTo(By.xpath("//button[@aria-label='Confirmar']"));
        botonesGenericos.getConfirmar().isDisplayed();
    }

    @Step("Click Continuar Generico")
    public void clickContinuarGenerico() {
        try {
            scrollTo(By.xpath("//button[@aria-label='Continuar']"));
            botonesGenericos.getContinuar().click();
            if(utilsgp.estaPresente(botonesGenericos.getMensajeEmergente().getElementBy(),2)){
                System.out.println("========ERROR MENSAJE EMERGENTE : " + botonesGenericos.getMensajeEmergente().getText());
            }
        } catch (Exception e) {
            System.out.println("============== NO SE ENCONTRO MENSAJE EMERGENTE ===============");
        }
    }

    @Step("Click Modificar Generico")
    public void clickModificarGenerico() {
        botonesGenericos.getModificar().click();
    }

    @Step("Click Aceptar Generico")
    public void clickAceptarGenerico() {
        botonesGenericos.getAceptar().isDisplayed();
        botonesGenericos.getAceptar().click();
    }

    @Step("Click Consultar Generico")
    public void clickConsultarGenerico() {
        botonesGenericos.getConsultar().click();
    }

    @Step("Click Cancelar Generico")
    public void clickCancelarGenerico() {

        if (botonesGenericos.getCancelar().isDisplayed()){
            scrollTo(By.xpath("//button[@aria-label='Cancelar']"));
            botonesGenericos.getCancelar().click();
        }
    }

    @Step("Click Rechazar Generico")
    public void clickRechazarGenerico() {
        botonesGenericos.getRechazar().click();
    }

    @Step("Click Guardar Generico")
    public void clickGuardarGenerico() {
        botonesGenericos.getGuardar().click();
    }

    @Step("Click Volver Generico")
    public void clickVolverGenerico() {
        By volver = botonesGenericos.getVolver().getElementBy();
        By notificacionExitosaVisible = botonesGenericos.getNotificationSuccess().getElementBy();

        scrollTo(volver);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(notificacionExitosaVisible));
        wait.until(ExpectedConditions.elementToBeClickable(volver));
        botonesGenericos.getVolver().click();
    }


    @Step("Click Volver Flecha Generico")
    public void clickVolverFlechaGenerico() {
        By volverFlecha = botonesGenericos.getVolverFlecha().getElementBy();
        By notificacionExitosaVisible = botonesGenericos.getNotificationSuccess().getElementBy();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(notificacionExitosaVisible));
        wait.until(ExpectedConditions.elementToBeClickable(volverFlecha));
        botonesGenericos.getVolverFlecha().click();
    }

    @Step("Click Mas Opciones Generico")
    public void clickMasOpcionesGenerico() {
        botonesGenericos.getMasOpciones().isDisplayed();
        botonesGenericos.getMasOpciones().click();
        UtilsGP.fixedWait(5000);
    }

    @Step("Validar titulo Generico: {titulo}")
    public void validarTituloPageGenerico(String titulo) {
        UtilsGP.fixedWait(5000);
        scrollTo(botonesGenericos.getTituloPage(titulo).getElementBy());
        Assert.assertTrue("Titulo no visible", botonesGenericos.getTituloPage(titulo).isDisplayed());

    }

    @Step("Validar titulo Generico: {titulo}")
    public void validarTituloPageGenerico2(String titulo) {
        Assert.assertTrue("Titulo no visible", botonesGenericos.getTituloPage2(titulo).isDisplayed());
        scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]", titulo)));
    }

    @Step("Validar Msj Campo Obligatorio")
    public void validarCampoObligatorioGenerico() {
        Assert.assertTrue("Mensaje no visible", botonesGenericos.getCampoObligatorio().isDisplayed());
    }

    @Step("Click Descargar PDF")
    public void clickDescargarPdfGenerico() {
        botonesGenericos.getDescargarPdf().click();
    }

    @Step("Click en Hasta")
    public void clickHasta() {
        botonesGenericos.getFechaHasta().click();
    }

    @Step("Verificar Icono Descargar PDF")
    public void validarDescargarPdfGenerico() {
        WebDriverWait wait = new WebDriverWait(global.practia.jemeinsamstark.web.core.WebDriver.getInstance(), 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-label='Icono de descargar  en formato pdf']")));
        Assert.assertTrue("Icono no visible", botonesGenericos.getDescargarPdf().isDisplayed());
    }

    @Step("Click Descargar XLS")
    public void clickDescargarXlsGenerico() {
        botonesGenericos.getDescargarXls().click();
    }

    @Step("Verificar Icono Descargar XLS")
    public void validarDescargarXlsGenerico() {
        WebDriverWait wait = new WebDriverWait(global.practia.jemeinsamstark.web.core.WebDriver.getInstance(), 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-label='Icono de descargar  en formato xls']")));
        Assert.assertTrue("Icono no visible", botonesGenericos.getDescargarXls().isDisplayed());
    }

    @Step("Se selecciona el elemento {textoValidar}")
    public void keyDownXParametrosGenerico(int repeticiones, String textoValidar) {
        utilsgp.keyDown(repeticiones);
        if (textoValidar != "") {
            Assert.assertTrue("elemento no coicidente", botonesGenericos.getTextoDesplegableGenerico(textoValidar).isDisplayed());
        }
    }


    @Step("Se selecciona el elemento {texto}")
    public void keyDownToText(String texto) throws AWTException {
        try {
            utilsgp.keyDownAndSelectElement(texto);
        } catch (Exception e) {
            utilsgp.keyDown(3);
            scrollTo(By.xpath(String.format("//*[contains(text(),'%s')]", texto)));
            if (!botonesGenericos.getTextKeyDownToText(texto).isDisplayed()) {
                throw new NoSuchElementException("Elemento no encontrado o no visible: " + texto);
            }
        }
        if (!texto.isEmpty()) {
            System.out.println("Opción seleccionada del desplegable: " + texto);
        }
    }

    @Step("Completar Importe")
    public void setImporteGenerico(String importe) {
        TransferenciasInmediatasSteps.montoTransferido = Integer.parseInt(importe);
        botonesGenericos.getCampoImporte().isDisplayed();
        botonesGenericos.getCampoImporte().sendKeys(importe);
    }

    @Step("Click Guardar plantilla")
    public void clickGuardarPlantillaGenerico() {
        botonesGenericos.getGuardarPlantilla().click();
    }

    @Step("Click Boton Más Listado Transferencia")
    public void clickVerMas() {
        UtilsGP.fixedWait(3000);
        scrollTo(botonesGenericos.getVerMas().getElementBy());
        botonesGenericos.getVerMas().click();
    }

    @Step("Click Guardar borrador")
    public void clickGuardarBorradorGenerico() {
        botonesGenericos.getGuardarBorrador().click();
    }

    @Step("Click checkbox Terminos y Condiciones")
    public void clickCheckTerminosCondicionesGenerico() {
        Assert.assertTrue("NO SE ENCONTRO EL BOTON DE TERMINOS Y CONDICIONES " + botonesGenericos.getCheckTerminosCondiciones().getElementBy(),botonesGenericos.getCheckTerminosCondiciones().isDisplayed());
        scrollTo(By.xpath("//*[@name='agreement']/following-sibling::span"));
        botonesGenericos.getCheckTerminosCondiciones().click();
    }

    @Step("Click boton Aceptar Terminos y Condiciones")
    public void clickAceptarTyCGenerico() {
        scrollTo(By.id("termsAndConditions.component.aceptar.label"));
        botonesGenericos.getAceptarTerminos().click();
    }

    @Step("Click boton Cancelar Termminos y Condiciones")
    public void clickCancelarTyCGenerico() {
        scrollTo(By.id("termsAndConditions.component.cancelar.label"));
        botonesGenericos.getCancelarTerminos().click();
    }

    @Step("Se valida descarga del archivo {nombreArchivo}")
    public void validarDescarga(String nombreArchivo) throws InterruptedException { //nombre ej: "Tenencias_31_12.pdf"
        UtilsGP.fixedWait(3000);
        Assert.assertTrue("Archivo " + nombreArchivo + " no existe", utilsgp.validarExistenciaArchivo(nombreArchivo));
    }

    @Step("Se valida descarga del archivo {nombreArchivo}")
    public void
    validarDescargaSinEliminarArchivo(String nombreArchivo) throws InterruptedException { //nombre ej: "Tenencias_31_12.pdf"
        UtilsGP.fixedWait(3000);
        Assert.assertTrue("Archivo " + nombreArchivo + " no existe", utilsgp.validarExistenciaArchivoSinEliminarlo(nombreArchivo));
    }

    @Step("Click boton Validar")
    public void clickValidarGenerico() {
        Assert.assertTrue("No se encuentra boton Validar", botonesGenericos.getValidar().isDisplayed());
        botonesGenericos.getValidar().click();
    }

    @Step("Click icono Eliminar")
    public void clickIconoEliminar() {
        botonesGenericos.getIconoEliminar().sendKeys(Keys.RETURN);
    }

    @Step("Click icono Editar")
    public void clickIconoEditar() {
        botonesGenericos.getIconoEditar().click();
    }

    @Step("Click Salir de la sesion")
    public void clickSalirDelaSesion() {
        botonesGenericos.getSalirSesion().click();
    }

    @Step("Click desplegable Tarjeta de debito doble factor")
    public void clickTarjetaDebitoDobleFactor() {
        botonesGenericos.getTarjetaDebitoDobleFactor().click();
    }

    @Step("Se valida Notificacion emergente: {mensaje}")
    public void validaNotificacionEmergenteGenerico(String mensaje) {
        wait.until(ExpectedConditions.visibilityOf(botonesGenericos.getNotificacionEmergente()));
        String notificacion = botonesGenericos.getNotificacionEmergente().getText();
        notificacion = UtilsGP.removerAcentos(notificacion);
        Assert.assertEquals(notificacion, mensaje);
    }

    public void validaNotificacionExitosaGenerico(String mensaje) {
        //wait.until(ExpectedConditions.visibilityOf(botonesGenericos.getNotificationSuccess()));
        botonesGenericos.getNotificationSuccess().isDisplayed();
        String notificacion = botonesGenericos.getNotificationSuccess().getText();
        System.out.println("MENSAJE EXITO : " + notificacion);
        notificacion = UtilsGP.removerAcentos(notificacion);
        Assert.assertTrue(notificacion.contains(mensaje));
    }

    @Step("Se valida notificacion de error: {error}")
    public void validaNotificacionErrorGenerico(String error) {
        String notificacion = botonesGenericos.getNotificacionError().getText();
        notificacion = utilsgp.removerAcentos(notificacion);
        Assert.assertEquals("La notificacion no esta presente", notificacion, error);
    }

    @Step("Click icono imprimir")
    public void clickImprimir() {
        botonesGenericos.getIconoImprimir().click();
    }

    @Step("Click buscar generico")
    public void clickBuscarGenerico() {
        this.scrollTo(By.xpath("//button[@aria-label='Buscar']"));
        botonesGenericos.getBuscar().click();
    }

    @Step("Validar URL")
    public void validarFragmentoURL(String fragmentoURL) throws InterruptedException {
        UtilsGP utilsGP = new UtilsGP();
        utilsGP.ManageTabPorParametro(2);
        wait.until(ExpectedConditions.urlContains(fragmentoURL));
        String URL_Completa = driver.getCurrentUrl();

        assertTrue("No contiene la palabra "+ fragmentoURL + " en la URL ",
                URL_Completa.contains(fragmentoURL));






//        if (URL_Completa.contains(fragmentoURL)) {
//            System.out.println("El fragmento de URL: " + fragmentoURL + " esta presente en la URL actual:" + URL_Completa);
//        } else {
//            throw new Error("El fragmento de URL: " + fragmentoURL + " no esta presente en la URL actual." + URL_Completa);
//        }
//        utilsGP.backMainTap();
    }

    @Step("Click icono de cerrar")
    public void clickCerrar() throws InterruptedException {
        Thread.sleep(2000);
        botonesGenericos.getIconoCerrar().click();
    }

    @Step("validar boton Continuar")
    public void validarBotonContinuar() {
        scrollTo(By.xpath("//button[@aria-label='Continuar']"));
        utilsgp.estaPresente(botonesGenericos.getContinuar(), 2);
    }

    @Step("validar Botones Genericos Inhabilitados")
    public void validarBotonDeshabilitado(String nombreBoton) {
        scrollTo(By.xpath(String.format("//button[@aria-label='%s']", nombreBoton)));
        Assert.assertTrue("No se encontro el boton", utilsgp.estaPresente(botonesGenericos.getBotonesDiables(nombreBoton), 2));
        System.out.println("BOTON " + nombreBoton + " DESHABILITADO");
    }

    @Step("validar Botones Genericos Habilitados")
    public void validarBotonHabilitado(String nombreBoton) {
        scrollTo(By.xpath(String.format("//button[@aria-label='%s']", nombreBoton)));
        Assert.assertFalse("El botón se encuentra habilitado", utilsgp.estaPresente(botonesGenericos.getBotonesDiables(nombreBoton), 2));
        System.out.println("BOTON " + nombreBoton + " DESHABILITADO");
    }

    @Step("Se selecciona la opcion {opcion}")
    public void seleccionarDropdownGenerico(String opcion) {
        UtilsGP.fixedWait(8000);
        botonesGenericos.getSeleccionarGenerico(opcion).click();
    }

    @Step("Se selecciona Plazo Fijo UVA")
    public void seleccionarPFU() {
        UtilsGP.fixedWait(1000);
        botonesGenericos.getSeleccionarPlazoFijoUVA().click();
    }

    @Step("Hacer click en combo box seleccionar cuenta Debito")
    public void clickComboCuentaDebito() {
        scrollTo(botonesGenericos.getSeleccionCuentaDebito().getElementBy());
        botonesGenericos.getSeleccionCuentaDebito().isDisplayed();
        botonesGenericos.getSeleccionCuentaDebito().click();
    }

    @Step("Hacer click en botón Filtrar")
    public void clickbtnFiltrar() {
        botonesGenericos.getSeleccionCuentaDebito().click();
    }

    @Step("Hacer click en Pagar")
    public void clickbtnPagar() {
        UtilsGP.fixedWait(2000);
        botonesGenericos.getPagar().click();
    }

    @Step("Completar Referencia")
    public void setReferenciaGenerica(String referencia) {
        botonesGenericos.getCampoReferencia().isDisplayed();
        botonesGenericos.getCampoReferencia().sendKeys(referencia);
    }

    @Step("Click en Adherir Servicio Nuevo")
    public void seleccionarServicioGenerico1(String servicio) {
        UtilsGP.fixedWait(5000);
        botonesGenericos.getSeleccionarServicio1(servicio).click();
    }

    @Step("Click en Servicio Adheridos")
    public void seleccionarServicioGenerico2(String servicio) {
        UtilsGP.fixedWait(5000);
        botonesGenericos.getSeleccionarServicio2(servicio).click();
    }

    @Step("Click desplegable lista -Genérico-")
    public void clickListaDesplegableGenerico(String lista) {
        UtilsGP.fixedWait(5000);
        botonesGenericos.getlistaDesplegableGenerico(lista).click();
    }

    @Step("Click desplegable lista -Genérico-")
    public void clickListaDesplegableGenerico2(String lista) {

        scrollTo(botonesGenericos.getlistaDesplegableGenerico2(lista).getElementBy());
        botonesGenericos.getlistaDesplegableGenerico2(lista).click();
        UtilsGP.fixedWait(3000);
    }

    @Step("Validar visualización de Comprobante-")
    public void validarComprobante(String lista) {
        String comprobante;

        UtilsGP.fixedWait(5000);

        comprobante = botonesGenericos.getSeleccionarServicio1(lista).getText();

        Allure.addAttachment("Código de pago del comprobane; ", comprobante);

        Assert.assertFalse("No hay resultados para esta búsqueda", !botonesGenericos.getSeleccionarServicio1(lista).isDisplayed());
    }

    @Step("Click genérico botón")
    public void clickBtnGenerico(String boton) {
        UtilsGP.fixedWait(4000);
        botonesGenericos.getSeleccionarServicio2(boton).click();
    }

    @Step("Click genérico botón")
    public void clickButtonPorTexto(String boton) {
        UtilsGP.fixedWait(4000);
        botonesGenericos.getBotonPorTexto(boton).click();
    }

    @Step("Se borra el archivo {nombreArchivo}")
    public void eliminarArchivoDescargado(String nombreArchivo) throws InterruptedException { //nombre ej: "Tenencias_31_12.pdf"
        UtilsGP.fixedWait(3000);
        Assert.assertTrue("Archivo " + nombreArchivo + " no existe", utilsgp.eiliminarArchivo(nombreArchivo));
    }

    @Step("Validar pantalla completa 'Confirmacion de solicitud'")
    public void validacionTextosCrearUsuario() {
        List<String> campos = Arrays.asList(
                "Debe tener de 8 a 12 caracteres",
                "Puede contener números y letras (sin tildes ni \"ñ\")");
        for (String campo : campos) {
            scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]", campo)));
            Assert.assertTrue("El campo '" + campo + "' no esta visible", Boolean.parseBoolean(campo));
            //Assert.assertTrue("Boton confirmar no se encuentra disponible", botonesGenericos.getConfirmar().isEnabled());
        }
        System.out.println("Titulos validados con exito");
    }

    @Step("Completar Importe para validar botón Continuar")
    public void setImporteGenericoValidacion(String importe) {
        botonesGenericos.getCampoImporte().sendKeys(importe);
    }

    @Step("Click sobre campo Importe")
    public void clickCampoImporte() {
        botonesGenericos.getCampoImporte().isDisplayed();
        botonesGenericos.getCampoImporte().click();
    }

    @Step("Validar Msj Compra fuera de horario")
    public void validarMsjCompraFueraDeHorario(String texto) {
        Assert.assertTrue("Mensaje no visible", botonesGenericos.getTituloPage2(texto).isDisplayed());
    }

    @Step("Click sobre Botón -Ahora No-")
    public void clickBtnAhoraNo(String titulo) {
        UtilsGP.fixedWait(3000);

        if (botonesGenericos.getSeleccionarServicio2(titulo).isDisplayed()){

            botonesGenericos.getSeleccionarServicio2(titulo).click();
        }
    }

    @Step("Validar cartel -Empezá a operar-")
    public void clickBtnCancelarSobreCartelEmpezaAOperar() {
        UtilsGP.fixedWait(3000);

        if (botonesGenericos.getCancelar().isDisplayed()){
            botonesGenericos.getCancelar().click();
        }
    }

    @Step("Validar títulos de Filtros de Búsqueda de Echeqs")
    public void validarOrdenDeTitulosDeFiltrosDeBusqueda() {

        Assert.assertTrue("Título Filtrar no aparece visible", botonesGenericos.getlistaDesplegableGenerico2("Filtrar").isDisplayed());
        Assert.assertTrue("Etiqueta Cuit/Emisor no aparece visible", botonesGenericos.getlistaDesplegableGenerico("cuit/cuil Emisor").isDisplayed());
        Assert.assertTrue("Etiqueta Estado no aparece visible", botonesGenericos.getlistaDesplegableGenerico2("Estado").isDisplayed());
        Assert.assertTrue("Etiqueta Nro Echeq no aparece visible", botonesGenericos.getlistaDesplegableGenerico("Nro. ECheq").isDisplayed());
        Assert.assertTrue("Etiqueta ID Agrupador no aparece visible", botonesGenericos.getlistaDesplegableGenerico("Id agrupador").isDisplayed());
        Assert.assertTrue("Etiqueta Fecha de Emisión Desde no aparece visible", botonesGenericos.getlistaDesplegableGenerico2("Fecha de emisión desde").isDisplayed());
        Assert.assertTrue("Etiqueta Fecha de Emisión Hasta no aparece visible", botonesGenericos.getlistaDesplegableGenerico2("Fecha de emisión hasta").isDisplayed());
        scrollTo(By.xpath("//span[contains(text(), 'Fecha de pago desde')]"));
        Assert.assertTrue("Etiqueta Fecha de Pago Desde no aparece visible", botonesGenericos.getlistaDesplegableGenerico2("Fecha de pago desde").isDisplayed());
        Assert.assertTrue("Etiqueta Fecha de Pago Hasta no aparece visible", botonesGenericos.getlistaDesplegableGenerico2("Fecha de pago hasta").isDisplayed());
        Assert.assertTrue("Botón Buscar no aparece visible", botonesGenericos.getlistaDesplegableGenerico2("Aplicar").isDisplayed());
    }


    @Step("Realizar la búsqueda del Echeq")
    public void busquedaDeEcheq() {

        transferencias.getFiltrar().click();
//        botonesGenericos.getlistaDesplegableGenerico("cuit/cuil Emisor").click();
        echeqs.getInputCuitCuilEmisor().sendKeys("20063078175");
        echeqs.getDesplegableEstado().click();
        botonesGenericos.getSeleccionarGenerico("Emitido pendiente").click();
        echeqs.getFechaDeEmisionDesde().click();
        echeqs.getButtonCalendar("Previous").click();
        echeqs.getButtonCalendar("Previous").click();
        botonesGenericos.getlistaDesplegableGenerico("7").click();
        echeqs.getFechaDeEmisionHasta().click();
        echeqs.getButtonCalendar("Next").click();
        echeqs.getButtonCalendar("Next").click();
        botonesGenericos.getlistaDesplegableGenerico("1").click();
        echeqs.getBtnAplicar().click();
        Assert.assertTrue("El nro de CUIT no aparece visible", botonesGenericos.getSeleccionarServicio1("20063078175").isDisplayed());
        Assert.assertTrue("El estado no aparece visible", botonesGenericos.getDatoGrillaGenerico("Estado: Emitido-Pendiente").isDisplayed());
        Assert.assertTrue("La fecha de emisión no aparece visible", botonesGenericos.getSeleccionarServicio1("/").isDisplayed());


        Assert.assertTrue("Etiqueta Estado no aparece visible", botonesGenericos.getlistaDesplegableGenerico2("Estado").isDisplayed());
        Assert.assertTrue("Etiqueta Nro Echeq no aparece visible", botonesGenericos.getlistaDesplegableGenerico("Nro. ECheq").isDisplayed());
        Assert.assertTrue("Etiqueta ID Agrupador no aparece visible", botonesGenericos.getlistaDesplegableGenerico("Id agrupador").isDisplayed());
        Assert.assertTrue("Etiqueta Fecha de Emisión Desde no aparece visible", botonesGenericos.getlistaDesplegableGenerico2("Fecha de emisión desde").isDisplayed());
        Assert.assertTrue("Etiqueta Fecha de Emisión Hasta no aparece visible", botonesGenericos.getlistaDesplegableGenerico2("Fecha de emisión hasta").isDisplayed());
        scrollTo(By.xpath("//span[contains(text(), 'Fecha de pago desde')]"));
        Assert.assertTrue("Etiqueta Fecha de Pago Desde no aparece visible", botonesGenericos.getlistaDesplegableGenerico2("Fecha de pago desde").isDisplayed());
        Assert.assertTrue("Etiqueta Fecha de Pago Hasta no aparece visible", botonesGenericos.getlistaDesplegableGenerico2("Fecha de pago hasta").isDisplayed());
        Assert.assertTrue("Botón Buscar no aparece visible", botonesGenericos.getlistaDesplegableGenerico2("Aplicar").isDisplayed());
    }

}
