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
 *         Bean per la tabella Usr_V_Unaorg_By_Produt
 *
 */
public class UsrVUnaorgByProdutTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Monday, 25 March 2019 11:55" )
     */

    public static final String SELECT = "Select * from Usr_V_Unaorg_By_Produt /**/";
    public static final String TABLE_NAME = "Usr_V_Unaorg_By_Produt";
    public static final String COL_ID_USER_IAM_COR = "id_user_iam_cor";
    public static final String COL_ID_APPLIC = "id_applic";
    public static final String COL_NM_APPLIC = "nm_applic";
    public static final String COL_ID_ENTE_PRODUT = "id_ente_produt";
    public static final String COL_ID_ORGANIZ_IAM_STRUT = "id_organiz_iam_strut";
    public static final String COL_ID_APPART_COLLEG_ENTI = "id_appart_colleg_enti";
    public static final String COL_ID_ENTE_PRODUT_COLLEG = "id_ente_produt_colleg";
    public static final String COL_DS_CAUSALE_DICH = "ds_causale_dich";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
        map.put(COL_ID_USER_IAM_COR, new ColumnDescriptor(COL_ID_USER_IAM_COR, Types.DECIMAL, 22, true));
        map.put(COL_ID_APPLIC, new ColumnDescriptor(COL_ID_APPLIC, Types.DECIMAL, 22, true));
        map.put(COL_NM_APPLIC, new ColumnDescriptor(COL_NM_APPLIC, Types.VARCHAR, 100, true));
        map.put(COL_ID_ENTE_PRODUT, new ColumnDescriptor(COL_ID_ENTE_PRODUT, Types.DECIMAL, 22, true));
        map.put(COL_ID_ORGANIZ_IAM_STRUT, new ColumnDescriptor(COL_ID_ORGANIZ_IAM_STRUT, Types.DECIMAL, 22, true));
        map.put(COL_ID_APPART_COLLEG_ENTI, new ColumnDescriptor(COL_ID_APPART_COLLEG_ENTI, Types.DECIMAL, 22, true));
        map.put(COL_ID_ENTE_PRODUT_COLLEG, new ColumnDescriptor(COL_ID_ENTE_PRODUT_COLLEG, Types.DECIMAL, 22, true));
        map.put(COL_DS_CAUSALE_DICH, new ColumnDescriptor(COL_DS_CAUSALE_DICH, Types.VARCHAR, 57, true));
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
