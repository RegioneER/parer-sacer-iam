package it.eng.saceriam.slite.gen.tablebean;

import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.util.Iterator;

/**
 * TableBean per la tabella Prf_Uso_Ruolo_Applic
 *
 */
public class PrfUsoRuoloApplicTableBean extends AbstractBaseTable<PrfUsoRuoloApplicRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Wednesday, 19 March 2014 10:37" )
     */

    public static PrfUsoRuoloApplicTableDescriptor TABLE_DESCRIPTOR = new PrfUsoRuoloApplicTableDescriptor();

    public PrfUsoRuoloApplicTableBean() {
        super();
    }

    protected PrfUsoRuoloApplicRowBean createRow() {
        return new PrfUsoRuoloApplicRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<PrfUsoRuoloApplicRowBean> getRowsIterator() {
        return iterator();
    }
}
