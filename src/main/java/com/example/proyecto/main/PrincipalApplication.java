package com.example.proyecto.main;

import com.example.proyecto.controller.LoginManager;
import com.example.proyecto.controller.PrincipalController;
import com.example.proyecto.interfaz.PrincipalView;
import com.example.proyecto.modal.DatabaseManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

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
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            iniciarAplicacion();
        } catch (IOException e) {
            System.err.println(STR."Error al iniciar la aplicación: \{e.getMessage()}");
        }
    }

    /**
     * Método que inicializa la aplicación configurando la vista principal, el controlador y la base de datos.
     *
     * @throws IOException Si ocurre un error durante la inicialización.
     */
    private void iniciarAplicacion() throws IOException {
        PrincipalView view = null;
        try {
            view = new PrincipalView(null);
            DatabaseManager dbManager = new DatabaseManager(view);
            LoginManager loginManager = new LoginManager(dbManager);
            PrincipalController controller = new PrincipalController(loginManager);
            view = new PrincipalView(controller);

            // Crea la base de datos al inicio de la aplicación
            dbManager.createNewDatabase();
            // Establece la vista en el controlador
            controller.setView(view);
            // Muestra la ventana de login
            view.mostrarVentanaLogin();
        } catch (SQLException e) {
            view.mostrarMensaje(String.format("Error al mostrar la ventana de inicio de sesión: %s", e.getMessage()), false);
            System.err.println(STR."Error al crear la base de datos: \{e.getMessage()}");
        } catch (Exception e) {
            System.err.println(STR."Error al iniciar la aplicación: \{e.getMessage()}");
        }
    }
}
