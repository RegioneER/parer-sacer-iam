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
 *         Bean per la tabella Org_Scaglione_Tariffa
 *
 */
public class OrgScaglioneTariffaTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Friday, 12 August 2016 16:44" )
     */

    public static final String SELECT = "Select * from Org_Scaglione_Tariffa /**/";
    public static final String TABLE_NAME = "Org_Scaglione_Tariffa";
    public static final String COL_ID_SCAGLIONE_TARIFFA = "id_scaglione_tariffa";
    public static final String COL_ID_TARIFFA = "id_tariffa";
    public static final String COL_NI_INI_SCAGLIONE = "ni_ini_scaglione";
    public static final String COL_NI_FINE_SCAGLIONE = "ni_fine_scaglione";
    public static final String COL_IM_SCAGLIONE = "im_scaglione";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
        map.put(COL_ID_SCAGLIONE_TARIFFA, new ColumnDescriptor(COL_ID_SCAGLIONE_TARIFFA, Types.DECIMAL, 22, true));
        map.put(COL_ID_TARIFFA, new ColumnDescriptor(COL_ID_TARIFFA, Types.DECIMAL, 22, false));
        map.put(COL_NI_INI_SCAGLIONE, new ColumnDescriptor(COL_NI_INI_SCAGLIONE, Types.DECIMAL, 22, false));
        map.put(COL_NI_FINE_SCAGLIONE, new ColumnDescriptor(COL_NI_FINE_SCAGLIONE, Types.DECIMAL, 22, false));
        map.put(COL_IM_SCAGLIONE, new ColumnDescriptor(COL_IM_SCAGLIONE, Types.DECIMAL, 22, false));
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
