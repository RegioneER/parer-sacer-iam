package it.eng.saceriam.slite.gen.tablebean;

import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.util.Iterator;

/**
 * TableBean per la tabella Org_Ente_Siam
 *
 */
public class OrgEnteSiamTableBean extends AbstractBaseTable<OrgEnteSiamRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Friday, 19 January 2018 15:06" )
     */

    public static OrgEnteSiamTableDescriptor TABLE_DESCRIPTOR = new OrgEnteSiamTableDescriptor();

    public OrgEnteSiamTableBean() {
        super();
    }

    protected OrgEnteSiamRowBean createRow() {
        return new OrgEnteSiamRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<OrgEnteSiamRowBean> getRowsIterator() {
        return iterator();
    }
}
