package steps.yopmail;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import pages.yopmail.YopmailPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.UtilsGP;

import java.util.concurrent.TimeUnit;

import static utils.UtilsGP.removerAcentos;

public class YopmailSteps extends BotonesGenericosSteps {

    private YopmailPage yopmailPage = new YopmailPage();
    private UtilsGP utilsGP = new UtilsGP();
    private String URL_Yopmail = "https://yopmail.com/es/";

    @Step("Se ingrasa mail {mail}")
    public void setMail(String mail) throws InterruptedException {
        Thread.sleep(3000);
        yopmailPage.getInputMail().sendKeys(mail);
    }

    @Step("Click ingresar a casilla de mail")
    public void clickFlechaIngresar() {
        yopmailPage.getFlechaIngresarMail().click();
    }

    @Step("Click menu 3 puntos")
    public void clickMenuTresPuntos() {
        yopmailPage.getMenuTresPuntos().click();
    }

    @Step("Click boton casilla")
    public void clickBotonVaciarCasilla() {
        yopmailPage.getVaciarCasilla().click();
    }

    @Step("Click primer mail")
    public void clickPrimerMail() throws InterruptedException {
        Thread.sleep(3000);
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='ifinbox']")));
        Assert.assertTrue("Primer mail no existe", yopmailPage.getPrimerMail().isDisplayed());
        yopmailPage.getPrimerMail().click();
        driver.switchTo().defaultContent();
    }
    private static final int MAX_INTENTOS_ACTUALIZACION = 10;

    @Step("Actualizar casilla de mail")
    public void clickActualizarCasilla() {
        Assert.assertTrue("Actualizar no encontrado", yopmailPage.getActualizarCasilla().isDisplayed());
        for (int i = 0; i < MAX_INTENTOS_ACTUALIZACION; i++) {
            yopmailPage.getActualizarCasilla().click();
            if (yopmailPage.getTituloEmail("Recupero de usuario").isDisplayed()) {
                System.out.println("Se encontró un correo electrónico con el título especificado.");
                return;
            }
        }
        System.out.println("No se encontró un correo electrónico con el título especificado después de " + MAX_INTENTOS_ACTUALIZACION + " intentos.");
    }
    @Step("Vaciar casilla de Yopmail")
    public void vaciarCasillaYopmail(String mail) throws InterruptedException {
        utilsGP.NuevoTab();
        utilsGP.ManageTab();
        utilsGP.NavegarUtilsWeb(URL_Yopmail);
        Thread.sleep(3000);
        setMail(mail);
        clickFlechaIngresar();
        clickMenuTresPuntos();
        clickBotonVaciarCasilla();
        utilsGP.aceptarAlert();
        utilsGP.backMainTap();


    }

    @Step("Ingresar a Yopmail")
    public void ingresarYopmail() throws InterruptedException {
        utilsGP.NuevoTab();
        utilsGP.ManageTab();
        utilsGP.NavegarUtilsWeb(URL_Yopmail);
    }

    @Step("Validar titulo mail recibido")
    public void validarTituloMailRecibido(String titulo) throws InterruptedException {
        Thread.sleep(3000);
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='ifmail']")));
        String textoObtenido = yopmailPage.getTituloMail().getText();
        textoObtenido = removerAcentos(textoObtenido);
        Assert.assertEquals("No existe mail", titulo, textoObtenido);
        driver.switchTo().defaultContent();
    }

    @Step("Regresar al banco")
    public void regresaAlBanco() {
        utilsGP.backMainTap();
    }

    @Step("Logo Yopmail visible")
    public void waitYopmail() {
        global.practia.jemeinsamstark.web.core.WebDriver.getInstance().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
}
