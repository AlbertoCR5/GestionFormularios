package com.example.proyecto.modal;

import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.MessageManager;
import com.example.proyecto.util.ValidadorFecha;
import com.example.proyecto.util.ConversorFechaToLetras;

/**
 * Clase que representa una salida sindical completa.
 * Contiene información sobre la comunicación de la salida y la fecha de salida, además de los detalles de la empresa y del delegado.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class Salida_Sindical_Completa {

    private String fechaComunicacion;
    private String empresa;
    private String nombreCompletoDelegado;
    private String fechaSalida;
    private String diaComunicacion;
    private String mesComunicacion;
    private String anioComunicacion;
    private String diaSalida;
    private String mesSalida;
    private String anioSalida;

    private final ValidadorFecha validarFecha = new ValidadorFecha();

    /**
     * Constructor por defecto.
     */
    public Salida_Sindical_Completa() {
    }

    /**
     * Constructor que inicializa una salida sindical completa con los datos necesarios.
     *
     * @param fechaComunicacion      La fecha de comunicación.
     * @param empresa                El nombre de la empresa.
     * @param nombreCompletoDelegado El nombre completo del delegado.
     * @param fechaSalida            La fecha de salida.
     * @throws CumplimentarPDFException Si hay un error en el formato de las fechas.
     */
    public Salida_Sindical_Completa(String fechaComunicacion, String empresa, String nombreCompletoDelegado, String fechaSalida) throws CumplimentarPDFException {
        setFechaComunicacion(fechaComunicacion);
        this.empresa = empresa;
        this.nombreCompletoDelegado = nombreCompletoDelegado;
        setFechaSalida(fechaSalida);
    }

    public String getFechaComunicacion() {
        return fechaComunicacion;
    }

    public void setFechaComunicacion(String fechaComunicacion) throws CumplimentarPDFException {
        if (ValidadorFecha.esFormatoFechaNoValido(fechaComunicacion)) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.fecha.comunicacion.incorrecta").concat(String.valueOf(Constantes.FORMATO_FECHA)).concat("\n"));
        }
        this.fechaComunicacion = fechaComunicacion;
        actualizarCamposFecha(fechaComunicacion, true);
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getNombreCompletoDelegado() {
        return nombreCompletoDelegado;
    }

    public void setNombreCompletoDelegado(String nombreCompletoDelegado) {
        this.nombreCompletoDelegado = nombreCompletoDelegado;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) throws CumplimentarPDFException {
        if (ValidadorFecha.esFormatoFechaNoValido(fechaSalida)) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.fecha.salida.incorrecta").concat(String.valueOf(Constantes.FORMATO_FECHA)).concat("\n"));
        }
        this.fechaSalida = fechaSalida;
        actualizarCamposFecha(fechaSalida, false);
    }

    public String getDiaComunicacion() {
        return diaComunicacion;
    }

    public String getMesComunicacion() {
        return mesComunicacion;
    }

    public String getAnioComunicacion() {
        return anioComunicacion;
    }

    public String getDiaSalida() {
        return diaSalida;
    }

    public String getMesSalida() {
        return mesSalida;
    }

    public String getAnioSalida() {
        return anioSalida;
    }

    /**
     * Actualiza los campos de día, mes y año basados en la fecha proporcionada.
     *
     * @param fecha La fecha en formato dd/MM/yyyy.
     * @param esComunicacion Indica si la fecha es de comunicación (true) o de salida (false).
     */
    private void actualizarCamposFecha(String fecha, boolean esComunicacion) {
        String[] partes = fecha.split("/");
        String dia = partes[0];
        String mes = partes[1].startsWith("0") ? partes[1].substring(1) : partes[1];
        String anio = partes[2];

        if (esComunicacion) {
            this.diaComunicacion = dia;
            this.mesComunicacion = ConversorFechaToLetras.convertirMesALetras(mes);
            this.anioComunicacion = anio;
        } else {
            this.diaSalida = dia;
            this.mesSalida = ConversorFechaToLetras.convertirMesALetras(mes);
            this.anioSalida = anio;
        }
    }

    @Override
    public String toString() {
        return "Salida_Sindical_Completa{" +
                "fechaComunicacion='" + fechaComunicacion + '\'' +
                ", empresa='" + empresa + '\'' +
                ", nombreCompletoDelegado='" + nombreCompletoDelegado + '\'' +
                ", fechaSalida='" + fechaSalida + '\'' +
                '}';
    }
}
