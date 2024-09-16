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

package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the PRF_V_LIS_AUTOR_RUOLO database table.
 */
@Entity
@Table(name = "PRF_V_LIS_AUTOR_RUOLO")
public class PrfVLisAutorRuolo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dlLinkEntryMenu;

    private String dsAutor;

    private BigDecimal niLivelloEntryMenu;

    private BigDecimal niOrdEntryMenu;

    private String nmApplic;

    private String nmAutor;

    private String nmPaginaWeb;

    private String nmRuolo;

    public PrfVLisAutorRuolo() {
        // document why this constructor is empty
    }

    @Column(name = "DL_LINK_ENTRY_MENU")
    public String getDlLinkEntryMenu() {
        return this.dlLinkEntryMenu;
    }

    public void setDlLinkEntryMenu(String dlLinkEntryMenu) {
        this.dlLinkEntryMenu = dlLinkEntryMenu;
    }

    @Column(name = "DS_AUTOR")
    public String getDsAutor() {
        return this.dsAutor;
    }

    public void setDsAutor(String dsAutor) {
        this.dsAutor = dsAutor;
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

    @Column(name = "NM_AUTOR")
    public String getNmAutor() {
        return this.nmAutor;
    }

    public void setNmAutor(String nmAutor) {
        this.nmAutor = nmAutor;
    }

    @Column(name = "NM_PAGINA_WEB")
    public String getNmPaginaWeb() {
        return this.nmPaginaWeb;
    }

    public void setNmPaginaWeb(String nmPaginaWeb) {
        this.nmPaginaWeb = nmPaginaWeb;
    }

    @Column(name = "NM_RUOLO")
    public String getNmRuolo() {
        return this.nmRuolo;
    }

    public void setNmRuolo(String nmRuolo) {
        this.nmRuolo = nmRuolo;
    }

    private PrfVLisAutorRuoloId prfVLisAutorRuoloId;

    @EmbeddedId()
    public PrfVLisAutorRuoloId getPrfVLisAutorRuoloId() {
        return prfVLisAutorRuoloId;
    }

    public void setPrfVLisAutorRuoloId(PrfVLisAutorRuoloId prfVLisAutorRuoloId) {
        this.prfVLisAutorRuoloId = prfVLisAutorRuoloId;
    }
}
