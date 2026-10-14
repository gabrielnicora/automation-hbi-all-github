package steps.herramientas_de_accesibilidad;

import global.practia.jemeinsamstark.web.core.WebDriver;
import global.practia.jemeinsamstark.web.elements.PandoraElement;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.herramientas_de_accesibilidad.EscritorioHerramientasDeAccesibilidadPage;
import steps.botonesgenericos.BotonesGenericosSteps;

import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Pattern;

public class EscritorioHerramientasDeAccesibilidadSteps extends BotonesGenericosSteps {

    WebDriverWait wait = new WebDriverWait(WebDriver.getInstance(), 60);
    JavascriptExecutor js = (JavascriptExecutor) WebDriver.getInstance();
    Actions actions = new Actions(WebDriver.getInstance());
    HerramientasDeAccesibilidadSteps herramientasDeAccesibilidadSteps = new HerramientasDeAccesibilidadSteps();
    EscritorioHerramientasDeAccesibilidadPage escritorio = new EscritorioHerramientasDeAccesibilidadPage();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    String tituloCardOferta = null;
    String tituloCardCuenta = null;
    String tituloCardAccesoRapido = null;


    @Step("Validar cuadro de selectores")
    public void validarSelectoresDeHerramientasDeAccesibilidadEscritorio() throws InterruptedException {
        herramientasDeAccesibilidadSteps.sleep();
        WebElement ofertas = wait.until(ExpectedConditions.elementToBeClickable((WebElement) js.executeScript("return document.querySelector('#x-insuit-shadow-container').shadowRoot.querySelector('#area_1')")));
        ofertas.isEnabled();
        WebElement cuentas = wait.until(ExpectedConditions.elementToBeClickable((WebElement) js.executeScript("return document.querySelector('#x-insuit-shadow-container').shadowRoot.querySelector('#area_2')")));
        cuentas.isEnabled();
        WebElement tarjetas = wait.until(ExpectedConditions.elementToBeClickable((WebElement) js.executeScript("return document.querySelector('#x-insuit-shadow-container').shadowRoot.querySelector('#area_3')")));
        tarjetas.isEnabled();
        WebElement accesoRapido = wait.until(ExpectedConditions.elementToBeClickable((WebElement) js.executeScript("return document.querySelector('#x-insuit-shadow-container').shadowRoot.querySelector('#area_4')")));
        accesoRapido.isEnabled();
    }

    @Step("Validar selector opcion 1")
    public void validarCuadroSelectorOpcion1HA() throws InterruptedException {
        herramientasDeAccesibilidadSteps.sleep();
        WebElement ofertas = wait.until(ExpectedConditions.visibilityOf((WebElement) js.executeScript("return document.querySelector('#x-insuit-shadow-container').shadowRoot.querySelector('#area_1').querySelector('.x-insuit-area-number')")));
    }

    @Step("Validar selector opcion 2")
    public void validarCuadroSelectorOpcion2HA() throws InterruptedException {
        herramientasDeAccesibilidadSteps.sleep();
        scrollTo(By.xpath("//span[contains(text(), 'Cuentas')]"));
        WebElement ofertas = wait.until(ExpectedConditions.visibilityOf((WebElement) js.executeScript("return document.querySelector('#x-insuit-shadow-container').shadowRoot.querySelector('#area_2').querySelector('.x-insuit-area-number')")));
    }

    @Step("Validar selector opcion 3")
    public void validarCuadroSelectorOpcion3HA() throws InterruptedException {
        herramientasDeAccesibilidadSteps.sleep();
        scrollTo(By.xpath("//span[contains(text(), 'Tarjetas')]"));
        WebElement ofertas = wait.until(ExpectedConditions.visibilityOf((WebElement) js.executeScript("return document.querySelector('#x-insuit-shadow-container').shadowRoot.querySelector('#area_3').querySelector('.x-insuit-area-number')")));
    }

    @Step("Validar selector opcion 4")
    public void validarCuadroSelectorOpcion4HA() throws InterruptedException {
        herramientasDeAccesibilidadSteps.sleep();
        scrollTo(By.xpath("//span[contains(text(), 'Acceso R')]"));
        WebElement ofertas = wait.until(ExpectedConditions.visibilityOf((WebElement) js.executeScript("return document.querySelector('#x-insuit-shadow-container').shadowRoot.querySelector('#area_4').querySelector('.x-insuit-area-number')")));
    }

