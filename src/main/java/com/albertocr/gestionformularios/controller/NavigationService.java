package com.albertocr.gestionformularios.controller;

import com.albertocr.gestionformularios.util.AlertManager;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * Servicio de utilidad para gestionar la navegación entre ventanas (Stages).
 * <p>
 * Centraliza la lógica de creación y visualización de nuevas ventanas para
 * mantener los controladores más limpios y desacoplados.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.2
 */
public final class NavigationService {

    private static final Logger logger = LoggerFactory.getLogger(NavigationService.class);

    /**
     * Constructor privado para prevenir la instanciación.
     */
    private NavigationService() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada.");
    }

    /**
     * Crea y muestra una nueva ventana (Stage).
     *
     * @param windowSupplier El proveedor que crea la instancia de la ventana.
     * @param <T>            El tipo de la ventana, que debe extender de {@link Stage}.
     */
    public static <T extends Stage> void openWindow(Supplier<T> windowSupplier) {
        try {
            T window = windowSupplier.get();
            logger.info("Abriendo ventana de tipo: {}", window.getClass().getSimpleName());
            window.show();
        } catch (Exception e) {
            logger.error("No se pudo crear o mostrar la ventana.", e);
            AlertManager.mostrarAlertaError(
                "Error de Navegación",
                "No se pudo abrir la nueva ventana. Ocurrió un error inesperado."
            );
        }
    }
}
