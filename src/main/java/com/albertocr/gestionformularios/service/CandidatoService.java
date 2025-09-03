package com.albertocr.gestionformularios.service;

import com.albertocr.gestionformularios.model.Candidato;
import com.albertocr.gestionformularios.model.CandidatosDAO;

import java.util.List;

/**
 * Servicio para manejar la lógica de negocio de los candidatos.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.0
 */
public class CandidatoService {

    private final CandidatosDAO candidatosDAO;

    public CandidatoService(CandidatosDAO candidatosDAO) {
        this.candidatosDAO = candidatosDAO;
    }

    /**
     * Obtiene todos los candidatos de la base de datos.
     *
     * @return Una lista de todos los candidatos.
     */
    public List<Candidato> obtenerTodosLosCandidatos() {
        return candidatosDAO.buscarTodos();
    }

    /**
     * Guarda un nuevo candidato o actualiza uno existente.
     *
     * @param candidato El candidato a guardar o actualizar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public boolean guardarOActualizarCandidato(Candidato candidato) {
        // Aquí se podría añadir lógica de negocio adicional, como validaciones complejas.
        return candidatosDAO.guardarOActualizar(candidato);
    }

    /**
     * Elimina un candidato por su DNI.
     *
     * @param dni El DNI del candidato a eliminar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public boolean eliminarCandidatoPorDni(String dni) {
        return candidatosDAO.eliminarPorDni(dni);
    }
}
