package com.albertocr.gestionformularios.tools;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility to scan PDF templates under resources and report AcroForm field names.
 * Outputs a JSON file at target/pdf-fields-report.json
 */
public class PdfFieldsInspector {

    public static void main(String[] args) throws IOException {
        Path resources = Paths.get("src/main/resources");
        List<Path> pdfs = new ArrayList<>();
        Files.walk(resources)
                .filter(p -> p.toString().toLowerCase().endsWith(".pdf"))
                .forEach(pdfs::add);

        Map<String, List<String>> report = new HashMap<>();
        for (Path pdf : pdfs) {
            List<String> fields = inspectPdfForFields(pdf.toFile());
            report.put(resources.relativize(pdf).toString().replace('\\', '/'), fields);
        }

        // Write JSON simple
        Path out = Paths.get("target/pdf-fields-report.json");
        out.getParent().toFile().mkdirs();
        try (FileWriter w = new FileWriter(out.toFile())) {
            w.write("{");
            boolean first = true;
            for (Map.Entry<String, List<String>> e : report.entrySet()) {
                if (!first) w.write(",\n");
                first = false;
                w.write("  \"");
                w.write(e.getKey().replace("\"", "\\\""));
                w.write("\": [\n");
                boolean f2 = true;
                for (String fn : e.getValue()) {
                    if (!f2) w.write(",\n");
                    f2 = false;
                    w.write("    \"");
                    w.write(fn.replace("\"", "\\\""));
                    w.write("\"");
                }
                w.write("\n  ]");
                w.write("\n");
            }
            w.write("}\n");
        }
        System.out.println("PDF fields report written to " + out.toAbsolutePath());
    }

    private static List<String> inspectPdfForFields(File pdfFile) {
        List<String> names = new ArrayList<>();
    try (PDDocument doc = org.apache.pdfbox.Loader.loadPDF(pdfFile)) {
            PDAcroForm form = doc.getDocumentCatalog().getAcroForm();
            if (form != null) {
                for (PDField f : form.getFields()) {
                    names.add(f.getFullyQualifiedName());
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to inspect " + pdfFile + ": " + e.getMessage());
        }
        return names;
    }
}
