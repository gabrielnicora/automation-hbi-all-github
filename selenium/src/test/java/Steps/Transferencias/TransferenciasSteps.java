package Steps.Transferencias;

import Config.Settings;
import Config.TestCaseData;
import Config.ValidarResponse;
import Steps.DobleFactor.DobleFactor;
import com.jayway.jsonpath.JsonPath;
import constants.RequestMethods;
import core.PandoraRequest;
import core.PandoraResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.GP_Individuos_API.Utils;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import steps.RequestSteps;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static Config.Settings.*;

public class TransferenciasSteps {
    DobleFactor dobleFactor = new DobleFactor();
    RequestSteps requestSteps = new RequestSteps();
    String host = System.getProperty("bankAPI");
    PandoraRequest postRequest = new PandoraRequest(host, RequestMethods.POST);
    Utils utils = new Utils();
    ValidarResponse validarResponse = new ValidarResponse();
    TestCaseData testCaseData = new TestCaseData();
    //Variables de Transferencias
    String idAdhered,cuit,targetName,bankName,accountTypeLabel,
            accountNumber,accountCode,brandType,productType,
            synchronisedAccountLabel,adheredAccountLabel,accountAlias,
            cbu, amount, conceptLabel, targetNumberBank= null;
    int cantidadDePaginas;
    //Variables de Credin
    String debitName,debitCuit,debitNumber,debitAlias,debitBrandType,debitNumberBank,creditName,creditCuil,creditNumber,creditNumberBank,creditAlias,creditBrandType,creditBank = null;
    Boolean isAdhered,ownAccount;

    private PandoraResponse transfersAccountMake(){
        Settings.endpoint = "/api/v1/execute/transfers.account.make";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);


        String body = utils.leerContenidoArchivo("./Requests/Transferencias/transfersAccountMake.json");

