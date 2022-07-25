package it.eng.saceriam.slite.gen.viewbean;

/**
 * ViewBean per la vista Usr_V_Abil_Ente_To_Add
 *
 */
import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.util.Iterator;

/**
 * TableBean per la tabella Usr_V_Abil_Ente_To_Add
 *
 */
public class UsrVAbilEnteToAddTableBean extends AbstractBaseTable<UsrVAbilEnteToAddRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Thursday, 16 February 2017 16:58" )
     */

    public static UsrVAbilEnteToAddTableDescriptor TABLE_DESCRIPTOR = new UsrVAbilEnteToAddTableDescriptor();

    public UsrVAbilEnteToAddTableBean() {
        super();
    }

    protected UsrVAbilEnteToAddRowBean createRow() {
        return new UsrVAbilEnteToAddRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<UsrVAbilEnteToAddRowBean> getRowsIterator() {
        return iterator();
    }
}
