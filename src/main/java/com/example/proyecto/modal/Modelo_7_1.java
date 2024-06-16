package com.example.proyecto.modal;

import com.example.proyecto.util.*;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

/**
 * Clase que representa el modelo 7.1 para técnicos en el proceso de escrutinio.
 * Extiende de Modelo_6_1_Tecnicos y añade propiedades específicas.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class Modelo_7_1 extends Modelo_6_1_Tecnicos {

    private String horas;
    private String mesLetras;

    /**
     * Constructor vacío.
     */
    public Modelo_7_1() {
    }

    /**
     * Constructor con parámetros iniciales.
     *
     * @param colegio El nombre del colegio.
     * @param nombreEmpresa El nombre de la empresa.
     * @param CIF El CIF de la empresa.
     * @param nombreComercial El nombre comercial de la empresa.
     * @param nombreCentro El nombre del centro.
     * @param direccion La dirección del centro.
     * @param municipio El municipio del centro.
     * @param provincia La provincia del centro.
     * @param promotores Los promotores del proceso.
     * @param fechaConstitucion La fecha de constitución.
     * @param fechaVotacion La fecha de votación.
     * @param municipioElecciones El municipio de las elecciones.
     * @param dia El día de las elecciones.
     * @param mes El mes de las elecciones.
     * @param anioFormateado El año de las elecciones.
     * @throws CumplimentarPDFException Si algún dato es incorrecto.
     */
    public Modelo_7_1(String colegio, String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro, String direccion,
                      String municipio, String provincia, String promotores, String fechaConstitucion, String fechaVotacion,
                      String municipioElecciones, String dia, String mes, String anioFormateado) throws CumplimentarPDFException {
        super(colegio, nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores,
                fechaConstitucion, fechaVotacion, municipioElecciones, dia, mes, anioFormateado);
    }

    /**
     * Constructor completo con todos los parámetros.
     *
     * @param numeroMesa El número de la mesa.
     * @param colegio El nombre del colegio.
     * @param nombreEmpresa El nombre de la empresa.
     * @param CIF El CIF de la empresa.
     * @param nombreComercial El nombre comercial de la empresa.
     * @param nombreCentro El nombre del centro.
     * @param direccion La dirección del centro.
     * @param municipio El municipio del centro.
     * @param provincia La provincia del centro.
     * @param promotores Los promotores del proceso.
     * @param fechaConstitucion La fecha de constitución.
     * @param fechaVotacion La fecha de votación.
     * @param municipioElecciones El municipio de las elecciones.
     * @param horas Las horas de las elecciones.
     * @param dia El día de las elecciones.
     * @param mes El mes de las elecciones.
     * @param mesLetras El mes de las elecciones en letras.
     * @param anioFormateado El año de las elecciones.
     * @param presidente El nombre del presidente.
     * @param vocal El nombre del vocal.
     * @param secretario El nombre del secretario.
     * @param dniPresidente El DNI del presidente.
     * @param dniVocal El DNI del vocal.
     * @param dniSecretario El DNI del secretario.
     * @throws CumplimentarPDFException Si algún dato es incorrecto.
     */
    public Modelo_7_1(String numeroMesa, String colegio, String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro,
                      String direccion, String municipio, String provincia, String promotores, String fechaConstitucion, String fechaVotacion,
                      String municipioElecciones, String horas, String dia, String mes, String mesLetras, String anioFormateado, String presidente,
                      String vocal, String secretario, String dniPresidente, String dniVocal, String dniSecretario) throws CumplimentarPDFException {
        super(numeroMesa, colegio, nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores,
                fechaConstitucion, fechaVotacion, municipioElecciones, horas, dia, mes, anioFormateado, presidente, vocal, secretario, dniPresidente, dniVocal, dniSecretario);
        setHoras(horas);
        setMesLetras(mesLetras);
    }

    /**
     * Obtiene las horas de las elecciones.
     *
     * @return Las horas de las elecciones.
     */
    @Override
    public String getHoras() {
        return horas;
    }

    /**
     * Establece las horas de las elecciones.
     *
     * @param horas Las horas de las elecciones.
     * @throws CumplimentarPDFException Si la hora es inválida.
     */
    @Override
    public void setHoras(String horas) throws CumplimentarPDFException {
        if (!ValidadorCampos.validarHora(horas)) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.hora.invalida"));
        }
        this.horas = horas;
    }

    /**
     * Obtiene el mes en letras.
     *
     * @return El mes en letras.
     */
    public String getMesLetras() {
        return mesLetras;
    }

    /**
     * Establece el mes en letras.
     *
     * @param mesLetras El mes en letras.
     * @throws CumplimentarPDFException Si el mes es inválido.
     */
    public void setMesLetras(@NotNull String mesLetras) throws CumplimentarPDFException {
        if (!mesLetras.matches("[0-9]+") || Integer.parseInt(mesLetras) < Constantes.PRIMER_MES || Integer.parseInt(mesLetras) > Constantes.ULTIMO_MES) {
            throw new CumplimentarPDFException(MessageFormat.format(MessageManager.getMessage("error.mes.preaviso.incorrecto"), Constantes.PRIMER_MES, Constantes.ULTIMO_MES));
        }
        this.mesLetras = mesLetras.length() == 1 ? STR."0\{mesLetras}" : mesLetras;
        this.mesLetras = Meses.obtenerNombrePorNumero(this.mesLetras);
    }

    @Override
    public String toString() {
        return "com.example.proyecto.modal.Modelo_7_1 Cumplimentado";
    }
}
