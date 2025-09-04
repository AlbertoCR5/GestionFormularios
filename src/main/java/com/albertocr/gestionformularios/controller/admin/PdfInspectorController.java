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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.util.stream.Stream;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDRadioButton;
import org.apache.pdfbox.pdmodel.interactive.form.PDPushButton;

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
                Path baseDocs = Paths.get(System.getProperty("user.home"), "Documents");
                Path targetDir = baseDocs.resolve("Elecciones").resolve("Listado Campos").resolve("Listado TXT");
                com.albertocr.gestionformularios.util.DirectorioManager.ensureHiddenDirectory(targetDir);

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
            AlertManager.mostrarAlertaInformacion("Exportación Completa", "Todos los listados de campos han sido exportados a 'Documentos/Elecciones/Listado Campos/Listado TXT'.");
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
                Path baseDocs = Paths.get(System.getProperty("user.home"), "Documents");
                Path targetDir = baseDocs.resolve("Elecciones").resolve("Listado Campos").resolve("Listado TXT");
                com.albertocr.gestionformularios.util.DirectorioManager.ensureHiddenDirectory(targetDir);
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
            AlertManager.mostrarAlertaInformacion("Exportación Completa", "El archivo 'listado_completo_campos.txt' se ha guardado correctamente en 'Documentos/Elecciones/Listado Campos/Listado TXT'.");
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
                record FieldInfo(String name, String type, String exportValue) {}
                record PdfFields(String pdf, List<FieldInfo> fields) {}

                // Construir el listado para TODOS los PDFs disponibles, leyendo nombre y tipo desde el propio PDF
                List<PdfFields> all = new java.util.ArrayList<>();
                for (PdfInfo pdf : pdfFiles) {
                    String resourcePath = pdf.resourcePath() + pdf.name();
                    try (java.io.InputStream in = getClass().getResourceAsStream(resourcePath)) {
                        if (in == null) continue;
                        try (PDDocument doc = Loader.loadPDF(in.readAllBytes())) {
                            PDAcroForm acro = doc.getDocumentCatalog().getAcroForm();
                            if (acro == null) continue;
                            List<FieldInfo> fields = new java.util.ArrayList<>();
                            for (PDField f : acro.getFields()) {
                                String name = f.getFullyQualifiedName();
                                String type = f.getCOSObject().getNameAsString(COSName.FT);
                                if (type == null) type = "";
                                String exportVal = "";
                                // Si es botón (checkbox, radio, push), intentar extraer valor de exportación
                                if (f instanceof PDCheckBox cb) {
                                    try { exportVal = cb.getOnValue(); } catch (Exception ignore) { }
                                } else if (f instanceof PDRadioButton rb) {
                                    try {
                                        java.util.List<String> vals = rb.getExportValues();
                                        if (vals != null && !vals.isEmpty()) exportVal = String.join(",", vals);
                                    } catch (Exception ignore) { }
                                } else if (f instanceof PDPushButton) {
                                    exportVal = ""; // sin valor de exportación útil
                                }
                                fields.add(new FieldInfo(name, type, exportVal));
                            }
                            all.add(new PdfFields(pdf.name(), fields));
                        }
                    }
                }

                // Serializar a JSON consolidado
                String json = buildJson(all);

                // Guardar en la ruta solicitada: Documents/Elecciones/Listado Campos/Listado JSON
                Path baseDocs = Paths.get(System.getProperty("user.home"), "Documents");
                Path outDir = baseDocs.resolve("Elecciones").resolve("Listado Campos").resolve("Listado JSON");
                com.albertocr.gestionformularios.util.DirectorioManager.ensureHiddenDirectory(outDir);
                Path out = outDir.resolve("Listado Campos y Tipo JSON.json");
                Files.writeString(out, json);

                logger.info("JSON consolidado exportado en: {}", out.toAbsolutePath());
                return null;
            }
        };

        exportTask.setOnRunning(e -> setButtonsDisabled(true));
        exportTask.setOnSucceeded(e -> {
            setButtonsDisabled(false);
            AlertManager.mostrarAlertaInformacion("Exportación a JSON", "Se ha generado el JSON consolidado en 'Documentos/Elecciones/Listado Campos/Listado JSON'.");
        });
        exportTask.setOnFailed(e -> {
            setButtonsDisabled(false);
            logger.error("Error exportando a JSON", exportTask.getException());
            AlertManager.mostrarAlertaError("Error", "No se pudo exportar a JSON: " + exportTask.getException().getMessage());
        });

        new Thread(exportTask).start();
    }

    // Extrae el tipo (FT) desde el diccionario del campo. Devuelve "UNKNOWN" si no está presente.
    // Nota: el tipo de campo ahora proviene del JSON de mapeo, ya no escaneamos el PDF.

    /**
     * Carga un mapeo de campos desde un archivo JSON.
     * Formatos soportados:
     *  - Objeto simple: { "campo1": "tipo1", "campo2": "tipo2" }
     *  - Array de objetos: [ {"name":"campo1","type":"tipo1"}, ... ]
     * Devuelve un LinkedHashMap preservando el orden de aparición.
     */
    @SuppressWarnings("unused")
    private Map<String, String> loadFieldMapFromJson(String jsonFilePath) throws IOException {
        String content = Files.readString(Path.of(jsonFilePath));
        Map<String, String> result = new java.util.LinkedHashMap<>();

        String trimmed = content.trim();
        // Caso 1: Array de objetos con name/type
        if (trimmed.startsWith("[")) {
            java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\{\\s*\\\"name\\\"\\s*:\\s*\\\"([^\\\"]+)\\\"\\s*,\\s*\\\"type\\\"\\s*:\\s*\\\"([^\\\"]*)\\\"\\s*}\\s*");
            java.util.regex.Matcher m = p.matcher(trimmed);
            while (m.find()) {
                result.put(m.group(1), m.group(2));
            }
            return result;
        }

        // Caso 2: Objeto simple campo->tipo (no anidado)
        if (trimmed.startsWith("{")) {
            // Quitar llaves externas
            String body = trimmed.substring(1, trimmed.lastIndexOf('}'));
            // Dividir por comas de primer nivel. Aproximación simple.
            int depth = 0;
            StringBuilder token = new StringBuilder();
            java.util.List<String> pairs = new java.util.ArrayList<>();
            for (int i = 0; i < body.length(); i++) {
                char c = body.charAt(i);
                if (c == '"') {
                    token.append(c);
                    i++;
                    while (i < body.length()) {
                        char cc = body.charAt(i);
                        token.append(cc);
                        if (cc == '"' && body.charAt(i - 1) != '\\') break;
                        i++;
                    }
                    continue;
                }
                if (c == '{' || c == '[') depth++;
                if (c == '}' || c == ']') depth--;
                if (c == ',' && depth == 0) {
                    pairs.add(token.toString());
                    token.setLength(0);
                } else {
                    token.append(c);
                }
            }
            if (token.length() > 0) pairs.add(token.toString());

            java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\s*\\\"([^\\\"]+)\\\"\\s*:\\s*\\\"([^\\\"]*)\\\"\\s*");
            for (String pair : pairs) {
                java.util.regex.Matcher m = p.matcher(pair);
                if (m.matches()) {
                    result.put(m.group(1), m.group(2));
                }
            }
        }

        return result;
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
                    // Acceso por nombre de componente para soportar exportValue opcional
                    var comps = f.getClass().getRecordComponents();
                    String name = null, type = null, exportValue = null;
                    for (var c : comps) {
                        Object val = c.getAccessor().invoke(f);
                        switch (c.getName()) {
                            case "name" -> name = (String) val;
                            case "type" -> type = (String) val;
                            case "exportValue" -> exportValue = (String) val;
                        }
                    }
                    sb.append(indent).append(indent).append(indent).append("{")
                      .append("\"name\": \"").append(jsonEscape(name != null ? name : "")).append("\", ")
                      .append("\"type\": \"").append(jsonEscape(type != null ? type : "")).append("\"");
                    if (exportValue != null && !exportValue.isBlank()) {
                        sb.append(", \"exportValue\": \"").append(jsonEscape(exportValue)).append("\"");
                    }
                    sb.append("}");
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
