package steps.inversiones.plazosfijos;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.inversiones.plazosfijos.PlazosFijosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.Settings;
import utils.UtilsGP;

import java.math.BigDecimal;
import java.util.regex.*;


import java.awt.*;

public class PlazosFijosSteps extends BotonesGenericosSteps{

    private PlazosFijosPage plazosFijosPage = new PlazosFijosPage();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    SolicitarPlazoFijoSteps solicitarPlazoFijoSteps = new SolicitarPlazoFijoSteps();
    String sinPunto;

    @Step("Click en Consulta de Tasas")
    public void clickConsultaTasas() { plazosFijosPage.getConsultaDeTasas().click(); }

    @Step("Click en Solicitar Plazo Fijo")
    public void clickSolicitarPlazoFijo() {
        plazosFijosPage.getSolicitarPlazoFijo().click();
    }

    @Step("Validar votón Continuar deshabilitado")
    public void validarBtnContinuarDeshabilitado() {
        Assert.assertFalse("El botón Continuar se encuentra habilitado",botonesGenericosPage.getContinuar().isEnabled());
    }

    @Step("Validar botón Continuar habilitado -Plazo Fijo en Pesos")
    public void validarBtnContinuarHabilitado(String cuenta) throws AWTException {


        solicitarPlazoFijoSteps.clickSelectorCuentaDebito();
        solicitarPlazoFijoSteps.keyDownToText(cuenta);
        String saldo = botonesGenericosPage.getSeleccionCuentaDebito().getText();

        String s = saldo;

        Pattern p = Pattern.compile("(?m)^\\$\\s*([0-9.]+,[0-9]{2})\\s*$");
        Matcher m = p.matcher(s);

        saldo = m.find() ? m.group(1) : null;

        String saldoActualizado = UtilsGP.parseMontoAR(saldo).toString();

        String sinComa = saldoActualizado.replace(",", "");
        sinPunto = sinComa.replace(".", "");

        solicitarPlazoFijoSteps.clickSelectorTipoPF();
        solicitarPlazoFijoSteps.keyDownToText("Plazo Fijo Tradicional en P");
        UtilsGP.fixedWait(3000);
        botonesGenericosSteps.setImporteGenericoValidacion(sinPunto);
        solicitarPlazoFijoSteps.setPlazoEnDias("180");
        solicitarPlazoFijoSteps.clickSelectorAccionVencimiento();
        solicitarPlazoFijoSteps.keyDownToText( "al vencimiento");

        Assert.assertTrue("El botón Continuar se encuentra habilitado",botonesGenericosPage.getContinuar().isEnabled() );
    }

    @Step("Validar botón Continuar habilitado -Plazo Fijo en Dólaares")
    public void validarBtnContinuarHabilitadoDolares(String cuenta) throws AWTException {



        solicitarPlazoFijoSteps.clickSelectorTipoPF();
        solicitarPlazoFijoSteps.keyDownToText("Plazo Fijo Tradicional en D");
        UtilsGP.fixedWait(3000);

        solicitarPlazoFijoSteps.clickSelectorCuentaDebito();
        solicitarPlazoFijoSteps.keyDownToText(cuenta);
        UtilsGP.fixedWait(4000);
        String saldo = botonesGenericosPage.getSeleccionCuentaDebito().getText();

        String s = saldo;

        Pattern p = Pattern.compile("(?m)^USD\\s*([0-9.]+,[0-9]{2})\\s*$");
        Matcher m = p.matcher(s);


        saldo = m.find() ? m.group(1) : null;

        String saldoActualizado = UtilsGP.parseMontoAR(saldo).toString();

        String sinComa = saldoActualizado.replace(",", "");
        sinPunto = sinComa.replace(".", "");

        botonesGenericosSteps.setImporteGenericoValidacion(sinPunto);
        solicitarPlazoFijoSteps.setPlazoEnDias("180");
        solicitarPlazoFijoSteps.clickSelectorAccionVencimiento();
        solicitarPlazoFijoSteps.keyDownToText( "al vencimiento");

        Assert.assertTrue("El botón Continuar se encuentra habilitado",botonesGenericosPage.getContinuar().isEnabled() );
    }

