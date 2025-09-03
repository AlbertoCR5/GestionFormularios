package com.albertocr.gestionformularios.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Modelo de datos para el Calendario del Comité.
 * <p>
 * Encapsula toda la información necesaria para generar el PDF del calendario.
 * Internamente, gestiona las fechas clave como objetos {@link LocalDate} y
 * expone getters para los componentes individuales (día, mes, año) como Strings,
 * facilitando el rellenado de formularios PDF.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.1
 */
public class CalendarioComite {

    // --- Datos base ---
    private Empresa empresa;
    private Eleccion eleccion;

    // --- Fechas clave (calculadas o editables) ---
    private LocalDate fechaInicioProceso;
    private LocalDate fechaTopeCenso;
    private LocalDate fechaPreaviso;
    private LocalDate fechaElecciones;
    private LocalDate fechaFinPropaganda;
    private LocalDate fechaReflexion;

    // --- Campos del formulario (entrada del usuario) ---
    private String horarioVotacion;
    private String lugarVotacion;
    private String localidadFirma;
    private LocalDate fechaFirma;
    private String nombrePresidente;
    private String nombreVocal;
    private String nombreSecretario;
    private LocalTime horaConstitucion;

    // --- Getters para los datos base ---

    public Empresa getEmpresa() {
        return empresa;
    }

    public Eleccion getEleccion() {
        return eleccion;
    }

    // --- Setters para los datos base y de entrada ---

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setEleccion(Eleccion eleccion) {
        this.eleccion = eleccion;
        if (eleccion != null && eleccion.getFechaConstitucion() != null) {
            calcularFechasSugeridas();
        }
    }

    private void calcularFechasSugeridas() {
        LocalDate fechaConst = eleccion.getFechaConstitucion();
        this.fechaInicioProceso = fechaConst.plusDays(1);
        this.fechaTopeCenso = fechaConst.plusDays(8);
        this.fechaPreaviso = fechaConst.plusDays(9);
        this.fechaElecciones = fechaConst.plusDays(30);
        this.fechaFinPropaganda = this.fechaElecciones.minusDays(2);
        this.fechaReflexion = this.fechaElecciones.minusDays(1);
    }

    // --- Getters y Setters para fechas editables ---

    public LocalDate getFechaInicioProceso() { return fechaInicioProceso; }
    public void setFechaInicioProceso(LocalDate fechaInicioProceso) { this.fechaInicioProceso = fechaInicioProceso; }
    public LocalDate getFechaTopeCenso() { return fechaTopeCenso; }
    public void setFechaTopeCenso(LocalDate fechaTopeCenso) { this.fechaTopeCenso = fechaTopeCenso; }
    public LocalDate getFechaPreaviso() { return fechaPreaviso; }
    public void setFechaPreaviso(LocalDate fechaPreaviso) { this.fechaPreaviso = fechaPreaviso; }
    public LocalDate getFechaElecciones() { return fechaElecciones; }
    public void setFechaElecciones(LocalDate fechaElecciones) { this.fechaElecciones = fechaElecciones; }

    // --- Getters y Setters para otros campos ---

    public String getHorarioVotacion() { return horarioVotacion; }
    public void setHorarioVotacion(String horarioVotacion) { this.horarioVotacion = horarioVotacion; }
    public String getLugarVotacion() { return lugarVotacion; }
    public void setLugarVotacion(String lugarVotacion) { this.lugarVotacion = lugarVotacion; }
    public String getLocalidadFirma() { return localidadFirma; }
    public void setLocalidadFirma(String localidadFirma) { this.localidadFirma = localidadFirma; }
    public LocalDate getFechaFirma() { return fechaFirma; }
    public void setFechaFirma(LocalDate fechaFirma) { this.fechaFirma = fechaFirma; }
    public String getNombrePresidente() { return nombrePresidente; }
    public void setNombrePresidente(String nombrePresidente) { this.nombrePresidente = nombrePresidente; }
    public String getNombreVocal() { return nombreVocal; }
    public void setNombreVocal(String nombreVocal) { this.nombreVocal = nombreVocal; }
    public String getNombreSecretario() { return nombreSecretario; }
    public void setNombreSecretario(String nombreSecretario) { this.nombreSecretario = nombreSecretario; }
    public LocalTime getHoraConstitucionAsTime() { return horaConstitucion; }
    public void setHoraConstitucion(LocalTime horaConstitucion) { this.horaConstitucion = horaConstitucion; }


    // --- Getters para los campos del PDF (con formato) ---

    private String getDay(LocalDate date) {
        return date != null ? String.valueOf(date.getDayOfMonth()) : "";
    }

