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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the APL_V_VIS_HELP_ON_LINE database table.
 *
 */
@Entity
@Table(name = "APL_V_VIS_HELP_ON_LINE")
@NamedQuery(name = "selHelpByIdApplicTiHelpNmPagina", query = "SELECT h FROM AplVVisHelpOnLine h, AplApplic a "
        + "WHERE h.idApplic = a.idApplic " + "AND a.nmApplic=:nmApplic " + "AND h.tiHelpOnLine=:tiHelpOnLine "
        + "AND h.nmPaginaWeb=:nmPaginaWeb " + "AND h.dtIniVal <= :dtRiferimento " + "AND h.dtFineVal >= :dtRiferimento")
@NamedQuery(name = "selHelpByIdApplicTiHelpNmPaginaNmEntryMenu", query = "SELECT h FROM AplVVisHelpOnLine h, AplApplic a "
        + "WHERE h.idApplic = a.idApplic " + "AND a.nmApplic=:nmApplic " + "AND h.tiHelpOnLine=:tiHelpOnLine "
        + "AND h.nmPaginaWeb=:nmPaginaWeb " + "AND h.nmEntryMenu=:nmEntryMenu " + "AND h.dtIniVal <= :dtRiferimento "
        + "AND h.dtFineVal >= :dtRiferimento")
// Query ricerca Help tra date
@NamedQuery(name = "selHelpByIdApplicTiHelpBetweenDate", query = "SELECT h FROM AplVVisHelpOnLine h "
        + "WHERE h.idApplic=:idApplic " + "AND h.tiHelpOnLine=:tiHelpOnLine " + "AND h.dtIniVal<=:dtRiferimento "
        + "AND h.dtFineVal>=:dtRiferimento " + "ORDER BY h.dtIniVal DESC ")
@NamedQuery(name = "selHelpByIdApplicTiHelpBetweenDateAndPage", query = "SELECT h FROM AplVVisHelpOnLine h "
        + "WHERE h.idApplic=:idApplic " + "AND h.tiHelpOnLine=:tiHelpOnLine " + "AND h.dtIniVal<=:dtRiferimento "
        + "AND h.dtFineVal>=:dtRiferimento " + "AND h.idPaginaWeb=:idPaginaWeb " + "ORDER BY h.dtIniVal DESC ")
@NamedQuery(name = "selHelpByIdApplicTiHelpBetweenDateAndPageAndMenu", query = "SELECT h FROM AplVVisHelpOnLine h "
        + "WHERE h.idApplic=:idApplic " + "AND h.tiHelpOnLine=:tiHelpOnLine " + "AND h.dtIniVal<=:dtRiferimento "
        + "AND h.dtFineVal>=:dtRiferimento " + "AND h.idPaginaWeb=:idPaginaWeb " + "AND h.idEntryMenu=:idEntryMenu "
        + "ORDER BY h.dtIniVal DESC ")
// Query ricerca Help senza date
@NamedQuery(name = "selHelpByIdApplicTiHelp", query = "SELECT h FROM AplVVisHelpOnLine h "
        + "WHERE h.idApplic=:idApplic " + "AND h.tiHelpOnLine=:tiHelpOnLine " + "ORDER BY h.dtIniVal DESC ")
@NamedQuery(name = "selHelpByIdApplicTiHelpAndPage", query = "SELECT h FROM AplVVisHelpOnLine h "
        + "WHERE h.idApplic=:idApplic " + "AND h.tiHelpOnLine=:tiHelpOnLine " + "AND h.idPaginaWeb=:idPaginaWeb "
        + "ORDER BY h.dtIniVal DESC ")
@NamedQuery(name = "selHelpByIdApplicTiHelpAndPageAndMenu", query = "SELECT h FROM AplVVisHelpOnLine h "
        + "WHERE h.idApplic=:idApplic " + "AND h.tiHelpOnLine=:tiHelpOnLine " + "AND h.idPaginaWeb=:idPaginaWeb "
        + "AND h.idEntryMenu=:idEntryMenu " + "ORDER BY h.dtIniVal DESC ")
