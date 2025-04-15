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
import javax.persistence.Lob;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the ORG_ENTE_USER_RIF database table.
 */
@Entity
@Table(name = "ORG_ENTE_USER_RIF")
public class OrgEnteUserRif implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idEnteUserRif;

    private OrgEnteSiam orgEnteSiam;

    private UsrUser usrUser;
    private String qualificaUser;
    private String dlNote;

    private String cdRegistroEnteUserRif;
    private BigDecimal aaEnteUserRif;
    private String cdKeyEnteUserRif;
    private Date dtRegEnteUserRif;
    private String cdEnteUserRif;
    private byte[] blEnteUserRif;
    private String nmFileEnteUserRif;
    private String dsEnteUserRif;

    public OrgEnteUserRif() {
    }

    @Id
    @Column(name = "ID_ENTE_USER_RIF")
    @GenericGenerator(name = "SORG_ENTE_USER_RIF_ID_ENTE_USER_RIF_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SORG_ENTE_USER_RIF"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SORG_ENTE_USER_RIF_ID_ENTE_USER_RIF_GENERATOR")
    public Long getIdEnteUserRif() {
	return this.idEnteUserRif;
    }

    public void setIdEnteUserRif(Long idEnteUserRif) {
	this.idEnteUserRif = idEnteUserRif;
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

    // bi-directional many-to-one association to UsrUser
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USER_IAM")
    public UsrUser getUsrUser() {
	return this.usrUser;
    }

    public void setUsrUser(UsrUser usrUser) {
	this.usrUser = usrUser;
    }

    @Column(name = "QUALIFICA_USER")
    public String getQualificaUser() {
	return this.qualificaUser;
    }

    public void setQualificaUser(String qualificaUser) {
	this.qualificaUser = qualificaUser;
    }

    @Column(name = "DL_NOTE")
    public String getDlNote() {
	return this.dlNote;
    }

    public void setDlNote(String dlNote) {
	this.dlNote = dlNote;
    }

    @Column(name = "CD_REGISTRO_ENTE_USER_RIF")
    public String getCdRegistroEnteUserRif() {
	return this.cdRegistroEnteUserRif;
    }

    public void setCdRegistroEnteUserRif(String cdRegistroEnteUserRif) {
	this.cdRegistroEnteUserRif = cdRegistroEnteUserRif;
    }

    @Column(name = "AA_ENTE_USER_RIF")
    public BigDecimal getAaEnteUserRif() {
	return this.aaEnteUserRif;
    }

    public void setAaEnteUserRif(BigDecimal aaEnteUserRif) {
	this.aaEnteUserRif = aaEnteUserRif;
    }

    @Column(name = "CD_KEY_ENTE_USER_RIF")
    public String getCdKeyEnteUserRif() {
	return this.cdKeyEnteUserRif;
    }

    public void setCdKeyEnteUserRif(String cdKeyEnteUserRif) {
	this.cdKeyEnteUserRif = cdKeyEnteUserRif;
    }

    @Column(name = "DT_REG_ENTE_USER_RIF")
    public Date getDtRegEnteUserRif() {
	return this.dtRegEnteUserRif;
    }

    public void setDtRegEnteUserRif(Date dtRegEnteUserRif) {
	this.dtRegEnteUserRif = dtRegEnteUserRif;
    }

    @Column(name = "CD_ENTE_USER_RIF")
    public String getCdEnteUserRif() {
	return this.cdEnteUserRif;
    }

    public void setCdEnteUserRif(String cdEnteUserRif) {
	this.cdEnteUserRif = cdEnteUserRif;
    }

    @Lob
    @Column(name = "BL_ENTE_USER_RIF")
    public byte[] getBlEnteUserRif() {
	return this.blEnteUserRif;
    }

    public void setBlEnteUserRif(byte[] blEnteUserRif) {
	this.blEnteUserRif = blEnteUserRif;
    }

    @Column(name = "NM_FILE_ENTE_USER_RIF")
    public String getNmFileEnteUserRif() {
	return this.nmFileEnteUserRif;
    }

    public void setNmFileEnteUserRif(String nmFileEnteUserRif) {
	this.nmFileEnteUserRif = nmFileEnteUserRif;
    }

    @Column(name = "DS_ENTE_USER_RIF")
    public String getDsEnteUserRif() {
	return this.dsEnteUserRif;
    }

    public void setDsEnteUserRif(String dsEnteUserRif) {
	this.dsEnteUserRif = dsEnteUserRif;
    }

}
