package Steps.Cuentas;

import Config.Settings;
import Config.ValidarResponse;
import Steps.DobleFactor.DobleFactor;
import com.jayway.jsonpath.JsonPath;
import constants.RequestMethods;
import core.PandoraRequest;
import core.PandoraResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import java.util.*;

import org.GP_Individuos_API.Utils;
import org.junit.Assert;
import steps.RequestSteps;

public class CuentasSteps {
    RequestSteps requestSteps = new RequestSteps();
    String host = System.getProperty("bankAPI");
    PandoraRequest postRequest = new PandoraRequest(host, RequestMethods.POST);
    Utils utils = new Utils();
    DobleFactor dobleFactor = new DobleFactor();
    ValidarResponse validarResponse = new ValidarResponse();
    private PandoraResponse resp;
    private String code2FA, idAdheredAcount = null;
    private boolean ownAccount, isAdhered, estadoContacto;
    private double quantityLoan, quantityAccredit, tea, tna, cft, idSalaryAdvance = 0;

    //Seccion de  mapeo de servicios

    private PandoraResponse adhesionAccountNew(String perteneceA, String destinatarioCBU, String code2FA, String tipo) {
        Settings.endpoint = "/api/v1/execute/adhesion.account.new";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Cuentas/adhesionAccountNew.json");
        body = body.replace("_destinatarioCBU_", destinatarioCBU)
                .replace("_perteneceA_", perteneceA)
                .replace("\"_ownAccount_\"", tipo)
                .replace("_code2FA_", code2FA);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse deleteAdheredAccount(String idAdhered) {
        Settings.endpoint = "/api/v1/execute/delete.adheredAccount";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Cuentas/deleteAdheredAccount.json");
        body = body.replace("_idAdhered_", idAdhered);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse listAdheredAccounts() {
        Settings.endpoint = "/api/v1/execute/list.adheredAccounts";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Cuentas/listAdheredAccounts.json");

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse transfersAccountSearch(String cbu, String tipo) {
        Settings.endpoint = "/api/v1/execute/account.transfer.search";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Cuentas/accountTransferSearch.json");
        body = body.replace("_cbu_", cbu)
                .replace("_typeAccount_", tipo);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse transfersAccountSearchCbuNull(String tipo) {
        Settings.endpoint = "/api/v1/execute/account.transfer.search";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Cuentas/accountTransferSearchCbuNull.json");
        body = body.replace("_typeAccount_", tipo);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse transfersAccountSearchTypeNull(String cbu) {
        Settings.endpoint = "/api/v1/execute/account.transfer.search";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Cuentas/accountTransferSearchTypeNull.json");
        body = body.replace("_cbu_", cbu);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }
    private PandoraResponse transfersAccountSearchTrueFalseAliasCbu(String cbu, boolean tipo) {
        Settings.endpoint = "/api/v1/execute/account.transfer.search";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Cuentas/accountTransferSearch.json");

        body = body.replace("_cbu_", String.valueOf(cbu))
                .replace("\"_typeAccount_\"", String.valueOf(tipo));


        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse transfersAccountSearchTrueFalseAliasCbuPertenecaA(boolean cbu, String tipo) {
        Settings.endpoint = "/api/v1/execute/account.transfer.search";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Cuentas/accountTransferSearch.json");

        body = body.replace("\"_cbu_\"", String.valueOf(cbu))
                .replace("_typeAccount_", String.valueOf(tipo));


        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse generateDebinSearch(String cbu, String tipo) {
        Settings.endpoint = "/api/v1/execute/account.transfer.search";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Pagos/debinGenerateSearch.json");
        body = body.replace("_aliasCbu_", cbu)
                .replace("_typeSelector_", tipo);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse atmGeneratePullOrderByAccounts(String originAccount, String code2FA) {
        Settings.endpoint = "/api/v1/execute/atm.generatePullOrder.byAccounts";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Cuentas/atmGeneratePullOrderByAccounts.json");
        body = body.replace("_document_", Settings.documentNumber)
                   .replace("_originAccount_", originAccount)
                   .replace("_originNumber_", Settings.number)
                   .replace("_idProduct_", Settings.idCuentaOrigen)
                   .replace("_code2FA_", code2FA);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse salaryAdvanceConfirm(String code2FA) {
        Settings.endpoint = "/api/v1/execute/salary.advance.confirm";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Cuentas/salaryAdvanceConfirm.json");

        body = body.replace("_idProduct_", Settings.idCuentaOrigen)
                   .replace("\"_amountSeals_\"", String.valueOf(Double.valueOf(quantityLoan).intValue()))
                   .replace("\"_moneyToAccredit_\"", String.valueOf(Double.valueOf(quantityAccredit).intValue()))
                   .replace("\"_tea_\"", String.valueOf(Double.valueOf(tea).intValue()))
                   .replace("\"_tna_\"", String.valueOf(tna))
                   .replace("\"_cft_\"", String.valueOf(Double.valueOf(cft).intValue()))
                   .replace("\"_idAdvance_\"", String.valueOf(Double.valueOf(idSalaryAdvance).intValue()))
                   .replace("_code2FA_", code2FA);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse salaryAdvancePre() {
        Settings.endpoint = "/api/v1/execute/salary.advance.pre";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Compartidos/requestVacio.json");

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse sealLoansByAmount() {
        Settings.endpoint = "/api/v1/execute/seal.loans.byAmount";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Cuentas/sealLoansByAmount.json");

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse listEcheq(String echeqNumber, String statement,String filterCuil,
                                      String operationType,String page) {
        Settings.endpoint = "/api/v1/execute/list.echeck";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Echeq/listEcheqValidaciones.json");
        body = body.replace("_echeqNumber_", echeqNumber)
                .replace("_statement_", statement)
                .replace("_filterCuil_", filterCuil)
                .replace("_operationType_", operationType)
                .replace("_page_", page);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse listEcheckTrueFalseCuil(String echeqNumber, String statement,
                                                    boolean filterCuil,
                                                    String operationType,String page) {
        Settings.endpoint = "/api/v1/execute/list.echeck";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Echeq/listEcheqValidaciones.json");

        body = body.replace("_echeqNumber_", echeqNumber)
                .replace("_statement_", statement)
                .replace("\"_filterCuil_\"", String.valueOf(filterCuil))
                .replace("_operationType_", operationType)
                .replace("_page_", page);


        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse listEcheckTrueFalsePage(String echeqNumber, String statement,
                                                    String filterCuil,
                                                    String operationType,Boolean page) {
        Settings.endpoint = "/api/v1/execute/list.echeck";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Echeq/listEcheqValidaciones.json");

        body = body.replace("_echeqNumber_", echeqNumber)
                .replace("_statement_", statement)
                .replace("_filterCuil_", filterCuil)
                .replace("_operationType_", operationType)
                .replace("\"_page_\"", String.valueOf(page));


        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse listEcheqNull(String echeqNumber, String statement,
                                          String operationType,String page) {
        Settings.endpoint = "/api/v1/execute/list.echeck";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Echeq/listEcheqValidaciones.json");
        body = body.replace("_echeqNumber_", echeqNumber)
                .replace("_statement_", statement)
                .replace("_operationType_", operationType)
                .replace("_page_", page);

        postRequest.setBody(utils.createBodyFromString(body));
        return requestSteps.executeRequest(postRequest);
    }

    //seccion de metodos publicos donde se consumen los servicios

    @Step
    public void agregarDestinatarioAgendaCBU(String aliasCbu, String perteneceA) {

        resp = transfersAccountSearch(aliasCbu, perteneceA);
        validarResponse.validateResponses(resp);

        code2FA = dobleFactor.getSecondFactor();
        ownAccount = JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.ownAccount");

        System.out.println("======================== OWN ACCOUNT : " + ownAccount);

        resp = adhesionAccountNew(perteneceA, aliasCbu, code2FA, String.valueOf(ownAccount));

        for (int i = 0; i < 3; i++) {
            if (Settings.flagServicioReejecucion) {

                code2FA = dobleFactor.getSecondFactor();
                resp = adhesionAccountNew(perteneceA, aliasCbu, code2FA, String.valueOf(ownAccount));

            } else {
                System.out.println("=================== SE AGENDO EL CONTACTO CORRECTAMENTE =====================");
                break;
            }
        }
        if (Settings.flagServicioReejecucion) {
            Assert.fail("FALLO 3 VECES EL SERVICIO AGENDA NUEVO ALIAS/CBU");
        }
    }

    @Step
    public void consultarDestinatarioAgendaCBU(String aliasCbu, String perteneceA) {

        resp = transfersAccountSearch(aliasCbu, perteneceA);
        validarResponse.validateResponses(resp);

        //-------VALIDO SI EL CONTACTO YA ESTA AGENDADO-----------------//
        if (Character.isDigit(aliasCbu.charAt(0))) {
            System.out.println("======================= TIPO CBU ===========================");
            for (int i = 0; i < 4; i++) {
                if (Settings.flagServicioReejecucion) {
                    if (Character.isDigit(aliasCbu.charAt(0))) {
                        System.out.println("======================= TIPO CBU ===========================");
                        resp = transfersAccountSearch(aliasCbu, perteneceA);
                    }
                } else {
                    break;
                }
            }
            if (Settings.flagServicioReejecucion) {
                Assert.fail("SE INTENTO 3 VECES Y EL SERVICIO FALLO");
            }

            isAdhered = JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.isAdhered");
            ownAccount = JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.ownAccount");
            if (isAdhered) {
                System.out.println("============================= EL CONTACTO ESTA AGENDADO ====================");
                System.out.println("============================= OBTENER ID ADHERED ============================");
                idAdheredAcount = utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.customer.idAdheredAccount").toString());
                System.out.println("========================= ID ADHERED : " + idAdheredAcount);
                estadoContacto = true;
            } else {
                System.out.println("=============================== EL CONTACTO NO ESTA AGENDANDO =============================");
                estadoContacto = false;
            }
        } else {
            System.out.println("======================= TIPO ALIAS ===========================");
            resp = transfersAccountSearch(aliasCbu, "ALIAS");
            for (int i = 0; i < 4; i++) {
                if (Settings.flagServicioReejecucion) {
                    System.out.println("======================= TIPO ALIAS ===========================");
                    resp = transfersAccountSearch(aliasCbu, "ALIAS");
                } else {
                    break;
                }
            }
            if (Settings.flagServicioReejecucion) {
                Assert.fail("SE INTENTO 3 VECES Y EL SERVICIO FALLO");
            }

            isAdhered = JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.isAdhered");
            ownAccount = JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.ownAccount");
            if (isAdhered) {
                System.out.println("============================= EL CONTACTO ESTA AGENDADO ====================");
                System.out.println("============================= OBTENER ID ADHERED ============================");
                idAdheredAcount = utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.customer.idAdheredAccount").toString());
                System.out.println("========================= ID ADHERED : " + idAdheredAcount);
                //System.out.println("============================= ELIMINANDO CONTACTO ==============================");
                //deleteAdheredAccount(idAdheredAcount);
                estadoContacto = true;
            } else {
                System.out.println("=============================== EL CONTACTO NO ESTA AGENDANDO =============================");
                estadoContacto = false;
            }
        }
    }

    @Step
    public void eliminarDestinatarioAgendaCBU() {

        int intentos = 0;
        if (estadoContacto) {
            System.out.println("============================= ELIMINANDO CONTACTO ==============================");

            PandoraResponse response = deleteAdheredAccount(idAdheredAcount);
            validarResponse.validateResponses(response);

            while (intentos<3){
                if(Settings.flagServicioReejecucion){
                    response = deleteAdheredAccount(idAdheredAcount);
                    validarResponse.validateResponses(response);
                }else{
                    Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                    Allure.addAttachment("SE ELIMINO EL CONTACTO CORRECTAMENTE", Settings.endpoint);
                    break;
                }
                intentos++;
            }
            Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);
        }
        else {
            System.out.println("=============================== EL CONTACTO NO ESTA AGENDANDO =============================");
        }
    }

    @Step
    public void generarOrdenDeExtracion(String cuentaOrigen){
        int intentos = 0;
        code2FA = dobleFactor.getSecondFactor();

        resp = atmGeneratePullOrderByAccounts(cuentaOrigen, code2FA);
        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                resp = atmGeneratePullOrderByAccounts(cuentaOrigen, code2FA);
                //validamos el codigo de respuesta
                validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ SE GENERO LA ORDEN DE EXTRACION CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO DE GENERAR LA ORDEN DE EXTRACION INTENTO 4 VECES",intentos==4);
    }

    @Step
    public void adelantoDeSueldo(){
        int intentos = 0;
        code2FA = dobleFactor.getSecondFactor();

        resp = salaryAdvanceConfirm(code2FA);
        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                resp = salaryAdvanceConfirm(code2FA);
                //validamos el codigo de respuesta
                validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ SE ADELANTO EL SUELDO CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO DE ADELANTO DE SUELDO INTENTO 4 VECES",intentos==4);
    }

    @Step
    public void obtenerDatosAdelantoDeSueldo(String cuentaOrigen){
        int intentos = 0;

        resp = salaryAdvancePre();
        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                resp = salaryAdvancePre();
                //validamos el codigo de respuesta
                validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ SE OBTUVIERON LOS DATOS DE ADELANTO DE SUELDO CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO DE OBTENER LOS DATOS DE ADELANTO DE SUELDO INTENTO 4 VECES",intentos==4);

        tea = Double.valueOf(utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString())
                .read("$.data.accounts[?(@.shortLabel == '"+cuentaOrigen+"')].tea").toString())).intValue();
        tna = Double.parseDouble(utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString())
                .read("$.data.accounts[?(@.shortLabel == '"+cuentaOrigen+"')].tna").toString()));
        cft = Double.valueOf(utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString())
                .read("$.data.accounts[?(@.shortLabel == '"+cuentaOrigen+"')].cft").toString())).intValue();
        idSalaryAdvance = Double.valueOf(utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString())
                .read("$.data.accounts[?(@.shortLabel == '"+cuentaOrigen+"')].idSalaryAdvance").toString())).intValue();
        }

    @Step
    public void obtenerDatosMontosAcreditacionAdelantoDeSueldo(){
        int intentos = 0;

        resp = sealLoansByAmount();
        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                resp = sealLoansByAmount();
                //validamos el codigo de respuesta
                validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ SE OBTUVIERON LOS DATOS DE MONTO DE ACREDITACION DE SUELDO CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO DE OBTENER LOS DATOS DE MONTO DE ACREDITACION DE SUELDO INTENTO 4 VECES",intentos==4);

        quantityLoan = Double.valueOf(utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString())
                .read("$.data.amountLoan.quantity").toString())).intValue();
        quantityAccredit = Double.valueOf(utils.quitarCochetesJsonPath(JsonPath.parse(resp.getBody().toPrettyString())
                .read("$.data.moneyToAccredit.quantity").toString())).intValue();
    }

    @Step("Validar card de adelanto de sueldo")
    public void validarCardAdelantoDeSueldo(){
                int intentos = 0;

        resp = salaryAdvancePre();
        validarResponse.validateResponses(resp);

        //Realizamos el bloque de reintentos por si falla el servicio
        while(intentos<4){
            if(Settings.flagServicioReejecucion){
                resp = salaryAdvancePre();
                //validamos el codigo de respuesta
                validarResponse.validateResponses(resp);
            }else{
                System.out.println("============ SE OBTUVIERON LOS DATOS DE ADELANTO DE SUELDO CORRECTAMENTE ====================");
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO SERVICIO DE OBTENER LOS DATOS DE ADELANTO DE SUELDO INTENTO 4 VECES",intentos==4);

        Assert.assertTrue("NO SE VALIDA LA CARD, USUARIO SIN PRECALIFICACION", JsonPath.parse(resp.getBody().toPrettyString()).read("$.data.accounts", List.class).size()>0);
    }

    @Step("Validar Response del servicio /account.transfer.search")
    public void validarErroresAccountTransferSearch(String aliasOCbu, String tipo) {

        resp = transfersAccountSearch(aliasOCbu, tipo);

        String code = JsonPath.parse(resp.getBody().toPrettyString()).read("$.code").toString();
        String message = JsonPath.parse(resp.getBody().toPrettyString()).read("$.message").toString();
        String codigoError = "COR020W";
        String codigoError2 = "API946W";

        Assert.assertNotNull("La respuesta no debe ser nula.", resp);
        Assert.assertEquals("El código de estado debería ser 200.", 200, resp.getStatusCode());
        Assert.assertTrue(
                "Transacción realizada",
                code.equals(codigoError) || code.equals(codigoError2)
        );


        System.out.println("+++++++++++++++  MENSAJE DE ERROR +++++++++++++++ ");
        System.out.println("/*/*/*/ STATUS CODE /*/*/*/ " + resp.getStatusCode());
        System.out.println("/*/*/*/ STATUS CODE ERROR /*/*/*/ " + code);
        System.out.println("/*/*/*/ MENSAJE DE ERROR /*/*/*/ " + message);
    }

    @Step("Validar Response del servicio /account.transfer.search")
    public void validarErroresAccountTransferSearchTrueFalseAliasCbu(String aliasOCbu, boolean tipo) {

        resp = transfersAccountSearchTrueFalseAliasCbu(aliasOCbu, tipo);

        String code = JsonPath.parse(resp.getBody().toPrettyString()).read("$.code").toString();
        String message = JsonPath.parse(resp.getBody().toPrettyString()).read("$.message").toString();
        String codigoError = "COR020W";
        String codigoError2 = "API946W";

        Assert.assertNotNull("La respuesta no debe ser nula.", resp);
        Assert.assertEquals("El código de estado debería ser 200.", 200, resp.getStatusCode());
        Assert.assertTrue(
                "Dato ingresado correctamente",
                code.equals(codigoError) || code.equals(codigoError2)
        );

        System.out.println("+++++++++++++++  MENSAJE DE ERROR +++++++++++++++ ");
        System.out.println("/*/*/*/ STATUS CODE /*/*/*/ " + resp.getStatusCode());
        System.out.println("/*/*/*/ STATUS CODE ERROR /*/*/*/ " + code);
        System.out.println("/*/*/*/ MENSAJE DE ERROR /*/*/*/ " + message);
    }

    @Step("Validar Response del servicio /account.transfer.search")
    public void validarErroresAccountTransferSearchTrueFalsePerteneceA(boolean aliasOCbu, String tipo) {

        resp = transfersAccountSearchTrueFalseAliasCbuPertenecaA(aliasOCbu, tipo);

        String code = JsonPath.parse(resp.getBody().toPrettyString()).read("$.code").toString();
        String message = JsonPath.parse(resp.getBody().toPrettyString()).read("$.message").toString();
        String codigoError = "COR020W";
        String codigoError2 = "API946W";

        Assert.assertNotNull("La respuesta no debe ser nula.", resp);
        Assert.assertEquals("El código de estado debería ser 200.", 200, resp.getStatusCode());
        Assert.assertTrue(
                "Transacción realizada",
                code.equals(codigoError) || code.equals(codigoError2)
        );


        System.out.println("+++++++++++++++  MENSAJE DE ERROR +++++++++++++++ ");
        System.out.println("/*/*/*/ STATUS CODE /*/*/*/ " + resp.getStatusCode());
        System.out.println("/*/*/*/ STATUS CODE ERROR /*/*/*/ " + code);
        System.out.println("/*/*/*/ MENSAJE DE ERROR /*/*/*/ " + message);
    }

    @Step("Validar Response del servicio /account.transfer.search")
    public void validarErroresAccountTransferSearchCbuNull(String tipo) {

        resp = transfersAccountSearchCbuNull(tipo);

        String code = JsonPath.parse(resp.getBody().toPrettyString()).read("$.code").toString();
        String message = JsonPath.parse(resp.getBody().toPrettyString()).read("$.message").toString();
        String codigoError = "COR020W";
        String codigoError2 = "API946W";

        Assert.assertNotNull("La respuesta no debe ser nula.", resp);
        Assert.assertEquals("El código de estado debería ser 200.", 200, resp.getStatusCode());
        Assert.assertTrue(
                "Transacción realizada",
                code.equals(codigoError) || code.equals(codigoError2)
        );

        System.out.println("+++++++++++++++  MENSAJE DE ERROR +++++++++++++++ ");
        System.out.println("/*/*/*/ STATUS CODE /*/*/*/ " + resp.getStatusCode());
        System.out.println("/*/*/*/ STATUS CODE ERROR /*/*/*/ " + code);
        System.out.println("/*/*/*/ MENSAJE DE ERROR /*/*/*/ " + message);
    }

    @Step("Validar Response del servicio /account.transfer.search")
    public void validarErroresAccountTransferSearchTipoNull(String aliasOCbu) {

        resp = transfersAccountSearchTypeNull(aliasOCbu);

        String code = JsonPath.parse(resp.getBody().toPrettyString()).read("$.code").toString();
        String message = JsonPath.parse(resp.getBody().toPrettyString()).read("$.message").toString();
        String codigoError = "COR020W";
        String codigoError2 = "API946W";

        Assert.assertNotNull("La respuesta no debe ser nula.", resp);
        Assert.assertEquals("El código de estado debería ser 200.", 200, resp.getStatusCode());
        Assert.assertTrue(
                "Transacción realizada",
                code.equals(codigoError) || code.equals(codigoError2)
        );

        System.out.println("+++++++++++++++  MENSAJE DE ERROR +++++++++++++++ ");
        System.out.println("/*/*/*/ STATUS CODE /*/*/*/ " + resp.getStatusCode());
        System.out.println("/*/*/*/ STATUS CODE ERROR /*/*/*/ " + code);
        System.out.println("/*/*/*/ MENSAJE DE ERROR /*/*/*/ " + message);
    }

    @Step("Validar Response del servicio /listEcheq")
    public void validarErroresListEcheq(String echeq, String estado,String cuil, String tipoOperacion,String pagina) {

        resp = listEcheq(echeq,estado,cuil,tipoOperacion,pagina);

        String code = JsonPath.parse(resp.getBody().toPrettyString()).read("$.code").toString();
        String message = JsonPath.parse(resp.getBody().toPrettyString()).read("$.message").toString();
        String codigoError = "COR020W";
        String codigoError2 = "BAK001E";

        Assert.assertNotNull("La respuesta no debe ser nula.", resp);
        Assert.assertEquals("El código de estado debería ser 200.", 200, resp.getStatusCode());
        Assert.assertTrue(
                "Transacción realizada",
                code.equals(codigoError) || code.equals(codigoError2)
        );


        System.out.println("+++++++++++++++  MENSAJE DE ERROR +++++++++++++++ ");
        System.out.println("/*/*/*/ STATUS CODE /*/*/*/ " + resp.getStatusCode());
        System.out.println("/*/*/*/ STATUS CODE ERROR /*/*/*/ " + code);
        System.out.println("/*/*/*/ MENSAJE DE ERROR /*/*/*/ " + message);
    }

    @Step("Validar Response del servicio /listEcheq")
    public void validarErroresListEcheqTrueFalseCuil(String echeq, String estado,boolean cuil, String tipoOperacion,String pagina) {

        resp = listEcheckTrueFalseCuil(echeq,estado,cuil,tipoOperacion,pagina);

        String code = JsonPath.parse(resp.getBody().toPrettyString()).read("$.code").toString();
        String message = JsonPath.parse(resp.getBody().toPrettyString()).read("$.message").toString();
        String codigoError = "COR020W";
        String codigoError2 = "BAK001E";

        Assert.assertNotNull("La respuesta no debe ser nula.", resp);
        Assert.assertEquals("El código de estado debería ser 200.", 200, resp.getStatusCode());
        Assert.assertTrue(
                "Transacción realizada",
                code.equals(codigoError) || code.equals(codigoError2)
        );

        System.out.println("+++++++++++++++  MENSAJE DE ERROR +++++++++++++++ ");
        System.out.println("/*/*/*/ STATUS CODE /*/*/*/ " + resp.getStatusCode());
        System.out.println("/*/*/*/ STATUS CODE ERROR /*/*/*/ " + code);
        System.out.println("/*/*/*/ MENSAJE DE ERROR /*/*/*/ " + message);
    }

    @Step("Validar Response del servicio /listEcheq")
    public void validarErroresListEcheqNull(String echeq, String estado,
                                            String tipoOperacion,String pagina) {

        resp = listEcheqNull(echeq,estado,tipoOperacion,pagina);

        String code = JsonPath.parse(resp.getBody().toPrettyString()).read("$.code").toString();
        String message = JsonPath.parse(resp.getBody().toPrettyString()).read("$.message").toString();
        String codigoError = "COR020W";
        String codigoError2 = "BAK001E";

        Assert.assertNotNull("La respuesta no debe ser nula.", resp);
        Assert.assertEquals("El código de estado debería ser 200.", 200, resp.getStatusCode());
        Assert.assertTrue(
                "Transacción realizada",
                code.equals(codigoError) || code.equals(codigoError2)
        );


        System.out.println("+++++++++++++++  MENSAJE DE ERROR +++++++++++++++ ");
        System.out.println("/*/*/*/ STATUS CODE /*/*/*/ " + resp.getStatusCode());
        System.out.println("/*/*/*/ STATUS CODE ERROR /*/*/*/ " + code);
        System.out.println("/*/*/*/ MENSAJE DE ERROR /*/*/*/ " + message);
    }

    @Step("Validar Response del servicio /listEcheq")
    public void validarErroresListEcheqTrueFalsePage(String echeq, String estado,String cuil,
                                                      String tipoOperacion,boolean pagina) {

        resp = listEcheckTrueFalsePage(echeq,estado,cuil,tipoOperacion,pagina);

        String code = JsonPath.parse(resp.getBody().toPrettyString()).read("$.code").toString();
        String message = JsonPath.parse(resp.getBody().toPrettyString()).read("$.message").toString();
        String codigoError = "COR020W";
        String codigoError2 = "BAK001E";

        Assert.assertNotNull("La respuesta no debe ser nula.", resp);
        Assert.assertEquals("El código de estado debería ser 200.", 200, resp.getStatusCode());
        Assert.assertTrue(
                "Transacción realizada",
                code.equals(codigoError) || code.equals(codigoError2)
        );

        System.out.println("+++++++++++++++  MENSAJE DE ERROR +++++++++++++++ ");
        System.out.println("/*/*/*/ STATUS CODE /*/*/*/ " + resp.getStatusCode());
        System.out.println("/*/*/*/ STATUS CODE ERROR /*/*/*/ " + code);
        System.out.println("/*/*/*/ MENSAJE DE ERROR /*/*/*/ " + message);
    }

}


