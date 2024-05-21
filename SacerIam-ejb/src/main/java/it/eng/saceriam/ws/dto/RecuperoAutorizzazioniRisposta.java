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

package it.eng.saceriam.ws.dto;

import java.util.ArrayList;
import java.util.List;

import it.eng.saceriam.web.util.Constants;
import it.eng.saceriam.web.util.Constants.EsitoServizio;

/**
 *
 * @author Gilioli_P
 */
public class RecuperoAutorizzazioniRisposta {

    private Constants.EsitoServizio cdEsito;
    private String cdErr;
    private String dsErr;
    private Integer idUserIam;
    private String nmUserIam;
    private String nmApplic;
    private Integer idOrganizApplic;
    private String nmOrganizApplic;
    private List<Pagina> pagineList;
    private List<Menu> menuList;
    private List<Azione> azioniList;

    public RecuperoAutorizzazioniRisposta() {
        this.pagineList = new ArrayList();
        this.menuList = new ArrayList();
        this.azioniList = new ArrayList();

    }

    public EsitoServizio getCdEsito() {
        return cdEsito;
    }

    public void setCdEsito(EsitoServizio cdEsito) {
        this.cdEsito = cdEsito;
    }

    public String getCdErr() {
        return cdErr;
    }

    public void setCdErr(String cdErr) {
        this.cdErr = cdErr;
    }

    public String getDsErr() {
        return dsErr;
    }

    public void setDsErr(String dsErr) {
        this.dsErr = dsErr;
    }

    public Integer getIdUserIam() {
        return idUserIam;
    }

    public void setIdUserIam(Integer idUserIam) {
        this.idUserIam = idUserIam;
    }

    public String getNmUserIam() {
        return nmUserIam;
    }

    public void setNmUserIam(String nmUserIam) {
        this.nmUserIam = nmUserIam;
    }

    public String getNmApplic() {
        return nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    public Integer getIdOrganizApplic() {
        return idOrganizApplic;
    }

    public void setIdOrganizApplic(Integer idOrganizApplic) {
        this.idOrganizApplic = idOrganizApplic;
    }

    public String getNmOrganizApplic() {
        return nmOrganizApplic;
    }

    public void setNmOrganizApplic(String nmOrganizApplic) {
        this.nmOrganizApplic = nmOrganizApplic;
    }

    public List<Pagina> getPagineList() {
        return pagineList;
    }

    public void setPagineList(List<Pagina> pagineList) {
        this.pagineList = pagineList;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public List<Azione> getAzioniList() {
        return azioniList;
    }

    public void setAzioniList(List<Azione> azioniList) {
        this.azioniList = azioniList;
    }

    public static class Menu {

        private String nmEntryMenu;
        private String dsEntryMenu;
        private Integer niLivelloEntryMenu;
        private Integer niOrdEntryMenu;
        private String dsLinkEntryMenu;
        // private boolean flHelpPresente;

        public String getNmEntryMenu() {
            return nmEntryMenu;
        }

        public void setNmEntryMenu(String nmEntryMenu) {
            this.nmEntryMenu = nmEntryMenu;
        }

        public String getDsEntryMenu() {
            return dsEntryMenu;
        }

        public void setDsEntryMenu(String dsEntryMenu) {
            this.dsEntryMenu = dsEntryMenu;
        }

        public Integer getNiLivelloEntryMenu() {
            return niLivelloEntryMenu;
        }

        public void setNiLivelloEntryMenu(Integer niLivelloEntryMenu) {
            this.niLivelloEntryMenu = niLivelloEntryMenu;
        }

        public Integer getNiOrdEntryMenu() {
            return niOrdEntryMenu;
        }

        public void setNiOrdEntryMenu(Integer niOrdEntryMenu) {
            this.niOrdEntryMenu = niOrdEntryMenu;
        }

        public String getDsLinkEntryMenu() {
            return dsLinkEntryMenu;
        }

        public void setDsLinkEntryMenu(String dsLinkEntryMenu) {
            this.dsLinkEntryMenu = dsLinkEntryMenu;
        }
        /*
         * public boolean isFlHelpPresente() { return flHelpPresente; }
         * 
         * public void setFlHelpPresente(boolean flHelpPresente) { this.flHelpPresente = flHelpPresente; }
         */
    }

    public static class Pagina {

        private String nmPaginaWeb;
        private boolean flHelpPresente;
        /*
         * Contiene NEL CASO DI SACERDIPS le informazioni sugli help attivi per le varie voci di menù
         */
        private List<HelpDips> helpDipsList;

        public Pagina() {
            helpDipsList = new ArrayList<>();
        }

        public String getNmPaginaWeb() {
            return nmPaginaWeb;
        }

        public void setNmPaginaWeb(String nmPaginaWeb) {
            this.nmPaginaWeb = nmPaginaWeb;
        }

        public boolean isFlHelpPresente() {
            return flHelpPresente;
        }

        public void setFlHelpPresente(boolean flHelpPresente) {
            this.flHelpPresente = flHelpPresente;
        }

        public List<HelpDips> getHelpDipsList() {
            return helpDipsList;
        }

        public void setHelpDipsList(List<HelpDips> helpDipsList) {
            this.helpDipsList = helpDipsList;
        }
    }

    public static class Azione {

        private String nmPaginaWeb;
        private String nmAzionePagina;

        public String getNmPaginaWeb() {
            return nmPaginaWeb;
        }

        public void setNmPaginaWeb(String nmPaginaWeb) {
            this.nmPaginaWeb = nmPaginaWeb;
        }

        public String getNmAzionePagina() {
            return nmAzionePagina;
        }

        public void setNmAzionePagina(String nmAzionePagina) {
            this.nmAzionePagina = nmAzionePagina;
        }
    }

    public static class HelpDips {

        private String nmEntryMenu;

        public HelpDips() {
        }

        public HelpDips(String nmEntryMenu) {
            this.nmEntryMenu = nmEntryMenu;
        }

        public String getNmEntryMenu() {
            return nmEntryMenu;
        }

        public void setNmEntryMenu(String nmEntryMenu) {
            this.nmEntryMenu = nmEntryMenu;
        }

    }
}
