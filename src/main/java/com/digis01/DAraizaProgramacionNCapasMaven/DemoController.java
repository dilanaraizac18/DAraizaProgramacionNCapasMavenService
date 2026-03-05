/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.DAraizaProgramacionNCapasMaven;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("demo")
public class DemoController {
    
    @GetMapping("saludo")
    public String Test(@RequestParam String nombre, Model model){
        model.addAttribute("nombre", nombre);
        return "Saludo";
    }
    
    @GetMapping("saludo/{nombre}")
    public String Test2(@PathVariable("nombre") String nombre , Model model){
        model.addAttribute("nombre", nombre);
        return "Saludo";
    }
    
    @GetMapping("suma")
    public String Suma(@RequestParam int numero1, int numero2, Model model){
        model.addAttribute("numero1", numero1);
        model.addAttribute("numero2", numero2);
        return "Saludo";
        
    }
    
    @GetMapping("factorial") 
    public String Factorial (@RequestParam int n, Model model){
    int factorial = 1;
    for(int i= 1; i<=n; i++){
        factorial *=i;
    }
    model.addAttribute("resultado", factorial);
    
    return "Saludo";
}
    
    @GetMapping("getAll")
    public String GetAll(Model model){
        return("GetAll");
    }
    
    @GetMapping("formulario")
    public String Formulario(Model model){
        return ("Formulario");
    }
}
