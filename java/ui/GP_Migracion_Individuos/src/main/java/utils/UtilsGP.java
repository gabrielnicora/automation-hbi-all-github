package utils;

import com.github.fge.jackson.jsonpointer.JsonNodeResolver;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import db.ConexionDB;
import global.practia.jemeinsamstark.web.core.ConfigHelper;
import global.practia.jemeinsamstark.web.elements.PandoraElement;
import global.practia.jemeinsamstark.web.pages.Page;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.io.JsonStringEncoder;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.doblefactor.DoblefactorPage;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.math.BigDecimal;



public class UtilsGP extends Page {

    private String mainTab = global.practia.jemeinsamstark.web.core.WebDriver.getInstance().getWindowHandle();


    public boolean validarExistenciaArchivo(String nombreArchivoFragmento) throws InterruptedException {
        String user = System.getProperty("user.name");
        File directory = new File("C:\\Users\\" + user + "\\Downloads");
        int intentos = 0;

        while (intentos < 3) {
            Thread.sleep(4000);
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                        String nombreArchivo = new String(file.getName().getBytes(Charset.defaultCharset()));
                        if (nombreArchivo.contains(nombreArchivoFragmento)) {
                            Thread.sleep(3000); // Esperar 3 segundos antes de intentar borrar el archivo
                            if (file.delete()) {
                                return true;
                            } else {
                                System.out.println("No se pudo borrar el archivo.");
                                return false;
                            }
                        }
                }
            }
            // Incrementar el contador de intentos y esperar antes de volver a intentar
            intentos++;
            System.out.println("El archivo no se encontró en el intento número " + intentos + ". Reintentando...");
        }

        System.out.println("El archivo no se encontró después de 3 intentos.");
        return false;
    }


    public boolean validarExistenciaArchivoSinEliminarlo(String nombreArchivoFragmento) throws InterruptedException {
        String user = System.getProperty("user.name");
        File directory = new File("C:\\Users\\" + user + "\\Downloads");
        int intentos = 0;

        while (intentos < 3) {
            Thread.sleep(4000);
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    String nombreArchivo = new String(file.getName().getBytes(Charset.defaultCharset()));
                    if (nombreArchivo.contains(nombreArchivoFragmento)) {
                        Thread.sleep(3000); // Esperar 3 segundos antes de intentar borrar el archivo
                        if (file.exists()) {
                            return true;
                        } else {
                            System.out.println("El archivo no existe");
                            return false;
                        }
                    }
                }
            }
            // Incrementar el contador de intentos y esperar antes de volver a intentar
            intentos++;
            System.out.println("El archivo no se encontró en el intento número " + intentos + ". Reintentando...");
        }

        System.out.println("El archivo no se encontró después de 3 intentos.");
        return false;
    }


    public static String removerAcentos(String src) {
        return StringUtils.stripAccents(src);
    }

    public void keyDown(int repeticiones) {
        Actions keyDown = new Actions(global.practia.jemeinsamstark.web.core.WebDriver.getInstance());
        for (int i = 0; i < repeticiones; i++) {
            keyDown.sendKeys(Keys.DOWN);
        }
        keyDown.sendKeys(Keys.ENTER);
        keyDown.click().perform();
    }


    public void keyDownAndSelectElement(String textoValidar) {
        WebDriver driver = global.practia.jemeinsamstark.web.core.WebDriver.getInstance();
        Actions actions = new Actions(driver);
        boolean elementoEncontrado = false;
        WebDriverWait wait = new WebDriverWait(global.practia.jemeinsamstark.web.core.WebDriver.getInstance(), 10);

        for (int i = 0; i < 10; i++) {
            By elementoLocator = By.xpath("//*[contains(text(), '" + textoValidar + "')]");
            WebElement elemento = driver.findElement(elementoLocator);
            actions.sendKeys(Keys.DOWN).perform();
            if (elemento.isDisplayed()) {
                elemento.click();
                wait.until(ExpectedConditions.stalenessOf(elemento));
                elemento = driver.findElement(elementoLocator);
                System.out.println(elemento.getText());
                elementoEncontrado = true;
                break;
            }
        }
        if (!elementoEncontrado) {
            throw new NoSuchElementException("Elemento no encontrado: " + textoValidar);
        }
    }

    public static String generarString(int largo, String string) {
        String cadena = RandomStringUtils.random(largo, string);
        return cadena;
    }

    public void NuevoTab() throws InterruptedException {
        ((JavascriptExecutor) global.practia.jemeinsamstark.web.core.WebDriver.getInstance()).executeScript("window.open()");
        Thread.sleep(5000);
    }

    public void ManageTab() {
        String newTab = "";

        Set<String> tabs = global.practia.jemeinsamstark.web.core.WebDriver.getInstance().getWindowHandles();
        for (String actual : tabs) {
            if (!actual.equalsIgnoreCase(mainTab)) {
                global.practia.jemeinsamstark.web.core.WebDriver.getInstance().switchTo().window(actual);
                newTab = actual;
            }
        }
    }

    public boolean ManageTabPorParametro(int targetTabNumber) throws InterruptedException {
        Thread.sleep(5000);

        String mainTab = global.practia.jemeinsamstark.web.core.WebDriver.getInstance().getWindowHandle();
        String newTab = "";

        Set<String> tabs = global.practia.jemeinsamstark.web.core.WebDriver.getInstance().getWindowHandles();

        int tabCounter = 1;

        for (String actual : tabs) {
            if (!actual.equalsIgnoreCase(mainTab)) {
                if (tabCounter == targetTabNumber) {
                    global.practia.jemeinsamstark.web.core.WebDriver.getInstance().switchTo().window(actual);
                    newTab = actual;
                    break;
                }
            }
            tabCounter++;
        }


        return !newTab.isEmpty() && !newTab.equalsIgnoreCase(mainTab);
    }


    public void backMainTap() {
        global.practia.jemeinsamstark.web.core.WebDriver.getInstance().close();
        UtilsGP.fixedWait(3000);
        global.practia.jemeinsamstark.web.core.WebDriver.getInstance().switchTo().window(mainTab);
    }

    public void paginaActual() {
        global.practia.jemeinsamstark.web.core.WebDriver.getInstance().switchTo().window(mainTab);
    }

    public void NavegarUtilsWeb(String URL_Seccion) {
        global.practia.jemeinsamstark.web.core.WebDriver.getInstance().get(URL_Seccion);

    }

    public void aceptarAlert() {
        global.practia.jemeinsamstark.web.core.WebDriver.getInstance().switchTo().alert().accept();
    }

    /*//update db json
    public static void actualizarDBjson(String nombreDb, String id, String claveDato, String nuevoDato) {
        try {
            Reader myReader = Files.newBufferedReader(Paths.get("src/main/java/db/" + nombreDb + ".json"));
            Gson gson = new Gson();
            JsonObject js = gson.fromJson(myReader, JsonObject.class);
            JsonArray arrayJson = (JsonArray) js.get(nombreDb);

            for (JsonElement datoJson : arrayJson) {
                JsonObject datoObject = datoJson.getAsJsonObject();
                System.out.println(datoObject.has(id));
                if (datoObject.has(id)) {
                    System.out.println("ENTRA AQUI "+datoObject.getAsString().equals(id));
                    if (claveDato != null && claveDato != "password" && claveDato != "amount") {
                        datoObject.addProperty(claveDato, nuevoDato);
                    } else {
                        datoObject.addProperty(id, nuevoDato);
                    }
                    break;
                }
            }
            Writer myWriter = Files.newBufferedWriter(Paths.get("src/main/java/db/" + nombreDb + ".json"));
            gson.toJson(js, myWriter);
            myWriter.close();

        } catch (IOException e) {
            System.out.print("ERROR" + e);
        }
    }
    //select db json
    public static String consultaDBjson(String nombreDb, String id, String claveDato) {
        try {
            Reader myReader = Files.newBufferedReader(Paths.get("src/main/java/db/" + nombreDb + ".json"));
            Gson gson = new Gson();
            JsonObject js = gson.fromJson(myReader, JsonObject.class);
            JsonArray arrayJson = (JsonArray) js.get(nombreDb);
            for (JsonElement datoJson : arrayJson) {
                JsonObject datoObject = datoJson.getAsJsonObject();
                String valorId = String.valueOf(datoObject.get(id));
                if (!valorId.equals("null")) {
                    String valorIdReturn = datoObject.get(id).getAsString();
                    if (claveDato != null && claveDato != "password" && claveDato != "amount") {
                        String valorDato = datoObject.get(claveDato).getAsString();
                        return valorDato;
                    } else {
                        return valorIdReturn;
                    }
                }
            }
        } catch (IOException e) {
            System.out.print("ERROR" + e);
        }
        return null;
    }*/
    public static void actualizarDBjson(String nombreDb, String id, String claveDato, String nuevoDato) {
        try {
            Reader myReader = Files.newBufferedReader(Paths.get("src/main/java/db/" + nombreDb + ".json"));
            Gson gson = new Gson();
            JsonObject js = gson.fromJson(myReader, JsonObject.class);
            JsonArray arrayJson = (JsonArray) js.get(nombreDb);
            for (JsonElement datoJson : arrayJson) {
                JsonObject datoObject = datoJson.getAsJsonObject();
                String valorId = datoObject.get("name").getAsString();
                if (valorId.equals(id)) {
                    if (!claveDato.isEmpty()) {
                        datoObject.addProperty(claveDato, nuevoDato);
                        System.out.println("id: " + valorId + ", columna: " + claveDato + ", nuevo dato: " + nuevoDato);
                    }
                    break;
                }
            }
            Writer myWriter = Files.newBufferedWriter(Paths.get("src/main/java/db/" + nombreDb + ".json"));
            gson.toJson(js, myWriter);
            myWriter.close();
        } catch (IOException e) {
            System.out.print("ERROR" + e);
        }
    }

    public static String consultaDBjson(String nombreDb, String id, String claveDato) {
        try {
            Reader myReader = Files.newBufferedReader(Paths.get("src/main/java/db/" + nombreDb + ".json"));
            Gson gson = new Gson();
            JsonObject js = gson.fromJson(myReader, JsonObject.class);
            JsonArray arrayJson = (JsonArray) js.get(nombreDb);
            for (JsonElement datoJson : arrayJson) {
                JsonObject datoObject = datoJson.getAsJsonObject();
                String valorId = datoObject.get("name").getAsString();
                if (valorId.equals(id)) {
                    if (!claveDato.isEmpty()) {
                        String valorDato = datoObject.get(claveDato).getAsString();
                        return valorDato;
                    } else {
                        System.out.println("NO HAY DATO");
                    }
                }
            }
        } catch (IOException e) {
            System.out.print("ERROR" + e);
        }
        return null;
    }

    public String cambioDeClave(String claveActual) {
        String practia = "Practia";
        try {
            int numeros = Integer.parseInt(StringUtils.substringAfter(claveActual, practia));
            numeros++;
            String numerosString = String.valueOf(numeros);
            practia += numerosString;
        } catch (Exception ex) {
            System.out.println("No se pudo modificar la clave" + ex.getMessage());
        }
        return practia;
    }

    //select db sql
    public static String consultaDBsql(String tabla, String id, String columna) {
        Connection conn = ConexionDB.obtenerConexion();
        try {
            String consulta = "select * from " + tabla + " where name = ?";
            PreparedStatement statement = conn.prepareStatement(consulta);
            statement.setString(1, id); // establece el valor del parámetro 1
            ResultSet resultado = statement.executeQuery();
            if (resultado.next()) {
                do {
                    String dato = resultado.getString(columna);
                    System.out.println("DATO OBTENIDO: " + dato);
                    return dato;
                } while (resultado.next());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    public static LinkedHashMap<String, String> consultaDBsqlTag(String tag) {
        Connection conn = ConexionDB.obtenerConexion();
        LinkedHashMap<String,String> dato = new LinkedHashMap<>();
        try {
            String consulta = "select * from usuarios  where tag = ?";
            PreparedStatement statement = conn.prepareStatement(consulta);
            statement.setString(1, tag); // establece el valor del parámetro 1
            ResultSet resultado = statement.executeQuery();
            if (resultado.next()) {
                do {
                    dato.put("nombre", resultado.getString("name"));
                    dato.put("password", resultado.getString("password"));
                    dato.put("tarjetaAOperar", resultado.getString("tarjetaAOperar"));
                    dato.put("cuentaOrigen", resultado.getString("cuentaOrigen"));
                    dato.put("cuentaDestino", resultado.getString("cuentaDestino"));
                    dato.put("cbu", resultado.getString("cbu"));
                    dato.put("alias", resultado.getString("alias"));
                    System.out.println("DATO OBTENIDO: " + dato.values());
                } while (resultado.next());
            }
            return dato;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return dato=null;
        }
    }

    //update db sql
    public static void actualizarDBsql(String tabla, String id, String columna, String nuevoDato) {
        Connection conn = ConexionDB.obtenerConexion();
        try {
            String consulta = "update " + tabla + " set " + columna + " = ? where name = ?";
            PreparedStatement statement = conn.prepareStatement(consulta);
            statement.setString(1, nuevoDato); // establece el valor del parámetro 1
            statement.setString(2, id); // establece el valor del parámetro 2
            int filasActualizada = statement.executeUpdate();
            if (filasActualizada > 0) {
                System.out.println("UPDATE REALIZADO");
            } else {
                System.out.println("NO SE ACTUALIZARON REGISTROS");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static String consultaDB(String tabla, String id, String columna) {
        String dataBase = System.getProperty("dataBase");
        switch (dataBase) {
            case "mysql":
                return consultaDBsql(tabla, id, columna);
            case "json":
                return consultaDBjson(tabla, id, columna);
            default:
                return null;
        }
    }

    public static void actualizarDB(String tabla, String id, String columna, String nuevoDato) {
        String dataBase = System.getProperty("dataBase");
        switch (dataBase) {
            case "mysql":
                actualizarDBsql(tabla, id, columna, nuevoDato);
            case "json":
                actualizarDBjson(tabla, id, columna, nuevoDato);
            default:
                break;
        }
    }

    public static void capturarMensajeEmergente() {
        try {
            DoblefactorPage doblefactor = new DoblefactorPage();
            doblefactor.getMensajeEmergente().isDisplayed();
            String mensaje= doblefactor.getMensajeEmergente().getText();
            throw new Error("Mensaje emergente presente: " + mensaje);
        }catch (Exception e){
            System.out.println("No hay mensaje emergente de error");
        }

    }
    /**
     * Método para saber si un elemento está presente
     * @param locator identificador del elemento en la página
     * @param intentos cantidad máxima de reintentos para localizarlo
     * @return true si fue encontrado, false en caso contrario
     * @throws InterruptedException
     */
    public boolean estaPresente(By locator, int intentos) {
        List<WebElement> elementosEncontrados;
        for (int reintentos = 0; reintentos < intentos; reintentos++) {
            try {Thread.sleep(500);}
            catch (InterruptedException e) {e.printStackTrace();}
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            elementosEncontrados = driver.findElements(locator);
            if (!elementosEncontrados.isEmpty()) {
                driver.manage().timeouts().implicitlyWait(ConfigHelper.getAppDefaultWait(),TimeUnit.SECONDS);
                return true;
            }
        }
        driver.manage().timeouts().implicitlyWait(ConfigHelper.getAppDefaultWait(),TimeUnit.SECONDS);
        return false;
    }
    public static boolean estaPresente(PandoraElement locator, int intentos) {
        List<WebElement> elementosEncontrados;
        for (int reintentos = 0; reintentos < intentos; reintentos++) {
            try {Thread.sleep(500);}
            catch (InterruptedException e) {e.printStackTrace();}
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            elementosEncontrados = driver.findElements(locator.getElementBy());
            if (!elementosEncontrados.isEmpty()) {
                driver.manage().timeouts().implicitlyWait(ConfigHelper.getAppDefaultWait(),TimeUnit.SECONDS);
                return true;
            }
        }
        driver.manage().timeouts().implicitlyWait(ConfigHelper.getAppDefaultWait(),TimeUnit.SECONDS);
        return false;
    }

    public String fechaActual(){
        LocalDateTime fechaHoy =LocalDateTime.now();
        String fechaFormateada = fechaHoy.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.println("FECHA : " + fechaFormateada);
        return fechaFormateada;
    }

    public String fechaYHoraActual(){
        LocalDateTime fechaHoy =LocalDateTime.now();
        String fechaFormateada = fechaHoy.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.println("FECHA : " + fechaFormateada);
        return fechaFormateada;
    }

    public String fechaPosteriorHabil(){
        LocalDateTime fecha = LocalDateTime.now();
        String fechaFormateada ="";
        if(fecha.getDayOfWeek()==DayOfWeek.FRIDAY){
            fecha = fecha.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            fechaFormateada = fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }else{
            fecha = fecha.plusDays(1);
            fechaFormateada = fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return fechaFormateada;
    }

    public  String quitarCochetesJsonPath(String valor){
        return valor.replaceAll("\\[","").replace("]","");
    }

    public  String leerContenidoArchivo(String nombreArchivo)  {
        byte[] contenido = new byte[0];

        try {
            contenido = Files.readAllBytes(Paths.get(nombreArchivo));
        } catch( Exception ex) {
            System.out.println(ex);
        }

        return new String(contenido);

    }
    public static void fixedWait(long wait){
        try{
            Thread.sleep(wait);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    // Response Management
    public String convertResponseToCorrectJSON(String originalResponse) {

        // Reemplazar el inicio del array de environments para convertirlo en un array de objetos
        String modifiedResponse = originalResponse.replace("\"environments\": {", "\"environments\": [");

        // Reemplazar los separadores de elementos del array de environments
        modifiedResponse = modifiedResponse.replaceAll("\"\\d+\": ", "");

        System.out.println("Previo 1: \n" + modifiedResponse);

        // Reemplazar el cierre del objeto data por el cierre del array de environments y el cierre del objeto data
        modifiedResponse = modifiedResponse.replaceFirst("\\},\\s*\"selectEnvironment\"", "], \"selectEnvironment\"");

        System.out.println("final: \n" + modifiedResponse);
        return modifiedResponse;
    }
    public String generarUsuarioCorreoAleatorio() {
        String CHARACTERS = "abcdefghijklmnopqrstuvwxyz";

        Random random = new Random();

        // Generar nombre de usuario
        StringBuilder usuario = new StringBuilder();
        int longitudUsuario = random.nextInt(10) + 5; // Entre 5 y 15 caracteres

        for (int i = 0; i < longitudUsuario; i++) {
            usuario.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        // Construir el correo electrónico con dominio fijo
        return usuario.toString() + "@" + "gmail.com";
    }

    public boolean eiliminarArchivo(String nombreArchivoFragmento) throws InterruptedException {
        String user = System.getProperty("user.name");
        File directory = new File("C:\\Users\\" + user + "\\Downloads");
        int intentos = 0;

        while (intentos < 3) {
            Thread.sleep(4000);
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    String nombreArchivo = new String(file.getName().getBytes(Charset.defaultCharset()));
                    if (nombreArchivo.contains(nombreArchivoFragmento)) {
                        Thread.sleep(3000); // Esperar 3 segundos antes de intentar borrar el archivo
                        if (file.delete()) {
                            return true;
                        } else {
                            System.out.println("No se pudo borrar el archivo.");
                            return false;
                        }
                    }
                }
            }
            // Incrementar el contador de intentos y esperar antes de volver a intentar
            intentos++;
            System.out.println("El archivo no se encontró en el intento número " + intentos + ". Reintentando...");
        }

        System.out.println("El archivo no se encontró después de 3 intentos.");
        return false;
    }

    public String sumarFechaHoyPorDias(int dias){
        LocalDateTime dateTime = LocalDateTime.now().plusDays(dias);
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }


    public static Number parseMontoAR(String monto) {

        String normalized = monto.replace(".", "").replace(",", ".");

        BigDecimal valor = new BigDecimal(normalized);
        BigDecimal resultado = valor.add(new BigDecimal("100000"));


        return resultado;
    }

    @Step("Cambiar de pestaña del navegador")
    public void switchTab() {

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

    }

    public String escaparArchivoJsonToString(String responseList){
        String json,jsonEscapado = null;
        try{

            jsonEscapado = new String(JsonStringEncoder.getInstance().quoteAsString(responseList));

            jsonEscapado = jsonEscapado.replace("\\r","")
                    .replace("\\n","")
                    .replace(" ","");

        }catch (Exception e){
            System.out.println("CAPTH ERROR : " + e.getMessage());
        }

        return jsonEscapado;
    }

    public static String validarNullParametro(String parametro){
        try{
            System.out.println("El parametro viene con valor");
            if(parametro.equals(null)){
                parametro = "null";

            }
            return parametro;
        }catch (Exception e){
            System.out.println("- Valor null - ");
            parametro = "null";
            return parametro;
        }
    }


}