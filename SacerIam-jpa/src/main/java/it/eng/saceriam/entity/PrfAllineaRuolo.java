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
 * The persistent class for the PRF_ALLINEA_RUOLO database table.
 */
@Entity
@Table(name = "PRF_ALLINEA_RUOLO")
public class PrfAllineaRuolo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idAllineaRuolo;

    private String dsPathEntryMenuFoglia;

    private String dsPathEntryMenuPadre;

    private PrfRuolo prfRuolo;

    private String nmApplic;

    private String nmAzionePagina;

    private String nmPaginaWeb;

    private String nmServizioWeb;

    private String tiDichAutor;

    private String tiScopoDichAutor;

    public PrfAllineaRuolo() {
    }

    @Id
    // "PRF_ALLINEA_RUOLO_IDALLINEARUOLO_GENERATOR",
    // sequenceName = "SPRF_ALLINEA_RUOLO",
    // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "PRF_ALLINEA_RUOLO_IDALLINEARUOLO_GENERATOR")
    @Column(name = "ID_ALLINEA_RUOLO")
    @GenericGenerator(name = "SPRF_ALLINEA_RUOLO_ID_ALLINEA_RUOLO_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SPRF_ALLINEA_RUOLO"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SPRF_ALLINEA_RUOLO_ID_ALLINEA_RUOLO_GENERATOR")
    public Long getIdAllineaRuolo() {
	return this.idAllineaRuolo;
    }

    public void setIdAllineaRuolo(Long idAllineaRuolo) {
	this.idAllineaRuolo = idAllineaRuolo;
    }

    @Column(name = "DS_PATH_ENTRY_MENU_FOGLIA")
    public String getDsPathEntryMenuFoglia() {
	return this.dsPathEntryMenuFoglia;
    }

    public void setDsPathEntryMenuFoglia(String dsPathEntryMenuFoglia) {
	this.dsPathEntryMenuFoglia = dsPathEntryMenuFoglia;
    }

    @Column(name = "DS_PATH_ENTRY_MENU_PADRE")
    public String getDsPathEntryMenuPadre() {
	return this.dsPathEntryMenuPadre;
    }

    public void setDsPathEntryMenuPadre(String dsPathEntryMenuPadre) {
	this.dsPathEntryMenuPadre = dsPathEntryMenuPadre;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
	return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
	this.nmApplic = nmApplic;
    }

    @Column(name = "NM_AZIONE_PAGINA")
    public String getNmAzionePagina() {
	return this.nmAzionePagina;
    }

    public void setNmAzionePagina(String nmAzionePagina) {
	this.nmAzionePagina = nmAzionePagina;
    }

    @Column(name = "NM_PAGINA_WEB")
    public String getNmPaginaWeb() {
	return this.nmPaginaWeb;
    }

    public void setNmPaginaWeb(String nmPaginaWeb) {
	this.nmPaginaWeb = nmPaginaWeb;
    }

    @Column(name = "NM_SERVIZIO_WEB")
    public String getNmServizioWeb() {
	return this.nmServizioWeb;
    }

    public void setNmServizioWeb(String nmServizioWeb) {
	this.nmServizioWeb = nmServizioWeb;
    }

    @Column(name = "TI_DICH_AUTOR")
    public String getTiDichAutor() {
	return this.tiDichAutor;
    }

    public void setTiDichAutor(String tiDichAutor) {
	this.tiDichAutor = tiDichAutor;
    }

    @Column(name = "TI_SCOPO_DICH_AUTOR")
    public String getTiScopoDichAutor() {
	return this.tiScopoDichAutor;
    }

    public void setTiScopoDichAutor(String tiScopoDichAutor) {
	this.tiScopoDichAutor = tiScopoDichAutor;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RUOLO")
    public PrfRuolo getPrfRuolo() {
	return this.prfRuolo;
    }

    public void setPrfRuolo(PrfRuolo prfRuolo) {
	this.prfRuolo = prfRuolo;
    }
}
