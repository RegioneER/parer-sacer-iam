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

import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.entity.AplCompSw;
import it.eng.saceriam.entity.LogAgente;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Apl_Comp_Sw
 *
 */
public class AplCompSwRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter",
     * comments = "This class was generated by OraTool", date = "Thursday, 9 June 2016 10:56" )
     */

    public static AplCompSwTableDescriptor TABLE_DESCRIPTOR = new AplCompSwTableDescriptor();

    public AplCompSwRowBean() {
	super();
    }

    public TableDescriptor getTableDescriptor() {
	return TABLE_DESCRIPTOR;
    }

    // getter e setter
    public BigDecimal getIdCompSw() {
	return getBigDecimal("id_comp_sw");
    }

    public void setIdCompSw(BigDecimal idCompSw) {
	setObject("id_comp_sw", idCompSw);
    }

    public BigDecimal getIdApplic() {
	return getBigDecimal("id_applic");
    }

    public void setIdApplic(BigDecimal idApplic) {
	setObject("id_applic", idApplic);
    }

    public String getNmCompSw() {
	return getString("nm_comp_sw");
    }

    public void setNmCompSw(String nmCompSw) {
	setObject("nm_comp_sw", nmCompSw);
    }

    public BigDecimal getIdAgente() {
	return getBigDecimal("id_agente");
    }

    public void setIdAgente(BigDecimal idAgente) {
	setObject("id_agente", idAgente);
    }

    @Override
    public void entityToRowBean(Object obj) {
	AplCompSw entity = (AplCompSw) obj;
	this.setIdCompSw(new BigDecimal(entity.getIdCompSw()));
	if (entity.getAplApplic() != null) {
	    this.setIdApplic(new BigDecimal(entity.getAplApplic().getIdApplic()));
	}
	this.setNmCompSw(entity.getNmCompSw());

	if (entity.getLogAgente() != null) {
	    this.setIdAgente(new BigDecimal(entity.getLogAgente().getIdAgente()));
	}
    }

    @Override
    public AplCompSw rowBeanToEntity() {
	AplCompSw entity = new AplCompSw();
	if (this.getIdCompSw() != null) {
	    entity.setIdCompSw(this.getIdCompSw().longValue());
	}
	if (this.getIdApplic() != null) {
	    if (entity.getAplApplic() == null) {
		entity.setAplApplic(new AplApplic());
	    }
	    entity.getAplApplic().setIdApplic(this.getIdApplic().longValue());
	}
	entity.setNmCompSw(this.getNmCompSw());
	if (this.getIdAgente() != null) {
	    if (entity.getLogAgente() == null) {
		entity.setLogAgente(new LogAgente());
	    }
	    entity.getLogAgente().setIdAgente(this.getIdAgente().longValue());
	}
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
