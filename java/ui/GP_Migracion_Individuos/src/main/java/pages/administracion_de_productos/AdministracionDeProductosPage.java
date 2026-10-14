package pages.administracion_de_productos;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class AdministracionDeProductosPage {

    public PandoraElement getDesplegableProducto(){
        return new PandoraElement(By.id("contractsSelector"));
    }

}
