package steps.inversiones.titulos;

import api.ValidarCodeResponses;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.inversiones.titulos.TitulosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import utils.Settings;
import utils.UtilsGP;
import pages.inversiones.fci.FondoComunInversionPage;
import pages.botonesgenericos.BotonesGenericosPage;
import pages.inversiones.*;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;


import static org.junit.Assert.*;


public class TitulosStep extends BotonesGenericosSteps {

    private static final Logger log = LoggerFactory.getLogger(TitulosStep.class);
    private FondoComunInversionPage fondoComunInversionPage = new FondoComunInversionPage();
    private  TitulosPage titulosPage = new TitulosPage();
    private BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    private  InversionesPage inversionesPage = new InversionesPage();

    @Step("Seleccionar opción -Mis tenencias en Mercados..")
    public void clickOpcionMisTenenciasEnMercados() {
        titulosPage.getMisTenenciasEnMercadoDeCapitales().click();
    }

    @Step("Click sobre Panel de Precios")
    public void clickPanelDePrecios() {

        titulosPage.getPanelDePrecios().click();
        UtilsGP.fixedWait(3000);
    }

    @Step("Click sobre Mis tenencias")
    public void clickMisTenencias() {
        UtilsGP.fixedWait(3000);
        titulosPage.getMisTenencias().click();
    }

    @Step("Elije algún Instrumento")
    public void clickBtnInstrumentos(String boton) {

        scrollTo(titulosPage.getBtnInstrumentos(boton).getElementBy());
        titulosPage.getBtnInstrumentos(boton).click();
        UtilsGP.fixedWait(3000);
    }

    @Step("Click sobre botón Continuar")
    public void clickBtnContinuar() {
        UtilsGP.fixedWait(3000);
        titulosPage.getBtnInstrumentos("Acciones").click();
    }

    @Step("Click sobre expandir Instrumentos")
    public void clickExpandirInstrumentos( String instrumento) {
        UtilsGP.fixedWait(3000);
        titulosPage.getInstrumentosOpciones("Acciones").click();
    }

    @Step("Click sobre botón Operar")
    public void clickBtnOperar( String fila) {

        scrollTo(titulosPage.getBtnOperar(fila).getElementBy());
        UtilsGP.fixedWait(4000);
        titulosPage.getBtnOperar(fila).click();
        UtilsGP.fixedWait(5000);
    }

    @Step("Click sobre botón Continuar/Confirmar")
    public void clickBtnContinuarConfirmar( String boton) {
        UtilsGP.fixedWait(3000);
        titulosPage.getBtnContinuarConfirmarYVolver(boton).click();
        UtilsGP.fixedWait(10000);
    }

    @Step("Click sobre Gestioná tus inversiones")
    public void clickGestionaTusInversiones() {

        inversionesPage.getBotonNuevaInversion().click();
        UtilsGP.fixedWait(3000);
    }

    @Step("Click sobre Panel Acciones")
    public void clickPanelAcciones() {

        scrollTo(titulosPage.getBtnInstrumentos("Acciones").getElementBy());
        titulosPage.getPanelAcciones().click();
        UtilsGP.fixedWait(3000);
    }

    @Step("Click sobre checkbox Moneda")
    public void clickCheckMoneda(String moneda) {

        scrollTo(titulosPage.getCheckMoneda(moneda).getElementBy());
        titulosPage.getCheckMoneda(moneda).click();
        UtilsGP.fixedWait(2000);
    }



    @Step("Validar información Pantalla de Precios")
    public void validarPantallaPrecios() {
        assertTrue("No se muestra título Mercado de Capitales: ",
                botonesGenericosPage.getTituloPage("Mercado de capitales").isDisplayed());
        assertTrue("No se muestra simulación de perfil: ",
                fondoComunInversionPage.getSimularPerfilInversor().isDisplayed());
        assertTrue("No se muestra título Empezá a gestionar..: ",
                titulosPage.getTituloEmpezaAGestionar().isDisplayed());
        assertTrue("No se muestra título Cómo funciona?: ",
                titulosPage.getTituloComoFunciona().isDisplayed());
        assertTrue("No se muestra título Acceso rápido: ",
                titulosPage.getTituloAccesoRapido().isDisplayed());
        assertTrue("No se muestra título Mis tenencias: ",
                titulosPage.getBtnMisTenencias().isDisplayed());
        assertTrue("No se muestra título Cuenta comitente: ",
                titulosPage.getCtaComitente().isDisplayed());
        assertTrue("No se muestra título Perfil inversor: ",
                titulosPage.getPerfilInversor().isDisplayed());
        scrollTo(titulosPage.getBtnInstrumentos("Acciones").getElementBy());
            assertTrue("No se muestra título Instrumentos: ",
                    titulosPage.getInstrumentos("Instrumentos").isDisplayed());
        String classes = titulosPage.getBtnInstrumentos("Acciones").getAttribute("class");
        assertTrue("No se muestra Card Acciones seleccionada ",
                classes.contains("sc-fjvvzt iYrDcg"));
        assertTrue("No se muestra título Botón CEDEARs: ",
                titulosPage.getBtnInstrumentos("CEDEARs").isDisplayed());
        assertTrue("No se muestra título Bonos públicos: ",
                titulosPage.getBtnInstrumentos("Bonos públicos").isDisplayed());
        assertTrue("No se muestra título Botón Letras: ",
                titulosPage.getBtnInstrumentos("Letras").isDisplayed());
        assertTrue("No se muestra título Botón Obligaciones negociables: ",
                titulosPage.getBtnInstrumentos("Obligaciones negociables").isDisplayed());
        String nombreBtn = titulosPage.getPanelLider().getText();
        assertTrue("No se muestra Panel lider seleccionado: ",
                nombreBtn.contains("Panel lider"));
        assertTrue("No se muestra botón Hoy: ",
                titulosPage.getButtonPlazoYMoneda("24hs").isDisplayed());
        assertTrue("No se muestra botón Hoy: ",
                titulosPage.getButtonPlazoYMoneda("Hoy").isDisplayed());
        assertTrue("No se muestra botón Hoy: ",
                titulosPage.getButtonPlazoYMoneda("Pesos").isDisplayed());
        assertTrue("No se muestra botón Hoy: ",
                titulosPage.getButtonPlazoYMoneda("Dólares").isDisplayed());
        assertTrue("No se muestra tabla Especie: ",
                titulosPage.getTablaEspecie().isDisplayed());
    }

    @Step("Validar información Pantalla Mis Tenencias")
    public void validarPantallaMisTenencias() {

        UtilsGP.fixedWait(6000);

        String textoAccesoRapido = titulosPage.getTituloAccesoRapido().getText();

        assertTrue(
                "No se muestra título Acceso rápido",
                textoAccesoRapido != null && !textoAccesoRapido.trim().isEmpty()
        );
        String textoCtaComitente = titulosPage.getCtaComitente().getText();
        assertTrue(
                "No se muestra título Cuenta comitente: ",
                textoCtaComitente != null && !textoCtaComitente.trim().isEmpty()
        );
        String textoPerfilInversor = titulosPage.getPerfilInversor().getText();
        assertTrue(
                "No se muestra título Perfil inversor: ",
                textoPerfilInversor != null && !textoPerfilInversor.trim().isEmpty()
        );
        scrollTo(titulosPage.getInstrumentos("Mis").getElementBy());

        String textoAcciones = titulosPage.getcomboTitulosMistenencias("Acciones").getText();
        assertTrue(
                "No se muestra título Acciones: ",
                textoAcciones != null && !textoAcciones.trim().isEmpty()
        );
        String textoCedears = titulosPage.getcomboTitulosMistenencias("CEDEARs").getText();
        assertTrue(
                "No se muestra título CEDEARs: ",
                textoCedears != null && !textoCedears.trim().isEmpty()
        );
        String textoBonosPublicos = titulosPage.getcomboTitulosMistenencias("Bonos Públicos").getText();
        assertTrue(
                "No se muestra título Bonos Públicos: ",
                textoBonosPublicos != null && !textoBonosPublicos.trim().isEmpty()
        );
        String textoLetras = titulosPage.getcomboTitulosMistenencias("Letras").getText();
        assertTrue(
                "No se muestra título Letras: ",
                textoLetras != null && !textoLetras.trim().isEmpty()
        );
        String textoObligaciones = titulosPage.getcomboTitulosMistenencias("Obligaciones Negociables (ONs)").getText();
        assertTrue(
                "No se muestra título Obligaciones Negociables (ONs): ",
                textoObligaciones != null && !textoObligaciones.trim().isEmpty()
        );

        String textoHorarioDeMercado = titulosPage.getTextoHorario().getText();
        assertTrue(
                "No se muestra título texto Horario de Mercado: ",
                textoHorarioDeMercado != null && !textoHorarioDeMercado.trim().isEmpty()
        );
        String montoPesos = titulosPage.getTenencaTotalPesos().getText();
        assertTrue(
                "No se muestra Monto en pesos Tenencia Total",
                montoPesos != null && !montoPesos.trim().isEmpty()
        );
    }

