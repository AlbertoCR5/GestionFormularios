package com.albertocr.gestionformularios.controller.usuario;

import com.albertocr.gestionformularios.model.Usuario;
import com.albertocr.gestionformularios.model.UsuarioDAO;
import com.albertocr.gestionformularios.util.AlertManager;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Controlador para la ventana de gestión de usuarios (usuario-view.fxml).
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.4
 */
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @FXML private TextField tfNombreUsuario;
    @FXML private PasswordField pfContrasena;
    @FXML private ComboBox<Usuario.Rol> rolComboBox;

    private final UsuarioDAO usuarioDAO;

    /**
     * Constructor del controlador.
     * @param usuarioDAO El DAO de usuarios para realizar las operaciones.
     */
    public UsuarioController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @FXML
    public void initialize() {
        rolComboBox.getItems().setAll(Usuario.Rol.values());
        rolComboBox.setValue(Usuario.Rol.STANDARD);
    }

    @FXML
    private void handleCrearUsuario() {
        String nombreUsuario = tfNombreUsuario.getText();
        String contrasenaPlana = pfContrasena.getText(); // Contraseña en texto plano
        Usuario.Rol rolSeleccionado = rolComboBox.getValue();

        if (nombreUsuario.isBlank() || contrasenaPlana.isBlank() || rolSeleccionado == null) {
            AlertManager.mostrarAlertaAdvertencia("Campos Vacíos", "El nombre de usuario, la contraseña y el rol son obligatorios.");
            return;
        }

        // La validación de fortaleza de la contraseña se haría aquí, sobre la contraseña en texto plano

        // Se pasa la contraseña en texto plano al DAO, que se encargará de hashearla.
        Usuario nuevoUsuario = new Usuario(nombreUsuario, contrasenaPlana, rolSeleccionado, false);

        if (usuarioDAO.crearUsuario(nuevoUsuario)) {
            AlertManager.mostrarAlertaInformacion("Éxito", "Usuario creado correctamente.");
            logger.info("Usuario '{}' con rol '{}' creado con éxito.", nombreUsuario, rolSeleccionado);
            limpiarCampos();
        } else {
            AlertManager.mostrarAlertaError("Error", "No se pudo crear el usuario. Es posible que el nombre de usuario ya exista.");
            logger.error("Fallo al crear el usuario '{}'.", nombreUsuario);
        }
    }

    @FXML
    private void handleActualizarUsuario() {
        String nombreUsuario = tfNombreUsuario.getText();
        String nuevaContrasenaPlana = pfContrasena.getText(); // Contraseña en texto plano

        if (nombreUsuario.isBlank() || nuevaContrasenaPlana.isBlank()) {
            AlertManager.mostrarAlertaAdvertencia("Campos Vacíos", "El nombre de usuario y la nueva contraseña son obligatorios.");
            return;
        }

        // Se pasa la contraseña en texto plano al DAO, que se encargará de hashearla.
        if (usuarioDAO.actualizarContrasena(nombreUsuario, nuevaContrasenaPlana)) {
            AlertManager.mostrarAlertaInformacion("Éxito", "Contraseña actualizada correctamente.");
            logger.info("Contraseña para el usuario '{}' actualizada.", nombreUsuario);
            limpiarCampos();
        } else {
            AlertManager.mostrarAlertaError("Error", "No se pudo actualizar la contraseña. Verifique que el usuario exista.");
            logger.error("Fallo al actualizar la contraseña para el usuario '{}'.", nombreUsuario);
        }
    }

    @FXML
    private void handleEliminarUsuario() {
        String nombreUsuario = tfNombreUsuario.getText();
        if (nombreUsuario.isBlank()) {
            AlertManager.mostrarAlertaAdvertencia("Campo Vacío", "Debe especificar el nombre de usuario a eliminar.");
            return;
        }

        Optional<ButtonType> resultado = AlertManager.mostrarAlertaConfirmacion(
                "Confirmar Eliminación",
                "¿Está seguro de que desea eliminar el usuario especificado?",
                "Esta acción no se puede deshacer."
        );

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            if (usuarioDAO.eliminarUsuario(nombreUsuario)) {
                AlertManager.mostrarAlertaInformacion("Éxito", "Usuario eliminado correctamente.");
                logger.info("Usuario '{}' eliminado con éxito.", nombreUsuario);
                limpiarCampos();
            } else {
                AlertManager.mostrarAlertaError("Error", "No se pudo eliminar el usuario. Verifique que el usuario exista.");
                logger.error("Fallo al eliminar el usuario '{}'.", nombreUsuario);
            }
        } else {
            logger.info("Eliminación del usuario '{}' cancelada.", nombreUsuario);
        }
    }

    private void limpiarCampos() {
        tfNombreUsuario.clear();
        pfContrasena.clear();
        rolComboBox.setValue(Usuario.Rol.STANDARD);
    }
}
