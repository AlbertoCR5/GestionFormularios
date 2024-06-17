package com.example.proyecto.interfaz;

import com.example.proyecto.modal.Preaviso;
import com.example.proyecto.util.ConversorFechaToLetras;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase `CalendarioComite` gestiona la visualización y el manejo de la ventana del calendario del comité.
 * Permite al usuario ingresar información específica del calendario y guardar los datos.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class VentanaCalendarioComite {

    private final Preaviso preaviso;
    private final List<TextField> textFields = new ArrayList<>();

    /**
     * Constructor para la clase `CalendarioComiteView`.
     *
     * @param preaviso El preaviso asociado con el calendario del comité.
     */
    public VentanaCalendarioComite(Preaviso preaviso) {
        this.preaviso = preaviso;
    }

    /**
     * Muestra la ventana del calendario del comité.
     */
    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Calendario del Comité");

        // Configuración del VBox principal
        VBox vboxPrincipal = new VBox(10);
        vboxPrincipal.setAlignment(Pos.TOP_CENTER);
        vboxPrincipal.setPadding(new Insets(10));

        Label titulo = new Label("CALENDARIO_COMITE");
        titulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        vboxPrincipal.getChildren().add(titulo);

        // Configuración del GridPane
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        int row = 0;

        // Campos del formulario
        Label labelEmpresa = new Label("Empresa:");
        labelEmpresa.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        Label textFieldEmpresa = new Label(preaviso.getNombreEmpresa());
        textFieldEmpresa.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        gridPane.add(labelEmpresa, 0, row);
        gridPane.add(textFieldEmpresa, 1, row++, 2, 1);

        Label labelFechaConstitucion = new Label("Constitución Mesa Electoral");
        labelFechaConstitucion.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        Label textFieldFechaConstitucion = new Label(ConversorFechaToLetras.convertirFechaGuiones(preaviso.getFechaConstitucion()));
        textFieldFechaConstitucion.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        gridPane.add(labelFechaConstitucion, 0, row);
        gridPane.add(textFieldFechaConstitucion, 2, row++, 2, 1);

        // Agregar campos divididos en columnas
        agregarCampo(gridPane, "Exposición del Censo Electoral:", "(3 días naturales)", row++);
        agregarCampo(gridPane, "Reclamaciones al Censo Electoral:", "(1 día natural)", row++);
        agregarCampo(gridPane, "Resolución de Reclamaciones:", "(1 día natural)", row++);
        agregarCampo(gridPane, "Exposición del Censo Definitivo:", "(1 día natural)", row++);
        agregarCampo(gridPane, "Presentación de Candidaturas:", "(9 días naturales)", row++);
        agregarCampo(gridPane, "Exposición de Candidaturas presentadas:", "(2 días laborales)", row++);
        agregarCampo(gridPane, "Reclamación a las Candidaturas presentadas:", "(1 día laboral)", row++);
        agregarCampo(gridPane, "Resolución y Proclamación definitiva de Candidaturas:", "(1 día hábil)", row++);
        agregarCampo(gridPane, "Propaganda Electoral:", "(4 días naturales)", row++);
        agregarCampo(gridPane, "Jornada de Reflexión:", "(1 día natural)", row++);
        agregarCampo(gridPane, "Día de la Votación:", "(1 día laboral)", row++);

        // Campo para horario de votación
        Label labelHorario = new Label("Horario de Votación:");
        labelHorario.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        gridPane.add(labelHorario, 0, row);
        HBox hBoxHorarioVotacion = crearCamposHora();
        gridPane.add(hBoxHorarioVotacion, 2, row++);

        // Botones de Guardar, Cancelar y Limpiar
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(_ -> {
            // Lógica para guardar los datos del calendario
            stage.close();
        });

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(_ -> stage.close());

        Button btnLimpiar = new Button("Limpiar");
        btnLimpiar.setOnAction(_ -> limpiarCampos());

        hBox.getChildren().addAll(btnGuardar, btnCancelar, btnLimpiar);
        gridPane.add(hBox, 0, ++row, 3, 1);

        vboxPrincipal.getChildren().add(gridPane);

        // Configuración de la escena y muestra de la ventana
        Scene scene = new Scene(vboxPrincipal, 1200, 650);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método auxiliar para agregar un campo al GridPane.
     *
     * @param gridPane     El GridPane al que se agregará el campo.
     * @param label1       La primera parte de la etiqueta del campo.
     * @param label2       La segunda parte de la etiqueta del campo.
     * @param row          La fila en la que se agregará el campo.
     */
    private void agregarCampo(GridPane gridPane, String label1, String label2, int row) {
        agregarCampo(gridPane, label1, label2, "", row);
    }

    /**
     * Método sobrecargado para agregar un campo al GridPane con un valor inicial.
     *
     * @param gridPane     El GridPane al que se agregará el campo.
     * @param label1       La primera parte de la etiqueta del campo.
     * @param label2       La segunda parte de la etiqueta del campo.
     * @param valorInicial El valor inicial del campo.
     * @param row          La fila en la que se agregará el campo.
     */
    private void agregarCampo(GridPane gridPane, String label1, String label2, String valorInicial, int row) {
        Label lbl1 = new Label(label1);
        lbl1.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        Label lbl2 = new Label(label2);
        lbl2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        HBox hbox = crearCamposFecha(valorInicial);
        gridPane.add(lbl1, 0, row);
        gridPane.add(lbl2, 1, row);
        gridPane.add(hbox, 2, row);
    }

    /**
     * Método auxiliar para crear un HBox con los campos de fecha.
     *
     * @param valorInicial El valor inicial del campo de fecha.
     * @return Un HBox con los campos de fecha.
     */
    private HBox crearCamposFecha(String valorInicial) {
        HBox hbox = new HBox(5);
        TextField textFieldDiaInicio = new TextField();
        textFieldDiaInicio.setPrefWidth(35);
        textFieldDiaInicio.setStyle("-fx-font-size: 14px;");
        TextField textFieldMesInicio = new TextField();
        textFieldMesInicio.setPrefWidth(100);
        textFieldMesInicio.setStyle("-fx-font-size: 14px;");
        TextField textFieldAnioInicio = new TextField();
        textFieldAnioInicio.setPrefWidth(35);
        textFieldAnioInicio.setStyle("-fx-font-size: 14px;");

        TextField textFieldDiaFin = new TextField();
        textFieldDiaFin.setPrefWidth(35);
        textFieldDiaFin.setStyle("-fx-font-size: 14px;");
        TextField textFieldMesFin = new TextField();
        textFieldMesFin.setPrefWidth(100);
        textFieldMesFin.setStyle("-fx-font-size: 14px;");
        TextField textFieldAnioFin = new TextField();
        textFieldAnioFin.setPrefWidth(35);
        textFieldAnioFin.setStyle("-fx-font-size: 14px;");

        textFields.add(textFieldDiaInicio);
        textFields.add(textFieldMesInicio);
        textFields.add(textFieldAnioInicio);
        textFields.add(textFieldDiaFin);
        textFields.add(textFieldMesFin);
        textFields.add(textFieldAnioFin);

        hbox.getChildren().addAll(
                new Label("Desde el"), textFieldDiaInicio, new Label("de"), textFieldMesInicio, new Label("de 20"), textFieldAnioInicio,
                new Label("hasta el"), textFieldDiaFin, new Label("de"), textFieldMesFin, new Label("de 20"), textFieldAnioFin
        );
        return hbox;
    }

    /**
     * Método auxiliar para crear un HBox con los campos de hora.
     *
     * @return Un HBox con los campos de hora.
     */
    private HBox crearCamposHora() {
        HBox hbox = new HBox(5);
        TextField textFieldHoraInicio = new TextField();
        textFieldHoraInicio.setPrefWidth(55);
        textFieldHoraInicio.setStyle("-fx-font-size: 14px;");
        TextField textFieldHoraFin = new TextField();
        textFieldHoraFin.setPrefWidth(55);
        textFieldHoraFin.setStyle("-fx-font-size: 14px;");

        textFields.add(textFieldHoraInicio);
        textFields.add(textFieldHoraFin);

        hbox.getChildren().addAll(
                new Label("De"), textFieldHoraInicio, new Label("a"), textFieldHoraFin, new Label("horas.")
        );
        return hbox;
    }

    /**
     * Método auxiliar para limpiar todos los campos de texto.
     */
    private void limpiarCampos() {
        for (TextField textField : textFields) {
            textField.setText("");
        }
    }
}
