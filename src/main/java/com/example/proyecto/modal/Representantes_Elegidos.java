package com.example.proyecto.modal;

import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.MessageManager;
import com.example.proyecto.util.ValidadorCampos;

import java.util.Date;

/**
 * Clase que representa a los representantes elegidos.
 * Contiene información sobre el representante, como iniciales, apellidos, DNI, antigüedad, sexo, fecha de nacimiento, votos y sindicato.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class Representantes_Elegidos {

    private char altaBaja;
    private String inicial;
    private String apellidos;
    private String dni;
    private String antiguedad;
    private char sexo;
    private Date fechaNacimiento;
    private int votos;
    private String sindicato;

    /**
     * Constructor que inicializa un representante elegido con los datos necesarios.
     *
     * @param inicial          Las iniciales del representante.
     * @param apellidos        Los apellidos del representante.
     * @param dni              El DNI del representante.
     * @param antiguedad       La antigüedad del representante en años.
     * @param sexo             El sexo del representante.
     * @param fechaNacimiento  La fecha de nacimiento del representante.
     * @param votos            La cantidad de votos obtenidos.
     * @param sindicato        El sindicato al que pertenece.
     * @throws CumplimentarPDFException Si algún dato es incorrecto.
     */
    public Representantes_Elegidos(String inicial, String apellidos, String dni, String antiguedad, char sexo,
                                   Date fechaNacimiento, int votos, String sindicato) throws CumplimentarPDFException {
        setInicial(inicial);
        this.apellidos = apellidos;
        setDni(dni);
        setAntiguedad(antiguedad);
        setSexo(sexo);
        setFechaNacimiento(fechaNacimiento);
        this.votos = votos;
        this.sindicato = sindicato;
    }

    /**
     * Constructor que inicializa un representante elegido con los datos necesarios incluyendo estado de alta o baja.
     *
     * @param altaBaja         Indica si el representante está de alta (A) o baja (B).
     * @param inicial          Las iniciales del representante.
     * @param apellidos        Los apellidos del representante.
     * @param dni              El DNI del representante.
     * @param antiguedad       La antigüedad del representante en años.
     * @param sexo             El sexo del representante.
     * @param fechaNacimiento  La fecha de nacimiento del representante.
     * @param votos            La cantidad de votos obtenidos.
     * @param sindicato        El sindicato al que pertenece.
     * @throws CumplimentarPDFException Si algún dato es incorrecto.
     */
    public Representantes_Elegidos(char altaBaja, String inicial, String apellidos, String dni, String antiguedad, char sexo, Date fechaNacimiento, int votos, String sindicato) throws CumplimentarPDFException {
        setAltaBaja(altaBaja);
        setInicial(inicial);
        this.apellidos = apellidos;
        setDni(dni);
        setAntiguedad(antiguedad);
        setSexo(sexo);
        setFechaNacimiento(fechaNacimiento);
        this.votos = votos;
        this.sindicato = sindicato;
    }

    public char getAltaBaja() {
        return altaBaja;
    }

    public void setAltaBaja(char altaBaja) throws CumplimentarPDFException {
        if (altaBaja != 'A' && altaBaja != 'B') {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.altaBaja.incorrecto"));
        }
        this.altaBaja = altaBaja;
    }

    public String getInicial() {
        return inicial;
    }

    public void setInicial(String inicial) throws CumplimentarPDFException {
        if (inicial.length() > 2 || inicial.isEmpty()) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.inicial.incorrecto"));
        }
        this.inicial = inicial;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) throws CumplimentarPDFException {
        if (!ValidadorCampos.verificarDNI(dni)) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.dni.incorrecto"));
        }
        this.dni = dni;
    }

    public String getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(String antiguedad) throws CumplimentarPDFException {
        if (antiguedad.length() > 3 || antiguedad.isEmpty() || Integer.parseInt(antiguedad) < 3) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.antiguedad.incorrecto"));
        }
        this.antiguedad = antiguedad;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) throws CumplimentarPDFException {
        if (sexo != 'H' && sexo != 'M' && sexo != 'V' && sexo != 'F') {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.sexo.incorrecto"));
        }
        this.sexo = sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) throws CumplimentarPDFException {
        if (fechaNacimiento == null) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.fechaNacimiento.incorrecto"));
        }
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }

    public String getSindicato() {
        return sindicato;
    }

    public void setSindicato(String sindicato) {
        this.sindicato = sindicato;
    }
}
