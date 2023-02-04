/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login_grupo8;

import java.sql.*;

/**
 *
 * @author aburg
 */
public class Conexion {
    private static Connection con = null;
    private String usuario;
    private String clave;
    private String driver;
    private String url;
   
    private Conexion(){
        String usuario = "root";
        String clave = "adrian99";
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/alumnos?zeroDateTimeBehavior=convertToNull [root on Default schema]";
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url,usuario,clave);
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();        
        }
    }
    
    public static Connection obtenerConexion(){
        if(con == null){
            new Conexion();
        }
        return con;
    }
    
    public static void cerrarConexion(){
        if(con != null){
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
