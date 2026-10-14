package Config;

import net.minidev.json.JSONObject;

public class Settings {

    public static String exchangeToken;
    public static String bearerToken;
    public static String idSession;
    public static String telefono;
    public static String dobleFactor;
    public static JSONObject dataTestCase;
    public static boolean flagServicioReejecucion;
    public static String endpoint;
    public static String documentNumber;
    public static String mail;
    public static String name;
    public static String cuil;


    //DATOS COMPARTIDOS PARA UTILIZAR EN LOS PROCESOS
    //CLASE COMPARTIDOS API - DATOS DE CUENTAS Y DATOS DE TARJETAS
    public static String idCuentaOrigen ;
    public static String idCuentaDestino;
    public static String idDebitCard = "";
    public static String labelDebitCard;
    public static String idCreditCard;
    public static String cbuTarget;
    public static String currency;
    public static String label;
    public static String number;
    public static long montoActual;
    public static int montoActualizado;
    public static int montoActualDestino;
    public static int montoActualizadoDestino;
    public static String idTransaccion;
    public static String ctaOrigen;
    public static String accountCodeUsd;
    public static String balance;
    public static String accountTypeUsdLabel;


    //DATOS PARA PAGOS
    public static String idFile;
    public static String PAGOS_PDF="Pagos de impuestos o servicios realizados.pdf";
    public static String PAGOS_XLS="Pagos de impuestos o servicios realizados.xls";



    //DATOS FCI
    public static String deltaPesosUnysis = "A-Delta Pesos";
    public static String deltaAhorroPlusUnisys = "A-Delta Ahorro Plus";
    public static String deltaPesos = "A-DELTA PESOS";
    public static String keyLongValueUnisys = "11340";
    public static String keyLongValueBSF = "28739";
    public static String keyLongValueBSC = "1925";

    //DATOS TARJETAS
    public static String PEP = "Persona expuesta politicamente";
    public static String EXTERIOR = "Residencia en el exterior";
    public static String UIF = "Error UIF";

    //DATOS PERFIL

    public static String telefonoDeReemplazo = "1156676780";

    //DATOS DE ECHEQ
    public static String aLaOrden = "ALO";
    public static String noALaOrden = "NLO";
    public static String nroDeEcheq;
    public static String cuitEmisor;
    public static String iDEcheq;

    //DOBLE FACTOR MASTER CODE
    public static String masterCode = "1234";

    //Datos de Respuesta de ejecución del Servicio
    public static String mensaje;

    public static String paginated ="1";
}
