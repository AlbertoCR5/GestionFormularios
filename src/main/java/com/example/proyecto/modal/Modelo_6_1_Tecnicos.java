package com.example.proyecto.modal;

import com.example.proyecto.util.ConversorFechaToLetras;
import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.MessageManager;

/**
 * Clase que representa el modelo 6.1 para técnicos en el proceso de escrutinio.
 * Extiende de Modelo_4_Tecnicos y añade propiedades específicas.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class Modelo_6_1_Tecnicos extends Modelo_4_Tecnicos {

    private int representantesElegibles;
    private String fechaConstitucionLetras;
    private String fechaVotacion;
    private String fechaVotacionLetras;
    private int totalVotantes;
    private int varonesVotantes;
    private int mujeresVotantes;
    private int papeletasCumplimentadas;
    private int papeletasBlancas;
    private int nulos;
    private int totalPapeletas;

    /**
     * Constructor vacío.
     */
    public Modelo_6_1_Tecnicos() {
    }

    /**
     * Constructor con parámetros iniciales.
     *
     * @param colegio El nombre del colegio.
     * @param nombreEmpresa El nombre de la empresa.
     * @param CIF El CIF de la empresa.
     * @param nombreComercial El nombre comercial de la empresa.
     * @param nombreCentro El nombre del centro.
     * @param direccion La dirección del centro.
     * @param municipio El municipio del centro.
     * @param provincia La provincia del centro.
     * @param promotores Los promotores del proceso.
     * @param fechaConstitucion La fecha de constitución.
     * @param municipioElecciones El municipio de las elecciones.
     * @param dia El día de las elecciones.
     * @param mes El mes de las elecciones.
     * @param anioFormateado El año de las elecciones.
     * @throws CumplimentarPDFException Si algún dato es incorrecto.
     */
    public Modelo_6_1_Tecnicos(String colegio, String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro,
                               String direccion, String municipio, String provincia, String promotores, String fechaConstitucion,
                               String municipioElecciones, String dia, String mes, String anioFormateado) throws CumplimentarPDFException {
        super(colegio, nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores, fechaConstitucion, municipioElecciones, dia, mes, anioFormateado);
        setFechaConstitucion(fechaConstitucion);
    }

    /**
     * Constructor con fecha de votación.
     *
     * @param colegio El nombre del colegio.
     * @param nombreEmpresa El nombre de la empresa.
     * @param CIF El CIF de la empresa.
     * @param nombreComercial El nombre comercial de la empresa.
     * @param nombreCentro El nombre del centro.
     * @param direccion La dirección del centro.
     * @param municipio El municipio del centro.
     * @param provincia La provincia del centro.
     * @param promotores Los promotores del proceso.
     * @param fechaConstitucion La fecha de constitución.
     * @param fechaVotacion La fecha de votación.
     * @param municipioElecciones El municipio de las elecciones.
     * @param dia El día de las elecciones.
     * @param mes El mes de las elecciones.
     * @param anioFormateado El año de las elecciones.
     * @throws CumplimentarPDFException Si algún dato es incorrecto.
     */
    public Modelo_6_1_Tecnicos(String colegio, String nombreEmpresa, String CIF, String nombreComercial, String nombreCentro,
                               String direccion, String municipio, String provincia, String promotores, String fechaConstitucion, String fechaVotacion,
                               String municipioElecciones, String dia, String mes, String anioFormateado) throws CumplimentarPDFException {
        super(colegio, nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores, fechaConstitucion, municipioElecciones, dia, mes, anioFormateado);
        setFechaVotacion(fechaVotacion);
    }

    /**
     * Constructor completo sin votación.
     *
     * @param numeroMesa El número de la mesa.
     * @param colegio El nombre del colegio.
     * @param nombreEmpresa El nombre de la empresa.
     * @param CIF El CIF de la empresa.
     * @param nombreComercial El nombre comercial de la empresa.
     * @param nombreCentro El nombre del centro.
     * @param direccion La dirección del centro.
     * @param municipio El municipio del centro.
     * @param provincia La provincia del centro.
     * @param promotores Los promotores del proceso.
     * @param fechaConstitucion La fecha de constitución.
     * @param municipioElecciones El municipio de las elecciones.
     * @param horas Las horas de las elecciones.
     * @param dia El día de las elecciones.
     * @param mes El mes de las elecciones.
     * @param anioFormateado El año de las elecciones.
     * @param presidente El nombre del presidente.
     * @param vocal El nombre del vocal.
     * @param secretario El nombre del secretario.
     * @param dniPresidente El DNI del presidente.
     * @param dniVocal El DNI del vocal.
     * @param dniSecretario El DNI del secretario.
     * @throws CumplimentarPDFException Si algún dato es incorrecto.
     */
    public Modelo_6_1_Tecnicos(String numeroMesa, String colegio, String nombreEmpresa, String CIF, String nombreComercial,
                               String nombreCentro, String direccion, String municipio, String provincia, String promotores,
                               String fechaConstitucion, String municipioElecciones, String horas, String dia, String mes,
                               String anioFormateado, String presidente, String vocal, String secretario, String dniPresidente,
                               String dniVocal, String dniSecretario) throws CumplimentarPDFException {
        super(numeroMesa, colegio, nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores,
                fechaConstitucion, municipioElecciones, horas, dia, mes, anioFormateado, presidente, vocal, secretario, dniPresidente, dniVocal, dniSecretario);
        setFechaConstitucion(fechaConstitucion);
        setFechaConstitucionLetras(fechaConstitucion);
    }

    /**
     * Constructor completo con votación.
     *
     * @param numeroMesa El número de la mesa.
     * @param colegio El nombre del colegio.
     * @param nombreEmpresa El nombre de la empresa.
     * @param CIF El CIF de la empresa.
     * @param nombreComercial El nombre comercial de la empresa.
     * @param nombreCentro El nombre del centro.
     * @param direccion La dirección del centro.
     * @param municipio El municipio del centro.
     * @param provincia La provincia del centro.
     * @param promotores Los promotores del proceso.
     * @param fechaConstitucion La fecha de constitución.
     * @param fechaVotacion La fecha de votación.
     * @param municipioElecciones El municipio de las elecciones.
     * @param horas Las horas de las elecciones.
     * @param dia El día de las elecciones.
     * @param mes El mes de las elecciones.
     * @param anioFormateado El año de las elecciones.
     * @param presidente El nombre del presidente.
     * @param vocal El nombre del vocal.
     * @param secretario El nombre del secretario.
     * @param dniPresidente El DNI del presidente.
     * @param dniVocal El DNI del vocal.
     * @param dniSecretario El DNI del secretario.
     * @throws CumplimentarPDFException Si algún dato es incorrecto.
     */
    public Modelo_6_1_Tecnicos(String numeroMesa, String colegio, String nombreEmpresa, String CIF, String nombreComercial,
                               String nombreCentro, String direccion, String municipio, String provincia, String promotores,
                               String fechaConstitucion, String fechaVotacion, String municipioElecciones, String horas, String dia, String mes,
                               String anioFormateado, String presidente, String vocal, String secretario, String dniPresidente,
                               String dniVocal, String dniSecretario) throws CumplimentarPDFException {
        super(numeroMesa, colegio, nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, promotores,
                fechaConstitucion, municipioElecciones, horas, dia, mes, anioFormateado, presidente, vocal, secretario, dniPresidente, dniVocal, dniSecretario);
        setFechaVotacion(fechaVotacion);
        setFechaConstitucionLetras(fechaConstitucion);
        setFechaVotacionLetras(fechaVotacionLetras);
    }

    /**
     * Constructor completo con todos los parámetros.
     *
     * @param numeroMesa El número de la mesa.
     * @param colegio El nombre del colegio.
     * @param nombreEmpresa El nombre de la empresa.
     * @param CIF El CIF de la empresa.
     * @param nombreComercial El nombre comercial de la empresa.
     * @param nombreCentro El nombre del centro.
     * @param direccion La dirección del centro.
     * @param municipio El municipio del centro.
     * @param provincia La provincia del centro.
     * @param totalElectores El total de electores.
     * @param varonesElectores El total de varones electores.
     * @param mujeresElectoras El total de mujeres electoras.
     * @param promotores Los promotores del proceso.
     * @param fechaConstitucion La fecha de constitución.
     * @param municipioElecciones El municipio de las elecciones.
     * @param horas Las horas de las elecciones.
     * @param dia El día de las elecciones.
     * @param mes El mes de las elecciones.
     * @param anioFormateado El año de las elecciones.
     * @param presidente El nombre del presidente.
     * @param vocal El nombre del vocal.
     * @param secretario El nombre del secretario.
     * @param dniPresidente El DNI del presidente.
     * @param dniVocal El DNI del vocal.
     * @param dniSecretario El DNI del secretario.
     * @param representantesElegibles El número de representantes elegibles.
     * @param fechaConstitucionLetras La fecha de constitución en letras.
     * @param fechaVotacion La fecha de votación.
     * @param fechaVotacionLetras La fecha de votación en letras.
     * @param totalVotantes El total de votantes.
     * @param varonesVotantes El total de varones votantes.
     * @param mujeresVotantes El total de mujeres votantes.
     * @param papeletasCumplimentadas El total de papeletas cumplimentadas.
     * @param papeletasBlancas El total de papeletas en blanco.
     * @param nulos El total de votos nulos.
     * @param totalPapeletas El total de papeletas.
     * @throws CumplimentarPDFException Si algún dato es incorrecto.
     */
    public Modelo_6_1_Tecnicos(String numeroMesa, String colegio, String nombreEmpresa, String CIF, String nombreComercial,
                               String nombreCentro, String direccion, String municipio, String provincia, int totalElectores,
                               int varonesElectores, int mujeresElectoras, String promotores, String fechaConstitucion,
                               String municipioElecciones, String horas, String dia, String mes, String anioFormateado,
                               String presidente, String vocal, String secretario, String dniPresidente, String dniVocal,
                               String dniSecretario, int representantesElegibles, String fechaConstitucionLetras, String fechaVotacion,
                               String fechaVotacionLetras, int totalVotantes, int varonesVotantes, int mujeresVotantes,
                               int papeletasCumplimentadas, int papeletasBlancas, int nulos, int totalPapeletas) throws CumplimentarPDFException {
        super(numeroMesa, colegio, nombreEmpresa, CIF, nombreComercial, nombreCentro, direccion, municipio, provincia, totalElectores,
                varonesElectores, mujeresElectoras, promotores, fechaConstitucion, municipioElecciones, horas, dia, mes, anioFormateado,
                presidente, vocal, secretario, dniPresidente, dniVocal, dniSecretario);
        setRepresentantesElegibles(representantesElegibles);
        setFechaConstitucionLetras(fechaConstitucion);
        setFechaVotacion(fechaVotacion);
        setFechaVotacionLetras(fechaVotacionLetras);
        setTotalVotantes(totalVotantes);
        setVaronesVotantes(varonesVotantes);
        setMujeresVotantes(mujeresVotantes);
        setPapeletasCumplimentadas(papeletasCumplimentadas);
        setPapeletasBlancas(papeletasBlancas);
        setNulos(nulos);
        setTotalPapeletas(totalPapeletas);
    }

    public int getRepresentantesElegibles() {
        return representantesElegibles;
    }

    public void setRepresentantesElegibles(int representantesElegibles) {

        if (getTotalElectores() < 101) {
            this.representantesElegibles = 5;
        } else if (getTotalElectores() < 251) {
            this.representantesElegibles = 9;
        } else if (getTotalElectores() < 501) {
            this.representantesElegibles = 13;
        } else if (getTotalElectores() < 751) {
            this.representantesElegibles = 17;
        } else if (getTotalElectores() < 1001) {
            this.representantesElegibles = 21;
        } else {
            // Cálculo para los siguientes bloques de 1000 electores
            int bloquesAdicionales = (getTotalElectores() - 1000) / 1000;
            this.representantesElegibles = Math.min(75, 21 + bloquesAdicionales * 2);
        }
    }

    public String getFechaConstitucionLetras() {
        return fechaConstitucionLetras;
    }

    public void setFechaConstitucionLetras(String fechaConstitucion) {
        this.fechaConstitucionLetras = ConversorFechaToLetras.convertirFechaCompleta(fechaConstitucion);
    }

    public String getFechaVotacion() {
        return fechaVotacion;
    }

    public void setFechaVotacion(String fechaVotacion) throws CumplimentarPDFException {
        if (!validarFecha.esFormatoFechaValido(fechaVotacion)) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.fecha.escrutinio.incorrecto"));
        }
        String[] partes = fechaVotacion.split("/");
        String dia = partes[0].length() == 1 ? "0" + partes[0] : partes[0];
        String mes = partes[1].length() == 1 ? "0" + partes[1] : partes[1];
        String anio = partes[2];
        this.fechaVotacion = dia + "/" + mes + "/" + anio;
        setFechaVotacionLetras(fechaVotacion);
    }

    public String getFechaVotacionLetras() {
        return fechaVotacionLetras;
    }

    public void setFechaVotacionLetras(String fechaVotacion) {
        this.fechaVotacionLetras = ConversorFechaToLetras.convertirFechaCompleta(fechaVotacion);
    }

    public int getTotalVotantes() {
        return totalVotantes;
    }

    public void setTotalVotantes(int totalVotantes) throws CumplimentarPDFException {
        if (totalVotantes > getTotalElectores()) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.total.votantes.incorrecto").concat(" -->" + getTotalElectores()));
        }
        this.totalVotantes = totalVotantes;
    }

    public int getVaronesVotantes() {
        return varonesVotantes;
    }

    public void setVaronesVotantes(int varonesVotantes) throws CumplimentarPDFException {
        if (varonesVotantes > totalVotantes) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.varones.votantes.incorrecto"));
        }
        this.varonesVotantes = varonesVotantes;
    }

    public int getMujeresVotantes() {
        return mujeresVotantes;
    }

    public void setMujeresVotantes(int mujeresVotantes) {
        this.mujeresVotantes = totalVotantes - varonesVotantes;
    }

    public int getPapeletasCumplimentadas() {
        return papeletasCumplimentadas;
    }

    public void setPapeletasCumplimentadas(int papeletasCumplimentadas) throws CumplimentarPDFException {
        if (papeletasCumplimentadas > totalVotantes) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.papeletas.cumplimentadas.incorrecto"));
        }
        this.papeletasCumplimentadas = papeletasCumplimentadas;
    }

    public int getPapeletasBlancas() {
        return papeletasBlancas;
    }

    public void setPapeletasBlancas(int papeletasBlancas) throws CumplimentarPDFException {
        if (papeletasBlancas > totalVotantes) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.papeletas.blancas.incorrecto"));
        }
        this.papeletasBlancas = papeletasBlancas;
    }

    public int getNulos() {
        return nulos;
    }

    public void setNulos(int nulos) throws CumplimentarPDFException {
        if (nulos > totalVotantes) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.papeletas.nulas.incorrecto"));
        }
        this.nulos = nulos;
    }

    public int getTotalPapeletas() {
        return totalPapeletas;
    }

    public void setTotalPapeletas(int totalPapeletas) throws CumplimentarPDFException {
        if (totalPapeletas != totalVotantes) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.total.papeletas.incorrecto"));
        }
        this.totalPapeletas = papeletasCumplimentadas + papeletasBlancas + nulos;
    }
}
