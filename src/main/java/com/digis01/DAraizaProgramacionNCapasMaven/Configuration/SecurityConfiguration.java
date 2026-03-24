/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.DAraizaProgramacionNCapasMaven.Configuration;

import com.digis01.DAraizaProgramacionNCapasMaven.Component.JwtAuthenticationFilter;
import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.UsuarioDAOJPAImplementation;
import com.digis01.DAraizaProgramacionNCapasMaven.Service.UserDetailJPAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author digis
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthentication;
    
    private final UserDetailJPAService userDetailJPA;
    
    public SecurityConfiguration (UserDetailJPAService userDetailJPA){
        this.userDetailJPA = userDetailJPA;
    }
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
//        http.authorizeHttpRequests(configurer -> configurer
//        .requestMatchers("/usuario/**")
//        .hasAnyRole("Administrador", "Editor", "Usuario Estandar", "Visor", "Invitado")
//        .requestMatchers("/css/**", "/js/**", "/images/**", "/login").permitAll()
//        .anyRequest().authenticated())
//                
//        .formLogin(form -> form 
//         .loginPage("/login")
//        .loginProcessingUrl("/login")
//        .defaultSuccessUrl("/usuario")
//         .failureUrl("/login?error=true")
//        )
//        .userDetailsService(userDetailJPA);
//        
//        return http.build();

    return http
        .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/**").permitAll()
                    .anyRequest().authenticated()
            )
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
            .addFilterBefore(jwtAuthentication, UsernamePasswordAuthenticationFilter.class)
            .build();
    
    }
    
     @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}
 
