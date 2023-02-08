/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.monster.controlador;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author aburg
 */
@Named("controladorPrincipal")
@RequestScoped
public class ControladorPrincipal{
    private EmpleadoControlador empControlador;
    private UsuarioControlador usuControlador;
    
    public ControladorPrincipal(){
        this.empControlador = new EmpleadoControlador();
        this.usuControlador = new UsuarioControlador();
    }
    
    public void registro(String cedula, String nombre, String apellido_materno, String apellido_paterno, String usuario, String correo){
        java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
        FacesMessage mensaje;
        try{
            if(this.usuControlador.correoValido(correo)){
                this.empControlador.insertar(nombre.substring(0, 1), cedula, nombre, apellido_materno, apellido_paterno, date);
                this.usuControlador.insertar(nombre.substring(0, 1),usuario,"A",date,date,correo);
                mensaje= new FacesMessage(FacesMessage.SEVERITY_INFO,"Registrado con éxito!",null);
                FacesContext.getCurrentInstance().addMessage("a",mensaje);
            }else{
                mensaje= new FacesMessage(FacesMessage.SEVERITY_ERROR,"El correo electrónica ingresado no es válido.",null);
                FacesContext.getCurrentInstance().addMessage("a",mensaje);
            }
        }catch(Exception e){
            mensaje= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hubo un problema al registrar, por favor, inténtelo de nuevo.",null);
            FacesContext.getCurrentInstance().addMessage("a",mensaje);
        }
    }
    
    public void login(String usuario, String pass){
        int temporal = 0;
        int[] respuestas;
        FacesMessage mensaje;
        if(usuario.equals("")|| pass.equals("")){
            mensaje= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debe ingresar el usuario y la contraseña.",null);
            FacesContext.getCurrentInstance().addMessage("a",mensaje);
        }else{
            respuestas = this.usuControlador.login(usuario,pass);
            if(respuestas[1] == 1){
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                try {
                    System.out.println("PATH: " + externalContext.getRequestContextPath());
                    if(respuestas[0] == 0){                        
                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/usuario/InicioUsuario.xhtml");
                    }else{
                        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/usuario/CambiarClave.xhtml");
                    }
                } catch (Exception ex) {
                    System.out.println("Login ERROR: " + ex.getMessage());
                }
            }else{
                mensaje= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuario y/o contraseña incorrecta.",null);
                FacesContext.getCurrentInstance().addMessage("a",mensaje);
            }    
        }
    }
    
    public void actualizarClave(String pass, String repetir, String usuario){
        this.usuControlador.actualizarClaveUsuario(pass, repetir, usuario);
    }
}
