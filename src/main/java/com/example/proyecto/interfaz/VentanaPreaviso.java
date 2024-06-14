package com.example.proyecto.interfaz;

import com.example.proyecto.modal.DatabaseManager;
import com.example.proyecto.modal.EleccionesDAO;
import com.example.proyecto.modal.EmpresaDAO;
import com.example.proyecto.modal.Preaviso;
import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.MessageManager;
import com.example.proyecto.util.Meses;
import com.example.proyecto.util.Registro;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

/**
 * La clase `VentanaPreaviso` gestiona la interfaz de la ventana para los preavisos.
 * Utiliza JavaFX para la interfaz gráfica y permite registrar nuevos preavisos.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 */
public class VentanaPreaviso {

    private final PrincipalView nuevaVentanaPreaviso;
    private final Preaviso nuevoPreaviso;
    private boolean alertaMostrada = false;
    private TextField textFieldNombreEmpresa;
    private TextField textFieldCIF;
    private TextField textFieldNombreComercial;
    private TextField textFieldNombreCentro;
    private TextField textFieldDireccion;
    private TextField textFieldMunicipio;
    private TextField textFieldCodigoPostal;
    private TextField textFieldProvincia;
    private TextField textFieldNumTrabajadores;
    private TextField textFieldNumSegSocial;
    private ComboBox<Meses> comboBoxMesEleccion;
    private TextField textFieldPromotores;
    private DatePicker datePickerFechaInicio;
    private DatePicker datePickerFechaPreaviso;

    /**
     * Constructor de la clase VentanaPreaviso.
     *
     * @param principalView La vista principal.
     * @param nuevoPreaviso El objeto Preaviso que se va a registrar.
     */
    public VentanaPreaviso(PrincipalView principalView, Preaviso nuevoPreaviso) {
        this.nuevaVentanaPreaviso = principalView;
        this.nuevoPreaviso = nuevoPreaviso;
    }

    /**
     * Muestra la ventana de preaviso.
     *
     * @throws IOException Si hay un error al mostrar la ventana.
     */
    public void mostrarVentanaPreaviso() throws IOException {
        Stage ventanaDatos = new Stage();
        ventanaDatos.initModality(Modality.APPLICATION_MODAL);
        ventanaDatos.setTitle(MessageManager.getMessage("preaviso.titulo"));

        Registro registro = new Registro(nuevoPreaviso, nuevaVentanaPreaviso);

        GridPane gridPane = crearGridPane();
        agregarCampos(gridPane);

        configurarValidadores();

        Button btnRegistrar = new Button(MessageManager.getMessage("preaviso.registrar"));
        btnRegistrar.setOnAction(_ -> {
            try {
                registrarPreaviso(registro, ventanaDatos);
            } catch (CumplimentarPDFException e) {
                nuevaVentanaPreaviso.mostrarMensaje(e.getMessage(), false);
            } catch (SQLException e) {
                nuevaVentanaPreaviso.mostrarMensaje(MessageManager.getMessage("preaviso.error_registro"), false);
            }
        });

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(gridPane, btnRegistrar);

        Scene scene = new Scene(vbox, 600, 800);
        ventanaDatos.setScene(scene);
        ventanaDatos.showAndWait();
    }

    /**
     * Crea y configura el GridPane.
     *
     * @return el GridPane configurado.
     */
    private GridPane crearGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(col1, col2);

