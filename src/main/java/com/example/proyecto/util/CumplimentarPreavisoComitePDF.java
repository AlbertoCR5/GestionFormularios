package com.example.proyecto.util;

import com.example.proyecto.interfaz.PrincipalView;
import com.example.proyecto.modal.Preaviso;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.function.Consumer;

public class CumplimentarPreavisoComitePDF {

    private int documentosGuardados = 0;
    private final PrincipalView ventanaPreaviso;

    /**
     * Constructor de la clase CumplimentarPreavisoPDF.
     *
     * @param ventanaPreaviso La vista principal de la aplicación.
     */
    public CumplimentarPreavisoComitePDF(PrincipalView ventanaPreaviso) {
        this.ventanaPreaviso = ventanaPreaviso;
    }

    /**
     * Modifica los campos de texto en el formulario PDF.
     *
     * @param rutaFormularioPDF      La ruta del formulario PDF.
     * @param rutaDirectorioEmpresa  La ruta del directorio de la empresa.
     * @param nuevoPreaviso          El objeto Preaviso con los nuevos datos.
     * @param fieldModifier          Un Consumer que define cómo modificar los campos del formulario.
     */
    private void modificarCamposTextoPDF(String rutaFormularioPDF, String rutaDirectorioEmpresa, Preaviso nuevoPreaviso, Consumer<PDAcroForm> fieldModifier) {
        try {
            String nombreArchivo = generarNombreArchivo(rutaFormularioPDF, nuevoPreaviso);
            Path rutaArchivoSistema = Paths.get(rutaDirectorioEmpresa, nombreArchivo);

            if (!Files.exists(rutaArchivoSistema)) {
                try (InputStream in = getClass().getResourceAsStream(rutaFormularioPDF)) {
                    if (in == null) {
                        throw new IOException(STR."Archivo no encontrado: \{rutaFormularioPDF}");
                    }
                    Files.copy(in, rutaArchivoSistema, StandardCopyOption.REPLACE_EXISTING);
                }
            }

            try (PDDocument pdfDocument = PDDocument.load(rutaArchivoSistema.toFile())) {
                PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
                pdfDocument.setAllSecurityToBeRemoved(true);
                PDAcroForm acroForm = docCatalog.getAcroForm();

                if (acroForm != null) {
                    fieldModifier.accept(acroForm);
                    guardarPDF(pdfDocument, rutaDirectorioEmpresa, nombreArchivo);
                } else {
                    ventanaPreaviso.mostrarMensaje(MessageManager.getMessage("error.formulario.acro.no.encontrado"), false);
                }
            }
        } catch (IOException e) {
            ventanaPreaviso.mostrarMensaje(MessageManager.getMessage("error.modificar.pdf") + e.getMessage(), false);
        }
    }

    /**
     * Modifica un campo de texto en el formulario PDF.
     *
     * @param acroForm   El formulario acro donde se encuentra el campo.
     * @param campoID    El ID del campo a modificar.
     * @param nuevoValor El nuevo valor a asignar al campo.
     */
    public void modificarCampoTexto(PDAcroForm acroForm, String campoID, String nuevoValor) {
        PDField field = acroForm.getField(campoID);
        if (field != null) {
            try {
                field.setValue(nuevoValor);
            } catch (IOException e) {
                ventanaPreaviso.mostrarMensaje(STR."\{MessageManager.getMessage("error.modificar.campo")}\{campoID}: \{e.getMessage()}", false);
            }
        } else {
            ventanaPreaviso.mostrarMensaje(MessageManager.getMessage("error.campo.texto.no.encontrado") + campoID, false);
        }
    }

    /**
     * Genera el nombre del archivo PDF basado en la ruta del formulario y el objeto Preaviso.
     *
     * @param rutaFormularioPDF La ruta del formulario PDF.
     * @param nuevoPreaviso     El objeto Preaviso.
     * @return El nombre del archivo PDF.
     */
    public String generarNombreArchivo(String rutaFormularioPDF, Preaviso nuevoPreaviso) {
        String baseName = new File(rutaFormularioPDF).getName().replace(".pdf", "");
        return STR."\{DirectorioManager.sanitizarNombreEmpresa(nuevoPreaviso.getNombreEmpresa())} \{baseName}\{Constantes.EXTENSION_ARCHIVO}";
    }

