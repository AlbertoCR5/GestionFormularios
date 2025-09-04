package com.albertocr.gestionformularios.util;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDRadioButton;
import org.apache.pdfbox.pdmodel.interactive.form.PDVariableText;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilidad estática para rellenar PDFs a partir de un mapeo JSON por plantilla.
 * - Busca mapeos en classpath (/mapeos/<templateName>.json) y en carpeta de usuario (Documentos/Elecciones/Mapeos).
 * - Si no existe el mapeo, lo genera a partir del documento y lo guarda en la carpeta de usuario.
 * - Rellena campos de texto y botones (checkbox/radio) respetando exportValue.
 */
public final class PdfFillUtility {

    /** Contenedor de cada campo en el mapeo. */
    public record FieldMapping(String name, String type, String exportValue) {}
    /** Contenedor del mapeo por plantilla. */
    public record TemplateMapping(String template, List<FieldMapping> fields) {}

    private PdfFillUtility() {}

    /**
     * Punto de entrada principal: asegura el mapeo para la plantilla y aplica los valores del mapa de datos.
     * La incrustación de fuente Helvetica-Bold se aplica automáticamente.
     *
     * @param document     Documento PDF abierto (plantilla cargada).
     * @param templateName Nombre de la plantilla (por ejemplo, "modelo_5_1.pdf").
     * @param data         Valores a aplicar, indexados por nombre de campo del PDF.
     */
    public static void fillPdf(PDDocument document, String templateName, Map<String, Object> data) throws IOException {
        Objects.requireNonNull(document, "document");
        Objects.requireNonNull(templateName, "templateName");
        Objects.requireNonNull(data, "data");

        PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
        if (acroForm == null) return;

        // Fuente incrustada por defecto
        ensureHelveticaBold(document, acroForm);
        acroForm.setNeedAppearances(true);

        TemplateMapping mapping = loadOrCreateMapping(document, templateName);
        if (mapping == null || mapping.fields() == null) return;

        for (FieldMapping fm : mapping.fields()) {
            PDField field = acroForm.getField(fm.name());
            if (field == null) continue;
            Object raw = data.get(fm.name());
            if (raw == null) continue;
            String value = String.valueOf(raw);
            if (value == null) continue;
            String type = fm.type() == null ? "" : fm.type();
            try {
                // Asegurar que los campos de texto utilicen la fuente embebida
                if (field instanceof PDVariableText vt) {
                    String da = acroForm.getDefaultAppearance();
                    if (da != null && !da.isBlank()) {
                        try { vt.setDefaultAppearance(da); } catch (Exception ignore) { }
                    }
                }
                if ("Btn".equalsIgnoreCase(type)) {
                    applyButtonValue(field, fm, value);
                } else {
                    field.setValue(value);
                }
            } catch (Exception ignore) { }
        }
    }

    // --- Mapping management ---

    private static TemplateMapping loadOrCreateMapping(PDDocument document, String templateName) throws IOException {
        String jsonFileName = templateName.endsWith(".json") ? templateName : templateName.replace(".pdf", "") + ".json";

        // 1) Intentar en classpath
        try (InputStream in = PdfFillUtility.class.getResourceAsStream("/mapeos/" + jsonFileName)) {
            if (in != null) return readMapping(in);
        }

        // 2) Intentar en carpeta de usuario
        Path userPath = getUserMappingsDir().resolve(jsonFileName);
        if (Files.exists(userPath)) {
            try (InputStream in = Files.newInputStream(userPath)) {
                return readMapping(in);
            }
        }

        // 3) No existe: generar desde el documento y guardar en usuario
        TemplateMapping generated = generateMappingFromDocument(document, templateName);
        saveMapping(generated, userPath);
        return generated;
    }

    private static Path getUserMappingsDir() throws IOException {
        Path raiz = DirectorioManager.crearDirectorioRaiz();
        Path dir = raiz.resolve("Mapeos");
        Files.createDirectories(dir);
        return dir;
    }

