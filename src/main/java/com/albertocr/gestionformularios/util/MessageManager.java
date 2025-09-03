package com.albertocr.gestionformularios.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Gestiona los mensajes de la aplicación para la internacionalización (i18n).
 * <p>
 * Carga los mensajes desde archivos de propiedades (messages_xx.properties)
 * según el Locale configurado. Es una clase de utilidad estática.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.1
 */
public final class MessageManager {

    private static final Logger logger = LoggerFactory.getLogger(MessageManager.class);
    private static final String BUNDLE_NAME = "messages";
    private static ResourceBundle resourceBundle;
    private static Locale currentLocale;

    // Bloque estático para inicializar con el Locale por defecto del sistema.
    static {
        setLocale(Locale.getDefault());
    }

    /**
     * Constructor privado para prevenir la instanciación.
     */
    private MessageManager() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada.");
    }

    /**
     * Establece el Locale para la aplicación y carga el ResourceBundle correspondiente.
     * Si no se encuentra el bundle para el locale especificado, intenta usar el
     * idioma base (ej. 'es' para 'es-ES') y, si también falla, usa el bundle por defecto.
     *
     * @param locale El nuevo Locale a establecer.
     */
    public static void setLocale(Locale locale) {
        currentLocale = locale;
        try {
            resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, currentLocale);
            logger.info("ResourceBundle cargado para el locale: {}", locale.toLanguageTag());
        } catch (MissingResourceException e) {
            logger.warn("No se encontró el archivo de propiedades para el locale: {}. Intentando fallback.", locale.toLanguageTag());
            try {
                // Fallback al idioma base (ej. de 'es-MX' a 'es')
                Locale languageOnlyLocale = Locale.of(locale.getLanguage());
                resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, languageOnlyLocale);
                currentLocale = languageOnlyLocale;
                logger.info("Fallback exitoso. ResourceBundle cargado para el locale base: {}", currentLocale.toLanguageTag());
            } catch (MissingResourceException e2) {
                // Fallback final al bundle por defecto (messages.properties)
                logger.error("No se pudo encontrar el archivo de propiedades para el locale base. Usando el de por defecto.", e2);
                resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, Locale.ROOT);
                currentLocale = Locale.ROOT;
            }
        }
    }

    /**
     * Obtiene el mensaje asociado a una clave del ResourceBundle actual.
     *
     * @param key La clave del mensaje.
     * @return El mensaje traducido, o la clave entre '!' si no se encuentra.
     */
    public static String getMessage(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException | NullPointerException e) {
            logger.warn("Mensaje no encontrado para la clave: '{}' en el locale: {}", key, currentLocale.toLanguageTag());
            return '!' + key + '!'; // Retorna la clave para indicar que falta la traducción
        }
    }

    /**
     * Obtiene el Locale actualmente configurado.
     *
     * @return El Locale actual.
     */
    public static Locale getCurrentLocale() {
        return currentLocale;
    }
}
