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

package it.eng.saceriam.web.ejb;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.entity.AplEntryMenu;
import it.eng.saceriam.entity.AplPaginaWeb;
import it.eng.saceriam.slite.gen.tablebean.AplApplicTableBean;
import it.eng.saceriam.slite.gen.tablebean.AplEntryMenuTableBean;
import it.eng.saceriam.slite.gen.tablebean.AplPaginaWebTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVAbilOrganizTableBean;
import it.eng.saceriam.viewEntity.UsrVAbilOrganiz;
import it.eng.saceriam.web.helper.AmministrazioneUtentiHelper;
import it.eng.saceriam.web.helper.ComboHelper;
import it.eng.saceriam.web.helper.UserHelper;
import it.eng.saceriam.web.util.Transform;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.base.sorting.SortingRule;
import it.eng.spagoLite.db.base.table.BaseTable;
import it.eng.spagoLite.db.decodemap.DecodeMapIF;
import it.eng.spagoLite.db.oracle.decode.DecodeMap;

/**
 *
 * @author Gilioli_P
 */
@Stateless
@LocalBean
public class ComboEjb {

    public static final String CAMPO_REGISTRO_AG = "registro_ag";
    public static final String CAMPO_TI_GESTIONE_PARAM = "ti_gestione_param";
    public static final String CAMPO_TIPOLOGIA = "tipologia";
    public static final String CAMPO_VALORE = "valore";
    public static final String CAMPO_FLAG = "flag";
    public static final String CAMPO_NOME = "nome";
    public static final String CAMPO_ANNO = "anno";

    @EJB
    private ComboHelper comboHelper;
    @EJB
    private UserHelper userHelper;
    @EJB
    private AmministrazioneUtentiHelper amministrazioneUtentiHelper;

    private static final Logger log = LoggerFactory.getLogger(ComboEjb.class);

    /**
     * Ricava la lista di applicazioni per l'utente che sta per essere creato (sulla base di quelle
     * abilitate all'amministratore che sta inserendo il record)
     *
     * @param idUserIamCorrente l'id dell'utente "amministratore" (quello corrente)
     * @param isUserAdmin       id user Admin
     *
     * @return la mappa con le applicazioni abilitate
     *
     * @throws EMFError errore generico
     */
    public DecodeMapIF getMappaApplicAbilitate(long idUserIamCorrente, boolean isUserAdmin)
	    throws EMFError {
	return getMappaApplicAbilitate(idUserIamCorrente, isUserAdmin, false);
    }

    @Deprecated(forRemoval = true)
    public DecodeMapIF getMappaApplicAbilitateUtente(long idUserIamCorrente,
	    boolean isAppartEnteConvenzAdmin, boolean isEnteOrganoVigilanza) {
	List<AplApplic> applicList = comboHelper.getAplApplicAbilitateUtenteList(idUserIamCorrente,
		isAppartEnteConvenzAdmin, isEnteOrganoVigilanza);
	AplApplicTableBean applicTableBean = new AplApplicTableBean();
	try {
	    if (applicList != null && !applicList.isEmpty()) {
		applicTableBean = (AplApplicTableBean) Transform.entities2TableBean(applicList);
	    }
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	}
	DecodeMap applicDM = DecodeMap.Factory.newInstance(applicTableBean, "id_applic",
		"nm_applic");
	return applicDM;
    }

    /**
     * Ricava la lista di applicazioni per l'utente che sta per essere creato (sulla base di quelle
     * abilitate all'amministratore che sta inserendo il record)
     *
     * @param idUserIamCorrente l'id dell'utente "amministratore" (quello corrente)
     * @param isUserAdmin       id user Admin
     * @param estraiDescApplic  estrae la descrizione dell'applicazione se true, altrimenti il nome
     *                          dell'applicazione
     *
     * @return la mappa con le applicazioni abilitate
     *
     * @throws EMFError errore generico
     */
    public DecodeMapIF getMappaApplicAbilitate(long idUserIamCorrente, boolean isUserAdmin,
	    boolean estraiDescApplic) throws EMFError {
	AplApplicTableBean applicTableBean = new AplApplicTableBean();
	List<AplApplic> applicList = comboHelper.getAplApplicAbilitateList(idUserIamCorrente);
	try {
	    if (applicList != null && !applicList.isEmpty()) {
		applicTableBean = (AplApplicTableBean) Transform.entities2TableBean(applicList);
	    }
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	}
	DecodeMap applicDM = DecodeMap.Factory.newInstance(applicTableBean, "id_applic",
		estraiDescApplic ? "ds_applic" : "nm_applic");
	return applicDM;
    }

