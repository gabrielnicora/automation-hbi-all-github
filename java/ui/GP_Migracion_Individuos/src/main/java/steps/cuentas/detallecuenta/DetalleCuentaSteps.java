package steps.cuentas.detallecuenta;

import io.qameta.allure.Step;
//import org.graalvm.compiler.replacements.Log;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.cuentas.detallecuenta.DetalleCuentaPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.login.LoginSteps;
import utils.UtilsGP;

import static org.junit.Assert.assertFalse;

public class DetalleCuentaSteps<dimension> extends BotonesGenericosSteps {
    private DetalleCuentaPage detalleCuentaPage = new DetalleCuentaPage();
    private static BotonesGenericosPage botonesGenericos = new BotonesGenericosPage();

    @Step("Click en Detalle de movimiento")
    public void clickDetalleMovimientoPorNroFila(String nroFila) {
        scrollTo(By.xpath(String.format("//div[@aria-label='fila'][%s]/div[5]", nroFila)));
        detalleCuentaPage.getDetalleMovimientoPorFila(nroFila).click();
    }

    @Step("Click Todos los movimientos")
    public void clickTodosLosMovimientos() {
        detalleCuentaPage.getTodosLosMovimientos().click();
    }

    @Step("Click Mes anterior")
    public void clickMesAnterior() {
        detalleCuentaPage.getMesAnterior().click();
    }

    @Step("Click Mas filtros")
    public void clickMasFiltros() {
        Assert.assertTrue("No se aparece el boton MAS FIlTROS", detalleCuentaPage.getMasFiltros().isDisplayed());
        scrollTo(detalleCuentaPage.getMasFiltros().getElementBy());
        detalleCuentaPage.getMasFiltros().click();
    }

    @Step("Click Cuotificalo")
    public void clickCuotificalo() {
        detalleCuentaPage.getCuotificalo().click();
    }

    @Step("Click Modificar alias")
    public void clickModificarAlias() {
        detalleCuentaPage.getModificarAlias().click();
    }

    private String aliasGenerado;

    @Step("Setear alias")
    public void setModificarAlias(String alias) {
        aliasGenerado = UtilsGP.generarString(10, alias);
        detalleCuentaPage.setModificarAlias().sendKeys(Keys.chord(Keys.CONTROL, "a"), aliasGenerado);

    }

    @Step("Ver Alias")
    public void verAlias() {

        String aliasActual = detalleCuentaPage.getVerAliasTexto().getText();
        ;
        if (aliasActual.equals(aliasGenerado)) {
            System.out.println("El alias se ha guardado correctamente.");
        } else {
            System.out.println("Ha ocurrido un error al guardar el alias.");
        }

    }

    @Step("Click Ver Alias")
    public void clickVerAlias() {
        detalleCuentaPage.getVerAlias().click();
    }


    @Step("Click Estado de cuenta")
    public void clickEstadoDeCuenta() {
        detalleCuentaPage.getVerAlias().click();
    }

    @Step("Click Compartir datos")
    public void clickCompartirDatos() {
        detalleCuentaPage.getCompartirDatos().click();
    }

    @Step("Click boton Buscar")
    public void clickBuscar() {
        scrollTo(By.xpath("//button[@aria-label='Buscar']"));
        clickBuscarGenerico();
    }

    @Step("Se valida que exista movimiento")
    public void validarMovimiento() {
        scrollTo(By.xpath("//div[@aria-label='fila'][1]/div[5]"));
        Assert.assertTrue("Movimiento no visible", detalleCuentaPage.getDetalleMovimientoPorFila("1").isDisplayed());
        detalleCuentaPage.getDetalleMovimientoPorFila("1").click();
    }

    @Step("Click en Solicitud de baja de cuenta")
    public void clickSolicitudBajaCuenta() {
        Assert.assertTrue("Solicitud de baja de cuenta no visible", detalleCuentaPage.getSolicitudBajaCuenta().isDisplayed());
        detalleCuentaPage.getSolicitudBajaCuenta().click();
    }

    @Step("Click en Solicitud de chequera")
    public void clickSolicitudDeChequera() {
        Assert.assertTrue("Solicitud de chequera no visible", detalleCuentaPage.getSolicitudDeChequera().isDisplayed());
        detalleCuentaPage.getSolicitudDeChequera().click();
    }

