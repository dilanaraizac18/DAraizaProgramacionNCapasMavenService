/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO;

import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Municipio;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Dilan
 */
@Repository
@RequestMapping("/api/municipio")
public class MunicipioDAOJPAImplementation implements IMunicipioJPA{

    @Autowired
    private EntityManager entityManager; 
    
    @Override
    public Result MunicipioByIdEstado(int idEstado) {
        
        Result result = new Result();
        
        try{
            TypedQuery<Municipio> municipioQuery = entityManager.createQuery("FROM Municipio where estado.idEstado = :pidEstado", Municipio.class);
            municipioQuery.setParameter("pidEstado", idEstado);
            
            result.objects = new ArrayList<>(municipioQuery.getResultList());
            
            result.correct = true;
        }catch (Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
}
