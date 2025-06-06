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

package it.eng.saceriam.slite.gen.viewbean;

import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;

import it.eng.spagoLite.db.oracle.bean.column.ColumnDescriptor;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * @author Sloth
 *
 *         Bean per la tabella Org_V_Ente_Convenz_By_Organiz
 *
 */
public class OrgVEnteConvenzByOrganizTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter",
     * comments = "This class was generated by OraTool", date = "Wednesday, 15 May 2019 11:56" )
     */

    public static final String SELECT = "Select * from Org_V_Ente_Convenz_By_Organiz /**/";
    public static final String TABLE_NAME = "Org_V_Ente_Convenz_By_Organiz";
    public static final String COL_ID_APPLIC = "id_applic";
    public static final String COL_NM_APPLIC = "nm_applic";
    public static final String COL_ID_TIPO_ORGANIZ = "id_tipo_organiz";
    public static final String COL_NM_TIPO_ORGANIZ = "nm_tipo_organiz";
    public static final String COL_ID_ORGANIZ_IAM = "id_organiz_iam";
    public static final String COL_ID_ORGANIZ_APPLIC = "id_organiz_applic";
    public static final String COL_NM_ORGANIZ = "nm_organiz";
    public static final String COL_ID_ENTE_CONVENZ_ORG = "id_ente_convenz_org";
    public static final String COL_DT_INI_VAL = "dt_ini_val";
    public static final String COL_DT_FINE_VAL = "dt_fine_val";
    public static final String COL_ID_ENTE_CONVENZ = "id_ente_convenz";
    public static final String COL_NM_ENTE_CONVENZ = "nm_ente_convenz";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
	map.put(COL_ID_APPLIC, new ColumnDescriptor(COL_ID_APPLIC, Types.DECIMAL, 22, true));
	map.put(COL_NM_APPLIC, new ColumnDescriptor(COL_NM_APPLIC, Types.VARCHAR, 100, true));
	map.put(COL_ID_TIPO_ORGANIZ,
		new ColumnDescriptor(COL_ID_TIPO_ORGANIZ, Types.DECIMAL, 22, true));
	map.put(COL_NM_TIPO_ORGANIZ,
		new ColumnDescriptor(COL_NM_TIPO_ORGANIZ, Types.VARCHAR, 100, true));
	map.put(COL_ID_ORGANIZ_IAM,
		new ColumnDescriptor(COL_ID_ORGANIZ_IAM, Types.DECIMAL, 22, true));
	map.put(COL_ID_ORGANIZ_APPLIC,
		new ColumnDescriptor(COL_ID_ORGANIZ_APPLIC, Types.DECIMAL, 22, true));
	map.put(COL_NM_ORGANIZ, new ColumnDescriptor(COL_NM_ORGANIZ, Types.VARCHAR, 100, true));
	map.put(COL_ID_ENTE_CONVENZ_ORG,
		new ColumnDescriptor(COL_ID_ENTE_CONVENZ_ORG, Types.DECIMAL, 22, true));
	map.put(COL_DT_INI_VAL, new ColumnDescriptor(COL_DT_INI_VAL, Types.TIMESTAMP, 7, true));
	map.put(COL_DT_FINE_VAL, new ColumnDescriptor(COL_DT_FINE_VAL, Types.TIMESTAMP, 7, true));
	map.put(COL_ID_ENTE_CONVENZ,
		new ColumnDescriptor(COL_ID_ENTE_CONVENZ, Types.DECIMAL, 22, true));
	map.put(COL_NM_ENTE_CONVENZ,
		new ColumnDescriptor(COL_NM_ENTE_CONVENZ, Types.VARCHAR, 254, true));
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
