package com.ejemplo.personas.controller;

import com.ejemplo.personas.model.Usuario;
import com.ejemplo.personas.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller 
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service){
        this.service = service;
    }

    // Leer Usuarios (CON BÚSQUEDA)
    @GetMapping
    public String listar(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("usuarios", service.buscar(q)); 
        model.addAttribute("q", q); 
        return "usuarios/lista";
    }

    // Crear Usuarios 
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("modo","crear");
        return "usuarios/formulario";
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult errores, Model model, RedirectAttributes flash) {
        if (errores.hasErrors()){
            model.addAttribute("modo", "crear");
            return "usuarios/formulario";
        }
        try {
            service.crear(usuario);
            flash.addFlashAttribute("mensaje", "Usuario creado correctamente");
            return "redirect:/usuarios";
        } catch (IllegalArgumentException e){
            model.addAttribute("modo", "crear");
            model.addAttribute("error", e.getMessage());
            return "usuarios/formulario";
        }
    }

    // Actualizar usuarios 
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Usuario u = service.buscarPorId(id);
        u.setPassword("");
        model.addAttribute("usuario", u);
        model.addAttribute("modo", "editar");
        return "usuarios/formulario";
    }

    @PostMapping("/{id}/actualizar")
    public String actualizar(@PathVariable Long id, @Valid @ModelAttribute("usuario") Usuario usuario, BindingResult errores, Model model, RedirectAttributes flash){
        if(errores.hasErrors()){
            usuario.setId(id);
            model.addAttribute("modo", "editar");
            return "usuarios/formulario";
        }
        try {
            service.actualizar(id, usuario);
            flash.addFlashAttribute("mensaje", "Usuario actualizado correctamente");
            return "redirect:/usuarios";
        } catch (IllegalArgumentException e){
            usuario.setId(id);
            model.addAttribute("modo", "editar");
            model.addAttribute("error", e.getMessage());
            return "usuarios/formulario";
        }
    }
    
    // Eliminar un Usuario (auto-eliminación)
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash, Authentication authentication) {
        // Se obtiene el username de la persona que está logueada
        String usernameActual = authentication.getName();
        Usuario usuarioAEliminar = service.buscarPorId(id);

        // Se compara si el que intenta borrar es el mismo que está logueado
        if (usuarioAEliminar.getUsername().equals(usernameActual)) {
            flash.addFlashAttribute("error", "Error: Un administrador no puede eliminarse a sí mismo.");
            return "redirect:/usuarios";
        }

        //  Si no es él mismo, se elimina
        service.eliminar(id);
        flash.addFlashAttribute("mensaje", "Usuario eliminado con exito!");
        return "redirect:/usuarios";
    }

    // Mostrar detalle de un usuario
    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model, RedirectAttributes flash) {
        try {
            Usuario u = service.buscarPorId(id);
            model.addAttribute("usuario", u);
            return "usuarios/detalle";
        } catch (IllegalArgumentException e) {
            // Si el ID no existe, se muestra el mensaje:
            flash.addFlashAttribute("error", "El usuario solicitado no existe.");
            return "redirect:/usuarios";
        }
    }
}