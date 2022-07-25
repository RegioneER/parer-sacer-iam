package it.eng.saceriam.slite.gen.viewbean;

/**
 * ViewBean per la vista Org_V_Ente_Convenz_By_Organiz
 *
 */
import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.util.Iterator;

/**
 * TableBean per la tabella Org_V_Ente_Convenz_By_Organiz
 *
 */
public class OrgVEnteConvenzByOrganizTableBean extends AbstractBaseTable<OrgVEnteConvenzByOrganizRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Wednesday, 15 May 2019 11:56" )
     */

    public static OrgVEnteConvenzByOrganizTableDescriptor TABLE_DESCRIPTOR = new OrgVEnteConvenzByOrganizTableDescriptor();

    public OrgVEnteConvenzByOrganizTableBean() {
        super();
    }

    protected OrgVEnteConvenzByOrganizRowBean createRow() {
        return new OrgVEnteConvenzByOrganizRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<OrgVEnteConvenzByOrganizRowBean> getRowsIterator() {
        return iterator();
    }
}
