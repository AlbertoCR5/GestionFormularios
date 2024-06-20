package com.example.proyecto.util;

import com.example.proyecto.interfaz.PrincipalView;
import com.example.proyecto.modal.*;
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
    private final CumplimentarPreavisoComitePDF cumplimentarPreavisoComitePDF;
    private final CumplimentarEscrutinioPDF cumplimentarEscrutinioPDF;
    private final CumplimentarEscrutinioComitePDF cumplimentarEscrutinioComitePDF;
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
        this.cumplimentarPreavisoComitePDF = new CumplimentarPreavisoComitePDF(ventanaPreaviso);
        this.rutaElecciones = directorioManager.crearDirectorioElecciones();
        this.nuevoModelo51 = null;
        this.nuevoModelo52Proceso = null;
        this.nuevoModelo52Conclusion = null;
        this.cumplimentarEscrutinioPDF = null;
        this.cumplimentarEscrutinioComitePDF = null;
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
        this.cumplimentarPreavisoComitePDF = null;
        this.cumplimentarEscrutinioComitePDF = null;
    }

    public Registro(@NotNull Modelo_4_Especialistas nuevoModelo4Especialistas, @NotNull Modelo_4_Tecnicos nuevoModelo4Tecnicos, @NotNull Modelo_6_1_Especialistas nuevoModelo61Especialistas,
                    @NotNull Modelo_6_2_Especialistas nuevoModelo62Especialistas, @NotNull Modelo_6_3_Especialistas nuevoModelo63Especialistas, @NotNull Modelo_6_1_Tecnicos nuevoModelo61Tecnicos,
                    @NotNull Modelo_6_2_Tecnicos nuevoModelo62Tecnicos, @NotNull Modelo_6_3_Tecnicos nuevoModelo63Tecnicos, @NotNull Modelo_7_1 nuevoModelo71, @NotNull Modelo_7_2 nuevoModelo72,
                    @NotNull Modelo_7_3_Acta_Global nuevoModelo73ActaGlobal, @NotNull Modelo_7_3_Anexo nuevoModelo73Anexo, @NotNull Modelo_7_3_Proceso nuevcModelo73Proces,
                    @NotNull CalendarioComite nuevoCalendarioComite, @NotNull Autorizacion nuevaAutorizacion, @NotNull PrincipalView ventanaPreaviso) throws IOException {
        this.nuevoPreaviso = null;
        this.nuevoModelo51 = null;
        this.nuevoModelo52Proceso = null;
        this.nuevoModelo52Conclusion = null;
        this.ventanaPreaviso = ventanaPreaviso;
        this.directorioManager = new DirectorioManager();
        this.cumplimentarEscrutinioPDF = new CumplimentarEscrutinioPDF(ventanaPreaviso);
        this.rutaElecciones = directorioManager.crearDirectorioElecciones();
        this.cumplimentarPreavisoPDF = null;
        this.cumplimentarPreavisoComitePDF = null;
        this.cumplimentarEscrutinioComitePDF = new CumplimentarEscrutinioComitePDF(ventanaPreaviso);
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
            String[] rutaFormularios = directorioManager.generarRutasFormulariosDelegados();

            for (String rutaFormularioPDF : rutaFormularios) {
                try {
                    procesarFormularioPreavisoDelegados(rutaFormularioPDF, rutaDirectorioEmpresa);
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
    private void procesarFormularioPreavisoDelegados(@NotNull String rutaFormularioPDF, @NotNull Path rutaDirectorioEmpresa) {
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
    public void registrarModelosEscrutinioDelegados(@NotNull Modelo_5_1 nuevoModelo51, @NotNull Modelo_5_2_Proceso nuevoModelo52Proceso, @NotNull Modelo_5_2_Conclusion nuevoModelo52Conclusion, @NotNull Path rutaEmpresa) {
            String[] rutaFormularios = directorioManager.generarRutasFormulariosBuscados(rutaEmpresa);
            for (String rutaFormularioPDF : rutaFormularios) {
                try {
                    procesarFormularioEscrutinioDelegados(rutaFormularioPDF, nuevoModelo51, nuevoModelo52Proceso, nuevoModelo52Conclusion);
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
    private void procesarFormularioEscrutinioDelegados(@NotNull String rutaFormularioPDF, @NotNull Modelo_5_1 nuevoModelo51, @NotNull Modelo_5_2_Proceso nuevoModelo52Proceso, @NotNull Modelo_5_2_Conclusion nuevoModelo52Conclusion) {
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
            String[] rutaFormularios = directorioManager.generarRutasFormulariosComite();

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
            if (rutaFormularioPDF.contains(Constantes.MODELO_6_1_ESPECIALISTAS)) {
                cumplimentarPreavisoComitePDF.modificarCamposTextoModelo6_1EspecialistasPDF(rutaFormularioPDF, rutaDirectorioEmpresa.toString(), nuevoPreaviso);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_6_1_TECNICOS)) {
                cumplimentarPreavisoComitePDF.modificarCamposTextoModelo6_1TecnicosPDF(rutaFormularioPDF, rutaDirectorioEmpresa.toString(), nuevoPreaviso);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_6_2_ESPECIALISTAS)) {
                cumplimentarPreavisoComitePDF.modificarCamposTextoModelo6_2EspecialistasPDF(rutaFormularioPDF, rutaDirectorioEmpresa.toString(), nuevoPreaviso);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_6_2_TECNICOS)) {
                cumplimentarPreavisoComitePDF.modificarCamposTextoModelo6_2TecnicosPDF(rutaFormularioPDF, rutaDirectorioEmpresa.toString(), nuevoPreaviso);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_6_3_ESPECIALISTAS)) {
                cumplimentarPreavisoComitePDF.modificarCamposTextoModelo6_3EspecialistasPDF(rutaFormularioPDF, rutaDirectorioEmpresa.toString(), nuevoPreaviso);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_6_3_TECNICOS)) {
                cumplimentarPreavisoComitePDF.modificarCamposTextoModelo6_3TecnicosPDF(rutaFormularioPDF, rutaDirectorioEmpresa.toString(), nuevoPreaviso);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_7_1)) {
                cumplimentarPreavisoComitePDF.modificarCamposTextoModelo7_1PDF(rutaFormularioPDF, rutaDirectorioEmpresa.toString(), nuevoPreaviso);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_7_2)) {
                cumplimentarPreavisoComitePDF.modificarCamposTextoModelo7_2PDF(rutaFormularioPDF, rutaDirectorioEmpresa.toString(), nuevoPreaviso);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_7_3_ACTA_GLOBAL)) {
                cumplimentarPreavisoComitePDF.modificarCamposTextoModelo7_3ActaGlobalPDF(rutaFormularioPDF, rutaDirectorioEmpresa.toString(), nuevoPreaviso);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_7_3_ANEXO)) {
                cumplimentarPreavisoComitePDF.modificarCamposTextoModelo7_3AnexoPDF(rutaFormularioPDF, rutaDirectorioEmpresa.toString(), nuevoPreaviso);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_7_3_PROCESO)) {
                cumplimentarPreavisoComitePDF.modificarCamposTextoModelo7_3ProcesoPDF(rutaFormularioPDF, rutaDirectorioEmpresa.toString(), nuevoPreaviso);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_9_COMITE)) {
                cumplimentarPreavisoComitePDF.modificarCamposTextoModelo9ComitePDF(rutaFormularioPDF, rutaDirectorioEmpresa.toString(), nuevoPreaviso);
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
     * @param rutaEmpresa La ruta de la empresa.
     */
    public void registrarModelosEscrutinioComite(Modelo_6_1_Especialistas nuevoModelo61Especialistas, Modelo_6_2_Especialistas nuevoModelo62Especialistas,
                                                 Modelo_6_1_Tecnicos nuevoModelo61Tecnicos, Modelo_6_2_Tecnicos nuevoModelo62Tecnicos,
                                                 Modelo_7_3_Acta_Global nuevoModelo73ActaGlobal, Modelo_7_3_Proceso nuevoModelo73Proceso, Path rutaEmpresa) {
        String[] rutaFormularios = directorioManager.generarRutasFormulariosBuscados(rutaEmpresa);
        for (String rutaFormularioPDF : rutaFormularios) {
            try {
                procesarFormularioEscrutinioComite(rutaFormularioPDF, nuevoModelo61Especialistas, nuevoModelo62Especialistas, nuevoModelo61Tecnicos, nuevoModelo62Tecnicos,nuevoModelo73ActaGlobal, nuevoModelo73Proceso);
            } catch (Exception e) {
                ventanaPreaviso.mostrarMensaje(MessageManager.getMessage("error.procesar.formulario") + e.getMessage(), false);
            }
        }
    }

    private void procesarFormularioEscrutinioComite(@NotNull String rutaFormularioPDF, Modelo_6_1_Especialistas nuevoModelo61Especialistas,
                                                    Modelo_6_2_Especialistas nuevoModelo62Especialistas, Modelo_6_1_Tecnicos nuevoModelo61Tecnicos,
                                                    Modelo_6_2_Tecnicos nuevoModelo62Tecnicos, Modelo_7_3_Acta_Global nuevoModelo73ActaGlobal,
                                                    Modelo_7_3_Proceso nuevoModelo73Proceso) {
        try {
            if (rutaFormularioPDF.contains(Constantes.MODELO_6_1_ESPECIALISTAS)) {
                cumplimentarEscrutinioComitePDF.modificarCamposTextoModelo6_1EspecialistasPDF(rutaFormularioPDF, nuevoModelo61Especialistas);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_6_1_TECNICOS)) {
                cumplimentarEscrutinioComitePDF.modificarCamposTextoModelo6_1TecnicosPDF(rutaFormularioPDF, nuevoModelo61Tecnicos);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_6_2_ESPECIALISTAS)) {
                cumplimentarEscrutinioComitePDF.modificarCamposTextoModelo6_2EspecialistasPDF(rutaFormularioPDF, nuevoModelo62Especialistas);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_6_2_TECNICOS)) {
                cumplimentarEscrutinioComitePDF.modificarCamposTextoModelo6_2TecnicosPDF(rutaFormularioPDF, nuevoModelo62Tecnicos);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_6_3_ESPECIALISTAS)) {
                cumplimentarEscrutinioComitePDF.modificarCamposTextoModelo6_3EspecialistasPDF(rutaFormularioPDF, nuevoModelo62Especialistas);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_6_3_TECNICOS)) {
                cumplimentarEscrutinioComitePDF.modificarCamposTextoModelo6_3TecnicosPDF(rutaFormularioPDF, nuevoModelo62Tecnicos);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_7_1)) {
                cumplimentarEscrutinioComitePDF.modificarCamposTextoModelo7_1PDF(rutaFormularioPDF, nuevoModelo61Tecnicos);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_7_3_ACTA_GLOBAL)) {
                cumplimentarEscrutinioComitePDF.modificarCamposTextoModelo7_3ActaGlobalPDF(rutaFormularioPDF, nuevoModelo73ActaGlobal);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_7_3_PROCESO)) {
                cumplimentarEscrutinioComitePDF.modificarCamposTextoModelo7_3ProcesoPDF(rutaFormularioPDF, nuevoModelo61Especialistas, nuevoModelo73Proceso);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_9_COMITE)) {
                cumplimentarEscrutinioComitePDF.modificarCamposTextoModelo9ComitePDF(rutaFormularioPDF, nuevoModelo61Especialistas);
            } else if (rutaFormularioPDF.contains(Constantes.AUTORIZACION)) {
                cumplimentarEscrutinioComitePDF.modificarCamposTextoAutorizacionPDF(rutaFormularioPDF, nuevoModelo73Proceso);
            }
        } catch (Exception e) {
            ventanaPreaviso.mostrarMensaje(MessageManager.getMessage("error.procesar.formulario") + e.getMessage(), false);
        }
    }

    public void registrarModelosEscrutinioComiteUnico(Modelo_6_1_Unico nuevoModelo61Unico, Modelo_6_2_Unico nuevoModelo62unico, Modelo_7_3_Acta_Global nuevoModelo73ActaGlobal, Modelo_7_3_Proceso nuevoModelo73Proceso, Path rutaEmpresa) {
        String[] rutaFormularios = directorioManager.generarRutasFormulariosBuscados(rutaEmpresa);
        for (String rutaFormularioPDF : rutaFormularios) {
            try {
                procesarFormularioEscrutinioComiteUnico(rutaFormularioPDF, nuevoModelo61Unico, nuevoModelo62unico, nuevoModelo73ActaGlobal, nuevoModelo73Proceso);
            } catch (Exception e) {
                ventanaPreaviso.mostrarMensaje(MessageManager.getMessage("error.procesar.formulario") + e.getMessage(), false);
            }
        }
    }

    private void procesarFormularioEscrutinioComiteUnico(String rutaFormularioPDF, Modelo_6_1_Unico nuevoModelo61Unico, Modelo_6_2_Unico nuevoModelo62unico,
                                                         Modelo_7_3_Acta_Global nuevoModelo73ActaGlobal, Modelo_7_3_Proceso nuevoModelo73Proceso) {
        try {
            if (rutaFormularioPDF.contains(Constantes.MODELO_6_1_UNICO)) {
                cumplimentarEscrutinioComitePDF.modificarCamposTextoModelo6_1UnicoPDF(rutaFormularioPDF, nuevoModelo61Unico);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_6_2_UNICO)) {
                cumplimentarEscrutinioComitePDF.modificarCamposTextoModelo6_2UnicoPDF(rutaFormularioPDF, nuevoModelo62unico);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_6_3_UNICO)) {
                cumplimentarEscrutinioComitePDF.modificarCamposTextoModelo6_3UnicoPDF(rutaFormularioPDF, nuevoModelo62unico);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_7_1)) {
                cumplimentarEscrutinioComitePDF.modificarCamposTextoModelo7_1PDF(rutaFormularioPDF, nuevoModelo61Unico);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_7_3_ACTA_GLOBAL)) {
                cumplimentarEscrutinioComitePDF.modificarCamposTextoModelo7_3ActaGlobalPDF(rutaFormularioPDF, nuevoModelo73ActaGlobal);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_7_3_PROCESO)) {
                cumplimentarEscrutinioComitePDF.modificarCamposTextoModelo7_3ProcesoPDF(rutaFormularioPDF, nuevoModelo61Unico, nuevoModelo73Proceso);
            } else if (rutaFormularioPDF.contains(Constantes.MODELO_9_COMITE)) {
                cumplimentarEscrutinioComitePDF.modificarCamposTextoModelo9ComitePDF(rutaFormularioPDF, nuevoModelo61Unico);
            } else if (rutaFormularioPDF.contains(Constantes.AUTORIZACION)) {
                cumplimentarEscrutinioComitePDF.modificarCamposTextoAutorizacionPDF(rutaFormularioPDF, nuevoModelo73Proceso);
            }
        } catch (Exception e) {
            ventanaPreaviso.mostrarMensaje(MessageManager.getMessage("error.procesar.formulario") + e.getMessage(), false);
        }
    }

}
