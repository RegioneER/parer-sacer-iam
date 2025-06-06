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

package it.eng.saceriam.slite.gen.tablebean;

import java.math.BigDecimal;
import java.sql.Timestamp;

import it.eng.saceriam.entity.OrgFatturaEnte;
import it.eng.saceriam.entity.OrgPagamFatturaEnte;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Org_Pagam_Fattura_Ente
 *
 */
public class OrgPagamFatturaEnteRowBean extends BaseRow
	implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter",
     * comments = "This class was generated by OraTool", date = "Thursday, 29 June 2017 14:45" )
     */

    public static OrgPagamFatturaEnteTableDescriptor TABLE_DESCRIPTOR = new OrgPagamFatturaEnteTableDescriptor();

    public OrgPagamFatturaEnteRowBean() {
	super();
    }

    public TableDescriptor getTableDescriptor() {
	return TABLE_DESCRIPTOR;
    }

    // getter e setter
    public BigDecimal getIdPagamFatturaEnte() {
	return getBigDecimal("id_pagam_fattura_ente");
    }

    public void setIdPagamFatturaEnte(BigDecimal idPagamFatturaEnte) {
	setObject("id_pagam_fattura_ente", idPagamFatturaEnte);
    }

    public BigDecimal getIdFatturaEnte() {
	return getBigDecimal("id_fattura_ente");
    }

    public void setIdFatturaEnte(BigDecimal idFatturaEnte) {
	setObject("id_fattura_ente", idFatturaEnte);
    }

    public BigDecimal getPgPagam() {
	return getBigDecimal("pg_pagam");
    }

    public void setPgPagam(BigDecimal pgPagam) {
	setObject("pg_pagam", pgPagam);
    }

    public String getCdProvvPagam() {
	return getString("cd_provv_pagam");
    }

    public void setCdProvvPagam(String cdProvvPagam) {
	setObject("cd_provv_pagam", cdProvvPagam);
    }

    public String getCdRevPagam() {
	return getString("cd_rev_pagam");
    }

    public void setCdRevPagam(String cdRevPagam) {
	setObject("cd_rev_pagam", cdRevPagam);
    }

    public Timestamp getDtPagam() {
	return getTimestamp("dt_pagam");
    }

    public void setDtPagam(Timestamp dtPagam) {
	setObject("dt_pagam", dtPagam);
    }

    public BigDecimal getImPagam() {
	return getBigDecimal("im_pagam");
    }

    public void setImPagam(BigDecimal imPagam) {
	setObject("im_pagam", imPagam);
    }

    public String getNtPagam() {
	return getString("nt_pagam");
    }

    public void setNtPagam(String ntPagam) {
	setObject("nt_pagam", ntPagam);
    }

    @Override
    public void entityToRowBean(Object obj) {
	OrgPagamFatturaEnte entity = (OrgPagamFatturaEnte) obj;
	this.setIdPagamFatturaEnte(new BigDecimal(entity.getIdPagamFatturaEnte()));
	if (entity.getOrgFatturaEnte() != null) {
	    this.setIdFatturaEnte(new BigDecimal(entity.getOrgFatturaEnte().getIdFatturaEnte()));

	}
	this.setPgPagam(entity.getPgPagam());
	this.setCdProvvPagam(entity.getCdProvvPagam());
	this.setCdRevPagam(entity.getCdRevPagam());
	if (entity.getDtPagam() != null) {
	    this.setDtPagam(new Timestamp(entity.getDtPagam().getTime()));
	}
	this.setImPagam(entity.getImPagam());
	this.setNtPagam(entity.getNtPagam());
    }

    @Override
    public OrgPagamFatturaEnte rowBeanToEntity() {
	OrgPagamFatturaEnte entity = new OrgPagamFatturaEnte();
	if (this.getIdPagamFatturaEnte() != null) {
	    entity.setIdPagamFatturaEnte(this.getIdPagamFatturaEnte().longValue());
	}
	if (this.getIdFatturaEnte() != null) {
	    if (entity.getOrgFatturaEnte() == null) {
		entity.setOrgFatturaEnte(new OrgFatturaEnte());
	    }
	    entity.getOrgFatturaEnte().setIdFatturaEnte(this.getIdFatturaEnte().longValue());
	}
	entity.setPgPagam(this.getPgPagam());
	entity.setCdProvvPagam(this.getCdProvvPagam());
	entity.setCdRevPagam(this.getCdRevPagam());
	entity.setDtPagam(this.getDtPagam());
	entity.setImPagam(this.getImPagam());
	entity.setNtPagam(this.getNtPagam());
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
