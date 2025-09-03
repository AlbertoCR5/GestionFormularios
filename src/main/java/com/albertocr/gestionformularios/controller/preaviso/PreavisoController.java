package com.albertocr.gestionformularios.controller.preaviso;

import com.albertocr.gestionformularios.interfaz.preaviso.VentanaConfirmacionPreaviso;
import com.albertocr.gestionformularios.model.Eleccion;
import com.albertocr.gestionformularios.model.Empresa;
import com.albertocr.gestionformularios.model.TipoColegioElectoral;
import com.albertocr.gestionformularios.service.PreavisoService;
import com.albertocr.gestionformularios.util.AlertManager;
import com.albertocr.gestionformularios.util.Constantes;
import com.albertocr.gestionformularios.util.ConversorFechaToLetras;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Optional;

/**
 * Controlador para la ventana de preaviso (preaviso-view.fxml).
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.16
 */
public class PreavisoController {

    private static final Logger logger = LoggerFactory.getLogger(PreavisoController.class);

    @FXML private TextField tfNombreEmpresa, tfCif, tfNombreComercial, tfNombreCentro, tfDireccion, tfMunicipio, tfComarca, tfCodigoPostal, tfNumeroTrabajadores, tfAnioElecciones, tfPromotores, tfNumeroISS;
    @FXML private ComboBox<String> cbProvincia, cbMesElecciones;
    @FXML private RadioButton rbTotal, rbParcial;
    @FXML private DatePicker dpFechaConstitucion, dpFechaPreaviso;

    private final PreavisoService preavisoService;

    public PreavisoController(PreavisoService preavisoService) {
        this.preavisoService = preavisoService;
    }

    @FXML
    public void initialize() {
        configurarValoresPorDefecto();
        configurarListeners();
    }

    private void configurarValoresPorDefecto() {
        ObservableList<String> provincias = FXCollections.observableArrayList("Almería", "Cádiz", "Córdoba", "Granada", "Huelva", "Jaén", "Málaga", "Sevilla");
        cbProvincia.setItems(provincias);
        cbProvincia.setValue("Sevilla");

        ObservableList<String> meses = FXCollections.observableArrayList("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");
        cbMesElecciones.setItems(meses);

        LocalDate fechaSiguiente = LocalDate.now().plusMonths(1);
        String mesSiguiente = fechaSiguiente.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-ES"));
        cbMesElecciones.setValue(mesSiguiente.substring(0, 1).toUpperCase() + mesSiguiente.substring(1));

        tfAnioElecciones.setText(String.valueOf(LocalDate.now().getYear()));
        rbTotal.setSelected(true);
        dpFechaConstitucion.setValue(LocalDate.now().plusDays(31));
        dpFechaPreaviso.setValue(LocalDate.now());
        tfPromotores.setText(Constantes.PROMOTORES);
    }

    private void configurarListeners() {
        ToggleGroup tipoEleccionGroup = new ToggleGroup();
        rbTotal.setToggleGroup(tipoEleccionGroup);
        rbParcial.setToggleGroup(tipoEleccionGroup);
    }

    @FXML
    public void handleGuardar() {
        if (!validarFormulario()) {
            return;
        }

        Empresa empresa = crearEmpresaDesdeFormulario();
        Eleccion eleccion = crearEleccionDesdeFormulario(empresa);

        VentanaConfirmacionPreaviso confirmacion = new VentanaConfirmacionPreaviso(empresa, eleccion);

        if (confirmacion.isConfirmado()) {
            TipoColegioElectoral tipoColegio = TipoColegioElectoral.NO_APLICA;
            if (eleccion.getNumeroTrabajadores() >= 50) {
                Optional<TipoColegioElectoral> seleccion = preguntarTipoColegio();
                if (seleccion.isEmpty()) {
                    logger.info("El usuario canceló la selección de colegio electoral.");
                    return; // Cancelar la operación si el usuario cierra el diálogo
                }
                tipoColegio = seleccion.get();
            }

            try {
                String rutaPdf = preavisoService.procesarPreaviso(empresa, eleccion, tipoColegio);
                logger.info("Proceso de preaviso completado. PDF generado en: {}", rutaPdf);
                AlertManager.mostrarAlertaInformacion("Éxito", "PDF y datos guardados correctamente en: " + rutaPdf);
            } catch (Exception e) {
                logger.error("Error al procesar el preaviso.", e);
                AlertManager.mostrarAlertaError("Error Inesperado", "Ocurrió un error al procesar el preaviso: " + e.getMessage());
            }
        } else {
            logger.info("El usuario canceló la operación de guardado.");
        }
    }

