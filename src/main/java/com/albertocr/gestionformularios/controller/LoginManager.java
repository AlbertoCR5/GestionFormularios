package com.albertocr.gestionformularios.controller;

import com.albertocr.gestionformularios.model.Usuario;
import com.albertocr.gestionformularios.model.UsuarioDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Gestiona la lógica de autenticación de usuarios.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.3
 */
public class LoginManager {

    private static final Logger logger = LoggerFactory.getLogger(LoginManager.class);
    private final UsuarioDAO usuarioDAO;

    public LoginManager(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    /**
     * Intenta autenticar a un usuario.
     *
     * @param nombreUsuario El nombre de usuario.
     * @param contrasena    La contraseña del usuario. Se recomienda cambiar a char[] para mayor seguridad.
     * @return Un {@link Optional} con el objeto {@link Usuario} si la autenticación es exitosa.
     */
    public Optional<Usuario> autenticar(String nombreUsuario, String contrasena) {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty() || contrasena == null || contrasena.trim().isEmpty()) {
            logger.warn("Intento de autenticación con nombre de usuario o contraseña nulos o vacíos.");
            return Optional.empty();
        }

        Optional<Usuario> usuarioOpt = usuarioDAO.validarUsuario(nombreUsuario, contrasena);

        if (usuarioOpt.isPresent()) {
            logger.info("Autenticación exitosa para el usuario: {}", nombreUsuario);
            return usuarioOpt;
        } else {
            // Se evita registrar el nombre de usuario para no revelar si un usuario existe o no.
            logger.warn("Fallo de autenticación.");
            return Optional.empty();
        }
    }
}
