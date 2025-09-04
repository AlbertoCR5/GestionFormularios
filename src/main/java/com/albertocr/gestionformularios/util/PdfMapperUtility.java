package com.albertocr.gestionformularios.util;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDRadioButton;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.PDResources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilidad centralizada para:
 * - Localizar/generar un JSON consolidado de campos PDF (nombre, tipo y export values de botones)
 * - Seleccionar el mapeo objetivo por contexto (Comité/Delegados)
 * - Rellenar un PDF usando un proveedor de valores y el mapeo cargado
 *
 * Sin dependencias externas de JSON: se usa una serialización/parseo mínimo manual.
 */
public final class PdfMapperUtility {

    /** Contenedor inmutable de información de un campo PDF. */
    public record FieldInfo(String name, String type, String exportValue) {}

    /** Contenedor inmutable del mapeo de un PDF (plantilla) a sus campos. */
    public record Mapping(String pdfName, List<FieldInfo> fields) {}

    private PdfMapperUtility() {
        // Utility
    }

    /**
     * Ruta del JSON consolidado bajo Documents/Elecciones/Listado Campos/Listado JSON.
     */
    public static Path getConsolidatedJsonPath() throws IOException {
        Path raiz = DirectorioManager.crearDirectorioRaiz();
        Path dir = raiz.resolve(Paths.get("Listado Campos", "Listado JSON"));
        DirectorioManager.ensureHiddenDirectory(dir);
        return dir.resolve("Listado Campos y Tipo JSON.json");
    }

    /**
     * Carga el JSON consolidado si existe; si no, lo genera escaneando plantillas conocidas de Comité y Delegados.
     */
    public static List<Mapping> loadOrGenerateMappings() throws IOException {
        Path path = getConsolidatedJsonPath();
        if (Files.exists(path)) {
            return readMappingsFromJson(path);
        }
        List<Mapping> mappings = scanAllMappings();
        saveMappingsToJson(mappings, path);
        return mappings;
    }

    /** Escanea recursos de /Comite y /Delegados para construir el listado de campos. */
    public static List<Mapping> scanAllMappings() throws IOException {
        List<Mapping> result = new ArrayList<>();
        // Listas conocidas de plantillas (evitamos listar classpath de forma genérica dentro del JAR)
        String[] comite = new String[]{
                "calendario_comite.pdf",
                "modelo_4_Especialistas.pdf",
                "modelo_4_Tecnicos.pdf",
                "modelo_4_Unico.pdf",
                "modelo_6_1_Especialistas.pdf",
                "modelo_6_1_Tecnicos.pdf",
                "modelo_6_1_Unico.pdf",
                "modelo_6_2_Especialistas.pdf",
                "modelo_6_2_Tecnicos.pdf",
                "modelo_6_2_Unico.pdf",
                "modelo_6_3_Especialistas.pdf",
                "modelo_6_3_Tecnicos.pdf",
                "modelo_6_3_Unico.pdf",
                "modelo_7_1.pdf",
                "modelo_7_2.pdf",
                "modelo_7_3_acta_global.pdf",
                "modelo_7_3_anexo.pdf",
                "modelo_7_3_proceso.pdf"
        };
        String[] delegados = new String[]{
                "anexo_delegado_prevencion.pdf",
                "autorizacion.pdf",
                "calendario_delegado.pdf",
                "modelo_3.pdf",
                "modelo_5_1.pdf",
                "modelo_5_2_conclusion.pdf",
                "modelo_5_2_proceso.pdf",
                "modelo_9.pdf",
                "preaviso.pdf"
        };
        result.addAll(scanMappingsUnder("/Comite/", comite));
        result.addAll(scanMappingsUnder("/Delegados/", delegados));
        return result;
    }

