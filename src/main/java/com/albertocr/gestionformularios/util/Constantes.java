package com.albertocr.gestionformularios.util;

import com.albertocr.gestionformularios.model.TipoColegioElectoral;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase que contiene constantes de negocio y de configuración de la aplicación.
 * <p>
 * Esta clase no debe contener constantes relacionadas con la interfaz de usuario (UI),
 * las cuales se encuentran en {@link UIConstantes}.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.3
 */
public final class Constantes {

    private Constantes() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada.");
    }

    // --- Información General ---
    public static final String PROMOTORES = "UNIÓN GENERAL DE TRABAJADORES (UGT)";
    public static final String EXTENSION_ARCHIVO = ".pdf";

    // --- Reglas de Negocio y Validación ---
    public static final int MINIMO_ELECTORES = 6;
    public static final int MAXIMO_ELECTORES_DELEGADOS = 50;
    public static final int MAXIMO_1_DELEGADO = 30;
    public static final int DIAS_ENTRE_PREAVISO_Y_CONSTITUCION = 32;

    // --- Nombres de Archivos y Plantillas PDF (Delegados) ---
    public static final String PREAVISO = "preaviso";
    public static final String AUTORIZACION = "autorizacion";
    public static final String CALENDARIO_DELEGADOS = "calendario_delegado";
    public static final String MODELO_3 = "modelo_3";
    public static final String MODELO_5_1 = "modelo_5_1";
    public static final String MODELO_5_2_PROCESO = "modelo_5_2_proceso";
    public static final String MODELO_5_2_CONCLUSION = "modelo_5_2_conclusion";
    public static final String MODELO_9 = "modelo_9";
    public static final String[] DOCUMENTACION_DELEGADOS = {
            PREAVISO, CALENDARIO_DELEGADOS, MODELO_3, MODELO_5_1, MODELO_5_2_PROCESO,
            MODELO_5_2_CONCLUSION, MODELO_9, AUTORIZACION
    };

    public static final String[] MODELOS_ESCRUTINIO_DELEGADOS = {
            MODELO_5_1, MODELO_5_2_PROCESO, MODELO_5_2_CONCLUSION, MODELO_9
    };

    // --- Nombres de Archivos y Plantillas PDF (Comité) ---
    public static final String CALENDARIO_COMITE = "calendario_comite";
    public static final String MODELO_4_ESPECIALISTAS = "modelo_4_Especialistas";
    public static final String MODELO_4_TECNICOS = "modelo_4_Tecnicos";
    public static final String MODELO_4_UNICO = "modelo_4_Unico";
    public static final String MODELO_6_1_ESPECIALISTAS = "modelo_6_1_Especialistas";
    public static final String MODELO_6_1_TECNICOS = "modelo_6_1_Tecnicos";
    public static final String MODELO_6_1_UNICO = "modelo_6_1_Unico";
    public static final String MODELO_6_2_ESPECIALISTAS = "modelo_6_2_Especialistas";
    public static final String MODELO_6_2_TECNICOS = "modelo_6_2_Tecnicos";
    public static final String MODELO_6_2_UNICO = "modelo_6_2_Unico";
    public static final String MODELO_6_3_ESPECIALISTAS = "modelo_6_3_Especialistas";
    public static final String MODELO_6_3_TECNICOS = "modelo_6_3_Tecnicos";
    public static final String MODELO_6_3_UNICO = "modelo_6_3_Unico";
    public static final String MODELO_7_1 = "modelo_7_1";
    public static final String MODELO_7_2 = "modelo_7_2";
    public static final String MODELO_7_3_ACTA_GLOBAL = "modelo_7_3_acta_global";
    public static final String MODELO_7_3_ANEXO = "modelo_7_3_anexo";
    public static final String MODELO_7_3_PROCESO = "modelo_7_3_proceso";
    public static final String[] DOCUMENTACION_COMITE = {
            AUTORIZACION, PREAVISO, CALENDARIO_COMITE,
            MODELO_3,
            MODELO_4_ESPECIALISTAS, MODELO_4_TECNICOS, MODELO_4_UNICO,
            MODELO_6_1_ESPECIALISTAS, MODELO_6_1_TECNICOS, MODELO_6_1_UNICO,
            MODELO_6_2_ESPECIALISTAS, MODELO_6_2_TECNICOS, MODELO_6_2_UNICO,
            MODELO_6_3_ESPECIALISTAS, MODELO_6_3_TECNICOS, MODELO_6_3_UNICO,
            MODELO_7_1, MODELO_7_2, MODELO_7_3_ACTA_GLOBAL, MODELO_7_3_ANEXO, MODELO_7_3_PROCESO,
            MODELO_9
    };

    private static final List<String> DOCUMENTOS_EXCLUIR_PARA_COLEGIO_UNICO = Arrays.asList(
            MODELO_4_ESPECIALISTAS, MODELO_4_TECNICOS,
            MODELO_6_1_ESPECIALISTAS, MODELO_6_1_TECNICOS,
            MODELO_6_2_ESPECIALISTAS, MODELO_6_2_TECNICOS,
            MODELO_6_3_ESPECIALISTAS, MODELO_6_3_TECNICOS
    );

    private static final List<String> DOCUMENTOS_EXCLUIR_PARA_VARIOS_COLEGIOS = Arrays.asList(
            MODELO_4_UNICO,
            MODELO_6_1_UNICO,
            MODELO_6_2_UNICO,
            MODELO_6_3_UNICO
    );

    public static List<String> getDocumentacionComite(TipoColegioElectoral tipoColegio) {
        if (tipoColegio == null) {
            return Collections.emptyList();
        }

        switch (tipoColegio) {
            case UNICO:
                return Arrays.stream(DOCUMENTACION_COMITE)
                        .filter(doc -> !DOCUMENTOS_EXCLUIR_PARA_COLEGIO_UNICO.contains(doc))
                        .collect(Collectors.toList());
            case DOS_COLEGIOS:
                return Arrays.stream(DOCUMENTACION_COMITE)
                        .filter(doc -> !DOCUMENTOS_EXCLUIR_PARA_VARIOS_COLEGIOS.contains(doc))
                        .collect(Collectors.toList());
            case TODOS:
                return Arrays.asList(DOCUMENTACION_COMITE);
            case NO_APLICA:
                return Collections.emptyList();
            default:
                throw new IllegalArgumentException("Tipo de colegio electoral no soportado: " + tipoColegio);
        }
    }
}
