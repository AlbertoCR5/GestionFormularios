package com.example.proyecto.modal;

import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.MessageManager;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que representa el modelo 7.3 de proceso de escrutinio.
 * Contiene información sobre las candidaturas, los electores, votantes y representantes.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class Modelo_7_3_Proceso {

    private List<Candidatura_Comite> candidaturasTecnicos;
    private List<Candidatura_Comite> candidaturasEspecialistas;

    private String preaviso;
    private String dia;
    private String mes;
    private String anio;
    private int totalElectores;
    private int electoresVaronesTecnicos;
    private int electoresMujeresTecnicos;
    private int totalElectoresTecnicos;
    private int numeroRepresentantesTecnicos;
    private int votantesVaronesTecnicos;
    private int votantesMujeresTecnicos;
    private int totalVotantesTecnicos;
    private int papeletasCumplimentadasTecnicos;
    private int papeletasBlancasTecnicos;
    private int nulosTecnicos;
    private int representantesElegidosTecnicos;
    private int electoresVaronesEspecialistas;
    private int electoresMujeresEspecialistas;
    private int totalElectoresEspecialistas;
    private int numeroRepresentantesEspecialistas;
    private int votantesVaronesEspecialistas;
    private int votantesMujeresEspecialistas;
    private int totalVotantesEspecialistas;
    private int papeletasCumplimentadasEspecialistas;
    private int papeletasBlancasEspecialistas;
    private int nulosEspecialistas;
    private int representantesElegidosEspecialistas;
    private int totalRepresentantesElegidos;

    /**
     * Constructor vacío.
     */
    public Modelo_7_3_Proceso() {
    }

    /**
     * Constructor con preaviso.
     *
     * @param preaviso El preaviso del proceso.
     */
    public Modelo_7_3_Proceso(String preaviso) {
        this.preaviso = preaviso;
    }

    /**
     * Constructor con parámetros básicos.
     *
     * @param preaviso El preaviso del proceso.
     * @param dia El día del proceso.
     * @param mes El mes del proceso.
     * @param anio El año del proceso.
     * @param totalElectores El total de electores.
     * @param electoresVaronesTecnicos El número de electores varones técnicos.
     * @param electoresMujeresTecnicos El número de electores mujeres técnicos.
     * @param totalElectoresTecnicos El total de electores técnicos.
     * @param numeroRepresentantesTecnicos El número de representantes técnicos.
     * @param electoresVaronesEspecialistas El número de electores varones especialistas.
     * @param electoresMujeresEspecialistas El número de electores mujeres especialistas.
     * @param totalElectoresEspecialistas El total de electores especialistas.
     * @param numeroRepresentantesEspecialistas El número de representantes especialistas.
     * @throws CumplimentarPDFException Si algún dato es incorrecto.
     */
    public Modelo_7_3_Proceso(String preaviso, String dia, String mes, String anio, int totalElectores, int electoresVaronesTecnicos,
                              int electoresMujeresTecnicos, int totalElectoresTecnicos, int numeroRepresentantesTecnicos,
                              int electoresVaronesEspecialistas, int electoresMujeresEspecialistas, int totalElectoresEspecialistas,
                              int numeroRepresentantesEspecialistas) throws CumplimentarPDFException {
        setPreaviso(preaviso);
        setDia(dia);
        setMes(mes);
        setAnio(anio);
        setTotalElectores(totalElectores);
        setElectoresVaronesTecnicos(electoresVaronesTecnicos);
        setElectoresMujeresTecnicos(electoresMujeresTecnicos);
        setTotalElectoresTecnicos(totalElectoresTecnicos);
        setNumeroRepresentantesTecnicos(numeroRepresentantesTecnicos);
        setElectoresVaronesEspecialistas(electoresVaronesEspecialistas);
        setElectoresMujeresEspecialistas(electoresMujeresEspecialistas);
        setTotalElectoresEspecialistas(totalElectoresEspecialistas);
        setNumeroRepresentantesEspecialistas(numeroRepresentantesEspecialistas);
    }

    /**
     * Constructor con todos los parámetros.
     *
     * @param candidaturasTecnicos Lista de candidaturas de técnicos.
     * @param candidaturasEspecialistas Lista de candidaturas de especialistas.
     * @param preaviso El preaviso del proceso.
     * @param dia El día del proceso.
     * @param mes El mes del proceso.
     * @param anio El año del proceso.
     * @param totalElectores El total de electores.
     * @param electoresVaronesTecnicos El número de electores varones técnicos.
     * @param electoresMujeresTecnicos El número de electores mujeres técnicos.
     * @param totalElectoresTecnicos El total de electores técnicos.
     * @param numeroRepresentantesTecnicos El número de representantes técnicos.
     * @param votantesVaronesTecnicos El número de votantes varones técnicos.
     * @param votantesMujeresTecnicos El número de votantes mujeres técnicos.
     * @param totalVotantesTecnicos El total de votantes técnicos.
     * @param papeletasCumplimentadasTecnicos El número de papeletas cumplimentadas de técnicos.
     * @param papeletasBlancasTecnicos El número de papeletas blancas de técnicos.
     * @param nulosTecnicos El número de votos nulos de técnicos.
     * @param representantesElegidosTecnicos El número de representantes elegidos de técnicos.
     * @param electoresVaronesEspecialistas El número de electores varones especialistas.
     * @param electoresMujeresEspecialistas El número de electores mujeres especialistas.
     * @param totalElectoresEspecialistas El total de electores especialistas.
     * @param numeroRepresentantesEspecialistas El número de representantes especialistas.
     * @param votantesVaronesEspecialistas El número de votantes varones especialistas.
     * @param votantesMujeresEspecialistas El número de votantes mujeres especialistas.
     * @param totalVotantesEspecialistas El total de votantes especialistas.
     * @param papeletasCumplimentadasEspecialistas El número de papeletas cumplimentadas de especialistas.
     * @param papeletasBlancasEspecialistas El número de papeletas blancas de especialistas.
     * @param nulosEspecialistas El número de votos nulos de especialistas.
     * @param representantesElegidosEspecialistas El número de representantes elegidos de especialistas.
     * @param totalRepresentantesElegidos El total de representantes elegidos.
     * @throws CumplimentarPDFException Si algún dato es incorrecto.
     */
    public Modelo_7_3_Proceso(List<Candidatura_Comite> candidaturasTecnicos, List<Candidatura_Comite> candidaturasEspecialistas,
                              String preaviso, String dia, String mes, String anio, int totalElectores, int electoresVaronesTecnicos,
                              int electoresMujeresTecnicos, int totalElectoresTecnicos, int numeroRepresentantesTecnicos,
                              int votantesVaronesTecnicos, int votantesMujeresTecnicos, int totalVotantesTecnicos,
                              int papeletasCumplimentadasTecnicos, int papeletasBlancasTecnicos, int nulosTecnicos,
                              int representantesElegidosTecnicos, int electoresVaronesEspecialistas, int electoresMujeresEspecialistas,
                              int totalElectoresEspecialistas, int numeroRepresentantesEspecialistas, int votantesVaronesEspecialistas,
                              int votantesMujeresEspecialistas, int totalVotantesEspecialistas, int papeletasCumplimentadasEspecialistas,
                              int papeletasBlancasEspecialistas, int nulosEspecialistas, int representantesElegidosEspecialistas, int totalRepresentantesElegidos) throws CumplimentarPDFException {
        this.candidaturasTecnicos = candidaturasTecnicos;
        this.candidaturasEspecialistas = candidaturasEspecialistas;
        setPreaviso(preaviso);
        setDia(dia);
        setMes(mes);
        setAnio(anio);
        setTotalElectores(totalElectores);
        setElectoresVaronesTecnicos(electoresVaronesTecnicos);
        setElectoresMujeresTecnicos(electoresMujeresTecnicos);
        setTotalElectoresTecnicos(totalElectoresTecnicos);
        setNumeroRepresentantesTecnicos(numeroRepresentantesTecnicos);
        setVotantesVaronesTecnicos(votantesVaronesTecnicos);
        setVotantesMujeresTecnicos(votantesMujeresTecnicos);
        setPapeletasCumplimentadasTecnicos(papeletasCumplimentadasTecnicos);
        setPapeletasBlancasTecnicos(papeletasBlancasTecnicos);
        setNulosTecnicos(nulosTecnicos);
        setRepresentantesElegidosTecnicos(representantesElegidosTecnicos);
        setElectoresVaronesEspecialistas(electoresVaronesEspecialistas);
        setElectoresMujeresEspecialistas(electoresMujeresEspecialistas);
        setTotalElectoresEspecialistas(totalElectoresEspecialistas);
        setNumeroRepresentantesEspecialistas(numeroRepresentantesEspecialistas);
        setVotantesVaronesEspecialistas(votantesVaronesEspecialistas);
        setVotantesMujeresEspecialistas(votantesMujeresEspecialistas);
        setPapeletasCumplimentadasEspecialistas(papeletasCumplimentadasEspecialistas);
        setPapeletasBlancasEspecialistas(papeletasBlancasEspecialistas);
        setNulosEspecialistas(nulosEspecialistas);
        setRepresentantesElegidosEspecialistas(representantesElegidosEspecialistas);
        setTotalRepresentantesElegidos(totalRepresentantesElegidos);
    }

    public List<Candidatura_Comite> getCandidaturasTecnicos() {
        return candidaturasTecnicos;
    }

    public void setCandidaturasTecnicos(List<Candidatura_Comite> candidaturasTecnicos) {
        this.candidaturasTecnicos = candidaturasTecnicos;
    }

    public List<Candidatura_Comite> getCandidaturasEspecialistas() {
        return candidaturasEspecialistas;
    }

    public void setCandidaturasEspecialistas(List<Candidatura_Comite> candidaturasEspecialistas) {
        this.candidaturasEspecialistas = candidaturasEspecialistas;
    }

    public String getPreaviso() {
        return preaviso;
    }

    public void setPreaviso(String preaviso) throws CumplimentarPDFException {
        Pattern patron = Pattern.compile("^([0-9]{1,4})/([0-9]{4})$");
        Matcher matcher = patron.matcher(preaviso);

        if (!matcher.matches()) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.preaviso.invalido"));
        }
        this.preaviso = preaviso;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public int getTotalElectores() {
        return totalElectores;
    }

    public void setTotalElectores(int totalElectores) {
        this.totalElectores = totalElectores;
    }

    public int getElectoresVaronesTecnicos() {
        return electoresVaronesTecnicos;
    }

    public void setElectoresVaronesTecnicos(int electoresVaronesTecnicos) throws CumplimentarPDFException {
        if (electoresVaronesTecnicos > totalElectores) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.varones.electores.tecnicos.superior"));
        }
        this.electoresVaronesTecnicos = electoresVaronesTecnicos;
    }

    public int getElectoresMujeresTecnicos() {
        return electoresMujeresTecnicos;
    }

    public void setElectoresMujeresTecnicos(int electoresMujeresTecnicos) throws CumplimentarPDFException {
        if (electoresMujeresTecnicos > totalElectores) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.mujeres.electores.tecnicos.superior"));
        }
        this.electoresMujeresTecnicos = electoresMujeresTecnicos;
    }

    public int getTotalElectoresTecnicos() {
        return totalElectoresTecnicos;
    }

    public void setTotalElectoresTecnicos(int totalElectoresTecnicos) {
        this.totalElectoresTecnicos = electoresVaronesTecnicos + electoresMujeresTecnicos;
    }

    public int getNumeroRepresentantesTecnicos() {
        return numeroRepresentantesTecnicos;
    }

    public void setNumeroRepresentantesTecnicos(int numeroRepresentantesTecnicos) {
        this.numeroRepresentantesTecnicos = calcularNumeroRepresentantes();
    }

    /**
     * Calcula el número de representantes elegibles basado en el número total de electores.
     *
     * @return El número de representantes elegibles.
     */
    private int calcularNumeroRepresentantes() {
        int representantesElegibles;

        if (getTotalElectores() < 101) {
            representantesElegibles = 5;
        } else if (getTotalElectores() < 251) {
            representantesElegibles = 9;
        } else if (getTotalElectores() < 501) {
            representantesElegibles = 13;
        } else if (getTotalElectores() < 751) {
            representantesElegibles = 17;
        } else if (getTotalElectores() < 1001) {
            representantesElegibles = 21;
        } else {
            // Cálculo para los siguientes bloques de 1000 electores
            int bloquesAdicionales = (getTotalElectores() - 1000) / 1000;
            representantesElegibles = Math.min(75, 21 + bloquesAdicionales * 2);
        }

        double representantesColegioSinFormateo = ((double) (representantesElegibles * totalElectoresTecnicos) / getTotalElectores());
        return (int) Math.round(representantesColegioSinFormateo);
    }

    public int getVotantesVaronesTecnicos() {
        return votantesVaronesTecnicos;
    }

    public void setVotantesVaronesTecnicos(int votantesVaronesTecnicos) throws CumplimentarPDFException {
        if (votantesVaronesTecnicos > electoresVaronesTecnicos) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.varones.votantes.tecnicos.superior"));
        }
        this.votantesVaronesTecnicos = votantesVaronesTecnicos;
    }

    public int getVotantesMujeresTecnicos() {
        return votantesMujeresTecnicos;
    }

    public void setVotantesMujeresTecnicos(int votantesMujeresTecnicos) throws CumplimentarPDFException {
        if (votantesMujeresTecnicos > electoresMujeresTecnicos) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.mujeres.votantes.tecnicos.superior"));
        }
        this.votantesMujeresTecnicos = votantesMujeresTecnicos;
    }

    public int getTotalVotantesTecnicos() {
        return totalVotantesTecnicos;
    }

    public void setTotalVotantesTecnicos(int totalVotantesTecnicos) {
        this.totalVotantesTecnicos = votantesVaronesTecnicos + votantesMujeresTecnicos;
    }

    public int getPapeletasCumplimentadasTecnicos() {
        return papeletasCumplimentadasTecnicos;
    }

    public void setPapeletasCumplimentadasTecnicos(int papeletasCumplimentadasTecnicos) throws CumplimentarPDFException {
        if (papeletasCumplimentadasTecnicos > totalVotantesTecnicos) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.papeletas.cumplimentadas.tecnicos.superior"));
        }
        this.papeletasCumplimentadasTecnicos = papeletasCumplimentadasTecnicos;
    }

    public int getPapeletasBlancasTecnicos() {
        return papeletasBlancasTecnicos;
    }

    public void setPapeletasBlancasTecnicos(int papeletasBlancasTecnicos) throws CumplimentarPDFException {
        if (papeletasBlancasTecnicos > totalVotantesTecnicos) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.papeletas.blancas.tecnicos.superior"));
        }
        this.papeletasBlancasTecnicos = papeletasBlancasTecnicos;
    }

    public int getNulosTecnicos() {
        return nulosTecnicos;
    }

    public void setNulosTecnicos(int nulosTecnicos) throws CumplimentarPDFException {
        if (nulosTecnicos > totalVotantesTecnicos) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.papeletas.nulas.tecnicos.superior"));
        }
        this.nulosTecnicos = nulosTecnicos;
    }

    public int getRepresentantesElegidosTecnicos() {
        return representantesElegidosTecnicos;
    }

    public void setRepresentantesElegidosTecnicos(int representantesElegidosTecnicos) throws CumplimentarPDFException {
        if (representantesElegidosTecnicos > numeroRepresentantesTecnicos) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.representantes.elegidos.tecnicos.superior"));
        }
        this.representantesElegidosTecnicos = representantesElegidosTecnicos;
    }

    public int getElectoresVaronesEspecialistas() {
        return electoresVaronesEspecialistas;
    }

    public void setElectoresVaronesEspecialistas(int electoresVaronesEspecialistas) throws CumplimentarPDFException {
        if (electoresVaronesEspecialistas > totalElectores) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.varones.electores.especialistas.superior"));
        }
        this.electoresVaronesEspecialistas = electoresVaronesEspecialistas;
    }

    public int getElectoresMujeresEspecialistas() {
        return electoresMujeresEspecialistas;
    }

    public void setElectoresMujeresEspecialistas(int electoresMujeresEspecialistas) throws CumplimentarPDFException {
        if (electoresMujeresEspecialistas > totalElectores) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.mujeres.electores.especialistas.superior"));
        }
        this.electoresMujeresEspecialistas = electoresMujeresEspecialistas;
    }

    public int getTotalElectoresEspecialistas() {
        return totalElectoresEspecialistas;
    }

    public void setTotalElectoresEspecialistas(int totalElectoresEspecialistas) {
        this.totalElectoresEspecialistas = electoresVaronesEspecialistas + electoresMujeresEspecialistas;
    }

    public int getNumeroRepresentantesEspecialistas() {
        return numeroRepresentantesEspecialistas;
    }

    public void setNumeroRepresentantesEspecialistas(int numeroRepresentantesEspecialistas) {
        this.numeroRepresentantesEspecialistas = calcularNumeroRepresentantes();
    }

    public int getVotantesVaronesEspecialistas() {
        return votantesVaronesEspecialistas;
    }

    public void setVotantesVaronesEspecialistas(int votantesVaronesEspecialistas) throws CumplimentarPDFException {
        if (votantesVaronesEspecialistas > electoresVaronesEspecialistas) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.varones.votantes.especialistas.superior"));
        }
        this.votantesVaronesEspecialistas = votantesVaronesEspecialistas;
    }

    public int getVotantesMujeresEspecialistas() {
        return votantesMujeresEspecialistas;
    }

    public void setVotantesMujeresEspecialistas(int votantesMujeresEspecialistas) throws CumplimentarPDFException {
        if (votantesMujeresEspecialistas > electoresMujeresEspecialistas) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.mujeres.votantes.especialistas.superior"));
        }
        this.votantesMujeresEspecialistas = votantesMujeresEspecialistas;
    }

    public int getTotalVotantesEspecialistas() {
        return totalVotantesEspecialistas;
    }

    public void setTotalVotantesEspecialistas(int totalVotantesEspecialistas) {
        this.totalVotantesEspecialistas = votantesVaronesEspecialistas + votantesMujeresEspecialistas;
    }

    public int getPapeletasCumplimentadasEspecialistas() {
        return papeletasCumplimentadasEspecialistas;
    }

    public void setPapeletasCumplimentadasEspecialistas(int papeletasCumplimentadasEspecialistas) throws CumplimentarPDFException {
        if (papeletasCumplimentadasEspecialistas > totalVotantesEspecialistas) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.papeletas.cumplimentadas.especialistas.superior"));
        }
        this.papeletasCumplimentadasEspecialistas = papeletasCumplimentadasEspecialistas;
    }

    public int getPapeletasBlancasEspecialistas() {
        return papeletasBlancasEspecialistas;
    }

    public void setPapeletasBlancasEspecialistas(int papeletasBlancasEspecialistas) throws CumplimentarPDFException {
        if (papeletasBlancasEspecialistas > totalVotantesEspecialistas) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.papeletas.blancas.especialistas.superior"));
        }
        this.papeletasBlancasEspecialistas = papeletasBlancasEspecialistas;
    }

    public int getNulosEspecialistas() {
        return nulosEspecialistas;
    }

    public void setNulosEspecialistas(int nulosEspecialistas) throws CumplimentarPDFException {
        if (nulosEspecialistas > totalVotantesEspecialistas) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.papeletas.nulas.especialistas.superior"));
        }
        this.nulosEspecialistas = nulosEspecialistas;
    }

    public int getRepresentantesElegidosEspecialistas() {
        return representantesElegidosEspecialistas;
    }

    public void setRepresentantesElegidosEspecialistas(int representantesElegidosEspecialistas) throws CumplimentarPDFException {
        if (representantesElegidosEspecialistas > numeroRepresentantesEspecialistas) {
            throw new CumplimentarPDFException(MessageManager.getMessage("error.representantes.elegidos.especialistas.superior"));
        }
        this.representantesElegidosEspecialistas = representantesElegidosEspecialistas;
    }

    public int getTotalRepresentantesElegidos() {
        return totalRepresentantesElegidos;
    }

    public void setTotalRepresentantesElegidos(int totalRepresentantesElegidos) {
        this.totalRepresentantesElegidos = representantesElegidosTecnicos + representantesElegidosEspecialistas;
    }
}
