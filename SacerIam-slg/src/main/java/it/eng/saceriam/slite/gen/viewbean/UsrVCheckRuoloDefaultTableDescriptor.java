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
 *         Bean per la tabella Usr_V_Check_Ruolo_Default
 *
 */
public class UsrVCheckRuoloDefaultTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Thursday, 3 October 2013 15:05" )
     */

    public static final String SELECT = "Select * from Usr_V_Check_Ruolo_Default /**/";
    public static final String TABLE_NAME = "Usr_V_Check_Ruolo_Default";
    public static final String COL_ID_USER_CORRENTE = "id_user_corrente";
    public static final String COL_ID_RUOLO_AGGIUNTO = "id_ruolo_aggiunto";
    public static final String COL_ID_APPLIC_SCELTA = "id_applic_scelta";
    public static final String COL_TI_DICH_AUTOR = "ti_dich_autor";
    public static final String COL_ID_AUTOR_AGGIUNTA = "id_autor_aggiunta";
    public static final String COL_NM_AUTOR_AGGIUNTA = "nm_autor_aggiunta";
    public static final String COL_DS_AUTOR_AGGIUNTA = "ds_autor_aggiunta";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
        map.put(COL_ID_USER_CORRENTE, new ColumnDescriptor(COL_ID_USER_CORRENTE, Types.DECIMAL, 22, true));
        map.put(COL_ID_RUOLO_AGGIUNTO, new ColumnDescriptor(COL_ID_RUOLO_AGGIUNTO, Types.DECIMAL, 22, true));
        map.put(COL_ID_APPLIC_SCELTA, new ColumnDescriptor(COL_ID_APPLIC_SCELTA, Types.DECIMAL, 22, true));
        map.put(COL_TI_DICH_AUTOR, new ColumnDescriptor(COL_TI_DICH_AUTOR, Types.VARCHAR, 20, true));
        map.put(COL_ID_AUTOR_AGGIUNTA, new ColumnDescriptor(COL_ID_AUTOR_AGGIUNTA, Types.DECIMAL, 22, true));
        map.put(COL_NM_AUTOR_AGGIUNTA, new ColumnDescriptor(COL_NM_AUTOR_AGGIUNTA, Types.VARCHAR, 205, true));
        map.put(COL_DS_AUTOR_AGGIUNTA, new ColumnDescriptor(COL_DS_AUTOR_AGGIUNTA, Types.VARCHAR, 513, true));
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
