package pages.pagos.debincomprador;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class AdherirRecurrenciaPage {

    public PandoraElement getSelectorCuentaDebito() {
        return new PandoraElement(By.id("debin.asBuyer.recurrencies.subscribe.making.account"));
    }

    public PandoraElement getCuitVendedor() {
        return new PandoraElement(By.id("debin.asBuyer.recurrencies.subscribe.making.sellerCuit"));
    }

    public PandoraElement getBotonConsultar() {
        return new PandoraElement(By.xpath("//button[@aria-label='Consultar']"));
    }

    public PandoraElement getSelectorPrestacion() {
        return new PandoraElement(By.id("debin.asBuyer.recurrencies.subscribe.making.entitlement"));
    }

    public PandoraElement getSelectorConcepto() {
        return new PandoraElement(By.id("conceptSelector"));
    }

    public PandoraElement getReferencia() {
        return new PandoraElement(By.id("debin.asBuyer.recurrencies.subscribe.making.reference"));
    }

    public PandoraElement getDescripcion() {
        return new PandoraElement(By.id("debin.asBuyer.recurrencies.subscribe.making.description"));
    }

    public PandoraElement getBotonContinuar() {
        return new PandoraElement(By.xpath("//button[@aria-label='Continuar']"));
    }

}
