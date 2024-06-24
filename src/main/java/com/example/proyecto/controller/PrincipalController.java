package com.example.proyecto.controller;

import com.example.proyecto.interfaz.PrincipalView;
import com.example.proyecto.interfaz.VentanaLogin;
import com.example.proyecto.interfaz.escrutinio.VentanaModelosEscrutinio;
import com.example.proyecto.interfaz.VentanaUsuario;
import com.example.proyecto.interfaz.preaviso.VentanaPreaviso;
import com.example.proyecto.modal.*;
import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.DirectorioManager;
import com.example.proyecto.util.MessageManager;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Locale;
import java.util.logging.Level;

/**
 * La clase `PrincipalController` gestiona la lógica de la aplicación y la interacción entre las vistas y el modelo.
 * Se encarga de iniciar sesión y de cambiar entre diferentes vistas.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class PrincipalController {

    private final Timeline timeline = new Timeline();
    private final DirectorioManager directorioManager = new DirectorioManager();
    private final LoginManager loginManager;
    private final Path rutaElecciones;
    private PrincipalView principalView;
    private VentanaLogin ventanaLoginActual;
    VentanaModelosEscrutinio ventanaModelosEscrutinio;

    /**
     * Constructor de la clase PrincipalController.
     *
     * @param loginManager La instancia del gestor de inicio de sesión.
     * @throws IOException Sí ocurre un error al crear el directorio de elecciones.
     */
    public PrincipalController(LoginManager loginManager) throws IOException {
        this.loginManager = loginManager;
        this.rutaElecciones = directorioManager.crearDirectorioElecciones();
    }

    /**
     * Establece la vista principal de la aplicación.
     *
     * @param principalView La vista principal.
     */
    public void setView(PrincipalView principalView) {
        this.principalView = principalView;
    }

    /**
     * Maneja el proceso de inicio de sesión.
     *
     * @param usuario    El nombre de usuario.
     * @param contrasena La contraseña del usuario.
     */
    public void iniciarSesion(String usuario, String contrasena) {
        if (loginManager.verificarCredenciales(usuario, contrasena)) {
            manejarInicioSesionCorrecto();
        } else {
            principalView.mostrarMensaje(MessageManager.getMessage("login.credenciales.incorrectas"), false);
        }
    }

    /**
     * Maneja un inicio de sesión correcto mostrando un mensaje y luego la ventana principal.
     */
    private void manejarInicioSesionCorrecto() {
        Platform.runLater(() -> {
            principalView.mostrarMensaje(MessageManager.getMessage("login.correcto"), true);
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(_ -> principalView.mostrarVentanaPrincipal());
            pause.play();
        });
    }

    /**
     * Maneja la selección de opciones del menú.
     *
     * @param opcion La opción seleccionada.
     */
    public void tratarOpcion(int opcion) throws IOException, SQLException {
        switch (opcion) {
            case 1 -> tratarPreaviso();
            case 2 -> tratarModelosEscrutinio();
            case 3 -> tratarModelo73();
            case 4 -> tratarAnexoDelegados();
            case 5 -> tratarCalendarioComite();
            case 6 -> tratarSalidaSindical();
            case 7 -> tratarUsuarios();
            case 8 -> tratarImprimir();
            default -> principalView.mostrarMensaje(MessageManager.getMessage("menu.opcion.noValida"), false);
        }
    }

    /**
     * Muestra la ventana de preaviso después de un pequeño retraso.
     */
    private void tratarPreaviso() {
        principalView.mostrarMensaje(MessageManager.getMessage("menu.opcion.preaviso"), true);
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), _ -> Platform.runLater(() -> {
            try {
                mostrarVentanaPreaviso();
            } catch (IOException e) {
                principalView.mostrarMensaje(String.format(MessageManager.getMessage("preaviso.error"), e.getMessage()), false);
                Constantes.LOGGER.log(Level.SEVERE, "error.tratarPreaviso", e);
            }
        })));
        timeline.play();
    }

    /**
     * Muestra la ventana de preaviso.
     *
     * @throws IOException Si ocurre un error al mostrar la ventana.
     */
    private void mostrarVentanaPreaviso() throws IOException {
        Preaviso preaviso = new Preaviso(); // Creamos la instancia de Preaviso
        VentanaPreaviso nuevaVentanaPreaviso = new VentanaPreaviso(principalView, preaviso); // Pasamos la instancia al constructor
        nuevaVentanaPreaviso.mostrarVentanaPreaviso();
    }

    /**
     * Muestra la ventana de modelos de escrutinio después de solicitar el nombre de la empresa.
     */
    private void tratarModelosEscrutinio(){
        principalView.mostrarMensaje(MessageManager.getMessage("menu.opcion.modelosEscrutinio"), true);
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), _ -> Platform.runLater(() -> {
            try {
                mostrarModelosEscrutinio();
            } catch (IOException e) {
                principalView.mostrarMensaje(String.format(MessageManager.getMessage("modelosEscrutinio.error"), e.getMessage()), false);
                Constantes.LOGGER.log(Level.SEVERE, MessageManager.getMessage("error.tratarPreaviso"), e);
            }
        })));
        timeline.play();
    }

    /**
     * Muestra la ventana de modelos de escrutinio.
     *
     * @throws IOException Si ocurre un error al mostrar la ventana.
     */
    private void mostrarModelosEscrutinio() throws IOException {
        String nombreEmpresa = principalView.solicitarNombreEmpresa();
        Path rutaEmpresa = directorioManager.buscarCarpetaEmpresa(rutaElecciones, nombreEmpresa);
        if (rutaEmpresa != null) {
            principalView.mostrarMensaje(String.format(MessageManager.getMessage("empresa.encontrada"), rutaEmpresa), true);
            PauseTransition pause = getPauseTransition(rutaEmpresa);
            pause.play();
        } else {
            principalView.mostrarMensaje(MessageManager.getMessage("empresa.noEncontrada"), false);
        }
    }

    /**
     * Crea una transición de pausa antes de mostrar la ventana de modelos de escrutinio.
     *
     * @param rutaEmpresa La ruta de la empresa.
     * @return La transición de pausa.
     */
    @NotNull
    private PauseTransition getPauseTransition(Path rutaEmpresa) {
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(_ -> {
            try {
                Modelo_5_1 nuevoModelo5_1 = new Modelo_5_1();
                Modelo_5_2_Proceso nuevoModelo5_2Proceso = new Modelo_5_2_Proceso();
                Modelo_5_2_Conclusion nuevoModeloConclusion = new Modelo_5_2_Conclusion();
                ventanaModelosEscrutinio = new VentanaModelosEscrutinio(principalView, nuevoModelo5_1, nuevoModelo5_2Proceso, nuevoModeloConclusion, rutaEmpresa);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return pause;
    }

    /**
     * Muestra la ventana de usuarios con opciones específicas según el tipo de usuario (admin o regular).
     */
    private void tratarUsuarios() {
        principalView.mostrarMensaje(MessageManager.getMessage("menu.opcion.usuarios"), true);
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), _ -> Platform.runLater(() -> {
            try {
                mostrarVentanaUsuarios();
            } catch (IOException | SQLException e) {
                principalView.mostrarMensaje(String.format(MessageManager.getMessage("usuarios.error"), e.getMessage()), false);
                Constantes.LOGGER.log(Level.SEVERE, MessageManager.getMessage("error.tratarUsuarios"), e);
            }
        })));
        timeline.play();
    }

    /**
     * Muestra la ventana de usuarios.
     *
     * @throws IOException Si ocurre un error al mostrar la ventana.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    private void mostrarVentanaUsuarios() throws IOException, SQLException {
        String usuarioActual = loginManager.getUsuarioActual();
        UsuarioDAO usuarioDAO = new UsuarioDAO(principalView, new DatabaseManager(principalView));
        boolean esAdmin = usuarioDAO.esAdmin(usuarioActual);
        VentanaUsuario ventanaUsuario = new VentanaUsuario(usuarioDAO, usuarioActual, esAdmin, principalView);
        ventanaUsuario.mostrarVentanaUsuario();
    }

    /**
     * Muestra la ventana de Modelo 73.
     */
    private void tratarModelo73() {
        principalView.mostrarMensaje(MessageManager.getMessage("menu.opcion.modelo73"), true);
        // Lógica pendiente de implementación
    }

    /**
     * Muestra la ventana de Anexo Delegados.
     */
    private void tratarAnexoDelegados() {
        principalView.mostrarMensaje(MessageManager.getMessage("menu.opcion.anexoDelegados"), true);
        // Lógica pendiente de implementación
    }

    /**
     * Muestra la ventana de Calendario Comité.
     */
    private void tratarCalendarioComite() {
        principalView.mostrarMensaje(MessageManager.getMessage("menu.opcion.calendarioComite"), true);
        // Lógica pendiente de implementación
    }

    /**
     * Muestra la ventana de Salida Sindical.
     */
    private void tratarSalidaSindical() {
        principalView.mostrarMensaje(MessageManager.getMessage("menu.opcion.salidaSindical"), true);
        // Lógica pendiente de implementación
    }

    /**
     * Muestra la ventana de Imprimir.
     */
    private void tratarImprimir() {
        principalView.mostrarMensaje(MessageManager.getMessage("menu.opcion.imprimir"), true);
        // Lógica pendiente de implementación
    }

    /**
     * Cambia el idioma de la interfaz.
     *
     * @param nuevoIdioma El nuevo idioma a establecer.
     */
    public void cambiarIdioma(String nuevoIdioma) {
        Locale locale = Locale.forLanguageTag(nuevoIdioma);
        MessageManager.setLocale(locale);
        if (ventanaLoginActual != null) {
            ventanaLoginActual.close();
        }
        VentanaLogin nuevaVentanaLogin = new VentanaLogin(this);
        ventanaLoginActual = nuevaVentanaLogin;
        nuevaVentanaLogin.mostrarVentanaLogin();
    }

    /**
     * Establece la ventana de login actual.
     *
     * @param ventanaLogin La ventana de login actual.
     */
    public void setVentanaLoginActual(VentanaLogin ventanaLogin) {
        this.ventanaLoginActual = ventanaLogin;
    }
}

