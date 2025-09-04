package com.albertocr.gestionformularios.controller.escrutinio;

import com.albertocr.gestionformularios.interfaz.escrutinio.VentanaGestionCandidatos;
import com.albertocr.gestionformularios.model.CandidatosDAO;
import com.albertocr.gestionformularios.model.Candidato;
import com.albertocr.gestionformularios.service.EscrutinioService;
import com.albertocr.gestionformularios.service.dto.ActaComiteData;
import com.albertocr.gestionformularios.util.AlertManager;
import com.albertocr.gestionformularios.util.DirectorioManager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
// import javafx.stage.FileChooser;  // Eliminado: ahora la carga del JSON es automática
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

/**
 * Controlador para la ventana de escrutinio de comités (escrutinio-comite-view.fxml).
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.2
 */
public class EscrutinioComiteController {

    private static final Logger logger = LoggerFactory.getLogger(EscrutinioComiteController.class);

    // --- Inner Class for Dynamic Fields ---
    private static class ColegioFields {
        String titulo;
        TextField totalElectores = new TextField();
        TextField varones = new TextField();
        TextField mujeres = new TextField();

        ColegioFields(String titulo) {
            this.titulo = titulo;
        }
    }

    // --- Injected by FXML ---
    @FXML private Label lblNombreEmpresa, lblNombreComercial, lblCif, lblNombreCentro, lblDireccionCentro, lblMunicipioCentro, lblProvincia, lblFechaConstitucion, lblTrabajadoresEventualesComputo, lblTotalTrabajadores;
    @FXML private TextField tfPreaviso;
    @FXML private TextField tfActividadEconomica, tfNombreConvenio, tfNumeroConvenio, tfTrabajadoresFijos, tfTrabajadoresJornadas;
    @FXML private VBox panelColegios;
    @FXML private Button btnGestionarCandidatos, btnGuardarPDF;
    // Campos adicionales para validación entre Sección D y Sección E
    // Los totales de electores por colegio se extraen de los campos dinámicos dentro de listaColegiosFields

    // --- Injected via constructor ---
    private final ActaComiteData initialActaData;
    @SuppressWarnings("unused")
    private final EscrutinioService escrutinioService;

    // --- State ---
    private List<Candidato> candidatos;
    private final List<ColegioFields> listaColegiosFields = new ArrayList<>();
    private final CandidatosDAO candidatosDAO = new CandidatosDAO();

    public EscrutinioComiteController(ActaComiteData actaData, EscrutinioService escrutinioService) {
        this.initialActaData = actaData;
        this.escrutinioService = escrutinioService;
        this.candidatos = new ArrayList<>();
    }

    @FXML
    /**
     * Inicializa la ventana de escrutinio de comité.
     * Configura bindings, listeners y estructura visual.
     */
    public void initialize() {
        poblarDatosGenerales();
        construirSeccionElectoral();
        configurarValidacionPreaviso();
    configurarCalculosSeccionD();
        configurarValidacionSeccionesDvsE();
    }

    private void configurarValidacionSeccionesDvsE() {
        // Añadir listeners a fijos y jornadas para revalidar
        javafx.beans.value.ChangeListener<String> listener = (obs, oldVal, newVal) -> validarSeccionesDvsE();
        tfTrabajadoresFijos.textProperty().addListener(listener);
        tfTrabajadoresJornadas.textProperty().addListener(listener);
        // Añadir listeners a los campos de electores en cada colegio para revalidar
        // Se añadirán dinámicamente cuando se construyan los paneles de colegios
    }

