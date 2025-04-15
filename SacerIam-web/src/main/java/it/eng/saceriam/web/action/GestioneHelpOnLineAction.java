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

/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package it.eng.saceriam.web.action;

import static it.eng.spagoCore.ConfigProperties.StandardProperty.HELP_ONLINE_MAX_FILE_SIZE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import javax.ejb.EJB;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// import org.codehaus.jackson.map.ObjectMapper; // rimosso da FF
// (sostituito la dipendenza con com.fasterxml.jackson nel pom del modulo ejb)
import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.saceriam.common.Constants;
import it.eng.saceriam.entity.AplHelpOnLine;
import it.eng.saceriam.slite.gen.Application;
import it.eng.saceriam.slite.gen.action.GestioneHelpOnLineAbstractAction;
import it.eng.saceriam.slite.gen.form.GestioneHelpOnLineForm;
import it.eng.saceriam.slite.gen.tablebean.AplApplicRowBean;
import it.eng.saceriam.slite.gen.viewbean.AplVVisHelpOnLineRowBean;
import it.eng.saceriam.slite.gen.viewbean.AplVVisHelpOnLineTableBean;
import it.eng.saceriam.util.DateUtil;
import it.eng.saceriam.viewEntity.constraint.ConstAplVVisHelpOnLine;
import it.eng.saceriam.web.ejb.AmministrazioneUtentiEjb;
import it.eng.saceriam.web.ejb.ComboEjb;
import it.eng.saceriam.web.util.ComboGetter;
import it.eng.saceriam.web.util.HelpZipProcessor;
import it.eng.saceriam.web.util.WebConstants;
import it.eng.saceriam.ws.rest.ejb.RecuperoHelpEjb;
import it.eng.spagoCore.ConfigSingleton;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.actions.form.ListAction;
import it.eng.spagoLite.db.decodemap.DecodeMapIF;
import it.eng.spagoLite.db.oracle.decode.DecodeMap;
import it.eng.spagoLite.form.base.BaseElements.Status;
import it.eng.spagoLite.message.Message;
import it.eng.spagoLite.security.Secure;

/**
 *
 * @author Iacolucci_M
 */
public class GestioneHelpOnLineAction extends GestioneHelpOnLineAbstractAction {

    private static final Logger log = LoggerFactory.getLogger(GestioneHelpOnLineAction.class);

    private static final String MSG_ERROR_DOC_ZIP = "Il file ZIP deve contenere nella ROOT almeno un file .docx (o .doc), almeno un file .htm e opzionalmente una cartella con all'interno le immagini dell'help";

    @EJB(mappedName = "java:app/SacerIam-ejb/ComboEjb")
    private ComboEjb comboEjb;

    @EJB(mappedName = "java:app/SacerIam-ejb/AmministrazioneUtentiEjb")
    private AmministrazioneUtentiEjb amministrazioneUtentiEjb;

    @EJB(mappedName = "java:app/SacerIam-ejb/RecuperoHelpEjb")
    private RecuperoHelpEjb recuperoHelpEjb;

    @Override
    @Secure(action = "Menu.AmministrazioneSistema.GestioneHelpOnLine")
    public void initOnClick() throws EMFError {
	log.debug(">>>INIT-ON-CLICK");
	getUser().getMenu().reset();
	getUser().getMenu().select("Menu.AmministrazioneSistema.GestioneHelpOnLine");

	getForm().getFiltriHelpOnLine().reset();

	// getForm().getFiltriHelpOnLine().getNm_applic()
	// .setDecodeMap(comboGetter.getMappaApplicAbilitate(getUser().getIdUtente(), true, true));
	getForm().getFiltriHelpOnLine().getNm_applic().setDecodeMap(new DecodeMap());
	getForm().getFiltriHelpOnLine().getNm_applic().setEditMode();

	DecodeMapIF ogg2 = ComboGetter.getMappaSortedGenericEnum("tipoHelp",
		Constants.TiHelpOnLine.values());
	getForm().getFiltriHelpOnLine().getTipo_help().setDecodeMap(ogg2);
	getForm().getFiltriHelpOnLine().getTipo_help().setEditMode();

	getForm().getFiltriHelpOnLine().getDt_riferimento().setEditMode();
	getForm().getFiltriHelpOnLine().getDt_riferimento()
		.setValue(DateFormatUtils.format(new Date(), DateUtil.DATE_FORMAT_SLASH));

	getForm().getFiltriHelpOnLine().getPagina().setEditMode();
	getForm().getFiltriHelpOnLine().getNm_menu().setEditMode();

	getForm().getFiltriHelpOnLine().getRicercaHelpOnLine().setEditMode();
	getForm().getFiltriHelpOnLine().getPulisci().setEditMode();

	pulisciComboBoxELista();

	forwardToPublisher(getDefaultPublsherName());
    }

    /*
     * Cambio applicazione del filtro di ricerca gestione Help
     */
    @Override
    public JSONObject triggerFiltriHelpOnLineNm_applicOnTrigger() throws EMFError {
	log.debug(">>>TRIGGER-RICERCA-HELP");

	getForm().getFiltriHelpOnLine().post(getRequest());
	BigDecimal applic = getForm().getFiltriHelpOnLine().getNm_applic().parse();
	String tiHelpOnLine = getForm().getFiltriHelpOnLine().getTipo_help().parse();
	if (applic != null) {
	    AplApplicRowBean aplApplicById = amministrazioneUtentiEjb.getAplApplicRowBean(applic);

	    if (tiHelpOnLine.equals(Constants.TiHelpOnLine.INFO_PRIVACY.name())) {
		getForm().getFiltriHelpOnLine().getPagina().setDecodeMap(null);
	    } else {
		getForm().getFiltriHelpOnLine().getPagina().setDecodeMap(
			comboEjb.getMappaPaginePerApplicazione(applic, tiHelpOnLine, true));
		getForm().getFiltriHelpOnLine().getNm_menu().setDecodeMap(comboEjb
			.getMappaMenuUltimoLivelloPerApplSortedByDesc(aplApplicById.getIdApplic()));
	    }
	} else {
	    getForm().getFiltriHelpOnLine().getNm_applic().setValue("");
	}
	pulisciComboBoxELista();
	return getForm().getFiltriHelpOnLine().asJSON();
    }

