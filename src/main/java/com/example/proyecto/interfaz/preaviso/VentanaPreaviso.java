package com.example.proyecto.interfaz.preaviso;

import com.example.proyecto.interfaz.PrincipalView;
import com.example.proyecto.modal.Preaviso;
import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.MessageManager;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

/**
 * La clase `VentanaPreaviso` gestiona la interfaz de la ventana para los preavisos.
 * Utiliza JavaFX para la interfaz gráfica y permite registrar nuevos preavisos.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class VentanaPreaviso {

    private final PrincipalView nuevaVentanaPreaviso;
    private final Preaviso nuevoPreaviso;

    /**
     * Constructor de la clase VentanaPreaviso.
     *
     * @param principalView La vista principal.
     * @param nuevoPreaviso El objeto Preaviso que se va a registrar.
     */
    public VentanaPreaviso(@NotNull PrincipalView principalView, @NotNull Preaviso nuevoPreaviso) {
        this.nuevaVentanaPreaviso = principalView;
        this.nuevoPreaviso = nuevoPreaviso;
    }

    /**
     * Muestra la ventana de preaviso.
     */
    public void mostrarVentanaPreaviso() {
        Stage ventanaDatos = new Stage();
        ventanaDatos.initModality(Modality.APPLICATION_MODAL);
        ventanaDatos.setTitle(MessageManager.getMessage("preaviso.titulo"));

        PreavisoFormManager formManager = new PreavisoFormManager(nuevoPreaviso, nuevaVentanaPreaviso);

        VBox vbox = formManager.crearFormulario();
        Button btnRegistrar = formManager.crearBotonRegistrar(ventanaDatos);

        vbox.getChildren().add(btnRegistrar);

        Scene scene = new Scene(vbox, Constantes.ANCHO_VENTANA_PREAVISO, Constantes.ALTO_VENTANA_PREAVISO);
        ventanaDatos.setScene(scene);
        ventanaDatos.showAndWait();
    }
}
