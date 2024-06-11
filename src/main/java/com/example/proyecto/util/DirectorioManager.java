package com.example.proyecto.util;

import com.example.proyecto.interfaz.PrincipalView;
import com.example.proyecto.modal.Preaviso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.ResourceBundle;

/**
 * La clase `DirectorioManager` gestiona la creación y manejo de directorios en la aplicación.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 */
public class DirectorioManager {

   PrincipalView ventanaPreaviso;
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages");

    /**
     * Crea el directorio de elecciones si no existe.
     *
     * @return La ruta del directorio de elecciones.
     * @throws IOException Si ocurre un error al crear el directorio.
     */
    public Path crearDirectorioElecciones() throws IOException {
        String rutaBase = Constantes.RUTA_BASE;
        Path rutaElecciones = Paths.get(rutaBase, Constantes.NOMBRE_DIRECTORIO);
        File directorioElecciones = rutaElecciones.toFile();
        if (!directorioElecciones.exists()) {
            boolean dirCreated = directorioElecciones.mkdir();
            if (!dirCreated) {
                ventanaPreaviso.mostrarMensaje(bundle.getString("error.crear.directorio") + rutaElecciones, false);
                throw new IOException(rutaElecciones.toString());
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
    public Path crearDirectorioEmpresa(Path rutaElecciones, Preaviso nuevoPreaviso) throws IOException {
        String nombreEmpresa = sanitizarNombreEmpresa(nuevoPreaviso.getNombreEmpresa());
        Path rutaEmpresa = Paths.get(rutaElecciones.toString(), nombreEmpresa);
        File directorioEmpresa = rutaEmpresa.toFile();
        if (!directorioEmpresa.exists()) {
            boolean dirCreated = directorioEmpresa.mkdir();
            if (!dirCreated) {
                ventanaPreaviso.mostrarMensaje(bundle.getString("error.crear.directorio") + rutaEmpresa, false);
                throw new IOException(rutaEmpresa.toString());
            }
        }
        return rutaEmpresa;
    }

    /**
     * Genera las rutas de los formularios necesarios para el proceso de preaviso.
     *
     * @return Un array de rutas de formularios.
     */
    public String[] generarRutasFormularios() {
        String[] rutaFormularios = new String[Constantes.DOCUMENTACION_DELEGADOS.length];
        for (int i = 0; i < Constantes.DOCUMENTACION_DELEGADOS.length; i++) {
            rutaFormularios[i] = STR."\{Constantes.RUTA_DELEGADOS_JAR}/\{Constantes.DOCUMENTACION_DELEGADOS[i]}\{Constantes.EXTENSION_ARCHIVO}";
        }
        return rutaFormularios;
    }

    /**
     * Genera las rutas de los formularios buscados para una empresa dada.
     *
     * @param rutaEmpresa La ruta del directorio de la empresa.
     * @return Un array de rutas de formularios.
     */
    public String[] generarRutasFormulariosBuscados(Path rutaEmpresa) {
        String[] rutaFormulariosBuscados = new String[Constantes.DOCUMENTACION_DELEGADOS.length];
        for (int i = 0; i < Constantes.DOCUMENTACION_DELEGADOS.length; i++) {
            rutaFormulariosBuscados[i] = String.valueOf(Paths.get(rutaEmpresa.toString(), rutaEmpresa.getFileName().toString()+ " ".concat( Constantes.DOCUMENTACION_DELEGADOS[i] + Constantes.EXTENSION_ARCHIVO)));
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
    public Path buscarCarpetaEmpresa(Path rutaElecciones, String nombreEmpresa) {
        String nombreEmpresaSanitizado = sanitizarNombreEmpresa(nombreEmpresa);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(rutaElecciones)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry) && entry.getFileName().toString().toLowerCase().contains(nombreEmpresaSanitizado.toLowerCase())) {
                    return entry;
                }
            }
        } catch (IOException e) {
            ventanaPreaviso.mostrarMensaje(bundle.getString("error.buscar.carpeta.empresa"), false);
        }
        return null;
    }

    /**
     * Sanitiza el nombre de la empresa para uso como nombre de carpeta.
     *
     * @param nombreEmpresa El nombre de la empresa a sanitizar.
     * @return El nombre de la empresa sanitizado.
     */
    public static String sanitizarNombreEmpresa(String nombreEmpresa) {
        return nombreEmpresa.replace(".", "").replace("/", "").replace("\\", "").replace(",", "");
    }

    public void copiarRecursosADirectorio(String rutaDestino, Preaviso nuevoPreaviso) throws IOException {
        String[] formularios = generarRutasFormularios();
        CumplimentarPreavisoPDF cumplimentarPreavisoPDF = new CumplimentarPreavisoPDF(ventanaPreaviso);

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
    public InputStream obtenerRecursoComoStream(String ruta) {
        return getClass().getResourceAsStream(ruta);
    }
}
