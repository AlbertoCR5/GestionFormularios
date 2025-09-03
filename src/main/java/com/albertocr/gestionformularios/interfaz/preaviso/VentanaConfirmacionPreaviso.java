package com.albertocr.gestionformularios.interfaz.preaviso;

import com.albertocr.gestionformularios.controller.preaviso.ConfirmacionPreavisoController;
import com.albertocr.gestionformularios.model.Eleccion;
import com.albertocr.gestionformularios.model.Empresa;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * Ventana modal para mostrar y confirmar los datos del preaviso antes de guardarlos.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.1
 */
public class VentanaConfirmacionPreaviso extends Stage {

    private static final Logger logger = LoggerFactory.getLogger(VentanaConfirmacionPreaviso.class);
    private static final String FXML_PATH = "/com/albertocr/gestionformularios/interfaz/preaviso/confirmacion-preaviso-view.fxml";
    private boolean confirmado = false;

    /**
     * Constructor que carga y configura la ventana de confirmación.
     *
     * @param empresa La entidad Empresa con los datos a mostrar.
     * @param eleccion La entidad Eleccion con los datos a mostrar.
     */
    public VentanaConfirmacionPreaviso(Empresa empresa, Eleccion eleccion) {
        setTitle("Confirmar Datos del Preaviso");
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);

        try {
            URL fxmlLocation = getClass().getResource(FXML_PATH);
            Objects.requireNonNull(fxmlLocation, "No se pudo encontrar el FXML: " + FXML_PATH);

            FXMLLoader loader = new FXMLLoader(fxmlLocation);

            // Inyectar dependencias en el controlador
            loader.setControllerFactory(controllerClass -> {
                if (controllerClass == ConfirmacionPreavisoController.class) {
                    return new ConfirmacionPreavisoController(empresa, eleccion);
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
            logger.info("Ventana de confirmación de preaviso cargada.");

            // Obtener el controlador después de cargar la vista
            ConfirmacionPreavisoController controller = loader.getController();

            // Mostrar la ventana y esperar a que se cierre
            showAndWait();

            // Una vez cerrada, obtener el resultado del controlador
            this.confirmado = controller.isConfirmado();

        } catch (IOException e) {
            logger.error("Error fatal al cargar la vista de confirmación de preaviso.", e);
        } catch (RuntimeException e) {
            logger.error("Error fatal durante la inicialización del controlador.", e);
        }
    }

    /**
     * Comprueba si el usuario ha confirmado los datos.
     *
     * @return true si se pulsó el botón de confirmar, false en caso contrario.
     */
    public boolean isConfirmado() {
        return confirmado;
    }
}
