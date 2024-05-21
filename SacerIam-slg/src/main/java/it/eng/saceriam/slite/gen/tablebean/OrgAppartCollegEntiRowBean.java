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

import it.eng.saceriam.entity.OrgAppartCollegEnti;
import it.eng.saceriam.entity.OrgCollegEntiConvenz;
import it.eng.saceriam.entity.OrgEnteSiam;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Org_Appart_Colleg_Enti
 *
 */
public class OrgAppartCollegEntiRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Tuesday, 19 March 2019 10:45" )
     */

    public static OrgAppartCollegEntiTableDescriptor TABLE_DESCRIPTOR = new OrgAppartCollegEntiTableDescriptor();

    public OrgAppartCollegEntiRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    // getter e setter
    public BigDecimal getIdAppartCollegEnti() {
        return getBigDecimal("id_appart_colleg_enti");
    }

    public void setIdAppartCollegEnti(BigDecimal idAppartCollegEnti) {
        setObject("id_appart_colleg_enti", idAppartCollegEnti);
    }

    public BigDecimal getIdCollegEntiConvenz() {
        return getBigDecimal("id_colleg_enti_convenz");
    }

    public void setIdCollegEntiConvenz(BigDecimal idCollegEntiConvenz) {
        setObject("id_colleg_enti_convenz", idCollegEntiConvenz);
    }

    public BigDecimal getIdEnteConvenz() {
        return getBigDecimal("id_ente_convenz");
    }

    public void setIdEnteConvenz(BigDecimal idEnteConvenz) {
        setObject("id_ente_convenz", idEnteConvenz);
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
        OrgAppartCollegEnti entity = (OrgAppartCollegEnti) obj;
        this.setIdAppartCollegEnti(new BigDecimal(entity.getIdAppartCollegEnti()));
        if (entity.getOrgCollegEntiConvenz() != null) {
            this.setIdCollegEntiConvenz(new BigDecimal(entity.getOrgCollegEntiConvenz().getIdCollegEntiConvenz()));

        }
        if (entity.getOrgEnteSiam() != null) {
            this.setIdEnteConvenz(new BigDecimal(entity.getOrgEnteSiam().getIdEnteSiam()));

        }
        if (entity.getDtIniVal() != null) {
            this.setDtIniVal(new Timestamp(entity.getDtIniVal().getTime()));
        }
        if (entity.getDtFinVal() != null) {
            this.setDtFinVal(new Timestamp(entity.getDtFinVal().getTime()));
        }
    }

    @Override
    public OrgAppartCollegEnti rowBeanToEntity() {
        OrgAppartCollegEnti entity = new OrgAppartCollegEnti();
        if (this.getIdAppartCollegEnti() != null) {
            entity.setIdAppartCollegEnti(this.getIdAppartCollegEnti().longValue());
        }
        if (this.getIdCollegEntiConvenz() != null) {
            if (entity.getOrgCollegEntiConvenz() == null) {
                entity.setOrgCollegEntiConvenz(new OrgCollegEntiConvenz());
            }
            entity.getOrgCollegEntiConvenz().setIdCollegEntiConvenz(this.getIdCollegEntiConvenz().longValue());
        }
        if (this.getIdEnteConvenz() != null) {
            if (entity.getOrgEnteSiam() == null) {
                entity.setOrgEnteSiam(new OrgEnteSiam());
            }
            entity.getOrgEnteSiam().setIdEnteSiam(this.getIdEnteConvenz().longValue());
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