    private static TemplateMapping generateMappingFromDocument(PDDocument document, String templateName) throws IOException {
        PDAcroForm acro = document.getDocumentCatalog().getAcroForm();
        List<FieldMapping> fields = new ArrayList<>();
        if (acro != null) {
            for (PDField f : acro.getFields()) {
                String fname = f.getFullyQualifiedName();
                String ftype = f.getCOSObject().getNameAsString(org.apache.pdfbox.cos.COSName.FT);
                if (ftype == null) ftype = "";
                String export = "";
                try {
                    if (f instanceof PDCheckBox cb) {
                        export = cb.getOnValue();
                        if (export == null) export = "Yes";
                    } else if (f instanceof PDRadioButton rb) {
                        List<String> exps = rb.getExportValues();
                        if (exps != null && !exps.isEmpty()) export = String.join(",", exps);
                    }
                } catch (Exception ignore) { }
                fields.add(new FieldMapping(fname, ftype, export));
            }
        }
        return new TemplateMapping(templateName, fields);
    }

    private static TemplateMapping readMapping(InputStream in) throws IOException {
        String content = new String(in.readAllBytes());
        String trimmed = content.trim();
        // Soportar formato con objeto {"template":"...","fields":[...]}
        String template = null;
        Matcher mTpl = Pattern.compile("\"template\"\s*:\s*\"([^\\\"]+)\"").matcher(trimmed);
        if (mTpl.find()) template = mTpl.group(1);

        List<FieldMapping> fields = new ArrayList<>();
        Matcher mFields = Pattern.compile(
            "\\{\\s*\"name\"\\s*:\\s*\"([^\\\\\"]+)\"\\s*,\\s*\"type\"\\s*:\\s*\"([^\\\\\"]*)\"(\\s*,\\s*\"exportValue\"\\s*:\\s*\"([^\\\\\"]*)\")?\\s*}\\s*"
        ).matcher(trimmed.replaceAll("\n|\r", " "));
        while (mFields.find()) {
            String name = mFields.group(1);
            String type = mFields.group(2);
            String export = (mFields.groupCount() >= 4) ? mFields.group(4) : "";
            fields.add(new FieldMapping(name, type, export == null ? "" : export));
        }

        // Si no hay objeto principal, probar con un array simple [ {name,type,...}, ... ]
        if (template == null) template = "";
        return new TemplateMapping(template, fields);
    }

    private static void saveMapping(TemplateMapping mapping, Path destino) throws IOException {
        Files.createDirectories(destino.getParent());
        String json = buildJson(mapping);
        Files.writeString(destino, json);
    }

