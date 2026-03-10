
package com.digis01.DAraizaProgramacionNCapasMaven.JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Usuario {
    
    
    @Id    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idusuario")
    private int IdUsuario;
    
    @Column(name= "nombre")
    @NotEmpty(message= "No puede ser nulo")
    @Size(min = 3, max = 50, message = "El nombre debe llevar minimo 3 caracteres")
    private String Nombre;
    
    @Column(name= "apellidopaterno")
    @NotEmpty(message= "No puede ser nulo")
    @Size(min = 3, max = 50, message = "El Apellido Paterno debe llevar minimo 3 caracteres")
    private String ApellidoPaterno;
    
    @Column(name="apellidomaterno")
    @NotEmpty(message= "No puede ser nulo")
    @Size(min = 3, max = 50, message = "El Apellido Materno debe llevar minimo 3 caracteres")
    private String ApellidoMaterno;
    
    @Column(name="numerotelefonico")
    @NotEmpty(message = "No puedo ser vacio")
    @Pattern(regexp = "^[0-9]{10}$", message = "Solo numeros")
    @Size(min = 10, max = 10, message = "Debe haber minimo 10 numeros")
    private String NumeroTelefonico;
    
    @Column (name= "fechanacimiento")
    @NotNull(message = "La fecha no puede ser nula")
    @PastOrPresent(message = "La fecha debe ser en el pasado")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date FechaNacimiento;
    
//    @Pattern(regexp = "^([A-Z][AEIOUX][A-Z]{2}\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01])[HM](?:AS|B[CS]|C[CLMSH]|D[FG]|G[TR]|HG|JC|M[CNS]|N[ETL]|OC|PL|Q[TR]|S[PLR]|T[CSL]|VZ|YN|ZS)[B-DF-HJ-NP-TV-Z]{3}[A-Z\\d])(\\d)$", message = "Formato invalido")
    @Column(name="CURP")
    private String CURP;
    
    @Column(name="username")
    @NotEmpty(message = "No puede ser vacio")
//    @Pattern(regexp = "^[a-zA-Z ]+[0-9]+$", message = "Introduzca minimo una letra y minimo un numero")
    private String Username;
    
    @Column(name= "Sexo")
    private String Sexo;
    
    @Column(name = "celular")
//    @Pattern(regexp = "^[0-9]{10}$", message = "Solo numeros")
    @Size(min = 10, max = 10, message = "Debe haber minimo 10 numeros")
    private String Celular;
    @Column(name = "email")
//    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z]+.com$", message = "formato invalido")
    @NotEmpty(message = "No puedo ser vacio")
    private String Email;
    
    @Column(name="password")
    private String Password;
    
    @OneToOne
    @JoinColumn(name="idrol_fk")
    public Rol Rol; 
    
    @Column(name="imagen")
    @Lob
    public String Imagen;
    
    public int Status;

    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Direccion> Direcciones;
    
    //setters
    public int getIdUsuario(){
        return IdUsuario;
    }
    
    public void setIdUsuario(int IdUsuario){
        this.IdUsuario = IdUsuario;
    }

    public Usuario(int IdUsuario, String Nombre, String ApellidoPaterno, String ApellidoMaterno, String NumeroTelefonico, Date FechaNacimiento, String CURP, String Username, String Sexo, String Celular, String Email, String Imagen,int Status,String Password, Rol Rol) {
       this.IdUsuario = IdUsuario;
        this.Nombre = Nombre;
        this.ApellidoPaterno = ApellidoPaterno;
        this.ApellidoMaterno = ApellidoMaterno;
        this.NumeroTelefonico = NumeroTelefonico;
        this.FechaNacimiento = FechaNacimiento;
        this.CURP = CURP;
        this.Username = Username;
        this.Sexo = Sexo;
        this.Celular = Celular;
        this.Email = Email;
        this.Status = Status;
        this.Password = Password;
        this.Rol = Rol;
        this.Imagen = Imagen;
    }
    
    
    public Usuario(){
    }

   

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    public String getNombre(){
        return Nombre;
    }
    
    public void setApellidoPaterno(String ApellidoPaterno){
        this.ApellidoPaterno = ApellidoPaterno;
        
    }
    
    public String getApellidoPaterno(){
        return ApellidoPaterno;
    }
    
    public void setApellidoMaterno (String ApellidoMaterno){
        this.ApellidoMaterno = ApellidoMaterno;
    }
    
    public String getApellidoMaterno (){
        return ApellidoMaterno;
    }
     

    public void setFechaNacimiento(Date FechaNacimiento){
        this.FechaNacimiento = FechaNacimiento;
    }
    
    public Date getFechaNacimiento(){
        return FechaNacimiento;
    }
    
    public void setCURP(String CURP){
        this.CURP = CURP;
    }
    
    public String getCURP(){
        return CURP;
    }
    
    public void setNumeroTelefonico(String NumeroTelefonico){
        this.NumeroTelefonico = NumeroTelefonico;
    }
    
    public String getNumeroTelefonico (){
        return NumeroTelefonico;
    } 
    
    public void setUsername(String Username){
        this.Username = Username;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }
    
    public String getUsername(){
        return Username;
    }
    
    
    public void setSexo(String Sexo){
        this.Sexo = Sexo;
    }
    
    public String getSexo(){
        return Sexo;
    }
    
    public void setCelular(String Celular){
        this.Celular =  Celular;
    }
    
    public String getCelular(){
        return Celular;
    }
    
    public void setEmail(String Email){
        this.Email = Email;
    }
    
    public String getEmail (){
        return Email;
    }
    
    public void setPassword(String Password){
        this.Password = Password;
    }
    
    public String getPassword(){
        return Password;
    }

    public Rol getRol() {
        return Rol;
    }

    public void setRol(Rol Rol) {
        this.Rol = Rol;
    }

//    public List<Direccion> getDirecciones() {
//        return Direcciones;
//    }
//
//    public void setDirecciones(List<Direccion> Direcciones) {
//        this.Direcciones = Direcciones;
//    }

    public Object Rol() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
        public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }
 
}