    private static List<Mapping> scanMappingsUnder(String resourcePrefix, String[] filenames) throws IOException {
        List<Mapping> list = new ArrayList<>();
        for (String name : filenames) {
            String res = resourcePrefix + name;
            try (InputStream in = PdfMapperUtility.class.getResourceAsStream(res)) {
                if (in == null) continue;
                try (PDDocument doc = Loader.loadPDF(in.readAllBytes())) {
                    PDAcroForm acro = doc.getDocumentCatalog().getAcroForm();
                    if (acro == null) continue;
                    List<FieldInfo> fields = new ArrayList<>();
                    for (PDField f : acro.getFields()) {
                        String fname = f.getFullyQualifiedName();
                        String ftype = f.getCOSObject().getNameAsString(COSName.FT);
                        if (ftype == null) ftype = "";
                        String export = "";
                        try {
                            if (f instanceof PDCheckBox cb) {
                                export = cb.getOnValue();
                                if (export == null) export = "Yes"; // convencional
                            } else if (f instanceof PDRadioButton rb) {
                                List<String> exps = rb.getExportValues();
                                if (exps != null && !exps.isEmpty()) {
                                    export = String.join(",", exps);
                                }
                            }
                        } catch (Exception ignore) { }
                        fields.add(new FieldInfo(fname, ftype, export));
                    }
                    if (!fields.isEmpty()) list.add(new Mapping(name, fields));
                }
            }
        }
        return list;
    }

    /** Lee mapeos desde JSON. Soporta campos con o sin exportValue. */
    public static List<Mapping> readMappingsFromJson(Path jsonPath) throws IOException {
        String content = Files.readString(jsonPath);
        String trimmed = content.trim();
        if (!trimmed.startsWith("[")) return Collections.emptyList();
        List<Mapping> result = new ArrayList<>();

        // Partir por objetos de primer nivel con "pdf": usamos un patrón tolerante
        Pattern objPat = Pattern.compile("\\{[^}]*\\\"pdf\\\"[^}]*}\\s*(,|])");
        Matcher objMat = objPat.matcher(trimmed + "]");
        while (objMat.find()) {
            String obj = objMat.group();
            Matcher mPdf = Pattern.compile("\\\"pdf\\\"\\s*:\\s*\\\"([^\\\"]+)\\\"").matcher(obj);
            String pdfName = null;
            if (mPdf.find()) pdfName = mPdf.group(1);

            List<FieldInfo> fields = new ArrayList<>();
            Matcher mFields = Pattern.compile(
                    "\\{\\s*\\\"name\\\"\\s*:\\s*\\\"([^\\\"]+)\\\"\\s*,\\s*\\\"type\\\"\\s*:\\s*\\\"([^\\\"]*)\\\"(\\s*,\\s*\\\"exportValue\\\"\\s*:\\s*\\\"([^\\\"]*)\\\")?\\s*}\\s*"
            ).matcher(obj);
            while (mFields.find()) {
                String name = mFields.group(1);
                String type = mFields.group(2);
                String export = null;
                if (mFields.groupCount() >= 4) export = mFields.group(4);
                fields.add(new FieldInfo(name, type, export == null ? "" : export));
            }

            if (pdfName != null && !fields.isEmpty()) {
                result.add(new Mapping(pdfName, fields));
            }
        }
        return result;
    }

    /** Guarda el mapeo a JSON, incluyendo exportValue cuando exista. */
    public static void saveMappingsToJson(List<Mapping> mappings, Path destinoJson) throws IOException {
        String json = buildJson(mappings);
        Files.writeString(destinoJson, json);
    }

