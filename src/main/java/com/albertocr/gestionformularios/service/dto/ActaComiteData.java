package com.albertocr.gestionformularios.service.dto;

import com.albertocr.gestionformularios.model.Candidato;

import java.util.List;

/**
 * DTO que encapsula todos los datos necesarios para el acta de escrutinio de comités.
 *
 * @param tipoColegio "Colegio Único" o "Especialistas y Técnicos/Administrativos".
 */
public record ActaComiteData(
        String numeroPreaviso,
        String nombreEmpresa,
        String nombreComercial,
        String cif,
        String actividadEconomica,
        String nombreConvenio,
        String numeroConvenio,
        String nombreCentro,
        String direccionCentro,
        String municipioCentro,
        String provincia,
        String fechaConstitucionLetras,
        int trabajadoresFijos,
        int trabajadoresEventuales,
        int trabajadoresJornadas,
        String tipoColegio, // Campo clave para esta ventana
        List<Candidato> candidatos
) {
}
