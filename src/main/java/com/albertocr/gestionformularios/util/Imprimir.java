package com.albertocr.gestionformularios.util;

import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 * Clase de utilidad para manejar la impresión de documentos.
 *
 * @autor Alberto Castro <AlbertoCastroCR>
 * @version 1.0
 */
public class Imprimir {

    private static final Logger logger = LoggerFactory.getLogger(Imprimir.class);

    /**
     * Envía un archivo PDF a la impresora predeterminada del sistema.
     *
     * @param archivo El archivo PDF a imprimir.
     */
    public static void imprimirPDF(File archivo) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.PRINT)) {
                try {
                    desktop.print(archivo);
                    logger.info("El archivo {} ha sido enviado a la cola de impresión.", archivo.getName());
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Impresión", "El documento se ha enviado a la impresora.");
                } catch (IOException e) {
                    logger.error("Error al intentar imprimir el archivo: " + archivo.getAbsolutePath(), e);
                    mostrarAlerta(Alert.AlertType.ERROR, "Error de Impresión", "No se pudo imprimir el archivo. Verifique su impresora.");
                }
            } else {
                logger.warn("La acción de imprimir no es soportada en este sistema.");
                mostrarAlerta(Alert.AlertType.WARNING, "Impresión no Soportada", "La impresión no es soportada en este sistema.");
            }
        } else {
            logger.warn("El escritorio no es soportado en este sistema, no se puede imprimir.");
            mostrarAlerta(Alert.AlertType.WARNING, "Escritorio no Soportado", "No se puede acceder a las funciones de escritorio para imprimir.");
        }
    }

    /**
     * Muestra una ventana de alerta.
     *
     * @param type    El tipo de alerta.
     * @param title   El título de la alerta.
     * @param message El mensaje de la alerta.
     */
    private static void mostrarAlerta(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
