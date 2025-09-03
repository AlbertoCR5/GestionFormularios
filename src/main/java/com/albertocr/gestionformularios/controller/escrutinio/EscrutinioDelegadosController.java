package com.albertocr.gestionformularios.controller.escrutinio;

import com.albertocr.gestionformularios.interfaz.escrutinio.VentanaGestionCandidatos;
import com.albertocr.gestionformularios.model.Candidato;
import com.albertocr.gestionformularios.service.EscrutinioService;
import com.albertocr.gestionformularios.service.dto.ActaDelegadosData;
import com.albertocr.gestionformularios.util.AlertManager;
import com.albertocr.gestionformularios.util.DirectorioManager;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Controlador para la ventana de escrutinio de delegados (escrutinio-delegados-view.fxml).
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 2.7
 */
public class EscrutinioDelegadosController {

    private static final Logger logger = LoggerFactory.getLogger(EscrutinioDelegadosController.class);
    private static final Pattern PREAVISO_PATTERN = Pattern.compile("^\\d{1,4}/\\d{4}$");



    @FXML private Label lblNombreEmpresa, lblNombreComercial, lblCif, lblNombreCentro, lblDireccionCentro, lblMunicipioCentro, lblProvincia, lblFechaConstitucion;
    @FXML private TextField tfPreaviso, tfActividadEconomica, tfNombreConvenio, tfNumeroConvenio, tfTrabajadoresFijos, tfTrabajadoresEventuales, tfTrabajadoresJornadas, tfTrabajadoresEventualesComputo, tfTotalTrabajadores, tfTotalElectores, tfElectoresVarones, tfElectorasMujeres, tfNumeroRepresentantes;
    @FXML private DatePicker dpFechaEscrutinio;
    @FXML private Button btnGestionarCandidatos, btnGuardarPDF;

    private final ActaDelegadosData initialActaData;
    private final EscrutinioService escrutinioService;
    private List<Candidato> candidatos;

    public EscrutinioDelegadosController(ActaDelegadosData actaData, EscrutinioService escrutinioService) {
        this.initialActaData = actaData;
        this.escrutinioService = escrutinioService;
        this.candidatos = new ArrayList<>();
    }

    @FXML
    public void initialize() {
        poblarDatosIniciales();
        configurarListeners();
        calcularCamposTrabajadores();
        calcularTotalElectores(); // Tarea 2
    }

    private void poblarDatosIniciales() {
        // Mostrar ejemplo formateado por defecto como promptText (123/AÑO_ACTUAL) si no viene en los datos iniciales
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
        tfTrabajadoresEventuales.setText(String.valueOf(initialActaData.trabajadoresEventuales()));
        tfTrabajadoresJornadas.setText(String.valueOf(initialActaData.trabajadoresJornadas()));
        // Se elimina la carga inicial de tfTotalElectores para que se calcule siempre
    }

    private void configurarListeners() {
        // Listener para campos que afectan al total de trabajadores y representantes
        ChangeListener<String> trabajadorListener = (obs, oldVal, newVal) -> {
            calcularCamposTrabajadores();
            calcularTotalElectores(); // Tarea 2: recalcular el total de electores
        };
        tfTrabajadoresFijos.textProperty().addListener(trabajadorListener);
        tfTrabajadoresEventuales.textProperty().addListener(trabajadorListener); // Tarea 2: añadir listener
        tfTrabajadoresJornadas.textProperty().addListener(trabajadorListener);


        // Listener para campos de electores (varones/mujeres)
        ChangeListener<String> electorListener = (obs, oldVal, newVal) -> calcularCamposElectorales();
        tfElectoresVarones.textProperty().addListener(electorListener);
        tfElectorasMujeres.textProperty().addListener(electorListener);
    }

    // Tarea 2: Nuevo método para calcular el total de electores
    private void calcularTotalElectores() {
        try {
            int fijos = parseInteger(tfTrabajadoresFijos.getText());
            int eventuales = parseInteger(tfTrabajadoresEventuales.getText());
            tfTotalElectores.setText(String.valueOf(fijos + eventuales));
            validarSumaElectores(); // Validar contra la suma de varones y mujeres
        } catch (NumberFormatException e) {
            tfTotalElectores.setText("0");
        }
    }


    private void calcularCamposTrabajadores() {
        try {
            int fijos = parseInteger(tfTrabajadoresFijos.getText());
            int jornadas = parseInteger(tfTrabajadoresJornadas.getText());
            double eventualesComputo = (jornadas > 0) ? (double) jornadas / 200.0 : 0.0;
            tfTrabajadoresEventualesComputo.setText(String.format("%.2f", eventualesComputo));
            int totalComputo = fijos + (int) Math.ceil(eventualesComputo);
            tfTotalTrabajadores.setText(String.valueOf(totalComputo));
            calcularRepresentantes();
        } catch (NumberFormatException e) { /* Ignorar */ }
    }

    private void calcularCamposElectorales() {
        // Ya no calcula el total, solo valida la suma de varones y mujeres
        validarSumaElectores();
    }

