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

import javax.persistence.Cacheable;
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
 * The persistent class for the APL_AZIONE_PAGINA database table.
 */
@Entity
@Cacheable(true)
@Table(name = "APL_AZIONE_PAGINA")
public class AplAzionePagina implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idAzionePagina;

    private String dsAzionePagina;

    private String nmAzionePagina;

    private AplPaginaWeb aplPaginaWeb;

    private AplTipoEvento aplTipoEvento;

    private List<PrfAutor> prfAutors = new ArrayList<>();

    private List<PrfDichAutor> prfDichAutors = new ArrayList<>();

    public AplAzionePagina() {
    }

    @Id
    @Column(name = "ID_AZIONE_PAGINA")
    @GenericGenerator(name = "SAPL_AZIONE_PAGINA_ID_AZIONE_PAGINA_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SAPL_AZIONE_PAGINA"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAPL_AZIONE_PAGINA_ID_AZIONE_PAGINA_GENERATOR")
    public Long getIdAzionePagina() {
        return this.idAzionePagina;
    }

    public void setIdAzionePagina(Long idAzionePagina) {
        this.idAzionePagina = idAzionePagina;
    }

    @Column(name = "DS_AZIONE_PAGINA")
    public String getDsAzionePagina() {
        return this.dsAzionePagina;
    }

    public void setDsAzionePagina(String dsAzionePagina) {
        this.dsAzionePagina = dsAzionePagina;
    }

    @Column(name = "NM_AZIONE_PAGINA")
    public String getNmAzionePagina() {
        return this.nmAzionePagina;
    }

    public void setNmAzionePagina(String nmAzionePagina) {
        this.nmAzionePagina = nmAzionePagina;
    }

    // bi-directional many-to-one association to AplPaginaWeb
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PAGINA_WEB")
    public AplPaginaWeb getAplPaginaWeb() {
        return this.aplPaginaWeb;
    }

    public void setAplPaginaWeb(AplPaginaWeb aplPaginaWeb) {
        this.aplPaginaWeb = aplPaginaWeb;
    }

    // bi-directional many-to-one association to AplTipoEvento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_EVENTO")
    public AplTipoEvento getAplTipoEvento() {
        return this.aplTipoEvento;
    }

    public void setAplTipoEvento(AplTipoEvento aplTipoEvento) {
        this.aplTipoEvento = aplTipoEvento;
    }

    // bi-directional many-to-one association to PrfAutor
    @OneToMany(mappedBy = "aplAzionePagina")
    public List<PrfAutor> getPrfAutors() {
        return this.prfAutors;
    }

    public void setPrfAutors(List<PrfAutor> prfAutors) {
        this.prfAutors = prfAutors;
    }

    // bi-directional many-to-one association to PrfDichAutor
    @OneToMany(mappedBy = "aplAzionePagina")
    public List<PrfDichAutor> getPrfDichAutors() {
        return this.prfDichAutors;
    }

    public void setPrfDichAutors(List<PrfDichAutor> prfDichAutors) {
        this.prfDichAutors = prfDichAutors;
    }
}
