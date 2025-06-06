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

import it.eng.saceriam.entity.IamParamApplic;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Iam_Param_Applic
 *
 */
public class IamParamApplicRowBean extends BaseRow
	implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter",
     * comments = "This class was generated by OraTool", date = "Friday, 14 December 2018 16:10" )
     */

    public static IamParamApplicTableDescriptor TABLE_DESCRIPTOR = new IamParamApplicTableDescriptor();

    public IamParamApplicRowBean() {
	super();
    }

    public TableDescriptor getTableDescriptor() {
	return TABLE_DESCRIPTOR;
    }

    // getter e setter
    public BigDecimal getIdParamApplic() {
	return getBigDecimal("id_param_applic");
    }

    public void setIdParamApplic(BigDecimal idParamApplic) {
	setObject("id_param_applic", idParamApplic);
    }

    public String getCdVersioneAppIni() {
	return getString("cd_versione_app_ini");
    }

    public void setCdVersioneAppIni(String cdVersioneAppIni) {
	setObject("cd_versione_app_ini", cdVersioneAppIni);
    }

    public String getCdVersioneAppFine() {
	return getString("cd_versione_app_fine");
    }

    public void setCdVersioneAppFine(String cdVersioneAppFine) {
	setObject("cd_versione_app_fine", cdVersioneAppFine);
    }

    public String getNmParamApplic() {
	return getString("nm_param_applic");
    }

    public void setNmParamApplic(String nmParamApplic) {
	setObject("nm_param_applic", nmParamApplic);
    }

    public String getDsParamApplic() {
	return getString("ds_param_applic");
    }

    public void setDsParamApplic(String dsParamApplic) {
	setObject("ds_param_applic", dsParamApplic);
    }

    public String getTiParamApplic() {
	return getString("ti_param_applic");
    }

    public void setTiParamApplic(String tiParamApplic) {
	setObject("ti_param_applic", tiParamApplic);
    }

    public String getTiGestioneParam() {
	return getString("ti_gestione_param");
    }

    public void setTiGestioneParam(String tiGestioneParam) {
	setObject("ti_gestione_param", tiGestioneParam);
    }

    public String getFlAppartApplic() {
	return getString("fl_appart_applic");
    }

    public void setFlAppartApplic(String flAppartApplic) {
	setObject("fl_appart_applic", flAppartApplic);
    }

    public String getFlAppartAmbiente() {
	return getString("fl_appart_ambiente");
    }

    public void setFlAppartAmbiente(String flAppartAmbiente) {
	setObject("fl_appart_ambiente", flAppartAmbiente);
    }

    public String getFlApparteEnte() {
	return getString("fl_apparte_ente");
    }

    public void setFlApparteEnte(String flApparteEnte) {
	setObject("fl_apparte_ente", flApparteEnte);
    }

    public String getDsListaValoriAmmessi() {
	return getString("ds_lista_valori_ammessi");
    }

    public void setDsListaValoriAmmessi(String dsListaValoriAmmessi) {
	setObject("ds_lista_valori_ammessi", dsListaValoriAmmessi);
    }

    public String getTiValoreParamApplic() {
	return getString("ti_valore_param_applic");
    }

    public void setTiValoreParamApplic(String ti_valore_param_applic) {
	setObject("ti_valore_param_applic", ti_valore_param_applic);
    }

    @Override
    public void entityToRowBean(Object obj) {
	IamParamApplic entity = (IamParamApplic) obj;
	this.setIdParamApplic(new BigDecimal(entity.getIdParamApplic()));
	this.setCdVersioneAppIni(entity.getCdVersioneAppIni());
	this.setCdVersioneAppFine(entity.getCdVersioneAppFine());
	this.setNmParamApplic(entity.getNmParamApplic());
	this.setDsParamApplic(entity.getDsParamApplic());
	this.setTiParamApplic(entity.getTiParamApplic());
	this.setTiGestioneParam(entity.getTiGestioneParam());
	this.setFlAppartApplic(entity.getFlAppartApplic());
	this.setFlAppartAmbiente(entity.getFlAppartAmbiente());
	this.setFlApparteEnte(entity.getFlApparteEnte());
	this.setTiValoreParamApplic(entity.getTiValoreParamApplic());
	this.setDsListaValoriAmmessi(entity.getDsListaValoriAmmessi());
    }

    @Override
    public IamParamApplic rowBeanToEntity() {
	IamParamApplic entity = new IamParamApplic();
	if (this.getIdParamApplic() != null) {
	    entity.setIdParamApplic(this.getIdParamApplic().longValue());
	}
	entity.setCdVersioneAppIni(this.getCdVersioneAppIni());
	entity.setCdVersioneAppFine(this.getCdVersioneAppFine());
	entity.setNmParamApplic(this.getNmParamApplic());
	entity.setDsParamApplic(this.getDsParamApplic());
	entity.setTiParamApplic(this.getTiParamApplic());
	entity.setTiGestioneParam(this.getTiGestioneParam());
	entity.setFlAppartApplic(this.getFlAppartApplic());
	entity.setFlAppartAmbiente(this.getFlAppartAmbiente());
	entity.setFlApparteEnte(this.getFlApparteEnte());
	entity.setTiValoreParamApplic(this.getTiValoreParamApplic());
	entity.setDsListaValoriAmmessi(this.getDsListaValoriAmmessi());
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
