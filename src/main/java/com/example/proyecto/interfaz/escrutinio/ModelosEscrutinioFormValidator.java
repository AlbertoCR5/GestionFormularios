package com.example.proyecto.interfaz.escrutinio;

import com.example.proyecto.interfaz.PrincipalView;
import com.example.proyecto.modal.Modelo_5_1;
import com.example.proyecto.modal.Modelo_5_2_Conclusion;
import com.example.proyecto.modal.Modelo_5_2_Proceso;
import com.example.proyecto.util.CumplimentarPDFException;
import javafx.animation.PauseTransition;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

public class ModelosEscrutinioFormValidator {

    private final PrincipalView ventanaPrincipal;
    private final Modelo_5_1 nuevoModelo5_1;
    private final Modelo_5_2_Proceso nuevoModelo5_2Proceso;
    private final Modelo_5_2_Conclusion nuevoModeloConclusion;
    private boolean alertaMostrada = false;

    public ModelosEscrutinioFormValidator(PrincipalView ventanaPrincipal,  Modelo_5_1 nuevoModelo5_1, Modelo_5_2_Proceso nuevoModelo5_2Proceso, Modelo_5_2_Conclusion nuevoModeloConclusion) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.nuevoModelo5_1 = nuevoModelo5_1;
        this.nuevoModelo5_2Proceso = nuevoModelo5_2Proceso;
        this.nuevoModeloConclusion = nuevoModeloConclusion;
    }

    public void addValidationListenerWithErrorHandlingM51(@NotNull TextField textField, @NotNull CheckedBiConsumer<String, Modelo_5_1> setter, @NotNull Duration focusDuration) {
        textField.focusedProperty().addListener((_, _, newValue) -> {
            if (!newValue && !alertaMostrada) {
                try {
                    setter.accept(textField.getText(), nuevoModelo5_1);
                } catch (CumplimentarPDFException e) {
                    alertaMostrada = true;
                    ventanaPrincipal.mostrarMensaje(e.getMessage(), false);
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

    public void addValidationListenerWithErrorHandlingM52(@NotNull TextField textField, @NotNull CheckedBiConsumer<String, Modelo_5_2_Proceso> setter, @NotNull Duration focusDuration) {
        textField.focusedProperty().addListener((_, _, newValue) -> {
            if (!newValue && !alertaMostrada) {
                try {
                    setter.accept(textField.getText(), nuevoModelo5_2Proceso);
                } catch (CumplimentarPDFException e) {
                    alertaMostrada = true;
                    ventanaPrincipal.mostrarMensaje(e.getMessage(), false);
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

    public void addValidationListenerWithErrorHandlingMC(@NotNull TextField textField, @NotNull CheckedBiConsumer<String, Modelo_5_2_Conclusion> setter, @NotNull Duration focusDuration) {
        textField.focusedProperty().addListener((_, _, newValue) -> {
            if (!newValue && !alertaMostrada) {
                try {
                    setter.accept(textField.getText(), nuevoModeloConclusion);
                } catch (CumplimentarPDFException e) {
                    alertaMostrada = true;
                    ventanaPrincipal.mostrarMensaje(e.getMessage(), false);
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

    @FunctionalInterface
    public interface CheckedBiConsumer<T, U> {
        void accept(T t, U u) throws CumplimentarPDFException;
    }
}