    @Step("Validar información Solicitud de Orden sin homologación Dólares ")
    public void validarPantallaSolicitudDeOrdenSinHomologoDolares() {

        List<String> especiePesos = new ArrayList<>();
        List<String> especieDolares = new ArrayList<>();

        scrollTo(titulosPage.getDatoEspecieGrilla
                (String.valueOf(1), "1").getElementBy());

        if (!titulosPage
                .getDatoEspecieGrilla(String.valueOf(1), "1")
                .isDisplayed()) {
            System.out.println("No se encontraron resultados en la grilla");
            return;
        }

        // Completa la lista de Especie en Pesos

        for (int i = 1; i <= 30; i++) {

            if (titulosPage.getDatoEspecieGrilla(String.valueOf(i), "1").isDisplayed()) {

                scrollTo(titulosPage.getDatoEspecieGrilla
                        (String.valueOf(i), "1").getElementBy());

                String datoEspecie = titulosPage.getDatoEspecieGrilla(String.valueOf(i), "1").getText();
                String primerosCuatroPesos =
                        datoEspecie.substring(0, Math.min(4, datoEspecie.length())) + "D";

                especiePesos.add(primerosCuatroPesos);
            } else {
                break;
            }

            if (i == 10 || i == 20 || i == 30) {
                scrollTo(titulosPage.getBtnVerMas().getElementBy());
                wait.until(ExpectedConditions.elementToBeClickable(
                        titulosPage.getBtnVerMas()
                )).click();
            }
        }

        System.out.println("----- ESPECIES PESOS -----");
        for (String p : especiePesos) {
            System.out.println("Pesos: " + p);
        }

        // Completa la lista de Especie en Dólares

        UtilsGP.fixedWait(3000);

        scrollTo(titulosPage.getCheckMoneda("Dólares").getElementBy());

        titulosPage.getCheckMoneda("Dólares").click();

        if (!titulosPage
                .getDatoEspecieGrilla(String.valueOf(1), "1")
                .isDisplayed()) {
            System.out.println("No se encontraron resultados en la grilla");
            return;
        }

        for (int i = 1; i <= 30; i++) {

            if (titulosPage.getDatoEspecieGrilla(String.valueOf(i), "1").isDisplayed()) {

                scrollTo(titulosPage.getDatoEspecieGrilla
                        (String.valueOf(i), "1").getElementBy());

                String datoEspecie = titulosPage.getDatoEspecieGrilla(String.valueOf(i), "1").getText();
                String primerosCuatroDolares = datoEspecie.substring(0, Math.min(5, datoEspecie.length()));
                especieDolares.add(primerosCuatroDolares);
            } else {
                break;
            }

            if (i == 10 || i == 20 || i == 30) {
                scrollTo(titulosPage.getBtnVerMas().getElementBy());
                wait.until(ExpectedConditions.elementToBeClickable(
                        titulosPage.getBtnVerMas()
                )).click();
            }
        }

        System.out.println("----- ESPECIES DOLARES -----");
        for (String d : especieDolares) {
            System.out.println("Dólares: " + d);
        }

        // Busca especie en Pesos sin Homólogo en Dólares

        String primerPesoSinHomologo = null;

        for (String p : especiePesos) {
            if (!especieDolares.contains(p)) {
                primerPesoSinHomologo = p;
                break;
            }
        }

        System.out.println("Primer peso sin homólogo en dólares: " + primerPesoSinHomologo);

        // Vuelve a la grilla en Pesos para seleccionar especie para operar

        UtilsGP.fixedWait(3000);
        scrollTo(titulosPage.getCheckMoneda("Pesos").getElementBy());

        titulosPage.getCheckMoneda("Pesos").click();


        if (!titulosPage
                .getDatoEspecieGrilla(String.valueOf(1), "1")
                .isDisplayed()) {
            System.out.println("No se encontraron resultados en la grilla");
            return;

        }
        for (int i = 1; i <= especiePesos.size(); i++) {

            scrollTo(titulosPage.getDatoEspecieGrilla(String.valueOf(i), "1").getElementBy());

            String datoEspecie = titulosPage.getDatoEspecieGrilla(String.valueOf(i), "1").getText();
            String primerosCuatroPesosGrillaFinal = datoEspecie.substring(0, Math.min(4, datoEspecie.length()));

            String primerosCuatroPesos = primerPesoSinHomologo.substring(0, Math.min(4, primerPesoSinHomologo.length()));

            if (primerosCuatroPesosGrillaFinal.equals(primerosCuatroPesos)) {

                titulosPage.getBtnOperar(String.valueOf(i)).click();
                UtilsGP.fixedWait(4000);

                assertTrue("No se muestra título Solicitud de Orden: ",
                        botonesGenericosPage.getTituloPage("Solicitud de orden").isDisplayed());

                assertTrue("No se muestra título Empezá a gestionar..: ",
                        titulosPage.getTituloEmpezaAGestionar().isDisplayed());

                String porcentajeVariacionDiaria =
                        titulosPage.getDatosPantallaSolicitudDeOrden("Variación diaria").getText();

                String sinPorcentaje = porcentajeVariacionDiaria.replace("%", "").trim();

                int valorNumerico = Integer.parseInt(
                        sinPorcentaje.replace(",", ".").split("\\.")[0]
                );

                String color = titulosPage.getIconoVariacionDiaria()
                        .getCssValue("color");


                String valores = color
                        .replace("rgba(", "")
                        .replace("rgb(", "")
                        .replace(")", "")
                        .trim();

                String[] parts = valores.split(",");

                String soloRG = parts[0].trim() + ", " + parts[1].trim();

                String naranja = "224, 134";
                String negro = "48, 46";
                String verde = "30, 145";
                String riesgoModerado = "MODERADO";
                String riesgoDinamico = "DINAMICO";
                String riesgoConservador = "CONSERVADOR";
                String riesgoSinDatos = "--";

                if (valorNumerico == 0) {
                    assertTrue("Esperaba NEGRO para 0 pero fue: " + color,
                            soloRG.contains("48, 46"));
                } else if (valorNumerico < 0) {
                    assertTrue("Esperaba ROJO/NARANJA para negativo pero fue: " + color,
                            soloRG.contains(naranja));
                } else { // valorNumerico > 0
                    assertTrue("Esperaba VERDE para positivo pero fue: " + color,
                            soloRG.equals("30, 145"));
                }
                String ultimoPrecioOperado = titulosPage.getDatosPantallaSolicitudDeOrden
                        ("Último precio operado").getText();
                assertTrue("Formato inválido de último precio operado: " + ultimoPrecioOperado,
                        ultimoPrecioOperado.matches("^\\$\\s*\\d+,\\d{4}$"));
                String tenenciaValorizada = titulosPage.getDatosPantallaSolicitudDeOrden
                        ("Tenencia valorizada").getText();
                assertTrue("Formato inválido de tenencia valorizada: " + tenenciaValorizada,
                        tenenciaValorizada.matches("^\\$\\s*\\d{1,3}(?:\\.\\d{3})*,\\d{2}$"));
                String saldoDisponible = titulosPage.getDatosPantallaSolicitudDeOrden
                        ("Saldo Disponible").getText();
                assertTrue("Formato inválido de saldo disponible: " + ultimoPrecioOperado,
                        saldoDisponible.matches("^\\$\\s*\\d{1,3}(?:\\.\\d{3})*,\\d{2}$"));

             String riesgo =   titulosPage.getDatosPantallaSolicitudDeOrden("Riesgo").getText();
                System.out.println("Riesgo" + riesgo);

                assertTrue("No coincide Riesgo: ",
                        riesgo.equals(riesgoSinDatos)
                                || riesgo.equals(riesgoDinamico)
                                || riesgo.equals(riesgoModerado)
                                || riesgo.equals(riesgoConservador)
                );
                break;
            }

            if (i == 10 || i == 20 || i == 30) {
                scrollTo(titulosPage.getBtnVerMas().getElementBy());
                wait.until(ExpectedConditions.elementToBeClickable(
                        titulosPage.getBtnVerMas()
                )).click();
            }
        }
    }

