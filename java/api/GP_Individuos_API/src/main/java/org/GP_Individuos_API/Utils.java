package org.GP_Individuos_API;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.jayway.jsonpath.Configuration;

import groovy.json.JsonOutput;
import org.junit.Assert;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;


public class Utils {
    public  String leerContenidoArchivo(String nombreArchivo)  {
        byte[] contenido = new byte[0];

        try {
            contenido = Files.readAllBytes(Paths.get(nombreArchivo));
        } catch( Exception ex) {
            ex.printStackTrace();
        }

        return new String(contenido);

    }

    public JsonNode createBodyFromString(String body) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readTree(body);
        } catch (Exception e) {
            Assert.fail("No se pudo crear el body en formato JsonNode");
            return null;
        }
    }
    public String quitarCochetesJsonPath(String valor){
        return valor.replaceAll("\\[","").replace("]","").replace("\"","");
    }
    public void fixedWait(long wait){
        try{
            Thread.sleep(wait);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public String fechaHoy(){
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }
    public String fechaHoySinHora(){
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    public String sumarFechaHoyPorDias(int dias){
        LocalDateTime dateTime = LocalDateTime.now().plusDays(dias);
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }
    public String sumarFechaHoyPorDiasSinHora(int dias){
        LocalDateTime dateTime = LocalDateTime.now().plusDays(dias);
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }


    public String validarNullParametro(String parametro){
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

    // Misma clase Utils — NO elimina la versión String
    public String validarNullParametro(Object value) {
        if (value == null) return "null";

        // En JDK 11 no podés usar: (value instanceof List<?> list)
        if (value instanceof List) {
            @SuppressWarnings("unchecked")
            List<?> list = (List<?>) value;

            Object first = list.isEmpty() ? null : list.get(0);
            return validarNullParametro(first == null ? null : String.valueOf(first));
        }

        // Cualquier otro tipo: lo convierto a String y reutilizo la versión String
        return validarNullParametro(String.valueOf(value));
    }


    // Response Management
    public String convertResponseToCorrectJSON(String originalResponse, String nodo, String finalNodo) {
        // Reemplazar el inicio del array de environments para convertirlo en un array de objetos
        String modifiedResponse = originalResponse.replace("\"" + nodo + "\" : {", "\""+ nodo +"\": [");
        // Reemplazar los separadores de elementos del array de environments
        modifiedResponse = modifiedResponse.replaceAll("\"\\d+\" : ", "");
        System.out.println("Previo 1: \n" + modifiedResponse);
        // Reemplazar el cierre del objeto data por el cierre del array de environments y el cierre del objeto data
        modifiedResponse = modifiedResponse.replaceFirst("\\},\\s*\"" + finalNodo + "\"", "], \"selectEnvironment\"");
        System.out.println("final: \n" + modifiedResponse);
        return modifiedResponse;

    }
    public String fechaTransferenciaMasAntigua(String days){
        LocalDateTime dateTime = LocalDateTime.now().minusDays(Long.parseLong(days));
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'00:00:00.000-03"));
    }

    public String fechaTransferenciaUnAñoAntiguedad(String dias){
        LocalDateTime dateTime = LocalDateTime.now().minusDays(Long.parseLong(dias));
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }

    public String escaparArrayDatos(LinkedHashMap<String,Object> responseMap){
        String jsonString = "null";
        // Usar Jackson para convertir LinkedHashMap a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonString = objectMapper.writeValueAsString(responseMap);
            System.out.println(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonString;

    }

    public Object deserializarParametroAJson(String jsonEscapado){

        Configuration config = Configuration.defaultConfiguration();

        Object outterResponse = config.jsonProvider().parse(jsonEscapado);

        return outterResponse;

    }


}
