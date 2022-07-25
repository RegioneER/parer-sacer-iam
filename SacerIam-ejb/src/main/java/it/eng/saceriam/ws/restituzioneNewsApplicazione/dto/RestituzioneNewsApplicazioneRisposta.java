package it.eng.saceriam.ws.restituzioneNewsApplicazione.dto;

import it.eng.saceriam.web.util.Constants;

/**
 *
 * @author Gilioli_P
 */
public class RestituzioneNewsApplicazioneRisposta {

    private Constants.EsitoServizio cdEsito;
    private String cdErr;
    private String dsErr;
    private String nmUserId;
    private String cdPsw;
    private String nmApplic;
    private ListaNews listaNews;

    public Constants.EsitoServizio getCdEsito() {
        return cdEsito;
    }

    public void setCdEsito(Constants.EsitoServizio cdEsito) {
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

    public String getNmUserId() {
        return nmUserId;
    }

    public void setNmUserId(String nmUserId) {
        this.nmUserId = nmUserId;
    }

    public String getCdPsw() {
        return cdPsw;
    }

    public void setCdPsw(String cdPsw) {
        this.cdPsw = cdPsw;
    }

    public String getNmApplic() {
        return nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    public ListaNews getListaNews() {
        return listaNews;
    }

    public void setListaNews(ListaNews listaNews) {
        this.listaNews = listaNews;
    }

}