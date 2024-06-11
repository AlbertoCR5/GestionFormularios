package com.example.proyecto.modal;

import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.Meses;
import com.example.proyecto.util.ValidadorCampos;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * La clase `Calendario_Comite` extiende la clase `Preaviso`.
 * Esto significa que `Calendario_Comite` hereda algunas de las propiedades y métodos de `Preaviso`.
 * Esta clase representa el modelo con el cual se elabora el calendario para comités de empresa.
 *
 * @author Alberto Castro <AlbertoCastrovas@gmail.com>
 */
public class CalendarioComite extends Preaviso{

    private Date horaConstitucion;
    private String diaInicioExposicionCenso;
    private String mesInicioExposicionCenso;
    private String diaFinExposicionCenso;
    private String mesFinExposicionCenso;
    private String anioFinExposicionCenso;
    private String diaReclamacionCenso;
    private String mesReclamacionCenso;
    private String anioReclamacionCenso;
    private String diaResolucionCenso;
    private String mesResolucionCenso;
    private String anioResolucionCenso;
    private String diaExposicionCensoDefinitivo;
    private String mesExposicionCensoDefinitivo;
    private String anioExposicionCensoDefinitivo;
    private String diaInicioPresentacionCandidaturas;
    private String mesInicioPresentacionCandidaturas;
    private String diaFinPresentacionCandidaturas;
    private String mesFinPresentacionCandidaturas;
    private String anioFinPresentacionCandidaturas;
    private String diaInicioExposicionCandidaturas;
    private String mesInicioExposicionCandidaturas;
    private String diaFinExposicionCandidaturas;
    private String mesFinExposicionCandidaturas;
    private String anioFinExposicionCandidaturas;
    private String diaReclamacionCandidaturas;
    private String mesReclamacionCandidaturas;
    private String anioReclamacionCandidaturas;
    private String diaProclamacionCandidaturas;
    private String mesProclamacionCandidaturas;
    private String anioProclamacionCandidaturas;
    private String diaInicioPropaganda;
    private String mesInicioPropaganda;
    private String diaFinPropaganda;
    private String anioFinPropaganda;
    private String mesFinPropaganda;
    private String diaReflexion;
    private String mesReflexion;
    private String anioReflexion;
    private String diaVotacion;
    private String mesVotacion;
    private String anioVotacion;

    /**
     * Constructor por defecto.
     */
    public CalendarioComite() {
    }

    /**
     * Constructor que inicializa nombre de la empresa y fecha de constitución.
     *
     * @param nombreEmpresa     El nombre de la empresa.
     * @param fechaConstitucion La fecha de constitución.
     * @throws CumplimentarPDFException Si hay un error en la cumplimentación del PDF.
     */
    public CalendarioComite(String nombreEmpresa, String fechaConstitucion) throws CumplimentarPDFException {
        super(nombreEmpresa, fechaConstitucion);
    }