    @Step("Validar información Solicitud de Orden con homologación Dólares ")
    public void validarPantallaSolicitudDeOrdenConHomologoDolares() {

        List<String> especiePesos = new ArrayList<>();
        List<String> especieDolares = new ArrayList<>();

        scrollTo(titulosPage.getDatoEspecieGrilla
                (String.valueOf(1), "1").getElementBy());

        if (!titulosPage
                .getDatoEspecieGrilla(String.valueOf(1), "1")
                .isDisplayed()) {
            System.out.println("No se encontraron resultados en la grilla");
            return;
        }

        // Completa la lista de Especie en Pesos

        for (int i = 1; i <= 30; i++) {

            if (titulosPage.getDatoEspecieGrilla(String.valueOf(i), "1").isDisplayed()) {

                scrollTo(titulosPage.getDatoEspecieGrilla
                        (String.valueOf(i), "1").getElementBy());

                String datoEspecie = titulosPage.getDatoEspecieGrilla(String.valueOf(i), "1").getText();
                String primerosCuatroPesos =
                        datoEspecie.substring(0, Math.min(4, datoEspecie.length())) + "D";

                especiePesos.add(primerosCuatroPesos);
            } else {
                break;
            }

            if (i == 10 || i == 20 || i == 30) {
                scrollTo(titulosPage.getBtnVerMas().getElementBy());
                wait.until(ExpectedConditions.elementToBeClickable(
                        titulosPage.getBtnVerMas()
                )).click();
            }
        }

        System.out.println("----- ESPECIES PESOS -----");
        for (String p : especiePesos) {
            System.out.println("Pesos: " + p);
        }

        // Completa la lista de Especie en Dólares

        UtilsGP.fixedWait(3000);

        scrollTo(titulosPage.getCheckMoneda("Dólares").getElementBy());

        titulosPage.getCheckMoneda("Dólares").click();

        if (!titulosPage
                .getDatoEspecieGrilla(String.valueOf(1), "1")
                .isDisplayed()) {
            System.out.println("No se encontraron resultados en la grilla");
            return;
        }

        for (int i = 1; i <= 30; i++) {

            if (titulosPage.getDatoEspecieGrilla(String.valueOf(i), "1").isDisplayed()) {

                scrollTo(titulosPage.getDatoEspecieGrilla
                        (String.valueOf(i), "1").getElementBy());

                String datoEspecie = titulosPage.getDatoEspecieGrilla(String.valueOf(i), "1").getText();
                String primerosCuatroDolares = datoEspecie.substring(0, Math.min(5, datoEspecie.length()));
                especieDolares.add(primerosCuatroDolares);
            } else {
                break;
            }

            if (i == 10 || i == 20 || i == 30) {
                scrollTo(titulosPage.getBtnVerMas().getElementBy());
                wait.until(ExpectedConditions.elementToBeClickable(
                        titulosPage.getBtnVerMas()
                )).click();
            }
        }

        System.out.println("----- ESPECIES DOLARES -----");
        for (String d : especieDolares) {
            System.out.println("Dólares: " + d);
        }

        // Busca especie en Pesos Con Homólogo en Dólares

        String primerPesoConHomologo = null;

        for (String p : especiePesos) {
            if (especieDolares.contains(p)) {
                primerPesoConHomologo = p;
                break;
            }
        }

        System.out.println("Primer peso con homólogo en dólares: " + primerPesoConHomologo);

        // Vuelve a la grilla en Pesos para seleccionar especie para operar

        for (int i = 1; i <= especieDolares.size(); i++) {

            scrollTo(titulosPage.getDatoEspecieGrilla
                        (String.valueOf(i), "1").getElementBy());

            String datoEspecie = titulosPage.getDatoEspecieGrilla(String.valueOf(i), "1").getText();


            if (datoEspecie.equals(primerPesoConHomologo)) {
                titulosPage.getBtnOperar(String.valueOf(i)).click();
                UtilsGP.fixedWait(4000);

                assertTrue("No se muestra título Solicitud de Orden: ",
                        botonesGenericosPage.getTituloPage("Solicitud de orden").isDisplayed());

                assertTrue("No se muestra título Empezá a gestionar..: ",
                        titulosPage.getTituloEmpezaAGestionar().isDisplayed());

                String porcentajeVariacionDiaria =
                        titulosPage.getDatosPantallaSolicitudDeOrden("Variación diaria").getText();

                String sinPorcentaje = porcentajeVariacionDiaria.replace("%", "").trim();

                int valorNumerico = Integer.parseInt(
                        sinPorcentaje.replace(",", ".").split("\\.")[0]
                );

                String color = titulosPage.getIconoVariacionDiaria()
                        .getCssValue("color");


                String valores = color
                        .replace("rgba(", "")
                        .replace("rgb(", "")
                        .replace(")", "")
                        .trim();

                String[] parts = valores.split(",");

                String soloRG = parts[0].trim() + ", " + parts[1].trim();

                String naranja = "224, 134";
                String negro = "48, 46";
                String verde = "30, 145";
                String riesgoModerado = "MODERADO";
                String riesgoDinamico = "DINAMICO";
                String riesgoConservador = "CONSERVADOR";
                String riesgoSinDatos = "--";

                if (valorNumerico == 0) {
                    assertTrue("Esperaba NEGRO para 0 pero fue: " + color,
                            soloRG.contains("48, 46"));
                } else if (valorNumerico < 0) {
                    assertTrue("Esperaba ROJO/NARANJA para negativo pero fue: " + color,
                            soloRG.contains(naranja));
                } else { // valorNumerico > 0
                    assertTrue("Esperaba VERDE para positivo pero fue: " + color,
                            soloRG.equals("30, 145"));
                }
                String ultimoPrecioOperado = titulosPage.getDatosPantallaSolicitudDeOrden
                        ("Último precio operado").getText();
                assertTrue("Formato inválido de último precio operado: " + ultimoPrecioOperado,
                        ultimoPrecioOperado.matches("^\\$\\s*\\d+,\\d{4}$"));
                String tenenciaValorizada = titulosPage.getDatosPantallaSolicitudDeOrden
                        ("Tenencia valorizada").getText();
                assertTrue("Formato inválido de tenencia valorizada: " + tenenciaValorizada,
                        tenenciaValorizada.matches("^\\$\\s*\\d{1,3}(?:\\.\\d{3})*,\\d{2}$"));
                String saldoDisponible = titulosPage.getDatosPantallaSolicitudDeOrden
                        ("Saldo Disponible").getText();
                assertTrue("Formato inválido de saldo disponible: " + ultimoPrecioOperado,
                        saldoDisponible.matches("^\\$\\s*\\d{1,3}(?:\\.\\d{3})*,\\d{2}$"));

                String riesgo =   titulosPage.getDatosPantallaSolicitudDeOrden("Riesgo").getText();
                System.out.println("Riesgo" + riesgo);

                assertTrue("No coincide Riesgo: ",
                        riesgo.equals(riesgoSinDatos)
                                || riesgo.equals(riesgoDinamico)
                                || riesgo.equals(riesgoModerado)
                                || riesgo.equals(riesgoConservador)
                );

            }
        }
    }

    @Step("Validar componente Tab Comprar")
    public void validarTabComprar() {


        assertTrue("No se muestra Tab Comprar ",
                titulosPage.getTabCompraYVenta("COMPRAR").isDisplayed());
        assertTrue("No se muestra texto ¿Cuánto querés comprar? * ",
                titulosPage.getTextoInformativoCuantoQueresComprarVender("¿Cuánto querés comprar? *").isDisplayed());
        assertTrue("Radio button MONTO no se encuentra seleccionado por default ",
                titulosPage.getButtonMontoYNominal("AMOUNT").isSelected());
        assertTrue("Radio button Nominal no se muestra visible ",
                titulosPage.getTituloNominales().isDisplayed());
        assertTrue("Signo pesos no se muestra visible ",
                titulosPage.getMonedaPesos().isDisplayed());
        String valorEsperadoMonto = "00,00";
        String valorEsperadoNominal = "0";

        String monedaYMonto = titulosPage.getInputComprarYVender
                        ("amountTradedForm").getAttribute("placeholder");
        assertTrue("Placeholder Monto incorrecto ",
                monedaYMonto.equals(valorEsperadoMonto));

        scrollTo(titulosPage.getRadioButtonMontoyNominales("NOMINALS").getElementBy());
        titulosPage.getRadioButtonMontoyNominales("NOMINALS").click();

        String monedaYNominal = titulosPage.getInputComprarYVender
                ("nominalTradedForm").getAttribute("placeholder");
        assertTrue("Placeholder Nominal incorrecto ",
                monedaYNominal.equals(valorEsperadoNominal));

        scrollTo(titulosPage.getRadioButtonMontoyNominales("AMOUNT").getElementBy());
        titulosPage.getRadioButtonMontoyNominales("AMOUNT").click();

        String compraMinimaEsperada = "La compra mínima debe ser de";

        titulosPage.getInputComprarYVender
                        ("amountTradedForm").sendKeys("1");
        String compraMinima = titulosPage.getTextoInformativoMontoNominal
                ("La compra mínima debe ser de").getText();

        assertTrue("Mensaje de compra mínima monto incorrecto ",
                compraMinima.contains(compraMinimaEsperada));

        titulosPage.getRadioButtonMontoyNominales("NOMINALS").click();

        titulosPage.getInputComprarYVender
                ("nominalTradedForm").sendKeys("10000");

        assertTrue("Mensaje de compra mínima nominales incorrecto ",
                titulosPage.getTextoInformativoMontoNominal
                        ("El estimado a debitar supera el saldo disponible").isDisplayed());
    }

    @Step("Validar componente Tab Vender")
    public void validarTabVender() {


        scrollTo( titulosPage.getTabCompraYVenta("VENDER").getElementBy());

        assertTrue("No se muestra Tab Vender ",
                titulosPage.getTabCompraYVenta("VENDER").isDisplayed());
        titulosPage.getTabCompraYVenta("VENDER").click();
        UtilsGP.fixedWait(4000);
        scrollTo( titulosPage.getTabCompraYVenta("VENDER").getElementBy());
        assertTrue("No se muestra texto ¿Cuánto querés vender? * ",
                titulosPage.getTextoInformativoCuantoQueresComprarVender("").isDisplayed());
        assertTrue("Radio button MONTO no se encuentra seleccionado por default ",
                titulosPage.getButtonMontoYNominal("AMOUNT").isSelected());
        assertTrue("Radio button Nominal no se muestra visible ",
                titulosPage.getTituloNominales().isDisplayed());
        scrollTo( titulosPage.getMonedaPesos().getElementBy());
        assertTrue("Signo pesos no se muestra visible ",
                titulosPage.getMonedaPesos().isPresent());
        String valorEsperadoMonto = "00,00";
        String valorEsperadoNominal = "0";


        String monedaYMonto = titulosPage.getInputComprarYVender
                ("amountTradedForm").getAttribute("placeholder");

        assertTrue("Placeholder Monto incorrecto ",
                monedaYMonto.equals(valorEsperadoMonto));

        scrollTo(titulosPage.getRadioButtonMontoyNominales("NOMINALS").getElementBy());
        titulosPage.getRadioButtonMontoyNominales("NOMINALS").click();

        String monedaYNominal = titulosPage.getInputComprarYVender
                ("nominalTradedForm").getAttribute("placeholder");
        assertTrue("Placeholder Nominal incorrecto ",
                monedaYNominal.equals(valorEsperadoNominal));

        scrollTo(titulosPage.getRadioButtonMontoyNominales("AMOUNT").getElementBy());
        titulosPage.getRadioButtonMontoyNominales("AMOUNT").click();

        String mensajeInformativoMontoSuperado = "El monto ingresado supera la tenencia disponible para la venta";

        titulosPage.getInputComprarYVender
                ("amountTradedForm").sendKeys("1");
        String montoSuperado = titulosPage.getTextoInformativoMontoNominal
                ("El monto ingresado supera la tenencia disponible para la venta").getText();

        assertTrue("Mensaje de monto supera la tenencia no se encuentra visible ",
                montoSuperado.contains(mensajeInformativoMontoSuperado));

        titulosPage.getRadioButtonMontoyNominales("NOMINALS").click();

        titulosPage.getInputComprarYVender
                ("nominalTradedForm").sendKeys("10000");

        assertTrue("Mensaje nominal superado no visible ",
                titulosPage.getTextoInformativoMontoNominal
                        ("Los nominales ingresados, superan la tenencia disponible para la venta").isDisplayed());

        assertTrue("Estimado a acreditar no visible ",
                titulosPage.getDetalleDeOrden("Estimado a acreditar").isDisplayed());
        assertTrue("Comisiones e impuestos estimados no visible ",
                titulosPage.getDetalleDeOrden("Comisiones e impuestos estimados").isDisplayed());
        assertTrue("Estimado de la orden no visible ",
                titulosPage.getDetalleDeOrden("Estimado de la orden").isDisplayed());
    }

