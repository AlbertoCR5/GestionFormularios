package com.example.proyecto.interfaz;

import com.example.proyecto.controller.PrincipalController;
import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.MessageManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ResourceBundle;

/**
 * La clase `VentanaLogin` gestiona la interfaz de la ventana de inicio de sesión.
 * Utiliza JavaFX para la interfaz gráfica.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 */
public class VentanaLogin {
    private final PrincipalController controller;
    private final Stage stage;

    /**
     * Constructor de la clase VentanaLogin.
     *
     * @param controller El controlador principal que maneja la lógica de inicio de sesión.
     */
    public VentanaLogin(PrincipalController controller) {
        this.controller = controller;
        this.stage = new Stage();
        controller.setVentanaLoginActual(this);
        configurarInterfazUsuario();
    }

    /**
     * Configura la interfaz de usuario para la ventana de inicio de sesión.
     */
    private void configurarInterfazUsuario() {
        stage.setTitle(MessageManager.getMessage("login.title"));
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.TOP_RIGHT);
        headerBox.setSpacing(10);

        Label labelIniciarSesion = new Label(MessageManager.getMessage("login.iniciarSesion"));
        labelIniciarSesion.setStyle(Constantes.BOLD_UNDERLINED_STYLE);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnCambiarIdioma = new Button(MessageManager.getMessage("language.switch"));

        btnCambiarIdioma.setOnAction(_ -> {
            String nuevoIdioma = btnCambiarIdioma.getText().equals("ES") ? "en" : "es";
            controller.cambiarIdioma(nuevoIdioma);
        });

        headerBox.getChildren().addAll(labelIniciarSesion, spacer, btnCambiarIdioma);

        Label usuarioLabel = new Label(MessageManager.getMessage("login.username"));
        usuarioLabel.setStyle("-fx-font-size: 14px;");
        TextField usuarioTextField = new TextField();
        usuarioTextField.setPrefWidth(250);
        usuarioTextField.setMaxWidth(250);

        Label contrasenaLabel = new Label(MessageManager.getMessage("login.password"));
        contrasenaLabel.setStyle("-fx-font-size: 14px;");
        PasswordField contrasenaField = new PasswordField();
        contrasenaField.setPrefWidth(250);
        contrasenaField.setMaxWidth(250);

        Button btnIniciarSesion = new Button(MessageManager.getMessage("login.button"));

        VBox buttonBox = new VBox(btnIniciarSesion);
        buttonBox.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(headerBox, usuarioLabel, usuarioTextField, contrasenaLabel, contrasenaField, buttonBox);

        btnIniciarSesion.setOnAction(_ -> {
            String usuario = usuarioTextField.getText();
            String contrasena = contrasenaField.getText();
            controller.iniciarSesion(usuario, contrasena);
            usuarioTextField.requestFocus();
        });

        Scene scene = new Scene(vbox, 255, 210);
        stage.setScene(scene);
    }


    /**
     * Muestra la ventana de inicio de sesión.
     */
    public void mostrarVentanaLogin() {
        stage.show();
    }

    /**
     * Cierra la ventana de inicio de sesión.
     */
    public void close() {
        stage.close();
    }
}
