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

package it.eng.saceriam.ws.allineaRuolo.dto;

import java.io.Serializable;

/**
 *
 * @author Bonora_L
 */
public class AllineaRuoloRisposta implements Serializable {

    private CdEsito cdEsito;
    private String cdErr, dlErr, nmSistemaChiamante, nmUseridCor, nmRuolo, dsRuolo, tiRuolo;
    private ListaCategRuolo listaCategRuolo;
    private ListaApplic listaApplic;

    public enum CdEsito {
	OK, KO;
    }

    public AllineaRuoloRisposta() {
	this.cdEsito = CdEsito.KO;
    }

    public AllineaRuoloRisposta(AllineaRuoloInput input) {
	this();
	this.nmSistemaChiamante = input.getNmSistemaChiamante();
	this.nmUseridCor = input.getNmUseridCor();
	this.nmRuolo = input.getNmRuolo();
	this.dsRuolo = input.getDsRuolo();
	this.tiRuolo = input.getTiRuolo();
	this.listaCategRuolo = input.getListaCategRuolo();
	this.listaApplic = input.getListaApplic();
    }

    /**
     * @return the cdEsito
     */
    public CdEsito getCdEsito() {
	return cdEsito;
    }

    /**
     * @param cdEsito the cdEsito to set
     */
    public void setCdEsito(CdEsito cdEsito) {
	this.cdEsito = cdEsito;
    }

    /**
     * @return the cdErr
     */
    public String getCdErr() {
	return cdErr;
    }

    /**
     * @param cdErr the cdErr to set
     */
    public void setCdErr(String cdErr) {
	this.cdErr = cdErr;
    }

    /**
     * @return the dlErr
     */
    public String getDlErr() {
	return dlErr;
    }

    /**
     * @param dlErr the dlErr to set
     */
    public void setDlErr(String dlErr) {
	this.dlErr = dlErr;
    }

    /**
     * @return the nmSistemaChiamante
     */
    public String getNmSistemaChiamante() {
	return nmSistemaChiamante;
    }

    /**
     * @param nmSistemaChiamante the nmSistemaChiamante to set
     */
    public void setNmSistemaChiamante(String nmSistemaChiamante) {
	this.nmSistemaChiamante = nmSistemaChiamante;
    }

    /**
     * @return the nmUseridCor
     */
    public String getNmUseridCor() {
	return nmUseridCor;
    }

    /**
     * @param nmUseridCor the nmUseridCor to set
     */
    public void setNmUseridCor(String nmUseridCor) {
	this.nmUseridCor = nmUseridCor;
    }

    /**
     * @return the nmRuolo
     */
    public String getNmRuolo() {
	return nmRuolo;
    }

    /**
     * @param nmRuolo the nmRuolo to set
     */
    public void setNmRuolo(String nmRuolo) {
	this.nmRuolo = nmRuolo;
    }

    /**
     * @return the dsRuolo
     */
    public String getDsRuolo() {
	return dsRuolo;
    }

    /**
     * @param dsRuolo the dsRuolo to set
     */
    public void setDsRuolo(String dsRuolo) {
	this.dsRuolo = dsRuolo;
    }

    /**
     * @return the tiRuolo
     */
    public String getTiRuolo() {
	return tiRuolo;
    }

    /**
     * @param tiRuolo the tiRuolo to set
     */
    public void setTiRuolo(String tiRuolo) {
	this.tiRuolo = tiRuolo;
    }

    /**
     * @return the listaCategRuolo
     */
    public ListaCategRuolo getListaCategRuolo() {
	return listaCategRuolo;
    }

    /**
     * @param listaCategRuolo the listaCategRuolo to set
     */
    public void setListaCategRuolo(ListaCategRuolo listaCategRuolo) {
	this.listaCategRuolo = listaCategRuolo;
    }

    /**
     * @return the listaApplic
     */
    public ListaApplic getListaApplic() {
	return listaApplic;
    }

    /**
     * @param listaApplic the listaApplic to set
     */
    public void setListaApplic(ListaApplic listaApplic) {
	this.listaApplic = listaApplic;
    }

}
