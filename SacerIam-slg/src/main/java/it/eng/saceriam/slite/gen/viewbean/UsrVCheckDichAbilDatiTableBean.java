package it.eng.saceriam.slite.gen.viewbean;

/**
 * ViewBean per la vista Usr_V_Check_Dich_Abil_Dati
 *
 */
import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.util.Iterator;

/**
 * TableBean per la tabella Usr_V_Check_Dich_Abil_Dati
 *
 */
public class UsrVCheckDichAbilDatiTableBean extends AbstractBaseTable<UsrVCheckDichAbilDatiRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Friday, 22 November 2013 16:49" )
     */

    public static UsrVCheckDichAbilDatiTableDescriptor TABLE_DESCRIPTOR = new UsrVCheckDichAbilDatiTableDescriptor();

    public UsrVCheckDichAbilDatiTableBean() {
        super();
    }

    protected UsrVCheckDichAbilDatiRowBean createRow() {
        return new UsrVCheckDichAbilDatiRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<UsrVCheckDichAbilDatiRowBean> getRowsIterator() {
        return iterator();
    }
}
