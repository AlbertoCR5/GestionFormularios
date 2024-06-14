package com.example.proyecto.interfaz;

import com.example.proyecto.modal.*;
import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.MessageManager;
import com.example.proyecto.util.Registro;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Optional;


/**
 * La clase `VentanaModeloConclusion` gestiona la ventana que solicita los datos del modelo de conclusión.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 */
public class VentanaModeloConclusion {

    private final PrincipalView vistaPrincipal;
    private final Modelo_5_1 nuevoModelo5_1;
    private final Modelo_5_2_Proceso nuevoModeloProceso;
    private final Modelo_5_2_Conclusion modeloConclusion;
    private final Registro registro;
    private final Path rutaEmpresa;

    /**
     * Constructor para la clase `VentanaModeloConclusion`.
     *
     * @param vistaPrincipal La vista principal de la aplicación.
     * @param rutaEmpresa La ruta de la empresa.
     * @param nuevoModelo5_1 El modelo 5_1 que se va a utilizar para almacenar los datos ingresados.
     * @param nuevoModeloProceso El modelo de proceso que se va a utilizar para almacenar los datos ingresados.
     * @param modeloConclusion El modelo de conclusión que se va a utilizar para almacenar los datos ingresados.
     * @param registro El registro donde se almacenarán los modelos de escrutinio.
     */
    public VentanaModeloConclusion(PrincipalView vistaPrincipal, Path rutaEmpresa, Modelo_5_1 nuevoModelo5_1, Modelo_5_2_Proceso nuevoModeloProceso, Modelo_5_2_Conclusion modeloConclusion, Registro registro) {
        this.vistaPrincipal = vistaPrincipal;
        this.rutaEmpresa = rutaEmpresa;
        this.nuevoModelo5_1 = nuevoModelo5_1;
        this.nuevoModeloProceso = nuevoModeloProceso;
        this.modeloConclusion = modeloConclusion;
        this.registro = registro;
    }

    /**
     * Configura la ventana para ingresar los datos del modelo de conclusión.
     *
     * @throws IOException Si ocurre un error al configurar la ventana.
     */
    public void configurarVentanaConclusion() throws IOException {
        Stage stage = new Stage();
        stage.setTitle(MessageManager.getMessage("conclusion.title"));

        VBox vbox = crearVBoxPrincipal();

        Label labelModeloConclusion = crearLabelPrincipal();
        GridPane gridPane = crearGridPane();

        TextField textFieldActividadEconomica = crearTextField(gridPane, MessageManager.getMessage("conclusion.actividad_economica"), 0);
        TextField textFieldConvenio = crearTextField(gridPane, MessageManager.getMessage("conclusion.convenio"), 1);
        TextField textFieldNumeroConvenio = crearTextField(gridPane, MessageManager.getMessage("conclusion.numero_convenio"), 2);
        TextField textFieldTrabajadoresFijos = crearTextField(gridPane, MessageManager.getMessage("conclusion.trabajadores_fijos"), 3);

        Button btnGuardar = crearBotonGuardar(stage, textFieldActividadEconomica, textFieldConvenio, textFieldNumeroConvenio, textFieldTrabajadoresFijos);

        vbox.getChildren().addAll(labelModeloConclusion, gridPane, crearGuardarBox(btnGuardar));

        Scene scene = new Scene(vbox, 500, 400);
        stage.setScene(scene);
        stage.show();
    }

    private VBox crearVBoxPrincipal() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    private Label crearLabelPrincipal() {
        Label labelModeloConclusion = new Label(MessageManager.getMessage("conclusion.informacion"));
        labelModeloConclusion.setStyle(Constantes.BOLD_UNDERLINED_STYLE);
        labelModeloConclusion.setAlignment(Pos.CENTER);
        return labelModeloConclusion;
    }