    @Step("Validar selector opcion 5")
    public void validarCuadroSelectorOpcion5HA() throws InterruptedException {
        herramientasDeAccesibilidadSteps.sleep();
        WebElement ofertas = wait.until(ExpectedConditions.visibilityOf((WebElement) js.executeScript("return document.querySelector('#x-insuit-shadow-container').shadowRoot.querySelector('#area_5').querySelector('.x-insuit-area-number')")));
    }

    @Step("Validar selector titulos ofertas")
    public void validarTituloSeccion(String text) throws InterruptedException {
        Thread.sleep(2000);
        String titulo1 = new String(text.getBytes(),
                StandardCharsets.UTF_8);
        escritorio.getOpcionesHerraientaDeAccesibilidad(titulo1).isDisplayed();
    }

    @Step("Guardar titulo de card de Oferta")
    public void getTituloCardOfertas(String numCard) {
        tituloCardOferta = escritorio.getTituloCardOferta(numCard).getText();
    }

    @Step("Validar titulos card")
    public void validarTitulosOfertas() {
        String[] titulos = {"Adelanto de Sueldo", "Tarjeta de credito", "Prestamo", "Seguro"};
        String tituloCardOfertaSinTildes = quitarTildes(tituloCardOferta);

        System.out.println(tituloCardOfertaSinTildes);

        for (String titulo : titulos) {
            if (tituloCardOfertaSinTildes.contains(titulo)) {
                switch (titulo) {
                    case "Adelanto de Sueldo":
                        Assert.assertTrue(escritorio.getTituloCard2("Adelanto de sueldo").isDisplayed());
                        break;
                    case "Tarjeta de credito":
                        Assert.assertTrue(escritorio.getTituloCard2("Solicitud de tarjeta de cr").isDisplayed());
                        break;
                    case "Prestamo":
                        Assert.assertTrue(escritorio.getTituloCard2("stamo").isDisplayed());
                        break;
                    case "Seguro":
                        Assert.assertTrue(escritorio.getTituloCard2("Seguro").isDisplayed());
                        break;
                }
                return;
            }
        }
        Assert.fail("Titulo de tarjeta de oferta no reconocido: " + tituloCardOfertaSinTildes);
    }

    private String quitarTildes(String input) {
        String normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizedString).replaceAll("");
    }

    @Step("Guardar titulo de card de Cuenta")
    public void getTituloCardCuentas(String numCard) throws InterruptedException {
        Thread.sleep(2000);
        tituloCardCuenta = escritorio.getTituloCardCuenta(numCard).getText();
        System.out.println(tituloCardCuenta);
    }

    @Step("Validar titulo card cuentas")
    public void validarTitulosCardCuentas() throws InterruptedException {
        Thread.sleep(2000);
        Assert.assertTrue(escritorio.getTituloCuenta(tituloCardCuenta).isDisplayed());
    }

    @Step("Guardar titulo de card de Tarjetas")
    public void getTituloCardTarjetas(String numCard) throws InterruptedException {
        Thread.sleep(2000);
        tituloCardCuenta = escritorio.getTituloCardTarjetas(numCard).getText();
        System.out.println(tituloCardCuenta);
    }

    @Step("Validar titulo card Tarjetas")
    public void validarTitulosCardTarjetas() throws InterruptedException {
        Thread.sleep(2000);
        Assert.assertTrue(escritorio.getTituloTarjetas(tituloCardCuenta).isDisplayed());
    }

    @Step("Guardar titulo de cards de Acceso Rapido")
    public void getTituloCardAccesoRapido(String numCard) {
        scrollTo(By.xpath("(//*[contains(text(), 'Acceso R')]/ancestor::div/following::div/*[p[contains(@href,'')]])[1]"));
        tituloCardAccesoRapido = escritorio.getTituloCardAccesoRapido(numCard).getText();
        System.out.println(tituloCardAccesoRapido);
    }
    @Step("Validar titulos accesos rapido")
    public void validarTitulosAccesoRapido() throws InterruptedException {
        Thread.sleep(2000);
        if (tituloCardAccesoRapido.contains("Pagos")) {
            Assert.assertTrue(escritorio.getTituloCard2("ximos Vencimientos").isDisplayed() || escritorio.getTituloCard2("Pago de servicios").isDisplayed());
        } else if (tituloCardAccesoRapido.contains("Bonos de salud")) {
            Assert.assertTrue(escritorio.getTituloCard2("Compra de bonos de salud").isDisplayed());
        }
        else if (tituloCardAccesoRapido.contains("QR")) {
            Assert.assertTrue(escritorio.getTituloCard2("Extracción QR").isDisplayed());
        }else {
            Assert.assertTrue(escritorio.getTituloAccesoRapido(tituloCardAccesoRapido).isDisplayed());
        }
    }
}
