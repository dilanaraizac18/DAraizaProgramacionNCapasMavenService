/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO;

import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Colonia;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Direccion;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Result;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Rol;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository
public class UsuarioDAOJPAImplementation implements IUsuarioJPA{

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired 
    private EntityManager entityManager;

    @Override
    public Result GetAll() {
        Result result = new Result();
        
        
        try{
            TypedQuery<Usuario> queryUsuario = entityManager.createQuery("FROM Usuario", Usuario.class);
            
            List<Usuario> usuarios = queryUsuario.getResultList();
            
            result.objects = new ArrayList<>();
            for (Usuario usuario : usuarios) {
                result.objects.add(usuario);
                
            }
            result.correct = true;
            
        }catch( Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        
        
        return result;
        
    }
    
    @Override
    @Transactional
    public Result ADD(Usuario usuario){
           Result result = new Result();

        try {

            Usuario usuariojpa = new Usuario();

            usuariojpa.setNombre(usuario.getNombre());
            usuariojpa.setApellidoPaterno(usuario.getApellidoPaterno());
            usuariojpa.setApellidoMaterno(usuario.getApellidoMaterno());
            usuariojpa.setFechaNacimiento(usuario.getFechaNacimiento());
            usuariojpa.setNumeroTelefonico(usuario.getNumeroTelefonico());
            usuariojpa.setEmail(usuario.getEmail());
            usuariojpa.setUsername(usuario.getUsername());
            usuariojpa.setPassword(usuario.getPassword());
            usuariojpa.setSexo(usuario.getSexo());
            usuariojpa.setCelular(usuario.getCelular());
            usuariojpa.setCURP(usuario.getCURP());
            usuariojpa.Rol = new Rol();
            usuariojpa.Rol.setidRol(usuario.Rol.getidRol());
            usuariojpa.setImagen(usuario.getImagen());

            usuariojpa.Direcciones = new ArrayList<>();
            Direccion direccionjpa = new Direccion();
            direccionjpa.colonia = new Colonia();

            Direccion direccion = usuario.Direcciones.get(0);

            direccionjpa.setCalle(direccion.getCalle());
            direccionjpa.setNumeroExterior(direccion.getNumeroExterior());
            direccionjpa.setNumeroInterior(direccion.getNumeroInterior());
            direccionjpa.colonia.setIdColonia(direccion.colonia.getIdColonia());

            usuariojpa.Direcciones.add(direccionjpa);
            direccionjpa.usuario = usuariojpa;

            entityManager.persist(usuariojpa);

            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }
    

    @Override
        @Transactional
    public Result GetById(int idUsuario) {
        ModelMapper modelMapper = new ModelMapper();
        
        Result result = new Result();
        
        try{
            Usuario usuariojpa = entityManager.find(Usuario.class,idUsuario);
            
             if (usuariojpa != null) {

           
            result.object = usuariojpa;

        } else {
            result.correct = false;
            result.errorMessage = "Usuario no encontrado";
        }
            result.correct = true;
        }catch( Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            
        }
        
        return result;
    }

    @Override
    @Transactional
    public Result Delete(int idusuario) {
        Result result = new Result();
        
        try{
            com.digis01.DAraizaProgramacionNCapasMaven.JPA.Usuario usuariojpa = entityManager.find(com.digis01.DAraizaProgramacionNCapasMaven.JPA.Usuario.class, idusuario);
            
            entityManager.remove(usuariojpa);
            result.correct = true;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            
        }
        
        
        return result;
    }

    @Override
    @Transactional
    public Result UpdateImagen(int idUsuario, String imagen) {
        Result result = new Result();
        
        try{
            Usuario usuariojpa = entityManager.find(Usuario.class, idUsuario);

            if(usuariojpa != null){
                usuariojpa.setImagen(imagen);
//                entityManager.merge(usuariojpa);
                
                result.correct = true;
            }else{
                result.correct= false;
                result.errorMessage = "No se ha encontrado al usuario";
            }
            
            
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            
        }
        
        return result;
        
    }
    
    @Override
    @Transactional
    public Result UpdateStatus(int idUsuario, int status) {
        Result result = new Result();

        try {

            Usuario usuariojpa = entityManager.find(Usuario.class, idUsuario);
            usuariojpa.setIdUsuario(idUsuario);
            usuariojpa.setStatus(status);

            entityManager.merge(usuariojpa);
            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

//    @Override
//    @Transactional
//    public Result Updateusuario(com.digis01.DAraizaProgramacionNCapasMaven.JPA.Usuario usuario) {
//     Result result = new Result();
//     
//     try{
//   com.digis01.DAraizaProgramacionNCapasMaven.JPA.Usuario usuariojpa = entityManager.find(com.digis01.DAraizaProgramacionNCapasMaven.JPA.Usuario.class, usuario.getIdUsuario());
//        if(usuariojpa != null){
//             usuariojpa.setNombre(usuario.getNombre());
//             usuariojpa.setApellidoPaterno(usuario.getApellidoPaterno());
//             usuariojpa.setApellidoMaterno(usuario.getApellidoMaterno());
//             usuariojpa.setCURP(usuario.getCURP());
//             usuariojpa.setCelular(usuario.getCelular());
//             usuariojpa.setEmail(usuario.getEmail());
//             usuariojpa.setFechaNacimiento(usuario.getFechaNacimiento());
//             usuariojpa.setPassword(usuario.getPassword());
//             usuariojpa.setSexo(usuario.getSexo());
//             usuariojpa.setStatus(usuario.getStatus());
//
//             
//
//
//            
//            if (usuariojpa.getRol() != null) {
//                Rol rolBD = entityManager.find(Rol.class, usuariojpa.getRol().getidRol());
//                usuariojpa.setRol(rolBD);
//            }
//
//            usuariojpa.getDirecciones().clear();
//
//            for (com.digis01.DAraizaProgramacionNCapasMaven.JPA.Direccion direccionML : usuario.getDirecciones()) {
//
//                com.digis01.DAraizaProgramacionNCapasMaven.JPA.Direccion direccionJPA = new com.digis01.DAraizaProgramacionNCapasMaven.JPA.Direccion();
//
//                direccionJPA.setCalle(direccionML.getCalle());
//                direccionJPA.setNumeroExterior(direccionML.getNumeroExterior());
//
//                direccionJPA.setUsuario(usuariojpa);
//
//                usuariojpa.getDirecciones().add(direccionJPA);
//            }
//
//            result.correct = true;
//             
//            
//        }
//
//         
//     }catch(Exception ex){
//         result.correct = false;
//         result.errorMessage = ex.getLocalizedMessage();
//         result.ex = ex;
//         
//     }
//     
//     return result;
//     
//    }
    
    
    @Override
    @Transactional
    public Result UpdateUsuario(com.digis01.DAraizaProgramacionNCapasMaven.JPA.Usuario usuario) {
        Result result = new Result();

        try {

            Usuario usuarioBD = entityManager.find(Usuario.class, usuario.getIdUsuario());
            if (usuario != null) { // alumno si existe
                //ML -> JPA
                ModelMapper modelMapper = new ModelMapper();
                modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
                com.digis01.DAraizaProgramacionNCapasMaven.JPA.Usuario usuarioJpa = modelMapper.map(usuario, com.digis01.DAraizaProgramacionNCapasMaven.JPA.Usuario.class);
                
                usuarioJpa.Direcciones = usuarioBD.Direcciones;
                entityManager.merge(usuarioJpa);
                result.correct = true;

            }

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }
    
    

 
 
        @Override
    public Result DireccionGetById(int idUsuario) {
        Result result = new Result();

        try {

            com.digis01.DAraizaProgramacionNCapasMaven.JPA.Direccion direccionjpa = entityManager.find(com.digis01.DAraizaProgramacionNCapasMaven.JPA.Direccion.class, idUsuario);

            ModelMapper modelMapper = new ModelMapper();
            com.digis01.DAraizaProgramacionNCapasMaven.JPA.Direccion direccion = modelMapper.map(direccionjpa, com.digis01.DAraizaProgramacionNCapasMaven.JPA.Direccion.class);
            result.object = direccion;

            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }


    
     @Override
    @Transactional
    public Result DeleteDireccion(int idDireccion) {
        Result result = new Result();

        try {

            com.digis01.DAraizaProgramacionNCapasMaven.JPA.Direccion direccionjpa = entityManager.find(com.digis01.DAraizaProgramacionNCapasMaven.JPA.Direccion.class, idDireccion);

            entityManager.remove(direccionjpa);
            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }
    
        @Override
    @Transactional
    public Result AddDireccion( com.digis01.DAraizaProgramacionNCapasMaven.JPA.Direccion direccion, int idUsuario) {
        Result result = new Result();

        try {

            ModelMapper modelMapper = new ModelMapper();
            Direccion direccionjpa = modelMapper.map(direccion, Direccion.class);

            Usuario usuariojpa = new Usuario();
            usuariojpa.setIdUsuario(idUsuario);
            direccionjpa.setUsuario(usuariojpa);

            entityManager.persist(direccionjpa);
            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }
    
    @Override
    @Transactional
    public Result UpdateDireccion(Direccion direccion){
        Result result = new Result();
    
    try{
       
            Direccion direccionupdate = entityManager.find(Direccion.class, direccion.getIdDireccion());
            
            direccionupdate.setCalle(direccion.getCalle());
            direccionupdate.setNumeroInterior(direccion.getNumeroInterior());
            direccionupdate.setNumeroExterior(direccion.getNumeroExterior());
            direccionupdate.colonia = new Colonia();
            direccionupdate.colonia.setIdColonia(direccion.getColonia().getIdColonia());
            
            
            
            //sin esto va a querer hacer insert en ves de update
//            direccionAntigua.setIdDireccion(identificador);

            //
            entityManager.merge(direccionupdate);
            result.correct = true;
    
    }catch (Exception ex){
        result.correct = false;
        result.errorMessage = ex.getLocalizedMessage();
        result.ex = ex;
}

   
    return result;
    }

    @Override
    public Result GetByEmail(String Email) {
        
        Result result = new Result();
        try {

            TypedQuery<Usuario> queryUsuario = entityManager.createQuery("FROM Usuario WHERE Email = :pEmail", Usuario.class);
            queryUsuario.setParameter("pEmail", Email);

            result.object = queryUsuario.getSingleResult();
            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.ex = e;
            result.errorMessage = e.getLocalizedMessage();

        }
        
        return result;
        

    }
}
