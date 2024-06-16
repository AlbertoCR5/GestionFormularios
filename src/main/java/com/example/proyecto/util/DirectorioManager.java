package com.example.proyecto.util;

import com.example.proyecto.interfaz.PrincipalView;
import com.example.proyecto.modal.Preaviso;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

/**
 * La clase `DirectorioManager` gestiona la creación y manejo de directorios en la aplicación.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class DirectorioManager {

    private PrincipalView view;

    /**
     * Crea el directorio de elecciones si no existe.
     *
     * @return La ruta del directorio de elecciones.
     * @throws IOException Si ocurre un error al crear el directorio.
     */
    public @NotNull Path crearDirectorioElecciones() throws IOException {
        String rutaBase = Constantes.RUTA_BASE;
        Path rutaElecciones = Paths.get(rutaBase, Constantes.NOMBRE_DIRECTORIO);
        File directorioElecciones = rutaElecciones.toFile();
        if (!directorioElecciones.exists()) {
            boolean dirCreated = directorioElecciones.mkdir();
            if (!dirCreated) {
                mostrarMensajeError("error.crear.directorio", rutaElecciones.toString());
                throw new IOException(STR."No se pudo crear el directorio: \{rutaElecciones}");
            }
        }
        return rutaElecciones;
    }

    /**
     * Crea el directorio de la empresa si no existe dentro del directorio de elecciones.
     *
     * @param rutaElecciones La ruta del directorio de elecciones.
     * @param nuevoPreaviso  El objeto Preaviso con el nombre de la empresa.
     * @return La ruta del directorio de la empresa.
     * @throws IOException Si ocurre un error al crear el directorio.
     */
    public @NotNull Path crearDirectorioEmpresa(@NotNull Path rutaElecciones, @NotNull Preaviso nuevoPreaviso) throws IOException {
        String nombreEmpresa = sanitizarNombreEmpresa(nuevoPreaviso.getNombreEmpresa());
        Path rutaEmpresa = Paths.get(rutaElecciones.toString(), nombreEmpresa);
        File directorioEmpresa = rutaEmpresa.toFile();
        if (!directorioEmpresa.exists()) {
            boolean dirCreated = directorioEmpresa.mkdir();
            if (!dirCreated) {
                mostrarMensajeError("error.crear.directorio", rutaEmpresa.toString());
                throw new IOException(STR."No se pudo crear el directorio: \{rutaEmpresa}");
            }
        }
        return rutaEmpresa;
    }

    /**
     * Genera las rutas de los formularios necesarios para el proceso de preaviso.
     *
     * @return Un array de rutas de formularios.
     */
    public @NotNull String[] generarRutasFormulariosDelegados() {
        String[] rutaFormulariosDelegados = new String[Constantes.DOCUMENTACION_DELEGADOS.length];
        for (int i = 0; i < Constantes.DOCUMENTACION_DELEGADOS.length; i++) {
            rutaFormulariosDelegados[i] = String.format("%s/%s%s", Constantes.RUTA_DELEGADOS_JAR, Constantes.DOCUMENTACION_DELEGADOS[i], Constantes.EXTENSION_ARCHIVO);
        }
        return rutaFormulariosDelegados;
    }

    /**
     * Genera las rutas de los formularios necesarios para el proceso de preaviso.
     *
     * @return Un array de rutas de formularios.
     */
    public @NotNull String[] generarRutasFormulariosComite() {
        String[] rutaFormulariosComite = new String[Constantes.DOCUMENTACION_DELEGADOS.length];
        for (int i = 0; i < Constantes.DOCUMENTACION_DELEGADOS.length; i++) {
            rutaFormulariosComite[i] = String.format("%s/%s%s", Constantes.RUTA_DELEGADOS_JAR, Constantes.DOCUMENTACION_DELEGADOS[i], Constantes.EXTENSION_ARCHIVO);
        }
        return rutaFormulariosComite;
    }

    /**
     * Genera las rutas de los formularios buscados para una empresa dada.
     *
     * @param rutaEmpresa La ruta del directorio de la empresa.
     * @return Un array de rutas de formularios.
     */
    public @NotNull String[] generarRutasFormulariosBuscados(@NotNull Path rutaEmpresa) {
        String[] rutaFormulariosBuscados = new String[Constantes.DOCUMENTACION_DELEGADOS.length];
        for (int i = 0; i < Constantes.DOCUMENTACION_DELEGADOS.length; i++) {
            rutaFormulariosBuscados[i] = String.valueOf(Paths.get(rutaEmpresa.toString(), String.format("%s %s%s", rutaEmpresa.getFileName().toString(), Constantes.DOCUMENTACION_DELEGADOS[i], Constantes.EXTENSION_ARCHIVO)));
        }
        return rutaFormulariosBuscados;
    }

    /**
     * Busca una carpeta con el nombre de una empresa dentro del directorio de elecciones.
     *
     * @param rutaElecciones La ruta del directorio de elecciones.
     * @param nombreEmpresa  El nombre de la empresa a buscar.
     * @return La ruta de la carpeta de la empresa si existe, null en caso contrario.
     */
    public Path buscarCarpetaEmpresa(@NotNull Path rutaElecciones, @NotNull String nombreEmpresa) {
        String nombreEmpresaSanitizado = sanitizarNombreEmpresa(nombreEmpresa);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(rutaElecciones)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry) && entry.getFileName().toString().toLowerCase().contains(nombreEmpresaSanitizado.toLowerCase())) {
                    return entry;
                }
            }
        } catch (IOException e) {
            mostrarMensajeError("error.buscar.carpeta.empresa", "");
        }
        return null;
    }

    /**
     * Sanitiza el nombre de la empresa para uso como nombre de carpeta.
     *
     * @param nombreEmpresa El nombre de la empresa a sanitizar.
     * @return El nombre de la empresa sanitizado.
     */
    public static @NotNull String sanitizarNombreEmpresa(@NotNull String nombreEmpresa) {
        return nombreEmpresa.replace(".", "").replace("/", "").replace("\\", "").replace(",", "");
    }

    /**
     * Copia los recursos de los formularios al directorio de destino.
     *
     * @param rutaDestino   La ruta de destino.
     * @param nuevoPreaviso El objeto Preaviso con los datos necesarios.
     * @throws IOException Sí ocurre un error durante la copia de los archivos.
     */
    public void copiarRecursosADirectorio(@NotNull String rutaDestino, @NotNull Preaviso nuevoPreaviso) throws IOException {
        String[] formularios = generarRutasFormulariosDelegados();
        CumplimentarPreavisoPDF cumplimentarPreavisoPDF = new CumplimentarPreavisoPDF(view);

        for (String formulario : formularios) {
            String nombreArchivo = cumplimentarPreavisoPDF.generarNombreArchivo(formulario, nuevoPreaviso);
            Path destino = Paths.get(rutaDestino, nombreArchivo);

            if (!Files.exists(destino)) {
                try (InputStream in = obtenerRecursoComoStream(formulario)) {
                    if (in == null) {
                        throw new IOException(STR."Archivo no encontrado: \{formulario}");
                    }
                    Files.createDirectories(destino.getParent());
                    Files.copy(in, destino, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }

    /**
     * Obtiene un recurso como InputStream desde el JAR.
     *
     * @param ruta La ruta del recurso dentro del JAR.
     * @return Un InputStream del recurso.
     */
    public InputStream obtenerRecursoComoStream(@NotNull String ruta) {
        return getClass().getResourceAsStream(ruta);
    }

    /**
     * Muestra un mensaje de error.
     *
     * @param claveMensaje La clave del mensaje.
     * @param detalles Los detalles adicionales.
     */
    private void mostrarMensajeError(@NotNull String claveMensaje, @NotNull String detalles) {
        if (view != null) {
            view.mostrarMensaje(MessageManager.getMessage(claveMensaje) + detalles, false);
        }
    }
}
