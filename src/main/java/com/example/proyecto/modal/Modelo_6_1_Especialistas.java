//package com.example.proyecto.modal;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class Modelo_6_1_Especialistas extends Modelo_4_Especialistas{
//
//    private int representantesELegibles;
//    private String fechaConstitucionLetras;
//    private Date fechaVotacion;
//    private String fechaVotacionLetras;
//    private int totalVotantes;
//    private int varonesVotantes;
//    private int mujeresVotantes;
//    private int papletasCumplimentadas;
//    private int papeletasBlancas;
//    private int nulos;
//    private int totalPapeletas;
//
//    public Modelo_6_1_Especialistas() {
//    }
//
//    public Modelo_6_1_Especialistas(String colegio, String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro,
//                                    String direccion, String municipio, String provincia, String promotores, String fechaConstitucion, String fechaConstitucionLetras,
//                                    String municipioElecciones, String dia, String mes, String anioFormateado) throws CumplimentarPDFException {
//        super(colegio, nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores, fechaConstitucion, municipioElecciones, dia, mes, anioFormateado);
//        setFechaConstitucion(fechaConstitucion);
//    }
//
//    //Constructor Modelo 7.2
//    public Modelo_6_1_Especialistas(String colegio, String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro,
//                               String direccion, String municipio, String provincia, String promotores, String fechaConstitucion, Date fechaVotacion,
//                               String municipioElecciones, String dia, String mes, String anioFormateado) throws CumplimentarPDFException {
//        super(colegio, nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores, fechaConstitucion, municipioElecciones, dia, mes, anioFormateado);
//        setFechaVotacion(fechaVotacion);
//    }
//
//    public Modelo_6_1_Especialistas(String numeroMesa, String colegio, String nombreEmpresa, String CIF, String nombreComercial,
//                                    String nombreCentro, String direccion, String municipio, String provincia, String promotores,
//                                    String fechaConstitucion, String municipioElecciones, String horas, String dia, String mes,
//                                    String anioFormateado, String presidente, String vocal, String secretario, String dniPresidente,
//                                    String dniVocal, String dniSecretario) throws CumplimentarPDFException {
//        super(numeroMesa, colegio, nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores,
//                fechaConstitucion, municipioElecciones, horas, dia, mes, anioFormateado, presidente, vocal, secretario, dniPresidente, dniVocal, dniSecretario);
//    }
//
//    //Constructor Modelo 7.2 Completo
//    public Modelo_6_1_Especialistas(String numeroMesa, String colegio, String nombreEmpresa, String CIF, String nombreComercial,
//                               String nombreCentro, String direccion, String municipio, String provincia, String promotores,
//                                    String fechaConstitucion, Date fechaVotacion, String municipioElecciones, String horas, String dia, String mes,
//                               String anioFormateado, String presidente, String vocal, String secretario, String dniPresidente,
//                               String dniVocal, String dniSecretario) throws CumplimentarPDFException {
//        super(numeroMesa, colegio, nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores,
//                fechaConstitucion, municipioElecciones, horas, dia, mes, anioFormateado, presidente, vocal, secretario, dniPresidente, dniVocal, dniSecretario);
//        setFechaVotacion(fechaVotacion);
//        setFechaConstitucionLetras(fechaConstitucionLetras);
//        setFechaVotacionLetras(fechaVotacionLetras);
//    }
//
//    public Modelo_6_1_Especialistas(String numeroMesa, String colegio, String nombreEmpresa, String CIF, String nombreComercial,
//                                    String nombreCentro, String direccion, String municipio, String provincia, int totalElectores,
//                                    int varonesElectores, int mujeresElectoras, String promotores, String fechaConstitucion,
//                                    String municipioElecciones, String horas, String dia, String mes, String anioFormateado,
//                                    String presidente, String vocal, String secretario, String dniPresidente, String dniVocal,
//                                    String dniSecretario, int representantesELegibles, String fechaConstitucionLetras, Date fechaVotacion,
//                                    String fechaVotacionLetras, int totalVotantes, int varonesVotantes, int mujeresVotantes,
//                                    int papletasCumplimentadas, int papeletasBlancas, int nulos, int totalPapeletas) throws CumplimentarPDFException {
//        super(numeroMesa, colegio, nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, totalElectores,
//                varonesElectores, mujeresElectoras, promotores, fechaConstitucion, municipioElecciones, horas, dia, mes, anioFormateado,
//                presidente, vocal, secretario, dniPresidente, dniVocal, dniSecretario);
//        setRepresentantesELegibles(representantesELegibles);
//        setFechaConstitucionLetras(fechaConstitucionLetras);
//        setFechaVotacion(fechaVotacion);
//        setFechaVotacionLetras(fechaVotacionLetras);
//        setTotalVotantes(totalVotantes);
//        setVaronesVotantes(varonesVotantes);
//        setMujeresVotantes(mujeresVotantes);
//        setPapletasCumplimentadas(papletasCumplimentadas);
//        setPapeletasBlancas(papeletasBlancas);
//        setNulos(nulos);
//        setTotalPapeletas(totalPapeletas);
//    }
//
//    public int getRepresentantesELegibles() {
//        return representantesELegibles;
//    }
//
//    public void setRepresentantesELegibles(int representantesELegibles) {
//
//        if (getTotalElectores() < 101) {
//            this.representantesELegibles = 5;
//        } else {
//            if (getTotalElectores() < 251) {
//                this.representantesELegibles = 9;
//            } else {
//                if (getTotalElectores() < 501) {
//                    this.representantesELegibles = 13;
//                } else {
//                    if (getTotalElectores() < 751) {
//                        this.representantesELegibles = 17;
//                    } else {
//                        if (getTotalElectores() < 1001) {
//                            this.representantesELegibles = 21;
//                        } else {
//                            // Cálculo para los siguientes bloques de 1000 electores
//                            int bloquesAdicionales = (getTotalElectores() - 1000) / 1000;
//                            this.representantesELegibles = (short) Math.min(75, 21 + bloquesAdicionales * 2);
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    public String getFechaConstitucionLetras() {
//        return fechaConstitucionLetras;
//    }
//
//    @Override
//    public void setFechaConstitucionLetras(String fechaConstitucion) {
//        String fechaConFormato = fechaConstitucion;
//        String[] partes = fechaConstitucion.split("/");
//
//        // Convertir el día y el mes a cadenas de texto
//        String dia = String.valueOf(partes[0]);
//        String mes = partes[1];
//
//        // Agregar un guión "-" entre el día y el mes
//        dia = convertirFechas.convertirDiaLetras(dia) + " de ";
//
//        // Elimina los 0 para evitar errores
//        if (mes.startsWith("0")){
//            mes = mes.replace("0", "");
//        }
//        // Agregar un espacio entre el mes y el año
//        mes = convertirFechas.convertirMesLetras(mes);
//
//        // Agregar el año a la cadena de texto
//        fechaConstitucion = dia + mes + convertirFechas.convertirAnioLetras(partes[2]);
//
//        // Establecer la fecha en el atributo
//        this.fechaConstitucionLetras = fechaConstitucion.concat("  (".concat(fechaConFormato).concat(")")).toUpperCase();
//    }
//
//    public Date getFechaVotacion() {
//        return fechaVotacion;
//    }
//
//    public void setFechaVotacion(Date fechaVotacion) {
//
//        if (fechaVotacion == null) {
//            this.fechaVotacion = null;
//            return;
//        }
//
//        SimpleDateFormat formatoFecha = new SimpleDateFormat(Constantes.FORMATO_FECHA);
//
//        try {
//            this.fechaVotacion = formatoFecha.parse(fechaVotacion.toString());
//        } catch (ParseException e) {
//            throw new IllegalArgumentException("El formato de la fecha de constitución es incorrecto. -->".concat(Constantes.FORMATO_FECHA.concat("\n")));
//        }
//        this.fechaVotacion = fechaVotacion;
//    }
//
//    public String getFechaVotacionLetras() {
//        return fechaVotacionLetras;
//    }
//
//    public void setFechaVotacionLetras(String fechaVotacionLetras) {
//
//        StringBuilder sbFechaEscrutinioLetras = new StringBuilder();
//        StringBuilder sbAnioFormateado = new StringBuilder();
//        for (Dias day : Dias.values()) {
//            if (day.getNumero().equalsIgnoreCase(validarFecha.getDia())){
//                sbFechaEscrutinioLetras.append(day.getNombre()).append(" de ");
//            }
//            if (day.getNumero().equalsIgnoreCase(validarFecha.getAnioFormateado())){
//                sbAnioFormateado.append(day.getNombre());
//            }
//        }
//
//        for (Meses month: Meses.values()) {
//            if (month.obtenerNombre().equalsIgnoreCase(validarFecha.getMes())){
//                sbFechaEscrutinioLetras.append(month).append(" de dos mil ").append(sbAnioFormateado).append("     (");
//            }
//        }
//
//
//        this.fechaVotacionLetras = sbFechaEscrutinioLetras.append(fechaVotacion).append(")").toString().toUpperCase();
//    }
//
//    public int getTotalVotantes() {
//        return totalVotantes;
//    }
//
//    public void setTotalVotantes(int totalVotantes) throws CumplimentarPDFException {
//
//        if (totalVotantes > getTotalElectores()){
//            throw new CumplimentarPDFException("ERROR, el total de votantes no puede ser superior al total de electores -->" + getTotalElectores() + "\n");
//        }
//        this.totalVotantes = totalVotantes;
//    }
//
//    public int getVaronesVotantes() {
//        return varonesVotantes;
//    }
//
//    public void setVaronesVotantes(int varonesVotantes) throws CumplimentarPDFException {
//
//        if (varonesVotantes > totalVotantes){
//            throw new CumplimentarPDFException("ERROR, el total de varones votantes no puede ser mayor al total de votantes\n");
//        }
//        this.varonesVotantes = varonesVotantes;
//    }
//
//    public int getMujeresVotantes() {
//        return mujeresVotantes;
//    }
//
//    public void setMujeresVotantes(int mujeresVotantes) {
//        this.mujeresVotantes = (totalVotantes - varonesVotantes);
//    }
//
//    public int getPapletasCumplimentadas() {
//        return papletasCumplimentadas;
//    }
//
//    public void setPapletasCumplimentadas(int papletasCumplimentadas) throws CumplimentarPDFException {
//
//        if (papletasCumplimentadas > totalVotantes){
//            throw new CumplimentarPDFException("ERROR, el total de papeletas cumplimentadas no puede ser mayor al total de votantes\n");
//        }
//        this.papletasCumplimentadas = papletasCumplimentadas;
//    }
//
//    public int getPapeletasBlancas() {
//        return papeletasBlancas;
//    }
//
//    public void setPapeletasBlancas(int papeletasBlancas) throws CumplimentarPDFException {
//
//        if (papeletasBlancas > totalVotantes){
//            throw new CumplimentarPDFException("ERROR, el total de papeletas blancas no puede ser mayor al total de votantes\n");
//        }
//        this.papeletasBlancas = papeletasBlancas;
//    }
//
//    public int getNulos() {
//        return nulos;
//    }
//
//    public void setNulos(int nulos) throws CumplimentarPDFException {
//
//        if (nulos > totalVotantes){
//            throw new CumplimentarPDFException("ERROR, el total de papeletas nulas no puede ser mayor al total de votantes\n");
//        }
//        this.nulos = nulos;
//    }
//
//    public int getTotalPapeletas() {
//        return totalPapeletas;
//    }
//
//    public void setTotalPapeletas(int totalPapeletas) throws CumplimentarPDFException {
//        if (totalPapeletas != getTotalVotantes()){
//            throw new CumplimentarPDFException("ERROR, el total de papeletas leidas debe ser igual al total de votantes\n");
//        }
//        this.totalPapeletas = (papletasCumplimentadas + papletasCumplimentadas + nulos);
//    }
//}