    private static String buildJson(TemplateMapping mapping) {
        String indent = "  ";
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append(indent).append("\"template\": \"").append(escape(mapping.template())).append("\",\n");
        sb.append(indent).append("\"fields\": [\n");
        for (int i = 0; i < mapping.fields().size(); i++) {
            FieldMapping f = mapping.fields().get(i);
            sb.append(indent).append(indent).append("{")
              .append("\"name\": \"").append(escape(f.name())).append("\", ")
              .append("\"type\": \"").append(escape(f.type())).append("\"");
            if (f.exportValue() != null && !f.exportValue().isEmpty()) {
                sb.append(", \"exportValue\": \"").append(escape(f.exportValue())).append("\"");
            }
            sb.append("}");
            if (i < mapping.fields().size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append(indent).append("]\n");
        sb.append("}\n");
        return sb.toString();
    }

    private static String escape(String s) { return s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\""); }

    // --- Font & field helpers ---

    private static void ensureHelveticaBold(PDDocument document, PDAcroForm acroForm) {
        try {
            PDResources resources = acroForm.getDefaultResources();
            if (resources == null) {
                resources = new PDResources();
                acroForm.setDefaultResources(resources);
            }
            PDFont font = loadHelveticaBold(document);
            if (font == null) return;
            COSName alias = resources.add(font);
            String name = alias != null ? alias.getName() : "F0";
            acroForm.setDefaultAppearance("/" + name + " 10 Tf 0 g");

            // Además, actualizar DA por-campo para campos de texto existentes
            for (PDField field : acroForm.getFields()) {
                if (field instanceof PDVariableText vt) {
                    try { vt.setDefaultAppearance("/" + name + " 10 Tf 0 g"); } catch (Exception ignore) { }
                }
                try {
                    List<PDAnnotationWidget> widgets = field.getWidgets();
                    if (widgets != null) {
                        for (PDAnnotationWidget w : widgets) {
                            if (w != null && w.getAppearanceCharacteristics() == null) {
                                // No appearance settings to patch here, but ensure the form resources include the font
                                // so regenerated appearances use the embedded font via AcroForm resources.
                                w.getCOSObject(); // touch to ensure serialization
                            }
                        }
                    }
                } catch (Exception ignore) { }
            }

            // Truco: mapear alias 'Helvetica-Bold' al font embebido en recursos del formulario y de cada página
            ensureFontAlias(resources, "Helvetica-Bold", font);
            for (PDPage page : document.getPages()) {
                PDResources pr = page.getResources();
                if (pr == null) { pr = new PDResources(); page.setResources(pr); }
                ensureFontAlias(pr, "Helvetica-Bold", font);
            }
        } catch (Exception ignore) { }
    }

    private static void ensureFontAlias(PDResources resources, String alias, PDFont font) {
        try {
            if (resources == null || font == null || alias == null || alias.isBlank()) return;
            COSDictionary resDict = resources.getCOSObject();
            COSDictionary fontDict = (COSDictionary) resDict.getDictionaryObject(COSName.FONT);
            if (fontDict == null) {
                fontDict = new COSDictionary();
                resDict.setItem(COSName.FONT, fontDict);
            }
            fontDict.setItem(COSName.getPDFName(alias), font.getCOSObject());
        } catch (Exception ignore) { }
    }

    private static PDFont loadHelveticaBold(PDDocument document) {
        // Intentar cargar desde recursos
        try (InputStream in = PdfFillUtility.class.getResourceAsStream("/fonts/helvetica-bold.ttf")) {
            if (in != null) return PDType0Font.load(document, in, true);
        } catch (Exception ignore) { }
        // Intentar desde rutas comunes del proyecto
        for (String p : new String[]{
                "src/main/resources/fonts/helvetica-bold.ttf",
                "src\\main\\resources\\fonts\\helvetica-bold.ttf",
                System.getProperty("user.home") + "\\Documents\\GitHub\\GestionFormularios\\src\\main\\resources\\fonts\\helvetica-bold.ttf"
        }) {
            try {
                Path path = Paths.get(p);
                if (Files.exists(path)) {
                    try (InputStream in = Files.newInputStream(path)) {
                        return PDType0Font.load(document, in, true);
                    }
                }
            } catch (Exception ignore) { }
        }
        return null;
    }

    private static void applyButtonValue(PDField field, FieldMapping fm, String desired) throws IOException {
        String desiredLower = desired.toLowerCase(Locale.ROOT);
        boolean truthy = desiredLower.equals("true") || desiredLower.equals("1") || desiredLower.equals("yes") || desiredLower.equals("si") || desiredLower.equals("sí");
        if (field instanceof PDCheckBox cb) {
            String on = (fm.exportValue() == null || fm.exportValue().isBlank()) ? cb.getOnValue() : fm.exportValue();
            if (on == null || on.isBlank()) on = "Yes";
            cb.setValue(truthy || desired.equalsIgnoreCase(on) ? on : "Off");
            return;
        }
        if (field instanceof PDRadioButton rb) {
            String export = fm.exportValue() == null ? "" : fm.exportValue();
            List<String> options = export.isEmpty() ? rb.getExportValues() : List.of(export.split(","));
            if (options != null && !options.isEmpty()) {
                String match = options.stream().filter(opt -> opt.equalsIgnoreCase(desired)).findFirst().orElse(null);
                rb.setValue(match != null ? match : (truthy ? options.get(0) : ""));
            }
        }
    }
}
