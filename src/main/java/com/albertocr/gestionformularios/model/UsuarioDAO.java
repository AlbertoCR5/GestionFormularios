package com.albertocr.gestionformularios.model;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * DAO para gestionar las operaciones CRUD de la tabla 'usuarios'.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.2
 */
public class UsuarioDAO {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioDAO.class);

    /**
     * Busca un usuario por su nombre y valida la contraseña.
     *
     * @param nombreUsuario   El nombre de usuario.
     * @param contrasenaPlana La contraseña en texto plano para validar.
     * @return Un {@link Optional} con el objeto {@link Usuario} si las credenciales son válidas.
     */
    public Optional<Usuario> validarUsuario(String nombreUsuario, String contrasenaPlana) {
        Optional<Usuario> usuarioOpt = buscarPorNombreUsuario(nombreUsuario);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (BCrypt.checkpw(contrasenaPlana, usuario.getContrasena())) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }

    /**
     * Busca y devuelve un usuario por su nombre de usuario.
     *
     * @param nombreUsuario El nombre del usuario a buscar.
     * @return Un {@link Optional} que contiene al usuario si se encuentra.
     */
    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombreUsuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(crearUsuarioDesdeResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error al buscar el usuario '{}'", nombreUsuario, e);
        }
        return Optional.empty();
    }

    /**
     * Crea un nuevo usuario en la base de datos.
     *
     * @param usuario El objeto {@link Usuario} a crear. La contraseña debe estar en texto plano.
     * @return {@code true} si se creó con éxito.
     */
    public boolean crearUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios(nombre_usuario, contrasena, rol, debe_cambiar_contrasena) VALUES(?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String hashContrasena = BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt());
            pstmt.setString(1, usuario.getNombreUsuario());
            pstmt.setString(2, hashContrasena);
            pstmt.setString(3, usuario.getRol().name());
            pstmt.setInt(4, usuario.isDebeCambiarContrasena() ? 1 : 0);
            pstmt.executeUpdate();
            logger.info("Usuario '{}' creado con éxito.", usuario.getNombreUsuario());
            return true;
        } catch (SQLException e) {
            logger.error("Error al crear el usuario '{}'", usuario.getNombreUsuario(), e);
            return false;
        }
    }

    /**
     * Actualiza la contraseña de un usuario y desactiva la bandera de cambio de contraseña.
     *
     * @param nombreUsuario   El nombre del usuario a actualizar.
     * @param nuevaContrasena La nueva contraseña en texto plano.
     * @return {@code true} si la actualización fue exitosa.
     */
    public boolean actualizarContrasena(String nombreUsuario, String nuevaContrasena) {
        String sql = "UPDATE usuarios SET contrasena = ?, debe_cambiar_contrasena = 0 WHERE nombre_usuario = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String hashContrasena = BCrypt.hashpw(nuevaContrasena, BCrypt.gensalt());
            pstmt.setString(1, hashContrasena);
            pstmt.setString(2, nombreUsuario);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            logger.error("Error al actualizar la contraseña para el usuario '{}'", nombreUsuario, e);
            return false;
        }
    }

    /**
     * Elimina un usuario de la base de datos por su nombre.
     *
     * @param nombreUsuario El nombre del usuario a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminarUsuario(String nombreUsuario) {
        String sql = "DELETE FROM usuarios WHERE nombre_usuario = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombreUsuario);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            logger.error("Error al eliminar el usuario '{}'", nombreUsuario, e);
            return false;
        }
    }

    private Usuario crearUsuarioDesdeResultSet(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario(
                rs.getString("nombre_usuario"),
                rs.getString("contrasena"),
                Usuario.Rol.valueOf(rs.getString("rol")),
                rs.getInt("debe_cambiar_contrasena") == 1
        );
        usuario.setId(rs.getInt("id_usuario"));
        return usuario;
    }
}
