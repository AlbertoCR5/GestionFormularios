package com.albertocr.gestionformularios.controller;

import com.albertocr.gestionformularios.interfaz.admin.VentanaPdfInspector;
import com.albertocr.gestionformularios.interfaz.escrutinio.VentanaModelosEscrutinio;
import com.albertocr.gestionformularios.interfaz.preaviso.VentanaPreaviso;
import com.albertocr.gestionformularios.interfaz.VentanaCalendarioComite;
import com.albertocr.gestionformularios.interfaz.VentanaUsuario;
import com.albertocr.gestionformularios.main.VCardGenerator;
import com.albertocr.gestionformularios.model.Usuario;
import com.albertocr.gestionformularios.util.AlertManager;
import com.albertocr.gestionformularios.util.MessageManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * Controlador para la ventana principal de la aplicación (view.fxml).
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.4
 */
public class PrincipalController {

    private static final Logger logger = LoggerFactory.getLogger(PrincipalController.class);

    @FXML private Menu menuHerramientas;

    @FXML
    public void initialize() {
        logger.info("PrincipalController inicializado.");
        configurarVisibilidadSegunRol();
    }

    private void configurarVisibilidadSegunRol() {
        Usuario usuarioActual = SessionManager.getInstance().getUsuarioActual();
        if (usuarioActual != null && usuarioActual.getRol() != Usuario.Rol.ADMIN) {
            if (menuHerramientas != null) {
                menuHerramientas.setVisible(false);
            }
        }
    }

    @FXML
    private void abrirVentanaPreaviso() {
        NavigationService.openWindow(VentanaPreaviso::new);
    }

    @FXML
    private void abrirVentanaModelosEscrutinio() {
        NavigationService.openWindow(VentanaModelosEscrutinio::new);
    }

    @FXML
    private void abrirVentanaCalendarioComite() {
        NavigationService.openWindow(VentanaCalendarioComite::new);
    }

    @FXML
    private void abrirVentanaUsuario() {
        NavigationService.openWindow(VentanaUsuario::new);
    }

    /**
     * Abre la nueva ventana del inspector de PDF.
     * Este método es llamado desde el MenuItem correspondiente en view.fxml.
     */
    @FXML
    private void abrirVentanaPdfInspector() {
        NavigationService.openWindow(VentanaPdfInspector::new);
    }

    @FXML
    private void cambiarIdiomaAEspanol() {
        logger.info("Cambiando idioma a español.");
        MessageManager.setLocale(Locale.of("es"));
        AlertManager.mostrarAlertaInformacion("Idioma", "Idioma cambiado a Español. Reinicie la aplicación para ver todos los cambios.");
    }

    @FXML
    private void cambiarIdiomaAIngles() {
        logger.info("Cambiando idioma a inglés.");
        MessageManager.setLocale(Locale.of("en"));
        AlertManager.mostrarAlertaInformacion("Language", "Language changed to English. Restart the application to see all changes.");
    }

    @FXML
    void generarVCard() {
        logger.info("Iniciando la generación de VCard.");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar VCard");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("VCard Files", "*.vcf"));
        fileChooser.setInitialFileName("contacto.vcf");
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                VCardGenerator.generarVCard(file);
                AlertManager.mostrarAlertaInformacion("VCard Generada", "La VCard se ha guardado correctamente en: " + file.getAbsolutePath());
                logger.info("VCard guardada en: {}", file.getAbsolutePath());
            } catch (IOException e) {
                AlertManager.mostrarAlertaError("Error", "No se pudo guardar la VCard.");
                logger.error("Error al guardar la VCard", e);
            }
        } else {
            logger.info("La operación de guardar VCard fue cancelada por el usuario.");
        }
    }

    @FXML
    private void salir() {
        AlertManager.mostrarAlertaConfirmacion("Confirmar Salida", "¿Está seguro de que quiere salir?", "La aplicación se cerrará.")
                .ifPresent(buttonType -> {
                    if (buttonType == javafx.scene.control.ButtonType.OK) {
                        logger.info("El usuario ha confirmado la salida. Cerrando la aplicación.");
                        Platform.exit();
                    } else {
                        logger.info("El usuario ha cancelado la salida.");
                    }
                });
    }
}
