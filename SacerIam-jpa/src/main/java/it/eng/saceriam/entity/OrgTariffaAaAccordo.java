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
import java.util.List;

import javax.persistence.CascadeType;
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
 * The persistent class for the ORG_TARIFFA_AA_ACCORDO database table.
 *
 */
@Entity
@Table(name = "ORG_TARIFFA_AA_ACCORDO")
public class OrgTariffaAaAccordo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idTariffaAaAccordo;
    private BigDecimal imTariffaAaAccordo;
    private List<OrgServizioErog> orgServizioErogs = new ArrayList<>();
    private OrgAaAccordo orgAaAccordo;
    private OrgTipoServizio orgTipoServizio;

    public OrgTariffaAaAccordo() {
    }

    @Id
    @SequenceGenerator(name = "ORG_TARIFFA_AA_ACCORDO_IDTARIFFAAAACCORDO_GENERATOR", sequenceName = "SORG_TARIFFA_AA_ACCORDO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORG_TARIFFA_AA_ACCORDO_IDTARIFFAAAACCORDO_GENERATOR")
    @Column(name = "ID_TARIFFA_AA_ACCORDO")
    public Long getIdTariffaAaAccordo() {
	return this.idTariffaAaAccordo;
    }

    public void setIdTariffaAaAccordo(Long idTariffaAaAccordo) {
	this.idTariffaAaAccordo = idTariffaAaAccordo;
    }

    @Column(name = "IM_TARIFFA_AA_ACCORDO")
    public BigDecimal getImTariffaAaAccordo() {
	return this.imTariffaAaAccordo;
    }

    public void setImTariffaAaAccordo(BigDecimal imTariffaAaAccordo) {
	this.imTariffaAaAccordo = imTariffaAaAccordo;
    }

    // bi-directional many-to-one association to OrgServizioErog
    @OneToMany(mappedBy = "orgTariffaAaAccordo", cascade = CascadeType.REMOVE)
    public List<OrgServizioErog> getOrgServizioErogs() {
	return this.orgServizioErogs;
    }

    public void setOrgServizioErogs(List<OrgServizioErog> orgServizioErogs) {
	this.orgServizioErogs = orgServizioErogs;
    }

    // bi-directional many-to-one association to OrgAaAccordo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AA_ACCORDO")
    public OrgAaAccordo getOrgAaAccordo() {
	return this.orgAaAccordo;
    }

    public void setOrgAaAccordo(OrgAaAccordo orgAaAccordo) {
	this.orgAaAccordo = orgAaAccordo;
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