    /**
     * Constructor que inicializa varios atributos.
     *
     * @param nombreEmpresa                         El nombre de la empresa.
     * @param fechaConstitucion                     La fecha de constitución.
     * @param diaInicioExposicionCenso              El día de inicio de exposición del censo.
     * @param mesInicioExposicionCenso              El mes de inicio de exposición del censo.
     * @param diaFinExposicionCenso                 El día de fin de exposición del censo.
     * @param mesFinExposicionCenso                 El mes de fin de exposición del censo.
     * @param anioFinExposicionCenso                El año de fin de exposición del censo.
     * @param diaReclamacionCenso                   El día de reclamación del censo.
     * @param mesReclamacionCenso                   El mes de reclamación del censo.
     * @param anioReclamacionCenso                  El año de reclamación del censo.
     * @param diaResolucionCenso                    El día de resolución del censo.
     * @param mesResolucionCenso                    El mes de resolución del censo.
     * @param anioResolucionCenso                   El año de resolución del censo.
     * @param diaExposicionCensoDefinitivo          El día de exposición del censo definitivo.
     * @param mesExposicionCensoDefinitivo          El mes de exposición del censo definitivo.
     * @param anioExposicionCensoDefinitivo         El año de exposición del censo definitivo.
     * @param diaInicioPresentacionCandidaturas     El día de inicio de presentación de candidaturas.
     * @param mesInicioPresentacionCandidaturas     El mes de inicio de presentación de candidaturas.
     * @param diaFinPresentacionCandidaturas        El día de fin de presentación de candidaturas.
     * @param mesFinPresentacionCandidaturas        El mes de fin de presentación de candidaturas.
     * @param anioFinPresentacionCandidaturas       El año de fin de presentación de candidaturas.
     * @param diaInicioExposicionCandidaturas       El día de inicio de exposición de candidaturas.
     * @param mesInicioExposicionCandidaturas       El mes de inicio de exposición de candidaturas.
     * @param diaFinExposicionCandidaturas          El día de fin de exposición de candidaturas.
     * @param mesFinExposicionCandidaturas          El mes de fin de exposición de candidaturas.
     * @param anioFinExposicionCandidaturas         El año de fin de exposición de candidaturas.
     * @param diaReclamacionCandidaturas            El día de reclamación de candidaturas.
     * @param mesReclamacionCandidaturas            El mes de reclamación de candidaturas.
     * @param anioReclamacionCandidaturas           El año de reclamación de candidaturas.
     * @param diaProclamacionCandidaturas           El día de proclamación de candidaturas.
     * @param mesProclamacionCandidaturas           El mes de proclamación de candidaturas.
     * @param anioProclamacionCandidaturas          El año de proclamación de candidaturas.
     * @param diaInicioPropaganda                   El día de inicio de propaganda.
     * @param mesInicioPropaganda                   El mes de inicio de propaganda.
     * @param diaFinPropaganda                      El día de fin de propaganda.
     * @param anioFinPropaganda                     El año de fin de propaganda.
     * @param mesFinPropaganda                      El mes de fin de propaganda.
     * @param diaReflexion                          El día de reflexión.
     * @param mesReflexion                          El mes de reflexión.
     * @param anioReflexion                         El año de reflexión.
     * @param diaVotacion                           El día de votación.
     * @param mesVotacion                           El mes de votación.
     * @param anioVotacion                          El año de votación.
     * @throws CumplimentarPDFException Si hay un error en la cumplimentación del PDF.
     */
    public CalendarioComite(String nombreEmpresa, String fechaConstitucion, String diaInicioExposicionCenso, String mesInicioExposicionCenso,
                            String diaFinExposicionCenso, String mesFinExposicionCenso, String anioFinExposicionCenso, String diaReclamacionCenso,
                            String mesReclamacionCenso, String anioReclamacionCenso, String diaResolucionCenso, String mesResolucionCenso,
                            String anioResolucionCenso, String diaExposicionCensoDefinitivo, String mesExposicionCensoDefinitivo, String anioExposicionCensoDefinitivo,
                            String diaInicioPresentacionCandidaturas, String mesInicioPresentacionCandidaturas, String diaFinPresentacionCandidaturas,
                            String mesFinPresentacionCandidaturas, String anioFinPresentacionCandidaturas, String diaInicioExposicionCandidaturas,
                            String mesInicioExposicionCandidaturas, String diaFinExposicionCandidaturas, String mesFinExposicionCandidaturas,
                            String anioFinExposicionCandidaturas, String diaReclamacionCandidaturas, String mesReclamacionCandidaturas, String anioReclamacionCandidaturas,
                            String diaProclamacionCandidaturas, String mesProclamacionCandidaturas, String anioProclamacionCandidaturas, String diaInicioPropaganda,
                            String mesInicioPropaganda, String diaFinPropaganda, String anioFinPropaganda, String mesFinPropaganda, String diaReflexion,
                            String mesReflexion, String anioReflexion, String diaVotacion, String mesVotacion, String anioVotacion) throws CumplimentarPDFException {
        super(nombreEmpresa, fechaConstitucion);
        this.diaInicioExposicionCenso = diaInicioExposicionCenso;
        this.mesInicioExposicionCenso = mesInicioExposicionCenso;
        this.diaFinExposicionCenso = diaFinExposicionCenso;
        this.mesFinExposicionCenso = mesFinExposicionCenso;
        this.anioFinExposicionCenso = anioFinExposicionCenso;
        this.diaReclamacionCenso = diaReclamacionCenso;
        this.mesReclamacionCenso = mesReclamacionCenso;
        this.anioReclamacionCenso = anioReclamacionCenso;
        this.diaResolucionCenso = diaResolucionCenso;
        this.mesResolucionCenso = mesResolucionCenso;
        this.anioResolucionCenso = anioResolucionCenso;
        this.diaExposicionCensoDefinitivo = diaExposicionCensoDefinitivo;
        this.mesExposicionCensoDefinitivo = mesExposicionCensoDefinitivo;
        this.anioExposicionCensoDefinitivo = anioExposicionCensoDefinitivo;
        this.diaInicioPresentacionCandidaturas = diaInicioPresentacionCandidaturas;
        this.mesInicioPresentacionCandidaturas = mesInicioPresentacionCandidaturas;
        this.diaFinPresentacionCandidaturas = diaFinPresentacionCandidaturas;
        this.mesFinPresentacionCandidaturas = mesFinPresentacionCandidaturas;
        this.anioFinPresentacionCandidaturas = anioFinPresentacionCandidaturas;
        this.diaInicioExposicionCandidaturas = diaInicioExposicionCandidaturas;
        this.mesInicioExposicionCandidaturas = mesInicioExposicionCandidaturas;
        this.diaFinExposicionCandidaturas = diaFinExposicionCandidaturas;
        this.mesFinExposicionCandidaturas = mesFinExposicionCandidaturas;
        this.anioFinExposicionCandidaturas = anioFinExposicionCandidaturas;
        this.diaReclamacionCandidaturas = diaReclamacionCandidaturas;
        this.mesReclamacionCandidaturas = mesReclamacionCandidaturas;
        this.anioReclamacionCandidaturas = anioReclamacionCandidaturas;
        this.diaProclamacionCandidaturas = diaProclamacionCandidaturas;
        this.mesProclamacionCandidaturas = mesProclamacionCandidaturas;
        this.anioProclamacionCandidaturas = anioProclamacionCandidaturas;
        this.diaInicioPropaganda = diaInicioPropaganda;
        this.mesInicioPropaganda = mesInicioPropaganda;
        this.diaFinPropaganda = diaFinPropaganda;
        this.anioFinPropaganda = anioFinPropaganda;
        this.mesFinPropaganda = mesFinPropaganda;
        this.diaReflexion = diaReflexion;
        this.mesReflexion = mesReflexion;
        this.anioReflexion = anioReflexion;
        this.diaVotacion = diaVotacion;
        this.mesVotacion = mesVotacion;
        this.anioVotacion = anioVotacion;
    }

