package com.albertocr.gestionformularios.model;

import java.time.LocalDate;

/**
 * Representa el modelo de datos para un Candidato.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.3
 */
public class Candidato {

    private int id;
    private String nombre;
    private String apellidos;
    private String dni;
    private String sindicato;
    private int antiguedadMeses;
    private LocalDate fechaNacimiento;
    private LocalDate fechaAntiguedad; // Tarea 4
    private String colegio; // Nuevo: indica el colegio al que pertenece el candidato (Especialistas / Técnicos y Administrativos)

    public Candidato() {
    }

    public Candidato(String nombre, String dni, LocalDate fechaNacimiento, LocalDate fechaAntiguedad, String sindicato) {
        this.nombre = nombre;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaAntiguedad = fechaAntiguedad;
        this.sindicato = sindicato;
    }

    public Candidato(String nombre, String apellidos, String dni, String sindicato, int antiguedadMeses, LocalDate fechaNacimiento, LocalDate fechaAntiguedad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.sindicato = sindicato;
        this.antiguedadMeses = antiguedadMeses;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaAntiguedad = fechaAntiguedad; // Tarea 4
    }

    /**
     * Constructor extendido incluyendo el colegio del candidato.
     */
    public Candidato(String nombre, String apellidos, String dni, String sindicato, int antiguedadMeses, LocalDate fechaNacimiento, LocalDate fechaAntiguedad, String colegio) {
        this(nombre, apellidos, dni, sindicato, antiguedadMeses, fechaNacimiento, fechaAntiguedad);
        this.colegio = colegio;
    }

    // Constructor anterior para mantener compatibilidad
    public Candidato(String nombre, String apellidos, String dni, String sindicato, int antiguedadMeses, LocalDate fechaNacimiento) {
        this(nombre, apellidos, dni, sindicato, antiguedadMeses, fechaNacimiento, null);
    }

    // Getters y Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getSindicato() { return sindicato; }
    public void setSindicato(String sindicato) { this.sindicato = sindicato; }

    public int getAntiguedadMeses() { return antiguedadMeses; }
    public void setAntiguedadMeses(int antiguedadMeses) { this.antiguedadMeses = antiguedadMeses; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public LocalDate getFechaAntiguedad() { return fechaAntiguedad; } // Tarea 4
    public void setFechaAntiguedad(LocalDate fechaAntiguedad) { this.fechaAntiguedad = fechaAntiguedad; } // Tarea 4

    public String getColegio() { return colegio; }
    public void setColegio(String colegio) { this.colegio = colegio; }
}
