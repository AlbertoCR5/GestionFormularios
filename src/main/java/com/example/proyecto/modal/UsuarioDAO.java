package com.example.proyecto.modal;

import com.example.proyecto.interfaz.PrincipalView;
import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.MessageManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * La clase UsuarioDAO se encarga de gestionar las operaciones de la base de datos relacionadas con la tabla Usuario.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class UsuarioDAO {

    private final PrincipalView view;
    private final DatabaseManager databaseManager;

    /**
     * Constructor de la clase UsuarioDAO.
     *
     * @param view La vista principal de la aplicación.
     * @param databaseManager El gestor de base de datos.
     */
    public UsuarioDAO(PrincipalView view, DatabaseManager databaseManager) {
        this.view = view;
        this.databaseManager = databaseManager;
    }

    /**
     * Crea la tabla `Usuario` en la base de datos si no existe.
     *
     * @throws SQLException Si ocurre un error al crear la tabla en la base de datos.
     */
    public void createTableUsuario() throws SQLException {
        String sqlUsuario = """
                CREATE TABLE IF NOT EXISTS Usuario (
                 id INTEGER PRIMARY KEY,
                 nombre_usuario TEXT NOT NULL,
                 contrasena TEXT NOT NULL,
                 es_admin INTEGER NOT NULL
                );""";

        try (Connection connection = databaseManager.connect();
             PreparedStatement pstmt = connection.prepareStatement(sqlUsuario)) {
            pstmt.execute();
            // Verificar si la tabla de usuarios está vacía, si es así, insertar el administrador predeterminado
            if (isTableEmpty(connection)) {
                insertUsuario("admin", "123456", true);
            }
        } catch (SQLException e) {
            handleSQLException(e, "error.crear.tabla.usuario");
        }
    }

    /**
     * Verifica si una tabla está vacía.
     *
     * @param connection La conexión a la base de datos.
     * @return true si la tabla está vacía, false en caso contrario.
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */
    private boolean isTableEmpty(Connection connection) throws SQLException {
        String sqlCheck = "SELECT COUNT(*) AS total FROM Usuario";
        try (PreparedStatement pstmt = connection.prepareStatement(sqlCheck);
             ResultSet rs = pstmt.executeQuery()) {
            return rs.next() && rs.getInt("total") == 0;
        }
    }

    /**
     * Inserta un nuevo usuario en la base de datos.
     *
     * @param nombreUsuario El nombre de usuario.
     * @param contrasena La contraseña del usuario.
     * @param esAdmin Indica si el usuario es administrador.
     * @throws SQLException Si ocurre un error al insertar el usuario en la base de datos.
     */
    public void insertUsuario(String nombreUsuario, String contrasena, boolean esAdmin) throws SQLException {
        String sqlUsuario = "INSERT INTO Usuario(nombre_usuario, contrasena, es_admin) VALUES(?,?,?)";

        try (Connection connection = databaseManager.connect();
             PreparedStatement pstmt = connection.prepareStatement(sqlUsuario)) {
            pstmt.setString(1, nombreUsuario);
            pstmt.setString(2, contrasena);
            pstmt.setInt(3, esAdmin ? 1 : 0);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e, "error.insertar.usuario");
        }
    }

    /**
     * Actualiza la contraseña de un usuario en la base de datos.
     *
     * @param nombreUsuario El nombre de usuario.
     * @param nuevaContrasena La nueva contraseña del usuario.
     */
    public void updateContrasena(String nombreUsuario, String nuevaContrasena) {
        String sql = "UPDATE Usuario SET contrasena = ? WHERE nombre_usuario = ?";

        try (Connection connection = databaseManager.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nuevaContrasena);
            pstmt.setString(2, nombreUsuario);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e, "error.actualizar.contrasena");
        }
    }

    /**
     * Verifica la contraseña del usuario.
     *
     * @param nombreUsuario El nombre de usuario.
     * @param contrasena La contraseña del usuario.
     * @return true si la contraseña es correcta, false en caso contrario.
     */
    public boolean verificarContrasena(String nombreUsuario, String contrasena) {
        String sql = "SELECT contrasena FROM Usuario WHERE nombre_usuario = ?";

        try (Connection connection = databaseManager.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nombreUsuario);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("contrasena").equals(contrasena);
            } else {
                return false;
            }
        } catch (SQLException e) {
            handleSQLException(e, "error.obtener.contrasena");
            return false;
        }
    }

    /**
     * Elimina un usuario de la base de datos.
     *
     * @param nombreUsuario El nombre de usuario.
     */
    public void deleteUsuario(String nombreUsuario)  {
        String sql = "DELETE FROM Usuario WHERE nombre_usuario = ?";

        try (Connection connection = databaseManager.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nombreUsuario);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e, "error.eliminar.usuario");
        }
    }

    /**
     * Verifica si el usuario es administrador.
     *
     * @param nombreUsuario El nombre de usuario.
     * @return true si el usuario es administrador, false en caso contrario.
     */
    public boolean esAdmin(String nombreUsuario) {
        String sql = "SELECT es_admin FROM Usuario WHERE nombre_usuario = ?";

        try (Connection connection = databaseManager.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nombreUsuario);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("es_admin") == 1;
            } else {
                return false;
            }
        } catch (SQLException e) {
            handleSQLException(e, "error.obtener.admin");
            return false;
        }
    }

    /**
     * Maneja las excepciones SQL mostrando el mensaje correspondiente y registrando el error.
     *
     * @param e La excepción SQL.
     * @param mensajeClave La clave del mensaje de error en el MessageManager.
     */
    private void handleSQLException(SQLException e, String mensajeClave) {
        if (view != null) {
            view.mostrarMensaje(String.format(MessageManager.getMessage(mensajeClave), e.getMessage()), false);
        }
        Constantes.LOGGER.log(Level.SEVERE, "SQL Error: {0}", e.getMessage());
    }
}
