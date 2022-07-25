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
 *         Bean per la tabella Org_V_Serv_Sist_Vers_Da_Erog
 *
 */
public class OrgVServSistVersDaErogTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Thursday, 9 June 2016 11:47" )
     */

    public static final String SELECT = "Select * from Org_V_Serv_Sist_Vers_Da_Erog /**/";
    public static final String TABLE_NAME = "Org_V_Serv_Sist_Vers_Da_Erog";
    public static final String COL_ID_ENTE_CONVENZ = "id_ente_convenz";
    public static final String COL_NM_ENTE_CONVENZ = "nm_ente_convenz";
    public static final String COL_ID_ACCORDO_ENTE = "id_accordo_ente";
    public static final String COL_DT_REG_ACCORDO = "dt_reg_accordo";
    public static final String COL_ID_SERVIZIO_EROGATO = "id_servizio_erogato";
    public static final String COL_NM_SERVIZIO_EROGATO = "nm_servizio_erogato";
    public static final String COL_DT_EROG = "dt_erog";
    public static final String COL_ID_SISTEMA_VERSANTE = "id_sistema_versante";
    public static final String COL_NM_SISTEMA_VERSANTE = "nm_sistema_versante";
    public static final String COL_LIST_STRUT = "list_strut";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
        map.put(COL_ID_ENTE_CONVENZ, new ColumnDescriptor(COL_ID_ENTE_CONVENZ, Types.DECIMAL, 22, true));
        map.put(COL_NM_ENTE_CONVENZ, new ColumnDescriptor(COL_NM_ENTE_CONVENZ, Types.VARCHAR, 100, true));
        map.put(COL_ID_ACCORDO_ENTE, new ColumnDescriptor(COL_ID_ACCORDO_ENTE, Types.DECIMAL, 22, true));
        map.put(COL_DT_REG_ACCORDO, new ColumnDescriptor(COL_DT_REG_ACCORDO, Types.TIMESTAMP, 7, true));
        map.put(COL_ID_SERVIZIO_EROGATO, new ColumnDescriptor(COL_ID_SERVIZIO_EROGATO, Types.DECIMAL, 22, true));
        map.put(COL_NM_SERVIZIO_EROGATO, new ColumnDescriptor(COL_NM_SERVIZIO_EROGATO, Types.VARCHAR, 100, true));
        map.put(COL_DT_EROG, new ColumnDescriptor(COL_DT_EROG, Types.TIMESTAMP, 7, true));
        map.put(COL_ID_SISTEMA_VERSANTE, new ColumnDescriptor(COL_ID_SISTEMA_VERSANTE, Types.DECIMAL, 22, true));
        map.put(COL_NM_SISTEMA_VERSANTE, new ColumnDescriptor(COL_NM_SISTEMA_VERSANTE, Types.VARCHAR, 100, true));
        map.put(COL_LIST_STRUT, new ColumnDescriptor(COL_LIST_STRUT, Types.VARCHAR, 4000, true));
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
