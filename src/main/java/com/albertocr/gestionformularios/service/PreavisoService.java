package com.albertocr.gestionformularios.service;

import com.albertocr.gestionformularios.model.Eleccion;
import com.albertocr.gestionformularios.model.EleccionesDAO;
import com.albertocr.gestionformularios.model.Empresa;
import com.albertocr.gestionformularios.model.EmpresaDAO;
import com.albertocr.gestionformularios.model.TipoColegioElectoral;
import com.albertocr.gestionformularios.util.Constantes;
import com.albertocr.gestionformularios.util.ConversorFechaToLetras;
import com.albertocr.gestionformularios.util.DirectorioManager;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Servicio para manejar la lógica de negocio de los preavisos.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.12
 */
public class PreavisoService {

    private static final Logger logger = LoggerFactory.getLogger(PreavisoService.class);
    private static final String FONT_PATH = "/fonts/LiberationSans-Bold.ttf";

    private final EmpresaDAO empresaDAO;
    private final EleccionesDAO eleccionesDAO;

    public PreavisoService(EmpresaDAO empresaDAO, EleccionesDAO eleccionesDAO) {
        this.empresaDAO = empresaDAO;
        this.eleccionesDAO = eleccionesDAO;
    }

    public String procesarPreaviso(Empresa empresa, Eleccion eleccion, TipoColegioElectoral tipoColegio) throws IOException {
        empresaDAO.guardarOActualizar(empresa);

        Optional<Empresa> empresaGuardadaOpt = empresaDAO.buscarPorCif(empresa.getCif());
        if (empresaGuardadaOpt.isEmpty()) {
            throw new IOException("No se pudo recuperar la empresa después de guardarla.");
        }
        Empresa empresaGuardada = empresaGuardadaOpt.get();
        eleccion.setIdEmpresa(empresaGuardada.getId());

        if (!eleccionesDAO.crearEleccion(eleccion)) {
            throw new IOException("No se pudo guardar la elección en la base de datos.");
        }

        return generarDocumentacion(empresaGuardada, eleccion, tipoColegio);
    }

    private String generarDocumentacion(Empresa empresa, Eleccion eleccion, TipoColegioElectoral tipoColegio) throws IOException {
        String nombreDirectorio = "Elecciones" + File.separator + DirectorioManager.sanitizarNombre(empresa.getNombre());
        String rutaBase = System.getProperty("user.home") + File.separator + "Documents";
        File directorioSalida = new File(rutaBase, nombreDirectorio);
        DirectorioManager.crearDirectorioSiNoExiste(directorioSalida.getAbsolutePath());

        Map<String, String> campos = crearMapaDeCampos(empresa, eleccion);

        List<String> documentosAGenerar;
        if (eleccion.getNumeroTrabajadores() < 50) {
            documentosAGenerar = Arrays.asList(Constantes.DOCUMENTACION_DELEGADOS);
        } else {
            documentosAGenerar = Constantes.getDocumentacionComite(tipoColegio);
        }

        for (String nombreDocumento : documentosAGenerar) {
            String templateFolder = findTemplateFolder(nombreDocumento);
            generarDocumento(campos, nombreDocumento, templateFolder, directorioSalida);
        }

        logger.info("Toda la documentación ha sido generada en: {}", directorioSalida.getAbsolutePath());
        return directorioSalida.getAbsolutePath();
    }

    private String findTemplateFolder(String docName) {
        if (docName.equals(Constantes.CALENDARIO_COMITE) ||
            docName.startsWith("modelo_4") ||
            docName.startsWith("modelo_6") ||
            docName.startsWith("modelo_7")) {
            return "/Comite/";
        }
        return "/Delegados/";
    }

    private void generarDocumento(Map<String, String> campos, String nombreDocumento, String templateFolder, File directorioSalida) throws IOException {
        String templatePath = templateFolder + nombreDocumento + Constantes.EXTENSION_ARCHIVO;
        String outputFileName = nombreDocumento + " " + DirectorioManager.sanitizarNombre(campos.get("nombreEmpresa")) + Constantes.EXTENSION_ARCHIVO;
        File archivoSalida = new File(directorioSalida, outputFileName);

        try (InputStream templateStream = getClass().getResourceAsStream(templatePath);
             PDDocument pdfDocument = Loader.loadPDF(Objects.requireNonNull(templateStream, "No se pudo encontrar la plantilla: " + templatePath).readAllBytes())) {

            PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();
            if (acroForm != null) {
                prepareAcroForm(pdfDocument, acroForm);

                Map<String, String> finalCampos = new HashMap<>(campos);

                // Lógica de formato específica para ciertas plantillas
                if (nombreDocumento.equals(Constantes.CALENDARIO_COMITE)) {
                    finalCampos.put("fechaConstitucion", campos.get("fechaConstitucionFormatoDe"));
                }
                if (nombreDocumento.equals(Constantes.MODELO_9)) {
                    handleModelo9Visibility(acroForm, finalCampos.get("tipoEleccion"));
                }

                for (Map.Entry<String, String> entry : finalCampos.entrySet()) {
                    setField(acroForm, entry.getKey(), entry.getValue());
                }
            } else {
                logger.warn("El PDF '{}' no contiene un formulario AcroForm, se guardará una copia en blanco.", templatePath);
            }

            pdfDocument.save(archivoSalida);
            logger.info("Documento generado exitosamente en: {}", archivoSalida.getAbsolutePath());

        } catch (Exception e) {
            logger.error("Error al procesar el documento '{}'", nombreDocumento, e);
            throw new IOException("Error al generar: " + nombreDocumento, e);
        }
    }

