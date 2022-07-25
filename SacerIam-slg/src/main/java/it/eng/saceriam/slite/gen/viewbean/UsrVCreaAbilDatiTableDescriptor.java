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
 *         Bean per la tabella Usr_V_Crea_Abil_Dati
 *
 */
public class UsrVCreaAbilDatiTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Tuesday, 16 April 2019 14:11" )
     */

    public static final String SELECT = "Select * from Usr_V_Crea_Abil_Dati /**/";
    public static final String TABLE_NAME = "Usr_V_Crea_Abil_Dati";
    public static final String COL_NM_USERID = "nm_userid";
    public static final String COL_ID_USO_USER_APPLIC = "id_uso_user_applic";
    public static final String COL_NM_APPLIC = "nm_applic";
    public static final String COL_ID_CLASSE_TIPO_DATO = "id_classe_tipo_dato";
    public static final String COL_NM_CLASSE_TIPO_DATO = "nm_classe_tipo_dato";
    public static final String COL_TI_SCOPO_DICH_ABIL_DATI = "ti_scopo_dich_abil_dati";
    public static final String COL_ID_ORGANIZ_IAM = "id_organiz_iam";
    public static final String COL_ID_APPART_COLLEG_ENTI = "id_appart_colleg_enti";
    public static final String COL_ID_SUPT_EST_ENTE_CONVENZ = "id_supt_est_ente_convenz";
    public static final String COL_ID_VIGIL_ENTE_PRODUT = "id_vigil_ente_produt";
    public static final String COL_DS_CAUSALE_DICH = "ds_causale_dich";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
        map.put(COL_NM_USERID, new ColumnDescriptor(COL_NM_USERID, Types.VARCHAR, 100, true));
        map.put(COL_ID_USO_USER_APPLIC, new ColumnDescriptor(COL_ID_USO_USER_APPLIC, Types.DECIMAL, 22, true));
        map.put(COL_NM_APPLIC, new ColumnDescriptor(COL_NM_APPLIC, Types.VARCHAR, 100, true));
        map.put(COL_ID_CLASSE_TIPO_DATO, new ColumnDescriptor(COL_ID_CLASSE_TIPO_DATO, Types.DECIMAL, 22, true));
        map.put(COL_NM_CLASSE_TIPO_DATO, new ColumnDescriptor(COL_NM_CLASSE_TIPO_DATO, Types.VARCHAR, 100, true));
        map.put(COL_TI_SCOPO_DICH_ABIL_DATI,
                new ColumnDescriptor(COL_TI_SCOPO_DICH_ABIL_DATI, Types.VARCHAR, 13, true));
        map.put(COL_ID_ORGANIZ_IAM, new ColumnDescriptor(COL_ID_ORGANIZ_IAM, Types.DECIMAL, 22, true));
        map.put(COL_ID_APPART_COLLEG_ENTI, new ColumnDescriptor(COL_ID_APPART_COLLEG_ENTI, Types.DECIMAL, 22, true));
        map.put(COL_ID_SUPT_EST_ENTE_CONVENZ,
                new ColumnDescriptor(COL_ID_SUPT_EST_ENTE_CONVENZ, Types.DECIMAL, 22, true));
        map.put(COL_ID_VIGIL_ENTE_PRODUT, new ColumnDescriptor(COL_ID_VIGIL_ENTE_PRODUT, Types.DECIMAL, 22, true));
        map.put(COL_DS_CAUSALE_DICH, new ColumnDescriptor(COL_DS_CAUSALE_DICH, Types.VARCHAR, 254, true));
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
