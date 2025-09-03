package com.albertocr.gestionformularios.interfaz;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * Representa la ventana principal de la aplicación.
 * <p>
 * Carga la interfaz de usuario desde un archivo FXML, la asocia con su controlador
 * y la muestra en un escenario (Stage).
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.1
 */
public class VentanaPrincipal extends Stage {

    private static final Logger logger = LoggerFactory.getLogger(VentanaPrincipal.class);
    private static final String FXML_PATH = "/com/albertocr/gestionformularios/interfaz/view.fxml";

    /**
     * Constructor de la clase VentanaPrincipal.
     * Carga el FXML, configura la escena y maneja posibles errores críticos en la carga.
     */
    public VentanaPrincipal() {
        setTitle("Gestión de Elecciones Sindicales");
        try {
            Scene scene = new Scene(cargarVistaPrincipal());
            setScene(scene);
            logger.info("Ventana principal cargada y configurada correctamente.");
        } catch (IOException e) {
            logger.error("Error fatal al cargar la ventana principal desde FXML. La aplicación se cerrará.", e);
            mostrarAlertaCritica(e);
        }
    }

    /**
     * Carga el panel raíz (BorderPane) desde el archivo FXML.
     * <p>
     * El controlador se especifica dentro del archivo FXML usando el atributo 'fx:controller',
     * permitiendo que el FXMLLoader lo instancie y lo enlace automáticamente.
     *
     * @return El panel raíz de la vista principal.
     * @throws IOException si el archivo FXML no se encuentra o no se puede cargar.
     */
    private BorderPane cargarVistaPrincipal() throws IOException {
        URL fxmlLocation = getClass().getResource(FXML_PATH);
        Objects.requireNonNull(fxmlLocation, "No se pudo encontrar el archivo FXML: " + FXML_PATH);

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        return loader.load();
    }

    /**
     * Muestra una alerta de error crítico y cierra la aplicación.
     * Se invoca cuando un recurso esencial, como el FXML principal, no puede ser cargado.
     *
     * @param e La excepción que causó el error.
     */
    private void mostrarAlertaCritica(IOException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Crítico");
        alert.setHeaderText("No se pudo iniciar la aplicación.");
        alert.setContentText("Ha ocurrido un error irrecuperable al cargar la interfaz principal. " +
                "Por favor, contacte con el administrador.\n\nDetalles: " + e.getMessage());
        alert.showAndWait();
        System.exit(1); // Termina la aplicación
    }
}
