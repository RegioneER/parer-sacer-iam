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

import it.eng.saceriam.viewEntity.UsrVAllorgchildByAmmin;
import it.eng.saceriam.viewEntity.UsrVAllorgchildByAmminId;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Usr_V_Allorgchild_By_Ammin
 *
 */
public class UsrVAllorgchildByAmminRowBean extends BaseRow
	implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter",
     * comments = "This class was generated by OraTool", date = "Monday, 25 March 2019 11:48" )
     */

    public static UsrVAllorgchildByAmminTableDescriptor TABLE_DESCRIPTOR = new UsrVAllorgchildByAmminTableDescriptor();

    public UsrVAllorgchildByAmminRowBean() {
	super();
    }

    public TableDescriptor getTableDescriptor() {
	return TABLE_DESCRIPTOR;
    }

    public BigDecimal getIdUserIamCor() {
	return getBigDecimal("id_user_iam_cor");
    }

    public void setIdUserIamCor(BigDecimal idUserIamCor) {
	setObject("id_user_iam_cor", idUserIamCor);
    }

    public BigDecimal getIdApplic() {
	return getBigDecimal("id_applic");
    }

    public void setIdApplic(BigDecimal idApplic) {
	setObject("id_applic", idApplic);
    }

    public String getNmApplic() {
	return getString("nm_applic");
    }

    public void setNmApplic(String nmApplic) {
	setObject("nm_applic", nmApplic);
    }

    public BigDecimal getIdOrganizIamAmbEnte() {
	return getBigDecimal("id_organiz_iam_amb_ente");
    }

    public void setIdOrganizIamAmbEnte(BigDecimal idOrganizIamAmbEnte) {
	setObject("id_organiz_iam_amb_ente", idOrganizIamAmbEnte);
    }

    public String getDsCausaleDich() {
	return getString("ds_causale_dich");
    }

    public void setDsCausaleDich(String dsCausaleDich) {
	setObject("ds_causale_dich", dsCausaleDich);
    }

    @Override
    public void entityToRowBean(Object obj) {
	UsrVAllorgchildByAmmin entity = (UsrVAllorgchildByAmmin) obj;
	this.setIdUserIamCor(entity.getUsrVAllorgchildByAmminId() == null ? null
		: entity.getUsrVAllorgchildByAmminId().getIdUserIamCor());
	this.setIdApplic(entity.getIdApplic());
	this.setNmApplic(entity.getNmApplic());
	this.setIdOrganizIamAmbEnte(entity.getUsrVAllorgchildByAmminId() == null ? null
		: entity.getUsrVAllorgchildByAmminId().getIdOrganizIamAmbEnte());
	this.setDsCausaleDich(entity.getDsCausaleDich());
    }

    @Override
    public UsrVAllorgchildByAmmin rowBeanToEntity() {
	UsrVAllorgchildByAmmin entity = new UsrVAllorgchildByAmmin();
	entity.setUsrVAllorgchildByAmminId(new UsrVAllorgchildByAmminId());
	entity.getUsrVAllorgchildByAmminId().setIdUserIamCor(this.getIdUserIamCor());
	entity.setIdApplic(this.getIdApplic());
	entity.setNmApplic(this.getNmApplic());
	entity.getUsrVAllorgchildByAmminId().setIdOrganizIamAmbEnte(this.getIdOrganizIamAmbEnte());
	entity.setDsCausaleDich(this.getDsCausaleDich());
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
