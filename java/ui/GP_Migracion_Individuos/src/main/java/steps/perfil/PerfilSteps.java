package steps.perfil;

import api.LoginAPI;
import api.PerfilAPI;
import io.qameta.allure.Step;
import pages.perfil.PerfilPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.login.LoginSteps;
import utils.MetodosAPI;


public class PerfilSteps extends BotonesGenericosSteps {
    private PerfilPage perfil = new PerfilPage();
    private LoginSteps loginSteps = new LoginSteps();
    PerfilAPI perfilAPI = new PerfilAPI();
    MetodosAPI metodosAPI = new MetodosAPI();
    LoginAPI loginAPI = new LoginAPI();

    @Step("Click en Perfil")
    public void clickMiPerfil(){
        perfil.getIrAPerfil().click();
        loginSteps.clickSalirPrestamo();
    }

    @Step("Click en Modificar Correo Electronico")
    public void clickModificarCorreo(){ perfil.getEmail().click();
    }
    @Step("Click en Modificar telefono celular")
    public void clickModificarTelefono(){ perfil.getTelefono().click();
    }
    @Step("Comparar Id imagen logueo con Id imagen actual")
    public void compararIdImagen(String user,int IdImagenLogueo){
        int idImagenActual = loginAPI.tomarIdImagen(user);

        assert IdImagenLogueo != idImagenActual:  "Los ID de la imagenes siguen siendo el mismo";
    }
    @Step("Click en Modificar domicilio")
    public void clickModificarDomicilio(){ perfil.getDomicilio().click();
    }
     @Step("Cambiar numero de telefono por el original API")
    public void setearNumeroTelefonoOriginalAPI(){
       perfilAPI.setearNumeroTelefonoOriginalAPI();

    }
    @Step("Click en Modificar contraseña")
    public void clickModificarContrasenia(){ perfil.getContrasenia().click();
    }
    @Step("Click en Modificar Imagen de seguridad")
    public void clickImagenDeSeguridad(){ perfil.getModificarImagen().click();
    }
    @Step("Click en Gestion de Token Virtual")
    public void clickGestionDeToken(){ perfil.getToken().click();
    }
    @Step("Click en Configuracion de datos Biometricos")
    public void clickEnDatosBiometricos(){ perfil.getDatosBiometricos().click();
    }
    @Step("Click en Configuracion dispositivos push")
    public void clickDispositivosPush(){ perfil.getDispositivosPush().click();
    }
    @Step("Click en Configuracion de Notificaciones")
    public void clickNotificaciones(){ perfil.getNotificaciones().click();
    }
    @Step("Click en Configuracion productos favoritos")
    public void clickProductosFavoritos(){ perfil.getProductosFavoritos().click();
    }
    @Step("Click en Cambiar imagen")
    public void clickCambiarImagen(){ perfil.getImagen().click();
    }
    @Step("Cambiar email por el original API")
    public void setearEmailOriginalAPI(){
        perfilAPI.setearEmailOriginalAPI();
    }
}
