package it.eng.saceriam.slite.gen.tablebean;

import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.entity.AplTipoOrganiz;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;
import java.math.BigDecimal;

/**
 * RowBean per la tabella Apl_Tipo_Organiz
 *
 */
public class AplTipoOrganizRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Wednesday, 19 March 2014 10:37" )
     */

    public static AplTipoOrganizTableDescriptor TABLE_DESCRIPTOR = new AplTipoOrganizTableDescriptor();

    public AplTipoOrganizRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    // getter e setter
    public BigDecimal getIdTipoOrganiz() {
        return getBigDecimal("id_tipo_organiz");
    }

    public void setIdTipoOrganiz(BigDecimal idTipoOrganiz) {
        setObject("id_tipo_organiz", idTipoOrganiz);
    }

    public BigDecimal getIdApplic() {
        return getBigDecimal("id_applic");
    }

    public void setIdApplic(BigDecimal idApplic) {
        setObject("id_applic", idApplic);
    }

    public String getNmTipoOrganiz() {
        return getString("nm_tipo_organiz");
    }

    public void setNmTipoOrganiz(String nmTipoOrganiz) {
        setObject("nm_tipo_organiz", nmTipoOrganiz);
    }

    public String getFlLastLivello() {
        return getString("fl_last_livello");
    }

    public void setFlLastLivello(String flLastLivello) {
        setObject("fl_last_livello", flLastLivello);
    }

    @Override
    public void entityToRowBean(Object obj) {
        AplTipoOrganiz entity = (AplTipoOrganiz) obj;
        this.setIdTipoOrganiz(new BigDecimal(entity.getIdTipoOrganiz()));
        if (entity.getAplApplic() != null) {
            this.setIdApplic(new BigDecimal(entity.getAplApplic().getIdApplic()));

        }
        this.setNmTipoOrganiz(entity.getNmTipoOrganiz());
        this.setFlLastLivello(entity.getFlLastLivello());
    }

    @Override
    public AplTipoOrganiz rowBeanToEntity() {
        AplTipoOrganiz entity = new AplTipoOrganiz();
        if (this.getIdTipoOrganiz() != null) {
            entity.setIdTipoOrganiz(this.getIdTipoOrganiz().longValue());
        }
        if (this.getIdApplic() != null) {
            if (entity.getAplApplic() == null) {
                entity.setAplApplic(new AplApplic());
            }
            entity.getAplApplic().setIdApplic(this.getIdApplic().longValue());
        }
        entity.setNmTipoOrganiz(this.getNmTipoOrganiz());
        entity.setFlLastLivello(this.getFlLastLivello());
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
