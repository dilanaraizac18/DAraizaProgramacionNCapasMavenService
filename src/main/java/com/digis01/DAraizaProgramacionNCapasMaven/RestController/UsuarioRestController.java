
package com.digis01.DAraizaProgramacionNCapasMaven.RestController;

import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.UsuarioDAOImplementation;
import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.UsuarioDAOJPAImplementation;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Colonia;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Direccion;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.ErroresArchivo;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Result;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Rol;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Usuario;
import io.jsonwebtoken.security.Jwks;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
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
    
    @GetMapping("test")
   public ResponseEntity Test (Authentication autentication){
       Map<String, Object> JSON = new HashMap<> ();
       
       JSON.put("User",  autentication.getName());
       JSON.put("Rol", autentication.getAuthorities());
       
       return ResponseEntity.ok(JSON);
   }
    
    
    @GetMapping()
            @PreAuthorize("hasRole('Administrador')")
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
        @PreAuthorize("hasRole('Administrador')")
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
        @PreAuthorize("hasRole('Administrador')")
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
    @PostMapping ("/imagen/{idusuario}")
        @PreAuthorize("hasRole('Administrador')")
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
        @PreAuthorize("hasRole('Administrador')")
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
    
     @DeleteMapping("/Delete/Direccion/{identificador}")
         @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity DeleteDireccion(@PathVariable ("identificador") int identificador) {
        try {
            Result result = usuarioDAOJPAImplementation.DeleteDireccion(identificador);
            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }
    
      @GetMapping("/Direccion/{IdDireccion}")
          @PreAuthorize("hasRole('Administrador')")
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
            @PreAuthorize("hasRole('Administrador')")
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
        @PreAuthorize("hasRole('Administrador')")
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
    
     @PostMapping("/cargarArchivo")
         @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<String> CargaArchivo(@RequestPart("archivo") MultipartFile archivo, HttpSession session) {
        Result result = new Result();
        List<Usuario> usuarios = new ArrayList<>();
        try {
            if (archivo != null) {

                String rutaBase = System.getProperty("user.dir");
                String rutaCarpeta = "src/main/resources/archivosCM";
                String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
                String nombreArchivo = fecha + archivo.getOriginalFilename();
                String rutaArchivo = rutaBase + "/" + rutaCarpeta + "/" + nombreArchivo;
                String extension = archivo.getOriginalFilename().split("\\.")[1];

                File archivoFile = new File(rutaArchivo);

                if (extension.equals("txt")) {
                    archivo.transferTo(new File(rutaArchivo));
                    usuarios = LecturaArchivoTxt(archivoFile);
                } else if (extension.equals("xlsx")) {
//                    archivo.transferTo(new File(rutaArchivo));
//                    usuarios = LecturaArchivoXLSX(new File(rutaArchivo));
                } else {
                    System.out.println("Extensión erronea, manda archivos del formato solicitado");
                }
                List<ErroresArchivo> errores = null;
                if (!usuarios.isEmpty()) {
                    errores = ValidarDatos(usuarios);
                }

                if (errores.isEmpty()) {
                    registrarEnBitacora(rutaArchivo, "SUCCESS", "Carga completada correctamente");
                    result.correct = true;

                } else {
                    registrarEnBitacora(rutaArchivo, "FAILED", result.errorMessage);
                }

            }
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return ResponseEntity.ok("Archivo procesado correctamente");
    }

    public List<Usuario> LecturaArchivoTxt(File archivo) {
        Result result = new Result();
        List<Usuario> usuarios = null;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

        try (InputStream inputStream = new FileInputStream(archivo); BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String linea;
            usuarios = new ArrayList<>();

            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split("\\|");

                if (datos.length >= 1) {
                    Usuario usuario = new Usuario();
                    usuario.Rol = new Rol();
                    usuario.Direcciones = new ArrayList<>();

                    usuario.setNombre(datos[0].trim());
                    usuario.setApellidoPaterno(datos[1].trim());
                    usuario.setApellidoMaterno(datos[2].trim());
                    try {
                        String fecha = datos[3].trim();
                        System.out.println(fecha);
                        usuario.setFechaNacimiento(formatoFecha.parse(fecha));
                    } catch (Exception e) {
                        result.errorMessage = e.getLocalizedMessage();
                    }

                    System.out.println(usuario.getFechaNacimiento());

                    usuario.setNumeroTelefonico(datos[4].trim());
                    usuario.setEmail(datos[5].trim());
                    usuario.setUsername(datos[6].trim());
                    usuario.setPassword(datos[7].trim());
                    usuario.setSexo(datos[8].trim());
                    usuario.setCelular(datos[9].trim());
                    usuario.setCURP(datos[10].trim());
                    try {
                        int idRol = Integer.parseInt(datos[11].trim());
                        usuario.Rol.setidRol(idRol);
                    } catch (NumberFormatException e) {
                        usuario.Rol.setidRol(0);
                    }

                    usuario.setImagen(datos[12].trim());

                    //DIRECCION
                    Direccion direccion = new Direccion();
                    direccion.colonia = new Colonia();
                    direccion.setCalle(datos[13].trim());
                    direccion.setNumeroExterior(datos[14].trim());
                    direccion.setNumeroInterior(datos[15].trim());
                    direccion.colonia.setIdColonia(Integer.parseInt(datos[16].trim()));

                    usuario.Direcciones.add(direccion);
                    usuarios.add(usuario);
                }
            }
        } catch (IOException e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return usuarios;
    }

    public List<ErroresArchivo> ValidarDatos(List<Usuario> usuarios) {
        List<ErroresArchivo> errores = new ArrayList<>();
        int numeroFila = 1; // sin encabezados

        if (usuarios.isEmpty()) {

            for (Usuario usuario : usuarios) {

//                BindingResult bindingResult = validationService.ValidateObject(usuario);
//
//                if (bindingResult.hasErrors()) {
//                    ErroresArchivo erroresArchivo = new ErroresArchivo();
//                    for (ObjectError objectError : bindingResult.getAllErrors()) {
//
////                    erroresArchivo.dato = objectError.getObjectName();
//                        erroresArchivo.dato = ((FieldError) objectError).getField();
//                        erroresArchivo.descripcion = objectError.getDefaultMessage();
//                        erroresArchivo.fila = numeroFila;
//
//                        errores.add(erroresArchivo);
//                    }
//
//                }
                numeroFila++;
            }
        } else {
            ErroresArchivo erroresArchivo = new ErroresArchivo();
            erroresArchivo.dato = "FORMATO EQUIVOCADO";
            erroresArchivo.descripcion = "FORMATO ERRONEO";
            erroresArchivo.fila = 0;
            errores.add(erroresArchivo);
        }
        return errores;
    }

    public String generarKeySHA256(String ruta) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(ruta.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash); // Convierte bytes a Hexadecimal
        } catch (Exception e) {
            return "ERROR_HASH";
        }
    }

    public void registrarEnBitacora(String rutaArchivo, String estatus, String detalleError) {
        String RUTA_LOG = "src/main/resources/log/bitacora.txt";
        String key = generarKeySHA256(rutaArchivo);
        String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Formato: key|ruta|estatus|fechahora|detalleDelError
        String linea = String.format("%s|%s|%s|%s|%s",
                key, rutaArchivo, estatus, fechaHora, (detalleError == null ? "" : detalleError));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_LOG, true))) {
            writer.write(linea);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error escribiendo en la bitácora: " + e.getMessage());
        }
    }

}