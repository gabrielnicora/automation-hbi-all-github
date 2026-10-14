package pages.perfil;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class PerfilPage {
    public PandoraElement getIrAPerfil() {
        return new PandoraElement(By.xpath("//a[contains(@href, 'settings')][contains(text(), 'Ir a mi perfil')]"));
    }

    public PandoraElement getImagen() {
        return new PandoraElement(By.xpath("//a[contains(@href, 'settings/changeSecuritySeal')]//img"));
    }
    public PandoraElement getEmail() {
        return new PandoraElement(By.xpath("//a[contains(@href, 'settings/changeEmail')]"));
    }
    public PandoraElement getDomicilio() {
        return new PandoraElement(By.xpath("//a[contains(@href, 'settings/changeAddress')]"));
    }
    public PandoraElement getTelefono() {
        return new PandoraElement(By.xpath("//a[contains(@href, 'settings/changePhone')]"));
    }
    public PandoraElement getContrasenia() {
        return new PandoraElement(By.xpath("//a[contains(@href, 'settings/changePassword')]"));
    }
    public PandoraElement getModificarImagen() {
        return new PandoraElement(By.xpath("//a[contains(@href, 'settings/changeSecuritySeal')]"));
    }
    public PandoraElement getToken() {
        return new PandoraElement(By.xpath("//a[contains(@href, 'settings/SoftToken')]"));
    }
    public PandoraElement getDatosBiometricos() {
        return new PandoraElement(By.xpath("//a[contains(@href, 'settings/fingerprintConfiguration')]"));
    }
    public PandoraElement getDispositivosPush() {
        return new PandoraElement(By.xpath("//a[contains(@href, 'settings/pushNotifications')]"));
    }
    public PandoraElement getNotificaciones() {
        return new PandoraElement(By.xpath("//a[contains(@href, 'settings/notificationsConfiguration')]"));
    }
    public PandoraElement getProductosFavoritos() {
        return new PandoraElement(By.xpath("//a[@href='/settings/favoriteProduct']"));
    }

}
