package it.eng.saceriam.slite.gen.tablebean;

import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.util.Iterator;

/**
 * TableBean per la tabella Org_Scaglione_Tariffa
 *
 */
public class OrgScaglioneTariffaTableBean extends AbstractBaseTable<OrgScaglioneTariffaRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Friday, 12 August 2016 16:44" )
     */

    public static OrgScaglioneTariffaTableDescriptor TABLE_DESCRIPTOR = new OrgScaglioneTariffaTableDescriptor();

    public OrgScaglioneTariffaTableBean() {
        super();
    }

    protected OrgScaglioneTariffaRowBean createRow() {
        return new OrgScaglioneTariffaRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<OrgScaglioneTariffaRowBean> getRowsIterator() {
        return iterator();
    }
}
