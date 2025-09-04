package com.albertocr.gestionformularios.controller.admin;

import com.albertocr.gestionformularios.util.AlertManager;
import com.albertocr.gestionformularios.util.DirectorioManager;
import com.albertocr.gestionformularios.util.PdfFieldReader;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Controlador para la ventana del inspector de PDF (pdf-inspector-view.fxml).
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.8
 */
public class PdfInspectorController {

    private static final Logger logger = LoggerFactory.getLogger(PdfInspectorController.class);

    private record PdfInfo(String name, String resourcePath) {
        @Override
        public String toString() {
            return name;
        }
    }

    @FXML private ComboBox<PdfInfo> pdfComboBox;
    @FXML private TableView<String> fieldsTableView;
    @FXML private TableColumn<String, String> fieldNameColumn;
    @FXML private Button exportButton, exportAllButton, exportAllInOneButton, exportAllJsonButton;
    @FXML private ProgressIndicator loadingIndicator;

    private final ObservableList<PdfInfo> pdfFiles = FXCollections.observableArrayList();
    private final ObservableList<String> fieldNames = FXCollections.observableArrayList();
    private final BooleanProperty busy = new SimpleBooleanProperty(false);

    @FXML
    public void initialize() {
        loadingIndicator.setVisible(false);
        cargarListaDePdfs();
        pdfComboBox.setItems(pdfFiles);

        fieldNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        fieldsTableView.setItems(fieldNames);

    // Deshabilitar mientras esté ocupado o no haya campos cargados
    exportButton.disableProperty().bind(Bindings.or(Bindings.isEmpty(fieldNames), busy));
    exportAllButton.disableProperty().bind(busy);
    exportAllInOneButton.disableProperty().bind(busy);
        pdfComboBox.disableProperty().bind(busy);
        if (exportAllJsonButton != null) {
            exportAllJsonButton.disableProperty().bind(busy);
        }
    }

    private void cargarListaDePdfs() {
        List<PdfInfo> allPdfs = Stream.of(
                new PdfInfo("anexo_delegado_prevencion.pdf", "/Delegados/"),
                new PdfInfo("autorizacion.pdf", "/Delegados/"),
                new PdfInfo("calendario_delegado.pdf", "/Delegados/"),
                new PdfInfo("modelo_3.pdf", "/Delegados/"),
                new PdfInfo("modelo_5_1.pdf", "/Delegados/"),
                new PdfInfo("modelo_5_2_conclusion.pdf", "/Delegados/"),
                new PdfInfo("modelo_5_2_proceso.pdf", "/Delegados/"),
                new PdfInfo("modelo_9.pdf", "/Delegados/"),
                new PdfInfo("preaviso.pdf", "/Delegados/"),
                new PdfInfo("calendario_comite.pdf", "/Comite/"),
                new PdfInfo("modelo_4_Especialistas.pdf", "/Comite/"),
                new PdfInfo("modelo_4_Tecnicos.pdf", "/Comite/"),
                new PdfInfo("modelo_4_Unico.pdf", "/Comite/"),
                new PdfInfo("modelo_6_1_Especialistas.pdf", "/Comite/"),
                new PdfInfo("modelo_6_1_Tecnicos.pdf", "/Comite/"),
                new PdfInfo("modelo_6_1_Unico.pdf", "/Comite/"),
                new PdfInfo("modelo_6_2_Especialistas.pdf", "/Comite/"),
                new PdfInfo("modelo_6_2_Tecnicos.pdf", "/Comite/"),
                new PdfInfo("modelo_6_2_Unico.pdf", "/Comite/"),
                new PdfInfo("modelo_6_3_Especialistas.pdf", "/Comite/"),
                new PdfInfo("modelo_6_3_Tecnicos.pdf", "/Comite/"),
                new PdfInfo("modelo_6_3_Unico.pdf", "/Comite/"),
                new PdfInfo("modelo_7_1.pdf", "/Comite/"),
                new PdfInfo("modelo_7_2.pdf", "/Comite/"),
                new PdfInfo("modelo_7_3_acta_global.pdf", "/Comite/"),
                new PdfInfo("modelo_7_3_anexo.pdf", "/Comite/"),
                new PdfInfo("modelo_7_3_proceso.pdf", "/Comite/")
        ).sorted(Comparator.comparing(PdfInfo::name)).toList();

        pdfFiles.setAll(allPdfs);
    }

    @FXML
    private void handlePdfSelection() {
        PdfInfo selectedPdf = pdfComboBox.getValue();
        if (selectedPdf == null) return;

        Task<List<String>> task = new Task<>() {
            @Override
            protected List<String> call() throws IOException {
                String fullPath = selectedPdf.resourcePath() + selectedPdf.name();
                return PdfFieldReader.getFormFieldNames(fullPath);
            }
        };

        task.setOnRunning(e -> {
            loadingIndicator.setVisible(true);
            setButtonsDisabled(true);
            fieldNames.clear();
        });

        task.setOnSucceeded(e -> {
            fieldNames.setAll(task.getValue());
            logger.info("Mostrando {} campos para el formulario '{}'", fieldNames.size(), selectedPdf.name());
            loadingIndicator.setVisible(false);
            setButtonsDisabled(false);
        });

        task.setOnFailed(e -> {
            loadingIndicator.setVisible(false);
            setButtonsDisabled(false);
            Throwable ex = task.getException();
            logger.error("Error al leer los campos del PDF: {}", selectedPdf.name(), ex);
            AlertManager.mostrarAlertaError("Error de Lectura", "No se pudo leer el archivo PDF. Verifique que el archivo no esté dañado y se encuentre en la ruta correcta.");
        });

        new Thread(task).start();
    }