    /**
     * Ricava la lista di applicazioni per l'utente che sta per essere creato (sulla base di quelle
     * abilitate all'amministratore che sta inserendo il record)
     *
     * @param idUserIamCorrente l'id dell'utente "amministratore" (quello corrente)
     *
     * @return la mappa con le applicazioni abilitate
     *
     * @throws EMFError errore generico
     */
    public DecodeMapIF getMappaApplicAbilitateRicercaRepliche(long idUserIamCorrente)
	    throws EMFError {
	AplApplicTableBean applicTableBean = new AplApplicTableBean();
	List<AplApplic> applicList = comboHelper
		.getAplApplicAbilitateRicercaReplicheList(idUserIamCorrente);
	try {
	    if (applicList != null && !applicList.isEmpty()) {
		applicTableBean = (AplApplicTableBean) Transform.entities2TableBean(applicList);
	    }
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	}
	DecodeMap applicDM = DecodeMap.Factory.newInstance(applicTableBean, "id_applic",
		"nm_applic");
	return applicDM;
    }

    @Deprecated(forRemoval = true)
    public DecodeMapIF getTipoGestioneAccordo() {
	BaseTable bt = new BaseTable();
	BaseRow br = new BaseRow();
	BaseRow br1 = new BaseRow();
	DecodeMap mappaTipoVarAccordo = new DecodeMap();
	String key = "tipo_var_accordo";
	/* Imposto i valori della combo */
	br.setString(key, "Adeguamento GDPR Allegato A");
	br1.setString(key, "Calcolo servizi su sistema versante");
	bt.add(br);
	bt.add(br1);

	mappaTipoVarAccordo.populatedMap(bt, key, key);
	return mappaTipoVarAccordo;
    }

    @Deprecated(forRemoval = true)
    public DecodeMapIF getMappaPaginePerApplicazione(String applName, boolean sortedByDesc) {
	AplPaginaWebTableBean paginaWebTableBean = new AplPaginaWebTableBean();

	List<AplPaginaWeb> pagineWeb = userHelper.getListAplPaginaWeb(applName);
	try {
	    if (pagineWeb != null && !pagineWeb.isEmpty()) {
		paginaWebTableBean = (AplPaginaWebTableBean) Transform
			.entities2TableBean(pagineWeb);
		if (sortedByDesc) {
		    paginaWebTableBean.addSortingRule(new SortingRule("ds_pagina_web"));
		    paginaWebTableBean.sort();
		}
	    }
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	}
	DecodeMap pagineDM = DecodeMap.Factory.newInstance(paginaWebTableBean, "id_pagina_web",
		"ds_pagina_web");
	return pagineDM;
    }

    public DecodeMapIF getMappaApplicAbilitateConPaginaInfoPrivacy(long idUserIamCorrente,
	    boolean isUserAdmin, boolean estraiDescApplic) {
	AplApplicTableBean applicTableBean = new AplApplicTableBean();

	List<AplApplic> applicList = comboHelper
		.getAplApplicAbilitateInfoPrivacyList(idUserIamCorrente);
	try {
	    if (applicList != null && !applicList.isEmpty()) {
		applicTableBean = (AplApplicTableBean) Transform.entities2TableBean(applicList);
	    }
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	}
	DecodeMap applicDM = DecodeMap.Factory.newInstance(applicTableBean, "id_applic",
		estraiDescApplic ? "ds_applic" : "nm_applic");
	return applicDM;
    }

    public DecodeMapIF getMappaPaginePerApplicazione(BigDecimal idApplic, String tiHelpOnLine,
	    boolean sortedByDesc) {
	AplPaginaWebTableBean paginaWebTableBean = new AplPaginaWebTableBean();

	List<AplPaginaWeb> pagineWeb = userHelper.getListAplPaginaWeb(idApplic, tiHelpOnLine);
	try {
	    if (pagineWeb != null && !pagineWeb.isEmpty()) {
		paginaWebTableBean = (AplPaginaWebTableBean) Transform
			.entities2TableBean(pagineWeb);
		if (sortedByDesc) {
		    paginaWebTableBean.addSortingRule(new SortingRule("ds_pagina_web"));
		    paginaWebTableBean.sort();
		}
	    }
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	}
	DecodeMap pagineDM = DecodeMap.Factory.newInstance(paginaWebTableBean, "id_pagina_web",
		"ds_pagina_web");
	return pagineDM;
    }

