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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the ORG_V_CREA_SERV_FATT_UNATANTUM database table.
 */
@Entity
@Table(name = "ORG_V_CREA_SERV_FATT_UNATANTUM")
@NamedQuery(name = "OrgVCreaServFattUnatantum.findAll", query = "SELECT o FROM OrgVCreaServFattUnatantum o")
public class OrgVCreaServFattUnatantum implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date dtErog;
    private BigDecimal idAccordoEnte;
    private BigDecimal idCdIva;

    private BigDecimal imIva;

    private BigDecimal imServizioFattura;

    private String nmServizioFattura;

    private String ntServizioFattura;

    private BigDecimal qtScaglioneServizioFattura;

    private String qtUnitServizioFattura;

    public OrgVCreaServFattUnatantum() {
        // document why this constructor is empty
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_EROG")
    public Date getDtErog() {
        return this.dtErog;
    }

    public void setDtErog(Date dtErog) {
        this.dtErog = dtErog;
    }

    @Column(name = "ID_ACCORDO_ENTE")
    public BigDecimal getIdAccordoEnte() {
        return this.idAccordoEnte;
    }

    public void setIdAccordoEnte(BigDecimal idAccordoEnte) {
        this.idAccordoEnte = idAccordoEnte;
    }

    @Column(name = "ID_CD_IVA")
    public BigDecimal getIdCdIva() {
        return this.idCdIva;
    }

    public void setIdCdIva(BigDecimal idCdIva) {
        this.idCdIva = idCdIva;
    }

    @Column(name = "IM_IVA")
    public BigDecimal getImIva() {
        return this.imIva;
    }

    public void setImIva(BigDecimal imIva) {
        this.imIva = imIva;
    }

    @Column(name = "IM_SERVIZIO_FATTURA")
    public BigDecimal getImServizioFattura() {
        return this.imServizioFattura;
    }

    public void setImServizioFattura(BigDecimal imServizioFattura) {
        this.imServizioFattura = imServizioFattura;
    }

    @Column(name = "NM_SERVIZIO_FATTURA")
    public String getNmServizioFattura() {
        return this.nmServizioFattura;
    }

    public void setNmServizioFattura(String nmServizioFattura) {
        this.nmServizioFattura = nmServizioFattura;
    }

    @Column(name = "NT_SERVIZIO_FATTURA")
    public String getNtServizioFattura() {
        return this.ntServizioFattura;
    }

    public void setNtServizioFattura(String ntServizioFattura) {
        this.ntServizioFattura = ntServizioFattura;
    }

    @Column(name = "QT_SCAGLIONE_SERVIZIO_FATTURA")
    public BigDecimal getQtScaglioneServizioFattura() {
        return this.qtScaglioneServizioFattura;
    }

    public void setQtScaglioneServizioFattura(BigDecimal qtScaglioneServizioFattura) {
        this.qtScaglioneServizioFattura = qtScaglioneServizioFattura;
    }

    @Column(name = "QT_UNIT_SERVIZIO_FATTURA")
    public String getQtUnitServizioFattura() {
        return this.qtUnitServizioFattura;
    }

    public void setQtUnitServizioFattura(String qtUnitServizioFattura) {
        this.qtUnitServizioFattura = qtUnitServizioFattura;
    }

    private OrgVCreaServFattUnatantumId orgVCreaServFattUnatantumId;

    @EmbeddedId()
    public OrgVCreaServFattUnatantumId getOrgVCreaServFattUnatantumId() {
        return orgVCreaServFattUnatantumId;
    }

    public void setOrgVCreaServFattUnatantumId(OrgVCreaServFattUnatantumId orgVCreaServFattUnatantumId) {
        this.orgVCreaServFattUnatantumId = orgVCreaServFattUnatantumId;
    }
}