    @FXML
    private void handleExportar() {
        PdfInfo selectedPdf = pdfComboBox.getValue();
        if (selectedPdf == null || fieldNames.isEmpty()) {
            AlertManager.mostrarAlertaAdvertencia("Sin datos", "No hay campos que exportar. Por favor, seleccione un formulario primero.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Lista de Campos");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de Texto", "*.txt"));
        String initialFileName = "campos_" + DirectorioManager.sanitizarNombre(selectedPdf.name().replace(".pdf", "")) + ".txt";
        fileChooser.setInitialFileName(initialFileName);

        File file = fileChooser.showSaveDialog(pdfComboBox.getScene().getWindow());
        if (file != null) {
            escribirContenidoEnArchivo(file, String.join("\n", fieldNames));
        }
    }

    @FXML
    private void handleExportarTodo() {
        Task<Void> exportTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Path targetDir = Paths.get(System.getProperty("user.home"), "Documents", "Elecciones", "Listado Campos");
                Files.createDirectories(targetDir);

                for (PdfInfo pdf : pdfFiles) {
                    updateMessage("Procesando: " + pdf.name());
                    String resourcePath = pdf.resourcePath() + pdf.name();
                    List<String> fields = PdfFieldReader.getFormFieldNames(resourcePath);
                    String outputFileName = "campos_" + DirectorioManager.sanitizarNombre(pdf.name().replace(".pdf", "")) + ".txt";
                    File outputFile = targetDir.resolve(outputFileName).toFile();
                    escribirContenidoEnArchivo(outputFile, String.join("\n", fields));
                }
                return null;
            }
        };

        exportTask.setOnRunning(e -> setButtonsDisabled(true));
        exportTask.setOnSucceeded(e -> {
            setButtonsDisabled(false);
            AlertManager.mostrarAlertaInformacion("Exportación Completa", "Todos los listados de campos han sido exportados a la carpeta 'Documentos/Elecciones/Listado Campos'.");
        });
        exportTask.setOnFailed(e -> {
            setButtonsDisabled(false);
            logger.error("Fallo la exportación masiva de campos.", exportTask.getException());
            AlertManager.mostrarAlertaError("Error de Exportación", "Ocurrió un error durante la exportación masiva.");
        });

