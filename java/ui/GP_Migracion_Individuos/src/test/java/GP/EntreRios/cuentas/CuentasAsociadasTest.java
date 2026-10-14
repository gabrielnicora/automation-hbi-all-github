package GP.EntreRios.cuentas;

import GP.Tags.Baja;
import GP.Tags.Criticos;
import db.ConexionDB;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.junit.experimental.categories.Category;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.cuentas.CuentasSteps;
import steps.cuentas.cuentas_asociadas.CuentasAsociadasSteps;
import steps.doblefactor.DobleFactorSteps;
import steps.login.LoginSteps;
import steps.menulateral.MenuLateralSteps;
import utils.Constantes;
import utils.TestCaseData;
import utils.UtilsGP;

import java.io.IOException;

import static db.ConexionDB.cerrarConexion;
import static utils.Constantes.uTranspull4;

public class CuentasAsociadasTest {
    MenuLateralSteps menuLateralSteps = new MenuLateralSteps();
    CuentasSteps cuentasSteps = new CuentasSteps();
    LoginSteps loginSteps = new LoginSteps();
    CuentasAsociadasSteps cuentasAsociadasSteps= new CuentasAsociadasSteps();
    UtilsGP utilsGP= new UtilsGP();
    DobleFactorSteps dobleFactorSteps= new DobleFactorSteps();
    BotonesGenericosSteps botonesGenericosSteps = new BotonesGenericosSteps();
    BotonesGenericosPage botonesGenericosPage = new BotonesGenericosPage();
    TestCaseData testCaseData = new TestCaseData();
    @BeforeClass
    public static void extraerDatosBD(){
        System.out.println("============= BEFORE CLASS ==========================");
        if(Constantes.usuarios == null){
            System.out.println("============= Array de datos vacio extraer desde la BD ==============");
            Constantes.usuarios = ConexionDB.extraerUsuariosDB();
            System.out.println("============= se completo el array de datos ==========================");
        }else {
            System.out.println("================ EL ARRAY CONTIENE DATOS ===================");
        }
    }
    @Test
    @Category({Baja.class})
    @DisplayName("TC-0246-BERSA: Consentimiento Billetera - Modal 'Como funciona'")
    public void TC0246BERSA() {
        try {
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickCuentasAsociadas();
            cuentasAsociadasSteps.clickEnComoFunciona();
            cuentasAsociadasSteps.validacionCamposComoFunciona();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @Test
    @Category({Criticos.class})
    @DisplayName("TC-0287-BERSA: Consentimiento Billetera - Aprobar consentimiento")
    public void TC0287BERSA() {
        try {
            testCaseData.cargaDeDatosTest(uTranspull4);
            loginSteps.iniciarSessionAlEscritorioConPass(uTranspull4,testCaseData.password);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickCuentasAsociadas();
            cuentasAsociadasSteps.validarTituloPageGenerico("Cuentas asociadas");
            cuentasAsociadasSteps.clickVincularUnaCuenta();
            cuentasAsociadasSteps.clickCheckTerminosCondicionesGenerico();
            //dobleFactorSteps.codigoSegundoFactorOtroMetodo(uTranspull4);
            cuentasAsociadasSteps.clickAceptarGenerico();
            utilsGP.ManageTabPorParametro(2);
            cuentasAsociadasSteps.botonContinuarPagExterna();
            cuentasAsociadasSteps.seleccionarBanco("Banco de Santa Fe");
            cuentasAsociadasSteps.botonContinuarPagExterna();
            cuentasAsociadasSteps.setUsuario("Transpull4");
            cuentasAsociadasSteps.setContrasenia("Banco045");
            cuentasAsociadasSteps.setTarjetaDebito("5009");
            cuentasAsociadasSteps.clickBotonIniciarSesion();
            cuentasAsociadasSteps.seleccionarPrimerCuentaChekbox();
            cuentasAsociadasSteps.checkboxTerminosYCondiciones();
            cuentasAsociadasSteps.botonContinuarPagExterna();
            utilsGP.paginaActual();
            cuentasAsociadasSteps.clickDesplegableEntidadesAsociadas();
            cuentasAsociadasSteps.validarEntidadAsociada("NUEVO BANCO DE SANTA FE SOCIEDAD ANONIMA");
            cuentasAsociadasSteps.abrirPestaniaBanco("https://hbtest01.bancosantafe.com/startPage");
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickCuentasAsociadas();
            cuentasAsociadasSteps.desplegableCuentasAsociadasEnOtrasEntidades();
            cuentasAsociadasSteps.validarEntidadAsociada("Banco Entre Rios");
            utilsGP.backMainTap();
            //TEARDOWN
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickCuentasAsociadas();
            cuentasAsociadasSteps.clickDesplegableEntidadesAsociadas();
            cuentasAsociadasSteps.validarEntidadAsociada("NUEVO BANCO DE SANTA FE SOCIEDAD ANONIMA");
            cuentasAsociadasSteps.desasociarEntidadAsociada("NUEVO BANCO DE SANTA FE SOCIEDAD ANONIMA");
            //dobleFactorSteps.codigoSegundoFactorOtroMetodo(uTranspull4);
            cuentasAsociadasSteps.validarMensajeCuentaDesasociada();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test
    @Category({Criticos.class})
    @DisplayName("TC-0340-BERSA: Consentimiento - Transferencias Pull - Solicitar transferencia pull desde la ventana de cuentas asociadas que otorgo consentimiento - Transacción Exitosa")
    public void TC0340BERSA() {
        try {
            //PRECONDICION
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickCuentasAsociadas();
            cuentasAsociadasSteps.validarTituloPageGenerico("Cuentas asociadas");
            cuentasAsociadasSteps.clickVincularUnaCuenta();
            cuentasAsociadasSteps.clickCheckTerminosCondicionesGenerico();
            //dobleFactorSteps.codigoSegundoFactorOtroMetodo(uTranspull4);
            cuentasAsociadasSteps.clickAceptarGenerico();
            utilsGP.ManageTabPorParametro(2);
            cuentasAsociadasSteps.botonContinuarPagExterna();
            cuentasAsociadasSteps.seleccionarBanco("Banco de Santa Fe");
            cuentasAsociadasSteps.botonContinuarPagExterna();
            cuentasAsociadasSteps.setUsuario("Transpull4");
            cuentasAsociadasSteps.setContrasenia("Banco045");
            cuentasAsociadasSteps.setTarjetaDebito("5009");
            cuentasAsociadasSteps.clickBotonIniciarSesion();
            cuentasAsociadasSteps.seleccionarPrimerCuentaChekbox();
            cuentasAsociadasSteps.checkboxTerminosYCondiciones();
            cuentasAsociadasSteps.botonContinuarPagExterna();
            utilsGP.paginaActual();
            cuentasAsociadasSteps.clickDesplegableEntidadesAsociadas();
            //TEST
            cuentasAsociadasSteps.clickCuentaAsociada("NUEVO BANCO DE SANTA FE SOCIEDAD ANONIMA");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }

    //@Test CASO COMENTADO POR BUG AL INTENTAR ASOCIAR UN CUENTA
    @Category({})
    @DisplayName("TC-0343-BERSA: CONSENTIMIENTO Revocación - Revocar Mis cuentas asociadas en otras billeteras")
    public void TC0343BERSA() {
        try {
            //FALTA PRECONDICIÓN ASOCIAR UNA CUENTA

            //CASO
            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickCuentasAsociadas();
            cuentasAsociadasSteps.clickDesplegableEntidadesAsociadas();
            cuentasAsociadasSteps.desasociarEntidadAsociada("BANCO CREDICOOP COOPERATIVO LIMITADO");
            //dobleFactorSteps.codigoSegundoFactor(uTranspull4);
            cuentasAsociadasSteps.validarMensajeCuentaDesasociada();


        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    //@Test CASO COMENTADO PORQUE FALTA PRECONDICIÓN REALIZAR TRANSFERENCIA PULL(BUG AL INTENTAR ASOCIAR UNA CUENTA)
    @Category({Criticos.class})
    @DisplayName("TC-0341-BERSA: Consentimiento - Transferencias Pull - Listado de transacciones")
    public void TC0341BERSA() {
        try {
            //FALTA PRECONDICIÓN REALIZAR TRANSFERENCIA PULL(TRANSFERENCIA A CUENTA ASOCIADA)

            loginSteps.iniciarSessionAlEscritorio(uTranspull4);
            menuLateralSteps.clickCuentas();
            cuentasSteps.clickMasOpcionesGenerico();
            cuentasSteps.clickListadoDeTransacciones();
            //Falta la validación "La transferencia pull realizada se ve con una "activitycode" especifica"

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Assert.fail("Ocurrio un error: " + ex.getMessage());
        }
    }
    @After
    public void cerrarSesionCapturarUPS(){
        if(utilsGP.estaPresente(botonesGenericosPage.getCodigoUps(),2) && botonesGenericosPage.getCodigoUps().getText().startsWith("#")) {
            String ups = botonesGenericosPage.getCodigoUps().getText();
            botonesGenericosSteps.clickVolverGenerico();
            loginSteps.clickSalirDelaSesion();
            loginSteps.waitGP();
            Assert.fail("UPS CODIGO : " + ups);
        }else if(utilsGP.estaPresente(botonesGenericosPage.getSalirSesion(),2))
        {
            loginSteps.clickSalirDelaSesion();
            loginSteps.waitGP();
        }else{
            System.out.println("=================== NO PUDO INICIAR SESION REFRESCAR NAVEGADOR ==============================");
            Allure.addAttachment("INICIO SESSION,","=================== NO PUDO INICIAR SESION REFRESCAR NAVEGADOR ==============================");
            global.practia.jemeinsamstark.web.core.WebDriver.getInstance().navigate().to(System.getProperty("bank"));
            System.out.println("Se Espera que aparezca la pagina de nuevo");
            loginSteps.waitGP();
        }
    }
    @AfterClass
    public static void teardown() throws IOException, InterruptedException {
        global.practia.jemeinsamstark.web.core.WebDriver.closeDriver();
        //cerrarConexion();
    }
}
