package com.example.proyecto.interfaz;

import com.example.proyecto.modal.UsuarioDAO;
import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.MessageManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.jetbrains.annotations.NotNull;

/**
 * La clase `VentanaUsuario` gestiona la interfaz gráfica para la administración de usuarios.
 * Permite añadir, actualizar y eliminar usuarios según los permisos del usuario actual.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 */
public class VentanaUsuario {

    private final UsuarioDAO usuarioDAO;
    private final String usuarioActual;
    private final boolean esAdmin;
    private final PrincipalView view;

    /**
     * Constructor de la clase VentanaUsuario.
     *
     * @param usuarioDAO    El DAO para las operaciones de usuario.
     * @param usuarioActual El nombre del usuario actual.
     * @param esAdmin       Indica si el usuario actual es administrador.
     * @param view          La vista principal de la aplicación.
     */
    public VentanaUsuario(@NotNull UsuarioDAO usuarioDAO, @NotNull String usuarioActual, boolean esAdmin, @NotNull PrincipalView view) {
        this.usuarioDAO = usuarioDAO;
        this.usuarioActual = usuarioActual;
        this.esAdmin = esAdmin;
        this.view = view;
    }

    /**
     * Muestra la ventana de administración de usuarios.
     * Si el usuario es administrador, se muestran las opciones de añadir y eliminar usuarios.
     * Todos los usuarios pueden actualizar su propia contraseña.
     */
    public void mostrarVentanaUsuario() {
        Stage stage = new Stage();
        stage.setTitle(MessageManager.getMessage("titulo.ventana.usuario"));

        VBox vbox = crearVBox();

        // Etiqueta de tipo de usuario centrada
        Label tipoUsuarioLabel = crearLabelTipoUsuario();
        vbox.getChildren().add(tipoUsuarioLabel);

        GridPane grid = crearGridPane();

        int row = 0;

        // Si el usuario es administrador, mostrar opciones de añadir y eliminar usuario
        if (esAdmin) {
            row = agregarSeccionAdmin(grid, row);
        }

        agregarSeccionActualizarContrasena(grid, row);

        vbox.getChildren().add(grid);

        Scene scene = new Scene(vbox, 300, esAdmin ? 470 : 200);
        stage.setScene(scene);
        stage.show();
    }

    private VBox crearVBox() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    private Label crearLabelTipoUsuario() {
        Label tipoUsuarioLabel = new Label(esAdmin ? "ADMINISTRADOR" : "USUARIO");
        tipoUsuarioLabel.setStyle(Constantes.BOLD_UNDERLINED_STYLE);
        tipoUsuarioLabel.setAlignment(Pos.CENTER);
        tipoUsuarioLabel.setMaxWidth(Double.MAX_VALUE);
        return tipoUsuarioLabel;
    }

