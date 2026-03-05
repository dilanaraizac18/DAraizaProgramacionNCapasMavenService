
package com.digis01.DAraizaProgramacionNCapasMaven.JPA;

import com.digis01.DAraizaProgramacionNCapasMaven.ML.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Colonia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "idcolonia")
    private int idColonia;
    @Column(name="nombre")
    private String Nombre;
    @Column(name="codigopostal")
    private String CodigoPostal;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idmunicipio_fk")
    public Municipio municipio;

    public Colonia(int idColonia, String Nombre, String CodigoPostal, Municipio municipio) {
        this.idColonia = idColonia;
        this.Nombre = Nombre;
        this.CodigoPostal = CodigoPostal;
        this.municipio = municipio;
    }
    
    
    
    public Colonia(){
        
    }
    
    
    public void setIdColonia(int idColonia){
        this.idColonia = idColonia;
    }
    
    public int getIdColonia(){
        return idColonia;
    }
    
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
    
    public String getNombre(){
        return Nombre;
    }
    
    public void setCodigoPostal( String CodigoPostal){
        this.CodigoPostal = CodigoPostal;
}
    public String getCodigoPostal(){
        return CodigoPostal;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }
    
}
