package com.example.proyecto.interfaz;

import com.example.proyecto.modal.*;
import com.example.proyecto.util.CumplimentarPDFException;
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
 */
public class VentanaModelo5_1 {

    private final PrincipalView vistaPrincipal;
    private final ValidadorCampos validadorCampos;
    private final Modelo_5_1 nuevoModelo5_1;
    private final Modelo_5_2_Proceso nuevoModelo5_2Proceso;
    private final Modelo_5_2_Conclusion nuevoModeloConclusion;
    private final Path rutaEmpresa;
    private final ResourceBundle bundle;

    /**
     * Constructor para la clase `VentanaModelo5_1`.
     *
     * @param vistaPrincipal La vista principal de la aplicación.
     * @param rutaEmpresa La ruta de la empresa.
     * @param nuevoModelo5_1 El modelo 5.1 que se va a utilizar para almacenar los datos ingresados.
     * @param nuevoModelo5_2Proceso El modelo 5.2 Proceso que se va a utilizar para almacenar los datos ingresados.
     * @param nuevoModeloConclusion El modelo 5.2 Conclusión que se va a utilizar para almacenar los datos ingresados.
     */
    public VentanaModelo5_1(PrincipalView vistaPrincipal, Path rutaEmpresa, Modelo_5_1 nuevoModelo5_1, Modelo_5_2_Proceso nuevoModelo5_2Proceso, Modelo_5_2_Conclusion nuevoModeloConclusion) {
        this.vistaPrincipal = vistaPrincipal;
        this.nuevoModeloConclusion = nuevoModeloConclusion;
        this.validadorCampos = new ValidadorCampos();
        this.nuevoModelo5_1 = nuevoModelo5_1;
        this.nuevoModelo5_2Proceso = nuevoModelo5_2Proceso;
        this.rutaEmpresa = rutaEmpresa;
        this.bundle = ResourceBundle.getBundle("messages", vistaPrincipal.getBundle().getLocale());
    }

