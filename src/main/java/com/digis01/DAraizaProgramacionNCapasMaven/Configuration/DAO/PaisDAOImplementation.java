
package com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO;

import com.digis01.DAraizaProgramacionNCapasMaven.ML.Pais;
import com.digis01.DAraizaProgramacionNCapasMaven.ML.Result;
import com.digis01.DAraizaProgramacionNCapasMaven.ML.Usuario;
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
public class PaisDAOImplementation implements IPais{
    Result result = new Result();
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    

    @Override
    public Result GetAll() {
        jdbcTemplate.execute("{CALL PaisGetAllSP(?)}", (CallableStatementCallback<Boolean>)callableStatement ->{
        callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
        callableStatement.execute();
        
        ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
        
        result.objects = new ArrayList<>();
        
        while(resultSet.next()){
            int idPais= resultSet.getInt("idPais");
                Pais pais = new Pais();
                
                pais.setIdPais(resultSet.getInt("idPais"));
                pais.setNombre(resultSet.getString("NombrePais"));
                
                result.objects.add(pais);
            }

            
        
            return true;
        }
        
        
        
        );
        
                
                
        
        
       return result; 
    }
                
   }
    



