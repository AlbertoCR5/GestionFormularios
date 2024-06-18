package com.example.proyecto.interfaz;

import com.example.proyecto.modal.CalendarioComite;
import com.example.proyecto.modal.Preaviso;
import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.ConversorFechaToLetras;
import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.MessageManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * La clase `VentanaCalendarioComite` gestiona la visualización y el manejo de la ventana del calendario del comité.
 * Permite al usuario ingresar información específica del calendario y guardar los datos.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class VentanaCalendarioComite {

    private final Preaviso preaviso;
    private final List<TextField> textFields = new ArrayList<>();
    private final CalendarioComite calendarioComite;

    /**
     * Constructor para la clase `VentanaCalendarioComite`.
     *
     * @param preaviso El preaviso asociado con el calendario del comité.
     */
    public VentanaCalendarioComite(Preaviso preaviso) {
        this.preaviso = preaviso;
        this.calendarioComite = new CalendarioComite();
    }

    /**
     * Muestra la ventana del calendario del comité.
     */
    public void mostrarVentanaCalendarioComite() {
        Stage stage = new Stage();
        stage.setTitle(MessageManager.getMessage("calendario_comite.titulo"));

        VBox vboxPrincipal = crearVBoxPrincipal();
        GridPane gridPane = crearGridPane();
        agregarCamposFormulario(gridPane);
        agregarBotones(gridPane, stage);

        vboxPrincipal.getChildren().addAll(
                crearTitulo(MessageManager.getMessage("calendario_comite.titulo1")),
                crearTitulo(MessageManager.getMessage("calendario_comite.titulo2")),
                new Region(),
                gridPane
        );

        Scene scene = new Scene(vboxPrincipal, 1150, 700);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Crea un VBox con la configuración principal.
     *
     * @return VBox configurado.
     */
    private @NotNull VBox crearVBoxPrincipal() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(10));
        return vbox;
    }

    /**
     * Crea un GridPane con la configuración principal.
     *
     * @return GridPane configurado.
     */
    private @NotNull GridPane crearGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(5, 5, 5, 5));
        return gridPane;
    }

    /**
     * Agrega los campos del formulario al GridPane.
     *
     * @param gridPane El GridPane al que se agregarán los campos.
     */
    private void agregarCamposFormulario(GridPane gridPane) {
        int row = 0;

        agregarCampoEmpresa(gridPane, row++);
        agregarCampoFechaConstitucion(gridPane, row++);

        agregarCampo(gridPane, MessageManager.getMessage("calendario_comite.exposicion_censo"),
                MessageFormat.format(MessageManager.getMessage("calendario_comite.dias_naturales"), 3), row++);
        agregarCampo(gridPane, MessageManager.getMessage("calendario_comite.reclamaciones_censo"),
                MessageFormat.format(MessageManager.getMessage("calendario_comite.dia_natural"), 1), row++);
        agregarCampo(gridPane, MessageManager.getMessage("calendario_comite.resolucion_reclamaciones"),
                MessageFormat.format(MessageManager.getMessage("calendario_comite.dia_natural"), 1), row++);
        agregarCampo(gridPane, MessageManager.getMessage("calendario_comite.exposicion_censo_definitivo"),
                MessageFormat.format(MessageManager.getMessage("calendario_comite.dia_natural"), 1), row++);
        agregarCampo(gridPane, MessageManager.getMessage("calendario_comite.presentacion_candidaturas"),
                MessageFormat.format(MessageManager.getMessage("calendario_comite.dias_naturales"), 9), row++);
        agregarCampo(gridPane, MessageManager.getMessage("calendario_comite.exposicion_candidaturas"),
                MessageFormat.format(MessageManager.getMessage("calendario_comite.dias_laborales"), 2), row++);
        agregarCampo(gridPane, MessageManager.getMessage("calendario_comite.reclamacion_candidaturas"),
                MessageFormat.format(MessageManager.getMessage("calendario_comite.dia_laboral"), 1), row++);
        agregarCampo(gridPane, MessageManager.getMessage("calendario_comite.proclamacion_candidaturas"),
                MessageFormat.format(MessageManager.getMessage("calendario_comite.dia_habil"), 1), row++);
        agregarCampo(gridPane, MessageManager.getMessage("calendario_comite.propaganda_electoral"),
                MessageFormat.format(MessageManager.getMessage("calendario_comite.dias_naturales"), 4), row++);
        agregarCampo(gridPane, MessageManager.getMessage("calendario_comite.jornada_reflexion"),
                MessageFormat.format(MessageManager.getMessage("calendario_comite.dia_natural"), 1), row++);
        agregarCampo(gridPane, MessageManager.getMessage("calendario_comite.dia_votacion"),
                MessageFormat.format(MessageManager.getMessage("calendario_comite.dia_laboral"), 1), row++);

        agregarCampoHorarioVotacion(gridPane, row);
    }

    /**
     * Agrega los botones de guardar, cancelar y limpiar al GridPane.
     *
     * @param gridPane El GridPane al que se agregarán los botones.
     * @param stage    La ventana actual.
     */
    private void agregarBotones(@NotNull GridPane gridPane, Stage stage) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        Button btnGuardar = new Button(MessageManager.getMessage("boton.guardar"));
        btnGuardar.setOnAction(_ -> {
            try {
                guardarDatosCalendario();
            } catch (CumplimentarPDFException e) {
                throw new RuntimeException(e);
            }
            stage.close();
        });

        Button btnCancelar = new Button(MessageManager.getMessage("boton.cancelar"));
        btnCancelar.setOnAction(_ -> stage.close());

        Button btnLimpiar = new Button(MessageManager.getMessage("boton.limpiar"));
        btnLimpiar.setOnAction(_ -> limpiarCampos());

        hBox.getChildren().addAll(btnGuardar, btnCancelar, btnLimpiar);
        gridPane.add(hBox, 0, 15, 3, 1);
    }

    /**
     * Método auxiliar para agregar un campo al GridPane.
     *
     * @param gridPane El GridPane al que se agregará el campo.
     * @param label1   La primera parte de la etiqueta del campo.
     * @param label2   La segunda parte de la etiqueta del campo.
     * @param row      La fila en la que se agregará el campo.
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
    private void agregarCampo(@NotNull GridPane gridPane, String label1, String label2, String valorInicial, int row) {
        Label lbl1 = new Label(label1);
        lbl1.setStyle(Constantes.FONT_SIZE_14_FONT_WEIGHT_BOLD);
        Label lbl2 = new Label(label2);
        lbl2.setStyle(Constantes.FONT_SIZE_14_FONT_WEIGHT_BOLD);
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
    private @NotNull HBox crearCamposFecha(String valorInicial) {
        HBox hbox = new HBox(5);
        hbox.setAlignment(Pos.CENTER_LEFT);
        TextField textFieldDiaInicio = new TextField();
        textFieldDiaInicio.setPrefWidth(35);
        textFieldDiaInicio.setStyle(Constantes.ESTILO_ETIQUETA_12PX);
        TextField textFieldMesInicio = new TextField();
        textFieldMesInicio.setPrefWidth(100);
        textFieldMesInicio.setStyle(Constantes.ESTILO_ETIQUETA_12PX);
        TextField textFieldAnioInicio = new TextField(LocalDate.now().getYear() % 100 + "");
        textFieldAnioInicio.setPrefWidth(35);
        textFieldAnioInicio.setStyle(Constantes.ESTILO_ETIQUETA_12PX);

        TextField textFieldDiaFin = new TextField();
        textFieldDiaFin.setPrefWidth(35);
        textFieldDiaFin.setStyle(Constantes.ESTILO_ETIQUETA_12PX);
        TextField textFieldMesFin = new TextField();
        textFieldMesFin.setPrefWidth(100);
        textFieldMesFin.setStyle(Constantes.ESTILO_ETIQUETA_14PX);
        TextField textFieldAnioFin = new TextField(LocalDate.now().getYear() % 100 + "");
        textFieldAnioFin.setPrefWidth(35);
        textFieldAnioFin.setStyle(Constantes.ESTILO_ETIQUETA_14PX);

        textFields.add(textFieldDiaInicio);
        textFields.add(textFieldMesInicio);
        textFields.add(textFieldAnioInicio);
        textFields.add(textFieldDiaFin);
        textFields.add(textFieldMesFin);
        textFields.add(textFieldAnioFin);

        hbox.getChildren().addAll(
                crearLabel(MessageManager.getMessage("calendario_comite.desde_el")), textFieldDiaInicio, crearLabel(MessageManager.getMessage("calendario_comite.de")), textFieldMesInicio, crearLabel(MessageManager.getMessage("calendario_comite.de_20")), textFieldAnioInicio,
                crearLabel(MessageManager.getMessage("calendario_comite.hasta_el")), textFieldDiaFin, crearLabel(MessageManager.getMessage("calendario_comite.de")), textFieldMesFin, crearLabel(MessageManager.getMessage("calendario_comite.de_20")), textFieldAnioFin
        );
        return hbox;
    }

    /**
     * Método auxiliar para crear un HBox con los campos de hora.
     *
     * @return Un HBox con los campos de hora.
     */
    private @NotNull HBox crearCamposHora() {
        HBox hbox = new HBox(5);
        hbox.setAlignment(Pos.CENTER_LEFT);
        TextField textFieldHoraInicio = new TextField();
        textFieldHoraInicio.setPrefWidth(55);
        textFieldHoraInicio.setStyle(Constantes.ESTILO_ETIQUETA_14PX);
        TextField textFieldHoraFin = new TextField();
        textFieldHoraFin.setPrefWidth(55);
        textFieldHoraFin.setStyle(Constantes.ESTILO_ETIQUETA_14PX);

        textFields.add(textFieldHoraInicio);
        textFields.add(textFieldHoraFin);

        hbox.getChildren().addAll(
                crearLabel(MessageManager.getMessage("calendario_comite.de")), textFieldHoraInicio, crearLabel(MessageManager.getMessage("calendario_comite.a")), textFieldHoraFin, crearLabel(MessageManager.getMessage("calendario_comite.horas"))
        );
        return hbox;
    }

    /**
     * Crea una etiqueta con un estilo específico.
     *
     * @param texto El texto de la etiqueta.
     * @return Una nueva instancia de Label con el texto especificado.
     */
    private @NotNull Label crearLabel(String texto) {
        Label label = new Label(texto);
        label.setStyle(Constantes.ESTILO_ETIQUETA_14PX);
        return label;
    }

    /**
     * Crea y configura un título para la ventana.
     *
     * @param texto El texto del título.
     * @return Una nueva instancia de Label configurada como título.
     */
    private @NotNull Label crearTitulo(String texto) {
        Label titulo = new Label(texto);
        titulo.setStyle(Constantes.BOLD_UNDERLINED_STYLE);
        return titulo;
    }

    /**
     * Agrega el campo de Empresa al GridPane.
     *
     * @param gridPane El GridPane al que se agregará el campo.
     * @param row      La fila en la que se agregará el campo.
     */
    private void agregarCampoEmpresa(@NotNull GridPane gridPane, int row) {
        Label labelEmpresa = new Label(MessageManager.getMessage("calendario_comite.empresa"));
        labelEmpresa.setStyle(Constantes.FONT_SIZE_14_FONT_WEIGHT_BOLD);
        Label textFieldEmpresa = new Label(preaviso.getNombreEmpresa());
        textFieldEmpresa.setStyle(Constantes.FONT_SIZE_14_FONT_WEIGHT_BOLD);
        gridPane.add(labelEmpresa, 0, row);
        gridPane.add(textFieldEmpresa, 1, row, 2, 1);
    }

    /**
     * Agrega el campo de Fecha de Constitución al GridPane.
     *
     * @param gridPane El GridPane al que se agregará el campo.
     * @param row      La fila en la que se agregará el campo.
     */
    private void agregarCampoFechaConstitucion(@NotNull GridPane gridPane, int row) {
        Label labelFechaConstitucion = new Label(MessageManager.getMessage("calendario_comite.constitucion_mesa"));
        labelFechaConstitucion.setStyle(Constantes.FONT_SIZE_14_FONT_WEIGHT_BOLD);
        Label textFieldFechaConstitucion = new Label(ConversorFechaToLetras.convertirFechaDe(preaviso.getFechaConstitucion()));
        textFieldFechaConstitucion.setStyle(Constantes.FONT_SIZE_14_FONT_WEIGHT_BOLD);
        TextField textFieldHoraConstitucion = new TextField();
        textFieldHoraConstitucion.setPrefWidth(50);
        textFieldHoraConstitucion.setStyle(Constantes.ESTILO_ETIQUETA_12PX);
        gridPane.add(labelFechaConstitucion, 0, row);
        HBox hBoxConstitucion = new HBox(5, textFieldFechaConstitucion, crearLabel(MessageManager.getMessage("calendario_comite.a_las")), textFieldHoraConstitucion, crearLabel(MessageManager.getMessage("calendario_comite.horas")));
        hBoxConstitucion.setAlignment(Pos.CENTER_LEFT);
        gridPane.add(hBoxConstitucion, 1, row, 2, 1);
    }

    /**
     * Agrega el campo de Horario de Votación al GridPane.
     *
     * @param gridPane El GridPane al que se agregará el campo.
     * @param row      La fila en la que se agregará el campo.
     */
    private void agregarCampoHorarioVotacion(@NotNull GridPane gridPane, int row) {
        Label labelHorario = new Label(MessageManager.getMessage("calendario_comite.horario_votacion"));
        labelHorario.setStyle(Constantes.FONT_SIZE_14_FONT_WEIGHT_BOLD);
        gridPane.add(labelHorario, 0, row);
        HBox hBoxHorarioVotacion = crearCamposHora();
        gridPane.add(hBoxHorarioVotacion, 2, row);
    }

    /**
     * Guarda los datos del formulario en la instancia de CalendarioComite.
     */
    private void guardarDatosCalendario() throws CumplimentarPDFException {
        calendarioComite.setHoraConstitucion(getHoraConstitucion());
        calendarioComite.setDiaInicioExposicionCenso(textFields.get(0).getText());
        calendarioComite.setMesInicioExposicionCenso(textFields.get(1).getText());
        calendarioComite.setAnioFinExposicionCenso(textFields.get(2).getText());
        calendarioComite.setDiaFinExposicionCenso(textFields.get(3).getText());
        calendarioComite.setMesFinExposicionCenso(textFields.get(4).getText());
        calendarioComite.setAnioFinExposicionCenso(textFields.get(5).getText());
        // Similar assignments for all other fields...
    }

    /**
     * Obtiene la hora de constitución a partir de los campos de texto.
     *
     * @return La hora de constitución.
     */
    private Date getHoraConstitucion() {
        // Implement your logic to parse the hour from textFields
        return null;
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
