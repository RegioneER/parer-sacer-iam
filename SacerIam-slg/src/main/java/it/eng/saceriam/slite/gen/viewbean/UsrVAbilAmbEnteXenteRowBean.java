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

import it.eng.saceriam.viewEntity.UsrVAbilAmbEnteXente;
import it.eng.saceriam.viewEntity.UsrVAbilAmbEnteXenteId;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Usr_V_Abil_Amb_Ente_Xente
 *
 */
public class UsrVAbilAmbEnteXenteRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Friday, 24 February 2017 11:20" )
     */

    public static UsrVAbilAmbEnteXenteTableDescriptor TABLE_DESCRIPTOR = new UsrVAbilAmbEnteXenteTableDescriptor();

    public UsrVAbilAmbEnteXenteRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    public BigDecimal getIdUserIam() {
        return getBigDecimal("id_user_iam");
    }

    public void setIdUserIam(BigDecimal idUserIam) {
        setObject("id_user_iam", idUserIam);
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

    @Override
    public void entityToRowBean(Object obj) {
        UsrVAbilAmbEnteXente entity = (UsrVAbilAmbEnteXente) obj;
        this.setIdUserIam(
                entity.getUsrVAbilAmbEnteXenteId() == null ? null : entity.getUsrVAbilAmbEnteXenteId().getIdUserIam());
        this.setIdAmbienteEnteConvenz(entity.getUsrVAbilAmbEnteXenteId() == null ? null
                : entity.getUsrVAbilAmbEnteXenteId().getIdAmbienteEnteConvenz());
        this.setNmAmbienteEnteConvenz(entity.getNmAmbienteEnteConvenz());
        this.setDsAmbienteEnteConvenz(entity.getDsAmbienteEnteConvenz());
    }

    @Override
    public UsrVAbilAmbEnteXente rowBeanToEntity() {
        UsrVAbilAmbEnteXente entity = new UsrVAbilAmbEnteXente();
        entity.setUsrVAbilAmbEnteXenteId(new UsrVAbilAmbEnteXenteId());
        entity.getUsrVAbilAmbEnteXenteId().setIdUserIam(this.getIdUserIam());
        entity.getUsrVAbilAmbEnteXenteId().setIdAmbienteEnteConvenz(this.getIdAmbienteEnteConvenz());
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
