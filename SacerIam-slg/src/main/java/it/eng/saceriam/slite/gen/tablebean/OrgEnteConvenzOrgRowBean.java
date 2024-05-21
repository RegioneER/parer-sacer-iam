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

import it.eng.saceriam.entity.OrgEnteConvenzOrg;
import it.eng.saceriam.entity.OrgEnteSiam;
import it.eng.saceriam.entity.UsrOrganizIam;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Org_Ente_Convenz_Org
 *
 */
public class OrgEnteConvenzOrgRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Thursday, 16 June 2016 15:51" )
     */

    public static OrgEnteConvenzOrgTableDescriptor TABLE_DESCRIPTOR = new OrgEnteConvenzOrgTableDescriptor();

    public OrgEnteConvenzOrgRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    // getter e setter
    public BigDecimal getIdEnteConvenzOrg() {
        return getBigDecimal("id_ente_convenz_org");
    }

    public void setIdEnteConvenzOrg(BigDecimal idEnteConvenzOrg) {
        setObject("id_ente_convenz_org", idEnteConvenzOrg);
    }

    public BigDecimal getIdEnteConvenz() {
        return getBigDecimal("id_ente_convenz");
    }

    public void setIdEnteConvenz(BigDecimal idEnteConvenz) {
        setObject("id_ente_convenz", idEnteConvenz);
    }

    public BigDecimal getIdOrganizIam() {
        return getBigDecimal("id_organiz_iam");
    }

    public void setIdOrganizIam(BigDecimal idOrganizIam) {
        setObject("id_organiz_iam", idOrganizIam);
    }

    public Timestamp getDtIniVal() {
        return getTimestamp("dt_ini_val");
    }

    public void setDtIniVal(Timestamp dtIniVal) {
        setObject("dt_ini_val", dtIniVal);
    }

    public Timestamp getDtFineVal() {
        return getTimestamp("dt_fine_val");
    }

    public void setDtFineVal(Timestamp dtFineVal) {
        setObject("dt_fine_val", dtFineVal);
    }

    @Override
    public void entityToRowBean(Object obj) {
        OrgEnteConvenzOrg entity = (OrgEnteConvenzOrg) obj;
        this.setIdEnteConvenzOrg(new BigDecimal(entity.getIdEnteConvenzOrg()));
        if (entity.getOrgEnteSiam() != null) {
            this.setIdEnteConvenz(new BigDecimal(entity.getOrgEnteSiam().getIdEnteSiam()));

        }
        if (entity.getUsrOrganizIam() != null) {
            this.setIdOrganizIam(new BigDecimal(entity.getUsrOrganizIam().getIdOrganizIam()));

        }
        if (entity.getDtIniVal() != null) {
            this.setDtIniVal(new Timestamp(entity.getDtIniVal().getTime()));
        }
        if (entity.getDtFineVal() != null) {
            this.setDtFineVal(new Timestamp(entity.getDtFineVal().getTime()));
        }
    }

    @Override
    public OrgEnteConvenzOrg rowBeanToEntity() {
        OrgEnteConvenzOrg entity = new OrgEnteConvenzOrg();
        if (this.getIdEnteConvenzOrg() != null) {
            entity.setIdEnteConvenzOrg(this.getIdEnteConvenzOrg().longValue());
        }
        if (this.getIdEnteConvenz() != null) {
            if (entity.getOrgEnteSiam() == null) {
                entity.setOrgEnteSiam(new OrgEnteSiam());
            }
            entity.getOrgEnteSiam().setIdEnteSiam(this.getIdEnteConvenz().longValue());
        }
        if (this.getIdOrganizIam() != null) {
            if (entity.getUsrOrganizIam() == null) {
                entity.setUsrOrganizIam(new UsrOrganizIam());
            }
            entity.getUsrOrganizIam().setIdOrganizIam(this.getIdOrganizIam().longValue());
        }
        entity.setDtIniVal(this.getDtIniVal());
        entity.setDtFineVal(this.getDtFineVal());
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
