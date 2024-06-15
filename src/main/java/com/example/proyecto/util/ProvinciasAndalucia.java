package com.example.proyecto.util;
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

    ProvinciasAndalucia(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}

