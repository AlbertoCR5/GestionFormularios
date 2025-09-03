package com.albertocr.gestionformularios.util;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Clase de utilidad para leer campos de un formulario PDF.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.2
 */
public final class LectorPDF {

    private static final Logger logger = LoggerFactory.getLogger(LectorPDF.class);

    private LectorPDF() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada.");
    }

    public static String leerCampo(String rutaPdf, String nombreCampo) {
        File pdfFile = new File(rutaPdf);
        if (!pdfFile.exists()) {
            logger.warn("El archivo PDF no se encontró en la ruta: {}", rutaPdf);
            return "";
        }

        try (PDDocument document = Loader.loadPDF(pdfFile)) {
            PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
            if (acroForm == null) {
                logger.warn("El PDF en la ruta {} no contiene un formulario AcroForm.", rutaPdf);
                return "";
            }

            PDField field = acroForm.getField(nombreCampo);
            if (field != null) {
                String value = field.getValueAsString();
                return value != null ? value : "";
            } else {
                logger.warn("Campo '{}' no encontrado en el PDF: {}", nombreCampo, rutaPdf);
                return "";
            }
        } catch (IOException e) {
            logger.error("Error al leer el archivo PDF en la ruta: {}", rutaPdf, e);
            return "";
        }
    }

    /**
     * Método de depuración para listar todos los campos y sus valores de un formulario PDF.
     *
     * @param rutaPdf La ruta completa al archivo PDF.
     */
    public static void listarCamposYValores(String rutaPdf) {
        File pdfFile = new File(rutaPdf);
        if (!pdfFile.exists()) {
            logger.error("DEPURACIÓN: El archivo PDF no se encontró en la ruta: {}", rutaPdf);
            return;
        }

        logger.info("--- INICIO DEPURACIÓN AVANZADA DE CAMPOS PDF ---");
        logger.info("Analizando fichero: {}", rutaPdf);
        try (PDDocument document = Loader.loadPDF(pdfFile)) {
            PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
            if (acroForm != null && !acroForm.getFields().isEmpty()) {
                logger.info("Campos y valores encontrados:");
                for (PDField field : acroForm.getFields()) {
                    String nombre = field.getFullyQualifiedName();
                    String valor = field.getValueAsString();
                    logger.info("- Campo: '{}', Valor: '{}'", nombre, valor);
                }
            } else {
                logger.warn("No se encontraron campos de formulario en este PDF.");
            }
        } catch (IOException e) {
            logger.error("DEPURACIÓN: Error al leer el archivo PDF.", e);
        }
        logger.info("--- FIN DEPURACIÓN AVANZADA DE CAMPOS PDF ---");
    }
}
