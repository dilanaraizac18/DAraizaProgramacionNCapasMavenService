
package com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO;

import com.digis01.DAraizaProgramacionNCapasMaven.ML.Result;
import com.digis01.DAraizaProgramacionNCapasMaven.ML.Rol;
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
public class RolDAOImplementation implements IRol{
    Result result = new Result();

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetAll() {    
        
    jdbcTemplate.execute("{CALL RolGetAllSP (?)}", (CallableStatementCallback<Boolean>) callableStatement ->{
        callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
        callableStatement.execute();
        
        ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
        
        result.objects = new ArrayList<>();
        
        while(resultSet.next()){
            int idRol = resultSet.getInt("idRol");
            
            Rol rol = new Rol();
            
            rol.setidRol(resultSet.getInt("idRol"));
            rol.setNombreRol(resultSet.getString("NombreRol"));
            
            result.objects.add(rol);
        }
        
    
    
    
    
        return true;
    }

    );
    
    
    
    
    
    
    return result;
    
    
    
    
    
    }
    
}
