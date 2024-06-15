package com.example.proyecto.controller;

import com.example.proyecto.modal.DatabaseManager;
import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.MessageManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * La clase `LoginManager` gestiona la lógica de inicio de sesión de la aplicación.
 * Verifica las credenciales del usuario y controla el acceso al sistema.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class LoginManager {

    private final DatabaseManager databaseManager;
    private String usuarioActual;

    /**
     * Constructor para `LoginManager`.
     *
     * @param databaseManager La instancia de `DatabaseManager`.
     */
    public LoginManager(@NotNull DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    /**
     * Verifica las credenciales del usuario.
     *
     * @param usuario    El nombre de usuario.
     * @param contrasena La contraseña del usuario.
     * @return true si las credenciales son válidas, false en caso contrario.
     */
    public boolean verificarCredenciales(@NotNull String usuario, @NotNull String contrasena) {
        String sqlLogin = "SELECT * FROM Usuario WHERE nombre_usuario = ? AND contrasena = ?";
        try (Connection conn = databaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sqlLogin)) {

            pstmt.setString(1, usuario);
            pstmt.setString(2, contrasena);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    this.usuarioActual = usuario;
                    return true; // Devuelve true si hay algún resultado, lo que significa que las credenciales son correctas
                }
            }
        } catch (SQLException e) {
            Constantes.LOGGER.log(Level.SEVERE, MessageManager.getMessage("login.error"), e);
        } catch (Exception e) {
            Constantes.LOGGER.log(Level.SEVERE, MessageManager.getMessage("login.error.desconocido"), e);
        }
        return false;
    }

    /**
     * Obtiene el nombre del usuario actual.
     *
     * @return El nombre del usuario actual, o null si no hay usuario logueado.
     */
    @Nullable
    public String getUsuarioActual() {
        return usuarioActual;
    }
}
