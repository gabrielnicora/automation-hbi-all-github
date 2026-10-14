package steps.cuentas.echeq;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.checkerframework.checker.units.qual.A;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.cuentas.detallecuenta.DetalleCuentaPage;
import pages.cuentas.echeq.EcheqPage;
import org.junit.Assert;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.Settings;
import utils.UtilsGP;
import pages.transferencias.transferenciasinmediatas.TransferenciasInmediatasPage;


import java.io.FilterOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class EcheqSteps extends BotonesGenericosSteps {

    EcheqPage echeqPage = new EcheqPage();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    TransferenciasInmediatasPage transferencias = new TransferenciasInmediatasPage();
    private DetalleCuentaPage detalleCuentaPage = new DetalleCuentaPage();

    @Step("Ingresar CUIT/CUIL Emisor")
    public void setCuitCuilEmisor(String cuitCuil){
        echeqPage.getInputCuitCuilEmisor().sendKeys(cuitCuil);
    }
    @Step("Ingresar ID Agrupador")
    public void setIdAgrupador(String idAgrupador){
        echeqPage.getInputIdAgrupador().sendKeys(idAgrupador);
    }
    @Step("Click en calendario Fecha de emisión desde")
    public void clickFechaEmisionDesde(){
        echeqPage.getFechaDeEmisionDesde().click();
    }
    @Step("Validar Fecha emision seleccionada == {fecha} ")
    public void validarFechaEmisionSeleccionada(String fecha){
        Assert.assertTrue("Fecha no coincidente a la ingresada", echeqPage.getTextoFechaDeEmision(fecha).isDisplayed());
    }
    @Step("Click en calendario Fecha de emisión hasta")
    public void clickFechaEmisionHasta(){
        echeqPage.getFechaDeEmisionHasta().click();
    }
    @Step("Click en calendario Fecha de pago desde")
    public void clickFechaPagoDesde(){
        echeqPage.getFechaDePagoDesde().click();
    }
    @Step("Click en calendario Fecha de pago hasta")
    public void clickFechaPagoHasta(){
        echeqPage.getFechaDePagoHasta().click();
    }
    @Step("Click deplegable Estado")
    public void clickEstado(){
        echeqPage.getDesplegableEstado().click();
    }
    @Step("Click boton Buscar")
    public void clickBotonBuscar(){
        Assert.assertTrue("Boton Buscar no se encuentra",echeqPage.getBotonBuscar().isDisplayed());
        scrollTo(By.xpath("//button[@aria-label='Buscar']"));
        echeqPage.getBotonBuscar().click();
    }
    @Step("Validar completitud de pantalla")
    public void validarCompletitudPantalla(){
        Assert.assertTrue("No se encuentra campo Echeq",echeqPage.getTituloTablaEcheq("Nro. ECheq").isDisplayed());
        Assert.assertTrue("No se encuentra campo CUIT/CUIL Emisor",echeqPage.getTituloTablaEcheq("CUIT/CUIL").isDisplayed());
        Assert.assertTrue("No se encuentra campo Emisor",echeqPage.getTituloTablaEcheq("Emisor").isDisplayed());
        Assert.assertTrue("No se encuentra campo Estado",echeqPage.getTituloTablaEcheq("Estado").isDisplayed());
        Assert.assertTrue("No se encuentra campo Importe",echeqPage.getTituloTablaEcheq("Importe").isDisplayed());
        Assert.assertTrue("No se encuentra campo Concepto",echeqPage.getTituloTablaEcheq("Concepto").isDisplayed());
        Assert.assertTrue("No se encuentra campo Fecha de pago",echeqPage.getTituloTablaEcheq("Pago").isDisplayed());
    }
    @Step("Click Ver detalle primer echeq")
    public void clickVerDetallePrimerEcheq(){
        Assert.assertTrue("Primer echeq no se encuentra",echeqPage.getVerDetallePrimerEcheq().isDisplayed());
        scrollTo(By.xpath("(//*[@aria-label='tabla']//*[contains(@aria-label,'flecha')])[1]"));
        echeqPage.getVerDetallePrimerEcheq().click();
    }
    @Step("Click Ver detalle echeq nro {nroEcheq}")
    public void clickVerDetallePorNroEcheq(String nroEcheq){
        Assert.assertTrue("Echeq nro "+nroEcheq+" no se encuentra",echeqPage.getVerDetallePorNroEcheq(nroEcheq).isDisplayed());
        scrollTo(By.xpath(String.format("//*[@aria-label='tabla']//*[contains(text(),'%s')]/parent::div/following-sibling::div[7]",nroEcheq)));
        echeqPage.getVerDetallePorNroEcheq(nroEcheq).click();
    }

    @Step("Completar por Nro de Echeq")
    public void setNroEcheq(String numero) {

        UtilsGP.fixedWait(2000);
        echeqPage.getNroEcheq().isDisplayed();
        echeqPage.getNroEcheq().sendKeys(numero);
    }

    @Step("Validar datos del echeq en la grilla")
    public void validarDatoEhceqGrilla(String dato){

        Assert.assertTrue("No existe el dato en la grilla",
                echeqPage.getDatoNroEcheq(dato).isDisplayed());
        scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]", dato)));

    }

    @Step("Click Boton Aplicar")
    public void clickBotonAplicarBusqueda(){
        echeqPage.getBtnAplicar().isDisplayed();
        scrollTo(echeqPage.getBtnAplicar().getElementBy());
        echeqPage.getBtnAplicar().click();
    }

    @Step("Click sobre Tab Emitidos")
    public void clickTabEmitidos() {
        echeqPage.getTabEstadoEcheq().click();
        UtilsGP.fixedWait(2000);
    }

    @Step("Seleccionar tres echeq")
    public void seleccionarEcheq(String fila1, String fila2, String fila3) {

        echeqPage.getCheckPorFila(fila1).click();
        UtilsGP.fixedWait(2000);
        echeqPage.getCheckPorFila(fila1).sendKeys(Keys.PAGE_DOWN);
        UtilsGP.fixedWait(2000);
        scrollTo(echeqPage.getCheckPorFila(fila3).getElementBy());
        UtilsGP.fixedWait(2000);
        echeqPage.getCheckPorFila(fila3).click();
        UtilsGP.fixedWait(2000);
        echeqPage.getCheckPorFila(fila2).click();
        UtilsGP.fixedWait(2000);
    }

    @Step("Validar texto -No es posible operar-")
    public void validarTextoEstadoOperaativo(){

        Assert.assertTrue("No existe el dato en la grilla",
                echeqPage.getValidacionTextoEstadoOperativo().isDisplayed());
    }

    @Step("Validar datos de la búsqueda de echeq en la grilla")
    public void validarDatoBusquedaEhceqGrilla6meses(String cuit, String estado) throws InterruptedException {


        String cuitEcheq, estadoEcheq, mesDesdeText, mesHastaTesxt = null;

        String listaFechaEmision [] = new String[10];
        String listaCuit [] = new String[10];
        String ilstaEstado [] = new String[10];

        mesDesdeText = echeqPage.getDatoFechaEmisionDesde().getText();
        mesHastaTesxt = echeqPage.getFechaDeEmisionHasta().getText();


        if (echeqPage.getDatoBusquedaEcheqGrilla("1", "7").isDisplayed()) {
            System.out.println("No se encontraron resultados en la grilla");
            return;
        }

        for (int i = 1; i <= 10; i += 1) {

            if (echeqPage.getDatoBusquedaEcheqGrilla(String.valueOf(i), "7").isDisplayed()){


                String fechaTexto = echeqPage.getDatoBusquedaEcheqGrilla(String.valueOf(i), "7").getText();

                cuitEcheq = echeqPage.getDatoBusquedaEcheqGrilla(String.valueOf(i), "9").getText();
                estadoEcheq = echeqPage.getDatoBusquedaEcheqGrilla(String.valueOf(i), "14").getText();

                listaFechaEmision[i - 1] = fechaTexto;


                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                LocalDate fecha = LocalDate.parse(fechaTexto, formatter);
                LocalDate mesDesde = LocalDate.parse(mesDesdeText, formatter);
                LocalDate mesHasta = LocalDate.parse(mesHastaTesxt, formatter);

                Assert.assertTrue("Búsqueda de emisión de echeq fuera de rango: " + fecha,
                !(fecha.isBefore(mesDesde) || fecha.isAfter(mesHasta)));

                listaCuit[i - 1] = cuitEcheq;
                ilstaEstado[i - 1] = estadoEcheq;

                Assert.assertEquals("No Coincide CUIT Echeq ", cuit, listaCuit[i-1]);
                Assert.assertEquals("No Coincide Estado Echeq ", estado, ilstaEstado[i-1]);

                listaFechaEmision[i - 1] = null;
                listaCuit[i - 1] = null;
                ilstaEstado[i - 1] = null;
            }
            else {
                break;}

            if (i == 10 && transferencias.getclickSiguientePaginaGrilla().isDisplayed() ){
                scrollTo(transferencias.getclickSiguientePaginaGrilla().getElementBy());
                //Mudar a Page Botones Genericos
            }
            if (i == 10 && transferencias.getclickSiguientePaginaGrilla().isDisplayed()){
                transferencias.getclickSiguientePaginaGrilla().click();}
            }
    }

    @Step("Validar opciones del dropdown Estado")
    public void validarDropdownEstado(){

        String dropdownEstado [] = new String[14];

        String[] opcionesEstado = new String[]{"Activo", "Activo pendiente", "Anulado",
                "Cesión pendiente", "Custodia", "Depositado", "Devolución pendiente",
                "Emitido pendiente", "Pagado", "Presentado", "Rechazado",
                "Repudiado", "Vencido", "Todos"};


        for (int i = 0; i <= 13; i += 1) {

            echeqPage.getDesplegableEstado().click();
            botonesGenericosPage.getSeleccionarGenerico(opcionesEstado[i]).click();
            dropdownEstado [i] = botonesGenericosPage.getSeleccionarGenerico(opcionesEstado[i]).getText();

            Assert.assertEquals("Esta opción no se encuentra la lista ", opcionesEstado[i], dropdownEstado[i]);

        }
    }

    @Step("Validar guión Detalle Motivo de Pago-")
    public void validarGuionEnDetalleMotivoDePago(){

        String guion = "-";

        for (int i = 1; i <= 10; i += 1) {
            if (echeqPage.getDatoBusquedaEcheqGrilla(String.valueOf(i), "6").isDisplayed()){
                echeqPage.getDatoBusquedaEcheqGrilla(String.valueOf(i), "6").click();
                UtilsGP.fixedWait(2000);
                scrollTo(echeqPage.getMotivoDePagoGuion().getElementBy());
                Settings.guionMotivoDePago = echeqPage.getMotivoDePagoGuion().getText();

                if (Settings.guionMotivoDePago.equals(guion)){
                    break;
                }
                else {
                    botonesGenericosPage.getVolverFlecha().click();}
            }
            else {
                break;}

            if (i == 10 && transferencias.getclickSiguientePaginaGrilla().isDisplayed() ){
                scrollTo(transferencias.getclickSiguientePaginaGrilla().getElementBy());
                //Mudar a Page Botones Genericos
            }
            if (i == 10 && transferencias.getclickSiguientePaginaGrilla().isDisplayed()){
                transferencias.getclickSiguientePaginaGrilla().click();}
        }

        Assert.assertEquals("No se muestra guión en Motivo de Pago", guion, Settings.guionMotivoDePago);
    }

    @Step("Validar estado echeq Cesión Pendiente")
    public void validarEstadoCesionPendiente() {

        String guion = "-";
        String nombreCesionPendiente = "Cesión-Pendiente";
        String colorEstadoCesion = "rgba(255, 241, 184, 1)";
        boolean encontrado = false;

        while (!encontrado) {
            for (int i = 1; i <= 10; i += 1) {
                if (echeqPage.getDatoBusquedaEcheqGrilla(String.valueOf(i), "13").isDisplayed()) {
                    scrollTo(echeqPage.getDatoBusquedaEcheqGrilla(String.valueOf(i), "13").getElementBy());
                    Settings.echeqType = echeqPage.getDatoBusquedaEcheqGrilla
                            (String.valueOf(i), "13").getText();
                    UtilsGP.fixedWait(2000);
                    if (Settings.echeqType.equals(nombreCesionPendiente)) {
                        Settings.colorEstadoEcheq = echeqPage.getEstadoEcheqPorFila().getCssValue("background-color");
                        encontrado = true;
                        break;
                    }
                } else {
                    break;
                }

                if (encontrado) break;
                if (transferencias.getclickSiguientePaginaGrilla().isDisplayed()) {

                    scrollTo(transferencias.getclickSiguientePaginaGrilla().getElementBy());
                    UtilsGP.fixedWait(2000);
                    transferencias.getclickSiguientePaginaGrilla().click();
                    UtilsGP.fixedWait(2000);
                } else {
                    break;
                }
            }
            Assert.assertEquals("Estado Cesión Pendiente sin color " +
                    "amarillo correspondiente", colorEstadoCesion, Settings.colorEstadoEcheq);
        }
    }

    @Step("Validar estado echeq Devolución Pendiente")
    public void validarEstadoDevolucionPendiente() {

        String guion = "-";
        String nombreDevolucionPendiente = "Devolución-Pendiente";
        String colorEstadoCesion = "rgba(255, 241, 184, 1)";
        boolean encontrado = false;

        while (!encontrado) {
            for (int i = 1; i <= 10; i += 1) {
                if (echeqPage.getDatoBusquedaEcheqGrilla(String.valueOf(i), "13").isDisplayed()) {
                    scrollTo(echeqPage.getDatoBusquedaEcheqGrilla(String.valueOf(i), "13").getElementBy());
                    Settings.echeqType = echeqPage.getDatoBusquedaEcheqGrilla
                            (String.valueOf(i), "13").getText();
                    if (Settings.echeqType.equals(nombreDevolucionPendiente)) {
                        Settings.colorEstadoEcheq = echeqPage.getEstadoEcheqPorFila().getCssValue("background-color");
                        encontrado = true;
                        break;
                    }
                } else {
                    break;
                }

                if (encontrado) break;
                if (transferencias.getclickSiguientePaginaGrilla().isDisplayed()) {

                    scrollTo(transferencias.getclickSiguientePaginaGrilla().getElementBy());
                    transferencias.getclickSiguientePaginaGrilla().click();

                    UtilsGP.fixedWait(2000);
                } else {
                    break;
                }
            }
            Assert.assertEquals("Estado Devolución Pendiente sin color " +
                    "amarillo correspondiente", colorEstadoCesion, Settings.colorEstadoEcheq);
        }
    }
}



