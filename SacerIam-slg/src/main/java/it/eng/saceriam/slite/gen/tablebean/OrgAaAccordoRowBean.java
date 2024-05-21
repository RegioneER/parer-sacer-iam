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

import it.eng.saceriam.entity.OrgAaAccordo;
import it.eng.saceriam.entity.OrgAccordoEnte;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Org_Aa_Accordo
 *
 */
public class OrgAaAccordoRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Tuesday, 29 September 2020 18:05" )
     */

    public static OrgAaAccordoTableDescriptor TABLE_DESCRIPTOR = new OrgAaAccordoTableDescriptor();

    public OrgAaAccordoRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    // getter e setter
    public BigDecimal getIdAaAccordo() {
        return getBigDecimal("id_aa_accordo");
    }

    public void setIdAaAccordo(BigDecimal id_aa_accordo) {
        setObject("id_aa_accordo", id_aa_accordo);
    }

    public BigDecimal getIdAccordoEnte() {
        return getBigDecimal("id_accordo_ente");
    }

    public void setIdAccordoEnte(BigDecimal id_accordo_ente) {
        setObject("id_accordo_ente", id_accordo_ente);
    }

    public BigDecimal getAaAnnoAccordo() {
        return getBigDecimal("aa_anno_accordo");
    }

    public void setAaAnnoAccordo(BigDecimal aa_anno_accordo) {
        setObject("aa_anno_accordo", aa_anno_accordo);
    }

    public BigDecimal getMmAaAccordo() {
        return getBigDecimal("mm_aa_accordo");
    }

    public void setMmAaAccordo(BigDecimal mm_aa_accordo) {
        setObject("mm_aa_accordo", mm_aa_accordo);
    }

    public String getCdCigAaAccordo() {
        return getString("cd_cig_aa_accordo");
    }

    public void setCdCigAaAccordo(String cd_cig_aa_accordo) {
        setObject("cd_cig_aa_accordo", cd_cig_aa_accordo);
    }

    public String getCdCupAaAccordo() {
        return getString("cd_cup_aa_accordo");
    }

    public void setCdCupAaAccordo(String cd_cup_aa_accordo) {
        setObject("cd_cup_aa_accordo", cd_cup_aa_accordo);
    }

    public String getCdOdaAaAccordo() {
        return getString("cd_oda_aa_accordo");
    }

    public void setCdOdaAaAccordo(String cd_oda_aa_accordo) {
        setObject("cd_oda_aa_accordo", cd_oda_aa_accordo);
    }

    public String getDsNotaAaAccordo() {
        return getString("ds_nota_aa_accordo");
    }

    public void setDsNotaAaAccordo(String ds_nota_aa_accordo) {
        setObject("ds_nota_aa_accordo", ds_nota_aa_accordo);
    }

    public BigDecimal getImCanoneAaAccordo() {
        return getBigDecimal("im_canone_aa_accordo");
    }

    public void setImCanoneAaAccordo(BigDecimal im_canone_aa_accordo) {
        setObject("im_canone_aa_accordo", im_canone_aa_accordo);
    }

    public String getDsAttoAaAccordo() {
        return getString("ds_atto_aa_accordo");
    }

    public void setDsAttoAaAccordo(String ds_atto_aa_accordo) {
        setObject("ds_atto_aa_accordo", ds_atto_aa_accordo);
    }

    public String getCdCapitoloAaAccordo() {
        return getString("cd_capitolo_aa_accordo");
    }

    public void setCdCapitoloAaAccordo(String cd_capitolo_aa_accordo) {
        setObject("cd_capitolo_aa_accordo", cd_capitolo_aa_accordo);
    }

    public String getDsImpegnoAaAccordo() {
        return getString("ds_impegno_aa_accordo");
    }

    public void setDsImpegnoAaAccordo(String ds_impegno_aa_accordo) {
        setObject("ds_impegno_aa_accordo", ds_impegno_aa_accordo);
    }

    @Override
    public void entityToRowBean(Object obj) {
        OrgAaAccordo entity = (OrgAaAccordo) obj;
        this.setIdAaAccordo(new BigDecimal(entity.getIdAaAccordo()));

        if (entity.getOrgAccordoEnte() != null) {
            this.setIdAccordoEnte(new BigDecimal(entity.getOrgAccordoEnte().getIdAccordoEnte()));
        }

        this.setAaAnnoAccordo(entity.getAaAnnoAccordo());
        this.setMmAaAccordo(entity.getMmAaAccordo());
        this.setCdCigAaAccordo(entity.getCdCigAaAccordo());
        this.setCdCupAaAccordo(entity.getCdCupAaAccordo());
        this.setCdOdaAaAccordo(entity.getCdOdaAaAccordo());
        this.setDsNotaAaAccordo(entity.getDsNotaAaAccordo());
        this.setImCanoneAaAccordo(entity.getImCanoneAaAccordo());
        this.setDsAttoAaAccordo(entity.getDsAttoAaAccordo());
        this.setCdCapitoloAaAccordo(entity.getCdCapitoloAaAccordo());
        this.setDsImpegnoAaAccordo(entity.getDsImpegnoAaAccordo());
    }

    @Override
    public OrgAaAccordo rowBeanToEntity() {
        OrgAaAccordo entity = new OrgAaAccordo();
        if (this.getIdAaAccordo() != null) {
            entity.setIdAaAccordo(this.getIdAaAccordo().longValue());
        }
        if (this.getIdAccordoEnte() != null) {
            if (entity.getOrgAccordoEnte() == null) {
                entity.setOrgAccordoEnte(new OrgAccordoEnte());
            }
            entity.getOrgAccordoEnte().setIdAccordoEnte(this.getIdAccordoEnte().longValue());
        }
        entity.setAaAnnoAccordo(this.getAaAnnoAccordo());
        entity.setMmAaAccordo(this.getMmAaAccordo());
        entity.setCdCigAaAccordo(this.getCdCigAaAccordo());
        entity.setCdCupAaAccordo(this.getCdCupAaAccordo());
        entity.setCdOdaAaAccordo(this.getCdOdaAaAccordo());
        entity.setDsNotaAaAccordo(this.getDsNotaAaAccordo());
        entity.setImCanoneAaAccordo(this.getImCanoneAaAccordo());
        entity.setDsAttoAaAccordo(this.getDsAttoAaAccordo());
        entity.setCdCapitoloAaAccordo(this.getCdCapitoloAaAccordo());
        entity.setDsImpegnoAaAccordo(this.getDsImpegnoAaAccordo());
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
