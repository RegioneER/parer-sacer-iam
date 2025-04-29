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

package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the ORG_V_VIS_FATTURA database table.
 *
 */
@Entity
@Table(name = "ORG_V_VIS_FATTURA")
public class OrgVVisFattura implements Serializable {
    private static final long serialVersionUID = 1L;
    private BigDecimal aaEmissFattura;
    private BigDecimal aaEmissNotaCredito;
    private BigDecimal aaFattura;
    private String cdCapitolo;
    private String cdCategEnte;
    private String cdCig;
    private String cdClasseEnteConvenz;
    private String cdClienteSap;
    private String cdCoge;
    private String cdCup;
    private String cdEmisFattura;
    private String cdEmisNotaCredito;
    private String cdEnteConvenz;
    private String cdFattura;
    private String cdFatturaSap;
    private String cdIva;
    private String cdFisc;
    private String cdRegistroEmisFattura;
    private String cdRegistroEmisNotaCredito;
    private String cdRifContab;
    private String cdTipoAccordo;
    private String cdUfe;
    private String dsIva;
    private String dsNoteAccordo;
    private String dsUfe;
    private Date dtCreazione;
    private Date dtDecAccordoInfo;
    private Date dtDecAccordo;
    private Date dtEmisFattura;
    private Date dtEmisNotaCredito;
    private Date dtRegStatoFatturaEnte;
    private Date dtScadAccordo;
    private Date dtFineValidAccordo;
    private Date dtScadFattura;
    private String flDaRiemis;
    private String flPagamento;
    private String flSuperamentoScaglione;
    private BigDecimal idAmbienteEnteConvenz;
    private BigDecimal idEnteConvenz;
    private BigDecimal imTotDaPagare;
    private BigDecimal imTotFattura;
    private BigDecimal imTotIva;
    private BigDecimal imTotNetto;
    private BigDecimal imTotPagam;
    private BigDecimal niAbitanti;
    private String nmAmbienteEnteConvenz;
    private String nmEnteConvenz;
    private String nmTariffario;
    private String ntEmisFattura;
    private String ntEmisNotaCredito;
    private BigDecimal pgFattura;
    private String tiCdEnteConvenz;
    private String tiStatoFatturaEnte;
    private String nmAmbiente;
    private String nmEnte;
    private String nmStrut;
    private OrgVVisFatturaId orgVVisFatturaId;

    public OrgVVisFattura() {
	// document why this constructor is empty
    }

    @Column(name = "AA_EMISS_FATTURA")
    public BigDecimal getAaEmissFattura() {
	return this.aaEmissFattura;
    }

    public void setAaEmissFattura(BigDecimal aaEmissFattura) {
	this.aaEmissFattura = aaEmissFattura;
    }

    @Column(name = "AA_EMISS_NOTA_CREDITO")
    public BigDecimal getAaEmissNotaCredito() {
	return this.aaEmissNotaCredito;
    }

    public void setAaEmissNotaCredito(BigDecimal aaEmissNotaCredito) {
	this.aaEmissNotaCredito = aaEmissNotaCredito;
    }

    @Column(name = "AA_FATTURA")
    public BigDecimal getAaFattura() {
	return this.aaFattura;
    }

    public void setAaFattura(BigDecimal aaFattura) {
	this.aaFattura = aaFattura;
    }

    @Column(name = "CD_CAPITOLO")
    public String getCdCapitolo() {
	return this.cdCapitolo;
    }

    public void setCdCapitolo(String cdCapitolo) {
	this.cdCapitolo = cdCapitolo;
    }

    @Column(name = "CD_CATEG_ENTE")
    public String getCdCategEnte() {
	return this.cdCategEnte;
    }

    public void setCdCategEnte(String cdCategEnte) {
	this.cdCategEnte = cdCategEnte;
    }

    @Column(name = "CD_CIG")
    public String getCdCig() {
	return this.cdCig;
    }

    public void setCdCig(String cdCig) {
	this.cdCig = cdCig;
    }

    @Column(name = "CD_CLASSE_ENTE_CONVENZ")
    public String getCdClasseEnteConvenz() {
	return this.cdClasseEnteConvenz;
    }

    public void setCdClasseEnteConvenz(String cdClasseEnteConvenz) {
	this.cdClasseEnteConvenz = cdClasseEnteConvenz;
    }

    @Column(name = "CD_CLIENTE_SAP")
    public String getCdClienteSap() {
	return this.cdClienteSap;
    }

