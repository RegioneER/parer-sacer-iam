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
 * The persistent class for the PRF_V_LIS_DICH_AUTOR_DA_ALLIN database table.
 *
 */
@Entity
@Table(name = "PRF_V_LIS_DICH_AUTOR_DA_ALLIN")
public class PrfVLisDichAutorDaAllin implements Serializable {

    private static final long serialVersionUID = 1L;
    private String cdVersioneComp;
    private String dsPathEntryMenuFoglia;
    private String dsPathEntryMenuPadre;
    private BigDecimal idDichAutor;
    private BigDecimal idRuolo;
    private String nmApplic;
    private String nmAzionePagina;
    private String nmPaginaWeb;
    private String nmServizioWeb;
    private String tiDichAutor;
    private String tiScopoDichAutor;

    public PrfVLisDichAutorDaAllin() {
	// document why this constructor is empty
    }

    @Column(name = "CD_VERSIONE_COMP")
    public String getCdVersioneComp() {
	return cdVersioneComp;
    }

    public void setCdVersioneComp(String cdVersioneComp) {
	this.cdVersioneComp = cdVersioneComp;
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

    @Id
    @Column(name = "ID_DICH_AUTOR")
    public BigDecimal getIdDichAutor() {
	return this.idDichAutor;
    }

    public void setIdDichAutor(BigDecimal idDichAutor) {
	this.idDichAutor = idDichAutor;
    }

    @Column(name = "ID_RUOLO")
    public BigDecimal getIdRuolo() {
	return this.idRuolo;
    }

    public void setIdRuolo(BigDecimal idRuolo) {
	this.idRuolo = idRuolo;
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

}
