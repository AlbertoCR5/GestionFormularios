package com.albertocr.gestionformularios.main;

import com.albertocr.gestionformularios.interfaz.VentanaLogin;
import com.albertocr.gestionformularios.model.DatabaseManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * Clase principal que inicia la aplicación JavaFX.
 * <p>
 * Se encarga de inicializar la base de datos (incluyendo la creación de un
 * usuario por defecto si es necesario) y mostrar la ventana de inicio de sesión.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.3
 */
public class PrincipalApplication extends Application {

    private static final Logger logger = LoggerFactory.getLogger(PrincipalApplication.class);

    /**
     * Punto de entrada para la aplicación JavaFX.
     *
     * @param stage El escenario principal proporcionado por la plataforma JavaFX.
     */
    @Override
    public void start(Stage stage) {
        try {
            // Inicializa la base de datos, sus tablas y el usuario por defecto en una sola operación
            DatabaseManager.inicializarBaseDatos();
            logger.info("Base de datos inicializada correctamente.");
        } catch (SQLException e) {
            logger.error("Error fatal al inicializar la base de datos. La aplicación no puede continuar.", e);
            // En una aplicación real, aquí se podría mostrar una alerta crítica y cerrar.
            System.exit(1);
        }

        // Mostrar la ventana de login
        VentanaLogin ventanaLogin = new VentanaLogin();
        ventanaLogin.show();
    }

    /**
     * Método principal que lanza la aplicación JavaFX.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
