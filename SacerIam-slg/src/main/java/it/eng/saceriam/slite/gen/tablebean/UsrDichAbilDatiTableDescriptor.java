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
 *         Bean per la tabella Usr_Dich_Abil_Dati
 *
 */
public class UsrDichAbilDatiTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter",
     * comments = "This class was generated by OraTool", date = "Monday, 15 April 2019 15:50" )
     */

    public static final String SELECT = "Select * from Usr_Dich_Abil_Dati /**/";
    public static final String TABLE_NAME = "Usr_Dich_Abil_Dati";
    public static final String COL_ID_DICH_ABIL_DATI = "id_dich_abil_dati";
    public static final String COL_ID_USO_USER_APPLIC = "id_uso_user_applic";
    public static final String COL_TI_SCOPO_DICH_ABIL_DATI = "ti_scopo_dich_abil_dati";
    public static final String COL_ID_ORGANIZ_IAM = "id_organiz_iam";
    public static final String COL_ID_TIPO_DATO_IAM = "id_tipo_dato_iam";
    public static final String COL_ID_CLASSE_TIPO_DATO = "id_classe_tipo_dato";
    public static final String COL_ID_APPART_COLLEG_ENTI = "id_appart_colleg_enti";
    public static final String COL_ID_SUPT_EST_ENTE_CONVENZ = "id_supt_est_ente_convenz";
    public static final String COL_ID_VIGIL_ENTE_PRODUT = "id_vigil_ente_produt";
    public static final String COL_DS_CAUSALE_DICH = "ds_causale_dich";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
	map.put(COL_ID_DICH_ABIL_DATI,
		new ColumnDescriptor(COL_ID_DICH_ABIL_DATI, Types.DECIMAL, 22, true));
	map.put(COL_ID_USO_USER_APPLIC,
		new ColumnDescriptor(COL_ID_USO_USER_APPLIC, Types.DECIMAL, 22, false));
	map.put(COL_TI_SCOPO_DICH_ABIL_DATI,
		new ColumnDescriptor(COL_TI_SCOPO_DICH_ABIL_DATI, Types.VARCHAR, 20, false));
	map.put(COL_ID_ORGANIZ_IAM,
		new ColumnDescriptor(COL_ID_ORGANIZ_IAM, Types.DECIMAL, 22, false));
	map.put(COL_ID_TIPO_DATO_IAM,
		new ColumnDescriptor(COL_ID_TIPO_DATO_IAM, Types.DECIMAL, 22, false));
	map.put(COL_ID_CLASSE_TIPO_DATO,
		new ColumnDescriptor(COL_ID_CLASSE_TIPO_DATO, Types.DECIMAL, 22, false));
	map.put(COL_ID_APPART_COLLEG_ENTI,
		new ColumnDescriptor(COL_ID_APPART_COLLEG_ENTI, Types.DECIMAL, 22, false));
	map.put(COL_ID_SUPT_EST_ENTE_CONVENZ,
		new ColumnDescriptor(COL_ID_SUPT_EST_ENTE_CONVENZ, Types.DECIMAL, 22, false));
	map.put(COL_ID_VIGIL_ENTE_PRODUT,
		new ColumnDescriptor(COL_ID_VIGIL_ENTE_PRODUT, Types.DECIMAL, 22, false));
	map.put(COL_DS_CAUSALE_DICH,
		new ColumnDescriptor(COL_DS_CAUSALE_DICH, Types.VARCHAR, 254, false));
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