    /**
     * Guarda el documento PDF modificado.
     *
     * @param pdfDocument            El documento PDF a guardar.
     * @param rutaDirectorioEmpresa  La ruta del directorio donde se guardará el PDF.
     * @param nombreArchivo          El nombre del archivo PDF.
     * @throws IOException Si ocurre un error al guardar el PDF.
     */
    private void guardarPDF(PDDocument pdfDocument, String rutaDirectorioEmpresa, String nombreArchivo) throws IOException {
        String rutaFinal = Paths.get(rutaDirectorioEmpresa, nombreArchivo).toString();
        pdfDocument.save(rutaFinal);
        documentosGuardados++;

        if (documentosGuardados == Constantes.DOCUMENTACION_DELEGADOS.length) {
            ventanaPreaviso.mostrarMensaje(MessageManager.getMessage("pdf.guardado.exitosamente") + rutaDirectorioEmpresa, true);
            documentosGuardados = 0;
        }
    }

    public void modificarCamposTextoCalendarioComitePDF(String rutaFormularioPDF, String string, Preaviso nuevoPreaviso) {
        modificarCamposTextoPDF(rutaFormularioPDF, rutaDirectorioEmpresa, nuevoPreaviso, acroForm -> {
            modificarCampoTexto(acroForm, "nombreEmpresa", nuevoPreaviso.getNombreEmpresa());
            modificarCampoTexto(acroForm, "fechaConstitucion", ConversorFechaToLetras.convertirFechaCompleta(nuevoPreaviso.getFechaConstitucion()));
        });
    }

    public void modificarCamposTextoModelo4EspecialistasPDF(String rutaFormularioPDF, String string, Preaviso nuevoPreaviso) {
        //Modifica campos de texto en Modelo 4 Especialistas
        modificarCamposTextoPDF(rutaFormularioPDF, rutaDirectorioEmpresa, nuevoPreaviso, acroForm -> {
            modificarCampoTexto(acroForm, "numeroMesa", "1");
            modificarCampoTexto(acroForm, "nombreEmpresa", nuevoPreaviso.getNombreEmpresa());
            modificarCampoTexto(acroForm, "CIF", nuevoPreaviso.getCIF());
            modificarCampoTexto(acroForm, "nombreComercial", nuevoPreaviso.getNombreComercial());
            modificarCampoTexto(acroForm, "nombreCentro", nuevoPreaviso.getNombreCentro());
            modificarCampoTexto(acroForm, "direccion", nuevoPreaviso.getDireccion());
            modificarCampoTexto(acroForm, "municipio", nuevoPreaviso.getMunicipio());

        });
    }

    public void modificarCamposTextoModelo4TecnicosPDF(String rutaFormularioPDF, String string, Preaviso nuevoPreaviso) {
        modificarCamposTextoPDF(rutaFormularioPDF, rutaDirectorioEmpresa, nuevoPreaviso, acroForm -> {
            modificarCampoTexto(acroForm, "numeroMesa", "1");
            modificarCampoTexto(acroForm, "nombreEmpresa", nuevoPreaviso.getNombreEmpresa());
            modificarCampoTexto(acroForm, "CIF", nuevoPreaviso.getCIF());
            modificarCampoTexto(acroForm, "nombreComercial", nuevoPreaviso.getNombreComercial());
            modificarCampoTexto(acroForm, "nombreCentro", nuevoPreaviso.getNombreCentro());
            modificarCampoTexto(acroForm, "direccion", nuevoPreaviso.getDireccion());
            modificarCampoTexto(acroForm, "municipio", nuevoPreaviso.getMunicipio());
        });
    }

