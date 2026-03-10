/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO;

import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Estado;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author digis
 */

@Repository
@RequestMapping("/api/estado")
public class EstadoDAOJPAImplementation implements IEstadoJPA{

    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result EstadosByIdPais(int idPais) {
        Result result = new Result();
        
        try{
            TypedQuery<Estado> estadoQuery = entityManager.createQuery("FROM Estado where Pais.idPais = :pidPais", Estado.class);
            estadoQuery.setParameter("pidPais", this);
            
            result.objects = new ArrayList<>(estadoQuery.getResultList());
            
            result.correct = true;
        }catch (Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            
        }
        
        
        return result;
    }
    
}
