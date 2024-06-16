package com.example.proyecto.modal;

import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.MessageManager;

/**
 * Clase que representa el modelo 4 para técnicos en el proceso de elecciones.
 * Extiende de Modelo_3 y añade propiedades específicas.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class Modelo_4_Tecnicos extends Modelo_3 {

    private String numeroMesa;
    private String colegio;
    private int totalElectores;
    private int varonesElectores;
    private int mujeresElectoras;

    /**
     * Constructor vacío.
     */
    public Modelo_4_Tecnicos() {
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
     * @param municipioElecciones El municipio de las elecciones.
     * @param dia El día de las elecciones.
     * @param mes El mes de las elecciones.
     * @param anioFormateado El año de las elecciones.
     * @throws CumplimentarPDFException Si algún dato es incorrecto.
     */
    public Modelo_4_Tecnicos(String colegio, String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro,
                             String direccion, String municipio, String provincia, String promotores, String fechaConstitucion,
                             String municipioElecciones, String dia, String mes, String anioFormateado) throws CumplimentarPDFException {
        super(nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores, fechaConstitucion, municipioElecciones, dia, mes, anioFormateado);
        this.colegio = colegio;
    }

    /**
     * Constructor con más parámetros.
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
    public Modelo_4_Tecnicos(String numeroMesa, String colegio, String nombreEmpresa, String CIF, String nombreComercial,
                             String nombreCentro, String direccion, String municipio, String provincia, String promotores,
                             String fechaConstitucion, String municipioElecciones, String horas, String dia, String mes,
                             String anioFormateado, String presidente, String vocal, String secretario, String dniPresidente,
                             String dniVocal, String dniSecretario) throws CumplimentarPDFException {
        super(nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores, fechaConstitucion, municipioElecciones, horas, dia, mes, anioFormateado, presidente, vocal, secretario, dniPresidente, dniVocal, dniSecretario);
        this.numeroMesa = numeroMesa;
        this.colegio = colegio;
    }

    /**
     * Constructor con todos los parámetros.
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
     * @param totalElectores El total de electores.
     * @param varonesElectores El total de varones electores.
     * @param mujeresElectoras El total de mujeres electoras.
     * @param promotores Los promotores del proceso.
     * @param fechaConstitucion La fecha de constitución.
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
    public Modelo_4_Tecnicos(String numeroMesa, String colegio, String nombreEmpresa, String CIF, String nombreComercial,
                             String nombreCentro, String direccion, String municipio, String provincia, int totalElectores,
                             int varonesElectores, int mujeresElectoras, String promotores, String fechaConstitucion,
                             String municipioElecciones, String horas, String dia, String mes, String anioFormateado,
                             String presidente, String vocal, String secretario, String dniPresidente, String dniVocal,
                             String dniSecretario) throws CumplimentarPDFException {
        super(nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores, fechaConstitucion, municipioElecciones, horas, dia, mes, anioFormateado, presidente, vocal, secretario, dniPresidente, dniVocal, dniSecretario);
        this.numeroMesa = numeroMesa;
        this.colegio = colegio;
        setTotalElectores(totalElectores);
        setVaronesElectores(varonesElectores);
        setMujeresElectoras(mujeresElectoras);
    }

    public String getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(String numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public String getColegio() {
        return colegio;
    }

    public void setColegio(String colegio) {
        this.colegio = colegio;
    }

    public int getTotalElectores() {
        return totalElectores;
    }

    public void setTotalElectores(int totalElectores) throws CumplimentarPDFException {
        if (totalElectores <= Constantes.MAXIMO_ELECTORES_DELEGADOS) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.total.electores.incorrecto"));
        }
        this.totalElectores = totalElectores;
    }

    public int getVaronesElectores() {
        return varonesElectores;
    }

    public void setVaronesElectores(int varonesElectores) throws CumplimentarPDFException {
        if (varonesElectores > totalElectores) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.varones.electores.incorrecto"));
        }
        this.varonesElectores = varonesElectores;
        this.mujeresElectoras = totalElectores - varonesElectores;
    }

    public int getMujeresElectoras() {
        return mujeresElectoras;
    }

    public void setMujeresElectoras(int mujeresElectoras) {
        this.mujeresElectoras = mujeresElectoras;
    }

    @Override
    public String toString() {
        return STR."Modelo_4_\{colegio} Cumplimentado";
    }
}
