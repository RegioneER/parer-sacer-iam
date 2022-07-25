package it.eng.saceriam.slite.gen.viewbean;

/**
 * ViewBean per la vista Usr_V_Vis_Last_Rich_Gest_User
 *
 */
import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.util.Iterator;

/**
 * TableBean per la tabella Usr_V_Vis_Last_Rich_Gest_User
 *
 */
public class UsrVVisLastRichGestUserTableBean extends AbstractBaseTable<UsrVVisLastRichGestUserRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Friday, 20 October 2017 13:41" )
     */

    public static UsrVVisLastRichGestUserTableDescriptor TABLE_DESCRIPTOR = new UsrVVisLastRichGestUserTableDescriptor();

    public UsrVVisLastRichGestUserTableBean() {
        super();
    }

    protected UsrVVisLastRichGestUserRowBean createRow() {
        return new UsrVVisLastRichGestUserRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<UsrVVisLastRichGestUserRowBean> getRowsIterator() {
        return iterator();
    }
}