@NamedQuery(name = "selMenuByIdApplicAndIdPaginaWebDips", query = "SELECT h.nmEntryMenu FROM AplVVisHelpOnLine h "
        + "WHERE h.nmApplic=:nmApplic " + "AND h.tiHelpOnLine=:tiHelpOnLine " + "AND h.nmPaginaWeb=:nmPaginaWeb "
        + "AND h.dtIniVal<=:dtRiferimento " + "AND h.dtFineVal>=:dtRiferimento ")
public class AplVVisHelpOnLine implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal idHelpOnLine;
    private BigDecimal idApplic;
    private String nmApplic;
    private String tiHelpOnLine;
    private Date dtIniVal;
    private Date dtFineVal;
    private BigDecimal idPaginaWeb;
    private String nmPaginaWeb;
    private BigDecimal idEntryMenu;
    private String nmEntryMenu;
    private String dsFileHelpOnLine;
    private String blHelpOnLine;
    private String dsPaginaWeb;
    private String dlCompositoEntryMenu;

    @Id
    @Column(name = "ID_HELP_ON_LINE")
    public BigDecimal getIdHelpOnLine() {
        return idHelpOnLine;
    }

    public void setIdHelpOnLine(BigDecimal idHelpOnLine) {
        this.idHelpOnLine = idHelpOnLine;
    }

    @Column(name = "ID_APPLIC")
    public BigDecimal getIdApplic() {
        return idApplic;
    }

    public void setIdApplic(BigDecimal idApplic) {
        this.idApplic = idApplic;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    @Column(name = "TI_HELP_ON_LINE")
    public String getTiHelpOnLine() {
        return tiHelpOnLine;
    }

    public void setTiHelpOnLine(String tiHelpOnLine) {
        this.tiHelpOnLine = tiHelpOnLine;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_INI_VAL")
    public Date getDtIniVal() {
        return dtIniVal;
    }

    public void setDtIniVal(Date dtIniVal) {
        this.dtIniVal = dtIniVal;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_FINE_VAL")
    public Date getDtFineVal() {
        return dtFineVal;
    }

    public void setDtFineVal(Date dtFineVal) {
        this.dtFineVal = dtFineVal;
    }

    @Column(name = "ID_PAGINA_WEB")
    public BigDecimal getIdPaginaWeb() {
        return idPaginaWeb;
    }

    public void setIdPaginaWeb(BigDecimal idPaginaWeb) {
        this.idPaginaWeb = idPaginaWeb;
    }

    @Column(name = "NM_PAGINA_WEB")
    public String getNmPaginaWeb() {
        return nmPaginaWeb;
    }

    public void setNmPaginaWeb(String nmPaginaWeb) {
        this.nmPaginaWeb = nmPaginaWeb;
    }

    @Column(name = "ID_ENTRY_MENU")
    public BigDecimal getIdEntryMenu() {
        return idEntryMenu;
    }

    public void setIdEntryMenu(BigDecimal idEntryMenu) {
        this.idEntryMenu = idEntryMenu;
    }

    @Column(name = "NM_ENTRY_MENU")
    public String getNmEntryMenu() {
        return nmEntryMenu;
    }

    public void setNmEntryMenu(String nmEntryMenu) {
        this.nmEntryMenu = nmEntryMenu;
    }

    @Column(name = "DS_FILE_HELP_ON_LINE")
    public String getDsFileHelpOnLine() {
        return dsFileHelpOnLine;
    }

    public void setDsFileHelpOnLine(String dsFileHelpOnLine) {
        this.dsFileHelpOnLine = dsFileHelpOnLine;
    }

    @Lob
    @Column(name = "BL_HELP_ON_LINE")
    public String getBlHelpOnLine() {
        return blHelpOnLine;
    }

    public void setBlHelpOnLine(String blHelpOnLine) {
        this.blHelpOnLine = blHelpOnLine;
    }

    @Column(name = "DS_PAGINA_WEB")
    public String getDsPaginaWeb() {
        return dsPaginaWeb;
    }

    public void setDsPaginaWeb(String dsPaginaWeb) {
        this.dsPaginaWeb = dsPaginaWeb;
    }

    @Column(name = "DL_COMPOSITO_ENTRY_MENU")
    public String getDlCompositoEntryMenu() {
        return dlCompositoEntryMenu;
    }

    public void setDlCompositoEntryMenu(String dlCompositoEntryMenu) {
        this.dlCompositoEntryMenu = dlCompositoEntryMenu;
    }

}
