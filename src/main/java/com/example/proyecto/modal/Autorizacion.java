package com.example.proyecto.modal;

import org.jetbrains.annotations.NotNull;

/**
 * La clase `Autorizacion` extiende la clase `Preaviso`.
 * Representa el modelo con el cual el presidente de la mesa nos autoriza a presentar la documentación de manera telemática.
 *
 * @author Alberto Castro <AlbertoCastrovas@gmail.com>
 */
public class Autorizacion extends Preaviso {

    // Variables privadas que representan los datos de la autorización.
    private String presidente;
    private String dia;
    private String mes;
    private String anio;

    // Constructor por defecto.
    public Autorizacion() {
    }

    /**
     * Constructor que inicializa `nombreEmpresa`, `nombreCentro` y `municipio`.
     *
     * @param nombreEmpresa El nombre de la empresa.
     * @param nombreCentro  El nombre del centro.
     * @param municipio     El municipio.
     */
    public Autorizacion(String nombreEmpresa, String nombreCentro, String municipio) {
        super(nombreEmpresa, nombreCentro, municipio);
    }

    /**
     * Constructor que inicializa todos los atributos de la clase.
     *
     * @param nombreEmpresa El nombre de la empresa.
     * @param nombreCentro  El nombre del centro.
     * @param municipio     El municipio.
     * @param presidente    El nombre del presidente.
     * @param dia           El día.
     * @param mes           El mes.
     * @param anio          El año.
     */
    public Autorizacion(String nombreEmpresa, String nombreCentro, String municipio, String presidente, String dia, String mes, String anio) {
        super(nombreEmpresa, nombreCentro, municipio);
        this.presidente = presidente;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    // Métodos Getters y Setters
    public String getPresidente() {
        return presidente;
    }

    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnio() {
        return anio;
    }

    /**
     * Establece el valor del año, tomando solo los últimos dos dígitos.
     *
     * @param anio El año a establecer. Debe ser una cadena de cuatro dígitos representando un año.
     * @throws NullPointerException           si el año proporcionado es nulo.
     * @throws StringIndexOutOfBoundsException si la longitud del año es menor a dos caracteres.
     */
    public void setAnio(@NotNull String anio) {
        this.anio = anio.substring(anio.length() - 2);
    }

    @Override
    public String toString() {
        return "Autorizacion{presidente='" + presidente + "', dia='" + dia + "', mes='" + mes + "', anio='" + anio + "'}";
    }
}
