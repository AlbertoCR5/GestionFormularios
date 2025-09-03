package com.albertocr.gestionformularios.interfaz.escrutinio;

import com.albertocr.gestionformularios.model.Candidato;
import com.albertocr.gestionformularios.util.AlertManager;
import com.albertocr.gestionformularios.view.CandidatoCellFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

public class VentanaGestionCandidatos extends Stage {

    private static final Logger logger = LoggerFactory.getLogger(VentanaGestionCandidatos.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    private TableView<Candidato> tablaCandidatos;
    @FXML
    private Label lblTitulo;
    @FXML
    private TableColumn<Candidato, String> colNombre, colDni, colSindicato;
    @FXML
    private TableColumn<Candidato, LocalDate> colFechaNacimiento, colAntiguedad;
    @FXML
    private TextField tfNombre, tfDni, tfAntiguedad, tfSindicato;
    @FXML
    private DatePicker dpFechaNacimiento;
    @FXML
    private Button btnAnadir, btnEliminar, btnGuardar;

    private final ObservableList<Candidato> candidatos;


    /**
     * Constructor clásico. Mantener por compatibilidad con código existente.
     * Prefiera el constructor con título dinámico para nuevas llamadas.
     * @param candidatosIniciales Lista inicial de candidatos
     */
    public VentanaGestionCandidatos(List<Candidato> candidatosIniciales) {
        this(candidatosIniciales, "Gestión de Candidatos");
    }

    /**
     * Constructor recomendado. Permite establecer el título de la ventana dinámicamente (i18n).
     * @param candidatosIniciales Lista inicial de candidatos
     * @param tituloVentana Título a mostrar en la ventana
     */
    public VentanaGestionCandidatos(List<Candidato> candidatosIniciales, String tituloVentana) {
        this.candidatos = FXCollections.observableArrayList(candidatosIniciales);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/albertocr/gestionformularios/interfaz/escrutinio/candidatos-gestion-view.fxml"));
            loader.setController(this);
            Scene scene = new Scene(loader.load());
            setScene(scene);
            setTitle(tituloVentana);
            // Si la etiqueta existe en la vista, actualizarla para reflejar el título completo
            if (lblTitulo != null) {
                lblTitulo.setText(tituloVentana);
            }
            initModality(Modality.APPLICATION_MODAL);
        } catch (IOException e) {
            logger.error("Error al cargar la vista de gestión de candidatos", e);
            AlertManager.mostrarAlertaError("Error de Carga", "No se pudo cargar la interfaz de gestión de candidatos.");
        }
    }

    @FXML
    private void initialize() {
        configurarTabla();
        configurarBindings();
        configurarListeners();
        tablaCandidatos.setItems(candidatos);
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        colAntiguedad.setCellValueFactory(new PropertyValueFactory<>("fechaAntiguedad"));
        colSindicato.setCellValueFactory(new PropertyValueFactory<>("sindicato"));

        // Aplicar CellFactory para mayúsculas y alineación
        colNombre.setCellFactory(createCellFactory(Pos.CENTER_LEFT));
        colDni.setCellFactory(createCellFactory(Pos.CENTER));
        colSindicato.setCellFactory(createCellFactory(Pos.CENTER));

        // CellFactory específica para la fecha de nacimiento
        colFechaNacimiento.setCellFactory(column -> {
            TableCell<Candidato, LocalDate> cell = new TableCell<>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(DATE_FORMATTER.format(item).toUpperCase());
                    }
                }
            };
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        // La CellFactory de Antigüedad ya centra el contenido, solo falta ponerlo en mayúsculas
        colAntiguedad.setCellFactory(new CandidatoCellFactory());
    }

    private <T> Callback<TableColumn<Candidato, T>, TableCell<Candidato, T>> createCellFactory(Pos alignment) {
        return column -> {
            TableCell<Candidato, T> cell = new TableCell<>() {
                @Override
                protected void updateItem(T item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.toString().toUpperCase());
                    }
                }
            };
            cell.setAlignment(alignment);
            return cell;
        };
    }

    private void configurarBindings() {
        btnEliminar.disableProperty().bind(tablaCandidatos.getSelectionModel().selectedItemProperty().isNull());
    }

    private void configurarListeners() {
        btnAnadir.setOnAction(event -> anadirCandidato());
        btnEliminar.setOnAction(event -> eliminarCandidato());
        btnGuardar.setOnAction(event -> guardarYcerrar());
    }

    private void anadirCandidato() {
        String nombre = tfNombre.getText();
        String dni = tfDni.getText();
        LocalDate fechaNacimiento = dpFechaNacimiento.getValue();
        String antiguedadInput = tfAntiguedad.getText();
        String sindicato = tfSindicato.getText(); // Campo opcional

        if (nombre.isBlank() || dni.isBlank() || fechaNacimiento == null || antiguedadInput.isBlank()) {
            AlertManager.mostrarAlertaError("Campos incompletos", "Por favor, rellene todos los campos para añadir un candidato.");
            return;
        }

        LocalDate fechaAntiguedad = parseAntiguedad(antiguedadInput);
        if (fechaAntiguedad == null) {
            AlertManager.mostrarAlertaError("Formato incorrecto", "El formato de la antigüedad no es válido. Use 'dd/mm/yyyy' o un número de meses.");
            return;
        }

        Candidato nuevoCandidato = new Candidato(nombre, dni, fechaNacimiento, fechaAntiguedad, sindicato);
        candidatos.add(nuevoCandidato);
        ordenarCandidatosPorApellidos();
        limpiarCampos();
    }

    /**
     * Ordena la lista de candidatos alfabéticamente por los apellidos (últimas dos palabras del nombre).
     */
    private void ordenarCandidatosPorApellidos() {
        FXCollections.sort(candidatos, (c1, c2) -> {
            String apellidos1 = extraerApellidos(c1.getNombre());
            String apellidos2 = extraerApellidos(c2.getNombre());
            return apellidos1.compareToIgnoreCase(apellidos2);
        });
    }

    /**
     * Extrae los apellidos de un nombre completo (últimas dos palabras).
     */
    private String extraerApellidos(String nombreCompleto) {
        if (nombreCompleto == null || nombreCompleto.isBlank()) return "";
        String[] partes = nombreCompleto.trim().split("\\s+");
        if (partes.length >= 2) {
            return partes[partes.length - 2] + " " + partes[partes.length - 1];
        } else {
            return nombreCompleto.trim();
        }
    }

    private LocalDate parseAntiguedad(String input) {
        try {
            // Intentar parsear como meses
            int meses = Integer.parseInt(input);
            return LocalDate.now().minusMonths(meses);
        } catch (NumberFormatException e) {
            // Si falla, intentar parsear como fecha
            try {
                return LocalDate.parse(input, DATE_FORMATTER);
            } catch (DateTimeParseException ex) {
                return null; // Formato inválido
            }
        }
    }

    private void eliminarCandidato() {
        Candidato seleccionado = tablaCandidatos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Está seguro de que desea eliminar al candidato?");
            confirmacion.setContentText(seleccionado.getNombre());

            Optional<ButtonType> resultado = confirmacion.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                candidatos.remove(seleccionado);
            }
        }
    }

    private void guardarYcerrar() {
        close();
    }

    private void limpiarCampos() {
        tfNombre.clear();
        tfDni.clear();
        dpFechaNacimiento.setValue(null);
        tfAntiguedad.clear();
        tfSindicato.clear();
    }

    public List<Candidato> getCandidatosActualizados() {
        return List.copyOf(candidatos);
    }
}
