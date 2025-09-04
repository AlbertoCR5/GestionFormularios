package com.albertocr.gestionformularios.util;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDVariableText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Herramienta para parchear plantillas PDF en recursos (comité y delegados):
 * - Inserta fuente Helvetica-Bold (TTF) con incrustación por subconjunto.
 * - Fija la apariencia por defecto (DA) del AcroForm y de cada campo de texto.
 * - Mapea el alias "Helvetica-Bold" a la fuente embebida en recursos de formulario y de página.
 * - Regenera apariencias sin aplanar, y guarda de forma atómica.
 *
 * Uso: ejecutar el main sin argumentos para parchear los dos directorios por defecto.
 */
public final class PdfTemplatePatcher {

    private static final Logger log = LoggerFactory.getLogger(PdfTemplatePatcher.class);

    private PdfTemplatePatcher() {}

    public static void main(String[] args) throws Exception {
        Path repoRoot = Paths.get(System.getProperty("user.dir"));
        // Directorios por defecto (case-insensitive en Windows)
        Path comite = repoRoot.resolve("src/main/resources/Comite");
        Path delegados = repoRoot.resolve("src/main/resources/Delegados");
        if (args != null && args.length >= 1) comite = Paths.get(args[0]);
        if (args != null && args.length >= 2) delegados = Paths.get(args[1]);

        AtomicInteger patched = new AtomicInteger();
        for (Path dir : new Path[]{comite, delegados}) {
            if (dir == null) continue;
            if (!Files.isDirectory(dir)) {
                log.warn("Directorio no encontrado: {}", dir);
                continue;
            }
            log.info("Parcheando PDFs en: {}", dir.toAbsolutePath());
            try (Stream<Path> walk = Files.list(dir)) {
                walk.filter(p -> p.toString().toLowerCase().endsWith(".pdf")).forEach(pdf -> {
                    try {
                        if (patchFile(pdf)) patched.incrementAndGet();
                    } catch (Exception e) {
                        log.error("Error parchando {}: {}", pdf.getFileName(), e.getMessage());
                    }
                });
            }
        }
        log.info("Plantillas parchadas: {}", patched.get());
    }

    public static boolean patchFile(Path pdfPath) throws IOException {
        if (pdfPath == null || !Files.exists(pdfPath)) return false;

        Path parent = pdfPath.getParent();
        Path temp = Files.createTempFile(parent, pdfPath.getFileName().toString() + ".", ".tmp");
        try (PDDocument doc = Loader.loadPDF(Files.readAllBytes(pdfPath))) {
            PDAcroForm acro = doc.getDocumentCatalog().getAcroForm();
            if (acro == null) {
                // Nada que parchear
                return false;
            }

            // Asegurar recursos y fuente embebida
            PDResources resources = acro.getDefaultResources();
            if (resources == null) {
                resources = new PDResources();
                acro.setDefaultResources(resources);
            }
            PDFont helvBold = loadHelveticaBold(doc);
            if (helvBold == null) {
                log.warn("No se pudo cargar helvetica-bold.ttf; se omite: {}", pdfPath.getFileName());
                return false;
            }
            COSName alias = resources.add(helvBold);
            String daFont = "/" + (alias != null ? alias.getName() : "F0") + " 10 Tf 0 g";
            acro.setDefaultAppearance(daFont);

            // Fijar DA en cada campo de texto y tocar widgets
            for (PDField f : acro.getFields()) {
                if (f instanceof PDVariableText vt) {
                    try { vt.setDefaultAppearance(daFont); } catch (Exception ignore) { }
                }
                try {
                    List<PDAnnotationWidget> widgets = f.getWidgets();
                    if (widgets != null) {
                        for (PDAnnotationWidget w : widgets) {
                            if (w != null) w.getCOSObject();
                        }
                    }
                } catch (Exception ignore) { }
            }

            // Mapear alias Helvetica-Bold en recursos del formulario y páginas
            ensureFontAlias(resources, "Helvetica-Bold", helvBold);
            for (PDPage page : doc.getPages()) {
                PDResources pr = page.getResources();
                if (pr == null) { pr = new PDResources(); page.setResources(pr); }
                ensureFontAlias(pr, "Helvetica-Bold", helvBold);
            }

            try { acro.refreshAppearances(); } catch (Exception ignore) { }
            doc.save(temp.toFile());
        }

        try { Files.deleteIfExists(pdfPath); } catch (Exception ignore) { }
        Files.move(temp, pdfPath);
        return true;
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
        // 1) Recurso
        try (InputStream in = PdfTemplatePatcher.class.getResourceAsStream("/fonts/helvetica-bold.ttf")) {
            if (in != null) return PDType0Font.load(document, in, true);
        } catch (Exception ignore) { }
        // 2) Ruta del proyecto
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
}
