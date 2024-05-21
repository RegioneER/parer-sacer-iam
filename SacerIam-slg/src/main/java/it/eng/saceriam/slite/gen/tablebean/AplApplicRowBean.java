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

package it.eng.saceriam.slite.gen.tablebean;

import java.math.BigDecimal;

import it.eng.saceriam.entity.AplApplic;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Apl_Applic
 *
 */
public class AplApplicRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Wednesday, 18 October 2017 12:13" )
     */

    public static AplApplicTableDescriptor TABLE_DESCRIPTOR = new AplApplicTableDescriptor();

    public AplApplicRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    // getter e setter
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

    public String getDsApplic() {
        return getString("ds_applic");
    }

    public void setDsApplic(String dsApplic) {
        setObject("ds_applic", dsApplic);
    }

    public String getDsUrlReplicaUser() {
        return getString("ds_url_replica_user");
    }

    public void setDsUrlReplicaUser(String dsUrlReplicaUser) {
        setObject("ds_url_replica_user", dsUrlReplicaUser);
    }

    public String getNmUserReplicaUser() {
        return getString("nm_user_replica_user");
    }

    public void setNmUserReplicaUser(String nmUserReplicaUser) {
        setObject("nm_user_replica_user", nmUserReplicaUser);
    }

    public String getCdPswReplicaUser() {
        return getString("cd_psw_replica_user");
    }

    public void setCdPswReplicaUser(String cdPswReplicaUser) {
        setObject("cd_psw_replica_user", cdPswReplicaUser);
    }

    public String getCdVersioneComp() {
        return getString("cd_versione_comp");
    }

    public void setCdVersioneComp(String cdVersioneComp) {
        setObject("cd_versione_comp", cdVersioneComp);
    }

    @Override
    public void entityToRowBean(Object obj) {
        AplApplic entity = (AplApplic) obj;
        this.setIdApplic(new BigDecimal(entity.getIdApplic()));
        this.setNmApplic(entity.getNmApplic());
        this.setDsApplic(entity.getDsApplic());
        this.setDsUrlReplicaUser(entity.getDsUrlReplicaUser());
        this.setNmUserReplicaUser(entity.getNmUserReplicaUser());
        this.setCdPswReplicaUser(entity.getCdPswReplicaUser());
        this.setCdVersioneComp(entity.getCdVersioneComp());
    }

    @Override
    public AplApplic rowBeanToEntity() {
        AplApplic entity = new AplApplic();
        if (this.getIdApplic() != null) {
            entity.setIdApplic(this.getIdApplic().longValue());
        }
        entity.setNmApplic(this.getNmApplic());
        entity.setDsApplic(this.getDsApplic());
        entity.setDsUrlReplicaUser(this.getDsUrlReplicaUser());
        entity.setNmUserReplicaUser(this.getNmUserReplicaUser());
        entity.setCdPswReplicaUser(this.getCdPswReplicaUser());
        entity.setCdVersioneComp(this.getCdVersioneComp());
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
