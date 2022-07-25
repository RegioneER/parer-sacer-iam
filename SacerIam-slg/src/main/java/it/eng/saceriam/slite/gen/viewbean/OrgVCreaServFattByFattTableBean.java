package it.eng.saceriam.slite.gen.viewbean;

/**
 * ViewBean per la vista Org_V_Crea_Serv_Fatt_By_Fatt
 *
 */
import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.util.Iterator;

/**
 * TableBean per la tabella Org_V_Crea_Serv_Fatt_By_Fatt
 *
 */
public class OrgVCreaServFattByFattTableBean extends AbstractBaseTable<OrgVCreaServFattByFattRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Tuesday, 8 May 2018 12:09" )
     */

    public static OrgVCreaServFattByFattTableDescriptor TABLE_DESCRIPTOR = new OrgVCreaServFattByFattTableDescriptor();

    public OrgVCreaServFattByFattTableBean() {
        super();
    }

    protected OrgVCreaServFattByFattRowBean createRow() {
        return new OrgVCreaServFattByFattRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<OrgVCreaServFattByFattRowBean> getRowsIterator() {
        return iterator();
    }
}
