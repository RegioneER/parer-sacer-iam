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
 *         Bean per la tabella Apl_Entry_Menu
 *
 */
public class AplEntryMenuTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Tuesday, 21 February 2017 14:57" )
     */

    public static final String SELECT = "Select * from Apl_Entry_Menu /**/";
    public static final String TABLE_NAME = "Apl_Entry_Menu";
    public static final String COL_ID_ENTRY_MENU = "id_entry_menu";
    public static final String COL_ID_APPLIC = "id_applic";
    public static final String COL_NM_ENTRY_MENU = "nm_entry_menu";
    public static final String COL_DS_ENTRY_MENU = "ds_entry_menu";
    public static final String COL_NI_LIVELLO_ENTRY_MENU = "ni_livello_entry_menu";
    public static final String COL_NI_ORD_ENTRY_MENU = "ni_ord_entry_menu";
    public static final String COL_ID_ENTRY_MENU_PADRE = "id_entry_menu_padre";
    public static final String COL_DL_LINK_ENTRY_MENU = "dl_link_entry_menu";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
        map.put(COL_ID_ENTRY_MENU, new ColumnDescriptor(COL_ID_ENTRY_MENU, Types.DECIMAL, 22, true));
        map.put(COL_ID_APPLIC, new ColumnDescriptor(COL_ID_APPLIC, Types.DECIMAL, 22, false));
        map.put(COL_NM_ENTRY_MENU, new ColumnDescriptor(COL_NM_ENTRY_MENU, Types.VARCHAR, 100, false));
        map.put(COL_DS_ENTRY_MENU, new ColumnDescriptor(COL_DS_ENTRY_MENU, Types.VARCHAR, 254, false));
        map.put(COL_NI_LIVELLO_ENTRY_MENU, new ColumnDescriptor(COL_NI_LIVELLO_ENTRY_MENU, Types.DECIMAL, 22, false));
        map.put(COL_NI_ORD_ENTRY_MENU, new ColumnDescriptor(COL_NI_ORD_ENTRY_MENU, Types.DECIMAL, 22, false));
        map.put(COL_ID_ENTRY_MENU_PADRE, new ColumnDescriptor(COL_ID_ENTRY_MENU_PADRE, Types.DECIMAL, 22, false));
        map.put(COL_DL_LINK_ENTRY_MENU, new ColumnDescriptor(COL_DL_LINK_ENTRY_MENU, Types.VARCHAR, 2000, false));
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
