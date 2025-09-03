package com.albertocr.gestionformularios.model;

import java.time.LocalDate;

/**
 * Representa el modelo de datos para un proceso de Elección Sindical.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.1
 */
public class Eleccion {

    private int id;
    private int idEmpresa;
    private int numeroTrabajadores;
    private LocalDate fechaConstitucion;
    private String promotores;
    private String localidadFecha;
    private LocalDate fecha;
    private String tipoEleccion; // TOTAL o PARCIAL

    public Eleccion() {
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdEmpresa() { return idEmpresa; }
    public void setIdEmpresa(int idEmpresa) { this.idEmpresa = idEmpresa; }
    public int getNumeroTrabajadores() { return numeroTrabajadores; }
    public void setNumeroTrabajadores(int numeroTrabajadores) { this.numeroTrabajadores = numeroTrabajadores; }
    public LocalDate getFechaConstitucion() { return fechaConstitucion; }
    public void setFechaConstitucion(LocalDate fechaConstitucion) { this.fechaConstitucion = fechaConstitucion; }
    public String getPromotores() { return promotores; }
    public void setPromotores(String promotores) { this.promotores = promotores; }
    public String getLocalidadFecha() { return localidadFecha; }
    public void setLocalidadFecha(String localidadFecha) { this.localidadFecha = localidadFecha; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getTipoEleccion() { return tipoEleccion; }
    public void setTipoEleccion(String tipoEleccion) { this.tipoEleccion = tipoEleccion; }
}
