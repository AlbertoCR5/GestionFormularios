package com.example.proyecto.interfaz;

import com.example.proyecto.modal.DatabaseManager;
import com.example.proyecto.modal.EleccionesDAO;
import com.example.proyecto.modal.EmpresaDAO;
import com.example.proyecto.modal.Preaviso;
import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.Registro;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * La clase `VentanaPreaviso` gestiona la interfaz de la ventana para los preavisos.
 * Utiliza JavaFX para la interfaz gráfica y permite registrar nuevos preavisos.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 */
public class VentanaPreaviso {

    private final ResourceBundle bundle;
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
    private TextField textFieldMesEleccion;
    private TextField textFieldPromotores;
    private TextField textFieldFechaInicio;
    private TextField textFieldDiaPreaviso;
    private TextField textFieldMesPreaviso;
    private TextField textFieldAnioPreaviso;

    /**
     * Constructor de la clase VentanaPreaviso.
     *
     * @param principalView La vista principal.
     * @param nuevoPreaviso El objeto Preaviso que se va a registrar.
     */
    public VentanaPreaviso(PrincipalView principalView, Preaviso nuevoPreaviso) {
        this.nuevaVentanaPreaviso = principalView;
        this.nuevoPreaviso = nuevoPreaviso;
        this.bundle = ResourceBundle.getBundle("messages", principalView.getBundle().getLocale());
    }