    @Step("Validar botón Continuar habilitado -Plazo Fijo UVA")
    public void validarBtnContinuarHabilitadoUva(String cuenta) throws AWTException {


        solicitarPlazoFijoSteps.clickSelectorTipoPF();
        UtilsGP.fixedWait(2000);
        solicitarPlazoFijoSteps.clickPlazoFijoUVA();
        UtilsGP.fixedWait(3000);

        solicitarPlazoFijoSteps.clickSelectorCuentaDebito();
        solicitarPlazoFijoSteps.keyDownToText(cuenta);
        String saldo = botonesGenericosPage.getSeleccionCuentaDebito().getText();

        String s = saldo;

        Pattern p = Pattern.compile("(?m)^\\$\\s*([0-9.]+,[0-9]{2})\\s*$");
        Matcher m = p.matcher(s);

        saldo = m.find() ? m.group(1) : null;

        String saldoActualizado = UtilsGP.parseMontoAR(saldo).toString();

        String sinComa = saldoActualizado.replace(",", "");
        sinPunto = sinComa.replace(".", "");



        botonesGenericosSteps.setImporteGenericoValidacion(sinPunto);
        solicitarPlazoFijoSteps.setPlazoEnDias("180");
        solicitarPlazoFijoSteps.clickSelectorAccionVencimiento();
        solicitarPlazoFijoSteps.keyDownToText( "al vencimiento");

        Assert.assertTrue("El botón Continuar se encuentra habilitado",botonesGenericosPage.getContinuar().isEnabled() );
    }

    @Step("Validar botón Continuar habilitado -Plazo Fijo Precancelable")
    public void validarBtnContinuarHabilitadoPrecancelable(String cuenta) throws AWTException {


        solicitarPlazoFijoSteps.clickSelectorTipoPF();
        solicitarPlazoFijoSteps.keyDownToText("Plazo Fijo UVA Precancelable");
        UtilsGP.fixedWait(3000);


        solicitarPlazoFijoSteps.clickSelectorCuentaDebito();
        solicitarPlazoFijoSteps.keyDownToText(cuenta);
        String saldo = botonesGenericosPage.getSeleccionCuentaDebito().getText();

        String s = saldo;

        Pattern p = Pattern.compile("(?m)^\\$\\s*([0-9.]+,[0-9]{2})\\s*$");
        Matcher m = p.matcher(s);

        saldo = m.find() ? m.group(1) : null;

        String saldoActualizado = UtilsGP.parseMontoAR(saldo).toString();

        String sinComa = saldoActualizado.replace(",", "");
        sinPunto = sinComa.replace(".", "");



        botonesGenericosSteps.setImporteGenericoValidacion(sinPunto);
        solicitarPlazoFijoSteps.setPlazoEnDias("180");
        solicitarPlazoFijoSteps.clickSelectorAccionVencimiento();
        solicitarPlazoFijoSteps.keyDownToText( "al vencimiento");

        Assert.assertTrue("El botón Continuar se encuentra habilitado",botonesGenericosPage.getContinuar().isEnabled() );
    }

    @Step("Validar mensaje de error -Monto supera saldo en cuenta-")
    public void validarMensajeMontoSuperaSaldoCta(String cuenta) throws AWTException {


        solicitarPlazoFijoSteps.clickSelectorCuentaDebito();
        solicitarPlazoFijoSteps.keyDownToText(cuenta);
        String saldo = botonesGenericosPage.getSeleccionCuentaDebito().getText();

        String s = saldo;

        Pattern p = Pattern.compile("(?m)^\\$\\s*([0-9.]+,[0-9]{2})\\s*$");
        Matcher m = p.matcher(s);

        saldo = m.find() ? m.group(1) : null;

        String saldoActualizado = UtilsGP.parseMontoAR(saldo).toString();

        String sinComa = saldoActualizado.replace(",", "");
        sinPunto = sinComa.replace(".", "");


        solicitarPlazoFijoSteps.clickSelectorTipoPF();
        solicitarPlazoFijoSteps.keyDownToText("Plazo Fijo Tradicional en P");
        UtilsGP.fixedWait(3000);
        botonesGenericosSteps.setImporteGenericoValidacion(sinPunto);
        solicitarPlazoFijoSteps.setPlazoEnDias("180");
        solicitarPlazoFijoSteps.clickSelectorAccionVencimiento();
        solicitarPlazoFijoSteps.keyDownToText( "al vencimiento");
        botonesGenericosSteps.clickContinuarGenerico();

        Assert.assertFalse("El botón Continuar se encuentra habilitado",botonesGenericosPage.getContinuar().isEnabled() );
        Assert.assertTrue("No se muestra mensaje de error", plazosFijosPage.getValidarMensajeDeError().isEnabled() );
    }

    @Step("Validar botón Continuar habilitado después de modificar el saldo")
    public void validarBotonContinuarHabilitadoSaldoModificado() throws AWTException {



        BigDecimal valor = new BigDecimal(sinPunto);
        BigDecimal montoModificado = valor.subtract(BigDecimal.valueOf(4000000));


        UtilsGP.fixedWait(3000);
        solicitarPlazoFijoSteps.clickCampoImporte();
        solicitarPlazoFijoSteps.clearCampoImporte();
        botonesGenericosSteps.setImporteGenericoValidacion(String.valueOf(montoModificado));


        Assert.assertTrue("El botón Continuar se encuentra habilitado",botonesGenericosPage.getContinuar().isEnabled() );
    }

}
