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
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the ORG_PAGAM_FATTURA_ENTE database table.
 */
@Entity
@Table(name = "ORG_PAGAM_FATTURA_ENTE")
@NamedQuery(name = "OrgPagamFatturaEnte.findAll", query = "SELECT o FROM OrgPagamFatturaEnte o")
public class OrgPagamFatturaEnte implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idPagamFatturaEnte;

    private String cdProvvPagam;

    private String cdRevPagam;

    private Date dtPagam;

    private BigDecimal imPagam;

    private String ntPagam;

    private BigDecimal pgPagam;

    private OrgFatturaEnte orgFatturaEnte;

    public OrgPagamFatturaEnte() {
    }

    @Id
    @Column(name = "ID_PAGAM_FATTURA_ENTE")
    @GenericGenerator(name = "SORG_PAGAM_FATTURA_ENTE_ID_PAGAM_FATTURA_ENTE_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SORG_PAGAM_FATTURA_ENTE"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SORG_PAGAM_FATTURA_ENTE_ID_PAGAM_FATTURA_ENTE_GENERATOR")
    public Long getIdPagamFatturaEnte() {
        return this.idPagamFatturaEnte;
    }

    public void setIdPagamFatturaEnte(Long idPagamFatturaEnte) {
        this.idPagamFatturaEnte = idPagamFatturaEnte;
    }

    @Column(name = "CD_PROVV_PAGAM")
    public String getCdProvvPagam() {
        return this.cdProvvPagam;
    }

    public void setCdProvvPagam(String cdProvvPagam) {
        this.cdProvvPagam = cdProvvPagam;
    }

    @Column(name = "CD_REV_PAGAM")
    public String getCdRevPagam() {
        return this.cdRevPagam;
    }

    public void setCdRevPagam(String cdRevPagam) {
        this.cdRevPagam = cdRevPagam;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_PAGAM")
    public Date getDtPagam() {
        return this.dtPagam;
    }

    public void setDtPagam(Date dtPagam) {
        this.dtPagam = dtPagam;
    }

    @Column(name = "IM_PAGAM")
    public BigDecimal getImPagam() {
        return this.imPagam;
    }

    public void setImPagam(BigDecimal imPagam) {
        this.imPagam = imPagam;
    }

    @Column(name = "NT_PAGAM")
    public String getNtPagam() {
        return this.ntPagam;
    }

    public void setNtPagam(String ntPagam) {
        this.ntPagam = ntPagam;
    }

    @Column(name = "PG_PAGAM")
    public BigDecimal getPgPagam() {
        return this.pgPagam;
    }

    public void setPgPagam(BigDecimal pgPagam) {
        this.pgPagam = pgPagam;
    }

    // bi-directional many-to-one association to OrgFatturaEnte
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FATTURA_ENTE")
    public OrgFatturaEnte getOrgFatturaEnte() {
        return this.orgFatturaEnte;
    }

    public void setOrgFatturaEnte(OrgFatturaEnte orgFatturaEnte) {
        this.orgFatturaEnte = orgFatturaEnte;
    }
}
