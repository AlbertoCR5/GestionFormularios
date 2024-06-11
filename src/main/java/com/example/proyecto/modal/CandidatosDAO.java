package com.example.proyecto.modal;

import com.example.proyecto.interfaz.PrincipalView;
import com.example.proyecto.util.MessageManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * La clase CandidatosDAO se encarga de gestionar las operaciones de base de datos relacionadas con la tabla Candidatos.
 * Proporciona métodos para crear la tabla y para insertar nuevos candidatos.
 * Utiliza JDBC para las operaciones de base de datos.
 *
 * @author Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class CandidatosDAO {

    private final PrincipalView view;
    private final DatabaseManager databaseManager;

    /**
     * Constructor de la clase CandidatosDAO.
     *
     * @param view La vista principal de la aplicación.
     * @param databaseManager El gestor de la base de datos.
     */
    public CandidatosDAO(PrincipalView view, DatabaseManager databaseManager) {
        this.view = view;
        this.databaseManager = databaseManager;
    }

    /**
     * Crea la tabla Candidatos en la base de datos si no existe.
     *
     * @throws SQLException Si ocurre un error al crear la tabla en la base de datos.
     */
    public void createTableCandidatos() throws SQLException {
        String sqlCandidatos = "CREATE TABLE IF NOT EXISTS Candidatos ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " nombreEmpresa TEXT,"
                + " nombreApellidos TEXT,"
                + " sindicato TEXT,"
                + " FOREIGN KEY (nombreEmpresa) REFERENCES Empresa(nombre)"
                + ");";

        try (Connection connection = databaseManager.connect();
             PreparedStatement pstmt = connection.prepareStatement(sqlCandidatos)) {
            pstmt.execute();
        } catch (SQLException e) {
            view.mostrarMensaje(MessageManager.getMessage("error.crear.tabla.candidatos").concat(e.getMessage()), false);
        }
    }

    /**
     * Inserta un nuevo candidato en la base de datos.
     *
     * @param candidato El candidato a insertar.
     * @param nombreEmpresaCompleto El nombre completo de la empresa.
     * @throws SQLException Si ocurre un error al insertar el candidato en la base de datos.
     */
    public void insertCandidato(Candidato candidato, String nombreEmpresaCompleto) throws SQLException {
        String sqlCandidatos = "INSERT INTO Candidatos(nombreEmpresa, nombreApellidos, sindicato) VALUES(?, ?, ?)";

        try (Connection connection = databaseManager.connect();
             PreparedStatement pstmt = connection.prepareStatement(sqlCandidatos)) {
            pstmt.setString(1, nombreEmpresaCompleto);
            pstmt.setString(2, candidato.getNombreApellidos());
            pstmt.setString(3, candidato.getSindicato());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            view.mostrarMensaje(MessageManager.getMessage("error.insertar.candidato").concat(e.getMessage()), false);
        }
    }
}
