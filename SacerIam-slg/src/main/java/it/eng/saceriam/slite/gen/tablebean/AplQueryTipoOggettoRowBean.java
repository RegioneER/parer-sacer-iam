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

import it.eng.saceriam.entity.AplQueryTipoOggetto;
import it.eng.saceriam.entity.AplTipoOggetto;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Apl_Query_Tipo_Oggetto
 *
 */
public class AplQueryTipoOggettoRowBean extends BaseRow
	implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter",
     * comments = "This class was generated by OraTool", date = "Thursday, 9 June 2016 11:14" )
     */

    public static AplQueryTipoOggettoTableDescriptor TABLE_DESCRIPTOR = new AplQueryTipoOggettoTableDescriptor();

    public AplQueryTipoOggettoRowBean() {
	super();
    }

    public TableDescriptor getTableDescriptor() {
	return TABLE_DESCRIPTOR;
    }

    // getter e setter
    public BigDecimal getIdQueryTipoOggetto() {
	return getBigDecimal("id_query_tipo_oggetto");
    }

    public void setIdQueryTipoOggetto(BigDecimal idQueryTipoOggetto) {
	setObject("id_query_tipo_oggetto", idQueryTipoOggetto);
    }

    public BigDecimal getIdTipoOggetto() {
	return getBigDecimal("id_tipo_oggetto");
    }

    public void setIdTipoOggetto(BigDecimal idTipoOggetto) {
	setObject("id_tipo_oggetto", idTipoOggetto);
    }

    public String getNmQueryTipoOggetto() {
	return getString("nm_query_tipo_oggetto");
    }

    public void setNmQueryTipoOggetto(String nmQueryTipoOggetto) {
	setObject("nm_query_tipo_oggetto", nmQueryTipoOggetto);
    }

    public String getTipoUsoQuery() {
	return getString("tipo_uso_query");
    }

    public void setTipoUsoQuery(String tipoUsoQuery) {
	setObject("tipo_uso_query", tipoUsoQuery);
    }

    public String getBlQueryTipoOggetto() {
	return getString("bl_query_tipo_oggetto");
    }

    public void setBlQueryTipoOggetto(String blQueryTipoOggetto) {
	setObject("bl_query_tipo_oggetto", blQueryTipoOggetto);
    }

    public BigDecimal getIdTipoOggettoAccesso() {
	return getBigDecimal("id_tipo_oggetto_accesso");
    }

    public void setIdTipoOggettoAccesso(BigDecimal idTipoOggettoAccesso) {
	setObject("id_tipo_oggetto_accesso", idTipoOggettoAccesso);
    }

    public BigDecimal getIdTipoOggettoSel() {
	return getBigDecimal("id_tipo_oggetto_sel");
    }

    public void setIdTipoOggettoSel(BigDecimal idTipoOggettoSel) {
	setObject("id_tipo_oggetto_sel", idTipoOggettoSel);
    }

    @Override
    public void entityToRowBean(Object obj) {
	AplQueryTipoOggetto entity = (AplQueryTipoOggetto) obj;
	this.setIdQueryTipoOggetto(new BigDecimal(entity.getIdQueryTipoOggetto()));
	if (entity.getAplTipoOggetto2() != null) {
	    this.setIdTipoOggetto(new BigDecimal(entity.getAplTipoOggetto2().getIdTipoOggetto()));
	}
	this.setNmQueryTipoOggetto(entity.getNmQueryTipoOggetto());
	this.setTipoUsoQuery(entity.getTipoUsoQuery());
	this.setBlQueryTipoOggetto(entity.getBlQueryTipoOggetto());
	if (entity.getAplTipoOggetto3() != null) {
	    this.setIdTipoOggettoAccesso(
		    new BigDecimal(entity.getAplTipoOggetto3().getIdTipoOggetto()));
	}
	if (entity.getAplTipoOggetto1() != null) {
	    this.setIdTipoOggettoSel(
		    new BigDecimal(entity.getAplTipoOggetto1().getIdTipoOggetto()));
	}
    }

    @Override
    public AplQueryTipoOggetto rowBeanToEntity() {
	AplQueryTipoOggetto entity = new AplQueryTipoOggetto();
	if (this.getIdQueryTipoOggetto() != null) {
	    entity.setIdQueryTipoOggetto(this.getIdQueryTipoOggetto().longValue());
	}
	if (this.getIdTipoOggetto() != null) {
	    if (entity.getAplTipoOggetto2() == null) {
		entity.setAplTipoOggetto2(new AplTipoOggetto());
	    }
	    entity.getAplTipoOggetto2().setIdTipoOggetto(this.getIdTipoOggetto().longValue());
	}
	entity.setNmQueryTipoOggetto(this.getNmQueryTipoOggetto());
	entity.setTipoUsoQuery(this.getTipoUsoQuery());
	entity.setBlQueryTipoOggetto(this.getBlQueryTipoOggetto());
	if (this.getIdTipoOggettoAccesso() != null) {
	    if (entity.getAplTipoOggetto3() == null) {
		entity.setAplTipoOggetto3(new AplTipoOggetto());
	    }
	    entity.getAplTipoOggetto3()
		    .setIdTipoOggetto(this.getIdTipoOggettoAccesso().longValue());
	}
	if (this.getIdTipoOggettoSel() != null) {
	    if (entity.getAplTipoOggetto1() == null) {
		entity.setAplTipoOggetto1(new AplTipoOggetto());
	    }
	    entity.getAplTipoOggetto1().setIdTipoOggetto(this.getIdTipoOggettoSel().longValue());
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
