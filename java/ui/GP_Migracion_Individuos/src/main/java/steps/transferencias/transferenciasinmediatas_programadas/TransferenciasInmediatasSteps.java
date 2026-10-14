package steps.transferencias.transferenciasinmediatas_programadas;

import api.TransferenciasAPI;
import global.practia.jemeinsamstark.web.core.WebDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.codehaus.jettison.json.JSONException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.transferencias.transferenciasinmediatas.TransferenciasInmediatasPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.transferencias.agenda_nuevo_destinatario.AgendaNuevoDestinatarioSteps;
import utils.MetodosAPI;
import utils.Settings;
import utils.UtilsGP;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import java.awt.*;

public class TransferenciasInmediatasSteps extends BotonesGenericosSteps {

    private TransferenciasInmediatasPage transferenciasInmediatasPage =
            new TransferenciasInmediatasPage();
    WebDriverWait wait = new WebDriverWait(WebDriver.getInstance(), 30);
    private String guardarNumeroMontoCuenta = null;
    TransferenciasAPI transferenciasAPI = new TransferenciasAPI();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();

    private MetodosAPI metodosAPI = new MetodosAPI();

    private UtilsGP utilsGP = new UtilsGP();
    public static int montoOriginal = 0;
    public static String cuentaOrigenG = "";
    public static int montoTransferido = 0;
    String saldoInicialal;
    String saldoFinal;
    String transferenciaMasAntigua;
    String ultimaTransferencia;

    AgendaNuevoDestinatarioSteps agendaNuevoDestinatarioSteps = new AgendaNuevoDestinatarioSteps();

    @Step("Click Desplegable Tarjeta Debito")
    public void clickTarjetaDeDebito() {
        transferenciasInmediatasPage.getTarjetaDebitoDesplegable().click();
    }
    @Step("Click Boton Programar Para Otro Dia")
    public void clickProgramarOtroDia() {
        transferenciasInmediatasPage.getProgramarOtroDia().click();
    }
    @Step("Validar listado transferencias")
    public void validarListadoTransferencias() {
        transferenciasInmediatasPage.getTablaTransferencias().isDisplayed();
    }

    @Step("Click Listado de plantillas")
    public void clickListadoDePlantillas() {
        transferenciasInmediatasPage.getListadoDePlantillas().click();
    }

    @Step("Click desplegable Tipo de transferencia")
    public void clickTipoDeTransferencia() {
        transferenciasInmediatasPage.getTipoDeTransferenciaDesplegable().click();
    }

    @Step("Seleccionar tipo de transferecia")
    public void seleccionarTipoTransferencia(String tipoTransferencia) throws AWTException, InterruptedException {


        switch (tipoTransferencia) {
            case "Cuenta Propia":
                transferenciasInmediatasPage.getTipoDeTransferencia("A una cuenta propia").click();
                break;
            case "Terceros":
                UtilsGP.fixedWait(5000);
                transferenciasInmediatasPage.getTipoDeTransferencia("A un CBU, CVU o Alias").click();
                break;
            case "Contacto Agendado":
                scrollTo(By.xpath("(//div[@class='slick-list'])[2]//div[@class='slick-slide slick-active'][1]"));
                transferenciasInmediatasPage.getTipoDeTransferencia("A un contacto agendado").isDisplayed();
                transferenciasInmediatasPage.getTipoDeTransferencia("A un contacto agendado").click();
                break;
        }
    }

    @Step("Seleccionar Primer contacto agendado")
    public void seleccionarPrimerContactoAgendado() {
        transferenciasInmediatasPage.getPrimerContactoAgendado().click();
    }

    @Step("Click desplegable Cuenta origen")
    public void clickCuentaOrigen() {
        transferenciasInmediatasPage.getCuentaOrigenDesplegable().click();
    }

    @Step("Obtener Saldo Cuenta Origen")
    public void obtenerSaldoCtaOrigen() {
        transferenciasInmediatasPage.
                getCuentaOrigenDesplegable().click();
        transferenciasInmediatasPage.
                getCuentaOrigenDesplegableSaldo1().getText();
    }

