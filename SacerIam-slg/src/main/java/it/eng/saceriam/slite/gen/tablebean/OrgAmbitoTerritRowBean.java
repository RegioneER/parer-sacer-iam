package it.eng.saceriam.slite.gen.tablebean;

import it.eng.saceriam.entity.OrgAmbitoTerrit;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;
import java.math.BigDecimal;

/**
 * RowBean per la tabella Org_Ambito_Territ
 *
 */
public class OrgAmbitoTerritRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Tuesday, 11 March 2014 18:25" )
     */

    public static OrgAmbitoTerritTableDescriptor TABLE_DESCRIPTOR = new OrgAmbitoTerritTableDescriptor();

    public OrgAmbitoTerritRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    // getter e setter
    public BigDecimal getIdAmbitoTerrit() {
        return getBigDecimal("id_ambito_territ");
    }

    public void setIdAmbitoTerrit(BigDecimal idAmbitoTerrit) {
        setObject("id_ambito_territ", idAmbitoTerrit);
    }

    public String getCdAmbitoTerrit() {
        return getString("cd_ambito_territ");
    }

    public void setCdAmbitoTerrit(String cdAmbitoTerrit) {
        setObject("cd_ambito_territ", cdAmbitoTerrit);
    }

    public String getTiAmbitoTerrit() {
        return getString("ti_ambito_territ");
    }

    public void setTiAmbitoTerrit(String tiAmbitoTerrit) {
        setObject("ti_ambito_territ", tiAmbitoTerrit);
    }

    public BigDecimal getIdAmbitoTerritPadre() {
        return getBigDecimal("id_ambito_territ_padre");
    }

    public void setIdAmbitoTerritPadre(BigDecimal idAmbitoTerritPadre) {
        setObject("id_ambito_territ_padre", idAmbitoTerritPadre);
    }

    @Override
    public void entityToRowBean(Object obj) {
        OrgAmbitoTerrit entity = (OrgAmbitoTerrit) obj;
        this.setIdAmbitoTerrit(new BigDecimal(entity.getIdAmbitoTerrit()));
        this.setCdAmbitoTerrit(entity.getCdAmbitoTerrit());
        this.setTiAmbitoTerrit(entity.getTiAmbitoTerrit());
        if (entity.getOrgAmbitoTerrit() != null) {
            this.setIdAmbitoTerritPadre(new BigDecimal(entity.getOrgAmbitoTerrit().getIdAmbitoTerrit()));

        }
    }

    @Override
    public OrgAmbitoTerrit rowBeanToEntity() {
        OrgAmbitoTerrit entity = new OrgAmbitoTerrit();
        if (this.getIdAmbitoTerrit() != null) {
            entity.setIdAmbitoTerrit(this.getIdAmbitoTerrit().longValue());
        }
        entity.setCdAmbitoTerrit(this.getCdAmbitoTerrit());
        entity.setTiAmbitoTerrit(this.getTiAmbitoTerrit());
        if (this.getIdAmbitoTerritPadre() != null) {
            if (entity.getOrgAmbitoTerrit() == null) {
                entity.setOrgAmbitoTerrit(new OrgAmbitoTerrit());
            }
            entity.getOrgAmbitoTerrit().setIdAmbitoTerrit(this.getIdAmbitoTerritPadre().longValue());
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
