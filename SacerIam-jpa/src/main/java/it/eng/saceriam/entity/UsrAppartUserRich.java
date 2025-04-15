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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the USR_APPART_USER_RICH database table.
 */
@Entity
@Table(name = "USR_APPART_USER_RICH")
public class UsrAppartUserRich implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idAppartUserRich;

    private String flAzioneRichEvasa;

    private String nmCognomeUser;

    private String nmNomeUser;

    private String nmUserid;

    private String tiAppartUserRich;

    private String tiAzioneRich;

    private UsrRichGestUser usrRichGestUser;

    private OrgEnteSiam orgEnteSiam;

    private NtfNotifica ntfNotifica1;

    private NtfNotifica ntfNotifica2;

    private UsrUser usrUser;

    public UsrAppartUserRich() {
    }

    public UsrAppartUserRich(UsrUser usrUser, String nmCognomeUser, String nmNomeUser,
	    String nmUserid) {
	this.usrUser = usrUser;
	this.nmCognomeUser = nmCognomeUser;
	this.nmNomeUser = nmNomeUser;
	this.nmUserid = nmUserid;
    }

    @Id
    @Column(name = "ID_APPART_USER_RICH")
    @GenericGenerator(name = "SUSR_APPART_USER_RICH_ID_APPART_USER_RICH_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SUSR_APPART_USER_RICH"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUSR_APPART_USER_RICH_ID_APPART_USER_RICH_GENERATOR")
    public Long getIdAppartUserRich() {
	return this.idAppartUserRich;
    }

    public void setIdAppartUserRich(Long idAppartUserRich) {
	this.idAppartUserRich = idAppartUserRich;
    }

    @Column(name = "FL_AZIONE_RICH_EVASA", columnDefinition = "char(1)")
    public String getFlAzioneRichEvasa() {
	return this.flAzioneRichEvasa;
    }

    public void setFlAzioneRichEvasa(String flAzioneRichEvasa) {
	this.flAzioneRichEvasa = flAzioneRichEvasa;
    }

    @Column(name = "NM_COGNOME_USER")
    public String getNmCognomeUser() {
	return this.nmCognomeUser;
    }

    public void setNmCognomeUser(String nmCognomeUser) {
	this.nmCognomeUser = nmCognomeUser;
    }

    @Column(name = "NM_NOME_USER")
    public String getNmNomeUser() {
	return this.nmNomeUser;
    }

    public void setNmNomeUser(String nmNomeUser) {
	this.nmNomeUser = nmNomeUser;
    }

    @Column(name = "NM_USERID")
    public String getNmUserid() {
	return this.nmUserid;
    }

    public void setNmUserid(String nmUserid) {
	this.nmUserid = nmUserid;
    }

    @Column(name = "TI_APPART_USER_RICH")
    public String getTiAppartUserRich() {
	return this.tiAppartUserRich;
    }

    public void setTiAppartUserRich(String tiAppartUserRich) {
	this.tiAppartUserRich = tiAppartUserRich;
    }

    @Column(name = "TI_AZIONE_RICH")
    public String getTiAzioneRich() {
	return this.tiAzioneRich;
    }

    public void setTiAzioneRich(String tiAzioneRich) {
	this.tiAzioneRich = tiAzioneRich;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_USER")
    public OrgEnteSiam getOrgEnteSiam() {
	return this.orgEnteSiam;
    }

    public void setOrgEnteSiam(OrgEnteSiam orgEnteSiam) {
	this.orgEnteSiam = orgEnteSiam;
    }

    // bi-directional many-to-one association to NtfNotifica
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_NOTIFICA_AZIONE_2")
    public NtfNotifica getNtfNotifica1() {
	return this.ntfNotifica1;
    }

    public void setNtfNotifica1(NtfNotifica ntfNotifica1) {
	this.ntfNotifica1 = ntfNotifica1;
    }

    // bi-directional many-to-one association to NtfNotifica
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_NOTIFICA_AZIONE_1")
    public NtfNotifica getNtfNotifica2() {
	return this.ntfNotifica2;
    }

    public void setNtfNotifica2(NtfNotifica ntfNotifica2) {
	this.ntfNotifica2 = ntfNotifica2;
    }

    // bi-directional many-to-one association to UsrRichGestUser
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RICH_GEST_USER")
    public UsrRichGestUser getUsrRichGestUser() {
	return this.usrRichGestUser;
    }

    public void setUsrRichGestUser(UsrRichGestUser usrRichGestUser) {
	this.usrRichGestUser = usrRichGestUser;
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
}
