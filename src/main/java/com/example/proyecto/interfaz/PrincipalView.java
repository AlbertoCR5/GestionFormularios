package com.example.proyecto.interfaz;

import com.example.proyecto.controller.PrincipalController;
import com.example.proyecto.util.MessageManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import java.util.Optional;

/**
 * La clase `PrincipalView` gestiona la interfaz principal de la aplicación.
 * Se encarga de mostrar mensajes y de inicializar las vistas de login y principal.
 * Utiliza JavaFX para la interfaz gráfica.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 */
public class PrincipalView {

    private PrincipalController controller;
    private final Timeline timeline = new Timeline();

    /**
     * Constructor de la clase PrincipalView.
     *
     * @param controller El controlador principal de la aplicación.
     */
    public PrincipalView(PrincipalController controller) {
        this.controller = controller;
    }

    /**
     * Establece el controlador principal de la aplicación.
     *
     * @param controller El controlador principal.
     */
    public void setController(PrincipalController controller) {
        this.controller = controller;
    }

    /**
     * Muestra la ventana de login de la aplicación.
     */
    public void mostrarVentanaLogin() {
        VentanaLogin ventanaLogin = new VentanaLogin(controller);
        controller.setVentanaLoginActual(ventanaLogin);
        ventanaLogin.mostrarVentanaLogin();
    }

    /**
     * Muestra la ventana principal de la aplicación.
     */
    public void mostrarVentanaPrincipal() {
        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(controller);
        ventanaPrincipal.mostrarVentanaPrincipal();
    }

    /**
     * Muestra un mensaje de información o error en la interfaz.
     *
     * @param mensaje      El mensaje a mostrar.
     * @param esInformacion Indica si el mensaje es de información (true) o de error (false).
     */

    public void mostrarMensaje(String mensaje, boolean esInformacion) {
        Platform.runLater(() -> {
            Alert alert;
            if (esInformacion) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(MessageManager.getMessage("info.title"));
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(MessageManager.getMessage("error.title"));
            }
            alert.setHeaderText(null);
            alert.setContentText(mensaje);

            if (esInformacion) {
                // Detiene el Timeline anterior
                timeline.stop();
                // Crea un nuevo KeyFrame con una duración de 2 segundos
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(2), _ -> alert.close());
                // Asigna el nuevo KeyFrame al Timeline
                timeline.getKeyFrames().setAll(keyFrame);
                alert.show();
                // Empieza el Timeline después de mostrar la alerta
                timeline.play();
            } else {
                alert.showAndWait();
            }
        });
    }

    /**
     * Muestra una alerta de confirmación con los mensajes correspondientes.
     *
     * @return Un Optional con el ButtonType seleccionado por el usuario.
     */
    public Optional<ButtonType> mostrarAlertaConfirmacion(GridPane mensaje) {
        // Crear un VBox para contener el GridPane y el mensaje adicional
        VBox content = new VBox();
        content.setSpacing(10); // Espacio entre los elementos

        // Añadir el GridPane al VBox
        content.getChildren().add(mensaje);

        // Crear el texto adicional
        Text continuarText = new Text(MessageManager.getMessage("conclusion.continuar"));
        continuarText.setTextAlignment(TextAlignment.RIGHT);
        VBox.setMargin(continuarText, new Insets(0, 30, 0, 0));
        content.setAlignment(Pos.CENTER_RIGHT);

        // Añadir el texto adicional al VBox
        content.getChildren().add(continuarText);

        // Crear una alerta de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(MessageManager.getMessage("conclusion.confirmacion"));
        alert.setHeaderText(MessageManager.getMessage("conclusion.revisa_datos"));
        alert.getDialogPane().setContent(content);

        return alert.showAndWait();
    }

    /**
     * Solicita el nombre de una empresa al usuario.
     *
     * @return El nombre de la empresa ingresado por el usuario.
     */
    public String solicitarNombreEmpresa() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(MessageManager.getMessage("empresa.nombre.titulo"));
        dialog.setHeaderText(MessageManager.getMessage("empresa.nombre.encabezado"));
        dialog.setContentText(MessageManager.getMessage("empresa.nombre.contenido"));

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }
}
