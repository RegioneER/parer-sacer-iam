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

import it.eng.saceriam.viewEntity.PrfVCalcAutorRuolo;
import it.eng.saceriam.viewEntity.PrfVCalcAutorRuoloId;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Prf_V_Calc_Autor_Ruolo
 *
 */
public class PrfVCalcAutorRuoloRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Thursday, 9 June 2016 11:48" )
     */

    public static PrfVCalcAutorRuoloTableDescriptor TABLE_DESCRIPTOR = new PrfVCalcAutorRuoloTableDescriptor();

    public PrfVCalcAutorRuoloRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    public String getNmRuolo() {
        return getString("nm_ruolo");
    }

    public void setNmRuolo(String nmRuolo) {
        setObject("nm_ruolo", nmRuolo);
    }

    public BigDecimal getIdRuolo() {
        return getBigDecimal("id_ruolo");
    }

    public void setIdRuolo(BigDecimal idRuolo) {
        setObject("id_ruolo", idRuolo);
    }

    public String getNmApplic() {
        return getString("nm_applic");
    }

    public void setNmApplic(String nmApplic) {
        setObject("nm_applic", nmApplic);
    }

    public BigDecimal getIdApplic() {
        return getBigDecimal("id_applic");
    }

    public void setIdApplic(BigDecimal idApplic) {
        setObject("id_applic", idApplic);
    }

    public BigDecimal getIdUsoRuoloApplic() {
        return getBigDecimal("id_uso_ruolo_applic");
    }

    public void setIdUsoRuoloApplic(BigDecimal idUsoRuoloApplic) {
        setObject("id_uso_ruolo_applic", idUsoRuoloApplic);
    }

    public String getTiDichAutor() {
        return getString("ti_dich_autor");
    }

    public void setTiDichAutor(String tiDichAutor) {
        setObject("ti_dich_autor", tiDichAutor);
    }

    public BigDecimal getIdAutor() {
        return getBigDecimal("id_autor");
    }

    public void setIdAutor(BigDecimal idAutor) {
        setObject("id_autor", idAutor);
    }

    public String getDsAutor() {
        return getString("ds_autor");
    }

    public void setDsAutor(String dsAutor) {
        setObject("ds_autor", dsAutor);
    }

    @Override
    public void entityToRowBean(Object obj) {
        PrfVCalcAutorRuolo entity = (PrfVCalcAutorRuolo) obj;
        this.setNmRuolo(entity.getNmRuolo());
        this.setIdRuolo(
                entity.getPrfVCalcAutorRuoloId() == null ? null : entity.getPrfVCalcAutorRuoloId().getIdRuolo());
        this.setNmApplic(entity.getNmApplic());
        this.setIdApplic(
                entity.getPrfVCalcAutorRuoloId() == null ? null : entity.getPrfVCalcAutorRuoloId().getIdApplic());
        this.setIdUsoRuoloApplic(entity.getIdUsoRuoloApplic());
        this.setTiDichAutor(
                entity.getPrfVCalcAutorRuoloId() == null ? null : entity.getPrfVCalcAutorRuoloId().getTiDichAutor());
        this.setIdAutor(
                entity.getPrfVCalcAutorRuoloId() == null ? null : entity.getPrfVCalcAutorRuoloId().getIdAutor());
        this.setDsAutor(entity.getDsAutor());
    }

    @Override
    public PrfVCalcAutorRuolo rowBeanToEntity() {
        PrfVCalcAutorRuolo entity = new PrfVCalcAutorRuolo();
        entity.setPrfVCalcAutorRuoloId(new PrfVCalcAutorRuoloId());
        entity.setNmRuolo(this.getNmRuolo());
        entity.getPrfVCalcAutorRuoloId().setIdRuolo(this.getIdRuolo());
        entity.setNmApplic(this.getNmApplic());
        entity.getPrfVCalcAutorRuoloId().setIdApplic(this.getIdApplic());
        entity.setIdUsoRuoloApplic(this.getIdUsoRuoloApplic());
        entity.getPrfVCalcAutorRuoloId().setTiDichAutor(this.getTiDichAutor());
        entity.getPrfVCalcAutorRuoloId().setIdAutor(this.getIdAutor());
        entity.setDsAutor(this.getDsAutor());
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
