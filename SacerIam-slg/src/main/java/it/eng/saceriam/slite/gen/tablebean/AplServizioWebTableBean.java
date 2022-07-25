package it.eng.saceriam.slite.gen.tablebean;

import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.util.Iterator;

/**
 * TableBean per la tabella Apl_Servizio_Web
 *
 */
public class AplServizioWebTableBean extends AbstractBaseTable<AplServizioWebRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Wednesday, 19 March 2014 10:37" )
     */

    public static AplServizioWebTableDescriptor TABLE_DESCRIPTOR = new AplServizioWebTableDescriptor();

    public AplServizioWebTableBean() {
        super();
    }

    protected AplServizioWebRowBean createRow() {
        return new AplServizioWebRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<AplServizioWebRowBean> getRowsIterator() {
        return iterator();
    }
}
