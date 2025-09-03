package com.albertocr.gestionformularios.service.dto;

import com.albertocr.gestionformularios.model.Candidato;

import java.util.List;

/**
 * DTO (Data Transfer Object) que encapsula todos los datos necesarios
 * para rellenar la ventana y generar el acta de escrutinio de delegados.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.2
 */
public record ActaDelegadosData(
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
        int totalElectores,
        List<Candidato> candidatos // Nuevo campo
) {
}
