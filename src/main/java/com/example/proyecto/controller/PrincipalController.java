package com.example.proyecto.controller;

import com.example.proyecto.interfaz.*;
import com.example.proyecto.modal.*;
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
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * La clase `PrincipalController` gestiona la lógica de la aplicación y la interacción entre las vistas y el modelo.
 * Se encarga de iniciar sesión y de cambiar entre diferentes vistas.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 */
public class PrincipalController {

    private final Timeline timeline = new Timeline();
    private final DirectorioManager directorioManager = new DirectorioManager();
    private final LoginManager inicioSesion;
    private final Path rutaElecciones;
    private PrincipalView vistaPrincipal;
    private VentanaLogin ventanaLoginActual;

    /**
     * Constructor de la clase PrincipalController.
     *
     * @param inicioSesion La instancia del gestor de inicio de sesión.
     * @throws IOException Si ocurre un error al crear el directorio de elecciones.
     */
    public PrincipalController(LoginManager inicioSesion) throws IOException {
        this.inicioSesion = inicioSesion;
        this.rutaElecciones = directorioManager.crearDirectorioElecciones();
    }

    /**
     * Establece la vista principal de la aplicación.
     *
     * @param vistaPrincipal La vista principal.
     */
    public void setView(PrincipalView vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
    }

    /**
     * Maneja el proceso de inicio de sesión.
     *
     * @param usuario    El nombre de usuario.
     * @param contrasena La contraseña del usuario.
     */
    public void iniciarSesion(String usuario, String contrasena) {
        try {
            if (inicioSesion.verificarCredenciales(usuario, contrasena)) {
                manejarInicioSesionCorrecto();
            } else {
                vistaPrincipal.mostrarMensaje(vistaPrincipal.getBundle().getString("login.credenciales.incorrectas"), false);
            }
        } catch (Exception e) {
            vistaPrincipal.mostrarMensaje(String.format(vistaPrincipal.getBundle().getString("login.error"), e.getMessage()), false);
        }
    }

    /**
     * Maneja un inicio de sesión correcto mostrando un mensaje y luego la ventana principal.
     */
    private void manejarInicioSesionCorrecto() {
        Platform.runLater(() -> {
            vistaPrincipal.mostrarMensaje(vistaPrincipal.getBundle().getString("login.correcto"), true);
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(_ -> vistaPrincipal.mostrarVentanaPrincipal());
            pause.play();
        });
    }

    /**
     * Maneja la selección de opciones del menú.
     *
     * @param opcion La opción seleccionada.
     */
    public void tratarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1:
                    tratarPreaviso();
                    break;
                case 2:
                    tratarModelosEscrutinio();
                    break;
                case 3:
                    vistaPrincipal.mostrarMensaje(vistaPrincipal.getBundle().getString("menu.opcion.modelo73"), true);
                    break;
                case 4:
                    vistaPrincipal.mostrarMensaje(vistaPrincipal.getBundle().getString("menu.opcion.anexoDelegados"), true);
                    break;
                case 5:
                    vistaPrincipal.mostrarMensaje(vistaPrincipal.getBundle().getString("menu.opcion.calendarioComite"), true);
                    break;
                case 6:
                    tratarUsuarios();
                    break;
                case 7:
                    vistaPrincipal.mostrarMensaje(vistaPrincipal.getBundle().getString("menu.opcion.imprimir"), true);
                    break;
                default:
                    vistaPrincipal.mostrarMensaje(vistaPrincipal.getBundle().getString("menu.opcion.noValida"), false);
                    break;
            }
        } catch (Exception e) {
            vistaPrincipal.mostrarMensaje(String.format(vistaPrincipal.getBundle().getString("menu.error"), e.getMessage()), false);
        }
    }

    /**
     * Muestra la ventana de preaviso después de un pequeño retraso.
     */
    private void tratarPreaviso() {
        vistaPrincipal.mostrarMensaje(vistaPrincipal.getBundle().getString("menu.opcion.preaviso"), true);
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), _ -> Platform.runLater(() -> {
            Preaviso preaviso = new Preaviso(); // Creamos la instancia de Preaviso
            VentanaPreaviso nuevaVentanaPreaviso = new VentanaPreaviso(vistaPrincipal, preaviso); // Pasamos la instancia al constructor
            try {
                nuevaVentanaPreaviso.mostrarVentanaPreaviso();
            } catch (IOException e) {
                vistaPrincipal.mostrarMensaje(String.format(vistaPrincipal.getBundle().getString("preaviso.error"), e.getMessage()), false);
            }
        })));
        timeline.play();
    }

    /**
     * Muestra la ventana de modelos de escrutinio después de solicitar el nombre de la empresa.
     */
    private void tratarModelosEscrutinio() {
        vistaPrincipal.mostrarMensaje(vistaPrincipal.getBundle().getString("menu.opcion.modelosEscrutinio"), true);
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), _ -> Platform.runLater(() -> {
            String nombreEmpresa = vistaPrincipal.solicitarNombreEmpresa();
            Path rutaEmpresa = directorioManager.buscarCarpetaEmpresa(rutaElecciones, nombreEmpresa);
            if (rutaEmpresa != null) {
                vistaPrincipal.mostrarMensaje(String.format(vistaPrincipal.getBundle().getString("empresa.encontrada"), rutaEmpresa), true);
                PauseTransition pause = getPauseTransition(rutaEmpresa);
                pause.play();
            } else {
                vistaPrincipal.mostrarMensaje(vistaPrincipal.getBundle().getString("empresa.noEncontrada"), false);
            }
        })));
        timeline.play();
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
            Modelo_5_1 modelo51 = new Modelo_5_1();
            Modelo_5_2_Proceso modeloProceso = new Modelo_5_2_Proceso();
            Modelo_5_2_Conclusion modeloConclusion = new Modelo_5_2_Conclusion();
            VentanaModelosEscrutinio ventanaModelosEscrutinio = new VentanaModelosEscrutinio(vistaPrincipal, rutaEmpresa, modelo51, modeloProceso, modeloConclusion);
            ventanaModelosEscrutinio.iniciarSecuencia();
        });
        return pause;
    }

    /**
     * Muestra la ventana de usuarios con opciones específicas según el tipo de usuario (admin o regular).
     */
    private void tratarUsuarios() {
        vistaPrincipal.mostrarMensaje(vistaPrincipal.getBundle().getString("menu.opcion.usuarios"), true);
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), _ -> Platform.runLater(() -> {
        String usuarioActual = inicioSesion.getUsuarioActual();

        UsuarioDAO usuarioDAO = new UsuarioDAO(vistaPrincipal, new DatabaseManager(vistaPrincipal));
        boolean esAdmin = usuarioDAO.esAdmin(usuarioActual);

        VentanaUsuario ventanaUsuario = new VentanaUsuario(usuarioDAO, usuarioActual, esAdmin, vistaPrincipal);
        ventanaUsuario.mostrarVentanaUsuario();
        })));
        timeline.play();
    }

    /**
     * Cambia el idioma de la interfaz.
     *
     * @param nuevoIdioma El nuevo idioma a establecer.
     */
    public void cambiarIdioma(String nuevoIdioma) {
        Locale locale = Locale.forLanguageTag(nuevoIdioma);
        MessageManager.setLocale(locale);
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        vistaPrincipal.setBundle(bundle);
        if (ventanaLoginActual != null) {
            ventanaLoginActual.close();
        }
        VentanaLogin nuevaVentanaLogin = new VentanaLogin(this, bundle);
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
