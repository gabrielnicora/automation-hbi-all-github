package steps.inversiones.fci;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.inversiones.fci.SuscripcionFCIPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.MetodosAPI;
import utils.UtilsGP;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class SuscripcionFCISteps extends BotonesGenericosSteps {

    private SuscripcionFCIPage suscripcionFCI = new SuscripcionFCIPage();

    public  LinkedHashMap<String,String> datosCapturados = new LinkedHashMap<>();
    private MetodosAPI metodosAPI = new MetodosAPI();


    @Step("Seleccionar Cuenta Debito")
    public void clickCuentaDebito() { suscripcionFCI.getCuentaDebito().click(); }
    @Step("Click en cada checkbox de la pantalla Suscribir")
    public void clickCheckbox(){
        scrollTo(By.xpath("//*[contains(@aria-label,'checkbox No chequeado')]"));
        List<WebElement> listaCheckbox = suscripcionFCI.getTodosLosCheckboxNoChequeados();
        if (listaCheckbox.size() > 0){
            for (WebElement checkbox:listaCheckbox) {
                checkbox.click();
            }
        }
    }
    @Step("Se ingresa importe a suscribir")
    public void setImporteSuscribirFci(String amount) {
        setImporteGenerico(amount);
    }
    @Step("Se ingresa importe a suscribir")
    public void setImporteSuscribirFci2(String amount) {
        String banco = System.getProperty("bank");
        String importeObtenido = UtilsGP.consultaDB("suscribir_fci",banco,amount);
        setImporteGenerico(importeObtenido);
    }
    @Step("Se actualiza  importe a suscribir")
    public void actualizarImporteSuscribir(String amount, String signoSumarORestar) {
        String banco = System.getProperty("bank");
        int nuevoImporte = Integer.parseInt(UtilsGP.consultaDB("suscribir_fci", banco, amount));

        if (signoSumarORestar.equals("+")) {
            nuevoImporte++;
        } else if (signoSumarORestar.equals("-")) {
            nuevoImporte--;
        }

        UtilsGP.actualizarDB("suscribir_fci", banco, amount, String.valueOf(nuevoImporte));
    }

    @Step("Validar pantalla completa 'Confirmar la suscripción de FCI'")
    public void validacionPantallaConfirmacionFCI() {
        List<String> campos = Arrays.asList(
                "Nombre del Fondo Común de inversión",
                //"Fecha de suscripción",
                "Cuenta Comitente",
                "Cuenta débito",
                "Importe a Suscribir",
                "Cantidad estimada de cuotapartes");
                //"Cantidad de cuota estimada en base al último valor de cuotaparte publicado. Dicha operación se liquidará con el valor de la cuotaparte del día de la fecha, generado al cierre de las operaciones.");
        for (String campo : campos) {
            scrollTo(By.xpath(String.format("//*[contains(text(), '%s')]",campo)));
            Assert.assertTrue("El campo '" + campo + "' no esta visible", suscripcionFCI.textos(campo).isDisplayed());
        }
        System.out.println("Titulos validados con exito");
    }

    @Step("Captura de datos a validar en el ticket")
    public LinkedHashMap<String, String> capturaDatosAValidarEnTicket(){
        datosCapturados.put("Nombre del Fondo Común de inversión",suscripcionFCI.getCamposAValidar("Nombre del Fondo Común de inversión").getText());
        datosCapturados.put("Cuenta comitente",suscripcionFCI.getCamposAValidar("Cuenta Comitente").getText());
        datosCapturados.put("Cuenta débito",suscripcionFCI.getCamposAValidar("Cuenta débito").getText());
        datosCapturados.put("Importe a Suscribir",suscripcionFCI.getCamposAValidar("Importe a Suscribir").getText());
        datosCapturados.put("Cantidad estimada de cuotapartes",suscripcionFCI.getCamposAValidar("Cantidad estimada de cuotapartes").getText());
        System.out.println("DATOS A CAPTURADOS PARA VALIDAR EN EL TICKET");
        System.out.println("NOMBRE DEL FONDO : "+datosCapturados.get("Nombre del Fondo Común de inversión"));
        System.out.println("CUENTA COMITENTE : "+datosCapturados.get("Cuenta comitente"));
        System.out.println("CUENTA DEBITO : "+datosCapturados.get("Cuenta débito"));
        System.out.println("IMPORTE : "+datosCapturados.get("Importe a Suscribir"));
        System.out.println("CUOTAPARTES : "+datosCapturados.get("Cantidad estimada de cuotapartes"));
        return datosCapturados;
    }

    @Step("Generación de FCI por API")
    public void generarFciAPI(String usuario, String cuentaComitente, int tipoFCI, int importe, String cuentaDebito, String password) {
        metodosAPI.generarFCI(usuario,password,cuentaComitente ,tipoFCI ,importe,cuentaDebito );

    }
}
