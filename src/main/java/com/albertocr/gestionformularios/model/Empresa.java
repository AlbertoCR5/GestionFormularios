package com.albertocr.gestionformularios.model;

/**
 * Representa el modelo de datos para una Empresa.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.4
 */
public class Empresa {

    private int id;
    private String nombre;
    private String cif;
    private String nombreComercial;
    private String nombreCentro;
    private String direccion;
    private String municipio;
    private String comarca;
    private String provincia;
    private String codigoPostal;
    private String numeroISS;
    private String numeroId;
    private String localidad;
    private String actividad;
    private String convenio;
    private int numeroTrabajadores;
    private TipoColegioElectoral tipoColegio;

    public Empresa() {
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCif() { return cif; }
    public void setCif(String cif) { this.cif = cif; }
    public String getNombreComercial() { return nombreComercial; }
    public void setNombreComercial(String nombreComercial) { this.nombreComercial = nombreComercial; }
    public String getNombreCentro() { return nombreCentro; }
    public void setNombreCentro(String nombreCentro) { this.nombreCentro = nombreCentro; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }
    public String getComarca() { return comarca; }
    public void setComarca(String comarca) { this.comarca = comarca; }
    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }
    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }
    public String getNumeroISS() { return numeroISS; }
    public void setNumeroISS(String numeroISS) { this.numeroISS = numeroISS; }
    public String getNumeroId() { return numeroId; }
    public void setNumeroId(String numeroId) { this.numeroId = numeroId; }
    public String getLocalidad() { return localidad; }
    public void setLocalidad(String localidad) { this.localidad = localidad; }
    public String getActividad() { return actividad; }
    public void setActividad(String actividad) { this.actividad = actividad; }
    public String getConvenio() { return convenio; }
    public void setConvenio(String convenio) { this.convenio = convenio; }
    public int getNumeroTrabajadores() {
        return numeroTrabajadores;
    }
    public void setNumeroTrabajadores(int numeroTrabajadores) {
        this.numeroTrabajadores = numeroTrabajadores;
    }
    public TipoColegioElectoral getTipoColegio() {
        return tipoColegio;
    }
    public void setTipoColegio(TipoColegioElectoral tipoColegio) {
        this.tipoColegio = tipoColegio;
    }
}
