package com.example.proyecto.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * La clase MessageManager gestiona los mensajes de la aplicación utilizando archivos de propiedades.
 * Proporciona métodos para cambiar el idioma de los mensajes y para obtener mensajes localizados.
 * Esta clase es útil para internacionalizar una aplicación, permitiendo que los mensajes se adapten
 * automáticamente al idioma configurado.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class MessageManager {
    private static ResourceBundle bundle = ResourceBundle.getBundle("messages");

    /**
     * Establece el idioma para los mensajes de la aplicación.
     * Este método permite cambiar el idioma de los mensajes cargando el archivo de propiedades correspondiente.
     *
     * @param locale El nuevo idioma que se desea configurar.
     */
    public static void setLocale(Locale locale) {
        bundle = ResourceBundle.getBundle("messages", locale);
    }

    /**
     * Obtiene un mensaje localizado basado en la clave proporcionada.
     * Este método busca en el archivo de propiedades configurado el mensaje correspondiente a la clave especificada.
     *
     * @param key La clave del mensaje que se desea obtener.
     * @return El mensaje localizado correspondiente a la clave.
     */
    public static String getMessage(String key) {
        return bundle.getString(key);
    }
}
