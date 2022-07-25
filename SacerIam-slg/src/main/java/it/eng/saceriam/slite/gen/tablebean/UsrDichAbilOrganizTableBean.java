package it.eng.saceriam.slite.gen.tablebean;

import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.util.Iterator;

/**
 * TableBean per la tabella Usr_Dich_Abil_Organiz
 *
 */
public class UsrDichAbilOrganizTableBean extends AbstractBaseTable<UsrDichAbilOrganizRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Monday, 15 April 2019 15:50" )
     */

    public static UsrDichAbilOrganizTableDescriptor TABLE_DESCRIPTOR = new UsrDichAbilOrganizTableDescriptor();

    public UsrDichAbilOrganizTableBean() {
        super();
    }

    protected UsrDichAbilOrganizRowBean createRow() {
        return new UsrDichAbilOrganizRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<UsrDichAbilOrganizRowBean> getRowsIterator() {
        return iterator();
    }
}
