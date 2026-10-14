package steps.seguros;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.Assert;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.seguros.SegurosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.UtilsGP;

import java.util.Arrays;
import java.util.List;

public class SegurosSteps extends BotonesGenericosSteps {
    UtilsGP utilsGP= new UtilsGP();
    SegurosPage segurosPage= new SegurosPage();
    BotonesGenericosPage botonesGenericosPage= new BotonesGenericosPage();

    @Step("Click card de seguro de notebook")
    public void clickCardSeguroNotebook() {
      segurosPage.getCardSeguroNotebook().click();
    }
    @Step("Click card de seguro de Robo")
    public void clickCardSeguroRobo() {
        Assert.assertTrue("NO SE ENCONTRO LA CARD ", segurosPage.getCardSeguroRobo().isDisplayed());
        segurosPage.getCardSeguroRobo().click();
    }
    @Step("Click card de seguro de Accidentes")
    public void clickCardSeguroAccidentes() {
        Assert.assertTrue("NO SE ENCONTRO LA CARD ", segurosPage.getCardSeguroAccidentes().isDisplayed());
        segurosPage.getCardSeguroAccidentes().click();
    }
    @Step("Click card de seguro de hogar")
    public void clickCardSeguroHogar() {
        segurosPage.getCardSeguroHogar().click();
    }
    @Step("Validar boton continuar disponible")
    public void validarBotonContinuarDisponible() {
        botonesGenericosPage.getContinuar().isEnabled();
    }
    @Step("Validar boton cancelar disponible")
    public void validarBotonCancelarDisponible() {
        botonesGenericosPage.getCancelar().isEnabled();
    }
    @Step("Descargar resumen contrato")
    public void clickDescargarResumenContrato() {
        segurosPage.getDescargaResumenContrato().click();
    }

    @Step("Validar campos de Pago de Tarjeta")
    public void validacionDeCardsSeguros() {
        boolean flag = false;
        List<String> cards = Arrays.asList(
                "Notebook",
                "Mascotas",
                "RoboCajeros",
                "Hogar",
                "Compra",
                "RoboBolso",
                "Accidentes"
        );

        for (String card : cards) {
            if(utilsGP.estaPresente(segurosPage.getValidacionCards(card),1)){
                System.out.println("SE ENCONTRÓ LA CARD "+ card +" PARA REGISTRAR UN SEGURO ");
                Allure.addAttachment("CARD",card);
                flag = true;
                break;
            }else{
                System.out.println("NO SE ENCONTRO LA CARD DE " + card);
            }
        }
        Assert.assertTrue("USUARIO SIN CARD DE SEGURO - USUARIO SIN CALIFICACION PARA SEGUROS", flag);
    }
    @Step("Validar detalle de seguro de hogar")
    public void validacionCardRobo() {
        Assert.assertTrue("La card seguro de ROBO no está visible", UtilsGP.estaPresente(segurosPage.getValidacionCards("Robo"),2 ));
    }
    @Step("Validar detalle de seguro de hogar")
    public void validacionCardAccidentes() {
        Assert.assertTrue("La card seguro de ROBO no está visible", UtilsGP.estaPresente(segurosPage.getValidacionCards("Accidentes"),2 ));
    }

    @Step("Validar detalle de seguro de notebook")
    public void validacionDetalleSeguroNotebook() {
        List<String> texts = Arrays.asList(
                "ESTIMADO CLIENTE",
                "BANCO ENTRE RIOS S.A",
                "LE OFRECE UN SEGURO DE NOTEBOOK",
                "DESEA MAS INFORMACION?"

        );

        for (String text : texts) {
            Assert.assertTrue("La card '" + text + "' no está visible", segurosPage.getValidacionText(text).isDisplayed());
        }

        System.out.println("Cards de seguros validadas con éxito");
    }
    @Step("Validar detalle de seguro de robo")
    public void validacionDetalleSeguroRobo() {
        List<String> texts = Arrays.asList(
                "ESTIMADO CLIENTE",
                "LE OFRECE",
                "UN SEGURO DE",
                "BOLSO PROTEGIDO"

        );

        for (String text : texts) {
            Assert.assertTrue("La card '" + text + "' no está visible", segurosPage.getValidacionText(text).isDisplayed());
        }

        System.out.println("Cards de seguros validadas con éxito");
    }
    @Step("Validar detalle de seguro de Accidentes")
    public void validacionDetalleSeguroAccidentes() {
        List<String> texts = Arrays.asList(
                "TENEMOS NOVEDADES PARA VOS",
                "TE OFRECE UN SEGURO",
                "DE ACCIDENTES PERSONALES",
                "SUMA ASEGURADA POR",
                "MUERTE ACCIDENTAL $1.500.000",
                "VALOR:$310 POR MES",
                "SI QUERES CONOCER MAS,SELECCIONA",
                "ME INTERESA Y SE IMPRIMIRA UN",
                "TICKET CON TODA LA INFORMACION",
                "SOBRE LAS CONDICIONES",
                "DE CONTRATACION"

        );

        for (String text : texts) {
            Assert.assertTrue("La card '" + text + "' no está visible", segurosPage.getValidacionText(text).isDisplayed());
        }

        System.out.println("Cards de seguros validadas con éxito");
    }
    @Step("Validar detalle de seguro de hogar")
    public void validacionDetalleSeguroHogar() {
        List<String> texts = Arrays.asList(
                "ESTIMADO CLIENTE",
                "LE OFRECE UN SEGURO DE HOGAR",
                "ASEGURA TU CASA ",
                "DESEA MAS INFORMACION?"

        );

        for (String text : texts) {
            Assert.assertTrue("La card '" + text + "' no está visible", segurosPage.getValidacionText(text).isDisplayed());
        }

        System.out.println("Cards de seguros validadas con éxito");
    }
    @Step("Validar seguro activo")
    public void validacionSeguroActivo() {
        List<String> texts = Arrays.asList(
                "Tipo",
                "Plan",
                "Aseguradora",
                "Forma de Pago",
                "Valor"

        );

        for (String text : texts) {
            Assert.assertTrue("La leyenda del seguro activo '" + text + "' no está visible", segurosPage.getValidacionText(text).isDisplayed());
        }

        System.out.println("Leyendas del seguro activo validadas");
    }
}
