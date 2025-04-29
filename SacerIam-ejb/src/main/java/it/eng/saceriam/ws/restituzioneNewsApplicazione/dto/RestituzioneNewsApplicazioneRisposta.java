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
