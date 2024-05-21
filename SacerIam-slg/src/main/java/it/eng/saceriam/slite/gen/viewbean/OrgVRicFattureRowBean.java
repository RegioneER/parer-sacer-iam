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

package it.eng.saceriam.slite.gen.viewbean;

import java.math.BigDecimal;
import java.sql.Timestamp;

import it.eng.saceriam.viewEntity.OrgVRicFatture;
import it.eng.saceriam.viewEntity.OrgVRicFattureId;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Org_V_Ric_Fatture
 *
 */
public class OrgVRicFattureRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Thursday, 25 January 2018 14:25" )
     */

    public static OrgVRicFattureTableDescriptor TABLE_DESCRIPTOR = new OrgVRicFattureTableDescriptor();

    public OrgVRicFattureRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    public BigDecimal getIdEnteConvenz() {
        return getBigDecimal("id_ente_convenz");
    }

    public void setIdEnteConvenz(BigDecimal idEnteConvenz) {
        setObject("id_ente_convenz", idEnteConvenz);
    }

    public String getNmEnteConvenz() {
        return getString("nm_ente_convenz");
    }

    public void setNmEnteConvenz(String nmEnteConvenz) {
        setObject("nm_ente_convenz", nmEnteConvenz);
    }

    public BigDecimal getIdAccordoEnte() {
        return getBigDecimal("id_accordo_ente");
    }

    public void setIdAccordoEnte(BigDecimal idAccordoEnte) {
        setObject("id_accordo_ente", idAccordoEnte);
    }

    public BigDecimal getIdTipoAccordo() {
        return getBigDecimal("id_tipo_accordo");
    }

    public void setIdTipoAccordo(BigDecimal idTipoAccordo) {
        setObject("id_tipo_accordo", idTipoAccordo);
    }

    public String getCdTipoAccordo() {
        return getString("cd_tipo_accordo");
    }

    public void setCdTipoAccordo(String cdTipoAccordo) {
        setObject("cd_tipo_accordo", cdTipoAccordo);
    }

    public BigDecimal getIdFatturaEnte() {
        return getBigDecimal("id_fattura_ente");
    }

    public void setIdFatturaEnte(BigDecimal idFatturaEnte) {
        setObject("id_fattura_ente", idFatturaEnte);
    }

    public BigDecimal getAaFattura() {
        return getBigDecimal("aa_fattura");
    }

    public void setAaFattura(BigDecimal aaFattura) {
        setObject("aa_fattura", aaFattura);
    }

    public BigDecimal getPgFattura() {
        return getBigDecimal("pg_fattura");
    }

    public void setPgFattura(BigDecimal pgFattura) {
        setObject("pg_fattura", pgFattura);
    }

    public String getCdFattura() {
        return getString("cd_fattura");
    }

    public void setCdFattura(String cdFattura) {
        setObject("cd_fattura", cdFattura);
    }

    public String getCdRegistroEmisFattura() {
        return getString("cd_registro_emis_fattura");
    }

    public void setCdRegistroEmisFattura(String cdRegistroEmisFattura) {
        setObject("cd_registro_emis_fattura", cdRegistroEmisFattura);
    }

    public BigDecimal getAaEmissFattura() {
        return getBigDecimal("aa_emiss_fattura");
    }

    public void setAaEmissFattura(BigDecimal aaEmissFattura) {
        setObject("aa_emiss_fattura", aaEmissFattura);
    }

    public String getCdEmisFattura() {
        return getString("cd_emis_fattura");
    }

    public void setCdEmisFattura(String cdEmisFattura) {
        setObject("cd_emis_fattura", cdEmisFattura);
    }

    public Timestamp getDtEmisFattura() {
        return getTimestamp("dt_emis_fattura");
    }

    public void setDtEmisFattura(Timestamp dtEmisFattura) {
        setObject("dt_emis_fattura", dtEmisFattura);
    }

    public BigDecimal getImTotFattura() {
        return getBigDecimal("im_tot_fattura");
    }

    public void setImTotFattura(BigDecimal imTotFattura) {
        setObject("im_tot_fattura", imTotFattura);
    }

    public BigDecimal getImTotPagato() {
        return getBigDecimal("im_tot_pagato");
    }

    public void setImTotPagato(BigDecimal imTotPagato) {
        setObject("im_tot_pagato", imTotPagato);
    }

    public BigDecimal getIdStatoFatturaEnte() {
        return getBigDecimal("id_stato_fattura_ente");
    }

    public void setIdStatoFatturaEnte(BigDecimal idStatoFatturaEnte) {
        setObject("id_stato_fattura_ente", idStatoFatturaEnte);
    }

    public String getTiStatoFatturaEnte() {
        return getString("ti_stato_fattura_ente");
    }

    public void setTiStatoFatturaEnte(String tiStatoFatturaEnte) {
        setObject("ti_stato_fattura_ente", tiStatoFatturaEnte);
    }

    public Timestamp getDtRegStatoFatturaEnte() {
        return getTimestamp("dt_reg_stato_fattura_ente");
    }

    public void setDtRegStatoFatturaEnte(Timestamp dtRegStatoFatturaEnte) {
        setObject("dt_reg_stato_fattura_ente", dtRegStatoFatturaEnte);
    }

    public BigDecimal getIdServizioFattura() {
        return getBigDecimal("id_servizio_fattura");
    }

    public void setIdServizioFattura(BigDecimal idServizioFattura) {
        setObject("id_servizio_fattura", idServizioFattura);
    }

    public BigDecimal getIdServizioErogato() {
        return getBigDecimal("id_servizio_erogato");
    }

    public void setIdServizioErogato(BigDecimal idServizioErogato) {
        setObject("id_servizio_erogato", idServizioErogato);
    }

    public BigDecimal getIdTipoServizio() {
        return getBigDecimal("id_tipo_servizio");
    }

    public void setIdTipoServizio(BigDecimal idTipoServizio) {
        setObject("id_tipo_servizio", idTipoServizio);
    }

    public String getCdTipoServizio() {
        return getString("cd_tipo_servizio");
    }

    public void setCdTipoServizio(String cdTipoServizio) {
        setObject("cd_tipo_servizio", cdTipoServizio);
    }

    @Override
    public void entityToRowBean(Object obj) {
        OrgVRicFatture entity = (OrgVRicFatture) obj;
        this.setIdEnteConvenz(entity.getIdEnteConvenz());
        this.setNmEnteConvenz(entity.getNmEnteConvenz());
        this.setIdAccordoEnte(entity.getIdAccordoEnte());
        this.setIdTipoAccordo(entity.getIdTipoAccordo());
        this.setCdTipoAccordo(entity.getCdTipoAccordo());
        this.setIdFatturaEnte(
                entity.getOrgVRicFattureId() == null ? null : entity.getOrgVRicFattureId().getIdFatturaEnte());
        this.setAaFattura(entity.getAaFattura());
        this.setPgFattura(entity.getPgFattura());
        this.setCdFattura(entity.getCdFattura());
        this.setCdRegistroEmisFattura(entity.getCdRegistroEmisFattura());
        this.setAaEmissFattura(entity.getAaEmissFattura());
        this.setCdEmisFattura(entity.getCdEmisFattura());
        if (entity.getDtEmisFattura() != null) {
            this.setDtEmisFattura(new Timestamp(entity.getDtEmisFattura().getTime()));
        }
        this.setImTotFattura(entity.getImTotFattura());
        this.setImTotPagato(entity.getImTotPagato());
        this.setIdStatoFatturaEnte(entity.getIdStatoFatturaEnte());
        this.setTiStatoFatturaEnte(entity.getTiStatoFatturaEnte());
        if (entity.getDtRegStatoFatturaEnte() != null) {
            this.setDtRegStatoFatturaEnte(new Timestamp(entity.getDtRegStatoFatturaEnte().getTime()));
        }
        this.setIdServizioFattura(
                entity.getOrgVRicFattureId() == null ? null : entity.getOrgVRicFattureId().getIdServizioFattura());
        this.setIdServizioErogato(entity.getIdServizioErogato());
        this.setIdTipoServizio(entity.getIdTipoServizio());
        this.setCdTipoServizio(entity.getCdTipoServizio());
    }

    @Override
    public OrgVRicFatture rowBeanToEntity() {
        OrgVRicFatture entity = new OrgVRicFatture();
        entity.setOrgVRicFattureId(new OrgVRicFattureId());
        entity.setIdEnteConvenz(this.getIdEnteConvenz());
        entity.setNmEnteConvenz(this.getNmEnteConvenz());
        entity.setIdAccordoEnte(this.getIdAccordoEnte());
        entity.setIdTipoAccordo(this.getIdTipoAccordo());
        entity.setCdTipoAccordo(this.getCdTipoAccordo());
        entity.getOrgVRicFattureId().setIdFatturaEnte(this.getIdFatturaEnte());
        entity.setAaFattura(this.getAaFattura());
        entity.setPgFattura(this.getPgFattura());
        entity.setCdFattura(this.getCdFattura());
        entity.setCdRegistroEmisFattura(this.getCdRegistroEmisFattura());
        entity.setAaEmissFattura(this.getAaEmissFattura());
        entity.setCdEmisFattura(this.getCdEmisFattura());
        entity.setDtEmisFattura(this.getDtEmisFattura());
        entity.setImTotFattura(this.getImTotFattura());
        entity.setImTotPagato(this.getImTotPagato());
        entity.setIdStatoFatturaEnte(this.getIdStatoFatturaEnte());
        entity.setTiStatoFatturaEnte(this.getTiStatoFatturaEnte());
        entity.setDtRegStatoFatturaEnte(this.getDtRegStatoFatturaEnte());
        entity.getOrgVRicFattureId().setIdServizioFattura(this.getIdServizioFattura());
        entity.setIdServizioErogato(this.getIdServizioErogato());
        entity.setIdTipoServizio(this.getIdTipoServizio());
        entity.setCdTipoServizio(this.getCdTipoServizio());
        return entity;
    }

    // gestione della paginazione
    public void setRownum(Integer rownum) {
        setObject("rownum", rownum);
    }

    public Integer getRownum() {
        return Integer.parseInt(getObject("rownum").toString());
    }

    public void setRnum(Integer rnum) {
        setObject("rnum", rnum);
    }

    public Integer getRnum() {
        return Integer.parseInt(getObject("rnum").toString());
    }

    public void setNumrecords(Integer numRecords) {
        setObject("numrecords", numRecords);
    }

    public Integer getNumrecords() {
        return Integer.parseInt(getObject("numrecords").toString());
    }

}
