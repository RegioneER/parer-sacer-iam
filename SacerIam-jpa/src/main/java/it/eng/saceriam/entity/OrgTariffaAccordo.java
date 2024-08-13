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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the ORG_TARIFFA_ACCORDO database table.
 * 
 */
@Entity
@Table(name = "ORG_TARIFFA_ACCORDO")
public class OrgTariffaAccordo implements Serializable {
    private static final long serialVersionUID = 1L;
    private long idTariffaAccordo;
    private BigDecimal imTariffaAccordo;
    private List<OrgServizioErog> orgServizioErogs = new ArrayList<>();
    private OrgAccordoEnte orgAccordoEnte;
    private OrgTipoServizio orgTipoServizio;

    public OrgTariffaAccordo() {
    }

    @Id
    @SequenceGenerator(name = "ORG_TARIFFA_ACCORDO_IDTARIFFAACCORDO_GENERATOR", sequenceName = "SORG_TARIFFA_ACCORDO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORG_TARIFFA_ACCORDO_IDTARIFFAACCORDO_GENERATOR")
    @Column(name = "ID_TARIFFA_ACCORDO")
    public long getIdTariffaAccordo() {
        return this.idTariffaAccordo;
    }

    public void setIdTariffaAccordo(long idTariffaAccordo) {
        this.idTariffaAccordo = idTariffaAccordo;
    }

    @Column(name = "IM_TARIFFA_ACCORDO")
    public BigDecimal getImTariffaAccordo() {
        return this.imTariffaAccordo;
    }

    public void setImTariffaAccordo(BigDecimal imTariffaAccordo) {
        this.imTariffaAccordo = imTariffaAccordo;
    }

    // bi-directional many-to-one association to OrgServizioErog
    @OneToMany(mappedBy = "orgTariffaAccordo")
    public List<OrgServizioErog> getOrgServizioErogs() {
        return this.orgServizioErogs;
    }

    public void setOrgServizioErogs(List<OrgServizioErog> orgServizioErogs) {
        this.orgServizioErogs = orgServizioErogs;
    }

    public OrgServizioErog addOrgServizioErog(OrgServizioErog orgServizioErog) {
        getOrgServizioErogs().add(orgServizioErog);
        orgServizioErog.setOrgTariffaAccordo(this);

        return orgServizioErog;
    }

    public OrgServizioErog removeOrgServizioErog(OrgServizioErog orgServizioErog) {
        getOrgServizioErogs().remove(orgServizioErog);
        orgServizioErog.setOrgTariffaAccordo(null);

        return orgServizioErog;
    }

    // bi-directional many-to-one association to OrgAccordoEnte
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ACCORDO_ENTE")
    public OrgAccordoEnte getOrgAccordoEnte() {
        return this.orgAccordoEnte;
    }

    public void setOrgAccordoEnte(OrgAccordoEnte orgAccordoEnte) {
        this.orgAccordoEnte = orgAccordoEnte;
    }

    // bi-directional many-to-one association to OrgTipoServizio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_SERVIZIO")
    public OrgTipoServizio getOrgTipoServizio() {
        return this.orgTipoServizio;
    }

    public void setOrgTipoServizio(OrgTipoServizio orgTipoServizio) {
        this.orgTipoServizio = orgTipoServizio;
    }

}
