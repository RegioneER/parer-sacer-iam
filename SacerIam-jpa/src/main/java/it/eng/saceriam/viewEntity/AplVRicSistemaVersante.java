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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the APL_V_RIC_SISTEMA_VERSANTE database table.
 */
@Entity
@Table(name = "APL_V_RIC_SISTEMA_VERSANTE")
public class AplVRicSistemaVersante implements Serializable {

    private static final long serialVersionUID = 1L;

    private String archivista;

    private String cdCapSedeLegale;

    private String cdVersione;

    private String dlCompositoOrganiz;

    private String dsCittaSedeLegale;

    private String dsEmail;
    private String dsNote;
    private String dsSistemaVersante;

    private String dsViaSedeLegale;

    private Date dtFineVal;

    private Date dtIniVal;

    private String flIntegrazione;

    private String flAssociaPersonaFisica;

    private String flCessato;

    private String flPec;

    private BigDecimal idEnteSiam;

    private BigDecimal idOrganizApplic;

    private BigDecimal idUserIamArk;

    private BigDecimal idUserIamCor;

    private String nmApplic;

    private String nmProduttore;

    private String nmSistemaVersante;

    private String nmUserid;

    public AplVRicSistemaVersante() {
    }

    public AplVRicSistemaVersante(BigDecimal idSistemaVersante, String nmSistemaVersante,
	    String dsSistemaVersante, String nmProduttore, String dsEmail, String flPec,
	    String flCessato, String flIntegrazione, String flAssociaPersonaFisica,
	    String archivista) {
	this.aplVRicSistemaVersanteId = new AplVRicSistemaVersanteId();
	this.aplVRicSistemaVersanteId.setIdSistemaVersante(idSistemaVersante);
	this.nmSistemaVersante = nmSistemaVersante;
	this.dsSistemaVersante = dsSistemaVersante;
	this.nmProduttore = nmProduttore;
	this.dsEmail = dsEmail;
	this.flPec = flPec;
	this.flCessato = flCessato;
	this.flIntegrazione = flIntegrazione;
	this.flAssociaPersonaFisica = flAssociaPersonaFisica;
	this.archivista = archivista;
    }

    public AplVRicSistemaVersante(BigDecimal idSistemaVersante, String nmSistemaVersante,
	    String dsSistemaVersante, String cdVersione, String nmEnteSiam, String flIntegrazione,
	    String flAssociaPersonaFisica, String dsEmail, String flPec, String cdCapSedeLegale,
	    String dsCittaSedeLegale, String dsViaSedeLegale, BigDecimal idEnteSiam, Date dtIniVal,
	    Date dtFineVal, String dsNote) {
	this.aplVRicSistemaVersanteId = new AplVRicSistemaVersanteId();
	this.aplVRicSistemaVersanteId.setIdSistemaVersante(idSistemaVersante);
	this.nmSistemaVersante = nmSistemaVersante;
	this.dsSistemaVersante = dsSistemaVersante;
	this.cdVersione = cdVersione;
	this.nmProduttore = nmEnteSiam;
	this.flIntegrazione = flIntegrazione;
	this.flAssociaPersonaFisica = flAssociaPersonaFisica;
	this.dsEmail = dsEmail;
	this.flPec = flPec;
	this.cdCapSedeLegale = cdCapSedeLegale;
	this.dsCittaSedeLegale = dsCittaSedeLegale;
	this.dsViaSedeLegale = dsViaSedeLegale;
	this.idEnteSiam = idEnteSiam;
	this.dtIniVal = dtIniVal;
	this.dtFineVal = dtFineVal;
	this.dsNote = dsNote;
    }

    @Column(name = "ARCHIVISTA")
    public String getArchivista() {
	return this.archivista;
    }

    public void setArchivista(String archivista) {
	this.archivista = archivista;
    }

    @Column(name = "CD_CAP_SEDE_LEGALE")
    public String getCdCapSedeLegale() {
	return this.cdCapSedeLegale;
    }

    public void setCdCapSedeLegale(String cdCapSedeLegale) {
	this.cdCapSedeLegale = cdCapSedeLegale;
    }

    @Column(name = "CD_VERSIONE")
    public String getCdVersione() {
	return this.cdVersione;
    }

    public void setCdVersione(String cdVersione) {
	this.cdVersione = cdVersione;
    }

    @Column(name = "DL_COMPOSITO_ORGANIZ")
    public String getDlCompositoOrganiz() {
	return this.dlCompositoOrganiz;
    }

    public void setDlCompositoOrganiz(String dlCompositoOrganiz) {
	this.dlCompositoOrganiz = dlCompositoOrganiz;
    }

    @Column(name = "DS_CITTA_SEDE_LEGALE")
    public String getDsCittaSedeLegale() {
	return this.dsCittaSedeLegale;
    }

    public void setDsCittaSedeLegale(String dsCittaSedeLegale) {
	this.dsCittaSedeLegale = dsCittaSedeLegale;
    }

