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
 *         Bean per la tabella Org_V_Ric_Ente_Convenz
 *
 */
public class OrgVRicEnteConvenzTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter",
     * comments = "This class was generated by OraTool", date = "Monday, 15 October 2018 11:00" )
     */

    public static final String SELECT = "Select * from ORG_V_RIC_ENTE_CONVENZ /**/";
    public static final String TABLE_NAME = "ORG_V_RIC_ENTE_CONVENZ";
    public static final String COL_ID_ENTE_CONVENZ = "id_ente_convenz";
    public static final String COL_NM_ENTE_CONVENZ = "nm_ente_convenz";
    public static final String COL_ID_CATEG_ENTE = "id_categ_ente";
    public static final String COL_ID_AMBITO_TERRIT = "id_ambito_territ";
    public static final String COL_DT_INI_VAL = "dt_ini_val";
    public static final String COL_DT_CESSAZIONE = "dt_cessazione";
    public static final String COL_ID_TIPO_ACCORDO = "id_tipo_accordo";
    public static final String COL_CD_TIPO_ACCORDO = "cd_tipo_accordo";
    public static final String COL_DT_RICH_MODULO_INFO = "dt_rich_modulo_info";
    public static final String COL_FL_ESISTONO_MODULI = "fl_esistono_moduli";
    public static final String COL_FL_RECESSO = "fl_recesso";
    public static final String COL_FL_CHIUSO = "fl_chiuso";
    public static final String COL_ENTE_ATTIVO = "ente_attivo";
    public static final String COL_ARCHIVISTA = "archivista";
    public static final String COL_ID_USER_IAM_ARK = "id_user_iam_ark";
    public static final String COL_ID_USER_IAM_COR = "id_user_iam_cor";
    public static final String COL_ID_AMBIENTE_ENTE_CONVENZ = "id_ambiente_ente_convenz";
    public static final String COL_ID_ENTE_CONSERV = "id_ente_conserv";
    public static final String COL_ID_ENTE_GESTORE = "id_ente_gestore";
    public static final String COL_DT_DEC_ACCORDO = "dt_dec_accordo";
    public static final String COL_DT_FINE_VALID_ACCORDO = "dt_fine_valid_accordo";
    public static final String COL_DT_SCAD_ACCORDO = "dt_scad_accordo";
    public static final String COL_FL_NON_CONVENZ = "fl_non_convenz";
    public static final String COL_FL_ESISTONO_GEST_ACC = "fl_esistono_gest_acc";
    public static final String COL_TI_ENTE_CONVENZ = "ti_ente_convenz";
    public static final String COL_TIPO_GESTIONE_ACCORDO = "tipo_gestione_accordo";
    public static final String COL_FL_GEST_ACC_NO_RISP = "fl_gest_acc_no_risp";
    public static final String COL_TI_STATO_ACCORDO = "ti_stato_accordo";
    public static final String COL_CD_ENTE_CONVENZ = "cd_ente_convenz";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
	map.put(COL_ID_ENTE_CONVENZ,
		new ColumnDescriptor(COL_ID_ENTE_CONVENZ, Types.DECIMAL, 22, true));
	map.put(COL_NM_ENTE_CONVENZ,
		new ColumnDescriptor(COL_NM_ENTE_CONVENZ, Types.VARCHAR, 254, true));
	map.put(COL_ID_CATEG_ENTE,
		new ColumnDescriptor(COL_ID_CATEG_ENTE, Types.DECIMAL, 22, true));
	map.put(COL_ID_AMBITO_TERRIT,
		new ColumnDescriptor(COL_ID_AMBITO_TERRIT, Types.DECIMAL, 22, true));
	map.put(COL_DT_INI_VAL, new ColumnDescriptor(COL_DT_INI_VAL, Types.TIMESTAMP, 7, true));
	map.put(COL_DT_CESSAZIONE,
		new ColumnDescriptor(COL_DT_CESSAZIONE, Types.TIMESTAMP, 7, true));
	map.put(COL_ID_TIPO_ACCORDO,
		new ColumnDescriptor(COL_ID_TIPO_ACCORDO, Types.DECIMAL, 22, true));
	map.put(COL_CD_TIPO_ACCORDO,
		new ColumnDescriptor(COL_CD_TIPO_ACCORDO, Types.VARCHAR, 100, true));
	map.put(COL_DT_RICH_MODULO_INFO,
		new ColumnDescriptor(COL_DT_RICH_MODULO_INFO, Types.TIMESTAMP, 7, true));
	map.put(COL_FL_ESISTONO_MODULI,
		new ColumnDescriptor(COL_FL_ESISTONO_MODULI, Types.VARCHAR, 1, true));
	map.put(COL_FL_RECESSO, new ColumnDescriptor(COL_FL_RECESSO, Types.VARCHAR, 1, true));
	map.put(COL_FL_CHIUSO, new ColumnDescriptor(COL_FL_CHIUSO, Types.VARCHAR, 1, true));
	map.put(COL_ENTE_ATTIVO, new ColumnDescriptor(COL_ENTE_ATTIVO, Types.DECIMAL, 22, true));
	map.put(COL_ARCHIVISTA, new ColumnDescriptor(COL_ARCHIVISTA, Types.VARCHAR, 100, true));
	map.put(COL_ID_USER_IAM_ARK,
		new ColumnDescriptor(COL_ID_USER_IAM_ARK, Types.DECIMAL, 22, true));
	map.put(COL_ID_USER_IAM_COR,
		new ColumnDescriptor(COL_ID_USER_IAM_COR, Types.DECIMAL, 22, true));
	map.put(COL_ID_AMBIENTE_ENTE_CONVENZ,
		new ColumnDescriptor(COL_ID_AMBIENTE_ENTE_CONVENZ, Types.DECIMAL, 22, true));
	map.put(COL_ID_ENTE_GESTORE,
		new ColumnDescriptor(COL_ID_ENTE_GESTORE, Types.DECIMAL, 22, true));
	map.put(COL_ID_ENTE_CONSERV,
		new ColumnDescriptor(COL_ID_ENTE_CONSERV, Types.DECIMAL, 22, true));
	map.put(COL_DT_DEC_ACCORDO,
		new ColumnDescriptor(COL_DT_DEC_ACCORDO, Types.TIMESTAMP, 7, true));
	map.put(COL_DT_FINE_VALID_ACCORDO,
		new ColumnDescriptor(COL_DT_FINE_VALID_ACCORDO, Types.TIMESTAMP, 7, true));
	map.put(COL_DT_SCAD_ACCORDO,
		new ColumnDescriptor(COL_DT_SCAD_ACCORDO, Types.TIMESTAMP, 7, true));
	map.put(COL_FL_NON_CONVENZ,
		new ColumnDescriptor(COL_FL_NON_CONVENZ, Types.VARCHAR, 1, true));
	map.put(COL_FL_ESISTONO_GEST_ACC,
		new ColumnDescriptor(COL_FL_ESISTONO_GEST_ACC, Types.VARCHAR, 1, true));
	map.put(COL_TI_ENTE_CONVENZ,
		new ColumnDescriptor(COL_TI_ENTE_CONVENZ, Types.VARCHAR, 100, true));
	map.put(COL_TIPO_GESTIONE_ACCORDO,
		new ColumnDescriptor(COL_TIPO_GESTIONE_ACCORDO, Types.VARCHAR, 100, true));
	map.put(COL_FL_GEST_ACC_NO_RISP,
		new ColumnDescriptor(COL_FL_GEST_ACC_NO_RISP, Types.VARCHAR, 1, true));
	map.put(COL_TI_STATO_ACCORDO,
		new ColumnDescriptor(COL_TI_STATO_ACCORDO, Types.VARCHAR, 100, true));
	map.put(COL_CD_ENTE_CONVENZ,
		new ColumnDescriptor(COL_CD_ENTE_CONVENZ, Types.VARCHAR, 100, true));

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
