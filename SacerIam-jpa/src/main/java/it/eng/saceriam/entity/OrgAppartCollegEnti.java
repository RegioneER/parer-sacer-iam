/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna
 * <p/>
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the ORG_APPART_COLLEG_ENTI database table.
 */
@Entity
@Table(name = "ORG_APPART_COLLEG_ENTI")
@NamedQuery(name = "OrgAppartCollegEnti.findAll", query = "SELECT o FROM OrgAppartCollegEnti o")
public class OrgAppartCollegEnti implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idAppartCollegEnti;

    private Date dtIniVal;

    private Date dtFinVal;

    private OrgEnteSiam orgEnteSiam;

    private OrgCollegEntiConvenz orgCollegEntiConvenz;
    private List<UsrAbilDati> usrAbilDatis;
    private List<UsrAbilEnteSiam> usrAbilEnteSiams;
    private List<UsrAbilOrganiz> usrAbilOrganizs;
    private List<UsrDichAbilDati> usrDichAbilDatis;
    private List<UsrDichAbilEnteConvenz> usrDichAbilEnteConvenzs;
    private List<UsrDichAbilOrganiz> usrDichAbilOrganizs;

    public OrgAppartCollegEnti() {
    }

    @Id
    @Column(name = "ID_APPART_COLLEG_ENTI")
    @GenericGenerator(name = "SORG_APPART_COLLEG_ENTI_ID_APPART_COLLEG_ENTI_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SORG_APPART_COLLEG_ENTI"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SORG_APPART_COLLEG_ENTI_ID_APPART_COLLEG_ENTI_GENERATOR")
    public Long getIdAppartCollegEnti() {
        return this.idAppartCollegEnti;
    }

    public void setIdAppartCollegEnti(Long idAppartCollegEnti) {
        this.idAppartCollegEnti = idAppartCollegEnti;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_INI_VAL")
    public Date getDtIniVal() {
        return this.dtIniVal;
    }

    public void setDtIniVal(Date dtIniVal) {
        this.dtIniVal = dtIniVal;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FIN_VAL")
    public Date getDtFinVal() {
        return this.dtFinVal;
    }

    public void setDtFinVal(Date dtFinVal) {
        this.dtFinVal = dtFinVal;
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

    // bi-directional many-to-one association to OrgCollegEntiConvenz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_COLLEG_ENTI_CONVENZ")
    public OrgCollegEntiConvenz getOrgCollegEntiConvenz() {
        return this.orgCollegEntiConvenz;
    }

    public void setOrgCollegEntiConvenz(OrgCollegEntiConvenz orgCollegEntiConvenz) {
        this.orgCollegEntiConvenz = orgCollegEntiConvenz;
    }

    // bi-directional many-to-one association to UsrAbilDati
    @OneToMany(mappedBy = "orgAppartCollegEnti")
    public List<UsrAbilDati> getUsrAbilDatis() {
        return this.usrAbilDatis;
    }

    public void setUsrAbilDatis(List<UsrAbilDati> usrAbilDatis) {
        this.usrAbilDatis = usrAbilDatis;
    }

    public UsrAbilDati addUsrAbilDati(UsrAbilDati usrAbilDati) {
        getUsrAbilDatis().add(usrAbilDati);
        usrAbilDati.setOrgAppartCollegEnti(this);

        return usrAbilDati;
    }

    public UsrAbilDati removeUsrAbilDati(UsrAbilDati usrAbilDati) {
        getUsrAbilDatis().remove(usrAbilDati);
        usrAbilDati.setOrgAppartCollegEnti(null);

        return usrAbilDati;
    }

    // bi-directional many-to-one association to UsrAbilEnteSiam
    @OneToMany(mappedBy = "orgAppartCollegEnti")
    public List<UsrAbilEnteSiam> getUsrAbilEnteSiams() {
        return this.usrAbilEnteSiams;
    }

    public void setUsrAbilEnteSiams(List<UsrAbilEnteSiam> usrAbilEnteSiams) {
        this.usrAbilEnteSiams = usrAbilEnteSiams;
    }

    public UsrAbilEnteSiam addUsrAbilEnteSiam(UsrAbilEnteSiam usrAbilEnteSiam) {
        getUsrAbilEnteSiams().add(usrAbilEnteSiam);
        usrAbilEnteSiam.setOrgAppartCollegEnti(this);

        return usrAbilEnteSiam;
    }

    public UsrAbilEnteSiam removeUsrAbilEnteSiam(UsrAbilEnteSiam usrAbilEnteSiam) {
        getUsrAbilEnteSiams().remove(usrAbilEnteSiam);
        usrAbilEnteSiam.setOrgAppartCollegEnti(null);

        return usrAbilEnteSiam;
    }

    // bi-directional many-to-one association to UsrAbilOrganiz
    @OneToMany(mappedBy = "orgAppartCollegEnti")
    public List<UsrAbilOrganiz> getUsrAbilOrganizs() {
        return this.usrAbilOrganizs;
    }

    public void setUsrAbilOrganizs(List<UsrAbilOrganiz> usrAbilOrganizs) {
        this.usrAbilOrganizs = usrAbilOrganizs;
    }

    public UsrAbilOrganiz addUsrAbilOrganiz(UsrAbilOrganiz usrAbilOrganiz) {
        getUsrAbilOrganizs().add(usrAbilOrganiz);
        usrAbilOrganiz.setOrgAppartCollegEnti(this);

        return usrAbilOrganiz;
    }

    public UsrAbilOrganiz removeUsrAbilOrganiz(UsrAbilOrganiz usrAbilOrganiz) {
        getUsrAbilOrganizs().remove(usrAbilOrganiz);
        usrAbilOrganiz.setOrgAppartCollegEnti(null);

        return usrAbilOrganiz;
    }

    // bi-directional many-to-one association to UsrDichAbilDati
    @OneToMany(mappedBy = "orgAppartCollegEnti")
    public List<UsrDichAbilDati> getUsrDichAbilDatis() {
        return this.usrDichAbilDatis;
    }

    public void setUsrDichAbilDatis(List<UsrDichAbilDati> usrDichAbilDatis) {
        this.usrDichAbilDatis = usrDichAbilDatis;
    }

    public UsrDichAbilDati addUsrDichAbilDati(UsrDichAbilDati usrDichAbilDati) {
        getUsrDichAbilDatis().add(usrDichAbilDati);
        usrDichAbilDati.setOrgAppartCollegEnti(this);

        return usrDichAbilDati;
    }

    public UsrDichAbilDati removeUsrDichAbilDati(UsrDichAbilDati usrDichAbilDati) {
        getUsrDichAbilDatis().remove(usrDichAbilDati);
        usrDichAbilDati.setOrgAppartCollegEnti(null);

        return usrDichAbilDati;
    }

    // bi-directional many-to-one association to UsrDichAbilEnteConvenz
    @OneToMany(mappedBy = "orgAppartCollegEnti")
    public List<UsrDichAbilEnteConvenz> getUsrDichAbilEnteConvenzs() {
        return this.usrDichAbilEnteConvenzs;
    }

    public void setUsrDichAbilEnteConvenzs(List<UsrDichAbilEnteConvenz> usrDichAbilEnteConvenzs) {
        this.usrDichAbilEnteConvenzs = usrDichAbilEnteConvenzs;
    }

    public UsrDichAbilEnteConvenz addUsrDichAbilEnteConvenz(UsrDichAbilEnteConvenz usrDichAbilEnteConvenz) {
        getUsrDichAbilEnteConvenzs().add(usrDichAbilEnteConvenz);
        usrDichAbilEnteConvenz.setOrgAppartCollegEnti(this);

        return usrDichAbilEnteConvenz;
    }

    public UsrDichAbilEnteConvenz removeUsrDichAbilEnteConvenz(UsrDichAbilEnteConvenz usrDichAbilEnteConvenz) {
        getUsrDichAbilEnteConvenzs().remove(usrDichAbilEnteConvenz);
        usrDichAbilEnteConvenz.setOrgAppartCollegEnti(null);

        return usrDichAbilEnteConvenz;
    }

    // bi-directional many-to-one association to UsrDichAbilOrganiz
    @OneToMany(mappedBy = "orgAppartCollegEnti")
    public List<UsrDichAbilOrganiz> getUsrDichAbilOrganizs() {
        return this.usrDichAbilOrganizs;
    }

    public void setUsrDichAbilOrganizs(List<UsrDichAbilOrganiz> usrDichAbilOrganizs) {
        this.usrDichAbilOrganizs = usrDichAbilOrganizs;
    }

    public UsrDichAbilOrganiz addUsrDichAbilOrganiz(UsrDichAbilOrganiz usrDichAbilOrganiz) {
        getUsrDichAbilOrganizs().add(usrDichAbilOrganiz);
        usrDichAbilOrganiz.setOrgAppartCollegEnti(this);

        return usrDichAbilOrganiz;
    }

    public UsrDichAbilOrganiz removeUsrDichAbilOrganiz(UsrDichAbilOrganiz usrDichAbilOrganiz) {
        getUsrDichAbilOrganizs().remove(usrDichAbilOrganiz);
        usrDichAbilOrganiz.setOrgAppartCollegEnti(null);

        return usrDichAbilOrganiz;
    }

}
