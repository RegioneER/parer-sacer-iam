package it.eng.saceriam.slite.gen.viewbean;

import it.eng.spagoLite.db.oracle.bean.column.ColumnDescriptor;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Sloth
 *
 *         Bean per la tabella Usr_V_Abil_Organiz
 *
 */
public class UsrVAbilOrganizTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Tuesday, 20 October 2015 11:31" )
     */

    public static final String SELECT = "Select * from Usr_V_Abil_Organiz /**/";
    public static final String TABLE_NAME = "Usr_V_Abil_Organiz";
    public static final String COL_ID_USER_IAM = "id_user_iam";
    public static final String COL_FL_ATTIVO = "fl_attivo";
    public static final String COL_ID_USO_USER_APPLIC = "id_uso_user_applic";
    public static final String COL_ID_APPLIC = "id_applic";
    public static final String COL_NM_APPLIC = "nm_applic";
    public static final String COL_ID_DICH_ABIL_ORGANIZ = "id_dich_abil_organiz";
    public static final String COL_ID_ORGANIZ_IAM = "id_organiz_iam";
    public static final String COL_ID_ORGANIZ_APPLIC = "id_organiz_applic";
    public static final String COL_ID_TIPO_ORGANIZ = "id_tipo_organiz";
    public static final String COL_NM_TIPO_ORGANIZ = "nm_tipo_organiz";
    public static final String COL_NM_ORGANIZ = "nm_organiz";
    public static final String COL_DS_ORGANIZ = "ds_organiz";
    public static final String COL_ID_ORGANIZ_IAM_PADRE = "id_organiz_iam_padre";
    public static final String COL_DL_COMPOSITO_ORGANIZ = "dl_composito_organiz";
    public static final String COL_DL_PATH_ID_ORGANIZ_IAM = "dl_path_id_organiz_iam";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
        map.put(COL_ID_USER_IAM, new ColumnDescriptor(COL_ID_USER_IAM, Types.DECIMAL, 22, true));
        map.put(COL_FL_ATTIVO, new ColumnDescriptor(COL_FL_ATTIVO, Types.VARCHAR, 1, true));
        map.put(COL_ID_USO_USER_APPLIC, new ColumnDescriptor(COL_ID_USO_USER_APPLIC, Types.DECIMAL, 22, true));
        map.put(COL_ID_APPLIC, new ColumnDescriptor(COL_ID_APPLIC, Types.DECIMAL, 22, true));
        map.put(COL_NM_APPLIC, new ColumnDescriptor(COL_NM_APPLIC, Types.VARCHAR, 100, true));
        map.put(COL_ID_DICH_ABIL_ORGANIZ, new ColumnDescriptor(COL_ID_DICH_ABIL_ORGANIZ, Types.DECIMAL, 22, true));
        map.put(COL_ID_ORGANIZ_IAM, new ColumnDescriptor(COL_ID_ORGANIZ_IAM, Types.DECIMAL, 22, true));
        map.put(COL_ID_ORGANIZ_APPLIC, new ColumnDescriptor(COL_ID_ORGANIZ_APPLIC, Types.DECIMAL, 22, true));
        map.put(COL_ID_TIPO_ORGANIZ, new ColumnDescriptor(COL_ID_TIPO_ORGANIZ, Types.DECIMAL, 22, true));
        map.put(COL_NM_TIPO_ORGANIZ, new ColumnDescriptor(COL_NM_TIPO_ORGANIZ, Types.VARCHAR, 100, true));
        map.put(COL_NM_ORGANIZ, new ColumnDescriptor(COL_NM_ORGANIZ, Types.VARCHAR, 100, true));
        map.put(COL_DS_ORGANIZ, new ColumnDescriptor(COL_DS_ORGANIZ, Types.VARCHAR, 254, true));
        map.put(COL_ID_ORGANIZ_IAM_PADRE, new ColumnDescriptor(COL_ID_ORGANIZ_IAM_PADRE, Types.DECIMAL, 22, true));
        map.put(COL_DL_COMPOSITO_ORGANIZ, new ColumnDescriptor(COL_DL_COMPOSITO_ORGANIZ, Types.VARCHAR, 4000, true));
        map.put(COL_DL_PATH_ID_ORGANIZ_IAM,
                new ColumnDescriptor(COL_DL_PATH_ID_ORGANIZ_IAM, Types.VARCHAR, 4000, true));
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
