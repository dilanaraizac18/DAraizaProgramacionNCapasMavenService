/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO;

import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Colonia;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Dilan
 */
@Repository
public class ColoniaDAOJPAImplementation implements IColoniaJPA{

    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result ColoniaByIdMunicipio(int idMunicipio) {
        
        Result result = new Result();
        
        try{
            TypedQuery<Colonia> coloniaQuery = entityManager.createQuery("From Colonia where municipio.idMunicipio = :pidMunicipio", Colonia.class);
            coloniaQuery.setParameter("pidMunicipio", idMunicipio);
            
            result.objects = new ArrayList<>(coloniaQuery.getResultList());
           
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            
        }
        return result;
    }
    
}
