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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the ORG_SCAGLIONE_TARIFFA database table.
 */
@Entity
@Table(name = "ORG_SCAGLIONE_TARIFFA")
@NamedQuery(name = "OrgScaglioneTariffa.findAll", query = "SELECT o FROM OrgScaglioneTariffa o")
public class OrgScaglioneTariffa implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idScaglioneTariffa;

    private BigDecimal imScaglione;

    private BigDecimal niFineScaglione;

    private BigDecimal niIniScaglione;

    private OrgTariffa orgTariffa;

    public OrgScaglioneTariffa() {
    }

    @Id
    @Column(name = "ID_SCAGLIONE_TARIFFA")
    @GenericGenerator(name = "SORG_SCAGLIONE_TARIFFA_ID_SCAGLIONE_TARIFFA_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SORG_SCAGLIONE_TARIFFA"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SORG_SCAGLIONE_TARIFFA_ID_SCAGLIONE_TARIFFA_GENERATOR")
    public Long getIdScaglioneTariffa() {
        return this.idScaglioneTariffa;
    }

    public void setIdScaglioneTariffa(Long idScaglioneTariffa) {
        this.idScaglioneTariffa = idScaglioneTariffa;
    }

    @Column(name = "IM_SCAGLIONE")
    public BigDecimal getImScaglione() {
        return this.imScaglione;
    }

    public void setImScaglione(BigDecimal imScaglione) {
        this.imScaglione = imScaglione;
    }

    @Column(name = "NI_FINE_SCAGLIONE")
    public BigDecimal getNiFineScaglione() {
        return this.niFineScaglione;
    }

    public void setNiFineScaglione(BigDecimal niFineScaglione) {
        this.niFineScaglione = niFineScaglione;
    }

    @Column(name = "NI_INI_SCAGLIONE")
    public BigDecimal getNiIniScaglione() {
        return this.niIniScaglione;
    }

    public void setNiIniScaglione(BigDecimal niIniScaglione) {
        this.niIniScaglione = niIniScaglione;
    }

    // bi-directional many-to-one association to OrgTariffa
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TARIFFA")
    public OrgTariffa getOrgTariffa() {
        return this.orgTariffa;
    }

    public void setOrgTariffa(OrgTariffa orgTariffa) {
        this.orgTariffa = orgTariffa;
    }
}
