package it.eng.saceriam.slite.gen.viewbean;

import it.eng.saceriam.viewEntity.OrgVVisFattura;
import it.eng.saceriam.viewEntity.OrgVVisFatturaId;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * RowBean per la tabella Org_V_Vis_Fattura
 *
 */
public class OrgVVisFatturaRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Tuesday, 23 March 2021 11:35" )
     */

    public static OrgVVisFatturaTableDescriptor TABLE_DESCRIPTOR = new OrgVVisFatturaTableDescriptor();

    public OrgVVisFatturaRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    public BigDecimal getIdStatoCalc() {
        return getBigDecimal("id_stato_calc");
    }

    public void setIdStatoCalc(BigDecimal idStatoCalc) {
        setObject("id_stato_calc", idStatoCalc);
    }

    public BigDecimal getIdFatturaEnte() {
        return getBigDecimal("id_fattura_ente");
    }

    public void setIdFatturaEnte(BigDecimal idFatturaEnte) {
        setObject("id_fattura_ente", idFatturaEnte);
    }

    public String getNmAmbienteEnteConvenz() {
        return getString("nm_ambiente_ente_convenz");
    }

    public void setNmAmbienteEnteConvenz(String nmAmbienteEnteConvenz) {
        setObject("nm_ambiente_ente_convenz", nmAmbienteEnteConvenz);
    }

    public BigDecimal getIdAmbienteEnteConvenz() {
        return getBigDecimal("id_ambiente_ente_convenz");
    }

    public void setIdAmbienteEnteConvenz(BigDecimal idAmbienteEnteConvenz) {
        setObject("id_ambiente_ente_convenz", idAmbienteEnteConvenz);
    }

    public BigDecimal getIdEnteConvenz() {
        return getBigDecimal("id_ente_convenz");
    }

    public void setIdEnteConvenz(BigDecimal idEnteConvenz) {
        setObject("id_ente_convenz", idEnteConvenz);
    }

    public String getNmEnteConvenz() {
        return getString("nm_ente_convenz");
    }

    public void setNmEnteConvenz(String nmEnteConvenz) {
        setObject("nm_ente_convenz", nmEnteConvenz);
    }

    public String getCdEnteConvenz() {
        return getString("cd_ente_convenz");
    }

    public void setCdEnteConvenz(String cdEnteConvenz) {
        setObject("cd_ente_convenz", cdEnteConvenz);
    }

    public String getTiCdEnteConvenz() {
        return getString("ti_cd_ente_convenz");
    }

    public void setTiCdEnteConvenz(String tiCdEnteConvenz) {
        setObject("ti_cd_ente_convenz", tiCdEnteConvenz);
    }

    public String getCdFisc() {
        return getString("cd_fisc");
    }

    public void setCdFisc(String cdFisc) {
        setObject("cd_fisc", cdFisc);
    }

    public String getCdClienteSap() {
        return getString("cd_cliente_sap");
    }

    public void setCdClienteSap(String cdClienteSap) {
        setObject("cd_cliente_sap", cdClienteSap);
    }

    public String getCdCategEnte() {
        return getString("cd_categ_ente");
    }

    public void setCdCategEnte(String cdCategEnte) {
        setObject("cd_categ_ente", cdCategEnte);
    }

    public String getCdTipoAccordo() {
        return getString("cd_tipo_accordo");
    }

    public void setCdTipoAccordo(String cdTipoAccordo) {
        setObject("cd_tipo_accordo", cdTipoAccordo);
    }

    public void setDtDecAccordoInfo(Timestamp dt_dec_accordo_info) {
        setObject("dt_dec_accordo_info", dt_dec_accordo_info);
    }

    public Timestamp getDtDecAccordoInfo() {
        return getTimestamp("dt_dec_accordo_info");
    }

    public Timestamp getDtDecAccordo() {
        return getTimestamp("dt_dec_accordo");
    }

    public void setDtDecAccordo(Timestamp dtDecAccordo) {
        setObject("dt_dec_accordo", dtDecAccordo);
    }

    public Timestamp getDtScadAccordo() {
        return getTimestamp("dt_scad_accordo");
    }

    public void setDtScadAccordo(Timestamp dtScadAccordo) {
        setObject("dt_scad_accordo", dtScadAccordo);
    }

    public Timestamp getDtFineValidAccordo() {
        return getTimestamp("dt_fine_valid_accordo");
    }

    public void setDtFineValidAccordo(Timestamp dtFineValidAccordo) {
        setObject("dt_fine_valid_accordo", dtFineValidAccordo);
    }

    public String getFlPagamento() {
        return getString("fl_pagamento");
    }

    public void setFlPagamento(String flPagamento) {
        setObject("fl_pagamento", flPagamento);
    }

    public String getNmTariffario() {
        return getString("nm_tariffario");
    }

    public void setNmTariffario(String nmTariffario) {
        setObject("nm_tariffario", nmTariffario);
    }

    public String getCdClasseEnteConvenz() {
        return getString("cd_classe_ente_convenz");
    }

    public void setCdClasseEnteConvenz(String cdClasseEnteConvenz) {
        setObject("cd_classe_ente_convenz", cdClasseEnteConvenz);
    }

    public BigDecimal getNiAbitanti() {
        return getBigDecimal("ni_abitanti");
    }

    public void setNiAbitanti(BigDecimal niAbitanti) {
        setObject("ni_abitanti", niAbitanti);
    }

    public String getDsNoteAccordo() {
        return getString("ds_note_accordo");
    }

    public void setDsNoteAccordo(String dsNoteAccordo) {
        setObject("ds_note_accordo", dsNoteAccordo);
    }

    public String getCdUfe() {
        return getString("cd_ufe");
    }

    public void setCdUfe(String cdUfe) {
        setObject("cd_ufe", cdUfe);
    }

    public String getDsUfe() {
        return getString("ds_ufe");
    }

    public void setDsUfe(String dsUfe) {
        setObject("ds_ufe", dsUfe);
    }

    public String getCdIva() {
        return getString("cd_iva");
    }

    public void setCdIva(String cdIva) {
        setObject("cd_iva", cdIva);
    }

    public String getDsIva() {
        return getString("ds_iva");
    }

    public void setDsIva(String dsIva) {
        setObject("ds_iva", dsIva);
    }

    public String getCdCig() {
        return getString("cd_cig");
    }

    public void setCdCig(String cdCig) {
        setObject("cd_cig", cdCig);
    }

    public String getCdCup() {
        return getString("cd_cup");
    }

    public void setCdCup(String cdCup) {
        setObject("cd_cup", cdCup);
    }

    public String getCdRifContab() {
        return getString("cd_rif_contab");
    }

    public void setCdRifContab(String cdRifContab) {
        setObject("cd_rif_contab", cdRifContab);
    }

    public String getCdCoge() {
        return getString("cd_coge");
    }

    public void setCdCoge(String cdCoge) {
        setObject("cd_coge", cdCoge);
    }

    public String getCdCapitolo() {
        return getString("cd_capitolo");
    }

    public void setCdCapitolo(String cdCapitolo) {
        setObject("cd_capitolo", cdCapitolo);
    }

    public String getTiStatoFatturaEnte() {
        return getString("ti_stato_fattura_ente");
    }

    public void setTiStatoFatturaEnte(String tiStatoFatturaEnte) {
        setObject("ti_stato_fattura_ente", tiStatoFatturaEnte);
    }

    public Timestamp getDtRegStatoFatturaEnte() {
        return getTimestamp("dt_reg_stato_fattura_ente");
    }

    public void setDtRegStatoFatturaEnte(Timestamp dtRegStatoFatturaEnte) {
        setObject("dt_reg_stato_fattura_ente", dtRegStatoFatturaEnte);
    }

    public BigDecimal getAaFattura() {
        return getBigDecimal("aa_fattura");
    }

    public void setAaFattura(BigDecimal aaFattura) {
        setObject("aa_fattura", aaFattura);
    }

    public BigDecimal getPgFattura() {
        return getBigDecimal("pg_fattura");
    }

    public void setPgFattura(BigDecimal pgFattura) {
        setObject("pg_fattura", pgFattura);
    }

    public String getCdFattura() {
        return getString("cd_fattura");
    }

    public void setCdFattura(String cdFattura) {
        setObject("cd_fattura", cdFattura);
    }

    public Timestamp getDtCreazione() {
        return getTimestamp("dt_creazione");
    }

    public void setDtCreazione(Timestamp dtCreazione) {
        setObject("dt_creazione", dtCreazione);
    }

    public String getCdRegistroEmisFattura() {
        return getString("cd_registro_emis_fattura");
    }

    public void setCdRegistroEmisFattura(String cdRegistroEmisFattura) {
        setObject("cd_registro_emis_fattura", cdRegistroEmisFattura);
    }

    public BigDecimal getAaEmissFattura() {
        return getBigDecimal("aa_emiss_fattura");
    }

    public void setAaEmissFattura(BigDecimal aaEmissFattura) {
        setObject("aa_emiss_fattura", aaEmissFattura);
    }

    public String getCdEmisFattura() {
        return getString("cd_emis_fattura");
    }

    public void setCdEmisFattura(String cdEmisFattura) {
        setObject("cd_emis_fattura", cdEmisFattura);
    }

    public Timestamp getDtEmisFattura() {
        return getTimestamp("dt_emis_fattura");
    }

    public void setDtEmisFattura(Timestamp dtEmisFattura) {
        setObject("dt_emis_fattura", dtEmisFattura);
    }

    public Timestamp getDtScadFattura() {
        return getTimestamp("dt_scad_fattura");
    }

    public void setDtScadFattura(Timestamp dtScadFattura) {
        setObject("dt_scad_fattura", dtScadFattura);
    }

    public String getNtEmisFattura() {
        return getString("nt_emis_fattura");
    }

    public void setNtEmisFattura(String ntEmisFattura) {
        setObject("nt_emis_fattura", ntEmisFattura);
    }

    public String getCdFatturaSap() {
        return getString("cd_fattura_sap");
    }

    public void setCdFatturaSap(String cdFatturaSap) {
        setObject("cd_fattura_sap", cdFatturaSap);
    }

    public BigDecimal getImTotNetto() {
        return getBigDecimal("im_tot_netto");
    }

    public void setImTotNetto(BigDecimal imTotNetto) {
        setObject("im_tot_netto", imTotNetto);
    }

    public BigDecimal getImTotIva() {
        return getBigDecimal("im_tot_iva");
    }

    public void setImTotIva(BigDecimal imTotIva) {
        setObject("im_tot_iva", imTotIva);
    }

    public BigDecimal getImTotFattura() {
        return getBigDecimal("im_tot_fattura");
    }

    public void setImTotFattura(BigDecimal imTotFattura) {
        setObject("im_tot_fattura", imTotFattura);
    }

    public BigDecimal getImTotDaPagare() {
        return getBigDecimal("im_tot_da_pagare");
    }

    public void setImTotDaPagare(BigDecimal imTotDaPagare) {
        setObject("im_tot_da_pagare", imTotDaPagare);
    }

    public BigDecimal getImTotPagam() {
        return getBigDecimal("im_tot_pagam");
    }

    public void setImTotPagam(BigDecimal imTotPagam) {
        setObject("im_tot_pagam", imTotPagam);
    }

    public String getFlSuperamentoScaglione() {
        return getString("fl_superamento_scaglione");
    }

    public void setFlSuperamentoScaglione(String flSuperamentoScaglione) {
        setObject("fl_superamento_scaglione", flSuperamentoScaglione);
    }

    public String getCdRegistroEmisNotaCredito() {
        return getString("cd_registro_emis_nota_credito");
    }

    public void setCdRegistroEmisNotaCredito(String cdRegistroEmisNotaCredito) {
        setObject("cd_registro_emis_nota_credito", cdRegistroEmisNotaCredito);
    }

    public BigDecimal getAaEmissNotaCredito() {
        return getBigDecimal("aa_emiss_nota_credito");
    }

    public void setAaEmissNotaCredito(BigDecimal aaEmissNotaCredito) {
        setObject("aa_emiss_nota_credito", aaEmissNotaCredito);
    }

    public String getCdEmisNotaCredito() {
        return getString("cd_emis_nota_credito");
    }

    public void setCdEmisNotaCredito(String cdEmisNotaCredito) {
        setObject("cd_emis_nota_credito", cdEmisNotaCredito);
    }

    public Timestamp getDtEmisNotaCredito() {
        return getTimestamp("dt_emis_nota_credito");
    }

    public void setDtEmisNotaCredito(Timestamp dtEmisNotaCredito) {
        setObject("dt_emis_nota_credito", dtEmisNotaCredito);
    }

    public String getNtEmisNotaCredito() {
        return getString("nt_emis_nota_credito");
    }

    public void setNtEmisNotaCredito(String ntEmisNotaCredito) {
        setObject("nt_emis_nota_credito", ntEmisNotaCredito);
    }

    public String getFlDaRiemis() {
        return getString("fl_da_riemis");
    }

    public void setFlDaRiemis(String flDaRiemis) {
        setObject("fl_da_riemis", flDaRiemis);
    }

    public String getNmAmbiente() {
        return getString("nm_ambiente");
    }

    public void setNmAmbiente(String nmAmbiente) {
        setObject("nm_ambiente", nmAmbiente);
    }

    public String getNmEnte() {
        return getString("nm_ente");
    }

    public void setNmEnte(String nmEnte) {
        setObject("nm_ente", nmEnte);
    }

    public String getNmStrut() {
        return getString("nm_strut");
    }

    public void setNmStrut(String nmStrut) {
        setObject("nm_strut", nmStrut);
    }

    @Override
    public void entityToRowBean(Object obj) {
        OrgVVisFattura entity = (OrgVVisFattura) obj;
        this.setIdStatoCalc(entity.getOrgVVisFatturaId().getIdStatoCalc());
        this.setIdFatturaEnte(entity.getOrgVVisFatturaId().getIdFatturaEnte());
        this.setNmAmbienteEnteConvenz(entity.getNmAmbienteEnteConvenz());
        this.setIdAmbienteEnteConvenz(entity.getIdAmbienteEnteConvenz());
        this.setIdEnteConvenz(entity.getIdEnteConvenz());
        this.setNmEnteConvenz(entity.getNmEnteConvenz());
        this.setCdEnteConvenz(entity.getCdEnteConvenz());
        this.setTiCdEnteConvenz(entity.getTiCdEnteConvenz());
        this.setCdFisc(entity.getCdFisc());
        this.setCdClienteSap(entity.getCdClienteSap());
        this.setCdCategEnte(entity.getCdCategEnte());
        this.setCdTipoAccordo(entity.getCdTipoAccordo());
        if (entity.getDtDecAccordoInfo() != null) {
            this.setDtDecAccordoInfo(new Timestamp(entity.getDtDecAccordoInfo().getTime()));
        }
        if (entity.getDtDecAccordo() != null) {
            this.setDtDecAccordo(new Timestamp(entity.getDtDecAccordo().getTime()));
        }
        if (entity.getDtScadAccordo() != null) {
            this.setDtScadAccordo(new Timestamp(entity.getDtScadAccordo().getTime()));
        }
        if (entity.getDtFineValidAccordo() != null) {
            this.setDtFineValidAccordo(new Timestamp(entity.getDtFineValidAccordo().getTime()));
        }
        this.setFlPagamento(entity.getFlPagamento());
        this.setNmTariffario(entity.getNmTariffario());
        this.setCdClasseEnteConvenz(entity.getCdClasseEnteConvenz());
        this.setNiAbitanti(entity.getNiAbitanti());
        this.setDsNoteAccordo(entity.getDsNoteAccordo());
        this.setCdUfe(entity.getCdUfe());
        this.setDsUfe(entity.getDsUfe());
        this.setCdIva(entity.getCdIva());
        this.setDsIva(entity.getDsIva());
        this.setCdCig(entity.getCdCig());
        this.setCdCup(entity.getCdCup());
        this.setCdRifContab(entity.getCdRifContab());
        this.setCdCoge(entity.getCdCoge());
        this.setCdCapitolo(entity.getCdCapitolo());
        this.setTiStatoFatturaEnte(entity.getTiStatoFatturaEnte());
        if (entity.getDtRegStatoFatturaEnte() != null) {
            this.setDtRegStatoFatturaEnte(new Timestamp(entity.getDtRegStatoFatturaEnte().getTime()));
        }
        this.setAaFattura(entity.getAaFattura());
        this.setPgFattura(entity.getPgFattura());
        this.setCdFattura(entity.getCdFattura());
        if (entity.getDtCreazione() != null) {
            this.setDtCreazione(new Timestamp(entity.getDtCreazione().getTime()));
        }
        this.setCdRegistroEmisFattura(entity.getCdRegistroEmisFattura());
        this.setAaEmissFattura(entity.getAaEmissFattura());
        this.setCdEmisFattura(entity.getCdEmisFattura());
        if (entity.getDtEmisFattura() != null) {
            this.setDtEmisFattura(new Timestamp(entity.getDtEmisFattura().getTime()));
        }
        if (entity.getDtScadFattura() != null) {
            this.setDtScadFattura(new Timestamp(entity.getDtScadFattura().getTime()));
        }
        this.setNtEmisFattura(entity.getNtEmisFattura());
        this.setCdFatturaSap(entity.getCdFatturaSap());
        this.setImTotNetto(entity.getImTotNetto());
        this.setImTotIva(entity.getImTotIva());
        this.setImTotFattura(entity.getImTotFattura());
        this.setImTotDaPagare(entity.getImTotDaPagare());
        this.setImTotPagam(entity.getImTotPagam());
        this.setFlSuperamentoScaglione(entity.getFlSuperamentoScaglione());
        this.setCdRegistroEmisNotaCredito(entity.getCdRegistroEmisNotaCredito());
        this.setAaEmissNotaCredito(entity.getAaEmissNotaCredito());
        this.setCdEmisNotaCredito(entity.getCdEmisNotaCredito());
        if (entity.getDtEmisNotaCredito() != null) {
            this.setDtEmisNotaCredito(new Timestamp(entity.getDtEmisNotaCredito().getTime()));
        }
        this.setNtEmisNotaCredito(entity.getNtEmisNotaCredito());
        this.setFlDaRiemis(entity.getFlDaRiemis());
        this.setNmAmbiente(entity.getNmAmbiente());
        this.setNmEnte(entity.getNmEnte());
        this.setNmStrut(entity.getNmStrut());
    }

    @Override
    public OrgVVisFattura rowBeanToEntity() {
        OrgVVisFattura entity = new OrgVVisFattura();
        entity.setOrgVVisFatturaId(new OrgVVisFatturaId());
        entity.getOrgVVisFatturaId().setIdStatoCalc(this.getIdStatoCalc());
        entity.getOrgVVisFatturaId().setIdFatturaEnte(this.getIdFatturaEnte());
        entity.setNmAmbienteEnteConvenz(this.getNmAmbienteEnteConvenz());
        entity.setIdAmbienteEnteConvenz(this.getIdAmbienteEnteConvenz());
        entity.setIdEnteConvenz(this.getIdEnteConvenz());
        entity.setNmEnteConvenz(this.getNmEnteConvenz());
        entity.setCdEnteConvenz(this.getCdEnteConvenz());
        entity.setTiCdEnteConvenz(this.getTiCdEnteConvenz());
        entity.setCdFisc(this.getCdFisc());
        entity.setCdClienteSap(this.getCdClienteSap());
        entity.setCdCategEnte(this.getCdCategEnte());
        entity.setCdTipoAccordo(this.getCdTipoAccordo());
        entity.setDtDecAccordoInfo(this.getDtDecAccordoInfo());
        entity.setDtDecAccordo(this.getDtDecAccordo());
        entity.setDtScadAccordo(this.getDtScadAccordo());
        entity.setDtFineValidAccordo(this.getDtFineValidAccordo());
        entity.setFlPagamento(this.getFlPagamento());
        entity.setNmTariffario(this.getNmTariffario());
        entity.setCdClasseEnteConvenz(this.getCdClasseEnteConvenz());
        entity.setNiAbitanti(this.getNiAbitanti());
        entity.setDsNoteAccordo(this.getDsNoteAccordo());
        entity.setCdUfe(this.getCdUfe());
        entity.setDsUfe(this.getDsUfe());
        entity.setCdIva(this.getCdIva());
        entity.setDsIva(this.getDsIva());
        entity.setCdCig(this.getCdCig());
        entity.setCdCup(this.getCdCup());
        entity.setCdRifContab(this.getCdRifContab());
        entity.setCdCoge(this.getCdCoge());
        entity.setCdCapitolo(this.getCdCapitolo());
        entity.setTiStatoFatturaEnte(this.getTiStatoFatturaEnte());
        entity.setDtRegStatoFatturaEnte(this.getDtRegStatoFatturaEnte());
        entity.setAaFattura(this.getAaFattura());
        entity.setPgFattura(this.getPgFattura());
        entity.setCdFattura(this.getCdFattura());
        entity.setDtCreazione(this.getDtCreazione());
        entity.setCdRegistroEmisFattura(this.getCdRegistroEmisFattura());
        entity.setAaEmissFattura(this.getAaEmissFattura());
        entity.setCdEmisFattura(this.getCdEmisFattura());
        entity.setDtEmisFattura(this.getDtEmisFattura());
        entity.setDtScadFattura(this.getDtScadFattura());
        entity.setNtEmisFattura(this.getNtEmisFattura());
        entity.setCdFatturaSap(this.getCdFatturaSap());
        entity.setImTotNetto(this.getImTotNetto());
        entity.setImTotIva(this.getImTotIva());
        entity.setImTotFattura(this.getImTotFattura());
        entity.setImTotDaPagare(this.getImTotDaPagare());
        entity.setImTotPagam(this.getImTotPagam());
        entity.setFlSuperamentoScaglione(this.getFlSuperamentoScaglione());
        entity.setCdRegistroEmisNotaCredito(this.getCdRegistroEmisNotaCredito());
        entity.setAaEmissNotaCredito(this.getAaEmissNotaCredito());
        entity.setCdEmisNotaCredito(this.getCdEmisNotaCredito());
        entity.setDtEmisNotaCredito(this.getDtEmisNotaCredito());
        entity.setNtEmisNotaCredito(this.getNtEmisNotaCredito());
        entity.setFlDaRiemis(this.getFlDaRiemis());
        entity.setNmAmbiente(this.getNmAmbiente());
        entity.setNmEnte(this.getNmEnte());
        entity.setNmStrut(this.getNmStrut());
        return entity;
    }

    // gestione della paginazione
    public void setRownum(Integer rownum) {
        setObject("rownum", rownum);
    }

    public Integer getRownum() {
        return Integer.parseInt(getObject("rownum").toString());
    }

    public void setRnum(Integer rnum) {
        setObject("rnum", rnum);
    }

    public Integer getRnum() {
        return Integer.parseInt(getObject("rnum").toString());
    }

    public void setNumrecords(Integer numRecords) {
        setObject("numrecords", numRecords);
    }

    public Integer getNumrecords() {
        return Integer.parseInt(getObject("numrecords").toString());
    }

}