    @Column(name = "DS_EMAIL")
    public String getDsEmail() {
	return this.dsEmail;
    }

    public void setDsEmail(String dsEmail) {
	this.dsEmail = dsEmail;
    }

    @Column(name = "DS_NOTE")
    public String getDsNote() {
	return this.dsNote;
    }

    public void setDsNote(String dsNote) {
	this.dsNote = dsNote;
    }

    @Column(name = "DS_SISTEMA_VERSANTE")
    public String getDsSistemaVersante() {
	return this.dsSistemaVersante;
    }

    public void setDsSistemaVersante(String dsSistemaVersante) {
	this.dsSistemaVersante = dsSistemaVersante;
    }

    @Column(name = "DS_VIA_SEDE_LEGALE")
    public String getDsViaSedeLegale() {
	return this.dsViaSedeLegale;
    }

    public void setDsViaSedeLegale(String dsViaSedeLegale) {
	this.dsViaSedeLegale = dsViaSedeLegale;
    }

    @Column(name = "FL_INTEGRAZIONE", columnDefinition = "char(1)")
    public String getFlIntegrazione() {
	return this.flIntegrazione;
    }

    public void setFlIntegrazione(String flIntegrazione) {
	this.flIntegrazione = flIntegrazione;
    }

    @Column(name = "FL_ASSOCIA_PERSONA_FISICA", columnDefinition = "char(1)")
    public String getFlAssociaPersonaFisica() {
	return this.flAssociaPersonaFisica;
    }

    public void setFlAssociaPersonaFisica(String flAssociaPersonaFisica) {
	this.flAssociaPersonaFisica = flAssociaPersonaFisica;
    }

    @Column(name = "FL_CESSATO", columnDefinition = "char(1)")
    public String getFlCessato() {
	return this.flCessato;
    }

    public void setFlCessato(String flCessato) {
	this.flCessato = flCessato;
    }

    @Column(name = "FL_PEC", columnDefinition = "char(1)")
    public String getFlPec() {
	return this.flPec;
    }

    public void setFlPec(String flPec) {
	this.flPec = flPec;
    }

    @Column(name = "ID_ENTE_SIAM")
    public BigDecimal getIdEnteSiam() {
	return this.idEnteSiam;
    }

    public void setIdEnteSiam(BigDecimal idEnteSiam) {
	this.idEnteSiam = idEnteSiam;
    }

    @Column(name = "ID_ORGANIZ_APPLIC")
    public BigDecimal getIdOrganizApplic() {
	return this.idOrganizApplic;
    }

    public void setIdOrganizApplic(BigDecimal idOrganizApplic) {
	this.idOrganizApplic = idOrganizApplic;
    }

    @Column(name = "ID_USER_IAM_ARK")
    public BigDecimal getIdUserIamArk() {
	return this.idUserIamArk;
    }

    public void setIdUserIamArk(BigDecimal idUserIamArk) {
	this.idUserIamArk = idUserIamArk;
    }

    @Column(name = "ID_USER_IAM_COR")
    public BigDecimal getIdUserIamCor() {
	return this.idUserIamCor;
    }

    public void setIdUserIamCor(BigDecimal idUserIamCor) {
	this.idUserIamCor = idUserIamCor;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
	return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
	this.nmApplic = nmApplic;
    }

    @Column(name = "NM_PRODUTTORE")
    public String getNmProduttore() {
	return this.nmProduttore;
    }

    public void setNmProduttore(String nmProduttore) {
	this.nmProduttore = nmProduttore;
    }

    @Column(name = "NM_SISTEMA_VERSANTE")
    public String getNmSistemaVersante() {
	return this.nmSistemaVersante;
    }

    public void setNmSistemaVersante(String nmSistemaVersante) {
	this.nmSistemaVersante = nmSistemaVersante;
    }

    @Column(name = "NM_USERID")
    public String getNmUserid() {
	return this.nmUserid;
    }

    public void setNmUserid(String nmUserid) {
	this.nmUserid = nmUserid;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_FINE_VAL")
    public Date getDtFineVal() {
	return this.dtFineVal;
    }

    public void setDtFineVal(Date dtFineVal) {
	this.dtFineVal = dtFineVal;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_INI_VAL")
    public Date getDtIniVal() {
	return this.dtIniVal;
    }

    public void setDtIniVal(Date dtIniVal) {
	this.dtIniVal = dtIniVal;
    }

    private AplVRicSistemaVersanteId aplVRicSistemaVersanteId;

    @EmbeddedId()
    public AplVRicSistemaVersanteId getAplVRicSistemaVersanteId() {
	return aplVRicSistemaVersanteId;
    }

    public void setAplVRicSistemaVersanteId(AplVRicSistemaVersanteId aplVRicSistemaVersanteId) {
	this.aplVRicSistemaVersanteId = aplVRicSistemaVersanteId;
    }
}
