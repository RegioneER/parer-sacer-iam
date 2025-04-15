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
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.saceriam.exception.IncoherenceException;
import it.eng.saceriam.slite.gen.Application;
import it.eng.saceriam.slite.gen.action.GestioneNoteRilascioAbstractAction;
import it.eng.saceriam.slite.gen.form.GestioneNoteRilascioForm;
import it.eng.saceriam.slite.gen.form.GestioneNoteRilascioForm.DettaglioNoteRilascio;
import it.eng.saceriam.slite.gen.form.GestioneNoteRilascioForm.FiltriNoteRilascio;
import it.eng.saceriam.slite.gen.tablebean.AplNotaRilascioRowBean;
import it.eng.saceriam.slite.gen.tablebean.AplNotaRilascioTableBean;
import it.eng.saceriam.slite.gen.tablebean.AplNotaRilascioTableDescriptor;
import it.eng.saceriam.web.ejb.ComboEjb;
import it.eng.saceriam.web.ejb.GestioneNoteRilascioEjb;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.db.base.BaseTableInterface;
import it.eng.spagoLite.db.base.sorting.SortingRule;
import it.eng.spagoLite.form.base.BaseElements.Status;
import it.eng.spagoLite.form.base.BaseForm;
import it.eng.spagoLite.form.fields.Field;
import it.eng.spagoLite.form.fields.Fields;
import it.eng.spagoLite.form.fields.SingleValueField;
import it.eng.spagoLite.message.Message;
import it.eng.spagoLite.message.Message.MessageLevel;
import it.eng.spagoLite.message.MessageBox.ViewMode;
import it.eng.spagoLite.security.Secure;

/**
 *
 * @author DiLorenzo_F
 */
@SuppressWarnings({
	"unchecked" })
public class GestioneNoteRilascioAction extends GestioneNoteRilascioAbstractAction {

    private static final Logger logger = LoggerFactory.getLogger(GestioneNoteRilascioAction.class);

    @EJB(mappedName = "java:app/SacerIam-ejb/GestioneNoteRilascioEjb")
    private GestioneNoteRilascioEjb gestioneNoteRilascioEjb;

    @EJB(mappedName = "java:app/SacerIam-ejb/ComboEjb")
    private ComboEjb comboEjb;

    @Override
    public void initOnClick() throws EMFError {
    }

