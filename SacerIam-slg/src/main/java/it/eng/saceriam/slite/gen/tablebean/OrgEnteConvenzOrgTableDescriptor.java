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
 *         Bean per la tabella Org_Ente_Convenz_Org
 *
 */
public class OrgEnteConvenzOrgTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Thursday, 16 June 2016 15:51" )
     */

    public static final String SELECT = "Select * from Org_Ente_Convenz_Org /**/";
    public static final String TABLE_NAME = "Org_Ente_Convenz_Org";
    public static final String COL_ID_ENTE_CONVENZ_ORG = "id_ente_convenz_org";
    public static final String COL_ID_ENTE_CONVENZ = "id_ente_convenz";
    public static final String COL_ID_ORGANIZ_IAM = "id_organiz_iam";
    public static final String COL_DT_INI_VAL = "dt_ini_val";
    public static final String COL_DT_FINE_VAL = "dt_fine_val";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
        map.put(COL_ID_ENTE_CONVENZ_ORG, new ColumnDescriptor(COL_ID_ENTE_CONVENZ_ORG, Types.DECIMAL, 22, true));
        map.put(COL_ID_ENTE_CONVENZ, new ColumnDescriptor(COL_ID_ENTE_CONVENZ, Types.DECIMAL, 22, false));
        map.put(COL_ID_ORGANIZ_IAM, new ColumnDescriptor(COL_ID_ORGANIZ_IAM, Types.DECIMAL, 22, false));
        map.put(COL_DT_INI_VAL, new ColumnDescriptor(COL_DT_INI_VAL, Types.TIMESTAMP, 7, false));
        map.put(COL_DT_FINE_VAL, new ColumnDescriptor(COL_DT_FINE_VAL, Types.TIMESTAMP, 7, false));
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
