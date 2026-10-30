package com.ejemplo.personas.repository;

import com.ejemplo.personas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface UsuarioRepository extends JpaRepository <Usuario, Long> {
    Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String username);

    //Para validar un correo único  
    boolean existsByEmail(String email);  

    // Buscando por nombre/usuario ignorando mayusculas-minusculas
    List<Usuario> findByNombreContainingIgnoreCaseOrUsernameContainingIgnoreCase(String nombre, String username);
}





