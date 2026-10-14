package steps.cuentas.agendadedestinatarios;

import io.qameta.allure.Step;
import org.junit.Assert;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.cuentas.agendadedestinararios.AgendaDeDestinatariosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.ticket.TicketSteps;

public class AgendaDeDesetinatariosSteps extends BotonesGenericosSteps {
    private AgendaDeDestinatariosPage agendaDeDestinatarios = new AgendaDeDestinatariosPage();
    BotonesGenericosPage botonesGenericosPage= new BotonesGenericosPage();
    TicketSteps ticketSteps= new TicketSteps();

    @Step("Click en Radio Button Cuentas Propias")
    public void clickCuentasPropias(){
        agendaDeDestinatarios.getCuentasPropias().click();
    }
    @Step("Click en radio button Cuentas Terceros")
    public void clickCuentasTerceros(){
        agendaDeDestinatarios.getCuentaTerceros().click();
    }
    @Step("Click en Entidad Bancaria desplegable")
    public void clickEntidadBancariaDesplegable(){
        agendaDeDestinatarios.getEntidadBancariaDesplegable().click();
    }
    @Step("Ingresar Pertenece A")
    public void setPertenceA(String texto){
        agendaDeDestinatarios.getPerteneceA().sendKeys(texto);
    }
    @Step("Eliminar contacto Pertenece A")
    public void eliminarContacto(String texto) throws InterruptedException {
        agendaDeDestinatarios.getPerteneceA().sendKeys(texto);
        if(botonesGenericosPage.getIconoEliminar().isDisplayed()){
            clickIconoEliminar();
            clickConfirmarGenerico();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
        }else{
            System.out.println("No se encuentra ningun contacto agendado con el nombre: " + texto);
        }
    }
    @Step("Click en Adherir nuevo destinatario")
    public void clickAdherirNuevoDestinatario(){
        agendaDeDestinatarios.getAdherirNuevoDestinatario().click();
    }
    @Step("Click en Cuenta {nroCuenta} adherida") //Ej: "CA $ 000003555304"
    public void clickCuentaAdheridaXnroCuenta(String nroCuenta){
        agendaDeDestinatarios.getCuentaAdheridaXnroCuenta(nroCuenta).click();
    }
}