    public CalendarioComite(String nombreEmpresa, String fechaConstitucion, Date horaConstitucion, String diaInicioExposicionCenso,
                            String mesInicioExposicionCenso, String diaFinExposicionCenso, String mesFinExposicionCenso,
                            String anioFinExposicionCenso, String diaReclamacionCenso, String mesReclamacionCenso, String anioReclamacionCenso,
                            String diaResolucionCenso, String mesResolucionCenso, String anioResolucionCenso, String diaExposicionCensoDefinitivo,
                            String mesExposicionCensoDefinitivo, String anioExposicionCensoDefinitivo, String diaInicioPresentacionCandidaturas,
                            String mesInicioPresentacionCandidaturas, String diaFinPresentacionCandidaturas, String mesFinPresentacionCandidaturas,
                            String anioFinPresentacionCandidaturas, String diaInicioExposicionCandidaturas, String mesInicioExposicionCandidaturas,
                            String diaFinExposicionCandidaturas, String mesFInExposicionCandidaturas, String anioFInExposicionCandidaturas,
                            String diaReclamacionCandidaturas, String mesReclamacionCandidaturas, String anioReclamacionCandidaturas,
                            String diaProclamacionCandidaturas, String mesProclamacionCandidaturas, String anioProclamacionCandidaturas,
                            String diaInicioPropaganda, String mesInicioPropaganda, String diaFinPropaganda, String anioFinPropaganda,
                            String mesFinPropaganda, String diaReflexion, String mesReflexion, String anioReflexion, String diaVotacion,
                            String mesVotacion, String anioVotacion) throws CumplimentarPDFException {
        super(nombreEmpresa, fechaConstitucion);
        this.horaConstitucion = horaConstitucion;
        this.diaInicioExposicionCenso = diaInicioExposicionCenso;
        this.mesInicioExposicionCenso = mesInicioExposicionCenso;
        this.diaFinExposicionCenso = diaFinExposicionCenso;
        this.mesFinExposicionCenso = mesFinExposicionCenso;
        this.anioFinExposicionCenso = anioFinExposicionCenso;
        this.diaReclamacionCenso = diaReclamacionCenso;
        this.mesReclamacionCenso = mesReclamacionCenso;
        this.anioReclamacionCenso = anioReclamacionCenso;
        this.diaResolucionCenso = diaResolucionCenso;
        this.mesResolucionCenso = mesResolucionCenso;
        this.anioResolucionCenso = anioResolucionCenso;
        this.diaExposicionCensoDefinitivo = diaExposicionCensoDefinitivo;
        this.mesExposicionCensoDefinitivo = mesExposicionCensoDefinitivo;
        this.anioExposicionCensoDefinitivo = anioExposicionCensoDefinitivo;
        this.diaInicioPresentacionCandidaturas = diaInicioPresentacionCandidaturas;
        this.mesInicioPresentacionCandidaturas = mesInicioPresentacionCandidaturas;
        this.diaFinPresentacionCandidaturas = diaFinPresentacionCandidaturas;
        this.mesFinPresentacionCandidaturas = mesFinPresentacionCandidaturas;
        this.anioFinPresentacionCandidaturas = anioFinPresentacionCandidaturas;
        this.diaInicioExposicionCandidaturas = diaInicioExposicionCandidaturas;
        this.mesInicioExposicionCandidaturas = mesInicioExposicionCandidaturas;
        this.diaFinExposicionCandidaturas = diaFinExposicionCandidaturas;
        this.mesFinExposicionCandidaturas = mesFInExposicionCandidaturas;
        this.anioFinExposicionCandidaturas = anioFInExposicionCandidaturas;
        this.diaReclamacionCandidaturas = diaReclamacionCandidaturas;
        this.mesReclamacionCandidaturas = mesReclamacionCandidaturas;
        this.anioReclamacionCandidaturas = anioReclamacionCandidaturas;
        this.diaProclamacionCandidaturas = diaProclamacionCandidaturas;
        this.mesProclamacionCandidaturas = mesProclamacionCandidaturas;
        this.anioProclamacionCandidaturas = anioProclamacionCandidaturas;
        this.diaInicioPropaganda = diaInicioPropaganda;
        this.mesInicioPropaganda = mesInicioPropaganda;
        this.diaFinPropaganda = diaFinPropaganda;
        this.anioFinPropaganda = anioFinPropaganda;
        this.mesFinPropaganda = mesFinPropaganda;
        this.diaReflexion = diaReflexion;
        this.mesReflexion = mesReflexion;
        this.anioReflexion = anioReflexion;
        this.diaVotacion = diaVotacion;
        this.mesVotacion = mesVotacion;
        this.anioVotacion = anioVotacion;
    }

