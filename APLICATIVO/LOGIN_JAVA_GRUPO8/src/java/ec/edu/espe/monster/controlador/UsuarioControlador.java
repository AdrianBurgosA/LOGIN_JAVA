package ec.edu.espe.monster.controlador;

/**
 *
 * @author aburg
 */

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import org.apache.commons.codec.binary.Base64;

public class UsuarioControlador {
    
   // private Usuario usuario;
    private Conexion conn;
    private Connection conex;
    private String secretKey = "monster";
    
    public UsuarioControlador(){
        this.conn = Conexion.obtenerInstancia();
        //this.usuario = new Usuario();
    }
    
    private String generarClaveAleatoria(int tam){
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < tam; i++){
            int randomIndex = random.nextInt(letras.length());
            sb.append(letras.charAt(randomIndex));
        }
        return sb.toString();
    }
    
    private void enviarClaveCorreo(String correoDestino, String mensajeEnviar){
        Properties propiedad = new Properties();
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.auth", "true");
        
        Session sesion = Session.getDefaultInstance(propiedad);
        
        String correoEnvia = "empresamonster8338@gmail.com";
        String contrasena = "zpdgfxefupmdqsbv";
        String destinatario = correoDestino;
        String asunto = "Clave Temporal";
        String mensaje = mensajeEnviar;
        
        MimeMessage mail = new MimeMessage(sesion);
        
        try {
            mail.setFrom(new InternetAddress (correoEnvia));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            mail.setSubject(asunto);
            mail.setText(mensaje);
            
            
            Transport transporte = sesion.getTransport("smtp");
            transporte.connect(correoEnvia,contrasena);
            transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            transporte.close();
            
            JOptionPane.showMessageDialog(null, "Correo enviado");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrió un problema al enviar el correo, inténtelo de nuevo.");
        }
    }
    
    public String encriptar(String Skey, String pass){
        String claveEncriptada = "";
        try{
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] llavePassword = md5.digest(Skey.getBytes("utf-8"));
            byte[] bytesKey = Arrays.copyOf(llavePassword, 24);
            SecretKey key = new SecretKeySpec(bytesKey,"DESede");
            Cipher cifrado = Cipher.getInstance("DESede");
            cifrado.init(Cipher.ENCRYPT_MODE,key);
            byte[] textoPlano = pass.getBytes("utf-8");
            byte[] buf = cifrado.doFinal(textoPlano);
            byte[] base64 = Base64.encodeBase64(buf);
            claveEncriptada = new String(base64);
        }catch(Exception e){
            System.out.println("Error al encriptar: " + e.getMessage());
        }
        return claveEncriptada;
    }
    
    public void insertar(String empleadoId, String usuario, String estadoCodigo,Date fechaCreacion, Date fechaModificacion, String correo){
        String password = this.generarClaveAleatoria(10);
        String passwordEncriptada = encriptar(secretKey,password);
        String mensaje = "Estimado usuario, su contraseña temporal es " + password + " , inicie sesión para poder cambiar la contraseña";
        //this.usuario = new Usuario(empleadoId,usuario,estadoCodigo,password,fechaCreacion,fechaModificacion,true,correo);
        PreparedStatement ps;
        String sql;
        try{
            this.conex = this.conn.conectar();
            sql = "insert into xesubse_usuario(emp_id,usuario,est_codigo,usu_paswd,usu_feccre,usu_fecmod,clave_temporal,usu_correo) values (?,?,?,?,?,?,?,?)";
            ps = conex.prepareStatement(sql);
            ps.setString(1, empleadoId);
            ps.setString(2, usuario);
            ps.setString(3, estadoCodigo);
            ps.setString(4, passwordEncriptada);
            ps.setDate(5, fechaCreacion);
            ps.setDate(6, fechaModificacion);
            ps.setInt(7,1);
            ps.setString(8, correo);
            ps.executeUpdate();
            enviarClaveCorreo(correo,mensaje);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public boolean correoValido(String correo){
        Pattern patron = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher coincide = patron.matcher(correo);
        return coincide.find();
    }
    
    public int[] login(String usuario, String pass){
        int existe = 0;
        int temporal;
        int[] respuestas = new int[2];
        try{
            String passEncriptada = encriptar(secretKey,pass);
            PreparedStatement ps;
            ResultSet rs = null;
            String sql;
            this.conex = this.conn.conectar();
            sql = "select usuario,usu_paswd,clave_temporal from xesubse_usuario where usuario = ?";
            ps = conex.prepareStatement(sql);
            ps.setString(1, usuario);
            rs = ps.executeQuery();
            if(rs.next()){
                System.out.println("Clave txt: " + passEncriptada + " | Clave BDD: " + rs.getString(2));
                temporal = rs.getInt(3);
                respuestas[0] = temporal;
                if(passEncriptada.equals(rs.getString(2))){
                    existe = 1;
                }
            }else{
                System.out.println("nada");
            }
        }catch(Exception e){
            System.out.println("Login Error: " + e.getMessage());
        }
        respuestas[1] = existe;
        return respuestas;
    }
    
    public void actualizarClaveUsuario(String pass, String repetida, String usuario){
        FacesMessage mensaje;
        PreparedStatement ps;
        String sql;
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        if(pass.equals(repetida)){
            try {
                String claveEncriptada = encriptar(secretKey,pass);
                this.conex = this.conn.conectar();
                sql = "update xesubse_usuario set usu_paswd = ?, clave_temporal = 0 where usuario = ?";
                ps = conex.prepareStatement(sql);
                ps.setString(1, claveEncriptada);
                ps.setString(2, usuario);
                ps.executeUpdate();
                try {
                    externalContext.redirect(externalContext.getRequestContextPath());
                } catch (Exception ex) {
                    System.out.println("ERROR: " + ex.getMessage());
                }
                mensaje= new FacesMessage(FacesMessage.SEVERITY_INFO,"Contraseña actualizada con éxito!",null);
                FacesContext.getCurrentInstance().addMessage("a",mensaje);
            } catch (SQLException ex) {
                mensaje= new FacesMessage(FacesMessage.SEVERITY_ERROR,"No ha sido posible actualizar la contraseña.",null);
                FacesContext.getCurrentInstance().addMessage("a",mensaje);
            }            
        }else{
            mensaje= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Las contraseñas deben coincidir.",null);
            FacesContext.getCurrentInstance().addMessage("a",mensaje);
        }
    }
}
