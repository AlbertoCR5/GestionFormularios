package com.albertocr.gestionformularios.service;

import com.albertocr.gestionformularios.model.TipoEleccion;

/**
 * DTO para transportar información sobre la configuración de una elección anterior.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.0
 */
public class DatosEleccionAnterior {

    private final TipoEleccion tipoEleccion;
    private final String colegioUtilizado;

    public DatosEleccionAnterior(TipoEleccion tipoEleccion, String colegioUtilizado) {
        this.tipoEleccion = tipoEleccion;
        this.colegioUtilizado = colegioUtilizado;
    }

    public TipoEleccion getTipoEleccion() {
        return tipoEleccion;
    }

    public String getColegioUtilizado() {
        return colegioUtilizado;
    }

    /**
     * Devuelve la opción de configuración de colegio contraria a la utilizada anteriormente.
     * Esto se usa para pre-seleccionar la opción más probable en el diálogo de selección.
     *
     * @return "Colegio Único" o "Especialistas y Técnicos/Administrativos".
     */
    public String getOpcionColegioContraria() {
        if ("Colegio Único".equalsIgnoreCase(colegioUtilizado)) {
            return "Especialistas y Técnicos/Administrativos";
        } else {
            return "Colegio Único";
        }
    }
}