    @Step("Validar cantidad válida de Nominales")
    public void validarCantidadNominales() {


        scrollTo(titulosPage.getLimite().getElementBy());

        String montoLimite = titulosPage.getLimite().getText();


        String limpioMontoLimite = montoLimite
                .replaceAll("[^0-9,\\.]", "")  // deja solo dígitos y separadores
                .replace(".", "");             // saca separador de miles

        // cortar decimales (todo lo que esté después de la coma o punto)
        limpioMontoLimite  = limpioMontoLimite .split("[,\\.]")[0];

        int limpioMontoLimiteInt = Integer.parseInt(limpioMontoLimite); // 58175

        int estimadoDeLaOrden = limpioMontoLimiteInt  * 10;

        String montoConDecimales = estimadoDeLaOrden  + ",00";

        scrollTo(titulosPage.getRadioButtonMontoyNominales("NOMINALS").getElementBy());

        titulosPage.getRadioButtonMontoyNominales("NOMINALS").click();

        titulosPage.getInputComprarYVender
                ("nominalTradedForm").sendKeys("10");

        String estimadoDeLaOrdenDetalle = titulosPage.getDetalleDeOrden("Estimado de la orden").getText();

        String limpioEstimadoDeLaOrdenDetalle = estimadoDeLaOrdenDetalle
                .replace("$", "")
                .replace(".", "")
                .trim();

        assertTrue(
                "Valor estimado de la orden incorrecto",
                limpioEstimadoDeLaOrdenDetalle.replace(",", ".")
                        .equals(montoConDecimales.replace(",", "."))
        );

        System.out.println("estimado " + limpioEstimadoDeLaOrdenDetalle + montoConDecimales);


        double comisionesEImpuestoEstimados = estimadoDeLaOrden * 0.01;

        BigDecimal comisiones = BigDecimal
                .valueOf(comisionesEImpuestoEstimados)
                .setScale(2, RoundingMode.HALF_UP);

        String comisionesEImpuestosEstimadosDetalle = titulosPage.getDetalleDeOrden("Comisiones e impuestos estimados").getText();

        String limpioComisionesEImpuestosEstimadosDetalle  = comisionesEImpuestosEstimadosDetalle
                .replace("$", "")
                .replace(".", "")
                .trim();


        BigDecimal comisionesBD = new BigDecimal(
                limpioComisionesEImpuestosEstimadosDetalle.replace(",", ".")
        );


        assertTrue("Valor comisiones e impuestos estimados",
                comisionesBD.compareTo(comisiones) == 0);


        BigDecimal estimadoDeLaOrdenBD = new BigDecimal(
                limpioEstimadoDeLaOrdenDetalle.replace(",", ".")
        );

        // add operador de suma para variable tipo BigDecimal
        BigDecimal estimadoADebitarSuma = estimadoDeLaOrdenBD.add(comisionesBD);

        String estimadoADebitar = titulosPage.getDetalleDeOrden("Estimado a debitar").getText();

        String limpioEstimadoADebitar  = estimadoADebitar
                .replace("$", "")
                .replace(".", "")
                .trim();

        BigDecimal estimadoADebitarBD = new BigDecimal(
                limpioEstimadoADebitar .replace(",", ".")
        );

        assertTrue("Valor comisiones e impuestos estimados",
                estimadoADebitarBD.compareTo(estimadoADebitarSuma) == 0);

        String maxLength = titulosPage.getInputComprarYVender
                ("nominalTradedForm").getAttribute("maxlength");

        assertEquals("El maxlength es incorrecto", "13", maxLength);
    }

    @Step("Validar comportamiento de ingreso de Monto -Compra-")
    public void validarIngresoMontoComprar() {

        scrollTo(titulosPage.getRadioButtonMontoyNominales("AMOUNT").getElementBy());

        titulosPage.getRadioButtonMontoyNominales("AMOUNT").click();

        titulosPage.getInputComprarYVender
                ("amountTradedForm").sendKeys("10000");
    }

    @Step("Validar pantalla Confirmación de Orden Compra")
    public void validarPantallaConfirmacionDeOrdenCompra() {

        assertTrue("Título Confirmación de Orden no visible ",
                botonesGenericosPage.getTituloPage("Confirmación de Orden").isDisplayed());
        assertTrue("Especie no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Nombre de especie")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Nombre de especie").isDisplayed());
        assertTrue("Riesgo no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Riesgo")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Riesgo").isDisplayed());


        String ctaComitenteTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Cuenta comitente")
                .getText()
                .trim()
                .replaceAll("\\D", ""); // deja solo dígitos

        int ctaComitente = Integer.parseInt(ctaComitenteTexto);

        assertTrue(
                "Cuenta comitente no visible o es 0",
                !ctaComitenteTexto.isEmpty() && ctaComitente  > 0
        );
        assertTrue("Fecha de liquidación no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Fecha de liquidación")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Fecha de liquidación").isDisplayed());
        String estiamadoDeLaOrdenTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Estimado de la Orden")
                .getText()
                .trim();

        String estimadoDeLaOrdenLimpio = estiamadoDeLaOrdenTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();


        int estiamadoDeLaOrden = Integer.parseInt(estimadoDeLaOrdenLimpio);

        assertTrue(
                "Estimado de la Orden no visible o es 0",
                !estiamadoDeLaOrdenTexto.isEmpty() && estiamadoDeLaOrden   > 0
        );
        String comisionesEImpuestosEstimadosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Estimado de la Orden")
                .getText()
                .trim();

        String comisionesEImpuestosEstimadosLimpio = comisionesEImpuestosEstimadosTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();

        int comisionesEImpuestosEstimados = Integer.parseInt(comisionesEImpuestosEstimadosLimpio);

        assertTrue(
                "Comisiones e impuestos estimados no visible o es 0",
                !comisionesEImpuestosEstimadosTexto.isEmpty() && comisionesEImpuestosEstimados  > 0
        );
        String estimadosADebitarTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Estimado a debitar")
                .getText()
                .trim();

        String estimadosADebitarLimpio = estimadosADebitarTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();

        int estimadosADebitar = Integer.parseInt(estimadosADebitarLimpio);

        assertTrue(
                "Estimados a debitar  no visible o es 0",
                !estimadosADebitarTexto.isEmpty() && estimadosADebitar  > 0
        );
        String nominalesEstimadosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Nominales estimados")
                .getText()
                .trim();

        int nominalesEstimados = Integer.parseInt(nominalesEstimadosTexto);

        assertTrue(
                "Estimados a debitar  no visible o es 0",
                !nominalesEstimadosTexto.isEmpty() && nominalesEstimados  > 0
        );
        String precioEstimadoDeMercadoTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Precio estimado de mercado")
                .getText()
                .trim();

        String precioEstimadoDeMercadoLimpio = precioEstimadoDeMercadoTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();

        int precioEstimadoDeMercado = Integer.parseInt(precioEstimadoDeMercadoLimpio);

        assertTrue(
                "Precio estimado de mercado no visible o es 0",
                !precioEstimadoDeMercadoTexto.isEmpty() && precioEstimadoDeMercado  > 0
        );

        assertTrue("Moneda no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Moneda")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Moneda").isDisplayed());

                scrollTo(titulosPage.getCheckBoxConfirmacionDeOrden().getElementBy());
                UtilsGP.fixedWait(4000);
                titulosPage.getCheckBoxConfirmacionDeOrden().click();
                titulosPage.getCheckBoxConfirmacionDeOrdenTerminosYCondiciones().click();

    }

    @Step("Validar pantalla Confirmación de Orden Compra Dólares")
    public void validarPantallaConfirmacionDeOrdenCompraDolares() {

        assertTrue("Título Solicitud de compra no visible ",
                botonesGenericosPage.getTituloPage("Solicitud de compra").isDisplayed());
        assertTrue("Especie no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Nombre de especie")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Nombre de especie").isDisplayed());
        assertTrue("Riesgo no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Riesgo")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Riesgo").isDisplayed());

        String ctaComitenteTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Cuenta comitente")
                .getText()
                .trim()
                .replaceAll("\\D", ""); // deja solo dígitos

        int ctaComitente = Integer.parseInt(ctaComitenteTexto);

        assertTrue(
                "Cuenta comitente no visible o es 0",
                !ctaComitenteTexto.isEmpty() && ctaComitente  > 0
        );

        String cuentaDebitoEnPesosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Cuenta débito en pesos")
                .getText()
                .trim();

        String cuentaDebitoEnPesosLimpio = cuentaDebitoEnPesosTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .replace("CA", "")
                .trim();

        long cuentaDebitoEnPesos = Long.parseLong(cuentaDebitoEnPesosLimpio);

        assertTrue(
                "Cuenta débito en pesos no visible o es 0",
                !cuentaDebitoEnPesosTexto.isEmpty() && cuentaDebitoEnPesos  > 0
        );

        String cuentaDebitoEnDolaresTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Cuenta débito en dólares")
                .getText()
                .trim();

        String cuentaDebitoEnDolaresLimpio = cuentaDebitoEnDolaresTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .replace("CA", "")
                .replace("USD", "")
                .trim();

        long cuentaDebitoEnDolares = Long.parseLong(cuentaDebitoEnDolaresLimpio);

        assertTrue(
                "Cuenta débito en dólares no visible o es 0",
                !cuentaDebitoEnDolaresTexto.isEmpty() && cuentaDebitoEnDolares > 0
        );

        assertTrue("Fecha de liquidación no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Fecha de liquidación")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Fecha de liquidación").isDisplayed());


        String comisionesEImpuestosEstimadosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Comisiones e impuestos estimados")
                .getText()
                .trim();

        String comisionesEImpuestosEstimadosLimpio = comisionesEImpuestosEstimadosTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();

        int comisionesEImpuestosEstimados = Integer.parseInt(comisionesEImpuestosEstimadosLimpio);

        assertTrue(
                "Comisiones e impuestos estimados no visible o es 0",
                !comisionesEImpuestosEstimadosTexto.isEmpty() && comisionesEImpuestosEstimados  > 0
        );

        String estimadosAADebitarTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Estimado a debitar")
                .getText()
                .trim();

        String estimadosAADebitarLimpio = estimadosAADebitarTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .replace("USD", "")
                .trim();

        int estimadosAADebitar = Integer.parseInt(estimadosAADebitarLimpio);

        assertTrue(
                "Estimados a acreditar  no visible o es 0",
                !estimadosAADebitarTexto.isEmpty() && estimadosAADebitar  > 0
        );

        String nominalesEstimadosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Nominales estimados")
                .getText()
                .trim();

        int nominalesEstimados = Integer.parseInt(nominalesEstimadosTexto);

        assertTrue(
                "Nominales estimados      no visible o es 0",
                !nominalesEstimadosTexto.isEmpty() && nominalesEstimados  > 0
        );
        String precioEstimadoDeMercadoTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Precio estimado de mercado")
                .getText()
                .trim();

        String precioEstimadoDeMercadoLimpio = precioEstimadoDeMercadoTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .replace("USD", "")
                .trim();

        int precioEstimadoDeMercado = Integer.parseInt(precioEstimadoDeMercadoLimpio);

        assertTrue(
                "Precio estimado de mercado no visible o es 0",
                !precioEstimadoDeMercadoTexto.isEmpty() && precioEstimadoDeMercado  > 0
        );

        assertTrue("Moneda no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Moneda")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Moneda").isDisplayed());

        scrollTo(titulosPage.getCheckBoxConfirmacionDeOrden().getElementBy());
        UtilsGP.fixedWait(4000);
        titulosPage.getCheckBoxConfirmacionDeOrden().click();
        titulosPage.getCheckBoxConfirmacionDeOrdenTerminosYCondiciones().click();
    }