    public void modificarCamposTextoModelo6_1EspecialistasPDF(String rutaFormularioPDF, String string, Preaviso nuevoPreaviso) {
        modificarCamposTextoPDF(rutaFormularioPDF, rutaDirectorioEmpresa, nuevoPreaviso, acroForm -> {
            modificarCampoTexto(acroForm, "numeroMesa", "1");
            modificarCampoTexto(acroForm, "nombreEmpresa", nuevoPreaviso.getNombreEmpresa());
            modificarCampoTexto(acroForm, "CIF", nuevoPreaviso.getCIF());
            modificarCampoTexto(acroForm, "nombreComercial", nuevoPreaviso.getNombreComercial());
            modificarCampoTexto(acroForm, "nombreCentro", nuevoPreaviso.getNombreCentro());
            modificarCampoTexto(acroForm, "direccion", nuevoPreaviso.getDireccion());
            modificarCampoTexto(acroForm, "municipio", nuevoPreaviso.getMunicipio());
            modificarCampoTexto(acroForm, "fechaConstitucion", ConversorFechaToLetras.convertirFechaCompleta(nuevoPreaviso.getFechaConstitucion()));
        });
    }

    public void modificarCamposTextoModelo6_2EspecialistasPDF(String rutaFormularioPDF, String string, Preaviso nuevoPreaviso) {
        modificarCamposTextoPDF(rutaFormularioPDF, rutaDirectorioEmpresa, nuevoPreaviso, acroForm -> {
            modificarCampoTexto(acroForm, "nombreEmpresa", nuevoPreaviso.getNombreEmpresa());
            modificarCampoTexto(acroForm, "sindicato", nuevoPreaviso.getPromotores());
        });
    }

    public void modificarCamposTextoModelo6_3EspecialistasPDF(String rutaFormularioPDF, String string, Preaviso nuevoPreaviso) {
        modificarCamposTextoPDF(rutaFormularioPDF, rutaDirectorioEmpresa, nuevoPreaviso, acroForm -> {
            modificarCampoTexto(acroForm, "nombreEmpresa", nuevoPreaviso.getNombreEmpresa());
            modificarCampoTexto(acroForm, "CIF", nuevoPreaviso.getCIF());
        });
    }

    public void modificarCamposTextoModelo6_1TecnicosPDF(String rutaFormularioPDF, String string, Preaviso nuevoPreaviso) {
        modificarCamposTextoPDF(rutaFormularioPDF, rutaDirectorioEmpresa, nuevoPreaviso, acroForm -> {
            modificarCampoTexto(acroForm, "numeroMesa", "1");
            modificarCampoTexto(acroForm, "nombreEmpresa", nuevoPreaviso.getNombreEmpresa());
            modificarCampoTexto(acroForm, "CIF", nuevoPreaviso.getCIF());
            modificarCampoTexto(acroForm, "nombreComercial", nuevoPreaviso.getNombreComercial());
            modificarCampoTexto(acroForm, "nombreCentro", nuevoPreaviso.getNombreCentro());
            modificarCampoTexto(acroForm, "direccion", nuevoPreaviso.getDireccion());
            modificarCampoTexto(acroForm, "municipio", nuevoPreaviso.getMunicipio());
            modificarCampoTexto(acroForm, "fechaConstitucion", ConversorFechaToLetras.convertirFechaCompleta(nuevoPreaviso.getFechaConstitucion()));
        });
    }

    public void modificarCamposTextoModelo6_2TecnicosPDF(String rutaFormularioPDF, String string, Preaviso nuevoPreaviso) {
        modificarCamposTextoPDF(rutaFormularioPDF, rutaDirectorioEmpresa, nuevoPreaviso, acroForm -> {
            modificarCampoTexto(acroForm, "nombreEmpresa", nuevoPreaviso.getNombreEmpresa());
            modificarCampoTexto(acroForm, "sindicato", nuevoPreaviso.getPromotores());
        });
    }

    public void modificarCamposTextoModelo6_3TecnicosPDF(String rutaFormularioPDF, String string, Preaviso nuevoPreaviso) {
        modificarCamposTextoPDF(rutaFormularioPDF, rutaDirectorioEmpresa, nuevoPreaviso, acroForm -> {
            modificarCampoTexto(acroForm, "nombreEmpresa", nuevoPreaviso.getNombreEmpresa());
            modificarCampoTexto(acroForm, "CIF", nuevoPreaviso.getCIF());
        });
    }

