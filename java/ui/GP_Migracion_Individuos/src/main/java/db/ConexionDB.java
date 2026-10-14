package db;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.sql.*;

public class ConexionDB {

    private static Connection conexionDB;

    private ConexionDB() {
    }

    // Nombre de usuario
    private final static String username = "testUser";

    // Clave de usuario
    private final static String password = "unaP4ssV4l1d4";

    // Nombre de la base de datos
    private final static String database = "test";

    // Host
    private final static String hostname = "172.30.211.50";

    // Puerto
    private final static String port = "3306";

    // Ruta base de datos
    private final static String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database;

    // Librería de MySQL
    private final static String driver = "com.mysql.cj.jdbc.Driver";

    public static Connection obtenerConexion() {
        if (conexionDB == null) {
            try {
                Class.forName(driver);
                conexionDB = DriverManager.getConnection(url, username, password);
                System.out.println("CONEXION EXITOSA");
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return conexionDB;
    }
    public static void cerrarConexionDB(){
        if(conexionDB != null){
            try{
                conexionDB.close();
                conexionDB = null;
                System.out.println("========= CONEXION FINALIZDA ===========");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void cerrarConexion() {
        if (conexionDB != null) {
            try {
                conexionDB.close();
                conexionDB = null;
                System.out.println("CONEXION CERRADA");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Extraer todos los datos de la bd esto se mueve
    public static JSONObject extraerUsuariosDB(){
        Connection conexion = ConexionDB.obtenerConexion();
        //variables
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String consulta;
        JSONObject usuarios = new JSONObject();
        JSONArray datosBD = new JSONArray();

        //consulta bd
        consulta = "SELECT * FROM usuarios";

        //obtengo una conexion a la base
        //obtenerConexion();

        try{
            preparedStatement = conexion.prepareStatement(consulta);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                JSONObject userJson = new JSONObject();
                userJson.put("usuario",resultSet.getString("name"));
                userJson.put("password",resultSet.getString("password"));
                userJson.put("cuentaOrigen", resultSet.getString("cuentaOrigen"));
                userJson.put("cuentaDestino", resultSet.getString("cuentaDestino"));
                userJson.put("cbu", resultSet.getString("cbu"));
                userJson.put("alias", resultSet.getString("alias"));
                userJson.put("tarjetaAOperar", resultSet.getString("tarjetaAOperar"));
                userJson.put("banco",resultSet.getString("bank"));
                datosBD.add(userJson);
            }
            usuarios.put("data", datosBD);
            preparedStatement.close();
            resultSet.close();
            ConexionDB.cerrarConexionDB();
        }catch (Exception e){
            e.printStackTrace();
        }
        return usuarios;
    }
    //Extraer todos los datos de la bd esto se mueve
    public static JSONObject usuariosDB(){
        Connection conexion = ConexionDB.obtenerConexion();
        //variables
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String consulta;
        JSONObject usuarios = new JSONObject();
        JSONArray datosBD = new JSONArray();

        //consulta bd
        consulta = "SELECT * FROM datos_hbi";

        //obtengo una conexion a la base
        //obtenerConexion();

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
                userJson.put("cbuDestino", resultSet.getString("cbuDestino"));
                userJson.put("aliasDestino", resultSet.getString("aliasDestino"));
                userJson.put("cuitDestino", resultSet.getString("cuitDestino"));
                userJson.put("tarjetaDebito", resultSet.getString("tarjetaDebito"));
                userJson.put("tarjetaAOperar",resultSet.getString("tarjetaOperar"));
                userJson.put("ambiente",resultSet.getString("ambiente"));
                datosBD.add(userJson);
            }
            usuarios.put("data", datosBD);
            preparedStatement.close();
            resultSet.close();
            ConexionDB.cerrarConexionDB();
        }catch (Exception e){
            e.printStackTrace();
        }
        return usuarios;
    }
}