    private static String buildJson(List<Mapping> mappings) {
        String indent = "  ";
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < mappings.size(); i++) {
            Mapping m = mappings.get(i);
            sb.append(indent).append("{\n");
            sb.append(indent).append(indent).append("\"pdf\": \"").append(escape(m.pdfName())).append("\",\n");
            sb.append(indent).append(indent).append("\"fields\": [\n");
            List<FieldInfo> fields = m.fields();
            for (int j = 0; j < fields.size(); j++) {
                FieldInfo f = fields.get(j);
                sb.append(indent).append(indent).append(indent).append("{")
                  .append("\"name\": \"").append(escape(f.name())).append("\", ")
                  .append("\"type\": \"").append(escape(f.type())).append("\"");
                if (f.exportValue() != null && !f.exportValue().isEmpty()) {
                    sb.append(", \"exportValue\": \"").append(escape(f.exportValue())).append("\"");
                }
                sb.append("}");
                if (j < fields.size() - 1) sb.append(",");
                sb.append("\n");
            }
            sb.append(indent).append(indent).append("]\n");
            sb.append(indent).append("}");
            if (i < mappings.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("]\n");
        return sb.toString();
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    /** Selección para Comité, en base al tipo de colegio. */
    public static Mapping selectComiteMapping(List<Mapping> mappings, String tipoColegio) {
        if (mappings == null || mappings.isEmpty()) return null;
        String preferido = "Colegio Único".equalsIgnoreCase(tipoColegio) ? "modelo_6_1_Unico.pdf" : "modelo_6_1_Especialistas.pdf";
        for (Mapping m : mappings) if (m.pdfName().equalsIgnoreCase(preferido)) return m;
        for (String alt : new String[]{"modelo_7_3_acta_global.pdf", "modelo_7_1.pdf"}) {
            for (Mapping m : mappings) if (m.pdfName().equalsIgnoreCase(alt)) return m;
        }
        return mappings.get(0);
    }

    /** Selección por defecto para Delegados: Modelo 5_1. */
    public static Mapping selectDelegadosMapping(List<Mapping> mappings) {
        if (mappings == null || mappings.isEmpty()) return null;
        for (Mapping m : mappings) if (m.pdfName().equalsIgnoreCase("modelo_5_1.pdf")) return m;
        for (String alt : new String[]{"modelo_5_2_proceso.pdf", "modelo_5_2_conclusion.pdf"}) {
            for (Mapping m : mappings) if (m.pdfName().equalsIgnoreCase(alt)) return m;
        }
        return mappings.get(0);
    }

    /**
     * Rellena un PDF existente en pdfPath con los valores proporcionados por valueProvider.
     * Maneja campos de texto y botones (checkbox/radio) usando exportValue cuando sea necesario.
     */
    public static void fillPdfUsingMapping(Path pdfPath, Mapping mapping, Function<String, String> valueProvider) throws IOException {
        Objects.requireNonNull(pdfPath, "pdfPath");
        Objects.requireNonNull(mapping, "mapping");
        Objects.requireNonNull(valueProvider, "valueProvider");

        // Operación atómica: trabajar sobre una copia temporal y reemplazar al final
        Path parent = pdfPath.getParent();
        if (parent == null) parent = Paths.get(".");
        Path tempCopy = Files.createTempFile(parent, pdfPath.getFileName().toString() + ".", ".tmp");
        Files.copy(pdfPath, tempCopy, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        boolean success = false;
        try (PDDocument document = Loader.loadPDF(Files.readAllBytes(tempCopy))) {
            PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
            if (acroForm == null) {
                document.save(tempCopy.toFile());
            } else {
                // Asegurar fuente incrustada por defecto
                ensureDefaultAcroFormFont(document, acroForm);
                acroForm.setNeedAppearances(true);

                for (FieldInfo fi : mapping.fields()) {
                    PDField field = acroForm.getField(fi.name());
                    if (field == null) continue;
                    String desired = safe(valueProvider.apply(fi.name()));
                    if (desired == null) continue;

                    String type = fi.type() == null ? "" : fi.type();
                    try {
                        if ("Btn".equalsIgnoreCase(type)) {
                            applyButtonValue(field, fi, desired);
                        } else {
                            field.setValue(desired);
                        }
                    } catch (Exception e) {
                        // Continuar con el siguiente campo
                    }
                }
                try { acroForm.flatten(); } catch (Exception ignore) { }
                document.save(tempCopy.toFile());
            }
            success = true;
        } finally {
            if (success) {
                // Reemplazo atómico cuando sea posible
                try {
                    Files.move(tempCopy, pdfPath,
                            java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                            java.nio.file.StandardCopyOption.ATOMIC_MOVE);
                } catch (IOException ex) {
                    // Si ATOMIC_MOVE no está soportado, intentar un move normal
                    Files.move(tempCopy, pdfPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                }
            } else {
                try { Files.deleteIfExists(tempCopy); } catch (IOException ignore) { }
            }
        }
    }

    /** Inserta y configura una fuente incrustada por defecto para el AcroForm. */
    private static void ensureDefaultAcroFormFont(PDDocument document, PDAcroForm acroForm) {
        try {
            PDResources resources = acroForm.getDefaultResources();
            if (resources == null) {
                resources = new PDResources();
                acroForm.setDefaultResources(resources);
            }

            // 1) Intentar cargar Helvetica-Bold desde el sistema de archivos o recursos, incrustando subconjunto
            PDFont font = tryLoadHelveticaBold(document);
            // 2) Fallback a LiberationSans si no se encuentra Helvetica
            if (font == null) {
                font = tryLoadEmbeddedFont(document,
                        "/fonts/LiberationSans-Regular.ttf",
                        "/fonts/LiberationSans-Regular.TTF",
                        "/fonts/LiberationSans-Bold.ttf");
            }
            if (font == null) return; // si no se puede cargar, no forzar

            // Registrar la fuente en los recursos y preparar apariencia por defecto
            COSName fontName;
            try {
                // PDFBox 3: PDResources#add(PDFont) devuelve nombre
                fontName = resources.add(font);
            } catch (Throwable t) {
                // Fallback: usar un alias estándar
                fontName = COSName.getPDFName("F0");
                try {
                    // Intento de asociación manual, puede no ser necesario según la versión
                    resources.getCOSObject();
                } catch (Throwable ignore) { }
            }
            String alias = fontName != null ? fontName.getName() : "F0";
            // DA: seleccionar fuente y tamaño 10, color negro
            acroForm.setDefaultAppearance("/" + alias + " 10 Tf 0 g");
        } catch (Exception ignore) {
        }
    }

    /**
     * Intenta cargar la fuente Helvetica-Bold (TrueType) desde el sistema de archivos o desde los recursos.
     * Usa incrustación por subconjunto (embedSubset=true) para optimizar el tamaño del PDF.
     */
    private static PDFont tryLoadHelveticaBold(PDDocument document) {
        // Candidatos en el sistema de archivos (Windows y relativo al proyecto)
        File[] fileCandidates = new File[] {
                // Ruta absoluta típica en este proyecto (usuario Windows actual)
                new File(System.getProperty("user.home") + "\\Documents\\GitHub\\GestionFormularios\\src\\main\\resources\\fonts\\helvetica-bold.ttf"),
                // Ruta relativa cuando se ejecuta desde la raíz del proyecto
                new File("src\\main\\resources\\fonts\\helvetica-bold.ttf"),
                new File("src/main/resources/fonts/helvetica-bold.ttf")
        };
        for (File f : fileCandidates) {
            try {
                if (f.exists() && f.isFile()) {
                    try (java.io.InputStream fis = new java.io.FileInputStream(f)) {
                        return PDType0Font.load(document, fis, true); // true: incrustación por subconjunto
                    }
                }
            } catch (Exception ignore) { }
        }

        // Intentar desde recursos del classpath
        try (InputStream in = PdfMapperUtility.class.getResourceAsStream("/fonts/helvetica-bold.ttf")) {
            if (in != null) {
                return PDType0Font.load(document, in, true); // true: incrustación por subconjunto
            }
        } catch (Exception ignore) { }
        return null;
    }

    /** Intenta cargar una fuente TTF embebida desde varios recursos. */
    private static PDFont tryLoadEmbeddedFont(PDDocument document, String... resourcePaths) {
        for (String path : resourcePaths) {
            try (InputStream in = PdfMapperUtility.class.getResourceAsStream(path)) {
                if (in == null) continue;
                return PDType0Font.load(document, in, true);
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    private static void applyButtonValue(PDField field, FieldInfo fi, String desired) throws IOException {
        String desiredLower = desired.toLowerCase(Locale.ROOT);
        // Interpretar boolean-like
        boolean truthy = desiredLower.equals("true") || desiredLower.equals("1") || desiredLower.equals("yes") || desiredLower.equals("si") || desiredLower.equals("sí");
        if (field instanceof PDCheckBox cb) {
            String on = fi.exportValue() == null || fi.exportValue().isEmpty() ? cb.getOnValue() : fi.exportValue();
            if (on == null || on.isBlank()) on = "Yes";
            cb.setValue(truthy || desired.equalsIgnoreCase(on) ? on : "Off");
            return;
        }
        if (field instanceof PDRadioButton rb) {
            String export = fi.exportValue() == null ? "" : fi.exportValue();
            List<String> options = export.isEmpty() ? rb.getExportValues() : List.of(export.split(","));
            if (options != null && !options.isEmpty()) {
                // Si desired coincide con una opción, usarla; si no, si es truthy elegir la primera
                String match = options.stream().filter(opt -> opt.equalsIgnoreCase(desired)).findFirst().orElse(null);
                rb.setValue(match != null ? match : (truthy ? options.get(0) : ""));
            }
        }
        // Otros botones (push) se ignoran
    }

    private static String safe(String s) { return (s == null) ? null : s; }

    /**
     * Variante utilitaria: copiar una plantilla desde classpath a destino y rellenarla de forma atómica.
     */
    public static void copyAndFillFromTemplate(String templateResource, Path outputPath, Mapping mapping, Function<String, String> valueProvider) throws IOException {
        Objects.requireNonNull(templateResource, "templateResource");
        Objects.requireNonNull(outputPath, "outputPath");
        Objects.requireNonNull(mapping, "mapping");
        Objects.requireNonNull(valueProvider, "valueProvider");
        Files.createDirectories(outputPath.getParent());

        // Copia inicial a archivo destino (si no existe) para luego operar atómicamente con el método principal
        if (Files.notExists(outputPath)) {
            try (InputStream in = PdfMapperUtility.class.getResourceAsStream(templateResource)) {
                if (in == null) throw new IOException("No se encuentra la plantilla: " + templateResource);
                Files.write(outputPath, in.readAllBytes());
            }
        }
        // Rellenar sobre una copia temporal y reemplazar
        fillPdfUsingMapping(outputPath, mapping, valueProvider);
    }

    /**
     * Genera un PDF a partir de una plantilla en classpath, rellenando campos y refrescando apariencias
     * (sin aplanar), guardando en un archivo temporal y reemplazando el destino al finalizar.
     */
    public static void generateFromTemplateWithRefresh(String templateResource, Path outputPath, Mapping mapping, Function<String, String> valueProvider) throws IOException {
        Objects.requireNonNull(templateResource, "templateResource");
        Objects.requireNonNull(outputPath, "outputPath");
        Objects.requireNonNull(mapping, "mapping");
        Objects.requireNonNull(valueProvider, "valueProvider");
        Files.createDirectories(outputPath.getParent());

        Path parent = outputPath.getParent();
        Path tempFile = Files.createTempFile(parent, outputPath.getFileName().toString() + ".", ".tmp");

        try (InputStream in = PdfMapperUtility.class.getResourceAsStream(templateResource)) {
            if (in == null) throw new IOException("No se encuentra la plantilla: " + templateResource);
            try (PDDocument document = Loader.loadPDF(in.readAllBytes())) {
                PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
                if (acroForm != null) {
                    ensureDefaultAcroFormFont(document, acroForm);
                    acroForm.setNeedAppearances(true);
                }
                if (acroForm != null) {
                    for (FieldInfo fi : mapping.fields()) {
                        PDField field = acroForm.getField(fi.name());
                        if (field == null) continue;
                        String desired = safe(valueProvider.apply(fi.name()));
                        if (desired == null) continue;
                        String type = fi.type() == null ? "" : fi.type();
                        try {
                            if ("Btn".equalsIgnoreCase(type)) {
                                applyButtonValue(field, fi, desired);
                            } else {
                                field.setValue(desired);
                            }
                        } catch (Exception ignore) { }
                    }
                    try { acroForm.refreshAppearances(); } catch (Exception ignore) { }
                }
                document.save(tempFile.toFile());
            }
        }

        // Reemplazo: eliminar original (si existe) y renombrar temporal
        try { Files.deleteIfExists(outputPath); } catch (IOException ignore) { }
        Files.move(tempFile, outputPath);
    }
}
