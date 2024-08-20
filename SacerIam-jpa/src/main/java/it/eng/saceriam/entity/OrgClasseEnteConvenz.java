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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the ORG_CLASSE_ENTE_CONVENZ database table.
 */
@Entity
@Table(name = "ORG_CLASSE_ENTE_CONVENZ")
public class OrgClasseEnteConvenz implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idClasseEnteConvenz;

    private String cdClasseEnteConvenz;

    private String dsClasseEnteConvenz;

    private BigDecimal niMaxAbit;

    private BigDecimal niMinAbit;

    private List<OrgAccordoEnte> orgAccordoEntes = new ArrayList<>();

    private List<OrgTariffa> orgTariffas = new ArrayList<>();

    public OrgClasseEnteConvenz() {
    }

    @Id
    @Column(name = "ID_CLASSE_ENTE_CONVENZ")
    @GenericGenerator(name = "SORG_CLASSE_ENTE_CONVENZ_ID_CLASSE_ENTE_CONVENZ_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SORG_CLASSE_ENTE_CONVENZ"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SORG_CLASSE_ENTE_CONVENZ_ID_CLASSE_ENTE_CONVENZ_GENERATOR")
    public Long getIdClasseEnteConvenz() {
        return this.idClasseEnteConvenz;
    }

    public void setIdClasseEnteConvenz(Long idClasseEnteConvenz) {
        this.idClasseEnteConvenz = idClasseEnteConvenz;
    }

    @Column(name = "CD_CLASSE_ENTE_CONVENZ")
    public String getCdClasseEnteConvenz() {
        return this.cdClasseEnteConvenz;
    }

    public void setCdClasseEnteConvenz(String cdClasseEnteConvenz) {
        this.cdClasseEnteConvenz = cdClasseEnteConvenz;
    }

    @Column(name = "DS_CLASSE_ENTE_CONVENZ")
    public String getDsClasseEnteConvenz() {
        return this.dsClasseEnteConvenz;
    }

    public void setDsClasseEnteConvenz(String dsClasseEnteConvenz) {
        this.dsClasseEnteConvenz = dsClasseEnteConvenz;
    }

    @Column(name = "NI_MAX_ABIT")
    public BigDecimal getNiMaxAbit() {
        return this.niMaxAbit;
    }

    public void setNiMaxAbit(BigDecimal niMaxAbit) {
        this.niMaxAbit = niMaxAbit;
    }

    @Column(name = "NI_MIN_ABIT")
    public BigDecimal getNiMinAbit() {
        return this.niMinAbit;
    }

    public void setNiMinAbit(BigDecimal niMinAbit) {
        this.niMinAbit = niMinAbit;
    }

    // bi-directional many-to-one association to OrgAccordoEnte
    @OneToMany(mappedBy = "orgClasseEnteConvenz")
    public List<OrgAccordoEnte> getOrgAccordoEntes() {
        return this.orgAccordoEntes;
    }

    public void setOrgAccordoEntes(List<OrgAccordoEnte> orgAccordoEntes) {
        this.orgAccordoEntes = orgAccordoEntes;
    }

    public OrgAccordoEnte addOrgAccordoEnte(OrgAccordoEnte orgAccordoEnte) {
        getOrgAccordoEntes().add(orgAccordoEnte);
        orgAccordoEnte.setOrgClasseEnteConvenz(this);
        return orgAccordoEnte;
    }

    public OrgAccordoEnte removeOrgAccordoEnte(OrgAccordoEnte orgAccordoEnte) {
        getOrgAccordoEntes().remove(orgAccordoEnte);
        orgAccordoEnte.setOrgClasseEnteConvenz(null);
        return orgAccordoEnte;
    }

    // bi-directional many-to-one association to OrgTariffa
    @OneToMany(mappedBy = "orgClasseEnteConvenz")
    public List<OrgTariffa> getOrgTariffas() {
        return this.orgTariffas;
    }

    public void setOrgTariffas(List<OrgTariffa> orgTariffas) {
        this.orgTariffas = orgTariffas;
    }

    public OrgTariffa addOrgTariffa(OrgTariffa orgTariffa) {
        getOrgTariffas().add(orgTariffa);
        orgTariffa.setOrgClasseEnteConvenz(this);
        return orgTariffa;
    }

    public OrgTariffa removeOrgTariffa(OrgTariffa orgTariffa) {
        getOrgTariffas().remove(orgTariffa);
        orgTariffa.setOrgClasseEnteConvenz(null);
        return orgTariffa;
    }
}