    @Step("Validar Confirmación de Orden realizada")
    public void validarConfirmacionRealizada() {
        UtilsGP.fixedWait(3000);
        botonesGenericosPage.getTituloPage("Tu solicitud se realizó con éxito").isDisplayed();
    }

    @Step("Validar comportamiento de ingreso de Monto -Venta-")
    public void validarIngresoMontoVenta() {


        scrollTo(titulosPage.getTabCompraYVenta("VENDER").getElementBy());

        assertTrue("No se muestra Tab Vender ",
                titulosPage.getTabCompraYVenta("VENDER").isDisplayed());
        titulosPage.getTabCompraYVenta("VENDER").click();
        UtilsGP.fixedWait(4000);
        scrollTo(titulosPage.getTabCompraYVenta("VENDER").getElementBy());

        titulosPage.getInputComprarYVender
                ("amountTradedForm").sendKeys("123");
        UtilsGP.fixedWait(3000);

        if(titulosPage.getTextoInformativoMontoNominal
                ("El monto ingresado").isDisplayed()){
            String MensajeMontoTenencia = titulosPage.getTextoInformativoMontoNominal
                    ("El monto ingresado").getText();
            Assert.fail("Mensaje de error: " + MensajeMontoTenencia);
        }
    }

    @Step("Validar pantalla Confirmación de Orden Venta Dólares")
    public void validarPantallaConfirmacionDeOrdenVentaDolares() {

        assertTrue("Título Solicitud de Venta no visible ",
                botonesGenericosPage.getTituloPage("Solicitud de Venta").isDisplayed());
        assertTrue("Especie no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Nombre de especie")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Nombre de especie").isDisplayed());
        assertTrue("Riesgo no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Riesgo")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Riesgo").isDisplayed());

        String ctaComitenteTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Cuenta comitente")
                .getText()
                .trim()
                .replaceAll("\\D", ""); // deja solo dígitos

        int ctaComitente = Integer.parseInt(ctaComitenteTexto);

        assertTrue(
                "Cuenta comitente no visible o es 0",
                !ctaComitenteTexto.isEmpty() && ctaComitente  > 0
        );

        String cuentaDebitoEnPesosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Cuenta débito en pesos")
                .getText()
                .trim();

        String cuentaDebitoEnPesosLimpio = cuentaDebitoEnPesosTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .replace("CA", "")
                .trim();

        long cuentaDebitoEnPesos = Long.parseLong(cuentaDebitoEnPesosLimpio);

        assertTrue(
                "Cuenta débito en pesos no visible o es 0",
                !cuentaDebitoEnPesosTexto.isEmpty() && cuentaDebitoEnPesos  > 0
        );

        String cuentaDebitoEnDolaresTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Cuenta destino en dólares")
                .getText()
                .trim();

        String cuentaDebitoEnDolaresLimpio = cuentaDebitoEnDolaresTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .replace("CA", "")
                .replace("USD", "")
                .trim();

        long cuentaDebitoEnDolares = Long.parseLong(cuentaDebitoEnDolaresLimpio);

        assertTrue(
                "Cuenta débito en dólares no visible o es 0",
                !cuentaDebitoEnDolaresTexto.isEmpty() && cuentaDebitoEnDolares > 0
        );


        assertTrue("Fecha de liquidación no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Fecha de liquidación")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Fecha de liquidación").isDisplayed());

        String comisionesEImpuestosEstimadosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Comisiones e impuestos estimados")
                .getText()
                .trim();

        String comisionesEImpuestosEstimadosLimpio = comisionesEImpuestosEstimadosTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();

        int comisionesEImpuestosEstimados = Integer.parseInt(comisionesEImpuestosEstimadosLimpio);

        assertTrue(
                "Comisiones e impuestos estimados no visible o es 0",
                !comisionesEImpuestosEstimadosTexto.isEmpty() && comisionesEImpuestosEstimados  > 0
        );
        String estimadoAAcreditarTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Estimado a acreditar")
                .getText()
                .trim();

        String estimadoAAcreditarLimpio = estimadoAAcreditarTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .replace("USD", "")
                .trim();

        int estimadoAAcreditar = Integer.parseInt(estimadoAAcreditarLimpio);

        assertTrue(
                "Estimado a debitar no visible o es 0",
                !estimadoAAcreditarTexto.isEmpty() && estimadoAAcreditar  > 0
        );

        String nominalesEstimadosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Nominales estimados")
                .getText()
                .trim();

        int nominalesEstimados = Integer.parseInt(nominalesEstimadosTexto);

        assertTrue(
                "Estimados a debitar  no visible o es 0",
                !nominalesEstimadosTexto.isEmpty() && nominalesEstimados  > 0
        );
        String precioEstimadoDeMercadoTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Precio estimado de mercado")
                .getText()
                .trim();

        String precioEstimadoDeMercadoLimpio = precioEstimadoDeMercadoTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .replace("USD", "")
                .trim();

        int precioEstimadoDeMercado = Integer.parseInt(precioEstimadoDeMercadoLimpio);

        assertTrue(
                "Precio estimado de mercado no visible o es 0",
                !precioEstimadoDeMercadoTexto.isEmpty() && precioEstimadoDeMercado  > 0
        );

        assertTrue("Moneda no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Moneda")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Moneda").isDisplayed());


        scrollTo(titulosPage.getCheckBoxConfirmacionDeOrdenTerminosYCondiciones().getElementBy());
        UtilsGP.fixedWait(4000);

        assertNotNull(
                titulosPage.getBtnContinuarConfirmarYVolver("Confirmar").getAttribute("disabled"),
                "El botón Confirmar debería estar deshabilitado"
        );

        titulosPage.getCheckBoxConfirmacionDeOrdenTerminosYCondiciones().click();
    }

    @Step("Validar pantalla Confirmación de Orden Venta")
    public void validarPantallaConfirmacionDeOrdenVenta() {

        assertTrue("Título Solicitud de Venta no visible ",
                botonesGenericosPage.getTituloPage("Solicitud de Venta").isDisplayed());
        assertTrue("Especie no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Nombre de especie")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Nombre de especie").isDisplayed());
        assertTrue("Riesgo no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Riesgo")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Riesgo").isDisplayed());

        String ctaComitenteTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Cuenta comitente")
                .getText()
                .trim()
                .replaceAll("\\D", ""); // deja solo dígitos

        int ctaComitente = Integer.parseInt(ctaComitenteTexto);

        assertTrue(
                "Cuenta comitente no visible o es 0",
                !ctaComitenteTexto.isEmpty() && ctaComitente  > 0
        );
        assertTrue("Fecha de liquidación no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Fecha de liquidación")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Fecha de liquidación").isDisplayed());
        String estiamadoDeLaOrdenTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Estimado de la Orden")
                .getText()
                .trim();

        String estimadoDeLaOrdenLimpio = estiamadoDeLaOrdenTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .replace("USD", "")
                .trim();


        int estiamadoDeLaOrden = Integer.parseInt(estimadoDeLaOrdenLimpio);

        assertTrue(
                "Estimado de la Orden no visible o es 0",
                !estiamadoDeLaOrdenTexto.isEmpty() && estiamadoDeLaOrden   > 0
        );
        String comisionesEImpuestosEstimadosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Comisiones e impuestos estimados")
                .getText()
                .trim();

        String comisionesEImpuestosEstimadosLimpio = comisionesEImpuestosEstimadosTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .replace("USD", "")
                .trim();

        int comisionesEImpuestosEstimados = Integer.parseInt(comisionesEImpuestosEstimadosLimpio);

        assertTrue(
                "Comisiones e impuestos estimados no visible o es 0",
                !comisionesEImpuestosEstimadosTexto.isEmpty() && comisionesEImpuestosEstimados  > 0
        );
        String estimadosAAcreditarTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Estimado a acreditar")
                .getText()
                .trim();

        String estimadosAAcreditarLimpio = estimadosAAcreditarTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .replace("USD", "")
                .trim();

        int estimadosAAcreditar = Integer.parseInt(estimadosAAcreditarLimpio);

        assertTrue(
                "Estimados a debitar  no visible o es 0",
                !estimadosAAcreditarTexto.isEmpty() && estimadosAAcreditar  > 0
        );
        String nominalesEstimadosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Nominales estimados")
                .getText()
                .trim();

        int nominalesEstimados = Integer.parseInt(nominalesEstimadosTexto);

        assertTrue(
                "Estimados a debitar  no visible o es 0",
                !nominalesEstimadosTexto.isEmpty() && nominalesEstimados  > 0
        );
        String precioEstimadoDeMercadoTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Precio estimado de mercado")
                .getText()
                .trim();

        String precioEstimadoDeMercadoLimpio = precioEstimadoDeMercadoTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .replace("USD", "")
                .trim();

        int precioEstimadoDeMercado = Integer.parseInt(precioEstimadoDeMercadoLimpio);

        assertTrue(
                "Precio estimado de mercado no visible o es 0",
                !precioEstimadoDeMercadoTexto.isEmpty() && precioEstimadoDeMercado  > 0
        );

        assertTrue("Moneda no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Moneda")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Moneda").isDisplayed());


        scrollTo(titulosPage.getCheckBoxConfirmacionDeOrdenTerminosYCondiciones().getElementBy());
        UtilsGP.fixedWait(4000);

        assertNotNull(
                titulosPage.getBtnContinuarConfirmarYVolver("Confirmar").getAttribute("disabled"),
                "El botón Confirmar debería estar deshabilitado"
        );

        titulosPage.getCheckBoxConfirmacionDeOrdenTerminosYCondiciones().click();
    }

