package com.albertocr.gestionformularios.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.albertocr.gestionformularios.util.Constantes;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;

/**
 * Clase de servicio encargada de la generación de documentos PDF para el proceso electoral.
 * Gestiona la lógica para crear los documentos necesarios según el tamaño de la empresa
 * y rellena los campos a partir de un mapa de datos.
 */
public class GeneradorDocumentosService {

    private static final Logger LOGGER = Logger.getLogger(GeneradorDocumentosService.class.getName());

    /**
     * Genera el preaviso y toda la documentación electoral asociada en función del número de trabajadores de la empresa.
     * <p>
     * Este método determina si se debe generar la documentación para delegados (menos de 50 trabajadores)
     * o para el comité de empresa (50 o más trabajadores). Luego, itera sobre la lista de documentos
     * correspondiente definida en la clase {@code Constantes} y genera cada PDF en la ruta de destino especificada.
     *
     * @param empresa La entidad Empresa con los datos necesarios, incluido el total de trabajadores.
     * @param carpetaDestino La ruta (Path) del directorio donde se guardarán todos los documentos generados.
     * @throws IOException Si ocurre un error de entrada/salida durante la manipulación de los archivos PDF.
     */
    package com.albertocr.gestionformularios.service;

import com.albertocr.gestionformularios.model.Eleccion;
import com.albertocr.gestionformularios.model.Empresa;
import com.albertocr.gestionformularios.model.TipoEleccion;
import com.albertocr.gestionformularios.util.Constantes;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase de servicio encargada de la generación de documentos PDF para el proceso electoral.
 * Gestiona la lógica para crear los documentos necesarios según el tamaño de la empresa
 * y rellena los campos a partir de un mapa de datos.
 */
public class GeneradorDocumentosService {

    private static final Logger LOGGER = Logger.getLogger(GeneradorDocumentosService.class.getName());

    /**
     * Genera el preaviso y toda la documentación electoral asociada en función del número de trabajadores de la empresa.
     * <p>
     * Este método determina si se debe generar la documentación para delegados (menos de 50 trabajadores)
     * o para el comité de empresa (50 o más trabajadores). Luego, itera sobre la lista de documentos
     * correspondiente definida en la clase {@code Constantes} y genera cada PDF en la ruta de destino especificada.
     *
     * @param empresa La entidad Empresa con los datos necesarios, incluido el total de trabajadores.
     * @param carpetaDestino La ruta (Path) del directorio donde se guardarán todos los documentos generados.
     * @throws IOException Si ocurre un error de entrada/salida durante la manipulación de los archivos PDF.
     */
    public void generarPreavisoYDocumentacionAsociada(Empresa empresa, Path carpetaDestino) throws IOException {
        // 1. Determinar qué conjunto de documentos y plantillas utilizar.
        final String[] documentosAGenerar;
        final String carpetaPlantillas;

        if (empresa.getTotalTrabajadores() < 50) {
            documentosAGenerar = Constantes.DOCUMENTACION_DELEGADOS;
            carpetaPlantillas = "Delegados";
            LOGGER.log(Level.INFO, "Generando documentación para Delegados (empresa con {0} trabajadores).", empresa.getTotalTrabajadores());
        } else {
            documentosAGenerar = Constantes.DOCUMENTACION_COMITE;
            carpetaPlantillas = "Comite";
            LOGGER.log(Level.INFO, "Generando documentación para Comité de Empresa (empresa con {0} trabajadores).", empresa.getTotalTrabajadores());
        }

        // 2. Crear el mapa de campos que se reutilizará para todos los documentos.
        Map<String, String> mapaCampos = crearMapaCamposPreavisoPDF(empresa);

        // 3. Iterar sobre la lista de documentos y generar cada uno.
        for (String nombreDocumento : documentosAGenerar) {
            try {
                // CORRECCIÓN: Apuntar a las carpetas correctas 'Comite' o 'Delegados'
                Path rutaPlantilla = Path.of("src/main/resources/", carpetaPlantillas, nombreDocumento + ".pdf");
                Path rutaSalida = carpetaDestino.resolve(nombreDocumento + ".pdf");

                LOGGER.log(Level.INFO, "Generando documento: {0}", rutaSalida);

                PDDocument pdfDocument = Loader.loadPDF(rutaPlantilla.toFile());
                PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();

                if (acroForm == null) {
                    LOGGER.log(Level.WARNING, "El documento {0} no contiene un formulario AcroForm.", nombreDocumento);
                    pdfDocument.close();
                    continue;
                }

                for (Map.Entry<String, String> entry : mapaCampos.entrySet()) {
                    if (acroForm.getField(entry.getKey()) != null) {
                        acroForm.getField(entry.getKey()).setValue(entry.getValue());
                    }
                }

                acroForm.flatten();
                pdfDocument.save(rutaSalida.toFile());
                pdfDocument.close();

            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error al generar el documento PDF: " + nombreDocumento, e);
                throw new IOException("Fallo al procesar el archivo " + nombreDocumento, e);
            }
        }
        LOGGER.log(Level.INFO, "Todos los documentos han sido generados exitosamente en: {0}", carpetaDestino);
    }

