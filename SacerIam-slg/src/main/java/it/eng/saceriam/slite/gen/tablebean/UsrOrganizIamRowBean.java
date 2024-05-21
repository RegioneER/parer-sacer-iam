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
import it.eng.saceriam.entity.AplTipoOrganiz;
import it.eng.saceriam.entity.OrgEnteSiam;
import it.eng.saceriam.entity.UsrOrganizIam;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Usr_Organiz_Iam
 *
 */
public class UsrOrganizIamRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Wednesday, 19 March 2014 10:37" )
     */

    public static UsrOrganizIamTableDescriptor TABLE_DESCRIPTOR = new UsrOrganizIamTableDescriptor();

    public UsrOrganizIamRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    // getter e setter
    public BigDecimal getIdOrganizIam() {
        return getBigDecimal("id_organiz_iam");
    }

    public void setIdOrganizIam(BigDecimal idOrganizIam) {
        setObject("id_organiz_iam", idOrganizIam);
    }

    public BigDecimal getIdApplic() {
        return getBigDecimal("id_applic");
    }

    public void setIdApplic(BigDecimal idApplic) {
        setObject("id_applic", idApplic);
    }

    public BigDecimal getIdOrganizApplic() {
        return getBigDecimal("id_organiz_applic");
    }

    public void setIdOrganizApplic(BigDecimal idOrganizApplic) {
        setObject("id_organiz_applic", idOrganizApplic);
    }

    public BigDecimal getIdTipoOrganiz() {
        return getBigDecimal("id_tipo_organiz");
    }

    public void setIdTipoOrganiz(BigDecimal idTipoOrganiz) {
        setObject("id_tipo_organiz", idTipoOrganiz);
    }

    public String getNmOrganiz() {
        return getString("nm_organiz");
    }

    public void setNmOrganiz(String nmOrganiz) {
        setObject("nm_organiz", nmOrganiz);
    }

    public String getDsOrganiz() {
        return getString("ds_organiz");
    }

    public void setDsOrganiz(String dsOrganiz) {
        setObject("ds_organiz", dsOrganiz);
    }

    public BigDecimal getIdOrganizIamPadre() {
        return getBigDecimal("id_organiz_iam_padre");
    }

    public void setIdOrganizIamPadre(BigDecimal idOrganizIamPadre) {
        setObject("id_organiz_iam_padre", idOrganizIamPadre);
    }

    public BigDecimal getIdEnteConserv() {
        return getBigDecimal("id_ente_conserv");
    }

    public void setIdEnteConserv(BigDecimal idEnteConserv) {
        setObject("id_ente_conserv", idEnteConserv);
    }

    public BigDecimal getIdEnteGestore() {
        return getBigDecimal("id_ente_gestore");
    }

    public void setIdEnteGestore(BigDecimal idEnteGestore) {
        setObject("id_ente_gestore", idEnteGestore);
    }

    @Override
    public void entityToRowBean(Object obj) {
        UsrOrganizIam entity = (UsrOrganizIam) obj;
        this.setIdOrganizIam(new BigDecimal(entity.getIdOrganizIam()));
        if (entity.getAplApplic() != null) {
            this.setIdApplic(new BigDecimal(entity.getAplApplic().getIdApplic()));

        }
        this.setIdOrganizApplic(entity.getIdOrganizApplic());
        if (entity.getAplTipoOrganiz() != null) {
            this.setIdTipoOrganiz(new BigDecimal(entity.getAplTipoOrganiz().getIdTipoOrganiz()));

        }
        this.setNmOrganiz(entity.getNmOrganiz());
        this.setDsOrganiz(entity.getDsOrganiz());
        if (entity.getUsrOrganizIam() != null) {
            this.setIdOrganizIamPadre(new BigDecimal(entity.getUsrOrganizIam().getIdOrganizIam()));

        }
        if (entity.getOrgEnteSiamConserv() != null) {
            this.setIdEnteConserv(new BigDecimal(entity.getOrgEnteSiamConserv().getIdEnteSiam()));

        }
        if (entity.getOrgEnteSiamGestore() != null) {
            this.setIdEnteGestore(new BigDecimal(entity.getOrgEnteSiamGestore().getIdEnteSiam()));

        }
    }

    @Override
    public UsrOrganizIam rowBeanToEntity() {
        UsrOrganizIam entity = new UsrOrganizIam();
        if (this.getIdOrganizIam() != null) {
            entity.setIdOrganizIam(this.getIdOrganizIam().longValue());
        }
        if (this.getIdApplic() != null) {
            if (entity.getAplApplic() == null) {
                entity.setAplApplic(new AplApplic());
            }
            entity.getAplApplic().setIdApplic(this.getIdApplic().longValue());
        }
        entity.setIdOrganizApplic(this.getIdOrganizApplic());
        if (this.getIdTipoOrganiz() != null) {
            if (entity.getAplTipoOrganiz() == null) {
                entity.setAplTipoOrganiz(new AplTipoOrganiz());
            }
            entity.getAplTipoOrganiz().setIdTipoOrganiz(this.getIdTipoOrganiz().longValue());
        }
        entity.setNmOrganiz(this.getNmOrganiz());
        entity.setDsOrganiz(this.getDsOrganiz());
        if (this.getIdOrganizIamPadre() != null) {
            if (entity.getUsrOrganizIam() == null) {
                entity.setUsrOrganizIam(new UsrOrganizIam());
            }
            entity.getUsrOrganizIam().setIdOrganizIam(this.getIdOrganizIamPadre().longValue());
        }
        if (this.getIdEnteConserv() != null) {
            if (entity.getOrgEnteSiamConserv() == null) {
                entity.setOrgEnteSiamConserv(new OrgEnteSiam());
            }
            entity.getOrgEnteSiamConserv().setIdEnteSiam(this.getIdEnteConserv().longValue());
        }
        if (this.getIdEnteGestore() != null) {
            if (entity.getOrgEnteSiamGestore() == null) {
                entity.setOrgEnteSiamGestore(new OrgEnteSiam());
            }
            entity.getOrgEnteSiamGestore().setIdEnteSiam(this.getIdEnteGestore().longValue());
        }
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
