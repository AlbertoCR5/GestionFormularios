package com.albertocr.gestionformularios.interfaz;

import com.albertocr.gestionformularios.controller.LoginManager;
import com.albertocr.gestionformularios.controller.SessionManager;
import com.albertocr.gestionformularios.interfaz.usuario.VentanaCambiarContrasena;
import com.albertocr.gestionformularios.model.Usuario;
import com.albertocr.gestionformularios.model.UsuarioDAO;
import com.albertocr.gestionformularios.util.AlertManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Ventana de inicio de sesión para la aplicación.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.3
 */
public class VentanaLogin extends Stage {

    private static final Logger logger = LoggerFactory.getLogger(VentanaLogin.class);
    private final LoginManager loginManager;

    private TextField userTextField;
    private PasswordField pwBox;

    public VentanaLogin() {
        this.loginManager = new LoginManager(new UsuarioDAO());
        setTitle("Inicio de Sesión");
        GridPane grid = configurarGrid();
        crearCamposEntrada(grid);
        crearBotones(grid);
        Scene scene = new Scene(grid, 350, 250);
        setScene(scene);
    }

    private void handleLogin() {
        String username = userTextField.getText();
        String password = pwBox.getText();
        logger.debug("Intento de inicio de sesión para el usuario: {}", username);

        Optional<Usuario> usuarioOpt = loginManager.autenticar(username, password);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            SessionManager.getInstance().setUsuarioActual(usuario);
            logger.info("Inicio de sesión exitoso para el usuario: {}", username);

            if (usuario.isDebeCambiarContrasena()) {
                logger.info("El usuario '{}' debe cambiar su contraseña.", username);
                abrirVentanaCambioContrasena(usuario);
            } else {
                abrirVentanaPrincipal();
            }
            this.close();
        } else {
            AlertManager.mostrarAlertaError("Error de autenticación", "Usuario o contraseña incorrectos.");
        }
    }

    private void abrirVentanaCambioContrasena(Usuario usuario) {
        VentanaCambiarContrasena ventana = new VentanaCambiarContrasena(usuario);
        ventana.show();
    }

    private void abrirVentanaPrincipal() {
        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
        ventanaPrincipal.show();
    }

    private GridPane configurarGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        return grid;
    }

    private void crearCamposEntrada(GridPane grid) {
        Label userName = new Label("Usuario:");
        grid.add(userName, 0, 1);
        userTextField = new TextField();
        userTextField.setPromptText("Ingrese su usuario");
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Contraseña:");
        grid.add(pw, 0, 2);
        pwBox = new PasswordField();
        pwBox.setPromptText("Ingrese su contraseña");
        grid.add(pwBox, 1, 2);
    }

    private void crearBotones(GridPane grid) {
        Button btnLogin = new Button("Iniciar Sesión");
        Button btnExit = new Button("Salir");

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(btnExit, btnLogin);
        grid.add(hbBtn, 1, 4);

        btnLogin.setOnAction(e -> handleLogin());
        btnExit.setOnAction(e -> handleExit());
    }

    private void handleExit() {
        Optional<ButtonType> result = AlertManager.mostrarAlertaConfirmacion("Confirmar Salida",
                "¿Está seguro de que quiere salir?",
                "La aplicación se cerrará.");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            logger.info("El usuario ha confirmado la salida. Cerrando aplicación.");
            System.exit(0);
        } else {
            logger.info("El usuario ha cancelado la salida.");
        }
    }
}
