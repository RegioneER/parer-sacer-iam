package it.eng.saceriam.slite.gen.viewbean;

/**
 * ViewBean per la vista Usr_V_Allorgchild_By_Conserv
 *
 */
import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.util.Iterator;

/**
 * TableBean per la tabella Usr_V_Allorgchild_By_Conserv
 *
 */
public class UsrVAllorgchildByConservTableBean extends AbstractBaseTable<UsrVAllorgchildByConservRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Monday, 25 March 2019 11:48" )
     */

    public static UsrVAllorgchildByConservTableDescriptor TABLE_DESCRIPTOR = new UsrVAllorgchildByConservTableDescriptor();

    public UsrVAllorgchildByConservTableBean() {
        super();
    }

    protected UsrVAllorgchildByConservRowBean createRow() {
        return new UsrVAllorgchildByConservRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<UsrVAllorgchildByConservRowBean> getRowsIterator() {
        return iterator();
    }
}
