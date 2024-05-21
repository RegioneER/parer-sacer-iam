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

import it.eng.saceriam.viewEntity.UsrVLisEntiSiamAppEnte;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Usr_V_Lis_Enti_Siam_App_Ente
 *
 */
public class UsrVLisEntiSiamAppEnteRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Friday, 17 May 2019 16:06" )
     */

    public static UsrVLisEntiSiamAppEnteTableDescriptor TABLE_DESCRIPTOR = new UsrVLisEntiSiamAppEnteTableDescriptor();

    public UsrVLisEntiSiamAppEnteRowBean() {
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

    public BigDecimal getIdEnteSiam() {
        return getBigDecimal("id_ente_siam");
    }

    public void setIdEnteSiam(BigDecimal idEnteSiam) {
        setObject("id_ente_siam", idEnteSiam);
    }

    public String getNmEnteSiam() {
        return getString("nm_ente_siam");
    }

    public void setNmEnteSiam(String nmEnteSiam) {
        setObject("nm_ente_siam", nmEnteSiam);
    }

    public BigDecimal getIdAmbienteEnteConvenz() {
        return getBigDecimal("id_ambiente_ente_convenz");
    }

    public void setIdAmbienteEnteConvenz(BigDecimal idAmbienteEnteConvenz) {
        setObject("id_ambiente_ente_convenz", idAmbienteEnteConvenz);
    }

    public String getNmAmbienteEnteConvenz() {
        return getString("nm_ambiente_ente_convenz");
    }

    public void setNmAmbienteEnteConvenz(String nmAmbienteEnteConvenz) {
        setObject("nm_ambiente_ente_convenz", nmAmbienteEnteConvenz);
    }

    public String getDsAmbienteEnteConvenz() {
        return getString("ds_ambiente_ente_convenz");
    }

    public void setDsAmbienteEnteConvenz(String dsAmbienteEnteConvenz) {
        setObject("ds_ambiente_ente_convenz", dsAmbienteEnteConvenz);
    }

    public String getNmAmbienteEnte() {
        return getString("nm_ambiente_ente");
    }

    public void setNmAmbienteEnte(String nmAmbienteEnte) {
        setObject("nm_ambiente_ente", nmAmbienteEnte);
    }

    @Override
    public void entityToRowBean(Object obj) {
        UsrVLisEntiSiamAppEnte entity = (UsrVLisEntiSiamAppEnte) obj;
        this.setIdUserIamCor(entity.getIdUserIamCor());
        this.setIdEnteSiam(entity.getIdEnteSiam());
        this.setNmEnteSiam(entity.getNmEnteSiam());
        this.setIdAmbienteEnteConvenz(entity.getIdAmbienteEnteConvenz());
        this.setNmAmbienteEnteConvenz(entity.getNmAmbienteEnteConvenz());
        this.setDsAmbienteEnteConvenz(entity.getDsAmbienteEnteConvenz());
    }

    @Override
    public UsrVLisEntiSiamAppEnte rowBeanToEntity() {
        UsrVLisEntiSiamAppEnte entity = new UsrVLisEntiSiamAppEnte();
        entity.setIdUserIamCor(this.getIdUserIamCor());
        entity.setIdEnteSiam(this.getIdEnteSiam());
        entity.setNmEnteSiam(this.getNmEnteSiam());
        entity.setIdAmbienteEnteConvenz(this.getIdAmbienteEnteConvenz());
        entity.setNmAmbienteEnteConvenz(this.getNmAmbienteEnteConvenz());
        entity.setDsAmbienteEnteConvenz(this.getDsAmbienteEnteConvenz());
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
