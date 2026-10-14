package steps.inversiones.fci;

import io.qameta.allure.Step;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.inversiones.fci.SimularPerfilInversorPage;
import steps.botonesgenericos.BotonesGenericosSteps;

public class SimularPerfilInversorSteps extends BotonesGenericosSteps {

    SimularPerfilInversorPage simularPerfilInversorPage = new SimularPerfilInversorPage();

    @Step("Se completan las 12 preguntas del formulario de simulacion")
    public void completarFormularioDeSimulacion() throws InterruptedException {
        String pregunta, cantidadDePreguntas, nroDePregunta;
        int cant, cont = 0, nuevoNroPregunta;
        pregunta = simularPerfilInversorPage.getCantidadPreguntasSimulacion().getText();
        cantidadDePreguntas = StringUtils.substringAfter(pregunta,"/");
        nroDePregunta = StringUtils.substringBetween(pregunta,"Pregunta ","/");
        cant = Integer.parseInt(cantidadDePreguntas);
        while(cont<cant){
            System.out.println("Nro pregunta: "+nroDePregunta+" Contador: "+cont);
            int existePregunta = simularPerfilInversorPage.getNroPreguntaSimulacion(nroDePregunta).size();
            scrollTo(By.xpath("//button[@aria-label='Continuar']"));
            if(existePregunta < 1) { //si la pregunta NO esta visible, presiona continuar
                clickContinuarGenerico();
                wait.until(ExpectedConditions.visibilityOf(simularPerfilInversorPage.getPrimerCheckPorPregunta(nroDePregunta)));
            }
            Thread.sleep(1000);
            simularPerfilInversorPage.getPrimerCheckPorPregunta(nroDePregunta).click(); //click primera opcion de cada pregunta
            nuevoNroPregunta = Integer.parseInt(nroDePregunta);
            nuevoNroPregunta++;
            nroDePregunta = String.valueOf(nuevoNroPregunta);
            cont++;
        }
    }
}