        return gridPane;
    }

    /**
     * Agrega los campos al GridPane.
     *
     * @param gridPane el GridPane donde se agregarán los campos.
     */
    private void agregarCampos(GridPane gridPane) {
        agregarSeccion(gridPane, MessageManager.getMessage("preaviso.datos_empresa"), 0);
        textFieldNombreEmpresa = agregarCampo(gridPane, MessageManager.getMessage("preaviso.nombre_empresa"), 1);
        textFieldCIF = agregarCampo(gridPane, MessageManager.getMessage("preaviso.cif"), 2);
        textFieldNombreComercial = agregarCampo(gridPane, MessageManager.getMessage("preaviso.nombre_comercial"), 3);

        agregarSeccion(gridPane, MessageManager.getMessage("preaviso.datos_centro"), 4);
        textFieldNombreCentro = agregarCampo(gridPane, MessageManager.getMessage("preaviso.nombre_centro"), 5);
        textFieldDireccion = agregarCampo(gridPane, MessageManager.getMessage("preaviso.direccion"), 6);
        textFieldMunicipio = agregarCampo(gridPane, MessageManager.getMessage("preaviso.municipio"), 7);
        textFieldCodigoPostal = agregarCampo(gridPane, MessageManager.getMessage("preaviso.codigo_postal"), 8);
        textFieldProvincia = agregarCampo(gridPane, MessageManager.getMessage("preaviso.provincia"), 9);
        textFieldNumTrabajadores = agregarCampo(gridPane, MessageManager.getMessage("preaviso.num_trabajadores"), 10);
        textFieldNumSegSocial = agregarCampo(gridPane, MessageManager.getMessage("preaviso.num_seguridad_social"), 11);

        agregarSeccion(gridPane, MessageManager.getMessage("preaviso.datos_eleccion"), 12);
        comboBoxMesEleccion = agregarComboBoxMeses(gridPane, MessageManager.getMessage("preaviso.mes_eleccion"), 13);
        textFieldPromotores = agregarCampo(gridPane, MessageManager.getMessage("preaviso.promotores"), 14);
        datePickerFechaInicio = agregarDatePicker(gridPane, MessageManager.getMessage("preaviso.fecha_inicio"), 15);
        datePickerFechaPreaviso = agregarDatePicker(gridPane, MessageManager.getMessage("preaviso.fecha_preaviso"), 16);

        configurarMesEleccionPorDefecto();
    }

    /**
     * Agrega una sección al GridPane.
     *
     * @param gridPane el GridPane donde se agregará la sección.
     * @param titulo   el título de la sección.
     * @param rowIndex el índice de la fila donde se agregará la sección.
     */
    private void agregarSeccion(GridPane gridPane, String titulo, int rowIndex) {
        Label label = new Label(titulo);
        label.setStyle(Constantes.BOLD_UNDERLINED_STYLE);
        gridPane.add(label, 0, rowIndex, 2, 1);
    }

    /**
     * Agrega un campo al GridPane.
     *
     * @param gridPane el GridPane donde se agregará el campo.
     * @param labelText el texto de la etiqueta.
     * @param rowIndex el índice de la fila donde se agregará el campo.
     * @return el TextField asociado.
     */
    private TextField agregarCampo(GridPane gridPane, String labelText, int rowIndex) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 14px;");
        TextField textField = new TextField();
        gridPane.add(label, 0, rowIndex);
        gridPane.add(textField, 1, rowIndex);
        return textField;
    }

    /**
     * Agrega un ComboBox de meses al GridPane.
     *
     * @param gridPane el GridPane donde se agregará el ComboBox.
     * @param labelText el texto de la etiqueta.
     * @param rowIndex el índice de la fila donde se agregará el ComboBox.
     * @return el ComboBox asociado.
     */
    private ComboBox<Meses> agregarComboBoxMeses(GridPane gridPane, String labelText, int rowIndex) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 14px;");
        ComboBox<Meses> comboBox = new ComboBox<>();
        comboBox.getItems().setAll(Meses.values());
        gridPane.add(label, 0, rowIndex);
        gridPane.add(comboBox, 1, rowIndex);
        return comboBox;
    }

    /**
     * Agrega un DatePicker al GridPane.
     *
     * @param gridPane el GridPane donde se agregará el DatePicker.
     * @param labelText el texto de la etiqueta.
     * @param rowIndex el índice de la fila donde se agregará el DatePicker.
     * @return el DatePicker asociado.
     */
    private DatePicker agregarDatePicker(GridPane gridPane, String labelText, int rowIndex) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 14px;");
        DatePicker datePicker = new DatePicker();
        gridPane.add(label, 0, rowIndex);
        gridPane.add(datePicker, 1, rowIndex);
        return datePicker;
    }

    /**
     * Configura los validadores para los campos de texto.
     */
    private void configurarValidadores() {
        addValidationListenerWithErrorHandling(textFieldNombreEmpresa, (text, preaviso) -> preaviso.setNombreEmpresa(text.toUpperCase()), Duration.seconds(0.1));
        addValidationListenerWithErrorHandling(textFieldCIF, (text, preaviso) -> preaviso.setCIF(text.toUpperCase()), Duration.seconds(0.1));
        addValidationListenerWithErrorHandling(textFieldDireccion, (text, preaviso) -> preaviso.setDireccion(text.toUpperCase()), Duration.seconds(0.1));
        addValidationListenerWithErrorHandling(textFieldMunicipio, (text, preaviso) -> preaviso.setMunicipio(text.toUpperCase()), Duration.seconds(0.1));
        addValidationListenerWithErrorHandling(textFieldCodigoPostal, (text, preaviso) -> preaviso.setCodigoPostal(text), Duration.seconds(0.1));
        addValidationListenerWithErrorHandling(textFieldNumTrabajadores, (text, preaviso) -> preaviso.setTotalTrabajadores(text), Duration.seconds(0.1));
        addValidationListenerWithErrorHandling(textFieldNumSegSocial, (text, preaviso) -> preaviso.setNumeroISS(text), Duration.seconds(0.1));
        addValidationListenerWithErrorHandling(comboBoxMesEleccion, (text, preaviso) -> preaviso.setMesElecciones(text.getNombre()), Duration.seconds(0.1));
        addValidationListenerWithErrorHandling(datePickerFechaInicio.getEditor(), (text, preaviso) -> preaviso.setFechaConstitucion(text), Duration.seconds(0.1));
        addValidationListenerWithErrorHandling(datePickerFechaPreaviso.getEditor(), (text, preaviso) -> preaviso.setFechaPreaviso(text), Duration.seconds(0.1));
    }

    /**
     * Registra el preaviso.
     *
     * @param registro el registro donde se guardará el preaviso.
     */
    private void registrarPreaviso(Registro registro, Stage stage) throws CumplimentarPDFException, SQLException {

        nuevoPreaviso.setNombreComercial(textFieldNombreComercial.getText().toUpperCase());
        nuevoPreaviso.setNombreCentro(textFieldNombreCentro.getText().toUpperCase());
        nuevoPreaviso.setProvincia(textFieldProvincia.getText().toUpperCase());
        nuevoPreaviso.setPromotores(textFieldPromotores.getText().toUpperCase());

        nuevoPreaviso.setMesElecciones(comboBoxMesEleccion.getValue().getNombre());

        GridPane mensajeConfirmacion = construirMensajeConfirmacion();

        Optional<ButtonType> result = nuevaVentanaPreaviso.mostrarAlertaConfirmacion(mensajeConfirmacion);
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Crea instancia de DatabaseManager y EmpresaDAO
                DatabaseManager dbManager = new DatabaseManager(nuevaVentanaPreaviso);
                EmpresaDAO empresaDAO = new EmpresaDAO(nuevaVentanaPreaviso, dbManager);
                EleccionesDAO eleccionesDAO = new EleccionesDAO(nuevaVentanaPreaviso, dbManager);

                // Inserta los datos de la empresa en la base de datos
                empresaDAO.insertEmpresa(nuevoPreaviso);
                eleccionesDAO.insertFechaConstitucion(nuevoPreaviso);

                // Registra el preaviso
                registro.registrarNuevoPreaviso(nuevoPreaviso);
                cerrarVentanaPreaviso(stage);
            } catch (SQLException e) {
                nuevaVentanaPreaviso.mostrarMensaje(MessageManager.getMessage("preaviso.error_db") + e.getMessage(), false);
            }
        }
    }

    private void cerrarVentanaPreaviso(Stage stage) {
        stage.close();
    }

    /**
     * Configura el mes de elección por defecto en el ComboBox de acuerdo al número de trabajadores.
     */
    private void configurarMesEleccionPorDefecto() {
        textFieldNumTrabajadores.focusedProperty().addListener((_, _, newValue) -> {
            if (!newValue) {
                try {
                    int numTrabajadores = Integer.parseInt(textFieldNumTrabajadores.getText());
                    LocalDate fechaActual = LocalDate.now();
                    Meses mesSiguiente = Meses.values()[(fechaActual.getMonthValue()) % 12];
                    Meses mesDosMesesDespues = Meses.values()[(fechaActual.getMonthValue() + 1) % 12];

                    if (numTrabajadores < 51) {
                        comboBoxMesEleccion.setValue(mesSiguiente);
                    } else {
                        comboBoxMesEleccion.setValue(mesDosMesesDespues);
                    }
                } catch (NumberFormatException e) {
                    nuevaVentanaPreaviso.mostrarMensaje(MessageManager.getMessage("preaviso.numero_trabajadores_invalido"), false);
                }
            }
        });
    }

    /**
     * Agrega un listener de validación a un campo de texto con manejo de excepciones.
     *
     * @param textField el campo de texto a validar.
     * @param setter el método para establecer el valor en el objeto Preaviso.
     * @param focusDuration la duración para reenfocar el campo de texto en caso de error.
     */
    private void addValidationListenerWithErrorHandling(TextField textField, CheckedBiConsumer<String, Preaviso> setter, Duration focusDuration) {
        textField.focusedProperty().addListener((_, _, newValue) -> {
            if (!newValue && !alertaMostrada) {
                try {
                    setter.accept(textField.getText(), nuevoPreaviso);
                } catch (CumplimentarPDFException e) {
                    alertaMostrada = true;
                    nuevaVentanaPreaviso.mostrarMensaje(e.getMessage(), false);
                    PauseTransition pause = new PauseTransition(focusDuration);
                    pause.setOnFinished(_ -> {
                        textField.requestFocus();
                        textField.positionCaret(textField.getLength());
                        alertaMostrada = false;
                    });
                    pause.play();
                }
            }
        });
    }

    // Método para construir el GridPane con los datos
    private GridPane construirMensajeConfirmacion() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        int rowIndex = 0;
        agregarFila(gridPane, MessageManager.getMessage("preaviso.nombre_empresa"), textFieldNombreEmpresa.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.cif"), textFieldCIF.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.nombre_comercial"), textFieldNombreComercial.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.nombre_centro"), textFieldNombreCentro.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.direccion"), textFieldDireccion.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.municipio"), textFieldMunicipio.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.codigo_postal"), textFieldCodigoPostal.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.provincia"), textFieldProvincia.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.num_trabajadores"), textFieldNumTrabajadores.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.num_seguridad_social"), textFieldNumSegSocial.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.mes_eleccion"), comboBoxMesEleccion.getValue() != null ? comboBoxMesEleccion.getValue().getNombre() : "", rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.promotores"), textFieldPromotores.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.fecha_inicio"), datePickerFechaInicio.getValue() != null ? datePickerFechaInicio.getValue().toString() : "", rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.fecha_preaviso"), datePickerFechaPreaviso.getValue() != null ? datePickerFechaPreaviso.getValue().toString() : "", rowIndex++);

        return gridPane;
    }

    // Método auxiliar para agregar una fila al GridPane
    private void agregarFila(GridPane gridPane, String label, String value, int rowIndex) {
        Text labelText = new Text(label + " ");
        Text valueText = new Text(value);
        valueText.setStyle("-fx-font-weight: bold");

        gridPane.add(labelText, 0, rowIndex);
        gridPane.add(valueText, 1, rowIndex);
    }

    @FunctionalInterface
    public interface CheckedBiConsumer<T, U> {
        void accept(T t, U u) throws CumplimentarPDFException;
    }
}
