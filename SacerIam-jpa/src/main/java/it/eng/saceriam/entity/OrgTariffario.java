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
 * The persistent class for the ORG_TARIFFARIO database table.
 */
@Entity
@Table(name = "ORG_TARIFFARIO")
public class OrgTariffario implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idTariffario;

    private Date dtFineVal;

    private Date dtIniVal;

    private String nmTariffario;

    private List<OrgAccordoEnte> orgAccordoEntes = new ArrayList<>();

    private List<OrgTariffa> orgTariffas = new ArrayList<>();

    private OrgTipoAccordo orgTipoAccordo;

    public OrgTariffario() {
    }

    @Id
    @Column(name = "ID_TARIFFARIO")
    @GenericGenerator(name = "SORG_TARIFFARIO_ID_TARIFFARIO_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SORG_TARIFFARIO"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SORG_TARIFFARIO_ID_TARIFFARIO_GENERATOR")
    public Long getIdTariffario() {
        return this.idTariffario;
    }

    public void setIdTariffario(Long idTariffario) {
        this.idTariffario = idTariffario;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FINE_VAL")
    public Date getDtFineVal() {
        return this.dtFineVal;
    }

    public void setDtFineVal(Date dtFineVal) {
        this.dtFineVal = dtFineVal;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_INI_VAL")
    public Date getDtIniVal() {
        return this.dtIniVal;
    }

    public void setDtIniVal(Date dtIniVal) {
        this.dtIniVal = dtIniVal;
    }

    @Column(name = "NM_TARIFFARIO")
    public String getNmTariffario() {
        return this.nmTariffario;
    }

    public void setNmTariffario(String nmTariffario) {
        this.nmTariffario = nmTariffario;
    }

    // bi-directional many-to-one association to OrgAccordoEnte
    @OneToMany(mappedBy = "orgTariffario")
    public List<OrgAccordoEnte> getOrgAccordoEntes() {
        return this.orgAccordoEntes;
    }

    public void setOrgAccordoEntes(List<OrgAccordoEnte> orgAccordoEntes) {
        this.orgAccordoEntes = orgAccordoEntes;
    }

    public OrgAccordoEnte addOrgAccordoEnte(OrgAccordoEnte orgAccordoEnte) {
        getOrgAccordoEntes().add(orgAccordoEnte);
        orgAccordoEnte.setOrgTariffario(this);
        return orgAccordoEnte;
    }

    public OrgAccordoEnte removeOrgAccordoEnte(OrgAccordoEnte orgAccordoEnte) {
        getOrgAccordoEntes().remove(orgAccordoEnte);
        orgAccordoEnte.setOrgTariffario(null);
        return orgAccordoEnte;
    }

    // bi-directional many-to-one association to OrgTariffa
    @OneToMany(mappedBy = "orgTariffario")
    public List<OrgTariffa> getOrgTariffas() {
        return this.orgTariffas;
    }

    public void setOrgTariffas(List<OrgTariffa> orgTariffas) {
        this.orgTariffas = orgTariffas;
    }

    public OrgTariffa addOrgTariffa(OrgTariffa orgTariffa) {
        getOrgTariffas().add(orgTariffa);
        orgTariffa.setOrgTariffario(this);
        return orgTariffa;
    }

    public OrgTariffa removeOrgTariffa(OrgTariffa orgTariffa) {
        getOrgTariffas().remove(orgTariffa);
        orgTariffa.setOrgTariffario(null);
        return orgTariffa;
    }

    // bi-directional many-to-one association to OrgTipoAccordo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_ACCORDO")
    public OrgTipoAccordo getOrgTipoAccordo() {
        return this.orgTipoAccordo;
    }

    public void setOrgTipoAccordo(OrgTipoAccordo orgTipoAccordo) {
        this.orgTipoAccordo = orgTipoAccordo;
    }
}
