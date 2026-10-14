package pages.menulateral;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class MenuLateralPage {

    public PandoraElement getInicio(){
        return new PandoraElement(By.xpath("//p[contains(text(), 'Inicio')]//ancestor::button"));
    }
    public PandoraElement getCuentas() {
        return new PandoraElement(By.xpath("//p[contains(text(), 'Cuentas')]//ancestor::button"));
    }
    public PandoraElement getTarjetas() {
        return new PandoraElement(By.xpath("//p[contains(text(), 'Tarjetas')]//ancestor::button"));
    }
    public PandoraElement getTarjetaDeCredito() {
        return new PandoraElement(By.xpath("//span[contains(text(), 'Tarjeta de cr')]//ancestor::button"));

    }

    public PandoraElement getTarjetaDeDebito() {
        return new PandoraElement(By.xpath("//span[contains(text(), 'Tarjeta de d')]//ancestor::button"));

    }

    public PandoraElement getPrestamos() {
        return new PandoraElement(By.xpath("//p[contains(text(),'stamos')]//ancestor::button"));

    }

    public PandoraElement getTransferencias() {
        return new PandoraElement(By.xpath("//p[contains(text(), 'Transferencias')]//ancestor::button"));

    }

    public PandoraElement getPagos() {
        return new PandoraElement(By.xpath("//span[contains(text(), 'Pagos')]//ancestor::button"));

    }
    public PandoraElement getNotificacion() {
        return new PandoraElement(By.xpath("//*[@class='notification-message']"));

    }


    public PandoraElement getBonosDeSalud() {
        return new PandoraElement(By.xpath("//p[contains(text(),'Bonos de salud')]//ancestor::button"));

    }

    public PandoraElement getRecargas() {
        return new PandoraElement(By.xpath("//span[contains(text(), 'Recargas')]//ancestor::button"));

    }

    public PandoraElement getInversiones() {
        return new PandoraElement(By.xpath("//p[contains(text(), 'Inversiones')]//ancestor::button"));

    }

    public PandoraElement getPagosYServicios() {
        return new PandoraElement(By.xpath("//p[contains(text(), 'Pagos y servicios')]//ancestor::button"));

    }
    public PandoraElement getSeguros() {
        return new PandoraElement(By.xpath("//p[contains(text(), 'Seguros')]//ancestor::button"));

    }

    public PandoraElement getBaja_Arrepentimiento() {
        return new PandoraElement(By.xpath("//span[contains(text(), 'Arrepentimiento')]//ancestor::button"));

    }

    public PandoraElement getMisTramites() {
        return new PandoraElement(By.xpath("//span[contains(text(),'Mis trámites')]//ancestor::button"));

    }

    public PandoraElement getFeDeVida() {
        return new PandoraElement(By.xpath("//nav//a[contains(@href, 'faithOfLine')]"));

    }
    public PandoraElement getTurnosOnline() {
        return new PandoraElement(By.xpath("//span[contains(text(),'Turnos online')]//ancestor::button"));

    }
    public PandoraElement getComparacionDeComisiones() {
        return new PandoraElement(By.xpath("//p[contains(text(),'n de comisiones')]//ancestor::button"));

    }
    public PandoraElement getColocacionesAPlazo() {
        return new PandoraElement(By.xpath("//p[contains(text(),'Colocaciones a Plazo')]//ancestor::button"));

    }
    public PandoraElement getAdministracionDeProductos() {
        return new PandoraElement(By.xpath("//p[contains(text(), 'Administración')]//ancestor::button"));

    }
    public PandoraElement getCuotificalo() {
        return new PandoraElement(By.xpath("//p[contains(text(),'Cuotifícalo')]//ancestor::button"));

    }
    public PandoraElement getMisPuntos() {
        return new PandoraElement(By.xpath("//p[contains(text(),'Mis puntos')]//ancestor::button"));

    }
    public PandoraElement getTramites() {
        return new PandoraElement(By.xpath("//p[contains(text(),'Trámites')]//ancestor::button"));

    }
    public PandoraElement getMisProductos() {
        return new PandoraElement(By.xpath("//p[contains(text(),'Mis productos')]//ancestor::button"));

    }
    public PandoraElement getContratos() {
        return new PandoraElement(By.xpath("//span[contains(text(), 'Contratos')]/ancestor::button"));

    }

    public PandoraElement getProductos() {
        return new PandoraElement(By.xpath("//span[contains(text(), 'Productos')]/ancestor::button"));

    }


}
