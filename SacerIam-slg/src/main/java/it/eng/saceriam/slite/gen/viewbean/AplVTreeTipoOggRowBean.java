package it.eng.saceriam.slite.gen.viewbean;

import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;
import it.eng.saceriam.viewEntity.AplVTreeTipoOgg;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Types;
import javax.annotation.Generated;

/**
 * RowBean per la tabella Apl_V_Tree_Tipo_Ogg
 *
 */
public class AplVTreeTipoOggRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Thursday, 9 June 2016 11:47" )
     */

    public static AplVTreeTipoOggTableDescriptor TABLE_DESCRIPTOR = new AplVTreeTipoOggTableDescriptor();

    public AplVTreeTipoOggRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

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

    public BigDecimal getIdTipoOggetto() {
        return getBigDecimal("id_tipo_oggetto");
    }

    public void setIdTipoOggetto(BigDecimal idTipoOggetto) {
        setObject("id_tipo_oggetto", idTipoOggetto);
    }

    public String getNmTipoOggetto() {
        return getString("nm_tipo_oggetto");
    }

    public void setNmTipoOggetto(String nmTipoOggetto) {
        setObject("nm_tipo_oggetto", nmTipoOggetto);
    }

    public BigDecimal getIdTipoOggettoPadre() {
        return getBigDecimal("id_tipo_oggetto_padre");
    }

    public void setIdTipoOggettoPadre(BigDecimal idTipoOggettoPadre) {
        setObject("id_tipo_oggetto_padre", idTipoOggettoPadre);
    }

    public String getDlCompositoTipoOggetto() {
        return getString("dl_composito_tipo_oggetto");
    }

    public void setDlCompositoTipoOggetto(String dlCompositoTipoOggetto) {
        setObject("dl_composito_tipo_oggetto", dlCompositoTipoOggetto);
    }

    public String getDlPathIdTipoOggetto() {
        return getString("dl_path_id_tipo_oggetto");
    }

    public void setDlPathIdTipoOggetto(String dlPathIdTipoOggetto) {
        setObject("dl_path_id_tipo_oggetto", dlPathIdTipoOggetto);
    }

    @Override
    public void entityToRowBean(Object obj) {
        AplVTreeTipoOgg entity = (AplVTreeTipoOgg) obj;
        this.setIdApplic(entity.getIdApplic());
        this.setNmApplic(entity.getNmApplic());
        this.setIdTipoOggetto(entity.getIdTipoOggetto());
        this.setNmTipoOggetto(entity.getNmTipoOggetto());
        this.setIdTipoOggettoPadre(entity.getIdTipoOggettoPadre());
        this.setDlCompositoTipoOggetto(entity.getDlCompositoTipoOggetto());
        this.setDlPathIdTipoOggetto(entity.getDlPathIdTipoOggetto());
    }

    @Override
    public AplVTreeTipoOgg rowBeanToEntity() {
        AplVTreeTipoOgg entity = new AplVTreeTipoOgg();
        entity.setIdApplic(this.getIdApplic());
        entity.setNmApplic(this.getNmApplic());
        entity.setIdTipoOggetto(this.getIdTipoOggetto());
        entity.setNmTipoOggetto(this.getNmTipoOggetto());
        entity.setIdTipoOggettoPadre(this.getIdTipoOggettoPadre());
        entity.setDlCompositoTipoOggetto(this.getDlCompositoTipoOggetto());
        entity.setDlPathIdTipoOggetto(this.getDlPathIdTipoOggetto());
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
