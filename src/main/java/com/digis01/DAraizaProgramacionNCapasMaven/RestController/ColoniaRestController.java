/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.DAraizaProgramacionNCapasMaven.RestController;

import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.ColoniaDAOImplementation;
import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.ColoniaDAOJPAImplementation;
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
@RequestMapping("api/colonia")
public class ColoniaRestController {
    
    @Autowired
    private ColoniaDAOJPAImplementation coloniaDAOJPAImplementation;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('Administrador', 'Visor', 'Usuario Estandar')")
    public ResponseEntity ColoniaByMunicipio (@RequestParam("idMunicipio") int idMunicipio){
        Result result;
        
        try{
            
            result = coloniaDAOJPAImplementation.ColoniaByIdMunicipio(idMunicipio);
            
            
            
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex);
            
        }
        return ResponseEntity.ok(result);
    }
    
}
