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

package it.eng.saceriam.gestioneFatturazioneServizi.dto;

import java.math.BigDecimal;
import java.util.Date;

import it.eng.saceriam.slite.gen.form.GestioneFatturazioneServiziForm;
import it.eng.spagoCore.error.EMFError;

/**
 *
 * @author Gilioli_P
 */
public class FatturaBean {

    private BigDecimal aaFattura;
    private BigDecimal pgFattura;
    private String cdFattura;
    private String cdRegistroEmisFattura;
    private BigDecimal aaEmissFattura;
    private String cdEmisFattura;
    private Date dtEmisFattura;
    private Date dtScadFattura;
    private String cdFatturaSap;
    private String ntEmisFattura;
    private BigDecimal imTotFattura;
    private BigDecimal imTotDaPagare;
    private String cdRegistroEmisNotaCredito;
    private BigDecimal aaEmissNotaCredito;
    private String cdEmisNotaCredito;
    private Date dtEmisNotaCredito;
    private String flDaRiemis;
    private String ntEmisNotaCredito;
    private String tiStatoFatturaEnte;
    private BigDecimal idFatturaEnte;
    private BigDecimal imTotIva;
    private String nmAmbiente;
    private String nmEnte;
    private String nmStrut;

    public FatturaBean(GestioneFatturazioneServiziForm.FatturaDetail detail) throws EMFError {
	this.aaFattura = detail.getAa_fattura().parse();
	this.pgFattura = detail.getPg_fattura().parse();
	this.cdFattura = detail.getCd_fattura().parse();
	this.cdRegistroEmisFattura = detail.getCd_registro_emis_fattura_combo().parse();
	this.aaEmissFattura = detail.getAa_emiss_fattura().parse();
	this.cdEmisFattura = detail.getCd_emis_fattura().parse();
	this.dtEmisFattura = detail.getDt_emis_fattura().parse();
	this.dtScadFattura = detail.getDt_scad_fattura().parse();
	this.cdFatturaSap = detail.getCd_fattura_sap().parse();
	this.ntEmisFattura = detail.getNt_emis_fattura().parse();
	this.imTotFattura = detail.getIm_tot_fattura().parse();
	this.imTotDaPagare = detail.getIm_tot_da_pagare().parse();
	this.cdRegistroEmisNotaCredito = detail.getCd_registro_emis_nota_credito_combo().parse();
	this.aaEmissNotaCredito = detail.getAa_emiss_nota_credito().parse();
	this.cdEmisNotaCredito = detail.getCd_emis_nota_credito().parse();
	this.dtEmisNotaCredito = detail.getDt_emis_nota_credito().parse();
	this.flDaRiemis = detail.getFl_da_riemis().parse();
	this.ntEmisNotaCredito = detail.getNt_emis_nota_credito().parse();
	this.tiStatoFatturaEnte = detail.getTi_stato_fattura_ente().parse();
	this.idFatturaEnte = detail.getId_fattura_ente().parse();
	this.imTotIva = detail.getIm_tot_iva().parse();
	// this.nmAmbiente = detail.getNm_ambiente().parse();
	this.nmEnte = detail.getNm_ente().parse();
	this.nmStrut = detail.getNm_strut().parse();
    }

    public BigDecimal getIdFatturaEnte() {
	return idFatturaEnte;
    }

    public void setIdFatturaEnte(BigDecimal idFatturaEnte) {
	this.idFatturaEnte = idFatturaEnte;
    }

    public String getTiStatoFatturaEnte() {
	return tiStatoFatturaEnte;
    }

    public void setTiStatoFatturaEnte(String tiStatoFatturaEnte) {
	this.tiStatoFatturaEnte = tiStatoFatturaEnte;
    }

    public BigDecimal getAaFattura() {
	return aaFattura;
    }

    public void setAaFattura(BigDecimal aaFattura) {
	this.aaFattura = aaFattura;
    }

    public BigDecimal getPgFattura() {
	return pgFattura;
    }

    public void setPgFattura(BigDecimal pgFattura) {
	this.pgFattura = pgFattura;
    }

    public String getCdFattura() {
	return cdFattura;
    }

