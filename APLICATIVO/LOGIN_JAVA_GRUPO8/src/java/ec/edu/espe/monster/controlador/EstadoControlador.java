package ec.edu.espe.monster.controlador;

import ec.edu.espe.monster.modelo.*;
import java.sql.*;

/**
 *
 * @author aburg
 */
public class EstadoControlador {
    private Estado estado;
    private Conexion conn;
    private Connection conex;
    
    public EstadoControlador(){
        this.conn = Conexion.obtenerInstancia();
    }
    
    public void insertar(String codigo, String descripcion){
        PreparedStatement ps;
        String sql;
        this.estado = new Estado(codigo,descripcion);
        try{
            this.conex = this.conn.conectar();
            sql = "insert into xesubse_estado(est_codigo,est_descri) values(?,?)";
            ps = conex.prepareStatement(sql);
            ps.setString(1, codigo);
            ps.setString(2, descripcion);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println("Hubo un error al guardar un nuevo estado");
        }
    }
}
