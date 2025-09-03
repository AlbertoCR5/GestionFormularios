package com.albertocr.gestionformularios.model;

/**
 * Enumera los tipos de colegios electorales para las elecciones a comité de empresa.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.1
 */
public enum TipoColegioElectoral {
    /**
     * No aplica, para elecciones a delegados de personal (&lt; 50 trabajadores).
     */
    NO_APLICA,
    /**
     * Un único colegio electoral.
     */
    UNICO,
    /**
     * Dos colegios: Técnicos y Administrativos, y Especialistas y No Cualificados.
     */
    DOS_COLEGIOS,
    /**
     * Opción para generar toda la documentación del comité sin filtrar.
     */
    TODOS
}
