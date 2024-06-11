package com.example.proyecto.modal;

import com.example.proyecto.interfaz.PrincipalView;
import com.example.proyecto.util.DirectorioManager;
import com.example.proyecto.util.MessageManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * La clase EmpresaDAO se encarga de gestionar las operaciones de la base de datos relacionadas con la tabla Empresa.
 * Utiliza SQLite y JDBC para interactuar con la base de datos.
 *
 * @author Alberto Castro <AlbertoCastrovas@gmail.com>
 */
public class EmpresaDAO {

    private final PrincipalView view;
    private final DatabaseManager databaseManager;

    public EmpresaDAO(PrincipalView view, DatabaseManager databaseManager) {
        this.view = view;
        this.databaseManager = databaseManager;
    }

    /**
     * Crea la tabla Empresa en la base de datos si no existe.
     *
     * @throws SQLException Si ocurre un error al crear la tabla en la base de datos.
     */
    public void createTableEmpresa() throws SQLException {
        String sqlEmpresa = """
                CREATE TABLE IF NOT EXISTS Empresa (
                 id integer PRIMARY KEY,
                 nombre text NOT NULL,
                 cif text NOT NULL,
                 direccion text NOT NULL,
                 municipio text NOT NULL,
                 codigo_postal text NOT NULL,
                 provincia text NOT NULL,
                 cnae text,
                 convenio text,
                 numero_convenio text,
                 total_electores integer
                );""";

        try (Connection connection = databaseManager.connect();
             Statement stmt = connection.createStatement()) {
            stmt.execute(sqlEmpresa);
        } catch (SQLException e) {
            view.mostrarMensaje(String.format(MessageManager.getMessage("error.crear.tabla.empresa"), e.getMessage()), false);
        }
    }

    /**
     * Inserta una nueva empresa en la base de datos.
     *
     * @param datosEmpresa La empresa a insertar.
     * @throws SQLException Si ocurre un error al insertar la empresa en la base de datos.
     */
    public void insertEmpresa(Preaviso datosEmpresa) throws SQLException {
        String sqlEmpresa = "INSERT INTO Empresa(nombre, cif, direccion, municipio, provincia, codigo_postal) VALUES(?,?,?,?,?,?)";

        try (Connection conn = databaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sqlEmpresa)) {
            pstmt.setString(1, DirectorioManager.sanitizarNombreEmpresa(datosEmpresa.getNombreEmpresa()));
            pstmt.setString(2, datosEmpresa.getCIF());
            pstmt.setString(3, datosEmpresa.getDireccion());
            pstmt.setString(4, datosEmpresa.getMunicipio());
            pstmt.setString(5, datosEmpresa.getProvincia());
            pstmt.setString(6, datosEmpresa.getCodigoPostal());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            view.mostrarMensaje(String.format(MessageManager.getMessage("error.insertar.empresa"), e.getMessage()), false);
        }
    }

    /**
     * Actualiza una empresa existente en la base de datos con datos adicionales.
     *
     * @param totalTrabajadores El total de trabajadores.
     * @param otrosDatosEmpresa Otros datos de la empresa.
     * @param nombreEmpresa El nombre de la empresa a actualizar.
     * @throws SQLException Si ocurre un error al actualizar la empresa en la base de datos.
     */
    public void updateEmpresaOtros(Modelo_5_2_Proceso totalTrabajadores, Modelo_5_2_Conclusion otrosDatosEmpresa, String nombreEmpresa) throws SQLException {
        String sqlEmpresa = "UPDATE Empresa SET cnae = ?, convenio = ?, numero_convenio = ?, total_electores = ? WHERE nombre = ?";

        try (Connection conn = databaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sqlEmpresa)) {
            pstmt.setString(1, otrosDatosEmpresa.getActvEcono());
            pstmt.setString(2, otrosDatosEmpresa.getNombreConvenio());
            pstmt.setString(3, otrosDatosEmpresa.getNumeroConvenio());
            pstmt.setInt(4, totalTrabajadores.getTotalElectores());
            pstmt.setString(5, nombreEmpresa);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            view.mostrarMensaje(String.format(MessageManager.getMessage("error.actualizar.empresa"), e.getMessage()), false);
        }
    }
}
