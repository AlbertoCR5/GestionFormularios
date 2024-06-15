package com.example.proyecto.interfaz.preaviso;

import com.example.proyecto.interfaz.PrincipalView;
import com.example.proyecto.modal.Preaviso;
import com.example.proyecto.util.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * La clase `PreavisoFormManager` gestiona la creación y configuración de los formularios de preaviso.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class PreavisoFormManager {

    private final Preaviso nuevoPreaviso;
    private final PrincipalView nuevaVentanaPreaviso;
    private TextField textFieldNombreEmpresa;
    private TextField textFieldCIF;
    private TextField textFieldNombreComercial;
    private TextField textFieldNombreCentro;
    private TextField textFieldDireccion;
    private TextField textFieldMunicipio;
    private TextField textFieldCodigoPostal;
    private ComboBox<ProvinciasAndalucia> comboBoxProvincia;
    private TextField textFieldNumTrabajadores;
    private TextField textFieldNumSegSocial;
    private ComboBox<Meses> comboBoxMesEleccion;
    private TextField textFieldPromotores;
    private DatePicker datePickerFechaInicio;
    private DatePicker datePickerFechaPreaviso;

    /**
     * Constructor de la clase PreavisoFormManager.
     *
     * @param nuevoPreaviso El objeto Preaviso que se va a registrar.
     * @param nuevaVentanaPreaviso La vista principal.
     */
    public PreavisoFormManager(@NotNull Preaviso nuevoPreaviso, @NotNull PrincipalView nuevaVentanaPreaviso) {
        this.nuevoPreaviso = nuevoPreaviso;
        this.nuevaVentanaPreaviso = nuevaVentanaPreaviso;
    }

    /**
     * Crea y configura el formulario de preaviso.
     *
     * @return Un VBox que contiene el formulario.
     */
    public VBox crearFormulario() {
        GridPane gridPane = crearGridPane();
        agregarCampos(gridPane);
        configurarValidadores();

        VBox vbox = new VBox(Constantes.ESPACIADO_VBOX);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(gridPane);

        return vbox;
    }

    /**
     * Crea y configura el GridPane.
     *
     * @return el GridPane configurado.
     */
    private GridPane crearGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(Constantes.ESPACIADO_HGAP);
        gridPane.setVgap(Constantes.ESPACIADO_VGAP);
        gridPane.setPadding(new Insets(Constantes.ESPACIADO_PADDING));

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
    private void agregarCampos(@NotNull GridPane gridPane) {
        agregarSeccion(gridPane, MessageManager.getMessage("preaviso.datos_empresa"), 0);
        textFieldNombreEmpresa = agregarCampo(gridPane, MessageManager.getMessage("preaviso.nombre_empresa"), 1);
        textFieldCIF = agregarCampo(gridPane, MessageManager.getMessage("preaviso.cif"), 2);
        textFieldNombreComercial = agregarCampo(gridPane, MessageManager.getMessage("preaviso.nombre_comercial"), 3);

        agregarSeccion(gridPane, MessageManager.getMessage("preaviso.datos_centro"), 4);
        textFieldNombreCentro = agregarCampo(gridPane, MessageManager.getMessage("preaviso.nombre_centro"), 5);
        textFieldDireccion = agregarCampo(gridPane, MessageManager.getMessage("preaviso.direccion"), 6);
        textFieldMunicipio = agregarCampo(gridPane, MessageManager.getMessage("preaviso.municipio"), 7);
        textFieldCodigoPostal = agregarCampo(gridPane, MessageManager.getMessage("preaviso.codigo_postal"), 8);
        comboBoxProvincia = agregarComboBoxProvincias(gridPane, MessageManager.getMessage("preaviso.provincia"), 9);
        textFieldNumTrabajadores = agregarCampo(gridPane, MessageManager.getMessage("preaviso.num_trabajadores"), 10);
        textFieldNumSegSocial = agregarCampo(gridPane, MessageManager.getMessage("preaviso.num_seguridad_social"), 11);

        agregarSeccion(gridPane, MessageManager.getMessage("preaviso.datos_eleccion"), 12);
        comboBoxMesEleccion = agregarComboBoxMeses(gridPane, MessageManager.getMessage("preaviso.mes_eleccion"), 13);
        textFieldPromotores = agregarCampo(gridPane, MessageManager.getMessage("preaviso.promotores"), 14);

        datePickerFechaInicio = agregarDatePicker(gridPane, MessageManager.getMessage("preaviso.fecha_inicio"), 15);
        datePickerFechaPreaviso = agregarDatePicker(gridPane, MessageManager.getMessage("preaviso.fecha_preaviso"), 16);

        configurarMesEleccionPorDefecto();
        configurarFechaConstitucionPorDefecto();
    }

    /**
     * Agrega una sección al GridPane.
     *
     * @param gridPane el GridPane donde se agregará la sección.
     * @param titulo   el título de la sección.
     * @param rowIndex el índice de la fila donde se agregará la sección.
     */
    private void agregarSeccion(@NotNull GridPane gridPane, @NotNull String titulo, int rowIndex) {
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
    private TextField agregarCampo(@NotNull GridPane gridPane, @NotNull String labelText, int rowIndex) {
        Label label = new Label(labelText);
        label.setStyle(Constantes.ESTILO_ETIQUETA_LOGIN);
        TextField textField = new TextField();
        gridPane.add(label, 0, rowIndex);
        gridPane.add(textField, 1, rowIndex);
        return textField;
    }

    private ComboBox<ProvinciasAndalucia> agregarComboBoxProvincias(@NotNull GridPane gridPane, @NotNull String labelText, int rowIndex) {
        Label label = new Label(labelText);
        label.setStyle(Constantes.ESTILO_ETIQUETA_LOGIN);
        ComboBox<ProvinciasAndalucia> comboBox = new ComboBox<>();
        comboBox.getItems().setAll(ProvinciasAndalucia.values());
        comboBox.setValue(ProvinciasAndalucia.SEVILLA); // Seleccionar Sevilla por defecto
        gridPane.add(label, 0, rowIndex);
        gridPane.add(comboBox, 1, rowIndex);
        return comboBox;
    }

    /**
     * Agrega un ComboBox de meses al GridPane.
     *
     * @param gridPane el GridPane donde se agregará el ComboBox.
     * @param labelText el texto de la etiqueta.
     * @param rowIndex el índice de la fila donde se agregará el ComboBox.
     * @return el ComboBox asociado.
     */
    private ComboBox<Meses> agregarComboBoxMeses(@NotNull GridPane gridPane, @NotNull String labelText, int rowIndex) {
        Label label = new Label(labelText);
        label.setStyle(Constantes.ESTILO_ETIQUETA_LOGIN);
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
    private DatePicker agregarDatePicker(@NotNull GridPane gridPane, @NotNull String labelText, int rowIndex) {
        Label label = new Label(labelText);
        label.setStyle(Constantes.ESTILO_ETIQUETA_LOGIN);
        DatePicker datePicker = new DatePicker();
        datePicker.setPrefWidth(Constantes.ANCHO_DATEPICKER);
        gridPane.add(label, 0, rowIndex);
        gridPane.add(datePicker, 1, rowIndex);
        return datePicker;
    }

    /**
     * Configura los validadores para los campos de texto y ComboBox.
     */
    private void configurarValidadores() {
        PreavisoFormValidator validator = new PreavisoFormValidator(nuevaVentanaPreaviso, nuevoPreaviso);
        validator.addValidationListenerWithErrorHandling(textFieldNombreEmpresa, (text, preaviso) -> preaviso.setNombreEmpresa(text.toUpperCase()), Duration.seconds(Constantes.DURACION_VALIDACION));
        validator.addValidationListenerWithErrorHandling(textFieldCIF, (text, preaviso) -> preaviso.setCIF(text.toUpperCase()), Duration.seconds(Constantes.DURACION_VALIDACION));
        validator.addValidationListenerWithErrorHandling(textFieldDireccion, (text, preaviso) -> preaviso.setDireccion(text.toUpperCase()), Duration.seconds(Constantes.DURACION_VALIDACION));
        validator.addValidationListenerWithErrorHandling(textFieldMunicipio, (text, preaviso) -> preaviso.setMunicipio(text.toUpperCase()), Duration.seconds(Constantes.DURACION_VALIDACION));
        validator.addValidationListenerWithErrorHandling(textFieldCodigoPostal, (text, preaviso) -> preaviso.setCodigoPostal(text), Duration.seconds(Constantes.DURACION_VALIDACION));
        validator.addProvinciaValidationListenerWithErrorHandling(comboBoxProvincia, (provincia, preaviso) -> preaviso.setProvincia(provincia.getNombre()), Duration.seconds(Constantes.DURACION_VALIDACION));
        validator.addValidationListenerWithErrorHandling(textFieldNumTrabajadores, (text, preaviso) -> preaviso.setTotalTrabajadores(text), Duration.seconds(Constantes.DURACION_VALIDACION));
        validator.addValidationListenerWithErrorHandling(textFieldNumSegSocial, (text, preaviso) -> preaviso.setNumeroISS(text), Duration.seconds(Constantes.DURACION_VALIDACION));
        validator.addMesValidationListenerWithErrorHandling(comboBoxMesEleccion, (mes, preaviso) -> preaviso.setMesElecciones(mes.getNombre()), Duration.seconds(Constantes.DURACION_VALIDACION));
        validator.addValidationListenerWithErrorHandling(datePickerFechaInicio.getEditor(), (text, preaviso) -> preaviso.setFechaConstitucion(text), Duration.seconds(Constantes.DURACION_VALIDACION));
        validator.addValidationListenerWithErrorHandling(datePickerFechaPreaviso.getEditor(), (text, preaviso) -> preaviso.setFechaPreaviso(text), Duration.seconds(Constantes.DURACION_VALIDACION));
    }

    /**
     * Crea el botón de registro.
     *
     * @param stage La ventana de preaviso.
     * @return El botón de registro configurado.
     */
    public Button crearBotonRegistrar(@NotNull Stage stage) {
        Button btnRegistrar = new Button(MessageManager.getMessage("preaviso.registrar"));
        btnRegistrar.setOnAction(_ -> {
            try {
                // Establece los valores de los campos en el objeto nuevoPreaviso
                nuevoPreaviso.setNombreComercial(textFieldNombreComercial.getText().toUpperCase());
                nuevoPreaviso.setNombreCentro(textFieldNombreCentro.getText().toUpperCase());
                nuevoPreaviso.setPromotores(textFieldPromotores.getText().toUpperCase());

                PreavisoRegistrar registrar = new PreavisoRegistrar(nuevaVentanaPreaviso);
                registrar.registrarPreaviso(nuevoPreaviso, stage);
            } catch (CumplimentarPDFException e) {
                nuevaVentanaPreaviso.mostrarMensaje(e.getMessage(), false);
            } catch (SQLException e) {
                nuevaVentanaPreaviso.mostrarMensaje(MessageManager.getMessage("preaviso.error_registro"), false);
            }
        });
        return btnRegistrar;
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

                    if (numTrabajadores <= Constantes.MAXIMO_ELECTORES_DELEGADOS) {
                        comboBoxMesEleccion.setValue(mesSiguiente);
                    } else {
                        comboBoxMesEleccion.setValue(mesDosMesesDespues);
                    }
                } catch (NumberFormatException e) {
                    nuevaVentanaPreaviso.mostrarMensaje(MessageManager.getMessage("preaviso.error.configurar.mes.eleccion"), false);
                }
            }
        });
    }

    /**
     * Configura la fecha por defecto para el DatePicker de fechaConstitucion.
     */
    private void configurarFechaConstitucionPorDefecto() {
        LocalDate fechaConstitucion = LocalDate.now().plusDays(32);
        while (fechaConstitucion.getDayOfWeek() == DayOfWeek.SATURDAY || fechaConstitucion.getDayOfWeek() == DayOfWeek.SUNDAY) {
            fechaConstitucion = fechaConstitucion.plusDays(1);
        }
        datePickerFechaInicio.setValue(fechaConstitucion);
    }
}
