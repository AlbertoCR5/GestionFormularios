package com.example.proyecto.modal;

import com.example.proyecto.util.*;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Calendar;

/**
 * Clase que representa un preaviso para elecciones en una empresa.
 * Incluye la información necesaria para la generación de un preaviso y métodos para validar y establecer esta información.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class Preaviso {


    private String nombreEmpresa;
    private String CIF;
    private String nombreComercial;
    private String nombreCentro;
    private String direccion;
    private String municipio;
    private String codigoPostal;
    private String comarca;
    private String provincia;
    private String totalTrabajadores;
    private String numeroISS;
    private String mesElecciones;
    private String anioElecciones;
    private boolean total;
    private boolean parcial;
    private String promotores;
    private String fechaConstitucion;
    private String mesConstitucionLetras;
    private String electores;
    private String fechaPreaviso;
    private String diaPreaviso;
    private String mesPreaviso;
    private String anioPreaviso;

    // Constructores
    public Preaviso() {
    }

    public Preaviso(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public Preaviso(String nombreEmpresa, String fechaConstitucion) throws CumplimentarPDFException {
        this.nombreEmpresa = nombreEmpresa;
        setFechaConstitucion(fechaConstitucion);
    }

    public Preaviso(String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro, String direccion, String municipio, String provincia, String promotores, String fechaConstitucion) throws CumplimentarPDFException {
        this(nombreEmpresa);
        setCIF(CIF);
        setNombreComercial(nombreComercial);
        setNombreCentro(nombreCentro);
        this.direccion = direccion;
        this.municipio = municipio;
        setProvincia(provincia);
        this.promotores = promotores;
        setFechaConstitucion(fechaConstitucion);
    }

    public Preaviso(String nombreEmpresa, String nombreCentro, String municipio) {
        this(nombreEmpresa);
        setNombreCentro(nombreCentro);
        this.municipio = municipio;
    }

    public Preaviso(String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro, String direccion, String municipio, String codigoPostal, String comarca, String provincia, String numeroISS) throws CumplimentarPDFException {
        this(nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, null, null);
        setCodigoPostal(codigoPostal);
        this.comarca = comarca;
        setNumeroISS(numeroISS);
    }

    public Preaviso(String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro, String direccion, String municipio, String provincia) throws CumplimentarPDFException {
        this(nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, null, null);
    }

    public Preaviso(String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro, String direccion, String municipio,
                    String codigoPostal, String comarca, String provincia, String totalTrabajadores, String numeroISS, String mesElecciones,
                    String anioElecciones, boolean total, boolean parcial, String promotores, String fechaConstitucion, String diaPreaviso,
                    String mesPreaviso, short anioPreaviso) throws CumplimentarPDFException {
        this(nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, codigoPostal, comarca, provincia, numeroISS);
        this.totalTrabajadores = totalTrabajadores;
        setMesElecciones(mesElecciones);
        setAnioElecciones(anioElecciones);
        setTotal(total);
        setParcial(parcial);
        this.promotores = promotores;
        setFechaConstitucion(fechaConstitucion);
        this.electores = totalTrabajadores;
        setDiaPreaviso(diaPreaviso);
        setMesPreaviso(mesPreaviso);
        setAnioPreaviso(String.valueOf(anioPreaviso));
    }

    // Métodos Getters y Setters
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) throws CumplimentarPDFException {
        if (nombreEmpresa.isEmpty()) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.nombre.empresa.vacio"));
        }
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getCIF() {
        return CIF;
    }

    public void setCIF(String CIF) throws CumplimentarPDFException {
        if (!ValidadorCampos.verificarCIF(CIF)) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.cif.incorrecto"));
        }
        this.CIF = CIF;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(@NotNull String nombreComercial) {
        this.nombreComercial = nombreComercial.isEmpty() ? nombreEmpresa : nombreComercial;
    }

    public String getNombreCentro() {
        return nombreCentro;
    }

    public void setNombreCentro(@NotNull String nombreCentro) {
        this.nombreCentro = nombreCentro.isEmpty() ? nombreEmpresa : nombreCentro;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) throws CumplimentarPDFException {
        if (direccion.isEmpty()) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.direccion.vacia"));
        }
        this.direccion = direccion;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) throws CumplimentarPDFException {
        if (municipio.isEmpty()) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.municipio.vacio"));
        }
        this.municipio = municipio;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(@NotNull String codigoPostal) throws CumplimentarPDFException {
        if (codigoPostal.length() != Constantes.DIGITOS_CODIGO_POSTAL || !codigoPostal.matches("[0-9]+")) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.codigo.postal.incorrecto"));
        }
        this.codigoPostal = codigoPostal;
    }

    public String getComarca() {
        return comarca;
    }

    public void setComarca(String comarca) {
        this.comarca = comarca;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        if (provincia.isEmpty()) {
            provincia = ProvinciasAndalucia.SEVILLA.getNombre();
        }
        this.provincia = provincia;
    }

    public String getTotalTrabajadores() {
        return totalTrabajadores;
    }

    public void setTotalTrabajadores(String totalTrabajadores) throws CumplimentarPDFException {
        if (!totalTrabajadores.matches("[0-9]+") || Integer.parseInt(totalTrabajadores) < Constantes.MINIMO_ELECTORES) {
            throw new CumplimentarPDFException(MessageFormat.format(MessageManager.getMessage("error.total.trabajadores.incorrecto"), Constantes.MINIMO_ELECTORES));
        }
        this.totalTrabajadores = totalTrabajadores;
    }

    public String getNumeroISS() {
        return numeroISS;
    }

    public void setNumeroISS(@NotNull String numeroISS) throws CumplimentarPDFException {
        if (!numeroISS.isEmpty() && !numeroISS.matches(Constantes.FORMATO_ISS)) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.numero.iss.incorrecto"));
        }
        this.numeroISS = numeroISS;
    }

    public String getMesElecciones() {
        return mesElecciones;
    }

    public void setMesElecciones(@NotNull String mesElecciones) throws CumplimentarPDFException {
        if (mesElecciones.isEmpty()) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.mes.elecciones.vacio"));
        }
        this.mesElecciones = mesElecciones;
    }

    public String getAnioElecciones() {
        return anioElecciones;
    }

    public void setAnioElecciones(@NotNull String anioElecciones) throws CumplimentarPDFException {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (!anioElecciones.matches("[0-9]+") || Integer.parseInt(anioElecciones) < currentYear || Integer.parseInt(anioElecciones) > currentYear + 1) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.anio.elecciones.incorrecto"));
        }
        this.anioElecciones = anioElecciones;
    }

    public boolean isTotal() {
        return total;
    }

    public void setTotal(boolean total) {
        if (isParcial()) {
            total = false;
        }
        this.total = total;
    }

    public boolean isParcial() {
        return parcial;
    }

    public void setParcial(boolean parcial) {
        if (isTotal()) {
            parcial = false;
        }
        this.parcial = parcial;
    }

    public String getPromotores() {
        return promotores;
    }

    public void setPromotores(String promotores) {
        if (promotores.isEmpty()) {
            promotores = Constantes.PROMOTORES;
        }
        this.promotores = promotores;
    }

    public String getFechaConstitucion() {
        return fechaConstitucion;
    }

    public void setFechaConstitucion(String fechaConstitucion) throws CumplimentarPDFException {
        if (ValidadorFecha.esFormatoFechaNoValido(fechaConstitucion)) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.fecha.constitucion.incorrecto").concat(String.valueOf(Constantes.FORMATO_FECHA)));
        }
        String[] partes = fechaConstitucion.split("/");
        String dia = partes[0].length() == 1 ? STR."0\{partes[0]}" : partes[0];
        String mes = partes[1].length() == 1 ? STR."0\{partes[1]}" : partes[1];
        String anio = partes[2];

        this.fechaConstitucion = STR."\{dia}/\{mes}/\{anio}";
        setMesConstitucionLetas(mes);
    }

    public String getMesConstitucionLetras() {
        return mesConstitucionLetras;
    }
    private void setMesConstitucionLetas(String mes) {
       this.mesConstitucionLetras = ConversorFechaToLetras.convertirMesALetras(mes);
    }

    public String getElectores() {
        return electores;
    }

    public void setElectores(String electores) {
        this.electores = electores;
    }

    public String getFechaPreaviso() {
        return fechaPreaviso;
    }
    /**
     * Establece la fecha de preaviso y realiza las validaciones necesarias.
     *
     * @param fechaPreaviso La fecha de preaviso en formato dd/MM/yyyy.
     * @throws CumplimentarPDFException Si la fecha es inválida o no cumple con los requisitos mínimos.
     */
    public void setFechaPreaviso(String fechaPreaviso) throws CumplimentarPDFException {
        // Verificar si la fecha de preaviso está vacía
        if (fechaPreaviso.isEmpty()) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.fecha.preaviso.incorrecto").concat(" ").concat(Constantes.FORMATO_FECHA.toString().concat("holaqqq")));
        }

        try {

        String[] partes = fechaPreaviso.split("/");
        setDiaPreaviso(diaPreaviso = partes[0].length() == 1 ? "0" + partes[0] : partes[0]);
        setMesPreaviso(mesPreaviso = partes[1].length() == 1 ? "0" + partes[1] : partes[1]);
        setAnioPreaviso(anioPreaviso = partes[2]);
        fechaPreaviso = STR."\{diaPreaviso}/\{partes[1].length() == 1 ? "0" + partes[1] : partes[1]}/\{anioPreaviso}";

        LocalDate fechaPreavisoDate = LocalDate.parse(fechaPreaviso, Constantes.FORMATO_FECHA);
        LocalDate fechaConstitucionDate = LocalDate.parse(getFechaConstitucion(), Constantes.FORMATO_FECHA);

        if (fechaConstitucion != null) {
            long diasEntreFechas = java.time.temporal.ChronoUnit.DAYS.between(fechaPreavisoDate, fechaConstitucionDate);
            if (diasEntreFechas < Constantes.DIAS_ENTRE_PREAVISO_Y_CONSTITUCION) {
                throw new CumplimentarPDFException(MessageFormat.format(MessageManager.getMessage("preaviso.error_fechas"), Constantes.DIAS_ENTRE_PREAVISO_Y_CONSTITUCION));
            }
        }
        this.fechaPreaviso = fechaPreaviso;
        } catch (DateTimeParseException e) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.fecha.preaviso.incorrecto").concat(": ").concat(fechaPreaviso));
        }
    }

    public String getDiaPreaviso() {
        return diaPreaviso;
    }

    public void setDiaPreaviso(String diaPreaviso) throws CumplimentarPDFException {
        if (!diaPreaviso.matches("[0-9]+") || Integer.parseInt(diaPreaviso) < Constantes.PRIMER_DIA_MES || Integer.parseInt(diaPreaviso) > Constantes.ULTIMO_DIA_MES) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.dia.preaviso.incorrecto"));
        }
        this.diaPreaviso = diaPreaviso.length() == 1 ? STR."0\{diaPreaviso}" : diaPreaviso;
    }

    public String getMesPreaviso() {
        return mesPreaviso;
    }

    public void setMesPreaviso(@NotNull String mesPreaviso) throws CumplimentarPDFException {
        if (!mesPreaviso.matches("[0-9]+") || Integer.parseInt(mesPreaviso) < Constantes.PRIMER_MES || Integer.parseInt(mesPreaviso) > Constantes.ULTIMO_MES) {
            throw new CumplimentarPDFException(MessageFormat.format(MessageManager.getMessage("error.mes.preaviso.incorrecto"), Constantes.PRIMER_MES, Constantes.ULTIMO_MES));
        }
        this.mesPreaviso = mesPreaviso.length() == 1 ? STR."0\{mesPreaviso}" : mesPreaviso;
        this.mesPreaviso = Meses.obtenerNombrePorNumero(this.mesPreaviso);
    }

    public String getAnioPreaviso() {
        return anioPreaviso;
    }

    public void setAnioPreaviso(@NotNull String anioPreaviso) throws CumplimentarPDFException {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (!anioPreaviso.matches("[0-9]+") || Integer.parseInt(anioPreaviso) < currentYear || Integer.parseInt(anioPreaviso) > currentYear + 1) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.anio.preaviso.incorrecto"));
        }
        this.anioPreaviso = anioPreaviso;
    }

    @Override
    public String toString() {
        return STR."Preaviso de la Empresa \{nombreEmpresa} Generado.\n";
    }
}
