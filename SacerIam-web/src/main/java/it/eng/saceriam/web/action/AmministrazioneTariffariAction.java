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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.ejb.EJB;

import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.parer.sacerlog.ejb.SacerLogEjb;
import it.eng.parer.sacerlog.util.LogParam;
import it.eng.parer.sacerlog.util.web.SpagoliteLogUtil;
import it.eng.saceriam.amministrazioneEntiConvenzionati.ejb.EntiConvenzionatiEjb;
import it.eng.saceriam.entity.constraint.ConstOrgTariffa;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.saceriam.slite.gen.Application;
import it.eng.saceriam.slite.gen.action.AmministrazioneTariffariAbstractAction;
import it.eng.saceriam.slite.gen.form.AmministrazioneTariffariForm;
import it.eng.saceriam.slite.gen.form.AmministrazioneTipiAccordoForm;
import it.eng.saceriam.slite.gen.tablebean.OrgScaglioneTariffaRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgScaglioneTariffaTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgScaglioneTariffaTableDescriptor;
import it.eng.saceriam.slite.gen.tablebean.OrgTariffaRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgTariffaTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgTariffarioRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgTariffarioTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgTipoServizioRowBean;
import it.eng.saceriam.util.DateUtil;
import it.eng.saceriam.web.dto.PairNiScaglioni;
import it.eng.saceriam.web.util.ComboGetter;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.ExecutionHistory;
import it.eng.spagoLite.SessionManager;
import it.eng.spagoLite.actions.form.ListAction;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.sorting.SortingRule;
import it.eng.spagoLite.db.oracle.decode.DecodeMap;
import it.eng.spagoLite.form.base.BaseElements.Status;
import it.eng.spagoLite.message.MessageBox.ViewMode;
import it.eng.spagoLite.security.Secure;

/**
 *
 * @author Gilioli_P
 */
@SuppressWarnings({
	"unchecked", "rawtypes" })
public class AmministrazioneTariffariAction extends AmministrazioneTariffariAbstractAction {

    private static final Logger actionLogger = LoggerFactory
	    .getLogger(AmministrazioneTariffariAction.class);

    @EJB(mappedName = "java:app/SacerIam-ejb/EntiConvenzionatiEjb")
    private EntiConvenzionatiEjb entiConvenzionatiEjb;
    @EJB(mappedName = "java:app/SacerIam-ejb/ParamHelper")
    ParamHelper paramHelper;
    @EJB(mappedName = "java:app/sacerlog-ejb/SacerLogEjb")
    private SacerLogEjb sacerLogEjb;

    @Override
    public void initOnClick() throws EMFError {
	// non richiede azioni
    }