    private void pulisciComboBoxELista() {
	// getForm().getFiltriHelpOnLine().getTipo_help().setValue("");
	getForm().getFiltriHelpOnLine().getNm_menu().setValue("");
	getForm().getFiltriHelpOnLine().getPagina().setValue("");
	getForm().getListaHelpOnLine().clear();
    }

    private void pulisciComboBoxDettaglio() {
	// getForm().getDettaglioHelpOnLine().getTi_help_on_line().setValue("");
	getForm().getDettaglioHelpOnLine().getNm_entry_menu().setValue("");
	getForm().getDettaglioHelpOnLine().getNm_pagina().setValue("");
    }

    @Override
    public void ricercaHelpOnLine() throws EMFError {
	log.debug(">>>RICERCA HELP ON LINE");

	if (getForm().getFiltriHelpOnLine().postAndValidate(getRequest(), getMessageBox())) {
	    String idApplicazione = getForm().getFiltriHelpOnLine().getNm_applic().getValue();
	    String idTipoHelp = getForm().getFiltriHelpOnLine().getTipo_help().getValue();
	    String idPaginaWeb = getForm().getFiltriHelpOnLine().getPagina().getValue();
	    String idEntryMenu = getForm().getFiltriHelpOnLine().getNm_menu().getValue();
	    Date dataRiferimento = null;
	    if (getForm().getFiltriHelpOnLine().getDt_riferimento() != null) {
		String dtRiferimento = getForm().getFiltriHelpOnLine().getDt_riferimento()
			.getValue();
		try {
		    dataRiferimento = DateUtils.parseDate(dtRiferimento, new String[] {
			    DateUtil.DATE_FORMAT_SLASH });
		} catch (ParseException ex) {
		}
	    }

	    AplVVisHelpOnLineTableBean tb = recuperoHelpEjb.getAplVVisHelpOnLineTableBean(
		    new BigDecimal(idApplicazione), idTipoHelp, dataRiferimento,
		    (idPaginaWeb == null || idPaginaWeb.trim().equals("")) ? null
			    : new BigDecimal(idPaginaWeb),
		    (idEntryMenu == null || idEntryMenu.trim().equals("")) ? null
			    : new BigDecimal(idEntryMenu));
	    getForm().getListaHelpOnLine().setTable(tb);
	    getForm().getListaHelpOnLine().getTable().setPageSize(10);
	    getForm().getListaHelpOnLine().getTable().first();
	}
	// Resto sulla pagina di gestione
	forwardToPublisher(Application.Publisher.GESTIONE_HELP_ON_LINE);
    }

    /*
     * Cambio tipo help abilito pagina e/o menu pulendo i relativi campi...
     */
    @Override
    public JSONObject triggerFiltriHelpOnLineTipo_helpOnTrigger() throws EMFError {
	log.debug(">>>TRIGGER-RICERCA-HELP-TIPO");
	getForm().getFiltriHelpOnLine().post(getRequest());

	String tiHelpOnLine = getForm().getFiltriHelpOnLine().getTipo_help().getValue();
	getForm().getFiltriHelpOnLine().getNm_applic().clear();

	if (tiHelpOnLine.equals("INFO_PRIVACY")) {
	    getForm().getFiltriHelpOnLine().getNm_applic().setDecodeMap(
		    comboEjb.getMappaApplicAbilitateConPaginaInfoPrivacy(getUser().getIdUtente(),
			    true, true));
	} else {
	    getForm().getFiltriHelpOnLine().getNm_applic().setDecodeMap(
		    comboEjb.getMappaApplicAbilitate(getUser().getIdUtente(), true, true));
	}

	getForm().getFiltriHelpOnLine().getPagina().setValue("");
	getForm().getFiltriHelpOnLine().getNm_menu().setValue("");
	return getForm().getFiltriHelpOnLine().asJSON();
    }

    @Override
    public JSONObject triggerFiltriHelpOnLinePaginaOnTrigger() throws EMFError {
	log.debug(">>>TRIGGER-RICERCA-HELP-PAGINA");
	getForm().getFiltriHelpOnLine().post(getRequest());
	return getForm().getFiltriHelpOnLine().asJSON();
    }

    @Override
    public JSONObject triggerFiltriHelpOnLineNm_menuOnTrigger() throws EMFError {
	log.debug(">>>TRIGGER-RICERCA-HELP-MENU");
	getForm().getFiltriHelpOnLine().post(getRequest());
	return getForm().getFiltriHelpOnLine().asJSON();
    }

    @Override
    public void pulisci() throws EMFError {
	log.debug(">>>PULISCI");
	throw new UnsupportedOperationException("Not supported yet."); // To change body of
								       // generated methods, choose
	// Tools | Templates.
    }

    @Override
    public void insertDettaglio() throws EMFError {
	log.debug(">>>INSERT-DETTAGLIO");

	// tolgo l'eventuale readonly sui campi non modificabili in fase di UPDATE
	getForm().getDettaglioHelpOnLine().getNm_applic().setReadonly(true);
	getForm().getDettaglioHelpOnLine().getTi_help_on_line().setReadonly(true);
	getForm().getDettaglioHelpOnLine().getNm_pagina().setReadonly(false);
	getForm().getDettaglioHelpOnLine().getNm_entry_menu().setReadonly(false);
	getForm().getDettaglioHelpOnLine().getDownloadFile().setViewMode();
	getForm().getListaHelpOnLine().setStatus(Status.insert);
	getForm().getDettaglioHelpOnLine().setStatus(Status.insert);
	getForm().getListaHelpOnLine().add(new AplApplicRowBean());

	// }
    }

