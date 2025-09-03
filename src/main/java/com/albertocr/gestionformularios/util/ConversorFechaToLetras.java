package com.albertocr.gestionformularios.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Clase de utilidad para formatear fechas de objetos {@link LocalDate} a diferentes
 * representaciones en texto y en español.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.3
 */
public final class ConversorFechaToLetras {

    private static final Logger logger = LoggerFactory.getLogger(ConversorFechaToLetras.class);
    private static final Locale SPANISH_LOCALE = Locale.of("es", "ES");
    private static final DateTimeFormatter GUIONES_FORMATTER = DateTimeFormatter.ofPattern("dd - MMMM - yyyy", SPANISH_LOCALE);
    private static final DateTimeFormatter DE_FORMATTER = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", SPANISH_LOCALE);

    /**
     * Constructor privado para prevenir la instanciación de esta clase de utilidad.
     */
    private ConversorFechaToLetras() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada.");
    }

    /**
     * Convierte un objeto LocalDate a un formato de texto completo en mayúsculas.
     *
     * @param fecha La fecha a convertir. No debe ser nula.
     * @return La fecha en formato "DÍA de MES de AÑO" en letras (ej. "UNO de ENERO de DOS MIL VEINTICINCO").
     */
    public static String convertirFechaEnLetras(LocalDate fecha) {
        if (fecha == null) {
            logger.warn("Se intentó convertir una fecha nula a letras.");
            return "";
        }

        String diaEnLetras = NumberToWordsConverter.convertirNumero(fecha.getDayOfMonth());
        String mesEnLetras = fecha.format(DateTimeFormatter.ofPattern("MMMM", SPANISH_LOCALE));
        String anioEnLetras = NumberToWordsConverter.convertirAnio(fecha.getYear());

        return String.format("%s de %s de %s", diaEnLetras, mesEnLetras, anioEnLetras).toUpperCase();
    }

    /**
     * Convierte un objeto LocalDate a un formato con guiones.
     *
     * @param fecha La fecha a convertir. No debe ser nula.
     * @return La fecha en formato "dd - MES - yyyy" (ej. "01 - Enero - 2025").
     */
    public static String convertirFechaGuiones(LocalDate fecha) {
        if (fecha == null) return "";
        return fecha.format(GUIONES_FORMATTER);
    }

    /**
     * Convierte un objeto LocalDate a un formato con la preposición "de".
     *
     * @param fecha La fecha a convertir. No debe ser nula.
     * @return La fecha en formato "dd de MES de yyyy" (ej. "01 de enero de 2025").
     */
    public static String convertirFechaDe(LocalDate fecha) {
        if (fecha == null) return "";
        return fecha.format(DE_FORMATTER);
    }

    /**
     * Obtiene el nombre del mes en español a partir de su valor numérico.
     *
     * @param monthValue El valor del mes (1 para enero, 12 para diciembre).
     * @return El nombre del mes en español.
     */
    public static String getMes(int monthValue) {
        if (monthValue < 1 || monthValue > 12) {
            logger.error("Valor de mes no válido: {}", monthValue);
            throw new IllegalArgumentException("El valor del mes debe estar entre 1 y 12.");
        }
        // Crea una fecha temporal para formatear el nombre del mes
        return LocalDate.of(2000, monthValue, 1).format(DateTimeFormatter.ofPattern("MMMM", SPANISH_LOCALE));
    }
}
