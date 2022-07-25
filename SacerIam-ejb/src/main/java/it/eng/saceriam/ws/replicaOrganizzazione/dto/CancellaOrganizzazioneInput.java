package it.eng.saceriam.ws.replicaOrganizzazione.dto;

/**
 *
 * @author Gilioli_P
 */
public class CancellaOrganizzazioneInput {

    private String nmApplic;
    private Integer idOrganizApplic;
    private String nmTipoOrganiz;

    public CancellaOrganizzazioneInput(String nmApplic, Integer idOrganizApplic, String nmTipoOrganiz) {
        this.nmApplic = nmApplic;
        this.idOrganizApplic = idOrganizApplic;
        this.nmTipoOrganiz = nmTipoOrganiz;
    }

    public String getNmApplic() {
        return nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    public Integer getIdOrganizApplic() {
        return idOrganizApplic;
    }

    public void setIdOrganizApplic(Integer idOrganizApplic) {
        this.idOrganizApplic = idOrganizApplic;
    }

    public String getNmTipoOrganiz() {
        return nmTipoOrganiz;
    }

    public void setNmTipoOrganiz(String nmTipoOrganiz) {
        this.nmTipoOrganiz = nmTipoOrganiz;
    }
}