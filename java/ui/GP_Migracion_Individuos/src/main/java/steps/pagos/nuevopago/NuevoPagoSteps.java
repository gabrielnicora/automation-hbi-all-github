package steps.pagos.nuevopago;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.pagos.PagosPage;
import pages.pagos.nuevopago.NuevoPagoPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.doblefactor.DobleFactorSteps;
import steps.menulateral.MenuLateralSteps;
import steps.pagos.PagosSteps;
import steps.pagos.adherirservicio.AdherirServicioSteps;
import steps.ticket.TicketSteps;
import utils.UtilsGP;

import java.awt.*;

public class NuevoPagoSteps extends BotonesGenericosSteps {

    private NuevoPagoPage nuevoPagoPage = new NuevoPagoPage();
    private BotonesGenericosPage botonesGenericos = new BotonesGenericosPage();
    private BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    private AdherirServicioSteps adherirServicioSteps = new AdherirServicioSteps();
    private MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    private TicketSteps ticketSteps = new TicketSteps();
    PagosSteps pagosSteps = new PagosSteps();
    private DobleFactorSteps dobleFactorSteps= new DobleFactorSteps();
    private PagosPage pagos = new PagosPage();
    private UtilsGP utilsgp = new UtilsGP();

    @Step("Tarjeta de Credito Desplegable")
    public void clickDesplegableTarjetaDeDedito(){
        nuevoPagoPage.getTarjetaDebito().click();
    }
    @Step("Click Tarjeta Debito")
    public void seleccionarTarjetaDebitoSiEstaPresente(String tarjetaDebito) throws AWTException {
        if(nuevoPagoPage.getTarjetaDebito().isDisplayed()){
            nuevoPagoPage.getTarjetaDebito().click();
            keyDownToText(tarjetaDebito);
        }else{
            System.out.println("No está presente el selector de tarjeta de debito");
        }
    }
    @Step("Click card Servicios")
    public void clickCardServicios(){
        UtilsGP.fixedWait(5000);
        nuevoPagoPage.getCardServicios().isDisplayed();
        nuevoPagoPage.getCardServicios().click();
    }
    @Step("Rubro")
    public void clickDesplegableRubro(){
        nuevoPagoPage.getRubro().click();
    }
    @Step("Se ingresa rubro {rubro}")
    public void clickInputRubro(){
        //nuevoPagoPage.getInputRubro().sendKeys(rubro);
        nuevoPagoPage.getInputRubro().isDisplayed();
        nuevoPagoPage.getInputRubro().click();
    }
    @Step("Click Desplegable Empresa")
    public void clickDesplegableEmpresa(){
        Assert.assertTrue("NO SE ENCONTRO EL DESPLEGABLE EMPRESA", nuevoPagoPage.getEmpresa().isEnabled());
        nuevoPagoPage.getEmpresa().click();
    }
    @Step("Se ingresa empresa {empresa}")
    public void clickInputEmpresa(){
        //nuevoPagoPage.getInputEmpresa().sendKeys(empresa);
        nuevoPagoPage.getInputEmpresa().isDisplayed();
        nuevoPagoPage.getInputEmpresa().click();
    }
    @Step("Tarjeta de Credito Desplegable")
    public void setCodigoDePago(String codigodepago){
        nuevoPagoPage.getCodigoDePago().sendKeys(codigodepago);
        }
    @Step("Tarjeta de Credito Desplegable")
    public void clickPagandoDesdeDesplegable(){
        nuevoPagoPage.getPagandoDesde().isDisplayed();
        nuevoPagoPage.getPagandoDesde().click();
    }
    @Step("Obtener codigo de pago guardado")
    public String getCodigoPagoDb(String id) {
        return UtilsGP.consultaDB("codigo_de_pago",id,"code");
    }
    @Step("Obtener empresa para codigo de pago guardado")
    public String getEmpresaCpeDb() {
        return UtilsGP.consultaDB("codigo_de_pago","Coop. De Serv. Pub. Mar Del Tuyu","name");
    }
    @Step("Validar mensaje CÓDIGO DE PAGO")
    public void validarMensajeCodigoPago() {
        Assert.assertTrue("No aparece el mensaje Código de pago",nuevoPagoPage.getElementoCodigoPago().isDisplayed());
    }
    @Step("Obtener rubro para codigo de pago guardado")
    public String getRubroCpeDb() {
        return UtilsGP.consultaDB("codigo_de_pago","Coop. De Serv. Pub. Mar Del Tuyu","category");
    }
    @Step("Actualizar codigo de pago")
    public void actualizarCodigoPagoDb(String codigoPago, String id) {
        Long aEnteroCodigo = Long.parseLong(codigoPago);
        aEnteroCodigo+=1;
        String aStringCodigo = Long.toString(aEnteroCodigo);
        UtilsGP.actualizarDB("codigo_de_pago",id,"code",aStringCodigo);
    }
    @Step("Click check box Añadir nuevo Serivcio")
    public void clickCheckBoxServicio(){
        nuevoPagoPage.getAdherirServicio().isDisplayed();
        nuevoPagoPage.getAdherirServicio().click();
    }
    @Step("Click Desplegable Tarjeta Debito")
    public void clickDesplegableTarjetaDebito(){
        nuevoPagoPage.getTarjetaDebitoDesplegable().click();
    }
    @Step("Se selecciona el elemento {texto}")
    public void keyDownToText(String texto) throws AWTException {
        try {
            utilsgp.keyDownAndSelectElement(texto);
        } catch (Exception e) {
            utilsgp.keyDown(0);
            scrollTo(By.xpath(String.format("//*[contains(text(),'%s')]",texto)));
            if (!botonesGenericos.getTextKeyDownToText(texto).isDisplayed()) {
                throw new NoSuchElementException("Elemento no encontrado o no visible: " + texto);
            }
        }
        if (!texto.isEmpty()) {
            System.out.println("Opción seleccionada del desplegable: " + texto);
        }
    }