    @Step("Click desplegable Cuenta destino")
    public void clickCuentaDestino() throws InterruptedException {
        scrollTo(By.id("targetAccount"));
        transferenciasInmediatasPage.getCuentaDestinoDesplegable().isDisplayed();
        transferenciasInmediatasPage.getCuentaDestinoDesplegable().click();
    }

    @Step("Seleccionar Cuenta origen {cuentaOrigen}")
    public void seleccionarCuentaOrigen(String cuentaOrigen) throws AWTException, JSONException, InterruptedException {

        Assert.assertTrue("NO SE ENCONTRO BOTON TRANSFERIR DESDE",transferenciasInmediatasPage.getCuentaOrigenDesplegable().isDisplayed());
        transferenciasInmediatasPage.getCuentaOrigenDesplegable().click();

        keyDownToText(cuentaOrigen);

        montoOriginal = transferenciasAPI.verificarMontoAPI(cuentaOrigen);

        cuentaOrigenG = cuentaOrigen;

        System.out.println("NombrecuentaOrigenG: "+cuentaOrigenG+"\nmontoOriginalCapturado: "+montoOriginal);
    }
    @Step("Seleccionar Tarjeta a Operar")
    public void seleecionarTarjeaAOperar(String tarjeta) throws AWTException {
        if(utilsGP.estaPresente(transferenciasInmediatasPage.getTarjetaAOperar(),2)){
            transferenciasInmediatasPage.getTarjetaAOperar().click();
        }else {
            transferenciasInmediatasPage.getTarjetaAOperarForm().click();
        }
        keyDownToText(tarjeta);
    }

    @Step("Seleccionar Cuenta destino de cuentas propias")
    public void seleccionarCuentaDestinoCuentasPropias(String cuentaDestino) throws InterruptedException {
        try {
            if(transferenciasInmediatasPage.getCardAgendaDestinatarios().isDisplayed()) {
                transferenciasInmediatasPage.getCardAgendaDestinatarios().click();
                transferenciasInmediatasPage.getCuentaDestinoPropia().isDisplayed();
                transferenciasInmediatasPage.getCuentaDestinoPropia().click();
                transferenciasInmediatasPage.getCuentaDestinoPropia().sendKeys(cuentaDestino);
                transferenciasInmediatasPage.getBotonTrasnferir().click();
            }
        } catch (TimeoutException e) {
            transferenciasInmediatasPage.getIconoSiguiente("1").click();
            transferenciasInmediatasPage.getCuentaDestino(cuentaDestino).isDisplayed();
            transferenciasInmediatasPage.getCuentaDestino(cuentaDestino).click();
        }
    }

    @Step("Seleccionar cuenta destino")
    public void seleccionarCuentaDestino(String cuentaDestino) throws AWTException, InterruptedException {
        scrollTo(By.id("targetAccount"));
        transferenciasInmediatasPage.getCuentaDestinoDesplegable().isDisplayed();
        transferenciasInmediatasPage.getCuentaDestinoDesplegable().click();
        keyDownToText(cuentaDestino);
    }

    @Step("Click desplegable Concepto")
    public void clickConcepto() {
        scrollTo(By.id("concept"));
        transferenciasInmediatasPage.getConceptoDesplegable().click();
    }

    @Step("Seleccionar Concepto")
    public void seleccionarConcepto(String concepto) throws AWTException {

        UtilsGP.fixedWait(5000);
        scrollTo(By.id("concept"));
        transferenciasInmediatasPage.getConceptoDesplegable().click();
        keyDownToText(concepto);
    }

    @Step("Se ingresa descripcion {descripcion}")
    public void setDescripcion(String descripcion) {
        transferenciasInmediatasPage.getInputReferencia().sendKeys(descripcion);
    }

    @Step("Validacion boton Transferir Ahora deshabilitado")
    public void validarBotonTransferirDeshabilitado() {
        Assert.assertFalse("El Boton Transferir Ahora se encuentra clickeable", transferenciasInmediatasPage.getBotonTransferirAhora().isEnabled());
        ;
    }