        body = body.replace("_originAccount_",Settings.idCuentaOrigen)
                .replace("_targetAccount_",Settings.idCuentaDestino)
                .replace("_debitCardId_",Settings.idDebitCard)
                .replace("_debitCardLabel_",Settings.labelDebitCard)
                .replace("_cbu_", Settings.cbuTarget);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }
    private PandoraResponse transfersAccountSearch(String cbu, String tipo){
        Settings.endpoint = "/api/v1/execute/account.transfer.search";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Transferencias/accountTransferSearch.json");
        body =body.replace("_cbu_",cbu).replace("_typeAccount_",tipo);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }
    private PandoraResponse deleteAdheredAccount(String idAdhered){
        Settings.endpoint = "/api/v1/execute/delete.adheredAccount";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Transferencias/deleteAdheredAccount.json");
        body = body.replace("_idAdhered_",idAdhered);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }
    private PandoraResponse adhesionAccountNew(String perteneceA, String destinatarioCBU, String code2FA, String tipo){
        Settings.endpoint = "/api/v1/execute/adhesion.account.new";
        postRequest.setHeaders("Content-Type","application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Transferencias/adhesionAccountNew.json");
        body = body.replace("_destinatarioCBU_",destinatarioCBU)
                .replace("_perteneceA_",perteneceA)
                .replace("\"_ownAccount_\"",tipo)
                .replace("_code_",code2FA);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }
    private PandoraResponse transferAccountMakeWithCapsCbu(String code){
        Settings.endpoint = "/api/v1/execute/account.transfer.makeWithCapsCbu";
        postRequest.setHeaders("Content-Type","application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String  body = utils.leerContenidoArchivo("./Requests/Transferencias/transfersAccountMakeWithCapsCbu.json");
        body = body.replace("_cbu_",this.cbu)
                .replace("_originAccount_",Settings.idCuentaOrigen)
                .replace("_targetCuit_",cuit)
                .replace("_code_",code)
                .replace("_debitCardId_",Settings.idDebitCard)
                .replace("_debitCardLabel_",Settings.labelDebitCard)
                .replace("_currency_", Settings.currency)
                .replace("_originAccountLabel_", Settings.label)
                .replace("_targetAccountLabel_",targetName)
                .replace("_targetBankName_",bankName)
                .replace("_targetAccountNumberLabel_",accountNumber)
                .replace("_targetAccountCode_",accountCode)
                .replace("_targetBrandType_",brandType)
                .replace("_productType_",productType)
                .replace("_adheredAccountFalse_",adheredAccountLabel)
                .replace("_accountTypeLabel_",accountTypeLabel)
                .replace("_accountAlias_",accountAlias)
                .replace("_targetNumberBank_",targetNumberBank)
                .replace("_accountCodeUsd_",accountCodeUsd)
                .replace("_balance_",balance)
                .replace("_accountTypeUsdLabel_",accountTypeUsdLabel)
                .replace("_accountCode_", accountCode);


        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse transferAccountMakeWithCapsCbuUsd(String code){
        Settings.endpoint = "/api/v1/execute/account.transfer.makeWithCapsCbu";
        postRequest.setHeaders("Content-Type","application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String  body = utils.leerContenidoArchivo("./Requests/Transferencias/transfersAccountMakeWithCapsCbu.json");
        body = body.replace("_cbu_",this.cbu)
                .replace("_originAccount_",Settings.idCuentaOrigen)
                .replace("_targetCuit_",cuit)
                .replace("_code_",code)
                .replace("_debitCardId_",Settings.idDebitCard)
                .replace("_debitCardLabel_",Settings.labelDebitCard)
                .replace("_currency_", Settings.currency)
                .replace("_originAccountLabel_", Settings.label)
                .replace("_targetAccountLabel_",targetName)
                .replace("_targetBankName_",bankName)
                .replace("_targetAccountNumberLabel_",accountNumber)
                .replace("_targetAccountCode_",accountCodeUsd)
                .replace("_targetBrandType_",brandType)
                .replace("_productType_",productType)
                .replace("_adheredAccountFalse_",adheredAccountLabel)
                .replace("_accountTypeLabel_",accountTypeLabel)
                .replace("_accountAlias_",accountAlias)
                .replace("_targetNumberBank_",targetNumberBank)
                .replace("_accountCodeUsd_",accountCodeUsd)
                .replace("_balance_",balance)
                .replace("_accountTypeUsdLabel_",accountTypeUsdLabel)
                .replace("_accountCode_","");


        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse transferAccountMakeWithCapsCbuSinDescripcion(String code){
        Settings.endpoint = "/api/v1/execute/transfers.account.makeWithCapsCbu";
        postRequest.setHeaders("Content-Type","application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String  body = utils.leerContenidoArchivo("./Requests/Transferencias/transfersAccountMakeWithCapsCbuSinDescripcion.json");
        body = body.replace("_cbu_",this.cbu)
                .replace("_originAccount_",Settings.idCuentaOrigen)
                .replace("_targetCuit_",cuit)
                .replace("_code_",code)
                .replace("_debitCardId_",Settings.idDebitCard)
                .replace("_debitCardLabel_",Settings.labelDebitCard)
                .replace("_currency_", Settings.currency)
                .replace("_originAccountLabel_", Settings.label)
                .replace("_targetAccountLabel_",targetName)
                .replace("_targetBankName_",bankName)
                .replace("_targetAccountNumberLabel_",accountNumber)
                .replace("_targetAccountCode_",accountCode)
                .replace("_targetBrandType_",brandType)
                .replace("_productType_",productType)
                .replace("_adheredAccountFalse_",adheredAccountLabel)
                .replace("_accountTypeLabel_",accountTypeLabel)
                .replace("_accountAlias_",accountAlias)
                .replace("_targetNumberBank_",targetNumberBank);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse ticket(){
        Settings.endpoint = "/api/v1/execute/ticket.get";
        postRequest.setHeaders("Content-Type","application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String  body = utils.leerContenidoArchivo("./Requests/Transferencias/ticket.json");
        body = body.replace("_idTransaccion_", idTransaccion);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }


    private PandoraResponse
    transfersAccountScheduleWithCapsCbu(String fecha,String code){
        Settings.endpoint = "/api/v1/execute/transfers.account.scheduleWithCapsCbu";
        postRequest.setHeaders("Content-Type","application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String  body = utils.leerContenidoArchivo("./Requests/Transferencias/transfersAccountScheduleWithCapsCbu.json");
        body = body.replace("_cbu_",cbu)
                .replace("_originAccount_",Settings.idCuentaOrigen)
                .replace("_targetCuit_",cuit)
                .replace("_code_",code)
                .replace("_debitCardId_",Settings.idDebitCard)
                .replace("_debitCardLabel_",Settings.labelDebitCard)
                .replace("_currency_", Settings.currency)
                .replace("_originAccountLabel_", Settings.label)
                .replace("_targetAccountLabel_",targetName)
                .replace("_targetBankName_",bankName)
                .replace("_targetAccountNumberLabel_",accountNumber)
                .replace("_targetAccountCode_",accountCode)
                .replace("_targetBrandType_",brandType)
                .replace("_productType_",productType)
                .replace("_adheredAccountFalse_",adheredAccountLabel)
                .replace("_accountTypeLabel_",accountTypeLabel)
                .replace("_accountAlias_",accountAlias)
                .replace("_date_",fecha)
                .replace("_targetNumberBank_",targetNumberBank);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }
    private PandoraResponse transfersAccountScheduleWithCaps(String fecha,String belongsTo){
        Settings.endpoint = "/api/v1/execute/transfers.account.scheduleMakeWithCaps";
        postRequest.setHeaders("Content-Type","application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String  body = utils.leerContenidoArchivo("./Requests/Transferencias/transfersAccountScheduleWithCaps.json");
        body = body.replace("_cbu_",cbu)
                .replace("_originAccount_",Settings.idCuentaOrigen)
                .replace("_targetCuit_",cuit)
                .replace("_belongsTo_",belongsTo)
                .replace("_debitCardId_",Settings.idDebitCard)
                .replace("_debitCardLabel_",Settings.labelDebitCard)
                .replace("_currency_", Settings.currency)
                .replace("_originAccountLabel_", Settings.label)
                .replace("_targetAccountLabel_",targetName)
                .replace("_targetBankName_",bankName)
                .replace("_targetAccountNumberLabel_",accountNumber)
                .replace("_targetAccountCode_",accountCode)
                .replace("_targetBrandType_",brandType)
                .replace("_productType_",productType)
                .replace("_adheredAccountFalse_",adheredAccountLabel)
                .replace("_accountTypeLabel_",accountTypeLabel)
                .replace("_accountAlias_",accountAlias)
                .replace("_date_",fecha)
                .replace("_targetNumberBank_",targetNumberBank);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }
    private PandoraResponse transferAccountMakeWithCaps(String belongsTo){
        Settings.endpoint = "/api/v1/execute/transfers.account.makeWithCaps";
        postRequest.setHeaders("Content-Type","application/json");
        postRequest.setHeaders("Authorization",Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Transferencias/transfersAccountMakeWithCaps.json");
        body = body.replace("_cbu_",cbu)
                .replace("_originAccount_",Settings.idCuentaOrigen)
                .replace("_targetCuit_",cuit)
                .replace("_debitCardId_",Settings.idDebitCard)
                .replace("_debitCardLabel_",Settings.labelDebitCard)
                .replace("_currency_", Settings.currency)
                .replace("_originAccountLabel_", Settings.label)
                .replace("_targetAccountLabel_",targetName)
                .replace("_targetBankName_",bankName)
                .replace("_targetAccountNumberLabel_",accountNumber)
                .replace("_targetAccountCode_",accountCode)
                .replace("_targetAccount_",idAdhered)
                .replace("_belongsTo_",belongsTo);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }
    private PandoraResponse transfersAccountScheduleMake(String fecha){
        Settings.endpoint = "/api/v1/execute/transfers.account.scheduleMake";
        postRequest.setHeaders("Content-Type", "application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);


        String body = utils.leerContenidoArchivo("./Requests/Transferencias/transfersAccountScheduleMake.json");

        body = body.replace("_originAccount_",Settings.idCuentaOrigen)
                .replace("_targetAccount_",Settings.idCuentaDestino)
                .replace("_debitCardId_",Settings.idDebitCard)
                .replace("_debitCardLabel_",Settings.labelDebitCard)
                .replace("_cbu_", Settings.cbuTarget)
                .replace("_date_", fecha);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }
    private PandoraResponse credinGenerateSearch(String cbu,String owner){
        Settings.endpoint="/api/v1/execute/credin.generate.search";
        postRequest.setHeaders("Content-Type","application/json");
        postRequest.setHeaders("Authorization",Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Transferencias/credinGenerateSearch.json");

        body = body.replace("_cbu_",cbu).replace("_owner_",owner);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }
    private PandoraResponse generateCredin(String code,String cbuDestino){
        Settings.endpoint="/api/v1/execute/credin.generate";
        postRequest.setHeaders("Content-Type","application/json");
        postRequest.setHeaders("Authorization",Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Transferencias/credinGenerate.json");
        body = body.replace("_debitName_",debitName)
                .replace("_debitCuit_",debitCuit)
                .replace("_debitNumber_",debitNumber)
                .replace("_debitAlias_",debitAlias)
                .replace("_debitBrandType_",debitBrandType)
                .replace("_debitNumberBank_",debitNumberBank)
                .replace("_debitIdProduct_",Settings.idDebitCard)
                .replace("_creditOwner_",creditName)
                .replace("_creditCuit_",creditCuil)
                .replace("_creidtNumber_",creditNumber)
                .replace("_cbuDestino_",cbuDestino)
                .replace("_creditAlias_",creditAlias)
                .replace("_creditBrandtype_",creditBrandType)
                .replace("_creditNumberBank_",creditNumberBank)
                .replace("_cbuOrigen_",Settings.cbuTarget)
                .replace("_code_",code)
                .replace("_currency_",Settings.currency);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }
    private PandoraResponse credinSearch(String start,String end,String tipo){
        Settings.endpoint="/api/v1/execute/credin.search";
        postRequest.setHeaders("Content-Type","application/json");
        postRequest.setHeaders("Authorization",Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String body = utils.leerContenidoArchivo("./Requests/Transferencias/credinSearch.json");
        body = body.replace("_startDate_",String.valueOf(start))
                .replace("_endDate_",String.valueOf(end))
                .replace("_cuitOriginator_",debitCuit)
                .replace("_cuitRecipient_",creditCuil)
                .replace("_searchAs_",tipo);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse historialTransferenciasUnAño(){
        Settings.endpoint = "/api/v1/execute/transfer.list.history";
        postRequest.setHeaders("Content-Type","application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String  body = utils.leerContenidoArchivo("./Requests/Transferencias/transferListHistory.json");
        body = body.replace("_dateFrom_", utils.fechaTransferenciaUnAñoAntiguedad("365"))
                .replace("_dateTo_", utils.fechaTransferenciaUnAñoAntiguedad("0"));


        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse historialTransferencias(){
        Settings.endpoint = "/api/v1/execute/transfer.list.history";
        postRequest.setHeaders("Content-Type","application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String  body = utils.leerContenidoArchivo("./Requests/Transferencias/transferListHistory.json");
        body = body.replace("_dateFrom_", utils.fechaTransferenciaUnAñoAntiguedad("365"))
                .replace("_dateTo_", utils.fechaHoy());

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }

    private PandoraResponse historialTransferenciasBusquedaRangoNombreODescripcion(String ND){
        Settings.endpoint = "/api/v1/execute/transfer.list.history";
        postRequest.setHeaders("Content-Type","application/json");
        postRequest.setHeaders("Authorization", Settings.bearerToken);
        postRequest.setPaths(Settings.endpoint);

        String  body = utils.leerContenidoArchivo("./Requests/Transferencias/transferListHistoryPorRangoNombreODescripcion.json");
        body = body.replace("_dateFrom_", utils.fechaTransferenciaUnAñoAntiguedad("180"))
                .replace("_dateTo_", utils.fechaHoy())
        .replace("_searchedText_", ND);

        postRequest.setBody(utils.createBodyFromString(body));

        return requestSteps.executeRequest(postRequest);
    }

    @Step("Realiza trasnferencias entre cuentas propias {cuentaOrigen} - {cuentaDestino} - {tarjetaDebito}")
    public void trasnferenciasEntreCuentaPropias(){
        //Realiza la transferencia
        int intentos = 0;
        PandoraResponse response = transfersAccountMake();
        validarResponse.validateResponses(response);
        while (intentos<3){
            if(Settings.flagServicioReejecucion){
                response = transfersAccountMake();
                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);
    }
    @Step("Validar Descuento de la cuenta origen")
    public void validarDescuentoCuenta(){
        Allure.addAttachment("Monto Actual ", String.valueOf(Settings.montoActual));
        Allure.addAttachment("Monto Actualizado ", String.valueOf(Settings.montoActualizado));
        Assert.assertTrue("NO SE REALIZO EL DESCUENTO :",(Settings.montoActual -1)>=Settings.montoActualizado );
    }
    @Step("Validar Incremento en la cuenta destino")
    public void validarIncrementoEnCuenta(){
        Allure.addAttachment("Monto Actual ", String.valueOf(Settings.montoActualDestino));
        Allure.addAttachment("Monto Actualizado ", String.valueOf(Settings.montoActualizadoDestino));
        Assert.assertTrue("NO SE REALIZO EL INCREMENTO : ", (Settings.montoActualDestino+1)<=Settings.montoActualizadoDestino);
    }
    @Step("Validar si el cbu ingresado ya esta -agendado")
    public void validarCbuAgendado(String cbu){
        PandoraResponse response;
        //Consulto si el cbu ingresado ya esta agendado en la agenda de destinatario
        response = transfersAccountSearch(cbu,"CBU");
        validarResponse.validateResponses(response);
        for (int i = 0; i<3;i++){
            if(Settings.flagServicioReejecucion){
                response = transfersAccountSearch(cbu,"CBU");
                validarResponse.validateResponses(response);
            }else {
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);

        //Obtener el id Adhered para validar si esta agendado o no


        isAdhered  = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.isAdhered");
        ownAccount = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.ownAccount");
        cuit = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.cuil");
        System.out.println("Cuit prueba" + cuit);
        targetName = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.owner"));
        bankName = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.bankName"));
        synchronisedAccountLabel = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.synchronisedAccountLabel"));
        accountNumber = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.accountNumber"));
        accountCode = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.accountCode"));
        brandType = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.brandType"));
        productType = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.productType"));
        accountTypeLabel = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.accountTypeLabel"));
        adheredAccountLabel = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.adheredAccountLabel"));
        accountAlias = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.accountAlias"));
        accountCodeUsd = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.accountCodeUsd"));
        balance = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.balance"));
        accountTypeUsdLabel = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.accountTypeUsdLabel"));
        this.cbu = cbu;
        targetNumberBank = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.bankNumber"));
        Allure.addAttachment("isAdhered",String.valueOf(isAdhered));
        Allure.addAttachment("ownAccount",String.valueOf(ownAccount));
        Allure.addAttachment("cuit",cuit);
        Allure.addAttachment("targetName",targetName);
        Allure.addAttachment("bankName",bankName);
        Allure.addAttachment("accountNumber",accountNumber);
        Allure.addAttachment("accountCode",accountCode);
        Allure.addAttachment("cbu destinatario",cbu);


        if(isAdhered){
            System.out.println("===================== EL CONTACTO ESTA AGENDADO =================");
            idAdhered = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.idAdheredAccount").toString());
            Allure.addAttachment("EL CONTACTO ESTA AGENDADO idAdhered",idAdhered);
        }else {
            System.out.println("=============================== EL CONTACTO NO ESTA AGENDADO =============================");
        }
    }
    @Step("Validar si el alias ingresado ya esta -agendado")
    public void validarAliasAgendado(String alias){
        PandoraResponse response;
        //Consulto si el cbu ingresado ya esta agendado en la agenda de destinatario
        response = transfersAccountSearch(alias,"ALIAS");
        validarResponse.validateResponses(response);
        for (int i = 0; i<3;i++){
            if(Settings.flagServicioReejecucion){
                response = transfersAccountSearch(alias,"ALIAS");
                validarResponse.validateResponses(response);
            }else {
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);

        //Obtener el id Adhered para validar si esta agendado o no
        isAdhered  = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.isAdhered");
        ownAccount = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.ownAccount");
        cuit = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.cuil");
        targetName = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.owner"));
        bankName = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.bankName"));
        accountNumber = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.accountNumber"));
        accountCode = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.accountCode"));
        brandType = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.brandType"));
        productType = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.productType"));
        accountTypeLabel = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.accountTypeLabel"));
        adheredAccountLabel = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.adheredAccountLabel"));
        accountAlias = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.accountAlias"));
        cbu = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.cbu"));
        accountCodeUsd = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.accountCodeUsd"));
        balance = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.balance"));
        accountTypeUsdLabel = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.accountTypeUsdLabel"));
        targetNumberBank = utils.validarNullParametro(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.bankNumber"));
        Allure.addAttachment("isAdhered",String.valueOf(isAdhered));
        Allure.addAttachment("ownAccount",String.valueOf(ownAccount));

        if(isAdhered){
            System.out.println("===================== EL CONTACTO ESTA AGENDADO =================");
            idAdhered = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customer.idAdheredAccount").toString());
            Allure.addAttachment("EL CONTACTO ESTA AGENDADO idAdhered",idAdhered);
        }else {
            System.out.println("=============================== EL CONTACTO NO ESTA AGENDADO =============================");
        }
    }
    @Step("Eliminar Contacto Agendado")
    public void eliminarContactoAgendado(){
        if(idAdhered == null){
            Allure.addAttachment("EL CONTACTO NO ESTA AGENDADO", "null");
        }else {
            validarResponse.validateResponses(deleteAdheredAccount(idAdhered));
        }
    }
    @Step("Agendar un nuevo contacto Mediante Cbu/Alias")
    public void agendarNuevoContacto(String perteneceA,String aliasCbu){
        String codigo;
        PandoraResponse response;

        //Obetnemeos el codigo segundo factor
        codigo = dobleFactor.getSecondFactor();
        //EJecutamos el servicio para agendar nuevo destinatario
        response = adhesionAccountNew(perteneceA,aliasCbu,codigo,String.valueOf(ownAccount));
        validarResponse.validateResponses(response);

        for (int i = 0 ; i<5; i++){
            if(Settings.flagServicioReejecucion){

                codigo = dobleFactor.getSecondFactor();
                response = adhesionAccountNew(perteneceA, aliasCbu, codigo, String.valueOf(ownAccount));
                validarResponse.validateResponses(response);
            }else{
                System.out.println("=================== SE AGENDO EL CONTACTO CORRECTAMENTE =====================");
                idAdhered = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.idAdheredAccount");
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);

        idTransaccion = JsonPath.parse(response.getBody().toPrettyString()).read("$.idTransaction").toString();

    }

    @Step("Realiza transferencia a tercero con cbu {cbu} desde cuenta {cuenta} con todos los campos")
    public void transferenciaATerceros(){
        //Realiza la transferencia
        String doblefactor;
        PandoraResponse response;
        int intentos = 0;

        //Obtenemos el doble factor
        doblefactor = dobleFactor.getSecondFactor();
        response = transferAccountMakeWithCapsCbu(doblefactor);

        validarResponse.validateResponses(response);
        while (intentos<3){
            if(Settings.flagServicioReejecucion){

                doblefactor = dobleFactor.getSecondFactor();
                response = transferAccountMakeWithCapsCbu(doblefactor);

                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);
    }

    @Step("Realiza transferencia a tercero con cbu {cbu} desde cuenta {cuenta} con todos los campos")
    public void transferenciaATercerosUsd(){
        //Realiza la transferencia
        String doblefactor;
        PandoraResponse response;
        int intentos = 0;

        //Obtenemos el doble factor
        doblefactor = dobleFactor.getSecondFactor();
        response = transferAccountMakeWithCapsCbuUsd(doblefactor);

        validarResponse.validateResponses(response);
        while (intentos<3){
            if(Settings.flagServicioReejecucion){

                doblefactor = dobleFactor.getSecondFactor();
                response = transferAccountMakeWithCapsCbuUsd(doblefactor);

                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);
    }

    @Step("Crea el ticket de transferencia a tercero con cbu {cbu} desde cuenta {cuenta} \" +\n" +
            "        \"con todos los campos menos la Descripcion")
    public void validarticketSinCampoDescripcion(){
        //Crea el ticket

        PandoraResponse response;
        int intentos = 0;
        SoftAssertions softflyTicket = new SoftAssertions();
        response = ticket();
        validarResponse.validateResponses(response);

        while (intentos<3){
            if(Settings.flagServicioReejecucion){
                response = ticket();
                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);

        //Valida el ticket
        String nombreEmisor  = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data._ticket.data.ticket.originAccountName").toString());
        String monto = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data._ticket.data.ticket.amount").toString());
        String cuentaEmisor = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data._ticket.data.ticket.originAccountLabel").toString());
        String cuilEmisor = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data._ticket.data.ticket.originAccountDocument").toString());
        String nombreDestinatario = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data._ticket.data.ticket.targetAccountDenomination").toString());
        String cbuDestinatario = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data._ticket.data.ticket.cbuTarget").toString());
        String cuilDestinatario = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data._ticket.data.ticket.targetAccountCuil").toString());
        softflyTicket.assertThat(Settings.name).isEqualTo(nombreEmisor);
        softflyTicket.assertThat("$ 1,00").isEqualTo(monto);
        softflyTicket.assertThat(Settings.ctaOrigen).isEqualTo(cuentaEmisor);
        softflyTicket.assertThat(Settings.cuil).isEqualTo(cuilEmisor);
        softflyTicket.assertThat(targetName).isEqualTo(nombreDestinatario);
        softflyTicket.assertThat(cbu).isEqualTo(cbuDestinatario);
        softflyTicket.assertThat(cuit).isEqualTo(cuilDestinatario);
        softflyTicket.assertAll();


        Allure.addAttachment("Nombre del Emisor :", String.valueOf(Settings.name));
        //Assert.assertTrue("NO SE PUDO MOSTRAR EL DATO NOMBRE EMISOR " +
          //      ":",!Settings.name.isEmpty() ) ;


    }

    @Step("Validar todos los datos emitidos en el ticket")
    public void validarticketTodosLosCampos(String cuentaOrigen){
        //Crea el ticket

        PandoraResponse response;
        int intentos = 0;
        SoftAssertions softflyTicket = new SoftAssertions();
        response = ticket();
        validarResponse.validateResponses(response);

        while (intentos<3){
            if(Settings.flagServicioReejecucion){
                response = ticket();
                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);


        //Valida el ticket
            String nombreEmisor  = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data._ticket.data.ticket.originAccountName").toString());
            String monto = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data._ticket.data.ticket.amount").toString());
            String cuentaEmisor = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data._ticket.data.ticket.originAccountLabel").toString());
            String cuilEmisor = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data._ticket.data.ticket.originAccountDocument").toString());
            String nombreDestinatario = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data._ticket.data.ticket.targetAccountDenomination").toString());
            String cbuDestinatario = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data._ticket.data.ticket.cbuTarget").toString());
            String cuilDestinatario = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data._ticket.data.ticket.targetAccountCuil").toString());
            String concepto = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().toPrettyString()).read("$.data._ticket.data.ticket.conceptLabel").toString());


            softflyTicket.assertThat(Settings.name).isEqualTo(nombreEmisor);
            softflyTicket.assertThat("$ 1,00").isEqualTo(monto);
            softflyTicket.assertThat(cuentaOrigen).isEqualTo(cuentaEmisor);
            softflyTicket.assertThat(Settings.cuil).isEqualTo(cuilEmisor);
            softflyTicket.assertThat(targetName).isEqualTo(nombreDestinatario);
            softflyTicket.assertThat(cbu).isEqualTo(cbuDestinatario);
            softflyTicket.assertThat(cuit).isEqualTo(cuilDestinatario);
            softflyTicket.assertThat("Alquileres").isEqualTo(concepto);
            softflyTicket.assertAll();

        Allure.addAttachment("Nombre del Emisor :", String.valueOf(Settings.name));
        Assert.assertTrue("NO SE PUDO MOSTRAR EL DATO NOMBRE EMISOR " +
                ":",!Settings.name.isEmpty() ) ;
        conceptLabel = utils.quitarCochetesJsonPath(JsonPath.parse(response.getBody().
                toPrettyString()).read("$.data._ticket.data.ticket." +
                "conceptLabel").toString());
        Allure.addAttachment("Descripción ",
                String.valueOf(conceptLabel));

    }

    @Step("Realiza transferencia a tercero con cbu {cbu} desde cuenta {cuenta} \" +\n" +
            "            \"con todos los campos sin la Descripcion")
    public void transferenciaATercerosSinCampoDescripcion(){
        //Realiza la transferencia
        String doblefactor;
        PandoraResponse response;
        int intentos = 0;

        //Obtenemos el doble factor
        doblefactor = dobleFactor.getSecondFactor();
        response = transferAccountMakeWithCapsCbuSinDescripcion(doblefactor);

        validarResponse.validateResponses(response);
        while (intentos<3){
            if(Settings.flagServicioReejecucion){

                doblefactor = dobleFactor.getSecondFactor();
                response = transferAccountMakeWithCapsCbuSinDescripcion(doblefactor);

                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }


        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);

        idTransaccion = JsonPath.parse(response.getBody().toPrettyString()).read("$.idTransaction").toString();

        System.out.println("monto PRUEBA" + amount);
    }

    @Step("Realiza transferencia a tercero a cbu {cbu} desde cuenta {cuenta}")
    public void transferenciaProgramadaTerceros(){
        String doblefactor;
        PandoraResponse response;

        int intentos = 0;
        String fecha;
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(1);
        fecha = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        doblefactor = dobleFactor.getSecondFactor();
        response = transfersAccountScheduleWithCapsCbu(fecha,doblefactor);

        validarResponse.validateResponses(response);

        while(intentos<3){
            if(Settings.flagServicioReejecucion){
                doblefactor = dobleFactor.getSecondFactor();
                response = transfersAccountScheduleWithCapsCbu(fecha,doblefactor);

                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " +
                Settings.endpoint,Settings.flagServicioReejecucion);
        idTransaccion = JsonPath.parse(response.getBody().
                toPrettyString()).read("$.idTransaction").toString();


    }
    @Step("Obtener datos de debito del credin - cbu origen")
    public void obtenerDatosDebitos(){
        PandoraResponse response;
        int intentos = 0;

        response = credinGenerateSearch(Settings.cbuTarget,"true");
        validarResponse.validateResponses(response);

        while (intentos<3){
            if(Settings.flagServicioReejecucion){
                response = credinGenerateSearch(Settings.cbuTarget,"true");
                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);
        debitName = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customerSearched.headline.firstName");
        debitCuit = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customerSearched.headline.cuil");
        debitNumber = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customerSearched.account.number");
        debitNumberBank = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customerSearched.account.numberBank");
        debitBrandType = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customerSearched.account.brandType");
        debitAlias = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customerSearched.account.aliasCbu");
        Allure.addAttachment("Debit name ",debitName);
        Allure.addAttachment("Debit Cuit ",debitCuit);
        Allure.addAttachment("Debit Number ",debitNumber);
        Allure.addAttachment("Debit Number Bank ",debitNumberBank);
        Allure.addAttachment("Debit Brand-Type ",debitBrandType);
        Allure.addAttachment("Debit Alias ",debitAlias);
    }
    @Step("Obtener datos de acreditacion del credin - cbu origen")
    public void obtenerDatosCredito(String cbu){
        PandoraResponse response;
        int intentos = 0;

        response = credinGenerateSearch(cbu,"");
        validarResponse.validateResponses(response);

        while (intentos<3){
            if(Settings.flagServicioReejecucion){
                response = credinGenerateSearch(cbu,"true");
                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        creditName = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customerSearched.headline.firstName");
        creditCuil = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customerSearched.headline.cuil");
        creditNumber = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customerSearched.account.number");
        creditNumberBank = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customerSearched.account.numberBank");
        creditBrandType = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customerSearched.account.brandType");
        creditAlias = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.customerSearched.account.aliasCbu");
        Allure.addAttachment("Debit name ",creditName);
        Allure.addAttachment("Debit Cuit ",creditCuil);
        Allure.addAttachment("Debit Number ",creditNumber);
        Allure.addAttachment("Debit Number Bank ",creditNumberBank);
        Allure.addAttachment("Debit Brand-Type ",creditBrandType);
        Allure.addAttachment("Debit Alias ",creditAlias);
    }

    @Step("Generacion de Credin")
    public void generarCredin(String cbuDestino){
        String codigo;
        PandoraResponse response;
        int intentos = 0;

        //Generacion de doble factor y ejecucion de servicio de generar credin
        codigo = dobleFactor.getSecondFactor();
        response = generateCredin(codigo,cbuDestino);
        validarResponse.validateResponses(response);

        while(intentos<3){
            if(Settings.flagServicioReejecucion){
                codigo = dobleFactor.getSecondFactor();
                response = generateCredin(codigo,cbuDestino);
                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
    }
    @Step("Consultar credin originante")
    public void consultarCredin(String tipo){
        PandoraResponse response;
        int cantidad = 0;
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd'T'HH:mm:ss.SSS'Z'"));
        int intentos = 0;

        //consulta de credines generados
        response = credinSearch(fecha,fecha,tipo);
        validarResponse.validateResponses(response);

        while (intentos<3){
            if(Settings.flagServicioReejecucion){
                response = credinSearch(fecha,fecha,tipo);
                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);

        //Validar que el array paginatedList no este vacio
        cantidad = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.paginatedData.paginatedList.elementList", List.class).size();
        Allure.addAttachment("Cantidad de credines encontrados",String.valueOf(cantidad));
        Assert.assertTrue("NO ENCUENTRA LOS CREDINES GENERADOS ",cantidad!=0);
    }
    @Step("Realizar transferencia a contacto agendado")
    public void trasnferenciaContactoAgendado(String belongsTo){
        PandoraResponse response;
        int intentos =0;

        response = transferAccountMakeWithCaps(belongsTo);
        validarResponse.validateResponses(response);

        while(intentos<3){
            if(Settings.flagServicioReejecucion){
                response = transferAccountMakeWithCaps(belongsTo);
                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);
    }
    @Step("Realizar transferencia programada a contacto agendado")
    public void trasnferenciaProgramadaContactoAgendado(String belongsTo){
        PandoraResponse response;
        int intentos =0;
        String fecha;
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(1);
        fecha = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));

        response = transfersAccountScheduleWithCaps(fecha,belongsTo);
        validarResponse.validateResponses(response);

        while(intentos<3){
            if(Settings.flagServicioReejecucion){
                response = transfersAccountScheduleWithCaps(fecha,belongsTo);
                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);
    }
    @Step("Realiza trasnferencias programada entre cuentas propias {cuentaOrigen} - {cuentaDestino} - {tarjetaDebito}")
    public void trasnferenciasProgramadaEntreCuentaPropias(){
        //Realiza la transferencia
        int intentos = 0;
        String fecha;
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(1);
        fecha = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        PandoraResponse response = transfersAccountScheduleMake(fecha);
        validarResponse.validateResponses(response);
        while (intentos<3){
            if(Settings.flagServicioReejecucion){
               response = transfersAccountScheduleMake(fecha);
                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);
    }

    @Step("Valida transferencia más antigua y transferencia más actual")
    public void validaHistorialDeTransferencias(){
        //Realiza la transferencia

        int cantidad = 0;
        String transferenciaMasAntigua;
        String ultimaTransferencia;

        PandoraResponse response;
        int intentos = 0;

        //Obtenemos el doble factor

        response = historialTransferencias();

        validarResponse.validateResponses(response);
        while (intentos<3){
            if(Settings.flagServicioReejecucion){


                response = historialTransferencias();

                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);

    //Valida cantidad cantidad de transferencias en el array

        cantidadDePaginas = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.totalPages");
        Assert.assertFalse("No hay resultados para esta búsqueda",cantidadDePaginas == 0);


        cantidad = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.transfers", List.class).size();
        transferenciaMasAntigua = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.transfers[9].creationDateTime");
        ultimaTransferencia = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.transfers[0].creationDateTime");
        Allure.addAttachment("Cantidad de transferencias por página", String.valueOf(cantidad));
        Allure.addAttachment("Transferencia más antigua", transferenciaMasAntigua);
        Allure.addAttachment("Transferencia Actual", ultimaTransferencia);

        Assert.assertFalse("La grilla tiene más de diez registros", cantidad > 10);

    }

    @Step("Valida transferencias de menos de un año")
    public void validaHistorialDeTransferenciasUnAñoAntiguedad(){
        //Realiza la transferencia

        String transferenciaMenosDeUnAñoAntiguedad;

        PandoraResponse response;
        int intentos = 0;

        //Obtener fecha Actual



        //Obtenemos el doble factor

        response = historialTransferenciasUnAño();

        validarResponse.validateResponses(response);
        while (intentos<3){
            if(Settings.flagServicioReejecucion){


                response = historialTransferenciasUnAño();

                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }
        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);

        //Valida cantidad cantidad de transferencias en el array


        cantidadDePaginas = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.totalPages");
        Assert.assertFalse("No hay resultados para esta búsqueda",cantidadDePaginas == 0);

        transferenciaMenosDeUnAñoAntiguedad = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.transfers[0].creationDateTime");
        Allure.addAttachment("Transferencia con menos de un año", transferenciaMenosDeUnAñoAntiguedad);



    }

    @Step("Valida transferencias por búsqueda por rango y nombre")
    public void validaHistorialDeTransferenciasPorRangoNombreODescripcion(String NameDescription){
        //Realiza la transferencia

        String transferenciaFiltradaPorRangoNombreODescripcion;

        PandoraResponse response;
        int intentos = 0;


        //Obtenemos el doble factor

        response = historialTransferenciasBusquedaRangoNombreODescripcion(NameDescription);

        validarResponse.validateResponses(response);
        while (intentos<3){
            if(Settings.flagServicioReejecucion){


                response = historialTransferenciasBusquedaRangoNombreODescripcion(NameDescription);

                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }


        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);

        //Valida cantidad cantidad de transferencias en el array


        cantidadDePaginas = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.totalPages");
        Assert.assertFalse("No hay resultados para esta búsqueda",cantidadDePaginas == 0);


        transferenciaFiltradaPorRangoNombreODescripcion = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.transfers[0].creationDateTime");
        Allure.addAttachment("Búsqueda por Rango y Nombre", transferenciaFiltradaPorRangoNombreODescripcion);
    }

    @Step("Validar búsquedas sin resultados")
    public void validaBusquedasDelHistorialSinResultados(String NameDescription){
        //Realiza la transferencia

        String transferenciaFiltradaPorRangoNombreODescripcion;

        PandoraResponse response;
        int intentos = 0;


        //Obtenemos el doble factor

        response = historialTransferenciasBusquedaRangoNombreODescripcion(NameDescription);

        validarResponse.validateResponses(response);
        while (intentos<3){
            if(Settings.flagServicioReejecucion){


                response = historialTransferenciasBusquedaRangoNombreODescripcion(NameDescription);

                validarResponse.validateResponses(response);
            }else{
                Allure.addAttachment("SERVICIO OK", Settings.endpoint);
                break;
            }
            intentos++;
        }


        Assert.assertFalse("SE INTENTO 4 VECES EL SERVICIO " + Settings.endpoint,Settings.flagServicioReejecucion);

        //Valida cantidad cantidad de transferencias en el array


        cantidadDePaginas = JsonPath.parse(response.getBody().toPrettyString()).read("$.data.totalPages");
        Assert.assertFalse("La búsqueda arrojó resultados",cantidadDePaginas != 0);


        Allure.addAttachment("Cantidad de resultados de la búsqueda :", String.valueOf(cantidadDePaginas));
    }


}





