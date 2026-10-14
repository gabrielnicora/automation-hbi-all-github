package steps.cuentas.rentafinanciera;

import io.qameta.allure.Step;
import pages.cuentas.rentafinanciera.RentaFinancieraPage;
import steps.botonesgenericos.BotonesGenericosSteps;

public class RentaFinancieraSteps extends BotonesGenericosSteps {
    private RentaFinancieraPage rentaFinancieraPage = new RentaFinancieraPage();

    @Step("Click en registro anio {anio}")
    public void clickEnDesplegableAnio(String anio){
        rentaFinancieraPage.getAnioDesplegablePorAnio(anio).click();
    }
    @Step("Click en boton descarga del registro {fecha}")
    public void clickBotonDescargaPorFecha(String fecha){
        rentaFinancieraPage.getBotonDescargarPorFecha(fecha).click();
    }
}
