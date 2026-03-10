
package com.digis01.DAraizaProgramacionNCapasMaven.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Municipio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idmunicipio")
    private int idMunicipio;
    @Column(name="nombre")
    private String Nombre;
    @ManyToOne 
    @JoinColumn(name="idestado_fk")
    public Estado estado;
    
    
    
    public void setIdMunicipio(int idMunicipio){
        this.idMunicipio = idMunicipio;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    public int getIdMunicipio(){
        return idMunicipio;
    }
    
    public void setNombre( String Nombre){
        this.Nombre = Nombre;
    }
    
    public String getNombre(){
        return Nombre;
    }
}
