package com.example.proyecto.util;

import com.example.proyecto.interfaz.PrincipalView;
import com.example.proyecto.modal.Candidato;
import com.example.proyecto.modal.Modelo_5_1;
import com.example.proyecto.modal.Modelo_5_2_Conclusion;
import com.example.proyecto.modal.Modelo_5_2_Proceso;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * La clase `CumplimentarEscrutinioPDF` gestiona la modificación de formularios PDF.
 * Utiliza PDFBox para la manipulación de los documentos PDF.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class CumplimentarEscrutinioPDF {

    private int documentosGuardados = 0;
    private final PrincipalView ventanaPreaviso;

    /**
     * Constructor de la clase CumplimentarEscrutinioPDF.
     *
     * @param ventanaPreaviso La vista principal de la aplicación.
     */
    public CumplimentarEscrutinioPDF(PrincipalView ventanaPreaviso) {
        this.ventanaPreaviso = ventanaPreaviso;
    }

    /**
     * Modifica los campos de texto en el formulario PDF.
     *
     * @param rutaFormularioPDF La ruta del formulario PDF.
     * @param fieldModifier Un Consumer que define cómo modificar los campos del formulario.
     */
    private void modificarCamposTextoPDF(String rutaFormularioPDF, Consumer<PDAcroForm> fieldModifier) {
        try (PDDocument pdfDocument = PDDocument.load(new File(rutaFormularioPDF))) {
            PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
            pdfDocument.setAllSecurityToBeRemoved(true);
            PDAcroForm acroForm = docCatalog.getAcroForm();

            if (acroForm != null) {
                fieldModifier.accept(acroForm);
                guardarPDF(rutaFormularioPDF, pdfDocument);
            } else {
                ventanaPreaviso.mostrarMensaje(MessageManager.getMessage("error.formulario.acro.no.encontrado"), false);
            }
        } catch (IOException e) {
            ventanaPreaviso.mostrarMensaje(MessageManager.getMessage("error.modificar.pdf") + e.getMessage(), false);
        }
    }

    /**
     * Modifica los campos de texto en el formulario de modelo 5.1 PDF.
     */
    public void modificarCamposTextoEscrutinioModelo5_1PDF(String rutaFormularioPDF, Modelo_5_1 nuevoModelo5_1) {
        modificarCamposTextoPDF(rutaFormularioPDF, acroForm -> {
            modificarCampoTexto(acroForm, "fechaEscrutinio", nuevoModelo5_1.getFechaEscrutinioLetras());
            ArrayList<Candidato> candidatos = nuevoModelo5_1.getCandidatos();
            for (int i = 0; i < candidatos.size(); i++) {
                Candidato candidato = candidatos.get(i);
                modificarCampoTexto(acroForm, STR."nombre\{i + 1}", candidato.getNombreApellidos());
                modificarCampoTexto(acroForm, STR."dni\{i + 1}", candidato.getDni());
                modificarCampoTexto(acroForm, STR."sindicato\{i + 1}", candidato.getSindicato());
            }
            modificarCampoTexto(acroForm, "reclamaciones1", nuevoModelo5_1.getReclamaciones1());
        });
    }

    /**
     * Modifica los campos de texto en el formulario de modelo 5.2 (proceso) PDF.
     */
    public void modificarCamposTextoEscrutinioModelo5_2ProcesoPDF(String rutaFormularioPDF, Modelo_5_1 nuevoModelo5_1, Modelo_5_2_Proceso nuevoModelo5_2_proceso) {
        modificarCamposTextoPDF(rutaFormularioPDF, acroForm -> {
            modificarCampoTexto(acroForm, "preaviso", nuevoModelo5_2_proceso.getPreaviso());
            modificarCampoTexto(acroForm, "dia", String.valueOf(nuevoModelo5_1.getDiaVotacion()));
            modificarCampoTexto(acroForm, "mes", nuevoModelo5_1.getMesVotacion());
            modificarCampoTexto(acroForm, "anio", String.valueOf(nuevoModelo5_1.getAnioVotacion()));
            modificarCampoTexto(acroForm, "totalElectores", String.valueOf(nuevoModelo5_2_proceso.getTotalElectores()));
            modificarCampoTexto(acroForm, "numeroRepresentantes", String.valueOf(nuevoModelo5_2_proceso.getNumeroRepresentantes()));
            if (nuevoModelo5_2_proceso.getTotalVotantes() > 0) {
                modificarCampoTexto(acroForm, "votantesVarones", String.valueOf(nuevoModelo5_2_proceso.getVotantesVarones()));
                modificarCampoTexto(acroForm, "votantesMujeres", String.valueOf(nuevoModelo5_2_proceso.getVotantesMujeres()));
                modificarCampoTexto(acroForm, "totalVotantes", String.valueOf((nuevoModelo5_2_proceso.getVotantesVarones() + nuevoModelo5_2_proceso.getVotantesMujeres())));
                modificarCampoTexto(acroForm, "papeletasCumplimentadas", String.valueOf(nuevoModelo5_2_proceso.getPapeletasCumplimentadas()));
                modificarCampoTexto(acroForm, "papeletasBlancas", String.valueOf(nuevoModelo5_2_proceso.getPapeletasBlancas()));
                modificarCampoTexto(acroForm, "nulos", String.valueOf(nuevoModelo5_2_proceso.getNulos()));
                modificarCampoTexto(acroForm, "representantesElegidos", String.valueOf(nuevoModelo5_2_proceso.getRepresentantesElegidos()));
            }
        });
    }

    /**
     * Modifica los campos de texto en el formulario de modelo 5.2 (conclusión) PDF.
     */
    public void modificarCamposTextoEscrutinioModelo5_2ConclusionPDF(String rutaFormularioPDF, Modelo_5_2_Conclusion nuevoModelo5_2_conclusion) {
        modificarCamposTextoPDF(rutaFormularioPDF, acroForm -> {
            modificarCampoTexto(acroForm, "actividadEconomica", nuevoModelo5_2_conclusion.getActvEcono());
            modificarCampoTexto(acroForm, "actividadEconomica1", nuevoModelo5_2_conclusion.getActvEcono1());
            modificarCampoTexto(acroForm, "actividadEconomica2", nuevoModelo5_2_conclusion.getActvEcono2());
            modificarCampoTexto(acroForm, "actividadEconomica3", nuevoModelo5_2_conclusion.getActvEcono());
            modificarCampoTexto(acroForm, "telefono", nuevoModelo5_2_conclusion.getTelefono());
            modificarCampoTexto(acroForm, "nombreConvenio", nuevoModelo5_2_conclusion.getNombreConvenio());
            modificarCampoTexto(acroForm, "numeroConvenio", nuevoModelo5_2_conclusion.getNumeroConvenio());
            modificarCampoTexto(acroForm, "trabajadoresFijos", String.valueOf(nuevoModelo5_2_conclusion.getTrabajadoresFijos()));
            modificarCampoTexto(acroForm, "trabajadoresEventuales", String.valueOf(nuevoModelo5_2_conclusion.getTrabajadoresEventuales()));
            modificarCampoTexto(acroForm, "trabajadoresJornadas", String.valueOf(nuevoModelo5_2_conclusion.getTrabajadoresJornadas()));
            modificarCampoTexto(acroForm, "trabajadoresEventualesComputo", String.valueOf(nuevoModelo5_2_conclusion.getTrabajadoresEventualesComputo()));
            modificarCampoTexto(acroForm, "totalTrabajadores", String.valueOf(nuevoModelo5_2_conclusion.getTotalTrabajadores()));
            modificarCampoTexto(acroForm, "presidente", nuevoModelo5_2_conclusion.getPresidente());
            modificarCampoTexto(acroForm, "secretario", nuevoModelo5_2_conclusion.getSecretario());
            modificarCampoTexto(acroForm, "representantes", nuevoModelo5_2_conclusion.getRepresentantes());
            modificarCampoTexto(acroForm, "dniPresidente", nuevoModelo5_2_conclusion.getDniPresidente());
            modificarCampoTexto(acroForm, "dniSecretario", nuevoModelo5_2_conclusion.getDniSecretario());
            modificarCampoTexto(acroForm, "dniRepresentante", nuevoModelo5_2_conclusion.getDniRepresentante());
        });
    }

    /**
     * Modifica los campos de texto en el formulario de modelo 9 PDF.
     */
    public void modificarCamposTextoEscrutinioModelo9PDF(String rutaFormularioPDF, Modelo_5_1 nuevoModelo5_1, Modelo_5_2_Conclusion nuevoModelo5_2_Conclusion, Modelo_5_2_Proceso nuevoModelo5_2_Proceso) {
        modificarCamposTextoPDF(rutaFormularioPDF, acroForm -> {
            modificarCampoTexto(acroForm, "presidente", nuevoModelo5_2_Conclusion.getPresidente());
            modificarCampoTexto(acroForm, "dniPresidente", nuevoModelo5_2_Conclusion.getDniPresidente());
            modificarCampoTexto(acroForm, "dia", String.valueOf(nuevoModelo5_1.getDiaVotacion()));
            modificarCampoTexto(acroForm, "mes", ConversorFechaToLetras.convertirMesALetras(nuevoModelo5_1.getFechaEscrutinio()));
            modificarCampoTexto(acroForm, "anio", String.valueOf(nuevoModelo5_1.getAnioVotacion()));
            modificarCampoTexto(acroForm, "totalRepresentantes", String.valueOf(nuevoModelo5_2_Proceso.getNumeroRepresentantes()));
        });
    }

    /**
     * Modifica los campos de texto en el formulario de autorización PDF.
     */
    public void modificarCamposTextoEscrutinioAutorizacionPDF(String rutaFormularioPDF, Modelo_5_1 nuevoModelo5_1, Modelo_5_2_Conclusion nuevoModelo5_2_Conclusion) {
        modificarCamposTextoPDF(rutaFormularioPDF, acroForm -> {
            modificarCampoTexto(acroForm, "nombrePresidente", nuevoModelo5_2_Conclusion.getPresidente());
            modificarCampoTexto(acroForm, "dia", String.valueOf(nuevoModelo5_1.getDiaVotacion()));
            modificarCampoTexto(acroForm, "mes", ConversorFechaToLetras.convertirMesALetras(nuevoModelo5_1.getFechaEscrutinio()));
            modificarCampoTexto(acroForm, "anio", nuevoModelo5_1.getAnioVotacion().substring(nuevoModelo5_1.getAnioVotacion().length() - 2));
        });
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
                ventanaPreaviso.mostrarMensaje(String.format("%s%s: %s", MessageManager.getMessage("error.modificar.campo"), campoID, e.getMessage()), false);
            }
        } else {
            ventanaPreaviso.mostrarMensaje(MessageManager.getMessage("error.campo.texto.no.encontrado") + campoID, false);
        }
    }

    /**
     * Guarda el documento PDF modificado.
     *
     * @param rutaFormularioPDF La ruta del formulario PDF.
     * @param pdfDocument El documento PDF a guardar.
     * @throws IOException Si ocurre un error al guardar el PDF.
     */
    private void guardarPDF(String rutaFormularioPDF, PDDocument pdfDocument) throws IOException {
        pdfDocument.save(rutaFormularioPDF);
        documentosGuardados++;
        if (documentosGuardados == 5) {
            ventanaPreaviso.mostrarMensaje(MessageManager.getMessage("pdf.guardado.exitosamente") + rutaFormularioPDF, true);
            documentosGuardados = 0;
        }
    }
}
