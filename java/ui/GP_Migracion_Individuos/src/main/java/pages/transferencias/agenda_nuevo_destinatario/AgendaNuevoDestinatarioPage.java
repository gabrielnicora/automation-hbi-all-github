package pages.transferencias.agenda_nuevo_destinatario;

import global.practia.jemeinsamstark.web.elements.PandoraElement;
import org.openqa.selenium.By;

public class AgendaNuevoDestinatarioPage {

    public PandoraElement getAdherirNuevoDestinatario(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Agregar contacto nuevo')]"));
    }

    public PandoraElement getInputCbuAlias(){
        return new PandoraElement(By.id("cbuSearch"));
    }

    public PandoraElement getMensajeCbuInvalido(){
        return new PandoraElement(By.xpath("//div[contains(text(),'CBU inválido')]"));
    }
    public PandoraElement getMensajeAliasInvalido(){
        return new PandoraElement(By.xpath("//div[contains(text(),'Alias inválido')]"));
    }
    public PandoraElement getTipoCuenta(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Tipo de cuen')]/../following-sibling::p"));
    }
    public PandoraElement getBuscar(){
        return new PandoraElement(By.xpath("//button[@aria-label='Buscar']"));
    }
    public PandoraElement getNumeroCuenta(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Número de c')]/../following-sibling::p"));
    }
    public PandoraElement getEntidadBancaria(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Entidad')]/../following-sibling::p"));
    }
    public PandoraElement getCbu(){
        return new PandoraElement(By.xpath("//span[contains(text(),'CBU')]/../following-sibling::p"));
    }
    public PandoraElement getTitular(){
        return new PandoraElement(By.xpath("//span[contains(text(),'Titular')]/../following-sibling::p"));
    }
    public PandoraElement getCuit(){
        return new PandoraElement(By.xpath("//span[contains(text(),'CUIT')]/../following-sibling::p"));
    }
    public PandoraElement getInputPerteneceA(){
        //return new PandoraElement(By.id("belongsTo"));
        return new PandoraElement(By.id("reference"));

    }

    public PandoraElement getLabelAlias(){
        return new PandoraElement(By.xpath("//label[@for='ALIAS']"));
    }

    public PandoraElement getLabelCuentaTerceros(){
        return new PandoraElement(By.xpath("//label[@for='TERCERO']/*"));
    }

    public PandoraElement getIconoEliminarContacto(String contacto){
        //return new PandoraElement(By.xpath(String.format("//*[contains(text(), '%s')]/ancestor::div[@type='grid']//div[@class='sc-doqphf jDEmCV']//span[contains(text(), 'Eliminar')]", contacto)));
        return new PandoraElement(By.xpath(String.format("//*[contains(text(), '%s')]/../..//div[@class='sc-cDltVh eTcdvg']//span[contains(text(), 'Eliminar')]", contacto)));
    }
    public PandoraElement getIconoMasOpciones(String contacto){
        //return new PandoraElement(By.xpath(String.format("//*[contains(text(), '%s')]/ancestor::div[@type='grid']//div[@class='sc-doqphf jDEmCV']", contacto)));
        return new PandoraElement(By.xpath(String.format("//*[contains(text(), '%s')]/../..//div[@class='sc-cDltVh eTcdvg']", contacto)));

    }
    public PandoraElement getLabelTrueTerceros(){
        return new PandoraElement(By.xpath("//input[@value='TERCERO' and @aria-checked='true']/.."));
    }

    public PandoraElement getLabelTruePropias(){
        return new PandoraElement(By.xpath("//input[@value='PROPIA' and @aria-checked='true']/.."));
    }

    public PandoraElement getEliminar(){
        return new PandoraElement(By.xpath("//span[contains(text(), 'Eliminar')]"));


    }
    public PandoraElement getConfirmarEliminacionContacto(){
        return new PandoraElement(By.xpath("//span[contains(text(), 'Si, eliminar')]"));
    }
    public PandoraElement getInputBuscarContacto(){return new PandoraElement(By.id("belongsTo"));}

    public PandoraElement getTresPuntos(String referencia){
        return new PandoraElement(By.xpath("//p[contains(text(),'"+referencia+"')]/../../div[last()]"));
    }

    public PandoraElement getTresPuntosAlternativo(){
        return new PandoraElement(By.id("Grupo_11105"));
    }

    public PandoraElement getTresPieDPag(){
        return new PandoraElement(By.xpath("//p[contains(text(),'Pag 1')]"));
    }


}
