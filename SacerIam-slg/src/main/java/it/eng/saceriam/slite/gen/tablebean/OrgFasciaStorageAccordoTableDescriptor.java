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

import it.eng.spagoLite.db.oracle.bean.column.ColumnDescriptor;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Generated;

/**
 * @author Sloth
 *
 *         Bean per la tabella Org_Fascia_Storage_Accordo
 *
 */
public class OrgFasciaStorageAccordoTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter",
     * comments = "This class was generated by OraTool", date = "Monday, 5 June 2023 16:55" )
     */

    public static final String SELECT = "Select * from Org_Fascia_Storage_Accordo /**/";
    public static final String TABLE_NAME = "Org_Fascia_Storage_Accordo";
    public static final String COL_ID_FASCIA_STORAGE_ACCORDO = "id_fascia_storage_accordo";
    public static final String COL_TI_FASCIA = "ti_fascia";
    public static final String COL_NI_FASCIA_DA = "ni_fascia_da";
    public static final String COL_NI_FASCIA_A = "ni_fascia_a";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
	map.put(COL_ID_FASCIA_STORAGE_ACCORDO,
		new ColumnDescriptor(COL_ID_FASCIA_STORAGE_ACCORDO, Types.DECIMAL, 22, true));
	map.put(COL_TI_FASCIA, new ColumnDescriptor(COL_TI_FASCIA, Types.VARCHAR, 20, false));
	map.put(COL_NI_FASCIA_DA, new ColumnDescriptor(COL_NI_FASCIA_DA, Types.DECIMAL, 22, false));
	map.put(COL_NI_FASCIA_A, new ColumnDescriptor(COL_NI_FASCIA_A, Types.DECIMAL, 22, false));
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
