package api;

import io.qameta.allure.Allure;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.Assert;
import utils.RestAssuredApi;
import utils.UtilsGP;

public class TransferenciasAPI {
    RestAssuredApi restAssuredApi = new RestAssuredApi();
    UtilsGP utilsGP = new UtilsGP();
    LoginAPI loginAPI = new LoginAPI();
    DobleFactorAPI dobleFactorAPI = new DobleFactorAPI();
    CompartidosAPI compartidosAPI = new CompartidosAPI();
    String debitNumber,debitNumberBank,debitAlias,debitBrandType,debitIdProduct,creditName,creditCuil,creditNumber,creditNumberBank,creditAlias,creditBrandType;


    private Response adhesionAccountNew(String perteneceA, String destinatarioCBU, String code2FA, String tipo){
        String body;
        body = utilsGP.leerContenidoArchivo("./Requests/transferencias/adhesionAccountNew.json");
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );
        body = body.replace("_destinatarioCBU_",destinatarioCBU).replace("_perteneceA_",perteneceA).replace("\"_ownAccount_\"",tipo).replace("_code2FA_",code2FA);

        return restAssuredApi.sendPostRequest("api/v1/execute/adhesion.account.new",headers,body);
    }
    private void deleteAdheredAccount(String idAdhered){
        String body;
        body = utilsGP.leerContenidoArchivo("./Requests/transferencias/deleteAdheredAccount.json");
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );
        body = body.replace("_idAdhered_",idAdhered);

        restAssuredApi.sendPostRequest("api/v1/execute/delete.adheredAccount", headers, body);
    }
    private Response listAdheredAccounts(){
        String body;
        body = utilsGP.leerContenidoArchivo("./Requests/transferencias/listAdheredAccounts.json");
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );
        return restAssuredApi.sendPostRequest("api/v1/execute/list.adheredAccounts", headers, body);
    }
    private Response transfersAccountSearch(String cbu,String tipo){
        String body;
        body = utilsGP.leerContenidoArchivo("./Requests/transferencias/accountTransferSearch.json");
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );
        body =body.replace("_cbu_",cbu).replace("_typeAccount_",tipo);

        return restAssuredApi.sendPostRequest("api/v1/execute/account.transfer.search",headers,body);
    }
    private Response credinGenerateSearch(String cbu, String owner){
        String body;
        body = utilsGP.leerContenidoArchivo("./Requests/transferencias/credinGenerateSearch.json");
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );
        body = body.replace("_cbu_",cbu).replace("_owner_",owner);
        return restAssuredApi.sendPostRequest("api/v1/execute/credin.generate.search",headers,body);
    }
    private Response generateCredin(String code,String cbuOrigen,String cbuDestino){
        String body;
        body = utilsGP.leerContenidoArchivo("./Requests/transferencias/credinGenerate.json");
        Headers headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("Authorization", loginAPI.accessToken)
        );
       body = body.replace("_debitName_",loginAPI.name)
               .replace("_debitCuit_",loginAPI.cuil)
               .replace("_debitNumber_",debitNumber)
               .replace("_debitAlias_",debitAlias)
               .replace("_debitBrandType_",debitBrandType)
               .replace("_debitNumberBank_",debitNumberBank)
               .replace("_debitIdProduct_",debitIdProduct)
               .replace("_creditOwner_",creditName)
               .replace("_creditCuit_",creditCuil)
               .replace("_creidtNumber_",creditNumber)
               .replace("_cbuDestino_",cbuDestino)
               .replace("_creditAlias_",creditAlias)
               .replace("_creditBrandtype_",creditBrandType)
               .replace("_creditNumberBank_",creditNumberBank)
               .replace("_cbuOrigen_",cbuOrigen)
               .replace("_code_",code);
        return restAssuredApi.sendPostRequest("api/v1/execute/credin.generate",headers,body);
    }
    public void  agregarDestinatarioAgendaCBU(String aliasCbu, String perteneceA){
        String idAdhered,codigoDobleFactor,data;
        boolean isAdhered,ownAccount;
        //boolean flagEliminacion =false;
        //boolean flagAgendado = false;
        Response res;

        //-------VALIDO SI EL CONTACTO YA ESTA AGENDADO, SI LLEGA A ESTAR AGENDADO LO ELIMINO-----------------//
        if(Character.isDigit(aliasCbu.charAt(0))){
            System.out.println("======================= TIPO CBU ===========================");
            res = transfersAccountSearch(aliasCbu,"CBU");
            for (int i = 0; i<4;i++){
                if(!ValidarCodeResponses.flagServicioReejecucion){
                    if(Character.isDigit(aliasCbu.charAt(0))){
                        System.out.println("======================= TIPO CBU ===========================");
                        res = transfersAccountSearch(aliasCbu,"CBU");
                    }
                }else{
                   // flagEliminacion = true;
                    break;
                }
            }
            if(!ValidarCodeResponses.flagServicioReejecucion){
                Assert.fail("SE INTENTO 3 VECES Y EL SERVICIO FALLO ELIMINAR CBU AGENDADO");
            }
            isAdhered = restAssuredApi.getBooleanFromJson(res,"data.isAdhered");
            ownAccount = restAssuredApi.getBooleanFromJson(res,"data.ownAccount");
            if(isAdhered){
                System.out.println("============================= EL CONTACTO ESTA AGENDADO ====================");
                System.out.println("============================= OBTENER ID ADHERED ============================");
                idAdhered = utilsGP.quitarCochetesJsonPath(restAssuredApi.getStringFromJson(res,"data.customer.idAdheredAccount"));
                System.out.println("========================= ID ADHERED : "+ idAdhered);
                System.out.println("============================= ELIMINANDO CONTACTO ==============================");
                deleteAdheredAccount(idAdhered);
            }else{
                System.out.println("=============================== EL CONTACTO NO ESTA AGENDANDO =============================");
            }
        }else{
            System.out.println("======================= TIPO ALIAS ===========================");
            res = transfersAccountSearch(aliasCbu,"ALIAS");
            for (int i =0;i<4;i++){
                if(!ValidarCodeResponses.flagServicioReejecucion){
                    System.out.println("======================= TIPO ALIAS ===========================");
                    res = transfersAccountSearch(aliasCbu,"ALIAS");
                }else{
                    break;
                }
            }
            if(!ValidarCodeResponses.flagServicioReejecucion){
                Assert.fail("SE INTENTO 3 VECES Y EL SERVICIO FALLO ALIMINAR ALIAS");
            }
            isAdhered = restAssuredApi.getBooleanFromJson(res,"data.isAdhered");
            ownAccount = restAssuredApi.getBooleanFromJson(res,"data.ownAccount");
            if(isAdhered){
                System.out.println("============================= EL CONTACTO ESTA AGENDADO ====================");
                System.out.println("============================= OBTENER ID ADHERED ============================");
                idAdhered = utilsGP.quitarCochetesJsonPath(restAssuredApi.getStringFromJson(res,"data.customer.idAdheredAccount"));
                System.out.println("========================= ID ADHERED : "+ idAdhered);
                System.out.println("============================= ELIMINANDO CONTACTO ==============================");
                deleteAdheredAccount(idAdhered);
            }else{
                System.out.println("=============================== EL CONTACTO NO ESTA AGENDANDO =============================");
            }
        }
        //---------------------------------------------------------------------------------------------------------//
        //------- -------------------------------AGENDAR EL NUEVO CBU----------------------------------------------//
        codigoDobleFactor = dobleFactorAPI.getSecondFactor();

        System.out.println("======================== OWN ACCOUNT : "+ ownAccount);

        res = adhesionAccountNew(perteneceA, aliasCbu, codigoDobleFactor, String.valueOf(ownAccount));

       // data = utilsGP.quitarCochetesJsonPath(restAssuredApi.getStringFromJson(res,"data"));
            for (int i = 0 ; i<3; i++){
                if(!ValidarCodeResponses.flagServicioReejecucion){

                    codigoDobleFactor = dobleFactorAPI.getSecondFactor();
                    res = adhesionAccountNew(perteneceA, aliasCbu, codigoDobleFactor, String.valueOf(ownAccount));

                   // data = utilsGP.quitarCochetesJsonPath(restAssuredApi.getStringFromJson(res,"data"));
            }else{
                    System.out.println("=================== SE AGENDO EL CONTACTO CORRECTAMENTE =====================");
                    //flagAgendado = true;
                    break;
                }
        }
         if(!ValidarCodeResponses.flagServicioReejecucion){
             Assert.fail("FALLO 3 VECES EL SERVICIO AGENDA NUEVO ALIAS/CBU");
         }
    }
    public void eliminarContantoAgendado (String aliasCbu) {
        String idAdhered;
        boolean isAdhered,ownAccount;
        //boolean flag = false;
        Response res;
        //-------VALIDO SI EL CONTACTO YA ESTA AGENDADO, SI LLEGA A ESTAR AGENDADO LO ELIMINO-----------------
        if(Character.isDigit(aliasCbu.charAt(0))){
            System.out.println("======================= TIPO CBU ===========================");
            res = transfersAccountSearch(aliasCbu,"CBU");
            for (int i = 1; i<4;i++){
               // utilsGP.fixedWait(3000);
                if(!ValidarCodeResponses.flagServicioReejecucion){
                    if(Character.isDigit(aliasCbu.charAt(0))){
                        System.out.println("======================= TIPO CBU ===========================");
                       // utilsGP.fixedWait(3000);
                        res = transfersAccountSearch(aliasCbu,"CBU");
                    }
                }else{
                    //flag = true;
                    break;
                }
            }
            if(!ValidarCodeResponses.flagServicioReejecucion){
                Assert.fail("SE INTENTO 3 VECES - SERVICIO NO OK TRANSFER.ACCOUNT.SEARCH POR CBU - POSIBLE UPS");
            }
            isAdhered = restAssuredApi.getBooleanFromJson(res,"data.isAdhered");
            ownAccount = restAssuredApi.getBooleanFromJson(res,"data.ownAccount");
            if(isAdhered){
                System.out.println("============================= EL CONTACTO ESTA AGENDADO ====================");
                System.out.println("============================= OBTENER ID ADHERED ============================");
                idAdhered = utilsGP.quitarCochetesJsonPath(restAssuredApi.getStringFromJson(res,"data.customer.idAdheredAccount"));
                System.out.println("========================= ID ADHERED : "+ idAdhered);
                System.out.println("============================= ELIMINANDO CONTACTO ==============================");
                deleteAdheredAccount(idAdhered);
            }else{
                System.out.println("=============================== EL CONTACTO NO ESTA AGENDANDO =============================");
            }
        }else{
            System.out.println("======================= TIPO ALIAS ===========================");
            res = transfersAccountSearch(aliasCbu,"ALIAS");
            for (int i =0;i<4;i++){
                if(!ValidarCodeResponses.flagServicioReejecucion){
                    System.out.println("======================= TIPO ALIAS ===========================");
                    res = transfersAccountSearch(aliasCbu,"ALIAS");
                }else{
                   // flag=true;
                    break;
                }
            }
            if(!ValidarCodeResponses.flagServicioReejecucion){
                Assert.fail("SE INTENTO 3 VECES - SERVICIO NO OK TRANSFER.ACCOUNT.SEARCH POR ALIAS - POSIBLE UPS");
            }
            isAdhered = restAssuredApi.getBooleanFromJson(res,"data.isAdhered");
            ownAccount = restAssuredApi.getBooleanFromJson(res,"data.ownAccount");
            if(isAdhered){
                System.out.println("============================= EL CONTACTO ESTA AGENDADO ====================");
                System.out.println("============================= OBTENER ID ADHERED ============================");
                idAdhered = utilsGP.quitarCochetesJsonPath(restAssuredApi.getStringFromJson(res,"data.customer.idAdheredAccount"));
                System.out.println("========================= ID ADHERED : "+ idAdhered);
                System.out.println("============================= ELIMINANDO CONTACTO ==============================");
                deleteAdheredAccount(idAdhered);
            }else{
                System.out.println("=============================== EL CONTACTO NO ESTA AGENDANDO =============================");
            }
        }
    }
    public int verificarMontoAPI(String perteneceA){
        int balance;
        Response res = compartidosAPI.widgetsAccount();

        if(perteneceA.contains("USD")){
            balance = Integer.parseInt(restAssuredApi.getStringFromJson(res,"data.accounts.find { it.shortLabel == '"+perteneceA+"'}.labelWithBalance").split("USD ")[2].split(",")[0].replace(".",""));

        }else{
            balance = Integer.parseInt(restAssuredApi.getStringFromJson(res,"data.accounts.find { it.shortLabel == '"+perteneceA+"'}.labelWithBalance").split("\\$ ")[2].split(",")[0].replace(".",""));
        }

        return balance;
    }

    public void generarDebin(String cbuOrigen, String cbuDestino, String card){
        String dobleFactor;
        Response res;
        int intentos = 0;
        int intetos2FA = 0;

        res = compartidosAPI.getwidgetsCreditCards();

        debitIdProduct = utilsGP.quitarCochetesJsonPath(restAssuredApi.getStringFromJson(res,"data.creditCards.findAll {it.label == 'Visa Débito **** **** **** "+card+"'}.idProduct"));

        res = credinGenerateSearch(cbuOrigen,"true");
        while (intentos<4){
            if(!ValidarCodeResponses.flagServicioReejecucion){
                System.out.println("SE REEJECUTA EL SERVICIO");
                res = credinGenerateSearch(cbuOrigen,"true");
            }else{
                Allure.addAttachment("EL SERVICIO credin.generate.search (Datos debitos) Paso OK intentos",String.valueOf((intentos+1)));
                System.out.println("EL SERVICIO PASO OK DESPUES DE INTENTOS  = " + (intentos+1));
                break;
            }
            intentos++;
        }
        Allure.addAttachment("EL SERVICIO CREDIN.GENERATE.SEARCH (Datos Debitos) FALLO - intentos ",String.valueOf((intentos+1)));
        Assert.assertFalse("FALLO POR CANTIDAD DE INTETNOS 4",!ValidarCodeResponses.flagServicioReejecucion);

        debitNumber = restAssuredApi.getStringFromJson(res,"data.customerSearched.account.number");
        debitNumberBank =  restAssuredApi.getStringFromJson(res,"data.customerSearched.account.numberBank");
        debitAlias = restAssuredApi.getStringFromJson(res,"data.customerSearched.account.aliasCbu");
        debitBrandType = restAssuredApi.getStringFromJson(res,"data.customerSearched.account.brandType");

        res = credinGenerateSearch(cbuDestino,"");
        intentos = 0;
        while (intentos<4){
            if(restAssuredApi.getStringFromJson(res,"code").contains("BAK001E")){
                System.out.println("SE REEJECUTA EL SERVICIO RANCIO");
                res = credinGenerateSearch(cbuDestino,"");
            }else{
                Allure.addAttachment("EL SERVICIO credin.generate.search (Datos creditos) Paso OK intentos",String.valueOf((intentos+1)));
                System.out.println("EL SERVICIO PASO OK DESPUES DE INTENTOS  = " + (intentos+1));
                break;
            }
            intentos++;
        }
        Allure.addAttachment("EL SERVICIO CREDIN.GENERATE.SEARCH (Datos Creditos) FALLO - intentos ",String.valueOf((intentos+1)));
        Assert.assertFalse("FALLO POR CANTIDAD DE INTETNOS 4",intentos==4);
        creditName = restAssuredApi.getStringFromJson(res,"data.customerSearched.headline.firstName");
        creditCuil = restAssuredApi.getStringFromJson(res,"data.customerSearched.headline.cuil");
        creditNumber = restAssuredApi.getStringFromJson(res,"data.customerSearched.account.number");
        creditNumberBank = restAssuredApi.getStringFromJson(res,"data.customerSearched.account.numberBank");
        creditAlias = restAssuredApi.getStringFromJson(res,"data.customerSearched.account.aliasCbu");
        creditBrandType = restAssuredApi.getStringFromJson(res,"data.customerSearched.account.brandType");

        dobleFactor = dobleFactorAPI.getSecondFactor();
        res = generateCredin(dobleFactor,cbuOrigen,cbuDestino);
        intentos = 0;
        while(intentos<4){
            while(intetos2FA < 3){
                if(!ValidarCodeResponses.flagServicioReejecucion){
                    System.out.println("VUELVE POR DOBLE FACTOR");
                    dobleFactor = dobleFactorAPI.getSecondFactor();
                    res = generateCredin(dobleFactor,cbuOrigen,cbuDestino);
                }else {
                    System.out.println("NO FALLO POR 2FA");
                    break;
                }
                intetos2FA++;
            }
            if(restAssuredApi.getStringFromJson(res,"code").contains("BAK001E")){
                dobleFactor = dobleFactorAPI.getSecondFactor();
                res = generateCredin(dobleFactor,cbuOrigen,cbuDestino);
            }else {
                System.out.println("EL SERVICIO PASO OK DESPUES DE INTENTOS  = " + (intentos +1));
                break;
            }
            intentos++;
        }
        Assert.assertFalse("FALLO POR CANTIDAD DE INTETNOS 4",intentos==4);

    }

}
