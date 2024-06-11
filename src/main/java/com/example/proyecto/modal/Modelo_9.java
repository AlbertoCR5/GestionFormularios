package com.example.proyecto.modal;

import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.ValidadorCampos;
import com.example.proyecto.util.ValidadorFecha;

/**
 * Esta clase representa el Modelo_9 en el cual se certifica que se ha elegido un total de "x" representantes
 * y los sindicatos a los que representan.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 */
public class Modelo_9 extends Preaviso {

    private final ValidadorCampos verificarDNI = new ValidadorCampos();
    private final ValidadorFecha extraerFecha = new ValidadorFecha();

    private String presidente;
    private String dniPresidente;
    private String dia;
    private String mes;
    private String anio;
    private short totalRepresentantes;
    private String sindicato1;
    private String representantesElegidos1;
    private String sindicato2;
    private String representantesElegidos2;
    private String sindicato3;
    private String representantesElegidos3;

    /**
     * Constructor por defecto.
     */
    public Modelo_9() {
    }

    /**
     * Constructor que inicializa la clase con los parámetros básicos.
     *
     * @param nombreEmpresa Nombre de la empresa.
     * @param CIF           CIF de la empresa.
     * @param nombreComercial Nombre comercial de la empresa.
     * @param nombreCentro Nombre del centro.
     * @param direccion Dirección de la empresa.
     * @param municipio Municipio de la empresa.
     * @param provincia Provincia de la empresa.
     * @throws CumplimentarPDFException Si hay un error en la creación del PDF.
     */
    public Modelo_9(String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro, String direccion, String municipio, String provincia) throws CumplimentarPDFException {
        super(nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia);
    }

    /**
     * Constructor que inicializa la clase con todos los parámetros.
     *
     * @param nombreEmpresa Nombre de la empresa.
     * @param CIF           CIF de la empresa.
     * @param nombreComercial Nombre comercial de la empresa.
     * @param nombreCentro Nombre del centro.
     * @param direccion Dirección de la empresa.
     * @param municipio Municipio de la empresa.
     * @param provincia Provincia de la empresa.
     * @param presidente Nombre del presidente.
     * @param dniPresidente DNI del presidente.
     * @param dia Día de la constitución de la mesa.
     * @param mes Mes de la constitución de la mesa.
     * @param anio Año de la constitución de la mesa.
     * @param totalRepresentantes Total de representantes.
     * @param sindicato1 Primer sindicato.
     * @param representantesElegidos1 Representantes elegidos del primer sindicato.
     * @param sindicato2 Segundo sindicato.
     * @param representantesElegidos2 Representantes elegidos del segundo sindicato.
     * @param sindicato3 Tercer sindicato.
     * @param representantesElegidos3 Representantes elegidos del tercer sindicato.
     * @throws CumplimentarPDFException Si hay un error en la creación del PDF.
     */
    public Modelo_9(String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro, String direccion, String municipio,
                    String provincia, String presidente, String dniPresidente, String dia, String mes, String anio,
                    short totalRepresentantes, String sindicato1, String representantesElegidos1, String sindicato2,
                    String representantesElegidos2, String sindicato3, String representantesElegidos3) throws CumplimentarPDFException {
        super(nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia);
        this.presidente = presidente;
        setDniPresidente(dniPresidente);
        setDia(dia);
        setMes(mes);
        setAnio(anio);
        setTotalRepresentantes(totalRepresentantes);
        this.sindicato1 = sindicato1;
        this.representantesElegidos1 = representantesElegidos1;
        this.sindicato2 = sindicato2;
        this.representantesElegidos2 = representantesElegidos2;
        this.sindicato3 = sindicato3;
        this.representantesElegidos3 = representantesElegidos3;
    }

    public String getPresidente() {
        return presidente;
    }

    /**
     * Establece el nombre del presidente.
     *
     * @param presidente el nombre del presidente.
     */
    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    public String getDniPresidente() {
        return dniPresidente;
    }

    /**
     * Establece el DNI del presidente.
     *
     * @param dniPresidente el DNI del presidente.
     * @throws CumplimentarPDFException si el DNI no es válido.
     */
    public void setDniPresidente(String dniPresidente) throws CumplimentarPDFException {
        if (!verificarDNI.verificarDNI(dniPresidente)) {
            throw new CumplimentarPDFException("ERROR, DNI introducido incorrecto");
        }
        this.dniPresidente = dniPresidente;
    }

    public String getDia() {
        return dia;
    }

    /**
     * Establece el día de la constitución de la mesa.
     *
     * @param dia el día de la constitución de la mesa.
     */
    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    /**
     * Establece el mes de la constitución de la mesa.
     *
     * @param mes el mes de la constitución de la mesa.
     */
    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnio() {
        return anio;
    }

    /**
     * Establece el año de la constitución de la mesa.
     *
     * @param anio el año de la constitución de la mesa.
     */
    public void setAnio(String anio) {
        this.anio = anio;
    }

    public short getTotalRepresentantes() {
        return totalRepresentantes;
    }

    /**
     * Establece el total de representantes.
     *
     * @param totalRepresentantes el total de representantes.
     */
    public void setTotalRepresentantes(short totalRepresentantes) {
        this.totalRepresentantes = totalRepresentantes;
    }

    public String getSindicato1() {
        return sindicato1;
    }

    /**
     * Establece el nombre del primer sindicato.
     *
     * @param sindicato1 el nombre del primer sindicato.
     */
    public void setSindicato1(String sindicato1) {
        this.sindicato1 = sindicato1;
    }

    public String getRepresentantesElegidos1() {
        return representantesElegidos1;
    }

    /**
     * Establece los representantes elegidos del primer sindicato.
     *
     * @param representantesElegidos1 los representantes elegidos del primer sindicato.
     */
    public void setRepresentantesElegidos1(String representantesElegidos1) {
        this.representantesElegidos1 = representantesElegidos1;
    }

    public String getSindicato2() {
        return sindicato2;
    }

    /**
     * Establece el nombre del segundo sindicato.
     *
     * @param sindicato2 el nombre del segundo sindicato.
     */
    public void setSindicato2(String sindicato2) {
        this.sindicato2 = sindicato2;
    }

    public String getRepresentantesElegidos2() {
        return representantesElegidos2;
    }

    /**
     * Establece los representantes elegidos del segundo sindicato.
     *
     * @param representantesElegidos2 los representantes elegidos del segundo sindicato.
     */
    public void setRepresentantesElegidos2(String representantesElegidos2) {
        this.representantesElegidos2 = representantesElegidos2;
    }

    public String getSindicato3() {
        return sindicato3;
    }

    /**
     * Establece el nombre del tercer sindicato.
     *
     * @param sindicato3 el nombre del tercer sindicato.
     */
    public void setSindicato3(String sindicato3) {
        this.sindicato3 = sindicato3;
    }

    public String getRepresentantesElegidos3() {
        return representantesElegidos3;
    }

    /**
     * Establece los representantes elegidos del tercer sindicato.
     *
     * @param representantesElegidos3 los representantes elegidos del tercer sindicato.
     */
    public void setRepresentantesElegidos3(String representantesElegidos3) {
        this.representantesElegidos3 = representantesElegidos3;
    }
}
