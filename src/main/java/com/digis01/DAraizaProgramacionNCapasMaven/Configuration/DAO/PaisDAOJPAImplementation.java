/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO;

import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Pais;
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
public class PaisDAOJPAImplementation implements IPaisJPA{
    
    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result GetPais(int idPais) {

        Result result = new Result();
        
       try{
           
           TypedQuery<Pais> paisQuery = entityManager.createQuery("From Pais where idPais = :pidPais", Pais.class);
           paisQuery.setParameter("pidPais", idPais);
           
           result.object = new ArrayList<>(paisQuery.getResultList());
           
           
       }catch(Exception ex){
           result.correct = false;
           result.errorMessage = ex.getLocalizedMessage();
           result.ex = ex;
       }
       return result;
    }
    
}
