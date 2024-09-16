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

package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the ORG_V_CREA_FATTURA_BY_ANNO database table.
 */
@Entity
@Table(name = "ORG_V_CREA_FATTURA_BY_ANNO")
public class OrgVCreaFatturaByAnno implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date dtDecAccordo;

    private BigDecimal imTotDaPagare;

    private BigDecimal imTotFattura;

    private BigDecimal imTotIva;

    public OrgVCreaFatturaByAnno() {
        // document why this constructor is empty
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_DEC_ACCORDO")
    public Date getDtDecAccordo() {
        return this.dtDecAccordo;
    }

    public void setDtDecAccordo(Date dtDecAccordo) {
        this.dtDecAccordo = dtDecAccordo;
    }

    @Column(name = "IM_TOT_DA_PAGARE")
    public BigDecimal getImTotDaPagare() {
        return this.imTotDaPagare;
    }

    public void setImTotDaPagare(BigDecimal imTotDaPagare) {
        this.imTotDaPagare = imTotDaPagare;
    }

    @Column(name = "IM_TOT_FATTURA")
    public BigDecimal getImTotFattura() {
        return this.imTotFattura;
    }

    public void setImTotFattura(BigDecimal imTotFattura) {
        this.imTotFattura = imTotFattura;
    }

    @Column(name = "IM_TOT_IVA")
    public BigDecimal getImTotIva() {
        return this.imTotIva;
    }

    public void setImTotIva(BigDecimal imTotIva) {
        this.imTotIva = imTotIva;
    }

    private OrgVCreaFatturaByAnnoId orgVCreaFatturaByAnnoId;

    @EmbeddedId()
    public OrgVCreaFatturaByAnnoId getOrgVCreaFatturaByAnnoId() {
        return orgVCreaFatturaByAnnoId;
    }

    public void setOrgVCreaFatturaByAnnoId(OrgVCreaFatturaByAnnoId orgVCreaFatturaByAnnoId) {
        this.orgVCreaFatturaByAnnoId = orgVCreaFatturaByAnnoId;
    }
}
