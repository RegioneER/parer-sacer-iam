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
 *         Bean per la tabella Usr_V_Lis_Abil_Ente
 *
 */
public class UsrVLisAbilEnteTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter",
     * comments = "This class was generated by OraTool", date = "Tuesday, 19 March 2019 11:49" )
     */

    public static final String SELECT = "Select * from Usr_V_Lis_Abil_Ente /**/";
    public static final String TABLE_NAME = "Usr_V_Lis_Abil_Ente";
    public static final String COL_ID_USER_IAM_GESTITO = "id_user_iam_gestito";
    public static final String COL_ID_ENTE_UTENTE = "id_ente_utente";
    public static final String COL_TI_SCOPO_ABIL = "ti_scopo_abil";
    public static final String COL_ID_AMBIENTE_ENTE_CONVENZ = "id_ambiente_ente_convenz";
    public static final String COL_ID_ENTE_SIAM = "id_ente_siam";
    public static final String COL_NM_COMPOSITO_ENTE = "nm_composito_ente";
    public static final String COL_DS_CAUSALE_ABIL = "ds_causale_abil";
    public static final String COL_FL_ABIL_AUTOMATICA = "fl_abil_automatica";
    public static final String COL_ID_APPART_COLLEG_ENTI = "id_appart_colleg_enti";
    public static final String COL_ID_SUPT_EST_ENTE_CONVENZ = "id_supt_est_ente_convenz";
    public static final String COL_ID_ACCORDO_VIGIL = "id_accordo_vigil";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
	map.put(COL_ID_USER_IAM_GESTITO,
		new ColumnDescriptor(COL_ID_USER_IAM_GESTITO, Types.DECIMAL, 22, true));
	map.put(COL_ID_ENTE_UTENTE,
		new ColumnDescriptor(COL_ID_ENTE_UTENTE, Types.DECIMAL, 22, true));
	map.put(COL_TI_SCOPO_ABIL,
		new ColumnDescriptor(COL_TI_SCOPO_ABIL, Types.VARCHAR, 11, true));
	map.put(COL_ID_AMBIENTE_ENTE_CONVENZ,
		new ColumnDescriptor(COL_ID_AMBIENTE_ENTE_CONVENZ, Types.DECIMAL, 22, true));
	map.put(COL_ID_ENTE_SIAM, new ColumnDescriptor(COL_ID_ENTE_SIAM, Types.DECIMAL, 22, true));
	map.put(COL_NM_COMPOSITO_ENTE,
		new ColumnDescriptor(COL_NM_COMPOSITO_ENTE, Types.VARCHAR, 355, true));
	map.put(COL_DS_CAUSALE_ABIL,
		new ColumnDescriptor(COL_DS_CAUSALE_ABIL, Types.VARCHAR, 254, true));
	map.put(COL_FL_ABIL_AUTOMATICA,
		new ColumnDescriptor(COL_FL_ABIL_AUTOMATICA, Types.VARCHAR, 1, true));
	map.put(COL_ID_APPART_COLLEG_ENTI,
		new ColumnDescriptor(COL_ID_APPART_COLLEG_ENTI, Types.DECIMAL, 22, true));
	map.put(COL_ID_SUPT_EST_ENTE_CONVENZ,
		new ColumnDescriptor(COL_ID_SUPT_EST_ENTE_CONVENZ, Types.DECIMAL, 22, true));
	map.put(COL_ID_ACCORDO_VIGIL,
		new ColumnDescriptor(COL_ID_ACCORDO_VIGIL, Types.DECIMAL, 22, true));
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
