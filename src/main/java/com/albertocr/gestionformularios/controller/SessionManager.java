package com.albertocr.gestionformularios.controller;

import com.albertocr.gestionformularios.model.Usuario;

/**
 * Gestiona la sesión del usuario activo en la aplicación.
 * Utiliza un patrón Singleton para garantizar una única instancia.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.1
 */
public final class SessionManager {

    private static final SessionManager instance = new SessionManager();
    private Usuario usuarioActual;

    private SessionManager() {
    }

    /**
     * Obtiene la instancia única del SessionManager.
     *
     * @return La instancia del SessionManager.
     */
    public static SessionManager getInstance() {
        return instance;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public void cerrarSesion() {
        this.usuarioActual = null;
    }
}