    public DecodeMapIF getMappaPaginePerApplicazione(BigDecimal idApplic, boolean sortedByDesc)
	    throws EMFError {
	AplPaginaWebTableBean paginaWebTableBean = new AplPaginaWebTableBean();

	List<AplPaginaWeb> pagineWeb = userHelper.getListAplPaginaWeb(idApplic);
	try {
	    if (pagineWeb != null && !pagineWeb.isEmpty()) {
		paginaWebTableBean = (AplPaginaWebTableBean) Transform
			.entities2TableBean(pagineWeb);
		if (sortedByDesc) {
		    paginaWebTableBean.addSortingRule(new SortingRule("ds_pagina_web"));
		    paginaWebTableBean.sort();
		}
	    }
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	}
	DecodeMap pagineDM = DecodeMap.Factory.newInstance(paginaWebTableBean, "id_pagina_web",
		"ds_pagina_web");
	return pagineDM;
    }

    @Deprecated(forRemoval = true)
    public DecodeMapIF getMappaMenuPerApplicazione(String applName) throws EMFError {
	AplEntryMenuTableBean aplEntryMenuTableBean = new AplEntryMenuTableBean();

	List<AplEntryMenu> entryMenu = userHelper.getListAplEntryMenu(applName);
	try {
	    if (entryMenu != null && !entryMenu.isEmpty()) {
		aplEntryMenuTableBean = (AplEntryMenuTableBean) Transform
			.entities2TableBean(entryMenu);
	    }
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	}
	DecodeMap menuDM = DecodeMap.Factory.newInstance(aplEntryMenuTableBean, "id_entry_menu",
		"nm_entry_menu");
	return menuDM;
    }

    @Deprecated(forRemoval = true)
    public DecodeMapIF getMappaMenuUltimoLivelloPerApplSortedByDesc(String applName)
	    throws EMFError {
	AplEntryMenuTableBean aplEntryMenuTableBean = new AplEntryMenuTableBean();

	List<AplEntryMenu> entryMenu = userHelper.getListAplEntryMenuUltimoLivello(applName);
	try {
	    if (entryMenu != null && !entryMenu.isEmpty()) {
		aplEntryMenuTableBean = (AplEntryMenuTableBean) Transform
			.entities2TableBean(entryMenu);
		aplEntryMenuTableBean.addSortingRule(new SortingRule("nm_entry_menu"));
		aplEntryMenuTableBean.sort();
	    }
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	}
	DecodeMap menuDM = DecodeMap.Factory.newInstance(aplEntryMenuTableBean, "id_entry_menu",
		"ds_entry_menu");
	return menuDM;
    }

    public DecodeMapIF getMappaMenuUltimoLivelloPerApplSortedByDesc(BigDecimal idApplic)
	    throws EMFError {
	AplEntryMenuTableBean aplEntryMenuTableBean = new AplEntryMenuTableBean();

	List<AplEntryMenu> entryMenu = userHelper.getListAplEntryMenuUltimoLivello(idApplic);
	try {
	    if (entryMenu != null && !entryMenu.isEmpty()) {
		aplEntryMenuTableBean = (AplEntryMenuTableBean) Transform
			.entities2TableBean(entryMenu);
		aplEntryMenuTableBean.addSortingRule(new SortingRule("nm_entry_menu"));
		aplEntryMenuTableBean.sort();
	    }
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	}
	DecodeMap menuDM = DecodeMap.Factory.newInstance(aplEntryMenuTableBean, "id_entry_menu",
		"ds_entry_menu");
	return menuDM;
    }

    @Deprecated(forRemoval = true)
    public DecodeMapIF getMappaUsrVAbilOrganiz(BigDecimal idUserIam, BigDecimal idApplic,
	    String nmTipoOrganiz, List<BigDecimal> idOrganizIamDaTogliere) {
	UsrVAbilOrganizTableBean abilOrganizTableBean = new UsrVAbilOrganizTableBean();
	List<UsrVAbilOrganiz> abilOrganizList = amministrazioneUtentiHelper.getUsrVAbilOrganizList(
		idUserIam, idApplic, nmTipoOrganiz, idOrganizIamDaTogliere, true);
	try {
	    if (abilOrganizList != null && !abilOrganizList.isEmpty()) {
		abilOrganizTableBean = (UsrVAbilOrganizTableBean) Transform
			.entities2TableBean(abilOrganizList);
	    }
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	}
	DecodeMap abilOrganizDM = DecodeMap.Factory.newInstance(abilOrganizTableBean,
		"id_organiz_iam", "dl_composito_organiz");
	return abilOrganizDM;
    }

}
