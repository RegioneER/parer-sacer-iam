/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna <p/> This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version. <p/> This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Affero General Public License for more details. <p/> You should
 * have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.slite.gen.viewbean;

import java.math.BigDecimal;
import java.sql.Timestamp;

import it.eng.saceriam.viewEntity.OrgVCreaFatturaByAnno;
import it.eng.saceriam.viewEntity.OrgVCreaFatturaByAnnoId;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Org_V_Crea_Fattura_By_Anno
 *
 */
public class OrgVCreaFatturaByAnnoRowBean extends BaseRow
	implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter",
     * comments = "This class was generated by OraTool", date = "Wednesday, 21 October 2020 18:46" )
     */

    public static OrgVCreaFatturaByAnnoTableDescriptor TABLE_DESCRIPTOR = new OrgVCreaFatturaByAnnoTableDescriptor();

    public OrgVCreaFatturaByAnnoRowBean() {
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

    public BigDecimal getAaFattura() {
	return getBigDecimal("aa_fattura");
    }

    public void setAaFattura(BigDecimal aaFattura) {
	setObject("aa_fattura", aaFattura);
    }

    public BigDecimal getAaServizioFattura() {
	return getBigDecimal("aa_servizio_fattura");
    }

    public void setAaServizioFattura(BigDecimal aaServizioFattura) {
	setObject("aa_servizio_fattura", aaServizioFattura);
    }

    public BigDecimal getImTotFattura() {
	return getBigDecimal("im_tot_fattura");
    }

    public void setImTotFattura(BigDecimal imTotFattura) {
	setObject("im_tot_fattura", imTotFattura);
    }

    public BigDecimal getImTotDaPagare() {
	return getBigDecimal("im_tot_da_pagare");
    }

    public void setImTotDaPagare(BigDecimal imTotDaPagare) {
	setObject("im_tot_da_pagare", imTotDaPagare);
    }

    public BigDecimal getImTotIva() {
	return getBigDecimal("im_tot_iva");
    }

    public void setImTotIva(BigDecimal imTotIva) {
	setObject("im_tot_iva", imTotIva);
    }

    public BigDecimal getIdAccordoEnte() {
	return getBigDecimal("id_accordo_ente");
    }

    public void setIdAccordoEnte(BigDecimal id_accordo_ente) {
	setObject("id_accordo_ente", id_accordo_ente);
    }

    public Timestamp getDtDecAccordo() {
	return getTimestamp("dt_dec_accordo");
    }

    public void setDtDecAccordo(Timestamp dt_dec_accordo) {
	setObject("dt_dec_accordo", dt_dec_accordo);
    }

    // public BigDecimal getPgFattura() {
    // return getBigDecimal("pg_fattura");
    // }
    //
    // public void setPgFattura(BigDecimal pg_fattura) {
    // setObject("pg_fattura", pg_fattura);
    // }
    //
    // public String getCdFattura() {
    // return getString("cd_fattura");
    // }
    //
    // public void setCdFattura(String cd_fattura) {
    // setObject("cd_fattura", cd_fattura);
    // }

    @Override
    public void entityToRowBean(Object obj) {
	OrgVCreaFatturaByAnno entity = (OrgVCreaFatturaByAnno) obj;
	this.setIdEnteConvenz(entity.getOrgVCreaFatturaByAnnoId() == null ? null
		: entity.getOrgVCreaFatturaByAnnoId().getIdEnteConvenz());
	this.setAaFattura(entity.getOrgVCreaFatturaByAnnoId() == null ? null
		: entity.getOrgVCreaFatturaByAnnoId().getAaFattura());
	this.setAaServizioFattura(entity.getOrgVCreaFatturaByAnnoId() == null ? null
		: entity.getOrgVCreaFatturaByAnnoId().getAaServizioFattura());
	this.setImTotFattura(entity.getImTotFattura());
	this.setImTotDaPagare(entity.getImTotDaPagare());
	this.setImTotIva(entity.getImTotIva());
	this.setIdAccordoEnte(entity.getOrgVCreaFatturaByAnnoId().getIdAccordoEnte());
	if (entity.getDtDecAccordo() != null) {
	    this.setDtDecAccordo(new Timestamp(entity.getDtDecAccordo().getTime()));
	}
	// this.setPgFattura(entity.getPgFattura());
	// this.setCdFattura(entity.getCdFattura());
    }

    @Override
    public OrgVCreaFatturaByAnno rowBeanToEntity() {
	OrgVCreaFatturaByAnno entity = new OrgVCreaFatturaByAnno();
	entity.setOrgVCreaFatturaByAnnoId(new OrgVCreaFatturaByAnnoId());
	entity.getOrgVCreaFatturaByAnnoId().setIdEnteConvenz(this.getIdEnteConvenz());
	entity.getOrgVCreaFatturaByAnnoId().setAaFattura(this.getAaFattura());
	entity.getOrgVCreaFatturaByAnnoId().setAaServizioFattura(this.getAaServizioFattura());
	entity.getOrgVCreaFatturaByAnnoId().setIdAccordoEnte(this.getIdAccordoEnte());
	entity.setImTotFattura(this.getImTotFattura());
	entity.setImTotDaPagare(this.getImTotDaPagare());
	entity.setImTotIva(this.getImTotIva());
	entity.setDtDecAccordo(this.getDtDecAccordo());
	// entity.setPgFattura(this.getPgFattura());
	// entity.setCdFattura(this.getCdFattura());
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
