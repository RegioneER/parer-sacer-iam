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
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

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

import org.apache.commons.lang3.Validate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the MON_CONTA_UD_DOC_COMP database table.
 */
@Entity
@Table(schema = "SACER", name = "MON_CONTA_UD_DOC_COMP")
public class MonContaUdDocComp implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idContaUdDocComp;

    private BigDecimal aaKeyUnitaDoc;

    private Date dtRifConta;

    private BigDecimal idStrut;

    private BigDecimal niCompAgg;

    private BigDecimal niCompAnnulUd;

    private BigDecimal niCompVers;

    private BigDecimal niDocAgg;

    private BigDecimal niDocAnnulUd;

    private BigDecimal niDocVers;

    private BigDecimal niSizeAgg;

    private BigDecimal niSizeAnnulUd;

    private BigDecimal niSizeVers;

    private BigDecimal niUnitaDocAnnul;

    private BigDecimal niUnitaDocVers;

    private Long idDecRegistroUnitaDoc;

    private Long idDecTipoDoc;

    private DecTipoUnitaDoc decTipoUnitaDoc;

    private Long idDecTipoUnitaDoc;

    private Long idOrgSubStrut;

    public MonContaUdDocComp() {/* Hibernate */
    }

    @Id
    @Column(name = "ID_CONTA_UD_DOC_COMP")
    public Long getIdContaUdDocComp() {
	return this.idContaUdDocComp;
    }

    public void setIdContaUdDocComp(Long idContaUdDocComp) {
	this.idContaUdDocComp = idContaUdDocComp;
    }

    @Column(name = "AA_KEY_UNITA_DOC")
    public BigDecimal getAaKeyUnitaDoc() {
	return this.aaKeyUnitaDoc;
    }

    public void setAaKeyUnitaDoc(BigDecimal aaKeyUnitaDoc) {
	this.aaKeyUnitaDoc = aaKeyUnitaDoc;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_RIF_CONTA")
    public Date getDtRifConta() {
	return this.dtRifConta;
    }

    public void setDtRifConta(Date dtRifConta) {
	this.dtRifConta = dtRifConta;
    }

    @Column(name = "ID_STRUT")
    public BigDecimal getIdStrut() {
	return this.idStrut;
    }

    public void setIdStrut(BigDecimal idStrut) {
	this.idStrut = idStrut;
    }

    @Column(name = "NI_COMP_AGG")
    public BigDecimal getNiCompAgg() {
	return this.niCompAgg;
    }

    public void setNiCompAgg(BigDecimal niCompAgg) {
	this.niCompAgg = niCompAgg;
    }

    @Column(name = "NI_COMP_ANNUL_UD")
    public BigDecimal getNiCompAnnulUd() {
	return this.niCompAnnulUd;
    }

    public void setNiCompAnnulUd(BigDecimal niCompAnnulUd) {
	this.niCompAnnulUd = niCompAnnulUd;
    }

    @Column(name = "NI_COMP_VERS")
    public BigDecimal getNiCompVers() {
	return this.niCompVers;
    }

    public void setNiCompVers(BigDecimal niCompVers) {
	this.niCompVers = niCompVers;
    }

    @Column(name = "NI_DOC_AGG")
    public BigDecimal getNiDocAgg() {
	return this.niDocAgg;
    }

    public void setNiDocAgg(BigDecimal niDocAgg) {
	this.niDocAgg = niDocAgg;
    }

    @Column(name = "NI_DOC_ANNUL_UD")
    public BigDecimal getNiDocAnnulUd() {
	return this.niDocAnnulUd;
    }

    public void setNiDocAnnulUd(BigDecimal niDocAnnulUd) {
	this.niDocAnnulUd = niDocAnnulUd;
    }

    @Column(name = "NI_DOC_VERS")
    public BigDecimal getNiDocVers() {
	return this.niDocVers;
    }

    public void setNiDocVers(BigDecimal niDocVers) {
	this.niDocVers = niDocVers;
    }

    @Column(name = "NI_SIZE_AGG")
    public BigDecimal getNiSizeAgg() {
	return this.niSizeAgg;
    }

    public void setNiSizeAgg(BigDecimal niSizeAgg) {
	this.niSizeAgg = niSizeAgg;
    }

    @Column(name = "NI_SIZE_ANNUL_UD")
    public BigDecimal getNiSizeAnnulUd() {
	return this.niSizeAnnulUd;
    }

    public void setNiSizeAnnulUd(BigDecimal niSizeAnnulUd) {
	this.niSizeAnnulUd = niSizeAnnulUd;
    }

    @Column(name = "NI_SIZE_VERS")
    public BigDecimal getNiSizeVers() {
	return this.niSizeVers;
    }

    public void setNiSizeVers(BigDecimal niSizeVers) {
	this.niSizeVers = niSizeVers;
    }

    @Column(name = "NI_UNITA_DOC_ANNUL")
    public BigDecimal getNiUnitaDocAnnul() {
	return this.niUnitaDocAnnul;
    }

    public void setNiUnitaDocAnnul(BigDecimal niUnitaDocAnnul) {
	this.niUnitaDocAnnul = niUnitaDocAnnul;
    }

    @Column(name = "NI_UNITA_DOC_VERS")
    public BigDecimal getNiUnitaDocVers() {
	return this.niUnitaDocVers;
    }

    public void setNiUnitaDocVers(BigDecimal niUnitaDocVers) {
	this.niUnitaDocVers = niUnitaDocVers;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_UNITA_DOC", insertable = false, updatable = false)
    public DecTipoUnitaDoc getDecTipoUnitaDoc() {
	return this.decTipoUnitaDoc;
    }

    public void setDecTipoUnitaDoc(DecTipoUnitaDoc decTipoUnitaDoc) {
	this.decTipoUnitaDoc = decTipoUnitaDoc;
    }

    @Column(name = "ID_REGISTRO_UNITA_DOC")
    public Long getIdDecRegistroUnitaDoc() {
	return idDecRegistroUnitaDoc;
    }

    public void setIdDecRegistroUnitaDoc(Long idDecRegistroUnitaDoc) {
	this.idDecRegistroUnitaDoc = idDecRegistroUnitaDoc;
    }

    @Column(name = "ID_TIPO_DOC_PRINC")
    public Long getIdDecTipoDoc() {
	return idDecTipoDoc;
    }

    public void setIdDecTipoDoc(Long idDecTipoDoc) {
	this.idDecTipoDoc = idDecTipoDoc;
    }

    @Column(name = "ID_TIPO_UNITA_DOC")
    public Long getIdDecTipoUnitaDoc() {
	return idDecTipoUnitaDoc;
    }

    public void setIdDecTipoUnitaDoc(Long idDecTipoUnitaDoc) {
	this.idDecTipoUnitaDoc = idDecTipoUnitaDoc;
    }

    @Column(name = "ID_SUB_STRUT")
    public Long getIdOrgSubStrut() {
	return idOrgSubStrut;
    }

    public void setIdOrgSubStrut(Long idOrgSubStrut) {
	this.idOrgSubStrut = idOrgSubStrut;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 89 * hash + Objects.hashCode(this.aaKeyUnitaDoc);
	hash = 89 * hash + Objects.hashCode(this.dtRifConta);
	hash = 89 * hash + Objects.hashCode(this.idStrut);
	hash = 89 * hash + (int) (this.idDecRegistroUnitaDoc ^ (this.idDecRegistroUnitaDoc >>> 32));
	hash = 89 * hash + (int) (this.idDecTipoDoc ^ (this.idDecTipoDoc >>> 32));
	hash = 89 * hash + (int) (this.idDecTipoUnitaDoc ^ (this.idDecTipoUnitaDoc >>> 32));
	hash = 89 * hash + (int) (this.idOrgSubStrut ^ (this.idOrgSubStrut >>> 32));
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final MonContaUdDocComp other = (MonContaUdDocComp) obj;
	if (!Objects.equals(this.aaKeyUnitaDoc, other.aaKeyUnitaDoc)) {
	    return false;
	}
	if (!Objects.equals(this.dtRifConta, other.dtRifConta)) {
	    return false;
	}
	if (!Objects.equals(this.idStrut, other.idStrut)) {
	    return false;
	}
	if (!Objects.equals(this.idDecRegistroUnitaDoc, other.idDecRegistroUnitaDoc)) {
	    return false;
	}
	if (!Objects.equals(this.idDecTipoDoc, other.idDecTipoDoc)) {
	    return false;
	}
	if (!Objects.equals(this.idDecTipoUnitaDoc, other.idDecTipoUnitaDoc)) {
	    return false;
	}
	return Objects.equals(this.idOrgSubStrut, other.idOrgSubStrut);
    }

    public enum TipoConteggio {

	UD_VERS, DOC_VERS, COMP_VERS, BYTE_VERS, DOC_AGG, COMP_AGG, BYTE_AGG, UD_ANNULL, DOC_ANNULL,
	COMP_ANNULL, BYTE_ANNULL
    }
}
