package com.albertocr.gestionformularios.service;

import com.albertocr.gestionformularios.model.Eleccion;
import com.albertocr.gestionformularios.model.EleccionParaCalendario;
import com.albertocr.gestionformularios.model.EleccionesDAO;
import com.albertocr.gestionformularios.model.Empresa;
import com.albertocr.gestionformularios.model.EmpresaDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Servicio para manejar la lógica de negocio del calendario de comité.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.0
 */
public class CalendarioService {

    private static final Logger logger = LoggerFactory.getLogger(CalendarioService.class);

    private final EmpresaDAO empresaDAO;
    private final EleccionesDAO eleccionesDAO;

    public CalendarioService(EmpresaDAO empresaDAO, EleccionesDAO eleccionesDAO) {
        this.empresaDAO = empresaDAO;
        this.eleccionesDAO = eleccionesDAO;
    }

    /**
     * Carga todas las elecciones y las prepara para la vista del calendario de forma eficiente.
     *
     * @return Una lista de DTOs {@link EleccionParaCalendario}.
     */
    public List<EleccionParaCalendario> obtenerEleccionesParaCalendario() {
        logger.info("Iniciando carga optimizada de elecciones para el calendario.");

        // 1. Obtener todos los datos necesarios con el mínimo de consultas
        List<Eleccion> todasLasElecciones = eleccionesDAO.buscarTodas();
        List<Empresa> todasLasEmpresas = empresaDAO.buscarTodas();

        // 2. Crear un mapa de empresas por ID para una búsqueda rápida en memoria
        Map<Integer, Empresa> mapaEmpresas = todasLasEmpresas.stream()
                .collect(Collectors.toMap(Empresa::getId, Function.identity()));

        // 3. Combinar los datos en una lista de DTOs
        List<EleccionParaCalendario> dtos = new ArrayList<>();
        for (Eleccion eleccion : todasLasElecciones) {
            Empresa empresaAsociada = mapaEmpresas.get(eleccion.getIdEmpresa());
            if (empresaAsociada != null) {
                dtos.add(new EleccionParaCalendario(
                        eleccion.getId(),
                        empresaAsociada.getId(),
                        empresaAsociada.getNombre(),
                        empresaAsociada.getCif(),
                        empresaAsociada.getMunicipio(), // Asumiendo que getMunicipio es la localidad
                        eleccion.getNumeroTrabajadores(),
                        eleccion.getFechaConstitucion()
                ));
            } else {
                logger.warn("No se encontró la empresa con ID {} para la elección con ID {}.", eleccion.getIdEmpresa(), eleccion.getId());
            }
        }

        logger.info("Carga finalizada. Se prepararon {} elecciones para la vista.", dtos.size());
        return dtos;
    }

    // Aquí se añadiría la lógica para generar el PDF, similar a como se hizo en PreavisoService
    // public void generarPdfCalendario(...) { ... }
}
