package it.eng.saceriam.slite.gen.viewbean;

/**
 * ViewBean per la vista Apl_V_Tree_Tipo_Ogg
 *
 */
import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.util.Iterator;

/**
 * TableBean per la tabella Apl_V_Tree_Tipo_Ogg
 *
 */
public class AplVTreeTipoOggTableBean extends AbstractBaseTable<AplVTreeTipoOggRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Thursday, 9 June 2016 11:47" )
     */

    public static AplVTreeTipoOggTableDescriptor TABLE_DESCRIPTOR = new AplVTreeTipoOggTableDescriptor();

    public AplVTreeTipoOggTableBean() {
        super();
    }

    protected AplVTreeTipoOggRowBean createRow() {
        return new AplVTreeTipoOggRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<AplVTreeTipoOggRowBean> getRowsIterator() {
        return iterator();
    }
}
