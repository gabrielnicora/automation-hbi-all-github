package pages.cuentas.detallecuenta;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class DetalleCuentaPage {
    public PandoraElement getDetalleMovimientoPorFila(String nroFila){
        return new PandoraElement(By.xpath(String.format("//div[@aria-label='fila'][%s]/div[5]", nroFila)));
    }
    public PandoraElement getTodosLosMovimientos(){
        return new PandoraElement(By.id("movements.filter.all.movements.button.label"));
    }
    public PandoraElement getMesAnterior(){
        return new PandoraElement(By.id("movements.filter.previousMonth.button.label"));
    }
    public PandoraElement getMasFiltros(){
        return new PandoraElement(By.xpath("//button[@aria-label='MAS FILTROS']"));
    }
    public PandoraElement getCuotificalo(){
        return new PandoraElement(By.id("menu.quotification"));
    }
    public PandoraElement getModificarAlias(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Modificar Alias')]/parent::p/parent::a"));
    }
    public PandoraElement setModificarAlias(){
        return new PandoraElement(By.id("cbuAlias"));
    }
    public PandoraElement getVerAlias(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Ver alias')]"));
    }
    public PandoraElement getVerAliasTexto(){
        return new PandoraElement(By.xpath("(//div[@aria-label='Icono de copiar']/parent::p)[2]"));
    }

    public PandoraElement getEstadoDeCuenta(){
        return new PandoraElement(By.xpath("//*[contains(@href,'/accountStateConsultationOrDownload/')]"));
    }
    public PandoraElement getCompartirDatos(){
        return new PandoraElement(By.id("movements.info.share.button.label"));
    }

    public PandoraElement getSolicitudBajaCuenta(){
        return new PandoraElement(By.xpath("//*[contains(@href,'/closeAccount/')]"));
    }
    public PandoraElement getSolicitudDeChequera(){
        return new PandoraElement(By.xpath("//*[contains(@href,'/checkbookRequest/')]"));
    }
    public PandoraElement getConceptoDelMovimiento(){return new PandoraElement(By.xpath("(//span[contains(text(),'Concepto')]/parent::div/following::span)[2]"));}
    public PandoraElement getFechaDelMovimiento(){return new PandoraElement(By.xpath("(//span[contains(text(),'Fecha')]/../following::p)[1]"));}
    public PandoraElement getMontoDelMovimiento(){return new PandoraElement(By.xpath("(//span[contains(text(),'Monto')]/parent::div/following::span)[2]"));}
    public PandoraElement getFiltroBuscarPor(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Buscar por')]/../../div"));
    }

    public PandoraElement getInputAlias(){
        return new PandoraElement(By.xpath("//*[@id='cbuAlias']"));
    }

    public PandoraElement getLabelRevisáLosCaracteres(){
        return new PandoraElement(By.xpath("//*[@id='mainLayout']/div[1]/div/div[3]/p"));
    }
}