    @Step("Validar ticket Solicitud de Orden Compra exitosa")
    public void validarTicketCompraExitosa() {

        assertTrue("Título Solicitud de compra no visible ",
                botonesGenericosPage.getTituloPage("Solicitud de compra").isDisplayed());

        String nroDeOperacion = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Número de operación")
                .getText()
                .trim();

        assertTrue(
                "Número de operación no visible o vacío",
                nroDeOperacion != null && !nroDeOperacion.isEmpty()
        );

        assertTrue("Fecha no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Fecha")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Fecha").isDisplayed());

        String nroDeOrden = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Número de orden")
                .getText()
                .trim();

        assertTrue(
                "Número de orden no visible o vacío",
                nroDeOrden != null && !nroDeOrden.isEmpty()
        );
        assertTrue("Especie no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Nombre de especie")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Nombre de especie").isDisplayed());
        assertTrue("Riesgo no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Riesgo")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Riesgo").isDisplayed());
        String ctaComitenteTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Cuenta comitente")
                .getText()
                .trim()
                .replaceAll("\\D", ""); // deja solo dígitos

        int ctaComitente = Integer.parseInt(ctaComitenteTexto);

        assertTrue(
                "Cuenta comitente no visible o es 0",
                !ctaComitenteTexto.isEmpty() && ctaComitente  > 0
        );

        String cuentaDebitOEnPesosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Cuenta débito en pesos")
                .getText()
                .trim()
                .replaceAll("\\D", ""); // deja solo dígitos

        long cuentaDebitOEnPesos = Long.parseLong(cuentaDebitOEnPesosTexto);

        assertTrue(
                "Cuenta débito en pesos no visible o es 0",
                !cuentaDebitOEnPesosTexto.isEmpty() && cuentaDebitOEnPesos  > 0
        );

        String comisionesEImpuestosEstimadosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Comisiones e impuestos estimados")
                .getText()
                .trim();

        String comisionesEImpuestosEstimadosLimpio = comisionesEImpuestosEstimadosTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();

        int comisionesEImpuestosEstimados = Integer.parseInt(comisionesEImpuestosEstimadosLimpio);

        assertTrue(
                "Comisiones e impuestos estimados no visible o es 0",
                !comisionesEImpuestosEstimadosTexto.isEmpty() && comisionesEImpuestosEstimados  > 0
        );

        String estimadoDeLaOrdenTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Estimado de la orden")
                .getText()
                .trim();

        String estimadoDeLaOrdenLimpio = estimadoDeLaOrdenTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();

        int estimadoDeLaOrden = Integer.parseInt(estimadoDeLaOrdenLimpio);

        assertTrue(
                "Estimados a debitar  no visible o es 0",
                !estimadoDeLaOrdenTexto.isEmpty() && estimadoDeLaOrden  > 0
        );

        String estimadosADebitarTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Estimado a debitar")
                .getText()
                .trim();

        String estimadosADebitarLimpio = estimadosADebitarTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();

        int estimadosADebitar = Integer.parseInt(estimadosADebitarLimpio);

        assertTrue(
                "Estimados a debitar  no visible o es 0",
                !estimadosADebitarTexto.isEmpty() && estimadosADebitar  > 0
        );

        String nominalesEstimadosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Nominales estimados")
                .getText()
                .trim();

        int nominalesEstimados = Integer.parseInt(nominalesEstimadosTexto);

        assertTrue(
                "Estimados a debitar  no visible o es 0",
                !nominalesEstimadosTexto.isEmpty() && nominalesEstimados  > 0
        );

        String precioEstimadoDeMercadoTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Precio estimado de mercado")
                .getText()
                .trim();

        String precioEstimadoDeMercadoLimpio = precioEstimadoDeMercadoTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();

        int precioEstimadoDeMercado = Integer.parseInt(precioEstimadoDeMercadoLimpio);

        assertTrue(
                "Precio estimado de mercado no visible o es 0",
                !precioEstimadoDeMercadoTexto.isEmpty() && precioEstimadoDeMercado  > 0
        );

        assertTrue("Moneda no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Moneda")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Moneda").isDisplayed());


        assertTrue("Texto informativo -Esta solicitud de orden está pendiente.. no visible ",
                !titulosPage.getInformacionSolicitudPendiente("Moneda")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Esta solicitud de orden está pendiente de ejecución, podrás ver su estado en la pantalla de Órdenes y movimientos.").isDisplayed());

        assertTrue("Inversión no coincide con perfil de inversor no visible",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Inversión no coincide con perfil de inversor")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Inversión no coincide con perfil de inversor").isDisplayed());

        assertTrue("S.E.U.O no visible",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("S.E.U.O")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("S.E.U.O").isDisplayed());

        scrollTo(titulosPage.getBtnContinuarConfirmarYVolver("Volver").getElementBy());

        UtilsGP.fixedWait(3000);
        titulosPage.getBtnContinuarConfirmarYVolver("Volver").click();
    }

    @Step("Validar ticket Solicitud de Orden Compra exitosa Dólares")
    public void  validarTicketCompraExitosaDolares() {

        assertTrue("Título Solicitud de compra no visible ",
                botonesGenericosPage.getTituloPage("Solicitud de compra").isDisplayed());

        String nroDeOperacion = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Número de operación")
                .getText()
                .trim();

        assertTrue(
                "Número de operación no visible o vacío",
                nroDeOperacion != null && !nroDeOperacion.isEmpty()
        );

        assertTrue("Fecha no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Fecha")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Fecha").isDisplayed());

        String nroDeOrden = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Número de orden")
                .getText()
                .trim();

        assertTrue(
                "Número de orden no visible o vacío",
                nroDeOrden != null && !nroDeOrden.isEmpty()
        );
        assertTrue("Especie no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Nombre de especie")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Nombre de especie").isDisplayed());
        assertTrue("Riesgo no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Riesgo")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Riesgo").isDisplayed());
        String ctaComitenteTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Cuenta comitente")
                .getText()
                .trim()
                .replaceAll("\\D", ""); // deja solo dígitos

        int ctaComitente = Integer.parseInt(ctaComitenteTexto);

        assertTrue(
                "Cuenta comitente no visible o es 0",
                !ctaComitenteTexto.isEmpty() && ctaComitente  > 0
        );

        String cuentaDebitoEnPesosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Cuenta débito en pesos")
                .getText()
                .trim();

        String cuentaDebitoEnPesosLimpio = cuentaDebitoEnPesosTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .replace("CA", "")
                .trim();

        long cuentaDebitoEnPesos = Long.parseLong(cuentaDebitoEnPesosLimpio);

        assertTrue(
                "Cuenta débito en pesos no visible o es 0",
                !cuentaDebitoEnPesosTexto.isEmpty() && cuentaDebitoEnPesos  > 0
        );

        String cuentaDebitoEnDolaresTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Cuenta débito en dólares")
                .getText()
                .trim();

        String cuentaDebitoEnDolaresLimpio = cuentaDebitoEnDolaresTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .replace("CA", "")
                .replace("USD", "")
                .trim();

        long cuentaDebitoEnDolares = Long.parseLong(cuentaDebitoEnDolaresLimpio);

        assertTrue(
                "Cuenta destino en dólares no visible o es 0",
                !cuentaDebitoEnDolaresTexto.isEmpty() && cuentaDebitoEnDolares > 0
        );

        assertTrue("Fecha de Liquidación no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Fecha de liquidación")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Fecha de liquidación").isDisplayed());

        String comisionesEImpuestosEstimadosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Comisiones e impuestos estimados")
                .getText()
                .trim();

        String comisionesEImpuestosEstimadosLimpio = comisionesEImpuestosEstimadosTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();

        int comisionesEImpuestosEstimados = Integer.parseInt(comisionesEImpuestosEstimadosLimpio);

        assertTrue(
                "Comisiones e impuestos estimados no visible o es 0",
                !comisionesEImpuestosEstimadosTexto.isEmpty() && comisionesEImpuestosEstimados  > 0
        );


        String estimadoADebitarTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Estimado a debitar")
                .getText()
                .trim();

        String estimadoADebitarLimpio = estimadoADebitarTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .replace("USD", "")
                .trim();

        int estimadoADebitar = Integer.parseInt(estimadoADebitarLimpio);

        assertTrue(
                "Estimado a debitar no visible o es 0",
                !estimadoADebitarTexto.isEmpty() && estimadoADebitar  > 0
        );

        String nominalesEstimadosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Nominales estimados")
                .getText()
                .trim();

        int nominalesEstimados = Integer.parseInt(nominalesEstimadosTexto);

        assertTrue(
                "Estimados a debitar  no visible o es 0",
                !nominalesEstimadosTexto.isEmpty() && nominalesEstimados  > 0
        );

        String precioEstimadoDeMercadoTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Precio estimado de mercado")
                .getText()
                .trim();

        String precioEstimadoDeMercadoLimpio = precioEstimadoDeMercadoTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .replace("USD", "")
                .trim();

        int precioEstimadoDeMercado = Integer.parseInt(precioEstimadoDeMercadoLimpio);

        assertTrue(
                "Precio estimado de mercado no visible o es 0",
                !precioEstimadoDeMercadoTexto.isEmpty() && precioEstimadoDeMercado  > 0
        );

        assertTrue("Moneda no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Moneda")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Moneda").isDisplayed());


        assertTrue("Texto informativo -Esta solicitud de orden está pendiente.. no visible ",
                !titulosPage.getInformacionSolicitudPendiente("Moneda")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Esta solicitud de orden está pendiente de ejecución, podrás ver su estado en la pantalla de Órdenes y movimientos.").isDisplayed());

        assertTrue("Inversión no coincide con perfil de inversor no visible",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Inversión no coincide con perfil de inversor")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Inversión no coincide con perfil de inversor").isDisplayed());

        assertTrue("S.E.U.O no visible",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("S.E.U.O")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("S.E.U.O").isDisplayed());

        scrollTo(titulosPage.getBtnContinuarConfirmarYVolver("Volver").getElementBy());

        UtilsGP.fixedWait(3000);
        titulosPage.getBtnContinuarConfirmarYVolver("Volver").click();
    }

