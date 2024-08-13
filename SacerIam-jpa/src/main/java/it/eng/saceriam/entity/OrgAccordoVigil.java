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
import java.math.BigDecimal;
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
 * The persistent class for the ORG_ACCORDO_VIGIL database table.
 */
@Entity
@Table(name = "ORG_ACCORDO_VIGIL")
public class OrgAccordoVigil implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idAccordoVigil;

    private BigDecimal aaRepertorio;

    private String cdKeyRepertorio;

    private String cdRegistroRepertorio;

    private String dsFirmatarioEnte;

    private String dsNoteEnteAccordo;

    private Date dtFinVal;

    private Date dtIniVal;

    private Date dtRegAccordo;

    private String nmEnte;

    private String nmStrut;

    private OrgEnteSiam orgEnteSiamByIdEnteConserv;

    private OrgEnteSiam orgEnteSiamByIdEnteOrganoVigil;

    private List<OrgVigilEnteProdut> orgVigilEnteProduts = new ArrayList<>();

    public OrgAccordoVigil() {
    }

    @Id
    @Column(name = "ID_ACCORDO_VIGIL")
    @GenericGenerator(name = "SORG_ACCORDO_VIGIL_ID_ACCORDO_VIGIL_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SORG_ACCORDO_VIGIL"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SORG_ACCORDO_VIGIL_ID_ACCORDO_VIGIL_GENERATOR")
    public Long getIdAccordoVigil() {
        return this.idAccordoVigil;
    }

    public void setIdAccordoVigil(Long idAccordoVigil) {
        this.idAccordoVigil = idAccordoVigil;
    }

    @Column(name = "AA_REPERTORIO")
    public BigDecimal getAaRepertorio() {
        return this.aaRepertorio;
    }

    public void setAaRepertorio(BigDecimal aaRepertorio) {
        this.aaRepertorio = aaRepertorio;
    }

    @Column(name = "CD_KEY_REPERTORIO")
    public String getCdKeyRepertorio() {
        return this.cdKeyRepertorio;
    }

    public void setCdKeyRepertorio(String cdKeyRepertorio) {
        this.cdKeyRepertorio = cdKeyRepertorio;
    }

    @Column(name = "CD_REGISTRO_REPERTORIO")
    public String getCdRegistroRepertorio() {
        return this.cdRegistroRepertorio;
    }

    public void setCdRegistroRepertorio(String cdRegistroRepertorio) {
        this.cdRegistroRepertorio = cdRegistroRepertorio;
    }

    @Column(name = "DS_FIRMATARIO_ENTE")
    public String getDsFirmatarioEnte() {
        return this.dsFirmatarioEnte;
    }

    public void setDsFirmatarioEnte(String dsFirmatarioEnte) {
        this.dsFirmatarioEnte = dsFirmatarioEnte;
    }

    @Column(name = "DS_NOTE_ENTE_ACCORDO")
    public String getDsNoteEnteAccordo() {
        return this.dsNoteEnteAccordo;
    }

    public void setDsNoteEnteAccordo(String dsNoteEnteAccordo) {
        this.dsNoteEnteAccordo = dsNoteEnteAccordo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FIN_VAL")
    public Date getDtFinVal() {
        return this.dtFinVal;
    }

    public void setDtFinVal(Date dtFinVal) {
        this.dtFinVal = dtFinVal;
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
    @Column(name = "DT_REG_ACCORDO")
    public Date getDtRegAccordo() {
        return this.dtRegAccordo;
    }

    public void setDtRegAccordo(Date dtRegAccordo) {
        this.dtRegAccordo = dtRegAccordo;
    }

    @Column(name = "NM_ENTE")
    public String getNmEnte() {
        return nmEnte;
    }

    public void setNmEnte(String nmEnte) {
        this.nmEnte = nmEnte;
    }

    @Column(name = "NM_STRUT")
    public String getNmStrut() {
        return nmStrut;
    }

    public void setNmStrut(String nmStrut) {
        this.nmStrut = nmStrut;
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
    @JoinColumn(name = "ID_ENTE_ORGANO_VIGIL")
    public OrgEnteSiam getOrgEnteSiamByIdEnteOrganoVigil() {
        return this.orgEnteSiamByIdEnteOrganoVigil;
    }

    public void setOrgEnteSiamByIdEnteOrganoVigil(OrgEnteSiam orgEnteSiamByIdEnteOrganoVigil) {
        this.orgEnteSiamByIdEnteOrganoVigil = orgEnteSiamByIdEnteOrganoVigil;
    }

    // bi-directional many-to-one association to OrgVigilEnteProdut
    @OneToMany(mappedBy = "orgAccordoVigil")
    public List<OrgVigilEnteProdut> getOrgVigilEnteProduts() {
        return this.orgVigilEnteProduts;
    }

    public void setOrgVigilEnteProduts(List<OrgVigilEnteProdut> orgVigilEnteProduts) {
        this.orgVigilEnteProduts = orgVigilEnteProduts;
    }

    public OrgVigilEnteProdut addOrgVigilEnteProdut(OrgVigilEnteProdut orgVigilEnteProdut) {
        getOrgVigilEnteProduts().add(orgVigilEnteProdut);
        orgVigilEnteProdut.setOrgAccordoVigil(this);
        return orgVigilEnteProdut;
    }

    public OrgVigilEnteProdut removeOrgVigilEnteProdut(OrgVigilEnteProdut orgVigilEnteProdut) {
        getOrgVigilEnteProduts().remove(orgVigilEnteProdut);
        orgVigilEnteProdut.setOrgAccordoVigil(null);
        return orgVigilEnteProdut;
    }
}
