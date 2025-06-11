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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import it.eng.parer.sacerlog.ejb.SacerLogEjb;
import it.eng.parer.sacerlog.slite.gen.form.GestioneLogEventiForm;
import it.eng.parer.sacerlog.util.LogParam;
import it.eng.parer.sacerlog.util.web.SpagoliteLogUtil;
import it.eng.saceriam.amministrazioneEntiConvenzionati.ejb.EntiConvenzionatiEjb;
import it.eng.saceriam.common.Constants;
import it.eng.saceriam.entity.constraint.ConstIamParamApplic;
import it.eng.saceriam.entity.constraint.ConstOrgStatoFatturaEnte;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.gestioneFatturazioneServizi.dto.FatturaBean;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.saceriam.slite.gen.Application;
import it.eng.saceriam.slite.gen.action.GestioneFatturazioneServiziAbstractAction;
import it.eng.saceriam.slite.gen.form.GestioneFatturazioneServiziForm;
import it.eng.saceriam.slite.gen.form.GestioneFatturazioneServiziForm.ListaEstraiRigheFatture;
import it.eng.saceriam.slite.gen.tablebean.OrgPagamFatturaEnteRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgPagamFatturaEnteTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgSollecitoFatturaEnteRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgSollecitoFatturaEnteTableBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVCalcTotFattRowBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVLisServFatturaRowBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVLisServFatturaTableBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicFattureRowBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicFattureTableBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVVisFatturaRowBean;
import it.eng.saceriam.util.SacerLogConstants;
import it.eng.saceriam.web.helper.AmministrazioneUtentiHelper;
import it.eng.saceriam.web.util.ComboGetter;
import it.eng.saceriam.web.util.NavigatorDetailBean;
import it.eng.saceriam.web.util.NavigatorDetailBeanManager;
import it.eng.saceriam.web.util.WebConstants;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.SessionManager;
import it.eng.spagoLite.actions.form.ListAction;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.BaseTableInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.base.table.BaseTable;
import it.eng.spagoLite.db.oracle.decode.DecodeMap;
import it.eng.spagoLite.form.base.BaseElements.Status;
import it.eng.spagoLite.form.fields.SingleValueField;
import it.eng.spagoLite.message.MessageBox;
import it.eng.spagoLite.message.MessageBox.ViewMode;
import it.eng.spagoLite.security.Secure;

/**
 *
 * @author Gilioli_P
 */
@SuppressWarnings({
	"unchecked", "rawtypes" })
public class GestioneFatturazioneServiziAction extends GestioneFatturazioneServiziAbstractAction {

    private static final Logger actionLogger = LoggerFactory
	    .getLogger(GestioneFatturazioneServiziAction.class);

    @EJB(mappedName = "java:app/SacerIam-ejb/EntiConvenzionatiEjb")
    private EntiConvenzionatiEjb entiConvenzionatiEjb;
    @EJB(mappedName = "java:app/SacerIam-ejb/ParamHelper")
    private ParamHelper paramHelper;
    @EJB(mappedName = "java:app/sacerlog-ejb/SacerLogEjb")
    private SacerLogEjb sacerLogEjb;
    @EJB(mappedName = "java:app/SacerIam-ejb/AmministrazioneUtentiHelper")
    private AmministrazioneUtentiHelper amministrazioneUtentiHelper;

    @Override
    public void initOnClick() throws EMFError {
    }

