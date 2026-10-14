package api;

import io.qameta.allure.Allure;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ValidarCodeResponses {

    static Boolean flagServicioReejecucion;

    public void validateResponses(Response response,String endpoint){
        String message,data,code,idTransaccion;
        message = JsonPath.from(response.getBody().asPrettyString()).getString("message");
        code = JsonPath.from(response.getBody().asPrettyString()).getString("code");
        data = JsonPath.from(response.getBody().asPrettyString()).getString("data");
        idTransaccion = JsonPath.from(response.getBody().asPrettyString()).getString("idTransaction");
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        switch (code){
            case "COR002E":
                System.out.println("==================================== ERROR LOGIN API ====================================");
                System.out.println("====== HORA : "+ formato.format(LocalDateTime.now()));
                System.out.println("====== ENDPOINT : "+endpoint);
                System.out.println("====== STATUS CODE: "+code);
                System.out.println("====== MESSAGE : "+message);
                System.out.println("====== DATA/BODY: "+data);
                System.out.println("==================================== ERROR LOGIN API ====================================");
                Allure.addAttachment("SERVICIO NO OK", endpoint);
                Allure.addAttachment("CODIGO UPS", idTransaccion);
                Assert.fail("VERIFICAR USUARIO - UPS AL INICIAR SESSION");
                break;
            case "API019W":
                System.out.println("==================================== ERROR CREDENCIALES LOGIN API ====================================");
                System.out.println("====== HORA : "+ formato.format(LocalDateTime.now()));
                System.out.println("====== ENDPOINT : "+endpoint);
                System.out.println("====== STATUS CODE: "+code);
                System.out.println("====== MESSAGE : "+message);
                System.out.println("====== DATA/BODY: "+data);
                System.out.println("==================================== ERROR CREDENCIALES LOGIN API ====================================");
                Allure.addAttachment("SERVICIO NO OK", endpoint);
                Assert.fail("USUARIO Y/O CONTRASEÑA INCORRECTA");
                break;
            case "API526E":
                System.out.println("==================================== ERROR USUARIOLOGIN API ====================================");
                System.out.println("====== HORA : "+ formato.format(LocalDateTime.now()));
                System.out.println("====== ENDPOINT : "+endpoint);
                System.out.println("====== STATUS CODE: "+code);
                System.out.println("====== MESSAGE : "+message);
                System.out.println("====== DATA/BODY: "+data);
                System.out.println("==================================== ERROR USUARIO LOGIN API ====================================");
                Allure.addAttachment("SERVICIO NO OK", endpoint);
                Assert.fail("USUARIO BLOQUEADO - PEDIR DESBLOQUEO AL GESTOR DE DATOS");
                break;
            case "API949E":
                System.out.println("==================================== ERROR SETTINGS AMBIENTE ====================================");
                System.out.println("====== HORA : "+ formato.format(LocalDateTime.now()));
                System.out.println("====== ENDPOINT : "+endpoint);
                System.out.println("====== STATUS CODE: "+code);
                System.out.println("====== MESSAGE : "+message);
                System.out.println("====== DATA/BODY: "+data);
                System.out.println("==================================== ERROR SETTINGS AMBIENTE ====================================");
                Allure.addAttachment("SERVICIO NO OK", endpoint);
                Assert.fail("VARIABLE DE DISPOSITIVO DESCONOCIDO ACTIVA - REVISAR BACKOFFICE");
                break;
            case "COR001E":
                System.out.println("==================================== ERROR API - UPS GENERICO ====================================");
                System.out.println("====== HORA : "+ formato.format(LocalDateTime.now()));
                System.out.println("====== ENDPOINT : "+endpoint);
                System.out.println("====== STATUS CODE: "+code);
                System.out.println("====== MESSAGE : "+message);
                System.out.println("====== DATA/BODY: "+data);
                System.out.println("==================================== ERROR API - UPS GENERICO ====================================");
                Allure.addAttachment("SERVICIO NO OK", endpoint);
                System.out.println("====================== RE-EJEUCTAR SERVICIO =========================");
                flagServicioReejecucion =false;
                break;
            case "COR999E":
                System.out.println("==================================== ERROR HEADER API ====================================");
                System.out.println("====== HORA : "+ formato.format(LocalDateTime.now()));
                System.out.println("====== ENDPOINT : "+endpoint);
                System.out.println("====== STATUS CODE: "+code);
                System.out.println("====== MESSAGE : "+message);
                System.out.println("====== DATA/BODY: "+data);
                System.out.println("==================================== ERROR HEADER API ====================================");
                Allure.addAttachment("SERVICIO NO OK", endpoint);
                Assert.fail("VERIFICAR HEADERS - POSIBLE FALTA DE BEARER TOKEN - SERVICIO: " + endpoint);
                break;
            case "COR020W":
                System.out.println("==================================== ERROR API - VERIFICAR DATA ====================================");
                System.out.println("====== HORA : "+ formato.format(LocalDateTime.now()));
                System.out.println("====== ENDPOINT : "+endpoint);
                System.out.println("====== STATUS CODE: "+code);
                System.out.println("====== MESSAGE : "+message);
                System.out.println("====== DATA/BODY: "+data);
                System.out.println("==================================== ERROR API - VERIFICAR DATA ====================================");
                verificarData(data,endpoint);
                System.out.println("====================== RE-EJEUCTAR SERVICIO =========================");
                flagServicioReejecucion =false;
                break;
            case "API946W":
                System.out.println("==================================== ERROR API - UPS INESTABILIDAD ====================================");
                System.out.println("====== HORA : "+ formato.format(LocalDateTime.now()));
                System.out.println("====== ENDPOINT : "+endpoint);
                System.out.println("====== STATUS CODE: "+code);
                System.out.println("====== MESSAGE : "+message);
                System.out.println("====== DATA/BODY: "+data);
                System.out.println("==================================== ERROR API - UPS INTESTABILIDAD ====================================");
                System.out.println("====================== RE-EJEUCTAR SERVICIO =========================");
                flagServicioReejecucion =false;
                break;
            case "BAK001E":
            case "API603E":
            case "API604E":
                System.out.println("==================================== ERROR API - UPS INESTABILIDAD ====================================");
                System.out.println("====== HORA : "+ formato.format(LocalDateTime.now()));
                System.out.println("====== ENDPOINT : "+endpoint);
                System.out.println("====== STATUS CODE: "+code);
                System.out.println("====== MESSAGE : "+message);
                System.out.println("====== DATA/BODY: "+data);
                System.out.println("==================================== ERROR API - UPS INTESTABILIDAD ====================================");
                Allure.addAttachment("SERVICIO NO OK", endpoint);
                Allure.addAttachment("CODIGO UPS", idTransaccion);
                System.out.println("====================== RE-EJEUCTAR SERVICIO =========================");
                flagServicioReejecucion =false;
                break;
            case "COR000I":
            case "COR013I":
                System.out.println("===================== SERVICIO OK ===================");
                Allure.addAttachment("SERVICIO OK" , endpoint );
                flagServicioReejecucion =true;
                break;
            default:
                System.out.println("==================================== ERROR API ====================================");
                System.out.println("====== HORA : "+ formato.format(LocalDateTime.now()));
                System.out.println("====== ENDPOINT : "+endpoint);
                System.out.println("====== STATUS CODE: "+code);
                System.out.println("====== MESSAGE : "+message);
                System.out.println("====== DATA/BODY: "+data);
                System.out.println("==================================== ERROR API ====================================");
                Allure.addAttachment("SERVICIO NO OK", endpoint);
                Assert.fail("STATUS CODE NO MAPEADO - REQUIERE ANALSIS DEL SERVICIO");
                break;
        }
    }
    private void verificarData(String data, String endpoint){

        //VALIDAMOS SI ES EL ERROR ES POR DOBLE FACTOR O POR REQUEST INCOMPLETO
        if(data.contains("El código ingresado es inválido") || data.contains("El código ingresado ya se ha utilizad") || data.contains("El código de validación ya se ha utilizado")){
            System.out.println("========= FALLO EL CODIGO DEL SEGUNDO FACTOR ===============");
        }
        else if(data.contains("Ocurrió un error al realizar la subscripción. Por favor, intentá nuevamente más tarde.")){
            System.out.println("========= FALLO PROCESO REEJEUTAR POR AMBIENTE INESTBALE ===============");
        }else if(data.contains("El código de validación ya se ha utilizado")){
            System.out.println("=================== FALLO EL CODIGO DE VERIFICACION ALFANUMERICO VOLVER A INTENTAR CON OTRO CODIGO ==================");
        }else{
                System.out.println("========= REVISAR CAMPOS DE REQUEST POSIBLE FALTA PARAMETROS =====================");
                Allure.addAttachment("SERVICIO NO OK", endpoint);
                Assert.fail("============ FALLO EL SERVICIO  - REVISAR POSIBLE FALTA DE CAMPOS EN EL REQUEST - SERVICIO :" + endpoint +" =================");
            }
        }
}
