package steps.perfil.productofavoritos;

import io.qameta.allure.Step;
import org.junit.Assert;
import pages.perfil.productosfavoritos.ProductosFavoritosPage;
import steps.botonesgenericos.BotonesGenericosSteps;

public class ProductosFavoritosSteps extends BotonesGenericosSteps {

    private ProductosFavoritosPage productosFavoritosPage= new ProductosFavoritosPage();

    @Step("Click Desplegable Tipo de Producto")
    public void clickDesplegableTipoProducto(){ productosFavoritosPage.getDesplegableTipoDeProducto().click();
    }
    @Step("Click Corazon pintado o despintado")
    public void clickCorazonPintadoODespintado(String cuenta) {
        if (productosFavoritosPage.getCheckSeleccionado(cuenta).isDisplayed()) {
            Assert.assertTrue("El checkbox esta seleccionado", productosFavoritosPage.getCheckSeleccionado(cuenta).isDisplayed());
            productosFavoritosPage.getCheckSeleccionado(cuenta).click();
        } else {
            Assert.assertTrue("El checkbox no esta seleccionado", productosFavoritosPage.getCheckNoSelecionado(cuenta).isDisplayed());
            productosFavoritosPage.getCheckNoSelecionado(cuenta).click();
        }
    }
}
