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

import it.eng.saceriam.viewEntity.UsrVAbilDatiToAdd;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Usr_V_Abil_Dati_To_Add
 *
 */
public class UsrVAbilDatiToAddRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Friday, 10 February 2017 11:56" )
     */

    public static UsrVAbilDatiToAddTableDescriptor TABLE_DESCRIPTOR = new UsrVAbilDatiToAddTableDescriptor();

    public UsrVAbilDatiToAddRowBean() {
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

    public BigDecimal getIdUsoUserApplic() {
        return getBigDecimal("id_uso_user_applic");
    }

    public void setIdUsoUserApplic(BigDecimal idUsoUserApplic) {
        setObject("id_uso_user_applic", idUsoUserApplic);
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

    public BigDecimal getIdDichAbilDati() {
        return getBigDecimal("id_dich_abil_dati");
    }

    public void setIdDichAbilDati(BigDecimal idDichAbilDati) {
        setObject("id_dich_abil_dati", idDichAbilDati);
    }

    public BigDecimal getIdTipoDatoIam() {
        return getBigDecimal("id_tipo_dato_iam");
    }

    public void setIdTipoDatoIam(BigDecimal idTipoDatoIam) {
        setObject("id_tipo_dato_iam", idTipoDatoIam);
    }

    public BigDecimal getIdClasseTipoDato() {
        return getBigDecimal("id_classe_tipo_dato");
    }

    public void setIdClasseTipoDato(BigDecimal idClasseTipoDato) {
        setObject("id_classe_tipo_dato", idClasseTipoDato);
    }

    @Override
    public void entityToRowBean(Object obj) {
        UsrVAbilDatiToAdd entity = (UsrVAbilDatiToAdd) obj;
        this.setIdUserIam(
                entity.getUsrVAbilDatiToAddId() == null ? null : entity.getUsrVAbilDatiToAddId().getIdUserIam());
        this.setIdUsoUserApplic(entity.getIdUsoUserApplic());
        this.setIdApplic(entity.getIdApplic());
        this.setNmApplic(entity.getNmApplic());
        this.setIdDichAbilDati(entity.getIdDichAbilDati());
        this.setIdTipoDatoIam(
                entity.getUsrVAbilDatiToAddId() == null ? null : entity.getUsrVAbilDatiToAddId().getIdTipoDatoIam());
        this.setIdClasseTipoDato(entity.getIdClasseTipoDato());
    }

    @Override
    public UsrVAbilDatiToAdd rowBeanToEntity() {
        UsrVAbilDatiToAdd entity = new UsrVAbilDatiToAdd();
        entity.getUsrVAbilDatiToAddId().setIdUserIam(this.getIdUserIam());
        entity.setIdUsoUserApplic(this.getIdUsoUserApplic());
        entity.setIdApplic(this.getIdApplic());
        entity.setNmApplic(this.getNmApplic());
        entity.setIdDichAbilDati(this.getIdDichAbilDati());
        entity.getUsrVAbilDatiToAddId().setIdTipoDatoIam(this.getIdTipoDatoIam());
        entity.setIdClasseTipoDato(this.getIdClasseTipoDato());
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
