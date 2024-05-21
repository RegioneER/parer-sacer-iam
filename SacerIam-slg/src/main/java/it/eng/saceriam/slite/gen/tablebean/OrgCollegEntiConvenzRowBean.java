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
import java.sql.Timestamp;

import it.eng.saceriam.entity.OrgCollegEntiConvenz;
import it.eng.saceriam.entity.OrgEnteSiam;
import it.eng.saceriam.entity.constraint.ConstOrgCollegEntiConvenz.TiColleg;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Org_Colleg_Enti_Convenz
 *
 */
public class OrgCollegEntiConvenzRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Tuesday, 19 March 2019 10:45" )
     */

    public static OrgCollegEntiConvenzTableDescriptor TABLE_DESCRIPTOR = new OrgCollegEntiConvenzTableDescriptor();

    public OrgCollegEntiConvenzRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    // getter e setter
    public BigDecimal getIdCollegEntiConvenz() {
        return getBigDecimal("id_colleg_enti_convenz");
    }

    public void setIdCollegEntiConvenz(BigDecimal idCollegEntiConvenz) {
        setObject("id_colleg_enti_convenz", idCollegEntiConvenz);
    }

    public String getNmColleg() {
        return getString("nm_colleg");
    }

    public void setNmColleg(String nmColleg) {
        setObject("nm_colleg", nmColleg);
    }

    public String getDsColleg() {
        return getString("ds_colleg");
    }

    public void setDsColleg(String dsColleg) {
        setObject("ds_colleg", dsColleg);
    }

    public String getTiColleg() {
        return getString("ti_colleg");
    }

    public void setTiColleg(String tiColleg) {
        setObject("ti_colleg", tiColleg);
    }

    public BigDecimal getIdEnteConvenzCapofila() {
        return getBigDecimal("id_ente_convenz_capofila");
    }

    public void setIdEnteConvenzCapofila(BigDecimal idEnteConvenzCapofila) {
        setObject("id_ente_convenz_capofila", idEnteConvenzCapofila);
    }

    public Timestamp getDtIniVal() {
        return getTimestamp("dt_ini_val");
    }

    public void setDtIniVal(Timestamp dtIniVal) {
        setObject("dt_ini_val", dtIniVal);
    }

    public Timestamp getDtFinVal() {
        return getTimestamp("dt_fin_val");
    }

    public void setDtFinVal(Timestamp dtFinVal) {
        setObject("dt_fin_val", dtFinVal);
    }

    @Override
    public void entityToRowBean(Object obj) {
        OrgCollegEntiConvenz entity = (OrgCollegEntiConvenz) obj;
        this.setIdCollegEntiConvenz(new BigDecimal(entity.getIdCollegEntiConvenz()));
        this.setNmColleg(entity.getNmColleg());
        this.setDsColleg(entity.getDsColleg());
        this.setTiColleg(entity.getTiColleg().name());
        if (entity.getOrgEnteSiam() != null) {
            this.setIdEnteConvenzCapofila(new BigDecimal(entity.getOrgEnteSiam().getIdEnteSiam()));

        }
        if (entity.getDtIniVal() != null) {
            this.setDtIniVal(new Timestamp(entity.getDtIniVal().getTime()));
        }
        if (entity.getDtFinVal() != null) {
            this.setDtFinVal(new Timestamp(entity.getDtFinVal().getTime()));
        }
    }

    @Override
    public OrgCollegEntiConvenz rowBeanToEntity() {
        OrgCollegEntiConvenz entity = new OrgCollegEntiConvenz();
        if (this.getIdCollegEntiConvenz() != null) {
            entity.setIdCollegEntiConvenz(this.getIdCollegEntiConvenz().longValue());
        }
        entity.setNmColleg(this.getNmColleg());
        entity.setDsColleg(this.getDsColleg());
        entity.setTiColleg(TiColleg.valueOf(this.getTiColleg()));
        if (this.getIdEnteConvenzCapofila() != null) {
            if (entity.getOrgEnteSiam() == null) {
                entity.setOrgEnteSiam(new OrgEnteSiam());
            }
            entity.getOrgEnteSiam().setIdEnteSiam(this.getIdEnteConvenzCapofila().longValue());
        }
        entity.setDtIniVal(this.getDtIniVal());
        entity.setDtFinVal(this.getDtFinVal());
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
