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

package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the PRF_V_LIS_DICH_AUTOR database table.
 *
 */
@Entity
@Table(name = "PRF_V_LIS_DICH_AUTOR")
public class PrfVLisDichAutor implements Serializable {

    private static final long serialVersionUID = 1L;
    private String dsAzionePagina;
    private String dsEntryMenu;
    private String dsPaginaWeb;
    private String dsPaginaWebAzio;
    private String dsServizioWeb;
    private BigDecimal idApplic;
    private BigDecimal idAzionePagina;
    private BigDecimal idDichAutor;
    private BigDecimal idEntryMenu;
    private BigDecimal idPaginaWeb;
    private BigDecimal idPaginaWebAzio;
    private BigDecimal idRuolo;
    private BigDecimal idServizioWeb;
    private BigDecimal idUsoRuoloApplic;
    private BigDecimal niLivelloEntryMenu;
    private BigDecimal niOrdEntryMenu;
    private String nmApplic;
    private String tiDichAutor;
    private String tiScopoDichAutor;

    public PrfVLisDichAutor() {
	// document why this constructor is empty
    }

    @Column(name = "DS_AZIONE_PAGINA")
    public String getDsAzionePagina() {
	return this.dsAzionePagina;
    }

    public void setDsAzionePagina(String dsAzionePagina) {
	this.dsAzionePagina = dsAzionePagina;
    }

    @Column(name = "DS_ENTRY_MENU")
    public String getDsEntryMenu() {
	return this.dsEntryMenu;
    }

    public void setDsEntryMenu(String dsEntryMenu) {
	this.dsEntryMenu = dsEntryMenu;
    }

    @Column(name = "DS_PAGINA_WEB")
    public String getDsPaginaWeb() {
	return this.dsPaginaWeb;
    }

    public void setDsPaginaWeb(String dsPaginaWeb) {
	this.dsPaginaWeb = dsPaginaWeb;
    }

    @Column(name = "DS_PAGINA_WEB_AZIO")
    public String getDsPaginaWebAzio() {
	return this.dsPaginaWebAzio;
    }

    public void setDsPaginaWebAzio(String dsPaginaWebAzio) {
	this.dsPaginaWebAzio = dsPaginaWebAzio;
    }

    @Column(name = "DS_SERVIZIO_WEB")
    public String getDsServizioWeb() {
	return this.dsServizioWeb;
    }

    public void setDsServizioWeb(String dsServizioWeb) {
	this.dsServizioWeb = dsServizioWeb;
    }

    @Column(name = "ID_APPLIC")
    public BigDecimal getIdApplic() {
	return this.idApplic;
    }

    public void setIdApplic(BigDecimal idApplic) {
	this.idApplic = idApplic;
    }

    @Column(name = "ID_AZIONE_PAGINA")
    public BigDecimal getIdAzionePagina() {
	return this.idAzionePagina;
    }

    public void setIdAzionePagina(BigDecimal idAzionePagina) {
	this.idAzionePagina = idAzionePagina;
    }

    @Id
    @Column(name = "ID_DICH_AUTOR")
    public BigDecimal getIdDichAutor() {
	return this.idDichAutor;
    }

    public void setIdDichAutor(BigDecimal idDichAutor) {
	this.idDichAutor = idDichAutor;
    }

    @Column(name = "ID_ENTRY_MENU")
    public BigDecimal getIdEntryMenu() {
	return this.idEntryMenu;
    }

    public void setIdEntryMenu(BigDecimal idEntryMenu) {
	this.idEntryMenu = idEntryMenu;
    }

    @Column(name = "ID_PAGINA_WEB")
    public BigDecimal getIdPaginaWeb() {
	return this.idPaginaWeb;
    }

    public void setIdPaginaWeb(BigDecimal idPaginaWeb) {
	this.idPaginaWeb = idPaginaWeb;
    }

    @Column(name = "ID_PAGINA_WEB_AZIO")
    public BigDecimal getIdPaginaWebAzio() {
	return this.idPaginaWebAzio;
    }

    public void setIdPaginaWebAzio(BigDecimal idPaginaWebAzio) {
	this.idPaginaWebAzio = idPaginaWebAzio;
    }

    @Column(name = "ID_RUOLO")
    public BigDecimal getIdRuolo() {
	return this.idRuolo;
    }

    public void setIdRuolo(BigDecimal idRuolo) {
	this.idRuolo = idRuolo;
    }

    @Column(name = "ID_SERVIZIO_WEB")
    public BigDecimal getIdServizioWeb() {
	return this.idServizioWeb;
    }

    public void setIdServizioWeb(BigDecimal idServizioWeb) {
	this.idServizioWeb = idServizioWeb;
    }

    @Column(name = "ID_USO_RUOLO_APPLIC")
    public BigDecimal getIdUsoRuoloApplic() {
	return this.idUsoRuoloApplic;
    }

    public void setIdUsoRuoloApplic(BigDecimal idUsoRuoloApplic) {
	this.idUsoRuoloApplic = idUsoRuoloApplic;
    }

    @Column(name = "NI_LIVELLO_ENTRY_MENU")
    public BigDecimal getNiLivelloEntryMenu() {
	return this.niLivelloEntryMenu;
    }

    public void setNiLivelloEntryMenu(BigDecimal niLivelloEntryMenu) {
	this.niLivelloEntryMenu = niLivelloEntryMenu;
    }

    @Column(name = "NI_ORD_ENTRY_MENU")
    public BigDecimal getNiOrdEntryMenu() {
	return this.niOrdEntryMenu;
    }

    public void setNiOrdEntryMenu(BigDecimal niOrdEntryMenu) {
	this.niOrdEntryMenu = niOrdEntryMenu;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
	return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
	this.nmApplic = nmApplic;
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

}
