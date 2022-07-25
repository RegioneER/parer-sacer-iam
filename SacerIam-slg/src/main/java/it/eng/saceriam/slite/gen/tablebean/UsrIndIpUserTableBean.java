package it.eng.saceriam.slite.gen.tablebean;

import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.util.Iterator;

/**
 * TableBean per la tabella Usr_Ind_Ip_User
 *
 */
public class UsrIndIpUserTableBean extends AbstractBaseTable<UsrIndIpUserRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Wednesday, 19 March 2014 10:37" )
     */

    public static UsrIndIpUserTableDescriptor TABLE_DESCRIPTOR = new UsrIndIpUserTableDescriptor();

    public UsrIndIpUserTableBean() {
        super();
    }

    protected UsrIndIpUserRowBean createRow() {
        return new UsrIndIpUserRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<UsrIndIpUserRowBean> getRowsIterator() {
        return iterator();
    }
}