    @Step("Click en Plantilla {nombrePlantilla}")
    public void clickPlantillaPorNombre(String nombrePlantilla) {
        scrollTo(By.xpath(String.format("//div[contains(text(),'%s')]", nombrePlantilla)));
        transferenciasInmediatasPage.getPlantillaPorNombre(nombrePlantilla).click();
    }

    @Step("Click desplegable Transferencia programada para")
    public void clickTransferenciaProgramadaPara() {
        scrollTo(By.id("scheduledFor"));
        transferenciasInmediatasPage.getProgramadaParaDesplegable().click();
    }

    @Step("Click desplegable En la fecha")
    public void clickEnLaFechaCalendario() {
        scrollTo(By.id("customImputID"));
        transferenciasInmediatasPage.getEnLaFechaCalendario().click();
    }
    @Step("Click Fecha Especifica")
    public void clickEnFechaEspecifica(){
        transferenciasInmediatasPage.getEnUnaFechaEspeficia().click();
    }
    @Step("Click en boton Transferir Ahora")
    public void clickBotonTransferirAhora() {
        transferenciasInmediatasPage.getBotonTransferirAhora().click();

    }

    @Step("Click boton Confirmar")
    public void clickBotonConfimar() {
        scrollTo(By.xpath("//button[@aria-label='Confirmar']"));
        transferenciasInmediatasPage.getBotonConfirmar().click();
    }

    @Step("Validar mensaje de campos obligatorios")
    public void validarMsjeCampoObligatorio() {
        scrollTo(By.xpath("//div[text()= 'Este campo es obligatorio']"));
        transferenciasInmediatasPage.getMensajeCampoObligatorio().isDisplayed();
    }

    @Step("Validar mensaje emergente referencia no puede ser null o vacía")
    public void validarMsjeReferenciaVacia() {
        transferenciasInmediatasPage.getNotificacionReferenciaVacia().isDisplayed();
        String mensaje = transferenciasInmediatasPage.getNotificacionReferenciaVacia().getText();
        Assert.assertEquals("El valor de la referencia no puede ser null ni vacío.", mensaje);
    }

    @Step("Click en agenda de destinatarios")
    public void clickAgendaDestinatario() {
        UtilsGP.fixedWait(4000);
        transferenciasInmediatasPage.getCardAgendaDestinatarios().click();
    }

    @Step("Seleccionar misma cuenta propia que la seleccionada segun monto")
    public void seleccionarMismaCuenta() throws InterruptedException {
        guardarNumeroMontoCuenta = transferenciasInmediatasPage.getMontoCuentaDesde().getText();
        seleccionarCuentaDestinoCuentasPropias(guardarNumeroMontoCuenta);
        Assert.assertTrue(transferenciasInmediatasPage.getMensajeNotificacion().isDisplayed());
    }

    @Step("Completar Alias CBU")
    public void completarInputAliasCbu(String aliasCbu){
        transferenciasInmediatasPage.getInputAliasCbu().sendKeys(aliasCbu);
    }

    @Step("Click en boton Son correctos")
    public void botonSonCorrectos() {
        scrollTo(By.xpath("//button[@aria-label='Son correctos']"));
        transferenciasInmediatasPage.getButtonSonCorrectos().click();
    }

    @Step("Click mis transferencias")
    public void misTransferencias() {

        Assert.assertTrue("NO SE ENCONTRO EL BOTON MIS TRASNFERENCIAS O CAMBIO DE XPATH",
                transferenciasInmediatasPage.getMisTransferencias().isDisplayed());
        UtilsGP.fixedWait(5000);
        transferenciasInmediatasPage.getMisTransferencias().click();
    }

    @Step("Desplegable cuenta")
    public void desplegableCuenta() {
        transferenciasInmediatasPage.getDesplegableCuenta().click();
    }

