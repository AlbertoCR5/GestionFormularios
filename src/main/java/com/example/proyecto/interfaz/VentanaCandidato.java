package com.example.proyecto.interfaz;

import com.example.proyecto.modal.Candidato;
import com.example.proyecto.modal.Modelo_5_1;
import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.MessageManager;
import com.example.proyecto.util.ValidadorCampos;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Optional;

/**
 * La clase `VentanaCandidatos` gestiona la ventana para agregar y mostrar candidatos.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class VentanaCandidato {

    private final PrincipalView vistaPrincipal;
    private final Modelo_5_1 nuevoModelo5_1;
    private TableView<Candidato> tableView;

    /**
     * Constructor de la clase VentanaCandidatos.
     *
     * @param nuevoModelo5_1 El modelo 5_1 que contiene la lista de candidatos.
     */
    public VentanaCandidato(PrincipalView vistaPrincipal, Modelo_5_1 nuevoModelo5_1) {
        this.vistaPrincipal = vistaPrincipal;
        this.nuevoModelo5_1 = nuevoModelo5_1;
    }

    /**
     * Crea y muestra la ventana para gestionar candidatos.
     *
     * @param stage El escenario principal de la aplicación.
     */
    public void mostrarVentanaCandidatos(@NotNull Stage stage) {
        VBox vbox = crearVBoxCandidatos();
        Scene scene = new Scene(vbox, 500, 400);
        stage.setScene(scene);
    }

    /**
     * Crea el VBox que contiene la tabla y los controles para gestionar candidatos.
     *
     * @return El VBox creado.
     */
    public VBox crearVBoxCandidatos() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        Label labelCandidatosHeader = crearLabelCandidatosHeader();
        tableView = crearTableViewCandidatos();
        Button btnAgregarCandidato = crearBotonAgregarCandidato();

        vbox.getChildren().addAll(labelCandidatosHeader, tableView, btnAgregarCandidato);
        return vbox;
    }

    @NotNull
    private Label crearLabelCandidatosHeader() {
        Label labelCandidatosHeader = new Label(MessageManager.getMessage("modelo5_1.candidatos"));
        labelCandidatosHeader.setStyle(Constantes.BOLD_UNDERLINED_STYLE);
        labelCandidatosHeader.setAlignment(Pos.CENTER);
        return labelCandidatosHeader;
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
        Collections.addAll(tableView.getColumns(),colNombre, colDNI, colSindicato);
        tableView.setPrefHeight(200);
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    }

    @NotNull
    private Button crearBotonAgregarCandidato() {
        Button btnAgregarCandidato = new Button(MessageManager.getMessage("modelo5_1.agregar_candidato"));
        btnAgregarCandidato.setOnAction(_ -> mostrarDialogoAgregarCandidato());
        return btnAgregarCandidato;
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
}
