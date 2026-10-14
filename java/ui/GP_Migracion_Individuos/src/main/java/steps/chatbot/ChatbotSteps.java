package steps.chatbot;

import global.practia.jemeinsamstark.web.core.WebDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.chatbot.ChatbotPage;

public class ChatbotSteps {

    private ChatbotPage chatbotPage = new ChatbotPage();
    WebDriverWait wait = new WebDriverWait(WebDriver.getInstance(), 10);
    JavascriptExecutor js = (JavascriptExecutor) WebDriver.getInstance();
    Actions actions = new Actions(WebDriver.getInstance());

    @Step("Posicionarse sobre le icono del chatbot")
    public void hoverChatbot() throws InterruptedException {
        Actions action = new Actions(WebDriver.getInstance());
        Thread.sleep(1500);
        action.moveToElement(chatbotPage.getBotonChatbot()).perform();
    }

    @Step("Click boton necesitas ayuda chatbot")
    public void clickNecesitasAyudaChatbot() {
        chatbotPage.getBotonNecesitasAyuda().click();
    }

    @Step("Validar acceso chatbot")
    public void validarChatbot() throws InterruptedException {
        Thread.sleep(2000);
        JavascriptExecutor js = (JavascriptExecutor) WebDriver.getInstance();
        WebElement shadow = (WebElement) js.executeScript("document.querySelector('#AgentAppContainer').shadowRoot.querySelector('.aivochat-header-maximize')");
        shadow.click();
    }
}
