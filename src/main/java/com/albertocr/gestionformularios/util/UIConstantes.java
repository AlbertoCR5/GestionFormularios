package com.albertocr.gestionformularios.util;

/**
 * Clase que contiene constantes utilizadas exclusivamente para la Interfaz de Usuario (UI) en JavaFX.
 * <p>
 * Centraliza los estilos, tamaños y espaciados para mantener una apariencia consistente
 * y facilitar cambios de diseño.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.0
 */
public final class UIConstantes {

    /**
     * Constructor privado para prevenir la instanciación.
     */
    private UIConstantes() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada.");
    }

    // --- Estilos de Texto CSS ---
    public static final String BOLD_UNDERLINED_STYLE = "-fx-font-size: 16px; -fx-font-weight: bold; -fx-underline: true;";
    public static final String FONT_SIZE_14_FONT_WEIGHT_BOLD = "-fx-font-size: 14px; -fx-font-weight: bold;";
    public static final String ESTILO_ETIQUETA_14PX = "-fx-font-size: 14px;";
    public static final String FONT_WEIGHT_BOLD = "-fx-font-weight: bold";

    // --- Espaciados y Tamaños ---
    public static final int ESPACIADO_VBOX = 10;
    public static final int ESPACIADO_HGAP = 10;
    public static final int ESPACIADO_VGAP = 10;
    public static final int PADDING_GENERAL = 20;
}
