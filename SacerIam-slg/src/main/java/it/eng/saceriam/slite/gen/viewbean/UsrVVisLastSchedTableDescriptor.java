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
 *         Bean per la tabella Usr_V_Vis_Last_Sched
 *
 */
public class UsrVVisLastSchedTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Tuesday, 3 December 2013 10:43" )
     */

    public static final String SELECT = "Select * from Usr_V_Vis_Last_Sched /**/";
    public static final String TABLE_NAME = "Usr_V_Vis_Last_Sched";
    public static final String COL_ID_LOG_JOB = "id_log_job";
    public static final String COL_NM_JOB = "nm_job";
    public static final String COL_DT_REG_LOG_JOB_INI = "dt_reg_log_job_ini";
    public static final String COL_FL_JOB_ATTIVO = "fl_job_attivo";
    public static final String COL_FL_ERR = "fl_err";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
        map.put(COL_ID_LOG_JOB, new ColumnDescriptor(COL_ID_LOG_JOB, Types.DECIMAL, 22, true));
        map.put(COL_NM_JOB, new ColumnDescriptor(COL_NM_JOB, Types.VARCHAR, 100, true));
        map.put(COL_DT_REG_LOG_JOB_INI, new ColumnDescriptor(COL_DT_REG_LOG_JOB_INI, Types.TIMESTAMP, 7, true));
        map.put(COL_FL_JOB_ATTIVO, new ColumnDescriptor(COL_FL_JOB_ATTIVO, Types.VARCHAR, 1, true));
        map.put(COL_FL_ERR, new ColumnDescriptor(COL_FL_ERR, Types.VARCHAR, 1, true));
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
