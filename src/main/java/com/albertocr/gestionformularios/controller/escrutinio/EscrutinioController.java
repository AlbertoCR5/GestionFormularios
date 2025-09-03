package com.albertocr.gestionformularios.controller.escrutinio;

import com.albertocr.gestionformularios.interfaz.escrutinio.VentanaEscrutinioComite;
import com.albertocr.gestionformularios.interfaz.escrutinio.VentanaEscrutinioDelegados;
import com.albertocr.gestionformularios.model.EleccionParaEscrutinio;
import com.albertocr.gestionformularios.model.TipoEleccion;
import com.albertocr.gestionformularios.service.DatosEleccionAnterior;
import com.albertocr.gestionformularios.service.EscrutinioService;
import com.albertocr.gestionformularios.service.dto.ActaComiteData;
import com.albertocr.gestionformularios.service.dto.ActaDelegadosData;
import com.albertocr.gestionformularios.util.AlertManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para la ventana de escrutinio (escrutinio-view.fxml).
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 2.6
 */
public class EscrutinioController {

    private static final Logger logger = LoggerFactory.getLogger(EscrutinioController.class);

    private static final ButtonType BOTON_SI = new ButtonType("Si", ButtonBar.ButtonData.YES);
    private static final ButtonType BOTON_NO = new ButtonType("No", ButtonBar.ButtonData.NO);
    private static final ButtonType BOTON_CANCELAR = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

    @FXML private ComboBox<EleccionParaEscrutinio> eleccionComboBox;
    @FXML private GridPane formGridPane;

    private final EscrutinioService escrutinioService;

    public EscrutinioController(EscrutinioService escrutinioService) {
        this.escrutinioService = escrutinioService;
    }

    @FXML
    public void initialize() {
        eleccionComboBox.setItems(FXCollections.observableArrayList(escrutinioService.obtenerEleccionesParaEscrutinio()));
        formGridPane.setVisible(false);
        formGridPane.setManaged(false);
    }

    @FXML
    private void handleEleccionSeleccionada() {
        EleccionParaEscrutinio eleccionSeleccionada = eleccionComboBox.getSelectionModel().getSelectedItem();
        if (eleccionSeleccionada != null) {
            iniciarProcesoEscrutinio(eleccionSeleccionada);
        }
    }

    private void iniciarProcesoEscrutinio(EleccionParaEscrutinio eleccionActual) {
        DatosEleccionAnterior datosAnteriores = escrutinioService.obtenerDatosEleccionAnterior(eleccionActual.empresa());

        if (datosAnteriores == null || datosAnteriores.getTipoEleccion() == null) {
            AlertManager.mostrarAlertaInformacion("Información", "No se encontraron datos de una elección anterior. Por favor, proceda manualmente.");
            return;
        }

        if (datosAnteriores.getTipoEleccion() == TipoEleccion.DELEGADOS) {
            Optional<ButtonType> respuesta = AlertManager.mostrarAlertaConfirmacionPersonalizada("Verificación de Censo",
                    "¿El número de trabajadores de " + eleccionActual.empresa().getNombre() + " sigue siendo inferior a 50?",
                    "Seleccione \"Si\" para confirmar, o \"No\" si ha aumentado.", BOTON_SI, BOTON_NO, BOTON_CANCELAR);

            respuesta.ifPresent(buttonType -> {
                if (buttonType == BOTON_SI) abrirVentanaActaDelegados(eleccionActual);
                else if (buttonType == BOTON_NO) preguntarPorColegios(eleccionActual, datosAnteriores);
            });
        } else { // TipoEleccion.COMITE
            Optional<ButtonType> respuesta = AlertManager.mostrarAlertaConfirmacionPersonalizada("Verificación de Censo",
                    "¿El número de trabajadores de " + eleccionActual.empresa().getNombre() + " sigue siendo igual o superior a 50?",
                    "Seleccione \"Si\" para confirmar, o \"No\" si ha disminuido.", BOTON_SI, BOTON_NO, BOTON_CANCELAR);

            respuesta.ifPresent(buttonType -> {
                if (buttonType == BOTON_SI) preguntarPorColegios(eleccionActual, datosAnteriores);
                else if (buttonType == BOTON_NO) abrirVentanaActaDelegados(eleccionActual);
            });
        }
    }

    private void preguntarPorColegios(EleccionParaEscrutinio eleccionActual, DatosEleccionAnterior datosAnteriores) {
        List<String> opciones = List.of("Colegio Único", "Especialistas y Técnicos/Administrativos");
        ChoiceDialog<String> dialog = new ChoiceDialog<>(datosAnteriores.getOpcionColegioContraria(), opciones);
        dialog.setTitle("Configuración de Colegios");
        dialog.setHeaderText("¿Cómo se configura el censo para esta elección?");
        dialog.setContentText("Por favor, selecciona una opción:");

        dialog.showAndWait().ifPresent(seleccion -> abrirVentanaActaComite(eleccionActual, seleccion));
    }

    private void abrirVentanaActaDelegados(EleccionParaEscrutinio eleccion) {
        logger.info("Iniciando la preparación de datos para el acta de delegados.");
        try {
            ActaDelegadosData actaData = escrutinioService.prepararDatosActaDelegados(eleccion);
            new VentanaEscrutinioDelegados(actaData, escrutinioService).show();
        } catch (Exception e) {
            logger.error("No se pudo abrir la ventana de escrutinio de delegados.", e);
            AlertManager.mostrarAlertaError("Error de Apertura", "No se pudo preparar o abrir la ventana: " + e.getMessage());
        }
    }

    private void abrirVentanaActaComite(EleccionParaEscrutinio eleccion, String tipoColegio) {
        logger.info("Iniciando la preparación de datos para el acta de comité.");
        try {
            ActaComiteData actaData = escrutinioService.prepararDatosActaComite(eleccion, tipoColegio);
            new VentanaEscrutinioComite(actaData, escrutinioService).show();
        } catch (Exception e) {
            logger.error("No se pudo abrir la ventana de escrutinio de comité.", e);
            AlertManager.mostrarAlertaError("Error de Apertura", "No se pudo preparar o abrir la ventana: " + e.getMessage());
        }
    }
}
