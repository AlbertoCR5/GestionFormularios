//package com.example.proyecto.modal;
//
//import com.example.proyecto.util.CumplimentarPDFException;
//
//import java.util.Objects;
//
//public class Representantes_Elegidos_Comite {
//
//    private String altaBaja;
//    private String iniciales;
//    private String apellidos;
//    private String dni;
//    private short antiguedad;
//    private String sexo;
//    private String fechaNacimiento;
//    private String colegio;
//    private String sindicato;
//
//    public Representantes_Elegidos_Comite(String iniciales, String apellidos, String dni, short antiguedad, String sexo, String fechaNacimiento, String colegio, String sindicato) {
//        this.iniciales = iniciales;
//        this.apellidos = apellidos;
//        this.dni = dni;
//        this.antiguedad = antiguedad;
//        this.sexo = sexo;
//        this.fechaNacimiento = fechaNacimiento;
//        this.colegio = colegio;
//        this.sindicato = sindicato;
//    }
//
//    public Representantes_Elegidos_Comite(String altaBaja, String iniciales, String apellidos, String dni, short antiguedad,
//                                          String sexo, String fechaNacimiento, String colegio, String sindicato) {
//        this.altaBaja = altaBaja;
//        this.iniciales = iniciales;
//        this.apellidos = apellidos;
//        this.dni = dni;
//        this.antiguedad = antiguedad;
//        this.sexo = sexo;
//        this.fechaNacimiento = fechaNacimiento;
//        this.colegio = colegio;
//        this.sindicato = sindicato;
//    }
//
//    public String getAltaBaja() {
//        return altaBaja;
//    }
//
//    public void setAltaBaja(String altaBaja) {
//        this.altaBaja = altaBaja;
//    }
//
//    public String getIniciales() {
//        return iniciales;
//    }
//
//    public void setIniciales(String iniciales) throws CumplimentarPDFException {
//
//        if (iniciales.isEmpty()){
//            throw new CumplimentarPDFException("ERROR, este campo no puede estar vacío\n");
//        }
//
//        if (iniciales.length() > 2){
//            throw new CumplimentarPDFException("ERROR, debes indicar un máximos de dos iniciales\n");
//        }
//        this.iniciales = iniciales;
//    }
//
//    public String getApellidos() {
//        return apellidos;
//    }
//
//    public void setApellidos(String apellidos) {
//        this.apellidos = apellidos;
//    }
//
//    public String getDni() {
//        return dni;
//    }
//
//    public void setDni(String dni) throws CumplimentarPDFException {
//        ValidadorDNI validarDNI = new ValidadorDNI();
//
//        if (!validarDNI.esDNIValido(dni)){
//            throw new CumplimentarPDFException("ERROR, DNI introducido incorrecto\n");
//        }
//        this.dni = dni;
//    }
//
//    public short getAntiguedad() {
//        return antiguedad;
//    }
//
//    public void setAntiguedad(short antiguedad) throws CumplimentarPDFException {
//
//        if (antiguedad < 3 || antiguedad > 564){
//            throw new CumplimentarPDFException("ERROR, antigüedad erronea\n");
//        }
//        this.antiguedad = antiguedad;
//    }
//
//    public String getSexo() {
//        return sexo;
//    }
//
//    public void setSexo(String sexo) {
//        this.sexo = sexo;
//    }
//
//    public String getFechaNacimiento() {
//        return fechaNacimiento;
//    }
//
//    public void setFechaNacimiento(String fechaNacimiento) throws CumplimentarPDFException {
//        ValidadorFecha validarFecha = new ValidadorFecha();
//
//        if ((validarFecha.esFormatoFechaValido(String.valueOf(fechaNacimiento)))){
//            throw new CumplimentarPDFException("ERROR, formato de fecha incorrecto -->" + Constantes.FORMATO_FECHA + "\n");
//        }
//        this.fechaNacimiento = fechaNacimiento;
//    }
//
//    public String getColegio() {
//        return colegio;
//    }
//
//    public void setColegio(String colegio) {
//        this.colegio = colegio;
//    }
//
//    public String getSindicato() {
//        return sindicato;
//    }
//
//    public void setSindicato(String sindicato) {
//        this.sindicato = sindicato;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Representantes_Elegidos_Comite that = (Representantes_Elegidos_Comite) o;
//        return Objects.equals(dni, that.dni);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(dni);
//
//    }
//
//    @Override
//    public String toString() {
//        return "com.example.proyecto.modal.Representantes_Elegidos_Comite{" +
//                "altaBaja=" + altaBaja +
//                ", iniciales='" + iniciales + '\'' +
//                ", apellidos='" + apellidos + '\'' +
//                ", dni='" + dni + '\'' +
//                ", antiguedad=" + antiguedad +
//                ", sexo=" + sexo +
//                ", fechaNacimiento=" + fechaNacimiento +
//                ", colegio=" + colegio +
//                ", sindicato='" + sindicato + '\'' +
//                '}';
//    }
//}
