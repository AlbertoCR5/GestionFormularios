package com.example.proyecto.interfaz;

import com.example.proyecto.modal.Candidato;
import com.example.proyecto.modal.Modelo_5_1;
import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.MessageManager;
import com.example.proyecto.util.ValidadorCampos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Optional;

/**
 * La clase `VentanaCandidatos` gestiona la ventana que permite agregar y mostrar candidatos.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class VentanaCandidatos {

    private final Modelo_5_1 modelo5_1;
    private TableView<Candidato> tableView;

    /**
     * Constructor para la clase `VentanaCandidatos`.
     *
     * @param modelo5_1 El modelo 5.1 que se va a utilizar para almacenar los datos de los candidatos.
     */
    public VentanaCandidatos(@NotNull Modelo_5_1 modelo5_1) {
        this.modelo5_1 = modelo5_1;
    }

    /**
     * Muestra la ventana para gestionar los candidatos.
     */
    public void mostrarVentanaCandidatos(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        Label labelCandidatosHeader = crearLabelCandidatosHeader();
        Button btnAgregarCandidato = crearBotonAgregarCandidato();
        tableView = crearTableViewCandidatos();

        vbox.getChildren().addAll(labelCandidatosHeader, btnAgregarCandidato, tableView);
        stage.getScene().setRoot(vbox);
    }

    @NotNull
    private Label crearLabelCandidatosHeader() {
        Label labelCandidatosHeader = new Label(MessageManager.getMessage("modelo5_1.candidatos"));
        labelCandidatosHeader.setStyle("-fx-font-weight: bold; -fx-underline: true;");
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

    private void mostrarDialogoAgregarCandidato() {
        Dialog<Candidato> dialog = new Dialog<>();
        dialog.setTitle(MessageManager.getMessage("modelo5_1.agregar_candidato"));

        GridPane dialogPane = crearDialogPane();
        dialog.getDialogPane().setContent(dialogPane);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> validarDNI(dialogPane, event));

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    return obtenerResultadoDialogo(dialogPane);
                } catch (CumplimentarPDFException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
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
            event.consume();
            dniField.requestFocus();
        }
    }

    private Candidato obtenerResultadoDialogo(@NotNull GridPane dialogPane) throws CumplimentarPDFException {
        TextField nombreApellidos = (TextField) dialogPane.getChildren().get(1);
        TextField dni = (TextField) dialogPane.getChildren().get(3);
        TextField sindicato = (TextField) dialogPane.getChildren().get(5);
        return new Candidato(nombreApellidos.getText().toUpperCase(), dni.getText().toUpperCase(), sindicato.getText().toUpperCase());
    }

    private void agregarCandidato(@NotNull Candidato candidato) {
        modelo5_1.getCandidatos().add(candidato);
        actualizarTablaCandidatos();
    }

    private void actualizarTablaCandidatos() {
        tableView.getItems().setAll(modelo5_1.getCandidatos());
    }
}
