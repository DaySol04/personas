package com.ejemplo.personas.config;

import com.ejemplo.personas.model.Usuario;
import com.ejemplo.personas.repository.UsuarioRepository;
import com.ejemplo.personas.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Crear Usuarios para Poder Acceder al Sistema por primera vez 

@Configuration 
public class DataInitializer {
    
    @Bean 
    CommandLineRunner cargarUsuarios(UsuarioRepository repo, UsuarioService service){
        return args -> {
            if (repo.count() ==0 ){
                service.crear(new Usuario("Administrador","admin","admin@admin.com","admin123","ADMIN"));
                service.crear(new Usuario("Maria", "maria","maria@ejemplo.com","maria123","USER"));
            }
        };
    }
}
