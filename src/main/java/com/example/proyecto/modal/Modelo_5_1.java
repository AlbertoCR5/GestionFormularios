package com.example.proyecto.modal;

import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.ConversorFechaToLetras;
import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.MessageManager;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * La clase `Modelo_5_1` extiende la clase `Modelo_3`.
 * Representa el Modelo_5_1 que incluye fecha de constitución y escrutinio,
 * datos de los candidatos y reclamaciones.
 *
 * @autor Alberto Castro
 * @version 1.0
 */
public class Modelo_5_1 extends Modelo_3 {

    // Atributos
    private String diaVotacion;
    private String anioVotacion;
    private String mesVotacion, mesVotacionLetras;
    private String fechaEscrutinio;
    private String fechaEscrutinioLetras;
    private String fechaConstitucionLetras;

    // Datos de los candidatos
    private ArrayList<Candidato> candidatos;

    // Datos de las reclamaciones
    private String reclamaciones1;
    private String reclamaciones2;
    private String reclamaciones3;
    private String resolucionMesa;

    // Constructor por defecto
    public Modelo_5_1() {
        this.candidatos = new ArrayList<>();
    }

    // Constructor con parámetros
    public Modelo_5_1(String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro, String direccion,
                      String municipio, String provincia, String promotores, String fechaConstitucion, String municipioElecciones,
                      String dia, String mes, String anioFormateado, String fechaEscrutinio, String fechaEscrutinioLetras) throws CumplimentarPDFException {
        super(nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores,
                fechaConstitucion, municipioElecciones, dia, mes, anioFormateado);
        setFechaEscrutinio(fechaEscrutinio);
        setFechaEscrutinioLetras(fechaEscrutinio);
        this.candidatos = new ArrayList<>();
    }

    // Constructor con parámetros adicionales
    public Modelo_5_1(String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro, String direccion,
                      String municipio, String provincia, String promotores, String fechaConstitucion, String municipioElecciones,
                      String dia, String mes, String anioFormateado, String fechaEscrutinio, String fechaEscrutinioLetras,
                      String mesVotacion, String mesVotacionLetras, ArrayList<Candidato> candidatos, String reclamaciones1,
                      String reclamaciones2, String reclamaciones3, String resolucionMesa) throws CumplimentarPDFException {
        this(nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores,
                fechaConstitucion, municipioElecciones, dia, mes, anioFormateado, fechaEscrutinio, fechaEscrutinioLetras);
        setMesVotacionLetras(mesVotacionLetras);
        this.candidatos = candidatos != null ? candidatos : new ArrayList<>();
        this.reclamaciones1 = reclamaciones1;
        this.reclamaciones2 = reclamaciones2;
        this.reclamaciones3 = reclamaciones3;
        this.resolucionMesa = resolucionMesa;
    }

    // Métodos de acceso y mutadores con validaciones
    public String getFechaEscrutinio() {
        return fechaEscrutinio;
    }

    public void setFechaEscrutinio(String fechaEscrutinio) throws CumplimentarPDFException {
        if (!validarFecha.esFormatoFechaValido(fechaEscrutinio)) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.fecha.escrutinio.incorrecto"));
        }
        String[] partes = fechaEscrutinio.split("/");
        String dia = partes[0].length() == 1 ? STR."0\{partes[0]}" : partes[0];
        String mes = partes[1].length() == 1 ? STR."0\{partes[1]}" : partes[1];
        String anio = partes[2];
        this.fechaEscrutinio = STR."\{dia}/\{mes}/\{anio}";
        setFechaEscrutinioLetras(fechaEscrutinio);
        setDiaVotacion(fechaEscrutinio);
        setMesVotacion(fechaEscrutinio);
        setAnioVotacion(fechaEscrutinio);
    }

    public String getFechaEscrutinioLetras() {
        return fechaEscrutinioLetras;
    }

    public void setFechaEscrutinioLetras(String fechaEscrutinio) {
        this.fechaEscrutinioLetras = ConversorFechaToLetras.convertirFechaCompleta(fechaEscrutinio);
    }

    public String getDiaVotacion() {
        return diaVotacion;
    }

    public void setDiaVotacion(String fecha) throws CumplimentarPDFException {
        String dia = fecha.split("/")[0];
        dia = dia.length() == 1 ? STR."0\{dia}" : dia;
        if (Integer.parseInt(dia) < Constantes.PRIMER_DIA_MES || Integer.parseInt(dia) > Constantes.ULTIMO_DIA_MES) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.dia.incorrecto"));
        }
        this.diaVotacion = dia;
    }

    public String getMesVotacion() {
        return mesVotacion;
    }

    public void setMesVotacion(String fecha) throws CumplimentarPDFException {
        String[] partes = fecha.split("/");
        if (partes.length != 3) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.formato.fecha.incorrecto"));
        }
        this.mesVotacion = partes[1].length() == 1 ? STR."0\{partes[1]}" : partes[1];
    }

    public String getAnioVotacion() {
        return anioVotacion;
    }

    public void setAnioVotacion(String fecha) throws CumplimentarPDFException {
        String anio = fecha.split("/")[2];
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (Integer.parseInt(anio) < currentYear || Integer.parseInt(anio) > currentYear + 1) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.anio.incorrecto"));
        }
        this.anioVotacion = anio;
    }

    public String getMesVotacionLetras() {
        return mesVotacionLetras;
    }

    public void setMesVotacionLetras(String mesVotacion) {
        this.mesVotacionLetras = ConversorFechaToLetras.convertirMesALetras(mesVotacion);
    }

    public String getFechaConstitucionLetras() {
        return fechaConstitucionLetras;
    }

    public ArrayList<Candidato> getCandidatos() {
        return candidatos;
    }

    public void setCandidatos(ArrayList<Candidato> candidatos) {
        this.candidatos = candidatos;
    }

    public String getReclamaciones1() {
        return reclamaciones1;
    }

    public void setReclamaciones1(String reclamaciones1) {
        this.reclamaciones1 = reclamaciones1;
    }

    public String getReclamaciones2() {
        return reclamaciones2;
    }

    public void setReclamaciones2(String reclamaciones2) {
        this.reclamaciones2 = reclamaciones2;
    }

    public String getReclamaciones3() {
        return reclamaciones3;
    }

    public void setReclamaciones3(String reclamaciones3) {
        this.reclamaciones3 = reclamaciones3;
    }

    public String getResolucionMesa() {
        return resolucionMesa;
    }

    public void setResolucionMesa(String resolucionMesa) {
        this.resolucionMesa = resolucionMesa;
    }

    public void agregarCandidato(Candidato nuevoCandidato) throws CumplimentarPDFException {
        boolean esAnadido = candidatos.add(nuevoCandidato);
        if (!esAnadido) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.anadir.candidato") + nuevoCandidato);
        }
    }
}
