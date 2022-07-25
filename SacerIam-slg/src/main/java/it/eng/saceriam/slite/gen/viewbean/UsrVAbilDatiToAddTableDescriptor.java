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
 *         Bean per la tabella Usr_V_Abil_Dati_To_Add
 *
 */
public class UsrVAbilDatiToAddTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Friday, 10 February 2017 11:56" )
     */

    public static final String SELECT = "Select * from Usr_V_Abil_Dati_To_Add /**/";
    public static final String TABLE_NAME = "Usr_V_Abil_Dati_To_Add";
    public static final String COL_ID_USER_IAM = "id_user_iam";
    public static final String COL_ID_USO_USER_APPLIC = "id_uso_user_applic";
    public static final String COL_ID_APPLIC = "id_applic";
    public static final String COL_NM_APPLIC = "nm_applic";
    public static final String COL_ID_DICH_ABIL_DATI = "id_dich_abil_dati";
    public static final String COL_ID_TIPO_DATO_IAM = "id_tipo_dato_iam";
    public static final String COL_ID_CLASSE_TIPO_DATO = "id_classe_tipo_dato";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
        map.put(COL_ID_USER_IAM, new ColumnDescriptor(COL_ID_USER_IAM, Types.DECIMAL, 22, true));
        map.put(COL_ID_USO_USER_APPLIC, new ColumnDescriptor(COL_ID_USO_USER_APPLIC, Types.DECIMAL, 22, true));
        map.put(COL_ID_APPLIC, new ColumnDescriptor(COL_ID_APPLIC, Types.DECIMAL, 22, true));
        map.put(COL_NM_APPLIC, new ColumnDescriptor(COL_NM_APPLIC, Types.VARCHAR, 100, true));
        map.put(COL_ID_DICH_ABIL_DATI, new ColumnDescriptor(COL_ID_DICH_ABIL_DATI, Types.DECIMAL, 22, true));
        map.put(COL_ID_TIPO_DATO_IAM, new ColumnDescriptor(COL_ID_TIPO_DATO_IAM, Types.DECIMAL, 22, true));
        map.put(COL_ID_CLASSE_TIPO_DATO, new ColumnDescriptor(COL_ID_CLASSE_TIPO_DATO, Types.DECIMAL, 22, true));
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