    /**
     *
     * @deprecated
     */
    public Date getHoraConstitucion() {
        return horaConstitucion;
    }

    /**
     * Este método no se utiliza actualmente.
     * Se incluye para posibles futuras implementaciones.
     *
     * @deprecated Este método está deprecado y muy rara vez será utilizado.
     */
    public void setHoraConstitucion(Date horaConstitucion) throws CumplimentarPDFException {
        ValidadorCampos validarHora = new ValidadorCampos();

        if (!validarHora.validarHora(String.valueOf(horaConstitucion))){
            throw new CumplimentarPDFException("ERROR, Formato de hora incorrecto -->" + Constantes.FORMATO_FECHA + "\n");
        }
        this.horaConstitucion = horaConstitucion;
    }

    public String getDiaInicioExposicionCenso() {
        return diaInicioExposicionCenso;
    }

    /**
     * Establece el día de inicio de exposición del censo después de comprobar su validez.
     *
     * @param diaInicioExposicionCenso El día de inicio de exposición del censo.
     */
    public void setDiaInicioExposicionCenso(String diaInicioExposicionCenso) {
        this.diaInicioExposicionCenso = comprobarDia(diaInicioExposicionCenso);
    }

    public String getMesInicioExposicionCenso() {
        return mesInicioExposicionCenso;
    }

    /**
     * Establece el mes de inicio de exposición del censo después de validar su formato.
     *
     * @param mesInicioExposicionCenso El mes de inicio de exposición del censo.
     */
    public void setMesInicioExposicionCenso(String mesInicioExposicionCenso) {
        this.mesInicioExposicionCenso = validarYObtenerMes(mesInicioExposicionCenso);
    }

    public String getDiaFinExposicionCenso() {
        return diaFinExposicionCenso;
    }

    /**
     * Establece el día de fin de exposición del censo después de comprobar su validez.
     *
     * @param diaFinExposicionCenso El día de fin de exposición del censo.
     */
    public void setDiaFinExposicionCenso(String diaFinExposicionCenso) {
        this.diaFinExposicionCenso = comprobarDia(diaFinExposicionCenso);
    }

    public String getMesFinExposicionCenso() {
        return mesFinExposicionCenso;
    }

    /**
     * Establece el mes de fin de exposición del censo después de validar su formato.
     *
     * @param mesFinExposicionCenso El mes de fin de exposición del censo.
     */
    public void setMesFinExposicionCenso(String mesFinExposicionCenso) {
        this.mesFinExposicionCenso = validarYObtenerMes(mesFinExposicionCenso);
    }

    public String getAnioFinExposicionCenso() {
        return anioFinExposicionCenso;
    }

    /**
     * Establece el año de fin de exposición del censo, tomando los últimos dos dígitos del año proporcionado.
     *
     * @param anioFinExposicionCenso El año de fin de exposición del censo.
     */
    public void setAnioFinExposicionCenso(@NotNull String anioFinExposicionCenso) {
        this.anioFinExposicionCenso = anioFinExposicionCenso.substring(anioFinExposicionCenso.length() - 2);
    }

    public String getDiaReclamacionCenso() {
        return diaReclamacionCenso;
    }

