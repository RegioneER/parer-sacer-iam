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

import it.eng.saceriam.viewEntity.OrgVServTiServDaErog;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Org_V_Serv_Ti_Serv_Da_Erog
 *
 */
public class OrgVServTiServDaErogRowBean extends BaseRow
	implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter",
     * comments = "This class was generated by OraTool", date = "Thursday, 9 June 2016 11:47" )
     */

    public static OrgVServTiServDaErogTableDescriptor TABLE_DESCRIPTOR = new OrgVServTiServDaErogTableDescriptor();

    public OrgVServTiServDaErogRowBean() {
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

    /*
     * public Timestamp getDtRegAccordo() { return getTimestamp("dt_reg_accordo"); }
     *
     * public void setDtRegAccordo(Timestamp dtRegAccordo) { setObject("dt_reg_accordo",
     * dtRegAccordo); }
     */
    public BigDecimal getIdServizioErogato() {
	return getBigDecimal("id_servizio_erogato");
    }

    public void setIdServizioErogato(BigDecimal idServizioErogato) {
	setObject("id_servizio_erogato", idServizioErogato);
    }

    public String getNmServizioErogato() {
	return getString("nm_servizio_erogato");
    }

    public void setNmServizioErogato(String nmServizioErogato) {
	setObject("nm_servizio_erogato", nmServizioErogato);
    }

    public Timestamp getDtErog() {
	return getTimestamp("dt_erog");
    }

    public void setDtErog(Timestamp dtErog) {
	setObject("dt_erog", dtErog);
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

    public String getListStrut() {
	return getString("list_strut");
    }

    public void setListStrut(String listStrut) {
	setObject("list_strut", listStrut);
    }

    @Override
    public void entityToRowBean(Object obj) {
	OrgVServTiServDaErog entity = (OrgVServTiServDaErog) obj;
	this.setIdEnteConvenz(entity.getIdEnteConvenz());
	this.setNmEnteConvenz(entity.getNmEnteConvenz());
	this.setIdAccordoEnte(entity.getIdAccordoEnte());
	/*
	 * if (entity.getDtRegAccordo() != null) { this.setDtRegAccordo(new
	 * Timestamp(entity.getDtRegAccordo().getTime())); }
	 */
	this.setIdServizioErogato(entity.getIdServizioErogato());
	this.setNmServizioErogato(entity.getNmServizioErogato());
	if (entity.getDtErog() != null) {
	    this.setDtErog(new Timestamp(entity.getDtErog().getTime()));
	}
	this.setIdTipoServizio(entity.getIdTipoServizio());
	this.setCdTipoServizio(entity.getCdTipoServizio());
	this.setListStrut(entity.getListStrut());
    }

    @Override
    public OrgVServTiServDaErog rowBeanToEntity() {
	OrgVServTiServDaErog entity = new OrgVServTiServDaErog();
	entity.setIdEnteConvenz(this.getIdEnteConvenz());
	entity.setNmEnteConvenz(this.getNmEnteConvenz());
	entity.setIdAccordoEnte(this.getIdAccordoEnte());
	// entity.setDtRegAccordo(this.getDtRegAccordo());
	entity.setIdServizioErogato(this.getIdServizioErogato());
	entity.setNmServizioErogato(this.getNmServizioErogato());
	entity.setDtErog(this.getDtErog());
	entity.setIdTipoServizio(this.getIdTipoServizio());
	entity.setCdTipoServizio(this.getCdTipoServizio());
	entity.setListStrut(this.getListStrut());
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
