package com.example.proyecto.modal;

import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.MessageManager;

import java.util.List;

/**
 * Clase que representa el modelo 6.2 para técnicos y administrativos en el proceso de escrutinio.
 * Extiende de Preaviso y añade propiedades específicas.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class Modelo_6_2_Tecnicos extends Preaviso {

    private String sindicato;
    private List<Candidato_Comite> candidatosEspecialistas;

    /**
     * Constructor vacío.
     */
    public Modelo_6_2_Tecnicos() {
    }

    /**
     * Constructor con el nombre de la empresa.
     *
     * @param nombreEmpresa El nombre de la empresa.
     */
    public Modelo_6_2_Tecnicos(String nombreEmpresa) {
        super(nombreEmpresa);
    }

    /**
     * Constructor con todos los parámetros.
     *
     * @param nombreEmpresa          El nombre de la empresa.
     * @param sindicato              El sindicato.
     * @param candidatosEspecialistas La lista de candidatos especialistas.
     */
    public Modelo_6_2_Tecnicos(String nombreEmpresa, String sindicato, List<Candidato_Comite> candidatosEspecialistas) {
        super(nombreEmpresa);
        this.sindicato = sindicato;
        this.candidatosEspecialistas = candidatosEspecialistas;
    }

    /**
     * Obtiene el sindicato.
     *
     * @return El sindicato.
     */
    public String getSindicato() {
        return sindicato;
    }

    /**
     * Establece el sindicato.
     *
     * @param sindicato El sindicato.
     */
    public void setSindicato(String sindicato) {
        this.sindicato = sindicato;
    }

    /**
     * Obtiene la lista de candidatos especialistas.
     *
     * @return La lista de candidatos especialistas.
     */
    public List<Candidato_Comite> getCandidatosEspecialistas() {
        return candidatosEspecialistas;
    }

    /**
     * Establece la lista de candidatos especialistas.
     *
     * @param candidatosEspecialistas La lista de candidatos especialistas.
     */
    public void setCandidatosEspecialistas(List<Candidato_Comite> candidatosEspecialistas) {
        this.candidatosEspecialistas = candidatosEspecialistas;
    }

    /**
     * Verifica que todos los campos requeridos estén completos.
     *
     * @throws CumplimentarPDFException Si algún campo obligatorio está vacío.
     */
    public void validarCampos() throws CumplimentarPDFException {
        if (sindicato == null || sindicato.isEmpty()) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.sindicato.vacio"));
        }
        if (candidatosEspecialistas == null || candidatosEspecialistas.isEmpty()) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.candidatosEspecialistas.vacio"));
        }
    }

    @Override
    public String toString() {
        return STR."Modelo_6_2_Técnicos \{getNombreEmpresa()} Cumplimentado";
    }
}
