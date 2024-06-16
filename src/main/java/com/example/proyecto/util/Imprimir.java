package com.example.proyecto.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que gestiona la impresión de documentos PDF.
 * Extiende la clase Thread para permitir la impresión en un hilo separado.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class Imprimir extends Thread {

    private static final Logger logger = Logger.getLogger(Imprimir.class.getName());

    /**
     * Método que imprime directamente un archivo PDF desde una ruta especificada.
     *
     * @param rutaArchivoPDF La ruta del archivo PDF a imprimir.
     */
    public void imprimirDirectamente(String rutaArchivoPDF) {
        PDDocument document = null;
        try {
            // Cargar el documento PDF
            document = PDDocument.load(new File(rutaArchivoPDF));

            // Configurar la impresión
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(document));

            // Mostrar el cuadro de diálogo de impresión
            if (job.printDialog()) {
                // Imprimir el documento
                job.print();
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, MessageManager.getMessage("error.carga.pdf") + e.getMessage(), e);
        } catch (PrinterException e) {
            logger.log(Level.SEVERE, MessageManager.getMessage("error.impresion.pdf") + e.getMessage(), e);
        } finally {
            // Cerrar el documento después de imprimir
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    logger.log(Level.SEVERE, MessageManager.getMessage("error.cerrar.pdf") + e.getMessage(), e);
                }
            }
        }
    }
}
