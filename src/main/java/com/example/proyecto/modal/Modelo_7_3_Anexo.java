//package com.example.proyecto.modal;
//
//import java.util.List;
//
//public class Modelo_7_3_Anexo extends Preaviso{
//
//    private String dia;
//    private String mes;
//    private String anio;
//    private String fechaEscrutinio;
//    private List<Representantes_Elegidos_Comite> candidatosElegidos;
//
//    public Modelo_7_3_Anexo() {
//    }
//
//    public Modelo_7_3_Anexo(String nombreEmpresa, String nombreCentro, String municipio, String dia, String mes, String anio) {
//        super(nombreEmpresa, nombreCentro, municipio);
//        setDia(dia);
//        setMes(mes);
//        setAnio(anio);
//    }
//
//    public String getFechaEscrutinio() {
//        return fechaEscrutinio;
//    }
//
//    public void setFechaEscrutinio(String fechaEscrutinio) {
//        String[] partes = fechaEscrutinio.split("/");
//
//        this.setDia(partes[0]);
//        this.setMes(partes[1]);
//        this.setAnio(partes[2]);
//        this.fechaEscrutinio = fechaEscrutinio;
//    }
//
//    public String getDia() {
//        return dia;
//    }
//
//    public void setDia(String dia) {
//        this.dia = dia;
//    }
//
//    public String getMes() {
//        return mes;
//    }
//
//    public void setMes(String mes) {
//        this.mes = mes;
//    }
//
//    public String getAnio() {
//        return anio;
//    }
//
//    public void setAnio(String anio) {
//        this.anio = anio;
//    }
//
//    public List<Representantes_Elegidos_Comite> getCandidatosElegidos() {
//        return candidatosElegidos;
//    }
//
//    public void setCandidatosElegidos(List<Representantes_Elegidos_Comite> candidatosElegidos) {
//        this.candidatosElegidos = candidatosElegidos;
//    }
//}
