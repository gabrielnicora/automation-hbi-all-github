package steps.home;

import io.qameta.allure.Step;
import org.junit.Assert;
import pages.home.HomePage;



public class HomeSteps {

    private HomePage page = new HomePage();

    @Step("Verificacion del Banner")
    public void verifyBanner(){
        Assert.assertTrue("Unable to find Banner", page.getBanner().isDisplayed());
    }

    @Step("Verificacion del producto: {producto}")
    public void verifyProduct(String producto){
        Assert.assertTrue("Unable to find Product: " + producto, page.getProduct(producto).isDisplayed());
    }

}