    private Optional<TipoColegioElectoral> preguntarTipoColegio() {
        Dialog<TipoColegioElectoral> dialog = new Dialog<>();
        dialog.setTitle("Selección de Colegio Electoral");
        dialog.setHeaderText("¿Hay colegios electorales?");

        // Añadir solo el botón de cancelar al panel de botones estándar
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        // Crear un contenedor vertical para los botones de selección
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER_LEFT);
        vbox.setPadding(new Insets(20, 10, 10, 10));

        // Crear botones personalizados
        Button btnDosColegios = new Button("Colegio Especialistas\nColegio Técnicos y Administrativos");
        btnDosColegios.setMaxWidth(Double.MAX_VALUE);
        btnDosColegios.setOnAction(e -> dialog.setResult(TipoColegioElectoral.DOS_COLEGIOS));

        Button btnUnico = new Button("Colegio Único");
        btnUnico.setMaxWidth(Double.MAX_VALUE);
        btnUnico.setOnAction(e -> dialog.setResult(TipoColegioElectoral.UNICO));

        Button btnTodos = new Button("Generar todo");
        btnTodos.setMaxWidth(Double.MAX_VALUE);
        btnTodos.setOnAction(e -> dialog.setResult(TipoColegioElectoral.TODOS));

        // Añadir botones al VBox en el orden especificado
        vbox.getChildren().addAll(btnDosColegios, btnUnico, btnTodos);
        dialog.getDialogPane().setContent(vbox);

        return dialog.showAndWait();
    }

    @FXML
    public void handleLimpiar() {
        tfNombreEmpresa.clear();
        tfCif.clear();
        tfNombreComercial.clear();
        tfNombreCentro.clear();
        tfDireccion.clear();
        tfMunicipio.clear();
        tfComarca.clear();
        tfCodigoPostal.clear();
        tfNumeroTrabajadores.clear();
        tfNumeroISS.clear();
        configurarValoresPorDefecto();
        logger.info("Campos del formulario de preaviso limpiados.");
    }

    private boolean validarFormulario() {
        if (tfNombreEmpresa.getText().isBlank() || tfCif.getText().isBlank() || tfDireccion.getText().isBlank() ||
                tfMunicipio.getText().isBlank() || cbProvincia.getValue().isBlank() || tfCodigoPostal.getText().isBlank() ||
                tfNumeroTrabajadores.getText().isBlank() || dpFechaConstitucion.getValue() == null || dpFechaPreaviso.getValue() == null) {
            AlertManager.mostrarAlertaAdvertencia("Campos Obligatorios", "Por favor, rellene todos los campos requeridos.");
            return false;
        }
        try {
            Integer.parseInt(tfNumeroTrabajadores.getText());
        } catch (NumberFormatException e) {
            AlertManager.mostrarAlertaError("Formato Incorrecto", "El campo 'Nº Trabajadores' debe ser un número.");
            return false;
        }
        return true;
    }

    private Empresa crearEmpresaDesdeFormulario() {
        Empresa empresa = new Empresa();
        empresa.setNombre(tfNombreEmpresa.getText().toUpperCase());
        empresa.setCif(tfCif.getText().toUpperCase());
        empresa.setNombreComercial(tfNombreComercial.getText().isBlank() ? tfNombreEmpresa.getText().toUpperCase() : tfNombreComercial.getText().toUpperCase());
        empresa.setNombreCentro(tfNombreCentro.getText().isBlank() ? tfNombreEmpresa.getText().toUpperCase() : tfNombreCentro.getText().toUpperCase());
        empresa.setDireccion(tfDireccion.getText().toUpperCase());
        empresa.setMunicipio(tfMunicipio.getText().toUpperCase());
        empresa.setComarca(tfComarca.getText().toUpperCase());
        empresa.setProvincia(cbProvincia.getValue().toUpperCase());
        empresa.setCodigoPostal(tfCodigoPostal.getText());
        empresa.setNumeroISS(tfNumeroISS.getText());
        return empresa;
    }

    private Eleccion crearEleccionDesdeFormulario(Empresa empresa) {
        Eleccion eleccion = new Eleccion();
        eleccion.setIdEmpresa(empresa.getId());
        eleccion.setNumeroTrabajadores(Integer.parseInt(tfNumeroTrabajadores.getText()));
        eleccion.setFechaConstitucion(dpFechaConstitucion.getValue());
        eleccion.setPromotores(tfPromotores.getText());
        eleccion.setTipoEleccion(rbTotal.isSelected() ? "TOTAL" : "PARCIAL");
        eleccion.setFecha(dpFechaPreaviso.getValue());
        eleccion.setLocalidadFecha(String.format("%s, %s", empresa.getMunicipio(),
                ConversorFechaToLetras.convertirFechaEnLetras(dpFechaPreaviso.getValue())).toUpperCase());
        return eleccion;
    }
}
