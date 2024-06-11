package com.example.proyecto.util;

import org.jetbrains.annotations.NotNull;

/**
 * Clase utilitaria para convertir fechas en diferentes formatos a letras.
 */
public class ConversorFechaToLetras {

    /**
     * Convierte una fecha en formato dd/MM/yyyy a un formato con guiones.
     *
     * @param fecha La fecha a convertir.
     * @return La fecha en formato dd - MES - yyyy.
     */
    public static @NotNull String convertirFechaGuiones(String fecha) {
        String[] partes = fecha.split("/");
        String dia = partes[0];
        String mes = convertirMesALetras(partes[1]);
        String anio = partes[2];

        dia = (dia.length() == 1 ? "0" + dia : dia);
        return dia + " - " + mes + " - " + anio;
    }

    /**
     * Convierte una fecha en formato dd/MM/yyyy a un formato con el nombre del mes y año en letras.
     *
     * @param fecha La fecha a convertir.
     * @return La fecha en formato día de MES de dos mil año.
     */
    public static @NotNull String convertirFechaCompleta(String fecha) {
        String[] partes = fecha.split("/");
        String dia = partes[0].length() == 1 ? "0" + partes[0] : partes[0];
        String mes = partes[1].length() == 1 ? "0" + partes[1] : partes[1];
        String anio = partes[2];

        anio = convertirDiaALetras(anio.substring(anio.length() - 2));
        return (convertirDiaALetras(dia) + " de " + convertirMesALetras(mes) + " de dos mil " + anio).toUpperCase();
    }

    /**
     * Convierte un día en formato numérico a letras.
     *
     * @param dia El día en formato numérico.
     * @return El día en letras.
     */
    public static String convertirDiaALetras(String dia) {
        return Dias.obtenerNombrePorNumero(dia);
    }

    /**
     * Convierte un mes en formato numérico a letras.
     *
     * @param mes El mes en formato numérico.
     * @return El mes en letras.
     */
    public static String convertirMesALetras(@NotNull String mes) {
        if (mes.length() == 1) {
            mes = "0" + mes;
        }
        if (mes.length() > 2) {
            String[] partes = mes.split("/");
            mes = partes[1];
        }
        return Meses.obtenerNombrePorNumero(mes);
    }
}
