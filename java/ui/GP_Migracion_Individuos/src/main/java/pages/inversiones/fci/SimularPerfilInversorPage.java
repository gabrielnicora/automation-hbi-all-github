package pages.inversiones.fci;

import global.practia.jemeinsamstark.web.core.WebDriver;
import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import steps.botonesgenericos.BotonesGenericosSteps;

import java.util.List;

public class SimularPerfilInversorPage {

    public PandoraElement getCantidadPreguntasSimulacion(){
        return new PandoraElement(By.xpath("(//*[@id='mainLayout']//*[text()='Pregunta']/..)[1]"));
    }
    public List<WebElement> getNroPreguntaSimulacion(String nro){
        return WebDriver.getInstance().findElements(By.xpath(String.format("//p[contains(text(),'%s.')]",nro)));
    }
    public PandoraElement getPrimerCheckPorPregunta(String nroPregunta){
        return new PandoraElement(By.xpath(String.format("//p[contains(text(),'%s.')]/following-sibling::div[1]/div[1]",nroPregunta)));
    }
}
