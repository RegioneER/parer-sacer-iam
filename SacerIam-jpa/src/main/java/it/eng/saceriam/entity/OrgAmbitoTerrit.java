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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the ORG_AMBITO_TERRIT database table.
 */
@Entity
@Table(name = "ORG_AMBITO_TERRIT")
public class OrgAmbitoTerrit implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idAmbitoTerrit;

    private String cdAmbitoTerrit;

    private String tiAmbitoTerrit;

    private OrgAmbitoTerrit orgAmbitoTerrit;

    private List<OrgAmbitoTerrit> orgAmbitoTerrits = new ArrayList<>();

    public OrgAmbitoTerrit() {
    }

    @Id
    @Column(name = "ID_AMBITO_TERRIT")
    @GenericGenerator(name = "SORG_AMBITO_TERRIT_ID_AMBITO_TERRIT_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SORG_AMBITO_TERRIT"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SORG_AMBITO_TERRIT_ID_AMBITO_TERRIT_GENERATOR")
    public Long getIdAmbitoTerrit() {
        return this.idAmbitoTerrit;
    }

    public void setIdAmbitoTerrit(Long idAmbitoTerrit) {
        this.idAmbitoTerrit = idAmbitoTerrit;
    }

    @Column(name = "CD_AMBITO_TERRIT")
    public String getCdAmbitoTerrit() {
        return this.cdAmbitoTerrit;
    }

    public void setCdAmbitoTerrit(String cdAmbitoTerrit) {
        this.cdAmbitoTerrit = cdAmbitoTerrit;
    }

    @Column(name = "TI_AMBITO_TERRIT")
    public String getTiAmbitoTerrit() {
        return this.tiAmbitoTerrit;
    }

    public void setTiAmbitoTerrit(String tiAmbitoTerrit) {
        this.tiAmbitoTerrit = tiAmbitoTerrit;
    }

    // bi-directional many-to-one association to OrgAmbitoTerrit
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AMBITO_TERRIT_PADRE")
    public OrgAmbitoTerrit getOrgAmbitoTerrit() {
        return this.orgAmbitoTerrit;
    }

    public void setOrgAmbitoTerrit(OrgAmbitoTerrit orgAmbitoTerrit) {
        this.orgAmbitoTerrit = orgAmbitoTerrit;
    }

    // bi-directional many-to-one association to OrgAmbitoTerrit
    @OneToMany(mappedBy = "orgAmbitoTerrit")
    public List<OrgAmbitoTerrit> getOrgAmbitoTerrits() {
        return this.orgAmbitoTerrits;
    }

    public void setOrgAmbitoTerrits(List<OrgAmbitoTerrit> orgAmbitoTerrits) {
        this.orgAmbitoTerrits = orgAmbitoTerrits;
    }
}
