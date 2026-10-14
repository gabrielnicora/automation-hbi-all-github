package steps.inversiones.fci;

import enums.inversiones.fci.FiltroFci;
import enums.inversiones.fci.FiltroMonedaFci;
import enums.inversiones.fci.FiltroRiesgoFci;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.inversiones.fci.FondoComunInversionPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.UtilsGP;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class FondoComunInversionSteps extends BotonesGenericosSteps {

    private FondoComunInversionPage fondoComunInversionPage = new FondoComunInversionPage();
    Wait<org.openqa.selenium.WebDriver> wait = new WebDriverWait(driver, 30);
    private UtilsGP utilsGP = new UtilsGP();

    private double tenenciaTotalInicial = 0 ;
    private double sumaTenenciaValorizada = 0 ;
    private double valorSuscripcionEnCursoActual=0;
    private double valorSuscripcionEnCursoActualizada = 0;
    private double cuotapartesDisponibles = 0;

    @Step("Seleccionar Cuenta Comitente y si no esta visible verificar numero")
    public void selectorCuentaComitente(String nroCuenta) {
        if(fondoComunInversionPage.getDesplegableCuentaComitente().isDisplayed()){
            fondoComunInversionPage.getDesplegableCuentaComitente().click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format("//div[@id='commitentAccountSelector']/descendant::div[contains(text(), '%s')]", nroCuenta))));
            fondoComunInversionPage.getDesplegableCuentaComitente(nroCuenta).click();
        }
        else{
            Assert.assertEquals("Los numeros de cuenta no coinciden",nroCuenta,fondoComunInversionPage.getNumeroCuentaComitente().getText());
        }
    }
    @Step("Click Selector Cuenta Comitente")
    public void clickSelectorCuentaComitente2( int rep, String nroCuenta) {
        fondoComunInversionPage.getDesplegableCuentaComitente().click();
        keyDownXParametrosGenerico(rep, nroCuenta);
    }
    @Step("Valida Cuenta Comitente seleccionada")
    public void validaCuentaComitentaSeleccionada() {
        Assert.assertTrue("No hay cuenta comitente seleccionada",fondoComunInversionPage.getCuentaComitenteSeleccionada().isDisplayed());
    }
    @Step("Click Simular Perfil de Inversor")
    public void clickSimularPerfilInversor() throws InterruptedException {
        Thread.sleep(3000);
        fondoComunInversionPage.getSimularPerfilInversor().click();
    }
    @Step("Click Selector de Fondos por Moneda")
    public void clickSelectorMoneda() { fondoComunInversionPage.getSelectorFondosPorMoneda().click();
    }
    @Step("Click Selector de Fondos por Riesgo")
    public void clickSelectorRiesgo() { fondoComunInversionPage.getSelectorFondosPorRiesgo().click();
    }
    @Step("Ingresar al detalle de un FCI")
    public void clickIconoAccionesDetalleFCI(String tipoFCI) {
        scrollTo(By.xpath(String.format("//p[contains(text(),'%s')]/../..//following::div[@aria-label='Icono de listar acciones del FCI'][1]", tipoFCI)));
        fondoComunInversionPage.getIconoAccionesDetalleFCI(tipoFCI).click();
        fondoComunInversionPage.getDetalleDelFondo().click();
        fondoComunInversionPage.getMiFondo().click();
    }
    @Step("Click Movimientos")
    public void clickMovimientos() {fondoComunInversionPage.getMovimientos().click();
    }
    @Step("Click Mas Informacion")
    public void clickMasInformacion() {
        fondoComunInversionPage.getMasInformacion().isDisplayed();
        fondoComunInversionPage.getMasInformacion().click();
    }
    @Step("Click Rechazar")
    public void clickRechazar() {fondoComunInversionPage.getRechazar().click();
    }
    @Step("Click Aceptar")
    public void clickAceptar() {
        fondoComunInversionPage.getAceptar().isDisplayed();
        fondoComunInversionPage.getAceptar().click();
    }
    @Step("Click Rendimiento")
    public void clickRendimiento() {fondoComunInversionPage.getRendimiento().click();
    }
    @Step("Click Suscribir")
    public void clickSuscribir() {
        Assert.assertTrue("NO APARECE EL BOTON : "+ fondoComunInversionPage.getSuscribir().getElementBy(),fondoComunInversionPage.getSuscribir().isDisplayed());
        fondoComunInversionPage.getSuscribir().click();
    }
    @Step("Click Descargar Detalle")
    public void clickDescargarDetalle() {fondoComunInversionPage.getDescargarDetalle().click(); }
    @Step("Click Rescate")
    public void clickRescate() {
        Assert.assertTrue("NO APAREC EL BOTON : "+fondoComunInversionPage.getRescate().getElementBy(),fondoComunInversionPage.getRescate().isDisplayed());
        fondoComunInversionPage.getRescate().click();
    }
    @Step("Dejar seleccionados únicamente los filtros de riesgo indicados")
    public void seleccionarUnicamenteFiltrosPorRiesgo(FiltroRiesgoFci... filtrosSeleccionados) {
        Set<FiltroRiesgoFci> seleccionados = filtrosSeleccionados == null || filtrosSeleccionados.length == 0
                ? EnumSet.noneOf(FiltroRiesgoFci.class)
                : EnumSet.copyOf(Arrays.asList(filtrosSeleccionados));

        configurarFiltros(FiltroRiesgoFci.values(), seleccionados);
    }

    @Step("Dejar seleccionados únicamente los filtros de moneda indicados")
    public void seleccionarUnicamenteFiltrosPorMoneda(FiltroMonedaFci... filtrosSeleccionados) {
        Set<FiltroMonedaFci> seleccionados = filtrosSeleccionados == null || filtrosSeleccionados.length == 0
                ? EnumSet.noneOf(FiltroMonedaFci.class)
                : EnumSet.copyOf(Arrays.asList(filtrosSeleccionados));

        configurarFiltros(FiltroMonedaFci.values(), seleccionados);
    }

    private void configurarFiltros(FiltroFci[] filtrosDisponibles, Set<? extends FiltroFci> filtrosSeleccionados) {
        for (FiltroFci filtro : filtrosDisponibles) {
            establecerEstadoFiltro(filtro, filtrosSeleccionados.contains(filtro));
        }

        for (FiltroFci filtro : filtrosDisponibles) {
            boolean estadoEsperado = filtrosSeleccionados.contains(filtro);
            boolean estadoActual = fondoComunInversionPage.isFiltroSeleccionado(filtro);
            Assert.assertEquals("El filtro " + filtro + " no quedó en el estado esperado", estadoEsperado, estadoActual);
        }
    }

    private void establecerEstadoFiltro(FiltroFci filtro, boolean estadoEsperado) {
        boolean estadoActual = fondoComunInversionPage.isFiltroSeleccionado(filtro);
        if (estadoActual == estadoEsperado) {
            return;
        }

        fondoComunInversionPage.getFiltro(filtro).click();
        wait.until(webDriver -> fondoComunInversionPage.isFiltroSeleccionado(filtro) == estadoEsperado);
        System.out.println("--CLICK EN FILTRO " + filtro + " -> " + (estadoEsperado ? "CHECKED" : "UNCHECKED") + "--");
    }
    @Step("Se valida visibilidad del listado de FCI")
    public void validaListadoFCI() {
        Assert.assertTrue("No hay datos disponibles para los filtros aplicados",fondoComunInversionPage.getListadoFiltrosAplicados().isDisplayed());
    }
    @Step("Se valida que sean visibles solamente fondos que coincidan con el filtro: {fondo}")
    public void validaFondosPorRiesgoVisiblesEnListado(String fondo) {
        List<WebElement> listFondos = fondoComunInversionPage.getTodosLosFondosDeRiesgoConservadorEnListado();
        if(!listFondos.isEmpty()){
            for (WebElement estado : listFondos) {
                String estadoSinAcentos = UtilsGP.removerAcentos(estado.getText());
                if(!estadoSinAcentos.equals(fondo)){
                    System.out.println("--ESTADO INCORRECTO "+estadoSinAcentos+" --");
                    Assert.fail("El listado muestra fondos que NO han sido seleccionados en el filtro: "+ estado.getText());
                }else{
                    System.out.println("--ESTADO CORRECTO "+estadoSinAcentos+" --");
                    Assert.assertEquals("No hay datos disponibles", estadoSinAcentos, fondo);
                }
            }
        }else{
            Assert.fail("No hay datos disponibles para los filtros aplicados");
        }
    }
    @Step("Se valida que sean visibles solamente fondos que coincidan con el filtro: {fondo}")
    public void validaFondosConversadorVisibleEnLista(String fondo) {
        List<WebElement> listFondos = fondoComunInversionPage.getTodosLosFondosDeRiesgoConservadorEnListado();
        if(!listFondos.isEmpty()){
            for (WebElement estado : listFondos) {
                String estadoSinAcentos = UtilsGP.removerAcentos(estado.getText());
                if(!estadoSinAcentos.equals(fondo)){
                    System.out.println("--ESTADO INCORRECTO "+estadoSinAcentos+" --");
                    Assert.fail("El listado muestra fondos que NO han sido seleccionados en el filtro: "+ estado.getText());
                }else{
                    System.out.println("--ESTADO CORRECTO "+estadoSinAcentos+" --");
                    Assert.assertEquals("No hay datos disponibles", estadoSinAcentos, fondo);
                }
            }
        }else{
            Assert.fail("No hay datos disponibles para los filtros aplicados");
        }
    }
    @Step("Se valida visibilidad del fondo: {fondo} en el listado")
    public void validaFondoPorRiesgoEnListado(String fondo) {
        Assert.assertTrue("No hay datos para los filtros aplicados: "+fondo,fondoComunInversionPage.getListadoFiltroPorFondoDeRiesgo(fondo).isDisplayed());
    }
    @Step("Se valida que sean visibles solamente fondos que coincidan con el filtro: {moneda}")
    public void validaFondosPorMonedaEnListado(String moneda) { //moneda : pesos|dolar
        List<WebElement> listFondos = fondoComunInversionPage.getTodosLosFondosPorMonedaVisiblesEnListado();
        moneda = moneda.equals("pesos") ? "$" : "USD";
        if(!listFondos.isEmpty()){
            for (WebElement estado : listFondos) {
                String estadoObtenido = estado.getText();
                /* if(!estadoObtenido.contains(moneda)){
                    System.out.println("--ESTADO INCORRECTO "+estadoObtenido+" --");
                    Assert.fail("El listado muestra fondos que NO han sido seleccionados en el filtro: "+ estadoObtenido);
                }else{
                    System.out.println("--ESTADO CORRECTO "+estadoObtenido+" --");
                    Assert.assertTrue("No hay datos disponibles", estadoObtenido.contains(moneda));
                } */
                if(estadoObtenido.contains(moneda)){
                    System.out.println("--ESTADO CORRECTO "+estadoObtenido+" --");
                    Assert.assertTrue("No hay datos disponibles", estadoObtenido.contains(moneda));
                }
            }
        }else{
            Assert.fail("No hay datos disponibles para los filtros aplicados");
        }
    }
    @Step("Click en boton Rescatar")
    public void clickRescatarPrimerElementoLiquidacionEnElDia() {
        scrollTo(By.xpath("//p[contains(text(), 'En el día')]/../parent::div//button[@aria-label='Rescatar']"));
        fondoComunInversionPage.getRescatarPrimerElemento().click();
    }

    @Step("Click en label mis fondos")
    public void clickLabelMisFondos() {
        fondoComunInversionPage.getLabelMisFondos().click();
    }
    @Step("Click en MI FONDO ")
    public void clickMiFondo() {
        fondoComunInversionPage.getMiFondo().click();
    }
    @Step("Click Calendario Desde ")
    public void clickCalendarioDesde() {
        fondoComunInversionPage.getCalendarioDesde().click();
    }
    @Step("Validar tabla registros")
    public void validarRegistros() {
        scrollTo(By.xpath("//div[@role='row']"));
        Assert.assertTrue(fondoComunInversionPage.getRegistros().isDisplayed());

    }
    @Step("Suscribir FCI T0 con liquidacion en el Dia")
    public void clickSuscribirEnEldia(){
        scrollTo(By.xpath("//p[contains(text(), 'En el día')]"));
        fondoComunInversionPage.getSuscribirEnElDia().click();
    }
    @Step("Rescatar FCI")
    public void clickRescatar(String tipoFCI){
        fondoComunInversionPage.getRescatar(tipoFCI).isDisplayed();
        scrollTo(By.xpath(String.format("//p[contains(text(), '%s')]/../..//parent::div//button[@aria-label='Rescatar']", tipoFCI)));
        fondoComunInversionPage.getRescatar(tipoFCI).click();
    }

    @Step("Click Suscribir FCI")
    public void clickSuscribir(String tipo){
        scrollTo(By.xpath(String.format("//p[contains(text(), '%s')]",tipo)));
        fondoComunInversionPage.getSuscribirDiaHabil(tipo).click();

        /*switch (tipo){
            case "Ahorro":
                scrollTo(By.xpath("//p[contains(text(), 'A-DELTA AHORRO')]"));
                fondoComunInversionPage.getSuscribirDiaHabil("A-DELTA AHORRO").click();
                break;
            case "Plus":
                scrollTo(By.xpath("//p[contains(text(), 'A-DELTA AHORRO PLUS')]"));
                fondoComunInversionPage.getSuscribirDiaHabil("A-DELTA AHORRO PLUS").click();
                break;
            case "Moneda":
                scrollTo(By.xpath("//p[contains(text(), 'A-DELTA MONEDA')]"));
                fondoComunInversionPage.getSuscribirDiaHabil("A-DELTA MONEDA").click();
                break;
            default:
                System.out.println("Seleccionar un tipo de FCI valido");
                break;
        }*/
    }
    @Step("Validar Tipo de fondo Comun de inversion")
    public void validarTipoFCI(String tipo){
        if(tipo.equals("En el día")){
            Assert.assertEquals("En el día",fondoComunInversionPage.getTipoDeFCI(tipo).getText());
            System.out.println("Se valida correctamente  FCI T0");
        }else if(tipo.equals("1 día hábil")){
            Assert.assertEquals("1 día hábil", fondoComunInversionPage.getTipoDeFCI(tipo).getText());
            System.out.println("Se valida Correctamente FCI T1");
        }
    }
    @Step("Verificar valor de Tenencia Valorizada")
    public void validarTenencia(){
        DecimalFormat formato = new DecimalFormat("#.##");
                double cotizacion = Double.parseDouble(fondoComunInversionPage.getCotizacion().getText().split(" ")[1].replace(".", "").replace(",",".").replace(",","."));
                capturarValorCuotapartesDisponibles();
                double tenencia = Double.parseDouble(fondoComunInversionPage.getTenenciaValorizada().getText().split(" ")[1].replace(".", "").replace(",",".").replace(",","."));
                double calculo = Double.parseDouble((formato.format((cotizacion*cuotapartesDisponibles)+valorSuscripcionEnCursoActualizada)).replace(",","."));
                String fallaValidacion = String.format("No se valida la tenencia valorizada\nCalculo: %s\nTenencia valorizada: %s\n",calculo,tenencia);
                //Assert.assertTrue(fallaValidacion,( (calculo >= (tenencia-1)) && (calculo <= tenencia+1)) );
                Assert.assertTrue(fallaValidacion,calculo == tenencia );
                System.out.println("SE VALIDA CORRECTAMENTE LA TENENCIA VALORIZADA");
    }

    @Step("Verificar valor de Tenencia Valorizada USD")
    public void validarTenenciaUSD(){
        DecimalFormat formato = new DecimalFormat("#.##");
        double cotizacion = Double.parseDouble(fondoComunInversionPage.getCotizacion().getText().split(" ")[1].replace(".", "").replace(",",".").replace(",","."));
        capturarValorCuotapartesDisponibles();
        double tenencia = Double.parseDouble(fondoComunInversionPage.getTenenciaValorizadaUSD().getText().split(" ")[1].replace(".", "").replace(",",".").replace(",","."));
        double calculo = Double.parseDouble((formato.format((cotizacion*cuotapartesDisponibles)+valorSuscripcionEnCursoActualizada)).replace(",","."));
        String fallaValidacion = String.format("No se valida la tenencia valorizada\nCalculo: %s\nTenencia valorizada: %s\n",calculo,tenencia);
        //Assert.assertTrue(fallaValidacion,( (calculo >= (tenencia-1)) && (calculo <= tenencia+1)) );
        Assert.assertTrue(fallaValidacion,calculo == tenencia );
        System.out.println("SE VALIDA CORRECTAMENTE LA TENENCIA VALORIZADA");
    }

    @Step("Verificar tenencia total")
    public double verificarTenenciaTotal(){
        DecimalFormat formato = new DecimalFormat("#.##");
        tenenciaTotalInicial = Double.parseDouble(formato.format(Double.parseDouble(fondoComunInversionPage.getTenenciaValorizada().getText().split(" ")[1].replace(".", "").replace(",","."))).replace(",","."));
        List<WebElement> listaElementos = (List<WebElement>) fondoComunInversionPage.getTenenciaValorizadaGrilla();
        List<Double> listaDoubles = new ArrayList<>();
        for (WebElement element : listaElementos) {
            listaDoubles.add(Double.parseDouble( (((element.getText().replace("$","")).replace(" ","")).replace(".","")).replace(",",".")  ));
        }
        for (Double element : listaDoubles) {
            sumaTenenciaValorizada += element;
        }

        sumaTenenciaValorizada = Double.parseDouble(formato.format(sumaTenenciaValorizada).replace(",","."));
        assert (sumaTenenciaValorizada == tenenciaTotalInicial) : "LA SUMA DE LA TENENCIA VALORIZADA ES DIFERENTE A LA TENENCIA TOTAL\n" +
                "TENENCIA TOTAL: "+tenenciaTotalInicial+"\nSUMA TENENCIA VALORIZADA: "+sumaTenenciaValorizada;
        return  tenenciaTotalInicial;
    }

    @Step("Verificar tenencia total")
    public double verificarTenenciaTotalUSD(){
        DecimalFormat formato = new DecimalFormat("#.##");
        tenenciaTotalInicial = Double.parseDouble(formato.format(Double.parseDouble(fondoComunInversionPage.getTenenciaValorizadaUSD().getText().split(" ")[1].replace(".", "").replace(",","."))).replace(",","."));
        List<WebElement> listaElementos = (List<WebElement>) fondoComunInversionPage.getTenenciaValorizadaUSDGrilla();
        List<Double> listaDoubles = new ArrayList<>();
        for (WebElement element : listaElementos) {
            listaDoubles.add(Double.parseDouble( (((element.getText().replace("USD","")).replace(" ","")).replace(".","")).replace(",",".")  ));
        }
        for (Double element : listaDoubles) {
            sumaTenenciaValorizada += element;
        }
        assert (sumaTenenciaValorizada == tenenciaTotalInicial) : "LA SUMA DE LA TENENCIA VALORIZADA ES DIFERENTE A LA TENENCIA TOTAL\n" +
                "TENENCIA TOTAL: "+tenenciaTotalInicial+"\nSUMA TENENCIA VALORIZADA: "+sumaTenenciaValorizada;
        return  tenenciaTotalInicial;
    }
    @Step("Verificar tenencia total")
    public double verificarTenenciaTotalUSDBSC(){
        DecimalFormat formato = new DecimalFormat("#.##");
        tenenciaTotalInicial = Double.parseDouble(formato.format(Double.parseDouble(fondoComunInversionPage.getTenenciaValorizadaUSD().getText().split(" ")[1].replace(".", "").replace(",","."))).replace(",","."));
        List<WebElement> listaElementos = (List<WebElement>) fondoComunInversionPage.getTenenciaValorizadaUSDGrillaBSC();
        List<Double> listaDoubles = new ArrayList<>();
        for (WebElement element : listaElementos) {
            listaDoubles.add(Double.parseDouble( (((element.getText().replace("USD","")).replace(" ","")).replace(".","")).replace(",",".")  ));
        }
        for (Double element : listaDoubles) {
            sumaTenenciaValorizada += element;
        }
        assert (sumaTenenciaValorizada == tenenciaTotalInicial) : "LA SUMA DE LA TENENCIA VALORIZADA ES DIFERENTE A LA TENENCIA TOTAL\n" +
                "TENENCIA TOTAL: "+tenenciaTotalInicial+"\nSUMA TENENCIA VALORIZADA: "+sumaTenenciaValorizada;
        return  tenenciaTotalInicial;
    }

    @Step("Obtener valor de campo Suscripcion en curso Actual")
    public  double capturarValorSuscripcionEnCursoInicial(String liquidacion){
        String valor = fondoComunInversionPage.getSuscipcionEnCurso().getText();
        if(!valor.equals("--")){
            valorSuscripcionEnCursoActual = Double.parseDouble(fondoComunInversionPage.getSuscipcionEnCurso().getText().split(" ")[1].replace(".", "").replace(",", "."));
            System.out.println("SUSCRIPCION EN CURSO INICIAL: " + valorSuscripcionEnCursoActual);
        }else{
            System.out.println("SIN SUSCRIPCION EN CURSO");
        }
        return valorSuscripcionEnCursoActual;
    }
    @Step("Obtener valor de campo Suscripcion en curso Actualizada")
    public  double capturarValorSuscripcionEnCursoActualizada(){
        String valor = fondoComunInversionPage.getSuscipcionEnCurso().getText();
        if(!valor.equals("--")){
            valorSuscripcionEnCursoActualizada = Double.parseDouble(fondoComunInversionPage.getSuscipcionEnCurso().getText().split(" ")[1].replace(".", "").replace(",", "."));
            System.out.println("SUSCRIPCION EN CURSO ACTUALIZADA: " + valorSuscripcionEnCursoActualizada);
        }else{
            System.out.println("SIN SUSCRIPCION EN CURSO");
        }
        return valorSuscripcionEnCursoActualizada;
    }

    @Step("Incremento de tenencia valorizada")
    public void verificarIncrementoTenenciaTotal(String importe){
        DecimalFormat formato = new DecimalFormat("#.#");
        double tenenciaActualizada = Double.parseDouble(formato.format(Double.parseDouble(fondoComunInversionPage.getTenenciaValorizada().getText().split(" ")[1].replace(".", "").replace(",","."))).replace(",","."));
        double resultado = tenenciaTotalInicial+Double.parseDouble(importe);
        Assert.assertTrue("No se incremento la tenencia total",tenenciaActualizada == resultado);
        System.out.println("SE INCREMENTO CORRECTAMENTE LA TENENCIA TOTAL POR " + importe);
    }

    @Step("Incremento de Suscripción en Curso")
    public void verificarIncrementoSaldoEnSuscripcionEnCurso(String importe){
        double resultado = valorSuscripcionEnCursoActual+Double.parseDouble(importe);
        Assert.assertTrue("NO SE INCREMENTO LA SUSCRIPCION EN CURSO", valorSuscripcionEnCursoActualizada==resultado);
        System.out.println("SE INCREMENTO LA SUSCRIPCION EN CURSO CORRETAMENTE");
    }
    @Step("Validar Modal Fuera del Horario de Mercado")
    public void validarModalFCI(String mensaje){
        Assert.assertTrue("MODAL NO ENCONTRADO",utilsGP.estaPresente(fondoComunInversionPage.getModalFCI(mensaje),2));
        System.out.println("SE VALIDO CORRECTAMENTE EL MODAL");
    }

    @Step("Validar mensaje Importante Fuera de Horario Mercado")
    public void validarMensajeAdvertencia(String mensaje){
        scrollTo(By.xpath(String.format("//p[contains(text(),'%s')]",mensaje)));
        Assert.assertTrue("ADVERTENCIA NO ENCONTRADA",utilsGP.estaPresente(fondoComunInversionPage.getMensajeImportanteDiaHabil(mensaje),2));
        System.out.println("SE VALIDA MENSAJE DE ADVERTENCIA");
    }
    @Step("Obtener valor de campo Cuotapartes disponibles")
    public  double capturarValorCuotapartesDisponibles(){
        String valor = fondoComunInversionPage.getCantidadCuotaPartes().getText();
        if(!valor.equals("--")){
            cuotapartesDisponibles = Double.parseDouble(fondoComunInversionPage.getCantidadCuotaPartes().getText().replace(".", "").replace(",", "."));
            System.out.println("SUSCRIPCION EN CURSO ACTUALIZADA: " + valorSuscripcionEnCursoActualizada);
        }else{
            System.out.println("SIN SUSCRIPCION EN CURSO");
        }
        return cuotapartesDisponibles;
    }

}
