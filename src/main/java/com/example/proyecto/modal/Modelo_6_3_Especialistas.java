package com.example.proyecto.modal;

import com.example.proyecto.util.CumplimentarPDFException;

import java.util.List;

public class Modelo_6_3_Especialistas extends Preaviso{

    private List<Candidatura_Comite> candidaturasEspecialistas;
    private String [] incidencias;

    public Modelo_6_3_Especialistas() {
    }

    public Modelo_6_3_Especialistas(String nombreEmpresa, String CIF) throws CumplimentarPDFException {
        super(nombreEmpresa, CIF);
    }

    public Modelo_6_3_Especialistas(String nombreEmpresa, String CIF, List<Candidatura_Comite> candidaturasEspecialistas, String[] incidencias) throws CumplimentarPDFException {
        super(nombreEmpresa, CIF);
        this.candidaturasEspecialistas = candidaturasEspecialistas;
        this.incidencias = incidencias;
    }

    public List<Candidatura_Comite> getCandidaturasEspecialistas() {
        return candidaturasEspecialistas;
    }

    public void setCandidaturasEspecialistas(List<Candidatura_Comite> candidaturasEspecialistas) {
        this.candidaturasEspecialistas = candidaturasEspecialistas;
    }

    public String[] getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(String[] incidencias) {
        this.incidencias = incidencias;
    }
}
