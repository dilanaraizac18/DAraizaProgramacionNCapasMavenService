
package com.digis01.DAraizaProgramacionNCapasMaven.RestController;

import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.UsuarioDAOImplementation;
import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.UsuarioDAOJPAImplementation;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Result;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo/api")
public class DemoRestController {
    
    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;
    
    
    
    @GetMapping
    public ResponseEntity ObtenerDatos(){
        Result result = new Result();
        result = usuarioDAOJPAImplementation.GetAll();
        return ResponseEntity.status(200).body(result);
    }
    
    @GetMapping("{idUsuario}")
    public ResponseEntity GetById(@PathVariable ("idUsuario") int idUsuario){
        Result result = new Result();
        result = usuarioDAOJPAImplementation.GetById(idUsuario);
        
        return ResponseEntity.ok(result.object);
    }
    
    @PostMapping
    public ResponseEntity Add(@RequestBody Usuario usuario){
        
        try{
            Result result = usuarioDAOJPAImplementation.ADD(usuario);
            
            if(result.correct){
                return ResponseEntity.ok(result.object);
            }
            else{
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
            
            
        }catch (Exception ex){
           return  ResponseEntity.status(500).body(ex);
        }
    }
    
    @PutMapping
    public ResponseEntity Update(@RequestBody Usuario usuario){
        try{
            Result result = usuarioDAOJPAImplementation.UpdateUsuario(usuario);
            
            if(result.correct){
                return ResponseEntity.ok(result.object);
            }
            else{
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex);
            
        }
    }
    }

