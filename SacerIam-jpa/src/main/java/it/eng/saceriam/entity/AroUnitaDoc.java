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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the ARO_UNITA_DOC database table.
 */
@Entity
@Table(name = "ARO_UNITA_DOC", schema = "SACER")
public class AroUnitaDoc implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idUnitaDoc;

    private BigDecimal aaKeyUnitaDoc;

    private String cdFascicPrinc;

    private String cdKeyUnitaDoc;

    private String cdRegistroKeyUnitaDoc;

    private String cdSottofascicPrinc;

    private String dlOggettoUnitaDoc;

    private String dsClassifPrinc;

    private String dsKeyOrd;

    private String dsMsgEsitoVerifFirme;

    private String dsOggettoFascicPrinc;

    private String dsOggettoSottofascicPrinc;

    private String dsUffCompUnitaDoc;

    private Date dtAnnul;

    private Date dtCreazione;

    private Date dtRegUnitaDoc;

    private String flCartaceo;

    private String flForzaAccettazione;

    private String flForzaCollegamento;

    private String flForzaConservazione;

    private String flUnitaDocFirmato;

    private BigDecimal niAlleg;

    private BigDecimal niAnnessi;

    private BigDecimal niAnnot;

    private String nmSistemaMigraz;

    private String ntAnnul;

    private String ntUnitaDoc;

    private Long idOrgStrut;

    private IamUser iamUser;

    private BigDecimal pgUnitaDoc;

    private String tiAnnul;

    private String tiConservazione;

    private String tiEsitoVerifFirme;

    private String tiStatoConservazione;

    private String tiStatoUdElencoVers;

    public AroUnitaDoc() {
    }

    @Id
    @Column(name = "ID_UNITA_DOC")
    public Long getIdUnitaDoc() {
	return this.idUnitaDoc;
    }

    public void setIdUnitaDoc(Long idUnitaDoc) {
	this.idUnitaDoc = idUnitaDoc;
    }

    @Column(name = "AA_KEY_UNITA_DOC")
    public BigDecimal getAaKeyUnitaDoc() {
	return this.aaKeyUnitaDoc;
    }

    public void setAaKeyUnitaDoc(BigDecimal aaKeyUnitaDoc) {
	this.aaKeyUnitaDoc = aaKeyUnitaDoc;
    }

    @Column(name = "CD_FASCIC_PRINC")
    public String getCdFascicPrinc() {
	return this.cdFascicPrinc;
    }

    public void setCdFascicPrinc(String cdFascicPrinc) {
	this.cdFascicPrinc = cdFascicPrinc;
    }

    @Column(name = "CD_KEY_UNITA_DOC")
    public String getCdKeyUnitaDoc() {
	return this.cdKeyUnitaDoc;
    }

    public void setCdKeyUnitaDoc(String cdKeyUnitaDoc) {
	this.cdKeyUnitaDoc = cdKeyUnitaDoc;
    }

    @Column(name = "CD_REGISTRO_KEY_UNITA_DOC")
    public String getCdRegistroKeyUnitaDoc() {
	return this.cdRegistroKeyUnitaDoc;
    }

    public void setCdRegistroKeyUnitaDoc(String cdRegistroKeyUnitaDoc) {
	this.cdRegistroKeyUnitaDoc = cdRegistroKeyUnitaDoc;
    }

    @Column(name = "CD_SOTTOFASCIC_PRINC")
    public String getCdSottofascicPrinc() {
	return this.cdSottofascicPrinc;
    }

    public void setCdSottofascicPrinc(String cdSottofascicPrinc) {
	this.cdSottofascicPrinc = cdSottofascicPrinc;
    }

    @Column(name = "DL_OGGETTO_UNITA_DOC")
    public String getDlOggettoUnitaDoc() {
	return this.dlOggettoUnitaDoc;
    }

    public void setDlOggettoUnitaDoc(String dlOggettoUnitaDoc) {
	this.dlOggettoUnitaDoc = dlOggettoUnitaDoc;
    }

    @Column(name = "DS_CLASSIF_PRINC")
    public String getDsClassifPrinc() {
	return this.dsClassifPrinc;
    }

    public void setDsClassifPrinc(String dsClassifPrinc) {
	this.dsClassifPrinc = dsClassifPrinc;
    }

    @Column(name = "DS_KEY_ORD")
    public String getDsKeyOrd() {
	return this.dsKeyOrd;
    }

    public void setDsKeyOrd(String dsKeyOrd) {
	this.dsKeyOrd = dsKeyOrd;
    }

    @Column(name = "DS_MSG_ESITO_VERIF_FIRME")
    public String getDsMsgEsitoVerifFirme() {
	return this.dsMsgEsitoVerifFirme;
    }

    public void setDsMsgEsitoVerifFirme(String dsMsgEsitoVerifFirme) {
	this.dsMsgEsitoVerifFirme = dsMsgEsitoVerifFirme;
    }

    @Column(name = "DS_OGGETTO_FASCIC_PRINC")
    public String getDsOggettoFascicPrinc() {
	return this.dsOggettoFascicPrinc;
    }

    public void setDsOggettoFascicPrinc(String dsOggettoFascicPrinc) {
	this.dsOggettoFascicPrinc = dsOggettoFascicPrinc;
    }

    @Column(name = "DS_OGGETTO_SOTTOFASCIC_PRINC")
    public String getDsOggettoSottofascicPrinc() {
	return this.dsOggettoSottofascicPrinc;
    }

    public void setDsOggettoSottofascicPrinc(String dsOggettoSottofascicPrinc) {
	this.dsOggettoSottofascicPrinc = dsOggettoSottofascicPrinc;
    }

    @Column(name = "DS_UFF_COMP_UNITA_DOC")
    public String getDsUffCompUnitaDoc() {
	return this.dsUffCompUnitaDoc;
    }

    public void setDsUffCompUnitaDoc(String dsUffCompUnitaDoc) {
	this.dsUffCompUnitaDoc = dsUffCompUnitaDoc;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_ANNUL")
    public Date getDtAnnul() {
	return this.dtAnnul;
    }

    public void setDtAnnul(Date dtAnnul) {
	this.dtAnnul = dtAnnul;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_CREAZIONE")
    public Date getDtCreazione() {
	return this.dtCreazione;
    }

    public void setDtCreazione(Date dtCreazione) {
	this.dtCreazione = dtCreazione;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_REG_UNITA_DOC")
    public Date getDtRegUnitaDoc() {
	return this.dtRegUnitaDoc;
    }

    public void setDtRegUnitaDoc(Date dtRegUnitaDoc) {
	this.dtRegUnitaDoc = dtRegUnitaDoc;
    }

    @Column(name = "FL_CARTACEO", columnDefinition = "CHAR")
    public String getFlCartaceo() {
	return this.flCartaceo;
    }

    public void setFlCartaceo(String flCartaceo) {
	this.flCartaceo = flCartaceo;
    }

    @Column(name = "FL_FORZA_ACCETTAZIONE", columnDefinition = "CHAR")
    public String getFlForzaAccettazione() {
	return this.flForzaAccettazione;
    }

    public void setFlForzaAccettazione(String flForzaAccettazione) {
	this.flForzaAccettazione = flForzaAccettazione;
    }

    @Column(name = "FL_FORZA_COLLEGAMENTO", columnDefinition = "CHAR")
    public String getFlForzaCollegamento() {
	return this.flForzaCollegamento;
    }

    public void setFlForzaCollegamento(String flForzaCollegamento) {
	this.flForzaCollegamento = flForzaCollegamento;
    }

    @Column(name = "FL_FORZA_CONSERVAZIONE", columnDefinition = "CHAR")
    public String getFlForzaConservazione() {
	return this.flForzaConservazione;
    }

    public void setFlForzaConservazione(String flForzaConservazione) {
	this.flForzaConservazione = flForzaConservazione;
    }

    @Column(name = "FL_UNITA_DOC_FIRMATO", columnDefinition = "CHAR")
    public String getFlUnitaDocFirmato() {
	return this.flUnitaDocFirmato;
    }

    public void setFlUnitaDocFirmato(String flUnitaDocFirmato) {
	this.flUnitaDocFirmato = flUnitaDocFirmato;
    }

    @Column(name = "NI_ALLEG")
    public BigDecimal getNiAlleg() {
	return this.niAlleg;
    }

    public void setNiAlleg(BigDecimal niAlleg) {
	this.niAlleg = niAlleg;
    }

    @Column(name = "NI_ANNESSI")
    public BigDecimal getNiAnnessi() {
	return this.niAnnessi;
    }

    public void setNiAnnessi(BigDecimal niAnnessi) {
	this.niAnnessi = niAnnessi;
    }

    @Column(name = "NI_ANNOT")
    public BigDecimal getNiAnnot() {
	return this.niAnnot;
    }

    public void setNiAnnot(BigDecimal niAnnot) {
	this.niAnnot = niAnnot;
    }

    @Column(name = "NM_SISTEMA_MIGRAZ")
    public String getNmSistemaMigraz() {
	return this.nmSistemaMigraz;
    }

    public void setNmSistemaMigraz(String nmSistemaMigraz) {
	this.nmSistemaMigraz = nmSistemaMigraz;
    }

    @Column(name = "NT_ANNUL")
    public String getNtAnnul() {
	return this.ntAnnul;
    }

    public void setNtAnnul(String ntAnnul) {
	this.ntAnnul = ntAnnul;
    }

    @Column(name = "NT_UNITA_DOC")
    public String getNtUnitaDoc() {
	return this.ntUnitaDoc;
    }

    public void setNtUnitaDoc(String ntUnitaDoc) {
	this.ntUnitaDoc = ntUnitaDoc;
    }

    @Column(name = "ID_STRUT", insertable = false, updatable = false)
    public Long getIdOrgStrut() {
	return idOrgStrut;
    }

    public void setIdOrgStrut(Long idOrgStrut) {
	this.idOrgStrut = idOrgStrut;
    }

    // bi-directional many-to-one association to IamUser
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USER_VERS")
    public IamUser getIamUser() {
	return this.iamUser;
    }

    public void setIamUser(IamUser iamUser) {
	this.iamUser = iamUser;
    }

    @Column(name = "PG_UNITA_DOC")
    public BigDecimal getPgUnitaDoc() {
	return this.pgUnitaDoc;
    }

    public void setPgUnitaDoc(BigDecimal pgUnitaDoc) {
	this.pgUnitaDoc = pgUnitaDoc;
    }

    @Column(name = "TI_ANNUL")
    public String getTiAnnul() {
	return this.tiAnnul;
    }

    public void setTiAnnul(String tiAnnul) {
	this.tiAnnul = tiAnnul;
    }

    @Column(name = "TI_CONSERVAZIONE")
    public String getTiConservazione() {
	return this.tiConservazione;
    }

    public void setTiConservazione(String tiConservazione) {
	this.tiConservazione = tiConservazione;
    }

    @Column(name = "TI_ESITO_VERIF_FIRME")
    public String getTiEsitoVerifFirme() {
	return this.tiEsitoVerifFirme;
    }

    public void setTiEsitoVerifFirme(String tiEsitoVerifFirme) {
	this.tiEsitoVerifFirme = tiEsitoVerifFirme;
    }

    @Column(name = "TI_STATO_CONSERVAZIONE")
    public String getTiStatoConservazione() {
	return this.tiStatoConservazione;
    }

    public void setTiStatoConservazione(String tiStatoConservazione) {
	this.tiStatoConservazione = tiStatoConservazione;
    }

    @Column(name = "TI_STATO_UD_ELENCO_VERS")
    public String getTiStatoUdElencoVers() {
	return this.tiStatoUdElencoVers;
    }

    public void setTiStatoUdElencoVers(String tiStatoUdElencoVers) {
	this.tiStatoUdElencoVers = tiStatoUdElencoVers;
    }
}