    @Step("Click en todos los movimientos")
    public void todosLosMovimientos() {
        transferenciasInmediatasPage.getTodosLosMovimientos().isDisplayed();
        transferenciasInmediatasPage.getTodosLosMovimientos().click();
    }

    @Step("Validar tabla de historial")
    public void validarHistorial() {
        Assert.assertTrue("No se registran transferencias", transferenciasInmediatasPage.getTablaHistorial().isDisplayed());
    }

    @Step("Seleccionar transferencia programada para")
    public void transferenciaProgramadaPara() throws AWTException {
        transferenciasInmediatasPage.getTransferenciaProgramadaPara().click();
    }
    @Step("Selecionar Cuando queres realizar la transferencia")
    public void seleccionFechaFutura(String fechaTransferencia){
        switch (fechaTransferencia)
        {
            case "Especifica":
                transferenciasInmediatasPage.getSeleccionarFechaFutura("En una fecha específica").isDisplayed();
                transferenciasInmediatasPage.getSeleccionarFechaFutura("En una fecha específica").click();
                break;
            case "Semana":
                transferenciasInmediatasPage.getSeleccionarFechaFutura("Una vez por semana").isDisplayed();
                transferenciasInmediatasPage.getSeleccionarFechaFutura("Una vez por semana").click();
                break;
            case "Mes":
                transferenciasInmediatasPage.getSeleccionarFechaFutura("Una vez por mes").isDisplayed();
                transferenciasInmediatasPage.getSeleccionarFechaFutura("Una vez por mes").click();
                break;
        }
    }
    @Step("Transferir a un contacto")
    public void transferirAunContacto(String perteneceA){
        try {
                transferenciasInmediatasPage.getInputBuscarContacto().isDisplayed();
                transferenciasInmediatasPage.getInputBuscarContacto().sendKeys(perteneceA);
                if(transferenciasInmediatasPage.getBotonTrasnferir().isDisplayed()){
                    transferenciasInmediatasPage.getBotonTrasnferir().click();
                }else{
                    transferenciasInmediatasPage.getCuentaDestino(perteneceA).click();
                }

        } catch (TimeoutException e) {
            transferenciasInmediatasPage.getIconoSiguiente("1").click();
            transferenciasInmediatasPage.getCuentaDestino(perteneceA).isDisplayed();
            transferenciasInmediatasPage.getCuentaDestino(perteneceA).click();
        }
    }
    @Step("Verificar descuento en cuenta que realiza la transferencia API")
    public void verificarDescuentoCuentaOrigenTransferencia() throws JSONException {
        int montoActual = transferenciasAPI.verificarMontoAPI(cuentaOrigenG);
        System.out.println("Monto --- "+montoTransferido);
        System.out.println("Monto original: "+montoOriginal);
        System.out.println("Monto actual: " + montoActual +" Cuenta: "+cuentaOrigenG);

        int resultado = montoOriginal-montoTransferido;

        Assert.assertTrue("NO SE IMPACTO EL DESCUENTO EN LA CUENTA", resultado==montoActual);
    }

    @Step("Click en botón Cerrar")
    public void btnCerrar() {


        if (transferenciasInmediatasPage.getButtonCerrar().
                isDisplayed()) {
            transferenciasInmediatasPage.getButtonCerrar();
        }
    }

        @Step("Validar Número de Operación")
    public void txtNroDeOperacion() {
        transferenciasInmediatasPage.getNumeroDeOperacion().isDisplayed();
        }

    @Step("Validar que no se muestre el dato Descripción en el ticket")
    public void txtDescripcion(){
        Assert.assertFalse("Dato Desripción no visible",
                transferenciasInmediatasPage.getDescripcion().isDisplayed());
    }

    @Step("Obtener saldo inicial")
    public void obtenerSaldoInicial(){

//        transferenciasInmediatasPage.getCuentaOrigenDesplegable().click();
//        UtilsGP.fixedWait(3000);
        saldoInicialal = transferenciasInmediatasPage.
                getCuentaOrigenDesplegableSaldo1().getText();
        Allure.addAttachment("Saldo Inicial", saldoInicialal);
//        transferenciasInmediatasPage.getCuentaOrigenDesplegableSaldo1().click();
        UtilsGP.fixedWait(3000);
    }


