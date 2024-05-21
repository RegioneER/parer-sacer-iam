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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.web.rest.controller;

/**
 *
 * @author Iacolucci_M
 */
public class RecuperoHelpRisposta {

    private String cdEsito;
    private String cdErr;
    private String dlErr;
    private String nmUserId;
    private String cdPsw;
    private String nmApplic;
    private String nmPaginaWeb;
    private String nmEntryMenu;
    private String blHelp;

    public String getCdEsito() {
        return cdEsito;
    }

    public void setCdEsito(String cdEsito) {
        this.cdEsito = cdEsito;
    }

    public String getCdErr() {
        return cdErr;
    }

    public void setCdErr(String cdErr) {
        this.cdErr = cdErr;
    }

    public String getDlErr() {
        return dlErr;
    }

    public void setDlErr(String dlErr) {
        this.dlErr = dlErr;
    }

    public String getNmUserId() {
        return nmUserId;
    }

    public void setNmUserId(String nmUserId) {
        this.nmUserId = nmUserId;
    }

    public String getCdPsw() {
        return cdPsw;
    }

    public void setCdPsw(String cdPsw) {
        this.cdPsw = cdPsw;
    }

    public String getNmApplic() {
        return nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    public String getNmPaginaWeb() {
        return nmPaginaWeb;
    }

    public void setNmPaginaWeb(String nmPaginaWeb) {
        this.nmPaginaWeb = nmPaginaWeb;
    }

    public String getNmEntryMenu() {
        return nmEntryMenu;
    }

    public void setNmEntryMenu(String nmEntryMenu) {
        this.nmEntryMenu = nmEntryMenu;
    }

    public String getBlHelp() {
        return blHelp;
    }

    public void setBlHelp(String blHelp) {
        this.blHelp = blHelp;
    }

}