    public void customLabel() throws EMFError {
	if (getForm().getFiltriHelpOnLine().getTipo_help().parse() != null
		&& getForm().getFiltriHelpOnLine().getTipo_help().parse().equals("INFO_PRIVACY")) {
	    getForm().getDettaglioHelpOnLine().setDescription("Dettaglio info privacy");
	    getForm().getDettaglioHelpOnLine().getPreview().setDescription("Preview info privacy");
	    getForm().getDettaglioHelpOnLine().getDs_file_help_online()
		    .setDescription("Nome file privacy");
	    getForm().getDettaglioHelpOnLine().getDs_file_zip_help_online()
		    .setDescription("File zip contenente l'informativa privacy");
	    getSession().setAttribute("tiHelpOnLine", "INFO_PRIVACY");
	    getSession().setAttribute("titlePopup", "Informativa sulla privacy");
	} else {
	    getForm().getDettaglioHelpOnLine().setDescription("Dettaglio help online");
	    getForm().getDettaglioHelpOnLine().getPreview().setDescription("Preview Help online");
	    getForm().getDettaglioHelpOnLine().getDs_file_help_online()
		    .setDescription("Nome file help");
	    getForm().getDettaglioHelpOnLine().getDs_file_zip_help_online()
		    .setDescription("File zip contenente l'help online");
	    getSession().setAttribute("tiHelpOnLine", "HELP_ONLINE");
	    getSession().setAttribute("titlePopup", "Help On Line");
	}
    }

