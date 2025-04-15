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

package it.eng.saceriam.amministrazioneEntiConvenzionati.dto;

import java.math.BigDecimal;
import java.util.Date;

import it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm;
import it.eng.spagoCore.error.EMFError;

/**
 *
 * @author Bonora_L
 */
public class EnteConvenzionatoBean {

    private String nm_ente_convenz;
    private BigDecimal id_ambiente_ente_convenz;
    private Date dt_ini_val;
    private Date dt_cessazione;
    private Date dt_ini_val_appart_ambiente;
    private Date dt_fin_val_appart_ambiente;
    private Date dt_rich_modulo_info;
    private String ds_via_sede_legale;
    private String cd_ente_convenz;
    private String ti_cd_ente_convenz;
    private String cd_nazione_sede_legale;
    private BigDecimal id_prov_sede_legale;
    private String cd_cap_sede_legale;
    private String ds_citta_sede_legale;
    private String cd_fisc;
    private BigDecimal ds_categ_ente;
    private BigDecimal cd_ambito_territ_regione;
    private BigDecimal cd_ambito_territ_provincia;
    private BigDecimal cd_ambito_territ_forma_associata;
    private BigDecimal id_ente_convenz_nuovo;
    private String info_message;
    private String cd_ufe;
    private String ds_ufe;
    private BigDecimal id_cd_iva;
    private String ti_mod_pagam;
    private String ds_note;

    public EnteConvenzionatoBean(
	    AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail detail) throws EMFError {
	this.nm_ente_convenz = detail.getNm_ente_siam().parse();
	this.id_ambiente_ente_convenz = detail.getId_ambiente_ente_convenz().parse();
	this.dt_ini_val = detail.getDt_ini_val().parse();
	this.dt_cessazione = detail.getDt_cessazione().parse();
	this.ds_via_sede_legale = detail.getDs_via_sede_legale().parse();
	this.cd_cap_sede_legale = detail.getCd_cap_sede_legale().parse();
	this.ds_citta_sede_legale = detail.getDs_citta_sede_legale().parse();
	this.cd_fisc = detail.getCd_fisc().parse();
	this.ds_categ_ente = detail.getDs_categ_ente().parse();
	this.cd_ambito_territ_regione = detail.getCd_ambito_territ_regione().parse();
	this.cd_ambito_territ_provincia = detail.getCd_ambito_territ_provincia().parse();
	this.cd_ambito_territ_forma_associata = detail.getCd_ambito_territ_forma_associata()
		.parse();
	this.id_ente_convenz_nuovo = detail.getId_ente_convenz_nuovo().parse();
	this.cd_ente_convenz = detail.getCd_ente_convenz().parse();
	this.ti_cd_ente_convenz = detail.getTi_cd_ente_convenz().parse();
	this.cd_nazione_sede_legale = detail.getCd_nazione_sede_legale().parse();
	this.id_prov_sede_legale = detail.getId_prov_sede_legale().parse();
	this.dt_rich_modulo_info = detail.getDt_rich_modulo_info().parse();
	this.dt_ini_val_appart_ambiente = detail.getDt_ini_val_appart_ambiente().parse();
	this.dt_fin_val_appart_ambiente = detail.getDt_fin_val_appart_ambiente().parse();
	this.cd_ufe = detail.getCd_ufe().parse();
	this.ds_ufe = detail.getDs_ufe().parse();
	this.id_cd_iva = detail.getId_cd_iva().parse();
	this.ti_mod_pagam = detail.getTi_mod_pagam().parse();
	this.ds_note = detail.getDs_note().parse();
    }

