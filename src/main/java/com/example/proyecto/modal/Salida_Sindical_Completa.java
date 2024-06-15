//package com.example.proyecto.modal;
//
//import com.example.proyecto.util.CumplimentarPDFException;
//
//public class Salida_Sindical_Completa {
//
//    String fechaComunicacion;
//    String empresa;
//    String nombreCompletoDelegado;
//    String fechaSalida;
//    String diaComunicacion, mesComunicacion, anioComunicacion;
//    String diaSalida, mesSalida, anioSalida;
//    ValidadorFecha validarFecha = new ValidadorFecha();
//    ConversorFechaToLetras convertirFechas = new ConversorFechaToLetras();
//
//
//    public Salida_Sindical_Completa(){
//    }
//    public Salida_Sindical_Completa(String fechaComunicacion, String empresa, String nombreCompletoDelegado, String fechaSalida) throws CumplimentarPDFException {
//        setFechaComunicacion(fechaComunicacion);
//        this.empresa = empresa;
//        this.nombreCompletoDelegado = nombreCompletoDelegado;
//        setFechaSalida(fechaSalida);
//    }
//
//    public String getFechaComunicacion() {
//        return fechaComunicacion;
//    }
//
//    public void setFechaComunicacion(String fechaComunicacion) throws CumplimentarPDFException {
//
//        if (!validarFecha.esFormatoFechaValido(fechaComunicacion)){
//            throw new CumplimentarPDFException("El formato de la fecha de constitución es incorrecto. -->".concat(Constantes.FORMATO_FECHA.concat("\n")));
//        }
//        this.fechaComunicacion = fechaComunicacion;
//
//        setDiaComunicacion(fechaComunicacion);
//        setMesComunicacion(fechaComunicacion);
//        setAnioComunicacion(fechaComunicacion);
//    }
//
//    public String getEmpresa() {
//        return empresa;
//    }
//
//    public void setEmpresa(String empresa) {
//        this.empresa = empresa;
//    }
//
//    public String getNombreCompletoDelegado() {
//        return nombreCompletoDelegado;
//    }
//
//    public void setNombreCompletoDelegado(String nombreCompletoDelegado) {
//        this.nombreCompletoDelegado = nombreCompletoDelegado;
//    }
//
//    public String getFechaSalida() {
//        return fechaSalida;
//    }
//
//    public void setFechaSalida(String fechaSalida) throws CumplimentarPDFException {
//
//        if (!validarFecha.esFormatoFechaValido(fechaSalida)){
//            throw new CumplimentarPDFException("El formato de la fecha de constitución es incorrecto. -->".concat(Constantes.FORMATO_FECHA.concat("\n")));
//        }
//        this.fechaSalida = fechaSalida;
//
//        setDiaSalida(fechaSalida);
//        setMesSalida(fechaSalida);
//        setAnioSalida(fechaSalida);
//    }
//
//    public String getDiaComunicacion() {
//        return diaComunicacion;
//    }
//
//    public void setDiaComunicacion(String diaComunicacion) {
//        String[] partes = diaComunicacion.split("/");
//
//        // Convertir el día y el mes a cadenas de texto
//        this.diaComunicacion =  String.valueOf(partes[0]);
//    }
//
//    public String getMesComunicacion() {
//        return mesComunicacion;
//    }
//
//    public void setMesComunicacion(String mesComunicacion) {
//        String[] partes = mesComunicacion.split("/");
//        String mes = partes[1];
//
//        // Elimina los 0 para evitar errores
//        if (mes.startsWith("0")){
//            mes = mes.replace("0", "");
//        }
//        this.mesComunicacion = convertirFechas.convertirMesLetras(mes);
//    }
//
//    public String getAnioComunicacion() {
//        return anioComunicacion;
//    }
//
//    public void setAnioComunicacion(String anioComunicacion) {
//        String[] partes = anioComunicacion.split("/");
//
//        // Convertir el día y el mes a cadenas de texto
//        this.anioComunicacion =  String.valueOf(partes[2]);
//    }
//
//    public String getDiaSalida() {
//        return diaSalida;
//    }
//
//    public void setDiaSalida(String diaSalida) {
//
//        String[] partes = diaSalida.split("/");
//
//        // Convertir el día y el mes a cadenas de texto
//        this.diaSalida =  String.valueOf(partes[0]);
//    }
//
//    public String getMesSalida() {
//        return mesSalida;
//    }
//
//    public void setMesSalida(String mesSalida) {
//        String[] partes = mesSalida.split("/");
//        String mes = partes[1];
//
//        // Elimina los 0 para evitar errores
//        if (mes.startsWith("0")){
//            mes = mes.replace("0", "");
//        }
//        this.mesSalida = convertirFechas.convertirMesLetras(mes);
//    }
//
//    public String getAnioSalida() {
//        return anioSalida;
//    }
//
//    public void setAnioSalida(String anioSalida) {
//        String[] partes = anioSalida.split("/");
//
//        // Convertir el día y el mes a cadenas de texto
//        this.anioSalida =  String.valueOf(partes[2]);
//    }
//
//
//    @Override
//    public String toString() {
//        return "com.example.proyecto.modal.Salida_Sindical_Completa{" +
//                "fechaComunicacion='" + fechaComunicacion + '\'' +
//                ", empresa='" + empresa + '\'' +
//                ", nombreCompletoDelegado='" + nombreCompletoDelegado + '\'' +
//                ", fechaSalida='" + fechaSalida + '\'' +
//                '}';
//    }
//}
