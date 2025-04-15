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

package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the PRF_V_LIS_RUOLO database table.
 *
 */
@Entity
@Table(name = "PRF_V_LIS_RUOLO")
public class PrfVLisRuolo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String dsApplic;
    private String dsEsitoRichAllineaRuoli_1;
    private String dsEsitoRichAllineaRuoli_2;
    private String dsMsgAllineamentoParz;
    private String dsRuolo;
    private String flAllineamentoInCorso;
    private BigDecimal idRuolo;
    private BigDecimal idUserIamCorr;
    private BigDecimal idUsoRuoloApplic;
    private String nmApplic;
    private String nmRuolo;
    private String tiRuolo;
    private String tiCateg;
    private String tiCategRuolo;
    private String tiStatoRichAllineaRuoli_1;
    private String tiStatoRichAllineaRuoli_2;

    public PrfVLisRuolo() {
    }

    public PrfVLisRuolo(BigDecimal idRuolo, String nmRuolo, String dsRuolo, String tiRuolo,
	    String tiCategRuolo, String dsApplic, String flAllineamentoInCorso,
	    String tiStatoRichAllineaRuoli_1, String tiStatoRichAllineaRuoli_2,
	    String dsEsitoRichAllineaRuoli_1, String dsEsitoRichAllineaRuoli_2,
	    String dsMsgAllineamentoParz) {
	this.idRuolo = idRuolo;
	this.nmRuolo = nmRuolo;
	this.dsRuolo = dsRuolo;
	this.tiRuolo = tiRuolo;
	this.tiCategRuolo = tiCategRuolo;
	this.dsApplic = dsApplic;
	this.flAllineamentoInCorso = flAllineamentoInCorso;
	this.tiStatoRichAllineaRuoli_1 = tiStatoRichAllineaRuoli_1;
	this.tiStatoRichAllineaRuoli_2 = tiStatoRichAllineaRuoli_2;
	this.dsEsitoRichAllineaRuoli_1 = dsEsitoRichAllineaRuoli_1;
	this.dsEsitoRichAllineaRuoli_2 = dsEsitoRichAllineaRuoli_2;
	this.dsMsgAllineamentoParz = dsMsgAllineamentoParz;
    }

    @Column(name = "DS_APPLIC")
    public String getDsApplic() {
	return this.dsApplic;
    }

    public void setDsApplic(String dsApplic) {
	this.dsApplic = dsApplic;
    }

    @Column(name = "DS_ESITO_RICH_ALLINEA_RUOLI_1")
    public String getDsEsitoRichAllineaRuoli_1() {
	return dsEsitoRichAllineaRuoli_1;
    }

    public void setDsEsitoRichAllineaRuoli_1(String dsEsitoRichAllineaRuoli_1) {
	this.dsEsitoRichAllineaRuoli_1 = dsEsitoRichAllineaRuoli_1;
    }

    @Column(name = "DS_ESITO_RICH_ALLINEA_RUOLI_2")
    public String getDsEsitoRichAllineaRuoli_2() {
	return dsEsitoRichAllineaRuoli_2;
    }

    public void setDsEsitoRichAllineaRuoli_2(String dsEsitoRichAllineaRuoli_2) {
	this.dsEsitoRichAllineaRuoli_2 = dsEsitoRichAllineaRuoli_2;
    }

    @Column(name = "DS_MSG_ALLINEAMENTO_PARZ")
    public String getDsMsgAllineamentoParz() {
	return dsMsgAllineamentoParz;
    }

    public void setDsMsgAllineamentoParz(String dsMsgAllineamentoParz) {
	this.dsMsgAllineamentoParz = dsMsgAllineamentoParz;
    }

    @Column(name = "DS_RUOLO")
    public String getDsRuolo() {
	return this.dsRuolo;
    }

    public void setDsRuolo(String dsRuolo) {
	this.dsRuolo = dsRuolo;
    }

    @Column(name = "FL_ALLINEAMENTO_IN_CORSO", columnDefinition = "char(1)")
    public String getFlAllineamentoInCorso() {
	return this.flAllineamentoInCorso;
    }

    public void setFlAllineamentoInCorso(String flAllineamentoInCorso) {
	this.flAllineamentoInCorso = flAllineamentoInCorso;
    }

    @Column(name = "ID_RUOLO")
    public BigDecimal getIdRuolo() {
	return this.idRuolo;
    }

    public void setIdRuolo(BigDecimal idRuolo) {
	this.idRuolo = idRuolo;
    }

    @Column(name = "ID_USER_IAM_CORR")
    public BigDecimal getIdUserIamCorr() {
	return this.idUserIamCorr;
    }

    public void setIdUserIamCorr(BigDecimal idUserIamCorr) {
	this.idUserIamCorr = idUserIamCorr;
    }

    @Id
    @Column(name = "ID_USO_RUOLO_APPLIC")
    public BigDecimal getIdUsoRuoloApplic() {
	return this.idUsoRuoloApplic;
    }

    public void setIdUsoRuoloApplic(BigDecimal idUsoRuoloApplic) {
	this.idUsoRuoloApplic = idUsoRuoloApplic;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
	return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
	this.nmApplic = nmApplic;
    }

    @Column(name = "NM_RUOLO")
    public String getNmRuolo() {
	return this.nmRuolo;
    }

    public void setNmRuolo(String nmRuolo) {
	this.nmRuolo = nmRuolo;
    }

    @Column(name = "TI_RUOLO")
    public String getTiRuolo() {
	return this.tiRuolo;
    }

    public void setTiRuolo(String tiRuolo) {
	this.tiRuolo = tiRuolo;
    }

    @Column(name = "TI_CATEG")
    public String getTiCateg() {
	return this.tiCateg;
    }

    public void setTiCateg(String tiCateg) {
	this.tiCateg = tiCateg;
    }

    @Column(name = "TI_CATEG_RUOLO")
    public String getTiCategRuolo() {
	return this.tiCategRuolo;
    }

    public void setTiCategRuolo(String tiCategRuolo) {
	this.tiCategRuolo = tiCategRuolo;
    }

    @Column(name = "TI_STATO_RICH_ALLINEA_RUOLI_1")
    public String getTiStatoRichAllineaRuoli_1() {
	return this.tiStatoRichAllineaRuoli_1;
    }

    public void setTiStatoRichAllineaRuoli_1(String tiStatoRichAllineaRuoli_1) {
	this.tiStatoRichAllineaRuoli_1 = tiStatoRichAllineaRuoli_1;
    }

    @Column(name = "TI_STATO_RICH_ALLINEA_RUOLI_2")
    public String getTiStatoRichAllineaRuoli_2() {
	return this.tiStatoRichAllineaRuoli_2;
    }

    public void setTiStatoRichAllineaRuoli_2(String tiStatoRichAllineaRuoli_2) {
	this.tiStatoRichAllineaRuoli_2 = tiStatoRichAllineaRuoli_2;
    }

}
