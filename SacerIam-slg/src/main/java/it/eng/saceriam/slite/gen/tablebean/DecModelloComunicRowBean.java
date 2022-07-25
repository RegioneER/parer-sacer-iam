package it.eng.saceriam.slite.gen.tablebean;

import it.eng.saceriam.entity.DecModelloComunic;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * RowBean per la tabella Dec_Modello_Comunic
 *
 */
public class DecModelloComunicRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Monday, 12 June 2017 17:13" )
     */

    public static DecModelloComunicTableDescriptor TABLE_DESCRIPTOR = new DecModelloComunicTableDescriptor();

    public DecModelloComunicRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    // getter e setter
    public BigDecimal getIdModelloComunic() {
        return getBigDecimal("id_modello_comunic");
    }

    public void setIdModelloComunic(BigDecimal idModelloComunic) {
        setObject("id_modello_comunic", idModelloComunic);
    }

    public String getCdModelloComunic() {
        return getString("cd_modello_comunic");
    }

    public void setCdModelloComunic(String cdModelloComunic) {
        setObject("cd_modello_comunic", cdModelloComunic);
    }

    public String getDsModelloComunic() {
        return getString("ds_modello_comunic");
    }

    public void setDsModelloComunic(String dsModelloComunic) {
        setObject("ds_modello_comunic", dsModelloComunic);
    }

    public String getTiComunic() {
        return getString("ti_comunic");
    }

    public void setTiComunic(String tiComunic) {
        setObject("ti_comunic", tiComunic);
    }

    public String getTiOggettoQuery() {
        return getString("ti_oggetto_query");
    }

    public void setTiOggettoQuery(String tiOggettoQuery) {
        setObject("ti_oggetto_query", tiOggettoQuery);
    }

    public Timestamp getDtIstituz() {
        return getTimestamp("dt_istituz");
    }

    public void setDtIstituz(Timestamp dtIstituz) {
        setObject("dt_istituz", dtIstituz);
    }

    public Timestamp getDtSoppres() {
        return getTimestamp("dt_soppres");
    }

    public void setDtSoppres(Timestamp dtSoppres) {
        setObject("dt_soppres", dtSoppres);
    }

    public String getTiStatoTrigComunic() {
        return getString("ti_stato_trig_comunic");
    }

    public void setTiStatoTrigComunic(String tiStatoTrigComunic) {
        setObject("ti_stato_trig_comunic", tiStatoTrigComunic);
    }

    public String getNmMittente() {
        return getString("nm_mittente");
    }

    public void setNmMittente(String nmMittente) {
        setObject("nm_mittente", nmMittente);
    }

    public String getDsOggettoComunic() {
        return getString("ds_oggetto_comunic");
    }

    public void setDsOggettoComunic(String dsOggettoComunic) {
        setObject("ds_oggetto_comunic", dsOggettoComunic);
    }

    public String getBlTestoComunic() {
        return getString("bl_testo_comunic");
    }

    public void setBlTestoComunic(String blTestoComunic) {
        setObject("bl_testo_comunic", blTestoComunic);
    }

    @Override
    public void entityToRowBean(Object obj) {
        DecModelloComunic entity = (DecModelloComunic) obj;
        this.setIdModelloComunic(new BigDecimal(entity.getIdModelloComunic()));
        this.setCdModelloComunic(entity.getCdModelloComunic());
        this.setDsModelloComunic(entity.getDsModelloComunic());
        this.setTiComunic(entity.getTiComunic());
        this.setTiOggettoQuery(entity.getTiOggettoQuery());
        if (entity.getDtIstituz() != null) {
            this.setDtIstituz(new Timestamp(entity.getDtIstituz().getTime()));
        }
        if (entity.getDtSoppres() != null) {
            this.setDtSoppres(new Timestamp(entity.getDtSoppres().getTime()));
        }
        this.setTiStatoTrigComunic(entity.getTiStatoTrigComunic());
        this.setNmMittente(entity.getNmMittente());
        this.setDsOggettoComunic(entity.getDsOggettoComunic());
        this.setBlTestoComunic(entity.getBlTestoComunic());
    }

    @Override
    public DecModelloComunic rowBeanToEntity() {
        DecModelloComunic entity = new DecModelloComunic();
        if (this.getIdModelloComunic() != null) {
            entity.setIdModelloComunic(this.getIdModelloComunic().longValue());
        }
        entity.setCdModelloComunic(this.getCdModelloComunic());
        entity.setDsModelloComunic(this.getDsModelloComunic());
        entity.setTiComunic(this.getTiComunic());
        entity.setTiOggettoQuery(this.getTiOggettoQuery());
        entity.setDtIstituz(this.getDtIstituz());
        entity.setDtSoppres(this.getDtSoppres());
        entity.setTiStatoTrigComunic(this.getTiStatoTrigComunic());
        entity.setNmMittente(this.getNmMittente());
        entity.setDsOggettoComunic(this.getDsOggettoComunic());
        entity.setBlTestoComunic(this.getBlTestoComunic());
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
