package ec.edu.espe.monster.controlador;

import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author aburg
 */
public class Conexion {
    
    private static Connection conn;
    private static Conexion instancia;
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/alumnos?characterEncoding=UTF-8&useConfigs=maxPerformance";
    //private String url = "jdbc:mysql://localhost:3306/alumnos";
    private String usuario = "root";
    private String password = "adrian99";
    
    private Conexion(){
        
    }
    
    public Connection conectar(){
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url,usuario,password);
            System.out.println("Conectado!");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return this.conn;
    }
    
    public static Conexion obtenerInstancia(){
        if(instancia == null){
            instancia = new Conexion();
        }
        return instancia;
    }
    
    public void cerrarConexion() throws SQLException{
        try{
            this.conn.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
            this.conn.close();
        }finally{
            this.conn.close();
        }
    }
}
