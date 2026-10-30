package com.ejemplo.personas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

@Entity 
@Table(name="usuarios")
public class Usuario {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @NotBlank (message = "El nombre es requerido!")
    @Size (max=100)
    private String nombre; 

    @NotBlank (message = "El username es requerido!")
    @Size (min=3, max=50, message = "El username debe contener entre 3 y 50 caracteres")
    @Column (unique=true, nullable = false)
    private String username;

    @NotBlank (message = "El email es requerido!")
    @Email (message ="Correo no valido!")
    private String email; 

    @Column (nullable = false)
    private String password;

    @Column (nullable = false)
    private String rol ="USER";

    @NotBlank(message = "El teléfono es requerido")
    @Pattern(regexp = "\\d{8}", message = "El teléfono debe tener 8 digitos") //Pattern: para que tenga solo 8 digitos
    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private boolean activo = true;

    public Usuario(){}

    public Usuario(String nombre, String username, String email, String password, String rol, String telefono, boolean activo){
    this.nombre = nombre; 
    this.username = username; 
    this.email = email;
    this.password = password;
    this.rol = rol; 
    this.telefono = telefono;
    this.activo = activo;
    }

    //Getters y Setters 
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getRol(){
        return rol;
    }

    public void setRol(String rol){
        this.rol = rol;
    }

    public String getTelefono(){
        return telefono;
    }

    public void setTelefono(String telefono){
        this.telefono = telefono;
    }

    public boolean isActivo(){ //Is... por ser booleana
        return activo; 
    }

    public void setActivo(boolean activo){
         this.activo = activo; 
    }
}

