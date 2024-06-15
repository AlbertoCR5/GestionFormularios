package com.example.proyecto.modal;

public class Candidatura_Comite {

    private String colegio;
    private String sindicato;
    private int presentados;
    private int elegidos;
    private int votos;


    public Candidatura_Comite(String colegio, String sindicato, int votos) {
        this.colegio = colegio;
        this.sindicato = sindicato;
        this.votos = votos;
    }

    public Candidatura_Comite(String colegio, String sindicato, int presentados, int elegidos, int votos) {
        this.colegio = colegio;
        this.sindicato = sindicato;
        this.presentados = presentados;
        this.elegidos = elegidos;
        this.votos = votos;
    }

    public int getPresentados() {
        return presentados;
    }

    public void setPresentados(int presentados) {
        this.presentados = presentados;
    }

    public int getElegidos() {
        return elegidos;
    }

    public void setElegidos(int elegidos) {
        this.elegidos = elegidos;
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

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }

    @Override
    public String toString() {
        return "com.example.proyecto.modal.Candidatura_Comite{" +
                "colegio='" + colegio + '\'' +
                ", sindicato='" + sindicato + '\'' +
                ", presentados=" + presentados +
                ", elegidos=" + elegidos +
                ", votos=" + votos +
                '}';
    }
}
