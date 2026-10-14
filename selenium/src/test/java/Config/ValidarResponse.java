package Config;

import core.PandoraResponse;
import io.qameta.allure.Allure;
import org.junit.Assert;
import com.jayway.jsonpath.JsonPath;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ValidarResponse {

    public void validateResponses(PandoraResponse response){
        String message,data,code,idTransaccion;
        message = JsonPath.parse(response.getBody().toPrettyString()).read("$.message").toString();
        code = JsonPath.parse(response.getBody().toPrettyString()).read("$.code").toString();
        data = JsonPath.parse(response.getBody().toPrettyString()).read("$.data").toString();
        idTransaccion = JsonPath.parse(response.getBody().toPrettyString()).read("$.idTransaction").toString();

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        switch (code){
            case "COR002E":
                System.out.println("==================================== ERROR LOGIN API ====================================");
                System.out.println("====== HORA : "+ formato.format(LocalDateTime.now()));
                System.out.println("====== ENDPOINT : "+Settings.endpoint);
                System.out.println("====== STATUS CODE: "+code);
                System.out.println("====== MESSAGE : "+message);
                System.out.println("====== DATA/BODY: "+data);
                System.out.println("==================================== ERROR LOGIN API ====================================");
                Allure.addAttachment("SERVICIO NO OK", Settings.endpoint);
                Allure.addAttachment("CODIGO UPS", idTransaccion);
                Assert.fail("VERIFICAR USUARIO - UPS AL INICIAR SESSION");
                break;
            case "API019W":
                System.out.println("==================================== ERROR CREDENCIALES LOGIN API ====================================");
                System.out.println("====== HORA : "+ formato.format(LocalDateTime.now()));
                System.out.println("====== ENDPOINT : "+Settings.endpoint);
                System.out.println("====== STATUS CODE: "+code);
                System.out.println("====== MESSAGE : "+message);
                System.out.println("====== DATA/BODY: "+data);
                System.out.println("==================================== ERROR CREDENCIALES LOGIN API ====================================");
                Allure.addAttachment("SERVICIO NO OK", Settings.endpoint);
                Assert.fail("USUARIO Y/O CONTRASEÑA INCORRECTA");
                break;
            case "API526E":
                System.out.println("==================================== ERROR USUARIO BLOQUEADO LOGIN API ====================================");
                System.out.println("====== HORA : "+ formato.format(LocalDateTime.now()));
                System.out.println("====== ENDPOINT : "+Settings.endpoint);
                System.out.println("====== STATUS CODE: "+code);
                System.out.println("====== MESSAGE : "+message);
                System.out.println("====== DATA/BODY: "+data);
                System.out.println("==================================== ERROR USUARIO BLOQUEADO LOGIN APII ====================================");
                Allure.addAttachment("SERVICIO NO OK", Settings.endpoint);
                Assert.fail("USUARIO BLOQUEADO");
                break;
            case "COR001E":
                System.out.println("==================================== ERROR API - UPS GENERICO ====================================");
                System.out.println("====== HORA : "+ formato.format(LocalDateTime.now()));
                System.out.println("====== ENDPOINT : "+Settings.endpoint);
                System.out.println("====== STATUS CODE: "+code);
                System.out.println("====== MESSAGE : "+message);
                System.out.println("====== DATA/BODY: "+data);
                System.out.println("==================================== ERROR API - UPS GENERICO ====================================");
                Allure.addAttachment("SERVICIO NO OK", Settings.endpoint);
                System.out.println("====================== RE-EJEUCTAR SERVICIO =========================");
                Settings.flagServicioReejecucion = true;
                break;
            case "COR999E":
                System.out.println("==================================== ERROR HEADER API ====================================");
                System.out.println("====== HORA : "+ formato.format(LocalDateTime.now()));
                System.out.println("====== ENDPOINT : "+Settings.endpoint);
                System.out.println("====== STATUS CODE: "+code);
                System.out.println("====== MESSAGE : "+message);
                System.out.println("====== DATA/BODY: "+data);
                System.out.println("==================================== ERROR HEADER API ====================================");
                Allure.addAttachment("SERVICIO NO OK", Settings.endpoint);
                Assert.fail("VERIFICAR HEADERS - POSIBLE FALTA DE BEARER TOKEN - SERVICIO: " + Settings.endpoint);
                break;
            case "COR020W":
            case "API924W":
                System.out.println("==================================== ERROR API - VERIFICAR DATA ====================================");
                System.out.println("====== HORA : "+ formato.format(LocalDateTime.now()));
                System.out.println("====== ENDPOINT : "+Settings.endpoint);
                System.out.println("====== STATUS CODE: "+code);
                System.out.println("====== MESSAGE : "+message);
                System.out.println("====== DATA/BODY: "+data);
                System.out.println("==================================== ERROR API - VERIFICAR DATA ====================================");
                verificarData(data,Settings.endpoint);
                System.out.println("====================== RE-EJEUCTAR SERVICIO =========================");
                Settings.flagServicioReejecucion = true;
                break;
            case "API946W":
                System.out.println("==================================== ERROR API - UPS INESTABILIDAD ====================================");
                System.out.println("====== HORA : "+ formato.format(LocalDateTime.now()));
                System.out.println("====== ENDPOINT : "+Settings.endpoint);
                System.out.println("====== STATUS CODE: "+code);
                System.out.println("====== MESSAGE : "+message);
                System.out.println("====== DATA/BODY: "+data);
                System.out.println("==================================== ERROR API - UPS INTESTABILIDAD ====================================");
                System.out.println("====================== RE-EJEUCTAR SERVICIO =========================");
                Settings.flagServicioReejecucion = true;
                break;
            case "BAK001E":
            case "API603E":
            case "API604E":
            case "COR044E":
                System.out.println("==================================== ERROR API - UPS INESTABILIDAD ====================================");
                System.out.println("====== HORA : "+ formato.format(LocalDateTime.now()));
                System.out.println("====== ENDPOINT : "+Settings.endpoint);
                System.out.println("====== STATUS CODE: "+code);
                System.out.println("====== MESSAGE : "+message);
                System.out.println("====== DATA/BODY: "+data);
                System.out.println("==================================== ERROR API - UPS INTESTABILIDAD ====================================");
                Allure.addAttachment("SERVICIO NO OK", Settings.endpoint);
                Allure.addAttachment("CODIGO UPS", idTransaccion);
                System.out.println("====================== RE-EJEUCTAR SERVICIO =========================");
                Settings.flagServicioReejecucion = true;
                break;
            case "COR000I":
            case "COR013I":
            case "API690W":
            case "API691W":
            case "API692W":
                System.out.println("===================== SERVICIO OK ===================");
                Allure.addAttachment("SERVICIO OK" , Settings.endpoint );
                Settings.flagServicioReejecucion = false;
                break;
            default:
                System.out.println("==================================== ERROR API ====================================");
                System.out.println("====== HORA : "+ formato.format(LocalDateTime.now()));
                System.out.println("====== ENDPOINT : "+Settings.endpoint);
                System.out.println("====== STATUS CODE: "+code);
                System.out.println("====== MESSAGE : "+message);
                System.out.println("====== DATA/BODY: "+data);
                System.out.println("==================================== ERROR API ====================================");
                Allure.addAttachment("SERVICIO NO OK", Settings.endpoint);
                Assert.fail("STATUS CODE NO MAPEADO - REQUIERE ANALSIS DEL SERVICIO");
                break;
        }
    }
    private void verificarData(String data, String endpoint){
        //VALIDAMOS SI ES EL ERROR ES POR DOBLE FACTOR O POR REQUEST INCOMPLETO
        if(data.contains("{NO_FIELD=Ya contás con una solicitud de Tarjeta Credito") || data.contains("{NO_FIELD=Ya contás con una Tarjeta")){
            System.out.println("============================= PASO POR AQUI ===============================");
            data = "Tarjeta de Credito sin oferta";
        }
        switch (data){
            case "{otp=El código ingresado es inválido}":
            case "{otp=No ha presentado un otp válido}":
            case "{otp=El código ingresado ya se ha utilizad}":
            case "{otp=El código de validación ya se ha utilizado}":
                System.out.println("========= FALLO EL CODIGO DEL SEGUNDO FACTOR ===============");
                break;
            case "{NO_FIELD=- MOMENTANEAMENTE NO SE PUEDE PROCESAR - MOMENTANEAMENTE NO SE PUEDE PROCESAR}":
            case "{NO_FIELD=DEBIN/CREDIN DUPLICADO DEBIN/CREDIN DUPLICADO}":
            case "{NO_FIELD=No pudimos procesar tu pago, por favor inténtalo nuevamente}":
            case "{messageError=- MOMENTANEAMENTE NO SE PUEDE PROCESAR}":
            case "{}":
            case "{NO_FIELD=backend.error.generic.iso.05}":
            case "{NO_FIELD=Error en servicio de Emitir ECheq COELSA}":
                boolean flag = verificarEndpoint(endpoint);
                if(flag){
                    System.out.println("============= SERVICIO INESTABLE RE-EJECUTAR ===========================");
                } else {
                    Assert.fail("================== COMPORTAMIENTO DE SERVICIO NO MAPEADO - REVISAR DATA Y ENDPOINT ==========================");
                }
                break;
            case "{NO_FIELD=Error de validación}":
                System.out.println("=============== ERROR VALIDACION - RE-EJEUCTAR EL SERVICIO===========");
                break;
            case "{messageError=Ocurrió un error en el sistema. Por favor, intentá nuevamente en otro momento., codeError=89}":
                System.out.println("============= USUARIO SIN PRECALIFICACION - ERROR DATOS ===========================");
                Assert.fail("============= USUARIO SIN PRECALIFICACION - ERROR DATOS ===========================");
                break;
            case "Tarjeta de Credito sin oferta":
                System.out.println("=========================== USUARIO CON OFERTA YA UTILIZAFDA - USUARIO QUEMADO ===========================");
                Assert.fail("=========================== USUARIO CON OFERTA YA UTILIZAFDA - USUARIO QUEMADO ===========================");
                break;
            default:
                System.out.println("========= REVISAR CAMPOS DE REQUEST POSIBLE FALTA PARAMETROS =====================");
                Allure.addAttachment("SERVICIO NO OK", endpoint);
                Assert.fail("============ FALLO EL SERVICIO  - REVISAR POSIBLE FALTA DE CAMPOS EN EL REQUEST - SERVICIO :" + endpoint +" =================");
                break;
        }
    }
    private boolean verificarEndpoint(String endpoint){
        //validamos el endpoint si requiere o no re-ejecución
        boolean endpointReejecucion = false;
        switch (endpoint){
            case "/api/v1/execute/echeq.emit":
                System.out.println("======================== FALLO SERVICIO DE ECHEQ - INESTABILIDAD/2FA =========================");;
                endpointReejecucion = true;
                break;
            case "/api/v1/execute/credin.generate":
                System.out.println("======================== FALLO SERVICIO DE CREDIN MENSAJE EMERGENETE - INESTABILIDAD =========================");;
                endpointReejecucion = true;
                break;
            case "/api/v1/execute/debins.inform.acceptance.accept":
                System.out.println("======================== FALLO SERVICIO DE ACEPTAR DEBIN MENSAJE EMERGENETE - INESTABILIDAD =========================");
                endpointReejecucion = true;
                break;
            case "/api/v1/execute/firstPayment.request":
                System.out.println("======================== FALLO SERVICIO DE PAGAR MENSAJE EMERGENTE - INESTABILIDAD ================================");
                endpointReejecucion = true;
                break;
            case "/api/v1/execute/recharge.value.list":
                System.out.println("======================= FALLO SERVICIO DE RECARGAS SUBE/PERSONAL - UPS EN LA SECCION ===========================");
                endpointReejecucion = true;
                break;
            case "/api/v1/execute/buyselldollar.buyorselldollartransfer":
                System.out.println("================================ FALLO EL SERVICIO DE COMPRAR DOLARES ========================");
                Assert.fail("================================ FALLO EL SERVICIO DE COMPRAR DOLARES ========================");
                break;
        }
        return endpointReejecucion;
    }

}
