package com.example.proyecto.modal;

import com.example.proyecto.interfaz.PrincipalView;
import com.example.proyecto.util.DirectorioManager;
import com.example.proyecto.util.MessageManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * La clase EleccionesDAO se encarga de las operaciones de base de datos para la tabla Elecciones.
 *
 * @author Alberto Castro <AlbertoCastrovas@gmail.com>
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
    public EleccionesDAO(PrincipalView view, DatabaseManager databaseManager) {
        this.view = view;
        this.databaseManager = databaseManager;
    }

    /**
     * Crea la tabla Elecciones en la base de datos si no existe.
     *
     * @throws SQLException Si ocurre un error al crear la tabla en la base de datos.
     */
    public void createTableElecciones() throws SQLException {
        String sqlElecciones = "CREATE TABLE IF NOT EXISTS Elecciones ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " nombreEmpresa TEXT,"
                + " numeroPreaviso TEXT,"
                + " fechaConstitucion TEXT,"
                + " fechaEscrutinio TEXT,"
                + " FOREIGN KEY (nombreEmpresa) REFERENCES Empresa(nombre)"
                + ");";

        try (Connection connection = databaseManager.connect();
             PreparedStatement pstmt = connection.prepareStatement(sqlElecciones)) {
            pstmt.execute();
        } catch (SQLException e) {
            view.mostrarMensaje(String.format(MessageManager.getMessage("error.crear.tabla.elecciones"), e.getMessage()), false);
        }
    }

    public void insertFechaConstitucion(Preaviso nuevoPreaviso) {
        String sqlElecciones = "INSERT INTO Elecciones(nombreEmpresa, fechaConstitucion) VALUES(?, ?)";

        try (Connection connection = databaseManager.connect();
             PreparedStatement pstmt = connection.prepareStatement(sqlElecciones)) {
            pstmt.setString(1, DirectorioManager.sanitizarNombreEmpresa(nuevoPreaviso.getNombreEmpresa()));
            pstmt.setString(2, nuevoPreaviso.getFechaConstitucion());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            view.mostrarMensaje(String.format(MessageManager.getMessage("error.insertar.eleccion"), e.getMessage()), false);
        }
    }

    /**
     * Inserta una nueva elección en la base de datos.
     *
     * @param fechas El objeto Modelo_5_1 con las fechas.
     * @param numeroPreaviso El objeto Modelo_5_2_Proceso con los datos del escrutinio.
     * @param nombreEmpresaCompleto El nombre completo de la empresa.
     * @throws SQLException Si ocurre un error al insertar la elección en la base de datos.
     */
    public void updateEleccion(Modelo_5_1 fechas, Modelo_5_2_Proceso numeroPreaviso, String nombreEmpresaCompleto) throws SQLException {
        String sqlElecciones = "UPDATE Elecciones SET fechaEscrutinio = ?, numeroPreaviso = ? WHERE nombreEmpresa = ?";

        try (Connection connection = databaseManager.connect();
             PreparedStatement pstmt = connection.prepareStatement(sqlElecciones)) {
            pstmt.setString(1, fechas.getFechaEscrutinio());
            pstmt.setString(2, numeroPreaviso.getPreaviso());
            pstmt.setString(3, nombreEmpresaCompleto);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            view.mostrarMensaje(String.format(MessageManager.getMessage("error.actualizar.eleccion"), e.getMessage()), false);
        }
    }
}