    /**
     * Establece el día de reclamación del censo después de comprobar su validez.
     *
     * @param diaReclamacionCenso El día de reclamación del censo.
     */
    public void setDiaReclamacionCenso(String diaReclamacionCenso) {
        this.diaReclamacionCenso = comprobarDia(diaReclamacionCenso);
    }

    public String getMesReclamacionCenso() {
        return mesReclamacionCenso;
    }

    /**
     * Establece el mes de reclamación del censo después de validar su formato.
     *
     * @param mesReclamacionCenso El mes de reclamación del censo.
     */
    public void setMesReclamacionCenso(String mesReclamacionCenso) {
        this.mesReclamacionCenso = validarYObtenerMes(mesReclamacionCenso);
    }

    public String getAnioReclamacionCenso() {
        return anioReclamacionCenso;
    }

    /**
     * Establece el año de reclamación del censo, tomando los últimos dos dígitos del año proporcionado.
     *
     * @param anioReclamacionCenso El año de reclamación del censo.
     */
    public void setAnioReclamacionCenso(@NotNull String anioReclamacionCenso) {
        this.anioReclamacionCenso = anioReclamacionCenso.substring(anioReclamacionCenso.length() - 2);
    }

    public String getDiaResolucionCenso() {
        return diaResolucionCenso;
    }

    /**
     * Establece el día de resolución del censo después de comprobar su validez.
     *
     * @param diaResolucionCenso El día de resolución del censo.
     */
    public void setDiaResolucionCenso(String diaResolucionCenso) {
        this.diaResolucionCenso = comprobarDia(diaResolucionCenso);
    }

    public String getMesResolucionCenso() {
        return mesResolucionCenso;
    }

    /**
     * Establece el mes de resolución del censo después de validar su formato.
     *
     * @param mesResolucionCenso El mes de resolución del censo.
     */
    public void setMesResolucionCenso(String mesResolucionCenso) {
        this.mesResolucionCenso = validarYObtenerMes(mesResolucionCenso);
    }

    public String getAnioResolucionCenso() {
        return anioResolucionCenso;
    }

    /**
     * Establece el año de resolución del censo, tomando los últimos dos dígitos del año proporcionado.
     *
     * @param anioResolucionCenso El año de resolución del censo.
     */
    public void setAnioResolucionCenso(@NotNull String anioResolucionCenso) {
        this.anioResolucionCenso = anioResolucionCenso.substring(anioResolucionCenso.length() - 2);
    }

    public String getDiaExposicionCensoDefinitivo() {
        return diaExposicionCensoDefinitivo;
    }

    /**
     * Establece el día de exposición del censo definitivo después de comprobar su validez.
     *
     * @param diaExposicionCensoDefinitivo El día de exposición del censo definitivo.
     */
    public void setDiaExposicionCensoDefinitivo(String diaExposicionCensoDefinitivo) {
        this.diaExposicionCensoDefinitivo = comprobarDia(diaExposicionCensoDefinitivo);
    }

    public String getMesExposicionCensoDefinitivo() {
        return mesExposicionCensoDefinitivo;
    }

    /**
     * Establece el mes de exposición del censo definitivo después de validar su formato.
     *
     * @param mesExposicionCensoDefinitivo El mes de exposición del censo definitivo.
     */
    public void setMesExposicionCensoDefinitivo(String mesExposicionCensoDefinitivo) {
        this.mesExposicionCensoDefinitivo = validarYObtenerMes(mesExposicionCensoDefinitivo);
    }

    public String getAnioExposicionCensoDefinitivo() {
        return anioExposicionCensoDefinitivo;
    }

    /**
     * Establece el año de exposición del censo definitivo, tomando los últimos dos dígitos del año proporcionado.
     *
     * @param anioExposicionCensoDefinitivo El año de exposición del censo definitivo.
     */
    public void setAnioExposicionCensoDefinitivo(@NotNull String anioExposicionCensoDefinitivo) {
        this.anioExposicionCensoDefinitivo = anioExposicionCensoDefinitivo.substring(anioExposicionCensoDefinitivo.length() - 2);
    }

    public String getDiaInicioPresentacionCandidaturas() {
        return diaInicioPresentacionCandidaturas;
    }

    /**
     * Establece el día de inicio de presentación de candidaturas después de comprobar su validez.
     *
     * @param diaInicioPresentacionCandidaturas El día de inicio de presentación de candidaturas.
     */
    public void setDiaInicioPresentacionCandidaturas(String diaInicioPresentacionCandidaturas) {
        this.diaInicioPresentacionCandidaturas = comprobarDia(diaInicioPresentacionCandidaturas);
    }

    public String getMesInicioPresentacionCandidaturas() {
        return mesInicioPresentacionCandidaturas;
    }

