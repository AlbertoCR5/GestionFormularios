package com.example.proyecto.modal;

import com.example.proyecto.util.*;

import java.util.Objects;

/**
 * Clase que representa a los representantes elegidos del comité.
 * Contiene información sobre el representante, como iniciales, apellidos, DNI, antigüedad, sexo, fecha de nacimiento, colegio y sindicato.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class Representantes_Elegidos_Comite {

    private String altaBaja;
    private String iniciales;
    private String apellidos;
    private String dni;
    private short antiguedad;
    private String sexo;
    private String fechaNacimiento;
    private String colegio;
    private String sindicato;

    /**
     * Constructor que inicializa un representante elegido con los datos necesarios.
     *
     * @param iniciales       Las iniciales del representante.
     * @param apellidos       Los apellidos del representante.
     * @param dni             El DNI del representante.
     * @param antiguedad      La antigüedad del representante en años.
     * @param sexo            El sexo del representante.
     * @param fechaNacimiento La fecha de nacimiento del representante.
     * @param colegio         El colegio al que pertenece el representante.
     * @param sindicato       El sindicato al que pertenece el representante.
     */
    public Representantes_Elegidos_Comite(String iniciales, String apellidos, String dni, short antiguedad, String sexo, String fechaNacimiento, String colegio, String sindicato) {
        this.iniciales = iniciales;
        this.apellidos = apellidos;
        this.dni = dni;
        this.antiguedad = antiguedad;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.colegio = colegio;
        this.sindicato = sindicato;
    }

    /**
     * Constructor que inicializa un representante elegido con los datos necesarios incluyendo el estado de alta o baja.
     *
     * @param altaBaja        Indica si el representante está de alta (A) o baja (B).
     * @param iniciales       Las iniciales del representante.
     * @param apellidos       Los apellidos del representante.
     * @param dni             El DNI del representante.
     * @param antiguedad      La antigüedad del representante en años.
     * @param sexo            El sexo del representante.
     * @param fechaNacimiento La fecha de nacimiento del representante.
     * @param colegio         El colegio al que pertenece el representante.
     * @param sindicato       El sindicato al que pertenece el representante.
     */
    public Representantes_Elegidos_Comite(String altaBaja, String iniciales, String apellidos, String dni, short antiguedad,
                                          String sexo, String fechaNacimiento, String colegio, String sindicato) {
        this.altaBaja = altaBaja;
        this.iniciales = iniciales;
        this.apellidos = apellidos;
        this.dni = dni;
        this.antiguedad = antiguedad;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.colegio = colegio;
        this.sindicato = sindicato;
    }

    public String getAltaBaja() {
        return altaBaja;
    }

    public void setAltaBaja(String altaBaja) {
        this.altaBaja = altaBaja;
    }

    public String getIniciales() {
        return iniciales;
    }

    public void setIniciales(String iniciales) throws CumplimentarPDFException {
        if (iniciales.isEmpty()) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.iniciales.vacio"));
        }
        if (iniciales.length() > 2) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.iniciales.longitud"));
        }
        this.iniciales = iniciales;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) throws CumplimentarPDFException {
        if (!ValidadorCampos.verificarDNI(dni)) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.dni.incorrecto"));
        }
        this.dni = dni;
    }

    public short getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(short antiguedad) throws CumplimentarPDFException {
        if (antiguedad < 3 || antiguedad > 564) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.antiguedad.incorrecta"));
        }
        this.antiguedad = antiguedad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) throws CumplimentarPDFException {
        ValidadorFecha validarFecha = new ValidadorFecha();
        if (ValidadorFecha.esFormatoFechaNoValido(fechaNacimiento)) {
            throw new CumplimentarPDFException(String.format("ERROR, formato de fecha incorrecto -->%s\n", Constantes.FORMATO_FECHA));
        }
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getColegio() {
        return colegio;
    }

    public void setColegio(String colegio) {
        this.colegio = colegio;
    }

    public String getSindicato() {
        return sindicato;
    }

    public void setSindicato(String sindicato) {
        this.sindicato = sindicato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Representantes_Elegidos_Comite that = (Representantes_Elegidos_Comite) o;
        return Objects.equals(dni, that.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public String toString() {
        return "Representantes_Elegidos_Comite{" +
                "altaBaja='" + altaBaja + '\'' +
                ", iniciales='" + iniciales + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", dni='" + dni + '\'' +
                ", antiguedad=" + antiguedad +
                ", sexo='" + sexo + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", colegio='" + colegio + '\'' +
                ", sindicato='" + sindicato + '\'' +
                '}';
    }
}
