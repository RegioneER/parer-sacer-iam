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
 * The persistent class for the PRF_USO_RUOLO_APPLIC database table.
 */
@Entity
@Table(name = "PRF_USO_RUOLO_APPLIC")
public class PrfUsoRuoloApplic implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idUsoRuoloApplic;

    private List<PrfAutor> prfAutors = new ArrayList<>();

    private List<PrfDichAutor> prfDichAutors = new ArrayList<>();

    private AplApplic aplApplic;

    private PrfRuolo prfRuolo;

    public PrfUsoRuoloApplic() {
    }

    @Id
    @Column(name = "ID_USO_RUOLO_APPLIC")
    @GenericGenerator(name = "SPRF_USO_RUOLO_APPLIC_ID_USO_RUOLO_APPLIC_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SPRF_USO_RUOLO_APPLIC"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SPRF_USO_RUOLO_APPLIC_ID_USO_RUOLO_APPLIC_GENERATOR")
    public Long getIdUsoRuoloApplic() {
	return this.idUsoRuoloApplic;
    }

    public void setIdUsoRuoloApplic(Long idUsoRuoloApplic) {
	this.idUsoRuoloApplic = idUsoRuoloApplic;
    }

    // bi-directional many-to-one association to PrfAutor
    @OneToMany(mappedBy = "prfUsoRuoloApplic")
    public List<PrfAutor> getPrfAutors() {
	return this.prfAutors;
    }

    public void setPrfAutors(List<PrfAutor> prfAutors) {
	this.prfAutors = prfAutors;
    }

    // bi-directional many-to-one association to PrfDichAutor
    @OneToMany(mappedBy = "prfUsoRuoloApplic", cascade = {
	    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    public List<PrfDichAutor> getPrfDichAutors() {
	return this.prfDichAutors;
    }

    public void setPrfDichAutors(List<PrfDichAutor> prfDichAutors) {
	this.prfDichAutors = prfDichAutors;
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

    // bi-directional many-to-one association to PrfRuolo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RUOLO")
    public PrfRuolo getPrfRuolo() {
	return this.prfRuolo;
    }

    public void setPrfRuolo(PrfRuolo prfRuolo) {
	this.prfRuolo = prfRuolo;
    }
}
