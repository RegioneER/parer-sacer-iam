package it.eng.saceriam.ws.replicaOrganizzazione.dto;

import it.eng.saceriam.web.util.Constants;
import it.eng.saceriam.web.util.Constants.EsitoServizio;

/**
 *
 * @author Gilioli_P
 */
public abstract class ReplicaOrganizzazioneRispostaAbstract {

    private Constants.EsitoServizio cdEsito;
    private String cdErr;
    private String dsErr;
    private String nmApplic;
    private Integer idOrganizApplic;
    private String nmTipoOrganiz;

    public EsitoServizio getCdEsito() {
        return cdEsito;
    }

    public void setCdEsito(EsitoServizio cdEsito) {
        this.cdEsito = cdEsito;
    }

    public String getCdErr() {
        return cdErr;
    }

    public void setCdErr(String cdErr) {
        this.cdErr = cdErr;
    }

    public String getDsErr() {
        return dsErr;
    }

    public void setDsErr(String dsErr) {
        this.dsErr = dsErr;
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
