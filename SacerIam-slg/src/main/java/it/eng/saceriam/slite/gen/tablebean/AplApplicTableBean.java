package it.eng.saceriam.slite.gen.tablebean;

import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.util.Iterator;

/**
 * TableBean per la tabella Apl_Applic
 *
 */
public class AplApplicTableBean extends AbstractBaseTable<AplApplicRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Wednesday, 18 October 2017 12:13" )
     */

    public static AplApplicTableDescriptor TABLE_DESCRIPTOR = new AplApplicTableDescriptor();

    public AplApplicTableBean() {
        super();
    }

    protected AplApplicRowBean createRow() {
        return new AplApplicRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<AplApplicRowBean> getRowsIterator() {
        return iterator();
    }
}
