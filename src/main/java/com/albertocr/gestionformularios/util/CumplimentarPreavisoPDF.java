package com.albertocr.gestionformularios.util;

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
import java.util.Map;
import java.util.Objects;

/**
 * Clase de utilidad para rellenar el formulario PDF de preaviso con los datos proporcionados.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.4
 */
public final class CumplimentarPreavisoPDF {

    private static final Logger logger = LoggerFactory.getLogger(CumplimentarPreavisoPDF.class);
    private static final String TEMPLATE_PATH = "/Delegados/preaviso.pdf";
    private static final String FONT_PATH = "/fonts/LiberationSans-Bold.ttf"; // Actualizado a Bold

    private CumplimentarPreavisoPDF() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada.");
    }

    public static void cumplimentarYGuardar(Map<String, String> campos, String rutaGuardado) throws IOException {
        try (InputStream templateStream = CumplimentarPreavisoPDF.class.getResourceAsStream(TEMPLATE_PATH);
             PDDocument pdfDocument = Loader.loadPDF(Objects.requireNonNull(templateStream).readAllBytes())) {

            PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();
            if (acroForm == null) {
                throw new IOException("El PDF no contiene un formulario AcroForm.");
            }

            // Cargar la fuente que se va a incrustar
            try (InputStream fontStream = CumplimentarPreavisoPDF.class.getResourceAsStream(FONT_PATH)) {
                if (fontStream == null) {
                    throw new IOException("No se pudo encontrar el archivo de fuente en: " + FONT_PATH);
                }
                PDType0Font font = PDType0Font.load(pdfDocument, fontStream);
                PDResources resources = acroForm.getDefaultResources();
                if (resources == null) {
                    resources = new PDResources();
                }
                // Añadir la fuente a los recursos del formulario con un nombre
                String fontName = resources.add(font).getName();
                // Establecer la apariencia por defecto para usar la fuente incrustada
                acroForm.setDefaultAppearance("/" + fontName + " 10 Tf 0 g");
            } catch (Exception e) {
                logger.error("Error al cargar o incrustar la fuente.", e);
                throw new IOException("Fallo al procesar la fuente del PDF.", e);
            }

            for (Map.Entry<String, String> entry : campos.entrySet()) {
                setField(acroForm, entry.getKey(), entry.getValue());
            }

            pdfDocument.save(new File(rutaGuardado));
            logger.info("PDF de preaviso guardado exitosamente en: {}", rutaGuardado);

        } catch (IOException e) {
            logger.error("Error al procesar el PDF de preaviso.", e);
            throw e; // Relanzar para que el controlador la maneje
        }
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
