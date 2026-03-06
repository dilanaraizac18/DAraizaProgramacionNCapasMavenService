
package com.digis01.DAraizaProgramacionNCapasMaven.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

/**
 *
 * @author digis
 */

@Entity
public class Direccion {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name="iddireccion")
    private int idDireccion;
    @Column (name="calle")
    private String Calle;
    @Column (name="numerointerior")
    private String NumeroInterior;
    @Column (name="numeroexterior")
    private String NumeroExterior;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario_fk")
    public Usuario usuario;
    @OneToOne
    @JoinColumn(name="idcolonia_fk")
    public Colonia colonia;

    public com.digis01.DAraizaProgramacionNCapasMaven.JPA.Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(com.digis01.DAraizaProgramacionNCapasMaven.JPA.Usuario usuario) {
        this.usuario = usuario;
    }

    public Direccion() {
    }

    public Direccion(int idDireccion, String Calle, String NumeroInterior, String NumeroExterior, Colonia colonia) {
        this.idDireccion = idDireccion;
        this.Calle = Calle;
        this.NumeroInterior = NumeroInterior;
        this.NumeroExterior = NumeroExterior;
        this.colonia = colonia;
    }
    
    
            
    public void setIdDireccion( int idDireccion){
        this.idDireccion = idDireccion;
    }        
    
    public int getIdDireccion (){
        return idDireccion;
    }
    
    public void setCalle (String Calle){
        this.Calle = Calle;
    }
    
    public String getCalle (){
        return Calle;
    }
    
    public void setNumeroInterior (String NumeroInterior){
        this.NumeroInterior = NumeroInterior;
    }
    
    public String getNumeroInterior (){
        return NumeroInterior;
    }
    
    public void setNumeroExterior (String NumeroExterior){
        this.NumeroExterior = NumeroExterior;
    }
    
    public String getNumeroExterior (){
        return NumeroExterior;
    }

    public Colonia getColonia() {
        return colonia;
    }

    public void setColonia(Colonia colonia) {
        this.colonia = colonia;
    }
    
    
}