    /**
     * Valida que el total de trabajadores a efectos de cómputo coincida con la suma de electores de ambos colegios.
     * @return true si coinciden o no hay datos suficientes, false si hay discrepancia
     */
    private boolean validarSeccionesDvsE() {
        try {
            int fijos = Integer.parseInt(tfTrabajadoresFijos.getText());
            // eventuales a efectos de cómputo ya calculados en calcularSeccionD (ceil)
            double eventualesComputo = Double.parseDouble(lblTrabajadoresEventualesComputo.getText());
            int totalTrabajadores = fijos + (int) Math.ceil(eventualesComputo);

            int sumaElectores = 0;
            for (ColegioFields fields : listaColegiosFields) {
                try {
                    int total = Integer.parseInt(fields.totalElectores.getText());
                    sumaElectores += total;
                } catch (NumberFormatException ignored) { }
            }

            if (sumaElectores == 0) return true; // no hay datos electorales introducidos aún

            if (totalTrabajadores != sumaElectores) {
                // Mostrar advertencia visual y devolver false
                AlertManager.mostrarAlertaAdvertencia(getBundle().getString("warning.validacion.titulo"),
                        getBundle().getString("warning.secciones.d_e.discrepancia").replace("{0}", String.valueOf(totalTrabajadores)).replace("{1}", String.valueOf(sumaElectores)));
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return true; // datos incompletos o no numéricos
        }
    }

    /**
     * Configura la validación del campo de preaviso.
     * Solo permite formato XXXX/YYYY y años válidos.
     */
    private void configurarValidacionPreaviso() {
        tfPreaviso.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) { // perdió el foco
                validarPreaviso();
            }
        });
    }

    /**
     * Valida el campo de preaviso según el formato XXXX/YYYY y años válidos.
     * Muestra alerta si no es válido.
     * @return true si es válido, false si no
     */
    private boolean validarPreaviso() {
        String valor = tfPreaviso.getText().trim();
        if (valor.isEmpty()) return false;
        // Formato: hasta 4 dígitos, barra, año 2024 o 2025
        if (!valor.matches("\\d{1,4}/(2024|2025)")) {
            AlertManager.mostrarAlertaError(
                getBundle().getString("error.formato.preaviso.invalido.titulo"),
                getBundle().getString("error.formato.preaviso.invalido.mensaje"));
            tfPreaviso.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * Pone los datos generales en los campos correspondientes.
     */
    private void poblarDatosGenerales() {
        // Si no hay preaviso, mostrar un ejemplo como promptText con el año actual
        String defaultPreaviso = "123/" + java.time.Year.now().getValue();
        String preaviso = initialActaData.numeroPreaviso();
        if (preaviso == null || preaviso.isBlank()) {
            tfPreaviso.clear();
            tfPreaviso.setPromptText(defaultPreaviso);
        } else {
            tfPreaviso.setText(preaviso);
        }
        lblNombreEmpresa.setText(valorOPlaceholder(initialActaData.nombreEmpresa(), "—"));
        lblNombreComercial.setText(valorOPlaceholder(initialActaData.nombreComercial(), "—"));
        lblCif.setText(valorOPlaceholder(initialActaData.cif(), "—"));
        tfActividadEconomica.setText(valorOPlaceholder(initialActaData.actividadEconomica(), ""));
        tfNombreConvenio.setText(valorOPlaceholder(initialActaData.nombreConvenio(), ""));
        tfNumeroConvenio.setText(valorOPlaceholder(initialActaData.numeroConvenio(), ""));
        lblNombreCentro.setText(valorOPlaceholder(initialActaData.nombreCentro(), "—"));
        lblDireccionCentro.setText(valorOPlaceholder(initialActaData.direccionCentro(), "—"));
        lblMunicipioCentro.setText(valorOPlaceholder(initialActaData.municipioCentro(), "—"));
        lblProvincia.setText(valorOPlaceholder(initialActaData.provincia(), "—"));
        lblFechaConstitucion.setText(valorOPlaceholder(initialActaData.fechaConstitucionLetras(), "—"));
        tfTrabajadoresFijos.setText(String.valueOf(initialActaData.trabajadoresFijos()));
        tfTrabajadoresJornadas.setText(String.valueOf(initialActaData.trabajadoresJornadas()));
    }

    /**
     * Configura los listeners y cálculos para la Sección D (trabajadores y eventuales a efectos de cómputo).
     */
    private void configurarCalculosSeccionD() {
        javafx.beans.value.ChangeListener<String> listener = (obs, oldVal, newVal) -> calcularSeccionD();
        tfTrabajadoresFijos.textProperty().addListener(listener);
        tfTrabajadoresJornadas.textProperty().addListener(listener);
        // Calcular inicialmente
        calcularSeccionD();
    }

    private void calcularSeccionD() {
        try {
            int fijos = Integer.parseInt(tfTrabajadoresFijos.getText());
            int jornadas = Integer.parseInt(tfTrabajadoresJornadas.getText());
            double eventualesComputo = jornadas > 0 ? ((double) jornadas) / 200.0 : 0.0;
            int totalComputo = fijos + (int) Math.ceil(eventualesComputo);
            lblTrabajadoresEventualesComputo.setText(String.format("%.2f", eventualesComputo));
            lblTotalTrabajadores.setText(String.valueOf(totalComputo));
        } catch (NumberFormatException e) {
            lblTrabajadoresEventualesComputo.setText("0.00");
            lblTotalTrabajadores.setText("0");
        }
    }

    /**
     * Construye la sección de colegios electorales de forma compacta y visualmente clara.
     */
    private void construirSeccionElectoral() {
        panelColegios.getChildren().clear();
        listaColegiosFields.clear();
        ResourceBundle bundleCustom = ResourceBundle.getBundle("messages_es_custom");
        if ("Colegio Único".equalsIgnoreCase(initialActaData.tipoColegio())) {
            logger.info("Construyendo vista para Colegio Único.");
            panelColegios.getChildren().add(crearPanelColegio("Colegio Único"));
            btnGestionarCandidatos.setVisible(true);
        } else {
            logger.info("Construyendo vista para colegios separados.");
            panelColegios.getChildren().add(crearPanelColegio(bundleCustom.getString("comite.colegio.especialistas")));
            panelColegios.getChildren().add(crearPanelColegio(bundleCustom.getString("comite.colegio.tecnicos")));
            btnGestionarCandidatos.setVisible(false);
        }
    }

    /**
     * Crea el panel de un colegio con los campos y, si corresponde, el botón para gestionar candidatos.
     * @param titulo Nombre del colegio
     * @return TitledPane con los controles del colegio
     */
    /**
     * Crea el panel compacto de un colegio con campos y botón de gestión.
     * @param titulo Nombre del colegio
     * @return TitledPane con controles alineados horizontalmente
     */
    private TitledPane crearPanelColegio(String titulo) {
        ColegioFields fields = new ColegioFields(titulo);
        listaColegiosFields.add(fields);
        ResourceBundle bundleMain = ResourceBundle.getBundle("messages_es");
        ResourceBundle bundleCustom = ResourceBundle.getBundle("messages_es_custom");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(5);
        grid.setPadding(new Insets(5));

        // Fila 1: Varones, Total
        grid.add(new Label(bundleMain.getString("conclusion.electores_varones")), 0, 0);
        grid.add(fields.varones, 1, 0);
        grid.add(new Label(bundleMain.getString("conclusion.total_electores")), 2, 0);
        grid.add(fields.totalElectores, 3, 0);
        // Fila 2: Mujeres, Botón
        grid.add(new Label(bundleMain.getString("conclusion.electores_mujeres")), 0, 1);
        grid.add(fields.mujeres, 1, 1);
        Button btnGestionar = null;
        if (titulo.equalsIgnoreCase(bundleCustom.getString("comite.colegio.especialistas"))) {
            btnGestionar = new Button(bundleCustom.getString("comite.boton.gestionar_candidatos_especialistas"));
        } else if (titulo.equalsIgnoreCase(bundleCustom.getString("comite.colegio.tecnicos"))) {
            btnGestionar = new Button(bundleCustom.getString("comite.boton.gestionar_candidatos_tecnicos"));
        }
        if (btnGestionar != null) {
            btnGestionar.setOnAction(e -> handleGestionarCandidatosColegio(titulo));
            grid.add(btnGestionar, 2, 1, 2, 1);
        }
        TitledPane titledPane = new TitledPane(titulo, grid);
        titledPane.setCollapsible(false);
        return titledPane;
    }

    /**
     * Abre la ventana de gestión de candidatos para el colegio indicado, con título dinámico.
     * @param colegio Nombre del colegio
     */
    /**
     * Abre la ventana de gestión de candidatos para el colegio indicado, con título dinámico i18n.
     * @param colegio Nombre del colegio
     */
    private void handleGestionarCandidatosColegio(String colegio) {
        logger.info("Abriendo la ventana de gestión de candidatos para el colegio: {}", colegio);
        try {
            ResourceBundle bundleCustom = ResourceBundle.getBundle("messages_es_custom");
            String tituloVentana;
            if (colegio.equalsIgnoreCase(bundleCustom.getString("comite.colegio.especialistas"))) {
                tituloVentana = bundleCustom.getString("comite.titulo.ventana_candidatos_especialistas");
            } else if (colegio.equalsIgnoreCase(bundleCustom.getString("comite.colegio.tecnicos"))) {
                tituloVentana = bundleCustom.getString("comite.titulo.ventana_candidatos_tecnicos");
            } else {
                ResourceBundle bundle = getBundle();
                tituloVentana = bundle.getString("gestion.candidatos.titulo") + " - " + colegio;
            }
            // Cargar candidatos filtrados por colegio cuando se gestione un colegio concreto
            List<Candidato> candidatosParaColegio = this.candidatos;
            try {
                candidatosParaColegio = candidatosDAO.buscarPorColegio(colegio);
            } catch (Exception ex) {
                logger.warn("No se pudieron cargar candidatos filtrados por colegio, usando la lista en memoria.", ex);
            }
            VentanaGestionCandidatos ventanaCandidatos = new VentanaGestionCandidatos(candidatosParaColegio, tituloVentana);
            ventanaCandidatos.showAndWait();
            // Si se gestionó un colegio específico, actualizar sólo los candidatos de ese colegio
            List<Candidato> actualizados = ventanaCandidatos.getCandidatosActualizados();
            // Persistir cambios en la BBDD y actualizar la lista global
            for (Candidato c : actualizados) {
                c.setColegio(colegio);
                new CandidatosDAO().guardarOActualizar(c);
            }
            // Recargar la lista global para reflejar cambios
            this.candidatos = candidatosDAO.buscarTodos();
            logger.info("Ventana de gestión de candidatos cerrada. Se tienen {} candidatos.", this.candidatos.size());
        } catch (Exception e) {
            logger.error("Error al abrir la ventana de gestión de candidatos", e);
            AlertManager.mostrarAlertaError(getBundle().getString("error.titulo"), getBundle().getString("error.abrir.ventana.candidatos"));
        }
    }

    /**
     * Abre la ventana de gestión de candidatos (modo único/colegio único).
     */
    @FXML
    private void handleGestionarCandidatos() {
        logger.info("Abriendo la ventana de gestión de candidatos.");
        try {
            ResourceBundle bundle = getBundle();
            String tituloVentana = bundle.getString("gestion.candidatos.titulo");
            VentanaGestionCandidatos ventanaCandidatos = new VentanaGestionCandidatos(this.candidatos, tituloVentana);
            ventanaCandidatos.showAndWait();
            this.candidatos = ventanaCandidatos.getCandidatosActualizados();
            logger.info("Ventana de gestión de candidatos cerrada. Se tienen {} candidatos.", this.candidatos.size());
        } catch (Exception e) {
            logger.error("Error al abrir la ventana de gestión de candidatos", e);
            AlertManager.mostrarAlertaError(getBundle().getString("error.titulo"), getBundle().getString("error.abrir.ventana.candidatos"));
        }
    }

    @FXML
    private void handleGuardarPDF() {
        // 1) Validaciones previas
        if (!validarFormulario()) return;
        if (!validarSeccionesDvsE()) return; // coherencia entre secciones D y E

        try {
            // 2) Intentar cargar el JSON consolidado en la ruta indicada; si no existe, generarlo
            Path raiz = DirectorioManager.crearDirectorioRaiz();
            Path jsonPath = Paths.get(raiz.toString(), "Elecciones", "Listado Campos", "Listado Campos y Tipo JSON.json");
            Files.createDirectories(jsonPath.getParent());

            List<Mapping> mappings;
            if (Files.exists(jsonPath)) {
                mappings = loadMappingsFromJson(jsonPath);
            } else {
                mappings = generarYGuardarMapeoJson(jsonPath);
            }
            if (mappings == null || mappings.isEmpty()) {
                AlertManager.mostrarAlertaError(getBundle().getString("error.titulo"),
                        getBundle().getString("error.mapeo.json.invalido"));
                return;
            }

            // 3) Seleccionar el PDF objetivo según el contexto (tipo de colegio) o fallback
            Mapping target = seleccionarMappingObjetivo(mappings);
            if (target == null || target.pdfName() == null || target.pdfName().isBlank()) {
                AlertManager.mostrarAlertaError(getBundle().getString("error.titulo"),
                        getBundle().getString("error.mapeo.json.invalido"));
                return;
            }

            // 4) Preparar carpeta de empresa dentro de Documentos/Elecciones
            String nombreEmpresa = valorOPlaceholder(initialActaData.nombreEmpresa(), "Empresa");
            Path carpetaEmpresa = DirectorioManager.crearDirectorioEmpresa(raiz, nombreEmpresa);

            // 5) Asegurar que el PDF objetivo existe; si no, copiar desde recursos /Comite/
            Path destinoPdf = carpetaEmpresa.resolve(target.pdfName());
            if (Files.notExists(destinoPdf)) {
                copiarRecursoPDFA(destinoPdf, "/Comite/" + target.pdfName());
                logger.info("Plantilla copiada a {}", destinoPdf);
            }

            // 6) Rellenar campos con valores del formulario/acta usando el mapeo
            rellenarPdfConDatos(destinoPdf, target);

            AlertManager.mostrarAlertaInformacion(
                    getBundle().getString("info.title"),
                    getBundle().getString("info.pdf.guardado").replace("{0}", destinoPdf.toAbsolutePath().toString())
            );
        } catch (Exception ex) {
            logger.error("Error al generar/guardar el PDF de comité", ex);
            AlertManager.mostrarAlertaError(getBundle().getString("error.titulo"),
                    getBundle().getString("error.pdf.guardado"));
        }
    }

    /**
     * Valida el formulario completo, incluyendo preaviso y datos electorales.
     * @return true si todo es válido
     */
    private boolean validarFormulario() {
        if (!validarPreaviso()) return false;
        if (tfActividadEconomica.getText().isBlank() || tfNombreConvenio.getText().isBlank() || tfNumeroConvenio.getText().isBlank() ||
            tfTrabajadoresFijos.getText().isBlank() || tfTrabajadoresJornadas.getText().isBlank()) {
            AlertManager.mostrarAlertaError(getBundle().getString("error.validacion.titulo"), getBundle().getString("error.validacion.campos.obligatorios"));
            return false;
        }
        for (ColegioFields fields : listaColegiosFields) {
            try {
                int total = Integer.parseInt(fields.totalElectores.getText());
                int varones = Integer.parseInt(fields.varones.getText());
                int mujeres = Integer.parseInt(fields.mujeres.getText());
                if (varones + mujeres != total) {
                    AlertManager.mostrarAlertaError(getBundle().getString("error.validacion.titulo"),
                            getBundle().getString("error.validacion.electores.suma").replace("{0}", fields.titulo));
                    return false;
                }
            } catch (NumberFormatException e) {
                AlertManager.mostrarAlertaError(getBundle().getString("error.validacion.titulo"), getBundle().getString("error.validacion.electores.formato").replace("{0}", fields.titulo));
                return false;
            }
        }
        return true;
    }

    /**
     * Recopila los datos actuales de la ventana para el acta.
     * @return ActaComiteData con los datos actuales
     */
    @SuppressWarnings("unused")
    private ActaComiteData recopilarDatosDeLaVentana() {
        // Nota: Esta recopilación es parcial. Faltarían los datos de los colegios.
        // La estructura de ActaComiteData debería ser extendida para soportar múltiples colegios.
        return new ActaComiteData(
                tfPreaviso.getText(),
                lblNombreEmpresa.getText(),
                lblNombreComercial.getText(),
                lblCif.getText(),
                tfActividadEconomica.getText(),
                tfNombreConvenio.getText(),
                tfNumeroConvenio.getText(),
                lblNombreCentro.getText(),
                lblDireccionCentro.getText(),
                lblMunicipioCentro.getText(),
                lblProvincia.getText(),
                lblFechaConstitucion.getText(),
                Integer.parseInt(tfTrabajadoresFijos.getText()),
                0, // Eventuales no está en esta vista
                Integer.parseInt(tfTrabajadoresJornadas.getText()),
                initialActaData.tipoColegio(),
                this.candidatos
        );
    }
    /**
     * Devuelve el ResourceBundle principal para i18n.
     */
    private ResourceBundle getBundle() {
        return ResourceBundle.getBundle("messages_es");
    }

    private String valorOPlaceholder(String valor, String placeholder) {
        return (valor == null || valor.isBlank()) ? placeholder : valor;
    }

    // =====================
    // Utilidades de guardado
    // =====================

    /**
     * Copia una plantilla PDF desde el classpath a la ruta de destino indicada.
     * Si no se encuentra el recurso, lanza IOException.
     */
    private void copiarRecursoPDFA(Path destino, String recursoClasspath) throws IOException {
        Objects.requireNonNull(destino);
        Objects.requireNonNull(recursoClasspath);
        Files.createDirectories(destino.getParent());
        try (InputStream in = getClass().getResourceAsStream(recursoClasspath)) {
            if (in == null) {
                throw new IOException("No se encuentra la plantilla en recursos: " + recursoClasspath);
            }
            byte[] bytes = in.readAllBytes();
            Files.write(destino, bytes);
        }
    }

    /**
     * Carga un mapeo desde el JSON generado por el Inspector de PDFs.
     * Formato soportado: [ { "pdf": "archivo.pdf", "fields": [ {"name":"campo","type":"tipo"}, ... ] } ]
     */
    private List<Mapping> loadMappingsFromJson(Path jsonPath) throws IOException {
        String content = Files.readString(jsonPath);
        String trimmed = content.trim();
        List<Mapping> result = new ArrayList<>();
        if (!trimmed.startsWith("[")) {
            return result;
        }
        // Partir por objetos de primer nivel con "pdf":
        java.util.regex.Pattern objPat = java.util.regex.Pattern.compile("\\{[^}]*\\\"pdf\\\"[^}]*}\\s*(,|])");
        java.util.regex.Matcher objMat = objPat.matcher(trimmed + "]");
        while (objMat.find()) {
            String obj = objMat.group();
            java.util.regex.Matcher mPdf = java.util.regex.Pattern
                    .compile("\\\"pdf\\\"\\s*:\\s*\\\"([^\\\"]+)\\\"")
                    .matcher(obj);
            String pdfName = null;
            if (mPdf.find()) pdfName = mPdf.group(1);
            List<FieldInfo> fields = new ArrayList<>();
            java.util.regex.Matcher mFields = java.util.regex.Pattern
                    .compile("\\{\\s*\\\"name\\\"\\s*:\\s*\\\"([^\\\"]+)\\\"\\s*,\\s*\\\"type\\\"\\s*:\\s*\\\"([^\\\"]*)\\\"\\s*}\\s*")
                    .matcher(obj);
            while (mFields.find()) {
                fields.add(new FieldInfo(mFields.group(1), mFields.group(2)));
            }
            if (pdfName != null && !fields.isEmpty()) {
                result.add(new Mapping(pdfName, fields));
            }
        }
        return result;
    }

    /**
     * Genera el JSON consolidado con los campos y tipos de los PDFs de Comité y lo guarda en destinoJson.
     * Devuelve la estructura de mapeo generada.
     */
    private List<Mapping> generarYGuardarMapeoJson(Path destinoJson) throws IOException {
        // Lista de PDFs de Comité, similar a PdfInspectorController
        String[] comitePdfs = new String[] {
                "calendario_comite.pdf",
                "modelo_4_Especialistas.pdf",
                "modelo_4_Tecnicos.pdf",
                "modelo_4_Unico.pdf",
                "modelo_6_1_Especialistas.pdf",
                "modelo_6_1_Tecnicos.pdf",
                "modelo_6_1_Unico.pdf",
                "modelo_6_2_Especialistas.pdf",
                "modelo_6_2_Tecnicos.pdf",
                "modelo_6_2_Unico.pdf",
                "modelo_6_3_Especialistas.pdf",
                "modelo_6_3_Tecnicos.pdf",
                "modelo_6_3_Unico.pdf",
                "modelo_7_1.pdf",
                "modelo_7_2.pdf",
                "modelo_7_3_acta_global.pdf",
                "modelo_7_3_anexo.pdf",
                "modelo_7_3_proceso.pdf"
        };

        List<Mapping> mappings = new ArrayList<>();
        for (String pdfName : comitePdfs) {
            String resourcePath = "/Comite/" + pdfName;
            try (InputStream in = getClass().getResourceAsStream(resourcePath)) {
                if (in == null) continue;
                try (PDDocument doc = Loader.loadPDF(in.readAllBytes())) {
                    PDAcroForm acro = doc.getDocumentCatalog().getAcroForm();
                    if (acro == null) continue;
                    List<FieldInfo> fields = new ArrayList<>();
                    for (PDField f : acro.getFields()) {
                        String name = f.getFullyQualifiedName();
                        String type = f.getCOSObject().getNameAsString(COSName.FT);
                        if (type == null) type = "";
                        fields.add(new FieldInfo(name, type));
                    }
                    if (!fields.isEmpty()) {
                        mappings.add(new Mapping(pdfName, fields));
                    }
                }
            }
        }

        // Serializar a JSON simple
        String json = buildJson(mappings);
        Files.writeString(destinoJson, json);
        logger.info("Mapeo JSON generado y guardado en: {}", destinoJson.toAbsolutePath());
        return mappings;
    }

    /** Selección del PDF objetivo según el tipo de colegio o fallback. */
    private Mapping seleccionarMappingObjetivo(List<Mapping> mappings) {
        if (mappings == null || mappings.isEmpty()) return null;
        String tipo = initialActaData.tipoColegio();
        String preferido = null;
        if ("Colegio Único".equalsIgnoreCase(tipo)) {
            preferido = "modelo_6_1_Unico.pdf";
        } else {
            // Por defecto especialistas
            preferido = "modelo_6_1_Especialistas.pdf";
        }
        for (Mapping m : mappings) {
            if (m.pdfName().equalsIgnoreCase(preferido)) return m;
        }
        // Fallbacks
        for (String alt : new String[]{"modelo_7_3_acta_global.pdf", "modelo_7_1.pdf"}) {
            for (Mapping m : mappings) {
                if (m.pdfName().equalsIgnoreCase(alt)) return m;
            }
        }
        return mappings.get(0);
    }

    /** Serializa lista de mapeos a JSON. */
    private String buildJson(List<Mapping> mappings) {
        String indent = "  ";
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < mappings.size(); i++) {
            Mapping m = mappings.get(i);
            sb.append(indent).append("{\n");
            sb.append(indent).append(indent).append("\"pdf\": \"").append(escape(m.pdfName())).append("\",\n");
            sb.append(indent).append(indent).append("\"fields\": [\n");
            List<FieldInfo> fields = m.fields();
            for (int j = 0; j < fields.size(); j++) {
                FieldInfo f = fields.get(j);
                sb.append(indent).append(indent).append(indent).append("{")
                  .append("\"name\": \"").append(escape(f.name())).append("\", ")
                  .append("\"type\": \"").append(escape(f.type())).append("\"}");
                if (j < fields.size() - 1) sb.append(",");
                sb.append("\n");
            }
            sb.append(indent).append(indent).append("]\n");
            sb.append(indent).append("}");
            if (i < mappings.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("]\n");
        return sb.toString();
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    /**
     * Rellena el PDF de destino utilizando el mapeo de campos y los valores actuales del formulario.
     */
    private void rellenarPdfConDatos(Path destinoPdf, Mapping mapping) throws IOException {
        try (PDDocument document = Loader.loadPDF(Files.readAllBytes(destinoPdf))) {
            PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
            if (acroForm == null) {
                logger.warn("El PDF no contiene AcroForm: {}", destinoPdf);
            } else {
                for (FieldInfo fi : mapping.fields()) {
                    PDField field = acroForm.getField(fi.name());
                    if (field == null) continue;
                    String valor = valorParaCampo(fi.name());
                    if (valor != null) {
                        try {
                            field.setValue(valor);
                        } catch (Exception e) {
                            logger.warn("No se pudo establecer el valor del campo '{}': {}", fi.name(), e.getMessage());
                        }
                    }
                }
                // Aplanar formulario para evitar ediciones accidentales posteriores
                try { acroForm.flatten(); } catch (Exception ignore) { }
            }
            document.save(destinoPdf.toFile());
        }
    }

    /**
     * Obtiene el valor adecuado para un nombre de campo PDF conocido.
     * Implementa mapeos frecuente: empresa, centro, CIF, convenio, preaviso y totales.
     * Para campos por colegio, usa heurística por nombre (varones/mujeres/total + especialistas/técnicos/único).
     */
    private String valorParaCampo(String fieldName) {
        if (fieldName == null) return null;
        String fn = fieldName.trim();
        String fnLower = fn.toLowerCase(Locale.ROOT);

        // A: Generales
        if (fn.equals("numeroPreaviso") || fnLower.contains("preaviso")) return tfPreaviso.getText();

        // B: Empresa
        if (fn.equals("nombreEmpresa") || fnLower.contains("nombreempresa")) return lblNombreEmpresa.getText();
        if (fn.equals("nombreComercial") || fnLower.contains("nombrecomercial")) return lblNombreComercial.getText();
        if (fn.equals("CIF") || fnLower.equals("cif")) return lblCif.getText();
        if (fn.equals("actividadEconomica") || fnLower.contains("actividad")) return tfActividadEconomica.getText();
        if (fn.equals("nombreConvenio") || fnLower.contains("convenio_nombre")) return tfNombreConvenio.getText();
        if (fn.equals("numeroConvenio") || fnLower.contains("convenio_numero")) return tfNumeroConvenio.getText();

        // C: Centro
        if (fn.equals("nombreCentro") || fnLower.contains("nombrecentro")) return lblNombreCentro.getText();
        if (fn.equals("direccion") || fnLower.contains("direccion")) return lblDireccionCentro.getText();
        if (fn.equals("municipio") || fnLower.contains("municipio")) return lblMunicipioCentro.getText();
        if (fn.equals("provincia") || fnLower.contains("provincia")) return lblProvincia.getText();
        if (fn.equals("fechaConstitucionLetras") || fnLower.contains("fechaconstitucion")) return lblFechaConstitucion.getText();

        // D: Totales trabajadores
        if (fn.equals("trabajadoresFijos") || fnLower.contains("fijos")) return tfTrabajadoresFijos.getText();
        if (fn.equals("eventualesComputo") || fnLower.contains("eventuales") && fnLower.contains("comput")) return lblTrabajadoresEventualesComputo.getText();
        if (fn.equals("totalTrabajadoresComputo") || fnLower.contains("total") && fnLower.contains("comput")) return lblTotalTrabajadores.getText();

        // E: Colegios - heurística
        return valorCamposColegios(fnLower);
    }

    private String valorCamposColegios(String fieldNameLower) {
        if (listaColegiosFields.isEmpty()) return null;
    ResourceBundle bundleCustom = ResourceBundle.getBundle("messages_es_custom");

        // Seleccionar colegio por nombre en el campo
        ColegioFields colegio = null;
        if (fieldNameLower.contains("especialist")) {
            colegio = listaColegiosFields.stream().filter(c -> c.titulo.equalsIgnoreCase(bundleCustom.getString("comite.colegio.especialistas"))).findFirst().orElse(null);
        } else if (fieldNameLower.contains("tecnic") || fieldNameLower.contains("administrativ")) {
            colegio = listaColegiosFields.stream().filter(c -> c.titulo.equalsIgnoreCase(bundleCustom.getString("comite.colegio.tecnicos"))).findFirst().orElse(null);
        } else if (fieldNameLower.contains("unico") || listaColegiosFields.size() == 1) {
            colegio = listaColegiosFields.get(0);
        }
        if (colegio == null) return null;

        if (fieldNameLower.contains("varon")) return colegio.varones.getText();
        if (fieldNameLower.contains("mujer")) return colegio.mujeres.getText();
        if (fieldNameLower.contains("total")) return colegio.totalElectores.getText();
        return null;
    }

    // Records simples para transportar mapeo
    private record FieldInfo(String name, String type) {}
    private record Mapping(String pdfName, List<FieldInfo> fields) {}
}
