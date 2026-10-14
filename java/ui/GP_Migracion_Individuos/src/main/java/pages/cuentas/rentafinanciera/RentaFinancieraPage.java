package pages.cuentas.rentafinanciera;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class RentaFinancieraPage {
    public PandoraElement getAnioDesplegablePorAnio(String anio){
        return new PandoraElement(By.xpath(String.format("//p[text()='%s']", anio)));
    }
    public PandoraElement getBotonDescargarPorFecha(String fecha){
        return new PandoraElement(By.xpath(String.format("//p[text()='%s']/following-sibling::div", fecha)));
    }
}
