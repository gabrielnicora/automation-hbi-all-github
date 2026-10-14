package org.GP_Individuos_API;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.sql.*;

public class ConexionDB {
    static Connection conexion = null;
    private static Connection obtenerConexion(){
        String user,pass,db,host,port,url,driver;

        user = "testUser";
        pass = "unaP4ssV4l1d4";
        db = "test";
        host = "172.30.211.50";
        port = "3306";
        url = "jdbc:mysql://" + host + ":" + port + "/" + db;
        driver = "com.mysql.cj.jdbc.Driver";

        if(conexion == null){

            try{
                Class.forName(driver);
                conexion = DriverManager.getConnection(url,user,pass);
                System.out.println("===== CONEXION EXITOSA ====");

            }catch(ClassNotFoundException | SQLException e){
                System.out.println("============= FALLO LA CONEXION ============");
                e.printStackTrace();
            }
        }
        return conexion;
    }
    private static void cerrarConexion(){
        if(conexion != null){
            try{
                conexion.close();
                conexion = null;
                System.out.println("========= CONEXION FINALIZDA ===========");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static JSONObject extraerUsuariosDB(){
        //variables
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String consulta;
        JSONObject usuarios = new JSONObject();
        JSONArray datosBD = new JSONArray();

        //consulta bd
        consulta = "SELECT * FROM datos_hbi";

        //obtengo una conexion a la base
        obtenerConexion();

        try{
            preparedStatement = conexion.prepareStatement(consulta);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                    JSONObject userJson = new JSONObject();
                    userJson.put("tcName", resultSet.getString("tcName"));
                    userJson.put("usuario",resultSet.getString("user"));
                    userJson.put("password",resultSet.getString("pass"));
                    userJson.put("usuario2",resultSet.getString("user1"));
                    userJson.put("password2",resultSet.getString("pass1"));
                    userJson.put("cuentaOrigen", resultSet.getString("ctaOrigen"));
                    userJson.put("cuentaDestino", resultSet.getString("ctaDestino"));
                    userJson.put("cuentaComitente", resultSet.getString("ctaComitente"));
                    userJson.put("cbu", resultSet.getString("cbuDestino"));
                    userJson.put("alias", resultSet.getString("aliasDestino"));
                    userJson.put("cuitDestino", resultSet.getString("cuitDestino"));
                    userJson.put("tarjetaDebito", resultSet.getString("tarjetaDebito"));
                    userJson.put("tarjetaAOperar",resultSet.getString("tarjetaOperar"));
                    userJson.put("ambiente",resultSet.getString("ambiente"));
                    datosBD.add(userJson);
                }
            usuarios.put("data", datosBD);
            preparedStatement.close();
            resultSet.close();
            cerrarConexion();
        }catch (Exception e){
            e.printStackTrace();
        }
        return usuarios;
    }
}
