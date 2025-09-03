package com.albertocr.gestionformularios.interfaz;

import com.albertocr.gestionformularios.controller.PrincipalController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

/**
 * Representa la vista principal de la aplicación.
 * Carga la interfaz de usuario desde un archivo FXML y la muestra en un escenario.
 *
 * @autor Alberto Castro <AlbertoCastroCR>
 * @version 1.0
 */
public class PrincipalView extends Stage {

    private static final Logger logger = LoggerFactory.getLogger(PrincipalView.class);

    /**
     * Constructor de la clase PrincipalView.
     * Carga el archivo FXML, establece el controlador y configura la escena.
     */
    public PrincipalView() {
        try {
            // Cargar el archivo FXML de la vista principal
            URL fxmlLocation = getClass().getResource("view.fxml");
            if (fxmlLocation == null) {
                throw new IOException("No se pudo encontrar el archivo FXML: view.fxml");
            }
            FXMLLoader loader = new FXMLLoader(fxmlLocation);

            // Establecer el controlador para la vista
            loader.setController(new PrincipalController());

            // Cargar el panel raíz desde el FXML
            BorderPane root = loader.load();

            // Configurar la escena
            Scene scene = new Scene(root);
            setScene(scene);
            setTitle("Gestión de Elecciones Sindicales");
            logger.info("Vista principal cargada correctamente.");

        } catch (IOException e) {
            logger.error("Error al cargar la vista principal desde FXML", e);
            // Opcionalmente, mostrar un diálogo de error al usuario
        }
    }

    /**
     * Muestra un mensaje de información o error.
     *
     * @param mensaje El mensaje a mostrar.
     * @param isError True si es un mensaje de error, false si es de información.
     */
    public void mostrarMensaje(String mensaje, boolean isError) {
        Alert.AlertType type = isError ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION;
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}