    /**
     * Establece el mes de inicio de presentación de candidaturas después de validar su formato.
     *
     * @param mesInicioPresentacionCandidaturas El mes de inicio de presentación de candidaturas.
     */
    public void setMesInicioPresentacionCandidaturas(String mesInicioPresentacionCandidaturas) {
        this.mesInicioPresentacionCandidaturas = validarYObtenerMes(mesInicioPresentacionCandidaturas);
    }

    public String getDiaFinPresentacionCandidaturas() {
        return diaFinPresentacionCandidaturas;
    }

    /**
     * Establece el día de fin de presentación de candidaturas después de comprobar su validez.
     *
     * @param diaFinPresentacionCandidaturas El día de fin de presentación de candidaturas.
     */
    public void setDiaFinPresentacionCandidaturas(String diaFinPresentacionCandidaturas) {
        this.diaFinPresentacionCandidaturas = comprobarDia(diaFinPresentacionCandidaturas);
    }

    public String getMesFinPresentacionCandidaturas() {
        return mesFinPresentacionCandidaturas;
    }

    /**
     * Establece el mes de fin de presentación de candidaturas después de validar su formato.
     *
     * @param mesFinPresentacionCandidaturas El mes de fin de presentación de candidaturas.
     */
    public void setMesFinPresentacionCandidaturas(String mesFinPresentacionCandidaturas) {
        this.mesFinPresentacionCandidaturas = validarYObtenerMes(mesFinPresentacionCandidaturas);
    }

    public String getAnioFinPresentacionCandidaturas() {
        return anioFinPresentacionCandidaturas;
    }

    /**
     * Establece el año de fin de presentación de candidaturas, tomando los últimos dos dígitos del año proporcionado.
     *
     * @param anioFinPresentacionCandidaturas El año de fin de presentación de candidaturas.
     */
    public void setAnioFinPresentacionCandidaturas(@NotNull String anioFinPresentacionCandidaturas) {
        this.anioFinPresentacionCandidaturas = anioFinPresentacionCandidaturas.substring(anioFinPresentacionCandidaturas.length() - 2);
    }

    public String getDiaInicioExposicionCandidaturas() {
        return diaInicioExposicionCandidaturas;
    }

    /**
     * Establece el día de inicio de exposición de candidaturas después de comprobar su validez.
     *
     * @param diaInicioExposicionCandidaturas El día de inicio de exposición de candidaturas.
     */
    public void setDiaInicioExposicionCandidaturas(String diaInicioExposicionCandidaturas) {
        this.diaInicioExposicionCandidaturas = comprobarDia(diaInicioExposicionCandidaturas);
    }

    public String getMesInicioExposicionCandidaturas() {
        return mesInicioExposicionCandidaturas;
    }

    /**
     * Establece el mes de inicio de exposición de candidaturas después de validar su formato.
     *
     * @param mesInicioExposicionCandidaturas El mes de inicio de exposición de candidaturas.
     */
    public void setMesInicioExposicionCandidaturas(String mesInicioExposicionCandidaturas) {
        this.mesInicioExposicionCandidaturas = validarYObtenerMes(mesInicioExposicionCandidaturas);
    }

    public String getDiaFinExposicionCandidaturas() {
        return diaFinExposicionCandidaturas;
    }

    /**
     * Establece el día de fin de exposición de candidaturas después de comprobar su validez.
     *
     * @param diaFinExposicionCandidaturas El día de fin de exposición de candidaturas.
     */
    public void setDiaFinExposicionCandidaturas(String diaFinExposicionCandidaturas) {
        this.diaFinExposicionCandidaturas = comprobarDia(diaFinExposicionCandidaturas);
    }

    public String getMesFinExposicionCandidaturas() {
        return mesFinExposicionCandidaturas;
    }

    /**
     * Establece el mes de fin de exposición de candidaturas después de validar su formato.
     *
     * @param mesFinExposicionCandidaturas El mes de fin de exposición de candidaturas.
     */
    public void setMesFinExposicionCandidaturas(String mesFinExposicionCandidaturas) {
        this.mesFinExposicionCandidaturas = validarYObtenerMes(mesFinExposicionCandidaturas);
    }

    public String getAnioFinExposicionCandidaturas() {
        return anioFinExposicionCandidaturas;
    }

    /**
     * Establece el año de fin de exposición de candidaturas, tomando los últimos dos dígitos del año proporcionado.
     *
     * @param anioFinExposicionCandidaturas El año de fin de exposición de candidaturas.
     */
    public void setAnioFinExposicionCandidaturas(@NotNull String anioFinExposicionCandidaturas) {
        this.anioFinExposicionCandidaturas = anioFinExposicionCandidaturas.substring(anioFinExposicionCandidaturas.length() - 2);
    }

    public String getDiaReclamacionCandidaturas() {
        return diaReclamacionCandidaturas;
    }

