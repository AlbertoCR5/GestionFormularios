package com.albertocr.gestionformularios.util;

import com.albertocr.gestionformularios.model.Candidato;
import com.albertocr.gestionformularios.model.Eleccion;
import com.albertocr.gestionformularios.model.Empresa;
import com.albertocr.gestionformularios.model.EscrutinioData;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * La clase `CumplimentarEscrutinioPDF` gestiona la modificación de formularios PDF de escrutinio para Delegados.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.6
 */
public final class CumplimentarEscrutinioPDF {

    private static final Logger logger = LoggerFactory.getLogger(CumplimentarEscrutinioPDF.class);
    private static final String FONT_PATH = "/fonts/LiberationSans-Bold.ttf"; // Actualizado a Bold

    private CumplimentarEscrutinioPDF() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada.");
    }

    private static PDAcroForm prepareAcroForm(PDDocument pdfDocument) throws IOException {
        PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();
        if (acroForm == null) {
            throw new IOException("El PDF no contiene un formulario AcroForm.");
        }

        try (InputStream fontStream = CumplimentarEscrutinioPDF.class.getResourceAsStream(FONT_PATH)) {
            if (fontStream == null) {
                throw new IOException("No se pudo encontrar el archivo de fuente en: " + FONT_PATH);
            }
            PDType0Font font = PDType0Font.load(pdfDocument, fontStream);
            PDResources resources = acroForm.getDefaultResources();
            if (resources == null) {
                resources = new PDResources();
            }
            String fontName = resources.add(font).getName();
            acroForm.setDefaultResources(resources);
            acroForm.setDefaultAppearance("/" + fontName + " 10 Tf 0 g");
        } catch (Exception e) {
            logger.error("Error al cargar o incrustar la fuente.", e);
            throw new IOException("Fallo al procesar la fuente del PDF.", e);
        }
        return acroForm;
    }

    private static PDDocument loadOrCreatePdfDocument(String templatePath, String outputPath) throws IOException {
        File outputFile = new File(outputPath);
        if (outputFile.exists()) {
            logger.info("Cargando PDF existente desde: {}", outputPath);
            return Loader.loadPDF(outputFile);
        } else {
            logger.info("Creando nuevo PDF desde la plantilla: {}", templatePath);
            try (InputStream is = CumplimentarEscrutinioPDF.class.getResourceAsStream(templatePath)) {
                if (is == null) {
                    throw new IOException("No se pudo encontrar la plantilla PDF en: " + templatePath);
                }
                return Loader.loadPDF(is.readAllBytes());
            }
        }
    }

    public static void cumplimentarModelo5_1(Empresa empresa, Eleccion eleccion, List<Candidato> candidatos, EscrutinioData escrutinioData, String rutaSalida) throws IOException {
        String templatePath = "/Delegados/modelo_5_1.pdf";
        try (PDDocument pdfDocument = loadOrCreatePdfDocument(templatePath, rutaSalida)) {
            PDAcroForm acroForm = prepareAcroForm(pdfDocument);

            setField(acroForm, "fechaEscrutinio", eleccion.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            for (int i = 0; i < candidatos.size(); i++) {
                if (i >= 25) break; // Limitar al número de campos en el PDF
                Candidato candidato = candidatos.get(i);
                setField(acroForm, String.format("nombre%d", i + 1), candidato.getNombre() + " " + candidato.getApellidos());
                setField(acroForm, String.format("dni%d", i + 1), candidato.getDni());
            }
            setField(acroForm, "reclamaciones1", escrutinioData.getReclamaciones());

            pdfDocument.save(new File(rutaSalida));
            logger.info("PDF del Modelo 5.1 guardado en: {}", rutaSalida);
        }
    }

    public static void cumplimentarModelo5_2Proceso(String rutaSalida, Empresa empresa, Eleccion eleccion, EscrutinioData data) throws IOException {
        logger.warn("La lógica para cumplimentar 'Modelo 5.2 Proceso' aún no ha sido implementada.");
    }

    public static void cumplimentarModelo5_2Conclusion(String rutaSalida, Empresa empresa, Eleccion eleccion, EscrutinioData data) throws IOException {
        logger.warn("La lógica para cumplimentar 'Modelo 5.2 Conclusion' aún no ha sido implementada.");
    }

    public static void cumplimentarModelo9(String rutaSalida, Empresa empresa, Eleccion eleccion, EscrutinioData data) throws IOException {
        logger.warn("La lógica para cumplimentar 'Modelo 9' aún no ha sido implementada.");
    }

    public static void cumplimentarAutorizacion(String rutaSalida, Empresa empresa, EscrutinioData data) throws IOException {
        logger.warn("La lógica para cumplimentar 'Autorizacion' aún no ha sido implementada.");
    }

    private static void setField(PDAcroForm acroForm, String fieldName, String value) throws IOException {
        PDField field = acroForm.getField(fieldName);
        if (field != null) {
            field.setValue(value != null ? value : "");
        } else {
            logger.warn("Campo no encontrado en el PDF: '{}'", fieldName);
        }
    }
}
