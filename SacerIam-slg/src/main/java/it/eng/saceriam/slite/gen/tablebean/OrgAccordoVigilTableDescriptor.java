/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna <p/> This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version. <p/> This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Affero General Public License for more details. <p/> You should
 * have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.slite.gen.tablebean;

import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;

import it.eng.spagoLite.db.oracle.bean.column.ColumnDescriptor;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * @author Sloth
 *
 *         Bean per la tabella Org_Accordo_Vigil
 *
 */
public class OrgAccordoVigilTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter",
     * comments = "This class was generated by OraTool", date = "Thursday, 18 March 2021 17:38" )
     */

    public static final String SELECT = "Select * from Org_Accordo_Vigil /**/";
    public static final String TABLE_NAME = "Org_Accordo_Vigil";
    public static final String COL_ID_ACCORDO_VIGIL = "id_accordo_vigil";
    public static final String COL_ID_ENTE_ORGANO_VIGIL = "id_ente_organo_vigil";
    public static final String COL_ID_ENTE_CONSERV = "id_ente_conserv";
    public static final String COL_DT_INI_VAL = "dt_ini_val";
    public static final String COL_DT_FIN_VAL = "dt_fin_val";
    public static final String COL_DS_NOTE_ENTE_ACCORDO = "ds_note_ente_accordo";
    public static final String COL_CD_REGISTRO_REPERTORIO = "cd_registro_repertorio";
    public static final String COL_AA_REPERTORIO = "aa_repertorio";
    public static final String COL_CD_KEY_REPERTORIO = "cd_key_repertorio";
    public static final String COL_DS_FIRMATARIO_ENTE = "ds_firmatario_ente";
    public static final String COL_DT_REG_ACCORDO = "dt_reg_accordo";
    public static final String COL_NM_ENTE = "nm_ente";
    public static final String COL_NM_STRUT = "nm_strut";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
	map.put(COL_ID_ACCORDO_VIGIL,
		new ColumnDescriptor(COL_ID_ACCORDO_VIGIL, Types.DECIMAL, 22, true));
	map.put(COL_ID_ENTE_ORGANO_VIGIL,
		new ColumnDescriptor(COL_ID_ENTE_ORGANO_VIGIL, Types.DECIMAL, 22, false));
	map.put(COL_ID_ENTE_CONSERV,
		new ColumnDescriptor(COL_ID_ENTE_CONSERV, Types.DECIMAL, 22, false));
	map.put(COL_DT_INI_VAL, new ColumnDescriptor(COL_DT_INI_VAL, Types.TIMESTAMP, 7, false));
	map.put(COL_DT_FIN_VAL, new ColumnDescriptor(COL_DT_FIN_VAL, Types.TIMESTAMP, 7, false));
	map.put(COL_DS_NOTE_ENTE_ACCORDO,
		new ColumnDescriptor(COL_DS_NOTE_ENTE_ACCORDO, Types.VARCHAR, 254, false));
	map.put(COL_CD_REGISTRO_REPERTORIO,
		new ColumnDescriptor(COL_CD_REGISTRO_REPERTORIO, Types.VARCHAR, 100, false));
	map.put(COL_AA_REPERTORIO,
		new ColumnDescriptor(COL_AA_REPERTORIO, Types.DECIMAL, 22, false));
	map.put(COL_CD_KEY_REPERTORIO,
		new ColumnDescriptor(COL_CD_KEY_REPERTORIO, Types.VARCHAR, 100, false));
	map.put(COL_DS_FIRMATARIO_ENTE,
		new ColumnDescriptor(COL_DS_FIRMATARIO_ENTE, Types.VARCHAR, 254, false));
	map.put(COL_DT_REG_ACCORDO,
		new ColumnDescriptor(COL_DT_REG_ACCORDO, Types.TIMESTAMP, 7, false));
	map.put(COL_NM_ENTE, new ColumnDescriptor(COL_NM_ENTE, Types.VARCHAR, 100, false));
	map.put(COL_NM_STRUT, new ColumnDescriptor(COL_NM_STRUT, Types.VARCHAR, 100, false));
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
