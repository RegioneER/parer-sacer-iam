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
 * The persistent class for the APL_V_TREE_ENTRY_MENU database table.
 *
 */
@Entity
@Table(name = "APL_V_TREE_ENTRY_MENU")
public class AplVTreeEntryMenu implements Serializable {

    private static final long serialVersionUID = 1L;
    private String dlCompositoEntryMenu;
    private String dlLinkEntryMenu;
    private String dlPathIdEntryMenu;
    private String dsEntryMenu;
    private BigDecimal idApplic;
    private BigDecimal idEntryMenu;
    private BigDecimal idEntryMenuPadre;
    private BigDecimal niLivelloEntryMenu;
    private BigDecimal niOrdEntryMenu;
    private String nmEntryMenu;

    public AplVTreeEntryMenu() {
	// document why this constructor is empty
    }

    @Column(name = "DL_COMPOSITO_ENTRY_MENU")
    public String getDlCompositoEntryMenu() {
	return this.dlCompositoEntryMenu;
    }

    public void setDlCompositoEntryMenu(String dlCompositoEntryMenu) {
	this.dlCompositoEntryMenu = dlCompositoEntryMenu;
    }

    @Column(name = "DL_LINK_ENTRY_MENU")
    public String getDlLinkEntryMenu() {
	return this.dlLinkEntryMenu;
    }

    public void setDlLinkEntryMenu(String dlLinkEntryMenu) {
	this.dlLinkEntryMenu = dlLinkEntryMenu;
    }

    @Column(name = "DL_PATH_ID_ENTRY_MENU")
    public String getDlPathIdEntryMenu() {
	return this.dlPathIdEntryMenu;
    }

    public void setDlPathIdEntryMenu(String dlPathIdEntryMenu) {
	this.dlPathIdEntryMenu = dlPathIdEntryMenu;
    }

    @Column(name = "DS_ENTRY_MENU")
    public String getDsEntryMenu() {
	return this.dsEntryMenu;
    }

    public void setDsEntryMenu(String dsEntryMenu) {
	this.dsEntryMenu = dsEntryMenu;
    }

    @Column(name = "ID_APPLIC")
    public BigDecimal getIdApplic() {
	return this.idApplic;
    }

    public void setIdApplic(BigDecimal idApplic) {
	this.idApplic = idApplic;
    }

    @Id
    @Column(name = "ID_ENTRY_MENU")
    public BigDecimal getIdEntryMenu() {
	return this.idEntryMenu;
    }

    public void setIdEntryMenu(BigDecimal idEntryMenu) {
	this.idEntryMenu = idEntryMenu;
    }

    @Column(name = "ID_ENTRY_MENU_PADRE")
    public BigDecimal getIdEntryMenuPadre() {
	return this.idEntryMenuPadre;
    }

    public void setIdEntryMenuPadre(BigDecimal idEntryMenuPadre) {
	this.idEntryMenuPadre = idEntryMenuPadre;
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

    @Column(name = "NM_ENTRY_MENU")
    public String getNmEntryMenu() {
	return this.nmEntryMenu;
    }

    public void setNmEntryMenu(String nmEntryMenu) {
	this.nmEntryMenu = nmEntryMenu;
    }
}
