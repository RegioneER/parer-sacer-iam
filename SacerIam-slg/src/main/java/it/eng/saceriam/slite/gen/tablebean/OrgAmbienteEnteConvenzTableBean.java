package it.eng.saceriam.slite.gen.tablebean;

import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.util.Iterator;

/**
 * TableBean per la tabella Org_Ambiente_Ente_Convenz
 *
 */
public class OrgAmbienteEnteConvenzTableBean extends AbstractBaseTable<OrgAmbienteEnteConvenzRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Wednesday, 15 February 2017 11:06" )
     */

    public static OrgAmbienteEnteConvenzTableDescriptor TABLE_DESCRIPTOR = new OrgAmbienteEnteConvenzTableDescriptor();

    public OrgAmbienteEnteConvenzTableBean() {
        super();
    }

    protected OrgAmbienteEnteConvenzRowBean createRow() {
        return new OrgAmbienteEnteConvenzRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<OrgAmbienteEnteConvenzRowBean> getRowsIterator() {
        return iterator();
    }
}
