package pages.inversiones.plazosfijos;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class ConsultaDeTasasPage {

    public PandoraElement getSelectorTipoPlazoFijo() {
        return new PandoraElement(By.id("investments.rate.query.fixedTermType"));
    }

    public PandoraElement getSelectorTipoConsulta() {
        return new PandoraElement(By.id("investments.rate.query.queryType"));
    }

    public PandoraElement getPlazo() {
        return new PandoraElement(By.id("investments.rate.query.period"));
    }

    public PandoraElement getColumnaPlazoDesde() {
        return new PandoraElement(By.xpath("//*[@id='mainLayout']//span[contains(text(),'Plazo Desde')]"));
    }

    public PandoraElement getColumnaPlazoHasta() {
        return new PandoraElement(By.xpath("//*[@id='mainLayout']//span[contains(text(),'Plazo Hasta')]"));
    }

    public PandoraElement getColumnaTasaTNA() {
        return new PandoraElement(By.xpath("//*[@id='mainLayout']//span[contains(text(),'Tasa TNA pizarra')]"));
    }
}
