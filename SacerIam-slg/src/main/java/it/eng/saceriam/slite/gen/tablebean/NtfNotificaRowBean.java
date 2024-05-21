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

import it.eng.saceriam.entity.DecModelloComunic;
import it.eng.saceriam.entity.NtfNotifica;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Ntf_Notifica
 *
 */
public class NtfNotificaRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Monday, 12 June 2017 17:13" )
     */

    public static NtfNotificaTableDescriptor TABLE_DESCRIPTOR = new NtfNotificaTableDescriptor();

    public NtfNotificaRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    // getter e setter
    public BigDecimal getIdNotifica() {
        return getBigDecimal("id_notifica");
    }

    public void setIdNotifica(BigDecimal idNotifica) {
        setObject("id_notifica", idNotifica);
    }

    public BigDecimal getIdModelloComunic() {
        return getBigDecimal("id_modello_comunic");
    }

    public void setIdModelloComunic(BigDecimal idModelloComunic) {
        setObject("id_modello_comunic", idModelloComunic);
    }

    public String getDsEmailDestinatario() {
        return getString("ds_email_destinatario");
    }

    public void setDsEmailDestinatario(String dsEmailDestinatario) {
        setObject("ds_email_destinatario", dsEmailDestinatario);
    }

    public Timestamp getTsNotif() {
        return getTimestamp("ts_notif");
    }

    public void setTsNotif(Timestamp tsNotif) {
        setObject("ts_notif", tsNotif);
    }

    public String getNmMittente() {
        return getString("nm_mittente");
    }

    public void setNmMittente(String nmMittente) {
        setObject("nm_mittente", nmMittente);
    }

    public String getDsOggetto() {
        return getString("ds_oggetto");
    }

    public void setDsOggetto(String dsOggetto) {
        setObject("ds_oggetto", dsOggetto);
    }

    public String getDsTestoNotifica() {
        return getString("ds_testo_notifica");
    }

    public void setDsTestoNotifica(String dsTestoNotifica) {
        setObject("ds_testo_notifica", dsTestoNotifica);
    }

    public String getDsDestinatario() {
        return getString("ds_destinatario");
    }

    public void setDsDestinatario(String dsDestinatario) {
        setObject("ds_destinatario", dsDestinatario);
    }

    public String getTiStatoNotif() {
        return getString("ti_stato_notif");
    }

    public void setTiStatoNotif(String tiStatoNotif) {
        setObject("ti_stato_notif", tiStatoNotif);
    }

    @Override
    public void entityToRowBean(Object obj) {
        NtfNotifica entity = (NtfNotifica) obj;
        this.setIdNotifica(new BigDecimal(entity.getIdNotifica()));
        if (entity.getDecModelloComunic() != null) {
            this.setIdModelloComunic(new BigDecimal(entity.getDecModelloComunic().getIdModelloComunic()));
        }
        this.setDsEmailDestinatario(entity.getDsEmailDestinatario());
        this.setTsNotif(entity.getTsNotif());
        this.setNmMittente(entity.getNmMittente());
        this.setDsOggetto(entity.getDsOggetto());
        this.setDsTestoNotifica(entity.getDsTestoNotifica());
        this.setDsDestinatario(entity.getDsDestinatario());
        this.setTiStatoNotif(entity.getTiStatoNotif());
    }

    @Override
    public NtfNotifica rowBeanToEntity() {
        NtfNotifica entity = new NtfNotifica();
        if (this.getIdNotifica() != null) {
            entity.setIdNotifica(this.getIdNotifica().longValue());
        }
        if (this.getIdModelloComunic() != null) {
            if (entity.getDecModelloComunic() == null) {
                entity.setDecModelloComunic(new DecModelloComunic());
            }
            entity.getDecModelloComunic().setIdModelloComunic(this.getIdModelloComunic().longValue());
        }
        entity.setDsEmailDestinatario(this.getDsEmailDestinatario());
        entity.setTsNotif(this.getTsNotif());
        entity.setNmMittente(this.getNmMittente());
        entity.setDsOggetto(this.getDsOggetto());
        entity.setDsTestoNotifica(this.getDsTestoNotifica());
        entity.setDsDestinatario(this.getDsDestinatario());
        entity.setTiStatoNotif(this.getTiStatoNotif());
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
