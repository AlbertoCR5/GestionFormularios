package com.albertocr.gestionformularios.interfaz.usuario;

import com.albertocr.gestionformularios.controller.usuario.CambiarContrasenaController;
import com.albertocr.gestionformularios.interfaz.VentanaPrincipal;
import com.albertocr.gestionformularios.model.Usuario;
import com.albertocr.gestionformularios.model.UsuarioDAO;
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
 * Ventana modal para forzar el cambio de contraseña.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.1
 */
public class VentanaCambiarContrasena extends Stage {

    private static final Logger logger = LoggerFactory.getLogger(VentanaCambiarContrasena.class);
    private static final String FXML_PATH = "/com/albertocr/gestionformularios/interfaz/usuario/cambiar-contrasena-view.fxml";

    public VentanaCambiarContrasena(Usuario usuario) {
        setTitle("Cambiar Contraseña");
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);

        try {
            URL fxmlLocation = getClass().getResource(FXML_PATH);
            Objects.requireNonNull(fxmlLocation, "No se pudo encontrar el FXML: " + FXML_PATH);

            FXMLLoader loader = new FXMLLoader(fxmlLocation);

            // Definir la acción de navegación que se ejecutará si el cambio es exitoso
            Runnable onPasswordChanged = () -> {
                this.close();
                new VentanaPrincipal().show();
            };

            // Inyectar dependencias en el controlador
            loader.setControllerFactory(controllerClass -> {
                if (controllerClass == CambiarContrasenaController.class) {
                    return new CambiarContrasenaController(usuario, new UsuarioDAO(), onPasswordChanged);
                }
                // Manejo por defecto para otros posibles controladores
                try {
                    return controllerClass.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("No se pudo crear el controlador: " + controllerClass.getName(), e);
                }
            });

            VBox root = loader.load();
            Scene scene = new Scene(root);
            setScene(scene);
            logger.info("Ventana de cambio de contraseña cargada para el usuario '{}'.", usuario.getNombreUsuario());

        } catch (IOException e) {
            logger.error("Error fatal al cargar la vista de cambio de contraseña.", e);
        } catch (RuntimeException e) {
            logger.error("Error fatal durante la inicialización del controlador.", e);
        }
    }
}
