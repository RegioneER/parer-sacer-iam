package it.eng.saceriam.slite.gen.viewbean;

/**
 * ViewBean per la vista Usr_V_Lis_Ini_Sched_Job
 *
 */
import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.util.Iterator;

/**
 * TableBean per la tabella Usr_V_Lis_Ini_Sched_Job
 *
 */
public class UsrVLisIniSchedJobTableBean extends AbstractBaseTable<UsrVLisIniSchedJobRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Thursday, 9 June 2016 11:48" )
     */

    public static UsrVLisIniSchedJobTableDescriptor TABLE_DESCRIPTOR = new UsrVLisIniSchedJobTableDescriptor();

    public UsrVLisIniSchedJobTableBean() {
        super();
    }

    protected UsrVLisIniSchedJobRowBean createRow() {
        return new UsrVLisIniSchedJobRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<UsrVLisIniSchedJobRowBean> getRowsIterator() {
        return iterator();
    }
}
