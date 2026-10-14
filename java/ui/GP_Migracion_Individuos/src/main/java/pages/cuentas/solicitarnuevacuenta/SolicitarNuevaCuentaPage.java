package pages.cuentas.solicitarnuevacuenta;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class SolicitarNuevaCuentaPage {

    public PandoraElement getTipoDeCuentaDesplegable(){
        return new PandoraElement(By.id("selectorDescribedBy_0"));
    }
    public PandoraElement getMonedaDeLaCuentaDesplegable(){
        return new PandoraElement(By.id("selectorDescribedBy_1"));
    }
}
