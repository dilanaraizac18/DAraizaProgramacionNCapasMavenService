
package com.digis01.DAraizaProgramacionNCapasMaven.RestController;

import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.UsuarioDAOImplementation;
import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.UsuarioDAOJPAImplementation;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Direccion;
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
import org.springframework.web.bind.annotation.PatchMapping;
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
public class UsuarioRestController {
    
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
        
        try {
            Result result = usuarioDAOJPAImplementation.GetById(idUsuario);

            if (result.correct) {
                if (result.object != null) {
                    return ResponseEntity.ok(result);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }
    
    
    @PostMapping (consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity Add(@RequestPart ("datos") Usuario usuario, @RequestPart(name = "imagen", required = false) MultipartFile imagen) {
        try {
            
            if (imagen != null && !imagen.isEmpty()) {
                byte[] bytes = imagen.getBytes();
                String base64 = Base64.getEncoder().encodeToString(bytes);
                usuario.setImagen(base64);
            }
            
            Result result = usuarioDAOJPAImplementation.ADD(usuario);

            if (result.correct) {
                return ResponseEntity.ok(result.object);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
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
    public ResponseEntity UpdateImagen (@PathVariable ("idusuario") int idusuario, @RequestPart (name = "imagen") MultipartFile imagen){
        try{
            
    
                byte[] bytes = imagen.getBytes();
                String base64 = Base64.getEncoder().encodeToString(bytes);
                Result result = usuarioDAOJPAImplementation.UpdateImagen(idusuario, base64);
                
                

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
    
     @DeleteMapping("/Delete/Direccion")
    public ResponseEntity DeleteDireccion(@RequestParam("identificador") int identificador) {
        try {
            Result result = usuarioDAOJPAImplementation.DeleteDireccion(identificador);
            if (result.correct) {
                return ResponseEntity.ok("exito en el borrado " + result);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }
    
      @GetMapping("/Direccion/{IdDireccion}")
    public ResponseEntity GetByIdDireccion(@PathVariable("IdDireccion") int identificador) {
        Result result = new Result();
        try {
            result = usuarioDAOJPAImplementation.DireccionGetById(identificador);
            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }
    
        @PostMapping("/Direccion")
    public ResponseEntity AddDireccion(@RequestBody Direccion direccion, @RequestParam("identificador") int identificador) {
        try {
            Result result = usuarioDAOJPAImplementation.AddDireccion(direccion, identificador);

            if (result.correct) {
                return ResponseEntity.ok(result.object);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
  
//      @PatchMapping("/Estatus")
//    public ResponseEntity UpdateEstatus(@RequestParam("identificador") int identificador, @RequestParam("estatus") int estatus){
//        try {
//            Result result = usuarioDAOJPAImplementation.UpdateEstatus(identificador, estatus);
//            if (result.correct) {
//                return ResponseEntity.ok().body(result);
//            }else{
//                return ResponseEntity.badRequest().body(result.errorMessage);
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(e.getLocalizedMessage());
//        }
//    }
//    
    @PutMapping("/Direccion")
    public ResponseEntity UpdateDireccion(@RequestBody Direccion direccion){
        try{
            Result result = usuarioDAOJPAImplementation.UpdateDireccion(direccion);
            
            if(result.correct){
                return ResponseEntity.ok().body(result);
            }else{
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
            
        }catch(Exception ex){
            return ResponseEntity.status(500).body(ex);
    }
        
    }
}