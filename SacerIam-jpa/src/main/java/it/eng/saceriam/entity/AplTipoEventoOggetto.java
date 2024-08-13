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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
 * The persistent class for the APL_TIPO_EVENTO_OGGETTO database table.
 */
@Entity
@Table(name = "APL_TIPO_EVENTO_OGGETTO")
public class AplTipoEventoOggetto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idTipoEventoOggetto;

    private BigDecimal idQueryTipoOggettoFoto;

    private BigDecimal idTipoEvento;

    private BigDecimal idTipoOggetto;

    private List<AplTipoEventoOggettoTrig> aplTipoEventoOggettoTrigs = new ArrayList<>();

    public AplTipoEventoOggetto() {
    }

    @Id
    @Column(name = "ID_TIPO_EVENTO_OGGETTO")
    @GenericGenerator(name = "SAPL_TIPO_EVENTO_OGGETTO_ID_TIPO_EVENTO_OGGETTO_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SAPL_TIPO_EVENTO_OGGETTO"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAPL_TIPO_EVENTO_OGGETTO_ID_TIPO_EVENTO_OGGETTO_GENERATOR")
    public Long getIdTipoEventoOggetto() {
        return this.idTipoEventoOggetto;
    }

    public void setIdTipoEventoOggetto(Long idTipoEventoOggetto) {
        this.idTipoEventoOggetto = idTipoEventoOggetto;
    }

    @Column(name = "ID_QUERY_TIPO_OGGETTO_FOTO")
    public BigDecimal getIdQueryTipoOggettoFoto() {
        return this.idQueryTipoOggettoFoto;
    }

    public void setIdQueryTipoOggettoFoto(BigDecimal idQueryTipoOggettoFoto) {
        this.idQueryTipoOggettoFoto = idQueryTipoOggettoFoto;
    }

    @Column(name = "ID_TIPO_EVENTO")
    public BigDecimal getIdTipoEvento() {
        return this.idTipoEvento;
    }

    public void setIdTipoEvento(BigDecimal idTipoEvento) {
        this.idTipoEvento = idTipoEvento;
    }

    @Column(name = "ID_TIPO_OGGETTO")
    public BigDecimal getIdTipoOggetto() {
        return this.idTipoOggetto;
    }

    public void setIdTipoOggetto(BigDecimal idTipoOggetto) {
        this.idTipoOggetto = idTipoOggetto;
    }

    // bi-directional many-to-one association to AplTipoEventoOggettoTrig
    @OneToMany(mappedBy = "aplTipoEventoOggetto")
    public List<AplTipoEventoOggettoTrig> getAplTipoEventoOggettoTrigs() {
        return this.aplTipoEventoOggettoTrigs;
    }

    public void setAplTipoEventoOggettoTrigs(List<AplTipoEventoOggettoTrig> aplTipoEventoOggettoTrigs) {
        this.aplTipoEventoOggettoTrigs = aplTipoEventoOggettoTrigs;
    }

    public AplTipoEventoOggettoTrig addAplTipoEventoOggettoTrig(AplTipoEventoOggettoTrig aplTipoEventoOggettoTrig) {
        getAplTipoEventoOggettoTrigs().add(aplTipoEventoOggettoTrig);
        aplTipoEventoOggettoTrig.setAplTipoEventoOggetto(this);
        return aplTipoEventoOggettoTrig;
    }

    public AplTipoEventoOggettoTrig removeAplTipoEventoOggettoTrig(AplTipoEventoOggettoTrig aplTipoEventoOggettoTrig) {
        getAplTipoEventoOggettoTrigs().remove(aplTipoEventoOggettoTrig);
        aplTipoEventoOggettoTrig.setAplTipoEventoOggetto(null);
        return aplTipoEventoOggettoTrig;
    }
}
