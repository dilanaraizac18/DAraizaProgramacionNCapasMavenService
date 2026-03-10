/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO;

import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Result;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Rol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Dilan
 */
@Repository
public class RolDAOJPAImplementation implements IRolJPA{

    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result GetRol() {
        
        Result result = new Result();
        try{
            TypedQuery<Rol> rolQuery = entityManager.createQuery("From Rol", Rol.class);
             List <Rol> rol = rolQuery.getResultList();

            result.objects = (List<Object>) (Object) rol;
            result.correct = true;
        } catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
}
