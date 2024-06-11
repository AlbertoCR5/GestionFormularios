package com.example.proyecto.modal;

import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.ValidadorFecha;

/**
 * La clase `CalendarioDelegado` extiende la clase `Preaviso`.
 * Representa el modelo con el cual se elabora el calendario para la elección de delegados.
 * Hereda algunas propiedades y métodos de `Preaviso`.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 */
public class CalendarioDelegado extends Preaviso {

    // Instancia de ValidadorFecha para extraer y validar fechas.
    ValidadorFecha extraerFecha = new ValidadorFecha();

    // Atributos específicos de la clase CalendarioDelegado.
    private String diaEscrutinio;
    private String mesEscrutinio;
    private String anioEscrutinio;

    /**
     * Constructor por defecto.
     */
    public CalendarioDelegado() {
    }

    /**
     * Constructor con parámetros que inicializa los atributos de la clase.
     *
     * @param nombreEmpresa Nombre de la empresa.
     * @param fechaConstitucion Fecha de constitución de la empresa.
     * @param diaEscrutinio Día del escrutinio.
     * @param mesEscrutinio Mes del escrutinio.
     * @param anioEscrutinio Año del escrutinio.
     * @throws CumplimentarPDFException Si se produce un error al completar el PDF.
     */
    public CalendarioDelegado(String nombreEmpresa, String fechaConstitucion, String diaEscrutinio, String mesEscrutinio, String anioEscrutinio) throws CumplimentarPDFException {
        super(nombreEmpresa, fechaConstitucion);
        setDiaEscrutinio(diaEscrutinio);
        setMesEscrutinio(mesEscrutinio);
        setAnioEscrutinio(anioEscrutinio);
    }

    /**
     * Obtiene el día del escrutinio.
     *
     * @return Día del escrutinio.
     */
    public String getDiaEscrutinio() {
        return diaEscrutinio;
    }

    /**
     * Establece el día del escrutinio.
     *
     * @param diaEscrutinio Día del escrutinio.
     * @throws CumplimentarPDFException Si el formato del día es incorrecto.
     */
    public void setDiaEscrutinio(String diaEscrutinio) throws CumplimentarPDFException {
        if (extraerFecha.esFormatoFechaValido(diaEscrutinio)) {
            this.diaEscrutinio = extraerFecha.getDia();
        } else {
            throw new CumplimentarPDFException("ERROR, Día del escrutinio incorrecto");
        }
    }

    /**
     * Obtiene el mes del escrutinio.
     *
     * @return Mes del escrutinio.
     */
    public String getMesEscrutinio() {
        return mesEscrutinio;
    }

    /**
     * Establece el mes del escrutinio.
     *
     * @param mesEscrutinio Mes del escrutinio.
     */
    public void setMesEscrutinio(String mesEscrutinio) {
        this.mesEscrutinio = mesEscrutinio;
    }

    /**
     * Obtiene el año del escrutinio.
     *
     * @return Año del escrutinio.
     */
    public String getAnioEscrutinio() {
        return anioEscrutinio;
    }

    /**
     * Establece el año del escrutinio.
     *
     * @param anioEscrutinio Año del escrutinio.
     * @throws CumplimentarPDFException Si el formato del año es incorrecto.
     */
    public void setAnioEscrutinio(String anioEscrutinio) throws CumplimentarPDFException {
        if (extraerFecha.esFormatoFechaValido(anioEscrutinio)) {
            this.anioEscrutinio = extraerFecha.getAnioFormateado();
        } else {
            throw new CumplimentarPDFException("ERROR, Año del escrutinio incorrecto");
        }
    }

    @Override
    public String toString() {
        return "Calendario de Delegado con fecha de constitución "
                + getDiaEscrutinio().concat("/").concat(getMesEscrutinio().concat("/").concat(getAnioEscrutinio())) + " Generado";
    }
}
