package com.ejemplo.personas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


//Controlador de Login

@Controller 
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String inicio() {
        return "redirect:/usuarios";
    }
    
    
    
}
