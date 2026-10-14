package GP.SanityTest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.jersey.spi.inject.Errors;
import db.ConexionDB;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import pages.botonesgenericos.BotonesGenericosPage;
import steps.botonesgenericos.BotonesGenericosSteps;
import steps.login.LoginSteps;
import utils.UtilsGP;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static db.ConexionDB.cerrarConexion;

public class Sanity {

    LoginSteps loginSteps = new LoginSteps();

    @Test
    @DisplayName("Sanity test para login de usuarios")
    public void SanityLogin() throws Exception {
        String dataBase = System.getProperty("dataBase");
        String bank = System.getProperty("bank");
        switch (dataBase){
            case "mysql":
                Connection conn = ConexionDB.obtenerConexion();
                try {
                    String consulta = "select * from usuarios where bank = ?";
                    PreparedStatement statement = conn.prepareStatement(consulta);
                    statement.setString(1, bank);
                    ResultSet resultado = statement.executeQuery();
                    while (resultado.next()) {
                        String username = resultado.getString("name");
                        System.out.println("DATO OBTENIDO: " + username);
                        loginSteps.loginParaSanityTest(username);
                    }
                }catch (Exception e){
                    //System.out.println("Error: DBmysql" + e.getMessage());
                    throw new Exception("Error: DBmysql" + e.getMessage());
                }

            case "json":
                try {
                    Reader myReader = Files.newBufferedReader(Paths.get("src/main/java/db/usuarios.json"));
                    Gson gson = new Gson();
                    JsonObject js = gson.fromJson(myReader, JsonObject.class);
                    JsonArray arrayJson = (JsonArray) js.get("usuarios");
                    for (JsonElement datoJson : arrayJson) {
                        JsonObject datoObject = datoJson.getAsJsonObject();
                        String valorBank = datoObject.get("bank").getAsString();
                        String valorId = datoObject.get("name").getAsString();
                        if (valorBank.equals(bank) && !valorId.isEmpty()){
                            System.out.println("DATO OBTENIDO: " + valorId);
                            loginSteps.loginParaSanityTest(valorId);
                        }
                    }
                }catch (Exception e){
                    //System.out.println("Error DBjson: " + e.getMessage());
                    throw new Exception("Error: DBmysql" + e.getMessage());

                }
            default:
                break;
        }
    }
    @Test
    @DisplayName("Sanity test consulta de base de datos")
    public void SanityConsultaDB() {
        try {
            //codigo de pago
            String result1 = UtilsGP.consultaDB("codigo_de_pago", "ECOWIFI", "name");
            System.out.println("codigo_de_pago " + result1);
            //limite de transferencia
            String result2 = UtilsGP.consultaDB("limite_transferencias", "santafe", "amount");
            System.out.println("limite_transferencias " + result2);
            //prestamos presonales
            String result3 = UtilsGP.consultaDB("prestamos_personales", "volpatomonica", "accumulated");
            System.out.println("prestamos_presonales " + result3);
            //suscribir fci
            String result4 = UtilsGP.consultaDB("suscribir_fci", "SantaCruz", "amount_subtracted");
            System.out.println("suscribir_fci " + result4);


        } catch (Exception e) {
            System.out.println("Error DBjson: " + e.getMessage());
        }
    }
    @Test
    @DisplayName("Sanity test update en base de datos")
    public void SanityUpdateDB() {
        try {
            String db, id, columna, nuevoDato;
            db = "dbtest";
            id = "test2";
            columna = "code";
            nuevoDato = "4500";
            //update
            UtilsGP.actualizarDB(db,id,columna,nuevoDato);
            //validacion
            String datoActualizado = UtilsGP.consultaDB(db, id,columna);
            Assert.assertEquals("No se actualizo la base de datos", nuevoDato, datoActualizado);
        } catch (Exception e) {
            System.out.println("Error DBjson: " + e.getMessage());
        }
    }
    @Test
    @DisplayName("Sanity test update en base de datos")
    public void testLoginUnicoUsuario() {
        try {
            loginSteps.loginParaSanityTest("prueba");
        } catch (Exception e) {
            System.out.println("Error DBjson: " + e.getMessage());
        }
    }

    @After
    public void teardown() throws IOException, InterruptedException {
        global.practia.jemeinsamstark.web.core.WebDriver.closeDriver();
        cerrarConexion();
    }
}

