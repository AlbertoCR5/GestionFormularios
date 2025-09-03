package com.albertocr.gestionformularios.util;

import com.albertocr.gestionformularios.model.Eleccion;
import com.albertocr.gestionformularios.model.Empresa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio para gestionar el registro de procesos electorales.
 * <p>
 * Se encarga de crear la estructura de directorios necesaria para una elección
 * y de orquestar la generación de los documentos PDF correspondientes.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.5
 */
public class Registro {

    private static final Logger logger = LoggerFactory.getLogger(Registro.class);
    private final Path rutaElecciones;

    /**
     * Constructor del servicio de registro.
     *
     * @throws IOException Si ocurre un error al crear el directorio raíz de elecciones.
     */
    public Registro() throws IOException {
        this.rutaElecciones = DirectorioManager.crearDirectorioRaiz();
    }

    /**
     * Registra un nuevo preaviso para delegados (menos de 50 trabajadores).
     * Crea los directorios y todos los PDFs necesarios.
     *
     * @param empresa  La entidad {@link Empresa} con los datos.
     * @param eleccion La entidad {@link Eleccion} con los datos.
     * @throws IOException Si ocurre un error de entrada/salida durante la creación de archivos o directorios.
     */
    public void registrarNuevoPreavisoDelegado(Empresa empresa, Eleccion eleccion) throws IOException {
        procesarFormulariosParaTipo(empresa, eleccion, "Delegados");
    }

    /**
     * Registra un nuevo preaviso para comité (50 o más trabajadores).
     *
     * @param empresa  La entidad {@link Empresa}.
     * @param eleccion La entidad {@link Eleccion}.
     * @throws IOException Si ocurre un error de I/O.
     */
    public void registrarNuevoPreavisoComite(Empresa empresa, Eleccion eleccion) throws IOException {
        procesarFormulariosParaTipo(empresa, eleccion, "Comite");
    }

    /**
     * Procesa los formularios PDF para un tipo de elección específico (Delegados o Comité).
     * <p>
     * Este método crea el directorio de la empresa, copia las plantillas de PDF, y luego
     * rellena cada formulario con los datos proporcionados.
     *
     * @param empresa El objeto {@link Empresa} con los datos de la empresa.
     * @param eleccion El objeto {@link Eleccion} con los datos de la elección.
     * @param carpetaRecurso El nombre de la carpeta de recursos que contiene las plantillas (p. ej., "Delegados").
     * @throws IOException Si ocurre un error de entrada/salida durante la creación de archivos o directorios.
     */
    private void procesarFormulariosParaTipo(Empresa empresa, Eleccion eleccion, String carpetaRecurso) throws IOException {
        try {
            // Crear el directorio específico para la empresa dentro del directorio raíz
            Path rutaDirectorioEmpresa = DirectorioManager.crearDirectorioEmpresa(rutaElecciones, empresa.getNombre());

            // Copiar los archivos PDF de la plantilla al nuevo directorio
            DirectorioManager.copiarRecursosADirectorio(rutaDirectorioEmpresa.toString(), carpetaRecurso);

            // Obtener las rutas de todos los archivos PDF copiados
            String[] rutasFormularios = DirectorioManager.generarRutasFormularios(rutaDirectorioEmpresa);

            // Procesar cada formulario individualmente
            for (String rutaFormularioPDF : rutasFormularios) {
                try {
                    String nombreArchivo = new File(rutaFormularioPDF).getName();

                    // Mapear los campos del PDF a los datos del modelo
                    Map<String, String> campos = crearMapaCamposPDF(empresa, eleccion);

                    // Generar el PDF
                    if (nombreArchivo.contains(Constantes.PREAVISO)) {
                        CumplimentarPreavisoPDF.cumplimentarYGuardar(campos, rutaFormularioPDF);
                        logger.info("Procesado: {}", Constantes.PREAVISO);
                    } else if (nombreArchivo.contains(Constantes.CALENDARIO_DELEGADOS)) {
                        // Lógica para el calendario de delegados.
                        // CumplimentarCalendarioDelegadosPDF.cumplimentarYGuardar(campos, rutaFormularioPDF);
                        logger.info("Procesado: {}", Constantes.CALENDARIO_DELEGADOS);
                    } else if (nombreArchivo.contains(Constantes.MODELO_3)) {
                        // Lógica para el modelo 3.
                        // CumplimentarModelo3PDF.cumplimentarYGuardar(campos, rutaFormularioPDF);
                        logger.info("Procesado: {}", Constantes.MODELO_3);
                    }
                    // Se pueden añadir más 'else if' para otros formularios
                } catch (Exception e) {
                    // Loguear el error y continuar con el siguiente documento
                    logger.error("Error al procesar el formulario '{}'", rutaFormularioPDF, e);
                }
            }
            logger.info("Proceso de registro para {} de la empresa '{}' completado.", carpetaRecurso, empresa.getNombre());
            AlertManager.mostrarAlertaInformacion("Éxito", "PDFs generados correctamente en el directorio de la empresa.");
        } catch (IOException e) {
            logger.error("Error general al registrar la elección para la empresa '{}'", empresa.getNombre(), e);
            AlertManager.mostrarAlertaError("Error de I/O", "Ocurrió un error de entrada/salida al procesar los archivos.");
            throw e;
        }
    }

