package com.example.proyecto.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase utilitaria para validar fechas.
 * Proporciona métodos para verificar el formato de una fecha y extraer sus componentes.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class ValidadorFecha {

    private String dia;
    private String mes;
    private String anio;
    private String anioFormateado;

    /**
     * Verifica si una fecha tiene un formato válido.
     *
     * @param fechaStr La fecha en formato de cadena.
     * @return true si el formato es válido, false en caso contrario.
     */
    public boolean esFormatoFechaValido(String fechaStr) {
        if (fechaStr == null || fechaStr.isEmpty()) {
            return false;
        }

        SimpleDateFormat formatoFecha = new SimpleDateFormat();
        formatoFecha.setLenient(false);

        try {
            Date fecha = formatoFecha.parse(fechaStr);
            extraerDiaMesAnio(fecha);
            this.anioFormateado = String.valueOf(Short.parseShort(this.anio) % 100);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Extrae el día, mes y año de una fecha dada.
     *
     * @param fecha La fecha de la cual extraer los componentes.
     */
    private void extraerDiaMesAnio(Date fecha) {
        java.time.LocalDate localDate = fecha.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        this.dia = String.format("%02d", localDate.getDayOfMonth());
        this.mes = String.format("%02d", localDate.getMonthValue());
        this.anio = String.valueOf(localDate.getYear());
    }

    public String getDia() {
        return dia;
    }

    public String getMes() {
        return mes;
    }

    public String getAnio() {
        return anio;
    }

    public String getAnioFormateado() {
        return anioFormateado;
    }
}