    private GridPane crearGridPane() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);
        return grid;
    }

    private int agregarSeccionAdmin(GridPane grid, int row) {
        // Añadir Usuario
        Label addUserLabel = crearLabelTitulo(MessageManager.getMessage("label.aniadir.usuario"));
        GridPane.setConstraints(addUserLabel, 0, row++, 2, 1);

        Label usuarioLabel = new Label(MessageManager.getMessage("label.usuario"));
        GridPane.setConstraints(usuarioLabel, 0, row);

        TextField usuarioInput = new TextField();
        usuarioInput.setPromptText(MessageManager.getMessage("prompt.usuario"));
        GridPane.setConstraints(usuarioInput, 1, row++);

        Label contrasenaLabel = new Label(MessageManager.getMessage("label.contrasena"));
        GridPane.setConstraints(contrasenaLabel, 0, row);

        PasswordField contrasenaInput = new PasswordField();
        contrasenaInput.setPromptText(MessageManager.getMessage("prompt.contrasena"));
        GridPane.setConstraints(contrasenaInput, 1, row++);

        Label tipoUsuarioNuevoLabel = new Label("Tipo de Usuario:");
        GridPane.setConstraints(tipoUsuarioNuevoLabel, 0, row);

        ComboBox<String> tipoUsuarioComboBox = new ComboBox<>();
        tipoUsuarioComboBox.getItems().addAll("Usuario", "Administrador");
        tipoUsuarioComboBox.setValue("Usuario"); // Valor por defecto
        GridPane.setConstraints(tipoUsuarioComboBox, 1, row++);

        Button addButton = new Button(MessageManager.getMessage("boton.aniadir"));
        GridPane.setConstraints(addButton, 1, row++);
        addButton.setOnAction(_ -> agregarUsuario(usuarioInput, contrasenaInput, tipoUsuarioComboBox));

        grid.getChildren().addAll(addUserLabel, usuarioLabel, usuarioInput, contrasenaLabel, contrasenaInput, tipoUsuarioNuevoLabel, tipoUsuarioComboBox, addButton);

        // Eliminar Usuario
        Label deleteUserLabel = crearLabelTitulo(MessageManager.getMessage("label.eliminar.usuario"));
        GridPane.setConstraints(deleteUserLabel, 0, row++, 2, 1);

        Label usuarioDeleteLabel = new Label(MessageManager.getMessage("label.usuario"));
        GridPane.setConstraints(usuarioDeleteLabel, 0, row);

        TextField usuarioDeleteInput = new TextField();
        usuarioDeleteInput.setPromptText(MessageManager.getMessage("prompt.usuario"));
        GridPane.setConstraints(usuarioDeleteInput, 1, row++);

        Label contrasenaDeleteLabel = new Label(MessageManager.getMessage("label.contrasena"));
        GridPane.setConstraints(contrasenaDeleteLabel, 0, row);

        PasswordField contrasenaDeleteInput = new PasswordField();
        contrasenaDeleteInput.setPromptText(MessageManager.getMessage("prompt.contrasena"));
        GridPane.setConstraints(contrasenaDeleteInput, 1, row++);

        Button deleteButton = new Button(MessageManager.getMessage("boton.eliminar"));
        GridPane.setConstraints(deleteButton, 1, row++);
        deleteButton.setOnAction(_ -> eliminarUsuario(usuarioDeleteInput, contrasenaDeleteInput));

        grid.getChildren().addAll(deleteUserLabel, usuarioDeleteLabel, usuarioDeleteInput, contrasenaDeleteLabel, contrasenaDeleteInput, deleteButton);

        return row;
    }

    private void agregarSeccionActualizarContrasena(GridPane grid, int row) {
        Label updatePasswordLabel = crearLabelTitulo(MessageManager.getMessage("label.actualizar.contrasena"));
        GridPane.setConstraints(updatePasswordLabel, 0, row++, 2, 1);

        Label usuarioUpdateLabel = new Label(MessageManager.getMessage("label.usuario"));
        GridPane.setConstraints(usuarioUpdateLabel, 0, row);

        TextField usuarioUpdateInput = new TextField(usuarioActual);
        usuarioUpdateInput.setDisable(true);
        GridPane.setConstraints(usuarioUpdateInput, 1, row++);

        Label contrasenaUpdateLabel = new Label(MessageManager.getMessage("label.nueva.contrasena"));
        GridPane.setConstraints(contrasenaUpdateLabel, 0, row);

        PasswordField contrasenaUpdateInput = new PasswordField();
        contrasenaUpdateInput.setPromptText(MessageManager.getMessage("prompt.nueva.contrasena"));
        GridPane.setConstraints(contrasenaUpdateInput, 1, row++);

        Button updateButton = new Button(MessageManager.getMessage("boton.actualizar"));
        GridPane.setConstraints(updateButton, 1, row);
        updateButton.setOnAction(_ -> actualizarContrasena(contrasenaUpdateInput));

        grid.getChildren().addAll(updatePasswordLabel, usuarioUpdateLabel, usuarioUpdateInput, contrasenaUpdateLabel, contrasenaUpdateInput, updateButton);
    }

    private Label crearLabelTitulo(@NotNull String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        label.setAlignment(Pos.CENTER);
        label.setMaxWidth(Double.MAX_VALUE);
        return label;
    }

    private void agregarUsuario(@NotNull TextField usuarioInput, @NotNull PasswordField contrasenaInput, @NotNull ComboBox<String> tipoUsuarioComboBox) {
        try {
            boolean esAdminNuevo = tipoUsuarioComboBox.getValue().equals("Administrador");
            usuarioDAO.insertUsuario(usuarioInput.getText(), contrasenaInput.getText(), esAdminNuevo);
            usuarioInput.clear();
            contrasenaInput.clear();
            tipoUsuarioComboBox.setValue("Usuario");
            view.mostrarMensaje(MessageManager.getMessage("mensaje.usuario.aniadido"), true);
        } catch (Exception ex) {
            view.mostrarMensaje(MessageManager.getMessage("error.aniadir.usuario") + ex.getMessage(), false);
        }
    }

    private void eliminarUsuario(@NotNull TextField usuarioDeleteInput, @NotNull PasswordField contrasenaDeleteInput) {
        try {
            if (usuarioDAO.verificarContrasena(usuarioDeleteInput.getText(), contrasenaDeleteInput.getText())) {
                usuarioDAO.deleteUsuario(usuarioDeleteInput.getText());
                usuarioDeleteInput.clear();
                contrasenaDeleteInput.clear();
                view.mostrarMensaje(MessageManager.getMessage("mensaje.usuario.eliminado"), true);
            } else {
                view.mostrarMensaje(MessageManager.getMessage("error.contrasena.incorrecta"), false);
            }
        } catch (Exception ex) {
            view.mostrarMensaje(MessageManager.getMessage("error.eliminar.usuario") + ex.getMessage(), false);
        }
    }

    private void actualizarContrasena(@NotNull PasswordField contrasenaUpdateInput) {
        try {
            usuarioDAO.updateContrasena(usuarioActual, contrasenaUpdateInput.getText());
            contrasenaUpdateInput.clear();
            view.mostrarMensaje(MessageManager.getMessage("mensaje.contrasena.actualizada"), true);
        } catch (Exception ex) {
            view.mostrarMensaje(MessageManager.getMessage("error.actualizar.contrasena") + ex.getMessage(), false);
        }
    }
}
