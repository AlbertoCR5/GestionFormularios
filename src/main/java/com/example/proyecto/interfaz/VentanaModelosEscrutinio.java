package com.example.proyecto.interfaz;

import com.example.proyecto.modal.*;
import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.MessageManager;
import com.example.proyecto.util.Registro;
import javafx.animation.PauseTransition;
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
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.Optional;

/**
 * La clase `VentanaModelosEscrutinioB` gestiona la secuencia de ventanas para cumplimentar los modelos de escrutinio.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class VentanaModelosEscrutinio {

    private final PrincipalView vistaPrincipal;
    private final Path rutaEmpresa;
    private Modelo_5_1 nuevoModelo5_1;
    private Modelo_5_2_Proceso nuevoModelo5_2Proceso;
    private Modelo_5_2_Conclusion nuevoModeloConclusion;
    private Modelo_6_1_Especialistas nuevoModelo6_1Especialistas;
    private Modelo_6_2_Especialistas nuevoModelo6_2Especialistas;
    private Modelo_6_1_Tecnicos nuevoModelo6_1Tecnicos;
    private Modelo_6_2_Tecnicos nuevoModelo6_2Tecnicos;
    private Modelo_6_1_Unico nuevoModelo6_1Unico;
    private Modelo_6_2_Unico nuevoModelo6_2Unico;
    private Modelo_7_3_Acta_Global nuevoModelo7_3ActaGlobal;
    private Modelo_7_3_Proceso nuevoModelo7_3Proceso;
    private Registro registro;
    private TextField textFieldNumeroPreaviso;
    private DatePicker datePicker;
    private VentanaCandidato ventanaCandidato;

    /**
     * Constructor de la clase VentanaModelosEscrutinioB.
     *
     * @param vistaPrincipal La vista principal de la aplicación.
     * @param rutaEmpresa La ruta donde se guardan los datos de la empresa.
     */
    public VentanaModelosEscrutinio(@NotNull PrincipalView vistaPrincipal, @NotNull Path rutaEmpresa) throws IOException {
        this.vistaPrincipal = vistaPrincipal;
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
        VBox vboxNumeroPreaviso = crearVBoxNumeroPreaviso();
        Label labelFechaVotacion = new Label("FECHA DE VOTACIÓN");
        labelFechaVotacion.setStyle(Constantes.BOLD_UNDERLINED_STYLE);
        datePicker = getDatePicker();

        ventanaCandidato = new VentanaCandidato(vistaPrincipal, nuevoModelo5_1);
        VBox vboxCandidatos = ventanaCandidato.crearVBoxCandidatos();

        Label labelModeloConclusion = crearLabelPrincipal();
        GridPane gridPaneConclusion = crearGridPaneConclusion();

        TextField textFieldActividadEconomica = crearTextField(gridPaneConclusion, MessageManager.getMessage("conclusion.actividad_economica"), 0);
        TextField textFieldConvenio = crearTextField(gridPaneConclusion, MessageManager.getMessage("conclusion.convenio"), 1);
        TextField textFieldNumeroConvenio = crearTextField(gridPaneConclusion, MessageManager.getMessage("conclusion.numero_convenio"), 2);
        TextField textFieldTrabajadoresFijos = crearTextField(gridPaneConclusion, MessageManager.getMessage("conclusion.trabajadores_fijos"), 3);

        // Agregar listener para mostrar alerta informativa al cambiar el focus
        textFieldTrabajadoresFijos.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                mostrarAlertaInformativaTrabajadores(textFieldTrabajadoresFijos.getText());
            }
        });

        Button btnGuardar = crearBotonGuardar(stage, textFieldActividadEconomica, textFieldConvenio, textFieldNumeroConvenio, textFieldTrabajadoresFijos);

        vbox.getChildren().addAll(vboxNumeroPreaviso, labelFechaVotacion, datePicker, vboxCandidatos, labelModeloConclusion, gridPaneConclusion, crearGuardarBox(btnGuardar));

        Scene scene = new Scene(vbox, 600, 800);
        stage.setScene(scene);
        stage.show();
    }

    @NotNull
    private VBox crearVBoxGeneral() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    @NotNull
    private VBox crearVBoxNumeroPreaviso() {
        Label labelNumeroPreaviso = new Label(MessageManager.getMessage("modelo5_1.preaviso"));
        labelNumeroPreaviso.setStyle(Constantes.BOLD_UNDERLINED_STYLE);
        textFieldNumeroPreaviso = new TextField();
        textFieldNumeroPreaviso.setMaxWidth(100);

        VBox vboxNumeroPreaviso = new VBox(5);
        vboxNumeroPreaviso.setAlignment(Pos.CENTER);
        vboxNumeroPreaviso.getChildren().addAll(labelNumeroPreaviso, textFieldNumeroPreaviso);
        return vboxNumeroPreaviso;
    }

    @NotNull
    private Button crearBotonGuardar(@NotNull Stage stage, TextField textFieldActividadEconomica, TextField textFieldConvenio, TextField textFieldNumeroConvenio, TextField textFieldTrabajadoresFijos) {
        Button btnGuardar = new Button(MessageManager.getMessage("modelo5_1.guardar"));
        btnGuardar.setOnAction(_ -> guardarInformacion(stage, textFieldActividadEconomica, textFieldConvenio, textFieldNumeroConvenio, textFieldTrabajadoresFijos));
        return btnGuardar;
    }

    @NotNull
    private HBox crearGuardarBox(@NotNull Button btnGuardar) {
        HBox guardarBox = new HBox(btnGuardar);
        guardarBox.setAlignment(Pos.CENTER);
        guardarBox.setPadding(new Insets(10, 0, 0, 0));
        return guardarBox;
    }

    private void guardarInformacion(@NotNull Stage stage, TextField textFieldActividadEconomica, TextField textFieldConvenio, TextField textFieldNumeroConvenio, TextField textFieldTrabajadoresFijos) {
        LocalDate fechaActa = datePicker.getValue();
        if (fechaActa == null || fechaActa.isBefore(LocalDate.now())) {
            vistaPrincipal.mostrarMensaje(MessageManager.getMessage("modelo5_1.fecha_invalida"), false);
        } else {
            try {
                nuevoModelo5_2Proceso.setPreaviso(textFieldNumeroPreaviso.getText());
                nuevoModelo5_1.setFechaEscrutinio(fechaActa.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                nuevoModelo5_1.getCandidatos().sort(Comparator.comparing(Candidato::getApellido));

                nuevoModeloConclusion.setActvEcono(textFieldActividadEconomica.getText().toUpperCase());
                nuevoModeloConclusion.setNombreConvenio(textFieldConvenio.getText().toUpperCase());
                nuevoModeloConclusion.setNumeroConvenio(textFieldNumeroConvenio.getText());
                nuevoModeloConclusion.setTrabajadoresFijos(textFieldTrabajadoresFijos.getText());
                nuevoModelo5_2Proceso.setTotalElectores(Integer.parseInt(textFieldTrabajadoresFijos.getText()));

                DatabaseManager dbManager = new DatabaseManager(vistaPrincipal);
                EleccionesDAO eleccionesDAO = new EleccionesDAO(vistaPrincipal, dbManager);
                EmpresaDAO empresaDAO = new EmpresaDAO(vistaPrincipal, dbManager);
                String nombreEmpresaCompleto = rutaEmpresa.getFileName().toString();
                eleccionesDAO.updateEleccion(nuevoModelo5_1, nuevoModelo5_2Proceso, nombreEmpresaCompleto);
                empresaDAO.updateEmpresaOtros(nuevoModelo5_2Proceso, nuevoModeloConclusion, nombreEmpresaCompleto);
                GridPane mensajeConfirmacion = construirMensajeConfirmacion(textFieldActividadEconomica, textFieldConvenio, textFieldNumeroConvenio, textFieldTrabajadoresFijos);
                mostrarConfirmacion(stage, mensajeConfirmacion);
            } catch (CumplimentarPDFException | NumberFormatException | SQLException ex) {
                manejarExcepciones(ex);
            }
        }
    }

    private GridPane construirMensajeConfirmacion(TextField textFieldActividadEconomica, TextField textFieldConvenio, TextField textFieldNumeroConvenio, TextField textFieldTrabajadoresFijos) {
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

        // Agregar encabezado para candidatos
        Label candidatosHeader = new Label(MessageManager.getMessage("modelo5_1.candidatos"));
        gridPane.add(candidatosHeader, 0, rowIndex, 2, 1);
        GridPane.setMargin(candidatosHeader, new Insets(10, 0, 0, 0));
        rowIndex++;

        // Agregar información de candidatos
        for (Candidato candidato : nuevoModelo5_1.getCandidatos()) {
            agregarFila(gridPane, " - " + MessageManager.getMessage("modelo5_1.nombre"), candidato.getNombreApellidos(), rowIndex++);
            agregarFila(gridPane, " - " + MessageManager.getMessage("modelo5_1.dni"), candidato.getDni(), rowIndex++);
            agregarFila(gridPane, " - " + MessageManager.getMessage("modelo5_1.sindicato"), candidato.getSindicato(), rowIndex++);
        }

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
                if (resultColleges.get() == buttonTypeSi) {
                    registro.registrarModelosEscrutinioComite(nuevoModelo6_1Especialistas, nuevoModelo6_2Especialistas, nuevoModelo6_1Tecnicos, nuevoModelo6_2Tecnicos, nuevoModelo7_3ActaGlobal, nuevoModelo7_3Proceso, rutaEmpresa);
                } else if (resultColleges.get() == buttonTypeNo) {
                    registro.registrarModelosEscrutinioComiteUnico(nuevoModelo6_1Unico, nuevoModelo6_2Unico, nuevoModelo7_3ActaGlobal, nuevoModelo7_3Proceso,rutaEmpresa);
                }
            }
            // Mostrar un mensaje de éxito
            vistaPrincipal.mostrarMensaje(MessageManager.getMessage("conclusion.datos_guardados"), true);
            // Cerrar la ventana actual
            stage.close();
        }
    }

    private void manejarExcepciones(@NotNull Exception ex) {
        if (ex instanceof CumplimentarPDFException || ex instanceof NumberFormatException) {
            vistaPrincipal.mostrarMensaje(String.format(MessageManager.getMessage("modelo5_1.error_guardar"), ex.getMessage()), false);
        } else if (ex instanceof SQLException) {
            vistaPrincipal.mostrarMensaje(String.format(MessageManager.getMessage("modelo5_1.error_db"), ex.getMessage()), false);
        }
    }

    @NotNull
    private DatePicker getDatePicker() {
        DatePicker datePicker = getPicker();
        datePicker.focusedProperty().addListener((_, _, newValue) -> {
            if (!newValue) {
                try {
                    datePicker.getConverter().fromString(datePicker.getEditor().getText());
                } catch (DateTimeParseException e) {
                    devolverFocoDatePicker(datePicker);
                }
            }
        });
        return datePicker;
    }

    private void devolverFocoDatePicker(@NotNull DatePicker datePicker) {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.1));
        pause.setOnFinished(_ -> datePicker.requestFocus());
        pause.play();
    }

    @NotNull
    private DatePicker getPicker() {
        DatePicker datePicker = new DatePicker();
        datePicker.setPrefWidth(125);
        datePicker.setConverter(new StringConverter<>() {
            private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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

    private Label crearLabelPrincipal() {
        Label label = new Label(MessageManager.getMessage("conclusion.informacion"));
        label.setStyle(Constantes.BOLD_UNDERLINED_STYLE);
        return label;
    }

    private GridPane crearGridPaneConclusion() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }

    private TextField crearTextField(GridPane gridPane, String labelText, int rowIndex) {
        Label label = new Label(labelText);
        TextField textField = new TextField();
        gridPane.add(label, 0, rowIndex);
        gridPane.add(textField, 1, rowIndex);
        return textField;
    }

    private void mostrarAlertaInformativaTrabajadores(String trabajadores) {
        if (!trabajadores.isEmpty() && Integer.parseInt(trabajadores) >= 50) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("Revise el número de trabajadores, ya que la documentación a generar depende de si hay más de 50 trabajadores o no.");
            alert.showAndWait();
        }
    }

    private void mostrarMensajeError(@NotNull String claveMensaje, @NotNull IOException e) {
        vistaPrincipal.mostrarMensaje(String.format(MessageManager.getMessage(claveMensaje), e.getMessage()), false);
    }
}
