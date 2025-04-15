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
 * The persistent class for the APL_TIPO_EVENTO database table.
 */
@Entity
@Table(name = "APL_TIPO_EVENTO")
public class AplTipoEvento implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idTipoEvento;

    private String nmTipoEvento;

    private String tipoClasseEvento;

    private String tipoClassePremisEvento;

    private String tipoOrigineEvento;

    private List<AplAzioneCompSw> aplAzioneCompSws = new ArrayList<>();

    private List<AplAzionePagina> aplAzionePaginas = new ArrayList<>();

    private AplApplic aplApplic;

    private List<AplTipoEventoOggettoTrig> aplTipoEventoOggettoTrigs = new ArrayList<>();

    public AplTipoEvento() {
    }

    @Id
    @Column(name = "ID_TIPO_EVENTO")
    @GenericGenerator(name = "SAPL_TIPO_EVENTO_ID_TIPO_EVENTO_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SAPL_TIPO_EVENTO"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAPL_TIPO_EVENTO_ID_TIPO_EVENTO_GENERATOR")
    public Long getIdTipoEvento() {
	return this.idTipoEvento;
    }

    public void setIdTipoEvento(Long idTipoEvento) {
	this.idTipoEvento = idTipoEvento;
    }

    @Column(name = "NM_TIPO_EVENTO")
    public String getNmTipoEvento() {
	return this.nmTipoEvento;
    }

    public void setNmTipoEvento(String nmTipoEvento) {
	this.nmTipoEvento = nmTipoEvento;
    }

    @Column(name = "TIPO_CLASSE_EVENTO")
    public String getTipoClasseEvento() {
	return this.tipoClasseEvento;
    }

    public void setTipoClasseEvento(String tipoClasseEvento) {
	this.tipoClasseEvento = tipoClasseEvento;
    }

    @Column(name = "TIPO_CLASSE_PREMIS_EVENTO")
    public String getTipoClassePremisEvento() {
	return this.tipoClassePremisEvento;
    }

    public void setTipoClassePremisEvento(String tipoClassePremisEvento) {
	this.tipoClassePremisEvento = tipoClassePremisEvento;
    }

    @Column(name = "TIPO_ORIGINE_EVENTO")
    public String getTipoOrigineEvento() {
	return this.tipoOrigineEvento;
    }

    public void setTipoOrigineEvento(String tipoOrigineEvento) {
	this.tipoOrigineEvento = tipoOrigineEvento;
    }

    // bi-directional many-to-one association to AplAzioneCompSw
    @OneToMany(mappedBy = "aplTipoEvento")
    public List<AplAzioneCompSw> getAplAzioneCompSws() {
	return this.aplAzioneCompSws;
    }

    public void setAplAzioneCompSws(List<AplAzioneCompSw> aplAzioneCompSws) {
	this.aplAzioneCompSws = aplAzioneCompSws;
    }

    public AplAzioneCompSw addAplAzioneCompSw(AplAzioneCompSw aplAzioneCompSw) {
	getAplAzioneCompSws().add(aplAzioneCompSw);
	aplAzioneCompSw.setAplTipoEvento(this);
	return aplAzioneCompSw;
    }

    public AplAzioneCompSw removeAplAzioneCompSw(AplAzioneCompSw aplAzioneCompSw) {
	getAplAzioneCompSws().remove(aplAzioneCompSw);
	aplAzioneCompSw.setAplTipoEvento(null);
	return aplAzioneCompSw;
    }

    // bi-directional many-to-one association to AplAzionePagina
    @OneToMany(mappedBy = "aplTipoEvento")
    public List<AplAzionePagina> getAplAzionePaginas() {
	return this.aplAzionePaginas;
    }

    public void setAplAzionePaginas(List<AplAzionePagina> aplAzionePaginas) {
	this.aplAzionePaginas = aplAzionePaginas;
    }

    public AplAzionePagina addAplAzionePagina(AplAzionePagina aplAzionePagina) {
	getAplAzionePaginas().add(aplAzionePagina);
	aplAzionePagina.setAplTipoEvento(this);
	return aplAzionePagina;
    }

    public AplAzionePagina removeAplAzionePagina(AplAzionePagina aplAzionePagina) {
	getAplAzionePaginas().remove(aplAzionePagina);
	aplAzionePagina.setAplTipoEvento(null);
	return aplAzionePagina;
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

    // bi-directional many-to-one association to AplTipoEventoOggettoTrig
    @OneToMany(mappedBy = "aplTipoEvento")
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
	aplTipoEventoOggettoTrig.setAplTipoEvento(this);
	return aplTipoEventoOggettoTrig;
    }

    public AplTipoEventoOggettoTrig removeAplTipoEventoOggettoTrig(
	    AplTipoEventoOggettoTrig aplTipoEventoOggettoTrig) {
	getAplTipoEventoOggettoTrigs().remove(aplTipoEventoOggettoTrig);
	aplTipoEventoOggettoTrig.setAplTipoEvento(null);
	return aplTipoEventoOggettoTrig;
    }
}
