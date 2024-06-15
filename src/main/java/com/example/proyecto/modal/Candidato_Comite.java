package com.example.proyecto.modal;

import java.util.Objects;

public class Candidato_Comite {

    private final String nombreCompleto;
    private String colegio;

    public Candidato_Comite(String colegio, String nombreCompleto) {
        this.colegio = colegio;
        this.nombreCompleto = nombreCompleto;
    }

    public String getColegio() {
        return colegio;
    }

    public void setColegio(String colegio) {
        this.colegio = colegio;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidato_Comite that = (Candidato_Comite) o;
        return Objects.equals(nombreCompleto, that.nombreCompleto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreCompleto);
    }

    @Override
    public String toString() {
        return "com.example.proyecto.modal.Candidato_Comite{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                '}';
    }
}