    /**
     * Genera un conjunto de documentos de escrutinio (actas, anexos, etc.) a partir de una lista de modelos.
     *
     * @param eleccion La entidad Eleccion con los datos del proceso electoral.
     * @param carpetaDestino La ruta (Path) del directorio donde se guardarán los documentos.
     * @param modelosAGenerar Un array de Strings con los nombres de los modelos de documento a generar.
     * @throws IOException Si ocurre un error de entrada/salida durante la manipulación de los archivos.
     */
    public void generarDocumentosEscrutinio(Eleccion eleccion, Path carpetaDestino, String[] modelosAGenerar) throws IOException {
        final String carpetaPlantillas = eleccion.getTipoEleccion() == TipoEleccion.COMITE ? "Comite" : "Delegados";
        LOGGER.log(Level.INFO, "Iniciando generación de documentos de escrutinio para {0}", eleccion.getEmpresa().getNombre());

        Map<String, String> mapaCampos = crearMapaCamposEscrutinioPDF(eleccion);

        for (String nombreDocumento : modelosAGenerar) {
            try {
                Path rutaPlantilla = Path.of("src/main/resources/", carpetaPlantillas, nombreDocumento + ".pdf");
                Path rutaSalida = carpetaDestino.resolve(nombreDocumento + ".pdf");

                LOGGER.log(Level.INFO, "Generando documento de escrutinio: {0}", rutaSalida);

                PDDocument pdfDocument = Loader.loadPDF(rutaPlantilla.toFile());
                PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();

                if (acroForm == null) {
                    LOGGER.log(Level.WARNING, "El documento {0} no contiene un formulario AcroForm.", nombreDocumento);
                    pdfDocument.close();
                    continue;
                }

                for (Map.Entry<String, String> entry : mapaCampos.entrySet()) {
                    if (acroForm.getField(entry.getKey()) != null) {
                        acroForm.getField(entry.getKey()).setValue(entry.getValue());
                    }
                }

                acroForm.flatten();
                pdfDocument.save(rutaSalida.toFile());
                pdfDocument.close();

            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error al generar el documento de escrutinio: " + nombreDocumento, e);
                throw new IOException("Fallo al procesar el archivo de escrutinio " + nombreDocumento, e);
            }
        }
        LOGGER.log(Level.INFO, "Documentos de escrutinio generados exitosamente en: {0}", carpetaDestino);
    }

    private Map<String, String> crearMapaCamposPreavisoPDF(Empresa empresa) {
        // Lógica para crear mapa de campos para el preaviso
        return java.util.Collections.emptyMap();
    }

    private Map<String, String> crearMapaCamposEscrutinioPDF(Eleccion eleccion) {
        // Lógica para crear mapa de campos para el escrutinio
        return java.util.Collections.emptyMap();
    }
}

        // 1. Determinar qué conjunto de documentos utilizar según el número de trabajadores.
        final String[] documentosAGenerar;

