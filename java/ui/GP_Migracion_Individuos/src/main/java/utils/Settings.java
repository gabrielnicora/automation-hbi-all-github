package utils;

import net.minidev.json.JSONObject;

public class Settings {

    public static JSONObject dataTestCase;
    public static String exchangeToken;
    public static String bearerToken;
    public static String telefono;
    public static String dobleFactor;
    public static boolean flagServicioReejecucion;
    public static String endpoint;

    /**
     * Path a las landings de las secciones
     */
    public static String ESCRITORIO = "desktop";
    public static String BONOS_SALUD = "healthBonus";

    public static String CUENTA = "accounts";
    public static String ECHEQS = "accounts/echeqs";
    public static String CUOTIFICALO = "quotification?fromMenu=true";

    public static String INVERSIONES = "investments";
    public static String FCI = "investment/fund";
    public static String PLAZO_FIJO = "fixedTerms";
    public static String NUEVO_PRESTAMO = "loanRequest/1";
    public static String PRES_CANCELAR_DEUDAS = "loanRequest/cancellations";
    public static String MENSAJES = "communications";
    public static String CONTRATOS = "digitalFilesConsult";
    public static String BAJA_ARREPENTIMIENTO = "unsuscribe";
    public static String PERFIL = "settings";
    public static String SEGUROS = "insurance";

    public static String TRANSFERENCIAS = "newTransfers?fromMenu=true";
    public static String TARJETA_CREDITO = "creditCards";
    public static String PAGOS = "payments";

    public static String COMPRA_VENTA_USD = "buySellForeignCurrency";
    public static String MIS_TENENCIAS = "investment/holdings";




    //DOBLE FACTOR MASTER CODE
    public static String masterCode = "1234";

    //DATOS DE ECHEQ
    public static String aLaOrden = "ALO";
    public static String noALaOrden = "NLO";
    public static String echeqType;
    public static String idCuentaOrigen;
    public static String nroDeEcheq;
    public static String guionMotivoDePago;
    public static String colorEstadoEcheq;


    //DATOS TICKET
    public static String nroDeOperacion;

    /**
     * Seccion de FCI ESCO SEMI-AUTOMATICA - BSF
     */
    public static String importe = "1000";

    public static String DELTA_PESOS= "A-DELTA PESOS";
    public static String AHORRO_PLUS="A-DELTA AHORRO PLUS";
    public static String DELTA_MONEDA= "A-DELTA MONEDA";
    public static String DELTA_AHORRO= "A-DELTA AHORRO";
    public static String DELTA_DOLARES= "A-DELTA DOLARES";
    public static String DELTA_SCHRODER_RETORNO_DOLARES= "Clase A-SCHRODER RETORNO TOTAL DOLARES";
    public static String DELTA_SHCRODER_INCOME_DOLARES = "Clase A-SCHRODER INCOME DOLARES";
    public static int DELTA_PESOS_ID = 10008;
    public static int DELTA_AHORRO_ID = 10005;
    public static int DELTA_AHORRO_PLUS_ID = 10010;
    public static int DELTA_MONEDA_ID = 10013;
    public static int DELTA_DOLARES_ID = 10042;
    public static int DELTA_DOLARES_SCHRODER_RETORNO_ID = 10046;
    public static int DELTA_SCHODER_INCOME_DOLARES_ID = 10048;

    public static String MENSAJE_ADVERTENCIA_T0= "Recordá que tu suscripción se procesará el próximo día hábil.";
    public static String MENSAJE_ADVERTENCIA_T1="Recordá que tus fondos serán acreditados en tu cuenta dentro de las próximas 48 hs hábiles.";
    public static String MENSAJE_ADVERTENCIA_T2="Recordá que tus fondos serán acreditados en tu cuenta dentro de las próximas 72 hs hábiles.";

    /**
     * Seccion de FCI ESCO SEMI-AUTOMATICA - UNYSIS
     */

    public static String DELTA_PESOS_UNISYS= "A-Delta Pesos";
    public static String AHORRO_PLUS_UNISYS="A-Delta Ahorro Plus";
    public static String DELTA_MONEDA_UNISYS= "A-Delta Moneda";
    public static String DELTA_AHORRO_UNISYS= "A-Delta Ahorro";
    public static int DELTA_PESOS_ID_UNISYS= 5;
    public static int DELTA_AHORRO_ID_UNISYS = 7;
    public static int DELTA_AHORRO_PLUS_ID_UNISYS = 3;
    public static int DELTA_MONEDA_ID_UNISYS = 11;
    public static String DELTA_DOLARES_UNISYS= "A-Delta Dolares";
    public static String DELTA_SHCRODER_INCOME_DOLARES_UNISYS= "Clase A-SCHRODER INCOME";
    public static int DELTA_SHCRODER_INCOME_DOLARES_ID_UNISYS = 36;

    /**
     * Seccion de Inversiones - Títulos
     *
     */

    public static String especie;
    public static String riesgoTitulos;
    public static String ctaComitenteTitulos;
    public static String ctaDebitoEnPesosTitulos;
    public static String monedaTitulos;
    public static String estimadoDeLaOrden;
    public static String comisionesEImpuestosEstimados;
    public static String estimadoADebitar;
    public static String estimadoAAcreditar;
    public static String precioLimite;
    public static String nominalesDisponibles;

}
