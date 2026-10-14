package org.GP_Individuos_API;

import com.jayway.jsonpath.JsonPath;
import jdk.jfr.EventSettings;
import net.minidev.json.JSONArray;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Alt+Intro with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");
        //String pass = JsonPath.parse(e).read("$.*.[?(@.usuario == 'Oscarb')].password").toString().replace("[","").replace("\"","").replace("]","").replace("\"","");
        //System.out.println("" + pass);
        // Press Mayús+F10 or click the green arrow button in the gutter to run the code.
        for (int i = 1; i <= 5; i++) {

            // Press Mayús+F9 to start debugging your code. We have set one breakpoint
            // for you, but you can always add more by pressing Ctrl+F8.
            System.out.println("i = " + i);
        }
    }
}