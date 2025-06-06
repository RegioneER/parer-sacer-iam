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

import it.eng.saceriam.viewEntity.OrgVCalcTotFatt;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Org_V_Calc_Tot_Fatt
 *
 */
public class OrgVCalcTotFattRowBean extends BaseRow
	implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter",
     * comments = "This class was generated by OraTool", date = "Wednesday, 20 June 2018 16:19" )
     */

    public static OrgVCalcTotFattTableDescriptor TABLE_DESCRIPTOR = new OrgVCalcTotFattTableDescriptor();

    public OrgVCalcTotFattRowBean() {
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

    @Override
    public void entityToRowBean(Object obj) {
	OrgVCalcTotFatt entity = (OrgVCalcTotFatt) obj;
	this.setIdFatturaEnte(entity.getIdFatturaEnte());
	this.setImTotFattura(entity.getImTotFattura());
	this.setImTotDaPagare(entity.getImTotDaPagare());
	this.setImTotIva(entity.getImTotIva());
    }

    @Override
    public OrgVCalcTotFatt rowBeanToEntity() {
	OrgVCalcTotFatt entity = new OrgVCalcTotFatt();
	entity.setIdFatturaEnte(this.getIdFatturaEnte());
	entity.setImTotFattura(this.getImTotFattura());
	entity.setImTotDaPagare(this.getImTotDaPagare());
	entity.setImTotIva(this.getImTotIva());
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
