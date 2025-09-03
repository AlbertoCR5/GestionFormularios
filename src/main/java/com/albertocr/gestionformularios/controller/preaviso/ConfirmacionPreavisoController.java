package com.albertocr.gestionformularios.controller.preaviso;

import com.albertocr.gestionformularios.model.Eleccion;
import com.albertocr.gestionformularios.model.Empresa;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

/**
 * Controlador para la ventana de confirmación de datos del preaviso.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.1
 */
public class ConfirmacionPreavisoController {

    // Asumimos que estos Labels están definidos con fx:id en el FXML
    @FXML private Label labelNombreEmpresa, labelCif, labelNombreComercial, labelNombreCentro, labelDireccion, labelMunicipio, labelCodigoPostal, labelProvincia, labelNumeroTrabajadores, labelTipoEleccion, labelFechaConstitucion, labelFechaPreaviso, labelPromotores;

    private final Empresa empresa;
    private final Eleccion eleccion;
    private boolean confirmado = false;

    public ConfirmacionPreavisoController(Empresa empresa, Eleccion eleccion) {
        this.empresa = empresa;
        this.eleccion = eleccion;
    }

    @FXML
    public void initialize() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        labelNombreEmpresa.setText(empresa.getNombre());
        labelCif.setText(empresa.getCif());
        labelNombreComercial.setText(empresa.getNombreComercial());
        labelNombreCentro.setText(empresa.getNombreCentro());
        labelDireccion.setText(empresa.getDireccion());
        labelMunicipio.setText(empresa.getMunicipio());
        labelCodigoPostal.setText(empresa.getCodigoPostal());
        labelProvincia.setText(empresa.getProvincia());
        labelNumeroTrabajadores.setText(String.valueOf(eleccion.getNumeroTrabajadores()));
        labelTipoEleccion.setText(eleccion.getTipoEleccion());
        labelFechaConstitucion.setText(eleccion.getFechaConstitucion().format(formatter));
        labelFechaPreaviso.setText(eleccion.getFecha().format(formatter));
        labelPromotores.setText(eleccion.getPromotores());
    }

    @FXML
    private void handleConfirmar() {
        this.confirmado = true;
        cerrarVentana();
    }

    @FXML
    private void handleCancelar() {
        this.confirmado = false;
        cerrarVentana();
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    private void cerrarVentana() {
        // Obtener la referencia al Stage desde cualquier nodo del FXML
        Stage stage = (Stage) labelNombreEmpresa.getScene().getWindow();
        stage.close();
    }
}
