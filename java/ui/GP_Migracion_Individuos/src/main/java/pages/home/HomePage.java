package pages.home;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class HomePage {

    public PandoraElement getBanner(){
        return new PandoraElement(By.id("slideshow0"));
    }

    public PandoraElement getProduct(String product){
        return new PandoraElement(By.xpath(String.format("//div[@class='caption']//a[contains(text(), '%s')]", product))); }
}