        new Thread(exportTask).start();
    }

    @FXML
    public void handleExportAllInOne() {
        Task<Void> exportTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Path targetDir = Paths.get(System.getProperty("user.home"), "Documents", "Elecciones", "Listado Campos");
                Files.createDirectories(targetDir);
                File outputFile = targetDir.resolve("listado_completo_campos.txt").toFile();

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                    for (int i = 0; i < pdfFiles.size(); i++) {
                        PdfInfo pdf = pdfFiles.get(i);
                        updateMessage("Procesando: " + pdf.name());
                        updateProgress(i + 1, pdfFiles.size());

                        writer.write("--- CAMPOS DE: " + pdf.name() + " ---");
                        writer.newLine();
                        writer.newLine();

                        String fullPath = pdf.resourcePath() + pdf.name();
                        List<String> fields = PdfFieldReader.getFormFieldNames(fullPath);
                        for (String field : fields) {
                            writer.write(field);
                            writer.newLine();
                        }
                        writer.newLine();
                        writer.newLine();
                    }
                } catch (IOException e) {
                    logger.error("Fallo durante la escritura del archivo consolidado.", e);
                    throw e; // Relanzar para que onFailed se active
                }
                return null;
            }
        };

        exportTask.setOnRunning(e -> setButtonsDisabled(true));
        exportTask.setOnSucceeded(e -> {
            setButtonsDisabled(false);
            AlertManager.mostrarAlertaInformacion("Exportación Completa", "El archivo 'listado_completo_campos.txt' se ha guardado correctamente en la carpeta 'Documentos/Elecciones/Listado Campos'.");
        });
        exportTask.setOnFailed(e -> {
            setButtonsDisabled(false);
            logger.error("Fallo la exportación consolidada de campos.", exportTask.getException());
            AlertManager.mostrarAlertaError("Error de Exportación", "Ocurrió un error durante la exportación consolidada.");
        });

        new Thread(exportTask).start();
    }

    private void escribirContenidoEnArchivo(File file, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
            logger.info("Contenido exportado a: {}", file.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Error al exportar el contenido a {}", file.getAbsolutePath(), e);
            AlertManager.mostrarAlertaError("Error de Escritura", "No se pudo guardar el archivo: " + e.getMessage());
        }
    }

    private void setButtonsDisabled(boolean disabled) {
        // Control centralizado de estado ocupado; las vistas están enlazadas a 'busy'
        busy.set(disabled);
    }

    /**
     * Exporta a JSON los campos de todos los formularios disponibles en la vista,
     * consolidándolos en un único archivo JSON.
     * El archivo se guarda en "Documentos/Elecciones/Listado Campos" como listado_completo_campos.json.
     */
    @FXML
    public void onExportToJsonButtonClick() {
        Task<Void> exportTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                record FieldInfo(String name, String type) {}
                record PdfFields(String pdf, List<FieldInfo> fields) {}

                List<PdfFields> all = new java.util.ArrayList<>();

                for (PdfInfo pdfInfo : pdfFiles) {
                    String resourcePath = pdfInfo.resourcePath() + pdfInfo.name();
                    updateMessage("Leyendo: " + pdfInfo.name());

                    try (InputStream is = PdfInspectorController.class.getResourceAsStream(resourcePath);
                         PDDocument pdf = Loader.loadPDF(Objects.requireNonNull(is, "Plantilla no encontrada").readAllBytes())) {

                        PDAcroForm acroForm = pdf.getDocumentCatalog().getAcroForm();
                        List<FieldInfo> info = new java.util.ArrayList<>();
                        if (acroForm != null) {
                            for (PDField field : acroForm.getFieldTree()) {
                                info.add(new FieldInfo(field.getFullyQualifiedName(), extractFieldType(field)));
                            }
                        }
                        all.add(new PdfFields(pdfInfo.name(), info));

                    }
                }

                String json = buildJson(all);

                Path targetDir = Paths.get(System.getProperty("user.home"), "Documents", "Elecciones", "Listado Campos");
                Files.createDirectories(targetDir);
                Path out = targetDir.resolve("listado_completo_campos.json");
                Files.writeString(out, json);
                logger.info("JSON exportado a: {}", out.toAbsolutePath());
                return null;
            }
        };

        exportTask.setOnRunning(e -> setButtonsDisabled(true));
        exportTask.setOnSucceeded(e -> {
            setButtonsDisabled(false);
            AlertManager.mostrarAlertaInformacion("Exportación a JSON", "El archivo JSON se ha generado correctamente en Documentos/Elecciones/Listado Campos.");
        });
        exportTask.setOnFailed(e -> {
            setButtonsDisabled(false);
            logger.error("Error exportando a JSON", exportTask.getException());
            AlertManager.mostrarAlertaError("Error", "No se pudo exportar a JSON: " + exportTask.getException().getMessage());
        });

        new Thread(exportTask).start();
    }

    // Extrae el tipo (FT) desde el diccionario del campo. Devuelve "UNKNOWN" si no está presente.
    private String extractFieldType(PDField field) {
        COSBase base = field.getCOSObject();
        if (base instanceof COSDictionary dict) {
            COSBase ft = dict.getDictionaryObject(COSName.FT);
            if (ft instanceof COSName cosName) {
                return cosName.getName();
            } else if (ft != null) {
                return ft.toString();
            }
        }
        return "UNKNOWN";
    }

    // Serializa a JSON simple sin dependencias externas (con pretty-print básico)
    private String buildJson(List<?> all) {
        // all es List<PdfFields> donde PdfFields es record local { String pdf; List<FieldInfo> fields; }
        String indent = "  ";
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        @SuppressWarnings("unchecked")
        List<Object> list = (List<Object>) all;
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            // Reflection sobre record local
            try {
                String pdfName = (String) obj.getClass().getRecordComponents()[0].getAccessor().invoke(obj);
                @SuppressWarnings("unchecked")
                List<Object> fields = (List<Object>) obj.getClass().getRecordComponents()[1].getAccessor().invoke(obj);
                sb.append(indent).append("{\n");
                sb.append(indent).append(indent).append("\"pdf\": \"").append(jsonEscape(pdfName)).append("\",\n");
                sb.append(indent).append(indent).append("\"fields\": [\n");
                for (int j = 0; j < fields.size(); j++) {
                    Object f = fields.get(j);
                    String name = (String) f.getClass().getRecordComponents()[0].getAccessor().invoke(f);
                    String type = (String) f.getClass().getRecordComponents()[1].getAccessor().invoke(f);
                    sb.append(indent).append(indent).append(indent).append("{")
                      .append("\"name\": \"").append(jsonEscape(name)).append("\", ")
                      .append("\"type\": \"").append(jsonEscape(type)).append("\"}");
                    if (j < fields.size() - 1) sb.append(",");
                    sb.append("\n");
                }
                sb.append(indent).append(indent).append("]\n");
                sb.append(indent).append("}");
                if (i < list.size() - 1) sb.append(",");
                sb.append("\n");
            } catch (Throwable t) {
                logger.error("Error serializando a JSON", t);
            }
        }
        sb.append("]\n");
        return sb.toString();
    }

    private String jsonEscape(String s) {
        if (s == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"' -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\b' -> sb.append("\\b");
                case '\f' -> sb.append("\\f");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default -> {
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
                }
            }
        }
        return sb.toString();
    }
}
