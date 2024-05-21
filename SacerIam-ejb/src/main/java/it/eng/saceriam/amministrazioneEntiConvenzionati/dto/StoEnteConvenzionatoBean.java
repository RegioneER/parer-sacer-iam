/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna
 * <p/>
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.amministrazioneEntiConvenzionati.dto;

import java.math.BigDecimal;
import java.util.Date;

import it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm;
import it.eng.spagoCore.error.EMFError;

/**
 *
 * @author Gilioli_P
 */
public class StoEnteConvenzionatoBean {

    private String nm_ente_convenz;
    private Date dt_ini_val;
    private Date dt_fine_val;
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
    private String dl_note;
    private BigDecimal id_ente_convenz;

    public StoEnteConvenzionatoBean(AmministrazioneEntiConvenzionatiForm.AnagraficaDetail detail) throws EMFError {
        this.nm_ente_convenz = detail.getNm_ente_siam().parse();
        this.dt_ini_val = detail.getDt_ini_val().parse();
        this.ds_via_sede_legale = detail.getDs_via_sede_legale().parse();
        this.cd_cap_sede_legale = detail.getCd_cap_sede_legale().parse();
        this.ds_citta_sede_legale = detail.getDs_citta_sede_legale().parse();
        this.cd_fisc = detail.getCd_fisc().parse();
        this.ds_categ_ente = detail.getDs_categ_ente().parse();
        this.cd_ambito_territ_regione = detail.getCd_ambito_territ_regione().parse();
        this.cd_ambito_territ_provincia = detail.getCd_ambito_territ_provincia().parse();
        this.cd_ambito_territ_forma_associata = detail.getCd_ambito_territ_forma_associata().parse();
        this.id_ente_convenz_nuovo = detail.getId_ente_convenz_nuovo().parse();
        this.cd_ente_convenz = detail.getCd_ente_convenz().parse();
        this.ti_cd_ente_convenz = detail.getTi_cd_ente_convenz().parse();
        this.cd_nazione_sede_legale = detail.getCd_nazione_sede_legale().parse();
        this.id_prov_sede_legale = detail.getId_prov_sede_legale().parse();
        //
        this.dt_fine_val = detail.getDt_fine_val().parse();
        this.dl_note = detail.getDl_note().parse();
    }

    public BigDecimal getId_ente_convenz() {
        return id_ente_convenz;
    }

    public void setId_ente_convenz(BigDecimal id_ente_convenz) {
        this.id_ente_convenz = id_ente_convenz;
    }

    public Date getDt_fine_val() {
        return dt_fine_val;
    }

    public void setDt_fine_val(Date dt_fine_val) {
        this.dt_fine_val = dt_fine_val;
    }

    public String getDl_note() {
        return dl_note;
    }

    public void setDl_note(String dl_note) {
        this.dl_note = dl_note;
    }

    public String getNm_ente_convenz() {
        return nm_ente_convenz;
    }

    public void setNm_ente_convenz(String nm_ente_convenz) {
        this.nm_ente_convenz = nm_ente_convenz;
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

}
