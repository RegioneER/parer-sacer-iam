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

package it.eng.saceriam.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the ORG_STO_ENTE_CONVENZ database table.
 */
@Entity
@Table(name = "ORG_STO_ENTE_CONVENZ")
public class OrgStoEnteConvenz implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idStoEnteConvenz;

    private String cdCapSedeLegale;

    private String cdEnteConvenz;

    private String cdFisc;

    private String cdNazioneSedeLegale;

    private String dlNote;

    private String dsCittaSedeLegale;

    private String dsViaSedeLegale;

    private Date dtFineVal;

    private Date dtIniVal;

    private java.math.BigDecimal idAmbitoTerrit;

    private java.math.BigDecimal idCategEnte;

    private java.math.BigDecimal idProvSedeLegale;

    private String nmEnteConvenz;

    private String tiCdEnteConvenz;

    private OrgEnteSiam orgEnteSiam;

    public OrgStoEnteConvenz() {
    }

    @Id
    @Column(name = "ID_STO_ENTE_CONVENZ")
    @GenericGenerator(name = "SORG_STO_ENTE_CONVENZ_ID_STO_ENTE_CONVENZ_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SORG_STO_ENTE_CONVENZ"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SORG_STO_ENTE_CONVENZ_ID_STO_ENTE_CONVENZ_GENERATOR")
    public Long getIdStoEnteConvenz() {
	return this.idStoEnteConvenz;
    }

    public void setIdStoEnteConvenz(Long idStoEnteConvenz) {
	this.idStoEnteConvenz = idStoEnteConvenz;
    }

    @Column(name = "CD_CAP_SEDE_LEGALE")
    public String getCdCapSedeLegale() {
	return this.cdCapSedeLegale;
    }

    public void setCdCapSedeLegale(String cdCapSedeLegale) {
	this.cdCapSedeLegale = cdCapSedeLegale;
    }

    @Column(name = "CD_ENTE_CONVENZ")
    public String getCdEnteConvenz() {
	return this.cdEnteConvenz;
    }

    public void setCdEnteConvenz(String cdEnteConvenz) {
	this.cdEnteConvenz = cdEnteConvenz;
    }

    @Column(name = "CD_FISC")
    public String getCdFisc() {
	return this.cdFisc;
    }

    public void setCdFisc(String cdFisc) {
	this.cdFisc = cdFisc;
    }

    @Column(name = "CD_NAZIONE_SEDE_LEGALE")
    public String getCdNazioneSedeLegale() {
	return this.cdNazioneSedeLegale;
    }

    public void setCdNazioneSedeLegale(String cdNazioneSedeLegale) {
	this.cdNazioneSedeLegale = cdNazioneSedeLegale;
    }

    @Column(name = "DL_NOTE")
    public String getDlNote() {
	return this.dlNote;
    }

    public void setDlNote(String dlNote) {
	this.dlNote = dlNote;
    }

    @Column(name = "DS_CITTA_SEDE_LEGALE")
    public String getDsCittaSedeLegale() {
	return this.dsCittaSedeLegale;
    }

    public void setDsCittaSedeLegale(String dsCittaSedeLegale) {
	this.dsCittaSedeLegale = dsCittaSedeLegale;
    }

    @Column(name = "DS_VIA_SEDE_LEGALE")
    public String getDsViaSedeLegale() {
	return this.dsViaSedeLegale;
    }

    public void setDsViaSedeLegale(String dsViaSedeLegale) {
	this.dsViaSedeLegale = dsViaSedeLegale;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FINE_VAL")
    public Date getDtFineVal() {
	return this.dtFineVal;
    }

    public void setDtFineVal(Date dtFineVal) {
	this.dtFineVal = dtFineVal;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_INI_VAL")
    public Date getDtIniVal() {
	return this.dtIniVal;
    }

    public void setDtIniVal(Date dtIniVal) {
	this.dtIniVal = dtIniVal;
    }

    @Column(name = "ID_AMBITO_TERRIT")
    public BigDecimal getIdAmbitoTerrit() {
	return this.idAmbitoTerrit;
    }

    public void setIdAmbitoTerrit(BigDecimal idAmbitoTerrit) {
	this.idAmbitoTerrit = idAmbitoTerrit;
    }

    @Column(name = "ID_CATEG_ENTE")
    public BigDecimal getIdCategEnte() {
	return this.idCategEnte;
    }

    public void setIdCategEnte(BigDecimal idCategEnte) {
	this.idCategEnte = idCategEnte;
    }

    @Column(name = "ID_PROV_SEDE_LEGALE")
    public BigDecimal getIdProvSedeLegale() {
	return this.idProvSedeLegale;
    }

    public void setIdProvSedeLegale(BigDecimal idProvSedeLegale) {
	this.idProvSedeLegale = idProvSedeLegale;
    }

    @Column(name = "NM_ENTE_CONVENZ")
    public String getNmEnteConvenz() {
	return this.nmEnteConvenz;
    }

    public void setNmEnteConvenz(String nmEnteConvenz) {
	this.nmEnteConvenz = nmEnteConvenz;
    }

    @Column(name = "TI_CD_ENTE_CONVENZ")
    public String getTiCdEnteConvenz() {
	return this.tiCdEnteConvenz;
    }

    public void setTiCdEnteConvenz(String tiCdEnteConvenz) {
	this.tiCdEnteConvenz = tiCdEnteConvenz;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_CONVENZ")
    public OrgEnteSiam getOrgEnteSiam() {
	return this.orgEnteSiam;
    }

    public void setOrgEnteSiam(OrgEnteSiam orgEnteSiam) {
	this.orgEnteSiam = orgEnteSiam;
    }
}
