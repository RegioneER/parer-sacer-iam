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
 *         Bean per la tabella Apl_Sistema_Versante_User_Ref
 *
 */
public class AplSistemaVersanteUserRefTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter",
     * comments = "This class was generated by OraTool", date = "Friday, 27 January 2017 15:37" )
     */

    public static final String SELECT = "Select * from Apl_Sistema_Versante_User_Ref /**/";
    public static final String TABLE_NAME = "Apl_Sistema_Versante_User_Ref";
    public static final String COL_ID_SISTEMA_VERSANTE_USER_REF = "id_sistema_versante_user_ref";
    public static final String COL_ID_SISTEMA_VERSANTE = "id_sistema_versante";
    public static final String COL_ID_USER_IAM_REF = "id_user_iam_ref";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
	map.put(COL_ID_SISTEMA_VERSANTE_USER_REF,
		new ColumnDescriptor(COL_ID_SISTEMA_VERSANTE_USER_REF, Types.DECIMAL, 22, true));
	map.put(COL_ID_SISTEMA_VERSANTE,
		new ColumnDescriptor(COL_ID_SISTEMA_VERSANTE, Types.DECIMAL, 22, false));
	map.put(COL_ID_USER_IAM_REF,
		new ColumnDescriptor(COL_ID_USER_IAM_REF, Types.DECIMAL, 22, false));
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