    private String getMonth(LocalDate date) {
        if (date == null) return "";
        return date.format(DateTimeFormatter.ofPattern("MMMM", Locale.of("es", "ES")));
    }

    private String getYear(LocalDate date) {
        return date != null ? String.valueOf(date.getYear()).substring(2) : "";
    }

    public String getHoraConstitucion() {
        return horaConstitucion != null ? horaConstitucion.format(DateTimeFormatter.ofPattern("HH:mm")) : "";
    }

    public String getDiaInicioExposicionCenso() { return getDay(fechaInicioProceso); }
    public String getMesInicioExposicionCenso() { return getMonth(fechaInicioProceso); }
    public String getDiaFinExposicionCenso() { return getDay(fechaTopeCenso); }
    public String getMesFinExposicionCenso() { return getMonth(fechaTopeCenso); }
    public String getAnioFinExposicionCenso() { return getYear(fechaTopeCenso); }
    public String getDiaReclamacionCenso() { return getDay(fechaTopeCenso.plusDays(1)); }
    public String getMesReclamacionCenso() { return getMonth(fechaTopeCenso.plusDays(1)); }
    public String getAnioReclamacionCenso() { return getYear(fechaTopeCenso.plusDays(1)); }
    public String getDiaResolucionCenso() { return getDay(fechaTopeCenso.plusDays(2)); }
    public String getMesResolucionCenso() { return getMonth(fechaTopeCenso.plusDays(2)); }
    public String getAnioResolucionCenso() { return getYear(fechaTopeCenso.plusDays(2)); }
    public String getDiaExposicionCensoDefinitivo() { return getDay(fechaTopeCenso.plusDays(3)); }
    public String getMesExposicionCensoDefinitivo() { return getMonth(fechaTopeCenso.plusDays(3)); }
    public String getAnioExposicionCensoDefinitivo() { return getYear(fechaTopeCenso.plusDays(3)); }
    public String getDiaInicioPresentacionCandidaturas() { return getDay(fechaTopeCenso.plusDays(3)); }
    public String getMesInicioPresentacionCandidaturas() { return getMonth(fechaTopeCenso.plusDays(3)); }
    public String getDiaFinPresentacionCandidaturas() { return getDay(fechaPreaviso); }
    public String getMesFinPresentacionCandidaturas() { return getMonth(fechaPreaviso); }
    public String getAnioFinPresentacionCandidaturas() { return getYear(fechaPreaviso); }
    public String getDiaInicioExposicionCandidaturas() { return getDay(fechaPreaviso.plusDays(1)); }
    public String getMesInicioExposicionCandidaturas() { return getMonth(fechaPreaviso.plusDays(1)); }
    public String getDiaFinExposicionCandidaturas() { return getDay(fechaPreaviso.plusDays(2)); }
    public String getMesFinExposicionCandidaturas() { return getMonth(fechaPreaviso.plusDays(2)); }
    public String getAnioFinExposicionCandidaturas() { return getYear(fechaPreaviso.plusDays(2)); }
    public String getDiaReclamacionCandidaturas() { return getDay(fechaPreaviso.plusDays(3)); }
    public String getMesReclamacionCandidaturas() { return getMonth(fechaPreaviso.plusDays(3)); }
    public String getAnioReclamacionCandidaturas() { return getYear(fechaPreaviso.plusDays(3)); }
    public String getDiaProclamacionCandidaturas() { return getDay(fechaPreaviso.plusDays(4)); }
    public String getMesProclamacionCandidaturas() { return getMonth(fechaPreaviso.plusDays(4)); }
    public String getAnioProclamacionCandidaturas() { return getYear(fechaPreaviso.plusDays(4)); }
    public String getDiaInicioPropaganda() { return getDay(fechaPreaviso.plusDays(5)); }
    public String getMesInicioPropaganda() { return getMonth(fechaPreaviso.plusDays(5)); }
    public String getDiaFinPropaganda() { return getDay(fechaFinPropaganda); }
    public String getAnioFinPropaganda() { return getYear(fechaFinPropaganda); }
    public String getMesFinPropaganda() { return getMonth(fechaFinPropaganda); }
    public String getDiaReflexion() { return getDay(fechaReflexion); }
    public String getMesReflexion() { return getMonth(fechaReflexion); }
    public String getAnioReflexion() { return getYear(fechaReflexion); }
    public String getDiaVotacion() { return getDay(fechaElecciones); }
    public String getMesVotacion() { return getMonth(fechaElecciones); }
    public String getAnioVotacion() { return getYear(fechaElecciones); }
}
