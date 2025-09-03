package com.albertocr.gestionformularios.interfaz;

import com.albertocr.gestionformularios.controller.usuario.UsuarioController;
import com.albertocr.gestionformularios.model.UsuarioDAO;
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
 * Ventana para la gestión de usuarios (crear, actualizar, eliminar).
 * Carga la interfaz de usuario desde el archivo 'usuario-view.fxml'.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.2
 */
public class VentanaUsuario extends Stage {

    private static final Logger logger = LoggerFactory.getLogger(VentanaUsuario.class);
    private static final String FXML_PATH = "/com/albertocr/gestionformularios/interfaz/usuario/usuario-view.fxml";

    /**
     * Constructor que carga y configura la ventana de gestión de usuarios.
     */
    public VentanaUsuario() {
        setTitle("Gestión de Usuarios");
        try {
            URL fxmlLocation = getClass().getResource(FXML_PATH);
            Objects.requireNonNull(fxmlLocation, "No se pudo encontrar el FXML: " + FXML_PATH);

            FXMLLoader loader = new FXMLLoader(fxmlLocation);

            // Inyección de dependencias: se proporciona el DAO al controlador
            loader.setControllerFactory(controllerClass -> {
                if (controllerClass == UsuarioController.class) {
                    return new UsuarioController(new UsuarioDAO());
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
            logger.info("Ventana de Gestión de Usuarios cargada correctamente.");
        } catch (IOException e) {
            logger.error("Error fatal al cargar la vista de Gestión de Usuarios desde FXML.", e);
            // Considerar mostrar una alerta de error crítico aquí.
        }
    }
}