    public EnteConvenzionatoBean(
	    AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail wizardDetail)
	    throws EMFError {
	this.nm_ente_convenz = wizardDetail.getNm_ente_siam().parse();
	this.id_ambiente_ente_convenz = wizardDetail.getId_ambiente_ente_convenz().parse();
	this.dt_ini_val = wizardDetail.getDt_ini_val().parse();
	this.dt_cessazione = wizardDetail.getDt_cessazione().parse();
	this.ds_via_sede_legale = wizardDetail.getDs_via_sede_legale().parse();
	this.cd_cap_sede_legale = wizardDetail.getCd_cap_sede_legale().parse();
	this.ds_citta_sede_legale = wizardDetail.getDs_citta_sede_legale().parse();
	this.cd_fisc = wizardDetail.getCd_fisc().parse();
	this.ds_categ_ente = wizardDetail.getDs_categ_ente().parse();
	this.cd_ambito_territ_regione = wizardDetail.getCd_ambito_territ_regione().parse();
	this.cd_ambito_territ_provincia = wizardDetail.getCd_ambito_territ_provincia().parse();
	this.cd_ambito_territ_forma_associata = wizardDetail.getCd_ambito_territ_forma_associata()
		.parse();
	// MEV#19333
	// this.id_ente_convenz_nuovo = wizardDetail.getId_ente_convenz_nuovo().parse();
	// end MEV#19333
	this.cd_ente_convenz = wizardDetail.getCd_ente_convenz().parse();
	this.ti_cd_ente_convenz = wizardDetail.getTi_cd_ente_convenz().parse();
	this.cd_nazione_sede_legale = wizardDetail.getCd_nazione_sede_legale().parse();
	this.id_prov_sede_legale = wizardDetail.getId_prov_sede_legale().parse();
	this.dt_rich_modulo_info = wizardDetail.getDt_rich_modulo_info().parse();
	this.dt_ini_val_appart_ambiente = wizardDetail.getDt_ini_val_appart_ambiente().parse();
	this.dt_fin_val_appart_ambiente = wizardDetail.getDt_fin_val_appart_ambiente().parse();
	this.cd_ufe = wizardDetail.getCd_ufe().parse();
	this.ds_ufe = wizardDetail.getDs_ufe().parse();
	this.id_cd_iva = wizardDetail.getId_cd_iva().parse();
	this.ti_mod_pagam = wizardDetail.getTi_mod_pagam().parse();
	this.ds_note = wizardDetail.getDs_note().parse();
    }

    public String getNm_ente_convenz() {
	return nm_ente_convenz;
    }

    public void setNm_ente_convenz(String nm_ente_convenz) {
	this.nm_ente_convenz = nm_ente_convenz;
    }

    public BigDecimal getId_ambiente_ente_convenz() {
	return id_ambiente_ente_convenz;
    }

    public void setId_ambiente_ente_convenz(BigDecimal id_ambiente_ente_convenz) {
	this.id_ambiente_ente_convenz = id_ambiente_ente_convenz;
    }

    public Date getDt_ini_val() {
	return dt_ini_val;
    }

    public void setDt_ini_val(Date dt_ini_val) {
	this.dt_ini_val = dt_ini_val;
    }

    public String getDs_via_sede_legale() {
	return ds_via_sede_legale;
    }

    public void setDs_via_sede_legale(String ds_via_sede_legale) {
	this.ds_via_sede_legale = ds_via_sede_legale;
    }

    public Date getDt_rich_modulo_info() {
	return dt_rich_modulo_info;
    }

    public void setDt_rich_modulo_info(Date dt_rich_modulo_info) {
	this.dt_rich_modulo_info = dt_rich_modulo_info;
    }

    public String getCd_cap_sede_legale() {
	return cd_cap_sede_legale;
    }

    public void setCd_cap_sede_legale(String cd_cap_sede_legale) {
	this.cd_cap_sede_legale = cd_cap_sede_legale;
    }

    public String getDs_citta_sede_legale() {
	return ds_citta_sede_legale;
    }

    public void setDs_citta_sede_legale(String ds_citta_sede_legale) {
	this.ds_citta_sede_legale = ds_citta_sede_legale;
    }

    public String getCd_fisc() {
	return cd_fisc;
    }

    public void setCd_fisc(String cd_fisc) {
	this.cd_fisc = cd_fisc;
    }

    public BigDecimal getDs_categ_ente() {
	return ds_categ_ente;
    }

    public void setDs_categ_ente(BigDecimal ds_categ_ente) {
	this.ds_categ_ente = ds_categ_ente;
    }

