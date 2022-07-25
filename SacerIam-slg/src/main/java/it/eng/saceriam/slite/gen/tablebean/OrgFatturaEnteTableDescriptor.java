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
 *         Bean per la tabella Org_Fattura_Ente
 *
 */
public class OrgFatturaEnteTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Wednesday, 20 June 2018 16:18" )
     */

    public static final String SELECT = "Select * from Org_Fattura_Ente /**/";
    public static final String TABLE_NAME = "Org_Fattura_Ente";
    public static final String COL_ID_FATTURA_ENTE = "id_fattura_ente";
    public static final String COL_ID_ENTE_CONVENZ = "id_ente_convenz";
    public static final String COL_AA_FATTURA = "aa_fattura";
    public static final String COL_PG_FATTURA = "pg_fattura";
    public static final String COL_IM_TOT_FATTURA = "im_tot_fattura";
    public static final String COL_CD_REGISTRO_EMIS_FATTURA = "cd_registro_emis_fattura";
    public static final String COL_AA_EMISS_FATTURA = "aa_emiss_fattura";
    public static final String COL_CD_EMIS_FATTURA = "cd_emis_fattura";
    public static final String COL_ID_STATO_FATTURA_ENTE_COR = "id_stato_fattura_ente_cor";
    public static final String COL_DT_EMIS_FATTURA = "dt_emis_fattura";
    public static final String COL_DS_TESTO_FATTURA = "ds_testo_fattura";
    public static final String COL_NT_EMIS_FATTURA = "nt_emis_fattura";
    public static final String COL_DT_SCAD_FATTURA = "dt_scad_fattura";
    public static final String COL_CD_REGISTRO_EMIS_NOTA_CREDITO = "cd_registro_emis_nota_credito";
    public static final String COL_AA_EMISS_NOTA_CREDITO = "aa_emiss_nota_credito";
    public static final String COL_CD_EMIS_NOTA_CREDITO = "cd_emis_nota_credito";
    public static final String COL_DT_EMIS_NOTA_CREDITO = "dt_emis_nota_credito";
    public static final String COL_NT_EMIS_NOTA_CREDITO = "nt_emis_nota_credito";
    public static final String COL_NT_FATTURA = "nt_fattura";
    public static final String COL_ID_FATTURA_ENTE_STORNATA = "id_fattura_ente_stornata";
    public static final String COL_CD_FATTURA = "cd_fattura";
    public static final String COL_IM_TOT_DA_PAGARE = "im_tot_da_pagare";
    public static final String COL_CD_FATTURA_SAP = "cd_fattura_sap";
    public static final String COL_FL_DA_RIEMIS = "fl_da_riemis";
    public static final String COL_IM_TOT_IVA = "im_tot_iva";
    public static final String COL_NM_ENTE = "nm_ente";
    public static final String COL_NM_STRUT = "nm_strut";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
        map.put(COL_ID_FATTURA_ENTE, new ColumnDescriptor(COL_ID_FATTURA_ENTE, Types.DECIMAL, 22, true));
        map.put(COL_ID_ENTE_CONVENZ, new ColumnDescriptor(COL_ID_ENTE_CONVENZ, Types.DECIMAL, 22, false));
        map.put(COL_AA_FATTURA, new ColumnDescriptor(COL_AA_FATTURA, Types.DECIMAL, 22, false));
        map.put(COL_PG_FATTURA, new ColumnDescriptor(COL_PG_FATTURA, Types.DECIMAL, 22, false));
        map.put(COL_IM_TOT_FATTURA, new ColumnDescriptor(COL_IM_TOT_FATTURA, Types.DECIMAL, 22, false));
        map.put(COL_CD_REGISTRO_EMIS_FATTURA,
                new ColumnDescriptor(COL_CD_REGISTRO_EMIS_FATTURA, Types.VARCHAR, 100, false));
        map.put(COL_AA_EMISS_FATTURA, new ColumnDescriptor(COL_AA_EMISS_FATTURA, Types.DECIMAL, 22, false));
        map.put(COL_CD_EMIS_FATTURA, new ColumnDescriptor(COL_CD_EMIS_FATTURA, Types.VARCHAR, 100, false));
        map.put(COL_ID_STATO_FATTURA_ENTE_COR,
                new ColumnDescriptor(COL_ID_STATO_FATTURA_ENTE_COR, Types.DECIMAL, 22, false));
        map.put(COL_DT_EMIS_FATTURA, new ColumnDescriptor(COL_DT_EMIS_FATTURA, Types.TIMESTAMP, 7, false));
        map.put(COL_DS_TESTO_FATTURA, new ColumnDescriptor(COL_DS_TESTO_FATTURA, Types.VARCHAR, 1024, false));
        map.put(COL_NT_EMIS_FATTURA, new ColumnDescriptor(COL_NT_EMIS_FATTURA, Types.VARCHAR, 1024, false));
        map.put(COL_DT_SCAD_FATTURA, new ColumnDescriptor(COL_DT_SCAD_FATTURA, Types.TIMESTAMP, 7, false));
        map.put(COL_CD_REGISTRO_EMIS_NOTA_CREDITO,
                new ColumnDescriptor(COL_CD_REGISTRO_EMIS_NOTA_CREDITO, Types.VARCHAR, 100, false));
        map.put(COL_AA_EMISS_NOTA_CREDITO, new ColumnDescriptor(COL_AA_EMISS_NOTA_CREDITO, Types.DECIMAL, 22, false));
        map.put(COL_CD_EMIS_NOTA_CREDITO, new ColumnDescriptor(COL_CD_EMIS_NOTA_CREDITO, Types.VARCHAR, 100, false));
        map.put(COL_DT_EMIS_NOTA_CREDITO, new ColumnDescriptor(COL_DT_EMIS_NOTA_CREDITO, Types.TIMESTAMP, 7, false));
        map.put(COL_NT_EMIS_NOTA_CREDITO, new ColumnDescriptor(COL_NT_EMIS_NOTA_CREDITO, Types.VARCHAR, 1024, false));
        map.put(COL_NT_FATTURA, new ColumnDescriptor(COL_NT_FATTURA, Types.VARCHAR, 1024, false));
        map.put(COL_ID_FATTURA_ENTE_STORNATA,
                new ColumnDescriptor(COL_ID_FATTURA_ENTE_STORNATA, Types.DECIMAL, 22, false));
        map.put(COL_CD_FATTURA, new ColumnDescriptor(COL_CD_FATTURA, Types.VARCHAR, 100, false));
        map.put(COL_IM_TOT_DA_PAGARE, new ColumnDescriptor(COL_IM_TOT_DA_PAGARE, Types.DECIMAL, 22, false));
        map.put(COL_CD_FATTURA_SAP, new ColumnDescriptor(COL_CD_FATTURA_SAP, Types.VARCHAR, 100, false));
        map.put(COL_FL_DA_RIEMIS, new ColumnDescriptor(COL_FL_DA_RIEMIS, Types.VARCHAR, 1, false));
        map.put(COL_IM_TOT_IVA, new ColumnDescriptor(COL_IM_TOT_IVA, Types.DECIMAL, 22, false));
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
