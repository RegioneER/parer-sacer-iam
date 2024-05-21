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

package it.eng.saceriam.web.dto;

import java.math.BigDecimal;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;

import it.eng.saceriam.slite.gen.form.GestioneFatturazioneServiziForm;
import it.eng.spagoCore.error.EMFError;

/**
 *
 * @author Gilioli_P
 */
public class EstraiFattureBean implements Comparable<EstraiFattureBean> {

    private String nm_ente_convenz;
    private String cd_fisc;
    private String nm_servizio_erogato_completo;
    private BigDecimal aa_erogazione;
    private String ds_atto_aa_accordo;
    private String cd_ufe;
    private String cd_cig;
    private BigDecimal im_servizio_fattura;
    private BigDecimal im_totale;
    private String cd_iva;
    private String conto_tesoreria_unica;
    private String conto_iban;

    public EstraiFattureBean(GestioneFatturazioneServiziForm.ListaEstraiRigheFatture detail) throws EMFError {
        this.nm_ente_convenz = detail.getNm_ente_convenz().parse();
        this.cd_fisc = detail.getCd_fisc().parse();
        this.nm_servizio_erogato_completo = detail.getNm_servizio_erogato_completo().parse();
        this.aa_erogazione = detail.getAa_erogazione().parse();
        this.ds_atto_aa_accordo = detail.getDs_atto_aa_accordo().parse();
        this.cd_ufe = detail.getCd_ufe().parse();
        this.cd_cig = detail.getCd_cig().parse();
        this.im_servizio_fattura = detail.getIm_servizio_fattura().parse();
        this.im_totale = detail.getIm_totale().parse();
        this.cd_iva = detail.getCd_iva().parse();
        this.conto_tesoreria_unica = detail.getConto_tesoreria_unica().parse();
        this.conto_iban = detail.getConto_IBAN().parse();
    }

    public EstraiFattureBean() {
    }

    public String getNm_ente_convenz() {
        return nm_ente_convenz;
    }

    public void setNm_ente_convenz(String nm_ente_convenz) {
        this.nm_ente_convenz = nm_ente_convenz;
    }

    public String getCd_fisc() {
        return cd_fisc;
    }

    public void setCd_fisc(String cd_fisc) {
        this.cd_fisc = cd_fisc;
    }

    public String getNm_servizio_erogato_completo() {
        return nm_servizio_erogato_completo;
    }

    public void setNm_servizio_erogato_completo(String nm_servizio_erogato_completo) {
        this.nm_servizio_erogato_completo = nm_servizio_erogato_completo;
    }

    public BigDecimal getAa_erogazione() {
        return aa_erogazione;
    }

    public void setAa_erogazione(BigDecimal aa_erogazione) {
        this.aa_erogazione = aa_erogazione;
    }

    public String getDs_atto_aa_accordo() {
        return ds_atto_aa_accordo;
    }

    public void setDs_atto_aa_accordo(String ds_atto_aa_accordo) {
        this.ds_atto_aa_accordo = ds_atto_aa_accordo;
    }

    public String getCd_ufe() {
        return cd_ufe;
    }

    public void setCd_ufe(String cd_ufe) {
        this.cd_ufe = cd_ufe;
    }

    public String getCd_cig() {
        return cd_cig;
    }

    public void setCd_cig(String cd_cig) {
        this.cd_cig = cd_cig;
    }

    public BigDecimal getIm_servizio_fattura() {
        return im_servizio_fattura;
    }

    public void setIm_servizio_fattura(BigDecimal im_servizio_fattura) {
        this.im_servizio_fattura = im_servizio_fattura;
    }

    public BigDecimal getIm_totale() {
        return im_totale;
    }

    public void setIm_totale(BigDecimal im_totale) {
        this.im_totale = im_totale;
    }

    public String getCd_iva() {
        return cd_iva;
    }

    public void setCd_iva(String cd_iva) {
        this.cd_iva = cd_iva;
    }

    public String getConto_tesoreria_unica() {
        return conto_tesoreria_unica;
    }

    public void setConto_tesoreria_unica(String conto_tesoreria_unica) {
        this.conto_tesoreria_unica = conto_tesoreria_unica;
    }

    public String getConto_iban() {
        return conto_iban;
    }

    public void setConto_iban(String conto_iban) {
        this.conto_iban = conto_iban;
    }

    @Override
    public boolean equals(Object riga) {
        boolean uguale = false;
        if (riga instanceof EstraiFattureBean) {
            EstraiFattureBean rigaFattura = (EstraiFattureBean) riga;
            return Objects.equals(rigaFattura.getNm_ente_convenz(), this.nm_ente_convenz)
                    && Objects.equals(rigaFattura.getCd_fisc(), this.cd_fisc)
                    && Objects.equals(rigaFattura.getNm_servizio_erogato_completo(), this.nm_servizio_erogato_completo)
                    && ObjectUtils.compare(rigaFattura.getAa_erogazione(), this.aa_erogazione) == 0
                    && Objects.equals(rigaFattura.getDs_atto_aa_accordo(), this.ds_atto_aa_accordo)
                    && Objects.equals(rigaFattura.getCd_ufe(), this.cd_ufe)
                    && Objects.equals(rigaFattura.getCd_cig(), this.cd_cig)
                    && ObjectUtils.compare(rigaFattura.getIm_servizio_fattura(), this.im_servizio_fattura) == 0
                    && ObjectUtils.compare(rigaFattura.getIm_totale(), this.im_totale) == 0
                    && Objects.equals(rigaFattura.getCd_iva(), this.cd_iva)
                    && Objects.equals(rigaFattura.getConto_tesoreria_unica(), this.conto_tesoreria_unica)
                    && Objects.equals(rigaFattura.getConto_iban(), this.conto_iban);
        }

        return uguale;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.nm_ente_convenz);
        hash = 59 * hash + Objects.hashCode(this.cd_fisc);
        hash = 59 * hash + Objects.hashCode(this.nm_servizio_erogato_completo);
        hash = 59 * hash + Objects.hashCode(this.aa_erogazione);
        hash = 59 * hash + Objects.hashCode(this.ds_atto_aa_accordo);
        hash = 59 * hash + Objects.hashCode(this.cd_ufe);
        hash = 59 * hash + Objects.hashCode(this.cd_cig);
        hash = 59 * hash + Objects.hashCode(this.im_servizio_fattura);
        hash = 59 * hash + Objects.hashCode(this.im_totale);
        hash = 59 * hash + Objects.hashCode(this.cd_iva);
        hash = 59 * hash + Objects.hashCode(this.conto_tesoreria_unica);
        hash = 59 * hash + Objects.hashCode(this.conto_iban);
        return hash;
    }

    @Override
    public int compareTo(EstraiFattureBean o) {
        int r = getNm_ente_convenz().compareTo(o.getNm_ente_convenz());

        if (r == 0) {
            r = getAa_erogazione().compareTo(o.getAa_erogazione());
        }

        if (r == 0) {
            r = getNm_servizio_erogato_completo().compareTo(o.getNm_servizio_erogato_completo());
        }

        return r;
    }

}
