/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.DAraizaProgramacionNCapasMaven.Service;

import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.UsuarioDAOJPAImplementation;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Result;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author digis
 */
@Service
public class UserDetailJPAService implements UserDetailsService{
    
    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;
    
//    public UserDetailJPAService(UsuarioDAOJPAImplementation usuarioDAOJPAImplementation){
//        this.usuarioDAOJPAImplementation = usuarioDAOJPAImplementation;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Result result = usuarioDAOJPAImplementation.GetByEmail(username);
        
        Usuario usuario = (Usuario) result.object;
        
        return new CustomUserDetailsService(
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getStatus() !=0,
                AuthorityUtils.createAuthorityList("ROLE_" + usuario.Rol.getNombreRol().trim()),
                usuario.getIdUsuario()
                );
    }
    
    
}
