package com.example.proyecto.util;

import com.example.proyecto.interfaz.PrincipalView;
import com.example.proyecto.modal.Modelo_5_1;
import com.example.proyecto.modal.Modelo_5_2_Conclusion;
import com.example.proyecto.modal.Modelo_5_2_Proceso;
import com.example.proyecto.modal.Preaviso;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

/**
 * La clase `Registro` gestiona el registro de nuevos preavisos y la creación de directorios correspondientes.
 * Utiliza `DirectorioManager` para gestionar directorios y `CumplimentarPDF` para manipular formularios PDF.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class Registro {

    private final Preaviso nuevoPreaviso;
    private final Modelo_5_1 nuevoModelo51;
    private final Modelo_5_2_Proceso nuevoModelo52Proceso;
    private final Modelo_5_2_Conclusion nuevoModelo52Conclusion;
    private final PrincipalView ventanaPreaviso;
    private final DirectorioManager directorioManager;
    private final CumplimentarPreavisoPDF cumplimentarPreavisoPDF;
    private final CumplimentarEscrutinioPDF cumplimentarEscrutinioPDF;
    private final Path rutaElecciones;

    /**
     * Constructor para registrar un nuevo preaviso.
     *
     * @param nuevoPreaviso El preaviso que se va a registrar.
     * @param ventanaPreaviso La vista de la ventana de preaviso.
     * @throws IOException Sí ocurre un error al crear el directorio de elecciones.
     */
    public Registro(@NotNull Preaviso nuevoPreaviso, @NotNull PrincipalView ventanaPreaviso) throws IOException {
        this.nuevoPreaviso = nuevoPreaviso;
        this.ventanaPreaviso = ventanaPreaviso;
        this.directorioManager = new DirectorioManager();
        this.cumplimentarPreavisoPDF = new CumplimentarPreavisoPDF(ventanaPreaviso);
        this.rutaElecciones = directorioManager.crearDirectorioElecciones();
        this.nuevoModelo51 = null;
        this.nuevoModelo52Proceso = null;
        this.nuevoModelo52Conclusion = null;
        this.cumplimentarEscrutinioPDF = null;
    }

    /**
     * Constructor para registrar modelos de escrutinio.
     *
     * @param nuevoModelo51 El modelo 5.1 de escrutinio.
     * @param nuevoModelo52Proceso El modelo 5.2 de proceso de escrutinio.
     * @param nuevoModelo52Conclusion El modelo 5.2 de conclusión de escrutinio.
     * @param ventanaPreaviso La vista de la ventana de preaviso.
     * @throws IOException Sí ocurre un error al crear el directorio de elecciones.
     */
    public Registro(@NotNull Modelo_5_1 nuevoModelo51, @NotNull Modelo_5_2_Proceso nuevoModelo52Proceso, @NotNull Modelo_5_2_Conclusion nuevoModelo52Conclusion, @NotNull PrincipalView ventanaPreaviso) throws IOException {
        this.nuevoPreaviso = null;
        this.nuevoModelo51 = nuevoModelo51;
        this.nuevoModelo52Proceso = nuevoModelo52Proceso;
        this.nuevoModelo52Conclusion = nuevoModelo52Conclusion;
        this.ventanaPreaviso = ventanaPreaviso;
        this.directorioManager = new DirectorioManager();
        this.cumplimentarEscrutinioPDF = new CumplimentarEscrutinioPDF(ventanaPreaviso);
        this.rutaElecciones = directorioManager.crearDirectorioElecciones();
        this.cumplimentarPreavisoPDF = null;
    }

    /**
     * Registra un nuevo preaviso creando los directorios y modificando los formularios PDF.
     *
     * @param nuevoPreaviso El preaviso que se va a registrar.
     */
    public void registrarNuevoPreavisoDelegado(@NotNull Preaviso nuevoPreaviso) {
        try {
            Path rutaDirectorioEmpresa = directorioManager.crearDirectorioEmpresa(rutaElecciones, nuevoPreaviso);
            directorioManager.copiarRecursosADirectorio(rutaDirectorioEmpresa.toString(), nuevoPreaviso);
            String[] rutaFormularios = directorioManager.generarRutasFormularios();

            for (String rutaFormularioPDF : rutaFormularios) {
                try {
                    procesarFormularioPreaviso(rutaFormularioPDF, rutaDirectorioEmpresa);
                } catch (Exception e) {
                    ventanaPreaviso.mostrarMensaje(MessageManager.getMessage("error.procesar.formulario") + e.getMessage(), false);
                }
            }
        } catch (IOException e) {
            ventanaPreaviso.mostrarMensaje(MessageManager.getMessage("error.crear.directorio") + e.getMessage(), false);
        }
    }

    /**
     * Procesa los formularios para el preaviso.
     *
     * @param rutaFormularioPDF Ruta del formulario PDF.
     * @param rutaDirectorioEmpresa Ruta del directorio de la empresa.
     */
    private void procesarFormularioPreaviso(@NotNull String rutaFormularioPDF, @NotNull Path rutaDirectorioEmpresa) {
        try {
            if (rutaFormularioPDF.contains(Constantes.PREAVISO)) {
                cumplimentarPreavisoPDF.modificarCamposTextoPreavisoPDF(rutaFormularioPDF, rutaDirectorioEmpresa.toString(), nuevoPreaviso);
            } else if (rutaFormularioPDF.contains(Constantes.CALENDARIO_DELEGADOS)) {
                cumplimentarPreavisoPDF.modificarCamposTextoCalendarioDelegadosPDF(rutaFormularioPDF, rutaDirectorioEmpresa.toString(), nuevoPreaviso);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_3)) {
                cumplimentarPreavisoPDF.modificarCamposTextoModelo3PDF(rutaFormularioPDF, rutaDirectorioEmpresa.toString(), nuevoPreaviso);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_5_1)) {
                cumplimentarPreavisoPDF.modificarCamposTextoModelo5_1PDF(rutaFormularioPDF, rutaDirectorioEmpresa.toString(), nuevoPreaviso);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_5_2_CONCLUSION)) {
                cumplimentarPreavisoPDF.modificarCamposTextoModelo5_2ConclusionPDF(rutaFormularioPDF, rutaDirectorioEmpresa.toString(), nuevoPreaviso);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_5_2_PROCESO)) {
                cumplimentarPreavisoPDF.modificarCamposTextoModelo5_2ProcesoPDF(rutaFormularioPDF, rutaDirectorioEmpresa.toString(), nuevoPreaviso);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_9)) {
                cumplimentarPreavisoPDF.modificarCamposTextoModelo9PDF(rutaFormularioPDF, rutaDirectorioEmpresa.toString(), nuevoPreaviso);
            } else if (rutaFormularioPDF.contains(Constantes.AUTORIZACION)) {
                cumplimentarPreavisoPDF.modificarCamposTextoAutorizacionPDF(rutaFormularioPDF, rutaDirectorioEmpresa.toString(), nuevoPreaviso);
            }
        } catch (Exception e) {
            ventanaPreaviso.mostrarMensaje(MessageManager.getMessage("error.procesar.formulario") + e.getMessage(), false);
        }
    }

    /**
     * Registra los modelos de escrutinio creando los directorios y modificando los formularios PDF.
     *
     * @param nuevoModelo51 El modelo 5.1 de escrutinio.
     * @param nuevoModelo52Proceso El modelo 5.2 de proceso de escrutinio.
     * @param nuevoModelo52Conclusion El modelo 5.2 de conclusión de escrutinio.
     * @param rutaEmpresa La ruta de la empresa.
     */
    public void registrarModelosEscrutinio(@NotNull Modelo_5_1 nuevoModelo51, @NotNull Modelo_5_2_Proceso nuevoModelo52Proceso, @NotNull Modelo_5_2_Conclusion nuevoModelo52Conclusion, @NotNull Path rutaEmpresa) {
            String[] rutaFormularios = directorioManager.generarRutasFormulariosBuscados(rutaEmpresa);
            for (String rutaFormularioPDF : rutaFormularios) {
                try {
                    procesarFormularioEscrutinio(rutaFormularioPDF, nuevoModelo51, nuevoModelo52Proceso, nuevoModelo52Conclusion);
                } catch (Exception e) {
                    ventanaPreaviso.mostrarMensaje(MessageManager.getMessage("error.procesar.formulario") + e.getMessage(), false);
                }
            }
    }

    /**
     * Procesa los formularios para el escrutinio.
     *
     * @param rutaFormularioPDF Ruta del formulario PDF.
     * @param nuevoModelo51 El modelo 5.1 de escrutinio.
     * @param nuevoModelo52Proceso El modelo 5.2 de proceso de escrutinio.
     * @param nuevoModelo52Conclusion El modelo 5.2 de conclusión de escrutinio.
     */
    private void procesarFormularioEscrutinio(@NotNull String rutaFormularioPDF, @NotNull Modelo_5_1 nuevoModelo51, @NotNull Modelo_5_2_Proceso nuevoModelo52Proceso, @NotNull Modelo_5_2_Conclusion nuevoModelo52Conclusion) {
        try {
            if (rutaFormularioPDF.contains(Constantes.MODELO_5_1)) {
                cumplimentarEscrutinioPDF.modificarCamposTextoEscrutinioModelo5_1PDF(rutaFormularioPDF, nuevoModelo51);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_5_2_PROCESO)) {
                cumplimentarEscrutinioPDF.modificarCamposTextoEscrutinioModelo5_2ProcesoPDF(rutaFormularioPDF, nuevoModelo51, nuevoModelo52Proceso);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_5_2_CONCLUSION)) {
                cumplimentarEscrutinioPDF.modificarCamposTextoEscrutinioModelo5_2ConclusionPDF(rutaFormularioPDF, nuevoModelo52Conclusion);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_9)) {
                cumplimentarEscrutinioPDF.modificarCamposTextoEscrutinioModelo9PDF(rutaFormularioPDF, nuevoModelo51, nuevoModelo52Conclusion, nuevoModelo52Proceso);
            } else if (rutaFormularioPDF.contains(Constantes.AUTORIZACION)) {
                cumplimentarEscrutinioPDF.modificarCamposTextoEscrutinioAutorizacionPDF(rutaFormularioPDF, nuevoModelo51, nuevoModelo52Conclusion);
            }
        } catch (Exception e) {
            ventanaPreaviso.mostrarMensaje(MessageManager.getMessage("error.procesar.formulario") + e.getMessage(), false);
        }
    }

    /**
     * Registra un nuevo preaviso para más de 50 trabajadores.
     *
     * @param nuevoPreaviso El preaviso que se va a registrar.
     */
    public void registrarNuevoPreavisoComite(Preaviso nuevoPreaviso) {
        try {
            Path rutaDirectorioEmpresa = directorioManager.crearDirectorioEmpresa(rutaElecciones, nuevoPreaviso);
            directorioManager.copiarRecursosADirectorio(rutaDirectorioEmpresa.toString(), nuevoPreaviso);
            String[] rutaFormularios = directorioManager.generarRutasFormularios();

            for (String rutaFormularioPDF : rutaFormularios) {
                try {
                    procesarFormularioPreavisoComite(rutaFormularioPDF, rutaDirectorioEmpresa);
                } catch (Exception e) {
                    ventanaPreaviso.mostrarMensaje(MessageManager.getMessage("error.procesar.formulario") + e.getMessage(), false);
                }
            }
        } catch (IOException e) {
            ventanaPreaviso.mostrarMensaje(MessageManager.getMessage("error.crear.directorio") + e.getMessage(), false);
        }
    }

    private void procesarFormularioPreavisoComite(String rutaFormularioPDF, Path rutaDirectorioEmpresa) {
        try {
//            if (rutaFormularioPDF.contains(Constantes.PREAVISO)) {
//                cumplimentarPreavisoComitePDF.modificarCamposTextoComitePDF(rutaFormularioPDF, nuevoPreaviso);
//            }
            // Agregar otros formularios específicos para más de 50 trabajadores aquí
        } catch (Exception e) {
            ventanaPreaviso.mostrarMensaje(MessageManager.getMessage("error.procesar.formulario") + e.getMessage(), false);
        }
    }
}
