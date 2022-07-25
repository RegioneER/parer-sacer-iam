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
 *         Bean per la tabella Ntf_Notifica
 *
 */
public class NtfNotificaTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Monday, 12 June 2017 17:13" )
     */

    public static final String SELECT = "Select * from Ntf_Notifica /**/";
    public static final String TABLE_NAME = "Ntf_Notifica";
    public static final String COL_ID_NOTIFICA = "id_notifica";
    public static final String COL_ID_MODELLO_COMUNIC = "id_modello_comunic";
    public static final String COL_DS_EMAIL_DESTINATARIO = "ds_email_destinatario";
    public static final String COL_TS_NOTIF = "ts_notif";
    public static final String COL_NM_MITTENTE = "nm_mittente";
    public static final String COL_DS_OGGETTO = "ds_oggetto";
    public static final String COL_DS_TESTO_NOTIFICA = "ds_testo_notifica";
    public static final String COL_DS_DESTINATARIO = "ds_destinatario";
    public static final String COL_TI_STATO_NOTIF = "ti_stato_notif";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
        map.put(COL_ID_NOTIFICA, new ColumnDescriptor(COL_ID_NOTIFICA, Types.DECIMAL, 22, true));
        map.put(COL_ID_MODELLO_COMUNIC, new ColumnDescriptor(COL_ID_MODELLO_COMUNIC, Types.DECIMAL, 22, false));
        map.put(COL_DS_EMAIL_DESTINATARIO, new ColumnDescriptor(COL_DS_EMAIL_DESTINATARIO, Types.VARCHAR, 254, false));
        map.put(COL_TS_NOTIF, new ColumnDescriptor(COL_TS_NOTIF, Types.TIMESTAMP, 11, false));
        map.put(COL_NM_MITTENTE, new ColumnDescriptor(COL_NM_MITTENTE, Types.VARCHAR, 100, false));
        map.put(COL_DS_OGGETTO, new ColumnDescriptor(COL_DS_OGGETTO, Types.VARCHAR, 254, false));
        map.put(COL_DS_TESTO_NOTIFICA, new ColumnDescriptor(COL_DS_TESTO_NOTIFICA, Types.VARCHAR, 4000, false));
        map.put(COL_DS_DESTINATARIO, new ColumnDescriptor(COL_DS_DESTINATARIO, Types.VARCHAR, 100, false));
        map.put(COL_TI_STATO_NOTIF, new ColumnDescriptor(COL_TI_STATO_NOTIF, Types.VARCHAR, 30, false));
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
