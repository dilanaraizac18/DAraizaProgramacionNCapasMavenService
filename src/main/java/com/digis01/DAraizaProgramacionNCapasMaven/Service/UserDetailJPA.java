/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.DAraizaProgramacionNCapasMaven.Service;

import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.UsuarioDAOJPAImplementation;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Result;
import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Usuario;
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
public class UserDetailJPA implements UserDetailsService {

    private final UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;

    public UserDetailJPA(UsuarioDAOJPAImplementation usuarioDAOJPAImplementation) {
        this.usuarioDAOJPAImplementation = usuarioDAOJPAImplementation;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Result result = usuarioDAOJPAImplementation.GetByEmail(username);

        if (result.correct) {
            Usuario usuario = (Usuario) result.object;

            return User.withUsername(usuario.getUsername())
                    .password(usuario.getPassword())
                    .roles(usuario.Rol.getNombreRol())
                    .disabled(usuario.getStatus() == 0)
                    .build();
        } else {
            throw new UsernameNotFoundException("No se ha encontrado el usuario con el corre " + username);

        }
    }
}
