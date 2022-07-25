package it.eng.saceriam.slite.gen.viewbean;

/**
 * ViewBean per la vista Usr_V_Vis_Dich_Abil
 *
 */
import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.util.Iterator;

/**
 * TableBean per la tabella Usr_V_Vis_Dich_Abil
 *
 */
public class UsrVVisDichAbilTableBean extends AbstractBaseTable<UsrVVisDichAbilRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Monday, 25 November 2013 16:36" )
     */

    public static UsrVVisDichAbilTableDescriptor TABLE_DESCRIPTOR = new UsrVVisDichAbilTableDescriptor();

    public UsrVVisDichAbilTableBean() {
        super();
    }

    protected UsrVVisDichAbilRowBean createRow() {
        return new UsrVVisDichAbilRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<UsrVVisDichAbilRowBean> getRowsIterator() {
        return iterator();
    }
}
