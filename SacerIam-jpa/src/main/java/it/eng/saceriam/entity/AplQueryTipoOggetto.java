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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the APL_QUERY_TIPO_OGGETTO database table.
 */
@Entity
@Table(name = "APL_QUERY_TIPO_OGGETTO")
@NamedQuery(name = "AplQueryTipoOggetto.findAll", query = "SELECT a FROM AplQueryTipoOggetto a")
public class AplQueryTipoOggetto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idQueryTipoOggetto;

    private String blQueryTipoOggetto;

    private String nmQueryTipoOggetto;

    private String tipoUsoQuery;

    private AplTipoOggetto aplTipoOggetto1;

    private AplTipoOggetto aplTipoOggetto2;

    private AplTipoOggetto aplTipoOggetto3;

    private List<AplTipoEventoOggettoTrig> aplTipoEventoOggettoTrigs1 = new ArrayList<>();

    private List<AplTipoEventoOggettoTrig> aplTipoEventoOggettoTrigs2 = new ArrayList<>();

    public AplQueryTipoOggetto() {
    }

    @Id
    @Column(name = "ID_QUERY_TIPO_OGGETTO")
    @GenericGenerator(name = "SAPL_QUERY_TIPO_OGGETTO_ID_QUERY_TIPO_OGGETTO_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SAPL_QUERY_TIPO_OGGETTO"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAPL_QUERY_TIPO_OGGETTO_ID_QUERY_TIPO_OGGETTO_GENERATOR")
    public Long getIdQueryTipoOggetto() {
        return this.idQueryTipoOggetto;
    }

    public void setIdQueryTipoOggetto(Long idQueryTipoOggetto) {
        this.idQueryTipoOggetto = idQueryTipoOggetto;
    }

    @Lob
    @Column(name = "BL_QUERY_TIPO_OGGETTO")
    public String getBlQueryTipoOggetto() {
        return this.blQueryTipoOggetto;
    }

    public void setBlQueryTipoOggetto(String blQueryTipoOggetto) {
        this.blQueryTipoOggetto = blQueryTipoOggetto;
    }

    @Column(name = "NM_QUERY_TIPO_OGGETTO")
    public String getNmQueryTipoOggetto() {
        return this.nmQueryTipoOggetto;
    }

    public void setNmQueryTipoOggetto(String nmQueryTipoOggetto) {
        this.nmQueryTipoOggetto = nmQueryTipoOggetto;
    }

    @Column(name = "TIPO_USO_QUERY")
    public String getTipoUsoQuery() {
        return this.tipoUsoQuery;
    }

    public void setTipoUsoQuery(String tipoUsoQuery) {
        this.tipoUsoQuery = tipoUsoQuery;
    }

    // bi-directional many-to-one association to AplTipoOggetto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_OGGETTO_SEL")
    public AplTipoOggetto getAplTipoOggetto1() {
        return this.aplTipoOggetto1;
    }

    public void setAplTipoOggetto1(AplTipoOggetto aplTipoOggetto1) {
        this.aplTipoOggetto1 = aplTipoOggetto1;
    }

    // bi-directional many-to-one association to AplTipoOggetto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_OGGETTO")
    public AplTipoOggetto getAplTipoOggetto2() {
        return this.aplTipoOggetto2;
    }

    public void setAplTipoOggetto2(AplTipoOggetto aplTipoOggetto2) {
        this.aplTipoOggetto2 = aplTipoOggetto2;
    }

    // bi-directional many-to-one association to AplTipoOggetto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_OGGETTO_ACCESSO")
    public AplTipoOggetto getAplTipoOggetto3() {
        return this.aplTipoOggetto3;
    }

    public void setAplTipoOggetto3(AplTipoOggetto aplTipoOggetto3) {
        this.aplTipoOggetto3 = aplTipoOggetto3;
    }

    // bi-directional many-to-one association to AplTipoEventoOggettoTrig
    @OneToMany(mappedBy = "aplQueryTipoOggetto1")
    public List<AplTipoEventoOggettoTrig> getAplTipoEventoOggettoTrigs1() {
        return this.aplTipoEventoOggettoTrigs1;
    }

    public void setAplTipoEventoOggettoTrigs1(List<AplTipoEventoOggettoTrig> aplTipoEventoOggettoTrigs1) {
        this.aplTipoEventoOggettoTrigs1 = aplTipoEventoOggettoTrigs1;
    }

    public AplTipoEventoOggettoTrig addAplTipoEventoOggettoTrigs1(AplTipoEventoOggettoTrig aplTipoEventoOggettoTrigs1) {
        getAplTipoEventoOggettoTrigs1().add(aplTipoEventoOggettoTrigs1);
        aplTipoEventoOggettoTrigs1.setAplQueryTipoOggetto1(this);
        return aplTipoEventoOggettoTrigs1;
    }

    public AplTipoEventoOggettoTrig removeAplTipoEventoOggettoTrigs1(
            AplTipoEventoOggettoTrig aplTipoEventoOggettoTrigs1) {
        getAplTipoEventoOggettoTrigs1().remove(aplTipoEventoOggettoTrigs1);
        aplTipoEventoOggettoTrigs1.setAplQueryTipoOggetto1(null);
        return aplTipoEventoOggettoTrigs1;
    }

    // bi-directional many-to-one association to AplTipoEventoOggettoTrig
    @OneToMany(mappedBy = "aplQueryTipoOggetto2")
    public List<AplTipoEventoOggettoTrig> getAplTipoEventoOggettoTrigs2() {
        return this.aplTipoEventoOggettoTrigs2;
    }

    public void setAplTipoEventoOggettoTrigs2(List<AplTipoEventoOggettoTrig> aplTipoEventoOggettoTrigs2) {
        this.aplTipoEventoOggettoTrigs2 = aplTipoEventoOggettoTrigs2;
    }

    public AplTipoEventoOggettoTrig addAplTipoEventoOggettoTrigs2(AplTipoEventoOggettoTrig aplTipoEventoOggettoTrigs2) {
        getAplTipoEventoOggettoTrigs2().add(aplTipoEventoOggettoTrigs2);
        aplTipoEventoOggettoTrigs2.setAplQueryTipoOggetto2(this);
        return aplTipoEventoOggettoTrigs2;
    }

    public AplTipoEventoOggettoTrig removeAplTipoEventoOggettoTrigs2(
            AplTipoEventoOggettoTrig aplTipoEventoOggettoTrigs2) {
        getAplTipoEventoOggettoTrigs2().remove(aplTipoEventoOggettoTrigs2);
        aplTipoEventoOggettoTrigs2.setAplQueryTipoOggetto2(null);
        return aplTipoEventoOggettoTrigs2;
    }
}