    /**
     * Establece el día de reclamación de candidaturas después de comprobar su validez.
     *
     * @param diaReclamacionCandidaturas El día de reclamación de candidaturas.
     */
    public void setDiaReclamacionCandidaturas(String diaReclamacionCandidaturas) {
        this.diaReclamacionCandidaturas = comprobarDia(diaReclamacionCandidaturas);
    }

    public String getMesReclamacionCandidaturas() {
        return mesReclamacionCandidaturas;
    }

    /**
     * Establece el mes de reclamación de candidaturas después de validar su formato.
     *
     * @param mesReclamacionCandidaturas El mes de reclamación de candidaturas.
     */
    public void setMesReclamacionCandidaturas(String mesReclamacionCandidaturas) {
        this.mesReclamacionCandidaturas = validarYObtenerMes(mesReclamacionCandidaturas);
    }

    public String getAnioReclamacionCandidaturas() {
        return anioReclamacionCandidaturas;
    }

    /**
     * Establece el año de reclamación de candidaturas, tomando los últimos dos dígitos del año proporcionado.
     *
     * @param anioReclamacionCandidaturas El año de reclamación de candidaturas.
     */
    public void setAnioReclamacionCandidaturas(@NotNull String anioReclamacionCandidaturas) {
        this.anioReclamacionCandidaturas = anioReclamacionCandidaturas.substring(anioReclamacionCandidaturas.length() - 2);
    }

    public String getDiaProclamacionCandidaturas() {
        return diaProclamacionCandidaturas;
    }

    /**
     * Establece el día de proclamación de candidaturas después de comprobar su validez.
     *
     * @param diaProclamacionCandidaturas El día de proclamación de candidaturas.
     */
    public void setDiaProclamacionCandidaturas(String diaProclamacionCandidaturas) {
        this.diaProclamacionCandidaturas = comprobarDia(diaProclamacionCandidaturas);
    }

    public String getMesProclamacionCandidaturas() {
        return mesProclamacionCandidaturas;
    }

    /**
     * Establece el mes de proclamación de candidaturas después de validar su formato.
     *
     * @param mesProclamacionCandidaturas El mes de proclamación de candidaturas.
     */
    public void setMesProclamacionCandidaturas(String mesProclamacionCandidaturas) {
        this.mesProclamacionCandidaturas = validarYObtenerMes(mesProclamacionCandidaturas);
    }

    public String getAnioProclamacionCandidaturas() {
        return anioProclamacionCandidaturas;
    }

    /**
     * Establece el año de proclamación de candidaturas, tomando los últimos dos dígitos del año proporcionado.
     *
     * @param anioProclamacionCandidaturas El año de proclamación de candidaturas.
     */
    public void setAnioProclamacionCandidaturas(@NotNull String anioProclamacionCandidaturas) {
        this.anioProclamacionCandidaturas = anioProclamacionCandidaturas.substring(anioProclamacionCandidaturas.length() - 2);
    }

    public String getDiaInicioPropaganda() {
        return diaInicioPropaganda;
    }

    /**
     * Establece el día de inicio de propaganda después de comprobar su validez.
     *
     * @param diaInicioPropaganda El día de inicio de propaganda.
     */
    public void setDiaInicioPropaganda(String diaInicioPropaganda) {
        this.diaInicioPropaganda = comprobarDia(diaInicioPropaganda);
    }

    public String getMesInicioPropaganda() {
        return mesInicioPropaganda;
    }

    /**
     * Establece el mes de inicio de propaganda después de validar su formato.
     *
     * @param mesInicioPropaganda El mes de inicio de propaganda.
     */
    public void setMesInicioPropaganda(String mesInicioPropaganda) {
        this.mesInicioPropaganda = validarYObtenerMes(mesInicioPropaganda);
    }

    public String getDiaFinPropaganda() {
        return diaFinPropaganda;
    }

    /**
     * Establece el día de fin de propaganda después de comprobar su validez.
     *
     * @param diaFinPropaganda El día de fin de propaganda.
     */
    public void setDiaFinPropaganda(String diaFinPropaganda) {
        this.diaFinPropaganda = comprobarDia(diaFinPropaganda);
    }

    public String getAnioFinPropaganda() {
        return anioFinPropaganda;
    }

    /**
     * Establece el año de fin de propaganda, tomando los últimos dos dígitos del año proporcionado.
     *
     * @param anioFinPropaganda El año de fin de propaganda.
     */
    public void setAnioFinPropaganda(@NotNull String anioFinPropaganda) {
        this.anioFinPropaganda = anioFinPropaganda.substring(anioFinPropaganda.length() - 2);
    }

    public String getMesFinPropaganda() {
        return mesFinPropaganda;
    }

