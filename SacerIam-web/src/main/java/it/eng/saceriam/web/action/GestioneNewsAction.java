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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;

import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.saceriam.exception.IncoherenceException;
import it.eng.saceriam.slite.gen.Application;
import it.eng.saceriam.slite.gen.action.GestioneNewsAbstractAction;
import it.eng.saceriam.slite.gen.form.GestioneNewsForm.DettaglioNews;
import it.eng.saceriam.slite.gen.form.GestioneNewsForm.FiltriNews;
import it.eng.saceriam.slite.gen.tablebean.AplApplicRowBean;
import it.eng.saceriam.slite.gen.tablebean.AplApplicTableBean;
import it.eng.saceriam.slite.gen.tablebean.AplApplicTableDescriptor;
import it.eng.saceriam.slite.gen.tablebean.AplNewsRowBean;
import it.eng.saceriam.slite.gen.tablebean.AplNewsTableBean;
import it.eng.saceriam.web.ejb.GestioneNewsEjb;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.db.base.sorting.SortingRule;
import it.eng.spagoLite.db.oracle.decode.DecodeMap;
import it.eng.spagoLite.form.base.BaseElements.Status;
import it.eng.spagoLite.form.fields.Field;
import it.eng.spagoLite.form.fields.Fields;
import it.eng.spagoLite.message.Message;
import it.eng.spagoLite.message.Message.MessageLevel;
import it.eng.spagoLite.message.MessageBox.ViewMode;
import it.eng.spagoLite.security.Secure;

/**
 *
 * @author Gilioli_P feat. Pippo_Em
 */
@SuppressWarnings({
	"unchecked", "rawtypes" })
public class GestioneNewsAction extends GestioneNewsAbstractAction {

    private static final Logger logger = LoggerFactory.getLogger(GestioneNewsAction.class);

    @EJB(mappedName = "java:app/SacerIam-ejb/GestioneNewsEjb")
    private GestioneNewsEjb gestioneNewsEjb;

    @Override
    public void initOnClick() throws EMFError {
    }

    /**
     * Se ho cliccato sul tasto di inserimento News dalla lista news
     *
     * @throws EMFError errore generico
     */
    @Override
    public void insertDettaglio() throws EMFError {
	String lista = getRequest().getParameter("table");
	if (lista.equals(getForm().getListaNews().getName())) {
	    // Setto lista e dettaglio in modalitÃ  editabile
	    getForm().getDettaglioNews().setEditMode();
	    getForm().getApplicazioniFields().setEditMode();
	    getForm().getDettaglioNews().clear();
	    getForm().getListaNews().setStatus(Status.insert);
	    getForm().getDettaglioNews().setStatus(Status.insert);
	    // Popolo la combo delle applicazioni
	    getForm().getApplicazioniFields().getNm_applic().setDecodeMap(getMappaApplic());
	    getForm().getApplicazioniFields().getDs_applic().setValue("\u00A0");
	    // Inizializzo, senza valori, la lista applicazioni
	    // e azzero l'insieme applicazioni in sessione usato per i controlli di coerenza
	    getSession().removeAttribute("applicationsSet");
	    getForm().getApplicazioniList().clear();
	    AplApplicTableBean apl = new AplApplicTableBean();
	    apl.addSortingRule(AplApplicTableDescriptor.COL_NM_APPLIC, SortingRule.ASC);
	    apl.sort();
	    getForm().getApplicazioniList().setTable(apl);
	}
    }

