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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the PRF_AUTOR database table.
 */
@Entity
@Table(name = "PRF_AUTOR")
public class PrfAutor implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idAutor;

    private String tiDichAutor;

    private AplAzionePagina aplAzionePagina;

    private AplEntryMenu aplEntryMenu;

    private AplPaginaWeb aplPaginaWeb;

    private AplServizioWeb aplServizioWeb;

    private PrfUsoRuoloApplic prfUsoRuoloApplic;

    public PrfAutor() {
    }

    @Id
    // "PRF_AUTOR_IDAUTOR_GENERATOR", sequenceName =
    // "SPRF_AUTOR", allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRF_AUTOR_IDAUTOR_GENERATOR")
    @Column(name = "ID_AUTOR")
    @GenericGenerator(name = "SPRF_AUTOR_ID_AUTOR_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SPRF_AUTOR"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SPRF_AUTOR_ID_AUTOR_GENERATOR")
    public Long getIdAutor() {
        return this.idAutor;
    }

    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }

    @Column(name = "TI_DICH_AUTOR")
    public String getTiDichAutor() {
        return this.tiDichAutor;
    }

    public void setTiDichAutor(String tiDichAutor) {
        this.tiDichAutor = tiDichAutor;
    }

    // bi-directional many-to-one association to AplAzionePagina
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AZIONE_PAGINA")
    public AplAzionePagina getAplAzionePagina() {
        return this.aplAzionePagina;
    }

    public void setAplAzionePagina(AplAzionePagina aplAzionePagina) {
        this.aplAzionePagina = aplAzionePagina;
    }

    // bi-directional many-to-one association to AplEntryMenu
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTRY_MENU")
    public AplEntryMenu getAplEntryMenu() {
        return this.aplEntryMenu;
    }

    public void setAplEntryMenu(AplEntryMenu aplEntryMenu) {
        this.aplEntryMenu = aplEntryMenu;
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

    // bi-directional many-to-one association to AplServizioWeb
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SERVIZIO_WEB")
    public AplServizioWeb getAplServizioWeb() {
        return this.aplServizioWeb;
    }

    public void setAplServizioWeb(AplServizioWeb aplServizioWeb) {
        this.aplServizioWeb = aplServizioWeb;
    }

    // bi-directional many-to-one association to PrfUsoRuoloApplic
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USO_RUOLO_APPLIC")
    public PrfUsoRuoloApplic getPrfUsoRuoloApplic() {
        return this.prfUsoRuoloApplic;
    }

    public void setPrfUsoRuoloApplic(PrfUsoRuoloApplic prfUsoRuoloApplic) {
        this.prfUsoRuoloApplic = prfUsoRuoloApplic;
    }
}
