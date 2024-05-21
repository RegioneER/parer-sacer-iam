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
 *         Bean per la tabella Log_V_Ric_Accessi
 *
 */
public class LogVRicAccessiTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Thursday, 9 June 2022 14:41" )
     */

    public static final String SELECT = "Select * from Log_V_Ric_Accessi /**/";
    public static final String TABLE_NAME = "Log_V_Ric_Accessi";
    public static final String COL_ID_EVENTO = "id_evento";
    public static final String COL_NM_USERID = "nm_userid";
    public static final String COL_CD_IND_IP_CLIENT = "cd_ind_ip_client";
    public static final String COL_CD_IND_SERVER = "cd_ind_server";
    public static final String COL_DT_EVENTO = "dt_evento";
    public static final String COL_TIPO_EVENTO = "tipo_evento";
    public static final String COL_DS_EVENTO = "ds_evento";
    public static final String COL_NM_NOME_USER = "nm_nome_user";
    public static final String COL_NM_COGNOME_USER = "nm_cognome_user";
    public static final String COL_CD_FISC_USER = "cd_Fisc_user";
    public static final String COL_DS_EMAIL_USER = "ds_Email_user";
    public static final String COL_CD_ID_ESTERNO = "cd_id_esterno";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
        map.put(COL_ID_EVENTO, new ColumnDescriptor(COL_ID_EVENTO, Types.DECIMAL, 22, true));
        map.put(COL_NM_USERID, new ColumnDescriptor(COL_NM_USERID, Types.VARCHAR, 100, true));
        map.put(COL_CD_IND_IP_CLIENT, new ColumnDescriptor(COL_CD_IND_IP_CLIENT, Types.VARCHAR, 100, true));
        map.put(COL_CD_IND_SERVER, new ColumnDescriptor(COL_CD_IND_SERVER, Types.VARCHAR, 100, true));
        map.put(COL_DT_EVENTO, new ColumnDescriptor(COL_DT_EVENTO, Types.TIMESTAMP, 7, true));
        map.put(COL_TIPO_EVENTO, new ColumnDescriptor(COL_TIPO_EVENTO, Types.VARCHAR, 20, true));
        map.put(COL_DS_EVENTO, new ColumnDescriptor(COL_DS_EVENTO, Types.VARCHAR, 1000, true));
        map.put(COL_NM_NOME_USER, new ColumnDescriptor(COL_NM_NOME_USER, Types.VARCHAR, 100, true));
        map.put(COL_NM_COGNOME_USER, new ColumnDescriptor(COL_NM_COGNOME_USER, Types.VARCHAR, 100, true));
        map.put(COL_CD_FISC_USER, new ColumnDescriptor(COL_CD_FISC_USER, Types.VARCHAR, 16, true));
        map.put(COL_DS_EMAIL_USER, new ColumnDescriptor(COL_DS_EMAIL_USER, Types.VARCHAR, 254, true));
        map.put(COL_CD_ID_ESTERNO, new ColumnDescriptor(COL_CD_ID_ESTERNO, Types.VARCHAR, 100, true));
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
