/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.DAraizaProgramacionNCapasMaven.RestController;

import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.RolDAOImplementation;
import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.RolDAOJPAImplementation;
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
@RequestMapping("api/rol")
public class RolRestController {
    
    @Autowired
    private RolDAOJPAImplementation rolDAOJPAImplementation;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('Administrador', 'Visor', 'Usuario Estandar')")
    public ResponseEntity GetRol (){
        Result result = new Result();
        
        try{
            result = rolDAOJPAImplementation.GetRol();
        } catch(Exception ex){
            return ResponseEntity.status(500).body(ex);
        }
        
        return ResponseEntity.ok(result);
    }
}
