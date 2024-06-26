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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * La clase `PrincipalView` gestiona la interfaz principal de la aplicación.
 * Se encarga de mostrar mensajes y de inicializar las vistas de login y principal.
 * Utiliza JavaFX para la interfaz gráfica.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
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
    public void setController(@NotNull PrincipalController controller) {
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
     * @param mensaje       El mensaje a mostrar.
     * @param esInformacion Indica si el mensaje es de información (true) o de error (false).
     */
    public void mostrarMensaje(@NotNull String mensaje, boolean esInformacion) {
        Platform.runLater(() -> {
            Alert alert = esInformacion ? new Alert(Alert.AlertType.INFORMATION) : new Alert(Alert.AlertType.ERROR);
            alert.setTitle(MessageManager.getMessage(esInformacion ? "info.title" : "error.title"));
            alert.setHeaderText(null);
            alert.setContentText(mensaje);

            if (esInformacion) {
                timeline.stop(); // Detiene el Timeline anterior
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(2), _ -> alert.close()); // Crea un nuevo KeyFrame con una duración de 2 segundos
                timeline.getKeyFrames().setAll(keyFrame); // Asigna el nuevo KeyFrame al Timeline
                alert.show();
                timeline.play(); // Empieza el Timeline después de mostrar la alerta
            } else {
                alert.showAndWait();
            }
        });
    }

    /**
     * Muestra una alerta de confirmación con los mensajes correspondientes.
     *
     * @param mensaje El mensaje a mostrar en la alerta.
     * @return Un Optional con el ButtonType seleccionado por el usuario.
     */
    public Optional<ButtonType> mostrarAlertaConfirmacion(@NotNull GridPane mensaje) {
        VBox content = new VBox(10); // Crear un VBox con espacio entre los elementos
        content.getChildren().add(mensaje); // Añadir el GridPane al VBox

        Text continuarText = new Text(MessageManager.getMessage("conclusion.continuar")); // Crear el texto adicional
        continuarText.setTextAlignment(TextAlignment.RIGHT);
        VBox.setMargin(continuarText, new Insets(0, 30, 0, 0));
        content.setAlignment(Pos.CENTER_RIGHT);
        content.getChildren().add(continuarText); // Añadir el texto adicional al VBox

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // Crear una alerta de confirmación
        alert.setTitle(MessageManager.getMessage("conclusion.confirmacion"));
        alert.setHeaderText(MessageManager.getMessage("conclusion.revisa_datos"));
        alert.getDialogPane().setContent(content);

        return alert.showAndWait();
    }

    /**
     * Solicita el nombre de una empresa al usuario.
     *
     * @return El nombre de la empresa ingresado por el usuario o null si el usuario canceló.
     */
    @Nullable
    public String solicitarNombreEmpresa() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(MessageManager.getMessage("empresa.nombre.titulo"));
        dialog.setHeaderText(MessageManager.getMessage("empresa.nombre.encabezado"));
        dialog.setContentText(MessageManager.getMessage("empresa.nombre.contenido"));

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }
}
