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
import java.util.ArrayList;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the ORG_AMBIENTE_ENTE_CONVENZ database table.
 */
@Entity
@Table(name = "ORG_AMBIENTE_ENTE_CONVENZ")
public class OrgAmbienteEnteConvenz implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idAmbienteEnteConvenz;

    private String dsAmbienteEnteConvenz;

    private String nmAmbienteEnteConvenz;

    private String dsNote;

    private Date dtIniVal;

    private Date dtFineVal;

    private OrgEnteSiam orgEnteSiamByIdEnteConserv;

    private OrgEnteSiam orgEnteSiamByIdEnteGestore;

    private List<OrgEnteSiam> orgEnteSiams = new ArrayList<>();

    private List<UsrDichAbilEnteConvenz> usrDichAbilEnteConvenzs = new ArrayList<>();

    private List<IamValoreParamApplic> iamValoreParamApplics = new ArrayList<>();

    public OrgAmbienteEnteConvenz() {
    }

    @Id
    @Column(name = "ID_AMBIENTE_ENTE_CONVENZ")
    @GenericGenerator(name = "SORG_AMBIENTE_ENTE_CONVENZ_ID_AMBIENTE_ENTE_CONVENZ_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SORG_AMBIENTE_ENTE_CONVENZ"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SORG_AMBIENTE_ENTE_CONVENZ_ID_AMBIENTE_ENTE_CONVENZ_GENERATOR")
    public Long getIdAmbienteEnteConvenz() {
        return this.idAmbienteEnteConvenz;
    }

    public void setIdAmbienteEnteConvenz(Long idAmbienteEnteConvenz) {
        this.idAmbienteEnteConvenz = idAmbienteEnteConvenz;
    }

    @Column(name = "DS_AMBIENTE_ENTE_CONVENZ")
    public String getDsAmbienteEnteConvenz() {
        return this.dsAmbienteEnteConvenz;
    }

    public void setDsAmbienteEnteConvenz(String dsAmbienteEnteConvenz) {
        this.dsAmbienteEnteConvenz = dsAmbienteEnteConvenz;
    }

    @Column(name = "NM_AMBIENTE_ENTE_CONVENZ")
    public String getNmAmbienteEnteConvenz() {
        return this.nmAmbienteEnteConvenz;
    }

    public void setNmAmbienteEnteConvenz(String nmAmbienteEnteConvenz) {
        this.nmAmbienteEnteConvenz = nmAmbienteEnteConvenz;
    }

    @Column(name = "DS_NOTE")
    public String getDsNote() {
        return this.dsNote;
    }

    public void setDsNote(String dsNote) {
        this.dsNote = dsNote;
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
    @Column(name = "DT_FINE_VAL")
    public Date getDtFineVal() {
        return this.dtFineVal;
    }

    public void setDtFineVal(Date dtFineVal) {
        this.dtFineVal = dtFineVal;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_CONSERV")
    public OrgEnteSiam getOrgEnteSiamByIdEnteConserv() {
        return this.orgEnteSiamByIdEnteConserv;
    }

    public void setOrgEnteSiamByIdEnteConserv(OrgEnteSiam orgEnteSiamByIdEnteConserv) {
        this.orgEnteSiamByIdEnteConserv = orgEnteSiamByIdEnteConserv;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_GESTORE")
    public OrgEnteSiam getOrgEnteSiamByIdEnteGestore() {
        return this.orgEnteSiamByIdEnteGestore;
    }

    public void setOrgEnteSiamByIdEnteGestore(OrgEnteSiam orgEnteSiamByIdEnteGestore) {
        this.orgEnteSiamByIdEnteGestore = orgEnteSiamByIdEnteGestore;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @OneToMany(mappedBy = "orgAmbienteEnteConvenz")
    public List<OrgEnteSiam> getOrgEnteSiams() {
        return this.orgEnteSiams;
    }

    public void setOrgEnteSiams(List<OrgEnteSiam> orgEnteSiams) {
        this.orgEnteSiams = orgEnteSiams;
    }

    public OrgEnteSiam addOrgEnteSiam(OrgEnteSiam orgEnteSiam) {
        getOrgEnteSiams().add(orgEnteSiam);
        orgEnteSiam.setOrgAmbienteEnteConvenz(this);
        return orgEnteSiam;
    }

    public OrgEnteSiam removeOrgEnteConvenz(OrgEnteSiam orgEnteSiam) {
        getOrgEnteSiams().remove(orgEnteSiam);
        orgEnteSiam.setOrgAmbienteEnteConvenz(null);
        return orgEnteSiam;
    }

    // bi-directional many-to-one association to UsrDichAbilEnteConvenz
    @OneToMany(mappedBy = "orgAmbienteEnteConvenz")
    public List<UsrDichAbilEnteConvenz> getUsrDichAbilEnteConvenzs() {
        return this.usrDichAbilEnteConvenzs;
    }

    public void setUsrDichAbilEnteConvenzs(List<UsrDichAbilEnteConvenz> usrDichAbilEnteConvenzs) {
        this.usrDichAbilEnteConvenzs = usrDichAbilEnteConvenzs;
    }

    public UsrDichAbilEnteConvenz addUsrDichAbilEnteConvenz(UsrDichAbilEnteConvenz usrDichAbilEnteConvenz) {
        getUsrDichAbilEnteConvenzs().add(usrDichAbilEnteConvenz);
        usrDichAbilEnteConvenz.setOrgAmbienteEnteConvenz(this);
        return usrDichAbilEnteConvenz;
    }

    public UsrDichAbilEnteConvenz removeUsrDichAbilEnteConvenz(UsrDichAbilEnteConvenz usrDichAbilEnteConvenz) {
        getUsrDichAbilEnteConvenzs().remove(usrDichAbilEnteConvenz);
        usrDichAbilEnteConvenz.setOrgAmbienteEnteConvenz(null);
        return usrDichAbilEnteConvenz;
    }

    // bi-directional many-to-one association to IamValoreParamApplic
    @OneToMany(mappedBy = "orgAmbienteEnteConvenz")
    public List<IamValoreParamApplic> getIamValoreParamApplics() {
        return this.iamValoreParamApplics;
    }

    public void setIamValoreParamApplics(List<IamValoreParamApplic> iamValoreParamApplics) {
        this.iamValoreParamApplics = iamValoreParamApplics;
    }

    public IamValoreParamApplic addIamValoreParamApplic(IamValoreParamApplic iamValoreParamApplic) {
        getIamValoreParamApplics().add(iamValoreParamApplic);
        iamValoreParamApplic.setOrgAmbienteEnteConvenz(this);
        return iamValoreParamApplic;
    }

    public IamValoreParamApplic removeIamValoreParamApplic(IamValoreParamApplic iamValoreParamApplic) {
        getIamValoreParamApplics().remove(iamValoreParamApplic);
        iamValoreParamApplic.setOrgAmbienteEnteConvenz(null);
        return iamValoreParamApplic;
    }
}
