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
 *         Bean per la tabella Dec_Modello_Comunic
 *
 */
public class DecModelloComunicTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Monday, 12 June 2017 17:13" )
     */

    public static final String SELECT = "Select * from Dec_Modello_Comunic /**/";
    public static final String TABLE_NAME = "Dec_Modello_Comunic";
    public static final String COL_ID_MODELLO_COMUNIC = "id_modello_comunic";
    public static final String COL_CD_MODELLO_COMUNIC = "cd_modello_comunic";
    public static final String COL_DS_MODELLO_COMUNIC = "ds_modello_comunic";
    public static final String COL_TI_COMUNIC = "ti_comunic";
    public static final String COL_TI_OGGETTO_QUERY = "ti_oggetto_query";
    public static final String COL_DT_ISTITUZ = "dt_istituz";
    public static final String COL_DT_SOPPRES = "dt_soppres";
    public static final String COL_TI_STATO_TRIG_COMUNIC = "ti_stato_trig_comunic";
    public static final String COL_NM_MITTENTE = "nm_mittente";
    public static final String COL_DS_OGGETTO_COMUNIC = "ds_oggetto_comunic";
    public static final String COL_BL_TESTO_COMUNIC = "bl_testo_comunic";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
        map.put(COL_ID_MODELLO_COMUNIC, new ColumnDescriptor(COL_ID_MODELLO_COMUNIC, Types.DECIMAL, 22, true));
        map.put(COL_CD_MODELLO_COMUNIC, new ColumnDescriptor(COL_CD_MODELLO_COMUNIC, Types.VARCHAR, 100, false));
        map.put(COL_DS_MODELLO_COMUNIC, new ColumnDescriptor(COL_DS_MODELLO_COMUNIC, Types.VARCHAR, 254, false));
        map.put(COL_TI_COMUNIC, new ColumnDescriptor(COL_TI_COMUNIC, Types.VARCHAR, 20, false));
        map.put(COL_TI_OGGETTO_QUERY, new ColumnDescriptor(COL_TI_OGGETTO_QUERY, Types.VARCHAR, 30, false));
        map.put(COL_DT_ISTITUZ, new ColumnDescriptor(COL_DT_ISTITUZ, Types.TIMESTAMP, 7, false));
        map.put(COL_DT_SOPPRES, new ColumnDescriptor(COL_DT_SOPPRES, Types.TIMESTAMP, 7, false));
        map.put(COL_TI_STATO_TRIG_COMUNIC, new ColumnDescriptor(COL_TI_STATO_TRIG_COMUNIC, Types.VARCHAR, 30, false));
        map.put(COL_NM_MITTENTE, new ColumnDescriptor(COL_NM_MITTENTE, Types.VARCHAR, 100, false));
        map.put(COL_DS_OGGETTO_COMUNIC, new ColumnDescriptor(COL_DS_OGGETTO_COMUNIC, Types.VARCHAR, 254, false));
        map.put(COL_BL_TESTO_COMUNIC, new ColumnDescriptor(COL_BL_TESTO_COMUNIC, Types.CLOB, 4000, false));
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
