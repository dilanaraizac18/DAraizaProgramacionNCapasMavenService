package com.digis01.DAraizaProgramacionNCapasMaven;

import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.UsuarioDAOJPAImplementation;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Colonia;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Direccion;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Result;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Usuario;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DAraizaProgramacionNCapasMavenApplicationTests {

	   @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;

    @Autowired
    private EntityManager entityManager;

    @Test
    void GetAll() {

        Result result = usuarioDAOJPAImplementation.GetAll();

        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.objects);
        Assertions.assertEquals(4, result.objects.size());

    }

    @Test
    void Delete() {
        Result result = usuarioDAOJPAImplementation.Delete(81);


        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result);
    }
    
@Test
    void GetByIdDireccion() {
        Result result = usuarioDAOJPAImplementation.DireccionGetById(81);


        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result);
    }
    @Test
    void GetByID() {
        Result result = usuarioDAOJPAImplementation.GetById(81);


        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.object);
    }
    
    @Test
    void AddDireccion(){
        Direccion direccion = new Direccion();
        
        direccion.setCalle("Nueva calle3");
        direccion.setNumeroExterior("44");
        direccion.setNumeroInterior("SN");
        direccion.colonia = new Colonia();
        direccion.colonia.setIdColonia(9120);
        
        Result result = usuarioDAOJPAImplementation.AddDireccion(direccion, 61);
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result);
    }
    
    @Test
    void DeleteDireccion(){
        Result result = usuarioDAOJPAImplementation.DeleteDireccion(123);
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result);
        
    }
    
    
    
    
    
    


}