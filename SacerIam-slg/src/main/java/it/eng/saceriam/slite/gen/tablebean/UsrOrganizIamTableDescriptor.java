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

package it.eng.saceriam.slite.gen.tablebean;

import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;

import it.eng.spagoLite.db.oracle.bean.column.ColumnDescriptor;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * @author Sloth
 *
 *         Bean per la tabella Usr_Organiz_Iam
 *
 */
public class UsrOrganizIamTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Friday, 5 April 2019 13:49" )
     */

    public static final String SELECT = "Select * from Usr_Organiz_Iam /**/";
    public static final String TABLE_NAME = "Usr_Organiz_Iam";
    public static final String COL_ID_ORGANIZ_IAM = "id_organiz_iam";
    public static final String COL_ID_APPLIC = "id_applic";
    public static final String COL_ID_ORGANIZ_APPLIC = "id_organiz_applic";
    public static final String COL_ID_TIPO_ORGANIZ = "id_tipo_organiz";
    public static final String COL_NM_ORGANIZ = "nm_organiz";
    public static final String COL_DS_ORGANIZ = "ds_organiz";
    public static final String COL_ID_ORGANIZ_IAM_PADRE = "id_organiz_iam_padre";
    public static final String COL_ID_ENTE_CONSERV = "id_ente_conserv";
    public static final String COL_ID_ENTE_GESTORE = "id_ente_gestore";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
        map.put(COL_ID_ORGANIZ_IAM, new ColumnDescriptor(COL_ID_ORGANIZ_IAM, Types.DECIMAL, 22, true));
        map.put(COL_ID_APPLIC, new ColumnDescriptor(COL_ID_APPLIC, Types.DECIMAL, 22, false));
        map.put(COL_ID_ORGANIZ_APPLIC, new ColumnDescriptor(COL_ID_ORGANIZ_APPLIC, Types.DECIMAL, 22, false));
        map.put(COL_ID_TIPO_ORGANIZ, new ColumnDescriptor(COL_ID_TIPO_ORGANIZ, Types.DECIMAL, 22, false));
        map.put(COL_NM_ORGANIZ, new ColumnDescriptor(COL_NM_ORGANIZ, Types.VARCHAR, 100, false));
        map.put(COL_DS_ORGANIZ, new ColumnDescriptor(COL_DS_ORGANIZ, Types.VARCHAR, 254, false));
        map.put(COL_ID_ORGANIZ_IAM_PADRE, new ColumnDescriptor(COL_ID_ORGANIZ_IAM_PADRE, Types.DECIMAL, 22, false));
        map.put(COL_ID_ENTE_CONSERV, new ColumnDescriptor(COL_ID_ENTE_CONSERV, Types.DECIMAL, 22, false));
        map.put(COL_ID_ENTE_GESTORE, new ColumnDescriptor(COL_ID_ENTE_GESTORE, Types.DECIMAL, 22, false));
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
