package steps.cuentas;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.cuentas.CuentasPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.login.LoginSteps;
import utils.UtilsGP;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class CuentasSteps extends BotonesGenericosSteps {

    private CuentasPage cuentas = new CuentasPage();
    private final BotonesGenericosPage botonesGenericos = new BotonesGenericosPage();
    LoginSteps loginSteps= new LoginSteps();
    private double saldoInicial = 0;
    private int saldoInicialInt = 0;
    private double saldoAcutalizado =0;
    private int saldoActualizadolInt = 0;
    private String importeTotal = null;
    private BigDecimal saldoInicialBigDecimal;
    private BigDecimal saldoActualizadoBigDecimal;

    @Step("Click en Solicitar Nueva Cuenta")
    public void clickSolicitarNuevaCuenta(){
        Assert.assertTrue("Solicitar nueva cuenta no se encuentra", cuentas.getSolicitarNuevaCuenta().isDisplayed());
        cuentas.getSolicitarNuevaCuenta().click();
    }
    @Step("Click en Órdenes de Extracción")
    public void clickOrdenesDeExtraccion(){
        cuentas.getOrdenesDeExtraccion().isDisplayed();
        cuentas.getOrdenesDeExtraccion().click();
    }
    @Step("Click en Tenencias al 31/12")
    public void clickTenencias(){
        cuentas.getTenencia3112().click();
    }
    @Step("Click en Agenda de Destinatarios")
    public void clickAgendaDeDestinararios(){
        cuentas.getAgendaDeDestinatarios().click();
    }
    @Step("Click en Listado de Transacciones")
    public void clickListadoDeTransacciones(){
        cuentas.getListadoDeTransacciones().click();
    }
    @Step("Click en Transferencias")
    public void clickTransferencias(){
        cuentas.getTransferencias().click();
    }
    @Step("Click en Transferencias Programadas")
    public void clickTransferenciasProgramadas(){
        cuentas.getTransferenciasProgramadas().click();
    }
    @Step("Click en Transferencias de Importe Superior")
    public void clickTransferenciasDeImporteSuperior(){
        cuentas.getTransferenciasDeImporteSuperior().click();
    }
    @Step("Click en Limite de Transferencias")
    public void clickLimiteDeTransferencia(){
        cuentas.getLimiteDeTransferencia().click();
    }
    @Step("Click en Transferencias Realizadas")
    public void clickTransferenciasRealizadas(){
        cuentas.getTransferenciasRealizadas().click();
    }
    @Step("Click en ECHEQ")
    public void clickEcheq(){
        cuentas.getEcheq().click();
    }
    @Step("Click en Renta Financiera")
    public void clickRentaFinanciera(){
        cuentas.getRentaFinanciera().click();
    }
    @Step("Click en Ver mas 1er cuenta")
    public void clickVerMas1erCuenta(){
        Assert.assertTrue("NO SE ENCUENTRA LA CUENTA : ",cuentas.getVerMas1erCuenta().isDisplayed());
        cuentas.getVerMas1erCuenta().click();
    }
    @Step("Click en Ver Mas de cuenta {nroCuenta}") //Ej: "CA $ 000003555304" // o los ultimos 4 ultimos digitos 2210
    public void clickVerMasXnroCuenta(String nroCuenta){
        Assert.assertTrue("NO SE ENCUENTRA EL BOTON", cuentas.getVerMasXnroCuenta(nroCuenta).isDisplayed());
        cuentas.getVerMasXnroCuenta(nroCuenta).isEnabled();
        cuentas.getVerMasXnroCuenta(nroCuenta).click();
    }
    @Step("Se valida que no existe mas de 1 Cuenta en el usuario")
    public void validarClienteConUnaCuenta() {
        Assert.assertTrue("Usuario con mas de una cuenta",!cuentas.getVerMasPorPosicion().isDisplayed());
    }
    @Step("Click Solicitud de adelanto de Sueldo")
    public void clickSolicitudAdelantoDeSueldo(){
        cuentas.getAdelantoDeSueldo().click();
        loginSteps.clickSalirPrestamo();
    }
    @Step("Set Cuanto necesitas adelando de sueldo")
    public void setCuantoNecesitas(String monto){
        cuentas.getCuantoNecesitas().sendKeys(monto);
    }
    @Step("Click Resumen de Contrato")
    public void clickResumenDeContrato(){
        cuentas.getResumenDeContrato().click();
    }
    @Step("Click Mis Adelantos")
    public void clickMisAdelantos(){
        cuentas.getMisAdelantos().click();
    }
    @Step("Validar pantalla completa de Adelantos de sueldo")
    public void validacionPantallaSolicitudAH() {
        List<String> campos = Arrays.asList(
                "Solicitud de adelanto",
                "solicitar entre $",
                "El importe solicitado más el interés se devolverá en la próxima acreditación de sueldo.",
                "El monto de interés se calculará según la cantidad de días que transcurran desde que solicites el adelanto hasta que cobres tu próximo sueldo.",
                "Resumen de contrato",
                "Mis adelantos");
        for (String campo : campos) {
            String campo1 = new String(campo.getBytes(),
                    StandardCharsets.UTF_8);
            scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]", campo1)));
            Assert.assertTrue("El campo '" + campo1 + "' no esta visible", cuentas.textos(campo1).isDisplayed());
            Assert.assertTrue("Boton Continuar no se encuentra disponible", botonesGenericos.getContinuar().isEnabled());
        }
        System.out.println("Titulos validados con exito");
    }
    @Step("Validar pantalla completa 'Confirmacion de solicitud'")
    public void validacionCamposConfirmacion() {
        List<String> campos = Arrays.asList(
                "Confirmación",
                "Número de cuenta",
                "Monto total del préstamo",
                "Sellados",
                "Dinero a acreditar en la cuenta",
                "T.E.A",
                "T.N.A",
                "CFT T.E.A",
                "El importe solicitado más el interés se devolverá en la próxima acreditación de sueldo.",
                "El monto de interés se calculará según la cantidad de días que transcurran desde que solicites el adelanto hasta que cobres tu próximo sueldo.",
                "Acepta términos y condiciones",
                "Información sobre acreditación",
                "La acreditación se verá reflejada en el transcurso del día.",
                "Confirmar");
        for (String campo : campos) {
            scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]", campo)));
            Assert.assertTrue("El campo '" + campo + "' no esta visible", cuentas.textos(campo).isDisplayed());
            Assert.assertTrue("Boton confirmar no se encuentra disponible", botonesGenericos.getConfirmar().isEnabled());
        }
        System.out.println("Titulos validados con exito");
    }
    @Step("Validar Card Mis adelantos")
    public void validacionCardMisAdelantos() {
        List<String> campos = Arrays.asList(
                "Mis adelantos",
                "Cuenta",
                "Tasas",
                "$",
                "TNA 0,01% TEA 25%");
        for (String campo : campos) {
            String campo1 = new String(campo.getBytes(),
                    StandardCharsets.UTF_8);
            scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]", campo1)));
            Assert.assertTrue("El campo '" + campo1 + "' no esta visible", cuentas.textos(campo1).isDisplayed());
        }
        System.out.println("Titulos validados con exito");
    }
    @Step("Campo requerido TyC")
    public void validarCamposRequeridos() {
        Assert.assertTrue("Campos requeridos no se muestan", cuentas.getCampoRequeridoTyC().isDisplayed());
    }

    @Step("Validar campos requeridos {campos}")
    public void validarCamposRequeridos(String campos) throws UnsupportedEncodingException {
        String campo = new String(campos.getBytes(),
                StandardCharsets.UTF_8);
        Assert.assertTrue("Titulo no visible", cuentas.getCampoRequerido(campo).isDisplayed());
    }
    @Step("Validar alert monto permitido")
    public void validarAlertMontoPermitido() {
        String montosPermitidos = cuentas.getMontoPermitido().getText();
        System.out.println("Alert - Monto invalido - " + montosPermitidos);
        Assert.assertTrue("Alert de Monto permitidos no esta disponible", cuentas.getMontoPermitido().isDisplayed());
    }
    @Step("Click cuentas asociadas")
    public void clickCuentasAsociadas() {
        cuentas.getCuentasAsociadas().click();

    }
    @Step("Click Acceso directo Adelanto de Sueldo")
    public void clickAccesoDirectoAdelantoSueldo() {
        scrollTo(By.xpath("//p[contains(text(),'Adelanto de sueldo')]"));
        cuentas.getAccesoDirectoAdelantoSueldo().click();
        loginSteps.clickSalirPrestamo();
    }
    @Step("Click Card Adelanto de Sueldo")
    public void clickCardAdelantoSueldo() {
        cuentas.getCardAdelantoSueldo().click();
    }
    @Step("Seleccionar desplegable cuenta origen si es visible")
    public void seleccionarDesplegableCuentaOrigen() {
        if(cuentas.getDesplegableCuentaOrigen().isDisplayed()){
            cuentas.getDesplegableCuentaOrigen().click();
            keyDownXParametrosGenerico(0,"");
        }
        else {
            System.out.println("El desplegable de cuenta origen no está disponible");
        }
    }
    @Step("Click Check tyc")
    public void clickCheckTyC() {
        cuentas.getCheckTyC().click();

    }

    @Step("Obtener el saldo de la cuenta actual")
    public double capturarSaldoInicialEnCuenta(String cuenta){
        String saldoInicial1 = "";
        scrollTo(By.xpath(String.format("//span[contains(text(),'%s')]/../../following-sibling::div//span[@aria-label='pesos']/..",cuenta)));
        saldoInicial1 = botonesGenericos.getMontoCuenta(cuenta).getText().split(" ")[1].replace(".","").replace(",",".");
        saldoInicialBigDecimal = new BigDecimal(saldoInicial1);
        Assert.assertFalse("LA CUENTA NO POSEE SALDO O EL USUARIO NO POSEE CUENTA", saldoInicialBigDecimal.compareTo(BigDecimal.ZERO)==0);
        System.out.println("SALDO DE LA CUENTA : " + saldoInicialBigDecimal);
        return saldoInicial;
    }
    @Step("Obtener la parte entera del saldo en cuenta")
    public int capturarSaldoInicialEnCuentaInt(String cuenta){
        scrollTo(By.xpath(String.format("//span[contains(text(),'%s')]/../../following-sibling::div//span[@aria-label='pesos']/..",cuenta)));
        saldoInicialInt = Double.valueOf(botonesGenericos.getMontoCuenta(cuenta).getText().split(" ")[1].replace(".","").replace(",",".")).intValue();
        Assert.assertFalse("LA CUENTA NO POSEE SALDO O EL USUARIO NO POSEE CUENTA",saldoInicialInt==0);
        System.out.println("SALDO DE LA CUENTA : " + saldoInicialInt);
        return  saldoInicialInt;
    }

    @Step("Obetner el saldo de la cuenta Actualiado Post proceso")
    public double capturarSaldoActualizadoEnCuenta(String cuenta){
        String saldoActualizado1;
        UtilsGP.fixedWait(3000);
        saldoActualizado1 = botonesGenericos.getMontoCuenta(cuenta).getText().split(" ")[1].replace(".","").replace(",",".");
        saldoActualizadoBigDecimal = new BigDecimal(saldoActualizado1);
        System.out.println("SALDO DE LA CUENTA : " + saldoActualizadoBigDecimal);
        return saldoAcutalizado;
    }
    @Step("Obtener importe total en FCI")
    public String capturaImporteTotal(String moneda){
     importeTotal = botonesGenericos.getImporteRescateTotal(moneda).getText().split(" ")[1].replace(".","").replace(",",".");
        System.out.println("IMPORTE TOTAL " +  importeTotal);
     return importeTotal;
    }
    @Step("Obtener importe total en FCI USD")
    public String capturaImporteTotalUSD(){
        importeTotal = botonesGenericos.getImporteRescateTotalUSD().getText().split(" ")[1].replace(".","").replace(",",".");
        System.out.println("IMPORTE TOTAL " +  importeTotal);
        return importeTotal;
    }

    @Step("Validar descuento de importe de cuenta en FCI")
    public void verificarDescuentoDeSaldoEnCuenta(String importe){
        BigDecimal resultado = saldoInicialBigDecimal.subtract(saldoActualizadoBigDecimal);
        Assert.assertTrue("No se realizo el descuento en la cuenta ",
                resultado.compareTo(new BigDecimal(importe))==0);
        System.out.println("SE REALIZO CORRECTAMENTE EL DESCUENTO DE SALDO EN LA CUENTA");
    }
    @Step("Validar incremento de importe de cuenta en FCI")
    public void verificarIncrementoDeSaldoEnCuenta(String importe){
        BigDecimal resultado;
        if(importe.equals("0")){
         resultado = saldoInicialBigDecimal.add(new BigDecimal(importeTotal));
            System.out.println("RESULTADO " + resultado);
         Assert.assertTrue("No se realizo el incremento en la cuenta ", saldoActualizadoBigDecimal.compareTo(resultado)==0);
        }else {
            resultado = saldoInicialBigDecimal.add(new BigDecimal(importe));
            System.out.println("RESULTADO " + resultado);
            Assert.assertTrue( "No se realizo el incremento en la cuenta ", saldoActualizadoBigDecimal.compareTo(resultado)==0);
        }
        System.out.println("SE REALIZO CORRECTAMENTE EL INCREMENTO DE SALDO EN LA CUENTA");
    }

    @Step("Obtener el saldo de la cuenta actual USD")
    public double capturarSaldoInicialEnCuentaUSD(String cuenta){
        String saldoInicial1;
        scrollTo(By.xpath(String.format("//span[contains(text(),'%s')]/../../following-sibling::div//span[@aria-label='dolares']/..",cuenta)));
        saldoInicial1 = botonesGenericos.getMontoCuentaUSD(cuenta).getText().split(" ")[1].replace(".","").replace(",",".");
        saldoInicialBigDecimal = new BigDecimal(saldoInicial1);
        Assert.assertFalse("LA CUENTA NO POSEE SALDO O EL USUARIO NO POSEE CUENTA", saldoInicialBigDecimal.compareTo(BigDecimal.ZERO)==0);
        System.out.println("SALDO DE LA CUENTA : " + saldoInicialBigDecimal);
        return saldoInicial;
    }

    @Step("Obetner el saldo de la cuenta Actualiado Post proceso")
    public double capturarSaldoActualizadoEnCuentaUSD(String cuenta){
        String saldoActualizado1;
        UtilsGP.fixedWait(3000);
        if(botonesGenericos.getMontoCuentaUSD(cuenta).isDisplayed()){
            saldoActualizado1 = botonesGenericos.getMontoCuentaUSD(cuenta).getText().split(" ")[1].replace(".","").replace(",",".");
        }else{
            botonesGenericos.getVerMas().click();
            saldoActualizado1 = botonesGenericos.getMontoCuentaUSD(cuenta).getText().split(" ")[1].replace(".","").replace(",",".");

        }
        saldoActualizadoBigDecimal = new BigDecimal(saldoActualizado1);
        System.out.println("SALDO DE LA CUENTA : " + saldoActualizadoBigDecimal);
        return saldoAcutalizado;
    }

    @Step("Click en Solicitar nueva Cuenta")
    public void clickBtnSolicitarNuevaCta(String nombre) {
        botonesGenericos.getlistaDesplegableGenerico2(nombre).click();
    }

    @Step("Validar botón deshabilitado")
    public void validarBtnDeshabilitado() {

        Assert.assertFalse("El botón está habilitado", botonesGenericos.getContinuar().isEnabled());
    }

//    @Step("Validar botón habilitado después de seleccionar Tipo de Cuenta y Moneda")
//    public void validarBtnHabilitadoDespuesDeSeleccionarTipoDeCtaYMoneda(String name1, String nombre1, String name2, String nombre2) {
//
//        cuentas.getListTipoDeCta(name1).click();
//        UtilsGP.fixedWait(3000);
//        botonesGenericos.getlistaDesplegableGenerico(nombre1).click();
//        UtilsGP.fixedWait(3000);
//        cuentas.getListTipoDeCta(name2).click();
//        UtilsGP.fixedWait(3000);
//        botonesGenericos.getlistaDesplegableGenerico(nombre2).click();
//
//        Assert.assertTrue("El botón sigue deshabilitado", botonesGenericos.getContinuar().isEnabled());
//    }


}
