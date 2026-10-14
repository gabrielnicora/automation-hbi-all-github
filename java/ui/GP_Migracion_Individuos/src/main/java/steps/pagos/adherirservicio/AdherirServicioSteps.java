package steps.pagos.adherirservicio;
import io.qameta.allure.Step;
import org.junit.Assert;
import pages.pagos.adherirservicio.AdherirServicioPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.UtilsGP;

import java.awt.*;
import java.sql.SQLException;

public class AdherirServicioSteps extends BotonesGenericosSteps {

    private AdherirServicioPage adherirServicioPage = new AdherirServicioPage();
    private UtilsGP utilsGP = new UtilsGP();

    @Step("Click Tarjeta de Debito")
    public void clickTarjetaDesplegableDebito(){
        adherirServicioPage.getTarjetaDebito().click();
    }
    @Step("Click Tarjeta Debito")
    public void seleccionarTarjetaDesplegableSiEstaPresente(String tarjetaDebito) throws AWTException {
        if(adherirServicioPage.getTarjetaDebito().isDisplayed()){
            adherirServicioPage.getTarjetaDebito().click();
            keyDownToText(tarjetaDebito);
        }else{
            System.out.println("No está presente el selector de tarjeta de debito");
        }
    }
    @Step("Rubro")
    public void clickDesplegableRubro(){
        UtilsGP.fixedWait(3000);
        adherirServicioPage.getRubro().click();
    }
    @Step("Click Ver más adhesiones")
    public void clickVerMasAdhesiones() {
        adherirServicioPage.getVerMasAdhesiones().click();
    }
    @Step("Se ingresa rubro {rubro}")
    public void setInputRubro(String rubro){
        adherirServicioPage.getInputRubro().sendKeys(rubro);
    }
    @Step("Empresa")
    public void clickDesplegableEmpresa(){
        UtilsGP.fixedWait(3000);
        Assert.assertTrue("NO SE ENCONTRO EL DESPLEGABLE EMPRESA",adherirServicioPage.getEmpresa().isEnabled());
        adherirServicioPage.getEmpresa().click();
    }
    @Step("Se ingresa empresa {empresa}")
    public void setInputEmpresa(String empresa){
        adherirServicioPage.getInputEmpresa().sendKeys(empresa);
    }

    @Step("Se ingresa Codigo de Pago")
    public void setCodigoDePago(String codigodepago){
        adherirServicioPage.getCodigoDePago().sendKeys(codigodepago);
    }

    @Step("Obtener codigo de pago guardado ")
    public String getCodigoPagoDb(String id) {
        return utilsGP.consultaDB("codigo_de_pago",id,"code");
    }

    @Step("Actualizar codigo de pago")
    public void actualizarCodigoPagoDb(String codigoPago, String id) {
        Long aEnteroCodigo = Long.parseLong(codigoPago);
        aEnteroCodigo+=1;
        String aStringCodigo = Long.toString(aEnteroCodigo);
        utilsGP.actualizarDB("codigo_de_pago",id,"code", aStringCodigo);
    }

    @Step("Setear Descrcipcion de Adhesion de servicio")
    public void setearDescripciondeAdhesion(String descripcion){
        UtilsGP.fixedWait(3000);
        adherirServicioPage.getdescripcion().sendKeys(descripcion);
    }

    @Step("Ingresar código de la empresa")
    public void setearCodigoEmpresa(String descripcion){
        UtilsGP.fixedWait(3000);
        adherirServicioPage.getcodigoempresa().sendKeys(descripcion);
    }

    @Step("Seleccionar Servicio a pagar")
    public void clickCheckServicio(){
        adherirServicioPage.getservicio().click();
    }
}
