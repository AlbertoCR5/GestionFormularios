package com.albertocr.gestionformularios.util;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.stream.Stream;

/**
 * Clase de utilidad para gestionar la creación y manipulación de directorios
 * para el almacenamiento de documentos del proyecto.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.3
 */
public final class DirectorioManager {

    private static final Logger logger = LoggerFactory.getLogger(DirectorioManager.class);
    private static final String ROOT_DIR_NAME = "Elecciones";

    /**
     * Constructor privado para prevenir la instanciación de esta clase de utilidad.
     */
    private DirectorioManager() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada.");
    }

    /**
     * Crea un directorio y todos los directorios padres si no existen.
     * Es un método robusto para garantizar que una ruta de archivo completa esté disponible.
     *
     * @param directoryPath La ruta completa del directorio a crear como String.
     * @throws IOException si ocurre un error de entrada/salida al crear los directorios.
     */
    public static void crearDirectorioSiNoExiste(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);
        if (Files.notExists(path)) {
            Files.createDirectories(path);
            logger.info("Directorio creado en: {}", path);
        }
    }

    /**
     * Muestra un diálogo al usuario para que seleccione un directorio donde guardar archivos.
     *
     * @return El directorio seleccionado como un objeto {@link File}, o null si el usuario cancela la selección.
     */
    public static File obtenerDirectorioSeleccionado() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar Directorio para Guardar");
        // Se crea un Stage temporal para mostrar el diálogo de manera modal.
        return directoryChooser.showDialog(new Stage());
    }

    /**
     * Crea el directorio raíz 'Elecciones' en el directorio de documentos del usuario si no existe.
     * Este método utiliza el método genérico {@link #crearDirectorioSiNoExiste(String)} para su implementación.
     *
     * @return La ruta (Path) al directorio raíz creado o existente.
     * @throws IOException Si ocurre un error al crear el directorio.
     */
    public static Path crearDirectorioRaiz() throws IOException {
        Path userHome = Paths.get(System.getProperty("user.home"), "Documents");
        Path rutaElecciones = userHome.resolve(ROOT_DIR_NAME);
        crearDirectorioSiNoExiste(rutaElecciones.toString());
        return rutaElecciones;
    }

    /**
     * Asegura que el directorio existe y, si es posible (Windows), lo marca como oculto.
     * Ignora silenciosamente si el atributo DOS no está disponible.
     *
     * @param dir Ruta del directorio a crear/marcar.
     * @return La misma ruta recibida.
     * @throws IOException Si falla la creación del directorio.
     */
    public static Path ensureHiddenDirectory(Path dir) throws IOException {
        if (dir == null) return null;
        Files.createDirectories(dir);
        try {
            // En Windows, marcar como oculto si no lo está
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                Boolean hidden = (Boolean) Files.getAttribute(dir, "dos:hidden");
                if (hidden == null || !hidden) {
                    Files.setAttribute(dir, "dos:hidden", true);
                }
            }
        } catch (UnsupportedOperationException | IOException ignored) {
            // Otros SO o FS sin atributos DOS: ignorar
        }
        return dir;
    }

    /**
     * Crea un subdirectorio para una empresa específica dentro del directorio raíz de elecciones.
     * El nombre del directorio de la empresa se sanitiza para evitar caracteres no válidos.
     *
     * @param rutaRaiz      La ruta del directorio raíz de elecciones.
     * @param nombreEmpresa El nombre de la empresa para crear el subdirectorio.
     * @return La ruta (Path) al subdirectorio de la empresa.
     * @throws IOException Si ocurre un error al crear el directorio.
     */
    public static Path crearDirectorioEmpresa(Path rutaRaiz, String nombreEmpresa) throws IOException {
        String nombreSanitizado = sanitizarNombre(nombreEmpresa);
        Path rutaEmpresa = rutaRaiz.resolve(nombreSanitizado);
        crearDirectorioSiNoExiste(rutaEmpresa.toString());
        return rutaEmpresa;
    }

    /**
     * Copia todos los archivos de una carpeta de recursos (dentro del JAR) a un directorio de destino.
     * Este método es útil para inicializar el proyecto con plantillas de documentos.
     *
     * @param destino      La ruta del directorio de destino donde se copiarán los archivos.
     * @param carpetaRecurso El nombre de la carpeta de recursos a copiar (p. ej., "Delegados").
     * @throws IOException Si ocurre un error de entrada/salida al acceder o copiar los recursos.
     */
    public static void copiarRecursosADirectorio(String destino, String carpetaRecurso) throws IOException {
        try {
            URI uri = DirectorioManager.class.getResource("/" + carpetaRecurso).toURI();
            // Acceder al sistema de archivos dentro del JAR
            try (FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
                Path resourcesPath = fileSystem.getPath("/" + carpetaRecurso);
                // Recorrer los archivos y copiarlos
                try (Stream<Path> walk = Files.walk(resourcesPath, 1)) {
                    walk.filter(Files::isRegularFile).forEach(source -> {
                        try {
                            Path destinationFile = Paths.get(destino, source.getFileName().toString());
                            Files.copy(source, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            throw new RuntimeException("Fallo al copiar recurso: " + source, e);
                        }
                    });
                }
            }
        } catch (URISyntaxException | IOException e) {
            logger.error("Error al acceder a la carpeta de recursos: {}", carpetaRecurso, e);
            throw new IOException("No se pudo acceder a los recursos de la aplicación.", e);
        }
    }

    /**
     * Genera las rutas completas de todos los archivos PDF dentro de un directorio especificado.
     *
     * @param rutaDirectorioEmpresa La ruta del directorio a inspeccionar.
     * @return Un array de Strings con las rutas absolutas a los archivos PDF,
     * o un array vacío si no se encuentran archivos o si ocurre un error.
     */
    public static String[] generarRutasFormularios(Path rutaDirectorioEmpresa) {
        try (Stream<Path> files = Files.walk(rutaDirectorioEmpresa)) {
            return files
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().toLowerCase().endsWith(".pdf"))
                    .map(Path::toString)
                    .toArray(String[]::new);
        } catch (IOException e) {
            logger.error("No se pudieron leer los formularios del directorio: {}", rutaDirectorioEmpresa, e);
            return new String[0];
        }
    }

    /**
     * Sanitiza un nombre de archivo o directorio, reemplazando caracteres no alfanuméricos
     * (excepto guiones, guiones bajos y puntos) por un guion bajo.
     *
     * @param nombre El nombre a sanitizar.
     * @return El nombre sanitizado.
     */
    public static String sanitizarNombre(String nombre) {
        return nombre.replaceAll("[^a-zA-Z0-9 .-]", "");
    }
}
