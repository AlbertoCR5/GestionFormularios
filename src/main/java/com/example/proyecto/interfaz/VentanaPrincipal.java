package com.example.proyecto.interfaz;

import com.example.proyecto.controller.PrincipalController;
import com.example.proyecto.util.Constantes;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * La clase `VentanaPrincipal` gestiona la interfaz de la ventana principal de la aplicación.
 * Utiliza JavaFX para la interfaz gráfica.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 */
public class VentanaPrincipal {

    private final PrincipalController controller;
    private final ResourceBundle bundle;
    private final Stage stage;

    public VentanaPrincipal(PrincipalController controller, ResourceBundle bundle) {
        this.controller = controller;
        this.bundle = bundle;
        this.stage = new Stage();
        configurarVentanaPrincipal();
    }

    /**
     * Configura la ventana principal de la aplicación.
     */
    private void configurarVentanaPrincipal() {
        stage.setTitle(bundle.getString("principal.title"));

        VBox vbox = crearVBox();
        Label titulo = crearTitulo();
        Label label = crearLabel();

        List<Button> botones = Arrays.asList(
                crearBoton(bundle.getString("principal.preaviso"), 1),
                crearBoton(bundle.getString("principal.modelos"), 2),
                crearBotonDeshabilitado(bundle.getString("principal.modelo_73")), // Opción 3 deshabilitada
                crearBotonDeshabilitado(bundle.getString("principal.anexo_delegados")), // Opción 4 deshabilitada
                crearBotonDeshabilitado(bundle.getString("principal.calendario_comite")), // Opción 5 deshabilitada
                crearBoton(bundle.getString("principal.usuarios"), 6),
                crearBotonDeshabilitado(bundle.getString("principal.imprimir")), // Opción 7 deshabilitada
                crearBotonSalir()
        );

        configurarAnchoBotones(botones);
        vbox.getChildren().addAll(titulo, label);
        vbox.getChildren().addAll(botones);

        Scene scene = new Scene(vbox, 300, 400);
        stage.setScene(scene);
    }

    /**
     * Muestra la ventana principal.
     */
    public void mostrarVentanaPrincipal() {
        stage.show();
    }

    /**
     * Crea un VBox configurado.
     *
     * @return VBox configurado.
     */
    private VBox crearVBox() {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);
        return vbox;
    }

    /**
     * Crea un título para la ventana.
     *
     * @return Label configurado como título.
     */
    private Label crearTitulo() {
        Label titulo = new Label(bundle.getString("principal.menu"));
        titulo.setStyle(Constantes.BOLD_UNDERLINED_STYLE);
        return titulo;
    }

    /**
     * Crea un Label.
     *
     * @return Label configurado.
     */
    private Label crearLabel() {
        Label label = new Label(bundle.getString("principal.seleccion"));
        label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        return label;
    }

    /**
     * Crea un botón configurado.
     *
     * @param texto  El texto del botón.
     * @param opcion La opción que representa el botón.
     * @return Botón configurado.
     */
    private Button crearBoton(String texto, int opcion) {
        Button boton = new Button(texto);
        boton.setOnAction(_ -> controller.tratarOpcion(opcion));
        return boton;
    }

    /**
     * Crea un botón deshabilitado configurado.
     *
     * @param texto El texto del botón.
     * @return Botón deshabilitado configurado.
     */
    private Button crearBotonDeshabilitado(String texto) {
        Button boton = new Button(texto);
        boton.setDisable(true);
        boton.setStyle("-fx-opacity: 0.5;"); // Cambia la opacidad para indicar que está deshabilitado
        return boton;
    }

    /**
     * Crea el botón de salir.
     *
     * @return Botón de salir configurado.
     */
    private Button crearBotonSalir() {
        Button btnSalir = new Button(bundle.getString("principal.salir"));
        btnSalir.setOnAction(_ -> stage.close());
        return btnSalir;
    }

    /**
     * Configura el ancho de los botones.
     *
     * @param botones Los botones a configurar.
     */
    private void configurarAnchoBotones(List<Button> botones) {
        for (Button boton : botones) {
            boton.setPrefWidth(Constantes.ANCHO_BOTON);
        }
    }
}
