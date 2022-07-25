package it.eng.saceriam.slite.gen.tablebean;

import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.util.Iterator;

/**
 * TableBean per la tabella Org_Servizio_Erog
 *
 */
public class OrgServizioErogTableBean extends AbstractBaseTable<OrgServizioErogRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Wednesday, 9 May 2018 10:37" )
     */

    public static OrgServizioErogTableDescriptor TABLE_DESCRIPTOR = new OrgServizioErogTableDescriptor();

    public OrgServizioErogTableBean() {
        super();
    }

    protected OrgServizioErogRowBean createRow() {
        return new OrgServizioErogRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<OrgServizioErogRowBean> getRowsIterator() {
        return iterator();
    }
}
