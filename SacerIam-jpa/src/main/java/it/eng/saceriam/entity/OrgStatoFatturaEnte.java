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
import java.util.Date;

import javax.persistence.CascadeType;
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
 * The persistent class for the ORG_STATO_FATTURA_ENTE database table.
 */
@Entity
@Table(name = "ORG_STATO_FATTURA_ENTE")
@NamedQuery(name = "OrgStatoFatturaEnte.findAll", query = "SELECT o FROM OrgStatoFatturaEnte o")
public class OrgStatoFatturaEnte implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idStatoFatturaEnte;

    private Date dtRegStatoFatturaEnte;

    private String tiStatoFatturaEnte;

    private OrgFatturaEnte orgFatturaEnte;

    public OrgStatoFatturaEnte() {
    }

    @Id
    @Column(name = "ID_STATO_FATTURA_ENTE")
    @GenericGenerator(name = "SORG_STATO_FATTURA_ENTE_ID_STATO_FATTURA_ENTE_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SORG_STATO_FATTURA_ENTE"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SORG_STATO_FATTURA_ENTE_ID_STATO_FATTURA_ENTE_GENERATOR")
    public Long getIdStatoFatturaEnte() {
        return this.idStatoFatturaEnte;
    }

    public void setIdStatoFatturaEnte(Long idStatoFatturaEnte) {
        this.idStatoFatturaEnte = idStatoFatturaEnte;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_REG_STATO_FATTURA_ENTE")
    public Date getDtRegStatoFatturaEnte() {
        return this.dtRegStatoFatturaEnte;
    }

    public void setDtRegStatoFatturaEnte(Date dtRegStatoFatturaEnte) {
        this.dtRegStatoFatturaEnte = dtRegStatoFatturaEnte;
    }

    @Column(name = "TI_STATO_FATTURA_ENTE")
    public String getTiStatoFatturaEnte() {
        return this.tiStatoFatturaEnte;
    }

    public void setTiStatoFatturaEnte(String tiStatoFatturaEnte) {
        this.tiStatoFatturaEnte = tiStatoFatturaEnte;
    }

    // bi-directional many-to-one association to OrgFatturaEnte
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "ID_FATTURA_ENTE", nullable = false)
    public OrgFatturaEnte getOrgFatturaEnte() {
        return this.orgFatturaEnte;
    }

    public void setOrgFatturaEnte(OrgFatturaEnte orgFatturaEnte) {
        this.orgFatturaEnte = orgFatturaEnte;
    }
}
