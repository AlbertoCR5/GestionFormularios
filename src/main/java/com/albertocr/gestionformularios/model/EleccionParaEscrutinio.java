package com.albertocr.gestionformularios.model;

import java.time.format.DateTimeFormatter;

/**
 * DTO (Data Transfer Object) para transportar los datos necesarios para la
 * vista de escrutinio de una forma eficiente.
 *
 * @param eleccion          La entidad Eleccion original.
 * @param empresa           La entidad Empresa original.
 */
public record EleccionParaEscrutinio(Eleccion eleccion, Empresa empresa) {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Representación en texto para mostrar en un ComboBox.
     * @return Una cadena formateada con los datos más relevantes.
     */
    @Override
    public String toString() {
        return String.format("%s (CIF: %s) - Constituida el %s",
                empresa.getNombre(),
                empresa.getCif(),
                eleccion.getFechaConstitucion().format(DATE_FORMATTER));
    }
}
