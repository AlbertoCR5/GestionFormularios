package com.example.proyecto.util;

/**
 * Clase utilitaria para validar diversos campos.
 */
public class ValidadorCampos {

    /**
     * Verifica si un DNI es válido.
     *
     * @param dni El DNI a verificar.
     * @return true si el DNI es válido, false en caso contrario.
     */
    public boolean verificarDNI(String dni) {
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
    public boolean verificarCIF(String cif) {
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
        int suma = 0;

        for (int i = 2; i <= 8; i++) {
            int digito = Character.getNumericValue(cif.charAt(i - 1));
            if (i % 2 == 0) {
                digito *= 2;
                digito = digito < 10 ? digito : digito - 9;
            }
            suma += digito;
        }

        int resto = suma % 10;
        int resultado = resto == 0 ? 0 : 10 - resto;

        return digitoControl == resultado;
    }

    /**
     * Valida si una hora tiene un formato válido.
     *
     * @param hora La hora en formato de cadena.
     * @return true si el formato es válido, false en caso contrario.
     */
    public boolean validarHora(String hora) {
        if (!hora.matches("^([0-9]{2}):([0-9]{2})$")) {
            return false;
        }
        int horaEntera = Integer.parseInt(hora.substring(0, 2));
        int minutoEntero = Integer.parseInt(hora.substring(3, 5));

        return horaEntera >= 0 && horaEntera <= 23 && minutoEntero >= 0 && minutoEntero <= 59;
    }
}
