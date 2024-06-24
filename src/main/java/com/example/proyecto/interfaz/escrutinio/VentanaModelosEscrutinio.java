package com.example.proyecto.interfaz.escrutinio;

import com.example.proyecto.interfaz.PrincipalView;
import com.example.proyecto.modal.*;
import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.MessageManager;
import com.example.proyecto.util.Registro;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

/**
 * La clase VentanaModelosEscrutinio gestiona la secuencia de ventanas para cumplimentar los modelos de escrutinio.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class VentanaModelosEscrutinio {

    private final PrincipalView vistaPrincipal;
    private final Modelo_5_1 nuevoModelo5_1;
    private final Modelo_5_2_Proceso nuevoModelo5_2Proceso;
    private final Modelo_5_2_Conclusion nuevoModeloConclusion;
    private final Path rutaEmpresa;
    private Registro registro;
    private TextField textFieldNumeroPreaviso;
    private DatePicker datePicker;
    private TextField textFieldActividadEconomica;
    private TextField textFieldConvenio;
    private TextField textFieldNumeroConvenio;
    private TextField textFieldTrabajadoresFijos;
    private TextField textFieldTrabajadoresEventuales;
    private TextField textFieldTrabajadoresJornadas;
    private TextField textFieldComputoTrabajadoresEventuales;
    private TextField textFieldTotalTrabajadoresComputo;
    private TextField textFieldTotalElectores;
    private TextField textFieldElectoresVarones;
    private TextField textFieldElectoresMujeres;

    /**
     * Constructor de la clase VentanaModelosEscrutinio.
     *
     * @param vistaPrincipal La vista principal de la aplicación.
     * @param rutaEmpresa La ruta donde se guardan los datos de la empresa.
     */
    public VentanaModelosEscrutinio(@NotNull PrincipalView vistaPrincipal, Modelo_5_1 nuevoModelo51, Modelo_5_2_Proceso nuevoModelo52Proceso, Modelo_5_2_Conclusion nuevoModeloConclusion, @NotNull Path rutaEmpresa) throws IOException {
        this.vistaPrincipal = vistaPrincipal;
        nuevoModelo5_1 = nuevoModelo51;
        nuevoModelo5_2Proceso = nuevoModelo52Proceso;
        this.nuevoModeloConclusion = nuevoModeloConclusion;
        this.rutaEmpresa = rutaEmpresa;
        iniciarSecuencia();
    }

    /**
     * Inicia la secuencia de ventanas.
     */
    public void iniciarSecuencia() {
        Platform.runLater(() -> {
            try {
                mostrarVentanaEscrutinio();
            } catch (IOException e) {
                mostrarMensajeError("modelo51.error_mostrar", e);
            }
        });
    }

    /**
     * Muestra la ventana de escrutinio.
     *
     * @throws IOException Si hay un error al mostrar la ventana.
     */
    private void mostrarVentanaEscrutinio() throws IOException {
        Stage stage = new Stage();
        stage.setTitle(MessageManager.getMessage("escrutinio.title"));

        VBox vbox = crearVBoxGeneral();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        Label labelNumeroPreaviso = new Label(MessageManager.getMessage("modelo5_1.preaviso"));
        labelNumeroPreaviso.setStyle(Constantes.FONT_SIZE_14_FONT_WEIGHT_BOLD);
        textFieldNumeroPreaviso = new TextField();
        textFieldNumeroPreaviso.setMaxWidth(100);

        Label labelFechaEscrutinio = new Label(MessageManager.getMessage("modelo5_1.fecha_escrutinio"));
        labelFechaEscrutinio.setStyle(Constantes.FONT_SIZE_14_FONT_WEIGHT_BOLD);
        datePicker = getDatePicker();

        Label labelOtrosDatosEmpresa = new Label(MessageManager.getMessage("conclusion.otros_datos_empresa"));
        labelOtrosDatosEmpresa.setStyle(Constantes.BOLD_UNDERLINED_STYLE);

        Label labelActividadEconomica = new Label(MessageManager.getMessage("conclusion.actividad_economica"));
        labelActividadEconomica.setStyle(Constantes.ESTILO_ETIQUETA_14PX);
        textFieldActividadEconomica = new TextField();

        Label labelConvenio = new Label(MessageManager.getMessage("conclusion.convenio"));
        labelConvenio.setStyle(Constantes.ESTILO_ETIQUETA_14PX);
        textFieldConvenio = new TextField();

        Label labelNumeroConvenio = new Label(MessageManager.getMessage("conclusion.numero_convenio"));
        labelNumeroConvenio.setStyle(Constantes.ESTILO_ETIQUETA_14PX);
        textFieldNumeroConvenio = new TextField();

        Label labelTrabajadores = new Label(MessageManager.getMessage("conclusion.trabajadores"));
        labelTrabajadores.setStyle(Constantes.BOLD_UNDERLINED_STYLE);

        Label labelTrabajadoresFijos = new Label(MessageManager.getMessage("conclusion.trabajadores_fijos"));
        labelTrabajadoresFijos.setStyle(Constantes.ESTILO_ETIQUETA_14PX);
        textFieldTrabajadoresFijos = new TextField();

        Label labelTrabajadoresEventuales = new Label(MessageManager.getMessage("conclusion.trabajadores_eventuales"));
        labelTrabajadoresEventuales.setStyle(Constantes.ESTILO_ETIQUETA_14PX);
        textFieldTrabajadoresEventuales = new TextField();

        Label labelTrabajadoresJornadas = new Label(MessageManager.getMessage("conclusion.trabajadores_jornadas"));
        labelTrabajadoresJornadas.setStyle(Constantes.ESTILO_ETIQUETA_14PX);
        textFieldTrabajadoresJornadas = new TextField();

        Label labelComputoTrabajadoresEventuales = new Label(MessageManager.getMessage("conclusion.computo_trabajadores_eventuales"));
        labelComputoTrabajadoresEventuales.setStyle(Constantes.ESTILO_ETIQUETA_14PX);
        textFieldComputoTrabajadoresEventuales = new TextField();
        textFieldComputoTrabajadoresEventuales.setDisable(true);

        Label labelTotalTrabajadoresComputo = new Label(MessageManager.getMessage("conclusion.total_trabajadores_computo"));
        labelTotalTrabajadoresComputo.setStyle(Constantes.ESTILO_ETIQUETA_14PX);
        textFieldTotalTrabajadoresComputo = new TextField();
        textFieldTotalTrabajadoresComputo.setDisable(true);

        Label labelElectores = new Label(MessageManager.getMessage("conclusion.electores"));
        labelElectores.setStyle(Constantes.BOLD_UNDERLINED_STYLE);

        Label labelTotalElectores = new Label(MessageManager.getMessage("conclusion.total_electores"));
        labelTotalElectores.setStyle(Constantes.ESTILO_ETIQUETA_14PX);
        textFieldTotalElectores = new TextField();
        textFieldTotalElectores.setDisable(true);

        Label labelElectoresVarones = new Label(MessageManager.getMessage("conclusion.electores_varones"));
        labelElectoresVarones.setStyle(Constantes.ESTILO_ETIQUETA_14PX);
        textFieldElectoresVarones = new TextField();

        Label labelElectoresMujeres = new Label(MessageManager.getMessage("conclusion.electores_mujeres"));
        labelElectoresMujeres.setStyle(Constantes.ESTILO_ETIQUETA_14PX);
        textFieldElectoresMujeres = new TextField();
        textFieldElectoresMujeres.setDisable(true);

        // Adding components to gridPane
        gridPane.add(labelNumeroPreaviso, 0, 0);
        gridPane.add(textFieldNumeroPreaviso, 1, 0);

        gridPane.add(labelFechaEscrutinio, 0, 1);
        gridPane.add(datePicker, 1, 1);

        gridPane.add(labelOtrosDatosEmpresa, 0, 2, 2, 1);

        gridPane.add(labelActividadEconomica, 0, 3);
        gridPane.add(textFieldActividadEconomica, 1, 3);

        gridPane.add(labelConvenio, 0, 4);
        gridPane.add(textFieldConvenio, 1, 4);

        gridPane.add(labelNumeroConvenio, 0, 5);
        gridPane.add(textFieldNumeroConvenio, 1, 5);

        gridPane.add(labelTrabajadores, 0, 6, 2, 1);

        gridPane.add(labelTrabajadoresFijos, 0, 7);
        gridPane.add(textFieldTrabajadoresFijos, 1, 7);

        gridPane.add(labelTrabajadoresEventuales, 0, 8);
        gridPane.add(textFieldTrabajadoresEventuales, 1, 8);

        gridPane.add(labelTrabajadoresJornadas, 0, 9);
        gridPane.add(textFieldTrabajadoresJornadas, 1, 9);

        gridPane.add(labelComputoTrabajadoresEventuales, 0, 10);
        gridPane.add(textFieldComputoTrabajadoresEventuales, 1, 10);

        gridPane.add(labelTotalTrabajadoresComputo, 0, 11);
        gridPane.add(textFieldTotalTrabajadoresComputo, 1, 11);

        gridPane.add(labelElectores, 0, 12, 2, 1);

        gridPane.add(labelTotalElectores, 0, 13);
        gridPane.add(textFieldTotalElectores, 1, 13);

        gridPane.add(labelElectoresVarones, 0, 14);
        gridPane.add(textFieldElectoresVarones, 1, 14);

        gridPane.add(labelElectoresMujeres, 0, 15);
        gridPane.add(textFieldElectoresMujeres, 1, 15);

        Button btnGuardar = crearBotonGuardar(stage);

        vbox.getChildren().addAll(gridPane, crearGuardarBox(btnGuardar));

        Scene scene = new Scene(vbox, 450, 620);
        stage.setScene(scene);
        stage.show();

        configurarValidadores();
    }

    /**
     * Configura los validadores para los campos del formulario.
     */
    private void configurarValidadores() {
        ModelosEscrutinioFormValidator validator = new ModelosEscrutinioFormValidator(vistaPrincipal, nuevoModelo5_1, nuevoModelo5_2Proceso, nuevoModeloConclusion);
        validator.addValidationListenerWithErrorHandlingM52(textFieldNumeroPreaviso, (text, nuevoModelo5_2Proceso) -> nuevoModelo5_2Proceso.setPreaviso(text), Duration.seconds(Constantes.DURACION_VALIDACION));
        validator.addValidationListenerWithErrorHandlingM51(datePicker.getEditor(), (text, nuevoModelo5_1) -> nuevoModelo5_1.setFechaEscrutinio(text), Duration.seconds(Constantes.DURACION_VALIDACION));
        validator.addValidationListenerWithErrorHandlingMC(textFieldNumeroConvenio, (text, nuevoModeloConclusion) -> nuevoModeloConclusion.setNumeroConvenio(text), Duration.seconds(Constantes.DURACION_VALIDACION));
        validator.addValidationListenerWithErrorHandlingMC(textFieldTrabajadoresFijos, (text, nuevoModeloConclusion) -> nuevoModeloConclusion.setTrabajadoresFijos(text), Duration.seconds(Constantes.DURACION_VALIDACION));

        textFieldTrabajadoresFijos.focusedProperty().addListener((_, _, newVal) -> {
            if (!newVal) updateComputedFields();
        });
        textFieldTrabajadoresEventuales.focusedProperty().addListener((_, _, newVal) -> {
            if (!newVal) updateComputedFields();
        });
        textFieldTrabajadoresJornadas.focusedProperty().addListener((_, _, newVal) -> {
            if (!newVal) updateComputedFields();
        });
        textFieldElectoresVarones.focusedProperty().addListener((_, _, newVal) -> {
            if (!newVal) updateComputedFields();
        });
    }

    /**
     * Actualiza los campos computados.
     */
    private void updateComputedFields() {
        try {
            int trabajadoresFijos = parseInteger(textFieldTrabajadoresFijos.getText());
            int trabajadoresEventuales = parseInteger(textFieldTrabajadoresEventuales.getText());
            int totalElectores = trabajadoresFijos + trabajadoresEventuales;
            textFieldTotalElectores.setText(String.valueOf(totalElectores));

            int jornadasEventuales = parseInteger(textFieldTrabajadoresJornadas.getText());
            double computoEventuales = new BigDecimal(jornadasEventuales).divide(new BigDecimal(200), 2, RoundingMode.HALF_UP).doubleValue();
            textFieldComputoTrabajadoresEventuales.setText(String.valueOf(computoEventuales));

            int totalTrabajadoresComputo = trabajadoresFijos + (int) Math.ceil(computoEventuales);
            textFieldTotalTrabajadoresComputo.setText(String.valueOf(totalTrabajadoresComputo));

            int electoresVarones = parseInteger(textFieldElectoresVarones.getText());
            int electoresMujeres = totalElectores - electoresVarones;
            textFieldElectoresMujeres.setText(String.valueOf(electoresMujeres));
        } catch (NumberFormatException e) {
            vistaPrincipal.mostrarMensaje(MessageManager.getMessage("error.formato_incorrecto"), false);
        }
    }

    /**
     * Parses a string to an integer. Returns 0 if the string is empty.
     *
     * @param text the string to parse
     * @return the parsed integer
     * @throws NumberFormatException if the string cannot be parsed as an integer
     */
    private int parseInteger(String text) {
        return text.isEmpty() ? 0 : Integer.parseInt(text);
    }

    @NotNull
    private VBox crearVBoxGeneral() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    @NotNull
    private Button crearBotonGuardar(@NotNull Stage stage) {
        Button btnGuardar = new Button(MessageManager.getMessage("modelo5_1.guardar"));
        btnGuardar.setOnAction(_ -> guardarInformacion(stage));
        return btnGuardar;
    }

    @NotNull
    private HBox crearGuardarBox(@NotNull Button btnGuardar) {
        HBox guardarBox = new HBox(btnGuardar);
        guardarBox.setAlignment(Pos.CENTER);
        guardarBox.setPadding(new Insets(10, 0, 0, 0));
        return guardarBox;
    }

    private void guardarInformacion(@NotNull Stage stage) {
        LocalDate fechaActa = datePicker.getValue();
        if (fechaActa == null || fechaActa.isBefore(LocalDate.now())) {
            vistaPrincipal.mostrarMensaje(MessageManager.getMessage("modelo5_1.fecha_invalida"), false);
        } else {
            try {
                nuevoModelo5_2Proceso.setPreaviso(textFieldNumeroPreaviso.getText().toUpperCase());
                nuevoModelo5_1.setFechaEscrutinio(datePicker.getValue().format(DateTimeFormatter.ofPattern("d/M/yyyy")));
                nuevoModeloConclusion.setActvEcono(textFieldActividadEconomica.getText().toUpperCase());
                nuevoModeloConclusion.setNombreConvenio(textFieldConvenio.getText().toUpperCase());
                nuevoModeloConclusion.setNumeroConvenio(textFieldNumeroConvenio.getText().toUpperCase());
                nuevoModeloConclusion.setTrabajadoresFijos(textFieldTrabajadoresFijos.getText());
                nuevoModeloConclusion.setTrabajadoresEventuales(textFieldTrabajadoresEventuales.getText());
                nuevoModeloConclusion.setTrabajadoresJornadas(textFieldTrabajadoresJornadas.getText());
                nuevoModeloConclusion.setTrabajadoresEventualesComputo(Double.parseDouble(textFieldComputoTrabajadoresEventuales.getText()));
                nuevoModeloConclusion.setTotalTrabajadores(textFieldTotalTrabajadoresComputo.getText());

                DatabaseManager dbManager = new DatabaseManager(vistaPrincipal);
                EleccionesDAO eleccionesDAO = new EleccionesDAO(vistaPrincipal, dbManager);
                EmpresaDAO empresaDAO = new EmpresaDAO(vistaPrincipal, dbManager);
                String nombreEmpresaCompleto = rutaEmpresa.getFileName().toString();
                eleccionesDAO.updateEleccion(nuevoModelo5_1, nuevoModelo5_2Proceso, nombreEmpresaCompleto);
                empresaDAO.updateEmpresaOtros(nuevoModelo5_2Proceso, nuevoModeloConclusion, nombreEmpresaCompleto);
                GridPane mensajeConfirmacion = construirMensajeConfirmacion();
                mostrarConfirmacion(stage, mensajeConfirmacion);
            } catch (CumplimentarPDFException | NumberFormatException | SQLException ex) {
                vistaPrincipal.mostrarMensaje(ex.getMessage(), false);
            }
        }
    }

    private GridPane construirMensajeConfirmacion() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        int rowIndex = 0;

        agregarFila(gridPane, MessageManager.getMessage("modelo5_1.preaviso"), nuevoModelo5_2Proceso.getPreaviso(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("modelo5_1.fecha_escrutinio"), nuevoModelo5_1.getFechaEscrutinio(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("conclusion.actividad_economica"), textFieldActividadEconomica.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("conclusion.convenio"), textFieldConvenio.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("conclusion.numero_convenio"), textFieldNumeroConvenio.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("conclusion.trabajadores_fijos"), textFieldTrabajadoresFijos.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("conclusion.trabajadores_eventuales"), textFieldTrabajadoresEventuales.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("conclusion.trabajadores_jornadas"), textFieldTrabajadoresJornadas.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("conclusion.computo_trabajadores_eventuales"), textFieldComputoTrabajadoresEventuales.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("conclusion.total_trabajadores_computo"), textFieldTotalTrabajadoresComputo.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("conclusion.total_electores"), textFieldTotalElectores.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("conclusion.electores_varones"), textFieldElectoresVarones.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("conclusion.electores_mujeres"), textFieldElectoresMujeres.getText(), rowIndex);

        return gridPane;
    }

    // Método auxiliar para agregar una fila al GridPane
    private void agregarFila(@NotNull GridPane gridPane, String label, String value, int rowIndex) {
        Label labelText = new Label(label + ": ");
        Label valueText = new Label(value);
        valueText.setStyle(Constantes.FONT_WEIGHT_BOLD);

        gridPane.add(labelText, 0, rowIndex);
        gridPane.add(valueText, 1, rowIndex);
    }

    private void mostrarConfirmacion(@NotNull Stage stage, @NotNull GridPane mensajeConfirmacion) throws CumplimentarPDFException {
        Optional<ButtonType> result = vistaPrincipal.mostrarAlertaConfirmacion(mensajeConfirmacion);
        if (result.isPresent() && result.get() == ButtonType.OK) { // Si el usuario confirma
            // Registrar los modelos de escrutinio
            if (Integer.parseInt(nuevoModeloConclusion.getTotalTrabajadores()) < 50){
                registro.registrarModelosEscrutinioDelegados(nuevoModelo5_1, nuevoModelo5_2Proceso, nuevoModeloConclusion, rutaEmpresa);
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText("¿Existen 2 colegios electorales (Especialistas y Técnicos)?");
                alert.setContentText("Elija su opción.");

                ButtonType buttonTypeSi = new ButtonType("Sí");
                ButtonType buttonTypeNo = new ButtonType("No");
                ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo, buttonTypeCancel);

                Optional<ButtonType> resultColleges = alert.showAndWait();
                Modelo_7_3_Acta_Global nuevoModelo7_3ActaGlobal = new Modelo_7_3_Acta_Global();
                Modelo_7_3_Proceso nuevoModelo7_3Proceso = new Modelo_7_3_Proceso();
                if (resultColleges.get() == buttonTypeSi) {
                    Modelo_6_1_Especialistas nuevoModelo6_1Especialistas = new Modelo_6_1_Especialistas();
                    Modelo_6_2_Especialistas nuevoModelo6_2Especialistas = new Modelo_6_2_Especialistas();
                    Modelo_6_1_Tecnicos nuevoModelo6_1Tecnicos = new Modelo_6_1_Tecnicos();
                    Modelo_6_2_Tecnicos nuevoModelo6_2Tecnicos = new Modelo_6_2_Tecnicos();
                    registro.registrarModelosEscrutinioComite(nuevoModelo6_1Especialistas, nuevoModelo6_2Especialistas, nuevoModelo6_1Tecnicos, nuevoModelo6_2Tecnicos, nuevoModelo7_3ActaGlobal, nuevoModelo7_3Proceso, rutaEmpresa);
                } else if (resultColleges.get() == buttonTypeNo) {
                    Modelo_6_1_Unico nuevoModelo6_1Unico = new Modelo_6_1_Unico();
                    Modelo_6_2_Unico nuevoModelo6_2Unico = new Modelo_6_2_Unico();
                    registro.registrarModelosEscrutinioComiteUnico(nuevoModelo6_1Unico, nuevoModelo6_2Unico, nuevoModelo7_3ActaGlobal, nuevoModelo7_3Proceso, rutaEmpresa);
                }
            }
            // Mostrar un mensaje de éxito
            vistaPrincipal.mostrarMensaje(MessageManager.getMessage("conclusion.datos_guardados"), true);
            // Cerrar la ventana actual
            stage.close();
        }
    }

    @NotNull
    private DatePicker getDatePicker() {
        DatePicker datePicker = new DatePicker();
        datePicker.setPrefWidth(125);
        datePicker.setConverter(new StringConverter<>() {
            private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");

            @Override
            public String toString(LocalDate date) {
                return date != null ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                if (string == null || string.trim().isEmpty()) {
                    return null;
                }
                try {
                    return LocalDate.parse(string, dateFormatter);
                } catch (DateTimeParseException e) {
                    vistaPrincipal.mostrarMensaje(MessageManager.getMessage("modelo5_1.fecha_invalida"), false);
                    return null;
                }
            }
        });
        return datePicker;
    }


    private void mostrarMensajeError(@NotNull String claveMensaje, @NotNull IOException e) {
        vistaPrincipal.mostrarMensaje(String.format(MessageManager.getMessage(claveMensaje), e.getMessage()), false);
    }
}
