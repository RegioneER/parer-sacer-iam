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

package it.eng.saceriam.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the PRF_RUOLO database table.
 */
@Entity
@Table(name = "PRF_RUOLO")
public class PrfRuolo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idRuolo;

    private String dsEsitoRichAllineaRuoli_1;

    private String dsEsitoRichAllineaRuoli_2;

    private String dsMsgAllineamentoParz;

    private String dsRuolo;

    private String flAllineamentoInCorso;

    private String nmRuolo;

    private String tiRuolo;

    private String tiStatoRichAllineaRuoli_1;

    private String tiStatoRichAllineaRuoli_2;

    private List<PrfAllineaRuolo> prfAllineaRuolos = new ArrayList<>();

    private List<PrfUsoRuoloApplic> prfUsoRuoloApplics = new ArrayList<>();

    private List<UsrUsoRuoloDich> usrUsoRuoloDiches = new ArrayList<>();

    private List<UsrUsoRuoloUserDefault> usrUsoRuoloUserDefaults = new ArrayList<>();

    private List<PrfRuoloCategoria> prfRuoloCategorias = new ArrayList<>();

    public PrfRuolo() {
    }

    @Id
    @Column(name = "ID_RUOLO")
    @GenericGenerator(name = "SPRF_RUOLO_ID_RUOLO_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SPRF_RUOLO"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SPRF_RUOLO_ID_RUOLO_GENERATOR")
    public Long getIdRuolo() {
	return this.idRuolo;
    }

    public void setIdRuolo(Long idRuolo) {
	this.idRuolo = idRuolo;
    }

    @Column(name = "DS_ESITO_RICH_ALLINEA_RUOLI_1")
    public String getDsEsitoRichAllineaRuoli_1() {
	return dsEsitoRichAllineaRuoli_1;
    }

    public void setDsEsitoRichAllineaRuoli_1(String dsEsitoRichAllineaRuoli_1) {
	this.dsEsitoRichAllineaRuoli_1 = dsEsitoRichAllineaRuoli_1;
    }

    @Column(name = "DS_ESITO_RICH_ALLINEA_RUOLI_2")
    public String getDsEsitoRichAllineaRuoli_2() {
	return dsEsitoRichAllineaRuoli_2;
    }

    public void setDsEsitoRichAllineaRuoli_2(String dsEsitoRichAllineaRuoli_2) {
	this.dsEsitoRichAllineaRuoli_2 = dsEsitoRichAllineaRuoli_2;
    }

    @Column(name = "DS_MSG_ALLINEAMENTO_PARZ")
    public String getDsMsgAllineamentoParz() {
	return dsMsgAllineamentoParz;
    }

    public void setDsMsgAllineamentoParz(String dsMsgAllineamentoParz) {
	this.dsMsgAllineamentoParz = dsMsgAllineamentoParz;
    }

    @Column(name = "DS_RUOLO")
    public String getDsRuolo() {
	return this.dsRuolo;
    }

    public void setDsRuolo(String dsRuolo) {
	this.dsRuolo = dsRuolo;
    }

    @Column(name = "FL_ALLINEAMENTO_IN_CORSO", columnDefinition = "char(1)")
    public String getFlAllineamentoInCorso() {
	return this.flAllineamentoInCorso;
    }

    public void setFlAllineamentoInCorso(String flAllineamentoInCorso) {
	this.flAllineamentoInCorso = flAllineamentoInCorso;
    }

    @Column(name = "NM_RUOLO")
    public String getNmRuolo() {
	return this.nmRuolo;
    }

    public void setNmRuolo(String nmRuolo) {
	this.nmRuolo = nmRuolo;
    }

    @Column(name = "TI_RUOLO")
    public String getTiRuolo() {
	return this.tiRuolo;
    }

    public void setTiRuolo(String tiRuolo) {
	this.tiRuolo = tiRuolo;
    }

    @Column(name = "TI_STATO_RICH_ALLINEA_RUOLI_1")
    public String getTiStatoRichAllineaRuoli_1() {
	return this.tiStatoRichAllineaRuoli_1;
    }

    public void setTiStatoRichAllineaRuoli_1(String tiStatoRichAllineaRuoli_1) {
	this.tiStatoRichAllineaRuoli_1 = tiStatoRichAllineaRuoli_1;
    }

    @Column(name = "TI_STATO_RICH_ALLINEA_RUOLI_2")
    public String getTiStatoRichAllineaRuoli_2() {
	return this.tiStatoRichAllineaRuoli_2;
    }

    public void setTiStatoRichAllineaRuoli_2(String tiStatoRichAllineaRuoli_2) {
	this.tiStatoRichAllineaRuoli_2 = tiStatoRichAllineaRuoli_2;
    }

    // bi-directional many-to-one association to PrfAllineaRuolo
    @OneToMany(mappedBy = "prfRuolo")
    public List<PrfAllineaRuolo> getPrfAllineaRuolos() {
	return this.prfAllineaRuolos;
    }

    public void setPrfAllineaRuolos(List<PrfAllineaRuolo> prfAllineaRuolos) {
	this.prfAllineaRuolos = prfAllineaRuolos;
    }

    // bi-directional many-to-one association to PrfUsoRuoloApplic
    @OneToMany(mappedBy = "prfRuolo", cascade = {
	    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    public List<PrfUsoRuoloApplic> getPrfUsoRuoloApplics() {
	return this.prfUsoRuoloApplics;
    }

    public void setPrfUsoRuoloApplics(List<PrfUsoRuoloApplic> prfUsoRuoloApplics) {
	this.prfUsoRuoloApplics = prfUsoRuoloApplics;
    }

    // bi-directional many-to-one association to UsrUsoRuoloDich
    @OneToMany(mappedBy = "prfRuolo")
    public List<UsrUsoRuoloDich> getUsrUsoRuoloDiches() {
	return this.usrUsoRuoloDiches;
    }

    public void setUsrUsoRuoloDiches(List<UsrUsoRuoloDich> usrUsoRuoloDiches) {
	this.usrUsoRuoloDiches = usrUsoRuoloDiches;
    }

    // bi-directional many-to-one association to UsrUsoRuoloUserDefault
    @OneToMany(mappedBy = "prfRuolo")
    public List<UsrUsoRuoloUserDefault> getUsrUsoRuoloUserDefaults() {
	return this.usrUsoRuoloUserDefaults;
    }

    public void setUsrUsoRuoloUserDefaults(List<UsrUsoRuoloUserDefault> usrUsoRuoloUserDefaults) {
	this.usrUsoRuoloUserDefaults = usrUsoRuoloUserDefaults;
    }

    // bi-directional many-to-one association to PrfRuoloCategoria
    @OneToMany(mappedBy = "prfRuolo", cascade = {
	    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    public List<PrfRuoloCategoria> getPrfRuoloCategorias() {
	return this.prfRuoloCategorias;
    }

    public void setPrfRuoloCategorias(List<PrfRuoloCategoria> prfRuoloCategorias) {
	this.prfRuoloCategorias = prfRuoloCategorias;
    }
}
