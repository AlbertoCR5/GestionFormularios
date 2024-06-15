package com.example.proyecto.interfaz;

import com.example.proyecto.modal.Modelo_5_1;
import com.example.proyecto.modal.Modelo_5_2_Conclusion;
import com.example.proyecto.modal.Modelo_5_2_Proceso;
import com.example.proyecto.util.MessageManager;
import com.example.proyecto.util.Registro;
import javafx.application.Platform;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

/**
 * La clase `VentanaModelosEscrutinio` gestiona la secuencia de ventanas para cumplimentar los modelos de escrutinio.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class VentanaModelosEscrutinio {

    private final PrincipalView vistaPrincipal;
    private final Path rutaEmpresa;
    private final Modelo_5_1 nuevoModelo5_1;
    private final Modelo_5_2_Proceso nuevoModelo5_2Proceso;
    private final Modelo_5_2_Conclusion nuevoModeloConclusion;

    /**
     * Constructor de la clase VentanaModelosEscrutinio.
     *
     * @param vistaPrincipal La vista principal de la aplicación.
     * @param rutaEmpresa La ruta donde se guardan los datos de la empresa.
     * @param nuevoModelo5_1 El modelo 5_1 que se va a cumplimentar.
     * @param nuevoModelo5_2Proceso El modelo 5_2 de proceso que se va a cumplimentar.
     * @param nuevoModeloConclusion El modelo 5_2 de conclusión que se va a cumplimentar.
     */
    public VentanaModelosEscrutinio(@NotNull PrincipalView vistaPrincipal, @NotNull Path rutaEmpresa,
                                    @NotNull Modelo_5_1 nuevoModelo5_1, @NotNull Modelo_5_2_Proceso nuevoModelo5_2Proceso,
                                    @NotNull Modelo_5_2_Conclusion nuevoModeloConclusion) {
        this.vistaPrincipal = vistaPrincipal;
        this.rutaEmpresa = rutaEmpresa;
        this.nuevoModelo5_1 = nuevoModelo5_1;
        this.nuevoModelo5_2Proceso = nuevoModelo5_2Proceso;
        this.nuevoModeloConclusion = nuevoModeloConclusion;
    }

    /**
     * Inicia la secuencia de ventanas.
     */
    public void iniciarSecuencia() {
        Platform.runLater(() -> {
            try {
                mostrarVentanaModelo51();
            } catch (IOException e) {
                mostrarMensajeError("modelo51.error_mostrar", e);
            }
        });
    }

    /**
     * Muestra la ventana para el modelo 5_1.
     *
     * @throws IOException Si hay un error al mostrar la ventana.
     */
    private void mostrarVentanaModelo51() throws IOException {
        VentanaModelo5_1 ventanaModelo51 = new VentanaModelo5_1(vistaPrincipal, rutaEmpresa, nuevoModelo5_1, nuevoModelo5_2Proceso, nuevoModeloConclusion);
        ventanaModelo51.configurarVentanaModelo5_1();
    }

    /**
     * Muestra la ventana para el modelo de conclusión.
     */
    public void mostrarVentanaModeloConclusion() {
        Platform.runLater(() -> {
            try {
                Registro registro = new Registro(nuevoModelo5_1, nuevoModelo5_2Proceso, nuevoModeloConclusion, vistaPrincipal);
                VentanaModeloConclusion ventanaConclusion = new VentanaModeloConclusion(vistaPrincipal, rutaEmpresa, nuevoModelo5_1, nuevoModelo5_2Proceso, nuevoModeloConclusion, registro);
                ventanaConclusion.configurarVentanaConclusion();
            } catch (IOException e) {
                mostrarMensajeError("conclusion.error_mostrar", e);
            }
        });
    }

    /**
     * Muestra un mensaje de error en la vista principal.
     *
     * @param claveMensaje La clave del mensaje de error en el MessageManager.
     * @param e La excepción que ocurrió.
     */
    private void mostrarMensajeError(@NotNull String claveMensaje, @NotNull IOException e) {
        vistaPrincipal.mostrarMensaje(String.format(MessageManager.getMessage(claveMensaje), e.getMessage()), false);
    }
}