    public void setCdClienteSap(String cdClienteSap) {
	this.cdClienteSap = cdClienteSap;
    }

    @Column(name = "CD_COGE")
    public String getCdCoge() {
	return this.cdCoge;
    }

    public void setCdCoge(String cdCoge) {
	this.cdCoge = cdCoge;
    }

    @Column(name = "CD_CUP")
    public String getCdCup() {
	return this.cdCup;
    }

    public void setCdCup(String cdCup) {
	this.cdCup = cdCup;
    }

    @Column(name = "CD_EMIS_FATTURA")
    public String getCdEmisFattura() {
	return this.cdEmisFattura;
    }

    public void setCdEmisFattura(String cdEmisFattura) {
	this.cdEmisFattura = cdEmisFattura;
    }

    @Column(name = "CD_EMIS_NOTA_CREDITO")
    public String getCdEmisNotaCredito() {
	return this.cdEmisNotaCredito;
    }

    public void setCdEmisNotaCredito(String cdEmisNotaCredito) {
	this.cdEmisNotaCredito = cdEmisNotaCredito;
    }

    @Column(name = "CD_ENTE_CONVENZ")
    public String getCdEnteConvenz() {
	return this.cdEnteConvenz;
    }

    public void setCdEnteConvenz(String cdEnteConvenz) {
	this.cdEnteConvenz = cdEnteConvenz;
    }

    @Column(name = "CD_FATTURA")
    public String getCdFattura() {
	return this.cdFattura;
    }

    public void setCdFattura(String cdFattura) {
	this.cdFattura = cdFattura;
    }

    @Column(name = "CD_FATTURA_SAP")
    public String getCdFatturaSap() {
	return this.cdFatturaSap;
    }

    public void setCdFatturaSap(String cdFatturaSap) {
	this.cdFatturaSap = cdFatturaSap;
    }

    @Column(name = "CD_IVA")
    public String getCdIva() {
	return this.cdIva;
    }

    public void setCdIva(String cdIva) {
	this.cdIva = cdIva;
    }

    @Column(name = "CD_FISC")
    public String getCdFisc() {
	return this.cdFisc;
    }

    public void setCdFisc(String cdFisc) {
	this.cdFisc = cdFisc;
    }

    @Column(name = "CD_REGISTRO_EMIS_FATTURA")
    public String getCdRegistroEmisFattura() {
	return this.cdRegistroEmisFattura;
    }

    public void setCdRegistroEmisFattura(String cdRegistroEmisFattura) {
	this.cdRegistroEmisFattura = cdRegistroEmisFattura;
    }

    @Column(name = "CD_REGISTRO_EMIS_NOTA_CREDITO")
    public String getCdRegistroEmisNotaCredito() {
	return this.cdRegistroEmisNotaCredito;
    }

    public void setCdRegistroEmisNotaCredito(String cdRegistroEmisNotaCredito) {
	this.cdRegistroEmisNotaCredito = cdRegistroEmisNotaCredito;
    }

    @Column(name = "CD_RIF_CONTAB")
    public String getCdRifContab() {
	return this.cdRifContab;
    }

    public void setCdRifContab(String cdRifContab) {
	this.cdRifContab = cdRifContab;
    }

    @Column(name = "CD_TIPO_ACCORDO")
    public String getCdTipoAccordo() {
	return this.cdTipoAccordo;
    }

    public void setCdTipoAccordo(String cdTipoAccordo) {
	this.cdTipoAccordo = cdTipoAccordo;
    }

    @Column(name = "CD_UFE")
    public String getCdUfe() {
	return this.cdUfe;
    }

    public void setCdUfe(String cdUfe) {
	this.cdUfe = cdUfe;
    }

    @Column(name = "DS_IVA")
    public String getDsIva() {
	return this.dsIva;
    }

    public void setDsIva(String dsIva) {
	this.dsIva = dsIva;
    }

    @Column(name = "DS_NOTE_ACCORDO")
    public String getDsNoteAccordo() {
	return this.dsNoteAccordo;
    }

    public void setDsNoteAccordo(String dsNoteAccordo) {
	this.dsNoteAccordo = dsNoteAccordo;
    }

    @Column(name = "DS_UFE")
    public String getDsUfe() {
	return this.dsUfe;
    }

