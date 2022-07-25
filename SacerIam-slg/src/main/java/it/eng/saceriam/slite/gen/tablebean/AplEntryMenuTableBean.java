package it.eng.saceriam.slite.gen.tablebean;

import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.util.Iterator;

/**
 * TableBean per la tabella Apl_Entry_Menu
 *
 */
public class AplEntryMenuTableBean extends AbstractBaseTable<AplEntryMenuRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Tuesday, 21 February 2017 14:57" )
     */

    public static AplEntryMenuTableDescriptor TABLE_DESCRIPTOR = new AplEntryMenuTableDescriptor();

    public AplEntryMenuTableBean() {
        super();
    }

    protected AplEntryMenuRowBean createRow() {
        return new AplEntryMenuRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<AplEntryMenuRowBean> getRowsIterator() {
        return iterator();
    }
}