    /**
     * Se ho cliccato sul tasto di inserimento note di rilascio dalla lista note di rilascio
     *
     * @throws EMFError errore generico
     */
    @Override
    public void insertDettaglio() throws EMFError {
	String lista = getRequest().getParameter("table");
	if (lista.equals(getForm().getListaNoteRilascio().getName())) {
	    // Setto lista e dettaglio in modalitÃ  editabile
	    getForm().getDettaglioNoteRilascio().setEditMode();
	    getForm().getDettaglioNoteRilascio().clear();
	    getForm().getListaNoteRilascio().setStatus(Status.insert);
	    getForm().getDettaglioNoteRilascio().setStatus(Status.insert);
	    // Inizializzo la combo applicazioni
	    getForm().getDettaglioNoteRilascio().getId_applic().setDecodeMap(
		    comboEjb.getMappaApplicAbilitate(getUser().getIdUtente(), true, false));
	    getForm().getDettaglioNoteRilascio().getId_applic().setEditMode();

	    // Inizializzo, senza valori, la lista delle note di rilascio precedenti
	    // e azzero l'attributo in sessione usato per i controlli sulla visualizzazione della
	    // lista
	    getSession().removeAttribute("isFromNotaRilascioPrec");
	    getForm().getNoteRilascioPrecedentiList().clear();
	    AplNotaRilascioTableBean apl = new AplNotaRilascioTableBean();
	    apl.addSortingRule(AplNotaRilascioTableDescriptor.COL_DT_VERSIONE, SortingRule.DESC);
	    apl.sort();
	    getForm().getNoteRilascioPrecedentiList().setTable(apl);
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
	    // Se carico il dettaglio della nota di rilascio e NON sono in fase di inserimento
	    if (lista.equals(getForm().getListaNoteRilascio().getName())
		    && !getNavigationEvent().equals(NE_DETTAGLIO_INSERT)) {
		// Imposto lista e dettaglio in modalitÃ  visualizzazione
		getForm().getListaNoteRilascio().setStatus(Status.view);
		// getForm().getListaNoteRilascio().setHideDeleteButton(true);
		getForm().getDettaglioNoteRilascio().setViewMode();
		// Inizializzo la combo applicazioni
		getForm().getDettaglioNoteRilascio().getId_applic().setDecodeMap(
			comboEjb.getMappaApplicAbilitate(getUser().getIdUtente(), true, false));

		// Carico le info del dettaglio NOTE DI RILASCIO
		BigDecimal idNotaRilascio = ((AplNotaRilascioTableBean) getForm()
			.getListaNoteRilascio().getTable()).getCurrentRow().getIdNotaRilascio();
		AplNotaRilascioRowBean detailRow = gestioneNoteRilascioEjb
			.getAplNotaRilascioRowBean(idNotaRilascio);
		getForm().getDettaglioNoteRilascio().copyFromBean(detailRow);

		Calendar today = GregorianCalendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);

		// if (getForm().getDettaglioNoteRilascio().getDt_fine_val().parse() == null ||
		// (getForm().getDettaglioNoteRilascio().getDt_fine_val().parse() != null &&
		// today.getTime().compareTo(getForm().getDettaglioNoteRilascio().getDt_fine_val().parse())
		// <= 0)
		// &&
		// today.getTime().compareTo(getForm().getDettaglioNoteRilascio().getDt_ini_val().parse())
		// >= 0) {
		// getRequest().setAttribute("showNotaRilascio", true);
		// } else {
		// getRequest().setAttribute("infoDaMostrare", "Informazioni non disponibili");
		// }
		// In IAM la nota di rilascio deve essere sempre visualizzata nella sua interezza
		getRequest().setAttribute("showNotaRilascio", true);
		// Carico da DB la lista delle note di rilascio precedenti associate
		// all'applicazione
		AplNotaRilascioTableBean noteRilascioPrecTableBean = gestioneNoteRilascioEjb
			.getAplNoteRilascioPrecTableBean(detailRow.getBigDecimal("id_applic"),
				idNotaRilascio,
				new Date(detailRow.getTimestamp("dt_versione").getTime()));
		getForm().getNoteRilascioPrecedentiList().setTable(noteRilascioPrecTableBean);
		getForm().getNoteRilascioPrecedentiList().getTable().setPageSize(10);
		getForm().getNoteRilascioPrecedentiList().getTable().addSortingRule(
			AplNotaRilascioTableDescriptor.COL_DT_VERSIONE, SortingRule.DESC);
		getForm().getNoteRilascioPrecedentiList().getTable().sort();
		getForm().getNoteRilascioPrecedentiList().getTable().first();
		// Disabilito i tasti sulla lista note di rilascio precedenti
		getForm().getNoteRilascioPrecedentiList().setUserOperations(true, false, false,
			false);
	    }
	}
    }

    /**
     * Annullamento modiche note di rilascio
     *
     * @throws EMFError errore generico
     */
    @Override
    public void undoDettaglio() throws EMFError {
	String publisher = getLastPublisher();
	// Se provengo da dettaglio in fase di inserimento
	if (publisher.equals(Application.Publisher.DETTAGLIO_NOTE_RILASCIO)
		&& getForm().getDettaglioNoteRilascio().getStatus() != null
		&& getForm().getDettaglioNoteRilascio().getStatus().toString().equals("insert")) {
	    // Torno indietro, annullando eventuali modifiche
	    goBack();
	} else {
	    // Altrimenti se provengo da dettaglio in fase di modifica
	    loadDettaglio();
	}
	// Rimuovi comunque l'attributo in sessione
	getSession().removeAttribute("isFromNotaRilascioPrec");
    }

    /**
     * Richiama il metodo per il salvataggio del dettaglio
     *
     * @throws EMFError errore generico
     */
    @Override
    public void saveDettaglio() throws EMFError {
	String publisher = getLastPublisher();
	if (publisher.equals(Application.Publisher.DETTAGLIO_NOTE_RILASCIO)) {
	    salvaNoteRilascio();
	}
    }

    @Override
    public void dettaglioOnClick() throws EMFError {
	String lista = getTableName();
	if (lista != null) {
	    if (getForm().getListaNoteRilascio().getName().equals(lista)) {
		forwardToPublisher(Application.Publisher.DETTAGLIO_NOTE_RILASCIO);
	    } else if (getTableName().equals(getForm().getNoteRilascioPrecedentiList().getName())) {
		redirectToNoteRilascioPage();
	    }
	}
    }

    @Override
    public void elencoOnClick() throws EMFError {
	goBack();
    }

    @Override
    protected String getDefaultPublsherName() {
	return Application.Publisher.RICERCA_NOTE_RILASCIO;
    }

    @Override
    public void reloadAfterGoBack(String publisherName) {
	try {
	    if (publisherName.equals(Application.Publisher.RICERCA_NOTE_RILASCIO)) {
		int rowIndex;
		int pageSize;
		if (getForm().getListaNoteRilascio().getTable() != null) {
		    rowIndex = getForm().getListaNoteRilascio().getTable().getCurrentRowIndex();
		    pageSize = getForm().getListaNoteRilascio().getTable().getPageSize();
		} else {
		    rowIndex = 0;
		    pageSize = 10;
		}
		getForm().getListaNoteRilascio().setTable(gestioneNoteRilascioEjb
			.getAplNoteRilascioTableBean(getForm().getFiltriNoteRilascio()));
		getForm().getListaNoteRilascio().getTable().setPageSize(pageSize);
		getForm().getListaNoteRilascio().getTable().setCurrentRowIndex(rowIndex);
		getForm().getListaNoteRilascio().setUserOperations(true, true, true, true);
	    } else if (publisherName.equals(Application.Publisher.DETTAGLIO_NOTE_RILASCIO)) {
		Calendar today = GregorianCalendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		// if (getForm().getDettaglioNoteRilascio().getDt_fine_val().parse() == null ||
		// (getForm().getDettaglioNoteRilascio().getDt_fine_val().parse() != null &&
		// today.getTime().compareTo(getForm().getDettaglioNoteRilascio().getDt_fine_val().parse())
		// <= 0)
		// &&
		// today.getTime().compareTo(getForm().getDettaglioNoteRilascio().getDt_ini_val().parse())
		// >= 0) {
		// getRequest().setAttribute("showNotaRilascio", true);
		// } else {
		// getRequest().setAttribute("infoDaMostrare", "Informazioni non disponibili");
		// }
		// In IAM la nota di rilascio deve essere sempre visualizzata nella sua interezza
		getRequest().setAttribute("showNotaRilascio", true);
		getSession().removeAttribute("isFromNotaRilascioPrec");
	    }
	} catch (Exception e) {
	    logger.error(e.getMessage(), e);
	}
    }

    @Override
    public String getControllerName() {
	return Application.Actions.GESTIONE_NOTE_RILASCIO;
    }

    @Secure(action = "Menu.AmministrazioneSistema.GestioneNoteRilascio")
    public void ricercaNoteRilascioPage() throws EMFError {
	getUser().getMenu().reset();
	getUser().getMenu().select("Menu.AmministrazioneSistema.GestioneNoteRilascio");
	// Pulisco i filtri di ricerca note di rilascio
	getForm().getFiltriNoteRilascio().reset();
	getForm().getFiltriNoteRilascio().setEditMode();
	getForm().getFiltriNoteRilascio().getRicercaNoteRilascio().setEditMode();

	getForm().getFiltriNoteRilascio().getNm_applic().setDecodeMap(
		comboEjb.getMappaApplicAbilitate(getUser().getIdUtente(), true, false));
	getForm().getFiltriNoteRilascio().getNm_applic().setEditMode();

	getForm().getListaNoteRilascio().clear();
	getForm().getListaNoteRilascio().setStatus(Status.view);

	// Azzero in sessione l'attributo che mi tiene conto della lista delle note di rilascio
	// precedenti
	getSession().removeAttribute("isFromNotaRilascioPrec");
    }

    /**
     * Ricerca note di rilascio in base ai filtri passati in ingresso
     *
     * @throws EMFError errore generico
     */
    @Override
    public void ricercaNoteRilascio() throws EMFError {
	FiltriNoteRilascio filtriNoteRilascio = getForm().getFiltriNoteRilascio();

	filtriNoteRilascio.post(getRequest());
	if (filtriNoteRilascio.validate(getMessageBox())) {
	    AplNotaRilascioTableBean noteRilascioTableBean = gestioneNoteRilascioEjb
		    .getAplNoteRilascioTableBean(filtriNoteRilascio);
	    getForm().getListaNoteRilascio().setTable(noteRilascioTableBean);
	    getForm().getListaNoteRilascio().getTable().first();
	    getForm().getListaNoteRilascio().getTable().setPageSize(10);
	    getForm().getListaNoteRilascio().setStatus(Status.view);
	    getForm().getListaNoteRilascio().setHideDeleteButton(false);
	}
	forwardToPublisher(Application.Publisher.RICERCA_NOTE_RILASCIO);
    }

    /**
     * Effettua il salvataggio della note di rilascio
     *
     * @throws EMFError errore generico
     */
    private void salvaNoteRilascio() throws EMFError {
	getMessageBox().clear();
	AplNotaRilascioRowBean noteRilascioRowBean = new AplNotaRilascioRowBean();

	// Post dei filtri compilati
	DettaglioNoteRilascio noteRilascioDetail = getForm().getDettaglioNoteRilascio();
	noteRilascioDetail.post(getRequest());

	// ArrayList contenente il nome dei campi obbligatori che non sono stati compilati
	List<String> campiErrati = new ArrayList<>();

	// Valida i filtri per verificare quelli obbligatori
	BigDecimal idApplic = noteRilascioDetail.getId_applic().parse();
	String cdVersione = noteRilascioDetail.getCd_versione().parse();
	Date dtVersione = noteRilascioDetail.getDt_versione().parse();
	String blNota = noteRilascioDetail.getBl_nota().parse();
	if (idApplic == null) {
	    campiErrati.add(noteRilascioDetail.getId_applic().getDescription());
	}
	if (cdVersione == null) {
	    campiErrati.add(noteRilascioDetail.getCd_versione().getDescription());
	}
	if (dtVersione == null) {
	    campiErrati.add(noteRilascioDetail.getDt_versione().getDescription());
	}
	if (blNota == null) {
	    campiErrati.add(noteRilascioDetail.getBl_nota().getDescription());
	}

	Calendar today = GregorianCalendar.getInstance();
	today.set(Calendar.HOUR_OF_DAY, 0);
	today.set(Calendar.MINUTE, 0);
	today.set(Calendar.SECOND, 0);
	today.set(Calendar.MILLISECOND, 0);

	if (noteRilascioDetail.getDt_ini_val().parse() == null) {
	    campiErrati.add(noteRilascioDetail.getDt_ini_val().getDescription());
	} else if (noteRilascioDetail.getDt_ini_val().parse().before(today.getTime())) {
	    // getMessageBox().addError("Errore di compilazione form: data inizio validità
	    // precedente alla data
	    // odierna</br>");
	}
	if (noteRilascioDetail.getDt_fine_val().parse() == null) {
	    // campiErrati.add(noteRilascioDetail.getDt_fine_val().getDescription());
	} else if (noteRilascioDetail.getDt_ini_val().parse() != null && noteRilascioDetail
		.getDt_ini_val().parse().after(noteRilascioDetail.getDt_fine_val().parse())) {
	    getMessageBox().addError(
		    "Errore di compilazione form: data fine validità precedente a data inizio validità</br>");
	}

	if (campiErrati.size() > 0) {
	    getMessageBox().addError("Errore: mancata valorizzazione dei campi obbligatori ");
	    getMessageBox().addError(campiErrati.get(0));
	    // for (int i = 1; i < campiErrati.size(); i++) {
	    // getMessageBox().addError(", " + campiErrati.get(i));
	    // }
	    // getMessageBox().addError("</br>");
	}

	if (noteRilascioDetail.validate(getMessageBox()) && getMessageBox().isEmpty()) {
	    try {
		noteRilascioDetail.copyToBean(noteRilascioRowBean);

		if (getForm().getDettaglioNoteRilascio().getStatus().equals(Status.insert)) {
		    long idNotaRilascio = gestioneNoteRilascioEjb
			    .insertAplNoteRilascio(noteRilascioRowBean);
		    noteRilascioRowBean.setIdNotaRilascio(BigDecimal.valueOf(idNotaRilascio));
		    if (getForm().getListaNoteRilascio().getTable() != null) {
			getForm().getListaNoteRilascio().getTable().last();
			getForm().getListaNoteRilascio().getTable().add(noteRilascioRowBean);
		    } else {
			getForm().getListaNoteRilascio().setTable(new AplNotaRilascioTableBean());
			getForm().getListaNoteRilascio().add(noteRilascioRowBean);
			getForm().getListaNoteRilascio().getTable().setPageSize(10);
		    }
		    // getForm().getListaNoteRilascio().getTable().setCurrentRowIndex(getForm().getListaNoteRilascio().getTable().getCurrentRowIndex());
		    getMessageBox().addMessage(new Message(MessageLevel.INF,
			    "Nota di rilascio inserita con successo"));
		} else if (getForm().getDettaglioNoteRilascio().getStatus().equals(Status.update)) {
		    BigDecimal idNotaRilascio = ((AplNotaRilascioRowBean) getForm()
			    .getListaNoteRilascio().getTable().getCurrentRow()).getIdNotaRilascio();
		    gestioneNoteRilascioEjb.updateAplNoteRilascio(idNotaRilascio,
			    noteRilascioRowBean);
		    // getForm().getListaNoteRilascio().getTable().setCurrentRowIndex(getForm().getListaNoteRilascio().getTable().getCurrentRowIndex());
		    getMessageBox().addMessage(new Message(MessageLevel.INF,
			    "Nota di rilascio modificata con successo"));
		}
		getForm().getDettaglioNoteRilascio().setViewMode();
		getForm().getDettaglioNoteRilascio().setStatus(Status.view);
		getForm().getListaNoteRilascio().setStatus(Status.view);
		getForm().getListaNoteRilascio().setHideDeleteButton(false);

		// Rimuovo l'attributo in sessione della lista delle note di rilascio precedenti
		getSession().removeAttribute("isFromNotaRilascioPrec");

		int currentRow = getForm().getListaNoteRilascio().getTable().getCurrentRowIndex();
		// Ricarico la lista (subito perchÃ¨ se ad esempio scorro i record del dettaglio con
		// le frecce,
		// non trovo ancora le eventuali modifiche apportate)
		// reloadAfterGoBack(Application.Publisher.DETTAGLIO_NOTE_RILASCIO);
		// Mi riposiziono nel record in cui mi trovavo
		getForm().getListaNoteRilascio().getTable().setCurrentRowIndex(currentRow);
		loadDettaglio();
		getMessageBox().setViewMode(ViewMode.plain);
	    } catch (IncoherenceException e) {
		logger.error("Errore nel salvataggio della nota di rilascio: " + e.getMessage(), e);
		getMessageBox().addError(e.getMessage());
	    }
	}

	forwardToPublisher(Application.Publisher.DETTAGLIO_NOTE_RILASCIO);
    }

    @Override
    public void updateListaNoteRilascio() throws EMFError {
	getForm().getDettaglioNoteRilascio().setEditMode();
	getForm().getDettaglioNoteRilascio().setStatus(Status.update);
	getForm().getListaNoteRilascio().setStatus(Status.update);
	getRequest().setAttribute("showNotaRilascio", true);
	getForm().getNoteRilascioPrecedentiList().setUserOperations(false, false, false, true);
    }

    private void redirectToNoteRilascioPage() throws EMFError {
	GestioneNoteRilascioForm form = new GestioneNoteRilascioForm();
	form.getListaNoteRilascio().setFilterValidRecords(
		getForm().getNoteRilascioPrecedentiList().isFilterValidRecords());
	form.getListaNoteRilascio().setUserOperations(true, false, false, false);
	getSession().setAttribute("isFromNotaRilascioPrec", true);
	redirectToPage(Application.Actions.GESTIONE_NOTE_RILASCIO, form,
		form.getListaNoteRilascio().getName(),
		getForm().getNoteRilascioPrecedentiList().getTable(), getNavigationEvent());
    }

    private void redirectToPage(final String action, BaseForm form, String listToPopulate,
	    BaseTableInterface<?> table, String event) throws EMFError {
	((it.eng.spagoLite.form.list.List<SingleValueField<?>>) form.getComponent(listToPopulate))
		.setTable(table);
	redirectToAction(action, "?operation=listNavigationOnClick&navigationEvent=" + event
		+ "&table=" + listToPopulate + "&riga=" + table.getCurrentRowIndex(), form);
    }

    /**
     * Cancella un elemento della lista note di rilascio La modifica ha effetto immediato su DB
     *
     * @throws EMFError errore generico
     */
    @Override
    public void deleteListaNoteRilascio() throws EMFError {
	getMessageBox().clear();
	AplNotaRilascioRowBean noteRilascioRowBean = (AplNotaRilascioRowBean) getForm()
		.getListaNoteRilascio().getTable().getCurrentRow();
	try {
	    gestioneNoteRilascioEjb.deleteNoteRilascio(noteRilascioRowBean);
	    goBack();
	    reloadAfterGoBack("/amministrazioneSistema/ricercaNoteRilascio");
	    // Segnalo l'avvenuta modifica andata a buon fine
	    getMessageBox().addMessage(new Message(MessageLevel.INF,
		    "Cancellazione nota di rilascio effettuata con successo"));
	    getMessageBox().setViewMode(ViewMode.plain);
	} catch (IncoherenceException ex) {
	    logger.error("Errore nella cancellazione della nota di rilascio: " + ex.getMessage(),
		    ex);
	    getMessageBox().addError(ex.getMessage());
	}
    }
}
