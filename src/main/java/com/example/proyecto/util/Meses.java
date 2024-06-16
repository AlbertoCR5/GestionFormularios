package com.example.proyecto.util;

/**
 * Enumeración que representa los meses del año con su número y nombre.
 * Proporciona métodos para obtener el nombre del mes a partir de su número.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public enum Meses {

    ENERO("01", "ENERO"),
    FEBRERO("02", "FEBRERO"),
    MARZO("03", "MARZO"),
    ABRIL("04", "ABRIL"),
    MAYO("05", "MAYO"),
    JUNIO("06", "JUNIO"),
    JULIO("07", "JULIO"),
    AGOSTO("08", "AGOSTO"),
    SEPTIEMBRE("09", "SEPTIEMBRE"),
    OCTUBRE("10", "OCTUBRE"),
    NOVIEMBRE("11", "NOVIEMBRE"),
    DICIEMBRE("12", "DICIEMBRE");

    private final String numero;
    private final String nombre;

    /**
     * Constructor de la enumeración.
     *
     * @param numero El número del mes.
     * @param nombre El nombre del mes.
     */
    Meses(String numero, String nombre) {
        this.numero = numero;
        this.nombre = nombre;
    }

    /**
     * Obtiene el número del mes.
     *
     * @return El número del mes.
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Obtiene el nombre del mes.
     *
     * @return El nombre del mes.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el nombre del mes a partir de su número.
     *
     * @param numero El número del mes.
     * @return El nombre del mes, o una cadena vacía si no se encuentra el mes.
     */
    public static String obtenerNombrePorNumero(String numero) {
        for (Meses mes : values()) {
            if (mes.getNumero().equals(numero)) {
                return mes.getNombre();
            }
        }
        return "";
    }
}
