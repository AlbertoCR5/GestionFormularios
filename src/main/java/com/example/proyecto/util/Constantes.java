package com.example.proyecto.util;

import com.example.proyecto.modal.DatabaseManager;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

/**
 * Clase que contiene constantes utilizadas en el proyecto.
 */
public class Constantes {

    // Información de promotores
    public static final String PROMOTORES = "UNIÓN GENERAL DE TRABAJADORES (UGT)";

    // Formatos de fecha y hora
    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final String FORMATO_HORA = "HH:mm";

    // Constantes de fechas
    public static final short PRIMER_DIA_MES = 1;
    public static final short ULTIMO_DIA_MES = 31;
    public static final short PRIMER_MES = 1;
    public static final short ULTIMO_MES = 12;

    // Constantes de validación
    public static final short DIGITOS_CODIGO_POSTAL = 5;
    public static final short DIGITOS_ISS = 11;
    public static final int MINIMO_ELECTORES = 6;
    public static final int MAXIMO_ELECTORES_DELEGADOS = 50;
    public static final int MAXIMO_1_DELEGADO = 30;
    public static final int DIAS_ENTRE_PREAVISO_Y_CONSTITUCION = 32;

    // Cantidad de representantes
    public static final int CANTIDAD_REPRESENTANTES_3 = 3;
    public static final int CANTIDAD_REPRESENTANTES_1 = 1;

    // Rutas de archivos
    public static final String RUTA_BASE = String.format("%s%sDocuments", System.getProperty("user.home"), File.separator);
    public static final String NOMBRE_DIRECTORIO = "Elecciones";
    public static final String DATABASE = String.format("%s%s%s", RUTA_BASE, File.separator, NOMBRE_DIRECTORIO);
    public static final String DATABASE_URL = String.format("jdbc:sqlite:%s%s%s.db", DATABASE, File.separator, NOMBRE_DIRECTORIO);

    // Rutas relativas
    public static final String RUTA_DELEGADOS = String.format("%s\\src\\main\\resources\\Delegados", System.getProperty("user.dir"));
    public static final String RUTA_DELEGADOS_JAR = "/Delegados";
    public static final String RUTA_COMITE_JAR = "/Comite";
    public static final String RUTA_TEMPORAL = System.getProperty("java.io.tmpdir") + "/proyecto_temp";

    // Salidas sindicales
    public static final String SALIDAS_SINDICALES = "Salidas Sindicales\\";
    public static final String SALIDA_SINDICAL_COMPLETA = "Salida Sindical Completa";
    public static final String SALIDA_SINDICAL_HORAS = "Salida Sindical Horas";

    // Ruta de preaviso
    public static final String PREAVISO = "preaviso";

    // Directorios de actas para delegados
    public static final String AUTORIZACION = "autorizacion";
    public static final String DELEGADO_PREVENCION = "anexo_delegado_prevencion";
    public static final String CALENDARIO_DELEGADOS = "calendario_delegado";
    public static final String MODELO_3 = "modelo_3";
    public static final String MODELO_5_1 = "modelo_5_1";
    public static final String MODELO_5_2_PROCESO = "modelo_5_2_proceso";
    public static final String MODELO_5_2_CONCLUSION = "modelo_5_2_conclusion";
    public static final String MODELO_9 = "modelo_9";
    public static final String FICHA_DELEGADO = "ficha_delegado";
    public static final String[] DOCUMENTACION_DELEGADOS = {
            PREAVISO, CALENDARIO_DELEGADOS, MODELO_3, MODELO_5_1, MODELO_5_2_PROCESO,
            MODELO_5_2_CONCLUSION, MODELO_9, AUTORIZACION
    };

    // Directorios de actas para comité
    public static final String CALENDARIO_COMITE = "calendario_comite";
    public static final String MODELO_4_ESPECIALISTAS = "modelo_4_Especialistas";
    public static final String MODELO_4_TECNICOS = "modelo_4_Tecnicos";
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
    public static final String MODELO_9_COMITE = "modelo_9";
    public static final String[] DOCUMENTACION_COMITE = {
            PREAVISO, CALENDARIO_COMITE, MODELO_3, MODELO_4_ESPECIALISTAS, MODELO_4_TECNICOS,
            MODELO_6_1_ESPECIALISTAS, MODELO_6_1_TECNICOS, MODELO_6_2_ESPECIALISTAS,
            MODELO_6_2_TECNICOS, MODELO_6_3_ESPECIALISTAS, MODELO_6_3_TECNICOS, MODELO_7_1,
            MODELO_7_2, MODELO_7_3_ACTA_GLOBAL, MODELO_7_3_ANEXO, MODELO_7_3_PROCESO,
            MODELO_9_COMITE, AUTORIZACION
    };
    public static final String[] DOCUMENTACION_COMITE_UNICO = {
            PREAVISO, CALENDARIO_COMITE, MODELO_3, MODELO_4_ESPECIALISTAS, MODELO_4_TECNICOS,
            MODELO_6_1_UNICO, MODELO_6_2_UNICO, MODELO_6_3_UNICO, MODELO_7_1,
            MODELO_7_2, MODELO_7_3_ACTA_GLOBAL, MODELO_7_3_ANEXO, MODELO_7_3_PROCESO,
            MODELO_9_COMITE, AUTORIZACION
    };
    public static final String IMPRESION_COMITE = "ImprimirComite.ps1";

    // Impresión de empresa
    public static final String IMPRESION_EMPRESA = "ImprimirEmpresa.ps1";

    // Extensiones y formatos de archivo
    public static final String EXTENSION_ARCHIVO = ".pdf";
    public static final String FORMATO_PREAVISO = "ppp/aaaa";
    public static final String FORMATO_ISS = "\\d{11}";
    public static final String FORMATO_CONVENIO = "^\\d{14}$";

    // Niveles Formación en PRL
    public static final String BASICA = "Básica";
    public static final String MEDIA = "Media";
    public static final String SUPERIOR = "Superior";

    // Otras constantes
    public static final double ANCHO_BOTON = 250;
    public static final String BOLD_UNDERLINED_STYLE = "-fx-font-size: 16px; -fx-font-weight: bold; -fx-underline: true;";
    public static final String FONT_SIZE_14_FONT_WEIGHT_BOLD = "-fx-font-size: 14px; -fx-font-weight: bold;";
    public static final Logger LOGGER = Logger.getLogger(DatabaseManager.class.getName());
    public static final String ESTILO_ETIQUETA_14PX = "-fx-font-size: 14px;";
    public static final String ESTILO_ETIQUETA_12PX = "-fx-font-size: 12px;";
    public static final String OPACIDAD_DESACTIVADO = "-fx-opacity: 0.5;";
    public static final int ESPACIADO_VBOX = 10;
    public static final int ESPACIADO_HGAP = 10;
    public static final int ESPACIADO_VGAP = 10;
    public static final int ESPACIADO_PADDING = 20;
    public static final int ANCHO_VENTANA_PREAVISO = 600;
    public static final int ALTO_VENTANA_PREAVISO = 700;
    public static final int ANCHO_DATEPICKER = 110;
    public static final String FONT_WEIGHT_BOLD = "-fx-font-weight: bold";
    public static final double DURACION_VALIDACION = 0.1;

}
