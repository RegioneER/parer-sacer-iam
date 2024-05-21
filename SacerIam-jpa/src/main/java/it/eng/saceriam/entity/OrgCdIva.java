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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the ORG_CD_IVA database table.
 */
@Entity
@Table(name = "ORG_CD_IVA")
@NamedQuery(name = "OrgCdIva.findAll", query = "SELECT o FROM OrgCdIva o")
public class OrgCdIva implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idCdIva;

    private String cdIva;

    private String dsIva;

    private List<OrgAccordoEnte> orgAccordoEntes = new ArrayList<>();

    private List<OrgServizioFattura> orgServizioFatturas = new ArrayList<>();

    public OrgCdIva() {
    }

    @Id
    @Column(name = "ID_CD_IVA")
    @GenericGenerator(name = "SORG_CD_IVA_ID_CD_IVA_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SORG_CD_IVA"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SORG_CD_IVA_ID_CD_IVA_GENERATOR")
    public Long getIdCdIva() {
        return this.idCdIva;
    }

    public void setIdCdIva(Long idCdIva) {
        this.idCdIva = idCdIva;
    }

    @Column(name = "CD_IVA")
    public String getCdIva() {
        return this.cdIva;
    }

    public void setCdIva(String cdIva) {
        this.cdIva = cdIva;
    }

    @Column(name = "DS_IVA")
    public String getDsIva() {
        return this.dsIva;
    }

    public void setDsIva(String dsIva) {
        this.dsIva = dsIva;
    }

    // bi-directional many-to-one association to OrgAccordoEnte
    @OneToMany(mappedBy = "orgCdIva")
    public List<OrgAccordoEnte> getOrgAccordoEntes() {
        return this.orgAccordoEntes;
    }

    public void setOrgAccordoEntes(List<OrgAccordoEnte> orgAccordoEntes) {
        this.orgAccordoEntes = orgAccordoEntes;
    }

    // bi-directional many-to-one association to OrgAccordoEnte
    @OneToMany(mappedBy = "orgCdIva")
    public List<OrgServizioFattura> getOrgServizioFatturas() {
        return orgServizioFatturas;
    }

    public void setOrgServizioFatturas(List<OrgServizioFattura> orgServizioFatturas) {
        this.orgServizioFatturas = orgServizioFatturas;
    }
}
