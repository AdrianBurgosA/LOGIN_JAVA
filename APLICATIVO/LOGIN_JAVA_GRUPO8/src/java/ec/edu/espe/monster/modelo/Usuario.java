/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.monster.modelo;

import java.io.Serializable;
import java.sql.Date;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author aburg
 */
@Named("usuarioBean")
@SessionScoped
public class Usuario implements Serializable{

    /**
     * Creates a new instance of Usuario
     */
    private String usuario;
    private String empleadoId;
    private String estadoCodigo;
    private String password;
    private String repetirPassword;
    private Date fechaCreacion;
    private Date fechaModificacion;
    private boolean claveTemporal;
    private String correo;

    public Usuario(){
        
    }
    

    public String getRepetirPassword() {
        return repetirPassword;
    }

    public void setRepetirPassword(String repetirPassword) {
        this.repetirPassword = repetirPassword;
    }

    
    public String getEmpleadoId() {
        return empleadoId;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getEstadoCodigo() {
        return estadoCodigo;
    }

    public String getPassword() {
        return password;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public boolean isClaveTemporal() {
        return claveTemporal;
    }

    public void setEmpleadoId(String empleadoId) {
        this.empleadoId = empleadoId;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setEstadoCodigo(String estadoCodigo) {
        this.estadoCodigo = estadoCodigo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public void setClaveTemporal(boolean claveTemporal) {
        this.claveTemporal = claveTemporal;
    }    

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