    @Step("Seleccionar Cta Origen")
    public void seleccionarCtaOrigen(){

        UtilsGP.fixedWait(5000);
        transferenciasInmediatasPage.getCuentaOrigenDesplegable().click();
        transferenciasInmediatasPage.getCuentaOrigenDesplegableSaldo2().click();
    }



    @Step("Validar saldo actualizado")
    public void saldoActualizado(){

        UtilsGP.fixedWait(5000);
        transferenciasInmediatasPage.getCuentaOrigenDesplegable().click();
        saldoFinal = transferenciasInmediatasPage.
                getCuentaOrigenDesplegableSaldo1().getText();
        Allure.addAttachment("Saldo Anterior", saldoInicialal);
        Allure.addAttachment("Saldo Actualizado", saldoFinal);
        transferenciasInmediatasPage.getCuentaOrigenDesplegableSaldo1().click();
    }

    @Step("Obtener transferencia más antigua")
    public void obtenertransferenciaMasAntigua(){

        UtilsGP.fixedWait(5000);
        transferenciaMasAntigua = transferenciasInmediatasPage.
                getfechaPasada().getText();
        Allure.addAttachment("Transferencia más antigua", transferenciaMasAntigua);
    }

    @Step("Obtener última transferencia")
    public void obtenerUltimaTransferencia(){

        ultimaTransferencia = transferenciasInmediatasPage.
                getfechaActual().getText();
        Allure.addAttachment("Última transferencia", ultimaTransferencia);
    }

    @Step("Hacer click en botón Filtrar")
    public void clickbtnFiltrar(){
        transferenciasInmediatasPage.getFiltrar().isDisplayed();
        transferenciasInmediatasPage.getFiltrar().click();
    }


    @Step("Validar cantidad de registros por página")
    public void getregistrosPorPaginaGrilla(){

        List<WebElement> files = transferenciasInmediatasPage.getcantidadDeRegistrosGrilla();
        int cantidad = files.size();
        Allure.addAttachment("Cantidad de Registros " +
                "por página", String.valueOf(cantidad));
        Assert.assertFalse("Cantidad de Registros superiores a diez",
                cantidad > 10);

        scrollTo(By.xpath
                ("//p[contains(text(), 'Pag ' )]"));
        String Paginador = transferenciasInmediatasPage.validarPaginador().getText();
        Allure.addAttachment("Paginador", Paginador);
        Assert.assertTrue("Paginador No visible",
                transferenciasInmediatasPage.validarPaginador().isDisplayed());
    }

    @Step("Validar Movimientos con antiguedad de dos meses")
    public void buscarMovimientosConAntiguedadDeDosMeses(){

        String fechaDosMeses = "";
        boolean prueba = true;
        LocalDateTime fechaPrueba = LocalDateTime.now();
        LocalDateTime dosmesesValue = fechaPrueba.minusMonths(2);
        String fechaInicioMes = dosmesesValue.format(DateTimeFormatter.ofPattern("MM"));


        while (prueba){
            if (!utilsGP.estaPresente(transferenciasInmediatasPage.
                    getFechaMovimiento(fechaInicioMes).getElementBy(),2)) {
                scrollTo(transferenciasInmediatasPage.getclickSiguientePaginaGrilla().getElementBy());
                transferenciasInmediatasPage.getclickSiguientePaginaGrilla().click();
            }
            else {
                scrollTo(transferenciasInmediatasPage.getFechaMovimiento(fechaInicioMes).getElementBy());
                fechaDosMeses = String.valueOf(transferenciasInmediatasPage.getFechaMovimiento(fechaInicioMes).getText());
                prueba = false;
            }
        }
        Allure.addAttachment("Fecha de dos meses de Antiguedad", fechaDosMeses);
        Assert.assertTrue("No hay movimientos de dos meses de Antiguedad",transferenciasInmediatasPage
                .getFechaMovimiento(fechaInicioMes).isDisplayed());
    }

