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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the ORG_SOLLECITO_FATTURA_ENTE database table.
 */
@Entity
@Table(name = "ORG_SOLLECITO_FATTURA_ENTE")
public class OrgSollecitoFatturaEnte implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idSollecitoFatturaEnte;

    private BigDecimal aaVarSollecito;

    private String cdKeyVarSollecito;

    private String cdRegistroSollecito;

    private String dlSollecito;

    private Date dtSollecito;

    private OrgFatturaEnte orgFatturaEnte;

    public OrgSollecitoFatturaEnte() {
    }

    @Id
    @Column(name = "ID_SOLLECITO_FATTURA_ENTE")
    @GenericGenerator(name = "SORG_SOLLECITO_FATTURA_ENTE_ID_SOLLECITO_FATTURA_ENTE_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SORG_SOLLECITO_FATTURA_ENTE"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SORG_SOLLECITO_FATTURA_ENTE_ID_SOLLECITO_FATTURA_ENTE_GENERATOR")
    public Long getIdSollecitoFatturaEnte() {
        return this.idSollecitoFatturaEnte;
    }

    public void setIdSollecitoFatturaEnte(Long idSollecitoFatturaEnte) {
        this.idSollecitoFatturaEnte = idSollecitoFatturaEnte;
    }

    @Column(name = "AA_VAR_SOLLECITO")
    public BigDecimal getAaVarSollecito() {
        return this.aaVarSollecito;
    }

    public void setAaVarSollecito(BigDecimal aaVarSollecito) {
        this.aaVarSollecito = aaVarSollecito;
    }

    @Column(name = "CD_KEY_VAR_SOLLECITO")
    public String getCdKeyVarSollecito() {
        return this.cdKeyVarSollecito;
    }

    public void setCdKeyVarSollecito(String cdKeyVarSollecito) {
        this.cdKeyVarSollecito = cdKeyVarSollecito;
    }

    @Column(name = "CD_REGISTRO_SOLLECITO")
    public String getCdRegistroSollecito() {
        return this.cdRegistroSollecito;
    }

    public void setCdRegistroSollecito(String cdRegistroSollecito) {
        this.cdRegistroSollecito = cdRegistroSollecito;
    }

    @Column(name = "DL_SOLLECITO")
    public String getDlSollecito() {
        return this.dlSollecito;
    }

    public void setDlSollecito(String dlSollecito) {
        this.dlSollecito = dlSollecito;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_SOLLECITO")
    public Date getDtSollecito() {
        return this.dtSollecito;
    }

    public void setDtSollecito(Date dtSollecito) {
        this.dtSollecito = dtSollecito;
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
