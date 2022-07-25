package it.eng.saceriam.slite.gen.viewbean;

/**
 * ViewBean per la vista Usr_V_Lis_Stato_User
 *
 */
import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.util.Iterator;

/**
 * TableBean per la tabella Usr_V_Lis_Stato_User
 *
 */
public class UsrVLisStatoUserTableBean extends AbstractBaseTable<UsrVLisStatoUserRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Tuesday, 19 September 2017 16:08" )
     */

    public static UsrVLisStatoUserTableDescriptor TABLE_DESCRIPTOR = new UsrVLisStatoUserTableDescriptor();

    public UsrVLisStatoUserTableBean() {
        super();
    }

    protected UsrVLisStatoUserRowBean createRow() {
        return new UsrVLisStatoUserRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<UsrVLisStatoUserRowBean> getRowsIterator() {
        return iterator();
    }
}