    /**
     * Muestra la ventana de preaviso.
     *
     * @throws IOException Si hay un error al mostrar la ventana.
     */
    public void mostrarVentanaPreaviso() throws IOException {
        Stage ventanaDatos = new Stage();
        ventanaDatos.initModality(Modality.APPLICATION_MODAL);
        ventanaDatos.setTitle(bundle.getString("preaviso.titulo"));

        Registro registro = new Registro(nuevoPreaviso, nuevaVentanaPreaviso);

        GridPane gridPane = crearGridPane();
        agregarCampos(gridPane);

        configurarValidadores();

        Button btnRegistrar = new Button(bundle.getString("preaviso.registrar"));
        btnRegistrar.setOnAction(_ -> {
            try {
                registrarPreaviso(registro, ventanaDatos);
            } catch (CumplimentarPDFException e) {
                nuevaVentanaPreaviso.mostrarMensaje(e.getMessage(), false);
            } catch (SQLException e) {
                nuevaVentanaPreaviso.mostrarMensaje(bundle.getString("preaviso.error_registro"), false);
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
        agregarSeccion(gridPane, bundle.getString("preaviso.datos_empresa"), 0);
        textFieldNombreEmpresa = agregarCampo(gridPane, bundle.getString("preaviso.nombre_empresa"), 1);
        textFieldCIF = agregarCampo(gridPane, bundle.getString("preaviso.cif"), 2);
        textFieldNombreComercial = agregarCampo(gridPane, bundle.getString("preaviso.nombre_comercial"), 3);

        agregarSeccion(gridPane, bundle.getString("preaviso.datos_centro"), 4);
        textFieldNombreCentro = agregarCampo(gridPane, bundle.getString("preaviso.nombre_centro"), 5);
        textFieldDireccion = agregarCampo(gridPane, bundle.getString("preaviso.direccion"), 6);
        textFieldMunicipio = agregarCampo(gridPane, bundle.getString("preaviso.municipio"), 7);
        textFieldCodigoPostal = agregarCampo(gridPane, bundle.getString("preaviso.codigo_postal"), 8);
        textFieldProvincia = agregarCampo(gridPane, bundle.getString("preaviso.provincia"), 9);
        textFieldNumTrabajadores = agregarCampo(gridPane, bundle.getString("preaviso.num_trabajadores"), 10);
        textFieldNumSegSocial = agregarCampo(gridPane, bundle.getString("preaviso.num_seguridad_social"), 11);

        agregarSeccion(gridPane, bundle.getString("preaviso.datos_eleccion"), 12);
        textFieldMesEleccion = agregarCampo(gridPane, bundle.getString("preaviso.mes_eleccion"), 14);
        textFieldPromotores = agregarCampo(gridPane, bundle.getString("preaviso.promotores"), 15);
        textFieldFechaInicio = agregarCampo(gridPane, bundle.getString("preaviso.fecha_inicio"), 16);
        textFieldDiaPreaviso = agregarCampo(gridPane, bundle.getString("preaviso.dia_preaviso"), 17);
        textFieldMesPreaviso = agregarCampo(gridPane, bundle.getString("preaviso.mes_preaviso"), 18);
        textFieldAnioPreaviso = agregarCampo(gridPane, bundle.getString("preaviso.anio_preaviso"), 19);
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
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-underline: true;");
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
        addValidationListenerWithErrorHandling(textFieldMesEleccion, (text, preaviso) -> preaviso.setMesElecciones(text), Duration.seconds(0.1));
        addValidationListenerWithErrorHandling(textFieldFechaInicio, (text, preaviso) -> preaviso.setFechaConstitucion(text), Duration.seconds(0.1));
        addValidationListenerWithErrorHandling(textFieldDiaPreaviso, (text, preaviso) -> preaviso.setDiaPreaviso(text), Duration.seconds(0.1));
        addValidationListenerWithErrorHandling(textFieldMesPreaviso, (text, preaviso) -> preaviso.setMesPreaviso(text), Duration.seconds(0.1));
        addValidationListenerWithErrorHandling(textFieldAnioPreaviso, (text, preaviso) -> preaviso.setAnioPreaviso(text), Duration.seconds(0.1));
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

        String mensajeConfirmacion = construirMensajeConfirmacion().toUpperCase();

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
                nuevaVentanaPreaviso.mostrarMensaje(bundle.getString("preaviso.error_db") + e.getMessage(), false);
            }
        }
    }

    private void cerrarVentanaPreaviso(Stage stage) {
        stage.close();
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

    private String construirMensajeConfirmacion() {
        return STR."\{bundle.getString("preaviso.nombre_empresa")} \{textFieldNombreEmpresa.getText()}\n\{bundle.getString("preaviso.cif")} \{textFieldCIF.getText()}\n\{bundle.getString("preaviso.nombre_comercial")} \{textFieldNombreComercial.getText()}\n\{bundle.getString("preaviso.nombre_centro")} \{textFieldNombreCentro.getText()}\n\{bundle.getString("preaviso.direccion")} \{textFieldDireccion.getText()}\n\{bundle.getString("preaviso.municipio")} \{textFieldMunicipio.getText()}\n\{bundle.getString("preaviso.codigo_postal")} \{textFieldCodigoPostal.getText()}\n\{bundle.getString("preaviso.provincia")} \{textFieldProvincia.getText()}\n\{bundle.getString("preaviso.num_trabajadores")} \{textFieldNumTrabajadores.getText()}\n\{bundle.getString("preaviso.num_seguridad_social")} \{textFieldNumSegSocial.getText()}\n\{bundle.getString("preaviso.mes_eleccion")} \{textFieldMesEleccion.getText()}\n\{bundle.getString("preaviso.promotores")} \{textFieldPromotores.getText()}\n\{bundle.getString("preaviso.fecha_inicio")} \{textFieldFechaInicio.getText()}\n\{bundle.getString("preaviso.dia_preaviso")} \{textFieldDiaPreaviso.getText()}\n\{bundle.getString("preaviso.mes_preaviso")} \{textFieldMesPreaviso.getText()}\n\{bundle.getString("preaviso.anio_preaviso")} \{textFieldAnioPreaviso.getText()}";
    }

    @FunctionalInterface
    public interface CheckedBiConsumer<T, U> {
        void accept(T t, U u) throws CumplimentarPDFException;
    }
}
