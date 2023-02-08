/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.monster.modelo;

import java.io.Serializable;
import java.sql.Date;
import javax.enterprise.context.*;
import javax.inject.Named;

/**
 *
 * @author aburg
 */
@Named("empleado")
@SessionScoped

public class Empleado implements Serializable{

    /**
     * Creates a new instance of Empleado
     */
    private String id;
    private String cedula;
    private String nombre;
    private String apellido_materno;
    private String apellido_paterno;
    private Date fecha;
    private boolean esta_activo;

    public Empleado(){
        
    }
    
    /*public Empleado(String id, String cedula, String nombre, String apellido_materno, String apellido_paterno, Date fecha, boolean esta_activo) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido_materno = apellido_materno;
        this.apellido_paterno = apellido_paterno;
        this.fecha = fecha;
        this.esta_activo = esta_activo;
    }*/

    public String getId() {
        return id;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public boolean isEsta_activo() {
        return esta_activo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public void setEsta_activo(boolean esta_activo) {
        this.esta_activo = esta_activo;
    }   

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
}
