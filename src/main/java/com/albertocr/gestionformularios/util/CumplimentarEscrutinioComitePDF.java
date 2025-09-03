package com.albertocr.gestionformularios.util;

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

/**
 * Clase de utilidad para rellenar los diversos formularios PDF de escrutinio para Comités.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.6
 */
public final class CumplimentarEscrutinioComitePDF {

    private static final Logger logger = LoggerFactory.getLogger(CumplimentarEscrutinioComitePDF.class);
    private static final String FONT_PATH = "/fonts/LiberationSans-Bold.ttf"; // Actualizado a Bold

    private CumplimentarEscrutinioComitePDF() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada.");
    }

    private static PDAcroForm prepareAcroForm(PDDocument pdfDocument) throws IOException {
        PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();
        if (acroForm == null) {
            throw new IOException("El PDF no contiene un formulario AcroForm.");
        }

        try (InputStream fontStream = CumplimentarEscrutinioComitePDF.class.getResourceAsStream(FONT_PATH)) {
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
            try (InputStream is = CumplimentarEscrutinioComitePDF.class.getResourceAsStream(templatePath)) {
                if (is == null) {
                    throw new IOException("No se pudo encontrar la plantilla PDF en: " + templatePath);
                }
                return Loader.loadPDF(is.readAllBytes());
            }
        }
    }

    public static void cumplimentarModelo7_3_Acta_Global(Empresa empresa, Eleccion eleccion, EscrutinioData escrutinioData, String rutaSalida) throws IOException {
        String templatePath = "/Comite/modelo_7_3_acta_global.pdf";
        try (PDDocument pdfDocument = loadOrCreatePdfDocument(templatePath, rutaSalida)) {

            PDAcroForm acroForm = prepareAcroForm(pdfDocument);

            // Rellenar campos del PDF
            setField(acroForm, "nombreEmpresa", empresa.getNombre());
            setField(acroForm, "cif", empresa.getCif());
            setField(acroForm, "presidente", escrutinioData.getPresidente());
            setField(acroForm, "dniPresidente", escrutinioData.getDniPresidente());
            // ... añadir el resto de campos necesarios

            pdfDocument.save(new File(rutaSalida));
            logger.info("PDF del Modelo 7.3 Acta Global guardado en: {}", rutaSalida);
        }
    }

    // Los métodos restantes pueden ahora reutilizar la lógica de prepareAcroForm
    public static void cumplimentarModelo6_1_Especialistas(String rutaSalida, Object data) throws IOException {
        logger.warn("La lógica para cumplimentar 'Modelo 6.1 Especialistas' aún no ha sido implementada.");
    }

    public static void cumplimentarModelo6_2_Especialistas(String rutaSalida, Object data) throws IOException {
        logger.warn("La lógica para cumplimentar 'Modelo 6.2 Especialistas' aún no ha sido implementada.");
    }

    public static void cumplimentarModelo6_1_Tecnicos(String rutaSalida, Object data) throws IOException {
        logger.warn("La lógica para cumplimentar 'Modelo 6.1 Tecnicos' aún no ha sido implementada.");
    }

    private static void setField(PDAcroForm acroForm, String fieldName, String value) throws IOException {
        PDField field = acroForm.getField(fieldName);
        if (field != null) {
            field.setValue(value != null ? value : "");
        } else {
            logger.warn("Campo no encontrado en el PDF: {}", fieldName);
        }
    }
}
