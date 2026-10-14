package pages.chatbot;

import global.practia.jemeinsamstark.web.core.WebDriver;
import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ChatbotPage {

    public WebElement getBotonChatbot(){
        return WebDriver.getInstance().findElement(By.id("bot_launcher"));
    }

    public PandoraElement getTitulo(){
        return new PandoraElement(By.xpath("//*[@id='mainLayout']/header/h1/span"));
    }
    public PandoraElement getBotonNecesitasAyuda(){
        return new PandoraElement(By.id("agentbot-tab"));
    }

    public PandoraElement getTextoBtnNecesitasAyuda(){
        return new PandoraElement(By.xpath("//span[contains(text(),'¿Necesitás ayuda?')]"));
    }
    public WebElement getShadowHost(){ //padre shadow
        return WebDriver.getInstance().findElement(By.cssSelector("#AgentAppContainer"));
    }
    public WebElement getBotonMaximizarChatbot(){
        return WebDriver.getInstance().findElement(By.cssSelector("div > div > div > div > div:nth-child(1) > div > div:nth-child(1) > div.aivochat-header.custom-background.close-icon-disabled-header > div.aivochat-header-title > div > div.aivochat-header-maximize"));
    }
}
