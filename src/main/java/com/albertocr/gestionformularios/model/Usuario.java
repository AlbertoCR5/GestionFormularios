package com.albertocr.gestionformularios.model;

/**
 * Representa el modelo de datos para un Usuario.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.1
 */
public class Usuario {

    /**
     * Define los roles de usuario disponibles en la aplicación.
     */
    public enum Rol {
        ADMIN,
        STANDARD
    }

    private int id;
    private String nombreUsuario;
    private String contrasena;
    private Rol rol;
    private boolean debeCambiarContrasena;

    /**
     * Constructor para crear un nuevo objeto Usuario.
     *
     * @param nombreUsuario El nombre de usuario.
     * @param contrasena    La contraseña del usuario (se recomienda que ya esté hasheada).
     * @param rol           El rol del usuario.
     * @param debeCambiarContrasena true si el usuario debe cambiar la contraseña en el próximo inicio de sesión.
     */
    public Usuario(String nombreUsuario, String contrasena, Rol rol, boolean debeCambiarContrasena) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.rol = rol;
        this.debeCambiarContrasena = debeCambiarContrasena;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean isDebeCambiarContrasena() {
        return debeCambiarContrasena;
    }

    public void setDebeCambiarContrasena(boolean debeCambiarContrasena) {
        this.debeCambiarContrasena = debeCambiarContrasena;
    }
}
