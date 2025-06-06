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

import it.eng.spagoLite.db.oracle.bean.column.ColumnDescriptor;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Generated;

/**
 * @author Sloth
 *
 *         Bean per la tabella Org_V_Occup_Storage_Accordo
 *
 */
public class OrgVOccupStorageAccordoTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter",
     * comments = "This class was generated by OraTool", date = "Tuesday, 6 June 2023 13:15" )
     */

    public static final String SELECT = "Select * from Org_V_Occup_Storage_Accordo /**/";
    public static final String TABLE_NAME = "Org_V_Occup_Storage_Accordo";
    public static final String COL_AMBIENTE_ENTE = "ambiente_ente";
    public static final String COL_CD_TIPO_ACCORDO = "cd_tipo_accordo";
    public static final String COL_NM_AMBIENTE_ENTE_CONVENZ = "nm_ambiente_ente_convenz";
    public static final String COL_NM_ENTE_SIAM = "nm_ente_siam";
    public static final String COL_ID_ACCORDO_ENTE = "id_accordo_ente";
    public static final String COL_DT_DEC_ACCORDO = "dt_dec_accordo";
    public static final String COL_MESI_VALIDITA = "mesi_validita";
    public static final String COL_NUM_UD = "num_ud";
    public static final String COL_NUM_DOC = "num_doc";
    public static final String COL_NUM_COMP = "num_comp";
    public static final String COL_DIM_BYTES = "dim_bytes";
    public static final String COL_NI_FASCIA_ACCORDO_A = "ni_fascia_accordo_a";
    public static final String COL_DT_FINE_ACCORDO = "dt_fine_accordo";
    public static final String COL_DIM_BYTES_MEDIA_ANNO = "dim_bytes_media_anno";
    public static final String COL_FASCIA_ACCORDO = "fascia_accordo";
    public static final String COL_NI_FASCIA_OCCUPAZIONE_A = "ni_fascia_occupazione_a";
    public static final String COL_FASCIA_OCCUPAZIONE = "fascia_occupazione";
    public static final String COL_FL_SFORO = "fl_sforo";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
	map.put(COL_AMBIENTE_ENTE,
		new ColumnDescriptor(COL_AMBIENTE_ENTE, Types.VARCHAR, 357, true));
	map.put(COL_CD_TIPO_ACCORDO,
		new ColumnDescriptor(COL_CD_TIPO_ACCORDO, Types.VARCHAR, 357, true));
	map.put(COL_NM_AMBIENTE_ENTE_CONVENZ,
		new ColumnDescriptor(COL_NM_AMBIENTE_ENTE_CONVENZ, Types.VARCHAR, 100, true));
	map.put(COL_NM_ENTE_SIAM, new ColumnDescriptor(COL_NM_ENTE_SIAM, Types.VARCHAR, 254, true));
	map.put(COL_ID_ACCORDO_ENTE,
		new ColumnDescriptor(COL_ID_ACCORDO_ENTE, Types.DECIMAL, 22, true));
	map.put(COL_DT_DEC_ACCORDO,
		new ColumnDescriptor(COL_DT_DEC_ACCORDO, Types.TIMESTAMP, 7, true));
	map.put(COL_MESI_VALIDITA,
		new ColumnDescriptor(COL_MESI_VALIDITA, Types.DECIMAL, 22, true));
	map.put(COL_NUM_UD, new ColumnDescriptor(COL_NUM_UD, Types.DECIMAL, 22, true));
	map.put(COL_NUM_DOC, new ColumnDescriptor(COL_NUM_DOC, Types.DECIMAL, 22, true));
	map.put(COL_NUM_COMP, new ColumnDescriptor(COL_NUM_COMP, Types.DECIMAL, 22, true));
	map.put(COL_DIM_BYTES, new ColumnDescriptor(COL_DIM_BYTES, Types.DECIMAL, 22, true));
	map.put(COL_DIM_BYTES_MEDIA_ANNO,
		new ColumnDescriptor(COL_DIM_BYTES_MEDIA_ANNO, Types.DECIMAL, 22, true));
	map.put(COL_NI_FASCIA_ACCORDO_A,
		new ColumnDescriptor(COL_NI_FASCIA_ACCORDO_A, Types.DECIMAL, 22, true));
	map.put(COL_DT_FINE_ACCORDO,
		new ColumnDescriptor(COL_DT_FINE_ACCORDO, Types.TIMESTAMP, 7, true));
	map.put(COL_NI_FASCIA_ACCORDO_A,
		new ColumnDescriptor(COL_NI_FASCIA_ACCORDO_A, Types.DECIMAL, 22, true));
	map.put(COL_FASCIA_ACCORDO,
		new ColumnDescriptor(COL_FASCIA_ACCORDO, Types.VARCHAR, 69, true));
	map.put(COL_FASCIA_OCCUPAZIONE,
		new ColumnDescriptor(COL_FASCIA_OCCUPAZIONE, Types.VARCHAR, 109, true));
	map.put(COL_FL_SFORO, new ColumnDescriptor(COL_FL_SFORO, Types.DECIMAL, 22, true));
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
