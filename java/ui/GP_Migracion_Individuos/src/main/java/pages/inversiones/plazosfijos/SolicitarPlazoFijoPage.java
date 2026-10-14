package pages.inversiones.plazosfijos;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class SolicitarPlazoFijoPage {

    public PandoraElement getSelectorTipo() {
        return new PandoraElement(By.xpath("//*[@id='selectorControlBy_account']/div[1]"));

    }

    public PandoraElement getPlazoEnDias() {
        return new PandoraElement(By.id("term"));
    }

    public PandoraElement getSelectorCuentaDebito() {
        return new PandoraElement(By.id("account"));
    }

    public PandoraElement getSelectorAccionAlVencimiento() {
        return new PandoraElement(By.id("selectorDescribedBy_2"));
    }

    public PandoraElement getTipoPlazoFijo(String tipoPF) {
        return new PandoraElement(By.xpath(String.format("//*[text()='%s']",tipoPF)));
    }

    public PandoraElement getListaDesplegableItemPlazoFijoUVA() {
        return new PandoraElement(By.xpath("//div[text()='Plazo Fijo UVA']"));
    }

}