    private void calcularRepresentantes() {
        try {
            int totalTrabajadores = parseInteger(tfTotalTrabajadores.getText());
            tfNumeroRepresentantes.setText(String.valueOf((totalTrabajadores > 30) ? 3 : 1));
        } catch (NumberFormatException e) { /* Ignorar */ }
    }

    private boolean validarSumaElectores() {
        tfTotalElectores.setStyle("-fx-background-color: #e0e0e0;");
        tfElectoresVarones.setStyle("");
        tfElectorasMujeres.setStyle("");
        if (tfTotalElectores.getText().isBlank() || tfElectoresVarones.getText().isBlank() || tfElectorasMujeres.getText().isBlank()) return true;

        int total = parseInteger(tfTotalElectores.getText());
        int varones = parseInteger(tfElectoresVarones.getText());
        int mujeres = parseInteger(tfElectorasMujeres.getText());

        if (varones + mujeres != total) {
            String errorStyle = "-fx-border-color: red; -fx-border-width: 1px;";
            tfElectoresVarones.setStyle(errorStyle);
            tfElectorasMujeres.setStyle(errorStyle);
            return false;
        }
        return true;
    }

    // Tarea 1: Nuevo método para validar el formato del preaviso
    private boolean validarPreaviso() {
        String preaviso = tfPreaviso.getText();
        if (preaviso == null || preaviso.isBlank()) {
            AlertManager.mostrarAlertaError("Error de Validación", "El campo 'Número de Preaviso' no puede estar vacío.");
            return false;
        }

        if (!PREAVISO_PATTERN.matcher(preaviso).matches()) {
            AlertManager.mostrarAlertaError("Error de Validación", "El formato del 'Número de Preaviso' es incorrecto. Debe ser NÚMERO/AÑO (ej: 123/2024).");
            return false;
        }

        String[] partes = preaviso.split("/");
        int anio = Integer.parseInt(partes[1]);
        int anioActual = LocalDate.now().getYear();

        if (anio != anioActual && anio != anioActual - 1) {
            AlertManager.mostrarAlertaError("Error de Validación", "El año del preaviso debe ser el año actual (" + anioActual + ") o el anterior (" + (anioActual - 1) + ").");
            return false;
        }

        return true;
    }


    @FXML
    private void handleGuardarPDF() {
        // Tarea 1: Añadir validación de preaviso
        if (!validarPreaviso() || !validarSumaElectores()) {
            if (!validarSumaElectores()) {
                 AlertManager.mostrarAlertaError("Error de Validación", "La suma de varones y mujeres no coincide con el total de electores calculado.");
            }
            return;
        }

        File directorioDestino = DirectorioManager.obtenerDirectorioSeleccionado();
        if (directorioDestino == null) {
            logger.warn("El usuario canceló la selección del directorio.");
            return;
        }
        try {
            ActaDelegadosData datosActualizados = recopilarDatosDeLaVentana();
            escrutinioService.generarActaEscrutinioDelegados(datosActualizados, directorioDestino);
            AlertManager.mostrarAlertaInformacion("Éxito", "El acta de escrutinio para delegados se ha guardado correctamente.");
        } catch (Exception e) {
            logger.error("Error al guardar el PDF del acta de delegados", e);
            AlertManager.mostrarAlertaError("Error al Guardar", "No se pudo generar el archivo PDF: " + e.getMessage());
        }
    }

    private ActaDelegadosData recopilarDatosDeLaVentana() {
        return new ActaDelegadosData(
                tfPreaviso.getText(), lblNombreEmpresa.getText(), lblNombreComercial.getText(), lblCif.getText(),
                tfActividadEconomica.getText(), tfNombreConvenio.getText(), tfNumeroConvenio.getText(),
                lblNombreCentro.getText(), lblDireccionCentro.getText(), lblMunicipioCentro.getText(), lblProvincia.getText(),
                lblFechaConstitucion.getText(), parseInteger(tfTrabajadoresFijos.getText()),
                parseInteger(tfTrabajadoresEventuales.getText()), parseInteger(tfTrabajadoresJornadas.getText()),
                parseInteger(tfTotalElectores.getText()), this.candidatos
        );
    }

    @FXML
    private void handleGestionarCandidatos() {
        logger.info("Abriendo la ventana de gestión de candidatos.");
        try {
            VentanaGestionCandidatos ventanaCandidatos = new VentanaGestionCandidatos(this.candidatos);
            ventanaCandidatos.showAndWait();
            this.candidatos = ventanaCandidatos.getCandidatosActualizados();
            logger.info("Ventana de gestión de candidatos cerrada. Se tienen {} candidatos.", this.candidatos.size());
        } catch (Exception e) {
            logger.error("Error al abrir la ventana de gestión de candidatos", e);
            AlertManager.mostrarAlertaError("Error", "No se pudo abrir la ventana de gestión de candidatos.");
        }
    }

    private int parseInteger(String text) {
        return text == null || text.isBlank() ? 0 : Integer.parseInt(text);
    }

    private String valorOPlaceholder(String valor, String placeholder) {
        return (valor == null || valor.isBlank()) ? placeholder : valor;
    }
}
