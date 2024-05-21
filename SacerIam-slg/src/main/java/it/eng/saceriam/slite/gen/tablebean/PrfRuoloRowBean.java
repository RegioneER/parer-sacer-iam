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

import it.eng.saceriam.entity.PrfRuolo;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Prf_Ruolo
 *
 */
public class PrfRuoloRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Thursday, 19 October 2017 15:20" )
     */

    public static PrfRuoloTableDescriptor TABLE_DESCRIPTOR = new PrfRuoloTableDescriptor();

    public PrfRuoloRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    // getter e setter
    public BigDecimal getIdRuolo() {
        return getBigDecimal("id_ruolo");
    }

    public void setIdRuolo(BigDecimal idRuolo) {
        setObject("id_ruolo", idRuolo);
    }

    public String getNmRuolo() {
        return getString("nm_ruolo");
    }

    public void setNmRuolo(String nmRuolo) {
        setObject("nm_ruolo", nmRuolo);
    }

    public String getDsRuolo() {
        return getString("ds_ruolo");
    }

    public void setDsRuolo(String dsRuolo) {
        setObject("ds_ruolo", dsRuolo);
    }

    public String getTiRuolo() {
        return getString("ti_ruolo");
    }

    public void setTiRuolo(String tiRuolo) {
        setObject("ti_ruolo", tiRuolo);
    }

    public String getTiStatoRichAllineaRuoli_1() {
        return getString("ti_stato_rich_allinea_ruoli_1");
    }

    public void setTiStatoRichAllineaRuoli_1(String ti_stato_rich_allinea_ruoli_1) {
        setObject("ti_stato_rich_allinea_ruoli_1", ti_stato_rich_allinea_ruoli_1);
    }

    public String getTiStatoRichAllineaRuoli_2() {
        return getString("ti_stato_rich_allinea_ruoli_2");
    }

    public void setTiStatoRichAllineaRuoli_2(String ti_stato_rich_allinea_ruoli_2) {
        setObject("ti_stato_rich_allinea_ruoli_2", ti_stato_rich_allinea_ruoli_2);
    }

    public String getFlAllineamentoInCorso() {
        return getString("fl_allineamento_in_corso");
    }

    public void setFlAllineamentoInCorso(String flAllineamentoInCorso) {
        setObject("fl_allineamento_in_corso", flAllineamentoInCorso);
    }

    public String getDsEsitoRichAllineaRuoli_1() {
        return getString("ds_esito_rich_allinea_ruoli_1");
    }

    public void setDsEsitoRichAllineaRuoli_1(String ds_esito_rich_allinea_ruoli_1) {
        setObject("ds_esito_rich_allinea_ruoli_1", ds_esito_rich_allinea_ruoli_1);
    }

    public String getDsEsitoRichAllineaRuoli_2() {
        return getString("ds_esito_rich_allinea_ruoli_2");
    }

    public void setDsEsitoRichAllineaRuoli_2(String ds_esito_rich_allinea_ruoli_2) {
        setObject("ds_esito_rich_allinea_ruoli_2", ds_esito_rich_allinea_ruoli_2);
    }

    public String getDsMsgAllineamentoParz() {
        return getString("ds_msg_allineamento_parz");
    }

    public void setDsMsgAllineamentoParz(String dsMsgAllineamentoParz) {
        setObject("ds_msg_allineamento_parz", dsMsgAllineamentoParz);
    }

    @Override
    public void entityToRowBean(Object obj) {
        PrfRuolo entity = (PrfRuolo) obj;
        this.setIdRuolo(new BigDecimal(entity.getIdRuolo()));
        this.setNmRuolo(entity.getNmRuolo());
        this.setDsRuolo(entity.getDsRuolo());
        this.setTiRuolo(entity.getTiRuolo());
        this.setTiStatoRichAllineaRuoli_1(entity.getTiStatoRichAllineaRuoli_1());
        this.setTiStatoRichAllineaRuoli_2(entity.getTiStatoRichAllineaRuoli_2());
        this.setFlAllineamentoInCorso(entity.getFlAllineamentoInCorso());
        this.setDsEsitoRichAllineaRuoli_1(entity.getDsEsitoRichAllineaRuoli_1());
        this.setDsEsitoRichAllineaRuoli_2(entity.getDsEsitoRichAllineaRuoli_2());
        this.setDsMsgAllineamentoParz(entity.getDsMsgAllineamentoParz());
    }

    @Override
    public PrfRuolo rowBeanToEntity() {
        PrfRuolo entity = new PrfRuolo();
        if (this.getIdRuolo() != null) {
            entity.setIdRuolo(this.getIdRuolo().longValue());
        }
        entity.setNmRuolo(this.getNmRuolo());
        entity.setDsRuolo(this.getDsRuolo());
        entity.setTiRuolo(this.getTiRuolo());
        entity.setTiStatoRichAllineaRuoli_1(this.getTiStatoRichAllineaRuoli_1());
        entity.setTiStatoRichAllineaRuoli_2(this.getTiStatoRichAllineaRuoli_2());
        entity.setFlAllineamentoInCorso(this.getFlAllineamentoInCorso());
        entity.setDsEsitoRichAllineaRuoli_1(this.getDsEsitoRichAllineaRuoli_1());
        entity.setDsEsitoRichAllineaRuoli_2(this.getDsEsitoRichAllineaRuoli_2());
        entity.setDsMsgAllineamentoParz(this.getDsMsgAllineamentoParz());
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
