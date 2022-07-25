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
 *         Bean per la tabella Apl_Tipo_Evento_Oggetto_Trig
 *
 */
public class AplTipoEventoOggettoTrigTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Thursday, 9 June 2016 11:14" )
     */

    public static final String SELECT = "Select * from Apl_Tipo_Evento_Oggetto_Trig /**/";
    public static final String TABLE_NAME = "Apl_Tipo_Evento_Oggetto_Trig";
    public static final String COL_ID_TIPO_EVENTO_OGGETTO_TRIG = "id_tipo_evento_oggetto_trig";
    public static final String COL_ID_TIPO_EVENTO_OGGETTO = "id_tipo_evento_oggetto";
    public static final String COL_ID_TIPO_EVENTO_TRIG = "id_tipo_evento_trig";
    public static final String COL_ID_TIPO_OGGETTO_TRIG = "id_tipo_oggetto_trig";
    public static final String COL_ID_QUERY_TIPO_OGGETTO_TRIG = "id_query_tipo_oggetto_trig";
    public static final String COL_ID_QUERY_TIPO_OGGETTO_SEL = "id_query_tipo_oggetto_sel";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
        map.put(COL_ID_TIPO_EVENTO_OGGETTO_TRIG,
                new ColumnDescriptor(COL_ID_TIPO_EVENTO_OGGETTO_TRIG, Types.DECIMAL, 22, true));
        map.put(COL_ID_TIPO_EVENTO_OGGETTO, new ColumnDescriptor(COL_ID_TIPO_EVENTO_OGGETTO, Types.DECIMAL, 22, false));
        map.put(COL_ID_TIPO_EVENTO_TRIG, new ColumnDescriptor(COL_ID_TIPO_EVENTO_TRIG, Types.DECIMAL, 22, false));
        map.put(COL_ID_TIPO_OGGETTO_TRIG, new ColumnDescriptor(COL_ID_TIPO_OGGETTO_TRIG, Types.DECIMAL, 22, false));
        map.put(COL_ID_QUERY_TIPO_OGGETTO_TRIG,
                new ColumnDescriptor(COL_ID_QUERY_TIPO_OGGETTO_TRIG, Types.DECIMAL, 22, false));
        map.put(COL_ID_QUERY_TIPO_OGGETTO_SEL,
                new ColumnDescriptor(COL_ID_QUERY_TIPO_OGGETTO_SEL, Types.DECIMAL, 22, false));
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
