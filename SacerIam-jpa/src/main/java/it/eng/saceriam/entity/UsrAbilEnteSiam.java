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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the USR_ABIL_ENTE_SIAM database table.
 */
@Entity
@Table(name = "USR_ABIL_ENTE_SIAM")
public class UsrAbilEnteSiam implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idAbilEnteSiam;

    private String dsCausaleAbil;

    private String flAbilAutomatica;

    private OrgAccordoVigil orgAccordoVigil;

    private OrgAppartCollegEnti orgAppartCollegEnti;

    private OrgEnteSiam orgEnteSiam;

    private OrgSuptEsternoEnteConvenz orgSuptEsternoEnteConvenz;

    private UsrDichAbilEnteConvenz usrDichAbilEnteConvenz;

    private UsrUser usrUser;

    public UsrAbilEnteSiam() {
    }

    @Id
    @Column(name = "ID_ABIL_ENTE_SIAM")
    @GenericGenerator(name = "SUSR_ABIL_ENTE_SIAM_ID_ABIL_ENTE_SIAM_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SUSR_ABIL_ENTE_SIAM"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUSR_ABIL_ENTE_SIAM_ID_ABIL_ENTE_SIAM_GENERATOR")
    public Long getIdAbilEnteSiam() {
	return this.idAbilEnteSiam;
    }

    public void setIdAbilEnteSiam(Long idAbilEnteSiam) {
	this.idAbilEnteSiam = idAbilEnteSiam;
    }

    @Column(name = "DS_CAUSALE_ABIL")
    public String getDsCausaleAbil() {
	return this.dsCausaleAbil;
    }

    public void setDsCausaleAbil(String dsCausaleAbil) {
	this.dsCausaleAbil = dsCausaleAbil;
    }

    @Column(name = "FL_ABIL_AUTOMATICA", columnDefinition = "char(1)")
    public String getFlAbilAutomatica() {
	return this.flAbilAutomatica;
    }

    public void setFlAbilAutomatica(String flAbilAutomatica) {
	this.flAbilAutomatica = flAbilAutomatica;
    }

    // bi-directional many-to-one association to OrgAccordoVigil
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ACCORDO_VIGIL")
    public OrgAccordoVigil getOrgAccordoVigil() {
	return this.orgAccordoVigil;
    }

    public void setOrgAccordoVigil(OrgAccordoVigil orgAccordoVigil) {
	this.orgAccordoVigil = orgAccordoVigil;
    }

    // bi-directional many-to-one association to OrgAppartCollegEnti
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_APPART_COLLEG_ENTI")
    public OrgAppartCollegEnti getOrgAppartCollegEnti() {
	return this.orgAppartCollegEnti;
    }

    public void setOrgAppartCollegEnti(OrgAppartCollegEnti orgAppartCollegEnti) {
	this.orgAppartCollegEnti = orgAppartCollegEnti;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_SIAM")
    public OrgEnteSiam getOrgEnteSiam() {
	return this.orgEnteSiam;
    }

    public void setOrgEnteSiam(OrgEnteSiam orgEnteSiam) {
	this.orgEnteSiam = orgEnteSiam;
    }

    // bi-directional many-to-one association to OrgSuptEsternoEnteConvenz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SUPT_EST_ENTE_CONVENZ")
    public OrgSuptEsternoEnteConvenz getOrgSuptEsternoEnteConvenz() {
	return this.orgSuptEsternoEnteConvenz;
    }

    public void setOrgSuptEsternoEnteConvenz(OrgSuptEsternoEnteConvenz orgSuptEsternoEnteConvenz) {
	this.orgSuptEsternoEnteConvenz = orgSuptEsternoEnteConvenz;
    }

    // bi-directional many-to-one association to UsrDichAbilEnteConvenz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DICH_ABIL_ENTE_CONVENZ")
    public UsrDichAbilEnteConvenz getUsrDichAbilEnteConvenz() {
	return this.usrDichAbilEnteConvenz;
    }

    public void setUsrDichAbilEnteConvenz(UsrDichAbilEnteConvenz usrDichAbilEnteConvenz) {
	this.usrDichAbilEnteConvenz = usrDichAbilEnteConvenz;
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

    @PrePersist
    void preInsert() {
	if (this.flAbilAutomatica == null) {
	    this.flAbilAutomatica = "0";
	}
    }
}
