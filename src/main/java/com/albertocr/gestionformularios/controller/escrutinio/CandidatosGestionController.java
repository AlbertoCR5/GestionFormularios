package com.albertocr.gestionformularios.controller.escrutinio;

import com.albertocr.gestionformularios.model.Candidato;
import com.albertocr.gestionformularios.util.AlertManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

/**
 * Controlador para la ventana de gestión de candidatos (candidatos-gestion-view.fxml).
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.3
 */
public class CandidatosGestionController {

    // --- FXML Fields ---
    @FXML private TableView<Candidato> tablaCandidatos;
    @FXML private TableColumn<Candidato, String> colNombre;
    @FXML private TableColumn<Candidato, String> colDni;
    @FXML private TableColumn<Candidato, Integer> colAntiguedad;
    @FXML private TableColumn<Candidato, LocalDate> colFechaNacimiento;
    @FXML private TableColumn<Candidato, String> colSindicato;
    @FXML private TextField tfNombre;
    @FXML private TextField tfDni;
    @FXML private TextField tfAntiguedad;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private DatePicker dpFechaAntiguedad;
    @FXML private DatePicker dpFechaConstitucionMesa;
    @FXML private TextField tfSindicato;
    @FXML private Button btnAnadir, btnEliminar, btnCerrar;

    private final ObservableList<Candidato> candidatos;
    private boolean isUpdating = false;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public CandidatosGestionController(List<Candidato> candidatosActuales) {
        this.candidatos = FXCollections.observableArrayList(candidatosActuales);
        ordenarCandidatos();
    }

    @FXML
    public void initialize() {
        configurarAlineacionColumnas();
        configurarBindingsColumnas();
        configurarFormatoFechas();
        configurarListenersAntiguedad();

        tablaCandidatos.setItems(candidatos);
        dpFechaConstitucionMesa.setValue(LocalDate.now()); // Valor por defecto
    }

    private void configurarBindingsColumnas() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colSindicato.setCellValueFactory(new PropertyValueFactory<>("sindicato"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));

        // Tarea 4: Formato personalizado para la columna de antigüedad
        colAntiguedad.setCellValueFactory(new PropertyValueFactory<>("antiguedadMeses"));
        colAntiguedad.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer meses, boolean empty) {
                super.updateItem(meses, empty);
                if (empty || meses == null) {
                    setText(null);
                } else {
                    Candidato candidato = getTableView().getItems().get(getIndex());
                    String texto = String.valueOf(meses);
                    if (candidato.getFechaAntiguedad() != null) {
                        texto += " (" + candidato.getFechaAntiguedad().format(DATE_FORMATTER) + ")";
                    }
                    setText(texto);
                }
            }
        });
    }

    private void configurarListenersAntiguedad() {
        tfAntiguedad.textProperty().addListener((obs, oldVal, newVal) -> {
            if (isUpdating) return;
            isUpdating = true;
            try {
                if (newVal != null && !newVal.isBlank()) {
                    long meses = Long.parseLong(newVal);
                    LocalDate fechaConstitucion = dpFechaConstitucionMesa.getValue();
                    if (fechaConstitucion != null) {
                        dpFechaAntiguedad.setValue(fechaConstitucion.minusMonths(meses));
                    }
                } else {
                    dpFechaAntiguedad.setValue(null);
                }
            } catch (NumberFormatException e) {
                // Ignorar si el texto no es un número válido
            } finally {
                isUpdating = false;
            }
        });

        dpFechaAntiguedad.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (isUpdating) return;
            isUpdating = true;
            try {
                LocalDate fechaConstitucion = dpFechaConstitucionMesa.getValue();
                if (newVal != null && fechaConstitucion != null) {
                    long meses = ChronoUnit.MONTHS.between(newVal, fechaConstitucion);
                    tfAntiguedad.setText(String.valueOf(meses));
                } else {
                    tfAntiguedad.clear();
                }
            } finally {
                isUpdating = false;
            }
        });
    }

    @FXML
    private void handleAnadirCandidato() {
        if (tfNombre.getText().isBlank() || tfDni.getText().isBlank()) {
            AlertManager.mostrarAlertaAdvertencia("Campos Obligatorios", "El nombre y el DNI son obligatorios.");
            return;
        }

        try {
            int antiguedad = tfAntiguedad.getText().isBlank() ? 0 : Integer.parseInt(tfAntiguedad.getText());

            candidatos.add(new Candidato(
                    tfNombre.getText().toUpperCase(),
                    "",
                    tfDni.getText().toUpperCase(),
                    tfSindicato.getText().toUpperCase(),
                    antiguedad,
                    dpFechaNacimiento.getValue(),
                    dpFechaAntiguedad.getValue()
            ));

            ordenarCandidatos();
            limpiarCampos();
        } catch (NumberFormatException e) {
            AlertManager.mostrarAlertaError("Error de Formato", "La antigüedad debe ser un número válido.");
        }
    }

    @FXML
    private void handleEliminarCandidato() {
        Candidato seleccionado = tablaCandidatos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            candidatos.remove(seleccionado);
        } else {
            AlertManager.mostrarAlertaAdvertencia("Sin Selección", "Por favor, seleccione un candidato para eliminar.");
        }
    }

    @FXML
    private void handleCerrar() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    public List<Candidato> getCandidatosActualizados() {
        return List.copyOf(candidatos);
    }

    private void limpiarCampos() {
        tfNombre.clear();
        tfDni.clear();
        tfAntiguedad.clear();
        dpFechaNacimiento.setValue(null);
        dpFechaAntiguedad.setValue(null);
        tfSindicato.clear();
    }

    private void configurarAlineacionColumnas() {
        centrarColumna(colNombre);
        centrarColumna(colDni);
        centrarColumna(colAntiguedad);
        centrarColumna(colFechaNacimiento);
        centrarColumna(colSindicato);
    }

    private <T> void centrarColumna(TableColumn<Candidato, T> columna) {
        columna.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
                setAlignment(Pos.CENTER);
            }
        });
    }

    private void ordenarCandidatos() {
        candidatos.sort(Comparator.comparing(c -> getApellidos(c.getNombre())));
    }

    private String getApellidos(String nombreCompleto) {
        if (nombreCompleto == null || nombreCompleto.isBlank()) return "";
        String[] partes = nombreCompleto.trim().split("\\s+");
        return partes.length <= 2 ? partes[partes.length - 1] : partes[partes.length - 2] + " " + partes[partes.length - 1];
    }

    private void configurarFormatoFechas() {
        StringConverter<LocalDate> converter = new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? DATE_FORMATTER.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return string != null && !string.isEmpty() ? LocalDate.parse(string, DATE_FORMATTER) : null;
            }
        };
        dpFechaNacimiento.setConverter(converter);
        dpFechaAntiguedad.setConverter(converter);
        dpFechaConstitucionMesa.setConverter(converter);
    }
}
