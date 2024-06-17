package com.example.proyecto.interfaz;

import com.example.proyecto.modal.*;
import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.MessageManager;
import com.example.proyecto.util.ValidadorCampos;
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

import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * La clase `VentanaModelo5_1` gestiona la ventana que solicita la fecha del acta de escrutinio y los datos de los candidatos.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class VentanaModelo5_1 {

    private final PrincipalView vistaPrincipal;
    private final Modelo_5_1 nuevoModelo5_1;
    private final Modelo_5_2_Proceso nuevoModelo5_2Proceso;
    private final Modelo_5_2_Conclusion nuevoModeloConclusion;
    private final Path rutaEmpresa;
    private TextField textFieldNumeroPreaviso;
    private TableView<Candidato> tableView;

    /**
     * Constructor para la clase `VentanaModelo5_1`.
     *
     * @param vistaPrincipal La vista principal de la aplicación.
     * @param rutaEmpresa La ruta de la empresa.
     * @param nuevoModelo5_1 El modelo 5.1 que se va a utilizar para almacenar los datos ingresados.
     * @param nuevoModelo5_2Proceso El modelo 5.2 Proceso que se va a utilizar para almacenar los datos ingresados.
     * @param nuevoModeloConclusion El modelo 5.2 Conclusión que se va a utilizar para almacenar los datos ingresados.
     */
    public VentanaModelo5_1(@NotNull PrincipalView vistaPrincipal, @NotNull Path rutaEmpresa, @NotNull Modelo_5_1 nuevoModelo5_1,
                            @NotNull Modelo_5_2_Proceso nuevoModelo5_2Proceso, @NotNull Modelo_5_2_Conclusion nuevoModeloConclusion) {
        this.vistaPrincipal = vistaPrincipal;
        this.nuevoModelo5_1 = nuevoModelo5_1;
        this.nuevoModelo5_2Proceso = nuevoModelo5_2Proceso;
        this.nuevoModeloConclusion = nuevoModeloConclusion;
        this.rutaEmpresa = rutaEmpresa;
    }

    /**
     * Muestra la ventana para ingresar la fecha del acta de escrutinio y los candidatos.
     */
    public void configurarVentanaModelo5_1() {
        Stage stage = new Stage();
        stage.setTitle(MessageManager.getMessage("modelo5_1.title"));

        VBox vbox = crearVBox();
        VBox vboxNumeroPreaviso = crearVBoxNumeroPreaviso();
        Label labelFechaVotacion = new Label("FECHA DE VOTACIÓN");
        labelFechaVotacion.setStyle(Constantes.BOLD_UNDERLINED_STYLE);
        DatePicker datePicker = getDatePicker();
        Button btnAgregarCandidato = crearBotonAgregarCandidato();
        Label labelCandidatosHeader = crearLabelCandidatosHeader();
        tableView = crearTableViewCandidatos();
        Button btnGuardar = crearBotonGuardar(stage, datePicker);

        vbox.getChildren().addAll(vboxNumeroPreaviso, labelFechaVotacion, datePicker, btnAgregarCandidato, labelCandidatosHeader, tableView, crearGuardarBox(btnGuardar));

        Scene scene = new Scene(vbox, 500, 400);
        stage.setScene(scene);
        stage.show();
    }

    @NotNull
    private VBox crearVBox() {
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
    private Label crearLabelCandidatosHeader() {
        Label labelCandidatosHeader = new Label(MessageManager.getMessage("modelo5_1.candidatos"));
        labelCandidatosHeader.setStyle(Constantes.BOLD_UNDERLINED_STYLE);
        labelCandidatosHeader.setAlignment(Pos.CENTER);
        return labelCandidatosHeader;
    }

    @NotNull
    private Button crearBotonAgregarCandidato() {
        Button btnAgregarCandidato = new Button(MessageManager.getMessage("modelo5_1.agregar_candidato"));
        btnAgregarCandidato.setOnAction(_ -> mostrarDialogoAgregarCandidato());
        return btnAgregarCandidato;
    }

    @NotNull
    private TableView<Candidato> crearTableViewCandidatos() {
        TableView<Candidato> tableView = new TableView<>();
        TableColumn<Candidato, String> colNombre = new TableColumn<>(MessageManager.getMessage("modelo5_1.nombre"));
        TableColumn<Candidato, String> colDNI = new TableColumn<>(MessageManager.getMessage("modelo5_1.dni"));
        TableColumn<Candidato, String> colSindicato = new TableColumn<>(MessageManager.getMessage("modelo5_1.sindicato"));

        colNombre.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombreApellidos()));
        colDNI.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDni()));
        colSindicato.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getSindicato()));

        ajustarColumnas(tableView, colNombre, colDNI, colSindicato);
        return tableView;
    }

    private void ajustarColumnas(@NotNull TableView<Candidato> tableView, @NotNull TableColumn<Candidato, String> colNombre,
                                 @NotNull TableColumn<Candidato, String> colDNI, @NotNull TableColumn<Candidato, String> colSindicato) {
        colNombre.setMinWidth(280);
        colNombre.setStyle("-fx-alignment: CENTER-LEFT;");
        colDNI.setMinWidth(100);
        colDNI.setMaxWidth(100);
        colDNI.setStyle("-fx-alignment: CENTER;");
        colSindicato.setMinWidth(100);
        colSindicato.setMaxWidth(100);
        colSindicato.setStyle("-fx-alignment: CENTER;");
        Collections.addAll(tableView.getColumns(), colNombre, colDNI, colSindicato);
        tableView.setPrefHeight(200);
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    }

    @NotNull
    private Button crearBotonGuardar(@NotNull Stage stage, @NotNull DatePicker datePicker) {
        Button btnGuardar = new Button(MessageManager.getMessage("modelo5_1.guardar"));
        btnGuardar.setOnAction(_ -> guardarInformacion(stage, datePicker));
        return btnGuardar;
    }

    @NotNull
    private HBox crearGuardarBox(@NotNull Button btnGuardar) {
        HBox guardarBox = new HBox(btnGuardar);
        guardarBox.setAlignment(Pos.CENTER);
        guardarBox.setPadding(new Insets(10, 0, 0, 0));
        return guardarBox;
    }

    private void mostrarDialogoAgregarCandidato() {
        Dialog<Candidato> dialog = new Dialog<>();
        dialog.setTitle(MessageManager.getMessage("modelo5_1.agregar_candidato"));

        GridPane dialogPane = crearDialogPane();
        dialog.getDialogPane().setContent(dialogPane);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Platform.runLater(() -> dialogPane.getChildren().get(1).requestFocus());

        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> validarDNI(dialogPane, event));

        dialog.setResultConverter(dialogButton -> {
            try {
                return obtenerResultadoDialogo(dialogButton, dialogPane);
            } catch (CumplimentarPDFException e) {
                throw new RuntimeException(e);
            }
        });
        Optional<Candidato> result = dialog.showAndWait();
        result.ifPresent(this::agregarCandidato);
    }

    @NotNull
    private GridPane crearDialogPane() {
        GridPane dialogPane = new GridPane();
        dialogPane.setHgap(10);
        dialogPane.setVgap(10);
        dialogPane.setPadding(new Insets(10));

        dialogPane.add(new Label(MessageManager.getMessage("modelo5_1.nombre")), 0, 0);
        dialogPane.add(new TextField(), 1, 0);
        dialogPane.add(new Label(MessageManager.getMessage("modelo5_1.dni")), 0, 1);
        dialogPane.add(new TextField(), 1, 1);
        dialogPane.add(new Label(MessageManager.getMessage("modelo5_1.sindicato")), 0, 2);
        dialogPane.add(new TextField(), 1, 2);
        return dialogPane;
    }

    private void validarDNI(@NotNull GridPane dialogPane, @NotNull javafx.event.ActionEvent event) {
        TextField dniField = (TextField) dialogPane.getChildren().get(3);
        if (!ValidadorCampos.verificarDNI(dniField.getText())) {
            vistaPrincipal.mostrarMensaje(MessageManager.getMessage("modelo5_1.dni_invalido"), false);
            event.consume();
            dniField.requestFocus();
        }
    }

    private Candidato obtenerResultadoDialogo(ButtonType dialogButton, @NotNull GridPane dialogPane) throws CumplimentarPDFException {
        if (dialogButton == ButtonType.OK) {
            TextField nombreApellidos = (TextField) dialogPane.getChildren().get(1);
            TextField dni = (TextField) dialogPane.getChildren().get(3);
            TextField sindicato = (TextField) dialogPane.getChildren().get(5);
            return new Candidato(nombreApellidos.getText().toUpperCase(), dni.getText().toUpperCase(), sindicato.getText().toUpperCase());
        }
        return null;
    }

    private void agregarCandidato(@NotNull Candidato candidato) {
        nuevoModelo5_1.getCandidatos().add(candidato);
        actualizarTablaCandidatos();
    }

    private void actualizarTablaCandidatos() {
        tableView.getItems().setAll(nuevoModelo5_1.getCandidatos());
    }

    private void guardarInformacion(@NotNull Stage stage, @NotNull DatePicker datePicker) {
        LocalDate fechaActa = datePicker.getValue();
        if (fechaActa == null || fechaActa.isBefore(LocalDate.now())) {
            vistaPrincipal.mostrarMensaje(MessageManager.getMessage("modelo5_1.fecha_invalida"), false);
        } else {
            try {
                String nombreEmpresaCompleto = rutaEmpresa.getFileName().toString();
                DatabaseManager dbManager = new DatabaseManager(vistaPrincipal);
                nuevoModelo5_2Proceso.setPreaviso(textFieldNumeroPreaviso.getText());
                nuevoModelo5_1.setFechaEscrutinio(datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                nuevoModelo5_1.getCandidatos().sort(Comparator.comparing(Candidato::getApellido));
                guardarCandidatosEnBD(dbManager, nombreEmpresaCompleto);
                new VentanaModelosEscrutinio(vistaPrincipal, rutaEmpresa, nuevoModelo5_1, nuevoModelo5_2Proceso, nuevoModeloConclusion).mostrarVentanaModeloConclusion();
                stage.close();
            } catch (CumplimentarPDFException | NumberFormatException | SQLException ex) {
                manejarExcepciones(ex);
            }
        }
    }

    private void guardarCandidatosEnBD(@NotNull DatabaseManager dbManager, @NotNull String nombreEmpresaCompleto) throws SQLException {
        for (Candidato candidato : nuevoModelo5_1.getCandidatos()) {
            CandidatosDAO candidatosDAO = new CandidatosDAO(vistaPrincipal, dbManager);
            candidatosDAO.insertCandidato(candidato, nombreEmpresaCompleto);
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
}
