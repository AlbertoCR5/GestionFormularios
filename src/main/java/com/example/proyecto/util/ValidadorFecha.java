package com.example.proyecto.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Clase utilitaria para validar fechas.
 * Proporciona métodos para verificar el formato de una fecha y extraer sus componentes.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class ValidadorFecha {

    /**
     * Valida si una fecha está vacía o es incorrecta.
     *
     * @param fechaStr La fecha en formato de cadena.
     * @return true si la fecha está vacía o es incorrecta, false en caso contrario.
     */
    public static boolean esFormatoFechaNoValido(String fechaStr) {
        if (fechaStr == null || fechaStr.trim().isEmpty()) {
            return true;
        }

        if (!Constantes.DATE_PATTERN.matcher(fechaStr).matches()) {
            return true;
        }

        SimpleDateFormat formatoFecha = new SimpleDateFormat(Constantes.FORMATO_FECHA_1_DIGITO);
        formatoFecha.setLenient(false);

        try {
            formatoFecha.parse(fechaStr);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }
}
