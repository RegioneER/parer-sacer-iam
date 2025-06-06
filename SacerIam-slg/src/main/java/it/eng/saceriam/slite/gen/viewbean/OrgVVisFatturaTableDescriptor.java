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
 *         Bean per la tabella Org_V_Vis_Fattura
 *
 */
public class OrgVVisFatturaTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter",
     * comments = "This class was generated by OraTool", date = "Tuesday, 23 March 2021 11:35" )
     */

    public static final String SELECT = "Select * from Org_V_Vis_Fattura /**/";
    public static final String TABLE_NAME = "Org_V_Vis_Fattura";
    public static final String COL_ID_STATO_CALC = "id_stato_calc";
    public static final String COL_ID_FATTURA_ENTE = "id_fattura_ente";
    public static final String COL_NM_AMBIENTE_ENTE_CONVENZ = "nm_ambiente_ente_convenz";
    public static final String COL_ID_AMBIENTE_ENTE_CONVENZ = "id_ambiente_ente_convenz";
    public static final String COL_ID_ENTE_CONVENZ = "id_ente_convenz";
    public static final String COL_NM_ENTE_CONVENZ = "nm_ente_convenz";
    public static final String COL_CD_ENTE_CONVENZ = "cd_ente_convenz";
    public static final String COL_TI_CD_ENTE_CONVENZ = "ti_cd_ente_convenz";
    public static final String COL_CD_FISC = "cd_fisc";
    public static final String COL_CD_CLIENTE_SAP = "cd_cliente_sap";
    public static final String COL_CD_CATEG_ENTE = "cd_categ_ente";
    public static final String COL_CD_TIPO_ACCORDO = "cd_tipo_accordo";
    public static final String COL_DT_DEC_ACCORDO_INFO = "dt_dec_accordo_info";
    public static final String COL_DT_DEC_ACCORDO = "dt_dec_accordo";
    public static final String COL_DT_SCAD_ACCORDO = "dt_scad_accordo";
    public static final String COL_DT_FINE_VALID_ACCORDO = "dt_fine_valid_accordo";
    public static final String COL_FL_PAGAMENTO = "fl_pagamento";
    public static final String COL_NM_TARIFFARIO = "nm_tariffario";
    public static final String COL_CD_CLASSE_ENTE_CONVENZ = "cd_classe_ente_convenz";
    public static final String COL_NI_ABITANTI = "ni_abitanti";
    public static final String COL_DS_NOTE_ACCORDO = "ds_note_accordo";
    public static final String COL_CD_UFE = "cd_ufe";
    public static final String COL_DS_UFE = "ds_ufe";
    public static final String COL_CD_IVA = "cd_iva";
    public static final String COL_DS_IVA = "ds_iva";
    public static final String COL_CD_CIG = "cd_cig";
    public static final String COL_CD_CUP = "cd_cup";
    public static final String COL_CD_RIF_CONTAB = "cd_rif_contab";
    public static final String COL_CD_COGE = "cd_coge";
    public static final String COL_CD_CAPITOLO = "cd_capitolo";
    public static final String COL_TI_STATO_FATTURA_ENTE = "ti_stato_fattura_ente";
    public static final String COL_DT_REG_STATO_FATTURA_ENTE = "dt_reg_stato_fattura_ente";
    public static final String COL_AA_FATTURA = "aa_fattura";
    public static final String COL_PG_FATTURA = "pg_fattura";
    public static final String COL_CD_FATTURA = "cd_fattura";
    public static final String COL_DT_CREAZIONE = "dt_creazione";
    public static final String COL_CD_REGISTRO_EMIS_FATTURA = "cd_registro_emis_fattura";
    public static final String COL_AA_EMISS_FATTURA = "aa_emiss_fattura";
    public static final String COL_CD_EMIS_FATTURA = "cd_emis_fattura";
    public static final String COL_DT_EMIS_FATTURA = "dt_emis_fattura";
    public static final String COL_DT_SCAD_FATTURA = "dt_scad_fattura";
    public static final String COL_NT_EMIS_FATTURA = "nt_emis_fattura";
    public static final String COL_CD_FATTURA_SAP = "cd_fattura_sap";
    public static final String COL_IM_TOT_NETTO = "im_tot_netto";
    public static final String COL_IM_TOT_IVA = "im_tot_iva";
    public static final String COL_IM_TOT_FATTURA = "im_tot_fattura";
    public static final String COL_IM_TOT_DA_PAGARE = "im_tot_da_pagare";
    public static final String COL_IM_TOT_PAGAM = "im_tot_pagam";
    public static final String COL_FL_SUPERAMENTO_SCAGLIONE = "fl_superamento_scaglione";
    public static final String COL_CD_REGISTRO_EMIS_NOTA_CREDITO = "cd_registro_emis_nota_credito";
    public static final String COL_AA_EMISS_NOTA_CREDITO = "aa_emiss_nota_credito";
    public static final String COL_CD_EMIS_NOTA_CREDITO = "cd_emis_nota_credito";
    public static final String COL_DT_EMIS_NOTA_CREDITO = "dt_emis_nota_credito";
    public static final String COL_NT_EMIS_NOTA_CREDITO = "nt_emis_nota_credito";
    public static final String COL_FL_DA_RIEMIS = "fl_da_riemis";
    public static final String COL_NM_AMBIENTE = "nm_ambiente";
    public static final String COL_NM_ENTE = "nm_ente";
    public static final String COL_NM_STRUT = "nm_strut";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
	map.put(COL_ID_STATO_CALC,
		new ColumnDescriptor(COL_ID_STATO_CALC, Types.DECIMAL, 22, true));
	map.put(COL_ID_FATTURA_ENTE,
		new ColumnDescriptor(COL_ID_FATTURA_ENTE, Types.DECIMAL, 22, true));
	map.put(COL_NM_AMBIENTE_ENTE_CONVENZ,
		new ColumnDescriptor(COL_NM_AMBIENTE_ENTE_CONVENZ, Types.VARCHAR, 100, true));
	map.put(COL_ID_AMBIENTE_ENTE_CONVENZ,
		new ColumnDescriptor(COL_ID_AMBIENTE_ENTE_CONVENZ, Types.DECIMAL, 22, true));
	map.put(COL_ID_ENTE_CONVENZ,
		new ColumnDescriptor(COL_ID_ENTE_CONVENZ, Types.DECIMAL, 22, true));
	map.put(COL_NM_ENTE_CONVENZ,
		new ColumnDescriptor(COL_NM_ENTE_CONVENZ, Types.VARCHAR, 254, true));
	map.put(COL_CD_ENTE_CONVENZ,
		new ColumnDescriptor(COL_CD_ENTE_CONVENZ, Types.VARCHAR, 100, true));
	map.put(COL_TI_CD_ENTE_CONVENZ,
		new ColumnDescriptor(COL_TI_CD_ENTE_CONVENZ, Types.VARCHAR, 20, true));
	map.put(COL_CD_FISC, new ColumnDescriptor(COL_CD_FISC, Types.VARCHAR, 11, true));
	map.put(COL_CD_CLIENTE_SAP,
		new ColumnDescriptor(COL_CD_CLIENTE_SAP, Types.VARCHAR, 100, true));
	map.put(COL_CD_CATEG_ENTE,
		new ColumnDescriptor(COL_CD_CATEG_ENTE, Types.VARCHAR, 100, true));
	map.put(COL_CD_TIPO_ACCORDO,
		new ColumnDescriptor(COL_CD_TIPO_ACCORDO, Types.VARCHAR, 100, true));
	map.put(COL_DT_DEC_ACCORDO_INFO,
		new ColumnDescriptor(COL_DT_DEC_ACCORDO_INFO, Types.TIMESTAMP, 7, true));
	map.put(COL_DT_DEC_ACCORDO,
		new ColumnDescriptor(COL_DT_DEC_ACCORDO, Types.TIMESTAMP, 7, true));
	map.put(COL_DT_SCAD_ACCORDO,
		new ColumnDescriptor(COL_DT_SCAD_ACCORDO, Types.TIMESTAMP, 7, true));
	map.put(COL_DT_FINE_VALID_ACCORDO,
		new ColumnDescriptor(COL_DT_FINE_VALID_ACCORDO, Types.TIMESTAMP, 7, true));
	map.put(COL_FL_PAGAMENTO, new ColumnDescriptor(COL_FL_PAGAMENTO, Types.VARCHAR, 1, true));
	map.put(COL_NM_TARIFFARIO,
		new ColumnDescriptor(COL_NM_TARIFFARIO, Types.VARCHAR, 100, true));
	map.put(COL_CD_CLASSE_ENTE_CONVENZ,
		new ColumnDescriptor(COL_CD_CLASSE_ENTE_CONVENZ, Types.VARCHAR, 100, true));
	map.put(COL_NI_ABITANTI, new ColumnDescriptor(COL_NI_ABITANTI, Types.DECIMAL, 22, true));
	map.put(COL_DS_NOTE_ACCORDO,
		new ColumnDescriptor(COL_DS_NOTE_ACCORDO, Types.VARCHAR, 4000, true));
	map.put(COL_CD_UFE, new ColumnDescriptor(COL_CD_UFE, Types.VARCHAR, 100, true));
	map.put(COL_DS_UFE, new ColumnDescriptor(COL_DS_UFE, Types.VARCHAR, 254, true));
	map.put(COL_CD_IVA, new ColumnDescriptor(COL_CD_IVA, Types.VARCHAR, 100, true));
	map.put(COL_DS_IVA, new ColumnDescriptor(COL_DS_IVA, Types.VARCHAR, 254, true));
	map.put(COL_CD_CIG, new ColumnDescriptor(COL_CD_CIG, Types.VARCHAR, 100, true));
	map.put(COL_CD_CUP, new ColumnDescriptor(COL_CD_CUP, Types.VARCHAR, 100, true));
	map.put(COL_CD_RIF_CONTAB,
		new ColumnDescriptor(COL_CD_RIF_CONTAB, Types.VARCHAR, 254, true));
	map.put(COL_CD_COGE, new ColumnDescriptor(COL_CD_COGE, Types.VARCHAR, 100, true));
	map.put(COL_CD_CAPITOLO, new ColumnDescriptor(COL_CD_CAPITOLO, Types.VARCHAR, 100, true));
	map.put(COL_TI_STATO_FATTURA_ENTE,
		new ColumnDescriptor(COL_TI_STATO_FATTURA_ENTE, Types.VARCHAR, 30, true));
	map.put(COL_DT_REG_STATO_FATTURA_ENTE,
		new ColumnDescriptor(COL_DT_REG_STATO_FATTURA_ENTE, Types.TIMESTAMP, 7, true));
	map.put(COL_AA_FATTURA, new ColumnDescriptor(COL_AA_FATTURA, Types.DECIMAL, 22, true));
	map.put(COL_PG_FATTURA, new ColumnDescriptor(COL_PG_FATTURA, Types.DECIMAL, 22, true));
	map.put(COL_CD_FATTURA, new ColumnDescriptor(COL_CD_FATTURA, Types.VARCHAR, 100, true));
	map.put(COL_DT_CREAZIONE, new ColumnDescriptor(COL_DT_CREAZIONE, Types.TIMESTAMP, 7, true));
	map.put(COL_CD_REGISTRO_EMIS_FATTURA,
		new ColumnDescriptor(COL_CD_REGISTRO_EMIS_FATTURA, Types.VARCHAR, 100, true));
	map.put(COL_AA_EMISS_FATTURA,
		new ColumnDescriptor(COL_AA_EMISS_FATTURA, Types.DECIMAL, 22, true));
	map.put(COL_CD_EMIS_FATTURA,
		new ColumnDescriptor(COL_CD_EMIS_FATTURA, Types.VARCHAR, 100, true));
	map.put(COL_DT_EMIS_FATTURA,
		new ColumnDescriptor(COL_DT_EMIS_FATTURA, Types.TIMESTAMP, 7, true));
	map.put(COL_DT_SCAD_FATTURA,
		new ColumnDescriptor(COL_DT_SCAD_FATTURA, Types.TIMESTAMP, 7, true));
	map.put(COL_NT_EMIS_FATTURA,
		new ColumnDescriptor(COL_NT_EMIS_FATTURA, Types.VARCHAR, 1024, true));
	map.put(COL_CD_FATTURA_SAP,
		new ColumnDescriptor(COL_CD_FATTURA_SAP, Types.VARCHAR, 100, true));
	map.put(COL_IM_TOT_NETTO, new ColumnDescriptor(COL_IM_TOT_NETTO, Types.DECIMAL, 22, true));
	map.put(COL_IM_TOT_IVA, new ColumnDescriptor(COL_IM_TOT_IVA, Types.DECIMAL, 22, true));
	map.put(COL_IM_TOT_FATTURA,
		new ColumnDescriptor(COL_IM_TOT_FATTURA, Types.DECIMAL, 22, true));
	map.put(COL_IM_TOT_DA_PAGARE,
		new ColumnDescriptor(COL_IM_TOT_DA_PAGARE, Types.DECIMAL, 22, true));
	map.put(COL_IM_TOT_PAGAM, new ColumnDescriptor(COL_IM_TOT_PAGAM, Types.DECIMAL, 22, true));
	map.put(COL_FL_SUPERAMENTO_SCAGLIONE,
		new ColumnDescriptor(COL_FL_SUPERAMENTO_SCAGLIONE, Types.VARCHAR, 1, true));
	map.put(COL_CD_REGISTRO_EMIS_NOTA_CREDITO,
		new ColumnDescriptor(COL_CD_REGISTRO_EMIS_NOTA_CREDITO, Types.VARCHAR, 100, true));
	map.put(COL_AA_EMISS_NOTA_CREDITO,
		new ColumnDescriptor(COL_AA_EMISS_NOTA_CREDITO, Types.DECIMAL, 22, true));
	map.put(COL_CD_EMIS_NOTA_CREDITO,
		new ColumnDescriptor(COL_CD_EMIS_NOTA_CREDITO, Types.VARCHAR, 100, true));
	map.put(COL_DT_EMIS_NOTA_CREDITO,
		new ColumnDescriptor(COL_DT_EMIS_NOTA_CREDITO, Types.TIMESTAMP, 7, true));
	map.put(COL_NT_EMIS_NOTA_CREDITO,
		new ColumnDescriptor(COL_NT_EMIS_NOTA_CREDITO, Types.VARCHAR, 1024, true));
	map.put(COL_FL_DA_RIEMIS, new ColumnDescriptor(COL_FL_DA_RIEMIS, Types.VARCHAR, 1, true));
	map.put(COL_NM_AMBIENTE, new ColumnDescriptor(COL_NM_AMBIENTE, Types.VARCHAR, 100, false));
	map.put(COL_NM_ENTE, new ColumnDescriptor(COL_NM_ENTE, Types.VARCHAR, 100, false));
	map.put(COL_NM_STRUT, new ColumnDescriptor(COL_NM_STRUT, Types.VARCHAR, 100, false));
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
