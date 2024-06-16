package com.example.proyecto.modal;

import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.MessageManager;

import java.util.List;

/**
 * Clase que representa el modelo 7.3 anexos para el proceso de escrutinio.
 * Extiende de Preaviso y añade propiedades específicas.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class Modelo_7_3_Anexo extends Preaviso {

    private String dia;
    private String mes;
    private String anio;
    private String fechaEscrutinio;
    private List<Representantes_Elegidos_Comite> candidatosElegidos;

    /**
     * Constructor vacío.
     */
    public Modelo_7_3_Anexo() {
    }

    /**
     * Constructor con parámetros iniciales.
     *
     * @param nombreEmpresa El nombre de la empresa.
     * @param nombreCentro El nombre del centro.
     * @param municipio El municipio del centro.
     * @param dia El día del escrutinio.
     * @param mes El mes del escrutinio.
     * @param anio El año del escrutinio.
     */
    public Modelo_7_3_Anexo(String nombreEmpresa, String nombreCentro, String municipio, String dia, String mes, String anio) {
        super(nombreEmpresa, nombreCentro, municipio);
        setDia(dia);
        setMes(mes);
        setAnio(anio);
    }

    /**
     * Obtiene la fecha del escrutinio.
     *
     * @return La fecha del escrutinio.
     */
    public String getFechaEscrutinio() {
        return fechaEscrutinio;
    }

    /**
     * Establece la fecha del escrutinio.
     *
     * @param fechaEscrutinio La fecha del escrutinio en formato dd/MM/yyyy.
     */
    public void setFechaEscrutinio(String fechaEscrutinio) {
        try {
            String[] partes = fechaEscrutinio.split("/");
            if (partes.length != 3) {
                throw new CumplimentarPDFException(MessageManager.getMessage("error.fecha.incorrecta"));
            }
            setDia(partes[0]);
            setMes(partes[1]);
            setAnio(partes[2]);
            this.fechaEscrutinio = fechaEscrutinio;
        } catch (CumplimentarPDFException e) {
            // Log error or handle it accordingly
        }
    }

    /**
     * Obtiene el día del escrutinio.
     *
     * @return El día del escrutinio.
     */
    public String getDia() {
        return dia;
    }

    /**
     * Establece el día del escrutinio.
     *
     * @param dia El día del escrutinio.
     */
    public void setDia(String dia) {
        this.dia = dia;
    }

    /**
     * Obtiene el mes del escrutinio.
     *
     * @return El mes del escrutinio.
     */
    public String getMes() {
        return mes;
    }

    /**
     * Establece el mes del escrutinio.
     *
     * @param mes El mes del escrutinio.
     */
    public void setMes(String mes) {
        this.mes = mes;
    }

    /**
     * Obtiene el año del escrutinio.
     *
     * @return El año del escrutinio.
     */
    public String getAnio() {
        return anio;
    }

    /**
     * Establece el año del escrutinio.
     *
     * @param anio El año del escrutinio.
     */
    public void setAnio(String anio) {
        this.anio = anio;
    }

    /**
     * Obtiene la lista de candidatos elegidos.
     *
     * @return La lista de candidatos elegidos.
     */
    public List<Representantes_Elegidos_Comite> getCandidatosElegidos() {
        return candidatosElegidos;
    }

    /**
     * Establece la lista de candidatos elegidos.
     *
     * @param candidatosElegidos La lista de candidatos elegidos.
     */
    public void setCandidatosElegidos(List<Representantes_Elegidos_Comite> candidatosElegidos) {
        this.candidatosElegidos = candidatosElegidos;
    }

    @Override
    public String toString() {
        return STR."Modelo_7_3_Anexo \{getNombreEmpresa()} Cumplimentado";
    }
}