    @Step("Click lista Rubro")
    public void clicklistaRubro(){

        nuevoPagoPage.getlistaRubro().click();
    }

    @Step("Click lista Empresa")
    public void clicklistaEmpresa(){

        nuevoPagoPage.getlistaEmpresa().click();
    }

    @Step("Validar si existen servicios ya adheridos por descripción")
    public void validarServiciosUnisys(String servicio) throws AWTException {

        if (!botonesGenericos.getSeleccionarServicio1(servicio).isDisplayed()){
            botonesGenericosSteps.clickVolverFlechaGenerico();
            botonesGenericosSteps.seleccionarServicioGenerico2("Servicios adheridos");
            adherirServicioSteps.validarTituloPageGenerico("Servicios adheridos");
            botonesGenericosSteps.seleccionarServicioGenerico1("Agendar servicio");
            adherirServicioSteps.clickDesplegableRubro();
            botonesGenericosSteps.seleccionarDropdownGenerico("Cobranzas para Empresas y Comercios");
            adherirServicioSteps.clickDesplegableEmpresa();
            botonesGenericosSteps.seleccionarDropdownGenerico("Mercado Libre");
            adherirServicioSteps.setearCodigoEmpresa("00449535691");
            adherirServicioSteps.setearDescripciondeAdhesion("MLPAYMENT");
            adherirServicioSteps.clickContinuarGenerico();
            botonesGenericosSteps.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            menuLateralSteps.clickPagos();
            botonesGenericosSteps.seleccionarServicioGenerico2("Servicios adheridos");
            adherirServicioSteps.validarTituloPageGenerico("Servicios adheridos");
            botonesGenericosSteps.seleccionarServicioGenerico1("Adherir servicio");
            adherirServicioSteps.clickDesplegableRubro();
            botonesGenericosSteps.seleccionarDropdownGenerico("Cobranzas para Empresas y Comercios");
            adherirServicioSteps.clickDesplegableEmpresa();
            botonesGenericosSteps.seleccionarDropdownGenerico("Mercado Libre");
            adherirServicioSteps.setearCodigoEmpresa("00449535691");
            adherirServicioSteps.setearDescripciondeAdhesion("MLPAYMENT");
            adherirServicioSteps.clickContinuarGenerico();
            botonesGenericosSteps.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            menuLateralSteps.clickPagos();
        }
        botonesGenericosSteps.clickVolverFlechaGenerico();

    }

