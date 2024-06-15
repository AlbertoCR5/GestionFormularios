package com.example.proyecto.interfaz.preaviso;

import com.example.proyecto.interfaz.PrincipalView;
import com.example.proyecto.modal.Preaviso;
import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.Meses;
import com.example.proyecto.util.ProvinciasAndalucia;
import javafx.animation.PauseTransition;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

/**
 * La clase `PreavisoFormValidator` gestiona la validación de campos en el formulario de preaviso.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class PreavisoFormValidator {

    private final PrincipalView nuevaVentanaPreaviso;
    private final Preaviso nuevoPreaviso;
    private boolean alertaMostrada = false;

    /**
     * Constructor de la clase PreavisoFormValidator.
     *
     * @param nuevaVentanaPreaviso La vista principal.
     * @param nuevoPreaviso El objeto Preaviso que se va a registrar.
     */
    public PreavisoFormValidator(@NotNull PrincipalView nuevaVentanaPreaviso, @NotNull Preaviso nuevoPreaviso) {
        this.nuevaVentanaPreaviso = nuevaVentanaPreaviso;
        this.nuevoPreaviso = nuevoPreaviso;
    }

    /**
     * Agrega un listener de validación a un campo de texto con manejo de excepciones.
     *
     * @param textField el campo de texto a validar.
     * @param setter el método para establecer el valor en el objeto Preaviso.
     * @param focusDuration la duración para reenfocar el campo de texto en caso de error.
     */
    public void addValidationListenerWithErrorHandling(@NotNull TextField textField, @NotNull CheckedBiConsumer<String, Preaviso> setter, @NotNull Duration focusDuration) {
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

    /**
     * Agrega un listener de validación a un ComboBox de ProvinciasAndalucia con manejo de excepciones.
     *
     * @param comboBox el ComboBox de ProvinciasAndalucia a validar.
     * @param setter el método para establecer el valor en el objeto Preaviso.
     * @param focusDuration la duración para reenfocar el ComboBox en caso de error.
     */
    public void addProvinciaValidationListenerWithErrorHandling(@NotNull ComboBox<ProvinciasAndalucia> comboBox, @NotNull CheckedBiConsumer<ProvinciasAndalucia, Preaviso> setter, @NotNull Duration focusDuration) {
        comboBox.focusedProperty().addListener((_, _, newValue) -> {
            if (!newValue && !alertaMostrada) {
                try {
                    setter.accept(comboBox.getValue(), nuevoPreaviso);
                } catch (CumplimentarPDFException e) {
                    alertaMostrada = true;
                    nuevaVentanaPreaviso.mostrarMensaje(e.getMessage(), false);
                    PauseTransition pause = new PauseTransition(focusDuration);
                    pause.setOnFinished(_ -> {
                        comboBox.requestFocus();
                        alertaMostrada = false;
                    });
                    pause.play();
                }
            }
        });
    }

    /**
     * Agrega un listener de validación a un ComboBox de Meses con manejo de excepciones.
     *
     * @param comboBox el ComboBox de Meses a validar.
     * @param setter el método para establecer el valor en el objeto Preaviso.
     * @param focusDuration la duración para reenfocar el ComboBox en caso de error.
     */
    public void addMesValidationListenerWithErrorHandling(@NotNull ComboBox<Meses> comboBox, @NotNull CheckedBiConsumer<Meses, Preaviso> setter, @NotNull Duration focusDuration) {
        comboBox.focusedProperty().addListener((_, _, newValue) -> {
            if (!newValue && !alertaMostrada) {
                try {
                    setter.accept(comboBox.getValue(), nuevoPreaviso);
                } catch (CumplimentarPDFException e) {
                    alertaMostrada = true;
                    nuevaVentanaPreaviso.mostrarMensaje(e.getMessage(), false);
                    PauseTransition pause = new PauseTransition(focusDuration);
                    pause.setOnFinished(_ -> {
                        comboBox.requestFocus();
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
