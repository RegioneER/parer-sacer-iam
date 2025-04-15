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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the APL_TIPO_OGGETTO database table.
 */
@Entity
@Table(name = "APL_TIPO_OGGETTO")
public class AplTipoOggetto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idTipoOggetto;

    private String nmTipoOggetto;

    private List<AplQueryTipoOggetto> aplQueryTipoOggettos1 = new ArrayList<>();

    private List<AplQueryTipoOggetto> aplQueryTipoOggettos2 = new ArrayList<>();

    private List<AplQueryTipoOggetto> aplQueryTipoOggettos3 = new ArrayList<>();

    private List<AplTipoEventoOggettoTrig> aplTipoEventoOggettoTrigs = new ArrayList<>();

    private AplApplic aplApplic;

    private AplTipoOggetto aplTipoOggetto;

    private List<AplTipoOggetto> aplTipoOggettos = new ArrayList<>();

    public AplTipoOggetto() {
    }

    @Id
    @Column(name = "ID_TIPO_OGGETTO")
    @GenericGenerator(name = "SAPL_TIPO_OGGETTO_ID_TIPO_OGGETTO_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SAPL_TIPO_OGGETTO"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAPL_TIPO_OGGETTO_ID_TIPO_OGGETTO_GENERATOR")
    public Long getIdTipoOggetto() {
	return this.idTipoOggetto;
    }

    public void setIdTipoOggetto(Long idTipoOggetto) {
	this.idTipoOggetto = idTipoOggetto;
    }

    @Column(name = "NM_TIPO_OGGETTO")
    public String getNmTipoOggetto() {
	return this.nmTipoOggetto;
    }

    public void setNmTipoOggetto(String nmTipoOggetto) {
	this.nmTipoOggetto = nmTipoOggetto;
    }

    // bi-directional many-to-one association to AplQueryTipoOggetto
    @OneToMany(mappedBy = "aplTipoOggetto1")
    public List<AplQueryTipoOggetto> getAplQueryTipoOggettos1() {
	return this.aplQueryTipoOggettos1;
    }

    public void setAplQueryTipoOggettos1(List<AplQueryTipoOggetto> aplQueryTipoOggettos1) {
	this.aplQueryTipoOggettos1 = aplQueryTipoOggettos1;
    }

    public AplQueryTipoOggetto addAplQueryTipoOggettos1(AplQueryTipoOggetto aplQueryTipoOggettos1) {
	getAplQueryTipoOggettos1().add(aplQueryTipoOggettos1);
	aplQueryTipoOggettos1.setAplTipoOggetto1(this);
	return aplQueryTipoOggettos1;
    }

    public AplQueryTipoOggetto removeAplQueryTipoOggettos1(
	    AplQueryTipoOggetto aplQueryTipoOggettos1) {
	getAplQueryTipoOggettos1().remove(aplQueryTipoOggettos1);
	aplQueryTipoOggettos1.setAplTipoOggetto1(null);
	return aplQueryTipoOggettos1;
    }

    // bi-directional many-to-one association to AplQueryTipoOggetto
    @OneToMany(mappedBy = "aplTipoOggetto2")
    public List<AplQueryTipoOggetto> getAplQueryTipoOggettos2() {
	return this.aplQueryTipoOggettos2;
    }

    public void setAplQueryTipoOggettos2(List<AplQueryTipoOggetto> aplQueryTipoOggettos2) {
	this.aplQueryTipoOggettos2 = aplQueryTipoOggettos2;
    }

    public AplQueryTipoOggetto addAplQueryTipoOggettos2(AplQueryTipoOggetto aplQueryTipoOggettos2) {
	getAplQueryTipoOggettos2().add(aplQueryTipoOggettos2);
	aplQueryTipoOggettos2.setAplTipoOggetto2(this);
	return aplQueryTipoOggettos2;
    }

    public AplQueryTipoOggetto removeAplQueryTipoOggettos2(
	    AplQueryTipoOggetto aplQueryTipoOggettos2) {
	getAplQueryTipoOggettos2().remove(aplQueryTipoOggettos2);
	aplQueryTipoOggettos2.setAplTipoOggetto2(null);
	return aplQueryTipoOggettos2;
    }

    // bi-directional many-to-one association to AplQueryTipoOggetto
    @OneToMany(mappedBy = "aplTipoOggetto3")
    public List<AplQueryTipoOggetto> getAplQueryTipoOggettos3() {
	return this.aplQueryTipoOggettos3;
    }

    public void setAplQueryTipoOggettos3(List<AplQueryTipoOggetto> aplQueryTipoOggettos3) {
	this.aplQueryTipoOggettos3 = aplQueryTipoOggettos3;
    }

    public AplQueryTipoOggetto addAplQueryTipoOggettos3(AplQueryTipoOggetto aplQueryTipoOggettos3) {
	getAplQueryTipoOggettos3().add(aplQueryTipoOggettos3);
	aplQueryTipoOggettos3.setAplTipoOggetto3(this);
	return aplQueryTipoOggettos3;
    }

    public AplQueryTipoOggetto removeAplQueryTipoOggettos3(
	    AplQueryTipoOggetto aplQueryTipoOggettos3) {
	getAplQueryTipoOggettos3().remove(aplQueryTipoOggettos3);
	aplQueryTipoOggettos3.setAplTipoOggetto3(null);
	return aplQueryTipoOggettos3;
    }

    // bi-directional many-to-one association to AplTipoEventoOggettoTrig
    @OneToMany(mappedBy = "aplTipoOggetto")
    public List<AplTipoEventoOggettoTrig> getAplTipoEventoOggettoTrigs() {
	return this.aplTipoEventoOggettoTrigs;
    }

    public void setAplTipoEventoOggettoTrigs(
	    List<AplTipoEventoOggettoTrig> aplTipoEventoOggettoTrigs) {
	this.aplTipoEventoOggettoTrigs = aplTipoEventoOggettoTrigs;
    }

    public AplTipoEventoOggettoTrig addAplTipoEventoOggettoTrig(
	    AplTipoEventoOggettoTrig aplTipoEventoOggettoTrig) {
	getAplTipoEventoOggettoTrigs().add(aplTipoEventoOggettoTrig);
	aplTipoEventoOggettoTrig.setAplTipoOggetto(this);
	return aplTipoEventoOggettoTrig;
    }

    public AplTipoEventoOggettoTrig removeAplTipoEventoOggettoTrig(
	    AplTipoEventoOggettoTrig aplTipoEventoOggettoTrig) {
	getAplTipoEventoOggettoTrigs().remove(aplTipoEventoOggettoTrig);
	aplTipoEventoOggettoTrig.setAplTipoOggetto(null);
	return aplTipoEventoOggettoTrig;
    }

    // bi-directional many-to-one association to AplApplic
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_APPLIC")
    public AplApplic getAplApplic() {
	return this.aplApplic;
    }

    public void setAplApplic(AplApplic aplApplic) {
	this.aplApplic = aplApplic;
    }

    // bi-directional many-to-one association to AplTipoOggetto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_OGGETTO_PADRE")
    public AplTipoOggetto getAplTipoOggetto() {
	return this.aplTipoOggetto;
    }

    public void setAplTipoOggetto(AplTipoOggetto aplTipoOggetto) {
	this.aplTipoOggetto = aplTipoOggetto;
    }

    // bi-directional many-to-one association to AplTipoOggetto
    @OneToMany(mappedBy = "aplTipoOggetto")
    public List<AplTipoOggetto> getAplTipoOggettos() {
	return this.aplTipoOggettos;
    }

    public void setAplTipoOggettos(List<AplTipoOggetto> aplTipoOggettos) {
	this.aplTipoOggettos = aplTipoOggettos;
    }

    public AplTipoOggetto addAplTipoOggetto(AplTipoOggetto aplTipoOggetto) {
	getAplTipoOggettos().add(aplTipoOggetto);
	aplTipoOggetto.setAplTipoOggetto(this);
	return aplTipoOggetto;
    }

    public AplTipoOggetto removeAplTipoOggetto(AplTipoOggetto aplTipoOggetto) {
	getAplTipoOggettos().remove(aplTipoOggetto);
	aplTipoOggetto.setAplTipoOggetto(null);
	return aplTipoOggetto;
    }
}
