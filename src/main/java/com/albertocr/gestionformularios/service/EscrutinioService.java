package com.albertocr.gestionformularios.service;

import com.albertocr.gestionformularios.model.*;
import com.albertocr.gestionformularios.service.dto.ActaComiteData;
import com.albertocr.gestionformularios.service.dto.ActaDelegadosData;
import com.albertocr.gestionformularios.util.CumplimentarEscrutinioPDF;
import com.albertocr.gestionformularios.util.DirectorioManager;
import com.albertocr.gestionformularios.util.LectorPDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EscrutinioService {

    private static final Logger logger = LoggerFactory.getLogger(EscrutinioService.class);

    private final EmpresaDAO empresaDAO;
    private final EleccionesDAO eleccionesDAO;
    private final CandidatosDAO candidatosDAO;

    public EscrutinioService(EmpresaDAO empresaDAO, EleccionesDAO eleccionesDAO, CandidatosDAO candidatosDAO) {
        this.empresaDAO = empresaDAO;
        this.eleccionesDAO = eleccionesDAO;
        this.candidatosDAO = candidatosDAO;
    }

    public List<EleccionParaEscrutinio> obtenerEleccionesParaEscrutinio() {
        logger.info("Iniciando carga optimizada de elecciones para el escrutinio.");
        List<Eleccion> todasLasElecciones = eleccionesDAO.buscarTodas();
        Map<Integer, Empresa> mapaEmpresas = empresaDAO.buscarTodas().stream()
                .collect(Collectors.toMap(Empresa::getId, Function.identity()));

        List<EleccionParaEscrutinio> dtos = new ArrayList<>();
        for (Eleccion eleccion : todasLasElecciones) {
            Empresa empresaAsociada = mapaEmpresas.get(eleccion.getIdEmpresa());
            if (empresaAsociada != null) {
                dtos.add(new EleccionParaEscrutinio(eleccion, empresaAsociada));
            }
        }
        return dtos;
    }

    public DatosEleccionAnterior obtenerDatosEleccionAnterior(Empresa empresa) {
        Optional<Eleccion> ultimaEleccionOpt = eleccionesDAO.buscarUltimaEleccionPorEmpresa(empresa.getId());
        if (ultimaEleccionOpt.isEmpty()) {
            logger.warn("No se encontró ninguna elección anterior para la empresa: {}", empresa.getNombre());
            return null;
        }
        Eleccion ultimaEleccion = ultimaEleccionOpt.get();
        return (ultimaEleccion.getNumeroTrabajadores() < 50)
                ? new DatosEleccionAnterior(TipoEleccion.DELEGADOS, null)
                : new DatosEleccionAnterior(TipoEleccion.COMITE, determinarColegioPorFicheros(empresa));
    }

    public ActaDelegadosData prepararDatosActaDelegados(EleccionParaEscrutinio eleccionData) throws IOException {
        Empresa empresa = eleccionData.empresa();
        Eleccion eleccion = eleccionData.eleccion();
        String nombreSanitizado = DirectorioManager.sanitizarNombre(empresa.getNombre());
        String nombreFichero = "preaviso " + nombreSanitizado + ".pdf";
        Path rutaPreaviso = DirectorioManager.crearDirectorioRaiz().resolve(nombreSanitizado).resolve(nombreFichero);

        String nombreEmpresaPdf = LectorPDF.leerCampo(rutaPreaviso.toString(), "nombreEmpresa");
        String cifPdf = LectorPDF.leerCampo(rutaPreaviso.toString(), "CIF");
        String nombreComercialPdf = LectorPDF.leerCampo(rutaPreaviso.toString(), "nombreComercial");
        String nombreCentroPdf = LectorPDF.leerCampo(rutaPreaviso.toString(), "nombreCentro");
        String direccionPdf = LectorPDF.leerCampo(rutaPreaviso.toString(), "direccion");
        String municipioPdf = LectorPDF.leerCampo(rutaPreaviso.toString(), "municipio");
        String provinciaPdf = LectorPDF.leerCampo(rutaPreaviso.toString(), "provincia");
        String fechaConstitucionLetrasPdf = LectorPDF.leerCampo(rutaPreaviso.toString(), "fechaConstitucionLetras");

        return new ActaDelegadosData(
                String.valueOf(eleccion.getId()),
                !nombreEmpresaPdf.isBlank() ? nombreEmpresaPdf : empresa.getNombre(),
                nombreComercialPdf,
                !cifPdf.isBlank() ? cifPdf : empresa.getCif(),
                empresa.getActividad(),
                empresa.getConvenio(),
                "",
                nombreCentroPdf,
                direccionPdf,
                municipioPdf,
                provinciaPdf,
                fechaConstitucionLetrasPdf,
                eleccion.getNumeroTrabajadores(),
                0, 0, 0, new ArrayList<>()
        );
    }

    public ActaComiteData prepararDatosActaComite(EleccionParaEscrutinio eleccionData, String tipoColegio) throws IOException {
        logger.info("Preparando datos para el acta de comité. Tipo de colegio: {}", tipoColegio);
        Empresa empresa = eleccionData.empresa();
        Eleccion eleccion = eleccionData.eleccion();

        String nombreSanitizado = DirectorioManager.sanitizarNombre(empresa.getNombre());
        String nombreFichero = "preaviso " + nombreSanitizado + ".pdf";
        Path rutaPreaviso = DirectorioManager.crearDirectorioRaiz().resolve(nombreSanitizado).resolve(nombreFichero);

        String nombreComercial = LectorPDF.leerCampo(rutaPreaviso.toString(), "nombreComercial");
        String nombreCentro = LectorPDF.leerCampo(rutaPreaviso.toString(), "nombreCentro");
        String direccion = LectorPDF.leerCampo(rutaPreaviso.toString(), "direccion");
        String municipio = LectorPDF.leerCampo(rutaPreaviso.toString(), "municipio");
        String provincia = LectorPDF.leerCampo(rutaPreaviso.toString(), "provincia");
        String fechaConstitucion = LectorPDF.leerCampo(rutaPreaviso.toString(), "fechaConstitucionLetras");

        return new ActaComiteData(
                String.valueOf(eleccion.getId()),
                empresa.getNombre(),
                nombreComercial,
                empresa.getCif(),
                empresa.getActividad(),
                empresa.getConvenio(),
                "", // numConvenio
                nombreCentro,
                direccion,
                municipio,
                provincia,
                fechaConstitucion,
                eleccion.getNumeroTrabajadores(),
                0, // eventuales
                0, // jornadas
                tipoColegio,
                new ArrayList<>()
        );
    }

    public void generarActaEscrutinioDelegados(ActaDelegadosData datosActa, File directorioDestino) throws IOException {
        logger.info("Iniciando generación de documentos de escrutinio para delegados.");
        int eleccionId = Integer.parseInt(datosActa.numeroPreaviso());
        Eleccion eleccion = eleccionesDAO.buscarTodas().stream().filter(e -> e.getId() == eleccionId).findFirst()
                .orElseThrow(() -> new IOException("No se pudo encontrar la elección con ID: " + eleccionId));
        Empresa empresa = empresaDAO.buscarTodas().stream().filter(em -> em.getId() == eleccion.getIdEmpresa()).findFirst()
                .orElseThrow(() -> new IOException("No se pudo encontrar la empresa con ID: " + eleccion.getIdEmpresa()));

        EscrutinioData escrutinioData = new EscrutinioData();
        // TODO: Mapear campos de datosActa a escrutinioData si es necesario

        String nombreSanitizado = DirectorioManager.sanitizarNombre(empresa.getNombre());
        String rutaModelo5_1 = new File(directorioDestino, nombreSanitizado + "_Modelo5_1.pdf").getAbsolutePath();
        CumplimentarEscrutinioPDF.cumplimentarModelo5_1(empresa, eleccion, datosActa.candidatos(), escrutinioData, rutaModelo5_1);
        logger.info("Documentos de escrutinio para delegados generados con éxito.");
    }

    private String determinarColegioPorFicheros(Empresa empresa) {
        try {
            Path rutaRaiz = DirectorioManager.crearDirectorioRaiz();
            Path rutaEmpresa = DirectorioManager.crearDirectorioEmpresa(rutaRaiz, empresa.getNombre());
            if (Files.exists(rutaEmpresa.resolve("modelo_6_1_Especialistas.pdf"))) return "Especialistas y Técnicos/Administrativos";
        } catch (IOException e) {
            logger.error("Error de E/S al determinar el tipo de colegio.", e);
        }
        return "Colegio Único";
    }
}
