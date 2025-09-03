package com.albertocr.gestionformularios.model;

/**
 * Modelo de datos para encapsular la información adicional del proceso de Escrutinio.
 * <p>
 * Contiene datos que se recogen durante el escrutinio y que no pertenecen
 * directamente a las entidades Empresa o Eleccion, como el recuento de votos
 * o los miembros de la mesa electoral.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.0
 */
public class EscrutinioData {

    // --- Datos del Proceso de Votación ---
    private int totalElectores;
    private int numeroRepresentantes;
    private int votantesVarones;
    private int votantesMujeres;
    private int papeletasCumplimentadas;
    private int papeletasBlancas;
    private int nulos;
    private int representantesElegidos;

    // --- Datos de la Conclusión y Miembros de la Mesa ---
    private String telefono;
    private String presidente;
    private String secretario;
    private String vocal; // Asumiendo un solo vocal para simplificar
    private String dniPresidente;
    private String dniSecretario;
    private String dniVocal;
    private String reclamaciones;

    // --- Getters y Setters ---

    public int getTotalElectores() {
        return totalElectores;
    }

    public void setTotalElectores(int totalElectores) {
        this.totalElectores = totalElectores;
    }

    public int getNumeroRepresentantes() {
        return numeroRepresentantes;
    }

    public void setNumeroRepresentantes(int numeroRepresentantes) {
        this.numeroRepresentantes = numeroRepresentantes;
    }

    public int getVotantesVarones() {
        return votantesVarones;
    }

    public void setVotantesVarones(int votantesVarones) {
        this.votantesVarones = votantesVarones;
    }

    public int getVotantesMujeres() {
        return votantesMujeres;
    }

    public void setVotantesMujeres(int votantesMujeres) {
        this.votantesMujeres = votantesMujeres;
    }

    public int getPapeletasCumplimentadas() {
        return papeletasCumplimentadas;
    }

    public void setPapeletasCumplimentadas(int papeletasCumplimentadas) {
        this.papeletasCumplimentadas = papeletasCumplimentadas;
    }

    public int getPapeletasBlancas() {
        return papeletasBlancas;
    }

    public void setPapeletasBlancas(int papeletasBlancas) {
        this.papeletasBlancas = papeletasBlancas;
    }

    public int getNulos() {
        return nulos;
    }

    public void setNulos(int nulos) {
        this.nulos = nulos;
    }

    public int getRepresentantesElegidos() {
        return representantesElegidos;
    }

    public void setRepresentantesElegidos(int representantesElegidos) {
        this.representantesElegidos = representantesElegidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPresidente() {
        return presidente;
    }

    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    public String getSecretario() {
        return secretario;
    }

    public void setSecretario(String secretario) {
        this.secretario = secretario;
    }

    public String getVocal() {
        return vocal;
    }

    public void setVocal(String vocal) {
        this.vocal = vocal;
    }

    public String getDniPresidente() {
        return dniPresidente;
    }

    public void setDniPresidente(String dniPresidente) {
        this.dniPresidente = dniPresidente;
    }

    public String getDniSecretario() {
        return dniSecretario;
    }

    public void setDniSecretario(String dniSecretario) {
        this.dniSecretario = dniSecretario;
    }

    public String getDniVocal() {
        return dniVocal;
    }

    public void setDniVocal(String dniVocal) {
        this.dniVocal = dniVocal;
    }

    public String getReclamaciones() {
        return reclamaciones;
    }

    public void setReclamaciones(String reclamaciones) {
        this.reclamaciones = reclamaciones;
    }
}
