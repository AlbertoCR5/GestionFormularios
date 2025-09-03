package com.albertocr.gestionformularios.util;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Clase de utilidad para leer información de formularios PDF.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.0
 */
public final class PdfFieldReader {

    private static final Logger logger = LoggerFactory.getLogger(PdfFieldReader.class);

    /**
     * Constructor privado para prevenir la instanciación.
     */
    private PdfFieldReader() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada.");
    }

    /**
     * Obtiene una lista de los nombres de todos los campos de un formulario PDF.
     *
     * @param resourcePath La ruta al archivo PDF dentro de los recursos del proyecto (ej. "/Comite/modelo_3.pdf").
     * @return Una lista de Strings con los nombres de los campos.
     * @throws IOException Si el recurso no se encuentra o hay un error al leer el PDF.
     */
    public static List<String> getFormFieldNames(String resourcePath) throws IOException {
        List<String> fieldNames = new ArrayList<>();
        try (InputStream is = PdfFieldReader.class.getResourceAsStream(resourcePath);
             PDDocument pdfDocument = Loader.loadPDF(Objects.requireNonNull(is).readAllBytes())) {

            PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();
            if (acroForm != null) {
                for (PDField field : acroForm.getFieldTree()) {
                    fieldNames.add(field.getFullyQualifiedName());
                }
            } else {
                logger.warn("El PDF en la ruta '{}' no contiene un formulario AcroForm.", resourcePath);
            }
        }
        return fieldNames;
    }
}
