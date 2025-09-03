package com.albertocr.gestionformularios.controller.calendario;

import com.albertocr.gestionformularios.model.CalendarioComite;
import com.albertocr.gestionformularios.model.EleccionParaCalendario;
import com.albertocr.gestionformularios.service.CalendarioService;
import com.albertocr.gestionformularios.util.AlertManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Controlador para la ventana de Calendario de Comité (calendario-comite-view.fxml).
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.2
 */
public class CalendarioComiteController {

    private static final Logger logger = LoggerFactory.getLogger(CalendarioComiteController.class);

    @FXML private ComboBox<EleccionParaCalendario> eleccionComboBox;
    @FXML private GridPane formGridPane;
    @FXML private TextField tfNombreEmpresa, tfCif, tfNumeroTrabajadores, tfHorarioVotacion, tfLugarVotacion, tfLocalidadFirma, tfNombrePresidente, tfNombreVocal, tfNombreSecretario;
    @FXML private DatePicker dpFechaConstitucion, dpFechaInicio, dpFechaTope, dpFechaPreaviso, dpFechaElecciones, dpFechaFirma;
    @FXML private Button btnGenerar, btnLimpiar;

    private final CalendarioService calendarioService;
    private EleccionParaCalendario eleccionSeleccionada;

    public CalendarioComiteController(CalendarioService calendarioService) {
        this.calendarioService = calendarioService;
    }

    @FXML
    public void initialize() {
        eleccionComboBox.setItems(FXCollections.observableArrayList(calendarioService.obtenerEleccionesParaCalendario()));
        formGridPane.setDisable(true);
    }

    @FXML
    private void handleEleccionSeleccionada() {
        eleccionSeleccionada = eleccionComboBox.getSelectionModel().getSelectedItem();
        if (eleccionSeleccionada != null) {
            cargarDatosFormulario();
            formGridPane.setDisable(false);
        } else {
            formGridPane.setDisable(true);
        }
    }

    private void cargarDatosFormulario() {
        tfNombreEmpresa.setText(eleccionSeleccionada.nombreEmpresa());
        tfCif.setText(eleccionSeleccionada.cifEmpresa());
        tfNumeroTrabajadores.setText(String.valueOf(eleccionSeleccionada.numeroTrabajadores()));

        LocalDate fechaConst = eleccionSeleccionada.fechaConstitucion();
        dpFechaConstitucion.setValue(fechaConst);
        dpFechaInicio.setValue(fechaConst.plusDays(1));
        dpFechaTope.setValue(fechaConst.plusDays(8));
        dpFechaPreaviso.setValue(fechaConst.plusDays(9));
        dpFechaElecciones.setValue(fechaConst.plusDays(30));
        dpFechaFirma.setValue(LocalDate.now());
        tfLocalidadFirma.setText(eleccionSeleccionada.localidadEmpresa());
    }

    @FXML
    private void handleGenerarPDF() {
        if (!validarFormulario()) {
            return;
        }
        // La lógica de creación del objeto CalendarioComite y la generación del PDF
        // se delegaría al CalendarioService.
        logger.info("Generando PDF para el calendario del comité de la empresa: {}", eleccionSeleccionada.nombreEmpresa());
        AlertManager.mostrarAlertaInformacion("Función no implementada", "La generación del PDF del calendario aún no está implementada en el servicio.");
        // calendarioService.generarPdfCalendario(crearCalendarioDesdeFormulario());
    }

    @FXML
    private void handleLimpiar() {
        if (eleccionSeleccionada == null) return;
        tfHorarioVotacion.clear();
        tfLugarVotacion.clear();
        tfLocalidadFirma.setText(eleccionSeleccionada.localidadEmpresa());
        tfNombrePresidente.clear();
        tfNombreVocal.clear();
        tfNombreSecretario.clear();
        dpFechaFirma.setValue(LocalDate.now());
        logger.info("Campos editables del formulario limpiados.");
    }

    private boolean validarFormulario() {
        if (tfHorarioVotacion.getText().isBlank() || tfLugarVotacion.getText().isBlank() ||
                tfLocalidadFirma.getText().isBlank() || tfNombrePresidente.getText().isBlank() ||
                tfNombreVocal.getText().isBlank() || tfNombreSecretario.getText().isBlank() ||
                dpFechaElecciones.getValue() == null) {
            AlertManager.mostrarAlertaAdvertencia("Campos Incompletos", "Por favor, rellene todos los campos editables.");
            return false;
        }
        return true;
    }
}
