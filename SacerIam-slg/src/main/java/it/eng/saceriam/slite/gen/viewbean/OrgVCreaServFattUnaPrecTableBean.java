package it.eng.saceriam.slite.gen.viewbean;

/**
 * ViewBean per la vista Org_V_Crea_Serv_Fatt_Una_Prec
 *
 */
import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.util.Iterator;

/**
 * TableBean per la tabella Org_V_Crea_Serv_Fatt_Una_Prec
 *
 */
public class OrgVCreaServFattUnaPrecTableBean extends AbstractBaseTable<OrgVCreaServFattUnaPrecRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Wednesday, 21 October 2020 18:51" )
     */

    public static OrgVCreaServFattUnaPrecTableDescriptor TABLE_DESCRIPTOR = new OrgVCreaServFattUnaPrecTableDescriptor();

    public OrgVCreaServFattUnaPrecTableBean() {
        super();
    }

    protected OrgVCreaServFattUnaPrecRowBean createRow() {
        return new OrgVCreaServFattUnaPrecRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<OrgVCreaServFattUnaPrecRowBean> getRowsIterator() {
        return iterator();
    }
}
