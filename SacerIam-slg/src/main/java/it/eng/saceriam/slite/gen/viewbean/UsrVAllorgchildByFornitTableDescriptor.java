package it.eng.saceriam.slite.gen.viewbean;

import it.eng.spagoLite.db.oracle.bean.column.ColumnDescriptor;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Generated;

/**
 * @author Sloth
 *
 *         Bean per la tabella Usr_V_Allorgchild_By_Fornit
 *
 */
public class UsrVAllorgchildByFornitTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Monday, 25 March 2019 11:48" )
     */

    public static final String SELECT = "Select * from Usr_V_Allorgchild_By_Fornit /**/";
    public static final String TABLE_NAME = "Usr_V_Allorgchild_By_Fornit";
    public static final String COL_ID_USER_IAM_COR = "id_user_iam_cor";
    public static final String COL_ID_APPLIC = "id_applic";
    public static final String COL_NM_APPLIC = "nm_applic";
    public static final String COL_ID_ENTE_FORNIT_ESTERNO = "id_ente_fornit_esterno";
    public static final String COL_ID_ORGANIZ_IAM_ENTE = "id_organiz_iam_ente";
    public static final String COL_ID_ENTE_PRODUT_CORRISP = "id_ente_produt_corrisp";
    public static final String COL_ID_SUPT_EST_ENTE_CONVENZ = "id_supt_est_ente_convenz";
    public static final String COL_ID_ENTE_PRODUT_SUPT = "id_ente_produt_supt";
    public static final String COL_DS_CAUSALE_DICH = "ds_causale_dich";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
        map.put(COL_ID_USER_IAM_COR, new ColumnDescriptor(COL_ID_USER_IAM_COR, Types.DECIMAL, 22, true));
        map.put(COL_ID_APPLIC, new ColumnDescriptor(COL_ID_APPLIC, Types.DECIMAL, 22, true));
        map.put(COL_NM_APPLIC, new ColumnDescriptor(COL_NM_APPLIC, Types.VARCHAR, 100, true));
        map.put(COL_ID_ENTE_FORNIT_ESTERNO, new ColumnDescriptor(COL_ID_ENTE_FORNIT_ESTERNO, Types.DECIMAL, 22, true));
        map.put(COL_ID_ORGANIZ_IAM_ENTE, new ColumnDescriptor(COL_ID_ORGANIZ_IAM_ENTE, Types.DECIMAL, 22, true));
        map.put(COL_ID_ENTE_PRODUT_CORRISP, new ColumnDescriptor(COL_ID_ENTE_PRODUT_CORRISP, Types.DECIMAL, 22, true));
        map.put(COL_ID_SUPT_EST_ENTE_CONVENZ,
                new ColumnDescriptor(COL_ID_SUPT_EST_ENTE_CONVENZ, Types.DECIMAL, 22, true));
        map.put(COL_ID_ENTE_PRODUT_SUPT, new ColumnDescriptor(COL_ID_ENTE_PRODUT_SUPT, Types.DECIMAL, 22, true));
        map.put(COL_DS_CAUSALE_DICH, new ColumnDescriptor(COL_DS_CAUSALE_DICH, Types.VARCHAR, 47, true));
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
