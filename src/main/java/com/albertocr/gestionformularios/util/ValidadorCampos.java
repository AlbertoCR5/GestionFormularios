package com.albertocr.gestionformularios.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;

/**
 * Clase utilitaria para validar diversos campos y realizar cálculos relacionados con el número de representantes.
 * Proporciona métodos para validar DNI, CIF, formato de hora y calcular el número de representantes elegibles.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.1
 */
public class ValidadorCampos {

    /**
     * Verifica si un DNI es válido.
     *
     * @param dni El DNI a verificar.
     * @return true si el DNI es válido, false en caso contrario.
     */
    public static boolean verificarDNI(String dni) {
        if (dni == null || dni.length() != 9) {
            return false;
        }

        String numeroStr = dni.substring(0, 8);
        String letra = dni.substring(8).toUpperCase();

        int numero;
        try {
            numero = Integer.parseInt(numeroStr);
        } catch (NumberFormatException e) {
            return false;
        }

        char letraCalculada = calcularLetraDNI(numero);
        return letraCalculada == letra.charAt(0);
    }

    /**
     * Calcula la letra correspondiente a un número de DNI.
     *
     * @param numeroDNI El número del DNI.
     * @return La letra correspondiente.
     */
    private static char calcularLetraDNI(int numeroDNI) {
        String caracteres = "TRWAGMYFPDXBNJZSQVHLCKE";
        int indice = numeroDNI % 23;
        return caracteres.charAt(indice);
    }

    /**
     * Verifica si un CIF es válido.
     *
     * @param cif El CIF a verificar.
     * @return true si el CIF es válido, false en caso contrario.
     */
    public static boolean verificarCIF(String cif) {
        cif = cif.trim().toUpperCase();
        if (cif.length() != 9) {
            return false;
        }

        char tipoEntidad = cif.charAt(0);
        if (tipoEntidad < 'A' || tipoEntidad > 'Z') {
            return false;
        }

        if (!cif.substring(1, 8).matches("\\d{7}")) {
            return false;
        }

        int digitoControl = Character.getNumericValue(cif.charAt(8));
        int suma = calcularSumaCif(cif);

        int resto = suma % 10;
        int resultado = resto == 0 ? 0 : 10 - resto;

        return digitoControl == resultado;
    }

    /**
     * Calcula la suma para la verificación del CIF.
     *
     * @param cif El CIF a verificar.
     * @return La suma calculada.
     */
    private static int calcularSumaCif(String cif) {
        int suma = 0;
        for (int i = 1; i < 8; i++) {
            int digito = Character.getNumericValue(cif.charAt(i));
            if (i % 2 != 0) {
                digito *= 2;
                digito = digito < 10 ? digito : digito - 9;
            }
            suma += digito;
        }
        return suma;
    }

    /**
     * Valida si una hora tiene un formato válido.
     *
     * @param hora La hora en formato de cadena.
     * @return true si el formato es válido, false en caso contrario.
     */
    public static boolean validarHora(String hora) {
        if (!hora.matches("^([0-9]{2}):([0-9]{2})$")) {
            return false;
        }
        int horaEntera = Integer.parseInt(hora.substring(0, 2));
        int minutoEntero = Integer.parseInt(hora.substring(3, 5));

        return horaEntera >= 0 && horaEntera <= 23 && minutoEntero >= 0 && minutoEntero <= 59;
    }

    /**
     * Calcula el número de representantes elegibles basado en el número total de electores.
     *
     * @param totalElectores El número total de electores.
     * @return El número de representantes elegibles.
     */
    public static int calcularNumeroRepresentantes(int totalElectores) {
        int representantesElegibles;

        if (totalElectores < 101) {
            representantesElegibles = 5;
        } else if (totalElectores < 251) {
            representantesElegibles = 9;
        } else if (totalElectores < 501) {
            representantesElegibles = 13;
        } else if (totalElectores < 751) {
            representantesElegibles = 17;
        } else if (totalElectores < 1001) {
            representantesElegibles = 21;
        } else {
            // Cálculo para los siguientes bloques de 1000 electores
            int bloquesAdicionales = (totalElectores - 1000) / 1000;
            representantesElegibles = Math.min(75, 21 + bloquesAdicionales * 2);
        }
        return representantesElegibles;
    }
}