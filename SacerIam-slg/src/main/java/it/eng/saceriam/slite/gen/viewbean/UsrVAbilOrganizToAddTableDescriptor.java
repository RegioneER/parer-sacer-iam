/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna
 * <p/>
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
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
 *         Bean per la tabella Usr_V_Abil_Organiz_To_Add
 *
 */
public class UsrVAbilOrganizToAddTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Friday, 3 February 2017 15:19" )
     */

    public static final String SELECT = "Select * from Usr_V_Abil_Organiz_To_Add /**/";
    public static final String TABLE_NAME = "Usr_V_Abil_Organiz_To_Add";
    public static final String COL_ID_USER_IAM = "id_user_iam";
    public static final String COL_FL_ATTIVO = "fl_attivo";
    public static final String COL_ID_USO_USER_APPLIC = "id_uso_user_applic";
    public static final String COL_ID_APPLIC = "id_applic";
    public static final String COL_NM_APPLIC = "nm_applic";
    public static final String COL_ID_DICH_ABIL_ORGANIZ = "id_dich_abil_organiz";
    public static final String COL_ID_ORGANIZ_IAM = "id_organiz_iam";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
        map.put(COL_ID_USER_IAM, new ColumnDescriptor(COL_ID_USER_IAM, Types.DECIMAL, 22, true));
        map.put(COL_FL_ATTIVO, new ColumnDescriptor(COL_FL_ATTIVO, Types.VARCHAR, 1, true));
        map.put(COL_ID_USO_USER_APPLIC, new ColumnDescriptor(COL_ID_USO_USER_APPLIC, Types.DECIMAL, 22, true));
        map.put(COL_ID_APPLIC, new ColumnDescriptor(COL_ID_APPLIC, Types.DECIMAL, 22, true));
        map.put(COL_NM_APPLIC, new ColumnDescriptor(COL_NM_APPLIC, Types.VARCHAR, 100, true));
        map.put(COL_ID_DICH_ABIL_ORGANIZ, new ColumnDescriptor(COL_ID_DICH_ABIL_ORGANIZ, Types.DECIMAL, 22, true));
        map.put(COL_ID_ORGANIZ_IAM, new ColumnDescriptor(COL_ID_ORGANIZ_IAM, Types.DECIMAL, 22, true));
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