    public void setCdFattura(String cdFattura) {
	this.cdFattura = cdFattura;
    }

    public String getCdRegistroEmisFattura() {
	return cdRegistroEmisFattura;
    }

    public void setCdRegistroEmisFattura(String cdRegistroEmisFattura) {
	this.cdRegistroEmisFattura = cdRegistroEmisFattura;
    }

    public BigDecimal getAaEmissFattura() {
	return aaEmissFattura;
    }

    public void setAaEmissFattura(BigDecimal aaEmissFattura) {
	this.aaEmissFattura = aaEmissFattura;
    }

    public String getCdEmisFattura() {
	return cdEmisFattura;
    }

    public void setCdEmisFattura(String cdEmisFattura) {
	this.cdEmisFattura = cdEmisFattura;
    }

    public Date getDtEmisFattura() {
	return dtEmisFattura;
    }

    public void setDtEmisFattura(Date dtEmisFattura) {
	this.dtEmisFattura = dtEmisFattura;
    }

    public Date getDtScadFattura() {
	return dtScadFattura;
    }

    public void setDtScadFattura(Date dtScadFattura) {
	this.dtScadFattura = dtScadFattura;
    }

    public String getCdFatturaSap() {
	return cdFatturaSap;
    }

    public void setCdFatturaSap(String cdFatturaSap) {
	this.cdFatturaSap = cdFatturaSap;
    }

    public String getNtEmisFattura() {
	return ntEmisFattura;
    }

    public void setNtEmisFattura(String ntEmisFattura) {
	this.ntEmisFattura = ntEmisFattura;
    }

    public BigDecimal getImTotFattura() {
	return imTotFattura;
    }

    public void setImTotFattura(BigDecimal imTotFattura) {
	this.imTotFattura = imTotFattura;
    }

    public BigDecimal getImTotDaPagare() {
	return imTotDaPagare;
    }

    public void setImTotDaPagare(BigDecimal imTotDaPagare) {
	this.imTotDaPagare = imTotDaPagare;
    }

    public String getCdRegistroEmisNotaCredito() {
	return cdRegistroEmisNotaCredito;
    }

    public void setCdRegistroEmisNotaCredito(String cdRegistroEmisNotaCredito) {
	this.cdRegistroEmisNotaCredito = cdRegistroEmisNotaCredito;
    }

    public BigDecimal getAaEmissNotaCredito() {
	return aaEmissNotaCredito;
    }

    public void setAaEmissNotaCredito(BigDecimal aaEmissNotaCredito) {
	this.aaEmissNotaCredito = aaEmissNotaCredito;
    }

    public String getCdEmisNotaCredito() {
	return cdEmisNotaCredito;
    }

    public void setCdEmisNotaCredito(String cdEmisNotaCredito) {
	this.cdEmisNotaCredito = cdEmisNotaCredito;
    }

    public Date getDtEmisNotaCredito() {
	return dtEmisNotaCredito;
    }

    public void setDtEmisNotaCredito(Date dtEmisNotaCredito) {
	this.dtEmisNotaCredito = dtEmisNotaCredito;
    }

    public String getFlDaRiemis() {
	return flDaRiemis;
    }

    public void setFlDaRiemis(String flDaRiemis) {
	this.flDaRiemis = flDaRiemis;
    }

    public String getNtEmisNotaCredito() {
	return ntEmisNotaCredito;
    }

    public void setNtEmisNotaCredito(String ntEmisNotaCredito) {
	this.ntEmisNotaCredito = ntEmisNotaCredito;
    }

    public BigDecimal getImTotIva() {
	return imTotIva;
    }

    public void setImTotIva(BigDecimal imTotIva) {
	this.imTotIva = imTotIva;
    }

    public String getNmAmbiente() {
	return nmAmbiente;
    }

    public void setNmAmbiente(String nmAmbiente) {
	this.nmAmbiente = nmAmbiente;
    }

    public String getNmEnte() {
	return nmEnte;
    }

    public void setNmEnte(String nmEnte) {
	this.nmEnte = nmEnte;
    }

    public String getNmStrut() {
	return nmStrut;
    }

    public void setNmStrut(String nmStrut) {
	this.nmStrut = nmStrut;
    }

}
