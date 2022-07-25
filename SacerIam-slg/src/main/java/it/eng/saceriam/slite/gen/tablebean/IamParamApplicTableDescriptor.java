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
 *         Bean per la tabella Iam_Param_Applic
 *
 */
public class IamParamApplicTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Friday, 14 December 2018 16:10" )
     */

    public static final String SELECT = "Select * from Iam_Param_Applic /**/";
    public static final String TABLE_NAME = "Iam_Param_Applic";
    public static final String COL_ID_PARAM_APPLIC = "id_param_applic";
    public static final String COL_NM_PARAM_APPLIC = "nm_param_applic";
    public static final String COL_DS_PARAM_APPLIC = "ds_param_applic";
    public static final String COL_DS_VALORE_PARAM_APPLIC = "ds_valore_param_applic";
    public static final String COL_TI_PARAM_APPLIC = "ti_param_applic";
    public static final String COL_TI_VALORE_PARAM_APPLIC = "ti_valore_param_applic";
    public static final String COL_TI_GESTIONE_PARAM = "ti_gestione_param";
    public static final String COL_FL_APPART_APPLIC = "fl_appart_applic";
    public static final String COL_FL_APPART_AMBIENTE = "fl_appart_ambiente";
    public static final String COL_FL_APPARTE_ENTE = "fl_apparte_ente";
    public static final String COL_DS_LISTA_VALORI_AMMESSI = "ds_lista_valori_ammessi";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
        map.put(COL_ID_PARAM_APPLIC, new ColumnDescriptor(COL_ID_PARAM_APPLIC, Types.DECIMAL, 22, true));
        map.put(COL_NM_PARAM_APPLIC, new ColumnDescriptor(COL_NM_PARAM_APPLIC, Types.VARCHAR, 100, false));
        map.put(COL_DS_PARAM_APPLIC, new ColumnDescriptor(COL_DS_PARAM_APPLIC, Types.VARCHAR, 254, false));
        map.put(COL_DS_VALORE_PARAM_APPLIC,
                new ColumnDescriptor(COL_DS_VALORE_PARAM_APPLIC, Types.VARCHAR, 254, false));
        map.put(COL_TI_PARAM_APPLIC, new ColumnDescriptor(COL_TI_PARAM_APPLIC, Types.VARCHAR, 40, false));
        map.put(COL_TI_VALORE_PARAM_APPLIC,
                new ColumnDescriptor(COL_TI_VALORE_PARAM_APPLIC, Types.VARCHAR, 100, false));
        map.put(COL_TI_GESTIONE_PARAM, new ColumnDescriptor(COL_TI_GESTIONE_PARAM, Types.VARCHAR, 30, false));
        map.put(COL_FL_APPART_APPLIC, new ColumnDescriptor(COL_FL_APPART_APPLIC, Types.VARCHAR, 1, false));
        map.put(COL_FL_APPART_AMBIENTE, new ColumnDescriptor(COL_FL_APPART_AMBIENTE, Types.VARCHAR, 1, false));
        map.put(COL_FL_APPARTE_ENTE, new ColumnDescriptor(COL_FL_APPARTE_ENTE, Types.VARCHAR, 1, false));
        map.put(COL_DS_LISTA_VALORI_AMMESSI,
                new ColumnDescriptor(COL_DS_LISTA_VALORI_AMMESSI, Types.VARCHAR, 4000, false));
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
