package steps.herramientas_de_accesibilidad;

import io.qameta.allure.Step;
import pages.tarjetasdecredito.TarjetasDeCreditoPage;
import pages.tarjetasdedebito.TarjetasDeDebitoPage;

public class TarjetasDeCreditoHerramientasAccesibilidadSteps extends HerramientasDeAccesibilidadSteps {
   TarjetasDeDebitoPage tarjetasDeDebitoPage= new TarjetasDeDebitoPage();
   TarjetasDeCreditoPage tarjetasDeCreditoPage= new TarjetasDeCreditoPage();
    @Step("Ingresar a Tarjetas de Credito")
    public void ingresarATarjetasCredito() throws InterruptedException {
        presionarTeclaFin();
        presionarTecla12();
        presionarTecla13();

    }

    @Step("Ingresar a Ver mas tc")
    public void ingresarAVerMas() throws InterruptedException {
        Thread.sleep(1000);
        presionarTecla4();
    }

    @Step("Ingresar a Solicitar Tarjeta Nueva")
    public void ingresarASolicitarTarjetaNueva() throws InterruptedException {
        if(tarjetasDeCreditoPage.getCancelacionSaldoEnCuotas().isDisplayed()){
            presionarTecla3();
        }
        else{
            presionarTecla2();
        }
    }

    @Step("Ingresar a Seguimiento de tarjetas")
    public void ingresarASeguimientoDeTarjetas() throws InterruptedException {
        if(tarjetasDeDebitoPage.getSeguimientoDeTaretas().isDisplayed()){
            presionarTecla3();
        }else {
            throw new RuntimeException("No existe la opción de seguimiento de tarjetas");
        }
    }

    @Step("Descargar resumen contrato")
    public void descargarResumenDeContrato() throws InterruptedException {
        presionarTecla2();
        Thread.sleep(1000);
        presionarTecla1();
        Thread.sleep(1000);
        presionarTecla1();
    }

    @Step("Descargar resumen de tarjeta")
    public void descargarResumenDeTC() throws InterruptedException {
        presionarTecla4();
    }

    @Step("Solicitar Adicional")
    public void solicitarAdicional() throws InterruptedException {
        presionarTecla3();
    }

    @Step("Ingresar a Denunciar Robo y Extravio")
    public void ingresarADenunciarRoboyExtravio() throws InterruptedException {
        presionarTecla5();
        sleep();
        presionarTecla6();
    }
    @Step("Ingresar a Pagar mi Tarjeta")
    public void ingresarAPagarMiTarjeta() throws InterruptedException {
        presionarTecla5();
        sleep();
        presionarTecla7();
    }
    @Step("Ingresar a Aviso de viaje al exterior")
    public void ingresarAvisoDeViajeAlExterior() throws InterruptedException {
        presionarTecla5();
        sleep();
        presionarTecla8();
    }
    @Step("Ingresar a Solicitar baja tarjeta")
    public void ingresarASolicitarBajaDeTarjeta() throws InterruptedException {
        presionarTecla5();
        sleep();
        presionarTecla9();
    }
    @Step("Ingresar a Consultar Ultimo Resumen")
    public void ingresarAConsultarUltimoResumen() throws InterruptedException {
        presionarTecla5();
        sleep();
        presionarTecla10();
    }


}
