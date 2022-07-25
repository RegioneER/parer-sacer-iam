package it.eng.saceriam.slite.gen.tablebean;

import it.eng.saceriam.entity.OrgScaglioneTariffa;
import it.eng.saceriam.entity.OrgTariffa;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;
import java.math.BigDecimal;

/**
 * RowBean per la tabella Org_Scaglione_Tariffa
 *
 */
public class OrgScaglioneTariffaRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Friday, 12 August 2016 16:44" )
     */

    public static OrgScaglioneTariffaTableDescriptor TABLE_DESCRIPTOR = new OrgScaglioneTariffaTableDescriptor();

    public OrgScaglioneTariffaRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    // getter e setter
    public BigDecimal getIdScaglioneTariffa() {
        return getBigDecimal("id_scaglione_tariffa");
    }

    public void setIdScaglioneTariffa(BigDecimal idScaglioneTariffa) {
        setObject("id_scaglione_tariffa", idScaglioneTariffa);
    }

    public BigDecimal getIdTariffa() {
        return getBigDecimal("id_tariffa");
    }

    public void setIdTariffa(BigDecimal idTariffa) {
        setObject("id_tariffa", idTariffa);
    }

    public BigDecimal getNiIniScaglione() {
        return getBigDecimal("ni_ini_scaglione");
    }

    public void setNiIniScaglione(BigDecimal niIniScaglione) {
        setObject("ni_ini_scaglione", niIniScaglione);
    }

    public BigDecimal getNiFineScaglione() {
        return getBigDecimal("ni_fine_scaglione");
    }

    public void setNiFineScaglione(BigDecimal niFineScaglione) {
        setObject("ni_fine_scaglione", niFineScaglione);
    }

    public BigDecimal getImScaglione() {
        return getBigDecimal("im_scaglione");
    }

    public void setImScaglione(BigDecimal imScaglione) {
        setObject("im_scaglione", imScaglione);
    }

    @Override
    public void entityToRowBean(Object obj) {
        OrgScaglioneTariffa entity = (OrgScaglioneTariffa) obj;
        this.setIdScaglioneTariffa(new BigDecimal(entity.getIdScaglioneTariffa()));
        if (entity.getOrgTariffa() != null) {
            this.setIdTariffa(new BigDecimal(entity.getOrgTariffa().getIdTariffa()));

        }
        this.setNiIniScaglione(entity.getNiIniScaglione());
        this.setNiFineScaglione(entity.getNiFineScaglione());
        this.setImScaglione(entity.getImScaglione());
    }

    @Override
    public OrgScaglioneTariffa rowBeanToEntity() {
        OrgScaglioneTariffa entity = new OrgScaglioneTariffa();
        if (this.getIdScaglioneTariffa() != null) {
            entity.setIdScaglioneTariffa(this.getIdScaglioneTariffa().longValue());
        }
        if (this.getIdTariffa() != null) {
            if (entity.getOrgTariffa() == null) {
                entity.setOrgTariffa(new OrgTariffa());
            }
            entity.getOrgTariffa().setIdTariffa(this.getIdTariffa().longValue());
        }
        entity.setNiIniScaglione(this.getNiIniScaglione());
        entity.setNiFineScaglione(this.getNiFineScaglione());
        entity.setImScaglione(this.getImScaglione());
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
