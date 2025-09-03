package com.albertocr.gestionformularios.service;

import com.albertocr.gestionformularios.model.Eleccion;
import com.albertocr.gestionformularios.model.Empresa;
import com.albertocr.gestionformularios.util.Constantes;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;

/**
 * Servicio para generar documentos PDF del proceso electoral.
 * <p>
 * Carga plantillas desde el classpath (carpetas resources/Comite y resources/Delegados),
 * rellena campos con datos de negocio y guarda copias en una carpeta destino.
 */
public class GeneradorDocumentosService {

    private static final Logger LOGGER =
            Logger.getLogger(GeneradorDocumentosService.class.getName());

    /**
     * Genera el preaviso y documentación asociada (Delegados o Comité según tamaño de empresa).
     *
     * @param empresa datos de la empresa, incluyendo el total de trabajadores
     * @param carpetaDestino carpeta donde se guardarán los PDFs generados
     * @throws IOException si falla la lectura de plantillas o el guardado de PDFs
     */
    public void generarPreavisoYDocumentacionAsociada(Empresa empresa, Path carpetaDestino)
            throws IOException {
        Objects.requireNonNull(empresa, "empresa no puede ser null");
        Objects.requireNonNull(carpetaDestino, "carpetaDestino no puede ser null");
        Files.createDirectories(carpetaDestino);

        final boolean esDelegados = empresa.getNumeroTrabajadores() < 50;
        final String carpetaPlantillas = esDelegados ? "Delegados" : "Comite";
        final String[] documentosAGenerar =
                esDelegados ? Constantes.DOCUMENTACION_DELEGADOS : Constantes.DOCUMENTACION_COMITE;

        LOGGER.log(
                Level.INFO,
                "Generando documentación para {0} (empresa con {1} trabajadores).",
            new Object[] {esDelegados ? "Delegados" : "Comité", empresa.getNumeroTrabajadores()});

        Map<String, String> mapaCampos = crearMapaCamposPreavisoPDF(empresa);

        for (String nombreDocumento : documentosAGenerar) {
            generarDesdePlantilla(carpetaPlantillas, nombreDocumento, mapaCampos, carpetaDestino);
        }
        LOGGER.log(Level.INFO, "Documentación generada en: {0}", carpetaDestino);
    }

    /**
     * Genera documentos de escrutinio en base al tipo de elección y la lista de modelos.
     *
     * @param eleccion proceso electoral con datos de empresa y tipo
     * @param carpetaDestino carpeta donde se guardarán los PDFs generados
     * @param modelosAGenerar lista de nombres de modelos a generar (sin extensión)
     * @throws IOException si falla la lectura de plantillas o el guardado de PDFs
     */
        public void generarDocumentosEscrutinio(
                Eleccion eleccion, Path carpetaDestino, String[] modelosAGenerar) throws IOException {
        Objects.requireNonNull(eleccion, "eleccion no puede ser null");
        Objects.requireNonNull(carpetaDestino, "carpetaDestino no puede ser null");
        Objects.requireNonNull(modelosAGenerar, "modelosAGenerar no puede ser null");
        Files.createDirectories(carpetaDestino);

        // Determinar por tamaño del censo de la elección
        final boolean esDelegados = eleccion.getNumeroTrabajadores() < 50;
        final String carpetaPlantillas = esDelegados ? "Delegados" : "Comite";

        LOGGER.log(
                Level.INFO,
            "Iniciando generación de documentos de escrutinio (empresaId={0})",
            eleccion.getIdEmpresa());

        Map<String, String> mapaCampos = crearMapaCamposEscrutinioPDF(eleccion);
        for (String nombreDocumento : modelosAGenerar) {
            generarDesdePlantilla(carpetaPlantillas, nombreDocumento, mapaCampos, carpetaDestino);
        }
        LOGGER.log(Level.INFO, "Escrutinio generado en: {0}", carpetaDestino);
    }

    /**
     * Carga una plantilla del classpath, rellena campos y guarda el PDF resultante en disco.
     */
    private void generarDesdePlantilla(
            String carpetaPlantillas,
            String nombreDocumento,
            Map<String, String> mapaCampos,
            Path carpetaDestino) throws IOException {

        String rutaRecurso = String.format("/%s/%s.pdf", carpetaPlantillas, nombreDocumento);
        Path rutaSalida = carpetaDestino.resolve(nombreDocumento + ".pdf");

        LOGGER.log(Level.INFO, "Generando documento: {0}", rutaSalida);

        try (InputStream in =
                GeneradorDocumentosService.class.getResourceAsStream(rutaRecurso)) {
            if (in == null) {
                throw new IOException(
                        "No se encontró la plantilla en el classpath: " + rutaRecurso);
            }

            try (PDDocument pdfDocument = Loader.loadPDF(in.readAllBytes())) {
                PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();
                if (acroForm == null) {
                    LOGGER.log(
                            Level.WARNING,
                            "El documento {0} no contiene un formulario AcroForm.",
                            nombreDocumento);
                } else {
                    for (Map.Entry<String, String> entry : mapaCampos.entrySet()) {
                        if (acroForm.getField(entry.getKey()) != null) {
                            acroForm.getField(entry.getKey()).setValue(entry.getValue());
                        }
                    }
                    acroForm.flatten();
                }

                pdfDocument.save(rutaSalida.toFile());
            }
        }
    }

    // TODO: Implementar mapeos reales según los campos de los PDFs.
    private Map<String, String> crearMapaCamposPreavisoPDF(Empresa empresa) {
        return java.util.Collections.emptyMap();
    }

    private Map<String, String> crearMapaCamposEscrutinioPDF(Eleccion eleccion) {
        return java.util.Collections.emptyMap();
    }
}