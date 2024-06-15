//package com.example.proyecto.modal;
//
//import java.util.regex.Pattern;
//
//public class Modelo_7_3_Acta_Global extends Preaviso {
//
//    private String actvEcono;
//    private String actvEcono1;
//    private String direccionCentro;
//    private String direccionCentro1;
//    private String telefono;
//    private String actvEcono2;
//    private String actvEcono3;
//    private boolean casillaVerificacion2;
//    private String nombreConvenio;
//    private String numeroConvenio;
//    private int trabajadoresFijos;
//    private int trabajadoresEventuales;
//    private int trabajadoresJornadas;
//    private int trabajadoresEventualesComputo;
//    private short totalTrabajadores;
//    private String presidente;
//    private String dniPresidente;
//    private String secretario;
//    private String dniSecretario;
//    private String representantes;
//    private String dniRepresentante;
//
//    public Modelo_7_3_Acta_Global() {
//    }
//
//    public Modelo_7_3_Acta_Global(String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro, String direccion,
//                                  String municipio, String codigoPostal, String comarca, String provincia, String numeroISS) throws CumplimentarPDFException {
//        super(nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, codigoPostal, comarca, provincia, numeroISS);
//    }
//
//    public Modelo_7_3_Acta_Global(String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro, String direccion,
//                                  String municipio, String codigoPostal, String comarca, String provincia, String numeroISS,
//                                  boolean casillaVerificacion2, String nombreConvenio, String numeroConvenio, int trabajadoresFijos,
//                                  int trabajadoresEventuales, int trabajadoresJornadas, int trabajadoresEventualesComputo, short totalTrabajadores) throws CumplimentarPDFException {
//        super(nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, codigoPostal, comarca, provincia, numeroISS);
//        setCasillaVerificacion2(casillaVerificacion2);
//        this.nombreConvenio = nombreConvenio;
//        setNumeroConvenio(numeroConvenio);
//        setTrabajadoresFijos(trabajadoresFijos);
//        this.trabajadoresEventuales = trabajadoresEventuales;
//        this.trabajadoresJornadas = trabajadoresJornadas;
//        this.trabajadoresEventualesComputo = trabajadoresEventualesComputo;
//        setTotalTrabajadores(totalTrabajadores);
//    }
//
//    public Modelo_7_3_Acta_Global(String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro, String direccion,
//                                  String municipio, String codigoPostal, String comarca, String provincia, String numeroISS,
//                                  String actvEcono, String actvEcono1, String direccionCentro, String direccionCentro1,
//                                  String telefono, String actvEcono2, String actvEcono3, boolean casillaVerificacion2,
//                                  String nombreConvenio, String numeroConvenio, int trabajadoresFijos, int trabajadoresEventuales,
//                                  int trabajadoresJornadas, int trabajadoresEventualesComputo, short totalTrabajadores,
//                                  String presidente, String dniPresidente, String secretario, String dniSecretario,
//                                  String representantes, String dniRepresentante) throws CumplimentarPDFException {
//        super(nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, codigoPostal, comarca, provincia, numeroISS);
//        this.actvEcono = actvEcono;
//        this.actvEcono1 = actvEcono1;
//        this.direccionCentro = direccionCentro;
//        this.direccionCentro1 = direccionCentro1;
//        this.telefono = telefono;
//        this.actvEcono2 = actvEcono2;
//        this.actvEcono3 = actvEcono3;
//        setCasillaVerificacion2(casillaVerificacion2);
//        this.nombreConvenio = nombreConvenio;
//        setNumeroConvenio(numeroConvenio);
//        setTrabajadoresFijos(trabajadoresFijos);
//        this.trabajadoresEventuales = trabajadoresEventuales;
//        this.trabajadoresJornadas = trabajadoresJornadas;
//        this.trabajadoresEventualesComputo = trabajadoresEventualesComputo;
//        setTotalTrabajadores(totalTrabajadores);
//        this.presidente = presidente;
//        setDniPresidente(dniPresidente);
//        this.secretario = secretario;
//        setDniSecretario(dniSecretario);
//        this.representantes = representantes;
//        setDniRepresentante(dniRepresentante);
//    }
//
//    public String getActvEcono() {
//        return actvEcono;
//    }
//
//    public void setActvEcono(String actvEcono) {
//        this.actvEcono = actvEcono;
//    }
//
//    public String getActvEcono1() {
//        return actvEcono1;
//    }
//
//    public void setActvEcono1(String actvEcono1) {
//        this.actvEcono1 = actvEcono1;
//    }
//
//    public String getDireccionCentro() {
//        return direccionCentro;
//    }
//
//    public void setDireccionCentro(String direccionCentro) {
//
//        if (direccionCentro.length() >= 35) {
//            int indiceUltimaComa = direccionCentro.substring(0, 35).lastIndexOf(',');
//
//            if (indiceUltimaComa >= 0) {
//                this.direccionCentro = direccionCentro.substring(0, indiceUltimaComa);
//                this.direccionCentro1 = direccionCentro.substring(indiceUltimaComa + 2);
//            } else {
//                this.direccionCentro = direccionCentro.substring(0, 35);
//                this.direccionCentro1 = "";
//            }
//        } else {
//            this.direccionCentro = direccionCentro;
//            this.direccionCentro1 = "";
//        }
//    }
//
//    public String getDireccionCentro1() {
//        return direccionCentro1;
//    }
//
//    public void setDireccionCentro1(String direccionCentro1) {
//        this.direccionCentro1 = direccionCentro1;
//    }
//
//    public String getTelefono() {
//        return telefono;
//    }
//
//    public void setTelefono(String telefono) {
//        this.telefono = telefono;
//    }
//
//    public String getActvEcono2() {
//        return actvEcono2;
//    }
//
//    public void setActvEcono2(String actvEcono2) {
//        this.actvEcono2 = actvEcono2;
//    }
//
//    public String getActvEcono3() {
//        return actvEcono3;
//    }
//
//    public void setActvEcono3(String actvEcono3) {
//        this.actvEcono3 = actvEcono3;
//    }
//
//    public boolean isCasillaVerificacion2() {
//        return casillaVerificacion2;
//    }
//
//    public void setCasillaVerificacion2(boolean casillaVerificacion2) {
//        this.casillaVerificacion2 = true;
//    }
//
//    public String getNombreConvenio() {
//        return nombreConvenio;
//    }
//
//    public void setNombreConvenio(String nombreConvenio) {
//        this.nombreConvenio = nombreConvenio;
//    }
//
//    public String getNumeroConvenio() {
//        return numeroConvenio;
//    }
//
//    public void setNumeroConvenio(String numeroConvenio) throws CumplimentarPDFException {
//        String expresionRegular = "^\\d{14}$";
//
//        // Compila la expresión regular
//        Pattern patron = Pattern.compile(expresionRegular);
//
//        if (!patron.matcher(numeroConvenio).matches()) {
//            throw new CumplimentarPDFException("ERROR, el número de convenio no es válido ya que no contiene 14 dígitos");
//        }
//        this.numeroConvenio = numeroConvenio;
//    }
//
//    public int getTrabajadoresFijos() {
//        return trabajadoresFijos;
//    }
//
//    public void setTrabajadoresFijos(int trabajadoresFijos) throws CumplimentarPDFException {
//
//        if (trabajadoresFijos < Constantes.MAXIMO_ELECTORES_DELEGADOS) {
//            throw new CumplimentarPDFException("ERROR, número de trabajadores fijos incorrecto o documentacion erronea\n");
//        }
//        this.trabajadoresFijos = trabajadoresFijos;
//    }
//
//    public int getTrabajadoresEventuales() {
//        return trabajadoresEventuales;
//    }
//
//    public void setTrabajadoresEventuales(int trabajadoresEventuales) {
//        this.trabajadoresEventuales = trabajadoresEventuales;
//    }
//
//    public int getTrabajadoresJornadas() {
//        return trabajadoresJornadas;
//    }
//
//    public void setTrabajadoresJornadas(int trabajadoresJornadas) {
//        this.trabajadoresJornadas = trabajadoresJornadas;
//    }
//
//    public int getTrabajadoresEventualesComputo() {
//        return trabajadoresEventualesComputo;
//    }
//
//    public void setTrabajadoresEventualesComputo(int trabajadoresEventualesComputo) {
//
////        if (trabajadoresJornadas / 200 == 0){
////            this.trabajadoresEventualesComputo = 1;
////        }
////        else{
//        this.trabajadoresEventualesComputo = trabajadoresEventualesComputo;
////        }
//    }
//
//    public short getTotalTrabajadores() {
//        return totalTrabajadores;
//    }
//
//    public void setTotalTrabajadores(short totalTrabajadores) throws CumplimentarPDFException {
//
//        if (trabajadoresFijos + trabajadoresEventualesComputo <= 50) {
//            throw new CumplimentarPDFException("ERROR, número de trabajadores fijos incorrecto o documentacion erronea\n");
//        }
//        this.totalTrabajadores = totalTrabajadores;
//    }
//
//    public String getPresidente() {
//        return presidente;
//    }
//
//    public void setPresidente(String presidente) {
//        this.presidente = presidente;
//    }
//
//    public String getDniPresidente() {
//        return dniPresidente;
//    }
//
//    public void setDniPresidente(String dniPresidente) throws CumplimentarPDFException {
//
//        if (!validarDNI.esDNIValido(dniPresidente)) {
//            throw new CumplimentarPDFException("ERROR, DNI introducido incorrecto");
//        }
//        this.dniPresidente = dniPresidente;
//    }
//
//    public String getSecretario() {
//        return secretario;
//    }
//
//    public void setSecretario(String secretario) {
//        this.secretario = secretario;
//    }
//
//    public String getDniSecretario() {
//        return dniSecretario;
//    }
//
//    public void setDniSecretario(String dniSecretario) throws CumplimentarPDFException {
//
//        if (!validarDNI.esDNIValido(dniSecretario)) {
//            throw new CumplimentarPDFException("ERROR, DNI introducido incorrecto");
//        }
//        this.dniSecretario = dniSecretario;
//    }
//
//    public String getRepresentantes() {
//        return representantes;
//    }
//
//    public void setRepresentantes(String representantes) {
//        this.representantes = representantes;
//    }
//
//    public String getDniRepresentante() {
//        return dniRepresentante;
//    }
//
//    public void setDniRepresentante(String dniRepresentante) throws CumplimentarPDFException {
//
//        if (!validarDNI.esDNIValido(dniRepresentante)) {
//            throw new CumplimentarPDFException("ERROR, DNI introducido incorrecto");
//        }
//        this.dniRepresentante = dniRepresentante;
//    }
//}
//
