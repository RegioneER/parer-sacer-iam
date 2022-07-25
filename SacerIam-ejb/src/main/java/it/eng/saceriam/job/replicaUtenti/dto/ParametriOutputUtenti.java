package it.eng.saceriam.job.replicaUtenti.dto;

import it.eng.saceriam.common.Constants;
import it.eng.saceriam.common.Constants.EsitoServizio;
import java.util.Date;

/**
 *
 * @author Gilioli_P
 */
public class ParametriOutputUtenti {

    private Constants.EsitoServizio cdEsito;
    private String cdErr;
    private String dsErr;
    private Integer idUserIam;
    private String nmUserid;
    private String cdPsw;
    private String nmCognomeUser;
    private String nmNomeUser;
    private String flAttivo;
    private Date dtRegPsw;
    private Date dtScadPsw;
    private ListaOrganizAbil listaOrganizAbil;

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

    public Integer getIdUserIam() {
        return idUserIam;
    }

    public void setIdUserIam(Integer idUserIam) {
        this.idUserIam = idUserIam;
    }

    public String getNmUserid() {
        return nmUserid;
    }

    public void setNmUserid(String nmUserid) {
        this.nmUserid = nmUserid;
    }

    public String getCdPsw() {
        return cdPsw;
    }

    public void setCdPsw(String cdPsw) {
        this.cdPsw = cdPsw;
    }

    public String getNmCognomeUser() {
        return nmCognomeUser;
    }

    public void setNmCognomeUser(String nmCognomeUser) {
        this.nmCognomeUser = nmCognomeUser;
    }

    public String getNmNomeUser() {
        return nmNomeUser;
    }

    public void setNmNomeUser(String nmNomeUser) {
        this.nmNomeUser = nmNomeUser;
    }

    public String getFlAttivo() {
        return flAttivo;
    }

    public void setFlAttivo(String flAttivo) {
        this.flAttivo = flAttivo;
    }

    public Date getDtRegPsw() {
        return dtRegPsw;
    }

    public void setDtRegPsw(Date dtRegPsw) {
        this.dtRegPsw = dtRegPsw;
    }

    public Date getDtScadPsw() {
        return dtScadPsw;
    }

    public void setDtScadPsw(Date dtScadPsw) {
        this.dtScadPsw = dtScadPsw;
    }

    public ListaOrganizAbil getListaOrganizAbil() {
        return listaOrganizAbil;
    }

    public void setListaOrganizAbil(ListaOrganizAbil listaOrganizAbil) {
        this.listaOrganizAbil = listaOrganizAbil;
    }
}