    @Override
    public void loadDettaglio() throws EMFError {
	try {
	    if (getNavigationEvent() != null) {
		if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW)
			|| getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)
			|| getNavigationEvent().equals(ListAction.NE_NEXT)
			|| getNavigationEvent().equals(ListAction.NE_PREV)) {
		    if (getTableName().equals(getForm().getListaTariffario().getName())) {
			initTariffarioDetail();
			OrgTariffarioRowBean currentRow = (OrgTariffarioRowBean) getForm()
				.getListaTariffario().getTable().getCurrentRow();
			loadDettaglioTariffario(currentRow.getIdTariffario());
		    } else if (getTableName().equals(getForm().getListaTariffe().getName())) {
			initTariffaDetail();
			OrgTariffaRowBean currentRow = (OrgTariffaRowBean) getForm()
				.getListaTariffe().getTable().getCurrentRow();
			loadDettaglioTariffa(currentRow.getIdTariffa());
		    }
		}
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
    }

    @Override
    public void undoDettaglio() throws EMFError {
	if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_TARIFFARIO)
		&& getForm().getListaTariffario().getStatus().equals(Status.update)) {
	    OrgTariffarioRowBean currentRow = (OrgTariffarioRowBean) getForm().getListaTariffario()
		    .getTable().getCurrentRow();
	    getForm().getTariffarioDetail().copyFromBean(currentRow);
	    getForm().getTariffarioDetail().setViewMode();
	    getForm().getTariffarioDetail().setStatus(Status.view);
	    getForm().getListaTariffario().setStatus(Status.view);
	    forwardToPublisher(Application.Publisher.DETTAGLIO_TARIFFARIO);
	} else {
	    goBack();
	}
    }

    @Override
    public void insertDettaglio() throws EMFError {
	try {
	    if (getTableName().equals(getForm().getListaTariffario().getName())) {
		// Pulisco i campi
		getForm().getTariffarioDetail().clear();
		// Inizializzo le combo
		initTariffarioDetail();
		// Metto in editMode
		getForm().getTariffarioDetail().setEditMode();

		/* Se provengo da Gestione Tipi Accordo, imposto già settato il tipo accordo */
		if (getSession().getAttribute("idTipoAccordo") != null) {
		    getForm().getTariffarioDetail().getId_tipo_accordo()
			    .setValue("" + getSession().getAttribute("idTipoAccordo"));
		    getSession().removeAttribute("idTipoAccordo");
		}

		// Setto tutto in status insert
		getForm().getTariffarioDetail().setStatus(Status.insert);
		getForm().getListaTariffario().setStatus(Status.insert);
		// Rimando alla pagina di dettaglio
		forwardToPublisher(Application.Publisher.DETTAGLIO_TARIFFARIO);
	    } else if (getTableName().equals(getForm().getListaTariffe().getName())) {
		/*
		 * Inserimento di una nuova tariffa, richiamato dalla pagina di dettaglio tariffario
		 * Carica il wizard di inserimento tariffa con i campi in status insert ed edit mode
		 */
		getForm().getTariffaDetail().clear();
		getForm().getListaTariffe().setStatus(Status.insert);
		getForm().getTariffaDetail().setStatus(Status.insert);
		getForm().getTariffaDetail().setEditMode();

		initTariffaDetail();

		// Inizializzo la lista scaglioni della tariffa che sto per inserire
		OrgScaglioneTariffaTableBean scaglioneTariffaTableBean = new OrgScaglioneTariffaTableBean();
		getForm().getListaScaglioni().setTable(scaglioneTariffaTableBean);
		getForm().getListaScaglioni().getTable().setPageSize(10);
		getForm().getListaScaglioni().getTable().first();

		// Imposto le operazioni possibili sulla lista scaglioni, ovvero solo la
		// cancellazione del record
		getForm().getListaScaglioni().setUserOperations(false, false, false, true);

		// Azzero gli attritubi in sessione usati per i controlli di coerenza
		getSession().removeAttribute("scaglioniSet");
		/* Resetta il wizard */
		getForm().getInserimentoWizard().reset();
		forwardToPublisher(Application.Publisher.DETTAGLIO_TARIFFA_WIZARD);
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
    }

    @Override
    public void saveDettaglio() throws EMFError {
	if (getTableName().equals(getForm().getListaTariffario().getName())
		|| getTableName().equals(getForm().getTariffarioDetail().getName())) {
	    saveTariffario();
	}
    }

    @Override
    public void dettaglioOnClick() throws EMFError {
	if (getNavigationEvent() != null) {
	    if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW)
		    || getNavigationEvent().equals(ListAction.NE_NEXT)
		    || getNavigationEvent().equals(ListAction.NE_PREV)) {
		if (getTableName().equals(getForm().getListaTariffario().getName())) {
		    forwardToPublisher(Application.Publisher.DETTAGLIO_TARIFFARIO);
		} else if (getTableName().equals(getForm().getListaTariffe().getName())) {
		    forwardToPublisher(Application.Publisher.DETTAGLIO_TARIFFA);
		}
	    }
	    if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)) {
		if (getTableName().equals(getForm().getListaTariffario().getName())) {
		    forwardToPublisher(Application.Publisher.DETTAGLIO_TARIFFARIO);
		} else if (getTableName().equals(getForm().getListaTariffe().getName())) {
		    forwardToPublisher(Application.Publisher.DETTAGLIO_TARIFFA_WIZARD);
		}
	    }
	}
    }

    @Override
    public void elencoOnClick() throws EMFError {
	if (getLastPublisher().equals("/amministrazioneEntiConvenzionati/dettaglioTariffa")
		|| getLastPublisher()
			.equals("/amministrazioneEntiConvenzionati/dettaglioTariffaWizard")) {
	    if (getSession().getAttribute("isFromTipoServizio") != null) {
		getSession().removeAttribute("isFromTipoServizio");
		goBack();
	    } else {
		goBackTo(Application.Publisher.DETTAGLIO_TARIFFARIO);
	    }
	} else {
	    goBack();
	}
    }

    @Override
    protected String getDefaultPublsherName() {
	return Application.Publisher.RICERCA_TARIFFARI;
    }

    @Override
    public void reloadAfterGoBack(String publisherName) {
	try {
	    if (publisherName.equals(Application.Publisher.RICERCA_TARIFFARI)) {
		int rowIndex;
		int pageSize;
		if (getForm().getListaTariffario().getTable() != null) {
		    rowIndex = getForm().getListaTariffario().getTable().getCurrentRowIndex();
		    pageSize = getForm().getListaTariffario().getTable().getPageSize();
		} else {
		    rowIndex = 0;
		    pageSize = 10;
		}
		String isValido = getForm().getFiltriTariffari().getIs_valido().parse();
		String nomeTariffario = getForm().getFiltriTariffari().getNm_tariffario().parse();
		String cdTipoAccordo = getForm().getFiltriTariffari().getCd_tipo_accordo().parse();
		OrgTariffarioTableBean table = entiConvenzionatiEjb
			.getOrgTariffarioTableBean(isValido, cdTipoAccordo, nomeTariffario);
		getForm().getListaTariffario().setTable(table);
		getForm().getListaTariffario().getTable().setPageSize(pageSize);
		getForm().getListaTariffario().getTable().setCurrentRowIndex(rowIndex);
	    } else if (publisherName.equals(Application.Publisher.DETTAGLIO_TARIFFARIO)) {
		// Rieseguo la query per avere la lista tariffe tenuto conto delle modifiche
		// apportate
		int inizio = getForm().getListaTariffe().getTable().getFirstRowPageIndex();
		int paginaCorrente = getForm().getListaTariffe().getTable().getCurrentPageIndex();
		int pageSize = getForm().getListaTariffe().getTable().getPageSize();
		getForm().getListaTariffe()
			.setTable(entiConvenzionatiEjb.getOrgTariffaByTariffarioTableBean(
				getForm().getTariffarioDetail().getId_tariffario().parse()));
		getForm().getListaTariffe().getTable().setPageSize(pageSize);
		getForm().getListaTariffe().setUserOperations(true, true, true, true);
		// rieseguo la query se necessario
		this.lazyLoadGoPage(getForm().getListaTariffe(), paginaCorrente);
		// ritorno alla pagina
		getForm().getListaTariffe().getTable().setCurrentRowIndex(inizio);
	    }
	} catch (ParerUserError e) {
	    getMessageBox().addError(e.getDescription());
	    forwardToPublisher(getLastPublisher());
	} catch (EMFError e) {
	    actionLogger.error("Errore nel ricaricamento della pagina " + publisherName, e);
	    getMessageBox().addError("Errore nel ricaricamento della pagina " + publisherName);
	    forwardToPublisher(getLastPublisher());
	}
    }

    @Override
    public String getControllerName() {
	return Application.Actions.AMMINISTRAZIONE_TARIFFARI;
    }

    // <editor-fold defaultstate="collapsed" desc="Gestione ricerca tariffari">
    @Secure(action = "Menu.Fatturazione.GestioneTariffario")
    public void ricercaTariffariPage() {
	getUser().getMenu().reset();
	getUser().getMenu().select("Menu.Fatturazione.GestioneTariffario");

	getForm().getFiltriTariffari().reset();
	getForm().getFiltriTariffari().setEditMode();
	getForm().getFiltriTariffari().getIs_valido()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());

	resetGestioneTariffario();

	forwardToPublisher(Application.Publisher.RICERCA_TARIFFARI);
    }

    @Override
    public void ricercaTariffari() throws EMFError {
	if (getForm().getFiltriTariffari().postAndValidate(getRequest(), getMessageBox())) {
	    String isValido = getForm().getFiltriTariffari().getIs_valido().parse();
	    String nomeTariffario = getForm().getFiltriTariffari().getNm_tariffario().parse();
	    String cdTipoAccordo = getForm().getFiltriTariffari().getCd_tipo_accordo().parse();
	    try {
		OrgTariffarioTableBean table = entiConvenzionatiEjb
			.getOrgTariffarioTableBean(isValido, cdTipoAccordo, nomeTariffario);
		getForm().getListaTariffario().setTable(table);
		getForm().getListaTariffario().getTable().setPageSize(10);
		getForm().getListaTariffario().getTable().first();
	    } catch (ParerUserError pue) {
		getMessageBox().addError(pue.getDescription());
	    }
	}
	forwardToPublisher(getLastPublisher());
    }

    private void resetGestioneTariffario() {
	// Azzero la lista risultato ed eventuali parametri in sessione
	getForm().getListaTariffario().setTable(null);
	getForm().getListaTariffe().setUserOperations(true, true, true, true);
	getSession().removeAttribute("isFromTipoServizio");
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Gestione dettaglio tariffario">
    private void initTariffarioDetail() throws ParerUserError {
	getForm().getTariffarioDetail().getId_tipo_accordo()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgTipoAccordoAttiviTableBean(), "id_tipo_accordo",
			"cd_tipo_accordo"));
    }

    private void loadDettaglioTariffario(BigDecimal idTariffario) throws ParerUserError, EMFError {
	OrgTariffarioRowBean detailRow = entiConvenzionatiEjb.getOrgTariffarioRowBean(idTariffario);
	getForm().getTariffarioDetail().copyFromBean(detailRow);

	getForm().getTariffarioDetail().setViewMode();
	getForm().getTariffarioDetail().setStatus(Status.view);
	getForm().getListaTariffario().setStatus(Status.view);

	// Popolamento lista tariffe del tariffario
	getForm().getListaTariffe()
		.setTable(entiConvenzionatiEjb.getOrgTariffaByTariffarioTableBean(idTariffario));
	getForm().getListaTariffe().getTable().setPageSize(10);
	getForm().getListaTariffe().getTable().first();
    }

    @Override
    public void deleteListaTariffario() throws EMFError {
	BaseRowInterface currentRow = getForm().getListaTariffario().getTable().getCurrentRow();
	BigDecimal idTariffario = currentRow.getBigDecimal("id_tariffario");
	int riga = getForm().getListaTariffario().getTable().getCurrentRowIndex();
	// Eseguo giusto un controllo per verificare che io stia prendendo la riga giusta se sono
	// nel dettaglio
	if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_TARIFFARIO)) {
	    if (!idTariffario.equals(getForm().getTariffarioDetail().getId_tariffario().parse())) {
		getMessageBox()
			.addError("Eccezione imprevista nell'eliminazione del Rimborso costi");
	    }
	}

	if (!getMessageBox().hasError() && idTariffario != null) {
	    try {
		/* Controlla che sul tariffario non ci siano degli accordi */
		if (entiConvenzionatiEjb.checkEsistenzaAccordiPerTariffario(idTariffario)) {
		    getMessageBox().addError(
			    "Il Rimborso costi non può essere eliminato: esistono degli accordi definiti su di esso");
		}

		if (!getMessageBox().hasError()) {
		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    if (param.getNomePagina()
			    .equalsIgnoreCase(Application.Publisher.DETTAGLIO_TARIFFARIO)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
		    } else if (param.getNomePagina()
			    .equalsIgnoreCase(Application.Publisher.DETTAGLIO_TIPO_ACCORDO)) {
			ExecutionHistory storia = SessionManager
				.getLastExecutionHistory(getSession());
			AmministrazioneTipiAccordoForm form = (AmministrazioneTipiAccordoForm) storia
				.getForm();
			param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(form,
				form.getListaTariffario()));
		    } else {
			AmministrazioneTariffariForm form = getForm();
			param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(form,
				form.getListaTariffario()));
		    }
		    entiConvenzionatiEjb.deleteOrgTariffario(param, idTariffario);
		    getForm().getListaTariffario().getTable().remove(riga);
		    getMessageBox().addInfo("Rimborso costi eliminato con successo");
		    getMessageBox().setViewMode(ViewMode.plain);
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(
			"Il Rimborso costi non pu\u00F2 essere eliminato: " + ex.getDescription());
	    }
	}
	if (!getMessageBox().hasError() && getSession().getAttribute("daTipoAccordo") != null) {
	    getSession().removeAttribute("daTipoAccordo");
	    goBack();
	} else {
	    if (!getMessageBox().hasError()
		    && getLastPublisher().equals(Application.Publisher.DETTAGLIO_TARIFFARIO)) {
		goBack();
	    } else {
		forwardToPublisher(getLastPublisher());
	    }
	}
    }

    @Override
    public void deleteTariffarioDetail() throws EMFError {
	deleteListaTariffario();
    }

    private void saveTariffario() throws EMFError {
	if (getForm().getTariffarioDetail().postAndValidate(getRequest(), getMessageBox())) {
	    try {
		BigDecimal idTipoAccordo = getForm().getTariffarioDetail().getId_tipo_accordo()
			.parse();
		String nmTariffario = getForm().getTariffarioDetail().getNm_tariffario().parse();
		Date dtIniVal = getForm().getTariffarioDetail().getDt_ini_val().parse();
		Date dtFineVal = getForm().getTariffarioDetail().getDt_fine_val().parse();

		// Se la data fine val è nulla si assume valore 31/12/2444
		if (dtFineVal == null) {
		    SimpleDateFormat df = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH);
		    Calendar cal = Calendar.getInstance();
		    cal.set(2444, Calendar.DECEMBER, 31);
		    dtFineVal = cal.getTime();
		    getForm().getTariffarioDetail().getDt_fine_val().setValue(df.format(dtFineVal));
		}

		if (dtIniVal.after(dtFineVal)) {
		    getMessageBox().addError(
			    "Attenzione: data di inizio validità superiore a data di fine validità");
		}

		if (!getMessageBox().hasError()) {
		    // Salva il tariffario
		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    if (getForm().getListaTariffario().getStatus().equals(Status.insert)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
			Long idTariffario = entiConvenzionatiEjb.saveTariffario(param,
				idTipoAccordo, nmTariffario, dtIniVal, dtFineVal);
			if (idTariffario != null) {
			    getForm().getTariffarioDetail().getId_tariffario()
				    .setValue(idTariffario.toString());
			}
			OrgTariffarioRowBean row = new OrgTariffarioRowBean();
			getForm().getTariffarioDetail().copyToBean(row);
			getForm().getListaTariffario().getTable().last();
			getForm().getListaTariffario().getTable().add(row);
		    } else if (getForm().getListaTariffario().getStatus().equals(Status.update)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
			BigDecimal idTariffario = getForm().getTariffarioDetail().getId_tariffario()
				.parse();
			entiConvenzionatiEjb.saveTariffario(param, idTariffario, idTipoAccordo,
				nmTariffario, dtIniVal, dtFineVal);
		    }
		    BigDecimal idTariffario = getForm().getTariffarioDetail().getId_tariffario()
			    .parse();
		    if (idTariffario != null) {
			loadDettaglioTariffario(idTariffario);
		    }
		    getForm().getTariffarioDetail().setViewMode();
		    getForm().getListaTariffario().setStatus(Status.view);
		    getForm().getTariffarioDetail().setStatus(Status.view);
		    getMessageBox().addInfo("Rimborso costi salvato con successo");
		    getMessageBox().setViewMode(ViewMode.plain);
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
	    }
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_TARIFFARIO);
    }

    @Override
    public void updateListaTariffario() throws EMFError {
	getForm().getTariffarioDetail().setEditMode();
	getForm().getTariffarioDetail().getId_tipo_accordo().setViewMode();
	getForm().getListaTariffario().setStatus(Status.update);
	getForm().getTariffarioDetail().setStatus(Status.update);
    }

    @Override
    public void updateTariffarioDetail() throws EMFError {
	updateListaTariffario();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Gestione dettaglio tariffa">
    private void loadDettaglioTariffa(BigDecimal idTariffa) throws ParerUserError, EMFError {
	OrgTariffaRowBean detailRow = entiConvenzionatiEjb.getOrgTariffaRowBean(idTariffa);
	getForm().getTariffaDetail().copyFromBean(detailRow);

	getForm().getTariffaDetail().setViewMode();
	getForm().getTariffaDetail().setStatus(Status.view);
	getForm().getListaTariffe().setStatus(Status.view);

	// Popolamento lista scaglioni della tariffa
	getForm().getListaScaglioni()
		.setTable(entiConvenzionatiEjb.getOrgScaglioneTariffaTableBean(idTariffa));
	getForm().getListaScaglioni().getTable().setPageSize(10);
	getForm().getListaScaglioni().getTable().first();
    }

    private void initTariffaDetail() throws ParerUserError {
	// Tipo servizio
	getForm().getTariffaDetail().getId_tipo_servizio()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgTipoServizioTableBean(), "id_tipo_servizio",
			"cd_tipo_servizio"));
	// Classe ente convenzionato
	getForm().getTariffaDetail().getId_classe_ente_convenz()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgClasseEnteConvenzTableBean(),
			"id_classe_ente_convenz", "cd_classe_ente_convenz"));
	// Tipo tariffa
	getForm().getTariffaDetail().getTipo_tariffa()
		.setDecodeMap(ComboGetter.getMappaTipoTariffa());

	//
    }

    public void addTariffaToList(BigDecimal idTariffa) {
	try {
	    OrgTariffaRowBean rowBean = entiConvenzionatiEjb.getOrgTariffaRowBean(idTariffa);
	    getForm().getListaTariffe().setTable(new OrgTariffaTableBean());
	    getForm().getListaTariffe().add(rowBean);
	    getForm().getListaTariffe().getTable().setPageSize(10);
	    getForm().getListaTariffe().setUserOperations(true, true, true, true);
	} catch (Exception e) {
	    actionLogger.error(e.getMessage(), e);
	}
    }

    /*
     * Metodo per impostare in 'view mode' il dettaglio tariffa
     */
    private void setDettaglioTariffaToViewMode() {
	getForm().getListaTariffe().setStatus(Status.view);
	getForm().getTariffaDetail().setStatus(Status.view);
	getForm().getTariffaDetail().setViewMode();
	// Imposto la lista scaglioni non modificabile
	getForm().getListaScaglioni().setUserOperations(false, false, false, false);
	getForm().getScaglioniFields().getAggiungiScaglione().setViewMode();
    }

    private boolean checkTipoTariffa() throws EMFError {
	boolean result = true;
	if (getForm().getTariffaDetail().getTipo_tariffa().parse()
		.equals(ConstOrgTariffa.TipoTariffa.VALORE_FISSO.name())
		&& (getForm().getTariffaDetail().getIm_valore_fisso_tariffa().parse() == null)) {
	    getMessageBox().addError(
		    "Il Tipo entità rimborso costi è VALORE_FISSO: è obbligatorio indicare l’importo della tariffa");
	    result = false;
	}
	if (getForm().getTariffaDetail().getTipo_tariffa().parse()
		.equals(ConstOrgTariffa.TipoTariffa.VALORE_UNITARIO_SCAGLIONI_STORAGE.name())
		&& (getForm().getTariffaDetail().getNi_quantita_unit().parse() == null)) {
	    getMessageBox().addError(
		    "Il Tipo entità rimborso costi è VALORE_UNITARIO_SCAGLIONI_STORAGE: è obbligatorio indicare il parametro scaglione tariffa");
	    result = false;
	}
	return result;
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
	return Application.Publisher.DETTAGLIO_TARIFFA_WIZARD;
    }

    @Override
    public boolean inserimentoWizardOnSave() throws EMFError {
	// Eseguo il salvataggio della tariffa
	boolean result = true;
	if (getForm().getTariffaDetail().postAndValidate(getRequest(), getMessageBox())) {
	    actionLogger.info(getClass().getName() + " Inizio salvataggio tariffa");
	    BigDecimal idTariffa = null;
	    try {
		OrgTariffaRowBean tariffaRowBean = new OrgTariffaRowBean();
		getForm().getTariffaDetail().copyToBean(tariffaRowBean);
		/*
		 * Codice aggiuntivo per il logging...
		 */
		LogParam param = SpagoliteLogUtil.getLogParam(
			paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			SpagoliteLogUtil.getPageName(this));
		param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		if (getForm().getListaTariffe().getStatus().equals(Status.insert)) {
		    param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
		    idTariffa = new BigDecimal(entiConvenzionatiEjb.saveTariffa(param,
			    getForm().getTariffarioDetail().getId_tariffario().parse(),
			    tariffaRowBean, (OrgScaglioneTariffaTableBean) getForm()
				    .getListaScaglioni().getTable()));
		    addTariffaToList(idTariffa);
		    getMessageBox().addInfo("Tariffa inserita con successo!");
		} else if (getForm().getListaTariffe().getStatus().equals(Status.update)) {
		    param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
		    idTariffa = ((OrgTariffaRowBean) getForm().getListaTariffe().getTable()
			    .getCurrentRow()).getIdTariffa();
		    entiConvenzionatiEjb.saveTariffa(param,
			    getForm().getTariffarioDetail().getId_tariffario().parse(),
			    tariffaRowBean,
			    (OrgScaglioneTariffaTableBean) getForm().getListaScaglioni().getTable(),
			    getScaglioniDeleteSet());
		    getMessageBox().addInfo("Tariffa modificata con successo!");
		}

		if (!getMessageBox().hasError()) {
		    getSession().removeAttribute("scaglioniDeleteList");
		    getMessageBox().setViewMode(ViewMode.plain);
		    loadDettaglioTariffa(idTariffa);
		    getForm().getListaTariffe().setStatus(Status.view);
		    getForm().getListaScaglioni().setStatus(Status.view);
		    getForm().getTariffaDetail().setStatus(Status.view);
		    getForm().getTariffaDetail().setViewMode();
		    actionLogger.info(getClass().getName() + " Fine salvataggio tariffa");
		    forwardToPublisher(Application.Publisher.DETTAGLIO_TARIFFA);
		    // No go back to perchè dopo aver inserito non ho ancora un dettaglio tariffa,
		    // provenivo da
		    // dettaglio tariffario e poi dettaglio tariffa wizard!
		}
	    } catch (ParerUserError ex) {
		result = false;
		getMessageBox().addError(ex.getDescription());
	    }
	}
	return result;
    }

    @Override
    public void inserimentoWizardOnCancel() throws EMFError {
	getSession().removeAttribute("scaglioniSet");
	getSession().removeAttribute("scaglioniDeleteSet");
	if (getForm().getListaTariffe().getStatus().equals(Status.insert)) {
	    setDettaglioTariffaToViewMode();
	    goBackTo(Application.Publisher.DETTAGLIO_TARIFFARIO);
	} else {
	    setTableName(getForm().getListaTariffe().getName());
	    OrgTariffaRowBean currentRow = (OrgTariffaRowBean) getForm().getListaTariffe()
		    .getTable().getCurrentRow();
	    getForm().getTariffaDetail().copyFromBean(currentRow);
	    try {
		getForm().getListaScaglioni().setTable(entiConvenzionatiEjb
			.getOrgScaglioneTariffaTableBean(currentRow.getIdTariffa()));
		getForm().getListaScaglioni().getTable().setPageSize(10);
		getForm().getListaScaglioni().getTable().first();
	    } catch (ParerUserError ex) {
		getMessageBox().addError("Errore durante il recupero degli scaglioni");
		actionLogger.error("Errore durante il recupero degli scaglioni");
	    }
	    setDettaglioTariffaToViewMode();
	    forwardToPublisher(Application.Publisher.DETTAGLIO_TARIFFA);
	}
    }

    /**
     * Primo step del wizard: - Carica il dettaglio tariffa in edit mode - Visualizza il bottone per
     * inserire dati nella lista
     *
     * @throws EMFError errore generico
     */
    @Override
    public void inserimentoWizardStep1OnEnter() throws EMFError {
	getSession().removeAttribute("scaglioniSet");
	getForm().getTariffaDetail().setEditMode();
	forwardToPublisher(Application.Publisher.DETTAGLIO_TARIFFA_WIZARD);
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
    public boolean inserimentoWizardStep1OnExit() throws EMFError {
	forwardToPublisher(Application.Publisher.DETTAGLIO_TARIFFA_WIZARD);
	boolean result = true;
	getForm().getTariffaDetail().post(getRequest());
	if (getForm().getTariffaDetail().validate(getMessageBox())) {
	    result = checkTipoTariffa();

	    /*
	     * Se il tipo tariffa è VALORE_FISSO il sistema persiste la tariffa senza presentare la
	     * seconda pagina del wizard
	     */
	    if (getForm().getTariffaDetail().getTipo_tariffa().parse()
		    .equals(ConstOrgTariffa.TipoTariffa.VALORE_FISSO.name())) {
		getForm().getInserimentoWizard().getStep2().setHidden(true);
		getForm().getInserimentoWizard().getStep3().setHidden(false);
		/* Elimino eventuali scaglioni presenti */
		Set<BigDecimal> scaglioniDeleteSet = getScaglioniDeleteSet();
		for (OrgScaglioneTariffaRowBean scaglioneTariffaRowBean : (OrgScaglioneTariffaTableBean) getForm()
			.getListaScaglioni().getTable()) {
		    if (scaglioneTariffaRowBean.getIdScaglioneTariffa() != null) {
			scaglioniDeleteSet.add(scaglioneTariffaRowBean.getIdScaglioneTariffa());
			getSession().setAttribute("scaglioniDeleteSet", scaglioniDeleteSet);
		    }
		}
		getForm().getListaScaglioni().getTable().removeAll();
	    } else {
		getForm().getInserimentoWizard().getStep2().setHidden(false);
		getForm().getInserimentoWizard().getStep3().setHidden(true);
	    }
	} else {
	    result = false;
	}
	return result;
    }

    @Override
    public void inserimentoWizardStep2OnEnter() throws EMFError {
	// Rendo visibile il bottone di aggiunta scaglione, i campi da cui scegliere i valori
	// ed inizializzo il "set" per i controlli di coerenza
	getForm().getScaglioniFields().clear();
	getForm().getScaglioniFields().setEditMode();
	populateScaglioniSet(
		(OrgScaglioneTariffaTableBean) getForm().getListaScaglioni().getTable());
	getForm().getTariffaDetail().setViewMode();
	forwardToPublisher(Application.Publisher.DETTAGLIO_TARIFFA_WIZARD);
    }

    @Override
    public boolean inserimentoWizardStep2OnExit() throws EMFError {
	forwardToPublisher(Application.Publisher.DETTAGLIO_TARIFFA_WIZARD);
	// SALVA LA TARIFFA
	return true;
    }

    @Override
    public void inserimentoWizardStep3OnEnter() throws EMFError {
	getForm().getTariffaDetail().setViewMode();
	forwardToPublisher(Application.Publisher.DETTAGLIO_TARIFFA_WIZARD);
    }

    @Override
    public boolean inserimentoWizardStep3OnExit() throws EMFError {
	forwardToPublisher(Application.Publisher.DETTAGLIO_TARIFFA_WIZARD);
	return true;
    }

    @Override
    public void deleteListaTariffe() throws EMFError {
	OrgTariffaRowBean currentRow = ((OrgTariffaTableBean) getForm().getListaTariffe()
		.getTable()).getCurrentRow();
	BigDecimal idTariffa = currentRow.getIdTariffa();
	int riga = getForm().getListaTariffe().getTable().getCurrentRowIndex();
	// Eseguo giusto un controllo per verificare che io stia prendendo la riga giusta se sono
	// nel dettaglio
	if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_TARIFFA)) {
	    if (!idTariffa.equals(getForm().getTariffaDetail().getId_tariffa().parse())) {
		getMessageBox().addError("Eccezione imprevista nell'eliminazione della tariffa");
	    }
	}

	if (!getMessageBox().hasError() && idTariffa != null) {
	    try {
		/* Controlla che non ci siano servizi erogati per quella tariffa */
		if (entiConvenzionatiEjb.checkEsistenzaServiziErogatiPerTariffa(idTariffa)) {
		    getMessageBox().addError(
			    "La tariffa non può essere eliminata: esistono dei servizi erogati associati ad essa");
		}

		if (!getMessageBox().hasError()) {
		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    if (param.getNomePagina()
			    .equalsIgnoreCase(Application.Publisher.DETTAGLIO_TARIFFA)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
		    } else {
			AmministrazioneTariffariForm form = getForm();
			param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(form,
				form.getListaTariffe()));
		    }
		    entiConvenzionatiEjb.deleteOrgTariffa(param, idTariffa);
		    getForm().getListaTariffe().getTable().remove(riga);
		    getMessageBox().addInfo("Tariffa eliminata con successo");
		    getMessageBox().setViewMode(ViewMode.plain);
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(
			"La tariffa non pu\u00F2 essere eliminata: " + ex.getDescription());
	    }
	}
	if (!getMessageBox().hasError()
		&& getLastPublisher().equals(Application.Publisher.DETTAGLIO_TARIFFA)) {
	    goBackTo(Application.Publisher.DETTAGLIO_TARIFFARIO);
	} else {
	    forwardToPublisher(getLastPublisher());
	}
    }

    @Override
    public void updateListaTariffe() throws EMFError {
	/* Salvo l'informazione del fatto che sono in UPDATE */
	getForm().getListaTariffe().setStatus(Status.update);
	getForm().getTariffaDetail().setStatus(Status.update);
	getForm().getTariffaDetail().setEditMode();
	// Imposto i campi della lista come eliminabili
	getForm().getListaScaglioni().setUserOperations(false, false, false, true);
	/* Resetta il wizard */
	getForm().getInserimentoWizard().reset();
    }

    @Override
    public void updateTariffaDetail() throws EMFError {
	updateListaTariffe();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Gestione scaglioni">
    /*
     * Metodo che mantiene in sessione il set di id degli scaglioni che sono stati selezionati per
     * l'eliminazione dalla lista.
     *
     */
    private Set<BigDecimal> getScaglioniDeleteSet() {
	if (getSession().getAttribute("scaglioniDeleteSet") == null) {
	    getSession().setAttribute("scaglioniDeleteSet", new HashSet<BigDecimal>());
	}
	return (Set<BigDecimal>) getSession().getAttribute("scaglioniDeleteSet");
    }

    /*
     * Inserisce in sessione l'insieme degli scaglioni della tariffa
     */
    private void populateScaglioniSet(OrgScaglioneTariffaTableBean scaglioneTariffaTableBean) {
	Set<PairNiScaglioni> scaglioniSet = new HashSet<>();
	for (OrgScaglioneTariffaRowBean scaglioneTariffaRowBean : scaglioneTariffaTableBean) {
	    scaglioniSet.add(new PairNiScaglioni(scaglioneTariffaRowBean.getNiIniScaglione(),
		    scaglioneTariffaRowBean.getNiFineScaglione()));
	}
	getSession().setAttribute("scaglioniSet", scaglioniSet);
    }

    /*
     * Getter per mantenere un set di scaglioni, utile per i controlli di coerenza
     *
     * @return il set di scaglioni
     */
    private Set<PairNiScaglioni> getScaglioniSet() {
	if (getSession().getAttribute("scaglioniSet") == null) {
	    getSession().setAttribute("scaglioniSet", new HashSet<String>());
	}
	return (Set<PairNiScaglioni>) getSession().getAttribute("scaglioniSet");
    }

    @Override
    public void aggiungiScaglione() throws EMFError {
	getForm().getScaglioniFields().postAndValidate(getRequest(), getMessageBox());

	if (!getMessageBox().hasError()) {
	    BigDecimal importoScaglione = getForm().getScaglioniFields().getIm_scaglione().parse();
	    BigDecimal niIniScaglione = getForm().getScaglioniFields().getNi_ini_scaglione()
		    .parse();
	    BigDecimal niFineScaglione = getForm().getScaglioniFields().getNi_fine_scaglione()
		    .parse();
	    PairNiScaglioni niIniFineScaglione = new PairNiScaglioni(niIniScaglione,
		    niFineScaglione);
	    if (niIniScaglione.compareTo(niFineScaglione) >= 0) {
		getMessageBox().addError(
			"Numero inizio scaglio maggiore o uguale a numero fine scaglione");
	    } else if (getScaglioniSet().contains(niIniFineScaglione)) {
		getMessageBox().addError("Scaglione già presente nella lista");
	    } else if (checkSovrapposizioneScaglione(getScaglioniSet(), niIniFineScaglione)) {
		getMessageBox().addError("Lo scaglione si sovrappone ad altri già presenti");
	    } else {
		// Creo la nuova riga
		Set<PairNiScaglioni> scaglioniSet = getScaglioniSet();
		scaglioniSet.add(niIniFineScaglione);
		getSession().setAttribute("scaglioniSet", scaglioniSet);
		OrgScaglioneTariffaRowBean scaglioneRow = new OrgScaglioneTariffaRowBean();
		scaglioneRow.setNiIniScaglione(niIniScaglione);
		scaglioneRow.setNiFineScaglione(niFineScaglione);
		scaglioneRow.setImScaglione(importoScaglione);
		getForm().getListaScaglioni().getTable().add(scaglioneRow);
		getForm().getListaScaglioni().getTable().addSortingRule(
			OrgScaglioneTariffaTableDescriptor.COL_NI_INI_SCAGLIONE, SortingRule.ASC);
		getForm().getListaScaglioni().getTable().sort();
	    }
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_TARIFFA_WIZARD);
    }

    private boolean checkSovrapposizioneScaglione(Set<PairNiScaglioni> scaglioniSet,
	    PairNiScaglioni niIniFineScaglione) {
	// Scorro gli scaglioni
	Iterator it = scaglioniSet.iterator();
	while (it.hasNext()) {
	    PairNiScaglioni scaglioneInLista = (PairNiScaglioni) it.next();
	    if ((((BigDecimal) niIniFineScaglione.getIni())
		    .compareTo(((BigDecimal) scaglioneInLista.getIni())) >= 0
		    && ((BigDecimal) niIniFineScaglione.getIni())
			    .compareTo(((BigDecimal) scaglioneInLista.getFine())) <= 0)
		    || (((BigDecimal) niIniFineScaglione.getFine())
			    .compareTo(((BigDecimal) scaglioneInLista.getIni())) >= 0
			    && ((BigDecimal) niIniFineScaglione.getFine())
				    .compareTo(((BigDecimal) scaglioneInLista.getFine())) <= 0)) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public void deleteListaScaglioni() throws EMFError {
	OrgScaglioneTariffaRowBean currentRow = (OrgScaglioneTariffaRowBean) getForm()
		.getListaScaglioni().getTable().getCurrentRow();
	int rowIndex = getForm().getListaScaglioni().getTable().getCurrentRowIndex();
	Set<BigDecimal> scaglioniDeleteSet = getScaglioniDeleteSet();
	if (currentRow.getIdScaglioneTariffa() != null) {
	    scaglioniDeleteSet.add(currentRow.getIdScaglioneTariffa());
	    getSession().setAttribute("scaglioniDeleteSet", scaglioniDeleteSet);
	}
	getForm().getListaScaglioni().getTable().remove(rowIndex);
	Set<PairNiScaglioni> scaglioniSet = getScaglioniSet();
	scaglioniSet.remove(new PairNiScaglioni(currentRow.getNiIniScaglione(),
		currentRow.getNiFineScaglione()));
	getSession().setAttribute("scaglioniSet", scaglioniSet);
	forwardToPublisher(Application.Publisher.DETTAGLIO_TARIFFA_WIZARD);
    }
    // </editor-fold>

    @Override
    public JSONObject triggerTariffaDetailId_tipo_servizioOnTrigger() throws EMFError {
	getForm().getTariffaDetail().post(getRequest());
	BigDecimal idTipoServizio = getForm().getTariffaDetail().getId_tipo_servizio().parse();
	OrgTipoServizioRowBean tipoServizioRowBean = entiConvenzionatiEjb
		.getOrgTipoServizioRowBean(idTipoServizio);
	if (tipoServizioRowBean.getCdTipoServizio() != null) {
	    if (tipoServizioRowBean.getTiClasseTipoServizio().equals("ATTIVAZIONE_TIPO_UD")) {
		getForm().getTariffaDetail().getTipo_tariffa()
			.setDecodeMap(ComboGetter.getMappaTipoTariffaSoloValoreFisso());
		getForm().getTariffaDetail().getId_classe_ente_convenz()
			.setDecodeMap(new DecodeMap());
	    } else {
		getForm().getTariffaDetail().getTipo_tariffa()
			.setDecodeMap(ComboGetter.getMappaTipoTariffa());
		try {
		    // Classe ente convenzionato
		    getForm().getTariffaDetail().getId_classe_ente_convenz()
			    .setDecodeMap(DecodeMap.Factory.newInstance(
				    entiConvenzionatiEjb.getOrgClasseEnteConvenzTableBean(),
				    "id_classe_ente_convenz", "cd_classe_ente_convenz"));
		} catch (ParerUserError ex) {
		    // TODO
		}
	    }
	}
	return getForm().getTariffaDetail().asJSON();
    }
}