    @Step("Validar ticket Solicitud de Orden Venta exitosa Dólares")
    public void validarTicketVentaExitosaDolares() {

        assertTrue("Título Solicitud de venta no visible ",
                botonesGenericosPage.getTituloPage("Solicitud de venta").isDisplayed());

        String nroDeOperacion = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Número de operación")
                .getText()
                .trim();

        assertTrue(
                "Número de operación no visible o vacío",
                nroDeOperacion != null && !nroDeOperacion.isEmpty()
        );

        assertTrue("Fecha no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Fecha")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Fecha").isDisplayed());

        String nroDeOrden = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Número de orden")
                .getText()
                .trim();

        assertTrue(
                "Número de orden no visible o vacío",
                nroDeOrden != null && !nroDeOrden.isEmpty()
        );
        assertTrue("Especie no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Nombre de especie")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Nombre de especie").isDisplayed());
        assertTrue("Riesgo no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Riesgo")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Riesgo").isDisplayed());
        String ctaComitenteTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Cuenta comitente")
                .getText()
                .trim()
                .replaceAll("\\D", ""); // deja solo dígitos

        int ctaComitente = Integer.parseInt(ctaComitenteTexto);

        assertTrue(
                "Cuenta comitente no visible o es 0",
                !ctaComitenteTexto.isEmpty() && ctaComitente  > 0
        );

        String cuentaDebitoEnPesosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Cuenta débito en pesos")
                .getText()
                .trim();

        String cuentaDebitoEnPesosLimpio = cuentaDebitoEnPesosTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .replace("CA", "")
                .trim();

        long cuentaDebitoEnPesos = Long.parseLong(cuentaDebitoEnPesosLimpio);

        assertTrue(
                "Cuenta débito en pesos no visible o es 0",
                !cuentaDebitoEnPesosTexto.isEmpty() && cuentaDebitoEnPesos  > 0
        );

        String cuentaDebitoEnDolaresTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Cuenta destino en dólares")
                .getText()
                .trim();

        String cuentaDebitoEnDolaresLimpio = cuentaDebitoEnDolaresTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .replace("CA", "")
                .replace("USD", "")
                .trim();

        long cuentaDebitoEnDolares = Long.parseLong(cuentaDebitoEnDolaresLimpio);

        assertTrue(
                "Cuenta destino en dólares no visible o es 0",
                !cuentaDebitoEnDolaresTexto.isEmpty() && cuentaDebitoEnDolares > 0
        );

        assertTrue("Fecha de Liquidación no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Fecha de liquidación")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Fecha de liquidación").isDisplayed());

        String comisionesEImpuestosEstimadosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Comisiones e impuestos estimados")
                .getText()
                .trim();

        String comisionesEImpuestosEstimadosLimpio = comisionesEImpuestosEstimadosTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();

        int comisionesEImpuestosEstimados = Integer.parseInt(comisionesEImpuestosEstimadosLimpio);

        assertTrue(
                "Comisiones e impuestos estimados no visible o es 0",
                !comisionesEImpuestosEstimadosTexto.isEmpty() && comisionesEImpuestosEstimados  > 0
        );


        String estimadoAAcreditarTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Estimado a acreditar")
                .getText()
                .trim();

        String estimadoAAcreditarLimpio = estimadoAAcreditarTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .replace("USD", "")
                .trim();

        int estimadoADebitar = Integer.parseInt(estimadoAAcreditarLimpio);

        assertTrue(
                "Estimado a debitar no visible o es 0",
                !estimadoAAcreditarTexto.isEmpty() && estimadoADebitar  > 0
        );

        String nominalesEstimadosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Nominales estimados")
                .getText()
                .trim();

        int nominalesEstimados = Integer.parseInt(nominalesEstimadosTexto);

        assertTrue(
                "Estimados a debitar  no visible o es 0",
                !nominalesEstimadosTexto.isEmpty() && nominalesEstimados  > 0
        );

        String precioEstimadoDeMercadoTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Precio estimado de mercado")
                .getText()
                .trim();

        String precioEstimadoDeMercadoLimpio = precioEstimadoDeMercadoTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .replace("USD", "")
                .trim();

        int precioEstimadoDeMercado = Integer.parseInt(precioEstimadoDeMercadoLimpio);

        assertTrue(
                "Precio estimado de mercado no visible o es 0",
                !precioEstimadoDeMercadoTexto.isEmpty() && precioEstimadoDeMercado  > 0
        );

        assertTrue("Moneda no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Moneda")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Moneda").isDisplayed());


        assertTrue("Texto informativo -Esta solicitud de orden está pendiente.. no visible ",
                !titulosPage.getInformacionSolicitudPendiente("Moneda")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Esta solicitud de orden está pendiente de ejecución, podrás ver su estado en la pantalla de Órdenes y movimientos.").isDisplayed());

        assertTrue("S.E.U.O no visible",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("S.E.U.O")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("S.E.U.O").isDisplayed());

        scrollTo(titulosPage.getBtnContinuarConfirmarYVolver("Volver").getElementBy());

        UtilsGP.fixedWait(3000);
        titulosPage.getBtnContinuarConfirmarYVolver("Volver").click();
    }

    @Step("Validar ticket Solicitud de Orden Venta exitosa")
    public void validarTicketVentaExitosa() {

        assertTrue("Solicitud de venta no visible ",
                botonesGenericosPage.getTituloPage("Solicitud de venta").isDisplayed());

        String nroDeOperacion = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Número de operación")
                .getText()
                .trim();

        assertTrue(
                "Número de operación no visible o vacío",
                nroDeOperacion != null && !nroDeOperacion.isEmpty()
        );

        assertTrue("Fecha no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Fecha")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Fecha").isDisplayed());

        String nroDeOrden = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Número de orden")
                .getText()
                .trim();

        assertTrue(
                "Número de operación no visible o vacío",
                nroDeOrden != null && !nroDeOrden.isEmpty()
        );

        assertTrue("Riesgo no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Riesgo")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Riesgo").isDisplayed());
        String ctaComitenteTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Cuenta comitente")
                .getText()
                .trim()
                .replaceAll("\\D", ""); // deja solo dígitos

        int ctaComitente = Integer.parseInt(ctaComitenteTexto);

        assertTrue(
                "Cuenta comitente no visible o es 0",
                !ctaComitenteTexto.isEmpty() && ctaComitente  > 0
        );

        String cuentaDestinoEnPesosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Cuenta destino en pesos")
                .getText()
                .trim()
                .replaceAll("\\D", ""); // deja solo dígitos

        long cuentaDestinoEnPesos = Long.parseLong(cuentaDestinoEnPesosTexto);

        assertTrue(
                "Cuenta débito en pesos no visible o es 0",
                !cuentaDestinoEnPesosTexto .isEmpty() && cuentaDestinoEnPesos  > 0
        );

        String comisionesEImpuestosEstimadosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Comisiones e impuestos estimados")
                .getText()
                .trim();

        String comisionesEImpuestosEstimadosLimpio = comisionesEImpuestosEstimadosTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();

        int comisionesEImpuestosEstimados = Integer.parseInt(comisionesEImpuestosEstimadosLimpio);

        assertTrue(
                "Comisiones e impuestos estimados no visible o es 0",
                !comisionesEImpuestosEstimadosTexto.isEmpty() && comisionesEImpuestosEstimados  > 0
        );

        String estimadoDeLaOrdenTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Estimado de la orden")
                .getText()
                .trim();

        String estimadoDeLaOrdenLimpio = estimadoDeLaOrdenTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();

        int estimadoDeLaOrden = Integer.parseInt(estimadoDeLaOrdenLimpio);

        assertTrue(
                "Estimados a debitar  no visible o es 0",
                !estimadoDeLaOrdenTexto.isEmpty() && estimadoDeLaOrden  > 0
        );

        String estimadoAAcreditarTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Estimado a acreditar")
                .getText()
                .trim();

        String estimadoAAcreditarLimpio = estimadoAAcreditarTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();

        int estimadoAAcreditar = Integer.parseInt(estimadoAAcreditarLimpio);

        assertTrue(
                "Estimados a debitar  no visible o es 0",
                !estimadoAAcreditarTexto.isEmpty() && estimadoAAcreditar  > 0
        );

        String nominalesEstimadosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Nominales estimados")
                .getText()
                .trim();

        int nominalesEstimados = Integer.parseInt(nominalesEstimadosTexto);

        assertTrue(
                "Estimados a debitar  no visible o es 0",
                !nominalesEstimadosTexto.isEmpty() && nominalesEstimados  > 0
        );

        String precioEstimadoDeMercadoTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Precio estimado de mercado")
                .getText()
                .trim();

        String precioEstimadoDeMercadoLimpio = precioEstimadoDeMercadoTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();

        int precioEstimadoDeMercado = Integer.parseInt(precioEstimadoDeMercadoLimpio);

        assertTrue(
                "Precio estimado de mercado no visible o es 0",
                !precioEstimadoDeMercadoTexto.isEmpty() && precioEstimadoDeMercado  > 0
        );

        assertTrue("Moneda no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Moneda")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Moneda").isDisplayed());


        assertTrue("Texto informativo -Esta solicitud de orden está pendiente.. no visible ",
                !titulosPage.getInformacionSolicitudPendiente("Moneda")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Esta solicitud de orden está pendiente de ejecución, podrás ver su estado en la pantalla de Órdenes y movimientos.").isDisplayed());

        assertTrue("S.E.U.O no visible",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("S.E.U.O")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("S.E.U.O").isDisplayed());

        scrollTo(titulosPage.getBtnContinuarConfirmarYVolver("Volver").getElementBy());

        UtilsGP.fixedWait(3000);
        titulosPage.getBtnContinuarConfirmarYVolver("Volver").click();
    }

    @Step("Validar pantalla Confirmación de Orden Compra Letras")
    public void validarPantallaConfirmacionDeOrdenCompraLetras() {

        assertTrue("Título Confirmación de Orden no visible ",
                botonesGenericosPage.getTituloPage("Confirmación de Orden").isDisplayed());
        assertTrue("Especie no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Nombre de especie")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Nombre de especie").isDisplayed());
        assertTrue("Riesgo no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Riesgo")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Riesgo").isDisplayed());


        String ctaComitenteTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Cuenta comitente")
                .getText()
                .trim()
                .replaceAll("\\D", ""); // deja solo dígitos

        int ctaComitente = Integer.parseInt(ctaComitenteTexto);

        assertTrue(
                "Cuenta comitente no visible o es 0",
                !ctaComitenteTexto.isEmpty() && ctaComitente  > 0
        );
        assertTrue("Fecha de liquidación no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Fecha de liquidación")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Fecha de liquidación").isDisplayed());
        String estiamadoDeLaOrdenTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Estimado de la Orden")
                .getText()
                .trim();

        String estimadoDeLaOrdenLimpio = estiamadoDeLaOrdenTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();


        int estiamadoDeLaOrden = Integer.parseInt(estimadoDeLaOrdenLimpio);

        assertTrue(
                "Estimado de la Orden no visible o es 0",
                !estiamadoDeLaOrdenTexto.isEmpty() && estiamadoDeLaOrden   > 0
        );
        String comisionesEImpuestosEstimadosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Estimado de la Orden")
                .getText()
                .trim();

        String comisionesEImpuestosEstimadosLimpio = comisionesEImpuestosEstimadosTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();

        int comisionesEImpuestosEstimados = Integer.parseInt(comisionesEImpuestosEstimadosLimpio);

        assertTrue(
                "Comisiones e impuestos estimados no visible o es 0",
                !comisionesEImpuestosEstimadosTexto.isEmpty() && comisionesEImpuestosEstimados  > 0
        );
        String estimadosADebitarTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Estimado a debitar")
                .getText()
                .trim();

        String estimadosADebitarLimpio = estimadosADebitarTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();

        int estimadosADebitar = Integer.parseInt(estimadosADebitarLimpio);

        assertTrue(
                "Estimados a debitar  no visible o es 0",
                !estimadosADebitarTexto.isEmpty() && estimadosADebitar  > 0
        );
        String nominalesEstimadosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Nominales estimados")
                .getText()
                .trim();

        int nominalesEstimados = Integer.parseInt(nominalesEstimadosTexto);

        assertTrue(
                "Estimados a debitar  no visible o es 0",
                !nominalesEstimadosTexto.isEmpty() && nominalesEstimados  > 0
        );
        String precioEstimadoDeMercadoTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Precio estimado de mercado")
                .getText()
                .trim();

        String precioEstimadoDeMercadoLimpio = precioEstimadoDeMercadoTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();

        int precioEstimadoDeMercado = Integer.parseInt(precioEstimadoDeMercadoLimpio);

        assertTrue(
                "Precio estimado de mercado no visible o es 0",
                !precioEstimadoDeMercadoTexto.isEmpty() && precioEstimadoDeMercado  > 0
        );

        assertTrue("Moneda no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Moneda")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Moneda").isDisplayed());

        scrollTo(titulosPage.getCheckBoxConfirmacionDeOrdenTerminosYCondiciones().getElementBy());
        UtilsGP.fixedWait(4000);
        titulosPage.getCheckBoxConfirmacionDeOrdenTerminosYCondiciones().click();

    }

    @Step("Validar pantalla Panel de Precios")
    public void validarPantallaPanelDePrecios() {
        UtilsGP.fixedWait(3000);
        botonesGenericosPage.getTituloPage("Panel de precios").isDisplayed();
    }

    @Step("Validar cartel informativo -Algo falló y no pudimos cargar la información-")
    public void validarCartelInformativoAlgoFallo() {

        if(titulosPage.getTituloInformativo("Importante").isDisplayed()) {
            scrollTo(titulosPage.getTituloInformativo("Importante").getElementBy());
           String MensajeDeError = titulosPage.getTituloInformativo("Importante").getText();
            Assert.fail("Mensaje de error: " + MensajeDeError);
        }
    }

    @Step("Validar ticket Solicitud de Orden Compra exitosa Letras")
    public void validarTicketCompraExitosaLetras() {

        assertTrue("Título Solicitud de compra no visible ",
                botonesGenericosPage.getTituloPage("Solicitud de compra").isDisplayed());

        String nroDeOperacion = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Número de operación")
                .getText()
                .trim();

        assertTrue(
                "Número de operación no visible o vacío",
                nroDeOperacion != null && !nroDeOperacion.isEmpty()
        );

        assertTrue("Fecha no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Fecha")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Fecha").isDisplayed());

        String nroDeOrden = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Número de orden")
                .getText()
                .trim();

        assertTrue(
                "Número de orden no visible o vacío",
                nroDeOrden != null && !nroDeOrden.isEmpty()
        );
        assertTrue("Especie no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Nombre de especie")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Nombre de especie").isDisplayed());
        assertTrue("Riesgo no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Riesgo")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Riesgo").isDisplayed());
        String ctaComitenteTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Cuenta comitente")
                .getText()
                .trim()
                .replaceAll("\\D", ""); // deja solo dígitos

        int ctaComitente = Integer.parseInt(ctaComitenteTexto);

        assertTrue(
                "Cuenta comitente no visible o es 0",
                !ctaComitenteTexto.isEmpty() && ctaComitente  > 0
        );

        String cuentaDebitOEnPesosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Cuenta débito en pesos")
                .getText()
                .trim()
                .replaceAll("\\D", ""); // deja solo dígitos

        long cuentaDebitOEnPesos = Long.parseLong(cuentaDebitOEnPesosTexto);

        assertTrue(
                "Cuenta débito en pesos no visible o es 0",
                !cuentaDebitOEnPesosTexto.isEmpty() && cuentaDebitOEnPesos  > 0
        );

        String comisionesEImpuestosEstimadosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Comisiones e impuestos estimados")
                .getText()
                .trim();

        String comisionesEImpuestosEstimadosLimpio = comisionesEImpuestosEstimadosTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();

        int comisionesEImpuestosEstimados = Integer.parseInt(comisionesEImpuestosEstimadosLimpio);

        assertTrue(
                "Comisiones e impuestos estimados no visible o es 0",
                !comisionesEImpuestosEstimadosTexto.isEmpty() && comisionesEImpuestosEstimados  > 0
        );

        String estimadoDeLaOrdenTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Estimado de la orden")
                .getText()
                .trim();

        String estimadoDeLaOrdenLimpio = estimadoDeLaOrdenTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();

        int estimadoDeLaOrden = Integer.parseInt(estimadoDeLaOrdenLimpio);

        assertTrue(
                "Estimados a debitar  no visible o es 0",
                !estimadoDeLaOrdenTexto.isEmpty() && estimadoDeLaOrden  > 0
        );

        String estimadosADebitarTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Estimado a debitar")
                .getText()
                .trim();

        String estimadosADebitarLimpio = estimadosADebitarTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();

        int estimadosADebitar = Integer.parseInt(estimadosADebitarLimpio);

        assertTrue(
                "Estimados a debitar  no visible o es 0",
                !estimadosADebitarTexto.isEmpty() && estimadosADebitar  > 0
        );

        String nominalesEstimadosTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Nominales estimados")
                .getText()
                .trim();

        int nominalesEstimados = Integer.parseInt(nominalesEstimadosTexto);

        assertTrue(
                "Estimados a debitar  no visible o es 0",
                !nominalesEstimadosTexto.isEmpty() && nominalesEstimados  > 0
        );

        String precioEstimadoDeMercadoTexto = titulosPage
                .getDatosConfirmacionDeOrdenYTicket("Precio estimado de mercado")
                .getText()
                .trim();

        String precioEstimadoDeMercadoLimpio = precioEstimadoDeMercadoTexto
                .replace("$", "")
                .replace(".", "")
                .replace(",", "")
                .trim();

        int precioEstimadoDeMercado = Integer.parseInt(precioEstimadoDeMercadoLimpio);

        assertTrue(
                "Precio estimado de mercado no visible o es 0",
                !precioEstimadoDeMercadoTexto.isEmpty() && precioEstimadoDeMercado  > 0
        );

        assertTrue("Moneda no visible ",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("Moneda")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Moneda").isDisplayed());


        assertTrue("Texto informativo -Esta solicitud de orden está pendiente.. no visible ",
                !titulosPage.getInformacionSolicitudPendiente("Moneda")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("Esta solicitud de orden está pendiente de ejecución, podrás ver su estado en la pantalla de Órdenes y movimientos.").isDisplayed());

        assertTrue("S.E.U.O no visible",
                !titulosPage.getDatosConfirmacionDeOrdenYTicket("S.E.U.O")
                        .getText().isEmpty() || titulosPage.getDatosConfirmacionDeOrdenYTicket
                        ("S.E.U.O").isDisplayed());

        scrollTo(titulosPage.getBtnContinuarConfirmarYVolver("Volver").getElementBy());

        UtilsGP.fixedWait(3000);
        titulosPage.getBtnContinuarConfirmarYVolver("Volver").click();
    }

}







