
package com.digis01.DAraizaProgramacionNCapasMaven.JPA;

import com.digis01.DAraizaProgramacionNCapasMaven.ML.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Rol {
    @Column(name="nombrerol")
    public String NombreRol;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idrol")
    public int idRol;

    public Rol(String NombreRol, int idRol) {
        this.NombreRol = NombreRol;
        this.idRol = idRol;
    }
    
    public Rol(){
        
    }
    
    public void setNombreRol(String NombreRol){
        this.NombreRol = NombreRol;
    }
    
    public String getNombreRol(){
        return NombreRol;
    }
    
    public void setidRol(int idRol){
        this.idRol = idRol;
    }
    
    public int getidRol(){
        return idRol;
    }
    
}
