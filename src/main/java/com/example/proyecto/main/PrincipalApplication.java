package com.example.proyecto.main;

import com.example.proyecto.controller.LoginManager;
import com.example.proyecto.controller.PrincipalController;
import com.example.proyecto.interfaz.PrincipalView;
import com.example.proyecto.modal.DatabaseManager;
import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.MessageManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.logging.Level;

/**
 * Clase principal para gestionar el menú de opciones y ejecutar las funcionalidades del programa.
 * Utiliza PDFBox para gestionar formularios PDF y JavaFX para la interfaz gráfica.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class PrincipalApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Método start que se ejecuta al iniciar la aplicación JavaFX.
     * Configura y muestra la ventana principal de la aplicación.
     *
     * @param primaryStage El escenario principal de la aplicación.
     * @throws IOException Si ocurre un error durante el inicio de la aplicación.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            iniciarAplicacion();
        } catch (IOException e) {
            Constantes.LOGGER.log(Level.SEVERE, MessageManager.getMessage("error.inicio.app"), e);
            throw e;
        }
    }

    /**
     * Método que inicializa la aplicación configurando la vista principal, el controlador y la base de datos.
     *
     * @throws IOException Si ocurre un error durante la inicialización.
     */
    private void iniciarAplicacion() throws IOException {
        PrincipalView view = inicializarVista();
        try {
            DatabaseManager dbManager = inicializarBaseDeDatos(view);
            configurarAplicacion(view, dbManager);
        } catch (SQLException e) {
            manejarErrorSQLException(view, e);
        } catch (RuntimeException e) {
            // Manejo específico de RuntimeException
            Constantes.LOGGER.log(Level.SEVERE, MessageManager.getMessage("error.runtime.app"), e);
        }
    }

    /**
     * Inicializa la vista principal de la aplicación.
     *
     * @return La vista principal inicializada.
     */
    private PrincipalView inicializarVista() {
        return new PrincipalView(null);
    }

    /**
     * Inicializa el gestor de base de datos.
     *
     * @param view La vista principal de la aplicación.
     * @return El gestor de base de datos inicializado.
     * @throws SQLException Si ocurre un error durante la inicialización de la base de datos.
     */
    private DatabaseManager inicializarBaseDeDatos(PrincipalView view) throws SQLException {
        return new DatabaseManager(view);
    }

    /**
     * Configura la aplicación estableciendo la vista principal, el controlador y el gestor de inicio de sesión.
     *
     * @param view La vista principal.
     * @param dbManager El gestor de base de datos.
     * @throws SQLException Si ocurre un error durante la configuración de la base de datos.
     * @throws IOException Sí ocurre un error durante la configuración de la aplicación.
     */
    private void configurarAplicacion(PrincipalView view, DatabaseManager dbManager) throws SQLException, IOException {
        LoginManager loginManager = new LoginManager(dbManager);
        PrincipalController controller = new PrincipalController(loginManager);
        configurarVista(view, controller);
        dbManager.createNewDatabase();
        view.mostrarVentanaLogin();
    }

    /**
     * Configura la vista principal y el controlador.
     *
     * @param view La vista principal.
     * @param controller El controlador principal.
     */
    private void configurarVista(PrincipalView view, PrincipalController controller) {
        controller.setView(view);
        view.setController(controller);
    }

    /**
     * Maneja los errores de SQL que ocurren durante la inicialización de la base de datos.
     *
     * @param view La vista principal de la aplicación.
     * @param e La excepción de SQL.
     */
    private void manejarErrorSQLException(PrincipalView view, SQLException e) {
        view.mostrarMensaje(MessageFormat.format(MessageManager.getMessage("error.sql.mensaje"), e.getMessage()), false);
        Constantes.LOGGER.log(Level.SEVERE, MessageManager.getMessage("error.crear.bd"), e);
    }
}
