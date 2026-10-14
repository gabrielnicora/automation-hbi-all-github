package steps.perfil.configurar_notificaciones;

import io.qameta.allure.Step;
import pages.perfil.configurarnotificaciones.ConfigurarNotificacionesPage;
import steps.botonesgenericos.BotonesGenericosSteps;

public class ConfigurarNotificacionesSteps extends BotonesGenericosSteps {
    ConfigurarNotificacionesPage notificacionesPage= new ConfigurarNotificacionesPage();

    @Step("Click en Mensajes del Banco")
    public void clickMensajesDelBanco(){
        notificacionesPage.getMensajesDelBanco().click();
    }
    @Step("Click en Operaciones Realizadas")
    public void clickOperacionesRealizadas(){
        notificacionesPage.getOperacionesRealizadas().click();
    }
    @Step("Click en Usuarios Bloqueados")
    public void clickUsuariosBloqueados(){
        notificacionesPage.getUsuarioBloqueado().click();
    }
    @Step("Validar Switch Activado por Default")
    public void validacionDeSwitchActivado(){
        notificacionesPage.getSwitchCorreoActivado().isDisplayed();
        notificacionesPage.getSwitchAplicacionActivado().isDisplayed();
        notificacionesPage.getSwitchMensajeTextoActivado().isDisplayed();
    }


}
