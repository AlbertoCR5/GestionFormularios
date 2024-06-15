package com.example.proyecto.modal;

import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.ValidadorCampos;
import org.jetbrains.annotations.NotNull;

/**
 * La clase `Modelo_3` extiende la clase `Preaviso`.
 * Representa el modelo 3 con el cual se constituye la mesa electoral.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 */
public class Modelo_3 extends Preaviso {

    // Validadores necesarios para la clase
    private final ValidadorCampos validarHora = new ValidadorCampos();
    private final ValidadorCampos verificarDNI = new ValidadorCampos();

    // Atributos de la clase
    private String municipioElecciones;
    private String horas;
    private String dia;
    private String mes;
    private String anioFormateado;
    private String presidente;
    private String vocal;
    private String secretario;
    private String dniPresidente;
    private String dniVocal;
    private String dniSecretario;

    // Constructor vacío
    public Modelo_3() {
    }

    // Constructor con parámetros
    public Modelo_3(String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro, String direccion,
                    String municipio, String provincia, String promotores, String fechaConstitucion,
                    String municipioElecciones, String dia, String mes, String anioFormateado) throws CumplimentarPDFException {
        super(nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores, fechaConstitucion);
        setMunicipioElecciones(municipioElecciones);
        setDia(dia);
        setMes(mes);
        setAnioFormateado(anioFormateado);
    }

    // Constructor con parámetros adicionales
    public Modelo_3(String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro, String direccion,
                    String municipio, String provincia, String promotores, String fechaConstitucion, String municipioElecciones,
                    String horas, String dia, String mes, String anioFormateado, String presidente, String vocal,
                    String secretario, String dniPresidente, String dniVocal, String dniSecretario) throws CumplimentarPDFException {
        super(nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores, fechaConstitucion);
        setMunicipioElecciones(municipioElecciones);
        setHoras(horas);
        setDia(dia);
        setMes(mes);
        setAnioFormateado(anioFormateado);
        setPresidente(presidente);
        setVocal(vocal);
        setSecretario(secretario);
        setDniPresidente(dniPresidente);
        setDniVocal(dniVocal);
        setDniSecretario(dniSecretario);
    }

    // Métodos Getters y Setters
    public String getMunicipioElecciones() {
        return municipioElecciones;
    }

    /**
     * Establece el municipio de las elecciones.
     * Si el valor proporcionado está vacío, se utiliza el municipio de la empresa.
     * El método está pensado porque puede darse el caso de que la localización de las elecciones
     * sea en un municipio diferente al de la constitución de la mesa electoral.
     *
     * @param municipioElecciones El municipio de las elecciones.
     */
    public void setMunicipioElecciones(@NotNull String municipioElecciones) {
        this.municipioElecciones = municipioElecciones.isEmpty() ? super.getMunicipio() : municipioElecciones;
    }

    public String getHoras() {
        return horas;
    }

    /**
     * Establece la hora de la constitución de la mesa electoral.
     *
     * @param horas La hora de la constitución de la mesa electoral.
     * @throws CumplimentarPDFException Si la hora no es válida.
     */
    public void setHoras(String horas) throws CumplimentarPDFException {
        if (!validarHora.validarHora(horas)) {
            throw new CumplimentarPDFException("ERROR, Hora introducida no válida");
        }
        this.horas = horas;
    }

    public String getDia() {
        return dia;
    }

    /**
     * Establece el día de la constitución de la mesa electoral.
     *
     * @param dia El día de la constitución de la mesa electoral.
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
     * @param mes El mes de la constitución de la mesa.
     */
    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnioFormateado() {
        return anioFormateado;
    }

    /**
     * Establece el año de la constitución de la mesa.
     *
     * @param anioFormateado El año de la constitución de la mesa.
     */
    public void setAnioFormateado(String anioFormateado) {
        this.anioFormateado = anioFormateado;
    }

    public String getPresidente() {
        return presidente;
    }

    /**
     * Establece el nombre del presidente de la constitución de la mesa.
     *
     * @param presidente El nombre del presidente de la mesa.
     */
    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    public String getVocal() {
        return vocal;
    }

    /**
     * Establece el nombre del vocal de la constitución de la mesa.
     *
     * @param vocal El nombre del vocal de la mesa.
     */
    public void setVocal(String vocal) {
        this.vocal = vocal;
    }

    public String getSecretario() {
        return secretario;
    }

    /**
     * Establece el nombre del secretario de la constitución de la mesa.
     *
     * @param secretario El nombre del secretario.
     */
    public void setSecretario(String secretario) {
        this.secretario = secretario;
    }

    public String getDniPresidente() {
        return dniPresidente;
    }

    /**
     * Establece el DNI del presidente de la constitución de la mesa.
     *
     * @param dniPresidente El DNI del presidente.
     * @throws CumplimentarPDFException Si el DNI no es válido.
     */
    public void setDniPresidente(String dniPresidente) throws CumplimentarPDFException {
        if (!verificarDNI.verificarDNI(dniPresidente)) {
            throw new CumplimentarPDFException("ERROR, DNI introducido incorrecto");
        }
        this.dniPresidente = dniPresidente;
    }

    public String getDniVocal() {
        return dniVocal;
    }

    /**
     * Establece el DNI del vocal de la constitución de la mesa.
     *
     * @param dniVocal El DNI del vocal.
     * @throws CumplimentarPDFException Si el DNI no es válido.
     */
    public void setDniVocal(String dniVocal) throws CumplimentarPDFException {
        if (!verificarDNI.verificarDNI(dniVocal)) {
            throw new CumplimentarPDFException("ERROR, DNI introducido incorrecto");
        }
        this.dniVocal = dniVocal;
    }

    public String getDniSecretario() {
        return dniSecretario;
    }

    /**
     * Establece el DNI del secretario de la constitución de la mesa.
     *
     * @param dniSecretario El DNI del secretario.
     * @throws CumplimentarPDFException Si el DNI no es válido.
     */
    public void setDniSecretario(String dniSecretario) throws CumplimentarPDFException {
        if (!verificarDNI.verificarDNI(dniSecretario)) {
            throw new CumplimentarPDFException("ERROR, DNI introducido incorrecto");
        }
        this.dniSecretario = dniSecretario;
    }
}