    @Override
    public void loadDettaglio() throws EMFError {
	log.debug(">>>LOAD-DETTAGLIO");
	getForm().getDettaglioHelpOnLine().getDs_file_help_online().setReadonly(true);
	if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_INSERT)) {
	    if (getTableName().equals(getForm().getListaHelpOnLine().getName())) {
		log.debug("SI PROVIENE DALLA LISTA HELP ON LINE");
		getForm().getDettaglioHelpOnLine().reset();
		initInsertUpdate();
		pulisciComboBoxDettaglio();
		customLabel();
		getForm().getDettaglioHelpOnLine().getDs_file_zip_help_online().setHidden(false);
	    }
	} else if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)) {
	    if (getTableName().equals(getForm().getListaHelpOnLine().getName())) {
		log.debug("SI PROVIENE DALLA LISTA HELP ON LINE");
		initInsertUpdate();
		customLabel();
		AplVVisHelpOnLineRowBean bean = (AplVVisHelpOnLineRowBean) getForm()
			.getListaHelpOnLine().getTable().getCurrentRow();
		AplVVisHelpOnLineRowBean help = recuperoHelpEjb
			.findVistaById(bean.getIdHelpOnLine());
		GestioneHelpOnLineForm.DettaglioHelpOnLine dett = getForm()
			.getDettaglioHelpOnLine();
		if (help != null) {
		    dett.getNm_pagina().setValue(bean.getIdPaginaWeb().toString());
		    dett.getTi_help_on_line().setValue(bean.getTiHelpOnLine());
		    if (bean.getIdEntryMenu() != null) {
			dett.getNm_entry_menu().setValue("" + bean.getIdEntryMenu());
		    }
		    dett.getDt_ini_val().setValue(DateUtil.formatDateWithSlash(bean.getDtIniVal()));
		    dett.getDt_fine_val()
			    .setValue(DateUtil.formatDateWithSlash(bean.getDtFineVal()));
		    dett.getDs_file_help_online().setValue(bean.getDsFileHelpOnLine());
		}
		dett.getNm_applic().setReadonly(true);
		dett.getTi_help_on_line().setReadonly(true);
		dett.getNm_pagina().setReadonly(true);
		dett.getNm_entry_menu().setReadonly(true);
		getForm().getDettaglioHelpOnLine().getDs_file_zip_help_online().setHidden(false);
		getForm().getDettaglioHelpOnLine().getDownloadFile().setEditMode();
		getForm().getDettaglioHelpOnLine().getPreview().setEditMode();
	    }
	} else if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW)) {
	    if (getTableName().equals(getForm().getListaHelpOnLine().getName())) {
		log.debug("SI PROVIENE DALLA LISTA HELP ON LINE");
		initInsertUpdate();
		customLabel();
		AplVVisHelpOnLineRowBean bean = (AplVVisHelpOnLineRowBean) getForm()
			.getListaHelpOnLine().getTable().getCurrentRow();
		AplVVisHelpOnLineRowBean help = recuperoHelpEjb
			.findVistaById(bean.getIdHelpOnLine());
		if (help != null) {
		    GestioneHelpOnLineForm.DettaglioHelpOnLine dett = getForm()
			    .getDettaglioHelpOnLine();
		    dett.getNm_applic().setValue(
			    getForm().getFiltriHelpOnLine().getNm_applic().getDecodedValue());
		    dett.getNm_pagina().setValue(bean.getIdPaginaWeb().toString());
		    dett.getTi_help_on_line().setValue(bean.getTiHelpOnLine());
		    if (bean.getIdEntryMenu() != null) {
			dett.getNm_entry_menu().setValue("" + bean.getIdEntryMenu());
		    }
		    dett.getDt_ini_val().setValue(DateUtil.formatDateWithSlash(bean.getDtIniVal()));
		    dett.getDt_fine_val()
			    .setValue(DateUtil.formatDateWithSlash(bean.getDtFineVal()));
		    dett.getDs_file_help_online().setValue(bean.getDsFileHelpOnLine());
		}
		getForm().getListaHelpOnLine().setStatus(Status.view);
		getForm().getDettaglioHelpOnLine().setStatus(Status.view);
		getForm().getDettaglioHelpOnLine().setViewMode();
		getForm().getDettaglioHelpOnLine().getPreview().setEditMode();
		getForm().getDettaglioHelpOnLine().getDownloadFile().setEditMode();
		getForm().getDettaglioHelpOnLine().getDs_file_zip_help_online().setHidden(true);
	    }
	}
    }

    private void initInsertUpdate() throws EMFError {
	getForm().getDettaglioHelpOnLine().setEditMode();
	getForm().getDettaglioHelpOnLine().reset();
	DecodeMapIF ogg2 = ComboGetter.getMappaSortedGenericEnum("tipoHelp",
		Constants.TiHelpOnLine.values());
	getForm().getDettaglioHelpOnLine().getTi_help_on_line().setDecodeMap(ogg2);
	BigDecimal idAppl = new BigDecimal(
		getForm().getFiltriHelpOnLine().getNm_applic().getValue());
	getForm().getDettaglioHelpOnLine().getNm_pagina()
		.setDecodeMap(comboEjb.getMappaPaginePerApplicazione(idAppl, true));
	getForm().getDettaglioHelpOnLine().getNm_entry_menu()
		.setDecodeMap(comboEjb.getMappaMenuUltimoLivelloPerApplSortedByDesc(idAppl));
	getForm().getDettaglioHelpOnLine().getNm_applic()
		.setValue(getForm().getFiltriHelpOnLine().getNm_applic().getDecodedValue());
	getForm().getDettaglioHelpOnLine().getTi_help_on_line()
		.setValue(getForm().getFiltriHelpOnLine().getTipo_help().getDecodedValue());
    }

    @Override
    public void undoDettaglio() throws EMFError {
	if (getTableName().equals(getForm().getListaHelpOnLine().getName())) {
	    if (getForm().getListaHelpOnLine().getStatus().equals(Status.insert)) {
		getForm().getListaHelpOnLine().getTable()
			.remove(getForm().getListaHelpOnLine().getTable().getCurrentRowIndex());
		goBack();
	    } else if (getForm().getListaHelpOnLine().getStatus().equals(Status.update)) {
		getForm().getListaHelpOnLine().setStatus(Status.view);
	    }
	}
    }

    @Override
    public void saveDettaglio() throws EMFError {
	log.debug("SAVE-DETTAGLIO!");
    }

    @Override
    public void dettaglioOnClick() throws EMFError {
	log.debug(">>>DETTAGLIO-ON-CLICK");
	if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_INSERT)) {
	    // Se si proviene dalla lista help...
	    if (getTableName().equals(getForm().getListaHelpOnLine().getName())) {
		forwardToPublisher(Application.Publisher.DETTAGLIO_HELP_ON_LINE);
	    }
	} else if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_DELETE)) {
	    // Se si proviene dalla lista help...
	    if (getTableName().equals(getForm().getListaHelpOnLine().getName())) {
		int riga = getForm().getListaHelpOnLine().getTable().getCurrentRowIndex();
		AplVVisHelpOnLineRowBean bean = (AplVVisHelpOnLineRowBean) getForm()
			.getListaHelpOnLine().getTable().getRow(riga);
		BigDecimal chiave = bean.getIdHelpOnLine();
		recuperoHelpEjb.cancellaHelp(chiave);
		getForm().getListaHelpOnLine().getTable().remove(riga);
		if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_HELP_ON_LINE)) {
		    goBack();
		} else {
		    // prende il default publisher
		}
	    }
	} else if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)) {
	    // Se si proviene dalla lista help...
	    if (getTableName().equals(getForm().getListaHelpOnLine().getName())) {
		forwardToPublisher(Application.Publisher.DETTAGLIO_HELP_ON_LINE);
	    }
	} else if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW)) {
	    // Se si proviene dalla lista help...
	    if (getTableName().equals(getForm().getListaHelpOnLine().getName())) {
		forwardToPublisher(Application.Publisher.DETTAGLIO_HELP_ON_LINE);
	    }
	}

    }

    @Override
    public void elencoOnClick() throws EMFError {
	log.debug(">>>ELENCO-ON-CLICK");
	ripristinaValoriOriginariInUpdateOPreview();
	goBack();
    }

    @Override
    protected String getDefaultPublsherName() {
	log.debug(">>>DEFAULT-PUBLISHER-NAME");
	return Application.Publisher.GESTIONE_HELP_ON_LINE;
    }

    @Override
    public void process() throws EMFError {
	log.debug(">>>PROCESS");
	boolean isMultipart = ServletFileUpload.isMultipartContent(getRequest());
	if (isMultipart) {
	    if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_HELP_ON_LINE)) {
		try {
		    int fileSize = ConfigSingleton.getInstance()
			    .getIntValue(HELP_ONLINE_MAX_FILE_SIZE.name());
		    String[] paramReturn = getForm().getDettaglioHelpOnLine()
			    .postMultipart(getRequest(), fileSize);
		    /*
		     * gestione del tasto preview
		     */
		    if (paramReturn != null && paramReturn.length > 0
			    && paramReturn[0].equals("preview")) {

			// Altrimenti lo prende dalla multipart
			byte[] fileBlob = getForm().getDettaglioHelpOnLine()
				.getDs_file_zip_help_online().getFileBytes();
			// Nel caso si chiede la preview dell'help in modificama non si fornisce lo
			// zip
			// l'help viene preso da db
			if (fileBlob == null && getForm().getDettaglioHelpOnLine().getStatus()
				.equals(Status.update)) {
			    preview();
			    ripristinaValoriOriginariInUpdateOPreview();
			} else {
			    ripristinaValoriOriginariInUpdateOPreview();
			    HelpZipProcessor proc = new HelpZipProcessor(fileBlob);
			    if (proc.processa()) {
				if (proc.getHelpHtml() == null) {
				    // caso in cui l'utente clicca su preview senza fornire il file
				    // da caricare
				    getMessageBox().addError(MSG_ERROR_DOC_ZIP);
				} else {
				    ObjectMapper om = new ObjectMapper();
				    String arr = om.convertValue(
					    proc.getHelpHtml().getBytes(StandardCharsets.UTF_8),
					    String.class);
				    getRequest().setAttribute("Preview_hidden_field", arr);
				    // getRequest().setAttribute("Preview_hidden_field",
				    // proc.getBase64EncodedHelpHtml());
				    getForm().getDettaglioHelpOnLine().getDs_file_help_online()
					    .setValue(proc.getDocFileName());
				}
			    } else {
				getMessageBox().addError(MSG_ERROR_DOC_ZIP);
			    }
			}
			forwardToPublisher(Application.Publisher.DETTAGLIO_HELP_ON_LINE);
		    } else if (paramReturn != null && paramReturn.length > 0
			    && paramReturn[0].equals("downloadFile")) {
			// Gestione del download del file nel caso di modalità modifica
			downloadFile();

		    } else if (paramReturn != null && paramReturn.length > 1
			    && paramReturn[2].equals(ListAction.NE_DETTAGLIO_CANCEL)) {
			/*
			 * Se ero in inserimento cancello la riga che avevo inserito nella lista,
			 * altrimenti quando torno indietro la trovo vuota!
			 */
			if (getForm().getListaHelpOnLine().getStatus().equals(Status.insert)) {
			    getForm().getListaHelpOnLine().remove(
				    getForm().getListaHelpOnLine().getTable().getCurrentRowIndex());
			}
			getForm().getDettaglioHelpOnLine().reset();
			goBack();

		    } else {
			if (getForm().getDettaglioHelpOnLine().getStatus().equals(Status.update)) {
			    String tmpIdApplic = "" + getForm().getListaHelpOnLine().getTable()
				    .getCurrentRow().getBigDecimal("id_applic").longValueExact();
			    getForm().getDettaglioHelpOnLine().getNm_applic().setValue(tmpIdApplic);
			}
			if (validaDati()) {
			    /*
			     * Gestione del salvataggio (Modifica o Inserimento)
			     */
			    BigDecimal idApplic = getForm().getFiltriHelpOnLine().getNm_applic()
				    .parse();
			    String idTipoHelp = getForm().getDettaglioHelpOnLine()
				    .getTi_help_on_line().parse();
			    BigDecimal idPagina = getForm().getDettaglioHelpOnLine().getNm_pagina()
				    .parse();
			    BigDecimal idMenu = getForm().getDettaglioHelpOnLine()
				    .getNm_entry_menu().parse();
			    Date dataIni = getForm().getDettaglioHelpOnLine().getDt_ini_val()
				    .parse();
			    Date dataFine = getForm().getDettaglioHelpOnLine().getDt_fine_val()
				    .parse();

			    byte[] fileBlob = getForm().getDettaglioHelpOnLine()
				    .getDs_file_zip_help_online().getFileBytes();
			    long longApplic = idApplic == null ? 0 : idApplic.longValue();
			    long longPagina = idPagina == null ? 0 : idPagina.longValue();

			    if (idTipoHelp.equals(Constants.TiHelpOnLine.INFO_PRIVACY.name())) {
				longPagina = recuperoHelpEjb
					.getIdPaginaWebInfoPrivacy(idApplic.longValue());
			    }

			    long longMenu = idMenu == null ? 0 : idMenu.longValue();

			    HelpZipProcessor proc = new HelpZipProcessor(fileBlob);
			    if (proc.processa()) {
				AplHelpOnLine rigaProcessata = null;
				// se si sta salvando la modifica (tasto "save") si va in modifica
				// del record...
				if (paramReturn.length > 2
					&& paramReturn[2].equals(ListAction.NE_DETTAGLIO_SAVE)) {
				    if (getForm().getDettaglioHelpOnLine().getStatus()
					    .equals(Status.update)) {
					// MODIFICA DETTAGLIO
					AplVVisHelpOnLineRowBean bean = (AplVVisHelpOnLineRowBean) getForm()
						.getListaHelpOnLine().getTable().getCurrentRow();
					BigDecimal idHelp = bean.getIdHelpOnLine();
					rigaProcessata = recuperoHelpEjb.modificaHelp(idHelp,
						proc.getDocFileName(), dataIni, dataFine,
						proc.getHelpHtml(), proc.getDocSorgenteHelp());

				    } else {
					// INSERIMENTO DETTAGLIO
					rigaProcessata = recuperoHelpEjb.inserisciHelp(longApplic,
						idTipoHelp, proc.getDocFileName(), longPagina,
						longMenu, dataIni, dataFine, proc.getHelpHtml(),
						proc.getDocSorgenteHelp());
				    }
				} else if (paramReturn.length > 2
					&& paramReturn[2].equals(ListAction.NE_DETTAGLIO_DELETE)) {
				    // Altrimenti cancella il record
				    AplVVisHelpOnLineRowBean bean = (AplVVisHelpOnLineRowBean) getForm()
					    .getListaHelpOnLine().getTable().getCurrentRow();
				    BigDecimal chiave = bean.getIdHelpOnLine();
				    recuperoHelpEjb.cancellaHelp(chiave);
				    getForm().getListaHelpOnLine().getTable().remove();
				}
				// in tutti i casi diversi dalla cancellazione...
				if (paramReturn.length > 2
					&& !paramReturn[2].equals(ListAction.NE_DETTAGLIO_DELETE)) {
				    AplVVisHelpOnLineRowBean bean = (AplVVisHelpOnLineRowBean) getForm()
					    .getListaHelpOnLine().getTable().getCurrentRow();
				    // bean.entityToRowBean(rigaInserita);
				    bean.setIdApplic(new BigDecimal(
					    rigaProcessata.getAplApplic().getIdApplic()));
				    bean.setDtIniVal(
					    new Timestamp(rigaProcessata.getDtIniVal().getTime()));
				    bean.setDtFineVal(
					    new Timestamp(rigaProcessata.getDtFineVal().getTime()));
				    bean.setIdPaginaWeb(new BigDecimal(
					    rigaProcessata.getAplPaginaWeb().getIdPaginaWeb()));
				    bean.setDsPaginaWeb(
					    rigaProcessata.getAplPaginaWeb().getDsPaginaWeb());
				    bean.setDlCompositoEntryMenu(idMenu == null ? ""
					    : rigaProcessata.getAplEntryMenu().getDsEntryMenu());
				    bean.setIdEntryMenu(
					    rigaProcessata.getAplEntryMenu() == null ? null
						    : new BigDecimal(rigaProcessata
							    .getAplEntryMenu().getIdEntryMenu()));
				    bean.setTiHelpOnLine(rigaProcessata.getTiHelpOnLine());
				    bean.setIdHelpOnLine(
					    new BigDecimal(rigaProcessata.getIdHelpOnLine()));
				    bean.setDsFileHelpOnLine(rigaProcessata.getDsFileHelpOnLine());
				    /*
				     * Nel caso di modifica ripopola il dettaglio che altrimenti
				     * sarebbe vuoto perchè alcuni campi sono in readonly.
				     */
				    if (getForm().getDettaglioHelpOnLine().getStatus()
					    .equals(Status.update)) {
					getForm().getDettaglioHelpOnLine().getNm_applic().setValue(
						"" + rigaProcessata.getAplApplic().getIdApplic());
					getForm().getDettaglioHelpOnLine().getNm_pagina()
						.setValue(bean.getIdPaginaWeb().toString());
					if (bean.getIdEntryMenu() != null && !bean.getIdEntryMenu()
						.toString().trim().equals("")) {
					    getForm().getDettaglioHelpOnLine().getNm_entry_menu()
						    .setValue(bean.getNmEntryMenu().toString());
					}
					getForm().getDettaglioHelpOnLine().getTi_help_on_line()
						.setValue(rigaProcessata.getTiHelpOnLine());
					if (bean.getIdEntryMenu() != null) {
					    getForm().getDettaglioHelpOnLine().getNm_entry_menu()
						    .setValue("" + bean.getIdEntryMenu());
					}
					getForm().getDettaglioHelpOnLine()
						.getDs_file_zip_help_online().setHidden(true);
				    } else {
					getForm().getDettaglioHelpOnLine().getDs_file_help_online()
						.setValue(rigaProcessata.getDsFileHelpOnLine());
				    }
				    // bean.setNmApplic(nmApplic);
				    // Imposta lo stato in viewmode
				    getForm().getListaHelpOnLine().setStatus(Status.view);
				    getForm().getDettaglioHelpOnLine().setStatus(Status.view);
				    getForm().getDettaglioHelpOnLine().setViewMode();
				    getForm().getDettaglioHelpOnLine().getDs_file_zip_help_online()
					    .setHidden(true);
				    getForm().getDettaglioHelpOnLine().getDownloadFile()
					    .setEditMode();
				    getForm().getDettaglioHelpOnLine().getPreview().setEditMode();
				    forwardToPublisher(
					    Application.Publisher.DETTAGLIO_HELP_ON_LINE);
				}
			    } else {
				ripristinaValoriOriginariInUpdateOPreview();
				getMessageBox().addError(MSG_ERROR_DOC_ZIP);
				forwardToPublisher(Application.Publisher.DETTAGLIO_HELP_ON_LINE);
			    }
			} else {
			    ripristinaValoriOriginariInUpdateOPreview();
			    forwardToPublisher(Application.Publisher.DETTAGLIO_HELP_ON_LINE);
			}
		    }

		} catch (FileUploadException | SecurityException | IllegalArgumentException ex) {
		    log.error("Errore nell'invocazione del metodo di navigazione wizard :"
			    + ExceptionUtils.getRootCauseMessage(ex), ex);
		    getMessageBox().addError("Errore nella navigazione del wizard");
		    goBack();
		}
	    } else {

	    }
	}

    }

    /*
     * Nel caso di messaggi a video che comportano il rimanere nella stessa form in modifica
     * riprende i valori originari dalla lista e filtro della pagina precedente e li mette a video
     */
    private void ripristinaValoriOriginariInUpdateOPreview() throws EMFError {
	if (getForm().getDettaglioHelpOnLine().getStatus().equals(Status.update)
		|| getForm().getDettaglioHelpOnLine().getStatus().equals(Status.view)) {
	    AplVVisHelpOnLineRowBean bean = (AplVVisHelpOnLineRowBean) getForm()
		    .getListaHelpOnLine().getTable().getCurrentRow();
	    GestioneHelpOnLineForm.DettaglioHelpOnLine dett = getForm().getDettaglioHelpOnLine();
	    dett.getNm_applic()
		    .setValue(getForm().getFiltriHelpOnLine().getNm_applic().getDecodedValue());
	    dett.getNm_pagina().setValue(bean.getIdPaginaWeb().toString());
	    dett.getTi_help_on_line().setValue(bean.getTiHelpOnLine());
	    if (bean.getIdEntryMenu() != null) {
		dett.getNm_entry_menu().setValue("" + bean.getIdEntryMenu());
	    }
	    dett.getDs_file_help_online().setValue(bean.getDsFileHelpOnLine());
	}
    }

    @Override
    public void reloadAfterGoBack(String publisherName) {
	log.debug(">>>RELOAD-AFTER-GO-BACK");
    }

    @Override
    public String getControllerName() {
	log.debug(">>>GET-CONTROLLER-NAME");
	return Application.Actions.GESTIONE_HELP_ON_LINE;
    }

    @Override
    public JSONObject triggerDettaglioHelpOnLineNm_paginaOnTrigger() throws EMFError {
	log.debug(">>>TRIGGER-DETTAGLIO-HELP-PAGINA");
	getForm().getDettaglioHelpOnLine().post(getRequest());

	processaCampiData();
	return getForm().getDettaglioHelpOnLine().asJSON();
    }

    private void processaCampiData() throws EMFError {
	BigDecimal idApplic = getForm().getFiltriHelpOnLine().getNm_applic().parse();
	String idTipoHelp = getForm().getDettaglioHelpOnLine().getTi_help_on_line().parse();
	BigDecimal idPagina = getForm().getDettaglioHelpOnLine().getNm_pagina().parse();
	BigDecimal idMenu = getForm().getDettaglioHelpOnLine().getNm_entry_menu().parse();
	Date recentDate = recuperoHelpEjb.findMostRecentDtFin(idApplic.longValueExact(), idTipoHelp,
		idPagina == null ? 0 : idPagina.longValueExact(),
		idMenu == null ? 0 : idMenu.longValueExact());
	/*
	 * Se trova un help con data piu' recente (diversa da infinito) mette data di inizio alla
	 * data fine che ha trovato + 1 altrimenti mette la data attuale. La data fine e' sempre
	 * infinita.
	 */
	if (recentDate == null) {
	    recentDate = new Date();
	} else {
	    recentDate = DateUtils.addDays(recentDate, 1);
	}
	getForm().getDettaglioHelpOnLine().getDt_ini_val()
		.setValue(DateUtil.formatDateWithSlash(recentDate));
	getForm().getDettaglioHelpOnLine().getDt_fine_val()
		.setValue(DateUtil.MAX_DATE_FORMAT_SLASH);
    }

    @Override
    public JSONObject triggerDettaglioHelpOnLineNm_entry_menuOnTrigger() throws EMFError {
	log.debug(">>>TRIGGER-RICERCA-HELP-MENU");
	getForm().getDettaglioHelpOnLine().post(getRequest());
	return getForm().getDettaglioHelpOnLine().asJSON();
    }

    @Override
    public JSONObject triggerDettaglioHelpOnLineTi_help_on_lineOnTrigger() throws EMFError {
	log.debug(">>>TRIGGER-RICERCA-HELP-TIPO-DETTAGLIO");
	getForm().getDettaglioHelpOnLine().post(getRequest());
	getForm().getDettaglioHelpOnLine().getNm_applic()
		.setValue(getForm().getFiltriHelpOnLine().getNm_applic().getDecodedValue());
	BigDecimal idAppl = new BigDecimal(
		getForm().getFiltriHelpOnLine().getNm_applic().getValue());
	String tiHelpOnLine = null;
	tiHelpOnLine = getForm().getDettaglioHelpOnLine().getTi_help_on_line().getValue();
	getForm().getDettaglioHelpOnLine().getNm_pagina()
		.setDecodeMap(comboEjb.getMappaPaginePerApplicazione(idAppl, tiHelpOnLine, true));

	getForm().getDettaglioHelpOnLine().getNm_pagina().setValue("");
	getForm().getDettaglioHelpOnLine().getNm_entry_menu().setValue("");
	return getForm().getDettaglioHelpOnLine().asJSON();
    }

    /*
     * Viene richiamato sia dal framework quando il dettaglio Ã¨ in modalitÃ  view oppure dal metodo
     * process quando si chiede la preview dell'Help ma si Ã¨ in modalitÃ  modifica
     */
    @Override
    public void preview() throws EMFError {

	if (getForm().getDettaglioHelpOnLine().getStatus().equals(Status.view)
		|| getForm().getDettaglioHelpOnLine().getStatus().equals(Status.update)) {
	    AplVVisHelpOnLineRowBean bean = (AplVVisHelpOnLineRowBean) getForm()
		    .getListaHelpOnLine().getTable().getCurrentRow();
	    BigDecimal idHelp = bean.getIdHelpOnLine();
	    bean = recuperoHelpEjb.findVistaById(idHelp);
	    ObjectMapper om = new ObjectMapper();
	    String arr = om.convertValue(bean.getBlHelpOnLine().getBytes(StandardCharsets.UTF_8),
		    String.class);
	    getRequest().setAttribute("Preview_hidden_field", arr);
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_HELP_ON_LINE);
    }

    @Override
    public void updateListaHelpOnLine() throws EMFError {
	getForm().getListaHelpOnLine().setStatus(Status.update);
	getForm().getDettaglioHelpOnLine().setStatus(Status.update);
	getForm().getDettaglioHelpOnLine().getDownloadFile().setEditMode();
	getForm().getDettaglioHelpOnLine().getPreview().setEditMode();
    }

    private boolean validaDati() throws EMFError {
	boolean retCode = true;

	BigDecimal idApplic = getForm().getFiltriHelpOnLine().getNm_applic().parse();
	String idTipoHelp = getForm().getDettaglioHelpOnLine().getTi_help_on_line().parse();
	BigDecimal idPagina = null;
	BigDecimal idMenu = null;
	String dsFile = null;
	// String infoPrivacyTextArea = null;
	Date dataIni = getForm().getDettaglioHelpOnLine().getDt_ini_val().parse();
	Date dataFine = getForm().getDettaglioHelpOnLine().getDt_fine_val().parse();

	if (getForm().getDettaglioHelpOnLine().getStatus().equals(Status.insert)) {
	    retCode = getForm().getDettaglioHelpOnLine().validate(getMessageBox());
	    idPagina = getForm().getDettaglioHelpOnLine().getNm_pagina().parse();
	    dsFile = getForm().getDettaglioHelpOnLine().getDs_file_zip_help_online().parse();
	    if (idTipoHelp.equals(ConstAplVVisHelpOnLine.TiHelpOnLine.HELP_RICERCA_DIPS.name())
		    || idTipoHelp.equals(ConstAplVVisHelpOnLine.TiHelpOnLine.HELP_PAGINA.name())) {
		if (idPagina == null) {
		    getMessageBox().addError("Campo 'Pagina' obbligatorio");
		    retCode = false;
		}
	    }

	    if (dsFile == null) {
		if (idTipoHelp.equals(ConstAplVVisHelpOnLine.TiHelpOnLine.HELP_RICERCA_DIPS.name())
			|| idTipoHelp
				.equals(ConstAplVVisHelpOnLine.TiHelpOnLine.HELP_PAGINA.name())) {
		    getMessageBox()
			    .addError("Campo 'File zip contenente l'help online' obbligatorio");
		} else {
		    getMessageBox().addError(
			    "Campo 'File zip contenente l'informativa sulla privacy obbligatorio");
		}
		retCode = false;
	    }

	    idMenu = getForm().getDettaglioHelpOnLine().getNm_entry_menu().parse();
	    // Controllo inserimento menu' nel caso di sacerdips
	    if (idTipoHelp.equals(ConstAplVVisHelpOnLine.TiHelpOnLine.HELP_RICERCA_DIPS.name())) {
		if (idMenu == null) {
		    getMessageBox()
			    .addError("Per l'help di SacerDips e' necessario inserire un menu'");
		    retCode = false;
		}
	    }
	}

	Message msg = getForm().getDettaglioHelpOnLine().getDt_ini_val().validate();
	if (msg != null) {
	    getMessageBox().addMessage(msg);
	    retCode = false;
	}
	msg = getForm().getDettaglioHelpOnLine().getDt_fine_val().validate();
	if (msg != null) {
	    getMessageBox().addMessage(msg);
	    retCode = false;
	}
	if (retCode == true) {
	    if (dataFine.before(dataIni)) {
		getMessageBox().addError("La data finale e' antecedente alla data iniziale");
		retCode = false;
	    }

	    boolean risultatoIntersect = false;

	    if (idTipoHelp.equals(ConstAplVVisHelpOnLine.TiHelpOnLine.HELP_RICERCA_DIPS.name())
		    || idTipoHelp.equals(ConstAplVVisHelpOnLine.TiHelpOnLine.HELP_PAGINA.name())) {

		if (getForm().getDettaglioHelpOnLine().getStatus().equals(Status.insert)) {
		    risultatoIntersect = recuperoHelpEjb.intersectsExistingHelp(
			    idApplic.longValueExact(), idTipoHelp, idPagina.longValueExact(),
			    idMenu == null ? 0 : idMenu.longValueExact(), dataIni, dataFine);
		} else {
		    AplVVisHelpOnLineRowBean bean = (AplVVisHelpOnLineRowBean) getForm()
			    .getListaHelpOnLine().getTable().getCurrentRow();
		    risultatoIntersect = recuperoHelpEjb
			    .intersectsExistingHelp(bean.getIdHelpOnLine(), dataIni, dataFine);
		}

		if (risultatoIntersect) {
		    getMessageBox()
			    .addError("L'intervallo di validita' interseca altri intervalli");
		    retCode = false;
		}

	    }
	}
	return retCode;
    }

    @Override
    public void downloadFile() throws EMFError {
	AplVVisHelpOnLineRowBean bean = (AplVVisHelpOnLineRowBean) getForm().getListaHelpOnLine()
		.getTable().getCurrentRow();
	BigDecimal idHelp = bean.getIdHelpOnLine();
	AplHelpOnLine help = recuperoHelpEjb.findById(idHelp);
	if (help != null) {
	    if (!getMessageBox().hasError()) {
		File tmpFile = new File(System.getProperty("java.io.tmpdir"),
			help.getDsFileHelpOnLine());
		FileOutputStream out = null;
		try {
		    out = new FileOutputStream(tmpFile);
		    IOUtils.write(help.getBlSorgenteHelpOnLine(), out);

		    getRequest().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_ACTION.name(),
			    getControllerName());
		    getSession().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_FILENAME.name(),
			    tmpFile.getName());
		    getSession().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_FILEPATH.name(),
			    tmpFile.getPath());
		    getSession().setAttribute(
			    WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_DELETEFILE.name(),
			    Boolean.toString(true));
		    getSession().setAttribute(
			    WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_CONTENTTYPE.name(),
			    WebConstants.MIME_TYPE_MICROSOFT_WORD);
		} catch (IOException ex) {
		    log.error("Errore in download " + ExceptionUtils.getRootCauseMessage(ex), ex);
		    getMessageBox().addError("Errore inatteso nella preparazione del download");
		} finally {
		    IOUtils.closeQuietly(out);
		}
	    }

	    if (getMessageBox().hasError()) {
		forwardToPublisher(getLastPublisher());
	    } else {
		forwardToPublisher(Application.Publisher.DOWNLOAD_PAGE);
	    }
	}

    }

    public void download() throws EMFError {
	log.debug(">>>DOWNLOAD");
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
		 * Definiamo l'output previsto che sarÃƒÂ  un file in formato zip di cui si
		 * occuperÃƒÂ  la servlet per fare il download
		 */
		OutputStream outUD = getServletOutputStream();
		getResponse().setContentType(
			StringUtils.isBlank(contentType) ? WebConstants.MIME_TYPE_MICROSOFT_WORD
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
		    log.error("Eccezione nel recupero del documento ", e);
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

}