    @Step("Verificar Concepto/Importe en Movimientos de Cuenta")
    public void verificarGrillaDeMoviemientoEnCuenta(String concepto, String importe) {
        Assert.assertTrue("Fecha no se encuentra en la grilla", detalleCuentaPage.getFechaDelMovimiento().getText().equals(new UtilsGP().fechaActual()));
        System.out.println("FECHA ENCONTRADO EN LOS MOVIMIENTOS DE LA CUENTA");
        Assert.assertTrue("Concepto no se encuentra en la grilla", detalleCuentaPage.getConceptoDelMovimiento().getText().equals(concepto));
        System.out.println("CONCPETO ENCONTRADO EN LOS MOVIMIENTOS DE LA CUENTA");
        Assert.assertTrue("Monto no se encuentra en la grilla", detalleCuentaPage.getMontoDelMovimiento().getText().replace(".", "").split(",")[0].equals(String.format("$-" + Integer.parseInt(importe))));
        System.out.println("IMPORTE ENCONTRADO EN LOS MOVIMIENTOS DE LA CUENTA");
    }

    @Step("Click en filtro 'Buscar por'")
    public void clickBuscarPor() {
        Assert.assertTrue("No se encontro el desplegable buscar por", detalleCuentaPage.getFiltroBuscarPor().isDisplayed());
        scrollTo(detalleCuentaPage.getFiltroBuscarPor().getElementBy());
        detalleCuentaPage.getFiltroBuscarPor().click();
    }

    @Step("Validar botón -Guardar- deshabilitado y grisado'")
    public void validarBotonDeshabilitadoyGris() {


        assertFalse("El botón Continuar aparece habilitado", botonesGenericos.getGuardar().isEnabled());
        Assert.assertFalse("El botón no tiene color", Boolean.parseBoolean(botonesGenericos.getGuardar().getCssValue("008000")));
    }

    @Step("Validar botón -Guardar- deshabilitado y grisado mientras no se carguen entre 6 y 10 caracteres'")
    public void validarBotonDeshabilitadoyGrisCaracteres() {

        String texto2 = "test";

        String texto = String.valueOf(detalleCuentaPage.getInputAlias().getAttribute("Value"));


        int tamaño = texto.length();

        detalleCuentaPage.getInputAlias().click();
        UtilsGP.fixedWait(3000);
        for (int i = 0; i < tamaño; i++) {
            detalleCuentaPage.getInputAlias().sendKeys(Keys.BACK_SPACE);
        }
        UtilsGP.fixedWait(3000);
        detalleCuentaPage.getInputAlias().sendKeys(texto2);

        tamaño = texto2.length();

        if (tamaño < 6) {

            Assert.assertFalse("El botón no está habilitado", botonesGenericos.getGuardar().isEnabled());
            Assert.assertFalse("El botón no tiene color", Boolean.parseBoolean(botonesGenericos.getGuardar().getCssValue("008000")));
        };
    }

    @Step("Validar ingresar hasta 20 caracteres'")
    public void validarVeinteCaracteres() {

        String texto2 = "qweryyhtfdsxggtjhgdeghii";

       String texto = String.valueOf(detalleCuentaPage.getInputAlias().getAttribute("Value"));


        int tamaño = texto.length();

        detalleCuentaPage.getInputAlias().click();
        UtilsGP.fixedWait(3000);
        for (int i = 0; i < tamaño; i++) {
            detalleCuentaPage.getInputAlias().sendKeys(Keys.BACK_SPACE);
        }
        UtilsGP.fixedWait(3000);
        detalleCuentaPage.getInputAlias().sendKeys(texto2);
    }

    @Step("Validar ingreso inválido'")
    public void validarIngresoInvalido() {


        if (detalleCuentaPage.getLabelRevisáLosCaracteres().isDisplayed()) {

            assertFalse("El botón Continuar aparece habilitado", botonesGenericos.getGuardar().isEnabled());
            Assert.assertFalse("El botón no tiene color", Boolean.parseBoolean(botonesGenericos.getGuardar().getCssValue("008000")));
        }
    }

    @Step("Validar ingresar alias inválido'")
    public void validarAliasInvalido() {

        String texto2 = "!!!!!!!!!!!!!!!!";

        String texto = String.valueOf(detalleCuentaPage.getInputAlias().getAttribute("Value"));

        int tamaño = texto.length();

        detalleCuentaPage.getInputAlias().click();
        UtilsGP.fixedWait(3000);
        for (int i = 0; i < tamaño; i++) {
            detalleCuentaPage.getInputAlias().sendKeys(Keys.BACK_SPACE);
        }
        UtilsGP.fixedWait(3000);

        detalleCuentaPage.getInputAlias().sendKeys(texto2);

        if (texto2.contains("!!!!!!!!!!!!!!!!")) {

            detalleCuentaPage.getLabelRevisáLosCaracteres().isDisplayed();
        }

    }

}


