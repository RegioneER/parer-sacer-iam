package it.eng.saceriam.slite.gen.tablebean;

import it.eng.spagoLite.db.oracle.bean.column.ColumnDescriptor;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Generated;

/**
 * @author Sloth
 *
 *         Bean per la tabella Apl_Pagina_Web
 *
 */
public class AplPaginaWebTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Tuesday, 21 February 2017 14:57" )
     */

    public static final String SELECT = "Select * from Apl_Pagina_Web /**/";
    public static final String TABLE_NAME = "Apl_Pagina_Web";
    public static final String COL_ID_PAGINA_WEB = "id_pagina_web";
    public static final String COL_ID_APPLIC = "id_applic";
    public static final String COL_NM_PAGINA_WEB = "nm_pagina_web";
    public static final String COL_DS_PAGINA_WEB = "ds_pagina_web";
    public static final String COL_TI_HELP_ON_LINE = "ti_help_on_line";
    public static final String COL_ID_ENTRY_MENU = "id_entry_menu";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
        map.put(COL_ID_PAGINA_WEB, new ColumnDescriptor(COL_ID_PAGINA_WEB, Types.DECIMAL, 22, true));
        map.put(COL_ID_APPLIC, new ColumnDescriptor(COL_ID_APPLIC, Types.DECIMAL, 22, false));
        map.put(COL_NM_PAGINA_WEB, new ColumnDescriptor(COL_NM_PAGINA_WEB, Types.VARCHAR, 100, false));
        map.put(COL_DS_PAGINA_WEB, new ColumnDescriptor(COL_DS_PAGINA_WEB, Types.VARCHAR, 254, false));
        map.put(COL_TI_HELP_ON_LINE, new ColumnDescriptor(COL_TI_HELP_ON_LINE, Types.VARCHAR, 20, false));
        map.put(COL_ID_ENTRY_MENU, new ColumnDescriptor(COL_ID_ENTRY_MENU, Types.DECIMAL, 22, false));
    }

    public Map<String, ColumnDescriptor> getColumnMap() {
        return map;
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    public String getStatement() {
        return SELECT;
    }

}
