package com.albertocr.gestionformularios.interfaz.admin;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * Ventana para la herramienta de inspección de campos de formularios PDF.
 * Carga la interfaz de usuario desde 'pdf-inspector-view.fxml'.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.0
 */
public class VentanaPdfInspector extends Stage {

    private static final Logger logger = LoggerFactory.getLogger(VentanaPdfInspector.class);
    private static final String FXML_PATH = "/com/albertocr/gestionformularios/interfaz/admin/pdf-inspector-view.fxml";

    /**
     * Constructor que carga y configura la ventana del inspector de PDF.
     */
    public VentanaPdfInspector() {
        setTitle("Inspector de Formularios PDF");
        try {
            URL fxmlLocation = getClass().getResource(FXML_PATH);
            Objects.requireNonNull(fxmlLocation, "No se pudo encontrar el FXML: " + FXML_PATH);

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            VBox root = loader.load();

            Scene scene = new Scene(root);
            setScene(scene);
            logger.info("Ventana de Inspector de PDF cargada correctamente.");
        } catch (IOException e) {
            logger.error("Error fatal al cargar la vista de Inspector de PDF desde FXML.", e);
        }
    }
}
