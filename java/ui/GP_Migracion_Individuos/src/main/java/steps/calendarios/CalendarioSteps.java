package steps.calendarios;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import pages.calendarios.CalendarioPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.UtilsGP;

import java.time.LocalDate;

public class CalendarioSteps extends BotonesGenericosSteps {
    CalendarioPage calendarioPage = new CalendarioPage();

    @Step("Click mes anterior")
    public void clickMesAnterior(int repeticiones){
        for (int i=0; i<repeticiones; i++) { calendarioPage.getMesAnterior().click(); }
    }
    @Step("Click mes proximo")
    public void clickMesProximo(int repeticiones){
        for (int i=0; i<repeticiones; i++) { calendarioPage.getMesProximo().click(); }
    }
    @Step("Click día Actual")
    public void clickDiaActual(){
        calendarioPage.getDiaActual().click();
    }
    @Step("Click dia {dia} del calendario")
    public void clickEnDiaXParametro(String dia){
        calendarioPage.getDiaXParametro(dia).click();
    }

    @Step("Click Fecha de Creacion Desde")
    public void clickEnFechaDeCreacionDesde(){
        scrollTo(calendarioPage.getFechaCreacionDesde().getElementBy());
        Assert.assertTrue("NO SE ENCONTRO EL BOTON",calendarioPage.getFechaCreacionDesde().isDisplayed());
        calendarioPage.getFechaCreacionDesde().click();
    }
    @Step("Click Fecha de Creacion Hasta")
    public void clickEnFechaDeCreacionHasta(){
        calendarioPage.getFechaCreacionHasta().click();
    }
    @Step("Click Buscar")
    public void clickBuscar(){
        scrollTo(By.xpath("//button[@aria-label='Buscar']"));
        calendarioPage.getBuscar().click();
    }
    @Step("Click calendario")
    public void clickCalendario(){
        calendarioPage.getCalendario().click();
    }

    @Step("Click mes especifico")
    public void retrocederSegunMes(int numeroMes) {
        LocalDate fechaActual = LocalDate.now();
        int mesActual = fechaActual.getMonthValue();
        int anioActual = fechaActual.getYear();
        int mesesParaRetroceder = 0;
        int anioRetroceder = anioActual;
        if (mesActual >= numeroMes) {
            mesesParaRetroceder = mesActual - numeroMes;
        } else {
            mesesParaRetroceder = 12 - (numeroMes - mesActual);
            anioRetroceder--;
        }
        while (anioRetroceder < anioActual) {
            anioActual--;
        }
        for (int i = 0; i < mesesParaRetroceder; i++) {
            calendarioPage.getMesAnterior().click();
        }
    }
    @Step("Cick primer dia de un mes y anio especifico")
    public void retrocedeMesYAnioEspecifico(String mesAnio){
        calendarioPage.getNombreMesAnio().isDisplayed();
        while(true){
            if(mesAnio.equals(calendarioPage.getNombreMesAnio().getText())){
                System.out.println("================== Se Encontro el mes especifico ==========");
                break;
            }else{
                calendarioPage.getMesAnterior().click();
            }
        }
    }
}
