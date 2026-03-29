/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.DAraizaProgramacionNCapasMaven.RestController;

import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.PaisDAOJPAImplementation;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Dilan
 */
@RestController
@RequestMapping("api/pais")
public class PaisRestController {
    
    @Autowired
    private PaisDAOJPAImplementation paisDAOJPAImplementation;
    
    @GetMapping
       @PreAuthorize("hasAnyRole('Administrador', 'Visor', 'Usuario Estandar')")
     public ResponseEntity GetPais(){
        Result result = new Result();
        
       try{
           result = paisDAOJPAImplementation.GetPais();
           
               if (result.correct) {
                return ResponseEntity.ok(result);
            }else{
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
       } catch(Exception ex){
           return ResponseEntity.status(500).body(ex);
       }
       
       
    }
    
}
