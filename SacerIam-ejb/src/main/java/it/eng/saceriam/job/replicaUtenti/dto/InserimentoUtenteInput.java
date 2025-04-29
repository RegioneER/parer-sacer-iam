/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna <p/> This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version. <p/> This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Affero General Public License for more details. <p/> You should
 * have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.job.replicaUtenti.dto;

import java.util.Date;

/**
 *
 * @author Gilioli_P
 */
public class InserimentoUtenteInput {

    private Integer idUserIam;
    private String nmUserid;
    private String cdPsw;
    private String nmCognomeUser;
    private String nmNomeUser;
    private String flAttivo;
    private Date dtRegPsw;
    private Date dtScadPsw;
    private ListaOrganizAbil listaOrganizAbil;

    public InserimentoUtenteInput(Integer idUserIam, String nmUserid, String cdPsw,
	    String nmCognomeUser, String nmNomeUser, String flAttivo, Date dtRegPsw, Date dtScadPsw,
	    ListaOrganizAbil listaOrganizAbil) {

	this.idUserIam = idUserIam;
	this.nmUserid = nmUserid;
	this.cdPsw = cdPsw;
	this.nmCognomeUser = nmCognomeUser;
	this.nmNomeUser = nmNomeUser;
	this.flAttivo = flAttivo;
	this.dtRegPsw = dtRegPsw;
	this.dtScadPsw = dtScadPsw;
	this.listaOrganizAbil = listaOrganizAbil;
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
