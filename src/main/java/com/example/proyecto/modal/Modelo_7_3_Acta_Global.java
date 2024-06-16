package com.example.proyecto.modal;

import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.ValidadorCampos;
import com.example.proyecto.util.MessageManager;

import java.util.regex.Pattern;

/**
 * Clase que representa el modelo 7.3 de acta global.
 * Extiende de Preaviso y añade propiedades específicas.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class Modelo_7_3_Acta_Global extends Preaviso {

    private String actvEcono;
    private String actvEcono1;
    private String direccionCentro;
    private String direccionCentro1;
    private String telefono;
    private String actvEcono2;
    private String actvEcono3;
    private boolean casillaVerificacion2;
    private String nombreConvenio;
    private String numeroConvenio;
    private String trabajadoresFijos;
    private String trabajadoresEventuales;
    private String trabajadoresJornadas;
    private String trabajadoresEventualesComputo;
    private String totalTrabajadores;
    private String presidente;
    private String dniPresidente;
    private String secretario;
    private String dniSecretario;
    private String representantes;
    private String dniRepresentante;

    /**
     * Constructor vacío.
     */
    public Modelo_7_3_Acta_Global() {
    }

    /**
     * Constructor con parámetros iniciales.
     *
     * @param nombreEmpresa El nombre de la empresa.
     * @param CIF El CIF de la empresa.
     * @param nombreComercial El nombre comercial de la empresa.
     * @param nombreCentro El nombre del centro.
     * @param direccion La dirección del centro.
     * @param municipio El municipio del centro.
     * @param codigoPostal El código postal del centro.
     * @param comarca La comarca del centro.
     * @param provincia La provincia del centro.
     * @param numeroISS El número ISS de la empresa.
     * @throws CumplimentarPDFException Si algún dato es incorrecto.
     */
    public Modelo_7_3_Acta_Global(String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro, String direccion,
                                  String municipio, String codigoPostal, String comarca, String provincia, String numeroISS) throws CumplimentarPDFException {
        super(nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, codigoPostal, comarca, provincia, numeroISS);
    }

    /**
     * Constructor con parámetros adicionales.
     *
     * @param nombreEmpresa El nombre de la empresa.
     * @param CIF El CIF de la empresa.
     * @param nombreComercial El nombre comercial de la empresa.
     * @param nombreCentro El nombre del centro.
     * @param direccion La dirección del centro.
     * @param municipio El municipio del centro.
     * @param codigoPostal El código postal del centro.
     * @param comarca La comarca del centro.
     * @param provincia La provincia del centro.
     * @param numeroISS El número ISS de la empresa.
     * @param casillaVerificacion2 Estado de la casilla de verificación.
     * @param nombreConvenio El nombre del convenio.
     * @param numeroConvenio El número del convenio.
     * @param trabajadoresFijos El número de trabajadores fijos.
     * @param trabajadoresEventuales El número de trabajadores eventuales.
     * @param trabajadoresJornadas El número de trabajadores por jornadas.
     * @param trabajadoresEventualesComputo El número de trabajadores eventuales por computo.
     * @param totalTrabajadores El número total de trabajadores.
     * @throws CumplimentarPDFException Si algún dato es incorrecto.
     */
    public Modelo_7_3_Acta_Global(String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro, String direccion,
                                  String municipio, String codigoPostal, String comarca, String provincia, String numeroISS,
                                  boolean casillaVerificacion2, String nombreConvenio, String numeroConvenio, String trabajadoresFijos,
                                  String trabajadoresEventuales, String trabajadoresJornadas, String trabajadoresEventualesComputo, String totalTrabajadores) throws CumplimentarPDFException {
        super(nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, codigoPostal, comarca, provincia, numeroISS);
        setCasillaVerificacion2(casillaVerificacion2);
        this.nombreConvenio = nombreConvenio;
        setNumeroConvenio(numeroConvenio);
        setTrabajadoresFijos(trabajadoresFijos);
        this.trabajadoresEventuales = trabajadoresEventuales;
        this.trabajadoresJornadas = trabajadoresJornadas;
        this.trabajadoresEventualesComputo = trabajadoresEventualesComputo;
        setTotalTrabajadores(totalTrabajadores);
    }

    /**
     * Constructor completo.
     *
     * @param nombreEmpresa El nombre de la empresa.
     * @param CIF El CIF de la empresa.
     * @param nombreComercial El nombre comercial de la empresa.
     * @param nombreCentro El nombre del centro.
     * @param direccion La dirección del centro.
     * @param municipio El municipio del centro.
     * @param codigoPostal El código postal del centro.
     * @param comarca La comarca del centro.
     * @param provincia La provincia del centro.
     * @param numeroISS El número ISS de la empresa.
     * @param actvEcono Actividad económica.
     * @param actvEcono1 Actividad económica adicional.
     * @param direccionCentro La dirección del centro.
     * @param direccionCentro1 Dirección adicional del centro.
     * @param telefono El teléfono del centro.
     * @param actvEcono2 Actividad económica secundaria.
     * @param actvEcono3 Actividad económica terciaria.
     * @param casillaVerificacion2 Estado de la casilla de verificación.
     * @param nombreConvenio El nombre del convenio.
     * @param numeroConvenio El número del convenio.
     * @param trabajadoresFijos El número de trabajadores fijos.
     * @param trabajadoresEventuales El número de trabajadores eventuales.
     * @param trabajadoresJornadas El número de trabajadores por jornadas.
     * @param trabajadoresEventualesComputo El número de trabajadores eventuales por computo.
     * @param totalTrabajadores El número total de trabajadores.
     * @param presidente El nombre del presidente.
     * @param dniPresidente El DNI del presidente.
     * @param secretario El nombre del secretario.
     * @param dniSecretario El DNI del secretario.
     * @param representantes El número de representantes.
     * @param dniRepresentante El DNI del representante.
     * @throws CumplimentarPDFException Si algún dato es incorrecto.
     */
    public Modelo_7_3_Acta_Global(String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro, String direccion,
                                  String municipio, String codigoPostal, String comarca, String provincia, String numeroISS,
                                  String actvEcono, String actvEcono1, String direccionCentro, String direccionCentro1,
                                  String telefono, String actvEcono2, String actvEcono3, boolean casillaVerificacion2,
                                  String nombreConvenio, String numeroConvenio, String trabajadoresFijos, String trabajadoresEventuales,
                                  String trabajadoresJornadas, String trabajadoresEventualesComputo, String totalTrabajadores,
                                  String presidente, String dniPresidente, String secretario, String dniSecretario,
                                  String representantes, String dniRepresentante) throws CumplimentarPDFException {
        super(nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, codigoPostal, comarca, provincia, numeroISS);
        this.actvEcono = actvEcono;
        this.actvEcono1 = actvEcono1;
        this.direccionCentro = direccionCentro;
        this.direccionCentro1 = direccionCentro1;
        this.telefono = telefono;
        this.actvEcono2 = actvEcono2;
        this.actvEcono3 = actvEcono3;
        setCasillaVerificacion2(casillaVerificacion2);
        this.nombreConvenio = nombreConvenio;
        setNumeroConvenio(numeroConvenio);
        setTrabajadoresFijos(trabajadoresFijos);
        this.trabajadoresEventuales = trabajadoresEventuales;
        this.trabajadoresJornadas = trabajadoresJornadas;
        this.trabajadoresEventualesComputo = trabajadoresEventualesComputo;
        setTotalTrabajadores(totalTrabajadores);
        this.presidente = presidente;
        setDniPresidente(dniPresidente);
        this.secretario = secretario;
        setDniSecretario(dniSecretario);
        this.representantes = representantes;
        setDniRepresentante(dniRepresentante);
    }

    // Getters y setters con validación

    public String getActvEcono() {
        return actvEcono;
    }

    public void setActvEcono(String actvEcono) {
        this.actvEcono = actvEcono;
    }

    public String getActvEcono1() {
        return actvEcono1;
    }

    public void setActvEcono1(String actvEcono1) {
        this.actvEcono1 = actvEcono1;
    }

    public String getDireccionCentro() {
        return direccionCentro;
    }

    /**
     * Establece la dirección del centro, dividiéndola en dos partes si es necesario.
     *
     * @param direccionCentro La dirección del centro.
     */
    public void setDireccionCentro(String direccionCentro) {
        if (direccionCentro.length() >= 35) {
            int indiceUltimaComa = direccionCentro.substring(0, 35).lastIndexOf(',');

            if (indiceUltimaComa >= 0) {
                this.direccionCentro = direccionCentro.substring(0, indiceUltimaComa);
                this.direccionCentro1 = direccionCentro.substring(indiceUltimaComa + 2);
            } else {
                this.direccionCentro = direccionCentro.substring(0, 35);
                this.direccionCentro1 = "";
            }
        } else {
            this.direccionCentro = direccionCentro;
            this.direccionCentro1 = "";
        }
    }

    public String getDireccionCentro1() {
        return direccionCentro1;
    }

    public void setDireccionCentro1(String direccionCentro1) {
        this.direccionCentro1 = direccionCentro1;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getActvEcono2() {
        return actvEcono2;
    }

    public void setActvEcono2(String actvEcono2) {
        this.actvEcono2 = actvEcono2;
    }

    public String getActvEcono3() {
        return actvEcono3;
    }

    public void setActvEcono3(String actvEcono3) {
        this.actvEcono3 = actvEcono3;
    }

    public boolean isCasillaVerificacion2() {
        return casillaVerificacion2;
    }

    public void setCasillaVerificacion2(boolean casillaVerificacion2) {
        this.casillaVerificacion2 = casillaVerificacion2;
    }

    public String getNombreConvenio() {
        return nombreConvenio;
    }

    public void setNombreConvenio(String nombreConvenio) {
        this.nombreConvenio = nombreConvenio;
    }

    public String getNumeroConvenio() {
        return numeroConvenio;
    }

    /**
     * Establece el número del convenio.
     *
     * @param numeroConvenio El número del convenio.
     * @throws CumplimentarPDFException Si el número del convenio no es válido.
     */
    public void setNumeroConvenio(String numeroConvenio) throws CumplimentarPDFException {
        String expresionRegular = "^\\d{14}$";
        Pattern patron = Pattern.compile(expresionRegular);

        if (!patron.matcher(numeroConvenio).matches()) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.numero.convenio.incorrecto"));
        }
        this.numeroConvenio = numeroConvenio;
    }

    public String getTrabajadoresFijos() {
        return trabajadoresFijos;
    }

    /**
     * Establece el número de trabajadores fijos.
     *
     * @param trabajadoresFijos El número de trabajadores fijos.
     * @throws CumplimentarPDFException Si el número de trabajadores fijos es incorrecto.
     */
    public void setTrabajadoresFijos(String trabajadoresFijos) throws CumplimentarPDFException {
        if (Integer.parseInt(trabajadoresFijos) < Constantes.MAXIMO_ELECTORES_DELEGADOS) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.trabajadores.fijos.incorrecto"));
        }
        this.trabajadoresFijos = trabajadoresFijos;
    }

    public String getTrabajadoresEventuales() {
        return trabajadoresEventuales;
    }

    public void setTrabajadoresEventuales(String trabajadoresEventuales) {
        this.trabajadoresEventuales = trabajadoresEventuales;
    }

    public String getTrabajadoresJornadas() {
        return trabajadoresJornadas;
    }

    public void setTrabajadoresJornadas(String trabajadoresJornadas) {
        this.trabajadoresJornadas = trabajadoresJornadas;
    }

    public String getTrabajadoresEventualesComputo() {
        return trabajadoresEventualesComputo;
    }

    public void setTrabajadoresEventualesComputo(String trabajadoresEventualesComputo) {
        this.trabajadoresEventualesComputo = trabajadoresEventualesComputo;
    }

    public String getTotalTrabajadores() {
        return totalTrabajadores;
    }

    /**
     * Establece el número total de trabajadores.
     *
     * @param totalTrabajadores El número total de trabajadores.
     * @throws CumplimentarPDFException Si el número total de trabajadores es incorrecto.
     */
    public void setTotalTrabajadores(String totalTrabajadores) throws CumplimentarPDFException {
        if (Integer.parseInt(trabajadoresFijos) + Integer.parseInt(trabajadoresEventualesComputo) <= 50) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.total.trabajadores.incorrecto"));
        }
        this.totalTrabajadores = totalTrabajadores;
    }

    public String getPresidente() {
        return presidente;
    }

    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    public String getDniPresidente() {
        return dniPresidente;
    }

    /**
     * Establece el DNI del presidente.
     *
     * @param dniPresidente El DNI del presidente.
     * @throws CumplimentarPDFException Si el DNI del presidente es incorrecto.
     */
    public void setDniPresidente(String dniPresidente) throws CumplimentarPDFException {
        if (!ValidadorCampos.verificarDNI(dniPresidente)) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.dni.presidente.incorrecto"));
        }
        this.dniPresidente = dniPresidente;
    }

    public String getSecretario() {
        return secretario;
    }

    public void setSecretario(String secretario) {
        this.secretario = secretario;
    }

    public String getDniSecretario() {
        return dniSecretario;
    }

    /**
     * Establece el DNI del secretario.
     *
     * @param dniSecretario El DNI del secretario.
     * @throws CumplimentarPDFException Si el DNI del secretario es incorrecto.
     */
    public void setDniSecretario(String dniSecretario) throws CumplimentarPDFException {
        if (!ValidadorCampos.verificarDNI(dniSecretario)) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.dni.secretario.incorrecto"));
        }
        this.dniSecretario = dniSecretario;
    }

    public String getRepresentantes() {
        return representantes;
    }

    public void setRepresentantes(String representantes) {
        this.representantes = representantes;
    }

    public String getDniRepresentante() {
        return dniRepresentante;
    }

    /**
     * Establece el DNI del representante.
     *
     * @param dniRepresentante El DNI del representante.
     * @throws CumplimentarPDFException Si el DNI del representante es incorrecto.
     */
    public void setDniRepresentante(String dniRepresentante) throws CumplimentarPDFException {
        if (!ValidadorCampos.verificarDNI(dniRepresentante)) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.dni.representante.incorrecto"));
        }
        this.dniRepresentante = dniRepresentante;
    }
}
