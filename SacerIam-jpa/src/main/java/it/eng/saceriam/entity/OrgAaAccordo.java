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
 * The persistent class for the ORG_AA_ACCORDO database table.
 *
 */
@Entity
@Table(name = "ORG_AA_ACCORDO")
public class OrgAaAccordo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idAaAccordo;
    private BigDecimal aaAnnoAccordo;
    private String cdCapitoloAaAccordo;
    private String cdCigAaAccordo;
    private String cdCupAaAccordo;
    private String cdOdaAaAccordo;
    private String dsAttoAaAccordo;
    private String dsImpegnoAaAccordo;
    private String dsNotaAaAccordo;
    private BigDecimal imCanoneAaAccordo;
    private BigDecimal mmAaAccordo;
    private OrgAccordoEnte orgAccordoEnte;
    private List<OrgTariffaAaAccordo> orgTariffaAaAccordos = new ArrayList<>();

    public OrgAaAccordo() {
    }

    @Id
    @SequenceGenerator(name = "ORG_AA_ACCORDO_IDAAACCORDO_GENERATOR", sequenceName = "SORG_AA_ACCORDO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORG_AA_ACCORDO_IDAAACCORDO_GENERATOR")
    @Column(name = "ID_AA_ACCORDO")
    public Long getIdAaAccordo() {
        return this.idAaAccordo;
    }

    public void setIdAaAccordo(Long idAaAccordo) {
        this.idAaAccordo = idAaAccordo;
    }

    @Column(name = "AA_ANNO_ACCORDO")
    public BigDecimal getAaAnnoAccordo() {
        return this.aaAnnoAccordo;
    }

    public void setAaAnnoAccordo(BigDecimal aaAnnoAccordo) {
        this.aaAnnoAccordo = aaAnnoAccordo;
    }

    @Column(name = "CD_CAPITOLO_AA_ACCORDO")
    public String getCdCapitoloAaAccordo() {
        return this.cdCapitoloAaAccordo;
    }

    public void setCdCapitoloAaAccordo(String cdCapitoloAaAccordo) {
        this.cdCapitoloAaAccordo = cdCapitoloAaAccordo;
    }

    @Column(name = "CD_CIG_AA_ACCORDO")
    public String getCdCigAaAccordo() {
        return this.cdCigAaAccordo;
    }

    public void setCdCigAaAccordo(String cdCigAaAccordo) {
        this.cdCigAaAccordo = cdCigAaAccordo;
    }

    @Column(name = "CD_CUP_AA_ACCORDO")
    public String getCdCupAaAccordo() {
        return this.cdCupAaAccordo;
    }

    public void setCdCupAaAccordo(String cdCupAaAccordo) {
        this.cdCupAaAccordo = cdCupAaAccordo;
    }

    @Column(name = "CD_ODA_AA_ACCORDO")
    public String getCdOdaAaAccordo() {
        return this.cdOdaAaAccordo;
    }

    public void setCdOdaAaAccordo(String cdOdaAaAccordo) {
        this.cdOdaAaAccordo = cdOdaAaAccordo;
    }

    @Column(name = "DS_ATTO_AA_ACCORDO")
    public String getDsAttoAaAccordo() {
        return this.dsAttoAaAccordo;
    }

    public void setDsAttoAaAccordo(String dsAttoAaAccordo) {
        this.dsAttoAaAccordo = dsAttoAaAccordo;
    }

    @Column(name = "DS_IMPEGNO_AA_ACCORDO")
    public String getDsImpegnoAaAccordo() {
        return this.dsImpegnoAaAccordo;
    }

    public void setDsImpegnoAaAccordo(String dsImpegnoAaAccordo) {
        this.dsImpegnoAaAccordo = dsImpegnoAaAccordo;
    }

    @Column(name = "DS_NOTA_AA_ACCORDO")
    public String getDsNotaAaAccordo() {
        return this.dsNotaAaAccordo;
    }

    public void setDsNotaAaAccordo(String dsNotaAaAccordo) {
        this.dsNotaAaAccordo = dsNotaAaAccordo;
    }

    @Column(name = "IM_CANONE_AA_ACCORDO")
    public BigDecimal getImCanoneAaAccordo() {
        return this.imCanoneAaAccordo;
    }

    public void setImCanoneAaAccordo(BigDecimal imCanoneAaAccordo) {
        this.imCanoneAaAccordo = imCanoneAaAccordo;
    }

    @Column(name = "MM_AA_ACCORDO")
    public BigDecimal getMmAaAccordo() {
        return this.mmAaAccordo;
    }

    public void setMmAaAccordo(BigDecimal mmAaAccordo) {
        this.mmAaAccordo = mmAaAccordo;
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

    // bi-directional many-to-one association to OrgTariffaAaAccordo
    @OneToMany(mappedBy = "orgAaAccordo", cascade = CascadeType.REMOVE)
    public List<OrgTariffaAaAccordo> getOrgTariffaAaAccordos() {
        return this.orgTariffaAaAccordos;
    }

    public void setOrgTariffaAaAccordos(List<OrgTariffaAaAccordo> orgTariffaAaAccordos) {
        this.orgTariffaAaAccordos = orgTariffaAaAccordos;
    }

    public OrgTariffaAaAccordo addOrgTariffaAaAccordo(OrgTariffaAaAccordo orgTariffaAaAccordo) {
        getOrgTariffaAaAccordos().add(orgTariffaAaAccordo);
        orgTariffaAaAccordo.setOrgAaAccordo(this);

        return orgTariffaAaAccordo;
    }

    public OrgTariffaAaAccordo removeOrgTariffaAaAccordo(OrgTariffaAaAccordo orgTariffaAaAccordo) {
        getOrgTariffaAaAccordos().remove(orgTariffaAaAccordo);
        orgTariffaAaAccordo.setOrgAaAccordo(null);

        return orgTariffaAaAccordo;
    }

}
