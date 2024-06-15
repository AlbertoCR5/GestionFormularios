//package com.example.proyecto.modal;
//
//import com.example.proyecto.util.CumplimentarPDFException;
//
//import java.util.Date;
//
//public class Modelo_7_1 extends Modelo_6_1_Tecnicos{
//
//    private String horas;
//    private String mesLetras;
//
//    public Modelo_7_1() {
//    }
//
//    public Modelo_7_1(String colegio, String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro, String direccion,
//                      String municipio, String provincia, String promotores, String fechaConstitucion, Date fechaVotacion,
//                      String municipioElecciones, String dia, String mes, String anioFormateado) throws CumplimentarPDFException {
//        super(colegio, nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores,
//                 fechaConstitucion, fechaVotacion, municipioElecciones, dia, mes, anioFormateado);
//    }
//
//    public Modelo_7_1(String numeroMesa, String colegio, String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro,
//                      String direccion, String municipio, String provincia, String promotores, String fechaConstitucion, Date fechaVotacion,
//                      String municipioElecciones, String horas, String dia, String mes, String mesLetras, String anioFormateado, String presidente,
//                      String vocal, String secretario, String dniPresidente, String dniVocal, String dniSecretario) throws CumplimentarPDFException {
//        super(numeroMesa, colegio, nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores,
//                fechaConstitucion, fechaVotacion, municipioElecciones, horas, dia, mes, anioFormateado, presidente, vocal, secretario, dniPresidente, dniVocal, dniSecretario);
//        setHoras(horas);
//        setMesLetras(mesLetras);
//    }
//
//    @Override
//    public String getHoras() {
//        return horas;
//    }
//
//    @Override
//    public void setHoras(String horas) throws CumplimentarPDFException {
//
//        if (!validarHora.validarHora(horas)){
//            throw new CumplimentarPDFException("ERROR, Hora introducida no válida");
//        }
//        this.horas = horas;
//    }
//
//    public String getMesLetras() {
//        return mesLetras;
//    }
//
//    public void setMesLetras(String mesLetras) {
//
//        for (Meses month: Meses.values()) {
//            if (month.obtenerNombre().equalsIgnoreCase(validarFecha.getMes())){
//                this.mesLetras = month.toString();
//            }
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "com.example.proyecto.modal.Modelo_7_1 Cumplimentado";
//    }
//}