    @Override
    public void update(Fields<Field> fields) throws EMFError {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Fields<Field> fields) throws EMFError {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void loadDettaglio() throws EMFError {
	String lista = getTableName();
	if (lista != null) {
	    // Se carico il dettaglio della news e NON sono in fase di inserimento
	    if (lista.equals(getForm().getListaNews().getName())
		    && !getNavigationEvent().equals(NE_DETTAGLIO_INSERT)) {
		// Imposto lista e dettaglio in modalitÃ  visualizzazione
		getForm().getListaNews().setStatus(Status.view);
		// getForm().getListaNews().setHideDeleteButton(true);
		getForm().getDettaglioNews().setViewMode();
		getForm().getApplicazioniFields().setViewMode();
		// Setto la combo delle applicazioni
		getForm().getApplicazioniFields().getNm_applic().setDecodeMap(getMappaApplic());
		getForm().getApplicazioniFields().getDs_applic().setValue("\u00A0");
		// Carico le info del dettaglio NEWS
		AplNewsRowBean newsRowBean = (AplNewsRowBean) getForm().getListaNews().getTable()
			.getCurrentRow();
		getForm().getDettaglioNews().copyFromBean(newsRowBean);

		// Carico da DB la lista delle applicazioni associate alla news
		AplApplicTableBean applicTableBean = gestioneNewsEjb
			.getAplApplicByIdNewsTableBean(newsRowBean.getBigDecimal("id_news"));
		getForm().getApplicazioniList().setTable(applicTableBean);
		getForm().getApplicazioniList().getTable().setPageSize(10);
		getForm().getApplicazioniList().getTable()
			.addSortingRule(AplApplicTableDescriptor.COL_NM_APPLIC, SortingRule.ASC);
		getForm().getApplicazioniList().getTable().sort();
		getForm().getApplicazioniList().getTable().first();
		// Disabilito i tasti sulla lista applicazioni
		getForm().getApplicazioniList().setUserOperations(false, false, false, false);

		// Replico la lista applicazioni in sessioni per controlli di coerenza
		Set<BigDecimal> apps = getApplicationsSet();
		for (AplApplicRowBean aplApplic : applicTableBean) {
		    apps.add(aplApplic.getIdApplic());
		}
		getSession().setAttribute("applicationsSet", apps);
	    }
	}
    }

    /**
     * Annullamento modiche news
     *
     * @throws EMFError errore generico
     */
    @Override
    public void undoDettaglio() throws EMFError {
	String publisher = getLastPublisher();
	// Se provengo da dettaglio in fase di inserimento
	if (publisher.equals(Application.Publisher.DETTAGLIO_NEWS)
		&& getForm().getDettaglioNews().getStatus() != null
		&& getForm().getDettaglioNews().getStatus().toString().equals("insert")) {
	    // Torno indietro, annullando eventuali modifiche
	    goBack();
	} else {
	    // Altrimenti se provengo da dettaglio in fase di modifica
	    loadDettaglio();
	}
	// Rimuovi comunque l'attributo in sessione
	getSession().removeAttribute("applicationsSet");
    }

    /**
     * Richiama il metodo per il salvataggio del dettaglio
     *
     * @throws EMFError errore generico
     */
    @Override
    public void saveDettaglio() throws EMFError {
	String publisher = getLastPublisher();
	if (publisher.equals(Application.Publisher.DETTAGLIO_NEWS)) {
	    salvaNews();
	}
    }

    @Override
    public void dettaglioOnClick() throws EMFError {
	String lista = getTableName();
	if (lista != null) {
	    if (getForm().getListaNews().getName().equals(lista)) {
		forwardToPublisher(Application.Publisher.DETTAGLIO_NEWS);
	    }
	}
    }

    @Override
    public void elencoOnClick() throws EMFError {
	goBack();
    }

    @Override
    protected String getDefaultPublsherName() {
	return Application.Publisher.RICERCA_NEWS;
    }

    @Override
    public void process() throws EMFError {
    }

    /**
     * Esegue la post dei campi: utilizzato quando forzo la post a seguito dell'eliminazione di una
     * riga dalla lista applicazione, operazione che, essendo un link (lavora in get), non
     * effettuerebbe la post http
     *
     * @throws EMFError errore generico
     */
    public void postData() throws EMFError {
	getForm().getDettaglioNews().post(getRequest());
    }

    @Override
    public void reloadAfterGoBack(String publisherName) {
	try {
	    // Rieseguo la query per avere la lista News tenuto conto delle modifiche apportate
	    int inizio = getForm().getListaNews().getTable().getFirstRowPageIndex();
	    int paginaCorrente = getForm().getListaNews().getTable().getCurrentPageIndex();
	    int pageSize = getForm().getListaNews().getTable().getPageSize();
	    getForm().getListaNews()
		    .setTable(gestioneNewsEjb.getAplNewsTableBean(getForm().getFiltriNews()));
	    getForm().getListaNews().getTable().setPageSize(pageSize);
	    getForm().getListaNews().setUserOperations(true, true, true, true);
	    // rieseguo la query se necessario
	    this.lazyLoadGoPage(getForm().getListaNews(), paginaCorrente);
	    // ritorno alla pagina
	    getForm().getListaNews().getTable().setCurrentRowIndex(inizio);
	} catch (Exception e) {
	    logger.error(e.getMessage(), e);
	}
    }

    @Override
    public String getControllerName() {
	return Application.Actions.GESTIONE_NEWS;
    }

    @Secure(action = "Menu.AmministrazioneSistema.GestioneNews")
    public void ricercaNewsPage() throws EMFError {
	getUser().getMenu().reset();
	getUser().getMenu().select("Menu.AmministrazioneSistema.GestioneNews");
	// Pulisco i filtri di ricerca news
	getForm().getFiltriNews().reset();
	getForm().getFiltriNews().setEditMode();
	getForm().getFiltriNews().getRicercaNews().setEditMode();

	getForm().getListaNews().clear();
	getForm().getListaNews().setStatus(Status.view);

	// Azzero in sessione l'attributo che mi tiene conto delle applicazioni
	getSession().removeAttribute("applicationsSet");
    }

    /**
     * Ricerca news in base ai filtri passati in ingresso
     *
     * @throws EMFError errore generico
     */
    @Override
    public void ricercaNews() throws EMFError {
	FiltriNews filtriNews = getForm().getFiltriNews();
	filtriNews.post(getRequest());
	if (filtriNews.validate(getMessageBox())) {
	    AplNewsTableBean newsTableBean = gestioneNewsEjb.getAplNewsTableBean(filtriNews);
	    getForm().getListaNews().setTable(newsTableBean);
	    getForm().getListaNews().getTable().first();
	    getForm().getListaNews().getTable().setPageSize(10);
	    getForm().getListaNews().setStatus(Status.view);
	    getForm().getListaNews().setHideDeleteButton(false);
	}
	forwardToPublisher(Application.Publisher.RICERCA_NEWS);
    }

    /**
     * Effettua il salvataggio della news
     *
     * @throws EMFError errore generico
     */
    private void salvaNews() throws EMFError {
	getMessageBox().clear();
	AplNewsRowBean newsRowBean = new AplNewsRowBean();
	AplApplicTableBean applicTableBean = (AplApplicTableBean) getForm().getApplicazioniList()
		.getTable();

	// Post dei filtri compilati
	DettaglioNews newsDetail = getForm().getDettaglioNews();
	newsDetail.post(getRequest());

	// Imposto i flaggozzi nel front-end (in pratica ne faccio il post)
	getForm().getDettaglioNews().getFl_pubblic_homepage().setChecked(
		getRequest().getParameter("Fl_pubblic_homepage") != null ? true : false);
	getForm().getDettaglioNews().getFl_pubblic_login()
		.setChecked(getRequest().getParameter("Fl_pubblic_login") != null ? true : false);

	// ArrayList contenente il nome dei campi obbligatori che non sono stati compilati
	List<String> campiErrati = new ArrayList();

	// Valida i filtri per verificare quelli obbligatori
	String oggetto = newsDetail.getDs_oggetto().parse();
	String testo = newsDetail.getDl_testo().parse();
	if (oggetto == null) {
	    campiErrati.add(newsDetail.getDs_oggetto().getDescription());
	}
	if (testo == null) {
	    campiErrati.add(newsDetail.getDl_testo().getDescription());
	}

	Calendar today = GregorianCalendar.getInstance();
	today.set(Calendar.HOUR_OF_DAY, 0);
	today.set(Calendar.MINUTE, 0);
	today.set(Calendar.SECOND, 0);
	today.set(Calendar.MILLISECOND, 0);

	if (newsDetail.getDt_ini_pubblic().parse() == null) {
	    campiErrati.add(newsDetail.getDt_ini_pubblic().getDescription());
	    // } else if (!getForm().getDettaglioNews().getStatus().equals(Status.update) &&
	    // newsDetail.getDt_ini_pubblic().parse().before(today.getTime())) {
	} else if (newsDetail.getDt_ini_pubblic().parse().before(today.getTime())) {
	    getMessageBox().addError(
		    "Errore di compilazione form: data pubblicazione precedente alla data odierna</br>");
	}
	if (newsDetail.getDt_fin_pubblic().parse() == null) {
	    campiErrati.add(newsDetail.getDt_fin_pubblic().getDescription());
	} else if (newsDetail.getDt_ini_pubblic().parse() != null && newsDetail.getDt_ini_pubblic()
		.parse().after(newsDetail.getDt_fin_pubblic().parse())) {
	    getMessageBox().addError(
		    "Errore di compilazione form: data soppressione precedente a data istituzione</br>");
	}
	if (getRequest().getParameter("Fl_pubblic_homepage") == null
		&& getRequest().getParameter("Fl_pubblic_login") == null) {
	    getMessageBox().addError(
		    "Errore di compilazione form: selezionare almeno un metodo di pubblicazione</br>");
	}

	if (campiErrati.size() > 0) {
	    getMessageBox().addError("Errore: mancata valorizzazione dei campi obbligatori ");
	    getMessageBox().addError(campiErrati.get(0));
	    for (int i = 1; i < campiErrati.size(); i++) {
		getMessageBox().addError(", " + campiErrati.get(i));
	    }
	    getMessageBox().addError("</br>");
	}

	if (getForm().getDettaglioNews().getFl_pubblic_homepage().isChecked()
		&& applicTableBean.isEmpty()) {
	    getMessageBox().addError(
		    "Attenzione: flag mostra in home page settato ma nessuna applicazione selezionata!</br>");
	}

	if (!getForm().getDettaglioNews().getFl_pubblic_homepage().isChecked()
		&& !applicTableBean.isEmpty()) {
	    getMessageBox().addError(
		    "Attenzione: flag mostra in home page non settato nonostante siano state selezionate delle applicazioni!</br>");
	}

	if (newsDetail.validate(getMessageBox()) && getMessageBox().isEmpty()) {
	    try {
		newsDetail.copyToBean(newsRowBean);
		if (getRequest().getParameter("Fl_pubblic_homepage") != null) {
		    newsRowBean.setFlPubblicHomepage("1");
		} else {
		    newsRowBean.setFlPubblicHomepage("0");
		}
		if (getRequest().getParameter("Fl_pubblic_login") != null) {
		    newsRowBean.setFlPubblicLogin("1");
		} else {
		    newsRowBean.setFlPubblicLogin("0");
		}

		if (getForm().getDettaglioNews().getStatus().equals(Status.insert)) {
		    gestioneNewsEjb.insertAplNews(newsRowBean, applicTableBean);
		    // getForm().getListaNews().getTable().setCurrentRowIndex(getForm().getListaNews().getTable().getCurrentRowIndex());
		    getMessageBox().addMessage(
			    new Message(MessageLevel.INF, "News inserita con successo"));
		} else if (getForm().getDettaglioNews().getStatus().equals(Status.update)) {
		    BigDecimal idNews = ((AplNewsRowBean) getForm().getListaNews().getTable()
			    .getCurrentRow()).getIdNews();
		    gestioneNewsEjb.updateAplNews(idNews, newsRowBean, applicTableBean);
		    // getForm().getListaNews().getTable().setCurrentRowIndex(getForm().getListaNews().getTable().getCurrentRowIndex());
		    getMessageBox().addMessage(
			    new Message(MessageLevel.INF, "News modificata con successo"));
		}
		getForm().getDettaglioNews().setViewMode();
		getForm().getApplicazioniFields().setViewMode();
		getForm().getDettaglioNews().setStatus(Status.view);
		getForm().getListaNews().setStatus(Status.view);
		getForm().getListaNews().setHideDeleteButton(false);

		// Rimuovo l'attributo in sessione delle applicazioni
		getSession().removeAttribute("applicationsSet");

		// goBack();
		int currentRow = getForm().getListaNews().getTable().getCurrentRowIndex();
		// Ricarico la lista (subito perchÃ¨ se ad esempio scorro i record del dettaglio con
		// le frecce,
		// non trovo ancora le eventuali modifiche apportate)
		reloadAfterGoBack(Application.Publisher.DETTAGLIO_NEWS);
		// Mi riposiziono nel record in cui mi trovavo
		getForm().getListaNews().getTable().setCurrentRowIndex(currentRow);
		loadDettaglio();
		getMessageBox().setViewMode(ViewMode.plain);
		forwardToPublisher(Application.Publisher.DETTAGLIO_NEWS);
	    } catch (IncoherenceException e) {
		logger.error("Errore nel salvataggio della news: " + e.getMessage(), e);
		getMessageBox().addError(e.getMessage());
	    }
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_NEWS);
    }

    @Override
    public void updateListaNews() throws EMFError {
	getForm().getDettaglioNews().setEditMode();
	getForm().getApplicazioniFields().setEditMode();
	getForm().getDettaglioNews().setStatus(Status.update);
	getForm().getListaNews().setStatus(Status.update);
	getForm().getApplicazioniList().setUserOperations(false, false, false, true);
    }

    /**
     * Cancella un elemento della lista news La modifica ha effetto immediato su DB
     *
     * @throws EMFError errore generico
     */
    @Override
    public void deleteListaNews() throws EMFError {
	getMessageBox().clear();
	AplNewsRowBean newsRowBean = (AplNewsRowBean) getForm().getListaNews().getTable()
		.getCurrentRow();
	try {
	    gestioneNewsEjb.deleteNews(newsRowBean);
	    goBack();
	    reloadAfterGoBack("/amministrazioneSistema/ricercaNews");
	    // Segnalo l'avvenuta modifica andata a buon fine
	    getMessageBox().addMessage(
		    new Message(MessageLevel.INF, "Cancellazione news effettuata con successo"));
	    getMessageBox().setViewMode(ViewMode.plain);
	} catch (IncoherenceException ex) {
	    logger.error("Errore nella cancellazione della news: " + ex.getMessage(), ex);
	    getMessageBox().addError(ex.getMessage());
	}
    }

    /**
     * Cancella un elemento dalla lista applicazioni La modifica NON ha effetto immediato su DB
     *
     * @throws EMFError errore generico
     */
    @Override
    public void deleteApplicazioniList() throws EMFError {
	AplApplicRowBean applicRowBean = (AplApplicRowBean) getForm().getApplicazioniList()
		.getTable().getCurrentRow();
	long idApplic = applicRowBean.getIdApplic().longValue();
	// Tolgo dal set delle applicazioni in sessione quello selezionato
	getApplicationsSet().remove(new BigDecimal(idApplic));
	// E dalla lista a video
	getForm().getApplicazioniList().getTable().remove();
	getForm().getApplicazioniList().getTable().sort();
	forwardToPublisher(Application.Publisher.DETTAGLIO_NEWS);
    }

    /**
     * Aggiunge un'applicazione scelta dalla combo a quelle presenti in lista
     *
     * @throws EMFError errore generico
     */
    @Override
    public void aggiungiApplicazione() throws EMFError {
	getForm().getDettaglioNews().post(getRequest());
	// Imposto i flaggozzi nel front-end (in pratica ne faccio il post)
	getForm().getDettaglioNews().getFl_pubblic_homepage().setChecked(
		getRequest().getParameter("Fl_pubblic_homepage") != null ? true : false);
	getForm().getDettaglioNews().getFl_pubblic_login()
		.setChecked(getRequest().getParameter("Fl_pubblic_login") != null ? true : false);
	BigDecimal applic = getForm().getApplicazioniFields().getNm_applic().parse();
	if (applic != null) {
	    if (getApplicationsSet().contains(applic)) {
		// Applicazione giÃ  presente
		getMessageBox().addError("Applicazione giÃ  presente nella lista");
	    } else {
		// Creo la nuova riga
		Set<BigDecimal> apps = getApplicationsSet();
		apps.add(applic);
		getSession().setAttribute("applicationsSet", apps);
		AplApplicRowBean row = gestioneNewsEjb.getAplApplicRowBean(applic);
		getForm().getApplicazioniList().getTable().add(row);
		getForm().getApplicazioniList().getTable().sort();
	    }
	} else {
	    // Non Ã¨ stata selezionata alcuna applicazione
	    getMessageBox().addError("Non Ã¨ stata selezionata alcuna applicazione");
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_NEWS);
    }

    public void saveApplicazioniList() throws EMFError {
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

    @Override
    public JSONObject triggerApplicazioniFieldsNm_applicOnTrigger() throws EMFError {
	getForm().getApplicazioniFields().post(getRequest());
	BigDecimal applic = getForm().getApplicazioniFields().getNm_applic().parse();
	if (applic != null) {
	    AplApplicRowBean aplApplicById = gestioneNewsEjb.getAplApplicRowBean(applic);
	    getForm().getApplicazioniFields().getDs_applic().setValue(aplApplicById.getDsApplic());
	} else {
	    getForm().getApplicazioniFields().getDs_applic().setValue("\u00A0");
	}
	return getForm().getApplicazioniFields().asJSON();
    }

    public DecodeMap getMappaApplic() throws EMFError {
	getForm().getApplicazioniFields().getNm_applic().clear();
	AplApplicTableBean applicTableBean = gestioneNewsEjb.getAplApplicTableBean();
	applicTableBean.addSortingRule(AplApplicTableDescriptor.COL_NM_APPLIC, SortingRule.ASC);
	applicTableBean.sort();
	DecodeMap applicDM = DecodeMap.Factory.newInstance(applicTableBean, "id_applic",
		"nm_applic");
	return applicDM;
    }

    @Override
    protected void postLoad() {
	// Elimino l'attributo relativo alle applicazioni per controllo di coerenza
	if (getNavigationEvent().equalsIgnoreCase(NE_DETTAGLIO_VIEW)) {
	    getSession().removeAttribute("applicationsSet");
	}
    }
}
