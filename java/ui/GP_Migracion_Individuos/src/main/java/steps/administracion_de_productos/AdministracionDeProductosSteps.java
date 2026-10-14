package steps.administracion_de_productos;

import io.qameta.allure.Step;
import pages.administracion_de_productos.AdministracionDeProductosPage;
import steps.botonesgenericos.BotonesGenericosSteps;

import java.awt.*;
import java.awt.event.KeyEvent;

public class AdministracionDeProductosSteps extends BotonesGenericosSteps {
    AdministracionDeProductosPage administracionDeProductosPage= new AdministracionDeProductosPage();

    @Step("Click Desplegable Producto")
    public void clickDesplegableProducto(){
        administracionDeProductosPage.getDesplegableProducto().isDisplayed();
        administracionDeProductosPage.getDesplegableProducto().click();
    }

    @Step("Descarga de archivos")
    public void descargarArchivo() throws InterruptedException, AWTException {
        clickConsultarGenerico();
    }
}