    @Step("Validar limpiar filtros de la búsqueda")
    public void validarBorrarFiltros(){

        String filtroInicialFechaDesde = "0";
        String filtroInicialFechaHasta = "0";
        String filtroCambiadoFechaDesde = "0";
        String filtroCambiadoFechaHasta = "0";
        String filtroFinalFechaDesde = "0";
        String filtroFinalFechaHasta = "0";



        UtilsGP.fixedWait(3000);

        transferenciasInmediatasPage.getFiltrar().click();

        UtilsGP.fixedWait(3000);

        filtroInicialFechaDesde = transferenciasInmediatasPage.getfechaPasada().getText();
        filtroInicialFechaHasta = transferenciasInmediatasPage.getfechaActual().getText();

        Allure.addAttachment("Fecha Inicial Filtro Desde",filtroInicialFechaDesde);
        Allure.addAttachment("Fecha Inicial Filtro Hasta",filtroInicialFechaHasta);

        transferenciasInmediatasPage.getfechaPasada().click();
        UtilsGP.fixedWait(3000);
        transferenciasInmediatasPage.getdatePicker().click();
        filtroCambiadoFechaDesde = transferenciasInmediatasPage.getfechaPasada().getText();

        transferenciasInmediatasPage.getfechaActual().click();
        UtilsGP.fixedWait(3000);
        transferenciasInmediatasPage.getdatePicker().click();
        filtroCambiadoFechaHasta = transferenciasInmediatasPage.getfechaActual().getText();

        Allure.addAttachment("Fecha Cambiada Filtro Desde",filtroCambiadoFechaDesde);
        Allure.addAttachment("Fecha Cambiada Filtro Hasta",filtroCambiadoFechaHasta);

        transferenciasInmediatasPage.getBorrarFiltros().click();
        UtilsGP.fixedWait(3000);
        transferenciasInmediatasPage.getFiltrar().click();
        UtilsGP.fixedWait(3000);
        filtroFinalFechaDesde = transferenciasInmediatasPage.getfechaPasada().getText();
        filtroFinalFechaHasta = transferenciasInmediatasPage.getfechaActual().getText();

        Allure.addAttachment("Fecha Final Reseteada Filtro Desde",filtroFinalFechaDesde);
        Allure.addAttachment("Fecha Final Reseteada Filtro Hasta",filtroFinalFechaHasta);

        Assert.assertEquals("La fecha del filtro reseteado 'Desde' no coincide" +
                "con la fecha Inicial",filtroInicialFechaDesde,filtroFinalFechaDesde);
        Assert.assertEquals("La fecha del filtro reseteado 'Hasta' no coincide" +
                "con la fecha Inicial",filtroInicialFechaHasta,filtroFinalFechaHasta);

    }

    @Step("Guardar Nro de Operación")
    public void guardarNroDeOperacion() {

        Settings.nroDeOperacion = transferenciasInmediatasPage.getNumeroDeOperacion().getText();
    }

    @Step("Click en -Transferir a este contacto-")
    public void clickBtnTransferiAEsteContacto(String texto) {

        scrollTo(By.xpath("//span[contains(text(), '"+ texto +"')]"));
        botonesGenericosPage.getSeleccionarServicio2(texto).click();
    }

    @Step("Click Botón Desplegable -Más Opciones-")
    public void clickBtnDesplegableTransferenciasProgramadas(String nombre) {
//        scrollTo(By.xpath("//span[contains(text(), '"+ nombre +"')]"));
        botonesGenericosPage.getlistaDesplegableGenerico2(nombre).click();

    }

    @Step("Click Botón Editar")
    public void clickBtnEditar(String nombre) {
//        scrollTo(By.xpath("//span[contains(text(), '"+ nombre +"')]"));
        botonesGenericosPage.getlistaDesplegableGenerico2(nombre).click();

    }




}
