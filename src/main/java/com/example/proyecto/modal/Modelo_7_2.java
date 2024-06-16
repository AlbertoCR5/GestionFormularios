package com.example.proyecto.modal;

import com.example.proyecto.util.CumplimentarPDFException;

/**
 * Clase que representa el modelo 7.2 para especialistas en el proceso de escrutinio.
 * Extiende de Modelo_6_1_Especialistas y añade propiedades específicas.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class Modelo_7_2 extends Modelo_6_1_Especialistas {

    private String[] reclamaciones;

    /**
     * Constructor vacío.
     */
    public Modelo_7_2() {
    }

    /**
     * Constructor con parámetros iniciales.
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
     * @param anioFormateado El año de las elecciones.
     * @param presidente El nombre del presidente.
     * @param vocal El nombre del vocal.
     * @param secretario El nombre del secretario.
     * @param dniPresidente El DNI del presidente.
     * @param dniVocal El DNI del vocal.
     * @param dniSecretario El DNI del secretario.
     * @throws CumplimentarPDFException Si algún dato es incorrecto.
     */
    public Modelo_7_2(String numeroMesa, String colegio, String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro,
                      String direccion, String municipio, String provincia, String promotores, String fechaConstitucion, String fechaVotacion,
                      String municipioElecciones, String horas, String dia, String mes, String anioFormateado, String presidente,
                      String vocal, String secretario, String dniPresidente, String dniVocal, String dniSecretario) throws CumplimentarPDFException {
        super(numeroMesa, colegio, nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores, fechaConstitucion,
                fechaVotacion, municipioElecciones, horas, dia, mes, anioFormateado, presidente, vocal, secretario, dniPresidente, dniVocal, dniSecretario);
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
     * @param anioFormateado El año de las elecciones.
     * @param presidente El nombre del presidente.
     * @param vocal El nombre del vocal.
     * @param secretario El nombre del secretario.
     * @param dniPresidente El DNI del presidente.
     * @param dniVocal El DNI del vocal.
     * @param dniSecretario El DNI del secretario.
     * @param reclamaciones Las reclamaciones del proceso.
     * @throws CumplimentarPDFException Si algún dato es incorrecto.
     */
    public Modelo_7_2(String numeroMesa, String colegio, String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro,
                      String direccion, String municipio, String provincia, String promotores, String fechaConstitucion, String fechaVotacion,
                      String municipioElecciones, String horas, String dia, String mes, String anioFormateado, String presidente, String vocal,
                      String secretario, String dniPresidente, String dniVocal, String dniSecretario, String[] reclamaciones) throws CumplimentarPDFException {
        super(numeroMesa, colegio, nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores, fechaConstitucion,
                fechaVotacion, municipioElecciones, horas, dia, mes, anioFormateado, presidente, vocal, secretario, dniPresidente, dniVocal, dniSecretario);
        setReclamaciones(reclamaciones);
    }

    /**
     * Obtiene las reclamaciones del proceso.
     *
     * @return Un array de reclamaciones.
     */
    public String[] getReclamaciones() {
        return reclamaciones;
    }

    /**
     * Establece las reclamaciones del proceso.
     *
     * @param reclamaciones Un array de reclamaciones.
     */
    public void setReclamaciones(String[] reclamaciones) {
        this.reclamaciones = reclamaciones;
    }

    @Override
    public String toString() {
        return "com.example.proyecto.modal.Modelo_7_2 Cumplimentado";
    }
}
