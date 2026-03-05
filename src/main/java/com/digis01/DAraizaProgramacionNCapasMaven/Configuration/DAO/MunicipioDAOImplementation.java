/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO;

import com.digis01.DAraizaProgramacionNCapasMaven.ML.Municipio;
import com.digis01.DAraizaProgramacionNCapasMaven.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDAOImplementation implements IMunicipio{
    Result result = new Result();

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetById(int idEstado) {
        
        jdbcTemplate.execute("{CALL MunicipioGetByID(?,?)}", (CallableStatementCallback<Boolean>) callableStatement ->{
        callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
        callableStatement.setInt(2, idEstado);
        callableStatement.execute();
        
        ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
        
          result.objects = new ArrayList<>();
          
          while(resultSet.next()){
              Municipio municipio = new Municipio();
              
              result.objects.add(municipio);
              
              municipio.setIdMunicipio(resultSet.getInt("idMunicipio"));
              municipio.setNombre(resultSet.getString("NombreMunicipio"));
          }
            
            return true;
        });
        
        
        return result;
        
    }
    
    
}
