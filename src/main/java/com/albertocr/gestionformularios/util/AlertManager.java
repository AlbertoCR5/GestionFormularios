package com.albertocr.gestionformularios.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Clase de utilidad para mostrar diálogos de alerta estándar de JavaFX.
 * <p>
 * Centraliza la creación de alertas de información, advertencia, error y confirmación,
 * asegurando una apariencia y comportamiento consistentes en toda la aplicación.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.1
 */
public final class AlertManager {

    private AlertManager() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada.");
    }

    public static void mostrarAlertaInformacion(String titulo, String mensaje) {
        mostrarAlerta(Alert.AlertType.INFORMATION, titulo, mensaje);
    }

    public static void mostrarAlertaAdvertencia(String titulo, String mensaje) {
        mostrarAlerta(Alert.AlertType.WARNING, titulo, mensaje);
    }

    public static void mostrarAlertaError(String titulo, String mensaje) {
        mostrarAlerta(Alert.AlertType.ERROR, titulo, mensaje);
    }

    public static Optional<ButtonType> mostrarAlertaConfirmacion(String titulo, String cabecera, String contenido) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecera);
        alert.setContentText(contenido);
        return alert.showAndWait();
    }

    /**
     * Muestra un diálogo de confirmación con botones personalizados.
     *
     * @param titulo    El título de la ventana de confirmación.
     * @param cabecera  El mensaje de la cabecera.
     * @param contenido El texto de contenido que proporciona más detalles.
     * @param buttons   Los tipos de botón personalizados a mostrar.
     * @return Un {@link Optional} con el {@link ButtonType} seleccionado por el usuario.
     */
    public static Optional<ButtonType> mostrarAlertaConfirmacionPersonalizada(String titulo, String cabecera, String contenido, ButtonType... buttons) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecera);
        alert.setContentText(contenido);
        alert.getButtonTypes().setAll(buttons);
        return alert.showAndWait();
    }

    private static void mostrarAlerta(Alert.AlertType type, String titulo, String mensaje) {
        Alert alert = new Alert(type);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
