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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the USR_RICH_GEST_USER database table.
 */
@Entity
@Table(name = "USR_RICH_GEST_USER")
public class UsrRichGestUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idRichGestUser;

    private BigDecimal aaRichGestUser;

    private byte[] blRichGestUser;

    private String cdKeyRichGestUser;

    private String cdRegistroRichGestUser;

    private String cdRichGestUser;

    private String dsEmailRich;

    private Date dtRichGestUser;

    private String nmFileRichGestUser;

    private String nmUserRich;

    private String ntRichGestUser;

    private String tiRichGestUser;

    private String tiStatoRichGestUser;

    private String tiUserRich;

    private List<UsrAppartUserRich> usrAppartUserRiches = new ArrayList<>();

    private NtfNotifica ntfNotifica;

    private OrgEnteSiam orgEnteSiam;

    private UsrOrganizIam usrOrganizIam;

    private UsrUser usrUser;

    private List<UsrStatoUser> usrStatoUsers = new ArrayList<>();

    public UsrRichGestUser() {
    }

    @Id
    @Column(name = "ID_RICH_GEST_USER")
    @GenericGenerator(name = "SUSR_RICH_GEST_USER_ID_RICH_GEST_USER_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SUSR_RICH_GEST_USER"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUSR_RICH_GEST_USER_ID_RICH_GEST_USER_GENERATOR")
    public Long getIdRichGestUser() {
	return this.idRichGestUser;
    }

    public void setIdRichGestUser(Long idRichGestUser) {
	this.idRichGestUser = idRichGestUser;
    }

    @Column(name = "AA_RICH_GEST_USER")
    public BigDecimal getAaRichGestUser() {
	return this.aaRichGestUser;
    }

    public void setAaRichGestUser(BigDecimal aaRichGestUser) {
	this.aaRichGestUser = aaRichGestUser;
    }

    @Lob
    @Column(name = "BL_RICH_GEST_USER")
    public byte[] getBlRichGestUser() {
	return this.blRichGestUser;
    }

    public void setBlRichGestUser(byte[] blRichGestUser) {
	this.blRichGestUser = blRichGestUser;
    }

    @Column(name = "CD_KEY_RICH_GEST_USER")
    public String getCdKeyRichGestUser() {
	return this.cdKeyRichGestUser;
    }

    public void setCdKeyRichGestUser(String cdKeyRichGestUser) {
	this.cdKeyRichGestUser = cdKeyRichGestUser;
    }

    @Column(name = "CD_REGISTRO_RICH_GEST_USER")
    public String getCdRegistroRichGestUser() {
	return this.cdRegistroRichGestUser;
    }

    public void setCdRegistroRichGestUser(String cdRegistroRichGestUser) {
	this.cdRegistroRichGestUser = cdRegistroRichGestUser;
    }

    @Column(name = "CD_RICH_GEST_USER")
    public String getCdRichGestUser() {
	return this.cdRichGestUser;
    }

    public void setCdRichGestUser(String cdRichGestUser) {
	this.cdRichGestUser = cdRichGestUser;
    }

    @Column(name = "DS_EMAIL_RICH")
    public String getDsEmailRich() {
	return this.dsEmailRich;
    }

    public void setDsEmailRich(String dsEmailRich) {
	this.dsEmailRich = dsEmailRich;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_RICH_GEST_USER")
    public Date getDtRichGestUser() {
	return this.dtRichGestUser;
    }

    public void setDtRichGestUser(Date dtRichGestUser) {
	this.dtRichGestUser = dtRichGestUser;
    }

    @Column(name = "NM_FILE_RICH_GEST_USER")
    public String getNmFileRichGestUser() {
	return this.nmFileRichGestUser;
    }

    public void setNmFileRichGestUser(String nmFileRichGestUser) {
	this.nmFileRichGestUser = nmFileRichGestUser;
    }

    @Column(name = "NM_USER_RICH")
    public String getNmUserRich() {
	return this.nmUserRich;
    }

    public void setNmUserRich(String nmUserRich) {
	this.nmUserRich = nmUserRich;
    }

    @Column(name = "NT_RICH_GEST_USER")
    public String getNtRichGestUser() {
	return this.ntRichGestUser;
    }

    public void setNtRichGestUser(String ntRichGestUser) {
	this.ntRichGestUser = ntRichGestUser;
    }

    @Column(name = "TI_RICH_GEST_USER")
    public String getTiRichGestUser() {
	return this.tiRichGestUser;
    }

    public void setTiRichGestUser(String tiRichGestUser) {
	this.tiRichGestUser = tiRichGestUser;
    }

    @Column(name = "TI_STATO_RICH_GEST_USER")
    public String getTiStatoRichGestUser() {
	return this.tiStatoRichGestUser;
    }

    public void setTiStatoRichGestUser(String tiStatoRichGestUser) {
	this.tiStatoRichGestUser = tiStatoRichGestUser;
    }

    @Column(name = "TI_USER_RICH")
    public String getTiUserRich() {
	return this.tiUserRich;
    }

    public void setTiUserRich(String tiUserRich) {
	this.tiUserRich = tiUserRich;
    }

    // bi-directional many-to-one association to UsrAppartUserRich
    @OneToMany(mappedBy = "usrRichGestUser")
    public List<UsrAppartUserRich> getUsrAppartUserRiches() {
	return this.usrAppartUserRiches;
    }

    public void setUsrAppartUserRiches(List<UsrAppartUserRich> usrAppartUserRiches) {
	this.usrAppartUserRiches = usrAppartUserRiches;
    }

    // bi-directional many-to-one association to NtfNotifica
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_NOTIFICA_RICH_EVASA")
    public NtfNotifica getNtfNotifica() {
	return this.ntfNotifica;
    }

    public void setNtfNotifica(NtfNotifica ntfNotifica) {
	this.ntfNotifica = ntfNotifica;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_RICH")
    public OrgEnteSiam getOrgEnteSiam() {
	return this.orgEnteSiam;
    }

    public void setOrgEnteSiam(OrgEnteSiam orgEnteSiam) {
	this.orgEnteSiam = orgEnteSiam;
    }

    // bi-directional many-to-one association to UsrOrganizIam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ORGANIZ_IAM")
    public UsrOrganizIam getUsrOrganizIam() {
	return this.usrOrganizIam;
    }

    public void setUsrOrganizIam(UsrOrganizIam usrOrganizIam) {
	this.usrOrganizIam = usrOrganizIam;
    }

    // bi-directional many-to-one association to UsrUser
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USER_IAM_RICH")
    public UsrUser getUsrUser() {
	return this.usrUser;
    }

    public void setUsrUser(UsrUser usrUser) {
	this.usrUser = usrUser;
    }

    // bi-directional many-to-one association to UsrStatoUser
    @OneToMany(mappedBy = "usrRichGestUser", cascade = {
	    CascadeType.PERSIST })
    public List<UsrStatoUser> getUsrStatoUsers() {
	return this.usrStatoUsers;
    }

    public void setUsrStatoUsers(List<UsrStatoUser> usrStatoUsers) {
	this.usrStatoUsers = usrStatoUsers;
    }
}
