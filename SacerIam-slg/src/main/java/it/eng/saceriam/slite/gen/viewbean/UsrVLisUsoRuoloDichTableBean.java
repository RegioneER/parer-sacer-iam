package it.eng.saceriam.slite.gen.viewbean;

/**
 * ViewBean per la vista Usr_V_Lis_Uso_Ruolo_Dich
 *
 */
import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.util.Iterator;

/**
 * TableBean per la tabella Usr_V_Lis_Uso_Ruolo_Dich
 *
 */
public class UsrVLisUsoRuoloDichTableBean extends AbstractBaseTable<UsrVLisUsoRuoloDichRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Wednesday, 18 April 2018 16:23" )
     */

    public static UsrVLisUsoRuoloDichTableDescriptor TABLE_DESCRIPTOR = new UsrVLisUsoRuoloDichTableDescriptor();

    public UsrVLisUsoRuoloDichTableBean() {
        super();
    }

    protected UsrVLisUsoRuoloDichRowBean createRow() {
        return new UsrVLisUsoRuoloDichRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<UsrVLisUsoRuoloDichRowBean> getRowsIterator() {
        return iterator();
    }
}
