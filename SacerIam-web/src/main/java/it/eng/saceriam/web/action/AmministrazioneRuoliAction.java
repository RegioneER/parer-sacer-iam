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

package it.eng.saceriam.web.action;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.parer.sacerlog.ejb.SacerLogEjb;
import it.eng.parer.sacerlog.slite.gen.form.GestioneLogEventiForm;
import it.eng.parer.sacerlog.util.LogParam;
import it.eng.parer.sacerlog.util.web.SpagoliteLogUtil;
import it.eng.saceriam.entity.constraint.ConstIamParamApplic;
import it.eng.saceriam.entity.constraint.ConstPrfDichAutor;
import it.eng.saceriam.entity.constraint.ConstPrfRuolo;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.saceriam.job.util.Costanti;
import it.eng.saceriam.slite.gen.Application;
import it.eng.saceriam.slite.gen.action.AmministrazioneRuoliAbstractAction;
import it.eng.saceriam.slite.gen.tablebean.AplApplicRowBean;
import it.eng.saceriam.slite.gen.tablebean.AplApplicTableBean;
import it.eng.saceriam.slite.gen.tablebean.AplApplicTableDescriptor;
import it.eng.saceriam.slite.gen.tablebean.AplAzionePaginaRowBean;
import it.eng.saceriam.slite.gen.tablebean.AplAzionePaginaTableBean;
import it.eng.saceriam.slite.gen.tablebean.AplAzionePaginaTableDescriptor;
import it.eng.saceriam.slite.gen.tablebean.AplEntryMenuRowBean;
import it.eng.saceriam.slite.gen.tablebean.AplEntryMenuTableBean;
import it.eng.saceriam.slite.gen.tablebean.AplPaginaWebTableBean;
import it.eng.saceriam.slite.gen.tablebean.AplPaginaWebTableDescriptor;
import it.eng.saceriam.slite.gen.tablebean.AplServizioWebTableBean;
import it.eng.saceriam.slite.gen.tablebean.AplServizioWebTableDescriptor;
import it.eng.saceriam.slite.gen.tablebean.PrfRuoloCategoriaRowBean;
import it.eng.saceriam.slite.gen.tablebean.PrfRuoloCategoriaTableBean;
import it.eng.saceriam.slite.gen.tablebean.PrfRuoloCategoriaTableDescriptor;
import it.eng.saceriam.slite.gen.tablebean.PrfUsoRuoloApplicRowBean;
import it.eng.saceriam.slite.gen.tablebean.PrfUsoRuoloApplicTableBean;
import it.eng.saceriam.slite.gen.viewbean.AplVTreeEntryMenuTableBean;
import it.eng.saceriam.slite.gen.viewbean.AplVTreeMenuPagAzioRowBean;
import it.eng.saceriam.slite.gen.viewbean.AplVTreeMenuPagAzioTableBean;
import it.eng.saceriam.slite.gen.viewbean.PrfVLisDichAutorRowBean;
import it.eng.saceriam.slite.gen.viewbean.PrfVLisDichAutorTableBean;
import it.eng.saceriam.slite.gen.viewbean.PrfVLisDichAutorTableDescriptor;
import it.eng.saceriam.slite.gen.viewbean.PrfVLisRuoloRowBean;
import it.eng.saceriam.slite.gen.viewbean.PrfVLisRuoloTableBean;
import it.eng.saceriam.slite.gen.viewbean.PrfVLisRuoloTableDescriptor;
import it.eng.saceriam.util.SacerLogConstants;
import it.eng.saceriam.web.dto.PairAuth;
import it.eng.saceriam.web.dto.tree.RoleTree;
import it.eng.saceriam.web.dto.tree.RoleTreeElement;
import it.eng.saceriam.web.ejb.AmministrazioneRuoliEjb;
import it.eng.saceriam.web.ejb.AmministrazioneRuoliEjb.AllineaAmbiente;
import it.eng.saceriam.web.ejb.AuthEjb;
import it.eng.saceriam.web.ejb.ComboEjb;
import it.eng.saceriam.web.util.ApplEnum;
import it.eng.saceriam.web.util.ComboGetter;
import it.eng.saceriam.web.util.Constants;
import it.eng.saceriam.ws.client.SoapClients;
import it.eng.saceriam.ws.client.allineaRuolo.AllineaRuolo;
import it.eng.saceriam.ws.client.allineaRuolo.AllineaRuoloRisposta;
import it.eng.saceriam.ws.client.allineaRuolo.CdEsito;
import it.eng.saceriam.ws.client.allineaRuolo.ListaApplic;
import it.eng.saceriam.ws.client.allineaRuolo.ListaCategRuolo;
import it.eng.saceriam.ws.utils.MessaggiWSBundle;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.base.sorting.SortingRule;
import it.eng.spagoLite.db.base.table.BaseTable;
import it.eng.spagoLite.db.decodemap.DecodeMapIF;
import it.eng.spagoLite.db.oracle.decode.DecodeMap;
import it.eng.spagoLite.form.base.BaseElements;
import it.eng.spagoLite.form.base.BaseElements.Status;
import it.eng.spagoLite.form.tab.TabElement;
import it.eng.spagoLite.message.MessageBox;
import it.eng.spagoLite.security.Secure;
import it.eng.spagoLite.security.SuppressLogging;

@SuppressWarnings("unchecked")
public class AmministrazioneRuoliAction extends AmministrazioneRuoliAbstractAction {

    private static final Logger log = LoggerFactory.getLogger(AmministrazioneRuoliAction.class);

    @EJB(mappedName = "java:app/SacerIam-ejb/AuthEjb")
    private AuthEjb auth;
    @EJB(mappedName = "java:app/SacerIam-ejb/AmministrazioneRuoliEjb")
    private AmministrazioneRuoliEjb amministrazioneRuoliEjb;
    @EJB(mappedName = "java:app/SacerIam-ejb/ComboEjb")
    private ComboEjb comboEjb;
    @EJB(mappedName = "java:app/SacerIam-ejb/ParamHelper")
    private ParamHelper paramHelper;
    @EJB(mappedName = "java:app/sacerlog-ejb/SacerLogEjb")
    private SacerLogEjb sacerLogEjb;

    private static final String TREE_ENTRY_MENU_ATTRIBUTE = "TREE_ENTRY_MENU";
    private static final String TREE_PAGES_ATTRIBUTE = "TREE_PAGES";
    private static final String TREE_ACTIONS_ATTRIBUTE = "TREE_ACTIONS";
    private static final String TREE_ID_NODE_ATTRIBUTE = "Id_node";
    private static final String TREE_NODE_PATH_ATTRIBUTE = "Node_path[]";
    private static final String TREE_ID_DICH_AUTOR_ATTRIBUTE = "Id_dich_autor";
    private static final String TREE_ID_TREE_ATTRIBUTE = "Id_tree";
    private static final String TREE_CHECKED_NODES_ATTRIBUTE = "Checked_nodes[]";
    private static final String TREE_MENU_CHECKED_NODES_ATTRIBUTE = "Menu_checked_nodes[]";

    /*
     * Getter per mantenere un set di categorie, utile per i controlli di coerenza
     *
     * @return il set di applicazioni presenti in sessione
     */
    private Set<String> getCategorieSet() {
	if (getSession().getAttribute("categorieSet") == null) {
	    getSession().setAttribute("categorieSet", new HashSet<String>());
	}
	return (Set<String>) getSession().getAttribute("categorieSet");
    }

    /*
     * Getter per mantenere un set di applicazioni, utile per i controlli di coerenza
     *
     * @return il set di applicazioni presenti in sessione
     */
    private Set<BigDecimal> getApplicationsSet() {
	if (getSession().getAttribute("applicationsSet") == null) {
	    getSession().setAttribute("applicationsSet", new HashSet<BigDecimal>());
	}
	return (Set<BigDecimal>) getSession().getAttribute("applicationsSet");
    }

    private Set<PairAuth> getDichAutorSet(String applicazione) {
	if (getSession().getAttribute("dichAutorSet_" + applicazione) == null) {
	    getSession().setAttribute("dichAutorSet_" + applicazione, new HashSet<PairAuth>());
	}
	return (Set<PairAuth>) getSession().getAttribute("dichAutorSet_" + applicazione);
    }

    private Set<String> getCategorieDeleteList() {
	if (getSession().getAttribute("categorieDeleteList") == null) {
	    getSession().setAttribute("categorieDeleteList", new HashSet<String>());
	}
	return (Set<String>) getSession().getAttribute("categorieDeleteList");
    }

    private Set<BigDecimal> getApplicationsDeleteList() {
	if (getSession().getAttribute("applicationsDeleteList") == null) {
	    getSession().setAttribute("applicationsDeleteList", new HashSet<BigDecimal>());
	}
	return (Set<BigDecimal>) getSession().getAttribute("applicationsDeleteList");
    }

    // FIXME: NON PIU' UTILIZZATA CAUSA ALBERI RUOLI
    @Deprecated
    private Set<BigDecimal> getEntryMenuDeleteList() {
	if (getSession().getAttribute("entryMenuDeleteList") == null) {
	    getSession().setAttribute("entryMenuDeleteList", new HashSet<BigDecimal>());
	}
	return (Set<BigDecimal>) getSession().getAttribute("entryMenuDeleteList");
    }

    // FIXME: NON PIU' UTILIZZATA CAUSA ALBERI RUOLI
    @Deprecated
    private Set<BigDecimal> getPagesDeleteList() {
	if (getSession().getAttribute("pagesDeleteList") == null) {
	    getSession().setAttribute("pagesDeleteList", new HashSet<BigDecimal>());
	}
	return (Set<BigDecimal>) getSession().getAttribute("pagesDeleteList");
    }

    // FIXME: NON PIU' UTILIZZATA CAUSA ALBERI RUOLI
    @Deprecated
    private Set<BigDecimal> getActionsDeleteList() {
	if (getSession().getAttribute("actionsDeleteList") == null) {
	    getSession().setAttribute("actionsDeleteList", new HashSet<BigDecimal>());
	}
	return (Set<BigDecimal>) getSession().getAttribute("actionsDeleteList");
    }

    private Set<BigDecimal> getServicesDeleteList() {
	if (getSession().getAttribute("servicesDeleteList") == null) {
	    getSession().setAttribute("servicesDeleteList", new HashSet<BigDecimal>());
	}
	return (Set<BigDecimal>) getSession().getAttribute("servicesDeleteList");
    }

    private Set<BigDecimal> getServicesDeleteListAppId() {
	if (getSession().getAttribute("servicesDeleteListAppId") == null) {
	    getSession().setAttribute("servicesDeleteListAppId", new HashSet<BigDecimal>());
	}
	return (Set<BigDecimal>) getSession().getAttribute("servicesDeleteListAppId");
    }

    /**
     * Metodo che crea un hashset delle chiavi della lista di dichiarazioni passata. Per evitare NPE
     * nel caso di scopo ALL_ABILITAZIONI, inserisco nel set il valore 0
     *
     * @param prfVLisDichAutorTableBean la lista dichiarazioni
     * @param dichAutor                 il tipo di dichiarazione
     */
    private void populateDichAutorSet(PrfVLisDichAutorTableBean prfVLisDichAutorTableBean,
	    String dichAutor) throws EMFError {
	deleteDichAutorSets();
	for (PrfVLisDichAutorRowBean row : prfVLisDichAutorTableBean) {
	    String applicazione = row.getNmApplic();
	    Set<PairAuth> dichAutorSet = getDichAutorSet(applicazione);
	    if (dichAutor.equals(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name())) {
		BigDecimal value = row.getIdEntryMenu() != null ? row.getIdEntryMenu()
			: BigDecimal.ZERO;
		dichAutorSet.add(new PairAuth(row.getTiScopoDichAutor(), value));
	    } else if (dichAutor.equals(ConstPrfDichAutor.TiDichAutor.PAGINA.name())) {
		BigDecimal value = row.getIdPaginaWeb() != null ? row.getIdPaginaWeb()
			: BigDecimal.ZERO;
		dichAutorSet.add(new PairAuth(row.getTiScopoDichAutor(), value));
	    } else if (dichAutor.equals(ConstPrfDichAutor.TiDichAutor.AZIONE.name())) {
		BigDecimal value = row.getIdPaginaWebAzio() != null ? row.getIdPaginaWebAzio()
			: BigDecimal.ZERO;
		BigDecimal value2 = row.getIdAzionePagina() != null ? row.getIdAzionePagina()
			: BigDecimal.ZERO;
		dichAutorSet
			.add(new PairAuth(row.getTiScopoDichAutor(), new PairAuth(value, value2)));
	    } else if (dichAutor.equals(ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name())) {
		BigDecimal value = row.getIdServizioWeb() != null ? row.getIdServizioWeb()
			: BigDecimal.ZERO;
		dichAutorSet.add(new PairAuth(row.getTiScopoDichAutor(), value));
	    }
	    getSession().setAttribute("dichAutorSet_" + applicazione, dichAutorSet);
	}
    }

    private void populateCategoriaSet(PrfRuoloCategoriaTableBean ruoloCategTB) throws EMFError {
	Set<String> categorieSet = new HashSet<String>();
	for (PrfRuoloCategoriaRowBean ruoloCategRB : ruoloCategTB) {
	    categorieSet.add(ruoloCategRB.getTiCategRuolo());
	}
	getSession().setAttribute("categorieSet", categorieSet);
    }

    private void populateApplicationSet(PrfUsoRuoloApplicTableBean applicTB) throws EMFError {
	Set<BigDecimal> applicationsSet = new HashSet<BigDecimal>();
	for (PrfUsoRuoloApplicRowBean applicRB : applicTB) {
	    applicationsSet.add(applicRB.getIdApplic());
	}
	getSession().setAttribute("applicationsSet", applicationsSet);
    }

    @Override
    public void initOnClick() throws EMFError {
    }

    /**
     * Esegue il caricamento dei dati per la visualizzazione del dettaglio
     *
     * @throws EMFError errore generico
     */
    @Override
    public void loadDettaglio() throws EMFError {
	// Controllo per quale tabella è stato invocato il metodo
	if (getTableName() != null) {
	    if (getTableName().equals(getForm().getListaRuoli().getName())
		    && !getForm().getListaRuoli().getTable().isEmpty()) {
		loadDettaglioRuolo(null);
	    }
	}
    }

    private void loadDettaglioRuolo(BigDecimal idRuolo) throws EMFError {
	if (idRuolo != null) {
	    PrfVLisRuoloRowBean ruoloBean = amministrazioneRuoliEjb.getPrfVLisRuoloRowBean(idRuolo);
	    // Ricavo l'indice di riga nel quale si trova il record nella tabella a video
	    int count = -1;
	    for (PrfVLisRuoloRowBean riga : (PrfVLisRuoloTableBean) getForm().getListaRuoli()
		    .getTable()) {
		count++;
		if (riga.getIdRuolo().compareTo(idRuolo) == 0) {
		    break;
		}
	    }
	    getForm().getListaRuoli().getTable().setCurrentRowIndex(count);
	    getForm().getDettaglioRuolo().copyFromBean(ruoloBean);
	} else {
	    PrfVLisRuoloRowBean ruoloBean = amministrazioneRuoliEjb.getPrfVLisRuoloRowBean(
		    ((PrfVLisRuoloRowBean) getForm().getListaRuoli().getTable().getCurrentRow())
			    .getIdRuolo());
	    getForm().getDettaglioRuolo().copyFromBean(ruoloBean);
	}

	// Imposto i valori nelle combo
	getForm().getDettaglioRuolo().getTi_ruolo().setDecodeMap(ComboGetter.getTiRuolo());
	// getForm().getDettaglioRuolo().getTi_categ_ruolo().setDecodeMap(comboGetter.getTiCategRuolo());
	// In primis imposto che vengano visualizzate le dichiarazioni come alberi
	getForm().getDettaglioRuolo().getShow_tree().setValue(String.valueOf(true));
	getForm().getDettaglioRuolo().getShowList().setDescription("Mostra lista di abilitazioni");

	getForm().getCategorieList().clear();
	getForm().getApplicazioniList().clear();
	getForm().getDichMenuList().clear();
	getForm().getDichPagineList().clear();
	getForm().getDichAzioniList().clear();
	getForm().getDichServiziList().clear();

	// Ricavo l'insieme degli usi del ruolo (dove viene usato)
	Set<BigDecimal> idUsoRuoloSet = (Set<BigDecimal>) getForm().getListaRuoli().getTable()
		.getCurrentRow().getObject("set_usi");

	// Carico la lista delle categorie in cui il ruolo è usato
	PrfRuoloCategoriaTableBean ruoloCategoriaTableBean = amministrazioneRuoliEjb
		.getPrfRuoloCategoriaTableBean(getForm().getDettaglioRuolo().getId_ruolo().parse());
	getForm().getCategorieList().setTable(ruoloCategoriaTableBean);
	getForm().getCategorieList().getTable().setPageSize(10);
	getForm().getCategorieList().getTable().first();

	// Carico la lista delle applicazioni in cui il ruolo è usato
	PrfUsoRuoloApplicTableBean usoRuoloApplic = amministrazioneRuoliEjb
		.getPrfUsoRuoloApplicTableBean(idUsoRuoloSet);
	getForm().getApplicazioniList().setTable(usoRuoloApplic);
	getForm().getApplicazioniList().getTable().setPageSize(10);
	getForm().getApplicazioniList().getTable().first();

	getForm().getDettaglioRuolo().getNm_applic_tree().setDecodeMap(
		DecodeMap.Factory.newInstance(usoRuoloApplic, "id_applic", "nm_applic"));
	getForm().getDettaglioRuolo().getNm_applic_tree()
		.setValue(usoRuoloApplic.getRow(0).getIdApplic().toPlainString());

	// Mostra il bottone solo se il ruolo deve essere allineato sull'ambiente
	final String tiStatoAllinea1 = getForm().getDettaglioRuolo()
		.getTi_stato_rich_allinea_ruoli_1().parse();
	ConstPrfRuolo.TiStatoRichAllineaRuoli tiStatoAllineaEnum1 = ConstPrfRuolo.TiStatoRichAllineaRuoli
		.valueOf(tiStatoAllinea1);
	boolean daAllineare1 = (tiStatoAllineaEnum1 == ConstPrfRuolo.TiStatoRichAllineaRuoli.DA_ALLINEARE
		|| tiStatoAllineaEnum1 == ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_PARZIALE
		|| tiStatoAllineaEnum1 == ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_IN_ERRORE);
	getForm().getDettaglioRuolo().getAllineaRuolo1().setHidden(!daAllineare1);

	final String tiStatoAllinea2 = getForm().getDettaglioRuolo()
		.getTi_stato_rich_allinea_ruoli_2().parse();
	ConstPrfRuolo.TiStatoRichAllineaRuoli tiStatoAllineaEnum2 = ConstPrfRuolo.TiStatoRichAllineaRuoli
		.valueOf(tiStatoAllinea2);
	boolean daAllineare2 = (tiStatoAllineaEnum2 == ConstPrfRuolo.TiStatoRichAllineaRuoli.DA_ALLINEARE
		|| tiStatoAllineaEnum2 == ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_PARZIALE
		|| tiStatoAllineaEnum2 == ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_IN_ERRORE);
	getForm().getDettaglioRuolo().getAllineaRuolo2().setHidden(!daAllineare2);

	// <editor-fold defaultstate="collapsed" desc="Gestione caricamento liste">
	// Versione col bean ordinato
	PrfVLisDichAutorTableBean entryMenu2 = amministrazioneRuoliEjb
		.getPrfDichAutorEntryMenuOrdinati(usoRuoloApplic,
			ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name());
	getForm().getDichMenuList().setTable(entryMenu2);
	getForm().getDichMenuList().getTable().setPageSize(10);
	getForm().getDichMenuList().getTable().first();

	// Carico la lista di dichiarazioni pagina
	PrfVLisDichAutorTableBean pages = amministrazioneRuoliEjb.getPrfDichAutorViewBean(
		idUsoRuoloSet, ConstPrfDichAutor.TiDichAutor.PAGINA.name(), null);
	pages.addSortingRule(PrfVLisDichAutorTableDescriptor.COL_NM_APPLIC, SortingRule.ASC);
	pages.addSortingRule(PrfVLisDichAutorTableDescriptor.COL_DS_PAGINA_WEB, SortingRule.ASC);
	pages.sort();
	getForm().getDichPagineList().setTable(pages);
	getForm().getDichPagineList().getTable().setPageSize(10);
	getForm().getDichPagineList().getTable().first();

	// Carico la lista di dichiarazioni azioni
	PrfVLisDichAutorTableBean actions = amministrazioneRuoliEjb.getPrfDichAutorViewBean(
		idUsoRuoloSet, ConstPrfDichAutor.TiDichAutor.AZIONE.name(), null);
	actions.addSortingRule(PrfVLisDichAutorTableDescriptor.COL_NM_APPLIC, SortingRule.ASC);
	actions.addSortingRule(PrfVLisDichAutorTableDescriptor.COL_DS_PAGINA_WEB_AZIO,
		SortingRule.ASC);
	actions.addSortingRule(PrfVLisDichAutorTableDescriptor.COL_DS_AZIONE_PAGINA,
		SortingRule.ASC);
	actions.sort();
	getForm().getDichAzioniList().setTable(actions);
	getForm().getDichAzioniList().getTable().setPageSize(10);
	getForm().getDichAzioniList().getTable().first();

	// Carico la lista di dichiarazioni servizi
	PrfVLisDichAutorTableBean services = amministrazioneRuoliEjb.getPrfDichAutorViewBean(
		idUsoRuoloSet, ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name(), null);
	services.addSortingRule(PrfVLisDichAutorTableDescriptor.COL_NM_APPLIC, SortingRule.ASC);
	services.addSortingRule(PrfVLisDichAutorTableDescriptor.COL_DS_SERVIZIO_WEB,
		SortingRule.ASC);
	services.sort();
	getForm().getDichServiziList().setTable(services);
	getForm().getDichServiziList().getTable().setPageSize(10);
	getForm().getDichServiziList().getTable().first();
	// Setto in sessione il Set che tiene traccia della situazione
	populateDichAutorSet((PrfVLisDichAutorTableBean) getForm().getDichMenuList().getTable(),
		ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name());
	// </editor-fold>
    }

