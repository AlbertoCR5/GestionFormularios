package com.example.proyecto.modal;

import com.example.proyecto.modal.Candidato_Comite;

import java.util.List;

public class Modelo_6_2_Tecnicos extends Preaviso{

    private String sindicato;
    private List<Candidato_Comite> candidatosEspecialistas;

    public Modelo_6_2_Tecnicos() {
    }

    public Modelo_6_2_Tecnicos(String nombreEmpresa) {
        super(nombreEmpresa);
    }

    public Modelo_6_2_Tecnicos(String nombreEmpresa, String sindicato, List<Candidato_Comite> candidatosEspecialistas) {
        super(nombreEmpresa);
        this.sindicato = sindicato;
        this.candidatosEspecialistas = candidatosEspecialistas;
    }

    public String getSindicato() {
        return sindicato;
    }

    public List<Candidato_Comite> getCandidatosEspecialistas() {
        return candidatosEspecialistas;
    }

    @Override
    public String toString() {
        return "Modelo_6_2_Técnicos " + getNombreEmpresa() +" Cumplimentado";
    }
}