        if (empresa.getTotalTrabajadores() < 50) {
            documentosAGenerar = Constantes.DOCUMENTACION_DELEGADOS;
            LOGGER.log(Level.INFO, "Generando documentación para Delegados (empresa con {0} trabajadores).", empresa.getTotalTrabajadores());
        } else {
            documentosAGenerar = Constantes.DOCUMENTACION_COMITE;
            LOGGER.log(Level.INFO, "Generando documentación para Comité de Empresa (empresa con {0} trabajadores).", empresa.getTotalTrabajadores());
        }

        // 2. Crear el mapa de campos que se reutilizará para todos los documentos.
        // Se asume que existe un método que prepara todos los datos necesarios.
        Map<String, String> mapaCampos = crearMapaCamposPDF(empresa);

        // 3. Iterar sobre la lista de documentos y generar cada uno.
        for (String nombreDocumento : documentosAGenerar) {
            try {
                // Construye la ruta de la plantilla y la ruta del archivo de salida.
                Path rutaPlantilla = Path.of("src/main/resources/plantillas/", nombreDocumento + ".pdf");
                Path rutaSalida = carpetaDestino.resolve(nombreDocumento + ".pdf");

                LOGGER.log(Level.INFO, "Generando documento: {0}", rutaSalida);

                // Cargar la plantilla PDF.
                PDDocument pdfDocument = Loader.loadPDF(rutaPlantilla.toFile());
                PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();

                if (acroForm == null) {
                    LOGGER.log(Level.WARNING, "El documento {0} no contiene un formulario AcroForm.", nombreDocumento);
                    pdfDocument.close();
                    continue; // Saltar al siguiente documento
                }

                // Rellenar los campos del formulario.
                for (Map.Entry<String, String> entry : mapaCampos.entrySet()) {
                    // Solo intentar rellenar si el campo existe en el PDF
                    if (acroForm.getField(entry.getKey()) != null) {
                        acroForm.getField(entry.getKey()).setValue(entry.getValue());
                    }
                }

                // Opcional: Aplanar el PDF para que los campos no sean editables (recomendado).
                acroForm.flatten();

                // Guardar el nuevo PDF en la carpeta de destino.
                pdfDocument.save(rutaSalida.toFile());
                pdfDocument.close();

            } catch (IOException e) {
                // Loguear el error con contexto claro y propagar la excepción.
                LOGGER.log(Level.SEVERE, "Error al generar el documento PDF: " + nombreDocumento, e);
                // Propagar la excepción para que la capa superior (UI) pueda notificar al usuario.
                throw new IOException("Fallo al procesar el archivo " + nombreDocumento, e);
            }
        }

        LOGGER.log(Level.INFO, "Todos los documentos han sido generados exitosamente en: {0}", carpetaDestino);
    }

    /**
     * Crea un mapa con los campos y valores que se usarán para rellenar los formularios PDF.
     * <p>
     * Este método centraliza la recolección de datos de la empresa y los formatea
     * en un mapa clave-valor, donde la clave es el nombre del campo en el PDF.
     *
     * @param empresa La entidad Empresa de la que se extraen los datos.
     * @return Un {@code Map<String, String>} con los datos listos para ser insertados en un PDF.
     */
    private Map<String, String> crearMapaCamposPDF(Empresa empresa) {
        // Aquí iría la lógica existente para crear el mapa de campos.
        // Ejemplo:
        // Map<String, String> campos = new HashMap<>();
        // campos.put("nombre_empresa", empresa.getNombre());
        // campos.put("cif_empresa", empresa.getCif());
        // ... resto de campos
        // return campos;

        // De momento, retornamos un mapa vacío como placeholder.
        return java.util.Collections.emptyMap();
    }

    // Clases Mock para que el código sea compilable y comprensible.
    // Deberás reemplazarlas con las tuyas.
    private static class Empresa {
        public int getTotalTrabajadores() { return 60; /* O 40 para probar el otro caso */ }
    }
}