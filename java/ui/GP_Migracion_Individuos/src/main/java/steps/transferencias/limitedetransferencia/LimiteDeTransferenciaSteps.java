package steps.transferencias.limitedetransferencia;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import pages.transferencias.limitedetransferencia.LimiteDeTransferenciaPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.UtilsGP;

import java.sql.SQLException;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

public class LimiteDeTransferenciaSteps extends BotonesGenericosSteps {
    private LimiteDeTransferenciaPage limiteDeTransferenciaPage = new LimiteDeTransferenciaPage();
    private UtilsGP utilsGP = new UtilsGP();

    @Step("Click desplegable Tarjeta de debito")
    public void clickTarjetaDebito(){
        limiteDeTransferenciaPage.getTarjetaDebitoDesplegable().click();
    }
    @Step("Click desplegable Cuenta origen")
    public void clickCuentaOrigen(){
        limiteDeTransferenciaPage.getCuentaOrigenDesplegable().click();
    }
    @Step("Click calendario En la fecha")
    public void clickEnLaFecha(){
        limiteDeTransferenciaPage.getEnLaFechaCalendario().click();
    }
    @Step("Se ingresa correo {correo}")
    public void setCorreoElectronico(String correo){
        limiteDeTransferenciaPage.getCorreoElectronicoInput().sendKeys(correo);
    }
    @Step("Click boton Continuar")
    public void clickBotonContinuar(){
        scrollTo(By.id("tisRequest.button.continue"));
        limiteDeTransferenciaPage.getBotonContinuar().click();
    }
    @Step("Se ingresa importe limite")
    public String setImporteLimite(String banco) {
        int limite = parseInt(UtilsGP.consultaDB("limite_transferencias", banco, "amount"));
        limite += 1;
        String importe = String.valueOf(limite);
        setImporteGenerico(importe);
        return importe;
    }

    @Step("Actualizar importe limite en db")
    public void actualizarLimiteDb(String banco, String importe) {
        UtilsGP.actualizarDB("limite_transferencias", banco, "amount", importe);
    }
}
