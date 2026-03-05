/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO;

import com.digis01.DAraizaProgramacionNCapasMaven.ML.Colonia;
import com.digis01.DAraizaProgramacionNCapasMaven.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaDAOImplementation implements IColonia{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public Result GetByID( int idMunicipio) {
        
        Result result = new Result();
        
        try{
        jdbcTemplate.execute("{CALL ColoniaGetByID(?,?)}", (CallableStatementCallback<Boolean>) callableStatement ->{
        callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
        callableStatement.setInt(2, idMunicipio);
        callableStatement.execute();
        
        ResultSet resultSet= (ResultSet) callableStatement.getObject(1);
        
        result.objects = new ArrayList<>();
        
        while(resultSet.next()){
            Colonia colonia =new Colonia();
            
            result.objects.add(colonia);
            
            colonia.setIdColonia(resultSet.getInt("idColonia"));
            colonia.setNombre(resultSet.getString("Nombre"));
            colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
            
        }
        return true;
        });
        
        }catch(Exception ex){
            result.correct= false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            
        }
        
        
        
        return result;
    }
    
}
