package com.albertocr.gestionformularios.util;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDRadioButton;

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
        try (PDDocument document = Loader.loadPDF(Files.readAllBytes(pdfPath))) {
            PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
            if (acroForm == null) {
                document.save(pdfPath.toFile());
                return;
            }
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
                    // No romper el proceso por un campo rebelde; continuar
                }
            }
            try { acroForm.flatten(); } catch (Exception ignore) { }
            document.save(pdfPath.toFile());
        }
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
}
