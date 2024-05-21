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
 * The persistent class for the ORG_V_RIC_FATTURE database table.
 */
@Entity
@Table(name = "ORG_V_RIC_FATTURE")
@NamedQuery(name = "OrgVRicFatture.findAll", query = "SELECT a FROM OrgVRicFatture a")
public class OrgVRicFatture implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal aaEmissFattura;

    private BigDecimal aaFattura;

    private String cdEmisFattura;

    private String cdFattura;

    private String cdRegistroEmisFattura;

    private String cdTipoAccordo;

    private String cdTipoServizio;

    private Date dtEmisFattura;

    private Date dtRegStatoFatturaEnte;

    private BigDecimal idAccordoEnte;

    private BigDecimal idEnteConvenz;

    private BigDecimal idServizioErogato;

    private BigDecimal idStatoFatturaEnte;

    private BigDecimal idTipoAccordo;

    private BigDecimal idTipoServizio;

    private BigDecimal imTotFattura;

    private BigDecimal imTotPagato;

    private String nmEnteConvenz;

    private BigDecimal pgFattura;

    private String tiStatoFatturaEnte;

    public OrgVRicFatture() {
    }

    public OrgVRicFatture(String cdFattura, BigDecimal aaEmissFattura, BigDecimal aaFattura, String cdEmisFattura,
            String cdRegistroEmisFattura, String cdTipoAccordo, Date dtEmisFattura, Date dtRegStatoFatturaEnte,
            BigDecimal idAccordoEnte, BigDecimal idEnteConvenz, BigDecimal idFatturaEnte, BigDecimal idStatoFatturaEnte,
            BigDecimal idTipoAccordo, BigDecimal imTotFattura, String nmEnteConvenz, BigDecimal pgFattura,
            String tiStatoFatturaEnte, BigDecimal imTotPagato) {
        this.cdFattura = cdFattura;
        this.aaEmissFattura = aaEmissFattura;
        this.aaFattura = aaFattura;
        this.cdEmisFattura = cdEmisFattura;
        this.cdRegistroEmisFattura = cdRegistroEmisFattura;
        this.cdTipoAccordo = cdTipoAccordo;
        this.dtEmisFattura = dtEmisFattura;
        this.dtRegStatoFatturaEnte = dtRegStatoFatturaEnte;
        this.idAccordoEnte = idAccordoEnte;
        this.idEnteConvenz = idEnteConvenz;
        this.orgVRicFattureId = new OrgVRicFattureId();
        this.orgVRicFattureId.setIdFatturaEnte(idFatturaEnte);
        this.idStatoFatturaEnte = idStatoFatturaEnte;
        this.idTipoAccordo = idTipoAccordo;
        this.imTotFattura = imTotFattura;
        this.nmEnteConvenz = nmEnteConvenz;
        this.pgFattura = pgFattura;
        this.tiStatoFatturaEnte = tiStatoFatturaEnte;
        this.imTotPagato = imTotPagato;
    }

    public OrgVRicFatture(BigDecimal idEnteConvenz, String nmEnteConvenz, String cdFattura, String cdEmisFattura,
            Date dtEmisFattura, String tiStatoFatturaEnte, Date dtRegStatoFatturaEnte, BigDecimal idFatturaEnte,
            BigDecimal imTotFattura, BigDecimal imTotPagato) {
        this.cdFattura = cdFattura;
        this.cdEmisFattura = cdEmisFattura;
        this.dtEmisFattura = dtEmisFattura;
        this.dtRegStatoFatturaEnte = dtRegStatoFatturaEnte;
        this.idEnteConvenz = idEnteConvenz;
        this.orgVRicFattureId = new OrgVRicFattureId();
        this.orgVRicFattureId.setIdFatturaEnte(idFatturaEnte);
        this.imTotFattura = imTotFattura;
        this.tiStatoFatturaEnte = tiStatoFatturaEnte;
        this.imTotPagato = imTotPagato;
        this.nmEnteConvenz = nmEnteConvenz;
    }

    @Column(name = "AA_EMISS_FATTURA")
    public BigDecimal getAaEmissFattura() {
        return this.aaEmissFattura;
    }

    public void setAaEmissFattura(BigDecimal aaEmissFattura) {
        this.aaEmissFattura = aaEmissFattura;
    }

    @Column(name = "AA_FATTURA")
    public BigDecimal getAaFattura() {
        return this.aaFattura;
    }

    public void setAaFattura(BigDecimal aaFattura) {
        this.aaFattura = aaFattura;
    }

    @Column(name = "CD_EMIS_FATTURA")
    public String getCdEmisFattura() {
        return this.cdEmisFattura;
    }

    public void setCdEmisFattura(String cdEmisFattura) {
        this.cdEmisFattura = cdEmisFattura;
    }

    @Column(name = "CD_FATTURA")
    public String getCdFattura() {
        return this.cdFattura;
    }

    public void setCdFattura(String cdFattura) {
        this.cdFattura = cdFattura;
    }

    @Column(name = "CD_REGISTRO_EMIS_FATTURA")
    public String getCdRegistroEmisFattura() {
        return this.cdRegistroEmisFattura;
    }

    public void setCdRegistroEmisFattura(String cdRegistroEmisFattura) {
        this.cdRegistroEmisFattura = cdRegistroEmisFattura;
    }

    @Column(name = "CD_TIPO_ACCORDO")
    public String getCdTipoAccordo() {
        return this.cdTipoAccordo;
    }

    public void setCdTipoAccordo(String cdTipoAccordo) {
        this.cdTipoAccordo = cdTipoAccordo;
    }

    @Column(name = "CD_TIPO_SERVIZIO")
    public String getCdTipoServizio() {
        return this.cdTipoServizio;
    }

    public void setCdTipoServizio(String cdTipoServizio) {
        this.cdTipoServizio = cdTipoServizio;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_EMIS_FATTURA")
    public Date getDtEmisFattura() {
        return this.dtEmisFattura;
    }

    public void setDtEmisFattura(Date dtEmisFattura) {
        this.dtEmisFattura = dtEmisFattura;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_REG_STATO_FATTURA_ENTE")
    public Date getDtRegStatoFatturaEnte() {
        return this.dtRegStatoFatturaEnte;
    }

    public void setDtRegStatoFatturaEnte(Date dtRegStatoFatturaEnte) {
        this.dtRegStatoFatturaEnte = dtRegStatoFatturaEnte;
    }

    @Column(name = "ID_ACCORDO_ENTE")
    public BigDecimal getIdAccordoEnte() {
        return this.idAccordoEnte;
    }

    public void setIdAccordoEnte(BigDecimal idAccordoEnte) {
        this.idAccordoEnte = idAccordoEnte;
    }

    @Column(name = "ID_ENTE_CONVENZ")
    public BigDecimal getIdEnteConvenz() {
        return this.idEnteConvenz;
    }

    public void setIdEnteConvenz(BigDecimal idEnteConvenz) {
        this.idEnteConvenz = idEnteConvenz;
    }

    @Column(name = "ID_SERVIZIO_EROGATO")
    public BigDecimal getIdServizioErogato() {
        return this.idServizioErogato;
    }

    public void setIdServizioErogato(BigDecimal idServizioErogato) {
        this.idServizioErogato = idServizioErogato;
    }

    @Column(name = "ID_STATO_FATTURA_ENTE")
    public BigDecimal getIdStatoFatturaEnte() {
        return this.idStatoFatturaEnte;
    }

    public void setIdStatoFatturaEnte(BigDecimal idStatoFatturaEnte) {
        this.idStatoFatturaEnte = idStatoFatturaEnte;
    }

    @Column(name = "ID_TIPO_ACCORDO")
    public BigDecimal getIdTipoAccordo() {
        return this.idTipoAccordo;
    }

    public void setIdTipoAccordo(BigDecimal idTipoAccordo) {
        this.idTipoAccordo = idTipoAccordo;
    }

    @Column(name = "ID_TIPO_SERVIZIO")
    public BigDecimal getIdTipoServizio() {
        return this.idTipoServizio;
    }

    public void setIdTipoServizio(BigDecimal idTipoServizio) {
        this.idTipoServizio = idTipoServizio;
    }

    @Column(name = "IM_TOT_FATTURA")
    public BigDecimal getImTotFattura() {
        return this.imTotFattura;
    }

    public void setImTotFattura(BigDecimal imTotFattura) {
        this.imTotFattura = imTotFattura;
    }

    @Column(name = "IM_TOT_PAGATO")
    public BigDecimal getImTotPagato() {
        return this.imTotPagato;
    }

    public void setImTotPagato(BigDecimal imTotPagato) {
        this.imTotPagato = imTotPagato;
    }

    @Column(name = "NM_ENTE_CONVENZ")
    public String getNmEnteConvenz() {
        return this.nmEnteConvenz;
    }

    public void setNmEnteConvenz(String nmEnteConvenz) {
        this.nmEnteConvenz = nmEnteConvenz;
    }

    @Column(name = "PG_FATTURA")
    public BigDecimal getPgFattura() {
        return this.pgFattura;
    }

    public void setPgFattura(BigDecimal pgFattura) {
        this.pgFattura = pgFattura;
    }

    @Column(name = "TI_STATO_FATTURA_ENTE")
    public String getTiStatoFatturaEnte() {
        return this.tiStatoFatturaEnte;
    }

    public void setTiStatoFatturaEnte(String tiStatoFatturaEnte) {
        this.tiStatoFatturaEnte = tiStatoFatturaEnte;
    }

    private OrgVRicFattureId orgVRicFattureId;

    @EmbeddedId()
    public OrgVRicFattureId getOrgVRicFattureId() {
        return orgVRicFattureId;
    }

    public void setOrgVRicFattureId(OrgVRicFattureId orgVRicFattureId) {
        this.orgVRicFattureId = orgVRicFattureId;
    }
}
