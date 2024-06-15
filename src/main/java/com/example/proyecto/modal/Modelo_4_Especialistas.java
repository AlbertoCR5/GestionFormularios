package com.example.proyecto.modal;

import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.CumplimentarPDFException;

public class Modelo_4_Especialistas extends Modelo_3{

    private String numeroMesa;
    private String colegio;
    private int totalElectores;
    private int varonesElectores;
    private int mujeresElectoras;

    public Modelo_4_Especialistas() {
    }

    public Modelo_4_Especialistas(String colegio, String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro,
                                  String direccion, String municipio, String provincia, String promotores, String fechaConstitucion,
                                  String municipioElecciones, String dia, String mes, String anioFormateado) throws CumplimentarPDFException {
        super(nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores, fechaConstitucion, municipioElecciones, dia, mes, anioFormateado);
        this.colegio = colegio;
    }

    public Modelo_4_Especialistas(String numeroMesa, String colegio, String nombreEmpresa, String CIF, String nombreComercial,
                                  String nombreCentro, String direccion, String municipio, String provincia, String promotores,
                                  String fechaConstitucion, String municipioElecciones, String horas, String dia, String mes,
                                  String anioFormateado, String presidente, String vocal, String secretario, String dniPresidente,
                                  String dniVocal, String dniSecretario) throws CumplimentarPDFException {
        super(nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores, fechaConstitucion, municipioElecciones, horas, dia, mes, anioFormateado, presidente, vocal, secretario, dniPresidente, dniVocal, dniSecretario);
        this.numeroMesa = numeroMesa;
        this.colegio = colegio;
    }

    public Modelo_4_Especialistas(String numeroMesa, String colegio, String nombreEmpresa, String CIF, String nombreComercial,
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

        if (totalElectores <= Constantes.MAXIMO_ELECTORES_DELEGADOS){
            throw new CumplimentarPDFException("ERROR, número de electores incorrecto o documentacion erronea\n");
        }
        this.totalElectores = totalElectores;
    }

    public int getVaronesElectores() {
        return varonesElectores;
    }

    public void setVaronesElectores(int varonesElectores) throws CumplimentarPDFException {

        if (varonesElectores > totalElectores){
            throw new CumplimentarPDFException("ERROR, el total de varones no puede ser mayor al de electores\n");
        }
        this.varonesElectores = varonesElectores;
    }

    public int getMujeresElectoras() {
        return mujeresElectoras;
    }

    public void setMujeresElectoras(int mujeresElectoras) {
        this.mujeresElectoras = (totalElectores - varonesElectores);
    }

    @Override
    public String toString() {
        return "Modelo_4_" + colegio + " Cumplimentado";
    }
}
