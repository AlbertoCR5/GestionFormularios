package com.albertocr.gestionformularios.main;

/**
 * Clase principal de entrada para la aplicación.
 * <p>
 * Su único propósito es servir como punto de entrada y lanzar la aplicación JavaFX
 * a través de la clase {@link PrincipalApplication}. Esto resuelve problemas con el
 * classloader de JavaFX al ejecutar la aplicación desde algunos IDEs.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.0
 */
public class Main {

    /**
     * El método principal que inicia la aplicación.
     *
     * @param args los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        PrincipalApplication.main(args);
    }
}
