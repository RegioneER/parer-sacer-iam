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

import javax.persistence.CascadeType;
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
 * The persistent class for the ORG_TIPI_GESTIONE_ACCORDO database table.
 */
@Entity
@Table(name = "ORG_TIPI_GESTIONE_ACCORDO")
public class OrgTipiGestioneAccordo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idTipoGestioneAccordo;

    private String cdTipoGestioneAccordo;

    private List<OrgGestioneAccordo> orgGestioneAccordos = new ArrayList<>();

    public OrgTipiGestioneAccordo() {
    }

    @Id
    @Column(name = "ID_TIPO_GESTIONE_ACCORDO")
    @GenericGenerator(name = "SORG_TIPI_GESTIONE_ACCORDO_ID_TIPO_GESTIONE_ACCORDO_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SORG_TIPI_GESTIONE_ACCORDO"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SORG_TIPI_GESTIONE_ACCORDO_ID_TIPO_GESTIONE_ACCORDO_GENERATOR")
    public Long getIdTipoGestioneAccordo() {
        return this.idTipoGestioneAccordo;
    }

    public void setIdTipoGestioneAccordo(Long idTipoGestioneAccordo) {
        this.idTipoGestioneAccordo = idTipoGestioneAccordo;
    }

    @Column(name = "CD_TIPO_GESTIONE_ACCORDO")
    public String getCdTipoGestioneAccordo() {
        return this.cdTipoGestioneAccordo;
    }

    public void setCdTipoGestioneAccordo(String cdTipoGestioneAccordo) {
        this.cdTipoGestioneAccordo = cdTipoGestioneAccordo;
    }

    // bi-directional many-to-one association to OrgGestioneAccordo
    @OneToMany(mappedBy = "orgTipiGestioneAccordo", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    public List<OrgGestioneAccordo> getOrgGestioneAccordos() {
        return this.orgGestioneAccordos;
    }

    public void setOrgGestioneAccordos(List<OrgGestioneAccordo> orgGestioneAccordos) {
        this.orgGestioneAccordos = orgGestioneAccordos;
    }

    public OrgGestioneAccordo addOrgGestioneAccordo(OrgGestioneAccordo orgGestioneAccordo) {
        getOrgGestioneAccordos().add(orgGestioneAccordo);
        orgGestioneAccordo.setOrgTipiGestioneAccordo(this);
        return orgGestioneAccordo;
    }

    public OrgGestioneAccordo removeOrgGestioneAccordo(OrgGestioneAccordo orgGestioneAccordo) {
        getOrgGestioneAccordos().remove(orgGestioneAccordo);
        orgGestioneAccordo.setOrgTipiGestioneAccordo(null);
        return orgGestioneAccordo;
    }
}
