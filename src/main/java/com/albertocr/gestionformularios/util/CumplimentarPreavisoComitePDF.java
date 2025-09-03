package com.albertocr.gestionformularios.util;

import com.albertocr.gestionformularios.model.CalendarioComite;
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
import java.util.Objects;

/**
 * Clase de utilidad para rellenar el formulario PDF del Calendario de Comité.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.4
 */
public final class CumplimentarPreavisoComitePDF {

    private static final Logger logger = LoggerFactory.getLogger(CumplimentarPreavisoComitePDF.class);
    private static final String TEMPLATE_PATH = "/Comite/calendario_comite.pdf";
    private static final String FONT_PATH = "/fonts/LiberationSans-Bold.ttf"; // Actualizado a Bold

    private CumplimentarPreavisoComitePDF() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada.");
    }

    private static PDAcroForm prepareAcroForm(PDDocument pdfDocument) throws IOException {
        PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();
        if (acroForm == null) {
            throw new IOException("El PDF no contiene un formulario AcroForm.");
        }

        try (InputStream fontStream = CumplimentarPreavisoComitePDF.class.getResourceAsStream(FONT_PATH)) {
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

    public static void cumplimentarYGuardar(CalendarioComite calendario, String rutaSalida) throws IOException {
        try (InputStream is = CumplimentarPreavisoComitePDF.class.getResourceAsStream(TEMPLATE_PATH);
             PDDocument pdfDocument = Loader.loadPDF(Objects.requireNonNull(is).readAllBytes())) {

            PDAcroForm acroForm = prepareAcroForm(pdfDocument);

            // Rellenar campos
            setField(acroForm, "nombreEmpresa", calendario.getEmpresa().getNombre());
            setField(acroForm, "cif", calendario.getEmpresa().getCif());
            setField(acroForm, "nombreComercial", calendario.getEmpresa().getNombreComercial());
            setField(acroForm, "numeroId", calendario.getEmpresa().getNumeroId());
            setField(acroForm, "localidad", calendario.getEmpresa().getLocalidad());
            setField(acroForm, "actividad", calendario.getEmpresa().getActividad());
            setField(acroForm, "convenio", calendario.getEmpresa().getConvenio());
            setField(acroForm, "numTrabajadores", String.valueOf(calendario.getEleccion().getNumeroTrabajadores()));
            setField(acroForm, "nombrePresidente", calendario.getNombrePresidente());
            setField(acroForm, "nombreVocal", calendario.getNombreVocal());
            setField(acroForm, "nombreSecretario", calendario.getNombreSecretario());
            setField(acroForm, "horaConstitucion", calendario.getHoraConstitucion());
            setField(acroForm, "diaInicioExpoCenso", calendario.getDiaInicioExposicionCenso());
            setField(acroForm, "mesInicioExpoCenso", calendario.getMesInicioExposicionCenso());
            setField(acroForm, "diaFinExpoCenso", calendario.getDiaFinExposicionCenso());
            setField(acroForm, "mesFinExpoCenso", calendario.getMesFinExposicionCenso());
            setField(acroForm, "anioFinExpoCenso", calendario.getAnioFinExposicionCenso());
            setField(acroForm, "diaReclamacionCenso", calendario.getDiaReclamacionCenso());
            setField(acroForm, "mesReclamacionCenso", calendario.getMesReclamacionCenso());
            setField(acroForm, "anioReclamacionCenso", calendario.getAnioReclamacionCenso());
            setField(acroForm, "diaResolucionCenso", calendario.getDiaResolucionCenso());
            setField(acroForm, "mesResolucionCenso", calendario.getMesResolucionCenso());
            setField(acroForm, "anioResolucionCenso", calendario.getAnioResolucionCenso());
            setField(acroForm, "diaExpoCensoDefinitivo", calendario.getDiaExposicionCensoDefinitivo());
            setField(acroForm, "mesExpoCensoDefinitivo", calendario.getMesExposicionCensoDefinitivo());
            setField(acroForm, "anioExpoCensoDefinitivo", calendario.getAnioExposicionCensoDefinitivo());
            setField(acroForm, "diaInicioPresentacionCandidaturas", calendario.getDiaInicioPresentacionCandidaturas());
            setField(acroForm, "mesInicioPresentacionCandidaturas", calendario.getMesInicioPresentacionCandidaturas());
            setField(acroForm, "diaFinPresentacionCandidaturas", calendario.getDiaFinPresentacionCandidaturas());
            setField(acroForm, "mesFinPresentacionCandidaturas", calendario.getMesFinPresentacionCandidaturas());
            setField(acroForm, "anioFinPresentacionCandidaturas", calendario.getAnioFinPresentacionCandidaturas());
            setField(acroForm, "diaInicioExpoCandidaturas", calendario.getDiaInicioExposicionCandidaturas());
            setField(acroForm, "mesInicioExpoCandidaturas", calendario.getMesInicioExposicionCandidaturas());
            setField(acroForm, "diaFinExpoCandidaturas", calendario.getDiaFinExposicionCandidaturas());
            setField(acroForm, "mesFinExpoCandidaturas", calendario.getMesFinExposicionCandidaturas());
            setField(acroForm, "anioFinExpoCandidaturas", calendario.getAnioFinExposicionCandidaturas());
            setField(acroForm, "diaReclamacionCandidaturas", calendario.getDiaReclamacionCandidaturas());
            setField(acroForm, "mesReclamacionCandidaturas", calendario.getMesReclamacionCandidaturas());
            setField(acroForm, "anioReclamacionCandidaturas", calendario.getAnioReclamacionCandidaturas());
            setField(acroForm, "diaProclamacionCandidaturas", calendario.getDiaProclamacionCandidaturas());
            setField(acroForm, "mesProclamacionCandidaturas", calendario.getMesProclamacionCandidaturas());
            setField(acroForm, "anioProclamacionCandidaturas", calendario.getAnioProclamacionCandidaturas());
            setField(acroForm, "diaInicioPropaganda", calendario.getDiaInicioPropaganda());
            setField(acroForm, "mesInicioPropaganda", calendario.getMesInicioPropaganda());
            setField(acroForm, "diaFinPropaganda", calendario.getDiaFinPropaganda());
            setField(acroForm, "mesFinPropaganda", calendario.getMesFinPropaganda());
            setField(acroForm, "anioFinPropaganda", calendario.getAnioFinPropaganda());
            setField(acroForm, "diaReflexion", calendario.getDiaReflexion());
            setField(acroForm, "mesReflexion", calendario.getMesReflexion());
            setField(acroForm, "anioReflexion", calendario.getAnioReflexion());
            setField(acroForm, "diaVotacion", calendario.getDiaVotacion());
            setField(acroForm, "mesVotacion", calendario.getMesVotacion());
            setField(acroForm, "anioVotacion", calendario.getAnioVotacion());
            setField(acroForm, "horarioVotacion", calendario.getHorarioVotacion());
            setField(acroForm, "lugarVotacion", calendario.getLugarVotacion());
            setField(acroForm, "localidadFirma", calendario.getLocalidadFirma());
            if (calendario.getFechaFirma() != null) {
                setField(acroForm, "fechaFirma", calendario.getFechaFirma().format(DateTimeFormatter.ofPattern("dd/MM/yy")));
            }

            pdfDocument.save(new File(rutaSalida));
            logger.info("PDF de Calendario de Comité guardado en: {}", rutaSalida);
        }
    }

    private static void setField(PDAcroForm acroForm, String fieldName, String value) throws IOException {
        PDField field = acroForm.getField(fieldName);
        if (field != null) {
            field.setValue(value != null ? value : "");
        } else {
            logger.warn("El campo '{}' no se encontró en la plantilla PDF.", fieldName);
        }
    }
}