    /**
     * Esegue la forward per visualizzare le pagine di dettaglio
     *
     * @throws EMFError errore generico
     */
    @Override
    public void dettaglioOnClick() throws EMFError {
	if (getTableName() != null) {
	    // Controllo per quale tabella è stato invocato il metodo
	    if (getTableName().equals(getForm().getListaRuoli().getName())
		    && (getNavigationEvent().equals(NE_DETTAGLIO_VIEW)
			    || getNavigationEvent().equals(NE_NEXT)
			    || getNavigationEvent().equals(NE_PREV))) {
		// Elimino gli attributi relativi alla gestione degli alberi
		deleteTreeSessionAttributes();
		// Carico il dettaglio ruolo in visualizzazione
		getForm().getDichiarazioniListsTabs().setCurrentTab(
			getForm().getDichiarazioniListsTabs().getListaApplicazioni());
		forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO);
		setDettaglioRuoliToViewMode();
	    } else if (getTableName().equals(getForm().getListaRuoli().getName())
		    && (getNavigationEvent().equals(NE_DETTAGLIO_UPDATE)
			    || getNavigationEvent().equals(NE_DETTAGLIO_INSERT))) {
		// Elimino gli attributi relativi alla gestione degli alberi
		deleteTreeSessionAttributes();
		final String flAllineamentoInCorso = getForm().getDettaglioRuolo()
			.getFl_allineamento_in_corso().parse();
		final String tiStatoAllinea1 = getForm().getDettaglioRuolo()
			.getTi_stato_rich_allinea_ruoli_1().parse();
		ConstPrfRuolo.TiStatoRichAllineaRuoli tiStatoAllineaEnum1 = ConstPrfRuolo.TiStatoRichAllineaRuoli
			.valueOf(tiStatoAllinea1);
		final String tiStatoAllinea2 = getForm().getDettaglioRuolo()
			.getTi_stato_rich_allinea_ruoli_2().parse();
		ConstPrfRuolo.TiStatoRichAllineaRuoli tiStatoAllineaEnum2 = ConstPrfRuolo.TiStatoRichAllineaRuoli
			.valueOf(tiStatoAllinea2);

		if (flAllineamentoInCorso.equals("0")
			&& (tiStatoAllineaEnum1 == ConstPrfRuolo.TiStatoRichAllineaRuoli.DA_ALLINEARE
				|| tiStatoAllineaEnum1 == ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_PARZIALE
				|| tiStatoAllineaEnum1 == ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_COMPLETO
				|| tiStatoAllineaEnum1 == ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_IN_ERRORE)
			&& (tiStatoAllineaEnum2 == ConstPrfRuolo.TiStatoRichAllineaRuoli.DA_ALLINEARE
				|| tiStatoAllineaEnum2 == ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_PARZIALE
				|| tiStatoAllineaEnum2 == ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_COMPLETO
				|| tiStatoAllineaEnum2 == ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_IN_ERRORE)) {
		    // Carico il dettaglio ruolo in modifica
		    getForm().getInserimentoWizard().reset();
		    inserimentoWizardPasso1OnEnter();
		} else {
		    getMessageBox()
			    .addError("Ruolo in corso di allineamento. Modifica non consentita");
		}
	    }
	}
    }

    private void deleteTreeSessionAttributes() {
	// Elimino gli attributi relativi alla gestione degli alberi
	Enumeration<String> attributeNames = getSession().getAttributeNames();
	while (attributeNames.hasMoreElements()) {
	    String attribute = attributeNames.nextElement();
	    if (attribute.startsWith("TREE_")) {
		getSession().removeAttribute(attribute);
	    }
	}
    }

    private void refreshDichCategorieFields() throws EMFError {
	getForm().getCategorieFields().getTi_categ_ruolo().clear();
	getForm().getCategorieFields().getTi_categ_ruolo().setEditMode();
	getForm().getCategorieFields().getTi_categ_ruolo()
		.setDecodeMap(ComboGetter.getMappaTiCategRuolo());
    }

    private void refreshDichApplicazioniFields() throws EMFError {
	getForm().getApplicazioniFields().getNm_applic().clear();
	getForm().getApplicazioniFields().getDs_applic().clear();
	getForm().getApplicazioniFields().getNm_applic().setEditMode();
	getForm().getApplicazioniFields().getNm_applic()
		.setDecodeMap(comboEjb.getMappaApplicAbilitate(getUser().getIdUtente(), true));
	// getForm().getDichAutorFields().getNodi_superiori().setValue("\u00A0");
    }

    private void refreshDichAutorMenuFields() {
	getForm().getDichAutorFields().getTi_scopo_dich_autor().clear();
	initTiScopoDichAutor(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name());
	setDichAutorFields(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name());
	getForm().getDichAutorFields().getDs_entry_menu().clear();
	getForm().getDichAutorFields().getDs_entry_menu().setDecodeMap(new DecodeMap());
	// getForm().getDichAutorFields().getNodi_superiori().setValue("\u00A0");
    }

    /**
     * Ricarica il dettaglio ruolo in visualizzazione
     */
    private void setDettaglioRuoliToViewMode() {
	getForm().getListaRuoli().setStatus(BaseElements.Status.view);
	getForm().getDettaglioRuolo().setViewMode();
	getForm().getDettaglioRuolo().getNm_applic_tree().setEditMode();
	getForm().getDettaglioRuolo().getShowList().setEditMode();
	getForm().getDettaglioRuolo().getAllineaRuolo1().setEditMode();
	getForm().getDettaglioRuolo().getAllineaRuolo2().setEditMode();
	getForm().getDettaglioRuolo().getLogEventi().setEditMode();

	// Imposto le liste in dettaglio né modificabili
	getForm().getCategorieList().setUserOperations(false, false, false, false);
	getForm().getApplicazioniList().setUserOperations(false, false, false, false);
	getForm().getDichMenuList().setUserOperations(false, false, false, false);
	getForm().getDichPagineList().setUserOperations(false, false, false, false);
	getForm().getDichAzioniList().setUserOperations(false, false, false, false);
	getForm().getDichServiziList().setUserOperations(false, false, false, false);

	getForm().getDichMenuList().getTi_scopo_dich_autor().setViewMode();
	getForm().getDichMenuList().getDs_entry_menu().setViewMode();

	getForm().getDichPagineList().getTi_scopo_dich_autor().setViewMode();
	getForm().getDichPagineList().getDs_pagina_web().setViewMode();

	getForm().getDichAzioniList().getTi_scopo_dich_autor().setViewMode();
	getForm().getDichAzioniList().getDs_pagina_web_azio().setViewMode();
	getForm().getDichAzioniList().getDs_azione_pagina().setViewMode();

	getForm().getDichServiziList().getTi_scopo_dich_autor().setViewMode();
	getForm().getDichServiziList().getDs_servizio_web().setViewMode();
    }

    /**
     * Esegue i controlli di validazione per l'inserimento di una nuova categoria ruolo
     *
     * @param newRow true se si tratta dell'inserimento di una nuova riga, false in caso di modifica
     *
     * @throws EMFError errore generico
     */
    private void addRowToCategorieRuoliList(boolean newRow) throws EMFError {
	getForm().getCategorieFields().getTi_categ_ruolo().post(getRequest());
	String tiCategRuolo = getForm().getCategorieFields().getTi_categ_ruolo().parse();
	if (tiCategRuolo != null) {
	    if (getCategorieSet().contains(tiCategRuolo)) {
		// Applicazione già presente
		getMessageBox().addError("Tipo categoria ruolo già presente nella lista");
	    } else {
		// Creo la nuova riga
		Set<String> cats = getCategorieSet();
		cats.add(tiCategRuolo);
		getSession().setAttribute("categorieSet", cats);
		PrfRuoloCategoriaRowBean row = new PrfRuoloCategoriaRowBean();
		row.setTiCategRuolo(tiCategRuolo);
		getForm().getCategorieList().getTable().add(row);
		getForm().getCategorieList().getTable().addSortingRule(
			PrfRuoloCategoriaTableDescriptor.COL_TI_CATEG_RUOLO, SortingRule.ASC);
		getForm().getCategorieList().getTable().sort();
	    }
	} else {
	    // Non è stata selezionata alcuna categoria ruolo
	    getMessageBox().addError("Non è stata selezionata alcuna categoria ruolo");
	}
    }

    /**
     * Esegue i controlli di validazione per l'inserimento di una nuova applicazione
     *
     * @param newRow true se si tratta dell'inserimento di una nuova riga, false in caso di modifica
     *
     * @throws EMFError errore generico
     */
    private void addRowToApplicazioniList(boolean newRow) throws EMFError {
	getForm().getApplicazioniFields().getNm_applic().post(getRequest());
	BigDecimal applic = getForm().getApplicazioniFields().getNm_applic().parse();
	if (applic != null) {
	    if (getApplicationsSet().contains(applic)) {
		// Applicazione già presente
		getMessageBox().addError("Applicazione già presente nella lista");
	    } else {
		// Creo la nuova riga
		Set<BigDecimal> apps = getApplicationsSet();
		apps.add(applic);
		getSession().setAttribute("applicationsSet", apps);
		AplApplicRowBean row = amministrazioneRuoliEjb.getAplApplicRowBean(applic);
		getForm().getApplicazioniList().getTable().add(row);
		getForm().getApplicazioniList().getTable()
			.addSortingRule(AplApplicTableDescriptor.COL_NM_APPLIC, SortingRule.ASC);
		getForm().getApplicazioniList().getTable().sort();
	    }
	} else {
	    // Non è stata selezionata alcuna applicazione
	    getMessageBox().addError("Non è stata selezionata alcuna applicazione");
	}
    }

    /**
     * Esegue i controlli di validazione per l'inserimento di un nuovo entry menu
     *
     * @param newRow true se si tratta dell'inserimento di una nuova riga, false in caso di modifica
     *
     * @throws EMFError errore generico
     */
    // FIXME: NON PIU' UTILIZZATA CAUSA ALBERI RUOLI
    @Deprecated
    private void saveEntryMenuDichAutorList(boolean newRow) throws EMFError {
	getForm().getDichAutorFields().getDs_entry_menu().post(getRequest());
	// getForm().getDichAutorFields().getNodi_superiori().post(getRequest());
	getForm().getDichAutorFields().getNm_applic().post(getRequest());
	String applicazione = getForm().getDichAutorFields().getNm_applic().getDecodedValue();
	String scopo = getForm().getDichAutorFields().getTi_scopo_dich_autor().parse();
	BigDecimal entryMenu = getForm().getDichAutorFields().getDs_entry_menu().parse() != null
		? getForm().getDichAutorFields().getDs_entry_menu().parse()
		: BigDecimal.ZERO;
	PairAuth pair = null;
	if (!getForm().getDichMenuList().getTable().isEmpty() && !newRow) {
	    BigDecimal rowEntryMenu = ((PrfVLisDichAutorRowBean) getForm().getDichMenuList()
		    .getTable().getCurrentRow()).getIdEntryMenu() != null
			    ? ((PrfVLisDichAutorRowBean) getForm().getDichMenuList().getTable()
				    .getCurrentRow()).getIdEntryMenu()
			    : BigDecimal.ZERO;
	    pair = new PairAuth(((PrfVLisDichAutorRowBean) getForm().getDichMenuList().getTable()
		    .getCurrentRow()).getTiScopoDichAutor(), rowEntryMenu);
	}
	if (StringUtils.isNotBlank(applicazione)) {
	    if (StringUtils.isNotBlank(scopo)) {
		BigDecimal idUsoRuolo = null;
		if (getForm().getListaRuoli().getStatus().equals(BaseElements.Status.update)) {
		    idUsoRuolo = ((PrfVLisRuoloRowBean) getForm().getListaRuoli().getTable()
			    .getCurrentRow())
			    .getBigDecimal(PrfVLisDichAutorTableDescriptor.COL_ID_USO_RUOLO_APPLIC);
		}
		// Eseguo i controlli sui dati inseriti
		if (scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name())
			&& !entryMenu.equals(BigDecimal.ZERO)) {
		    // Se lo scopo è ALL_ABILITAZIONI e il valore del nodo è diverso da null
		    // ERRORE
		    getMessageBox().addError(
			    "Non è possibile impostare un nodo per lo scopo 'ALL ABILITAZIONI'");
		} else if ((scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name())
			|| scopo.equals(
				ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI_CHILD.name()))
			&& entryMenu.equals(BigDecimal.ZERO)) {
		    // Se lo scopo è UNA_ABILITAZIONI o ALL_ABILITAZIONI_CHILD e il valore del nodo
		    // è uguale a null
		    // ERRORE
		    getMessageBox().addError("Non è stato impostato il valore del nodo");
		} else if (scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name())
			// && amministrazioneHelper.getPrfDichAutorViewBean(idUsoRuolo,
			// ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name(),
			// ActionUtils.getQueryMaxResults(getUser().getConfigurazione(),
			// ActionEnums.Configuration.MAX_RESULT_DICH_ENTRY_MENU.name())).size() > 0
			&& (getDichAutorSet(applicazione).size() > 1
				|| (getDichAutorSet(applicazione).size() == 1 && pair == null))) {
		    // Se lo scopo è ALL_ABILITAZIONI e la lista contiene già altre dichiarazioni
		    // per quella
		    // applicazione
		    // ERRORE
		    getMessageBox().addError(
			    "Non è possibile impostare un nodo per lo scopo 'ALL ABILITAZIONI', sono già presenti altre dichiarazioni nella lista");
		} else if (!scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name())
			&& getDichAutorSet(applicazione).contains(new PairAuth(
				ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name(),
				BigDecimal.ZERO))
			&& ((pair != null && !pair.equals(new PairAuth(
				ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name(),
				BigDecimal.ZERO))) || pair == null)) {
		    // Se è già presente uno scopo ALL_ABILITAZIONI nella lista e si sta inserendo
		    // un'altra
		    // dichiarazione
		    // ERRORE
		    getMessageBox().addError(
			    "La lista contiene già uno scopo 'ALL ABILITAZIONI', non è possibile inserire altre dichiarazioni nella lista");
		} else if (scopo
			.equals(ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI_CHILD.name())
			&& checkDichAutorSetEntryMenuForChilds(entryMenu)) {
		    // Verifico che non siano presenti dichiarazioni 'figlie' del nodo, altrimenti
		    // ERRORE
		    getMessageBox().addError(
			    "Non è possibile impostare la presente dichiarazione, la lista contiene già dichiarazioni gerarchicamente 'figlie' della presente da autorizzare");
		} else if (scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name())
			&& checkDichAutorSetEntryMenuForParents(entryMenu)) {
		    // Se è già presente una dichiarazioni ALL_ABILITAZIONI_CHILD e sto inserendo
		    // una dichiarazione
		    // figlia di questa
		    // ERRORE
		    getMessageBox().addError(
			    "Non è possibile impostare la presente dichiarazione, la lista contiene già dichiarazioni gerarchicamente 'padri' della presente da autorizzare");
		} else if (((idUsoRuolo == null) || (idUsoRuolo != null
			&& amministrazioneRuoliEjb.getPrfDichAutorRowBean(scopo,
				ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name(), entryMenu, null,
				idUsoRuolo) == null))
			&& !getDichAutorSet(applicazione)
				.contains(new PairAuth(scopo, entryMenu))) {
		    // Se la dichiarazione non è già presente nel database o all'interno della lista
		    // (nel caso abbia già
		    // subito modifiche), allora modifico la lista
		    // Prepara record per il salvataggio
		    if (!newRow) {
			// MODIFICA
			// Eseguo la modifica nell'hashset corrispondente alla lista aggiornata
			Set<PairAuth> dichAutorSet = getDichAutorSet(applicazione);
			dichAutorSet.remove(pair);
			dichAutorSet.add(new PairAuth(scopo, entryMenu));
			getSession().setAttribute("dichAutorSet_" + applicazione, dichAutorSet);
			// Modifica la lista
			((PrfVLisDichAutorRowBean) getForm().getDichMenuList().getTable()
				.getCurrentRow()).setTiScopoDichAutor(scopo);
			if (!entryMenu.equals(BigDecimal.ZERO)) {
			    ((PrfVLisDichAutorRowBean) getForm().getDichMenuList().getTable()
				    .getCurrentRow()).setIdEntryMenu(entryMenu);
			    ((PrfVLisDichAutorRowBean) getForm().getDichMenuList().getTable()
				    .getCurrentRow())
				    .setDsEntryMenu(getForm().getDichAutorFields()
					    .getDs_entry_menu().getDecodedValue());
			    // if
			    // (!scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name()))
			    // {
			    // ((PrfVLisDichAutorRowBean)
			    // getForm().getDichMenuList().getTable().getCurrentRow()).setString("nodi_superiori",
			    // getForm().getDichAutorFields().getNodi_superiori().parse());
			    // } else {
			    // ((PrfVLisDichAutorRowBean)
			    // getForm().getDichMenuList().getTable().getCurrentRow()).setString("nodi_superiori",
			    // null);
			    // }
			} else {
			    ((PrfVLisDichAutorRowBean) getForm().getDichMenuList().getTable()
				    .getCurrentRow()).setIdEntryMenu(null);
			    ((PrfVLisDichAutorRowBean) getForm().getDichMenuList().getTable()
				    .getCurrentRow()).setDsEntryMenu(null);
			    ((PrfVLisDichAutorRowBean) getForm().getDichMenuList().getTable()
				    .getCurrentRow()).setString("nodi_superiori", null);
			}
		    } else {
			// INSERIMENTO
			Set<PairAuth> dichAutorSet = getDichAutorSet(applicazione);
			dichAutorSet.add(new PairAuth(scopo, entryMenu));
			getSession().setAttribute("dichAutorSet_" + applicazione, dichAutorSet);
			PrfVLisDichAutorRowBean row = new PrfVLisDichAutorRowBean();
			row.setTiDichAutor(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name());
			row.setTiScopoDichAutor(scopo);
			row.setIdApplic(getForm().getDichAutorFields().getNm_applic().parse());
			row.setNmApplic(
				getForm().getDichAutorFields().getNm_applic().getDecodedValue());
			if (!entryMenu.equals(BigDecimal.ZERO)) {
			    row.setIdEntryMenu(entryMenu);
			    row.setDsEntryMenu(getForm().getDichAutorFields().getDs_entry_menu()
				    .getDecodedValue());
			    // row.setString("nodi_superiori",
			    // getForm().getDichAutorFields().getNodi_superiori().parse());
			}
			getForm().getDichMenuList().getTable().add(row);
			getForm().getDichMenuList().getTable().sort();
		    }
		} else {
		    // ERRORE
		    getMessageBox().addError("Dichiarazione già presente nella lista");
		}
	    } else {
		// ERRORE
		getMessageBox().addError("Non è stato inserito lo scopo");
	    }
	} else {
	    // ERRORE
	    getMessageBox().addError("Non è stata scelta l'applicazione");
	}
    }

    /**
     * Esegue i controlli di validazione per l'inserimento di una nuova pagina
     *
     * @param newRow true se si tratta dell'inserimento di una nuova riga, false in caso di modifica
     *
     * @throws EMFError errore generico
     */
    private void savePagesDichAutorList(boolean newRow) throws EMFError {
	// PAGINA
	getForm().getDichAutorFields().getDs_pagina_web().post(getRequest());
	getForm().getDichAutorFields().getNm_applic().post(getRequest());
	String scopo = getForm().getDichAutorFields().getTi_scopo_dich_autor().parse();
	String applicazione = getForm().getDichAutorFields().getNm_applic().getDecodedValue();
	BigDecimal page = getForm().getDichAutorFields().getDs_pagina_web().parse() != null
		? getForm().getDichAutorFields().getDs_pagina_web().parse()
		: BigDecimal.ZERO;
	PairAuth pair = null;
	if (!getForm().getDichPagineList().getTable().isEmpty() && !newRow) {
	    BigDecimal rowPage = ((PrfVLisDichAutorRowBean) getForm().getDichPagineList().getTable()
		    .getCurrentRow()).getIdPaginaWeb() != null
			    ? ((PrfVLisDichAutorRowBean) getForm().getDichPagineList().getTable()
				    .getCurrentRow()).getIdPaginaWeb()
			    : BigDecimal.ZERO;
	    pair = new PairAuth(((PrfVLisDichAutorRowBean) getForm().getDichPagineList().getTable()
		    .getCurrentRow()).getTiScopoDichAutor(), rowPage);
	}
	if (StringUtils.isNotBlank(applicazione)) {
	    if (StringUtils.isNotBlank(scopo)) {
		BigDecimal idUsoRuolo = null;
		if (getForm().getListaRuoli().getStatus().equals(BaseElements.Status.update)) {
		    idUsoRuolo = ((PrfVLisRuoloRowBean) getForm().getListaRuoli().getTable()
			    .getCurrentRow())
			    .getBigDecimal(PrfVLisDichAutorTableDescriptor.COL_ID_USO_RUOLO_APPLIC);
		}
		// Eseguo i controlli sui dati inseriti
		if (scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name())
			&& !page.equals(BigDecimal.ZERO)) {
		    // ERRORE
		    getMessageBox().addError(
			    "Non è possibile impostare una pagina per lo scopo 'ALL ABILITAZIONI'");
		} else if (scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name())
			&& page.equals(BigDecimal.ZERO)) {
		    // Se lo scopo è UNA_ABILITAZIONE e il valore della pagina è uguale a null
		    // ERRORE
		    getMessageBox().addError("Non è stato impostato il valore della pagina");
		} else if (scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name())
			// && amministrazioneHelper.getPrfDichAutorViewBean(idUsoRuolo,
			// ConstPrfDichAutor.TiDichAutor.PAGINA.name(),
			// ActionUtils.getQueryMaxResults(getUser().getConfigurazione(),
			// ActionEnums.Configuration.MAX_RESULT_DICH_ENTRY_MENU.name())).size() > 0)
			// {
			&& (getDichAutorSet(applicazione).size() > 1
				|| (getDichAutorSet(applicazione).size() == 1 && pair == null))) {
		    // Se lo scopo è ALL_ABILITAZIONI e la lista contiene già altre dichiarazioni
		    // ERRORE
		    getMessageBox().addError(
			    "Non è possibile impostare una pagina per lo scopo 'ALL ABILITAZIONI', sono già presenti altre dichiarazioni nella lista");
		} else if (!scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name())
			&& getDichAutorSet(applicazione).contains(new PairAuth(
				ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name(),
				BigDecimal.ZERO))
			&& ((pair != null && !pair.equals(new PairAuth(
				ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name(),
				BigDecimal.ZERO))) || pair == null)) {
		    // Se è già presente uno scopo ALL_ABILITAZIONI nella lista e si sta inserendo
		    // un'altra
		    // dichiarazione
		    // ERRORE
		    getMessageBox().addError(
			    "La lista contiene già uno scopo 'ALL ABILITAZIONI', non è possibile inserire altre dichiarazioni nella lista");
		} else if (((idUsoRuolo == null) || (idUsoRuolo != null
			&& amministrazioneRuoliEjb.getPrfDichAutorRowBean(scopo,
				ConstPrfDichAutor.TiDichAutor.PAGINA.name(), page, null,
				idUsoRuolo) == null))
			&& !getDichAutorSet(applicazione).contains(new PairAuth(scopo, page))) {
		    // Se la dichiarazione non è già presente nel database o all'interno della lista
		    // (nel caso abbia già
		    // subito modifiche), allora modifico la lista
		    // Eseguo la modifica nell'hashset corrispondente alla lista aggiornata
		    if (!newRow) {
			// MODIFICA
			Set<PairAuth> dichAutorSet = getDichAutorSet(applicazione);
			dichAutorSet.remove(pair);
			dichAutorSet.add(new PairAuth(scopo, page));
			getSession().setAttribute("dichAutorSet_" + applicazione, dichAutorSet);
			// Modifica la lista
			((PrfVLisDichAutorRowBean) getForm().getDichPagineList().getTable()
				.getCurrentRow()).setTiScopoDichAutor(scopo);
			if (!page.equals(BigDecimal.ZERO)) {
			    ((PrfVLisDichAutorRowBean) getForm().getDichPagineList().getTable()
				    .getCurrentRow()).setIdPaginaWeb(page);
			    ((PrfVLisDichAutorRowBean) getForm().getDichPagineList().getTable()
				    .getCurrentRow())
				    .setDsPaginaWeb(getForm().getDichAutorFields()
					    .getDs_pagina_web().getDecodedValue());
			} else {
			    ((PrfVLisDichAutorRowBean) getForm().getDichPagineList().getTable()
				    .getCurrentRow()).setIdPaginaWeb(null);
			    ((PrfVLisDichAutorRowBean) getForm().getDichPagineList().getTable()
				    .getCurrentRow()).setDsPaginaWeb(null);
			}
		    } else {
			// INSERIMENTO
			Set<PairAuth> dichAutorSet = getDichAutorSet(applicazione);
			dichAutorSet.add(new PairAuth(scopo, page));
			getSession().setAttribute("dichAutorSet_" + applicazione, dichAutorSet);
			PrfVLisDichAutorRowBean row = new PrfVLisDichAutorRowBean();
			row.setTiDichAutor(ConstPrfDichAutor.TiDichAutor.PAGINA.name());
			row.setTiScopoDichAutor(scopo);
			row.setIdApplic(getForm().getDichAutorFields().getNm_applic().parse());
			row.setNmApplic(
				getForm().getDichAutorFields().getNm_applic().getDecodedValue());
			if (!page.equals(BigDecimal.ZERO)) {
			    row.setIdPaginaWeb(page);
			    row.setDsPaginaWeb(getForm().getDichAutorFields().getDs_pagina_web()
				    .getDecodedValue());
			}
			getForm().getDichPagineList().getTable().add(row);
			getForm().getDichPagineList().getTable().sort();
		    }
		} else {
		    // ERRORE
		    getMessageBox().addError("Dichiarazione già presente nella lista");
		}
	    } else {
		// ERRORE
		getMessageBox().addError("Non è stato inserito lo scopo");
	    }
	} else {
	    // ERRORE
	    getMessageBox().addError("Non è stata scelta l'applicazione");
	}
    }

    /**
     * Esegue i controlli di validazione per l'inserimento di una nuova azione
     *
     * @param newRow true se si tratta dell'inserimento di una nuova riga, false in caso di modifica
     *
     * @throws EMFError errore generico
     */
    private void saveActionsDichAutorList(boolean newRow) throws EMFError {
	// AZIONE
	getForm().getDichAutorFields().getDs_pagina_web_azio().post(getRequest());
	getForm().getDichAutorFields().getDs_azione_pagina().post(getRequest());
	getForm().getDichAutorFields().getNm_applic().post(getRequest());
	String applicazione = getForm().getDichAutorFields().getNm_applic().getDecodedValue();
	String scopo = getForm().getDichAutorFields().getTi_scopo_dich_autor().parse();
	BigDecimal page = getForm().getDichAutorFields().getDs_pagina_web_azio().parse() != null
		? getForm().getDichAutorFields().getDs_pagina_web_azio().parse()
		: BigDecimal.ZERO;
	BigDecimal action = getForm().getDichAutorFields().getDs_azione_pagina().parse() != null
		? getForm().getDichAutorFields().getDs_azione_pagina().parse()
		: BigDecimal.ZERO;
	PairAuth pair = null;
	if (!getForm().getDichAzioniList().getTable().isEmpty() && !newRow) {
	    BigDecimal rowPage = ((PrfVLisDichAutorRowBean) getForm().getDichAzioniList().getTable()
		    .getCurrentRow()).getIdPaginaWebAzio() != null
			    ? ((PrfVLisDichAutorRowBean) getForm().getDichAzioniList().getTable()
				    .getCurrentRow()).getIdPaginaWebAzio()
			    : BigDecimal.ZERO;
	    BigDecimal rowAction = ((PrfVLisDichAutorRowBean) getForm().getDichAzioniList()
		    .getTable().getCurrentRow()).getIdAzionePagina() != null
			    ? ((PrfVLisDichAutorRowBean) getForm().getDichAzioniList().getTable()
				    .getCurrentRow()).getIdAzionePagina()
			    : BigDecimal.ZERO;
	    pair = new PairAuth(
		    ((PrfVLisDichAutorRowBean) getForm().getDichAzioniList().getTable()
			    .getCurrentRow()).getTiScopoDichAutor(),
		    new PairAuth(rowPage, rowAction));
	}
	if (StringUtils.isNotBlank(applicazione)) {
	    if (StringUtils.isNotBlank(scopo)) {
		BigDecimal idUsoRuolo = null;
		if (getForm().getListaRuoli().getStatus().equals(BaseElements.Status.update)) {
		    idUsoRuolo = ((PrfVLisRuoloRowBean) getForm().getListaRuoli().getTable()
			    .getCurrentRow())
			    .getBigDecimal(PrfVLisDichAutorTableDescriptor.COL_ID_USO_RUOLO_APPLIC);
		}
		// Eseguo i controlli sui dati inseriti
		if (scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name())
			&& (!page.equals(BigDecimal.ZERO) || !action.equals(BigDecimal.ZERO))) {
		    // Se lo scopo è ALL_ABILITAZIONI e il valore del nodo è diverso da null
		    // ERRORE
		    getMessageBox().addError(
			    "Non è possibile impostare pagina o azione per lo scopo 'ALL ABILITAZIONI'");
		} else if (scopo
			.equals(ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI_CHILD.name())
			&& page.equals(BigDecimal.ZERO)) {
		    // Se lo scopo è ALL_ABILITAZIONI_CHILD e il valore della pagina è uguale a null
		    // ERRORE
		    getMessageBox().addError("Non è stato impostato il valore della pagina");
		} else if (scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name())
			&& (page.equals(BigDecimal.ZERO) || action.equals(BigDecimal.ZERO))) {
		    getMessageBox().addError(
			    "Non sono stati impostati i valori di pagina e azione per lo scopo 'UNA_ABILITAZIONE'");
		} else if (scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name())
			// && amministrazioneHelper.getPrfDichAutorViewBean(idUsoRuolo,
			// ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name(),
			// ActionUtils.getQueryMaxResults(getUser().getConfigurazione(),
			// ActionEnums.Configuration.MAX_RESULT_DICH_ENTRY_MENU.name())).size() > 0
			&& (getDichAutorSet(applicazione).size() > 1
				|| (getDichAutorSet(applicazione).size() == 1 && pair == null))) {
		    // Se lo scopo è ALL_ABILITAZIONI e la lista contiene già altre dichiarazioni
		    // ERRORE
		    getMessageBox().addError(
			    "Non è possibile impostare lo scopo 'ALL ABILITAZIONI', sono già presenti altre dichiarazioni nella lista");
		} else if (!scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name())
			&& getDichAutorSet(applicazione).contains(new PairAuth(
				ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name(),
				new PairAuth(BigDecimal.ZERO, BigDecimal.ZERO)))
			&& ((pair != null && !pair.equals(new PairAuth(
				ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name(),
				new PairAuth(BigDecimal.ZERO, BigDecimal.ZERO))))
				|| pair == null)) {
		    // Se è già presente uno scopo ALL_ABILITAZIONI nella lista e si sta inserendo
		    // un'altra
		    // dichiarazione
		    // ERRORE
		    getMessageBox().addError(
			    "La lista contiene già uno scopo 'ALL ABILITAZIONI', non è possibile inserire altre dichiarazioni nella lista");
		} else if (scopo
			.equals(ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI_CHILD.name())
			&& checkDichAutorSetActionForChilds(page)) {
		    // Verifico che non siano presenti dichiarazioni di tipo UNA_ABILITAZIONE
		    // 'figlie' della pagina,
		    // altrimenti
		    // ERRORE
		    getMessageBox().addError(
			    "Non è possibile impostare la presente dichiarazione, la lista contiene già dichiarazioni 'figlie' della presente da autorizzare");
		} else if (scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name())
			&& getDichAutorSet(applicazione).contains(new PairAuth(
				ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI_CHILD.name(),
				new PairAuth(page, BigDecimal.ZERO)))) {
		    // Verifico che non siano presenti dichiarazioni di tipo ALL_ABILITAZIONI_CHILD
		    // 'padri' della
		    // pagina, altrimenti
		    // ERRORE
		    getMessageBox().addError(
			    "Dichiarazione non necessaria, la lista contiene già una dichiarazione con scopo 'ALL_ABILITAZIONI_CHILD' per la presente pagina");
		} else if (((idUsoRuolo == null) || (idUsoRuolo != null
			&& amministrazioneRuoliEjb.getPrfDichAutorRowBean(scopo,
				ConstPrfDichAutor.TiDichAutor.AZIONE.name(), page, action,
				idUsoRuolo) == null))
			&& !getDichAutorSet(applicazione)
				.contains(new PairAuth(scopo, new PairAuth(page, action)))) {
		    // Se la dichiarazione non è già presente nel database o all'interno della lista
		    // (nel caso abbia già
		    // subito modifiche), allora modifico la lista
		    if (!newRow) {
			// MODIFICA
			// Eseguo la modifica nell'hashset corrispondente alla lista aggiornata
			Set<PairAuth> dichAutorSet = getDichAutorSet(applicazione);
			dichAutorSet.remove(pair);
			dichAutorSet.add(new PairAuth(scopo, new PairAuth(page, action)));
			getSession().setAttribute("dichAutorSet_" + applicazione, dichAutorSet);
			// Modifica la lista
			((PrfVLisDichAutorRowBean) getForm().getDichAzioniList().getTable()
				.getCurrentRow()).setTiScopoDichAutor(scopo);
			if (!page.equals(BigDecimal.ZERO)) {
			    ((PrfVLisDichAutorRowBean) getForm().getDichAzioniList().getTable()
				    .getCurrentRow()).setIdPaginaWebAzio(page);
			    ((PrfVLisDichAutorRowBean) getForm().getDichAzioniList().getTable()
				    .getCurrentRow())
				    .setDsPaginaWebAzio(getForm().getDichAutorFields()
					    .getDs_pagina_web_azio().getDecodedValue());
			} else {
			    ((PrfVLisDichAutorRowBean) getForm().getDichAzioniList().getTable()
				    .getCurrentRow()).setIdPaginaWebAzio(null);
			    ((PrfVLisDichAutorRowBean) getForm().getDichAzioniList().getTable()
				    .getCurrentRow()).setDsPaginaWebAzio(null);
			}
			if (!action.equals(BigDecimal.ZERO)) {
			    ((PrfVLisDichAutorRowBean) getForm().getDichAzioniList().getTable()
				    .getCurrentRow()).setIdAzionePagina(action);
			    ((PrfVLisDichAutorRowBean) getForm().getDichAzioniList().getTable()
				    .getCurrentRow())
				    .setDsAzionePagina(getForm().getDichAutorFields()
					    .getDs_azione_pagina().getDecodedValue());
			} else {
			    ((PrfVLisDichAutorRowBean) getForm().getDichAzioniList().getTable()
				    .getCurrentRow()).setIdAzionePagina(null);
			    ((PrfVLisDichAutorRowBean) getForm().getDichAzioniList().getTable()
				    .getCurrentRow()).setDsAzionePagina(null);
			}
		    } else {
			// INSERIMENTO
			Set<PairAuth> dichAutorSet = getDichAutorSet(applicazione);
			dichAutorSet.add(new PairAuth(scopo, new PairAuth(page, action)));
			getSession().setAttribute("dichAutorSet_" + applicazione, dichAutorSet);
			PrfVLisDichAutorRowBean row = new PrfVLisDichAutorRowBean();
			row.setTiDichAutor(ConstPrfDichAutor.TiDichAutor.AZIONE.name());
			row.setTiScopoDichAutor(scopo);
			row.setIdApplic(getForm().getDichAutorFields().getNm_applic().parse());
			row.setNmApplic(
				getForm().getDichAutorFields().getNm_applic().getDecodedValue());
			if (!page.equals(BigDecimal.ZERO)) {
			    row.setIdPaginaWebAzio(page);
			    row.setDsPaginaWebAzio(getForm().getDichAutorFields()
				    .getDs_pagina_web_azio().getDecodedValue());
			}
			if (!action.equals(BigDecimal.ZERO)) {
			    row.setIdAzionePagina(action);
			    row.setDsAzionePagina(getForm().getDichAutorFields()
				    .getDs_azione_pagina().getDecodedValue());
			}
			getForm().getDichAzioniList().getTable().add(row);
			getForm().getDichAzioniList().getTable().sort();
		    }
		} else {
		    // ERRORE
		    getMessageBox().addError("Dichiarazione già presente nella lista");
		}
	    } else {
		// ERRORE
		getMessageBox().addError("Non è stato inserito lo scopo");
	    }
	} else {
	    // ERRORE
	    getMessageBox().addError("Non è stata scelta l'applicazione");
	}
    }

    /**
     * Esegue i controlli di validazione per l'inserimento di un nuovo servizio
     *
     * @param newRow true se si tratta dell'inserimento di una nuova riga, false in caso di modifica
     *
     * @throws EMFError errore generico
     */
    private void saveServicesDichAutorList(boolean newRow) throws EMFError {
	// SERVIZIO
	getForm().getDichAutorFields().getDs_servizio_web().post(getRequest());
	getForm().getDichAutorFields().getNm_applic().post(getRequest());
	String applicazione = getForm().getDichAutorFields().getNm_applic().getDecodedValue();
	String scopo = getForm().getDichAutorFields().getTi_scopo_dich_autor().parse();
	BigDecimal service = getForm().getDichAutorFields().getDs_servizio_web().parse() != null
		? getForm().getDichAutorFields().getDs_servizio_web().parse()
		: BigDecimal.ZERO;
	PairAuth pair = null;
	if (!getForm().getDichServiziList().getTable().isEmpty() && !newRow) {
	    BigDecimal rowService = ((PrfVLisDichAutorRowBean) getForm().getDichServiziList()
		    .getTable().getCurrentRow()).getIdServizioWeb() != null
			    ? ((PrfVLisDichAutorRowBean) getForm().getDichServiziList().getTable()
				    .getCurrentRow()).getIdServizioWeb()
			    : BigDecimal.ZERO;
	    pair = new PairAuth(((PrfVLisDichAutorRowBean) getForm().getDichServiziList().getTable()
		    .getCurrentRow()).getTiScopoDichAutor(), rowService);
	}
	if (StringUtils.isNotBlank(applicazione)) {
	    if (StringUtils.isNotBlank(scopo)) {
		BigDecimal idUsoRuolo = null;
		if (getForm().getListaRuoli().getStatus().equals(BaseElements.Status.update)) {
		    idUsoRuolo = ((PrfVLisRuoloRowBean) getForm().getListaRuoli().getTable()
			    .getCurrentRow())
			    .getBigDecimal(PrfVLisDichAutorTableDescriptor.COL_ID_USO_RUOLO_APPLIC);
		}
		if (scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name())
			&& !service.equals(BigDecimal.ZERO)) {
		    // ERRORE
		    getMessageBox().addError(
			    "Non è possibile impostare un servizio web per lo scopo 'ALL ABILITAZIONI'");
		} else if (scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name())
			&& service.equals(BigDecimal.ZERO)) {
		    // Se lo scopo è UNA_ABILITAZIONE e il valore del servizio è uguale a null
		    // ERRORE
		    getMessageBox().addError("Non è stato impostato il valore del servizio web");
		} else if (scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name())
			// && amministrazioneHelper.getPrfDichAutorViewBean(idUsoRuolo,
			// ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name(),
			// ActionUtils.getQueryMaxResults(getUser().getConfigurazione(),
			// ActionEnums.Configuration.MAX_RESULT_DICH_ENTRY_MENU.name())).size() > 0)
			// {
			&& (getDichAutorSet(applicazione).size() > 1
				|| (getDichAutorSet(applicazione).size() == 1 && pair == null))) {
		    // Se lo scopo è ALL_ABILITAZIONI e la lista contiene già altre dichiarazioni
		    // ERRORE
		    getMessageBox().addError(
			    "Non è possibile impostare un servizio web per lo scopo 'ALL ABILITAZIONI', sono già presenti altre dichiarazioni nella lista");
		} else if (!scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name())
			&& getDichAutorSet(applicazione).contains(new PairAuth(
				ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name(),
				BigDecimal.ZERO))
			&& ((pair != null && !pair.equals(new PairAuth(
				ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name(),
				BigDecimal.ZERO))) || pair == null)) {
		    // Se è già presente uno scopo ALL_ABILITAZIONI nella lista e si sta inserendo
		    // un'altra
		    // dichiarazione
		    // ERRORE
		    getMessageBox().addError(
			    "La lista contiene già uno scopo 'ALL ABILITAZIONI', non è possibile inserire altre dichiarazioni nella lista");
		} else if (((idUsoRuolo == null) || (idUsoRuolo != null
			&& amministrazioneRuoliEjb.getPrfDichAutorRowBean(scopo,
				ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name(), service, null,
				idUsoRuolo) == null))
			&& !getDichAutorSet(applicazione).contains(new PairAuth(scopo, service))) {
		    // INSERIMENTO
		    Set<PairAuth> dichAutorSet = getDichAutorSet(applicazione);
		    dichAutorSet.add(new PairAuth(scopo, service));
		    getSession().setAttribute("dichAutorSet_" + applicazione, dichAutorSet);

		    PrfVLisDichAutorRowBean row = new PrfVLisDichAutorRowBean();
		    row.setTiDichAutor(ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name());
		    row.setTiScopoDichAutor(scopo);
		    row.setIdApplic(getForm().getDichAutorFields().getNm_applic().parse());
		    row.setNmApplic(
			    getForm().getDichAutorFields().getNm_applic().getDecodedValue());
		    if (!service.equals(BigDecimal.ZERO)) {
			row.setIdServizioWeb(service);
			row.setDsServizioWeb(getForm().getDichAutorFields().getDs_servizio_web()
				.getDecodedValue());
		    }
		    getForm().getDichServiziList().getTable().add(row);
		    getForm().getDichServiziList().getTable().sort();
		    // }
		} else {
		    // ERRORE
		    getMessageBox().addError("Dichiarazione già presente nella lista");
		}
	    } else {
		// ERRORE
		getMessageBox().addError("Non è stato inserito lo scopo");
	    }
	} else {
	    // ERRORE
	    getMessageBox().addError("Non è stata scelta l'applicazione");
	}
    }

    /**
     * Carica la pagina di lista dei ruoli
     *
     * @throws EMFError errore generico
     */
    @Secure(action = "Menu.AmministrazioneSistema.GestioneRuoli")
    public void ricercaRuoliPage() throws EMFError {
	getUser().getMenu().reset();
	getUser().getMenu().select("Menu.AmministrazioneSistema.GestioneRuoli");

	// Elimino eventuali attributi in sessione
	removeSessionAttributes();
	// Ottengo i valori per l'allineamento
	String destAllinea1 = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.DESTINATARIO_ALLINEA_RUOLI_1.name(), null, null,
		it.eng.saceriam.common.Constants.TipoIamVGetValAppart.APPLIC);
	if (StringUtils.isNotBlank(destAllinea1) && !destAllinea1.equalsIgnoreCase("nullo")) {
	    getForm().getFiltriRuoli().getTi_stato_rich_allinea_ruoli_1().setHidden(false);
	    getForm().getListaRuoli().getTi_stato_rich_allinea_ruoli_1().setHidden(false);
	    getForm().getDettaglioRuolo().getTi_stato_rich_allinea_ruoli_1().setHidden(false);
	    getForm().getDettaglioRuolo().getAllineaRuolo1().setHidden(false);
	    String descriptionFiltri = getForm().getFiltriRuoli().getTi_stato_rich_allinea_ruoli_1()
		    .getDescription();
	    if (!descriptionFiltri.endsWith(destAllinea1)) {
		getForm().getFiltriRuoli().getTi_stato_rich_allinea_ruoli_1()
			.setDescription(descriptionFiltri + destAllinea1);
	    }
	    getForm().getFiltriRuoli().getTi_stato_rich_allinea_ruoli_1().setDecodeMap(
		    ComboGetter.getMappaSortedGenericEnum("ti_stato_rich_allinea_ruoli",
			    ConstPrfRuolo.TiStatoRichAllineaRuoli.values()));
	    String descriptionLista = getForm().getListaRuoli().getTi_stato_rich_allinea_ruoli_1()
		    .getDescription();
	    if (!descriptionLista.endsWith(destAllinea1)) {
		getForm().getListaRuoli().getTi_stato_rich_allinea_ruoli_1()
			.setDescription(descriptionLista + destAllinea1);
	    }
	    String descriptionStatoDetail = getForm().getDettaglioRuolo()
		    .getTi_stato_rich_allinea_ruoli_1().getDescription();
	    if (!descriptionStatoDetail.endsWith(destAllinea1)) {
		getForm().getDettaglioRuolo().getTi_stato_rich_allinea_ruoli_1()
			.setDescription(descriptionStatoDetail + destAllinea1);
	    }
	    String descriptionEsitoDetail = getForm().getDettaglioRuolo()
		    .getDs_esito_rich_allinea_ruoli_1().getDescription();
	    if (!descriptionEsitoDetail.endsWith(destAllinea1)) {
		getForm().getDettaglioRuolo().getDs_esito_rich_allinea_ruoli_1()
			.setDescription(descriptionEsitoDetail + destAllinea1);
	    }
	    String descriptionButton = getForm().getDettaglioRuolo().getAllineaRuolo1()
		    .getDescription();
	    if (!descriptionButton.endsWith(destAllinea1)) {
		getForm().getDettaglioRuolo().getAllineaRuolo1()
			.setDescription(descriptionButton + destAllinea1);
	    }
	    getForm().getDettaglioRuolo().getAllineaRuolo1().setEditMode();
	} else {
	    getForm().getFiltriRuoli().getTi_stato_rich_allinea_ruoli_1().setHidden(true);
	    getForm().getListaRuoli().getTi_stato_rich_allinea_ruoli_1().setHidden(true);
	    getForm().getDettaglioRuolo().getTi_stato_rich_allinea_ruoli_1().setHidden(true);
	    getForm().getDettaglioRuolo().getDs_esito_rich_allinea_ruoli_1().setHidden(true);
	    getForm().getDettaglioRuolo().getAllineaRuolo1().setHidden(true);
	}
	String destAllinea2 = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.DESTINATARIO_ALLINEA_RUOLI_2.name(), null, null,
		it.eng.saceriam.common.Constants.TipoIamVGetValAppart.APPLIC);
	if (StringUtils.isNotBlank(destAllinea2) && !destAllinea2.equalsIgnoreCase("nullo")) {
	    getForm().getFiltriRuoli().getTi_stato_rich_allinea_ruoli_2().setHidden(false);
	    getForm().getListaRuoli().getTi_stato_rich_allinea_ruoli_2().setHidden(false);
	    getForm().getDettaglioRuolo().getTi_stato_rich_allinea_ruoli_2().setHidden(false);
	    getForm().getDettaglioRuolo().getAllineaRuolo2().setHidden(false);
	    String descriptionFiltri = getForm().getFiltriRuoli().getTi_stato_rich_allinea_ruoli_2()
		    .getDescription();
	    if (!descriptionFiltri.endsWith(destAllinea2)) {
		getForm().getFiltriRuoli().getTi_stato_rich_allinea_ruoli_2()
			.setDescription(descriptionFiltri + destAllinea2);
	    }
	    getForm().getFiltriRuoli().getTi_stato_rich_allinea_ruoli_2().setDecodeMap(
		    ComboGetter.getMappaSortedGenericEnum("ti_stato_rich_allinea_ruoli",
			    ConstPrfRuolo.TiStatoRichAllineaRuoli.values()));
	    String descriptionLista = getForm().getListaRuoli().getTi_stato_rich_allinea_ruoli_2()
		    .getDescription();
	    if (!descriptionLista.endsWith(destAllinea2)) {
		getForm().getListaRuoli().getTi_stato_rich_allinea_ruoli_2()
			.setDescription(descriptionLista + destAllinea2);
	    }
	    String descriptionStatoDetail = getForm().getDettaglioRuolo()
		    .getTi_stato_rich_allinea_ruoli_2().getDescription();
	    if (!descriptionStatoDetail.endsWith(destAllinea2)) {
		getForm().getDettaglioRuolo().getTi_stato_rich_allinea_ruoli_2()
			.setDescription(descriptionStatoDetail + destAllinea2);
	    }
	    String descriptionEsitoDetail = getForm().getDettaglioRuolo()
		    .getDs_esito_rich_allinea_ruoli_2().getDescription();
	    if (!descriptionEsitoDetail.endsWith(destAllinea2)) {
		getForm().getDettaglioRuolo().getDs_esito_rich_allinea_ruoli_2()
			.setDescription(descriptionEsitoDetail + destAllinea2);
	    }
	    String descriptionButton = getForm().getDettaglioRuolo().getAllineaRuolo2()
		    .getDescription();
	    if (!descriptionButton.endsWith(destAllinea2)) {
		getForm().getDettaglioRuolo().getAllineaRuolo2()
			.setDescription(descriptionButton + destAllinea2);
	    }
	    getForm().getDettaglioRuolo().getAllineaRuolo2().setEditMode();
	} else {
	    getForm().getFiltriRuoli().getTi_stato_rich_allinea_ruoli_2().setHidden(true);
	    getForm().getListaRuoli().getTi_stato_rich_allinea_ruoli_2().setHidden(true);
	    getForm().getDettaglioRuolo().getTi_stato_rich_allinea_ruoli_2().setHidden(true);
	    getForm().getDettaglioRuolo().getDs_esito_rich_allinea_ruoli_2().setHidden(true);
	    getForm().getDettaglioRuolo().getAllineaRuolo2().setHidden(true);
	}
	// Popolo la combo delle applicazioni
	getForm().getFiltriRuoli().getNm_applic()
		.setDecodeMap(comboEjb.getMappaApplicAbilitate(getUser().getIdUtente(), true));
	//
	getForm().getFiltriRuoli().getTi_ruolo().setDecodeMap(ComboGetter
		.getMappaSortedGenericEnum("ti_ruolo", ApplEnum.TipoUser.getComboTipiPerRuoli()));
	//
	getForm().getFiltriRuoli().getTi_categ_ruolo()
		.setDecodeMap(ComboGetter.getMappaSortedGenericEnum("ti_categ_ruolo",
			ApplEnum.TipoUser.getComboTipiCategoriaPerRuoli()));

	// Imposto in editmode bottone e combo
	getForm().getFiltriRuoli().setEditMode();
	getForm().getFiltriRuoli().clear();

	// Azzero la lista dei ruoli
	getForm().getListaRuoli().setTable(null);
	setDettaglioRuoliToViewMode();
	forwardToPublisher(Application.Publisher.RICERCA_RUOLI);
    }

    public DecodeMap getMappaApplic() throws EMFError {
	getForm().getFiltriRuoli().getNm_applic().clear();
	AplApplicTableBean applicTableBean = amministrazioneRuoliEjb.getAplApplicTableBean();
	DecodeMap applicDM = DecodeMap.Factory.newInstance(applicTableBean, "id_applic",
		"nm_applic");
	return applicDM;
    }

    @Override
    public void deleteCategorieList() throws EMFError {
	PrfRuoloCategoriaRowBean currentRow = (PrfRuoloCategoriaRowBean) getForm()
		.getCategorieList().getTable().getCurrentRow();
	int rowIndex = getForm().getCategorieList().getTable().getCurrentRowIndex();
	Set<String> categorieDeleteList = getCategorieDeleteList();
	if (currentRow.getTiCategRuolo() != null) {
	    categorieDeleteList.add(currentRow.getTiCategRuolo());
	    getSession().setAttribute("categorieDeleteList", categorieDeleteList);
	}
	getForm().getCategorieList().getTable().remove(rowIndex);
	Set<String> categorieSet = getCategorieSet();
	categorieSet.remove(currentRow.getTiCategRuolo());
	getSession().setAttribute("categorieSet", categorieSet);
    }

    /**
     * Cancella un elemento dalla lista applicazioni La modifica NON ha effetto immediato su DB
     *
     * @throws EMFError errore generico
     */
    @Override
    // TODO: Modificare metodo per gestire l'eliminazione di entrymenu e azioni dall'albero
    public void deleteApplicazioniList() throws EMFError {
	PrfUsoRuoloApplicRowBean currentRow = (PrfUsoRuoloApplicRowBean) getForm()
		.getApplicazioniList().getTable().getCurrentRow();
	int rowIndex = getForm().getApplicazioniList().getTable().getCurrentRowIndex();
	Set<BigDecimal> applicationsDeleteList = getApplicationsDeleteList();
	if (currentRow.getIdApplic() != null) {
	    applicationsDeleteList.add(currentRow.getIdApplic());
	    getSession().setAttribute("applicationsDeleteList", applicationsDeleteList);
	}
	getForm().getApplicazioniList().getTable().remove(rowIndex);
	Set<BigDecimal> applicationsSet = getApplicationsSet();
	applicationsSet.remove(currentRow.getIdApplic());
	getSession().setAttribute("applicationsSet", applicationsSet);

	// Ora però devo togliere anche tutti gli entry menu, azioni, pagine e servizi
	// relativi all'applicazione tolta! Quindi vanno tolti a video e inseriti nelle relative
	// liste da cancellare
	BigDecimal idApplicazione = currentRow.getIdApplic();
	String applicazione = amministrazioneRuoliEjb.getAplApplicRowBean(idApplicazione)
		.getNmApplic();

	// ENTRY MENU
	PrfVLisDichAutorTableBean menu = (PrfVLisDichAutorTableBean) getForm().getDichMenuList()
		.getTable();
	Set<BigDecimal> entryMenuDeleteList = getEntryMenuDeleteList();
	for (int i = 0; i < menu.size(); i++) {
	    if (menu.getRow(i).getNmApplic().equals(applicazione)) {
		// Aggiunto la voce all'elenco di dati da eliminare da DB...
		entryMenuDeleteList.add(menu.getRow(i).getIdDichAutor());
		getSession().setAttribute("entryMenuDeleteList", entryMenuDeleteList);
		// ... e la rimuovo a video...
		getForm().getDichMenuList().getTable().remove(i);
		i--;
	    }
	}

	// PAGINE
	PrfVLisDichAutorTableBean pagine = (PrfVLisDichAutorTableBean) getForm().getDichPagineList()
		.getTable();
	Set<BigDecimal> pagesDeleteList = getPagesDeleteList();
	for (int i = 0; i < pagine.size(); i++) {
	    if (pagine.getRow(i).getNmApplic().equals(applicazione)) {
		pagesDeleteList.add(pagine.getRow(i).getIdDichAutor());
		getSession().setAttribute("pagesDeleteList", pagesDeleteList);
		getForm().getDichPagineList().getTable().remove(i);
		i--;
	    }
	}

	// AZIONI
	PrfVLisDichAutorTableBean azioni = (PrfVLisDichAutorTableBean) getForm().getDichAzioniList()
		.getTable();
	Set<BigDecimal> actionsDeleteList = getActionsDeleteList();
	for (int i = 0; i < azioni.size(); i++) {
	    if (azioni.getRow(i).getNmApplic().equals(applicazione)) {
		actionsDeleteList.add(azioni.getRow(i).getIdDichAutor());
		getSession().setAttribute("actionsDeleteList", actionsDeleteList);
		getForm().getDichAzioniList().getTable().remove(i);
		i--;
	    }
	}

	// SERVIZI
	PrfVLisDichAutorTableBean servizi = (PrfVLisDichAutorTableBean) getForm()
		.getDichServiziList().getTable();
	Set<BigDecimal> servicesDeleteList = getServicesDeleteList();
	for (int i = 0; i < servizi.size(); i++) {
	    if (servizi.getRow(i).getNmApplic().equals(applicazione)) {
		servicesDeleteList.add(servizi.getRow(i).getIdDichAutor());
		getSession().setAttribute("servicesDeleteList", servicesDeleteList);
		getForm().getDichServiziList().getTable().remove(i);
		i--;
	    }
	}

	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO_WIZARD);
    }

    /**
     * Esegue l'eliminazione logica dell'azione selezionata dalla lista dichiarazioni. L'azione
     * verrà eliminata fisicamente al salvataggio del ruolo.
     *
     * @throws EMFError errore generico
     */
    @Override
    public void deleteDichAzioniList() throws EMFError {
	PrfVLisDichAutorRowBean currentRow = (PrfVLisDichAutorRowBean) getForm().getDichAzioniList()
		.getTable().getCurrentRow();
	int rowIndex = getForm().getDichAzioniList().getTable().getCurrentRowIndex();
	Set<BigDecimal> actionsDeleteList = getActionsDeleteList();
	String applicazione = currentRow.getNmApplic();
	if (currentRow.getIdDichAutor() != null) {
	    actionsDeleteList.add(currentRow.getIdDichAutor());
	    getSession().setAttribute("actionsDeleteList", actionsDeleteList);
	}
	getForm().getDichAzioniList().getTable().remove(rowIndex);
	BigDecimal idPagina = currentRow.getIdPaginaWebAzio() != null
		? currentRow.getIdPaginaWebAzio()
		: BigDecimal.ZERO;
	BigDecimal idAzione = currentRow.getIdAzionePagina() != null
		? currentRow.getIdAzionePagina()
		: BigDecimal.ZERO;
	Set<PairAuth> dichAutorSet = getDichAutorSet(applicazione);
	dichAutorSet.remove(
		new PairAuth(currentRow.getTiScopoDichAutor(), new PairAuth(idPagina, idAzione)));
	getSession().setAttribute("dichAutorSet_" + applicazione, dichAutorSet);
	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO_WIZARD);
    }

    /**
     * Esegue l'eliminazione logica dell'entry menu selezionato dalla lista dichiarazioni. L'entry
     * menu verrà eliminato fisicamente al salvataggio del ruolo.
     *
     * @throws EMFError errore generico
     */
    // FIXME: NON PIU' UTILIZZATA CAUSA ALBERI RUOLI
    @Override
    @Deprecated
    public void deleteDichMenuList() throws EMFError {
	PrfVLisDichAutorRowBean currentRow = (PrfVLisDichAutorRowBean) getForm().getDichMenuList()
		.getTable().getCurrentRow();
	int rowIndex = getForm().getDichMenuList().getTable().getCurrentRowIndex();
	Set<BigDecimal> entryMenuDeleteList = getEntryMenuDeleteList();
	String applicazione = currentRow.getNmApplic();
	if (currentRow.getIdDichAutor() != null) {
	    entryMenuDeleteList.add(currentRow.getIdDichAutor());
	    getSession().setAttribute("entryMenuDeleteList", entryMenuDeleteList);
	}
	getForm().getDichMenuList().getTable().remove(rowIndex);
	BigDecimal idEntryMenu = currentRow.getIdEntryMenu() != null ? currentRow.getIdEntryMenu()
		: BigDecimal.ZERO;
	Set<PairAuth> dichAutorSet = getDichAutorSet(applicazione);
	dichAutorSet.remove(new PairAuth(currentRow.getTiScopoDichAutor(), idEntryMenu));
	getSession().setAttribute("dichAutorSet_" + applicazione, dichAutorSet);
	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO_WIZARD);
    }

    /**
     * Esegue l'eliminazione logica della pagina selezionata dalla lista dichiarazioni. La pagina
     * verrà eliminata fisicamente al salvataggio del ruolo.
     *
     * @throws EMFError errore generico
     */
    @Override
    public void deleteDichPagineList() throws EMFError {
	PrfVLisDichAutorRowBean currentRow = (PrfVLisDichAutorRowBean) getForm().getDichPagineList()
		.getTable().getCurrentRow();
	int rowIndex = getForm().getDichPagineList().getTable().getCurrentRowIndex();
	Set<BigDecimal> pagesDeleteList = getPagesDeleteList();
	String applicazione = currentRow.getNmApplic();
	if (currentRow.getIdDichAutor() != null) {
	    pagesDeleteList.add(currentRow.getIdDichAutor());
	    getSession().setAttribute("pagesDeleteList", pagesDeleteList);
	}
	getForm().getDichPagineList().getTable().remove(rowIndex);
	BigDecimal idPagina = currentRow.getIdPaginaWeb() != null ? currentRow.getIdPaginaWeb()
		: BigDecimal.ZERO;
	Set<PairAuth> dichAutorSet = getDichAutorSet(applicazione);
	dichAutorSet.remove(new PairAuth(currentRow.getTiScopoDichAutor(), idPagina));
	getSession().setAttribute("dichAutorSet_" + applicazione, dichAutorSet);
	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO_WIZARD);
    }

    /**
     * Esegue l'eliminazione logica del servizio selezionato dalla lista dichiarazioni. Il servizio
     * verrà eliminato fisicamente al salvataggio del ruolo.
     *
     * @throws EMFError errore generico
     */
    @Override
    public void deleteDichServiziList() throws EMFError {
	PrfVLisDichAutorRowBean currentRow = (PrfVLisDichAutorRowBean) getForm()
		.getDichServiziList().getTable().getCurrentRow();
	int rowIndex = getForm().getDichServiziList().getTable().getCurrentRowIndex();
	Set<BigDecimal> servicesDeleteList = getServicesDeleteList();
	Set<BigDecimal> servicesDeleteListAppId = getServicesDeleteListAppId();
	String applicazione = currentRow.getNmApplic();
	BigDecimal idApplicazione = currentRow.getIdApplic();
	if (idApplicazione != null) {
	    servicesDeleteListAppId.add(currentRow.getIdApplic());
	    getSession().setAttribute("servicesDeleteListAppId", servicesDeleteListAppId);
	}
	if (currentRow.getIdDichAutor() != null) {
	    servicesDeleteList.add(currentRow.getIdDichAutor());
	    getSession().setAttribute("servicesDeleteList", servicesDeleteList);
	}
	getForm().getDichServiziList().getTable().remove(rowIndex);
	BigDecimal idServizio = currentRow.getIdServizioWeb() != null
		? currentRow.getIdServizioWeb()
		: BigDecimal.ZERO;
	Set<PairAuth> dichAutorSet = getDichAutorSet(applicazione);
	dichAutorSet.remove(new PairAuth(currentRow.getTiScopoDichAutor(), idServizio));
	getSession().setAttribute("dichAutorSet_" + applicazione, dichAutorSet);
	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO_WIZARD);
    }

    /**
     * Esegue l'eliminazione del ruolo selezionato
     *
     * @throws EMFError errore generico
     */
    @Override
    public void deleteListaRuoli() throws EMFError {
	PrfVLisRuoloRowBean ruolo = (PrfVLisRuoloRowBean) getForm().getListaRuoli().getTable()
		.getCurrentRow();
	// Recupera le applicazione associate ai servizi del ruolo
	// Ricavo l'insieme degli usi del ruolo (dove viene usato)
	Set<BigDecimal> idUsoRuoloSet = (Set<BigDecimal>) getForm().getListaRuoli().getTable()
		.getCurrentRow().getObject("set_usi");
	PrfVLisDichAutorTableBean services = amministrazioneRuoliEjb.getPrfDichAutorViewBean(
		idUsoRuoloSet, ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name(),
		getForm().getFiltriRuoli().getNm_applic().parse());
	Set<BigDecimal> setApplicServizi = new HashSet();
	for (PrfVLisDichAutorRowBean rowAut : services) {
	    setApplicServizi.add(rowAut.getIdApplic());
	}
	try {
	    /*
	     * Codice aggiuntivo per il logging...
	     */
	    LogParam param = SpagoliteLogUtil.getLogParam(null, getUser().getUsername(),
		    SpagoliteLogUtil.getPageName(this));
	    if (param.getNomePagina().equals(Application.Publisher.DETTAGLIO_RUOLO)) {
		param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
	    } else {
		param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(this.getForm(),
			getForm().getListaRuoli()));
	    }
	    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());

	    String esito = auth.deletePrfRuolo(param, ruolo.getIdRuolo(), setApplicServizi);
	    if (esito.equals(Constants.Esito.OK.name())) {
		int rowIndex = getForm().getListaRuoli().getTable().getCurrentRowIndex();
		getForm().getListaRuoli().getTable().remove(rowIndex);
		getMessageBox().addInfo("Ruolo eliminato con successo");
		getMessageBox().setViewMode(MessageBox.ViewMode.plain);
	    } else {
		getMessageBox().addError(esito);
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
	forwardToPublisher(Application.Publisher.RICERCA_RUOLI);
    }

    /**
     * Ritorna il default publisher di questa action
     *
     * @return La stringa relativa al publisher di default
     */
    @Override
    protected String getDefaultPublsherName() {
	return Application.Publisher.RICERCA_RUOLI;
    }

    /**
     * Verifica se nella lista di entry menu sono già presenti dichiarazioni gerarchicamente
     * 'figlie' dell'entry data come parametro
     *
     * @param entryMenu l'id dell'entry menu
     *
     * @return true se la lista contiene già delle dichiarazioni
     *
     * @throws EMFError errore generico
     */
    // FIXME: NON PIU' UTILIZZATA CAUSA ALBERI RUOLI
    @Deprecated
    private boolean checkDichAutorSetEntryMenuForChilds(BigDecimal entryMenu) throws EMFError {
	boolean result = false;
	AplEntryMenuTableBean figli = amministrazioneRuoliEjb.getEntryMenuChilds(entryMenu);
	if (!figli.isEmpty()) {
	    for (AplEntryMenuRowBean row : figli) {
		String applicazione = amministrazioneRuoliEjb.getAplApplicRowBean(row.getIdApplic())
			.getNmApplic();
		if (getDichAutorSet(applicazione)
			.contains(new PairAuth(
				ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI_CHILD.name(),
				row.getIdEntryMenu()))
			|| getDichAutorSet(applicazione).contains(new PairAuth(
				ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name(),
				row.getIdEntryMenu()))) {
		    result = true;
		    break;
		}
	    }
	}
	return result;
    }

    /**
     * Verifica se nella lista di entry menu sono già presenti dichiarazioni gerarchicamente 'padri'
     * dell'entry data come parametro
     *
     * @param entryMenu l'id dell'entry menu
     *
     * @return true se la lista contiene già delle dichiarazioni
     *
     * @throws EMFError errore generico
     */
    // FIXME: NON PIU' UTILIZZATA CAUSA ALBERI RUOLI
    @Deprecated
    private boolean checkDichAutorSetEntryMenuForParents(BigDecimal entryMenu) throws EMFError {
	boolean result = false;
	AplEntryMenuTableBean padri = amministrazioneRuoliEjb.getEntryMenuParents(entryMenu);
	if (!padri.isEmpty()) {
	    for (AplEntryMenuRowBean row : padri) {
		String applicazione = amministrazioneRuoliEjb.getAplApplicRowBean(row.getIdApplic())
			.getNmApplic();
		if (getDichAutorSet(applicazione).contains(new PairAuth(
			ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI_CHILD.name(),
			row.getIdEntryMenu()))) {
		    result = true;
		    break;
		}
	    }
	}
	return result;
    }

    /**
     * Verifica se nella lista di azioni sono già presenti dichiarazioni gerarchicamente 'figlie'
     * della pagina data come parametro
     *
     * @param pagina l'id della pagina
     *
     * @return true se la lista contiene già delle dichiarazioni
     *
     * @throws EMFError errore generico
     */
    private boolean checkDichAutorSetActionForChilds(BigDecimal page) throws EMFError {
	boolean result = false;
	AplAzionePaginaTableBean figli = amministrazioneRuoliEjb.getActionChilds(page);
	if (!figli.isEmpty()) {
	    for (AplAzionePaginaRowBean row : figli) {
		String applicazione = amministrazioneRuoliEjb
			.getAplApplicFromAction(row.getIdAzionePagina());
		if (getDichAutorSet(applicazione).contains(
			new PairAuth(ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name(),
				new PairAuth(row.getIdPaginaWeb(), row.getIdAzionePagina())))) {
		    result = true;
		    break;
		}
	    }
	}
	return result;
    }

    private boolean checkAutorizzazioniServiziVsMenuAzioni(
	    PrfUsoRuoloApplicTableBean applicTableBean, PrfVLisDichAutorTableBean servizi,
	    PrfVLisDichAutorTableBean menu, PrfVLisDichAutorTableBean azioni) {
	boolean checkOK = true;

	for (PrfUsoRuoloApplicRowBean applic : applicTableBean) {

	    String nmApplic = applic.getString("nm_applic");

	    if (!nmApplic.toUpperCase().contains("SACER_VERSO")) {
		int countServizi = 0;
		int countMenu = 0;
		int countAzioni = 0;

		for (PrfVLisDichAutorRowBean servizio : servizi) {
		    if (servizio.getNmApplic().equals(nmApplic)) {
			countServizi++;
		    }
		}
		for (PrfVLisDichAutorRowBean singoloMenu : menu) {
		    if (singoloMenu.getNmApplic().equals(nmApplic)) {
			countMenu++;
		    }
		}
		for (PrfVLisDichAutorRowBean azione : azioni) {
		    if (azione.getNmApplic().equals(nmApplic)) {
			countAzioni++;
		    }
		}

		// Errore
		if (countServizi == 0 && (countMenu == 0 || countAzioni == 0)) {
		    getMessageBox().addError(
			    "Inserire almeno una dichiarazione per i servizi o, in alternativa, almeno una dichiarazione per menù e azioni per l'applicazione "
				    + nmApplic);
		    checkOK = false;
		    break;
		}
	    }
	}
	return checkOK;
    }

    @Override
    public void process() throws EMFError {
    }

    // TABS RELATIVI AL DETTAGLIO RUOLI
    @Override
    public void tabListaCategorieOnClick() throws EMFError {
	getForm().getDichiarazioniListsTabs()
		.setCurrentTab(getForm().getDichiarazioniListsTabs().getListaCategorie());
	getForm().getDettaglioRuolo().getNm_ruolo().post(getRequest());
	getForm().getDettaglioRuolo().getDs_ruolo().post(getRequest());
	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO);
    }

    @Override
    public void tabListaApplicazioniOnClick() throws EMFError {
	// populateDichAutorSet((PrfVLisDichAutorTableBean)
	// getForm().getDichServiziList().getTable(),
	// ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name());
	getForm().getDichiarazioniListsTabs()
		.setCurrentTab(getForm().getDichiarazioniListsTabs().getListaApplicazioni());
	getForm().getDettaglioRuolo().getNm_ruolo().post(getRequest());
	getForm().getDettaglioRuolo().getDs_ruolo().post(getRequest());
	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO);
    }

    /**
     * Carica la pagina delle dichiarazioni di entry menu. Popola il set delle dichiarazioni e
     * esegue la post dei dati del ruolo in caso di inserimento/modifica
     *
     * @throws EMFError errore generico
     */
    @Override
    public void tabListaEntryMenusDichOnClick() throws EMFError {
	populateDichAutorSet((PrfVLisDichAutorTableBean) getForm().getDichMenuList().getTable(),
		ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name());
	getForm().getDichiarazioniListsTabs()
		.setCurrentTab(getForm().getDichiarazioniListsTabs().getListaEntryMenusDich());
	getForm().getDettaglioRuolo().getNm_ruolo().post(getRequest());
	getForm().getDettaglioRuolo().getDs_ruolo().post(getRequest());
	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO);
    }

    /**
     * Carica la pagina delle dichiarazioni delle pagine. Popola il set delle dichiarazioni e esegue
     * la post dei dati del ruolo in caso di inserimento/modifica
     *
     * @throws EMFError errore generico
     */
    @Override
    public void tabListaPagesDichOnClick() throws EMFError {
	populateDichAutorSet((PrfVLisDichAutorTableBean) getForm().getDichPagineList().getTable(),
		ConstPrfDichAutor.TiDichAutor.PAGINA.name());
	getForm().getDichiarazioniListsTabs()
		.setCurrentTab(getForm().getDichiarazioniListsTabs().getListaPagesDich());
	getForm().getDettaglioRuolo().getNm_ruolo().post(getRequest());
	getForm().getDettaglioRuolo().getDs_ruolo().post(getRequest());
	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO);
    }

    /**
     * Carica la pagina delle dichiarazioni delle azioni. Popola il set delle dichiarazioni e esegue
     * la post dei dati del ruolo in caso di inserimento/modifica
     *
     * @throws EMFError errore generico
     */
    @Override
    public void tabListaActionsDichOnClick() throws EMFError {
	populateDichAutorSet((PrfVLisDichAutorTableBean) getForm().getDichAzioniList().getTable(),
		ConstPrfDichAutor.TiDichAutor.AZIONE.name());
	getForm().getDichiarazioniListsTabs()
		.setCurrentTab(getForm().getDichiarazioniListsTabs().getListaActionsDich());
	getForm().getDettaglioRuolo().getNm_ruolo().post(getRequest());
	getForm().getDettaglioRuolo().getDs_ruolo().post(getRequest());
	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO);
    }

    /**
     * Carica la pagina delle dichiarazioni dei servizi. Popola il set delle dichiarazioni e esegue
     * la post dei dati del ruolo in caso di inserimento/modifica
     *
     * @throws EMFError errore generico
     */
    @Override
    public void tabListaServicesDichOnClick() throws EMFError {
	populateDichAutorSet((PrfVLisDichAutorTableBean) getForm().getDichServiziList().getTable(),
		ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name());
	getForm().getDichiarazioniListsTabs()
		.setCurrentTab(getForm().getDichiarazioniListsTabs().getListaServicesDich());
	getForm().getDettaglioRuolo().getNm_ruolo().post(getRequest());
	getForm().getDettaglioRuolo().getDs_ruolo().post(getRequest());
	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO);
    }

    // METODI DI UPDATE DELLE LISTE RUOLI
    /**
     * Metodo invocato sul bottone di modifica della lista ruoli. Cambia lo stato della lista ruoli
     * e delle liste del dettaglio ruolo
     *
     * @throws EMFError errore generico
     */
    @Override
    public void updateListaRuoli() throws EMFError {
	final String flAllineamentoInCorso = getForm().getDettaglioRuolo()
		.getFl_allineamento_in_corso().parse();
	final String tiStatoAllinea1 = getForm().getDettaglioRuolo()
		.getTi_stato_rich_allinea_ruoli_1().parse();
	ConstPrfRuolo.TiStatoRichAllineaRuoli tiStatoAllineaEnum1 = ConstPrfRuolo.TiStatoRichAllineaRuoli
		.valueOf(tiStatoAllinea1);
	final String tiStatoAllinea2 = getForm().getDettaglioRuolo()
		.getTi_stato_rich_allinea_ruoli_2().parse();
	ConstPrfRuolo.TiStatoRichAllineaRuoli tiStatoAllineaEnum2 = ConstPrfRuolo.TiStatoRichAllineaRuoli
		.valueOf(tiStatoAllinea2);

	if (flAllineamentoInCorso.equals("0")
		&& (tiStatoAllineaEnum1 == ConstPrfRuolo.TiStatoRichAllineaRuoli.DA_ALLINEARE
			|| tiStatoAllineaEnum1 == ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_PARZIALE
			|| tiStatoAllineaEnum1 == ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_COMPLETO
			|| tiStatoAllineaEnum1 == ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_IN_ERRORE)
		&& (tiStatoAllineaEnum2 == ConstPrfRuolo.TiStatoRichAllineaRuoli.DA_ALLINEARE
			|| tiStatoAllineaEnum2 == ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_PARZIALE
			|| tiStatoAllineaEnum2 == ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_COMPLETO
			|| tiStatoAllineaEnum2 == ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_IN_ERRORE)) {
	    getForm().getListaRuoli().setStatus(BaseElements.Status.update);
	    // Mi salvo in sessione un attributo sul nome del ruolo
	    // Mi servirà in fase di controllo univocità
	    getSession().setAttribute("nomeRuolo",
		    getForm().getListaRuoli().getTable().getCurrentRow().getObject("nm_ruolo"));
	    // Imposto le liste come modificabili
	    getForm().getCategorieList().setUserOperations(false, false, false, true);
	    getForm().getApplicazioniList().setUserOperations(false, false, false, true);
	    getForm().getDichMenuList().setUserOperations(false, false, false, true);
	    getForm().getDichPagineList().setUserOperations(false, false, false, true);
	    getForm().getDichAzioniList().setUserOperations(false, false, false, true);
	    getForm().getDichServiziList().setUserOperations(false, false, false, true);
	} else {
	    forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO);
	}
    }

    /**
     * Mette in edit mode i campi della form di inserimento elementi della lista in base al tipo
     * passato come parametro
     *
     * @param type la lista in modifica: ENTRY_MENU,PAGINA,AZIONE,SERVIZIO
     */
    private void setDichAutorFields(String type) {
	getForm().getDichAutorFields().getDs_entry_menu().setViewMode();
	// getForm().getDichAutorFields().getNodi_superiori().setViewMode();
	getForm().getDichAutorFields().getDs_pagina_web().setViewMode();
	getForm().getDichAutorFields().getDs_pagina_web_azio().setViewMode();
	getForm().getDichAutorFields().getDs_azione_pagina().setViewMode();
	getForm().getDichAutorFields().getDs_servizio_web().setViewMode();
	// Setto in EditMode i campi in base al tipo
	getForm().getDichAutorFields().getTi_scopo_dich_autor().setEditMode();
	if (type.equals(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name())) {
	    getForm().getDichAutorFields().getDs_entry_menu().setEditMode();
	} else if (type.equals(ConstPrfDichAutor.TiDichAutor.PAGINA.name())) {
	    getForm().getDichAutorFields().getDs_pagina_web().setEditMode();
	} else if (type.equals(ConstPrfDichAutor.TiDichAutor.AZIONE.name())) {
	    getForm().getDichAutorFields().getDs_pagina_web_azio().setEditMode();
	    getForm().getDichAutorFields().getDs_azione_pagina().setEditMode();
	} else if (type.equals(ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name())) {
	    getForm().getDichAutorFields().getDs_servizio_web().setEditMode();
	}
    }

    /**
     * Inizializza il campo di scopo dichiarazione in base alla lista da modificare
     *
     * @param dichAutor la lista in modifica: ENTRY_MENU,PAGINA,AZIONE,SERVIZIO
     */
    private void initTiScopoDichAutor(String dichAutor) {
	BaseTable bt = new BaseTable();
	BaseRow br = new BaseRow();
	BaseRow br1 = new BaseRow();
	BaseRow br2 = new BaseRow();
	DecodeMap tiScopoDichAutor = new DecodeMap();
	br.setString("scopo", ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name());
	br1.setString("scopo", ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI_CHILD.name());
	br2.setString("scopo", ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name());
	bt.add(br);
	if (dichAutor.equals(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name())
		|| dichAutor.equals(ConstPrfDichAutor.TiDichAutor.AZIONE.name())) {
	    bt.add(br1);
	}
	bt.add(br2);
	tiScopoDichAutor.populatedMap(bt, "scopo", "scopo");
	getForm().getDichAutorFields().getTi_scopo_dich_autor().setDecodeMap(tiScopoDichAutor);
    }

    @Override
    public JSONObject triggerApplicazioniFieldsNm_applicOnTrigger() throws EMFError {
	getForm().getApplicazioniFields().post(getRequest());
	BigDecimal applic = getForm().getApplicazioniFields().getNm_applic().parse();
	if (applic != null) {
	    AplApplicRowBean aplApplicById = amministrazioneRuoliEjb.getAplApplicRowBean(applic);
	    getForm().getApplicazioniFields().getDs_applic().setValue(aplApplicById.getDsApplic());
	} else {
	    getForm().getApplicazioniFields().getDs_applic().setValue("\u00A0");
	}
	return getForm().getApplicazioniFields().asJSON();
    }

    @Override
    public JSONObject triggerDichAutorFieldsNm_applicOnTrigger() throws EMFError {
	getForm().getDichAutorFields().post(getRequest());
	// Azzera le altre combo
	getForm().getDichAutorFields().getTi_scopo_dich_autor().clear();
	getForm().getDichAutorFields().getDs_entry_menu().setDecodeMap(new DecodeMap());
	getForm().getDichAutorFields().getDs_pagina_web().setDecodeMap(new DecodeMap());
	getForm().getDichAutorFields().getDs_pagina_web_azio().setDecodeMap(new DecodeMap());
	getForm().getDichAutorFields().getDs_azione_pagina().setDecodeMap(new DecodeMap());
	getForm().getDichAutorFields().getDs_servizio_web().setDecodeMap(new DecodeMap());
	return getForm().getDichAutorFields().asJSON();
    }

    /**
     * Una volta scelto il tipo scopo, in base al quale vengono popolati i campi di inserimento
     * entry menu,pagina, azione o servizio
     *
     * @return la form aggiornata in formato JSONObject
     *
     * @throws EMFError errore generico
     */
    @Override
    public JSONObject triggerDichAutorFieldsTi_scopo_dich_autorOnTrigger() throws EMFError {
	getForm().getDichAutorFields().post(getRequest());
	String scopo = getForm().getDichAutorFields().getTi_scopo_dich_autor().parse();
	setDescriptionCombos(scopo);

	return getForm().getDichAutorFields().asJSON();
    }

    /**
     * Selezionata la pagina da inserire carica le azioni per quella pagina
     *
     * @return la form aggiornata in formato JSONObject
     *
     * @throws EMFError errore generico
     */
    @Override
    public JSONObject triggerDichAutorFieldsDs_pagina_web_azioOnTrigger() throws EMFError {
	getForm().getDichAutorFields().post(getRequest());
	String scopo = getForm().getDichAutorFields().getTi_scopo_dich_autor().parse();
	String idPagina = getForm().getDichAutorFields().getDs_pagina_web_azio().getValue();
	if (StringUtils.isNotBlank(idPagina)
		&& scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name())) {
	    // AplAzionePaginaTableBean actions =
	    // amministrazioneRuoliEjb.getActionsList(getForm().getDichAutorFields().getNm_applic().parse(),
	    // new
	    // BigDecimal(idPagina));
	    Set<BigDecimal> azioniGiaPresenti = new HashSet<>();
	    for (PrfVLisDichAutorRowBean row : (PrfVLisDichAutorTableBean) getForm()
		    .getDichAzioniList().getTable()) {
		if (row.getTiScopoDichAutor().equals(scopo)
			&& row.getIdPaginaWebAzio().compareTo(new BigDecimal(idPagina)) == 0) {
		    azioniGiaPresenti.add(row.getIdAzionePagina());
		}
	    }
	    AplAzionePaginaTableBean actions = amministrazioneRuoliEjb.getActionsFromPageList(
		    getForm().getDichAutorFields().getNm_applic().parse(), new BigDecimal(idPagina),
		    azioniGiaPresenti);
	    actions.addSortingRule(AplAzionePaginaTableDescriptor.COL_DS_AZIONE_PAGINA,
		    SortingRule.ASC);
	    actions.sort();
	    DecodeMap actionsDM = DecodeMap.Factory.newInstance(actions, "id_azione_pagina",
		    "ds_azione_pagina");
	    getForm().getDichAutorFields().getDs_azione_pagina().setDecodeMap(actionsDM);
	} else {
	    getForm().getDichAutorFields().getDs_azione_pagina().setDecodeMap(new DecodeMap());
	}
	return getForm().getDichAutorFields().asJSON();
    }

    /**
     * Carica in base allo scopo dichiarazione le combo dei campi entry menu, pagine azioni e
     * servizi
     *
     * @param scopo valore combo
     *
     * @throws EMFError errore generico
     */
    private void setDescriptionCombos(String scopo) throws EMFError {
	if (scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name())
		|| scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI_CHILD.name())) {

	    /* ENTRY MENU */
	    if (getForm().getWizardListsTabs().getListaWizardMenu().isCurrent()) {
		Set<String> entryGiaPresenti = new HashSet<>();
		for (PrfVLisDichAutorRowBean row : (PrfVLisDichAutorTableBean) getForm()
			.getDichMenuList().getTable()) {
		    if (row.getTiScopoDichAutor().equals(scopo) && row.getIdApplic().compareTo(
			    getForm().getDichAutorFields().getNm_applic().parse()) == 0) {
			entryGiaPresenti.add(row.getDsEntryMenu());
		    }
		}
		AplVTreeEntryMenuTableBean entryMenu = amministrazioneRuoliEjb.getEntryMenuList(
			getForm().getDichAutorFields().getNm_applic().parse(),
			scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name()),
			entryGiaPresenti);
		DecodeMap entries2 = DecodeMap.Factory.newInstance(entryMenu, "id_entry_menu",
			"dl_composito_entry_menu");
		getForm().getDichAutorFields().getDs_entry_menu().clear();
		getForm().getDichAutorFields().getDs_entry_menu().setDecodeMap(entries2);
	    }

	    /* PAGINE WEB */
	    /*
	     * if (getForm().getWizardListsTabs().getListaWizardPages().isCurrent()) { Set<String>
	     * pagineGiaPresenti = new HashSet<>(); for (PrfVLisDichAutorRowBean row :
	     * (PrfVLisDichAutorTableBean) getForm().getDichPagineList().getTable()) { if
	     * (row.getTiScopoDichAutor().equals(scopo) &&
	     * row.getIdApplic().compareTo(getForm().getDichAutorFields().getNm_applic().parse()) ==
	     * 0) { pagineGiaPresenti.add(row.getDsPaginaWeb()); } }
	     *
	     * AplPaginaWebTableBean pages =
	     * amministrazioneRuoliEjb.getPagesList(getForm().getDichAutorFields().getNm_applic().
	     * parse(), pagineGiaPresenti);
	     * pages.addSortingRule(AplPaginaWebTableDescriptor.COL_DS_PAGINA_WEB, SortingRule.ASC);
	     * pages.sort(); DecodeMap pagesDM = DecodeMap.Factory.newInstance(pages,
	     * "id_pagina_web", "ds_pagina_web");
	     * getForm().getDichAutorFields().getDs_pagina_web().clear();
	     * getForm().getDichAutorFields().getDs_pagina_web().setDecodeMap(pagesDM); }
	     */

	    /* AZIONI */
	    if (getForm().getWizardListsTabs().getListaWizardActions().isCurrent()) {
		if (scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name())) {
		    Set<BigDecimal> azioniGiaPresenti = new HashSet<>();
		    for (PrfVLisDichAutorRowBean row : (PrfVLisDichAutorTableBean) getForm()
			    .getDichAzioniList().getTable()) {
			if (row.getTiScopoDichAutor().equals(scopo) && row.getIdApplic().compareTo(
				getForm().getDichAutorFields().getNm_applic().parse()) == 0) {
			    azioniGiaPresenti.add(row.getIdAzionePagina());
			}
		    }
		    AplPaginaWebTableBean pagesAction = amministrazioneRuoliEjb.getPagesAzioniList(
			    getForm().getDichAutorFields().getNm_applic().parse(),
			    azioniGiaPresenti);
		    pagesAction.addSortingRule(AplPaginaWebTableDescriptor.COL_DS_PAGINA_WEB,
			    SortingRule.ASC);
		    pagesAction.sort();
		    DecodeMap pagesActionDM = DecodeMap.Factory.newInstance(pagesAction,
			    "id_pagina_web", "ds_pagina_web");
		    getForm().getDichAutorFields().getDs_pagina_web_azio().clear();
		    getForm().getDichAutorFields().getDs_pagina_web_azio()
			    .setDecodeMap(pagesActionDM);
		    getForm().getDichAutorFields().getDs_azione_pagina()
			    .setDecodeMap(new DecodeMap());
		} else {
		    // Caso ALL_ABILITAZIONI_CHILD
		    Set<String> pagineAzioniChildGiaPresenti = new HashSet<>();
		    for (PrfVLisDichAutorRowBean row : (PrfVLisDichAutorTableBean) getForm()
			    .getDichAzioniList().getTable()) {
			if (row.getTiScopoDichAutor().equals(scopo) && row.getIdApplic().compareTo(
				getForm().getDichAutorFields().getNm_applic().parse()) == 0) {
			    pagineAzioniChildGiaPresenti.add(row.getDsPaginaWebAzio());
			}
		    }
		    AplPaginaWebTableBean pages = amministrazioneRuoliEjb.getPagesList(
			    getForm().getDichAutorFields().getNm_applic().parse(),
			    pagineAzioniChildGiaPresenti);
		    pages.addSortingRule(AplPaginaWebTableDescriptor.COL_DS_PAGINA_WEB,
			    SortingRule.ASC);
		    pages.sort();
		    DecodeMap pagesDM = DecodeMap.Factory.newInstance(pages, "id_pagina_web",
			    "ds_pagina_web");
		    getForm().getDichAutorFields().getDs_pagina_web_azio().clear();
		    getForm().getDichAutorFields().getDs_pagina_web_azio().setDecodeMap(pagesDM);
		    getForm().getDichAutorFields().getDs_azione_pagina()
			    .setDecodeMap(new DecodeMap());
		}
	    }

	    /* SERVIZI */
	    if (getForm().getWizardListsTabs().getListaWizardServices().isCurrent()) {
		Set<String> serviziGiaPresenti = new HashSet<>();
		for (PrfVLisDichAutorRowBean row : (PrfVLisDichAutorTableBean) getForm()
			.getDichServiziList().getTable()) {
		    if (row.getTiScopoDichAutor().equals(scopo) && row.getIdApplic().compareTo(
			    getForm().getDichAutorFields().getNm_applic().parse()) == 0) {
			serviziGiaPresenti.add(row.getDsServizioWeb());
		    }
		}
		AplServizioWebTableBean services = amministrazioneRuoliEjb.getServicesList(
			getForm().getDichAutorFields().getNm_applic().parse(), serviziGiaPresenti);
		services.addSortingRule(AplServizioWebTableDescriptor.COL_DS_SERVIZIO_WEB,
			SortingRule.ASC);
		services.sort();
		DecodeMap servicesDM = DecodeMap.Factory.newInstance(services, "id_servizio_web",
			"ds_servizio_web");
		getForm().getDichAutorFields().getDs_servizio_web().setDecodeMap(servicesDM);
	    }
	} else if (scopo.equals(ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name())) {
	    getForm().getDichAutorFields().getDs_entry_menu().setDecodeMap(new DecodeMap());
	    getForm().getDichAutorFields().getDs_entry_menu().setValue(null);
	    getForm().getDichAutorFields().getDs_pagina_web().setDecodeMap(new DecodeMap());
	    getForm().getDichAutorFields().getDs_pagina_web().setValue(null);
	    getForm().getDichAutorFields().getDs_azione_pagina().setDecodeMap(new DecodeMap());
	    getForm().getDichAutorFields().getDs_azione_pagina().setValue(null);
	    getForm().getDichAutorFields().getDs_pagina_web_azio().setDecodeMap(new DecodeMap());
	    getForm().getDichAutorFields().getDs_pagina_web_azio().setValue(null);
	    getForm().getDichAutorFields().getDs_servizio_web().setDecodeMap(new DecodeMap());
	    getForm().getDichAutorFields().getDs_servizio_web().setValue(null);
	    // getForm().getDichAutorFields().getNodi_superiori().setValue("\u00A0");
	}
    }

    /**
     * Metodo invocato sul bottone di inserimento della lista ruoli. Cambia lo stato della lista
     * ruoli e delle liste del dettaglio ruolo
     *
     * @throws EMFError errore generico
     */
    @Override
    public void insertDettaglio() throws EMFError {
	if (getTableName() != null) {
	    if (getTableName().equals(getForm().getListaRuoli().getName())) {
		getForm().getListaRuoli().setStatus(BaseElements.Status.insert);

		getForm().getDettaglioRuolo().clear();
		getForm().getApplicazioniList().clear();
		getForm().getDichMenuList().clear();
		getForm().getDichPagineList().clear();
		getForm().getDichAzioniList().clear();
		getForm().getDichServiziList().clear();

		// Imposto i valori nelle combo
		getForm().getDettaglioRuolo().getTi_ruolo().setDecodeMap(ComboGetter.getTiRuolo());
		// getForm().getDettaglioRuolo().getTi_categ_ruolo().setDecodeMap(comboGetter.getTiCategRuolo());

		// Mi salvo in sessione un attributo sul nome da dare al ruolo
		// Mi servirà in fase di controllo univocità
		getSession().setAttribute("nomeRuolo", null);

		getForm().getCategorieList().clear();
		PrfRuoloCategoriaTableBean ruoloCateg = new PrfRuoloCategoriaTableBean();
		ruoloCateg.addSortingRule(PrfRuoloCategoriaTableDescriptor.COL_TI_CATEG_RUOLO,
			SortingRule.ASC);
		ruoloCateg.sort();
		getForm().getCategorieList().setTable(ruoloCateg);
		getForm().getCategorieList().getTable().setPageSize(10);
		getForm().getCategorieList().getTable().first();
		refreshDichCategorieFields();

		// Inizializzo, senza valori, la lista applicazioni
		// e azzero l'insieme applicazioni in sessione usato per i controlli di coerenza
		getForm().getApplicazioniList().clear();
		PrfUsoRuoloApplicTableBean apl = new PrfUsoRuoloApplicTableBean();
		apl.addSortingRule(AplApplicTableDescriptor.COL_NM_APPLIC, SortingRule.ASC);
		apl.sort();
		getForm().getApplicazioniList().setTable(apl);
		getForm().getApplicazioniList().getTable().setPageSize(10);
		getForm().getApplicazioniList().getTable().first();
		refreshDichApplicazioniFields();

		PrfVLisDichAutorTableBean dichsTB1 = new PrfVLisDichAutorTableBean();
		dichsTB1.addSortingRule("nodi_superiori", SortingRule.ASC);
		dichsTB1.addSortingRule(PrfVLisDichAutorTableDescriptor.COL_DS_ENTRY_MENU,
			SortingRule.ASC);
		dichsTB1.sort();
		getForm().getDichMenuList().setTable(dichsTB1);
		getForm().getDichMenuList().getTable().setPageSize(10);
		getForm().getDichMenuList().getTable().first();
		refreshDichAutorMenuFields();

		PrfVLisDichAutorTableBean dichsTB2 = new PrfVLisDichAutorTableBean();
		dichsTB2.addSortingRule(PrfVLisDichAutorTableDescriptor.COL_DS_PAGINA_WEB,
			SortingRule.ASC);
		dichsTB2.sort();
		getForm().getDichPagineList().setTable(dichsTB2);
		getForm().getDichPagineList().getTable().setPageSize(10);
		getForm().getDichPagineList().getTable().first();

		PrfVLisDichAutorTableBean dichsTB3 = new PrfVLisDichAutorTableBean();
		dichsTB3.addSortingRule(PrfVLisDichAutorTableDescriptor.COL_DS_PAGINA_WEB_AZIO,
			SortingRule.ASC);
		dichsTB3.addSortingRule(PrfVLisDichAutorTableDescriptor.COL_DS_AZIONE_PAGINA,
			SortingRule.ASC);
		dichsTB3.sort();
		getForm().getDichAzioniList().setTable(dichsTB3);
		getForm().getDichAzioniList().getTable().setPageSize(10);
		getForm().getDichAzioniList().getTable().first();

		PrfVLisDichAutorTableBean dichsTB4 = new PrfVLisDichAutorTableBean();
		dichsTB4.addSortingRule(PrfVLisDichAutorTableDescriptor.COL_DS_SERVIZIO_WEB,
			SortingRule.ASC);
		dichsTB4.sort();
		getForm().getDichServiziList().setTable(dichsTB4);
		getForm().getDichServiziList().getTable().setPageSize(10);
		getForm().getDichServiziList().getTable().first();

		// Imposto le liste come modificabili
		getForm().getCategorieList().setUserOperations(false, false, false, true);
		getForm().getApplicazioniList().setUserOperations(false, false, false, true);
		getForm().getDichMenuList().setUserOperations(false, false, false, true);
		getForm().getDichPagineList().setUserOperations(false, false, false, true);
		getForm().getDichAzioniList().setUserOperations(false, false, false, true);
		getForm().getDichServiziList().setUserOperations(false, false, false, true);
	    }
	}
    }

    @Override
    public void reloadAfterGoBack(String publisherName) {
    }

    public void reloadListaRuoli() {
	try {
	    Set<String> nmApplics = new HashSet<>();
	    String nmApplic = getForm().getFiltriRuoli().getNm_applic().getDecodedValue();
	    String nmRuolo = getForm().getFiltriRuoli().getNm_ruolo().parse();
	    String tiRuolo = getForm().getFiltriRuoli().getTi_ruolo().parse();
	    String tiCategRuolo = getForm().getFiltriRuoli().getTi_categ_ruolo().parse();
	    String tiStatoAllinea1 = getForm().getFiltriRuoli().getTi_stato_rich_allinea_ruoli_1()
		    .parse();
	    String tiStatoAllinea2 = getForm().getFiltriRuoli().getTi_stato_rich_allinea_ruoli_2()
		    .parse();
	    if (nmApplic != null && !nmApplic.equals("")) {
		nmApplics.add(nmApplic);
	    } else {
		DecodeMapIF mappaApplic = getForm().getFiltriRuoli().getNm_applic().getDecodeMap();
		Set<?> keys = mappaApplic.keySet(); // The set of keys in the map.
		Iterator<?> keyIter = keys.iterator();
		while (keyIter.hasNext()) {
		    BigDecimal key = (BigDecimal) keyIter.next(); // Get the next key.
		    nmApplics.add(getForm().getFiltriRuoli().getNm_applic().getValue(key)); // Get
											    // the
											    // value
											    // for
											    // that
		    // key.
		}
	    }

	    // Rieseguo la query per avere la lista Ruoli tenuto conto delle modifiche apportate
	    int inizio = getForm().getListaRuoli().getTable().getFirstRowPageIndex();
	    int paginaCorrente = getForm().getListaRuoli().getTable().getCurrentPageIndex();
	    int pageSize = getForm().getListaRuoli().getTable().getPageSize();
	    getForm().getListaRuoli().setTable(amministrazioneRuoliEjb.getPrfVLisRuoloTableBean(
		    nmApplics, tiStatoAllinea1, tiStatoAllinea2, nmRuolo, tiRuolo, tiCategRuolo));
	    getForm().getListaRuoli().getTable().setPageSize(pageSize);
	    getForm().getListaRuoli().setUserOperations(true, true, true, true);
	    // rieseguo la query se necessario
	    this.lazyLoadGoPage(getForm().getListaRuoli(), paginaCorrente);
	    // ritorno alla pagina
	    getForm().getListaRuoli().getTable().setCurrentRowIndex(inizio);
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	}
    }

    /**
     * Metodo eseguito al salvataggio del wizard di creazione/modifica ruolo
     *
     * @return true in caso di salvataggio riuscito
     *
     * @throws EMFError errore generico
     */
    @Override
    public boolean inserimentoWizardOnSave() throws EMFError {
	// Eseguo il salvataggio dell'intero ruolo
	boolean result = true;
	boolean statusInsert = false;
	if (getForm().getDettaglioRuolo().postAndValidate(getRequest(), getMessageBox())) {
	    PrfVLisRuoloRowBean ruolo = null;
	    BigDecimal idRuolo = null;
	    if (getForm().getListaRuoli().getStatus().equals(BaseElements.Status.insert)) {
		ruolo = new PrfVLisRuoloRowBean();
		statusInsert = true;
	    } else if (getForm().getListaRuoli().getStatus().equals(BaseElements.Status.update)) {
		ruolo = (PrfVLisRuoloRowBean) getForm().getListaRuoli().getTable().getCurrentRow();
	    }

	    PrfVLisDichAutorTableBean entryMenuTableBean = new PrfVLisDichAutorTableBean();
	    PrfVLisDichAutorTableBean pagineTableBean = new PrfVLisDichAutorTableBean();
	    PrfVLisDichAutorTableBean azioniTableBean = new PrfVLisDichAutorTableBean();
	    Set<BigDecimal> entryMenuToDelete = new HashSet<>();
	    Set<BigDecimal> pagesToDelete = new HashSet<>();
	    Set<BigDecimal> actionsToDelete = new HashSet<>();

	    PrfRuoloCategoriaTableBean ruoloCategoriaTableBean = (PrfRuoloCategoriaTableBean) getForm()
		    .getCategorieList().getTable();

	    // Ricavo la lista delle applicazioni gestite dal ruolo
	    PrfUsoRuoloApplicTableBean usoRuoloApplicTableBean = (PrfUsoRuoloApplicTableBean) getForm()
		    .getApplicazioniList().getTable();
	    for (PrfUsoRuoloApplicRowBean applic : usoRuoloApplicTableBean) {
		BigDecimal idApplic = applic.getIdApplic();
		RoleTree menuTree = (RoleTree) getSession()
			.getAttribute(TREE_ENTRY_MENU_ATTRIBUTE + "_" + idApplic.toPlainString());
		RoleTree pagesTree = (RoleTree) getSession()
			.getAttribute(TREE_PAGES_ATTRIBUTE + "_" + idApplic.toPlainString());
		RoleTree actionsTree = (RoleTree) getSession()
			.getAttribute(TREE_ACTIONS_ATTRIBUTE + "_" + idApplic.toPlainString());
		try {
		    if (menuTree != null) {
			entryMenuTableBean.addAll(menuTree.getSelectedNodesTableBean());
			entryMenuToDelete.addAll(menuTree.getIdDichAuthorsToDelete());
		    } // Se nullo prendo i dati dal db
		    else {
			entryMenuTableBean.addAll(
				amministrazioneRuoliEjb.getPrfDichAutorViewBean(ruolo.getIdRuolo(),
					ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name(), idApplic));
		    }
		    if (pagesTree != null) {
			pagineTableBean.addAll(pagesTree.getSelectedNodesTableBean());
			pagesToDelete.addAll(pagesTree.getIdDichAuthorsToDelete());
		    } // Se nullo prendo i dati dal db
		    else {
			pagineTableBean.addAll(
				amministrazioneRuoliEjb.getPrfDichAutorViewBean(ruolo.getIdRuolo(),
					ConstPrfDichAutor.TiDichAutor.PAGINA.name(), idApplic));
		    }
		    if (actionsTree != null) {
			azioniTableBean.addAll(actionsTree.getSelectedNodesTableBean());
			actionsToDelete.addAll(actionsTree.getIdDichAuthorsToDelete());
		    } // Se nullo prendo i dati dal db
		    else {
			azioniTableBean.addAll(
				amministrazioneRuoliEjb.getPrfDichAutorViewBean(ruolo.getIdRuolo(),
					ConstPrfDichAutor.TiDichAutor.AZIONE.name(), idApplic));
		    }
		} catch (ParerUserError ex) {
		    getMessageBox().addError(ex.getDescription());
		}
	    }
	    if (!getMessageBox().hasError()) {
		final PrfVLisDichAutorTableBean serviziTableBean = (PrfVLisDichAutorTableBean) getForm()
			.getDichServiziList().getTable();
		boolean check = checkAutorizzazioniServiziVsMenuAzioni(usoRuoloApplicTableBean,
			serviziTableBean, entryMenuTableBean, azioniTableBean);
		if (check) {

		    // Controlli su eventuale modifica Categoria Ruolo e/o Tipo Ruolo
		    String checkCategoria = "";
		    String checkTipoRuolo = "";
		    if (getForm().getListaRuoli().getStatus().equals(BaseElements.Status.update)) {
			PrfRuoloCategoriaTableBean tb = (PrfRuoloCategoriaTableBean) getForm()
				.getCategorieList().getTable();
			checkCategoria = amministrazioneRuoliEjb
				.checkCategoriaRuoloModificata(ruolo.getIdRuolo(), tb);
			checkTipoRuolo = amministrazioneRuoliEjb.checkTipoRuoloModificato(
				ruolo.getIdRuolo(),
				getForm().getDettaglioRuolo().getTi_ruolo().parse());
			if (!checkCategoria.equals("") || !checkTipoRuolo.equals("")) {
			    getMessageBox().addError(checkCategoria + " " + checkTipoRuolo);
			}
		    }

		    if (!getMessageBox().hasError()) {

			ruolo.setNmRuolo(getForm().getDettaglioRuolo().getNm_ruolo().parse());
			ruolo.setDsRuolo(getForm().getDettaglioRuolo().getDs_ruolo().parse());
			ruolo.setTiRuolo(getForm().getDettaglioRuolo().getTi_ruolo().parse());
			// ruolo.setTiCategRuolo(getForm().getDettaglioRuolo().getTi_categ_ruolo().parse());

			try {
			    /*
			     * Codice aggiuntivo per il logging...
			     */
			    LogParam param = SpagoliteLogUtil.getLogParam(
				    paramHelper.getParamApplicApplicationName(),
				    getUser().getUsername(), SpagoliteLogUtil.getPageName(this));
			    param.setTransactionLogContext(
				    sacerLogEjb.getNewTransactionLogContext());
			    if (statusInsert) {
				param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
			    } else {
				param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
			    }
			    idRuolo = auth.saveAndAlignRole(param, ruoloCategoriaTableBean,
				    usoRuoloApplicTableBean, ruolo, entryMenuTableBean,
				    pagineTableBean, azioniTableBean, serviziTableBean,
				    getCategorieDeleteList(), getApplicationsDeleteList(),
				    entryMenuToDelete, pagesToDelete, actionsToDelete,
				    getServicesDeleteList(), getServicesDeleteListAppId());
			} catch (ParerUserError ex) {
			    getMessageBox().addError(ex.getDescription());
			    result = false;
			}
		    } else {
			// almeno una dichiarazione per menù e azioni");
			result = false;
		    }

		} else {
		    // almeno una dichiarazione per menù e azioni");
		    result = false;
		}
	    }
	    if (!getMessageBox().hasError()) {
		getSession().removeAttribute("nomeRuolo");
		getSession().removeAttribute("categorieDeleteList");
		getSession().removeAttribute("applicationsDeleteList");
		getSession().removeAttribute("entryMenuDeleteList");
		getSession().removeAttribute("pagesDeleteList");
		getSession().removeAttribute("actionsDeleteList");
		getSession().removeAttribute("servicesDeleteList");
		getSession().removeAttribute("servicesDeleteListAppId");

		// Ricarico la lista
		reloadListaRuoli();

		if (getForm().getListaRuoli().getStatus().equals(BaseElements.Status.insert)) {
		    getMessageBox().addInfo("Ruolo creato con successo");
		    // ruolo.setIdRuolo(idRuolo);
		    // ruolo.setBigDecimal(PrfVLisDichAutorTableDescriptor.COL_ID_USO_RUOLO_APPLIC,
		    // amministrazioneRuoliEjb.getIdUsoRuoloApplic(idRuolo,
		    // getForm().getDichAutorFields().getNm_applic().parse()));
		    // getForm().getListaRuoli().getTable().add(ruolo);
		} else if (getForm().getListaRuoli().getStatus()
			.equals(BaseElements.Status.update)) {
		    getMessageBox().addInfo("Ruolo modificato con successo");
		}
		getMessageBox().setViewMode(MessageBox.ViewMode.plain);

		// Ricarico il dettaglio
		loadDettaglioRuolo(idRuolo);
		// Rimetto tutto in viewMode
		setDettaglioRuoliToViewMode();
		forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO);
	    }
	}
	return result;
    }

    /**
     * Metodo eseguito in caso di annullamento della creazione/modifica ruolo nel wizard. Ricarica
     * la lista ruoli in visualizzazione
     *
     * @throws EMFError errore generico
     */
    @Override
    public void inserimentoWizardOnCancel() throws EMFError {
	getForm().getListaRuoli().setStatus(BaseElements.Status.view);
	redirectToPublisher(Application.Publisher.RICERCA_RUOLI);
    }

    /**
     * Carica il publisher di default del wizard di inserimento
     *
     * @return il publisher del wizard
     *
     * @throws EMFError errore generico
     */
    @Override
    public String getDefaultInserimentoWizardPublisher() throws EMFError {
	return Application.Publisher.DETTAGLIO_RUOLO_WIZARD;
    }

    /**
     * Primo step del wizard: - Carica il dettaglio ruolo in edit mode - Visualizza il bottone per
     * inserire dati nelle liste
     *
     * @throws EMFError errore generico
     */
    @Override
    public void inserimentoWizardPasso1OnEnter() throws EMFError {
	getForm().getDettaglioRuolo().setEditMode();
	getForm().getDichAutorFields().getAdd_row_to_list().setViewMode();
	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO_WIZARD);
    }

    /**
     * Chiusura primo step: - Valida i dati inseriti e poi carica il secondo step, altrimenti mostra
     * il messaggio di errore validazione
     *
     * @return true se i dati sono validati
     *
     * @throws EMFError errore generico
     */
    @Override
    public boolean inserimentoWizardPasso1OnExit() throws EMFError {
	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO_WIZARD);
	String nomeRuoloPreModifica = (String) getSession().getAttribute("nomeRuolo");
	if (getForm().getDettaglioRuolo().postAndValidate(getRequest(), getMessageBox())) {
	    // Controllo che il nome ruolo non sia già presente su DB
	    String nomeRuoloPostModifica = getForm().getDettaglioRuolo().getNm_ruolo().parse();
	    boolean isUniqueRole = amministrazioneRuoliEjb.checkIsUniqueRole(nomeRuoloPreModifica,
		    nomeRuoloPostModifica, getForm().getListaRuoli().getStatus());
	    if (isUniqueRole) {
		return true;
	    } else {
		getMessageBox().addError("Nome ruolo non univoco");
	    }
	}
	return false;
    }

    @Override
    public void inserimentoWizardPassoCategorieOnEnter() throws EMFError {
	getForm().getCategorieFields().getAggiungiCategoria().setEditMode();
	getForm().getCategorieFields().getTi_categ_ruolo().setEditMode();
	refreshDichCategorieFields();
	populateCategoriaSet((PrfRuoloCategoriaTableBean) getForm().getCategorieList().getTable());
	getForm().getCategorieList().setHidden(false);
	getForm().getDettaglioRuolo().setViewMode();
	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO_WIZARD);
    }

    @Override
    public boolean inserimentoWizardPassoCategorieOnExit() throws EMFError {
	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO_WIZARD);
	if (getForm().getCategorieList().getTable().isEmpty()) {
	    getMessageBox().addError("Inserire almeno una categoria");
	    return false;
	}
	if (getForm().getCategorieFields().postAndValidate(getRequest(), getMessageBox())) {
	    return true;
	}
	return false;
    }

    @Override
    public void inserimentoWizardPassoApplicOnEnter() throws EMFError {
	getForm().getApplicazioniFields().getAggiungiApplicazione().setEditMode();
	getForm().getApplicazioniFields().getNm_applic().setEditMode();
	getForm().getApplicazioniFields().getDs_applic().setEditMode();
	getForm().getApplicazioniFields().getNm_applic().setHidden(false);
	getForm().getApplicazioniFields().getDs_applic().setHidden(false);
	refreshDichApplicazioniFields();
	populateApplicationSet(
		(PrfUsoRuoloApplicTableBean) getForm().getApplicazioniList().getTable());
	getForm().getApplicazioniList().setHidden(false);
	getForm().getDettaglioRuolo().setViewMode();
	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO_WIZARD);
    }

    @Override
    public boolean inserimentoWizardPassoApplicOnExit() throws EMFError {
	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO_WIZARD);
	if (getForm().getApplicazioniList().getTable().isEmpty()) {
	    getMessageBox().addError("Inserire almeno un'applicazione");
	    return false;
	}
	if (getForm().getApplicazioniFields().postAndValidate(getRequest(), getMessageBox())) {
	    return true;
	}
	return false;
    }

    /**
     * Carica il secondo step: - popola i set delle dichiarazioni di autorizzazione per l'entry menu
     * - mette in view mode i dati inseriti nel primo step
     *
     * @throws EMFError errore generico
     */
    @Override
    public void inserimentoWizardPasso2OnEnter() throws EMFError {
	getForm().getDichAutorFields().getAdd_row_to_list().setEditMode();
	getForm().getDettaglioRuolo().setViewMode();

	// Popola la mappa relativa alle applicazioni in base ai valori
	// impostati nello step precedente
	PrfUsoRuoloApplicTableBean applicImpostate = (PrfUsoRuoloApplicTableBean) getForm()
		.getApplicazioniList().getTable();
	DecodeMap mappaAplImp = new DecodeMap();
	mappaAplImp.populatedMap(applicImpostate, "id_applic", "nm_applic");
	getForm().getDichAutorFields().getNm_applic().setDecodeMap(mappaAplImp);
	getForm().getDichAutorFields().getNm_applic().setEditMode();
	getForm().getDichAutorFields().getNm_applic_tree().setDecodeMap(mappaAplImp);
	getForm().getDichAutorFields().getNm_applic_tree().setEditMode();
	getForm().getDichAutorFields().clear();

	populateDichAutorSet((PrfVLisDichAutorTableBean) getForm().getDichMenuList().getTable(),
		ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name());
	getForm().getWizardListsTabs()
		.setCurrentTab(getForm().getWizardListsTabs().getListaWizardMenu());
	refreshDichAutorMenuFields();
	getForm().getTreeButtonList().setEditMode();
	if (applicImpostate != null && applicImpostate.size() == 1) {
	    getForm().getDichAutorFields().getNm_applic_tree()
		    .setValue(applicImpostate.getRow(0).getIdApplic().toPlainString());
	}

	getForm().getDichAutorFields().setStatus(BaseElements.Status.insert);
    }

    /**
     * Chiusura secondo step. Dato che i dati vengono già validati nell'inserimento in lista, questo
     * step ricarica semplicemente la pagina.
     *
     * @return true
     *
     * @throws EMFError errore generico
     */
    @Override
    public boolean inserimentoWizardPasso2OnExit() throws EMFError {
	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO_WIZARD);
	// SALVA IL RUOLO
	return true;
    }

    /**
     * Carica la lista degli entry menu nel wizard
     *
     * @throws EMFError errore generico
     */
    @Override
    public void tabListaWizardMenuOnClick() throws EMFError {
	populateDichAutorSet((PrfVLisDichAutorTableBean) getForm().getDichMenuList().getTable(),
		ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name());
	getForm().getWizardListsTabs()
		.setCurrentTab(getForm().getWizardListsTabs().getListaWizardMenu());
	getForm().getDichAutorFields().getNm_applic().clear();

	refreshDichAutorMenuFields();

	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO_WIZARD);
    }

    /**
     * Carica la lista delle pagine nel wizard
     *
     * @throws EMFError errore generico
     */
    // @Override
    // public void tabListaWizardPagesOnClick() throws EMFError {
    // TabElement currentTab = getForm().getWizardListsTabs().getCurrentTab();
    // populateDichAutorSet((PrfVLisDichAutorTableBean) getForm().getDichPagineList().getTable(),
    // ConstPrfDichAutor.TiDichAutor.PAGINA.name());
    // getForm().getWizardListsTabs().setCurrentTab(getForm().getWizardListsTabs().getListaWizardPages());
    //
    // getForm().getDichAutorFields().getNm_applic().clear();
    // getForm().getDichAutorFields().getTi_scopo_dich_autor().clear();
    // initTiScopoDichAutor(ConstPrfDichAutor.TiDichAutor.PAGINA.name());
    // setDichAutorFields(ConstPrfDichAutor.TiDichAutor.PAGINA.name());
    // getForm().getDichAutorFields().getDs_pagina_web().clear();
    // getForm().getDichAutorFields().getDs_pagina_web().setDecodeMap(new DecodeMap());
    //
    // BigDecimal idApplic = getForm().getDichAutorFields().getNm_applic_tree().parse();
    // if (idApplic != null &&
    // currentTab.getName().equals(getForm().getWizardListsTabs().getListaWizardMenu().getName())) {
    // RoleTree roleTreeMenu = (RoleTree) getSession().getAttribute(TREE_ENTRY_MENU_ATTRIBUTE + "_"
    // +
    // idApplic.toPlainString());
    // RoleTree roleTreePages = (RoleTree) getSession().getAttribute(TREE_PAGES_ATTRIBUTE + "_" +
    // idApplic.toPlainString());
    // if (roleTreeMenu != null && roleTreePages != null) {
    // Set<String> selectedSet = new HashSet<>(roleTreePages.getSelectedNodes());
    // selectedSet.addAll(roleTreeMenu.getSelectedNodes());
    //
    // roleTreePages.setSelectedNodes(selectedSet.toArray(new String[selectedSet.size()]));
    // roleTreePages.setEdited(true);
    // getSession().setAttribute(TREE_PAGES_ATTRIBUTE + "_" + idApplic.toPlainString(),
    // roleTreePages);
    // }
    // }
    //
    // forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO_WIZARD);
    // }
    /**
     * Carica la lista delle azioni nel wizard
     *
     * @throws EMFError errore generico
     */
    @Override
    public void tabListaWizardActionsOnClick() throws EMFError {
	TabElement currentTab = getForm().getWizardListsTabs().getCurrentTab();
	populateDichAutorSet((PrfVLisDichAutorTableBean) getForm().getDichAzioniList().getTable(),
		ConstPrfDichAutor.TiDichAutor.AZIONE.name());
	getForm().getWizardListsTabs()
		.setCurrentTab(getForm().getWizardListsTabs().getListaWizardActions());

	getForm().getDichAutorFields().getNm_applic().clear();
	getForm().getDichAutorFields().getTi_scopo_dich_autor().clear();
	initTiScopoDichAutor(ConstPrfDichAutor.TiDichAutor.AZIONE.name());
	setDichAutorFields(ConstPrfDichAutor.TiDichAutor.AZIONE.name());
	getForm().getDichAutorFields().getDs_pagina_web_azio().clear();
	getForm().getDichAutorFields().getDs_pagina_web_azio().setDecodeMap(new DecodeMap());
	getForm().getDichAutorFields().getDs_azione_pagina().clear();
	getForm().getDichAutorFields().getDs_azione_pagina().setDecodeMap(new DecodeMap());
	BigDecimal idApplic = getForm().getDichAutorFields().getNm_applic_tree().parse();
	if (idApplic != null && currentTab.getName()
		.equals(getForm().getWizardListsTabs().getListaWizardMenu().getName())) {
	    RoleTree roleTreeMenu = (RoleTree) getSession()
		    .getAttribute(TREE_ENTRY_MENU_ATTRIBUTE + "_" + idApplic.toPlainString());
	    RoleTree roleTreePages = (RoleTree) getSession()
		    .getAttribute(TREE_PAGES_ATTRIBUTE + "_" + idApplic.toPlainString());
	    RoleTree roleTreeActions = (RoleTree) getSession()
		    .getAttribute(TREE_ACTIONS_ATTRIBUTE + "_" + idApplic.toPlainString());
	    if (roleTreeActions != null) {
		Set<String> tmp = new HashSet<>(roleTreeActions.getSelectedNodes());
		if (roleTreeMenu != null && roleTreeMenu.getMenuNodes() != null) {
		    Set<String> menu = new HashSet<>(roleTreeMenu.getMenuNodes());
		    roleTreePages.setMenuNodes(menu.toArray(new String[menu.size()]));
		    roleTreeActions.setMenuNodes(menu.toArray(new String[menu.size()]));
		}
		roleTreeActions.setSelectedNodes(tmp.toArray(new String[tmp.size()]));
		roleTreeActions.setEdited(true);
		getSession().setAttribute(TREE_ACTIONS_ATTRIBUTE + "_" + idApplic.toPlainString(),
			roleTreeActions);
	    }
	}

	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO_WIZARD);
    }

    /**
     * Carica la lista dei servizi nel wizard
     *
     * @throws EMFError errore generico
     */
    @Override
    public void tabListaWizardServicesOnClick() throws EMFError {
	populateDichAutorSet((PrfVLisDichAutorTableBean) getForm().getDichServiziList().getTable(),
		ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name());
	getForm().getWizardListsTabs()
		.setCurrentTab(getForm().getWizardListsTabs().getListaWizardServices());

	getForm().getDichAutorFields().getNm_applic().clear();
	getForm().getDichAutorFields().getTi_scopo_dich_autor().clear();
	initTiScopoDichAutor(ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name());
	setDichAutorFields(ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name());
	getForm().getDichAutorFields().getDs_servizio_web().clear();
	getForm().getDichAutorFields().getDs_servizio_web().setDecodeMap(new DecodeMap());

	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO_WIZARD);
    }

    /**
     * Esegue l'inserimento di una nuova riga nella lista corrente, che può essere quella di entry
     * menu, pagine, azioni o servizi. In ognuno dei metodi chiamati vengono eseguiti i controlli di
     * validazione per la riga.
     *
     * @throws EMFError errore generico
     */
    @Override
    public void add_row_to_list() throws EMFError {
	if (getForm().getWizardListsTabs().getListaWizardMenu().isCurrent()) {
	    saveEntryMenuDichAutorList(true);
	    // } else if (getForm().getWizardListsTabs().getListaWizardPages().isCurrent()) {
	    // savePagesDichAutorList(true);
	} else if (getForm().getWizardListsTabs().getListaWizardActions().isCurrent()) {
	    saveActionsDichAutorList(true);
	} else if (getForm().getWizardListsTabs().getListaWizardServices().isCurrent()) {
	    saveServicesDichAutorList(true);
	}
	if (!getMessageBox().hasError()) {
	    getForm().getDichAutorFields().clear();
	    // ATTENZIONE: commentato perchè si intrippa il trigger se resetto le decodemap...
	    // getForm().getDichAutorFields().getTi_scopo_dich_autor().setDecodeMap(new
	    // DecodeMap());
	    // getForm().getDichAutorFields().getDs_entry_menu().setDecodeMap(new DecodeMap());
	    // getForm().getDichAutorFields().getDs_azione_pagina().setDecodeMap(new DecodeMap());
	    // getForm().getDichAutorFields().getDs_pagina_web().setDecodeMap(new DecodeMap());
	    // getForm().getDichAutorFields().getDs_pagina_web_azio().setDecodeMap(new DecodeMap());
	    // getForm().getDichAutorFields().getDs_servizio_web().setDecodeMap(new DecodeMap());
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO_WIZARD);
    }

    @Override
    public void aggiungiCategoria() throws EMFError {
	addRowToCategorieRuoliList(true);
	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO_WIZARD); // Tools | Templates.
    }

    @Override
    public void aggiungiApplicazione() throws EMFError {
	addRowToApplicazioniList(true);
	forwardToPublisher(Application.Publisher.DETTAGLIO_RUOLO_WIZARD);
    }

    @Override
    public void ricercaRuoli() throws EMFError {
	getForm().getFiltriRuoli().post(getRequest());
	Set<String> nmApplics = new HashSet<>();
	Set<String> tiCategRuolos = new HashSet<>();
	String nmApplic = getForm().getFiltriRuoli().getNm_applic().getDecodedValue();
	String nmRuolo = getForm().getFiltriRuoli().getNm_ruolo().parse();
	String tiRuolo = getForm().getFiltriRuoli().getTi_ruolo().parse();
	String tiCategRuolo = getForm().getFiltriRuoli().getTi_categ_ruolo().parse();
	String tiStatoAllinea1 = getForm().getFiltriRuoli().getTi_stato_rich_allinea_ruoli_1()
		.parse();
	String tiStatoAllinea2 = getForm().getFiltriRuoli().getTi_stato_rich_allinea_ruoli_2()
		.parse();
	PrfVLisRuoloTableBean prfVLisRuoloViewBean;
	if (nmApplic != null && !nmApplic.equals("")) {
	    nmApplics.add(nmApplic);
	} else {
	    DecodeMapIF mappaApplic = getForm().getFiltriRuoli().getNm_applic().getDecodeMap();
	    Set<?> keys = mappaApplic.keySet(); // The set of keys in the map.
	    Iterator<?> keyIter = keys.iterator();
	    while (keyIter.hasNext()) {
		BigDecimal key = (BigDecimal) keyIter.next(); // Get the next key.
		nmApplics.add(getForm().getFiltriRuoli().getNm_applic().getValue(key)); // Get the
											// value for
											// that key.
	    }
	}
	if (tiCategRuolo != null && !tiCategRuolo.equals("")) {
	    tiCategRuolos.add(tiCategRuolo);
	} else {
	    DecodeMapIF mappaCateg = getForm().getFiltriRuoli().getTi_categ_ruolo().getDecodeMap();
	    Set<?> keys = mappaCateg.keySet(); // The set of keys in the map.
	    Iterator<?> keyIter = keys.iterator();
	    while (keyIter.hasNext()) {
		String key = (String) keyIter.next(); // Get the next key.
		tiCategRuolos.add(getForm().getFiltriRuoli().getTi_categ_ruolo().getValue(key)); // Get
												 // the
												 // value
												 // for
												 // that
												 // key.
	    }
	}
	prfVLisRuoloViewBean = amministrazioneRuoliEjb.getPrfVLisRuoloTableBean(nmApplics,
		tiStatoAllinea1, tiStatoAllinea2, nmRuolo, tiRuolo, tiCategRuolo);

	prfVLisRuoloViewBean.addSortingRule(PrfVLisRuoloTableDescriptor.COL_NM_RUOLO,
		SortingRule.ASC);
	prfVLisRuoloViewBean.sort();
	getForm().getListaRuoli().setTable(prfVLisRuoloViewBean);
	getForm().getListaRuoli().getTable().setPageSize(10);
	getForm().getListaRuoli().getTable().first();
	// Ricarico la pagina
	forwardToPublisher(Application.Publisher.RICERCA_RUOLI);
    }

    private void deleteDichAutorSets() throws EMFError {
	AplApplicTableBean aplApplicTableBean = amministrazioneRuoliEjb.getAplApplicTableBean();
	for (AplApplicRowBean row : aplApplicTableBean) {
	    getSession().setAttribute("dichAutorSet_" + row.getNmApplic(), new HashSet<PairAuth>());
	}
    }

    private void removeSessionAttributes() {
	getSession().removeAttribute("nomeRuolo");
	getSession().removeAttribute("categorieSet");
	getSession().removeAttribute("categorieDeleteList");
	getSession().removeAttribute("applicationsSet");
	getSession().removeAttribute("applicationsDeleteList");
	getSession().removeAttribute("entryMenuDeleteList");
	getSession().removeAttribute("pagesDeleteList");
	getSession().removeAttribute("actionsDeleteList");
	getSession().removeAttribute("servicesDeleteList");
	getSession().removeAttribute("servicesDeleteListAppId");
    }

    @Override
    public void undoDettaglio() throws EMFError {
    }

    @Override
    public void saveDettaglio() throws EMFError {
    }

    @Override
    public void elencoOnClick() throws EMFError {
	if (getLastPublisher().equals("/amministrazioneUtenti/dettaglioRuolo")) {
	    forwardToPublisher(Application.Publisher.RICERCA_RUOLI);
	} else {
	    goBack();
	}
    }

    @Override
    public String getControllerName() {
	return Application.Actions.AMMINISTRAZIONE_RUOLI;
    }

    // <editor-fold defaultstate="collapsed" desc="Gestione generica degli alberi di ruolo">
    /**
     * Metodo on change della combo idApplicazione per ogni albero di ruolo
     *
     * @throws EMFError errore generico
     */
    public void triggerNm_applic_treeOnTriggerJS() throws EMFError {
	BigDecimal oldIdApplic = null;
	BigDecimal idApplic = null;
	if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_RUOLO_WIZARD)) {
	    oldIdApplic = getForm().getDichAutorFields().getNm_applic_tree().parse();
	    getForm().getDichAutorFields().getNm_applic_tree().post(getRequest());
	    idApplic = getForm().getDichAutorFields().getNm_applic_tree().parse();
	} else if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_RUOLO)) {
	    oldIdApplic = getForm().getDettaglioRuolo().getNm_applic_tree().parse();
	    getForm().getDettaglioRuolo().getNm_applic_tree().post(getRequest());
	    idApplic = getForm().getDettaglioRuolo().getNm_applic_tree().parse();
	}
	triggerUpdateTree(oldIdApplic, idApplic);
    }

    private void triggerUpdateTree(BigDecimal oldIdApplic, BigDecimal idApplic) throws EMFError {
	try {
	    String idTree = getRequest().getParameter(TREE_ID_TREE_ATTRIBUTE);
	    String[] nodes = getRequest().getParameterValues(TREE_CHECKED_NODES_ATTRIBUTE);
	    String[] menuNodes = getRequest().getParameterValues(TREE_MENU_CHECKED_NODES_ATTRIBUTE);
	    String treeSessionAttribute;
	    if (StringUtils.endsWith(idTree, getForm().getWizardEntryMenuTree().getName())) {
		treeSessionAttribute = TREE_ENTRY_MENU_ATTRIBUTE;
		// } else if (StringUtils.endsWith(idTree,
		// getForm().getWizardPagineWebTree().getName())) {
		// treeSessionAttribute = TREE_PAGES_ATTRIBUTE;
	    } else if (StringUtils.endsWith(idTree, getForm().getWizardAzioniTree().getName())) {
		treeSessionAttribute = TREE_ACTIONS_ATTRIBUTE;
	    } else {
		throw new EMFError(EMFError.BLOCKING,
			"Errore inatteso nel caricamento della pagina");
	    }

	    if (!getForm().getListaRuoli().getStatus().equals(Status.view) && oldIdApplic != null
		    && !oldIdApplic.equals(idApplic)) {
		RoleTree tree = (RoleTree) getSession()
			.getAttribute(treeSessionAttribute + "_" + oldIdApplic.toPlainString());
		if (tree != null) {
		    tree.setEdited(true);
		    tree.setSelectedNodes(nodes);
		    if (treeSessionAttribute.equals(TREE_ENTRY_MENU_ATTRIBUTE)) {
			tree.setMenuNodes(menuNodes);
		    }
		    getSession().setAttribute(
			    treeSessionAttribute + "_" + oldIdApplic.toPlainString(), tree);
		}
		if (treeSessionAttribute.equals(TREE_ACTIONS_ATTRIBUTE) && idApplic != null) {
		    RoleTree roleTreeMenu = (RoleTree) getSession().getAttribute(
			    TREE_ENTRY_MENU_ATTRIBUTE + "_" + idApplic.toPlainString());
		    RoleTree roleTreePages = (RoleTree) getSession()
			    .getAttribute(TREE_PAGES_ATTRIBUTE + "_" + idApplic.toPlainString());
		    RoleTree roleTreeActions = (RoleTree) getSession()
			    .getAttribute(TREE_ACTIONS_ATTRIBUTE + "_" + idApplic.toPlainString());
		    if (roleTreeActions != null) {
			if (roleTreeMenu != null && roleTreeMenu.getMenuNodes() != null) {
			    Set<String> menu = new HashSet<>(roleTreeMenu.getMenuNodes());
			    roleTreePages.setMenuNodes(menu.toArray(new String[menu.size()]));
			    roleTreeActions.setMenuNodes(menu.toArray(new String[menu.size()]));
			}
			roleTreeActions.setEdited(true);
			getSession().setAttribute(
				TREE_ACTIONS_ATTRIBUTE + "_" + idApplic.toPlainString(),
				roleTreeActions);
		    }
		}
	    }
	    JSONObject response = new JSONObject();
	    response.put(getForm().getDichAutorFields().getNm_applic_tree().getName(), idApplic);
	    redirectToAjax(response);
	} catch (JSONException ex) {
	    throw new EMFError(EMFError.BLOCKING, "Errore inatteso nel caricamento della pagina");
	}
    }

    @SuppressLogging
    public void saveStateOnTriggerJS() throws EMFError {
	try {
	    getForm().getDichAutorFields().getNm_applic_tree().post(getRequest());
	    String idTree = getRequest().getParameter(TREE_ID_TREE_ATTRIBUTE);
	    String[] nodes = getRequest().getParameterValues(TREE_CHECKED_NODES_ATTRIBUTE);
	    String[] menuNodes = getRequest().getParameterValues(TREE_MENU_CHECKED_NODES_ATTRIBUTE);
	    final BigDecimal idApplic = getForm().getDichAutorFields().getNm_applic_tree().parse();
	    List<String> selectedNodes = new ArrayList<>();
	    String treeSessionAttribute;
	    if (StringUtils.endsWith(idTree, getForm().getWizardEntryMenuTree().getName())) {
		treeSessionAttribute = TREE_ENTRY_MENU_ATTRIBUTE;
		if (nodes != null) {
		    CollectionUtils.addAll(selectedNodes, nodes);
		}
		// } else if (StringUtils.endsWith(idTree,
		// getForm().getWizardPagineWebTree().getName())) {
		// treeSessionAttribute = TREE_PAGES_ATTRIBUTE;
		// if (nodes != null) {
		// for (String node : nodes) {
		// if (node.startsWith("P")) {
		// selectedNodes.add(node);
		// }
		// }
		// }
	    } else if (StringUtils.endsWith(idTree, getForm().getWizardAzioniTree().getName())) {
		if (idApplic != null) {
		    treeSessionAttribute = TREE_PAGES_ATTRIBUTE;
		    List<String> pagesNodes = new ArrayList<>();
		    if (nodes != null) {
			for (String node : nodes) {
			    if (node.startsWith("P")) {
				pagesNodes.add(node);
				selectedNodes.add(node);
			    } else if (node.startsWith("A")) {
				selectedNodes.add(node);
			    }
			}
		    }
		    // Gestione FAKE per l'albero delle pagine, non più visualizzato ma da
		    // utilizzare solo al
		    // salvataggio
		    RoleTree tree = (RoleTree) getSession()
			    .getAttribute(treeSessionAttribute + "_" + idApplic.toPlainString());
		    if (tree != null) {
			tree.setEdited(true);
			tree.setSelectedNodes(pagesNodes.toArray(new String[pagesNodes.size()]));
			getSession().setAttribute(
				treeSessionAttribute + "_" + idApplic.toPlainString(), tree);
		    }
		}
		treeSessionAttribute = TREE_ACTIONS_ATTRIBUTE;
	    } else {
		throw new EMFError(EMFError.BLOCKING,
			"Errore inatteso nel caricamento della pagina");
	    }

	    if (idApplic != null) {
		RoleTree tree = (RoleTree) getSession()
			.getAttribute(treeSessionAttribute + "_" + idApplic.toPlainString());
		if (tree != null) {
		    tree.setEdited(true);
		    tree.setSelectedNodes(selectedNodes.toArray(new String[selectedNodes.size()]));
		    if (menuNodes != null) {
			tree.setMenuNodes(menuNodes);
		    }
		    getSession().setAttribute(treeSessionAttribute + "_" + idApplic.toPlainString(),
			    tree);
		}
	    }
	    JSONObject response = new JSONObject();
	    response.put(getForm().getDichAutorFields().getNm_applic_tree().getName(), idApplic);
	    redirectToAjax(response);
	} catch (JSONException ex) {
	    throw new EMFError(EMFError.BLOCKING, "Errore inatteso nel caricamento della pagina");
	}
    }

    /**
     * Metodo di creazione dell'albero jsTree e dell'albero "logico" salvato in sessione Ritorna (al
     * metodo ajax che lo chiama) un JSON con l'elenco dei nodi
     *
     * @param tiDichAutor tipo di albero
     * @param idApplic    id applicazione
     * @param nmApplic    nome applicazione
     *
     * @throws EMFError errore generico
     */
    public void createTree(ConstPrfDichAutor.TiDichAutor tiDichAutor, BigDecimal idApplic,
	    String nmApplic) throws EMFError {
	List<String> treeSessionAttributesExcluded = new ArrayList<>();
	List<String> dichAutorsExcluded = new ArrayList<>();
	String treeSessionAttribute;
	switch (tiDichAutor) {
	case ENTRY_MENU:
	    treeSessionAttribute = TREE_ENTRY_MENU_ATTRIBUTE;
	    treeSessionAttributesExcluded
		    .addAll(Arrays.asList(TREE_ACTIONS_ATTRIBUTE, TREE_PAGES_ATTRIBUTE));
	    dichAutorsExcluded.addAll(Arrays.asList(ConstPrfDichAutor.TiDichAutor.AZIONE.name(),
		    ConstPrfDichAutor.TiDichAutor.PAGINA.name()));
	    break;
	case AZIONE:
	    treeSessionAttribute = TREE_ACTIONS_ATTRIBUTE;
	    treeSessionAttributesExcluded
		    .addAll(Arrays.asList(TREE_ENTRY_MENU_ATTRIBUTE, TREE_PAGES_ATTRIBUTE));
	    dichAutorsExcluded.addAll(Arrays.asList(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name(),
		    ConstPrfDichAutor.TiDichAutor.PAGINA.name()));
	    break;
	case PAGINA:
	    treeSessionAttribute = TREE_PAGES_ATTRIBUTE;
	    treeSessionAttributesExcluded
		    .addAll(Arrays.asList(TREE_ENTRY_MENU_ATTRIBUTE, TREE_ACTIONS_ATTRIBUTE));
	    dichAutorsExcluded.addAll(Arrays.asList(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name(),
		    ConstPrfDichAutor.TiDichAutor.AZIONE.name()));
	    break;
	case SERVIZIO_WEB:
	default:
	    throw new AssertionError(
		    tiDichAutor.name() + " non abilitato per la gestione ad albero");
	}
	if (idApplic != null) {
	    final AplVTreeMenuPagAzioTableBean nodesList = amministrazioneRuoliEjb
		    .getRoleTreeTableBean(idApplic, tiDichAutor.name());
	    // Sulla base delle dichiarazioni per l'utente, seleziono i nodi presenti
	    JSONObject treeJson = new JSONObject();
	    try {
		if (!nodesList.isEmpty()) {
		    treeJson = buildTree(nodesList, treeSessionAttribute, idApplic, nmApplic,
			    tiDichAutor);
		} else {
		    treeJson.put("data", new ArrayList<String>());
		}
		getResponse().setHeader("Content-Type", "application/json;charset=UTF-8");
		getResponse().getWriter().write(new String(
			treeJson.getJSONArray("data").toString().getBytes("UTF-8"), "UTF-8"));
		freeze();
		if (getForm().getListaRuoli().getStatus().equals(BaseElements.Status.insert)) {
		    /*
		     * Se sto inserendo un nuovo ruolo, precarico tutti gli alberi, in modo da
		     * potermi passare le modifiche tra menu -> pagine -> azioni al cambio dei tab
		     */
		    for (int i = 0; i < treeSessionAttributesExcluded.size(); i++) {
			String sessionAttribute = treeSessionAttributesExcluded.get(i);
			String dichAutor = dichAutorsExcluded.get(i);
			final AplVTreeMenuPagAzioTableBean treeList = amministrazioneRuoliEjb
				.getRoleTreeTableBean(idApplic, dichAutor);
			if (!treeList.isEmpty()) {
			    buildTree(treeList, sessionAttribute, idApplic, nmApplic,
				    ConstPrfDichAutor.TiDichAutor.valueOf(dichAutor));
			}
		    }
		} else {
		    // In modifica, precarico l'albero delle pagine, in modo da averlo sempre (dato
		    // che è solo
		    // 'virtuale')
		    final AplVTreeMenuPagAzioTableBean treeList = amministrazioneRuoliEjb
			    .getRoleTreeTableBean(idApplic,
				    ConstPrfDichAutor.TiDichAutor.PAGINA.name());
		    if (!treeList.isEmpty()) {
			buildTree(treeList, TREE_PAGES_ATTRIBUTE, idApplic, nmApplic,
				ConstPrfDichAutor.TiDichAutor.PAGINA);
		    }
		}
	    } catch (JSONException ex) {
		throw new EMFError(EMFError.ERROR,
			"Errore inatteso nel popolamento dell'albero dei ruoli "
				+ ExceptionUtils.getRootCauseMessage(ex),
			ex);
	    } catch (IOException ex) {
		throw new EMFError(EMFError.BLOCKING,
			"Errore inatteso nel caricamento della pagina");
	    }
	} else {
	    try {
		getResponse().setHeader("Content-Type", "application/json;charset=UTF-8");
		String response = new JSONObject("{}").toString();
		getResponse().getWriter().write(new String(response.getBytes("UTF-8")));
		freeze();
	    } catch (JSONException ex) {
		throw new EMFError(EMFError.ERROR,
			"Errore inatteso nel popolamento dell'albero dei ruoli "
				+ ExceptionUtils.getRootCauseMessage(ex),
			ex);
	    } catch (IOException ex) {
		throw new EMFError(EMFError.BLOCKING,
			"Errore inatteso nel caricamento della pagina");
	    }
	}
    }

    /**
     * Metodo di popolamento dell'albero dei ruoli identificato da
     * {@link ConstPrfDichAutor.TiDichAutor} . Se l'albero era già stato caricato in precedenza,
     * vengono ripresi i nodi selezionati dalla sessione
     *
     * @param nmApplicParam nome applicazione di cui si sta gestendo il ruolo
     * @param idRuoloParam  id del ruolo da gestire
     * @param tiDichAutor   identifica il tipo di albero di cui popolare i nodi
     *
     * @throws EMFError errore generico
     */
    private JSONObject populateNodiAbilitati(String nmApplicParam, String idRuoloParam,
	    ConstPrfDichAutor.TiDichAutor tiDichAutor) throws EMFError {
	String treeSessionAttribute;
	String idToList;
	String idToList2 = null;
	String type;
	switch (tiDichAutor) {
	case ENTRY_MENU:
	    treeSessionAttribute = TREE_ENTRY_MENU_ATTRIBUTE;
	    idToList = "id_entry_menu";
	    type = ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.getValue();
	    break;
	case AZIONE:
	    treeSessionAttribute = TREE_ACTIONS_ATTRIBUTE;
	    idToList = "id_azione_pagina";
	    idToList2 = "id_pagina_web_azio";
	    type = ConstPrfDichAutor.TiDichAutor.AZIONE.getValue();
	    break;
	case PAGINA:
	    treeSessionAttribute = TREE_PAGES_ATTRIBUTE;
	    idToList = "id_pagina_web";
	    type = ConstPrfDichAutor.TiDichAutor.PAGINA.getValue();
	    break;
	case SERVIZIO_WEB:
	default:
	    throw new AssertionError(
		    tiDichAutor.name() + " non abilitato per la gestione ad albero");
	}
	JSONObject obj = new JSONObject();
	if (StringUtils.isNotBlank(nmApplicParam)) {
	    try {
		BigDecimal idApplic = new BigDecimal(nmApplicParam);
		RoleTree tree = (RoleTree) getSession()
			.getAttribute(treeSessionAttribute + "_" + idApplic.toPlainString());
		if (tree != null && tree.isEdited()) {
		    // Se l'albero era già stato caricato in precedenza, allora ricarico i nodi
		    // selezionati nell'albero
		    // in sessione
		    if (tree.getTreeType()
			    .equals(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name())) {
			obj.put("id", tree.getSelectedNodes());
			obj.put("readOnlyIds", tree.getMenuNodes());
		    } else if (tree.getTreeType()
			    .equals(ConstPrfDichAutor.TiDichAutor.PAGINA.name())) {
			// Se il numero di nodi selezinati è uguale al numero totale di pagine,
			// significa che sono nel
			// caso ALL_ABILITAZIONI, dunque passo solo l'id della radice
			if (tree.getSelectedNodes().size() == tree.getTotalPageNodes().intValue()) {
			    List<String> nodes = new ArrayList<>();
			    nodes.add(tree.getIdElement());
			    obj.put("id", nodes);
			} else {
			    obj.put("id", tree.getSelectedNodes());
			    obj.put("readOnlyIds", tree.getMenuNodes());
			}
			/*
			 * QUESTE DUE CONDIZIONI POTREBBERO ESSERE UNITE IN UNA UNICA, MA OGGI SONO
			 * PIGRO
			 */
		    } else if (tree.getTreeType()
			    .equals(ConstPrfDichAutor.TiDichAutor.AZIONE.name())) {
			if (tree.getSelectedNodes().size() == (tree.getTotalPageNodes().intValue()
				+ tree.getTotalActionNodes().intValue())) {
			    List<String> nodes = new ArrayList<>();
			    nodes.add(tree.getIdElement());
			    obj.put("id", nodes);
			} else {
			    obj.put("id", tree.getSelectedNodes());
			    obj.put("readOnlyIds", tree.getMenuNodes());
			}
		    }
		    obj.put("totalMenuNodes", tree.getTotalMenuNodes().intValue());
		} else if (StringUtils.isNotBlank(idRuoloParam)) {
		    // Carico i nodi selezionati dal db
		    BigDecimal idRuolo = new BigDecimal(idRuoloParam);
		    PrfVLisDichAutorTableBean nodes = amministrazioneRuoliEjb
			    .getPrfDichAutorViewBean(idRuolo, tiDichAutor.name(), idApplic);
		    final List<Object> idsList = nodes.toList(idToList);
		    switch (tiDichAutor) {
		    case ENTRY_MENU:
			obj.put("id", idsList);
			break;
		    case AZIONE:
			if (idsList.contains(null)) {
			    List<String> typesList = new ArrayList<>();
			    // Ho il caso ALL_ABILITAZIONI o diversi ALL_ABILITAZIONI_CHILD
			    final List<Object> idsList2 = nodes.toList(idToList2);
			    if (idsList2.contains(null)) {
				typesList.add(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.getValue());
				obj.put("mixedType", typesList);
				// Caso ALL_ABILITAZIONI
				obj.put("id", nodes.toList("id_entry_menu"));
			    } else {
				// Casi di ALL_ABILITAZIONI_CHILD e UNA_ABILITAZIONE
				List<Object> tmpDichAutors = nodes.toList("id_dich_autor");
				Set<Object> set = new LinkedHashSet<>(idsList);
				List<Object> orderedDichAutors = new ArrayList<>();
				set.remove(null);
				for (Object object : set) {
				    // UNA_ABILITAZIONE
				    typesList.add(ConstPrfDichAutor.TiDichAutor.AZIONE.getValue());

				    int indexObj = idsList.indexOf(object);
				    idsList.remove(indexObj);
				    idsList2.remove(indexObj);
				    orderedDichAutors.add(tmpDichAutors.remove(indexObj));
				}
				set.addAll(idsList2);
				orderedDichAutors.addAll(tmpDichAutors);

				Set<Object> set2 = new LinkedHashSet<>(idsList2);
				for (Object object : set2) {
				    typesList.add(ConstPrfDichAutor.TiDichAutor.PAGINA.getValue());
				}
				obj.put("mixedType", typesList);
				obj.put("id", set);
				obj.put("id_dich_autor", orderedDichAutors);
			    }
			} else {
			    obj.put("id", idsList);
			}
			break;
		    case PAGINA:
			// Se la lista di id_pagina_web contiene almeno un elemento null, significa
			// che SONO nel caso
			// ALL_ABILITAZIONI
			if (idsList.contains(null)) {
			    List<String> typesList = new ArrayList<>();
			    typesList.add(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.getValue());
			    // Sono nel caso ALL_ABILITAZIONI
			    obj.put("id", nodes.toList("id_entry_menu"));
			    obj.put("mixedType", typesList);
			} else {
			    obj.put("id", idsList);
			}
			break;
		    case SERVIZIO_WEB:
		    default:
			throw new AssertionError(
				tiDichAutor.name() + " non abilitato per la gestione ad albero");
		    }
		    if (!obj.has("id_dich_autor")) {
			obj.put("id_dich_autor", nodes.toList("id_dich_autor"));
		    }
		    obj.put("totalMenuNodes",
			    amministrazioneRuoliEjb.getTotalMenus(idApplic).intValue());
		}
		obj.put("type", type);
	    } catch (JSONException ex) {
		obj = new JSONObject();
	    }
	}
	return obj;
    }

    /**
     * Metodo richiamato sul metodo di check del nodo di jsTree, esegue la selezione del nodo
     * nell'albero 'logico' assegnandogli l'idDichAutor
     *
     * @param sessionAttributeTree identifica l'albero per cui eseguire la selezione
     *
     * @throws EMFError errore generico
     */
    public void selectNode(String sessionAttributeTree) throws EMFError {
	BigDecimal idApplic = getForm().getDichAutorFields().getNm_applic_tree().parse();
	String idNode = getRequest().getParameter(TREE_ID_NODE_ATTRIBUTE);
	String[] nodesPath = getRequest().getParameterValues(TREE_NODE_PATH_ATTRIBUTE);
	List<String> nodes = Arrays.asList(nodesPath);
	BigDecimal idDichAutor = StringUtils
		.isNotBlank(getRequest().getParameter(TREE_ID_DICH_AUTOR_ATTRIBUTE))
			? new BigDecimal(getRequest().getParameter(TREE_ID_DICH_AUTOR_ATTRIBUTE))
			: null;
	RoleTree tree = (RoleTree) getSession()
		.getAttribute(sessionAttributeTree + "_" + idApplic.toPlainString());
	JSONObject responseJson = new JSONObject();
	try {
	    try {
		if (tree != null) {
		    tree.selectChild(idNode, nodes, idDichAutor);
		    getSession().setAttribute(sessionAttributeTree + "_" + idApplic.toPlainString(),
			    tree);
		    responseJson.put("RESULT", true);
		} else {
		    responseJson.put("RESULT", false);
		    responseJson.put("ERROR", "Impossibile recuperare l'albero di nodi");
		}
	    } catch (ParerUserError ex) {
		final String message = "Errore inatteso nel popolamento dell'albero dei ruoli "
			+ ex.getDescription();
		log.error(message, ex);
		responseJson.put("RESULT", false);
		responseJson.put("ERROR", message);
	    }
	} catch (JSONException ex) {
	    throw new EMFError(EMFError.ERROR,
		    "Errore inatteso nel popolamento dell'albero dei ruoli "
			    + ExceptionUtils.getRootCauseMessage(ex),
		    ex);
	}
	redirectToAjax(responseJson);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Gestione alberi degli entry menu">
    /**
     * Metodo di popolamento dell'albero dei ruoli riguardante gli entry menu Se l'albero era già
     * stato caricato in precedenza, vengono ripresi i nodi selezionati dalla sessione
     *
     * @throws EMFError errore generico
     */
    public void populateEntryMenuAbilitatiOnTriggerJs() throws EMFError {
	String nmApplicParam = getRequest()
		.getParameter(getForm().getDichAutorFields().getNm_applic_tree().getName());
	String idRuoloParam = getRequest()
		.getParameter(getForm().getDettaglioRuolo().getId_ruolo().getName());
	JSONObject populateNodiAbilitati = populateNodiAbilitati(nmApplicParam, idRuoloParam,
		ConstPrfDichAutor.TiDichAutor.ENTRY_MENU);
	// Rimuovo readOnlyIds che in questo caso creerebbe confusione, purtroppo non c'era altro
	// modo
	populateNodiAbilitati.remove("readOnlyIds");
	redirectToAjax(populateNodiAbilitati);
    }

    /**
     * Metodo di creazione dell'albero jsTree e dell'albero "logico" salvato in sessione Ritorna (al
     * metodo ajax che lo chiama) un JSON con l'elenco dei nodi
     *
     * @throws EMFError errore generico
     */
    public void createEntryMenuTree() throws EMFError {
	BigDecimal idApplic = null;
	String nmApplic = null;
	if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_RUOLO_WIZARD)) {
	    idApplic = getForm().getDichAutorFields().getNm_applic_tree().parse();
	    nmApplic = getForm().getDichAutorFields().getNm_applic_tree().getDecodedValue();
	} else if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_RUOLO)) {
	    idApplic = getForm().getDettaglioRuolo().getNm_applic_tree().parse();
	    nmApplic = getForm().getDettaglioRuolo().getNm_applic_tree().getDecodedValue();
	}
	getForm().getWizardEntryMenuTree().setHidden(false);
	createTree(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU, idApplic, nmApplic);
    }

    /**
     * Metodo richiamato sul metodo di check del nodo di jsTree, esegue la selezione del nodo
     * nell'albero 'logico'
     *
     * @throws EMFError errore generico
     */
    @SuppressLogging
    public void selectEntryMenuNode() throws EMFError {
	selectNode(TREE_ENTRY_MENU_ATTRIBUTE);
    }

    private JSONObject buildTree(AplVTreeMenuPagAzioTableBean nodeList, String treeSessionAttribute,
	    BigDecimal idApplic, String nmApplic, ConstPrfDichAutor.TiDichAutor tiDichAutor)
	    throws JSONException {
	JSONObject entry;
	int previousLevel = 1;
	int counter = 0;
	RoleTree roleTree = (RoleTree) getSession()
		.getAttribute(treeSessionAttribute + "_" + idApplic.toPlainString());
	Map<Integer, List<JSONObject>> map = new HashMap<>();
	Map<Integer, List<RoleTreeElement>> roleTreeMap = new HashMap<>();
	for (AplVTreeMenuPagAzioRowBean row : nodeList) {
	    entry = buildJSONNode(row, counter++);
	    int tmp = createHierarchicalMap(row, map, previousLevel, entry, counter);

	    if (roleTree == null) {
		roleTree = new RoleTree(idApplic, nmApplic, row.getIdNodo(), tiDichAutor.name(),
			ConstPrfDichAutor.TiDichAutor.getTiDichAutor(row.getTipoNodo()).name());
		createHierarchicalMap(row, roleTreeMap, previousLevel, roleTree, counter);
	    } else {
		RoleTreeElement roleTreeElement = new RoleTreeElement(
			ConstPrfDichAutor.TiDichAutor.getTiDichAutor(row.getTipoNodo()).name(),
			null, row.getIdNodo(), row.getNiOrdNodi().intValue());
		createHierarchicalMap(row, roleTreeMap, previousLevel, roleTreeElement, counter);
	    }
	    previousLevel = tmp;
	}
	// Aggiungo all'albero calcolato QUANTI NODI esistono per il tipo di albero creato
	BigDecimal totalMenuNodes = BigDecimal.ZERO;
	BigDecimal totalPageNodes = BigDecimal.ZERO;
	BigDecimal totalActionNodes = BigDecimal.ZERO;
	switch (tiDichAutor) {
	case ENTRY_MENU:
	    totalMenuNodes = amministrazioneRuoliEjb.getTotalMenus(idApplic);
	    break;
	case AZIONE:
	    totalMenuNodes = amministrazioneRuoliEjb.getTotalMenus(idApplic);
	    totalPageNodes = amministrazioneRuoliEjb.getTotalPages(idApplic);
	    totalActionNodes = amministrazioneRuoliEjb.getTotalActions(idApplic);
	    break;
	case PAGINA:
	    totalPageNodes = amministrazioneRuoliEjb.getTotalPages(idApplic);
	    break;
	case SERVIZIO_WEB:
	default:
	    throw new AssertionError(
		    tiDichAutor.name() + " non abilitato per la gestione ad albero");
	}
	roleTree.setTotalMenuNodes(totalMenuNodes);
	roleTree.setTotalPageNodes(totalPageNodes);
	roleTree.setTotalActionNodes(totalActionNodes);
	getSession().setAttribute(treeSessionAttribute + "_" + idApplic.toPlainString(), roleTree);
	JSONObject tree = new JSONObject();
	tree.put("data", map.get(1));
	return tree;
    }

    private <T extends Object & Serializable> int createHierarchicalMap(
	    AplVTreeMenuPagAzioRowBean row, Map<Integer, List<T>> map, int previousLevel, T entry,
	    int counter) throws JSONException {
	int entryLevel = row.getNiLivello().intValue();
	List<T> list = map.get(entryLevel);
	if (list == null) {
	    list = new ArrayList<>();
	}
	if (previousLevel != entryLevel) {
	    if (previousLevel < entryLevel) {
		putEntryIntoChild(map, previousLevel, entry);
	    } else if (previousLevel > entryLevel) {
		putEntryIntoChild(map, entryLevel - 1, entry);
	    }
	    previousLevel = entryLevel;
	} else if (counter > 1) {
	    putEntryIntoChild(map, entryLevel - 1, entry);
	}
	list.add(entry);
	map.put(entryLevel, list);
	return previousLevel;
    }

    private <T extends Object & Serializable> void putEntryIntoChild(Map<Integer, List<T>> map,
	    int level, T entry) throws JSONException {
	List<T> fatherList = map.get(level);
	T fatherNode = fatherList.get(fatherList.size() - 1);
	if (entry instanceof JSONObject) {
	    ((JSONObject) fatherNode).getJSONArray("children").put(entry);
	} else if (entry instanceof RoleTreeElement) {
	    RoleTreeElement child = (RoleTreeElement) entry;
	    child.setNumOrdine(((RoleTreeElement) fatherNode).getChildrenNumber());
	    ((RoleTreeElement) fatherNode).addChild(child.getIdElement(), child);
	}
    }

    private JSONObject buildJSONNode(AplVTreeMenuPagAzioRowBean rowBean, int counter)
	    throws JSONException {
	// JSONObject node = new JSONObject();
	String icon;
	if (rowBean.getIdNodo().startsWith("M")) {
	    icon = "red";
	} else if (rowBean.getIdNodo().startsWith("P")) {
	    icon = "blue";
	} else {
	    icon = "black";
	}
	JSONObject node = new JSONObject();
	node.put("id", rowBean.getIdNodo());
	node.put("tablePosition", counter);
	node.put("type", icon);
	// node.put("attr", attrs);
	JSONObject elementData = new JSONObject();
	node.put("text", rowBean.getDsNodo());
	node.put("attr", new JSONObject("{\"href\": \"#\"}"));
	// node.put("data", elementData);
	node.put("children", new JSONArray());
	return node;
    }
    // </editor-fold>

    /**
     * Metodo di creazione dell'albero jsTree e dell'albero "logico" salvato in sessione Ritorna (al
     * metodo ajax che lo chiama) un JSON con l'elenco dei nodi
     *
     * @throws EMFError errore generico
     */
    public void createPagineTree() throws EMFError {
	BigDecimal idApplic = null;
	String nmApplic = null;
	if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_RUOLO_WIZARD)) {
	    idApplic = getForm().getDichAutorFields().getNm_applic_tree().parse();
	    nmApplic = getForm().getDichAutorFields().getNm_applic_tree().getDecodedValue();
	} else if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_RUOLO)) {
	    idApplic = getForm().getDettaglioRuolo().getNm_applic_tree().parse();
	    nmApplic = getForm().getDettaglioRuolo().getNm_applic_tree().getDecodedValue();
	}
	// getForm().getWizardPagineWebTree().setHidden(false);
	createTree(ConstPrfDichAutor.TiDichAutor.PAGINA, idApplic, nmApplic);
    }

    /**
     * Metodo di popolamento dell'albero dei ruoli riguardante le pagine. Se l'albero era già stato
     * caricato in precedenza, vengono ripresi i nodi selezionati dalla sessione
     *
     * @throws EMFError errore generico
     */
    // public void populatePagineAbilitateOnTriggerJs() throws EMFError {
    // String nmApplicParam =
    // getRequest().getParameter(getForm().getDichAutorFields().getNm_applic_tree().getName());
    // String idRuoloParam =
    // getRequest().getParameter(getForm().getDettaglioRuolo().getId_ruolo().getName());
    // populateNodiAbilitati(nmApplicParam, idRuoloParam, ConstPrfDichAutor.TiDichAutor.PAGINA);
    // }
    /**
     * Metodo richiamato sul metodo di check del nodo di jsTree, esegue la selezione del nodo
     * nell'albero 'logico'
     *
     * @throws EMFError errore generico
     */
    @SuppressLogging
    public void selectPaginaNode() throws EMFError {
	selectNode(TREE_PAGES_ATTRIBUTE);
    }

    /**
     * Metodo di creazione dell'albero jsTree e dell'albero "logico" salvato in sessione Ritorna (al
     * metodo ajax che lo chiama) un JSON con l'elenco dei nodi
     *
     * @throws EMFError errore generico
     */
    public void createAzioniTree() throws EMFError {
	BigDecimal idApplic = null;
	String nmApplic = null;
	if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_RUOLO_WIZARD)) {
	    idApplic = getForm().getDichAutorFields().getNm_applic_tree().parse();
	    nmApplic = getForm().getDichAutorFields().getNm_applic_tree().getDecodedValue();
	} else if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_RUOLO)) {
	    idApplic = getForm().getDettaglioRuolo().getNm_applic_tree().parse();
	    nmApplic = getForm().getDettaglioRuolo().getNm_applic_tree().getDecodedValue();
	}
	getForm().getWizardAzioniTree().setHidden(false);
	createTree(ConstPrfDichAutor.TiDichAutor.AZIONE, idApplic, nmApplic);
    }

    /**
     * Metodo di popolamento dell'albero dei ruoli riguardante le pagine. Se l'albero era già stato
     * caricato in precedenza, vengono ripresi i nodi selezionati dalla sessione
     *
     * @throws EMFError errore generico
     */
    public void populateAzioniAbilitateOnTriggerJs() throws EMFError {
	String nmApplicParam = getRequest()
		.getParameter(getForm().getDichAutorFields().getNm_applic_tree().getName());
	String idRuoloParam = getRequest()
		.getParameter(getForm().getDettaglioRuolo().getId_ruolo().getName());
	// Costruisco i 3 JSON "parziali" popolati con i dati ricavati dalle tabelle del DB
	JSONObject menu = populateNodiAbilitati(nmApplicParam, idRuoloParam,
		ConstPrfDichAutor.TiDichAutor.ENTRY_MENU);
	JSONObject azioni = populateNodiAbilitati(nmApplicParam, idRuoloParam,
		ConstPrfDichAutor.TiDichAutor.AZIONE);
	JSONObject pagine = populateNodiAbilitati(nmApplicParam, idRuoloParam,
		ConstPrfDichAutor.TiDichAutor.PAGINA);
	// Ora li unisco per ricavare l'unico JSON da ritornare
	try {
	    if (menu != null) {
		JSONArray readOnlyIds = menu.optJSONArray("readOnlyIds");
		if (readOnlyIds != null) {
		    azioni.put("readOnlyIds", readOnlyIds);
		} else {
		    JSONArray ids = menu.optJSONArray("id");
		    azioni.put("readOnlyIds", ids);
		}
	    }
	    if (pagine != null) {
		JSONArray pagineAbilitate = pagine.optJSONArray("id");
		JSONArray pagineDichAutor = pagine.optJSONArray("id_dich_autor");
		if (pagine.has("mixedType")) {
		    azioni.put("pageMixedType", pagine.get("mixedType"));
		}
		azioni.put("pageIds", pagineAbilitate);
		azioni.put("pageIdDichAutor", pagineDichAutor);
	    }
	} catch (JSONException ex) {
	    azioni = new JSONObject();
	}
	redirectToAjax(azioni);
    }

    /**
     * Metodo richiamato sul metodo di check del nodo di jsTree, esegue la selezione del nodo
     * nell'albero 'logico'
     *
     * @throws EMFError errore generico
     */
    @SuppressLogging
    public void selectAzioneNode() throws EMFError {
	selectNode(TREE_ACTIONS_ATTRIBUTE);
    }

    @Override
    public void checkAll() throws EMFError {
    }

    @Override
    public void uncheckAll() throws EMFError {
    }

    @Override
    public void collapseAll() throws EMFError {
    }

    @Override
    public void expandAll() throws EMFError {
    }

    @Override
    public void showList() throws EMFError {
	boolean state = Boolean
		.parseBoolean(getForm().getDettaglioRuolo().getShow_tree().getValue());
	getForm().getDettaglioRuolo().getShow_tree().setValue(String.valueOf(!state));

	if (!state) {
	    getForm().getDettaglioRuolo().getShowList()
		    .setDescription("Mostra lista di abilitazioni");
	} else {
	    getForm().getDettaglioRuolo().getShowList()
		    .setDescription("Mostra albero di abilitazioni");
	}
	forwardToPublisher(getLastPublisher());
    }

    @Override
    public void allineaRuolo1() throws EMFError {
	String sistChiamante = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.CHIAMANTE_ALLINEA_RUOLI.name(), null, null,
		it.eng.saceriam.common.Constants.TipoIamVGetValAppart.APPLIC);
	String dest = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.DESTINATARIO_ALLINEA_RUOLI_1.name(), null, null,
		it.eng.saceriam.common.Constants.TipoIamVGetValAppart.APPLIC);
	String user = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.USER_ALLINEA_RUOLI.name(), null, null,
		it.eng.saceriam.common.Constants.TipoIamVGetValAppart.APPLIC);
	String psw = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.PSW_ALLINEA_RUOLI.name(), null, null,
		it.eng.saceriam.common.Constants.TipoIamVGetValAppart.APPLIC);
	String url = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.URL_ALLINEA_RUOLI_1.name(), null, null,
		it.eng.saceriam.common.Constants.TipoIamVGetValAppart.APPLIC);
	int timeout = Integer.valueOf(paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.TIMEOUT_ALLINEA_RUOLI.name(), null, null,
		it.eng.saceriam.common.Constants.TipoIamVGetValAppart.APPLIC));
	final AllineaAmbiente ambiente = AllineaAmbiente.ALLINEA_AMBIENTE_1;

	callAllineaRuolo(dest, user, psw, url, timeout, ambiente, sistChiamante);
    }

    private void callAllineaRuolo(String dest, String user, String psw, String url, int timeout,
	    final AllineaAmbiente ambiente, String sistChiamante) throws EMFError {
	boolean erroreAllineamento = false;
	if (StringUtils.isNotBlank(dest) && !dest.equalsIgnoreCase("nullo")
		&& StringUtils.isNotBlank(user) && StringUtils.isNotBlank(psw)
		&& StringUtils.isNotBlank(url)) {
	    BigDecimal idRuolo = getForm().getDettaglioRuolo().getId_ruolo().parse();
	    if (idRuolo != null) {
		boolean ricaricaPagina = true;
		try {
		    amministrazioneRuoliEjb.updateEsitoAllineamentoPrfRuolo(idRuolo, null,
			    ambiente);

		    String nmRuolo = getForm().getDettaglioRuolo().getNm_ruolo().parse();
		    String dsRuolo = getForm().getDettaglioRuolo().getDs_ruolo().parse();
		    String tiRuolo = getForm().getDettaglioRuolo().getTi_ruolo().parse();
		    // String tiCategRuolo =
		    // getForm().getDettaglioRuolo().getTi_categ_ruolo().parse();

		    AllineaRuolo client = SoapClients.allineaRuoloClient(user, psw, url, timeout);
		    // /*
		    // * Codice aggiuntivo per il logging in caso di delete...
		    // */
		    // LogParam param = SpagoliteLogUtil.getLogParam(null, getUser().getUsername(),
		    // SpagoliteLogUtil.getPageName(this));
		    // if (param.getNomePagina().equals(Application.Publisher.DETTAGLIO_RUOLO)) {
		    // param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
		    // } else {
		    // param.setNomeAzione(
		    // SpagoliteLogUtil.getDetailActionNameDelete(this.getForm(),
		    // getForm().getListaRuoli()));
		    // }
		    // param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    if (client != null) {
			// Salvo lo stato allineamento come "IN_CORSO"
			amministrazioneRuoliEjb.updateStatoAllineamentoPrfRuolo(idRuolo,
				ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_IN_CORSO.name(),
				null, ambiente);
			ListaCategRuolo categRuolos = amministrazioneRuoliEjb
				.getListaCategRuolos(idRuolo);
			ListaApplic applics = amministrazioneRuoliEjb.getListaApplics(idRuolo);
			AllineaRuoloRisposta risposta = client.allineaRuolo(sistChiamante, user,
				nmRuolo, dsRuolo, tiRuolo, categRuolos, applics);
			if (risposta.getCdEsito().equals(CdEsito.OK)) {
			    // Salvo lo stato allineamento come "COMPLETO"
			    amministrazioneRuoliEjb.updateStatoAllineamentoPrfRuolo(idRuolo,
				    ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_COMPLETO
					    .name(),
				    null, ambiente);
			    getMessageBox().addInfo("Allineamento ruolo eseguito con successo");
			} else {
			    log.error("Allinea ruolo - Risposta WS negativa per l'utente " + user
				    + " " + risposta.getCdErr() + " - " + risposta.getDlErr());
			    if (risposta.getCdErr().equals(MessaggiWSBundle.SERVIZI_RUOLI_002)) {
				// Salvo lo stato allineamento come "PARZIALE"
				amministrazioneRuoliEjb.updateStatoAllineamentoPrfRuolo(idRuolo,
					ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_PARZIALE
						.name(),
					risposta.getDlErr(), ambiente);
				getMessageBox().addError(risposta.getDlErr());
			    } else {
				// Salvo lo stato allineamento come "IN_ERRORE"
				amministrazioneRuoliEjb.updateStatoAllineamentoPrfRuolo(idRuolo,
					ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_IN_ERRORE
						.name(),
					risposta.getDlErr(), ambiente);
				getMessageBox()
					.addError("Allineamento ruolo terminato con errore : "
						+ risposta.getCdErr() + " - "
						+ risposta.getDlErr());
				if (!dest.equals("Test")) {
				    deleteRuoloPerAllineamento(idRuolo);
				}
				ricaricaPagina = false;
				erroreAllineamento = true;
			    }
			}
		    } else {
			amministrazioneRuoliEjb.updateStatoAllineamentoPrfRuolo(idRuolo,
				ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_IN_ERRORE.name(),
				"Errore nella creazione del client per la chiamata al WS di AllineaRuolo",
				ambiente);
			log.error("Allinea ruolo - Risposta WS negativa per l'utente " + user + " "
				+ Costanti.SERVIZI_USR_001);
			log.error(
				"Errore nella creazione del client per la chiamata al WS di AllineaRuolo");
			getMessageBox().addError("Impossibile contattare il sistema di " + dest);
		    }
		} catch (SOAPFaultException e) {
		    /*
		     * Se ricado in questo caso significa che ho avuto un problema sulle
		     * autorizzazioni
		     */
		    amministrazioneRuoliEjb.updateStatoAllineamentoPrfRuolo(idRuolo,
			    ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_IN_ERRORE.name(),
			    Costanti.SERVIZI_USR_005 + " - Utente " + user
				    + " che attiva il servizio non riconosciuto o non abilitato",
			    ambiente);
		    log.error("Allinea ruolo - Risposta WS negativa per l'utente " + user + " "
			    + Costanti.SERVIZI_USR_005
			    + " - Utente che attiva il servizio non riconosciuto o non abilitato",
			    e);
		    getMessageBox().addError("Errore di autenticazione sul sistema di " + dest);
		} catch (WebServiceException e) {
		    /*
		     * Se non risponde, mi annoto l'applicazione che ha dato problemi e scrivo nel
		     * log dell'utente
		     */
		    amministrazioneRuoliEjb.updateStatoAllineamentoPrfRuolo(idRuolo,
			    ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_IN_ERRORE.name(),
			    MessaggiWSBundle.SERVIZI_RUOLI_001
				    + " - Il servizio di allineamento non risponde",
			    ambiente);
		    log.error("Allinea ruolo - Risposta WS negativa per l'utente " + user + " "
			    + MessaggiWSBundle.SERVIZI_RUOLI_001
			    + " - Il servizio di allineamento non risponde", e);
		    getMessageBox().addError(
			    "Il servizio di allineamento non risponde sul sistema di " + dest);
		} catch (Exception e) {
		    amministrazioneRuoliEjb.updateStatoAllineamentoPrfRuolo(idRuolo,
			    ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_IN_ERRORE.name(),
			    "Errore inatteso sul servizio di allineamento "
				    + ExceptionUtils.getRootCauseMessage(e),
			    ambiente);
		    log.error("Allinea ruolo - Risposta WS negativa per l'utente " + user + " "
			    + Costanti.SERVIZI_USR_001, e);
		    getMessageBox().addError("Errore inatteso sul servizio di allineamento");
		}
		if (ricaricaPagina) {
		    loadDettaglioRuolo(idRuolo);
		}
	    } else {
		getMessageBox()
			.addError("Errore inatteso nel recupero dei dati di dettaglio del ruolo");
	    }
	} else {
	    getMessageBox().addError("Configurazione di sistema per l'allineamento errata");
	}
	if (erroreAllineamento) {
	    getDefaultPublsherName();
	} else {
	    forwardToPublisher(getLastPublisher());
	}
    }

    @Override
    public void allineaRuolo2() throws EMFError {
	String sistChiamante = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.CHIAMANTE_ALLINEA_RUOLI.name(), null, null,
		it.eng.saceriam.common.Constants.TipoIamVGetValAppart.APPLIC);
	String dest = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.DESTINATARIO_ALLINEA_RUOLI_2.name(), null, null,
		it.eng.saceriam.common.Constants.TipoIamVGetValAppart.APPLIC);
	String user = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.USER_ALLINEA_RUOLI.name(), null, null,
		it.eng.saceriam.common.Constants.TipoIamVGetValAppart.APPLIC);
	String psw = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.PSW_ALLINEA_RUOLI.name(), null, null,
		it.eng.saceriam.common.Constants.TipoIamVGetValAppart.APPLIC);
	String url = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.URL_ALLINEA_RUOLI_2.name(), null, null,
		it.eng.saceriam.common.Constants.TipoIamVGetValAppart.APPLIC);
	int timeout = Integer.valueOf(paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.TIMEOUT_ALLINEA_RUOLI.name(), null, null,
		it.eng.saceriam.common.Constants.TipoIamVGetValAppart.APPLIC));
	final AllineaAmbiente ambiente = AllineaAmbiente.ALLINEA_AMBIENTE_2;

	callAllineaRuolo(dest, user, psw, url, timeout, ambiente, sistChiamante);
    }

    private void deleteRuoloPerAllineamento(BigDecimal idRuolo) throws EMFError, ParerUserError {
	// PrfVLisRuoloRowBean ruolo = (PrfVLisRuoloRowBean)
	// getForm().getListaRuoli().getTable().getCurrentRow();
	// Recupera le applicazione associate ai servizi del ruolo
	// Ricavo l'insieme degli usi del ruolo (dove viene usato)
	Set<BigDecimal> idUsoRuoloSet = (Set<BigDecimal>) getForm().getListaRuoli().getTable()
		.getCurrentRow().getObject("set_usi");
	PrfVLisDichAutorTableBean services = amministrazioneRuoliEjb.getPrfDichAutorViewBean(
		idUsoRuoloSet, ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name(),
		getForm().getFiltriRuoli().getNm_applic().parse());
	Set<BigDecimal> setApplicServizi = new HashSet<>();
	for (PrfVLisDichAutorRowBean rowAut : services) {
	    setApplicServizi.add(rowAut.getIdApplic());
	}
	auth.deletePrfRuolo(idRuolo, setApplicServizi);
	int rowIndex = getForm().getListaRuoli().getTable().getCurrentRowIndex();
	getForm().getListaRuoli().getTable().remove(rowIndex);
    }

    @Override
    public void logEventi() throws EMFError {
	GestioneLogEventiForm form = new GestioneLogEventiForm();
	form.getOggettoDetail().getNmApp().setValue(paramHelper.getParamApplicApplicationName());
	form.getOggettoDetail().getNm_tipo_oggetto().setValue(SacerLogConstants.TIPO_OGGETTO_RUOLO);
	form.getOggettoDetail().getIdOggetto()
		.setValue(getForm().getDettaglioRuolo().getId_ruolo().getValue());
	redirectToAction(it.eng.parer.sacerlog.slite.gen.Application.Actions.GESTIONE_LOG_EVENTI,
		"?operation=inizializzaLogEventi", form);
    }

}
