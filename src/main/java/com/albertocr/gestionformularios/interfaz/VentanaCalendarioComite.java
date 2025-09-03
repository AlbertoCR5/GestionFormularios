package com.albertocr.gestionformularios.interfaz;

import com.albertocr.gestionformularios.controller.calendario.CalendarioComiteController;
import com.albertocr.gestionformularios.model.EleccionesDAO;
import com.albertocr.gestionformularios.model.EmpresaDAO;
import com.albertocr.gestionformularios.service.CalendarioService;
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
 * Ventana para la generación del calendario de elecciones del comité.
 * Carga la interfaz de usuario desde el archivo 'calendario-comite-view.fxml'.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.2
 */
public class VentanaCalendarioComite extends Stage {

    private static final Logger logger = LoggerFactory.getLogger(VentanaCalendarioComite.class);
    private static final String FXML_PATH = "/com/albertocr/gestionformularios/interfaz/calendario/calendario-comite-view.fxml";

    /**
     * Constructor que carga y configura la ventana del calendario.
     */
    public VentanaCalendarioComite() {
        setTitle("Generar Calendario de Comité");
        try {
            URL fxmlLocation = getClass().getResource(FXML_PATH);
            Objects.requireNonNull(fxmlLocation, "No se pudo encontrar el FXML: " + FXML_PATH);

            FXMLLoader loader = new FXMLLoader(fxmlLocation);

            // Inyección de dependencias: se proporciona el servicio al controlador
            loader.setControllerFactory(controllerClass -> {
                if (controllerClass == CalendarioComiteController.class) {
                    EmpresaDAO empresaDAO = new EmpresaDAO();
                    EleccionesDAO eleccionesDAO = new EleccionesDAO();
                    CalendarioService calendarioService = new CalendarioService(empresaDAO, eleccionesDAO);
                    return new CalendarioComiteController(calendarioService);
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
            logger.info("Ventana de Calendario de Comité cargada correctamente.");
        } catch (IOException e) {
            logger.error("Error fatal al cargar la vista de Calendario de Comité desde FXML.", e);
        } catch (RuntimeException e) {
            logger.error("Error fatal durante la inicialización del controlador.", e);
        }
    }
}
