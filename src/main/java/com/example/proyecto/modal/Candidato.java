package com.example.proyecto.modal;

import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.MessageManager;
import com.example.proyecto.util.ValidadorCampos;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * La clase `Candidato` representa un candidato con sus datos personales y profesionales.
 * Implementa la interfaz `Comparable` para permitir la comparación entre candidatos.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 */
public class Candidato implements Comparable<Candidato> {

    private final ValidadorCampos verificarDNI = new ValidadorCampos();
    private String nombreApellidos;
    private String dni;
    private int numeroVotos;
    private String sindicato;

    /**
     * Constructor para la clase `Candidato`.
     *
     * @param nombreApellidos El nombre y apellidos del candidato.
     * @param dni             El DNI del candidato.
     * @param sindicato       El sindicato al que pertenece el candidato.
     * @throws CumplimentarPDFException Si el DNI es incorrecto.
     */
    public Candidato(String nombreApellidos, String dni, String sindicato) throws CumplimentarPDFException {
        this.nombreApellidos = nombreApellidos;
        setDni(dni);
        this.sindicato = sindicato;
    }

    /**
     * Constructor para la clase `Candidato`.
     *
     * @param nombreApellidos El nombre y apellidos del candidato.
     * @param dni             El DNI del candidato.
     * @param nVotos          El número de votos obtenidos por el candidato.
     * @param sindicato       El sindicato al que pertenece el candidato.
     * @throws CumplimentarPDFException Si el DNI es incorrecto.
     */
    public Candidato(String nombreApellidos, String dni, int nVotos, String sindicato) throws CumplimentarPDFException {
        this.nombreApellidos = nombreApellidos;
        setDni(dni);
        setNumeroVotos(nVotos);
        this.sindicato = sindicato;
    }

    // Métodos Getters y Setters
    public String getNombreApellidos() {
        return nombreApellidos;
    }

    public void setNombreApellidos(String nombreApellidos) {
        this.nombreApellidos = nombreApellidos;
    }

    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI del candidato.
     *
     * @param dni El DNI del candidato.
     * @throws CumplimentarPDFException Si el DNI no es válido.
     */
    public void setDni(String dni) throws CumplimentarPDFException {
        if (!verificarDNI.verificarDNI(dni)) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.dni.invalido"));
        }
        this.dni = dni;
    }

    public int getNumeroVotos() {
        return numeroVotos;
    }

    public void setNumeroVotos(int numeroVotos) {
        this.numeroVotos = numeroVotos;
    }

    public String getSindicato() {
        return sindicato;
    }

    public void setSindicato(String sindicato) {
        this.sindicato = sindicato;
    }

    /**
     * Obtiene el último apellido del candidato.
     *
     * @return El último apellido del candidato.
     */
    public String getApellido() {
        String[] partes = nombreApellidos.split(" ");
        return partes[partes.length - 1];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidato candidato = (Candidato) o;
        return Objects.equals(dni, candidato.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public int compareTo(@NotNull Candidato otro) {
        return this.nombreApellidos.compareTo(otro.nombreApellidos);
    }

    @Override
    public String toString() {
        return STR."Candidato{nombreApellidos='\{nombreApellidos}\{'\''}, dni='\{dni}\{'\''}, numeroVotos=\{numeroVotos}, sindicato='\{sindicato}\{'\''}\{'}'}";
    }
}
