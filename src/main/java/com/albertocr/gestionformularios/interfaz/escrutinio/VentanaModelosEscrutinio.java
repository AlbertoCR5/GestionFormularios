package com.albertocr.gestionformularios.interfaz.escrutinio;

import com.albertocr.gestionformularios.controller.escrutinio.EscrutinioController;
import com.albertocr.gestionformularios.model.CandidatosDAO;
import com.albertocr.gestionformularios.model.EleccionesDAO;
import com.albertocr.gestionformularios.model.EmpresaDAO;
import com.albertocr.gestionformularios.service.EscrutinioService;
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
 * Ventana para la gestión de los modelos de escrutinio.
 * Carga la interfaz de usuario desde el archivo 'escrutinio-view.fxml'.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.2
 */
public class VentanaModelosEscrutinio extends Stage {

    private static final Logger logger = LoggerFactory.getLogger(VentanaModelosEscrutinio.class);
    private static final String FXML_PATH = "/com/albertocr/gestionformularios/interfaz/escrutinio/escrutinio-view.fxml";

    /**
     * Constructor que carga y configura la ventana de escrutinio.
     */
    public VentanaModelosEscrutinio() {
        setTitle("Gestión de Escrutinio");
        try {
            URL fxmlLocation = getClass().getResource(FXML_PATH);
            Objects.requireNonNull(fxmlLocation, "No se pudo encontrar el FXML: " + FXML_PATH);

            FXMLLoader loader = new FXMLLoader(fxmlLocation);

            // Inyección de dependencias: se proporciona el servicio al controlador
            loader.setControllerFactory(controllerClass -> {
                if (controllerClass == EscrutinioController.class) {
                    EmpresaDAO empresaDAO = new EmpresaDAO();
                    EleccionesDAO eleccionesDAO = new EleccionesDAO();
                    CandidatosDAO candidatosDAO = new CandidatosDAO();
                    EscrutinioService escrutinioService = new EscrutinioService(empresaDAO, eleccionesDAO, candidatosDAO);
                    return new EscrutinioController(escrutinioService);
                }
                try {
                    return controllerClass.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("No se pudo crear el controlador: " + controllerClass.getName(), e);
                }
            });

            VBox root = loader.load();

            Scene scene = new Scene(root);
            setScene(scene);
            logger.info("Ventana de escrutinio cargada correctamente.");
        } catch (IOException e) {
            logger.error("Error fatal al cargar la vista de escrutinio desde FXML.", e);
        } catch (RuntimeException e) {
            logger.error("Error fatal durante la inicialización del controlador.", e);
        }
    }
}
