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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the PRF_RUOLO_CATEGORIA database table.
 */
@Entity
@Table(name = "PRF_RUOLO_CATEGORIA")
public class PrfRuoloCategoria implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idRuoloCategoria;

    private String tiCategRuolo;

    private PrfRuolo prfRuolo;

    public PrfRuoloCategoria() {
    }

    @Id
    @Column(name = "ID_RUOLO_CATEGORIA")
    @GenericGenerator(name = "SPRF_RUOLO_CATEGORIA_ID_RUOLO_CATEGORIA_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SPRF_RUOLO_CATEGORIA"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SPRF_RUOLO_CATEGORIA_ID_RUOLO_CATEGORIA_GENERATOR")
    public Long getIdRuoloCategoria() {
	return this.idRuoloCategoria;
    }

    public void setIdRuoloCategoria(Long idRuoloCatgoria) {
	this.idRuoloCategoria = idRuoloCatgoria;
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

    @Column(name = "TI_CATEG_RUOLO")
    public String getTiCategRuolo() {
	return this.tiCategRuolo;
    }

    public void setTiCategRuolo(String tiCategRuolo) {
	this.tiCategRuolo = tiCategRuolo;
    }
}