    private GridPane crearGridPane() {
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

    private Button crearBotonGuardar(Stage stage, TextField textFieldActividadEconomica, TextField textFieldConvenio, TextField textFieldNumeroConvenio, TextField textFieldTrabajadoresFijos) {
        Button btnGuardar = new Button(MessageManager.getMessage("conclusion.guardar"));
        btnGuardar.setOnAction(_ -> guardarDatos(stage, textFieldActividadEconomica, textFieldConvenio, textFieldNumeroConvenio, textFieldTrabajadoresFijos));
        return btnGuardar;
    }

    private HBox crearGuardarBox(Button btnGuardar) {
        HBox guardarBox = new HBox(btnGuardar);
        guardarBox.setAlignment(Pos.CENTER);
        guardarBox.setPadding(new Insets(10, 0, 0, 0));
        return guardarBox;
    }

    private void guardarDatos(Stage stage, TextField textFieldActividadEconomica, TextField textFieldConvenio, TextField textFieldNumeroConvenio, TextField textFieldTrabajadoresFijos) {
        try {
            modeloConclusion.setActvEcono(textFieldActividadEconomica.getText().toUpperCase());
            modeloConclusion.setNombreConvenio(textFieldConvenio.getText().toUpperCase());
            modeloConclusion.setNumeroConvenio(textFieldNumeroConvenio.getText());
            modeloConclusion.setTrabajadoresFijos(textFieldTrabajadoresFijos.getText());
            nuevoModeloProceso.setTotalElectores(Integer.parseInt(textFieldTrabajadoresFijos.getText()));

            DatabaseManager dbManager = new DatabaseManager(vistaPrincipal);
            EleccionesDAO eleccionesDAO = new EleccionesDAO(vistaPrincipal, dbManager);
            EmpresaDAO empresaDAO = new EmpresaDAO(vistaPrincipal, dbManager);
            String nombreEmpresaCompleto = rutaEmpresa.getFileName().toString();
            eleccionesDAO.updateEleccion(nuevoModelo5_1, nuevoModeloProceso, nombreEmpresaCompleto);
            empresaDAO.updateEmpresaOtros(nuevoModeloProceso, modeloConclusion, nombreEmpresaCompleto);
            GridPane mensajeConfirmacion = construirMensajeConfirmacion(textFieldActividadEconomica, textFieldConvenio, textFieldNumeroConvenio, textFieldTrabajadoresFijos);
            mostrarConfirmacion(stage, mensajeConfirmacion);
        } catch (CumplimentarPDFException ex) {
            vistaPrincipal.mostrarMensaje(String.format(MessageManager.getMessage("conclusion.error_guardar"), ex.getMessage()), false);
        } catch (NumberFormatException ex) {
            vistaPrincipal.mostrarMensaje(MessageManager.getMessage("conclusion.numero_invalido"), false);
        } catch (SQLException e) {
            vistaPrincipal.mostrarMensaje(String.format(MessageManager.getMessage("conclusion.error_db"), e.getMessage()), false);
        }
    }

    private GridPane construirMensajeConfirmacion(TextField textFieldActividadEconomica, TextField textFieldConvenio, TextField textFieldNumeroConvenio, TextField textFieldTrabajadoresFijos) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        int rowIndex = 0;

        agregarFila(gridPane, MessageManager.getMessage("modelo5_1.preaviso"), nuevoModeloProceso.getPreaviso(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("modelo5_1.fecha_escrutinio"), nuevoModelo5_1.getFechaEscrutinio(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("conclusion.actividad_economica"), textFieldActividadEconomica.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("conclusion.convenio"), textFieldConvenio.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("conclusion.numero_convenio"), textFieldNumeroConvenio.getText(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("conclusion.trabajadores_fijos"), textFieldTrabajadoresFijos.getText(), rowIndex++);

        // Agregar encabezado para candidatos
        Text candidatosHeader = new Text("\n" + MessageManager.getMessage("modelo5_1.candidatos") + ":\n");
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
    private void agregarFila(GridPane gridPane, String label, String value, int rowIndex) {
        Text labelText = new Text(label + ": ");
        Text valueText = new Text(value);
        valueText.setStyle("-fx-font-weight: bold");

        gridPane.add(labelText, 0, rowIndex);
        gridPane.add(valueText, 1, rowIndex);
    }



    /**
     * Muestra una ventana de confirmación para revisar los datos ingresados.
     * Si el usuario confirma, se registran los modelos de escrutinio y se muestra un mensaje de éxito.
     *
     * @param stage La ventana actual que se está mostrando.
     * @throws CumplimentarPDFException Si ocurre un error al registrar los modelos de escrutinio.
     */
    private void mostrarConfirmacion(Stage stage, GridPane mensajeConfirmacion) throws CumplimentarPDFException {
        Optional<ButtonType> result = vistaPrincipal.mostrarAlertaConfirmacion(mensajeConfirmacion);
        if (result.isPresent() && result.get() == ButtonType.OK) { // Si el usuario confirma
            // Registrar los modelos de escrutinio
            registro.registrarModelosEscrutinio(nuevoModelo5_1, nuevoModeloProceso, modeloConclusion, rutaEmpresa);
            // Mostrar un mensaje de éxito
            vistaPrincipal.mostrarMensaje(MessageManager.getMessage("conclusion.datos_guardados"), true);            // Cerrar la ventana actual
            stage.close();
        }
    }
}

