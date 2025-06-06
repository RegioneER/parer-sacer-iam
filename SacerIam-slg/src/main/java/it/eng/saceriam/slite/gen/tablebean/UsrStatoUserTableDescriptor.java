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
 *         Bean per la tabella Usr_Stato_User
 *
 */
public class UsrStatoUserTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter",
     * comments = "This class was generated by OraTool", date = "Wednesday, 14 June 2017 11:12" )
     */

    public static final String SELECT = "Select * from Usr_Stato_User /**/";
    public static final String TABLE_NAME = "Usr_Stato_User";
    public static final String COL_ID_STATO_USER = "id_stato_user";
    public static final String COL_ID_USER_IAM = "id_user_iam";
    public static final String COL_TS_STATO = "ts_stato";
    public static final String COL_TI_STATO_USER = "ti_stato_user";
    public static final String COL_ID_RICH_GEST_USER = "id_rich_gest_user";
    public static final String COL_ID_NOTIFICA = "id_notifica";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
	map.put(COL_ID_STATO_USER,
		new ColumnDescriptor(COL_ID_STATO_USER, Types.DECIMAL, 22, true));
	map.put(COL_ID_USER_IAM, new ColumnDescriptor(COL_ID_USER_IAM, Types.DECIMAL, 22, false));
	map.put(COL_TS_STATO, new ColumnDescriptor(COL_TS_STATO, Types.TIMESTAMP, 11, false));
	map.put(COL_TI_STATO_USER,
		new ColumnDescriptor(COL_TI_STATO_USER, Types.VARCHAR, 30, false));
	map.put(COL_ID_RICH_GEST_USER,
		new ColumnDescriptor(COL_ID_RICH_GEST_USER, Types.DECIMAL, 22, false));
	map.put(COL_ID_NOTIFICA, new ColumnDescriptor(COL_ID_NOTIFICA, Types.DECIMAL, 22, false));
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
