
package com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO;

import com.digis01.DAraizaProgramacionNCapasMaven.ML.Colonia;
import com.digis01.DAraizaProgramacionNCapasMaven.ML.Direccion;
import com.digis01.DAraizaProgramacionNCapasMaven.ML.Estado;
import com.digis01.DAraizaProgramacionNCapasMaven.ML.Municipio;
import com.digis01.DAraizaProgramacionNCapasMaven.ML.Pais;
import com.digis01.DAraizaProgramacionNCapasMaven.ML.Result;
import com.digis01.DAraizaProgramacionNCapasMaven.ML.Rol;
import com.digis01.DAraizaProgramacionNCapasMaven.ML.Usuario;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioDAOImplementation implements IUsuario{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    

    @Override
    public Result GetAll() {
        Result result = new Result();
        
        jdbcTemplate.execute("{CALL UsuarioDireccionGetAllSP(?)}", (CallableStatementCallback<Boolean>)callableStatement ->{
            callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
            callableStatement.execute();
            
            ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
            
            result.objects = new ArrayList<>();
            
            
            
            
            while(resultSet.next()){
                int idUsuario = resultSet.getInt("idUsuario");
                if (!result.objects.isEmpty() && idUsuario == ((Usuario) (result.objects.get(result.objects.size() - 1))).getIdUsuario()) {
                    Direccion direccion = new Direccion();
                    Colonia colonia = new Colonia();
                    Municipio municipio= new Municipio();
                    Estado estado = new Estado();
                    Pais pais = new Pais();
                    direccion.setIdDireccion(resultSet.getInt("idDireccion"));
                    direccion.setCalle(resultSet.getString("Calle"));
                    direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                    direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                    direccion.colonia = new Colonia();
                    direccion.colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                    direccion.colonia.setNombre(resultSet.getString("NombreColonia"));
                    direccion.colonia.municipio = new Municipio();
                    direccion.colonia.municipio.setNombre(resultSet.getString("MunicipioNombre"));
                    direccion.colonia.municipio.estado = new Estado();
                    direccion.colonia.municipio.estado.setNombre(resultSet.getNString("NombreEstado"));
                    direccion.colonia.municipio.estado.pais = new Pais();
                    direccion.colonia.municipio.estado.pais.setNombre(resultSet.getString("NombrePais"));
                    

                    ((Usuario) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);

                } else {
                    Usuario usuario = new Usuario();

                    usuario.setIdUsuario(idUsuario);
                    usuario.setNombre(resultSet.getString("NombreUsuario"));
                    usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                    usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                    usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                    usuario.setUsername(resultSet.getString("Username"));
                    usuario.setCURP(resultSet.getString("CURP"));
                    usuario.setEmail(resultSet.getString("Email"));
                    usuario.setNumeroTelefonico(resultSet.getString("NumeroTelefonico"));
                    usuario.setPassword(resultSet.getString("Password"));
                    usuario.setCelular(resultSet.getString("Celular"));
                    usuario.setImagen(resultSet.getString("Imagen"));
                    usuario.setStatus(resultSet.getInt("Status"));
                    usuario.Rol = new Rol();
                    usuario.Rol.setNombreRol(resultSet.getString("NombreRol"));

                    int idDireccion = resultSet.getInt("idDireccion");
                    
                     if (idDireccion != 0) {
                        usuario.Direcciones = new ArrayList<>();
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(idDireccion);
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        usuario.Direcciones.add(direccion);

                    }

                    

                    result.objects.add(usuario);

                }
            }
            
            return true;

        });
        return result;
        
        
    }

    @Override
    public Result GetById(int identificador) {
        
        Result result = new Result();
        
        try{
        jdbcTemplate.execute("{CALL UsuarioDireccionGetById(?,?)}", (CallableStatementCallback<Boolean>)callableStatement ->{
         callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
         callableStatement.setInt(2, identificador );
           
         callableStatement.execute();
         
         ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
         
         result.objects = new ArrayList<>();
                while (resultSet.next()) {
                    int idUsuario = resultSet.getInt("idUsuario");
                    //si no esta vacias yyy es el mismo que el ultimo
                    if (!result.objects.isEmpty() && idUsuario == ((Usuario) (result.objects.get(result.objects.size() - 1))).getIdUsuario()) {
                        Direccion direccion = new Direccion();

                        direccion.setIdDireccion(resultSet.getInt("idDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior("NumeroInterior");
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        direccion.colonia = new Colonia();
                        direccion.colonia.municipio = new Municipio();
                        direccion.colonia.municipio.estado = new Estado();
                        direccion.colonia.municipio.estado.pais = new Pais();

                        direccion.colonia.setIdColonia(resultSet.getInt("idColonia"));
                        direccion.colonia.setNombre(resultSet.getString("NombreColonia"));
                        direccion.colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.colonia.municipio.setIdMunicipio(resultSet.getInt("idMunicipio"));
                        direccion.colonia.municipio.setNombre(resultSet.getString("MunicipioNombre"));
                        direccion.colonia.municipio.estado.setIdEstado(resultSet.getInt("idEstado"));
                        direccion.colonia.municipio.estado.setNombre(resultSet.getString("NombreEstado"));
                        direccion.colonia.municipio.estado.pais.setIdPais(resultSet.getInt("idPais"));
                        direccion.colonia.municipio.estado.pais.setNombre("NombrePais");

                        ((Usuario) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);
                    } else {
                        Usuario usuario = new Usuario();
                        usuario.setIdUsuario(idUsuario);
                        usuario.setNombre(resultSet.getString("NombreUsuario"));
                        usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuario.setNumeroTelefonico(resultSet.getString("NumeroTelefonico"));
                        usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                        usuario.setEmail(resultSet.getString("Email"));
                        usuario.setCURP(resultSet.getString("CURP"));
                        usuario.setUsername(resultSet.getString("Username"));
                        usuario.setSexo(resultSet.getString("Sexo"));
                        usuario.setPassword(resultSet.getString("Password"));
                        usuario.setCelular(resultSet.getString("Celular"));
                        usuario.setImagen(resultSet.getString("Imagen"));
                        usuario.Rol = new Rol();
                        usuario.Rol.setidRol(resultSet.getInt("idRol"));
                        usuario.Rol.setNombreRol(resultSet.getString("NombreRol"));

                        int idDireccion = resultSet.getInt("IdDireccion");
                        if (idDireccion != 0) {
                            usuario.Direcciones = new ArrayList<>();
                            Direccion direccion = new Direccion();
                            direccion.setIdDireccion(idDireccion);
                            direccion.setCalle(resultSet.getString("Calle"));
                            direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                            direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                            direccion.colonia = new Colonia();
                            direccion.colonia.municipio = new Municipio();
                            direccion.colonia.municipio.estado = new Estado();
                            direccion.colonia.municipio.estado.pais = new Pais();

                            direccion.colonia.setIdColonia(resultSet.getInt("idColonia"));
                            direccion.colonia.setNombre(resultSet.getString("NombreColonia"));
                            direccion.colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                            direccion.colonia.municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                            direccion.colonia.municipio.setNombre(resultSet.getString("MunicipioNombre"));
                            direccion.colonia.municipio.estado.setIdEstado(resultSet.getInt("IdEstado"));
                            direccion.colonia.municipio.estado.setNombre(resultSet.getString("NombreEstado"));
                            direccion.colonia.municipio.estado.pais.setIdPais(resultSet.getInt("IdPais"));
                            direccion.colonia.municipio.estado.pais.setNombre("NombrePais");

                            usuario.Direcciones.add(direccion);

                        }
                        result.object = usuario;
                        result.correct = true;
                    }
                }
                return true;
            });
        
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
}            
        
            
      
        
        return result;
    }
    
    @Override
    public Result Add(Usuario usuario) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("{CALL UsuarioDireccionesAddSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
//            ResultSet resultSet = (ResultSet) callableStatement.getObject(1);            
                Direccion direccion = usuario.Direcciones.get(0);
//            direccion.setCalle(resultSet.getString("Calle")); //ya no se usa el resultset

                callableStatement.setString(1, usuario.getNombre());
                callableStatement.setString(2, usuario.getApellidoPaterno());
                callableStatement.setString(3, usuario.getApellidoMaterno());
                callableStatement.setString(4, usuario.getEmail());
                callableStatement.setString(5, usuario.getPassword());
                callableStatement.setString(6, usuario.getUsername());

               
                callableStatement.setDate(7, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                System.out.println(usuario.getFechaNacimiento());
                System.out.println(usuario.getFechaNacimiento().getTime());
                callableStatement.setString(8, usuario.getSexo());

                callableStatement.setString(9, usuario.getNumeroTelefonico());
                callableStatement.setString(10, usuario.getCelular());
                callableStatement.setString(11, usuario.getCURP());
                callableStatement.setInt(12, usuario.Rol.getidRol());
                callableStatement.setString(13, usuario.getImagen());
                //aqui empiezan las direcciones
                callableStatement.setString(14, direccion.getCalle());
                callableStatement.setString(15, direccion.getNumeroInterior());
                callableStatement.setString(16, direccion.getNumeroExterior());
                callableStatement.setInt(17, direccion.colonia.getIdColonia());

                int rowAffected = 0;
                rowAffected = callableStatement.executeUpdate();

                result.correct = rowAffected != 0 ? true : false;

                return true;
            });
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }
    @Override
    public Result Delete (int identificadorUsuario){
        Result result = new Result();
        
        try{
            jdbcTemplate.execute("{Call UsuarioDeleteSP(?)}", (CallableStatementCallback<Boolean>) callableStatement->{
               
                callableStatement.setInt(1, identificadorUsuario);
                int rowAffect = callableStatement.executeUpdate();
                
                if(rowAffect !=0){
                    System.out.println("Completado con exito");
                    result.correct = true;
                }else{
                    result.correct = false;
                }
                
                return true;
            });
            
            
        } catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        
        return result;
    }
    
  
    @Override
    public Result Update(Usuario usuario) {
        Result result = new Result();

        try {

            jdbcTemplate.execute("{CALL UsuarioUpdateSP(?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.setString(1, usuario.getNombre());
                callableStatement.setString(2, usuario.getApellidoPaterno());
                callableStatement.setString(3, usuario.getApellidoMaterno());
                callableStatement.setDate(4, new java.sql.Date(usuario.getFechaNacimiento().getTime()));                
                callableStatement.setString(5, usuario.getCURP());                
                callableStatement.setString(6, usuario.getUsername());
                callableStatement.setString(7, usuario.getEmail());

                callableStatement.setString(8, usuario.getNumeroTelefonico());
                callableStatement.setString(9, usuario.getSexo());
                callableStatement.setString(10, usuario.getCelular());
                callableStatement.setInt(11, usuario.Rol.getidRol());
                callableStatement.setInt(12, usuario.getIdUsuario());
                
                int rowAffectted = 0;
                rowAffectted = callableStatement.executeUpdate();
                
                result.correct = rowAffectted != 0 ? true : false;

                return true;
            });

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    
    @Override
    public Result UpdateImagen(Usuario usuario){
        Result result = new Result();
        
        try{
            jdbcTemplate.execute("{CALL UsuarioImageUpdateSP(?,?)}", (CallableStatementCallback<Boolean>) callableStatement ->{
            callableStatement.setString(1, usuario.getImagen());
            callableStatement.setInt(2, usuario.getIdUsuario());
             int rowAffectted = 0;
                rowAffectted = callableStatement.executeUpdate();
                
                result.correct = rowAffectted != 0 ? true : false;

                return true;
            } );
        
        
        }
            
            
        catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
}
    
    @Override 
    public Result Search(Usuario Usuario){
        Result result = new Result();
        
        Usuario usuario = new Usuario();
        usuario.Rol = new Rol();
        
        try{
            
            jdbcTemplate.execute("{CALL UsuarioSearchSP(?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement ->{
              callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
              callableStatement.setString(2, usuario.getNombre());
              callableStatement.setString(3, usuario.getApellidoPaterno());
              callableStatement.setString(4, usuario.getApellidoMaterno());
              callableStatement.setInt(5, usuario.Rol.getidRol());
              
              callableStatement.execute();
              
              
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                result.objects = new ArrayList<>();
                //
                while (resultSet.next()) {
                    int idUsuario = resultSet.getInt("IdUsuario");
                    if (!result.objects.isEmpty() && idUsuario == ((Usuario) (result.objects.get(result.objects.size() - 1))).getIdUsuario()) {

                        Direccion direccion = new Direccion();

                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior("NumeroInterior");
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        direccion.colonia = new Colonia();
                        direccion.colonia.municipio = new Municipio();
                        direccion.colonia.municipio.estado = new Estado();
                        direccion.colonia.municipio.estado.pais = new Pais();

                        direccion.colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.colonia.setNombre(resultSet.getString("NombreColonia"));
                        direccion.colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.colonia.municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.colonia.municipio.setNombre(resultSet.getString("NombreMunicipio"));
                        direccion.colonia.municipio.estado.setIdEstado(resultSet.getInt("IdEstado"));
                        direccion.colonia.municipio.estado.setNombre(resultSet.getString("NombreEstado"));
                        direccion.colonia.municipio.estado.pais.setIdPais(resultSet.getInt("IdPais"));
                        direccion.colonia.municipio.estado.pais.setNombre("NombrePais");

                        /////////////////////////////////
                        ((Usuario) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);

                    } else {
                        usuario.Direcciones = new ArrayList<>();
                        Direccion direccion = new Direccion();

                        usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                        usuario.setNombre(resultSet.getString("NombreUsuario"));
                        usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuario.setNumeroTelefonico(resultSet.getString("NumeroTelefonico"));
                        usuario.setEmail(resultSet.getString("Email"));
                        usuario.setCelular(resultSet.getString("Celular"));
                        usuario.setUsername(resultSet.getString("Username"));
                        usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                        usuario.setCURP(resultSet.getString("CURP"));
                        usuario.setImagen(resultSet.getString("Imagen"));
                        usuario.setSexo(resultSet.getString("Sexo"));

                        usuario.Rol = new Rol();
                        usuario.Rol.setidRol(resultSet.getInt("IdRol"));

                        result.objects.add(usuario);

                        int idDireccion = resultSet.getInt("IdDireccion");
                        if (idDireccion != 0) {
                            usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                            usuario.setNombre(resultSet.getString("NombreUsuario"));
                            usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                            usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                            usuario.setNumeroTelefonico(resultSet.getString("NumeroTelefonico"));
                            usuario.setEmail(resultSet.getString("Email"));
                            usuario.setCelular(resultSet.getString("Celular"));
                            usuario.setUsername(resultSet.getString("Username"));
                            usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                            usuario.setCURP(resultSet.getString("Curp"));
                            usuario.setImagen(resultSet.getString("Imagen"));
                            usuario.setSexo(resultSet.getString("Sexo"));

                            ///////////////////////////////
                            direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                            direccion.setCalle(resultSet.getString("Calle"));
                            direccion.setNumeroInterior("NumeroInterior");
                            direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                            direccion.colonia = new Colonia();
                            direccion.colonia.municipio = new Municipio();
                            direccion.colonia.municipio.estado = new Estado();
                            direccion.colonia.municipio.estado.pais = new Pais();

                            direccion.colonia.setIdColonia(resultSet.getInt("IdColonia"));
                            direccion.colonia.setNombre(resultSet.getString("NombreColonia"));
                            direccion.colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                            direccion.colonia.municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                            direccion.colonia.municipio.setNombre(resultSet.getString("NombreMunicipio"));
                            direccion.colonia.municipio.estado.setIdEstado(resultSet.getInt("IdEstado"));
                            direccion.colonia.municipio.estado.setNombre(resultSet.getString("NombreEstado"));
                            direccion.colonia.municipio.estado.pais.setIdPais(resultSet.getInt("IdPais"));
                            direccion.colonia.municipio.estado.pais.setNombre("NombrePais");

                            /////////////////////////////////
                            ((Usuario) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);

                        }

                    }
                }
                //
                
                
                
                return true;
            });
            
            
            
            
            
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            
        }
        
        
        return result;
    }
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result AddAll(List<Usuario> usuarios) {
        Result result = new Result();
        
        try{
            jdbcTemplate.batchUpdate("{CALL  USUARIODIRECCIONESADDSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", usuarios, usuarios.size(), 
                    (callableStatement,usuario) ->{
                        
                        
                            callableStatement.setString(1, usuario.getNombre());
                            callableStatement.setString(2, usuario.getApellidoPaterno());
                            callableStatement.setString(3, usuario.getApellidoMaterno());
                            callableStatement.setString(4, usuario.getEmail());
                            callableStatement.setString(5, usuario.getUsername());
                            callableStatement.setDate(7, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                            callableStatement.setString(8, usuario.getSexo());
                            callableStatement.setString(9, usuario.getNumeroTelefonico());
                            callableStatement.setString(10,usuario.getCelular());
                            callableStatement.setString(11, usuario.getCURP());
                            callableStatement.setInt(12, usuario.Rol.getidRol());
                callableStatement.setString(13, usuario.getImagen());
                //aqui empiezan las direcciones
//                callableStatement.setString(14, direccion.getCalle());
//                callableStatement.setString(15, direccion.getNumeroInterior());
//                callableStatement.setString(16, direccion.getNumeroExterior());
//                callableStatement.setInt(17, direccion.colonia.getIdColonia());

                int rowAffected = 0;
                rowAffected = callableStatement.executeUpdate();

                result.correct = rowAffected != 0 ? true : false;

            });
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

      
        
        
        
        return result;
    }

    @Override
    public Result UpdateStatus(int status, int identUsuario) {
        Result result = new Result();
        Usuario usuario = new Usuario();
        
        try{
            jdbcTemplate.execute("{CALL UsuarioUpdateStatus(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, status);
                callableStatement.setInt(2, identUsuario);
                
                return true;
            });
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
}
 


//CHECAR EL PROCEDURE Y ARREGLAR LA BD DE LA CASA


//create or replace NONEDITIONABLE PROCEDURE UsuarioDireccionGetAllSP(
//pCursor OUT SYS_REFCURSOR
//)
//AS
//
//BEGIN 
//OPEN pCursor FOR
//SELECT Usuario.idUsuario, Usuario.Nombre as NombreUsuario, Usuario.ApellidoPaterno, Usuario.ApellidoMaterno, Usuario.FechaNacimiento, Usuario.CURP, Usuario.UserName, Usuario.Email, Usuario.NuevoTelefono as NumeroTelefonico, Usuario.Password,
//Usuario.Sexo, Usuario.Celular, Usuario.Imagen, Rol.NombreRol, Rol.idRol, Direccion.idDireccion, Direccion.Calle, Direccion.NumeroInterior, Direccion.NumeroExterior, Direccion.idColonia_fk, Colonia.Nombre as NombreColonia, Colonia.CodigoPostal, Colonia.idMunicipio_fk,
//Municipio.Nombre as MunicipioNombre, Municipio.idEstado_fk, Estado.Nombre as NombreEstado, Estado.idPais_fk, Pais.NombrePais as NombrePais
//FROM Usuario
//LEFT JOIN Rol on Usuario.idRol_fk = Rol.idRol
//LEFT JOIN Direccion on Usuario.idUsuario = Direccion.idUsuario_fk
//LEFT JOIN Colonia on Direccion.idColonia_fk  = Colonia.idColonia
//LEFT JOIN Municipio on Colonia.idMunicipio_fk = Municipio.idMunicipio
//LEFT JOIN Estado on Municipio.idEstado_fk = Estado.idEstado
//LEFT JOIN Pais on Estado.idPais_fk = Pais.idPais order by idUsuario;
//
//END UsuarioDireccionGetAllSP;
//
//var pCursor REFCURSOR
//exec UsuarioDireccionGetAllSP(:pCursor)
//print pCursor
//
//var pCursor REFCURSOR
//exec UsuarioDireccionGetById (:pCursor, 81)
//print pCursor
//
//
//
//create or replace NONEDITIONABLE PROCEDURE UsuarioDireccionGetAllSP(
//pCursor OUT SYS_REFCURSOR
//)
//AS
//
//BEGIN 
//OPEN pCursor FOR
//SELECT Usuario.idUsuario, Usuario.Nombre as NombreUsuario, Usuario.ApellidoPaterno, Usuario.ApellidoMaterno, Usuario.FechaNacimiento, Usuario.CURP, Usuario.UserName, Usuario.Email, Usuario.NuevoTelefono as NumeroTelefonico, Usuario.Password,
//Usuario.Sexo, Usuario.Celula, Usuario.Imagen, Rol.NombreRol, Rol.idRol, Direccion.idDireccion, Direccion.Calle, Direccion.NumeroInterior, Direccion.NumeroExterior, Direccion.idColonia_fk, Colonia.Nombre as NombreColonia, Colonia.CodigoPostal, Colonia.idMunicipio_fk,
//Municipio.Nombre as MunicipioNombre, Municipio.idEstado_fk, Estado.Nombre as NombreEstado, Estado.idPais_fk, Pais.NombrePais as NombrePais
//FROM Usuario
//LEFT JOIN Rol on Usuario.idRol_fk = Rol.idRol
//LEFT JOIN Direccion on Usuario.idUsuario = Direccion.idUsuario_fk
//LEFT JOIN Colonia on Direccion.idColonia_fk  = Colonia.idColonia
//LEFT JOIN Municipio on Colonia.idMunicipio_fk = Municipio.idMunicipio
//LEFT JOIN Estado on Municipio.idEstado_fk = Estado.idEstado
//LEFT JOIN Pais on Estado.idPais_fk = Pais.idPais order by idUsuario;
//
//END UsuarioDireccionGetAllSP;
//
//
//
//
//    CREATE OR REPLACE PROCEDURE UsuarioDireccionGetById (
//pCursor out SYS_REFCURSOR,
//pIdUsuario IN number
//)
//AS
//
//BEGIN 
//OPEN pCursor for
//SELECT Usuario.idUsuario, Usuario.Nombre as NombreUsuario, Usuario.ApellidoPaterno, Usuario.ApellidoMaterno, Usuario.FechaNacimiento, Usuario.CURP, Usuario.Email, Usuario.NuevoTelefono as NumeroTelefonico, Usuario.Password,
//Usuario.Sexo, Usuario.Celular, Usuario.Username,Rol.idRol, Rol.NombreRol, Usuario.Imagen, Direccion.idDireccion, Direccion.Calle, Direccion.NumeroInterior, Direccion.NumeroExterior, Direccion.idColonia_fk as idColonia, Colonia.Nombre as NombreColonia, Colonia.CodigoPostal, Colonia.idMunicipio_fk as idMunicipio,
//Municipio.Nombre, Municipio.idEstado_fk as idEstado, Estado.Nombre as NombreEstado, Estado.idPais_fk as idPais, Pais.NombrePais as NombrePais
//FROM Usuario
//LEFT JOIN Rol on Rol.idRol = Usuario.idRol_fk
//LEFT JOIN Direccion on Usuario.idUsuario = Direccion.idUsuario_fk
//LEFT JOIN Colonia on Direccion.idColonia_fk  = Colonia.idColonia
//LEFT JOIN Municipio on Colonia.idMunicipio_fk = Municipio.idMunicipio
//LEFT JOIN Estado on Municipio.idEstado_fk = Estado.idEstado
//LEFT JOIN Pais on Estado.idPais_fk = Pais.idPais where Usuario.idUsuario = pIdUsuario;
//
//END UsuarioDireccionGetById;
//
//create or replace NONEDITIONABLE procedure UsuarioImageUpdateSP (
//pImagen in CLOB,
//pidUsuario in Number
//)
//as
//begin 
//
//Update Usuario set Imagen = pImagen where idUsuario = pidUsuario;
//
//end UsuarioImageUpdateSP;
//
//create or replace view UsuarioGetAllVW
//as 
//
//SELECT Usuario.idUsuario, Usuario.Nombre as NombreUsuario, Usuario.ApellidoPaterno, Usuario.ApellidoMaterno, Usuario.FechaNacimiento, Usuario.CURP, Usuario.UserName, Usuario.Email, Usuario.NuevoTelefono as NumeroTelefonico, Usuario.Password,
//Usuario.Sexo, Usuario.Celular, Usuario.Imagen, Rol.NombreRol, Rol.idRol, Direccion.idDireccion, Direccion.Calle, Direccion.NumeroInterior, Direccion.NumeroExterior, Direccion.idColonia_fk, Colonia.Nombre as NombreColonia, Colonia.CodigoPostal, Colonia.idMunicipio_fk,
//Municipio.Nombre as MunicipioNombre, Municipio.idEstado_fk, Estado.Nombre as NombreEstado, Estado.idPais_fk, Pais.NombrePais as NombrePais
//FROM Usuario
//LEFT JOIN Rol on Usuario.idRol_fk = Rol.idRol
//LEFT JOIN Direccion on Usuario.idUsuario = Direccion.idUsuario_fk
//LEFT JOIN Colonia on Direccion.idColonia_fk  = Colonia.idColonia
//LEFT JOIN Municipio on Colonia.idMunicipio_fk = Municipio.idMunicipio
//LEFT JOIN Estado on Municipio.idEstado_fk = Estado.idEstado
//LEFT JOIN Pais on Estado.idPais_fk = Pais.idPais order by idUsuario;
//
//
//select * from UsuarioGetAllVW where Lower(NombreUsuario) like lower('%a%')
//and lower (ApellidoPaterno) like lower ('%or%')
//and NVL(LOWER (ApellidoMaterno), 'Sin apellido Materno') like lower ('%%')
//and idRol  = 4
//
//;
//
//create or replace procedure UsuarioSearchSP(
//pCursor out SYS_REFCURSOR,
//pNombre in Varchar,
//pApellidoPaterno in varchar,
//pApellidoMaterno in varchar,
//pidRol in varchar
//
//)
//AS
//
//BEGIN 
//OPEN pCursor for
//select NombreUsuario, ApellidoPaterno, ApellidoMaterno, idRol from UsuarioGetAllVW where Lower(NombreUsuario) like lower('%'||pNombre||'%')
//and lower (ApellidoPaterno) like lower ('%'||pApellidoPaterno||'%')
//and NVL(LOWER (ApellidoMaterno), 'Sin apellido Materno') like lower ('%'||pApellidoMaterno||'%')
//and idRol  = pidRol;
//
//end UsuarioSearchSP;
//
//var pCursor REFCURSOR
//exec UsuarioSearchSP(:pCursor, 'a','ro','',4)
//print pCursor