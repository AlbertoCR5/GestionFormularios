package com.albertocr.gestionformularios.interfaz.preaviso;

import com.albertocr.gestionformularios.controller.preaviso.PreavisoController;
import com.albertocr.gestionformularios.model.EleccionesDAO;
import com.albertocr.gestionformularios.model.EmpresaDAO;
import com.albertocr.gestionformularios.service.PreavisoService;
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
 * Ventana para la creación y gestión de preavisos de elecciones.
 * Carga la interfaz de usuario desde el archivo 'preaviso-view.fxml'.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.3
 */
public class VentanaPreaviso extends Stage {

    private static final Logger logger = LoggerFactory.getLogger(VentanaPreaviso.class);
    private static final String FXML_PATH = "/com/albertocr/gestionformularios/interfaz/preaviso/preaviso-view.fxml";

    /**
     * Constructor que carga y configura la ventana de preaviso.
     */
    public VentanaPreaviso() {
        setTitle("Generar Preaviso");
        try {
            URL fxmlLocation = getClass().getResource(FXML_PATH);
            Objects.requireNonNull(fxmlLocation, "No se pudo encontrar el FXML: " + FXML_PATH);

            FXMLLoader loader = new FXMLLoader(fxmlLocation);

            // Inyección de dependencias: se proporciona el servicio al controlador
            loader.setControllerFactory(controllerClass -> {
                if (controllerClass == PreavisoController.class) {
                    // Crear las dependencias necesarias para el servicio
                    EmpresaDAO empresaDAO = new EmpresaDAO();
                    EleccionesDAO eleccionesDAO = new EleccionesDAO();
                    PreavisoService preavisoService = new PreavisoService(empresaDAO, eleccionesDAO);
                    return new PreavisoController(preavisoService);
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
            logger.info("Ventana de preaviso cargada correctamente.");
        } catch (IOException e) {
            logger.error("Error fatal al cargar la vista de preaviso desde FXML.", e);
        } catch (RuntimeException e) {
            logger.error("Error fatal durante la inicialización del controlador.", e);
        }
    }
}
