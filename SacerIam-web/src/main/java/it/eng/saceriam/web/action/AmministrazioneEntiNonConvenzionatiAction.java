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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import javax.ejb.EJB;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.codehaus.jettison.json.JSONObject;
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
import it.eng.saceriam.amministrazioneEntiNonConvenzionati.dto.EnteNonConvenzionatoBean;
import it.eng.saceriam.amministrazioneEntiNonConvenzionati.ejb.EntiNonConvenzionatiEjb;
import it.eng.saceriam.common.Constants;
import it.eng.saceriam.entity.constraint.ConstIamParamApplic;
import it.eng.saceriam.entity.constraint.ConstOrgAmbitoTerrit;
import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam;
import it.eng.saceriam.entity.constraint.ConstUsrStatoUser;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.saceriam.slite.gen.Application;
import it.eng.saceriam.slite.gen.action.AmministrazioneEntiNonConvenzionatiAbstractAction;
import it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm;
import it.eng.saceriam.slite.gen.form.AmministrazioneEntiNonConvenzionatiForm;
import it.eng.saceriam.slite.gen.form.AmministrazioneUtentiForm;
import it.eng.saceriam.slite.gen.tablebean.OrgAccordoVigilRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAccordoVigilTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAmbitoTerritRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteArkRifRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteArkRifTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteSiamRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteUserRifRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteUserRifTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgSuptEsternoEnteConvenzRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgSuptEsternoEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgVigilEnteProdutRowBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicEnteNonConvenzTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisUserRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisUserTableBean;
import it.eng.saceriam.util.DateUtil;
import it.eng.saceriam.util.SacerLogConstants;
import it.eng.saceriam.web.helper.AmministrazioneUtentiHelper;
import it.eng.saceriam.web.util.ApplEnum;
import it.eng.saceriam.web.util.ComboGetter;
import it.eng.saceriam.web.util.NavigatorDetailBean;
import it.eng.saceriam.web.util.NavigatorDetailBeanManager;
import it.eng.saceriam.web.util.WebConstants;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.SessionManager;
import it.eng.spagoLite.actions.form.ListAction;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.oracle.decode.DecodeMap;
import it.eng.spagoLite.form.base.BaseElements.Status;
import it.eng.spagoLite.form.fields.SingleValueField;
import it.eng.spagoLite.message.MessageBox.ViewMode;
import it.eng.spagoLite.security.Secure;

/**
 *
 * @author DiLorenzo_F
 */
@SuppressWarnings({
	"unchecked", "rawtypes" })
