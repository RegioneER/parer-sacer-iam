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

import it.eng.saceriam.viewEntity.OrgVCreaServFattUnaPrec;
import it.eng.saceriam.viewEntity.OrgVCreaServFattUnaPrecId;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Org_V_Crea_Serv_Fatt_Una_Prec
 *
 */
public class OrgVCreaServFattUnaPrecRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Wednesday, 21 October 2020 18:51" )
     */

    public static OrgVCreaServFattUnaPrecTableDescriptor TABLE_DESCRIPTOR = new OrgVCreaServFattUnaPrecTableDescriptor();

    public OrgVCreaServFattUnaPrecRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    public BigDecimal getIdFatturaEnte() {
        return getBigDecimal("id_fattura_ente");
    }

    public void setIdFatturaEnte(BigDecimal idFatturaEnte) {
        setObject("id_fattura_ente", idFatturaEnte);
    }

    public BigDecimal getIdAccordoEnte() {
        return getBigDecimal("id_accordo_ente");
    }

    public void setIdAccordoEnte(BigDecimal id_accordo_ente) {
        setObject("id_accordo_ente", id_accordo_ente);
    }

    public BigDecimal getIdServizioErogato() {
        return getBigDecimal("id_servizio_erogato");
    }

    public void setIdServizioErogato(BigDecimal idServizioErogato) {
        setObject("id_servizio_erogato", idServizioErogato);
    }

    public BigDecimal getAaServizioFattura() {
        return getBigDecimal("aa_servizio_fattura");
    }

    public void setAaServizioFattura(BigDecimal aaServizioFattura) {
        setObject("aa_servizio_fattura", aaServizioFattura);
    }

    public BigDecimal getAaServizioFatturaPrec() {
        return getBigDecimal("aa_servizio_fattura_prec");
    }

    public void setAaServizioFatturaPrec(BigDecimal aaServizioFatturaPrec) {
        setObject("aa_servizio_fattura_prec", aaServizioFatturaPrec);
    }

    public String getNmServizioFattura() {
        return getString("nm_servizio_fattura");
    }

    public void setNmServizioFattura(String nmServizioFattura) {
        setObject("nm_servizio_fattura", nmServizioFattura);
    }

    public BigDecimal getImServizioFattura() {
        return getBigDecimal("im_servizio_fattura");
    }

    public void setImServizioFattura(BigDecimal imServizioFattura) {
        setObject("im_servizio_fattura", imServizioFattura);
    }

    public BigDecimal getQtScaglioneServizioFattura() {
        return getBigDecimal("qt_scaglione_servizio_fattura");
    }

    public void setQtScaglioneServizioFattura(BigDecimal qtScaglioneServizioFattura) {
        setObject("qt_scaglione_servizio_fattura", qtScaglioneServizioFattura);
    }

    public String getQtUnitServizioFattura() {
        return getString("qt_unit_servizio_fattura");
    }

    public void setQtUnitServizioFattura(String qtUnitServizioFattura) {
        setObject("qt_unit_servizio_fattura", qtUnitServizioFattura);
    }

    public Timestamp getDtErog() {
        return getTimestamp("dt_erog");
    }

    public void setDtErog(Timestamp dtErog) {
        setObject("dt_erog", dtErog);
    }

    public String getNtServizioFattura() {
        return getString("nt_servizio_fattura");
    }

    public void setNtServizioFattura(String ntServizioFattura) {
        setObject("nt_servizio_fattura", ntServizioFattura);
    }

    public BigDecimal getIdCdIva() {
        return getBigDecimal("id_cd_iva");
    }

    public void setIdCdIva(BigDecimal idCdIva) {
        setObject("id_cd_iva", idCdIva);
    }

    public BigDecimal getImIva() {
        return getBigDecimal("im_iva");
    }

    public void setImIva(BigDecimal imIva) {
        setObject("im_iva", imIva);
    }

    @Override
    public void entityToRowBean(Object obj) {
        OrgVCreaServFattUnaPrec entity = (OrgVCreaServFattUnaPrec) obj;
        this.setIdFatturaEnte(entity.getOrgVCreaServFattUnaPrecId() == null ? null
                : entity.getOrgVCreaServFattUnaPrecId().getIdFatturaEnte());
        this.setIdAccordoEnte(entity.getIdAccordoEnte());
        this.setIdServizioErogato(entity.getOrgVCreaServFattUnaPrecId() == null ? null
                : entity.getOrgVCreaServFattUnaPrecId().getIdServizioErogato());
        this.setAaServizioFattura(entity.getAaServizioFattura());
        this.setAaServizioFatturaPrec(entity.getOrgVCreaServFattUnaPrecId() == null ? null
                : entity.getOrgVCreaServFattUnaPrecId().getAaServizioFatturaPrec());
        this.setNmServizioFattura(entity.getNmServizioFattura());
        this.setImServizioFattura(entity.getImServizioFattura());
        this.setQtScaglioneServizioFattura(entity.getQtScaglioneServizioFattura());
        this.setQtUnitServizioFattura(entity.getQtUnitServizioFattura());
        if (entity.getDtErog() != null) {
            this.setDtErog(new Timestamp(entity.getDtErog().getTime()));
        }
        this.setNtServizioFattura(entity.getNtServizioFattura());
        this.setIdCdIva(entity.getIdCdIva());
        this.setImIva(entity.getImIva());
    }

    @Override
    public OrgVCreaServFattUnaPrec rowBeanToEntity() {
        OrgVCreaServFattUnaPrec entity = new OrgVCreaServFattUnaPrec();
        entity.setOrgVCreaServFattUnaPrecId(new OrgVCreaServFattUnaPrecId());
        entity.getOrgVCreaServFattUnaPrecId().setIdFatturaEnte(this.getIdFatturaEnte());
        entity.setIdAccordoEnte(this.getIdAccordoEnte());
        entity.getOrgVCreaServFattUnaPrecId().setIdServizioErogato(this.getIdServizioErogato());
        entity.setAaServizioFattura(this.getAaServizioFattura());
        entity.getOrgVCreaServFattUnaPrecId().setAaServizioFatturaPrec(this.getAaServizioFatturaPrec());
        entity.setNmServizioFattura(this.getNmServizioFattura());
        entity.setImServizioFattura(this.getImServizioFattura());
        entity.setQtScaglioneServizioFattura(this.getQtScaglioneServizioFattura());
        entity.setQtUnitServizioFattura(this.getQtUnitServizioFattura());
        entity.setDtErog(this.getDtErog());
        entity.setNtServizioFattura(this.getNtServizioFattura());
        entity.setIdCdIva(this.getIdCdIva());
        entity.setImIva(this.getImIva());
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
