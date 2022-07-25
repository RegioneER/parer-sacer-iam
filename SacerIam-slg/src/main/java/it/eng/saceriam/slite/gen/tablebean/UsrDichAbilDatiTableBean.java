package it.eng.saceriam.slite.gen.tablebean;

import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.util.Iterator;

/**
 * TableBean per la tabella Usr_Dich_Abil_Dati
 *
 */
public class UsrDichAbilDatiTableBean extends AbstractBaseTable<UsrDichAbilDatiRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Monday, 15 April 2019 15:50" )
     */

    public static UsrDichAbilDatiTableDescriptor TABLE_DESCRIPTOR = new UsrDichAbilDatiTableDescriptor();

    public UsrDichAbilDatiTableBean() {
        super();
    }

    protected UsrDichAbilDatiRowBean createRow() {
        return new UsrDichAbilDatiRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<UsrDichAbilDatiRowBean> getRowsIterator() {
        return iterator();
    }
}
