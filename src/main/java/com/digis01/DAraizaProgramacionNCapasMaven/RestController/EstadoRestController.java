/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.DAraizaProgramacionNCapasMaven.RestController;

import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.EstadoDAOImplementation;
import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.EstadoDAOJPAImplementation;
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
@RequestMapping("api/estado")
public class EstadoRestController {
    
    @Autowired
    private EstadoDAOJPAImplementation estadoDAOJPAImplementation;
    
    
    
    @GetMapping
    @PreAuthorize("hasAnyRole('Administrador', 'Visor', 'Usuario Estandar')")
    public ResponseEntity EstadoByIdPais(@RequestParam ("idPais") int idPais){
        
        Result result;
        
        try{
            
            result = estadoDAOJPAImplementation.EstadosByIdPais(idPais);
            
        }catch (Exception ex){
            return ResponseEntity.status(500).body(ex);
        }
        
        return ResponseEntity.ok(result);
        
    }
    
    
}
