package com.example.proyecto.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase utilitaria para validar fechas.
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

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
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

    private void extraerDiaMesAnio(Date fecha) {
        java.time.LocalDate localDate = fecha.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        this.dia = String.valueOf(localDate.getDayOfMonth());
        this.mes = String.valueOf(localDate.getMonthValue());
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
