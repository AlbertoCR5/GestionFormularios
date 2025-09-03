package com.albertocr.gestionformularios.controller.candidato;

import com.albertocr.gestionformularios.model.Candidato;
import com.albertocr.gestionformularios.service.CandidatoService;
import com.albertocr.gestionformularios.util.AlertManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Controlador para la ventana de gestión de candidatos (candidato-view.fxml).
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.3
 */
public class CandidatoController {

    private static final Logger logger = LoggerFactory.getLogger(CandidatoController.class);

    @FXML private TableView<Candidato> tablaCandidatos;
    @FXML private TableColumn<Candidato, Integer> columnaId;
    @FXML private TableColumn<Candidato, String> columnaNombre;
    @FXML private TableColumn<Candidato, String> columnaApellidos;
    @FXML private TableColumn<Candidato, String> columnaDni;
    @FXML private TextField tfNombre;
    @FXML private TextField tfApellidos;
    @FXML private TextField tfDni;

    private final CandidatoService candidatoService;
    private final ObservableList<Candidato> listaCandidatosObservable;

    public CandidatoController(CandidatoService candidatoService) {
        this.candidatoService = candidatoService;
        this.listaCandidatosObservable = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        configurarTabla();
        cargarCandidatos();
    }

    private void configurarTabla() {
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        columnaDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        tablaCandidatos.setItems(listaCandidatosObservable);
        tablaCandidatos.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        rellenarCamposConSeleccion(newSelection);
                    }
                });
    }

    private void cargarCandidatos() {
        listaCandidatosObservable.setAll(candidatoService.obtenerTodosLosCandidatos());
        logger.info("Candidatos cargados y tabla actualizada.");
    }

    @FXML
    private void handleAnadir() {
        if (!validarCampos()) return;

        // CORRECCIÓN: Llamar al nuevo constructor de 6 argumentos
        Candidato nuevoCandidato = new Candidato(tfNombre.getText(), tfApellidos.getText(), tfDni.getText(), null, 0, null);
        if (candidatoService.guardarOActualizarCandidato(nuevoCandidato)) {
            AlertManager.mostrarAlertaInformacion("Éxito", "Candidato añadido correctamente.");
            cargarCandidatos();
            limpiarCampos();
        } else {
            AlertManager.mostrarAlertaError("Error", "No se pudo añadir el candidato. Verifique que el DNI no esté duplicado.");
        }
    }

    @FXML
    private void handleModificar() {
        Candidato seleccionado = tablaCandidatos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            AlertManager.mostrarAlertaAdvertencia("Sin Selección", "Por favor, seleccione un candidato de la tabla para modificar.");
            return;
        }
        if (!validarCampos()) return;

        seleccionado.setNombre(tfNombre.getText());
        seleccionado.setApellidos(tfApellidos.getText());
        seleccionado.setDni(tfDni.getText());

        if (candidatoService.guardarOActualizarCandidato(seleccionado)) {
            AlertManager.mostrarAlertaInformacion("Éxito", "Candidato modificado correctamente.");
            cargarCandidatos();
            limpiarCampos();
        } else {
            AlertManager.mostrarAlertaError("Error", "No se pudo modificar el candidato.");
        }
    }

    @FXML
    private void handleEliminar() {
        Candidato seleccionado = tablaCandidatos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            AlertManager.mostrarAlertaAdvertencia("Sin Selección", "Por favor, seleccione un candidato de la tabla para eliminar.");
            return;
        }

        Optional<ButtonType> resultado = AlertManager.mostrarAlertaConfirmacion("Confirmar Eliminación",
                "¿Está seguro de que desea eliminar al candidato '" + seleccionado.getNombre() + " " + seleccionado.getApellidos() + "'?",
                "Esta acción no se puede deshacer.");

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            if (candidatoService.eliminarCandidatoPorDni(seleccionado.getDni())) {
                AlertManager.mostrarAlertaInformacion("Éxito", "Candidato eliminado correctamente.");
                cargarCandidatos();
                limpiarCampos();
            } else {
                AlertManager.mostrarAlertaError("Error", "No se pudo eliminar el candidato.");
            }
        }
    }

    private void rellenarCamposConSeleccion(Candidato candidato) {
        tfNombre.setText(candidato.getNombre());
        tfApellidos.setText(candidato.getApellidos());
        tfDni.setText(candidato.getDni());
    }

    private void limpiarCampos() {
        tfNombre.clear();
        tfApellidos.clear();
        tfDni.clear();
        tablaCandidatos.getSelectionModel().clearSelection();
    }

    private boolean validarCampos() {
        if (tfNombre.getText().isBlank() || tfApellidos.getText().isBlank() || tfDni.getText().isBlank()) {
            AlertManager.mostrarAlertaAdvertencia("Campos Vacíos", "Todos los campos (Nombre, Apellidos, DNI) son obligatorios.");
            return false;
        }
        return true;
    }
}
