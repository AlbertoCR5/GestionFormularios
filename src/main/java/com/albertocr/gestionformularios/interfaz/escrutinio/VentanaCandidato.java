package com.albertocr.gestionformularios.interfaz.escrutinio;

import com.albertocr.gestionformularios.controller.candidato.CandidatoController;
import com.albertocr.gestionformularios.model.CandidatosDAO;
import com.albertocr.gestionformularios.service.CandidatoService;
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
 * Ventana para la gestión de candidatos.
 * Carga la interfaz de usuario desde el archivo 'candidato-view.fxml'.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.3
 */
public class VentanaCandidato extends Stage {

    private static final Logger logger = LoggerFactory.getLogger(VentanaCandidato.class);
    private static final String FXML_PATH = "/com/albertocr/gestionformularios/interfaz/escrutinio/candidato-view.fxml";

    /**
     * Constructor que carga y configura la ventana de gestión de candidatos.
     */
    public VentanaCandidato() {
        setTitle("Gestión de Candidatos");
        try {
            URL fxmlLocation = getClass().getResource(FXML_PATH);
            Objects.requireNonNull(fxmlLocation, "No se pudo encontrar el FXML: " + FXML_PATH);

            FXMLLoader loader = new FXMLLoader(fxmlLocation);

            // Inyección de dependencias: se proporciona el servicio al controlador
            loader.setControllerFactory(controllerClass -> {
                if (controllerClass == CandidatoController.class) {
                    CandidatosDAO candidatosDAO = new CandidatosDAO();
                    CandidatoService candidatoService = new CandidatoService(candidatosDAO);
                    return new CandidatoController(candidatoService);
                }
                try {
                    return controllerClass.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("No se pudo crear el controlador: " + controllerClass.getName(), e);
                }
            });

            VBox root = loader.load();

            Scene scene = new Scene(root, 600, 500);
            setScene(scene);
            logger.info("Ventana de Gestión de Candidatos cargada correctamente.");

        } catch (IOException e) {
            logger.error("Error fatal al cargar la vista de Gestión de Candidatos desde FXML.", e);
        } catch (RuntimeException e) {
            logger.error("Error fatal durante la inicialización del controlador.", e);
        }
    }
}
