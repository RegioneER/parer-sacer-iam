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

import static it.eng.spagoCore.ConfigProperties.StandardProperty.DISCIPLINARE_TECNICO_MAX_FILE_SIZE;
import static it.eng.spagoCore.ConfigProperties.StandardProperty.MODULO_INFORMAZIONI_MAX_FILE_SIZE;
import static it.eng.spagoCore.ConfigProperties.StandardProperty.VARIAZIONE_ACCORDO_MAX_FILE_SIZE;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javax.ejb.EJB;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DateUtils;
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
import it.eng.saceriam.amministrazioneEntiConvenzionati.dto.EnteConvenzionatoBean;
import it.eng.saceriam.amministrazioneEntiConvenzionati.dto.StoEnteConvenzionatoBean;
import it.eng.saceriam.amministrazioneEntiConvenzionati.ejb.EntiConvenzionatiEjb;
import it.eng.saceriam.amministrazioneEntiConvenzionati.helper.EntiConvenzionatiHelper;
import it.eng.saceriam.common.Constants;
import it.eng.saceriam.entity.OrgAccordoEnte;
import it.eng.saceriam.entity.OrgAppartCollegEnti;
import it.eng.saceriam.entity.OrgEnteConvenzOrg;
import it.eng.saceriam.entity.OrgEnteUserRif;
import it.eng.saceriam.entity.OrgModuloInfoAccordo;
import it.eng.saceriam.entity.constraint.ConstIamParamApplic;
import it.eng.saceriam.entity.constraint.ConstOrgAmbitoTerrit;
import it.eng.saceriam.entity.constraint.ConstOrgCollegEntiConvenz.TiColleg;
import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam;
import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam.TiEnteConvenz;
import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam.TiEnteSiam;
import it.eng.saceriam.entity.constraint.ConstOrgGestioneAccordo.TipoTrasmissione;
import it.eng.saceriam.entity.constraint.ConstOrgTipoServizio;
import it.eng.saceriam.entity.constraint.ConstUsrStatoUser;
import it.eng.saceriam.exception.ParerInternalError;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.saceriam.slite.gen.Application;
import it.eng.saceriam.slite.gen.action.AmministrazioneEntiConvenzionatiAbstractAction;
import it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm;
import it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail;
import it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm.TipoServizioAccordoList;
import it.eng.saceriam.slite.gen.form.AmministrazioneEntiNonConvenzionatiForm;
import it.eng.saceriam.slite.gen.form.AmministrazioneUtentiForm;
import it.eng.saceriam.slite.gen.form.GestioneFatturazioneServiziForm;
import it.eng.saceriam.slite.gen.tablebean.IamParamApplicRowBean;
import it.eng.saceriam.slite.gen.tablebean.IamParamApplicTableBean;
import it.eng.saceriam.slite.gen.tablebean.IamParamApplicTableDescriptor;
import it.eng.saceriam.slite.gen.tablebean.OrgAaAccordoRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAaAccordoTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAccordoEnteRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAccordoEnteTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAmbienteEnteConvenzRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAmbienteEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAmbitoTerritRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAppartCollegEntiRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgCollegEntiConvenzRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgCollegEntiConvenzTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgDiscipStrutRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteArkRifRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteArkRifTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteSiamRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteSiamTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteUserRifRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteUserRifTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgGestioneAccordoRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgModuloInfoAccordoRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgServizioErogRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgServizioErogTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgStoEnteConvenzRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgTipoAccordoRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgTipoServizioRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrDichAbilEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrUserRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrUserTableBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVEnteConvenzByOrganizRowBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVEnteConvenzByOrganizTableBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicAccordoEnteRowBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicAccordoEnteTableBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicEnteConvenzRowBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVAbilAmbConvenzXenteTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVAbilAmbEnteConvenzRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisUserEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisUserRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisUserTableBean;
import it.eng.saceriam.util.DateUtil;
import it.eng.saceriam.util.SacerLogConstants;
import it.eng.saceriam.web.ejb.AmministrazioneUtentiEjb;
import it.eng.saceriam.web.ejb.SistemiVersantiEjb;
import it.eng.saceriam.web.helper.AmministrazioneUtentiHelper;
import it.eng.saceriam.web.util.ApplEnum;
import it.eng.saceriam.web.util.ComboGetter;
import it.eng.saceriam.web.util.NavigatorDetailBean;
import it.eng.saceriam.web.util.NavigatorDetailBeanManager;
import it.eng.saceriam.web.util.WebConstants;
import it.eng.saceriam.web.validator.AmministrazioneEntiConvenzionatiValidator;
import it.eng.spagoCore.ConfigSingleton;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.SessionManager;
import it.eng.spagoLite.actions.form.ListAction;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.base.table.BaseTable;
import it.eng.spagoLite.db.oracle.decode.DecodeMap;
import it.eng.spagoLite.form.base.BaseElements.Status;
import it.eng.spagoLite.form.fields.Fields;
import it.eng.spagoLite.form.fields.SingleValueField;
import it.eng.spagoLite.form.fields.impl.Input;
import it.eng.spagoLite.message.Message;
import it.eng.spagoLite.message.MessageBox.ViewMode;
import it.eng.spagoLite.security.Secure;

/**
 *
 * @author Bonora_L feat. Gilioli_P feat DiLorenzo_F
 */
@SuppressWarnings({
	"unchecked", "rawtypes" })
public class AmministrazioneEntiConvenzionatiAction
	extends AmministrazioneEntiConvenzionatiAbstractAction {

    private static final Logger logger = LoggerFactory
	    .getLogger(AmministrazioneEntiConvenzionatiAction.class);

    @EJB(mappedName = "java:app/SacerIam-ejb/EntiConvenzionatiEjb")
    private EntiConvenzionatiEjb entiConvenzionatiEjb;
    @EJB(mappedName = "java:app/SacerIam-ejb/SistemiVersantiEjb")
    private SistemiVersantiEjb sistemiVersantiEjb;
    @EJB(mappedName = "java:app/SacerIam-ejb/ParamHelper")
    private ParamHelper paramHelper;
    @EJB(mappedName = "java:app/SacerIam-ejb/EntiConvenzionatiHelper")
    private EntiConvenzionatiHelper entiConvenzionatiHelper;
    @EJB(mappedName = "java:app/SacerIam-ejb/AmministrazioneUtentiHelper")
    private AmministrazioneUtentiHelper amministrazioneUtentiHelper;
    @EJB(mappedName = "java:app/sacerlog-ejb/SacerLogEjb")
    private SacerLogEjb sacerLogEjb;
    @EJB(mappedName = "java:app/SacerIam-ejb/AmministrazioneUtentiEjb")
    private AmministrazioneUtentiEjb amministrazioneUtentiEjb;

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
		if (getTableName().equals(getForm().getListaEntiConvenzionati().getName())) {
		    // Al primo giro azzero lo stack
		    NavigatorDetailBeanManager.resetNavigatorDetailStack();
		    loadCurrentDettaglioEnteConvenzFromList(
			    getForm().getListaEntiConvenzionati().getName());
		} else if (getTableName().equals(getForm().getAccordiList().getName())
			|| getTableName().equals(getForm().getListaAccordi().getName())) {
		    if (getTableName().equals(getForm().getAccordiList().getName())) {
			initAccordoDetail();
		    } else {
			initAccordoDetailFromRicerca();
		    }
		    //
		    getForm().getAccordoDetail().getId_tipo_accordo()
			    .setDecodeMap(DecodeMap.Factory.newInstance(
				    entiConvenzionatiEjb.getOrgTipoAccordoTableBean(),
				    "id_tipo_accordo", "cd_tipo_accordo"));
		    getForm().getAccordoDetail().getId_classe_ente_convenz()
			    .setDecodeMap(DecodeMap.Factory.newInstance(
				    entiConvenzionatiEjb.getOrgClasseEnteConvenzTableBean(),
				    "id_classe_ente_convenz", "cd_classe_ente_convenz"));

		    BaseRowInterface currentRow = null;
		    if (getTableName().equals(getForm().getAccordiList().getName())) {
			currentRow = (OrgAccordoEnteRowBean) getForm().getAccordiList().getTable()
				.getCurrentRow();
		    } else {
			currentRow = (OrgVRicAccordoEnteRowBean) getForm().getListaAccordi()
				.getTable().getCurrentRow();
			OrgEnteSiamRowBean detailRow = entiConvenzionatiEjb
				.getOrgEnteConvenzRowBean(
					currentRow.getBigDecimal("id_ente_convenz"));
			getForm().getEnteConvenzionatoDetail().getId_ambiente_ente_convenz()
				.setDecodeMap(null);
			OrgAmbienteEnteConvenzRowBean orgAmbienteEnteConvenzByEnteConvenz = entiConvenzionatiEjb
				.getOrgAmbienteEnteConvenzByEnteConvenz(
					currentRow.getBigDecimal("id_ente_convenz"));
			detailRow.setString("nm_ambiente_ente_convenz",
				orgAmbienteEnteConvenzByEnteConvenz.getNmAmbienteEnteConvenz());
			getForm().getEnteConvenzionatoDetail().copyFromBean(detailRow);
		    }
		    loadDettaglioAccordo(currentRow.getBigDecimal("id_accordo_ente"),
			    currentRow.getBigDecimal("id_tipo_accordo"));
		} else if (getTableName().equals(getForm().getAnnualitaAccordoList().getName())) {
		    OrgAaAccordoRowBean currentRow = (OrgAaAccordoRowBean) getForm()
			    .getAnnualitaAccordoList().getTable().getCurrentRow();
		    loadDettaglioAnnualitaAccordo(currentRow.getIdAaAccordo());
		} else if (getTableName().equals(getForm().getServiziErogatiList().getName())
			|| getTableName().equals(getForm().getServizioErogatoDetail().getName())) {
		    initServizioErogatoDetail();
		    OrgServizioErogRowBean currentRow = (OrgServizioErogRowBean) getForm()
			    .getServiziErogatiList().getTable().getCurrentRow();
		    loadDettaglioServizioErogato(currentRow.getIdServizioErogato());
		} else if (getTableName().equals(getForm().getAnagraficheList().getName())) {
		    initAnagraficaDetail();
		    OrgStoEnteConvenzRowBean currentRow = (OrgStoEnteConvenzRowBean) getForm()
			    .getAnagraficheList().getTable().getCurrentRow();
		    loadDettaglioAnagrafica(currentRow.getIdStoEnteConvenz());
		} else if (getTableName().equals(getForm().getGestioniAccordoList().getName())) {
		    if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)
			    || getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW)
			    || getNavigationEvent().equals(ListAction.NE_NEXT)
			    || getNavigationEvent().equals(ListAction.NE_PREV)) {
			getForm().getGestioneAccordoDetail().reset();
			initGestioneAccordoDetail();
			OrgGestioneAccordoRowBean currentRow = (OrgGestioneAccordoRowBean) getForm()
				.getGestioniAccordoList().getTable().getCurrentRow();
			loadDettaglioGestioneAccordo(
				new BigDecimal(currentRow.getIdGestAccordo().longValueExact()));
		    }
		} else if (getTableName().equals(getForm().getModuliInformazioniList().getName())) {
		    if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)
			    || getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW)
			    || getNavigationEvent().equals(ListAction.NE_NEXT)
			    || getNavigationEvent().equals(ListAction.NE_PREV)) {
			getForm().getModuloInformazioniDetail().reset();
			initModuloInformazioniDetail();
			OrgModuloInfoAccordoRowBean currentRow = (OrgModuloInfoAccordoRowBean) getForm()
				.getModuliInformazioniList().getTable().getCurrentRow();
			loadDettaglioModuloInformazioni(new BigDecimal(
				currentRow.getIdModuloInfoAccordo().longValueExact()));
			getForm().getModuloInformazioniDetail().setViewMode();
			getForm().getModuloInformazioniDetail().setStatus(Status.view);
			getForm().getModuliInformazioniList().setViewMode();
			getForm().getModuliInformazioniList().setStatus(Status.view);
		    }
		} else if (getTableName()
			.equals(getForm().getListaAmbientiEntiConvenzionati().getName())) {
		    BaseRowInterface currentRow = getForm().getListaAmbientiEntiConvenzionati()
			    .getTable().getCurrentRow();
		    BigDecimal idAmbienteEnteConvenz = currentRow
			    .getBigDecimal("id_ambiente_ente_convenz");
		    BigDecimal idEnteGestore = currentRow.getBigDecimal("id_ente_gestore");
		    initAmbienteEnteConvenzionatoDetail(idEnteGestore);
		    loadDettaglioAmbienteEnteConvenzionato(idAmbienteEnteConvenz);
		} else if (getTableName().equals(getForm().getUtentiArchivistiList().getName())
			&& getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)) {
		    BaseRowInterface currentRow = getForm().getUtentiArchivistiList().getTable()
			    .getCurrentRow();
		    BigDecimal idEnteArkRif = currentRow.getBigDecimal("id_ente_ark_rif");
		    OrgEnteArkRifRowBean enteArkRifRowBean = entiConvenzionatiEjb
			    .getOrgEnteArkRifRowBean(idEnteArkRif);
		    getForm().getUtenteArchivistaDetail().copyFromBean(enteArkRifRowBean);
		} else if (getTableName().equals(getForm().getReferentiEnteList().getName())
			&& getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)) {
		    BaseRowInterface currentRow = getForm().getReferentiEnteList().getTable()
			    .getCurrentRow();
		    BigDecimal idEnteUserRif = currentRow.getBigDecimal("id_ente_user_rif");
		    OrgEnteUserRifRowBean enteUserRifRowBean = entiConvenzionatiEjb
			    .getOrgEnteUserRifRowBean(idEnteUserRif);
		    getForm().getReferenteEnteDetail().reset();
		    getForm().getReferenteEnteDetail().getQualifica_user()
			    .setDecodeMap(ComboGetter.getMappaQualificaUser());
		    loadDettaglioReferente(getForm().getEnteConvenzionatoDetail().getTi_ente());
		    getForm().getReferenteEnteDetail().copyFromBean(enteUserRifRowBean);
		} else if (getTableName()
			.equals(getForm().getCollegamentiEnteAppartList().getName())) {
		    OrgAppartCollegEntiRowBean currentRow = (OrgAppartCollegEntiRowBean) getForm()
			    .getCollegamentiEnteAppartList().getTable().getCurrentRow();
		    BigDecimal idAppartCollegEnti = currentRow.getIdAppartCollegEnti();
		    // Init combo
		    getForm().getCollegamentoEnteAppartDetail().getId_colleg_enti_convenz()
			    .setDecodeMap(DecodeMap.Factory.newInstance(
				    entiConvenzionatiEjb.getOrgCollegEntiConvenzValidTableBean(
					    BigDecimal.valueOf(getUser().getIdUtente())),
				    "id_colleg_enti_convenz", "nm_colleg"));
		    loadDettaglioAppartenenzaColleg(idAppartCollegEnti);
		} else if (getTableName().equals(getForm().getCollegamentiEnteList().getName())) {
		    OrgCollegEntiConvenzRowBean currentRow = (OrgCollegEntiConvenzRowBean) getForm()
			    .getCollegamentiEnteList().getTable().getCurrentRow();
		    BigDecimal idCollegEntiConvenz = currentRow.getIdCollegEntiConvenz();
		    initCollegamentoDetail(idCollegEntiConvenz);
		    loadDettaglioCollegamento(idCollegEntiConvenz);
		} else if (getTableName()
			.equals(getForm().getDisciplinariTecniciList().getName())) {
		    initDisciplinareTecnicoDetail();
		    OrgDiscipStrutRowBean currentRow = (OrgDiscipStrutRowBean) getForm()
			    .getDisciplinariTecniciList().getTable().getCurrentRow();
		    loadDettaglioDisciplinareTecnico(currentRow.getIdDiscipStrut());
		} // Se ho cliccato su un elemento della lista fatture riemesse o sulla lista "di
		  // appoggio" delle
		  // fatture riemesse
		else if (getTableName().equals(getForm().getEntiCessatiList().getName())
			|| getTableName().equals(getForm().getEntiCessatiDetailList().getName())) {

		    getForm().getEntiCessatiDetailList().setStatus(Status.view);
		    getForm().getEntiCessatiList().setStatus(Status.view);

		    // Setto l'informazione in sessione che sto trattando la lista degli enti
		    // cessati
		    getSession().setAttribute("navTableEntiConvenz",
			    getForm().getEntiCessatiList().getName());
		    BaseRow row;

		    /*
		     * Devo recuperare il record da trattare: cerco di capire se prenderlo dalla
		     * lista originale degli enti cessati o da quella di appoggio
		     */
		    // Se viene dalla lista originale degli enti cessati, copio la lista in quella
		    // di appoggio
		    if (getTableName().equals(getForm().getEntiCessatiList().getName())) {
			// Se la tabella è EntiCessatiList, copio nella tabella d'appoggio i suoi
			// record
			getForm().getEntiCessatiDetailList()
				.setTable(getForm().getEntiCessatiList().getTable());
			// Recupero la riga corrente
			row = (BaseRow) getForm().getEntiCessatiList().getTable().getCurrentRow();
		    } // Se già sto ricavando dalla lista di appoggio, recupero la riga corrente
		    else {
			// Recupero la riga corrente
			row = (BaseRow) getForm().getEntiCessatiDetailList().getTable()
				.getCurrentRow();
		    }

		    // Recupero il livello di annidamento e carico il dettaglio ente convenzionato
		    BigDecimal level = getForm().getEnteConvenzionatoDetail()
			    .getLevel_enti_convenz().parse();
		    loadDettaglioEnteConvenzionato(row.getBigDecimal("id_ente_convenz"));

		    // Se sto scorrendo orizzontalmente da un record all'altro
		    if (ListAction.NE_NEXT.equals(getNavigationEvent())
			    || ListAction.NE_PREV.equals(getNavigationEvent())) {
			// Se l'ultimo record di navigazione è presente e il livello di annidamento
			// è lo stesso
			if (NavigatorDetailBeanManager.getLastNavigatorDetailStack() != null
				&& level.intValue() == NavigatorDetailBeanManager
					.getLastNavigatorDetailStack().getLevel()) {
			    // Estraggo il record di navigazione
			    NavigatorDetailBeanManager.popNavigatorDetailStack();
			    // Rimango allo stesso livello perchè sto scorrendo orizzontalmente
			    getForm().getEnteConvenzionatoDetail().getLevel_enti_convenz()
				    .setValue(String.valueOf(level));
			} // Altrimenti significa che devo aggiungere un livello di navigazione
			else {
			    int nextLevel = NavigatorDetailBeanManager.getLastNavigatorDetailStack()
				    .getLevel() + 1;
			    getForm().getEnteConvenzionatoDetail().getLevel_enti_convenz()
				    .setValue(String.valueOf(nextLevel));
			}
		    } // Se invece sto andando in un dettaglio di un ente, sto andando ad un
		      // successivo livello di
		      // profondità
		    else {
			int nextLevel = NavigatorDetailBeanManager.getLastNavigatorDetailStack()
				.getLevel() + 1;
			getForm().getEnteConvenzionatoDetail().getLevel_enti_convenz()
				.setValue(String.valueOf(nextLevel));
		    }

		    // Ora che ho "gestito" pocanzi i livelli di annidamento, mi concentro sul
		    // caricamento del dettaglio
		    // vero e proprio
		    if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW)
			    || getNavigationEvent().equals(ListAction.NE_NEXT)
			    || getNavigationEvent().equals(ListAction.NE_PREV)
			    || (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)
				    // Controllo anche che l'id del nuovo ente di cui caricare il
				    // dettaglio sia diverso
				    // da quello salvato nel livello precedente
				    && NavigatorDetailBeanManager.getLastNavigatorDetailStack()
					    .getIdObject().compareTo(
						    row.getBigDecimal("id_ente_convenz")) != 0)) {
			// Mi salvo questo parametro in request che mi servirà nel caso di update
			// non da dettaglio
			// ma cliccando sulla lista fatture riemesse per consentire quindi di
			// inserire una nuova pagina
			// di dettaglio nello stack di navigazione personalizzato
			getRequest().setAttribute("newEnteConvenzPage", true);
			// Inserisco nello stack di navigazione i dati del nuovo ente convenz
			// cessato che sto per
			// visualizzare:
			// 1) id ente convenz (cessato)
			// 2) nome lista enti cessati
			// 3) Tabella enti cessati di "appoggio"
			// 4) Livello di annidamento
			NavigatorDetailBeanManager.pushNavigatorDetailStack(
				row.getBigDecimal("id_ente_convenz"),
				getForm().getEntiCessatiList().getName(),
				getForm().getEntiCessatiDetailList().getTable(),
				getForm().getEnteConvenzionatoDetail().getLevel_enti_convenz()
					.parse().intValue());
		    }
		}
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
    }

    public void loadDettaglioReferente(Input<String> tiEnte) throws EMFError, ParerUserError {
	// Se arrivo dagli enti convenzionati, carico la combo del registro
	if (tiEnte != null
		&& tiEnte.parse().equals(ConstOrgEnteSiam.TiEnteSiam.CONVENZIONATO.name())) {
	    loadDettaglioRifReferenteEnteConvenz();
	    getForm().getEnteConvenzionatoSection().setLegend("Ente convenzionato");
	    getForm().getReferenteEnteSection().setLegend("Referente dell'ente convenzionato");
	    getSession().removeAttribute("nascondiCodiceEnte");
	    getForm().getRiferimentiSection().setHidden(false);
	} else {
	    // Se arrivo dagli enti NON convenzionati
	    getForm().getEnteConvenzionatoSection().setLegend("Ente non convenzionato");
	    getForm().getReferenteEnteSection().setLegend("Referente dell'ente non convenzionato");
	    getSession().setAttribute("nascondiCodiceEnte", true);
	    getForm().getRiferimentiSection().setHidden(true);
	}

    }

    @Override
    public void undoDettaglio() throws EMFError {
	try {
	    if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO)
		    && (getForm().getListaEntiConvenzionati().getStatus().equals(Status.update))) {
		BaseRowInterface currentRow = getForm().getListaEntiConvenzionati().getTable()
			.getCurrentRow();
		BigDecimal idEnteConvenz = currentRow.getBigDecimal("id_ente_convenz");
		if (idEnteConvenz != null) {
		    loadDettaglioEnteConvenzionato(idEnteConvenz);
		}
		// Rimuovo dalla sessione l'attributo riferito al cambio ambiente
		getSession().removeAttribute("cambioAmbiente");
		forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO);
	    } else if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO)
		    && (getForm().getEntiCessatiDetailList().getStatus().equals(Status.update))) {
		BaseRowInterface currentRow = getForm().getEntiCessatiDetailList().getTable()
			.getCurrentRow();
		BigDecimal idEnteConvenz = currentRow.getBigDecimal("id_ente_convenz");
		if (idEnteConvenz != null) {
		    loadDettaglioEnteConvenzionato(idEnteConvenz);
		}
		forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO);
	    } else if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_ACCORDO)
		    && getForm().getAccordiList().getStatus().equals(Status.update)) {
		OrgAccordoEnteRowBean currentRow = (OrgAccordoEnteRowBean) getForm()
			.getAccordiList().getTable().getCurrentRow();
		BigDecimal idAccordo = currentRow.getIdAccordoEnte();
		BigDecimal idTipoAccordo = currentRow.getIdTipoAccordo();
		if (idAccordo != null) {
		    loadDettaglioAccordo(idAccordo, idTipoAccordo);
		}
		forwardToPublisher(Application.Publisher.DETTAGLIO_ACCORDO);
	    } else if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_SERVIZIO_EROGATO)
		    && getForm().getServiziErogatiList().getStatus().equals(Status.update)) {
		OrgServizioErogRowBean currentRow = (OrgServizioErogRowBean) getForm()
			.getServiziErogatiList().getTable().getCurrentRow();
		BigDecimal idServizioErogato = currentRow.getIdServizioErogato();
		if (idServizioErogato != null) {
		    loadDettaglioServizioErogato(idServizioErogato);
		}
		forwardToPublisher(Application.Publisher.DETTAGLIO_SERVIZIO_EROGATO);
	    } else if (getLastPublisher()
		    .equals(Application.Publisher.DETTAGLIO_DISCIPLINARE_TECNICO)
		    && getForm().getDisciplinariTecniciList().getStatus().equals(Status.update)) {
		OrgDiscipStrutRowBean currentRow = (OrgDiscipStrutRowBean) getForm()
			.getDisciplinariTecniciList().getTable().getCurrentRow();
		BigDecimal idDiscipStrut = currentRow.getIdDiscipStrut();
		if (idDiscipStrut != null) {
		    loadDettaglioDisciplinareTecnico(idDiscipStrut);
		}
		forwardToPublisher(Application.Publisher.DETTAGLIO_DISCIPLINARE_TECNICO);
	    } else if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_COLLEGAMENTO_ENTE)
		    && getForm().getCollegamentiEnteList().getStatus().equals(Status.update)) {
		OrgCollegEntiConvenzRowBean currentRow = null;
		currentRow = (OrgCollegEntiConvenzRowBean) getForm().getCollegamentiEnteList()
			.getTable().getCurrentRow();
		BigDecimal idCollegEntiConvenz = currentRow.getIdCollegEntiConvenz();
		if (idCollegEntiConvenz != null) {
		    loadDettaglioCollegamento(idCollegEntiConvenz);
		}
		forwardToPublisher(Application.Publisher.DETTAGLIO_COLLEGAMENTO_ENTE);
	    } else if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_VARIAZIONE_ACCORDO)
		    && getForm().getGestioniAccordoList().getStatus().equals(Status.update)) {
		OrgGestioneAccordoRowBean currentRow = null;
		BigDecimal idGestAccordo = null;
		if (!getIdGestAccStack().isEmpty()) {
		    idGestAccordo = getIdGestAccStack().get(getIdGestAccStack().size() - 1);
		} else {
		    currentRow = (OrgGestioneAccordoRowBean) getForm().getGestioniAccordoList()
			    .getTable().getCurrentRow();
		    idGestAccordo = currentRow.getIdGestAccordo();
		}
		if (idGestAccordo != null) {
		    loadDettaglioGestioneAccordo(idGestAccordo);
		}
		forwardToPublisher(Application.Publisher.DETTAGLIO_VARIAZIONE_ACCORDO);
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
	    if (getTableName().equals(getForm().getListaEntiConvenzionati().getName())) {
		inserisciEnteConvenzionatoWizard();
	    } else if (getTableName().equals(getForm().getAccordiList().getName())) {
		getForm().getAccordoDetail().clear();
		initAccordoDetail();

		getForm().getAccordoDetail().getId_classe_ente_convenz()
			.setDecodeMap(DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getOrgClasseEnteConvenzTableBean(),
				"id_classe_ente_convenz", "cd_classe_ente_convenz"));

		BigDecimal idAmbienteEnteConvenz = getForm().getEnteConvenzionatoDetail()
			.getId_ambiente_ente_convenz().parse();
		//
		OrgAmbienteEnteConvenzRowBean orgAmbienteEnteConvenzByEnteConvenz = entiConvenzionatiEjb
			.getOrgAmbienteEnteConvenzRowBean(idAmbienteEnteConvenz);
		getForm().getEnteConvenzionatoDetail().getNm_ambiente_ente_convenz()
			.setValue(orgAmbienteEnteConvenzByEnteConvenz.getNmAmbienteEnteConvenz());
		getForm().getEnteConvenzionatoDetail().getNm_ambiente_ente_convenz().setViewMode();
		//
		OrgEnteSiamRowBean enteConvenzGestore = entiConvenzionatiEjb
			.getOrgEnteConvenzGestore(idAmbienteEnteConvenz);
		if (enteConvenzGestore != null) {
		    getForm().getAccordoDetail().getId_ente_convenz_gestore()
			    .setValue(enteConvenzGestore.getIdEnteSiam().toPlainString());
		    getForm().getAccordoDetail().getNm_ente_convenz_gestore()
			    .setValue(enteConvenzGestore.getNmEnteSiam());
		}
		//
		OrgEnteSiamRowBean enteConvenzConserv = entiConvenzionatiEjb
			.getOrgEnteConvenzConserv(idAmbienteEnteConvenz);
		if (enteConvenzConserv != null) {
		    getForm().getAccordoDetail().getId_ente_convenz_conserv()
			    .setValue(enteConvenzConserv.getIdEnteSiam().toPlainString());
		    getForm().getAccordoDetail().getNm_ente_convenz_conserv()
			    .setValue(enteConvenzConserv.getNmEnteSiam());

		    OrgAccordoEnteTableBean accordoEnteTableBean = entiConvenzionatiEjb
			    .getOrgAccordoEntePiuRecenteTableBean(
				    enteConvenzConserv.getIdEnteSiam());
		    if (!accordoEnteTableBean.isEmpty()) {
			getForm().getAccordoDetail().getId_ente_convenz_amministratore()
				.setValue(accordoEnteTableBean.getRow(0)
					.getIdEnteConvenzAmministratore().toPlainString());
			getForm().getAccordoDetail().getNm_ente_convenz_amministratore()
				.setValue(accordoEnteTableBean.getRow(0)
					.getString("nm_ente_convenz_amministratore"));
		    }
		}
		// Riporto i dati presi da ente convenzionato
		getForm().getAccordoDetail().getNm_ente_siam()
			.setValue(getForm().getEnteConvenzionatoDetail().getNm_ente_siam().parse());
		getForm().getAccordoDetail().getTi_scopo_accordo().setValue("CONSERVAZIONE");

		getForm().getAccordoDetail().setEditMode();
		// Campo id accordo nascosto in inserimento
		getForm().getAccordoDetail().getId_accordo_ente().setHidden(true);
		// Bottone di ricalcolo
		getForm().getAccordoDetail().getRicalcoloServiziErogati().setHidden(true);
		// Bottone di chiusura accordo
		getForm().getAccordoDetail().getChiusuraAccordo().setHidden(true);

		// Ambiente/Ente/Struttura in sola visualizzazione
		getForm().getAccordoDetail().getNm_ente_siam().setViewMode();
		getForm().getAccordoDetail().getNm_ente_convenz_gestore().setViewMode();
		getForm().getAccordoDetail().getNm_ente_convenz_conserv().setViewMode();
		getForm().getAccordoDetail().getNm_ente_convenz_amministratore().setViewMode();

		getForm().getAccordoDetail().getNm_ente().setViewMode();
		getForm().getAccordoDetail().getNm_strut().setViewMode();

		getForm().getAccordoDetail().getId_ente_convenz_gestore().setHidden(true);
		getForm().getAccordoDetail().getId_ente_convenz_conserv().setHidden(true);
		getForm().getAccordoDetail().getId_ente_convenz_amministratore().setHidden(true);

		getForm().getAnnualitaAccordoSection().setHidden(true);
		getForm().getTipoServizioAccordoList().setStatus(Status.insert);
		getForm().getAccordoDetail().setStatus(Status.insert);
		getForm().getAccordiList().setStatus(Status.insert);
		forwardToPublisher(Application.Publisher.DETTAGLIO_ACCORDO);
	    } else if (getTableName().equals(getForm().getServiziErogatiList().getName())
		    || getTableName().equals(getForm().getServizioErogatoDetail().getName())) {
		getForm().getServizioErogatoDetail().clear();

		initServizioErogatoDetail();

		BigDecimal idClasseEnteConvenz = getForm().getAccordoDetail()
			.getId_classe_ente_convenz().parse();
		BigDecimal idTariffario = getForm().getAccordoDetail().getId_tariffario().parse();
		// Recupero la lista dei tipi servizio da escludere dalla combo
		OrgServizioErogTableBean servizioErogTableBean = (OrgServizioErogTableBean) getForm()
			.getServiziErogatiList().getTable();

		List<BigDecimal> idTipoServizioDaEscludere = new ArrayList<>();
		for (OrgServizioErogRowBean servizioErogRowBean : servizioErogTableBean) {
		    final String tipoClasse = servizioErogRowBean
			    .getString("ti_classe_tipo_servizio");
		    if (tipoClasse.equals(ConstOrgTipoServizio.TiClasseTipoServizio.ALTRO.name())
			    || tipoClasse
				    .equals(ConstOrgTipoServizio.TiClasseTipoServizio.CONSERVAZIONE
					    .name())) {
			idTipoServizioDaEscludere.add(servizioErogRowBean.getIdTipoServizio());
		    }
		}
		// Tipo servizio
		getForm().getServizioErogatoDetail().getId_tipo_servizio()
			.setDecodeMap(DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getOrgTipoServizioTableBean(
					idClasseEnteConvenz, idTariffario,
					idTipoServizioDaEscludere),
				"id_tipo_servizio", "cd_tipo_servizio"));

		// Preimposto il valore del flag "A pagamento" per l'inserimento
		getForm().getServizioErogatoDetail().getFl_pagamento()
			.setValue(getForm().getAccordoDetail().getFl_pagamento().parse());

		getForm().getServizioErogatoDetail().setEditMode();

		getForm().getServizioErogatoDetail().setStatus(Status.insert);
		getForm().getServiziErogatiList().setStatus(Status.insert);
		forwardToPublisher(Application.Publisher.DETTAGLIO_SERVIZIO_EROGATO);
	    } else if (getTableName().equals(getForm().getAnnualitaAccordoList().getName())
		    || getTableName().equals(getForm().getAnnualitaAccordoDetail().getName())) {
		getForm().getAnnualitaAccordoDetail().clear();

		BigDecimal idTariffario = getForm().getAccordoDetail().getId_tariffario().parse();

		getForm().getAnnualitaAccordoDetail().setEditMode();
		// Popolamento lista dei tipi servizio
		getForm().getTipoServizioAnnualitaAccordoList().setTable(entiConvenzionatiEjb
			.getTipiServizioAssociatiTariffarioPerAnnualitaDaCreare(idTariffario));
		getForm().getTipoServizioAnnualitaAccordoList().getTable().setPageSize(10);
		getForm().getTipoServizioAnnualitaAccordoList().getTable().first();
		getForm().getTipoServizioAnnualitaAccordoList().getIm_tariffa_aa_accordo()
			.setEditMode();

		getForm().getAnnualitaAccordoDetail().setStatus(Status.insert);
		getForm().getAnnualitaAccordoList().setStatus(Status.insert);
		forwardToPublisher(Application.Publisher.DETTAGLIO_ANNUALITA);
	    } else if (getTableName().equals(getForm().getUtentiArchivistiList().getName())) {
		// Puliamo il dettaglio e mettiamo tutto in status "insert"
		getForm().getUtenteArchivistaDetail().clear();
		getForm().getUtentiArchivistiList().setStatus(Status.insert);
		getForm().getUtenteArchivistaDetail().setStatus(Status.insert);
		getForm().getUtenteArchivistaDetail().setEditMode();
		forwardToPublisher(Application.Publisher.DETTAGLIO_ARCHIVISTA_RIFERIMENTO);
	    } else if (getTableName().equals(getForm().getAnagraficheList().getName())) {
		// Carico qui i dati e non nel pass1onEnter perchè se li caricassi là, nel caso in
		// cui tornassi dal
		// passo2, scazzerei tutto...
		initAnagraficaDetail();
		OrgEnteSiamRowBean enteConvenzRowBean = new OrgEnteSiamRowBean();
		getForm().getEnteConvenzionatoDetail().copyToBean(enteConvenzRowBean);
		getForm().getEnteConvenzionatoDetail().getId_ambiente_ente_convenz_field()
			.setValue(enteConvenzRowBean.getIdAmbienteEnteConvenz().toPlainString());
		// Carico i dati presi da dettaglio ente
		getForm().getAnagraficaDetail().copyFromBean(enteConvenzRowBean);
		// Tutto in viewMode a parte data di fine validità e note
		getForm().getAnagraficaDetail().setViewMode();
		//
		getForm().getAnagraficaDetail().getDt_ini_val().setEditMode();
		getForm().getAnagraficaDetail().getDt_fine_val().setEditMode();
		DateFormat formato = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH);
		if (getForm().getAnagraficheList().getTable().size() > 0) {
		    Date[] dateExtreme = entiConvenzionatiEjb
			    .getExtremeDatesAnagrafiche(enteConvenzRowBean.getIdEnteSiam());
		    Calendar c = Calendar.getInstance();
		    c.setTime(dateExtreme[1]);
		    c.add(Calendar.DATE, 1);
		    getForm().getAnagraficaDetail().getDt_ini_val()
			    .setValue(formato.format(c.getTime()));
		}
		getForm().getAnagraficaDetail().getDt_fine_val().setValue("");
		getForm().getAnagraficaDetail().getDl_note().setEditMode();
		/* Resetta il wizard per la storicizazione */
		getForm().getInserimentoWizard().reset();
		forwardToPublisher(Application.Publisher.DETTAGLIO_ANAGRAFICA_WIZARD);
	    } else if (getTableName().equals(getForm().getGestioniAccordoList().getName())) {
		getForm().getGestioneAccordoDetail().clear();
		initGestioneAccordoDetail();
		getForm().getGestioneAccordoDetail().setEditMode();
		getForm().getGestioneAccordoDetail().setStatus(Status.insert);
		getForm().getGestioniAccordoList().setStatus(Status.insert);
		forwardToPublisher(Application.Publisher.DETTAGLIO_VARIAZIONE_ACCORDO);
	    } else if (getTableName().equals(getForm().getModuliInformazioniList().getName())) {
		getForm().getModuloInformazioniDetail().clear();
		initModuloInformazioniDetail();
		getForm().getModuloInformazioniDetail().setEditMode();
		getForm().getModuloInformazioniDetail().setStatus(Status.insert);
		getForm().getModuliInformazioniList().setStatus(Status.insert);
		forwardToPublisher(Application.Publisher.DETTAGLIO_MODULO_INFORMAZIONI);
	    } else if (getTableName()
		    .equals(getForm().getListaAmbientiEntiConvenzionati().getName())) {
		getForm().getAmbienteEnteConvenzionatoDetail().clear();
		// Init campi
		DateFormat formato = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH);
		getForm().getAmbienteEnteConvenzionatoDetail().getDt_ini_val()
			.setValue(formato.format(Calendar.getInstance().getTime()));
		Calendar c = Calendar.getInstance();
		c.set(2444, Calendar.DECEMBER, 31, 0, 0, 0);
		getForm().getAmbienteEnteConvenzionatoDetail().getDt_fine_val()
			.setValue(formato.format(c.getTime()));
		// Init combo
		// MEV#19407
		getForm().getAmbienteEnteConvenzionatoDetail().getId_ente_gestore()
			.setDecodeMap(DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getEntiGestoreAbilitatiTableBean(
					new BigDecimal(getUser().getIdUtente())),
				"id_ente_gestore", "nm_ente_gestore"));
		// end MEV#19407
		// Parametri
		Object[] parametriObj = entiConvenzionatiEjb.getIamParamApplicAmbiente(null);
		getForm().getParametriAmministrazioneSection().setLoadOpened(true);
		getForm().getParametriConservazioneSection().setLoadOpened(true);
		getForm().getParametriGestioneSection().setLoadOpened(true);
		getForm().getParametriAmministrazioneAmbienteList()
			.setTable((IamParamApplicTableBean) parametriObj[0]);
		getForm().getParametriAmministrazioneAmbienteList().getTable().setPageSize(300);
		getForm().getParametriAmministrazioneAmbienteList().getTable().first();
		getForm().getParametriGestioneAmbienteList()
			.setTable((IamParamApplicTableBean) parametriObj[1]);
		getForm().getParametriGestioneAmbienteList().getTable().setPageSize(300);
		getForm().getParametriGestioneAmbienteList().getTable().first();
		getForm().getParametriConservazioneAmbienteList()
			.setTable((IamParamApplicTableBean) parametriObj[2]);
		getForm().getParametriConservazioneAmbienteList().getTable().setPageSize(300);
		getForm().getParametriConservazioneAmbienteList().getTable().first();
		getForm().getParametriAmministrazioneAmbienteList().setHideDeleteButton(true);
		getForm().getParametriAmministrazioneAmbienteList()
			.getDs_valore_param_applic_ambiente_amm().setEditMode();
		getForm().getParametriGestioneAmbienteList().setHideDeleteButton(true);
		getForm().getParametriGestioneAmbienteList()
			.getDs_valore_param_applic_ambiente_gest().setEditMode();
		getForm().getParametriConservazioneAmbienteList().setHideDeleteButton(true);
		getForm().getParametriConservazioneAmbienteList()
			.getDs_valore_param_applic_ambiente_cons().setEditMode();

		getForm().getAmbienteEnteConvenzionatoDetail().setEditMode();
		getForm().getAmbienteEnteConvenzionatoDetail().setStatus(Status.insert);
		getForm().getListaAmbientiEntiConvenzionati().setStatus(Status.insert);
		getForm().getAmbienteEnteConvenzionatoDetail().getLogEventiAmbiente()
			.setHidden(true);
		forwardToPublisher(Application.Publisher.DETTAGLIO_AMBIENTE);
	    } else if (getTableName().equals(getForm().getReferentiEnteList().getName())) {
		// Puliamo il dettaglio e mettiamo tutto in status "insert"
		getForm().getReferenteEnteDetail().clear();
		getForm().getReferentiEnteList().setStatus(Status.insert);
		getForm().getReferenteEnteDetail().setStatus(Status.insert);
		getForm().getReferenteEnteDetail().setEditMode();
		getForm().getReferenteEnteDetail().getQualifica_user()
			.setDecodeMap(ComboGetter.getMappaQualificaUser());
		// Se arrivo dagli enti convenzionati, mostro la sezione relativa a
		// registro/anno/numero...
		loadDettaglioReferente(getForm().getEnteConvenzionatoDetail().getTi_ente());
		forwardToPublisher(Application.Publisher.DETTAGLIO_REFERENTE_ENTE);
	    } else if (getTableName().equals(getForm().getDisciplinariTecniciList().getName())) {
		getForm().getDisciplinareTecnicoDetail().clear();
		initDisciplinareTecnicoDetail();
		getForm().getDisciplinareTecnicoDetail().setEditMode();
		getForm().getDisciplinareTecnicoDetail().setStatus(Status.insert);
		getForm().getDisciplinariTecniciList().setStatus(Status.insert);
		getForm().getDisciplinareTecnicoDetail().getFl_inserito_manualmente().setValue("1");
		forwardToPublisher(Application.Publisher.DETTAGLIO_DISCIPLINARE_TECNICO);
	    } else if (getTableName().equals(getForm().getCollegamentiEnteList().getName())) {
		// Puliamo il dettaglio e mettiamo tutto in status "insert"
		getForm().getCollegamentoEnteDetail().clear();
		// Init combo
		initCollegamentoDetail();
		// Init date
		DateFormat formato = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH);
		getForm().getCollegamentoEnteDetail().getDt_ini_val()
			.setValue(formato.format(Calendar.getInstance().getTime()));
		Calendar c = Calendar.getInstance();
		c.set(2444, Calendar.DECEMBER, 31, 0, 0, 0);
		getForm().getCollegamentoEnteDetail().getDt_fin_val()
			.setValue(formato.format(c.getTime()));

		getForm().getCollegamentiEnteList().setStatus(Status.insert);
		getForm().getCollegamentoEnteDetail().setStatus(Status.insert);
		getForm().getCollegamentoEnteDetail().setEditMode();
		forwardToPublisher(Application.Publisher.DETTAGLIO_COLLEGAMENTO_ENTE);
	    } else if (getTableName().equals(getForm().getCollegamentiEnteAppartList().getName())) {
		// Puliamo il dettaglio e mettiamo tutto in status "insert"
		getForm().getCollegamentoEnteAppartDetail().clear();
		// Init combo
		getForm().getCollegamentoEnteAppartDetail().getId_colleg_enti_convenz()
			.setDecodeMap(DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getOrgCollegEntiConvenzValidTableBean(
					BigDecimal.valueOf(getUser().getIdUtente())),
				"id_colleg_enti_convenz", "nm_colleg"));

		getForm().getCollegamentiEnteAppartList().setStatus(Status.insert);
		getForm().getCollegamentoEnteAppartDetail().setStatus(Status.insert);
		getForm().getCollegamentoEnteAppartDetail().setEditMode();
		forwardToPublisher(Application.Publisher.DETTAGLIO_APPARTENENZA_ENTE);
	    } else if (getTableName().equals(getForm().getEntiCollegatiList().getName())) {
		// Puliamo il dettaglio e mettiamo tutto in status "insert"
		getForm().getEnteCollegatoDetail().clear();
		// Init combo
		getForm().getEnteCollegatoDetail().getId_ambiente_ente_convenz()
			.setDecodeMap(DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getUsrVAbilAmbEnteConvenzTableBean(
					new BigDecimal(getUser().getIdUtente())),
				"id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));
		getForm().getEnteCollegatoDetail().getId_ente_convenz()
			.setDecodeMap(new DecodeMap());

		getForm().getEntiCollegatiList().setStatus(Status.insert);
		getForm().getEnteCollegatoDetail().setStatus(Status.insert);
		getForm().getEnteCollegatoDetail().setEditMode();
		forwardToPublisher(Application.Publisher.DETTAGLIO_APPARTENENZA_COLLEGAMENTO);
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
    }

    @Override
    public void saveDettaglio() throws EMFError {
	if (getTableName().equals(getForm().getListaEntiConvenzionati().getName())
		|| getTableName().equals(getForm().getEnteConvenzionatoDetail().getName())
		|| getTableName().equals(getForm().getEntiCessatiDetailList().getName())) {
	    saveEnteConvenzionato();
	} else if (getTableName().equals(getForm().getAccordiList().getName())
		|| getTableName().equals(getForm().getAccordoDetail().getName())) {
	    saveAccordo();
	} else if (getTableName().equals(getForm().getAnnualitaAccordoList().getName())
		|| getTableName().equals(getForm().getAnnualitaAccordoDetail().getName())) {
	    saveAnnualitaAccordo();
	} else if (getTableName().equals(getForm().getServiziErogatiList().getName())
		|| getTableName().equals(getForm().getServizioErogatoDetail().getName())) {
	    saveServizioErogato();
	} else if (getTableName().equals(getForm().getGestioniAccordoList().getName())
		|| getTableName().equals(getForm().getGestioneAccordoDetail().getName())) {
	    saveGestioneAccordo();
	} else if (getTableName().equals(getForm().getModuliInformazioniList().getName())
		|| getTableName().equals(getForm().getModuloInformazioniDetail().getName())) {
	    saveModuloInformazioni();
	} else if (getTableName().equals(getForm().getListaAmbientiEntiConvenzionati().getName())
		|| getTableName()
			.equals(getForm().getAmbienteEnteConvenzionatoDetail().getName())) {
	    saveAmbienteEnteConvenzionato();
	} else if (getTableName().equals(getForm().getAnagraficheList().getName())
		|| getTableName().equals(getForm().getAnagraficaDetail().getName())) {
	    saveModificaAnagrafica();
	} else if (getTableName().equals(getForm().getUtentiArchivistiList().getName())
		|| getTableName().equals(getForm().getUtenteArchivistaDetail().getName())) {
	    saveUtenteArchivista();
	} else if (getTableName().equals(getForm().getReferentiEnteList().getName())
		|| getTableName().equals(getForm().getReferenteEnteDetail().getName())) {
	    saveReferenteEnte();
	} else if (getTableName().equals(getForm().getDisciplinariTecniciList().getName())
		|| getTableName().equals(getForm().getDisciplinareTecnicoDetail().getName())) {
	    saveDisciplinareTecnico();
	} else if (getTableName().equals(getForm().getCollegamentiEnteList().getName())
		|| getTableName().equals(getForm().getCollegamentoEnteDetail().getName())) {
	    saveCollegamentoEnte();
	} else if (getTableName().equals(getForm().getEntiCollegatiList().getName())
		|| getTableName().equals(getForm().getEnteCollegatoDetail().getName())) {
	    saveEnteCollegato();
	} else if (getTableName().equals(getForm().getCollegamentiEnteAppartList().getName())
		|| getTableName().equals(getForm().getCollegamentoEnteAppartDetail().getName())) {
	    saveCollegamentoEnteAppart();
	}
    }

    @Override
    public void dettaglioOnClick() throws EMFError {
	if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW)
		|| getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)
		|| getNavigationEvent().equals(ListAction.NE_NEXT)
		|| getNavigationEvent().equals(ListAction.NE_PREV)) {
	    if (getTableName().equals(getForm().getListaEntiConvenzionati().getName())) {
		forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO);
	    } else if (getTableName().equals(getForm().getEntiCessatiList().getName())
		    || getTableName().equals(getForm().getEntiCessatiDetailList().getName())) {
		boolean newPage = getRequest().getAttribute("newEnteConvenzPage") != null;
		if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW)
			|| (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)
				&& newPage)) {
		    SessionManager.addPrevExecutionToHistory(getSession(), false, true);
		}
		forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO);
	    } else if ((getTableName().equals(getForm().getAccordiList().getName())
		    || getTableName().equals(getForm().getListaAccordi().getName())
		    || getTableName().equals(getForm().getAccordoDetail().getName()))
		    && !getMessageBox().hasError()) {
		forwardToPublisher(Application.Publisher.DETTAGLIO_ACCORDO);
	    } else if (getTableName().equals(getForm().getServiziErogatiList().getName())
		    || getTableName().equals(getForm().getServizioErogatoDetail().getName())) {
		forwardToPublisher(Application.Publisher.DETTAGLIO_SERVIZIO_EROGATO);
	    } else if (getTableName().equals(getForm().getAnnualitaAccordoList().getName())
		    || getTableName().equals(getForm().getAnnualitaAccordoList().getName())) {
		forwardToPublisher(Application.Publisher.DETTAGLIO_ANNUALITA);
	    } else if (getTableName().equals(getForm().getAnagraficheList().getName())) {
		forwardToPublisher(Application.Publisher.DETTAGLIO_ANAGRAFICA);
	    } else if (getTableName().equals(getForm().getGestioniAccordoList().getName())
		    && !getMessageBox().hasError()) {
		forwardToPublisher(Application.Publisher.DETTAGLIO_VARIAZIONE_ACCORDO);
	    } else if (getTableName().equals(getForm().getModuliInformazioniList().getName())
		    && !getMessageBox().hasError()) {
		forwardToPublisher(Application.Publisher.DETTAGLIO_MODULO_INFORMAZIONI);
	    } else if (getTableName()
		    .equals(getForm().getListaAmbientiEntiConvenzionati().getName())) {
		forwardToPublisher(Application.Publisher.DETTAGLIO_AMBIENTE);
	    } else if (getTableName().equals(getForm().getDisciplinariTecniciList().getName())
		    && !getMessageBox().hasError()) {
		forwardToPublisher(Application.Publisher.DETTAGLIO_DISCIPLINARE_TECNICO);
	    } else if (getTableName().equals(getForm().getFornitoriEsterniList().getName())) {
		redirectToEntiNonConvenzionati(getForm().getFornitoriEsterniList().getName());
	    } else if (getTableName().equals(getForm().getOrganiVigilanzaList().getName())) {
		redirectToEntiNonConvenzionati(getForm().getOrganiVigilanzaList().getName());
	    } else if (getTableName().equals(getForm().getFattureAccordoList().getName())) {
		redirectToFattureAccordo(getForm().getFattureAccordoList().getName());
	    } else if (getTableName().equals(getForm().getUtentiPersonaFisicaList().getName())
		    || getTableName().equals(getForm().getUtentiAutomaList().getName())) {
		/* Redirect a Dettaglio Utenti abilitati sull'ente convenzionato */
		AmministrazioneUtentiForm form = new AmministrazioneUtentiForm();
		UsrVLisUserTableBean utb = new UsrVLisUserTableBean();
		UsrVLisUserRowBean urb = new UsrVLisUserRowBean();
		BigDecimal idUserIam = null;
		String tipoUser = null;
		if (getTableName().equals(getForm().getUtentiPersonaFisicaList().getName())) {
		    idUserIam = ((UsrVLisUserEnteConvenzTableBean) getForm()
			    .getUtentiPersonaFisicaList().getTable()).getCurrentRow()
			    .getIdUserIam();
		    tipoUser = ((UsrVLisUserEnteConvenzTableBean) getForm()
			    .getUtentiPersonaFisicaList().getTable()).getCurrentRow().getTipoUser();
		} else {
		    idUserIam = ((UsrVLisUserEnteConvenzTableBean) getForm().getUtentiAutomaList()
			    .getTable()).getCurrentRow().getIdUserIam();
		    tipoUser = ((UsrVLisUserEnteConvenzTableBean) getForm().getUtentiAutomaList()
			    .getTable()).getCurrentRow().getTipoUser();
		}
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
	    } else if (getTableName().equals(getForm().getUtentiEnteList().getName())) {
		/* Redirect a Dettaglio Utenti appartenenti all'ente convenzionato */
		AmministrazioneUtentiForm form = new AmministrazioneUtentiForm();
		UsrVLisUserTableBean utb = new UsrVLisUserTableBean();
		UsrVLisUserRowBean urb = new UsrVLisUserRowBean();
		BigDecimal idUserIam = ((UsrUserTableBean) getForm().getUtentiEnteList().getTable())
			.getCurrentRow().getIdUserIam();
		String tipoUser = ((UsrUserTableBean) getForm().getUtentiEnteList().getTable())
			.getCurrentRow().getTipoUser();
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
	    } else if (getTableName().equals(getForm().getReferentiEnteList().getName())) {
		if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)) {
		    forwardToPublisher(Application.Publisher.DETTAGLIO_REFERENTE_ENTE);
		} else {
		    try {
			/* Redirect a Dettaglio Utenti referenti dell'ente convenzionato */
			AmministrazioneUtentiForm form = new AmministrazioneUtentiForm();
			UsrVLisUserTableBean utb = new UsrVLisUserTableBean();
			UsrVLisUserRowBean urb = new UsrVLisUserRowBean();
			BigDecimal idUserIam = ((OrgEnteUserRifTableBean) getForm()
				.getReferentiEnteList().getTable()).getCurrentRow().getIdUserIam();
			entiConvenzionatiEjb.checkReferenteEnteCessato(idUserIam);
			String tipoUser = ((OrgEnteUserRifTableBean) getForm()
				.getReferentiEnteList().getTable()).getCurrentRow()
				.getString("tipo_user");
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
		    } catch (ParerUserError e) {
			getMessageBox().addError(e.getDescription());
			forwardToPublisher(getLastPublisher());
		    }
		}
	    } else if (getTableName().equals(getForm().getUtentiArchivistiList().getName())) {
		if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)) {
		    forwardToPublisher(Application.Publisher.DETTAGLIO_ARCHIVISTA_RIFERIMENTO);
		} else {
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
	    } else if (getTableName().equals(getForm().getEntiCollegatiList().getName())) {
		forwardToPublisher(Application.Publisher.DETTAGLIO_APPARTENENZA_COLLEGAMENTO);
	    } else if (getTableName().equals(getForm().getCollegamentiEnteAppartList().getName())) {
		forwardToPublisher(Application.Publisher.DETTAGLIO_APPARTENENZA_ENTE);
	    } else if (getTableName().equals(getForm().getCollegamentiEnteList().getName())) {
		forwardToPublisher(Application.Publisher.DETTAGLIO_COLLEGAMENTO_ENTE);
	    }
	}
    }

    @Override
    public void elencoOnClick() throws EMFError {
	if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO)) {
	    if (getSession().getAttribute("navTableEntiConvenz")
		    .equals(AmministrazioneEntiConvenzionatiForm.EntiCessatiList.NAME)
		    || getSession().getAttribute("navTableEntiConvenz").equals(
			    AmministrazioneEntiConvenzionatiForm.ListaEntiConvenzionati.NAME)) {
		goBack();
	    } else {
		// Ritorno dal riepilogo del wizard di inserimento ente convenzionato
		goBackTo(Application.Publisher.RICERCA_ENTI_CONVENZIONATI);
	    }
	} else {
	    goBack();
	}
    }

    @Override
    protected String getDefaultPublsherName() {
	return Application.Publisher.RICERCA_ENTI_CONVENZIONATI;
    }

    @Override
    public void reloadAfterGoBack(String publisherName) {
	try {
	    if (publisherName.equals(Application.Publisher.RICERCA_ENTI_CONVENZIONATI)) {
		int rowIndex;
		int pageSize;
		if (getForm().getListaEntiConvenzionati().getTable() != null) {
		    rowIndex = getForm().getListaEntiConvenzionati().getTable()
			    .getCurrentRowIndex();
		    pageSize = getForm().getListaEntiConvenzionati().getTable().getPageSize();
		} else {
		    rowIndex = 0;
		    pageSize = 10;
		}
		String nmEnteConvenz = getForm().getFiltriEntiConvenzionati().getNm_ente_convenz()
			.parse();
		BigDecimal idUserIamCor = new BigDecimal(getUser().getIdUtente());
		BigDecimal idAmbienteEnteConvenz = getForm().getFiltriEntiConvenzionati()
			.getId_ambiente_ente_convenz().parse();
		String flEnteAttivo = getForm().getFiltriEntiConvenzionati().getFl_ente_attivo()
			.parse();
		String flEnteCessato = getForm().getFiltriEntiConvenzionati().getFl_ente_cessato()
			.parse();
		BigDecimal idCategEnte = getForm().getFiltriEntiConvenzionati().getDs_categ_ente()
			.parse();
		List<BigDecimal> idAmbitoTerritRegione = getForm().getFiltriEntiConvenzionati()
			.getCd_ambito_territ_regione().parse();
		List<BigDecimal> idAmbitoTerritProv = getForm().getFiltriEntiConvenzionati()
			.getCd_ambito_territ_provincia().parse();
		List<BigDecimal> idAmbitoTerritFormAssoc = getForm().getFiltriEntiConvenzionati()
			.getCd_ambito_territ_forma_associata().parse();
		List<BigDecimal> idTipoAccordo = getForm().getFiltriEntiConvenzionati()
			.getCd_tipo_accordo().parse();
		Date dtDecAccordoDa = getForm().getFiltriEntiConvenzionati().getDt_dec_accordo_da()
			.parse();
		Date dtDecAccordoA = getForm().getFiltriEntiConvenzionati().getDt_dec_accordo_a()
			.parse();
		Date dtDecAccordoInfoDa = getForm().getFiltriEntiConvenzionati()
			.getDt_dec_accordo_info_da().parse();
		Date dtDecAccordoInfoA = getForm().getFiltriEntiConvenzionati()
			.getDt_dec_accordo_info_a().parse();
		Date dtFineValidAccordoDa = getForm().getFiltriEntiConvenzionati()
			.getDt_fine_valid_accordo_da().parse();
		Date dtFineValidAccordoA = getForm().getFiltriEntiConvenzionati()
			.getDt_fine_valid_accordo_a().parse();
		Date dtScadAccordoDa = getForm().getFiltriEntiConvenzionati()
			.getDt_scad_accordo_da().parse();
		Date dtScadAccordoA = getForm().getFiltriEntiConvenzionati().getDt_scad_accordo_a()
			.parse();
		Date dtIniValDa = getForm().getFiltriEntiConvenzionati().getDt_ini_val_da().parse();
		Date dtIniValA = getForm().getFiltriEntiConvenzionati().getDt_ini_val_a().parse();
		Date dtCessazioneDa = getForm().getFiltriEntiConvenzionati().getDt_cessazione_da()
			.parse();
		Date dtCessazioneA = getForm().getFiltriEntiConvenzionati().getDt_cessazione_a()
			.parse();
		List<BigDecimal> idArchivista = getForm().getFiltriEntiConvenzionati()
			.getArchivista().parse();
		String noArchivista = getForm().getFiltriEntiConvenzionati().getNo_archivista()
			.parse();
		String flRicev = getForm().getFiltriEntiConvenzionati().getFl_ricezione_modulo_inf()
			.parse();
		String flRichModuloInfo = getForm().getFiltriEntiConvenzionati()
			.getFl_richiesta_modulo_inf().parse();
		String flNonConvenz = getForm().getFiltriEntiConvenzionati().getFl_non_convenz()
			.parse();
		String flRecesso = getForm().getFiltriEntiConvenzionati().getFl_recesso().parse();
		String flChiuso = getForm().getFiltriEntiConvenzionati().getFl_chiuso().parse();
		String flEsistonoGestAcc = getForm().getFiltriEntiConvenzionati()
			.getFl_esistono_gest_acc().parse();
		List<BigDecimal> idTipoGestioneAccordo = getForm().getFiltriEntiConvenzionati()
			.getTipo_gestione_accordo().parse();
		String flGestAccNoRisp = getForm().getFiltriEntiConvenzionati()
			.getFl_gest_acc_no_risp().parse();
		String tiStatoAccordo = getForm().getFiltriEntiConvenzionati().getTi_stato_accordo()
			.parse();
		String cdFisc = getForm().getFiltriEntiConvenzionati().getCd_fisc().parse();
		OrgVRicEnteConvenzTableBean table = entiConvenzionatiEjb
			.getOrgVRicEnteConvenzTableBean(nmEnteConvenz, idUserIamCor,
				idAmbienteEnteConvenz, flEnteAttivo, flEnteCessato, idCategEnte,
				idAmbitoTerritRegione, idAmbitoTerritProv, idAmbitoTerritFormAssoc,
				idTipoAccordo, dtFineValidAccordoDa, dtFineValidAccordoA,
				dtScadAccordoDa, dtScadAccordoA, idArchivista, noArchivista,
				flRicev, flRichModuloInfo, flNonConvenz, flRecesso, flChiuso,
				flEsistonoGestAcc, idTipoGestioneAccordo, flGestAccNoRisp,
				tiStatoAccordo, cdFisc, dtDecAccordoDa, dtDecAccordoA,
				dtDecAccordoInfoDa, dtDecAccordoInfoA, dtIniValDa, dtIniValA,
				dtCessazioneDa, dtCessazioneA);
		getForm().getListaEntiConvenzionati().setTable(table);
		getForm().getListaEntiConvenzionati().getTable().setPageSize(pageSize);
		getForm().getListaEntiConvenzionati().getTable().setCurrentRowIndex(rowIndex);
	    } else if (publisherName.equals(Application.Publisher.RICERCA_COLLEGAMENTI)) {
		int rowIndex;
		int pageSize;
		if (getForm().getCollegamentiEnteList().getTable() != null) {
		    rowIndex = getForm().getCollegamentiEnteList().getTable().getCurrentRowIndex();
		    pageSize = getForm().getCollegamentiEnteList().getTable().getPageSize();
		} else {
		    rowIndex = 0;
		    pageSize = 10;
		}
		String nmColleg = getForm().getFiltriCollegamenti().getNm_colleg().parse();
		String dsColleg = getForm().getFiltriCollegamenti().getDs_colleg().parse();
		Date dtValidDa = getForm().getFiltriCollegamenti().getDt_valid_da().parse();
		Date dtValidA = getForm().getFiltriCollegamenti().getDt_valid_a().parse();
		BigDecimal idUserIamCor = new BigDecimal(getUser().getIdUtente());

		OrgCollegEntiConvenzTableBean table = entiConvenzionatiEjb
			.getOrgCollegEntiConvenzTableBean(idUserIamCor, nmColleg, dsColleg,
				dtValidDa, dtValidA);
		getForm().getCollegamentiEnteList().setTable(table);
		getForm().getCollegamentiEnteList().getTable().setPageSize(pageSize);
		getForm().getCollegamentiEnteList().getTable().setCurrentRowIndex(rowIndex);
	    } else if (publisherName.equals(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO)) {
		BigDecimal idEnteConvenz;
		int level = 1;
		if (getLastPublisher().equals(publisherName)) {
		    // Recupero l'ultimo elemento dalla pila e lo rimuovo da essa
		    NavigatorDetailBean detailBean = NavigatorDetailBeanManager
			    .popNavigatorDetailStack();
		    // Dall'oggetto NavigatorDetailBean mi ricavo l'id ente convenzionato
		    idEnteConvenz = detailBean != null ? detailBean.getIdObject() : null;

		    if (idEnteConvenz != null && idEnteConvenz.equals(
			    getForm().getEnteConvenzionatoDetail().getId_ente_siam().parse())) {
			// Dopo aver rimosso col pop precedente l'ultimo elemento dalla pila, ora
			// prendo l'attuale
			// ultimo che sarà quello di cui caricare il dettaglio
			detailBean = NavigatorDetailBeanManager.getLastNavigatorDetailStack();
			idEnteConvenz = detailBean != null ? detailBean.getIdObject()
				: idEnteConvenz;
		    } else if (idEnteConvenz == null) {
			// Nel caso lo stack sia vuoto, non dovrebbe succedere mai.
			idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam()
				.parse();
		    }

		    // Recupero i valori dell'oggetto
		    if (detailBean != null) {
			// Salvo in sessione l'attributo riferito alla sourceList
			getSession().setAttribute("navTableEntiConvenz",
				detailBean.getSourceList());
			// Setto la lista corretta, ListaEntiConvenzionati o EntiCessatiList
			if (detailBean.getSourceList()
				.equals(getForm().getEntiCessatiList().getName())) {
			    getForm().getEntiCessatiList().setTable(detailBean.getSourceTable());
			} else {
			    ((it.eng.spagoLite.form.list.List<SingleValueField<?>>) getForm()
				    .getComponent(detailBean.getSourceList()))
				    .setTable(detailBean.getSourceTable());
			}
			// Recupero il livello di annidamento
			level = detailBean.getLevel();
		    }
		} else {
		    idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam()
			    .parse();
		    level = getForm().getEnteConvenzionatoDetail().getLevel_enti_convenz()
			    .parse() != null
				    ? getForm().getEnteConvenzionatoDetail().getLevel_enti_convenz()
					    .parse().intValue()
				    : 1;
		}

		// Carico il dettaglio fattura
		loadDettaglioEnteConvenzionato(idEnteConvenz);

		// Mi salvo il livello di annidamento attuale
		getForm().getEnteConvenzionatoDetail().getLevel_enti_convenz()
			.setValue(String.valueOf(level));

		// Rimuovo dalla sessione l'attributo riferito al cambio gestore
		getSession().removeAttribute("cambioGestore");

		// Rimuovo dalla sessione l'attributo riferito al nuovo accordo
		getSession().removeAttribute("nuovoAccordo");

	    } else if (publisherName.equals(Application.Publisher.DETTAGLIO_ACCORDO)) {
		OrgAccordoEnteRowBean currentRow = new OrgAccordoEnteRowBean();
		if (getForm().getAccordiList() != null
			&& getForm().getAccordiList().getTable() != null) {
		    currentRow = (OrgAccordoEnteRowBean) getForm().getAccordiList().getTable()
			    .getCurrentRow();
		} else {
		    getForm().getAccordoDetail().copyToBean(currentRow);
		}
		BigDecimal idAccordo = currentRow.getIdAccordoEnte();
		BigDecimal idTipoAccordo = currentRow.getIdTipoAccordo();
		if (idAccordo != null) {
		    loadDettaglioAccordo(idAccordo, idTipoAccordo);
		}
		getForm().getAccordoDetail().setViewMode();
		// Bottone di ricalcolo
		getForm().getAccordoDetail().getRicalcoloServiziErogati().setHidden(false);
		getForm().getAccordoDetail().getRicalcoloServiziErogati().setEditMode();

		getForm().getAccordoDetail().setStatus(Status.view);
		getForm().getAccordiList().setStatus(Status.view);
	    } else if (publisherName.equals(Application.Publisher.RICERCA_AMBIENTI)) {
		int rowIndex;
		int pageSize;
		if (getForm().getListaAmbientiEntiConvenzionati().getTable() != null) {
		    rowIndex = getForm().getListaAmbientiEntiConvenzionati().getTable()
			    .getCurrentRowIndex();
		    pageSize = getForm().getListaAmbientiEntiConvenzionati().getTable()
			    .getPageSize();
		} else {
		    rowIndex = 0;
		    pageSize = 10;
		}
		BigDecimal idUserIamCor = new BigDecimal(getUser().getIdUtente());
		String nmAmbienteEnteConvenz = getForm().getFiltriAmbientiEntiConvenzionati()
			.getNm_ambiente_ente_convenz().parse();
		String dsAmbienteEnteConvenz = getForm().getFiltriAmbientiEntiConvenzionati()
			.getDs_ambiente_ente_convenz().parse();
		UsrVAbilAmbConvenzXenteTableBean table = entiConvenzionatiEjb
			.getUsrVAbilAmbConvenzXenteTableBean(idUserIamCor, nmAmbienteEnteConvenz,
				dsAmbienteEnteConvenz);
		getForm().getListaAmbientiEntiConvenzionati().setTable(table);
		getForm().getListaAmbientiEntiConvenzionati().getTable().setPageSize(pageSize);
		getForm().getListaAmbientiEntiConvenzionati().getTable()
			.setCurrentRowIndex(rowIndex);
	    } else if (publisherName
		    .equals(Application.Publisher.DETTAGLIO_ARCHIVISTA_RIFERIMENTO)) {
		String idUserIamUtenteArk = (String) getSession()
			.getAttribute("idUserIamUtenteArk");
		String nmUseridUtenteCodificatoArk = (String) getSession()
			.getAttribute("nmUseridUtenteCodificatoArk");
		getForm().getUtenteArchivistaDetail().getId_user_iam().setValue(idUserIamUtenteArk);
		getForm().getUtenteArchivistaDetail().getNm_userid_codificato()
			.setValue(nmUseridUtenteCodificatoArk);
		getForm().getUtenteArchivistaDetail().getNm_userid_codificato().setViewMode();
		getSession().removeAttribute("idUserIamUtenteArk");
		getSession().removeAttribute("nmUseridUtenteCodificatoArk");
	    } else if (publisherName.equals(Application.Publisher.DETTAGLIO_REFERENTE_ENTE)) {
		String idUserIamReferenteEnte = (String) getSession()
			.getAttribute("idUserIamReferenteEnte");
		String nmUseridUtenteCodificatoReferenteEnte = (String) getSession()
			.getAttribute("nmUseridUtenteCodificatoReferenteEnte");
		getForm().getReferenteEnteDetail().getId_user_iam()
			.setValue(idUserIamReferenteEnte);
		getForm().getReferenteEnteDetail().getNm_userid_codificato()
			.setValue(nmUseridUtenteCodificatoReferenteEnte);
		getForm().getReferenteEnteDetail().getNm_userid_codificato().setViewMode();
		getSession().removeAttribute("idUserIamReferenteEnte");
		getSession().removeAttribute("nmUseridUtenteCodificatoReferenteEnte");
	    } else if (publisherName.equals(Application.Publisher.DETTAGLIO_VARIAZIONE_ACCORDO)
		    && !getIdGestAccStack().isEmpty()) {
		try {
		    List<BigDecimal> idGestAccStack = getIdGestAccStack();
		    BigDecimal id = idGestAccStack.remove(idGestAccStack.size() - 1);
		    getSession().setAttribute("idGestAccStack", idGestAccStack);
		    loadDettaglioGestioneAccordo(id);

		    if (getForm().getFakeGestioniAccordoList().getTable() != null
			    && !getForm().getFakeGestioniAccordoList().getTable().isEmpty()) {
			getForm().getFakeGestioniAccordoList().getTable().last();
			getForm().getFakeGestioniAccordoList().getTable().remove();
		    }
		} catch (EMFError ex) {
		    getMessageBox()
			    .addError("Errore inatteso nel caricamento della gestione accordo");
		}
	    }
	    postLoad();
	} catch (ParerUserError e) {
	    getMessageBox().addError(e.getDescription());
	    forwardToPublisher(getLastPublisher());
	} catch (EMFError e) {
	    logger.error("Errore nel ricaricamento della pagina " + publisherName, e);
	    getMessageBox().addError("Errore nel ricaricamento della pagina " + publisherName);
	    forwardToPublisher(getLastPublisher());
	}
    }

    @Override
    public String getControllerName() {
	return Application.Actions.AMMINISTRAZIONE_ENTI_CONVENZIONATI;
    }

    // <editor-fold defaultstate="expand" desc="Gestione collegamenti">
    @Secure(action = "Menu.EntiConvenzionati.GestioneCollegamenti")
    public void ricercaCollegamentiPage() {
	try {
	    getUser().getMenu().reset();
	    getUser().getMenu().select("Menu.EntiConvenzionati.GestioneCollegamenti");

	    initFiltriRicercaCollegamenti();

	    getForm().getCollegamentiEnteList().setTable(null);

	    forwardToPublisher(Application.Publisher.RICERCA_COLLEGAMENTI);
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
    }

    private void initFiltriRicercaCollegamenti() throws ParerUserError {
	getForm().getFiltriCollegamenti().reset();
	getForm().getFiltriCollegamenti().setEditMode();
    }

    @Override
    public void ricercaCollegamenti() throws EMFError {
	if (getForm().getFiltriCollegamenti().postAndValidate(getRequest(), getMessageBox())) {
	    BigDecimal idUserIamCor = new BigDecimal(getUser().getIdUtente());
	    String nmColleg = getForm().getFiltriCollegamenti().getNm_colleg().parse();
	    String dsColleg = getForm().getFiltriCollegamenti().getDs_colleg().parse();
	    Date dtValidDa = getForm().getFiltriCollegamenti().getDt_valid_da().parse();
	    Date dtValidA = getForm().getFiltriCollegamenti().getDt_valid_a().parse();

	    // Controllo coerenza date
	    if (dtValidDa != null && dtValidA != null && dtValidDa.after(dtValidA)) {
		getMessageBox().addError(
			"Attenzione: Data inizio validit\u00E0 maggiore di Data fine validit\u00E0 ");
	    }

	    if (!getMessageBox().hasError()) {
		try {
		    OrgCollegEntiConvenzTableBean table = entiConvenzionatiEjb
			    .getOrgCollegEntiConvenzTableBean(idUserIamCor, nmColleg, dsColleg,
				    dtValidDa, dtValidA);
		    getForm().getCollegamentiEnteList().setTable(table);
		    getForm().getCollegamentiEnteList().getTable().setPageSize(10);
		    getForm().getCollegamentiEnteList().getTable().first();
		} catch (ParerUserError pue) {
		    getMessageBox().addError(pue.getDescription());
		}
	    }
	}
	forwardToPublisher(getLastPublisher());
    }
    // </editor-fold>

    // <editor-fold defaultstate="expand" desc="Gestione ricerca enti convenzionati">
    @Secure(action = "Menu.EntiConvenzionati.GestioneEntiConvenzionati")
    public void ricercaEntiConvenzionatiPage() throws EMFError {
	try {
	    getUser().getMenu().reset();
	    getUser().getMenu().select("Menu.EntiConvenzionati.GestioneEntiConvenzionati");

	    initFiltriRicercaEntiConvenzionati();

	    getForm().getListaEntiConvenzionati().setTable(null);
	    getForm().getAnnualitaSenzaAttoList().setTable(null);
	    getForm().getAnnualitaSenzaAttoSection().setHidden(true);
	    getForm().getFattureProvvisorieFields().getCalcoloFattureProvvisorie().setViewMode();
	    getForm().getRiemissioneFattureFields().getRiemettiFattureStornate().setViewMode();

	    forwardToPublisher(Application.Publisher.RICERCA_ENTI_CONVENZIONATI);
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
    }

    private void initFiltriRicercaEntiConvenzionati() throws ParerUserError, EMFError {
	getForm().getFiltriEntiConvenzionati().reset();
	getForm().getFiltriEntiConvenzionati().setEditMode();
	getForm().getFiltriEntiConvenzionati().getInserisciEnteConvenzionatoWizard().setEditMode();
	getForm().getFiltriEntiConvenzionati().getId_ambiente_ente_convenz()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getUsrVAbilAmbEnteConvenzTableBean(
				new BigDecimal(getUser().getIdUtente())),
			"id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));
	getForm().getFiltriEntiConvenzionati().getFl_ente_attivo()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	getForm().getFiltriEntiConvenzionati().getFl_ente_cessato()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	getForm().getFiltriEntiConvenzionati().getCd_ambito_territ_regione()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgAmbitoTerritTableBean(
				ConstOrgAmbitoTerrit.TiAmbitoTerrit.REGIONE_STATO.toString()),
			"id_ambito_territ", "cd_ambito_territ"));
	getForm().getFiltriEntiConvenzionati().getCd_ambito_territ_provincia()
		.setDecodeMap(new DecodeMap());
	getForm().getFiltriEntiConvenzionati().getCd_ambito_territ_forma_associata()
		.setDecodeMap(new DecodeMap());
	getForm().getFiltriEntiConvenzionati().getDs_categ_ente()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgCategEnteTableBean(), "id_categ_ente",
			"ds_categ_ente"));
	getForm().getFiltriEntiConvenzionati().getCd_tipo_accordo()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgTipoAccordoTableBean(), "id_tipo_accordo",
			"cd_tipo_accordo"));
	getForm().getFiltriEntiConvenzionati().getArchivista().setDecodeMap(
		DecodeMap.Factory.newInstance(entiConvenzionatiEjb.getUtentiArchivisti(),
			"id_user_iam", "nm_userid"));
	getForm().getFiltriEntiConvenzionati().getFl_richiesta_modulo_inf()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	getForm().getFiltriEntiConvenzionati().getFl_ricezione_modulo_inf()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	getForm().getFiltriEntiConvenzionati().getFl_non_convenz()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	getForm().getFiltriEntiConvenzionati().getFl_recesso()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	getForm().getFiltriEntiConvenzionati().getFl_chiuso()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	getForm().getFiltriEntiConvenzionati().getFl_esistono_gest_acc()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	getForm().getFiltriEntiConvenzionati().getTipo_gestione_accordo()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgTipiGestioneAccordoTableBean(),
			"id_tipo_gestione_accordo", "cd_tipo_gestione_accordo"));
	getForm().getFiltriEntiConvenzionati().getFl_gest_acc_no_risp()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	getForm().getFiltriEntiConvenzionati().getTi_stato_accordo()
		.setDecodeMap(ComboGetter.getMappaTiStatoAccordo());
    }

    @Override
    public void ricercaEntiConvenzionati() throws EMFError {
	if (getForm().getFiltriEntiConvenzionati().postAndValidate(getRequest(), getMessageBox())) {
	    getForm().getListaAccordi().setTable(null);
	    BigDecimal idUserIamCor = new BigDecimal(getUser().getIdUtente());
	    String nmEnteConvenz = getForm().getFiltriEntiConvenzionati().getNm_ente_convenz()
		    .parse();
	    BigDecimal idAmbienteEnteConvenz = getForm().getFiltriEntiConvenzionati()
		    .getId_ambiente_ente_convenz().parse();
	    String flEnteAttivo = getForm().getFiltriEntiConvenzionati().getFl_ente_attivo()
		    .parse();
	    String flEnteCessato = getForm().getFiltriEntiConvenzionati().getFl_ente_cessato()
		    .parse();
	    BigDecimal idCategEnte = getForm().getFiltriEntiConvenzionati().getDs_categ_ente()
		    .parse();
	    List<BigDecimal> idAmbitoTerritRegione = getForm().getFiltriEntiConvenzionati()
		    .getCd_ambito_territ_regione().parse();
	    List<BigDecimal> idAmbitoTerritProv = getForm().getFiltriEntiConvenzionati()
		    .getCd_ambito_territ_provincia().parse();
	    List<BigDecimal> idAmbitoTerritFormAssoc = getForm().getFiltriEntiConvenzionati()
		    .getCd_ambito_territ_forma_associata().parse();
	    List<BigDecimal> cdTipoAccordo = getForm().getFiltriEntiConvenzionati()
		    .getCd_tipo_accordo().parse();
	    List<BigDecimal> idArchivista = getForm().getFiltriEntiConvenzionati().getArchivista()
		    .parse();
	    String noArchivista = getForm().getFiltriEntiConvenzionati().getNo_archivista().parse();
	    String flRicev = getForm().getFiltriEntiConvenzionati().getFl_ricezione_modulo_inf()
		    .parse();
	    String flRichiestaModuloInf = getForm().getFiltriEntiConvenzionati()
		    .getFl_richiesta_modulo_inf().parse();
	    String flNonConvenz = getForm().getFiltriEntiConvenzionati().getFl_non_convenz()
		    .parse();
	    String flRecesso = getForm().getFiltriEntiConvenzionati().getFl_recesso().parse();
	    String flChiuso = getForm().getFiltriEntiConvenzionati().getFl_chiuso().parse();
	    String flEsistonoGestAcc = getForm().getFiltriEntiConvenzionati()
		    .getFl_esistono_gest_acc().parse();
	    List<BigDecimal> idTipoGestioneAccordo = getForm().getFiltriEntiConvenzionati()
		    .getTipo_gestione_accordo().parse();
	    String flGestAccNoRisp = getForm().getFiltriEntiConvenzionati().getFl_gest_acc_no_risp()
		    .parse();
	    String tiStatoAccordo = getForm().getFiltriEntiConvenzionati().getTi_stato_accordo()
		    .parse();
	    String cdFisc = getForm().getFiltriEntiConvenzionati().getCd_fisc().parse();

	    // Sistemo il range di date
	    decoraDateRicercaEnti(
		    getForm().getFiltriEntiConvenzionati().getDt_dec_accordo_da().parse(),
		    getForm().getFiltriEntiConvenzionati().getDt_dec_accordo_a().parse(),
		    getForm().getFiltriEntiConvenzionati().getDt_fine_valid_accordo_da().parse(),
		    getForm().getFiltriEntiConvenzionati().getDt_fine_valid_accordo_a().parse(),
		    getForm().getFiltriEntiConvenzionati().getDt_dec_accordo_info_da().parse(),
		    getForm().getFiltriEntiConvenzionati().getDt_dec_accordo_info_a().parse(),
		    getForm().getFiltriEntiConvenzionati().getDt_scad_accordo_da().parse(),
		    getForm().getFiltriEntiConvenzionati().getDt_scad_accordo_a().parse(),
		    getForm().getFiltriEntiConvenzionati().getDt_ini_val_da().parse(),
		    getForm().getFiltriEntiConvenzionati().getDt_ini_val_a().parse(),
		    getForm().getFiltriEntiConvenzionati().getDt_cessazione_da().parse(),
		    getForm().getFiltriEntiConvenzionati().getDt_cessazione_a().parse());

	    Date dtDecAccordoDa = getForm().getFiltriEntiConvenzionati().getDt_dec_accordo_da()
		    .parse();
	    Date dtDecAccordoA = getForm().getFiltriEntiConvenzionati().getDt_dec_accordo_a()
		    .parse();
	    Date dtDecAccordoInfoDa = getForm().getFiltriEntiConvenzionati()
		    .getDt_dec_accordo_info_da().parse();
	    Date dtDecAccordoInfoA = getForm().getFiltriEntiConvenzionati()
		    .getDt_dec_accordo_info_a().parse();
	    Date dtFineValidAccordoDa = getForm().getFiltriEntiConvenzionati()
		    .getDt_fine_valid_accordo_da().parse();
	    Date dtFineValidAccordoA = getForm().getFiltriEntiConvenzionati()
		    .getDt_fine_valid_accordo_a().parse();
	    Date dtScadAccordoDa = getForm().getFiltriEntiConvenzionati().getDt_scad_accordo_da()
		    .parse();
	    Date dtScadAccordoA = getForm().getFiltriEntiConvenzionati().getDt_scad_accordo_a()
		    .parse();
	    Date dtIniValDa = getForm().getFiltriEntiConvenzionati().getDt_ini_val_da().parse();
	    Date dtIniValA = getForm().getFiltriEntiConvenzionati().getDt_ini_val_a().parse();
	    Date dtCessazioneDa = getForm().getFiltriEntiConvenzionati().getDt_cessazione_da()
		    .parse();
	    Date dtCessazioneA = getForm().getFiltriEntiConvenzionati().getDt_cessazione_a()
		    .parse();

	    String errorDateDec = null;
	    String errorDateScad = null;
	    String errorDateIniVal = null;
	    String errorDateFineVal = null;
	    String errorCdFisc = null;
	    String errorDateIniValEnte = null;
	    String errorDateCessazione = null;

	    errorDateDec = DateUtil.ucContrDate("", dtDecAccordoInfoDa, dtDecAccordoInfoA,
		    "decorrenza Da", "decorrenza A");
	    errorDateScad = DateUtil.ucContrDate("", dtScadAccordoDa, dtScadAccordoA, "scadenza Da",
		    "scadenza A");
	    errorDateIniVal = DateUtil.ucContrDate("", dtDecAccordoDa, dtDecAccordoA,
		    "inizio validità Da", "inizio validità A");
	    errorDateFineVal = DateUtil.ucContrDate("", dtFineValidAccordoDa, dtFineValidAccordoA,
		    "fine validità Da", "fine validità A");
	    errorDateIniValEnte = DateUtil.ucContrDate("", dtIniValDa, dtIniValA,
		    "inizio validità ente Da", "inizio validità ente A");
	    errorDateCessazione = DateUtil.ucContrDate("", dtCessazioneDa, dtCessazioneA,
		    "cessazione Da", "cessazione A");
	    if (!StringUtils.isBlank(cdFisc)) {
		errorCdFisc = !StringUtils.isAlphanumeric(cdFisc)
			? "Il codice fiscale/partita IVA contiene caratteri non validi."
			: null;
	    }

	    if (errorDateDec != null) {
		getMessageBox().addError(errorDateDec);
	    }
	    if (errorDateScad != null) {
		getMessageBox().addError(errorDateScad);
	    }
	    if (errorDateIniVal != null) {
		getMessageBox().addError(errorDateIniVal);
	    }
	    if (errorDateFineVal != null) {
		getMessageBox().addError(errorDateFineVal);
	    }
	    if (errorDateIniValEnte != null) {
		getMessageBox().addError(errorDateIniValEnte);
	    }
	    if (errorDateCessazione != null) {
		getMessageBox().addError(errorDateCessazione);
	    }
	    if (errorCdFisc != null) {
		getMessageBox().addError(errorCdFisc);
	    }

	    // Nascondo Annualità senza atto
	    getForm().getAnnualitaSenzaAttoList().setTable(null);
	    getForm().getAnnualitaSenzaAttoSection().setHidden(true);

	    if (!getMessageBox().hasError()) {
		try {
		    OrgVRicEnteConvenzTableBean table = entiConvenzionatiEjb
			    .getOrgVRicEnteConvenzTableBean(nmEnteConvenz, idUserIamCor,
				    idAmbienteEnteConvenz, flEnteAttivo, flEnteCessato, idCategEnte,
				    idAmbitoTerritRegione, idAmbitoTerritProv,
				    idAmbitoTerritFormAssoc, cdTipoAccordo, dtFineValidAccordoDa,
				    dtFineValidAccordoA, dtScadAccordoDa, dtScadAccordoA,
				    idArchivista, noArchivista, flRicev, flRichiestaModuloInf,
				    flNonConvenz, flRecesso, // bdc
				    flChiuso, flEsistonoGestAcc, idTipoGestioneAccordo,
				    flGestAccNoRisp, tiStatoAccordo, cdFisc, dtDecAccordoDa,
				    dtDecAccordoA, dtDecAccordoInfoDa, dtDecAccordoInfoA,
				    dtIniValDa, dtIniValA, dtCessazioneDa, dtCessazioneA);
		    getForm().getListaEntiConvenzionati().setTable(table);
		    getForm().getListaEntiConvenzionati().getTable().setPageSize(10);
		    getForm().getListaEntiConvenzionati().getTable().first();
		    getForm().getAnnualitaSenzaAttoList().setTable(null);

		    // Visualizzazione eventuale bottone calcolo fatture provvisorie a seconda che
		    // ci sia un risultato
		    if (!table.isEmpty()) {
			getForm().getFattureProvvisorieFields().getCalcoloFattureProvvisorie()
				.setEditMode();
			getForm().getRiemissioneFattureFields().getRiemettiFattureStornate()
				.setEditMode();
		    } else {
			getForm().getFattureProvvisorieFields().getCalcoloFattureProvvisorie()
				.setViewMode();
			getForm().getRiemissioneFattureFields().getRiemettiFattureStornate()
				.setViewMode();
		    }

		} catch (ParerUserError pue) {
		    getMessageBox().addError(pue.getDescription());
		}
	    }
	}
	forwardToPublisher(getLastPublisher());
    }

    // bdc stop
    private void decoraDateRicercaEnti(Date dtDecAccordoDa, Date dtDecAccordoA, Date dtFineValDa,
	    Date dtFineValA, Date dtDecAccordoInfoDa, Date dtDecAccordoInfoA, Date dtScadAccordoDa,
	    Date dtScadAccordoA, Date dtIniValDa, Date dtIniValA, Date dtCessazioneDa,
	    Date dtCessazioneA) {
	DateFormat formato = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH);

	if (dtDecAccordoDa != null && dtDecAccordoA == null) {
	    Calendar c = Calendar.getInstance();
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);
	    dtDecAccordoA = c.getTime();
	    getForm().getFiltriEntiConvenzionati().getDt_dec_accordo_a()
		    .setValue(formato.format(dtDecAccordoA));
	} else if (dtDecAccordoDa == null && dtDecAccordoA != null) {
	    Calendar c = Calendar.getInstance();
	    c.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
	    dtDecAccordoDa = c.getTime();
	    getForm().getFiltriEntiConvenzionati().getDt_dec_accordo_da()
		    .setValue(formato.format(dtDecAccordoDa));
	}

	if (dtFineValDa != null && dtFineValA == null) {
	    Calendar c = Calendar.getInstance();
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);
	    dtFineValA = c.getTime();
	    getForm().getFiltriEntiConvenzionati().getDt_fine_valid_accordo_a()
		    .setValue(formato.format(dtFineValA));
	} else if (dtFineValDa == null && dtFineValA != null) {
	    Calendar c = Calendar.getInstance();
	    c.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
	    dtFineValDa = c.getTime();
	    getForm().getFiltriEntiConvenzionati().getDt_fine_valid_accordo_da()
		    .setValue(formato.format(dtFineValDa));
	}

	if (dtDecAccordoInfoDa != null && dtDecAccordoInfoA == null) {
	    Calendar c = Calendar.getInstance();
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);
	    dtDecAccordoInfoA = c.getTime();
	    getForm().getFiltriEntiConvenzionati().getDt_dec_accordo_info_a()
		    .setValue(formato.format(dtDecAccordoInfoA));
	} else if (dtDecAccordoInfoDa == null && dtDecAccordoInfoA != null) {
	    Calendar c = Calendar.getInstance();
	    c.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
	    dtDecAccordoInfoDa = c.getTime();
	    getForm().getFiltriEntiConvenzionati().getDt_dec_accordo_info_da()
		    .setValue(formato.format(dtDecAccordoInfoDa));
	}

	if (dtScadAccordoDa != null && dtScadAccordoA == null) {
	    Calendar c = Calendar.getInstance();
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);
	    dtScadAccordoA = c.getTime();
	    getForm().getFiltriEntiConvenzionati().getDt_scad_accordo_a()
		    .setValue(formato.format(dtScadAccordoA));
	} else if (dtScadAccordoDa == null && dtScadAccordoA != null) {
	    Calendar c = Calendar.getInstance();
	    c.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
	    dtScadAccordoDa = c.getTime();
	    getForm().getFiltriEntiConvenzionati().getDt_scad_accordo_da()
		    .setValue(formato.format(dtScadAccordoDa));
	}

	if (dtIniValDa != null && dtIniValA == null) {
	    Calendar c = Calendar.getInstance();
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);
	    dtIniValA = c.getTime();
	    getForm().getFiltriEntiConvenzionati().getDt_ini_val_a()
		    .setValue(formato.format(dtIniValA));
	} else if (dtIniValDa == null && dtIniValA != null) {
	    Calendar c = Calendar.getInstance();
	    c.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
	    dtIniValDa = c.getTime();
	    getForm().getFiltriEntiConvenzionati().getDt_ini_val_da()
		    .setValue(formato.format(dtIniValDa));
	}

	if (dtCessazioneDa != null && dtCessazioneA == null) {
	    Calendar c = Calendar.getInstance();
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);
	    dtCessazioneA = c.getTime();
	    getForm().getFiltriEntiConvenzionati().getDt_cessazione_a()
		    .setValue(formato.format(dtCessazioneA));
	} else if (dtCessazioneDa == null && dtCessazioneA != null) {
	    Calendar c = Calendar.getInstance();
	    c.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
	    dtCessazioneDa = c.getTime();
	    getForm().getFiltriEntiConvenzionati().getDt_cessazione_da()
		    .setValue(formato.format(dtCessazioneDa));
	}

    }

    @Override
    public void pulisciRicercaEntiConvenzionati() throws EMFError {
	try {
	    initFiltriRicercaEntiConvenzionati();
	} catch (ParerUserError ex) {
	    getMessageBox()
		    .addError("Errore durante il reset dei filtri di ricerca enti convenzionati");
	}
	forwardToPublisher(Application.Publisher.RICERCA_ENTI_CONVENZIONATI);
    }

    @Override
    public void inserisciEnteConvenzionatoWizard() throws EMFError {
	try {
	    // Carico qui i dati e non nel pass1onEnter perchè se li caricassi là, nel caso in cui
	    // tornassi dal passo2,
	    // scazzerei tutto...
	    getSession().setAttribute("navTableEntiConvenz",
		    getForm().getListaEntiConvenzionati().getName());
	    getForm().getEnteConvenzionatoWizardDetail().clear();
	    initEnteConvenzionatoWizardDetail();
	    DateFormat formato = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH);
	    getForm().getEnteConvenzionatoWizardDetail().getDt_ini_val()
		    .setValue(formato.format(Calendar.getInstance().getTime()));
	    Calendar c = Calendar.getInstance();
	    c.set(2444, Calendar.DECEMBER, 31, 0, 0, 0);
	    getForm().getEnteConvenzionatoWizardDetail().getDt_cessazione()
		    .setValue(formato.format(c.getTime()));
	    getForm().getEnteConvenzionatoWizardDetail().getCd_nazione_sede_legale().setValue("IT");
	    getForm().getEnteConvenzionatoWizardDetail().getTi_ente_convenz()
		    .setValue("PRODUTTORE");

	    // Parametri
	    Object[] parametriObj = entiConvenzionatiEjb.getIamParamApplicEnte(null, null);
	    getForm().getParametriAmministrazioneList()
		    .setTable((IamParamApplicTableBean) parametriObj[0]);
	    getForm().getParametriGestioneList()
		    .setTable((IamParamApplicTableBean) parametriObj[1]);
	    getForm().getParametriConservazioneList()
		    .setTable((IamParamApplicTableBean) parametriObj[2]);
	    getForm().getParametriAmministrazioneList().setHideDeleteButton(true);
	    getForm().getParametriConservazioneList().setHideDeleteButton(true);
	    getForm().getParametriGestioneList().setHideDeleteButton(true);
	    getForm().getParametriAmministrazioneList().getDs_valore_param_applic_ente_amm()
		    .setEditMode();
	    getForm().getParametriConservazioneList().getDs_valore_param_applic_ente_cons()
		    .setEditMode();
	    getForm().getParametriGestioneList().getDs_valore_param_applic_ente_gest()
		    .setEditMode();
	    //
	    getForm().getEnteConvenzionatoWizardDetail().setEditMode();
	    getForm().getEnteConvenzionatoWizardDetail().setStatus(Status.insert);
	    getForm().getListaEntiConvenzionati().setStatus(Status.insert);
	    //
	    getForm().getInserimentoEnteConvenzionatoWizard().reset();
	    forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO_WIZARD);
	} catch (ParerUserError ex) {
	    getMessageBox().addError(
		    "Errore durante il caricamento del wizard di creazione enti convenzionati");
	}
    }

    @Override
    public String getDefaultInserimentoEnteConvenzionatoWizardPublisher() throws EMFError {
	return Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO_WIZARD;
    }

    /**
     * Ingresso primo step del wizard: - Non ho niente da fare
     *
     * @throws EMFError errore generico
     */
    @Override
    public void inserimentoEnteConvenzionatoWizardStep1OnEnter() throws EMFError {
	/*
	 * empty
	 */
    }

    /**
     * Uscita primo step del wizard: - Eseguo i controlli per rispondere se proseguire
     *
     * @return true/false, per capire se proseguire dopo i controlli
     *
     * @throws EMFError errore generico
     */
    @Override
    public boolean inserimentoEnteConvenzionatoWizardStep1OnExit() throws EMFError {
	boolean result = false;

	getForm().getParametriAmministrazioneList().post(getRequest());
	getForm().getParametriConservazioneList().post(getRequest());
	getForm().getParametriGestioneList().post(getRequest());
	if (getForm().getEnteConvenzionatoWizardDetail().postAndValidate(getRequest(),
		getMessageBox())) {

	    // Controllo esistenza denominazione
	    if (entiConvenzionatiEjb.checkEsistenzaEntePerDenominazione(
		    getForm().getEnteConvenzionatoWizardDetail().getNm_ente_siam().parse())) {
		OrgEnteSiamRowBean enteSiam = entiConvenzionatiEjb.getOrgEnteSiamByName(
			getForm().getEnteConvenzionatoWizardDetail().getNm_ente_siam().parse());
		if (enteSiam != null) {
		    if (enteSiam.getTiEnte().equals("CONVENZIONATO")) {
			getMessageBox()
				.addError("Ente gi\u00E0 censito nel sistema come convenzionato");
		    } else {
			getMessageBox().addError(
				"Ente gi\u00E0 censito nel sistema come non convenzionato");
		    }
		}
	    }

	    // Controllo esistenza codice e tipo codice ente
	    if (entiConvenzionatiEjb.checkEsistenzaEntePerCodiceTipoCodice(
		    getForm().getEnteConvenzionatoWizardDetail().getCd_ente_convenz().parse(),
		    getForm().getEnteConvenzionatoWizardDetail().getTi_cd_ente_convenz().parse())) {
		getMessageBox().addError(
			"Ente gi\u00E0 censito nel sistema come convenzionato " + "con codice "
				+ getForm().getEnteConvenzionatoWizardDetail().getCd_ente_convenz()
					.parse()
				+ " e tipo codice " + getForm().getEnteConvenzionatoWizardDetail()
					.getTi_cd_ente_convenz().parse());
	    }

	    IamParamApplicTableBean parametriAmministrazione = (IamParamApplicTableBean) getForm()
		    .getParametriAmministrazioneList().getTable();
	    IamParamApplicTableBean parametriConservazione = (IamParamApplicTableBean) getForm()
		    .getParametriConservazioneList().getTable();
	    IamParamApplicTableBean parametriGestione = (IamParamApplicTableBean) getForm()
		    .getParametriGestioneList().getTable();

	    // Controllo valori possibili su ente
	    for (IamParamApplicRowBean paramApplicRowBean : parametriAmministrazione) {
		if (paramApplicRowBean.getString("ds_lista_valori_ammessi") != null
			&& !paramApplicRowBean.getString("ds_lista_valori_ammessi").equals("")) {
		    if (paramApplicRowBean.getString("ds_valore_param_applic_ente_amm") != null
			    && !paramApplicRowBean.getString("ds_valore_param_applic_ente_amm")
				    .equals("")) {
			if (!inValoriPossibili(
				paramApplicRowBean.getString("ds_valore_param_applic_ente_amm"),
				paramApplicRowBean.getString("ds_lista_valori_ammessi"))) {
			    getMessageBox().addError(
				    "Il valore del parametro non è compreso tra i valori ammessi sul parametro");
			}
		    }
		}
	    }

	    for (IamParamApplicRowBean paramApplicRowBean : parametriConservazione) {
		if (paramApplicRowBean.getString("ds_lista_valori_ammessi") != null
			&& !paramApplicRowBean.getString("ds_lista_valori_ammessi").equals("")) {
		    if (paramApplicRowBean.getString("ds_valore_param_applic_ente_cons") != null
			    && !paramApplicRowBean.getString("ds_valore_param_applic_ente_cons")
				    .equals("")) {
			if (!inValoriPossibili(
				paramApplicRowBean.getString("ds_valore_param_applic_ente_cons"),
				paramApplicRowBean.getString("ds_lista_valori_ammessi"))) {
			    getMessageBox().addError(
				    "Il valore del parametro non è compreso tra i valori ammessi sul parametro");
			}
		    }
		}
	    }

	    for (IamParamApplicRowBean paramApplicRowBean : parametriGestione) {
		if (paramApplicRowBean.getString("ds_lista_valori_ammessi") != null
			&& !paramApplicRowBean.getString("ds_lista_valori_ammessi").equals("")) {
		    if (paramApplicRowBean.getString("ds_valore_param_applic_ente_gest") != null
			    && !paramApplicRowBean.getString("ds_valore_param_applic_ente_gest")
				    .equals("")) {
			if (!inValoriPossibili(
				paramApplicRowBean.getString("ds_valore_param_applic_ente_gest"),
				paramApplicRowBean.getString("ds_lista_valori_ammessi"))) {
			    getMessageBox().addError(
				    "Il valore del parametro non è compreso tra i valori ammessi sul parametro");
			}
		    }
		}
	    }

	    if (getForm().getEnteConvenzionatoWizardDetail().getDt_rich_modulo_info()
		    .parse() != null
		    && getForm().getEnteConvenzionatoWizardDetail().getDt_rich_modulo_info().parse()
			    .after(new Date())) {
		getMessageBox().addError(
			"Attenzione: data di richiesta del modulo di informazioni non pu\u00F2 essere una data futura");
	    }

	    try {
		if (!getMessageBox().hasError()) {
		    getForm().getAccordoWizardDetail().clear();
		    initAccordoWizardDetail();

		    BigDecimal idAmbienteEnteConvenz = getForm().getEnteConvenzionatoWizardDetail()
			    .getId_ambiente_ente_convenz().parse();
		    BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoWizardDetail()
			    .getId_ente_siam().parse();
		    //
		    OrgAmbienteEnteConvenzRowBean orgAmbienteEnteConvenzByEnteConvenz = entiConvenzionatiEjb
			    .getOrgAmbienteEnteConvenzRowBean(idAmbienteEnteConvenz);
		    getForm().getEnteConvenzionatoWizardDetail().getNm_ambiente_ente_convenz()
			    .setValue(
				    orgAmbienteEnteConvenzByEnteConvenz.getNmAmbienteEnteConvenz());
		    getForm().getEnteConvenzionatoWizardDetail().getNm_ambiente_ente_convenz()
			    .setViewMode();
		    //
		    OrgEnteSiamRowBean enteConvenzGestore = entiConvenzionatiEjb
			    .getOrgEnteConvenzGestore(idAmbienteEnteConvenz);
		    if (enteConvenzGestore != null) {
			getForm().getAccordoWizardDetail().getId_ente_convenz_gestore()
				.setValue(enteConvenzGestore.getIdEnteSiam().toPlainString());
			getForm().getAccordoWizardDetail().getNm_ente_convenz_gestore()
				.setValue(enteConvenzGestore.getNmEnteSiam());
		    }
		    //
		    OrgEnteSiamRowBean enteConvenzConserv = entiConvenzionatiEjb
			    .getOrgEnteConvenzConserv(idAmbienteEnteConvenz);
		    if (enteConvenzConserv != null) {
			getForm().getAccordoWizardDetail().getId_ente_convenz_conserv()
				.setValue(enteConvenzConserv.getIdEnteSiam().toPlainString());
			getForm().getAccordoWizardDetail().getNm_ente_convenz_conserv()
				.setValue(enteConvenzConserv.getNmEnteSiam());

			OrgAccordoEnteTableBean accordoEnteTableBean = entiConvenzionatiEjb
				.getOrgAccordoEntePiuRecenteTableBean(
					enteConvenzConserv.getIdEnteSiam());
			if (!accordoEnteTableBean.isEmpty()) {
			    getForm().getAccordoWizardDetail().getId_ente_convenz_amministratore()
				    .setValue(accordoEnteTableBean.getRow(0)
					    .getIdEnteConvenzAmministratore().toPlainString());
			    getForm().getAccordoWizardDetail().getNm_ente_convenz_amministratore()
				    .setValue(accordoEnteTableBean.getRow(0)
					    .getString("nm_ente_convenz_amministratore"));
			}
		    }

		    // Riporto i dati presi da ente convenzionato
		    getForm().getAccordoWizardDetail().getNm_ente_siam().setValue(
			    getForm().getEnteConvenzionatoWizardDetail().getNm_ente_siam().parse());

		    getForm().getAccordoWizardDetail().getTi_scopo_accordo()
			    .setValue("CONSERVAZIONE");

		    getForm().getAccordoWizardDetail().setEditMode();

		    // Ente gestore/Ente conservatore/Ente amministratore in sola visualizzazione
		    getForm().getAccordoWizardDetail().getNm_ente_siam().setViewMode();
		    getForm().getAccordoWizardDetail().getNm_ente_convenz_gestore().setViewMode();
		    getForm().getAccordoWizardDetail().getNm_ente_convenz_conserv().setViewMode();
		    getForm().getAccordoWizardDetail().getNm_ente_convenz_amministratore()
			    .setViewMode();

		    // Ambiente/Ente/Struttura in sola visualizzazione
		    getForm().getAccordoWizardDetail().getNm_ente().setViewMode();
		    getForm().getAccordoWizardDetail().getNm_strut().setViewMode();

		    getForm().getAccordoWizardDetail().setStatus(Status.insert);
		    getForm().getAccordiList().setStatus(Status.insert);

		    result = true;
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
	    }
	}

	return result;
    }

    /**
     * Ingresso secondo step del wizard: - Nascondo alcuni campi
     *
     * @throws EMFError errore generico
     */
    @Override
    public void inserimentoEnteConvenzionatoWizardStep2OnEnter() throws EMFError {
	getForm().getAccordoWizardDetail().getId_ente_convenz_gestore().setHidden(true);
	getForm().getAccordoWizardDetail().getId_ente_convenz_conserv().setHidden(true);
	getForm().getAccordoWizardDetail().getId_ente_convenz_amministratore().setHidden(true);
	getForm().getAccordoWizardDetail().getId_tariffario().setDecodeMap(new DecodeMap());
    }

    /**
     * Uscita secondo step del wizard: - Eseguo i controlli di validazione prima di procedere al
     * salvataggio
     *
     * @return true/false, per capire se proseguire dopo i controlli
     *
     * @throws EMFError errore generico
     */
    @Override
    public boolean inserimentoEnteConvenzionatoWizardStep2OnExit() throws EMFError {
	boolean result = false;

	// Se il flag è "checked" ripristino i campi che sono stati messi in stato "readonly" dal
	// trigger.
	// I suddetti campi devono essere gestiti dal framework come editabili e non in sola
	// lettura.
	String flAccNonApprov = (String) getRequest()
		.getParameter(getForm().getAccordoWizardDetail().getFl_acc_non_approv().getName());
	if (flAccNonApprov != null) { // checked
	    // SECTION: ID Accordo
	    getForm().getAccordoWizardDetail().getCd_registro_repertorio().setReadonly(false);
	    getForm().getAccordoWizardDetail().getAa_repertorio().setReadonly(false);
	    getForm().getAccordoWizardDetail().getCd_key_repertorio().setReadonly(false);
	    getForm().getAccordoWizardDetail().getDt_reg_accordo().setReadonly(false);
	    // end SECTION

	    // SECTION: Informazioni sull'accordo
	    getForm().getAccordoWizardDetail().getDt_atto_accordo().setReadonly(false);
	    getForm().getAccordoWizardDetail().getDt_dec_accordo().setReadonly(false);
	    getForm().getAccordoWizardDetail().getDt_fine_valid_accordo().setReadonly(false);
	    getForm().getAccordoWizardDetail().getDt_scad_accordo().setReadonly(false);
	    getForm().getAccordoWizardDetail().getDs_atto_accordo().setReadonly(false);
	    // end SECTION
	}

	if (getForm().getAccordoWizardDetail().postAndValidate(getRequest(), getMessageBox())) {
	    // Ricavo l'ambiente di appartenenza dell'ente
	    BigDecimal idAmbienteEnteConvenz = getForm().getEnteConvenzionatoWizardDetail()
		    .getId_ambiente_ente_convenz().parse();
	    // Ricavo la data di cessazione dell'ente convenzionato
	    Date dtCessazione = getForm().getEnteConvenzionatoWizardDetail().getDt_cessazione()
		    .parse();
	    // Ricavo la data di decorrenza dell'accordo
	    Date dtDecAccordoInfo = getForm().getAccordoWizardDetail().getDt_dec_accordo_info()
		    .parse();
	    // Ricavo la data di scadenza dell'accordo
	    Date dtScadAccordo = getForm().getAccordoWizardDetail().getDt_scad_accordo().parse();
	    // Ricavo la data di inizio validità dell'accordo
	    Date dtDecAccordo = getForm().getAccordoWizardDetail().getDt_dec_accordo().parse();
	    // Ricavo la data di fine validità dell'accordo
	    Date dtFineValidAccordo = getForm().getAccordoWizardDetail().getDt_fine_valid_accordo()
		    .parse();
	    // Ricavo la classe di appartenenza dell'ente
	    BigDecimal idClasseEnteConvenz = getForm().getAccordoWizardDetail()
		    .getId_classe_ente_convenz().parse();
	    // Ricavo il tariffario
	    BigDecimal idTariffario = getForm().getAccordoWizardDetail().getId_tariffario().parse();
	    // Numero abitanti
	    BigDecimal niAbitanti = getForm().getAccordoWizardDetail().getNi_abitanti().parse();
	    // Ricavo la data di richiesta del modulo di informazioni
	    // Date dtRichModuloInfo =
	    // getForm().getAccordoWizardDetail().getDt_rich_modulo_info().parse();
	    // Ricavo la data di approvazione accordo
	    Date dtAttoAccordo = getForm().getAccordoWizardDetail().getDt_atto_accordo().parse();
	    // Ricavo l'atto di approvazione accordo
	    String dsAttoAccordo = getForm().getAccordoWizardDetail().getDs_atto_accordo().parse();
	    // Ricavo il registro
	    String cdRegistroRepertorio = getForm().getAccordoWizardDetail()
		    .getCd_registro_repertorio().parse();
	    // Ricavo l'anno
	    BigDecimal anno = getForm().getAccordoWizardDetail().getAa_repertorio().parse();
	    // Ricavo il numero
	    String numero = getForm().getAccordoWizardDetail().getCd_key_repertorio().parse();
	    // Ricavo la data di registrazione accordo
	    Date dtRegAccordo = getForm().getAccordoWizardDetail().getDt_reg_accordo().parse();

	    getForm().getTipoServizioAccordoList().post(getRequest());

	    if (getForm().getAccordoWizardDetail().getId_ente_convenz_gestore().parse() == null) {
		getMessageBox().addError(
			"Sull'ambiente dell'ente convenzionato non \u00E8 definito il gestore.");
	    }

	    if (getForm().getAccordoWizardDetail().getId_ente_convenz_conserv().parse() == null) {
		getMessageBox().addError(
			"Sull'ambiente dell'ente convenzionato non \u00E8 definito il conservatore.");
	    }

	    if (idTariffario != null && idClasseEnteConvenz != null) {
		if (entiConvenzionatiEjb.existsTariffaPerTariffarioClasseEnte(idTariffario,
			idClasseEnteConvenz)) {
		    if (niAbitanti == null) {
			getMessageBox().addError("Il campo numero abitanti \u00E8 obbligatorio");
		    }
		}
	    }

	    // Controllo lato online sulle date
	    if (dtScadAccordo != null && dtDecAccordoInfo != null
		    && dtScadAccordo.before(dtDecAccordoInfo)) {
		getMessageBox().addError(
			"Attenzione: data di scadenza accordo inferiore alla data di decorrenza");
	    }
	    if (dtFineValidAccordo != null && dtDecAccordo != null
		    && dtFineValidAccordo.before(dtDecAccordo)) {
		getMessageBox().addError(
			"Attenzione: data di fine validit\u00E0 accordo inferiore alla data di inizio validità ");
	    }
	    if (dtDecAccordo != null && dtCessazione != null && dtDecAccordo.after(dtCessazione)) {
		getMessageBox().addError(
			"Attenzione: la data di inizio validità non pu\u00F2 essere superiore alla data di fine validit\u00E0 dell\u0027ente convenzionato");
	    }
	    if (dtFineValidAccordo != null && dtCessazione != null
		    && dtFineValidAccordo.after(dtCessazione)) {
		getMessageBox().addError(
			"Attenzione: la data di fine validit\u00E0 dell'accordo non pu\u00F2 essere superiore alla data di fine validit\u00E0 dell\u0027ente convenzionato");
	    }
	    if (dtRegAccordo != null) {
		if (cdRegistroRepertorio == null || anno == null || numero == null) {
		    getMessageBox().addError(
			    "Attenzione: in caso di registrazione dell\u0027accordo \u00E8 obbligatorio indicare tutti i campi presenti nella sezione ID Accordo");
		}
		if (dtScadAccordo == null) {
		    getMessageBox().addError(
			    "Attenzione: in caso di registrazione dell\u0027accordo \u00E8 obbligatorio indicare la data di scadenza");
		}
	    } else {
		if (dtAttoAccordo == null || dsAttoAccordo == null) {
		    getMessageBox().addError(
			    "Attenzione: data di approvazione e atto di approvazione sono obbligatori se l\u0027ente \u00E8 creato senza accordo firmato");
		}
		if ((dtDecAccordo != null && !DateUtils.truncate(dtDecAccordo, Calendar.DATE)
			.equals(DateUtil.MAX_DATE))
			|| (dtFineValidAccordo != null
				&& !DateUtils.truncate(dtFineValidAccordo, Calendar.DATE)
					.equals(DateUtil.MAX_DATE))) {
		    getMessageBox().addError(
			    "Attenzione: data di inizio validità e data di fine validit\u00E0 devono essere valorizzati con 31/12/2444 se l\u0027ente \u00E8 creato senza accordo firmato");
		}
	    }

	    if (dtDecAccordo != null && dtFineValidAccordo != null) {
		// Controllo sovrapposizione periodi
		if (!entiConvenzionatiEjb.checkEsistenzaAmbienteValidoDtDecDtFineValid(
			idAmbienteEnteConvenz, dtDecAccordo, dtFineValidAccordo, null)) {
		    getMessageBox().addError(
			    "Nel periodo di validit\u00E0 dell'accordo l'ambiente dell'ente convenzionato non \u00E8 valido");
		}
	    }

	    if (!getMessageBox().hasError()) {
		result = true;
	    }
	}

	return result;
    }

    @Override
    public boolean inserimentoEnteConvenzionatoWizardOnSave() throws EMFError {
	boolean result = true;

	Long idEnteConvenz = null;
	try {
	    IamParamApplicTableBean parametriAmministrazione = (IamParamApplicTableBean) getForm()
		    .getParametriAmministrazioneList().getTable();
	    IamParamApplicTableBean parametriConservazione = (IamParamApplicTableBean) getForm()
		    .getParametriConservazioneList().getTable();
	    IamParamApplicTableBean parametriGestione = (IamParamApplicTableBean) getForm()
		    .getParametriGestioneList().getTable();

	    // Salva l'ente convenzionato
	    EnteConvenzionatoBean creazioneBean = new EnteConvenzionatoBean(
		    getForm().getEnteConvenzionatoWizardDetail());

	    Date dtRegAccordo = getForm().getAccordoWizardDetail().getDt_reg_accordo().parse();
	    Date dtDecAccordo = getForm().getAccordoWizardDetail().getDt_dec_accordo().parse();
	    Date dtFineValidAccordo = getForm().getAccordoWizardDetail().getDt_fine_valid_accordo()
		    .parse();
	    // Se la data di registrazione è valorizzata e sono definite sia data di inizio validità
	    // che data di
	    // fine validità dell'accordo
	    if (dtRegAccordo != null && dtDecAccordo != null && dtFineValidAccordo != null) {
		// Richiamo il controllo inclusione dato figlio passando come entità figlio =
		// accordo e
		// entità padre = ente gestore dell'accordo.
		BigDecimal idEnteConvenzGestore = getForm().getAccordoWizardDetail()
			.getId_ente_convenz_gestore().parse();
		if (idEnteConvenzGestore != null) {
		    // Per l\u0027ente gestore, ricavo i relativi accordi, mettendoli in ordine di
		    // data e verificando
		    // che l\u0027accordo dell\u0027ente
		    // sia incluso nella sequenza di validità degli accordi del gestore
		    entiConvenzionatiEjb.checkInclusioneAccordoFiglio(idEnteConvenzGestore,
			    dtDecAccordo, dtFineValidAccordo, creazioneBean.getNm_ente_convenz());

		}
		// Richiamo il controllo inclusione dato figlio passando come entità figlio =
		// accordo e
		// entità padre = ente conservatore dell'accordo.
		BigDecimal idEnteConvenzConserv = getForm().getAccordoWizardDetail()
			.getId_ente_convenz_conserv().parse();
		if (idEnteConvenzConserv != null) {

		    // Per l\u0027ente conservatore, ricavo i relativi accordi, mettendoli in ordine
		    // di data e
		    // verificando che l\u0027accordo dell\u0027ente
		    // sia incluso nella sequenza di validità degli accordi del conservatore
		    entiConvenzionatiEjb.checkInclusioneAccordoFiglio(idEnteConvenzConserv,
			    dtDecAccordo, dtFineValidAccordo, creazioneBean.getNm_ente_convenz());

		}
	    }

	    // getForm().getTipoServizioAccordoList().post(getRequest());
	    // for (BaseRow rb : (BaseTable) getForm().getTipoServizioAccordoList().getTable()) {
	    // checkImportoTariffa(rb.getString("im_tariffa_accordo"));aa
	    // }
	    // if (!getMessageBox().hasError()) {

	    /*
	     * Codice aggiuntivo per il logging...
	     */
	    LogParam param = SpagoliteLogUtil.getLogParam(
		    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
		    SpagoliteLogUtil.getPageName(this));
	    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
	    if (getForm().getListaEntiConvenzionati().getStatus().equals(Status.insert)) {
		param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
		if (getForm().getListaEntiConvenzionati().getStatus().equals(Status.insert)) {
		    param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());

		    checkAndSaveEnteConvenzionato(param, creazioneBean, parametriAmministrazione,
			    parametriConservazione, parametriGestione,
			    getForm().getAccordoWizardDetail(),
			    getForm().getTipoServizioAccordoList());
		}

		if (getSession().getAttribute(
			"attributiSalvataggioEnteConvenzionatoControlloDate") == null) {
		    getSession().setAttribute("navTableEntiConvenz",
			    getForm().getEnteConvenzionatoWizardDetail().getName());
		    forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO);
		} else {
		    result = false;
		}
	    }
	} catch (ParerUserError ex) {
	    result = false;
	    getMessageBox().addError(ex.getDescription());
	    // for (BaseRow rb : (BaseTable) getForm().getTipoServizioAccordoList().getTable()) {
	    // rb.setBigDecimal("im_tariffa_accordo", (BigDecimal)
	    // rb.getOldObject("im_tariffa_accordo"));
	    // }
	}

	return result;
    }

    private void checkAndSaveEnteConvenzionato(LogParam param, EnteConvenzionatoBean creazioneBean,
	    IamParamApplicTableBean parametriAmministrazione,
	    IamParamApplicTableBean parametriConservazione,
	    IamParamApplicTableBean parametriGestione, AccordoWizardDetail accordoWizardDetail,
	    TipoServizioAccordoList tipoServizioAccordoList) throws ParerUserError, EMFError {
	getForm().getAccordoWizardDetail().post(getRequest());

	Date dtDecAccordo = getForm().getAccordoWizardDetail().getDt_dec_accordo().parse();
	Date dtIniValEnte = creazioneBean.getDt_ini_val();

	if (dtDecAccordo.before(dtIniValEnte)) {
	    // Dati in sessione
	    getRequest().setAttribute("customControlloDateMessageBox", true);
	    Object[] attributiSalvataggioAccordoControlloDate = new Object[7];
	    attributiSalvataggioAccordoControlloDate[0] = param;
	    attributiSalvataggioAccordoControlloDate[1] = creazioneBean;
	    attributiSalvataggioAccordoControlloDate[2] = parametriAmministrazione;
	    attributiSalvataggioAccordoControlloDate[3] = parametriConservazione;
	    attributiSalvataggioAccordoControlloDate[4] = parametriGestione;
	    attributiSalvataggioAccordoControlloDate[5] = accordoWizardDetail;
	    attributiSalvataggioAccordoControlloDate[6] = tipoServizioAccordoList;
	    getSession().setAttribute("attributiSalvataggioEnteConvenzionatoControlloDate",
		    attributiSalvataggioAccordoControlloDate);
	} else {
	    eseguiSalvataggioEnteConvenzionato(param, creazioneBean, parametriAmministrazione,
		    parametriConservazione, parametriGestione, accordoWizardDetail,
		    tipoServizioAccordoList);
	}

    }

    public void eseguiSalvataggioEnteConvenzionato(LogParam param,
	    EnteConvenzionatoBean creazioneBean, IamParamApplicTableBean parametriAmministrazione,
	    IamParamApplicTableBean parametriConservazione,
	    IamParamApplicTableBean parametriGestione, AccordoWizardDetail accordoWizardDetail,
	    TipoServizioAccordoList tipoServizioAccordoList) throws ParerUserError, EMFError {

	try {
	    Long idEnteConvenz = entiConvenzionatiEjb.saveEnteConvenzionatoAccordoWizard(param,
		    creazioneBean, parametriAmministrazione, parametriConservazione,
		    parametriGestione, getForm().getAccordoWizardDetail(),
		    getForm().getTipoServizioAccordoList());
	    if (idEnteConvenz != null) {
		getForm().getEnteConvenzionatoWizardDetail().getId_ente_siam()
			.setValue(idEnteConvenz.toString());
	    }
	    getMessageBox().addInfo("Ente convenzionato ed accordo salvati con successo");

	    OrgEnteSiamRowBean row = new OrgEnteSiamRowBean();
	    getForm().getEnteConvenzionatoWizardDetail().copyToBean(row);
	    row.setBigDecimal("id_ente_convenz", row.getIdEnteSiam());
	    row.setString("nm_ente_convenz", row.getNmEnteSiam());
	    if (getForm().getListaEntiConvenzionati().getTable() != null) {
		getForm().getListaEntiConvenzionati().getTable().last();
		getForm().getListaEntiConvenzionati().getTable().add(row);
	    } else {
		getForm().getListaEntiConvenzionati().setTable(new OrgEnteSiamTableBean());
		getForm().getListaEntiConvenzionati().add(row);
		getForm().getListaEntiConvenzionati().getTable().setPageSize(10);
	    }

	    getMessageBox().setViewMode(ViewMode.plain);
	    initEnteConvenzionatoDetail();
	    // String br = "";
	    loadDettaglioEnteConvenzionato(BigDecimal.valueOf(idEnteConvenz));
	    postLoad();

	    getSession().setAttribute("navTableEntiConvenz",
		    getForm().getEnteConvenzionatoWizardDetail().getName());

	    // "Rimuovo" dalla history il publisher relativo a DETTAGLIO_ENTE_CONVENZIONATO_WIZARD
	    // ed eseguo forward a
	    // DETTAGLIO_ENTE_CONVENZIONATO
	    forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO);
	    SessionManager.removeLastExecutionHistory(getSession());
	} catch (ParerUserError ex) {
	    // throw new ParerUserError(ex.getDescription());
	    getMessageBox().addError(ex.getDescription());
	    forwardToPublisher(getLastPublisher());
	} catch (Exception ex) {
	    logger.error(
		    "Errore imprevisto durante il salvataggio dell'ente convenzionato e dell'accordo : "
			    + ExceptionUtils.getRootCauseMessage(ex),
		    ex);
	    throw new ParerUserError(
		    "Eccezione imprevista durante il salvataggio dell'ente convenzionato e dell'accordo");
	}
    }

    public void confermaSalvataggioEnteConvenzionatoWizard() throws ParerUserError, EMFError {
	try {
	    if (getSession()
		    .getAttribute("attributiSalvataggioEnteConvenzionatoControlloDate") != null) {
		Object[] attributiSalvataggioEnteConvenzionatoControlloDate = (Object[]) getSession()
			.getAttribute("attributiSalvataggioEnteConvenzionatoControlloDate");
		LogParam param = (LogParam) attributiSalvataggioEnteConvenzionatoControlloDate[0];
		EnteConvenzionatoBean creazioneBean = (EnteConvenzionatoBean) attributiSalvataggioEnteConvenzionatoControlloDate[1];
		IamParamApplicTableBean parametriAmministrazione = (IamParamApplicTableBean) attributiSalvataggioEnteConvenzionatoControlloDate[2];
		IamParamApplicTableBean parametriConservazione = (IamParamApplicTableBean) attributiSalvataggioEnteConvenzionatoControlloDate[3];
		IamParamApplicTableBean parametriGestione = (IamParamApplicTableBean) attributiSalvataggioEnteConvenzionatoControlloDate[4];
		AccordoWizardDetail accordoWizardDetail = (AccordoWizardDetail) attributiSalvataggioEnteConvenzionatoControlloDate[5];
		TipoServizioAccordoList tipoServizioAccordoList = (TipoServizioAccordoList) attributiSalvataggioEnteConvenzionatoControlloDate[6];
		eseguiSalvataggioEnteConvenzionato(param, creazioneBean, parametriAmministrazione,
			parametriConservazione, parametriGestione, accordoWizardDetail,
			tipoServizioAccordoList);

	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	    forwardToPublisher(getLastPublisher());
	}
    }

    public void annullaSalvataggioEnteConvenzionatoWizard() throws EMFError, ParerUserError {
	getSession().removeAttribute("attributiSalvataggioEnteConvenzionatoControlloDate");
	forwardToPublisher(Application.Publisher.RICERCA_ENTI_CONVENZIONATI);
    }

    @Override
    public void inserimentoEnteConvenzionatoWizardOnCancel() throws EMFError {
	goBackTo(Application.Publisher.RICERCA_ENTI_CONVENZIONATI);
    }

    @Override
    public JSONObject triggerFiltriEntiConvenzionatiCd_ambito_territ_regioneOnTrigger()
	    throws EMFError {
	getForm().getFiltriEntiConvenzionati().post(getRequest());
	List<BigDecimal> idAmbitoTerrit = getForm().getFiltriEntiConvenzionati()
		.getCd_ambito_territ_regione().parse();
	try {
	    if (idAmbitoTerrit != null && !idAmbitoTerrit.isEmpty()) {
		getForm().getFiltriEntiConvenzionati().getCd_ambito_territ_provincia()
			.setDecodeMap(DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getOrgAmbitoTerritTableBean(idAmbitoTerrit),
				"id_ambito_territ", "cd_ambito_territ"));
		getForm().getFiltriEntiConvenzionati().getCd_ambito_territ_forma_associata()
			.setDecodeMap(new DecodeMap());
	    } else {
		getForm().getFiltriEntiConvenzionati().getCd_ambito_territ_provincia()
			.setDecodeMap(new DecodeMap());
		getForm().getFiltriEntiConvenzionati().getCd_ambito_territ_forma_associata()
			.setDecodeMap(new DecodeMap());
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
	return getForm().getFiltriEntiConvenzionati().asJSON();
    }

    @Override
    public JSONObject triggerFiltriEntiConvenzionatiCd_ambito_territ_provinciaOnTrigger()
	    throws EMFError {
	getForm().getFiltriEntiConvenzionati().post(getRequest());
	List<BigDecimal> idAmbitoTerrit = getForm().getFiltriEntiConvenzionati()
		.getCd_ambito_territ_provincia().parse();
	try {
	    if (idAmbitoTerrit != null && !idAmbitoTerrit.isEmpty()) {
		getForm().getFiltriEntiConvenzionati().getCd_ambito_territ_forma_associata()
			.setDecodeMap(DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getOrgAmbitoTerritTableBean(idAmbitoTerrit),
				"id_ambito_territ", "cd_ambito_territ"));
	    } else {
		getForm().getFiltriEntiConvenzionati().getCd_ambito_territ_forma_associata()
			.setDecodeMap(new DecodeMap());
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
	return getForm().getFiltriEntiConvenzionati().asJSON();
    }

    // </editor-fold>
    private void initEnteConvenzionatoWizardDetail() throws ParerUserError {
	getForm().getEnteConvenzionatoWizardDetail().getId_ambiente_ente_convenz()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getUsrVAbilAmbEnteXEnteValidTableBean(
				new BigDecimal(getUser().getIdUtente())),
			"id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));
	getForm().getEnteConvenzionatoWizardDetail().getTi_cd_ente_convenz()
		.setDecodeMap(ComboGetter.getMappaTiStatoRichGestUser());
	getForm().getEnteConvenzionatoWizardDetail().getId_prov_sede_legale()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgAmbitoTerritTableBean(
				ConstOrgAmbitoTerrit.TiAmbitoTerrit.PROVINCIA.toString()),
			"id_ambito_territ", "cd_ambito_territ"));
	getForm().getEnteConvenzionatoWizardDetail().getCd_ambito_territ_regione()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgAmbitoTerritTableBean(
				ConstOrgAmbitoTerrit.TiAmbitoTerrit.REGIONE_STATO.toString()),
			"id_ambito_territ", "cd_ambito_territ"));
	getForm().getEnteConvenzionatoWizardDetail().getCd_ambito_territ_provincia()
		.setDecodeMap(new DecodeMap());
	getForm().getEnteConvenzionatoWizardDetail().getCd_ambito_territ_forma_associata()
		.setDecodeMap(new DecodeMap());
	getForm().getEnteConvenzionatoWizardDetail().getDs_categ_ente()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgCategEnteTableBean(), "id_categ_ente",
			"ds_categ_ente"));
	getForm().getEnteConvenzionatoWizardDetail().getId_cd_iva().setDecodeMap(DecodeMap.Factory
		.newInstance(entiConvenzionatiEjb.getOrgCdIvaTableBean(), "id_cd_iva", "cd_iva"));

	getForm().getEnteConvenzionatoWizardDetail().getTi_mod_pagam()
		.setDecodeMap(ComboGetter.getMappaTiModParam());
    }

    // <editor-fold defaultstate="collapsed" desc="Gestione dettaglio ente convenzionato">
    private void initEnteConvenzionatoDetail() throws ParerUserError {
	getForm().getEnteConvenzionatoDetail().getId_ambiente_ente_convenz()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getUsrVAbilAmbEnteXEnteTableBean(
				new BigDecimal(getUser().getIdUtente())),
			"id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));
	getForm().getEnteConvenzionatoDetail().getId_ambiente_ente_convenz_nuovo()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getUsrVAbilAmbEnteXEnteValidTableBean(
				new BigDecimal(getUser().getIdUtente())),
			"id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));
	getForm().getEnteConvenzionatoDetail().getTi_cd_ente_convenz()
		.setDecodeMap(ComboGetter.getMappaTiStatoRichGestUser());
	getForm().getEnteConvenzionatoDetail().getId_prov_sede_legale()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgAmbitoTerritTableBean(
				ConstOrgAmbitoTerrit.TiAmbitoTerrit.PROVINCIA.toString()),
			"id_ambito_territ", "cd_ambito_territ"));
	getForm().getEnteConvenzionatoDetail().getCd_ambito_territ_regione()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgAmbitoTerritTableBean(
				ConstOrgAmbitoTerrit.TiAmbitoTerrit.REGIONE_STATO.toString()),
			"id_ambito_territ", "cd_ambito_territ"));
	getForm().getEnteConvenzionatoDetail().getCd_ambito_territ_provincia()
		.setDecodeMap(new DecodeMap());
	getForm().getEnteConvenzionatoDetail().getCd_ambito_territ_forma_associata()
		.setDecodeMap(new DecodeMap());
	getForm().getEnteConvenzionatoDetail().getId_ente_convenz_nuovo()
		.setDecodeMap(new DecodeMap());
	getForm().getEnteConvenzionatoDetail().getDs_categ_ente()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgCategEnteTableBean(), "id_categ_ente",
			"ds_categ_ente"));

	getForm().getEnteConvenzionatoDetail().getId_cd_iva().setDecodeMap(DecodeMap.Factory
		.newInstance(entiConvenzionatiEjb.getOrgCdIvaTableBean(), "id_cd_iva", "cd_iva"));

	getForm().getEnteConvenzionatoDetail().getTi_mod_pagam()
		.setDecodeMap(ComboGetter.getMappaTiModParam());

	getForm().getOrganizzazioniVersantiList().getDl_composito_organiz()
		.setGenericLinkId("url_assoluto");

	// // INIZIALIZZO IL CAMPO DELLA LISTA STRUTTURE VERSANTI PER GESTIRE LA CHIAMATA A SACER
	// getForm().getOrganizzazioniVersantiList().getNm_organiz_strut().setExternalLinkParamApplic(entiConvenzionatiEjb.getSacerUrlForStruttureVersanti());
	// // INIZIALIZZO IL CAMPO DELLA LISTA VERSATORI PER GESTIRE LA CHIAMATA A SACER_PREINGEST
	// getForm().getOrganizzazioniVersantiList().getNm_organiz_vers().setExternalLinkParamApplic(entiConvenzionatiEjb.getPreingestUrlForVersatori());
    }

    @Override
    public JSONObject triggerEnteConvenzionatoDetailCd_ambito_territ_regioneOnTrigger()
	    throws EMFError {
	getForm().getEnteConvenzionatoDetail().post(getRequest());
	BigDecimal idAmbitoTerrit = getForm().getEnteConvenzionatoDetail()
		.getCd_ambito_territ_regione().parse();
	try {
	    if (idAmbitoTerrit != null) {
		List<BigDecimal> ids = new ArrayList<>();
		ids.add(idAmbitoTerrit);
		getForm().getEnteConvenzionatoDetail().getCd_ambito_territ_provincia()
			.setDecodeMap(DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getOrgAmbitoTerritTableBean(ids),
				"id_ambito_territ", "cd_ambito_territ"));
		getForm().getEnteConvenzionatoDetail().getCd_ambito_territ_forma_associata()
			.setDecodeMap(new DecodeMap());
	    } else {
		getForm().getEnteConvenzionatoDetail().getCd_ambito_territ_provincia()
			.setDecodeMap(new DecodeMap());
		getForm().getEnteConvenzionatoDetail().getCd_ambito_territ_forma_associata()
			.setDecodeMap(new DecodeMap());
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
	return getForm().getEnteConvenzionatoDetail().asJSON();
    }

    @Override
    public JSONObject triggerEnteConvenzionatoWizardDetailCd_ambito_territ_regioneOnTrigger()
	    throws EMFError {
	getForm().getEnteConvenzionatoWizardDetail().post(getRequest());
	BigDecimal idAmbitoTerrit = getForm().getEnteConvenzionatoWizardDetail()
		.getCd_ambito_territ_regione().parse();
	try {
	    if (idAmbitoTerrit != null) {
		List<BigDecimal> ids = new ArrayList<>();
		ids.add(idAmbitoTerrit);
		getForm().getEnteConvenzionatoWizardDetail().getCd_ambito_territ_provincia()
			.setDecodeMap(DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getOrgAmbitoTerritTableBean(ids),
				"id_ambito_territ", "cd_ambito_territ"));
		getForm().getEnteConvenzionatoWizardDetail().getCd_ambito_territ_forma_associata()
			.setDecodeMap(new DecodeMap());
	    } else {
		getForm().getEnteConvenzionatoWizardDetail().getCd_ambito_territ_provincia()
			.setDecodeMap(new DecodeMap());
		getForm().getEnteConvenzionatoWizardDetail().getCd_ambito_territ_forma_associata()
			.setDecodeMap(new DecodeMap());
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
	return getForm().getEnteConvenzionatoWizardDetail().asJSON();
    }

    @Override
    public JSONObject triggerEnteConvenzionatoDetailCd_ambito_territ_provinciaOnTrigger()
	    throws EMFError {
	getForm().getEnteConvenzionatoDetail().post(getRequest());
	BigDecimal idAmbitoTerrit = getForm().getEnteConvenzionatoDetail()
		.getCd_ambito_territ_provincia().parse();
	try {
	    if (idAmbitoTerrit != null) {
		List<BigDecimal> ids = new ArrayList<>();
		ids.add(idAmbitoTerrit);
		getForm().getEnteConvenzionatoDetail().getCd_ambito_territ_forma_associata()
			.setDecodeMap(DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getOrgAmbitoTerritTableBean(ids),
				"id_ambito_territ", "cd_ambito_territ"));
	    } else {
		getForm().getEnteConvenzionatoDetail().getCd_ambito_territ_forma_associata()
			.setDecodeMap(new DecodeMap());
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
	return getForm().getEnteConvenzionatoDetail().asJSON();
    }

    @Override
    public JSONObject triggerEnteConvenzionatoWizardDetailCd_ambito_territ_provinciaOnTrigger()
	    throws EMFError {
	getForm().getEnteConvenzionatoWizardDetail().post(getRequest());
	BigDecimal idAmbitoTerrit = getForm().getEnteConvenzionatoWizardDetail()
		.getCd_ambito_territ_provincia().parse();
	try {
	    if (idAmbitoTerrit != null) {
		List<BigDecimal> ids = new ArrayList<>();
		ids.add(idAmbitoTerrit);
		getForm().getEnteConvenzionatoWizardDetail().getCd_ambito_territ_forma_associata()
			.setDecodeMap(DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getOrgAmbitoTerritTableBean(ids),
				"id_ambito_territ", "cd_ambito_territ"));
	    } else {
		getForm().getEnteConvenzionatoWizardDetail().getCd_ambito_territ_forma_associata()
			.setDecodeMap(new DecodeMap());
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
	return getForm().getEnteConvenzionatoWizardDetail().asJSON();
    }

    @Override
    public JSONObject triggerEnteConvenzionatoDetailId_ambiente_ente_convenz_nuovoOnTrigger()
	    throws EMFError {
	getForm().getEnteConvenzionatoDetail().post(getRequest());
	BigDecimal idAmbienteEnteConvenzNuovo = getForm().getEnteConvenzionatoDetail()
		.getId_ambiente_ente_convenz_nuovo().parse();
	BigDecimal idEnteConvenzExcluded = getForm().getEnteConvenzionatoDetail().getId_ente_siam()
		.parse();
	try {
	    if (idAmbienteEnteConvenzNuovo != null) {
		getForm().getEnteConvenzionatoDetail().getId_ente_convenz_nuovo()
			.setDecodeMap(DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getOrgEnteConvenzNonCessatiTableBean(
					idAmbienteEnteConvenzNuovo, idEnteConvenzExcluded),
				"id_ente_siam", "nm_ente_siam"));
	    } else {
		getForm().getEnteConvenzionatoDetail().getId_ente_convenz_nuovo()
			.setDecodeMap(new DecodeMap());
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
	return getForm().getEnteConvenzionatoDetail().asJSON();
    }

    @Override
    public JSONObject triggerEnteConvenzionatoDetailId_ambiente_ente_convenz_cambioOnTrigger()
	    throws EMFError {
	getForm().getEnteConvenzionatoDetail().post(getRequest());
	getForm().getAccordoDetail().post(getRequest());
	getForm().getAccordoDetail().getId_ente_convenz_gestore().setValue(null);
	getForm().getAccordoDetail().getNm_ente_convenz_gestore().setValue(null);
	getForm().getAccordoDetail().getId_ente_convenz_conserv().setValue(null);
	getForm().getAccordoDetail().getNm_ente_convenz_conserv().setValue(null);
	getForm().getAccordoDetail().getId_ente_convenz_amministratore().setValue(null);
	getForm().getAccordoDetail().getNm_ente_convenz_amministratore().setValue(null);
	BigDecimal idAmbienteEnteConvenzCambio = getForm().getEnteConvenzionatoDetail()
		.getId_ambiente_ente_convenz_cambio().parse();
	try {
	    if (idAmbienteEnteConvenzCambio != null) {
		OrgAmbienteEnteConvenzRowBean ambienteEnteConvenzRowBean = entiConvenzionatiEjb
			.getOrgAmbienteEnteConvenzRowBean(idAmbienteEnteConvenzCambio);
		getForm().getEnteConvenzionatoDetail().getNm_ambiente_ente_convenz()
			.setValue(ambienteEnteConvenzRowBean.getNmAmbienteEnteConvenz());
		//
		OrgEnteSiamRowBean enteConvenzGestore = entiConvenzionatiEjb
			.getOrgEnteConvenzGestore(idAmbienteEnteConvenzCambio);
		if (enteConvenzGestore != null) {
		    getForm().getAccordoDetail().getId_ente_convenz_gestore()
			    .setValue(enteConvenzGestore.getIdEnteSiam().toPlainString());
		    getForm().getAccordoDetail().getNm_ente_convenz_gestore()
			    .setValue(enteConvenzGestore.getNmEnteSiam());
		}
		//
		OrgEnteSiamRowBean enteConvenzConserv = entiConvenzionatiEjb
			.getOrgEnteConvenzConserv(idAmbienteEnteConvenzCambio);
		if (enteConvenzConserv != null) {
		    getForm().getAccordoDetail().getId_ente_convenz_conserv()
			    .setValue(enteConvenzConserv.getIdEnteSiam().toPlainString());
		    getForm().getAccordoDetail().getNm_ente_convenz_conserv()
			    .setValue(enteConvenzConserv.getNmEnteSiam());

		    OrgAccordoEnteTableBean accordoEnteTableBean = entiConvenzionatiEjb
			    .getOrgAccordoEntePiuRecenteTableBean(
				    enteConvenzConserv.getIdEnteSiam());
		    if (!accordoEnteTableBean.isEmpty()) {
			getForm().getAccordoDetail().getId_ente_convenz_amministratore()
				.setValue(accordoEnteTableBean.getRow(0)
					.getIdEnteConvenzAmministratore().toPlainString());
			getForm().getAccordoDetail().getNm_ente_convenz_amministratore()
				.setValue(accordoEnteTableBean.getRow(0)
					.getString("nm_ente_convenz_amministratore"));
		    }
		}
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
	return getForm().getAccordoDetail().asJSON();
    }

    private void saveEnteConvenzionato() throws EMFError {
	getForm().getParametriAmministrazioneList().post(getRequest());
	getForm().getParametriConservazioneList().post(getRequest());
	getForm().getParametriGestioneList().post(getRequest());
	if (getForm().getEnteConvenzionatoDetail().postAndValidate(getRequest(), getMessageBox())) {

	    // Controllo cessazione ente convenzionato
	    if (getForm().getEnteConvenzionatoDetail().getId_ente_convenz_nuovo().parse() != null) {
		if (getForm().getEnteConvenzionatoDetail().getDt_cessazione().parse()
			.after(new Date())) {
		    getMessageBox().addError(
			    "E\u0027 possibile indicare un nuovo ente convenzionato solo se l\u0027ente \u00E8 cessato");
		}
	    }

	    IamParamApplicTableBean parametriAmministrazione = (IamParamApplicTableBean) getForm()
		    .getParametriAmministrazioneList().getTable();
	    IamParamApplicTableBean parametriConservazione = (IamParamApplicTableBean) getForm()
		    .getParametriConservazioneList().getTable();
	    IamParamApplicTableBean parametriGestione = (IamParamApplicTableBean) getForm()
		    .getParametriGestioneList().getTable();

	    // Controllo valori possibili su ente
	    for (IamParamApplicRowBean paramApplicRowBean : parametriAmministrazione) {
		if (paramApplicRowBean.getString("ds_lista_valori_ammessi") != null
			&& !paramApplicRowBean.getString("ds_lista_valori_ammessi").equals("")) {
		    if (paramApplicRowBean.getString("ds_valore_param_applic_ente_amm") != null
			    && !paramApplicRowBean.getString("ds_valore_param_applic_ente_amm")
				    .equals("")) {
			if (!inValoriPossibili(
				paramApplicRowBean.getString("ds_valore_param_applic_ente_amm"),
				paramApplicRowBean.getString("ds_lista_valori_ammessi"))) {
			    getMessageBox().addError(
				    "Il valore del parametro non \u00E8 compreso tra i valori ammessi sul parametro");
			}
		    }
		}
	    }

	    for (IamParamApplicRowBean paramApplicRowBean : parametriConservazione) {
		if (paramApplicRowBean.getString("ds_lista_valori_ammessi") != null
			&& !paramApplicRowBean.getString("ds_lista_valori_ammessi").equals("")) {
		    if (paramApplicRowBean.getString("ds_valore_param_applic_ente_cons") != null
			    && !paramApplicRowBean.getString("ds_valore_param_applic_ente_cons")
				    .equals("")) {
			if (!inValoriPossibili(
				paramApplicRowBean.getString("ds_valore_param_applic_ente_cons"),
				paramApplicRowBean.getString("ds_lista_valori_ammessi"))) {
			    getMessageBox().addError(
				    "Il valore del parametro non \u00E8 compreso tra i valori ammessi sul parametro");
			}
		    }
		}
	    }

	    for (IamParamApplicRowBean paramApplicRowBean : parametriGestione) {
		if (paramApplicRowBean.getString("ds_lista_valori_ammessi") != null
			&& !paramApplicRowBean.getString("ds_lista_valori_ammessi").equals("")) {
		    if (paramApplicRowBean.getString("ds_valore_param_applic_ente_gest") != null
			    && !paramApplicRowBean.getString("ds_valore_param_applic_ente_gest")
				    .equals("")) {
			if (!inValoriPossibili(
				paramApplicRowBean.getString("ds_valore_param_applic_ente_gest"),
				paramApplicRowBean.getString("ds_lista_valori_ammessi"))) {
			    getMessageBox().addError(
				    "Il valore del parametro non \u00E8 compreso tra i valori ammessi sul parametro");
			}
		    }
		}
	    }

	    BigDecimal idAmbienteEnteConvenz = getForm().getEnteConvenzionatoDetail()
		    .getId_ambiente_ente_convenz().parse();
	    // Verifico la selezione dell'ambiente in caso di cambio ambiente
	    if (getSession().getAttribute("cambioAmbiente") != null
		    && idAmbienteEnteConvenz == null) {
		getMessageBox().addError("Attenzione: campo 'Ambiente' obbligatorio");
	    }

	    // Verifico che le date di validitÃ  dell'ente non vada in contrasto con eventuali
	    // Anagrafiche
	    if (getForm().getAnagraficheList().getTable().size() > 0) {
		boolean controlloOK = checkValiditaEnteAnagrafiche(
			getForm().getEnteConvenzionatoDetail().getId_ente_siam().parse(),
			getForm().getEnteConvenzionatoDetail().getDt_ini_val().parse(),
			getForm().getEnteConvenzionatoDetail().getDt_cessazione().parse());
		if (!controlloOK) {
		    getMessageBox().addError(
			    "Attenzione: impossibile modificare la validità dell'ente in quanto risulta in contrasto con la validità delle anagrafiche presenti.");
		}
	    }

	    if (getForm().getEnteConvenzionatoDetail().getDt_rich_modulo_info().parse() != null
		    && getForm().getEnteConvenzionatoDetail().getDt_rich_modulo_info().parse()
			    .after(new Date())) {
		getMessageBox().addError(
			"Attenzione: data di richiesta del modulo di informazioni non pu\u00F2 essere una data futura");
	    }

	    try {
		if (!getMessageBox().hasError()) {
		    // Salva l'ente convenzionato
		    EnteConvenzionatoBean creazioneBean = new EnteConvenzionatoBean(
			    getForm().getEnteConvenzionatoDetail());
		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    if (getForm().getListaEntiConvenzionati().getStatus().equals(Status.insert)
			    || getForm().getEntiCessatiDetailList().getStatus()
				    .equals(Status.insert)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
			Long idEnteConvenzionato = entiConvenzionatiEjb.saveEnteConvenzionato(param,
				creazioneBean, parametriAmministrazione, parametriConservazione,
				parametriGestione);
			if (idEnteConvenzionato != null) {
			    getForm().getEnteConvenzionatoDetail().getId_ente_siam()
				    .setValue(idEnteConvenzionato.toString());
			}
			OrgEnteSiamRowBean row = new OrgEnteSiamRowBean();
			getForm().getEnteConvenzionatoDetail().copyToBean(row);
			row.setBigDecimal("id_ente_convenz", row.getIdEnteSiam());
			row.setString("nm_ente_convenz", row.getNmEnteSiam());
			getForm().getListaEntiConvenzionati().getTable().last();
			getForm().getListaEntiConvenzionati().getTable().add(row);
			//
			loadDettaglioEnteConvenzionato(BigDecimal.valueOf(idEnteConvenzionato));
			getForm().getEnteConvenzionatoDetail().setViewMode();
			getForm().getListaEntiConvenzionati().setStatus(Status.view);
			getForm().getEnteConvenzionatoDetail().setStatus(Status.view);
			getMessageBox().addInfo("Ente convenzionato salvato con successo");
			getMessageBox().setViewMode(ViewMode.plain);
		    } else if (getForm().getListaEntiConvenzionati().getStatus()
			    .equals(Status.update)
			    || getForm().getEntiCessatiDetailList().getStatus()
				    .equals(Status.update)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
			BigDecimal idEnteConvenzionato = getForm().getEnteConvenzionatoDetail()
				.getId_ente_siam().parse();
			checkAndSaveModificaEnteConvenz(param, idEnteConvenzionato, creazioneBean,
				parametriAmministrazione, parametriConservazione,
				parametriGestione);
		    }
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
	    }
	}

	forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO);
    }

    public boolean checkValiditaEnteAnagrafiche(BigDecimal idEnteConvenz, Date dtIniValEnte,
	    Date dtCessazioneEnte) {
	Date[] dateAnagrafiche = entiConvenzionatiEjb.getExtremeDatesAnagrafiche(idEnteConvenz);
	Date minDtIniValAnagrafica = dateAnagrafiche[0];
	Date maxDtFineValAnagrafica = dateAnagrafiche[1];
	return dtIniValEnte.compareTo(minDtIniValAnagrafica) <= 0
		&& dtCessazioneEnte.compareTo(minDtIniValAnagrafica) >= 0
		&& dtIniValEnte.compareTo(maxDtFineValAnagrafica) < 0
		&& dtCessazioneEnte.compareTo(maxDtFineValAnagrafica) > 0;

    }

    private boolean inValoriPossibili(String dsValoreParamApplicEnte, String dsListaValoriAmmessi) {
	String[] tokens = dsListaValoriAmmessi.split("\\|");
	Set<String> mySet = new HashSet<String>(Arrays.asList(tokens));
	return mySet.contains(dsValoreParamApplicEnte);
    }

    private void checkAndSaveModificaEnteConvenz(LogParam param, BigDecimal idEnteConvenz,
	    EnteConvenzionatoBean creazioneBean, IamParamApplicTableBean parametriAmministrazione,
	    IamParamApplicTableBean parametriConservazione,
	    IamParamApplicTableBean parametriGestione) throws ParerUserError, EMFError {
	// Controlli sulle date degli accordi
	if (creazioneBean.getDt_cessazione().before(new Date())
		&& entiConvenzionatiEjb.checkEsistenzaAccordiAttivi(idEnteConvenz)) {
	    throw new ParerUserError(
		    "Non \u00E8 possibile eseguire la cessazione dell\u0027ente: esiste un accordo attivo alla data");
	}
	// Se l'ente ÃƒÆ’Ã‚Â¨ cessato verifico se esistono utenti attivi appartenenti o con
	// abilitazione puntuale
	// all\u0027ente
	// convenzionato
	UsrUserTableBean usrUserTableBean = entiConvenzionatiEjb.getUsrUserEnteConvenzTableBean(
		idEnteConvenz, Arrays.asList(ConstUsrStatoUser.TiStatotUser.ATTIVO.name()), null);
	UsrDichAbilEnteConvenzTableBean usrDichAbilEnteConvenzTableBean = amministrazioneUtentiEjb
		.getUsrDichAbilEnteConvenzUnEnteAttiviTableBean(idEnteConvenz);
	if ((usrUserTableBean.size() > 0 || usrDichAbilEnteConvenzTableBean.size() > 0)
		&& creazioneBean.getDt_cessazione().before(new Date())) {
	    Object[] attributiSalvataggioModificaEnteConvenz = new Object[6];
	    attributiSalvataggioModificaEnteConvenz[0] = param;
	    attributiSalvataggioModificaEnteConvenz[1] = idEnteConvenz;
	    attributiSalvataggioModificaEnteConvenz[2] = creazioneBean;
	    attributiSalvataggioModificaEnteConvenz[3] = parametriAmministrazione;
	    attributiSalvataggioModificaEnteConvenz[4] = parametriConservazione;
	    attributiSalvataggioModificaEnteConvenz[5] = parametriGestione;
	    getSession().setAttribute("attributiSalvataggioModificaEnteConvenz",
		    attributiSalvataggioModificaEnteConvenz);
	    getRequest().setAttribute("customModificaEnteConvenzMessageBox", true);
	} else {
	    eseguiSalvataggioModificaEnteConvenz(param, idEnteConvenz, creazioneBean,
		    parametriAmministrazione, parametriConservazione, parametriGestione);
	}
    }

    public void confermaModificaEnteConvenz() throws ParerUserError, EMFError {
	try {
	    if (getSession().getAttribute("attributiSalvataggioModificaEnteConvenz") != null) {
		Object[] attributiSalvataggioModificaEnteConvenz = (Object[]) getSession()
			.getAttribute("attributiSalvataggioModificaEnteConvenz");
		LogParam param = (LogParam) attributiSalvataggioModificaEnteConvenz[0];
		BigDecimal idEnteConvenz = (BigDecimal) attributiSalvataggioModificaEnteConvenz[1];
		EnteConvenzionatoBean creazioneBean = (EnteConvenzionatoBean) attributiSalvataggioModificaEnteConvenz[2];
		IamParamApplicTableBean parametriAmministrazione = (IamParamApplicTableBean) attributiSalvataggioModificaEnteConvenz[3];
		IamParamApplicTableBean parametriConservazione = (IamParamApplicTableBean) attributiSalvataggioModificaEnteConvenz[4];
		IamParamApplicTableBean parametriGestione = (IamParamApplicTableBean) attributiSalvataggioModificaEnteConvenz[5];
		eseguiSalvataggioModificaEnteConvenz(param, idEnteConvenz, creazioneBean,
			parametriAmministrazione, parametriConservazione, parametriGestione);
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}

	forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO);
    }

    public void annullaModificaEnteConvenz() {
	getSession().removeAttribute("attributiSalvataggioModificaEnteConvenz");
	forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO);
    }

    public void eseguiSalvataggioModificaEnteConvenz(LogParam param, BigDecimal idEnteConvenz,
	    EnteConvenzionatoBean creazioneBean, IamParamApplicTableBean parametriAmministrazione,
	    IamParamApplicTableBean parametriConservazione,
	    IamParamApplicTableBean parametriGestione) throws ParerUserError, EMFError {
	int numStoricizzazioniPreSalvataggio = -1;
	if (getSession().getAttribute("cambioAmbiente") != null) {
	    // // Controllo sull'ambiente
	    // entiConvenzionatiEjb.isAmbienteModificato(creazioneBean.getId_ambiente_ente_convenz(),
	    // idEnteConvenz,
	    // creazioneBean.getDt_ini_val_appart_ambiente(),
	    // creazioneBean.getDt_fin_val_appart_ambiente());
	    // Ricavo l'info di quante storicizzazioni ho finora
	    numStoricizzazioniPreSalvataggio = entiConvenzionatiEjb
		    .getNumStoricizzazioni(idEnteConvenz);
	}
	entiConvenzionatiEjb.saveEnteConvenzionato(param, idEnteConvenz, creazioneBean,
		parametriAmministrazione, parametriConservazione, parametriGestione,
		getUser().getIdUtente());

	if (getSession().getAttribute("cambioAmbiente") != null) {
	    int numStoricizzazioniPostSalvataggio = entiConvenzionatiEjb
		    .getNumStoricizzazioni(idEnteConvenz);
	    // Se non ho cambiato ambiente e/o date, non storicizzo
	    if (numStoricizzazioniPreSalvataggio == numStoricizzazioniPostSalvataggio) {
		getMessageBox().addWarning(
			"Ambiente e/o date di appartenenza non modificate: è stata eseguita la modifica dell’ente senza eseguire la storicizzazione");
	    } else {
		getMessageBox().addInfo(
			"Storicizzazione dell'appartenenza all'ambiente effettuata con successo!");
	    }
	} else {
	    getMessageBox().addInfo("Update dell'ente effettuato con successo");
	}
	//
	loadDettaglioEnteConvenzionato(idEnteConvenz);
	getForm().getEnteConvenzionatoDetail().setViewMode();
	getForm().getListaEntiConvenzionati().setStatus(Status.view);
	getForm().getEnteConvenzionatoDetail().setStatus(Status.view);

	getForm().getEntiCessatiDetailList().setStatus(Status.view);

	// getMessageBox().addInfo("Ente convenzionato salvato con successo");
	getMessageBox().setViewMode(ViewMode.plain);
	getSession().removeAttribute("attributiSalvataggioModificaEnteConvenz");

	// Rimuovo dalla sessione l'attributo riferito al cambio ambiente
	getSession().removeAttribute("cambioAmbiente");
	forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO);
    }

    private void loadCurrentDettaglioEnteConvenzFromList(String listName)
	    throws EMFError, ParerUserError {
	logger.info("Caricamento dettaglio ente convenz da ricerca enti");
	// Salvo in sessione il "tipo" di lista dalla quale "proviene" l'ente, sarÃƒÆ’Ã‚Â 
	// ListaEntiConvenzionati o
	// EntiCessatiList
	getSession().setAttribute("navTableEntiConvenz", listName);
	// Prendendo i dati dalla form, popolo un oggetto List generico del tipo gestito dal
	// framework naturalmente
	it.eng.spagoLite.form.list.List formList = ((it.eng.spagoLite.form.list.List) getForm()
		.getComponent(listName));
	// Ricavo la riga corrente, recuperata dall'oggetto Table della lista
	BaseRowInterface listRow = formList.getTable().getCurrentRow();
	// Inizializzo i campi di dettaglio
	initEnteConvenzionatoDetail();
	// Se nullo il relativo parametro, siamo per forza al primo livello di annidamento
	BigDecimal level = getForm().getEnteConvenzionatoDetail().getLevel_enti_convenz()
		.parse() != null
			? getForm().getEnteConvenzionatoDetail().getLevel_enti_convenz().parse()
			: BigDecimal.ONE;
	// Carico il dettaglio ente convenz
	loadDettaglioEnteConvenzionato(listRow.getBigDecimal("id_ente_convenz"));
	getForm().getEnteConvenzionatoDetail().getLevel_enti_convenz()
		.setValue(BigDecimal.ONE.toPlainString());
	// Se sto scorrendo avanti/indietro e il livello di annidamento dell'ultimo oggetto presente
	// nello stack
	// è uguale al mio, eseguo una "pop" di PARI LIVELLO
	if ((ListAction.NE_NEXT.equals(getNavigationEvent())
		|| ListAction.NE_PREV.equals(getNavigationEvent()))
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
		listRow.getBigDecimal("id_ente_convenz"), formList.getName(), formList.getTable(),
		1);
    }

    private void loadDettaglioEnteConvenzionato(BigDecimal idEnteConvenz)
	    throws ParerUserError, EMFError {
	// Carico entrambe le combo per mostrare l'ambito completo
	getForm().getEnteConvenzionatoDetail().getCd_ambito_territ_provincia()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgAmbitoTerritTableBean(
				ConstOrgAmbitoTerrit.TiAmbitoTerrit.PROVINCIA.toString()),
			"id_ambito_territ", "cd_ambito_territ"));
	getForm().getEnteConvenzionatoDetail().getCd_ambito_territ_forma_associata()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgAmbitoTerritTableBean(
				ConstOrgAmbitoTerrit.TiAmbitoTerrit.FORMA_ASSOCIATA.toString()),
			"id_ambito_territ", "cd_ambito_territ"));
	//
	OrgEnteSiamRowBean detailRow = entiConvenzionatiEjb.getOrgEnteConvenzRowBean(idEnteConvenz);
	getForm().getEnteConvenzionatoDetail().getId_ambiente_ente_convenz().setDecodeMap(null);
	OrgAmbienteEnteConvenzRowBean orgAmbienteEnteConvenzByEnteConvenz = entiConvenzionatiEjb
		.getOrgAmbienteEnteConvenzByEnteConvenz(idEnteConvenz);
	detailRow.setString("nm_ambiente_ente_convenz",
		orgAmbienteEnteConvenzByEnteConvenz.getNmAmbienteEnteConvenz());
	getForm().getEnteConvenzionatoDetail().copyFromBean(detailRow);
	getForm().getEnteConvenzionatoDetail().setViewMode();
	getForm().getEnteConvenzionatoDetail().getId_ambiente_ente_convenz().setHidden(false);
	getForm().getEnteConvenzionatoDetail().getDt_ini_val_appart_ambiente().setHidden(false);
	getForm().getEnteConvenzionatoDetail().getDt_fin_val_appart_ambiente().setHidden(false);
	if (getForm().getEnteConvenzionatoDetail().getFl_chiuso().parse().equals("1")) {
	    getForm().getEnteConvenzionatoDetail().getFl_chiuso().setHidden(false);
	} else {
	    getForm().getEnteConvenzionatoDetail().getFl_chiuso().setHidden(true);
	}

	// Carico la descrizione IVA
	if (detailRow.getIdCdIva() != null) {
	    getForm().getEnteConvenzionatoDetail().getDs_iva()
		    .setValue(entiConvenzionatiEjb.getDsIva(detailRow.getIdCdIva()));
	}

	getForm().getEnteConvenzionatoDetail().setStatus(Status.view);
	getForm().getListaEntiConvenzionati().setStatus(Status.view);
	getForm().getEntiCessatiDetailList().setStatus(Status.view);
	// Gestione dell'ambiente e dell'ente convenz nuovo per cessazione ente
	if (detailRow.getIdEnteConvenzNuovo() != null) {
	    OrgAmbienteEnteConvenzRowBean orgAmbienteEnteConvenzNuovoByEnteConvenz = entiConvenzionatiEjb
		    .getOrgAmbienteEnteConvenzByEnteConvenz(detailRow.getIdEnteConvenzNuovo());
	    getForm().getEnteConvenzionatoDetail().getId_ambiente_ente_convenz_nuovo().setValue(
		    "" + orgAmbienteEnteConvenzNuovoByEnteConvenz.getIdAmbienteEnteConvenz());
	    // Carico la combo "Ente convenz nuovo" in base all'ambiente ente convenz nuovo
	    getForm().getEnteConvenzionatoDetail().getId_ente_convenz_nuovo()
		    .setDecodeMap(DecodeMap.Factory.newInstance(
			    entiConvenzionatiEjb.getOrgEnteConvenzTableBean(idEnteConvenz),
			    "id_ente_siam", "nm_ente_siam"));
	    getForm().getEnteConvenzionatoDetail().getId_ente_convenz_nuovo()
		    .setValue("" + detailRow.getIdEnteConvenzNuovo());
	    getForm().getEnteConvenzionatoDetail().getId_ente_convenz_nuovo().setHidden(false);
	}

	/* Popolamento liste */
	// Precedenti appartenenza all'ambiente ente convenzionato
	getForm().getPrecedentiAppartenenzeAmbientiConvenzList().setTable(
		entiConvenzionatiEjb.getOrgStoEnteAmbienteConvenzTableBean(idEnteConvenz));
	getForm().getPrecedentiAppartenenzeAmbientiConvenzList().getTable().setPageSize(10);
	getForm().getPrecedentiAppartenenzeAmbientiConvenzList().getTable().first();
	// Anagrafiche
	getForm().getAnagraficheList()
		.setTable(entiConvenzionatiEjb.getOrgStoEnteConvenzTableBean(idEnteConvenz));
	getForm().getAnagraficheList().getTable().setPageSize(10);
	getForm().getAnagraficheList().getTable().first();
	// Enti cessati
	getForm().getEntiCessatiList()
		.setTable(entiConvenzionatiEjb.getOrgEnteConvenzCessatiTableBean(idEnteConvenz));
	getForm().getEntiCessatiList().getTable().setPageSize(10);
	getForm().getEntiCessatiList().getTable().first();
	// Accordi
	getForm().getAccordiList()
		.setTable(entiConvenzionatiEjb.getOrgAccordoEnteTableBean(idEnteConvenz));
	getForm().getAccordiList().getTable().setPageSize(10);
	getForm().getAccordiList().getTable().first();
	// Popolamento lista moduli
	getForm().getModuliInformazioniList().setTable(
		entiConvenzionatiEjb.getOrgModuloInfoAccordoByEnteTableBean(idEnteConvenz));
	getForm().getModuliInformazioniList().getTable().setPageSize(10);
	getForm().getModuliInformazioniList().getTable().first();
	// Popolamento lista disciplinari tecnici
	getForm().getDisciplinariTecniciList()
		.setTable(entiConvenzionatiEjb.getOrgDiscipStrutByEnteTableBean(idEnteConvenz));
	getForm().getDisciplinariTecniciList().getTable().setPageSize(10);
	getForm().getDisciplinariTecniciList().getTable().first();
	// Utenti ente convenzionato
	getForm().getUtentiEnteList().setTable(entiConvenzionatiEjb.getUsrUserEnteConvenzTableBean(
		idEnteConvenz,
		Arrays.asList(ConstUsrStatoUser.TiStatotUser.ATTIVO.name(),
			ConstUsrStatoUser.TiStatotUser.DISATTIVO.name()),
		Arrays.asList(ApplEnum.TipoUser.PERSONA_FISICA.name(),
			ApplEnum.TipoUser.AUTOMA.name(), ApplEnum.TipoUser.NON_DI_SISTEMA.name())));
	getForm().getUtentiEnteList().getTable().setPageSize(10);
	getForm().getUtentiEnteList().getTable().first();
	// Organizzazioni versanti
	getForm().getOrganizzazioniVersantiList()
		.setTable(entiConvenzionatiEjb.getOrgVEnteConvenzByOrganizTableBean(idEnteConvenz,
			getForm().getOrganizzazioniVersantiList().isFilterValidRecords()));
	getForm().getOrganizzazioniVersantiList().getTable().setPageSize(10);
	getForm().getOrganizzazioniVersantiList().getTable().first();

	UsrUserTableBean usrUserTableBean = null;
	Set<BigDecimal> idUserIamPfDaEscludere = new HashSet<>();
	Set<BigDecimal> idUserIamAuDaEscludere = new HashSet<>();
	String tiEnteConvenz = getForm().getEnteConvenzionatoDetail().getTi_ente_convenz().parse();
	// Recupero l'accordo più recente per l'ente convenzionato
	OrgAccordoEnteTableBean accordoTableBean = entiConvenzionatiEjb
		.getOrgAccordoEntePiuRecenteTableBean(idEnteConvenz);
	if (!accordoTableBean.isEmpty()) {
	    // Recupero la lista degli utenti da escludere dalla lista in base al tipo ente
	    // convenzionato
	    if (tiEnteConvenz.equals(TiEnteConvenz.CONSERVATORE)
		    || tiEnteConvenz.equals(TiEnteConvenz.GESTORE)
		    || tiEnteConvenz.equals(TiEnteConvenz.PRODUTTORE)) {
		usrUserTableBean = entiConvenzionatiEjb.getUsrUserEnteConvenzTableBean(
			accordoTableBean.getRow(0).getIdEnteConvenzAmministratore(),
			Arrays.asList(ConstUsrStatoUser.TiStatotUser.ATTIVO.name(),
				ConstUsrStatoUser.TiStatotUser.DISATTIVO.name()),
			Arrays.asList(ApplEnum.TipoUser.PERSONA_FISICA.name()));
		for (UsrUserRowBean usrUserRowBean : usrUserTableBean) {
		    idUserIamPfDaEscludere.add(usrUserRowBean.getIdUserIam());
		}
		usrUserTableBean = entiConvenzionatiEjb.getUsrUserEnteConvenzTableBean(
			accordoTableBean.getRow(0).getIdEnteConvenzAmministratore(),
			Arrays.asList(ConstUsrStatoUser.TiStatotUser.ATTIVO.name(),
				ConstUsrStatoUser.TiStatotUser.DISATTIVO.name()),
			Arrays.asList(ApplEnum.TipoUser.AUTOMA.name()));
		for (UsrUserRowBean usrUserRowBean : usrUserTableBean) {
		    idUserIamAuDaEscludere.add(usrUserRowBean.getIdUserIam());
		}
	    }
	    if (tiEnteConvenz.equals(TiEnteConvenz.GESTORE)
		    || tiEnteConvenz.equals(TiEnteConvenz.PRODUTTORE)) {
		usrUserTableBean = entiConvenzionatiEjb.getUsrUserEnteConvenzTableBean(
			accordoTableBean.getRow(0).getIdEnteConvenzConserv(),
			Arrays.asList(ConstUsrStatoUser.TiStatotUser.ATTIVO.name(),
				ConstUsrStatoUser.TiStatotUser.DISATTIVO.name()),
			Arrays.asList(ApplEnum.TipoUser.PERSONA_FISICA.name()));
		for (UsrUserRowBean usrUserRowBean : usrUserTableBean) {
		    idUserIamPfDaEscludere.add(usrUserRowBean.getIdUserIam());
		}
		usrUserTableBean = entiConvenzionatiEjb.getUsrUserEnteConvenzTableBean(
			accordoTableBean.getRow(0).getIdEnteConvenzConserv(),
			Arrays.asList(ConstUsrStatoUser.TiStatotUser.ATTIVO.name(),
				ConstUsrStatoUser.TiStatotUser.DISATTIVO.name()),
			Arrays.asList(ApplEnum.TipoUser.AUTOMA.name()));
		for (UsrUserRowBean usrUserRowBean : usrUserTableBean) {
		    idUserIamAuDaEscludere.add(usrUserRowBean.getIdUserIam());
		}
	    }
	    if (tiEnteConvenz.equals(TiEnteConvenz.PRODUTTORE)) {
		usrUserTableBean = entiConvenzionatiEjb.getUsrUserEnteConvenzTableBean(
			accordoTableBean.getRow(0).getIdEnteConvenzGestore(),
			Arrays.asList(ConstUsrStatoUser.TiStatotUser.ATTIVO.name(),
				ConstUsrStatoUser.TiStatotUser.DISATTIVO.name()),
			Arrays.asList(ApplEnum.TipoUser.PERSONA_FISICA.name()));
		for (UsrUserRowBean usrUserRowBean : usrUserTableBean) {
		    idUserIamPfDaEscludere.add(usrUserRowBean.getIdUserIam());
		}
		usrUserTableBean = entiConvenzionatiEjb.getUsrUserEnteConvenzTableBean(
			accordoTableBean.getRow(0).getIdEnteConvenzGestore(),
			Arrays.asList(ConstUsrStatoUser.TiStatotUser.ATTIVO.name(),
				ConstUsrStatoUser.TiStatotUser.DISATTIVO.name()),
			Arrays.asList(ApplEnum.TipoUser.AUTOMA.name()));
		for (UsrUserRowBean usrUserRowBean : usrUserTableBean) {
		    idUserIamAuDaEscludere.add(usrUserRowBean.getIdUserIam());
		}
	    }
	}
	// Utenti abilitati PERSONA_FISICA
	getForm().getUtentiPersonaFisicaList()
		.setTable(entiConvenzionatiEjb.getUsrVLisUserTableBean(idEnteConvenz,
			Arrays.asList(ConstUsrStatoUser.TiStatotUser.ATTIVO.name(),
				ConstUsrStatoUser.TiStatotUser.DISATTIVO.name()),
			Arrays.asList(ApplEnum.TipoUser.PERSONA_FISICA.name()),
			idUserIamPfDaEscludere));
	getForm().getUtentiPersonaFisicaList().getTable().setPageSize(10);
	getForm().getUtentiPersonaFisicaList().getTable().first();
	// Utenti abilitati AUTOMA
	getForm().getUtentiAutomaList()
		.setTable(entiConvenzionatiEjb.getUsrVLisUserTableBean(idEnteConvenz,
			Arrays.asList(ConstUsrStatoUser.TiStatotUser.ATTIVO.name(),
				ConstUsrStatoUser.TiStatotUser.DISATTIVO.name()),
			Arrays.asList(ApplEnum.TipoUser.AUTOMA.name()), idUserIamAuDaEscludere));
	getForm().getUtentiAutomaList().getTable().setPageSize(10);
	getForm().getUtentiAutomaList().getTable().first();
	// Utenti archivisti
	getForm().getUtentiArchivistiList()
		.setTable(entiConvenzionatiEjb.getOrgEnteArkRifTableBean(idEnteConvenz));
	getForm().getUtentiArchivistiList().getTable().setPageSize(10);
	getForm().getUtentiArchivistiList().getTable().first();
	// Referenti ente convenzionato
	getForm().getReferentiEnteList()
		.setTable(entiConvenzionatiEjb.getOrgEnteUserRifTableBean(idEnteConvenz));
	getForm().getReferentiEnteList().getTable().setPageSize(10);
	getForm().getReferentiEnteList().getTable().first();
	// Collegamenti di appartenenza dell'ente convenzionato
	getForm().getCollegamentiEnteAppartList()
		.setTable(entiConvenzionatiEjb.getOrgAppartCollegEnteTableBean(idEnteConvenz));
	getForm().getCollegamentiEnteAppartList().getTable().setPageSize(10);
	getForm().getCollegamentiEnteAppartList().getTable().first();
	// Fornitori esterni
	getForm().getFornitoriEsterniList()
		.setTable(entiConvenzionatiEjb.getOrgSuptEsternoEnteConvenzTableBean(idEnteConvenz,
			ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO.name()));
	getForm().getFornitoriEsterniList().getTable().setPageSize(10);
	getForm().getFornitoriEsterniList().getTable().first();
	// Soggetti attuatori
	getForm().getSoggettiAttuatoriList()
		.setTable(entiConvenzionatiEjb.getOrgSuptEsternoEnteConvenzTableBean(idEnteConvenz,
			ConstOrgEnteSiam.TiEnteNonConvenz.SOGGETTO_ATTUATORE.name()));
	getForm().getSoggettiAttuatoriList().getTable().setPageSize(10);
	getForm().getSoggettiAttuatoriList().getTable().first();
	// Organi di vigilanza
	getForm().getOrganiVigilanzaList()
		.setTable(entiConvenzionatiEjb.getOrgVigilEnteProdutTableBean(idEnteConvenz));
	getForm().getOrganiVigilanzaList().getTable().setPageSize(10);
	getForm().getOrganiVigilanzaList().getTable().first();

	// loadListeParametriEnte(orgAmbienteEnteConvenzByEnteConvenz.getIdAmbienteEnteConvenz(),
	// idEnteConvenz, null,
	// false, false, false, false);
	loadListaParametriAmministrazione(
		orgAmbienteEnteConvenzByEnteConvenz.getIdAmbienteEnteConvenz(), idEnteConvenz,
		false, false, false);
	loadListaParametriConservazione(
		orgAmbienteEnteConvenzByEnteConvenz.getIdAmbienteEnteConvenz(), idEnteConvenz,
		false, false, false);
	loadListaParametriGestione(orgAmbienteEnteConvenzByEnteConvenz.getIdAmbienteEnteConvenz(),
		idEnteConvenz, false, false, false);
    }

    private void loadDettaglioGestioneAccordo(BigDecimal idGestAccordo)
	    throws ParerUserError, EMFError {
	OrgGestioneAccordoRowBean bean = entiConvenzionatiEjb
		.getOrgGestioneAccordoRowBean(idGestAccordo);

	// Carico la combo "Registro", va messa qui e non in initAccordoDetail perchÃ¨
	// dipende dal valore dei parametri di gestione
	String nmEnte = bean.getEnteGestAccordo();
	String nmStrut = bean.getStrutturaGestAccordo();
	BigDecimal idStrut = entiConvenzionatiEjb.getIdStrut(nmEnte, nmStrut);
	getForm().getGestioneAccordoDetail().getCd_registro_gest_accordo()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getRegistriStrutturaTableBean(idStrut), "cd_registro",
			"cd_registro"));

	getForm().getGestioneAccordoDetail().copyFromBean(bean);

	// Metto tutto in view mode
	getForm().getGestioneAccordoDetail().setViewMode();
	getForm().getGestioneAccordoDetail().setStatus(Status.view);
	getForm().getGestioniAccordoList().setViewMode();
	getForm().getGestioniAccordoList().setStatus(Status.view);
    }

    private void loadDettaglioModuloInformazioni(BigDecimal idModulo)
	    throws ParerUserError, EMFError {
	OrgModuloInfoAccordoRowBean bean = entiConvenzionatiEjb
		.getOrgModuloInfoAccordoRowBean(idModulo);
	// Carico la combo "Registro", va messa qui e non in initAccordoDetail perchÃ¨
	// dipende dal valore dei parametri di gestione
	String nmEnte = bean.getNmEnte();
	String nmStrut = bean.getNmStrut();
	BigDecimal idStrut = entiConvenzionatiEjb.getIdStrut(nmEnte, nmStrut);
	getForm().getModuloInformazioniDetail().getCd_registro_modulo_info()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getRegistriStrutturaTableBean(idStrut), "cd_registro",
			"cd_registro"));

	getForm().getModuloInformazioniDetail().copyFromBean(bean);
    }

    private void loadDettaglioRifReferenteEnteConvenz() throws ParerUserError, EMFError {
	BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam().parse();
	BigDecimal idAmbienteEnteConvenz = getForm().getEnteConvenzionatoDetail()
		.getId_ambiente_ente_convenz().parse();
	String[][] ambEnteStrut = entiConvenzionatiEjb
		.getAmbEnteStrutByIamParamApplic(idAmbienteEnteConvenz, idEnteConvenz);

	getForm().getReferenteEnteDetail().getCd_registro_ente_user_rif()
		.setDecodeMap(
			DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getRegistriStrutturaTableBean(
					new BigDecimal(ambEnteStrut[2][0])),
				"cd_registro", "cd_registro"));

    }

    @Override
    public void updateListaEntiConvenzionati() throws EMFError {
	BigDecimal idEnteConvenz = getForm().getListaEntiConvenzionati().getTable().getCurrentRow()
		.getBigDecimal("id_ente_convenz");
	BigDecimal idAmbienteEnteConvenz = ((OrgAmbienteEnteConvenzRowBean) entiConvenzionatiEjb
		.getOrgAmbienteEnteConvenzByEnteConvenz(idEnteConvenz)).getIdAmbienteEnteConvenz();
	getForm().getEnteConvenzionatoDetail().setEditMode();
	getForm().getEnteConvenzionatoDetail().getId_ambiente_ente_convenz().setViewMode();
	getForm().getEnteConvenzionatoDetail().getNm_ambiente_ente_convenz().setViewMode();
	getForm().getEnteConvenzionatoDetail().getDt_ini_val_appart_ambiente().setViewMode();
	getForm().getEnteConvenzionatoDetail().getDt_fin_val_appart_ambiente().setViewMode();
	getForm().getParametriAmministrazioneList().getDs_valore_param_applic_ente_amm()
		.setEditMode();
	getForm().getParametriConservazioneList().getDs_valore_param_applic_ente_cons()
		.setEditMode();
	getForm().getParametriGestioneList().getDs_valore_param_applic_ente_gest().setEditMode();
	getForm().getParametriAmministrazioneList().setHideDeleteButton(true);
	getForm().getParametriConservazioneList().setHideDeleteButton(true);
	getForm().getParametriGestioneList().setHideDeleteButton(true);

	// Parametri da gestire senza offuscamento
	try {
	    // loadListeParametriEnte(idAmbienteEnteConvenz, idEnteConvenz, null, true, true, true,
	    // true);
	    loadListaParametriAmministrazione(idAmbienteEnteConvenz, idEnteConvenz, true, true,
		    true);
	    loadListaParametriConservazione(idAmbienteEnteConvenz, idEnteConvenz, true, true, true);
	    loadListaParametriGestione(idAmbienteEnteConvenz, idEnteConvenz, true, true, true);
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
	//
	getForm().getListaEntiConvenzionati().setStatus(Status.update);
    }

    @Override
    public void updateEntiCessatiList() throws EMFError {
	updateEntiCessatiDetailList();
    }

    @Override
    public void updateEntiCessatiDetailList() throws EMFError {
	getForm().getEnteConvenzionatoDetail().setEditMode();
	getForm().getEnteConvenzionatoDetail().getNm_ente_siam().setViewMode();
	getForm().getEnteConvenzionatoDetail().getId_ambiente_ente_convenz().setViewMode();
	getForm().getEnteConvenzionatoDetail().getNm_ambiente_ente_convenz().setViewMode();
	getForm().getEntiCessatiList().setStatus(Status.update);
	getForm().getEntiCessatiDetailList().setStatus(Status.update);
    }

    @Override
    public void updateAnagraficheList() throws EMFError {
	getForm().getAnagraficaDetail().setEditMode();
	getForm().getAnagraficaDetail().getNm_ente_siam().setViewMode();
	getForm().getAnagraficheList().setStatus(Status.update);
    }

    @Override
    public void deleteListaEntiConvenzionati() throws EMFError {
	BaseRowInterface currentRow = getForm().getListaEntiConvenzionati().getTable()
		.getCurrentRow();
	BigDecimal idEnteConvenz = currentRow.getBigDecimal("id_ente_convenz");
	int riga = getForm().getListaEntiConvenzionati().getTable().getCurrentRowIndex();
	// Eseguo giusto un controllo per verificare che io stia prendendo la riga giusta se sono
	// nel dettaglio
	if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO)) {
	    if (!idEnteConvenz
		    .equals(getForm().getEnteConvenzionatoDetail().getId_ente_siam().parse())) {
		getMessageBox()
			.addError("Eccezione imprevista nell'eliminazione dell'ente convenzionato");
	    }
	}

	if (!getMessageBox().hasError() && idEnteConvenz != null) {
	    try {
		if (!getMessageBox().hasError()) {
		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    if (param.getNomePagina()
			    .equalsIgnoreCase(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
		    } else {
			param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
				getForm().getListaEntiConvenzionati()));
		    }
		    entiConvenzionatiEjb.deleteOrgEnteConvenz(param, idEnteConvenz);
		    getForm().getListaEntiConvenzionati().getTable().remove(riga);
		    getMessageBox().addInfo("Ente convenzionato eliminato con successo");
		    getMessageBox().setViewMode(ViewMode.plain);
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
	    }
	}
	if (!getMessageBox().hasError()
		&& getLastPublisher().equals(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO)) {
	    goBackTo(Application.Publisher.RICERCA_ENTI_CONVENZIONATI);
	} else {
	    forwardToPublisher(getLastPublisher());
	}
    }

    @Override
    public JSONObject triggerAmbienteEnteConvenzionatoDetailId_ente_gestoreOnTrigger()
	    throws EMFError {
	getForm().getAmbienteEnteConvenzionatoDetail().post(getRequest());
	getForm().getAmbienteEnteConvenzionatoDetail().getId_ente_conserv()
		.setDecodeMap(new DecodeMap());
	BigDecimal idEnteGestore = getForm().getAmbienteEnteConvenzionatoDetail()
		.getId_ente_gestore().parse();
	if (idEnteGestore != null) {
	    try {
		getForm().getAmbienteEnteConvenzionatoDetail().getId_ente_conserv()
			.setDecodeMap(DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getEntiConservatori(
					new BigDecimal(getUser().getIdUtente()), idEnteGestore),
				"id_ente_siam", "nm_ente_siam"));
	    } catch (ParerUserError ex) {
		getMessageBox().addError(
			"Attenzione: errore durante il popolamento della combo relativa all'ente conservatore");
	    }
	}
	return getForm().getAmbienteEnteConvenzionatoDetail().asJSON();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Gestione associazione ente convenzionato -
    // struttura versante">
    // private void saveStrutturaVersante() throws EMFError {
    // if (getForm().getStrutturaVersanteDetail().postAndValidate(getRequest(), getMessageBox())) {
    // try {
    // BigDecimal idEnteConvenz =
    // getForm().getEnteConvenzionatoDetail().getId_ente_convenz().parse();
    // BigDecimal idOrganizIam = getForm().getStrutturaVersanteDetail().getId_organiz_iam().parse();
    // Date dtIniVal = getForm().getStrutturaVersanteDetail().getDt_ini_val().parse();
    // Date dtFineVal = getForm().getStrutturaVersanteDetail().getDt_fine_val().parse();
    // if (dtFineVal.before(dtIniVal)) {
    // getMessageBox().addError("Attenzione: data fine validit\u00E0Â  inferiore a data inizio
    // validit\u00E0Â ");
    // }
    //
    // if (!getMessageBox().hasError()) {
    // // Salva l'associazione ente convenzionato / struttura versante
    // /*
    // Codice aggiuntivo per il logging...
    // */
    // LogParam param = SpagoliteLogUtil.getLogParam(paramHelper.getParamApplicApplicationName(),
    // getUser().getUsername(),
    // SpagoliteLogUtil.getPageName(this));
    // param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
    // if (getForm().getStruttureVersantiList().getStatus().equals(Status.insert)) {
    // param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
    // Long idOrgEnteConvenzOrg = entiConvenzionatiEjb.saveAssociazioneEnteStruttura(param,
    // idEnteConvenz, idOrganizIam,
    // dtIniVal, dtFineVal);
    // if (idOrgEnteConvenzOrg != null) {
    // getForm().getStrutturaVersanteDetail().getId_ente_convenz_org().setValue(idOrgEnteConvenzOrg.toString());
    // }
    // OrgEnteConvenzOrgRowBean row = new OrgEnteConvenzOrgRowBean();
    // getForm().getStrutturaVersanteDetail().copyToBean(row);
    // getForm().getStruttureVersantiList().getTable().last();
    // getForm().getStruttureVersantiList().getTable().add(row);
    // } else if (getForm().getStruttureVersantiList().getStatus().equals(Status.update)) {
    // param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
    // BigDecimal idEnteConvenzOrg =
    // getForm().getStrutturaVersanteDetail().getId_ente_convenz_org().parse();
    // entiConvenzionatiEjb.saveAssociazioneEnteStruttura(param, idEnteConvenzOrg, idEnteConvenz,
    // idOrganizIam,
    // dtIniVal, dtFineVal);
    // }
    // // Riporto tutto in view mode
    // getForm().getStrutturaVersanteDetail().setViewMode();
    // getForm().getStruttureVersantiList().setStatus(Status.view);
    // getForm().getStrutturaVersanteDetail().setStatus(Status.view);
    // getMessageBox().addInfo("Associazione ente convenzionato / struttura versante salvata con
    // successo");
    // getMessageBox().setViewMode(ViewMode.plain);
    // }
    // } catch (ParerUserError ex) {
    // getMessageBox().addError(ex.getDescription());
    // }
    // }
    // forwardToPublisher(Application.Publisher.DETTAGLIO_STRUTTURA_VERSANTE);
    // }
    // private void initStruttureVersantiComboBox(List<BigDecimal> idOrganizIamDaTogliere) {
    // BigDecimal idApplic = new BigDecimal(userHelper.getAplApplic("SACER").getIdApplic());
    // DecodeMapIF mappaAbil = comboGetter.getMappaUsrVAbilOrganiz(new
    // BigDecimal(getUser().getIdUtente()), idApplic,
    // "STRUTTURA", idOrganizIamDaTogliere);
    // getForm().getStrutturaVersanteDetail().getId_organiz_iam().setDecodeMap(mappaAbil);
    // }
    //
    // @Override
    // public void updateStruttureVersantiList() throws EMFError {
    // getForm().getStrutturaVersanteDetail().setEditMode();
    // getForm().getStrutturaVersanteDetail().getId_organiz_iam().setViewMode();
    // getForm().getStruttureVersantiList().setStatus(Status.update);
    // }
    // @Override
    // public void deleteStruttureVersantiList() throws EMFError {
    // OrgEnteConvenzOrgRowBean currentRow = (OrgEnteConvenzOrgRowBean)
    // getForm().getStruttureVersantiList().getTable().getCurrentRow();
    // BigDecimal idEnteConvenzOrg = currentRow.getIdEnteConvenzOrg();
    // BigDecimal idEnteConvenz =
    // getForm().getEnteConvenzionatoDetail().getId_ente_convenz().parse();
    // int riga = getForm().getStruttureVersantiList().getTable().getCurrentRowIndex();
    // // Eseguo giusto un controllo per verificare che io stia prendendo la riga giusta se sono nel
    // dettaglio
    // if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_STRUTTURA_VERSANTE)) {
    // if
    // (!idEnteConvenzOrg.equals(getForm().getStrutturaVersanteDetail().getId_ente_convenz_org().parse()))
    // {
    // getMessageBox().addError("Eccezione imprevista nell'eliminazione della struttura versante");
    // }
    // }
    //
    // if (!getMessageBox().hasError() && idEnteConvenzOrg != null) {
    // try {
    // /* Controlla che nella tabella ORG_SERVIZIO_EROG non siano presenti servizi giÃƒÆ’Ã‚Â 
    // inseriti in una fattura */
    // if (entiConvenzionatiEjb.checkEsistenzaServiziInFatturaPerEnteConvenz(idEnteConvenz)) {
    // getMessageBox().addError("Sull\u0027ente convenzionato sono presenti dei servizi erogati
    // gi\u00E0Â  fatturati:
    // non
    // ÃƒÆ’Ã‚Â¨
    // possibile eseguire l\u0027eliminazione dell\u0027associazione");
    // }
    // if (!getMessageBox().hasError()) {
    // /*
    // Codice aggiuntivo per il logging...
    // */
    // LogParam param = SpagoliteLogUtil.getLogParam(paramHelper.getParamApplicApplicationName(),
    // getUser().getUsername(),
    // SpagoliteLogUtil.getPageName(this));
    // param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
    // if
    // (param.getNomePagina().equalsIgnoreCase(Application.Publisher.DETTAGLIO_STRUTTURA_VERSANTE))
    // {
    // param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
    // } else {
    // param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
    // getForm().getStruttureVersantiList()));
    // }
    // entiConvenzionatiEjb.deleteOrgEnteConvenzOrg(param, idEnteConvenzOrg, idEnteConvenz);
    // getForm().getStruttureVersantiList().getTable().remove(riga);
    // getMessageBox().addInfo("Associazione struttura versante - ente convenzionato eliminata con
    // successo");
    // getMessageBox().setViewMode(ViewMode.plain);
    // loadDettaglioEnteConvenzionato(idEnteConvenz);
    // }
    // } catch (ParerUserError ex) {
    // getMessageBox().addError("L'associazione struttura versante - ente convenzionato non pu\u00F2
    // essere eliminata: "
    // +
    // ex.getDescription());
    // }
    // }
    // if (!getMessageBox().hasError() &&
    // getLastPublisher().equals(Application.Publisher.DETTAGLIO_STRUTTURA_VERSANTE))
    // {
    // goBack();
    // } else {
    // forwardToPublisher(getLastPublisher());
    // }
    // }
    //
    // @Override
    // public void deleteStrutturaVersanteDetail() throws EMFError {
    // deleteStruttureVersantiList();
    // }
    // </editor-fold>
    private void initAccordoWizardDetail() throws ParerUserError, EMFError {
	BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoWizardDetail().getId_ente_siam()
		.parse();
	BigDecimal idAmbienteEnteConvenz = getForm().getEnteConvenzionatoWizardDetail()
		.getId_ambiente_ente_convenz().parse();
	String[][] ambEnteStrut = entiConvenzionatiEjb
		.getAmbEnteStrutByIamParamApplic(idAmbienteEnteConvenz, idEnteConvenz);

	getForm().getAccordoWizardDetail().getCd_registro_repertorio()
		.setDecodeMap(
			DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getRegistriStrutturaTableBean(
					new BigDecimal(ambEnteStrut[2][0])),
				"cd_registro", "cd_registro"));
	getForm().getAccordoWizardDetail().getCd_registro_determina()
		.setDecodeMap(
			DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getRegistriStrutturaTableBean(
					new BigDecimal(ambEnteStrut[2][0])),
				"cd_registro", "cd_registro"));
	getForm().getAccordoWizardDetail().getId_tipo_accordo()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgTipoAccordoNoClasseEnteTableBean(),
			"id_tipo_accordo", "cd_tipo_accordo"));
	getForm().getAccordoWizardDetail().getTi_scopo_accordo()
		.setDecodeMap(ComboGetter.getMappaTiScopoAccordo());
	getForm().getAccordoWizardDetail().getFl_pagamento()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	getForm().getAccordoWizardDetail().getId_classe_ente_convenz()
		.setDecodeMap(new DecodeMap());
	// Popolo le combo di cluster, fascia standard e fascia manuale
	getForm().getAccordoWizardDetail().getId_cluster_accordo()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgClusterAccordoTableBean(), "id_cluster_accordo",
			"ds_cluster_accordo"));
	getForm().getAccordoWizardDetail().getId_fascia_storage_standard_accordo()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgFasciaStorageAccordoTableBean(),
			"id_fascia_storage_accordo", "ds_fascia_storage_accordo"));
	getForm().getAccordoWizardDetail().getId_fascia_storage_manuale_accordo()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgFasciaStorageAccordoTableBean(),
			"id_fascia_storage_accordo", "ds_fascia_storage_accordo"));
	// getForm().getAccordoWizardDetail().getId_cd_iva().setDecodeMap(
	// DecodeMap.Factory.newInstance(entiConvenzionatiEjb.getOrgCdIvaTableBean(), "id_cd_iva",
	// "cd_iva"));
	getForm().getAccordoWizardDetail().getFl_recesso()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	getForm().getAccordoWizardDetail().getFl_recesso().setValue("0");
	// Carico i valori precompilati
	getForm().getAccordoWizardDetail().getNm_ente().setValue(ambEnteStrut[1][1]);
	getForm().getAccordoWizardDetail().getNm_strut().setValue(ambEnteStrut[2][1]);
	// Inizializzo la lista dei tipi servizio
	getForm().getTipoServizioAccordoList().setTable(new BaseTable());
	getForm().getTipoServizioAccordoList().getTable().setPageSize(10);
	getForm().getTipoServizioAccordoList().getTable().first();
    }

    // <editor-fold defaultstate="expand" desc="Gestione dettaglio accordo">
    private void initAccordoDetail() throws ParerUserError, EMFError {
	BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam().parse();
	BigDecimal idAmbienteEnteConvenz = getForm().getEnteConvenzionatoDetail()
		.getId_ambiente_ente_convenz().parse();
	String[][] ambEnteStrut = entiConvenzionatiEjb
		.getAmbEnteStrutByIamParamApplic(idAmbienteEnteConvenz, idEnteConvenz);
	getForm().getAccordoDetail().getCd_registro_repertorio()
		.setDecodeMap(
			DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getRegistriStrutturaTableBean(
					new BigDecimal(ambEnteStrut[2][0])),
				"cd_registro", "cd_registro"));
	getForm().getAccordoDetail().getCd_registro_determina()
		.setDecodeMap(
			DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getRegistriStrutturaTableBean(
					new BigDecimal(ambEnteStrut[2][0])),
				"cd_registro", "cd_registro"));
	getForm().getAccordoDetail().getId_tipo_accordo()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgTipoAccordoNoClasseEnteTableBean(),
			"id_tipo_accordo", "cd_tipo_accordo"));
	getForm().getAccordoDetail().getTi_scopo_accordo()
		.setDecodeMap(ComboGetter.getMappaTiScopoAccordo());
	getForm().getAccordoDetail().getFl_pagamento()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	getForm().getAccordoDetail().getId_classe_ente_convenz().setDecodeMap(new DecodeMap());
	// Popolo le combo di cluster, fascia standard e fascia manuale
	getForm().getAccordoDetail().getId_cluster_accordo()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgClusterAccordoTableBean(), "id_cluster_accordo",
			"ds_cluster_accordo"));
	getForm().getAccordoDetail().getId_fascia_storage_standard_accordo()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgFasciaStorageAccordoTableBean(),
			"id_fascia_storage_accordo", "ds_fascia_storage_accordo"));
	getForm().getAccordoDetail().getId_fascia_storage_manuale_accordo()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgFasciaStorageAccordoTableBean(),
			"id_fascia_storage_accordo", "ds_fascia_storage_accordo"));
	// getForm().getAccordoDetail().getId_cd_iva().setDecodeMap(
	// DecodeMap.Factory.newInstance(entiConvenzionatiEjb.getOrgCdIvaTableBean(), "id_cd_iva",
	// "cd_iva"));
	getForm().getAccordoDetail().getFl_recesso()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	getForm().getAccordoDetail().getFl_recesso().setValue("0");
	// Carico i valori precompilati
	getForm().getAccordoDetail().getNm_ente().setValue(ambEnteStrut[1][1]);
	getForm().getAccordoDetail().getNm_strut().setValue(ambEnteStrut[2][1]);
	// Inizializzo la lista dei tipi servizio
	getForm().getTipoServizioAccordoList().setTable(new BaseTable());
	getForm().getTipoServizioAccordoList().getTable().setPageSize(10);
	getForm().getTipoServizioAccordoList().getTable().first();
	// Inizializzo la lista dei tipi servizio
	getForm().getAnnualitaAccordoList().setTable(new BaseTable());
	getForm().getAnnualitaAccordoList().getTable().setPageSize(10);
	getForm().getAnnualitaAccordoList().getTable().first();
    }

    private void initAccordoDetailFromRicerca() throws ParerUserError, EMFError {
	OrgVRicAccordoEnteRowBean rb = ((OrgVRicAccordoEnteTableBean) getForm().getListaAccordi()
		.getTable()).getCurrentRow();
	BigDecimal idEnteConvenz = rb.getIdEnteConvenz();
	BigDecimal idAmbienteEnteConvenz = rb.getIdAmbienteEnteConvenz();
	String[][] ambEnteStrut = entiConvenzionatiEjb
		.getAmbEnteStrutByIamParamApplic(idAmbienteEnteConvenz, idEnteConvenz);
	getForm().getAccordoDetail().getCd_registro_repertorio()
		.setDecodeMap(
			DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getRegistriStrutturaTableBean(
					new BigDecimal(ambEnteStrut[2][0])),
				"cd_registro", "cd_registro"));
	getForm().getAccordoDetail().getCd_registro_determina()
		.setDecodeMap(
			DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getRegistriStrutturaTableBean(
					new BigDecimal(ambEnteStrut[2][0])),
				"cd_registro", "cd_registro"));
	getForm().getAccordoDetail().getId_tipo_accordo()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgTipoAccordoNoClasseEnteTableBean(),
			"id_tipo_accordo", "cd_tipo_accordo"));
	getForm().getAccordoDetail().getTi_scopo_accordo()
		.setDecodeMap(ComboGetter.getMappaTiScopoAccordo());
	getForm().getAccordoDetail().getFl_pagamento()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	getForm().getAccordoDetail().getId_classe_ente_convenz().setDecodeMap(new DecodeMap());
	// Popolo le combo di cluster, fascia standard e fascia manuale
	getForm().getAccordoDetail().getId_cluster_accordo()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgClusterAccordoTableBean(), "id_cluster_accordo",
			"ds_cluster_accordo"));
	getForm().getAccordoDetail().getId_fascia_storage_standard_accordo()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgFasciaStorageAccordoTableBean(),
			"id_fascia_storage_accordo", "ds_fascia_storage_accordo"));
	getForm().getAccordoDetail().getId_fascia_storage_manuale_accordo()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgFasciaStorageAccordoTableBean(),
			"id_fascia_storage_accordo", "ds_fascia_storage_accordo"));
	getForm().getAccordoDetail().getFl_recesso()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	getForm().getAccordoDetail().getFl_recesso().setValue("0");
	// Carico i valori precompilati
	getForm().getAccordoDetail().getNm_ente().setValue(ambEnteStrut[1][1]);
	getForm().getAccordoDetail().getNm_strut().setValue(ambEnteStrut[2][1]);
	// Inizializzo la lista dei tipi servizio
	getForm().getTipoServizioAccordoList().setTable(new BaseTable());
	getForm().getTipoServizioAccordoList().getTable().setPageSize(10);
	getForm().getTipoServizioAccordoList().getTable().first();
	// Inizializzo la lista dei tipi servizio
	getForm().getAnnualitaAccordoList().setTable(new BaseTable());
	getForm().getAnnualitaAccordoList().getTable().setPageSize(10);
	getForm().getAnnualitaAccordoList().getTable().first();
    }

    private void initGestioneAccordoDetail() throws ParerUserError, EMFError {
	BigDecimal idAccordoEnte = getForm().getAccordoDetail().getId_accordo_ente().parse();
	int numGestAccordo = entiConvenzionatiEjb.getNumMaxGestAccordoEnte(idAccordoEnte) + 1;
	getForm().getGestioneAccordoDetail().getPg_gest_accordo().setValue("" + numGestAccordo);
	BigDecimal idAmbienteEnteConvenz = getForm().getEnteConvenzionatoDetail()
		.getId_ambiente_ente_convenz().parse();
	BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam().parse();
	getForm().getGestioneAccordoDetail().getTipo_trasmissione()
		.setDecodeMap(ComboGetter.getMappaTipoTrasmissione());
	getForm().getGestioneAccordoDetail().getId_tipo_gestione_accordo()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgTipiGestioneAccordoTableBean(),
			"id_tipo_gestione_accordo", "cd_tipo_gestione_accordo"));
	getForm().getGestioneAccordoDetail().getId_gest_accordo_risposta()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgGestioneAccordoTableBean(idAccordoEnte),
			"id_gest_accordo", "key_id_gest_accordo"));

	String[][] ambEnteStrut = entiConvenzionatiEjb
		.getAmbEnteStrutByIamParamApplic(idAmbienteEnteConvenz, idEnteConvenz);
	getForm().getGestioneAccordoDetail().getCd_registro_gest_accordo()
		.setDecodeMap(
			DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getRegistriStrutturaTableBean(
					new BigDecimal(ambEnteStrut[2][0])),
				"cd_registro", "cd_registro"));
	// Carico i valori precompilati
	getForm().getGestioneAccordoDetail().getEnte_gest_accordo().setValue(ambEnteStrut[1][1]);
	getForm().getGestioneAccordoDetail().getStruttura_gest_accordo()
		.setValue(ambEnteStrut[2][1]);

    }

    private void initModuloInformazioniDetail() throws ParerUserError, EMFError {
	BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam().parse();
	BigDecimal idAmbienteEnteConvenz = getForm().getEnteConvenzionatoDetail()
		.getId_ambiente_ente_convenz().parse();
	String[][] ambEnteStrut = entiConvenzionatiEjb
		.getAmbEnteStrutByIamParamApplic(idAmbienteEnteConvenz, idEnteConvenz);
	getForm().getModuloInformazioniDetail().getCd_registro_modulo_info()
		.setDecodeMap(
			DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getRegistriStrutturaTableBean(
					new BigDecimal(ambEnteStrut[2][0])),
				"cd_registro", "cd_registro"));
	// Carico i valori precompilati
	getForm().getModuloInformazioniDetail().getNm_ente().setValue(ambEnteStrut[1][1]);
	getForm().getModuloInformazioniDetail().getNm_strut().setValue(ambEnteStrut[2][1]);
    }

    private void initRiferimentiReferenteDetail() throws ParerUserError, EMFError {
	BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam().parse();
	BigDecimal idAmbienteEnteConvenz = getForm().getEnteConvenzionatoDetail()
		.getId_ambiente_ente_convenz().parse();
	String[][] ambEnteStrut = entiConvenzionatiEjb
		.getAmbEnteStrutByIamParamApplic(idAmbienteEnteConvenz, idEnteConvenz);
	getForm().getReferenteEnteDetail().getCd_registro_ente_user_rif()
		.setDecodeMap(
			DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getRegistriStrutturaTableBean(
					new BigDecimal(ambEnteStrut[2][0])),
				"cd_registro", "cd_registro"));
	// Carico i valori precompilati
	// getForm().getReferenteEnteDetail().getNm_ente().setValue(ambEnteStrut[1][1]);
	// getForm().getReferenteEnteDetail().getNm_strut().setValue(ambEnteStrut[2][1]);
    }

    private void initDisciplinareTecnicoDetail() throws ParerUserError, EMFError {
	BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam().parse();
	BigDecimal idAmbienteEnteConvenz = getForm().getEnteConvenzionatoDetail()
		.getId_ambiente_ente_convenz().parse();
	String[][] ambEnteStrut = entiConvenzionatiEjb
		.getAmbEnteStrutByIamParamApplic(idAmbienteEnteConvenz, idEnteConvenz);
	getForm().getDisciplinareTecnicoDetail().getCd_registro_discip_strut()
		.setDecodeMap(
			DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getRegistriStrutturaTableBean(
					new BigDecimal(ambEnteStrut[2][0])),
				"cd_registro", "cd_registro"));
	getForm().getDisciplinareTecnicoDetail().getId_organiz_iam()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgEnteConvenzOrgTableBean(idEnteConvenz),
			"id_organiz_iam", "nm_organiz_strut_completo"));

	// Carico i valori precompilati
	getForm().getDisciplinareTecnicoDetail().getEnte_discip_strut()
		.setValue(ambEnteStrut[1][1]);
	getForm().getDisciplinareTecnicoDetail().getStruttura_discip_strut()
		.setValue(ambEnteStrut[2][1]);
    }

    private void initCollegamentoDetail() throws ParerUserError {
	getForm().getCollegamentoEnteDetail().getId_ente_convenz_capofila()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgVRicEnteConvenzAbilXEnteCapofilaTableBean(
				BigDecimal.valueOf(getUser().getIdUtente()), null),
			"id_ente_convenz", "nm_ente_convenz"));
	getForm().getCollegamentoEnteDetail().getTi_colleg()
		.setDecodeMap(ComboGetter.getMappaTiColleg());
    }

    private void initCollegamentoDetail(BigDecimal idCollegEntiConvenz) throws ParerUserError {
	getForm().getCollegamentoEnteDetail().getId_ente_convenz_capofila()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgAppartCollegEntiTableBean(idCollegEntiConvenz),
			"id_ente_convenz", "nm_ente_convenz"));
	getForm().getCollegamentoEnteDetail().getTi_colleg()
		.setDecodeMap(ComboGetter.getMappaTiColleg());
    }

    @Override
    public JSONObject triggerEnteCollegatoDetailId_ambiente_ente_convenzOnTrigger()
	    throws EMFError {
	getForm().getEnteCollegatoDetail().post(getRequest());
	BigDecimal idAmbienteEnteConvenz = getForm().getEnteCollegatoDetail()
		.getId_ambiente_ente_convenz().parse();
	try {
	    if (idAmbienteEnteConvenz != null) {
		getForm().getEnteCollegatoDetail().getId_ente_convenz()
			.setDecodeMap(DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb
					.getOrgVRicEnteConvenzAbilXEnteCapofilaTableBean(
						BigDecimal.valueOf(getUser().getIdUtente()),
						idAmbienteEnteConvenz),
				"id_ente_convenz", "nm_ente_convenz"));
	    } else {
		getForm().getEnteCollegatoDetail().getId_ente_convenz()
			.setDecodeMap(new DecodeMap());
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
	return getForm().getEnteCollegatoDetail().asJSON();
    }

    @Override
    public JSONObject triggerEnteCollegatoDetailId_ente_convenzOnTrigger() throws EMFError {
	getForm().getEnteCollegatoDetail().post(getRequest());

	DateFormat formato = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH);

	BigDecimal idEnteConvenz = getForm().getEnteCollegatoDetail().getId_ente_convenz().parse();
	Date dtIniVal = getForm().getCollegamentoEnteDetail().getDt_ini_val().parse();
	Date dtFinVal = getForm().getCollegamentoEnteDetail().getDt_fin_val().parse();
	try {
	    if (idEnteConvenz != null) {
		OrgEnteSiamRowBean enteConvenz = entiConvenzionatiEjb
			.getOrgEnteConvenzRowBean(idEnteConvenz);
		if (enteConvenz.getDtIniVal().after(dtIniVal)) {
		    getForm().getEnteCollegatoDetail().getDt_ini_appart()
			    .setValue(formato.format(enteConvenz.getDtIniVal()));
		} else {
		    getForm().getEnteCollegatoDetail().getDt_ini_appart()
			    .setValue(formato.format(dtIniVal));
		}
		if (enteConvenz.getDtCessazione().before(dtFinVal)) {
		    getForm().getEnteCollegatoDetail().getDt_fin_appart()
			    .setValue(formato.format(enteConvenz.getDtCessazione()));
		} else {
		    getForm().getEnteCollegatoDetail().getDt_fin_appart()
			    .setValue(formato.format(dtFinVal));
		}
	    } else {
		getForm().getEnteCollegatoDetail().getDt_ini_appart()
			.setValue(formato.format(Calendar.getInstance().getTime()));
		Calendar c = Calendar.getInstance();
		c.set(2444, Calendar.DECEMBER, 31, 0, 0, 0);
		getForm().getEnteCollegatoDetail().getDt_fin_appart()
			.setValue(formato.format(c.getTime()));
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
	return getForm().getEnteCollegatoDetail().asJSON();
    }

    @Override
    public JSONObject triggerCollegamentoEnteAppartDetailId_colleg_enti_convenzOnTrigger()
	    throws EMFError {
	getForm().getCollegamentoEnteAppartDetail().post(getRequest());

	DateFormat formato = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH);

	BigDecimal idCollegEntiConvenz = getForm().getCollegamentoEnteAppartDetail()
		.getId_colleg_enti_convenz().parse();
	Date dtIniVal = getForm().getEnteConvenzionatoDetail().getDt_ini_val().parse();
	Date dtFinVal = getForm().getEnteConvenzionatoDetail().getDt_cessazione().parse();
	try {
	    if (idCollegEntiConvenz != null) {
		OrgCollegEntiConvenzRowBean collegEnteConvenz = entiConvenzionatiEjb
			.getOrgCollegEntiConvenzRowBean(idCollegEntiConvenz);
		if (collegEnteConvenz.getDtIniVal().after(dtIniVal)) {
		    getForm().getCollegamentoEnteAppartDetail().getDt_ini_val()
			    .setValue(formato.format(collegEnteConvenz.getDtIniVal()));
		} else {
		    getForm().getCollegamentoEnteAppartDetail().getDt_ini_val()
			    .setValue(formato.format(dtIniVal));
		}
		if (collegEnteConvenz.getDtFinVal().before(dtFinVal)) {
		    getForm().getCollegamentoEnteAppartDetail().getDt_fin_val()
			    .setValue(formato.format(collegEnteConvenz.getDtFinVal()));
		} else {
		    getForm().getCollegamentoEnteAppartDetail().getDt_fin_val()
			    .setValue(formato.format(dtFinVal));
		}
	    } else {
		getForm().getCollegamentoEnteAppartDetail().getDt_ini_val()
			.setValue(formato.format(Calendar.getInstance().getTime()));
		Calendar c = Calendar.getInstance();
		c.set(2444, Calendar.DECEMBER, 31, 0, 0, 0);
		getForm().getCollegamentoEnteAppartDetail().getDt_fin_val()
			.setValue(formato.format(c.getTime()));
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
	return getForm().getCollegamentoEnteAppartDetail().asJSON();
    }

    private void loadDettaglioAccordo(BigDecimal idAccordoEnte, BigDecimal idTipoAccordo)
	    throws ParerUserError, EMFError {
	// Carico la combo "Tariffario" in base al tipo accordo, va messa qui e non in
	// initAccordoDetail perchè
	// dipende dal valore selezionato di Tipo Accordo
	getForm().getAccordoDetail().getId_tariffario()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgTariffarioTableBean(idTipoAccordo),
			"id_tariffario", "nm_tariffario"));

	// Carico il dettaglio accordo
	OrgAccordoEnteRowBean detailRow = entiConvenzionatiEjb
		.getOrgAccordoEnteRowBean(idAccordoEnte);

	// Carico la combo "Registro", va messa qui e non in initAccordoDetail perchè dipende dal
	// valore dei parametri
	// di gestione
	String nmEnte = detailRow.getNmEnte();
	String nmStrut = detailRow.getNmStrut();
	BigDecimal idStrut = entiConvenzionatiEjb.getIdStrut(nmEnte, nmStrut);
	getForm().getAccordoDetail().getCd_registro_repertorio()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getRegistriStrutturaTableBean(idStrut), "cd_registro",
			"cd_registro"));

	getForm().getAccordoDetail().copyFromBean(detailRow);

	OrgAccordoEnteRowBean accordoEnte = entiConvenzionatiEjb
		.getOrgAccordoEnteRowBean(idAccordoEnte);
	if (accordoEnte != null) {
	    getForm().getAccordoDetail().getId_ente_convenz_gestore()
		    .setValue(accordoEnte.getIdEnteConvenzGestore().toPlainString());
	    getForm().getAccordoDetail().getNm_ente_convenz_gestore()
		    .setValue(accordoEnte.getString("nm_ente_convenz_gestore"));
	    //
	    getForm().getAccordoDetail().getId_ente_convenz_conserv()
		    .setValue(accordoEnte.getIdEnteConvenzConserv().toPlainString());
	    getForm().getAccordoDetail().getNm_ente_convenz_conserv()
		    .setValue(accordoEnte.getString("nm_ente_convenz_conserv"));
	    //
	    getForm().getAccordoDetail().getId_ente_convenz_amministratore()
		    .setValue(accordoEnte.getIdEnteConvenzAmministratore().toPlainString());
	    getForm().getAccordoDetail().getNm_ente_convenz_amministratore()
		    .setValue(accordoEnte.getString("nm_ente_convenz_amministratore"));
	}
	//
	// Metto tutto in view mode
	getForm().getAccordoDetail().setViewMode();
	// Campo id accordo sempre non editabile
	getForm().getAccordoDetail().getId_accordo_ente().setHidden(false);
	getForm().getAccordoDetail().setStatus(Status.view);
	getForm().getTipoServizioAccordoList().getIm_tariffa_accordo().setViewMode();

	if (getForm().getAccordiList() != null) {
	    getForm().getAccordiList().setStatus(Status.view);
	}
	if (getForm().getListaAccordi() != null) {
	    getForm().getListaAccordi().setStatus(Status.view);
	}

	// Bottone di ricalcolo
	getForm().getAccordoDetail().getRicalcoloServiziErogati().setEditMode();
	getForm().getAccordoDetail().getRicalcoloServiziErogati().setHidden(false);

	getForm().getAccordoDetail().getId_ente_convenz_gestore().setHidden(true);
	getForm().getAccordoDetail().getId_ente_convenz_conserv().setHidden(true);
	getForm().getAccordoDetail().getId_ente_convenz_amministratore().setHidden(true);

	BigDecimal idTariffario = getForm().getAccordoDetail().getId_tariffario().parse();
	// Popolamento lista tipi servizio
	getForm().getTipoServizioAccordoList()
		.setTable(entiConvenzionatiEjb.getTipiServizioImporto(idTariffario, idAccordoEnte));
	getForm().getTipoServizioAccordoList().getTable().setPageSize(10);
	getForm().getTipoServizioAccordoList().getTable().first();

	// Popolamento lista annualità
	getForm().getAnnualitaAccordoSection().setHidden(false);
	getForm().getAnnualitaAccordoList().setTable(
		entiConvenzionatiEjb.getOrgAaAccordoEnteTableBean(idTariffario, idAccordoEnte));
	getForm().getAnnualitaAccordoList().getTable().setPageSize(10);
	getForm().getAnnualitaAccordoList().getTable().first();
	getForm().getAnnualitaAccordoList().setHideInsertButton(true);
	if (entiConvenzionatiEjb.checkInsertAnnualita(
		getForm().getAccordoDetail().getFl_pagamento().parse(),
		detailRow.getIdTipoAccordo(),
		getForm().getAccordoDetail().getDt_scad_accordo().parse())) {
	    getForm().getAnnualitaAccordoList().setHideInsertButton(false);
	}

	// Visualizzazione delle sezioni di storage occupato
	try {
	    // PRODUTTORE
	    BaseRow occupStorage = entiConvenzionatiEjb.getOccupStorageAccordo(idAccordoEnte,
		    detailRow.getIdFasciaStorageStandardAccordo());
	    if (occupStorage != null) {
		getForm().getMediaAnnualeOccupazioneStorageSection().setHidden(false);
		getForm().getMediaAnnualeOccupazioneStorageNoDispSection().setHidden(true);
		// Popolo la sezione
		getForm().getAccordoDetail().getStorage_occupato()
			.setValue("" + occupStorage.getBigDecimal("storage_occupato"));
		String datoCalcolatoDalString = new SimpleDateFormat("dd/MM/yyyy")
			.format(occupStorage.getTimestamp("dato_calcolato_dal"));
		String datoCalcolatoAlString = new SimpleDateFormat("dd/MM/yyyy")
			.format(occupStorage.getTimestamp("dato_calcolato_al"));
		getForm().getAccordoDetail().getDato_calcolato_dal()
			.setValue(datoCalcolatoDalString);
		getForm().getAccordoDetail().getDato_calcolato_al().setValue(datoCalcolatoAlString);
		getForm().getAccordoDetail().getFascia_da_accordo()
			.setValue("" + occupStorage.getString("fascia_da_accordo"));
		getForm().getAccordoDetail().getFascia_da_occupazione()
			.setValue("" + occupStorage.getString("fascia_da_occupazione"));
		getForm().getAccordoDetail().getColore_fascia()
			.setValue("" + occupStorage.getString("colore_fascia"));
	    } else {
		Date dtDecAccordo = getForm().getAccordoDetail().getDt_dec_accordo().parse();
		setMessaggioSezioneStorage(idAccordoEnte);
	    }

	    // GESTORE/CONSERVATORE
	    String tipoEnte = getForm().getEnteConvenzionatoDetail().getTi_ente_convenz().parse();
	    if (tipoEnte.equals(ConstOrgEnteSiam.TiEnteConvenz.GESTORE.name())
		    || tipoEnte.equals(ConstOrgEnteSiam.TiEnteConvenz.CONSERVATORE.name())) {
		Date dataDecorrenza = new Date(
			getForm().getAccordoDetail().getDt_dec_accordo().parse().getTime());
		Object[] occupStorageGestore = entiConvenzionatiEjb.getOccupStorageGestore(
			dataDecorrenza,
			getForm().getAccordoDetail().getId_ente_convenz_gestore().parse());

		getForm().getMediaAnnualeOccupazioneStorageGestoreSection().setHidden(false);
		getForm().getMediaAnnualeOccupazioneStorageNoDispSection().setHidden(true);
		// Popolo la sezione
		getForm().getAccordoDetail().getStorage_occupato_gestore()
			.setValue("" + (BigDecimal) occupStorageGestore[1]);
		String datoCalcolatoDalString = new SimpleDateFormat("dd/MM/yyyy")
			.format(getForm().getAccordoDetail().getDt_dec_accordo().parse());
		String datoCalcolatoAlString = new SimpleDateFormat("dd/MM/yyyy")
			.format((Date) occupStorageGestore[0]);
		getForm().getAccordoDetail().getDato_calcolato_dal_gestore()
			.setValue(datoCalcolatoDalString);
		getForm().getAccordoDetail().getDato_calcolato_al_gestore()
			.setValue(datoCalcolatoAlString);
	    }

	} catch (ParerUserError e) {
	    getMessageBox()
		    .addError(e.getDescription() + ": impossibile caricare il dettaglio accordo");
	}

	// Popolamento lista gestioni accordo
	getForm().getGestioniAccordoList()
		.setTable(entiConvenzionatiEjb.getOrgGestioneAccordoTableBean(idAccordoEnte));
	getForm().getGestioniAccordoList().getTable().setPageSize(10);
	getForm().getGestioniAccordoList().getTable().first();

	// Popolamento lista servizi erogati
	getForm().getServiziErogatiList()
		.setTable(entiConvenzionatiEjb.getOrgServizioErogTableBean(idAccordoEnte));
	getForm().getServiziErogatiList().getTable().setPageSize(10);
	getForm().getServiziErogatiList().getTable().first();

	// Popolamento lista fatture accordo
	getForm().getFattureAccordoList()
		.setTable(entiConvenzionatiEjb.getOrgVRicFattureTableBean(idAccordoEnte));
	getForm().getFattureAccordoList().getTable().setPageSize(10);
	getForm().getFattureAccordoList().getTable().first();
    }

    private void setMessaggioSezioneStorage(BigDecimal idAccordoEnte) {
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	getForm().getMediaAnnualeOccupazioneStorageSection().setHidden(true);
	getForm().getMediaAnnualeOccupazioneStorageGestoreSection().setHidden(true);
	getForm().getMediaAnnualeOccupazioneStorageNoDispSection().setHidden(false);
	// MEV #37034
	Date dataUltimoVersamento = entiConvenzionatiHelper
		.getDataUltimoVersamentoAccordo(idAccordoEnte);
	StringBuilder messaggio = new StringBuilder("MEDIA OCCUPAZIONE NON DISPONIBILE - ");
	// Verifico che la data di ultimo versamento sia compresa nella validità dell'accordo
	if (dataUltimoVersamento == null) {
	    messaggio.append("NON ESISTONO VERSAMENTI");
	} else {
	    String dataFormattata = formatter.format(dataUltimoVersamento);
	    messaggio.append("ULTIMO VERSAMENTO EFFETTUATO IL ").append(dataFormattata);
	    getForm().getAccordoDetail().getOccupazione_no_disp().setValue(messaggio.toString());
	}
    }

    private void loadDettaglioAnnualitaAccordo(BigDecimal idAaAccordo) throws EMFError {
	// Carico il dettaglio collegamento
	OrgAaAccordoRowBean detailRow = entiConvenzionatiEjb.getOrgAaAccordoRowBean(idAaAccordo);
	getForm().getAnnualitaAccordoDetail().copyFromBean(detailRow);

	// Metto tutto in view mode
	getForm().getAnnualitaAccordoDetail().setViewMode();
	getForm().getAnnualitaAccordoDetail().setStatus(Status.view);
	getForm().getAnnualitaAccordoList().setStatus(Status.view);
	getForm().getTipoServizioAnnualitaAccordoList().getIm_tariffa_aa_accordo().setViewMode();

	// Popolamento lista dei tipi servizio
	BigDecimal idTariffario = getForm().getAccordoDetail().getId_tariffario().parse();
	getForm().getTipoServizioAnnualitaAccordoList().setTable(
		entiConvenzionatiEjb.getTipiServizioAnnualitaImporto(idTariffario, idAaAccordo));
	getForm().getTipoServizioAnnualitaAccordoList().getTable().setPageSize(10);
	getForm().getTipoServizioAnnualitaAccordoList().getTable().first();
    }

    private void loadDettaglioCollegamento(BigDecimal idCollegEntiConvenz)
	    throws ParerUserError, EMFError {
	// Carico il dettaglio collegamento
	OrgCollegEntiConvenzRowBean detailRow = entiConvenzionatiEjb
		.getOrgCollegEntiConvenzRowBean(idCollegEntiConvenz);
	getForm().getCollegamentoEnteDetail().copyFromBean(detailRow);

	// Metto tutto in view mode
	getForm().getCollegamentoEnteDetail().setViewMode();
	getForm().getCollegamentoEnteDetail().setStatus(Status.view);
	getForm().getCollegamentiEnteList().setStatus(Status.view);

	// Popolamento lista enti associati al collegamento
	getForm().getEntiCollegatiList().setTable(
		entiConvenzionatiEjb.getOrgAppartCollegEntiTableBean(idCollegEntiConvenz));
	getForm().getEntiCollegatiList().getTable().setPageSize(10);
	getForm().getEntiCollegatiList().getTable().first();
    }

    private void loadDettaglioAppartenenzaColleg(BigDecimal idAppartCollegEnti)
	    throws ParerUserError, EMFError {
	// Carico il dettaglio dell'appartenenza al collegamento
	OrgAppartCollegEntiRowBean detailRow = entiConvenzionatiEjb
		.getOrgAppartCollegEntiRowBean(idAppartCollegEnti);
	getForm().getCollegamentoEnteAppartDetail().copyFromBean(detailRow);

	// Metto tutto in view mode
	getForm().getCollegamentoEnteAppartDetail().setViewMode();
	getForm().getCollegamentoEnteAppartDetail().setStatus(Status.view);
	getForm().getCollegamentiEnteAppartList().setStatus(Status.view);

	// Popolamento lista enti associati al collegamento
	getForm().getEntiCollegatiAppartList()
		.setTable(entiConvenzionatiEjb.getOrgAppartCollegEntiTableBean(getForm()
			.getCollegamentoEnteAppartDetail().getId_colleg_enti_convenz().parse()));
	getForm().getEntiCollegatiAppartList().getTable().setPageSize(10);
	getForm().getEntiCollegatiAppartList().getTable().first();
    }

    public void triggerAccordoDetailFl_acc_non_approvOnTriggerJs() throws EMFError {
	getForm().getAccordoDetail().post(getRequest());
	String flAccNonApprov = (String) getRequest()
		.getParameter(getForm().getAccordoDetail().getFl_acc_non_approv().getName());
	if (flAccNonApprov == null) { // not checked
	    // I seguenti campi sono automaticamente puliti e tornano ad essere editabili

	    // SECTION: ID Accordo
	    if (getForm().getAccordoDetail().getCd_registro_repertorio().isEditMode()) {
		getForm().getAccordoDetail().getCd_registro_repertorio().setValue("");
		getForm().getAccordoDetail().getCd_registro_repertorio().setReadonly(false);
	    }
	    //
	    if (getForm().getAccordoDetail().getAa_repertorio().isEditMode()) {
		getForm().getAccordoDetail().getAa_repertorio().setValue("");
		getForm().getAccordoDetail().getAa_repertorio().setReadonly(false);
	    }
	    //
	    if (getForm().getAccordoDetail().getCd_key_repertorio().isEditMode()) {
		getForm().getAccordoDetail().getCd_key_repertorio().setValue("");
		getForm().getAccordoDetail().getCd_key_repertorio().setReadonly(false);
	    }
	    //
	    if (getForm().getAccordoDetail().getDt_reg_accordo().isEditMode()) {
		getForm().getAccordoDetail().getDt_reg_accordo().setValue("");
		getForm().getAccordoDetail().getDt_reg_accordo().setReadonly(false);
	    }
	    // end SECTION

	    // SECTION: Informazioni sull'accordo
	    if (getForm().getAccordoDetail().getDt_atto_accordo().isEditMode()) {
		getForm().getAccordoDetail().getDt_atto_accordo().setValue("");
		getForm().getAccordoDetail().getDt_atto_accordo().setReadonly(false);
	    }
	    //
	    if (getForm().getAccordoDetail().getDt_dec_accordo().isEditMode()) {
		getForm().getAccordoDetail().getDt_dec_accordo().setValue("");
		getForm().getAccordoDetail().getDt_dec_accordo().setReadonly(false);
	    }
	    //
	    if (getForm().getAccordoDetail().getDt_fine_valid_accordo().isEditMode()) {
		getForm().getAccordoDetail().getDt_fine_valid_accordo().setValue("");
		getForm().getAccordoDetail().getDt_fine_valid_accordo().setReadonly(false);
	    }
	    //
	    if (getForm().getAccordoDetail().getDt_scad_accordo().isEditMode()) {
		getForm().getAccordoDetail().getDt_scad_accordo().setValue("");
		getForm().getAccordoDetail().getDt_scad_accordo().setReadonly(false);
	    }
	    //
	    if (getForm().getAccordoDetail().getDs_atto_accordo().isEditMode()) {
		getForm().getAccordoDetail().getDs_atto_accordo().setValue("");
		getForm().getAccordoDetail().getDs_atto_accordo().setReadonly(false);
	    }
	    // end SECTION
	} else {
	    // Inizializzo i valori da settare nei campi in questione, il sistema in automatico
	    // compila i seguenti
	    // campi, rendendoli NON modificabili

	    // SECTION: ID Accordo
	    if (getForm().getAccordoDetail().getCd_registro_repertorio().isEditMode()) {
		getForm().getAccordoDetail().getCd_registro_repertorio().setValue(null);
		getForm().getAccordoDetail().getCd_registro_repertorio().setReadonly(true);
	    }
	    //
	    if (getForm().getAccordoDetail().getAa_repertorio().isEditMode()) {
		getForm().getAccordoDetail().getAa_repertorio().setValue(null);
		getForm().getAccordoDetail().getAa_repertorio().setReadonly(true);
	    }
	    //
	    if (getForm().getAccordoDetail().getCd_key_repertorio().isEditMode()) {
		getForm().getAccordoDetail().getCd_key_repertorio().setValue(null);
		getForm().getAccordoDetail().getCd_key_repertorio().setReadonly(true);
	    }
	    //
	    if (getForm().getAccordoDetail().getDt_reg_accordo().isEditMode()) {
		getForm().getAccordoDetail().getDt_reg_accordo().setValue(null);
		getForm().getAccordoDetail().getDt_reg_accordo().setReadonly(true);
	    }
	    // end SECTION

	    // SECTION: Informazioni sull'accordo
	    DateFormat formato = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH);
	    Calendar cal = Calendar.getInstance();
	    cal.set(1900, Calendar.JANUARY, 01);
	    if (getForm().getAccordoDetail().getDt_atto_accordo().isEditMode()) {
		getForm().getAccordoDetail().getDt_atto_accordo()
			.setValue(formato.format(cal.getTime()));
		getForm().getAccordoDetail().getDt_atto_accordo().setReadonly(true);
	    }
	    //
	    if (getForm().getAccordoDetail().getDt_dec_accordo().isEditMode()) {
		cal.set(2444, Calendar.DECEMBER, 31);
		getForm().getAccordoDetail().getDt_dec_accordo()
			.setValue(formato.format(cal.getTime()));
		getForm().getAccordoDetail().getDt_dec_accordo().setReadonly(true);
	    }
	    //
	    if (getForm().getAccordoDetail().getDt_fine_valid_accordo().isEditMode()) {
		getForm().getAccordoDetail().getDt_fine_valid_accordo()
			.setValue(formato.format(cal.getTime()));
		getForm().getAccordoDetail().getDt_fine_valid_accordo().setReadonly(true);
	    }
	    //
	    if (getForm().getAccordoDetail().getDt_scad_accordo().isEditMode()) {
		// Data di scadenza: nulla
		getForm().getAccordoDetail().getDt_scad_accordo().setValue(null);
		getForm().getAccordoDetail().getDt_scad_accordo().setReadonly(true);
	    }
	    //
	    if (getForm().getAccordoDetail().getDs_atto_accordo().isEditMode()) {
		getForm().getAccordoDetail().getDs_atto_accordo().setValue("Da definire");
		getForm().getAccordoDetail().getDs_atto_accordo().setReadonly(true);
	    }
	    // end SECTION
	}
	redirectToAjax(getForm().getAccordoDetail().asJSON());
    }

    public void triggerAccordoWizardDetailFl_acc_non_approvOnTriggerJs() throws EMFError {
	getForm().getAccordoWizardDetail().post(getRequest());
	String flAccNonApprov = (String) getRequest()
		.getParameter(getForm().getAccordoWizardDetail().getFl_acc_non_approv().getName());
	if (flAccNonApprov == null) { // not checked
	    // I seguenti campi sono automaticamente puliti e tornano ad essere editabili

	    // SECTION: ID Accordo
	    if (getForm().getAccordoWizardDetail().getCd_registro_repertorio().isEditMode()) {
		getForm().getAccordoWizardDetail().getCd_registro_repertorio().setValue("");
		getForm().getAccordoWizardDetail().getCd_registro_repertorio().setReadonly(false);
	    }
	    //
	    if (getForm().getAccordoWizardDetail().getAa_repertorio().isEditMode()) {
		getForm().getAccordoWizardDetail().getAa_repertorio().setValue("");
		getForm().getAccordoWizardDetail().getAa_repertorio().setReadonly(false);
	    }
	    //
	    if (getForm().getAccordoWizardDetail().getCd_key_repertorio().isEditMode()) {
		getForm().getAccordoWizardDetail().getCd_key_repertorio().setValue("");
		getForm().getAccordoWizardDetail().getCd_key_repertorio().setReadonly(false);
	    }
	    //
	    if (getForm().getAccordoWizardDetail().getDt_reg_accordo().isEditMode()) {
		getForm().getAccordoWizardDetail().getDt_reg_accordo().setValue("");
		getForm().getAccordoWizardDetail().getDt_reg_accordo().setReadonly(false);
	    }
	    if (getForm().getAccordoWizardDetail().getFl_acc_non_approv().isEditMode()) {
		getForm().getAccordoWizardDetail().getFl_acc_non_approv().setValue("");
		getForm().getAccordoWizardDetail().getFl_acc_non_approv().setReadonly(false);
	    }
	    // end SECTION

	    // SECTION: Informazioni sull'accordo
	    if (getForm().getAccordoWizardDetail().getDt_atto_accordo().isEditMode()) {
		getForm().getAccordoWizardDetail().getDt_atto_accordo().setValue("");
		getForm().getAccordoWizardDetail().getDt_atto_accordo().setReadonly(false);
	    }
	    //
	    if (getForm().getAccordoWizardDetail().getDt_dec_accordo().isEditMode()) {
		getForm().getAccordoWizardDetail().getDt_dec_accordo().setValue("");
		getForm().getAccordoWizardDetail().getDt_dec_accordo().setReadonly(false);
	    }
	    //
	    if (getForm().getAccordoWizardDetail().getDt_fine_valid_accordo().isEditMode()) {
		getForm().getAccordoWizardDetail().getDt_fine_valid_accordo().setValue("");
		getForm().getAccordoWizardDetail().getDt_fine_valid_accordo().setReadonly(false);
	    }
	    //
	    if (getForm().getAccordoWizardDetail().getDt_scad_accordo().isEditMode()) {
		getForm().getAccordoWizardDetail().getDt_scad_accordo().setValue("");
		getForm().getAccordoWizardDetail().getDt_scad_accordo().setReadonly(false);
	    }
	    //
	    if (getForm().getAccordoWizardDetail().getDs_atto_accordo().isEditMode()) {
		getForm().getAccordoWizardDetail().getDs_atto_accordo().setValue("");
		getForm().getAccordoWizardDetail().getDs_atto_accordo().setReadonly(false);
	    }
	    // end SECTION
	} else {
	    // Inizializzo i valori da settare nei campi in questione, il sistema in automatico
	    // compila i seguenti
	    // campi, rendendoli NON modificabili

	    // SECTION: ID Accordo
	    if (getForm().getAccordoWizardDetail().getCd_registro_repertorio().isEditMode()) {
		getForm().getAccordoWizardDetail().getCd_registro_repertorio().setValue(null);
		getForm().getAccordoWizardDetail().getCd_registro_repertorio().setReadonly(true);
	    }
	    //
	    if (getForm().getAccordoWizardDetail().getAa_repertorio().isEditMode()) {
		getForm().getAccordoWizardDetail().getAa_repertorio().setValue(null);
		getForm().getAccordoWizardDetail().getAa_repertorio().setReadonly(true);
	    }
	    //
	    if (getForm().getAccordoWizardDetail().getCd_key_repertorio().isEditMode()) {
		getForm().getAccordoWizardDetail().getCd_key_repertorio().setValue(null);
		getForm().getAccordoWizardDetail().getCd_key_repertorio().setReadonly(true);
	    }
	    //
	    if (getForm().getAccordoWizardDetail().getDt_reg_accordo().isEditMode()) {
		getForm().getAccordoWizardDetail().getDt_reg_accordo().setValue(null);
		getForm().getAccordoWizardDetail().getDt_reg_accordo().setReadonly(true);
	    }
	    if (getForm().getAccordoWizardDetail().getFl_acc_non_approv().isEditMode()) {
		getForm().getAccordoWizardDetail().getFl_acc_non_approv().setValue("");
		getForm().getAccordoWizardDetail().getFl_acc_non_approv().setReadonly(false);
	    }
	    // end SECTION

	    // SECTION: Informazioni sull'accordo
	    DateFormat formato = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH);
	    Calendar cal = Calendar.getInstance();
	    cal.set(1900, Calendar.JANUARY, 01);
	    if (getForm().getAccordoWizardDetail().getDt_atto_accordo().isEditMode()) {
		getForm().getAccordoWizardDetail().getDt_atto_accordo()
			.setValue(formato.format(cal.getTime()));
		getForm().getAccordoWizardDetail().getDt_atto_accordo().setReadonly(true);
	    }
	    //
	    if (getForm().getAccordoWizardDetail().getDt_dec_accordo().isEditMode()) {
		cal.set(2444, Calendar.DECEMBER, 31);
		getForm().getAccordoWizardDetail().getDt_dec_accordo()
			.setValue(formato.format(cal.getTime()));
		getForm().getAccordoWizardDetail().getDt_dec_accordo().setReadonly(true);
	    }
	    //
	    if (getForm().getAccordoWizardDetail().getDt_fine_valid_accordo().isEditMode()) {
		getForm().getAccordoWizardDetail().getDt_fine_valid_accordo()
			.setValue(formato.format(cal.getTime()));
		getForm().getAccordoWizardDetail().getDt_fine_valid_accordo().setReadonly(true);
	    }
	    //
	    if (getForm().getAccordoWizardDetail().getDt_scad_accordo().isEditMode()) {
		// Data di scadenza: nulla
		getForm().getAccordoWizardDetail().getDt_scad_accordo().setValue(null);
		getForm().getAccordoWizardDetail().getDt_scad_accordo().setReadonly(true);
	    }
	    //
	    if (getForm().getAccordoWizardDetail().getDs_atto_accordo().isEditMode()) {
		getForm().getAccordoWizardDetail().getDs_atto_accordo().setValue("Da definire");
		getForm().getAccordoWizardDetail().getDs_atto_accordo().setReadonly(true);
	    }
	    // end SECTION
	}
	redirectToAjax(getForm().getAccordoWizardDetail().asJSON());
    }

    @Override
    public JSONObject triggerAccordoDetailId_tipo_accordoOnTrigger() throws EMFError {
	getForm().getAccordoDetail().post(getRequest());
	getForm().getAccordoDetail().getId_tariffario().setDecodeMap(new DecodeMap());
	BigDecimal idTipoAccordo = getForm().getAccordoDetail().getId_tipo_accordo().parse();

	if (idTipoAccordo != null) {
	    try {
		OrgTipoAccordoRowBean tipoAccordoRowBean = entiConvenzionatiEjb
			.getOrgTipoAccordoRowBean(idTipoAccordo);

		getForm().getAccordoDetail().getId_tariffario()
			.setDecodeMap(DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getOrgTariffarioTableBean(idTipoAccordo),
				"id_tariffario", "nm_tariffario"));
		getForm().getAccordoDetail().getFl_pagamento()
			.setValue(tipoAccordoRowBean.getFlPagamento());
		if (tipoAccordoRowBean.getCdAlgoTariffario().equals("CLASSE_ENTE")) {
		    getForm().getAccordoDetail().getId_classe_ente_convenz()
			    .setDecodeMap(DecodeMap.Factory.newInstance(
				    entiConvenzionatiEjb.getOrgClasseEnteConvenzTableBean(),
				    "id_classe_ente_convenz", "cd_classe_ente_convenz"));

		} else {
		    getForm().getAccordoDetail().getId_classe_ente_convenz()
			    .setDecodeMap(new DecodeMap());
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(
			"Attenzione: errore durante il popolamento della combo relativa al tariffario");
	    }
	}
	return getForm().getAccordoDetail().asJSON();
    }

    @Override
    public JSONObject triggerAccordoWizardDetailId_tipo_accordoOnTrigger() throws EMFError {
	getForm().getAccordoWizardDetail().post(getRequest());
	getForm().getAccordoWizardDetail().getId_tariffario().setDecodeMap(new DecodeMap());
	BigDecimal idTipoAccordo = getForm().getAccordoWizardDetail().getId_tipo_accordo().parse();

	if (idTipoAccordo != null) {
	    try {
		OrgTipoAccordoRowBean tipoAccordoRowBean = entiConvenzionatiEjb
			.getOrgTipoAccordoRowBean(idTipoAccordo);

		getForm().getAccordoWizardDetail().getId_tariffario()
			.setDecodeMap(DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getOrgTariffarioTableBean(idTipoAccordo),
				"id_tariffario", "nm_tariffario"));
		getForm().getAccordoWizardDetail().getFl_pagamento()
			.setValue(tipoAccordoRowBean.getFlPagamento());
		if (tipoAccordoRowBean.getCdAlgoTariffario().equals("CLASSE_ENTE")) {
		    getForm().getAccordoWizardDetail().getId_classe_ente_convenz()
			    .setDecodeMap(DecodeMap.Factory.newInstance(
				    entiConvenzionatiEjb.getOrgClasseEnteConvenzTableBean(),
				    "id_classe_ente_convenz", "cd_classe_ente_convenz"));

		} else {
		    getForm().getAccordoWizardDetail().getId_classe_ente_convenz()
			    .setDecodeMap(new DecodeMap());
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(
			"Attenzione: errore durante il popolamento della combo relativa al tariffario");
	    }
	}
	return getForm().getAccordoWizardDetail().asJSON();
    }

    private void saveAccordo() throws EMFError {
	// Se il flag è "checked" ripristino i campi che sono stati messi in stato "readonly" dal
	// trigger.
	// I suddetti campi devono essere gestiti dal framework come editabili e non in sola
	// lettura.
	String flAccNonApprov = (String) getRequest()
		.getParameter(getForm().getAccordoDetail().getFl_acc_non_approv().getName());
	if (flAccNonApprov != null) { // checked
	    // SECTION: ID Accordo
	    getForm().getAccordoDetail().getCd_registro_repertorio().setReadonly(false);
	    getForm().getAccordoDetail().getAa_repertorio().setReadonly(false);
	    getForm().getAccordoDetail().getCd_key_repertorio().setReadonly(false);
	    getForm().getAccordoDetail().getDt_reg_accordo().setReadonly(false);
	    // end SECTION

	    // SECTION: Informazioni sull'accordo
	    getForm().getAccordoDetail().getDt_atto_accordo().setReadonly(false);
	    getForm().getAccordoDetail().getDt_dec_accordo().setReadonly(false);
	    getForm().getAccordoDetail().getDt_fine_valid_accordo().setReadonly(false);
	    getForm().getAccordoDetail().getDt_scad_accordo().setReadonly(false);
	    getForm().getAccordoDetail().getDs_atto_accordo().setReadonly(false);
	    // end SECTION
	}

	if (getForm().getAccordoDetail().postAndValidate(getRequest(), getMessageBox())) {
	    // Ricavo la data di cessazione dell'ente convenzionato
	    Date dtCessazione = getForm().getEnteConvenzionatoDetail().getDt_cessazione().parse();
	    // Ricavo la data di decorrenza dell'accordo
	    Date dtDecAccordoInfo = getForm().getAccordoDetail().getDt_dec_accordo_info().parse();
	    // Ricavo la data di scadenza dell'accordo
	    Date dtScadAccordo = getForm().getAccordoDetail().getDt_scad_accordo().parse();
	    // Ricavo la data di inizio validità dell'accordo
	    Date dtDecAccordo = getForm().getAccordoDetail().getDt_dec_accordo().parse();
	    // Ricavo la data di fine validità dell'accordo
	    Date dtFineValidAccordo = getForm().getAccordoDetail().getDt_fine_valid_accordo()
		    .parse();
	    // Ricavo la data di richiesta del modulo di informazioni
	    // Date dtRichModuloInfo =
	    // getForm().getAccordoDetail().getDt_rich_modulo_info().parse();
	    // Ricavo la data di approvazione accordo
	    Date dtAttoAccordo = getForm().getAccordoDetail().getDt_atto_accordo().parse();
	    // Ricavo l'atto di approvazione accordo
	    String dsAttoAccordo = getForm().getAccordoDetail().getDs_atto_accordo().parse();
	    // Ricavo il registro
	    String cdRegistroRepertorio = getForm().getAccordoDetail().getCd_registro_repertorio()
		    .parse();
	    // Ricavo l'anno
	    BigDecimal anno = getForm().getAccordoDetail().getAa_repertorio().parse();
	    // Ricavo il numero
	    String numero = getForm().getAccordoDetail().getCd_key_repertorio().parse();
	    // Ricavo la data di registrazione accordo
	    Date dtRegAccordo = getForm().getAccordoDetail().getDt_reg_accordo().parse();

	    getForm().getTipoServizioAccordoList().post(getRequest());

	    // Controllo lato online sulle date
	    if (dtScadAccordo != null && dtDecAccordoInfo != null
		    && dtScadAccordo.before(dtDecAccordoInfo)) {
		getMessageBox().addError(
			"Attenzione: data di scadenza accordo inferiore alla data di decorrenza");
	    }
	    if (dtFineValidAccordo != null && dtDecAccordo != null
		    && dtFineValidAccordo.before(dtDecAccordo)) {
		getMessageBox().addError(
			"Attenzione: data di fine validit\u00E0 accordo inferiore alla data di inizio validità");
	    }
	    if (dtDecAccordo != null && dtCessazione != null && dtDecAccordo.after(dtCessazione)) {
		getMessageBox().addError(
			"Attenzione: la data di inizio validità non pu\u00F2 essere superiore alla data di fine validit\u00E0 dell\u0027ente convenzionato");
	    }
	    if (dtFineValidAccordo != null && dtCessazione != null
		    && dtFineValidAccordo.after(dtCessazione)) {
		getMessageBox().addError(
			"Attenzione: la data di fine validit\u00E0 dell'accordo non pu\u00F2 essere superiore alla data di fine validit\u00E0 dell\u0027ente convenzionato");
	    }
	    if (dtRegAccordo != null) {
		if (cdRegistroRepertorio == null || anno == null || numero == null) {
		    getMessageBox().addError(
			    "Attenzione: in caso di registrazione dell\u0027accordo \u00E8 obbligatorio indicare tutti i campi presenti nella sezione ID Accordo");
		}
		if (dtScadAccordo == null) {
		    getMessageBox().addError(
			    "Attenzione: in caso di registrazione dell\u0027accordo \u00E8 obbligatorio indicare la data di scadenza");
		}
	    } else {
		if (dtAttoAccordo == null || dsAttoAccordo == null) {
		    getMessageBox().addError(
			    "Attenzione: data di approvazione e atto di approvazione sono obbligatori se l\u0027ente \u00E8 creato senza accordo firmato");
		}
		if ((dtDecAccordo != null && !DateUtils.truncate(dtDecAccordo, Calendar.DATE)
			.equals(DateUtil.MAX_DATE))
			|| (dtFineValidAccordo != null
				&& !DateUtils.truncate(dtFineValidAccordo, Calendar.DATE)
					.equals(DateUtil.MAX_DATE))) {
		    getMessageBox().addError(
			    "Attenzione: data di inizio validità e data di fine validit\u00E0Â  devono essere valorizzati con 31/12/2444 se l\u0027ente \u00E8 creato senza accordo firmato");
		}
	    }

	    BigDecimal idAmbienteEnteConvenzCambio = getForm().getEnteConvenzionatoDetail()
		    .getId_ambiente_ente_convenz_cambio().parse();
	    // Verifico la selezione dell'ambiente in caso di cambio gestore
	    if (getSession().getAttribute("cambioGestore") != null
		    && idAmbienteEnteConvenzCambio == null) {
		getMessageBox().addError("Attenzione: campo 'Ambiente' obbligatorio");
	    }

	    try {
		if (!getMessageBox().hasError()) {
		    BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail()
			    .getId_ente_siam().parse();
		    // Ricavo le strutture di questo ente convenzionato
		    OrgVEnteConvenzByOrganizTableBean struttureVersanti = entiConvenzionatiEjb
			    .getOrgVEnteConvenzByOrganizTableBean(idEnteConvenz, true);
		    List<BigDecimal> idStrutList = new ArrayList<>();
		    for (OrgVEnteConvenzByOrganizRowBean strutturaVersante : struttureVersanti) {
			idStrutList.add(strutturaVersante.getBigDecimal("id_organiz_applic"));
		    }

		    // Se la data di registrazione è valorizzata e sono definite sia data di inizio
		    // validità che
		    // data di fine validità dell'accordo
		    if (dtRegAccordo != null && dtDecAccordo != null
			    && dtFineValidAccordo != null) {
			// Richiamo il controllo inclusione dato figlio passando come entità figlio
			// = accordo e
			// entità padre = ente gestore dell'accordo. Se l'ente è anche gestore non
			// effettuo il
			// controllo.
			BigDecimal idEnteConvenzGestore = getForm().getAccordoDetail()
				.getId_ente_convenz_gestore().parse();
			if (idEnteConvenzGestore != null
				&& !idEnteConvenzGestore.equals(idEnteConvenz)) {
			    // Per l\u0027ente gestore, ricavo i relativi accordi, mettendoli in
			    // ordine di data e
			    // verificando che l\u0027accordo dell\u0027ente
			    // sia incluso nella sequenza di validità degli accordi del gestore
			    entiConvenzionatiEjb.checkInclusioneAccordoFiglio(idEnteConvenzGestore,
				    dtDecAccordo, dtFineValidAccordo,
				    getForm().getEnteConvenzionatoDetail().getNm_ente_siam()
					    .parse());

			}
			// Richiamo il controllo inclusione dato figlio passando come entità figlio
			// = accordo e
			// entità padre = ente conservatore dell'accordo. Se l'ente è anche
			// conservatore non effettuo il
			// controllo.
			BigDecimal idEnteConvenzConserv = getForm().getAccordoDetail()
				.getId_ente_convenz_conserv().parse();
			if (idEnteConvenzConserv != null
				&& !idEnteConvenzConserv.equals(idEnteConvenz)) {
			    // Per l\u0027ente conservatore, ricavo i relativi accordi, mettendoli
			    // in ordine di data e
			    // verificando che l\u0027accordo dell\u0027ente sia incluso nella
			    // sequenza di validità
			    // degli accordi del conservatore
			    entiConvenzionatiEjb.checkInclusioneAccordoFiglio(idEnteConvenzConserv,
				    dtDecAccordo, dtFineValidAccordo,
				    getForm().getEnteConvenzionatoDetail().getNm_ente_siam()
					    .parse());
			}
		    }

		    // Salva l'accordo
		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    boolean serviziErogatiPresenti = getForm().getServiziErogatiList()
			    .getTable() != null
			    && !getForm().getServiziErogatiList().getTable().isEmpty();
		    boolean isEditMode = !getForm().getTipoServizioAccordoList().getStatus()
			    .equals(Status.view);
		    checkAndSaveAccordo(param, idEnteConvenz, getForm().getAccordoDetail(),
			    serviziErogatiPresenti, getForm().getTipoServizioAccordoList(),
			    isEditMode, idAmbienteEnteConvenzCambio, idStrutList);

		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
	    }
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_ACCORDO);
    }

    private void checkAndSaveAccordo(LogParam param, BigDecimal idEnteConvenz,
	    AmministrazioneEntiConvenzionatiForm.AccordoDetail accordoDetail,
	    boolean serviziErogatiPresenti,
	    AmministrazioneEntiConvenzionatiForm.TipoServizioAccordoList tipoServizioAccordoList,
	    boolean isEditMode, BigDecimal idAmbienteEnteConvenzCambio,
	    List<BigDecimal> idStrutList) throws ParerUserError, EMFError {
	getForm().getAccordoDetail().post(getRequest());

	Date dtDecAccordo = getForm().getAccordoDetail().getDt_dec_accordo().parse();
	Date dtIniValEnte = getForm().getEnteConvenzionatoDetail().getDt_ini_val().parse();

	if (dtDecAccordo.before(dtIniValEnte)) {
	    // roba in sessione
	    getRequest().setAttribute("customControlloDateMessageBox", true);
	    Object[] attributiSalvataggioAccordoControlloDate = new Object[8];
	    attributiSalvataggioAccordoControlloDate[0] = param;
	    attributiSalvataggioAccordoControlloDate[1] = idEnteConvenz;
	    attributiSalvataggioAccordoControlloDate[2] = accordoDetail;
	    attributiSalvataggioAccordoControlloDate[3] = serviziErogatiPresenti;
	    attributiSalvataggioAccordoControlloDate[4] = tipoServizioAccordoList;
	    attributiSalvataggioAccordoControlloDate[5] = isEditMode;
	    attributiSalvataggioAccordoControlloDate[6] = idAmbienteEnteConvenzCambio;
	    attributiSalvataggioAccordoControlloDate[7] = idStrutList;
	    getSession().setAttribute("attributiSalvataggioAccordoControlloDate",
		    attributiSalvataggioAccordoControlloDate);
	} else {
	    eseguiSalvataggioAccordo(param, idEnteConvenz, getForm().getAccordoDetail(),
		    serviziErogatiPresenti, getForm().getTipoServizioAccordoList(), isEditMode,
		    idAmbienteEnteConvenzCambio, idStrutList);
	}
    }

    public void eseguiSalvataggioAccordo(LogParam param, BigDecimal idEnteConvenz,
	    AmministrazioneEntiConvenzionatiForm.AccordoDetail accordoDetail,
	    boolean serviziErogatiPresenti,
	    AmministrazioneEntiConvenzionatiForm.TipoServizioAccordoList tipoServizioAccordoList,
	    boolean isEditMode, BigDecimal idAmbienteEnteConvenzCambio,
	    List<BigDecimal> idStrutList) throws ParerUserError, EMFError {

	if (getForm().getAccordiList().getStatus().equals(Status.insert)) {
	    param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
	    Long idAccordo = entiConvenzionatiEjb.saveAccordo(param, idEnteConvenz,
		    getForm().getAccordoDetail(), idAmbienteEnteConvenzCambio, idStrutList,
		    getForm().getTipoServizioAccordoList(), getUser().getIdUtente(),
		    (getSession().getAttribute("nuovoAccordo") != null));
	    if (idAccordo != null) {
		getForm().getAccordoDetail().getId_accordo_ente().setValue(idAccordo.toString());
	    }
	    OrgAccordoEnteRowBean row = new OrgAccordoEnteRowBean();
	    getForm().getAccordoDetail().copyToBean(row);
	    getForm().getAccordiList().getTable().last();
	    getForm().getAccordiList().getTable().add(row);
	} else if (getForm().getAccordiList().getStatus().equals(Status.update)
		|| getForm().getListaAccordi().getStatus().equals(Status.update)) {
	    param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
	    BigDecimal idAccordo = getForm().getAccordoDetail().getId_accordo_ente().parse();
	    entiConvenzionatiEjb.saveAccordo(param, idEnteConvenz, idAccordo,
		    getForm().getAccordoDetail(), serviziErogatiPresenti,
		    getForm().getTipoServizioAccordoList(), isEditMode);
	}
	BigDecimal idAccordo = getForm().getAccordoDetail().getId_accordo_ente().parse();
	BigDecimal idTipoAccordo = getForm().getAccordoDetail().getId_tipo_accordo().parse();
	initAccordoDetail();
	getForm().getAccordoDetail().getId_tipo_accordo()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgTipoAccordoTableBean(), "id_tipo_accordo",
			"cd_tipo_accordo"));
	// Ripopolo la combo classe ente
	getForm().getAccordoDetail().getId_classe_ente_convenz()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgClasseEnteConvenzTableBean(),
			"id_classe_ente_convenz", "cd_classe_ente_convenz"));
	loadDettaglioAccordo(idAccordo, idTipoAccordo);

	// View mode del campo della form di dettaglio dell'ente convenzionato messo in edit dal
	// cambio
	// gestore
	getForm().getEnteConvenzionatoDetail().getId_ambiente_ente_convenz_cambio().setViewMode();
	getForm().getEnteConvenzionatoDetail().setStatus(Status.view);
	getForm().getListaEntiConvenzionati().setStatus(Status.view);
	//
	getMessageBox().addInfo("Accordo salvato con successo");
	postLoad();
	getMessageBox().setViewMode(ViewMode.plain);
	// Rimuovo dalla sessione l'attributo riferito al cambio gestore
	getSession().removeAttribute("cambioGestore");
	// Rimuovo dalla sessione l'attributo riferito al nuovo accordo
	getSession().removeAttribute("nuovoAccordo");

	forwardToPublisher(Application.Publisher.DETTAGLIO_ACCORDO);
    }

    public void confermaSalvataggioControlloDateAccordo() throws ParerUserError, EMFError {

	if (getSession().getAttribute("attributiSalvataggioAccordoControlloDate") != null) {

	    Object[] attributiSalvataggioAccordoControlloDate = (Object[]) getSession()
		    .getAttribute("attributiSalvataggioAccordoControlloDate");
	    LogParam param = (LogParam) attributiSalvataggioAccordoControlloDate[0];
	    BigDecimal idEnteConvenz = (BigDecimal) attributiSalvataggioAccordoControlloDate[1];
	    AmministrazioneEntiConvenzionatiForm.AccordoDetail accordoDetail = (AmministrazioneEntiConvenzionatiForm.AccordoDetail) attributiSalvataggioAccordoControlloDate[2];
	    boolean serviziErogatiPresenti = (boolean) attributiSalvataggioAccordoControlloDate[3];
	    AmministrazioneEntiConvenzionatiForm.TipoServizioAccordoList tipoServizioAccordoList = (AmministrazioneEntiConvenzionatiForm.TipoServizioAccordoList) attributiSalvataggioAccordoControlloDate[4];
	    boolean isEditMode = (boolean) attributiSalvataggioAccordoControlloDate[5];
	    BigDecimal idAmbienteEnteConvenzCambio = (BigDecimal) attributiSalvataggioAccordoControlloDate[6];
	    List<BigDecimal> idStrutList = (List<BigDecimal>) attributiSalvataggioAccordoControlloDate[7];
	    try {
		eseguiSalvataggioAccordo(param, idEnteConvenz, accordoDetail,
			serviziErogatiPresenti, tipoServizioAccordoList, isEditMode,
			idAmbienteEnteConvenzCambio, idStrutList);

	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
		forwardToPublisher(Application.Publisher.DETTAGLIO_ACCORDO);
	    }
	}

    }

    public void annullaSalvataggioControlloDateAccordo() throws EMFError, ParerUserError {
	getSession().removeAttribute("attributiSalvataggioAccordoControlloDate");
	getForm().getAccordoDetail().setViewMode();
	getForm().getTipoServizioAccordoList().setViewMode();

	if (getForm().getAccordiList().getStatus().equals(Status.insert) // ||
	// getForm().getAccordoWizardDetail().getStatus().equals(Status.insert)
	) {
	    forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO);
	} else {
	    OrgAccordoEnteRowBean accordoEnteRowBean = entiConvenzionatiEjb
		    .getOrgAccordoEnteRowBean(
			    getForm().getAccordoDetail().getId_accordo_ente().parse());
	    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	    getForm().getAccordoDetail().getDt_dec_accordo()
		    .setValue(df.format(accordoEnteRowBean.getDtDecAccordo()));
	    forwardToPublisher(Application.Publisher.DETTAGLIO_ACCORDO);
	}

	getForm().getAccordiList().setStatus(Status.view);
    }

    @Override
    public void updateAccordiList() throws EMFError {
	getForm().getAccordoDetail().setEditMode();
	getForm().getAccordoDetail().getId_accordo_ente().setViewMode();
	getForm().getTipoServizioAccordoList().getIm_tariffa_accordo().setEditMode();
	getForm().getTipoServizioAccordoList().setStatus(Status.update);
	getForm().getAccordoDetail().getId_tipo_accordo().setViewMode();
	// Bottone di ricalcolo
	getForm().getAccordoDetail().getRicalcoloServiziErogati().setHidden(true);
	// Bottone di chiusura accordo
	getForm().getAccordoDetail().getChiusuraAccordo().setHidden(true);
	// Se sono presenti servizi erogati
	if (!getForm().getServiziErogatiList().getTable().isEmpty()) {
	    getForm().getAccordoDetail().getId_tariffario().setViewMode();
	    getForm().getAccordoDetail().getId_classe_ente_convenz().setViewMode();
	    getForm().getAccordoDetail().getNi_abitanti().setViewMode();
	    getForm().getAccordoDetail().getFl_pagamento().setViewMode();
	    getForm().getTipoServizioAccordoList().getIm_tariffa_accordo().setViewMode();
	    getForm().getTipoServizioAccordoList().setStatus(Status.view);
	} else {
	    // Importo tariffa accordo va "ritrasformato" in stringa senza il GroupingSeparator
	    for (BaseRow rb : (BaseTable) getForm().getTipoServizioAccordoList().getTable()) {
		if (rb.getString("im_tariffa_accordo") != null) {
		    rb.setString("im_tariffa_accordo",
			    rb.getString("im_tariffa_accordo").replaceAll("\\.", ""));
		}
	    }
	}

	getForm().getAnnualitaAccordoList().setHideInsertButton(true);

	checkModificaRegistro();
	getForm().getAccordoDetail().setStatus(Status.update);
	getForm().getAccordiList().setStatus(Status.update);
    }

    @Override
    public void updateListaAccordi() throws EMFError {
	getForm().getAccordoDetail().setEditMode();
	getForm().getTipoServizioAccordoList().getIm_tariffa_accordo().setEditMode();
	getForm().getTipoServizioAccordoList().setStatus(Status.update);
	getForm().getAccordoDetail().getId_tipo_accordo().setViewMode();
	// Bottone di ricalcolo
	getForm().getAccordoDetail().getRicalcoloServiziErogati().setHidden(true);
	// Bottone di chiusura accordo
	getForm().getAccordoDetail().getChiusuraAccordo().setHidden(true);
	// Se sono presenti servizi erogati
	if (!getForm().getServiziErogatiList().getTable().isEmpty()) {
	    getForm().getAccordoDetail().getId_tariffario().setViewMode();
	    getForm().getAccordoDetail().getId_classe_ente_convenz().setViewMode();
	    getForm().getAccordoDetail().getNi_abitanti().setViewMode();
	    getForm().getAccordoDetail().getFl_pagamento().setViewMode();
	    getForm().getTipoServizioAccordoList().getIm_tariffa_accordo().setViewMode();
	    getForm().getTipoServizioAccordoList().setStatus(Status.view);
	}

	getForm().getAnnualitaAccordoList().setHideInsertButton(true);

	checkModificaRegistro();
	getForm().getAccordoDetail().setStatus(Status.update);
	getForm().getListaAccordi().setStatus(Status.update);
    }

    @Override
    public void updateAccordoDetail() throws EMFError {
	getForm().getAccordoDetail().setEditMode();
	// Campo id accordo sempre non editabile
	getForm().getAccordoDetail().getId_accordo_ente().setViewMode();
	getForm().getTipoServizioAccordoList().getIm_tariffa_accordo().setEditMode();
	getForm().getTipoServizioAccordoList().setStatus(Status.update);
	getForm().getAccordoDetail().getId_tipo_accordo().setViewMode();
	// Bottone di ricalcolo
	getForm().getAccordoDetail().getRicalcoloServiziErogati().setHidden(true);
	// Bottone di chiusura accordo
	getForm().getAccordoDetail().getChiusuraAccordo().setHidden(true);
	// Se sono presenti servizi erogati
	if (!getForm().getServiziErogatiList().getTable().isEmpty()) {
	    getForm().getAccordoDetail().getId_tariffario().setViewMode();
	    getForm().getAccordoDetail().getId_classe_ente_convenz().setViewMode();
	    getForm().getAccordoDetail().getNi_abitanti().setViewMode();
	    getForm().getAccordoDetail().getFl_pagamento().setViewMode();
	    getForm().getTipoServizioAccordoList().getIm_tariffa_accordo().setViewMode();
	    getForm().getTipoServizioAccordoList().setStatus(Status.view);
	}

	getForm().getAnnualitaAccordoList().setHideInsertButton(true);

	checkModificaRegistro();
	getForm().getAccordoDetail().setStatus(Status.update);
	getForm().getListaAccordi().setStatus(Status.update);
    }

    public void checkModificaRegistro() throws EMFError {
	// Gestisco i valori di ambiente, ente, struttura e registro e l'eventuale possibilitÃ  di
	// modifica degli stessi
	BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam().parse();
	BigDecimal idAmbienteEnteConvenz = getForm().getEnteConvenzionatoDetail()
		.getId_ambiente_ente_convenz().parse();
	String[][] ambEnteStrut = null;
	try {
	    ambEnteStrut = entiConvenzionatiEjb
		    .getAmbEnteStrutByIamParamApplic(idAmbienteEnteConvenz, idEnteConvenz);
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getMessage());
	}

	String nmEnte = getForm().getAccordoDetail().getNm_ente().parse();
	String nmStrut = getForm().getAccordoDetail().getNm_strut().parse();
	String cdRegistro = getForm().getAccordoDetail().getCd_registro_repertorio().parse();
	BigDecimal idStrut = entiConvenzionatiEjb.getIdStrut(nmEnte, nmStrut);
	try {
	    getForm().getAccordoDetail().getCd_registro_repertorio()
		    .setDecodeMap(DecodeMap.Factory.newInstance(
			    entiConvenzionatiEjb.getRegistriStrutturaTableBean(idStrut),
			    "cd_registro", "cd_registro"));
	    getForm().getAccordoDetail().getCd_registro_repertorio().setValue(cdRegistro);
	    if (((String) ambEnteStrut[2][1]).equals(nmStrut)
		    && ((String) ambEnteStrut[1][1]).equals(nmEnte)) {
		getForm().getAccordoDetail().getCd_registro_repertorio().setEditMode();
	    } else {
		getForm().getAccordoDetail().getCd_registro_repertorio().setViewMode();
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError("Errore durante il caricamento del registro");
	}
    }

    public void checkModificaRegistroDisciplinareTecnico() throws EMFError {
	// Gestisco i valori di ambiente, ente, struttura e registro e l'eventuale possibilitÃ  di
	// modifica degli stessi
	BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam().parse();
	BigDecimal idAmbienteEnteConvenz = getForm().getEnteConvenzionatoDetail()
		.getId_ambiente_ente_convenz().parse();
	String[][] ambEnteStrut = null;
	try {
	    ambEnteStrut = entiConvenzionatiEjb
		    .getAmbEnteStrutByIamParamApplic(idAmbienteEnteConvenz, idEnteConvenz);
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getMessage());
	}

	String nmEnte = getForm().getDisciplinareTecnicoDetail().getEnte_discip_strut().parse();
	String nmStrut = getForm().getDisciplinareTecnicoDetail().getStruttura_discip_strut()
		.parse();
	String cdRegistro = getForm().getDisciplinareTecnicoDetail().getCd_registro_discip_strut()
		.parse();
	BigDecimal idStrut = entiConvenzionatiEjb.getIdStrut(nmEnte, nmStrut);
	try {
	    getForm().getDisciplinareTecnicoDetail().getCd_registro_discip_strut()
		    .setDecodeMap(DecodeMap.Factory.newInstance(
			    entiConvenzionatiEjb.getRegistriStrutturaTableBean(idStrut),
			    "cd_registro", "cd_registro"));
	    getForm().getDisciplinareTecnicoDetail().getCd_registro_discip_strut()
		    .setValue(cdRegistro);
	    if (((String) ambEnteStrut[2][1]).equals(nmStrut)
		    && ((String) ambEnteStrut[1][1]).equals(nmEnte)) {
		getForm().getDisciplinareTecnicoDetail().getCd_registro_discip_strut()
			.setEditMode();
	    } else {
		getForm().getDisciplinareTecnicoDetail().getCd_registro_discip_strut()
			.setViewMode();
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError("Errore durante il caricamento del registro");
	}
    }

    public void checkModificaRegistroModuloInformazioni() throws EMFError {
	// Gestisco i valori di ambiente, ente, struttura e registro e l'eventuale possibilitÃ  di
	// modifica degli stessi
	BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam().parse();
	BigDecimal idAmbienteEnteConvenz = getForm().getEnteConvenzionatoDetail()
		.getId_ambiente_ente_convenz().parse();
	String[][] ambEnteStrut = null;
	try {
	    ambEnteStrut = entiConvenzionatiEjb
		    .getAmbEnteStrutByIamParamApplic(idAmbienteEnteConvenz, idEnteConvenz);
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getMessage());
	}

	String nmEnte = getForm().getModuloInformazioniDetail().getNm_ente().parse();
	String nmStrut = getForm().getModuloInformazioniDetail().getNm_strut().parse();
	String cdRegistro = getForm().getModuloInformazioniDetail().getCd_registro_modulo_info()
		.parse();
	BigDecimal idStrut = entiConvenzionatiEjb.getIdStrut(nmEnte, nmStrut);
	try {
	    getForm().getModuloInformazioniDetail().getCd_registro_modulo_info()
		    .setDecodeMap(DecodeMap.Factory.newInstance(
			    entiConvenzionatiEjb.getRegistriStrutturaTableBean(idStrut),
			    "cd_registro", "cd_registro"));
	    getForm().getModuloInformazioniDetail().getCd_registro_modulo_info()
		    .setValue(cdRegistro);
	    if (((String) ambEnteStrut[2][1]).equals(nmStrut)
		    && ((String) ambEnteStrut[1][1]).equals(nmEnte)) {
		getForm().getModuloInformazioniDetail().getCd_registro_modulo_info().setEditMode();
	    } else {
		getForm().getModuloInformazioniDetail().getCd_registro_modulo_info().setViewMode();
	    }
	} catch (ParerUserError ex) {
	    getMessageBox()
		    .addError("Errore durante il caricamento del registro nel modulo informazioni");
	}
    }

    public void checkModificaRegistroGestioneAccordo() throws EMFError {
	// Gestisco i valori di ambiente, ente, struttura e registro e l'eventuale possibilitÃ  di
	// modifica degli stessi
	BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam().parse();
	BigDecimal idAmbienteEnteConvenz = getForm().getEnteConvenzionatoDetail()
		.getId_ambiente_ente_convenz().parse();
	String[][] ambEnteStrut = null;
	try {
	    ambEnteStrut = entiConvenzionatiEjb
		    .getAmbEnteStrutByIamParamApplic(idAmbienteEnteConvenz, idEnteConvenz);
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getMessage());
	}

	String nmEnte = getForm().getGestioneAccordoDetail().getEnte_gest_accordo().parse();
	String nmStrut = getForm().getGestioneAccordoDetail().getStruttura_gest_accordo().parse();
	String cdRegistro = getForm().getGestioneAccordoDetail().getCd_registro_gest_accordo()
		.parse();
	BigDecimal idStrut = entiConvenzionatiEjb.getIdStrut(nmEnte, nmStrut);
	try {
	    getForm().getGestioneAccordoDetail().getCd_registro_gest_accordo()
		    .setDecodeMap(DecodeMap.Factory.newInstance(
			    entiConvenzionatiEjb.getRegistriStrutturaTableBean(idStrut),
			    "cd_registro", "cd_registro"));
	    getForm().getGestioneAccordoDetail().getCd_registro_gest_accordo().setValue(cdRegistro);
	    if (((String) ambEnteStrut[2][1]).equals(nmStrut)
		    && ((String) ambEnteStrut[1][1]).equals(nmEnte)) {
		getForm().getGestioneAccordoDetail().getCd_registro_gest_accordo().setEditMode();
	    } else {
		getForm().getGestioneAccordoDetail().getCd_registro_gest_accordo().setViewMode();
	    }
	} catch (ParerUserError ex) {
	    getMessageBox()
		    .addError("Errore durante il caricamento del registro nella gestione accordo");
	}
    }

    @Override
    public void deleteListaAccordi() throws EMFError {
	OrgVRicAccordoEnteRowBean currentRow = (OrgVRicAccordoEnteRowBean) getForm()
		.getListaAccordi().getTable().getCurrentRow();
	BigDecimal idAccordoEnte = currentRow.getIdAccordoEnte();
	int riga = getForm().getListaAccordi().getTable().getCurrentRowIndex();
	// Eseguo giusto un controllo per verificare che io stia prendendo la riga giusta se sono
	// nel dettaglio
	if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_ACCORDO)) {
	    if (!idAccordoEnte.equals(getForm().getAccordoDetail().getId_accordo_ente().parse())) {
		getMessageBox().addError("Eccezione imprevista nell'eliminazione dell'accordo");
	    }
	}
	// Controllo che sull\u0027ente convenzionato oltre all\u0027accordo cui
	// si richiede la cancellazione esista un altro accordo.
	int numAccordiSuEnte = entiConvenzionatiEjb
		.countAccordiSuEnteConvenz(currentRow.getIdEnteConvenz());

	if (numAccordiSuEnte == 1) {
	    getMessageBox().addError(
		    "L\u0027accordo che si richiede di cancellare \u00E0 l\u0027unico definito sull\u0027ente. Operazione non consentita");
	}

	if (!getMessageBox().hasError() && idAccordoEnte != null) {
	    try {
		/*
		 * Controlla che nella tabella ORG_SERVIZIO_EROG non siano presenti servizi riferiti
		 * all'accordo da eliminare gi\u00E0 inseriti in una fattura
		 */
		if (entiConvenzionatiEjb.checkEsistenzaServiziInFatturaPerAccordo(idAccordoEnte)) {
		    getMessageBox().addError(
			    "I servizi erogati sull'accordo sono gi\u00E0 stati inseriti in una fattura; impossibile eseguire l'eliminazione");
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
			    .equalsIgnoreCase(Application.Publisher.DETTAGLIO_ACCORDO)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
		    } else {
			param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
				getForm().getAccordiList()));
		    }
		    entiConvenzionatiEjb.deleteOrgAccordoEnte(param, idAccordoEnte);
		    getForm().getAccordiList().getTable().remove(riga);
		    getMessageBox().addInfo("Accordo eliminato con successo");
		    getMessageBox().setViewMode(ViewMode.plain);
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(
			"L'accordo non pu\u00F2 essere eliminato: " + ex.getDescription());
	    }
	}
	if (!getMessageBox().hasError()
		&& getLastPublisher().equals(Application.Publisher.DETTAGLIO_ACCORDO)) {
	    goBack();
	} else {
	    forwardToPublisher(getLastPublisher());
	}
    }

    /**
     * Metodo per cancellare un accordo invocato con il tasto di cancellazione della
     * fieldNavBarDetail. NOTA BENE: al momento, visto che una lista accordi provenienti da
     * dettaglio ente convenzionato non può mai essere vuota (ci deve sempre essere almeno un
     * accordo) tale metodo verrà invocato solo dalla Ricerca accordi che non utilizza una
     * listNavBarDetail. In caso di sviluppo futuri il metodo dovrà prevedere il discernimento tra
     * AccordiList (da dettaglio ente convenzionato) e ListaAccordi (da Ricerca accordi)
     *
     * @throws EMFError errore applicativo
     */
    @Override
    public void deleteAccordoDetail() throws EMFError {
	BigDecimal idAccordoEnte = getForm().getAccordoDetail().getId_accordo_ente().parse();
	OrgEnteSiamRowBean enteSiamRowBean;
	try {
	    enteSiamRowBean = entiConvenzionatiEjb.getOrgEnteSiamRowBeanByAccordo(idAccordoEnte);
	} catch (ParerInternalError ex) {
	    throw new EMFError("Errore durante la cancellazione dell'accordo", ex);
	}
	// Eseguo giusto un controllo per verificare che io stia prendendo la riga giusta se sono
	// nel dettaglio
	if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_ACCORDO)) {
	    if (!idAccordoEnte.equals(getForm().getAccordoDetail().getId_accordo_ente().parse())) {
		getMessageBox().addError("Eccezione imprevista nell'eliminazione dell'accordo");
	    }
	}
	// Controllo che sull\u0027ente convenzionato oltre all\u0027accordo cui
	// si richiede la cancellazione esista un altro accordo.
	int numAccordiSuEnte = entiConvenzionatiEjb
		.countAccordiSuEnteConvenz(enteSiamRowBean.getIdEnteSiam());
	if (numAccordiSuEnte == 1) {
	    getMessageBox().addError(
		    "L\u0027accordo che si richiede di cancellare \u00E0 l\u0027unico definito sull\u0027ente. Operazione non consentita");
	}

	if (!getMessageBox().hasError() && idAccordoEnte != null) {
	    try {
		/*
		 * Controlla che nella tabella ORG_SERVIZIO_EROG non siano presenti servizi riferiti
		 * all'accordo da eliminare gi\u00E0 inseriti in una fattura
		 */
		if (entiConvenzionatiEjb.checkEsistenzaServiziInFatturaPerAccordo(idAccordoEnte)) {
		    getMessageBox().addError(
			    "I servizi erogati sull'accordo sono gi\u00E0 stati inseriti in una fattura; impossibile eseguire l'eliminazione");
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
			    .equalsIgnoreCase(Application.Publisher.DETTAGLIO_ACCORDO)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
		    } else {
			param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
				getForm().getAccordiList()));
		    }
		    entiConvenzionatiEjb.deleteOrgAccordoEnte(param, idAccordoEnte);
		    // getForm().getAccordiList().getTable().remove(riga);
		    getMessageBox().addInfo("Accordo eliminato con successo");
		    getMessageBox().setViewMode(ViewMode.plain);
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(
			"L'accordo non pu\u00F2 essere eliminato: " + ex.getDescription());
	    }
	}
	if (!getMessageBox().hasError()
		&& getLastPublisher().equals(Application.Publisher.DETTAGLIO_ACCORDO)) {
	    goBack();
	} else {
	    forwardToPublisher(getLastPublisher());
	}
    }

    @Override
    public void deleteAccordiList() throws EMFError {
	OrgAccordoEnteRowBean currentRow = (OrgAccordoEnteRowBean) getForm().getAccordiList()
		.getTable().getCurrentRow();
	BigDecimal idAccordo = currentRow.getIdAccordoEnte();
	int riga = getForm().getAccordiList().getTable().getCurrentRowIndex();
	// Eseguo giusto un controllo per verificare che io stia prendendo la riga giusta se sono
	// nel dettaglio
	if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_ACCORDO)) {
	    if (!idAccordo.equals(getForm().getAccordoDetail().getId_accordo_ente().parse())) {
		getMessageBox().addError("Eccezione imprevista nell'eliminazione dell'accordo");
	    }
	}
	// Controllo che sull\u0027ente convenzionato oltre all\u0027accordo cui si richiede la
	// cancellazione esista
	// un altro
	// accordo.
	if (getForm().getAccordiList().getTable().size() == 1) {
	    getMessageBox().addError(
		    "L\u0027accordo che si richiede di cancellare \u00E0 l\u0027unico definito sull\u0027ente. Operazione non consentita");
	}

	if (!getMessageBox().hasError() && idAccordo != null) {
	    try {
		/*
		 * Controlla che nella tabella ORG_SERVIZIO_EROG non siano presenti servizi riferiti
		 * all'accordo da eliminare già inseriti in una fattura
		 */
		if (entiConvenzionatiEjb.checkEsistenzaServiziInFatturaPerAccordo(idAccordo)) {
		    getMessageBox().addError(
			    "I servizi erogati sull'accordo sono gi\u00E0  stati inseriti in una fattura; impossibile eseguire l'eliminazione");
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
			    .equalsIgnoreCase(Application.Publisher.DETTAGLIO_ACCORDO)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
		    } else {
			param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
				getForm().getAccordiList()));
		    }
		    entiConvenzionatiEjb.deleteOrgAccordoEnte(param, idAccordo);
		    getForm().getAccordiList().getTable().remove(riga);
		    getMessageBox().addInfo("Accordo eliminato con successo");
		    getMessageBox().setViewMode(ViewMode.plain);
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(
			"L'accordo non pu\u00F2 essere eliminato: " + ex.getDescription());
	    }
	}
	if (!getMessageBox().hasError()
		&& getLastPublisher().equals(Application.Publisher.DETTAGLIO_ACCORDO)) {
	    goBack();
	} else {
	    forwardToPublisher(getLastPublisher());
	}
    }

    @Override
    public void deleteAnnualitaAccordoList() throws EMFError {
	OrgAaAccordoRowBean currentRow = (OrgAaAccordoRowBean) getForm().getAnnualitaAccordoList()
		.getTable().getCurrentRow();
	BigDecimal idAaAccordo = currentRow.getIdAaAccordo();
	int riga = getForm().getAnnualitaAccordoList().getTable().getCurrentRowIndex();
	// Eseguo giusto un controllo per verificare che io stia prendendo la riga giusta se sono
	// nel dettaglio
	if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_ANNUALITA)) {
	    if (!idAaAccordo
		    .equals(getForm().getAnnualitaAccordoDetail().getId_aa_accordo().parse())) {
		getMessageBox().addError("Eccezione imprevista nell'eliminazione dell'annualità");
	    }
	}

	if (!getMessageBox().hasError() && idAaAccordo != null) {
	    try {

		if (!getMessageBox().hasError()) {
		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    if (param.getNomePagina()
			    .equalsIgnoreCase(Application.Publisher.DETTAGLIO_ANNUALITA)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
		    } else {
			param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
				getForm().getAnnualitaAccordoList()));
		    }
		    entiConvenzionatiEjb.deleteOrgAaAccordo(param, idAaAccordo);
		    getForm().getAnnualitaAccordoList().getTable().remove(riga);
		    getMessageBox().addInfo("Annualità eliminata con successo");
		    getMessageBox().setViewMode(ViewMode.plain);
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(
			"L'annualità non pu\u00F2 essere eliminata: " + ex.getDescription());
	    }
	}
	if (!getMessageBox().hasError()
		&& getLastPublisher().equals(Application.Publisher.DETTAGLIO_ANNUALITA)) {
	    goBack();
	} else {
	    forwardToPublisher(getLastPublisher());
	}
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Gestione dettaglio servizio erogato">
    private void loadDettaglioServizioErogato(BigDecimal idServizioErogato)
	    throws ParerUserError, EMFError {
	// Carico il dettaglio servizio erogato
	OrgServizioErogRowBean detailRow = entiConvenzionatiEjb
		.getOrgServizioErogRowBean(idServizioErogato);
	getForm().getServizioErogatoDetail().copyFromBean(detailRow);

	getForm().getServizioErogatoDetail().setViewMode();
	getForm().getServizioErogatoDetail().setStatus(Status.view);
	getForm().getServiziErogatiList().setStatus(Status.view);

	// Popolamento lista servizi fatturati
	getForm().getServiziFatturatiList().setTable(entiConvenzionatiEjb
		.getOrgVLisServFatturaTableBeanByIdServizioErog(idServizioErogato));
	getForm().getServiziFatturatiList().getTable().setPageSize(10);
	getForm().getServiziFatturatiList().getTable().first();
    }

    private void initServizioErogatoDetail() throws ParerUserError, EMFError {
	// Tipo servizio
	getForm().getServizioErogatoDetail().getId_tipo_servizio()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgTipoServizioTableBean(), "id_tipo_servizio",
			"cd_tipo_servizio"));
	// Fl pagamento
	getForm().getServizioErogatoDetail().getFl_pagamento()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	// Sistema versante
	getForm().getServizioErogatoDetail().getId_sistema_versante()
		.setDecodeMap(
			DecodeMap.Factory.newInstance(
				sistemiVersantiEjb.getAplSistemaVersanteValidiTableBean(
					"PERSONA_FISICA", null),
				"id_sistema_versante", "nm_sistema_versante"));
    }

    private void saveAnnualitaAccordo() throws EMFError {
	if (getForm().getAnnualitaAccordoDetail().postAndValidate(getRequest(), getMessageBox())) {

	    getForm().getTipoServizioAnnualitaAccordoList().post(getRequest());

	    try {
		for (BaseRow rb : (BaseTable) getForm().getTipoServizioAnnualitaAccordoList()
			.getTable()) {
		    checkImportoTariffa(rb.getString("im_tariffa_aa_accordo"));
		}

		if (!getMessageBox().hasError()) {

		    // Salva l'annualitÃƒÂ 
		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    if (getForm().getAnnualitaAccordoList().getStatus().equals(Status.insert)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
			Long idAaAccordo = entiConvenzionatiEjb.saveAnnualitaAccordo(param,
				getForm().getAccordoDetail().getId_accordo_ente().parse(),
				getForm().getAnnualitaAccordoDetail(),
				getForm().getTipoServizioAnnualitaAccordoList());
			if (idAaAccordo != null) {
			    getForm().getAnnualitaAccordoDetail().getId_aa_accordo()
				    .setValue(idAaAccordo.toString());
			}
			OrgAaAccordoRowBean row = new OrgAaAccordoRowBean();
			getForm().getAnnualitaAccordoDetail().copyToBean(row);
			getForm().getAnnualitaAccordoList().getTable().last();
			getForm().getAnnualitaAccordoList().getTable().add(row);

		    } else if (getForm().getAnnualitaAccordoList().getStatus()
			    .equals(Status.update)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
			BigDecimal idAaAccordo = getForm().getAnnualitaAccordoDetail()
				.getId_aa_accordo().parse();
			BigDecimal idAccordoEnte = getForm().getAccordoDetail().getId_accordo_ente()
				.parse();
			entiConvenzionatiEjb.saveAnnualitaAccordo(param, idAccordoEnte, idAaAccordo,
				getForm().getAnnualitaAccordoDetail(),
				getForm().getTipoServizioAnnualitaAccordoList());
		    }
		    BigDecimal idAaAccordo = getForm().getAnnualitaAccordoDetail()
			    .getId_aa_accordo().parse();
		    if (idAaAccordo != null) {
			loadDettaglioAnnualitaAccordo(idAaAccordo);
		    }
		    getForm().getAnnualitaAccordoDetail().setViewMode();
		    getForm().getAnnualitaAccordoList().setStatus(Status.view);
		    getForm().getAnnualitaAccordoDetail().setStatus(Status.view);
		    getMessageBox().addInfo("AnnualitÃ Â accordo salvata con successo");
		    getMessageBox().setViewMode(ViewMode.plain);
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
		for (BaseRow rb : (BaseTable) getForm().getTipoServizioAnnualitaAccordoList()
			.getTable()) {
		    rb.setBigDecimal("im_tariffa_aa_accordo",
			    (BigDecimal) rb.getOldObject("im_tariffa_aa_accordo"));
		}
	    }
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_ANNUALITA);
    }

    private void saveServizioErogato() throws EMFError {
	if (getForm().getServizioErogatoDetail().postAndValidate(getRequest(), getMessageBox())) {
	    try {
		if (!getMessageBox().hasError()) {
		    // Salva il servizio erogato
		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    if (getForm().getServiziErogatiList().getStatus().equals(Status.insert)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
			Long idServizioErogato = entiConvenzionatiEjb.saveServizioErogato(param,
				getForm().getAccordoDetail().getId_accordo_ente().parse(),
				getForm().getServizioErogatoDetail());
			if (idServizioErogato != null) {
			    getForm().getServizioErogatoDetail().getId_servizio_erogato()
				    .setValue(idServizioErogato.toString());
			}
			OrgServizioErogRowBean row = new OrgServizioErogRowBean();
			getForm().getServizioErogatoDetail().copyToBean(row);
			getForm().getServiziErogatiList().getTable().last();
			getForm().getServiziErogatiList().getTable().add(row);
		    } else if (getForm().getServiziErogatiList().getStatus()
			    .equals(Status.update)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
			BigDecimal idServizioErogato = getForm().getServizioErogatoDetail()
				.getId_servizio_erogato().parse();
			BigDecimal idAccordoEnte = getForm().getAccordoDetail().getId_accordo_ente()
				.parse();
			entiConvenzionatiEjb.saveServizioErogato(param, idAccordoEnte,
				idServizioErogato, getForm().getServizioErogatoDetail());
		    }
		    BigDecimal idServizioErogato = getForm().getServizioErogatoDetail()
			    .getId_servizio_erogato().parse();
		    if (idServizioErogato != null) {
			loadDettaglioServizioErogato(idServizioErogato);
		    }
		    getForm().getServizioErogatoDetail().setViewMode();
		    getForm().getServiziErogatiList().setStatus(Status.view);
		    getForm().getServizioErogatoDetail().setStatus(Status.view);
		    getMessageBox().addInfo("Servizio erogato salvato con successo");
		    getMessageBox().setViewMode(ViewMode.plain);
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
	    }
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_SERVIZIO_EROGATO);
    }

    private void saveGestioneAccordo() throws EMFError {
	if (getForm().getGestioneAccordoDetail().validate(getMessageBox())) {
	    try {
		String dsGestAccordo = getForm().getGestioneAccordoDetail().getDs_gest_accordo()
			.parse();
		byte[] blGestAccordo = getForm().getGestioneAccordoDetail().getBl_gest_accordo()
			.getFileBytes();
		BigDecimal pgGestAccordo = getForm().getGestioneAccordoDetail().getPg_gest_accordo()
			.parse();

		String tipoTrasmissione = getForm().getGestioneAccordoDetail()
			.getTipo_trasmissione().parse();
		BigDecimal idTipoGestioneAccordo = getForm().getGestioneAccordoDetail()
			.getId_tipo_gestione_accordo().parse();

		BigDecimal idAccordoEnte = getForm().getAccordoDetail().getId_accordo_ente()
			.parse();
		BigDecimal idGestAccordo = getForm().getGestioneAccordoDetail().getId_gest_accordo()
			.parse();
		String cdRegistroGestAccordo = getForm().getGestioneAccordoDetail()
			.getCd_registro_gest_accordo().parse();
		BigDecimal aaGestAccordo = getForm().getGestioneAccordoDetail().getAa_gest_accordo()
			.parse();
		String cdKeyGestAccordo = getForm().getGestioneAccordoDetail()
			.getCd_key_gest_accordo().parse();
		Date dtGestAccordo = getForm().getGestioneAccordoDetail().getDt_gest_accordo()
			.parse();
		String dsNotaGestAccordo = getForm().getGestioneAccordoDetail()
			.getDs_nota_gest_accordo().parse();

		BigDecimal idGestAccordoRisposta = getForm().getGestioneAccordoDetail()
			.getId_gest_accordo_risposta().parse();

		String nmEnte = getForm().getGestioneAccordoDetail().getEnte_gest_accordo().parse();
		String nmStrut = getForm().getGestioneAccordoDetail().getStruttura_gest_accordo()
			.parse();

		if (TipoTrasmissione.COMUNICAZIONE_PROTOCOLLATA.name().equals(tipoTrasmissione)) {
		    // Devo valorizzare la chiave
		    if (cdRegistroGestAccordo == null || aaGestAccordo == null
			    || cdKeyGestAccordo == null) {
			getMessageBox().addError(
				"Se il tipo trasmissione e' comunicazione protocollata \u00E8 obbligatorio indicare la chiave del protocollo");
		    }
		}

		if (!getMessageBox().hasError()) {
		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());

		    String nmFileGestAccordo = getForm().getGestioneAccordoDetail()
			    .getBl_gest_accordo().parse();
		    if (nmFileGestAccordo != null) {
			getForm().getGestioneAccordoDetail().getNm_file_gest_accordo()
				.setValue(nmFileGestAccordo);
		    }

		    if (getForm().getGestioniAccordoList().getStatus().equals(Status.insert)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
			Long idGestioneAccordo = entiConvenzionatiEjb.saveGestioneAccordo(param,
				idAccordoEnte, idGestAccordo, cdRegistroGestAccordo, aaGestAccordo,
				cdKeyGestAccordo, dtGestAccordo, dsNotaGestAccordo, dsGestAccordo,
				blGestAccordo, nmFileGestAccordo, pgGestAccordo,
				idTipoGestioneAccordo, TipoTrasmissione.valueOf(tipoTrasmissione),
				idGestAccordoRisposta, null, nmEnte, nmStrut);
			getForm().getGestioneAccordoDetail().getId_gest_accordo()
				.setValue(idGestioneAccordo.toString());
			OrgGestioneAccordoRowBean row = new OrgGestioneAccordoRowBean();
			getForm().getGestioneAccordoDetail().copyToBean(row);
			row.setIdGestAccordo(new BigDecimal(idGestioneAccordo));
			getForm().getGestioneAccordoDetail().copyFromBean(row);
			getForm().getGestioniAccordoList().getTable().last();
			getForm().getGestioniAccordoList().getTable().add(row);
		    } else if (getForm().getGestioniAccordoList().getStatus()
			    .equals(Status.update)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
			entiConvenzionatiEjb.saveGestioneAccordo(param, idAccordoEnte,
				idGestAccordo, cdRegistroGestAccordo, aaGestAccordo,
				cdKeyGestAccordo, dtGestAccordo, dsNotaGestAccordo, dsGestAccordo,
				blGestAccordo, nmFileGestAccordo, pgGestAccordo,
				idTipoGestioneAccordo, TipoTrasmissione.valueOf(tipoTrasmissione),
				idGestAccordoRisposta, null, nmEnte, nmStrut);
			getForm().getGestioneAccordoDetail().copyToBean(
				getForm().getGestioniAccordoList().getTable().getCurrentRow());
		    }
		    loadDettaglioGestioneAccordo(
			    getForm().getGestioneAccordoDetail().getId_gest_accordo().parse());
		    getForm().getGestioneAccordoDetail().setViewMode();
		    getForm().getGestioniAccordoList().setStatus(Status.view);
		    getForm().getGestioneAccordoDetail().setStatus(Status.view);
		    getMessageBox().addInfo("Gestione accordo salvata con successo");
		    getMessageBox().setViewMode(ViewMode.plain);
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
	    }
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_VARIAZIONE_ACCORDO);
    }

    private void saveModuloInformazioni() throws EMFError {
	if (getForm().getModuloInformazioniDetail().validate(getMessageBox())) {
	    try {
		if (getForm().getModuloInformazioniDetail().getDt_ricev().parse()
			.after(new Date())) {
		    getMessageBox().addError(
			    "Attenzione: data di ricezione del modulo di informazioni non pu\u00F2 essere una data futura");
		}

		// Controlli di congruità
		if (StringUtils.isNotBlank(
			getForm().getModuloInformazioniDetail().getCd_modulo_info().getValue())) {
		    if (StringUtils
			    .isNotBlank(getForm().getModuloInformazioniDetail()
				    .getCd_registro_modulo_info().getValue())
			    || StringUtils.isNotBlank(getForm().getModuloInformazioniDetail()
				    .getAa_modulo_info().getValue())
			    || StringUtils.isNotBlank(getForm().getModuloInformazioniDetail()
				    .getCd_key_modulo_info().getValue())) {
			getMessageBox().addError(
				"Inserire l'Identificativo oppure la chiave Registro/Anno/Numero<br>");
		    }
		} else {
		    if (StringUtils
			    .isBlank(getForm().getModuloInformazioniDetail()
				    .getCd_registro_modulo_info().getValue())
			    || StringUtils.isBlank(getForm().getModuloInformazioniDetail()
				    .getAa_modulo_info().getValue())
			    || StringUtils.isBlank(getForm().getModuloInformazioniDetail()
				    .getCd_key_modulo_info().getValue())) {
			getMessageBox().addError(
				"Inserire l'Identificativo oppure la chiave Registro/Anno/Numero<br>");
		    }
		}

		String nmFileModuloInfo = getForm().getModuloInformazioniDetail()
			.getBl_modulo_info().parse();
		if (nmFileModuloInfo != null) {
		    if (nmFileModuloInfo.length() > 100) {
			getMessageBox().addError(
				"Il nome del file caricato supera i 100 caratteri ammessi<br>");
		    }
		}

		if (!getMessageBox().hasError()) {
		    // Salva il Modulo informazioni

		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    if (nmFileModuloInfo != null) {
			getForm().getModuloInformazioniDetail().getNm_file_modulo_info()
				.setValue(nmFileModuloInfo);
		    }

		    BigDecimal idAccordoEnte = getForm().getAccordoDetail().getId_accordo_ente()
			    .parse();
		    BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail()
			    .getId_ente_siam().parse();
		    BigDecimal idModuloInfoAccordo = getForm().getModuloInformazioniDetail()
			    .getId_modulo_info_accordo().parse();
		    Date dtRicev = getForm().getModuloInformazioniDetail().getDt_ricev().parse();
		    String cdModuloInfo = getForm().getModuloInformazioniDetail()
			    .getCd_modulo_info().parse();
		    String cdRegistroModuloInfo = getForm().getModuloInformazioniDetail()
			    .getCd_registro_modulo_info().parse();
		    BigDecimal aaModuloInfo = getForm().getModuloInformazioniDetail()
			    .getAa_modulo_info().parse();
		    String cdKeyModuloInfo = getForm().getModuloInformazioniDetail()
			    .getCd_key_modulo_info().parse();
		    byte[] blModuloInfo = getForm().getModuloInformazioniDetail()
			    .getBl_modulo_info().getFileBytes();
		    String dsModuloInfo = getForm().getModuloInformazioniDetail()
			    .getDs_modulo_info().parse();
		    String nmEnte = getForm().getModuloInformazioniDetail().getNm_ente().parse();
		    String nmStrut = getForm().getModuloInformazioniDetail().getNm_strut().parse();

		    if (getForm().getModuliInformazioniList().getStatus().equals(Status.insert)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
			Long idModuloInformazioni = entiConvenzionatiEjb.saveModuloInformazioni(
				param, idAccordoEnte, idModuloInfoAccordo, dtRicev,
				cdRegistroModuloInfo, aaModuloInfo, cdKeyModuloInfo, cdModuloInfo,
				blModuloInfo, nmFileModuloInfo, dsModuloInfo, null, nmEnte, nmStrut,
				idEnteConvenz);
			OrgModuloInfoAccordoRowBean row = new OrgModuloInfoAccordoRowBean();
			getForm().getModuloInformazioniDetail().copyToBean(row);
			row.setIdModuloInfoAccordo(new BigDecimal(idModuloInformazioni));
			getForm().getModuloInformazioniDetail().copyFromBean(row);
			getForm().getModuliInformazioniList().getTable().last();
			getForm().getModuliInformazioniList().getTable().add(row);
		    } else if (getForm().getModuliInformazioniList().getStatus()
			    .equals(Status.update)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
			entiConvenzionatiEjb.saveModuloInformazioni(param, idAccordoEnte,
				idModuloInfoAccordo, dtRicev, cdRegistroModuloInfo, aaModuloInfo,
				cdKeyModuloInfo, cdModuloInfo, blModuloInfo, nmFileModuloInfo,
				dsModuloInfo, null, nmEnte, nmStrut, idEnteConvenz);
			getForm().getModuloInformazioniDetail().copyToBean(
				getForm().getModuliInformazioniList().getTable().getCurrentRow());
		    }
		    getForm().getModuloInformazioniDetail().setViewMode();
		    getForm().getModuliInformazioniList().setStatus(Status.view);
		    getForm().getModuloInformazioniDetail().setStatus(Status.view);
		    getMessageBox().addInfo("Modulo informazioni salvato con successo");
		    getMessageBox().setViewMode(ViewMode.plain);
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
	    }
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_MODULO_INFORMAZIONI);
    }

    @Override
    public void updateAnnualitaAccordoDetail() throws EMFError {
	updateAnnualitaAccordoList();
    }

    @Override
    public void updateAnnualitaAccordoList() throws EMFError {
	getForm().getAnnualitaAccordoDetail().setEditMode();
	getForm().getTipoServizioAnnualitaAccordoList().getIm_tariffa_aa_accordo().setEditMode();

	getForm().getAnnualitaAccordoDetail().setStatus(Status.update);
	getForm().getAnnualitaAccordoList().setStatus(Status.update);
    }

    @Override
    public void updateServizioErogatoDetail() throws EMFError {
	updateServiziErogatiList();
    }

    @Override
    public void updateServiziErogatiList() throws EMFError {
	getForm().getServizioErogatoDetail().getDt_erog().setEditMode();
	getForm().getServizioErogatoDetail().getIm_valore_tariffa().setEditMode();
	getForm().getServizioErogatoDetail().getFl_pagamento().setEditMode();
	getForm().getServizioErogatoDetail().getNt_servizio_erog().setEditMode();
	getForm().getServiziErogatiList().setStatus(Status.update);
	getForm().getServizioErogatoDetail().setStatus(Status.update);
    }

    @Override
    public void updateGestioniAccordoList() throws EMFError {
	getForm().getGestioneAccordoDetail().setEditMode();
	checkModificaRegistroGestioneAccordo();
	getForm().getGestioneAccordoDetail().setStatus(Status.update);
	getForm().getGestioniAccordoList().setStatus(Status.update);
    }

    @Override
    public void updateModuliInformazioniList() throws EMFError {
	getForm().getModuloInformazioniDetail().setEditMode();
	checkModificaRegistroModuloInformazioni();
	getForm().getModuloInformazioniDetail().setStatus(Status.update);
	getForm().getModuliInformazioniList().setStatus(Status.update);
    }

    @Override
    public void updateUtentiArchivistiList() throws EMFError {
	getForm().getUtenteArchivistaDetail().getFl_referente().setEditMode();
	getForm().getUtenteArchivistaDetail().getDl_note().setEditMode();
	getForm().getUtenteArchivistaDetail().setStatus(Status.update);
	getForm().getUtentiArchivistiList().setStatus(Status.update);
    }

    @Override
    public void updateReferentiEnteList() throws EMFError {
	getForm().getReferenteEnteDetail().getDl_note().setEditMode();
	// Se arrivo dagli enti convenzionati, mostro la sezione relativa a registro/anno/numero...
	if (getForm().getEnteConvenzionatoDetail().getTi_ente() != null
		&& getForm().getEnteConvenzionatoDetail().getTi_ente().parse()
			.equals(ConstOrgEnteSiam.TiEnteSiam.CONVENZIONATO.name())) {
	    getForm().getRiferimentiSection().setHidden(false);
	    getForm().getReferenteEnteDetail().getBl_ente_user_rif().setEditMode();
	    getForm().getReferenteEnteDetail().getDs_ente_user_rif().setEditMode();
	    getForm().getReferenteEnteDetail().getCd_registro_ente_user_rif().setEditMode();
	    getForm().getReferenteEnteDetail().getAa_ente_user_rif().setEditMode();
	    getForm().getReferenteEnteDetail().getCd_key_ente_user_rif().setEditMode();
	    getForm().getReferenteEnteDetail().getDt_reg_ente_user_rif().setEditMode();
	} else {
	    getForm().getRiferimentiSection().setHidden(true);
	}

	getForm().getReferenteEnteDetail().setStatus(Status.update);
	getForm().getReferentiEnteList().setStatus(Status.update);
    }

    @Override
    public void updateCollegamentiEnteList() throws EMFError {
	getForm().getCollegamentoEnteDetail().setEditMode();
	getForm().getCollegamentoEnteDetail().setStatus(Status.update);
	getForm().getCollegamentiEnteList().setStatus(Status.update);
    }

    @Override
    public void updateEntiCollegatiList() throws EMFError {
	getForm().getEnteCollegatoDetail().setEditMode();
	getForm().getEnteCollegatoDetail().setStatus(Status.update);
	getForm().getEntiCollegatiList().setStatus(Status.update);
    }

    @Override
    public void updateCollegamentiEnteAppartList() throws EMFError {
	getForm().getCollegamentoEnteAppartDetail().setEditMode();
	getForm().getCollegamentoEnteAppartDetail().getId_colleg_enti_convenz().setViewMode();
	getForm().getCollegamentoEnteAppartDetail().setStatus(Status.update);
	getForm().getCollegamentiEnteAppartList().setStatus(Status.update);
    }

    @Override
    public void deleteGestioneAccordoDetail() throws EMFError {
	deleteGestioniAccordoList();
    }

    @Override
    public void deleteGestioniAccordoList() throws EMFError {
	OrgGestioneAccordoRowBean currentRow = (OrgGestioneAccordoRowBean) getForm()
		.getGestioniAccordoList().getTable().getCurrentRow();
	BigDecimal idGestAccordo = currentRow.getIdGestAccordo();
	int riga = getForm().getGestioniAccordoList().getTable().getCurrentRowIndex();

	if (!getMessageBox().hasError() && idGestAccordo != null) {
	    try {
		/*
		 * Codice aggiuntivo per il logging...
		 */
		LogParam param = SpagoliteLogUtil.getLogParam(
			paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			SpagoliteLogUtil.getPageName(this));
		param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		if (param.getNomePagina()
			.equalsIgnoreCase(Application.Publisher.DETTAGLIO_VARIAZIONE_ACCORDO)) {
		    param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
		} else {
		    param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
			    getForm().getGestioniAccordoList()));
		}
		entiConvenzionatiEjb.deleteGestioneAccordo(param, idGestAccordo);
		getForm().getGestioniAccordoList().getTable().remove(riga);
		getMessageBox().addInfo("Gestione accordo eliminata con successo");
		getMessageBox().setViewMode(ViewMode.plain);
	    } catch (ParerUserError ex) {
		getMessageBox().addError("La gestione accordo non pu\u00F2 essere eliminata: "
			+ ex.getDescription());
	    }
	}
	if (!getMessageBox().hasError()
		&& getLastPublisher().equals(Application.Publisher.DETTAGLIO_VARIAZIONE_ACCORDO)) {
	    goBack();
	} else {
	    forwardToPublisher(getLastPublisher());
	}
    }

    @Override
    public void deleteModuloInformazioniDetail() throws EMFError {
	deleteModuliInformazioniList();
    }

    @Override
    public void deleteModuliInformazioniList() throws EMFError {
	OrgModuloInfoAccordoRowBean currentRow = (OrgModuloInfoAccordoRowBean) getForm()
		.getModuliInformazioniList().getTable().getCurrentRow();
	BigDecimal idModulo = currentRow.getIdModuloInfoAccordo();
	int riga = getForm().getModuliInformazioniList().getTable().getCurrentRowIndex();

	if (!getMessageBox().hasError() && idModulo != null) {
	    try {
		/*
		 * Codice aggiuntivo per il logging...
		 */
		LogParam param = SpagoliteLogUtil.getLogParam(
			paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			SpagoliteLogUtil.getPageName(this));
		param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		if (param.getNomePagina()
			.equalsIgnoreCase(Application.Publisher.DETTAGLIO_MODULO_INFORMAZIONI)) {
		    param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
		} else {
		    param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
			    getForm().getModuliInformazioniList()));
		}
		entiConvenzionatiEjb.deleteModuloInformazioni(param, idModulo);
		getForm().getModuliInformazioniList().getTable().remove(riga);
		getMessageBox().addInfo("Modulo informazioni eliminato con successo");
		getMessageBox().setViewMode(ViewMode.plain);
	    } catch (ParerUserError ex) {
		getMessageBox().addError("Il modulo informazioni non pu\u00F2 essere eliminato: "
			+ ex.getDescription());
	    }
	}
	if (!getMessageBox().hasError()
		&& getLastPublisher().equals(Application.Publisher.DETTAGLIO_MODULO_INFORMAZIONI)) {
	    goBack();
	} else {
	    forwardToPublisher(getLastPublisher());
	}
    }

    @Override
    public void deleteServizioErogatoDetail() throws EMFError {
	deleteServiziErogatiList();
    }

    @Override
    public void deleteServiziErogatiList() throws EMFError {
	OrgServizioErogRowBean currentRow = (OrgServizioErogRowBean) getForm()
		.getServiziErogatiList().getTable().getCurrentRow();
	BigDecimal idServizioErogato = currentRow.getIdServizioErogato();
	int riga = getForm().getServiziErogatiList().getTable().getCurrentRowIndex();
	// Eseguo giusto un controllo per verificare che io stia prendendo la riga giusta se sono
	// nel dettaglio
	if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_SERVIZIO_EROGATO)) {
	    if (!idServizioErogato.equals(
		    getForm().getServizioErogatoDetail().getId_servizio_erogato().parse())) {
		getMessageBox()
			.addError("Eccezione imprevista nell'eliminazione del servizio erogato");
	    }
	}

	if (!getMessageBox().hasError() && idServizioErogato != null) {
	    try {
		/*
		 * Controlla che nella tabella ORG_SERVIZIO_EROG non siano presenti servizi
		 * giÃƒÆ’Ã‚Â  inseriti in una fattura
		 */
		if (entiConvenzionatiEjb.checkEsistenzaServiziInFattura(idServizioErogato)) {
		    getMessageBox().addError(
			    "Il servizio erogato sull'accordo\u00E8 gi\u00E0Â  stato inserito in una fattura; impossibile eseguire l'eliminazione");
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
			    .equalsIgnoreCase(Application.Publisher.DETTAGLIO_SERVIZIO_EROGATO)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
		    } else {
			param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
				getForm().getServiziErogatiList()));
		    }
		    entiConvenzionatiEjb.deleteOrgServizioErog(param, idServizioErogato);
		    getForm().getServiziErogatiList().getTable().remove(riga);
		    getMessageBox().addInfo("Servizio erogato eliminato con successo");
		    getMessageBox().setViewMode(ViewMode.plain);
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError("Il servizio erogato non pu\u00F2 essere eliminato: "
			+ ex.getDescription());
	    }
	}
	if (!getMessageBox().hasError()
		&& getLastPublisher().equals(Application.Publisher.DETTAGLIO_SERVIZIO_EROGATO)) {
	    goBack();
	} else {
	    forwardToPublisher(getLastPublisher());
	}
    }

    @Override
    public void deleteCollegamentiEnteList() throws EMFError {
	OrgCollegEntiConvenzRowBean currentRow = (OrgCollegEntiConvenzRowBean) getForm()
		.getCollegamentiEnteList().getTable().getCurrentRow();
	BigDecimal idCollegEntiConvenz = currentRow.getIdCollegEntiConvenz();
	BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam().parse();
	int riga = getForm().getCollegamentiEnteList().getTable().getCurrentRowIndex();
	if (!getMessageBox().hasError() && idCollegEntiConvenz != null) {
	    try {
		if (!getMessageBox().hasError()) {
		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    String nomeAzione = null;
		    if (SpagoliteLogUtil.getPageName(this)
			    .equals(Application.Publisher.DETTAGLIO_COLLEGAMENTO_ENTE)) {
			nomeAzione = SpagoliteLogUtil.getToolbarDelete();
		    } else {
			nomeAzione = SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
				getForm().getCollegamentiEnteList());
		    }
		    param.setNomeAzione(nomeAzione);
		    List<OrgAppartCollegEnti> appartCollegEntiList = entiConvenzionatiEjb
			    .getOrgAppartCollegEntiList(idCollegEntiConvenz);
		    Set<BigDecimal> appartCollegEntiSet = appartCollegEntiList.stream()
			    .map(sc -> new BigDecimal(sc.getOrgEnteSiam().getIdEnteSiam()))
			    .collect(Collectors.toSet());

		    // Recupero la lista di utenti coinvolti nella cancellazione del collegamento
		    SortedSet<String> listaUtenti = entiConvenzionatiEjb
			    .getUtentiConAbilitazioniCollegamento(idCollegEntiConvenz);

		    if (!listaUtenti.isEmpty()) {
			getRequest().setAttribute("numeroUtentiCollegamento", listaUtenti.size());
			getRequest().setAttribute("listaUtentiCollegamento",
				listaUtenti.toString().replace("[", "").replace("]", ""));
			getRequest().setAttribute("customDeleteCollegamentoMessageBox", true);
			Object[] attributiDeleteCollegamento = new Object[5];
			attributiDeleteCollegamento[0] = param;
			attributiDeleteCollegamento[1] = idCollegEntiConvenz;
			attributiDeleteCollegamento[2] = idEnteConvenz;
			attributiDeleteCollegamento[3] = appartCollegEntiSet;
			attributiDeleteCollegamento[4] = riga;
			getSession().setAttribute("attributiDeleteCollegamento",
				attributiDeleteCollegamento);
			forwardToPublisher(getLastPublisher());
		    } else {
			eseguiDeleteCollegamentoEnti(param, idCollegEntiConvenz, idEnteConvenz,
				appartCollegEntiSet, riga);
		    }
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError("Il collegamento dell'ente non pu\u00F2 essere eliminato: "
			+ ex.getDescription());
	    }
	} else {
	    getMessageBox().addError("Il collegamento dell'ente non pu\u00F2 essere eliminato");
	}
    }

    //
    public void eseguiDeleteCollegamentoEnti(LogParam param, BigDecimal idCollegEntiConvenz,
	    BigDecimal idEnteConvenz, Set<BigDecimal> appartCollegEntiSet, int riga)
	    throws EMFError {
	try {
	    entiConvenzionatiEjb.deleteOrgCollegEntiConvenz(param, idCollegEntiConvenz,
		    idEnteConvenz, appartCollegEntiSet);
	    getForm().getCollegamentiEnteList().getTable().remove(riga);
	    getMessageBox().addInfo("Collegamento eliminato con successo");
	    getMessageBox().setViewMode(ViewMode.plain);
	} catch (ParerUserError ex) {
	    getMessageBox().addError(
		    "Il collegamento non pu\u00F2 essere eliminato: " + ex.getDescription());
	    forwardToPublisher(getLastPublisher());
	}

	if (!getMessageBox().hasError()
		&& getLastPublisher().equals(Application.Publisher.DETTAGLIO_COLLEGAMENTO_ENTE)) {
	    goBack();
	} else {
	    forwardToPublisher(getLastPublisher());
	}

    }

    public void confermaDeleteCollegamento() throws EMFError {
	if (getSession().getAttribute("attributiDeleteCollegamento") != null) {
	    Object[] attributiDeleteCollegamento = (Object[]) getSession()
		    .getAttribute("attributiDeleteCollegamento");
	    LogParam param = (LogParam) attributiDeleteCollegamento[0];
	    BigDecimal idCollegEntiConvenz = (BigDecimal) attributiDeleteCollegamento[1];
	    BigDecimal idEnteConvenz = (BigDecimal) attributiDeleteCollegamento[2];
	    Set<BigDecimal> appartCollegEntiSet = (Set<BigDecimal>) attributiDeleteCollegamento[3];
	    int riga = (int) attributiDeleteCollegamento[4];
	    eseguiDeleteCollegamentoEnti(param, idCollegEntiConvenz, idEnteConvenz,
		    appartCollegEntiSet, riga);
	}
    }

    public void annullaDeleteCollegamento() {
	getSession().removeAttribute("attributiDeleteCollegamento");
	forwardToPublisher(getLastPublisher());
    }

    @Override
    public void deleteEntiCollegatiList() throws EMFError {
	OrgAppartCollegEntiRowBean currentRow = (OrgAppartCollegEntiRowBean) getForm()
		.getEntiCollegatiList().getTable().getCurrentRow();
	BigDecimal idAppartCollegEnti = currentRow.getIdAppartCollegEnti();
	BigDecimal idEnteConvenz = currentRow.getIdEnteConvenz();
	int riga = getForm().getEntiCollegatiList().getTable().getCurrentRowIndex();
	if (!getMessageBox().hasError() && idAppartCollegEnti != null) {

	    if (!getMessageBox().hasError()) {
		/* Codice aggiuntivo per il logging... */
		LogParam param = SpagoliteLogUtil.getLogParam(
			paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			Application.Publisher.DETTAGLIO_COLLEGAMENTO_ENTE);
		param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
			getForm().getEntiCollegatiList()));

		// Recupero la lista di utenti coinvolti nella cancellazione dell'ente convenzionato
		// dal collegamento
		List<String> nmUseridList2 = entiConvenzionatiEjb
			.getUtentiConAbilitazioniEntiCollegToDel2(idAppartCollegEnti);

		if (!nmUseridList2.isEmpty()) {
		    getRequest().setAttribute("numeroUtentiAppartenenzaCollegamento",
			    nmUseridList2.size());
		    getRequest().setAttribute("listaUtentiAppartenenzaCollegamento",
			    nmUseridList2.toString().replace("[", "").replace("]", ""));
		    getRequest().setAttribute("customDeleteAppartenenzaCollegamentoMessageBox",
			    true);

		    Object[] attributiDeleteAppartenenzaCollegamento = new Object[4];
		    attributiDeleteAppartenenzaCollegamento[0] = param;
		    attributiDeleteAppartenenzaCollegamento[1] = idAppartCollegEnti;
		    attributiDeleteAppartenenzaCollegamento[2] = idEnteConvenz;
		    attributiDeleteAppartenenzaCollegamento[3] = riga;
		    getSession().setAttribute("attributiDeleteAppartenenzaCollegamento",
			    attributiDeleteAppartenenzaCollegamento);
		    forwardToPublisher(getLastPublisher());

		} else {
		    eseguiDeleteAppartenenzaCollegamentiEnte(param, idAppartCollegEnti,
			    idEnteConvenz, riga);
		}
	    }

	} else {
	    getMessageBox().addError("L'associazione dell'ente non pu\u00F2 essere eliminata");
	    forwardToPublisher(getLastPublisher());
	}

    }

    public void eseguiDeleteAppartenenzaCollegamentiEnte(LogParam param,
	    BigDecimal idAppartCollegEnti, BigDecimal idEnteConvenz, int riga) throws EMFError {
	try {
	    entiConvenzionatiEjb.deleteOrgAppartCollegEnti(param, idAppartCollegEnti, idEnteConvenz,
		    getUser().getIdUtente());
	    getForm().getEntiCollegatiList().getTable().remove(riga);
	    getMessageBox()
		    .addInfo("Associazione dell'ente eliminata con successo dal collegamento");
	    getMessageBox().setViewMode(ViewMode.plain);
	    BigDecimal idCollegEntiConvenz = getForm().getCollegamentoEnteDetail()
		    .getId_colleg_enti_convenz().parse();
	    loadDettaglioCollegamento(idCollegEntiConvenz);
	    forwardToPublisher(getLastPublisher());

	} catch (ParerUserError ex) {
	    getMessageBox().addError("L'associazione dell'ente non pu\u00F2 essere eliminata: "
		    + ex.getDescription());
	    forwardToPublisher(getLastPublisher());
	}

    }

    public void confermaDeleteAppartenenzaCollegamento() throws EMFError {
	// try {
	if (getSession().getAttribute("attributiDeleteAppartenenzaCollegamento") != null) {
	    Object[] attributiDeleteAppartenenzaCollegamento = (Object[]) getSession()
		    .getAttribute("attributiDeleteAppartenenzaCollegamento");
	    LogParam param = (LogParam) attributiDeleteAppartenenzaCollegamento[0];
	    BigDecimal idAppartCollegEnti = (BigDecimal) attributiDeleteAppartenenzaCollegamento[1];
	    BigDecimal idEnteConvenz = (BigDecimal) attributiDeleteAppartenenzaCollegamento[2];
	    int riga = (int) attributiDeleteAppartenenzaCollegamento[3];

	    eseguiDeleteAppartenenzaCollegamentiEnte(param, idAppartCollegEnti, idEnteConvenz,
		    riga);
	}
	forwardToPublisher(getLastPublisher());
	SessionManager.removeLastExecutionHistory(getSession());
    }

    public void annullaDeleteAppartenenzaCollegamento() {
	getSession().removeAttribute("attributiDeleteAppartenenzaCollegamento");
	forwardToPublisher(getLastPublisher());
	SessionManager.removeLastExecutionHistory(getSession());
    }

    @Override
    public void deleteCollegamentiEnteAppartList() throws EMFError {
	OrgAppartCollegEntiRowBean currentRow = (OrgAppartCollegEntiRowBean) getForm()
		.getCollegamentiEnteAppartList().getTable().getCurrentRow();
	BigDecimal idAppartCollegEnti = currentRow.getIdAppartCollegEnti();
	BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam().parse();
	int riga = getForm().getCollegamentiEnteAppartList().getTable().getCurrentRowIndex();
	if (!getMessageBox().hasError() && idAppartCollegEnti != null) {
	    // try {
	    if (!getMessageBox().hasError()) {
		/* Codice aggiuntivo per il logging... */
		LogParam param = SpagoliteLogUtil.getLogParam(
			paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			SpagoliteLogUtil.getPageName(this));
		param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		String nomeAzione = null;
		if (SpagoliteLogUtil.getPageName(this)
			.equals(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO)) {
		    nomeAzione = SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
			    getForm().getCollegamentiEnteAppartList());
		} else {
		    nomeAzione = SpagoliteLogUtil.getToolbarDelete();
		}
		param.setNomeAzione(nomeAzione);

		// Recupero la lista di utenti coinvolti nella cancellazione del collegamento
		SortedSet<String> listaUtenti = entiConvenzionatiEjb
			.getUtentiConAbilitazioniAppartenenzaCollegamento(idAppartCollegEnti);

		List<String> listaUtenti2 = entiConvenzionatiEjb
			.getUtentiConAbilitazioniEntiCollegToDel2(idAppartCollegEnti);

		if (!listaUtenti.isEmpty()) {
		    getRequest().setAttribute("numeroUtentiAppartenenzaCollegamentoDaDettaglioEnte",
			    listaUtenti.size());
		    getRequest().setAttribute("listaUtentiAppartenenzaCollegamentoDaDettaglioEnte",
			    listaUtenti.toString().replace("[", "").replace("]", ""));
		    getRequest().setAttribute(
			    "customDeleteAppartenenzaCollegamentoDaDettaglioEnteMessageBox", true);
		    Object[] attributiDeleteAppartenenzaCollegamento = new Object[4];
		    attributiDeleteAppartenenzaCollegamento[0] = param;
		    attributiDeleteAppartenenzaCollegamento[1] = idAppartCollegEnti;
		    // attributiDeleteCollegamento[1] = idAppartCollegEnti;
		    attributiDeleteAppartenenzaCollegamento[2] = idEnteConvenz;
		    // attributiDeleteCollegamento[3] = appartCollegEntiSet;
		    attributiDeleteAppartenenzaCollegamento[3] = riga;
		    getSession().setAttribute(
			    "attributiDeleteAppartenenzaCollegamentoDaDettaglioEnte",
			    attributiDeleteAppartenenzaCollegamento);
		    forwardToPublisher(getLastPublisher());
		} else {
		    eseguiDeleteAppartenenzaCollegamentiEnteDaDettaglioEnte(param,
			    idAppartCollegEnti, idEnteConvenz, riga);
		}
	    }
	} else {
	    getMessageBox().addError("L'associazione dell'ente non pu\u00F2 essere eliminata");
	    forwardToPublisher(getLastPublisher());
	}
    }

    public void eseguiDeleteAppartenenzaCollegamentiEnteDaDettaglioEnte(LogParam param,
	    BigDecimal idAppartCollegEnti, BigDecimal idEnteConvenz, int riga) throws EMFError {
	try {
	    entiConvenzionatiEjb.deleteOrgAppartCollegEnti(param, idAppartCollegEnti, idEnteConvenz,
		    getUser().getIdUtente());
	    getForm().getCollegamentiEnteAppartList().getTable().remove(riga);
	    getMessageBox()
		    .addInfo("Associazione dell'ente eliminata con successo dal collegamento");
	    getMessageBox().setViewMode(ViewMode.plain);
	    goBackTo(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO);
	} catch (ParerUserError ex) {
	    getMessageBox().addError("L'associazione dell'ente non pu\u00F2 essere eliminata: "
		    + ex.getDescription());
	    forwardToPublisher(getLastPublisher());
	}
    }

    public void confermaDeleteAppartenenzaCollegamentoDaDettaglioEnte() throws EMFError {
	if (getSession()
		.getAttribute("attributiDeleteAppartenenzaCollegamentoDaDettaglioEnte") != null) {
	    Object[] attributiDeleteAppartenenzaCollegamentoDaDettaglioEnte = (Object[]) getSession()
		    .getAttribute("attributiDeleteAppartenenzaCollegamentoDaDettaglioEnte");
	    LogParam param = (LogParam) attributiDeleteAppartenenzaCollegamentoDaDettaglioEnte[0];
	    BigDecimal idAppartCollegEnti = (BigDecimal) attributiDeleteAppartenenzaCollegamentoDaDettaglioEnte[1];
	    BigDecimal idEnteConvenz = (BigDecimal) attributiDeleteAppartenenzaCollegamentoDaDettaglioEnte[2];
	    int riga = (int) attributiDeleteAppartenenzaCollegamentoDaDettaglioEnte[3];

	    eseguiDeleteAppartenenzaCollegamentiEnteDaDettaglioEnte(param, idAppartCollegEnti,
		    idEnteConvenz, riga);
	}
    }

    public void annullaDeleteAppartenenzaCollegamentoDaDettaglioEnte() {
	getSession().removeAttribute("attributiDeleteAppartenenzaCollegamentoDaDettaglioEnte");
	if (!getMessageBox().hasError()
		&& getLastPublisher().equals(Application.Publisher.DETTAGLIO_COLLEGAMENTO_ENTE)) {
	    goBack();
	} else {
	    forwardToPublisher(getLastPublisher());
	    SessionManager.removeLastExecutionHistory(getSession());
	}
    }

    @Override
    public JSONObject triggerServizioErogatoDetailId_tipo_servizioOnTrigger() throws EMFError {
	getForm().getServizioErogatoDetail().post(getRequest());
	BigDecimal idTipoServizio = getForm().getServizioErogatoDetail().getId_tipo_servizio()
		.parse();
	// Resetto i valori
	getForm().getServizioErogatoDetail().getNm_servizio_erogato().setValue("");
	getForm().getServizioErogatoDetail().getTipo_fatturazione().reset();
	getForm().getServizioErogatoDetail().getNm_tariffa().setValue("");
	getForm().getServizioErogatoDetail().getIm_valore_tariffa().reset();
	if (idTipoServizio != null) {
	    try {
		OrgTipoServizioRowBean tipoServizioRowBean = (OrgTipoServizioRowBean) entiConvenzionatiEjb
			.getOrgTipoServizioRowBean(idTipoServizio);
		getForm().getServizioErogatoDetail().getNm_servizio_erogato()
			.setValue(tipoServizioRowBean.getCdTipoServizio());
		getForm().getServizioErogatoDetail().getTipo_fatturazione()
			.setValue(tipoServizioRowBean.getTipoFatturazione());

		BigDecimal idClasseEnteConvenz = getForm().getAccordoDetail()
			.getId_classe_ente_convenz().parse();
		BigDecimal idTariffario = getForm().getAccordoDetail().getId_tariffario().parse();

		// Tariffa applicata e importo
		Object[] nmTariffaEImporto = (Object[]) entiConvenzionatiEjb
			.getNmTariffaEImporto(idTipoServizio, idTariffario, idClasseEnteConvenz);
		getForm().getServizioErogatoDetail().getNm_tariffa()
			.setValue((String) nmTariffaEImporto[0] != null
				? (String) nmTariffaEImporto[0]
				: null);

		getForm().getServizioErogatoDetail().getIm_valore_tariffa().reset();
		getForm().getServizioErogatoDetail().getIm_valore_tariffa()
			.setValue((BigDecimal) nmTariffaEImporto[1] != null
				? "" + (BigDecimal) nmTariffaEImporto[1]
				: null);
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
	    }
	}
	return getForm().getServizioErogatoDetail().asJSON();
    }

    @Override
    public JSONObject triggerServizioErogatoDetailId_sistema_versanteOnTrigger() throws EMFError {
	getForm().getServizioErogatoDetail().post(getRequest());
	BigDecimal idSistemaVersante = getForm().getServizioErogatoDetail().getId_sistema_versante()
		.parse();
	BigDecimal idTipoServizio = getForm().getServizioErogatoDetail().getId_tipo_servizio()
		.parse();
	getForm().getServizioErogatoDetail().getNm_servizio_erogato().setValue(" ");
	if (idTipoServizio != null) {
	    String nmTipoServizio = getForm().getServizioErogatoDetail().getId_tipo_servizio()
		    .getDecodedValue();
	    if (idSistemaVersante != null) {
		String nmSistemaVersante = getForm().getServizioErogatoDetail()
			.getId_sistema_versante().getDecodedValue();
		getForm().getServizioErogatoDetail().getNm_servizio_erogato()
			.setValue(nmTipoServizio + "_" + nmSistemaVersante);
	    } else {
		getForm().getServizioErogatoDetail().getNm_servizio_erogato()
			.setValue(nmTipoServizio);
	    }
	}
	return getForm().getServizioErogatoDetail().asJSON();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Gestione utenti archivisti">
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
			    Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO);
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
			    getForm().getUtentiArchivistiList()));
		    entiConvenzionatiEjb.deleteOrgEnteArkRif(param, idEnteArkRif);
		    getForm().getUtentiArchivistiList().getTable().remove(riga);
		    getMessageBox().addInfo(
			    "Utente archivista eliminato con successo dall'ente convenzionato");
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
    public void visualizzaUnitaDoc() throws EMFError {
    }

    @Override
    public JSONObject triggerEnteConvenzionatoDetailId_prov_sede_legaleOnTrigger() throws EMFError {
	getForm().getEnteConvenzionatoDetail().post(getRequest());
	BigDecimal idAmbitoTerrit = getForm().getEnteConvenzionatoDetail().getId_prov_sede_legale()
		.parse();
	try {
	    OrgAmbitoTerritRowBean ambitoTerritPadre = entiConvenzionatiEjb
		    .getOrgAmbitoTerritParentRowBean(idAmbitoTerrit);
	    // Valorizzo le combo Ambito territoriale / Regione e Ambito territoriale / Provincia...
	    getForm().getEnteConvenzionatoDetail().getCd_ambito_territ_regione()
		    .setValue("" + ambitoTerritPadre.getIdAmbitoTerrit());
	    List<BigDecimal> padre = new ArrayList<>();
	    padre.add(ambitoTerritPadre.getIdAmbitoTerrit());
	    getForm().getEnteConvenzionatoDetail().getCd_ambito_territ_provincia()
		    .setDecodeMap(DecodeMap.Factory.newInstance(
			    entiConvenzionatiEjb.getOrgAmbitoTerritTableBean(padre),
			    "id_ambito_territ", "cd_ambito_territ"));
	    getForm().getEnteConvenzionatoDetail().getCd_ambito_territ_provincia()
		    .setValue("" + idAmbitoTerrit);
	} catch (ParerUserError e) {
	    getMessageBox().addError(e.getDescription());
	}
	return getForm().getEnteConvenzionatoDetail().asJSON();
    }

    @Override
    public JSONObject triggerEnteConvenzionatoWizardDetailId_prov_sede_legaleOnTrigger()
	    throws EMFError {
	getForm().getEnteConvenzionatoWizardDetail().post(getRequest());
	BigDecimal idAmbitoTerrit = getForm().getEnteConvenzionatoWizardDetail()
		.getId_prov_sede_legale().parse();
	try {
	    OrgAmbitoTerritRowBean ambitoTerritPadre = entiConvenzionatiEjb
		    .getOrgAmbitoTerritParentRowBean(idAmbitoTerrit);
	    // Valorizzo le combo Ambito territoriale / Regione e Ambito territoriale / Provincia...
	    getForm().getEnteConvenzionatoWizardDetail().getCd_ambito_territ_regione()
		    .setValue("" + ambitoTerritPadre.getIdAmbitoTerrit());
	    List<BigDecimal> padre = new ArrayList<>();
	    padre.add(ambitoTerritPadre.getIdAmbitoTerrit());
	    getForm().getEnteConvenzionatoWizardDetail().getCd_ambito_territ_provincia()
		    .setDecodeMap(DecodeMap.Factory.newInstance(
			    entiConvenzionatiEjb.getOrgAmbitoTerritTableBean(padre),
			    "id_ambito_territ", "cd_ambito_territ"));
	    getForm().getEnteConvenzionatoWizardDetail().getCd_ambito_territ_provincia()
		    .setValue("" + idAmbitoTerrit);
	} catch (ParerUserError e) {
	    getMessageBox().addError(e.getDescription());
	}
	return getForm().getEnteConvenzionatoWizardDetail().asJSON();
    }

    // <editor-fold defaultstate="collapsed" desc="Gestione anagrafica">
    private void initAnagraficaDetail() throws ParerUserError, EMFError {
	getForm().getEnteConvenzionatoDetail().post(getRequest());
	BigDecimal idAmbienteEnteConvenzNuovo = getForm().getEnteConvenzionatoDetail()
		.getId_ambiente_ente_convenz_nuovo().parse();
	BigDecimal idEnteConvenzExcluded = getForm().getEnteConvenzionatoDetail().getId_ente_siam()
		.parse();
	try {
	    if (idAmbienteEnteConvenzNuovo != null) {
		getForm().getAnagraficaDetail().getId_ente_convenz_nuovo()
			.setDecodeMap(DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getOrgEnteConvenzNonCessatiTableBean(
					idAmbienteEnteConvenzNuovo, idEnteConvenzExcluded),
				"id_ente_siam", "nm_ente_siam"));
	    } else {
		getForm().getAnagraficaDetail().getId_ente_convenz_nuovo()
			.setDecodeMap(new DecodeMap());
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}

	getForm().getAnagraficaDetail().getTi_cd_ente_convenz()
		.setDecodeMap(ComboGetter.getMappaTiStatoRichGestUser());
	getForm().getAnagraficaDetail().getId_prov_sede_legale()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgAmbitoTerritTableBean(
				ConstOrgAmbitoTerrit.TiAmbitoTerrit.PROVINCIA.toString()),
			"id_ambito_territ", "cd_ambito_territ"));
	getForm().getAnagraficaDetail().getDs_categ_ente()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgCategEnteTableBean(), "id_categ_ente",
			"ds_categ_ente"));
	getForm().getAnagraficaDetail().getCd_ambito_territ_regione()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgAmbitoTerritTableBean(
				ConstOrgAmbitoTerrit.TiAmbitoTerrit.REGIONE_STATO.toString()),
			"id_ambito_territ", "cd_ambito_territ"));
	getForm().getAnagraficaDetail().getCd_ambito_territ_provincia()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgAmbitoTerritTableBean(
				ConstOrgAmbitoTerrit.TiAmbitoTerrit.PROVINCIA.toString()),
			"id_ambito_territ", "cd_ambito_territ"));
	getForm().getAnagraficaDetail().getCd_ambito_territ_forma_associata()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgAmbitoTerritTableBean(
				ConstOrgAmbitoTerrit.TiAmbitoTerrit.FORMA_ASSOCIATA.toString()),
			"id_ambito_territ", "cd_ambito_territ"));
    }

    private void loadDettaglioAnagrafica(BigDecimal idStoEnteConvenz)
	    throws ParerUserError, EMFError {
	// Carico il dettaglio anagrafica
	OrgStoEnteConvenzRowBean detailRow = entiConvenzionatiEjb
		.getOrgStoEnteConvenzRowBean(idStoEnteConvenz);
	getForm().getAnagraficaDetail().copyFromBean(detailRow);
	getForm().getAnagraficaDetail().getNm_ente_siam().setValue(detailRow.getNmEnteConvenz());

	// Metto tutto in view mode
	getForm().getAnagraficaDetail().setViewMode();
	getForm().getAnagraficaDetail().setStatus(Status.view);
	getForm().getAnagraficheList().setStatus(Status.view);
    }

    @Override
    public String getDefaultInserimentoWizardPublisher() throws EMFError {
	return Application.Publisher.DETTAGLIO_ANAGRAFICA_WIZARD;
    }

    /**
     * Ingresso primo step del wizard: - Non ho niente da fare
     *
     * @throws EMFError errore generico
     */
    @Override
    public void inserimentoWizardStep1OnEnter() throws EMFError {
    }

    /**
     * Uscita primo step del wizard: - Eseguo i controlli per rispondere se proseguire
     *
     * @return true/false, per capire se proseguire dopo i controlli
     *
     * @throws EMFError errore generico
     */
    @Override
    public boolean inserimentoWizardStep1OnExit() throws EMFError {
	// Controllo che la data di fine validità sia stata inserita
	boolean result = false;
	getForm().getAnagraficaDetail().post(getRequest());
	if (getForm().getAnagraficaDetail().validate(getMessageBox())) {
	    Date dtIniVal = getForm().getAnagraficaDetail().getDt_ini_val().parse();
	    Date dtFineVal = getForm().getAnagraficaDetail().getDt_fine_val().parse();
	    Date dtIniValEnte = getForm().getEnteConvenzionatoDetail().getDt_ini_val().parse();
	    Date dtFineValEnte = getForm().getEnteConvenzionatoDetail().getDt_cessazione().parse();
	    BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam()
		    .parse();
	    if (dtFineVal.before(dtIniVal)) {
		getMessageBox().addError(
			"Attenzione: data fine validit\u00E0  inferiore a data inizio validit\u00E0 ");
	    } else if (entiConvenzionatiEjb.isDateAnagraficaOutOfRange(dtIniValEnte, dtFineValEnte,
		    dtIniVal, dtFineVal)) {
		getMessageBox().addError(
			"Attenzione: date anagrafica esterne alla validit\u00E0  dell'ente ");
	    } else if (entiConvenzionatiEjb.existsDateSovrappostePrecAnagrafiche(idEnteConvenz,
		    dtIniVal, dtFineVal)) {
		getMessageBox().addError(
			"Attenzione: esistono precedenti anagrafiche le cui date di validit\u00E0 si sovrappongono a quelle dell'anagrafica che si sta inserendo ");
	    } else if (!entiConvenzionatiEjb.isDateAnagraficaContinue(dtIniVal, idEnteConvenz,
		    null)) {
		getMessageBox().addError(
			"Attenzione: si sta tentando di inserire una anagrafica le cui date di validit\u00E0 non sono continue ad anagrafiche precedenti ");
	    } else {
		getForm().getEnteConvenzionatoDetail().setEditMode();
		getForm().getEnteConvenzionatoDetail().getNm_ambiente_ente_convenz().setViewMode();
		getForm().getEnteConvenzionatoDetail().getDt_ini_val().setViewMode();
		getForm().getEnteConvenzionatoDetail().getDt_cessazione().setViewMode();
		getForm().getEnteConvenzionatoDetail().getId_ente_convenz_nuovo().setHidden(true);
		// Preparo i campi per il prossimo step
		getForm().getEnteConvenzionatoDetail().getNm_ente_siam().setValue("");
		result = true;
	    }
	}
	return result;
    }

    /**
     * Ingresso secondo step del wizard: - Non ho niente da fare
     *
     * @throws EMFError errore generico
     */
    @Override
    public void inserimentoWizardStep2OnEnter() throws EMFError {
	//
	getForm().getEnteConvenzionatoDetail().getNm_ambiente_ente_convenz().setHidden(true);
	getForm().getEnteConvenzionatoDetail().getId_ambiente_ente_convenz().setHidden(true);
	getForm().getEnteConvenzionatoDetail().getId_ambiente_ente_convenz_field().setHidden(true);
	getForm().getEnteConvenzionatoDetail().getDt_ini_val_appart_ambiente().setHidden(true);
	getForm().getEnteConvenzionatoDetail().getDt_fin_val_appart_ambiente().setHidden(true);
    }

    /**
     * Uscita secondo step del wizard: - Eseguo i controlli di validazione prima di procedere al
     * salvataggio
     *
     * @return true/false, per capire se proseguire dopo i controlli
     *
     * @throws EMFError errore generico
     */
    @Override
    public boolean inserimentoWizardStep2OnExit() throws EMFError {
	boolean result = true;
	getForm().getEnteConvenzionatoDetail().post(getRequest());
	getForm().getEnteConvenzionatoDetail().getId_ambiente_ente_convenz()
		.setValue(getForm().getEnteConvenzionatoDetail().getId_ambiente_ente_convenz_field()
			.parse().toPlainString());

	if (!getForm().getEnteConvenzionatoDetail().validate(getMessageBox())) {
	    result = false;
	}
	return result;
    }

    @Override
    public boolean inserimentoWizardOnSave() throws EMFError {
	// Eseguo il salvataggio dell'anagrafica
	boolean result = false;
	if (!getMessageBox().hasError()) {

	    result = true;

	    try {
		logger.info(getClass().getName() + " Inizio storicizzazione");
		BigDecimal idEnteConvez = getForm().getEnteConvenzionatoDetail().getId_ente_siam()
			.parse();
		StoEnteConvenzionatoBean stoCaBean = new StoEnteConvenzionatoBean(
			getForm().getAnagraficaDetail());
		EnteConvenzionatoBean enteBean = new EnteConvenzionatoBean(
			getForm().getEnteConvenzionatoDetail());
		enteBean.setId_ambiente_ente_convenz(getForm().getEnteConvenzionatoDetail()
			.getId_ambiente_ente_convenz_field().parse());
		/*
		 * Codice aggiuntivo per il logging...
		 */
		LogParam param = SpagoliteLogUtil.getLogParam(
			paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			SpagoliteLogUtil.getPageName(this));
		param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
		// Storicizzo
		entiConvenzionatiEjb.saveStoEnteConvenzionato(param, stoCaBean, enteBean,
			idEnteConvez);
		getMessageBox().addInfo("Ente storicizzato con successo!");
		getMessageBox().setViewMode(ViewMode.plain);
		loadDettaglioEnteConvenzionato(idEnteConvez);
		logger.info(getClass().getName() + " Fine storicizzazione");
		goBackTo(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO);
	    } catch (ParerUserError ex) {
		result = false;
		getMessageBox().addError(ex.getDescription());
	    }
	}
	return result;
    }

    @Override
    public void inserimentoWizardOnCancel() throws EMFError {
	getForm().getEnteConvenzionatoDetail().setViewMode();
	getForm().getAnagraficaDetail().setViewMode();
	goBackTo(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO);
    }

    @Override
    public JSONObject triggerAnagraficaDetailId_prov_sede_legaleOnTrigger() throws EMFError {
	getForm().getAnagraficaDetail().post(getRequest());
	BigDecimal idAmbitoTerrit = getForm().getAnagraficaDetail().getId_prov_sede_legale()
		.parse();
	try {
	    OrgAmbitoTerritRowBean ambitoTerritPadre = entiConvenzionatiEjb
		    .getOrgAmbitoTerritParentRowBean(idAmbitoTerrit);
	    // Valorizzo le combo Ambito territoriale / Regione e Ambito territoriale / Provincia...
	    getForm().getAnagraficaDetail().getCd_ambito_territ_regione()
		    .setValue("" + ambitoTerritPadre.getIdAmbitoTerrit());
	    List<BigDecimal> padre = new ArrayList<>();
	    padre.add(ambitoTerritPadre.getIdAmbitoTerrit());
	    getForm().getAnagraficaDetail().getCd_ambito_territ_provincia()
		    .setDecodeMap(DecodeMap.Factory.newInstance(
			    entiConvenzionatiEjb.getOrgAmbitoTerritTableBean(padre),
			    "id_ambito_territ", "cd_ambito_territ"));
	    getForm().getAnagraficaDetail().getCd_ambito_territ_provincia()
		    .setValue("" + idAmbitoTerrit);
	    BigDecimal idAmbienteEnteConvenz = getForm().getEnteConvenzionatoDetail()
		    .getId_ambiente_ente_convenz().parse();
	    BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam()
		    .parse();
	    getForm().getAnagraficaDetail().getFl_ente_regione().setValue(
		    entiConvenzionatiEjb.isInRegione(ambitoTerritPadre.getIdAmbitoTerrit(),
			    idAmbienteEnteConvenz, idEnteConvenz) ? "1" : "0");
	} catch (ParerUserError e) {
	    getMessageBox().addError(e.getDescription());
	}
	return getForm().getAnagraficaDetail().asJSON();
    }

    @Override
    public JSONObject triggerAnagraficaDetailCd_ambito_territ_regioneOnTrigger() throws EMFError {
	getForm().getAnagraficaDetail().post(getRequest());
	BigDecimal idAmbitoTerrit = getForm().getAnagraficaDetail().getCd_ambito_territ_regione()
		.parse();
	try {
	    if (idAmbitoTerrit != null) {
		List<BigDecimal> ids = new ArrayList<>();
		ids.add(idAmbitoTerrit);
		getForm().getAnagraficaDetail().getCd_ambito_territ_provincia()
			.setDecodeMap(DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getOrgAmbitoTerritTableBean(ids),
				"id_ambito_territ", "cd_ambito_territ"));
		getForm().getAnagraficaDetail().getCd_ambito_territ_forma_associata()
			.setDecodeMap(new DecodeMap());
		BigDecimal idAmbienteEnteConvenz = getForm().getEnteConvenzionatoDetail()
			.getId_ambiente_ente_convenz().parse();
		BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam()
			.parse();
		getForm().getAnagraficaDetail().getFl_ente_regione()
			.setValue(entiConvenzionatiEjb.isInRegione(idAmbitoTerrit,
				idAmbienteEnteConvenz, idEnteConvenz) ? "1" : "0");
	    } else {
		getForm().getAnagraficaDetail().getCd_ambito_territ_provincia()
			.setDecodeMap(new DecodeMap());
		getForm().getAnagraficaDetail().getCd_ambito_territ_forma_associata()
			.setDecodeMap(new DecodeMap());
		getForm().getAnagraficaDetail().getFl_ente_regione().setValue("0");
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
	return getForm().getAnagraficaDetail().asJSON();
    }

    @Override
    public JSONObject triggerAnagraficaDetailCd_ambito_territ_provinciaOnTrigger() throws EMFError {
	getForm().getAnagraficaDetail().post(getRequest());
	BigDecimal idAmbitoTerrit = getForm().getAnagraficaDetail().getCd_ambito_territ_provincia()
		.parse();
	try {
	    if (idAmbitoTerrit != null) {
		List<BigDecimal> ids = new ArrayList<>();
		ids.add(idAmbitoTerrit);
		getForm().getAnagraficaDetail().getCd_ambito_territ_forma_associata()
			.setDecodeMap(DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getOrgAmbitoTerritTableBean(ids),
				"id_ambito_territ", "cd_ambito_territ"));
	    } else {
		getForm().getAnagraficaDetail().getCd_ambito_territ_forma_associata()
			.setDecodeMap(new DecodeMap());
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
	return getForm().getAnagraficaDetail().asJSON();
    }

    // </editor-fold>
    @Override
    public void ricalcoloServiziErogati() throws EMFError {
	BigDecimal idAccordoEnte = getForm().getAccordoDetail().getId_accordo_ente().parse();
	BigDecimal idTipoAccordo = getForm().getAccordoDetail().getId_tipo_accordo().parse();
	/**
	 * Logging
	 */
	LogParam param = SpagoliteLogUtil.getLogParam(paramHelper.getParamApplicApplicationName(),
		getUser().getUsername(), SpagoliteLogUtil.getPageName(this));
	param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
	param.setNomeAzione(SpagoliteLogUtil.getButtonActionName(this.getForm(),
		this.getForm().getAccordoDetail(),
		this.getForm().getAccordoDetail().getRicalcoloServiziErogati().getName()));

	try {
	    entiConvenzionatiEjb.calcolaServiziErogatiDaDettaglioAccordo(param, idAccordoEnte);
	    getMessageBox().addInfo("Ricalcolo servizi erogati eseguito con successo");
	    loadDettaglioAccordo(idAccordoEnte, idTipoAccordo);
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_ACCORDO);
    }

    @Override
    public void logEventi() throws EMFError {
	GestioneLogEventiForm form = new GestioneLogEventiForm();
	form.getOggettoDetail().getNmApp().setValue(paramHelper.getParamApplicApplicationName());
	form.getOggettoDetail().getNm_tipo_oggetto()
		.setValue(SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO);
	form.getOggettoDetail().getIdOggetto()
		.setValue(getForm().getEnteConvenzionatoDetail().getId_ente_siam().getValue());
	redirectToAction(it.eng.parer.sacerlog.slite.gen.Application.Actions.GESTIONE_LOG_EVENTI,
		"?operation=inizializzaLogEventi", form);
    }

    @Override
    protected void postLoad() {
	super.postLoad();
	Object ogg = getForm();
	if (ogg instanceof AmministrazioneEntiConvenzionatiForm) {
	    AmministrazioneEntiConvenzionatiForm form = (AmministrazioneEntiConvenzionatiForm) ogg;
	    if (form.getListaEntiConvenzionati().getStatus().equals(Status.view)) {
		form.getEnteConvenzionatoDetail().getLogEventi().setEditMode();
		form.getEnteConvenzionatoDetail().getLogEventi().setHidden(false);
	    } else {
		form.getEnteConvenzionatoDetail().getLogEventi().setViewMode();
		form.getEnteConvenzionatoDetail().getLogEventi().setHidden(true);
	    }

	    if (getForm().getEnteConvenzionatoDetail().getStatus() != null) {
		if (getForm().getEnteConvenzionatoDetail().getStatus().equals(Status.view)
			&& form.getListaEntiConvenzionati() != null
			&& form.getListaEntiConvenzionati().getStatus().equals(Status.view)
			&& getSession().getAttribute("navTableEntiConvenz") != null
			&& !getSession().getAttribute("navTableEntiConvenz").equals(
				AmministrazioneEntiConvenzionatiForm.EntiCessatiList.NAME)) {
		    try {
			/*
			 * Se l\u0027utente appartiene ad un ente convenzionato di tipo PRODUTTORE e
			 * se appartiene all'ente amministratore o all'ente conservatore definiti
			 * sull'ultimo accordo definito sull'ente
			 */
			BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail()
				.getId_ente_siam().parse();
			BigDecimal idUserIamCor = new BigDecimal(getUser().getIdUtente());

			form.getEnteConvenzionatoDetail().getEstraiRigheFattureEnte().setEditMode();

			if (entiConvenzionatiEjb.checkUtenteAbilLastAccordo(idUserIamCor,
				idEnteConvenz)
				&& form.getEnteConvenzionatoDetail().getTi_ente_convenz().parse()
					.equals("PRODUTTORE")
				&& form.getEnteConvenzionatoDetail().getFl_chiuso().parse()
					.equals("0")) {
			    form.getEnteConvenzionatoDetail().getNuovoAccordo().setEditMode();
			    form.getEnteConvenzionatoDetail().getNuovoAccordo().setHidden(false);
			} else {
			    form.getEnteConvenzionatoDetail().getNuovoAccordo().setViewMode();
			    form.getEnteConvenzionatoDetail().getNuovoAccordo().setHidden(true);
			}

			if (entiConvenzionatiEjb.checkUtenteAbilLastAccordo(idUserIamCor,
				idEnteConvenz)
				&& entiConvenzionatiEjb.checkAccordoValido(idEnteConvenz)) {
			    form.getEnteConvenzionatoDetail().getCambioAmbiente().setEditMode();
			    form.getEnteConvenzionatoDetail().getCambioAmbiente().setHidden(false);
			} else {
			    form.getEnteConvenzionatoDetail().getCambioAmbiente().setViewMode();
			    form.getEnteConvenzionatoDetail().getCambioAmbiente().setHidden(true);
			}

			if (entiConvenzionatiEjb.checkUtenteAbilLastAccordo(idUserIamCor,
				idEnteConvenz)
				&& form.getEnteConvenzionatoDetail().getTi_ente_convenz().parse()
					.equals("PRODUTTORE")
				&& entiConvenzionatiEjb.checkAccordoValido(idEnteConvenz)) {
			    form.getEnteConvenzionatoDetail().getCambioGestore().setEditMode();
			    form.getEnteConvenzionatoDetail().getCambioGestore().setHidden(false);
			} else {
			    form.getEnteConvenzionatoDetail().getCambioGestore().setViewMode();
			    form.getEnteConvenzionatoDetail().getCambioGestore().setHidden(true);
			}

		    } catch (EMFError ex) {
			getMessageBox().addError("Errore durante il caricamento dei bottoni");
		    }
		} else {
		    form.getEnteConvenzionatoDetail().getNuovoAccordo().setViewMode();
		    form.getEnteConvenzionatoDetail().getNuovoAccordo().setHidden(true);

		    form.getEnteConvenzionatoDetail().getCambioAmbiente().setViewMode();
		    form.getEnteConvenzionatoDetail().getCambioAmbiente().setHidden(true);

		    form.getEnteConvenzionatoDetail().getCambioGestore().setViewMode();
		    form.getEnteConvenzionatoDetail().getCambioGestore().setHidden(true);
		}
	    }

	    if (getForm().getGestioneAccordoDetail().getStatus() != null) {
		if (getForm().getGestioneAccordoDetail().getStatus().equals(Status.view)) {
		    // Se sono in visualizzazione nascondo il campo per inserire il blobbo
		    try {
			if (getForm().getGestioneAccordoDetail().getBl_gest_accordo()
				.parse() == null
				&& getForm().getGestioneAccordoDetail().getNm_file_gest_accordo()
					.parse() == null) {
			    getForm().getGestioneAccordoDetail().getDownloadFileGestAccordo()
				    .setHidden(true);
			    getForm().getGestioneAccordoDetail().getNm_file_gest_accordo()
				    .setHidden(true);
			} else {
			    getForm().getGestioneAccordoDetail().getDownloadFileGestAccordo()
				    .setDisableHourGlass(true);
			    getForm().getGestioneAccordoDetail().getDownloadFileGestAccordo()
				    .setHidden(false);
			    getForm().getGestioneAccordoDetail().getNm_file_gest_accordo()
				    .setHidden(false);
			}

			if (getForm().getGestioneAccordoDetail().getId_gest_accordo_risposta()
				.parse() == null) {
			    getForm().getGestioneAccordoDetail().getVisualizzaRispGestAccordo()
				    .setHidden(true);
			} else {
			    getForm().getGestioneAccordoDetail().getVisualizzaRispGestAccordo()
				    .setHidden(false);
			}
		    } catch (EMFError ex) {
			getMessageBox().addError("Errore durante il recupero del nome file");
		    }
		    getForm().getGestioneAccordoDetail().getDownloadFileGestAccordo().setEditMode();
		    getForm().getGestioneAccordoDetail().getVisualizzaRispGestAccordo()
			    .setEditMode();
		} else {
		    // Se sono in edit mostro il campo per inserire il blobbo e nascondo bottone di
		    // download e campo del
		    // nome file
		    getForm().getGestioneAccordoDetail().getBl_gest_accordo().setHidden(false);
		    getForm().getGestioneAccordoDetail().getDownloadFileGestAccordo()
			    .setHidden(true);
		    getForm().getGestioneAccordoDetail().getDownloadFileGestAccordo().setViewMode();
		    getForm().getGestioneAccordoDetail().getNm_file_gest_accordo().setHidden(true);
		    getForm().getGestioneAccordoDetail().getVisualizzaRispGestAccordo()
			    .setHidden(true);
		    getForm().getGestioneAccordoDetail().getVisualizzaRispGestAccordo()
			    .setViewMode();
		}
	    }

	    if (getForm().getModuloInformazioniDetail().getStatus() != null) {
		if (getForm().getModuloInformazioniDetail().getStatus().equals(Status.view)) {
		    // Nascondo il campo per caricare il file
		    getForm().getModuloInformazioniDetail().getBl_modulo_info().setHidden(true);
		    // // Visualizzo il bottone di download del file
		    getForm().getModuloInformazioniDetail().getDownloadFile().setHidden(true);
		    getForm().getModuloInformazioniDetail().getDownloadFile().setEditMode();
		    try {
			BigDecimal idModuloInfo = getForm().getModuloInformazioniDetail()
				.getId_modulo_info_accordo().parse();
			if (entiConvenzionatiEjb.isNmFileModuloInfoPresente(idModuloInfo)) {
			    getForm().getModuloInformazioniDetail().getDownloadFile()
				    .setHidden(false);
			}

			if (getForm().getModuloInformazioniDetail().getCd_registro_modulo_info()
				.parse() != null
				&& getForm().getModuloInformazioniDetail().getAa_modulo_info()
					.parse() != null
				&& getForm().getModuloInformazioniDetail().getCd_key_modulo_info()
					.parse() != null) {
			    getForm().getUdButtonList().getScaricaCompFileUdModInfo()
				    .setDisableHourGlass(true);
			    getForm().getUdButtonList().getScaricaCompFileUdModInfo().setEditMode();
			} else {
			    getForm().getUdButtonList().getScaricaCompFileUdModInfo().setViewMode();
			}

		    } catch (EMFError ex) {
			getMessageBox().addError("Errore durante il recupero del nome file");
		    }
		    getForm().getModuloInformazioniDetail().getDownloadFile().setEditMode();
		} else {
		    getForm().getModuloInformazioniDetail().getBl_modulo_info().setHidden(false);
		    getForm().getModuloInformazioniDetail().getDownloadFile().setHidden(true);
		    getForm().getModuloInformazioniDetail().getDownloadFile().setViewMode();
		    getForm().getUdButtonList().getScaricaCompFileUdModInfo().setViewMode();
		}
	    }
	    if (getForm().getReferenteEnteDetail().getStatus() != null) {
		getForm().getReferenteEnteDetail().getBl_ente_user_rif().setHidden(false);
		// // Visualizzo il bottone di download del file
		getForm().getReferenteEnteDetail().getDownloadFileEnteUserRif().setHidden(true);
		getForm().getReferenteEnteDetail().getDownloadFileEnteUserRif().setEditMode();
		try {
		    BigDecimal identeUser = getForm().getReferenteEnteDetail().getId_ente_user_rif()
			    .parse();
		    if (identeUser != null
			    && entiConvenzionatiEjb.isNmFileReferentePresente(identeUser)) {
			getForm().getReferenteEnteDetail().getDownloadFileEnteUserRif()
				.setHidden(false);
		    }
		    getForm().getUdButtonList().getScaricaCompFileUdEnteUserRif().setViewMode();

		    if (getForm().getReferenteEnteDetail().getId_ente_user_rif() != null
			    && (getForm().getReferenteEnteDetail().getCd_registro_ente_user_rif()
				    .parse() != null
				    && getForm().getReferenteEnteDetail().getAa_ente_user_rif()
					    .parse() != null
				    && getForm().getReferenteEnteDetail().getCd_key_ente_user_rif()
					    .parse() != null)) {
			getForm().getUdButtonList().getScaricaCompFileUdEnteUserRif()
				.setDisableHourGlass(true);
			getForm().getUdButtonList().getScaricaCompFileUdEnteUserRif().setEditMode();
		    } else {
			getForm().getUdButtonList().getScaricaCompFileUdEnteUserRif().setViewMode();
		    }
		} catch (EMFError ex) {
		    getMessageBox().addError("Errore durante il recupero del nome file");
		}
	    }

	    if (getForm().getAccordoDetail().getStatus() != null) {
		// Se il flag è "checked" ripristino i campi che sono stati messi in stato
		// "readonly" dal
		// trigger.
		// I suddetti campi devono essere gestiti dal framework come editabili e non in sola
		// lettura.
		String flAccNonApprov = (String) getRequest().getParameter(
			getForm().getAccordoDetail().getFl_acc_non_approv().getName());
		if (flAccNonApprov != null) { // checked
		    // SECTION: ID Accordo
		    getForm().getAccordoDetail().getCd_registro_repertorio().setReadonly(false);
		    getForm().getAccordoDetail().getAa_repertorio().setReadonly(false);
		    getForm().getAccordoDetail().getCd_key_repertorio().setReadonly(false);
		    getForm().getAccordoDetail().getDt_reg_accordo().setReadonly(false);
		    // end SECTION

		    // SECTION: Informazioni sull'accordo
		    getForm().getAccordoDetail().getDt_atto_accordo().setReadonly(false);
		    getForm().getAccordoDetail().getDt_dec_accordo().setReadonly(false);
		    getForm().getAccordoDetail().getDt_fine_valid_accordo().setReadonly(false);
		    getForm().getAccordoDetail().getDt_scad_accordo().setReadonly(false);
		    getForm().getAccordoDetail().getDs_atto_accordo().setReadonly(false);
		    // end SECTION
		}

		if (getForm().getAccordoDetail().getStatus().equals(Status.view)) {
		    try {
			if (getForm().getAccordoDetail().getCd_registro_repertorio().parse() != null
				&& getForm().getAccordoDetail().getAa_repertorio().parse() != null
				&& getForm().getAccordoDetail().getCd_key_repertorio()
					.parse() != null) {
			    getForm().getUdButtonList().getScaricaCompFileUdAccordo()
				    .setDisableHourGlass(true);
			    getForm().getUdButtonList().getScaricaCompFileUdAccordo().setEditMode();
			} else {
			    getForm().getUdButtonList().getScaricaCompFileUdAccordo().setViewMode();
			}
			if (getForm().getAccordoDetail().getCd_registro_determina().parse() != null
				&& getForm().getAccordoDetail().getAa_determina().parse() != null
				&& getForm().getAccordoDetail().getCd_key_determina()
					.parse() != null) {
			    getForm().getUdButtonList().getScaricaCompFileUdAccordoAdozione()
				    .setDisableHourGlass(true);
			    getForm().getUdButtonList().getScaricaCompFileUdAccordoAdozione()
				    .setEditMode();
			} else {
			    getForm().getUdButtonList().getScaricaCompFileUdAccordoAdozione()
				    .setViewMode();
			}
		    } catch (EMFError ex) {
			getMessageBox().addError(
				"Errore durante il caricamento del bottone Scarica file unit\u00E0Â  documentaria");
		    }

		    try {
			BigDecimal idAccordoEnte = getForm().getAccordoDetail().getId_accordo_ente()
				.parse();
			if (idAccordoEnte != null) {
			    OrgEnteSiamRowBean rb = entiConvenzionatiEjb
				    .getOrgEnteSiamRowBeanByAccordo(idAccordoEnte);
			    if (rb != null) {
				String tiEnteConvenz = entiConvenzionatiEjb
					.getOrgEnteSiamRowBeanByAccordo(idAccordoEnte)
					.getTiEnteConvenz();
				BigDecimal idUserIamCor = new BigDecimal(getUser().getIdUtente());

				if (entiConvenzionatiEjb.checkUtenteAbilChiusuraAccordo(
					idUserIamCor, idAccordoEnte,
					TiEnteConvenz.valueOf(tiEnteConvenz))
					&& !entiConvenzionatiEjb
						.checkAccordoChiuso(idAccordoEnte)) {
				    getForm().getAccordoDetail().getChiusuraAccordo().setEditMode();
				    getForm().getAccordoDetail().getChiusuraAccordo()
					    .setHidden(false);
				} else {
				    getForm().getAccordoDetail().getChiusuraAccordo().setViewMode();
				    getForm().getAccordoDetail().getChiusuraAccordo()
					    .setHidden(true);
				}
			    }
			}
		    } catch (EMFError ex) {
			getMessageBox()
				.addError("Errore durante il caricamento del bottone Chiusura");
		    } catch (ParerInternalError e) {
			getMessageBox().addError(e.getDescription());
		    }
		} else {
		    getForm().getUdButtonList().getScaricaCompFileUdAccordo().setViewMode();
		    getForm().getUdButtonList().getScaricaCompFileUdAccordoAdozione().setViewMode();
		    getForm().getAccordoDetail().getChiusuraAccordo().setViewMode();
		}

		if (getForm().getAccordoDetail().getStatus().equals(Status.view)
			|| getForm().getAccordoDetail().getStatus().equals(Status.update)) {
		    getForm().getAccordoDetail().getFl_acc_non_approv().setHidden(true);
		} else {
		    getForm().getAccordoDetail().getFl_acc_non_approv().setHidden(false);
		}
	    }

	    if (getForm().getGestioneAccordoDetail().getStatus() != null) {
		if (getForm().getGestioneAccordoDetail().getStatus().equals(Status.view)) {
		    try {
			if (getForm().getGestioneAccordoDetail().getCd_registro_gest_accordo()
				.parse() != null
				&& getForm().getGestioneAccordoDetail().getAa_gest_accordo()
					.parse() != null
				&& getForm().getGestioneAccordoDetail().getCd_key_gest_accordo()
					.parse() != null) {
			    getForm().getUdButtonList().getScaricaCompFileUdGestAccordo()
				    .setHidden(false);
			    getForm().getUdButtonList().getScaricaCompFileUdGestAccordo()
				    .setDisableHourGlass(true);
			    getForm().getUdButtonList().getScaricaCompFileUdGestAccordo()
				    .setEditMode();
			} else {
			    getForm().getUdButtonList().getScaricaCompFileUdGestAccordo()
				    .setHidden(true);
			    getForm().getUdButtonList().getScaricaCompFileUdGestAccordo()
				    .setViewMode();
			}
		    } catch (EMFError ex) {
			getMessageBox().addError(
				"Errore durante il caricamento del bottone Scarica file unit\u00E0Â  documentaria");
		    }
		} else {
		    getForm().getUdButtonList().getScaricaCompFileUdGestAccordo().setHidden(true);
		    getForm().getUdButtonList().getScaricaCompFileUdGestAccordo().setViewMode();
		}
	    }

	    if (getForm().getDisciplinareTecnicoDetail().getStatus() != null) {
		if (getForm().getDisciplinareTecnicoDetail().getStatus().equals(Status.view)) {
		    // Nascondo il campo per caricare il file
		    getForm().getDisciplinareTecnicoDetail().getBl_discip_strut().setHidden(true);
		    // // Visualizzo il bottone di download del file
		    getForm().getDisciplinareTecnicoDetail().getDownloadFileDiscip()
			    .setHidden(false);
		    getForm().getDisciplinareTecnicoDetail().getDownloadFileDiscip().setEditMode();
		    try {
			if (getForm().getDisciplinareTecnicoDetail().getCd_registro_discip_strut()
				.parse() != null
				&& getForm().getDisciplinareTecnicoDetail().getAa_discip_strut()
					.parse() != null
				&& getForm().getDisciplinareTecnicoDetail().getCd_key_discip_strut()
					.parse() != null) {
			    // Visualizzo il bottone di scarica ud
			    getForm().getUdButtonList().getScaricaCompFileUdDiscip()
				    .setHidden(false);
			    getForm().getUdButtonList().getScaricaCompFileUdDiscip().setEditMode();
			} else {
			    getForm().getUdButtonList().getScaricaCompFileUdDiscip()
				    .setHidden(true);
			    getForm().getUdButtonList().getScaricaCompFileUdDiscip().setViewMode();
			}
		    } catch (EMFError ex) {
			getMessageBox().addError(
				"Errore durante il caricamento del bottone Scarica file unit\u00E0Â  documentaria");
		    }
		    // Nascondo il bottone di cancella file
		    getForm().getDisciplinareTecnicoDetail().getCancellaFileDiscip()
			    .setHidden(true);
		    getForm().getDisciplinareTecnicoDetail().getCancellaFileDiscip().setViewMode();
		    try {
			if (getForm().getDisciplinareTecnicoDetail().getFl_inserito_manualmente()
				.parse().equals("0")) {
			    // Nascondo il bottone di scarica ud
			    getForm().getUdButtonList().getScaricaCompFileUdDiscip()
				    .setHidden(true);
			    getForm().getUdButtonList().getScaricaCompFileUdDiscip().setViewMode();
			}
		    } catch (EMFError ex) {
			getMessageBox().addError(
				"Errore durante il caricamento del bottone Cancella file");
		    }
		} else {
		    // Nascondo i bottoni di download file e scarica ud
		    getForm().getDisciplinareTecnicoDetail().getDownloadFileDiscip()
			    .setHidden(true);
		    getForm().getDisciplinareTecnicoDetail().getDownloadFileDiscip().setViewMode();
		    getForm().getUdButtonList().getScaricaCompFileUdDiscip().setHidden(true);
		    getForm().getUdButtonList().getScaricaCompFileUdDiscip().setViewMode();
		    // Mostro il bottone di cancella file
		    getForm().getDisciplinareTecnicoDetail().getCancellaFileDiscip()
			    .setHidden(false);
		    getForm().getDisciplinareTecnicoDetail().getCancellaFileDiscip().setEditMode();
		    // E il campo per caricare il file
		    getForm().getDisciplinareTecnicoDetail().getBl_discip_strut().setHidden(false);
		    try {
			if (getForm().getDisciplinareTecnicoDetail().getFl_inserito_manualmente()
				.parse().equals("0")) {
			    getForm().getDisciplinareTecnicoDetail().getBl_discip_strut()
				    .setHidden(true);
			    // Nascondo il bottone di cancella file
			    getForm().getDisciplinareTecnicoDetail().getCancellaFileDiscip()
				    .setHidden(true);
			    getForm().getDisciplinareTecnicoDetail().getCancellaFileDiscip()
				    .setViewMode();
			}
		    } catch (EMFError ex) {
			getMessageBox().addError(
				"Errore durante il caricamento del bottone Cancella file");
		    }
		}
	    }
	}
    }

    @Override
    public void process() throws EMFError {
	logger.debug(">>>PROCESS");
	boolean isMultipart = ServletFileUpload.isMultipartContent(getRequest());
	if (isMultipart) {
	    if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_REFERENTE_ENTE)) {
		try {
		    int fileSize = ConfigSingleton.getInstance()
			    .getIntValue(MODULO_INFORMAZIONI_MAX_FILE_SIZE.name());
		    String[] a = getForm().getReferenteEnteDetail().postMultipart(getRequest(),
			    fileSize);

		    if (a != null) {
			String operationMethod = a[0];
			String[] navigationParams = Arrays.copyOfRange(a, 1, a.length);

			if (navigationParams != null && navigationParams.length > 0) {
			    Method method = AmministrazioneEntiConvenzionatiAction.class
				    .getMethod(operationMethod, String[].class);
			    method.invoke(this, (Object) navigationParams);

			} else {
			    Method method = AmministrazioneEntiConvenzionatiAction.class
				    .getMethod(operationMethod);
			    method.invoke(this);
			}
		    }

		} catch (FileUploadException | SecurityException | IllegalArgumentException
			| NoSuchMethodException | IllegalAccessException
			| InvocationTargetException ex) {
		    logger.error("Errore nell'invocazione del metodo di navigazione  :"
			    + ExceptionUtils.getRootCauseMessage(ex), ex);
		    getMessageBox().addError("Errore nella navigazione ");
		    goBack();
		}
	    } else if (getLastPublisher()
		    .equals(Application.Publisher.DETTAGLIO_MODULO_INFORMAZIONI)) {
		try {
		    int fileSize = ConfigSingleton.getInstance()
			    .getIntValue(MODULO_INFORMAZIONI_MAX_FILE_SIZE.name());
		    String[] a = getForm().getModuloInformazioniDetail().postMultipart(getRequest(),
			    fileSize);

		    if (a != null) {
			String operationMethod = a[0];
			String[] navigationParams = Arrays.copyOfRange(a, 1, a.length);

			if (navigationParams != null && navigationParams.length > 0) {
			    Method method = AmministrazioneEntiConvenzionatiAction.class
				    .getMethod(operationMethod, String[].class);
			    method.invoke(this, (Object) navigationParams);

			} else {
			    Method method = AmministrazioneEntiConvenzionatiAction.class
				    .getMethod(operationMethod);
			    method.invoke(this);
			}
		    }

		} catch (FileUploadException | SecurityException | IllegalArgumentException
			| NoSuchMethodException | IllegalAccessException
			| InvocationTargetException ex) {
		    logger.error("Errore nell'invocazione del metodo di navigazione  :"
			    + ExceptionUtils.getRootCauseMessage(ex), ex);
		    getMessageBox().addError("Errore nella navigazione ");
		    goBack();
		}
	    } else if (getLastPublisher()
		    .equals(Application.Publisher.DETTAGLIO_VARIAZIONE_ACCORDO)) {
		try {
		    int fileSize = ConfigSingleton.getInstance()
			    .getIntValue(VARIAZIONE_ACCORDO_MAX_FILE_SIZE.name());
		    String[] a = getForm().getGestioneAccordoDetail().postMultipart(getRequest(),
			    fileSize);

		    if (a != null) {
			String operationMethod = a[0];
			String[] navigationParams = Arrays.copyOfRange(a, 1, a.length);

			if (navigationParams != null && navigationParams.length > 0) {
			    Method method = AmministrazioneEntiConvenzionatiAction.class
				    .getMethod(operationMethod, String[].class);
			    method.invoke(this, (Object) navigationParams);

			} else {
			    Method method = AmministrazioneEntiConvenzionatiAction.class
				    .getMethod(operationMethod);
			    method.invoke(this);
			}
		    }

		} catch (FileUploadException | SecurityException | IllegalArgumentException
			| NoSuchMethodException | IllegalAccessException
			| InvocationTargetException ex) {
		    logger.error("Errore nell'invocazione del metodo di navigazione :"
			    + ExceptionUtils.getRootCauseMessage(ex), ex);
		    getMessageBox().addError("Errore nella navigazione ");
		    goBack();
		}
	    } else if (getLastPublisher()
		    .equals(Application.Publisher.DETTAGLIO_DISCIPLINARE_TECNICO)) {
		try {
		    int fileSize = ConfigSingleton.getInstance()
			    .getIntValue(DISCIPLINARE_TECNICO_MAX_FILE_SIZE.name());
		    String[] a = getForm().getDisciplinareTecnicoDetail()
			    .postMultipart(getRequest(), fileSize);

		    if (a != null) {
			String operationMethod = a[0];
			String[] navigationParams = Arrays.copyOfRange(a, 1, a.length);

			if (navigationParams != null && navigationParams.length > 0) {
			    Method method = AmministrazioneEntiConvenzionatiAction.class
				    .getMethod(operationMethod, String[].class);
			    method.invoke(this, (Object) navigationParams);

			} else {
			    Method method = AmministrazioneEntiConvenzionatiAction.class
				    .getMethod(operationMethod);
			    method.invoke(this);
			}
		    }

		} catch (FileUploadException | SecurityException | IllegalArgumentException
			| NoSuchMethodException | IllegalAccessException
			| InvocationTargetException ex) {
		    logger.error("Errore nell'invocazione del metodo di navigazione  :"
			    + ExceptionUtils.getRootCauseMessage(ex), ex);
		    getMessageBox().addError("Errore nella navigazione ");
		    goBack();
		}
	    }
	}
    }

    @Override
    public void visualizzaRispGestAccordo() throws EMFError {
	try {
	    // Carico la risposta gestione accordo
	    BigDecimal idGestAccordoRisposta = getForm().getGestioneAccordoDetail()
		    .getId_gest_accordo_risposta().parse();
	    /*
	     * Utilizzo idGestAccStack per navigare tra le gestioni accordo, inserendo nello stack
	     * l'id della gestione in cui mi trovo prima di passare alla successiva
	     */
	    List<BigDecimal> idGestAccStack = getIdGestAccStack();
	    idGestAccStack.add(getForm().getGestioneAccordoDetail().getId_gest_accordo().parse());
	    getSession().setAttribute("idGestAccStack", idGestAccStack);
	    loadDettaglioGestioneAccordo(idGestAccordoRisposta);
	    postLoad();

	    if (getForm().getFakeGestioniAccordoList().getTable() == null) {
		getForm().getFakeGestioniAccordoList().setTable(new BaseTable());
	    } else {
		getForm().getFakeGestioniAccordoList().getTable().last();
	    }
	    BaseRow row = new BaseRow();
	    row.setBigDecimal("id_gest_accordo", idGestAccordoRisposta);
	    getForm().getFakeGestioniAccordoList().getTable().add(row);
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}

	SessionManager.addPrevExecutionToHistory(getSession(), false, true);
	forwardToPublisher(Application.Publisher.DETTAGLIO_VARIAZIONE_ACCORDO);
    }

    /**
     * Metodo che fornisce uno stack utilizzato per mantenere gli id delle gestioni accordo
     * visualizzate
     *
     * @return lo stack di gestioni accordo
     */
    public List<BigDecimal> getIdGestAccStack() {
	if (getSession().getAttribute("idGestAccStack") == null) {
	    getSession().setAttribute("idGestAccStack", new ArrayList<BigDecimal>());
	}
	return (List<BigDecimal>) getSession().getAttribute("idGestAccStack");
    }

    @Override
    public void downloadFileGestAccordo() throws EMFError {
	BigDecimal idGestAccordo = getForm().getGestioneAccordoDetail().getId_gest_accordo()
		.parse();
	downloadFileCommonGestAccordo(idGestAccordo);
    }

    @Override
    public void downloadFile() throws EMFError {
	BigDecimal idModello = getForm().getModuloInformazioniDetail().getId_modulo_info_accordo()
		.parse();
	downloadFileCommon(idModello);
    }

    @Override
    public void downloadFileEnteUserRif() throws EMFError {
	BigDecimal idEnteUserRif = getForm().getReferenteEnteDetail().getId_ente_user_rif().parse();
	if (idEnteUserRif == null) {
	    OrgEnteUserRifRowBean row = (OrgEnteUserRifRowBean) getForm().getReferentiEnteList()
		    .getTable().getCurrentRow();
	    idEnteUserRif = row.getIdEnteUserRif();
	}
	downloadFileCommonReferente(idEnteUserRif);
    }

    private void downloadFileCommonGestAccordo(BigDecimal idGestAccordo) throws EMFError {
	File tmpFile = null;
	FileOutputStream out = null;
	try {
	    Object[] rec = entiConvenzionatiEjb.getFileGestioneAccordo(idGestAccordo);
	    // Controllo per scrupolo
	    if (rec[1] == null) {
		getMessageBox().addError("Non c'\u00E8 alcun file da scaricare<br/>");
	    } else {
		String nmFileGestAccordo = (String) rec[0];
		byte[] blGestAccordo = (byte[]) rec[1];
		tmpFile = new File(System.getProperty("java.io.tmpdir"), nmFileGestAccordo);
		out = new FileOutputStream(tmpFile);
		IOUtils.write(blGestAccordo, out);

		getRequest().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_ACTION.name(),
			getControllerName());
		getSession().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_FILENAME.name(),
			tmpFile.getName());
		getSession().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_FILEPATH.name(),
			tmpFile.getPath());
		getSession().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_DELETEFILE.name(),
			Boolean.toString(true));
		getSession().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_CONTENTTYPE.name(),
			WebConstants.MIME_TYPE_GENERIC);
	    }
	} catch (Exception ex) {
	    logger.error("Errore in download " + ExceptionUtils.getRootCauseMessage(ex), ex);
	    getMessageBox().addError("Errore inatteso nella preparazione del download<br/>");
	} finally {
	    IOUtils.closeQuietly(out);
	}

	if (getMessageBox().hasError()) {
	    forwardToPublisher(getLastPublisher());
	} else {
	    forwardToPublisher(Application.Publisher.DOWNLOAD_PAGE);
	}

    }

    private void downloadFileCommon(BigDecimal idModello) throws EMFError {
	File tmpFile = null;
	FileOutputStream out = null;

	try {
	    OrgModuloInfoAccordo rec = entiConvenzionatiHelper.findById(OrgModuloInfoAccordo.class,
		    idModello);
	    // Controllo per scrupolo
	    if (rec.getBlModuloInfo() == null) {
		getMessageBox().addError("Non c'\u00E8 alcun file da scaricare<br/>");
	    } else {
		tmpFile = new File(System.getProperty("java.io.tmpdir"), rec.getNmFileModuloInfo());
		out = new FileOutputStream(tmpFile);
		IOUtils.write(rec.getBlModuloInfo(), out);

		getRequest().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_ACTION.name(),
			getControllerName());
		getSession().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_FILENAME.name(),
			tmpFile.getName());
		getSession().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_FILEPATH.name(),
			tmpFile.getPath());
		getSession().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_DELETEFILE.name(),
			Boolean.toString(true));
		getSession().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_CONTENTTYPE.name(),
			WebConstants.MIME_TYPE_GENERIC);
	    }
	} catch (Exception ex) {
	    logger.error("Errore in download " + ExceptionUtils.getRootCauseMessage(ex), ex);
	    getMessageBox().addError("Errore inatteso nella preparazione del download<br/>");
	} finally {
	    IOUtils.closeQuietly(out);
	}

	if (getMessageBox().hasError()) {
	    forwardToPublisher(getLastPublisher());
	} else {
	    forwardToPublisher(Application.Publisher.DOWNLOAD_PAGE);
	}

    }

    private void downloadFileCommonReferente(BigDecimal idEnteUserRif) throws EMFError {
	File tmpFile = null;
	FileOutputStream out = null;

	try {
	    OrgEnteUserRif rec = entiConvenzionatiHelper.findById(OrgEnteUserRif.class,
		    idEnteUserRif);
	    // Controllo per scrupolo
	    if (rec.getBlEnteUserRif() == null) {
		getMessageBox().addError("Non c'\u00E8 alcun file da scaricare<br/>");
	    } else {
		tmpFile = new File(System.getProperty("java.io.tmpdir"),
			rec.getNmFileEnteUserRif());
		out = new FileOutputStream(tmpFile);
		IOUtils.write(rec.getBlEnteUserRif(), out);

		getRequest().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_ACTION.name(),
			getControllerName());
		getSession().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_FILENAME.name(),
			tmpFile.getName());
		getSession().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_FILEPATH.name(),
			tmpFile.getPath());
		getSession().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_DELETEFILE.name(),
			Boolean.toString(true));
		getSession().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_CONTENTTYPE.name(),
			WebConstants.MIME_TYPE_GENERIC);
	    }
	} catch (Exception ex) {
	    logger.error("Errore in download " + ExceptionUtils.getRootCauseMessage(ex), ex);
	    getMessageBox().addError("Errore inatteso nella preparazione del download<br/>");
	} finally {
	    IOUtils.closeQuietly(out);
	}

	if (getMessageBox().hasError()) {
	    forwardToPublisher(getLastPublisher());
	} else {
	    forwardToPublisher(Application.Publisher.DOWNLOAD_PAGE);
	}

    }

    public void download() throws EMFError {
	logger.debug(">>>DOWNLOAD");
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
		    logger.error("Eccezione nel recupero del documento ", e);
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

    public void downloadFileModuloDaLista() throws EMFError {
	logger.debug(">>>DOWNLOAD da Lista");
	setTableName(getForm().getModuliInformazioniList().getName());
	setRiga(getRequest().getParameter("riga"));
	getForm().getModuliInformazioniList().getTable()
		.setCurrentRowIndex(Integer.parseInt(getRiga()));
	OrgModuloInfoAccordoRowBean row = (OrgModuloInfoAccordoRowBean) getForm()
		.getModuliInformazioniList().getTable().getCurrentRow();
	BigDecimal idModello = row.getIdModuloInfoAccordo();
	downloadFileCommon(idModello);
    }

    public void downloadFileReferenteDaLista() throws EMFError, Throwable {
	logger.debug(">>>DOWNLOAD da Lista");
	setTableName(getForm().getReferentiEnteList().getName());
	setRiga(getRequest().getParameter("riga"));
	getForm().getReferentiEnteList().getTable().setCurrentRowIndex(Integer.parseInt(getRiga()));
	OrgEnteUserRifRowBean row = (OrgEnteUserRifRowBean) getForm().getReferentiEnteList()
		.getTable().getCurrentRow();
	BigDecimal idReferente = row.getIdEnteUserRif();
	OrgEnteUserRif rec = entiConvenzionatiHelper.findById(OrgEnteUserRif.class, idReferente);

	if (rec.getBlEnteUserRif() != null) {
	    downloadFileCommonReferente(BigDecimal.valueOf(rec.getIdEnteUserRif()));
	} else if (rec.getCdRegistroEnteUserRif() != null && rec.getAaEnteUserRif() != null
		&& rec.getCdKeyEnteUserRif() != null) {
	    scaricaCompFileUdEnteUserRif();
	}
    }

    public void downloadFileGestAccDaLista() throws EMFError {
	logger.debug(">>>DOWNLOAD da Lista");
	setTableName(getForm().getGestioniAccordoList().getName());
	setRiga(getRequest().getParameter("riga"));
	getForm().getGestioniAccordoList().getTable()
		.setCurrentRowIndex(Integer.parseInt(getRiga()));
	OrgGestioneAccordoRowBean row = (OrgGestioneAccordoRowBean) getForm()
		.getGestioniAccordoList().getTable().getCurrentRow();
	BigDecimal idModello = row.getIdGestAccordo();
	downloadFileCommonGestAccordo(idModello);
    }

    @Secure(action = "Menu.AmministrazioneSistema.GestioneAmbientiEntiConvenzionati")
    public void ricercaAmbientiEntiConvenzionatiPage() {
	getUser().getMenu().reset();
	getUser().getMenu().select("Menu.AmministrazioneSistema.GestioneAmbientiEntiConvenzionati");
	getForm().getFiltriAmbientiEntiConvenzionati().setEditMode();
	getForm().getListaAmbientiEntiConvenzionati().setTable(null);
	forwardToPublisher(Application.Publisher.RICERCA_AMBIENTI);
    }

    @Override
    public void ricercaAmbienti() throws EMFError {
	if (getForm().getFiltriAmbientiEntiConvenzionati().postAndValidate(getRequest(),
		getMessageBox())) {
	    BigDecimal idUserIamCor = new BigDecimal(getUser().getIdUtente());
	    String nmAmbienteEnteConvenz = getForm().getFiltriAmbientiEntiConvenzionati()
		    .getNm_ambiente_ente_convenz().parse();
	    String dsAmbienteEnteConvenz = getForm().getFiltriAmbientiEntiConvenzionati()
		    .getDs_ambiente_ente_convenz().parse();
	    UsrVAbilAmbConvenzXenteTableBean table = entiConvenzionatiEjb
		    .getUsrVAbilAmbConvenzXenteTableBean(idUserIamCor, nmAmbienteEnteConvenz,
			    dsAmbienteEnteConvenz);
	    getForm().getListaAmbientiEntiConvenzionati().setTable(table);
	    getForm().getListaAmbientiEntiConvenzionati().getTable().setPageSize(10);
	    getForm().getListaAmbientiEntiConvenzionati().getTable().first();
	}
	forwardToPublisher(getLastPublisher());
    }

    private void initAmbienteEnteConvenzionatoDetail(BigDecimal idEnteGestore)
	    throws ParerUserError, EMFError {
	// Inizializzo le combo del dettaglio
	// MEV#19407
	getForm().getAmbienteEnteConvenzionatoDetail().getId_ente_gestore()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getEntiGestoreAbilitatiTableBean(
				new BigDecimal(getUser().getIdUtente())),
			"id_ente_gestore", "nm_ente_gestore"));
	getForm().getAmbienteEnteConvenzionatoDetail().getId_ente_conserv()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getEntiConservatori(
				new BigDecimal(getUser().getIdUtente()), idEnteGestore),
			"id_ente_siam", "nm_ente_siam"));
	// end MEV#19407
    }

    private void loadDettaglioAmbienteEnteConvenzionato(BigDecimal idAmbienteEnteConvenz)
	    throws ParerUserError, EMFError {
	OrgAmbienteEnteConvenzRowBean detailRow = entiConvenzionatiEjb
		.getOrgAmbienteEnteConvenzRowBean(idAmbienteEnteConvenz);
	getForm().getAmbienteEnteConvenzionatoDetail().copyFromBean(detailRow);

	getForm().getAmbienteEnteConvenzionatoDetail().setViewMode();
	getForm().getAmbienteEnteConvenzionatoDetail().setStatus(Status.view);
	getForm().getListaAmbientiEntiConvenzionati().setStatus(Status.view);

	// Inizializzo i bottoni in edit mode
	getForm().getAmbienteEnteConvenzionatoDetail().getLogEventiAmbiente().setEditMode();
	getForm().getAmbienteEnteConvenzionatoDetail().getLogEventiAmbiente().setHidden(false);

	// Popolamento lista enti convenzionati
	getForm().getListaEntiConvenzionati().setTable(
		entiConvenzionatiEjb.getOrgVRicEnteConvenzTableBean(idAmbienteEnteConvenz));
	getForm().getListaEntiConvenzionati().getTable().setPageSize(10);
	getForm().getListaEntiConvenzionati().getTable().first();

	loadListaParametriAmministrazioneAmbiente(idAmbienteEnteConvenz, false, false,
		getForm().getParametriAmministrazioneAmbienteList().isFilterValidRecords());
	loadListaParametriGestioneAmbiente(idAmbienteEnteConvenz, false, false,
		getForm().getParametriGestioneAmbienteList().isFilterValidRecords());
	loadListaParametriConservazioneAmbiente(idAmbienteEnteConvenz, false, false,
		getForm().getParametriConservazioneAmbienteList().isFilterValidRecords());
    }

    private void saveAmbienteEnteConvenzionato() throws EMFError {
	getForm().getParametriAmministrazioneAmbienteList().post(getRequest());
	getForm().getParametriConservazioneAmbienteList().post(getRequest());
	getForm().getParametriGestioneAmbienteList().post(getRequest());

	getMessageBox().clear();
	AmministrazioneEntiConvenzionatiForm.AmbienteEnteConvenzionatoDetail ambienteEnteConvenz = getForm()
		.getAmbienteEnteConvenzionatoDetail();
	getForm().getAmbienteEnteConvenzionatoDetail().post(getRequest());
	OrgAmbienteEnteConvenzRowBean ambienteEnteConvenzRowBean = new OrgAmbienteEnteConvenzRowBean();
	ambienteEnteConvenz.copyToBean(ambienteEnteConvenzRowBean);

	if (getForm().getAmbienteEnteConvenzionatoDetail().validate(getMessageBox())) {
	    String nmAmbienteEnteconvenz = getForm().getAmbienteEnteConvenzionatoDetail()
		    .getNm_ambiente_ente_convenz().parse();
	    String dsAmbienteEnteconvenz = getForm().getAmbienteEnteConvenzionatoDetail()
		    .getDs_ambiente_ente_convenz().parse();
	    BigDecimal idEnteConserv = getForm().getAmbienteEnteConvenzionatoDetail()
		    .getId_ente_conserv().parse();
	    BigDecimal idEnteGestore = getForm().getAmbienteEnteConvenzionatoDetail()
		    .getId_ente_gestore().parse();
	    String dsNote = getForm().getAmbienteEnteConvenzionatoDetail().getDs_note().parse();
	    Date dtIniVal = getForm().getAmbienteEnteConvenzionatoDetail().getDt_ini_val().parse();
	    Date dtFineVal = getForm().getAmbienteEnteConvenzionatoDetail().getDt_fine_val()
		    .parse();
	    if (dtFineVal.before(dtIniVal)) {
		getMessageBox().addError(
			"Attenzione: data fine validità inferiore a data inizio validità");
	    }

	    IamParamApplicTableBean parametriAmministrazione = (IamParamApplicTableBean) getForm()
		    .getParametriAmministrazioneAmbienteList().getTable();
	    IamParamApplicTableBean parametriConservazione = (IamParamApplicTableBean) getForm()
		    .getParametriConservazioneAmbienteList().getTable();
	    IamParamApplicTableBean parametriGestione = (IamParamApplicTableBean) getForm()
		    .getParametriGestioneAmbienteList().getTable();

	    // Controllo valori possibili su ente
	    for (IamParamApplicRowBean paramApplicRowBean : parametriAmministrazione) {
		if (paramApplicRowBean.getString("ds_lista_valori_ammessi") != null
			&& !paramApplicRowBean.getString("ds_lista_valori_ammessi").equals("")) {
		    if (paramApplicRowBean.getString("ds_valore_param_applic_ambiente_amm") != null
			    && !paramApplicRowBean.getString("ds_valore_param_applic_ambiente_amm")
				    .equals("")) {
			if (!inValoriPossibili(
				paramApplicRowBean.getString("ds_valore_param_applic_ambiente_amm"),
				paramApplicRowBean.getString("ds_lista_valori_ammessi"))) {
			    getMessageBox().addError(
				    "Il valore del parametro non è compreso tra i valori ammessi sul parametro");
			}
		    }
		}
	    }

	    for (IamParamApplicRowBean paramApplicRowBean : parametriConservazione) {
		if (paramApplicRowBean.getString("ds_lista_valori_ammessi") != null
			&& !paramApplicRowBean.getString("ds_lista_valori_ammessi").equals("")) {
		    if (paramApplicRowBean.getString("ds_valore_param_applic_ambiente_cons") != null
			    && !paramApplicRowBean.getString("ds_valore_param_applic_ambiente_cons")
				    .equals("")) {
			if (!inValoriPossibili(
				paramApplicRowBean
					.getString("ds_valore_param_applic_ambiente_cons"),
				paramApplicRowBean.getString("ds_lista_valori_ammessi"))) {
			    getMessageBox().addError(
				    "Il valore del parametro non è compreso tra i valori ammessi sul parametro");
			}
		    }
		}
	    }

	    for (IamParamApplicRowBean paramApplicRowBean : parametriGestione) {
		if (paramApplicRowBean.getString("ds_lista_valori_ammessi") != null
			&& !paramApplicRowBean.getString("ds_lista_valori_ammessi").equals("")) {
		    if (paramApplicRowBean.getString("ds_valore_param_applic_ambiente_gest") != null
			    && !paramApplicRowBean.getString("ds_valore_param_applic_ambiente_gest")
				    .equals("")) {
			if (!inValoriPossibili(
				paramApplicRowBean
					.getString("ds_valore_param_applic_ambiente_gest"),
				paramApplicRowBean.getString("ds_lista_valori_ammessi"))) {
			    getMessageBox().addError(
				    "Il valore del parametro non è compreso tra i valori ammessi sul parametro");
			}
		    }
		}
	    }

	    try {
		if (!getMessageBox().hasError()) {
		    // Salva l'ambiente
		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    if (getForm().getListaAmbientiEntiConvenzionati().getStatus()
			    .equals(Status.insert)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
			Long idAmbienteEnteConvenzionato = entiConvenzionatiEjb
				.saveAmbienteEnteConvenzionato(param, nmAmbienteEnteconvenz,
					dsAmbienteEnteconvenz, dsNote, dtIniVal, dtFineVal,
					idEnteConserv, idEnteGestore, parametriAmministrazione,
					parametriConservazione, parametriGestione);
			if (idAmbienteEnteConvenzionato != null) {
			    getForm().getAmbienteEnteConvenzionatoDetail()
				    .getId_ambiente_ente_convenz()
				    .setValue(idAmbienteEnteConvenzionato.toString());
			}
			UsrVAbilAmbEnteConvenzRowBean row = new UsrVAbilAmbEnteConvenzRowBean();
			getForm().getAmbienteEnteConvenzionatoDetail().copyToBean(row);
			getForm().getListaAmbientiEntiConvenzionati().getTable().last();
			getForm().getListaAmbientiEntiConvenzionati().getTable().add(row);

			loadDettaglioAmbienteEnteConvenzionato(
				BigDecimal.valueOf(idAmbienteEnteConvenzionato));

			getMessageBox().addInfo("Ambiente ente convenzionato salvato con successo");
			getMessageBox().setViewMode(ViewMode.plain);
		    } else if (getForm().getListaAmbientiEntiConvenzionati().getStatus()
			    .equals(Status.update)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
			// MEV#19407
			checkAndSaveModificaAmbiente(param, ambienteEnteConvenzRowBean,
				parametriAmministrazione, parametriConservazione,
				parametriGestione);
			// end MEV#19407
		    }
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
	    }
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_AMBIENTE);
    }

    private void checkAndSaveModificaAmbiente(LogParam param,
	    OrgAmbienteEnteConvenzRowBean ambienteEnteConvenzRowBean,
	    IamParamApplicTableBean parametriAmministrazione,
	    IamParamApplicTableBean parametriConservazione,
	    IamParamApplicTableBean parametriGestione) throws ParerUserError, EMFError {
	Date dataCorrente = new Date();
	// Se l'ambiente non ÃƒÆ’Ã‚Â¨ piÃƒÆ’Ã‚Â¹ valido
	if (!(ambienteEnteConvenzRowBean.getDtIniVal().compareTo(dataCorrente) < 0
		&& ambienteEnteConvenzRowBean.getDtFineVal().compareTo(dataCorrente) > 0)) {
	    boolean esistenzaEntiConvenzionati = entiConvenzionatiEjb
		    .existsEntiValidiAmbienteTableBean(
			    ambienteEnteConvenzRowBean.getIdAmbienteEnteConvenz());
	    boolean esistenzaUtentiUnAmbiente = entiConvenzionatiEjb
		    .existsUtentiAttiviAbilitatiAdAmbienteTableBean(
			    ambienteEnteConvenzRowBean.getIdAmbienteEnteConvenz());
	    boolean esistenzaUtentiUnAmbiente2 = entiConvenzionatiEjb
		    .existsUtentiAttiviAbilitatiAdAmbienteTableBean2(
			    ambienteEnteConvenzRowBean.getIdAmbienteEnteConvenz());
	    if (esistenzaEntiConvenzionati || esistenzaUtentiUnAmbiente) {
		Object[] attributiSalvataggioModificaAmbiente = new Object[5];
		attributiSalvataggioModificaAmbiente[0] = param;
		attributiSalvataggioModificaAmbiente[1] = ambienteEnteConvenzRowBean;
		attributiSalvataggioModificaAmbiente[2] = parametriAmministrazione;
		attributiSalvataggioModificaAmbiente[3] = parametriConservazione;
		attributiSalvataggioModificaAmbiente[4] = parametriGestione;
		getSession().setAttribute("attributiSalvataggioModificaAmbiente",
			attributiSalvataggioModificaAmbiente);
		getRequest().setAttribute("customModificaAmbiente", true);
		String messaggio = "Attenzione: esiste almeno un ente convenzionato valido appartenente all\u0027ambiente "
			+ "oppure esiste almeno un utente attivo con abilitazione all\u0027ambiente. "
			+ "Confermare la modifica dell\u0027ambiente?";
		getRequest().setAttribute("messaggioModificaAmbiente", messaggio);
		getMessageBox().setViewMode(ViewMode.alert);
	    } else {
		eseguiSalvataggioModificaAmbiente(param, ambienteEnteConvenzRowBean,
			parametriAmministrazione, parametriConservazione, parametriGestione);
	    }
	} else {
	    eseguiSalvataggioModificaAmbiente(param, ambienteEnteConvenzRowBean,
		    parametriAmministrazione, parametriConservazione, parametriGestione);
	}
    }

    public void eseguiSalvataggioModificaAmbiente(LogParam param,
	    OrgAmbienteEnteConvenzRowBean ambienteEnteConvenzRowBean,
	    IamParamApplicTableBean parametriAmministrazione,
	    IamParamApplicTableBean parametriConservazione,
	    IamParamApplicTableBean parametriGestione) throws ParerUserError, EMFError {

	entiConvenzionatiEjb.saveAmbienteEnteConvenzionato(param,
		ambienteEnteConvenzRowBean.getIdAmbienteEnteConvenz(),
		ambienteEnteConvenzRowBean.getNmAmbienteEnteConvenz(),
		ambienteEnteConvenzRowBean.getDsAmbienteEnteConvenz(),
		ambienteEnteConvenzRowBean.getDsNote(), ambienteEnteConvenzRowBean.getDtIniVal(),
		ambienteEnteConvenzRowBean.getDtFineVal(),
		ambienteEnteConvenzRowBean.getIdEnteConserv(),
		ambienteEnteConvenzRowBean.getIdEnteGestore(), parametriAmministrazione,
		parametriConservazione, parametriGestione);

	getMessageBox().addInfo("Ambiente ente convenzionato salvato con successo");
	getMessageBox().setViewMode(ViewMode.plain);

	getForm().getListaAmbientiEntiConvenzionati().getTable().getCurrentRow()
		.copyFromBaseRow(ambienteEnteConvenzRowBean);
	loadDettaglioAmbienteEnteConvenzionato(
		ambienteEnteConvenzRowBean.getIdAmbienteEnteConvenz());

	// Rimuovo dalla sessione eventuali attributi
	getSession().removeAttribute("attributiSalvataggioModificaAmbiente");
	forwardToPublisher(Application.Publisher.DETTAGLIO_AMBIENTE);
    }

    public void confermaSalvataggioModificaAmbiente() {
	if (getSession().getAttribute("attributiSalvataggioModificaAmbiente") != null) {
	    Object[] attributiSalvataggioModificaAmbiente = (Object[]) getSession()
		    .getAttribute("attributiSalvataggioModificaAmbiente");
	    LogParam param = (LogParam) attributiSalvataggioModificaAmbiente[0];
	    OrgAmbienteEnteConvenzRowBean ambienteEnteConvenzRowBean = (OrgAmbienteEnteConvenzRowBean) attributiSalvataggioModificaAmbiente[1];
	    IamParamApplicTableBean parametriAmministrazione = (IamParamApplicTableBean) attributiSalvataggioModificaAmbiente[2];
	    IamParamApplicTableBean parametriConservazione = (IamParamApplicTableBean) attributiSalvataggioModificaAmbiente[3];
	    IamParamApplicTableBean parametriGestione = (IamParamApplicTableBean) attributiSalvataggioModificaAmbiente[4];
	    try {
		eseguiSalvataggioModificaAmbiente(param, ambienteEnteConvenzRowBean,
			parametriAmministrazione, parametriConservazione, parametriGestione);
	    } catch (ParerUserError ex) {
		logger.error("Errore nel salvataggio della modifica ambiente ente convenzionato",
			ex);
		getMessageBox().addError(
			"Errore nel salvataggio della modifica ambiente ente convenzionato");
	    } catch (EMFError ex) {
		logger.error(ex.getMessage());
		getMessageBox().addError(ex.getDescription());
	    }
	}
    }

    public void annullaSalvataggioModificaAmbiente() {
	getSession().removeAttribute("attributiSalvataggioModificaAmbiente");
	forwardToPublisher(Application.Publisher.DETTAGLIO_AMBIENTE);
    }

    private void saveModificaAnagrafica() throws EMFError {
	if (getForm().getAnagraficaDetail().postAndValidate(getRequest(), getMessageBox())) {
	    BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam()
		    .parse();
	    Date dtIniVal = getForm().getAnagraficaDetail().getDt_ini_val().parse();
	    Date dtFineVal = getForm().getAnagraficaDetail().getDt_fine_val().parse();
	    Date dtIniValEnte = getForm().getEnteConvenzionatoDetail().getDt_ini_val().parse();
	    Date dtFineValEnte = getForm().getEnteConvenzionatoDetail().getDt_cessazione().parse();
	    BigDecimal idStoEnteConvenz = ((OrgStoEnteConvenzRowBean) getForm().getAnagraficheList()
		    .getTable().getCurrentRow()).getIdStoEnteConvenz();
	    if (dtFineVal.before(dtIniVal)) {
		getMessageBox().addError(
			"Attenzione: data fine validit\u00E0Â  inferiore a data inizio validit\u00E0Â ");
	    } else if (entiConvenzionatiEjb.existOtherAnagrafichePerIntervallo(idEnteConvenz,
		    dtIniVal, dtFineVal, idStoEnteConvenz)) {
		getMessageBox().addError(
			"Attenzione: l'intervallo temporale si sovrappone a quello di altre anagrafiche gi\u00E0Â  presenti");
	    } else if (entiConvenzionatiEjb.isDateAnagraficaOutOfRange(dtIniValEnte, dtFineValEnte,
		    dtIniVal, dtFineVal)) {
		getMessageBox()
			.addError("Attenzione: date anagrafica esterne alla validitÃ  dell'enteÂ ");
	    } else if (!entiConvenzionatiEjb.isDateAnagraficaContinue(dtIniVal, idEnteConvenz,
		    idStoEnteConvenz)) {
		getMessageBox().addError(
			"Attenzione: si sta tentando di modificare una anagrafica le cui date di validit\u00E0 non sono continue ad anagrafiche precedentiÂ ");
	    }

	    if (!getMessageBox().hasError()) {
		try {
		    StoEnteConvenzionatoBean stoCaBean = new StoEnteConvenzionatoBean(
			    getForm().getAnagraficaDetail());
		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
		    // Storicizzo
		    entiConvenzionatiEjb.saveModificaStoEnteConvenzionato(param, idStoEnteConvenz,
			    stoCaBean, idEnteConvenz);
		    getMessageBox().addInfo("Ente storicizzato modificato con successo!");
		    getMessageBox().setViewMode(ViewMode.plain);
		    loadDettaglioAnagrafica(idStoEnteConvenz);
		    logger.info(getClass().getName() + " Fine storicizzazione");
		} catch (ParerUserError ex) {
		    getMessageBox().addError(ex.getDescription());
		}
	    }
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_ANAGRAFICA);
    }

    @Override
    public void updateListaAmbientiEntiConvenzionati() throws EMFError {
	BigDecimal idAmbienteEnteConvenz = getForm().getListaAmbientiEntiConvenzionati().getTable()
		.getCurrentRow().getBigDecimal("id_ambiente_ente_convenz");
	getForm().getAmbienteEnteConvenzionatoDetail().getDs_ambiente_ente_convenz().setEditMode();
	getForm().getAmbienteEnteConvenzionatoDetail().getDs_note().setEditMode();
	getForm().getAmbienteEnteConvenzionatoDetail().getDt_fine_val().setEditMode();
	// Se non sono presenti enti correlati, posso modificare anche il nome
	if (getForm().getListaEntiConvenzionati().getTable().isEmpty()) {
	    getForm().getAmbienteEnteConvenzionatoDetail().getNm_ambiente_ente_convenz()
		    .setEditMode();
	    getForm().getAmbienteEnteConvenzionatoDetail().getDt_ini_val().setEditMode();
	    getForm().getAmbienteEnteConvenzionatoDetail().getId_ente_conserv().setEditMode();
	    getForm().getAmbienteEnteConvenzionatoDetail().getId_ente_gestore().setEditMode();
	} else {
	    // Se sono presenti enti correlati ma l'ente conservatore non ÃƒÆ’Ã‚Â¨ valorizzato,
	    // posso modificarlo
	    if (StringUtils.isBlank(getForm().getAmbienteEnteConvenzionatoDetail()
		    .getId_ente_conserv().getValue())) {
		getForm().getAmbienteEnteConvenzionatoDetail().getId_ente_conserv().setEditMode();
	    }
	    // Se sono presenti enti correlati ma l'ente gestore non ÃƒÆ’Ã‚Â¨ valorizzato, posso
	    // modificarlo
	    if (StringUtils.isBlank(getForm().getAmbienteEnteConvenzionatoDetail()
		    .getId_ente_gestore().getValue())) {
		getForm().getAmbienteEnteConvenzionatoDetail().getId_ente_gestore().setEditMode();
	    }
	}

	// Parametri da gestire senza offuscamento
	try {
	    // loadListeParametriAmbiente(idAmbienteEnteConvenz, null, true, true, true, true);
	    loadListaParametriAmministrazioneAmbiente(idAmbienteEnteConvenz, true, true,
		    getForm().getParametriAmministrazioneAmbienteList().isFilterValidRecords());
	    loadListaParametriGestioneAmbiente(idAmbienteEnteConvenz, false, false,
		    getForm().getParametriGestioneAmbienteList().isFilterValidRecords());
	    loadListaParametriConservazioneAmbiente(idAmbienteEnteConvenz, false, false,
		    getForm().getParametriConservazioneAmbienteList().isFilterValidRecords());
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}

	getForm().getAmbienteEnteConvenzionatoDetail().setStatus(Status.update);
	getForm().getListaAmbientiEntiConvenzionati().setStatus(Status.update);
	getForm().getAmbienteEnteConvenzionatoDetail().getLogEventiAmbiente().setHidden(true);
    }

    @Override
    public void deleteListaAmbientiEntiConvenzionati() throws EMFError {
	BaseRowInterface currentRow = getForm().getListaAmbientiEntiConvenzionati().getTable()
		.getCurrentRow();
	BigDecimal idAmbienteEnteConvenz = currentRow.getBigDecimal("id_ambiente_ente_convenz");
	int riga = getForm().getListaAmbientiEntiConvenzionati().getTable().getCurrentRowIndex();
	// Eseguo giusto un controllo per verificare che io stia prendendo la riga giusta se sono
	// nel dettaglio
	if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_AMBIENTE)) {
	    if (!idAmbienteEnteConvenz.equals(getForm().getAmbienteEnteConvenzionatoDetail()
		    .getId_ambiente_ente_convenz().parse())) {
		getMessageBox().addError(
			"Eccezione imprevista nell'eliminazione dell'ambiente ente convenzionato");
	    }
	}

	if (!getMessageBox().hasError() && idAmbienteEnteConvenz != null) {
	    try {
		if (!getMessageBox().hasError()) {
		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    if (param.getNomePagina()
			    .equalsIgnoreCase(Application.Publisher.DETTAGLIO_AMBIENTE)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
		    } else {
			param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
				getForm().getListaAmbientiEntiConvenzionati()));
		    }
		    entiConvenzionatiEjb.deleteOrgAmbienteEnteConvenz(param, idAmbienteEnteConvenz);
		    getForm().getListaAmbientiEntiConvenzionati().getTable().remove(riga);
		    getMessageBox().addInfo("Ambiente ente convenzionato eliminato con successo");
		    getMessageBox().setViewMode(ViewMode.plain);
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
	    }
	}
	if (!getMessageBox().hasError()
		&& getLastPublisher().equals(Application.Publisher.DETTAGLIO_AMBIENTE)) {
	    goBack();
	} else {
	    forwardToPublisher(getLastPublisher());
	}
    }

    @Override
    public void logEventiAmbiente() throws EMFError {
	GestioneLogEventiForm form = new GestioneLogEventiForm();
	form.getOggettoDetail().getNmApp().setValue(paramHelper.getParamApplicApplicationName());
	form.getOggettoDetail().getNm_tipo_oggetto()
		.setValue(SacerLogConstants.TIPO_OGGETTO_AMB_ENTE_CONVENZ);
	form.getOggettoDetail().getIdOggetto().setValue(getForm()
		.getAmbienteEnteConvenzionatoDetail().getId_ambiente_ente_convenz().getValue());
	redirectToAction(it.eng.parer.sacerlog.slite.gen.Application.Actions.GESTIONE_LOG_EVENTI,
		"?operation=inizializzaLogEventi", form);
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
			    Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO);
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
			    getForm().getReferentiEnteList()));
		    entiConvenzionatiEjb.deleteOrgEnteUserRif(param, idEnteUserRif);
		    getForm().getReferentiEnteList().getTable().remove(riga);
		    getMessageBox().addInfo(
			    "Utente referente dell'ente eliminato con successo dall'ente convenzionato");
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

    ///////////////////
    // NUOVO ACCORDO //
    ///////////////////
    @Override
    public void nuovoAccordo() throws EMFError {
	// Pulizia campi
	getForm().getAccordoDetail().clear();
	try {
	    Date dtScadAccordo;

	    DateFormat formato = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH);
	    Calendar cal = Calendar.getInstance();
	    cal.set(2444, Calendar.DECEMBER, 31);

	    BigDecimal idAmbienteEnteConvenz = getForm().getEnteConvenzionatoDetail()
		    .getId_ambiente_ente_convenz().parse();
	    BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam()
		    .parse();

	    OrgAccordoEnteTableBean accordoEnte = entiConvenzionatiEjb
		    .getOrgAccordoEntePiuRecenteTableBean(idEnteConvenz);
	    if (!accordoEnte.isEmpty()) {
		Timestamp tsScadAccordo = accordoEnte.getRow(0).getDtScadAccordo();

		if (tsScadAccordo != null) {
		    dtScadAccordo = new Date(tsScadAccordo.getTime());
		} else {
		    // Se la data scadenza è nulla si assume valore 31/12/2444
		    dtScadAccordo = cal.getTime();
		}
		dtScadAccordo = DateUtils.truncate(dtScadAccordo, Calendar.DATE);

		// Se c'ÃƒÆ’Ã‚Â¨ una data infinita fornisce errore
		if (dtScadAccordo.equals(DateUtil.MAX_DATE)) {
		    getMessageBox()
			    .addError("L'accordo in vigore non ha la data di scadenza impostata");
		}
	    } else {
		// Non dovrebbe mai verificarsi
		getMessageBox().addError("Nessun accordo definito per l'ente convenzionato");
	    }

	    if (!getMessageBox().hasError()) {

		getSession().setAttribute("nuovoAccordo", true);

		initAccordoDetail();

		// Campo id accordo nascosto in inserimento
		getForm().getAccordoDetail().getId_accordo_ente().setHidden(true);

		OrgEnteSiamRowBean enteConvenzGestore = entiConvenzionatiEjb
			.getOrgEnteConvenzGestore(idAmbienteEnteConvenz);
		if (enteConvenzGestore != null) {
		    getForm().getAccordoDetail().getId_ente_convenz_gestore()
			    .setValue(enteConvenzGestore.getIdEnteSiam().toPlainString());
		    getForm().getAccordoDetail().getNm_ente_convenz_gestore()
			    .setValue(enteConvenzGestore.getNmEnteSiam());
		}
		//
		OrgEnteSiamRowBean enteConvenzConserv = entiConvenzionatiEjb
			.getOrgEnteConvenzConserv(idAmbienteEnteConvenz);
		if (enteConvenzConserv != null) {
		    getForm().getAccordoDetail().getId_ente_convenz_conserv()
			    .setValue(enteConvenzConserv.getIdEnteSiam().toPlainString());
		    getForm().getAccordoDetail().getNm_ente_convenz_conserv()
			    .setValue(enteConvenzConserv.getNmEnteSiam());

		    OrgAccordoEnteTableBean accordoEnteTableBean = entiConvenzionatiEjb
			    .getOrgAccordoEntePiuRecenteTableBean(
				    enteConvenzConserv.getIdEnteSiam());
		    if (!accordoEnteTableBean.isEmpty()) {
			getForm().getAccordoDetail().getId_ente_convenz_amministratore()
				.setValue(accordoEnteTableBean.getRow(0)
					.getIdEnteConvenzAmministratore().toPlainString());
			getForm().getAccordoDetail().getNm_ente_convenz_amministratore()
				.setValue(accordoEnteTableBean.getRow(0)
					.getString("nm_ente_convenz_amministratore"));
		    }
		}

		// Riporto i dati presi dall'ultimo accordo
		getForm().getAccordoDetail().getId_tipo_accordo()
			.setValue(accordoEnte.getRow(0).getIdTipoAccordo() != null
				? accordoEnte.getRow(0).getIdTipoAccordo().toPlainString()
				: null);
		getForm().getAccordoDetail().getFl_pagamento()
			.setValue(accordoEnte.getRow(0).getFlPagamento() != null
				? accordoEnte.getRow(0).getFlPagamento()
				: null);
		getForm().getAccordoDetail().getNi_abitanti()
			.setValue(accordoEnte.getRow(0).getNiAbitanti() != null
				? accordoEnte.getRow(0).getNiAbitanti().toPlainString()
				: null);
		getForm().getAccordoDetail().getCd_cliente_fatturazione()
			.setValue(accordoEnte.getRow(0).getCdClienteFatturazione() != null
				? accordoEnte.getRow(0).getCdClienteFatturazione()
				: null);
		// Riporto i dati presi da ente convenzionato
		getForm().getAccordoDetail().getNm_ente_siam()
			.setValue(getForm().getEnteConvenzionatoDetail().getNm_ente_siam().parse());

		getForm().getAccordoDetail().getTi_scopo_accordo().setValue("CONSERVAZIONE");

		getForm().getAccordoDetail().setEditMode();

		// MEV#22367
		/* (cond 2) */
		if (accordoEnte.getRow(0).getDtScadAccordo()
			.after(accordoEnte.getRow(0).getDtFineValidAccordo())) {
		    /* (fine cond 2) */
		    Calendar dataDec = Calendar.getInstance();
		    Date dataFineValid = accordoEnte.getRow(0).getDtFineValidAccordo();
		    dataDec.setTime(dataFineValid);
		    dataDec.add(Calendar.DATE, 1);
		    getForm().getAccordoDetail().getDt_dec_accordo()
			    .setValue(formato.format(dataDec.getTime()));
		    /* (cond 1 OR cond 3) */
		} else {
		    /* (fine cond. 1 OR cond. 3) */
		    Calendar dataDec = Calendar.getInstance();
		    Date dataScadenza = accordoEnte.getRow(0).getDtScadAccordo();
		    dataDec.setTime(dataScadenza);
		    dataDec.add(Calendar.DATE, 1);
		    getForm().getAccordoDetail().getDt_dec_accordo()
			    .setValue(formato.format(dataDec.getTime()));
		}
		// end MEV#22367

		// Bottone di ricalcolo
		getForm().getAccordoDetail().getRicalcoloServiziErogati().setHidden(true);
		// Bottone di chiusura accordo
		getForm().getAccordoDetail().getChiusuraAccordo().setHidden(true);
		// Bottone inserimento annualità
		getForm().getAnnualitaAccordoList().setHideInsertButton(true);

		// Ambiente/Ente/Struttura in sola visualizzazione
		getForm().getAccordoDetail().getNm_ente_siam().setViewMode();
		getForm().getAccordoDetail().getNm_ente_convenz_gestore().setViewMode();
		getForm().getAccordoDetail().getNm_ente_convenz_conserv().setViewMode();
		getForm().getAccordoDetail().getNm_ente_convenz_amministratore().setViewMode();

		getForm().getAccordoDetail().getNm_ente().setViewMode();
		getForm().getAccordoDetail().getNm_strut().setViewMode();

		getForm().getAccordoDetail().getId_ente_convenz_gestore().setHidden(true);
		getForm().getAccordoDetail().getId_ente_convenz_conserv().setHidden(true);
		getForm().getAccordoDetail().getId_ente_convenz_amministratore().setHidden(true);

		getForm().getAccordoDetail().setStatus(Status.insert);
		getForm().getAccordiList().setStatus(Status.insert);
		postLoad();
		forwardToPublisher(Application.Publisher.DETTAGLIO_ACCORDO);
	    } else {
		forwardToPublisher(getLastPublisher());
	    }
	} catch (ParerUserError e) {
	    getMessageBox().addError(e.getMessage());
	    forwardToPublisher(getLastPublisher());
	}
    }

    //////////////////////
    // CHIUSURA ACCORDO //
    //////////////////////
    @Override
    public void chiusuraAccordo() throws EMFError {
	BigDecimal idAccordoEnte = getForm().getAccordoDetail().getId_accordo_ente().parse();
	Date dtDecAccordo = getForm().getAccordoDetail().getDt_dec_accordo().parse();
	BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam().parse();
	String tiEnteConvenz = getForm().getEnteConvenzionatoDetail().getTi_ente_convenz().parse();
	String popup = getRequest().getParameter("popup");

	getForm().getAccordoDetail().getDt_chiusura().setEditMode();

	try {
	    // Se sull\u0027ente esiste almeno un accordo con intervallo di validità (data inizio /
	    // fine
	    // validità) più recente rispetto a quello da chiudere il sistema fornisce errore
	    OrgAccordoEnteTableBean accordoEnte = entiConvenzionatiEjb
		    .getOrgAccordoEntePiuRecenteTableBean(idEnteConvenz);
	    if (!accordoEnte.isEmpty()
		    && !accordoEnte.getCurrentRow().getIdAccordoEnte().equals(idAccordoEnte)) {
		getMessageBox().addError(
			"Chiusura non consentita: esiste almeno un accordo con decorrenza successiva a quello per cui si richiede la chiusura");
	    }
	    if (!getMessageBox().hasError()) {
		if (dtDecAccordo != null) {
		    switch (TiEnteConvenz.valueOf(tiEnteConvenz)) {
		    case GESTORE:
		    case CONSERVATORE:
			if (StringUtils.isBlank(popup)) {
			    List<OrgAccordoEnte> accordiEnteList = entiConvenzionatiEjb
				    .getOrgAccordoEnteValidListByTipoEnteConvenz(idEnteConvenz,
					    tiEnteConvenz);
			    if (!accordiEnteList.isEmpty()) {
				getRequest().setAttribute("customChiusuraAccordoMessageBox", true);
			    } else {
				getRequest().setAttribute("chiusuraAccordoBox", true);
			    }
			} else {
			    getRequest().setAttribute("chiusuraAccordoBox", true);
			}
			break;
		    case PRODUTTORE:
			if (StringUtils.isBlank(popup)) {
			    List<OrgEnteConvenzOrg> organizEnteList = entiConvenzionatiEjb
				    .getOrgEnteConvenzOrgListByEnteConvenz(idEnteConvenz);
			    if (!organizEnteList.isEmpty()) {
				getRequest().setAttribute("customChiusuraAccEnteProdutMessageBox",
					true);
			    } else {
				getRequest().setAttribute("chiusuraAccordoBox", true);
			    }
			} else {
			    getRequest().setAttribute("chiusuraAccordoBox", true);
			}
			break;
		    default:
			break;
		    }
		} else {
		    getMessageBox().addError(
			    "Errore nell'esecuzione della chiusura dell'accordo: l'accordo ha data di inizio validitÃ  non valorizzata");
		}
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}

	forwardToPublisher(getLastPublisher());
    }

    public void eseguiChiusuraAccordo() throws EMFError {
	Date currentDate = new Date();

	/* Campo note preso da finestra pop-up */
	final String notePopUp = (String) getRequest().getParameter("Note");
	/* Campo data preso da finestra pop-up */
	final String dataPopUp = (String) getRequest().getParameter("Data");
	BigDecimal idAccordoEnte = getForm().getAccordoDetail().getId_accordo_ente().parse();
	BigDecimal idTipoAccordo = getForm().getAccordoDetail().getId_tipo_accordo().parse();
	String dsNoteAccordo = getForm().getAccordoDetail().getDs_note_accordo().parse();
	BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam().parse();
	String nmEnteConvenz = getForm().getEnteConvenzionatoDetail().getNm_ente_siam().parse();
	String tiEnteConvenz = getForm().getEnteConvenzionatoDetail().getTi_ente_convenz().parse();
	Date dtIniVal = getForm().getEnteConvenzionatoDetail().getDt_ini_val().parse();
	Date dtCessazione = getForm().getEnteConvenzionatoDetail().getDt_cessazione().parse();
	Date dtDecAccordo = getForm().getAccordoDetail().getDt_dec_accordo().parse();

	if (StringUtils.isBlank(dataPopUp)) {
	    getMessageBox().addError("E' obbligatorio inserire la data di chiusura dell'accordo");
	}

	try {
	    if (!getMessageBox().hasError()) {
		Date dtChiusura = DateUtils.parseDate(dataPopUp, new String[] {
			DateUtil.DATE_FORMAT_SLASH });
		if (!dtChiusura.after(currentDate)) {
		    switch (TiEnteConvenz.valueOf(tiEnteConvenz)) {
		    case GESTORE:
		    case CONSERVATORE:
			// Richiamo il controllo inclusione dato il padre passando come entità padre
			// = accordo
			// ente da chiudere e come entità figlio gli accordi validi alla data in cui
			// l'ente
			// convenzionato in base
			// al suo tipo CONSERVATORE o GESTORE dell'accordo
			HashMap<String, Date[]> mappaAccordi = new HashMap<>();
			List<OrgAccordoEnte> accordiEnteList = entiConvenzionatiEjb
				.getOrgAccordoEnteValidListByTipoEnteConvenz(idEnteConvenz,
					tiEnteConvenz);
			for (OrgAccordoEnte accordo : accordiEnteList) {
			    Date[] dtArr = new Date[2];
			    dtArr[0] = accordo.getDtDecAccordo();
			    dtArr[1] = accordo.getDtFineValidAccordo();
			    mappaAccordi.put(accordo.getOrgEnteSiam().getNmEnteSiam(), dtArr);
			}
			entiConvenzionatiEjb.checkInclusionePadreFigli(dtDecAccordo, dtChiusura,
				nmEnteConvenz, mappaAccordi);
			break;
		    case PRODUTTORE:
			// Richiamo il controllo inclusione dato il padre passando come entità padre
			// = ente
			// produttore e come entità figlio le organizzazioni riferite all'ente
			// convenzionato PRODUTTORE
			HashMap<String, Date[]> mappaOrganiz = new HashMap<>();
			List<OrgEnteConvenzOrg> organizEnteList = entiConvenzionatiEjb
				.getOrgEnteConvenzOrgListByEnteConvenz(idEnteConvenz);
			for (OrgEnteConvenzOrg organiz : organizEnteList) {
			    Date[] dtArr = new Date[2];
			    dtArr[0] = organiz.getDtIniVal();
			    dtArr[1] = organiz.getDtFineVal();
			    mappaOrganiz.put(organiz.getUsrOrganizIam().getNmOrganiz(), dtArr);
			}
			entiConvenzionatiEjb.checkInclusionePadreFigli(dtIniVal, dtCessazione,
				nmEnteConvenz, mappaOrganiz);
			break;
		    default:
			break;
		    }
		    //
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    param.setNomeAzione(SpagoliteLogUtil.getButtonActionName(this.getForm(),
			    this.getForm().getAccordoDetail(),
			    this.getForm().getAccordoDetail().getChiusuraAccordo().getName()));
		    entiConvenzionatiEjb.chiusuraAccordo(param, idEnteConvenz, idAccordoEnte,
			    dtChiusura, dsNoteAccordo, notePopUp);
		    //
		    loadDettaglioAccordo(idAccordoEnte, idTipoAccordo);
		    getForm().getAccordoDetail().setViewMode();
		    getForm().getAccordoDetail().setStatus(Status.view);
		    getForm().getAccordiList().setStatus(Status.view);

		    getMessageBox().addInfo("Accordo ente convenzionato chiuso con successo");
		    getMessageBox().setViewMode(ViewMode.plain);
		} else {
		    getMessageBox().addError(
			    "La data di chiusura \u00E0 successiva alla data corrente; operazione di chiusura non consentita");
		}
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	} catch (ParseException e) {
	    getMessageBox()
		    .addError("Il campo (Data di chiusura accordo) inserito non \u00E8 valido");
	}

	forwardToPublisher(Application.Publisher.DETTAGLIO_ACCORDO);
    }

    public void annullaChiusuraAccordo() {
	forwardToPublisher(Application.Publisher.DETTAGLIO_ACCORDO);
    }

    ////////////////////
    // CAMBIO GESTORE //
    ////////////////////
    @Override
    public void cambioGestore() throws EMFError {
	// Pulizia campi
	getForm().getAccordoDetail().clear();
	try {
	    BigDecimal idAmbienteEnteConvenz = getForm().getEnteConvenzionatoDetail()
		    .getId_ambiente_ente_convenz().parse();
	    BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam()
		    .parse();

	    OrgAccordoEnteTableBean accordoEnte = entiConvenzionatiEjb
		    .getOrgAccordoEntePiuRecenteTableBean(idEnteConvenz);
	    if (accordoEnte.isEmpty()) {
		// Non dovrebbe mai verificarsi
		getMessageBox().addError("Nessun accordo definito per l'ente convenzionato");
	    }

	    if (!getMessageBox().hasError()) {

		getSession().setAttribute("cambioGestore", true);

		// Init combo ambiente per cambio gestore
		getForm().getEnteConvenzionatoDetail().getId_ambiente_ente_convenz_cambio()
			.setDecodeMap(
				DecodeMap.Factory.newInstance(
					entiConvenzionatiEjb
						.getUsrVAbilAmbEnteConvenzByLastAccordoTableBean(
							new BigDecimal(getUser().getIdUtente()),
							accordoEnte.getRow(0)
								.getIdEnteConvenzConserv()),
					"id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));
		getForm().getEnteConvenzionatoDetail().getId_ambiente_ente_convenz_cambio()
			.setValue("" + idAmbienteEnteConvenz);

		initAccordoDetail();

		getForm().getAccordoDetail().getId_ente_convenz_gestore()
			.setValue(accordoEnte.getRow(0).getIdEnteConvenzGestore().toPlainString());
		getForm().getAccordoDetail().getNm_ente_convenz_gestore()
			.setValue(accordoEnte.getRow(0).getString("nm_ente_convenz_gestore"));
		//
		getForm().getAccordoDetail().getId_ente_convenz_conserv()
			.setValue(accordoEnte.getRow(0).getIdEnteConvenzConserv().toPlainString());
		getForm().getAccordoDetail().getNm_ente_convenz_conserv()
			.setValue(accordoEnte.getRow(0).getString("nm_ente_convenz_conserv"));
		//
		getForm().getAccordoDetail().getId_ente_convenz_amministratore().setValue(
			accordoEnte.getRow(0).getIdEnteConvenzAmministratore().toPlainString());
		getForm().getAccordoDetail().getNm_ente_convenz_amministratore().setValue(
			accordoEnte.getRow(0).getString("nm_ente_convenz_amministratore"));

		// Riporto i dati presi da ente convenzionato
		getForm().getAccordoDetail().getNm_ente_siam()
			.setValue(getForm().getEnteConvenzionatoDetail().getNm_ente_siam().parse());

		getForm().getAccordoDetail().getTi_scopo_accordo().setValue("CONSERVAZIONE");

		getForm().getAccordoDetail().setEditMode();

		// Bottone di ricalcolo
		getForm().getAccordoDetail().getRicalcoloServiziErogati().setHidden(true);
		// Bottone di chiusura accordo
		getForm().getAccordoDetail().getChiusuraAccordo().setHidden(true);

		// Ambiente/Ente/Struttura in sola visualizzazione
		getForm().getAccordoDetail().getNm_ente_siam().setViewMode();
		getForm().getAccordoDetail().getNm_ente_convenz_gestore().setViewMode();
		getForm().getAccordoDetail().getNm_ente_convenz_conserv().setViewMode();
		getForm().getAccordoDetail().getNm_ente_convenz_amministratore().setViewMode();

		getForm().getAccordoDetail().getNm_ente().setViewMode();
		getForm().getAccordoDetail().getNm_strut().setViewMode();

		getForm().getAccordoDetail().getId_ente_convenz_gestore().setHidden(true);
		getForm().getAccordoDetail().getId_ente_convenz_conserv().setHidden(true);
		getForm().getAccordoDetail().getId_ente_convenz_amministratore().setHidden(true);

		getForm().getAccordoDetail().setStatus(Status.insert);
		getForm().getAccordiList().setStatus(Status.insert);

		getForm().getEnteConvenzionatoDetail().getId_ambiente_ente_convenz_cambio()
			.setEditMode();
		getForm().getEnteConvenzionatoDetail().setStatus(Status.insert);
		getForm().getListaEntiConvenzionati().setStatus(Status.insert);
		postLoad();
		forwardToPublisher(Application.Publisher.DETTAGLIO_ACCORDO);
	    } else {
		forwardToPublisher(getLastPublisher());
	    }
	} catch (ParerUserError e) {
	    getMessageBox().addError(e.getMessage());
	    forwardToPublisher(getLastPublisher());
	}
    }

    /////////////////////
    // CAMBIO AMBIENTE //
    /////////////////////
    @Override
    public void cambioAmbiente() throws EMFError {
	try {
	    // Al primo giro azzero lo stack
	    NavigatorDetailBeanManager.resetNavigatorDetailStack();

	    loadCurrentDettaglioEnteConvenzFromList(
		    getForm().getListaEntiConvenzionati().getName());

	    BigDecimal idAmbienteEnteConvenz = getForm().getEnteConvenzionatoDetail()
		    .getId_ambiente_ente_convenz().parse();
	    BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam()
		    .parse();

	    OrgAccordoEnteTableBean accordoEnte = entiConvenzionatiEjb
		    .getOrgAccordoEntePiuRecenteTableBean(idEnteConvenz);
	    if (accordoEnte.isEmpty()) {
		// Non dovrebbe mai verificarsi
		getMessageBox().addError("Nessun accordo definito per l'ente convenzionato");
	    }

	    if (!getMessageBox().hasError()) {

		getSession().setAttribute("cambioAmbiente", true);

		// Init combo ambiente per cambio ambiente
		getForm().getEnteConvenzionatoDetail().getId_ambiente_ente_convenz()
			.setDecodeMap(DecodeMap.Factory.newInstance(
				entiConvenzionatiEjb.getUsrVAbilAmbEnteXEnteByLastAccordoTableBean(
					new BigDecimal(getUser().getIdUtente()),
					accordoEnte.getRow(0).getIdEnteConvenzConserv(),
					accordoEnte.getRow(0).getIdEnteConvenzGestore()),
				"id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));
		getForm().getEnteConvenzionatoDetail().getId_ambiente_ente_convenz()
			.setValue("" + idAmbienteEnteConvenz);
	    }

	    getForm().getEnteConvenzionatoDetail().getId_ambiente_ente_convenz().setEditMode();
	    getForm().getEnteConvenzionatoDetail().getDt_ini_val_appart_ambiente().setEditMode();
	    getForm().getEnteConvenzionatoDetail().getDt_fin_val_appart_ambiente().setEditMode();
	    getForm().getListaEntiConvenzionati().setStatus(Status.update);
	    postLoad();
	    forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO);
	} catch (ParerUserError e) {
	    getMessageBox().addError(e.getMessage());
	    forwardToPublisher(getLastPublisher());
	}
    }

    @Override
    public void calcoloFattureProvvisorie() {

	// Pulizia campi
	getForm().getFattureProvvisorieFields().clear();

	try {
	    getForm().getFattureProvvisorieFields().setEditMode();
	    if (getLastPublisher().equals(Application.Publisher.RICERCA_ENTI_CONVENZIONATI)) {
		OrgVRicEnteConvenzTableBean listaEnti = (OrgVRicEnteConvenzTableBean) getForm()
			.getListaEntiConvenzionati().getTable();

		if (!listaEnti.isEmpty()) {
		    getForm().getFattureProvvisorieFields().getAnno_testata_provv()
			    .setValue("" + Calendar.getInstance().get(Calendar.YEAR));
		    getRequest().setAttribute("customCalcoloFattureProvvisorieMessageBox", true);
		} else {
		    getMessageBox().addError(
			    "Nessun ente convenzionato restituito dalla ricerca sul quale effettuare il calcolo fatture provvisorie");
		}

	    } else if (getLastPublisher()
		    .equals(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO)) {
		getForm().getFattureProvvisorieFields().getAnno_testata_provv()
			.setValue("" + Calendar.getInstance().get(Calendar.YEAR));
		getRequest().setAttribute("customCalcoloFattureProvvisorieMessageBox", true);
	    } else {
		getMessageBox().addError("Azione di calcolo fatture provvisorie non consentita");
	    }
	} catch (Exception e) {
	    getMessageBox().addError("Errore durante il calcolo delle fatture provvisorie");
	    getForm().getFattureProvvisorieFields().setViewMode();
	}
	forwardToPublisher(getLastPublisher());
    }

    @Override
    public void confermaCreazioneFattureProvvisorie() throws EMFError {
	String annoTestataString = (String) getRequest().getParameter("annoTestata");
	String annoFattServiziString = (String) getRequest().getParameter("annoFattServizi");
	BigDecimal annoCorrente = BigDecimal.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	BigDecimal annoTestata = null;
	BigDecimal annoFattServizi = null;

	// Controllo sulla correttezza formale dei campi
	try {
	    annoTestata = new BigDecimal(annoTestataString);
	    if (annoTestata.longValue() < 1000L) {
		throw new NumberFormatException();
	    }
	    getForm().getFattureProvvisorieFields().getAnno_testata_provv()
		    .setValue("" + annoTestata);
	} catch (NumberFormatException e) {
	    getMessageBox().addError("Anno testata mancante o non formalmente non corretto <br>");
	}
	try {
	    annoFattServizi = new BigDecimal(annoFattServiziString);
	    if (annoFattServizi.longValue() < 1000L) {
		throw new NumberFormatException();
	    }
	    getForm().getFattureProvvisorieFields().getAnno_fatt_servizi_provv()
		    .setValue("" + annoFattServizi);
	} catch (NumberFormatException e) {
	    getMessageBox()
		    .addError("Anno fatturazione servizi mancante o formalmente non corretto");
	}

	if (!getMessageBox().hasError()) {

	    // Controlli vincoli sui campi
	    if (annoTestata.longValue() > annoCorrente.longValue()) {
		getMessageBox().addError(
			"L\u0027anno della testata fattura non pu\u00F2 essere successivo all\u0027anno corrente");
	    }
	    if (annoFattServizi.longValue() > annoTestata.longValue()) {
		getMessageBox().addError(
			"L\u0027anno di fatturazione dei servizi non pu\u00F2 essere superiore all\u0027anno di testata della fattura");
	    }

	    if (!getMessageBox().hasError()) {
		LogParam param = SpagoliteLogUtil.getLogParam(
			paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			SpagoliteLogUtil.getPageName(this));
		param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		param.setNomeAzione(SpagoliteLogUtil.getButtonActionName(this.getForm(),
			this.getForm().getFattureProvvisorieFields(),
			this.getForm().getFattureProvvisorieFields().getCalcoloFattureProvvisorie()
				.getName()));

		int numFattureCalcolate = 0;
		if (getLastPublisher().equals(Application.Publisher.RICERCA_ENTI_CONVENZIONATI)) {
		    // Provenendo dalla pagina di ricerca, prelevo la lista di enti convenzionati
		    // risultati dalla
		    // ricerca
		    OrgVRicEnteConvenzTableBean entiConvenzionati = (OrgVRicEnteConvenzTableBean) getForm()
			    .getListaEntiConvenzionati().getTable();

		    for (OrgVRicEnteConvenzRowBean enteConvenzionato : entiConvenzionati) {
			BigDecimal idEnteConvenz = enteConvenzionato.getIdEnteConvenz();
			BigDecimal idAmbienteEnteConvenz = null;
			try {
			    OrgEnteSiamRowBean enteSiamRB = entiConvenzionatiEjb
				    .getOrgEnteConvenzRowBean(idEnteConvenz);
			    idAmbienteEnteConvenz = enteSiamRB.getIdAmbienteEnteConvenz();
			} catch (ParerUserError ex) {
			    getMessageBox().addError(ex.getMessage());
			}

			String[][] ambEnteStrut = null;
			// Carico i valori precompilati
			String nmAmbiente = null;
			String nmEnte = null;
			String nmStrut = null;
			try {
			    ambEnteStrut = entiConvenzionatiEjb.getAmbEnteStrutByIamParamApplic(
				    idAmbienteEnteConvenz, idEnteConvenz);
			    nmAmbiente = ambEnteStrut[0][1];
			    nmEnte = ambEnteStrut[1][1];
			    nmStrut = ambEnteStrut[2][1];
			} catch (ParerUserError ex) {
			    getMessageBox().addError(
				    "Errore durante il recupero dei parametri su ente e struttura: controllare che siano stati impostati in maniera esatta");
			}

			int numFatture = entiConvenzionatiEjb.calcolaFattureProvvisorie(param,
				enteConvenzionato.getIdEnteConvenz(), annoTestata, annoFattServizi,
				nmAmbiente, nmEnte, nmStrut);
			numFattureCalcolate = numFattureCalcolate + numFatture;
		    }
		} else if (getLastPublisher()
			.equals(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO)) {
		    BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail()
			    .getId_ente_siam().parse();
		    BigDecimal idAmbienteEnteConvenz = getForm().getEnteConvenzionatoDetail()
			    .getId_ambiente_ente_convenz().parse();
		    String[][] ambEnteStrut = null;
		    // Carico i valori precompilati
		    String nmAmbiente = null;
		    String nmEnte = null;
		    String nmStrut = null;
		    try {
			ambEnteStrut = entiConvenzionatiEjb.getAmbEnteStrutByIamParamApplic(
				idAmbienteEnteConvenz, idEnteConvenz);
			nmAmbiente = ambEnteStrut[0][1];
			nmEnte = ambEnteStrut[1][1];
			nmStrut = ambEnteStrut[2][1];
		    } catch (ParerUserError ex) {
			getMessageBox().addError(
				"Errore durante il recupero dei parametri su ente e struttura: controllare che siano stati impostati in maniera esatta");
		    }

		    // Provenendo dalla pagina di dettaglio, prelevo l'unico record dell'ente
		    // convenzionato
		    int numFatture = entiConvenzionatiEjb.calcolaFattureProvvisorie(param,
			    idEnteConvenz, annoTestata, annoFattServizi, nmAmbiente, nmEnte,
			    nmStrut);
		    numFattureCalcolate = numFattureCalcolate + numFatture;
		}
		getMessageBox().addInfo("Totale fatture calcolate: " + numFattureCalcolate);
	    }
	}
	forwardToPublisher(getLastPublisher());
    }

    @Override
    public void annullaCreazioneFattureProvvisorie() throws EMFError {
	// Nascondo i bottoni con javascript disattivato
	getForm().getFattureProvvisorieFields().setViewMode();
    }

    ///////////////////////////////
    // RIEMETTI FATTURE STORNATE //
    ///////////////////////////////
    @Override
    public void riemettiFattureStornate() throws EMFError {
	// Pulizia campi
	getForm().getRiemissioneFattureFields().clear();

	try {
	    getForm().getRiemissioneFattureFields().setEditMode();
	    if (getLastPublisher().equals(Application.Publisher.RICERCA_ENTI_CONVENZIONATI)) {
		OrgVRicEnteConvenzTableBean listaEnti = (OrgVRicEnteConvenzTableBean) getForm()
			.getListaEntiConvenzionati().getTable();

		if (!listaEnti.isEmpty()) {
		    getForm().getRiemissioneFattureFields().getAnno_testata_storn()
			    .setValue("" + Calendar.getInstance().get(Calendar.YEAR));
		    getRequest().setAttribute("customRiemissioneFattureStornateMessageBox", true);
		} else {
		    getMessageBox().addError(
			    "Nessun ente convenzionato restituito dalla ricerca sul quale effettuare la riemissione delle fatture stornate");
		}
	    } else {
		getMessageBox().addError("Azione di riemissione fatture stornate non consentita");
	    }
	} catch (Exception e) {
	    getMessageBox().addError("Errore durante la riemissione delle fatture stornate");
	    getForm().getRiemissioneFattureFields().setViewMode();
	}
	forwardToPublisher(Application.Publisher.RICERCA_ENTI_CONVENZIONATI);
    }

    @Override
    public void confermaRiemissioneFattureStornate() throws EMFError {
	String annoTestataString = (String) getRequest().getParameter("annoTestata");
	BigDecimal annoCorrente = BigDecimal.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	BigDecimal annoTestata = null;

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
			"L\u0027anno della testata fattura non pu\u00F2 essere successivo all\u0027anno corrente");
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

		int numFattureCalcolate = 0;
		if (getLastPublisher().equals(Application.Publisher.RICERCA_ENTI_CONVENZIONATI)) {
		    // Provenendo dalla pagina di ricerca, prelevo la lista di enti convenzionati
		    // risultati dalla
		    // ricerca
		    OrgVRicEnteConvenzTableBean entiConvenzionati = (OrgVRicEnteConvenzTableBean) getForm()
			    .getListaEntiConvenzionati().getTable();

		    for (OrgVRicEnteConvenzRowBean enteConvenzionato : entiConvenzionati) {
			numFattureCalcolate += entiConvenzionatiEjb.riemettiFattureStornate(param,
				enteConvenzionato.getIdEnteConvenz(), annoTestata);
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

    @Override
    public void selezionaUtenteArchivista() throws EMFError {
	getForm().getUtenteArchivistaDetail().post(getRequest());
	AmministrazioneUtentiForm amministrazioneUtentiForm = new AmministrazioneUtentiForm();
	BigDecimal idUserIamCor = new BigDecimal(getUser().getIdUtente());
	final BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam()
		.parse();
	amministrazioneUtentiForm.getUtenteArchivistaPerEnteConvenzionato().getId_ente_convenz()
		.setValue(idEnteConvenz.toPlainString());

	try {
	    // Recupero gli enti di appartenzenza di tipo conservatore e amministratore e i
	    // rispettivi enti collegati
	    // cui l'utente corrente è abilitato
	    OrgEnteSiamTableBean enteSiamTableBean = entiConvenzionatiEjb
		    .getOrgEnteSiamCollegUserAbilTableBeanByTiEnteConvenz(idUserIamCor,
			    TiEnteConvenz.CONSERVATORE, TiEnteConvenz.AMMINISTRATORE);
	    amministrazioneUtentiForm.getFiltriUtenti().getId_ente_convenz_appart()
		    .setDecodeMap(DecodeMap.Factory.newInstance(enteSiamTableBean, "id_ente_siam",
			    "nm_ente_siam"));
	    if (enteSiamTableBean.size() == 1) {
		String[] valoriEnte = new String[] {
			"" + enteSiamTableBean.getRow(0).getIdEnteSiam() };
		amministrazioneUtentiForm.getFiltriUtenti().getId_ente_convenz_appart()
			.setValues(valoriEnte);
	    }
	    // Recupero gli ambienti enti di appartenzenza di tipo conservatore e amministratore e i
	    // rispettivi ambienti
	    // degli enti collegati cui l'utente corrente è abilitato
	    OrgAmbienteEnteConvenzTableBean ambienteEnteConvenzTableBean = entiConvenzionatiEjb
		    .getOrgAmbientiEnteConvenzCollegUserAbilTableBeanByTiEnteConvenz(idUserIamCor,
			    TiEnteConvenz.CONSERVATORE, TiEnteConvenz.AMMINISTRATORE);
	    amministrazioneUtentiForm.getFiltriUtenti().getId_amb_ente_convenz_appart()
		    .setDecodeMap(DecodeMap.Factory.newInstance(ambienteEnteConvenzTableBean,
			    "id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));
	    if (enteSiamTableBean.size() == 1) {
		String[] valoriAmbiente = new String[] {
			"" + enteSiamTableBean.getRow(0).getIdAmbienteEnteConvenz() };
		amministrazioneUtentiForm.getFiltriUtenti().getId_amb_ente_convenz_appart()
			.setValues(valoriAmbiente);
	    }
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}

	redirectToAction(Application.Actions.AMMINISTRAZIONE_UTENTI,
		"?operation=ricercaUtentiArchivistiPage", amministrazioneUtentiForm);
    }

    @Override
    public void rimuoviUtenteArchivista() throws EMFError {
	getForm().getUtenteArchivistaDetail().post(getRequest());
	getForm().getUtenteArchivistaDetail().getId_user_iam().setValue(null);
	getForm().getUtenteArchivistaDetail().getNm_userid_codificato().setValue(null);
	forwardToPublisher(Application.Publisher.DETTAGLIO_ARCHIVISTA_RIFERIMENTO);
    }

    private void saveUtenteArchivista() throws EMFError {
	if (getForm().getUtenteArchivistaDetail().postAndValidate(getRequest(), getMessageBox())) {
	    // CONTROLLI DI COERENZA NELLA MASCHERA
	    // Controllo che solo uno tra utente manuale ed utente codificato sia valorizzato
	    if ((getForm().getUtenteArchivistaDetail().getNm_userid().parse() != null && getForm()
		    .getUtenteArchivistaDetail().getNm_userid_codificato().parse() != null)
		    || (getForm().getUtenteArchivistaDetail().getNm_userid().parse() == null)
			    && getForm().getUtenteArchivistaDetail().getNm_userid_codificato()
				    .parse() == null) {
		getMessageBox().addError(
			"E' obbligatorio inserire l'utente manuale o l'utente codificato (uno solo)");
	    }

	    try {
		if (!getMessageBox().hasError()) {
		    // Salva l'utente archivista
		    BigDecimal idEnteSiam = getForm().getEnteConvenzionatoDetail().getId_ente_siam()
			    .parse();
		    BigDecimal idUserIamCor = new BigDecimal(getUser().getIdUtente());
		    String flReferente = getForm().getUtenteArchivistaDetail().getFl_referente()
			    .parse();
		    String dlNote = getForm().getUtenteArchivistaDetail().getDl_note().parse();
		    String nmUserid = getForm().getUtenteArchivistaDetail().getNm_userid().parse();
		    String nmUseridCodificato = getForm().getUtenteArchivistaDetail()
			    .getNm_userid_codificato().parse();

		    if (StringUtils.isNotBlank(nmUserid)) {
			// Controllo che lo userid corrisponda a quello di un utente censito nel
			// sistema e associato ad
			// un utente attivo di tipo persona fisica appartenente a un ente di cui
			// sopra
			UsrUserRowBean userRowBean = amministrazioneUtentiEjb
				.getUserRowBean(nmUserid);
			if (userRowBean.getIdUserIam() != null
				&& userRowBean.getFlAttivo().equals("1")
				&& userRowBean.getTipoUser()
					.equals(ApplEnum.TipoUser.PERSONA_FISICA.name())
				&& entiConvenzionatiEjb.checkUtenteArkAbilEntiConvenzColleg(
					idUserIamCor, userRowBean.getIdUserIam())) {
			    getForm().getUtenteArchivistaDetail().getId_user_iam()
				    .setValue(userRowBean.getIdUserIam().toPlainString());
			} else {
			    throw new ParerUserError(
				    "Utente non esistente o non appartenente al dominio per lo username '"
					    + nmUserid + "' specificato");
			}
		    }

		    if (StringUtils.isNotBlank(nmUseridCodificato)) {
			// Recupero l'identificativo corrispondente a quello dell'utente censito nel
			// sistema
			UsrUserRowBean userRowBean = amministrazioneUtentiEjb
				.getUserRowBean(nmUseridCodificato);
			if (entiConvenzionatiEjb.checkUtenteArkAbilEntiConvenzColleg(idUserIamCor,
				userRowBean.getIdUserIam())) {
			    getForm().getUtenteArchivistaDetail().getId_user_iam()
				    .setValue(userRowBean.getIdUserIam().toPlainString());
			} else {
			    throw new ParerUserError(
				    "Utente non esistente o non appartenente al dominio per lo username '"
					    + nmUseridCodificato + "' specificato");
			}
		    }

		    OrgEnteArkRifRowBean enteArkRifRowBean = new OrgEnteArkRifRowBean();
		    getForm().getUtenteArchivistaDetail().copyToBean(enteArkRifRowBean);

		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    if (getForm().getUtentiArchivistiList().getStatus().equals(Status.insert)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
			Long idEnteArkRif = entiConvenzionatiEjb.insertUtenteArchivistaToEnteSiam(
				param, idEnteSiam,
				getForm().getUtenteArchivistaDetail().getId_user_iam().parse(),
				flReferente, dlNote);
			if (idEnteArkRif != null) {
			    enteArkRifRowBean.setIdEnteArkRif(new BigDecimal(idEnteArkRif));
			}
			getForm().getUtentiArchivistiList().getTable().last();
			getForm().getUtentiArchivistiList().getTable().add(enteArkRifRowBean);
		    } else if (getForm().getUtentiArchivistiList().getStatus()
			    .equals(Status.update)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
			BigDecimal idEnteArkRif = getForm().getUtenteArchivistaDetail()
				.getId_ente_ark_rif().parse();
			entiConvenzionatiEjb.updateUtenteArchivistaToEnteSiam(param, idEnteArkRif,
				idEnteSiam, flReferente, dlNote);
		    }

		    getForm().getUtenteArchivistaDetail().setViewMode();
		    getForm().getUtentiArchivistiList().setStatus(Status.view);
		    getForm().getUtenteArchivistaDetail().setStatus(Status.view);
		    getMessageBox()
			    .addInfo("Utente archivista di riferimento salvato con successo");
		    getMessageBox().setViewMode(ViewMode.plain);
		    goBack();
		} else {
		    forwardToPublisher(Application.Publisher.DETTAGLIO_ARCHIVISTA_RIFERIMENTO);
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
		forwardToPublisher(Application.Publisher.DETTAGLIO_ARCHIVISTA_RIFERIMENTO);
	    }
	}
    }

    @Override
    public void selezionaReferenteEnte() throws EMFError {
	// Verifica se la richiesta è multipart
	// if (ServletFileUpload.isMultipartContent(getRequest())) {
	// try {
	// // Usa postMultipart invece di post per la gestione dei file
	// getForm().getReferenteEnteDetail().postMultipart(getRequest(), 0);
	// } catch (FileUploadException ex) {
	// logger.error(
	// "Errore nell'invocazione del metodo di navigazione :" +
	// ExceptionUtils.getRootCauseMessage(ex),
	// ex);
	// getMessageBox().addError("Errore nella navigazione ");
	// goBack();
	// }
	// } else {
	// // Se la richiesta non è multipart, puoi usare il metodo post normale
	// getForm().getReferenteEnteDetail().post(getRequest());
	// }
	AmministrazioneUtentiForm amministrazioneUtentiForm = new AmministrazioneUtentiForm();
	final BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam()
		.parse();
	amministrazioneUtentiForm.getReferentePerEnteConvenzionato().getId_ente_convenz()
		.setValue(idEnteConvenz.toPlainString());

	// Mi salvo l'ente che sarà poi l'ente di appartenenza in fase di ricerca
	getSession().setAttribute("ente_appart_referente",
		getForm().getEnteConvenzionatoDetail().getId_ente_siam().parse());

	redirectToAction(Application.Actions.AMMINISTRAZIONE_UTENTI,
		"?operation=ricercaUtentiReferentiPage", amministrazioneUtentiForm);
    }

    @Override
    public void rimuoviReferenteEnte() throws EMFError {
	getForm().getReferenteEnteDetail().getId_user_iam().setValue(null);
	getForm().getReferenteEnteDetail().getNm_userid_codificato().setValue(null);
	forwardToPublisher(Application.Publisher.DETTAGLIO_REFERENTE_ENTE);
    }

    private void saveReferenteEnte() throws EMFError {
	if (getForm().getReferenteEnteDetail().validate(getMessageBox())) {
	    // CONTROLLI DI COERENZA NELLA MASCHERA
	    // Controllo che solo uno tra utente manuale ed utente codificato sia valorizzato
	    if ((getForm().getReferenteEnteDetail().getNm_userid().parse() != null
		    && getForm().getReferenteEnteDetail().getNm_userid_codificato().parse() != null)
		    || (getForm().getReferenteEnteDetail().getNm_userid().parse() == null)
			    && getForm().getReferenteEnteDetail().getNm_userid_codificato()
				    .parse() == null) {
		getMessageBox().addError(
			"E' obbligatorio inserire l'utente manuale o l'utente codificato (uno solo)");
	    }

	    if (getForm().getReferenteEnteDetail().getDt_reg_ente_user_rif().parse() != null
		    && getForm().getReferenteEnteDetail().getDt_reg_ente_user_rif().parse()
			    .after(new Date())) {
		getMessageBox().addError(
			"Attenzione: data registrazione non pu\u00F2 essere una data futura");
	    }

	    // Controlli di congruità
	    if (getForm().getReferenteEnteDetail().getBl_ente_user_rif().parse() != null) {
		if (StringUtils
			.isNotBlank(getForm().getReferenteEnteDetail()
				.getCd_registro_ente_user_rif().getValue())
			|| StringUtils.isNotBlank(
				getForm().getReferenteEnteDetail().getAa_ente_user_rif().getValue())
			|| StringUtils.isNotBlank(getForm().getReferenteEnteDetail()
				.getCd_key_ente_user_rif().getValue())
			|| StringUtils.isNotBlank(getForm().getReferenteEnteDetail()
				.getDt_reg_ente_user_rif().getValue())) {
		    getMessageBox().addError(
			    "Caricare un file semplice oppure inserire la chiave Registro/Anno/Numero/Data Registrazione<br>");
		    getForm().getReferenteEnteDetail().getBl_ente_user_rif().setValue(null);
		}
	    }
	    String nmFileEnteUser = getForm().getReferenteEnteDetail().getBl_ente_user_rif()
		    .parse();
	    if (nmFileEnteUser != null && nmFileEnteUser.length() > 100) {
		getMessageBox()
			.addError("Il nome del file caricato supera i 100 caratteri ammessi<br>");
	    } else if (nmFileEnteUser != null) {
		getForm().getReferenteEnteDetail().getNm_file_ente_user_rif()
			.setValue(nmFileEnteUser);
	    }

	    try {
		if (!getMessageBox().hasError()) {
		    // Salva l'utente referente
		    BigDecimal idEnteSiam = getForm().getEnteConvenzionatoDetail().getId_ente_siam()
			    .parse();
		    String qualificaUser = getForm().getReferenteEnteDetail().getQualifica_user()
			    .parse();
		    String dlNote = getForm().getReferenteEnteDetail().getDl_note().parse();
		    String nmUserid = getForm().getReferenteEnteDetail().getNm_userid().parse();
		    String nmUseridCodificato = getForm().getReferenteEnteDetail()
			    .getNm_userid_codificato().parse();
		    BigDecimal idUserIam = null;

		    if (StringUtils.isNotBlank(nmUserid)) {
			// Controllo che lo userid corrisponda a quello di un utente censito nel
			// sistema
			UsrUserRowBean userRowBean = amministrazioneUtentiEjb
				.getUserRowBean(nmUserid);
			idUserIam = userRowBean.getIdUserIam();
			if (idUserIam != null) {
			    getForm().getReferenteEnteDetail().getId_user_iam()
				    .setValue(idUserIam.toPlainString());
			} else {
			    throw new ParerUserError(
				    "Utente non esistente o non appartenente al dominio per lo username '"
					    + nmUserid + "' specificato");
			}
		    }
		    /* MEV 19339 */
		    if (StringUtils.isNotBlank(nmUseridCodificato)) {
			// Recupero l'identificativo corrispondente a quello dell'utente censito nel
			// sistema
			UsrUserRowBean userRowBean = amministrazioneUtentiEjb
				.getUserRowBean(nmUseridCodificato);
			idUserIam = userRowBean.getIdUserIam();
		    }
		    // Controllo che l'utente scelto appartenga all'ente corrente, oppure appartenga
		    // ad un ente
		    // collegato all'ente convenzionato,
		    // oppure appartenga all'ente convenzionato corrispondente all'ente non
		    // convenzionato del referente
		    if (!amministrazioneUtentiEjb.isReferenteOk(idEnteSiam, idUserIam)) {
			String tiEnte = entiConvenzionatiEjb.getTiEnteSiam(idEnteSiam);
			// e non appartiene ad un ente collegato o all'ente convenzionato
			// corrispondente
			StringBuilder msg = new StringBuilder();
			msg.append(
				"L'utente non pu\u00F2 essere un referente dell'ente in quanto non appartiene all'ente stesso ");
			if (tiEnte.equals(TiEnteSiam.CONVENZIONATO.name())) {
			    msg.append(" e non appartiene ad un ente collegato");
			} else {
			    msg.append(" e non appartiene all'ente convenzionato corrispondente");
			}
			throw new ParerUserError(msg.toString());
		    }
		    /* end MEV 19339 */

		    String cdRegistroEnteUserRif = getForm().getReferenteEnteDetail()
			    .getCd_registro_ente_user_rif().parse();
		    BigDecimal aaEnteUserRif = getForm().getReferenteEnteDetail()
			    .getAa_ente_user_rif().parse();
		    String cdKeyEnteUserRif = getForm().getReferenteEnteDetail()
			    .getCd_key_ente_user_rif().parse();
		    Date dtRegEnteUserRif = getForm().getReferenteEnteDetail()
			    .getDt_reg_ente_user_rif().parse();
		    byte[] blEnteUserRif = null;
		    if (getForm().getReferenteEnteDetail().getBl_ente_user_rif()
			    .getValue() != null) {
			blEnteUserRif = getForm().getReferenteEnteDetail().getBl_ente_user_rif()
				.getFileBytes();
		    }

		    String dsEnteUserRif = getForm().getReferenteEnteDetail().getDs_ente_user_rif()
			    .parse();
		    String nmFileEnteUserRif = getForm().getReferenteEnteDetail()
			    .getNm_file_ente_user_rif().parse();

		    OrgEnteUserRifRowBean enteUserRifRowBean = new OrgEnteUserRifRowBean();
		    getForm().getReferenteEnteDetail().copyToBean(enteUserRifRowBean);

		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    if (getForm().getReferentiEnteList().getStatus().equals(Status.insert)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
			Long idEnteUserRif = entiConvenzionatiEjb.insertReferenteEnteToEnteSiam(
				param, idEnteSiam,
				getForm().getReferenteEnteDetail().getId_user_iam().parse(),
				qualificaUser, dlNote, cdRegistroEnteUserRif, aaEnteUserRif,
				cdKeyEnteUserRif, dtRegEnteUserRif, blEnteUserRif,
				nmFileEnteUserRif, dsEnteUserRif);
			if (idEnteUserRif != null) {
			    enteUserRifRowBean.setIdEnteUserRif(new BigDecimal(idEnteUserRif));
			}
			getForm().getReferentiEnteList().getTable().last();
			getForm().getReferentiEnteList().getTable().add(enteUserRifRowBean);
		    } else if (getForm().getReferentiEnteList().getStatus().equals(Status.update)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
			BigDecimal idEnteUserRif = getForm().getReferenteEnteDetail()
				.getId_ente_user_rif().parse();
			entiConvenzionatiEjb.updateReferenteEnteToEnteSiam(param, idEnteSiam,
				idEnteUserRif, dlNote, cdRegistroEnteUserRif, aaEnteUserRif,
				cdKeyEnteUserRif, dtRegEnteUserRif, blEnteUserRif,
				nmFileEnteUserRif, dsEnteUserRif);
		    }

		    getForm().getReferenteEnteDetail().setViewMode();
		    getForm().getReferentiEnteList().setStatus(Status.view);
		    getForm().getReferenteEnteDetail().setStatus(Status.view);
		    getMessageBox().addInfo("Referente dell'ente salvato con successo");
		    getMessageBox().setViewMode(ViewMode.plain);
		    goBack();
		} else {
		    forwardToPublisher(Application.Publisher.DETTAGLIO_REFERENTE_ENTE);
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
		forwardToPublisher(Application.Publisher.DETTAGLIO_REFERENTE_ENTE);
	    }
	}

    }

    private void saveCollegamentoEnte() throws EMFError {
	if (getForm().getCollegamentoEnteDetail().postAndValidate(getRequest(), getMessageBox())) {
	    // CONTROLLI DI COERENZA NELLA MASCHERA
	    Date dtIniVal = getForm().getCollegamentoEnteDetail().getDt_ini_val().parse();
	    Date dtFinVal = getForm().getCollegamentoEnteDetail().getDt_fin_val().parse();
	    BigDecimal idEnteConvenzCapofila = getForm().getCollegamentoEnteDetail()
		    .getId_ente_convenz_capofila().parse();
	    String tiColleg = getForm().getCollegamentoEnteDetail().getTi_colleg().parse();

	    AmministrazioneEntiConvenzionatiValidator validator = new AmministrazioneEntiConvenzionatiValidator(
		    getMessageBox());
	    validator.validaOrdineDateOrari(dtIniVal, dtFinVal,
		    getForm().getCollegamentoEnteDetail().getDt_ini_val().getHtmlDescription(),
		    getForm().getCollegamentoEnteDetail().getDt_fin_val().getHtmlDescription());

	    if (idEnteConvenzCapofila != null) {
		if (!tiColleg.equals(TiColleg.GERARCHICO.name())) {
		    getMessageBox().addError(
			    "E\u0027 possibile indicare l\u0027ente capofila solo se il tipo di collegamento \u00E0 "
				    + TiColleg.GERARCHICO.name());
		}
	    }

	    try {
		if (!getMessageBox().hasError()) {
		    // Salva il collegamento
		    String nmColleg = getForm().getCollegamentoEnteDetail().getNm_colleg().parse();
		    String dsColleg = getForm().getCollegamentoEnteDetail().getDs_colleg().parse();

		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    if (getForm().getCollegamentiEnteList().getStatus().equals(Status.insert)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
			Long idCollegEntiConvenz = entiConvenzionatiEjb.insertNuovoCollegamentoEnte(
				param, idEnteConvenzCapofila, nmColleg, dsColleg, tiColleg,
				dtIniVal, dtFinVal);
			if (idCollegEntiConvenz != null) {
			    getForm().getCollegamentoEnteDetail().getId_colleg_enti_convenz()
				    .setValue(idCollegEntiConvenz.toString());
			}

			OrgCollegEntiConvenzRowBean collegEntiConvenzRowBean = new OrgCollegEntiConvenzRowBean();
			getForm().getCollegamentoEnteDetail().copyToBean(collegEntiConvenzRowBean);
			getForm().getCollegamentiEnteList().getTable().last();
			getForm().getCollegamentiEnteList().getTable()
				.add(collegEntiConvenzRowBean);
		    } else if (getForm().getCollegamentiEnteList().getStatus()
			    .equals(Status.update)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
			BigDecimal idCollegEntiConvenz = getForm().getCollegamentoEnteDetail()
				.getId_colleg_enti_convenz().parse();
			// Richiamo il controllo inclusione dato padre passando come entità padre =
			// collegamento
			// e come entità figlio gli intervalli di appartenza al collegamento
			// definite su ciascun
			// ente appartenente al collegamento
			HashMap<String, Date[]> mappaColleg = new HashMap<String, Date[]>();
			List<OrgAppartCollegEnti> appartCollegEntiList = entiConvenzionatiEjb
				.getOrgAppartCollegEntiList(idCollegEntiConvenz);
			for (OrgAppartCollegEnti colleg : appartCollegEntiList) {
			    Date[] dtArr = new Date[2];
			    dtArr[0] = colleg.getDtIniVal();
			    dtArr[1] = colleg.getDtFinVal();
			    mappaColleg.put(colleg.getOrgEnteSiam().getNmEnteSiam(), dtArr);

			}
			entiConvenzionatiEjb.checkInclusionePadreFigli(dtIniVal, dtFinVal, nmColleg,
				mappaColleg);
			Set<BigDecimal> appartCollegEntiSet = appartCollegEntiList.stream()
				.map(sc -> new BigDecimal(sc.getOrgEnteSiam().getIdEnteSiam()))
				.collect(Collectors.toSet());
			entiConvenzionatiEjb.updateCollegamento(param, idCollegEntiConvenz,
				idEnteConvenzCapofila, nmColleg, dsColleg, tiColleg, dtIniVal,
				dtFinVal, appartCollegEntiSet);
		    }

		    BigDecimal idCollegEntiConvenz = getForm().getCollegamentoEnteDetail()
			    .getId_colleg_enti_convenz().parse();
		    loadDettaglioCollegamento(idCollegEntiConvenz);
		    getMessageBox()
			    .addInfo("Collegamento dell'ente convenzionato salvato con successo");
		    getMessageBox().setViewMode(ViewMode.plain);
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
	    }
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_COLLEGAMENTO_ENTE);
    }

    private void saveEnteCollegato() throws EMFError {
	if (getForm().getEnteCollegatoDetail().postAndValidate(getRequest(), getMessageBox())) {
	    // CONTROLLI DI COERENZA NELLA MASCHERA
	    Date dtIniAppart = getForm().getEnteCollegatoDetail().getDt_ini_appart().parse();
	    Date dtFinAppart = getForm().getEnteCollegatoDetail().getDt_fin_appart().parse();

	    if (dtFinAppart.before(dtIniAppart)) {
		getMessageBox().addError(
			"La data di inizio appartenenza non pu\u00F2 essere successiva alla data di fine appartenenza");
	    }

	    try {
		if (!getMessageBox().hasError()) {
		    // Salva l'associazione al collegamento
		    BigDecimal idCollegEntiConvenz = getForm().getCollegamentoEnteDetail()
			    .getId_colleg_enti_convenz().parse();
		    BigDecimal idEnteConvenzCapofila = getForm().getCollegamentoEnteDetail()
			    .getId_ente_convenz_capofila().parse();
		    String nmColleg = getForm().getCollegamentoEnteDetail().getNm_colleg().parse();
		    Date dtIniVal = getForm().getCollegamentoEnteDetail().getDt_ini_val().parse();
		    Date dtFinVal = getForm().getCollegamentoEnteDetail().getDt_fin_val().parse();
		    BigDecimal idEnteConvenzAppart = getForm().getEnteCollegatoDetail()
			    .getId_ente_convenz().parse();

		    // Se è stato indicato un ente capofila, richiamo il controllo inclusione dato
		    // figlio
		    // passando come entità figlio = collegamento e entità padre = ente capofila.
		    if (idEnteConvenzCapofila != null) {
			OrgEnteSiamRowBean enteCapofila = entiConvenzionatiEjb
				.getOrgEnteConvenzRowBean(idEnteConvenzCapofila);
			entiConvenzionatiEjb.checkInclusioneFiglio(enteCapofila.getDtIniVal(),
				enteCapofila.getDtCessazione(), dtIniVal, dtFinVal,
				enteCapofila.getNmEnteSiam(), nmColleg);
		    }

		    // Creo una mappa con gli intervalli di appartenza del nuovo ente al
		    // collegamento
		    HashMap<String, Date[]> mappaColleg = new HashMap<String, Date[]>();
		    Date[] dtCurrArr = new Date[2];
		    dtCurrArr[0] = dtIniAppart;
		    dtCurrArr[1] = dtFinAppart;
		    mappaColleg.put(entiConvenzionatiEjb
			    .getOrgEnteConvenzRowBean(idEnteConvenzAppart).getNmEnteSiam(),
			    dtCurrArr);
		    // Richiamo il controllo inclusione dato padre passando come entità padre =
		    // collegamento
		    // e come entità figlio gli intervalli di appartenza al collegamento definite su
		    // ciascun ente
		    // appartenente al collegamento
		    List<OrgAppartCollegEnti> appartCollegEntiList = entiConvenzionatiEjb
			    .getOrgAppartCollegEntiList(idCollegEntiConvenz);
		    for (OrgAppartCollegEnti colleg : appartCollegEntiList) {
			Date[] dtArr = new Date[2];
			dtArr[0] = colleg.getDtIniVal();
			dtArr[1] = colleg.getDtFinVal();
			mappaColleg.put(colleg.getOrgEnteSiam().getNmEnteSiam(), dtArr);

		    }
		    entiConvenzionatiEjb.checkInclusionePadreFigli(dtIniVal, dtFinVal, nmColleg,
			    mappaColleg);

		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    if (getForm().getEntiCollegatiList().getStatus().equals(Status.insert)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
			Long idAppartCollegEnti = entiConvenzionatiEjb.insertOrgAppartCollegEnti(
				param, getUser().getIdUtente(), idCollegEntiConvenz,
				idEnteConvenzAppart, dtIniAppart, dtFinAppart);
			if (idAppartCollegEnti != null) {
			    getForm().getEnteCollegatoDetail().getId_appart_colleg_enti()
				    .setValue(idAppartCollegEnti.toString());
			}

			OrgAppartCollegEntiRowBean appartCollegEntiRowBean = new OrgAppartCollegEntiRowBean();
			getForm().getEnteCollegatoDetail().copyToBean(appartCollegEntiRowBean);
			getForm().getEntiCollegatiList().getTable().last();
			getForm().getEntiCollegatiList().getTable().add(appartCollegEntiRowBean);
		    }

		    loadDettaglioCollegamento(idCollegEntiConvenz);
		    getMessageBox()
			    .addInfo("Associazione dell'ente al collegamento salvata con successo");
		    getMessageBox().setViewMode(ViewMode.plain);
		    goBack();
		} else {
		    forwardToPublisher(Application.Publisher.DETTAGLIO_APPARTENENZA_COLLEGAMENTO);
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
		forwardToPublisher(Application.Publisher.DETTAGLIO_APPARTENENZA_COLLEGAMENTO);
	    }
	}
    }

    private void saveCollegamentoEnteAppart() throws EMFError {
	if (getForm().getCollegamentoEnteAppartDetail().postAndValidate(getRequest(),
		getMessageBox())) {
	    // CONTROLLI DI COERENZA NELLA MASCHERA
	    Date dtIniAppart = getForm().getCollegamentoEnteAppartDetail().getDt_ini_val().parse();
	    Date dtFinAppart = getForm().getCollegamentoEnteAppartDetail().getDt_fin_val().parse();

	    if (dtFinAppart.before(dtIniAppart)) {
		getMessageBox().addError(
			"La data di inizio appartenenza non pu\u00F2 essere successiva alla data di fine appartenenza");
	    }

	    try {
		if (!getMessageBox().hasError()) {
		    // Salva l'associazione al collegamento
		    BigDecimal idCollegEntiConvenz = getForm().getCollegamentoEnteAppartDetail()
			    .getId_colleg_enti_convenz().parse();
		    OrgCollegEntiConvenzRowBean collegEntiConvenz = entiConvenzionatiEjb
			    .getOrgCollegEntiConvenzRowBean(idCollegEntiConvenz);
		    BigDecimal idEnteConvenzCapofila = collegEntiConvenz.getIdEnteConvenzCapofila();
		    String nmColleg = collegEntiConvenz.getNmColleg();
		    Date dtIniVal = collegEntiConvenz.getDtIniVal();
		    Date dtFinVal = collegEntiConvenz.getDtFinVal();
		    BigDecimal idEnteConvenzAppart = getForm().getEnteConvenzionatoDetail()
			    .getId_ente_siam().parse();

		    // Se è stato indicato un ente capofila, richiamo il controllo inclusione dato
		    // figlio
		    // passando come entità figlio = collegamento e entità padre = ente capofila.
		    if (idEnteConvenzCapofila != null) {
			OrgEnteSiamRowBean enteCapofila = entiConvenzionatiEjb
				.getOrgEnteConvenzRowBean(idEnteConvenzCapofila);
			entiConvenzionatiEjb.checkInclusioneFiglio(enteCapofila.getDtIniVal(),
				enteCapofila.getDtCessazione(), dtIniVal, dtFinVal,
				enteCapofila.getNmEnteSiam(), nmColleg);
		    }

		    // Creo una mappa con gli intervalli di appartenza del nuovo ente al
		    // collegamento
		    HashMap<String, Date[]> mappaColleg = new HashMap<>();
		    Date[] dtCurrArr = new Date[2];
		    dtCurrArr[0] = dtIniAppart;
		    dtCurrArr[1] = dtFinAppart;
		    mappaColleg.put(entiConvenzionatiEjb
			    .getOrgEnteConvenzRowBean(idEnteConvenzAppart).getNmEnteSiam(),
			    dtCurrArr);
		    // Richiamo il controllo inclusione dato padre passando come entità padre =
		    // collegamento
		    // e come entità figlio gli intervalli di appartenza al collegamento definite su
		    // ciascun ente
		    // appartenente al collegamento
		    List<OrgAppartCollegEnti> appartCollegEntiList = entiConvenzionatiEjb
			    .getOrgAppartCollegEntiList(idCollegEntiConvenz);
		    for (OrgAppartCollegEnti colleg : appartCollegEntiList) {
			// Se il nome dell'ente è già presente nella mappa (vuol dire che sono in
			// modifica di
			// un'appartenenza), escludo dalla mappa i vecchi valori recuperati dal DB e
			// considero gli
			// intervalli di
			// appartenza appena inseriti.
			if (!mappaColleg.containsKey(colleg.getOrgEnteSiam().getNmEnteSiam())) {
			    Date[] dtArr = new Date[2];
			    dtArr[0] = colleg.getDtIniVal();
			    dtArr[1] = colleg.getDtFinVal();
			    mappaColleg.put(colleg.getOrgEnteSiam().getNmEnteSiam(), dtArr);
			}
		    }
		    entiConvenzionatiEjb.checkInclusionePadreFigli(dtIniVal, dtFinVal, nmColleg,
			    mappaColleg);

		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    LogParam param = SpagoliteLogUtil.getLogParam(
			    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			    SpagoliteLogUtil.getPageName(this));
		    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		    if (getForm().getCollegamentiEnteAppartList().getStatus()
			    .equals(Status.insert)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
			Long idAppartCollegEnti = entiConvenzionatiEjb.insertOrgAppartCollegEnti(
				param, getUser().getIdUtente(), idCollegEntiConvenz,
				idEnteConvenzAppart, dtIniAppart, dtFinAppart);
			if (idAppartCollegEnti != null) {
			    getForm().getCollegamentoEnteAppartDetail().getId_appart_colleg_enti()
				    .setValue(idAppartCollegEnti.toString());
			}

			OrgAppartCollegEntiRowBean appartCollegEntiRowBean = new OrgAppartCollegEntiRowBean();
			getForm().getCollegamentoEnteAppartDetail()
				.copyToBean(appartCollegEntiRowBean);
			getForm().getCollegamentiEnteAppartList().getTable().last();
			getForm().getCollegamentiEnteAppartList().getTable()
				.add(appartCollegEntiRowBean);
		    } else if (getForm().getCollegamentiEnteAppartList().getStatus()
			    .equals(Status.update)) {
			param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
			BigDecimal idAppartCollegEnti = getForm().getCollegamentoEnteAppartDetail()
				.getId_appart_colleg_enti().parse();
			entiConvenzionatiEjb.updateAppartenenzaCollegamento(param,
				idAppartCollegEnti, idEnteConvenzAppart, dtIniAppart, dtFinAppart);
		    }

		    loadDettaglioEnteConvenzionato(idEnteConvenzAppart);
		    getMessageBox()
			    .addInfo("Associazione dell'ente al collegamento salvata con successo");
		    getMessageBox().setViewMode(ViewMode.plain);
		    goBack();
		} else {
		    forwardToPublisher(Application.Publisher.DETTAGLIO_APPARTENENZA_ENTE);
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
		forwardToPublisher(Application.Publisher.DETTAGLIO_APPARTENENZA_ENTE);
	    }
	}
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
	BigDecimal idAmbienteEnteConvenz = getForm().getEnteConvenzionatoDetail()
		.getId_ambiente_ente_convenz().parse();
	BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam().parse();
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
	    multipartRequest.add("XMLSIP", getXmlSip(idEntita, tipoEntita, versione, loginname,
		    registro, anno, numero, idAmbienteEnteConvenz, idEnteConvenz));
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
		    while ((bytesRead = is.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		    }
		} catch (IOException e) {
		    logger.error("Errore durante la scrittura del file temporaneo", e);
		    getMessageBox().addError("Errore durante il tentativo di download del file");
		    forwardToPublisher(getLastPublisher());
		    return;
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
	    String nmUserid, String registro, BigDecimal anno, String numero,
	    BigDecimal idAmbienteEnteConvenz, BigDecimal idEnteConvenz)
	    throws EMFError, ParerUserError {
	String nmEnteUnitaDocAccordo = null;
	String nmStrutUnitaDocAccordo = null;
	if (tipoEntita.equals("accordo")) {
	    OrgAccordoEnteRowBean accordoEnteRowBean = entiConvenzionatiEjb
		    .getOrgAccordoEnteRowBean(idEntita);
	    nmEnteUnitaDocAccordo = accordoEnteRowBean.getNmEnte();
	    nmStrutUnitaDocAccordo = accordoEnteRowBean.getNmStrut();
	} else if (tipoEntita.equals("modulo")) {
	    OrgModuloInfoAccordoRowBean accordoEnteRowBean = entiConvenzionatiEjb
		    .getOrgModuloInfoAccordoRowBean(idEntita);
	    nmEnteUnitaDocAccordo = accordoEnteRowBean.getNmEnte();
	    nmStrutUnitaDocAccordo = accordoEnteRowBean.getNmStrut();
	} else if (tipoEntita.equals("disciplinare")) {
	    OrgDiscipStrutRowBean accordoEnteRowBean = entiConvenzionatiEjb
		    .getOrgDiscipStrutRowBean(idEntita);
	    nmEnteUnitaDocAccordo = accordoEnteRowBean.getEnteDiscipStrut();
	    nmStrutUnitaDocAccordo = accordoEnteRowBean.getStrutturaDiscipStrut();
	} else if (tipoEntita.equals("gestione")) {
	    OrgGestioneAccordoRowBean accordoEnteRowBean = entiConvenzionatiEjb
		    .getOrgGestioneAccordoRowBean(idEntita);
	    nmEnteUnitaDocAccordo = accordoEnteRowBean.getEnteGestAccordo();
	    nmStrutUnitaDocAccordo = accordoEnteRowBean.getStrutturaGestAccordo();
	} else if (tipoEntita.equals("user")) {
	    // OrgEnteUserRifRowBean accordoEnteRowBean =
	    // entiConvenzionatiEjb.getOrgEnteUserRifRowBean(idEntita);
	    String[][] ambEnteStrut = entiConvenzionatiEjb
		    .getAmbEnteStrutByIamParamApplic(idAmbienteEnteConvenz, idEnteConvenz);
	    nmEnteUnitaDocAccordo = ambEnteStrut[1][1];
	    nmStrutUnitaDocAccordo = ambEnteStrut[2][1];
	}
	BigDecimal idOrganizApplic = amministrazioneUtentiHelper
		.getIdOrganizIamByParam(nmEnteUnitaDocAccordo, nmStrutUnitaDocAccordo);
	String xml = null;
	if (idOrganizApplic != null) {
	    Map<String, String> org = entiConvenzionatiEjb.getUsrOrganizIamMap(idOrganizApplic);
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
    public void scaricaCompFileUdAccordo() throws Throwable {
	String registro = getForm().getAccordoDetail().getCd_registro_repertorio().parse();
	BigDecimal anno = getForm().getAccordoDetail().getAa_repertorio().parse();
	String numero = getForm().getAccordoDetail().getCd_key_repertorio().parse();
	BigDecimal idAccordoEnte = getForm().getAccordoDetail().getId_accordo_ente().parse();
	scaricaCompFileUd(idAccordoEnte, "accordo", registro, anno, numero);
    }

    @Override
    public void scaricaCompFileUdModInfo() throws Throwable {
	String registro = getForm().getModuloInformazioniDetail().getCd_registro_modulo_info()
		.parse();
	BigDecimal anno = getForm().getModuloInformazioniDetail().getAa_modulo_info().parse();
	String numero = getForm().getModuloInformazioniDetail().getCd_key_modulo_info().parse();
	BigDecimal idAccordoEnte = getForm().getModuloInformazioniDetail()
		.getId_modulo_info_accordo().parse();
	scaricaCompFileUd(idAccordoEnte, "modulo", registro, anno, numero);
    }

    @Override
    public void scaricaCompFileUdGestAccordo() throws Throwable {
	String registro = getForm().getGestioneAccordoDetail().getCd_registro_gest_accordo()
		.parse();
	BigDecimal anno = getForm().getGestioneAccordoDetail().getAa_gest_accordo().parse();
	String numero = getForm().getGestioneAccordoDetail().getCd_key_gest_accordo().parse();
	BigDecimal idGestAccordo = getForm().getGestioneAccordoDetail().getId_gest_accordo()
		.parse();
	scaricaCompFileUd(idGestAccordo, "gestione", registro, anno, numero);
    }

    @Override
    public void scaricaCompFileUdEnteUserRif() throws Throwable {
	// Recupero i parametri
	ParamsDocumentoReferente params = loadParametriReferenteEnte();
	// Recupero l'idEnteUser
	BigDecimal idEnteUser = getForm().getReferenteEnteDetail().getId_ente_user_rif().parse();
	scaricaCompFileUd(idEnteUser, "user", params.getRegistro(), params.getAnno(),
		params.getNumero());
    }

    // Metodo privato per caricare i parametri
    private ParamsDocumentoReferente loadParametriReferenteEnte() throws Throwable {
	// Recupero i parametri dal form
	String registro = getForm().getReferenteEnteDetail().getCd_registro_ente_user_rif().parse();
	BigDecimal anno = getForm().getReferenteEnteDetail().getAa_ente_user_rif().parse();
	String numero = getForm().getReferenteEnteDetail().getCd_key_ente_user_rif().parse();

	// Se i parametri dal form sono nulli, uso i valori salvati
	if (registro == null && anno == null && numero == null) {
	    OrgEnteUserRifRowBean row = (OrgEnteUserRifRowBean) getForm().getReferentiEnteList()
		    .getTable().getCurrentRow();
	    BigDecimal idReferente = row.getIdEnteUserRif();
	    OrgEnteUserRif rec = entiConvenzionatiHelper.findById(OrgEnteUserRif.class,
		    idReferente);
	    registro = rec.getCdRegistroEnteUserRif();
	    anno = rec.getAaEnteUserRif();
	    numero = rec.getCdKeyEnteUserRif();
	}

	return new ParamsDocumentoReferente(registro, anno, numero);
    }

    // Classe per incapsulare i parametri
    private static class ParamsDocumentoReferente {

	private final String registro;
	private final BigDecimal anno;
	private final String numero;

	public ParamsDocumentoReferente(String registro, BigDecimal anno, String numero) {
	    this.registro = registro;
	    this.anno = anno;
	    this.numero = numero;
	}

	public String getRegistro() {
	    return registro;
	}

	public BigDecimal getAnno() {
	    return anno;
	}

	public String getNumero() {
	    return numero;
	}
    }

    private void loadDettaglioDisciplinareTecnico(BigDecimal idDiscipStrut)
	    throws ParerUserError, EMFError {
	// Carico il dettaglio disciplinare tecnico
	OrgDiscipStrutRowBean detailRow = entiConvenzionatiEjb
		.getOrgDiscipStrutRowBean(idDiscipStrut);

	// Carico la combo "Registro", va messa qui e non in initAccordoDetail perchÃ¨
	// dipende dal valore dei parametri di gestione
	String nmEnte = detailRow.getEnteDiscipStrut();
	String nmStrut = detailRow.getStrutturaDiscipStrut();
	BigDecimal idStrut = entiConvenzionatiEjb.getIdStrut(nmEnte, nmStrut);
	getForm().getDisciplinareTecnicoDetail().getCd_registro_discip_strut()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getRegistriStrutturaTableBean(idStrut), "cd_registro",
			"cd_registro"));

	getForm().getDisciplinareTecnicoDetail().copyFromBean(detailRow);

	getForm().getDisciplinareTecnicoDetail().setViewMode();
	getForm().getDisciplinareTecnicoDetail().setStatus(Status.view);
	getForm().getDisciplinariTecniciList().setStatus(Status.view);
    }

    private void saveDisciplinareTecnico() throws EMFError {
	if (getForm().getDisciplinareTecnicoDetail().validate(getMessageBox())) {
	    try {
		// Controlli obbligatorietà personalizzati per l'inserimento manuale
		if (getForm().getDisciplinareTecnicoDetail().getFl_inserito_manualmente().parse()
			.equals("1")
			&& (getForm().getDisciplinareTecnicoDetail().getEnte_discip_strut()
				.parse() == null
				|| getForm().getDisciplinareTecnicoDetail()
					.getStruttura_discip_strut()
					.parse() == null /*
							  * MEV #19631 ||
							  * getForm().getDisciplinareTecnicoDetail()
							  * . getCd_registro_discip_strut().parse()
							  * == null ||
							  * getForm().getDisciplinareTecnicoDetail()
							  * .getAa_discip_strut( ).parse() == null
							  * ||
							  * getForm().getDisciplinareTecnicoDetail()
							  * . getCd_key_discip_strut().parse() ==
							  * null
							  */)) {
		    getMessageBox().addError(
			    "E\u0027 obbligatorio valorizzare i campi di registrazione del protocollo<br>");
		}
		if (getForm().getDisciplinareTecnicoDetail().getId_organiz_iam().parse() == null) {
		    getMessageBox().addError(
			    "E\u0027 obbligatorio scegliere l\u0027organizzazione cui il disciplinare si riferisce<br>");
		}
		//
		byte[] blDiscipStrut = getForm().getDisciplinareTecnicoDetail().getBl_discip_strut()
			.getFileBytes();
		if (blDiscipStrut != null) {
		    InputStream targetStream = new ByteArrayInputStream(blDiscipStrut);
		    String mimetype = entiConvenzionatiEjb.getFileTypeByTika(targetStream);
		    if (!mimetype.equals("application/pdf")) {
			getMessageBox()
				.addError("E' possibile inserire solo file in formato pdf<br>");
		    }
		}

		if (!getMessageBox().hasError()) {
		    BigDecimal idDiscipStrut = getForm().getDisciplinareTecnicoDetail()
			    .getId_discip_strut().parse();
		    BigDecimal aaDiscipStrut = getForm().getDisciplinareTecnicoDetail()
			    .getAa_discip_strut().parse();
		    String cdKeyDiscipStrut = getForm().getDisciplinareTecnicoDetail()
			    .getCd_key_discip_strut().parse();
		    String cdRegistroDiscipStrut = getForm().getDisciplinareTecnicoDetail()
			    .getCd_registro_discip_strut().parse();
		    String dsDiscipStrut = getForm().getDisciplinareTecnicoDetail()
			    .getDs_discip_strut().parse();
		    String dsNoteDiscipStrut = getForm().getDisciplinareTecnicoDetail()
			    .getDs_note_discip_strut().parse();
		    Date dtDiscipStrut = getForm().getDisciplinareTecnicoDetail()
			    .getDt_discip_strut().parse();
		    Calendar c = Calendar.getInstance();
		    Calendar c1 = Calendar.getInstance();
		    c.setTime(dtDiscipStrut);
		    c.set(Calendar.HOUR_OF_DAY, c1.get(Calendar.HOUR_OF_DAY));
		    c.set(Calendar.MINUTE, c1.get(Calendar.MINUTE));
		    c.set(Calendar.SECOND, c1.get(Calendar.SECOND));
		    c.set(Calendar.MILLISECOND, c1.get(Calendar.MILLISECOND));
		    dtDiscipStrut = c.getTime();
		    String flInseritoManualmente = getForm().getDisciplinareTecnicoDetail()
			    .getFl_inserito_manualmente().parse();
		    BigDecimal idAccordoEnte = getForm().getAccordoDetail().getId_accordo_ente()
			    .parse();
		    BigDecimal idOrganizIam = getForm().getDisciplinareTecnicoDetail()
			    .getId_organiz_iam().parse();
		    String nmEnte = getForm().getDisciplinareTecnicoDetail().getEnte_discip_strut()
			    .parse();
		    String nmStrut = getForm().getDisciplinareTecnicoDetail()
			    .getStruttura_discip_strut().parse();
		    BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail()
			    .getId_ente_siam().parse();

		    // Se l'inserimento è manuale eseguo altri controlli, altrimenti posso procedere
		    // direttamente col
		    // salvataggio
		    if (getForm().getDisciplinareTecnicoDetail().getFl_inserito_manualmente()
			    .parse().equals("1")) {
			checkAndSaveDisciplinareTecnico(idDiscipStrut, aaDiscipStrut, blDiscipStrut,
				cdKeyDiscipStrut, cdRegistroDiscipStrut, dsDiscipStrut,
				dsNoteDiscipStrut, dtDiscipStrut, flInseritoManualmente,
				idAccordoEnte, idOrganizIam, null, nmEnte, nmStrut, idEnteConvenz);
		    } else {
			eseguiSalvataggioDisciplinare(idDiscipStrut, aaDiscipStrut, blDiscipStrut,
				cdKeyDiscipStrut, cdRegistroDiscipStrut, dsDiscipStrut,
				dsNoteDiscipStrut, dtDiscipStrut, flInseritoManualmente,
				idAccordoEnte, idOrganizIam, null, nmEnte, nmStrut, idEnteConvenz);
		    }
		}
	    } catch (ParerUserError ex) {
		getMessageBox().addError(ex.getDescription());
	    }
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_DISCIPLINARE_TECNICO);
    }

    public void checkAndSaveDisciplinareTecnico(BigDecimal idDiscipStrut, BigDecimal aaDiscipStrut,
	    byte[] blDiscipStrut, String cdKeyDiscipStrut, String cdRegistroDiscipStrut,
	    String dsDiscipStrut, String dsNoteDiscipStrut, Date dtDiscipStrut,
	    String flInseritoManualmente, BigDecimal idAccordoEnte, BigDecimal idOrganizIam,
	    String nmAmbiente, String nmEnte, String nmStrut, BigDecimal idEnteConvenz)
	    throws ParerUserError, EMFError {
	String customMessage = "";
	BigDecimal idAmbienteEnteConvenz = getForm().getEnteConvenzionatoDetail()
		.getId_ambiente_ente_convenz().parse();
	String nmEnteUnitaDocAccordo = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.NM_ENTE_UNITA_DOC_ACCORDO.name(),
		idAmbienteEnteConvenz, null, Constants.TipoIamVGetValAppart.AMBIENTEENTECONVENZ);
	String nmStrutUnitaDocAccordo = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.NM_STRUT_UNITA_DOC_ACCORDO.name(),
		idAmbienteEnteConvenz, null, Constants.TipoIamVGetValAppart.AMBIENTEENTECONVENZ);
	BigDecimal idOrganizApplic = amministrazioneUtentiHelper
		.getIdOrganizIamByParam(nmEnteUnitaDocAccordo, nmStrutUnitaDocAccordo);
	/*
	 * Controlla che la chiave indicata in cd_registro_discip_strut - aa_discip_strut -
	 * cd_key_discip_strut per la struttura definita nella tabella IAM_PARAM_APPLIC sia
	 * effettivamente presente in Sacer
	 */
	if (!entiConvenzionatiEjb.existsUDInSacer(idOrganizApplic, cdRegistroDiscipStrut,
		aaDiscipStrut, cdKeyDiscipStrut)) {
	    customMessage = "La registrazione di protocollo indicata corrisponde ad una unit\u00E0 documentaria non presente sul sistema: si desidera proseguire?";
	    getRequest().setAttribute("customMessageSalvataggioDisciplinare", customMessage);
	    getRequest().setAttribute("customBoxSalvataggioDisciplinare", true);
	} else if (getForm().getDisciplinareTecnicoDetail().getBl_discip_strut().parse() != null) {
	    customMessage = "La registrazione di protocollo corrisponde ad una unit\u00E0 documentaria presente sul sistema ed \u00E8 stato eseguito l\u0027upload del disciplinare: si desidera proseguire?";
	    getRequest().setAttribute("customMessageSalvataggioDisciplinare", customMessage);
	    getRequest().setAttribute("customBoxSalvataggioDisciplinare", true);
	} else {
	    eseguiSalvataggioDisciplinare(idDiscipStrut, aaDiscipStrut, blDiscipStrut,
		    cdKeyDiscipStrut, cdRegistroDiscipStrut, dsDiscipStrut, dsNoteDiscipStrut,
		    dtDiscipStrut, flInseritoManualmente, idAccordoEnte, idOrganizIam, nmAmbiente,
		    nmEnte, nmStrut, idEnteConvenz);
	}
    }

    public void confermaSalvataggioDisciplinare() throws ParerUserError, EMFError {
	BigDecimal idDiscipStrut = getForm().getDisciplinareTecnicoDetail().getId_discip_strut()
		.parse();
	BigDecimal aaDiscipStrut = getForm().getDisciplinareTecnicoDetail().getAa_discip_strut()
		.parse();
	byte[] blDiscipStrut = getForm().getDisciplinareTecnicoDetail().getBl_discip_strut()
		.getFileBytes();
	String cdKeyDiscipStrut = getForm().getDisciplinareTecnicoDetail().getCd_key_discip_strut()
		.parse();
	String cdRegistroDiscipStrut = getForm().getDisciplinareTecnicoDetail()
		.getCd_registro_discip_strut().parse();
	String dsDiscipStrut = getForm().getDisciplinareTecnicoDetail().getDs_discip_strut()
		.parse();
	String dsNoteDiscipStrut = getForm().getDisciplinareTecnicoDetail()
		.getDs_note_discip_strut().parse();
	Date dtDiscipStrut = getForm().getDisciplinareTecnicoDetail().getDt_discip_strut().parse();
	Calendar c = Calendar.getInstance();
	Calendar c1 = Calendar.getInstance();
	c.setTime(dtDiscipStrut);
	c.set(Calendar.HOUR_OF_DAY, c1.get(Calendar.HOUR_OF_DAY));
	c.set(Calendar.MINUTE, c1.get(Calendar.MINUTE));
	c.set(Calendar.SECOND, c1.get(Calendar.SECOND));
	c.set(Calendar.MILLISECOND, c1.get(Calendar.MILLISECOND));
	dtDiscipStrut = c.getTime();
	String flInseritoManualmente = getForm().getDisciplinareTecnicoDetail()
		.getFl_inserito_manualmente().parse();
	BigDecimal idAccordoEnte = getForm().getAccordoDetail().getId_accordo_ente().parse();
	BigDecimal idOrganizIam = getForm().getDisciplinareTecnicoDetail().getId_organiz_iam()
		.parse();
	String nmEnte = getForm().getDisciplinareTecnicoDetail().getEnte_discip_strut().parse();
	String nmStrut = getForm().getDisciplinareTecnicoDetail().getStruttura_discip_strut()
		.parse();
	BigDecimal idEnteConvenz = getForm().getEnteConvenzionatoDetail().getId_ente_siam().parse();
	eseguiSalvataggioDisciplinare(idDiscipStrut, aaDiscipStrut, blDiscipStrut, cdKeyDiscipStrut,
		cdRegistroDiscipStrut, dsDiscipStrut, dsNoteDiscipStrut, dtDiscipStrut,
		flInseritoManualmente, idAccordoEnte, idOrganizIam, null, nmEnte, nmStrut,
		idEnteConvenz);
    }

    @Override
    public void scaricaCompFileUdDiscip() throws Throwable {
	String registro = getForm().getDisciplinareTecnicoDetail().getCd_registro_discip_strut()
		.parse();
	BigDecimal anno = getForm().getDisciplinareTecnicoDetail().getAa_discip_strut().parse();
	String numero = getForm().getDisciplinareTecnicoDetail().getCd_key_discip_strut().parse();
	BigDecimal idDiscipStrut = getForm().getDisciplinareTecnicoDetail().getId_discip_strut()
		.parse();
	scaricaCompFileUd(idDiscipStrut, "disciplinare", registro, anno, numero);
    }

    @Override
    public void downloadFileDiscip() throws EMFError {
	BigDecimal idDiscipStrut = getForm().getDisciplinareTecnicoDetail().getId_discip_strut()
		.parse();
	downloadFileCommonDiscipStrut(idDiscipStrut);
    }

    private void downloadFileCommonDiscipStrut(BigDecimal idDiscipStrut) throws EMFError {
	File tmpFile = null;
	FileOutputStream out = null;
	try {
	    Object[] rec = entiConvenzionatiEjb.getFileDisciplinareTecnico(idDiscipStrut);
	    // Controllo per scrupolo
	    if (rec[1] == null) {
		getMessageBox().addError("Non c'\u00E8 alcun file da scaricare<br/>");
	    } else {
		String nmFileDiscipStrut = (String) rec[0];
		byte[] blVarAccordo = (byte[]) rec[1];
		tmpFile = new File(System.getProperty("java.io.tmpdir"), nmFileDiscipStrut);
		out = new FileOutputStream(tmpFile);
		IOUtils.write(blVarAccordo, out);

		getRequest().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_ACTION.name(),
			getControllerName());
		getSession().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_FILENAME.name(),
			tmpFile.getName());
		getSession().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_FILEPATH.name(),
			tmpFile.getPath());
		getSession().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_DELETEFILE.name(),
			Boolean.toString(true));
		getSession().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_CONTENTTYPE.name(),
			WebConstants.MIME_TYPE_GENERIC);
	    }
	} catch (Exception ex) {
	    logger.error("Errore in download " + ExceptionUtils.getRootCauseMessage(ex), ex);
	    getMessageBox().addError("Errore inatteso nella preparazione del download<br/>");
	} finally {
	    IOUtils.closeQuietly(out);
	}

	if (getMessageBox().hasError()) {
	    forwardToPublisher(getLastPublisher());
	} else {
	    forwardToPublisher(Application.Publisher.DOWNLOAD_PAGE);
	}

    }

    public void eseguiSalvataggioDisciplinare(BigDecimal idDiscipStrut, BigDecimal aaDiscipStrut,
	    byte[] blDiscipStrut, String cdKeyDiscipStrut, String cdRegistroDiscipStrut,
	    String dsDiscipStrut, String dsNoteDiscipStrut, Date dtDiscipStrut,
	    String flInseritoManualmente, BigDecimal idAccordoEnte, BigDecimal idOrganizIam,
	    String nmAmbiente, String nmEnte, String nmStrut, BigDecimal idEnteConvenz)
	    throws ParerUserError, EMFError {

	try {
	    /*
	     * Codice aggiuntivo per il logging...
	     */
	    LogParam param = SpagoliteLogUtil.getLogParam(
		    paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
		    SpagoliteLogUtil.getPageName(this));
	    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());

	    if (getForm().getDisciplinariTecniciList().getStatus().equals(Status.insert)) {
		param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
		Long idDiscipStrutNew = entiConvenzionatiEjb.saveDisciplinareTecnico(param,
			aaDiscipStrut, blDiscipStrut, cdKeyDiscipStrut, cdRegistroDiscipStrut,
			dsDiscipStrut, dsNoteDiscipStrut, dtDiscipStrut, flInseritoManualmente,
			idAccordoEnte, idOrganizIam, nmAmbiente, nmEnte, nmStrut, idEnteConvenz);
		if (idDiscipStrutNew != null) {
		    getForm().getDisciplinareTecnicoDetail().getId_discip_strut()
			    .setValue(idDiscipStrutNew.toString());
		}
		OrgDiscipStrutRowBean row = new OrgDiscipStrutRowBean();
		getForm().getDisciplinareTecnicoDetail().copyToBean(row);
		getForm().getDisciplinariTecniciList().getTable().last();
		getForm().getDisciplinariTecniciList().getTable().add(row);
	    } else if (getForm().getDisciplinariTecniciList().getStatus().equals(Status.update)) {
		param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
		entiConvenzionatiEjb.saveDisciplinareTecnico(param, idDiscipStrut, aaDiscipStrut,
			blDiscipStrut, cdKeyDiscipStrut, cdRegistroDiscipStrut, dsDiscipStrut,
			dsNoteDiscipStrut, dtDiscipStrut, flInseritoManualmente, idAccordoEnte,
			idOrganizIam, nmAmbiente, nmEnte, nmStrut, idEnteConvenz);
	    }

	    getForm().getDisciplinareTecnicoDetail().setViewMode();
	    getForm().getDisciplinariTecniciList().setStatus(Status.view);
	    getForm().getDisciplinareTecnicoDetail().setStatus(Status.view);
	    postLoad();
	    getMessageBox().addInfo("Disciplinare tecnico salvato con successo");
	    getMessageBox().setViewMode(ViewMode.plain);

	} catch (ParerUserError e) {
	    getMessageBox().addError(e.getDescription());
	}

	forwardToPublisher(Application.Publisher.DETTAGLIO_DISCIPLINARE_TECNICO);
    }

    @Override
    public void updateDisciplinariTecniciList() throws EMFError {
	if (getForm().getDisciplinareTecnicoDetail().getFl_inserito_manualmente().parse()
		.equals("0")) {
	    getForm().getDisciplinareTecnicoDetail().getDs_discip_strut().setEditMode();
	    getForm().getDisciplinareTecnicoDetail().getDs_note_discip_strut().setEditMode();
	} else {
	    getForm().getDisciplinareTecnicoDetail().getCd_registro_discip_strut().setEditMode();
	    getForm().getDisciplinareTecnicoDetail().getAa_discip_strut().setEditMode();
	    getForm().getDisciplinareTecnicoDetail().getCd_key_discip_strut().setEditMode();
	    getForm().getDisciplinareTecnicoDetail().getDt_discip_strut().setEditMode();
	    getForm().getDisciplinareTecnicoDetail().getId_organiz_iam().setEditMode();
	    getForm().getDisciplinareTecnicoDetail().getDs_discip_strut().setEditMode();
	    getForm().getDisciplinareTecnicoDetail().getDs_note_discip_strut().setEditMode();
	    getForm().getDisciplinareTecnicoDetail().getBl_discip_strut().setEditMode();
	}
	checkModificaRegistroDisciplinareTecnico();
	getForm().getDisciplinariTecniciList().setStatus(Status.update);
	getForm().getDisciplinareTecnicoDetail().setStatus(Status.update);
    }

    @Override
    public void deleteDisciplinareTecnicoDetail() throws EMFError {
	deleteDisciplinariTecniciList();
    }

    @Override
    public void deleteDisciplinariTecniciList() throws EMFError {
	OrgDiscipStrutRowBean currentRow = (OrgDiscipStrutRowBean) getForm()
		.getDisciplinariTecniciList().getTable().getCurrentRow();
	BigDecimal idDiscipStrut = currentRow.getIdDiscipStrut();
	int riga = getForm().getDisciplinariTecniciList().getTable().getCurrentRowIndex();

	if (currentRow.getFlInseritoManualmente().equals("0")) {
	    getMessageBox().addError(
		    "Il disciplinare tecnico non pu\u00F2 essere eliminato in quanto non \u00E8 stato inserito manualmente");
	}

	if (!getMessageBox().hasError() && idDiscipStrut != null) {
	    try {
		/*
		 * Codice aggiuntivo per il logging...
		 */
		LogParam param = SpagoliteLogUtil.getLogParam(
			paramHelper.getParamApplicApplicationName(), getUser().getUsername(),
			SpagoliteLogUtil.getPageName(this));
		param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
		if (param.getNomePagina()
			.equalsIgnoreCase(Application.Publisher.DETTAGLIO_DISCIPLINARE_TECNICO)) {
		    param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
		} else {
		    param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
			    getForm().getDisciplinariTecniciList()));
		}
		entiConvenzionatiEjb.deleteOrgDiscipStrut(param, idDiscipStrut);
		getForm().getDisciplinariTecniciList().getTable().remove(riga);
		getMessageBox().addInfo("Disciplinare tecnico eliminato con successo");
		getMessageBox().setViewMode(ViewMode.plain);
	    } catch (ParerUserError ex) {
		getMessageBox().addError("Il disciplinare tecnico non pu\u00F2 essere eliminato: "
			+ ex.getDescription());
	    }
	}
	if (!getMessageBox().hasError() && getLastPublisher()
		.equals(Application.Publisher.DETTAGLIO_DISCIPLINARE_TECNICO)) {
	    goBack();
	} else {
	    forwardToPublisher(getLastPublisher());
	}
    }

    @Override
    public void cancellaFileDiscip() throws EMFError {
	try {
	    BigDecimal idDiscipStrut = getForm().getDisciplinareTecnicoDetail().getId_discip_strut()
		    .parse();
	    if (idDiscipStrut != null) {
		// Sono in update: Controlla se il file ÃƒÆ’Ã‚Â¨ su DB ed eventualmente cancellalo
		if (entiConvenzionatiEjb.existsFileDiscip(idDiscipStrut)) {
		    entiConvenzionatiEjb.deleteFileDiscip(idDiscipStrut);
		}
		getForm().getDisciplinareTecnicoDetail().getBl_discip_strut().reset();
	    } else {
		// Sono in insert: eventualmente pulisci il campo file
		getForm().getDisciplinareTecnicoDetail().getBl_discip_strut().reset();
	    }
	    getMessageBox().addInfo("File eliminato con successo");
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_DISCIPLINARE_TECNICO);
    }

    /**
     * Carica la lista dei parametri in base ai filtri scelti
     *
     * @throws EMFError errore generico
     */
    @Override
    public void load_config_list() throws EMFError {
	// Recupero i valori dai filtri
	getForm().getConfiguration().post(getRequest());
	String tiParamApplic = getForm().getConfiguration().getTi_param_applic_combo().parse();
	String tiGestioneParam = getForm().getConfiguration().getTi_gestione_param_combo().parse();
	String flAppartApplic = getForm().getConfiguration().getFl_appart_applic_combo().parse();
	String flAppartAmbiente = getForm().getConfiguration().getFl_appart_ambiente_combo()
		.parse();
	String flApparteEnte = getForm().getConfiguration().getFl_apparte_ente_combo().parse();

	// Carico i valori delle combo della lista
	getForm().getConfigurationList().getTi_gestione_param()
		.setDecodeMap(ComboGetter.getMappaTiGestioneParam());
	getForm().getConfigurationList().getTi_valore_param_applic()
		.setDecodeMap(ComboGetter.getTiValoreParamApplicCombo());

	// Carico i valori della lista configurazioni
	IamParamApplicTableBean paramApplicTableBean = entiConvenzionatiEjb
		.getIamParamApplicTableBean(tiParamApplic, tiGestioneParam, flAppartApplic,
			flAppartAmbiente, flApparteEnte,
			getForm().getConfigurationList().isFilterValidRecords());

	paramApplicTableBean = obfuscatePasswordParamApplic(paramApplicTableBean);

	getForm().getConfigurationList().setTable(paramApplicTableBean);

	setConfigListReadOnly();

	// Se non ho trovato risultati nascondo il pulsante "Modifica"
	if (paramApplicTableBean.isEmpty()) {
	    getForm().getConfiguration().getEdit_config().setViewMode();
	}

	forwardToPublisher(Application.Publisher.REGISTRO_PARAMETRI);
    }

    private void setConfigListReadOnly() {
	getForm().getConfigurationList().getTable().setPageSize(300);
	getForm().getConfigurationList().getTable().first();

	// Rendo visibili i bottoni di aggiunta/salvataggio configurazione
	getForm().getConfiguration().getEdit_config().setEditMode();
	getForm().getConfiguration().getAdd_config().setViewMode();
	getForm().getConfiguration().getSave_config().setViewMode();

	// Rendo non modificabili i campi della lista
	getForm().getConfigurationList().getTi_param_applic().setViewMode();
	getForm().getConfigurationList().getTi_gestione_param().setViewMode();
	getForm().getConfigurationList().getNm_param_applic().setViewMode();
	getForm().getConfigurationList().getDs_param_applic().setViewMode();
	getForm().getConfigurationList().getTi_valore_param_applic().setViewMode();
	getForm().getConfigurationList().getDs_lista_valori_ammessi().setViewMode();
	getForm().getConfigurationList().getDs_valore_param_applic().setViewMode();
	getForm().getConfigurationList().getCd_versione_app_ini().setViewMode();
	getForm().getConfigurationList().getCd_versione_app_fine().setViewMode();
	getForm().getConfigurationList().getFl_appart_applic().setEditMode();
	getForm().getConfigurationList().getFl_appart_ambiente().setEditMode();
	getForm().getConfigurationList().getFl_apparte_ente().setEditMode();
	getForm().getConfigurationList().getFl_appart_applic().setReadonly(true);
	getForm().getConfigurationList().getFl_appart_ambiente().setReadonly(true);
	getForm().getConfigurationList().getFl_apparte_ente().setReadonly(true);
    }

    /**
     * Aggiunge un nuovo parametro
     *
     * @throws EMFError errore generico
     */
    @Override
    public void add_config() throws EMFError {
	getForm().getConfigurationList().getTable().last();
	getForm().getConfigurationList().getTable().add(new IamParamApplicRowBean());
	forwardToPublisher(Application.Publisher.REGISTRO_PARAMETRI);
    }

    /**
     * Esegue un controllo sui campi e inserisce i parametri nel database
     *
     * @throws EMFError errore generico
     */
    @Override
    public void save_config() throws EMFError {
	String idParamApplicName = getForm().getConfigurationList().getId_param_applic().getName();
	String tiParamApplicName = getForm().getConfigurationList().getTi_param_applic().getName();
	String tiGestioneParamName = getForm().getConfigurationList().getTi_gestione_param()
		.getName();
	String nmParamApplicName = getForm().getConfigurationList().getNm_param_applic().getName();
	String dsParamApplicName = getForm().getConfigurationList().getDs_param_applic().getName();
	String dsListaValoriAmmessiName = getForm().getConfigurationList()
		.getDs_lista_valori_ammessi().getName();
	String dsValoreParamApplicName = getForm().getConfigurationList()
		.getDs_valore_param_applic().getName();
	String flAppartApplicName = getForm().getConfigurationList().getFl_appart_applic()
		.getName();
	String tiValoreParamApplic = getForm().getConfigurationList().getTi_valore_param_applic()
		.getName();
	String cdVersioneAppIni = getForm().getConfigurationList().getCd_versione_app_ini()
		.getName();
	Set<Integer> completeRows = new HashSet<>();
	Set<String> nmParamApplicSet = new HashSet<>();
	// Tiro su i dati i request di tutti i record della lista
	getForm().getConfigurationList().post(getRequest());
	// Scorro tutte le righe della tabella per effettuare i controlli
	for (int i = 0; i < getForm().getConfigurationList().getTable().size(); i++) {
	    BaseRowInterface r = getForm().getConfigurationList().getTable().getRow(i);
	    BigDecimal idParamApplicValue = r.getBigDecimal(idParamApplicName);
	    String tiParamApplicValue = r.getString(tiParamApplicName);
	    String tiGestioneParamValue = r.getString(tiGestioneParamName);
	    String nmParamApplicValue = r.getString(nmParamApplicName);
	    String dsParamApplicValue = r.getString(dsParamApplicName);
	    String dsListaValoriAmmessiValue = r.getString(dsListaValoriAmmessiName);
	    String dsValoreParamApplicValue = r.getString(dsValoreParamApplicName);
	    String tiValoreParamApplicValue = r.getString(tiValoreParamApplic);
	    String flAppartApplicValue = r.getString(flAppartApplicName);
	    String cdVersioneAppIniValue = r.getString(cdVersioneAppIni);
	    if (StringUtils.isNotBlank(tiParamApplicValue)
		    && StringUtils.isNotBlank(tiGestioneParamValue)
		    && StringUtils.isNotBlank(nmParamApplicValue)
		    && StringUtils.isNotBlank(dsParamApplicValue)
		    && StringUtils.isNotBlank(tiValoreParamApplicValue)
		    && StringUtils.isNotBlank(cdVersioneAppIniValue)// &&
	    ) {
		if (StringUtils.isNotBlank(dsValoreParamApplicValue)) {
		    if (flAppartApplicValue.equals("1")) {
			completeRows.add(i);
		    } else {
			getMessageBox().addError(
				"Il valore del parametro pu\u00F2 essere indicato solo se il parametro ha il flag Applicazione alzato");
			getMessageBox().setViewMode(ViewMode.plain);
		    }
		} else {
		    completeRows.add(i);
		}
	    } else {
		getMessageBox().addError(
			"Almeno un parametro non ha tutti i campi obbligatori valorizzati");
		getMessageBox().setViewMode(ViewMode.plain);
	    }

	    nmParamApplicSet.add(nmParamApplicValue);

	    // Controllo che il parametro non esista già su DB
	    if (entiConvenzionatiEjb.checkParamApplic(nmParamApplicValue, idParamApplicValue)) {
		getMessageBox().addError("Attenzione: parametro " + nmParamApplicValue
			+ " gi\u00E0 presente nel sistema");
	    }

	    // Controllo valori possibili su ente
	    if (dsListaValoriAmmessiValue != null && !dsListaValoriAmmessiValue.equals("")) {
		if (dsValoreParamApplicValue != null && !dsValoreParamApplicValue.equals("")) {
		    if (!inValoriPossibili(dsValoreParamApplicValue, dsListaValoriAmmessiValue)) {
			getMessageBox().addError(
				"Il valore del parametro non \u00E8 compreso tra i valori ammessi sul parametro");
		    }
		}
	    }
	}

	// Controllo che il nome-parametro non sia ripetuto per motivi di univocità
	if (nmParamApplicSet.size() != getForm().getConfigurationList().getTable().size()) {
	    getMessageBox().addError(
		    "Attenzione: esistono uno o pi\u0027 parametri con lo stesso nome parametro");
	}

	if (!getMessageBox().hasError()) {
	    for (Integer rowIndex : completeRows) {
		IamParamApplicRowBean row = ((IamParamApplicTableBean) getForm()
			.getConfigurationList().getTable()).getRow(rowIndex);

		// // MEV 26588 - non sovrascrivere con il valore offuscato il valore originale.
		// if (row.getTiValoreParamApplic()
		// .equals(it.eng.saceriam.web.util.Constants.ComboValueParamentersType.PASSWORD.name())
		// && row.getString("ds_valore_param_applic")
		// .equals(it.eng.saceriam.web.util.Constants.OBFUSCATED_STRING)) {
		// continue;
		// }
		if (!entiConvenzionatiEjb.saveConfiguration(row)) {
		    getMessageBox().addError("Errore durante il salvataggio della configurazione");
		}
	    }
	    if (!getMessageBox().hasError()) {
		getMessageBox().addInfo("Configurazione salvata con successo");
		getMessageBox().setViewMode(ViewMode.plain);

		initConfigurationCombo();

		IamParamApplicTableBean paramApplicTableBean = (IamParamApplicTableBean) getForm()
			.getConfigurationList().getTable();
		paramApplicTableBean = obfuscatePasswordParamApplic(paramApplicTableBean);
		getForm().getConfigurationList().setTable(paramApplicTableBean);
		setConfigListReadOnly();

	    }
	}

	forwardToPublisher(Application.Publisher.REGISTRO_PARAMETRI);
    }

    /**
     * Inizializza la combo dei tipi parametro
     *
     * @throws EMFError errore generico
     */
    private void initConfigurationCombo() throws EMFError {
	BaseTable tiParamApplic = entiConvenzionatiEjb.getTiParamApplicBaseTable();
	DecodeMap mappaTiParamApplic = DecodeMap.Factory.newInstance(tiParamApplic,
		IamParamApplicTableDescriptor.COL_TI_PARAM_APPLIC,
		IamParamApplicTableDescriptor.COL_TI_PARAM_APPLIC);
	getForm().getConfiguration().getTi_param_applic_combo().setDecodeMap(mappaTiParamApplic);

	getForm().getConfiguration().getTi_gestione_param_combo()
		.setDecodeMap(ComboGetter.getMappaTiGestioneParam());

	getForm().getConfiguration().getFl_appart_applic_combo()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	getForm().getConfiguration().getFl_appart_ambiente_combo()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	getForm().getConfiguration().getFl_apparte_ente_combo()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
    }

    /**
     * Carica la pagina di lista parametri SACER IAM
     *
     * @throws EMFError errore generico
     */
    @Secure(action = "Menu.AmministrazioneSistema.ListaConfigurazioni")
    public void loadListaConfigurazioni() throws EMFError {
	getUser().getMenu().reset();
	getUser().getMenu().select("Menu.AmministrazioneSistema.ListaConfigurazioni");
	getForm().getConfiguration().clear();
	getForm().getConfigurationList().clear();

	initConfigurationCombo();

	getForm().getConfiguration().getTi_param_applic_combo().setEditMode();
	getForm().getConfiguration().getTi_gestione_param_combo().setEditMode();
	getForm().getConfiguration().getFl_appart_applic_combo().setEditMode();
	getForm().getConfiguration().getFl_appart_ambiente_combo().setEditMode();
	getForm().getConfiguration().getFl_apparte_ente_combo().setEditMode();

	// Azzero il Mostra/Nascondi
	getForm().getConfigurationList().setFilterValidRecords(Boolean.TRUE);

	getForm().getConfiguration().getLoad_config_list().setEditMode();

	getForm().getConfiguration().getEdit_config().setViewMode();
	getForm().getConfiguration().getAdd_config().setViewMode();
	getForm().getConfiguration().getSave_config().setViewMode();

	// Carico la lista dei configurazioni
	forwardToPublisher(Application.Publisher.REGISTRO_PARAMETRI);
    }

    /**
     * MEV 25594 - Imposta la modalità di modifica della lista parametri
     *
     * @throws EMFError errore generico
     */
    @Override
    public void edit_config() throws EMFError {
	// Recupero i valori dai filtri ma NON riparsiamo la request!
	String tiParamApplic = getForm().getConfiguration().getTi_param_applic_combo().parse();
	String tiGestioneParam = getForm().getConfiguration().getTi_gestione_param_combo().parse();
	String flAppartApplic = getForm().getConfiguration().getFl_appart_applic_combo().parse();
	String flAppartAmbiente = getForm().getConfiguration().getFl_appart_ambiente_combo()
		.parse();
	String flApparteEnte = getForm().getConfiguration().getFl_apparte_ente_combo().parse();

	// Carico i valori delle combo della lista
	getForm().getConfigurationList().getTi_gestione_param()
		.setDecodeMap(ComboGetter.getMappaTiGestioneParam());
	getForm().getConfigurationList().getTi_valore_param_applic()
		.setDecodeMap(ComboGetter.getTiValoreParamApplicCombo());

	// Carico i valori della lista configurazioni
	IamParamApplicTableBean paramApplicTableBean = entiConvenzionatiEjb
		.getIamParamApplicTableBean(tiParamApplic, tiGestioneParam, flAppartApplic,
			flAppartAmbiente, flApparteEnte);

	getForm().getConfigurationList().setTable(paramApplicTableBean);
	getForm().getConfigurationList().getTable().setPageSize(300);
	getForm().getConfigurationList().getTable().first();

	// Rendo visibili i bottoni di aggiunta/salvataggio configurazione
	getForm().getConfiguration().getEdit_config().setViewMode();
	getForm().getConfiguration().getAdd_config().setEditMode();
	getForm().getConfiguration().getSave_config().setEditMode();

	// Rendo editabili i campi della lista
	// Rendo editabili i campi della lista
	getForm().getConfigurationList().getTi_param_applic().setEditMode();
	getForm().getConfigurationList().getTi_gestione_param().setEditMode();
	getForm().getConfigurationList().getCd_versione_app_ini().setEditMode();
	getForm().getConfigurationList().getCd_versione_app_fine().setEditMode();
	getForm().getConfigurationList().getNm_param_applic().setEditMode();
	getForm().getConfigurationList().getDs_param_applic().setEditMode();
	getForm().getConfigurationList().getTi_valore_param_applic().setEditMode();
	getForm().getConfigurationList().getDs_lista_valori_ammessi().setEditMode();
	getForm().getConfigurationList().getDs_valore_param_applic().setEditMode();
	getForm().getConfigurationList().getFl_appart_applic().setEditMode();
	getForm().getConfigurationList().getFl_appart_ambiente().setEditMode();
	getForm().getConfigurationList().getFl_apparte_ente().setEditMode();
	getForm().getConfigurationList().getFl_appart_applic().setReadonly(false);
	getForm().getConfigurationList().getFl_appart_ambiente().setReadonly(false);
	getForm().getConfigurationList().getFl_apparte_ente().setReadonly(false);

	forwardToPublisher(Application.Publisher.REGISTRO_PARAMETRI);
    }

    /**
     * Elimina un parametro dalla lista
     *
     * @throws EMFError errore generico
     */
    @Override
    public void deleteConfigurationList() throws EMFError {
	IamParamApplicRowBean row = (IamParamApplicRowBean) getForm().getConfigurationList()
		.getTable().getCurrentRow();
	int deletedRowIndex = getForm().getConfigurationList().getTable().getCurrentRowIndex();
	getForm().getConfigurationList().getTable().remove(deletedRowIndex);
	if (row.getIdParamApplic() != null
		&& entiConvenzionatiEjb.deleteIamParamApplicRowBean(row)) {
	    getMessageBox().addInfo("Configurazione eliminata con successo");
	    getMessageBox().setViewMode(ViewMode.plain);
	}

	forwardToPublisher(Application.Publisher.REGISTRO_PARAMETRI);
	// getForm().getConfigurationList().setTable(enti.getConfigurationViewBean(getForm().getConfiguration().getTi_param_applic_combo().parse()));
    }

    /**
     * Elimina un parametro di amministrazione dalla lista
     *
     * @throws EMFError errore generico
     */
    @Override
    public void deleteParametriAmministrazioneList() throws EMFError {
	IamParamApplicRowBean row = (IamParamApplicRowBean) getForm()
		.getParametriAmministrazioneList().getTable().getCurrentRow();
	int deletedRowIndex = getForm().getParametriAmministrazioneList().getTable()
		.getCurrentRowIndex();
	getForm().getParametriAmministrazioneList().getTable().remove(deletedRowIndex);
	BigDecimal idValoreParamApplic = row.getBigDecimal("id_valore_param_applic");
	/*
	 * Codice aggiuntivo per il logging...
	 */
	LogParam param = SpagoliteLogUtil.getLogParam(paramHelper.getParamApplicApplicationName(),
		getUser().getUsername(), SpagoliteLogUtil.getPageName(this));
	param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
	param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
		getForm().getParametriAmministrazioneList()));
	if (idValoreParamApplic != null) {
	    if (entiConvenzionatiEjb.deleteParametro(param, idValoreParamApplic)) {
		getMessageBox().addInfo("Parametro di amministrazione eliminato con successo");
		getMessageBox().setViewMode(ViewMode.plain);
	    }
	} else {
	    getMessageBox()
		    .addWarning("Valore sull'ente non presente: nessuna cancellazione effettuata");
	}
	try {
	    loadDettaglioEnteConvenzionato(
		    getForm().getEnteConvenzionatoDetail().getId_ente_siam().parse());
	    postLoad();
	} catch (ParerUserError ex) {
	    getMessageBox().addError("Errore durante il caricamento dell'ente convenzionato");
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO);
    }

    /**
     * Elimina un parametro di conservazione dalla lista
     *
     * @throws EMFError errore generico
     */
    @Override
    public void deleteParametriConservazioneList() throws EMFError {
	IamParamApplicRowBean row = (IamParamApplicRowBean) getForm()
		.getParametriConservazioneList().getTable().getCurrentRow();
	int deletedRowIndex = getForm().getParametriConservazioneList().getTable()
		.getCurrentRowIndex();
	getForm().getParametriConservazioneList().getTable().remove(deletedRowIndex);
	BigDecimal idValoreParamApplic = row.getBigDecimal("id_valore_param_applic");
	/*
	 * Codice aggiuntivo per il logging...
	 */
	LogParam param = SpagoliteLogUtil.getLogParam(paramHelper.getParamApplicApplicationName(),
		getUser().getUsername(), SpagoliteLogUtil.getPageName(this));
	param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
	param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
		getForm().getParametriConservazioneList()));
	if (idValoreParamApplic != null) {
	    if (entiConvenzionatiEjb.deleteParametro(param, idValoreParamApplic)) {
		getMessageBox().addInfo("Parametro di conservazione eliminato con successo");
		getMessageBox().setViewMode(ViewMode.plain);
	    }
	} else {
	    getMessageBox()
		    .addWarning("Valore sull'ente non presente: nessuna cancellazione effettuata");
	}
	try {
	    loadDettaglioEnteConvenzionato(
		    getForm().getEnteConvenzionatoDetail().getId_ente_siam().parse());
	    postLoad();
	} catch (ParerUserError ex) {
	    getMessageBox().addError("Errore durante il caricamento dell'ente convenzionato");
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO);
    }

    /**
     * Elimina un parametro di gestione dalla lista
     *
     * @throws EMFError errore generico
     */
    @Override
    public void deleteParametriGestioneList() throws EMFError {
	IamParamApplicRowBean row = (IamParamApplicRowBean) getForm().getParametriGestioneList()
		.getTable().getCurrentRow();
	int deletedRowIndex = getForm().getParametriGestioneList().getTable().getCurrentRowIndex();
	getForm().getParametriGestioneList().getTable().remove(deletedRowIndex);
	BigDecimal idValoreParamApplic = row.getBigDecimal("id_valore_param_applic");
	/*
	 * Codice aggiuntivo per il logging...
	 */
	LogParam param = SpagoliteLogUtil.getLogParam(paramHelper.getParamApplicApplicationName(),
		getUser().getUsername(), SpagoliteLogUtil.getPageName(this));
	param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
	param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
		getForm().getParametriGestioneList()));
	if (idValoreParamApplic != null) {
	    if (entiConvenzionatiEjb.deleteParametro(param, idValoreParamApplic)) {
		getMessageBox().addInfo("Parametro di gestione eliminato con successo");
		getMessageBox().setViewMode(ViewMode.plain);
	    }
	} else {
	    getMessageBox()
		    .addWarning("Valore sull'ente non presente: nessuna cancellazione effettuata");
	}
	try {
	    loadDettaglioEnteConvenzionato(
		    getForm().getEnteConvenzionatoDetail().getId_ente_siam().parse());
	    postLoad();
	} catch (ParerUserError ex) {
	    getMessageBox().addError("Errore durante il caricamento dell'ente convenzionato");
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO);
    }

    /**
     * Elimina un parametro di amministrazione dalla lista
     *
     * @throws EMFError errore generico
     */
    @Override
    public void deleteParametriAmministrazioneAmbienteList() throws EMFError {
	IamParamApplicRowBean row = (IamParamApplicRowBean) getForm()
		.getParametriAmministrazioneAmbienteList().getTable().getCurrentRow();
	int deletedRowIndex = getForm().getParametriAmministrazioneAmbienteList().getTable()
		.getCurrentRowIndex();
	getForm().getParametriAmministrazioneAmbienteList().getTable().remove(deletedRowIndex);
	BigDecimal idValoreParamApplic = row.getBigDecimal("id_valore_param_applic");
	/*
	 * Codice aggiuntivo per il logging...
	 */
	LogParam param = SpagoliteLogUtil.getLogParam(paramHelper.getParamApplicApplicationName(),
		getUser().getUsername(), SpagoliteLogUtil.getPageName(this));
	param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
	param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
		getForm().getParametriAmministrazioneAmbienteList()));
	if (idValoreParamApplic != null) {
	    if (entiConvenzionatiEjb.deleteParametroAmb(param, idValoreParamApplic)) {
		getMessageBox().addInfo("Parametro di amministrazione eliminato con successo");
		getMessageBox().setViewMode(ViewMode.plain);
	    }
	} else {
	    getMessageBox().addWarning(
		    "Valore sull'ambiente non presente: nessuna cancellazione effettuata");
	}
	try {
	    loadDettaglioAmbienteEnteConvenzionato(getForm().getAmbienteEnteConvenzionatoDetail()
		    .getId_ambiente_ente_convenz().parse());
	} catch (ParerUserError ex) {
	    getMessageBox()
		    .addError("Errore durante il caricamento dell'ambiente ente convenzionato");
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_AMBIENTE);
    }

    /**
     * Elimina un parametro di conservazione dalla lista
     *
     * @throws EMFError errore generico
     */
    @Override
    public void deleteParametriConservazioneAmbienteList() throws EMFError {
	IamParamApplicRowBean row = (IamParamApplicRowBean) getForm()
		.getParametriConservazioneAmbienteList().getTable().getCurrentRow();
	int deletedRowIndex = getForm().getParametriConservazioneAmbienteList().getTable()
		.getCurrentRowIndex();
	getForm().getParametriConservazioneAmbienteList().getTable().remove(deletedRowIndex);
	BigDecimal idValoreParamApplic = row.getBigDecimal("id_valore_param_applic");
	/*
	 * Codice aggiuntivo per il logging...
	 */
	LogParam param = SpagoliteLogUtil.getLogParam(paramHelper.getParamApplicApplicationName(),
		getUser().getUsername(), SpagoliteLogUtil.getPageName(this));
	param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
	param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
		getForm().getParametriConservazioneAmbienteList()));
	if (idValoreParamApplic != null) {
	    if (entiConvenzionatiEjb.deleteParametroAmb(param, idValoreParamApplic)) {
		getMessageBox().addInfo("Parametro di conservazione eliminato con successo");
		getMessageBox().setViewMode(ViewMode.plain);
	    }
	} else {
	    getMessageBox().addWarning(
		    "Valore sull'ambiente non presente: nessuna cancellazione effettuata");
	}
	try {
	    loadDettaglioAmbienteEnteConvenzionato(getForm().getAmbienteEnteConvenzionatoDetail()
		    .getId_ambiente_ente_convenz().parse());
	} catch (ParerUserError ex) {
	    getMessageBox()
		    .addError("Errore durante il caricamento dell'ambiente ente convenzionato");
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_AMBIENTE);
    }

    /**
     * Elimina un parametro di gestione dalla lista
     *
     * @throws EMFError errore generico
     */
    @Override
    public void deleteParametriGestioneAmbienteList() throws EMFError {
	IamParamApplicRowBean row = (IamParamApplicRowBean) getForm()
		.getParametriGestioneAmbienteList().getTable().getCurrentRow();
	int deletedRowIndex = getForm().getParametriGestioneAmbienteList().getTable()
		.getCurrentRowIndex();
	getForm().getParametriGestioneAmbienteList().getTable().remove(deletedRowIndex);
	BigDecimal idValoreParamApplic = row.getBigDecimal("id_valore_param_applic");
	/*
	 * Codice aggiuntivo per il logging...
	 */
	LogParam param = SpagoliteLogUtil.getLogParam(paramHelper.getParamApplicApplicationName(),
		getUser().getUsername(), SpagoliteLogUtil.getPageName(this));
	param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
	param.setNomeAzione(SpagoliteLogUtil.getDetailActionNameDelete(getForm(),
		getForm().getParametriGestioneAmbienteList()));
	if (idValoreParamApplic != null) {
	    if (entiConvenzionatiEjb.deleteParametroAmb(param, idValoreParamApplic)) {
		getMessageBox().addInfo("Parametro di gestione eliminato con successo");
		getMessageBox().setViewMode(ViewMode.plain);
	    }
	} else {
	    getMessageBox().addWarning(
		    "Valore sull'ambiente non presente: nessuna cancellazione effettuata");
	}
	try {
	    loadDettaglioAmbienteEnteConvenzionato(getForm().getAmbienteEnteConvenzionatoDetail()
		    .getId_ambiente_ente_convenz().parse());
	} catch (ParerUserError ex) {
	    getMessageBox()
		    .addError("Errore durante il caricamento dell'ambiente ente convenzionato");
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_AMBIENTE);
    }

    private void redirectToEntiNonConvenzionati(String listName) throws EMFError {
	AmministrazioneEntiNonConvenzionatiForm form = new AmministrazioneEntiNonConvenzionatiForm();
	it.eng.spagoLite.form.list.List formList = ((it.eng.spagoLite.form.list.List) getForm()
		.getComponent(listName));
	((it.eng.spagoLite.form.list.List<SingleValueField<?>>) form
		.getComponent(form.getListaEntiNonConvenzionati().getName()))
		.setTable(formList.getTable());
	redirectToAction(Application.Actions.AMMINISTRAZIONE_ENTI_NON_CONVENZIONATI,
		"?operation=listNavigationOnClick&navigationEvent=" + getNavigationEvent()
			+ "&table=" + form.getListaEntiNonConvenzionati().getName() + "&riga="
			+ formList.getTable().getCurrentRowIndex(),
		form);
    }

    private void redirectToFattureAccordo(String listName) throws EMFError {
	GestioneFatturazioneServiziForm form = new GestioneFatturazioneServiziForm();
	it.eng.spagoLite.form.list.List formList = ((it.eng.spagoLite.form.list.List) getForm()
		.getComponent(listName));
	((it.eng.spagoLite.form.list.List<SingleValueField<?>>) form
		.getComponent(form.getListaFatture().getName())).setTable(formList.getTable());
	redirectToAction(Application.Actions.GESTIONE_FATTURAZIONE_SERVIZI,
		"?operation=listNavigationOnClick&navigationEvent=" + getNavigationEvent()
			+ "&table=" + form.getListaFatture().getName() + "&riga="
			+ formList.getTable().getCurrentRowIndex(),
		form);
    }

    @Override
    public void filterInactiveRecordsOrganizzazioniVersantiList() throws EMFError {
	try {
	    int rowIndex = 0;
	    int pageSize = 10;
	    if (getForm().getOrganizzazioniVersantiList().getTable() != null) {
		rowIndex = getForm().getOrganizzazioniVersantiList().getTable()
			.getCurrentRowIndex();
		pageSize = getForm().getOrganizzazioniVersantiList().getTable().getPageSize();
	    }

	    final BigDecimal idEnteConvenz = ((OrgVRicEnteConvenzRowBean) getForm()
		    .getListaEntiConvenzionati().getTable().getCurrentRow()).getIdEnteConvenz();
	    getForm().getOrganizzazioniVersantiList()
		    .setTable(entiConvenzionatiEjb.getOrgVEnteConvenzByOrganizTableBean(
			    idEnteConvenz,
			    getForm().getOrganizzazioniVersantiList().isFilterValidRecords()));
	    getForm().getOrganizzazioniVersantiList().getTable().setCurrentRowIndex(rowIndex);
	    getForm().getOrganizzazioniVersantiList().getTable().setPageSize(pageSize);
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
	forwardToPublisher(getLastPublisher());
    }

    @Override
    public JSONObject triggerAccordoWizardDetailId_classe_ente_convenzOnTrigger() throws EMFError {
	throw new UnsupportedOperationException("Not supported yet."); // To change body of
								       // generated methods, choose
	// Tools | Templates.
    }

    @Override
    public void generaTabellaTipiServizioPerWz() throws EMFError {
	getForm().getAccordoWizardDetail().post(getRequest());
	BigDecimal idTariffario = getForm().getAccordoWizardDetail().getId_tariffario().parse();
	// Carico la lista dei tipi servizio
	BaseTable tabella = entiConvenzionatiEjb
		.getTipiServizioAssociatiTariffarioPerAccordoDaCreare(idTariffario);
	getForm().getTipoServizioAccordoList().setTable(tabella);
	getForm().getTipoServizioAccordoList().getTable().setPageSize(10);
	getForm().getTipoServizioAccordoList().getTable().first();
	getForm().getTipoServizioAccordoList().getIm_tariffa_accordo().setEditMode();
	forwardToPublisher(Application.Publisher.DETTAGLIO_ENTE_CONVENZIONATO_WIZARD);
    }

    @Override
    public void generaTabellaTipiServizio() throws EMFError {
	getForm().getAccordoDetail().post(getRequest());
	BigDecimal idTariffario = getForm().getAccordoDetail().getId_tariffario().parse();
	// Carico la lista dei tipi servizio
	BaseTable tabella = entiConvenzionatiEjb
		.getTipiServizioAssociatiTariffarioPerAccordoDaCreare(idTariffario);
	getForm().getTipoServizioAccordoList().setTable(tabella);
	getForm().getTipoServizioAccordoList().getTable().setPageSize(10);
	getForm().getTipoServizioAccordoList().getTable().first();
	getForm().getTipoServizioAccordoList().getIm_tariffa_accordo().setEditMode();
	forwardToPublisher(Application.Publisher.DETTAGLIO_ACCORDO);
    }

    private boolean checkImportoTariffa(String imTariffa) throws ParerUserError {
	boolean esito = false;
	if (imTariffa != null && !imTariffa.equals("")) {
	    try {
		BigDecimal imTariffaNum = new BigDecimal(imTariffa);
		esito = true;
	    } catch (NumberFormatException e) {
		throw new ParerUserError(
			"Uno degli importi delle Entità rimborso costi non è in formato numerico");
	    }
	} else {
	    esito = true;
	}
	return esito;
    }

    @Override
    public JSONObject triggerAnnualitaAccordoDetailAa_anno_accordoOnTrigger() throws EMFError {
	getMessageBox().clear();
	boolean erroriPresenti = getMessageBox().hasError();
	Message errore = new Message();
	try {
	    getForm().getAnnualitaAccordoDetail().post(getRequest());
	    BigDecimal anno = getForm().getAnnualitaAccordoDetail().getAa_anno_accordo().parse();
	    Date dtDecAccordo = getForm().getAccordoDetail().getDt_dec_accordo().parse();
	    Date dtScadAccordo = getForm().getAccordoDetail().getDt_scad_accordo().parse();
	    Calendar c = Calendar.getInstance();
	    c.setTime(dtDecAccordo);
	    int annoDecorrenza = c.get(Calendar.YEAR);
	    int meseDecorrenza = c.get(Calendar.MONTH);
	    c.setTime(dtScadAccordo);
	    int annoScadenza = c.get(Calendar.YEAR);
	    int meseScadenza = c.get(Calendar.MONTH);
	    int mesi = 0;

	    // Controllo che l'anno non sia già stato inserito
	    for (OrgAaAccordoRowBean aaAccordoRowBean : (OrgAaAccordoTableBean) getForm()
		    .getAnnualitaAccordoList().getTable()) {
		if (aaAccordoRowBean.getAaAnnoAccordo().intValue() == anno.intValue()) {
		    errore = new Message(Message.MessageLevel.ERR, "Anno giÃ  inserito");
		    return getForm().getAccordoDetail().asJSON(errore);
		}
	    }

	    if (annoDecorrenza <= anno.intValue() && annoScadenza >= anno.intValue()) {
		// Recupero il mese
		boolean isPrimoAnno = false;
		boolean isUltimoAnno = false;

		if (annoScadenza == anno.intValue()) {
		    isUltimoAnno = true;
		} else if (annoDecorrenza == anno.intValue()) {
		    isPrimoAnno = true;
		}

		if (isPrimoAnno) {
		    mesi = 12 - meseDecorrenza - 1;
		} else if (!isUltimoAnno) {
		    mesi = 12;
		} else {
		    mesi = meseScadenza + 1;
		}

		getForm().getAnnualitaAccordoDetail().getMm_aa_accordo().setValue("" + mesi);
		return getForm().getAnnualitaAccordoDetail().asJSON();

	    } else {
		errore = new Message(Message.MessageLevel.ERR,
			"Anno non compreso nel range di validitÃ ");
		return getForm().getAccordoDetail().asJSON(errore);
	    }

	} catch (Exception e) {
	    errore = new Message(Message.MessageLevel.ERR, "Anno non formalmente corretto");
	    return getForm().getAccordoDetail().asJSON(errore);
	}

    }

    @Override
    public void abilitaInserisciAnnualita() throws EMFError {
	getForm().getAccordoDetail().post(getRequest());
	getForm().getAnnualitaAccordoList().setHideInsertButton(true);
	if (entiConvenzionatiEjb.checkInsertAnnualita(
		getForm().getAccordoDetail().getFl_pagamento().parse(),
		getForm().getAccordoDetail().getId_tipo_accordo().parse(),
		getForm().getAccordoDetail().getDt_scad_accordo().parse())) {
	    getForm().getAnnualitaAccordoList().setHideInsertButton(false);
	}
	forwardToPublisher(Application.Publisher.DETTAGLIO_ACCORDO);
    }

    @Override
    public void scaricaCompFileUdAccordoAdozione() throws Throwable {
	String registro = getForm().getAccordoDetail().getCd_registro_determina().parse();
	BigDecimal anno = getForm().getAccordoDetail().getAa_determina().parse();
	String numero = getForm().getAccordoDetail().getCd_key_determina().parse();
	BigDecimal idAccordoEnte = getForm().getAccordoDetail().getId_accordo_ente().parse();
	scaricaCompFileUd(idAccordoEnte, "accordo", registro, anno, numero);
    }

    @Secure(action = "Menu.EntiConvenzionati.GestioneAccordi")
    public void ricercaAccordiPage() throws EMFError {
	try {
	    getUser().getMenu().reset();
	    getUser().getMenu().select("Menu.EntiConvenzionati.GestioneAccordi");

	    initFiltriRicercaAccordi();

	    getForm().getListaAccordi().setTable(null);
	    getForm().getAnnualitaSenzaAttoList().setTable(null);
	    getForm().getAnnualitaSenzaAttoSection().setHidden(true);
	    getForm().getReportStorageExtraRerSection().setHidden(true);

	    forwardToPublisher(Application.Publisher.RICERCA_ACCORDI);
	} catch (ParerUserError ex) {
	    getMessageBox().addError(ex.getDescription());
	}
    }

    private void initFiltriRicercaAccordi() throws ParerUserError, EMFError {
	getForm().getAccordiList().setTable(null);
	getForm().getFiltriAccordi().reset();
	getForm().getFiltriAccordi().setEditMode();
	getForm().getFiltriAccordi().getId_ambiente_ente_convenz()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getUsrVAbilAmbEnteConvenzTableBean(
				new BigDecimal(getUser().getIdUtente())),
			"id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));
	getForm().getFiltriAccordi().getId_tipo_accordo()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgTipoAccordoTableBean(), "id_tipo_accordo",
			"cd_tipo_accordo"));
	getForm().getFiltriAccordi().getFl_recesso()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	getForm().getFiltriAccordi().getId_tipo_gestione_accordo()
		.setDecodeMap(DecodeMap.Factory.newInstance(
			entiConvenzionatiEjb.getOrgTipiGestioneAccordoTableBean(),
			"id_tipo_gestione_accordo", "cd_tipo_gestione_accordo"));

	getForm().getFiltriAccordi().getFl_fascia_manuale()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	getForm().getFiltriAccordi().getFl_esiste_nota_fatturazione()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	getForm().getFiltriAccordi().getFl_esistono_sae_combo()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	getForm().getFiltriAccordi().getFl_esistono_sue_combo()
		.setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
	getForm().getFiltriAccordi().getTi_stato_accordo()
		.setDecodeMap(ComboGetter.getMappaTiStatoAccordo());
    }

    @Override
    public void ricercaAccordi() throws EMFError {
	if (getForm().getFiltriAccordi().postAndValidate(getRequest(), getMessageBox())) {
	    BigDecimal idUserIamCor = new BigDecimal(getUser().getIdUtente());
	    String nmEnteConvenz = getForm().getFiltriAccordi().getNm_ente_convenz().parse();
	    BigDecimal idAmbienteEnteConvenz = getForm().getFiltriAccordi()
		    .getId_ambiente_ente_convenz().parse();
	    BigDecimal idAccordoEnte = getForm().getFiltriAccordi().getId_accordo_ente().parse();
	    String cdRegistroRepertorio = getForm().getFiltriAccordi().getCd_registro_repertorio()
		    .parse();
	    BigDecimal aaRepertorio = getForm().getFiltriAccordi().getAa_repertorio().parse();
	    String cdKeyRepertorio = getForm().getFiltriAccordi().getCd_key_repertorio().parse();
	    String tiStatoAccordo = getForm().getFiltriAccordi().getTi_stato_accordo().parse();

	    List<BigDecimal> idTipoAccordo = getForm().getFiltriAccordi().getId_tipo_accordo()
		    .parse();
	    // Sistemo il range di date
	    decoraDateRicercaAccordi(getForm().getFiltriAccordi().getDt_dec_accordo_da().parse(),
		    getForm().getFiltriAccordi().getDt_dec_accordo_a().parse(),
		    getForm().getFiltriAccordi().getDt_fine_valid_accordo_da().parse(),
		    getForm().getFiltriAccordi().getDt_fine_valid_accordo_a().parse(),
		    getForm().getFiltriAccordi().getDt_dec_accordo_info_da().parse(),
		    getForm().getFiltriAccordi().getDt_dec_accordo_info_a().parse(),
		    getForm().getFiltriAccordi().getDt_scad_accordo_da().parse(),
		    getForm().getFiltriAccordi().getDt_scad_accordo_a().parse());

	    Date dtDecAccordoDa = getForm().getFiltriAccordi().getDt_dec_accordo_da().parse();
	    Date dtDecAccordoA = getForm().getFiltriAccordi().getDt_dec_accordo_a().parse();
	    Date dtDecAccordoInfoDa = getForm().getFiltriAccordi().getDt_dec_accordo_info_da()
		    .parse();
	    Date dtDecAccordoInfoA = getForm().getFiltriAccordi().getDt_dec_accordo_info_a()
		    .parse();
	    Date dtFineValidAccordoDa = getForm().getFiltriAccordi().getDt_fine_valid_accordo_da()
		    .parse();
	    Date dtFineValidAccordoA = getForm().getFiltriAccordi().getDt_fine_valid_accordo_a()
		    .parse();
	    Date dtScadAccordoDa = getForm().getFiltriAccordi().getDt_scad_accordo_da().parse();
	    Date dtScadAccordoA = getForm().getFiltriAccordi().getDt_scad_accordo_a().parse();

	    String errorDateDec = null;
	    String errorDateScad = null;
	    String errorDateIniVal = null;
	    String errorDateFineVal = null;

	    errorDateDec = DateUtil.ucContrDate("", dtDecAccordoInfoDa, dtDecAccordoInfoA,
		    "decorrenza Da", "decorrenza A");
	    errorDateScad = DateUtil.ucContrDate("", dtScadAccordoDa, dtScadAccordoA, "scadenza Da",
		    "scadenza A");
	    errorDateIniVal = DateUtil.ucContrDate("", dtDecAccordoDa, dtDecAccordoA,
		    "inizio validitÃ  Da", "inizio validit\u00E0 A");
	    errorDateFineVal = DateUtil.ucContrDate("", dtFineValidAccordoDa, dtFineValidAccordoA,
		    "fine validitÃ  Da", "fine validit\u00E0 A");

	    if (errorDateDec != null) {
		getMessageBox().addError(errorDateDec);
	    }
	    if (errorDateScad != null) {
		getMessageBox().addError(errorDateScad);
	    }
	    if (errorDateIniVal != null) {
		getMessageBox().addError(errorDateIniVal);
	    }
	    if (errorDateFineVal != null) {
		getMessageBox().addError(errorDateFineVal);
	    }

	    String flRecesso = getForm().getFiltriAccordi().getFl_recesso().parse();
	    List<BigDecimal> idTipoGestioneAccordo = getForm().getFiltriAccordi()
		    .getId_tipo_gestione_accordo().parse();
	    String flEsisteNotaFatturazione = getForm().getFiltriAccordi()
		    .getFl_esiste_nota_fatturazione().parse();
	    String flFasciaManuale = getForm().getFiltriAccordi().getFl_fascia_manuale().parse();
	    String flEsistonoSeAnnuali = getForm().getFiltriAccordi().getFl_esistono_sae_combo()
		    .parse();
	    String flEsistonoSeUt = getForm().getFiltriAccordi().getFl_esistono_sue_combo().parse();
	    Date saeDa = null;
	    Date saeA = null;
	    Date sueDa = null;
	    Date sueA = null;

	    if (getForm().getFiltriAccordi().getSae_da().isEditMode()) {
		decoraDateServiziAnnualiRicercaAccordi(
			getForm().getFiltriAccordi().getSae_da().parse(),
			getForm().getFiltriAccordi().getSae_a().parse());
		saeDa = getForm().getFiltriAccordi().getSae_da().parse();
		saeA = getForm().getFiltriAccordi().getSae_a().parse();
		String errorDateSae = DateUtil.ucContrDate("", saeDa, saeA,
			"servizi annuali erogati Da", "servizi annuali erogati A");
		if (errorDateSae != null) {
		    getMessageBox().addError(errorDateSae);
		}

	    }
	    if (getForm().getFiltriAccordi().getSue_da().isEditMode()) {
		decoraDateServiziUnatantumRicercaAccordi(
			getForm().getFiltriAccordi().getSue_da().parse(),
			getForm().getFiltriAccordi().getSue_a().parse());
		sueDa = getForm().getFiltriAccordi().getSue_da().parse();
		sueA = getForm().getFiltriAccordi().getSue_a().parse();
		String errorDateSue = DateUtil.ucContrDate("", sueDa, sueA,
			"servizi unatantum erogati Da", "servizi unatantum erogati A");
		if (errorDateSue != null) {
		    getMessageBox().addError(errorDateSue);
		}
	    }

	    // Nascondo Annualità senza atto
	    getForm().getAnnualitaSenzaAttoList().setTable(null);
	    getForm().getAnnualitaSenzaAttoSection().setHidden(true);
	    // Nascondo Report storage extra RER
	    getForm().getReportStorageExtraRerList().setTable(null);
	    getForm().getReportStorageExtraRerSection().setHidden(true);

	    if (!getMessageBox().hasError()) {
		try {
		    OrgVRicAccordoEnteTableBean table = entiConvenzionatiEjb
			    .getOrgVRicAccordoEnteTableBean(idUserIamCor, idAmbienteEnteConvenz,
				    nmEnteConvenz, idAccordoEnte, cdRegistroRepertorio,
				    aaRepertorio, cdKeyRepertorio, idTipoAccordo, dtDecAccordoDa,
				    dtDecAccordoA, dtFineValidAccordoDa, dtFineValidAccordoA,
				    dtDecAccordoInfoDa, dtDecAccordoInfoA, dtScadAccordoDa,
				    dtScadAccordoA, flRecesso, idTipoGestioneAccordo,
				    flEsisteNotaFatturazione, flEsistonoSeAnnuali, saeDa, saeA,
				    flEsistonoSeUt, sueDa, sueA, flFasciaManuale, tiStatoAccordo);
		    getForm().getListaAccordi().setTable(table);
		    getForm().getListaAccordi().getTable().setPageSize(10);
		    getForm().getListaAccordi().getTable().first();
		    // Passo i filtri utilizzati nella ricerca da mettere nell'Excel da scaricare
		    Fields genericFields = getForm().getFiltriAccordi();
		    getForm().getListaAccordi().setGenericFields(genericFields);

		} catch (ParerUserError pue) {
		    getMessageBox().addError(pue.getDescription());
		}
	    }
	}
	forwardToPublisher(getLastPublisher());
    }

    private void decoraDateRicercaAccordi(Date dtDecAccordoDa, Date dtDecAccordoA, Date dtFineValDa,
	    Date dtFineValA, Date dtDecAccordoInfoDa, Date dtDecAccordoInfoA, Date dtScadAccordoDa,
	    Date dtScadAccordoA) {
	DateFormat formato = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH);

	if (dtDecAccordoDa != null && dtDecAccordoA == null) {
	    Calendar c = Calendar.getInstance();
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);
	    dtDecAccordoA = c.getTime();
	    getForm().getFiltriAccordi().getDt_dec_accordo_a()
		    .setValue(formato.format(dtDecAccordoA));
	} else if (dtDecAccordoDa == null && dtDecAccordoA != null) {
	    Calendar c = Calendar.getInstance();
	    c.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
	    dtDecAccordoDa = c.getTime();
	    getForm().getFiltriAccordi().getDt_dec_accordo_da()
		    .setValue(formato.format(dtDecAccordoDa));
	}

	if (dtFineValDa != null && dtFineValA == null) {
	    Calendar c = Calendar.getInstance();
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);
	    dtFineValA = c.getTime();
	    getForm().getFiltriAccordi().getDt_fine_valid_accordo_a()
		    .setValue(formato.format(dtFineValA));
	} else if (dtFineValDa == null && dtFineValA != null) {
	    Calendar c = Calendar.getInstance();
	    c.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
	    dtFineValDa = c.getTime();
	    getForm().getFiltriAccordi().getDt_fine_valid_accordo_da()
		    .setValue(formato.format(dtFineValDa));
	}

	if (dtDecAccordoInfoDa != null && dtDecAccordoInfoA == null) {
	    Calendar c = Calendar.getInstance();
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);
	    dtDecAccordoInfoA = c.getTime();
	    getForm().getFiltriAccordi().getDt_dec_accordo_info_a()
		    .setValue(formato.format(dtDecAccordoInfoA));
	} else if (dtDecAccordoInfoDa == null && dtDecAccordoInfoA != null) {
	    Calendar c = Calendar.getInstance();
	    c.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
	    dtDecAccordoInfoDa = c.getTime();
	    getForm().getFiltriAccordi().getDt_dec_accordo_info_da()
		    .setValue(formato.format(dtDecAccordoInfoDa));
	}

	if (dtScadAccordoDa != null && dtScadAccordoA == null) {
	    Calendar c = Calendar.getInstance();
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);
	    dtScadAccordoA = c.getTime();
	    getForm().getFiltriAccordi().getDt_scad_accordo_a()
		    .setValue(formato.format(dtScadAccordoA));
	} else if (dtScadAccordoDa == null && dtScadAccordoA != null) {
	    Calendar c = Calendar.getInstance();
	    c.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
	    dtScadAccordoDa = c.getTime();
	    getForm().getFiltriAccordi().getDt_scad_accordo_da()
		    .setValue(formato.format(dtScadAccordoDa));
	}
    }

    private void decoraDateServiziAnnualiRicercaAccordi(Date saeDa, Date saeA) {
	DateFormat formato = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH);
	if (saeDa != null && saeA == null) {
	    Calendar c = Calendar.getInstance();
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);
	    saeA = c.getTime();
	    getForm().getFiltriAccordi().getSae_a().setValue(formato.format(saeA));
	} else if (saeDa == null && saeA != null) {
	    Calendar c = Calendar.getInstance();
	    c.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
	    saeDa = c.getTime();
	    getForm().getFiltriAccordi().getSae_da().setValue(formato.format(saeDa));
	}
    }

    private void decoraDateServiziUnatantumRicercaAccordi(Date sueDa, Date sueA) {
	DateFormat formato = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH);
	if (sueDa != null && sueA == null) {
	    Calendar c = Calendar.getInstance();
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);
	    sueA = c.getTime();
	    getForm().getFiltriAccordi().getSae_a().setValue(formato.format(sueA));
	} else if (sueDa == null && sueA != null) {
	    Calendar c = Calendar.getInstance();
	    c.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
	    sueDa = c.getTime();
	    getForm().getFiltriAccordi().getSae_da().setValue(formato.format(sueDa));
	}
    }

    @Override
    public void pulisciRicercaAccordi() throws EMFError {
	try {
	    initFiltriRicercaAccordi();
	} catch (ParerUserError ex) {
	    getMessageBox().addError(
		    "Errore durante il reset dei filtri di ricerca accordi degli enti convenzionati");
	}
	forwardToPublisher(Application.Publisher.RICERCA_ACCORDI);
    }

    @Override
    public void annualitaSenzaAtto() throws EMFError {
	getForm().getAnnualitaSenzaAttoSection().setHidden(false);
	BaseTable annualitaSenzaAttoTB = entiConvenzionatiEjb.getAnnualitaSenzaAtto();

	getForm().getAnnualitaSenzaAttoList().setTable(annualitaSenzaAttoTB);
	getForm().getAnnualitaSenzaAttoList().getTable().setPageSize(10);
	getForm().getAnnualitaSenzaAttoList().getTable().first();

	forwardToPublisher(Application.Publisher.RICERCA_ENTI_CONVENZIONATI);
    }

    @Override
    public void annualitaSenzaAttoAccordi() throws EMFError {
	getForm().getAnnualitaSenzaAttoSection().setHidden(false);
	BaseTable annualitaSenzaAttoTB = entiConvenzionatiEjb.getAnnualitaSenzaAtto();

	getForm().getAnnualitaSenzaAttoList().setTable(annualitaSenzaAttoTB);
	getForm().getAnnualitaSenzaAttoList().getTable().setPageSize(10);
	getForm().getAnnualitaSenzaAttoList().getTable().first();

	forwardToPublisher(Application.Publisher.RICERCA_ACCORDI);
    }

    @Override
    public JSONObject triggerEnteConvenzionatoWizardDetailId_cd_ivaOnTrigger() throws EMFError {
	getForm().getEnteConvenzionatoWizardDetail().post(getRequest());
	if (getForm().getEnteConvenzionatoWizardDetail().getId_cd_iva().parse() != null) {
	    BigDecimal idCdIva = getForm().getEnteConvenzionatoWizardDetail().getId_cd_iva()
		    .parse();
	    getForm().getEnteConvenzionatoWizardDetail().getDs_iva()
		    .setValue(entiConvenzionatiEjb.getDsIva(idCdIva));
	}
	return getForm().getEnteConvenzionatoWizardDetail().asJSON();
    }

    @Override
    public JSONObject triggerEnteConvenzionatoDetailId_cd_ivaOnTrigger() throws EMFError {
	getForm().getEnteConvenzionatoDetail().post(getRequest());
	getForm().getEnteConvenzionatoDetail().getDs_iva().setValue(null);
	if (getForm().getEnteConvenzionatoDetail().getId_cd_iva().parse() != null) {
	    BigDecimal idCdIva = getForm().getEnteConvenzionatoDetail().getId_cd_iva().parse();
	    getForm().getEnteConvenzionatoDetail().getDs_iva()
		    .setValue(entiConvenzionatiEjb.getDsIva(idCdIva));
	}
	return getForm().getEnteConvenzionatoDetail().asJSON();
    }

    private IamParamApplicTableBean obfuscatePasswordParamApplic(
	    IamParamApplicTableBean paramApplicTableBean) {
	// MEV26588 - offusca le password
	Iterator<IamParamApplicRowBean> rowIt = paramApplicTableBean.iterator();
	while (rowIt.hasNext()) {
	    IamParamApplicRowBean rowBean = rowIt.next();
	    if (rowBean.getTiValoreParamApplic().equals(
		    it.eng.saceriam.web.util.Constants.ComboValueParamentersType.PASSWORD.name())) {
		rowBean.setString("ds_valore_param_applic",
			it.eng.saceriam.web.util.Constants.OBFUSCATED_STRING);

		if (rowBean.getString("ds_valore_param_applic_applic") != null) {
		    rowBean.setString("ds_valore_param_applic_applic",
			    it.eng.saceriam.web.util.Constants.OBFUSCATED_STRING);
		}

		if (rowBean.getString("ds_valore_param_applic_ambiente") != null) {
		    rowBean.setString("ds_valore_param_applic_ambiente",
			    it.eng.saceriam.web.util.Constants.OBFUSCATED_STRING);
		}

		if (rowBean.getString("ds_valore_param_applic_ente_amm") != null) {
		    rowBean.setString("ds_valore_param_applic_ente_amm",
			    it.eng.saceriam.web.util.Constants.OBFUSCATED_STRING);
		}

		if (rowBean.getString("ds_valore_param_applic_ente_gest") != null) {
		    rowBean.setString("ds_valore_param_applic_ente_gest",
			    it.eng.saceriam.web.util.Constants.OBFUSCATED_STRING);
		}

		if (rowBean.getString("ds_valore_param_applic_ente_cons") != null) {
		    rowBean.setString("ds_valore_param_applic_ente_cons",
			    it.eng.saceriam.web.util.Constants.OBFUSCATED_STRING);
		}

		if (rowBean.getString("ds_valore_param_applic_ambiente_amm") != null) {
		    rowBean.setString("ds_valore_param_applic_ambiente_amm",
			    it.eng.saceriam.web.util.Constants.OBFUSCATED_STRING);
		}

		if (rowBean.getString("ds_valore_param_applic_ambiente_gest") != null) {
		    rowBean.setString("ds_valore_param_applic_ambiente_gest",
			    it.eng.saceriam.web.util.Constants.OBFUSCATED_STRING);
		}

		if (rowBean.getString("ds_valore_param_applic_ambiente_cons") != null) {
		    rowBean.setString("ds_valore_param_applic_ambiente_cons",
			    it.eng.saceriam.web.util.Constants.OBFUSCATED_STRING);
		}

	    }
	}

	return paramApplicTableBean;
    }

    @Override
    public void estraiRigheFattureEnte() throws EMFError {
	String nmEnteConvenz = getForm().getEnteConvenzionatoDetail().getNm_ente_siam().parse();
	BaseTable tabella = entiConvenzionatiEjb.getReportFattureCalcolateDaRicerca(nmEnteConvenz,
		null, null, null, null, null, null, null, null, null, null, null, null, null, null,
		null);

	SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");

	getForm().getListaEstraiRigheFatture().setTable(tabella);
	getForm().getListaEstraiRigheFatture().setExcelFileName(
		"FattureCalcolate_Ente_" + nmEnteConvenz + "_" + df.format(new Date()));

	listNavigationOnClick(getForm().getListaEstraiRigheFatture().NAME, ListAction.NE_EXPORT_XLS,
		"-1", "false");
    }

    @Override
    public void reportStorageExtraRer() throws EMFError {
	getForm().getReportStorageExtraRerSection().setHidden(false);
	try {
	    BaseTable reportStorageExtraRerTB = entiConvenzionatiEjb.getReportStorageExtraRer();

	    getForm().getReportStorageExtraRerList().setTable(reportStorageExtraRerTB);
	    getForm().getReportStorageExtraRerList().getTable().setPageSize(10);
	    getForm().getReportStorageExtraRerList().getTable().first();
	} catch (ParerUserError ex) {
	    getMessageBox()
		    .addError(ex.getDescription() + ": impossibile visualizzare il report storage");
	}

	forwardToPublisher(Application.Publisher.RICERCA_ACCORDI);
    }

    // MEV #32651
    @Override
    public void filterInactiveRecordsConfigurationList() throws EMFError {
	int rowIndex = 0;
	int pageSize = WebConstants.DEFAULT_PAGE_SIZE;
	if (getForm().getConfigurationList().getTable() != null) {
	    rowIndex = getForm().getConfigurationList().getTable().getCurrentRowIndex();
	    pageSize = getForm().getConfigurationList().getTable().getPageSize();
	}

	// getForm().getConfiguration().post(getRequest());
	String tiParamApplic = getForm().getConfiguration().getTi_param_applic_combo().parse();
	String tiGestioneParam = getForm().getConfiguration().getTi_gestione_param_combo().parse();
	String flAppartApplic = getForm().getConfiguration().getFl_appart_applic_combo().parse();
	String flAppartAmbiente = getForm().getConfiguration().getFl_appart_ambiente_combo()
		.parse();
	String flApparteEnte = getForm().getConfiguration().getFl_apparte_ente_combo().parse();

	// Carico i valori della lista configurazioni
	IamParamApplicTableBean paramApplicTableBean = entiConvenzionatiEjb
		.getIamParamApplicTableBean(tiParamApplic, tiGestioneParam, flAppartApplic,
			flAppartAmbiente, flApparteEnte,
			getForm().getConfigurationList().isFilterValidRecords());

	paramApplicTableBean = obfuscatePasswordParamApplic(paramApplicTableBean);

	getForm().getConfigurationList().setTable(paramApplicTableBean);

	setConfigListReadOnly();

	// se non ho trovato risultati nascondo il pulsate "Edita"
	if (paramApplicTableBean.isEmpty()) {
	    getForm().getConfiguration().getEdit_config().setViewMode();
	}

	getForm().getConfigurationList().getTable().setCurrentRowIndex(rowIndex);
	getForm().getConfigurationList().getTable().setPageSize(pageSize);

	forwardToPublisher(Application.Publisher.REGISTRO_PARAMETRI);
    }

    @Override
    public void filterInactiveRecordsParametriAmministrazioneAmbienteList() throws EMFError {
	BigDecimal idAmbiente = ((BaseRowInterface) getForm().getListaAmbientiEntiConvenzionati()
		.getTable().getCurrentRow()).getBigDecimal("id_ambiente_ente_convenz");
	boolean filterValid = getForm().getParametriAmministrazioneAmbienteList()
		.isFilterValidRecords();
	try {

	    loadListaParametriAmministrazioneAmbiente(idAmbiente, false, false, filterValid);

	} catch (ParerUserError ex) {
	    getMessageBox().addError(
		    "Errore durante il recupero dei parametri di amministrazione dell'ambiente");
	}
	forwardToPublisher(getLastPublisher());
    }

    @Override
    public void filterInactiveRecordsParametriConservazioneAmbienteList() throws EMFError {
	BigDecimal idAmbiente = ((BaseRowInterface) getForm().getListaAmbientiEntiConvenzionati()
		.getTable().getCurrentRow()).getBigDecimal("id_ambiente_ente_convenz");
	boolean filterValid = getForm().getParametriConservazioneAmbienteList()
		.isFilterValidRecords();
	try {

	    loadListaParametriConservazioneAmbiente(idAmbiente, false, false, filterValid);

	} catch (ParerUserError ex) {
	    getMessageBox().addError(
		    "Errore durante il recupero dei parametri di conservazione dell'ambiente");
	}
	forwardToPublisher(getLastPublisher());
    }

    @Override
    public void filterInactiveRecordsParametriGestioneAmbienteList() throws EMFError {
	BigDecimal idAmbiente = ((BaseRowInterface) getForm().getListaAmbientiEntiConvenzionati()
		.getTable().getCurrentRow()).getBigDecimal("id_ambiente_ente_convenz");
	boolean filterValid = getForm().getParametriGestioneAmbienteList().isFilterValidRecords();
	try {

	    loadListaParametriGestioneAmbiente(idAmbiente, false, false, filterValid);

	} catch (ParerUserError ex) {
	    getMessageBox()
		    .addError("Errore durante il recupero dei parametri di gestione dell'ambiente");
	}
	forwardToPublisher(getLastPublisher());
    }

    private void loadListaParametriAmministrazioneAmbiente(BigDecimal idAmbiente,
	    boolean hideDeleteButtons, boolean editModeAmministrazione, boolean filterValid)
	    throws ParerUserError {

	IamParamApplicTableBean parametriAmministrazione = entiConvenzionatiEjb
		.getIamParamApplicAmministrazioneAmbiente(idAmbiente, filterValid);

	if (!editModeAmministrazione) {
	    parametriAmministrazione = obfuscatePasswordParamApplic(parametriAmministrazione);
	}

	getForm().getParametriAmministrazioneAmbienteList().setTable(parametriAmministrazione);
	getForm().getParametriAmministrazioneAmbienteList().getTable().setPageSize(300);
	getForm().getParametriAmministrazioneAmbienteList().getTable().first();
	getForm().getParametriAmministrazioneAmbienteList().setHideDeleteButton(hideDeleteButtons);
	if (editModeAmministrazione) {
	    getForm().getParametriAmministrazioneAmbienteList()
		    .getDs_valore_param_applic_ambiente_amm().setEditMode();
	    getForm().getParametriAmministrazioneAmbienteList().setStatus(Status.update);
	} else {
	    getForm().getParametriAmministrazioneAmbienteList()
		    .getDs_valore_param_applic_ambiente_amm().setViewMode();
	    getForm().getParametriAmministrazioneAmbienteList().setStatus(Status.view);
	}
    }

    private void loadListaParametriConservazioneAmbiente(BigDecimal idAmbiente,
	    boolean hideDeleteButtons, boolean editModeConservazione, boolean filterValid)
	    throws ParerUserError {

	IamParamApplicTableBean parametriConservazione = entiConvenzionatiEjb
		.getIamParamApplicConservazioneAmbiente(idAmbiente, filterValid);

	if (!editModeConservazione) {
	    parametriConservazione = obfuscatePasswordParamApplic(parametriConservazione);
	}

	getForm().getParametriConservazioneAmbienteList().setTable(parametriConservazione);
	getForm().getParametriConservazioneAmbienteList().getTable().setPageSize(300);
	getForm().getParametriConservazioneAmbienteList().getTable().first();
	getForm().getParametriConservazioneAmbienteList().setHideDeleteButton(hideDeleteButtons);
	if (editModeConservazione) {
	    getForm().getParametriConservazioneAmbienteList()
		    .getDs_valore_param_applic_ambiente_cons().setEditMode();
	    getForm().getParametriConservazioneAmbienteList().setStatus(Status.update);
	} else {
	    getForm().getParametriConservazioneAmbienteList()
		    .getDs_valore_param_applic_ambiente_cons().setViewMode();
	    getForm().getParametriConservazioneAmbienteList().setStatus(Status.view);
	}
    }

    private void loadListaParametriGestioneAmbiente(BigDecimal idAmbiente,
	    boolean hideDeleteButtons, boolean editModeGestione, boolean filterValid)
	    throws ParerUserError {

	IamParamApplicTableBean parametriGestione = entiConvenzionatiEjb
		.getIamParamApplicGestioneAmbiente(idAmbiente, filterValid);

	if (!editModeGestione) {
	    parametriGestione = obfuscatePasswordParamApplic(parametriGestione);
	}

	getForm().getParametriGestioneAmbienteList().setTable(parametriGestione);
	getForm().getParametriGestioneAmbienteList().getTable().setPageSize(300);
	getForm().getParametriGestioneAmbienteList().getTable().first();
	getForm().getParametriGestioneAmbienteList().setHideDeleteButton(hideDeleteButtons);
	if (editModeGestione) {
	    getForm().getParametriGestioneAmbienteList().getDs_valore_param_applic_ambiente_gest()
		    .setEditMode();
	    getForm().getParametriGestioneAmbienteList().setStatus(Status.update);
	} else {
	    getForm().getParametriGestioneAmbienteList().getDs_valore_param_applic_ambiente_gest()
		    .setViewMode();
	    getForm().getParametriGestioneAmbienteList().setStatus(Status.view);
	}
    }

    @Override
    public void filterInactiveRecordsParametriAmministrazioneList() throws EMFError {
	BigDecimal idEnteConvenz = ((BaseRowInterface) getForm().getListaEntiConvenzionati()
		.getTable().getCurrentRow()).getBigDecimal("id_ente_convenz");
	OrgAmbienteEnteConvenzRowBean orgAmbienteEnteConvenzByEnteConvenz = entiConvenzionatiEjb
		.getOrgAmbienteEnteConvenzByEnteConvenz(idEnteConvenz);
	boolean filterValid = getForm().getParametriAmministrazioneList().isFilterValidRecords();
	try {
	    loadListaParametriAmministrazione(
		    orgAmbienteEnteConvenzByEnteConvenz.getIdAmbienteEnteConvenz(), idEnteConvenz,
		    false, false, filterValid);
	} catch (ParerUserError ex) {
	    getMessageBox().addError(
		    "Errore durante il recupero dei parametri di amministrazione dell'ente convenzionato");
	}
	forwardToPublisher(getLastPublisher());
    }

    @Override
    public void filterInactiveRecordsParametriConservazioneList() throws EMFError {
	BigDecimal idEnteConvenz = ((BaseRowInterface) getForm().getListaEntiConvenzionati()
		.getTable().getCurrentRow()).getBigDecimal("id_ente_convenz");
	OrgAmbienteEnteConvenzRowBean orgAmbienteEnteConvenzByEnteConvenz = entiConvenzionatiEjb
		.getOrgAmbienteEnteConvenzByEnteConvenz(idEnteConvenz);
	boolean filterValid = getForm().getParametriConservazioneList().isFilterValidRecords();
	try {

	    loadListaParametriConservazione(
		    orgAmbienteEnteConvenzByEnteConvenz.getIdAmbienteEnteConvenz(), idEnteConvenz,
		    false, false, filterValid);

	} catch (ParerUserError ex) {
	    getMessageBox().addError(
		    "Errore durante il recupero dei parametri di conservazione dell'ente convenzionato");
	}
	forwardToPublisher(getLastPublisher());
    }

    @Override
    public void filterInactiveRecordsParametriGestioneList() throws EMFError {
	BigDecimal idEnteConvenz = ((BaseRowInterface) getForm().getListaEntiConvenzionati()
		.getTable().getCurrentRow()).getBigDecimal("id_ente_convenz");
	OrgAmbienteEnteConvenzRowBean orgAmbienteEnteConvenzByEnteConvenz = entiConvenzionatiEjb
		.getOrgAmbienteEnteConvenzByEnteConvenz(idEnteConvenz);
	boolean filterValid = getForm().getParametriGestioneList().isFilterValidRecords();
	try {

	    loadListaParametriGestione(
		    orgAmbienteEnteConvenzByEnteConvenz.getIdAmbienteEnteConvenz(), idEnteConvenz,
		    false, false, filterValid);

	} catch (ParerUserError ex) {
	    getMessageBox().addError(
		    "Errore durante il recupero dei parametri di gestione dell'ente convenzionato");
	}
	forwardToPublisher(getLastPublisher());
    }

    private void loadListaParametriAmministrazione(BigDecimal idAmbienteEnteConvenz,
	    BigDecimal idEnteConvenz, boolean hideDeleteButtons, boolean editModeAmministrazione,
	    boolean filterValid) throws ParerUserError {

	IamParamApplicTableBean parametriAmministrazione = entiConvenzionatiEjb
		.getIamParamApplicAmministrazioneEnte(idAmbienteEnteConvenz, idEnteConvenz,
			filterValid);

	if (!editModeAmministrazione) {
	    parametriAmministrazione = obfuscatePasswordParamApplic(parametriAmministrazione);
	}

	getForm().getParametriAmministrazioneList().setTable(parametriAmministrazione);
	getForm().getParametriAmministrazioneList().getTable().setPageSize(300);
	getForm().getParametriAmministrazioneList().getTable().first();
	getForm().getParametriAmministrazioneList().setHideDeleteButton(hideDeleteButtons);
	if (editModeAmministrazione) {
	    getForm().getParametriAmministrazioneList().getDs_valore_param_applic_ente_amm()
		    .setEditMode();
	    getForm().getParametriAmministrazioneList().setStatus(Status.update);
	} else {
	    getForm().getParametriAmministrazioneList().getDs_valore_param_applic_ente_amm()
		    .setViewMode();
	    getForm().getParametriAmministrazioneList().setStatus(Status.view);
	}
    }

    private void loadListaParametriConservazione(BigDecimal idAmbienteEnteConvenz,
	    BigDecimal idEnteConvenz, boolean hideDeleteButtons, boolean editModeConservazione,
	    boolean filterValid) throws ParerUserError {

	IamParamApplicTableBean parametriConservazione = entiConvenzionatiEjb
		.getIamParamApplicConservazioneEnte(idAmbienteEnteConvenz, idEnteConvenz,
			filterValid);

	if (!editModeConservazione) {
	    parametriConservazione = obfuscatePasswordParamApplic(parametriConservazione);
	}

	getForm().getParametriConservazioneList().setTable(parametriConservazione);
	getForm().getParametriConservazioneList().getTable().setPageSize(300);
	getForm().getParametriConservazioneList().getTable().first();
	getForm().getParametriConservazioneList().setHideDeleteButton(hideDeleteButtons);
	if (editModeConservazione) {
	    getForm().getParametriConservazioneList().getDs_valore_param_applic_ente_cons()
		    .setEditMode();
	    getForm().getParametriConservazioneList().setStatus(Status.update);
	} else {
	    getForm().getParametriConservazioneList().getDs_valore_param_applic_ente_cons()
		    .setViewMode();
	    getForm().getParametriConservazioneList().setStatus(Status.view);
	}
    }

    private void loadListaParametriGestione(BigDecimal idAmbienteEnteConvenz,
	    BigDecimal idEnteConvenz, boolean hideDeleteButtons, boolean editModeGestione,
	    boolean filterValid) throws ParerUserError {

	IamParamApplicTableBean parametriGestione = entiConvenzionatiEjb
		.getIamParamApplicGestioneEnte(idAmbienteEnteConvenz, idEnteConvenz, filterValid);

	if (!editModeGestione) {
	    parametriGestione = obfuscatePasswordParamApplic(parametriGestione);
	}

	getForm().getParametriGestioneList().setTable(parametriGestione);
	getForm().getParametriGestioneList().getTable().setPageSize(300);
	getForm().getParametriGestioneList().getTable().first();
	getForm().getParametriGestioneList().setHideDeleteButton(hideDeleteButtons);
	if (editModeGestione) {
	    getForm().getParametriGestioneList().getDs_valore_param_applic_ente_gest()
		    .setEditMode();
	    getForm().getParametriGestioneList().setStatus(Status.update);
	} else {
	    getForm().getParametriGestioneList().getDs_valore_param_applic_ente_gest()
		    .setViewMode();
	    getForm().getParametriGestioneList().setStatus(Status.view);
	}
    }

    // end MEV #32651
}
