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
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the ORG_CLUSTER_ACCORDO database table.
 *
 */
@Entity
@Table(name = "ORG_CLUSTER_ACCORDO")
public class OrgClusterAccordo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idClusterAccordo;
    private String cdCluster;
    private List<OrgAccordoEnte> orgAccordoEntes;
    private OrgFasciaStorageAccordo orgFasciaStorageAccordo;

    public OrgClusterAccordo() {
    }

    // @Id
    // @SequenceGenerator(name = "ORG_CLUSTER_ACCORDO_IDCLUSTERACCORDO_GENERATOR", sequenceName = "$STABLE")
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORG_CLUSTER_ACCORDO_IDCLUSTERACCORDO_GENERATOR")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CLUSTER_ACCORDO")
    public Long getIdClusterAccordo() {
        return this.idClusterAccordo;
    }

    public void setIdClusterAccordo(Long idClusterAccordo) {
        this.idClusterAccordo = idClusterAccordo;
    }

    @Column(name = "CD_CLUSTER")
    public String getCdCluster() {
        return this.cdCluster;
    }

    public void setCdCluster(String cdCluster) {
        this.cdCluster = cdCluster;
    }

    // bi-directional many-to-one association to OrgAccordoEnte
    @OneToMany(mappedBy = "orgClusterAccordo")
    public List<OrgAccordoEnte> getOrgAccordoEntes() {
        return this.orgAccordoEntes;
    }

    public void setOrgAccordoEntes(List<OrgAccordoEnte> orgAccordoEntes) {
        this.orgAccordoEntes = orgAccordoEntes;
    }

    public OrgAccordoEnte addOrgAccordoEnte(OrgAccordoEnte orgAccordoEnte) {
        getOrgAccordoEntes().add(orgAccordoEnte);
        orgAccordoEnte.setOrgClusterAccordo(this);

        return orgAccordoEnte;
    }

    public OrgAccordoEnte removeOrgAccordoEnte(OrgAccordoEnte orgAccordoEnte) {
        getOrgAccordoEntes().remove(orgAccordoEnte);
        orgAccordoEnte.setOrgClusterAccordo(null);

        return orgAccordoEnte;
    }

    // bi-directional many-to-one association to OrgFasciaStorageAccordo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FASCIA_STORAGE_ACCORDO")
    public OrgFasciaStorageAccordo getOrgFasciaStorageAccordo() {
        return this.orgFasciaStorageAccordo;
    }

    public void setOrgFasciaStorageAccordo(OrgFasciaStorageAccordo orgFasciaStorageAccordo) {
        this.orgFasciaStorageAccordo = orgFasciaStorageAccordo;
    }

}
