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
 *         Bean per la tabella Org_Scaglione_Tariffa
 *
 */
public class OrgScaglioneTariffaTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter",
     * comments = "This class was generated by OraTool", date = "Friday, 12 August 2016 16:44" )
     */

    public static final String SELECT = "Select * from Org_Scaglione_Tariffa /**/";
    public static final String TABLE_NAME = "Org_Scaglione_Tariffa";
    public static final String COL_ID_SCAGLIONE_TARIFFA = "id_scaglione_tariffa";
    public static final String COL_ID_TARIFFA = "id_tariffa";
    public static final String COL_NI_INI_SCAGLIONE = "ni_ini_scaglione";
    public static final String COL_NI_FINE_SCAGLIONE = "ni_fine_scaglione";
    public static final String COL_IM_SCAGLIONE = "im_scaglione";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
	map.put(COL_ID_SCAGLIONE_TARIFFA,
		new ColumnDescriptor(COL_ID_SCAGLIONE_TARIFFA, Types.DECIMAL, 22, true));
	map.put(COL_ID_TARIFFA, new ColumnDescriptor(COL_ID_TARIFFA, Types.DECIMAL, 22, false));
	map.put(COL_NI_INI_SCAGLIONE,
		new ColumnDescriptor(COL_NI_INI_SCAGLIONE, Types.DECIMAL, 22, false));
	map.put(COL_NI_FINE_SCAGLIONE,
		new ColumnDescriptor(COL_NI_FINE_SCAGLIONE, Types.DECIMAL, 22, false));
	map.put(COL_IM_SCAGLIONE, new ColumnDescriptor(COL_IM_SCAGLIONE, Types.DECIMAL, 22, false));
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
