package com.example.proyecto.modal;

import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.MessageManager;
import com.example.proyecto.util.ValidadorCampos;

import java.util.regex.Pattern;

/**
 * La clase `Modelo_5_2_Conclusion`extiende la clase `Preaviso`.
 * Esto significa que `Modelo_5_2_Conclusion` hereda algunas de las propiedades y métodos de `Preaviso`.
 * Esta clase representa el Modelo_5_2_Conclusion en el cual se especifican datos de la empresa
 *
 * @author Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class Modelo_5_2_Conclusion extends Preaviso{

    // Validador necesario para la clase
    ValidadorCampos verificarDNI = new ValidadorCampos();

    // Atributos de la clase
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
    private int trabajadoresEventuales;
    private int trabajadoresJornadas;
    private int trabajadoresEventualesComputo;
    private String totalTrabajadores;
    private String presidente;
    private String dniPresidente;
    private String secretario;
    private String dniSecretario;
    private String representantes;
    private String dniRepresentante;

    //Constructor por defecto
    public Modelo_5_2_Conclusion() {
    }

    // Constructor con parametros
    public Modelo_5_2_Conclusion(ValidadorCampos verificarDNI) {
        this.verificarDNI = verificarDNI;
    }

    // Constructor con parametros adicionales
    public Modelo_5_2_Conclusion(String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro, String direccion,
                                 String municipio, String codigoPostal, String comarca, String provincia, String numeroISS) throws CumplimentarPDFException {
        super(nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, codigoPostal, comarca, provincia, numeroISS);
    }

    // Constructor con parametros adicionales
    public Modelo_5_2_Conclusion(String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro, String direccion,
                                 String municipio, String codigoPostal, String comarca, String provincia, String numeroISS,
                                 boolean casillaVerificacion2, String nombreConvenio, String numeroConvenio, String trabajadoresFijos,
                                 int trabajadoresEventuales, int trabajadoresJornadas, int trabajadoresEventualesComputo, String totalTrabajadores) throws CumplimentarPDFException {
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
     * @param nombreEmpresa                       Nombre de la empresa.
     * @param CIF                                 CIF de la empresa.
     * @param nombreComercial                     Nombre comercial de la empresa.
     * @param nombreCentro                        Nombre del centro de trabajo.
     * @param direccion                           Dirección de la empresa.
     * @param municipio                           Municipio de la empresa.
     * @param codigoPostal                        Código postal de la empresa.
     * @param comarca                             Comarca de la empresa.
     * @param provincia                           Provincia de la empresa.
     * @param numeroISS                           Número ISS de la empresa.
     * @param actvEcono                           Actividad económica.
     * @param actvEcono1                          Actividad económica 1.
     * @param direccionCentro                     Dirección del centro.
     * @param direccionCentro1                    Dirección del centro 1.
     * @param telefono                            Teléfono.
     * @param actvEcono2                          Actividad económica 2.
     * @param actvEcono3                          Actividad económica 3.
     * @param casillaVerificacion2                Casilla de verificación.
     * @param nombreConvenio                      Nombre del convenio.
     * @param numeroConvenio                      Número del convenio.
     * @param trabajadoresFijos                   Número de trabajadores fijos.
     * @param trabajadoresEventuales              Número de trabajadores eventuales.
     * @param trabajadoresJornadas                Número de trabajadores en jornadas.
     * @param trabajadoresEventualesComputo       Número de trabajadores eventuales en cómputo.
     * @param totalTrabajadores                   Total de trabajadores.
     * @param presidente                          Nombre del presidente.
     * @param dniPresidente                       DNI del presidente.
     * @param secretario                          Nombre del secretario.
     * @param dniSecretario                       DNI del secretario.
     * @param representantes                      Representantes.
     * @param dniRepresentante                    DNI del representante.
     * @throws CumplimentarPDFException Si hay un error al completar el PDF.
     */
    public Modelo_5_2_Conclusion(String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro, String direccion,
                                 String municipio, String codigoPostal, String comarca, String provincia, String numeroISS,
                                 String actvEcono, String actvEcono1, String direccionCentro, String direccionCentro1,
                                 String telefono, String actvEcono2, String actvEcono3, boolean casillaVerificacion2,
                                 String nombreConvenio, String numeroConvenio, String trabajadoresFijos, int trabajadoresEventuales,
                                 int trabajadoresJornadas, int trabajadoresEventualesComputo, String totalTrabajadores,
                                 String presidente, String dniPresidente, String secretario, String dniSecretario,
                                 String representantes, String dniRepresentante) throws CumplimentarPDFException {
        super(nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, codigoPostal, comarca, provincia, numeroISS);
        this.actvEcono = actvEcono;
        this.actvEcono1 = actvEcono1;
        setDireccionCentro(direccionCentro);
        setDireccionCentro1(direccionCentro1);
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
        setPresidente(presidente);
        setDniPresidente(dniPresidente);
        setSecretario(secretario);
        setDniSecretario(dniSecretario);
        this.representantes = representantes;
        setDniRepresentante(dniRepresentante);
    }

    public String getActvEcono() {
        return actvEcono;
    }

    /**
     * Establece la actividad económica.
     *
     * @param actvEcono La actividad económica a establecer.
     */
    public void setActvEcono(String actvEcono) {
        this.actvEcono = actvEcono;
    }

    public String getActvEcono1() {
        return actvEcono1;
    }

    /**
     * Establece la actividad económica en la segunda línea del documento.
     *
     * @param actvEcono1 La actividad económica a establecer.
     * @deprecated
     */
    public void setActvEcono1(String actvEcono1) {
        this.actvEcono1 = actvEcono1;
    }

    public String getDireccionCentro() {
        return direccionCentro;
    }

    /**
     * Establece la dirección del centro y separa la cadena si excede la longitud permitida.
     *
     * @param direccionCentro La dirección del centro a establecer.
     */
    public void setDireccionCentro(String direccionCentro) {

        if (direccionCentro.length() >= 35) {
            int indiceUltimaComa = direccionCentro.substring(0, 35).lastIndexOf(',');

            if (indiceUltimaComa >= 0) {
                this.direccionCentro = direccionCentro.substring(0, indiceUltimaComa);
                this.direccionCentro1 = direccionCentro.substring(indiceUltimaComa + 2);
                setDireccionCentro1(direccionCentro1);
            } else {
                this.direccionCentro = direccionCentro.substring(0, 35);
                this.direccionCentro1 = "";
                setDireccionCentro1(direccionCentro1);
            }
        } else {
            this.direccionCentro = direccionCentro;
            this.direccionCentro1 = "";
            setDireccionCentro1(direccionCentro1);
        }
    }

    public String getDireccionCentro1() {
        return direccionCentro1;
    }

    /**
     * Establece la segunda parte de la dirección del centro.
     *
     * @param direccionCentro1 La segunda parte de la dirección del centro a establecer.
     */
    public void setDireccionCentro1(String direccionCentro1) {
        this.direccionCentro1 = direccionCentro1;
    }

    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono.
     *
     * @param telefono El número de teléfono a establecer.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getActvEcono2() {
        return actvEcono2;
    }

    /**
     * Establece la actividad económica del centro de trabajo.
     *
     * @param actvEcono2 La actividad económica del centro de trabajo a establecer.
     * @deprecated
     */
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
        this.casillaVerificacion2 = true;
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
     * Establece el número de convenio y verifica si cumple con el formato requerido.
     *
     * @param numeroConvenio El número de convenio a establecer.
     * @throws CumplimentarPDFException Si el número de convenio no cumple con el formato requerido.
     */
    public void setNumeroConvenio(String numeroConvenio) throws CumplimentarPDFException {
        String formatoConvenio = Constantes.FORMATO_CONVENIO;

        // Compila la expresión regular
        Pattern patron = Pattern.compile(formatoConvenio);

        if (!numeroConvenio.isEmpty() && !patron.matcher(numeroConvenio).matches()){
            throw new CumplimentarPDFException(MessageManager.getMessage("error.convenio.invalido"));
        }
        this.numeroConvenio = numeroConvenio;
    }

    public String getTrabajadoresFijos() {
        return trabajadoresFijos;
    }

    /**
     * Establece el número de trabajadores fijos.
     *
     * @param trabajadoresFijos El número de trabajadores fijos a establecer.
     * @throws CumplimentarPDFException Si el número de trabajadores fijos es incorrecto o la documentación es errónea.
     */
    public void setTrabajadoresFijos(String trabajadoresFijos) throws CumplimentarPDFException {

        if (!trabajadoresFijos.matches("[0-9]+") || Integer.parseInt(trabajadoresFijos) > Constantes.MAXIMO_ELECTORES_DELEGADOS){
            throw new CumplimentarPDFException(MessageManager.getMessage("error.trabajadores.fijos.incorrecto"));
        }
        this.trabajadoresFijos = trabajadoresFijos;
        setTotalTrabajadores(trabajadoresFijos);
    }

    public int getTrabajadoresEventuales() {
        return trabajadoresEventuales;
    }

    /**
     * Establece el número de trabajadores eventuales.
     *
     * @param trabajadoresEventuales El número de trabajadores eventuales a establecer.
     */
    public void setTrabajadoresEventuales(int trabajadoresEventuales) {
        this.trabajadoresEventuales = trabajadoresEventuales;
    }

    public int getTrabajadoresJornadas() {
        return trabajadoresJornadas;
    }

    /**
     * Establece el número de trabajadores en jornadas.
     *
     * @param trabajadoresJornadas El número de trabajadores en jornadas a establecer.
     */
    public void setTrabajadoresJornadas(int trabajadoresJornadas) {
        this.trabajadoresJornadas = trabajadoresJornadas;
    }

    public int getTrabajadoresEventualesComputo() {
        return trabajadoresEventualesComputo;
    }

    /**
     * Establece el número de trabajadores eventuales de computo.
     *
     * @param trabajadoresEventualesComputo El número de trabajadores eventuales de computo a establecer.
     */
    public void setTrabajadoresEventualesComputo(int trabajadoresEventualesComputo) {

            this.trabajadoresEventualesComputo = trabajadoresEventualesComputo;
    }

    @Override
    public String getTotalTrabajadores() {
        return totalTrabajadores;
    }

    /**
     * Establece el total de trabajadores, limitado a un máximo de 50.
     *
     * @param totalTrabajadores El total de trabajadores a establecer.
     */
    @Override
    public void setTotalTrabajadores(String totalTrabajadores) {
        this.totalTrabajadores = getTrabajadoresFijos();
    }

    public String getPresidente() {
        return presidente;
    }

    /**
     * Establece el nombre del presidente.
     *
     * @param presidente El nombre del presidente a establecer.
     */
    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    public String getDniPresidente() {
        return dniPresidente;
    }

    /**
     * Establece el DNI del presidente y verifica si es válido.
     *
     * @param dniPresidente El DNI del presidente a establecer.
     * @throws CumplimentarPDFException Si el DNI introducido es incorrecto.
     */
    public void setDniPresidente(String dniPresidente) throws CumplimentarPDFException {

        if (!verificarDNI.verificarDNI(dniPresidente)){
            throw new CumplimentarPDFException(MessageManager.getMessage("error.dni.invalido"));
        }
        this.dniPresidente = dniPresidente;
    }

    public String getSecretario() {
        return secretario;
    }

    /**
     * Establece el nombre del secretario.
     *
     * @param secretario El nombre del secretario a establecer.
     */
    public void setSecretario(String secretario) {
        this.secretario = secretario;
    }

    public String getDniSecretario() {
        return dniSecretario;
    }

    /**
     * Establece el DNI del secretario y verifica si es válido.
     *
     * @param dniSecretario El DNI del secretario a establecer.
     * @throws CumplimentarPDFException Si el DNI introducido es incorrecto.
     */
    public void setDniSecretario(String dniSecretario) throws CumplimentarPDFException {

        if (!verificarDNI.verificarDNI(dniSecretario)){
            throw new CumplimentarPDFException(MessageManager.getMessage("error.dni.invalido"));
        }
        this.dniSecretario = dniSecretario;
    }

    public String getRepresentantes() {
        return representantes;
    }

    /**
     * Establece los representantes.
     *
     * @param representantes Los representantes a establecer.
     */
    public void setRepresentantes(String representantes) {
        this.representantes = representantes;
    }

    public String getDniRepresentante() {
        return dniRepresentante;
    }

    /**
     * Establece el DNI del representante y verifica si es válido.
     *
     * @param dniRepresentante El DNI del representante a establecer.
     * @throws CumplimentarPDFException Si el DNI introducido es incorrecto.
     */
    public void setDniRepresentante(String dniRepresentante) throws CumplimentarPDFException {

        if (!verificarDNI.verificarDNI(dniRepresentante)){
            throw new CumplimentarPDFException(MessageManager.getMessage("error.dni.invalido"));
        }
        this.dniRepresentante = dniRepresentante;
    }
}