public class AmministrazioneEntiNonConvenzionatiAction
	extends AmministrazioneEntiNonConvenzionatiAbstractAction {

    private static final Logger actionLogger = LoggerFactory
	    .getLogger(AmministrazioneEntiNonConvenzionatiAction.class);

    @EJB(mappedName = "java:app/SacerIam-ejb/EntiNonConvenzionatiEjb")
    private EntiNonConvenzionatiEjb entiNonConvenzionatiEjb;
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
	    if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW)
		    || getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)
		    || getNavigationEvent().equals(ListAction.NE_NEXT)
		    || getNavigationEvent().equals(ListAction.NE_PREV)) {
		if (getTableName().equals(getForm().getListaEntiNonConvenzionati().getName())) {
		    // Al primo giro azzero lo stack
		    NavigatorDetailBeanManager.resetNavigatorDetailStack();
		    loadCurrentDettaglioEnteNonConvenzFromList(
			    getForm().getListaEntiNonConvenzionati().getName());
		} else if (getTableName().equals(getForm().getAccordiVigilList().getName())) {
		    initAccordoVigilDetail();
		    OrgAccordoVigilRowBean currentRow = (OrgAccordoVigilRowBean) getForm()
			    .getAccordiVigilList().getTable().getCurrentRow();
		    loadDettaglioAccordoVigil(currentRow.getIdAccordoVigil());
		} else if (getTableName().equals(getForm().getEntiSupportatiList().getName())) {
		    OrgSuptEsternoEnteConvenzRowBean currentRow = (OrgSuptEsternoEnteConvenzRowBean) getForm()
			    .getEntiSupportatiList().getTable().getCurrentRow();
		    loadDettaglioEnteSupt(currentRow.getIdSuptEstEnteConvenz());
		} else if (getTableName().equals(getForm().getEntiVigilatiList().getName())) {
		    OrgVigilEnteProdutRowBean currentRow = (OrgVigilEnteProdutRowBean) getForm()
			    .getEntiVigilatiList().getTable().getCurrentRow();
		    loadDettaglioEnteVigilato(currentRow.getIdVigilEnteProdut());
		}
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
    }

    @Override
    public void undoDettaglio() throws EMFError {
	try {
	    if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_ENTE_NON_CONVENZIONATO)
		    && (getForm().getListaEntiNonConvenzionati().getStatus()
			    .equals(Status.update))) {
		BaseRowInterface currentRow = getForm().getListaEntiNonConvenzionati().getTable()
			.getCurrentRow();
		BigDecimal idEnteSiam = currentRow.getBigDecimal("id_ente_siam");
		if (idEnteSiam != null) {
		    loadDettaglioEnteNonConvenzionato(idEnteSiam);
		}
		forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_NON_CONVENZIONATO);
	    } else if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_ACCORDO_VIGIL)
		    && getForm().getAccordiVigilList().getStatus().equals(Status.update)) {
		OrgAccordoVigilRowBean currentRow = (OrgAccordoVigilRowBean) getForm()
			.getAccordiVigilList().getTable().getCurrentRow();
		BigDecimal idAccordoVigil = currentRow.getIdAccordoVigil();
		if (idAccordoVigil != null) {
		    loadDettaglioAccordoVigil(idAccordoVigil);
		}
		forwardToPublisher(Application.Publisher.DETTAGLIO_ACCORDO_VIGIL);
	    } else {
		goBack();
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	    forwardToPublisher(getLastPublisher());
	}
    }

    @Override
    public void insertDettaglio() throws EMFError {
	try {
	    if (getTableName().equals(getForm().getListaEntiNonConvenzionati().getName())) {
		getSession().setAttribute("navTableEntiNonConvenz",
			getForm().getListaEntiNonConvenzionati().getName());
		getForm().getEnteNonConvenzionatoDetail().reset();
		initEnteNonConvenzionatoDetail();
		DateFormat formato = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH);
		getForm().getEnteNonConvenzionatoDetail().getDt_ini_val()
			.setValue(formato.format(Calendar.getInstance().getTime()));
		Calendar c = Calendar.getInstance();
		c.set(2444, Calendar.DECEMBER, 31, 0, 0, 0);
		getForm().getEnteNonConvenzionatoDetail().getDt_cessazione()
			.setValue(formato.format(c.getTime()));
		getForm().getEnteNonConvenzionatoDetail().getCd_nazione_sede_legale()
			.setValue("IT");

		getForm().getEnteNonConvenzionatoDetail().setEditMode();
		getForm().getEnteNonConvenzionatoDetail().setStatus(Status.insert);
		getForm().getListaEntiNonConvenzionati().setStatus(Status.insert);
		forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_NON_CONVENZIONATO);
	    } else if (getTableName().equals(getForm().getEntiSupportatiList().getName())) {
		getForm().getEnteSupportatoDetail().clear();
		initEnteSuptDetail();

		getForm().getEntiSupportatiList().setStatus(Status.insert);
		getForm().getEnteSupportatoDetail().setStatus(Status.insert);
		getForm().getEnteSupportatoDetail().setEditMode();
		forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_SUPT);
	    } else if (getTableName().equals(getForm().getAccordiVigilList().getName())) {
		getForm().getAccordoVigilDetail().clear();
		initAccordoVigilDetail();

		// Precompilo le date di validità dell'accordo
		DateFormat formato = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH);
		Calendar cal = Calendar.getInstance();
		getForm().getAccordoVigilDetail().getDt_ini_val_accordo()
			.setValue(formato.format(cal.getTime()));
		cal.set(2444, Calendar.DECEMBER, 31);
		getForm().getAccordoVigilDetail().getDt_fin_val_accordo()
			.setValue(formato.format(cal.getTime()));

		getForm().getAccordoVigilDetail().setEditMode();

		getForm().getAccordoVigilDetail().setStatus(Status.insert);
		getForm().getAccordiVigilList().setStatus(Status.insert);
		forwardToPublisher(Application.Publisher.DETTAGLIO_ACCORDO_VIGIL);
	    } else if (getTableName().equals(getForm().getEntiVigilatiList().getName())) {
		getForm().getEnteVigilatoDetail().clear();
		initEnteVigilatoDetail();

		getForm().getEntiVigilatiList().setStatus(Status.insert);
		getForm().getEnteVigilatoDetail().setStatus(Status.insert);
		getForm().getEnteVigilatoDetail().setEditMode();
		forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_VIGILATO);
	    } else if (getTableName().equals(getForm().getUtentiArchivistiList().getName())) {
		AmministrazioneEntiConvenzionatiForm form = new AmministrazioneEntiConvenzionatiForm();
		form.getUtentiArchivistiList()
			.setTable(getForm().getUtentiArchivistiList().getTable());

		OrgEnteSiamRowBean enteSiamRowBean = entiNonConvenzionatiEjb.getOrgEnteSiamRowBean(
			getForm().getEnteNonConvenzionatoDetail().getId_ente_siam().parse());
		form.getEnteConvenzionatoDetail().copyFromBean(enteSiamRowBean);

		redirectToAction(Application.Actions.AMMINISTRAZIONE_ENTI_CONVENZIONATI,
			"?operation=listNavigationOnClick&navigationEvent="
				+ ListAction.NE_DETTAGLIO_INSERT + "&table="
				+ AmministrazioneEntiConvenzionatiForm.UtentiArchivistiList.NAME
				+ "&riga=-1",
			form);
	    } else if (getTableName().equals(getForm().getReferentiEnteList().getName())) {
		AmministrazioneEntiConvenzionatiForm form = new AmministrazioneEntiConvenzionatiForm();
		form.getReferentiEnteList().setTable(getForm().getReferentiEnteList().getTable());

		OrgEnteSiamRowBean enteSiamRowBean = entiNonConvenzionatiEjb.getOrgEnteSiamRowBean(
			getForm().getEnteNonConvenzionatoDetail().getId_ente_siam().parse());
		form.getEnteConvenzionatoDetail().copyFromBean(enteSiamRowBean);

		redirectToAction(Application.Actions.AMMINISTRAZIONE_ENTI_CONVENZIONATI,
			"?operation=listNavigationOnClick&navigationEvent="
				+ ListAction.NE_DETTAGLIO_INSERT + "&table="
				+ AmministrazioneEntiConvenzionatiForm.ReferentiEnteList.NAME
				+ "&riga=-1",
			form);
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
    }

    @Override
    public void saveDettaglio() throws EMFError {
	if (getTableName().equals(getForm().getListaEntiNonConvenzionati().getName())
		|| getTableName().equals(getForm().getEnteNonConvenzionatoDetail().getName())) {
	    saveEnteNonConvenzionato();
	} else if (getTableName().equals(getForm().getEntiSupportatiList().getName())
		|| getTableName().equals(getForm().getEnteSupportatoDetail().getName())) {
	    saveEnteSupportato();
	} else if (getTableName().equals(getForm().getAccordiVigilList().getName())
		|| getTableName().equals(getForm().getAccordoVigilDetail().getName())) {
	    saveAccordoVigil();
	} else if (getTableName().equals(getForm().getEntiVigilatiList().getName())
		|| getTableName().equals(getForm().getEnteVigilatoDetail().getName())) {
	    saveEnteVigilato();
	} else if (getTableName().equals(getForm().getUtentiArchivistiList().getName())
		|| getTableName().equals(getForm().getUtenteArchivistaDetail().getName())) {
	    // saveUtenteArchivista();
	} else if (getTableName().equals(getForm().getReferentiEnteList().getName())
		|| getTableName().equals(getForm().getReferenteEnteDetail().getName())) {
	    // saveReferenteEnte();
	}
    }

    @Override
    public void dettaglioOnClick() throws EMFError {
	try {
	    if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW)
		    || getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)
		    || getNavigationEvent().equals(ListAction.NE_NEXT)
		    || getNavigationEvent().equals(ListAction.NE_PREV)) {
		if (getTableName().equals(getForm().getListaEntiNonConvenzionati().getName())) {
		    forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_NON_CONVENZIONATO);
		} else if (getTableName().equals(getForm().getAccordiVigilList().getName())) {
		    forwardToPublisher(Application.Publisher.DETTAGLIO_ACCORDO_VIGIL);
		} else if (getTableName().equals(getForm().getEntiSupportatiList().getName())) {
		    forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_SUPT);
		} else if (getTableName().equals(getForm().getEntiVigilatiList().getName())) {
		    forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_VIGILATO);
		} else if (getTableName().equals(getForm().getUtentiEnteList().getName())) {
		    // /* Redirect a Dettaglio Utenti appartenenti all'ente non convenzionato */
		    // AmministrazioneUtentiForm form = new AmministrazioneUtentiForm();
		    // UsrVLisUserTableBean utb = new UsrVLisUserTableBean();
		    // UsrVLisUserRowBean urb = new UsrVLisUserRowBean();
		    // BigDecimal idUserIam = ((UsrUserTableBean)
		    // getForm().getUtentiEnteList().getTable()).getCurrentRow().getIdUserIam();
		    // String tipoUser = ((UsrUserTableBean)
		    // getForm().getUtentiEnteList().getTable()).getCurrentRow().getTipoUser();
		    // urb.setIdUserIam(idUserIam);
		    // urb.setTipoUser(tipoUser);
		    // utb.add(urb);
		    // /* Preparo la LISTA UTENTI lato AmministrazioneUtenti */
		    // form.getListaUtenti().setTable(utb);
		    // redirectToAction(Application.Actions.AMMINISTRAZIONE_UTENTI,
		    // "?operation=listNavigationOnClick&navigationEvent=" +
		    // ListAction.NE_DETTAGLIO_VIEW + "&table=" +
		    // AmministrazioneUtentiForm.ListaUtenti.NAME + "&riga=0", form);
		} else if (getTableName().equals(getForm().getReferentiEnteList().getName())
			&& getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW)) {
		    /* Redirect a Dettaglio Utenti referenti dell'ente convenzionato */
		    AmministrazioneUtentiForm form = new AmministrazioneUtentiForm();
		    UsrVLisUserTableBean utb = new UsrVLisUserTableBean();
		    UsrVLisUserRowBean urb = new UsrVLisUserRowBean();
		    BigDecimal idUserIam = ((OrgEnteUserRifTableBean) getForm()
			    .getReferentiEnteList().getTable()).getCurrentRow().getIdUserIam();
		    entiNonConvenzionatiEjb.checkReferenteEnteCessato(idUserIam);
		    String tipoUser = ((OrgEnteUserRifTableBean) getForm().getReferentiEnteList()
			    .getTable()).getCurrentRow().getString("tipo_user");
		    urb.setIdUserIam(idUserIam);
		    urb.setTipoUser(tipoUser);
		    utb.add(urb);
		    /* Preparo la LISTA UTENTI lato AmministrazioneUtenti */
		    form.getListaUtenti().setTable(utb);
		    redirectToAction(Application.Actions.AMMINISTRAZIONE_UTENTI,
			    "?operation=listNavigationOnClick&navigationEvent="
				    + ListAction.NE_DETTAGLIO_VIEW + "&table="
				    + AmministrazioneUtentiForm.ListaUtenti.NAME + "&riga=0",
			    form);
		} else if (getTableName().equals(getForm().getUtentiArchivistiList().getName())
			&& getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW)) {
		    /* Redirect a Dettaglio Utenti archivisti */
		    AmministrazioneUtentiForm form = new AmministrazioneUtentiForm();
		    UsrVLisUserTableBean utb = new UsrVLisUserTableBean();
		    UsrVLisUserRowBean urb = new UsrVLisUserRowBean();
		    BigDecimal idUserIam = ((OrgEnteArkRifTableBean) getForm()
			    .getUtentiArchivistiList().getTable()).getCurrentRow().getIdUserIam();
		    String tipoUser = ((OrgEnteArkRifTableBean) getForm().getUtentiArchivistiList()
			    .getTable()).getCurrentRow().getString("tipo_user");
		    urb.setIdUserIam(idUserIam);
		    urb.setTipoUser(tipoUser);
		    utb.add(urb);
		    /* Preparo la LISTA UTENTI lato AmministrazioneUtenti */
		    form.getListaUtenti().setTable(utb);
		    redirectToAction(Application.Actions.AMMINISTRAZIONE_UTENTI,
			    "?operation=listNavigationOnClick&navigationEvent="
				    + ListAction.NE_DETTAGLIO_VIEW + "&table="
				    + AmministrazioneUtentiForm.ListaUtenti.NAME + "&riga=0",
			    form);
		}
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
    }

    @Override
    public void elencoOnClick() throws EMFError {
	if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_ACCORDO_VIGIL)) {
	    goBackTo(Application.Publisher.DETTAGLIO_ENTE_NON_CONVENZIONATO);
	} else {
	    goBack();
	}
    }

    @Override
    protected String getDefaultPublsherName() {
	return Application.Publisher.RICERCA_ENTI_NON_CONVENZIONATI;
    }

    @Override
    public void reloadAfterGoBack(String publisherName) {
	try {
	    if (publisherName.equals(Application.Publisher.RICERCA_ENTI_NON_CONVENZIONATI)) {
		int rowIndex;
		int pageSize;
		if (getForm().getListaEntiNonConvenzionati().getTable() != null) {
		    rowIndex = getForm().getListaEntiNonConvenzionati().getTable()
			    .getCurrentRowIndex();
		    pageSize = getForm().getListaEntiNonConvenzionati().getTable().getPageSize();
		} else {
		    rowIndex = 0;
		    pageSize = 10;
		}
		String nmEnteSiam = getForm().getFiltriEntiNonConvenzionati().getNm_ente_siam()
			.parse();
		BigDecimal idUserIamCor = new BigDecimal(getUser().getIdUtente());
		String flEnteCessato = getForm().getFiltriEntiNonConvenzionati()
			.getFl_ente_cessato().parse();
		List<BigDecimal> idArchivista = getForm().getFiltriEntiNonConvenzionati()
			.getArchivista().parse();
		String noArchivista = getForm().getFiltriEntiNonConvenzionati().getNo_archivista()
			.parse();
		String tiEnteNonConvenz = getForm().getFiltriEntiNonConvenzionati()
			.getTi_ente_non_convenz().parse();
		OrgVRicEnteNonConvenzTableBean table = entiNonConvenzionatiEjb
			.getOrgVRicEnteNonConvenzTableBean(nmEnteSiam, idUserIamCor,
				/* idAmbienteEnteConvenz, */ flEnteCessato, idArchivista,
				noArchivista, tiEnteNonConvenz);
		getForm().getListaEntiNonConvenzionati().setTable(table);
		getForm().getListaEntiNonConvenzionati().getTable().setPageSize(pageSize);
		getForm().getListaEntiNonConvenzionati().getTable().setCurrentRowIndex(rowIndex);
	    } else if (publisherName
		    .equals(Application.Publisher.DETTAGLIO_ENTE_NON_CONVENZIONATO)) {
		BigDecimal idEnteSiam;
		int level = 1;
		if (getLastPublisher().equals(publisherName)) {
		    // Recupero l'ultimo elemento dalla pila e lo rimuovo da essa
		    NavigatorDetailBean detailBean = NavigatorDetailBeanManager
			    .popNavigatorDetailStack();
		    // Dall'oggetto NavigatorDetailBean mi ricavo l'id ente non convenzionato
		    idEnteSiam = detailBean != null ? detailBean.getIdObject() : null;

		    if (idEnteSiam != null && idEnteSiam.equals(
			    getForm().getEnteNonConvenzionatoDetail().getId_ente_siam().parse())) {
			// Dopo aver rimosso col pop precedente l'ultimo elemento dalla pila, ora
			// prendo l'attuale
			// ultimo che sarà quello di cui caricare il dettaglio
			detailBean = NavigatorDetailBeanManager.getLastNavigatorDetailStack();
			idEnteSiam = detailBean != null ? detailBean.getIdObject() : null;
			if (idEnteSiam == null) {
			    idEnteSiam = getForm().getEnteNonConvenzionatoDetail().getId_ente_siam()
				    .parse();
			}
		    } else if (idEnteSiam == null) {
			// Nel caso lo stack sia vuoto, non dovrebbe succedere mai.
			idEnteSiam = getForm().getEnteNonConvenzionatoDetail().getId_ente_siam()
				.parse();
		    }

		    // Recupero i valori dell'oggetto
		    if (detailBean != null) {
			// Salvo in sessione l'attributo riferito alla sourceList
			getSession().setAttribute("navTableEntiNonConvenz",
				detailBean.getSourceList());
			// Setto la lista corretta, ListaEntiNonConvenzionati
			((it.eng.spagoLite.form.list.List<SingleValueField<?>>) getForm()
				.getComponent(detailBean.getSourceList()))
				.setTable(detailBean.getSourceTable());
			// Recupero il livello di annidamento
			level = detailBean.getLevel();
		    }
		} else {
		    idEnteSiam = getForm().getEnteNonConvenzionatoDetail().getId_ente_siam()
			    .parse();
		    level = getForm().getEnteNonConvenzionatoDetail().getLevel_enti_convenz()
			    .parse() != null
				    ? getForm().getEnteNonConvenzionatoDetail()
					    .getLevel_enti_convenz().parse().intValue()
				    : 1;
		}

		// Carico il dettaglio fattura
		loadDettaglioEnteNonConvenzionato(idEnteSiam);

		// Mi salvo il livello di annidamento attuale
		getForm().getEnteNonConvenzionatoDetail().getLevel_enti_convenz()
			.setValue(String.valueOf(level));

	    } else if (publisherName.equals(Application.Publisher.DETTAGLIO_ACCORDO_VIGIL)) {
		OrgAccordoVigilRowBean currentRow = (OrgAccordoVigilRowBean) getForm()
			.getAccordiVigilList().getTable().getCurrentRow();
		BigDecimal idAccordoVigil = currentRow.getIdAccordoVigil();
		if (idAccordoVigil != null) {
		    loadDettaglioAccordoVigil(idAccordoVigil);
		}
		getForm().getAccordoVigilDetail().setViewMode();
		// Bottone di ricalcolo
		// getForm().getAccordoVigilDetail().getRicalcoloServiziErogati().setHidden(false);
		getForm().getAccordoVigilDetail().setStatus(Status.view);
		getForm().getAccordiVigilList().setStatus(Status.view);
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
	return Application.Actions.AMMINISTRAZIONE_ENTI_NON_CONVENZIONATI;
    }

    // <editor-fold defaultstate="expand" desc="Gestione ricerca enti non convenzionati">
    @Secure(action = "Menu.EntiConvenzionati.GestioneEntiNonConvenzionati")
    public void ricercaEntiNonConvenzionatiPage() {
	try {
	    getUser().getMenu().reset();
	    getUser().getMenu().select("Menu.EntiConvenzionati.GestioneEntiNonConvenzionati");

	    initFiltriRicercaEntiNonConvenzionati();

	    getForm().getListaEntiNonConvenzionati().setTable(null);

	    forwardToPublisher(Application.Publisher.RICERCA_ENTI_NON_CONVENZIONATI);
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
    }

    private void initFiltriRicercaEntiNonConvenzionati() throws ParerUserError {
	getForm().getFiltriEntiNonConvenzionati().reset();
	getForm().getFiltriEntiNonConvenzionati().setEditMode();
	getForm().getFiltriEntiNonConvenzionati().getTi_ente_non_convenz()
		.setDecodeMap(ComboGetter.getMappaTiEnteNonConvenz());
	getForm().getFiltriEntiNonConvenzionati().getFl_ente_cessato()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	getForm().getFiltriEntiNonConvenzionati().getArchivista().setDecodeMap(
		DecodeMap.Factory.newInstance(entiNonConvenzionatiEjb.getUtentiArchivisti(),
			"id_user_iam", "nm_userid"));
    }

    @Override
    public void ricercaEntiNonConvenz() throws EMFError {
	if (getForm().getFiltriEntiNonConvenzionati().postAndValidate(getRequest(),
		getMessageBox())) {
	    BigDecimal idUserIamCor = new BigDecimal(getUser().getIdUtente());
	    String nmEnteSiam = getForm().getFiltriEntiNonConvenzionati().getNm_ente_siam().parse();
	    String flEnteCessato = getForm().getFiltriEntiNonConvenzionati().getFl_ente_cessato()
		    .parse();
	    List<BigDecimal> idArchivista = getForm().getFiltriEntiNonConvenzionati()
		    .getArchivista().parse();
	    String noArchivista = getForm().getFiltriEntiNonConvenzionati().getNo_archivista()
		    .parse();
	    String tiEnteNonConvenz = getForm().getFiltriEntiNonConvenzionati()
		    .getTi_ente_non_convenz().parse();

	    if (!getMessageBox().hasError()) {
		try {
		    OrgVRicEnteNonConvenzTableBean table = entiNonConvenzionatiEjb
			    .getOrgVRicEnteNonConvenzTableBean(nmEnteSiam, idUserIamCor,
				    /* idAmbienteEnteConvenz, */ flEnteCessato, idArchivista,
				    noArchivista, tiEnteNonConvenz);
		    getForm().getListaEntiNonConvenzionati().setTable(table);
		    getForm().getListaEntiNonConvenzionati().getTable().setPageSize(10);
		    getForm().getListaEntiNonConvenzionati().getTable().first();
		} catch (ParerUserError pue) {
		    getMessageBox().addError(pue.getDescription());
		}
	    }
	}
	forwardToPublisher(getLastPublisher());
    }

    @Override
    public void pulisciRicerca() throws EMFError {
	try {
	    initFiltriRicercaEntiNonConvenzionati();
	} catch (ParerUserError ex) {
	    getMessageBox().addError(
		    "Errore durante il reset dei filtri di ricerca enti non convenzionati");
	}
	forwardToPublisher(Application.Publisher.RICERCA_ENTI_NON_CONVENZIONATI);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Gestione dettaglio ente convenzionato">
    private void initEnteNonConvenzionatoDetail() throws ParerUserError {
	getForm().getEnteNonConvenzionatoDetail().getTi_ente_non_convenz()
		.setDecodeMap(ComboGetter.getMappaTiEnteNonConvenz());
	getForm().getEnteNonConvenzionatoDetail().getId_ambiente_ente_convenz()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiNonConvenzionatiEjb.getUsrVAbilAmbEnteConvenzTableBean(
				new BigDecimal(getUser().getIdUtente())),
			"id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));
	getForm().getEnteNonConvenzionatoDetail().getId_prov_sede_legale()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiNonConvenzionatiEjb.getOrgAmbitoTerritTableBean(
				ConstOrgAmbitoTerrit.TiAmbitoTerrit.PROVINCIA.toString()),
			"id_ambito_territ", "cd_ambito_territ"));
	getForm().getEnteNonConvenzionatoDetail().getCd_ambito_territ_regione()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiNonConvenzionatiEjb.getOrgAmbitoTerritTableBean(
				ConstOrgAmbitoTerrit.TiAmbitoTerrit.REGIONE_STATO.toString()),
			"id_ambito_territ", "cd_ambito_territ"));
	getForm().getEnteNonConvenzionatoDetail().getCd_ambito_territ_provincia()
		.setDecodeMap(new DecodeMap());
	getForm().getEnteNonConvenzionatoDetail().getCd_ambito_territ_forma_associata()
		.setDecodeMap(new DecodeMap());
    }

    @Override
    public void updateListaEntiNonConvenzionati() throws EMFError {
	getForm().getEnteNonConvenzionatoDetail().setEditMode();
	getForm().getEnteNonConvenzionatoDetail().getTi_ente_non_convenz().setViewMode();
	getForm().getEnteNonConvenzionatoDetail().getNm_ambiente_ente_convenz().setViewMode();
	getForm().getEnteNonConvenzionatoDetail().getNm_ente_siam_produt_corrisp().setViewMode();

	getForm().getListaEntiNonConvenzionati().setStatus(Status.update);
    }

    private void saveEnteNonConvenzionato() throws EMFError {

	if (getForm().getEnteNonConvenzionatoDetail().postAndValidate(getRequest(),
		getMessageBox())) {
	    // CONTROLLI DI COERENZA NELLA MASCHERA
	    String tiEnteNonConvenz = getForm().getEnteNonConvenzionatoDetail()
		    .getTi_ente_non_convenz().parse();
	    String nmEnteNonConvenz = getForm().getEnteNonConvenzionatoDetail().getNm_ente_siam()
		    .parse();
	    Date dtIniVal = getForm().getEnteNonConvenzionatoDetail().getDt_ini_val().parse();
	    Date dtCessazione = getForm().getEnteNonConvenzionatoDetail().getDt_cessazione()
		    .parse();

	    if (dtCessazione.before(dtIniVal)) {
		getMessageBox().addError(
			"La data di inizio validità non può essere successiva alla data cessazione");
	    }
	    try {
		if (!getMessageBox().hasError()) {

		    BigDecimal idEnteSiamProdutCorrisp = getForm().getEnteNonConvenzionatoDetail()
			    .getId_ente_siam_produt_corrisp().parse();

		    // Se è stato indicato l'ente convenzionato corrispondente, richiamo il
		    // controllo inclusione
		    // passando come entità figlio = ente non convenzionato e entità padre = ente
		    // convenzionato
		    // corrispondente.
		    if (idEnteSiamProdutCorrisp != null) {
			OrgEnteSiamRowBean enteProdutCorrisp = entiNonConvenzionatiEjb
				.getOrgEnteSiamRowBean(idEnteSiamProdutCorrisp);
			entiNonConvenzionatiEjb.checkInclusioneFiglio(
				enteProdutCorrisp.getDtIniVal(),
				enteProdutCorrisp.getDtCessazione(), dtIniVal, dtCessazione,
				enteProdutCorrisp.getNmEnteSiam(), nmEnteNonConvenz);
		    }

		    // Salva l'ente non convenzionato
		    EnteNonConvenzionatoBean creazioneBean = new EnteNonConvenzionatoBean(
			    getForm().getEnteNonConvenzionatoDetail());
		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    if (getForm().getListaEntiNonConvenzionati().getStatus()
			    .equals(Status.insert)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
			Long idEnteNonConvenzionato = entiNonConvenzionatiEjb
				.saveEnteNonConvenzionato(param,
					new BigDecimal(getUser().getIdUtente()), creazioneBean);
			if (idEnteNonConvenzionato != null) {
			    getForm().getEnteNonConvenzionatoDetail().getId_ente_siam()
				    .setValue(idEnteNonConvenzionato.toString());
			}
			OrgEnteSiamRowBean row = new OrgEnteSiamRowBean();
			getForm().getEnteNonConvenzionatoDetail().copyToBean(row);
			row.setBigDecimal("id_ente_siam", row.getIdEnteSiam());
			row.setString("nm_ente_siam", row.getNmEnteSiam());
			getForm().getListaEntiNonConvenzionati().getTable().last();
			getForm().getListaEntiNonConvenzionati().getTable().add(row);
			//
			loadDettaglioEnteNonConvenzionato(
				BigDecimal.valueOf(idEnteNonConvenzionato));
			getForm().getEnteNonConvenzionatoDetail().setViewMode();
			getForm().getListaEntiNonConvenzionati().setStatus(Status.view);
			getForm().getEnteNonConvenzionatoDetail().setStatus(Status.view);
			getMessageBox().addInfo("Ente non convenzionato salvato con successo");
			getMessageBox().setViewMode(ViewMode.plain);
		    } else if (getForm().getListaEntiNonConvenzionati().getStatus()
			    .equals(Status.update)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
			BigDecimal idEnteNonConvenzionato = getForm()
				.getEnteNonConvenzionatoDetail().getId_ente_siam().parse();
			if (ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO.name()
				.equals(tiEnteNonConvenz)) {
			    HashMap<String, Date[]> mappaSuptFornitEst = new HashMap<>();
			    // Richiamo il controllo inclusione dato padre passando come entità
			    // padre = fornitore
			    // e come entità figlio gli eventuali supporti al fornitore indicati in
			    // ORG_SUPT_ESTERNO_ENTE_CONVENZ, per verificare che la validità di ogni
			    // supporto sia
			    // inclusa in validità fornitore
			    OrgSuptEsternoEnteConvenzTableBean suptEsternoEnteConvenzTableBean = entiNonConvenzionatiEjb
				    .getOrgEntiSupportatiTableBean(idEnteNonConvenzionato);
			    Iterator<OrgSuptEsternoEnteConvenzRowBean> it = suptEsternoEnteConvenzTableBean
				    .iterator();
			    while (it.hasNext()) {
				OrgSuptEsternoEnteConvenzRowBean suptEsternoEnteConvenzRowBean = it
					.next();
				Date[] dtArr = new Date[2];
				dtArr[0] = suptEsternoEnteConvenzRowBean.getDtIniVal();
				dtArr[1] = suptEsternoEnteConvenzRowBean.getDtFinVal();
				mappaSuptFornitEst.put(
					entiNonConvenzionatiEjb.getNmEnteSiam(
						suptEsternoEnteConvenzRowBean.getIdEnteProdut()),
					dtArr);

			    }
			    entiNonConvenzionatiEjb.checkInclusionePadreFigli(dtIniVal,
				    dtCessazione, nmEnteNonConvenz, mappaSuptFornitEst);
			    // Richiamo il controllo inclusione dato padre passando come entità
			    // padre = ente
			    // convenzionato supportato
			    // e come entità figlio gli eventuali supporti al fornitore indicati in
			    // ORG_SUPT_ESTERNO_ENTE_CONVENZ,
			    // per verificare che la validità di ogni supporto sia inclusa in
			    // validità ente
			    // convenzionato
			    if (idEnteSiamProdutCorrisp != null) {
				OrgEnteSiamRowBean enteProdutCorrisp = entiNonConvenzionatiEjb
					.getOrgEnteSiamRowBean(idEnteSiamProdutCorrisp);
				entiNonConvenzionatiEjb.checkInclusionePadreFigli(
					enteProdutCorrisp.getDtIniVal(),
					enteProdutCorrisp.getDtCessazione(),
					enteProdutCorrisp.getNmEnteSiam(), mappaSuptFornitEst);
			    }
			} else if (ConstOrgEnteSiam.TiEnteNonConvenz.ORGANO_VIGILANZA.name()
				.equals(tiEnteNonConvenz)) {
			    HashMap<String, Date[]> mappaAccordiVigil = new HashMap<>();
			    // Richiamo il controllo inclusione dato padre passando come entità
			    // padre = ente non
			    // convenzionato
			    // e come entità figlio gli eventuali accordi di vigilanza definiti
			    // sull’organo indicati in
			    // ORG_ACCORDO_VIGIL
			    OrgAccordoVigilTableBean accordoVigilTableBean = entiNonConvenzionatiEjb
				    .getOrgAccordoVigilTableBean(idEnteNonConvenzionato);
			    Iterator<OrgAccordoVigilRowBean> it = accordoVigilTableBean.iterator();
			    while (it.hasNext()) {
				OrgAccordoVigilRowBean accordoVigilRowBean = it.next();
				Date[] dtArr = new Date[2];
				dtArr[0] = accordoVigilRowBean.getDtIniVal();
				dtArr[1] = accordoVigilRowBean.getDtFinVal();
				mappaAccordiVigil.put(entiNonConvenzionatiEjb.getNmEnteSiam(
					accordoVigilRowBean.getIdEnteConserv()), dtArr); // TODO:
											 // VERIFICARE

			    }
			    entiNonConvenzionatiEjb.checkInclusionePadreFigli(dtIniVal,
				    dtCessazione, nmEnteNonConvenz, mappaAccordiVigil);
			} else if (ConstOrgEnteSiam.TiEnteNonConvenz.SOGGETTO_ATTUATORE.name()
				.equals(tiEnteNonConvenz)) {
			    HashMap<String, Date[]> mappaSuptFornitEst = new HashMap<>();
			    // Richiamo il controllo inclusione dato padre passando come entità
			    // padre = fornitore
			    // e come entità figlio gli eventuali supporti al fornitore indicati in
			    // ORG_SUPT_ESTERNO_ENTE_CONVENZ, per verificare che la validità di ogni
			    // supporto sia
			    // inclusa in validità fornitore
			    OrgSuptEsternoEnteConvenzTableBean suptEsternoEnteConvenzTableBean = entiNonConvenzionatiEjb
				    .getOrgEntiSupportatiTableBean(idEnteNonConvenzionato);
			    Iterator<OrgSuptEsternoEnteConvenzRowBean> it = suptEsternoEnteConvenzTableBean
				    .iterator();
			    while (it.hasNext()) {
				OrgSuptEsternoEnteConvenzRowBean suptEsternoEnteConvenzRowBean = it
					.next();
				Date[] dtArr = new Date[2];
				dtArr[0] = suptEsternoEnteConvenzRowBean.getDtIniVal();
				dtArr[1] = suptEsternoEnteConvenzRowBean.getDtFinVal();
				mappaSuptFornitEst.put(
					entiNonConvenzionatiEjb.getNmEnteSiam(
						suptEsternoEnteConvenzRowBean.getIdEnteProdut()),
					dtArr);

			    }
			    entiNonConvenzionatiEjb.checkInclusionePadreFigli(dtIniVal,
				    dtCessazione, nmEnteNonConvenz, mappaSuptFornitEst);
			    // Richiamo il controllo inclusione dato padre passando come entità
			    // padre = ente
			    // convenzionato supportato
			    // e come entità figlio gli eventuali supporti al fornitore indicati in
			    // ORG_SUPT_ESTERNO_ENTE_CONVENZ, per verificare che la validità di ogni
			    // supporto sia
			    // inclusa in validità ente convenzionato
			    if (idEnteSiamProdutCorrisp != null) {
				OrgEnteSiamRowBean enteProdutCorrisp = entiNonConvenzionatiEjb
					.getOrgEnteSiamRowBean(idEnteSiamProdutCorrisp);
				entiNonConvenzionatiEjb.checkInclusionePadreFigli(
					enteProdutCorrisp.getDtIniVal(),
					enteProdutCorrisp.getDtCessazione(),
					enteProdutCorrisp.getNmEnteSiam(), mappaSuptFornitEst);
			    }
			}

			checkAndSaveModificaEnteNonConvenz(param, idEnteNonConvenzionato,
				creazioneBean);
		    }
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
	    }
	}

	forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_NON_CONVENZIONATO);
    }

    private void checkAndSaveModificaEnteNonConvenz(LogParam param, BigDecimal idEnteNonConvenz,
	    EnteNonConvenzionatoBean creazioneBean) throws ParerUserError, EMFError {
	try {
	    BigDecimal idEnteSiamProdutCorrisp = creazioneBean.getId_ente_siam_produt_corrisp();
	    BigDecimal idEnteProdutCorrispSaved = entiNonConvenzionatiEjb
		    .getOrgEnteSiamRowBean(idEnteNonConvenz).getIdEnteProdutCorrisp();
	    if ((idEnteProdutCorrispSaved != null
		    && !idEnteProdutCorrispSaved.equals(idEnteSiamProdutCorrisp))
		    || (idEnteProdutCorrispSaved == null
			    && creazioneBean.getId_ente_siam_produt_corrisp() != null)) {
		entiNonConvenzionatiEjb.checkAppartUtenteOrganizEnteConvenzCorrisp(idEnteNonConvenz,
			idEnteProdutCorrispSaved);
	    }
	    eseguiSalvataggioModificaEnteNonConvenz(param, idEnteNonConvenz, creazioneBean);
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
    }

    public void eseguiSalvataggioModificaEnteNonConvenz(LogParam param, BigDecimal idEnteSiam,
	    EnteNonConvenzionatoBean creazioneBean) throws ParerUserError, EMFError {
	entiNonConvenzionatiEjb.saveEnteNonConvenzionato(param,
		new BigDecimal(getUser().getIdUtente()), idEnteSiam, creazioneBean);
	//
	loadDettaglioEnteNonConvenzionato(idEnteSiam);
	getForm().getEnteNonConvenzionatoDetail().setViewMode();
	getForm().getListaEntiNonConvenzionati().setStatus(Status.view);
	getForm().getEnteNonConvenzionatoDetail().setStatus(Status.view);

	getMessageBox().addInfo("Ente non convenzionato salvato con successo");
	getMessageBox().setViewMode(ViewMode.plain);
	getSession().removeAttribute("attributiSalvataggioModificaEnteNonConvenz");
	forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_NON_CONVENZIONATO);
    }

    private void loadCurrentDettaglioEnteNonConvenzFromList(String listName)
	    throws EMFError, ParerUserError {
	actionLogger.info("Caricamento dettaglio ente non convenz da ricerca enti");
	// Salvo in sessione il "tipo" di lista dalla quale "proviene" l'ente, sarà
	// ListaEntiNonConvenzionati
	getSession().setAttribute("navTableEntiNonConvenz", listName);
	// Prendendo i dati dalla form, popolo un oggetto List generico del tipo gestito dal
	// framework naturalmente
	it.eng.spagoLite.form.list.List formList = ((it.eng.spagoLite.form.list.List) getForm()
		.getComponent(listName));
	// Ricavo la riga corrente, recuperata dall'oggetto Table della lista
	BaseRowInterface listRow = formList.getTable().getCurrentRow();
	// Inizializzo i campi di dettaglio
	initEnteNonConvenzionatoDetail();
	// Se nullo il relativo parametro, siamo per forza al primo livello di annidamento
	BigDecimal level = getForm().getEnteNonConvenzionatoDetail().getLevel_enti_convenz()
		.parse() != null
			? getForm().getEnteNonConvenzionatoDetail().getLevel_enti_convenz().parse()
			: BigDecimal.ONE;
	// Carico il dettaglio ente non convenz
	loadDettaglioEnteNonConvenzionato(listRow.getBigDecimal("id_ente_siam"));
	getForm().getEnteNonConvenzionatoDetail().getLevel_enti_convenz()
		.setValue(BigDecimal.ONE.toPlainString());
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
	NavigatorDetailBeanManager.pushNavigatorDetailStack(listRow.getBigDecimal("id_ente_siam"),
		formList.getName(), formList.getTable(), 1);
    }

    private void loadDettaglioEnteNonConvenzionato(BigDecimal idEnteSiam)
	    throws ParerUserError, EMFError {
	// Carico entrambe le combo per mostrare l'ambito completo
	getForm().getEnteNonConvenzionatoDetail().getCd_ambito_territ_provincia()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiNonConvenzionatiEjb.getOrgAmbitoTerritTableBean(
				ConstOrgAmbitoTerrit.TiAmbitoTerrit.PROVINCIA.toString()),
			"id_ambito_territ", "cd_ambito_territ"));
	getForm().getEnteNonConvenzionatoDetail().getCd_ambito_territ_forma_associata()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiNonConvenzionatiEjb.getOrgAmbitoTerritTableBean(
				ConstOrgAmbitoTerrit.TiAmbitoTerrit.FORMA_ASSOCIATA.toString()),
			"id_ambito_territ", "cd_ambito_territ"));
	//
	OrgEnteSiamRowBean detailRow = entiNonConvenzionatiEjb.getOrgEnteSiamRowBean(idEnteSiam);
	if (detailRow.getIdEnteProdutCorrisp() != null) {
	    BigDecimal idAmbienteEnteConvenz = detailRow.getIdAmbienteEnteConvenz();
	    getForm().getEnteNonConvenzionatoDetail().getId_ente_siam_produt_corrisp()
		    .setDecodeMap(DecodeMap.Factory.newInstance(
			    entiNonConvenzionatiEjb.getOrgVRicEnteConvenzAbilAccordoValidoTableBean(
				    new BigDecimal(getUser().getIdUtente()), idAmbienteEnteConvenz),
			    "id_ente_convenz", "nm_ente_convenz"));
	}
	getForm().getEnteNonConvenzionatoDetail().copyFromBean(detailRow);
	getForm().getEnteNonConvenzionatoDetail().setViewMode();
	getForm().getEnteNonConvenzionatoDetail().setStatus(Status.view);
	getForm().getListaEntiNonConvenzionati().setStatus(Status.view);

	/* Popolamento liste */
	if (ConstOrgEnteSiam.TiEnteNonConvenz.ORGANO_VIGILANZA.name()
		.equals(detailRow.getTiEnteNonConvenz())) {
	    // Accordi di vigilanza
	    getForm().getAccordiVigilList()
		    .setTable(entiNonConvenzionatiEjb.getOrgAccordoVigilTableBean(idEnteSiam));
	    getForm().getAccordiVigilList().getTable().setPageSize(10);
	    getForm().getAccordiVigilList().getTable().first();
	} else if (ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO.name()
		.equals(detailRow.getTiEnteNonConvenz())) {
	    // Enti supportati
	    getForm().getEntiSupportatiList()
		    .setTable(entiNonConvenzionatiEjb.getOrgEntiSupportatiTableBean(idEnteSiam));
	    getForm().getEntiSupportatiList().getTable().setPageSize(10);
	    getForm().getEntiSupportatiList().getTable().first();
	} else if (ConstOrgEnteSiam.TiEnteNonConvenz.SOGGETTO_ATTUATORE.name()
		.equals(detailRow.getTiEnteNonConvenz())) {
	    // Enti supportati
	    getForm().getEntiSupportatiList()
		    .setTable(entiNonConvenzionatiEjb.getOrgEntiSupportatiTableBean(idEnteSiam));
	    getForm().getEntiSupportatiList().getTable().setPageSize(10);
	    getForm().getEntiSupportatiList().getTable().first();
	}
	// Utenti ente non convenzionato
	getForm().getUtentiEnteList()
		.setTable(entiNonConvenzionatiEjb.getUsrUserEnteNoconvenzTableBean(idEnteSiam,
			Arrays.asList(ConstUsrStatoUser.TiStatotUser.ATTIVO.name(),
				ConstUsrStatoUser.TiStatotUser.DISATTIVO.name()),
			Arrays.asList(ApplEnum.TipoUser.PERSONA_FISICA.name(),
				ApplEnum.TipoUser.AUTOMA.name(),
				ApplEnum.TipoUser.NON_DI_SISTEMA.name())));
	getForm().getUtentiEnteList().getTable().setPageSize(10);
	getForm().getUtentiEnteList().getTable().first();

	if (ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO.name()
		.equals(detailRow.getTiEnteNonConvenz())) {
	    // Versatori associati
	    getForm().getVersatoriAssociatiList()
		    .setTable(entiNonConvenzionatiEjb.getOrgEnteConvenzOrgTableBean(idEnteSiam));
	    getForm().getVersatoriAssociatiList().getTable().setPageSize(10);
	    getForm().getVersatoriAssociatiList().getTable().first();
	    // Sistemi versanti
	    getForm().getSistemiVersantiList()
		    .setTable(entiNonConvenzionatiEjb.getAplSistemaVersanteTableBean(idEnteSiam));
	    getForm().getSistemiVersantiList().getTable().setPageSize(10);
	    getForm().getSistemiVersantiList().getTable().first();
	}
	if (ConstOrgEnteSiam.TiEnteNonConvenz.SOGGETTO_ATTUATORE.name()
		.equals(detailRow.getTiEnteNonConvenz())) {
	    // Versatori associati
	    getForm().getVersatoriAssociatiList()
		    .setTable(entiNonConvenzionatiEjb.getOrgEnteConvenzOrgTableBean(idEnteSiam));
	    getForm().getVersatoriAssociatiList().getTable().setPageSize(10);
	    getForm().getVersatoriAssociatiList().getTable().first();
	    // Sistemi versanti
	    getForm().getSistemiVersantiList()
		    .setTable(entiNonConvenzionatiEjb.getAplSistemaVersanteTableBean(idEnteSiam));
	    getForm().getSistemiVersantiList().getTable().setPageSize(10);
	    getForm().getSistemiVersantiList().getTable().first();
	}
	// Utenti archivisti
	getForm().getUtentiArchivistiList()
		.setTable(entiNonConvenzionatiEjb.getOrgEnteArkRifTableBean(idEnteSiam));
	getForm().getUtentiArchivistiList().getTable().setPageSize(10);
	getForm().getUtentiArchivistiList().getTable().first();
	// Referenti ente non convenzionato
	getForm().getReferentiEnteList()
		.setTable(entiNonConvenzionatiEjb.getOrgEnteUserRifTableBean(idEnteSiam));
	getForm().getReferentiEnteList().getTable().setPageSize(10);
	getForm().getReferentiEnteList().getTable().first();
    }

    // <editor-fold defaultstate="expand" desc="Gestione dettaglio accordo di vigilanza">
    private void initAccordoVigilDetail() throws ParerUserError, EMFError {
	getForm().getAccordoVigilDetail().getId_ente_conserv()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgEnteSiamTableBeanByTiEnteConvenz(
				ConstOrgEnteSiam.TiEnteConvenz.AMMINISTRATORE,
				ConstOrgEnteSiam.TiEnteConvenz.CONSERVATORE),
			"id_ente_siam", "nm_ente_siam"));
    }

    private void loadDettaglioAccordoVigil(BigDecimal idAccordoVigil)
	    throws ParerUserError, EMFError {

	// Carico il dettaglio accordo di vigilanza
	OrgAccordoVigilRowBean detailRow = entiNonConvenzionatiEjb
		.getOrgAccordoVigilRowBean(idAccordoVigil);

	// Carico la combo "Registro", va messa qui e non in initAccordoVigilDetail perchè
	// dipende dal valore dei parametri di gestione
	String nmEnte = detailRow.getNmEnte();
	String nmStrut = detailRow.getNmStrut();
	BigDecimal idStrut = entiConvenzionatiEjb.getIdStrut(nmEnte, nmStrut);
	getForm().getAccordoVigilDetail().getCd_registro_repertorio()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getRegistriStrutturaTableBean(idStrut), "cd_registro",
			"cd_registro"));

	getForm().getAccordoVigilDetail().copyFromBean(detailRow);

	// Metto tutto in view mode
	getForm().getAccordoVigilDetail().setViewMode();
	getForm().getAccordoVigilDetail().setStatus(Status.view);
	getForm().getAccordiVigilList().setStatus(Status.view);

	// Popolamento lista enti produttori vigilati
	getForm().getEntiVigilatiList()
		.setTable(entiNonConvenzionatiEjb.getOrgVigilEnteProdutTableBean(idAccordoVigil));
	getForm().getEntiVigilatiList().getTable().setPageSize(10);
	getForm().getEntiVigilatiList().getTable().first();
    }

    // </editor-fold>
    // <editor-fold defaultstate="expand" desc="Gestione dettaglio ente supportato">
    private void initEnteSuptDetail() throws ParerUserError, EMFError {
	getForm().getEnteSupportatoDetail().getId_ambiente_ente_convenz()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiNonConvenzionatiEjb.getUsrVAbilAmbEnteXEnteTableBean(
				new BigDecimal(getUser().getIdUtente())),
			"id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));

	getForm().getEnteSupportatoDetail().getId_ente_convenz().setDecodeMap(new DecodeMap());
    }

    private void loadDettaglioEnteSupt(BigDecimal idSuptEstEnteConvenz)
	    throws ParerUserError, EMFError {

	// Carico il dettaglio ente supportato
	OrgSuptEsternoEnteConvenzRowBean detailRow = entiNonConvenzionatiEjb
		.getOrgSuptEsternoEnteConvenzRowBean(idSuptEstEnteConvenz);
	getForm().getEnteSupportatoDetail().copyFromBean(detailRow);

	// Metto tutto in view mode
	getForm().getEnteSupportatoDetail().setViewMode();
	getForm().getEnteSupportatoDetail().setStatus(Status.view);
	getForm().getEntiSupportatiList().setStatus(Status.view);
    }

    private void saveEnteSupportato() throws EMFError {
	if (getForm().getEnteSupportatoDetail().postAndValidate(getRequest(), getMessageBox())) {
	    // CONTROLLI DI COERENZA NELLA MASCHERA
	    BigDecimal idEnteConvenzSupt = getForm().getEnteSupportatoDetail().getId_ente_convenz()
		    .parse();
	    Date dtIniValSupt = getForm().getEnteSupportatoDetail().getDt_ini_val_supt().parse();
	    Date dtFinValSupt = getForm().getEnteSupportatoDetail().getDt_fin_val_supt().parse();
	    String tipoEnte = getForm().getEnteNonConvenzionatoDetail().getTi_ente_non_convenz()
		    .parse();

	    if (dtFinValSupt.before(dtIniValSupt)) {
		getMessageBox().addError(
			"La data di inizio validità non può essere successiva alla data di fine validità");
	    }

	    try {
		if (!getMessageBox().hasError()) {
		    // Salva l'associazione al nuovo ente supportato
		    BigDecimal idEnteFornitEst = getForm().getEnteNonConvenzionatoDetail()
			    .getId_ente_siam().parse();
		    BigDecimal idEnteSiamProdutCorrisp = getForm().getEnteNonConvenzionatoDetail()
			    .getId_ente_siam_produt_corrisp().parse();
		    String nmEnteFornitEst = getForm().getEnteNonConvenzionatoDetail()
			    .getNm_ente_siam().parse();
		    Date dtIniVal = getForm().getEnteNonConvenzionatoDetail().getDt_ini_val()
			    .parse();
		    Date dtCessazione = getForm().getEnteNonConvenzionatoDetail().getDt_cessazione()
			    .parse();
		    String nmEnteConvenzSupt = entiNonConvenzionatiEjb
			    .getNmEnteSiam(idEnteConvenzSupt);
		    BigDecimal idSuptEstEnteConvenz = getForm().getEnteSupportatoDetail()
			    .getId_supt_est_ente_convenz().parse();

		    // Verifico se la coppia ente non convenzionato / ente convenzionato supportato
		    // sia già presente
		    // nella tabella ORG_SUPT_ESTERNO_ENTE_CONVENZ;
		    // se già presente, si verifica che le date indicate nel presente supporto non
		    // siano sovrapposte a
		    // quelle dei supporti precedentemente indicati.
		    entiNonConvenzionatiEjb.checkEnteSuptPerEnteFornit(idEnteFornitEst,
			    idEnteConvenzSupt, dtIniValSupt, dtFinValSupt, idSuptEstEnteConvenz);

		    // Se è stato indicato un ente produttore corrispondente, richiamo il controllo
		    // inclusione dato
		    // figlio
		    // passando come entità figlio = supporto e entità padre = ente produttore
		    // corrispondente.
		    if (idEnteSiamProdutCorrisp != null) {
			OrgEnteSiamRowBean enteProdutCorrisp = entiNonConvenzionatiEjb
				.getOrgEnteSiamRowBean(idEnteSiamProdutCorrisp);
			entiNonConvenzionatiEjb.checkInclusioneFiglio(
				enteProdutCorrisp.getDtIniVal(),
				enteProdutCorrisp.getDtCessazione(), dtIniValSupt, dtFinValSupt,
				enteProdutCorrisp.getNmEnteSiam(), nmEnteConvenzSupt);
		    }

		    // richiamo il controllo inclusione dato figlio (UC_CONTR_INCLUSIONE_FIGLIO)
		    // passando come entità
		    // figlio = supporto
		    // e entità padre = fornitore corrispondente
		    entiNonConvenzionatiEjb.checkInclusioneFiglio(dtIniVal, dtCessazione,
			    dtIniValSupt, dtFinValSupt, nmEnteFornitEst, nmEnteConvenzSupt);

		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    if (getForm().getEntiSupportatiList().getStatus().equals(Status.insert)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
			Long idSuptEsternoEnteConvenz = entiNonConvenzionatiEjb
				.insertOrgSuptEsternoEnteConvenz(param, getUser().getIdUtente(),
					idEnteFornitEst, idEnteConvenzSupt, dtIniValSupt,
					dtFinValSupt, tipoEnte);
			if (idSuptEsternoEnteConvenz != null) {
			    getForm().getEnteSupportatoDetail().getId_supt_est_ente_convenz()
				    .setValue(idSuptEsternoEnteConvenz.toString());
			}

			OrgSuptEsternoEnteConvenzRowBean suptEsternoEnteConvenzRowBean = new OrgSuptEsternoEnteConvenzRowBean();
			getForm().getEnteSupportatoDetail()
				.copyToBean(suptEsternoEnteConvenzRowBean);
			getForm().getEntiSupportatiList().getTable().last();
			getForm().getEntiSupportatiList().getTable()
				.add(suptEsternoEnteConvenzRowBean);
		    } else if (getForm().getEntiSupportatiList().getStatus()
			    .equals(Status.update)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
			BigDecimal idSuptEsternoEnteConvenz = getForm().getEnteSupportatoDetail()
				.getId_supt_est_ente_convenz().parse();
			entiNonConvenzionatiEjb.updateOrgSuptEsternoEnteConvenz(param,
				idSuptEsternoEnteConvenz, idEnteFornitEst, dtIniValSupt,
				dtFinValSupt, tipoEnte);
		    }

		    loadDettaglioEnteNonConvenzionato(idEnteFornitEst);
		    getMessageBox()
			    .addInfo("Associazione dell'ente supportato salvata con successo");
		    getMessageBox().setViewMode(ViewMode.plain);
		    goBackTo(Application.Publisher.DETTAGLIO_ENTE_NON_CONVENZIONATO);
		} else {
		    forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_SUPT);
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
		forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_SUPT);
	    }
	}
    }

    @Override
    public void updateEntiSupportatiList() throws EMFError {
	try {
	    OrgSuptEsternoEnteConvenzRowBean currentRow = (OrgSuptEsternoEnteConvenzRowBean) getForm()
		    .getEntiSupportatiList().getTable().getCurrentRow();
	    if (!entiNonConvenzionatiEjb.checkUserAbilEnteConvenz(
		    new BigDecimal(getUser().getIdUtente()), currentRow.getIdEnteProdut())) {
		getMessageBox().addError(
			"Modifica dell'ente non consentita in quanto l'utente non è abilitato");
		forwardToPublisher(getLastPublisher());
	    }
	    if (!getMessageBox().hasError()) {
		getForm().getEnteSupportatoDetail().setEditMode();
		getForm().getEnteSupportatoDetail().getId_ambiente_ente_convenz().setViewMode();
		getForm().getEnteSupportatoDetail().getNm_ambiente_ente_convenz().setViewMode();
		getForm().getEnteSupportatoDetail().getId_ente_convenz().setViewMode();
		getForm().getEnteSupportatoDetail().getNm_ente_convenz().setViewMode();

		getForm().getEntiSupportatiList().setStatus(Status.update);
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(
		    "L'ente supportato non pu\u00F2 essere modificato: " + ex.getDescription());
	}
    }

    @Override
    public void deleteEntiSupportatiList() throws EMFError {
	OrgSuptEsternoEnteConvenzRowBean currentRow = (OrgSuptEsternoEnteConvenzRowBean) getForm()
		.getEntiSupportatiList().getTable().getCurrentRow();
	BigDecimal idSuptEstEnteConvenz = currentRow.getIdSuptEstEnteConvenz();
	String tipoEnte = getForm().getEnteNonConvenzionatoDetail().getTi_ente_non_convenz()
		.parse();
	int riga = getForm().getEntiSupportatiList().getTable().getCurrentRowIndex();
	// Eseguo giusto un controllo per verificare che io stia prendendo la riga giusta se sono
	// nel dettaglio
	if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_ENTE_SUPT)) {
	    if (!idSuptEstEnteConvenz.equals(
		    getForm().getEnteSupportatoDetail().getId_supt_est_ente_convenz().parse())) {
		getMessageBox()
			.addError("Eccezione imprevista nell'eliminazione dell'ente supportato");
	    }
	}
	if (!getMessageBox().hasError() && idSuptEstEnteConvenz != null) {
	    try {
		if (!entiNonConvenzionatiEjb.checkUserAbilEnteConvenz(
			new BigDecimal(getUser().getIdUtente()), currentRow.getIdEnteProdut())) {
		    getMessageBox().addError(
			    "Eliminazione dell'ente non consentita in quanto l'utente non è abilitato");
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
			    .equalsIgnoreCase(Application.Publisher.DETTAGLIO_ENTE_SUPT)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
		    } else {
			param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
				getForm().getEntiSupportatiList()));
		    }

		    // Determino gli utenti appartenenti all'ENTE CONVENZIONATO rimosso dal
		    // supporto, perchè poi su di
		    // essi dovrò rimuove l'abilitazione all'ente NON convenzionato di tipo
		    // fornitore esterno (visto che
		    // ora non mi supporta più...). L'elenco esclude gli utenti CESSATI
		    SortedSet<String> listaUtenti = entiNonConvenzionatiEjb
			    .retrieveUsrUserEnteSiamSupt(idSuptEstEnteConvenz);

		    if (!listaUtenti.isEmpty()) {
			getRequest().setAttribute("numeroUtentiSupporto", listaUtenti.size());
			getRequest().setAttribute("listaUtentiSupporto",
				listaUtenti.toString().replace("[", "").replace("]", ""));
			getRequest().setAttribute("customDeleteSupportoMessageBox", true);
			Object[] attributiDeleteSupporto = new Object[4];
			attributiDeleteSupporto[0] = param;
			attributiDeleteSupporto[1] = idSuptEstEnteConvenz;
			attributiDeleteSupporto[2] = tipoEnte;
			attributiDeleteSupporto[3] = riga;
			getSession().setAttribute("attributiDeleteSupporto",
				attributiDeleteSupporto);
		    } else {
			eseguiDeleteSupporto(param, idSuptEstEnteConvenz, tipoEnte, riga);
		    }
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(
			"L'ente supportato non pu\u00F2 essere eliminato: " + ex.getDescription());
		forwardToPublisher(getLastPublisher());
	    }
	}
	forwardToPublisher(getLastPublisher());
    }

    public void eseguiDeleteSupporto(LogParam param, BigDecimal idSuptEstEnteConvenz,
	    String tipoEnte, int riga) throws EMFError {
	try {
	    entiNonConvenzionatiEjb.deleteOrgSuptEsternoEnteConvenz(param, idSuptEstEnteConvenz,
		    tipoEnte);
	    getForm().getEntiSupportatiList().getTable().remove(riga);
	    getMessageBox().addInfo("Ente supportato eliminato con successo");
	    getMessageBox().setViewMode(ViewMode.plain);
	    // Questa delete, come tutte, passa per la finestra modale di "Conferma" cancellazione
	    // che attraverso il
	    // framework "aggiunge" un elemento alla pila di esecuzione. Va dunque rimosso onde
	    // evitare di avere
	    // un elemento ripetuto nella pila che comporterebbe la necessità di cliccare 2 volte il
	    // tasto Indietro
	    // durante la navigazione
	    SessionManager.removeLastExecutionHistory(getSession());
	} catch (ParerUserError ex) {
	    getMessageBox().addError(
		    "L'ente supportato non pu\u00F2 essere eliminato: " + ex.getDescription());
	    forwardToPublisher(getLastPublisher());
	}

	if (!getMessageBox().hasError()
		&& getLastPublisher().equals(Application.Publisher.DETTAGLIO_ENTE_SUPT)) {
	    goBack();
	} else {
	    forwardToPublisher(getLastPublisher());
	}

    }

    public void confermaDeleteSupporto() throws EMFError {
	if (getSession().getAttribute("attributiDeleteSupporto") != null) {
	    Object[] attributiDeleteSupporto = (Object[]) getSession()
		    .getAttribute("attributiDeleteSupporto");
	    LogParam param = (LogParam) attributiDeleteSupporto[0];
	    BigDecimal idSuptEstEnteConvenz = (BigDecimal) attributiDeleteSupporto[1];
	    String tipoEnte = (String) attributiDeleteSupporto[2];
	    int riga = (int) attributiDeleteSupporto[3];
	    eseguiDeleteSupporto(param, idSuptEstEnteConvenz, tipoEnte, riga);
	}
    }

    public void annullaDeleteSupporto() {
	getSession().removeAttribute("attributiDeleteSupporto");
	if (!getMessageBox().hasError()
		&& getLastPublisher().equals(Application.Publisher.DETTAGLIO_ENTE_SUPT)) {
	    goBack();
	} else {
	    forwardToPublisher(getLastPublisher());
	    SessionManager.removeLastExecutionHistory(getSession());
	}
    }

    // </editor-fold>
    @Override
    public void updateUtentiArchivistiList() throws EMFError {
	try {
	    Integer riga = Integer.parseInt(getRequest().getParameter("riga"));
	    BigDecimal idEnteArkRif = getForm().getUtentiArchivistiList().getTable().getRow(riga)
		    .getBigDecimal("id_ente_ark_rif");
	    AmministrazioneEntiConvenzionatiForm form = new AmministrazioneEntiConvenzionatiForm();
	    OrgEnteArkRifTableBean enteArkRifTB = new OrgEnteArkRifTableBean();
	    enteArkRifTB.add(entiNonConvenzionatiEjb.getOrgEnteArkRifRowBean(idEnteArkRif));
	    form.getUtentiArchivistiList().setTable(enteArkRifTB);

	    OrgEnteSiamRowBean enteSiamRowBean = entiNonConvenzionatiEjb.getOrgEnteSiamRowBean(
		    getForm().getEnteNonConvenzionatoDetail().getId_ente_siam().parse());
	    form.getEnteConvenzionatoDetail().copyFromBean(enteSiamRowBean);

	    redirectToAction(Application.Actions.AMMINISTRAZIONE_ENTI_CONVENZIONATI,
		    "?operation=listNavigationOnClick&navigationEvent="
			    + ListAction.NE_DETTAGLIO_UPDATE + "&table="
			    + AmministrazioneEntiConvenzionatiForm.UtentiArchivistiList.NAME
			    + "&riga=0",
		    form);
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
    }

    @Override
    public void updateReferentiEnteList() throws EMFError {
	try {
	    Integer riga = Integer.parseInt(getRequest().getParameter("riga"));
	    BigDecimal idEnteUserRif = getForm().getReferentiEnteList().getTable().getRow(riga)
		    .getBigDecimal("id_ente_user_rif");
	    AmministrazioneEntiConvenzionatiForm form = new AmministrazioneEntiConvenzionatiForm();
	    OrgEnteUserRifTableBean enteUserRifTB = new OrgEnteUserRifTableBean();
	    enteUserRifTB.add(entiNonConvenzionatiEjb.getOrgEnteUserRifRowBean(idEnteUserRif));
	    form.getReferentiEnteList().setTable(enteUserRifTB);

	    OrgEnteSiamRowBean enteSiamRowBean = entiNonConvenzionatiEjb.getOrgEnteSiamRowBean(
		    getForm().getEnteNonConvenzionatoDetail().getId_ente_siam().parse());
	    form.getEnteConvenzionatoDetail().copyFromBean(enteSiamRowBean);

	    redirectToAction(Application.Actions.AMMINISTRAZIONE_ENTI_CONVENZIONATI,
		    "?operation=listNavigationOnClick&navigationEvent="
			    + ListAction.NE_DETTAGLIO_UPDATE + "&table="
			    + AmministrazioneEntiConvenzionatiForm.ReferentiEnteList.NAME
			    + "&riga=0",
		    form);
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
    }

    // <editor-fold defaultstate="expand" desc="Gestione utenti archivisti">
    @Override
    public void deleteUtentiArchivistiList() throws EMFError {
	OrgEnteArkRifRowBean currentRow = (OrgEnteArkRifRowBean) getForm().getUtentiArchivistiList()
		.getTable().getCurrentRow();
	BigDecimal idEnteArkRif = currentRow.getIdEnteArkRif();
	int riga = getForm().getUtentiArchivistiList().getTable().getCurrentRowIndex();
	if (!getMessageBox().hasError() && idEnteArkRif != null) {
	    try {
		if (!getMessageBox().hasError()) {
		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    Application.Publisher.DETTAGLIO_ENTE_NON_CONVENZIONATO);
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
			    getForm().getUtentiArchivistiList()));
		    entiNonConvenzionatiEjb.deleteOrgEnteArkRif(param, idEnteArkRif);
		    SessionManager.removeLastExecutionHistory(getSession());
		    getForm().getUtentiArchivistiList().getTable().remove(riga);
		    getMessageBox().addInfo(
			    "Utente archivista eliminato con successo dall'ente non convenzionato");
		    getMessageBox().setViewMode(ViewMode.plain);
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError("L'utente archivista non pu\u00F2 essere eliminato: "
			+ ex.getDescription());
	    }
	} else {
	    getMessageBox().addError("L'utente archivista non pu\u00F2 essere eliminato");
	}

	forwardToPublisher(getLastPublisher());
    }
    // </editor-fold>

    @Override
    public JSONObject triggerEnteNonConvenzionatoDetailId_prov_sede_legaleOnTrigger()
	    throws EMFError {
	getForm().getEnteNonConvenzionatoDetail().post(getRequest());
	BigDecimal idAmbitoTerrit = getForm().getEnteNonConvenzionatoDetail()
		.getId_prov_sede_legale().parse();
	try {
	    OrgAmbitoTerritRowBean ambitoTerritPadre = entiNonConvenzionatiEjb
		    .getOrgAmbitoTerritParentRowBean(idAmbitoTerrit);
	    // Valorizzo le combo Ambito territoriale / Regione e Ambito territoriale / Provincia...
	    getForm().getEnteNonConvenzionatoDetail().getCd_ambito_territ_regione()
		    .setValue("" + ambitoTerritPadre.getIdAmbitoTerrit());
	    List<BigDecimal> padre = new ArrayList<>();
	    padre.add(ambitoTerritPadre.getIdAmbitoTerrit());
	    getForm().getEnteNonConvenzionatoDetail().getCd_ambito_territ_provincia()
		    .setDecodeMap(DecodeMap.Factory.newInstance(
			    entiNonConvenzionatiEjb.getOrgAmbitoTerritTableBean(padre),
			    "id_ambito_territ", "cd_ambito_territ"));
	    getForm().getEnteNonConvenzionatoDetail().getCd_ambito_territ_provincia()
		    .setValue("" + idAmbitoTerrit);
	} catch (ParerUserError e) {
	    getMessageBox().addError(e.getDescription());
	}
	return getForm().getEnteNonConvenzionatoDetail().asJSON();
    }

    @Override
    public void logEventi() throws EMFError {
	GestioneLogEventiForm form = new GestioneLogEventiForm();
	form.getOggettoDetail().getNmApp().setValue(paramHelper.getParamApplicApplicationName());
	String nmTipoOggetto = ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO.name().equals(
		(getForm().getEnteNonConvenzionatoDetail().getTi_ente_non_convenz().parse()))
			? SacerLogConstants.TIPO_OGGETTO_FORNITORE_ESTERNO
			: ConstOrgEnteSiam.TiEnteNonConvenz.ORGANO_VIGILANZA.name()
				.equals((getForm().getEnteNonConvenzionatoDetail()
					.getTi_ente_non_convenz().parse()))
						? SacerLogConstants.TIPO_OGGETTO_ORGANO_VIGILANZA
						: SacerLogConstants.TIPO_OGGETTO_SOGGETTO_ATTUATORE;
	form.getOggettoDetail().getNm_tipo_oggetto().setValue(nmTipoOggetto);
	form.getOggettoDetail().getIdOggetto()
		.setValue(getForm().getEnteNonConvenzionatoDetail().getId_ente_siam().getValue());
	redirectToAction(it.eng.parer.sacerlog.slite.gen.Application.Actions.GESTIONE_LOG_EVENTI,
		"?operation=inizializzaLogEventi", form);
    }

    @Override
    protected void postLoad() {
	super.postLoad();
	Object ogg = getForm();
	if (ogg instanceof AmministrazioneEntiNonConvenzionatiForm) {
	    AmministrazioneEntiNonConvenzionatiForm form = (AmministrazioneEntiNonConvenzionatiForm) ogg;
	    if (form.getListaEntiNonConvenzionati().getStatus().equals(Status.view)) {
		getForm().getEnteNonConvenzionatoDetail().getId_ambiente_ente_convenz()
			.setHidden(true);
		getForm().getEnteNonConvenzionatoDetail().getId_ente_siam_produt_corrisp()
			.setHidden(true);
		getForm().getEnteNonConvenzionatoDetail().getNm_ambiente_ente_convenz()
			.setHidden(false);
		getForm().getEnteNonConvenzionatoDetail().getNm_ente_siam_produt_corrisp()
			.setHidden(false);
	    } else {
		if (getForm().getEnteNonConvenzionatoDetail().getId_ente_siam_produt_corrisp()
			.getDecodeMap() != null) {
		    if (!getForm().getEnteNonConvenzionatoDetail().getId_ente_siam_produt_corrisp()
			    .getDecodeMap().isEmpty()) {
			getForm().getEnteNonConvenzionatoDetail().getId_ambiente_ente_convenz()
				.setHidden(false);
			getForm().getEnteNonConvenzionatoDetail().getId_ente_siam_produt_corrisp()
				.setHidden(false);
			getForm().getEnteNonConvenzionatoDetail().getNm_ambiente_ente_convenz()
				.setHidden(true);
			getForm().getEnteNonConvenzionatoDetail().getNm_ente_siam_produt_corrisp()
				.setHidden(true);
		    } else {
			getForm().getEnteNonConvenzionatoDetail().getId_ambiente_ente_convenz()
				.setHidden(true);
			getForm().getEnteNonConvenzionatoDetail().getId_ente_siam_produt_corrisp()
				.setHidden(true);
			getForm().getEnteNonConvenzionatoDetail().getNm_ambiente_ente_convenz()
				.setHidden(false);
			getForm().getEnteNonConvenzionatoDetail().getNm_ente_siam_produt_corrisp()
				.setHidden(false);
		    }
		} else {
		    getForm().getEnteNonConvenzionatoDetail().getId_ambiente_ente_convenz()
			    .setHidden(false);
		    getForm().getEnteNonConvenzionatoDetail().getId_ente_siam_produt_corrisp()
			    .setHidden(false);
		    getForm().getEnteNonConvenzionatoDetail().getNm_ambiente_ente_convenz()
			    .setHidden(true);
		    getForm().getEnteNonConvenzionatoDetail().getNm_ente_siam_produt_corrisp()
			    .setHidden(true);
		}
	    }

	    if (form.getListaEntiNonConvenzionati().getStatus().equals(Status.view)) {
		form.getEnteNonConvenzionatoDetail().getLogEventi().setEditMode();
		form.getEnteNonConvenzionatoDetail().getLogEventi().setHidden(false);
	    } else {
		form.getEnteNonConvenzionatoDetail().getLogEventi().setViewMode();
		form.getEnteNonConvenzionatoDetail().getLogEventi().setHidden(true);
	    }

	    if (getForm().getAccordoVigilDetail().getStatus() != null) {

		if (getForm().getAccordoVigilDetail().getStatus().equals(Status.view)) {
		    try {
			if (getForm().getAccordoVigilDetail().getCd_registro_repertorio()
				.parse() != null
				&& getForm().getAccordoVigilDetail().getAa_repertorio()
					.parse() != null
				&& getForm().getAccordoVigilDetail().getCd_key_repertorio()
					.parse() != null) {
			    getForm().getUdButtonList().getScaricaCompFileUdAccordoVigil()
				    .setDisableHourGlass(true);
			    getForm().getUdButtonList().getScaricaCompFileUdAccordoVigil()
				    .setEditMode();
			} else {
			    getForm().getUdButtonList().getScaricaCompFileUdAccordoVigil()
				    .setViewMode();
			}
		    } catch (EMFError ex) {
			getMessageBox().addError(
				"Errore durante il caricamento del bottone Scarica file unit\u00E0  documentaria");
		    }
		} else {
		    getForm().getUdButtonList().getScaricaCompFileUdAccordoVigil().setViewMode();
		}
	    }
	}
    }

    @Override
    public void deleteReferentiEnteList() throws EMFError {
	OrgEnteUserRifRowBean currentRow = (OrgEnteUserRifRowBean) getForm().getReferentiEnteList()
		.getTable().getCurrentRow();
	BigDecimal idEnteUserRif = currentRow.getIdEnteUserRif();
	int riga = getForm().getReferentiEnteList().getTable().getCurrentRowIndex();
	if (!getMessageBox().hasError() && idEnteUserRif != null) {
	    try {
		if (!getMessageBox().hasError()) {
		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    Application.Publisher.DETTAGLIO_ENTE_NON_CONVENZIONATO);
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
			    getForm().getReferentiEnteList()));
		    entiNonConvenzionatiEjb.deleteOrgEnteUserRif(param, idEnteUserRif);
		    getForm().getReferentiEnteList().getTable().remove(riga);
		    getMessageBox().addInfo(
			    "Utente referente dell'ente eliminato con successo dall'ente non convenzionato");
		    getMessageBox().setViewMode(ViewMode.plain);
		}
	    } catch (ParerUserError ex) {
		getMessageBox()
			.addError("L'utente referente dell'ente non pu\u00F2 essere eliminato: "
				+ ex.getDescription());
	    }
	} else {
	    getMessageBox().addError("L'utente referente dell'ente non pu\u00F2 essere eliminato");
	}

	forwardToPublisher(getLastPublisher());
    }

    @Override
    public JSONObject triggerEnteNonConvenzionatoDetailId_ambiente_ente_convenzOnTrigger()
	    throws EMFError {
	getForm().getEnteNonConvenzionatoDetail().post(getRequest());
	BigDecimal idAmbienteEnteConvenz = getForm().getEnteNonConvenzionatoDetail()
		.getId_ambiente_ente_convenz().parse();
	DecodeMap mappa = new DecodeMap();
	if (idAmbienteEnteConvenz != null) {
	    OrgVRicEnteConvenzTableBean ricEnteConvenz = entiNonConvenzionatiEjb
		    .getOrgVRicEnteConvenzAbilAccordoValidoTableBean(
			    BigDecimal.valueOf(getUser().getIdUtente()), idAmbienteEnteConvenz);
	    mappa.populatedMap(ricEnteConvenz, "id_ente_convenz", "nm_ente_convenz");
	}
	getForm().getEnteNonConvenzionatoDetail().getId_ente_siam_produt_corrisp()
		.setDecodeMap(mappa);
	return getForm().getEnteNonConvenzionatoDetail().asJSON();
    }

    @Override
    public JSONObject triggerEnteNonConvenzionatoDetailCd_ambito_territ_regioneOnTrigger()
	    throws EMFError {
	getForm().getEnteNonConvenzionatoDetail().post(getRequest());
	BigDecimal idAmbitoTerrit = getForm().getEnteNonConvenzionatoDetail()
		.getCd_ambito_territ_regione().parse();
	try {
	    if (idAmbitoTerrit != null) {
		List<BigDecimal> ids = new ArrayList<>();
		ids.add(idAmbitoTerrit);
		getForm().getEnteNonConvenzionatoDetail().getCd_ambito_territ_provincia()
			.setDecodeMap(DecodeMap.Factory.newInstance(
				entiNonConvenzionatiEjb.getOrgAmbitoTerritTableBean(ids),
				"id_ambito_territ", "cd_ambito_territ"));
		getForm().getEnteNonConvenzionatoDetail().getCd_ambito_territ_forma_associata()
			.setDecodeMap(new DecodeMap());
	    } else {
		getForm().getEnteNonConvenzionatoDetail().getCd_ambito_territ_provincia()
			.setDecodeMap(new DecodeMap());
		getForm().getEnteNonConvenzionatoDetail().getCd_ambito_territ_forma_associata()
			.setDecodeMap(new DecodeMap());
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
	return getForm().getEnteNonConvenzionatoDetail().asJSON();
    }

    @Override
    public JSONObject triggerEnteNonConvenzionatoDetailCd_ambito_territ_provinciaOnTrigger()
	    throws EMFError {
	getForm().getEnteNonConvenzionatoDetail().post(getRequest());
	BigDecimal idAmbitoTerrit = getForm().getEnteNonConvenzionatoDetail()
		.getCd_ambito_territ_provincia().parse();
	try {
	    if (idAmbitoTerrit != null) {
		List<BigDecimal> ids = new ArrayList<>();
		ids.add(idAmbitoTerrit);
		getForm().getEnteNonConvenzionatoDetail().getCd_ambito_territ_forma_associata()
			.setDecodeMap(DecodeMap.Factory.newInstance(
				entiNonConvenzionatiEjb.getOrgAmbitoTerritTableBean(ids),
				"id_ambito_territ", "cd_ambito_territ"));
	    } else {
		getForm().getEnteNonConvenzionatoDetail().getCd_ambito_territ_forma_associata()
			.setDecodeMap(new DecodeMap());
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
	return getForm().getEnteNonConvenzionatoDetail().asJSON();
    }

    @Override
    public JSONObject triggerEnteSupportatoDetailId_ambiente_ente_convenzOnTrigger()
	    throws EMFError {
	getForm().getEnteSupportatoDetail().post(getRequest());
	BigDecimal idAmbienteEnteConvenz = getForm().getEnteSupportatoDetail()
		.getId_ambiente_ente_convenz().parse();
	DecodeMap mappa = new DecodeMap();
	if (idAmbienteEnteConvenz != null) {
	    OrgVRicEnteConvenzTableBean ricEnteConvenz = entiNonConvenzionatiEjb
		    .getOrgVRicEnteConvenzAbilAccordoValidoTableBean(
			    BigDecimal.valueOf(getUser().getIdUtente()), idAmbienteEnteConvenz);
	    mappa.populatedMap(ricEnteConvenz, "id_ente_convenz", "nm_ente_convenz");
	}
	getForm().getEnteSupportatoDetail().getId_ente_convenz().setDecodeMap(mappa);
	return getForm().getEnteSupportatoDetail().asJSON();
    }

    @Override
    public JSONObject triggerEnteSupportatoDetailId_ente_convenzOnTrigger() throws EMFError {
	getForm().getEnteSupportatoDetail().post(getRequest());

	DateFormat formato = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH);

	BigDecimal idEnteConvenz = getForm().getEnteSupportatoDetail().getId_ente_convenz().parse();
	Date dtIniVal = getForm().getEnteNonConvenzionatoDetail().getDt_ini_val().parse();
	Date dtFinVal = getForm().getEnteNonConvenzionatoDetail().getDt_cessazione().parse();
	try {
	    if (idEnteConvenz != null) {
		OrgEnteSiamRowBean enteConvenz = entiNonConvenzionatiEjb
			.getOrgEnteSiamRowBean(idEnteConvenz);
		if (enteConvenz.getDtIniVal().after(dtIniVal)) {
		    getForm().getEnteSupportatoDetail().getDt_ini_val_supt()
			    .setValue(formato.format(enteConvenz.getDtIniVal()));
		} else {
		    getForm().getEnteSupportatoDetail().getDt_ini_val_supt()
			    .setValue(formato.format(dtIniVal));
		}
		if (enteConvenz.getDtCessazione().before(dtFinVal)) {
		    getForm().getEnteSupportatoDetail().getDt_fin_val_supt()
			    .setValue(formato.format(enteConvenz.getDtCessazione()));
		} else {
		    getForm().getEnteSupportatoDetail().getDt_fin_val_supt()
			    .setValue(formato.format(dtFinVal));
		}
	    } else {
		getForm().getEnteSupportatoDetail().getDt_ini_val_supt()
			.setValue(formato.format(Calendar.getInstance().getTime()));
		Calendar c = Calendar.getInstance();
		c.set(2444, Calendar.DECEMBER, 31, 0, 0, 0);
		getForm().getEnteSupportatoDetail().getDt_fin_val_supt()
			.setValue(formato.format(c.getTime()));
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
	return getForm().getEnteSupportatoDetail().asJSON();
    }

    public void scaricaCompFileUd(BigDecimal idEntita, String tipoEntita, String registro,
	    BigDecimal anno, String numero) throws EMFError, ParerUserError {
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
	    multipartRequest.add("XMLSIP",
		    getXmlSip(idEntita, tipoEntita, versione, loginname, registro, anno, numero));
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
		try (OutputStream outStream = new FileOutputStream(temp);
			InputStream inputStream = is) {
		    byte[] buffer = new byte[8 * 1024];
		    int bytesRead;
		    while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		    }
		} catch (IOException e) {
		    actionLogger.error("Errore durante il salvataggio del file temporaneo", e);
		    getMessageBox().addError("Errore durante il tentativo di download del file");
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

    private String getXmlSip(BigDecimal idEntita, String tipoEntita, String versione,
	    String nmUserid, String registro, BigDecimal anno, String numero)
	    throws EMFError, ParerUserError {

	String nmEnteUnitaDocAccordo = null;
	String nmStrutUnitaDocAccordo = null;
	if (tipoEntita.equals("accordo")) {
	    OrgAccordoVigilRowBean accordoVigilRowBean = entiNonConvenzionatiEjb
		    .getOrgAccordoVigilRowBean(idEntita);
	    nmEnteUnitaDocAccordo = accordoVigilRowBean.getNmEnte();
	    nmStrutUnitaDocAccordo = accordoVigilRowBean.getNmStrut();
	}
	BigDecimal idOrganizApplic = amministrazioneUtentiHelper
		.getIdOrganizIamByParam(nmEnteUnitaDocAccordo, nmStrutUnitaDocAccordo);
	String xml = null;
	if (idOrganizApplic != null) {
	    Map<String, String> org = entiNonConvenzionatiEjb.getUsrOrganizIamMap(idOrganizApplic);
	    xml = "<Recupero><Versione>" + versione + "</Versione><Versatore><Ambiente>"
		    + org.get("AMBIENTE") + "</Ambiente><Ente>" + org.get("ENTE")
		    + "</Ente><Struttura>" + org.get("STRUTTURA") + "</Struttura>" + "<UserID>"
		    + nmUserid + "</UserID></Versatore><Chiave><Numero>" + numero
		    + "</Numero><Anno>" + anno + "</Anno><TipoRegistro>" + registro
		    + "</TipoRegistro></Chiave></Recupero>";
	}
	return xml;
    }

    @Override
    public void scaricaCompFileUdAccordoVigil() throws Throwable {
	String registro = getForm().getAccordoVigilDetail().getCd_registro_repertorio().parse();
	BigDecimal anno = getForm().getAccordoVigilDetail().getAa_repertorio().parse();
	String numero = getForm().getAccordoVigilDetail().getCd_key_repertorio().parse();
	BigDecimal idAccordoVigil = getForm().getAccordoVigilDetail().getId_accordo_vigil().parse();
	scaricaCompFileUd(idAccordoVigil, "accordo", registro, anno, numero);
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
		 * Definiamo l'output previsto che sarà un file in formato zip di cui si occuperà la
		 * servlet per fare il download
		 */
		OutputStream outUD = getServletOutputStream();
		getResponse().setContentType(
			StringUtils.isBlank(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_CONTENTTYPE.name())
				? WebConstants.MIME_TYPE_GENERIC
				: WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_CONTENTTYPE.name());
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

    @Override
    public JSONObject triggerEnteVigilatoDetailId_ambiente_ente_convenzOnTrigger() throws EMFError {
	getForm().getEnteVigilatoDetail().post(getRequest());
	BigDecimal idAmbienteEnteConvenz = getForm().getEnteVigilatoDetail()
		.getId_ambiente_ente_convenz().parse();
	DecodeMap mappa = new DecodeMap();
	if (idAmbienteEnteConvenz != null) {
	    OrgVRicEnteConvenzTableBean ricEnteConvenz = entiNonConvenzionatiEjb
		    .getOrgVRicEnteConvenzAbilAccordoValidoTableBean(
			    BigDecimal.valueOf(getUser().getIdUtente()), idAmbienteEnteConvenz);
	    mappa.populatedMap(ricEnteConvenz, "id_ente_convenz", "nm_ente_convenz");
	}
	getForm().getEnteVigilatoDetail().getId_ente_produt().setDecodeMap(mappa);
	return getForm().getEnteVigilatoDetail().asJSON();
    }

    @Override
    public JSONObject triggerEnteVigilatoDetailId_ente_produtOnTrigger() throws EMFError {
	getForm().getEnteVigilatoDetail().post(getRequest());

	DateFormat formato = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH);

	BigDecimal idEnteProdut = getForm().getEnteVigilatoDetail().getId_ente_produt().parse();
	Date dtIniValAccordo = getForm().getAccordoVigilDetail().getDt_ini_val_accordo().parse();
	Date dtFinValAccordo = getForm().getAccordoVigilDetail().getDt_fin_val_accordo().parse();
	try {
	    if (idEnteProdut != null) {
		OrgEnteSiamRowBean enteProdut = entiNonConvenzionatiEjb
			.getOrgEnteSiamRowBean(idEnteProdut);
		if (enteProdut.getDtIniVal().after(dtIniValAccordo)) {
		    getForm().getEnteVigilatoDetail().getDt_ini_val_vigil()
			    .setValue(formato.format(enteProdut.getDtIniVal()));
		} else {
		    getForm().getEnteVigilatoDetail().getDt_ini_val_vigil()
			    .setValue(formato.format(dtIniValAccordo));
		}
		if (enteProdut.getDtCessazione().before(dtFinValAccordo)) {
		    getForm().getEnteVigilatoDetail().getDt_fin_val_vigil()
			    .setValue(formato.format(enteProdut.getDtCessazione()));
		} else {
		    getForm().getEnteVigilatoDetail().getDt_fin_val_vigil()
			    .setValue(formato.format(dtFinValAccordo));
		}
	    } else {
		getForm().getEnteVigilatoDetail().getDt_ini_val_vigil()
			.setValue(formato.format(Calendar.getInstance().getTime()));
		Calendar c = Calendar.getInstance();
		c.set(2444, Calendar.DECEMBER, 31, 0, 0, 0);
		getForm().getEnteVigilatoDetail().getDt_fin_val_vigil()
			.setValue(formato.format(c.getTime()));
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
	return getForm().getEnteVigilatoDetail().asJSON();
    }

    private void saveAccordoVigil() throws EMFError {
	if (getForm().getAccordoVigilDetail().postAndValidate(getRequest(), getMessageBox())) {

	    Date dtIniVal = getForm().getAccordoVigilDetail().getDt_ini_val_accordo().parse();
	    Date dtFinVal = getForm().getAccordoVigilDetail().getDt_fin_val_accordo().parse();

	    // Controlli su accordi
	    // 1) UC_CONTR_DATE
	    String errorDateDec = DateUtil.ucContrDate("", dtIniVal, dtFinVal);
	    if (errorDateDec != null) {
		getMessageBox().addError(errorDateDec);
	    }

	    try {
		if (!getMessageBox().hasError()) {
		    // Salva l'accordo tra l'organo di vigilanza e l'ente conservatore
		    BigDecimal idEnteOrganoVigil = getForm().getEnteNonConvenzionatoDetail()
			    .getId_ente_siam().parse();
		    Date dtIniValEnteSiam = getForm().getEnteNonConvenzionatoDetail()
			    .getDt_ini_val().parse();
		    Date dtCessazioneEnteSiam = getForm().getEnteNonConvenzionatoDetail()
			    .getDt_cessazione().parse();
		    String nmEnteOrganoVigil = getForm().getEnteNonConvenzionatoDetail()
			    .getNm_ente_siam().parse();

		    BigDecimal idEnteConserv = getForm().getAccordoVigilDetail()
			    .getId_ente_conserv().parse();
		    String nmEnteConserv = getForm().getAccordoVigilDetail().getId_ente_conserv()
			    .getDecodedValue();
		    String dsNoteEnteAccordo = getForm().getAccordoVigilDetail()
			    .getDs_note_ente_accordo().parse();

		    String cdRegistroRepertorio = getForm().getAccordoVigilDetail()
			    .getCd_registro_repertorio().parse();
		    BigDecimal aaRepertorio = getForm().getAccordoVigilDetail().getAa_repertorio()
			    .parse();
		    String cdKeyRepertorio = getForm().getAccordoVigilDetail()
			    .getCd_key_repertorio().parse();
		    String dsFirmatarioEnte = getForm().getAccordoVigilDetail()
			    .getDs_firmatario_ente().parse();
		    Date dtRegAccordo = getForm().getAccordoVigilDetail().getDt_reg_accordo()
			    .parse();
		    String nmEnte = getForm().getAccordoVigilDetail().getNm_ente().parse();
		    String nmStrut = getForm().getAccordoVigilDetail().getNm_strut().parse();

		    // Controlli su accordi
		    // 3) UC_CONTR_INCLUSIONE_FIGLIO: passando come entità figlio = accordo di
		    // vigilanza e entità padre
		    // = organo di vigilanza
		    entiNonConvenzionatiEjb.checkInclusioneFiglio(dtIniValEnteSiam,
			    dtCessazioneEnteSiam, dtIniVal, dtFinVal, nmEnteOrganoVigil,
			    nmEnteConserv);

		    // 4) Si controlla che l’ente convenzionato scelto come conservatore abbia un
		    // accordo valido alla
		    // data di inizio validità dell’accordo di vigilanza
		    if (!entiConvenzionatiEjb.checkAccordoValido(idEnteConserv, dtIniVal)) {
			throw new ParerUserError(
				"L’ente conservatore non ha un accordo valido alla data di inizio validità dell’accordo di vigilanza");
		    }

		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    BigDecimal idAccordoVigil = null;
		    if (getForm().getAccordiVigilList().getStatus().equals(Status.insert)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());

			// 2) Controllo sovrapposizione periodi
			if (entiNonConvenzionatiEjb.checkEsistenzaAccordiDtIniDtFineVal(
				idEnteOrganoVigil, dtIniVal, dtFinVal, null)) {
			    throw new ParerUserError(
				    "Esiste un accordo con periodo di validità sovrapposto a quello impostato nel presente accordo");
			}

			idAccordoVigil = BigDecimal
				.valueOf(entiNonConvenzionatiEjb.insertOrgAccordoVigil(param,
					getUser().getIdUtente(), idEnteOrganoVigil, idEnteConserv,
					dtIniVal, dtFinVal, dsNoteEnteAccordo, cdRegistroRepertorio,
					aaRepertorio, cdKeyRepertorio, dsFirmatarioEnte,
					dtRegAccordo, nmEnte, nmStrut));
			if (idAccordoVigil != null) {
			    getForm().getAccordoVigilDetail().getId_accordo_vigil()
				    .setValue(idAccordoVigil.toString());
			}

			OrgAccordoVigilRowBean accordoVigilRowBean = new OrgAccordoVigilRowBean();
			getForm().getAccordoVigilDetail().copyToBean(accordoVigilRowBean);
			getForm().getAccordiVigilList().getTable().last();
			getForm().getAccordiVigilList().getTable().add(accordoVigilRowBean);
		    } else if (getForm().getAccordiVigilList().getStatus().equals(Status.update)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
			idAccordoVigil = getForm().getAccordoVigilDetail().getId_accordo_vigil()
				.parse();

			// 2) Controllo sovrapposizione periodi
			if (entiNonConvenzionatiEjb.checkEsistenzaAccordiDtIniDtFineVal(
				idEnteOrganoVigil, dtIniVal, dtFinVal, idAccordoVigil)) {
			    throw new ParerUserError(
				    "Esiste un accordo con periodo di validità sovrapposto a quello impostato nel presente accordo");
			}

			entiNonConvenzionatiEjb.updateOrgAccordoVigil(param, idAccordoVigil,
				idEnteOrganoVigil, dtIniVal, dtFinVal, dsNoteEnteAccordo,
				cdRegistroRepertorio, aaRepertorio, cdKeyRepertorio,
				dsFirmatarioEnte, dtRegAccordo);
		    }

		    loadDettaglioAccordoVigil(idAccordoVigil);
		    getMessageBox().addInfo("Accordo di vigilanza salvato con successo");
		    getMessageBox().setViewMode(ViewMode.plain);
		}
		forwardToPublisher(Application.Publisher.DETTAGLIO_ACCORDO_VIGIL);
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
		forwardToPublisher(Application.Publisher.DETTAGLIO_ACCORDO_VIGIL);
	    }
	}
    }

    @Override
    public void updateAccordiVigilList() throws EMFError {
	getForm().getAccordoVigilDetail().setEditMode();
	getForm().getAccordoVigilDetail().getId_accordo_vigil().setViewMode();
	getForm().getAccordoVigilDetail().getId_ente_conserv().setViewMode();
	getForm().getAccordiVigilList().setStatus(Status.update);
    }

    private void initEnteVigilatoDetail() throws ParerUserError, EMFError {
	getForm().getEnteVigilatoDetail().getId_ambiente_ente_convenz()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiNonConvenzionatiEjb.getUsrVAbilAmbEnteXEnteTableBean(
				new BigDecimal(getUser().getIdUtente())),
			"id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));

	getForm().getEnteVigilatoDetail().getId_ente_produt().setDecodeMap(new DecodeMap());
    }

    private void saveEnteVigilato() throws EMFError {
	if (getForm().getEnteVigilatoDetail().postAndValidate(getRequest(), getMessageBox())) {
	    // CONTROLLI DI COERENZA NELLA MASCHERA
	    BigDecimal idEnteProdut = getForm().getEnteVigilatoDetail().getId_ente_produt().parse();
	    Date dtIniValVigil = getForm().getEnteVigilatoDetail().getDt_ini_val_vigil().parse();
	    Date dtFinValVigil = getForm().getEnteVigilatoDetail().getDt_fin_val_vigil().parse();

	    if (dtFinValVigil.before(dtIniValVigil)) {
		getMessageBox().addError(
			"La data di inizio validità non può essere successiva alla data di fine validità");
	    }

	    try {
		if (!getMessageBox().hasError()) {
		    // Salva l'associazione al nuovo ente vigilato
		    BigDecimal idEnteOrganoVigil = getForm().getEnteNonConvenzionatoDetail()
			    .getId_ente_siam().parse();
		    BigDecimal idEnteSiamProdutCorrisp = getForm().getEnteNonConvenzionatoDetail()
			    .getId_ente_siam_produt_corrisp().parse();

		    Date dtIniValAccordo = getForm().getAccordoVigilDetail().getDt_ini_val_accordo()
			    .parse();
		    Date dtFinValAccordo = getForm().getAccordoVigilDetail().getDt_fin_val_accordo()
			    .parse();

		    String nmEnteProdut = entiNonConvenzionatiEjb.getNmEnteSiam(idEnteProdut);
		    BigDecimal idVigilEnteProdut = getForm().getEnteVigilatoDetail()
			    .getId_vigil_ente_produt().parse();
		    BigDecimal idAccordoVigil = getForm().getAccordoVigilDetail()
			    .getId_accordo_vigil().parse();

		    // Verifico se la coppia accordo / ente produttore vigilato sia già presente
		    // nella tabella ORG_VIGIL_ENTE_PRODUT;
		    // se già presente, si verifica che le date indicate nella presente vigilanza
		    // non siano sovrapposte
		    // a quelle delle vigilanze precedentemente indicate sullo stesso ente
		    // produttore
		    entiNonConvenzionatiEjb.checkEnteVigilatoPerAccordoVigil(idAccordoVigil,
			    idEnteProdut, nmEnteProdut, dtIniValVigil, dtFinValVigil,
			    idVigilEnteProdut);

		    // Se è stato indicato un ente produttore corrispondente, richiamo il controllo
		    // inclusione dato
		    // figlio passando come entità figlio = vigilanza e entità padre = ente
		    // produttore corrispondente.
		    if (idEnteSiamProdutCorrisp != null) {
			OrgEnteSiamRowBean enteProdutCorrisp = entiNonConvenzionatiEjb
				.getOrgEnteSiamRowBean(idEnteSiamProdutCorrisp);
			entiNonConvenzionatiEjb.checkInclusioneFiglio(
				enteProdutCorrisp.getDtIniVal(),
				enteProdutCorrisp.getDtCessazione(), dtIniValVigil, dtFinValVigil,
				enteProdutCorrisp.getNmEnteSiam(), nmEnteProdut);
		    }

		    // richiamo il controllo inclusione dato figlio (UC_CONTR_INCLUSIONE_FIGLIO)
		    // passando come entità
		    // figlio = vigilanza e entità padre = accordo di vigilanza
		    entiNonConvenzionatiEjb.checkInclusioneFiglioVigil(dtIniValAccordo,
			    dtFinValAccordo, dtIniValVigil, dtFinValVigil, "accordo di vigilanza",
			    nmEnteProdut);

		    BigDecimal idVigilanzaEnteProdut = null;
		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    if (getForm().getEntiVigilatiList().getStatus().equals(Status.insert)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
			idVigilanzaEnteProdut = BigDecimal
				.valueOf(entiNonConvenzionatiEjb.insertOrgVigilEnteProdut(param,
					getUser().getIdUtente(), idAccordoVigil, idEnteProdut,
					dtIniValVigil, dtFinValVigil));
			if (idVigilanzaEnteProdut != null) {
			    getForm().getEnteVigilatoDetail().getId_vigil_ente_produt()
				    .setValue(idVigilanzaEnteProdut.toString());
			}

			OrgVigilEnteProdutRowBean vigilEnteProdutRowBean = new OrgVigilEnteProdutRowBean();
			getForm().getEnteVigilatoDetail().copyToBean(vigilEnteProdutRowBean);
			getForm().getEntiVigilatiList().getTable().last();
			getForm().getEntiVigilatiList().getTable().add(vigilEnteProdutRowBean);
		    } else if (getForm().getEntiVigilatiList().getStatus().equals(Status.update)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
			idVigilanzaEnteProdut = getForm().getEnteVigilatoDetail()
				.getId_vigil_ente_produt().parse();
			entiNonConvenzionatiEjb.updateOrgVigilEnteProdut(param,
				idVigilanzaEnteProdut, idEnteOrganoVigil, dtIniValVigil,
				dtFinValVigil);
		    }

		    loadDettaglioEnteVigilato(idVigilanzaEnteProdut);
		    getForm().getEnteVigilatoDetail().setViewMode();
		    getForm().getEnteVigilatoDetail().setStatus(Status.view);
		    getForm().getEntiVigilatiList().setStatus(Status.view);
		    getMessageBox().addInfo("Associazione dell'ente vigilato salvata con successo");
		    getMessageBox().setViewMode(ViewMode.plain);

		}
		forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_VIGILATO);
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
		forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_VIGILATO);
	    }
	}
    }

    @Override
    public void deleteEntiVigilatiList() throws EMFError {
	OrgVigilEnteProdutRowBean currentRow = (OrgVigilEnteProdutRowBean) getForm()
		.getEntiVigilatiList().getTable().getCurrentRow();
	BigDecimal idVigilEnteProdut = currentRow.getIdVigilEnteProdut();
	// String tipoEnte =
	// getForm().getEnteNonConvenzionatoDetail().getTi_ente_non_convenz().parse();
	int riga = getForm().getEntiVigilatiList().getTable().getCurrentRowIndex();
	// Eseguo giusto un controllo per verificare che io stia prendendo la riga giusta se sono
	// nel dettaglio
	if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_ENTE_VIGILATO)) {
	    if (!idVigilEnteProdut
		    .equals(getForm().getEnteVigilatoDetail().getId_vigil_ente_produt().parse())) {
		getMessageBox()
			.addError("Eccezione imprevista nell'eliminazione dell'ente vigilato");
	    }
	}
	if (!getMessageBox().hasError() && idVigilEnteProdut != null) {
	    try {
		if (!entiNonConvenzionatiEjb.checkUserAbilEnteConvenz(
			new BigDecimal(getUser().getIdUtente()), currentRow.getIdEnteProdut())) {
		    getMessageBox().addError(
			    "Eliminazione dell'ente non consentita in quanto l'utente non è abilitato");
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
			    .equalsIgnoreCase(Application.Publisher.DETTAGLIO_ENTE_VIGILATO)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
		    } else {
			param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
				getForm().getEntiVigilatiList()));
		    }

		    // Determino gli utenti appartenenti all'ENTE CONVENZIONATO rimosso dalla
		    // vigilanza, perchè poi su
		    // di
		    // essi dovrò rimuove l'abilitazione all'ente NON convenzionato di tipo organo
		    // di vigilanza (visto
		    // che
		    // ora non mi vigila più...). L'elenco esclude gli utenti CESSATI
		    SortedSet<String> listaUtenti = entiNonConvenzionatiEjb
			    .retrieveUsrUserEnteSiamVigilato(idVigilEnteProdut);

		    if (!listaUtenti.isEmpty()) {
			getRequest().setAttribute("numeroUtentiVigilanza", listaUtenti.size());
			getRequest().setAttribute("listaUtentiVigilanza",
				listaUtenti.toString().replace("[", "").replace("]", ""));
			getRequest().setAttribute("customDeleteVigilanzaMessageBox", true);
			Object[] attributiDeleteVigilanza = new Object[4];
			attributiDeleteVigilanza[0] = param;
			attributiDeleteVigilanza[1] = idVigilEnteProdut;
			attributiDeleteVigilanza[2] = riga;
			getSession().setAttribute("attributiDeleteVigilanza",
				attributiDeleteVigilanza);
		    } else {
			eseguiDeleteVigilanza(param, idVigilEnteProdut, riga);
		    }
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(
			"L'ente vigilato non pu\u00F2 essere eliminato: " + ex.getDescription());
		forwardToPublisher(getLastPublisher());
	    }
	}

	if (Application.Publisher.DETTAGLIO_ACCORDO_VIGIL.equals(getLastPublisher())) {
	    forwardToPublisher(getLastPublisher());
	}
    }

    public void eseguiDeleteVigilanza(LogParam param, BigDecimal idVigilEnteProdut, int riga)
	    throws EMFError {
	try {
	    entiNonConvenzionatiEjb.deleteOrgVigilEnteProdut(param, idVigilEnteProdut);
	    getForm().getEntiVigilatiList().getTable().remove(riga);
	    getMessageBox().addInfo("Ente vigilato eliminato con successo");
	    getMessageBox().setViewMode(ViewMode.plain);

	} catch (ParerUserError ex) {
	    getMessageBox().addError(
		    "L'ente vigilato non pu\u00F2 essere eliminato: " + ex.getDescription());
	    forwardToPublisher(getLastPublisher());
	}

	if (!getMessageBox().hasError()
		&& getLastPublisher().equals(Application.Publisher.DETTAGLIO_ENTE_VIGILATO)) {
	    goBack();
	} else {
	    // Questa delete, come tutte, passa per la finestra modale di "Conferma" cancellazione
	    // che attraverso il
	    // framework "aggiunge" un elemento alla pila di esecuzione. Va dunque rimosso onde
	    // evitare di avere
	    // un elemento ripetuto nella pila che comporterebbe la necessità di cliccare 2 volte il
	    // tasto Indietro
	    // durante la navigazione
	    SessionManager.removeLastExecutionHistory(getSession());
	    forwardToPublisher(getLastPublisher());
	}
    }

    public void confermaDeleteVigilanza() throws EMFError {
	if (getSession().getAttribute("attributiDeleteVigilanza") != null) {
	    Object[] attributiDeleteVigilanza = (Object[]) getSession()
		    .getAttribute("attributiDeleteVigilanza");
	    LogParam param = (LogParam) attributiDeleteVigilanza[0];
	    BigDecimal idVigilEnteProdut = (BigDecimal) attributiDeleteVigilanza[1];
	    int riga = (int) attributiDeleteVigilanza[2];
	    eseguiDeleteVigilanza(param, idVigilEnteProdut, riga);
	}
    }

    public void annullaDeleteVigilanza() {
	getSession().removeAttribute("attributiDeleteVigilanza");
	if (!getMessageBox().hasError()
		&& getLastPublisher().equals(Application.Publisher.DETTAGLIO_ENTE_VIGILATO)) {
	    goBack();
	} else {
	    forwardToPublisher(getLastPublisher());
	    SessionManager.removeLastExecutionHistory(getSession());
	}
    }

    private void loadDettaglioEnteVigilato(BigDecimal idVigilEnteProdut)
	    throws ParerUserError, EMFError {

	// Carico il dettaglio ente vigilato
	OrgVigilEnteProdutRowBean detailRow = entiNonConvenzionatiEjb
		.getOrgVigilEnteProdutRowBean(idVigilEnteProdut);
	getForm().getEnteVigilatoDetail().copyFromBean(detailRow);

	// Metto tutto in view mode
	getForm().getEnteVigilatoDetail().setViewMode();
	getForm().getEnteVigilatoDetail().setStatus(Status.view);
	getForm().getEntiVigilatiList().setStatus(Status.view);
    }

    @Override
    public void updateEntiVigilatiList() throws EMFError {
	try {
	    OrgVigilEnteProdutRowBean currentRow = (OrgVigilEnteProdutRowBean) getForm()
		    .getEntiVigilatiList().getTable().getCurrentRow();
	    if (!entiNonConvenzionatiEjb.checkUserAbilEnteConvenz(
		    new BigDecimal(getUser().getIdUtente()), currentRow.getIdEnteProdut())) {
		getMessageBox().addError(
			"Modifica dell'ente non consentita in quanto l'utente non è abilitato");
		forwardToPublisher(getLastPublisher());
	    }
	    if (!getMessageBox().hasError()) {
		getForm().getEnteVigilatoDetail().setEditMode();
		getForm().getEnteVigilatoDetail().getId_ambiente_ente_convenz().setViewMode();
		getForm().getEnteVigilatoDetail().getId_ente_produt().setViewMode();

		getForm().getEntiVigilatiList().setStatus(Status.update);
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(
		    "L'ente vigilato non pu\u00F2 essere modificato: " + ex.getDescription());
	}
    }

    @Override
    public JSONObject triggerAccordoVigilDetailId_ente_conservOnTrigger() throws EMFError {
	getForm().getAccordoVigilDetail().post(getRequest());
	BigDecimal idEnteConvenz = getForm().getAccordoVigilDetail().getId_ente_conserv().parse();
	BigDecimal idAmbienteEnteConvenz = entiConvenzionatiEjb
		.getOrgAmbienteEnteConvenzByEnteConvenz(idEnteConvenz).getIdAmbienteEnteConvenz();
	try {
	    if (idEnteConvenz != null) {
		String[][] ambEnteStrut = entiConvenzionatiEjb
			.getAmbEnteStrutByIamParamApplic(idAmbienteEnteConvenz, idEnteConvenz);
		getForm().getAccordoVigilDetail().getCd_registro_repertorio()
			.setDecodeMap(DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getRegistriStrutturaTableBean(
					new BigDecimal(ambEnteStrut[2][0])),
				"cd_registro", "cd_registro"));
		// Carico i valori precompilati
		getForm().getAccordoVigilDetail().getNm_ente().setValue(ambEnteStrut[1][1]);
		getForm().getAccordoVigilDetail().getNm_strut().setValue(ambEnteStrut[2][1]);
	    } else {
		getForm().getAccordoVigilDetail().getNm_ente().setValue(null);
		getForm().getAccordoVigilDetail().getNm_strut().setValue(null);
	    }
	} catch (ParerUserError ex) {
	    actionLogger.error(
		    "Errore durante il recupero dei parametri NM_ENTE_UNITA_DOC_ACCORDO ed NM_STRUT_UNITA_DOC_ACCORDO "
			    + ExceptionUtils.getRootCauseMessage(ex),
		    ex);
	    getMessageBox().addError(
		    "Errore durante il recupero dei parametri NM_ENTE_UNITA_DOC_ACCORDO ed NM_STRUT_UNITA_DOC_ACCORDO");
	}
	return getForm().getAccordoVigilDetail().asJSON();
    }

}
