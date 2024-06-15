//package com.example.proyecto.modal;
//
//import java.util.Date;
//
//public class Calendario_Comite extends Preaviso{
//
//    private Date horaConstitucion;
//    private String diaInicioExposicionCenso;
//    private String mesInicioExposicionCenso;
//    private String diaFinExposicionCenso;
//    private String mesFinExposicionCenso;
//    private String anioFinExposicionCenso;
//    private String diaReclamacionCenso;
//    private String mesReclamacionCenso;
//    private String anioReclamacionCenso;
//    private String diaResolucionCenso;
//    private String mesResolucionCenso;
//    private String anioResolucionCenso;
//    private String diaExposicionCensoDefinitivo;
//    private String mesExposicionCensoDefinitivo;
//    private String anioExposicionCensoDefinitivo;
//    private String diaInicioPresentacionCandidaturas;
//    private String mesInicioPresentacionCandidaturas;
//    private String diaFinPresentacionCandidaturas;
//    private String mesFinPresentacionCandidaturas;
//    private String anioFinPresentacionCandidaturas;
//    private String diaInicioExposicionCandidaturas;
//    private String mesInicioExposicionCandidaturas;
//    private String diaFinExposicionCandidaturas;
//    private String mesFInExposicionCandidaturas;
//    private String anioFInExposicionCandidaturas;
//    private String diaReclamacionCandidaturas;
//    private String mesReclamacionCandidaturas;
//    private String anioReclamacionCandidaturas;
//    private String diaProclamacionCandidaturas;
//    private String mesProclamacionCandidaturas;
//    private String anioProclamacionCandidaturas;
//    private String diaInicioPropaganda;
//    private String mesInicioPropaganda;
//    private String diaFinPropaganda;
//    private String anioFinPropaganda;
//    private String mesFinPropaganda;
//    private String diaReflexion;
//    private String mesReflexion;
//    private String anioReflexion;
//    private String diaVotacion;
//    private String mesVotacion;
//    private String anioVotacion;
//    private String nombreEmpresa;
//
//    public Calendario_Comite() {
//    }
//
//    public Calendario_Comite(String nombreEmpresa, String fechaConstitucion) throws CumplimentarPDFException {
//        super(nombreEmpresa, fechaConstitucion);
//    }
//
//    public Calendario_Comite(String nombreEmpresa, String fechaConstitucion, String diaInicioExposicionCenso, String mesInicioExposicionCenso,
//                             String diaFinExposicionCenso, String mesFinExposicionCenso, String anioFinExposicionCenso, String diaReclamacionCenso,
//                             String mesReclamacionCenso, String anioReclamacionCenso, String diaResolucionCenso, String mesResolucionCenso,
//                             String anioResolucionCenso, String diaExposicionCensoDefinitivo, String mesExposicionCensoDefinitivo, String anioExposicionCensoDefinitivo,
//                             String diaInicioPresentacionCandidaturas, String mesInicioPresentacionCandidaturas, String diaFinPresentacionCandidaturas,
//                             String mesFinPresentacionCandidaturas, String anioFinPresentacionCandidaturas, String diaInicioExposicionCandidaturas,
//                             String mesInicioExposicionCandidaturas, String diaFinExposicionCandidaturas, String mesFInExposicionCandidaturas,
//                             String anioFInExposicionCandidaturas, String diaReclamacionCandidaturas, String mesReclamacionCandidaturas, String anioReclamacionCandidaturas,
//                             String diaProclamacionCandidaturas, String mesProclamacionCandidaturas, String anioProclamacionCandidaturas, String diaInicioPropaganda,
//                             String mesInicioPropaganda, String diaFinPropaganda, String anioFinPropaganda, String mesFinPropaganda, String diaReflexion,
//                             String mesReflexion, String anioReflexion, String diaVotacion, String mesVotacion, String anioVotacion) throws CumplimentarPDFException {
//        super(nombreEmpresa, fechaConstitucion);
//        this.diaInicioExposicionCenso = diaInicioExposicionCenso;
//        this.mesInicioExposicionCenso = mesInicioExposicionCenso;
//        this.diaFinExposicionCenso = diaFinExposicionCenso;
//        this.mesFinExposicionCenso = mesFinExposicionCenso;
//        this.anioFinExposicionCenso = anioFinExposicionCenso;
//        this.diaReclamacionCenso = diaReclamacionCenso;
//        this.mesReclamacionCenso = mesReclamacionCenso;
//        this.anioReclamacionCenso = anioReclamacionCenso;
//        this.diaResolucionCenso = diaResolucionCenso;
//        this.mesResolucionCenso = mesResolucionCenso;
//        this.anioResolucionCenso = anioResolucionCenso;
//        this.diaExposicionCensoDefinitivo = diaExposicionCensoDefinitivo;
//        this.mesExposicionCensoDefinitivo = mesExposicionCensoDefinitivo;
//        this.anioExposicionCensoDefinitivo = anioExposicionCensoDefinitivo;
//        this.diaInicioPresentacionCandidaturas = diaInicioPresentacionCandidaturas;
//        this.mesInicioPresentacionCandidaturas = mesInicioPresentacionCandidaturas;
//        this.diaFinPresentacionCandidaturas = diaFinPresentacionCandidaturas;
//        this.mesFinPresentacionCandidaturas = mesFinPresentacionCandidaturas;
//        this.anioFinPresentacionCandidaturas = anioFinPresentacionCandidaturas;
//        this.diaInicioExposicionCandidaturas = diaInicioExposicionCandidaturas;
//        this.mesInicioExposicionCandidaturas = mesInicioExposicionCandidaturas;
//        this.diaFinExposicionCandidaturas = diaFinExposicionCandidaturas;
//        this.mesFInExposicionCandidaturas = mesFInExposicionCandidaturas;
//        this.anioFInExposicionCandidaturas = anioFInExposicionCandidaturas;
//        this.diaReclamacionCandidaturas = diaReclamacionCandidaturas;
//        this.mesReclamacionCandidaturas = mesReclamacionCandidaturas;
//        this.anioReclamacionCandidaturas = anioReclamacionCandidaturas;
//        this.diaProclamacionCandidaturas = diaProclamacionCandidaturas;
//        this.mesProclamacionCandidaturas = mesProclamacionCandidaturas;
//        this.anioProclamacionCandidaturas = anioProclamacionCandidaturas;
//        this.diaInicioPropaganda = diaInicioPropaganda;
//        this.mesInicioPropaganda = mesInicioPropaganda;
//        this.diaFinPropaganda = diaFinPropaganda;
//        this.anioFinPropaganda = anioFinPropaganda;
//        this.mesFinPropaganda = mesFinPropaganda;
//        this.diaReflexion = diaReflexion;
//        this.mesReflexion = mesReflexion;
//        this.anioReflexion = anioReflexion;
//        this.diaVotacion = diaVotacion;
//        this.mesVotacion = mesVotacion;
//        this.anioVotacion = anioVotacion;
//    }
//
//    public Calendario_Comite(String nombreEmpresa, String fechaConstitucion, Date horaConstitucion, String diaInicioExposicionCenso,
//                             String mesInicioExposicionCenso, String diaFinExposicionCenso, String mesFinExposicionCenso,
//                             String anioFinExposicionCenso, String diaReclamacionCenso, String mesReclamacionCenso, String anioReclamacionCenso,
//                             String diaResolucionCenso, String mesResolucionCenso, String anioResolucionCenso, String diaExposicionCensoDefinitivo,
//                             String mesExposicionCensoDefinitivo, String anioExposicionCensoDefinitivo, String diaInicioPresentacionCandidaturas,
//                             String mesInicioPresentacionCandidaturas, String diaFinPresentacionCandidaturas, String mesFinPresentacionCandidaturas,
//                             String anioFinPresentacionCandidaturas, String diaInicioExposicionCandidaturas, String mesInicioExposicionCandidaturas,
//                             String diaFinExposicionCandidaturas, String mesFInExposicionCandidaturas, String anioFInExposicionCandidaturas,
//                             String diaReclamacionCandidaturas, String mesReclamacionCandidaturas, String anioReclamacionCandidaturas,
//                             String diaProclamacionCandidaturas, String mesProclamacionCandidaturas, String anioProclamacionCandidaturas,
//                             String diaInicioPropaganda, String mesInicioPropaganda, String diaFinPropaganda, String anioFinPropaganda,
//                             String mesFinPropaganda, String diaReflexion, String mesReflexion, String anioReflexion, String diaVotacion,
//                             String mesVotacion, String anioVotacion) throws CumplimentarPDFException {
//        super(nombreEmpresa, fechaConstitucion);
//        this.horaConstitucion = horaConstitucion;
//        this.diaInicioExposicionCenso = diaInicioExposicionCenso;
//        this.mesInicioExposicionCenso = mesInicioExposicionCenso;
//        this.diaFinExposicionCenso = diaFinExposicionCenso;
//        this.mesFinExposicionCenso = mesFinExposicionCenso;
//        this.anioFinExposicionCenso = anioFinExposicionCenso;
//        this.diaReclamacionCenso = diaReclamacionCenso;
//        this.mesReclamacionCenso = mesReclamacionCenso;
//        this.anioReclamacionCenso = anioReclamacionCenso;
//        this.diaResolucionCenso = diaResolucionCenso;
//        this.mesResolucionCenso = mesResolucionCenso;
//        this.anioResolucionCenso = anioResolucionCenso;
//        this.diaExposicionCensoDefinitivo = diaExposicionCensoDefinitivo;
//        this.mesExposicionCensoDefinitivo = mesExposicionCensoDefinitivo;
//        this.anioExposicionCensoDefinitivo = anioExposicionCensoDefinitivo;
//        this.diaInicioPresentacionCandidaturas = diaInicioPresentacionCandidaturas;
//        this.mesInicioPresentacionCandidaturas = mesInicioPresentacionCandidaturas;
//        this.diaFinPresentacionCandidaturas = diaFinPresentacionCandidaturas;
//        this.mesFinPresentacionCandidaturas = mesFinPresentacionCandidaturas;
//        this.anioFinPresentacionCandidaturas = anioFinPresentacionCandidaturas;
//        this.diaInicioExposicionCandidaturas = diaInicioExposicionCandidaturas;
//        this.mesInicioExposicionCandidaturas = mesInicioExposicionCandidaturas;
//        this.diaFinExposicionCandidaturas = diaFinExposicionCandidaturas;
//        this.mesFInExposicionCandidaturas = mesFInExposicionCandidaturas;
//        this.anioFInExposicionCandidaturas = anioFInExposicionCandidaturas;
//        this.diaReclamacionCandidaturas = diaReclamacionCandidaturas;
//        this.mesReclamacionCandidaturas = mesReclamacionCandidaturas;
//        this.anioReclamacionCandidaturas = anioReclamacionCandidaturas;
//        this.diaProclamacionCandidaturas = diaProclamacionCandidaturas;
//        this.mesProclamacionCandidaturas = mesProclamacionCandidaturas;
//        this.anioProclamacionCandidaturas = anioProclamacionCandidaturas;
//        this.diaInicioPropaganda = diaInicioPropaganda;
//        this.mesInicioPropaganda = mesInicioPropaganda;
//        this.diaFinPropaganda = diaFinPropaganda;
//        this.anioFinPropaganda = anioFinPropaganda;
//        this.mesFinPropaganda = mesFinPropaganda;
//        this.diaReflexion = diaReflexion;
//        this.mesReflexion = mesReflexion;
//        this.anioReflexion = anioReflexion;
//        this.diaVotacion = diaVotacion;
//        this.mesVotacion = mesVotacion;
//        this.anioVotacion = anioVotacion;
//    }
//
//    public Date getHoraConstitucion() {
//        return horaConstitucion;
//    }
//
//    public void setHoraConstitucion(Date horaConstitucion) throws CumplimentarPDFException {
//        ValidadorHora validarHora = new ValidadorHora();
//
//        if (!validarHora.validarHora(String.valueOf(horaConstitucion))){
//            throw new CumplimentarPDFException("ERROR, Formato de hora incorrecto -->" + Constantes.FORMATO_FECHA + "\n");
//        }
//        this.horaConstitucion = horaConstitucion;
//    }
//
//    public String getDiaInicioExposicionCenso() {
//        return diaInicioExposicionCenso;
//    }
//
//    public void setDiaInicioExposicionCenso(String diaInicioExposicionCenso) {
//        this.diaInicioExposicionCenso = comprobarDia(diaInicioExposicionCenso);
//    }
//
//    public String getMesInicioExposicionCenso() {
//        return mesInicioExposicionCenso;
//    }
//
//    public void setMesInicioExposicionCenso(String mesInicioExposicionCenso) {
//        this.mesInicioExposicionCenso = validarYObtenerMes(mesInicioExposicionCenso);
//    }
//
//    public String getDiaFinExposicionCenso() {
//        return diaFinExposicionCenso;
//    }
//
//    public void setDiaFinExposicionCenso(String diaFinExposicionCenso) {
//        this.diaFinExposicionCenso = comprobarDia(diaFinExposicionCenso);
//    }
//
//    public String getMesFinExposicionCenso() {
//        return mesFinExposicionCenso;
//    }
//
//    public void setMesFinExposicionCenso(String mesFinExposicionCenso) {
//        this.mesFinExposicionCenso = validarYObtenerMes(mesFinExposicionCenso);
//    }
//
//    public String getAnioFinExposicionCenso() {
//        return anioFinExposicionCenso;
//    }
//
//    public void setAnioFinExposicionCenso(String anioFinExposicionCenso) {
//        this.anioFinExposicionCenso = anioFinExposicionCenso.substring(anioFinExposicionCenso.length() - 2);
//    }
//
//    public String getDiaReclamacionCenso() {
//        return diaReclamacionCenso;
//    }
//
//    public void setDiaReclamacionCenso(String diaReclamacionCenso) {
//        this.diaReclamacionCenso = comprobarDia(diaReclamacionCenso);
//    }
//
//    public String getMesReclamacionCenso() {
//        return mesReclamacionCenso;
//    }
//
//    public void setMesReclamacionCenso(String mesReclamacionCenso) {
//        this.mesReclamacionCenso = validarYObtenerMes(mesReclamacionCenso);
//    }
//
//    public String getAnioReclamacionCenso() {
//        return anioReclamacionCenso;
//    }
//
//    public void setAnioReclamacionCenso(String anioReclamacionCenso) {
//        this.anioReclamacionCenso = anioReclamacionCenso.substring(anioReclamacionCenso.length() - 2);
//    }
//
//    public String getDiaResolucionCenso() {
//        return diaResolucionCenso;
//    }
//
//    public void setDiaResolucionCenso(String diaResolucionCenso) {
//        this.diaResolucionCenso = comprobarDia(diaResolucionCenso);
//    }
//
//    public String getMesResolucionCenso() {
//        return mesResolucionCenso;
//    }
//
//    public void setMesResolucionCenso(String mesResolucionCenso) {
//        this.mesResolucionCenso = validarYObtenerMes(mesResolucionCenso);
//    }
//
//    public String getAnioResolucionCenso() {
//        return anioResolucionCenso;
//    }
//
//    public void setAnioResolucionCenso(String anioResolucionCenso) {
//        this.anioResolucionCenso = anioResolucionCenso.substring(anioResolucionCenso.length() - 2);
//    }
//
//    public String getDiaExposicionCensoDefinitivo() {
//        return diaExposicionCensoDefinitivo;
//    }
//
//    public void setDiaExposicionCensoDefinitivo(String diaExposicionCensoDefinitivo) {
//        this.diaExposicionCensoDefinitivo = comprobarDia(diaExposicionCensoDefinitivo);
//    }
//
//    public String getMesExposicionCensoDefinitivo() {
//        return mesExposicionCensoDefinitivo;
//    }
//
//    public void setMesExposicionCensoDefinitivo(String mesExposicionCensoDefinitivo) {
//        this.mesExposicionCensoDefinitivo = validarYObtenerMes(mesExposicionCensoDefinitivo);
//    }
//
//    public String getAnioExposicionCensoDefinitivo() {
//        return anioExposicionCensoDefinitivo;
//    }
//
//    public void setAnioExposicionCensoDefinitivo(String anioExposicionCensoDefinitivo) {
//        this.anioExposicionCensoDefinitivo = anioExposicionCensoDefinitivo.substring(anioExposicionCensoDefinitivo.length() - 2);
//    }
//
//    public String getDiaInicioPresentacionCandidaturas() {
//        return diaInicioPresentacionCandidaturas;
//    }
//
//    public void setDiaInicioPresentacionCandidaturas(String diaInicioPresentacionCandidaturas) {
//        this.diaInicioPresentacionCandidaturas = comprobarDia(diaInicioPresentacionCandidaturas);
//    }
//
//    public String getMesInicioPresentacionCandidaturas() {
//        return mesInicioPresentacionCandidaturas;
//    }
//
//    public void setMesInicioPresentacionCandidaturas(String mesInicioPresentacionCandidaturas) {
//        this.mesInicioPresentacionCandidaturas = validarYObtenerMes(mesInicioPresentacionCandidaturas);
//    }
//
//    public String getDiaFinPresentacionCandidaturas() {
//        return diaFinPresentacionCandidaturas;
//    }
//
//    public void setDiaFinPresentacionCandidaturas(String diaFinPresentacionCandidaturas) {
//        this.diaFinPresentacionCandidaturas = comprobarDia(diaFinPresentacionCandidaturas);
//    }
//
//    public String getMesFinPresentacionCandidaturas() {
//        return mesFinPresentacionCandidaturas;
//    }
//
//    public void setMesFinPresentacionCandidaturas(String mesFinPresentacionCandidaturas) {
//        this.mesFinPresentacionCandidaturas = validarYObtenerMes(mesFinPresentacionCandidaturas);
//    }
//
//    public String getAnioFinPresentacionCandidaturas() {
//        return anioFinPresentacionCandidaturas;
//    }
//
//    public void setAnioFinPresentacionCandidaturas(String anioFinPresentacionCandidaturas) {
//        this.anioFinPresentacionCandidaturas = anioFinPresentacionCandidaturas.substring(anioFinPresentacionCandidaturas.length() - 2);
//    }
//
//    public String getDiaInicioExposicionCandidaturas() {
//        return diaInicioExposicionCandidaturas;
//    }
//
//    public void setDiaInicioExposicionCandidaturas(String diaInicioExposicionCandidaturas) {
//        this.diaInicioExposicionCandidaturas = comprobarDia(diaInicioExposicionCandidaturas);
//    }
//
//    public String getMesInicioExposicionCandidaturas() {
//        return mesInicioExposicionCandidaturas;
//    }
//
//    public void setMesInicioExposicionCandidaturas(String mesInicioExposicionCandidaturas) {
//        this.mesInicioExposicionCandidaturas = validarYObtenerMes(mesInicioExposicionCandidaturas);
//    }
//
//    public String getDiaFinExposicionCandidaturas() {
//        return diaFinExposicionCandidaturas;
//    }
//
//    public void setDiaFinExposicionCandidaturas(String diaFinExposicionCandidaturas) {
//        this.diaFinExposicionCandidaturas = comprobarDia(diaFinExposicionCandidaturas);
//    }
//
//    public String getMesFInExposicionCandidaturas() {
//        return mesFInExposicionCandidaturas;
//    }
//
//    public void setMesFInExposicionCandidaturas(String mesFInExposicionCandidaturas) {
//        this.mesFInExposicionCandidaturas = validarYObtenerMes(mesFInExposicionCandidaturas);
//    }
//
//    public String getAnioFInExposicionCandidaturas() {
//        return anioFInExposicionCandidaturas;
//    }
//
//    public void setAnioFInExposicionCandidaturas(String anioFInExposicionCandidaturas) {
//        this.anioFInExposicionCandidaturas = anioFInExposicionCandidaturas.substring(anioFInExposicionCandidaturas.length() - 2);
//    }
//
//    public String getDiaReclamacionCandidaturas() {
//        return diaReclamacionCandidaturas;
//    }
//
//    public void setDiaReclamacionCandidaturas(String diaReclamacionCandidaturas) {
//        this.diaReclamacionCandidaturas = comprobarDia(diaReclamacionCandidaturas);
//    }
//
//    public String getMesReclamacionCandidaturas() {
//        return mesReclamacionCandidaturas;
//    }
//
//    public void setMesReclamacionCandidaturas(String mesReclamacionCandidaturas) {
//        this.mesReclamacionCandidaturas = validarYObtenerMes(mesReclamacionCandidaturas);
//    }
//
//    public String getAnioReclamacionCandidaturas() {
//        return anioReclamacionCandidaturas;
//    }
//
//    public void setAnioReclamacionCandidaturas(String anioReclamacionCandidaturas) {
//        this.anioReclamacionCandidaturas = anioReclamacionCandidaturas.substring(anioReclamacionCandidaturas.length() - 2);
//    }
//
//    public String getDiaProclamacionCandidaturas() {
//        return diaProclamacionCandidaturas;
//    }
//
//    public void setDiaProclamacionCandidaturas(String diaProclamacionCandidaturas) {
//        this.diaProclamacionCandidaturas = comprobarDia(diaProclamacionCandidaturas);
//    }
//
//    public String getMesProclamacionCandidaturas() {
//        return mesProclamacionCandidaturas;
//    }
//
//    public void setMesProclamacionCandidaturas(String mesProclamacionCandidaturas) {
//        this.mesProclamacionCandidaturas = validarYObtenerMes(mesProclamacionCandidaturas);
//    }
//
//    public String getAnioProclamacionCandidaturas() {
//        return anioProclamacionCandidaturas;
//    }
//
//    public void setAnioProclamacionCandidaturas(String anioProclamacionCandidaturas) {
//        this.anioProclamacionCandidaturas = anioProclamacionCandidaturas.substring(anioProclamacionCandidaturas.length() - 2);;
//    }
//
//    public String getDiaInicioPropaganda() {
//        return diaInicioPropaganda;
//    }
//
//    public void setDiaInicioPropaganda(String diaInicioPropaganda) {
//        this.diaInicioPropaganda = comprobarDia(diaInicioPropaganda);
//    }
//
//    public String getMesInicioPropaganda() {
//        return mesInicioPropaganda;
//    }
//
//    public void setMesInicioPropaganda(String mesInicioPropaganda) {
//        this.mesInicioPropaganda = validarYObtenerMes(mesInicioPropaganda);
//    }
//
//    public String getDiaFinPropaganda() {
//        return diaFinPropaganda;
//    }
//
//    public void setDiaFinPropaganda(String diaFinPropaganda) {
//        this.diaFinPropaganda = comprobarDia(diaFinPropaganda);
//    }
//
//    public String getAnioFinPropaganda() {
//        return anioFinPropaganda;
//    }
//
//    public void setAnioFinPropaganda(String anioFinPropaganda) {
//        this.anioFinPropaganda = anioFinPropaganda.substring(anioFinPropaganda.length() - 2);;
//    }
//
//    public String getMesFinPropaganda() {
//        return mesFinPropaganda;
//    }
//
//    public void setMesFinPropaganda(String mesFinPropaganda) {
//        this.mesFinPropaganda = validarYObtenerMes(mesFinPropaganda);
//    }
//
//    public String getDiaReflexion() {
//        return diaReflexion;
//    }
//
//    public void setDiaReflexion(String diaReflexion) {
//        this.diaReflexion = comprobarDia(diaReflexion);
//    }
//
//    public String getMesReflexion() {
//        return mesReflexion;
//    }
//
//    public void setMesReflexion(String mesReflexion) {
//        this.mesReflexion = validarYObtenerMes(mesReflexion);
//    }
//
//    public String getAnioReflexion() {
//        return anioReflexion;
//    }
//
//    public void setAnioReflexion(String anioReflexion) {
//        this.anioReflexion = anioReflexion.substring(anioReflexion.length() - 2);;
//    }
//
//    public String getDiaVotacion() {
//        return diaVotacion;
//    }
//
//    public void setDiaVotacion(String diaVotacion) {
//        this.diaVotacion = comprobarDia(diaVotacion);
//    }
//
//    public String getMesVotacion() {
//        return mesVotacion;
//    }
//
//    public void setMesVotacion(String mesVotacion) {
//        this.mesVotacion = validarYObtenerMes(mesVotacion);
//    }
//
//    public String getAnioVotacion() {
//        return anioVotacion;
//    }
//
//    public void setAnioVotacion(String anioVotacion) {
//        this.anioVotacion = anioVotacion.substring(anioVotacion.length() - 2);;
//    }
//
//    public String comprobarDia(String diaMes) {
//        try {
//            // Intenta convertir el día a un número entero
//            int dia = Integer.parseInt(diaMes);
//
//            // Comprueba si el día está en el rango válido
//            if (dia < Constantes.PRIMER_DIA_MES || dia > Constantes.ULTIMO_DIA_MES) {
//                // Maneja la lógica en caso de que el día esté fuera del rango
//                throw new IllegalArgumentException("El día está fuera del rango válido.");
//            }
//
//            return diaMes;
//        } catch (NumberFormatException e) {
//            // Maneja la excepción en caso de que la conversión a entero falle
//            throw new IllegalArgumentException("El día no es un número válido.");
//        }
//    }
//
//    public String validarYObtenerMes(String mes) {
//        try {
//            // Intenta convertir el mes a un número entero
//            int meses = Integer.parseInt(mes);
//
//            // Comprueba si el mes está en el rango válido
//            if (meses < Constantes.PRIMER_MES || meses > Constantes.ULTIMO_MES) {
//                // Maneja la lógica en caso de que el mes esté fuera del rango
//                throw new IllegalArgumentException("El mes está fuera del rango válido.");
//            }
//
//            // Si el mes es un número, devuelve el valor original
//            return mes;
//        } catch (NumberFormatException e) {
//            // Si no se puede convertir a entero, busca el nombre del mes en el enum
//            for (Meses month : Meses.values()) {
//                if (mes.equalsIgnoreCase(month.toString())) {
//                    // Devuelve el nombre del mes encontrado
//                    return month.toString();
//                }
//            }
//
//            // Si no se encuentra el nombre del mes en el enum, lanza una excepción
//            throw new IllegalArgumentException("El mes no es un número ni un nombre de mes válido.");
//        }
//    }
//}