    private void handleModelo9Visibility(PDAcroForm acroForm, String tipoEleccion) {
        boolean esTotal = "TOTAL".equals(tipoEleccion);
        setFieldVisibility(acroForm, "total", !esTotal);
        setFieldVisibility(acroForm, "parcial", esTotal);
    }

    private void setFieldVisibility(PDAcroForm acroForm, String fieldName, boolean visible) {
        PDField field = acroForm.getField(fieldName);
        if (field != null) {
            for (PDAnnotationWidget widget : field.getWidgets()) {
                widget.setNoView(!visible);
                widget.setPrinted(visible);
            }
        } else {
            logger.warn("Campo de visibilidad no encontrado en el PDF: {}", fieldName);
        }
    }

    private void prepareAcroForm(PDDocument pdfDocument, PDAcroForm acroForm) throws IOException {
        try (InputStream fontStream = getClass().getResourceAsStream(FONT_PATH)) {
            if (fontStream == null) {
                throw new IOException("No se pudo encontrar el archivo de fuente en: " + FONT_PATH);
            }
            PDType0Font font = PDType0Font.load(pdfDocument, fontStream);
            PDResources resources = acroForm.getDefaultResources();
            if (resources == null) resources = new PDResources();
            String fontName = resources.add(font).getName();
            acroForm.setDefaultResources(resources);
            acroForm.setDefaultAppearance("/" + fontName + " 10 Tf 0 g");
        } catch (Exception e) {
            logger.error("Error al cargar o incrustar la fuente.", e);
            throw new IOException("Fallo al procesar la fuente del PDF.", e);
        }
    }

    private void setField(PDAcroForm acroForm, String fieldName, String value) throws IOException {
        PDField field = acroForm.getField(fieldName);
        if (field != null) {
            field.setValue(value != null ? value : "");
        }
    }

    private Map<String, String> crearMapaDeCampos(Empresa empresa, Eleccion eleccion) {
        Map<String, String> campos = new HashMap<>();
        LocalDate fechaConstitucion = eleccion.getFechaConstitucion();

        boolean centroVacio = empresa.getNombreCentro() == null || empresa.getNombreCentro().isBlank();
        campos.put("nombreEmpresa", empresa.getNombre());
        campos.put("nombreCentro", centroVacio ? empresa.getNombre() : empresa.getNombreCentro());
        campos.put("municipioCentro", centroVacio ? empresa.getMunicipio() : empresa.getMunicipio());
        campos.put("direccionCentro", centroVacio ? empresa.getDireccion() : empresa.getDireccion());

        campos.put("CIF", empresa.getCif());
        campos.put("nombreComercial", empresa.getNombreComercial());
        campos.put("direccion", empresa.getDireccion());
        campos.put("municipio", empresa.getMunicipio());
        campos.put("comarca", empresa.getComarca());
        campos.put("provincia", empresa.getProvincia());
        campos.put("codigoPostal", empresa.getCodigoPostal());
        campos.put("totalTrabajadores", String.valueOf(eleccion.getNumeroTrabajadores()));
        campos.put("numeroISS", empresa.getNumeroISS());
        campos.put("mesElecciones", ConversorFechaToLetras.getMes(fechaConstitucion.getMonthValue()).toUpperCase());
        campos.put("anioElecciones", String.valueOf(fechaConstitucion.getYear()));
        campos.put("tipoEleccion", "TOTAL".equals(eleccion.getTipoEleccion()) ? "TOTAL" : "PARCIAL");
        campos.put("promotores", eleccion.getPromotores());

        // Número de mesa (por defecto 1, con lógica futura documentada)
        // TODO: Implementar cálculo de mesas: 1 por cada 250 trabajadores o fracción.
        campos.put("numeroMesa", "1");

        campos.put("diaPreaviso", String.valueOf(eleccion.getFecha().getDayOfMonth()));
        campos.put("mesPreaviso", ConversorFechaToLetras.getMes(eleccion.getFecha().getMonthValue()).toUpperCase());
        campos.put("anioPreaviso", String.format("%02d", eleccion.getFecha().getYear() % 100));
        campos.put("fechaConstitucionLetras", ConversorFechaToLetras.convertirFechaGuiones(fechaConstitucion).toUpperCase());

        campos.put("diaConstitucion", String.valueOf(fechaConstitucion.getDayOfMonth()));
        campos.put("mesConstitucion", ConversorFechaToLetras.getMes(fechaConstitucion.getMonthValue()).toUpperCase());
        campos.put("anioConstitucion", String.format("%02d", fechaConstitucion.getYear() % 100));

        String fechaCompletaLetras = ConversorFechaToLetras.convertirFechaEnLetras(fechaConstitucion);
        String fechaNumerica = fechaConstitucion.format(DateTimeFormatter.ofPattern("(dd/MM/yyyy)"));
        campos.put("fechaConstitucionTodoLetras", fechaCompletaLetras + "     " + fechaNumerica);

        // Formatos de fecha estándar y específicos
        campos.put("fechaConstitucion", fechaConstitucion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        campos.put("fechaConstitucionFormatoDe", ConversorFechaToLetras.convertirFechaDe(fechaConstitucion));

        return campos;
    }
}
