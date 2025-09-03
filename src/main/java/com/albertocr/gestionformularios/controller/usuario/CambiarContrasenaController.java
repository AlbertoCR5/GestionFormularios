package com.albertocr.gestionformularios.controller.usuario;

import com.albertocr.gestionformularios.model.Usuario;
import com.albertocr.gestionformularios.model.UsuarioDAO;
import com.albertocr.gestionformularios.util.AlertManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controlador para la ventana de cambio de contraseña.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.2
 */
public class CambiarContrasenaController {

    private static final Logger logger = LoggerFactory.getLogger(CambiarContrasenaController.class);
    private static final int MIN_PASSWORD_LENGTH = 8;

    @FXML private PasswordField pfNuevaContrasena;
    @FXML private PasswordField pfConfirmarContrasena;

    private final Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final Runnable onSuccessCallback;

    /**
     * Constructor para inyectar las dependencias y el callback.
     *
     * @param usuario           El usuario que va a cambiar la contraseña.
     * @param usuarioDAO        El DAO para interactuar con la base de datos.
     * @param onSuccessCallback La acción a ejecutar si el cambio es exitoso.
     */
    public CambiarContrasenaController(Usuario usuario, UsuarioDAO usuarioDAO, Runnable onSuccessCallback) {
        this.usuario = usuario;
        this.usuarioDAO = usuarioDAO;
        this.onSuccessCallback = onSuccessCallback;
    }

    @FXML
    private void handleCambiarContrasena() {
        String nueva = pfNuevaContrasena.getText();
        String confirmacion = pfConfirmarContrasena.getText();

        if (!validarEntrada(nueva, confirmacion)) {
            return;
        }

    // No pre-hash here: UsuarioDAO.actualizarContrasena espera la contraseña en texto plano
    if (usuarioDAO.actualizarContrasena(usuario.getNombreUsuario(), nueva)) {
            logger.info("Contraseña actualizada con éxito para el usuario '{}'.", usuario.getNombreUsuario());
            AlertManager.mostrarAlertaInformacion("Éxito", "Contraseña actualizada. Ahora accederá a la aplicación.");
            onSuccessCallback.run(); // Ejecutar la navegación
        } else {
            AlertManager.mostrarAlertaError("Error de Base de Datos", "No se pudo actualizar la contraseña.");
        }
    }

    private boolean validarEntrada(String nueva, String confirmacion) {
        if (nueva.isBlank() || confirmacion.isBlank()) {
            AlertManager.mostrarAlertaAdvertencia("Campos Vacíos", "Debe rellenar ambos campos.");
            return false;
        }

        if (!nueva.equals(confirmacion)) {
            AlertManager.mostrarAlertaError("Contraseñas no coinciden", "Las contraseñas introducidas no son iguales.");
            return false;
        }

        if (nueva.length() < MIN_PASSWORD_LENGTH) {
            AlertManager.mostrarAlertaError("Contraseña Débil", "La contraseña debe tener al menos " + MIN_PASSWORD_LENGTH + " caracteres.");
            return false;
        }

        return true;
    }
}
