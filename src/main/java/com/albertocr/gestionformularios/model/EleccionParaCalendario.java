package com.albertocr.gestionformularios.model;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) para transportar los datos necesarios para la
 * vista del calendario de comité de una forma eficiente.
 *
 * @param eleccionId         El ID de la elección.
 * @param empresaId          El ID de la empresa.
 * @param nombreEmpresa      El nombre de la empresa.
 * @param cifEmpresa         El CIF de la empresa.
 * @param localidadEmpresa   La localidad de la empresa.
 * @param numeroTrabajadores El número de trabajadores de la elección.
 * @param fechaConstitucion  La fecha de constitución de la mesa electoral.
 */
public record EleccionParaCalendario(
        int eleccionId,
        int empresaId,
        String nombreEmpresa,
        String cifEmpresa,
        String localidadEmpresa,
        int numeroTrabajadores,
        LocalDate fechaConstitucion
) {
    /**
     * Representación en texto para mostrar en un ComboBox.
     * @return Una cadena formateada con los datos más relevantes.
     */
    @Override
    public String toString() {
        return String.format("%s (CIF: %s) - Elección del %s",
                nombreEmpresa,
                cifEmpresa,
                fechaConstitucion.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }
}