    public BigDecimal getCd_ambito_territ_regione() {
	return cd_ambito_territ_regione;
    }

    public void setCd_ambito_territ_regione(BigDecimal cd_ambito_territ_regione) {
	this.cd_ambito_territ_regione = cd_ambito_territ_regione;
    }

    public BigDecimal getCd_ambito_territ_provincia() {
	return cd_ambito_territ_provincia;
    }

    public void setCd_ambito_territ_provincia(BigDecimal cd_ambito_territ_provincia) {
	this.cd_ambito_territ_provincia = cd_ambito_territ_provincia;
    }

    public BigDecimal getCd_ambito_territ_forma_associata() {
	return cd_ambito_territ_forma_associata;
    }

    public void setCd_ambito_territ_forma_associata(BigDecimal cd_ambito_territ_forma_associata) {
	this.cd_ambito_territ_forma_associata = cd_ambito_territ_forma_associata;
    }

    public BigDecimal getId_ente_convenz_nuovo() {
	return id_ente_convenz_nuovo;
    }

    public void setId_ente_convenz_nuovo(BigDecimal id_ente_convenz_nuovo) {
	this.id_ente_convenz_nuovo = id_ente_convenz_nuovo;
    }

    public String getCd_ente_convenz() {
	return cd_ente_convenz;
    }

    public void setCd_ente_convenz(String cd_ente_convenz) {
	this.cd_ente_convenz = cd_ente_convenz;
    }

    public String getTi_cd_ente_convenz() {
	return ti_cd_ente_convenz;
    }

    public void setTi_cd_ente_convenz(String ti_cd_ente_convenz) {
	this.ti_cd_ente_convenz = ti_cd_ente_convenz;
    }

    public String getCd_nazione_sede_legale() {
	return cd_nazione_sede_legale;
    }

    public void setCd_nazione_sede_legale(String cd_nazione_sede_legale) {
	this.cd_nazione_sede_legale = cd_nazione_sede_legale;
    }

    public BigDecimal getId_prov_sede_legale() {
	return id_prov_sede_legale;
    }

    public void setId_prov_sede_legale(BigDecimal id_prov_sede_legale) {
	this.id_prov_sede_legale = id_prov_sede_legale;
    }

    public Date getDt_cessazione() {
	return dt_cessazione;
    }

    public void setDt_cessazione(Date dt_cessazione) {
	this.dt_cessazione = dt_cessazione;
    }

    public Date getDt_ini_val_appart_ambiente() {
	return dt_ini_val_appart_ambiente;
    }

    public void setDt_ini_val_appart_ambiente(Date dt_ini_val_appart_ambiente) {
	this.dt_ini_val_appart_ambiente = dt_ini_val_appart_ambiente;
    }

    public Date getDt_fin_val_appart_ambiente() {
	return dt_fin_val_appart_ambiente;
    }

    public void setDt_fin_val_appart_ambiente(Date dt_fin_val_appart_ambiente) {
	this.dt_fin_val_appart_ambiente = dt_fin_val_appart_ambiente;
    }

    public String getInfo_message() {
	return info_message;
    }

    public void setInfo_message(String info_message) {
	this.info_message = info_message;
    }

    public String getCd_ufe() {
	return cd_ufe;
    }

    public void setCd_ufe(String cd_ufe) {
	this.cd_ufe = cd_ufe;
    }

    public String getDs_ufe() {
	return ds_ufe;
    }

    public void setDs_ufe(String ds_ufe) {
	this.ds_ufe = ds_ufe;
    }

    public BigDecimal getId_cd_iva() {
	return id_cd_iva;
    }

    public void setId_cd_iva(BigDecimal id_cd_iva) {
	this.id_cd_iva = id_cd_iva;
    }

    public String getTi_mod_pagam() {
	return ti_mod_pagam;
    }

    public void setTi_mod_pagam(String ti_mod_pagam) {
	this.ti_mod_pagam = ti_mod_pagam;
    }

    public String getDs_note() {
	return ds_note;
    }

    public void setDs_note(String ds_note) {
	this.ds_note = ds_note;
    }

}
