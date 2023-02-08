/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.monster.modelo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author aburg
 */
@Named(value = "estado")
@RequestScoped
public class Estado {

    /**
     * Creates a new instance of Estado
     */
    private String codigo;
    private String descripcion;

    public Estado(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }    
}
