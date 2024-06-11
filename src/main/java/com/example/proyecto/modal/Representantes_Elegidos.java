package com.example.proyecto.modal;

import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.ValidadorCampos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Representantes_Elegidos {

    ValidadorCampos verificarDNI = new ValidadorCampos();
    private char altaBaja;
    private String inicial;
    private String apellidos;
    private String dni;
    private String antiguedad;
    private char sexo;
    private Date fechaNacimiento;
    private int votos;
    private String sindicato;

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

    public void setAltaBaja(char altaBaja) {

        if (altaBaja != 'A' && altaBaja != 'B'){
            this.altaBaja = 'A';
        }
        this.altaBaja = altaBaja;
    }

    public String getInicial() {
        return inicial;
    }

    public void setInicial(String inicial) throws CumplimentarPDFException {
        if (inicial.length() > 2 || inicial.isEmpty()){
            throw new CumplimentarPDFException("ERROR, iniciales introducidas no válidas");
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

        if (!verificarDNI.verificarDNI(dni)){
            throw new CumplimentarPDFException("ERROR, DNI introducido incorrecto");
        }
        this.dni = dni;
    }

    public String getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(String antiguedad) throws CumplimentarPDFException {
        if (antiguedad.length() > 3 || antiguedad.isEmpty() || Integer.parseInt(antiguedad) < 3){
            throw new CumplimentarPDFException("ERROR, antigüedad introducuda incorrecta");
        }
        this.antiguedad = antiguedad;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {

        if (sexo != 'H' && sexo != 'M' && sexo != 'V' && sexo != 'F'){
            this.altaBaja = '-';
        }
        this.sexo = sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {

        if (fechaNacimiento == null) {
            this.fechaNacimiento = null;
            return;
        }

        SimpleDateFormat formatoFecha = new SimpleDateFormat(Constantes.FORMATO_FECHA);

        try {
            this.fechaNacimiento = formatoFecha.parse(fechaNacimiento.toString());
        } catch (ParseException e) {
            throw new IllegalArgumentException("El formato de la fecha de constitución es incorrecto. -->".concat(Constantes.FORMATO_FECHA.concat("\n")));
        }
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