    /**
     * Crea un mapa de campos a partir de los objetos Empresa y Eleccion.
     *
     * @param empresa La entidad {@link Empresa}.
     * @param eleccion La entidad {@link Eleccion}.
     * @return Un mapa con los nombres de los campos del PDF y sus valores.
     */
    private Map<String, String> crearMapaCamposPDF(Empresa empresa, Eleccion eleccion) {
        Map<String, String> campos = new HashMap<>();
        campos.put("nombreEmpresa", empresa.getNombre());
        campos.put("CIF", empresa.getCif());
        campos.put("nombreComercial", empresa.getNombreComercial());
        campos.put("nombreCentro", empresa.getNombreCentro());
        campos.put("direccion", empresa.getDireccion());
        campos.put("municipio", empresa.getMunicipio());
        campos.put("comarca", empresa.getComarca());
        campos.put("provincia", empresa.getProvincia());
        campos.put("codigoPostal", empresa.getCodigoPostal());
        campos.put("totalTrabajadores", String.valueOf(eleccion.getNumeroTrabajadores()));
        campos.put("numeroISS", empresa.getNumeroISS());
        campos.put("mesElecciones", String.valueOf(eleccion.getFechaConstitucion().getMonthValue()));
        campos.put("anioElecciones", String.valueOf(eleccion.getFechaConstitucion().getYear()));

        // Asignación correcta para el grupo de radio buttons
        if ("TOTAL".equals(eleccion.getTipoEleccion())) {
            campos.put("tipoEleccion", "TOTAL");
        } else if ("PARCIAL".equals(eleccion.getTipoEleccion())) {
            campos.put("tipoEleccion", "PARCIAL");
        } else {
            campos.put("tipoEleccion", "Off");
        }

        campos.put("promotores", eleccion.getPromotores());
        // `parentesisDer` y `parentesisIzq` son literales en el PDF, no necesitan ser mapeados en la lógica.
        campos.put("diaPreaviso", String.valueOf(eleccion.getFecha().getDayOfMonth()));
        campos.put("mesPreaviso", ConversorFechaToLetras.getMes(eleccion.getFecha().getMonthValue()));
        campos.put("anioPreaviso", String.valueOf(eleccion.getFecha().getYear()));
        campos.put("fechaConstitucionLetras", ConversorFechaToLetras.convertirFechaEnLetras(eleccion.getFechaConstitucion()));
        campos.put("fechaConstitucion", eleccion.getFechaConstitucion().toString());
        campos.put("Comarca", empresa.getComarca());
        return campos;
    }
}