    @Step("Validar si existen servicios ya adheridos por descripción para Banco Santa Fé")
    public void validarServiciosSantaFe(String servicio) throws AWTException {

        if (!botonesGenericos.getSeleccionarServicio1(servicio).isDisplayed()){
            botonesGenericosSteps.clickVolverFlechaGenerico();
            botonesGenericosSteps.seleccionarServicioGenerico2("Servicios agendados");
            adherirServicioSteps.validarTituloPageGenerico("Servicios agendados");
            botonesGenericosSteps.seleccionarServicioGenerico1("Agendar servicio");
            adherirServicioSteps.clickDesplegableRubro();
            botonesGenericosSteps.seleccionarDropdownGenerico("Cobranzas para Empresas y Comercios");
            adherirServicioSteps.clickDesplegableEmpresa();
            botonesGenericosSteps.seleccionarDropdownGenerico("Mercado Libre");
            adherirServicioSteps.setearCodigoEmpresa("00449535691");
            adherirServicioSteps.setearDescripciondeAdhesion("MLPAYMENT");
            adherirServicioSteps.clickContinuarGenerico();
            botonesGenericosSteps.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            menuLateralSteps.clickPagos();
            botonesGenericosSteps.seleccionarServicioGenerico2("Servicios agendados");
            adherirServicioSteps.validarTituloPageGenerico("Servicios agendados");
            botonesGenericosSteps.seleccionarServicioGenerico1("Agendar servicio");
            adherirServicioSteps.clickDesplegableRubro();
            botonesGenericosSteps.seleccionarDropdownGenerico("Cobranzas para Empresas y Comercios");
            adherirServicioSteps.clickDesplegableEmpresa();
            botonesGenericosSteps.seleccionarDropdownGenerico("Mercado Libre");
            adherirServicioSteps.setearCodigoEmpresa("00449535691");
            adherirServicioSteps.setearDescripciondeAdhesion("MLPAYMENT");
            adherirServicioSteps.clickContinuarGenerico();
            botonesGenericosSteps.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            menuLateralSteps.clickPagos();
            pagosSteps.clickMostrarTodos();
            pagosSteps.clickCheckboxServicio("1");
            pagosSteps.clickCheckboxServicio("2");
            botonesGenericosSteps.clickbtnPagar();
            botonesGenericosSteps.validarTituloPageGenerico("Mercado Libre");
            botonesGenericosSteps.setImporteGenerico("1");
            botonesGenericosSteps.setReferenciaGenerica("1234567890");
            botonesGenericosSteps.clickContinuarGenerico();
            botonesGenericosSteps.validarTituloPageGenerico("Mercado Libre");
            botonesGenericosSteps.setImporteGenerico("1");
            botonesGenericosSteps.setReferenciaGenerica("1234567890");
            botonesGenericosSteps.clickbtnPagar();
            botonesGenericosSteps.validarTituloPageGenerico("Nuevo pago");
            botonesGenericosSteps.clickbtnPagar();
            botonesGenericosSteps.clickConfirmarGenerico();
            botonesGenericosSteps.validarTituloPageGenerico("Estado de tus pagos");
            menuLateralSteps.clickPagos();
        }
    }

    @Step("Validar si está presente el botón 'Mostrar todos'")
    public void validarBotonMostrarTodos() throws AWTException {

        if (!pagos.getMostrarTodos().isDisplayed()){
            botonesGenericosSteps.seleccionarServicioGenerico2("Servicios agendados");
            adherirServicioSteps.validarTituloPageGenerico("Servicios agendados");
            botonesGenericosSteps.seleccionarServicioGenerico1("Agendar servicio");
            adherirServicioSteps.clickDesplegableRubro();
            botonesGenericosSteps.seleccionarDropdownGenerico("Cobranzas para Empresas y Comercios");
            adherirServicioSteps.clickDesplegableEmpresa();
            botonesGenericosSteps.seleccionarDropdownGenerico("Mercado Libre");
            adherirServicioSteps.setearCodigoEmpresa("00449535691");
            adherirServicioSteps.setearDescripciondeAdhesion("MLPAYMENT");
            adherirServicioSteps.clickContinuarGenerico();
            botonesGenericosSteps.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            menuLateralSteps.clickPagos();
            botonesGenericosSteps.seleccionarServicioGenerico2("Servicios agendados");
            adherirServicioSteps.validarTituloPageGenerico("Servicios agendados");
            botonesGenericosSteps.seleccionarServicioGenerico1("Agendar servicio");
            adherirServicioSteps.clickDesplegableRubro();
            botonesGenericosSteps.seleccionarDropdownGenerico("Cobranzas para Empresas y Comercios");
            adherirServicioSteps.clickDesplegableEmpresa();
            botonesGenericosSteps.seleccionarDropdownGenerico("Mercado Libre");
            adherirServicioSteps.setearCodigoEmpresa("00449535691");
            adherirServicioSteps.setearDescripciondeAdhesion("MLPAYMENT");
            adherirServicioSteps.clickContinuarGenerico();
            botonesGenericosSteps.clickConfirmarGenerico();
            dobleFactorSteps.codigoSegundoFactor();
            ticketSteps.validarTicket("Tu solicitud se realizo con exito");
            menuLateralSteps.clickPagos();
            pagosSteps.clickMostrarTodos();
            pagosSteps.clickCheckboxServicio("1");
            pagosSteps.clickCheckboxServicio("2");
            botonesGenericosSteps.clickbtnPagar();
            botonesGenericosSteps.validarTituloPageGenerico("Mercado Libre");
            botonesGenericosSteps.setImporteGenerico("1");
            botonesGenericosSteps.setReferenciaGenerica("1234567890");
            botonesGenericosSteps.clickContinuarGenerico();
            botonesGenericosSteps.validarTituloPageGenerico("Mercado Libre");
            botonesGenericosSteps.setImporteGenerico("1");
            botonesGenericosSteps.setReferenciaGenerica("1234567890");
            botonesGenericosSteps.clickbtnPagar();
            botonesGenericosSteps.validarTituloPageGenerico("Nuevo pago");
            botonesGenericosSteps.clickbtnPagar();
            botonesGenericosSteps.clickConfirmarGenerico();
            botonesGenericosSteps.validarTituloPageGenerico("Estado de tus pagos");
            menuLateralSteps.clickPagos();
        }
        pagosSteps.clickMostrarTodos();
    }
}
