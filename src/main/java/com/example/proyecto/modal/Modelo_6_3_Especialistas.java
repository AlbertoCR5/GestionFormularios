package com.example.proyecto.modal;

import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.MessageManager;

import java.util.List;

/**
 * Clase que representa el modelo 6.3 para especialistas en el proceso de escrutinio.
 * Extiende de Preaviso y añade propiedades específicas.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class Modelo_6_3_Especialistas extends Preaviso {

    private List<Candidatura_Comite> candidaturasEspecialistas;
    private String[] incidencias;

    /**
     * Constructor vacío.
     */
    public Modelo_6_3_Especialistas() {
    }

    /**
     * Constructor con nombre de empresa y CIF.
     *
     * @param nombreEmpresa El nombre de la empresa.
     * @param CIF El CIF de la empresa.
     * @throws CumplimentarPDFException Si el CIF es inválido.
     */
    public Modelo_6_3_Especialistas(String nombreEmpresa, String CIF) throws CumplimentarPDFException {
        super(nombreEmpresa, CIF);
    }

    /**
     * Constructor con todos los parámetros.
     *
     * @param nombreEmpresa El nombre de la empresa.
     * @param CIF El CIF de la empresa.
     * @param candidaturasEspecialistas La lista de candidaturas de especialistas.
     * @param incidencias Las incidencias ocurridas.
     * @throws CumplimentarPDFException Si algún dato es incorrecto.
     */
    public Modelo_6_3_Especialistas(String nombreEmpresa, String CIF, List<Candidatura_Comite> candidaturasEspecialistas, String[] incidencias) throws CumplimentarPDFException {
        super(nombreEmpresa, CIF);
        this.candidaturasEspecialistas = candidaturasEspecialistas;
        this.incidencias = incidencias;
    }

    /**
     * Obtiene la lista de candidaturas de especialistas.
     *
     * @return La lista de candidaturas de especialistas.
     */
    public List<Candidatura_Comite> getCandidaturasEspecialistas() {
        return candidaturasEspecialistas;
    }

    /**
     * Establece la lista de candidaturas de especialistas.
     *
     * @param candidaturasEspecialistas La lista de candidaturas de especialistas.
     */
    public void setCandidaturasEspecialistas(List<Candidatura_Comite> candidaturasEspecialistas) {
        this.candidaturasEspecialistas = candidaturasEspecialistas;
    }

    /**
     * Obtiene las incidencias ocurridas.
     *
     * @return Las incidencias.
     */
    public String[] getIncidencias() {
        return incidencias;
    }

    /**
     * Establece las incidencias ocurridas.
     *
     * @param incidencias Las incidencias.
     */
    public void setIncidencias(String[] incidencias) {
        this.incidencias = incidencias;
    }

    /**
     * Verifica que todos los campos requeridos estén completos.
     *
     * @throws CumplimentarPDFException Si algún campo obligatorio está vacío.
     */
    public void validarCampos() throws CumplimentarPDFException {
        if (getNombreEmpresa() == null || getNombreEmpresa().isEmpty()) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.nombreEmpresa.vacio"));
        }
        if (getCIF() == null || getCIF().isEmpty()) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.cif.vacio"));
        }
        if (candidaturasEspecialistas == null || candidaturasEspecialistas.isEmpty()) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.candidaturasEspecialistas.vacio"));
        }
    }

    @Override
    public String toString() {
        return STR."Modelo_6_3_Especialistas \{getNombreEmpresa()} Cumplimentado";
    }
}
