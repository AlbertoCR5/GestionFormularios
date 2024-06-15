//package com.example.proyecto.modal;
//
//import com.example.proyecto.util.CumplimentarPDFException;
//
//import java.util.Date;
//
//public class Modelo_7_2 extends Modelo_6_1_Especialistas{
//
//    private String[] reclamaciones;
//
//    public Modelo_7_2() {
//    }
//
//    public Modelo_7_2(String numeroMesa, String colegio, String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro,
//                      String direccion, String municipio, String provincia, String promotores, String fechaConstitucion, Date fechaVotacion,
//                      String municipioElecciones, String horas, String dia, String mes, String anioFormateado, String presidente,
//                      String vocal, String secretario, String dniPresidente, String dniVocal, String dniSecretario) throws CumplimentarPDFException {
//        super(numeroMesa, colegio, nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores, fechaConstitucion,
//                fechaVotacion, municipioElecciones, horas, dia, mes, anioFormateado, presidente, vocal, secretario, dniPresidente, dniVocal, dniSecretario);
//    }
//
//    public Modelo_7_2(String numeroMesa, String colegio, String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro,
//                      String direccion, String municipio, String provincia, String promotores, String fechaConstitucion, Date fechaVotacion,
//                      String municipioElecciones, String horas, String dia, String mes, String anioFormateado, String presidente, String vocal,
//                      String secretario, String dniPresidente, String dniVocal, String dniSecretario, String[] reclamaciones) throws CumplimentarPDFException {
//        super(numeroMesa, colegio, nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores, fechaConstitucion,
//                fechaVotacion, municipioElecciones, horas, dia, mes, anioFormateado, presidente, vocal, secretario, dniPresidente, dniVocal, dniSecretario);
//        setReclamaciones(reclamaciones);
//    }
//
//    public String[] getReclamaciones() {
//        return reclamaciones;
//    }
//
//    public void setReclamaciones(String[] reclamaciones) {
//        this.reclamaciones = reclamaciones;
//    }
//
//    @Override
//    public String toString() {
//        return "com.example.proyecto.modal.Modelo_7_2 Cumlimentado";
//    }
//}