    /**
     * Muestra la ventana para ingresar la fecha del acta de escrutinio y los candidatos.
     */
    public void configurarVentanaModelo5_1() {
        Stage stage = new Stage();
        stage.setTitle(bundle.getString("modelo5_1.title"));

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        // Etiqueta para el número de preaviso
        Label labelNumeroPreaviso = new Label(bundle.getString("modelo5_1.preaviso"));
        labelNumeroPreaviso.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-underline: true;");
        TextField textFieldNumeroPreaviso = new TextField();
        textFieldNumeroPreaviso.setMaxWidth(100);

        // Crear un VBox para el número de preaviso y el campo de texto
        VBox vboxNumeroPreaviso = new VBox(5);
        vboxNumeroPreaviso.setAlignment(Pos.CENTER);
        vboxNumeroPreaviso.getChildren().addAll(labelNumeroPreaviso, textFieldNumeroPreaviso);

        // Etiqueta para la fecha del acta de escrutinio
        Label labelFechaEscrutinio = new Label(bundle.getString("modelo5_1.fecha_escrutinio"));
        labelFechaEscrutinio.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-underline: true;");
        labelFechaEscrutinio.setAlignment(Pos.CENTER);

        // DatePicker para seleccionar la fecha del acta de escrutinio
        DatePicker datePicker = getDatePicker();

        vbox.getChildren().addAll(vboxNumeroPreaviso, labelFechaEscrutinio, datePicker);

        // Etiqueta para los candidatos
        Label labelCandidatosHeader = new Label(bundle.getString("modelo5_1.candidatos"));
        labelCandidatosHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-underline: true;");
        labelCandidatosHeader.setAlignment(Pos.CENTER);

        // Botón para agregar candidatos
        Button btnAgregarCandidato = new Button(bundle.getString("modelo5_1.agregar_candidato"));

        vbox.getChildren().addAll(labelCandidatosHeader, btnAgregarCandidato);

        // Tabla para mostrar los candidatos
        TableView<Candidato> tableView = new TableView<>();
        TableColumn<Candidato, String> colNombre = new TableColumn<>(bundle.getString("modelo5_1.nombre"));
        TableColumn<Candidato, String> colDNI = new TableColumn<>(bundle.getString("modelo5_1.dni"));
        TableColumn<Candidato, String> colSindicato = new TableColumn<>(bundle.getString("modelo5_1.sindicato"));

        colNombre.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombreApellidos()));
        colDNI.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDni()));
        colSindicato.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getSindicato()));

        // Ajustar anchos de columnas
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

        vbox.getChildren().add(tableView);

        // Botón para guardar la información
        Button btnGuardar = new Button(bundle.getString("modelo5_1.guardar"));
        HBox guardarBox = new HBox(btnGuardar);
        guardarBox.setAlignment(Pos.CENTER);
        guardarBox.setPadding(new Insets(10, 0, 0, 0));

        vbox.getChildren().add(guardarBox);

        ArrayList<Candidato> candidatos = new ArrayList<>();

        btnAgregarCandidato.setOnAction(_ -> {
            Dialog<Candidato> dialog = new Dialog<>();
            dialog.setTitle(bundle.getString("modelo5_1.agregar_candidato"));

            GridPane dialogPane = new GridPane();
            dialogPane.setHgap(10);
            dialogPane.setVgap(10);
            dialogPane.setPadding(new Insets(10));

            TextField nombreApellidos = new TextField();
            TextField dni = new TextField();
            TextField sindicato = new TextField();

            dialogPane.add(new Label(bundle.getString("modelo5_1.nombre")), 0, 0);
            dialogPane.add(nombreApellidos, 1, 0);
            dialogPane.add(new Label(bundle.getString("modelo5_1.dni")), 0, 1);
            dialogPane.add(dni, 1, 1);
            dialogPane.add(new Label(bundle.getString("modelo5_1.sindicato")), 0, 2);
            dialogPane.add(sindicato, 1, 2);

            dialog.getDialogPane().setContent(dialogPane);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            Platform.runLater(nombreApellidos::requestFocus);

            Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
            okButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
                if (!validadorCampos.verificarDNI(dni.getText())) {
                    vistaPrincipal.mostrarMensaje(bundle.getString("modelo5_1.dni_invalido"), false);
                    event.consume(); // Consume el evento para evitar cerrar el diálogo
                    dni.requestFocus(); // Devuelve el foco al campo de texto
                }
            });

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    try {
                        return new Candidato(nombreApellidos.getText().toUpperCase(), dni.getText().toUpperCase(), sindicato.getText().toUpperCase());
                    } catch (CumplimentarPDFException ex) {
                        vistaPrincipal.mostrarMensaje(ex.getMessage(), false);
                    }
                }
                return null;
            });

            Optional<Candidato> result = dialog.showAndWait();
            result.ifPresent(candidatos::add);
            tableView.getItems().setAll(candidatos);
        });

        btnGuardar.setOnAction(_ -> {
            LocalDate fechaActa = datePicker.getValue();
            if (fechaActa == null || fechaActa.isBefore(LocalDate.now())) {
                vistaPrincipal.mostrarMensaje(bundle.getString("modelo5_1.fecha_invalida"), false);
            } else {
                try {
                    String nombreEmpresaCompleto = rutaEmpresa.getFileName().toString();
                    DatabaseManager dbManager = new DatabaseManager(vistaPrincipal);
                    nuevoModelo5_2Proceso.setPreaviso(textFieldNumeroPreaviso.getText());
                    nuevoModelo5_1.setFechaEscrutinio(datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    candidatos.sort(Comparator.comparing(Candidato::getApellido));
                    nuevoModelo5_1.setCandidatos(candidatos);
                    for (Candidato candidato : nuevoModelo5_1.getCandidatos()) {
                        CandidatosDAO candidatosDAO = new CandidatosDAO(vistaPrincipal, dbManager);
                        candidatosDAO.insertCandidato(candidato, nombreEmpresaCompleto);
                    }
                    // Aquí guarda la información y continua con la siguiente ventana
                    new VentanaModelosEscrutinio(vistaPrincipal, rutaEmpresa, nuevoModelo5_1, nuevoModelo5_2Proceso, nuevoModeloConclusion).mostrarVentanaModeloConclusion();
                    stage.close();
                } catch (CumplimentarPDFException | NumberFormatException ex) {
                    vistaPrincipal.mostrarMensaje(String.format(bundle.getString("modelo5_1.error_guardar"), ex.getMessage()), false);
                } catch (SQLException e) {
                    vistaPrincipal.mostrarMensaje(String.format(bundle.getString("modelo5_1.error_db"), e.getMessage()), false);
                }
            }
        });

        Scene scene = new Scene(vbox, 500, 400);
        stage.setScene(scene);
        stage.show();
    }

    @NotNull
    private DatePicker getDatePicker() {
        DatePicker datePicker = getPicker();

        // Añadir un listener para devolver el foco al DatePicker si la fecha es inválida
        datePicker.focusedProperty().addListener((_, _, newValue) -> {
            if (!newValue) { // Cuando pierde el foco
                try {
                    datePicker.getConverter().fromString(datePicker.getEditor().getText());
                } catch (DateTimeParseException e) {
                    PauseTransition pause = new PauseTransition(Duration.seconds(0.1));
                    pause.setOnFinished(_ -> datePicker.requestFocus()); // Devuelve el foco al DatePicker;
                    pause.play();

                }
            }
        });
        return datePicker;
    }

    private @NotNull DatePicker getPicker() {
        DatePicker datePicker = new DatePicker();
        datePicker.setPrefWidth(125);

        // Configurar un StringConverter personalizado para manejar el formato y los errores de fecha
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
                    vistaPrincipal.mostrarMensaje(bundle.getString("modelo5_1.fecha_invalida"), false);
                    return null;
                }
            }
        });
        return datePicker;
    }
}
