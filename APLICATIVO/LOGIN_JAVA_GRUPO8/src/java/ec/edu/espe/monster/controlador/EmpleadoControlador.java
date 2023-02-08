package ec.edu.espe.monster.controlador;

import ec.edu.espe.monster.modelo.*;
import java.sql.*;

/**
 *
 * @author aburg
 */

public class EmpleadoControlador{
    private Empleado empleado;
    private Conexion conn;
    private Connection conex;
    
    public EmpleadoControlador() {
        this.conn = Conexion.obtenerInstancia();
    }
    
    public void insertar(String id, String cedula, String nombre, String apellido_materno, String apellido_paterno,Date fecha ){
        PreparedStatement ps;
        String sql;
        try{
            this.conex = this.conn.conectar();
            sql = "insert into xesubse_empleado(emp_id,emp_cedula,emp_nombre,emp_apellido_paterno,emp_apellido_materno,emp_fecha_nacimiento,emp_estaactivo) values (?,?,?,?,?,?,?)";
            ps = conex.prepareStatement(sql);
            ps.setString(1,id);
            ps.setString(2,cedula);
            ps.setString(3,nombre);
            ps.setString(4,apellido_paterno);
            ps.setString(5,apellido_paterno);
            ps.setDate(6,fecha);
            ps.setInt(7,1);
            ps.executeUpdate();      
        }catch(SQLException e){
            System.out.println("Hubo un error al guardar un nuevo empleado");
        }
    }
    
    public Empleado obtenerEmpleado(String id){
        PreparedStatement ps;
        String sql;
        Empleado empleado = new Empleado();
        try{
            ResultSet rs = null;
            this.conex = this.conn.conectar();
            sql = "select emp_cedula,emp_nombre,emp_apellido_paterno,emp_apellido_materno from xesubse_empleado where emp_id = ?";
            ps = conex.prepareStatement(sql);
            ps.setString(1,id);
            rs = ps.executeQuery();    
            empleado.setCedula(rs.getString(1));
            empleado.setNombre(rs.getString(2));
            empleado.setApellido_paterno(rs.getString(3));
            empleado.setApellido_materno(rs.getString(4));
            return empleado;
        }catch(SQLException e){
            System.out.println("Hubo un error al guardar un nuevo empleado");
        }
        return null;
    }
}