    @Override
    public void loadDettaglio() throws EMFError {
	try {
	    if (getNavigationEvent() != null) {
		if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW)
			|| getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)
			|| getNavigationEvent().equals(ListAction.NE_NEXT)
			|| getNavigationEvent().equals(ListAction.NE_PREV)) {
		    if (getTableName().equals(getForm().getListaFatture().getName())) {
			// Al primo giro azzero lo stack
			NavigatorDetailBeanManager.resetNavigatorDetailStack();
			loadCurrentDettaglioFatturaFromList(getForm().getListaFatture().getName());
		    } else if (getTableName()
			    .equals(getForm().getListaServiziFatturati().getName())) {
			initServizioFatturatoDetail();
			BaseRow servizioFatturatoBean = (BaseRow) getForm()
				.getListaServiziFatturati().getTable().getCurrentRow();
			loadDettaglioServizioFatturato(
				servizioFatturatoBean.getBigDecimal("id_servizio_fattura"));
		    } else if (getTableName().equals(getForm().getListaIncassi().getName())) {
			initIncassoSectionsOpened();
			OrgPagamFatturaEnteRowBean pagamFatturaEnteRowBean = (OrgPagamFatturaEnteRowBean) getForm()
				.getListaIncassi().getTable().getCurrentRow();
			loadDettaglioIncasso(pagamFatturaEnteRowBean.getIdPagamFatturaEnte());
		    } // Se ho cliccato su un elemento della lista fatture riemesse o sulla lista
		      // "di appoggio" delle
		      // fatture riemesse
		    else if (getTableName().equals(getForm().getListaFattureRiemesse().getName())
			    || getTableName()
				    .equals(getForm().getListaDetailFattureRiemesse().getName())) {
			actionLogger.info(
				"Caricamento dettaglio di una fattura PRECEDENTE, presa da una lista di fatture riemesse");

			// Setto l'informazione in sessione che sto trattando la lista delle fatture
			// riemesse
			getSession().setAttribute("navTableFatture",
				getForm().getListaFattureRiemesse().getName());
			BaseRow row;

			// Devo recuperare il record da trattare: cerco di capire se prenderlo dalla
			// lista originale o
			// da quella di appoggio
			if (getTableName().equals(getForm().getListaFattureRiemesse().getName())) {
			    getForm().getListaDetailFattureRiemesse()
				    .setTable(getForm().getListaFattureRiemesse().getTable());
			    row = (BaseRow) getForm().getListaFattureRiemesse().getTable()
				    .getCurrentRow();
			} else {
			    row = (BaseRow) getForm().getListaDetailFattureRiemesse().getTable()
				    .getCurrentRow();
			}

			// Il livello di annidamento cambia in base a quello del dettaglio
			// visualizzato in precedenza
			BigDecimal level = getForm().getFatturaDetail().getLevel_fatture().parse();
			loadDettaglioFattura(row.getBigDecimal("id_fattura_ente"));

			// Se sto scorrendo orizzontalmente
			if (getNavigationEvent().equals(ListAction.NE_NEXT)
				|| getNavigationEvent().equals(ListAction.NE_PREV)) {
			    if (NavigatorDetailBeanManager.getLastNavigatorDetailStack() != null
				    && level.intValue() == NavigatorDetailBeanManager
					    .getLastNavigatorDetailStack().getLevel()) {
				NavigatorDetailBeanManager.popNavigatorDetailStack();
				getForm().getFatturaDetail().getLevel_fatture()
					.setValue(String.valueOf(level));
			    } else {
				int nextLevel = NavigatorDetailBeanManager
					.getLastNavigatorDetailStack().getLevel() + 1;
				getForm().getFatturaDetail().getLevel_fatture()
					.setValue(String.valueOf(nextLevel));
			    }
			} else {
			    int nextLevel = NavigatorDetailBeanManager.getLastNavigatorDetailStack()
				    .getLevel() + 1;
			    getForm().getFatturaDetail().getLevel_fatture()
				    .setValue(String.valueOf(nextLevel));
			}

			if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW)
				|| getNavigationEvent().equals(ListAction.NE_NEXT)
				|| getNavigationEvent().equals(ListAction.NE_PREV)
				|| (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)
					&& NavigatorDetailBeanManager.getLastNavigatorDetailStack()
						.getIdObject().compareTo(row
							.getBigDecimal("id_fattura_ente")) != 0)) {
			    // Mi salvo questo parametro in request che mi servirà nel caso di
			    // update non da dettaglio
			    // ma cliccando sulla lista fatture riemesse per consentire quindi di
			    // inserire una nuova
			    // pagina
			    // di dettaglio nello stack di navigazione personalizzato
			    getRequest().setAttribute("newFatturaPage", true);
			    NavigatorDetailBeanManager.pushNavigatorDetailStack(
				    row.getBigDecimal("id_fattura_ente"),
				    getForm().getListaFattureRiemesse().getName(),
				    getForm().getListaDetailFattureRiemesse().getTable(),
				    getForm().getFatturaDetail().getLevel_fatture().parse()
					    .intValue());
			}
		    } else if (getTableName().equals(getForm().getListaSolleciti().getName())) {
			initSollecitoDetail();
			OrgSollecitoFatturaEnteRowBean sollecitoFatturaEnteRowBean = (OrgSollecitoFatturaEnteRowBean) getForm()
				.getListaSolleciti().getTable().getCurrentRow();
			loadDettaglioSollecito(
				sollecitoFatturaEnteRowBean.getIdSollecitoFatturaEnte());
		    }
		}
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
    }

    @Override
    public void undoDettaglio() throws EMFError {
	try {
	    if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_FATTURA)) {
		getForm().getListaFatture().setStatus(Status.view);
		getForm().getListaFattureRiemesse().setStatus(Status.view);
		getForm().getListaDetailFattureRiemesse().setStatus(Status.view);
		getForm().getFatturaDetail().setViewMode();
		loadDettaglioFattura(getForm().getFatturaDetail().getId_fattura_ente().parse());
	    } else {
		goBack();
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
    }

    @Override
    public void insertDettaglio() throws EMFError {
	try {
	    if (getTableName().equals(getForm().getListaFatture().getName())) {
		initFatturaSectionsOpened();
	    } else if (getTableName().equals(getForm().getListaIncassi().getName())) {
		getForm().getIncassoDetail().clear();
		getForm().getIncassoDetail().setEditMode();
		getForm().getFatturaDetail().setViewMode();
		initIncassoSectionsOpened();
		getForm().getIncassoDetail().setStatus(Status.insert);
		getForm().getListaIncassi().setStatus(Status.insert);
		forwardToPublisher(Application.Publisher.DETTAGLIO_INCASSO);
	    } else if (getTableName().equals(getForm().getListaSolleciti().getName())) {
		getForm().getSollecitoDetail().clear();
		getForm().getSollecitoDetail().setEditMode();
		getForm().getFatturaDetail().setViewMode();
		initSollecitoDetail();
		initSollecitoSectionsOpened();
		getForm().getSollecitoDetail().setStatus(Status.insert);
		getForm().getListaSolleciti().setStatus(Status.insert);
		forwardToPublisher(Application.Publisher.DETTAGLIO_SOLLECITO);
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
    }

    @Override
    public void saveDettaglio() throws EMFError {
	if (getTableName().equals(getForm().getListaFatture().getName())
		|| getTableName().equals(getForm().getFatturaDetail().getName())
		|| getTableName().equals(getForm().getListaDetailFattureRiemesse().getName())) {
	    saveFattura();
	} else if (getTableName().equals(getForm().getListaIncassi().getName())
		|| getTableName().equals(getForm().getIncassoDetail().getName())) {
	    saveIncasso();
	} else if (getTableName().equals(getForm().getListaServiziFatturati().getName())
		|| getTableName().equals(getForm().getServizioFatturatoDetail().getName())) {
	    saveServizioFatturato();
	} else if (getTableName().equals(getForm().getListaSolleciti().getName())
		|| getTableName().equals(getForm().getSollecitoDetail().getName())) {
	    saveSollecito();
	}
    }

    @Override
    public void dettaglioOnClick() throws EMFError {
	if (getNavigationEvent() != null) {
	    if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW)
		    || getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)
		    || getNavigationEvent().equals(ListAction.NE_NEXT)
		    || getNavigationEvent().equals(ListAction.NE_PREV)) {
		if (getTableName().equals(getForm().getListaFatture().getName())
			&& !getMessageBox().hasError()) {
		    forwardToPublisher(Application.Publisher.DETTAGLIO_FATTURA);
		} else if (getTableName().equals(getForm().getListaFattureRiemesse().getName())
			|| getTableName()
				.equals(getForm().getListaDetailFattureRiemesse().getName())) {
		    boolean newPage = getRequest().getAttribute("newFatturaPage") != null;
		    if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW)
			    || (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)
				    && newPage)) {
			SessionManager.addPrevExecutionToHistory(getSession(), false, true);
		    }
		    forwardToPublisher(Application.Publisher.DETTAGLIO_FATTURA);
		} else if (getTableName().equals(getForm().getListaServiziFatturati().getName())
			&& !getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)) {
		    forwardToPublisher(Application.Publisher.DETTAGLIO_SERVIZIO_FATTURATO);
		} else if (getTableName().equals(getForm().getListaIncassi().getName())) {
		    forwardToPublisher(Application.Publisher.DETTAGLIO_INCASSO);
		} else if (getTableName().equals(getForm().getListaSolleciti().getName())) {
		    forwardToPublisher(Application.Publisher.DETTAGLIO_SOLLECITO);
		}
	    }
	}
    }

    @Override
    public void elencoOnClick() throws EMFError {
	goBack();
    }

    @Override
    protected String getDefaultPublsherName() {
	return Application.Publisher.RICERCA_FATTURE;
    }

    @Override
    public void reloadAfterGoBack(String publisherName) {
	try {
	    if (publisherName.equals(Application.Publisher.RICERCA_FATTURE)) {
		int rowIndex;
		int pageSize;
		if (getForm().getListaFatture().getTable() != null) {
		    rowIndex = getForm().getListaFatture().getTable().getCurrentRowIndex();
		    pageSize = getForm().getListaFatture().getTable().getPageSize();
		} else {
		    rowIndex = 0;
		    pageSize = 10;
		}
		String nmEnteConvenz = getForm().getFiltriFattura().getNm_ente_convenz().parse();
		String cdClienteSap = getForm().getFiltriFattura().getCd_cliente_sap().parse();
		BigDecimal idTipoAccordo = getForm().getFiltriFattura().getId_tipo_accordo()
			.parse();
		BigDecimal idTipoServizio = getForm().getFiltriFattura().getId_tipo_servizio()
			.parse();
		BigDecimal aaFatturaDa = getForm().getFiltriFattura().getAa_fattura_da().parse();
		BigDecimal aaFatturaA = getForm().getFiltriFattura().getAa_fattura_a().parse();
		BigDecimal pgFatturaEnteDa = getForm().getFiltriFattura().getPg_fattura_ente_da()
			.parse();
		BigDecimal pgFatturaEnteA = getForm().getFiltriFattura().getPg_fattura_ente_a()
			.parse();
		String cdFattura = getForm().getFiltriFattura().getCd_fattura().parse();
		String cdRegistroEmisFattura = getForm().getFiltriFattura()
			.getCd_registro_emis_fattura().parse();
		BigDecimal aaEmissFattura = getForm().getFiltriFattura().getAa_emiss_fattura()
			.parse();
		String cdEmisFattura = getForm().getFiltriFattura().getCd_emis_fattura().parse();
		BigDecimal pgFatturaEmis = getForm().getFiltriFattura().getPg_fattura_emis()
			.parse();
		Date dtEmisFatturaDa = getForm().getFiltriFattura().getDt_emis_fattura_da().parse();
		Date dtEmisFatturaA = getForm().getFiltriFattura().getDt_emis_fattura_a().parse();
		Set<String> tiStatoFatturaEnte = getForm().getFiltriFattura()
			.getTi_stato_fattura_ente().getDecodedValues();
		try {
		    OrgVRicFattureTableBean table = entiConvenzionatiEjb.getOrgVRicFattureTableBean(
			    nmEnteConvenz, cdClienteSap, idTipoAccordo, idTipoServizio, aaFatturaDa,
			    aaFatturaA, pgFatturaEnteDa, pgFatturaEnteA, cdFattura,
			    cdRegistroEmisFattura, aaEmissFattura, cdEmisFattura, pgFatturaEmis,
			    dtEmisFatturaDa, dtEmisFatturaA, tiStatoFatturaEnte);
		    getForm().getListaFatture().setTable(table);
		    getForm().getListaFatture().getTable().setPageSize(pageSize);
		    getForm().getListaFatture().getTable().setCurrentRowIndex(rowIndex);
		} catch (ParerUserError pue) {
		    getMessageBox().addError(pue.getDescription());
		}
	    } else if (publisherName.equals(Application.Publisher.DETTAGLIO_FATTURA)) {
		BigDecimal idFatturaEnte;
		int level = 1;
		if (getLastPublisher().equals(publisherName)) {
		    // Se anche il last publisher = FATTURA_DETAIL, bisogna caricare il dettaglio di
		    // una fattura
		    // diversa.
		    // Recupero l'ultimo elemento dalla pila
		    NavigatorDetailBean detailBean = NavigatorDetailBeanManager
			    .popNavigatorDetailStack();
		    // Dall'oggetto NavigatorDetailBean mi ricavo l'id fattura
		    idFatturaEnte = detailBean != null ? detailBean.getIdObject() : null;

		    if (idFatturaEnte != null && idFatturaEnte
			    .equals(getForm().getFatturaDetail().getId_fattura_ente().parse())) {
			// Se l'idFattura ottenuto è l'ultimo dello stack, non devo ricaricare
			// quello ma il precedente,
			// quindi faccio un'altra pop
			detailBean = NavigatorDetailBeanManager.getLastNavigatorDetailStack();
			idFatturaEnte = detailBean != null ? detailBean.getIdObject() : null;
		    } else if (idFatturaEnte == null) {
			// Nel caso lo stack sia vuoto, non dovrebbe succedere mai.
			idFatturaEnte = getForm().getFatturaDetail().getId_fattura_ente().parse();
		    }

		    // Recupero i valori dell'oggetto
		    if (detailBean != null) {
			// Salvo in sessione l'attributo riferito alla sourceList
			getSession().setAttribute("navTableFatture", detailBean.getSourceList());
			// Setto la lista corretta, ListaFatture o ListaFattureRiemesse
			if (detailBean.getSourceList()
				.equals(getForm().getListaFattureRiemesse().getName())) {
			    getForm().getListaFattureRiemesse()
				    .setTable(detailBean.getSourceTable());
			} else {
			    ((it.eng.spagoLite.form.list.List<SingleValueField<?>>) getForm()
				    .getComponent(detailBean.getSourceList()))
				    .setTable(detailBean.getSourceTable());
			}
			// Recupero il livello di annidamento
			level = detailBean.getLevel();
		    }
		} else {
		    idFatturaEnte = getForm().getFatturaDetail().getId_fattura_ente().parse();
		    level = getForm().getFatturaDetail().getLevel_fatture().parse() != null
			    ? getForm().getFatturaDetail().getLevel_fatture().parse().intValue()
			    : 1;
		}

		// Carico il dettaglio fattura
		loadDettaglioFattura(idFatturaEnte);

		// Mi salvo il livello di annidamento attuale
		getForm().getFatturaDetail().getLevel_fatture().setValue(String.valueOf(level));
	    }

	    postLoad();
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
	return Application.Actions.GESTIONE_FATTURAZIONE_SERVIZI;
    }

    // <editor-fold defaultstate="collapsed" desc="Gestione fatturazione">
    @Secure(action = "Menu.Fatturazione.GestioneFatturazione")
    public void ricercaFatturazionePage() {
	getUser().getMenu().reset();
	getUser().getMenu().select("Menu.Fatturazione.GestioneFatturazione");

	getForm().getFiltriFattura().reset();
	getForm().getFiltriFattura().setEditMode();
	getForm().getFiltriFattura().getEstraiRigheFattureDaRicerca().setViewMode();
	try {
	    getForm().getFiltriFattura().getId_tipo_accordo()
		    .setDecodeMap(DecodeMap.Factory.newInstance(
			    entiConvenzionatiEjb.getOrgTipoAccordoTableBean(), "id_tipo_accordo",
			    "cd_tipo_accordo"));
	    getForm().getFiltriFattura().getId_tipo_servizio()
		    .setDecodeMap(DecodeMap.Factory.newInstance(
			    entiConvenzionatiEjb.getOrgTipoServizioTableBean(), "id_tipo_servizio",
			    "cd_tipo_servizio"));
	    getForm().getFiltriFattura().getTi_stato_fattura_ente()
		    .setDecodeMap(ComboGetter.getMappaTiStatoFatturaEnte());
	} catch (ParerUserError e) {
	    getMessageBox().addError(e.getDescription());
	}

	// Azzero la lista risultato ed eventuali stack di navigazione fattura
	getForm().getListaFatture().setTable(null);
	NavigatorDetailBeanManager.resetNavigatorDetailStack();

	forwardToPublisher(Application.Publisher.RICERCA_FATTURE);
    }

    @Override
    public void ricercaFattura() throws EMFError {
	if (getForm().getFiltriFattura().postAndValidate(getRequest(), getMessageBox())) {
	    String nmEnteConvenz = getForm().getFiltriFattura().getNm_ente_convenz().parse();
	    String cdClienteSap = getForm().getFiltriFattura().getCd_cliente_sap().parse();
	    BigDecimal idTipoAccordo = getForm().getFiltriFattura().getId_tipo_accordo().parse();
	    BigDecimal idTipoServizio = getForm().getFiltriFattura().getId_tipo_servizio().parse();
	    BigDecimal aaFatturaDa = getForm().getFiltriFattura().getAa_fattura_da().parse();
	    BigDecimal aaFatturaA = getForm().getFiltriFattura().getAa_fattura_a().parse();
	    BigDecimal pgFatturaEnteDa = getForm().getFiltriFattura().getPg_fattura_ente_da()
		    .parse();
	    BigDecimal pgFatturaEnteA = getForm().getFiltriFattura().getPg_fattura_ente_a().parse();
	    String cdFattura = getForm().getFiltriFattura().getCd_fattura().parse();
	    String cdRegistroEmisFattura = getForm().getFiltriFattura()
		    .getCd_registro_emis_fattura().parse();
	    BigDecimal aaEmissFattura = getForm().getFiltriFattura().getAa_emiss_fattura().parse();
	    String cdEmisFattura = getForm().getFiltriFattura().getCd_emis_fattura().parse();
	    BigDecimal pgFatturaEmis = getForm().getFiltriFattura().getPg_fattura_emis().parse();
	    Date dtEmisFatturaDa = getForm().getFiltriFattura().getDt_emis_fattura_da().parse();
	    Date dtEmisFatturaA = getForm().getFiltriFattura().getDt_emis_fattura_a().parse();
	    Set<String> tiStatoFatturaEnte = getForm().getFiltriFattura().getTi_stato_fattura_ente()
		    .getDecodedValues();
	    try {
		OrgVRicFattureTableBean table = entiConvenzionatiEjb.getOrgVRicFattureTableBean(
			nmEnteConvenz, cdClienteSap, idTipoAccordo, idTipoServizio, aaFatturaDa,
			aaFatturaA, pgFatturaEnteDa, pgFatturaEnteA, cdFattura,
			cdRegistroEmisFattura, aaEmissFattura, cdEmisFattura, pgFatturaEmis,
			dtEmisFatturaDa, dtEmisFatturaA, tiStatoFatturaEnte);
		getForm().getListaFatture().setTable(table);
		getForm().getListaFatture().getTable().setPageSize(10);
		getForm().getListaFatture().getTable().first();

		if (table.size() > 0) {
		    getForm().getFiltriFattura().getEstraiRigheFattureDaRicerca().setEditMode();
		}

	    } catch (ParerUserError pue) {
		getMessageBox().addError(pue.getDescription());
	    }
	}
	forwardToPublisher(Application.Publisher.RICERCA_FATTURE);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Gestione dettaglio fattura">
    private void loadDettaglioFattura(BigDecimal idFatturaEnte) throws ParerUserError, EMFError {
	// Setto chiuse le seguenti sezioni
	getForm().getDatiAccordoSection().setLoadOpened(false);
	getForm().getOperazioniStornoSection().setLoadOpened(false);

	getForm().getFatturaDetail().setViewMode();
	getForm().getFatturaDetail().setStatus(Status.view);
	getForm().getListaFatture().setStatus(Status.view);
	getForm().getListaDetailFattureRiemesse().setStatus(Status.view);

	// Dettaglio fattura
	OrgVVisFatturaRowBean detailRow = entiConvenzionatiEjb
		.getOrgVVisFatturaRowBean(idFatturaEnte);

	// Carico la combo "Registro", va messa qui e non in initAccordoDetail perchè
	// dipende dal valore dei parametri di gestione
	String nmEnte = detailRow.getNmEnte();
	String nmStrut = detailRow.getNmStrut();
	BigDecimal idStrut = entiConvenzionatiEjb.getIdStrut(nmEnte, nmStrut);
	getForm().getFatturaDetail().getCd_registro_emis_fattura_combo()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getRegistriStrutturaTableBean(idStrut), "cd_registro",
			"cd_registro"));

	getForm().getFatturaDetail().copyFromBean(detailRow);

	// Popolamento lista servizi fatturati
	getForm().getListaServiziFatturati().setTable(
		entiConvenzionatiEjb.getOrgVLisServFatturaTableBeanByIdFatturaEnte(idFatturaEnte));
	getForm().getListaServiziFatturati().getTable().setPageSize(10);
	getForm().getListaServiziFatturati().getTable().first();

	// Popolamento lista incassi (pagamento fattura ente)
	getForm().getListaIncassi()
		.setTable(entiConvenzionatiEjb.getOrgPagamFatturaEnteTableBean(idFatturaEnte));
	getForm().getListaIncassi().getTable().setPageSize(10);
	getForm().getListaIncassi().getTable().first();

	// Popolamento lista fatture riemesse
	getForm().getListaFattureRiemesse()
		.setTable(entiConvenzionatiEjb.getFattureRiemesseBaseTable(idFatturaEnte));
	getForm().getListaFattureRiemesse().getTable().setPageSize(10);
	getForm().getListaFattureRiemesse().getTable().first();

	// Popolamento lista solleciti
	getForm().getListaSolleciti()
		.setTable(entiConvenzionatiEjb.getOrgSollecitoFatturaEnteTableBean(idFatturaEnte));
	getForm().getListaSolleciti().getTable().setPageSize(10);
	getForm().getListaSolleciti().getTable().first();

	// Popolamento lista modifiche
	getForm().getListaModifiche()
		.setTable(entiConvenzionatiEjb.getOrgModifFatturaEnteTableBean(idFatturaEnte));
	getForm().getListaModifiche().getTable().setPageSize(10);
	getForm().getListaModifiche().getTable().first();

	// Se la fattura è PAGATA o STORNATA non posso effettuare inserimento di incassi
	if (detailRow.getTiStatoFatturaEnte()
		.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA.getDescrizione())
		|| detailRow.getTiStatoFatturaEnte().equals(
			ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.STORNATA.getDescrizione())) {
	    getForm().getListaIncassi().setHideInsertButton(true);
	} else {
	    getForm().getListaIncassi().setHideInsertButton(false);
	}

	String registro = detailRow.getCdRegistroEmisFattura();
	BigDecimal anno = detailRow.getAaEmissFattura();
	String numero = detailRow.getCdEmisFattura();

	if (!getMessageBox().hasError()) {

	    if (idStrut != null && registro != null && anno != null && numero != null
		    && entiConvenzionatiEjb.isUnitaDocVersataInSacer(idStrut, registro, anno,
			    numero)) {
		getRequest().setAttribute("linkFatturaEmessa", true);
		getSession().setAttribute("externalLinkParamApplic",
			entiConvenzionatiEjb.getSacerUrlForFatturaEmessa() + "/detail/ud/" + idStrut
				+ "/" + registro + "/" + anno + "/" + numero);
	    }
	    initFatturaSectionsOpened();
	}
    }

    public void checkModificaRegistro() throws EMFError {
	// Gestisco i valori di ambiente, ente, struttura e registro e l'eventuale possibilità di
	// modifica degli stessi
	BigDecimal idAmbienteEnteConvenz = getForm().getFatturaDetail()
		.getId_ambiente_ente_convenz().parse();
	BigDecimal idEnteConvenz = getForm().getFatturaDetail().getId_ente_convenz().parse();
	String[][] ambEnteStrut = null;
	try {
	    ambEnteStrut = entiConvenzionatiEjb
		    .getAmbEnteStrutByIamParamApplic(idAmbienteEnteConvenz, idEnteConvenz);
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getMessage());
	}

	String nmEnte = getForm().getFatturaDetail().getNm_ente().parse();
	String nmStrut = getForm().getFatturaDetail().getNm_strut().parse();
	String cdRegistro = getForm().getFatturaDetail().getCd_registro_emis_fattura_text().parse();
	BigDecimal idStrut = entiConvenzionatiEjb.getIdStrut(nmEnte, nmStrut);
	try {
	    getForm().getFatturaDetail().getCd_registro_emis_fattura_combo()
		    .setDecodeMap(DecodeMap.Factory.newInstance(
			    entiConvenzionatiEjb.getRegistriStrutturaTableBean(idStrut),
			    "cd_registro", "cd_registro"));
	    getForm().getFatturaDetail().getCd_registro_emis_fattura_combo().setValue(cdRegistro);
	    if (((String) ambEnteStrut[2][1]).equals(nmStrut)
		    && ((String) ambEnteStrut[1][1]).equals(nmEnte)) {
		getForm().getFatturaDetail().getCd_registro_emis_fattura_combo().setEditMode();
	    } else {
		getForm().getFatturaDetail().getCd_registro_emis_fattura_combo().setViewMode();
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError("Errore durante il caricamento del registro");
	}
    }

    private void loadCurrentDettaglioFatturaFromList(String listName)
	    throws EMFError, ParerUserError {
	actionLogger.info("Caricamento dettaglio della fattura da ricerca fatture");
	// Salvo in sessione il "tipo" di lista dalla quale "proviene" la fattura, sarà ListaFatture
	// o
	// ListaFattureRiemesse
	getSession().setAttribute("navTableFatture", listName);
	// Prendendo i dati dalla form, popolo un oggetto List generico del tipo gestito dal
	// framework naturalmente
	it.eng.spagoLite.form.list.List formList = ((it.eng.spagoLite.form.list.List) getForm()
		.getComponent(listName));
	// Ricavo la riga corrente, recuperata dall'oggetto Table della lista
	BaseRowInterface listRow = formList.getTable().getCurrentRow();
	// Se nullo il relativo parametro, siamo per forza al primo livello di annidamento
	BigDecimal level = getForm().getFatturaDetail().getLevel_fatture().parse() != null
		? getForm().getFatturaDetail().getLevel_fatture().parse()
		: BigDecimal.ONE;
	// Carico il dettaglio fattura
	loadDettaglioFattura(listRow.getBigDecimal("id_fattura_ente"));
	// Inizializzo i campi di dettaglio
	initFatturaDetail();
	getForm().getFatturaDetail().getLevel_fatture().setValue(BigDecimal.ONE.toPlainString());
	// Se sto scorrendo avanti/indietro e il livello di annidamento dell'ultimo oggetto presente
	// nello stack è
	// uguale al mio, eseguo una "pop" di PARI LIVELLO
	if ((getNavigationEvent().equals(ListAction.NE_NEXT)
		|| getNavigationEvent().equals(ListAction.NE_PREV))
		&& NavigatorDetailBeanManager.getLastNavigatorDetailStack() != null
		&& level.intValue() == NavigatorDetailBeanManager.getLastNavigatorDetailStack()
			.getLevel()) {
	    // Se mi sono spostato nella collezione eseguo una pop dallo stack per gli elementi
	    // dello stesso livello...
	    NavigatorDetailBeanManager.popNavigatorDetailStack();
	}
	// ... quindi inserisco (reinserisco se l'avevo tolto nel precedente if...) l'elemento allo
	// stesso livello
	NavigatorDetailBeanManager.pushNavigatorDetailStack(
		listRow.getBigDecimal("id_fattura_ente"), formList.getName(), formList.getTable(),
		1);
    }

    private void initFatturaDetail() throws ParerUserError, EMFError {
	BigDecimal idAmbienteEnteConvenz = getForm().getFatturaDetail()
		.getId_ambiente_ente_convenz().parse();
	BigDecimal idEnteConvenz = getForm().getFatturaDetail().getId_ente_convenz().parse();

	String nmEnte = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.NM_ENTE_FATT_ATTIVE.name(), idAmbienteEnteConvenz,
		idEnteConvenz, Constants.TipoIamVGetValAppart.ENTECONVENZ);
	String nmStrut = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.NM_STRUT_FATT_ATTIVE.name(),
		idAmbienteEnteConvenz, idEnteConvenz, Constants.TipoIamVGetValAppart.ENTECONVENZ);
	String nmTipoUnitaDoc = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.NM_TIPOUD_FATT_ATTIVE.name(),
		idAmbienteEnteConvenz, idEnteConvenz, Constants.TipoIamVGetValAppart.ENTECONVENZ);

	Long idTipoUnitaDoc = entiConvenzionatiEjb.getIdTipoUnitaDoc(nmEnte, nmStrut,
		nmTipoUnitaDoc);

	if (idTipoUnitaDoc != null) {
	    getForm().getFatturaDetail().getCd_registro_emis_fattura_combo()
		    .setDecodeMap(DecodeMap.Factory.newInstance(
			    entiConvenzionatiEjb
				    .getRegistriConnessiTableBean(new BigDecimal(idTipoUnitaDoc)),
			    "cd_registro_unita_doc", "cd_registro_unita_doc"));
	    getForm().getFatturaDetail().getCd_registro_emis_nota_credito_combo()
		    .setDecodeMap(DecodeMap.Factory.newInstance(
			    entiConvenzionatiEjb
				    .getRegistriConnessiTableBean(new BigDecimal(idTipoUnitaDoc)),
			    "cd_registro_unita_doc", "cd_registro_unita_doc"));
	}
	getForm().getFatturaDetail().getFl_da_riemis()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());

    }

    private void initServizioFatturatoDetail() throws ParerUserError {
	getForm().getServizioFatturatoDetail().getCd_iva().setDecodeMap(DecodeMap.Factory
		.newInstance(entiConvenzionatiEjb.getOrgCdIvaTableBean(), "cd_iva", "cd_iva"));
    }

    @Override
    public void updateListaFatture() throws EMFError {
	// Metto tutto in viewMode, tranne le poche cose che mi servono in base ai casi
	String tiStatoFatturaEnte = getForm().getFatturaDetail().getTi_stato_fattura_ente().parse();
	getForm().getFatturaDetail().setViewMode();
	checkModificaRegistro();
	getForm().getFatturaDetail().setStatus(Status.update);
	getForm().getListaFatture().setStatus(Status.update);
	// Prendo la data corrente
	getForm().getFatturaPopUpFields().setEditMode();
	DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	Calendar cal = Calendar.getInstance();
	getForm().getFatturaPopUpFields().getData_modifica()
		.setValue(formato.format(cal.getTime()));
	initFatturaFieldsStatusBySections(tiStatoFatturaEnte);
    }

    @Override
    public void updateListaDetailFattureRiemesse() throws EMFError {
	// Metto tutto in viewMode, tranne le poche cose che mi servono in base ai casi
	String tiStatoFatturaEnte = getForm().getFatturaDetail().getTi_stato_fattura_ente().parse();
	getForm().getFatturaDetail().setViewMode();
	checkModificaRegistro();
	getForm().getFatturaDetail().setStatus(Status.update);
	getForm().getListaDetailFattureRiemesse().setStatus(Status.update);
	// Prendo la data corrente
	getForm().getFatturaPopUpFields().setEditMode();
	DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	Calendar cal = Calendar.getInstance();
	getForm().getFatturaPopUpFields().getData_modifica()
		.setValue(formato.format(cal.getTime()));
	initFatturaFieldsStatusBySections(tiStatoFatturaEnte);
    }

    @Override
    public void updateListaFattureRiemesse() throws EMFError {
	updateListaDetailFattureRiemesse();
    }

    private void loadDettaglioServizioFatturato(BigDecimal idServizioFattura)
	    throws ParerUserError, EMFError {
	OrgVLisServFatturaRowBean detailRow = entiConvenzionatiEjb
		.getOrgVLisServFatturaRowBean(idServizioFattura);
	getForm().getServizioFatturatoDetail().copyFromBean(detailRow);
	getForm().getServizioFatturatoDetail().setViewMode();
	getForm().getServizioFatturatoDetail().setStatus(Status.view);
	getForm().getListaServiziFatturati().setStatus(Status.view);
    }

    private void initFatturaSectionsOpened() {
	getForm().getEnteConvenzionatoSection().setLoadOpened(true);
	getForm().getDatiAccordoSection().setLoadOpened(false);
	getForm().getStatoFatturaSection().setLoadOpened(true);
	getForm().getDatiFatturaTemporaneaSection().setLoadOpened(true);
	getForm().getDatiFatturaEmessaSection().setLoadOpened(true);
	getForm().getImportiSection().setLoadOpened(true);
	getForm().getOperazioniStornoSection().setLoadOpened(false);
    }

    private void initFatturaFieldsStatusBySections(String tiStatoFatturaEnte) {
	/*
	 * In tutti i casi non tocco i campi relativi ad Ente convenzionato, Dati Accordo e Stato
	 * Fattura in quanto non sono mai modificabili
	 */
	if (tiStatoFatturaEnte
		.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.CALCOLATA.getDescrizione())
		|| tiStatoFatturaEnte
			.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA.getDescrizione())
		|| tiStatoFatturaEnte.equals(
			ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.STORNATA.getDescrizione())
		|| tiStatoFatturaEnte
			.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE
				.getDescrizione())
		|| tiStatoFatturaEnte
			.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA.getDescrizione())
		|| tiStatoFatturaEnte.equals(
			ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA.getDescrizione())
		|| tiStatoFatturaEnte.equals(
			ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.SOLLECITATA.getDescrizione())) {
	    // Rendo editabili i dati di Fattura Temporanea
	    getForm().getFatturaDetail().getAa_fattura().setEditMode();
	    getForm().getFatturaDetail().getPg_fattura().setEditMode();
	    getForm().getFatturaDetail().getCd_fattura().setEditMode();
	    // Rendo editabili i dati di Fattura Emessa
	    getForm().getFatturaDetail().getCd_registro_emis_fattura_combo().setEditMode();
	    getForm().getFatturaDetail().getAa_emiss_fattura().setEditMode();
	    getForm().getFatturaDetail().getCd_emis_fattura().setEditMode();
	    getForm().getFatturaDetail().getDt_emis_fattura().setEditMode();
	    getForm().getFatturaDetail().getDt_scad_fattura().setEditMode();
	    getForm().getFatturaDetail().getCd_fattura_sap().setEditMode();
	    getForm().getFatturaDetail().getNt_emis_fattura().setEditMode();
	    // // Rendo editabili i dati di Importi
	    getForm().getFatturaDetail().getIm_tot_iva().setEditMode();
	    getForm().getFatturaDetail().getIm_tot_fattura().setEditMode();
	    getForm().getFatturaDetail().getIm_tot_da_pagare().setEditMode();

	    if (tiStatoFatturaEnte
		    .equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA.getDescrizione())
		    || tiStatoFatturaEnte
			    .equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.STORNATA
				    .getDescrizione())) {
		// Rendo editabili i dati di Operazioni di storno
		getForm().getFatturaDetail().getCd_registro_emis_nota_credito_combo().setEditMode();
		getForm().getFatturaDetail().getAa_emiss_nota_credito().setEditMode();
		getForm().getFatturaDetail().getCd_emis_nota_credito().setEditMode();
		getForm().getFatturaDetail().getDt_emis_nota_credito().setEditMode();
		getForm().getFatturaDetail().getNt_emis_nota_credito().setEditMode();

		if (tiStatoFatturaEnte.equals(
			ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA.getDescrizione())) {
		    getForm().getFatturaDetail().getFl_da_riemis().setViewMode();
		} else {
		    getForm().getFatturaDetail().getFl_da_riemis().setEditMode();
		}
	    }

	    // Se è insoluta e NON esistono dei pagamenti parziali, rendo editabili i campi della
	    // nota di credito
	    if ((tiStatoFatturaEnte
		    .equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA.getDescrizione())
		    || tiStatoFatturaEnte
			    .equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.SOLLECITATA
				    .getDescrizione()))
		    && (getForm().getListaIncassi().getTable() == null
			    || getForm().getListaIncassi().getTable().isEmpty())) {
		getForm().getFatturaDetail().getCd_registro_emis_nota_credito_combo().setEditMode();
		getForm().getFatturaDetail().getAa_emiss_nota_credito().setEditMode();
		getForm().getFatturaDetail().getCd_emis_nota_credito().setEditMode();
		getForm().getFatturaDetail().getDt_emis_nota_credito().setEditMode();
		getForm().getFatturaDetail().getNt_emis_nota_credito().setEditMode();
		getSession().setAttribute("pagamentiParziali", false);
	    }

	    if (tiStatoFatturaEnte.equals(
		    ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.STORNATA.getDescrizione())) {
		getForm().getFatturaDetail().getFl_da_riemis().setEditMode();
	    }
	}
    }

    /**
     * Controlla l'obbligatorietà dei campi in fase di modifica fattura in base allo stato della
     * stessa
     *
     * @param fatturaBean
     */
    private void checkObbligatorietaCampiFattura(FatturaBean fatturaBean) {
	if (fatturaBean.getTiStatoFatturaEnte()
		.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA.getDescrizione())
		|| fatturaBean.getTiStatoFatturaEnte().equals(
			ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.STORNATA.getDescrizione())
		|| ((fatturaBean.getTiStatoFatturaEnte().equals(
			ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA.getDescrizione())
			|| fatturaBean.getTiStatoFatturaEnte()
				.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.SOLLECITATA
					.getDescrizione()))
			&& (getForm().getListaIncassi().getTable() == null
				|| getForm().getListaIncassi().getTable().isEmpty()))) {
	    if (!((fatturaBean.getCdRegistroEmisNotaCredito() != null
		    && fatturaBean.getAaEmissNotaCredito() != null
		    && fatturaBean.getCdEmisNotaCredito() != null
		    && fatturaBean.getDtEmisNotaCredito() != null)
		    || (fatturaBean.getCdRegistroEmisNotaCredito() == null
			    && fatturaBean.getAaEmissNotaCredito() == null
			    && fatturaBean.getCdEmisNotaCredito() == null
			    && fatturaBean.getDtEmisNotaCredito() == null))) {
		getMessageBox().addError(
			"Occorre valorizzare sia la chiave che la data della nota di credito");
	    } else if ((fatturaBean.getCdRegistroEmisNotaCredito() != null
		    && fatturaBean.getAaEmissNotaCredito() != null
		    && fatturaBean.getCdEmisNotaCredito() != null
		    && fatturaBean.getDtEmisNotaCredito() != null)
		    && (fatturaBean.getCdRegistroEmisFattura() == null
			    || fatturaBean.getAaEmissFattura() == null
			    || fatturaBean.getCdEmisFattura() == null
			    || fatturaBean.getDtEmisFattura() == null
			    || fatturaBean.getDtScadFattura() == null)) {
		getMessageBox().addError(
			"Non è possibile valorizzare le informazioni riferite allo storno della fattura senza valorizzare i dati di emissione della fattura");
	    }
	}
	if (fatturaBean.getTiStatoFatturaEnte().equals(
		ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE.getDescrizione())
		|| fatturaBean.getTiStatoFatturaEnte()
			.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA.getDescrizione())
		|| ((fatturaBean.getTiStatoFatturaEnte().equals(
			ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA.getDescrizione())
			|| fatturaBean.getTiStatoFatturaEnte()
				.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.SOLLECITATA
					.getDescrizione()))
			&& getForm().getListaIncassi().getTable() != null
			&& !getForm().getListaIncassi().getTable().isEmpty())) {
	    if (fatturaBean.getCdRegistroEmisFattura() == null
		    || fatturaBean.getAaEmissFattura() == null
		    || fatturaBean.getCdEmisFattura() == null
		    || fatturaBean.getDtEmisFattura() == null
		    || fatturaBean.getDtScadFattura() == null) {
		getMessageBox().addError(
			"Non è possibile eliminare i campi di emissione della fattura senza aver prima eliminato i pagamenti");
	    }
	}
    }

    private void saveFattura() throws EMFError {
	if (getForm().getFatturaDetail().postAndValidate(getRequest(), getMessageBox())) {
	    try {
		FatturaBean fatturaBean = new FatturaBean(getForm().getFatturaDetail());
		checkObbligatorietaCampiFattura(fatturaBean);
		if (!getMessageBox().hasError()) {
		    checkChangeStatusAndSaveFattura(fatturaBean);
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
	    }
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_FATTURA);
    }

    public void checkChangeStatusAndSaveFattura(FatturaBean fatturaBean)
	    throws ParerUserError, EMFError {
	String customMessage = "";
	String tiStatoFatturaEnte = fatturaBean.getTiStatoFatturaEnte();
	// Salvo gli attributi in sessione
	Object[] attrFattura = new Object[2];
	attrFattura[0] = fatturaBean;
	getSession().setAttribute("salvataggioAttributesFattura", attrFattura);
	/* CALCOLATA */
	if (tiStatoFatturaEnte
		.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.CALCOLATA.getDescrizione())) {
	    if (fatturaBean.getCdRegistroEmisFattura() != null
		    && fatturaBean.getAaEmissFattura() != null
		    && fatturaBean.getCdEmisFattura() != null
		    && fatturaBean.getDtEmisFattura() != null
		    && fatturaBean.getDtScadFattura() != null) {
		customMessage = "La compilazione della Chiave Fattura, della Data di emissione e della Scadenza di pagamento comporterà il passaggio della fattura allo stato EMESSA. Si intende procedere?";
		attrFattura[1] = ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA
			.getDescrizione();
		getRequest().setAttribute("customMessageSalvataggioFattura", customMessage);
		getRequest().setAttribute("customBoxSalvataggioFatturaControllo1", true);
	    } else {
		eseguiSalvataggioFattura(fatturaBean, null, null, null);
	    }
	} /* EMESSA */ else if (tiStatoFatturaEnte
		.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA.getDescrizione())) {
	    if (fatturaBean.getCdRegistroEmisNotaCredito() != null
		    && fatturaBean.getAaEmissNotaCredito() != null
		    && fatturaBean.getCdEmisNotaCredito() != null
		    && fatturaBean.getDtEmisNotaCredito() != null
		    && fatturaBean.getCdRegistroEmisFattura() != null
		    && fatturaBean.getAaEmissFattura() != null
		    && fatturaBean.getCdEmisFattura() != null
		    && fatturaBean.getDtEmisFattura() != null
		    && fatturaBean.getDtScadFattura() != null) {
		customMessage = "La valorizzazione della chiave della nota di storno e della data della nota di credito comporterà il passaggio della fattura allo stato STORNATA. Si intende procedere?";
		attrFattura[1] = ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.STORNATA
			.getDescrizione();
		getRequest().setAttribute("customMessageSalvataggioFattura", customMessage);
		getRequest().setAttribute("customBoxSalvataggioFatturaControllo1", true);
	    } else {
		secondCheckChangeStatusAndSaveFattura();
	    }
	} /* STORNATA */ else if (tiStatoFatturaEnte
		.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.STORNATA.getDescrizione())) {
	    if (fatturaBean.getCdRegistroEmisNotaCredito() == null
		    && fatturaBean.getAaEmissNotaCredito() == null
		    && fatturaBean.getCdEmisNotaCredito() == null
		    && fatturaBean.getDtEmisNotaCredito() == null
		    && fatturaBean.getCdRegistroEmisFattura() != null
		    && fatturaBean.getAaEmissFattura() != null
		    && fatturaBean.getCdEmisFattura() != null
		    && fatturaBean.getDtEmisFattura() != null
		    && fatturaBean.getDtScadFattura() != null) {
		customMessage = "La mancata valorizzazione della Chiave della nota di storno e della data della nota di credito comporterà il passaggio della fattura allo stato EMESSA. Si intende procedere?";
		attrFattura[1] = attrFattura[1] = ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA
			.getDescrizione();
		;
		getRequest().setAttribute("customMessageSalvataggioFattura", customMessage);
		getRequest().setAttribute("customBoxSalvataggioFatturaControllo1", true);
	    } else {
		secondCheckChangeStatusAndSaveFattura();
	    }
	} /* INSOLUTA o SOLLECITATA */ else if (tiStatoFatturaEnte
		.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA.getDescrizione())
		|| tiStatoFatturaEnte.equals(
			ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.SOLLECITATA.getDescrizione())) {

	    if (getForm().getListaIncassi().getTable() == null
		    || getForm().getListaIncassi().getTable().isEmpty()) {
		if (fatturaBean.getCdRegistroEmisNotaCredito() != null
			&& fatturaBean.getAaEmissNotaCredito() != null
			&& fatturaBean.getCdEmisNotaCredito() != null
			&& fatturaBean.getDtEmisNotaCredito() != null) {
		    customMessage = "La valorizzazione della chiave della nota di storno e della data della nota di credito comporterà il passaggio della fattura allo stato STORNATA. Si intende procedere?";
		    attrFattura[1] = attrFattura[1] = ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.STORNATA
			    .getDescrizione();
		    ;
		    getRequest().setAttribute("customMessageSalvataggioFattura", customMessage);
		    getRequest().setAttribute("customBoxSalvataggioFatturaControllo1", true);
		} else {
		    secondCheckChangeStatusAndSaveFattura();
		}
	    } else {
		checkImportiTotaliFatturaAndGo();
	    }

	} else if (tiStatoFatturaEnte.equals(
		ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE.getDescrizione())
		|| tiStatoFatturaEnte.equals(
			ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA.getDescrizione())) {
	    thirdStepSaveFattura();
	}
    }

    public void secondCheckChangeStatusAndSaveFattura() throws ParerUserError, EMFError {
	Object[] attrFattura = (Object[]) getSession().getAttribute("salvataggioAttributesFattura");
	FatturaBean fatturaBean = (FatturaBean) attrFattura[0];
	String customMessage = "";
	if (fatturaBean.getTiStatoFatturaEnte()
		.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA.getDescrizione())) {
	    Object[] attrFattura2 = new Object[2];
	    attrFattura2[0] = fatturaBean;
	    attrFattura2[1] = (String) attrFattura[1];
	    getSession().setAttribute("salvataggioAttributesFattura", attrFattura2);
	    if (fatturaBean.getCdRegistroEmisFattura() == null
		    || fatturaBean.getAaEmissFattura() == null
		    || fatturaBean.getCdEmisFattura() == null
		    || fatturaBean.getDtEmisFattura() == null
		    || fatturaBean.getDtScadFattura() == null) {
		customMessage = "La mancata valorizzazione dei campi Chiave Fattura - Data di emissione - Scadenza di pagamento comporterà il passaggio della fattura allo stato CALCOLATA. Si intende procedere?";
		attrFattura2[1] = ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.CALCOLATA
			.getDescrizione();
		getRequest().setAttribute("customBoxSalvataggioFatturaControllo2", true);
		getRequest().setAttribute("customMessageSalvataggioFattura", customMessage);
	    } else {
		checkImportiTotaliFatturaAndGo();
	    }
	} else if (fatturaBean.getTiStatoFatturaEnte()
		.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.STORNATA.getDescrizione())) {
	    Object[] attrFattura2 = new Object[2];
	    attrFattura2[0] = fatturaBean;
	    attrFattura2[1] = (String) attrFattura[1];
	    getSession().setAttribute("salvataggioAttributesFattura", attrFattura2);
	    if (fatturaBean.getCdRegistroEmisFattura() == null
		    || fatturaBean.getAaEmissFattura() == null
		    || fatturaBean.getCdEmisFattura() == null
		    || fatturaBean.getDtEmisFattura() == null
		    || fatturaBean.getDtScadFattura() == null) {
		customMessage = "La mancata valorizzazione dei campi Chiave Fattura - Data di emissione - Scadenza di pagamento comporterà il passaggio della fattura allo stato CALCOLATA. Si intende procedere?";
		attrFattura2[1] = ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.CALCOLATA
			.getDescrizione();
		getRequest().setAttribute("customMessageSalvataggioFattura", customMessage);
		getRequest().setAttribute("customBoxSalvataggioFatturaControllo2", true);
	    } else {
		checkImportiTotaliFatturaAndGo();
	    }
	} else if (fatturaBean.getTiStatoFatturaEnte()
		.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA.getDescrizione())
		|| fatturaBean.getTiStatoFatturaEnte().equals(
			ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.SOLLECITATA.getDescrizione())) {
	    Object[] attrFattura2 = new Object[2];
	    attrFattura2[0] = fatturaBean;
	    attrFattura2[1] = (String) attrFattura[1];
	    getSession().setAttribute("salvataggioAttributesFattura", attrFattura2);
	    if (fatturaBean.getCdRegistroEmisFattura() == null
		    || fatturaBean.getAaEmissFattura() == null
		    || fatturaBean.getCdEmisFattura() == null
		    || fatturaBean.getDtEmisFattura() == null
		    || fatturaBean.getDtScadFattura() == null) {
		customMessage = "La mancata valorizzazione dei campi Chiave Fattura - Data di emissione - Scadenza di pagamento comporterà il passaggio della fattura allo stato CALCOLATA. Si intende procedere?";
		attrFattura2[1] = ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.CALCOLATA
			.getDescrizione();
		getRequest().setAttribute("customMessageSalvataggioFattura", customMessage);
		getRequest().setAttribute("customBoxSalvataggioFatturaControllo2", true);
	    } else {
		checkImportiTotaliFatturaAndGo();
	    }
	}
    }

    public void thirdStepSaveFattura() throws ParerUserError, EMFError {
	Object[] attrFattura = (Object[]) getSession().getAttribute("salvataggioAttributesFattura");
	FatturaBean fatturaBean = (FatturaBean) attrFattura[0];

	if (fatturaBean.getTiStatoFatturaEnte()
		.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.CALCOLATA.getDescrizione())) {
	    String tiStatoFatturaEnteNew = (String) attrFattura[1];
	    eseguiSalvataggioFattura(fatturaBean, tiStatoFatturaEnteNew, null, null);
	} else if (fatturaBean.getTiStatoFatturaEnte()
		.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA.getDescrizione())
		|| fatturaBean.getTiStatoFatturaEnte().equals(
			ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.STORNATA.getDescrizione())
		|| fatturaBean.getTiStatoFatturaEnte().equals(
			ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA.getDescrizione())
		|| fatturaBean.getTiStatoFatturaEnte()
			.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE
				.getDescrizione())
		|| fatturaBean.getTiStatoFatturaEnte().equals(
			ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA.getDescrizione())) {
	    checkImportiTotaliFatturaAndGo();
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_FATTURA);
    }

    private void checkImportiTotaliFatturaAndGo() throws ParerUserError, EMFError {
	Object[] attrFattura = (Object[]) getSession().getAttribute("salvataggioAttributesFattura");
	FatturaBean fatturaBean = (FatturaBean) attrFattura[0];

	// Controllo importi
	OrgVCalcTotFattRowBean totali = entiConvenzionatiEjb
		.getOrgVCalcTotFattRowBean(fatturaBean.getIdFatturaEnte());
	BigDecimal imTotIvaByCalc = totali.getImTotIva();
	BigDecimal imTotFatturaByCalc = totali.getImTotFattura();
	BigDecimal imTotDaPagareByCalc = totali.getImTotDaPagare();
	BigDecimal imTotIva = getForm().getFatturaDetail().getIm_tot_iva().parse() != null
		? getForm().getFatturaDetail().getIm_tot_iva().parse()
		: BigDecimal.ZERO;
	BigDecimal imTotFattura = getForm().getFatturaDetail().getIm_tot_fattura().parse() != null
		? getForm().getFatturaDetail().getIm_tot_fattura().parse()
		: BigDecimal.ZERO;
	BigDecimal imTotDaPagare = getForm().getFatturaDetail().getIm_tot_da_pagare()
		.parse() != null ? getForm().getFatturaDetail().getIm_tot_da_pagare().parse()
			: BigDecimal.ZERO;
	if (imTotDaPagare.compareTo(imTotDaPagareByCalc) != 0
		|| imTotFattura.compareTo(imTotFatturaByCalc) != 0
		|| imTotIva.compareTo(imTotIvaByCalc) != 0) {
	    String customMessage = "Attenzione: gli importi indicati sulla fattura non coincidono con quelli calcolati dal sistema. Desideri confermare il salvataggio?";
	    getRequest().setAttribute("customMessageSalvataggioFattura", customMessage);
	    getRequest().setAttribute("customBoxSalvataggioFatturaControlloImporti", true);
	} else {
	    String tiStatoFatturaEnte = fatturaBean.getTiStatoFatturaEnte();
	    if (tiStatoFatturaEnte
		    .equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA.getDescrizione())) {
		String customMessage = "Attenzione: è stata operata una modifica ad una fattura già emessa. Si desidera confermare l’operazione?";
		getRequest().setAttribute("customMessageSalvataggioFattura", customMessage);
	    } else {
		getRequest().setAttribute("customMessageSalvataggioFattura", null);
	    }
	    getRequest().setAttribute("customBoxSalvataggioFatturaInserimentoDataNote", true);
	}

	if (fatturaBean.getTiStatoFatturaEnte()
		.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA.getDescrizione())
		|| fatturaBean.getTiStatoFatturaEnte()
			.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE
				.getDescrizione())) {
	    // Importo incassato
	    BigDecimal imTotPagam = getForm().getFatturaDetail().getIm_tot_pagam().parse() != null
		    ? getForm().getFatturaDetail().getIm_tot_pagam().parse()
		    : BigDecimal.ZERO;
	    // im_tot_pagam --> Importo incassato
	    // im_tot_da_pagare --> Importo pagamento
	    if (imTotPagam.compareTo(imTotDaPagare) >= 0) {
		if (!fatturaBean.getTiStatoFatturaEnte().equals(
			ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA.getDescrizione())) {
		    attrFattura[1] = ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA
			    .getDescrizione();
		}
	    }

	    if (imTotPagam.compareTo(BigDecimal.ZERO) == 0) {
		if (!fatturaBean.getDtScadFattura().before(new Date())) {
		    attrFattura[1] = ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA
			    .getDescrizione();
		} else {
		    attrFattura[1] = ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA
			    .getDescrizione();
		}
	    }

	    if (imTotPagam.compareTo(BigDecimal.ZERO) != 0
		    && imTotPagam.compareTo(imTotDaPagare) < 0) {
		if (!fatturaBean.getDtScadFattura().before(new Date())) {
		    if (!fatturaBean.getTiStatoFatturaEnte()
			    .equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE
				    .getDescrizione())) {
			attrFattura[1] = ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE
				.getDescrizione();
		    }
		} else {
		    attrFattura[1] = ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA
			    .getDescrizione();
		}
	    }
	}

	if (fatturaBean.getTiStatoFatturaEnte()
		.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA.getDescrizione())
		|| fatturaBean.getTiStatoFatturaEnte().equals(
			ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.SOLLECITATA.getDescrizione())) {
	    // Se non esistono pagamenti parziali
	    if (getForm().getListaIncassi().getTable() == null
		    || getForm().getListaIncassi().getTable().isEmpty()) {
		// Se sono stati valorizzati i campi della nota di credito
		if (fatturaBean.getCdRegistroEmisNotaCredito() != null
			&& fatturaBean.getAaEmissNotaCredito() != null
			&& fatturaBean.getCdEmisNotaCredito() != null
			&& fatturaBean.getDtEmisNotaCredito() != null) {
		    attrFattura[1] = ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.STORNATA
			    .getDescrizione();
		} else {
		    checkImportiInsolutaSollecitata(attrFattura, imTotDaPagare);
		}
	    } else {
		checkImportiInsolutaSollecitata(attrFattura, imTotDaPagare);
	    }
	}
    }

    private void checkImportiInsolutaSollecitata(Object[] attrFattura, BigDecimal imTotDaPagare)
	    throws EMFError {
	FatturaBean fatturaBean = (FatturaBean) attrFattura[0];
	// Importo incassato
	BigDecimal imTotPagam = getForm().getFatturaDetail().getIm_tot_pagam().parse() != null
		? getForm().getFatturaDetail().getIm_tot_pagam().parse()
		: BigDecimal.ZERO;
	// im_tot_pagam --> Importo incassato
	// im_tot_da_pagare --> Importo pagamento
	if (imTotPagam.compareTo(imTotDaPagare) >= 0) {
	    attrFattura[1] = ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA.getDescrizione();
	}

	if (imTotPagam.compareTo(BigDecimal.ZERO) == 0) {
	    if (fatturaBean.getTiStatoFatturaEnte().equals(
		    ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.SOLLECITATA.getDescrizione())) {
		// resta sollecitata
	    } else {
		if (!fatturaBean.getDtScadFattura().before(new Date())) {
		    attrFattura[1] = ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA
			    .getDescrizione();
		} else {
		    if (!fatturaBean.getTiStatoFatturaEnte()
			    .equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA
				    .getDescrizione())) {
			attrFattura[1] = ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA
				.getDescrizione();
		    }
		}
	    }
	}

	if (imTotPagam.compareTo(BigDecimal.ZERO) != 0 && imTotPagam.compareTo(imTotDaPagare) < 0) {
	    if (fatturaBean.getTiStatoFatturaEnte().equals(
		    ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.SOLLECITATA.getDescrizione())) {
		// resta sollecitata
	    } else {
		if (!fatturaBean.getDtScadFattura().before(new Date())) {
		    attrFattura[1] = ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE
			    .getDescrizione();
		} else {
		    if (!fatturaBean.getTiStatoFatturaEnte()
			    .equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA
				    .getDescrizione())) {
			attrFattura[1] = ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA
				.getDescrizione();
		    }
		}
	    }
	}
    }

    public void fourthStepSaveFattura() throws ParerUserError, EMFError {
	getRequest().setAttribute("customBoxSalvataggioFatturaInserimentoDataNote", true);
	forwardToPublisher(Application.Publisher.DETTAGLIO_FATTURA);
    }

    @Override
    public void confermaSalvataggioFattura() {
	if (getSession().getAttribute("salvataggioAttributesFattura") != null) {
	    try {
		Object[] attrFattura = (Object[]) getSession()
			.getAttribute("salvataggioAttributesFattura");
		FatturaBean fatturaBean = (FatturaBean) attrFattura[0];
		String tiStatoFatturaEnteNew = (String) attrFattura[1];

		String dataModificaStr = getRequest().getParameter("dataModifica");
		String noteModifica = getRequest().getParameter("noteModifica");

		if (!StringUtils.isBlank(dataModificaStr) && !StringUtils.isBlank(noteModifica)) {
		    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		    Date dataModifica = df.parse(dataModificaStr);
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(dataModifica);
		    int year = cal.get(Calendar.YEAR);
		    int month = cal.get(Calendar.MONTH);
		    int day = cal.get(Calendar.DAY_OF_MONTH);
		    cal = Calendar.getInstance();
		    cal.set(year, month, day);
		    dataModifica = cal.getTime();
		    eseguiSalvataggioFattura(fatturaBean, tiStatoFatturaEnteNew, dataModifica,
			    noteModifica);
		} else {
		    getMessageBox().addError("Attenzione: data e note sono campi obbligatori");
		    // Faccio riapparire la popup
		    getRequest().setAttribute("customBoxSalvataggioFatturaInserimentoDataNote",
			    true);
		}
	    } catch (Exception ex) {
		getMessageBox().addError(ex.getMessage());
	    }
	    forwardToPublisher(Application.Publisher.DETTAGLIO_FATTURA);
	}
    }

    @Override
    public void annullaSalvataggioFattura() throws EMFError {
	throw new UnsupportedOperationException("Not supported yet."); // To change body of
								       // generated methods, choose
	// Tools | Templates.
    }

    private void eseguiSalvataggioFattura(FatturaBean fatturaBean, String tiStatoFatturaEnte,
	    Date dataModifica, String noteModifica) throws ParerUserError, EMFError {
	/*
	 * Codice aggiuntivo per il logging...
	 */
	LogParam param = SpagoliteLogUtil.getLogParam(paramHelper.getParamApplicApplicationName(),
		getUser().getUsername(), SpagoliteLogUtil.getPageName(this));
	param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());

	param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());

	entiConvenzionatiEjb.saveFattura(param, fatturaBean, tiStatoFatturaEnte, dataModifica,
		noteModifica);
	getForm().getFatturaDetail().setStatus(Status.view);
	getForm().getFatturaDetail().setViewMode();
	getForm().getListaFatture().setStatus(Status.view);
	loadDettaglioFattura(fatturaBean.getIdFatturaEnte());
	postLoad();
	getMessageBox().addInfo("Fattura modificata con successo");
    }

    @Override
    public void deleteListaFatture() throws EMFError {
	BigDecimal idFatturaEnte = ((BaseTableInterface<?>) getForm().getListaFatture().getTable())
		.getCurrentRow().getBigDecimal("id_fattura_ente");
	int riga = getForm().getListaFatture().getTable().getCurrentRowIndex();
	try {
	    /*
	     * Codice aggiuntivo per il logging...
	     */
	    LogParam param = SpagoliteLogUtil.getLogParam(
		    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
		    SpagoliteLogUtil.getPageName(this));
	    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
	    if (param.getNomePagina().equalsIgnoreCase(Application.Publisher.DETTAGLIO_FATTURA)) {
		param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
	    } else {
		param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
			getForm().getListaFatture()));
	    }

	    entiConvenzionatiEjb.deleteOrgFatturaEnte(param, idFatturaEnte);
	    getForm().getListaFatture().getTable().remove(riga);
	    getMessageBox().addInfo("Fattura eliminata con successo");
	    getMessageBox().setViewMode(ViewMode.plain);

	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}

	if (!getMessageBox().hasError()
		&& getLastPublisher().equals(Application.Publisher.DETTAGLIO_FATTURA)) {
	    goBack();
	} else {
	    forwardToPublisher(getLastPublisher());
	}
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Gestione incasso">
    private void loadDettaglioIncasso(BigDecimal idPagamFatturaEnte)
	    throws ParerUserError, EMFError {
	// Setto chiuse le seguenti sezioni
	getForm().getEnteConvenzionatoSection().setLoadOpened(false);
	getForm().getFatturaSection().setLoadOpened(true);
	getForm().getStatoFatturaSection().setLoadOpened(true);
	getForm().getDocumentoIncassoSection().setLoadOpened(true);

	// Dettaglio incasso
	OrgPagamFatturaEnteRowBean paramFatturaEnteRow = entiConvenzionatiEjb
		.getOrgPagamFatturaEnteRowBean(idPagamFatturaEnte);
	getForm().getIncassoDetail().copyFromBean(paramFatturaEnteRow);

	getForm().getIncassoDetail().setViewMode();
	getForm().getFatturaDetail().setStatus(Status.view);
	getForm().getListaIncassi().setStatus(Status.view);

	// Se la fattura è in stato STORNATA non posso effettuare modifiche
	if (getForm().getFatturaDetail().getTi_stato_fattura_ente().parse()
		.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.STORNATA.getDescrizione())) {
	    getForm().getListaIncassi().setHideUpdateButton(true);
	} else {
	    getForm().getListaIncassi().setHideUpdateButton(false);
	}
    }

    private void saveIncasso() throws EMFError {
	if (getForm().getIncassoDetail().postAndValidate(getRequest(), getMessageBox())) {
	    // Controllo che almeno uno dei due campi sia stato valorizzato
	    if (getForm().getIncassoDetail().getCd_provv_pagam().parse() == null
		    && getForm().getIncassoDetail().getCd_rev_pagam().parse() == null) {
		getMessageBox().addError(
			"Indicare l’identificativo del documento di incasso o la chiave del titolo di incasso");
	    }
	    try {
		if (!getMessageBox().hasError()) {
		    BigDecimal importoPagamento = getForm().getFatturaDetail().getIm_tot_da_pagare()
			    .parse();
		    BigDecimal idFatturaEnte = getForm().getFatturaDetail().getId_fattura_ente()
			    .parse();
		    OrgPagamFatturaEnteRowBean pagamFatturaEnteRowBean = new OrgPagamFatturaEnteRowBean();
		    getForm().getIncassoDetail().copyToBean(pagamFatturaEnteRowBean);
		    // Eseguo i controlli sugli importi prima di eseguire il pagamento
		    checkAndSaveIncasso(idFatturaEnte, importoPagamento, pagamFatturaEnteRowBean,
			    getForm().getListaIncassi().getStatus());
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
	    }
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_INCASSO);
    }

    private void checkAndSaveIncasso(BigDecimal idFatturaEnte, BigDecimal importoPagamento,
	    OrgPagamFatturaEnteRowBean pagamFatturaEnteRowBean, Status status)
	    throws ParerUserError, EMFError {
	BigDecimal importoIncassato = pagamFatturaEnteRowBean.getImPagam() != null
		? pagamFatturaEnteRowBean.getImPagam()
		: BigDecimal.ZERO;
	BigDecimal imTotPagam = getForm().getFatturaDetail().getIm_tot_pagam().parse() != null
		? getForm().getFatturaDetail().getIm_tot_pagam().parse()
		: BigDecimal.ZERO;

	switch (status) {
	case insert:
	    importoIncassato = importoIncassato.add(imTotPagam);
	    break;
	case update:
	    BigDecimal importoIncassatoPrecedente = ((OrgPagamFatturaEnteTableBean) getForm()
		    .getListaIncassi().getTable()).getCurrentRow().getImPagam();
	    if (importoIncassatoPrecedente == null) {
		importoIncassatoPrecedente = BigDecimal.ZERO;
	    }
	    importoIncassato = importoIncassato.subtract(importoIncassatoPrecedente)
		    .add(imTotPagam);
	    break;
	default:
	    throw new ParerUserError(
		    "Attenzione: errore durante il calcolo degli incassi, operazione annullata");
	}

	importoPagamento = importoPagamento != null ? importoPagamento : BigDecimal.ZERO;

	// Se ho cuccato più grana rispetto a quella dovuta, a malincuore lo segnalo
	if (importoPagamento.compareTo(importoIncassato) < 0) {
	    getForm().getSalvaIncassoCustomMessageButtonList().setEditMode();
	    getRequest().setAttribute("customBoxSalvataggioIncasso", true);
	} else if (importoPagamento.compareTo(importoIncassato) > 0) {
	    // PAGATA PARZIALMENTE
	    switch (status) {
	    case insert:
	    case update:
		ConstOrgStatoFatturaEnte.TiStatoFatturaEnte statoDaAssegnare = entiConvenzionatiEjb
			.getStatoFatturaDaAssegnareUpdate(idFatturaEnte);
		executeSaveIncasso(idFatturaEnte, pagamFatturaEnteRowBean, statoDaAssegnare);
		break;
	    default:
		throw new ParerUserError(
			"Attenzione: errore durante il calcolo degli incassi, operazione annullata");
	    }
	} else {
	    // PAGATA IL GIUSTO
	    executeSaveIncasso(idFatturaEnte, pagamFatturaEnteRowBean,
		    ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA);
	}
    }

    private void executeSaveIncasso(BigDecimal idFatturaEnte,
	    OrgPagamFatturaEnteRowBean pagamFatturaEnteRowBean,
	    ConstOrgStatoFatturaEnte.TiStatoFatturaEnte statoFatturaEnte)
	    throws ParerUserError, EMFError {
	/*
	 * Codice aggiuntivo per il logging...
	 */
	LogParam param = SpagoliteLogUtil.getLogParam(paramHelper.getParamApplicApplicationName(),
		getUser().getUsername(), SpagoliteLogUtil.getPageName(this));
	param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());

	if (getForm().getListaIncassi().getStatus().equals(Status.update)) {
	    param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
	    entiConvenzionatiEjb.updateIncasso(param, idFatturaEnte, pagamFatturaEnteRowBean,
		    statoFatturaEnte);
	} else {
	    param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
	    Long idPagamFatturaEnte = entiConvenzionatiEjb.insertIncasso(param, idFatturaEnte,
		    pagamFatturaEnteRowBean, statoFatturaEnte);
	    if (idPagamFatturaEnte != null) {
		pagamFatturaEnteRowBean.setIdPagamFatturaEnte(new BigDecimal(idPagamFatturaEnte));
	    }
	    getForm().getListaIncassi().getTable().last();
	    getForm().getListaIncassi().getTable().add(pagamFatturaEnteRowBean);
	}
	// Ricarico il dettaglio
	BigDecimal idPagamFatturaEnte = getForm().getIncassoDetail().getId_pagam_fattura_ente()
		.parse();
	if (idPagamFatturaEnte != null) {
	    loadDettaglioIncasso(idPagamFatturaEnte);
	}
	loadDettaglioFattura(idFatturaEnte);
	getForm().getIncassoDetail().setViewMode();
	getForm().getListaIncassi().setStatus(Status.view);
	getForm().getIncassoDetail().setStatus(Status.view);
	getMessageBox().addInfo("Pagamento incassato con successo");
	getMessageBox().setViewMode(MessageBox.ViewMode.plain);
    }

    @Override
    public void confermaSalvataggioIncasso() throws Throwable {
	OrgPagamFatturaEnteRowBean pagamFatturaEnteRowBean = new OrgPagamFatturaEnteRowBean();
	getForm().getIncassoDetail().copyToBean(pagamFatturaEnteRowBean);
	BigDecimal idFatturaEnte = ((OrgVRicFattureTableBean) getForm().getListaFatture()
		.getTable()).getCurrentRow().getIdFatturaEnte();
	executeSaveIncasso(idFatturaEnte, pagamFatturaEnteRowBean,
		ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA);
	forwardToPublisher(Application.Publisher.DETTAGLIO_INCASSO);
    }

    @Override
    public void annullaSalvataggioIncasso() throws Throwable {
	// Nascondo i bottoni con javascript disattivato
	getForm().getSalvaIncassoCustomMessageButtonList().setViewMode();
	forwardToPublisher(Application.Publisher.DETTAGLIO_INCASSO);
    }

    @Override
    public void updateListaIncassi() {
	try {
	    String tiStatoFatturaEnte = getForm().getFatturaDetail().getTi_stato_fattura_ente()
		    .parse();

	    if (tiStatoFatturaEnte.equals(
		    ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.STORNATA.getDescrizione())) {
		getMessageBox().addError(
			"Attenzione: la fattura è già stata pagata, dunque non è possibile eseguire alcuna modifica");
	    }

	    if (!getMessageBox().hasError()) {
		getForm().getIncassoDetail().setEditMode();
		getForm().getFatturaDetail().setViewMode();
		getForm().getIncassoDetail().setStatus(Status.update);
		getForm().getListaIncassi().setStatus(Status.update);
	    }

	} catch (EMFError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
    }

    @Override
    public void deleteListaIncassi() throws EMFError {
	BigDecimal idPagamFatturaEnte = ((OrgPagamFatturaEnteTableBean) getForm().getListaIncassi()
		.getTable()).getCurrentRow().getIdPagamFatturaEnte();
	BigDecimal idFatturaEnte = getForm().getFatturaDetail().getId_fattura_ente().parse();
	int riga = getForm().getListaIncassi().getTable().getCurrentRowIndex();
	try {
	    /*
	     * Codice aggiuntivo per il logging...
	     */
	    LogParam param = SpagoliteLogUtil.getLogParam(
		    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
		    SpagoliteLogUtil.getPageName(this));
	    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
	    if (param.getNomePagina().equalsIgnoreCase(Application.Publisher.DETTAGLIO_INCASSO)) {
		param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
	    } else {
		param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
			getForm().getListaIncassi()));
	    }

	    entiConvenzionatiEjb.deleteOrgPagamFatturaEnte(param, idFatturaEnte,
		    idPagamFatturaEnte);
	    getForm().getListaIncassi().getTable().remove(riga);
	    loadDettaglioFattura(idFatturaEnte);
	    getMessageBox().addInfo("Incasso eliminato con successo");
	    getMessageBox().setViewMode(ViewMode.plain);

	} catch (ParerUserError ex) {
	    getMessageBox()
		    .addError("L'incasso non pu\u00F2 essere eliminato: " + ex.getDescription());
	}

	if (!getMessageBox().hasError()
		&& getLastPublisher().equals(Application.Publisher.DETTAGLIO_INCASSO)) {
	    goBack();
	} else {
	    forwardToPublisher(getLastPublisher());
	}
    }

    private void initIncassoSectionsOpened() {
	getForm().getEnteConvenzionatoSection().setLoadOpened(false);
	getForm().getFatturaSection().setLoadOpened(true);
	getForm().getStatoFatturaSection().setLoadOpened(true);
	getForm().getDocumentoIncassoSection().setLoadOpened(true);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Gestione servizi fatturati">
    @Override
    public void updateListaServiziFatturati() throws EMFError {
	BigDecimal idFatturaEnte = getForm().getFatturaDetail().getId_fattura_ente().parse();
	String tiStatoFatturaEnte = getForm().getFatturaDetail().getTi_stato_fattura_ente().parse();
	// Importo totale fattura (comprensivo di iva)
	BigDecimal imTotFattura = getForm().getFatturaDetail().getIm_tot_fattura().parse() != null
		? getForm().getFatturaDetail().getIm_tot_fattura().parse()
		: BigDecimal.ZERO;
	// Importo servizio fatturato
	BigDecimal imValoreTariffa = getForm().getServizioFatturatoDetail().getIm_valore_tariffa()
		.parse() != null
			? getForm().getServizioFatturatoDetail().getIm_valore_tariffa().parse()
			: BigDecimal.ZERO;
	// Iva sull'importo del servizio fatturato
	BigDecimal imIva = getForm().getServizioFatturatoDetail().getIm_iva().parse() != null
		? getForm().getServizioFatturatoDetail().getIm_iva().parse()
		: BigDecimal.ZERO;
	// Importo servizio fatturato (netto + iva)
	BigDecimal imServizioFatturatoConIva = imValoreTariffa.add(imIva);
	// Importo complessivo pagato
	BigDecimal imTotPagam = getForm().getFatturaDetail().getIm_tot_pagam().parse() != null
		? getForm().getFatturaDetail().getIm_tot_pagam().parse()
		: BigDecimal.ZERO;

	if (tiStatoFatturaEnte
		.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.CALCOLATA.getDescrizione())
		|| ((tiStatoFatturaEnte
			.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA.getDescrizione())
			|| tiStatoFatturaEnte.equals(
				ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE
					.getDescrizione()))
			&& !entiConvenzionatiEjb.existsStatoFattura(idFatturaEnte,
				ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA.getDescrizione())
			&& (imTotFattura.subtract(imServizioFatturatoConIva)
				.compareTo(imTotPagam) < 0))) // Metto tutto
	// in
	// viewMode,
	// tranne i
	// campi
	// seguenti
	{
	    getForm().getServizioFatturatoDetail().setViewMode();
	    getForm().getServizioFatturatoDetail().getNm_servizio_fattura().setEditMode();
	    getForm().getServizioFatturatoDetail().getIm_netto().setEditMode();
	    getForm().getServizioFatturatoDetail().getCd_iva().setEditMode();
	    getForm().getServizioFatturatoDetail().getIm_iva().setEditMode();
	    getForm().getServizioFatturatoDetail().setStatus(Status.update);
	    getForm().getListaServiziFatturati().setStatus(Status.update);
	    forwardToPublisher(Application.Publisher.DETTAGLIO_SERVIZIO_FATTURATO);
	} else {
	    getMessageBox().addError(
		    "La fattura si trova in uno stato che non permette di modificare i servizi fatturati");
	}
    }

    private void saveServizioFatturato() throws EMFError {
	if (getForm().getServizioFatturatoDetail().postAndValidate(getRequest(), getMessageBox())) {
	    try {
		/*
		 * Codice aggiuntivo per il logging...
		 */
		LogParam param = SpagoliteLogUtil.getLogParam(
			paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			SpagoliteLogUtil.getPageName(this));
		param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		if (getForm().getListaServiziFatturati().getStatus().equals(Status.update)) {
		    param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());

		    BigDecimal idFatturaEnte = getForm().getFatturaDetail().getId_fattura_ente()
			    .parse();
		    BigDecimal idServizioFattura = ((OrgVLisServFatturaTableBean) getForm()
			    .getListaServiziFatturati().getTable()).getCurrentRow()
			    .getIdServizioFattura();
		    String nmServizioFatturato = getForm().getServizioFatturatoDetail()
			    .getNm_servizio_fattura().parse();
		    BigDecimal importoFatturato = getForm().getServizioFatturatoDetail()
			    .getIm_netto().parse();
		    String cdIva = getForm().getServizioFatturatoDetail().getCd_iva().parse();
		    BigDecimal imIva = getForm().getServizioFatturatoDetail().getIm_iva().parse();

		    entiConvenzionatiEjb.updateServizioFatturato(param, idFatturaEnte,
			    idServizioFattura, nmServizioFatturato, importoFatturato, cdIva, imIva);
		    // Ricarico il dettaglio servizio fatturato
		    loadDettaglioServizioFatturato(idServizioFattura);
		    getMessageBox().addInfo("Servizio fatturato modificato con successo");
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
	    }
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_SERVIZIO_FATTURATO);
    }

    @Override
    public void deleteListaServiziFatturati() throws EMFError {
	BigDecimal idFatturaEnte = getForm().getFatturaDetail().getId_fattura_ente().parse();
	String tiStatoFatturaEnte = getForm().getFatturaDetail().getTi_stato_fattura_ente().parse();
	// Importo totale fattura (comprensivo di iva)
	BigDecimal imTotFattura = getForm().getFatturaDetail().getIm_tot_fattura().parse() != null
		? getForm().getFatturaDetail().getIm_tot_fattura().parse()
		: BigDecimal.ZERO;
	// Importo servizio fatturato
	BigDecimal imValoreTariffa = getForm().getServizioFatturatoDetail().getIm_valore_tariffa()
		.parse() != null
			? getForm().getServizioFatturatoDetail().getIm_valore_tariffa().parse()
			: BigDecimal.ZERO;
	// Iva sull'importo del servizio fatturato
	BigDecimal imIva = getForm().getServizioFatturatoDetail().getIm_iva().parse() != null
		? getForm().getServizioFatturatoDetail().getIm_iva().parse()
		: BigDecimal.ZERO;
	// Importo servizio fatturato (netto + iva)
	BigDecimal imServizioFatturatoConIva = imValoreTariffa.add(imIva);
	// Importo complessivo pagato
	BigDecimal imTotPagam = getForm().getFatturaDetail().getIm_tot_pagam().parse() != null
		? getForm().getFatturaDetail().getIm_tot_pagam().parse()
		: BigDecimal.ZERO;

	// Condizione di cancellazione
	if (tiStatoFatturaEnte
		.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.CALCOLATA.getDescrizione())
		|| ((tiStatoFatturaEnte
			.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA.getDescrizione())
			|| tiStatoFatturaEnte.equals(
				ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE
					.getDescrizione()))
			&& !entiConvenzionatiEjb.existsStatoFattura(idFatturaEnte,
				ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA.getDescrizione())
			&& (imTotFattura.subtract(imServizioFatturatoConIva)
				.compareTo(imTotPagam) < 0))) {

	    BigDecimal idServizioFattura = ((OrgVLisServFatturaTableBean) getForm()
		    .getListaServiziFatturati().getTable()).getCurrentRow().getIdServizioFattura();
	    int riga = getForm().getListaServiziFatturati().getTable().getCurrentRowIndex();
	    try {
		/*
		 * Codice aggiuntivo per il logging...
		 */
		LogParam param = SpagoliteLogUtil.getLogParam(
			paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			SpagoliteLogUtil.getPageName(this));

		entiConvenzionatiEjb.deleteOrgServizioFattura(param, idFatturaEnte,
			idServizioFattura);
		getForm().getListaServiziFatturati().getTable().remove(riga);
		loadDettaglioFattura(idFatturaEnte);
		getMessageBox().addInfo("Servizio fatturato eliminato con successo");
		getMessageBox().setViewMode(ViewMode.plain);

	    } catch (ParerUserError ex) {
		getMessageBox().addError("Il servizio fatturato non pu\u00F2 essere eliminato: "
			+ ex.getDescription());
	    }

	} else {
	    getMessageBox().addError(
		    "La fattura si trova in uno stato che non permette di eliminare i servizi fatturati");
	}

	if (!getMessageBox().hasError()
		&& getLastPublisher().equals(Application.Publisher.DETTAGLIO_SERVIZIO_FATTURATO)) {
	    goBack();
	} else {
	    forwardToPublisher(getLastPublisher());
	}
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Gestione solleciti">
    private void initSollecitoSectionsOpened() {
	getForm().getEnteConvenzionatoSection().setLoadOpened(false);
	getForm().getFatturaSection().setLoadOpened(true);
	getForm().getStatoFatturaSection().setLoadOpened(true);
	getForm().getSollecitoSection().setLoadOpened(true);
    }

    private void loadDettaglioSollecito(BigDecimal idSollecitoFatturaEnte)
	    throws ParerUserError, EMFError {
	// Setto chiuse le seguenti sezioni
	initSollecitoSectionsOpened();

	// Dettaglio sollecito
	OrgSollecitoFatturaEnteRowBean sollecitoFatturaEnteRow = entiConvenzionatiEjb
		.getOrgSollecitoFatturaEnteRowBean(idSollecitoFatturaEnte);
	getForm().getSollecitoDetail().copyFromBean(sollecitoFatturaEnteRow);

	getForm().getSollecitoDetail().setViewMode();
	getForm().getFatturaDetail().setStatus(Status.view);
	getForm().getListaSolleciti().setStatus(Status.view);
    }

    private void saveSollecito() throws EMFError {
	if (getForm().getSollecitoDetail().postAndValidate(getRequest(), getMessageBox())) {
	    try {
		if (!getMessageBox().hasError()) {
		    BigDecimal idFatturaEnte = getForm().getFatturaDetail().getId_fattura_ente()
			    .parse();
		    String cdRegistroSollecito = getForm().getSollecitoDetail()
			    .getCd_registro_sollecito().parse();
		    BigDecimal aaVarSollecito = getForm().getSollecitoDetail().getAa_var_sollecito()
			    .parse();
		    String cdKeyVarSollecito = getForm().getSollecitoDetail()
			    .getCd_key_var_sollecito().parse();
		    Date dtSollecito = getForm().getSollecitoDetail().getDt_sollecito().parse();
		    String dlSollecito = getForm().getSollecitoDetail().getDl_sollecito().parse();

		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    if (getForm().getListaSolleciti().getStatus().equals(Status.insert)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
			Long idSollecitoFatturaEnte = entiConvenzionatiEjb.saveSollecito(param,
				idFatturaEnte, cdRegistroSollecito, aaVarSollecito,
				cdKeyVarSollecito, dtSollecito, dlSollecito);
			getForm().getSollecitoDetail().getId_sollecito_fattura_ente()
				.setValue(idSollecitoFatturaEnte.toString());
			OrgSollecitoFatturaEnteRowBean sollecitoFatturaEnteRowBean = new OrgSollecitoFatturaEnteRowBean();
			getForm().getSollecitoDetail().copyToBean(sollecitoFatturaEnteRowBean);
			getForm().getListaSolleciti().getTable().last();
			getForm().getListaSolleciti().getTable().add(sollecitoFatturaEnteRowBean);
		    } else if (getForm().getListaSolleciti().getStatus().equals(Status.update)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
			BigDecimal idSollecitoFatturaEnte = getForm().getSollecitoDetail()
				.getId_sollecito_fattura_ente().parse();
			entiConvenzionatiEjb.saveSollecito(param, idSollecitoFatturaEnte,
				idFatturaEnte, cdRegistroSollecito, aaVarSollecito,
				cdKeyVarSollecito, dtSollecito, dlSollecito);
		    }
		    BigDecimal idSollecitoFatturaEnte = getForm().getSollecitoDetail()
			    .getId_sollecito_fattura_ente().parse();
		    initSollecitoSectionsOpened();
		    loadDettaglioFattura(idFatturaEnte);
		    loadDettaglioSollecito(idSollecitoFatturaEnte);
		    getMessageBox().addInfo("Sollecito salvato con successo");
		    getMessageBox().setViewMode(ViewMode.plain);
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
	    }
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_SOLLECITO);
    }

    @Override
    public void deleteListaSolleciti() throws EMFError {
	BigDecimal idSollecitoFatturaEnte = ((OrgSollecitoFatturaEnteTableBean) getForm()
		.getListaSolleciti().getTable()).getCurrentRow().getIdSollecitoFatturaEnte();
	BigDecimal idFatturaEnte = getForm().getFatturaDetail().getId_fattura_ente().parse();
	int riga = getForm().getListaSolleciti().getTable().getCurrentRowIndex();
	int numRecordTabella = getForm().getListaSolleciti().getTable().size();

	try {
	    checkAndDeleteSollecito(idFatturaEnte, idSollecitoFatturaEnte, riga, numRecordTabella);
	} catch (ParerUserError ex) {
	    getMessageBox()
		    .addError("Il sollecito non pu\u00F2 essere eliminato: " + ex.getDescription());
	}

	if (!getMessageBox().hasError()
		&& getLastPublisher().equals(Application.Publisher.DETTAGLIO_SOLLECITO)
		&& getRequest().getAttribute("customBoxDeleteSollecito") == null) {
	    goBack();
	} else {
	    forwardToPublisher(getLastPublisher());
	}
    }

    private void checkAndDeleteSollecito(BigDecimal idFatturaEnte,
	    BigDecimal idSollecitoFatturaEnte, int riga, int numRecordTabella)
	    throws ParerUserError, EMFError {
	// Aggiorno lo stato della fattura in base ai nuovi totali

	if (entiConvenzionatiEjb.checkImportiPerSollecito(idFatturaEnte)) {
	    getRequest().setAttribute("customBoxDeleteSollecito", true);
	    Object[] deleteSollecitoData = new Object[3];
	    deleteSollecitoData[0] = idFatturaEnte;
	    deleteSollecitoData[1] = idSollecitoFatturaEnte;
	    deleteSollecitoData[2] = riga;
	    deleteSollecitoData[3] = numRecordTabella;
	    getSession().setAttribute("deleteSollecitoData", deleteSollecitoData);
	} else {
	    executeDeleteSollecito(idFatturaEnte, idSollecitoFatturaEnte, riga, numRecordTabella);
	}
    }

    private void executeDeleteSollecito(BigDecimal idFatturaEnte, BigDecimal idSollecitoFatturaEnte,
	    int riga, int numRecordTabella) throws ParerUserError, EMFError {
	/*
	 * Codice aggiuntivo per il logging...
	 */
	LogParam param = SpagoliteLogUtil.getLogParam(paramHelper.getParamApplicApplicationName(),
		getUser().getUsername(), SpagoliteLogUtil.getPageName(this));
	param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
	if (param.getNomePagina().equalsIgnoreCase(Application.Publisher.DETTAGLIO_SOLLECITO)) {
	    param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
	} else {
	    param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
		    getForm().getListaSolleciti()));
	}

	entiConvenzionatiEjb.deleteOrgSollecitoFatturaEnte(param, idFatturaEnte,
		idSollecitoFatturaEnte, numRecordTabella);
	getForm().getListaSolleciti().getTable().remove(riga);
	loadDettaglioFattura(idFatturaEnte);
	getMessageBox().addInfo("Sollecito fattura eliminato con successo");
	getMessageBox().setViewMode(ViewMode.plain);
    }

    @Override
    public void confermaDeleteSollecito() throws Throwable {
	if (getSession().getAttribute("deleteSollecitoData") != null) {
	    Object[] deleteSollecitoData = (Object[]) getSession()
		    .getAttribute("deleteSollecitoData");
	    BigDecimal idFatturaEnte = (BigDecimal) deleteSollecitoData[0];
	    BigDecimal idSollecitoFatturaEnte = (BigDecimal) deleteSollecitoData[1];
	    int riga = (int) deleteSollecitoData[2];
	    int numRecordTabella = (int) deleteSollecitoData[3];
	    executeDeleteSollecito(idFatturaEnte, idSollecitoFatturaEnte, riga, numRecordTabella);
	    getSession().removeAttribute("deleteSollecitoData");
	    forwardToPublisher(Application.Publisher.DETTAGLIO_FATTURA);
	}
    }

    @Override
    public void annullaDeleteSollecito() throws Throwable {
	// Nascondo i bottoni con javascript disattivato
	getForm().getDeleteSollecitoCustomMessageButtonList().setViewMode();
	forwardToPublisher(getLastPublisher());
    }

    @Override
    public void updateListaSolleciti() {
	getForm().getSollecitoDetail().setEditMode();
	getForm().getFatturaDetail().setViewMode();
	getForm().getSollecitoDetail().setStatus(Status.update);
	getForm().getListaSolleciti().setStatus(Status.update);
    }

    private void initSollecitoDetail() throws ParerUserError, EMFError {
	BigDecimal idAmbienteEnteConvenz = getForm().getFatturaDetail()
		.getId_ambiente_ente_convenz().parse();
	String nmEnteUnitaDocAccordo = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.NM_ENTE_UNITA_DOC_ACCORDO.name(),
		idAmbienteEnteConvenz, null, Constants.TipoIamVGetValAppart.AMBIENTEENTECONVENZ);
	String nmStrutUnitaDocAccordo = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.NM_STRUT_UNITA_DOC_ACCORDO.name(),
		idAmbienteEnteConvenz, null, Constants.TipoIamVGetValAppart.AMBIENTEENTECONVENZ);
	BigDecimal idOrganizApplic = amministrazioneUtentiHelper
		.getIdOrganizIamByParam(nmEnteUnitaDocAccordo, nmStrutUnitaDocAccordo);
	getForm().getSollecitoDetail().getCd_registro_sollecito()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getRegistriStrutturaTableBean(idOrganizApplic),
			"cd_registro", "cd_registro"));
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Riemetti fatture stornate">
    @Override
    public void riemettiFattureStornate() throws EMFError {
	// Pulizia campi
	getForm().getRiemissioneFattureFields().clear();
	String statoFattura = getForm().getFatturaDetail().getTi_stato_fattura_ente().parse();

	try {
	    getForm().getRiemissioneFattureFields().setEditMode();
	    if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_FATTURA)
		    && statoFattura.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.STORNATA
			    .getDescrizione())) {
		getForm().getRiemissioneFattureFields().getAnno_testata_storn()
			.setValue("" + Calendar.getInstance().get(Calendar.YEAR));
		getRequest().setAttribute("customRiemissioneFattureStornateMessageBox", true);
	    } else {
		getMessageBox().addError("Azione di riemissione fatture stornate non consentita");
	    }
	} catch (Exception e) {
	    getMessageBox().addError("Errore durante il calcolo delle fatture provvisorie");
	    getForm().getRiemissioneFattureFields().setViewMode();
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_FATTURA);
    }

    @Override
    public void confermaRiemissioneFattureStornate() throws EMFError {
	String annoTestataString = (String) getRequest().getParameter("annoTestata");
	BigDecimal annoCorrente = BigDecimal.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	BigDecimal annoTestata = null;
	String statoFattura = getForm().getFatturaDetail().getTi_stato_fattura_ente().parse();

	// Controllo sulla correttezza formale dei campi
	try {
	    annoTestata = new BigDecimal(annoTestataString);
	    if (annoTestata.longValue() < 1000L) {
		throw new NumberFormatException();
	    }
	    getForm().getRiemissioneFattureFields().getAnno_testata_storn()
		    .setValue("" + annoTestata);
	} catch (NumberFormatException e) {
	    getMessageBox().addError("Anno testata mancante o non formalmente non corretto <br>");
	    getRequest().setAttribute("customRiemissioneFattureStornateMessageBox", true);
	}

	if (!getMessageBox().hasError()) {

	    // Controlli vincoli sui campi
	    if (annoTestata.longValue() > annoCorrente.longValue()) {
		getMessageBox().addError(
			"L’anno della testata fattura non può essere successivo all’anno corrente");
		getRequest().setAttribute("customRiemissioneFattureStornateMessageBox", true);
	    }

	    if (!getMessageBox().hasError()) {
		LogParam param = SpagoliteLogUtil.getLogParam(
			paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			SpagoliteLogUtil.getPageName(this));
		param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		param.setNomeAzione(SpagoliteLogUtil.getButtonActionName(this.getForm(),
			this.getForm().getRiemissioneFattureFields(),
			this.getForm().getRiemissioneFattureFields().getRiemettiFattureStornate()
				.getName()));

		Long idFatturaProvvisoria = null;
		int numFattureCalcolate = 0;
		if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_FATTURA)
			&& statoFattura.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.STORNATA
				.getDescrizione())) {
		    BigDecimal idEnteConvenz = getForm().getFatturaDetail().getId_ente_convenz()
			    .parse();
		    BigDecimal idFatturaEnte = getForm().getFatturaDetail().getId_fattura_ente()
			    .parse();
		    idFatturaProvvisoria = entiConvenzionatiEjb.riemettiFatturaStornata(param,
			    idEnteConvenz, idFatturaEnte.longValue(), annoTestata);
		    if (idFatturaProvvisoria != null) {
			numFattureCalcolate++;
		    }
		}

		getMessageBox().addInfo("Totale fatture calcolate: " + numFattureCalcolate);
	    }
	}
    }

    @Override
    public void annullaRiemissioneFattureStornate() throws EMFError {
	// Nascondo i bottoni con javascript disattivato
	getForm().getRiemissioneFattureFields().setViewMode();
    }
    // </editor-fold>

    @Override
    public void logEventi() throws EMFError {
	GestioneLogEventiForm form = new GestioneLogEventiForm();
	form.getOggettoDetail().getNmApp().setValue(paramHelper.getParamApplicApplicationName());
	form.getOggettoDetail().getNm_tipo_oggetto()
		.setValue(SacerLogConstants.TIPO_OGGETTO_FATTURA);
	form.getOggettoDetail().getIdOggetto()
		.setValue(getForm().getFatturaDetail().getId_fattura_ente().getValue());
	redirectToAction(it.eng.parer.sacerlog.slite.gen.Application.Actions.GESTIONE_LOG_EVENTI,
		"?operation=inizializzaLogEventi", form);
    }

    @Override
    protected void postLoad() {
	super.postLoad();
	Object ogg = getForm();
	if (ogg instanceof GestioneFatturazioneServiziForm) {
	    GestioneFatturazioneServiziForm form = (GestioneFatturazioneServiziForm) ogg;
	    if (form.getListaFatture().getStatus().equals(Status.view)) {
		form.getFatturaDetail().getLogEventi().setEditMode();
		try {
		    if (form.getFatturaDetail().getTi_stato_fattura_ente().parse() != null) {
			if (form.getFatturaDetail().getTi_stato_fattura_ente().parse()
				.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.STORNATA
					.getDescrizione())) {
			    form.getRiemissioneFattureFields().getRiemettiFattureStornate()
				    .setEditMode();
			} else {
			    form.getRiemissioneFattureFields().getRiemettiFattureStornate()
				    .setViewMode();
			}

			if (form.getFatturaDetail().getTi_stato_fattura_ente().parse()
				.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA
					.getDescrizione())
				|| form.getFatturaDetail().getTi_stato_fattura_ente().parse()
					.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.SOLLECITATA
						.getDescrizione())) {
			    form.getListaSolleciti().setUserOperations(true, true, true, true);
			} else {
			    form.getListaSolleciti().setUserOperations(true, false, false, false);
			}
		    }
		} catch (EMFError ex) {
		    getMessageBox().addError(ex.getMessage());
		}
	    } else {
		form.getFatturaDetail().getLogEventi().setViewMode();
		form.getRiemissioneFattureFields().getRiemettiFattureStornate().setViewMode();
	    }

	    if (getForm().getFatturaDetail().getStatus() != null) {
		if (getForm().getFatturaDetail().getStatus().equals(Status.view)) {
		    try {
			if (getForm().getFatturaDetail().getCd_registro_emis_fattura_combo()
				.parse() != null
				&& getForm().getFatturaDetail().getAa_emiss_fattura()
					.parse() != null
				&& getForm().getFatturaDetail().getCd_emis_fattura()
					.parse() != null) {
			    getForm().getUdButtonList().getScaricaCompFileUdFatturaEmessa()
				    .setEditMode();
			} else {
			    getForm().getUdButtonList().getScaricaCompFileUdFatturaEmessa()
				    .setViewMode();
			}

			if (getForm().getFatturaDetail().getCd_registro_emis_nota_credito_combo()
				.parse() != null
				&& getForm().getFatturaDetail().getAa_emiss_nota_credito()
					.parse() != null
				&& getForm().getFatturaDetail().getCd_emis_nota_credito()
					.parse() != null) {
			    getForm().getUdButtonList().getScaricaCompFileUdStorno().setEditMode();
			} else {
			    getForm().getUdButtonList().getScaricaCompFileUdStorno().setViewMode();
			}
		    } catch (EMFError ex) {
			getMessageBox().addError(
				"Errore durante il caricamento del bottone Scarica file unità documentaria");
		    }
		} else {
		    getForm().getUdButtonList().getScaricaCompFileUdFatturaEmessa().setViewMode();
		    getForm().getUdButtonList().getScaricaCompFileUdStorno().setViewMode();
		}
	    }
	}
    }

    public void download() throws EMFError {
	actionLogger.debug(">>>DOWNLOAD");
	String filename = (String) getSession()
		.getAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_FILENAME.name());
	String path = (String) getSession()
		.getAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_FILEPATH.name());
	Boolean deleteFile = Boolean.parseBoolean((String) getSession()
		.getAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_DELETEFILE.name()));
	String contentType = (String) getSession()
		.getAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_CONTENTTYPE.name());
	if (path != null && filename != null) {
	    File fileToDownload = new File(path);
	    if (fileToDownload.exists()) {
		/*
		 * Definiamo l'output previsto che sarà  un file in formato zip di cui si occuperà 
		 * la servlet per fare il download
		 */
		OutputStream outUD = getServletOutputStream();
		getResponse().setContentType(
			StringUtils.isBlank(contentType) ? WebConstants.MIME_TYPE_GENERIC
				: contentType);
		getResponse().setHeader("Content-Disposition",
			"attachment; filename=\"" + filename);

		FileInputStream inputStream = null;
		try {
		    getResponse().setHeader("Content-Length",
			    String.valueOf(fileToDownload.length()));
		    inputStream = new FileInputStream(fileToDownload);
		    byte[] bytes = new byte[8000];
		    int bytesRead;
		    while ((bytesRead = inputStream.read(bytes)) != -1) {
			outUD.write(bytes, 0, bytesRead);
		    }
		    outUD.flush();
		} catch (IOException e) {
		    actionLogger.error("Eccezione nel recupero del documento ", e);
		    getMessageBox().addError("Eccezione nel recupero del documento");
		} finally {
		    IOUtils.closeQuietly(inputStream);
		    IOUtils.closeQuietly(outUD);
		    inputStream = null;
		    outUD = null;
		    freeze();
		}
		// Nel caso sia stato richiesto, elimina il file
		if (deleteFile) {
		    fileToDownload.delete();
		}
	    } else {
		getMessageBox()
			.addError("Errore durante il tentativo di download. File non trovato");
		forwardToPublisher(getLastPublisher());
	    }
	} else {
	    getMessageBox().addError("Errore durante il tentativo di download. File non trovato");
	    forwardToPublisher(getLastPublisher());
	}
	getSession().removeAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_FILENAME.name());
	getSession().removeAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_FILEPATH.name());
	getSession().removeAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_DELETEFILE.name());
	getSession().removeAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_CONTENTTYPE.name());
    }

    public void scaricaCompFileUd(BigDecimal idFatturaEnte, String registro, BigDecimal anno,
	    String numero) throws EMFError, ParerUserError {
	// Recupero da DB i parametri per la chiamata al ws
	String versione = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.VERSIONE_XML_RECUP_UD.name(), null, null,
		Constants.TipoIamVGetValAppart.APPLIC);
	String loginname = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.USERID_RECUP_UD.name(), null, null,
		Constants.TipoIamVGetValAppart.APPLIC);
	String password = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.PSW_RECUP_UD.name(), null, null,
		Constants.TipoIamVGetValAppart.APPLIC);
	Integer timeout = Integer.parseInt(paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.TIMEOUT_RECUP_UD.name(), null, null,
		Constants.TipoIamVGetValAppart.APPLIC));
	String url = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.URL_RECUP_UD.name(), null, null,
		Constants.TipoIamVGetValAppart.APPLIC);
	// String url = "http://localhost:8080/sacer/RecDIPUnitaDocumentariaSync";
	BigDecimal idAmbienteEnteConvenz = getForm().getFatturaDetail()
		.getId_ambiente_ente_convenz().parse();
	BigDecimal idEnteConvenz = getForm().getFatturaDetail().getId_ente_convenz().parse();
	// Creo il client di chiamata al ws rest impostando il timeout come parametro recuperato da
	// DB
	HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
	clientHttpRequestFactory.setConnectTimeout(timeout);
	RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
	try {
	    // Croo l'header della richiesta
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.MULTIPART_FORM_DATA);
	    // Creo i parametri della richiesta
	    MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();
	    multipartRequest.add("VERSIONE", versione);
	    multipartRequest.add("LOGINNAME", loginname);
	    multipartRequest.add("PASSWORD", password);
	    multipartRequest.add("XMLSIP", getXmlSip(idFatturaEnte, versione, loginname, registro,
		    anno, numero, idAmbienteEnteConvenz, idEnteConvenz));
	    // Creo la richiesta
	    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(
		    multipartRequest, header);
	    // Mi faccio restituire la risposta dalla chiamata al WS
	    HttpEntity<Resource> response = restTemplate.exchange(url, HttpMethod.POST,
		    requestEntity, Resource.class);
	    File temp = null;
	    // Recupero l'inputStream col flusso del file da scaricare dal corpo della response
	    InputStream is = response.getBody().getInputStream();
	    // Header RAW Content-Disposition:" attachment; filename="UD_DELIBERE DI
	    // GIUNTA-2018-1.zip"
	    // Dall'header recupero il Content-Disposition e successivamente da esso il filename
	    String fileName = response.getHeaders().getFirst("Content-Disposition");
	    MediaType contentType = response.getHeaders().getContentType();

	    if (contentType != null && "zip".equals(contentType.getSubtype())
		    && StringUtils.isNotBlank(fileName)) {
		fileName = fileName.substring(fileName.indexOf("\"") + 1, fileName.length());
		// Creo il file temporaneo
		temp = File.createTempFile("prefisso", "suffisso");
		// Leggo dall'inputStream e "riepio" il file temporaneo tramite outputstream
		try (InputStream inputStream = is;
			OutputStream outStream = new FileOutputStream(temp)) {
		    byte[] buffer = new byte[8 * 1024];
		    int bytesRead;
		    while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		    }
		}

		if (response == null) {
		    // caso KO
		    getMessageBox().addError("Errore durante il tentativo di download del file");
		} else {
		    // caso OK
		    getRequest().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_ACTION.name(),
			    getControllerName());
		    getSession().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_FILENAME.name(),
			    fileName);
		    getSession().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_FILEPATH.name(),
			    temp.getAbsolutePath());
		    getSession().setAttribute(
			    WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_DELETEFILE.name(),
			    Boolean.toString(true));
		}

		if (!getMessageBox().isEmpty()) {
		    forwardToPublisher(getLastPublisher());
		} else {
		    forwardToPublisher(Application.Publisher.DOWNLOAD_PAGE);
		}
	    } else {
		getMessageBox().addError(
			"Errore durante il tentativo di download del file: impossibile recuperare il nome file");
		// leggo l'input stream e recupero l'xml col messaggio di errore
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
		    String sCurrentLine;
		    while ((sCurrentLine = br.readLine()) != null) {
			sb.append(sCurrentLine);
		    }
		}
		int codiceErroreStart = sb.indexOf("<CodiceErrore>");
		int codiceErroreStop = sb.indexOf("</CodiceErrore>");
		int dsErroreStart = sb.indexOf("<MessaggioErrore>");
		int dsErroreStop = sb.indexOf("</MessaggioErrore>");
		String messaggioErrore = null;

		if (codiceErroreStart != -1 && codiceErroreStop != -1) {
		    messaggioErrore = sb.substring(codiceErroreStart + 14, codiceErroreStop);
		}
		if (dsErroreStart != -1 && dsErroreStop != -1) {
		    messaggioErrore = messaggioErrore + " - "
			    + sb.substring(dsErroreStart + 17, dsErroreStop);
		}
		if (messaggioErrore == null) {
		    messaggioErrore = "Errore durante il tentativo di download del file: impossibile recuperare il nome file";
		}
		getMessageBox().addError(messaggioErrore);
		forwardToPublisher(getLastPublisher());
	    }
	} catch (ResourceAccessException ex) {
	    getMessageBox()
		    .addError("Errore durante il tentativo di download del file: timeout scaduto");
	    forwardToPublisher(getLastPublisher());
	} catch (HttpClientErrorException ex) {
	    getMessageBox().addError("Errore durante la chiamata al ws per il download del file");
	    forwardToPublisher(getLastPublisher());
	} catch (EMFError | IOException | RestClientException ex) {
	    getMessageBox().addError("Errore durante il tentativo di download del file");
	    forwardToPublisher(getLastPublisher());
	}
    }

    private String getXmlSip(BigDecimal idFatturaEnte, String versione, String nmUserid,
	    String registro, BigDecimal anno, String numero, BigDecimal idAmbienteEnteConvenz,
	    BigDecimal idEnteConvenz) throws EMFError, ParerUserError {
	OrgVVisFatturaRowBean fatturaEnteRowBean = entiConvenzionatiEjb
		.getOrgVVisFatturaRowBean(idFatturaEnte);
	String nmEnteUnitaDocAccordo = fatturaEnteRowBean.getNmEnte();
	String nmStrutUnitaDocAccordo = fatturaEnteRowBean.getNmStrut();
	BigDecimal idOrganizApplic = amministrazioneUtentiHelper
		.getIdOrganizIamByParam(nmEnteUnitaDocAccordo, nmStrutUnitaDocAccordo);
	Map<String, String> org = entiConvenzionatiEjb.getUsrOrganizIamMap(idOrganizApplic);
	String xml = "<Recupero><Versione>" + versione + "</Versione><Versatore><Ambiente>"
		+ org.get("AMBIENTE") + "</Ambiente><Ente>" + org.get("ENTE") + "</Ente><Struttura>"
		+ org.get("STRUTTURA") + "</Struttura>" + "<UserID>" + nmUserid
		+ "</UserID></Versatore><Chiave><Numero>" + numero + "</Numero><Anno>" + anno
		+ "</Anno><TipoRegistro>" + registro + "</TipoRegistro></Chiave></Recupero>";
	return xml;
    }

    @Override
    public void scaricaCompFileUdFatturaEmessa() throws Throwable {
	String registro = getForm().getFatturaDetail().getCd_registro_emis_fattura_combo().parse();
	BigDecimal anno = getForm().getFatturaDetail().getAa_emiss_fattura().parse();
	String numero = getForm().getFatturaDetail().getCd_emis_fattura().parse();
	BigDecimal idFatturaEnte = getForm().getFatturaDetail().getId_fattura_ente().parse();
	scaricaCompFileUd(idFatturaEnte, registro, anno, numero);
    }

    @Override
    public void scaricaCompFileUdStorno() throws Throwable {
	String registro = getForm().getFatturaDetail().getCd_registro_emis_nota_credito_combo()
		.parse();
	BigDecimal anno = getForm().getFatturaDetail().getAa_emiss_nota_credito().parse();
	String numero = getForm().getFatturaDetail().getCd_emis_nota_credito().parse();
	BigDecimal idFatturaEnte = getForm().getFatturaDetail().getId_fattura_ente().parse();
	scaricaCompFileUd(idFatturaEnte, registro, anno, numero);
    }

    @Override
    public void cambiaStatoFatture() throws EMFError {
	// Ricavo gli id delle fatture da trattare
	Map<BigDecimal, Map<String, String>> fattureDaElaborare = new HashMap<>();
	getForm().getListaFatture().post(getRequest());
	OrgVRicFattureTableBean fattureTableBean = (OrgVRicFattureTableBean) getForm()
		.getListaFatture().getTable();
	if (fattureTableBean != null) {
	    for (OrgVRicFattureRowBean fatturaRowBean : fattureTableBean) {
		if (fatturaRowBean.getString("fl_fatture_sel") != null
			&& fatturaRowBean.getString("fl_fatture_sel").equals("1")) {
		    Map<String, String> cdFatturaStato = new HashMap<>();
		    cdFatturaStato.put(fatturaRowBean.getCdFattura(),
			    fatturaRowBean.getTiStatoFatturaEnte());
		    fattureDaElaborare.put(fatturaRowBean.getIdFatturaEnte(), cdFatturaStato);
		}
	    }
	}
	if (fattureDaElaborare.size() > 0) {

	    String messaggioWarning = "Attenzione! Le seguenti fatture non si trovano in stato CALCOLATA. Per esse non "
		    + "è stato effettuato alcun tipo di cambio stato: <br>";
	    String messaggioOK = "Le seguenti fatture sono state portate in stato EMESSA: <br>";
	    boolean almenoUnaNonCalcolata = false;
	    String listaNonCalcolate = "";
	    String listaCalcolate = "";
	    List<BigDecimal> idFattureEnteDaElaborare = new ArrayList<>();
	    for (Map.Entry<BigDecimal, Map<String, String>> entry : fattureDaElaborare.entrySet()) {
		Map<String, String> cdFatturaStato = entry.getValue();
		String stato = cdFatturaStato.get(cdFatturaStato.keySet().iterator().next());
		String cdFattura = cdFatturaStato.keySet().iterator().next();
		if (!stato.equals(
			ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.CALCOLATA.getDescrizione())) {
		    listaNonCalcolate = listaNonCalcolate + cdFattura + "<br> ";
		    almenoUnaNonCalcolata = true;
		} else {
		    listaCalcolate = listaCalcolate + cdFattura + "<br>";
		    idFattureEnteDaElaborare.add(entry.getKey());
		}
	    }

	    if (getMessageBox().isEmpty()) {
		entiConvenzionatiEjb.cambiaStatoFatture(idFattureEnteDaElaborare);
	    }

	    if (almenoUnaNonCalcolata) {
		String messaggio = messaggioWarning + listaNonCalcolate + "<br>";
		if (!idFattureEnteDaElaborare.isEmpty()) {
		    messaggio = messaggio + messaggioOK + listaCalcolate;
		}
		getMessageBox().addWarning(messaggio);
	    } else {
		getMessageBox().addInfo(messaggioOK + listaCalcolate);
	    }
	} else {
	    getMessageBox().addError(
		    "E’ necessario selezionare le fatture per le quali eseguire il cambio di stato");
	}
	ricercaFattura();
    }

    @Override
    public void estraiRigheFattureTotali() throws EMFError {
	BaseTable tabella = entiConvenzionatiEjb.getReportFattureCalcolate();
	getForm().getListaEstraiRigheFatture().setTable(tabella);

	SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");

	getForm().getListaEstraiRigheFatture().setTable(tabella);
	getForm().getListaEstraiRigheFatture()
		.setExcelFileName("FattureCalcolateTotali_" + df.format(new Date()));

	listNavigationOnClick(ListaEstraiRigheFatture.NAME, ListAction.NE_EXPORT_XLS, "-1",
		"false");
    }

    @Override
    public void estraiRigheFattureDaRicerca() throws EMFError {
	if (getForm().getFiltriFattura().postAndValidate(getRequest(), getMessageBox())) {
	    String nmEnteConvenz = getForm().getFiltriFattura().getNm_ente_convenz().parse();
	    String cdClienteSap = getForm().getFiltriFattura().getCd_cliente_sap().parse();
	    BigDecimal idTipoAccordo = getForm().getFiltriFattura().getId_tipo_accordo().parse();
	    BigDecimal idTipoServizio = getForm().getFiltriFattura().getId_tipo_servizio().parse();
	    BigDecimal aaFatturaDa = getForm().getFiltriFattura().getAa_fattura_da().parse();
	    BigDecimal aaFatturaA = getForm().getFiltriFattura().getAa_fattura_a().parse();
	    BigDecimal pgFatturaEnteDa = getForm().getFiltriFattura().getPg_fattura_ente_da()
		    .parse();
	    BigDecimal pgFatturaEnteA = getForm().getFiltriFattura().getPg_fattura_ente_a().parse();
	    String cdFattura = getForm().getFiltriFattura().getCd_fattura().parse();
	    String cdRegistroEmisFattura = getForm().getFiltriFattura()
		    .getCd_registro_emis_fattura().parse();
	    BigDecimal aaEmissFattura = getForm().getFiltriFattura().getAa_emiss_fattura().parse();
	    String cdEmisFattura = getForm().getFiltriFattura().getCd_emis_fattura().parse();
	    BigDecimal pgFatturaEmis = getForm().getFiltriFattura().getPg_fattura_emis().parse();
	    Date dtEmisFatturaDa = getForm().getFiltriFattura().getDt_emis_fattura_da().parse();
	    Date dtEmisFatturaA = getForm().getFiltriFattura().getDt_emis_fattura_a().parse();
	    Set<String> tiStatoFatturaEnte = getForm().getFiltriFattura().getTi_stato_fattura_ente()
		    .getDecodedValues();

	    BaseTable tabella = entiConvenzionatiEjb.getReportFattureCalcolateDaRicerca(
		    nmEnteConvenz, cdClienteSap, idTipoAccordo, idTipoServizio, aaFatturaDa,
		    aaFatturaA, pgFatturaEnteDa, pgFatturaEnteA, cdFattura, cdRegistroEmisFattura,
		    aaEmissFattura, cdEmisFattura, pgFatturaEmis, dtEmisFatturaDa, dtEmisFatturaA,
		    tiStatoFatturaEnte);

	    SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");

	    getForm().getListaEstraiRigheFatture().setTable(tabella);
	    getForm().getListaEstraiRigheFatture()
		    .setExcelFileName("FattureCalcolateDaRicerca_" + df.format(new Date()));

	    listNavigationOnClick(ListaEstraiRigheFatture.NAME, ListAction.NE_EXPORT_XLS, "-1",
		    "false");
	}
    }

}