    /**
     * Establece el mes de fin de propaganda después de validar su formato.
     *
     * @param mesFinPropaganda El mes de fin de propaganda.
     */
    public void setMesFinPropaganda(String mesFinPropaganda) {
        this.mesFinPropaganda = validarYObtenerMes(mesFinPropaganda);
    }

    public String getDiaReflexion() {
        return diaReflexion;
    }

    /**
     * Establece el día de reflexión después de comprobar su validez.
     *
     * @param diaReflexion El día de reflexión.
     */
    public void setDiaReflexion(String diaReflexion) {
        this.diaReflexion = comprobarDia(diaReflexion);
    }

    public String getMesReflexion() {
        return mesReflexion;
    }

    /**
     * Establece el mes de reflexión después de validar su formato.
     *
     * @param mesReflexion El mes de reflexión.
     */
    public void setMesReflexion(String mesReflexion) {
        this.mesReflexion = validarYObtenerMes(mesReflexion);
    }

    public String getAnioReflexion() {
        return anioReflexion;
    }

    /**
     * Establece el año de reflexión, tomando los últimos dos dígitos del año proporcionado.
     *
     * @param anioReflexion El año de reflexión.
     */
    public void setAnioReflexion(@NotNull String anioReflexion) {
        this.anioReflexion = anioReflexion.substring(anioReflexion.length() - 2);
    }

    public String getDiaVotacion() {
        return diaVotacion;
    }

    /**
     * Establece el día de votación después de comprobar su validez.
     *
     * @param diaVotacion El día de votación.
     */
    public void setDiaVotacion(String diaVotacion) {
        this.diaVotacion = comprobarDia(diaVotacion);
    }

    public String getMesVotacion() {
        return mesVotacion;
    }

    /**
     * Establece el mes de votación después de validar su formato.
     *
     * @param mesVotacion El mes de votación.
     */
    public void setMesVotacion(String mesVotacion) {
        this.mesVotacion = validarYObtenerMes(mesVotacion);
    }

    public String getAnioVotacion() {
        return anioVotacion;
    }

    /**
     * Establece el año de votación, tomando los últimos dos dígitos del año proporcionado.
     *
     * @param anioVotacion El año de votación.
     */
    public void setAnioVotacion(@NotNull String anioVotacion) {
        this.anioVotacion = anioVotacion.substring(anioVotacion.length() - 2);
    }

    @Override
    public String toString() {
        return "Propuesta Calendario Comite";
    }

    /**
     * Comprueba si el día proporcionado está dentro del rango válido (1 a 31).
     *
     * @param diaMes El día a comprobar.
     * @return El día proporcionado si está dentro del rango válido.
     * @throws IllegalArgumentException Si el día no es un número válido o está fuera del rango permitido.
     */

    public String comprobarDia(String diaMes) {
        try {
            // Intenta convertir el día a un número entero
            int dia = Integer.parseInt(diaMes);

            // Comprueba si el día está en el rango válido
            if (dia < Constantes.PRIMER_DIA_MES || dia > Constantes.ULTIMO_DIA_MES) {
                // Maneja la lógica en caso de que el día esté fuera del rango
                throw new IllegalArgumentException("El día está fuera del rango válido.");
            }

            return diaMes;
        } catch (NumberFormatException e) {
            // Maneja la excepción en caso de que la conversión a entero falle
            throw new IllegalArgumentException("El día no es un número válido.");
        }
    }

    /**
     * Valida y obtiene el nombre del mes a partir de un valor numérico o un nombre de mes.
     *
     * @param mes El mes a validar y obtener.
     * @return El nombre del mes si es válido.
     * @throws IllegalArgumentException Si el mes no es un número ni un nombre de mes válido.
     */
    public String validarYObtenerMes(String mes) {
        try {
            // Intenta convertir el mes a un número entero
            int meses = Integer.parseInt(mes);

            // Comprueba si el mes está en el rango válido
            if (meses < Constantes.PRIMER_MES || meses > Constantes.ULTIMO_MES) {
                // Maneja la lógica en caso de que el mes esté fuera del rango
                throw new IllegalArgumentException("El mes está fuera del rango válido.");
            }

            // Si el mes es un número, devuelve el valor original
            return mes;
        } catch (NumberFormatException e) {
            // Si no se puede convertir a entero, busca el nombre del mes en el enum
            for (Meses month : Meses.values()) {
                if (mes.equalsIgnoreCase(month.toString())) {
                    // Devuelve el nombre del mes encontrado
                    return month.toString();
                }
            }

            // Si no se encuentra el nombre del mes en el enum, lanza una excepción
            throw new IllegalArgumentException("El mes no es un número ni un nombre de mes válido.");
        }
    }
}
