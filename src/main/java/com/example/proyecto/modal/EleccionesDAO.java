package com.example.proyecto.modal;

import com.example.proyecto.interfaz.PrincipalView;
import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.DirectorioManager;
import com.example.proyecto.util.MessageManager;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * La clase EleccionesDAO se encarga de las operaciones de base de datos para la tabla Elecciones.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class EleccionesDAO {

    private final PrincipalView view;
    private final DatabaseManager databaseManager;

    /**
     * Constructor de la clase EleccionesDAO.
     *
     * @param view La vista principal de la aplicación.
     * @param databaseManager El gestor de la base de datos.
     */
    public EleccionesDAO(@NotNull PrincipalView view, @NotNull DatabaseManager databaseManager) {
        this.view = view;
        this.databaseManager = databaseManager;
    }

    /**
     * Crea la tabla Elecciones en la base de datos si no existe.
     *
     * @throws SQLException Sí ocurre un error al crear la tabla en la base de datos.
     */
    public void createTableElecciones() throws SQLException {
        String sqlElecciones = """
                CREATE TABLE IF NOT EXISTS Elecciones (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombreEmpresa TEXT,
                numeroPreaviso TEXT,
                fechaConstitucion TEXT,
                fechaEscrutinio TEXT,
                FOREIGN KEY (nombreEmpresa) REFERENCES Empresa(nombre)
                );""";

        try (Connection connection = databaseManager.connect();
             PreparedStatement pstmt = connection.prepareStatement(sqlElecciones)) {
            pstmt.execute();
        } catch (SQLException e) {
            handleSQLException(e, "error.crear.tabla.elecciones");
        }
    }

    /**
     * Inserta la fecha de constitución en la tabla Elecciones.
     *
     * @param nuevoPreaviso El objeto Preaviso con los datos de la constitución.
     */
    public void insertFechaConstitucion(@NotNull Preaviso nuevoPreaviso) {
        String sqlElecciones = "INSERT INTO Elecciones(nombreEmpresa, fechaConstitucion) VALUES(?, ?)";

        try (Connection connection = databaseManager.connect();
             PreparedStatement pstmt = connection.prepareStatement(sqlElecciones)) {
            pstmt.setString(1, DirectorioManager.sanitizarNombreEmpresa(nuevoPreaviso.getNombreEmpresa()));
            pstmt.setString(2, nuevoPreaviso.getFechaConstitucion());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e, "error.insertar.eleccion");
        }
    }

    /**
     * Actualiza una elección existente en la base de datos.
     *
     * @param fechas El objeto Modelo_5_1 con las fechas.
     * @param numeroPreaviso El objeto Modelo_5_2_Proceso con los datos del escrutinio.
     * @param nombreEmpresaCompleto El nombre completo de la empresa.
     * @throws SQLException Sí ocurre un error al actualizar la elección en la base de datos.
     */
    public void updateEleccion(@NotNull Modelo_5_1 fechas, @NotNull Modelo_5_2_Proceso numeroPreaviso, @NotNull String nombreEmpresaCompleto) throws SQLException {
        String sqlElecciones = "UPDATE Elecciones SET fechaEscrutinio = ?, numeroPreaviso = ? WHERE nombreEmpresa = ?";

        try (Connection connection = databaseManager.connect();
             PreparedStatement pstmt = connection.prepareStatement(sqlElecciones)) {
            pstmt.setString(1, fechas.getFechaEscrutinio());
            pstmt.setString(2, numeroPreaviso.getPreaviso());
            pstmt.setString(3, nombreEmpresaCompleto);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e, "error.actualizar.eleccion");
        }
    }

    /**
     * Maneja las excepciones SQL mostrando el mensaje correspondiente y registrando el error.
     *
     * @param e La excepción SQL.
     * @param mensajeClave La clave del mensaje de error en el MessageManager.
     */
    private void handleSQLException(@NotNull SQLException e, @NotNull String mensajeClave) {
        view.mostrarMensaje(String.format(MessageManager.getMessage(mensajeClave), e.getMessage()), false);
        Constantes.LOGGER.log(Level.SEVERE, MessageManager.getMessage(mensajeClave), e);
    }
}
