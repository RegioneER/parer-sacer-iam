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
import java.math.BigDecimal;
import java.util.List;

/**
 * The persistent class for the ORG_FASCIA_STORAGE_ACCORDO database table.
 *
 */
@Entity
@Table(name = "ORG_FASCIA_STORAGE_ACCORDO")
public class OrgFasciaStorageAccordo implements Serializable {
    private static final long serialVersionUID = 1L;
    private long idFasciaStorageAccordo;
    private BigDecimal niFasciaA;
    private BigDecimal niFasciaDa;
    private String tiFascia;
    private List<OrgAccordoEnte> orgAccordoEntes1;
    private List<OrgAccordoEnte> orgAccordoEntes2;
    private List<OrgClusterAccordo> orgClusterAccordos;

    public OrgFasciaStorageAccordo() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FASCIA_STORAGE_ACCORDO")
    public long getIdFasciaStorageAccordo() {
        return this.idFasciaStorageAccordo;
    }

    public void setIdFasciaStorageAccordo(long idFasciaStorageAccordo) {
        this.idFasciaStorageAccordo = idFasciaStorageAccordo;
    }

    @Column(name = "NI_FASCIA_A")
    public BigDecimal getNiFasciaA() {
        return this.niFasciaA;
    }

    public void setNiFasciaA(BigDecimal niFasciaA) {
        this.niFasciaA = niFasciaA;
    }

    @Column(name = "NI_FASCIA_DA")
    public BigDecimal getNiFasciaDa() {
        return this.niFasciaDa;
    }

    public void setNiFasciaDa(BigDecimal niFasciaDa) {
        this.niFasciaDa = niFasciaDa;
    }

    @Column(name = "TI_FASCIA")
    public String getTiFascia() {
        return this.tiFascia;
    }

    public void setTiFascia(String tiFascia) {
        this.tiFascia = tiFascia;
    }

    // bi-directional many-to-one association to OrgAccordoEnte
    @OneToMany(mappedBy = "orgFasciaStorageStandardAccordo")
    public List<OrgAccordoEnte> getOrgAccordoEntes1() {
        return this.orgAccordoEntes1;
    }

    public void setOrgAccordoEntes1(List<OrgAccordoEnte> orgAccordoEntes1) {
        this.orgAccordoEntes1 = orgAccordoEntes1;
    }

    // bi-directional many-to-one association to OrgAccordoEnte
    @OneToMany(mappedBy = "orgFasciaStorageManualeAccordo")
    public List<OrgAccordoEnte> getOrgAccordoEntes2() {
        return this.orgAccordoEntes2;
    }

    public void setOrgAccordoEntes2(List<OrgAccordoEnte> orgAccordoEntes2) {
        this.orgAccordoEntes2 = orgAccordoEntes2;
    }

    // bi-directional many-to-one association to OrgClusterAccordo
    @OneToMany(mappedBy = "orgFasciaStorageAccordo")
    public List<OrgClusterAccordo> getOrgClusterAccordos() {
        return this.orgClusterAccordos;
    }

    public void setOrgClusterAccordos(List<OrgClusterAccordo> orgClusterAccordos) {
        this.orgClusterAccordos = orgClusterAccordos;
    }

    public OrgClusterAccordo addOrgClusterAccordo(OrgClusterAccordo orgClusterAccordo) {
        getOrgClusterAccordos().add(orgClusterAccordo);
        orgClusterAccordo.setOrgFasciaStorageAccordo(this);

        return orgClusterAccordo;
    }

    public OrgClusterAccordo removeOrgClusterAccordo(OrgClusterAccordo orgClusterAccordo) {
        getOrgClusterAccordos().remove(orgClusterAccordo);
        orgClusterAccordo.setOrgFasciaStorageAccordo(null);

        return orgClusterAccordo;
    }

}
