package com.example.proyecto.util;

/**
 * Enumeración que representa las provincias de Andalucía.
 * Cada provincia tiene un nombre asociado.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public enum ProvinciasAndalucia {
    ALMERIA("Almería"),
    CADIZ("Cádiz"),
    CORDOBA("Córdoba"),
    GRANADA("Granada"),
    HUELVA("Huelva"),
    JAEN("Jaén"),
    MALAGA("Málaga"),
    SEVILLA("Sevilla");

    private final String nombre;

    /**
     * Constructor de la enumeración.
     *
     * @param nombre El nombre de la provincia.
     */
    ProvinciasAndalucia(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el nombre de la provincia.
     *
     * @return El nombre de la provincia.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Retorna el nombre de la provincia como cadena de texto.
     *
     * @return El nombre de la provincia.
     */
    @Override
    public String toString() {
        return nombre;
    }
}
