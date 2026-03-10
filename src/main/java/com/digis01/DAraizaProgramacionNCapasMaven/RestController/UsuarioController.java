
package com.digis01.DAraizaProgramacionNCapasMaven.RestController;

import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.UsuarioDAOImplementation;
import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.UsuarioDAOJPAImplementation;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Result;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Usuario;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("demo/api")
public class UsuarioController {
    
    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;
    
    
    
    @GetMapping
    public ResponseEntity ObtenerDatos(){
        Result result = new Result();
        try{
        result = usuarioDAOJPAImplementation.GetAll();
        
        if(result.correct){
            if(result.objects != null || !result.objects.isEmpty()){
                return ResponseEntity.ok(result);
            } else{
               return ResponseEntity.noContent().build();
            }
            
        }else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex);
        }
    }
    
    @GetMapping("{idUsuario}")
    public ResponseEntity GetById(@PathVariable ("idUsuario") int idUsuario){
        Result result = new Result();
        result = usuarioDAOJPAImplementation.GetById(idUsuario);
        
        return ResponseEntity.ok(result.object);
    }
    
    @PostMapping (consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity Add(@RequestPart ("usuario") Usuario usuario, @RequestPart (name = "imagen", required = false) MultipartFile imagen ){
        Result result = new Result();
       try {
            String nombreArchivo = imagen.getOriginalFilename();

            String[] cadena = nombreArchivo.split("\\.");
            if (cadena[1].equals(
                    "jpg") || cadena[1].equals("png")) {
                try {
                    byte[] fileContent = imagen.getBytes();

                    String encodedString = Base64.getEncoder().encodeToString(fileContent);

                    System.out.println(encodedString);

                    usuario.setImagen(encodedString);

                } catch (Exception ex) {
                    result.correct = false;
                    result.errorMessage = ex.getLocalizedMessage();
                    result.ex = ex;
                }

                // realizar la conversión de imagen a base 64; 
            } else if (imagen
                    != null) {
                System.out.println("Error");

                
            }

       
            Result resultadd = usuarioDAOJPAImplementation.ADD(usuario);
            
            if(result.correct){
                return ResponseEntity.ok(resultadd.object);
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
                return ResponseEntity.ok(result);
            }
            else{
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex);
            
        }
    }
    
    @PutMapping ("/imagen/{idusuario}")
    public ResponseEntity UpdateImagen (@PathVariable ("idusuario") int idusuario){
        try{
            Result result = usuarioDAOJPAImplementation.UpdateImagen(idusuario);
            
            if (result.correct){
                return ResponseEntity.ok(result);
            }
            else{
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
                    
           } catch(Exception ex){
            return ResponseEntity.status(500).body(ex);
        }
    }
    
    @DeleteMapping ("/delete/{idusuario}")
    public ResponseEntity DeleteUsuario (@PathVariable ("idusuario") int idusuario){
        
        try{
        Result result = usuarioDAOJPAImplementation.Delete(idusuario);
        
        if(result.correct){
            return ResponseEntity.ok(result);
        }
            else{
                    return ResponseEntity.badRequest().body(result.errorMessage);
                    }
            
            
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex);
        }
        
    }
    
    
    
    }

