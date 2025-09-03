package com.albertocr.gestionformularios.controller.escrutinio;

import com.albertocr.gestionformularios.interfaz.escrutinio.VentanaGestionCandidatos;
import com.albertocr.gestionformularios.model.CandidatosDAO;
import com.albertocr.gestionformularios.model.Candidato;
import com.albertocr.gestionformularios.service.EscrutinioService;
import com.albertocr.gestionformularios.service.dto.ActaComiteData;
import com.albertocr.gestionformularios.util.AlertManager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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
        if (!validarFormulario()) {
            return;
        }
        // Validación adicional: Sección D vs Sección E
        if (!validarSeccionesDvsE()) {
            // Cancelar guardado para evitar documentos inconsistentes
            return;
        }

        ActaComiteData datosActualizados = recopilarDatosDeLaVentana();
        logger.info("Datos del formulario validados y recopilados: {}", datosActualizados);

        // Aquí iría la llamada al servicio para generar el PDF
        // escrutinioService.generarActaEscrutinioComite(datosActualizados, directorioDestino);

        AlertManager.mostrarAlertaInformacion("Funcionalidad no Implementada",
                "La generación de PDF para actas de comité aún no está disponible.\n\nLos datos han sido validados correctamente.");
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
}
