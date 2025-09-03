package com.albertocr.gestionformularios.view;

import com.albertocr.gestionformularios.model.Candidato;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * CellFactory para la columna de antigüedad en la tabla de candidatos.
 * Formatea la fecha y calcula los meses de antigüedad, mostrando ambos valores.
 */
public class CandidatoCellFactory implements Callback<TableColumn<Candidato, LocalDate>, TableCell<Candidato, LocalDate>> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public TableCell<Candidato, LocalDate> call(TableColumn<Candidato, LocalDate> param) {
        return new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Calcular los meses de antigüedad
                    long meses = Period.between(item, LocalDate.now()).toTotalMonths();
                    // Formatear el texto y convertir a mayúsculas
                    String texto = String.format("(%s) --> %d MESES", item.format(DATE_FORMATTER), meses);
                    setText(texto.toUpperCase());
                    // Centrar el texto en la celda
                    setAlignment(Pos.CENTER);
                }
            }
        };
    }
}
