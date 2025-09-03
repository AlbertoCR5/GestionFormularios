package com.albertocr.gestionformularios.interfaz.escrutinio;

import com.albertocr.gestionformularios.controller.escrutinio.EscrutinioComiteController;
import com.albertocr.gestionformularios.service.EscrutinioService;
import com.albertocr.gestionformularios.service.dto.ActaComiteData;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * Clase que representa y gestiona la ventana de escrutinio para comités.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.0
 */
public class VentanaEscrutinioComite extends Stage {

    private static final Logger logger = LoggerFactory.getLogger(VentanaEscrutinioComite.class);
    private static final String FXML_PATH = "/com/albertocr/gestionformularios/interfaz/escrutinio/escrutinio-comite-view.fxml";

    /**
     * Constructor que crea y configura la ventana de escrutinio de comités.
     *
     * @param actaData         Los datos completos del acta, necesarios para el controlador.
     * @param escrutinioService La instancia del servicio para realizar operaciones de guardado.
     */
    public VentanaEscrutinioComite(ActaComiteData actaData, EscrutinioService escrutinioService) {
        setTitle("Gestión de Escrutinio de Comité de Empresa");
        initModality(Modality.APPLICATION_MODAL);

        try {
            FXMLLoader loader = new FXMLLoader(getResourceUrl());

            // Inyectar los datos y el servicio en el controlador usando un ControllerFactory
            loader.setControllerFactory(controllerClass -> new EscrutinioComiteController(actaData, escrutinioService));

            VBox root = loader.load();
            Scene scene = new Scene(root);
            setScene(scene);

            logger.info("Ventana de escrutinio de comité cargada correctamente.");
        } catch (IOException e) {
            logger.error("Error fatal al cargar la vista de escrutinio de comité desde FXML.", e);
            throw new RuntimeException(e);
        }
    }

    private URL getResourceUrl() throws IOException {
        URL url = getClass().getResource(FXML_PATH);
        return Objects.requireNonNull(url, "No se pudo encontrar el archivo FXML: " + FXML_PATH);
    }
}
