package pages.calendarios;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class CalendarioPage {
    public PandoraElement getMesAnterior(){
        return new PandoraElement(By.xpath("//button[contains(text(),'Previous Month')]"));
    }
    public PandoraElement getMesProximo(){
        return new PandoraElement(By.xpath("//button[contains(text(),'Next Month')]"));
    }
    public PandoraElement getDiaActual(){
        return new PandoraElement(By.xpath("//div[contains(@class,'day--today')]"));
    }
    public PandoraElement getDiaXParametro(String dia){
        return new PandoraElement(By.xpath(String.format("//div[text()= '%s' and (contains(@class,'day')) and not(contains(@class,'outside'))]", dia)));
    }
    public PandoraElement getFechaCreacionDesde(){
        return new PandoraElement(By.xpath("//div[contains(@aria-label,'esde')]/div"));
    }

    public PandoraElement getFechaCreacionHasta(){
        return new PandoraElement(By.xpath("//div[contains(@aria-label,'hasta')]/div"));
    }

    public PandoraElement getBuscar(){
        return new PandoraElement(By.xpath("//button[@aria-label='Buscar']"));
    }

    public PandoraElement getCalendario(){
        return new PandoraElement(By.id("customImputID"));
    }
    public PandoraElement getNombreMesAnio(){
        return new PandoraElement(By.xpath("//div[@class='react-datepicker__current-month']"));
    }
}