    public void modificarCamposTextoModelo7_1PDF(String rutaFormularioPDF, String string, Preaviso nuevoPreaviso) {
        modificarCamposTextoPDF(rutaFormularioPDF, rutaDirectorioEmpresa, nuevoPreaviso, acroForm -> {
            modificarCampoTexto(acroForm, "nombreEmpresa", nuevoPreaviso.getNombreEmpresa());
            modificarCampoTexto(acroForm, "CIF", nuevoPreaviso.getCIF());
            modificarCampoTexto(acroForm, "nombreComercial", nuevoPreaviso.getNombreComercial());
            modificarCampoTexto(acroForm, "nombreCentro", nuevoPreaviso.getNombreCentro());
            modificarCampoTexto(acroForm, "direccion", nuevoPreaviso.getDireccion());
            modificarCampoTexto(acroForm, "municipio", nuevoPreaviso.getMunicipio());
        });
    }

    public void modificarCamposTextoModelo7_2PDF(String rutaFormularioPDF, String string, Preaviso nuevoPreaviso) {
        modificarCamposTextoPDF(rutaFormularioPDF, rutaDirectorioEmpresa, nuevoPreaviso, acroForm -> {
        });
    }

    public void modificarCamposTextoModelo7_3ActaGlobalPDF(String rutaFormularioPDF, String string, Preaviso nuevoPreaviso) {
        modificarCamposTextoPDF(rutaFormularioPDF, rutaDirectorioEmpresa, nuevoPreaviso, acroForm -> {
            modificarCampoTexto(acroForm, "nombreEmpresa", nuevoPreaviso.getNombreEmpresa());
            modificarCampoTexto(acroForm, "nombreComercial", nuevoPreaviso.getNombreComercial());
            modificarCampoTexto(acroForm, "CIF", nuevoPreaviso.getCIF());
            modificarCampoTexto(acroForm, "domicilio", nuevoPreaviso.getDireccion());
            modificarCampoTexto(acroForm, "municipio", nuevoPreaviso.getMunicipio());
            modificarCampoTexto(acroForm, "nombreCentro", nuevoPreaviso.getNombreCentro());
            modificarCampoTexto(acroForm, "municipio", nuevoPreaviso.getMunicipio());
            modificarCampoTexto(acroForm, "comarca", nuevoPreaviso.getComarca());
            modificarCampoTexto(acroForm, "CP", nuevoPreaviso.getCodigoPostal());
            modificarCampoTexto(acroForm, "numeroISS", nuevoPreaviso.getNumeroISS());
        });
    }

    public void modificarCamposTextoModelo7_3AnexoPDF(String rutaFormularioPDF, String string, Preaviso nuevoPreaviso) {
        modificarCamposTextoPDF(rutaFormularioPDF, rutaDirectorioEmpresa, nuevoPreaviso, acroForm -> {
            modificarCampoTexto(acroForm, "nombreEmpresa", nuevoPreaviso.getNombreEmpresa());
            modificarCampoTexto(acroForm, "nombreCentro", nuevoPreaviso.getNombreCentro());
            modificarCampoTexto(acroForm, "municipio", nuevoPreaviso.getMunicipio());
        });
    }

    public void modificarCamposTextoModelo7_3ProcesoPDF(String rutaFormularioPDF, String string, Preaviso nuevoPreaviso) {
        modificarCamposTextoPDF(rutaFormularioPDF, rutaDirectorioEmpresa, nuevoPreaviso, acroForm -> {
        });
    }

    public void modificarCamposTextoModelo9ComitePDF(String rutaFormularioPDF, String string, Preaviso nuevoPreaviso) {
        modificarCamposTextoPDF(rutaFormularioPDF, rutaDirectorioEmpresa, nuevoPreaviso, acroForm -> {
            modificarCampoTexto(acroForm, "nombreEmpresa", nuevoPreaviso.getNombreEmpresa());
            modificarCampoTexto(acroForm, "CIF", nuevoPreaviso.getCIF());
            modificarCampoTexto(acroForm, "nombreComercial", nuevoPreaviso.getNombreComercial());
            modificarCampoTexto(acroForm, "nombreCentro", nuevoPreaviso.getNombreCentro());
            modificarCampoTexto(acroForm, "direccion", nuevoPreaviso.getDireccion());
            modificarCampoTexto(acroForm, "municipio", nuevoPreaviso.getMunicipio());
        });
    }
}