/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.DAraizaProgramacionNCapasMaven.RestController;

import com.digis01.DAraizaProgramacionNCapasMaven.JPA.Result;
import com.digis01.DAraizaProgramacionNCapasMaven.Service.JwtService;
import com.digis01.DAraizaProgramacionNCapasMaven.Service.UserDetailJPAService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Dilan
 */
@RestController
@RequestMapping("/auth")
public class AuthRestController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailJPAService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.get("email"), 
                loginRequest.get("password")
            )
        );

        UserDetails user = userDetailsService.loadUserByUsername(loginRequest.get("email"));
        // si todo fue correcto
        String token = jwtService.generateToken(user);
        // api key 
        Map<String, Object> map = new HashMap<>();
        map.put("Key", token);
        Result result = new Result();
        result.object = map.get("key");
        result.correct = true;
        
        
        
        return ResponseEntity.ok(map);
    }
}
