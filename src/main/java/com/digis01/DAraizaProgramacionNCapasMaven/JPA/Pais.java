
package com.digis01.DAraizaProgramacionNCapasMaven.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pais {
    @Id
    @Column (name="idPais")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPais;
    @Column(name="nombrepais")
    private String Nombre;

    public Pais(int idPais, String Nombre) {
        this.idPais = idPais;
        this.Nombre = Nombre;
    }
    
    public Pais(){
        
    }
    
    
    public void setIdPais(int idPais){
        this.idPais = idPais;
    }
    
    public int getIdPais( ){
        return idPais;
    }
    
    public void setNombre( String Nombre){
        this.Nombre = Nombre;
    }
    
    public String getNombre(){
        return Nombre;
    }
}
