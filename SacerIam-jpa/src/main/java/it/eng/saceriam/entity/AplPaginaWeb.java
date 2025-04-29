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
 * The persistent class for the APL_PAGINA_WEB database table.
 */
@Entity
@Cacheable(true)
@Table(name = "APL_PAGINA_WEB")
public class AplPaginaWeb implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idPaginaWeb;

    private String dsPaginaWeb;

    private String nmPaginaWeb;

    private String tiHelpOnLine;

    private List<AplAzionePagina> aplAzionePaginas = new ArrayList<>();

    private AplApplic aplApplic;

    private AplEntryMenu aplEntryMenu;

    private List<PrfAutor> prfAutors = new ArrayList<>();

    private List<PrfDichAutor> prfDichAutors = new ArrayList<>();

    private List<AplHelpOnLine> aplHelpOnLines = new ArrayList<>();

    public AplPaginaWeb() {
    }

    @Id
    @Column(name = "ID_PAGINA_WEB")
    @GenericGenerator(name = "SAPL_PAGINA_WEB_ID_PAGINA_WEB_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SAPL_PAGINA_WEB"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAPL_PAGINA_WEB_ID_PAGINA_WEB_GENERATOR")
    public Long getIdPaginaWeb() {
	return this.idPaginaWeb;
    }

    public void setIdPaginaWeb(Long idPaginaWeb) {
	this.idPaginaWeb = idPaginaWeb;
    }

    @Column(name = "DS_PAGINA_WEB")
    public String getDsPaginaWeb() {
	return this.dsPaginaWeb;
    }

    public void setDsPaginaWeb(String dsPaginaWeb) {
	this.dsPaginaWeb = dsPaginaWeb;
    }

    @Column(name = "NM_PAGINA_WEB")
    public String getNmPaginaWeb() {
	return this.nmPaginaWeb;
    }

    public void setNmPaginaWeb(String nmPaginaWeb) {
	this.nmPaginaWeb = nmPaginaWeb;
    }

    @Column(name = "TI_HELP_ON_LINE")
    public String getTiHelpOnLine() {
	return this.tiHelpOnLine;
    }

    public void setTiHelpOnLine(String tiHelpOnLine) {
	this.tiHelpOnLine = tiHelpOnLine;
    }

    // bi-directional many-to-one association to AplAzionePagina
    @OneToMany(mappedBy = "aplPaginaWeb")
    public List<AplAzionePagina> getAplAzionePaginas() {
	return this.aplAzionePaginas;
    }

    public void setAplAzionePaginas(List<AplAzionePagina> aplAzionePaginas) {
	this.aplAzionePaginas = aplAzionePaginas;
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

    // bi-directional many-to-one association to AplEntryMenu
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTRY_MENU")
    public AplEntryMenu getAplEntryMenu() {
	return this.aplEntryMenu;
    }

    public void setAplEntryMenu(AplEntryMenu aplEntryMenu) {
	this.aplEntryMenu = aplEntryMenu;
    }

    // bi-directional many-to-one association to PrfAutor
    @OneToMany(mappedBy = "aplPaginaWeb")
    public List<PrfAutor> getPrfAutors() {
	return this.prfAutors;
    }

    public void setPrfAutors(List<PrfAutor> prfAutors) {
	this.prfAutors = prfAutors;
    }

    // bi-directional many-to-one association to PrfDichAutor
    @OneToMany(mappedBy = "aplPaginaWeb")
    public List<PrfDichAutor> getPrfDichAutors() {
	return this.prfDichAutors;
    }

    public void setPrfDichAutors(List<PrfDichAutor> prfDichAutors) {
	this.prfDichAutors = prfDichAutors;
    }

    // bi-directional many-to-one association to AplHelpOnLine
    @OneToMany(mappedBy = "aplPaginaWeb")
    public List<AplHelpOnLine> getAplHelpOnLines() {
	return this.aplHelpOnLines;
    }

    public void setAplHelpOnLines(List<AplHelpOnLine> aplHelpOnLines) {
	this.aplHelpOnLines = aplHelpOnLines;
    }
}
