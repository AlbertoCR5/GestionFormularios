package com.example.proyecto.util;

/**
 * Enumeración que representa los meses del año con su número y nombre.
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

    Meses(String numero, String nombre) {
        this.numero = numero;
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

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
