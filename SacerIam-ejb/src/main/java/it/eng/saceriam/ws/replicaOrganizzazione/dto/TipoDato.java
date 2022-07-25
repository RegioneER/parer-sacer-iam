package it.eng.saceriam.ws.replicaOrganizzazione.dto;

/**
 *
 * @author Gilioli_P
 */
public class TipoDato {
    private String nmClasseTipoDato;
    private Integer idTipoDatoApplic;
    private String nmTipoDato;
    private String dsTipoDato;

    public String getNmClasseTipoDato() {
        return nmClasseTipoDato;
    }

    public void setNmClasseTipoDato(String nmClasseTipoDato) {
        this.nmClasseTipoDato = nmClasseTipoDato;
    }

    public Integer getIdTipoDatoApplic() {
        return idTipoDatoApplic;
    }

    public void setIdTipoDatoApplic(Integer idTipoDatoApplic) {
        this.idTipoDatoApplic = idTipoDatoApplic;
    }

    public String getNmTipoDato() {
        return nmTipoDato;
    }

    public void setNmTipoDato(String nmTipoDato) {
        this.nmTipoDato = nmTipoDato;
    }

    public String getDsTipoDato() {
        return dsTipoDato;
    }

    public void setDsTipoDato(String dsTipoDato) {
        this.dsTipoDato = dsTipoDato;
    }
}