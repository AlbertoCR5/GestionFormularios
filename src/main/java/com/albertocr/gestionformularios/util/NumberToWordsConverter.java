package com.albertocr.gestionformularios.util;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Clase de utilidad para convertir números a su representación en palabras en español.
 * Optimizada para los rangos necesarios en la aplicación (días, años).
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.0
 */
public final class NumberToWordsConverter {

    private static final Map<Integer, String> NUMEROS_A_PALABRAS = Stream.of(new Object[][] {
            {0, "cero"}, {1, "uno"}, {2, "dos"}, {3, "tres"}, {4, "cuatro"}, {5, "cinco"},
            {6, "seis"}, {7, "siete"}, {8, "ocho"}, {9, "nueve"}, {10, "diez"},
            {11, "once"}, {12, "doce"}, {13, "trece"}, {14, "catorce"}, {15, "quince"},
            {16, "dieciséis"}, {17, "diecisiete"}, {18, "dieciocho"}, {19, "diecinueve"},
            {20, "veinte"}, {21, "veintiuno"}, {22, "veintidós"}, {23, "veintitrés"},
            {24, "veinticuatro"}, {25, "veinticinco"}, {26, "veintiséis"}, {27, "veintisiete"},
            {28, "veintiocho"}, {29, "veintinueve"}, {30, "treinta"}, {31, "treinta y uno"}
    }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));

    private static final Map<Integer, String> DECENAS = Stream.of(new Object[][] {
            {20, "veinte"}, {30, "treinta"}, {40, "cuarenta"}, {50, "cincuenta"},
            {60, "sesenta"}, {70, "setenta"}, {80, "ochenta"}, {90, "noventa"}
    }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));

    /**
     * Constructor privado para prevenir la instanciación.
     */
    private NumberToWordsConverter() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada.");
    }

    /**
     * Convierte un número (generalmente un día del mes) a su representación en palabras.
     *
     * @param numero El número a convertir.
     * @return El número en palabras, o una cadena vacía si no se encuentra.
     */
    public static String convertirNumero(int numero) {
        return NUMEROS_A_PALABRAS.getOrDefault(numero, "");
    }

    /**
     * Convierte un año (ej. 2025) a su representación en palabras (ej. "dos mil veinticinco").
     *
     * @param anio El año a convertir.
     * @return El año en palabras.
     */
    public static String convertirAnio(int anio) {
        if (anio < 2000 || anio > 2099) {
            return String.valueOf(anio); // Retorna el número si está fuera del rango esperado
        }
        int ultimosDosDigitos = anio % 100;
        if (ultimosDosDigitos == 0) {
            return "dos mil";
        }

        String parteFinal;
        if (ultimosDosDigitos <= 31) {
            parteFinal = convertirNumero(ultimosDosDigitos);
        } else {
            int decena = ultimosDosDigitos / 10 * 10;
            int unidad = ultimosDosDigitos % 10;
            if (unidad == 0) {
                parteFinal = DECENAS.get(decena);
            } else {
                parteFinal = DECENAS.get(decena) + " y " + convertirNumero(unidad);
            }
        }
        return "dos mil " + parteFinal;
    }
}
