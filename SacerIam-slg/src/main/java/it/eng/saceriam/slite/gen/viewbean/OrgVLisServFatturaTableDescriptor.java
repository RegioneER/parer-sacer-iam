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
 *         Bean per la tabella Org_V_Lis_Serv_Fattura
 *
 */
public class OrgVLisServFatturaTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter",
     * comments = "This class was generated by OraTool", date = "Wednesday, 20 June 2018 16:19" )
     */

    public static final String SELECT = "Select * from Org_V_Lis_Serv_Fattura /**/";
    public static final String TABLE_NAME = "Org_V_Lis_Serv_Fattura";
    public static final String COL_ID_FATTURA_ENTE = "id_fattura_ente";
    public static final String COL_ID_SERVIZIO_FATTURA = "id_servizio_fattura";
    public static final String COL_AA_SERVIZIO_FATTURA = "aa_servizio_fattura";
    public static final String COL_NM_SERVIZIO_FATTURA = "nm_servizio_fattura";
    public static final String COL_IM_NETTO = "im_netto";
    public static final String COL_NI_BYTE_VERS = "ni_byte_vers";
    public static final String COL_NI_UNITÀ_DOC_VERS = "ni_unità_doc_vers";
    public static final String COL_NI_ABITANTI = "ni_abitanti";
    public static final String COL_DT_EROG = "dt_erog";
    public static final String COL_MM_FATTURA = "mm_fattura";
    public static final String COL_CD_IVA = "cd_iva";
    public static final String COL_IM_IVA = "im_iva";
    public static final String COL_FL_SUPERAMENTO_SCAGLIONE = "fl_superamento_scaglione";
    public static final String COL_CD_DDT = "cd_ddt";
    public static final String COL_CD_ODA = "cd_oda";
    public static final String COL_NM_SERVIZIO_EROGATO = "nm_servizio_erogato";
    public static final String COL_FL_PAGAMENTO = "fl_pagamento";
    public static final String COL_TIPO_FATTURAZIONE = "tipo_fatturazione";
    public static final String COL_NM_TARIFFA = "nm_tariffa";
    public static final String COL_IM_VALORE_TARIFFA = "im_valore_tariffa";
    public static final String COL_CD_TIPO_SERVIZIO = "cd_tipo_servizio";
    public static final String COL_NM_SISTEMA_VERSANTE = "nm_sistema_versante";
    public static final String COL_DT_EROG_SERV_EROG = "dt_erog_serv_erog";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
	map.put(COL_ID_FATTURA_ENTE,
		new ColumnDescriptor(COL_ID_FATTURA_ENTE, Types.DECIMAL, 22, true));
	map.put(COL_ID_SERVIZIO_FATTURA,
		new ColumnDescriptor(COL_ID_SERVIZIO_FATTURA, Types.DECIMAL, 22, true));
	map.put(COL_AA_SERVIZIO_FATTURA,
		new ColumnDescriptor(COL_AA_SERVIZIO_FATTURA, Types.DECIMAL, 22, true));
	map.put(COL_NM_SERVIZIO_FATTURA,
		new ColumnDescriptor(COL_NM_SERVIZIO_FATTURA, Types.VARCHAR, 150, true));
	map.put(COL_IM_NETTO, new ColumnDescriptor(COL_IM_NETTO, Types.DECIMAL, 22, true));
	map.put(COL_NI_BYTE_VERS, new ColumnDescriptor(COL_NI_BYTE_VERS, Types.DECIMAL, 22, true));
	map.put(COL_NI_UNITÀ_DOC_VERS,
		new ColumnDescriptor(COL_NI_UNITÀ_DOC_VERS, Types.DECIMAL, 22, true));
	map.put(COL_NI_ABITANTI, new ColumnDescriptor(COL_NI_ABITANTI, Types.DECIMAL, 22, true));
	map.put(COL_DT_EROG, new ColumnDescriptor(COL_DT_EROG, Types.TIMESTAMP, 7, true));
	map.put(COL_MM_FATTURA, new ColumnDescriptor(COL_MM_FATTURA, Types.DECIMAL, 22, true));
	map.put(COL_CD_IVA, new ColumnDescriptor(COL_CD_IVA, Types.VARCHAR, 100, true));
	map.put(COL_IM_IVA, new ColumnDescriptor(COL_IM_IVA, Types.DECIMAL, 22, true));
	map.put(COL_FL_SUPERAMENTO_SCAGLIONE,
		new ColumnDescriptor(COL_FL_SUPERAMENTO_SCAGLIONE, Types.VARCHAR, 1, true));
	map.put(COL_CD_DDT, new ColumnDescriptor(COL_CD_DDT, Types.VARCHAR, 100, true));
	map.put(COL_CD_ODA, new ColumnDescriptor(COL_CD_ODA, Types.VARCHAR, 100, true));
	map.put(COL_NM_SERVIZIO_EROGATO,
		new ColumnDescriptor(COL_NM_SERVIZIO_EROGATO, Types.VARCHAR, 150, true));
	map.put(COL_FL_PAGAMENTO, new ColumnDescriptor(COL_FL_PAGAMENTO, Types.VARCHAR, 1, true));
	map.put(COL_TIPO_FATTURAZIONE,
		new ColumnDescriptor(COL_TIPO_FATTURAZIONE, Types.VARCHAR, 30, true));
	map.put(COL_NM_TARIFFA, new ColumnDescriptor(COL_NM_TARIFFA, Types.VARCHAR, 100, true));
	map.put(COL_IM_VALORE_TARIFFA,
		new ColumnDescriptor(COL_IM_VALORE_TARIFFA, Types.DECIMAL, 22, true));
	map.put(COL_CD_TIPO_SERVIZIO,
		new ColumnDescriptor(COL_CD_TIPO_SERVIZIO, Types.VARCHAR, 100, true));
	map.put(COL_NM_SISTEMA_VERSANTE,
		new ColumnDescriptor(COL_NM_SISTEMA_VERSANTE, Types.VARCHAR, 100, true));
	map.put(COL_DT_EROG_SERV_EROG,
		new ColumnDescriptor(COL_DT_EROG_SERV_EROG, Types.TIMESTAMP, 7, true));
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
