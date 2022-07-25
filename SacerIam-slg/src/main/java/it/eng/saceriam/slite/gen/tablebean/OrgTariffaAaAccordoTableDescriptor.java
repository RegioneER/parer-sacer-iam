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
 *         Bean per la tabella Org_Tariffa_Aa_Accordo
 *
 */
public class OrgTariffaAaAccordoTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Monday, 7 September 2020 16:19" )
     */

    public static final String SELECT = "Select * from Org_Tariffa_Aa_Accordo /**/";
    public static final String TABLE_NAME = "Org_Tariffa_Aa_Accordo";
    public static final String COL_ID_TARIFFA_AA_ACCORDO = "id_tariffa_aa_accordo";
    public static final String COL_ID_AA_ACCORDO = "id_aa_accordo";
    public static final String COL_ID_TIPO_SERVIZIO = "id_tipo_servizio";
    public static final String COL_IM_TARIFFA_AA_ACCORDO = "im_tariffa_aa_accordo";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
        map.put(COL_ID_TARIFFA_AA_ACCORDO, new ColumnDescriptor(COL_ID_TARIFFA_AA_ACCORDO, Types.DECIMAL, 22, true));
        map.put(COL_ID_AA_ACCORDO, new ColumnDescriptor(COL_ID_AA_ACCORDO, Types.DECIMAL, 22, false));
        map.put(COL_ID_TIPO_SERVIZIO, new ColumnDescriptor(COL_ID_TIPO_SERVIZIO, Types.DECIMAL, 22, false));
        map.put(COL_IM_TARIFFA_AA_ACCORDO, new ColumnDescriptor(COL_IM_TARIFFA_AA_ACCORDO, Types.DECIMAL, 22, false));
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
