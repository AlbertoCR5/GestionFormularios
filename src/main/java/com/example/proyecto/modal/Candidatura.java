package com.example.proyecto.modal;

public class Candidatura {

    private String sindicato;
    private int votos;
    private int delegadosPresentados;
    private int delegadosElegidos;

    public Candidatura(String sindicato, int votos, int delegadosPresentados, int delegadosElegidos) {
        this.sindicato = sindicato;
        this.votos = votos;
        this.delegadosPresentados = delegadosPresentados;
        this.delegadosElegidos = delegadosElegidos;
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

    public int getDelegadosPresentados() {
        return delegadosPresentados;
    }

    public void setDelegadosPresentados(int delegadosPresentados) {
        this.delegadosPresentados = delegadosPresentados;
    }

    public int getDelegadosElegidos() {
        return delegadosElegidos;
    }

    public void setDelegadosElegidos(int delegadosElegidos) {
        this.delegadosElegidos = delegadosElegidos;
    }

}
