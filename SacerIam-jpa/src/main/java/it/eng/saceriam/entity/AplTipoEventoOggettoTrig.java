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
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the APL_TIPO_EVENTO_OGGETTO_TRIG database table.
 */
@Entity
@Table(name = "APL_TIPO_EVENTO_OGGETTO_TRIG")
@NamedQuery(name = "AplTipoEventoOggettoTrig.findAll", query = "SELECT a FROM AplTipoEventoOggettoTrig a")
public class AplTipoEventoOggettoTrig implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idTipoEventoOggettoTrig;

    private AplQueryTipoOggetto aplQueryTipoOggetto1;

    private AplQueryTipoOggetto aplQueryTipoOggetto2;

    private AplTipoEvento aplTipoEvento;

    private AplTipoEventoOggetto aplTipoEventoOggetto;

    private AplTipoOggetto aplTipoOggetto;

    public AplTipoEventoOggettoTrig() {
    }

    @Id
    @Column(name = "ID_TIPO_EVENTO_OGGETTO_TRIG")
    @GenericGenerator(name = "SAPL_TIPO_EVENTO_OGGETTO_TRIG_ID_TIPO_EVENTO_OGGETTO_TRIG_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SAPL_TIPO_EVENTO_OGGETTO_TRIG"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAPL_TIPO_EVENTO_OGGETTO_TRIG_ID_TIPO_EVENTO_OGGETTO_TRIG_GENERATOR")
    public Long getIdTipoEventoOggettoTrig() {
        return this.idTipoEventoOggettoTrig;
    }

    public void setIdTipoEventoOggettoTrig(Long idTipoEventoOggettoTrig) {
        this.idTipoEventoOggettoTrig = idTipoEventoOggettoTrig;
    }

    // bi-directional many-to-one association to AplQueryTipoOggetto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_QUERY_TIPO_OGGETTO_TRIG")
    public AplQueryTipoOggetto getAplQueryTipoOggetto1() {
        return this.aplQueryTipoOggetto1;
    }

    public void setAplQueryTipoOggetto1(AplQueryTipoOggetto aplQueryTipoOggetto1) {
        this.aplQueryTipoOggetto1 = aplQueryTipoOggetto1;
    }

    // bi-directional many-to-one association to AplQueryTipoOggetto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_QUERY_TIPO_OGGETTO_SEL")
    public AplQueryTipoOggetto getAplQueryTipoOggetto2() {
        return this.aplQueryTipoOggetto2;
    }

    public void setAplQueryTipoOggetto2(AplQueryTipoOggetto aplQueryTipoOggetto2) {
        this.aplQueryTipoOggetto2 = aplQueryTipoOggetto2;
    }

    // bi-directional many-to-one association to AplTipoEvento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_EVENTO_TRIG")
    public AplTipoEvento getAplTipoEvento() {
        return this.aplTipoEvento;
    }

    public void setAplTipoEvento(AplTipoEvento aplTipoEvento) {
        this.aplTipoEvento = aplTipoEvento;
    }

    // bi-directional many-to-one association to AplTipoEventoOggetto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_EVENTO_OGGETTO")
    public AplTipoEventoOggetto getAplTipoEventoOggetto() {
        return this.aplTipoEventoOggetto;
    }

    public void setAplTipoEventoOggetto(AplTipoEventoOggetto aplTipoEventoOggetto) {
        this.aplTipoEventoOggetto = aplTipoEventoOggetto;
    }

    // bi-directional many-to-one association to AplTipoOggetto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_OGGETTO_TRIG")
    public AplTipoOggetto getAplTipoOggetto() {
        return this.aplTipoOggetto;
    }

    public void setAplTipoOggetto(AplTipoOggetto aplTipoOggetto) {
        this.aplTipoOggetto = aplTipoOggetto;
    }
}
