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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the ORG_TIPO_SERVIZIO database table.
 */
@Entity
@Table(name = "ORG_TIPO_SERVIZIO")
public class OrgTipoServizio implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idTipoServizio;

    private String cdTipoServizio;

    private String dsTipoServizio;

    private String flTariffaAccordo;

    private String flTariffaAnnualita;

    private String ggFatturazione;

    private String tiClasseTipoServizio;

    private String tipoFatturazione;

    private List<OrgServizioErog> orgServizioErogs = new ArrayList<>();

    private List<OrgTariffa> orgTariffas = new ArrayList<>();

    private List<OrgTariffaAaAccordo> orgTariffaAaAccordos = new ArrayList<>();

    private List<OrgTariffaAccordo> orgTariffaAccordos = new ArrayList<>();

    public OrgTipoServizio() {
    }

    @Id
    @Column(name = "ID_TIPO_SERVIZIO")
    @GenericGenerator(name = "SORG_TIPO_SERVIZIO_ID_TIPO_SERVIZIO_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SORG_TIPO_SERVIZIO"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SORG_TIPO_SERVIZIO_ID_TIPO_SERVIZIO_GENERATOR")
    public Long getIdTipoServizio() {
        return this.idTipoServizio;
    }

    public void setIdTipoServizio(Long idTipoServizio) {
        this.idTipoServizio = idTipoServizio;
    }

    @Column(name = "CD_TIPO_SERVIZIO")
    public String getCdTipoServizio() {
        return this.cdTipoServizio;
    }

    public void setCdTipoServizio(String cdTipoServizio) {
        this.cdTipoServizio = cdTipoServizio;
    }

    @Column(name = "DS_TIPO_SERVIZIO")
    public String getDsTipoServizio() {
        return this.dsTipoServizio;
    }

    public void setDsTipoServizio(String dsTipoServizio) {
        this.dsTipoServizio = dsTipoServizio;
    }

    @Column(name = "FL_TARIFFA_ACCORDO", columnDefinition = "char(1)")
    public String getFlTariffaAccordo() {
        return flTariffaAccordo;
    }

    public void setFlTariffaAccordo(String flTariffaAccordo) {
        this.flTariffaAccordo = flTariffaAccordo;
    }

    @Column(name = "FL_TARIFFA_ANNUALITA", columnDefinition = "char(1)")
    public String getFlTariffaAnnualita() {
        return flTariffaAnnualita;
    }

    public void setFlTariffaAnnualita(String flTariffaAnnualita) {
        this.flTariffaAnnualita = flTariffaAnnualita;
    }

    @Column(name = "GG_FATTURAZIONE", columnDefinition = "char")
    public String getGgFatturazione() {
        return this.ggFatturazione;
    }

    public void setGgFatturazione(String ggFatturazione) {
        this.ggFatturazione = ggFatturazione;
    }

    @Column(name = "TI_CLASSE_TIPO_SERVIZIO")
    public String getTiClasseTipoServizio() {
        return this.tiClasseTipoServizio;
    }

    public void setTiClasseTipoServizio(String tiClasseTipoServizio) {
        this.tiClasseTipoServizio = tiClasseTipoServizio;
    }

    @Column(name = "TIPO_FATTURAZIONE")
    public String getTipoFatturazione() {
        return this.tipoFatturazione;
    }

    public void setTipoFatturazione(String tipoFatturazione) {
        this.tipoFatturazione = tipoFatturazione;
    }

    // bi-directional many-to-one association to OrgServizioErog
    @OneToMany(mappedBy = "orgTipoServizio")
    public List<OrgServizioErog> getOrgServizioErogs() {
        return this.orgServizioErogs;
    }

    public void setOrgServizioErogs(List<OrgServizioErog> orgServizioErogs) {
        this.orgServizioErogs = orgServizioErogs;
    }

    public OrgServizioErog addOrgServizioErog(OrgServizioErog orgServizioErog) {
        getOrgServizioErogs().add(orgServizioErog);
        orgServizioErog.setOrgTipoServizio(this);
        return orgServizioErog;
    }

    public OrgServizioErog removeOrgServizioErog(OrgServizioErog orgServizioErog) {
        getOrgServizioErogs().remove(orgServizioErog);
        orgServizioErog.setOrgTipoServizio(null);
        return orgServizioErog;
    }

    // bi-directional many-to-one association to OrgTariffa
    @OneToMany(mappedBy = "orgTipoServizio")
    public List<OrgTariffa> getOrgTariffas() {
        return this.orgTariffas;
    }

    public void setOrgTariffas(List<OrgTariffa> orgTariffas) {
        this.orgTariffas = orgTariffas;
    }

    public OrgTariffa addOrgTariffa(OrgTariffa orgTariffa) {
        getOrgTariffas().add(orgTariffa);
        orgTariffa.setOrgTipoServizio(this);
        return orgTariffa;
    }

    public OrgTariffa removeOrgTariffa(OrgTariffa orgTariffa) {
        getOrgTariffas().remove(orgTariffa);
        orgTariffa.setOrgTipoServizio(null);
        return orgTariffa;
    }

    // bi-directional many-to-one association to OrgTariffaAaAccordo
    @OneToMany(mappedBy = "orgTipoServizio")
    public List<OrgTariffaAaAccordo> getOrgTariffaAaAccordos() {
        return this.orgTariffaAaAccordos;
    }

    public void setOrgTariffaAaAccordos(List<OrgTariffaAaAccordo> orgTariffaAaAccordos) {
        this.orgTariffaAaAccordos = orgTariffaAaAccordos;
    }

    public OrgTariffaAaAccordo addOrgTariffaAaAccordo(OrgTariffaAaAccordo orgTariffaAaAccordo) {
        getOrgTariffaAaAccordos().add(orgTariffaAaAccordo);
        orgTariffaAaAccordo.setOrgTipoServizio(this);
        return orgTariffaAaAccordo;
    }

    public OrgTariffaAaAccordo removeOrgTariffaAaAccordo(OrgTariffaAaAccordo orgTariffaAaAccordo) {
        getOrgTariffaAaAccordos().remove(orgTariffaAaAccordo);
        orgTariffaAaAccordo.setOrgTipoServizio(null);
        return orgTariffaAaAccordo;
    }

    // bi-directional many-to-one association to OrgTariffaAccordo
    @OneToMany(mappedBy = "orgTipoServizio")
    public List<OrgTariffaAccordo> getOrgTariffaAccordos() {
        return this.orgTariffaAccordos;
    }

    public void setOrgTariffaAccordos(List<OrgTariffaAccordo> orgTariffaAccordos) {
        this.orgTariffaAccordos = orgTariffaAccordos;
    }

    public OrgTariffaAccordo addOrgTariffaAccordo(OrgTariffaAccordo orgTariffaAccordo) {
        getOrgTariffaAccordos().add(orgTariffaAccordo);
        orgTariffaAccordo.setOrgTipoServizio(this);
        return orgTariffaAccordo;
    }

    public OrgTariffaAccordo removeOrgTariffaAccordo(OrgTariffaAccordo orgTariffaAccordo) {
        getOrgTariffaAccordos().remove(orgTariffaAccordo);
        orgTariffaAccordo.setOrgTipoServizio(null);
        return orgTariffaAccordo;
    }
}
