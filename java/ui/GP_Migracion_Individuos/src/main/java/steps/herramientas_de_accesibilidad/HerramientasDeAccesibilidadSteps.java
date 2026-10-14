package steps.herramientas_de_accesibilidad;

import global.practia.jemeinsamstark.web.core.WebDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import steps.botonesgenericos.BotonesGenericosSteps;


import java.util.List;
import java.util.concurrent.TimeUnit;

import static global.practia.jemeinsamstark.web.core.ConfigHelper.getAppDefaultWait;

public class HerramientasDeAccesibilidadSteps extends BotonesGenericosSteps {

    WebDriverWait wait = new WebDriverWait(WebDriver.getInstance(), 50);
    JavascriptExecutor js = (JavascriptExecutor) WebDriver.getInstance();
    Actions actions = new Actions(WebDriver.getInstance());

    @Step("Esperar carga de elementos Herramientas accesibilidad")
    public void waitShadowNumber() {
        List<WebElement> shadowNumbers = (List<WebElement>) js.executeScript("return document.querySelector('#x-insuit-shadow-container').shadowRoot.querySelectorAll('.x-insuit-down')");
        global.practia.jemeinsamstark.web.core.WebDriver.getInstance().manage().timeouts().implicitlyWait(getAppDefaultWait(), TimeUnit.SECONDS);
        int idx = 0;
        while (idx < 5) {
            idx++;
            try {
                if (!shadowNumbers.isEmpty()) {
                    WebElement ultimoElemento = shadowNumbers.get(shadowNumbers.size() - 1);
                    ultimoElemento.isDisplayed();
                    sleep();
                    break;
                }
            } catch (Exception e) {
                System.out.println("================ Los elementos aun no se cargaron... ================");
                try {
                    sleep();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @Step("Esperar 3 segundos")
    public void sleep() throws InterruptedException {
        Thread.sleep(4000);
    }

    @Step("Click en herramientas de accesibilidad")
    public void clickHerramientasAccesibilidad() throws InterruptedException {
        WebElement shadow = (WebElement) js.executeScript("return document.querySelector('#x-insuit-shadow-container').shadowRoot.querySelector('.open')");
        wait.until(ExpectedConditions.visibilityOf(shadow));
        Thread.sleep(1500);
        actions.click(shadow).perform();


    }

    @Step("Ingresar en herramientas de accesibilidad, cerrar tutorial y silenciar")
    public void ingresarAHerramientasAccesibilidad() throws InterruptedException {
        clickHerramientasAccesibilidad();
        presionarTeclaF7();
        presionarTeclaF12();
    }

    @Step("Presionar tecla escape")
    public void presionarTeclaEsc() throws InterruptedException {
        sleep();
        actions.sendKeys(Keys.ESCAPE).build().perform();
    }

    @Step("Presionar F7 para cerrar tutorial")
    public void presionarTeclaF7() throws InterruptedException {
        sleep();
        actions.sendKeys(Keys.F7).build().perform();
    }

    @Step("Presionar Fin")
    public void presionarTeclaFin() throws InterruptedException {
        sleep();
        actions.sendKeys(Keys.END).build().perform();
    }

    @Step("Presionar F12 para silenciar")
    public void presionarTeclaF12() throws InterruptedException {
        Thread.sleep(1000);
        actions.sendKeys(Keys.F12).build().perform();
    }

    @Step("Presionar tecla 1")
    public void presionarTecla1() throws InterruptedException {
        sleep();
        actions.sendKeys("1").build().perform();
    }

    @Step("Presionar tecla 2")
    public void presionarTecla2() throws InterruptedException {
        sleep();
        actions.sendKeys("2").build().perform();
    }

    @Step("Presionar tecla 3")
    public void presionarTecla3() throws InterruptedException {
        sleep();
        actions.sendKeys("3").build().perform();
    }

    @Step("Presionar tecla 4")
    public void presionarTecla4() throws InterruptedException {
        sleep();
        actions.sendKeys("4").build().perform();
    }

    @Step("Presionar tecla 5")
    public void presionarTecla5() throws InterruptedException {
        sleep();
        actions.sendKeys("5").build().perform();
    }

    @Step("Presionar tecla 6")
    public void presionarTecla6() throws InterruptedException {
        sleep();
        actions.sendKeys("6").build().perform();
    }

    @Step("Presionar tecla 7")
    public void presionarTecla7() throws InterruptedException {
        sleep();
        actions.sendKeys("7").build().perform();
    }

    @Step("Presionar tecla 8")
    public void presionarTecla8() throws InterruptedException {
        sleep();
        actions.sendKeys("8").build().perform();
    }

    @Step("Presionar tecla 9")
    public void presionarTecla9() throws InterruptedException {
        sleep();
        actions.sendKeys("9").build().perform();
    }

    @Step("Presionar tecla 10")
    public void presionarTecla10() throws InterruptedException {
        sleep();
        actions.sendKeys("*10*").build().perform();
    }

    @Step("Presionar tecla 11")
    public void presionarTecla11() throws InterruptedException {
        sleep();
        actions.sendKeys("*11*").build().perform();
    }

    @Step("Presionar tecla 12")
    public void presionarTecla12() throws InterruptedException {
        sleep();
        actions.sendKeys("*12*").build().perform();
    }

    @Step("Presionar tecla 13")
    public void presionarTecla13() throws InterruptedException {
        sleep();
        actions.sendKeys("*13*").build().perform();
    }

    @Step("Presionar tecla 14")
    public void presionarTecla14() throws InterruptedException {
        sleep();
        actions.sendKeys("*14*").build().perform();
    }

    @Step("Presionar tecla 26")
    public void presionarTecla26() throws InterruptedException {
        sleep();
        actions.sendKeys("*26*").build().perform();
    }

    @Step("Presionar tecla 27")
    public void presionarTecla27() throws InterruptedException {
        sleep();
        actions.sendKeys("*27*").build().perform();
    }
    @Step("Presionar tecla 28")
    public void presionarTecla28() throws InterruptedException {
        sleep();
        actions.sendKeys("*28*").build().perform();
    }

    @Step("Presionar tecla Espacio")
    public void presionarTeclaEspacio() throws InterruptedException {
        sleep();
        actions.sendKeys(Keys.SPACE).build().perform();
    }

    @Step("Presionar tecla Abajo")
    public void presionarTeclaAbajo() throws InterruptedException {
        sleep();
        actions.sendKeys(Keys.DOWN).build().perform();
    }

    @Step("Presionar tecla Arriba")
    public void presionarTeclaArriba() throws InterruptedException {
        sleep();
        actions.sendKeys(Keys.UP).build().perform();
    }

    @Step("Presionar tecla Enter")
    public void presionarTeclaEnter() throws InterruptedException {
        sleep();
        actions.sendKeys(Keys.ENTER).build().perform();
    }

    @Step("Completar campo")
    public void completarCampo(String texto) throws InterruptedException {
        Thread.sleep(2000);
        actions.sendKeys(texto).build().perform();
    }

    @Step("Ingresar a Escritorio")
    public void ingresoEscritorio() throws InterruptedException {
        sleep();
        actions.sendKeys("*27*").build().perform();

    }
    @Step("Presionar boton Acceso Rapido")
    public void clickBotonACCesoRapido() throws InterruptedException {
        scrollTo(By.xpath("//span[contains(text(),'Acceso Rápido')]"));
        presionarTecla4();
    }

}
