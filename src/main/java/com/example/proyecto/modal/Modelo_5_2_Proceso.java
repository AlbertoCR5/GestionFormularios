package com.example.proyecto.modal;

import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.MessageManager;
import com.example.proyecto.util.ValidadorFecha;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Esta clase representa el Modelo_5_2_proceso en el cual se especifican datos de la empresa
 *
 * @author Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class Modelo_5_2_Proceso {

    // Validador necesario para la clase
    ValidadorFecha extraerFecha = new ValidadorFecha();

    // Atributos de la clase
    private String preaviso;
    private String dia;
    private String mes;
    private String anio;
    private int totalElectores;
    private int electoresVarones;
    private int electoresMujeres;
    private int numeroRepresentantes;
    private int votantesVarones;
    private int votantesMujeres;
    private int totalVotantes;
    private int papeletasCumplimentadas;
    private int papeletasBlancas;
    private int nulos;
    private int representantesElegidos;
    private List<Candidatura> candidaturas;
    private List<Representantes_Elegidos> candidatosElegidos;

    //Constructor por defecto
    public Modelo_5_2_Proceso() {
    }

    // Constructor con el parámetro número de preaviso
    public Modelo_5_2_Proceso(String preaviso) {
        this.preaviso = preaviso;
    }

    // Constructor con parametros adicionales
    public Modelo_5_2_Proceso(String preaviso, String dia, String mes, String anio, int totalElectores, int electoresVarones, int electoresMujeres) throws CumplimentarPDFException {
        setPreaviso(preaviso);
        setDia(dia);
        setMes(mes);
        setAnio(anio);
        setTotalElectores(totalElectores);
        setElectoresVarones(electoresVarones);
        setElectoresMujeres(electoresMujeres);
        setNumeroRepresentantes();
    }

    // Constructor con todos los parametros disponibles
    public Modelo_5_2_Proceso(String preaviso, String dia, String mes, String anio, int totalElectores, int electoresVarones,
                              int electoresMujeres, int votantesVarones, int votantesMujeres,
                              int totalVotantes, int papeletasCumplimentadas, int papeletasBlancas, short nulos,
                              int representantesElegidos, List<Candidatura> candidaturas, List<Representantes_Elegidos> candidatosElegidos) throws CumplimentarPDFException {
        this.candidaturas = candidaturas;
        this.candidatosElegidos = candidatosElegidos;
        setPreaviso(preaviso);
        setDia(dia);
        setMes(mes);
        setAnio(anio);
        setTotalElectores(totalElectores);
        setElectoresVarones(electoresVarones);
        setElectoresMujeres(electoresMujeres);
        setNumeroRepresentantes();
        setVotantesVarones(votantesVarones);
        setVotantesMujeres(votantesMujeres);
        setTotalVotantes(totalVotantes);
        setPapeletasCumplimentadas(papeletasCumplimentadas);
        setPapeletasBlancas(papeletasBlancas);
        setNulos(nulos);
        setRepresentantesElegidos(representantesElegidos);
    }

    public List<Candidatura> getCandidaturas() {
        return candidaturas;
    }

    /**
     * Establece la lista de candidaturas.
     * @param candidaturas La lista de candidaturas.
     */
    public void setCandidaturas(List<Candidatura> candidaturas) {
        this.candidaturas = candidaturas;
    }

    public List<Representantes_Elegidos> getCandidatosElegidos() {
        return candidatosElegidos;
    }

    /**
     * Establece la lista de candidatos elegidos.
     * @param candidatosElegidos La lista de candidatos elegidos.
     */
    public void setCandidatosElegidos(List<Representantes_Elegidos> candidatosElegidos) {
        this.candidatosElegidos = candidatosElegidos;
    }

    public String getPreaviso() {
        return preaviso;
    }

    /**
     * Establece el preaviso.
     * @param preaviso El preaviso.
     * @throws CumplimentarPDFException Si el formato del preaviso no es válido.
     */
    public void setPreaviso(String preaviso) throws CumplimentarPDFException {

        Pattern patron = Pattern.compile("^([0-9]{1,4})/([0-9]{4})$");
        Matcher matcher = patron.matcher(preaviso);

        if (!matcher.matches()){
            throw new CumplimentarPDFException(MessageManager.getMessage("error.formato.preaviso.invalido"));
        }
        this.preaviso = preaviso;
    }

    public String getDia() {
        return dia;
    }

    /**
     * Establece el día de la constitución de la mesa.
     * @param dia El día de la constitución de la mesa.
     */
    public void setDia(String dia) {
        this.dia = extraerFecha.getDia();
    }

    public String getMes() {
        return mes;
    }

    /**
     * Establece el mes de la constitución de la mesa.
     * @param mes El mes de la constitución de la mesa.
     */
    public void setMes(String mes) {
        this.mes = extraerFecha.getMes();
    }

    public String getAnio() {
        return anio;
    }

    /**
     * Establece el año de la constitución de la mesa.
     * @param anio El año de la constitución de la mesa.
     */
    public void setAnio(String anio) {
        this.anio = extraerFecha.getAnio();
    }

    public int getTotalElectores() {
        return totalElectores;
    }

    /**
     * Establece el total de electores.
     * @param totalElectores El total de electores.
     * @throws CumplimentarPDFException Si el número de electores es incorrecto.
     */
    public void setTotalElectores(int totalElectores) throws CumplimentarPDFException {

        if (totalElectores < Constantes.MINIMO_ELECTORES || totalElectores > Constantes.MAXIMO_ELECTORES_DELEGADOS){
            throw new CumplimentarPDFException(MessageManager.getMessage("error.numero.electores.incorrecto"));
        }
        this.totalElectores = totalElectores;
        setNumeroRepresentantes();
    }

    public int getElectoresVarones() {
        return electoresVarones;
    }

    /**
     * Establece el total de electores varones.
     * @param electoresVarones El total de electores varones.
     * @throws CumplimentarPDFException Si el total de varones es mayor al de electores.
     */
    public void setElectoresVarones(int electoresVarones) throws CumplimentarPDFException {

        if (electoresVarones > totalElectores){
            throw new CumplimentarPDFException(MessageManager.getMessage("error.total.varones.superior.electores"));
        }
        this.electoresVarones = electoresVarones;
    }

    public int getElectoresMujeres() {
        return electoresMujeres;
    }

    /**
     * Establece el total de electores mujeres.
     * @param electoresMujeres El total de electores mujeres.
     */
    public void setElectoresMujeres(int electoresMujeres) {
        this.electoresMujeres = (totalElectores - electoresVarones);
    }

    public int getNumeroRepresentantes() {
        return numeroRepresentantes;
    }

    /**
     * Establece el número de representantes.
     */
    public void setNumeroRepresentantes() {

        if (getTotalElectores() > Constantes.MAXIMO_1_DELEGADO){
            this.numeroRepresentantes = Constantes.CANTIDAD_REPRESENTANTES_3;
        }
        else {
            this.numeroRepresentantes = Constantes.CANTIDAD_REPRESENTANTES_1;
        }
    }

    public int getTotalVotantes() {
        return totalVotantes;
    }

    /**
     * Establece el total de votantes.
     * @param totalVotantes El total de votantes.
     * @throws CumplimentarPDFException Si el total de votantes es superior al permitido.
     */
    public void setTotalVotantes(int totalVotantes) throws CumplimentarPDFException {

        if (totalVotantes > Constantes.MAXIMO_ELECTORES_DELEGADOS || totalVotantes > totalElectores){
            throw new CumplimentarPDFException(MessageManager.getMessage("error.total.votantes.superior.permitido"));
        }
        this.totalVotantes = totalVotantes;
    }

    public int getVotantesVarones() {
        return votantesVarones;
    }

    /**
     * Establece el total de votantes varones.
     * @param votantesVarones El total de votantes varones.
     * @throws CumplimentarPDFException Si el total de varones votantes es mayor al total de votantes.
     */
    public void setVotantesVarones(int votantesVarones) throws CumplimentarPDFException {

        if (votantesVarones > totalVotantes){
            throw new CumplimentarPDFException(MessageManager.getMessage("error.total.varones.votantes.superior.total.votantes"));
        }
        this.votantesVarones = votantesVarones;
    }

    public int getVotantesMujeres() {
        return votantesMujeres;
    }

    /**
     * Establece el número de votantes mujeres.
     * @param votantesMujeres El número de votantes mujeres.
     */
    public void setVotantesMujeres(int votantesMujeres) {
        this.votantesMujeres = votantesMujeres;
    }

    public int getPapeletasCumplimentadas() {
        return papeletasCumplimentadas;
    }

    /**
     * Establece el número de papeletas cumplimentadas.
     * @param papeletasCumplimentadas El número de papeletas cumplimentadas.
     * @throws CumplimentarPDFException Si el número de papeletas cumplimentadas es mayor que el total de votantes.
     */
    public void setPapeletasCumplimentadas(int papeletasCumplimentadas) throws CumplimentarPDFException {

        if ( papeletasCumplimentadas > totalVotantes){
            throw new CumplimentarPDFException(MessageManager.getMessage("error.papeletas.cumplimentadas.superior.total.votantes"));
        }
        this.papeletasCumplimentadas = papeletasCumplimentadas;
    }

    public int getPapeletasBlancas() {
        return papeletasBlancas;
    }

    /**
     * Establece el número de papeletas blancas.
     * @param papeletasBlancas El número de papeletas blancas.
     * @throws CumplimentarPDFException Si el número de papeletas blancas es mayor que el total de votantes.
     */
    public void setPapeletasBlancas(int papeletasBlancas) throws CumplimentarPDFException {

        if ( papeletasBlancas > totalVotantes){
            throw new CumplimentarPDFException(MessageManager.getMessage("error.papeletas.blancas.superior.total.votantes"));
        }
        this.papeletasBlancas = papeletasBlancas;
    }

    public int getNulos() {
        return nulos;
    }

    /**
     * Establece el número de votos nulos.
     * @param nulos El número de votos nulos.
     * @throws CumplimentarPDFException Si el número de votos nulos es mayor que el total de votantes.
     */
    public void setNulos(short nulos) throws CumplimentarPDFException {

        if ( nulos > totalVotantes){
            throw new CumplimentarPDFException(MessageManager.getMessage("error.nulos.superior.total.votantes"));
        }
        this.nulos = (nulos - (papeletasCumplimentadas + papeletasBlancas));
    }

    public int getRepresentantesElegidos() {
        return representantesElegidos;
    }

    /**
     * Establece el número de representantes elegidos.
     * @param representantesElegidos El número de representantes elegidos.
     * @throws CumplimentarPDFException Si el número de representantes elegidos es mayor que el número de representantes.
     */
    public void setRepresentantesElegidos(int representantesElegidos) throws CumplimentarPDFException {

        if (representantesElegidos > numeroRepresentantes) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.representantes.elegidos.superior.total.representantes"));
        }
        this.representantesElegidos = representantesElegidos;
    }
}