    public void setDsUfe(String dsUfe) {
	this.dsUfe = dsUfe;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_CREAZIONE")
    public Date getDtCreazione() {
	return this.dtCreazione;
    }

    public void setDtCreazione(Date dtCreazione) {
	this.dtCreazione = dtCreazione;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_DEC_ACCORDO_INFO")
    public Date getDtDecAccordoInfo() {
	return this.dtDecAccordoInfo;
    }

    public void setDtDecAccordoInfo(Date dtDecAccordoInfo) {
	this.dtDecAccordoInfo = dtDecAccordoInfo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_DEC_ACCORDO")
    public Date getDtDecAccordo() {
	return this.dtDecAccordo;
    }

    public void setDtDecAccordo(Date dtDecAccordo) {
	this.dtDecAccordo = dtDecAccordo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_EMIS_FATTURA")
    public Date getDtEmisFattura() {
	return this.dtEmisFattura;
    }

    public void setDtEmisFattura(Date dtEmisFattura) {
	this.dtEmisFattura = dtEmisFattura;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_EMIS_NOTA_CREDITO")
    public Date getDtEmisNotaCredito() {
	return this.dtEmisNotaCredito;
    }

    public void setDtEmisNotaCredito(Date dtEmisNotaCredito) {
	this.dtEmisNotaCredito = dtEmisNotaCredito;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_REG_STATO_FATTURA_ENTE")
    public Date getDtRegStatoFatturaEnte() {
	return this.dtRegStatoFatturaEnte;
    }

    public void setDtRegStatoFatturaEnte(Date dtRegStatoFatturaEnte) {
	this.dtRegStatoFatturaEnte = dtRegStatoFatturaEnte;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_SCAD_ACCORDO")
    public Date getDtScadAccordo() {
	return this.dtScadAccordo;
    }

    public void setDtScadAccordo(Date dtScadAccordo) {
	this.dtScadAccordo = dtScadAccordo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FINE_VALID_ACCORDO")
    public Date getDtFineValidAccordo() {
	return this.dtFineValidAccordo;
    }

    public void setDtFineValidAccordo(Date dtFineValidAccordo) {
	this.dtFineValidAccordo = dtFineValidAccordo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_SCAD_FATTURA")
    public Date getDtScadFattura() {
	return this.dtScadFattura;
    }

    public void setDtScadFattura(Date dtScadFattura) {
	this.dtScadFattura = dtScadFattura;
    }

    @Column(name = "FL_DA_RIEMIS", columnDefinition = "char(1)")
    public String getFlDaRiemis() {
	return this.flDaRiemis;
    }

    public void setFlDaRiemis(String flDaRiemis) {
	this.flDaRiemis = flDaRiemis;
    }

    @Column(name = "FL_PAGAMENTO", columnDefinition = "char(1)")
    public String getFlPagamento() {
	return this.flPagamento;
    }

    public void setFlPagamento(String flPagamento) {
	this.flPagamento = flPagamento;
    }

    @Column(name = "FL_SUPERAMENTO_SCAGLIONE", columnDefinition = "char(1)")
    public String getFlSuperamentoScaglione() {
	return this.flSuperamentoScaglione;
    }

    public void setFlSuperamentoScaglione(String flSuperamentoScaglione) {
	this.flSuperamentoScaglione = flSuperamentoScaglione;
    }

    @Column(name = "ID_AMBIENTE_ENTE_CONVENZ")
    public BigDecimal getIdAmbienteEnteConvenz() {
	return this.idAmbienteEnteConvenz;
    }

    public void setIdAmbienteEnteConvenz(BigDecimal idAmbienteEnteConvenz) {
	this.idAmbienteEnteConvenz = idAmbienteEnteConvenz;
    }

    @Column(name = "ID_ENTE_CONVENZ")
    public BigDecimal getIdEnteConvenz() {
	return this.idEnteConvenz;
    }

    public void setIdEnteConvenz(BigDecimal idEnteConvenz) {
	this.idEnteConvenz = idEnteConvenz;
    }

    @Column(name = "IM_TOT_DA_PAGARE")
    public BigDecimal getImTotDaPagare() {
	return this.imTotDaPagare;
    }

    public void setImTotDaPagare(BigDecimal imTotDaPagare) {
	this.imTotDaPagare = imTotDaPagare;
    }

    @Column(name = "IM_TOT_FATTURA")
    public BigDecimal getImTotFattura() {
	return this.imTotFattura;
    }

    public void setImTotFattura(BigDecimal imTotFattura) {
	this.imTotFattura = imTotFattura;
    }

    @Column(name = "IM_TOT_IVA")
    public BigDecimal getImTotIva() {
	return this.imTotIva;
    }

    public void setImTotIva(BigDecimal imTotIva) {
	this.imTotIva = imTotIva;
    }

    @Column(name = "IM_TOT_NETTO")
    public BigDecimal getImTotNetto() {
	return this.imTotNetto;
    }

    public void setImTotNetto(BigDecimal imTotNetto) {
	this.imTotNetto = imTotNetto;
    }

    @Column(name = "IM_TOT_PAGAM")
    public BigDecimal getImTotPagam() {
	return this.imTotPagam;
    }

    public void setImTotPagam(BigDecimal imTotPagam) {
	this.imTotPagam = imTotPagam;
    }

    @Column(name = "NI_ABITANTI")
    public BigDecimal getNiAbitanti() {
	return this.niAbitanti;
    }

    public void setNiAbitanti(BigDecimal niAbitanti) {
	this.niAbitanti = niAbitanti;
    }

    @Column(name = "NM_AMBIENTE_ENTE_CONVENZ")
    public String getNmAmbienteEnteConvenz() {
	return this.nmAmbienteEnteConvenz;
    }

    public void setNmAmbienteEnteConvenz(String nmAmbienteEnteConvenz) {
	this.nmAmbienteEnteConvenz = nmAmbienteEnteConvenz;
    }

    @Column(name = "NM_ENTE_CONVENZ")
    public String getNmEnteConvenz() {
	return this.nmEnteConvenz;
    }

    public void setNmEnteConvenz(String nmEnteConvenz) {
	this.nmEnteConvenz = nmEnteConvenz;
    }

    @Column(name = "NM_TARIFFARIO")
    public String getNmTariffario() {
	return this.nmTariffario;
    }

    public void setNmTariffario(String nmTariffario) {
	this.nmTariffario = nmTariffario;
    }

    @Column(name = "NT_EMIS_FATTURA")
    public String getNtEmisFattura() {
	return this.ntEmisFattura;
    }

    public void setNtEmisFattura(String ntEmisFattura) {
	this.ntEmisFattura = ntEmisFattura;
    }

    @Column(name = "NT_EMIS_NOTA_CREDITO")
    public String getNtEmisNotaCredito() {
	return this.ntEmisNotaCredito;
    }

    public void setNtEmisNotaCredito(String ntEmisNotaCredito) {
	this.ntEmisNotaCredito = ntEmisNotaCredito;
    }

    @Column(name = "PG_FATTURA")
    public BigDecimal getPgFattura() {
	return this.pgFattura;
    }

    public void setPgFattura(BigDecimal pgFattura) {
	this.pgFattura = pgFattura;
    }

    @Column(name = "TI_CD_ENTE_CONVENZ")
    public String getTiCdEnteConvenz() {
	return this.tiCdEnteConvenz;
    }

    public void setTiCdEnteConvenz(String tiCdEnteConvenz) {
	this.tiCdEnteConvenz = tiCdEnteConvenz;
    }

    @Column(name = "TI_STATO_FATTURA_ENTE")
    public String getTiStatoFatturaEnte() {
	return this.tiStatoFatturaEnte;
    }

    public void setTiStatoFatturaEnte(String tiStatoFatturaEnte) {
	this.tiStatoFatturaEnte = tiStatoFatturaEnte;
    }

    @Column(name = "NM_AMBIENTE")
    public String getNmAmbiente() {
	return nmAmbiente;
    }

    public void setNmAmbiente(String nmAmbiente) {
	this.nmAmbiente = nmAmbiente;
    }

    @Column(name = "NM_ENTE")
    public String getNmEnte() {
	return nmEnte;
    }

    public void setNmEnte(String nmEnte) {
	this.nmEnte = nmEnte;
    }

    @Column(name = "NM_STRUT")
    public String getNmStrut() {
	return nmStrut;
    }

    public void setNmStrut(String nmStrut) {
	this.nmStrut = nmStrut;
    }

    @EmbeddedId()
    public OrgVVisFatturaId getOrgVVisFatturaId() {
	return orgVVisFatturaId;
    }

    public void setOrgVVisFatturaId(OrgVVisFatturaId orgVVisFatturaId) {
	this.orgVVisFatturaId = orgVVisFatturaId;
    }

}
