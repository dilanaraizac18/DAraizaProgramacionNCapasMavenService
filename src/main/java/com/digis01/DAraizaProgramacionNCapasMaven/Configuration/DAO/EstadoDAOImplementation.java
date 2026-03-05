/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO;

import com.digis01.DAraizaProgramacionNCapasMaven.ML.Estado;
import com.digis01.DAraizaProgramacionNCapasMaven.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author digis
 */

@Repository
public class EstadoDAOImplementation implements IEstado{
    Result result = new Result();
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetByID(int idPais) {
        jdbcTemplate.execute("{CALL EstadoGetById (?, ?)}", (CallableStatementCallback<Boolean> ) callableStatement ->{
        
            callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
            callableStatement.setInt(2, idPais);
            callableStatement.execute();
            
            ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
            
            result.objects = new ArrayList<>();
            
            while(resultSet.next()){
                
                Estado estado = new Estado();
                result.objects.add(estado);

                estado.setIdEstado(resultSet.getInt("idEstado"));
                estado.setNombre(resultSet.getString("NombreEstado"));
                
                
            }
        return true;
        });
        return result;


    }
    
}
