package it.eng.saceriam.ws.replicaOrganizzazione.dto;

import java.util.Date;

/**
 *
 * @author Gilioli_P
 */
public class ModificaOrganizzazioneRisposta extends ReplicaOrganizzazioneRispostaAbstract {

    private Integer idOrganizApplicPadre;
    private String nmTipoOrganizPadre;
    private String nmOrganiz;
    private String dsOrganiz;
    private Integer idEnteConvenz;
    private Date dtIniVal;
    private Date dtFineVal;
    private ListaTipiDato listaTipiDato;

    public Integer getIdOrganizApplicPadre() {
        return idOrganizApplicPadre;
    }

    public void setIdOrganizApplicPadre(Integer idOrganizApplicPadre) {
        this.idOrganizApplicPadre = idOrganizApplicPadre;
    }

    public String getNmOrganiz() {
        return nmOrganiz;
    }

    public void setNmOrganiz(String nmOrganiz) {
        this.nmOrganiz = nmOrganiz;
    }

    public String getDsOrganiz() {
        return dsOrganiz;
    }

    public void setDsOrganiz(String dsOrganiz) {
        this.dsOrganiz = dsOrganiz;
    }

    public ListaTipiDato getListaTipiDato() {
        return listaTipiDato;
    }

    public void setListaTipiDato(ListaTipiDato listaTipiDato) {
        this.listaTipiDato = listaTipiDato;
    }

    public String getNmTipoOrganizPadre() {
        return nmTipoOrganizPadre;
    }

    public void setNmTipoOrganizPadre(String nmTipoOrganizPadre) {
        this.nmTipoOrganizPadre = nmTipoOrganizPadre;
    }

    public Integer getIdEnteConvenz() {
        return idEnteConvenz;
    }

    public void setIdEnteConvenz(Integer idEnteConvenz) {
        this.idEnteConvenz = idEnteConvenz;
    }

    public Date getDtIniVal() {
        return dtIniVal;
    }

    public void setDtIniVal(Date dtIniVal) {
        this.dtIniVal = dtIniVal;
    }

    public Date getDtFineVal() {
        return dtFineVal;
    }

    public void setDtFineVal(Date dtFineVal) {
        this.dtFineVal = dtFineVal;
    }
}
