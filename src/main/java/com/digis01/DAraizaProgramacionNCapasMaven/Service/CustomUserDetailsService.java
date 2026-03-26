/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.DAraizaProgramacionNCapasMaven.Service;

import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.UsuarioDAOJPAImplementation;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author digis
 */
public class CustomUserDetailsService extends User{

    private final int idUsuario;

    public CustomUserDetailsService(String username, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities, int idUsuario) {
        super(username, password,enabled,true, true,true, authorities);
        this.idUsuario = idUsuario;
    }
    
    public int getIdUsuario(){
        return idUsuario;
    }
 
    
    
    
}
