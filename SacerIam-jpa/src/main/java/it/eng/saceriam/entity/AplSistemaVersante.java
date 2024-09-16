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
 * The persistent class for the APL_SISTEMA_VERSANTE database table.
 */
@Entity
@Table(name = "APL_SISTEMA_VERSANTE")
public class AplSistemaVersante implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idSistemaVersante;

    private String cdVersione;

    private String dsSistemaVersante;

    private String dsEmail;

    private String flAssociaPersonaFisica;

    private String flIntegrazione;

    private String flPec;

    private String dsNote;

    private Date dtFineVal;

    private Date dtIniVal;

    private String nmSistemaVersante;

    private OrgEnteSiam orgEnteSiam;

    private List<AplSistemaVersArkRif> aplSistemaVersArkRifs = new ArrayList<>();

    private List<AplSistemaVersanteUserRef> aplSistemaVersanteUserRefs = new ArrayList<>();

    private List<OrgServizioErog> orgServizioErogs = new ArrayList<>();

    private List<UsrUser> usrUsers = new ArrayList<>();

    public AplSistemaVersante() {
    }

    @Id
    @Column(name = "ID_SISTEMA_VERSANTE")
    @GenericGenerator(name = "SAPL_SISTEMA_VERSANTE_ID_SISTEMA_VERSANTE_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SAPL_SISTEMA_VERSANTE"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAPL_SISTEMA_VERSANTE_ID_SISTEMA_VERSANTE_GENERATOR")
    public Long getIdSistemaVersante() {
        return this.idSistemaVersante;
    }

    public void setIdSistemaVersante(Long idSistemaVersante) {
        this.idSistemaVersante = idSistemaVersante;
    }

    @Column(name = "CD_VERSIONE")
    public String getCdVersione() {
        return this.cdVersione;
    }

    public void setCdVersione(String cdVersione) {
        this.cdVersione = cdVersione;
    }

    @Column(name = "DS_SISTEMA_VERSANTE")
    public String getDsSistemaVersante() {
        return this.dsSistemaVersante;
    }

    public void setDsSistemaVersante(String dsSistemaVersante) {
        this.dsSistemaVersante = dsSistemaVersante;
    }

    @Column(name = "DS_EMAIL")
    public String getDsEmail() {
        return this.dsEmail;
    }

    public void setDsEmail(String dsEmail) {
        this.dsEmail = dsEmail;
    }

    @Column(name = "FL_ASSOCIA_PERSONA_FISICA", columnDefinition = "char(1)")
    public String getFlAssociaPersonaFisica() {
        return this.flAssociaPersonaFisica;
    }

    public void setFlAssociaPersonaFisica(String flAssociaPersonaFisica) {
        this.flAssociaPersonaFisica = flAssociaPersonaFisica;
    }

    @Column(name = "FL_INTEGRAZIONE", columnDefinition = "char(1)")
    public String getFlIntegrazione() {
        return this.flIntegrazione;
    }

    public void setFlIntegrazione(String flIntegrazione) {
        this.flIntegrazione = flIntegrazione;
    }

    @Column(name = "FL_PEC", columnDefinition = "char(1)")
    public String getFlPec() {
        return this.flPec;
    }

    public void setFlPec(String flPec) {
        this.flPec = flPec;
    }

    @Column(name = "NM_SISTEMA_VERSANTE")
    public String getNmSistemaVersante() {
        return this.nmSistemaVersante;
    }

    public void setNmSistemaVersante(String nmSistemaVersante) {
        this.nmSistemaVersante = nmSistemaVersante;
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

    // bi-directional many-to-one association to AplSistemaVersArkRif
    @OneToMany(mappedBy = "aplSistemaVersante")
    public List<AplSistemaVersArkRif> getAplSistemaVersArkRifs() {
        return this.aplSistemaVersArkRifs;
    }

    public void setAplSistemaVersArkRifs(List<AplSistemaVersArkRif> aplSistemaVersArkRifs) {
        this.aplSistemaVersArkRifs = aplSistemaVersArkRifs;
    }

    // bi-directional many-to-one association to OrgServizioErog
    @OneToMany(mappedBy = "aplSistemaVersante")
    public List<OrgServizioErog> getOrgServizioErogs() {
        return this.orgServizioErogs;
    }

    public void setOrgServizioErogs(List<OrgServizioErog> orgServizioErogs) {
        this.orgServizioErogs = orgServizioErogs;
    }

    public OrgServizioErog addOrgServizioErog(OrgServizioErog orgServizioErog) {
        getOrgServizioErogs().add(orgServizioErog);
        orgServizioErog.setAplSistemaVersante(this);
        return orgServizioErog;
    }

    public OrgServizioErog removeOrgServizioErog(OrgServizioErog orgServizioErog) {
        getOrgServizioErogs().remove(orgServizioErog);
        orgServizioErog.setAplSistemaVersante(null);
        return orgServizioErog;
    }

    // bi-directional many-to-one association to AplSistemaVersanteUserRef
    @OneToMany(mappedBy = "aplSistemaVersante")
    public List<AplSistemaVersanteUserRef> getAplSistemaVersanteUserRefs() {
        return this.aplSistemaVersanteUserRefs;
    }

    public void setAplSistemaVersanteUserRefs(List<AplSistemaVersanteUserRef> aplSistemaVersanteUserRefs) {
        this.aplSistemaVersanteUserRefs = aplSistemaVersanteUserRefs;
    }

    // bi-directional many-to-one association to UsrUser
    @OneToMany(mappedBy = "aplSistemaVersante")
    public List<UsrUser> getUsrUsers() {
        return this.usrUsers;
    }

    public void setUsrUsers(List<UsrUser> usrUsers) {
        this.usrUsers = usrUsers;
    }

    public UsrUser addUsrUser(UsrUser usrUser) {
        getUsrUsers().add(usrUser);
        usrUser.setAplSistemaVersante(this);
        return usrUser;
    }

    public UsrUser removeUsrUser(UsrUser usrUser) {
        getUsrUsers().remove(usrUser);
        usrUser.setAplSistemaVersante(null);
        return usrUser;
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

    @Column(name = "DS_NOTE")
    public String getDsNote() {
        return this.dsNote;
    }

    public void setDsNote(String dsNote) {
        this.dsNote = dsNote;
    }
}
