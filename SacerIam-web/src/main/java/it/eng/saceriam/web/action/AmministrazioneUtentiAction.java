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

package it.eng.saceriam.web.action;

import java.io.BufferedReader;
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
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.codehaus.jettison.json.JSONException;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.parer.jboss.timer.service.JbossTimerEjb;
import it.eng.parer.sacerlog.ejb.SacerLogEjb;
import it.eng.parer.sacerlog.slite.gen.form.GestioneLogEventiForm;
import it.eng.parer.sacerlog.util.LogParam;
import it.eng.parer.sacerlog.util.web.SpagoliteLogUtil;
import it.eng.saceriam.amministrazioneEntiConvenzionati.ejb.EntiConvenzionatiEjb;
import it.eng.saceriam.common.Constants;
import it.eng.saceriam.entity.constraint.ConstIamParamApplic;
import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam;
import it.eng.saceriam.entity.constraint.ConstUsrAppartUserRich;
import it.eng.saceriam.entity.constraint.ConstUsrRichGestUser;
import it.eng.saceriam.entity.constraint.ConstUsrStatoUser;
import it.eng.saceriam.exception.IncoherenceException;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.exception.ParerWarningException;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.saceriam.slite.gen.Application;
import it.eng.saceriam.slite.gen.action.AmministrazioneUtentiAbstractAction;
import it.eng.saceriam.slite.gen.form.AmministrazioneUtentiForm;
import it.eng.saceriam.slite.gen.form.AmministrazioneUtentiForm.DettaglioUtente;
import it.eng.saceriam.slite.gen.form.AmministrazioneUtentiForm.FiltriJobSchedulati;
import it.eng.saceriam.slite.gen.form.AmministrazioneUtentiForm.FiltriReplica;
import it.eng.saceriam.slite.gen.form.GestioneJobForm;
import it.eng.saceriam.slite.gen.tablebean.AplApplicRowBean;
import it.eng.saceriam.slite.gen.tablebean.AplApplicTableBean;
import it.eng.saceriam.slite.gen.tablebean.AplApplicTableDescriptor;
import it.eng.saceriam.slite.gen.tablebean.AplClasseTipoDatoTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAmbienteEnteConvenzRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteSiamRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteSiamTableBean;
import it.eng.saceriam.slite.gen.tablebean.PrfRuoloRowBean;
import it.eng.saceriam.slite.gen.tablebean.PrfRuoloTableBean;
import it.eng.saceriam.slite.gen.tablebean.PrfRuoloTableDescriptor;
import it.eng.saceriam.slite.gen.tablebean.UsrAppartUserRichRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrAppartUserRichTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrDichAbilDatiRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrDichAbilDatiTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrDichAbilEnteConvenzRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrDichAbilEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrDichAbilOrganizRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrDichAbilOrganizTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrIndIpUserRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrIndIpUserTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrIndIpUserTableDescriptor;
import it.eng.saceriam.slite.gen.tablebean.UsrRichGestUserRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrUserRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrUsoRuoloUserDefaultRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrUsoRuoloUserDefaultTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrUsoUserApplicRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrUsoUserApplicTableBean;
import it.eng.saceriam.slite.gen.viewbean.LogVVisLastSchedRowBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicEnteNonConvenzTableBean;
import it.eng.saceriam.slite.gen.viewbean.PrfVLisRuoloRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVAbilAmbEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVAbilDatiTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVAbilOrganizNolastLivTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVAbilOrganizRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVAbilOrganizTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisAbilEnteTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisAbilOrganizTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisSchedRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisStatoUserTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisStatoUserTableDescriptor;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisUserReplicTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisUserRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisUserTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisUsoRuoloDichRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisUsoRuoloDichTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVRicRichiesteTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVTreeOrganizIamRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVVisDichAbilRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVVisLastRichGestUserRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVVisLastSchedRowBean;
import it.eng.saceriam.util.DateUtil;
import it.eng.saceriam.util.SacerLogConstants;
import it.eng.saceriam.web.dto.PairAuth;
import it.eng.saceriam.web.ejb.AmministrazioneRuoliEjb;
import it.eng.saceriam.web.ejb.AmministrazioneUtentiEjb;
import it.eng.saceriam.web.ejb.AuthEjb;
import it.eng.saceriam.web.ejb.ComboEjb;
import it.eng.saceriam.web.ejb.GestioneJobEjb;
import it.eng.saceriam.web.ejb.SistemiVersantiEjb;
import it.eng.saceriam.web.helper.AmministrazioneUtentiHelper;
import it.eng.saceriam.web.helper.GestioneJobHelper;
import it.eng.saceriam.web.helper.UserHelper;
import it.eng.saceriam.web.util.ActionEnums;
import it.eng.saceriam.web.util.ApplEnum;
import it.eng.saceriam.web.util.ApplEnum.TipoRuolo;
import it.eng.saceriam.web.util.ComboGetter;
import it.eng.saceriam.web.util.WebConstants;
import it.eng.saceriam.web.validator.AmministrazioneUtentiValidator;
import it.eng.saceriam.web.validator.TypeValidator;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.ExecutionHistory;
import it.eng.spagoLite.SessionManager;
import it.eng.spagoLite.actions.form.ListAction;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.base.sorting.SortingRule;
import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.base.table.BaseTable;
import it.eng.spagoLite.db.decodemap.DecodeMapIF;
import it.eng.spagoLite.db.oracle.decode.DecodeMap;
import it.eng.spagoLite.form.base.BaseElements.Status;
import it.eng.spagoLite.form.fields.impl.Button;
import it.eng.spagoLite.form.fields.impl.CheckBox;
import it.eng.spagoLite.form.fields.impl.Input;
import it.eng.spagoLite.message.Message;
import it.eng.spagoLite.message.Message.MessageLevel;
import it.eng.spagoLite.message.MessageBox;
import it.eng.spagoLite.message.MessageBox.ViewMode;
import it.eng.spagoLite.security.Secure;
import it.eng.spagoLite.security.User;

/**
 *
 * @author Gilioli_P feat. Sbonny_I
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AmministrazioneUtentiAction extends AmministrazioneUtentiAbstractAction {

    public static final String FROM_GESTIONE_JOB = "fromGestioneJob";

    private static final Logger log = LoggerFactory.getLogger(AmministrazioneUtentiAction.class);
    public static final String ECCEZIONE_MSG = "Eccezione";
    public static final String FROM_RICERCA_JOB = "fromRicercaJob";
    private static final String WARNING_NO_JOB_SELEZIONATO = "Attenzione: nessun JOB selezionato";

    @EJB(mappedName = "java:app/SacerIam-ejb/AuthEjb")
    private AuthEjb auth;
    @EJB(mappedName = "java:app/SacerIam-ejb/AmministrazioneUtentiHelper")
    private AmministrazioneUtentiHelper amministrazioneUtentiHelper;
    @EJB(mappedName = "java:app/SacerIam-ejb/AmministrazioneUtentiEjb")
    private AmministrazioneUtentiEjb amministrazioneUtentiEjb;
    @EJB(mappedName = "java:app/SacerIam-ejb/AmministrazioneRuoliEjb")
    private AmministrazioneRuoliEjb amministrazioneRuoliEjb;
    @EJB(mappedName = "java:app/SacerIam-ejb/ComboEjb")
    private ComboEjb comboEjb;
    @EJB(mappedName = "java:app/SacerIam-ejb/UserHelper")
    UserHelper userHelper;
    @EJB(mappedName = "java:app/SacerIam-ejb/SistemiVersantiEjb")
    private SistemiVersantiEjb sistemiVersantiEjb;
    @EJB(mappedName = "java:app/SacerIam-ejb/EntiConvenzionatiEjb")
    private EntiConvenzionatiEjb entiConvenzionatiEjb;
    @EJB(mappedName = "java:app/SacerIam-ejb/ParamHelper")
    ParamHelper paramHelper;
    @EJB(mappedName = "java:app/sacerlog-ejb/SacerLogEjb")
    private SacerLogEjb sacerLogEjb;

    @EJB(mappedName = "java:app/JbossTimerWrapper-ejb/JbossTimerEjb")
    private JbossTimerEjb jbossTimerEjb;

    @EJB(mappedName = "java:app/SacerIam-ejb/GestioneJobEjb")
    private GestioneJobEjb gestioneJobEjb;

    @EJB(mappedName = "java:app/SacerIam-ejb/GestioneJobHelper")
    private GestioneJobHelper gestioneJobHelper;

    private static final String IP_ADDRESS = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    private static final String USERNAME = "^[a-zA-Z0-9]+([_\\.\\-]*[a-zA-Z0-9])*$";

    private static final String PASSWORD_REGEX = "(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!-/:-@\\[-`{-~])[A-Za-z\\d!-/:-@\\[-`{-~]{8,}$";
    private static final String SLASH_FORMAT = IP_ADDRESS.substring(0, IP_ADDRESS.length() - 1)
            + "/([1-9]{1}|[1-2][0-9]?|3[0-2])$";
    private static final Pattern addressPattern = Pattern.compile(IP_ADDRESS);
    private static final Pattern cidrPattern = Pattern.compile(SLASH_FORMAT);
    private static final Pattern usernamePattern = Pattern.compile(USERNAME);

    /*
     * Inserisce in sessione l'insieme degli indirizzi IP gestiti dall'utente
     */
    private void populateIndIpSet(UsrIndIpUserTableBean indIpTB) throws EMFError {
        Set<String> indIpSet = new HashSet<>();
        for (UsrIndIpUserRowBean indIpRB : indIpTB) {
            indIpSet.add(indIpRB.getCdIndIpUser());
        }
        getSession().setAttribute("indIpSet", indIpSet);
    }

    /*
     * Inserisce in sessione l'insieme delle applicazioni gestite dall'utente
     */
    private void populateApplicationSet(UsrUsoUserApplicTableBean applicTB) throws EMFError {
        Set<BigDecimal> applicationsSet = new HashSet<BigDecimal>();
        for (UsrUsoUserApplicRowBean applicRB : applicTB) {
            applicationsSet.add(applicRB.getIdApplic());
        }
        getSession().setAttribute("applicationsSet", applicationsSet);
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

    /*
     * Getter per mantenere un set di indirizzi IP, utile per i controlli di coerenza
     *
     * @return il set di indirizzi IP sotto forma di stringa, presenti in sessione
     */
    private Set<String> getIndIpSet() {
        if (getSession().getAttribute("indIpSet") == null) {
            getSession().setAttribute("indIpSet", new HashSet<String>());
        }
        return (Set<String>) getSession().getAttribute("indIpSet");
    }

    /*
     * Getter per mantenere un set di idRuoli, utile per i controlli di coerenza
     *
     * @return il set di ruoli presenti in sessione
     */
    private Set<BigDecimal> getRolesSet(String applicazione) {
        if (getSession().getAttribute("rolesSet_" + applicazione) == null) {
            getSession().setAttribute("rolesSet_" + applicazione, new HashSet<BigDecimal>());
        }
        return (Set<BigDecimal>) getSession().getAttribute("rolesSet_" + applicazione);
    }

    /*
     * Getter per mantenere un set di coppie di dati (SCOPO,ABILITAZIONE) per ogni applicazione gestita dall'utente,
     * utile per i controlli di coerenza
     *
     * @return
     */
    private Set<PairAuth> getScopoOrgSet(String applicazione) {
        if (getSession().getAttribute("scopoOrgSet_" + applicazione) == null) {
            getSession().setAttribute("scopoOrgSet_" + applicazione, new HashSet<PairAuth>());
        }
        return (Set<PairAuth>) getSession().getAttribute("scopoOrgSet_" + applicazione);
    }

    /*
     * Getter per mantenere un set di coppie di dati (SCOPO,ABILITAZIONE) per ogni applicazione gestita dall'utente,
     * utile per i controlli di coerenza
     *
     * @return
     */
    private Set<PairAuth> getScopoTipiDatoSet(String applicazione) {
        if (getSession().getAttribute("scopoTipiDatoSet_" + applicazione) == null) {
            getSession().setAttribute("scopoTipiDatoSet_" + applicazione, new HashSet<PairAuth>());
        }
        return (Set<PairAuth>) getSession().getAttribute("scopoTipiDatoSet_" + applicazione);
    }

    /*
     * Getter per mantenere un set di coppie di dati (SCOPO,ABILITAZIONE) per gli enti convenzionati abilitati, utile
     * per i controlli di coerenza
     *
     * @return
     */
    private Set<PairAuth> getScopoEnteConvenzSet() {
        if (getSession().getAttribute("scopoEnteConvenzSet") == null) {
            getSession().setAttribute("scopoEnteConvenzSet", new HashSet<>());
        }
        return (Set<PairAuth>) getSession().getAttribute("scopoEnteConvenzSet");
    }

    /*
     * Getter per mantenere un set di idRuoli, utile per i controlli di coerenza su Ruoli per Organizzazioni
     *
     * @return il set di ruoli presenti in sessione
     */
    private Set<BigDecimal> getOrgRolesSet() {
        if (getSession().getAttribute("orgRolesSet") == null) {
            getSession().setAttribute("orgRolesSet", new HashSet<BigDecimal>());
        }
        return (Set<BigDecimal>) getSession().getAttribute("orgRolesSet");
    }

    /*
     * Metodo che mantiene in sessione il set di idIndIpUser per cancellare gli indirizzi IP che sono state selezionati
     * per l'eliminazione dalla lista.
     *
     * @return il set di applicazioni salvato in sessione
     */
    private Set<BigDecimal> getIndIpDeleteList() {
        if (getSession().getAttribute("indIpDeleteList") == null) {
            getSession().setAttribute("indIpDeleteList", new HashSet<BigDecimal>());
        }
        return (Set<BigDecimal>) getSession().getAttribute("indIpDeleteList");
    }

    /*
     * Metodo che mantiene in sessione il set di idUsoUserApplic per cancellare le applicazioni che sono state
     * selezionate per l'eliminazione dalla lista.
     *
     * @return il set di applicazioni salvato in sessione
     */
    private Set<BigDecimal> getApplicationsDeleteList() {
        if (getSession().getAttribute("applicationsDeleteList") == null) {
            getSession().setAttribute("applicationsDeleteList", new HashSet<BigDecimal>());
        }
        return (Set<BigDecimal>) getSession().getAttribute("applicationsDeleteList");
    }

    /*
     * Metodo che mantiene in sessione il set di idUsoUserApplic per replicare solo le applicazioni che sono state
     * modificate
     *
     * @return il set di applicazioni salvato in sessione
     */
    private Set<BigDecimal> getApplicationsEditList() {
        if (getSession().getAttribute("applicationsEditList") == null) {
            getSession().setAttribute("applicationsEditList", new HashSet<BigDecimal>());
        }
        return (Set<BigDecimal>) getSession().getAttribute("applicationsEditList");
    }

    /*
     * Metodo che mantiene in sessione il set di id dei ruoli di default che sono stati selezionati per l'eliminazione
     * dalla lista.
     *
     * @return il set di ruoli salvato in sessione
     */
    private Set<BigDecimal> getDefaultRolesDeleteList() {
        if (getSession().getAttribute("defaultRolesDeleteList") == null) {
            getSession().setAttribute("defaultRolesDeleteList", new HashSet<BigDecimal>());
        }
        return (Set<BigDecimal>) getSession().getAttribute("defaultRolesDeleteList");
    }

    /*
     * Metodo che mantiene in sessione il set di id delle dichiarazioni di organizzazione che sono state selezionate per
     * l'eliminazione dalla lista.
     *
     * @return il set di dichiarazioni salvato in sessione
     */
    private Set<BigDecimal> getDichOrganizDeleteList() {
        if (getSession().getAttribute("dichOrganizDeleteList") == null) {
            getSession().setAttribute("dichOrganizDeleteList", new HashSet<BigDecimal>());
        }
        return (Set<BigDecimal>) getSession().getAttribute("dichOrganizDeleteList");
    }

    /*
     * Metodo che mantiene in sessione il set di id delle dichiarazioni di tipi dato che sono state selezionate per
     * l'eliminazione dalla lista.
     *
     * @return il set di dichiarazioni salvato in sessione
     */
    private Set<BigDecimal> getDichTipiDatoDeleteList() {
        if (getSession().getAttribute("dichTipiDatoDeleteList") == null) {
            getSession().setAttribute("dichTipiDatoDeleteList", new HashSet<BigDecimal>());
        }
        return (Set<BigDecimal>) getSession().getAttribute("dichTipiDatoDeleteList");
    }

    /*
     * Metodo che mantiene in sessione il set di id delle dichiarazioni di abilitazione degli enti convenzionati che
     * sono state selezionate per l'eliminazione dalla lista.
     *
     * @return il set di dichiarazioni salvato in sessione
     */
    private Set<BigDecimal> getDichAbilEnteConvenzDeleteList() {
        if (getSession().getAttribute("dichAbilEnteConvenzDeleteList") == null) {
            getSession().setAttribute("dichAbilEnteConvenzDeleteList", new HashSet<BigDecimal>());
        }
        return (Set<BigDecimal>) getSession().getAttribute("dichAbilEnteConvenzDeleteList");
    }

    /*
     * Metodo che mantiene in sessione il set di id dei ruoli dichiarati per la struttura che sono stati selezionati per
     * l'eliminazione dalla lista.
     *
     * @return il set di dichiarazioni salvato in sessione
     */
    private Set<BigDecimal> getOrgRolesDeleteList() {
        if (getSession().getAttribute("orgRolesDeleteList") == null) {
            getSession().setAttribute("orgRolesDeleteList", new HashSet<BigDecimal>());
        }
        return (Set<BigDecimal>) getSession().getAttribute("orgRolesDeleteList");
    }

    @Override
    public void process() throws EMFError {
        boolean isMultipart = ServletFileUpload.isMultipartContent(getRequest());
        if (isMultipart) {
            int size10Mb = 10 * 1024 * 1024;
            String[] paramMethods = null;
            try {
                if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_RICHIESTA)) {
                    paramMethods = getForm().getRichiestaDetail().postMultipart(getRequest(), size10Mb);
                }
                if (paramMethods != null) {
                    String operationMethod = paramMethods[0];
                    if (paramMethods.length > 1) {
                        String[] navigationParams = Arrays.copyOfRange(paramMethods, 1, paramMethods.length);

                        Method method = AmministrazioneUtentiAction.class.getMethod(operationMethod, String[].class);
                        method.invoke(this, (Object) navigationParams);
                    } else {
                        Method method = AmministrazioneUtentiAction.class.getMethod(operationMethod);
                        method.invoke(this);
                    }
                }

            } catch (FileUploadException | NoSuchMethodException | SecurityException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                log.error("Errore nell'invocazione del metodo :" + ExceptionUtils.getRootCauseMessage(ex), ex);
                getMessageBox().addError("Errore inatteso nella procedura invocata");
                forwardToPublisher(getLastPublisher());
            }
        }
    }

    @Override
    public void initOnClick() throws EMFError {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Metodo utilizzato per impostare il dettaglio della lista utenti. Esegue la forward alla jsp e modifica lo stato
     * dei campi
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public void dettaglioOnClick() throws EMFError {
        /* Controllo per quale tabella è stato invocato il metodo */
        if (getTableName() != null) {
            // DETTAGLIO da LISTA UTENTI
            if (getTableName().equals(getForm().getListaUtenti().getName())
                    && (getNavigationEvent().equals(NE_DETTAGLIO_VIEW) || getNavigationEvent().equals(NE_NEXT)
                            || getNavigationEvent().equals(NE_PREV))) {
                forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE);
                setDettaglioUtentiToViewMode();
            } // INSERT da LISTA UTENTI
            else if ((getTableName().equals(getForm().getListaUtenti().getName())
                    || getTableName().equals(getForm().getDettaglioUtente().getName()))
                    && (getNavigationEvent().equals(NE_DETTAGLIO_UPDATE)
                            || getNavigationEvent().equals(NE_DETTAGLIO_INSERT))) {

                getForm().getRichiestaWizard().setEditMode();
                getForm().getDettaglioUtente().setEditMode();
                getForm().getDichAbilFields().getAdd_row_to_list().setViewMode();
                getSession().removeAttribute("passwordUtente");
                getSession().removeAttribute("userType");
                /* Resetta il wizard */
                getForm().getInserimentoWizard().reset();
                forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
            } // DETTAGLIO da LISTA ABILITAZIONI SULLE ORGANIZZAZIONI (richiamo il dettaglio ruoli specifici per
              // organizzazioni)
            else if (getTableName().equals(getForm().getDichAbilOrgList().getName())
                    && !getNavigationEvent().equals(NE_DETTAGLIO_DELETE)) {
                /* Se la dichiarazione e' ALL_ORG non deve essere visualizzata la lista per organizzazioni */
                if (!getForm().getDichAbilOrgList().getTable().getCurrentRow().getString("ti_scopo_dich_abil_organiz")
                        .equals(ActionEnums.ScopoDichAbilOrganiz.ALL_ORG.name())) {

                    getForm().getGestioneRuoliOrganizzazione().getTi_scopo_ruolo().setHidden(true);
                    getForm().getGestioneRuoliOrganizzazione().getDl_composito_organiz_ruolo().setHidden(true);
                    getForm().getGestioneRuoliOrganizzazione().getNm_ruolo().setHidden(true);
                    getForm().getGestioneRuoliOrganizzazione().getDs_ruolo().setHidden(true);

                    // In fase di visualizzazione, inibisco la possibilità di cancellare un ruolo
                    getForm().getRuoliList().setHideDeleteButton(true);

                    forwardToPublisher(Application.Publisher.LISTA_RUOLI_ORGANIZZAZIONE);
                } else {
                    getMessageBox().addInfo(
                            "Non è possibile visualizzare la lista ruoli per organizzazioni di un'abilitazione con scopo ALL_ORG");
                }
            } else if (getTableName().equals(getForm().getRichiesteList().getName())
                    && !getNavigationEvent().equals(NE_DETTAGLIO_UPDATE)) {
                forwardToPublisher(Application.Publisher.DETTAGLIO_RICHIESTA);
            } else if (getTableName().equals(getForm().getAzioniList().getName())
                    && !getNavigationEvent().equals(NE_DETTAGLIO_DELETE)) {
                forwardToPublisher(Application.Publisher.DETTAGLIO_AZIONE);
            }
        }
    }

    /**
     * Metodo utilizzato per carico il dettaglio con i dati
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public void loadDettaglio() throws EMFError {
        if (getNavigationEvent() != null && (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW)
                || getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)
                || getNavigationEvent().equals(ListAction.NE_NEXT)
                || getNavigationEvent().equals(ListAction.NE_PREV))) {
            /* Controllo per quale tabella è stato invocato il metodo */
            if (getTableName() != null) {
                /* Lista utenti - carico il dettaglio utente */
                if (getTableName().equals(getForm().getListaUtenti().getName())
                        && !getForm().getListaUtenti().getTable().isEmpty()) {
                    getForm().getDettaglioUtente().getCd_psw().setHidden(true);
                    getSession().removeAttribute("passwordUtente");
                    getSession().removeAttribute("userType");
                    getSession().removeAttribute("idRichiesta");
                    loadDettaglioUtente(null);
                    setVisibilityAbilitaDisabilitaUtente(
                            ((UsrVLisUserRowBean) getForm().getListaUtenti().getTable().getCurrentRow())
                                    .getIdUserIam());
                } /*
                   * DETTAGLIO da LISTA ABILITAZIONI SULLE ORGANIZZAZIONI (richiamo Gestione ruoli specifici per
                   * organizzazioni)
                   */ else if (getTableName().equals(getForm().getDichAbilOrgList().getName())
                        && !getNavigationEvent().equals(NE_DETTAGLIO_INSERT)) {
                    /* Se la dichiarazione è ALL_ORG non deve essere visualizzata la lista per organizzazioni */
                    if (!getForm().getDichAbilOrgList().getTable().getCurrentRow()
                            .getString("ti_scopo_dich_abil_organiz")
                            .equals(ActionEnums.ScopoDichAbilOrganiz.ALL_ORG.name())) {

                        /*
                         * Lista dichiarazioni alle organizzazioni - carico la pagina di gestione ruoli specifici per
                         * organizzazioni
                         */
                        BigDecimal idDichAbilOrg = getForm().getDichAbilOrgList().getTable().getCurrentRow()
                                .getBigDecimal("id_dich_abil_organiz");
                        UsrVVisDichAbilRowBean rb = amministrazioneUtentiEjb.getUsrVVisDichAbilRowBean(idDichAbilOrg);
                        getForm().getGestioneRuoliOrganizzazione().copyFromBean(rb);

                        /* Recupero idOrganizIam a seconda dello scopo della dichiarazione */
                        BigDecimal idOrganizIam = getForm().getDichAbilOrgList().getTable().getCurrentRow()
                                .getBigDecimal("id_organiz_iam");
                        ;
                        if (getForm().getDichAbilOrgList().getTable().getCurrentRow()
                                .getString("ti_scopo_dich_abil_organiz")
                                .equals(ActionEnums.ScopoDichAbilOrganiz.ALL_ORG_CHILD.name())) {
                            idOrganizIam = amministrazioneUtentiEjb.getOrganizIamChilds(idOrganizIam).getRow(0)
                                    .getIdOrganizIam();
                        }
                        getForm().getGestioneRuoliOrganizzazione().getId_organiz_iam_dich().setValue("" + idOrganizIam);

                        // Carico la lista dei ruoli
                        UsrVLisUsoRuoloDichTableBean lisUsoRuoloDich = amministrazioneUtentiEjb
                                .getUsrVLisUsoRuoloDichTableBean(idDichAbilOrg);
                        getForm().getRuoliList().setTable(lisUsoRuoloDich);
                        getForm().getRuoliList().getTable().setPageSize(10);
                        getForm().getRuoliList().getTable().first();
                        //
                        populateRuoliPerOrganizzazioniSet(lisUsoRuoloDich);
                    }
                } else if (getTableName().equals(getForm().getRichiesteList().getName())
                        && !getNavigationEvent().equals(NE_DETTAGLIO_INSERT)) {
                    initRichiestaDetail();
                    BigDecimal idRichGestUser = ((BaseRowInterface) getForm().getRichiesteList().getTable()
                            .getCurrentRow()).getBigDecimal("id_rich_gest_user");
                    loadDettaglioRichiesta(idRichGestUser);
                    // Se la richiesta ha stato DA_COMPLETARE attivo il bottone COMPLETATA

                } else if (getTableName().equals(getForm().getAzioniList().getName())
                        && !getNavigationEvent().equals(NE_DETTAGLIO_INSERT)) {
                    // Carico il dettaglio dell'azione
                    BigDecimal idAppartGestUser = ((UsrAppartUserRichRowBean) getForm().getAzioniList().getTable()
                            .getCurrentRow()).getIdAppartUserRich();
                    loadDettaglioAzione(idAppartGestUser);
                }
            }
        }
    }

    public void loadDettaglioRichiesta(BigDecimal idRichGestUser) throws EMFError {
        // Azzero eventuali modifiche di visibilità
        getForm().getIdentificativoUdRichSection().setHidden(false);
        getForm().getRichiestaDetail().getNm_userid_manuale().setHidden(false);
        getForm().getRichiestaDetail().getNm_userid_codificato().setHidden(false);
        getForm().getRichiestaDetail().getDs_email_rich().setHidden(false);
        // Carico il dettaglio della richiesta
        UsrRichGestUserRowBean rb = amministrazioneUtentiEjb.getUsrRichGestUserRowBean(idRichGestUser);
        getForm().getRichiestaDetail().copyFromBean(rb);
        // Carico la lista delle azioni
        getForm().getAzioniList().setTable(
                amministrazioneUtentiEjb.getUsrAppartUserRichTableBean(idRichGestUser, rb.getTiStatoRichGestUser()));
        getForm().getAzioniList().getTable().addSortingRule("nm_cognome_user", SortingRule.ASC);
        getForm().getAzioniList().getTable().addSortingRule("nm_nome_user", SortingRule.ASC);
        getForm().getAzioniList().getTable().sort();
        getForm().getAzioniList().getTable().setPageSize(10);
        getForm().getAzioniList().getTable().first();

        // Popolo la combo dei registri in base all'eventuale organizzazione selezionata
        if (rb.getIdOrganizIam() != null) {
            BigDecimal idApplicSacer = new BigDecimal(userHelper.getAplApplic("SACER").getIdApplic());
            UsrVAbilDatiTableBean usrTipoDatoIam = amministrazioneUtentiEjb
                    .getUsrVAbilDatiTableBean(getUser().getIdUtente(), "REGISTRO", idApplicSacer, rb.getIdOrganizIam());
            DecodeMap mappa = new DecodeMap();
            mappa.populatedMap(usrTipoDatoIam, "nm_tipo_dato", "nm_tipo_dato");
            getForm().getRichiestaDetail().getCd_registro_rich_gest_user().setDecodeMap(mappa);
            getForm().getRichiestaDetail().getCd_registro_rich_gest_user().setValue(rb.getCdRegistroRichGestUser());
        }

        getForm().getRichiestaDetail().setViewMode();
        // Inizializzo i bottoni in edit mode
        getForm().getRichiestaDetail().getLogEventiRichiesta().setEditMode();
        getForm().getRichiestaDetail().getScaricaFileRichiesta().setDisableHourGlass(true);
        getForm().getRichiestaDetail().getScaricaFileRichiesta().setEditMode();
        getForm().getRichiestaDetail().getRichiestaCompletata().setEditMode();
        // Visualizzazione del bottone Scarica file richiesta
        if (rb.getNmFileRichGestUser() != null) {
            getForm().getRichiestaDetail().getScaricaFileRichiesta().setHidden(false);
        } else {
            getForm().getRichiestaDetail().getScaricaFileRichiesta().setHidden(true);
        }

        // Se la richiesta ha stato DA_COMPLETARE, l'amministratore può decidere di definire che la richiesta è
        // completata
        if (rb.getTiStatoRichGestUser().equals(ConstUsrRichGestUser.TiStatoRichGestUser.DA_COMPLETARE.name())) {
            getForm().getRichiestaDetail().getRichiestaCompletata().setHidden(false);
        } else {
            getForm().getRichiestaDetail().getRichiestaCompletata().setHidden(true);
        }

        getForm().getRichiestaDetail().getLogEventiRichiesta().setHidden(false);

        // Gestione ente e ambiente
        BigDecimal idEnteSiam = rb.getIdEnteRich();
        OrgAmbienteEnteConvenzRowBean ambienteEnteConvenz = entiConvenzionatiEjb
                .getOrgAmbienteEnteConvenzByEnteConvenz(idEnteSiam);
        if (ambienteEnteConvenz.getIdAmbienteEnteConvenz() != null) {
            // Seleziono il valore della combo ambiente con l'ambiente dell'ente
            getForm().getRichiestaDetail().getId_ambiente_ente_convenz()
                    .setValue("" + ambienteEnteConvenz.getIdAmbienteEnteConvenz());
        }
        DecodeMap mappa = new DecodeMap();
        if (amministrazioneUtentiEjb.getTipoEnte(idEnteSiam).equals("CONVENZIONATO")) {
            OrgVRicEnteConvenzTableBean ricEnteConvenz = entiConvenzionatiEjb.getOrgVRicEnteConvenzAbilTableBean(
                    BigDecimal.valueOf(getUser().getIdUtente()), ambienteEnteConvenz.getIdAmbienteEnteConvenz(), null);
            mappa.populatedMap(ricEnteConvenz, "id_ente_convenz", "nm_ente_convenz");
        } else {
            OrgVRicEnteNonConvenzTableBean ricEnteNonConvenz = entiConvenzionatiEjb
                    .getOrgVRicEnteNonConvenzAbilTableBean(BigDecimal.valueOf(getUser().getIdUtente()), null);
            mappa.populatedMap(ricEnteNonConvenz, "id_ente_siam", "nm_ente_siam");
        }

        getForm().getRichiestaDetail().getId_ente_rich().setDecodeMap(mappa);
        getForm().getRichiestaDetail().getId_ente_rich().setValue("" + rb.getIdEnteRich());

        getForm().getRichiestaDetail().setStatus(Status.view);
        getForm().getRichiesteList().setStatus(Status.view);
    }

    public void loadDettaglioAzione(BigDecimal idAppartRichUser) throws EMFError {
        // Carico il dettaglio dell'azione
        UsrAppartUserRichRowBean rb = amministrazioneUtentiEjb.getUsrAppartUserRichRowBean(idAppartRichUser);
        BaseTable ambientiTB = new BaseTable();
        BaseTable entiTB = new BaseTable();
        try {
            OrgEnteSiamRowBean enteSiamAppartRowBean = entiConvenzionatiEjb
                    .getOrgEnteConvenzRowBean(rb.getIdEnteUser());
            // Popola la combo dei Tipi Azione
            BigDecimal idEnteRich = getForm().getRichiestaDetail().getId_ente_rich().parse();
            String tiEnte = amministrazioneUtentiEjb.getTipoEnte(idEnteRich);
            getForm().getAzioneDetail().getTi_azione_rich().setDecodeMap(ComboGetter.getMappaTiAzioneRich(tiEnte));
            // Popola la combo ambiente ed eventualmente ente
            populateAzioneAmbienteEnteAppart(rb.getTiAzioneRich(), rb.getIdRichGestUser(), ambientiTB, entiTB);
            // Se ho almeno un ambiente nella combo e l'avevo selezionato in precedenza, popolo gli enti
            if (!getForm().getAzioneDetail().getId_ambiente_ente_user().getDecodeMap().isEmpty()
                    && enteSiamAppartRowBean.getIdAmbienteEnteConvenz() != null) {
                populateAzioneEnteAppart(rb.getTiAzioneRich(), rb.getIdRichGestUser(),
                        enteSiamAppartRowBean.getIdAmbienteEnteConvenz(), entiTB);
            }
        } catch (ParerUserError ex) {
            getMessageBox().addError(ex.getDescription());
        }

        getForm().getAzioneDetail().copyFromBean(rb);
        getForm().getAzioneDetail().setViewMode();
        getForm().getAzioneDetail().setStatus(Status.view);
        getForm().getAzioniList().setStatus(Status.view);
    }

    /**
     * Metodo che esegue il caricamento dei dati dell'utente scelto nella lista utenti
     *
     * @param idUser
     *            id user
     *
     * @throws EMFError
     *             errore generico
     */
    public void loadDettaglioUtente(BigDecimal idUser) throws EMFError {
        try {
            UsrUserRowBean utente;
            if (idUser != null) {
                utente = amministrazioneUtentiEjb.getUserRowBean(idUser);
                // Ricavo l'indice di riga nel quale si trova il record nella tabella a video
                int count = -1;
                boolean rigaTrovata = false;
                if (getForm().getListaUtenti().getTable() != null) {
                    for (UsrVLisUserRowBean riga : (UsrVLisUserTableBean) getForm().getListaUtenti().getTable()) {
                        count++;
                        if (riga.getIdUserIam().compareTo(idUser) == 0) {
                            rigaTrovata = true;
                            break;
                        }
                    }
                }
                if (!rigaTrovata) {
                    UsrVLisUserTableBean table = new UsrVLisUserTableBean();
                    table.add(utente);
                    getForm().getListaUtenti().setTable(table);
                    count = table.getCurrentRowIndex();
                }
                getForm().getListaUtenti().getTable().setCurrentRowIndex(count);
            } else {
                idUser = ((UsrVLisUserRowBean) getForm().getListaUtenti().getTable().getCurrentRow()).getIdUserIam();
                utente = amministrazioneUtentiEjb.getUserRowBean(idUser);
            }
            DecodeMapIF combo = ComboGetter.getMappaGenericFlagSiNo();
            getForm().getDettaglioUtente().getFl_attivo().setDecodeMap(combo);
            getForm().getDettaglioUtente().getFl_err_replic().setDecodeMap(combo);
            getForm().getDettaglioUtente().getFl_contr_ip().setDecodeMap(combo);
            getForm().getDettaglioUtente().getFl_resp_ente_convenz().setDecodeMap(combo);

            getForm().getDettaglioUtente().getTipo_user().setDecodeMap(
                    ComboGetter.getMappaSortedGenericEnum("tipo_user", ApplEnum.TipoUser.getComboTipiTotali()));
            getForm().getDettaglioUtente().getTipo_auth().setDecodeMap(
                    ComboGetter.getMappaSortedGenericEnum("tipo_auth", ApplEnum.TipoAuth.getComboTipiAuth()));

            String tipoUser = utente.getTipoUser();
            BigDecimal idEnteUser = utente.getIdEnteSiam();
            DecodeMap mappaSistemiVersanti = new DecodeMap();
            mappaSistemiVersanti.populatedMap(
                    sistemiVersantiEjb.getAplSistemaVersanteValidiTableBean(tipoUser, idEnteUser),
                    "id_sistema_versante", "nm_sistema_versante");
            getForm().getDettaglioUtente().getId_sistema_versante().setDecodeMap(mappaSistemiVersanti);

            getForm().getDettaglioUtente().copyFromBean(utente);

            getForm().getDettaglioUtente().getId_ente_siam_appart().setValue("" + utente.getIdEnteSiam());

            BigDecimal idAmbienteEnteConvenzAppart = entiConvenzionatiEjb
                    .getOrgAmbienteEnteConvenzByEnteConvenz(utente.getIdEnteSiam()).getIdAmbienteEnteConvenz();

            try {
                if (idEnteUser != null) {
                    OrgEnteSiamRowBean ente = entiConvenzionatiEjb.getOrgEnteConvenzRowBean(idEnteUser);
                    setFlagUtente(ente.getTiEnte(), ente.getTiEnteNonConvenz(), tipoUser);
                    getForm().getDettaglioUtente().getFl_resp_ente_convenz().setValue(utente.getFlRespEnteConvenz());
                    getForm().getDettaglioUtente().getFl_abil_enti_colleg_autom()
                            .setValue(utente.getFlAbilEntiCollegAutom());
                    getForm().getDettaglioUtente().getFl_abil_organiz_autom().setValue(utente.getFlAbilOrganizAutom());
                    getForm().getDettaglioUtente().getFl_abil_fornit_autom().setValue(utente.getFlAbilFornitAutom());
                }
            } catch (ParerUserError ex) {
                getMessageBox().addError(ex.getDescription());
            }

            // Combo ambiente ente convenzionato
            if (idAmbienteEnteConvenzAppart != null) {
                UsrVAbilAmbEnteConvenzTableBean abilAmbEnteConvenzTableBean = entiConvenzionatiEjb
                        .getUsrVAbilAmbEnteConvenzTableBean(new BigDecimal(getUser().getIdUtente()));
                getForm().getDettaglioUtente().getId_amb_ente_convenz_appart()
                        .setDecodeMap(DecodeMap.Factory.newInstance(abilAmbEnteConvenzTableBean,
                                "id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));
                getForm().getDettaglioUtente().getId_amb_ente_convenz_appart()
                        .setValue("" + idAmbienteEnteConvenzAppart);
            } else {
                getForm().getDettaglioUtente().getId_amb_ente_convenz_appart().setDecodeMap(new DecodeMap());
            }

            // Combo ente
            DecodeMap mappa = new DecodeMap();
            if (idAmbienteEnteConvenzAppart != null) {
                OrgVRicEnteConvenzTableBean ricEnteConvenz = entiConvenzionatiEjb.getOrgVRicEnteConvenzAbilTableBean(
                        BigDecimal.valueOf(getUser().getIdUtente()), idAmbienteEnteConvenzAppart, null);
                mappa.populatedMap(ricEnteConvenz, "id_ente_convenz", "nm_ente_convenz");
            } else {
                OrgVRicEnteNonConvenzTableBean ricEnteNonConvenz = entiConvenzionatiEjb
                        .getOrgVRicEnteNonConvenzAbilTableBean(BigDecimal.valueOf(getUser().getIdUtente()), null);
                mappa.populatedMap(ricEnteNonConvenz, "id_ente_siam", "nm_ente_siam");
            }
            getForm().getDettaglioUtente().getId_ente_siam_appart().setDecodeMap(mappa);
            getForm().getDettaglioUtente().getId_ente_siam_appart().setValue("" + utente.getIdEnteSiam());

            /* Setto il valore del flag errori di replica */
            if (amministrazioneUtentiEjb.hasErroriReplica(idUser)) {
                getForm().getDettaglioUtente().getFl_err_replic().setValue("1");
            } else {
                getForm().getDettaglioUtente().getFl_err_replic().setValue("0");
            }

            /* Setto il valore della/e qualifica/he */
            getForm().getDettaglioUtente().getQualifica_user().setHidden(false);
            String qualificaUser = amministrazioneUtentiEjb.getQualificaUser(idUser.longValue());
            getForm().getDettaglioUtente().getQualifica_user().setValue(qualificaUser);

            UsrVVisLastRichGestUserRowBean usrVVisLastRichGestUserRowBean = amministrazioneUtentiEjb
                    .getUsrVVisLastRichGestUserRowBean(idUser);
            if (usrVVisLastRichGestUserRowBean != null) {
                getForm().getDettaglioUltimaRichiestaUtente().copyFromBean(usrVVisLastRichGestUserRowBean);
            } else {
                getForm().getDettaglioUltimaRichiestaUtente().clear();
            }

            getForm().getIndIpList().clear();
            getForm().getApplicazioniList().clear();
            getForm().getRuoliDefaultList().clear();
            getForm().getDichAbilOrgList().clear();
            getForm().getDichAbilTipiDatoList().clear();
            getForm().getDichAbilEnteConvenzList().clear();

            // Abilito o meno la tab degli enti convenzionati
            abilitaEnteConvenzTab(utente.getIdEnteSiam(), utente.getTipoUser());

            // Carico la lista degli indirizzi IP dell'utente
            UsrIndIpUserTableBean uiiuTB = amministrazioneUtentiEjb.getUsrIndIpUserTableBean(utente.getIdUserIam());
            getForm().getIndIpList().setTable(uiiuTB);
            getForm().getIndIpList().getTable().setPageSize(10);
            getForm().getIndIpList().getTable().first();

            // Carico la lista delle applicazioni dell'utente
            UsrUsoUserApplicTableBean uuuaTB = amministrazioneUtentiEjb
                    .getUsoUsoUserApplicTableBean(utente.getIdUserIam());
            getForm().getApplicazioniList().setTable(uuuaTB);
            getForm().getApplicazioniList().getTable().setPageSize(10);
            getForm().getApplicazioniList().getTable().first();

            Set<BigDecimal> uuuaSet = new HashSet();
            for (UsrUsoUserApplicRowBean rb : uuuaTB) {
                uuuaSet.add(rb.getIdUsoUserApplic());
            }

            // Carico la lista dei ruoli di default utilizzati dall'utente
            UsrUsoRuoloUserDefaultTableBean ruoliDefault = amministrazioneUtentiEjb
                    .getUsrUsoRuoloUserDefaultTableBean(uuuaSet);
            getForm().getRuoliDefaultList().setTable(ruoliDefault);
            getForm().getRuoliDefaultList().getTable().setPageSize(10);
            getForm().getRuoliDefaultList().getTable().addSortingRule(PrfRuoloTableDescriptor.COL_NM_RUOLO,
                    SortingRule.ASC);
            getForm().getRuoliDefaultList().getTable().sort();
            getForm().getRuoliDefaultList().getTable().first();

            // Carico la lista delle dichiarazioni di abilitazione sulle organizzazioni
            UsrDichAbilOrganizTableBean dichAbilOrg = amministrazioneUtentiEjb.getUsrDichAbilOrganizTableBean(uuuaSet);
            getForm().getDichAbilOrgList().setTable(dichAbilOrg);
            getForm().getDichAbilOrgList().getTable().addSortingRule("nm_applic", SortingRule.ASC);
            getForm().getDichAbilOrgList().getTable().addSortingRule("dl_composito_organiz", SortingRule.ASC);
            getForm().getDichAbilOrgList().getTable().sort();
            getForm().getDichAbilOrgList().getTable().setPageSize(10);
            getForm().getDichAbilOrgList().getTable().first();

            // Carico la lista delle abilitazioni sui tipi dato
            UsrDichAbilDatiTableBean dichAbilTipiDato = amministrazioneUtentiEjb.getUsrDichAbilDatiTableBean(uuuaSet);
            getForm().getDichAbilTipiDatoList().setTable(dichAbilTipiDato);
            getForm().getDichAbilTipiDatoList().getTable().setPageSize(10);
            getForm().getDichAbilTipiDatoList().getTable().addSortingRule("nm_applic", SortingRule.ASC);
            getForm().getDichAbilTipiDatoList().getTable().addSortingRule("dl_composito_organiz", SortingRule.ASC);
            getForm().getDichAbilTipiDatoList().getTable().addSortingRule("nm_classe_tipo_dato", SortingRule.ASC);
            getForm().getDichAbilTipiDatoList().getTable().addSortingRule("nm_tipo_dato", SortingRule.ASC);
            getForm().getDichAbilTipiDatoList().getTable().sort();
            getForm().getDichAbilTipiDatoList().getTable().first();

            // Carico la lista delle abilitazioni sugli enti convenzionati
            UsrDichAbilEnteConvenzTableBean dichAbilEnteConvenz = amministrazioneUtentiEjb
                    .getUsrDichAbilEnteConvenzTableBean(idUser);
            getForm().getDichAbilEnteConvenzList().setTable(dichAbilEnteConvenz);
            getForm().getDichAbilEnteConvenzList().getTable().setPageSize(10);
            getForm().getDichAbilEnteConvenzList().getTable().addSortingRule("nm_ambiente_ente_convenz",
                    SortingRule.ASC);
            getForm().getDichAbilEnteConvenzList().getTable().addSortingRule("nm_ente_convenz", SortingRule.ASC);
            getForm().getDichAbilEnteConvenzList().getTable().sort();
            getForm().getDichAbilEnteConvenzList().getTable().first();

            // Carico la lista degli stati assunti dall'ente
            UsrVLisStatoUserTableBean statoUser = amministrazioneUtentiEjb.getUsrStatoUserTableBean(idUser);
            getForm().getStatiUtenteList().setTable(statoUser);
            getForm().getStatiUtenteList().getTable().setPageSize(10);
            getForm().getStatiUtenteList().getTable().addSortingRule(UsrVLisStatoUserTableDescriptor.COL_TS_STATO,
                    SortingRule.DESC);
            getForm().getStatiUtenteList().getTable().sort();
            getForm().getStatiUtenteList().getTable().first();

            // Carico la lista delle abilitazioni alle organizzazioni
            UsrVLisAbilOrganizTableBean abilOrganiz = amministrazioneUtentiEjb.getUsrVLisAbilOrganizTableBean(idUser);
            getForm().getAbilOrganizList().setTable(abilOrganiz);
            getForm().getAbilOrganizList().getTable().setPageSize(10);
            getForm().getAbilOrganizList().getTable().first();

            // Carico la lista delle abilitazioni agli enti
            UsrVLisAbilEnteTableBean abilEnte = amministrazioneUtentiEjb.getUsrVLisAbilEnteTableBean(idUser);
            getForm().getAbilEntiList().setTable(abilEnte);
            getForm().getAbilEntiList().getTable().setPageSize(10);
            getForm().getAbilEntiList().getTable().first();

            // Setto la tab corrente all'entrata del dettaglio utente
            getForm().getAbilListsTabs().setCurrentTab(getForm().getAbilListsTabs().getListaIndirizziIp());
            populateSet(ruoliDefault);
        } catch (ParerUserError ex) {
            getMessageBox().addError(ex.getDescription());
        }
    }

    /*
     * Metodo per impostare in 'view mode' il dettaglio utenti
     */
    private void setDettaglioUtentiToViewMode() throws EMFError {
        getForm().getListaUtenti().setStatus(Status.view);
        getForm().getDettaglioUtente().setViewMode();
        getForm().getDettaglioUtente().setStatus(Status.view);

        // Imposto le liste come non visualizzabili in dettaglio né modificabili
        getForm().getIndIpList().setUserOperations(false, false, false, false);
        getForm().getApplicazioniList().setUserOperations(false, false, false, false);
        getForm().getRuoliDefaultList().setUserOperations(false, false, false, false);
        getForm().getDichAbilOrgList().setUserOperations(true, false, false, false);
        getForm().getDichAbilTipiDatoList().setUserOperations(false, false, false, false);
        getForm().getDichAbilEnteConvenzList().setUserOperations(false, false, false, false);
        getForm().getRuoliList().setUserOperations(false, false, false, false);
        BigDecimal idUserIam = getForm().getDettaglioUtente().getId_user_iam().parse();

        getForm().getDettaglioUtente().getDuplicaUtente().setEditMode();
        getForm().getDettaglioUtente().getLogEventi().setEditMode();
        getForm().getDettaglioUtente().getVisualizzaReplicheUtenti().setEditMode();
        getForm().getDettaglioUtente().getNuovaPassword().setEditMode();
        getForm().getDettaglioUtente().getAttivaUtente().setEditMode();
        getForm().getDettaglioUtente().getDisattivaUtente().setEditMode();
        getForm().getDettaglioUtente().getInviaMailAttivazione().setEditMode();
        getForm().getDettaglioUtente().getCessaUtente().setEditMode();

        getForm().getGestioneRuoliOrganizzazione().getAggiungiRuoloOrg().setViewMode();

        setVisibilityAbilitaDisabilitaUtente(idUserIam);
    }

    private void setVisibilityAbilitaDisabilitaUtente(BigDecimal idUserIam) throws EMFError {
        // Recupero l'ultimo stato utente
        getForm().getDettaglioUtente().getAttivaUtente().setHidden(true);
        getForm().getDettaglioUtente().getDisattivaUtente().setHidden(true);
        getForm().getDettaglioUtente().getCessaUtente().setHidden(true);
        getForm().getDettaglioUtente().getNuovaPassword().setHidden(true);
        getForm().getDettaglioUtente().getInviaMailAttivazione().setHidden(true);

        if (idUserIam != null) {
            UsrUserRowBean userRowBean = amministrazioneUtentiEjb.getUserRowBean(idUserIam);
            String tiStatoUser = amministrazioneUtentiEjb.getStatoCorrenteUser(idUserIam);

            if (tiStatoUser.equals(ConstUsrStatoUser.TiStatotUser.DISATTIVO.name())) {
                getForm().getDettaglioUtente().getAttivaUtente().setHidden(false);
                getForm().getDettaglioUtente().getDisattivaUtente().setHidden(true);
                if (userRowBean.getTipoUser().equals(ApplEnum.TipoUser.PERSONA_FISICA.name())
                        || userRowBean.getTipoUser().equals(ApplEnum.TipoUser.AUTOMA.name())) {
                    getForm().getDettaglioUtente().getCessaUtente().setHidden(false);
                }
            } else if (tiStatoUser.equals(ConstUsrStatoUser.TiStatotUser.ATTIVO.name())) {
                getForm().getDettaglioUtente().getAttivaUtente().setHidden(true);
                getForm().getDettaglioUtente().getDisattivaUtente().setHidden(false);
                if (userRowBean.getTipoUser().equals(ApplEnum.TipoUser.PERSONA_FISICA.name())
                        || userRowBean.getTipoUser().equals(ApplEnum.TipoUser.AUTOMA.name())) {
                    getForm().getDettaglioUtente().getCessaUtente().setHidden(false);
                }

                if (userRowBean.getTipoUser().equals(ApplEnum.TipoUser.AUTOMA.name())) {
                    getForm().getDettaglioUtente().getNuovaPassword().setHidden(false);
                }

                if (userRowBean.getTipoUser().equals(ApplEnum.TipoUser.PERSONA_FISICA.name())) {
                    getForm().getDettaglioUtente().getInviaMailAttivazione().setHidden(false);
                }

            }
        }
    }

    @Override
    public void undoDettaglio() throws EMFError {
        if (getLastPublisher().equals(Application.Publisher.LISTA_RUOLI_ORGANIZZAZIONE)) {
            goBack();
        } else if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_RICHIESTA)
                && getForm().getRichiesteList().getStatus().equals(Status.update)) {
            BaseRowInterface currentRow = getForm().getRichiesteList().getTable().getCurrentRow();
            BigDecimal idRichGestUser = currentRow.getBigDecimal("id_rich_gest_user");
            if (idRichGestUser != null) {
                loadDettaglioRichiesta(idRichGestUser);
            }
            forwardToPublisher(Application.Publisher.DETTAGLIO_RICHIESTA);
        } else if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_AZIONE)
                && getForm().getRichiesteList().getStatus().equals(Status.update)) {
            BaseRowInterface currentRow = getForm().getAzioniList().getTable().getCurrentRow();
            BigDecimal idAppartUserRich = currentRow.getBigDecimal("id_appart_user_rich");
            if (idAppartUserRich != null) {
                loadDettaglioAzione(idAppartUserRich);
            }
            forwardToPublisher(Application.Publisher.DETTAGLIO_AZIONE);
        } else {
            goBack();
        }
    }

    @Override
    public void saveDettaglio() throws EMFError {
        if (getTableName().equals(getForm().getRichiesteList().getName())
                || getTableName().equals(getForm().getRichiestaDetail().getName())) {
            saveRichiesta(getForm().getRichiesteList().getStatus());
        } else if (getTableName().equals(getForm().getAzioniList().getName())
                || getTableName().equals(getForm().getAzioneDetail().getName())) {
            saveAzione();
        }
    }

    private void saveRichiesta(Status status) throws EMFError {
        if (getForm().getRichiestaDetail().validate(getMessageBox())) {
            // Esecuzione controlli online per inserimento/modifica
            controlliSalvataggioRichiesta(status);
            try {
                if (!getMessageBox().hasError()) {
                    // Salva la richiesta
                    UsrRichGestUserRowBean richGestUserRowBean = new UsrRichGestUserRowBean();
                    getForm().getRichiestaDetail().copyToBean(richGestUserRowBean);
                    if (getForm().getRichiestaDetail().getNm_userid_manuale().parse() != null) {
                        richGestUserRowBean.setTiUserRich(ConstUsrRichGestUser.TiUserRich.UTENTE_NO_CODIF.name());
                    } else if (getForm().getRichiestaDetail().getNm_userid_codificato().parse() != null) {
                        richGestUserRowBean.setTiUserRich(ConstUsrRichGestUser.TiUserRich.UTENTE_CODIF.name());
                    }

                    /*
                     * Codice aggiuntivo per il logging...
                     */
                    LogParam param = SpagoliteLogUtil.getLogParam(paramHelper.getParamApplicApplicationName(),
                            getUser().getUsername(), SpagoliteLogUtil.getPageName(this));
                    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
                    if (status.equals(Status.insert)) {
                        param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
                        byte[] blob = getForm().getRichiestaDetail().getBl_rich_gest_user().getFileBytes();
                        Long idRichGestUser = amministrazioneUtentiEjb.saveRichiesta(param, richGestUserRowBean, blob);
                        if (idRichGestUser != null) {
                            richGestUserRowBean.setIdRichGestUser(new BigDecimal(idRichGestUser));
                            richGestUserRowBean.setString("identificativo_ud_rich",
                                    richGestUserRowBean.getCdRegistroRichGestUser() + " "
                                            + richGestUserRowBean.getAaRichGestUser() + " "
                                            + richGestUserRowBean.getCdKeyRichGestUser());
                            getForm().getRichiestaDetail().getId_rich_gest_user().setValue(idRichGestUser.toString());
                        }
                        if (getForm().getRichiesteList().getTable() == null) {
                            getForm().getRichiesteList().setTable(new UsrVRicRichiesteTableBean());
                        }
                        getForm().getRichiesteList().getTable().last();
                        getForm().getRichiesteList().getTable().add(richGestUserRowBean);
                    } else if (status.equals(Status.update)) {
                        param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
                        BigDecimal idRichGestUser = getForm().getRichiestaDetail().getId_rich_gest_user().parse();
                        byte[] blob = getForm().getRichiestaDetail().getBl_rich_gest_user().getFileBytes();
                        amministrazioneUtentiEjb.saveRichiesta(param, idRichGestUser, richGestUserRowBean, blob);
                    }
                    BigDecimal idRichGestUser = getForm().getRichiestaDetail().getId_rich_gest_user().parse();
                    if (idRichGestUser != null) {
                        loadDettaglioRichiesta(idRichGestUser);
                    }
                    getForm().getRichiesteList().setStatus(Status.view);
                    getForm().getRichiestaDetail().setStatus(Status.view);
                    getMessageBox().addInfo("Richiesta salvata con successo");
                    getMessageBox().setViewMode(ViewMode.plain);
                }
            } catch (ParerUserError ex) {
                getMessageBox().addError(ex.getDescription());
            }
        }
        forwardToPublisher(Application.Publisher.DETTAGLIO_RICHIESTA);
    }

    @Override
    public void salvaRichiesta() throws EMFError {
        getRequest().setAttribute("isFromRichiestaConfigurazioneUtenti", true);
        saveRichiesta(getForm().getRichiesteList().getStatus());
        getForm().getIdentificativoUdRichSection().setHidden(true);
        getForm().getRichiestaDetail().getNm_userid_manuale().setHidden(true);
    }

    private void controlliSalvataggioRichiesta(Status status) throws EMFError {
        // Controllo obbligatorietà campi incrociati
        if ((getForm().getRichiestaDetail().getId_organiz_iam().parse() == null
                && getForm().getRichiestaDetail().getCd_registro_rich_gest_user().parse() == null
                && getForm().getRichiestaDetail().getAa_rich_gest_user().parse() == null
                && getForm().getRichiestaDetail().getCd_key_rich_gest_user().parse() == null
                && getForm().getRichiestaDetail().getCd_rich_gest_user().parse() == null)
                || ((getForm().getRichiestaDetail().getId_organiz_iam().parse() != null
                        || getForm().getRichiestaDetail().getCd_registro_rich_gest_user().parse() != null
                        || getForm().getRichiestaDetail().getAa_rich_gest_user().parse() != null
                        || getForm().getRichiestaDetail().getCd_key_rich_gest_user().parse() != null)
                        && getForm().getRichiestaDetail().getCd_rich_gest_user().parse() != null)) {
            getMessageBox().addError(
                    "E' obbligatorio indicare il numero protocollo della richiesta o l'identificativo richiesta");
        } else if (getForm().getRichiestaDetail().getCd_rich_gest_user().parse() == null) {
            if ((getForm().getRichiestaDetail().getCd_registro_rich_gest_user().parse() != null
                    && (getForm().getRichiestaDetail().getId_organiz_iam().parse() == null
                            || getForm().getRichiestaDetail().getAa_rich_gest_user().parse() == null
                            || getForm().getRichiestaDetail().getCd_key_rich_gest_user().parse() == null))
                    || (getForm().getRichiestaDetail().getAa_rich_gest_user().parse() != null
                            && (getForm().getRichiestaDetail().getId_organiz_iam().parse() == null
                                    || getForm().getRichiestaDetail().getCd_registro_rich_gest_user().parse() == null
                                    || getForm().getRichiestaDetail().getCd_key_rich_gest_user().parse() == null))
                    || (getForm().getRichiestaDetail().getCd_key_rich_gest_user().parse() != null
                            && (getForm().getRichiestaDetail().getId_organiz_iam().parse() == null
                                    || getForm().getRichiestaDetail().getCd_registro_rich_gest_user().parse() == null
                                    || getForm().getRichiestaDetail().getAa_rich_gest_user().parse() == null))
                    || (getForm().getRichiestaDetail().getId_organiz_iam() != null
                            && (getForm().getRichiestaDetail().getCd_registro_rich_gest_user().parse() == null
                                    || getForm().getRichiestaDetail().getAa_rich_gest_user().parse() == null
                                    || getForm().getRichiestaDetail().getCd_key_rich_gest_user().parse() == null))) {
                getMessageBox().addError(
                        "E' obbligatorio indicare il numero protocollo della richiesta specificando registro, anno e numero");
            } else if ((getForm().getRichiestaDetail().getCd_registro_rich_gest_user().parse() != null
                    || getForm().getRichiestaDetail().getAa_rich_gest_user().parse() != null
                    || getForm().getRichiestaDetail().getCd_key_rich_gest_user().parse() != null)
                    && getForm().getRichiestaDetail().getId_organiz_iam().parse() == null) {
                getMessageBox().addError(
                        "E' obbligatorio indicare il numero protocollo della richiesta specificando anche la struttura a cui appartiene tale unità documentaria");
            }
        }
        // Controllo data
        if (getForm().getRichiestaDetail().getDt_rich_gest_user().parse().after(new Date())) {
            getMessageBox().addError("La data della richiesta non può assumere un valore successivo alla data odierna");
        }
        // Controllo che solo uno tra richiedente manuale e richiedente codificato sia valorizzato
        if ((getForm().getRichiestaDetail().getNm_userid_manuale().parse() == null
                && getForm().getRichiestaDetail().getNm_userid_codificato().parse() == null)
                || (getForm().getRichiestaDetail().getNm_userid_manuale().parse() != null
                        && getForm().getRichiestaDetail().getNm_userid_codificato().parse() != null)) {
            getMessageBox()
                    .addError("E' obbligatorio inserire il mittente richiesta o il richiedente codificato (uno solo)");
        }
        // Controllo anno di 4 cifre
        BigDecimal aaRichGestUser = getForm().getRichiestaDetail().getAa_rich_gest_user().parse();
        if (aaRichGestUser != null && aaRichGestUser.compareTo(new BigDecimal(1000)) < 0) {
            getMessageBox().addError("L'anno è un numero di quattro cifre");
        }

        // Controllo identificativo UD
        // Controllo file specifico a seconda dello status
        if (status.equals(Status.insert)) {
            if (getForm().getRichiestaDetail().getBl_rich_gest_user().parse() != null) {
                getForm().getRichiestaDetail().getNm_file_rich_gest_user()
                        .setValue(getForm().getRichiestaDetail().getBl_rich_gest_user().parse());
            }
        } else if (status.equals(Status.update)) {
            // Se sono in modifica di sicuro ho l'id richiesta
            BigDecimal idRichGestUser = getForm().getRichiestaDetail().getId_rich_gest_user().parse();
            String nmFilePreModifica = amministrazioneUtentiEjb.getUsrRichGestUserRowBean(idRichGestUser)
                    .getNmFileRichGestUser();
            if (getForm().getRichiestaDetail().getBl_rich_gest_user().parse() != null) {
                getForm().getRichiestaDetail().getNm_file_rich_gest_user()
                        .setValue(getForm().getRichiestaDetail().getBl_rich_gest_user().parse());
            }
            String nmFilePostModifica = getForm().getRichiestaDetail().getNm_file_rich_gest_user().parse();
            String blobboPostModifica = getForm().getRichiestaDetail().getBl_rich_gest_user().parse();
            // I controlli devono in taluni casi tenere conto del vecchio valore del nome file
            if (nmFilePostModifica == null && blobboPostModifica != null) {
                getMessageBox()
                        .addError("E' stato caricato un file senza però specificarne il nome nell'apposito campo");
            } else if (nmFilePreModifica == null && nmFilePostModifica != null && blobboPostModifica == null) {
                getMessageBox().addError("E' stato specificato un nome file senza però aver caricato un file");
            } else if (nmFilePreModifica != null && nmFilePostModifica == null && blobboPostModifica == null) {
                getMessageBox().addError(
                        "E' stato caricato un file in precedenza, dunque il campo nome file deve essere valorizzato");
            }
        }

        // Se provengo da richiesta configurazione utenti e non ho caricato file, le note sono obbligatorie
        if (getRequest().getAttribute("isFromRichiestaConfigurazioneUtenti") != null
                && getForm().getRichiestaDetail().getNm_file_rich_gest_user().parse() == null
                && getForm().getRichiestaDetail().getNt_rich_gest_user().parse() == null) {
            getMessageBox().addError(
                    "Attenzione: non è stato specificato un file, in questo caso il campo Note deve essere valorizzato");
        }
    }

    private void saveAzione() throws EMFError {
        if (getForm().getAzioneDetail().postAndValidate(getRequest(), getMessageBox())) {
            // CONTROLLI DI COERENZA NELLA MASCHERA
            // Controllo che solo uno tra utente manuale ed utente codificato sia valorizzato
            if (((getForm().getAzioneDetail().getNm_cognome_user().parse() != null
                    || getForm().getAzioneDetail().getNm_nome_user().parse() != null
                    || getForm().getAzioneDetail().getNm_userid().parse() != null)
                    && getForm().getAzioneDetail().getNm_userid_codificato().parse() != null)
                    || ((getForm().getAzioneDetail().getNm_cognome_user().parse() == null
                            && getForm().getAzioneDetail().getNm_nome_user().parse() == null
                            && getForm().getAzioneDetail().getNm_userid().parse() == null)
                            && getForm().getAzioneDetail().getNm_userid_codificato().parse() == null)) {
                getMessageBox().addError("E' obbligatorio inserire l'utente manuale o l'utente codificato (uno solo)");
            } else if (getForm().getAzioneDetail().getTi_azione_rich().parse()
                    .equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_CREAZIONE.getDescrizione())
                    && (getForm().getAzioneDetail().getNm_userid_codificato().parse() == null
                            && (getForm().getAzioneDetail().getNm_cognome_user().parse() == null
                                    || getForm().getAzioneDetail().getNm_nome_user().parse() == null
                                    || getForm().getAzioneDetail().getNm_userid().parse() == null))) {
                getMessageBox().addError("Se si inserisce l'utente manualmente vanno inseriti cognome, nome e userid");
            }

            if (!getMessageBox().hasError()) {

                // Coerenza se è non codificato...
                if (StringUtils.isBlank(getForm().getAzioneDetail().getNm_userid().parse())
                        && getForm().getAzioneDetail().getId_user_iam().parse() == null && ( // getForm().getAzioneDetail().getTi_azione_rich().parse()
                        // .equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_RESET_PWD.getDescrizione())
                        // ||
                        getForm().getAzioneDetail().getTi_azione_rich().parse()
                                .equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_CESSAZIONE.getDescrizione())
                                || getForm().getAzioneDetail().getTi_azione_rich().parse()
                                        .equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ABILITAZIONI
                                                .getDescrizione())
                                || getForm().getAzioneDetail().getTi_azione_rich().parse().equals(
                                        ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_RIATTIVZIONE.getDescrizione()))) {
                    getMessageBox().addError("Per le azioni 'Richiesta di reset password', o 'Richiesta di cessazione' "
                            + "o 'Richiesta di modifica abilitazioni' o 'Richiesta di riattivazione' "
                            + "\u00E8 necessario specificare un utente");
                }
                // Coerenza se è codificato...
                if (getForm().getAzioneDetail().getId_user_iam().parse() != null
                        && getForm().getAzioneDetail().getTi_azione_rich().parse()
                                .equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_CREAZIONE.getDescrizione())) {
                    getMessageBox().addError(
                            "Per l'azione 'Richiesta di creazione' \u00E8 necessario specificare un utente non codificato ");
                }

                if (getForm().getAzioneDetail().getId_user_iam().parse() != null && !amministrazioneUtentiEjb
                        .isUtenteAppartEnteSpecificato(getForm().getAzioneDetail().getId_user_iam().parse(),
                                getForm().getAzioneDetail().getId_ente_user().parse())) {
                    getMessageBox().addError(
                            "L’operazione richiesta è riferita a un utente che appartiene ad un ente diverso da quello specificato ");
                }

                if (getForm().getAzioneDetail().getTi_azione_rich().parse()
                        .equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_CREAZIONE.getDescrizione())) {
                    checkUsername(getForm().getAzioneDetail().getNm_userid().parse());
                }

                try {
                    if (!getMessageBox().hasError()) {
                        // Salva l'azione
                        BigDecimal idRichGestUser = getForm().getRichiestaDetail().getId_rich_gest_user().parse();
                        String tiStatoRichGestUser = getForm().getRichiestaDetail().getTi_stato_rich_gest_user()
                                .parse();
                        String nmUserId = getForm().getAzioneDetail().getNm_userid().parse();
                        if (StringUtils.isNotBlank(nmUserId)) {
                            getForm().getAzioneDetail().getNm_userid().setValue(nmUserId.replaceAll("\\s+", ""));
                            if (!getForm().getAzioneDetail().getTi_azione_rich().parse()
                                    .equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_CREAZIONE.getDescrizione())) {
                                UsrUserRowBean userRowBean = amministrazioneUtentiEjb.getUserRowBean(nmUserId);
                                if (userRowBean.getIdUserIam() != null) {
                                    getForm().getAzioneDetail().getTi_appart_user_rich()
                                            .setValue(ConstUsrAppartUserRich.TiAppartUserRich.UTENTE_CODIFICATO.name());
                                    getForm().getAzioneDetail().getId_user_iam()
                                            .setValue(userRowBean.getIdUserIam().toPlainString());
                                } else {
                                    throw new ParerUserError(
                                            "Utente non esistente o non appartenente al dominio per lo username '"
                                                    + nmUserId + "' specificato");
                                }
                            }
                        }
                        UsrAppartUserRichRowBean appartUserRichRowBean = new UsrAppartUserRichRowBean();
                        getForm().getAzioneDetail().copyToBean(appartUserRichRowBean);

                        /*
                         * Codice aggiuntivo per il logging...
                         */
                        LogParam param = SpagoliteLogUtil.getLogParam(paramHelper.getParamApplicApplicationName(),
                                getUser().getUsername(), SpagoliteLogUtil.getPageName(this));
                        param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
                        if (getForm().getAzioniList().getStatus().equals(Status.insert)) {
                            param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
                            Long idAppartUserRich = amministrazioneUtentiEjb.saveAzione(param, idRichGestUser,
                                    tiStatoRichGestUser, appartUserRichRowBean);
                            if (idAppartUserRich != null) {
                                appartUserRichRowBean.setIdAppartUserRich(new BigDecimal(idAppartUserRich));
                                appartUserRichRowBean.setIdRichGestUser(idRichGestUser);
                            }
                            getForm().getAzioniList().getTable().last();
                            getForm().getAzioniList().getTable().add(appartUserRichRowBean);
                        } else if (getForm().getAzioniList().getStatus().equals(Status.update)) {
                            param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
                            BigDecimal idAppartUserRich = getForm().getAzioneDetail().getId_appart_user_rich().parse();
                            amministrazioneUtentiEjb.saveAzione(param, idRichGestUser, idAppartUserRich,
                                    appartUserRichRowBean);
                        }
                        getForm().getAzioneDetail().setViewMode();
                        getForm().getAzioniList().setStatus(Status.view);
                        getForm().getAzioneDetail().setStatus(Status.view);
                        getMessageBox().addInfo("Azione salvata con successo");
                        getMessageBox().setViewMode(ViewMode.plain);
                    }
                } catch (ParerUserError ex) {
                    getMessageBox().addError(ex.getDescription());
                }
            }
        }
        forwardToPublisher(Application.Publisher.DETTAGLIO_AZIONE);
    }

    @Override
    public void elencoOnClick() throws EMFError {
        goBack();
    }

    @Override
    public String getControllerName() {
        return Application.Actions.AMMINISTRAZIONE_UTENTI;
    }

    /*
     * WIZARD UTENTI
     */
    /**
     * Metodo eseguito in caso di annullamento della creazione/modifica utente nel wizard. Ricarica la lista utenti in
     * visualizzazione
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public void inserimentoWizardOnCancel() throws EMFError {
        getSession().removeAttribute("applicationsEditList");
        if (getForm().getListaUtenti().getStatus().equals(Status.insert)) {
            setDettaglioUtentiToViewMode();
            goBack();
        } else if (getForm().getListaUtenti().getStatus().equals(Status.update)) {
            setNavigationEvent(NE_DETTAGLIO_VIEW);
            setTableName(getForm().getListaUtenti().getName());
            loadDettaglio();
            forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE);
            setDettaglioUtentiToViewMode();
            SessionManager.removeLastExecutionHistory(getSession());
        } else {
            setTableName(getForm().getListaUtenti().getName());
            loadDettaglio();
            forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE);
            setDettaglioUtentiToViewMode();
            SessionManager.removeLastExecutionHistory(getSession());
        }
    }

    /**
     * Carica il publisher di default del wizard di inserimento
     *
     * @return il publisher del wizard
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public String getDefaultInserimentoWizardPublisher() throws EMFError {
        return Application.Publisher.DETTAGLIO_UTENTE_WIZARD;
    }

    @Override
    public void inserimentoWizardPassoRichiestaOnEnter() throws EMFError {
        getForm().getRichiestaWizard().setEditMode();
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
    }

    @Override
    public boolean inserimentoWizardPassoRichiestaOnExit() throws EMFError {
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);

        getForm().getRichiestaWizard().post(getRequest());
        getForm().getInserimentoWizard().getPassoApplic().setHidden(false);
        getForm().getInserimentoWizard().getPassoIp().setHidden(false);
        getForm().getInserimentoWizard().getPasso2().setHidden(false);
        if (getForm().getRichiestaWizard().validate(getMessageBox())) {
            BigDecimal idOrganizIamRich = getForm().getRichiestaWizard().getId_organiz_iam_rich().parse();
            String cdRegistroRich = getForm().getRichiestaWizard().getCd_registro_rich_gest_user().parse();
            BigDecimal aaRich = getForm().getRichiestaWizard().getAa_rich_gest_user().parse();
            String cdKeyRich = getForm().getRichiestaWizard().getCd_key_rich_gest_user().parse();
            BigDecimal idEnteConvenzRich = getForm().getRichiestaWizard().getId_ente_convenz_rich().parse();
            String nmEnteConvenzRich = getForm().getRichiestaWizard().getId_ente_convenz_rich().getDecodedValue();
            String cdRich = getForm().getRichiestaWizard().getCd_rich_gest_user().parse();

            /*
             * Richiesta non obbligatoria, controllo se è stato popolato qualche dato per inserirla, altrimenti vado
             * diretto al passo successivo
             */
            if (StringUtils.isNotBlank(cdRegistroRich) || aaRich != null || StringUtils.isNotBlank(cdKeyRich)
                    || StringUtils.isNotBlank(cdRich)) {
                if ((StringUtils.isNotBlank(cdRegistroRich) || aaRich != null || StringUtils.isNotBlank(cdKeyRich))
                        && StringUtils.isNotBlank(cdRich)) {
                    // Sono stati inseriti sia dati per l'ud richiesta, sia l'identificativo richiesta
                    getMessageBox()
                            .addError("Indicare o il numero protocollo della richiesta o l'identificativo richiesta");
                } else if (StringUtils.isBlank(cdRich)) {
                    if (cdRegistroRich == null || aaRich == null || cdKeyRich == null || idOrganizIamRich == null) {
                        getMessageBox().addError(
                                "Il numero protocollo della richiesta deve essere compilato fornendo l'organizzazione, il registro, l'anno ed il numero");
                    } else {// Verifico l'esistenza della richiesta con idOrganiz, registro, anno e numero
                        final UsrRichGestUserRowBean usrRichGest = amministrazioneUtentiEjb
                                .getUsrRichGestUserRowBean(idOrganizIamRich, cdRegistroRich, aaRich, cdKeyRich);
                        if (usrRichGest == null
                                || (getForm().getListaUtenti().getStatus().equals(Status.insert)
                                        && !usrRichGest.getTiStatoRichGestUser()
                                                .equals(ConstUsrRichGestUser.TiStatoRichGestUser.DA_EVADERE.name()))
                                || (getForm().getListaUtenti().getStatus().equals(Status.update)
                                        && !usrRichGest.getTiStatoRichGestUser()
                                                .equals(ConstUsrRichGestUser.TiStatoRichGestUser.DA_EVADERE.name())
                                        && !usrRichGest.getTiStatoRichGestUser()
                                                .equals(ConstUsrRichGestUser.TiStatoRichGestUser.EVASA.name()))) {
                            getMessageBox().addError(
                                    "La richiesta di gestione utenti specificata non \u00E8 presente nel sistema oppure non ha stato = DA_EVADERE "
                                            + (getForm().getListaUtenti().getStatus().equals(Status.update) ? "O EVASA"
                                                    : ""));
                        } else {
                            getForm().getRichiestaWizard().getId_rich_gest_user()
                                    .setValue(usrRichGest.getIdRichGestUser().toPlainString());
                            if (getForm().getListaUtenti().getStatus().equals(Status.insert)) {
                                // Richiesta inserita, inserisco un utente di tipo AUTOMA o PERSONA_FISICA
                                getForm().getDettaglioUtente().getTipo_user()
                                        .setDecodeMap(ComboGetter.getMappaSortedGenericEnum("tipo_user",
                                                ApplEnum.TipoUser.getComboTipiTotali()));
                                getForm().getDettaglioUtente().getTipo_user()
                                        .setValue(ApplEnum.TipoUser.PERSONA_FISICA.name());
                                // Popolo i dati dell'utente se contiene solo una azione di creazione non evasa
                                UsrAppartUserRichTableBean usrAppartUserRichTableBean = amministrazioneUtentiEjb
                                        .getUsrAppartUserRichTableBean(usrRichGest.getIdRichGestUser(),
                                                ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_CREAZIONE
                                                        .getDescrizione(),
                                                "0");
                                if (usrAppartUserRichTableBean.size() == 1) {
                                    UsrAppartUserRichRowBean row = usrAppartUserRichTableBean.getRow(0);
                                    getForm().getDettaglioUtente().getNm_cognome_user()
                                            .setValue(row.getNmCognomeUser());
                                    getForm().getDettaglioUtente().getNm_nome_user().setValue(row.getNmNomeUser());
                                    getForm().getDettaglioUtente().getNm_userid().setValue(row.getNmUserid());
                                }
                                gestioneAmbienteEntePerRichiestaWizard(usrRichGest.getBigDecimal("id_ente_siam"),
                                        usrRichGest.getString("nm_ente_siam"));

                                try {
                                    OrgEnteSiamRowBean ente = entiConvenzionatiEjb
                                            .getOrgEnteConvenzRowBean(usrRichGest.getBigDecimal("id_ente_siam"));
                                    setFlagUtente(ente.getTiEnte(), ente.getTiEnteNonConvenz(), "PERSONA_FISICA");
                                } catch (ParerUserError ex) {
                                    java.util.logging.Logger.getLogger(AmministrazioneUtentiAction.class.getName())
                                            .log(Level.SEVERE, null, ex);
                                }

                            } else if (getForm().getListaUtenti().getStatus().equals(Status.update)) {
                                // UPDATE e richiesta inserita, se era NON_DI_SISTEMA può essere solo un cambio a
                                // PERSONA_FISICA
                                String oldTipoUser = getForm().getDettaglioUtente().getTipo_user().parse();
                                if (oldTipoUser.equals(ApplEnum.TipoUser.NON_DI_SISTEMA.name())) {
                                    getForm().getDettaglioUtente().getTipo_user()
                                            .setDecodeMap(ComboGetter.getMappaSortedGenericEnum("tipo_user",
                                                    ApplEnum.TipoUser.getEnums(ApplEnum.TipoUser.PERSONA_FISICA)));
                                    getForm().getDettaglioUtente().getTipo_user()
                                            .setValue(ApplEnum.TipoUser.PERSONA_FISICA.name());
                                } else {
                                    getForm().getDettaglioUtente().getTipo_user()
                                            .setDecodeMap(ComboGetter.getMappaSortedGenericEnum("tipo_user",
                                                    ApplEnum.TipoUser.getComboTipiTotali()));
                                    getForm().getDettaglioUtente().getTipo_user().setValue(oldTipoUser);
                                }
                                gestioneAmbienteEntePerRichiestaWizard(usrRichGest.getBigDecimal("id_ente_siam"),
                                        usrRichGest.getString("nm_ente_siam"));

                                // Azzero le liste solo se è un'azione di modifica ente non evasa
                                UsrAppartUserRichTableBean usrAppartUserRichTableBean1 = amministrazioneUtentiEjb
                                        .getUsrAppartUserRichTableBean(usrRichGest.getIdRichGestUser(),
                                                ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ENTE_APPARTENENZA
                                                        .getDescrizione(),
                                                "0");
                                if (usrAppartUserRichTableBean1.size() == 1) {
                                    azzeraListeDichiarazioniAbilitazioni();
                                }

                                // RICHIESTA INSERITA ED UPDATE
                                manageAmbienteEnteAppartenenzaForUpdate(usrRichGest.getIdRichGestUser());
                            }

                        }
                    }
                } else {
                    if (idEnteConvenzRich == null) {
                        getMessageBox().addError(
                                "L'identificativo della richiesta deve essere compilato fornendo anche l'ente convenzionato della richiesta");
                    } else {// Verifico l'esistenza della richiesta con idEnteConvenzionato e cdRichiesta
                        final UsrRichGestUserRowBean usrRichGest = amministrazioneUtentiEjb
                                .getUsrRichGestUserRowBean(idEnteConvenzRich, cdRich);
                        if (usrRichGest == null
                                || (getForm().getListaUtenti().getStatus().equals(Status.insert)
                                        && !usrRichGest.getTiStatoRichGestUser()
                                                .equals(ConstUsrRichGestUser.TiStatoRichGestUser.DA_EVADERE.name()))
                                || (getForm().getListaUtenti().getStatus().equals(Status.update)
                                        && !usrRichGest.getTiStatoRichGestUser()
                                                .equals(ConstUsrRichGestUser.TiStatoRichGestUser.DA_EVADERE.name())
                                        && !usrRichGest.getTiStatoRichGestUser()
                                                .equals(ConstUsrRichGestUser.TiStatoRichGestUser.EVASA.name()))) {
                            getMessageBox().addError(
                                    "La richiesta di gestione utenti specificata non \u00E8 presente nel sistema oppure non ha stato = DA_EVADERE "
                                            + (getForm().getListaUtenti().getStatus().equals(Status.update) ? "O EVASA"
                                                    : ""));
                        } else {
                            getForm().getRichiestaWizard().getId_rich_gest_user()
                                    .setValue(usrRichGest.getIdRichGestUser().toPlainString());
                            if (getForm().getListaUtenti().getStatus().equals(Status.insert)) {

                                try {
                                    OrgEnteSiamRowBean ente = entiConvenzionatiEjb
                                            .getOrgEnteConvenzRowBean(usrRichGest.getBigDecimal("id_ente_siam"));

                                    if (ente.getTiEnte().equals("NON_CONVENZIONATO")) {
                                        getForm().getDettaglioUtente().getTipo_user()
                                                .setDecodeMap(ComboGetter.getMappaSortedGenericEnum("tipo_user",
                                                        ApplEnum.TipoUser.getComboTipiNonDiSistema()));
                                        getForm().getDettaglioUtente().getTipo_user()
                                                .setValue(ApplEnum.TipoUser.NON_DI_SISTEMA.name());
                                    } else {
                                        getForm().getDettaglioUtente().getTipo_user()
                                                .setDecodeMap(ComboGetter.getMappaSortedGenericEnum("tipo_user",
                                                        ApplEnum.TipoUser.getComboTipiTotali()));
                                        getForm().getDettaglioUtente().getTipo_user()
                                                .setValue(ApplEnum.TipoUser.PERSONA_FISICA.name());
                                    }

                                    setFlagUtente(ente.getTiEnte(), ente.getTiEnteNonConvenz(),
                                            getForm().getDettaglioUtente().getTipo_user().parse());

                                } catch (ParerUserError ex) {
                                    java.util.logging.Logger.getLogger(AmministrazioneUtentiAction.class.getName())
                                            .log(Level.SEVERE, null, ex);
                                }

                                // Popolo i dati dell'utente se contiene solo una azione di creazione non evasa
                                UsrAppartUserRichTableBean usrAppartUserRichTableBean = amministrazioneUtentiEjb
                                        .getUsrAppartUserRichTableBean(usrRichGest.getIdRichGestUser(),
                                                ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_CREAZIONE
                                                        .getDescrizione(),
                                                "0");
                                if (usrAppartUserRichTableBean.size() == 1) {
                                    UsrAppartUserRichRowBean row = usrAppartUserRichTableBean.getRow(0);
                                    getForm().getDettaglioUtente().getNm_cognome_user()
                                            .setValue(row.getNmCognomeUser());
                                    getForm().getDettaglioUtente().getNm_nome_user().setValue(row.getNmNomeUser());
                                    getForm().getDettaglioUtente().getNm_userid().setValue(row.getNmUserid());
                                }

                                setComboAmbienteEnteAppartenenza(idEnteConvenzRich, nmEnteConvenzRich);

                            } else if (getForm().getListaUtenti().getStatus().equals(Status.update)) {
                                // UPDATE e richiesta inserita, se era NON_DI_SISTEMA può essere solo un cambio a
                                // PERSONA_FISICA
                                String oldTipoUser = getForm().getDettaglioUtente().getTipo_user().parse();
                                if (oldTipoUser.equals(ApplEnum.TipoUser.NON_DI_SISTEMA.name())) {
                                    getForm().getDettaglioUtente().getTipo_user()
                                            .setDecodeMap(ComboGetter.getMappaSortedGenericEnum("tipo_user",
                                                    ApplEnum.TipoUser.getEnums(ApplEnum.TipoUser.PERSONA_FISICA)));
                                    getForm().getDettaglioUtente().getTipo_user()
                                            .setValue(ApplEnum.TipoUser.PERSONA_FISICA.name());
                                } else if (oldTipoUser.equals(ApplEnum.TipoUser.AUTOMA)
                                        || (oldTipoUser.equals(ApplEnum.TipoUser.PERSONA_FISICA.name()))) {
                                    getForm().getDettaglioUtente().getTipo_user()
                                            .setDecodeMap(ComboGetter.getMappaSortedGenericEnum("tipo_user",
                                                    ApplEnum.TipoUser.getComboTipiPerRuoli()));
                                    getForm().getDettaglioUtente().getTipo_user().setValue(oldTipoUser);
                                }

                                // RICHIESTA INSERITA ED UPDATE
                                manageAmbienteEnteAppartenenzaForUpdate(usrRichGest.getIdRichGestUser());

                                // Azzero le liste solo se è un'azione di modifica ente non evasa
                                UsrAppartUserRichTableBean usrAppartUserRichTableBean1 = amministrazioneUtentiEjb
                                        .getUsrAppartUserRichTableBean(usrRichGest.getIdRichGestUser(),
                                                ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ENTE_APPARTENENZA
                                                        .getDescrizione(),
                                                "0");
                                if (usrAppartUserRichTableBean1.size() == 1) {
                                    azzeraListeDichiarazioniAbilitazioni();
                                }

                                BigDecimal idEnteUser = getForm().getDettaglioUtente().getId_ente_siam_appart().parse();
                                String flRespEnteConvenz = getForm().getDettaglioUtente().getFl_resp_ente_convenz()
                                        .parse();
                                String flAbilEntiCollegAutom = getForm().getDettaglioUtente()
                                        .getFl_abil_enti_colleg_autom().parse();
                                String flAbilOrganizAutom = getForm().getDettaglioUtente().getFl_abil_organiz_autom()
                                        .parse();
                                String flAbilOrganizEntiAutom = getForm().getDettaglioUtente().getFl_abil_fornit_autom()
                                        .parse();
                                String tipoUser = getForm().getDettaglioUtente().getTipo_user().parse();

                                try {
                                    if (idEnteUser != null) {
                                        OrgEnteSiamRowBean ente = entiConvenzionatiEjb
                                                .getOrgEnteConvenzRowBean(idEnteUser);
                                        setFlagUtente(ente.getTiEnte(), ente.getTiEnteNonConvenz(), tipoUser);
                                        getForm().getDettaglioUtente().getFl_resp_ente_convenz()
                                                .setValue(flRespEnteConvenz);
                                        getForm().getDettaglioUtente().getFl_abil_enti_colleg_autom()
                                                .setValue(flAbilEntiCollegAutom);
                                        getForm().getDettaglioUtente().getFl_abil_organiz_autom()
                                                .setValue(flAbilOrganizAutom);
                                        getForm().getDettaglioUtente().getFl_abil_fornit_autom()
                                                .setValue(flAbilOrganizEntiAutom);
                                    }
                                } catch (ParerUserError ex) {
                                    getMessageBox().addError(ex.getDescription());
                                }

                            }
                        }
                    }
                }
            } else {
                // Richiesta non inserita, inserisco un utente di tipo NON_DI_SISTEMA
                String oldTipoUser = null;
                if (getForm().getListaUtenti().getStatus().equals(Status.insert)) {
                    getForm().getDettaglioUtente().getTipo_user().setValue(ApplEnum.TipoUser.NON_DI_SISTEMA.name());
                    // Combo ambiente ente convenzionato
                    UsrVAbilAmbEnteConvenzTableBean abilAmbEnteConvenzTableBean = entiConvenzionatiEjb
                            .getUsrVAbilAmbEnteConvenzTableBean(new BigDecimal(getUser().getIdUtente()));
                    getForm().getDettaglioUtente().getId_amb_ente_convenz_appart()
                            .setDecodeMap(DecodeMap.Factory.newInstance(abilAmbEnteConvenzTableBean,
                                    "id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));
                    // Combo ente non convenzionato
                    OrgVRicEnteNonConvenzTableBean ricEnteNonConvenz = entiConvenzionatiEjb
                            .getOrgVRicEnteNonConvenzAbilTableBean(BigDecimal.valueOf(getUser().getIdUtente()), null);
                    getForm().getDettaglioUtente().getId_ente_siam_appart().setDecodeMap(
                            DecodeMap.Factory.newInstance(ricEnteNonConvenz, "id_ente_siam", "nm_ente_siam"));

                    setFlagUtente(null, null, null);
                } else if (getForm().getListaUtenti().getStatus().equals(Status.update)) {
                    // RICHIESTA NON INSERITA ED UPDATE
                    manageAmbienteEnteAppartenenzaForUpdate(null);
                    oldTipoUser = getForm().getDettaglioUtente().getTipo_user().parse();
                    if (oldTipoUser.equals(ApplEnum.TipoUser.NON_DI_SISTEMA.name())) {
                        getForm().getDettaglioUtente().getTipo_user().setValue(oldTipoUser);
                    } else {
                        getMessageBox().addError(
                                "\u00C8 necessario indicare il numero protocollo o l'identificativo della richiesta che giustifica la modifica dell'utente");
                    }
                }
                if (!getMessageBox().hasError()) {
                    getForm().getDettaglioUtente().getTipo_user().setDecodeMap(ComboGetter
                            .getMappaSortedGenericEnum("tipo_user", ApplEnum.TipoUser.getComboTipiNonDiSistema()));
                    getForm().getDettaglioUtente().getTipo_user().setValue(oldTipoUser);

                    getForm().getInserimentoWizard().getPassoApplic().setHidden(true);
                    getForm().getInserimentoWizard().getPassoIp().setHidden(true);
                    getForm().getInserimentoWizard().getPasso2().setHidden(true);
                }
            }
        }

        // Se provengo dalla Gestione Ruoli Specifici per organizzazione, devo saltare gli altri passi del wizard
        if (getSession().getAttribute("fromRuoliSpecifici") != null) {
            getForm().getInserimentoWizard().getPasso1().setHidden(true);
            getForm().getInserimentoWizard().getPassoApplic().setHidden(true);
            getForm().getInserimentoWizard().getPassoIp().setHidden(true);
            getForm().getInserimentoWizard().getPasso2().setHidden(true);
            getForm().getInserimentoWizard().getPassoRuoliSpecifici().setHidden(false);
        } else {
            getForm().getInserimentoWizard().getPassoRuoliSpecifici().setHidden(true);
        }

        return !getMessageBox().hasError();
    }

    private void setComboAmbienteEnteAppartenenza(BigDecimal idEnteConvenz, String nmEnteConvenz) {

        OrgAmbienteEnteConvenzRowBean ambienteEnteConvenz = entiConvenzionatiEjb
                .getOrgAmbienteEnteConvenzByEnteConvenz(idEnteConvenz);

        BaseTable fakeTbAmbiente = new BaseTable();
        fakeTbAmbiente.add().setBigDecimal("id_ambiente_ente_convenz", ambienteEnteConvenz.getIdAmbienteEnteConvenz());
        fakeTbAmbiente.getCurrentRow().setString("nm_ambiente_ente_convenz",
                ambienteEnteConvenz.getNmAmbienteEnteConvenz());

        BaseTable fakeTb = new BaseTable();
        fakeTb.add().setBigDecimal("id_ente_convenz", idEnteConvenz);
        fakeTb.getCurrentRow().setString("nm_ente_convenz", nmEnteConvenz);

        getForm().getDettaglioUtente().getId_ente_siam_appart()
                .setDecodeMap(DecodeMap.Factory.newInstance(fakeTb, "id_ente_convenz", "nm_ente_convenz"));
        if (ambienteEnteConvenz.getIdAmbienteEnteConvenz() != null) {
            getForm().getDettaglioUtente().getId_amb_ente_convenz_appart().setDecodeMap(DecodeMap.Factory
                    .newInstance(fakeTbAmbiente, "id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));
            getForm().getDettaglioUtente().getId_amb_ente_convenz_appart()
                    .setValue("" + ambienteEnteConvenz.getIdAmbienteEnteConvenz());
        } else {
            getForm().getDettaglioUtente().getId_amb_ente_convenz_appart().setDecodeMap(new DecodeMap());
        }

        getForm().getDettaglioUtente().getId_ente_siam_appart().setValue("" + idEnteConvenz);
    }

    private void gestioneAmbienteEntePerRichiestaWizard(BigDecimal idEnteRich, String nmEnteRich) {

        OrgAmbienteEnteConvenzRowBean ambienteEnteConvenz = entiConvenzionatiEjb
                .getOrgAmbienteEnteConvenzByEnteConvenz(idEnteRich);

        BaseTable fakeTbAmbiente = new BaseTable();
        fakeTbAmbiente.add().setBigDecimal("id_ambiente_ente_convenz", ambienteEnteConvenz.getIdAmbienteEnteConvenz());
        fakeTbAmbiente.getCurrentRow().setString("nm_ambiente_ente_convenz",
                ambienteEnteConvenz.getNmAmbienteEnteConvenz());

        BaseTable fakeTb = new BaseTable();
        fakeTb.add().setBigDecimal("id_ente_siam", idEnteRich);
        fakeTb.getCurrentRow().setString("nm_ente_siam", nmEnteRich);

        getForm().getDettaglioUtente().getId_ente_siam_appart()
                .setDecodeMap(DecodeMap.Factory.newInstance(fakeTb, "id_ente_siam", "nm_ente_siam"));
        if (ambienteEnteConvenz.getIdAmbienteEnteConvenz() != null) {
            getForm().getDettaglioUtente().getId_amb_ente_convenz_appart().setDecodeMap(DecodeMap.Factory
                    .newInstance(fakeTbAmbiente, "id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));
            getForm().getDettaglioUtente().getId_amb_ente_convenz_appart()
                    .setValue("" + ambienteEnteConvenz.getIdAmbienteEnteConvenz());
        } else {
            getForm().getDettaglioUtente().getId_amb_ente_convenz_appart().setDecodeMap(new DecodeMap());
        }

        getForm().getDettaglioUtente().getId_ente_siam_appart().setValue("" + idEnteRich);

        getForm().getDettaglioUtente().getId_amb_ente_convenz_appart().setViewMode();
        getForm().getDettaglioUtente().getId_ente_siam_appart().setViewMode();

    }

    /**
     * Primo step del wizard: - Carica il dettaglio utente in edit mode - Visualizza il bottone per inserire dati nelle
     * liste
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public void inserimentoWizardPasso1OnEnter() throws EMFError {
        getSession().removeAttribute("applicationsEditList");
        setDettaglioUtenteEditMode(Status.update);
        if (getForm().getListaUtenti().getStatus().equals(Status.update)) {
            if (getForm().getDettaglioUtente().getTipo_user().parse().equals("NON_DI_SISTEMA")) {
                getForm().getDettaglioUtente().getId_sistema_versante().setDecodeMap(new DecodeMap());
                getForm().getDettaglioUtente().getNm_userid().setViewMode();
            } else {
                getForm().getDettaglioUtente().getId_sistema_versante().setEditMode();
            }
        }
        getForm().getDichAbilFields().getAdd_row_to_list().setViewMode();
        getForm().getDettaglioUtente().getNuovaPassword().setViewMode();
    }

    private void setDettaglioUtenteEditMode(Status status) {
        if (status.equals(Status.update)) {
            getForm().getDettaglioUtente().getNm_cognome_user().setEditMode();
            getForm().getDettaglioUtente().getNm_nome_user().setEditMode();
            getForm().getDettaglioUtente().getNm_userid().setEditMode();
            getForm().getDettaglioUtente().getCd_fisc().setEditMode();
            getForm().getDettaglioUtente().getDs_email().setEditMode();
            getForm().getDettaglioUtente().getDs_email_secondaria().setEditMode();
            getForm().getDettaglioUtente().getFl_contr_ip().setEditMode();
            getForm().getDettaglioUtente().getFl_err_replic().setEditMode();
            getForm().getDettaglioUtente().getTipo_user().setEditMode();
            getForm().getDettaglioUtente().getTipo_auth().setEditMode();
            getForm().getDettaglioUtente().getDt_ini_cert().setEditMode();
            getForm().getDettaglioUtente().getDt_fin_cert().setEditMode();
            getForm().getDettaglioUtente().getDl_cert_client().setEditMode();
            getForm().getDettaglioUtente().getId_sistema_versante().setEditMode();
            getForm().getDettaglioUtente().getFl_resp_ente_convenz().setEditMode();
            getForm().getDettaglioUtente().getFl_abil_enti_colleg_autom().setEditMode();
            getForm().getDettaglioUtente().getFl_abil_organiz_autom().setEditMode();
            getForm().getDettaglioUtente().getFl_abil_fornit_autom().setEditMode();
        } else if (status.equals(Status.view)) {
            getForm().getDettaglioUtente().getNm_cognome_user().setViewMode();
            getForm().getDettaglioUtente().getNm_nome_user().setViewMode();
            getForm().getDettaglioUtente().getNm_userid().setViewMode();
            getForm().getDettaglioUtente().getCd_fisc().setViewMode();
            getForm().getDettaglioUtente().getDs_email().setViewMode();
            getForm().getDettaglioUtente().getDs_email_secondaria().setViewMode();
            getForm().getDettaglioUtente().getFl_contr_ip().setViewMode();
            getForm().getDettaglioUtente().getFl_err_replic().setViewMode();
            getForm().getDettaglioUtente().getTipo_user().setViewMode();
            getForm().getDettaglioUtente().getTipo_auth().setViewMode();
            getForm().getDettaglioUtente().getDt_ini_cert().setViewMode();
            getForm().getDettaglioUtente().getDt_fin_cert().setViewMode();
            getForm().getDettaglioUtente().getDl_cert_client().setViewMode();
            getForm().getDettaglioUtente().getId_sistema_versante().setViewMode();
            getForm().getDettaglioUtente().getFl_resp_ente_convenz().setViewMode();
            getForm().getDettaglioUtente().getFl_abil_enti_colleg_autom().setViewMode();
            getForm().getDettaglioUtente().getFl_abil_organiz_autom().setViewMode();
            getForm().getDettaglioUtente().getFl_abil_fornit_autom().setViewMode();
        }
    }

    private void manageAmbienteEnteAppartenenzaForUpdate(BigDecimal idRichGestUser) throws EMFError {
        // Siccome sono in modifica (abilitazioni o ente di appartenenza) posso avere le seguenti informazioni
        // Sicuro
        BigDecimal idUserIam = getForm().getDettaglioUtente().getId_user_iam().parse();
        // Facoltativo
        BigDecimal idAmbienteEnteConvenzAppart = getForm().getDettaglioUtente().getId_amb_ente_convenz_appart().parse();
        // Sicuro
        BigDecimal idEnteSiam = getForm().getDettaglioUtente().getId_ente_siam_appart().parse();

        getForm().getDettaglioUtente().getNm_userid().setViewMode();

        // Se non è definita una richiesta
        if (idRichGestUser == null) {
            if (getForm().getDettaglioUtente().getTipo_user().parse().equals("NON_DI_SISTEMA")) {
                // utente e’ stato creato senza richiesta
                if (!amministrazioneUtentiEjb.existsRichiestaUtente(idUserIam)) {
                    DecodeMap mappaAmbienteEntiConvenzAppart = new DecodeMap();
                    mappaAmbienteEntiConvenzAppart.populatedMap(
                            entiConvenzionatiEjb
                                    .getUsrVAbilAmbEnteConvenzTableBean(new BigDecimal(getUser().getIdUtente())),
                            "id_ambiente_ente_convenz", "nm_ambiente_ente_convenz");
                    getForm().getDettaglioUtente().getId_amb_ente_convenz_appart()
                            .setDecodeMap(mappaAmbienteEntiConvenzAppart);
                    if (idAmbienteEnteConvenzAppart != null) {
                        getForm().getDettaglioUtente().getId_amb_ente_convenz_appart()
                                .setValue("" + idAmbienteEnteConvenzAppart);
                    }

                    if (idAmbienteEnteConvenzAppart != null) {
                        OrgVRicEnteConvenzTableBean ricEnteConvenz = entiConvenzionatiEjb
                                .getOrgVRicEnteConvenzTableBean(idAmbienteEnteConvenzAppart);
                        getForm().getDettaglioUtente().getId_ente_siam_appart().setDecodeMap(
                                DecodeMap.Factory.newInstance(ricEnteConvenz, "id_ente_convenz", "nm_ente_convenz"));
                    } else {
                        OrgVRicEnteNonConvenzTableBean ricEnteNonConvenz = entiConvenzionatiEjb
                                .getOrgVRicEnteNonConvenzAbilTableBean(BigDecimal.valueOf(getUser().getIdUtente()),
                                        null);
                        getForm().getDettaglioUtente().getId_ente_siam_appart().setDecodeMap(
                                DecodeMap.Factory.newInstance(ricEnteNonConvenz, "id_ente_siam", "nm_ente_siam"));
                    }

                    if (idEnteSiam != null) {
                        getForm().getDettaglioUtente().getId_ente_siam_appart().setValue("" + idEnteSiam);
                    }

                    getForm().getDettaglioUtente().getId_amb_ente_convenz_appart().setEditMode();
                    getForm().getDettaglioUtente().getId_ente_siam_appart().setEditMode();
                } else {
                    getForm().getDettaglioUtente().getId_amb_ente_convenz_appart().setViewMode();
                    getForm().getDettaglioUtente().getId_ente_siam_appart().setViewMode();
                }
            } else {
                getForm().getDettaglioUtente().getId_amb_ente_convenz_appart().setViewMode();
                getForm().getDettaglioUtente().getId_ente_siam_appart().setViewMode();
            }
        } // Se è definita una richiesta di modifica
        else {
            if (amministrazioneUtentiEjb.existsRichiestaUtenteDiversaAzione(idRichGestUser, idUserIam,
                    ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ENTE_APPARTENENZA.getDescrizione())) {
                getForm().getDettaglioUtente().getId_amb_ente_convenz_appart().setViewMode();
                getForm().getDettaglioUtente().getId_ente_siam_appart().setViewMode();
            } else if (amministrazioneUtentiEjb.existsRichiestaUtenteAzione(idRichGestUser, idUserIam,
                    ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ENTE_APPARTENENZA.getDescrizione())) {
                UsrAppartUserRichRowBean azioneRichiesta = amministrazioneUtentiEjb.getRichiestaUtenteAzioneRowBean(
                        idRichGestUser, idUserIam,
                        ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ENTE_APPARTENENZA.getDescrizione());
                BaseTable ambientiTB = new BaseTable();
                // POPOLAMENTO COMBO AMBIENTE DI APPARTENENZA UTENTE
                ambientiTB = entiConvenzionatiEjb.getAmbientiDaUsrVLisEntiModifAppEnteTableBean(idRichGestUser,
                        getUser().getIdUtente(), idUserIam);
                getForm().getDettaglioUtente().getId_amb_ente_convenz_appart().setDecodeMap(DecodeMap.Factory
                        .newInstance(ambientiTB, "id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));
                getForm().getDettaglioUtente().getId_amb_ente_convenz_appart().setEditMode();
                getForm().getDettaglioUtente().getId_ente_siam_appart().setEditMode();

                if (ambientiTB.size() >= 1) {
                    // incluso l'elemento vuoto
                    getForm().getDettaglioUtente().getId_amb_ente_convenz_appart().setDecodeMap(DecodeMap.Factory
                            .newInstance(ambientiTB, "id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));
                } else {
                    getForm().getDettaglioUtente().getId_amb_ente_convenz_appart().setDecodeMap(new DecodeMap());
                }

                BaseTable entiTB = new BaseTable();
                // POPOLAMENTO COMBO ENTE DI APPARTENENZA UTENTE
                // Se la lista ambienti è vuota, significa che ho enti non convenzionati
                if (ambientiTB.isEmpty()) {
                    entiTB = entiConvenzionatiEjb.getEntiDaUsrVLisEntiModifAppEnteTableBean(idRichGestUser,
                            getUser().getIdUtente(), idUserIam, null);
                    getForm().getDettaglioUtente().getId_ente_siam_appart()
                            .setDecodeMap(DecodeMap.Factory.newInstance(entiTB, "id_ente_siam", "nm_ente_siam"));
                } else {
                    entiTB = entiConvenzionatiEjb.getEntiDaUsrVLisEntiModifAppEnteTableBean(idRichGestUser,
                            getUser().getIdUtente(), idUserIam, azioneRichiesta.getBigDecimal("id_ambiente_ente_user"));
                    getForm().getDettaglioUtente().getId_ente_siam_appart()
                            .setDecodeMap(DecodeMap.Factory.newInstance(entiTB, "id_ente_siam", "nm_ente_siam"));
                }

                BigDecimal idAmbienteEnteUser = azioneRichiesta.getBigDecimal("id_ambiente_ente_user");
                BigDecimal idEnteUser = azioneRichiesta.getIdEnteUser();

                getForm().getDettaglioUtente().getId_amb_ente_convenz_appart().setValue("" + idAmbienteEnteUser);
                getForm().getDettaglioUtente().getId_ente_siam_appart().setValue("" + idEnteUser);
            }
        }
    }

    /**
     * Chiusura primo step: - Valida i dati inseriti e poi carica il secondo step, altrimenti mostra il messaggio di
     * errore validazione
     *
     * @return true se i dati sono validati
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public boolean inserimentoWizardPasso1OnExit() throws EMFError {
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
        boolean result = true;
        getForm().getDettaglioUtente().post(getRequest());
        // Inserisco di default al campo "Errori di replica" il valore NO
        getForm().getDettaglioUtente().getFl_err_replic().setValue("0");
        // Controllo formato campi e obbligatorietà
        if (getForm().getDettaglioUtente().validate(getMessageBox())) {
            if ((getForm().getDettaglioUtente().getTipo_user().parse().equals(ApplEnum.TipoUser.AUTOMA.name())
                    || getForm().getDettaglioUtente().getTipo_user().parse()
                            .equals(ApplEnum.TipoUser.PERSONA_FISICA.name()))
                    && getForm().getDettaglioUtente().getId_sistema_versante().parse() == null) {
                String tipologiaUtente = getForm().getDettaglioUtente().getTipo_user().parse();
                getMessageBox().addError("Il tipo utente \u00E8 " + tipologiaUtente
                        + ". \u00C8 obbligatorio indicare il sistema versante");
                result = false;
            }
            // Se ho inserito il sistema versante, eseguo i controlli di conformità tipo utente / sistema versante
            if (getForm().getDettaglioUtente().getId_sistema_versante().parse() != null) {
                String errore = amministrazioneUtentiEjb.checkTipoUtenteSistemaVersante(
                        getForm().getDettaglioUtente().getTipo_user().parse(),
                        getForm().getDettaglioUtente().getId_sistema_versante().parse(),
                        getForm().getDettaglioUtente().getId_ente_siam_appart().parse());
                if (errore != null) {
                    getMessageBox().addError(errore);
                    result = false;
                }
            }

            if (result) {
                result = checkUsername(getForm().getDettaglioUtente().getNm_userid().parse());
            }
            // Controllo date certificato
            DettaglioUtente d = getForm().getDettaglioUtente();
            if (d.getTipo_auth().parse() != null
                    && d.getTipo_auth().parse().equals(ApplEnum.TipoAuth.AUTH_CERT.name())) {
                if (d.getDl_cert_client().parse() == null || d.getDl_cert_client().parse().isEmpty()) {
                    getMessageBox().addError("Inserire il certificato client");
                }
                if (d.getDt_ini_cert().parse() == null) {
                    getMessageBox().addError("Inserire la data inizio certificato");
                }
                if (d.getDt_fin_cert().parse() == null) {
                    getMessageBox().addError("Inserire la data fine certificato");
                }
                if (d.getDt_fin_cert().parse() != null && d.getDt_ini_cert().parse() != null
                        && d.getDt_ini_cert().parse().after(d.getDt_fin_cert().parse())) {
                    getMessageBox().addError(
                            "La data inizio validità del certificato non può essere superiore alla data fine validità");
                }
            }
            if (getMessageBox().hasError()) {
                result = false;
            }

            if (!getMessageBox().hasError()) {
                // Verifico i dati della richiesta
                BigDecimal idRichGestUser = getForm().getRichiestaWizard().getId_rich_gest_user().parse();
                if (idRichGestUser != null) {
                    String nmUserid = getForm().getDettaglioUtente().getNm_userid().parse();
                    String nmCognomeUser = getForm().getDettaglioUtente().getNm_cognome_user().parse();
                    String nmNomeUser = getForm().getDettaglioUtente().getNm_nome_user().parse();
                    BigDecimal idAppartUserRich = null;
                    if (getForm().getListaUtenti().getStatus().equals(Status.insert)) {
                        List<String> listaTiAzioneRich = new ArrayList<>();
                        listaTiAzioneRich.add(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_CREAZIONE.getDescrizione());
                        if (!amministrazioneUtentiEjb.existsAzioneUtente(idRichGestUser, listaTiAzioneRich,
                                nmCognomeUser, nmNomeUser, nmUserid, "0")) {
                            getMessageBox().addError(
                                    "La richiesta di gestione utenti specificata non presenta una azione di creazione utente relativa all'utente da creare non evasa");
                            result = false;
                        } else {
                            idAppartUserRich = amministrazioneUtentiEjb.getAzioneUtente(idRichGestUser,
                                    listaTiAzioneRich, nmCognomeUser, nmNomeUser, nmUserid, "0");
                            if (idAppartUserRich != null) {
                                getForm().getRichiestaWizard().getId_appart_user_rich().setValue("" + idAppartUserRich);
                            }
                        }
                    } else if (getForm().getListaUtenti().getStatus().equals(Status.update)) {
                        // Recupero l'id azione in base allo userId dell'utente

                        // Se arrivo dal bottone esegui, ho anche l'id della azione
                        BigDecimal idUserIam = getForm().getDettaglioUtente().getId_user_iam().parse();
                        List<String> listaTiAzioneRich = new ArrayList<>();
                        listaTiAzioneRich.add(
                                ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ABILITAZIONI.getDescrizione());
                        listaTiAzioneRich.add(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ENTE_APPARTENENZA
                                .getDescrizione());
                        listaTiAzioneRich.add(
                                ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ANAGRAFICA.getDescrizione());
                        idAppartUserRich = amministrazioneUtentiEjb.getAzioneUtente(idRichGestUser, listaTiAzioneRich,
                                idUserIam);
                        if (idAppartUserRich != null) {
                            getForm().getRichiestaWizard().getId_appart_user_rich().setValue("" + idAppartUserRich);
                        }
                        if (idAppartUserRich != null && idUserIam != null) {
                            // Verifica che esista una azione di modifica abilitazioni per quell'utente

                            if (!amministrazioneUtentiEjb.existsAzioneUtente(idRichGestUser, idAppartUserRich,
                                    listaTiAzioneRich, idUserIam, null)) {
                                // Verifica che esista una azione di creazione per quell'utente, EVASA
                                if (!amministrazioneUtentiEjb.existsAzioneUtente(idRichGestUser,
                                        ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_CREAZIONE.getDescrizione(),
                                        nmCognomeUser, nmNomeUser, nmUserid, "1")) {
                                    getMessageBox().addError("La richiesta di gestione utenti specificata non presenta "
                                            + "una azione di modifica abilitazioni relativa all'utente da modificare non evasa, "
                                            + "oppure presenta una azione di modifica abilitazioni relativa all'utente da "
                                            + "modificare gi\u00E0 evasa ma la richiesta non \u00E8 l'ultima in cui \u00E8 coinvolto l'utente, "
                                            + "oppure non presenta una azione di creazione relativa all'utente da modificare gi\u00E0 evasa");
                                    result = false;
                                }
                            } else {
                                // Verifica che la richiesta inserita (SE EVASA) sia l'ultima definita per quell'utente
                                final UsrRichGestUserRowBean usrRichGest = amministrazioneUtentiEjb
                                        .getUsrRichGestUserRowBean(idRichGestUser);
                                if (usrRichGest.getTiStatoRichGestUser()
                                        .equals(ConstUsrRichGestUser.TiStatoRichGestUser.EVASA.name())
                                        && !amministrazioneUtentiEjb.isLastRichiestaForUser(idUserIam, idRichGestUser,
                                                listaTiAzioneRich)) {
                                    getMessageBox().addError("La richiesta di gestione utenti specificata non presenta "
                                            + "una azione di modifica abilitazioni relativa all'utente da modificare non evasa, "
                                            + "oppure presenta una azione di modifica abilitazioni relativa all'utente da "
                                            + "modificare gi\u00E0 evasa ma la richiesta non \u00E8 l'ultima in cui \u00E8 coinvolto l'utente, "
                                            + "oppure presenta una azione di creazione relativa all'utente da modificare gi\u00E0 evasa");
                                    result = false;
                                }
                            }
                        } else {
                            if (!amministrazioneUtentiEjb.existsAzioneUtente(idRichGestUser, null, listaTiAzioneRich,
                                    idUserIam, null)) {
                                if (!amministrazioneUtentiEjb.existsAzioneUtente(idRichGestUser,
                                        ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_CREAZIONE.getDescrizione(),
                                        nmCognomeUser, nmNomeUser, nmUserid, "1")) {
                                    getMessageBox().addError("La richiesta di gestione utenti specificata non presenta "
                                            + "una azione di modifica abilitazioni relativa all'utente da modificare non evasa, "
                                            + "oppure presenta una azione di modifica abilitazioni relativa all'utente da "
                                            + "modificare gi\u00E0 evasa ma la richiesta non \u00E8 l'ultima in cui \u00E8 coinvolto l'utente, "
                                            + "oppure presenta una azione di creazione relativa all'utente da modificare gi\u00E0 evasa");
                                    result = false;
                                }
                            } else {
                                // Verifica che la richiesta inserita (SE EVASA) sia l'ultima definita per quell'utente
                                final UsrRichGestUserRowBean usrRichGest = amministrazioneUtentiEjb
                                        .getUsrRichGestUserRowBean(idRichGestUser);
                                if (usrRichGest.getTiStatoRichGestUser()
                                        .equals(ConstUsrRichGestUser.TiStatoRichGestUser.EVASA.name())
                                        && !amministrazioneUtentiEjb.isLastRichiestaForUser(idUserIam, idRichGestUser,
                                                listaTiAzioneRich)) {
                                    getMessageBox().addError("La richiesta di gestione utenti specificata non presenta "
                                            + "una azione di modifica abilitazioni relativa all'utente da modificare non evasa, "
                                            + "oppure presenta una azione di modifica abilitazioni relativa all'utente da "
                                            + "modificare gi\u00E0 evasa ma la richiesta non \u00E8 l'ultima in cui \u00E8 coinvolto l'utente, "
                                            + "oppure presenta una azione di creazione relativa all'utente da modificare gi\u00E0 evasa");
                                    result = false;
                                }
                            }
                        }
                    }
                }
            }
            if (!getMessageBox().hasError()) {
                String tipoUser = getForm().getDettaglioUtente().getTipo_user().parse();
                // Se sono in insert
                if (getForm().getListaUtenti().getStatus().equals(Status.insert)) {
                    if (amministrazioneUtentiEjb
                            .checkUserExists(getForm().getDettaglioUtente().getNm_userid().parse())) {
                        getMessageBox().addError("Lo userid dell'utente non è univoco!");
                        result = false;
                    }
                } else if (getForm().getListaUtenti().getStatus().equals(Status.update)) {
                    // Verifico se ci sono state modifiche ai dati utente,
                    // nel qual caso imposto come modificata ogni applicazione
                    String nmUserid = getForm().getDettaglioUtente().getNm_userid().parse();
                    String nmCognomeUser = getForm().getDettaglioUtente().getNm_cognome_user().parse();
                    String nmNomeUser = getForm().getDettaglioUtente().getNm_nome_user().parse();
                    String cdFisc = getForm().getDettaglioUtente().getCd_fisc().parse();
                    String dsEmail = getForm().getDettaglioUtente().getDs_email().parse();
                    String dsEmailSecondaria = getForm().getDettaglioUtente().getDs_email_secondaria().parse();
                    String flContrIp = getForm().getDettaglioUtente().getFl_contr_ip().parse();
                    String flAttivo = getForm().getDettaglioUtente().getFl_attivo().parse();

                    if (amministrazioneUtentiEjb.checkModificheCampiUtente(nmUserid, nmCognomeUser, nmNomeUser,
                            flAttivo, cdFisc, dsEmail, dsEmailSecondaria, flContrIp, tipoUser)
                            && !tipoUser.equals(ApplEnum.TipoUser.NON_DI_SISTEMA.name())) {
                        Set<BigDecimal> applicationsEditList = getApplicationsEditList();
                        for (UsrUsoUserApplicRowBean row : (UsrUsoUserApplicTableBean) getForm().getApplicazioniList()
                                .getTable()) {
                            applicationsEditList.add(row.getIdApplic());
                        }
                        getSession().setAttribute("applicationsEditList", applicationsEditList);
                    }
                }
            }
        } else {
            result = false;
        }

        /*
         * Se non ho impostato a no il flag sull'indirizzo IP, non mostro il relativo step di inserimento
         */
        String flContrIp = getForm().getDettaglioUtente().getFl_contr_ip().parse();
        if (flContrIp != null && flContrIp.equals("0")) {
            getForm().getInserimentoWizard().getPassoIp().setHidden(true);
        } else if (flContrIp != null && flContrIp.equals("1")) {
            getForm().getInserimentoWizard().getPassoIp().setHidden(false);
        }

        // Validazione campo Tipo User in inserimento/modifica
        if (result) {
            if (getForm().getListaUtenti().getStatus().equals(Status.update)) {
                result = canModifyTipoUser(getUser().getIdUtente(),
                        getForm().getDettaglioUtente().getNm_userid().parse());
            } else if (getForm().getListaUtenti().getStatus().equals(Status.insert)) {
                result = canInsertTipoUser(getUser().getIdUtente());
            }
        }

        if (!getMessageBox().hasError()) {
            String tipoUser = getForm().getDettaglioUtente().getTipo_user().parse();
            if (tipoUser != null) {
                if (tipoUser.equals(ApplEnum.TipoUser.NON_DI_SISTEMA.name())) {
                    getForm().getInserimentoWizard().getPassoApplic().setHidden(true);
                    getForm().getInserimentoWizard().getPasso2().setHidden(true);
                } else {
                    getForm().getInserimentoWizard().getPassoApplic().setHidden(false);
                    getForm().getInserimentoWizard().getPasso2().setHidden(false);
                }
            }
        }

        return result;
    }

    private boolean canModifyTipoUser(Long idUserIamCorrente, String nmUserId) throws EMFError {
        String tipoUserCorrente = ((UsrUserRowBean) amministrazioneUtentiEjb
                .getUserRowBean(new BigDecimal(idUserIamCorrente))).getTipoUser();
        String tipoUserDaModificareDB = ((UsrUserRowBean) amministrazioneUtentiEjb.getUserRowBean(nmUserId))
                .getTipoUser();
        String tipoUserDaModificareOnline = getForm().getDettaglioUtente().getTipo_user().parse();
        // Se io amministratore sono un AUTOMA, non posso modificare il tipoUser dell'utente che sto trattando
        if (tipoUserCorrente.equals(ApplEnum.TipoUser.AUTOMA.name())) {
            // Ho modificato il campo
            if (!tipoUserDaModificareDB.equals(tipoUserDaModificareOnline)) {
                getMessageBox().addError(
                        "ATTENZIONE: l'utente amministratore di tipo AUTOMA non pu\u00f2 modificare il campo Tipologia utente");
                return false;
            } else {
                return true;
            }
        } // Se io amministratore sono una PERSONA_FISICA, posso lasciare il tipoUser che sto trattando com'è
          // o al massimo farlo diventare una PERSONA_FISICA o AUTOMA
        else if (tipoUserCorrente.equals(ApplEnum.TipoUser.PERSONA_FISICA.name())) {
            if (tipoUserDaModificareDB.equals(tipoUserDaModificareOnline)
                    || tipoUserDaModificareOnline.equals(ApplEnum.TipoUser.PERSONA_FISICA.name())
                    || tipoUserDaModificareOnline.equals(ApplEnum.TipoUser.AUTOMA.name())) {
                return true;
            } else {
                getMessageBox().addError(
                        "ATTENZIONE: l'utente amministratore di tipo PERSONA_FISICA non pu\u00f2 modificare il campo Tipologia utente se non in PERSONA_FISICA o AUTOMA");
                return false;
            }
        } else {
            return true;
        }
    }

    private boolean canInsertTipoUser(Long idUserIamCorrente) throws EMFError {
        String tipoUserCorrente = ((UsrUserRowBean) amministrazioneUtentiEjb
                .getUserRowBean(new BigDecimal(idUserIamCorrente))).getTipoUser();
        String tipoUserDaInserireOnline = getForm().getDettaglioUtente().getTipo_user().parse();
        // Se io amministratore sono un AUTOMA, non posso inserire il tipoUser dell'utente che sto trattando
        if (tipoUserCorrente.equals(ApplEnum.TipoUser.AUTOMA.name())) {
            getMessageBox().addError(
                    "ATTENZIONE: l'utente amministratore di tipo AUTOMA non pu\u00f2 inserire il campo Tipologia utente");
            return false;
        } // Se io amministratore sono una PERSONA_FISICA, posso inserire PERSONA_FISICA oppure AUTOMA
        else if (tipoUserCorrente.equals(ApplEnum.TipoUser.PERSONA_FISICA.name())) {
            if (tipoUserDaInserireOnline.equals(ApplEnum.TipoUser.PERSONA_FISICA.name())
                    || tipoUserDaInserireOnline.equals(ApplEnum.TipoUser.AUTOMA.name())
                    || tipoUserDaInserireOnline.equals(ApplEnum.TipoUser.NON_DI_SISTEMA.name())) {
                return true;
            } else {
                getMessageBox().addError(
                        "ATTENZIONE: l'utente amministratore di tipo PERSONA_FISICA non pu\u00f2 inserire il campo Tipologia utente diverso da PERSONA_FISICA, AUTOMA o NON_DI_SISTEMA");
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * Carica il secondo step: - popola i set delle dichiarazioni di autorizzazione per i ruoli - mette in view mode i
     * dati inseriti nel primo step
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public void inserimentoWizardPasso2OnEnter() throws EMFError {
        getForm().getDichAbilFields().getAdd_row_to_list().setEditMode();
        getForm().getDettaglioUtente().setViewMode();

        checkUserType();
        abilitaEnteConvenzTab(getForm().getDettaglioUtente().getId_ente_siam_appart().parse(),
                getForm().getDettaglioUtente().getTipo_user().parse());

        UsrUsoRuoloUserDefaultTableBean ruoliDefaultList = (UsrUsoRuoloUserDefaultTableBean) getForm()
                .getRuoliDefaultList().getTable();

        populateSet(ruoliDefaultList);

        getForm().getWizardListsTabs().setCurrentTab(getForm().getWizardListsTabs().getListaWizardRuoliDefault());
        resetDichAbilFields();

        getForm().getDichAbilFields().setStatus(Status.insert);
    }

    private void checkUserType() throws EMFError {
        getSession().setAttribute("userType", (String) getForm().getDettaglioUtente().getTipo_user().parse());
        if (getForm().getDettaglioUtente().getTipo_user().parse().equals(ApplEnum.TipoUser.NON_DI_SISTEMA.name()) // ||
        ) {
            getForm().getDettaglioUtente().getCd_psw().setViewMode();
            getForm().getDettaglioUtente().getCd_psw().setHidden(true);

        } else {
            getForm().getDettaglioUtente().getCd_psw().setEditMode();
            getForm().getDettaglioUtente().getCd_psw().setHidden(false);
        }
    }

    private void abilitaEnteConvenzTab(BigDecimal idEnteSiam, String tipoUser) throws EMFError {
        String tipoEnte = amministrazioneUtentiEjb.getTipoEnte(idEnteSiam);
        if (tipoEnte.equals("CONVENZIONATO") && tipoUser.equals("PERSONA_FISICA")) {
            getForm().getWizardListsTabs().getListaWizardAbilEnteConvenz().setHidden(false);
            getForm().getAbilListsTabs().getListaDichAbilEnteConvenz().setHidden(false);
        } else {
            getForm().getWizardListsTabs().getListaWizardAbilEnteConvenz().setHidden(true);
            getForm().getAbilListsTabs().getListaDichAbilEnteConvenz().setHidden(true);
        }
    }

    /*
     * Imposta la form di inserimento dichiarazioni di abilitazioni come modificabile, abilitando i campi che servono in
     * base a quale tab è attivo
     */
    private void resetDichAbilFields() throws EMFError {
        getForm().getDichAbilFields().clear();
        getForm().getDichAbilFields().getDs_ruolo().setViewMode();
        getForm().getDichAbilFields().getNm_ruolo().setViewMode();
        getForm().getDichAbilFields().getDl_composito_organiz().setViewMode();
        getForm().getDichAbilFields().getNm_classe_tipo_dato().setViewMode();
        getForm().getDichAbilFields().getNm_tipo_dato().setViewMode();
        getForm().getDichAbilFields().getTi_scopo_dich_abil_dati().setViewMode();
        getForm().getDichAbilFields().getTi_scopo_dich_abil_organiz().setViewMode();
        getForm().getDichAbilFields().getTi_scopo_dich_abil_ente().setViewMode();
        getForm().getDichAbilFields().getNm_ambiente_ente_convenz().setViewMode();
        getForm().getDichAbilFields().getNm_ente_convenz().setViewMode();
        getForm().getDichAbilFields().getNm_ruolo().setDecodeMap(new DecodeMap());
        getForm().getDichAbilFields().getTi_scopo_dich_abil_organiz().setDecodeMap(new DecodeMap());
        getForm().getDichAbilFields().getNm_classe_tipo_dato().setDecodeMap(new DecodeMap());
        getForm().getDichAbilFields().getTi_scopo_dich_abil_dati().setDecodeMap(new DecodeMap());
        getForm().getDichAbilFields().getNm_tipo_dato().setDecodeMap(new DecodeMap());
        getForm().getDichAbilFields().getTi_scopo_dich_abil_ente().setDecodeMap(new DecodeMap());
        getForm().getDichAbilFields().getNm_ambiente_ente_convenz().setDecodeMap(new DecodeMap());
        getForm().getDichAbilFields().getNm_ente_convenz().setDecodeMap(new DecodeMap());

        // Popola la mappa relativa alle applicazioni in base ai valori
        // impostati nello step precedente
        UsrUsoUserApplicTableBean applicImpostate = (UsrUsoUserApplicTableBean) getForm().getApplicazioniList()
                .getTable();
        Set<BigDecimal> idApplicSet = new HashSet();
        for (UsrUsoUserApplicRowBean applicRowBean : applicImpostate) {
            idApplicSet.add(applicRowBean.getIdApplic());
        }
        DecodeMap mappaAplImp = new DecodeMap();
        getForm().getDichAbilFields().getNm_applic().setEditMode();
        mappaAplImp.populatedMap(applicImpostate, "id_applic", "nm_applic");
        getForm().getDichAbilFields().getNm_applic().setDecodeMap(mappaAplImp);

        // Setto in EditMode e popolo i campi in base al tab in cui mi trovo
        if (getForm().getWizardListsTabs().getListaWizardRuoliDefault().isCurrent()) {
            getForm().getDichAbilFields().getNm_ruolo().setDecodeMap(new DecodeMap());
            getForm().getDichAbilFields().getDs_ruolo().setValue("\u00A0");
            getForm().getDichAbilFields().getNm_ruolo().setEditMode();
        } else if (getForm().getWizardListsTabs().getListaWizardAbilOrganiz().isCurrent()) {
            AplApplicTableBean anoiamtb = userHelper.getAplApplicConOrganizzazioni(idApplicSet);
            mappaAplImp.populatedMap(anoiamtb, "id_applic", "nm_applic");
            getForm().getDichAbilFields().getNm_applic().setDecodeMap(mappaAplImp);
            boolean isEnteAmministratore = amministrazioneUtentiEjb
                    .getTipoEnteConvenzNonConvenz(getForm().getDettaglioUtente().getId_ente_siam_appart().parse())
                    .equals("AMMINISTRATORE");
            getForm().getDichAbilFields().getTi_scopo_dich_abil_organiz()
                    .setDecodeMap(ComboGetter.getMappaDichOrganiz(isEnteAmministratore));
            getForm().getDichAbilFields().getTi_scopo_dich_abil_organiz().setEditMode();
            getForm().getDichAbilFields().getDl_composito_organiz().setDecodeMap(new DecodeMap());
            getForm().getDichAbilFields().getDl_composito_organiz().setEditMode();
        } else if (getForm().getWizardListsTabs().getListaWizardTipiDato().isCurrent()) {
            AplApplicTableBean anoiamtb = userHelper.getAplApplicConOrganizzazioni(idApplicSet);
            mappaAplImp.populatedMap(anoiamtb, "id_applic", "nm_applic");
            getForm().getDichAbilFields().getNm_applic().setDecodeMap(mappaAplImp);
            boolean isEnteAmministratore = amministrazioneUtentiEjb
                    .getTipoEnteConvenzNonConvenz(getForm().getDettaglioUtente().getId_ente_siam_appart().parse())
                    .equals("AMMINISTRATORE");
            getForm().getDichAbilFields().getTi_scopo_dich_abil_dati()
                    .setDecodeMap(ComboGetter.getMappaDichDato(isEnteAmministratore));
            getForm().getDichAbilFields().getTi_scopo_dich_abil_dati().setEditMode();
            getForm().getDichAbilFields().getDl_composito_organiz().setDecodeMap(new DecodeMap());
            getForm().getDichAbilFields().getDl_composito_organiz().setEditMode();
            getForm().getDichAbilFields().getNm_classe_tipo_dato().setDecodeMap(new DecodeMap());
            getForm().getDichAbilFields().getNm_classe_tipo_dato().setEditMode();
            getForm().getDichAbilFields().getNm_tipo_dato().setDecodeMap(new DecodeMap());
            getForm().getDichAbilFields().getNm_tipo_dato().setEditMode();
        } else if (getForm().getWizardListsTabs().getListaWizardAbilEnteConvenz().isCurrent()) {
            String tipoEnte = amministrazioneUtentiEjb
                    .getTipoEnteConvenzNonConvenz(getForm().getDettaglioUtente().getId_ente_siam_appart().parse());
            getForm().getDichAbilFields().getTi_scopo_dich_abil_ente()
                    .setDecodeMap(ComboGetter.getMappaDichAbilEnteConvenz(tipoEnte));
            getForm().getDichAbilFields().getTi_scopo_dich_abil_ente().setEditMode();
            getForm().getDichAbilFields().getNm_ambiente_ente_convenz().setDecodeMap(new DecodeMap());
            getForm().getDichAbilFields().getNm_ambiente_ente_convenz().setEditMode();
            getForm().getDichAbilFields().getNm_ente_convenz().setDecodeMap(new DecodeMap());
            getForm().getDichAbilFields().getNm_ente_convenz().setEditMode();
        }
    }

    /*
     * Inizializza le combo comuni
     */
    private void initCommonCombo() {
        getForm().getDichAbilFields().getNm_applic().clear();
        getForm().getDichAbilFields().getDl_composito_organiz().clear();
        getForm().getDichAbilFields().getDl_composito_organiz().setDecodeMap(new DecodeMap());
    }

    /**
     * Chiusura secondo step. Dato che i dati vengono già validati nell'inserimento in lista, questo step ricarica
     * semplicemente la pagina.
     *
     * @return true
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public boolean inserimentoWizardPasso2OnExit() throws EMFError {
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
        // SALVA L'UTENTE
        return true;
    }

    private void deleteRolesDichOrgDichTipiDatoSets() {
        AplApplicTableBean aplApplicTableBean = amministrazioneUtentiEjb.getAplApplicTableBean();
        for (AplApplicRowBean row : aplApplicTableBean) {
            getSession().setAttribute("rolesSet_" + row.getNmApplic(), new HashSet<>());
            getSession().setAttribute("scopoOrgSet_" + row.getNmApplic(), new HashSet<>());
            getSession().setAttribute("scopoTipiDatoSet_" + row.getNmApplic(), new HashSet<>());
        }
        getSession().setAttribute("scopoEnteConvenzSet", new HashSet<>());
    }

    /*
     * Metodo che carica in sessione il set di record presenti nella lista del dettaglio utente scelta di cui è stato
     * passato come parametro il tableBean. L'utilizzo dei set riguarda la fase dei controlli di coerenza in fase di
     * inserimento/modifica utente
     *
     * @param tableBean
     */
    private void populateSet(AbstractBaseTable tableBean) throws EMFError {
        deleteRolesDichOrgDichTipiDatoSets();
        if (tableBean instanceof UsrUsoRuoloUserDefaultTableBean) {
            Set<BigDecimal> rolesSet = new HashSet<BigDecimal>();
            for (UsrUsoRuoloUserDefaultRowBean ruolo : (UsrUsoRuoloUserDefaultTableBean) tableBean) {
                String applicazione = ruolo.getString("nm_applic");
                rolesSet = getRolesSet(applicazione);
                rolesSet.add(ruolo.getIdRuolo());
                getSession().setAttribute("rolesSet_" + applicazione, rolesSet);
            }
        } else if (tableBean instanceof UsrDichAbilOrganizTableBean
                && getForm().getWizardListsTabs().getListaWizardAbilOrganiz().isCurrent()) {
            Set<PairAuth> scopoSet = new HashSet<>();
            for (UsrDichAbilOrganizRowBean row : (UsrDichAbilOrganizTableBean) tableBean) {
                String applicazione = row.getString("nm_applic");
                scopoSet = getScopoOrgSet(applicazione);
                if (row.getTiScopoDichAbilOrganiz().equals(ActionEnums.ScopoDichAbilOrganiz.ALL_ORG.name())) {
                    scopoSet.add(new PairAuth(row.getTiScopoDichAbilOrganiz(), BigDecimal.ZERO));
                } else {
                    scopoSet.add(new PairAuth(row.getTiScopoDichAbilOrganiz(), row.getIdOrganizIam()));
                }
                getSession().setAttribute("scopoOrgSet_" + applicazione, scopoSet);
            }
        } else if (tableBean instanceof UsrDichAbilDatiTableBean
                && getForm().getWizardListsTabs().getListaWizardTipiDato().isCurrent()) {
            Set<PairAuth> scopoSet = new HashSet<>();
            for (UsrDichAbilDatiRowBean row : (UsrDichAbilDatiTableBean) tableBean) {
                String applicazione = row.getString("nm_applic");
                BigDecimal idClasseTipoDato = row.getIdClasseTipoDato();
                scopoSet = getScopoTipiDatoSet(applicazione);
                if (row.getTiScopoDichAbilDati().equals(ActionEnums.ScopoDichAbilOrganiz.ALL_ORG.name())) {
                    scopoSet.add(new PairAuth(idClasseTipoDato,
                            new PairAuth(row.getTiScopoDichAbilDati(), BigDecimal.ZERO)));
                } else {
                    scopoSet.add(new PairAuth(idClasseTipoDato,
                            new PairAuth(row.getTiScopoDichAbilDati(), row.getIdOrganizIam())));
                }
                getSession().setAttribute("scopoTipiDatoSet_" + applicazione, scopoSet);
            }
        } else if (tableBean instanceof UsrDichAbilEnteConvenzTableBean
                && getForm().getWizardListsTabs().getListaWizardAbilEnteConvenz().isCurrent()) {
            Set<PairAuth> scopoSet = new HashSet<PairAuth>();
            for (UsrDichAbilEnteConvenzRowBean row : (UsrDichAbilEnteConvenzTableBean) tableBean) {
                scopoSet = getScopoEnteConvenzSet();
                if (row.getTiScopoDichAbilEnte().equals(ActionEnums.ScopoDichAbilEnteConvenz.ALL_AMBIENTI.name())) {
                    scopoSet.add(
                            new PairAuth(row.getTiScopoDichAbilEnte(), new PairAuth(BigDecimal.ZERO, BigDecimal.ZERO)));
                } else if (row.getTiScopoDichAbilEnte()
                        .equals(ActionEnums.ScopoDichAbilEnteConvenz.UN_AMBIENTE.name())) {
                    scopoSet.add(new PairAuth(row.getTiScopoDichAbilEnte(),
                            new PairAuth(row.getIdAmbienteEnteConvenz(), BigDecimal.ZERO)));
                } else {
                    scopoSet.add(new PairAuth(row.getTiScopoDichAbilEnte(),
                            new PairAuth(BigDecimal.ZERO, row.getIdEnteConvenz())));
                }
                getSession().setAttribute("scopoEnteConvenzSet", scopoSet);
            }
        }
    }

    private void populateScopoTipoDatoSet(AbstractBaseTable tableBean) throws EMFError {
        Set<PairAuth> scopoSet = new HashSet<PairAuth>();
        for (UsrDichAbilDatiRowBean row : (UsrDichAbilDatiTableBean) tableBean) {
            String applicazione = row.getString("nm_applic");
            BigDecimal idClasseTipoDato = row.getIdClasseTipoDato();
            scopoSet = getScopoTipiDatoSet(applicazione);
            if (row.getTiScopoDichAbilDati().equals(ActionEnums.ScopoDichAbilOrganiz.ALL_ORG.name())) {
                scopoSet.add(
                        new PairAuth(idClasseTipoDato, new PairAuth(row.getTiScopoDichAbilDati(), BigDecimal.ZERO)));
            } else {
                scopoSet.add(new PairAuth(idClasseTipoDato,
                        new PairAuth(row.getTiScopoDichAbilDati(), row.getIdOrganizIam())));
            }
            getSession().setAttribute("scopoTipiDatoSet_" + applicazione, scopoSet);
        }
        String a = "";
    }

    private void populateRuoliPerOrganizzazioniSet(UsrVLisUsoRuoloDichTableBean tableBean) throws EMFError {
        getSession().setAttribute("orgRolesSet", new HashSet<PairAuth>());
        Set<BigDecimal> orgRolesSet = new HashSet<BigDecimal>();
        for (UsrVLisUsoRuoloDichRowBean ruolo : tableBean) {
            orgRolesSet = getOrgRolesSet();
            orgRolesSet.add(ruolo.getIdRuolo());
            getSession().setAttribute("orgRolesSet", orgRolesSet);
        }
    }

    @Secure(action = "Menu.AmministrazioneUtenti.GestioneUtenti")
    public void ricercaUtentiPage() throws EMFError {
        getUser().getMenu().reset();
        getUser().getMenu().select("Menu.AmministrazioneUtenti.GestioneUtenti");
        /* Preparo i filtri */
        //
        /* Carico la combo delle applicazioni in base all'utente corrente */
        getForm().getFiltriUtenti().getNm_applic()
                .setDecodeMap(comboEjb.getMappaApplicAbilitate(getUser().getIdUtente(), false));
        /* Setto i filtri generici */
        resetRicercaUtentiPageFiltriGenerici();
        /* Gestione sezione utenti archivisti */
        String cleanHistory = getRequest().getParameter(CLEAN_HISTORY);
        if (Boolean.parseBoolean(cleanHistory)) {
            getForm().getUtentiArchivistiPerSistemaVersante().reset();
            getForm().getUtentiArchivistiPerEnteConvenzionato().reset();
            getForm().getUtenteArchivistaPerEnteConvenzionato().reset();
            getForm().getReferentiPerEnteConvenzionato().reset();
            getForm().getListaUtenti().setUserOperations(true, true, true, true);
        } else {
            getForm().getListaUtenti().setUserOperations(false, false, false, false);
        }
        setUtentiArchivistiList();
        setReferentiDittaProduttriceList();
        setReferentiEnteList();
        // Nascondo il link aggiungi richiedente dalla lista
        getForm().getListaUtenti().getVisualizzaUtenteRichiedente().setHidden(true);
        getForm().getListaUtenti().getDownloadFileSessione().setHidden(true);

        forwardToPublisher(Application.Publisher.RICERCA_UTENTI);
    }

    public void ricercaUtentiArchivistiPage() throws EMFError {
        getUser().getMenu().reset();
        getUser().getMenu().select("Menu.AmministrazioneUtenti.GestioneUtenti");
        /* Preparo i filtri */
        //
        /* Carico la combo delle applicazioni in base all'utente corrente */
        getForm().getFiltriUtenti().getNm_applic()
                .setDecodeMap(comboEjb.getMappaApplicAbilitate(getUser().getIdUtente(), true));
        /* Setto i filtri generici */
        resetRicercaUtentiPageFiltriGenericiArchivisti();
        /* Filtro ricerca enti abilitati */
        getForm().getFiltriUtenti().getId_ente_convenz_abil()
                .setDecodeMap(getForm().getFiltriUtenti().getId_ente_convenz_appart().getDecodeMap());
        //
        /* Gestione sezione utenti archivisti */
        String cleanHistory = getRequest().getParameter(CLEAN_HISTORY);
        if (Boolean.parseBoolean(cleanHistory)) {
            getForm().getUtentiArchivistiPerSistemaVersante().reset();
            getForm().getUtentiArchivistiPerEnteConvenzionato().reset();
            getForm().getUtenteArchivistaPerEnteConvenzionato().reset();
            getForm().getReferentiDittaProduttricePerSistemaVersante().reset();
            getForm().getListaUtenti().setUserOperations(true, true, true, true);
        } else {
            getForm().getListaUtenti().setUserOperations(false, false, false, false);
        }
        setUtentiArchivistiList();
        setReferentiEnteList();
        // Nascondo il link aggiungi richiedente dalla lista
        getForm().getListaUtenti().getVisualizzaUtenteRichiedente().setHidden(true);
        getForm().getListaUtenti().getDownloadFileSessione().setHidden(true);
        forwardToPublisher(Application.Publisher.RICERCA_UTENTI);
    }

    public void ricercaUtentiReferentiPage() throws EMFError {
        getUser().getMenu().reset();
        getUser().getMenu().select("Menu.AmministrazioneUtenti.GestioneUtenti");
        /* Preparo i filtri */
        //
        /* Carico la combo delle applicazioni in base all'utente corrente */
        getForm().getFiltriUtenti().getNm_applic()
                .setDecodeMap(comboEjb.getMappaApplicAbilitate(getUser().getIdUtente(), true));
        /* Setto i filtri generici */
        resetRicercaUtentiPageFiltriGenerici();
        // Imposto i filtri con utenti attivi e di tipo PERSONA_FISICA o NON DI SISTEMA
        String[] valoriTipoUser = new String[] { ApplEnum.TipoUser.PERSONA_FISICA.name(),
                ApplEnum.TipoUser.NON_DI_SISTEMA.name() };
        getForm().getFiltriUtenti().getTipo_user().setValues(valoriTipoUser);
        getForm().getFiltriUtenti().getFl_attivo().setValue(ApplEnum.ComboFlag.SI.getValue());
        /* Filtro ricerca enti abilitati */
        getForm().getFiltriUtenti().getId_ente_convenz_abil()
                .setDecodeMap(getForm().getFiltriUtenti().getId_ente_convenz_appart().getDecodeMap());

        // Setto il valore di ambiente ed ente di appartenenza
        BigDecimal idEnteConvenz = (BigDecimal) getSession().getAttribute("ente_appart_referente");
        BigDecimal idAmbienteEnteConvez = entiConvenzionatiEjb.getOrgAmbienteEnteConvenzByEnteConvenz(idEnteConvenz)
                .getIdAmbienteEnteConvenz();
        String[] idAmbienteEnteConvenzStr = new String[] { idAmbienteEnteConvez.toString() };

        OrgVRicEnteConvenzTableBean ricEnteConvenz = entiConvenzionatiEjb.getOrgVRicEnteConvenzAbilTableBean(
                BigDecimal.valueOf(getUser().getIdUtente()), idAmbienteEnteConvez, null);
        getForm().getFiltriUtenti().getId_ente_convenz_appart()
                .setDecodeMap(DecodeMap.Factory.newInstance(ricEnteConvenz, "id_ente_convenz", "nm_ente_convenz"));

        String[] idEnteConvenzStr = new String[] { idEnteConvenz.toString() };
        getForm().getFiltriUtenti().getId_amb_ente_convenz_appart().setValues(idAmbienteEnteConvenzStr);
        getForm().getFiltriUtenti().getId_ente_convenz_appart().setValues(idEnteConvenzStr);
        //

        getForm().getFiltriUtenti().getFl_attivo().setViewMode();
        getForm().getFiltriUtenti().getTipo_user().setViewMode();

        /* Gestione sezione utenti archivisti */
        String cleanHistory = getRequest().getParameter(CLEAN_HISTORY);
        if (Boolean.parseBoolean(cleanHistory)) {
            getForm().getUtentiArchivistiPerSistemaVersante().reset();
            getForm().getUtentiArchivistiPerEnteConvenzionato().reset();
            getForm().getUtenteArchivistaPerEnteConvenzionato().reset();
            getForm().getReferentiDittaProduttricePerSistemaVersante().reset();
            getForm().getListaUtenti().setUserOperations(true, true, true, true);
        } else {
            getForm().getListaUtenti().setUserOperations(false, false, false, false);
        }
        setUtentiArchivistiList();
        setReferentiEnteList();
        // Nascondo il link aggiungi richiedente dalla lista
        getForm().getListaUtenti().getVisualizzaUtenteRichiedente().setHidden(true);
        getForm().getListaUtenti().getDownloadFileSessione().setHidden(true);
        forwardToPublisher(Application.Publisher.RICERCA_UTENTI);
    }

    public void ricercaAllUtentiPage() throws EMFError {
        /* Preparo i filtri */
        //
        /* Carico la combo delle applicazioni in base all'utente corrente */
        getForm().getFiltriUtenti().getNm_applic()
                .setDecodeMap(comboEjb.getMappaApplicAbilitate(getUser().getIdUtente(), false));
        /* Setto i filtri generici */
        resetRicercaUtentiPageFiltriGenerici();
        /* Gestione sezione utenti archivisti */
        String cleanHistory = getRequest().getParameter(CLEAN_HISTORY);
        if (Boolean.parseBoolean(cleanHistory)) {
            getForm().getUtentiArchivistiPerSistemaVersante().reset();
            getForm().getUtentiArchivistiPerEnteConvenzionato().reset();
            getForm().getUtenteArchivistaPerEnteConvenzionato().reset();
            getForm().getListaUtenti().setUserOperations(true, true, true, true);
        } else {
            getForm().getListaUtenti().setUserOperations(false, false, false, false);
        }
        setUtentiArchivistiList();
        setReferentiEnteList();
        setReferentiDittaProduttriceList();

        // Prepopolo il filtro dell'ente convenzionato con l'ente convenzionato dell'azione
        if (getForm().getAzioneDetail().getId_ente_user().parse() != null) {

            try {
                OrgEnteSiamRowBean enteSiam = entiConvenzionatiEjb
                        .getOrgEnteConvenzRowBean(getForm().getAzioneDetail().getId_ente_user().parse());

                if (enteSiam.getTiEnte().equals("CONVENZIONATO")) {
                    String[] valoriAmbiente = new String[] { "" + enteSiam.getIdAmbienteEnteConvenz() };
                    getForm().getFiltriUtenti().getId_amb_ente_convenz_appart().setValues(valoriAmbiente);
                    OrgVRicEnteConvenzTableBean ricEnteConvenz = entiConvenzionatiEjb
                            .getOrgVRicEnteConvenzTableBean(enteSiam.getIdAmbienteEnteConvenz());
                    getForm().getFiltriUtenti().getId_ente_convenz_appart().setDecodeMap(
                            DecodeMap.Factory.newInstance(ricEnteConvenz, "id_ente_convenz", "nm_ente_convenz"));
                } else {
                    OrgVRicEnteNonConvenzTableBean ricEnteNonConvenz = entiConvenzionatiEjb
                            .getOrgVRicEnteNonConvenzAbilTableBean(BigDecimal.valueOf(getUser().getIdUtente()), null);
                    getForm().getFiltriUtenti().getId_ente_convenz_appart().setDecodeMap(
                            DecodeMap.Factory.newInstance(ricEnteNonConvenz, "id_ente_siam", "nm_ente_siam"));
                }
                String[] valoriEnte = new String[] { "" + getForm().getAzioneDetail().getId_ente_user().parse() };
                getForm().getFiltriUtenti().getId_ente_convenz_appart().setValues(valoriEnte);

                // Se la richiesta è di disattivazione, prepopolo il filtro utente attivo a SI
                if (getForm().getAzioneDetail().getTi_azione_rich().parse()
                        .equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_DISATTIVAZIONE.getDescrizione())) {
                    getForm().getFiltriUtenti().getFl_attivo().setValue("1");
                }

            } catch (ParerUserError ex) {
                getMessageBox().addError(ex.getDescription());
            }

        }

        // Visualizzo il link aggiungi richiedente dalla lista
        getForm().getListaUtenti().getVisualizzaUtenteRichiedente().setHidden(true);
        getForm().getListaUtenti().getDownloadFileSessione().setHidden(true);
        if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_RICHIESTA)) {
            getForm().getListaUtenti().getVisualizzaUtenteRichiedente().setHidden(false);
            getForm().getListaUtenti().getDownloadFileSessione().setHidden(true);
        } else if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_AZIONE)) {
            getForm().getListaUtenti().getVisualizzaUtenteRichiedente().setHidden(true);
            getForm().getListaUtenti().getDownloadFileSessione().setHidden(false);
        }

        forwardToPublisher(Application.Publisher.RICERCA_UTENTI);
    }

    public void resetRicercaUtentiPageFiltriGenerici() throws EMFError {
        /* "Svuoto" le combo dipendenti dalla combo applicazioni */
        getForm().getFiltriUtenti().setEditMode();
        getForm().getFiltriUtenti().clear();
        getForm().getFiltriUtenti().getNm_ruolo_default().setDecodeMap(new DecodeMap());
        getForm().getFiltriUtenti().getDl_composito_organiz().setDecodeMap(new DecodeMap());
        getForm().getFiltriUtenti().getNm_ruolo_dich().setDecodeMap(new DecodeMap());

        getForm().getFiltriUtenti().getTipo_user().setDecodeMap(
                ComboGetter.getMappaSortedGenericEnum("tipo_user", ApplEnum.TipoUser.getComboRicercaUtenti()));
        getForm().getFiltriUtenti().getTi_stato_user().setDecodeMap(ComboGetter.getMappaTiStatoUser());

        getForm().getFiltriUtenti().getFl_attivo().setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
        getForm().getFiltriUtenti().getFl_err_replic().setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
        getForm().getFiltriUtenti().getFl_resp_ente_convenz().setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());

        // Sistemi versanti MEV #21763
        DecodeMap mappaSistemiVersanti = new DecodeMap();
        mappaSistemiVersanti.populatedMap(sistemiVersantiEjb.getAplSistemaVersanteTableBean(), "id_sistema_versante",
                "nm_sistema_versante");
        getForm().getFiltriUtenti().getId_sistema_versante().setDecodeMap(mappaSistemiVersanti);

        // Ambiente enti convenzionati richiesta, abilitati e di appartenenza
        DecodeMap mappaAmbienteEntiConvenzRich = new DecodeMap();
        mappaAmbienteEntiConvenzRich.populatedMap(
                entiConvenzionatiEjb.getUsrVAbilAmbEnteConvenzTableBean(new BigDecimal(getUser().getIdUtente())),
                "id_ambiente_ente_convenz", "nm_ambiente_ente_convenz");
        getForm().getFiltriUtenti().getId_ambiente_ente_convenz_rich().setDecodeMap(mappaAmbienteEntiConvenzRich);
        getForm().getFiltriUtenti().getId_ambiente_ente_convenz_abil().setDecodeMap(mappaAmbienteEntiConvenzRich);
        getForm().getFiltriUtenti().getId_amb_ente_convenz_appart().setDecodeMap(mappaAmbienteEntiConvenzRich);
        getForm().getFiltriUtenti().getId_ente_convenz_rich().setDecodeMap(new DecodeMap());
        getForm().getFiltriUtenti().getId_ente_convenz_abil().setDecodeMap(new DecodeMap());
        OrgVRicEnteNonConvenzTableBean ricEnteNonConvenz = entiConvenzionatiEjb
                .getOrgVRicEnteNonConvenzAbilTableBean(BigDecimal.valueOf(getUser().getIdUtente()), null);
        DecodeMap mappaEntiNonConvenz = new DecodeMap();
        mappaEntiNonConvenz.populatedMap(ricEnteNonConvenz, "id_ente_siam", "nm_ente_siam");
        getForm().getFiltriUtenti().getId_ente_convenz_appart().setDecodeMap(mappaEntiNonConvenz);
        getForm().getFiltriUtenti().getId_ente_convenz_rich().setDecodeMap(mappaEntiNonConvenz);

        /* Carico la lista organizzazioni in base alle abilitazioni dell'utente e all'applicazione */
        /* 17504 - Codice commentato inizialmente da lasciare */
        UsrVAbilOrganizTableBean usrOrgIammeTableBean = amministrazioneUtentiEjb
                .getOrganizAbilitate(new BigDecimal(getUser().getIdUtente()));
        DecodeMap usrOrganizIammeMap = DecodeMap.Factory.newInstance(usrOrgIammeTableBean, "id_organiz_iam",
                "dl_composito_organiz");
        getForm().getFiltriUtenti().getId_organiz_iam_rich().setDecodeMap(usrOrganizIammeMap);
        getForm().getListaUtenti().clear();
        getForm().getListaUtenti().setStatus(Status.view);
    }

    public void resetRicercaUtentiPageFiltriGenericiArchivisti() throws EMFError {
        /* "Svuoto" le combo dipendenti dalla combo applicazioni */
        getForm().getFiltriUtenti().getNm_ruolo_default().setDecodeMap(new DecodeMap());
        getForm().getFiltriUtenti().getDl_composito_organiz().setDecodeMap(new DecodeMap());
        getForm().getFiltriUtenti().getNm_ruolo_dich().setDecodeMap(new DecodeMap());

        getForm().getFiltriUtenti().getTipo_user().setDecodeMap(
                ComboGetter.getMappaSortedGenericEnum("tipo_user", ApplEnum.TipoUser.getComboRicercaUtenti()));
        String[] valoriTipoUser = new String[] { ApplEnum.TipoUser.PERSONA_FISICA.name() };
        getForm().getFiltriUtenti().getTipo_user().setValues(valoriTipoUser);
        getForm().getFiltriUtenti().getTi_stato_user().setDecodeMap(ComboGetter.getMappaTiStatoUser());

        getForm().getFiltriUtenti().getFl_attivo().setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
        getForm().getFiltriUtenti().getFl_attivo().setValue(ApplEnum.ComboFlag.SI.getValue());
        getForm().getFiltriUtenti().getFl_err_replic().setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
        getForm().getFiltriUtenti().getFl_resp_ente_convenz().setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());

        DecodeMap mappaSistemiVersanti = new DecodeMap();
        mappaSistemiVersanti.populatedMap(sistemiVersantiEjb.getAplSistemaVersanteTableBean(), "id_sistema_versante",
                "nm_sistema_versante");
        getForm().getFiltriUtenti().getId_sistema_versante().setDecodeMap(mappaSistemiVersanti);

        // Ambiente enti convenzionati richiesta, abilitati e di appartenenza
        DecodeMap mappaAmbienteEntiConvenzRich = new DecodeMap();
        mappaAmbienteEntiConvenzRich.populatedMap(
                entiConvenzionatiEjb.getUsrVAbilAmbEnteConvenzTableBean(new BigDecimal(getUser().getIdUtente())),
                "id_ambiente_ente_convenz", "nm_ambiente_ente_convenz");
        getForm().getFiltriUtenti().getId_ambiente_ente_convenz_rich().setDecodeMap(mappaAmbienteEntiConvenzRich);
        getForm().getFiltriUtenti().getId_ambiente_ente_convenz_abil().setDecodeMap(mappaAmbienteEntiConvenzRich);
        getForm().getFiltriUtenti().getId_ente_convenz_rich().setDecodeMap(new DecodeMap());
        getForm().getFiltriUtenti().getId_ente_convenz_abil().setDecodeMap(new DecodeMap());

        /* Carico la lista organizzazioni in base alle abilitazioni dell'utente e all'applicazione */
        BigDecimal idEnteConvenz = amministrazioneUtentiEjb.getUserRowBean(BigDecimal.valueOf(getUser().getIdUtente()))
                .getIdEnteSiam();
        BigDecimal idAmbienteEnteConvenz = entiConvenzionatiEjb.getOrgAmbienteEnteConvenzByEnteConvenz(idEnteConvenz)
                .getIdAmbienteEnteConvenz();
        /* 17504 */
        UsrVAbilOrganizTableBean usrOrgIammeTableBean = amministrazioneUtentiEjb
                .getOrganizAbilitate(new BigDecimal(getUser().getIdUtente()));
        UsrVAbilOrganizRowBean abilOrganizParam = amministrazioneUtentiEjb
                .getOrganizAbilitataByParamApplic(new BigDecimal(getUser().getIdUtente()), idAmbienteEnteConvenz);
        DecodeMap usrOrganizIammeMap = DecodeMap.Factory.newInstance(usrOrgIammeTableBean, "id_organiz_iam",
                "dl_composito_organiz");
        getForm().getFiltriUtenti().getId_organiz_iam_rich().setDecodeMap(usrOrganizIammeMap);
        if (abilOrganizParam != null) {
            getForm().getFiltriUtenti().getId_organiz_iam_rich().setValue("" + abilOrganizParam.getIdOrganizIam());
        }

        getForm().getFiltriUtenti().setEditMode();
        getForm().getFiltriUtenti().getTipo_user().setViewMode();
        getForm().getFiltriUtenti().getFl_attivo().setViewMode();

        getForm().getListaUtenti().clear();
        getForm().getListaUtenti().setStatus(Status.view);
    }

    private void setUtentiArchivistiList() throws EMFError {

        getForm().getUtentiArchivistiToEntiConvenzionatiSection().setHidden(true);
        getForm().getUtentiArchivistiToSistemiVersantiSection().setHidden(true);
        // GESTIONE RICERCA UTENTI ARCHIVISTI DA ENTE CONVENZIONATO (col bottone di selezione di un singolo utente)
        // Nascondo il bottone di seleziona utente artchivista (DA ENTE CONVENZIONATO)
        getForm().getListaUtenti().getVisualizzaUtenteArchivista().setHidden(true);
        // Lo mostro se o arrivo da ente convenzionato, o arrivo da sistemi versanti
        if (getForm().getUtenteArchivistaPerEnteConvenzionato().getId_ente_convenz().parse() != null) {
            getForm().getUtentiArchivistiToEntiConvenzionatiSection().setHidden(false);
            getForm().getListaUtenti().getVisualizzaUtenteArchivista().setHidden(false);
        }

        // GESTIONE RICERCA UTENTI ARCHIVISTI DA SISTEMA VERSANTE (con la lista degli utenti selezionati)
        if (getForm().getUtentiArchivistiPerSistemaVersante().getId_sistema_versante().parse() != null) {
            /* Inizializzo la lista degli utenti archivisti da aggiungere per il sistema versante */
            getForm().getUtentiArchivistiList().setTable(new BaseTable());
            getForm().getUtentiArchivistiList().getTable().setPageSize(10);
            getForm().getUtentiArchivistiList().getTable().first();
            getForm().getUtentiArchivistiToSistemiVersantiSection().setHidden(false);
        }

    }

    private void setReferentiEnteList() throws EMFError {
        getForm().getListaUtenti().getVisualizzaReferenteEnte().setHidden(true);
        if (getForm().getReferentePerEnteConvenzionato().getId_ente_convenz().parse() != null) {
            getForm().getListaUtenti().getVisualizzaReferenteEnte().setHidden(false);
        }
    }

    private void setReferentiDittaProduttriceList() throws EMFError {
        getForm().getReferentiDittaProduttriceToSistemiVersantiSection().setHidden(true);
        if (getForm().getReferentiDittaProduttricePerSistemaVersante().getId_sistema_versante().parse() != null) {
            getForm().getReferentiDittaProduttriceToSistemiVersantiSection().setHidden(false);
        }
        /* Inizializzo la lista dei referenti da aggiungere */
        getForm().getReferentiDittaProduttriceList().setTable(new BaseTable());
        getForm().getReferentiDittaProduttriceList().getTable().setPageSize(10);
        getForm().getReferentiDittaProduttriceList().getTable().first();
    }

    public void resetSchedulazioniReplicaUtentiPage() throws EMFError {
        getForm().getFiltriSchedulazioni().setEditMode();
        getForm().getFiltriSchedulazioni().clear();
        // Setto preimpostata il filtro della data con data odierna
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH);
        getForm().getFiltriSchedulazioni().getDt_reg_log_job_da().setValue(df.format(cal.getTime()));
        getForm().getFiltriSchedulazioni().getRepliche_bloccate().setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
        getForm().getStatoJob().clear();
        getForm().getSchedulazioniReplicaUtentiList().clear();
        getForm().getSchedulazioniReplicaUtentiList().setStatus(Status.view);
    }

    public void resetReplicaUtentiPage() throws EMFError {
        getForm().getFiltriReplica().getNm_applic()
                .setDecodeMap(comboEjb.getMappaApplicAbilitateRicercaRepliche(getUser().getIdUtente()));
        getForm().getFiltriReplica().getTi_oper_replic().setDecodeMap(ComboGetter.getMappaTiOperReplic());
        getForm().getFiltriReplica().getTi_stato_replic().setDecodeMap(ComboGetter.getMappaTiStatoReplic());

        getForm().getFiltriReplica().setEditMode();
        getForm().getFiltriReplica().clear();

        // Setto preimpostata il filtro della data con data odierna
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH);
        getForm().getFiltriReplica().getDt_log_user_da_replic().setValue(df.format(cal.getTime()));

        getForm().getReplicaUtentiList().clear();
        getForm().getReplicaUtentiList().setStatus(Status.view);
    }

    @Override
    public JSONObject triggerFiltriUtentiNm_applicOnTrigger() throws EMFError {
        getForm().getFiltriUtenti().post(getRequest());
        BigDecimal idApplic = getForm().getFiltriUtenti().getNm_applic().parse();

        /* Carico la lista dei ruoli di default in base all'applicazione */
        PrfRuoloTableBean prfRuoliDefaultTableBean = amministrazioneUtentiEjb.getRuoliByApplic(idApplic, TipoRuolo.DEF);
        DecodeMap ruoliDefaultMap = DecodeMap.Factory.newInstance(prfRuoliDefaultTableBean, "id_ruolo", "nm_ruolo");
        getForm().getFiltriUtenti().getNm_ruolo_default().setDecodeMap(ruoliDefaultMap);

        /* Carico la lista organizzazioni in base alle abilitazioni dell'utente e all'applicazione */
        UsrVAbilOrganizTableBean usrOrgIammeTableBean = amministrazioneUtentiEjb
                .getOrganizAbilitateByUserCorrente(new BigDecimal(getUser().getIdUtente()), idApplic);
        DecodeMap usrOrganizIammeMap = DecodeMap.Factory.newInstance(usrOrgIammeTableBean, "id_organiz_iam",
                "dl_composito_organiz");
        getForm().getFiltriUtenti().getDl_composito_organiz().setDecodeMap(usrOrganizIammeMap);

        /* Carico la lista dei ruoli in base all'applicazione */
        PrfRuoloTableBean prfRuoliDichTableBean = amministrazioneUtentiEjb.getRuoliByApplic(idApplic, TipoRuolo.DICH);
        DecodeMap ruoliDichMap = DecodeMap.Factory.newInstance(prfRuoliDichTableBean, "id_ruolo", "nm_ruolo");
        getForm().getFiltriUtenti().getNm_ruolo_dich().setDecodeMap(ruoliDichMap);

        return getForm().getFiltriUtenti().asJSON();
    }

    /**
     * Metodo caricato al click del pulsante ricerca utenti Esegue la ricerca in base ai filtri utenti impostati nella
     * form di ricerca
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public void ricercaUtenti() throws EMFError {
        getForm().getFiltriUtenti().post(getRequest());
        getSession().removeAttribute("fromRuoliSpecifici");

        // Mi porto dietro l'informazione che sto cercando per inserire utenti archivisti:
        // se non ho selezionato alcun ente di appartenenza utente,
        // dovrò ricercare per tutti e soli gli enti abilitati.
        List<BigDecimal> idEntiConvenzionatiAmministratori = new ArrayList<>();
        BigDecimal idUserIamCor = new BigDecimal(getUser().getIdUtente());
        if ((getForm().getUtentiArchivistiPerSistemaVersante().getId_sistema_versante().parse() != null
                || getForm().getUtenteArchivistaPerEnteConvenzionato().getId_ente_convenz().parse() != null)
                && getForm().getFiltriUtenti().getId_amb_ente_convenz_appart().parse() == null) {
            try {
                // Se ricerco per utenti archivisti e non popolo il filtro ambiente, devo cercare per i soli enti
                // amministratori
                OrgEnteSiamTableBean enteSiamTableBean = entiConvenzionatiEjb
                        .getOrgEnteSiamCollegUserAbilTableBeanByTiEnteConvenz(idUserIamCor,
                                ConstOrgEnteSiam.TiEnteConvenz.CONSERVATORE,
                                ConstOrgEnteSiam.TiEnteConvenz.AMMINISTRATORE);
                for (OrgEnteSiamRowBean enteSiamRowBean : enteSiamTableBean) {
                    idEntiConvenzionatiAmministratori.add(enteSiamRowBean.getIdEnteSiam());
                }
            } catch (ParerUserError e) {
                getMessageBox().addError(e.getDescription());
            }
        }

        // Controlli sui filtri ambiente ed ente
        if (getForm().getFiltriUtenti().getId_ambiente_ente_convenz_abil().parse() != null
                && getForm().getFiltriUtenti().getId_ente_convenz_abil().parse() == null) {
            getMessageBox().addError(
                    "Attenzione: è stato selezionato il filtro Ambiente enti convenzionati abilitati senza aver selezionato un ente abilitato");
        }
        if (getForm().getFiltriUtenti().getId_ambiente_ente_convenz_rich().parse() != null
                && getForm().getFiltriUtenti().getId_ente_convenz_rich().parse() == null) {
            getMessageBox().addError(
                    "Attenzione: è stato selezionato il filtro Ambiente enti convenzionati richiesta senza aver selezionato un ente richiesta");
        }
        if (getForm().getFiltriUtenti().getCd_fisc().parse() != null
                && getForm().getFiltriUtenti().getCd_fisc().parse().length() < 6) {
            getMessageBox()
                    .addError("Attenzione: se utilizzato, nel campo Codice fiscale vanno inseriti almeno 6 caratteri");
        }
        if (getForm().getFiltriUtenti().getFl_utenti_automa_cessati().parse() != null
                && getForm().getFiltriUtenti().getFl_utenti_automa_cessati().parse().equals("1")
                && getForm().getFiltriUtenti().getTipo_user().parse() != null
                && !getForm().getFiltriUtenti().getTipo_user().parse().isEmpty()
                && (getForm().getFiltriUtenti().getTipo_user().parse().contains(ApplEnum.TipoUser.PERSONA_FISICA.name())
                        || getForm().getFiltriUtenti().getTipo_user().parse()
                                .contains(ApplEnum.TipoUser.NON_DI_SISTEMA.name()))
                && getForm().getFiltriUtenti().getTi_stato_user().parse() != null
                && !getForm().getFiltriUtenti().getTi_stato_user().parse().isEmpty()) {
            getMessageBox().addError(
                    "Attenzione: si sta effettuando la ricerca di 'Utenti automa cessati', il filtro Tipologia utente deve essere valorizzato solo con AUTOMA e il filtro Stato utente non deve essere valorizzato");
        }

        if (!getMessageBox().hasError()) {
            if (getForm().getFiltriUtenti().validate(getMessageBox())) {
                TypeValidator validator = new TypeValidator(getMessageBox());
                Date[] validaDate = validator.validaDate(getForm().getFiltriUtenti().getDt_rich_gest_user_da().parse(),
                        null, null, getForm().getFiltriUtenti().getDt_rich_gest_user_a().parse(), null, null,
                        getForm().getFiltriUtenti().getDt_rich_gest_user_da().getName(),
                        getForm().getFiltriUtenti().getDt_rich_gest_user_a().getName());
                String cdRichGestUser = getForm().getFiltriUtenti().getCd_rich_gest_user().parse();
                BigDecimal idEnteConvenzRich = getForm().getFiltriUtenti().getId_ente_convenz_rich().parse();
                if (StringUtils.isNotBlank(cdRichGestUser) && idEnteConvenzRich == null) {
                    getMessageBox().addError(
                            "Per ricercare una richiesta deve essere inserito anche l'ente convenzionato della richiesta");
                }
                if (!getMessageBox().hasError()) {
                    UsrVLisUserTableBean utenti = amministrazioneUtentiEjb.getUsrVLisUserTableBean(
                            getForm().getFiltriUtenti(), validaDate, getUser().getIdUtente(),
                            idEntiConvenzionatiAmministratori);
                    getForm().getListaUtenti().setTable(utenti);
                    getForm().getListaUtenti().getTable().setPageSize(10);
                    getForm().getListaUtenti().getTable().first();
                    getForm().getInserimentoWizard().getPassoRichiesta().setHidden(false);
                }
            }
        }
        forwardToPublisher(Application.Publisher.RICERCA_UTENTI);
    }

    @Override
    public void pulisciUtenti() throws EMFError {
        resetRicercaUtentiPageFiltriGenerici();
        forwardToPublisher(Application.Publisher.RICERCA_UTENTI);
    }

    @Override
    public void attivaTriggerUtentiAutomaCessati() throws EMFError {
        getForm().getFiltriUtenti().post(getRequest());
        String flUtentiAutomaCessati = getForm().getFiltriUtenti().getFl_utenti_automa_cessati().parse();
        getForm().getFiltriUtenti().getTi_stato_user().setValue("");
        String[] automa;

        if (flUtentiAutomaCessati.equals("1")) {
            automa = new String[] { ApplEnum.TipoUser.AUTOMA.name() };
            getForm().getFiltriUtenti().getTipo_user().setValues(automa);
            getForm().getFiltriUtenti().getTi_stato_user().setViewMode();
            getForm().getFiltriUtenti().getTipo_user().setViewMode();
        } else {
            automa = new String[] { "" };
            getForm().getFiltriUtenti().getTipo_user().setValues(automa);
            getForm().getFiltriUtenti().getTi_stato_user().setEditMode();
            getForm().getFiltriUtenti().getTipo_user().setEditMode();
        }
        forwardToPublisher(Application.Publisher.RICERCA_UTENTI);
    }

    @Override
    public void tabListaIndirizziIpOnClick() throws EMFError {
        getForm().getDettaglioUtente().post(getRequest());
        getForm().getAbilListsTabs().setCurrentTab(getForm().getAbilListsTabs().getListaIndirizziIp());
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE);
    }

    @Override
    public void tabListaApplicazioniOnClick() throws EMFError {
        getForm().getDettaglioUtente().post(getRequest());
        getForm().getAbilListsTabs().setCurrentTab(getForm().getAbilListsTabs().getListaApplicazioni());
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE);
    }

    @Override
    public void tabListaRuoliDefaultOnClick() throws EMFError {
        getForm().getDettaglioUtente().post(getRequest());
        getForm().getAbilListsTabs().setCurrentTab(getForm().getAbilListsTabs().getListaRuoliDefault());
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE);
    }

    @Override
    public void tabListaDichAbilOrganizOnClick() throws EMFError {
        getForm().getDettaglioUtente().post(getRequest());
        getForm().getAbilListsTabs().setCurrentTab(getForm().getAbilListsTabs().getListaDichAbilOrganiz());
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE);
    }

    @Override
    public void tabListaDichAbilTipiDatoOnClick() throws EMFError {
        getForm().getDettaglioUtente().post(getRequest());
        getForm().getAbilListsTabs().setCurrentTab(getForm().getAbilListsTabs().getListaDichAbilTipiDato());
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE);
    }

    @Override
    public void tabListaDichAbilEnteConvenzOnClick() throws EMFError {
        getForm().getDettaglioUtente().post(getRequest());
        getForm().getAbilListsTabs().setCurrentTab(getForm().getAbilListsTabs().getListaDichAbilEnteConvenz());
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE);
    }

    @Override
    public void tabListaStatiUtenteOnClick() throws EMFError {
        getForm().getDettaglioUtente().post(getRequest());
        getForm().getAbilListsTabs().setCurrentTab(getForm().getAbilListsTabs().getListaStatiUtente());
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE);
    }

    @Override
    public void tabListaAbilOrganizOnClick() throws EMFError {
        getForm().getDettaglioUtente().post(getRequest());
        getForm().getAbilListsTabs().setCurrentTab(getForm().getAbilListsTabs().getListaAbilOrganiz());
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE);
    }

    @Override
    public void tabListaAbilEntiOnClick() throws EMFError {
        getForm().getDettaglioUtente().post(getRequest());
        getForm().getAbilListsTabs().setCurrentTab(getForm().getAbilListsTabs().getListaAbilEnti());
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE);
    }

    @Override
    public void duplicaUtente() throws EMFError {
        getSession().removeAttribute("passwordUtente");
        getSession().removeAttribute("userType");

        getForm().getDettaglioUtente().clear();
        getForm().getListaUtenti().setStatus(Status.insert);
        getForm().getDettaglioUtente().setEditMode();
        getForm().getDettaglioUtente().getCd_psw().setHidden(true);
        getForm().getRichiestaWizard().setEditMode();
        getForm().getRichiestaWizard().clear();
        getForm().getInserimentoWizard().getPassoRichiesta().setHidden(false);
        getForm().getRichiestaWizard().getId_ambiente_ente_convenz_rich()
                .setDecodeMap(DecodeMap.Factory.newInstance(
                        entiConvenzionatiEjb
                                .getUsrVAbilAmbEnteConvenzTableBean(BigDecimal.valueOf(getUser().getIdUtente())),
                        "id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));
        /* 17504 */
        UsrVAbilOrganizTableBean usrOrgIammeTableBean = amministrazioneUtentiEjb
                .getOrganizAbilitate(new BigDecimal(getUser().getIdUtente()));
        DecodeMap usrOrganizIammeMap = DecodeMap.Factory.newInstance(usrOrgIammeTableBean, "id_organiz_iam",
                "dl_composito_organiz");
        getForm().getRichiestaWizard().getId_organiz_iam_rich().setDecodeMap(usrOrganizIammeMap);
        getForm().getRichiestaWizard().getCd_registro_rich_gest_user().setDecodeMap(new DecodeMap());

        getForm().getWizardListsTabs().getListaWizardRuoliDefault().setCurrent(true);
        populateSet((UsrUsoRuoloUserDefaultTableBean) getForm().getRuoliDefaultList().getTable());

        // Setto a null gli id in modo che possano essere salvati nel nuovo utente
        for (UsrUsoUserApplicRowBean applic : (UsrUsoUserApplicTableBean) getForm().getApplicazioniList().getTable()) {
            applic.setIdUsoUserApplic(null);
        }

        for (UsrUsoRuoloUserDefaultRowBean ruolo : (UsrUsoRuoloUserDefaultTableBean) getForm().getRuoliDefaultList()
                .getTable()) {
            ruolo.setIdUsoRuoloUserDefault(null);
        }

        for (UsrDichAbilOrganizRowBean row : (UsrDichAbilOrganizTableBean) getForm().getDichAbilOrgList().getTable()) {
            row.setIdDichAbilOrganiz(null);
        }

        for (UsrDichAbilDatiRowBean row : (UsrDichAbilDatiTableBean) getForm().getDichAbilTipiDatoList().getTable()) {
            row.setIdDichAbilDati(null);
        }

        for (UsrDichAbilEnteConvenzRowBean row : (UsrDichAbilEnteConvenzTableBean) getForm()
                .getDichAbilEnteConvenzList().getTable()) {
            row.setIdDichAbilEnteConvenz(null);
        }

        DecodeMapIF combo = ComboGetter.getMappaGenericFlagSiNo();
        getForm().getDettaglioUtente().getFl_attivo().setDecodeMap(combo);

        getForm().getDettaglioUtente().getFl_attivo().setValue("1");
        getForm().getDettaglioUtente().getFl_attivo().setViewMode();

        getForm().getDettaglioUtente().getFl_contr_ip().setValue("0");
        getForm().getDettaglioUtente().getTipo_user().setValue(ApplEnum.TipoUser.PERSONA_FISICA.name());

        // Imposto le liste come non visualizzabili in dettaglio né modificabili
        getForm().getIndIpList().setUserOperations(false, false, false, true);
        getForm().getApplicazioniList().setUserOperations(false, false, false, true);
        getForm().getRuoliDefaultList().setUserOperations(false, false, false, true);
        getForm().getDichAbilOrgList().setUserOperations(false, false, false, true);
        getForm().getDichAbilTipiDatoList().setUserOperations(false, false, false, true);

        getForm().getInserimentoWizard().reset();
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
    }

    @Override
    public void ricercaSchedulazioni() throws EMFError {
        getForm().getFiltriSchedulazioni().getRicercaSchedulazioni().setDisableHourGlass(true);
        AmministrazioneUtentiForm.FiltriSchedulazioni filtri = getForm().getFiltriSchedulazioni();

        /* Esegue la post dei filtri compilati */
        filtri.post(getRequest());

        /* Valida i filtri per verificare quelli obbligatori */
        if (filtri.validate(getMessageBox())) {
            Date datada = filtri.getDt_reg_log_job_da().parse();
            Date dataa = filtri.getDt_reg_log_job_a().parse();
            BigDecimal oreda = filtri.getOre_dt_reg_log_job_da().parse();
            BigDecimal orea = filtri.getOre_dt_reg_log_job_a().parse();
            BigDecimal minutida = filtri.getMinuti_dt_reg_log_job_da().parse();
            BigDecimal minutia = filtri.getMinuti_dt_reg_log_job_a().parse();
            String descrizioneDataDa = filtri.getDt_reg_log_job_da().getHtmlDescription();
            String descrizioneDataA = filtri.getDt_reg_log_job_a().getHtmlDescription();

            String soloSbloccoRepliche = filtri.getRepliche_bloccate().parse();

            /* Valida i campi di ricerca */
            AmministrazioneUtentiValidator validator = new AmministrazioneUtentiValidator(getMessageBox());
            Date[] dateValidate = validator.validaDate(datada, oreda, minutida, dataa, orea, minutia, descrizioneDataDa,
                    descrizioneDataA);

            if (!getMessageBox().hasError()) {
                /* Setta la lista del job in base ai filtri di ricerca */
                boolean sbloccoRepliche = soloSbloccoRepliche != null ? soloSbloccoRepliche.equals("1") : false;
                getForm().getSchedulazioniReplicaUtentiList().setTable(amministrazioneUtentiEjb
                        .getUsrVLisSchedTableBean(ApplEnum.NmJob.REPLICA_UTENTI, dateValidate, sbloccoRepliche));
                getForm().getSchedulazioniReplicaUtentiList().getTable().setPageSize(10);
                /* Workaround in modo che la lista punti al primo record, non all'ultimo */
                getForm().getSchedulazioniReplicaUtentiList().getTable().first();

                /* Setto i campi di "Stato Job" */
                Date proxAttivazione = jbossTimerEjb
                        .getDataProssimaAttivazione(Constants.NomiJob.REPLICA_UTENTI.name());

                UsrVVisLastSchedRowBean rb = amministrazioneUtentiEjb
                        .getUsrVVisLastSchedRowBean(ApplEnum.NmJob.REPLICA_UTENTI);
                String formattata = "";
                DateFormat formato = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH_TIME);
                if (proxAttivazione != null) {
                    formattata = formato.format(proxAttivazione);
                }

                /*
                 * Se il job è già schedulato nascondo il pulsante Start, mostro Stop e visualizzo la prossima
                 * attivazione. Viceversa se è fermo mostro Start e nascondo Stop
                 */
                if (proxAttivazione != null) {
                    formattata = formato.format(proxAttivazione);
                    getForm().getStatoJob().getStartReplicaUtenti().setViewMode();
                    getForm().getStatoJob().getStopReplicaUtenti().setEditMode();
                    getForm().getStatoJob().getDt_prossima_attivazione().setValue(formattata);
                } else {
                    getForm().getStatoJob().getStartReplicaUtenti().setEditMode();
                    getForm().getStatoJob().getStopReplicaUtenti().setViewMode();
                    getForm().getStatoJob().getDt_prossima_attivazione().setValue(null);
                }

                getForm().getStatoJob().getDt_prossima_attivazione().setValue(formattata);
                if (rb.getFlJobAttivo() != null) {
                    if (rb.getFlJobAttivo().equals("1")) {
                        getForm().getStatoJob().getAttivo().setChecked(true);
                        formattata = formato.format(rb.getDtRegLogJobIni());
                        getForm().getStatoJob().getDt_reg_log_job_ini().setValue(formattata);
                    } else {
                        getForm().getStatoJob().getAttivo().setChecked(false);
                        getForm().getStatoJob().getDt_reg_log_job_ini().setValue(null);
                    }
                } else {
                    getForm().getStatoJob().getAttivo().setChecked(false);
                    getForm().getStatoJob().getDt_reg_log_job_ini().setValue(null);
                }

                if (rb.getFlErr() != null) {
                    if (rb.getFlErr().equals("1")) {
                        getForm().getStatoJob().getPresenza_errori_replica().setChecked(true);
                    } else {
                        getForm().getStatoJob().getPresenza_errori_replica().setChecked(false);
                    }
                } else {
                    getForm().getStatoJob().getPresenza_errori_replica().setChecked(false);
                }
            }
        }
        forwardToPublisher(Application.Publisher.SCHEDULAZIONI_REPLICA_UTENTI_LIST);
    }

    @Override
    public void pulisciSchedulazioni() throws EMFError {
        resetSchedulazioniReplicaUtentiPage();
        forwardToPublisher(Application.Publisher.SCHEDULAZIONI_REPLICA_UTENTI_LIST);
    }

    @Override
    public void stopReplicaUtenti() throws EMFError {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void tabListaWizardRuoliDefaultOnClick() throws EMFError {
        getForm().getWizardListsTabs().setCurrentTab(getForm().getWizardListsTabs().getListaWizardRuoliDefault());
        populateSet((UsrUsoRuoloUserDefaultTableBean) getForm().getRuoliDefaultList().getTable());

        resetDichAbilFields();
        initCommonCombo();

        if (getForm().getListaUtenti().getStatus().equals(Status.insert)) {
            getForm().getDettaglioUtente().getCd_psw().post(getRequest());
            getSession().setAttribute("passwordUtente", getForm().getDettaglioUtente().getCd_psw().getValue());
        }
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
    }

    @Override
    public void tabListaWizardAbilOrganizOnClick() throws EMFError {
        getForm().getWizardListsTabs().setCurrentTab(getForm().getWizardListsTabs().getListaWizardAbilOrganiz());
        populateSet((UsrDichAbilOrganizTableBean) getForm().getDichAbilOrgList().getTable());
        resetDichAbilFields();
        initCommonCombo();
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
    }

    @Override
    public void tabListaWizardTipiDatoOnClick() throws EMFError {
        getForm().getWizardListsTabs().setCurrentTab(getForm().getWizardListsTabs().getListaWizardTipiDato());
        populateSet((UsrDichAbilDatiTableBean) getForm().getDichAbilTipiDatoList().getTable());
        resetDichAbilFields();
        initCommonCombo();
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
    }

    @Override
    public void tabListaWizardAbilEnteConvenzOnClick() throws EMFError {
        getForm().getWizardListsTabs().setCurrentTab(getForm().getWizardListsTabs().getListaWizardAbilEnteConvenz());
        populateSet((UsrDichAbilEnteConvenzTableBean) getForm().getDichAbilEnteConvenzList().getTable());
        resetDichAbilFields();
        initCommonCombo();
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
    }

    /**
     * Metodo di trigger della combo di elenco ruoli. Selezionato un ruolo, carica la descrizione del ruolo selezionato
     *
     * @return la form con i dati aggiornati in formato JsonObject
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public JSONObject triggerDichAbilFieldsNm_ruoloOnTrigger() throws EMFError {
        getForm().getDichAbilFields().getNm_ruolo().post(getRequest());
        BigDecimal ruolo = getForm().getDichAbilFields().getNm_ruolo().parse();
        if (ruolo != null) {
            PrfRuoloRowBean prfRuoloById = amministrazioneUtentiEjb.getPrfRuoloById(ruolo);
            getForm().getDichAbilFields().getDs_ruolo().setValue(prfRuoloById.getDsRuolo());
        } else {
            getForm().getDichAbilFields().getDs_ruolo().setValue("\u00A0");
        }
        return getForm().getDichAbilFields().asJSON();
    }

    @Override
    public JSONObject triggerDichAbilFieldsTi_scopo_dich_abil_datiOnTrigger() throws EMFError {
        getForm().getDichAbilFields().post(getRequest());
        String scopo = getForm().getDichAbilFields().getTi_scopo_dich_abil_dati().parse();
        BigDecimal idEnte = getForm().getDettaglioUtente().getId_ente_siam_appart().parse();
        String tipoEnte = amministrazioneUtentiEjb.getTipoEnteConvenzNonConvenz(idEnte);
        BigDecimal idApplic = getForm().getDichAbilFields().getNm_applic().parse();
        String flAbilOrganizAutom = getForm().getDettaglioUtente().getFl_abil_organiz_autom().parse();
        String flAbilOrganizEntiAutom = getForm().getDettaglioUtente().getFl_abil_fornit_autom().parse();
        BigDecimal idRichGestUser = getForm().getRichiestaWizard().getId_rich_gest_user().parse();
        if (scopo != null) {
            if (scopo.equals(ActionEnums.ScopoDichAbilDato.ALL_ORG_CHILD.name())) {
                BaseTable tb = amministrazioneUtentiEjb.getUsrVTreeOrganizIamAllOrgChildTableBean(
                        getUser().getIdUtente(), idApplic, tipoEnte, idEnte, flAbilOrganizAutom, flAbilOrganizEntiAutom,
                        idRichGestUser);
                DecodeMap mappa = new DecodeMap();
                mappa.populatedMap(tb, "id_organiz_iam", "dl_composito_organiz");
                getForm().getDichAbilFields().getDl_composito_organiz().setDecodeMap(mappa);

                // Tengo in sessione una mappa con i dati "aggiuntivi"
                Map<BigDecimal, Object[]> datiAggiuntivi = new HashMap<>();
                for (BaseRow rb : tb) {
                    Object[] o = new Object[3];
                    o[0] = rb.getString("tipoEnte");
                    o[1] = rb.getString("ds_causale_dich");
                    o[2] = rb.getBigDecimal("id_ente");
                    datiAggiuntivi.put(rb.getBigDecimal("id_organiz_iam"), o);
                }
                if (datiAggiuntivi.size() > 0) {
                    getSession().setAttribute("datiAggiuntivi", datiAggiuntivi);
                }

            } else if (scopo.equals(ActionEnums.ScopoDichAbilDato.UN_TIPO_DATO.name())
                    || scopo.equals(ActionEnums.ScopoDichAbilDato.UNA_ORG.name())) {
                BaseTable tb = amministrazioneUtentiEjb.getUsrVTreeOrganizIamUnaOrgTableBean(getUser().getIdUtente(),
                        idApplic, tipoEnte, idEnte, flAbilOrganizAutom, flAbilOrganizEntiAutom, idRichGestUser);
                DecodeMap mappa = new DecodeMap();
                mappa.populatedMap(tb, "id_organiz_iam", "dl_composito_organiz");
                getForm().getDichAbilFields().getDl_composito_organiz().setDecodeMap(mappa);

                // Tengo in sessione una mappa con i dati "aggiuntivi"
                Map<BigDecimal, Object[]> datiAggiuntivi = new HashMap<>();
                for (BaseRow rb : tb) {
                    Object[] o = new Object[3];
                    o[0] = rb.getString("tipoEnte");
                    o[1] = rb.getString("ds_causale_dich");
                    o[2] = rb.getBigDecimal("id_ente");
                    datiAggiuntivi.put(rb.getBigDecimal("id_organiz_iam"), o);
                }
                if (datiAggiuntivi.size() > 0) {
                    getSession().setAttribute("datiAggiuntivi", datiAggiuntivi);
                }

            } else {
                getForm().getDichAbilFields().getDl_composito_organiz().setDecodeMap(new DecodeMap());
            }
        }
        return getForm().getDichAbilFields().asJSON();
    }

    @Override
    public JSONObject triggerDichAbilFieldsTi_scopo_dich_abil_enteOnTrigger() throws EMFError {
        getForm().getDichAbilFields().post(getRequest());
        String tiScopo = getForm().getDichAbilFields().getTi_scopo_dich_abil_ente().parse();
        getForm().getDichAbilFields().getNm_ambiente_ente_convenz().setDecodeMap(new DecodeMap());
        getForm().getDichAbilFields().getNm_ente_convenz().setDecodeMap(new DecodeMap());
        BigDecimal idEnte = getForm().getDettaglioUtente().getId_ente_siam_appart().parse();
        String tipoEnte = amministrazioneUtentiEjb.getTipoEnteConvenzNonConvenz(idEnte);
        if (tiScopo != null) {
            if (tiScopo.equals(ActionEnums.ScopoDichAbilEnteConvenz.UN_AMBIENTE.name())) {
                BaseTable ambientiAbilitati = entiConvenzionatiEjb
                        .getAmbientiAbilitatiUnAmbienteTableBean(getUser().getIdUtente(), tipoEnte, idEnte);
                DecodeMap mappa = new DecodeMap();
                mappa.populatedMap(ambientiAbilitati, "id_ambiente_ente_convenz", "nm_ambiente_ente_convenz");
                getForm().getDichAbilFields().getNm_ambiente_ente_convenz().setDecodeMap(mappa);

                // Tengo in sessione una mappa con i dati "aggiuntivi"
                Map<BigDecimal, Object[]> datiAggiuntivi = new HashMap<>();
                for (BaseRow rb : ambientiAbilitati) {
                    Object[] o = new Object[1];
                    o[0] = rb.getString("ds_causale_dich");
                    datiAggiuntivi.put(rb.getBigDecimal("id_ambiente_ente_convenz"), o);
                }
                if (datiAggiuntivi.size() > 0) {
                    getSession().setAttribute("datiAggiuntivi", datiAggiuntivi);
                }

            } else if (tiScopo.equals(ActionEnums.ScopoDichAbilEnteConvenz.UN_ENTE.name())) {
                BaseTable ambientiAbilitati = entiConvenzionatiEjb
                        .getAmbientiEntiAbilitatiUnEnteTableBean(getUser().getIdUtente(), tipoEnte, idEnte);
                DecodeMap mappa = new DecodeMap();
                mappa.populatedMap(ambientiAbilitati, "id_ambiente_ente_convenz", "nm_ambiente_ente_convenz");
                getForm().getDichAbilFields().getNm_ambiente_ente_convenz().setDecodeMap(mappa);
            }
        }
        return getForm().getDichAbilFields().asJSON();
    }

    @Override
    public JSONObject triggerDichAbilFieldsNm_ambiente_ente_convenzOnTrigger() throws EMFError {
        getForm().getDichAbilFields().post(getRequest());
        String tiScopo = getForm().getDichAbilFields().getTi_scopo_dich_abil_ente().parse();
        BigDecimal idAmbienteEnteConvenz = getForm().getDichAbilFields().getNm_ambiente_ente_convenz().parse();
        BigDecimal idEnte = getForm().getDettaglioUtente().getId_ente_siam_appart().parse();
        String tipoEnte = amministrazioneUtentiEjb.getTipoEnteConvenzNonConvenz(idEnte);
        if (tiScopo != null) {
            if (tiScopo.equals(ActionEnums.ScopoDichAbilEnteConvenz.UN_ENTE.name())) {
                BaseTable entiAbilitati = entiConvenzionatiEjb.getEntiAbilitatiUnEnteTableBean(getUser().getIdUtente(),
                        tipoEnte, idEnte, idAmbienteEnteConvenz);
                DecodeMap mappa = new DecodeMap();
                mappa.populatedMap(entiAbilitati, "id_ente_convenz", "nm_ente_convenz");
                getForm().getDichAbilFields().getNm_ente_convenz().setDecodeMap(mappa);

                // Tengo in sessione una mappa con i dati "aggiuntivi"
                Map<BigDecimal, Object[]> datiAggiuntivi = new HashMap<>();
                for (BaseRow rb : entiAbilitati) {
                    Object[] o = new Object[2];
                    o[0] = rb.getString("ds_causale_dich");
                    o[1] = rb.getBigDecimal("id_ente");
                    datiAggiuntivi.put(rb.getBigDecimal("id_ente_convenz"), o);
                }
                if (datiAggiuntivi.size() > 0) {
                    getSession().setAttribute("datiAggiuntivi", datiAggiuntivi);
                }

            }
        }
        return getForm().getDichAbilFields().asJSON();
    }

    @Override
    public JSONObject triggerFiltriRichiesteId_ambiente_ente_convenzOnTrigger() throws EMFError {
        getForm().getFiltriRichieste().post(getRequest());
        BigDecimal idAmbienteEnteConvenz = getForm().getFiltriRichieste().getId_ambiente_ente_convenz().parse();
        DecodeMap mappa = new DecodeMap();
        if (idAmbienteEnteConvenz != null) {
            OrgVRicEnteConvenzTableBean ricEnteConvenz = entiConvenzionatiEjb.getOrgVRicEnteConvenzAbilTableBean(
                    BigDecimal.valueOf(getUser().getIdUtente()), idAmbienteEnteConvenz, null);
            mappa.populatedMap(ricEnteConvenz, "id_ente_convenz", "nm_ente_convenz");
        } else {
            OrgVRicEnteNonConvenzTableBean ricEnteNonConvenz = entiConvenzionatiEjb
                    .getOrgVRicEnteNonConvenzAbilTableBean(BigDecimal.valueOf(getUser().getIdUtente()), null);
            mappa.populatedMap(ricEnteNonConvenz, "id_ente_siam", "nm_ente_siam");
        }
        getForm().getFiltriRichieste().getId_ente_convenz().setDecodeMap(mappa);
        return getForm().getFiltriRichieste().asJSON();
    }

    /*
     * Metodo per il salvataggio dei ruoli di default Esegue i controlli di coerenza prima del salvataggio. In caso
     * affermativo, inseriscono il ruolo nella lista, altrimenti errore
     *
     * @throws EMFError errore generico
     */
    private void saveRuoliDefaultList() throws EMFError {
        getForm().getDichAbilFields().post(getRequest());
        BigDecimal idRuolo = getForm().getDichAbilFields().getNm_ruolo().parse();
        String applicazione = getForm().getDichAbilFields().getNm_applic().getDecodedValue();
        BigDecimal idApplic = getForm().getDichAbilFields().getNm_applic().parse();
        if (StringUtils.isNotBlank(applicazione)) {
            if (idRuolo != null) {
                if (getRolesSet(applicazione).contains(idRuolo)) {
                    // Ruolo già presente
                    getMessageBox().addError("Ruolo già presente nella lista");
                } else {
                    // Creo la nuova riga
                    Set<BigDecimal> roles = getRolesSet(applicazione);
                    roles.add(idRuolo);
                    getSession().setAttribute("rolesSet_" + applicazione, roles);
                    PrfRuoloRowBean row = new PrfRuoloRowBean();
                    PrfVLisRuoloRowBean rb = amministrazioneRuoliEjb.getPrfVLisRuoloRowBean(idRuolo);
                    row.setIdRuolo(idRuolo);
                    row.setBigDecimal("id_applic", idApplic);
                    row.setString("nm_applic", applicazione);
                    row.setString("ti_ruolo", rb.getTiRuolo());
                    row.setNmRuolo(rb.getNmRuolo());
                    row.setDsRuolo(rb.getDsRuolo());
                    getForm().getRuoliDefaultList().getTable().add(row);
                    getForm().getRuoliDefaultList().getTable().sort();
                    resetDichAbilFields();
                    if (amministrazioneUtentiEjb.hasServicesAuthorization(idRuolo, idApplic)) {
                        Set<BigDecimal> applicationsEditList = getApplicationsEditList();
                        applicationsEditList.add(idApplic);
                        getSession().setAttribute("applicationsEditList", applicationsEditList);
                    }
                }
            } else {
                // Non è stato selezionato un ruolo
                getMessageBox().addError("Non è stato selezionato il ruolo");
            }
        } else {
            // ERRORE
            getMessageBox().addError("Non è stata scelta l'applicazione");
        }
    }

    /**
     * Metodo eseguito al salvataggio del wizard di creazione/modifica utente
     *
     * @return true in caso di salvataggio riuscito
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public boolean inserimentoWizardOnSave() throws EMFError {
        // Eseguo il salvataggio dell'utente
        boolean result = true;
        /*
         * Codice aggiuntivo per il logging...
         */
        LogParam param = SpagoliteLogUtil.getLogParam(paramHelper.getParamApplicApplicationName(),
                getUser().getUsername(), SpagoliteLogUtil.getPageName(this));
        param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
        param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());

        // MODIFICA RUOLI SPECIFICI PER ORGANIZZAZIONE
        if (getSession().getAttribute("fromRuoliSpecifici") != null) {
            // Salvataggio della gestione ruoli per la dichiarazione di organizzazione selezionata
            BigDecimal idUserIam = getForm().getDettaglioUtente().getId_user_iam().parse();
            String nmApplic = getForm().getGestioneRuoliOrganizzazione().getNm_applic().getValue();
            BigDecimal idDichAbilOrganiz = getForm().getGestioneRuoliOrganizzazione().getId_dich_abil_organiz().parse();
            BigDecimal idRichGestUser = getForm().getRichiestaWizard().getId_rich_gest_user().parse();
            BigDecimal idAppartUserRich = getForm().getRichiestaWizard().getId_appart_user_rich().parse();
            // Lista ruoli specifici
            UsrVLisUsoRuoloDichTableBean ruoliSpecificiTableBean = (UsrVLisUsoRuoloDichTableBean) getForm()
                    .getRuoliList().getTable();

            try {
                auth.saveRolesForOrganiz(param, getUser().getIdUtente(), idUserIam, nmApplic, idDichAbilOrganiz,
                        ruoliSpecificiTableBean, getOrgRolesDeleteList(), idRichGestUser, idAppartUserRich);
            } catch (ParerUserError ex) {
                log.error(ex.getDescription());
                throw new EMFError(EMFError.ERROR, ex.getDescription());
            } catch (IncoherenceException ex) {
                log.error(ex.getMessage());
                getMessageBox().addError(ex.getMessage());
            }

            if (!getMessageBox().hasError()) {
                getMessageBox().addInfo("Salvataggio ruoli specifici per organizzazione avvenuto con successo!");
                getMessageBox().setViewMode(ViewMode.plain);

                loadDettaglioUtente(idUserIam);
                setDettaglioUtentiToViewMode();

                forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE);
                SessionManager.removeLastExecutionHistory(getSession());
                getSession().removeAttribute("orgRolesDeleteList");
            } else {
                redirectToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
            }

            getSession().removeAttribute("fromRuoliSpecifici");
        } // MODIFICA UTENTE
        else if (getForm().getDettaglioUtente().postAndValidate(getRequest(), getMessageBox())) {
            log.info("INIZIO SALVATAGGIO UTENTE");
            UsrUserRowBean user = new UsrUserRowBean();
            String password = null;
            BigDecimal idUser = null;
            BigDecimal idSistemaVersantePreUpdate = null;
            ApplEnum.TiOperReplic oper = null;
            String userType = getForm().getDettaglioUtente().getTipo_user().parse();
            String oldUserType = "";
            if (getForm().getListaUtenti().getStatus().equals(Status.insert)) {
                param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
                oper = ApplEnum.TiOperReplic.INS;
                user.setNmCognomeUser(getForm().getDettaglioUtente().getNm_cognome_user().parse());
                user.setNmNomeUser(getForm().getDettaglioUtente().getNm_nome_user().parse());
                user.setNmUserid(getForm().getDettaglioUtente().getNm_userid().parse());
                user.setFlAttivo(getForm().getDettaglioUtente().getFl_attivo().parse());
                user.setDtRegPsw(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                user.setCdFisc(getForm().getDettaglioUtente().getCd_fisc().parse());
                user.setDsEmail(getForm().getDettaglioUtente().getDs_email().parse());
                user.setDsEmailSecondaria(getForm().getDettaglioUtente().getDs_email_secondaria().parse());
                user.setFlContrIp(getForm().getDettaglioUtente().getFl_contr_ip().parse());
                user.setTipoUser(userType);
                user.setTipoAuth(getForm().getDettaglioUtente().getTipo_auth().parse());
                user.setDtIniCert(getForm().getDettaglioUtente().getDt_ini_cert().parse());
                user.setDtFinCert(getForm().getDettaglioUtente().getDt_fin_cert().parse());
                user.setDlCertClient(getForm().getDettaglioUtente().getDl_cert_client().parse());
                user.setIdSistemaVersante(getForm().getDettaglioUtente().getId_sistema_versante().parse());
                user.setIdEnteSiam(getForm().getDettaglioUtente().getId_ente_siam_appart().parse());
                user.setFlRespEnteConvenz(getForm().getDettaglioUtente().getFl_resp_ente_convenz().parse());
                user.setFlAbilEntiCollegAutom(getForm().getDettaglioUtente().getFl_abil_enti_colleg_autom().parse());
                user.setFlAbilOrganizAutom(getForm().getDettaglioUtente().getFl_abil_organiz_autom().parse());
                user.setFlAbilFornitAutom(getForm().getDettaglioUtente().getFl_abil_fornit_autom().parse());
                if (userType.equals(ApplEnum.TipoUser.PERSONA_FISICA.name())
                        || userType.equals(ApplEnum.TipoUser.NON_DI_SISTEMA.name())) {
                    password = RandomStringUtils.randomAlphanumeric(8);
                    user.setCdPsw(password);
                    Date dataScad = new Date();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dataScad);
                    calendar.add(Calendar.DATE, -1);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    dataScad = calendar.getTime();
                    user.setDtScadPsw(new Timestamp(dataScad.getTime()));
                } else if (getForm().getDettaglioUtente().getCd_psw().parse() != null) {
                    password = getForm().getDettaglioUtente().getCd_psw().parse();
                    if (password.matches(PASSWORD_REGEX)) {
                        user.setCdPsw(password);
                        Calendar cal = Calendar.getInstance();
                        cal.set(2444, Calendar.DECEMBER, 31);
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        user.setDtScadPsw(new Timestamp(cal.getTimeInMillis()));
                    } else {
                        getMessageBox().addError("La password non rispetta i requisiti di sicurezza");
                        result = false;
                    }
                } else {
                    getMessageBox().addError("Attenzione: inserire la password!");
                    result = false;
                }
            } else if (getForm().getListaUtenti().getStatus().equals(Status.update)) {
                param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
                oper = ApplEnum.TiOperReplic.MOD;
                idUser = getForm().getDettaglioUtente().getId_user_iam().parse();
                oldUserType = userHelper.getTipologiaUtenteAdmin(idUser.longValue());
                try {
                    user = amministrazioneUtentiEjb.getUserRowBean(idUser);
                    log.debug("MAC 30075 - Inizio salvataggio modifica utente " + user.getNmUserid());
                } catch (Exception ex) {
                    throw new EMFError(EMFError.BLOCKING, "Errore nel caricamento dei dati utente", ex);
                }
                if (!user.getNmUserid().equals(getForm().getDettaglioUtente().getNm_userid().parse())) {
                    getMessageBox().addError("Errore durante il caricamento dei dati dell'utente");
                    result = false;
                }

                idSistemaVersantePreUpdate = user.getIdSistemaVersante();

                String tipoUserDaModificareDB = user.getTipoUser();
                String tipoUserDaModificareOnline = getForm().getDettaglioUtente().getTipo_user().parse();

                user.setNmCognomeUser(getForm().getDettaglioUtente().getNm_cognome_user().parse());
                user.setNmNomeUser(getForm().getDettaglioUtente().getNm_nome_user().parse());
                user.setFlAttivo(getForm().getDettaglioUtente().getFl_attivo().parse());
                user.setCdFisc(getForm().getDettaglioUtente().getCd_fisc().parse());
                user.setDsEmail(getForm().getDettaglioUtente().getDs_email().parse());
                user.setDsEmailSecondaria(getForm().getDettaglioUtente().getDs_email_secondaria().parse());
                user.setFlContrIp(getForm().getDettaglioUtente().getFl_contr_ip().parse());
                user.setTipoUser(getForm().getDettaglioUtente().getTipo_user().parse());
                user.setTipoAuth(getForm().getDettaglioUtente().getTipo_auth().parse());
                user.setDtIniCert(getForm().getDettaglioUtente().getDt_ini_cert().parse());
                user.setDtFinCert(getForm().getDettaglioUtente().getDt_fin_cert().parse());
                user.setDlCertClient(getForm().getDettaglioUtente().getDl_cert_client().parse());
                user.setIdSistemaVersante(getForm().getDettaglioUtente().getId_sistema_versante().parse());
                user.setIdEnteSiam(getForm().getDettaglioUtente().getId_ente_siam_appart().parse());
                user.setFlRespEnteConvenz(getForm().getDettaglioUtente().getFl_resp_ente_convenz().parse());
                user.setFlAbilEntiCollegAutom(getForm().getDettaglioUtente().getFl_abil_enti_colleg_autom().parse());
                user.setFlAbilOrganizAutom(getForm().getDettaglioUtente().getFl_abil_organiz_autom().parse());
                user.setFlAbilFornitAutom(getForm().getDettaglioUtente().getFl_abil_fornit_autom().parse());

                if (oldUserType.equals(ApplEnum.TipoUser.NON_DI_SISTEMA.name())
                        && (userType.equals(ApplEnum.TipoUser.PERSONA_FISICA.name()))) {
                    password = RandomStringUtils.randomAlphanumeric(8);
                    user.setCdPsw(password);
                    Date dataScad = new Date();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dataScad);
                    calendar.add(Calendar.DATE, -1);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    dataScad = calendar.getTime();
                    user.setDtScadPsw(new Timestamp(dataScad.getTime()));
                }

                if (oldUserType.equals(ApplEnum.TipoUser.NON_DI_SISTEMA.name())
                        && (userType.equals(ApplEnum.TipoUser.AUTOMA.name()))) {
                    if (getForm().getDettaglioUtente().getCd_psw().parse() != null) {
                        password = getForm().getDettaglioUtente().getCd_psw().parse();
                        if (password.matches(PASSWORD_REGEX)) {
                            user.setCdPsw(password);
                            Calendar cal = Calendar.getInstance();
                            cal.set(2444, Calendar.DECEMBER, 31);
                            cal.set(Calendar.HOUR_OF_DAY, 0);
                            cal.set(Calendar.MINUTE, 0);
                            cal.set(Calendar.SECOND, 0);
                            user.setDtScadPsw(new Timestamp(cal.getTimeInMillis()));
                        } else {
                            getMessageBox().addError("La password non rispetta i requisiti di sicurezza");
                            result = false;
                        }
                    } else {
                        getMessageBox().addError("Attenzione: inserire la password!");
                        result = false;
                    }
                }

                if (!tipoUserDaModificareDB.equals(tipoUserDaModificareOnline)) {
                    Calendar cal = Calendar.getInstance();
                    if (tipoUserDaModificareOnline.equals(ApplEnum.TipoUser.AUTOMA.name())) {
                        cal.set(2444, Calendar.DECEMBER, 31);
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        user.setDtScadPsw(new Timestamp(cal.getTimeInMillis()));
                    } else if (tipoUserDaModificareOnline.equals(ApplEnum.TipoUser.PERSONA_FISICA.name())
                            && tipoUserDaModificareDB.equals(ApplEnum.TipoUser.AUTOMA.name())
                            && (user.getDtScadPsw().after(user.getDtRegPsw()))) {
                        cal.add(Calendar.DATE, 90);
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        user.setDtScadPsw(new Timestamp(cal.getTimeInMillis()));
                    } else if (tipoUserDaModificareOnline.equals(ApplEnum.TipoUser.PERSONA_FISICA.name())
                            && tipoUserDaModificareDB.equals(ApplEnum.TipoUser.NON_DI_SISTEMA.name())) {
                        Date dataScad = new Date();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(dataScad);
                        calendar.add(Calendar.DATE, -1);
                        calendar.set(Calendar.HOUR_OF_DAY, 0);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);
                        dataScad = calendar.getTime();
                        user.setDtScadPsw(new Timestamp(dataScad.getTime()));
                    }
                }
            }
            if (!getMessageBox().hasError()) {
                populateScopoTipoDatoSet((UsrDichAbilDatiTableBean) getForm().getDichAbilTipiDatoList().getTable());

                /*
                 * Ho bisogno di tenere traccia della lista dei tipi dato divisi per applicazione in quanto anche in
                 * fase di salvataggio dovranno essere fatti dei controlli di coerenza quando verranno create in
                 * automatico le abilitazioni sui tipi dato con USR_V_CREA_ABIL_DATI
                 */
                Map<String, Set<PairAuth>> mappaTipiDatoApplic = new HashMap<>();
                Map<String, Set<PairAuth>> mappaOrgApplic = new HashMap<>();
                for (UsrUsoUserApplicRowBean r : (UsrUsoUserApplicTableBean) getForm().getApplicazioniList()
                        .getTable()) {
                    mappaTipiDatoApplic.put(r.getString("nm_applic"), getScopoTipiDatoSet(r.getString("nm_applic")));
                    mappaOrgApplic.put(r.getString("nm_applic"), getScopoOrgSet(r.getString("nm_applic")));
                }
                BigDecimal idRichGestUser = getForm().getRichiestaWizard().getId_rich_gest_user().parse();
                BigDecimal idAppartUserRich = getForm().getRichiestaWizard().getId_appart_user_rich().parse();
                UsrIndIpUserTableBean indIpUserTableBean = (UsrIndIpUserTableBean) getForm().getIndIpList().getTable();
                UsrUsoUserApplicTableBean usoUserApplicTableBean = (UsrUsoUserApplicTableBean) getForm()
                        .getApplicazioniList().getTable();
                UsrUsoRuoloUserDefaultTableBean usoRuoloUserDefaultTableBean = (UsrUsoRuoloUserDefaultTableBean) getForm()
                        .getRuoliDefaultList().getTable();
                UsrDichAbilOrganizTableBean dichAbilOrganizTableBean = (UsrDichAbilOrganizTableBean) getForm()
                        .getDichAbilOrgList().getTable();

                log.debug(
                        "MAC 30075 - In questo istante, prima di iniziare a salvare su DB, la 'Lista dich. abil. organiz' dell'utente "
                                + user.getNmUserid() + " contiene i seguenti record: ");
                UsrDichAbilDatiTableBean dichAbilDatiTableBean = (UsrDichAbilDatiTableBean) getForm()
                        .getDichAbilTipiDatoList().getTable();
                // MAC 30075
                dichAbilOrganizTableBean.forEach((dichAbilOrganizRowBean) -> log
                        .debug("MAC 30075 - Dichiarazione alle organizzazioni presente - applicazione: "
                                + dichAbilOrganizRowBean.getString("nm_applic") + ", scopo: "
                                + dichAbilOrganizRowBean.getTiScopoDichAbilOrganiz() + ", organizzazione: "
                                + dichAbilOrganizRowBean.getString("dl_composito_organiz")));

                UsrDichAbilEnteConvenzTableBean dichAbilEnteConvenzTableBean = (UsrDichAbilEnteConvenzTableBean) getForm()
                        .getDichAbilEnteConvenzList().getTable();

                try {
                    if (getForm().getListaUtenti().getStatus().equals(Status.insert)) {
                        idUser = auth.saveAndAlignUser(param, getUser().getIdUtente(), user, indIpUserTableBean,
                                usoUserApplicTableBean, usoRuoloUserDefaultTableBean, dichAbilOrganizTableBean,
                                dichAbilDatiTableBean, dichAbilEnteConvenzTableBean, getIndIpDeleteList(),
                                getApplicationsDeleteList(), getDefaultRolesDeleteList(), getDichOrganizDeleteList(),
                                getDichTipiDatoDeleteList(), getDichAbilEnteConvenzDeleteList(),
                                getApplicationsEditList(), mappaOrgApplic, mappaTipiDatoApplic, oper, idRichGestUser,
                                idAppartUserRich);
                        // Report del salvataggio
                        ultimateSalvataggioUtente(userType, user, idUser, oldUserType);
                    } else if (getForm().getListaUtenti().getStatus().equals(Status.update)) {
                        log.debug("MAC 30075 - Dopo i controlli, entro nel ramo di salvaggio modifica utente "
                                + user.getNmUserid());
                        // Controllo se il sistema versante è stato modificato/eliminato
                        if (idSistemaVersantePreUpdate != null) {
                            // Se è stato eliminato oppure c'è ancora ma è diverso dal precedente
                            if ((user.getIdSistemaVersante() != null
                                    && idSistemaVersantePreUpdate.compareTo(user.getIdSistemaVersante()) != 0)
                                    || user.getIdSistemaVersante() == null) {
                                auth.checkUltimoAccordoEntiConvenzionatiPerSistemaVersante(user.getIdUserIam(),
                                        idSistemaVersantePreUpdate);
                            }
                        }

                        eseguiSalvataggioModificaUtente(param, getUser().getIdUtente(), user, indIpUserTableBean,
                                usoUserApplicTableBean, usoRuoloUserDefaultTableBean, dichAbilOrganizTableBean,
                                dichAbilDatiTableBean, dichAbilEnteConvenzTableBean, getIndIpDeleteList(),
                                getApplicationsDeleteList(), getDefaultRolesDeleteList(), getDichOrganizDeleteList(),
                                getDichTipiDatoDeleteList(), getDichAbilEnteConvenzDeleteList(),
                                getApplicationsEditList(), mappaOrgApplic, mappaTipiDatoApplic, oper, idRichGestUser,
                                idAppartUserRich, password, userType, oldUserType);
                    }
                    forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE);
                    SessionManager.removeLastExecutionHistory(getSession());
                } catch (ParerUserError ex) {
                    log.error(ex.getDescription());
                    getMessageBox().addError(ex.getDescription());
                    result = false;
                } catch (IncoherenceException ex) {
                    log.error(ex.getMessage());
                    getMessageBox().addError(ex.getMessage());
                    result = false;
                } catch (ParerWarningException ex) {
                    getRequest().setAttribute("warningModificaUtente", ex.getDescription());
                    Object[] attributiSalvataggioModificaUtente = new Object[24];
                    attributiSalvataggioModificaUtente[0] = param;
                    attributiSalvataggioModificaUtente[1] = getUser().getIdUtente();
                    attributiSalvataggioModificaUtente[2] = user;
                    attributiSalvataggioModificaUtente[3] = indIpUserTableBean;
                    attributiSalvataggioModificaUtente[4] = usoUserApplicTableBean;
                    attributiSalvataggioModificaUtente[5] = usoRuoloUserDefaultTableBean;
                    attributiSalvataggioModificaUtente[6] = dichAbilOrganizTableBean;
                    attributiSalvataggioModificaUtente[7] = dichAbilDatiTableBean;
                    attributiSalvataggioModificaUtente[8] = dichAbilEnteConvenzTableBean;
                    attributiSalvataggioModificaUtente[9] = getIndIpDeleteList();
                    attributiSalvataggioModificaUtente[10] = getApplicationsDeleteList();
                    attributiSalvataggioModificaUtente[11] = getDefaultRolesDeleteList();
                    attributiSalvataggioModificaUtente[12] = getDichOrganizDeleteList();
                    attributiSalvataggioModificaUtente[13] = getDichTipiDatoDeleteList();
                    attributiSalvataggioModificaUtente[14] = getDichAbilEnteConvenzDeleteList();
                    attributiSalvataggioModificaUtente[15] = getApplicationsEditList();
                    attributiSalvataggioModificaUtente[16] = mappaOrgApplic;
                    attributiSalvataggioModificaUtente[17] = mappaTipiDatoApplic;
                    attributiSalvataggioModificaUtente[18] = oper;
                    attributiSalvataggioModificaUtente[19] = idRichGestUser;
                    attributiSalvataggioModificaUtente[20] = idAppartUserRich;
                    attributiSalvataggioModificaUtente[21] = password;
                    attributiSalvataggioModificaUtente[22] = userType;
                    attributiSalvataggioModificaUtente[23] = oldUserType;
                    getSession().setAttribute("attributiSalvataggioModificaUtente", attributiSalvataggioModificaUtente);
                    getRequest().setAttribute("customModificaUtenteMessageBox", true);
                    result = false;
                }
            }
        }
        return result;
    }

    private void ultimateSalvataggioUtente(String userType, UsrUserRowBean user, BigDecimal idUser, String oldUserType)
            throws EMFError {
        if (!getMessageBox().hasError()) {
            getSession().removeAttribute("indIpDeleteList");
            getSession().removeAttribute("applicationsDeleteList");
            getSession().removeAttribute("applicationsEditList");
            getSession().removeAttribute("defaultRolesDeleteList");
            getSession().removeAttribute("dichOrganizDeleteList");
            getSession().removeAttribute("dichTipiDatoDeleteList");
            getSession().removeAttribute("dichAbilEnteConvenzDeleteList");
            getSession().removeAttribute("datiAggiuntivi");

            if (getForm().getListaUtenti().getStatus().equals(Status.insert)) {
                getMessageBox().addInfo(
                        "Utente creato con successo.<br/>" + (userType.equals(ApplEnum.TipoUser.PERSONA_FISICA.name())
                                // ? "Richiedere all'utente il primo accesso per modifica password utilizzando i
                                // dati:<br/>"
                                ? "Inviare all'utente la e-mail di attivazione cliccando sull'apposito pulsante. <br/>"
                                : "") + "<ul><li>Username: " + user.getNmUserid() + "</li>"
                        // + (userType.equals(ApplEnum.TipoUser.NON_DI_SISTEMA.name()) ? ""
                        // : "<li>Password: " + password + "</li>")
                                + "<li>E-mail: " + user.getDsEmail() + "</li>" + "</ul>");
                user.setIdUserIam(idUser);
                if (getForm().getListaUtenti().getTable() == null) {
                    getForm().getListaUtenti().setTable(new UsrVLisUserTableBean());
                }
                getForm().getListaUtenti().getTable().last();
                getForm().getListaUtenti().add(user);
            } else if (getForm().getListaUtenti().getStatus().equals(Status.update)) {
                if (oldUserType.equals(ApplEnum.TipoUser.NON_DI_SISTEMA.name())
                        && (userType.equals(ApplEnum.TipoUser.PERSONA_FISICA.name())
                                || userType.equals(ApplEnum.TipoUser.AUTOMA.name()))) {
                    getMessageBox().addInfo(
                            "Utente modificato con successo.<br/>" + "<ul><li>Username: " + user.getNmUserid() + "</li>"
                            // + "<li>Password: " + password + "</li>"
                                    + "<li>E-mail: " + user.getDsEmail() + "</li>" + "</ul>");
                } else {
                    getMessageBox().addInfo("Utente modificato con successo.");
                }
            }

            getMessageBox().setViewMode(ViewMode.plain);

            /*
             * ATTENZIONE: L'utente va ora ricaricato nel dettaglio (dettaglio più liste) in quanto la lista
             * abilitazioni tipi dato ha inserito, all'atto del salvataggio, dei valori in automatico che altrimenti non
             * vedrei!
             */
            loadDettaglioUtente(idUser);
            setDettaglioUtentiToViewMode();
        }
        log.info("FINE SALVATAGGIO UTENTE");
    }

    public void confermaModificaUtente() throws ParerUserError, IncoherenceException, EMFError {
        if (getSession().getAttribute("attributiSalvataggioModificaUtente") != null) {
            Object[] attributiSalvataggioModificaUtente = (Object[]) getSession()
                    .getAttribute("attributiSalvataggioModificaUtente");
            LogParam param = (LogParam) attributiSalvataggioModificaUtente[0];
            long idUserCorrente = (Long) attributiSalvataggioModificaUtente[1];
            UsrUserRowBean user = (UsrUserRowBean) attributiSalvataggioModificaUtente[2];
            UsrIndIpUserTableBean indIp = (UsrIndIpUserTableBean) attributiSalvataggioModificaUtente[3];
            UsrUsoUserApplicTableBean applic = (UsrUsoUserApplicTableBean) attributiSalvataggioModificaUtente[4];
            UsrUsoRuoloUserDefaultTableBean ruoliDefault = (UsrUsoRuoloUserDefaultTableBean) attributiSalvataggioModificaUtente[5];
            UsrDichAbilOrganizTableBean orgDich = (UsrDichAbilOrganizTableBean) attributiSalvataggioModificaUtente[6];
            UsrDichAbilDatiTableBean tipiDato = (UsrDichAbilDatiTableBean) attributiSalvataggioModificaUtente[7];
            UsrDichAbilEnteConvenzTableBean dichAbilEnteConvenzTableBean = (UsrDichAbilEnteConvenzTableBean) attributiSalvataggioModificaUtente[8];
            Set<BigDecimal> indIpDeleteList = (Set<BigDecimal>) attributiSalvataggioModificaUtente[9];
            Set<BigDecimal> applicationsDeleteList = (Set<BigDecimal>) attributiSalvataggioModificaUtente[10];
            Set<BigDecimal> defaultRolesDeleteList = (Set<BigDecimal>) attributiSalvataggioModificaUtente[11];
            Set<BigDecimal> dichOrganizDeleteList = (Set<BigDecimal>) attributiSalvataggioModificaUtente[12];
            Set<BigDecimal> tipiDatoDeleteList = (Set<BigDecimal>) attributiSalvataggioModificaUtente[13];
            Set<BigDecimal> dichAbilEnteConvenzDeleteList = (Set<BigDecimal>) attributiSalvataggioModificaUtente[14];
            Set<BigDecimal> applicationsEditSet = (Set<BigDecimal>) attributiSalvataggioModificaUtente[15];
            Map<String, Set<PairAuth>> mappaOrgApplic = (Map<String, Set<PairAuth>>) attributiSalvataggioModificaUtente[16];
            Map<String, Set<PairAuth>> mappaTipiDatoApplic = (Map<String, Set<PairAuth>>) attributiSalvataggioModificaUtente[17];
            ApplEnum.TiOperReplic oper = (ApplEnum.TiOperReplic) attributiSalvataggioModificaUtente[18];
            BigDecimal idRichGestUser = (BigDecimal) attributiSalvataggioModificaUtente[19];
            BigDecimal idAppartUserRich = (BigDecimal) attributiSalvataggioModificaUtente[20];
            String password = (String) attributiSalvataggioModificaUtente[21];
            String userType = (String) attributiSalvataggioModificaUtente[22];
            String oldUserType = (String) attributiSalvataggioModificaUtente[23];

            eseguiSalvataggioModificaUtente(param, idUserCorrente, user, indIp, applic, ruoliDefault, orgDich, tipiDato,
                    dichAbilEnteConvenzTableBean, indIpDeleteList, applicationsDeleteList, defaultRolesDeleteList,
                    dichOrganizDeleteList, tipiDatoDeleteList, dichAbilEnteConvenzDeleteList, applicationsEditSet,
                    mappaOrgApplic, mappaTipiDatoApplic, oper, idRichGestUser, idAppartUserRich, password, userType,
                    oldUserType);
            getSession().removeAttribute("attributiSalvataggioModificaUtente");
            forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE);
            SessionManager.removeLastExecutionHistory(getSession());
        }
    }

    public void annullaModificaUtente() {
        getSession().removeAttribute("attributiSalvataggioModificaUtente");
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
    }

    public void eseguiSalvataggioModificaUtente(LogParam param, long idUserCorrente, UsrUserRowBean user,
            UsrIndIpUserTableBean indIp, UsrUsoUserApplicTableBean applic, UsrUsoRuoloUserDefaultTableBean ruoliDefault,
            UsrDichAbilOrganizTableBean orgDich, UsrDichAbilDatiTableBean tipiDato,
            UsrDichAbilEnteConvenzTableBean dichAbilEnteConvenzTableBean, Set<BigDecimal> indIpDeleteList,
            Set<BigDecimal> applicationsDeleteList, Set<BigDecimal> defaultRolesDeleteList,
            Set<BigDecimal> dichOrganizDeleteList, Set<BigDecimal> tipiDatoDeleteList,
            Set<BigDecimal> dichAbilEnteConvenzDeleteList, Set<BigDecimal> applicationsEditSet,
            Map<String, Set<PairAuth>> mappaOrgApplic, Map<String, Set<PairAuth>> mappaTipiDatoApplic,
            ApplEnum.TiOperReplic oper, BigDecimal idRichGestUser, BigDecimal idAppartUserRich, String password,
            String userType, String oldUserType) throws ParerUserError, IncoherenceException, EMFError {
        BigDecimal idUser = auth.saveAndAlignUser(param, idUserCorrente, user, indIp, applic, ruoliDefault, orgDich,
                tipiDato, dichAbilEnteConvenzTableBean, indIpDeleteList, applicationsDeleteList, defaultRolesDeleteList,
                dichOrganizDeleteList, tipiDatoDeleteList, dichAbilEnteConvenzDeleteList, applicationsEditSet,
                mappaOrgApplic, mappaTipiDatoApplic, oper, idRichGestUser, idAppartUserRich);
        ultimateSalvataggioUtente(userType, user, idUser, oldUserType);
    }

    @Override
    public void inserimentoWizardPassoApplicOnEnter() throws EMFError {
        getForm().getApplicazioniFields().getAggiungiApplicazione().setEditMode();
        getForm().getApplicazioniFields().getNm_applic().setEditMode();
        getForm().getApplicazioniFields().getDs_applic().setEditMode();
        getForm().getApplicazioniFields().getNm_applic().setHidden(false);
        getForm().getApplicazioniFields().getDs_applic().setHidden(false);
        getForm().getApplicazioniFields().getNm_applic().clear();
        getForm().getApplicazioniFields().getDs_applic().clear();

        /* GESTIONE POPOLAMENTO COMBO APPLIC E INSERIMENTO APPLIC DI DEFAULT IN LISTA */
        boolean isEnteAmministratore = amministrazioneUtentiEjb
                .isEnteAmministratore(getForm().getDettaglioUtente().getId_ente_siam_appart().parse());
        boolean isEnteOrganoVigilanza = amministrazioneUtentiEjb
                .isEnteOrganoVigilanza(getForm().getDettaglioUtente().getId_ente_siam_appart().parse());

        getForm().getApplicazioniFields().getNm_applic().setDecodeMap(comboEjb
                .getMappaApplicAbilitateUtente(getUser().getIdUtente(), isEnteAmministratore, isEnteOrganoVigilanza));
        populateApplicationSet((UsrUsoUserApplicTableBean) getForm().getApplicazioniList().getTable());
        getForm().getApplicazioniList().setHidden(false);
        getForm().getDettaglioUtente().setViewMode();
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
    }

    @Override
    public boolean inserimentoWizardPassoApplicOnExit() throws EMFError {
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
        if (getForm().getApplicazioniList().getTable().isEmpty()) {
            getMessageBox().addError("Inserire almeno un'applicazione");
            return false;
        }
        if (getForm().getApplicazioniFields().postAndValidate(getRequest(), getMessageBox())) {
            return true;
        }
        return false;
    }

    public DecodeMap getMappaApplic() {
        getForm().getFiltriUtenti().getNm_applic().clear();
        AplApplicTableBean applicTableBean = amministrazioneUtentiEjb.getAplApplicTableBean();
        DecodeMap applicDM = DecodeMap.Factory.newInstance(applicTableBean, "id_applic", "nm_applic");
        return applicDM;
    }

    @Override
    public void inserimentoWizardPassoIpOnEnter() throws EMFError {
        getForm().getIndIpFields().getAggiungiIP().setEditMode();
        getForm().getIndIpFields().getCd_ind_ip_user().setEditMode();
        getForm().getIndIpFields().getCd_ind_ip_user().clear();
        populateIndIpSet((UsrIndIpUserTableBean) getForm().getIndIpList().getTable());
        getForm().getDettaglioUtente().setViewMode();
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
    }

    @Override
    public boolean inserimentoWizardPassoIpOnExit() throws EMFError {
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
        if (getForm().getIndIpList().getTable().isEmpty()) {
            getMessageBox().addError("Inserire almeno un indirizzo IP/sottorete");
            return false;
        }
        if (getForm().getIndIpFields().postAndValidate(getRequest(), getMessageBox())) {
            return true;
        }
        return false;
    }

    @Override
    public void inserimentoWizardPassoRuoliSpecificiOnEnter() throws EMFError {
        // Visualizzo i campi che mi servono in fase di modifica
        getForm().getGestioneRuoliOrganizzazione().getTi_scopo_ruolo().setHidden(false);
        getForm().getGestioneRuoliOrganizzazione().getDl_composito_organiz_ruolo().setHidden(false);
        getForm().getGestioneRuoliOrganizzazione().getNm_ruolo().setHidden(false);
        getForm().getGestioneRuoliOrganizzazione().getDs_ruolo().setHidden(false);
        // Popolo le combo e metto editabili i campi di Ruoli Specifici che mi interessano
        long idUtenteInModifica = getForm().getListaUtenti().getTable().getCurrentRow().getBigDecimal("id_user_iam")
                .longValue();
        PrfRuoloTableBean prfRuoliViewBean = amministrazioneUtentiEjb.getRuoliSpecificiTableBean(
                getForm().getGestioneRuoliOrganizzazione().getNm_applic().getValue(), idUtenteInModifica);
        DecodeMap ruoliDM = DecodeMap.Factory.newInstance(prfRuoliViewBean, "id_ruolo", "nm_ruolo");
        getForm().getGestioneRuoliOrganizzazione().getNm_ruolo().setDecodeMap(ruoliDM);
        getForm().getGestioneRuoliOrganizzazione().getDs_ruolo().setValue("\u00A0");
        String scopoDichAbilOrganiz = getForm().getGestioneRuoliOrganizzazione().getTi_scopo_dich_abil_organiz()
                .parse();
        getForm().getGestioneRuoliOrganizzazione().getTi_scopo_ruolo()
                .setDecodeMap(ComboGetter.getTiScopoRuolo(scopoDichAbilOrganiz));
        getForm().getGestioneRuoliOrganizzazione().getDl_composito_organiz_ruolo().setDecodeMap(new DecodeMap());
        //
        getForm().getGestioneRuoliOrganizzazione().getTi_scopo_ruolo().setEditMode();
        getForm().getGestioneRuoliOrganizzazione().getDl_composito_organiz_ruolo().setEditMode();
        getForm().getGestioneRuoliOrganizzazione().getNm_ruolo().setEditMode();
        getForm().getGestioneRuoliOrganizzazione().getAggiungiRuoloOrg().setEditMode();

        // Consento eventualmente di cancellare dei record dalla lista Ruoli Specifici
        getForm().getRuoliList().setHideDeleteButton(false);
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
    }

    @Override
    public boolean inserimentoWizardPassoRuoliSpecificiOnExit() throws EMFError {
        // forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
        return true;
    }

    /**
     * Metodo invocato sul bottone di inserimento della lista utenti. Cambia lo stato della lista utenti e delle liste
     * del dettaglio utenti, in caso di inserimento nuovo utente. Nel caso dell'inserimento ruoli per struttura carica
     * la pagina per l'inserimento ruolo
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public void insertDettaglio() throws EMFError {
        if (getTableName() != null) {
            if (getTableName().equals(getForm().getListaUtenti().getName())) {
                /*
                 * Inserimento di un nuovo utente, richiamato dalla pagina di ricerca Carica il wizard di inserimento
                 * utente con i campi in status insert e edit mode
                 */
                getForm().getDettaglioUtente().clear();
                getForm().getListaUtenti().setStatus(Status.insert);
                getForm().getDettaglioUtente().setStatus(Status.insert);
                getForm().getDettaglioUtente().setEditMode();
                getForm().getDettaglioUtente().getNuovaPassword().setViewMode();
                getForm().getDettaglioUtente().getQualifica_user().setHidden(true);

                DecodeMapIF combo = ComboGetter.getMappaGenericFlagSiNo();
                DecodeMapIF comboNo = ComboGetter.getMappaGenericFlagNo();
                getForm().getDettaglioUtente().getFl_attivo().setDecodeMap(combo);
                getForm().getDettaglioUtente().getFl_attivo().setValue("1");
                getForm().getDettaglioUtente().getFl_err_replic().setDecodeMap(combo);
                // getForm().getDettaglioUtente().getFl_user_admin().setDecodeMap(combo);
                getForm().getDettaglioUtente().getFl_contr_ip().setDecodeMap(combo);
                getForm().getDettaglioUtente().getFl_resp_ente_convenz().setDecodeMap(comboNo);
                getForm().getDettaglioUtente().getFl_resp_ente_convenz().setValue("0");

                getForm().getRichiestaWizard().clear();
                getForm().getRichiestaWizard().getId_ambiente_ente_convenz_rich()
                        .setDecodeMap(DecodeMap.Factory.newInstance(
                                entiConvenzionatiEjb.getUsrVAbilAmbEnteConvenzTableBean(
                                        BigDecimal.valueOf(getUser().getIdUtente())),
                                "id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));
                getForm().getRichiestaWizard().getId_ente_convenz_rich()
                        .setDecodeMap(DecodeMap.Factory.newInstance(
                                entiConvenzionatiEjb.getOrgVRicEnteNonConvenzAbilTableBean(
                                        BigDecimal.valueOf(getUser().getIdUtente()), null),
                                "id_ente_siam", "nm_ente_siam"));

                /* 17504 - Codice commentato inizialmente da lasciare */
                UsrVAbilOrganizTableBean usrOrgIammeTableBean = amministrazioneUtentiEjb
                        .getOrganizAbilitate(new BigDecimal(getUser().getIdUtente()));
                DecodeMap usrOrganizIammeMap = DecodeMap.Factory.newInstance(usrOrgIammeTableBean, "id_organiz_iam",
                        "dl_composito_organiz");
                getForm().getRichiestaWizard().getId_organiz_iam_rich().setDecodeMap(usrOrganizIammeMap);
                getForm().getRichiestaWizard().getCd_registro_rich_gest_user().setDecodeMap(new DecodeMap());

                getForm().getDettaglioUtente().getFl_contr_ip().setValue("0");

                getForm().getIndIpList().clear();
                getForm().getApplicazioniList().clear();
                getForm().getRuoliDefaultList().clear();
                getForm().getDichAbilOrgList().clear();
                getForm().getDichAbilTipiDatoList().clear();
                getForm().getDichAbilEnteConvenzList().clear();

                /* Inizializzazione liste */
                UsrIndIpUserTableBean ipTB = new UsrIndIpUserTableBean();
                getForm().getIndIpList().setTable(ipTB);
                getForm().getIndIpList().getTable().setPageSize(10);
                getForm().getIndIpList().getTable().first();

                UsrUsoUserApplicTableBean apl = new UsrUsoUserApplicTableBean();
                apl.addSortingRule(AplApplicTableDescriptor.COL_NM_APPLIC, SortingRule.ASC);
                apl.sort();
                getForm().getApplicazioniList().setTable(apl);
                getForm().getApplicazioniList().getTable().setPageSize(10);
                getForm().getApplicazioniList().getTable().first();
                /* Setto applicazioniList in stato insert per successivi controlli */
                getForm().getApplicazioniList().setStatus(Status.insert);

                UsrUsoRuoloUserDefaultTableBean dichsTB1 = new UsrUsoRuoloUserDefaultTableBean();
                dichsTB1.addSortingRule("nm_applic", SortingRule.ASC);
                dichsTB1.addSortingRule(PrfRuoloTableDescriptor.COL_NM_RUOLO, SortingRule.ASC);
                dichsTB1.sort();
                UsrDichAbilOrganizTableBean dichsTB2 = new UsrDichAbilOrganizTableBean();
                UsrDichAbilDatiTableBean dichsTB3 = new UsrDichAbilDatiTableBean();
                UsrDichAbilEnteConvenzTableBean dichsTB4 = new UsrDichAbilEnteConvenzTableBean();

                // Carico la lista dei ruoli di default
                getForm().getRuoliDefaultList().setTable(dichsTB1);
                getForm().getRuoliDefaultList().getTable().setPageSize(10);
                getForm().getRuoliDefaultList().getTable().first();

                // Carico la lista di dichiarazioni organizzazione
                getForm().getDichAbilOrgList().setTable(dichsTB2);
                getForm().getDichAbilOrgList().getTable().setPageSize(10);
                getForm().getDichAbilOrgList().getTable().first();

                // Carico la lista di dichiarazioni tipi dato
                getForm().getDichAbilTipiDatoList().setTable(dichsTB3);
                getForm().getDichAbilTipiDatoList().getTable().setPageSize(10);
                getForm().getDichAbilTipiDatoList().getTable().first();

                // Carico la lista di dichiarazioni enti convenzionati
                getForm().getDichAbilEnteConvenzList().setTable(dichsTB4);
                getForm().getDichAbilEnteConvenzList().getTable().setPageSize(10);
                getForm().getDichAbilEnteConvenzList().getTable().first();

                // Imposto le liste come modificabili
                getForm().getIndIpList().setUserOperations(false, false, false, true);
                getForm().getApplicazioniList().setUserOperations(false, false, false, true);
                getForm().getRuoliDefaultList().setUserOperations(false, false, false, true);
                getForm().getDichAbilOrgList().setUserOperations(false, false, false, true);
                getForm().getDichAbilTipiDatoList().setUserOperations(false, false, false, true);
                getForm().getDichAbilEnteConvenzList().setUserOperations(false, false, false, true);

                // Azzero gli attritubi in sessione usati per i controlli di coerenza
                getSession().removeAttribute("indIpSet");
                getSession().removeAttribute("applicationsSet");
                getSession().removeAttribute("applicationsEditList");
                getSession().removeAttribute("rolesSet");
                getSession().removeAttribute("scopoSet");
                getSession().removeAttribute("userType");
                getSession().removeAttribute("passwordUtente");

                if (getRequest().getAttribute("fromRichiesta") == null) {
                    getForm().getInserimentoWizard().getPassoRichiesta().setHidden(false);
                } else {
                    getForm().getDettaglioUtente().getTipo_user().setDecodeMap(
                            ComboGetter.getMappaSortedGenericEnum("tipo_user", ApplEnum.TipoUser.getComboTipiTotali()));
                    getForm().getDettaglioUtente().getTipo_user().setValue(ApplEnum.TipoUser.PERSONA_FISICA.name());
                    getForm().getDettaglioUtente().getTipo_auth().setDecodeMap(
                            ComboGetter.getMappaSortedGenericEnum("tipo_auth", ApplEnum.TipoAuth.getComboTipiAuth()));

                    // Filtro "Sistema versante"
                    String tipoUtente = getForm().getDettaglioUtente().getTipo_user().parse();
                    BigDecimal idEnteUser = getForm().getDettaglioUtente().getId_ente_siam_appart().parse();
                    DecodeMap mappaSistemiVersanti = new DecodeMap();
                    mappaSistemiVersanti.populatedMap(
                            sistemiVersantiEjb.getAplSistemaVersanteValidiTableBean(tipoUtente, idEnteUser),
                            "id_sistema_versante", "nm_sistema_versante");
                    getForm().getDettaglioUtente().getId_sistema_versante().setDecodeMap(mappaSistemiVersanti);
                }

            } else if (getTableName().equals(getForm().getRichiesteList().getName())) {
                // Puliamo il dettaglio e mettiamo tutto in status "insert"
                getForm().getRichiestaDetail().clear();
                getForm().getAzioniList().clear();
                UsrAppartUserRichTableBean ipTB = new UsrAppartUserRichTableBean();
                getForm().getAzioniList().setTable(ipTB);
                getForm().getAzioniList().getTable().setPageSize(10);
                getForm().getAzioniList().getTable().first();

                initRichiestaDetail();

                OrgVRicEnteNonConvenzTableBean ricEnteNonConvenz = entiConvenzionatiEjb
                        .getOrgVRicEnteNonConvenzAbilTableBean(BigDecimal.valueOf(getUser().getIdUtente()), null);
                DecodeMap mappa = new DecodeMap();
                mappa.populatedMap(ricEnteNonConvenz, "id_ente_siam", "nm_ente_siam");
                getForm().getRichiestaDetail().getId_ente_rich().setDecodeMap(mappa);

                // Modifico i valori in alcune combo a seconda della provenienza
                if (getRequest().getAttribute("isFromRichiestaConfigurazioneUtenti") == null) {
                    setVisibilityFieldsForRicercaRichiesta();
                } else {
                    // La pagina "Richiesta configurazione utenti"
                    setVisibilityForRichiestaConfigurazioneUtenti();
                }

                getForm().getRichiesteList().setStatus(Status.insert);
                getForm().getRichiestaDetail().setStatus(Status.insert);
                getForm().getRichiestaDetail().setEditMode();
                // Precompilo con la data corrente
                DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                getForm().getRichiestaDetail().getDt_rich_gest_user()
                        .setValue(formato.format(Calendar.getInstance().getTime()));
                // Precompilo il tipo richiesta
                getForm().getRichiestaDetail().getTi_stato_rich_gest_user()
                        .setValue(ConstUsrRichGestUser.TiStatoRichGestUser.DA_COMPLETARE.name());
                getForm().getRichiestaDetail().getTi_stato_rich_gest_user().setViewMode();
                // I bottoni vanno nascosti
                getForm().getRichiestaDetail().getScaricaFileRichiesta().setHidden(true);
                getForm().getRichiestaDetail().getRichiestaCompletata().setHidden(true);
                getForm().getRichiestaDetail().getLogEventiRichiesta().setHidden(true);
            } else if (getTableName().equals(getForm().getAzioniList().getName())) {
                // Puliamo il dettaglio e mettiamo tutto in status "insert"
                getForm().getAzioneDetail().clear();
                initAzioneDetail();
                getForm().getAzioniList().setStatus(Status.insert);
                getForm().getAzioneDetail().setStatus(Status.insert);
                getForm().getAzioneDetail().setEditMode();
                // Precompilo con la data corrente
                DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                getForm().getRichiestaDetail().getDt_rich_gest_user()
                        .setValue(formato.format(Calendar.getInstance().getTime()));
                getForm().getRichiestaDetail().getId_organiz_iam().setViewMode();
            }
        }
    }

    private void setVisibilityFieldsForRicercaRichiesta() {
        getForm().getRichiestaDetail().getTi_rich_gest_user()
                .setDecodeMap(ComboGetter.getMappaTiRichGestUserRicRichieste());
        getForm().getIdentificativoUdRichSection().setHidden(false);
        getForm().getRichiestaDetail().getNm_userid_manuale().setHidden(false);
        // Non mostro il campo ed il bottoni per selezionare l'utente codificato
        getForm().getRichiestaDetail().getNm_userid_codificato().setHidden(true);
        //
        getForm().getRichiestaDetail().getDs_email_rich().setHidden(false);
        // Bottone salva personalizzato
        getForm().getRichiestaDetail().getSalvaRichiesta().setViewMode();
    }

    private void setVisibilityForRichiestaConfigurazioneUtenti() {
        getForm().getRichiestaDetail().getTi_rich_gest_user()
                .setDecodeMap(ComboGetter.getMappaTiRichGestUserRichConfUtenti());
        getForm().getRichiestaDetail().getTi_rich_gest_user()
                .setValue(ConstUsrRichGestUser.TiRichGestUser.COMUNICAZIONE_ONLINE.getDescrizione());
        getForm().getIdentificativoUdRichSection().setHidden(true);
        // Recupero il valore dalla sequence da presentare a video
        BigDecimal progr = amministrazioneUtentiEjb.getSusrRichGestUserCd();
        getForm().getRichiestaDetail().getCd_rich_gest_user().setValue("R" + progr);
        getForm().getRichiestaDetail().getNm_userid_manuale().setHidden(true);
        // Prepopolo l'utente codificato con l'utente corrente
        getForm().getRichiestaDetail().getNm_userid_codificato().setHidden(false);
        UsrUserRowBean user = amministrazioneUtentiEjb.getUserRowBean(BigDecimal.valueOf(getUser().getIdUtente()));
        getForm().getRichiestaDetail().getId_user_iam_rich().setValue(user.getIdUserIam().toPlainString());
        getForm().getRichiestaDetail().getNm_userid_codificato()
                .setValue(user.getNmCognomeUser() + " " + user.getNmNomeUser());
        //
        getForm().getRichiestaDetail().getDs_email_rich().setHidden(true);
        // Bottone salva personalizzato
        getForm().getRichiestaDetail().getSalvaRichiesta().setEditMode();
    }

    @Override
    public void deleteIndIpList() throws EMFError {
        /* Recupero la riga che ho deciso di cancellare */
        UsrIndIpUserRowBean currentRow = (UsrIndIpUserRowBean) getForm().getIndIpList().getTable().getCurrentRow();
        int rowIndex = getForm().getIndIpList().getTable().getCurrentRowIndex();
        /* Recupero l'insieme delle applicazioni che dovrò cancellare e che mi sto tenendo in memoria */
        Set<BigDecimal> indIpDeleteList = getIndIpDeleteList();
        /*
         * Attenzione: se idIndIpUser è nullo, significa che il record non era ancora stato persistito su DB (es. ho
         * fatto inserisci e subito cancella nell'online senza prima salvare). Dunque questo record non va aggiunto
         * nella lista di quelli da cancellare proprio perchè non c'è su DB!
         */
        if (currentRow.getIdIndIpUser() != null) {
            indIpDeleteList.add(currentRow.getIdIndIpUser());
            getSession().setAttribute("indIpDeleteList", indIpDeleteList);

            Set<BigDecimal> applicationsEditList = getApplicationsEditList();
            for (UsrUsoUserApplicRowBean row : (UsrUsoUserApplicTableBean) getForm().getApplicazioniList().getTable()) {
                applicationsEditList.add(row.getIdApplic());
            }
            getSession().setAttribute("applicationsEditList", applicationsEditList);
        }
        getForm().getIndIpList().getTable().remove(rowIndex);
        Set<String> indIpSet = getIndIpSet();
        indIpSet.remove(currentRow.getCdIndIpUser());
        getSession().setAttribute("indIpSet", indIpSet);
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
    }

    /**
     * Cancella un elemento dalla lista applicazioni La modifica NON ha effetto immediato su DB
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public void deleteApplicazioniList() throws EMFError {
        UsrUsoUserApplicRowBean currentRow = (UsrUsoUserApplicRowBean) getForm().getApplicazioniList().getTable()
                .getCurrentRow();
        /* Elimino solo se l'applicazione è diversa da SACER_IAM */
        if (!currentRow.getString("nm_applic").equals("SACER_IAM")) {
            // Elimino le applicazioni SACER o SACER_PING solo se per esse non sono stati effettuati versamenti
            // onde evitare possibili problemi di integrità
            if (currentRow.getIdUsoUserApplic() == null || !amministrazioneUtentiEjb
                    .checkExistsVersamenti(currentRow.getString("nm_applic"), currentRow.getIdUsoUserApplic())) {
                int rowIndex = getForm().getApplicazioniList().getTable().getCurrentRowIndex();
                Set<BigDecimal> applicationsDeleteList = getApplicationsDeleteList();
                if (currentRow.getIdUsoUserApplic() != null) {
                    applicationsDeleteList.add(currentRow.getIdUsoUserApplic());
                    getSession().setAttribute("applicationsDeleteList", applicationsDeleteList);
                } else {
                    Set<BigDecimal> applicationsEditList = getApplicationsEditList();
                    applicationsEditList.remove(currentRow.getIdApplic());
                    getSession().setAttribute("applicationsEditList", applicationsEditList);
                }
                getForm().getApplicazioniList().getTable().remove(rowIndex);
                Set<BigDecimal> applicationsSet = getApplicationsSet();
                applicationsSet.remove(currentRow.getIdApplic());
                getSession().setAttribute("applicationsSet", applicationsSet);

                /*
                 * Ora però devo togliere anche tutti i record delle liste ruoli default, abilitazioni organizzazioni e
                 * abilitazioni tipi dato relativi all'applicazione tolta! Quindi vanno tolti a video e inseriti nelle
                 * relative liste da cancellare
                 */
                BigDecimal idApplicazione = currentRow.getIdApplic();
                String applicazione = amministrazioneUtentiEjb.getAplApplicRowBean(idApplicazione).getNmApplic();

                // RUOLI DEFAULT
                UsrUsoRuoloUserDefaultTableBean ruoliDefault = (UsrUsoRuoloUserDefaultTableBean) getForm()
                        .getRuoliDefaultList().getTable();
                Set<BigDecimal> defaultRolesDeleteList = getDefaultRolesDeleteList();
                for (int i = 0; i < ruoliDefault.size(); i++) {
                    if (ruoliDefault.getRow(i).getString("nm_applic").equals(applicazione)) {
                        // Aggiunto la voce all'elenco di dati da eliminare da DB...
                        defaultRolesDeleteList.add(ruoliDefault.getRow(i).getIdUsoRuoloUserDefault());
                        getSession().setAttribute("defaultRolesDeleteList", defaultRolesDeleteList);
                        // ... e la rimuovo a video...
                        getForm().getRuoliDefaultList().getTable().remove(i);
                        i--;
                    }
                }

                // ORGANIZZAZIONI
                UsrDichAbilOrganizTableBean organizzazioni = (UsrDichAbilOrganizTableBean) getForm()
                        .getDichAbilOrgList().getTable();
                Set<BigDecimal> dichOrganizDeleteList = getDichOrganizDeleteList();
                for (int i = 0; i < organizzazioni.size(); i++) {
                    if (organizzazioni.getRow(i).getString("nm_applic").equals(applicazione)) {
                        dichOrganizDeleteList.add(organizzazioni.getRow(i).getIdDichAbilOrganiz());
                        getSession().setAttribute("dichOrganizDeleteList", dichOrganizDeleteList);
                        getForm().getDichAbilOrgList().getTable().remove(i);
                        i--;
                    }
                }

                // TIPI DATO
                UsrDichAbilDatiTableBean tipiDato = (UsrDichAbilDatiTableBean) getForm().getDichAbilTipiDatoList()
                        .getTable();
                Set<BigDecimal> dichTipiDatoDeleteList = getDichTipiDatoDeleteList();
                for (int i = 0; i < tipiDato.size(); i++) {
                    if (tipiDato.getRow(i).getString("nm_applic").equals(applicazione)) {
                        dichTipiDatoDeleteList.add(tipiDato.getRow(i).getIdDichAbilDati());
                        getSession().setAttribute("dichTipiDatoDeleteList", dichTipiDatoDeleteList);
                        getForm().getDichAbilTipiDatoList().getTable().remove(i);
                        i--;
                    }
                }
            } else {
                getMessageBox().addMessage(Message.MessageLevel.WAR,
                        "Attenzione: non è possibile eliminare l'applicazione in quanto l'utente ha effettuato versamenti");
            }
        } else {
            getMessageBox().addMessage(Message.MessageLevel.WAR,
                    "Attenzione: non è possibile eliminare l'applicazione SACER_IAM dalla lista!");
        }
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
    }

    /**
     * Marca per l'eliminazione il ruolo di default selezionato dalla lista di dettaglio utente. L'id del ruolo da
     * eliminare, se presente, ovvero se il record è salvato su database, viene mantenuto in un set ed eliminato al
     * salvataggio dell'utente, altrimenti viene semplicemente eliminato il rowbean dalla lista
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public void deleteRuoliDefaultList() throws EMFError {
        UsrUsoRuoloUserDefaultRowBean currentRow = (UsrUsoRuoloUserDefaultRowBean) getForm().getRuoliDefaultList()
                .getTable().getCurrentRow();
        int rowIndex = getForm().getRuoliDefaultList().getTable().getCurrentRowIndex();
        String applicazione = getForm().getRuoliDefaultList().getTable().getCurrentRow().getString("nm_applic");
        BigDecimal idApplic = getForm().getRuoliDefaultList().getTable().getCurrentRow().getBigDecimal("id_applic");
        Set<BigDecimal> ruoliDefaultDeleteList = getDefaultRolesDeleteList();
        boolean editRole = false;
        if (currentRow.getBigDecimal("id_uso_ruolo_user_default") != null) {
            ruoliDefaultDeleteList.add(currentRow.getBigDecimal("id_uso_ruolo_user_default"));
            getSession().setAttribute("defaultRolesDeleteList", ruoliDefaultDeleteList);
            editRole = true;
        }
        getForm().getRuoliDefaultList().getTable().remove(rowIndex);
        Set<BigDecimal> rolesDefault = getRolesSet(applicazione);
        rolesDefault.remove(currentRow.getIdRuolo());
        getSession().setAttribute("rolesSet_" + applicazione, rolesDefault);

        if (editRole && idApplic != null) {
            if (amministrazioneUtentiEjb.hasServicesAuthorization(currentRow.getIdRuolo(), idApplic)) {
                Set<BigDecimal> applicationsEditList = getApplicationsEditList();
                applicationsEditList.add(idApplic);
                getSession().setAttribute("applicationsEditList", applicationsEditList);
            }
        }
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
    }

    /**
     * Marca per l'eliminazione la dichiarazione di abilitazione in organizzazione selezionata dalla lista di dettaglio
     * utente. L'id della dichiarazione da eliminare, se presente, ovvero se il record è salvato su database, viene
     * mantenuto in un set ed eliminato al salvataggio dell'utente, altrimenti viene semplicemente eliminato il rowbean
     * dalla lista
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public void deleteDichAbilOrgList() throws EMFError {
        UsrDichAbilOrganizRowBean currentRow = (UsrDichAbilOrganizRowBean) getForm().getDichAbilOrgList().getTable()
                .getCurrentRow();
        int rowIndex = getForm().getDichAbilOrgList().getTable().getCurrentRowIndex();
        Set<BigDecimal> organizDeleteList = getDichOrganizDeleteList();
        BigDecimal idApplic = currentRow.getBigDecimal("id_applic");
        String applicazione = currentRow.getString("nm_applic");
        if (currentRow.getIdDichAbilOrganiz() != null) {
            organizDeleteList.add(currentRow.getIdDichAbilOrganiz());
            getSession().setAttribute("dichOrganizDeleteList", organizDeleteList);

            if (idApplic != null) {
                Set<BigDecimal> applicationsEditList = getApplicationsEditList();
                applicationsEditList.add(idApplic);
                getSession().setAttribute("applicationsEditList", applicationsEditList);
            }
        }
        getForm().getDichAbilOrgList().getTable().remove(rowIndex);
        Set<PairAuth> scopoSet = getScopoOrgSet(applicazione);
        if (currentRow.getTiScopoDichAbilOrganiz().equals(ActionEnums.ScopoDichAbilOrganiz.ALL_ORG.name())) {
            scopoSet.remove(new PairAuth(currentRow.getTiScopoDichAbilOrganiz(), BigDecimal.ZERO));
        } else {
            scopoSet.remove(new PairAuth(currentRow.getTiScopoDichAbilOrganiz(), currentRow.getIdOrganizIam()));
        }
        getSession().setAttribute("scopoOrgSet_" + applicazione, scopoSet);
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
    }

    /**
     * Marca per l'eliminazione la dichiarazione di abilitazione in tipi dato selezionata dalla lista di dettaglio
     * utente. L'id della dichiarazione da eliminare, se presente, ovvero se il record è salvato su database, viene
     * mantenuto in un set ed eliminato al salvataggio dell'utente, altrimenti viene semplicemente eliminato il rowbean
     * dalla lista
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public void deleteDichAbilTipiDatoList() throws EMFError {
        UsrDichAbilDatiRowBean currentRow = (UsrDichAbilDatiRowBean) getForm().getDichAbilTipiDatoList().getTable()
                .getCurrentRow();
        int rowIndex = getForm().getDichAbilTipiDatoList().getTable().getCurrentRowIndex();
        Set<BigDecimal> tipiDatoDeleteList = getDichTipiDatoDeleteList();
        String applicazione = currentRow.getString("nm_applic");
        BigDecimal idApplic = currentRow.getBigDecimal("id_applic");
        BigDecimal idClasseTipoDato = currentRow.getIdClasseTipoDato();
        if (currentRow.getIdDichAbilDati() != null) {
            tipiDatoDeleteList.add(currentRow.getIdDichAbilDati());
            getSession().setAttribute("dichTipiDatoDeleteList", tipiDatoDeleteList);

            if (idApplic != null) {
                Set<BigDecimal> applicationsEditList = getApplicationsEditList();
                applicationsEditList.add(idApplic);
                getSession().setAttribute("applicationsEditList", applicationsEditList);
            }
        }
        getForm().getDichAbilTipiDatoList().getTable().remove(rowIndex);
        Set<PairAuth> scopoSet = getScopoTipiDatoSet(applicazione);
        if (currentRow.getTiScopoDichAbilDati().equals(ActionEnums.ScopoDichAbilDato.ALL_ORG.name())) {
            scopoSet.remove(
                    new PairAuth(idClasseTipoDato, new PairAuth(currentRow.getTiScopoDichAbilDati(), BigDecimal.ZERO)));
        } else {
            scopoSet.remove(new PairAuth(idClasseTipoDato,
                    new PairAuth(currentRow.getTiScopoDichAbilDati(), currentRow.getIdOrganizIam())));
        }
        getSession().setAttribute("scopoTipiDatoSet_" + applicazione, scopoSet);
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
    }

    /**
     * Marca per l'eliminazione la dichiarazione di abilitazione enti convenzionati selezionata dalla lista di dettaglio
     * utente. L'id della dichiarazione da eliminare, se presente, ovvero se il record è salvato su database, viene
     * mantenuto in un set ed eliminato al salvataggio dell'utente, altrimenti viene semplicemente eliminato il rowbean
     * dalla lista
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public void deleteDichAbilEnteConvenzList() throws EMFError {
        UsrDichAbilEnteConvenzRowBean currentRow = (UsrDichAbilEnteConvenzRowBean) getForm()
                .getDichAbilEnteConvenzList().getTable().getCurrentRow();
        int rowIndex = getForm().getDichAbilEnteConvenzList().getTable().getCurrentRowIndex();
        BigDecimal idAmbienteEnteConvenz = currentRow.getIdAmbienteEnteConvenz();
        BigDecimal idEnteConvenz = currentRow.getIdEnteConvenz();
        Set<BigDecimal> enteConvenzDeleteList = getDichAbilEnteConvenzDeleteList();
        if (currentRow.getIdDichAbilEnteConvenz() != null) {
            enteConvenzDeleteList.add(currentRow.getIdDichAbilEnteConvenz());
            getSession().setAttribute("dichAbilEnteConvenzDeleteList", enteConvenzDeleteList);
        }
        getForm().getDichAbilEnteConvenzList().getTable().remove(rowIndex);
        Set<PairAuth> scopoSet = getScopoEnteConvenzSet();
        if (currentRow.getTiScopoDichAbilEnte().equals(ActionEnums.ScopoDichAbilEnteConvenz.ALL_AMBIENTI.name())) {
            scopoSet.remove(
                    new PairAuth(currentRow.getTiScopoDichAbilEnte(), new PairAuth(BigDecimal.ZERO, BigDecimal.ZERO)));
        } else if (currentRow.getTiScopoDichAbilEnte()
                .equals(ActionEnums.ScopoDichAbilEnteConvenz.UN_AMBIENTE.name())) {
            scopoSet.remove(new PairAuth(currentRow.getTiScopoDichAbilEnte(),
                    new PairAuth(idAmbienteEnteConvenz, BigDecimal.ZERO)));
        } else {
            scopoSet.remove(
                    new PairAuth(currentRow.getTiScopoDichAbilEnte(), new PairAuth(BigDecimal.ZERO, idEnteConvenz)));
            scopoSet.remove(new PairAuth(currentRow.getTiScopoDichAbilEnte(),
                    new PairAuth(idAmbienteEnteConvenz, idEnteConvenz)));
        }
        getSession().setAttribute("scopoEnteConvenzSet", scopoSet);
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
    }

    /**
     * Marca per l'eliminazione il ruolo selezionato dalla lista di gestione ruoli per strutture. L'id del ruolo da
     * eliminare, se presente, ovvero se il record è salvato su database, viene mantenuto in un set ed eliminato al
     * salvataggio dell'utente, altrimenti viene semplicemente eliminato il rowbean dalla lista
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public void deleteRuoliList() throws EMFError {
        UsrVLisUsoRuoloDichRowBean currentRow = (UsrVLisUsoRuoloDichRowBean) getForm().getRuoliList().getTable()
                .getCurrentRow();
        int rowIndex = getForm().getRuoliList().getTable().getCurrentRowIndex();
        getForm().getRuoliList().getTable().remove(rowIndex);
        Set<BigDecimal> orgRolesDeleteList = getOrgRolesDeleteList();
        if (currentRow.getBigDecimal("id_uso_ruolo_dich") != null) {
            orgRolesDeleteList.add(currentRow.getBigDecimal("id_uso_ruolo_dich"));
            getSession().setAttribute("orgRolesDeleteList", orgRolesDeleteList);
        }
        Set<BigDecimal> orgRolesSet = getOrgRolesSet();
        orgRolesSet.remove(currentRow.getIdRuolo());
        getSession().setAttribute("orgRolesSet", orgRolesSet);
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
    }

    @Override
    protected String getDefaultPublsherName() {
        return Application.Publisher.RICERCA_UTENTI;
    }

    @Override
    public void reloadAfterGoBack(String publisherName) {
        User u = (User) getRequest().getSession().getAttribute("###_USER_CONTAINER");
        int lastIndex = u.getMenu().getSelectedPath("").size() - 1;
        String lastMenuEntry = (u.getMenu().getSelectedPath("").get(lastIndex)).getCodice();
        /*
         * Ricarico la lista organizzazioni presente in dettaglio utente in quanto potrebbe aver subito modifiche:
         * recupero le vecchie, e le richiedo da DB
         */
        if (getForm().getRuoliList().getTable() != null && getLastPublisher().contains("/listaRuoliOrganizzazione")) {
            try {
                /* Recupero le dichiarazioni attraverso gli uso user applic */
                UsrUsoUserApplicTableBean uuuaTB = (UsrUsoUserApplicTableBean) getForm().getApplicazioniList()
                        .getTable();
                Set<BigDecimal> uuuaSet = new HashSet();
                for (UsrUsoUserApplicRowBean uuuaRB : uuuaTB) {
                    uuuaSet.add(uuuaRB.getIdUsoUserApplic());
                }

                // Carico la lista delle dichiarazioni di abilitazione sulle organizzazioni
                int paginaCorrenteOrg = getForm().getDichAbilOrgList().getTable().getCurrentPageIndex();
                int inizioOrg = getForm().getDichAbilOrgList().getTable().getFirstRowPageIndex();
                int pageSize = getForm().getDichAbilOrgList().getTable().getPageSize();
                UsrDichAbilOrganizTableBean dichAbilOrg = amministrazioneUtentiEjb
                        .getUsrDichAbilOrganizTableBean(uuuaSet);
                getForm().getDichAbilOrgList().setTable(dichAbilOrg);
                // Workaround in modo che la lista punti al primo record, non all'ultimo
                getForm().getDichAbilOrgList().getTable().first();
                getForm().getDichAbilOrgList().getTable().setPageSize(pageSize);
                this.lazyLoadGoPage(getForm().getDichAbilOrgList(), paginaCorrenteOrg);
                getForm().getDichAbilOrgList().getTable().setCurrentRowIndex(inizioOrg);
            } catch (EMFError ex) {
                log.error(ECCEZIONE_MSG, ex);
            }
        } else if (publisherName.equals(Application.Publisher.RICERCA_UTENTI)) {
            try {
                // Rieseguo la query per avere la lista Utenti tenuto conto delle modifiche apportate
                int inizio = getForm().getListaUtenti().getTable().getFirstRowPageIndex();
                int paginaCorrente = getForm().getListaUtenti().getTable().getCurrentPageIndex();
                int pageSize = getForm().getListaUtenti().getTable().getPageSize();
                TypeValidator validator = new TypeValidator(getMessageBox());
                Date[] validaDate = validator.validaDate(getForm().getFiltriUtenti().getDt_rich_gest_user_da().parse(),
                        null, null, getForm().getFiltriUtenti().getDt_rich_gest_user_a().parse(), null, null,
                        getForm().getFiltriUtenti().getDt_rich_gest_user_da().getName(),
                        getForm().getFiltriUtenti().getDt_rich_gest_user_a().getName());
                getForm().getListaUtenti().setTable(amministrazioneUtentiEjb.getUsrVLisUserTableBean(
                        getForm().getFiltriUtenti(), validaDate, getUser().getIdUtente(), null));
                getForm().getListaUtenti().getTable().setPageSize(pageSize);
                getForm().getListaUtenti().setUserOperations(true, true, true, true);
                // rieseguo la query se necessario
                this.lazyLoadGoPage(getForm().getListaUtenti(), paginaCorrente);
                // ritorno alla pagina
                getForm().getListaUtenti().getTable().setCurrentRowIndex(inizio);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } else if (publisherName.equals(Application.Publisher.RICERCA_RICHIESTE)) {
            try {
                // Rieseguo la query per avere la lista richieste tenuto conto delle modifiche apportate
                String nmCognomeUser = getForm().getFiltriRichieste().getNm_cognome_user_app_rich().parse();
                String nmNomeUser = getForm().getFiltriRichieste().getNm_nome_user_app_rich().parse();
                String nmUserid = getForm().getFiltriRichieste().getNm_userid_app_rich().parse();
                BigDecimal idAmbienteEnteConvenz = getForm().getFiltriRichieste().getId_ambiente_ente_convenz().parse();
                BigDecimal idEnteConvenz = getForm().getFiltriRichieste().getId_ente_convenz().parse();
                String nmCognomeUserRich = getForm().getFiltriRichieste().getNm_cognome_user_rich().parse();
                String nmNomeUserRich = getForm().getFiltriRichieste().getNm_nome_user_rich().parse();
                String nmUseridRich = getForm().getFiltriRichieste().getNm_userid_rich().parse();
                BigDecimal idOrganizIam = getForm().getFiltriRichieste().getId_organiz_iam().parse();
                String cdRegistroRichGestUser = getForm().getFiltriRichieste().getCd_registro_rich_gest_user().parse();
                BigDecimal aaRichGestUser = getForm().getFiltriRichieste().getAa_rich_gest_user().parse();
                String cdKeyRichGestUser = getForm().getFiltriRichieste().getCd_key_rich_gest_user().parse();
                String cdRichGestUser = getForm().getFiltriRichieste().getCd_rich_gest_user().parse();
                String tiStatoRichGestUser = getForm().getFiltriRichieste().getTi_stato_rich_gest_user().parse();
                int inizio = getForm().getRichiesteList().getTable().getFirstRowPageIndex();
                int paginaCorrente = getForm().getRichiesteList().getTable().getCurrentPageIndex();
                int pageSize = getForm().getRichiesteList().getTable().getPageSize();
                getForm().getRichiesteList()
                        .setTable(amministrazioneUtentiEjb.getUsrVRicRichiesteTableBean(
                                BigDecimal.valueOf(getUser().getIdUtente()), nmCognomeUser, nmNomeUser, nmUserid,
                                idAmbienteEnteConvenz, idEnteConvenz, nmCognomeUserRich, nmNomeUserRich, nmUseridRich,
                                idOrganizIam, cdRegistroRichGestUser, aaRichGestUser, cdKeyRichGestUser, cdRichGestUser,
                                tiStatoRichGestUser));
                getForm().getRichiesteList().getTable().setPageSize(pageSize);
                getForm().getRichiesteList().setUserOperations(true, true, true, true);
                // rieseguo la query se necessario
                this.lazyLoadGoPage(getForm().getRichiesteList(), paginaCorrente);
                // ritorno alla pagina
                getForm().getRichiesteList().getTable().setCurrentRowIndex(inizio);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } else if (publisherName.equals(Application.Publisher.DETTAGLIO_RICHIESTA)
                && getForm().getRichiesteList().getStatus().equals(Status.view)) {
            try {
                BigDecimal idRichGestUser = ((BaseRowInterface) getForm().getRichiesteList().getTable().getCurrentRow())
                        .getBigDecimal("id_rich_gest_user");
                loadDettaglioRichiesta(idRichGestUser);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } else if (publisherName.equals(Application.Publisher.DETTAGLIO_UTENTE)
                && getForm().getListaUtenti().getStatus().equals(Status.view)) {
            try {
                setTableName(getForm().getListaUtenti().getName());
                loadDettaglio();
            } catch (EMFError ex) {
                log.error("Eccezione", ex);
            }
        } else if (lastMenuEntry.contains("SchedulazioniJob")) {
            try {
                if (getForm().getFiltriJobSchedulati().getNm_job().parse() != null) {
                    ricercaJobSchedulati();
                }

            } catch (EMFError ex) {
                log.error("Eccezione", ex);
            }
        }
    }

    public void reloadListaUtenti() {
        try {
            // Rieseguo la query per avere la lista Utenti tenuto conto delle modifiche apportate
            int inizio = getForm().getListaUtenti().getTable().getFirstRowPageIndex();
            int paginaCorrente = getForm().getListaUtenti().getTable().getCurrentPageIndex();
            int pageSize = getForm().getListaUtenti().getTable().getPageSize();
            TypeValidator validator = new TypeValidator(getMessageBox());
            Date[] validaDate = validator.validaDate(getForm().getFiltriUtenti().getDt_rich_gest_user_da().parse(),
                    null, null, getForm().getFiltriUtenti().getDt_rich_gest_user_a().parse(), null, null,
                    getForm().getFiltriUtenti().getDt_rich_gest_user_da().getName(),
                    getForm().getFiltriUtenti().getDt_rich_gest_user_a().getName());
            getForm().getListaUtenti().setTable(amministrazioneUtentiEjb
                    .getUsrVLisUserTableBean(getForm().getFiltriUtenti(), validaDate, getUser().getIdUtente(), null));
            getForm().getListaUtenti().getTable().setPageSize(pageSize);
            getForm().getListaUtenti().setUserOperations(true, true, true, true);
            // rieseguo la query se necessario
            this.lazyLoadGoPage(getForm().getListaUtenti(), paginaCorrente);
            // ritorno alla pagina
            getForm().getListaUtenti().getTable().setCurrentRowIndex(inizio);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public JSONObject triggerApplicazioniFieldsNm_applicOnTrigger() throws EMFError {
        getForm().getApplicazioniFields().post(getRequest());
        BigDecimal applic = getForm().getApplicazioniFields().getNm_applic().parse();
        if (applic != null) {
            AplApplicRowBean aplApplicById = amministrazioneUtentiEjb.getAplApplicRowBean(applic);
            getForm().getApplicazioniFields().getDs_applic().setValue(aplApplicById.getDsApplic());
        } else {
            getForm().getApplicazioniFields().getDs_applic().setValue("\u00A0");
        }
        return getForm().getApplicazioniFields().asJSON();
    }

    @Override
    public void aggiungiApplicazione() throws EMFError {
        addRowToApplicazioniList();
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
    }

    @Override
    public void aggiungiIP() throws EMFError {
        addRowToIPList();
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
    }

    private void addRowToIPList() throws EMFError {
        getForm().getIndIpFields().getCd_ind_ip_user().post(getRequest());
        String indIp = getForm().getIndIpFields().getCd_ind_ip_user().parse();

        /* Verifico la correttezza formale dell'indirizzo IP */
        checkIpAddress(indIp);

        if (!getMessageBox().hasError()) {
            if (getIndIpSet().contains(indIp)) {
                // Indirizzo IP già presente
                getMessageBox().addError("Indirizzo IP/Sottorete già presente nella lista");
            } else {
                // Creo la nuova riga
                Set<String> indzIp = getIndIpSet();
                indzIp.add(indIp);
                getSession().setAttribute("indIpSet", indzIp);
                UsrIndIpUserRowBean indIpRow = new UsrIndIpUserRowBean();
                indIpRow.setCdIndIpUser(indIp);
                getForm().getIndIpList().getTable().add(indIpRow);
                getForm().getIndIpList().getTable().addSortingRule(UsrIndIpUserTableDescriptor.COL_CD_IND_IP_USER,
                        SortingRule.ASC);
                getForm().getIndIpList().getTable().sort();

                Set<BigDecimal> applicationsEditList = getApplicationsEditList();
                for (UsrUsoUserApplicRowBean row : (UsrUsoUserApplicTableBean) getForm().getApplicazioniList()
                        .getTable()) {
                    applicationsEditList.add(row.getIdApplic());
                }
                getSession().setAttribute("applicationsEditList", applicationsEditList);
            }
        }
    }

    private void checkIpAddress(String address) {
        if (address.equals("")) {
            getMessageBox().addError("Non è stato digitato alcun indirizzo IP");
        } else if (!address.contains("/")) {
            Matcher matcher = addressPattern.matcher(address);
            if (!matcher.matches()) {
                getMessageBox().addError("Attenzione: indirizzo IP formalmente errato!");
            }
        } else {
            Matcher matcher = cidrPattern.matcher(address);
            if (!matcher.matches()) {
                getMessageBox().addError("Attenzione: sottorete formalmente errata!");
            }
        }
    }

    private boolean checkUsername(String username) {
        if (username.equals("")) {
            getMessageBox().addError("Non è stato digitato alcun username");
            return false;
        } else {
            Matcher matcher = usernamePattern.matcher(username);
            if (!matcher.matches()) {
                getMessageBox().addError(
                        "Attenzione: username formalmente errato! Il campo username può contenere solo caratteri alfanumerici, maiuscoli e minuscoli, underscore, trattini e punti.");
                return false;
            }
        }
        return true;
    }

    /**
     * Esegue i controlli di validazione per l'inserimento di una nuova applicazione
     *
     * @param newRow
     *            true se si tratta dell'inserimento di una nuova riga, false in caso di modifica
     *
     * @throws EMFError
     *             errore generico
     */
    private void addRowToApplicazioniList() throws EMFError {
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
                AplApplicRowBean row = amministrazioneUtentiEjb.getAplApplicRowBean(applic);
                getForm().getApplicazioniList().getTable().add(row);
                getForm().getApplicazioniList().getTable().addSortingRule(AplApplicTableDescriptor.COL_NM_APPLIC,
                        SortingRule.ASC);
                getForm().getApplicazioniList().getTable().sort();
            }
        } else {
            // Non è stata selezionata alcuna applicazione
            getMessageBox().addError("Non è stata selezionata alcuna applicazione");
        }
    }

    /**
     * Esegue l'inserimento di una nuova riga nella lista corrente. In ognuno dei metodi chiamati vengono eseguiti i
     * controlli di validazione per la riga.
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public void add_row_to_list() throws EMFError {
        if (getForm().getListaUtenti().getStatus().equals(Status.insert)) {
            getForm().getDettaglioUtente().getCd_psw().post(getRequest());
            getSession().setAttribute("passwordUtente", getForm().getDettaglioUtente().getCd_psw().getValue());
        }
        if (getForm().getWizardListsTabs().getListaWizardRuoliDefault().isCurrent()) {
            saveRuoliDefaultList();
        } else if (getForm().getWizardListsTabs().getListaWizardAbilOrganiz().isCurrent()) {
            saveOrganizList();
        } else if (getForm().getWizardListsTabs().getListaWizardTipiDato().isCurrent()) {
            saveTipiDatoList();
        } else if (getForm().getWizardListsTabs().getListaWizardAbilEnteConvenz().isCurrent()) {
            saveEnteConvenzList();
        }
        if (!getMessageBox().hasError()) {
            getForm().getDichAbilFields().clear();
        }
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
    }

    @Override
    public JSONObject triggerDichAbilFieldsNm_applicOnTrigger() throws EMFError {
        // Azzero le combo
        getForm().getDichAbilFields().getTi_scopo_dich_abil_organiz().setValue("");
        getForm().getDichAbilFields().getDl_composito_organiz().setDecodeMap(new DecodeMap());

        getForm().getDichAbilFields().getNm_applic().post(getRequest());
        BigDecimal idApplic = getForm().getDichAbilFields().getNm_applic().parse();
        // PrfRuoloTableBean prfRuoliViewBean = amministrazioneUtentiEjb.getPrfRuoliTableBean(idApplic);
        String tipoEnte = amministrazioneUtentiEjb
                .getTipoEnteConvenzNonConvenz(getForm().getDettaglioUtente().getId_ente_siam_appart().parse());
        String tipoUtente = getForm().getDettaglioUtente().getTipo_user().parse();
        PrfRuoloTableBean prfRuoliViewBean = amministrazioneUtentiEjb.getPrfRuoliUtenteTableBean(idApplic, tipoEnte,
                tipoUtente);
        DecodeMap ruoliMap = DecodeMap.Factory.newInstance(prfRuoliViewBean, "id_ruolo", "nm_ruolo");
        getForm().getDichAbilFields().getNm_ruolo().setDecodeMap(ruoliMap);
        AplClasseTipoDatoTableBean classeTipoDatoTableBean = amministrazioneUtentiEjb
                .getAplClasseTipoDatoTableBean(idApplic);
        DecodeMap tipiDatoMap = DecodeMap.Factory.newInstance(classeTipoDatoTableBean, "id_classe_tipo_dato",
                "nm_classe_tipo_dato");
        getForm().getDichAbilFields().getNm_classe_tipo_dato().setDecodeMap(tipiDatoMap);
        return getForm().getDichAbilFields().asJSON();
    }

    /*
     * Metodo per il salvataggio delle dichiarazioni di abilitazione organizzazione Esegue i controlli di coerenza prima
     * del salvataggio. In caso affermativo, inseriscono l'abilitazione nella lista, altrimenti errore
     *
     * @throws EMFError errore generico
     */
    private void saveOrganizList() throws EMFError {
        getForm().getDichAbilFields().post(getRequest());
        String applicazione = getForm().getDichAbilFields().getNm_applic().getDecodedValue();
        BigDecimal idApplic = getForm().getDichAbilFields().getNm_applic().parse();
        String scopo = getForm().getDichAbilFields().getTi_scopo_dich_abil_organiz().parse();
        BigDecimal idOrganizIam = getForm().getDichAbilFields().getDl_composito_organiz().parse() != null
                ? getForm().getDichAbilFields().getDl_composito_organiz().parse() : BigDecimal.ZERO;
        String dlCompositoOrganiz = getForm().getDichAbilFields().getDl_composito_organiz().getDecodedValue();
        Set<PairAuth> scopoSet = getScopoOrgSet(applicazione);
        if (StringUtils.isNotBlank(applicazione)) {
            if (StringUtils.isNotBlank(scopo)) {
                // Se ho scelto come scopo ALL_ORG
                if (scopo.equals(ActionEnums.ScopoDichAbilOrganiz.ALL_ORG.name()) && scopoSet.size() > 0) {
                    getMessageBox().addError(
                            "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione");
                } // Se ho scelto come scopo ALL_ORG_CHILD
                else if (scopo.equals(ActionEnums.ScopoDichAbilOrganiz.ALL_ORG_CHILD.name())) {
                    if (StringUtils.isNotBlank(dlCompositoOrganiz)) {
                        if (idOrganizIam.equals(BigDecimal.ZERO)) {
                            getMessageBox().addError("Non è stata impostata l'organizzazione");
                        } else if (scopoSet.contains(
                                new PairAuth(ActionEnums.ScopoDichAbilOrganiz.ALL_ORG.name(), BigDecimal.ZERO))) {
                            getMessageBox().addError(
                                    "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo ALL_ORG");
                        } else if (amministrazioneUtentiEjb.checkDichAutorSetOrganizForChilds(idOrganizIam,
                                getScopoOrgSet(applicazione))) {
                            getMessageBox().addError(
                                    "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo ALL_ORG_CHILD relativa ad una organizzazione 'figlia'");
                        } else if (amministrazioneUtentiEjb.checkDichAutorSetOrganizUnaOrgForChilds(idOrganizIam,
                                getScopoOrgSet(applicazione))) {
                            getMessageBox().addError(
                                    "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo UNA_ORG relativa ad una organizzazione 'figlia'");
                        } else {
                            // Prendo il "padre" dell'organizzazione che sto inserendo
                            BigDecimal padre = amministrazioneUtentiEjb.getPadre(idOrganizIam);
                            if (padre != null) {
                                if (scopoSet.contains(
                                        new PairAuth(ActionEnums.ScopoDichAbilOrganiz.ALL_ORG_CHILD.name(), padre))) {
                                    getMessageBox().addError(
                                            "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo ALL_ORG_CHILD relativa ad una organizzazione 'padre'");
                                }
                            }
                        }
                    } else {
                        getMessageBox().addError("Non è stata inserita l'organizzazione");
                    } // Se ho scelto come scopo UNA_ORG o ORG_DEFAULT
                } else if (scopo.equals(ActionEnums.ScopoDichAbilOrganiz.UNA_ORG.name())) {
                    if (StringUtils.isNotBlank(dlCompositoOrganiz)) {
                        if (scopoSet.contains(
                                new PairAuth(ActionEnums.ScopoDichAbilOrganiz.ALL_ORG.name(), BigDecimal.ZERO))) {
                            getMessageBox().addError(
                                    "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo ALL_ORG");
                        } else if (scopoSet.contains(
                                new PairAuth(ActionEnums.ScopoDichAbilOrganiz.ORG_DEFAULT.name(), idOrganizIam))
                                || scopoSet.contains(
                                        new PairAuth(ActionEnums.ScopoDichAbilOrganiz.UNA_ORG.name(), idOrganizIam))) {
                            getMessageBox().addError(
                                    "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo UNA_ORG relativa alla stessa organizzazione");
                        } // Controllo gli scopi ALL_ORG_CHILD presenti nella lista
                        else {
                            // Prendo i "padri" (padri e nonni...) dell'organizzazione che sto inserendo
                            String[] padri = amministrazioneUtentiEjb.getPadri(idOrganizIam);
                            for (int i = 1; i < padri.length; i++) {
                                if (scopoSet
                                        .contains(new PairAuth(ActionEnums.ScopoDichAbilOrganiz.ALL_ORG_CHILD.name(),
                                                new BigDecimal(padri[i])))) {
                                    getMessageBox().addError(
                                            "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo ALL_ORG_CHILD relativa ad una organizzazione 'padre'");
                                }
                            }
                        }
                    } else {
                        getMessageBox().addError("Non è stata inserita l'organizzazione");
                    }
                } else if (scopo.equals(ActionEnums.ScopoDichAbilOrganiz.ORG_DEFAULT.name())) {
                    if (StringUtils.isNotBlank(dlCompositoOrganiz)) {
                        Iterator iteratore = scopoSet.iterator();
                        while (iteratore.hasNext()) {
                            PairAuth paio = (PairAuth) iteratore.next();
                            String tiScopo = paio.getAppl().toString();
                            if (tiScopo.equals(ActionEnums.ScopoDichAbilOrganiz.ORG_DEFAULT.name())) {
                                getMessageBox().addError(
                                        "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo ORG_DEFAULT");
                            }
                        }
                    }
                }

                if (!getMessageBox().hasError()) {
                    log.debug("MAC 30075 - L'utente corrente " + getUser().getUsername()
                            + " inserisce in 'Lista dich. abil. organiz' la dichiarazione di abilitazione alle organizzazioni per l'utenza "
                            + getForm().getDettaglioUtente().getNm_userid().parse());
                    // Inserisce l'organizzazione nella lista
                    scopoSet.add(new PairAuth(scopo, idOrganizIam));
                    getSession().setAttribute("scopoOrgSet_" + applicazione, scopoSet);
                    UsrDichAbilOrganizRowBean org = new UsrDichAbilOrganizRowBean();
                    org.setIdOrganizIam(idOrganizIam);
                    org.setBigDecimal("id_applic", idApplic);
                    org.setString("nm_applic", applicazione);
                    org.setTiScopoDichAbilOrganiz(scopo);
                    org.setString("dl_composito_organiz", dlCompositoOrganiz);
                    org.setString("nm_ruolo_dich", "Nessun ruolo definito");
                    if (getSession().getAttribute("datiAggiuntivi") != null) {
                        Map<BigDecimal, Object[]> datiAggiuntivi = (Map<BigDecimal, Object[]>) getSession()
                                .getAttribute("datiAggiuntivi");
                        if (idOrganizIam.compareTo(BigDecimal.ZERO) != 0) {
                            Object[] o = datiAggiuntivi.get(idOrganizIam);
                            org.setString("ds_causale_dich", (String) o[1]);
                            String tipoEnte = (String) o[0];
                            if (tipoEnte.equals(ConstOrgEnteSiam.TiEnteConvenz.PRODUTTORE.name())) {
                                org.setBigDecimal("id_appart_colleg_enti", (BigDecimal) o[2]);
                            } else if (tipoEnte.equals(ConstOrgEnteSiam.TiEnteNonConvenz.ORGANO_VIGILANZA.name())) {
                                org.setBigDecimal("id_vigil_ente_produt", (BigDecimal) o[2]);
                            } else if (tipoEnte.equals(ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO.name())) {
                                org.setBigDecimal("id_supt_est_ente_convenz", (BigDecimal) o[2]);
                            }
                        }

                    }

                    getForm().getDichAbilOrgList().getTable().add(org);
                    getForm().getDichAbilOrgList().getTable().sort();

                    log.debug("MAC 30075 - Ecco la dichiarazione appena inserita tramite wizard - applicazione: "
                            + applicazione + ", scopo: " + scopo + ", organizzazione: " + dlCompositoOrganiz);

                    Set<BigDecimal> applicationsEditList = getApplicationsEditList();
                    applicationsEditList.add(idApplic);
                    getSession().setAttribute("applicationsEditList", applicationsEditList);

                    resetDichAbilFields();
                }
            } else {
                // ERRORE
                getMessageBox().addError("Non è stato inserito lo scopo");
            }
        } else {
            // ERRORE
            getMessageBox().addError("Non è stata inserita l'applicazione");
        }
    }

    /*
     * Metodo per il salvataggio delle dichiarazioni di abilitazione tipi dato Esegue i controlli di coerenza prima del
     * salvataggio. In caso affermativo, inseriscono l'abilitazione nella lista, altrimenti errore
     *
     * @throws EMFError errore generico
     */
    private void saveTipiDatoList() throws EMFError {
        getForm().getDichAbilFields().post(getRequest());
        String applicazione = getForm().getDichAbilFields().getNm_applic().getDecodedValue();
        BigDecimal idApplic = getForm().getDichAbilFields().getNm_applic().parse();
        BigDecimal idClasseTipoDato = getForm().getDichAbilFields().getNm_classe_tipo_dato().parse();
        String scopo = getForm().getDichAbilFields().getTi_scopo_dich_abil_dati().parse();
        String dlCompositoOrganiz = getForm().getDichAbilFields().getDl_composito_organiz().getDecodedValue();
        BigDecimal idOrganizIam = getForm().getDichAbilFields().getDl_composito_organiz().parse() != null
                ? getForm().getDichAbilFields().getDl_composito_organiz().parse() : BigDecimal.ZERO;
        BigDecimal idTipoDato = getForm().getDichAbilFields().getNm_tipo_dato().parse() != null
                ? getForm().getDichAbilFields().getNm_tipo_dato().parse() : BigDecimal.ZERO;
        String nmTipoDato = getForm().getDichAbilFields().getNm_tipo_dato().getDecodedValue();
        Set<PairAuth> scopoSet = getScopoTipiDatoSet(applicazione);
        if (StringUtils.isNotBlank(applicazione)) {
            if (idClasseTipoDato != null) {
                if (StringUtils.isNotBlank(scopo)) {
                    // Se ho scelto come scopo ALL_ORG
                    if (scopo.equals(ActionEnums.ScopoDichAbilDato.ALL_ORG.name()) && scopoSet.size() > 0
                            && amministrazioneUtentiEjb.existsDichAnyScopo(scopoSet, idClasseTipoDato)) {
                        // scopoSet.contains(new PairAuth(idClasseTipoDato, new
                        // PairAuth(ActionEnums.ScopoDichAbilDato.ALL_ORG.name(), BigDecimal.ZERO)))) {
                        getMessageBox().addError(
                                "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione");
                    } // Se ho scelto come scopo ALL_ORG_CHILD
                    else if (scopo.equals(ActionEnums.ScopoDichAbilDato.ALL_ORG_CHILD.name())) {
                        if (StringUtils.isNotBlank(dlCompositoOrganiz)) {
                            if (idOrganizIam.equals(BigDecimal.ZERO)) {
                                getMessageBox().addError("Non è stato impostata l'organizzazione");
                            } else if (scopoSet.contains(new PairAuth(idClasseTipoDato,
                                    new PairAuth(ActionEnums.ScopoDichAbilDato.ALL_ORG.name(), BigDecimal.ZERO)))) {
                                getMessageBox().addError(
                                        "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo ALL_ORG");
                            } else if (amministrazioneUtentiEjb.checkDichAutorTipiDatoSetOrganizForChilds(idOrganizIam,
                                    idClasseTipoDato, getScopoOrgSet(applicazione))) {
                                getMessageBox().addError(
                                        "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo ALL_ORG_CHILD relativa ad una organizzazione 'figlia'");
                            } else if (amministrazioneUtentiEjb.checkDichAutorTipiDatoSetOrganizUnaOrgForChilds(
                                    idOrganizIam, idClasseTipoDato, getScopoOrgSet(applicazione))) {
                                getMessageBox().addError(
                                        "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo UNA_ORG relativa ad una organizzazione 'figlia'");
                            }
                        } else {
                            getMessageBox().addError("Non è stato inserita l'organizzazione");
                        } // Se ho scelto come scopo UNA_ORG
                    } else if (scopo.equals(ActionEnums.ScopoDichAbilDato.UNA_ORG.name())) {
                        if (StringUtils.isNotBlank(dlCompositoOrganiz)) {
                            if (scopoSet.contains(new PairAuth(idClasseTipoDato,
                                    new PairAuth(ActionEnums.ScopoDichAbilDato.ALL_ORG.name(), BigDecimal.ZERO)))) {
                                getMessageBox().addError(
                                        "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo ALL_ORG");
                            } else if (scopoSet.contains(new PairAuth(idClasseTipoDato,
                                    new PairAuth(ActionEnums.ScopoDichAbilDato.UNA_ORG.name(), idOrganizIam)))) {
                                getMessageBox().addError(
                                        "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo UNA_ORG relativa alla stessa organizzazione");
                            } // Controllo gli scopi ALL_ORG_CHILD presenti nella lista
                            else {
                                // Prendo i "padri" (padri e nonni...) dell'organizzazione che sto inserendo
                                String[] padri = amministrazioneUtentiEjb.getPadri(idOrganizIam);
                                for (int i = 1; i < padri.length; i++) {
                                    if (scopoSet.contains(new PairAuth(idClasseTipoDato,
                                            new PairAuth(ActionEnums.ScopoDichAbilDato.ALL_ORG_CHILD.name(),
                                                    new BigDecimal(padri[i]))))) {
                                        getMessageBox().addError(
                                                "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo ALL_ORG_CHILD relativa ad una organizzazione 'padre'");
                                    }
                                }
                            }
                        } else {
                            getMessageBox().addError("Non è stato inserita l'organizzazione");
                        }
                    } else if (scopo.equals(ActionEnums.ScopoDichAbilDato.UN_TIPO_DATO.name())) {
                        if (StringUtils.isNotBlank(dlCompositoOrganiz)) {
                            if (StringUtils.isNotBlank(nmTipoDato)) {
                                if (scopoSet.contains(new PairAuth(idClasseTipoDato,
                                        new PairAuth(ActionEnums.ScopoDichAbilDato.ALL_ORG.name(), BigDecimal.ZERO)))) {
                                    getMessageBox().addError(
                                            "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo ALL_ORG");
                                } else if (scopoSet.contains(new PairAuth(idClasseTipoDato,
                                        new PairAuth(ActionEnums.ScopoDichAbilDato.UNA_ORG.name(), idOrganizIam)))) {
                                    getMessageBox().addError(
                                            "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo UNA_ORG relativa alla stessa organizzazione");
                                } else if (scopoSet.contains(new PairAuth(idClasseTipoDato, new PairAuth(
                                        ActionEnums.ScopoDichAbilDato.UN_TIPO_DATO.name(), idOrganizIam)))) {
                                    // Solo per questo caso particolare, devo fare un controllo più approfondito sul
                                    // tipo dato
                                    UsrDichAbilDatiTableBean tb = (UsrDichAbilDatiTableBean) getForm()
                                            .getDichAbilTipiDatoList().getTable();
                                    for (UsrDichAbilDatiRowBean rb : tb) {
                                        // se sono uguali nel valore di id_organiz_iam ed id_tipo_dato
                                        if (rb.getIdOrganizIam() != null && rb.getIdTipoDatoIam() != null) {
                                            if (rb.getIdOrganizIam().compareTo(idOrganizIam) == 0
                                                    && rb.getIdTipoDatoIam().compareTo(idTipoDato) == 0) {
                                                getMessageBox().addError(
                                                        "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo UN_TIPO_DATO relativa allo stesso tipo di dato");
                                                break;
                                            }
                                        }
                                    }
                                } // Controllo gli scopi ALL_ORG_CHILD presenti nella lista
                                else {
                                    // Prendo i "padri" (padri e nonni...) dell'organizzazione che sto inserendo
                                    String[] padri = amministrazioneUtentiEjb.getPadri(idOrganizIam);
                                    for (int i = 1; i < padri.length; i++) {
                                        if (scopoSet.contains(new PairAuth(idClasseTipoDato,
                                                new PairAuth(ActionEnums.ScopoDichAbilDato.ALL_ORG_CHILD.name(),
                                                        new BigDecimal(padri[i]))))) {
                                            getMessageBox().addError(
                                                    "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo ALL_ORG_CHILD relativa ad una organizzazione 'padre'");
                                        }
                                    }
                                }
                            } else {
                                getMessageBox().addError("Non è stato inserito il tipo dato");
                            }
                        } else {
                            getMessageBox().addError("Non è stato inserita l'organizzazione");
                        }
                    }

                    if (!getMessageBox().hasError()) {
                        // Inserisce il tipo dato nella lista
                        scopoSet.add(new PairAuth(idClasseTipoDato, new PairAuth(scopo, idOrganizIam)));
                        getSession().setAttribute("scopoTipiDatoSet_" + applicazione, scopoSet);
                        UsrDichAbilDatiRowBean org = new UsrDichAbilDatiRowBean();
                        org.setIdOrganizIam(idOrganizIam);
                        org.setBigDecimal("id_applic", idApplic);
                        org.setString("nm_applic", applicazione);
                        org.setTiScopoDichAbilDati(scopo);
                        org.setString("dl_composito_organiz", dlCompositoOrganiz);
                        org.setIdClasseTipoDato(idClasseTipoDato);
                        org.setIdTipoDatoIam(idTipoDato);
                        org.setString("nm_classe_tipo_dato",
                                getForm().getDichAbilFields().getNm_classe_tipo_dato().getDecodedValue());
                        org.setString("nm_tipo_dato",
                                getForm().getDichAbilFields().getNm_tipo_dato().getDecodedValue());

                        if (getSession().getAttribute("datiAggiuntivi") != null) {
                            Map<BigDecimal, Object[]> datiAggiuntivi = (Map<BigDecimal, Object[]>) getSession()
                                    .getAttribute("datiAggiuntivi");
                            if (idOrganizIam.compareTo(BigDecimal.ZERO) != 0) {
                                Object[] o = datiAggiuntivi.get(idOrganizIam);
                                org.setString("ds_causale_dich", (String) o[1]);
                                String tipoEnte = (String) o[0];
                                if (tipoEnte.equals(ConstOrgEnteSiam.TiEnteConvenz.PRODUTTORE.name())) {
                                    org.setBigDecimal("id_appart_colleg_enti", (BigDecimal) o[2]);
                                } else if (tipoEnte.equals(ConstOrgEnteSiam.TiEnteNonConvenz.ORGANO_VIGILANZA.name())) {
                                    org.setBigDecimal("id_vigil_ente_produt", (BigDecimal) o[2]);
                                } else if (tipoEnte
                                        .equals(ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO.name())) {
                                    org.setBigDecimal("id_supt_est_ente_convenz", (BigDecimal) o[2]);
                                } else if (tipoEnte
                                        .equals(ConstOrgEnteSiam.TiEnteNonConvenz.SOGGETTO_ATTUATORE.name())) {
                                    org.setBigDecimal("id_supt_est_ente_convenz", (BigDecimal) o[2]);
                                }
                            }

                        }

                        getForm().getDichAbilTipiDatoList().getTable().add(org);
                        getForm().getDichAbilTipiDatoList().getTable().sort();

                        Set<BigDecimal> applicationsEditList = getApplicationsEditList();
                        applicationsEditList.add(idApplic);
                        getSession().setAttribute("applicationsEditList", applicationsEditList);

                        resetDichAbilFields();
                    }
                    // Se ho scelto come scopo ALL_ORG_CHILD
                } else {
                    // ERRORE
                    getMessageBox().addError("Non è stato inserito lo scopo");
                }
            } else {
                // ERRORE
                getMessageBox().addError("Non è stata inserita la classe tipo dato");
            }
        } else {
            // ERRORE
            getMessageBox().addError("Non è stata inserita l'applicazione");
        }
    }

    /*
     * Metodo per il salvataggio delle dichiarazioni di abilitazione sugli enti convenzionati Esegue i controlli di
     * coerenza prima del salvataggio. In caso affermativo, inseriscono l'abilitazione nella lista, altrimenti errore
     *
     * @throws EMFError errore generico
     */
    private void saveEnteConvenzList() throws EMFError {
        getForm().getDichAbilFields().post(getRequest());
        String tiScopo = getForm().getDichAbilFields().getTi_scopo_dich_abil_ente().parse();
        BigDecimal idAmbienteEnteConvenz = getForm().getDichAbilFields().getNm_ambiente_ente_convenz().parse() != null
                ? getForm().getDichAbilFields().getNm_ambiente_ente_convenz().parse() : BigDecimal.ZERO;
        BigDecimal idEnteConvenz = getForm().getDichAbilFields().getNm_ente_convenz().parse() != null
                ? getForm().getDichAbilFields().getNm_ente_convenz().parse() : BigDecimal.ZERO;
        Set<PairAuth> scopoSet = getScopoEnteConvenzSet();
        if (StringUtils.isNotBlank(tiScopo)) {
            // Se ho scelto come scopo ALL_AMBIENTI
            if (tiScopo.equals(ActionEnums.ScopoDichAbilEnteConvenz.ALL_AMBIENTI.name()) && scopoSet.size() > 0) {
                getMessageBox().addError(
                        "Non è possibile aggiungere la dichiarazione con scopo ALL_AMBIENTI perchè è già presente una dichiarazione");
            } // Se ho scelto come scopo UN_AMBIENTE
            else if (tiScopo.equals(ActionEnums.ScopoDichAbilEnteConvenz.UN_AMBIENTE.name())) {
                if (idAmbienteEnteConvenz.equals(BigDecimal.ZERO)) {
                    // ERRORE
                    getMessageBox().addError("Non è stato inserito l'ambiente");
                } else if (scopoSet.contains(new PairAuth(ActionEnums.ScopoDichAbilEnteConvenz.ALL_AMBIENTI.name(),
                        new PairAuth(BigDecimal.ZERO, BigDecimal.ZERO)))) {
                    getMessageBox().addError(
                            "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo ALL_AMBIENTI");
                } else if (scopoSet.contains(new PairAuth(ActionEnums.ScopoDichAbilEnteConvenz.UN_AMBIENTE.name(),
                        new PairAuth(idAmbienteEnteConvenz, BigDecimal.ZERO)))) {
                    getMessageBox().addError(
                            "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo UN_AMBIENTE relativa allo stesso ambiente");
                } else if (entiConvenzionatiEjb.checkDichAutorEnteConvenzSetForChilds(idAmbienteEnteConvenz,
                        scopoSet)) {
                    getMessageBox().addError(
                            "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo UN_ENTE relativa ad un ente 'figlio' dell'ambiente scelto");
                }
            } else if (tiScopo.equals(ActionEnums.ScopoDichAbilEnteConvenz.UN_ENTE.name())) {
                if (idEnteConvenz.equals(BigDecimal.ZERO)) {
                    // ERRORE
                    getMessageBox().addError("Non sono stati inseriti l'ambiente e/o l'ente");
                } else if (scopoSet.contains(new PairAuth(ActionEnums.ScopoDichAbilEnteConvenz.ALL_AMBIENTI.name(),
                        new PairAuth(BigDecimal.ZERO, BigDecimal.ZERO)))) {
                    getMessageBox().addError(
                            "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo ALL_AMBIENTI");
                } else if (scopoSet.contains(new PairAuth(ActionEnums.ScopoDichAbilEnteConvenz.UN_AMBIENTE.name(),
                        new PairAuth(entiConvenzionatiEjb.getOrgAmbienteEnteConvenzByEnteConvenz(idEnteConvenz)
                                .getIdAmbienteEnteConvenz(), BigDecimal.ZERO)))) {
                    getMessageBox().addError(
                            "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo UN_AMBIENTE relativa ad un ambiente padre dell'ente scelto");
                } else if (scopoSet
                        .contains(new PairAuth(ActionEnums.ScopoDichAbilEnteConvenz.UN_ENTE.name(),
                                new PairAuth(idAmbienteEnteConvenz, idEnteConvenz)))
                        || scopoSet.contains(new PairAuth(ActionEnums.ScopoDichAbilEnteConvenz.UN_ENTE.name(),
                                new PairAuth(BigDecimal.ZERO, idEnteConvenz)))) {
                    getMessageBox().addError(
                            "Non è possibile aggiungere la dichiarazione perchè è già presente una dichiarazione con scopo UN_ENTE relativa allo stesso ente");
                }
            }
        } else {
            // ERRORE
            getMessageBox().addError("Non è stato inserito lo scopo");
        }

        if (!getMessageBox().hasError()) {
            // Inserisce la dichiarazione nel set per i controlli di coerenza...
            scopoSet.add(new PairAuth(tiScopo, new PairAuth(idAmbienteEnteConvenz, idEnteConvenz)));
            getSession().setAttribute("scopoEnteConvenzSet", scopoSet);
            // ... e nella tabella ambienti/enti convenzionati
            UsrDichAbilEnteConvenzRowBean dichAbilEnteConvenzRowBean = new UsrDichAbilEnteConvenzRowBean();
            dichAbilEnteConvenzRowBean.setTiScopoDichAbilEnte(tiScopo);
            dichAbilEnteConvenzRowBean.setIdAmbienteEnteConvenz(idAmbienteEnteConvenz);
            dichAbilEnteConvenzRowBean.setString("nm_ambiente_ente_convenz",
                    getForm().getDichAbilFields().getNm_ambiente_ente_convenz().getDecodedValue());
            dichAbilEnteConvenzRowBean.setIdEnteConvenz(idEnteConvenz);
            dichAbilEnteConvenzRowBean.setString("nm_ente_convenz",
                    getForm().getDichAbilFields().getNm_ente_convenz().getDecodedValue());
            if (getSession().getAttribute("datiAggiuntivi") != null) {
                Map<BigDecimal, Object[]> datiAggiuntivi = (Map<BigDecimal, Object[]>) getSession()
                        .getAttribute("datiAggiuntivi");
                Object[] o = null;
                if (idEnteConvenz != null && idEnteConvenz.compareTo(BigDecimal.ZERO) != 0) {
                    o = datiAggiuntivi.get(idEnteConvenz);
                } else if (idAmbienteEnteConvenz != null && idAmbienteEnteConvenz.compareTo(BigDecimal.ZERO) != 0) {
                    o = datiAggiuntivi.get(idAmbienteEnteConvenz);
                }
                if (o != null) {
                    dichAbilEnteConvenzRowBean.setString("ds_causale_dich", (String) o[0]);
                    if (o.length > 1) {
                        dichAbilEnteConvenzRowBean.setBigDecimal("id_appart_colleg_enti", (BigDecimal) o[1]);
                    }
                }
            }

            getForm().getDichAbilEnteConvenzList().getTable().add(dichAbilEnteConvenzRowBean);
            getForm().getDichAbilEnteConvenzList().getTable().sort();
            //
            resetDichAbilFields();
        }
    }

    @Override
    public JSONObject triggerDichAbilFieldsTi_scopo_dich_abil_organizOnTrigger() throws EMFError {
        getForm().getDichAbilFields().getTi_scopo_dich_abil_organiz().post(getRequest());
        getForm().getDichAbilFields().getNm_applic().post(getRequest());
        String scopo = getForm().getDichAbilFields().getTi_scopo_dich_abil_organiz().parse();
        BigDecimal idEnte = getForm().getDettaglioUtente().getId_ente_siam_appart().parse();
        String tipoEnte = amministrazioneUtentiEjb.getTipoEnteConvenzNonConvenz(idEnte);
        BigDecimal idApplic = getForm().getDichAbilFields().getNm_applic().parse();
        String flAbilOrganizAutom = getForm().getDettaglioUtente().getFl_abil_organiz_autom().parse();
        String flAbilOrganizEntiAutom = getForm().getDettaglioUtente().getFl_abil_fornit_autom().parse();
        BigDecimal idRichGestUser = getForm().getRichiestaWizard().getId_rich_gest_user().parse();
        if (scopo != null) {
            if (scopo.equals(ActionEnums.ScopoDichAbilOrganiz.ALL_ORG_CHILD.name())) {

                BaseTable tb = amministrazioneUtentiEjb.getUsrVTreeOrganizIamAllOrgChildTableBean(
                        getUser().getIdUtente(), idApplic, tipoEnte, idEnte, flAbilOrganizAutom, flAbilOrganizEntiAutom,
                        idRichGestUser);
                DecodeMap mappa = new DecodeMap();
                mappa.populatedMap(tb, "id_organiz_iam", "dl_composito_organiz");
                getForm().getDichAbilFields().getDl_composito_organiz().setDecodeMap(mappa);

                // Tengo in sessione una mappa con i dati "aggiuntivi"
                Map<BigDecimal, Object[]> datiAggiuntivi = new HashMap<>();
                for (BaseRow rb : tb) {
                    Object[] o = new Object[3];
                    o[0] = rb.getString("tipoEnte");
                    o[1] = rb.getString("ds_causale_dich");
                    o[2] = rb.getBigDecimal("id_ente");
                    datiAggiuntivi.put(rb.getBigDecimal("id_organiz_iam"), o);
                }
                if (datiAggiuntivi.size() > 0) {
                    getSession().setAttribute("datiAggiuntivi", datiAggiuntivi);
                }

            } else if (scopo.equals(ActionEnums.ScopoDichAbilOrganiz.ORG_DEFAULT.name())
                    || scopo.equals(ActionEnums.ScopoDichAbilOrganiz.UNA_ORG.name())) {

                BaseTable tb = amministrazioneUtentiEjb.getUsrVTreeOrganizIamUnaOrgTableBean(getUser().getIdUtente(),
                        idApplic, tipoEnte, idEnte, flAbilOrganizAutom, flAbilOrganizEntiAutom, idRichGestUser);
                DecodeMap mappa = new DecodeMap();
                mappa.populatedMap(tb, "id_organiz_iam", "dl_composito_organiz");
                getForm().getDichAbilFields().getDl_composito_organiz().setDecodeMap(mappa);

                // Tengo in sessione una mappa con i dati "aggiuntivi"
                Map<BigDecimal, Object[]> datiAggiuntivi = new HashMap<>();
                for (BaseRow rb : tb) {
                    Object[] o = new Object[3];
                    o[0] = rb.getString("tipoEnte");
                    o[1] = rb.getString("ds_causale_dich");
                    o[2] = rb.getBigDecimal("id_ente");
                    datiAggiuntivi.put(rb.getBigDecimal("id_organiz_iam"), o);
                }
                if (datiAggiuntivi.size() > 0) {
                    getSession().setAttribute("datiAggiuntivi", datiAggiuntivi);
                }

            } else {
                getForm().getDichAbilFields().getDl_composito_organiz().setDecodeMap(new DecodeMap());
            }
            return getForm().getDichAbilFields().asJSON();
        }
        return getForm().getDichAbilFields().asJSON();
    }

    @Override
    public void startReplicaUtenti() throws EMFError {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JSONObject triggerDichAbilFieldsDl_composito_organizOnTrigger() throws EMFError {
        getForm().getDichAbilFields().post(getRequest());
        String nmClasseTipoDato = getForm().getDichAbilFields().getNm_classe_tipo_dato().getDecodedValue();
        String scopo = getForm().getDichAbilFields().getTi_scopo_dich_abil_dati().parse();
        BigDecimal idOrganizIam = getForm().getDichAbilFields().getDl_composito_organiz().parse();
        BigDecimal idApplic = getForm().getDichAbilFields().getNm_applic().parse();
        if (nmClasseTipoDato != null && idOrganizIam != null && scopo != null) {
            if (scopo.equals(ActionEnums.ScopoDichAbilDato.UN_TIPO_DATO.name())) {
                UsrVAbilDatiTableBean usrTipoDatoIam = amministrazioneUtentiEjb
                        .getUsrVAbilDatiTableBean(getUser().getIdUtente(), nmClasseTipoDato, idApplic, idOrganizIam);
                DecodeMap mappa = new DecodeMap();
                mappa.populatedMap(usrTipoDatoIam, "id_tipo_dato_iam", "nm_tipo_dato");
                getForm().getDichAbilFields().getNm_tipo_dato().setDecodeMap(mappa);
            }
        } else {
            getForm().getDichAbilFields().getNm_tipo_dato().setValue(null);
        }
        return getForm().getDichAbilFields().asJSON();
    }

    @Override
    public void updateDettaglioUtente() throws EMFError {
        updateListaUtenti();
    }

    /**
     * Metodo utilizzato per caricare il dettaglio della lista utenti in modifica modifica lo stato dei campi in status
     * update e edit mode
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public void updateListaUtenti() throws EMFError {
        /* Salvo l'informazione del fatto che sono in UPDATE */
        getForm().getListaUtenti().setStatus(Status.update);
        getForm().getDettaglioUtente().setEditMode();
        getForm().getDettaglioUtente().setStatus(Status.update);
        getForm().getDettaglioUtente().getNm_userid().setViewMode();
        getForm().getDettaglioUtente().getFl_attivo().setViewMode();
        getForm().getDettaglioUtente().getQualifica_user().setViewMode();

        getForm().getDettaglioUtente().getDuplicaUtente().setViewMode();
        getForm().getDettaglioUtente().getNuovaPassword().setViewMode();
        // Imposto i campi delle liste come eliminabili
        getForm().getIndIpList().setUserOperations(false, false, false, true);
        getForm().getApplicazioniList().setUserOperations(false, false, false, true);
        getForm().getRuoliDefaultList().setUserOperations(false, false, false, true);
        getForm().getDichAbilOrgList().setUserOperations(false, false, false, true);
        getForm().getDichAbilTipiDatoList().setUserOperations(false, false, false, true);
        getForm().getDichAbilEnteConvenzList().setUserOperations(false, false, false, true);

        if (getRequest().getAttribute("fromRichiesta") == null) {
            getForm().getInserimentoWizard().getPassoRichiesta().setHidden(false);
            // Passo richiesta in caso vada gestito
            getForm().getRichiestaWizard().clear();
            getForm().getRichiestaWizard().getId_ambiente_ente_convenz_rich()
                    .setDecodeMap(DecodeMap.Factory.newInstance(
                            entiConvenzionatiEjb
                                    .getUsrVAbilAmbEnteConvenzTableBean(BigDecimal.valueOf(getUser().getIdUtente())),
                            "id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));
            /* 17504 - Codice commentato inizialmente da lasciare */
            UsrVAbilOrganizTableBean usrOrgIammeTableBean = amministrazioneUtentiEjb
                    .getOrganizAbilitate(new BigDecimal(getUser().getIdUtente()));
            DecodeMap usrOrganizIammeMap = DecodeMap.Factory.newInstance(usrOrgIammeTableBean, "id_organiz_iam",
                    "dl_composito_organiz");
            getForm().getRichiestaWizard().getId_organiz_iam_rich().setDecodeMap(usrOrganizIammeMap);

            getForm().getRichiestaWizard().getId_ente_convenz_rich()
                    .setDecodeMap(DecodeMap.Factory.newInstance(
                            entiConvenzionatiEjb.getOrgVRicEnteNonConvenzAbilTableBean(
                                    BigDecimal.valueOf(getUser().getIdUtente()), null),
                            "id_ente_siam", "nm_ente_siam"));
            getForm().getRichiestaWizard().getCd_registro_rich_gest_user().setDecodeMap(new DecodeMap());
        } else {
            String oldTipoUser = getForm().getDettaglioUtente().getTipo_user().parse();
            if (oldTipoUser.equals(ApplEnum.TipoUser.NON_DI_SISTEMA.name())) {
                // MAC #23065
                getForm().getDettaglioUtente().getTipo_user().setDecodeMap(
                        ComboGetter.getMappaSortedGenericEnum("tipo_user", ApplEnum.TipoUser.getComboTipiTotali()));
            } else if (oldTipoUser.equals(ApplEnum.TipoUser.AUTOMA)
                    || (oldTipoUser.equals(ApplEnum.TipoUser.PERSONA_FISICA.name()))) {
                getForm().getDettaglioUtente().getTipo_user().setDecodeMap(
                        ComboGetter.getMappaSortedGenericEnum("tipo_user", ApplEnum.TipoUser.getComboTipiPerRuoli()));
                getForm().getDettaglioUtente().getTipo_user().setValue(oldTipoUser);
            }
            UsrUserRowBean utente = amministrazioneUtentiEjb
                    .getUserRowBean(getForm().getDettaglioUtente().getId_user_iam().parse());
            OrgEnteSiamRowBean ente;
            try {
                ente = entiConvenzionatiEjb.getOrgEnteConvenzRowBean(utente.getIdEnteSiam());
                setFlagUtente(ente.getTiEnte(), ente.getTiEnteNonConvenz(),
                        getForm().getDettaglioUtente().getTipo_user().parse());
            } catch (ParerUserError ex) {
                getMessageBox().addError("Errore durante il popolamento dei flag del dettaglio utente");
            }
        }

        if (getLastPublisher().equals(Application.Publisher.LISTA_RUOLI_ORGANIZZAZIONE)) {
            getSession().setAttribute("fromRuoliSpecifici", true);
        } else {
            getSession().removeAttribute("fromRuoliSpecifici");
        }

        // manageAmbienteEnteAppartenenza();
    }

    /**
     * Esegue l'eliminazione dell'utente selezionato dalla lista degli utenti
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public void deleteListaUtenti() throws EMFError {
        UsrVLisUserRowBean user = (UsrVLisUserRowBean) getForm().getListaUtenti().getTable().getCurrentRow();
        int riga = getForm().getListaUtenti().getTable().getCurrentRowIndex();
        try {
            /*
             * Codice aggiuntivo per il logging...
             */
            LogParam param = SpagoliteLogUtil.getLogParam(null, getUser().getUsername(),
                    SpagoliteLogUtil.getPageName(this));
            if (param.getNomePagina().equals(Application.Publisher.RICERCA_UTENTI)) {
                param.setNomeAzione(
                        SpagoliteLogUtil.getDetailActionNameDelete(this.getForm(), getForm().getListaUtenti()));
            } else {
                param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
            }
            param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
            if (user.getTipoUser().equals(ApplEnum.TipoUser.PERSONA_FISICA.name())
                    || user.getTipoUser().equals(ApplEnum.TipoUser.AUTOMA.name())) {
                // || user.getTipoUser().equals(ApplEnum.TipoUser.TEAM.name())) {
                amministrazioneUtentiEjb.cessaUtente(param, user.getIdUserIam());
            } else {
                auth.deleteUsrUser(param, user.getIdUserIam());
                getForm().getListaUtenti().getTable().remove(riga);
            }
            getForm().getDettaglioUtente().getFl_attivo().setValue("0");
            getMessageBox().addInfo("Utente eliminato con successo");
        } catch (ParerUserError e) {
            getMessageBox().addError(e.getDescription());
        }
        if (!getMessageBox().hasError() && getLastPublisher().equals(Application.Publisher.DETTAGLIO_UTENTE)) {
            goBack();
        } else {
            forwardToPublisher(getLastPublisher());
        }
    }

    @Override
    public void salva_password() throws EMFError {
        if (getForm().getEditPasswordUtente().postAndValidate(getRequest(), getMessageBox())) {
            String psw = getForm().getEditPasswordUtente().getCd_psw().getValue();
            String pswRep = getForm().getEditPasswordUtente().getCd_psw_repeated().getValue();
            if (psw.equals(pswRep)) {
                if (psw.matches(PASSWORD_REGEX)) {
                    BigDecimal idUser = ((UsrVLisUserRowBean) getForm().getListaUtenti().getTable().getCurrentRow())
                            .getIdUserIam();
                    String nmUserid = ((UsrVLisUserRowBean) getForm().getListaUtenti().getTable().getCurrentRow())
                            .getNmUserid();

                    try {
                        /*
                         * Codice aggiuntivo per il logging...
                         */
                        class Local {
                        }
                        ;
                        String nomeMetodo = Local.class.getEnclosingMethod().getName();
                        LogParam param = SpagoliteLogUtil.getLogParam(null, getUser().getUsername(),
                                SpagoliteLogUtil.getPageName(this), SpagoliteLogUtil.getButtonActionName(this.getForm(),
                                        this.getForm().getEditPasswordUtente(), nomeMetodo));
                        param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
                        amministrazioneUtentiEjb.savePassword(param, idUser, pswRep);

                        getMessageBox().addInfo("Password modificata con successo");
                    } catch (ParerUserError ex) {
                        getMessageBox().addError(ex.getDescription());
                    } catch (Exception e) {
                        getMessageBox().addError("Errore durante il salvataggio della password", e);
                    }
                } else {
                    getMessageBox().addError("La password non rispetta i requisiti di sicurezza");
                }
            } else {
                getMessageBox().addError("I campi password e conferma password non coincidono");
            }
        }
        if (getMessageBox().hasError()) {
            forwardToPublisher(getLastPublisher());
        } else {
            goBack();
        }
    }

    @Override
    public void nuovaPassword() throws EMFError {
        getForm().getEditPasswordUtente().clear();
        getForm().getEditPasswordUtente().setEditMode();
        getForm().getEditPasswordUtente().getSalva_password().setEditMode();
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_INSERISCI_PASSWORD);
    }

    @Override
    public void aggiungiRuoloOrg() throws EMFError {
        getForm().getGestioneRuoliOrganizzazione().post(getRequest());
        BigDecimal ruolo = getForm().getGestioneRuoliOrganizzazione().getNm_ruolo().parse();
        BigDecimal idOrganizIamRuolo = getForm().getGestioneRuoliOrganizzazione().getDl_composito_organiz_ruolo()
                .parse();
        String dlCompositoOrganizRuolo = getForm().getGestioneRuoliOrganizzazione().getDl_composito_organiz_ruolo()
                .getDecodedValue();
        String tiScopoRuolo = getForm().getGestioneRuoliOrganizzazione().getTi_scopo_ruolo().parse();
        if (ruolo != null && idOrganizIamRuolo != null && tiScopoRuolo != null) {
            if (getOrgRolesSet().contains(ruolo)) {
                /* Ruolo già presente */
                getMessageBox().addError("Ruolo già presente nella lista");
            } else {
                /* Creo la nuova riga */
                Set<BigDecimal> orgRolesSet = getOrgRolesSet();
                orgRolesSet.add(ruolo);
                getSession().setAttribute("orgRolesSet", orgRolesSet);
                PrfRuoloRowBean row = amministrazioneUtentiEjb.getPrfRuoloById(ruolo);
                row.setBigDecimal("id_dich_abil_organiz",
                        getForm().getGestioneRuoliOrganizzazione().getId_dich_abil_organiz().parse());
                row.setString("ti_scopo_ruolo", tiScopoRuolo);
                row.setBigDecimal("id_organiz_iam_ruolo", idOrganizIamRuolo);
                row.setString("dl_composito_organiz", dlCompositoOrganizRuolo);
                getForm().getRuoliList().getTable().add(row);
                getForm().getRuoliList().getTable().addSortingRule(PrfRuoloTableDescriptor.COL_NM_RUOLO,
                        SortingRule.ASC);
                getForm().getRuoliList().getTable().sort();
            }
        } else {
            getMessageBox().addError("Selezionare scopo, organizzazione e ruolo");
        }
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE_WIZARD);
    }

    @Override
    public JSONObject triggerGestioneRuoliOrganizzazioneNm_ruoloOnTrigger() throws EMFError {
        getForm().getGestioneRuoliOrganizzazione().post(getRequest());
        BigDecimal ruolo = getForm().getGestioneRuoliOrganizzazione().getNm_ruolo().parse();
        if (ruolo != null) {
            PrfRuoloRowBean prfRuoloById = amministrazioneUtentiEjb.getPrfRuoloById(ruolo);
            getForm().getGestioneRuoliOrganizzazione().getDs_ruolo().setValue(prfRuoloById.getDsRuolo());
        } else {
            getForm().getGestioneRuoliOrganizzazione().getDs_ruolo().setValue(null);
        }
        return getForm().getGestioneRuoliOrganizzazione().asJSON();
    }

    @Override
    public JSONObject triggerGestioneRuoliOrganizzazioneTi_scopo_ruoloOnTrigger() throws EMFError {
        getForm().getGestioneRuoliOrganizzazione().post(getRequest());
        String scopo = getForm().getGestioneRuoliOrganizzazione().getTi_scopo_ruolo().parse();
        BigDecimal idApplic = getForm().getDichAbilOrgList().getTable().getCurrentRow().getBigDecimal("id_applic");
        String dlPathIdOrganizIam = getForm().getDichAbilOrgList().getTable().getCurrentRow()
                .getString("dl_path_id_organiz_iam");
        if (scopo != null) {
            if (scopo.equals(ActionEnums.ScopoDichAbilOrganiz.ALL_ORG_CHILD.name())) {
                UsrVAbilOrganizNolastLivTableBean tb = amministrazioneUtentiEjb
                        .getUsrVAbilOrganizNolastLivTableBean(getUser().getIdUtente(), idApplic, dlPathIdOrganizIam);
                DecodeMap mappa = new DecodeMap();
                mappa.populatedMap(tb, "id_organiz_iam", "dl_composito_organiz");
                getForm().getGestioneRuoliOrganizzazione().getDl_composito_organiz_ruolo().setDecodeMap(mappa);
            } else if (scopo.equals(ActionEnums.ScopoDichAbilOrganiz.UNA_ORG.name())) {
                UsrVAbilOrganizTableBean tb = amministrazioneUtentiEjb
                        .getOrganizPerRuoliSpecificiUnaOrg(getUser().getIdUtente(), idApplic, dlPathIdOrganizIam);
                DecodeMap mappa = new DecodeMap();
                mappa.populatedMap(tb, "id_organiz_iam", "dl_composito_organiz");
                getForm().getGestioneRuoliOrganizzazione().getDl_composito_organiz_ruolo().setDecodeMap(mappa);
            } else {
                getForm().getGestioneRuoliOrganizzazione().getDl_composito_organiz_ruolo()
                        .setDecodeMap(new DecodeMap());
            }
            return getForm().getGestioneRuoliOrganizzazione().asJSON();
        }
        return getForm().getGestioneRuoliOrganizzazione().asJSON();
    }

    @Override
    public JSONObject triggerDettaglioUtenteFl_contr_ipOnTrigger() throws EMFError {
        getForm().getDettaglioUtente().getFl_contr_ip().post(getRequest());
        if (getForm().getDettaglioUtente().getFl_contr_ip().getValue().equals("0")) {
            UsrIndIpUserTableBean indIpTB = (UsrIndIpUserTableBean) getForm().getIndIpList().getTable();
            Set<BigDecimal> indIpDeleteList = getIndIpDeleteList();
            for (int i = 0; i < indIpTB.size(); i++) {
                indIpDeleteList.add(indIpTB.getRow(i).getIdIndIpUser());
                getSession().setAttribute("indIpDeleteList", indIpDeleteList);
            }
            // Rimuovo i record a video
            getForm().getIndIpList().getTable().removeAll();
        }
        return getForm().getIndIpList().asJSON();
    }

    @Secure(action = "Menu.Monitoraggio.VisualizzaSchedulazioniReplicheUtenti")
    public void schedulazioniReplicaUtentiPage() throws EMFError {
        getUser().getMenu().reset();
        getUser().getMenu().select("Menu.Monitoraggio.VisualizzaSchedulazioniReplicheUtenti");
        resetSchedulazioniReplicaUtentiPage();
        forwardToPublisher(Application.Publisher.SCHEDULAZIONI_REPLICA_UTENTI_LIST);
    }

    @Secure(action = "Menu.Monitoraggio.VisualizzaReplicheUtenti")
    public void replicaUtentiPage() throws EMFError {
        getUser().getMenu().reset();
        getUser().getMenu().select("Menu.Monitoraggio.VisualizzaReplicheUtenti");
        resetReplicaUtentiPage();
        forwardToPublisher(Application.Publisher.REPLICA_UTENTI_LIST);
    }

    @Override
    public void ricercaRepliche() throws EMFError {
        getForm().getFiltriReplica().getRicercaRepliche().setDisableHourGlass(true);
        FiltriReplica filtri = getForm().getFiltriReplica();
        filtri.post(getRequest());
        if (filtri.validate(getMessageBox())) {
            AmministrazioneUtentiValidator validator = new AmministrazioneUtentiValidator(getMessageBox());
            Date dataValidata = validator.validaData(filtri.getDt_log_user_da_replic().parse(),
                    filtri.getOre_dt_log_user_da_replic().parse(), filtri.getMinuti_dt_log_user_da_replic().parse(),
                    "Da data registrazione log");
            if (!getMessageBox().hasError()) {
                UsrVLisUserReplicTableBean userReplicaTB = amministrazioneUtentiEjb
                        .getUsrVLisUserReplicTableBean(filtri, dataValidata, false);
                getForm().getReplicaUtentiList().setTable(userReplicaTB);
                getForm().getReplicaUtentiList().getTable().setPageSize(10);
                getForm().getReplicaUtentiList().getTable().first();
            }
        }
        forwardToPublisher(Application.Publisher.REPLICA_UTENTI_LIST);
    }

    /**
     * Bottone di Dettaglio Utente per visualizzare la lista delle repliche utente
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public void visualizzaReplicheUtenti() throws EMFError {
        getForm().getFiltriReplica().clear();
        /* Setto il filtro user-id */
        String nmUserid = getForm().getDettaglioUtente().getNm_userid().getValue();
        getForm().getFiltriReplica().getNm_userid().setValue(nmUserid);

        // Setto preimpostata il filtro della data con data odierna
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        SimpleDateFormat df = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH);
        getForm().getFiltriReplica().getDt_log_user_da_replic().setValue(df.format(cal.getTime()));

        /* Popolo le combo */
        getForm().getFiltriReplica().getNm_applic().setDecodeMap(getMappaApplic());
        getForm().getFiltriReplica().getTi_oper_replic().setDecodeMap(ComboGetter.getMappaTiOperReplic());
        getForm().getFiltriReplica().getTi_stato_replic().setDecodeMap(ComboGetter.getMappaTiStatoReplic());

        getForm().getFiltriReplica().setEditMode();
        UsrVLisUserReplicTableBean userReplicaTB = amministrazioneUtentiEjb
                .getUsrVLisUserReplicTableBean(getForm().getFiltriReplica(), cal.getTime(), true);
        getForm().getReplicaUtentiList().setTable(userReplicaTB);
        getForm().getReplicaUtentiList().getTable().setPageSize(10);
        getForm().getReplicaUtentiList().getTable().first();
        forwardToPublisher(Application.Publisher.REPLICA_UTENTI_LIST);
    }

    @Override
    public void visualizzaReplicheUtentiSched() throws EMFError {
        getForm().getFiltriReplica().clear();
        /* Popolo le combo */
        getForm().getFiltriReplica().getNm_applic().setDecodeMap(getMappaApplic());
        /* Personalizzo questa combo con solo 3 valori disponibili */
        getForm().getFiltriReplica().getTi_oper_replic().setDecodeMap(ComboGetter.getMappaTiStatoReplicForSched());
        getForm().getFiltriReplica().getTi_stato_replic().setDecodeMap(ComboGetter.getMappaTiStatoReplic());

        getForm().getFiltriReplica().setEditMode();

        forwardToPublisher(Application.Publisher.REPLICA_UTENTI_LIST);
    }

    @Override
    public void pulisciRepliche() throws EMFError {
        resetReplicaUtentiPage();
        forwardToPublisher(Application.Publisher.REPLICA_UTENTI_LIST);
    }

    @Override
    public void attivaUtente() throws EMFError {
        try {
            /*
             * Codice aggiuntivo per il logging...
             */
            class Local {
            }
            ;
            String nomeMetodo = Local.class.getEnclosingMethod().getName();
            LogParam param = SpagoliteLogUtil.getLogParam(null, getUser().getUsername(),
                    SpagoliteLogUtil.getPageName(this), SpagoliteLogUtil.getButtonActionName(this.getForm(),
                            this.getForm().getDettaglioUtente(), nomeMetodo));
            param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
            AmministrazioneUtentiEjb.KeycloakMessageSent mex = amministrazioneUtentiEjb.attivaUtente(param,
                    getForm().getDettaglioUtente().getId_user_iam().parse());
            if (mex.getTipoMessaggio().equals(AmministrazioneUtentiEjb.KeycloakMessageSent.TIPO_INFO)) {
                getMessageBox().addInfo(mex.getMessaggio());
                getForm().getDettaglioUtente().getFl_attivo().setValue("1");
                setVisibilityAbilitaDisabilitaUtente(getForm().getDettaglioUtente().getId_user_iam().parse());
            } else {
                getMessageBox().addWarning(mex.getMessaggio());
            }
            getMessageBox().setViewMode(ViewMode.plain);
        } catch (ParerUserError e) {
            getMessageBox().addError(e.getDescription());
        }
        auth.registraUtentiDaReplic(getForm().getDettaglioUtente().getId_user_iam().parse(),
                (UsrUsoUserApplicTableBean) getForm().getApplicazioniList().getTable(), ApplEnum.TiOperReplic.MOD);
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE);
    }

    @Override
    public void disattivaUtente() throws EMFError {
        try {
            /*
             * Codice aggiuntivo per il logging...
             */
            class Local {
            }
            ;
            String nomeMetodo = Local.class.getEnclosingMethod().getName();
            LogParam param = SpagoliteLogUtil.getLogParam(null, getUser().getUsername(),
                    SpagoliteLogUtil.getPageName(this), SpagoliteLogUtil.getButtonActionName(this.getForm(),
                            this.getForm().getDettaglioUtente(), nomeMetodo));
            param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
            amministrazioneUtentiEjb.disattivaUtente(param, getForm().getDettaglioUtente().getId_user_iam().parse());
            getForm().getDettaglioUtente().getFl_attivo().setValue("0");
            setVisibilityAbilitaDisabilitaUtente(getForm().getDettaglioUtente().getId_user_iam().parse());
            getMessageBox().addInfo("Utente disattivato con successo.");
            getMessageBox().setViewMode(ViewMode.plain);
        } catch (ParerUserError e) {
            getMessageBox().addError(e.getDescription());
        }
        auth.registraUtentiDaReplic(getForm().getDettaglioUtente().getId_user_iam().parse(),
                (UsrUsoUserApplicTableBean) getForm().getApplicazioniList().getTable(), ApplEnum.TiOperReplic.MOD);
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE);
    }

    @Secure(action = "detail/AmministrazioneUtentiForm#SchedulazioniReplicaUtentiList/cleanAsyncLocked")
    public void cleanAsyncLocked() throws EMFError {
        setTableName(getForm().getSchedulazioniReplicaUtentiList().getName());
        setRiga(getRequest().getParameter("riga"));

        try {
            UsrVLisSchedRowBean row = (UsrVLisSchedRowBean) getForm().getSchedulazioniReplicaUtentiList().getTable()
                    .getRow(Integer.parseInt(getRiga()));
            if (row.getTiBlocco() != null) {
                if (row.getTiBlocco().equals("SENZA_FINE_SCHEDULAZIONE")) {
                    int replicheAggiornate = amministrazioneUtentiEjb.aggiornaReplicheInCorso(row.getIdLogJob(), true);
                    getMessageBox().addInfo("Aggiornate " + replicheAggiornate
                            + " repliche per essere selezionate al prossimo avvio di replica utenti");
                } else if (row.getTiBlocco().equals("ERRORE")) {
                    int replicheAggiornate = amministrazioneUtentiEjb.aggiornaReplicheInCorso(row.getIdLogJob(), false);
                    getMessageBox().addInfo("Aggiornate " + replicheAggiornate
                            + " repliche per essere selezionate al prossimo avvio di replica utenti");
                }
                row.setString("fl_async_locked", "0");
                row.setString("cleanAsyncLocked", null);
            }
        } catch (Exception ex) {
            log.error("Errore inaspettato in fase di aggiornamento repliche in corso :"
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            getMessageBox().addError("Errore inaspettato nell'aggiornamento dei record di replica");
        }
        forwardToPublisher(getLastPublisher());
    }

    @Override
    public void logEventi() throws EMFError {
        GestioneLogEventiForm form = new GestioneLogEventiForm();
        form.getOggettoDetail().getNmApp().setValue(paramHelper.getParamApplicApplicationName());
        form.getOggettoDetail().getNm_tipo_oggetto().setValue(SacerLogConstants.TIPO_OGGETTO_UTENTE);
        form.getOggettoDetail().getIdOggetto().setValue(getForm().getDettaglioUtente().getId_user_iam().getValue());
        redirectToAction(it.eng.parer.sacerlog.slite.gen.Application.Actions.GESTIONE_LOG_EVENTI,
                "?operation=inizializzaLogEventi", form);
    }

    @Override
    public void addUtentiArchivistiToSistemaVersante() throws EMFError {
        boolean forceGoBack = false;
        if (!getForm().getUtentiArchivistiList().getTable().isEmpty()) {
            BigDecimal idSistemaVersante = getForm().getUtentiArchivistiPerSistemaVersante().getId_sistema_versante()
                    .parse();
            try {
                if (idSistemaVersante != null) {
                    for (BaseRowInterface row : getForm().getUtentiArchivistiList().getTable()) {
                        BigDecimal idUserIam = row.getBigDecimal("id_user_iam");
                        String cognome = row.getString("nm_cognome_user");
                        String nome = row.getString("nm_nome_user");

                        if (!sistemiVersantiEjb.isUtenteArchivistaInSistemaVersante(idSistemaVersante, idUserIam)) {
                            sistemiVersantiEjb.addUtenteArchivistaToSistemaVersante(idSistemaVersante, idUserIam);
                        } else {
                            getMessageBox().addError("L'utente " + nome + " " + cognome
                                    + " \u00E8 gi\u00E0 presente come archivista nel sistema versante ");
                        }
                    }
                    if (!getMessageBox().hasError()) {
                        getMessageBox().addInfo(
                                "Gli utenti selezionati sono stati aggiunti con successo al sistema versante ");
                        getMessageBox().setViewMode(MessageBox.ViewMode.plain);
                        getForm().getUtentiArchivistiList().getTable().clear();
                    }
                } else {
                    getMessageBox()
                            .addError("Errore inaspettato nell'aggiunta di utenti archivisti al sistema versante");
                    forceGoBack = true;
                }
            } catch (ParerUserError ex) {
                getMessageBox().addError(ex.getDescription());
            }
        } else {
            getMessageBox().addError("Selezionare almeno un utente da aggiungere come archivista al sistema versante");
        }
        if (forceGoBack) {
            goBack();
        } else {
            forwardToPublisher(Application.Publisher.RICERCA_UTENTI);
        }
    }

    @Override
    public void selectReferentiDittaProduttriceList() throws EMFError {
        BaseRowInterface row = getForm().getReferentiDittaProduttriceList().getTable().getCurrentRow();
        int index = getForm().getReferentiDittaProduttriceList().getTable().getCurrentRowIndex();
        getForm().getReferentiDittaProduttriceList().getTable().remove(index);
        getForm().getListaUtenti().getTable().addFullIdx(row);
        forwardToPublisher(Application.Publisher.RICERCA_UTENTI);
    }

    @Override
    public void selectListaUtenti() throws EMFError {
        /* Ricavo il record interessato della lista utenti */
        boolean forceGoBack = false;
        BaseRowInterface row = getForm().getListaUtenti().getTable().getCurrentRow();
        int index = getForm().getListaUtenti().getTable().getCurrentRowIndex();
        BigDecimal idUserIam = row.getBigDecimal("id_user_iam");
        String nome = row.getString("nm_nome_user");
        String cognome = row.getString("nm_cognome_user");

        if (!getForm().getUtentiArchivistiToEntiConvenzionatiSection().isHidden()) {
            BigDecimal idEnteConvenz = getForm().getUtentiArchivistiPerEnteConvenzionato().getId_ente_convenz().parse();
            // Non deve mai capitare, ma per sicurezza eseguo il controllo
            if (idEnteConvenz != null) {
                if (!entiConvenzionatiEjb.isUtenteArchivistaInEnteConvenz(idEnteConvenz, idUserIam)) {
                    // Dato che la lista è paginata, devo richiamare il metodo removeFullIdx() al posto di remove()
                    getForm().getListaUtenti().getTable().removeFullIdx(index);
                    /* Aggiungo il record nella lista degli utenti archivisti selezionati */
                    getForm().getUtentiArchivistiList().add(row);
                } else {
                    getMessageBox().addError("L'utente " + nome + " " + cognome
                            + " \u00E8 gi\u00E0 presente come archivista nell'ente convenzionato ");
                }
            } else {
                getMessageBox()
                        .addError("Errore inaspettato nell'aggiunta di un utente archivista all'ente convenzionato");
                forceGoBack = true;
            }
        } else if (!getForm().getUtentiArchivistiToSistemiVersantiSection().isHidden()) {
            BigDecimal idSistemaVersante = getForm().getUtentiArchivistiPerSistemaVersante().getId_sistema_versante()
                    .parse();
            // Non deve mai capitare, ma per sicurezza eseguo il controllo
            if (idSistemaVersante != null) {
                if (!sistemiVersantiEjb.isUtenteArchivistaInSistemaVersante(idSistemaVersante, idUserIam)) {
                    // Dato che la lista è paginata, devo richiamare il metodo removeFullIdx() al posto di remove()
                    getForm().getListaUtenti().getTable().removeFullIdx(index);
                    /* Aggiungo il record nella lista degli utenti archivisti selezionati */
                    getForm().getUtentiArchivistiList().add(row);
                } else {
                    getMessageBox().addError("L'utente " + nome + " " + cognome
                            + " \u00E8 gi\u00E0 presente come archivista nel sistema versante ");
                }
            } else {
                getMessageBox()
                        .addError("Errore inaspettato nell'aggiunta di un utente archivista nel sistema versante");
                forceGoBack = true;
            }
        } else if (!getForm().getReferentiDittaProduttriceToSistemiVersantiSection().isHidden()) {
            BigDecimal idSistemaVersante = getForm().getReferentiDittaProduttricePerSistemaVersante()
                    .getId_sistema_versante().parse();
            // Non deve mai capitare, ma per sicurezza eseguo il controllo
            if (idSistemaVersante != null) {
                if (!sistemiVersantiEjb.isReferenteDittaProduttriceInSistemaVersante(idSistemaVersante, idUserIam)) {
                    // Dato che la lista è paginata, devo richiamare il metodo removeFullIdx() al posto di remove()
                    getForm().getListaUtenti().getTable().removeFullIdx(index);
                    /* Aggiungo il record nella lista degli utenti archivisti selezionati */
                    getForm().getReferentiDittaProduttriceList().add(row);
                } else {
                    getMessageBox().addError("L'utente " + nome + " " + cognome
                            + " \u00E8 gi\u00E0 presente come referente ditta produttrice nel sistema versante ");
                }
            } else {
                getMessageBox().addError(
                        "Errore inaspettato nell'aggiunta di un referente ditta produttrice nel sistema versante");
                forceGoBack = true;
            }
        } else if (!getForm().getReferentiEnteConvenzionatoSection().isHidden()) {
            BigDecimal idEnteConvenz = getForm().getReferentiPerEnteConvenzionato().getId_ente_convenz().parse();
            // Non deve mai capitare, ma per sicurezza eseguo il controllo
            if (idEnteConvenz != null) {
                if (!entiConvenzionatiEjb.isReferenteEnteInEnteConvenz(idEnteConvenz, idUserIam)) {
                    // Dato che la lista è paginata, devo richiamare il metodo removeFullIdx() al posto di remove()
                    getForm().getListaUtenti().getTable().removeFullIdx(index);
                    /* Aggiungo il record nella lista degli utenti archivisti selezionati */
                    getForm().getReferentiEnteList().add(row);
                } else {
                    getMessageBox().addError("L'utente " + nome + " " + cognome
                            + " \u00E8 gi\u00E0 presente come referente dell'ente convenzionato ");
                }
            } else {
                getMessageBox().addError("Errore inaspettato nell'aggiunta di un referente dell'ente convenzionato");
                forceGoBack = true;
            }
        }

        if (getMessageBox().hasError() && forceGoBack) {
            goBack();
        } else {
            forwardToPublisher(Application.Publisher.RICERCA_UTENTI);
        }
    }

    @Override
    public void addReferentiDittaProduttriceToSistemaVersante() throws EMFError {
        boolean forceGoBack = false;
        if (!getForm().getReferentiDittaProduttriceList().getTable().isEmpty()) {
            BigDecimal idSistemaVersante = getForm().getReferentiDittaProduttricePerSistemaVersante()
                    .getId_sistema_versante().parse();
            try {
                if (idSistemaVersante != null) {
                    for (BaseRowInterface row : getForm().getReferentiDittaProduttriceList().getTable()) {
                        BigDecimal idUserIam = row.getBigDecimal("id_user_iam");
                        String cognome = row.getString("nm_cognome_user");
                        String nome = row.getString("nm_nome_user");

                        if (!sistemiVersantiEjb.isReferenteDittaProduttriceInSistemaVersante(idSistemaVersante,
                                idUserIam)) {
                            sistemiVersantiEjb.addReferenteDittaProduttriceToSistemaVersante(idSistemaVersante,
                                    idUserIam);
                        } else {
                            getMessageBox().addError("L'utente " + nome + " " + cognome
                                    + " \u00E8 gi\u00E0 presente come referente della ditta produttrice nel sistema versante ");
                        }
                    }
                    if (!getMessageBox().hasError()) {
                        getMessageBox().addInfo(
                                "Gli utenti selezionati sono stati aggiunti con successo al sistema versante ");
                        getMessageBox().setViewMode(MessageBox.ViewMode.plain);
                        getForm().getReferentiDittaProduttriceList().getTable().clear();
                    }
                } else {
                    getMessageBox().addError(
                            "Errore inaspettato nell'aggiunta di referenti della ditta produttrice del sistema versante");
                    forceGoBack = true;
                }
            } catch (ParerUserError ex) {
                getMessageBox().addError(ex.getDescription());
            }
        } else {
            getMessageBox().addError(
                    "Selezionare almeno un utente da aggiungere come referente della ditta produttrice del sistema versante");
        }
        if (forceGoBack) {
            goBack();
        } else {
            forwardToPublisher(Application.Publisher.RICERCA_UTENTI);
        }
    }

    @Secure(action = "Menu.AmministrazioneUtenti.GestioneRichieste")
    public void ricercaRichiestePage() throws EMFError {
        getUser().getMenu().reset();
        getUser().getMenu().select("Menu.AmministrazioneUtenti.GestioneRichieste");
        getForm().getFiltriRichieste().reset();
        getForm().getFiltriRichieste().setEditMode();
        getForm().getFiltriRichieste().getId_ambiente_ente_convenz()
                .setDecodeMap(DecodeMap.Factory.newInstance(
                        entiConvenzionatiEjb
                                .getUsrVAbilAmbEnteConvenzTableBean(BigDecimal.valueOf(getUser().getIdUtente())),
                        "id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));
        getForm().getFiltriRichieste().getId_ente_convenz()
                .setDecodeMap(
                        DecodeMap.Factory.newInstance(
                                entiConvenzionatiEjb.getOrgVRicEnteNonConvenzAbilTableBean(
                                        BigDecimal.valueOf(getUser().getIdUtente()), null),
                                "id_ente_siam", "nm_ente_siam"));

        /* 17504 */
        UsrVAbilOrganizTableBean usrOrgIammeTableBean = amministrazioneUtentiEjb
                .getOrganizAbilitate(new BigDecimal(getUser().getIdUtente()));
        DecodeMap usrOrganizIammeMap = DecodeMap.Factory.newInstance(usrOrgIammeTableBean, "id_organiz_iam",
                "dl_composito_organiz");
        getForm().getFiltriRichieste().getId_organiz_iam().setDecodeMap(usrOrganizIammeMap);

        // Visualizza sezioni
        getForm().getIdentificativoUdRichSection().setHidden(false);
        getForm().getFiltriRichieste().getCd_registro_rich_gest_user().setDecodeMap(new DecodeMap());
        getForm().getFiltriRichieste().getTi_stato_rich_gest_user().setDecodeMap(ComboGetter.getTiStatoRichGestUser());
        getForm().getRichiesteList().setTable(null);
        forwardToPublisher(Application.Publisher.RICERCA_RICHIESTE);
    }

    @Secure(action = "Menu.AmministrazioneUtenti.GestioneRichiestaConfigurazioneUtenti")
    public void richiestaConfigurazioneUtentiPage() throws EMFError {
        getUser().getMenu().reset();
        getUser().getMenu().select("Menu.AmministrazioneUtenti.GestioneRichiestaConfigurazioneUtenti");
        // Setto il nome della tabella per quando passerà per insertDettaglio()
        setTableName(getForm().getRichiesteList().getName());
        getRequest().setAttribute("isFromRichiestaConfigurazioneUtenti", true);
        //
        insertDettaglio();
        forwardToPublisher(Application.Publisher.DETTAGLIO_RICHIESTA);
    }

    @Override
    public void ricercaRichieste() throws EMFError {
        if (getForm().getFiltriRichieste().postAndValidate(getRequest(), getMessageBox())) {
            String nmCognomeUserAppRich = getForm().getFiltriRichieste().getNm_cognome_user_app_rich().parse();
            String nmNomeUserAppRich = getForm().getFiltriRichieste().getNm_nome_user_app_rich().parse();
            String nmUseridAppRich = getForm().getFiltriRichieste().getNm_userid_app_rich().parse();
            BigDecimal idAmbienteEnteConvenz = getForm().getFiltriRichieste().getId_ambiente_ente_convenz().parse();
            BigDecimal idEnteConvenz = getForm().getFiltriRichieste().getId_ente_convenz().parse();
            String nmCognomeUserRich = getForm().getFiltriRichieste().getNm_cognome_user_rich().parse();
            String nmNomeUserRich = getForm().getFiltriRichieste().getNm_nome_user_rich().parse();
            String nmUseridRich = getForm().getFiltriRichieste().getNm_userid_rich().parse();
            BigDecimal idOrganizIam = getForm().getFiltriRichieste().getId_organiz_iam().parse();
            String cdRegistroRichGestUser = getForm().getFiltriRichieste().getCd_registro_rich_gest_user().parse();
            BigDecimal aaRichGestUser = getForm().getFiltriRichieste().getAa_rich_gest_user().parse();
            String cdKeyRichGestUser = getForm().getFiltriRichieste().getCd_key_rich_gest_user().parse();
            String cdRichGestUser = getForm().getFiltriRichieste().getCd_rich_gest_user().parse();
            String tiStatoRichGestUser = getForm().getFiltriRichieste().getTi_stato_rich_gest_user().parse();

            // Controllo che se ho inserito il filtro identificativo richiesta sia inserito anche il filtro dell'ente
            // convenzionato
            if (getForm().getFiltriRichieste().getCd_rich_gest_user().parse() != null
                    && getForm().getFiltriRichieste().getId_ente_convenz().parse() == null) {
                getMessageBox().addError(
                        "Attenzione: è stato valorizzato il filtro identificativo richiesta, è necessario valorizzare anche il filtro relativo all'ente");
            }

            if (!getMessageBox().hasError()) {
                UsrVRicRichiesteTableBean table = amministrazioneUtentiEjb.getUsrVRicRichiesteTableBean(
                        BigDecimal.valueOf(getUser().getIdUtente()), nmCognomeUserAppRich, nmNomeUserAppRich,
                        nmUseridAppRich, idAmbienteEnteConvenz, idEnteConvenz, nmCognomeUserRich, nmNomeUserRich,
                        nmUseridRich, idOrganizIam, cdRegistroRichGestUser, aaRichGestUser, cdKeyRichGestUser,
                        cdRichGestUser, tiStatoRichGestUser);
                getForm().getRichiesteList().setTable(table);
                getForm().getRichiesteList().getTable().setPageSize(10);
                getForm().getRichiesteList().getTable().first();
            }
        }
        forwardToPublisher(getLastPublisher());
    }

    private void initRichiestaDetail() {

        // Combo ambiente ente convenzionato
        UsrVAbilAmbEnteConvenzTableBean abilAmbEnteConvenzTableBean = entiConvenzionatiEjb
                .getUsrVAbilAmbEnteConvenzTableBean(new BigDecimal(getUser().getIdUtente()));
        getForm().getRichiestaDetail().getId_ambiente_ente_convenz().setDecodeMap(DecodeMap.Factory
                .newInstance(abilAmbEnteConvenzTableBean, "id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));

        // Combo tipo richiesta
        getForm().getRichiestaDetail().getTi_rich_gest_user().setDecodeMap(ComboGetter.getMappaTiRichGestUser());
        // Combo dlCompositoOrganiz
        /* 17504 - senza precompilare valore */
        UsrVAbilOrganizTableBean usrOrgIammeTableBean = amministrazioneUtentiEjb
                .getOrganizAbilitate(new BigDecimal(getUser().getIdUtente()));
        DecodeMap usrOrganizIammeMap = DecodeMap.Factory.newInstance(usrOrgIammeTableBean, "id_organiz_iam",
                "dl_composito_organiz");
        getForm().getRichiestaDetail().getId_organiz_iam().setDecodeMap(usrOrganizIammeMap);

        getForm().getRichiestaDetail().getCd_registro_rich_gest_user().setDecodeMap(new DecodeMap());
        // Visualizzo la sezione di "Identificativo unità documentaria richiesta"
        getForm().getIdentificativoUdRichSection().setHidden(false);
    }

    private void initAzioneDetail() throws EMFError {
        BigDecimal idEnteRich = getForm().getRichiestaDetail().getId_ente_rich().parse();
        String tiEnte = amministrazioneUtentiEjb.getTipoEnte(idEnteRich);
        getForm().getAzioneDetail().getTi_azione_rich().setDecodeMap(ComboGetter.getMappaTiAzioneRich(tiEnte));
        getForm().getAzioneDetail().getId_ambiente_ente_user().setDecodeMap(new DecodeMap());
        getForm().getAzioneDetail().getId_ente_user().setDecodeMap(new DecodeMap());
    }

    @Override
    public void deleteRichiesteList() throws EMFError {
        BigDecimal idRichGestUser = ((BaseRowInterface) getForm().getRichiesteList().getTable().getCurrentRow())
                .getBigDecimal("id_rich_gest_user");
        int riga = getForm().getRichiesteList().getTable().getCurrentRowIndex();
        try {
            /*
             * Codice aggiuntivo per il logging...
             */
            LogParam param = SpagoliteLogUtil.getLogParam(paramHelper.getParamApplicApplicationName(),
                    getUser().getUsername(), SpagoliteLogUtil.getPageName(this));
            param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
            if (param.getNomePagina().equalsIgnoreCase(Application.Publisher.DETTAGLIO_RICHIESTA)) {
                param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
            } else {
                param.setNomeAzione(
                        SpagoliteLogUtil.getDetailActionNameDelete(getForm(), getForm().getRichiesteList()));
            }
            amministrazioneUtentiEjb.deleteUsrRichGestUser(param, idRichGestUser);
            getForm().getRichiesteList().getTable().remove(riga);
            getMessageBox().addInfo("Richiesta eliminata con successo");
            getMessageBox().setViewMode(ViewMode.plain);
        } catch (ParerUserError ex) {
            getMessageBox().addError(ex.getDescription());
        }
        if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_RICHIESTA)) {
            if (SessionManager.getExecutionHistory(getSession()).size() > 1) {
                ExecutionHistory ehl = SessionManager.getLastExecutionHistory(getSession());
                if (ehl.getName().contains(Application.Publisher.RICERCA_RICHIESTE)) {
                    goBackTo(Application.Publisher.RICERCA_RICHIESTE);
                } else if (ehl.getName().contains(Application.Publisher.RICERCA_UTENTI)) {
                    goBackTo(Application.Publisher.RICERCA_UTENTI);
                }
            } else {
                ricercaRichiestePage();
            }
        } else {
            forwardToPublisher(getLastPublisher());
        }
    }

    public void aggiungiUtenteRichiedente() {
        // Ricavo l'utente selezionato
        Integer riga = Integer.parseInt(getRequest().getParameter("riga"));
        UsrVLisUserRowBean utenteSelezionato = ((UsrVLisUserTableBean) getForm().getListaUtenti().getTable())
                .getRow(riga);
        getForm().getRichiestaDetail().getId_user_iam_rich().setValue(utenteSelezionato.getIdUserIam().toString());
        getForm().getRichiestaDetail().getNm_userid_codificato().setValue(utenteSelezionato.getNmCognomeUser() + " "
                + utenteSelezionato.getNmNomeUser() + " (" + utenteSelezionato.getNmUserid() + ") ");
        getForm().getRichiestaDetail().getTi_user_rich().setValue(ConstUsrRichGestUser.TiUserRich.UTENTE_CODIF.name());
        goBack();
    }

    @Override
    public void updateRichiesteList() throws EMFError {
        // Controllo se la richiesta è modificabile
        getForm().getRichiesteList().setStatus(Status.update);
        getForm().getRichiestaDetail().setEditMode();
        getForm().getRichiestaDetail().getScaricaFileRichiesta().setHidden(true);
        getForm().getRichiestaDetail().getRichiestaCompletata().setHidden(true);
        getForm().getRichiestaDetail().getLogEventiRichiesta().setHidden(true);
        getForm().getRichiestaDetail().setStatus(Status.update);

        getForm().getRichiestaDetail().getId_ente_rich().setViewMode();
        getForm().getRichiestaDetail().getId_ambiente_ente_convenz().setViewMode();
        if (getForm().getAzioniList().getTable().isEmpty()) {
            getForm().getRichiestaDetail().getId_ente_rich().setEditMode();
            getForm().getRichiestaDetail().getId_ambiente_ente_convenz().setEditMode();
        }

        forwardToPublisher(Application.Publisher.DETTAGLIO_RICHIESTA);
    }

    @Override
    public void selezionaUtenteAzione() throws EMFError {
        getForm().getAzioneDetail().post(getRequest());
        getSession().removeAttribute("flUserAdmin");
        ricercaAllUtentiPage();
    }

    public void aggiungiUtentePerAzione() {
        // Ricavo l'utente selezionato
        Integer riga = Integer.parseInt(getRequest().getParameter("riga"));
        UsrVLisUserRowBean utenteSelezionato = ((UsrVLisUserTableBean) getForm().getListaUtenti().getTable())
                .getRow(riga);
        getForm().getAzioneDetail().getId_user_iam().setValue(utenteSelezionato.getIdUserIam().toString());
        getForm().getAzioneDetail().getNm_userid_codificato().setValue(utenteSelezionato.getNmCognomeUser() + " "
                + utenteSelezionato.getNmNomeUser() + " (" + utenteSelezionato.getNmUserid() + ") ");
        getForm().getAzioneDetail().getTi_appart_user_rich()
                .setValue(ConstUsrAppartUserRich.TiAppartUserRich.UTENTE_CODIFICATO.name());
        goBack();
    }

    @Override
    public void deleteAzioniList() throws EMFError {
        UsrAppartUserRichRowBean appartUserRichRowBean = (UsrAppartUserRichRowBean) getForm().getAzioniList().getTable()
                .getCurrentRow();
        BigDecimal idAppartUserRich = appartUserRichRowBean.getIdAppartUserRich();
        BigDecimal idUserIam = appartUserRichRowBean.getIdUserIam();
        BigDecimal idRichGestUser = appartUserRichRowBean.getIdRichGestUser();
        String tiAzioneRich = appartUserRichRowBean.getTiAzioneRich();
        String flAzioneRichEvasa = appartUserRichRowBean.getFlAzioneRichEvasa();
        if (flAzioneRichEvasa.equals("1")) {
            getMessageBox().addError("Eliminazione dell'azione non consentita in quanto la stessa ha stato EVASA");
            forwardToPublisher(getLastPublisher());
        }

        try {
            if (!getMessageBox().hasError()) {
                /*
                 * Codice aggiuntivo per il logging...
                 */
                LogParam param = SpagoliteLogUtil.getLogParam(paramHelper.getParamApplicApplicationName(),
                        getUser().getUsername(), SpagoliteLogUtil.getPageName(this));
                param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());

                if (param.getNomePagina().equalsIgnoreCase(Application.Publisher.DETTAGLIO_AZIONE)) {
                    param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
                } else {
                    param.setNomeAzione(
                            SpagoliteLogUtil.getDetailActionNameDelete(getForm(), getForm().getAzioniList()));
                }
                amministrazioneUtentiEjb.deleteUsrAppartUserRich(param, idAppartUserRich, idUserIam, tiAzioneRich,
                        flAzioneRichEvasa);

                loadDettaglioRichiesta(idRichGestUser);
                getMessageBox().addInfo("Azione eliminata con successo");
                getMessageBox().setViewMode(ViewMode.plain);
            }
        } catch (ParerUserError ex) {
            getMessageBox().addError(ex.getDescription());
        }

        if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_AZIONE)) {
            goBackTo(Application.Publisher.DETTAGLIO_RICHIESTA);
        } else {
            forwardToPublisher(getLastPublisher());
        }
    }

    @Override
    public void updateAzioniList() {
        // Controllo se l'azione è modificabile
        UsrAppartUserRichRowBean appartUserRichRowBean = (UsrAppartUserRichRowBean) getForm().getAzioniList().getTable()
                .getCurrentRow();
        String flAzioneRichEvasa = appartUserRichRowBean.getFlAzioneRichEvasa();
        if (flAzioneRichEvasa.equals("1")) {
            getMessageBox().addError("Modifica dell'azione non consentita in quanto la stessa ha stato EVASA");
            forwardToPublisher(getLastPublisher());
        }

        if (!getMessageBox().hasError()) {
            getForm().getAzioniList().setStatus(Status.update);
            getForm().getAzioneDetail().setEditMode();
            getForm().getAzioneDetail().setStatus(Status.update);
            forwardToPublisher(Application.Publisher.DETTAGLIO_AZIONE);
        }
    }

    @Override
    public void rimuoviUtenteAzione() throws EMFError {
        getForm().getAzioneDetail().post(getRequest());
        getForm().getAzioneDetail().getId_user_iam().setValue(null);
        getForm().getAzioneDetail().getNm_userid_codificato().setValue(null);
        getForm().getAzioneDetail().getTi_appart_user_rich()
                .setValue(ConstUsrAppartUserRich.TiAppartUserRich.UTENTE_NON_CODIFICATO.name());
        forwardToPublisher(Application.Publisher.DETTAGLIO_AZIONE);
    }

    @Override
    public void scaricaFileRichiesta() throws EMFError {
        BigDecimal idRichGestUser = getForm().getRichiestaDetail().getId_rich_gest_user().parse();
        String filename = getForm().getRichiestaDetail().getNm_file_rich_gest_user().parse();

        if (filename != null) {
            OutputStream out = null;
            try {
                byte[] blob = amministrazioneUtentiEjb.getBlRichGestUser(idRichGestUser);

                // getResponse().setContentType("text/xml");
                getResponse().setHeader("Content-Disposition", "attachment; filename=\"" + filename);
                out = getServletOutputStream();
                IOUtils.write(blob, out);
                out.flush();
                out.close();
                freeze();
            } catch (Exception e) {
                getMessageBox().addMessage(
                        new Message(Message.MessageLevel.ERR, "Errore nella generazione del file della richiesta"));
                log.error("Errore nella generazione del file della richiesta", e);
            } finally {
                IOUtils.closeQuietly(out);
            }
        } else {
            getMessageBox().addError("Attenzione, nome file assente");
        }
        forwardToPublisher(Application.Publisher.DETTAGLIO_RICHIESTA);
    }

    @Secure(action = "detail/AmministrazioneUtentiForm#AzioniList/eseguiAzione")
    public void eseguiAzione() throws EMFError {
        try {
            String riga = getRequest().getParameter("riga");
            Integer nr = Integer.parseInt(riga);
            BigDecimal idUserIam = ((UsrAppartUserRichRowBean) getForm().getAzioniList().getTable().getRow(nr))
                    .getIdUserIam();
            String tiAzioneRich = ((UsrAppartUserRichRowBean) getForm().getAzioniList().getTable().getRow(nr))
                    .getTiAzioneRich();
            BigDecimal idRichGestUser = ((UsrAppartUserRichRowBean) getForm().getAzioniList().getTable().getRow(nr))
                    .getIdRichGestUser();
            BigDecimal idAppartUserRich = ((UsrAppartUserRichRowBean) getForm().getAzioniList().getTable().getRow(nr))
                    .getIdAppartUserRich();
            String nmUserid = ((UsrAppartUserRichRowBean) getForm().getAzioniList().getTable().getRow(nr))
                    .getNmUserid();

            ConstUsrAppartUserRich.TiAzioneRich azioneEnum = ConstUsrAppartUserRich.TiAzioneRich.getEnum(tiAzioneRich);
            /*
             * Codice aggiuntivo per il logging...
             */
            // Param per richiesta
            LogParam param = null;
            AmministrazioneUtentiEjb.KeycloakMessageSent mex = null;
            switch (azioneEnum) {
            case RICHIESTA_CREAZIONE:
                if (amministrazioneUtentiEjb.existsUtenteByUseridAndTipo(nmUserid,
                        ApplEnum.TipoUser.NON_DI_SISTEMA.name())) {
                    idUserIam = amministrazioneUtentiEjb.getUserRowBean(nmUserid).getIdUserIam();
                    eseguiAzioneModificaAbilitazioni(param, idUserIam, idRichGestUser, idAppartUserRich, azioneEnum);
                } else {
                    eseguiAzioneCreazione(param, nr, idRichGestUser, idAppartUserRich);
                }
                BigDecimal idEnteConvenz = getForm().getAzioniList().getTable().getRow(nr)
                        .getBigDecimal("id_ente_appart_user");
                String nmEnteConvenz = getForm().getAzioniList().getTable().getRow(nr).getString("nm_ente_appart_user");

                OrgAmbienteEnteConvenzRowBean ambienteEnteConvenz = entiConvenzionatiEjb
                        .getOrgAmbienteEnteConvenzByEnteConvenz(idEnteConvenz);

                BaseTable fakeTbAmbiente = new BaseTable();
                fakeTbAmbiente.add().setBigDecimal("id_ambiente_ente_convenz",
                        ambienteEnteConvenz.getIdAmbienteEnteConvenz());
                fakeTbAmbiente.getCurrentRow().setString("nm_ambiente_ente_convenz",
                        ambienteEnteConvenz.getNmAmbienteEnteConvenz());

                BaseTable fakeTb = new BaseTable();
                fakeTb.add().setBigDecimal("id_ente_convenz", idEnteConvenz);
                fakeTb.getCurrentRow().setString("nm_ente_convenz", nmEnteConvenz);

                getForm().getDettaglioUtente().getId_ente_siam_appart()
                        .setDecodeMap(DecodeMap.Factory.newInstance(fakeTb, "id_ente_convenz", "nm_ente_convenz"));
                if (ambienteEnteConvenz.getIdAmbienteEnteConvenz() != null) {
                    getForm().getDettaglioUtente().getId_amb_ente_convenz_appart().setDecodeMap(DecodeMap.Factory
                            .newInstance(fakeTbAmbiente, "id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));
                    getForm().getDettaglioUtente().getId_amb_ente_convenz_appart()
                            .setValue("" + ambienteEnteConvenz.getIdAmbienteEnteConvenz());
                } else {
                    getForm().getDettaglioUtente().getId_amb_ente_convenz_appart().setDecodeMap(new DecodeMap());
                }

                getForm().getDettaglioUtente().getId_ente_siam_appart().setValue("" + idEnteConvenz);

                OrgEnteSiamRowBean ente = entiConvenzionatiEjb.getOrgEnteConvenzRowBean(idEnteConvenz);

                if (ente.getTiEnte().equals("NON_CONVENZIONATO")) {
                    getForm().getDettaglioUtente().getTipo_user().setDecodeMap(ComboGetter
                            .getMappaSortedGenericEnum("tipo_user", ApplEnum.TipoUser.getComboTipiNonDiSistema()));
                    getForm().getDettaglioUtente().getTipo_user().setValue("NON_DI_SISTEMA");
                } else {
                    getForm().getDettaglioUtente().getTipo_user().setDecodeMap(
                            ComboGetter.getMappaSortedGenericEnum("tipo_user", ApplEnum.TipoUser.getComboTipiTotali()));
                }
                String tipoUser = getForm().getDettaglioUtente().getTipo_user().parse().equals("NON_DI_SISTEMA")
                        ? "NON_DI_SISTEMA" : "PERSONA_FISICA";

                setFlagUtente(ente.getTiEnte(), ente.getTiEnteNonConvenz(), tipoUser);

                break;
            case RICHIESTA_CESSAZIONE:
                param = SpagoliteLogUtil.getLogParam(null, getUser().getUsername(),
                        Application.Publisher.DETTAGLIO_UTENTE, SpagoliteLogUtil.getButtonActionName(this.getForm(),
                                this.getForm().getDettaglioUtente(), "cessaUtente"));
                param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
                // Eseguo operazione di cessazione (disattivazione) utente
                amministrazioneUtentiEjb.cessaUtente(param, idUserIam);
                loadDettaglioRichiesta(idRichGestUser);
                getMessageBox().addInfo("Utente cessato");
                forwardToPublisher(Application.Publisher.DETTAGLIO_RICHIESTA);
                break;
            case RICHIESTA_RIATTIVZIONE:
                param = SpagoliteLogUtil.getLogParam(null, getUser().getUsername(),
                        Application.Publisher.DETTAGLIO_UTENTE, SpagoliteLogUtil.getButtonActionName(this.getForm(),
                                this.getForm().getDettaglioUtente(), "attivaUtente"));
                param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
                // Eseguo operazione di attivazione utente
                mex = amministrazioneUtentiEjb.attivaUtente(param, idUserIam);
                loadDettaglioRichiesta(idRichGestUser);
                if (mex.getTipoMessaggio().equals(AmministrazioneUtentiEjb.KeycloakMessageSent.TIPO_INFO)) {
                    getMessageBox().addInfo(mex.getMessaggio());
                } else {
                    getMessageBox().addWarning(mex.getMessaggio());
                }
                forwardToPublisher(Application.Publisher.DETTAGLIO_RICHIESTA);
                break;
            case RICHIESTA_DISATTIVAZIONE:
                param = SpagoliteLogUtil.getLogParam(null, getUser().getUsername(),
                        Application.Publisher.DETTAGLIO_UTENTE, SpagoliteLogUtil.getButtonActionName(this.getForm(),
                                this.getForm().getDettaglioUtente(), "disattivaUtente"));
                param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
                // Eseguo operazione di disattivazione utente
                amministrazioneUtentiEjb.disattivaUtente(param, idUserIam);
                loadDettaglioRichiesta(idRichGestUser);
                getMessageBox().addInfo("Utente disattivato con successo. ");
                forwardToPublisher(Application.Publisher.DETTAGLIO_RICHIESTA);
                break;
            case RICHIESTA_MODIFICA_ABILITAZIONI:
            case RICHIESTA_MODIFICA_ENTE_APPARTENENZA:
                eseguiAzioneModificaAbilitazioni(param, idUserIam, idRichGestUser, idAppartUserRich, azioneEnum);

                if (getForm().getDettaglioUtente().getTipo_user().parse().equals("NON_DI_SISTEMA")) {
                    getForm().getDettaglioUtente().getId_sistema_versante().setDecodeMap(new DecodeMap());
                } else {
                    getForm().getDettaglioUtente().getId_sistema_versante().setEditMode();
                }

                break;
            case RICHIESTA_MODIFICA_ANAGRAFICA:
                eseguiAzioneModificaAbilitazioni(param, idUserIam, idRichGestUser, idAppartUserRich, azioneEnum);
                getForm().getDettaglioUtente().getId_sistema_versante().setViewMode();
                break;
            case RICHIESTA_RESET_PWD:
                //
                // MEV#28279 - Inserimento richiesta: aggiunta azione di "Reset password" nel menù a tendina
                //
                // Se l'utente è diverso da PERSONA_FISICA
                if (!amministrazioneUtentiEjb.existsUtenteByUseridAndTipo(nmUserid,
                        ApplEnum.TipoUser.PERSONA_FISICA.name())) {
                    throw new ParerUserError("Attenzione: utente " + nmUserid
                            + " diverso da PERSONA_FISICA. Impossibile eseguire l'azione. E' necessario visualizzare l'utente ed utilizzare il bottone 'Inserisci nuova password'");
                }
                param = SpagoliteLogUtil.getLogParam(null, getUser().getUsername(),
                        Application.Publisher.DETTAGLIO_UTENTE, SpagoliteLogUtil.getButtonActionName(this.getForm(),
                                this.getForm().getDettaglioUtente(), "resetPassword"));
                param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
                mex = amministrazioneUtentiEjb.resetPassword(param, idUserIam);
                loadDettaglioRichiesta(idRichGestUser);
                if (mex.getTipoMessaggio().equals(AmministrazioneUtentiEjb.KeycloakMessageSent.TIPO_INFO)) {
                    getMessageBox().addInfo(mex.getMessaggio());
                } else {
                    getMessageBox().addWarning(mex.getMessaggio());
                }
                forwardToPublisher(Application.Publisher.DETTAGLIO_RICHIESTA);
                break;
            default:
                throw new AssertionError(tiAzioneRich + " non valido");
            }
        } catch (ParerUserError ex) {
            getMessageBox().addError(ex.getDescription());
            forwardToPublisher(getLastPublisher());
        }
    }

    private void setFlagUtente(String tiEnte, String tiEnteNonConvenz, String tipoUser) {
        if (tiEnte != null && tiEnte.equals("CONVENZIONATO") && tipoUser != null && tipoUser.equals("PERSONA_FISICA")) {
            getForm().getDettaglioUtente().getFl_resp_ente_convenz()
                    .setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
            getForm().getDettaglioUtente().getFl_abil_enti_colleg_autom()
                    .setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
            if (getForm().getDettaglioUtente().getStatus() != null
                    && getForm().getDettaglioUtente().getStatus().equals(Status.insert)) {
                getForm().getDettaglioUtente().getFl_abil_enti_colleg_autom().setValue("1");
            }
        } else {
            getForm().getDettaglioUtente().getFl_resp_ente_convenz().setDecodeMap(ComboGetter.getMappaGenericFlagNo());
            getForm().getDettaglioUtente().getFl_abil_enti_colleg_autom().setDecodeMap(new DecodeMap());
        }

        if (tiEnte != null && tiEnte.equals("NON_CONVENZIONATO") && tiEnteNonConvenz != null
                && tiEnteNonConvenz.equals("ORGANO_VIGILANZA") && tipoUser != null
                && tipoUser.equals("PERSONA_FISICA")) {
            getForm().getDettaglioUtente().getFl_abil_organiz_autom()
                    .setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
        } else {
            getForm().getDettaglioUtente().getFl_abil_organiz_autom().setDecodeMap(new DecodeMap());
        }

        if (tiEnte != null && tiEnte.equals("NON_CONVENZIONATO") && tiEnteNonConvenz != null
                && tiEnteNonConvenz.equals(ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO.name())
                && tipoUser != null && tipoUser.equals("PERSONA_FISICA")) {
            getForm().getDettaglioUtente().getFl_abil_fornit_autom()
                    .setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
            getForm().getDettaglioUtente().getFl_abil_fornit_autom().setValue("1");
        } else if (tiEnte != null && tiEnte.equals("NON_CONVENZIONATO") && tiEnteNonConvenz != null
                && tiEnteNonConvenz.equals("SOGGETTO_ATTUATORE") && tipoUser != null
                && tipoUser.equals("PERSONA_FISICA")) {
            getForm().getDettaglioUtente().getFl_abil_fornit_autom()
                    .setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
            getForm().getDettaglioUtente().getFl_abil_fornit_autom().setValue("1");
        } else {
            getForm().getDettaglioUtente().getFl_abil_fornit_autom().setDecodeMap(new DecodeMap());
        }

    }

    private void eseguiAzioneCreazione(LogParam param, int nr, BigDecimal idRichGestUser, BigDecimal idAppartUserRich)
            throws EMFError {
        param = SpagoliteLogUtil.getLogParam(null, getUser().getUsername(),
                Application.Publisher.DETTAGLIO_UTENTE_WIZARD, SpagoliteLogUtil.getToolbarInsert());
        param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
        // Reindirizzo al wizard di creazione utente con i dati dell'utente
        getRequest().setAttribute("fromRichiesta", true);
        getForm().getInserimentoWizard().getPassoRichiesta().setHidden(true);
        getForm().getInserimentoWizard().getPassoRuoliSpecifici().setHidden(true);
        getForm().getInserimentoWizard().getPasso1().setHidden(false);
        getForm().getInserimentoWizard().getPassoApplic().setHidden(false);
        getForm().getInserimentoWizard().getPassoIp().setHidden(false);
        getForm().getInserimentoWizard().getPasso2().setHidden(false);
        setTableName(getForm().getListaUtenti().getName());
        setNavigationEvent(NE_DETTAGLIO_INSERT);
        dettaglioOnClick();
        insertDettaglio();
        // Popolamento automatico dei campi
        getForm().getDettaglioUtente().getNm_nome_user()
                .setValue(((UsrAppartUserRichRowBean) getForm().getAzioniList().getTable().getRow(nr)).getNmNomeUser());
        getForm().getDettaglioUtente().getNm_cognome_user().setValue(
                ((UsrAppartUserRichRowBean) getForm().getAzioniList().getTable().getRow(nr)).getNmCognomeUser());
        getForm().getDettaglioUtente().getNm_userid()
                .setValue(((UsrAppartUserRichRowBean) getForm().getAzioniList().getTable().getRow(nr)).getNmUserid());
        getForm().getDettaglioUtente().getId_ente_siam_appart()
                .setValue("" + ((UsrAppartUserRichRowBean) getForm().getAzioniList().getTable().getRow(nr))
                        .getBigDecimal("id_ente_appart_user"));
        BigDecimal idAmbienteEnteAppartUser = ((UsrAppartUserRichRowBean) getForm().getAzioniList().getTable()
                .getRow(nr)).getBigDecimal("id_ambiente_ente_appart_user");
        getForm().getDettaglioUtente().getId_amb_ente_convenz_appart()
                .setValue("" + (idAmbienteEnteAppartUser != null ? idAmbienteEnteAppartUser : ""));
        // Se l'ente è non convenzionato il sistema versante non è selezionabile
        if (idAmbienteEnteAppartUser == null) {
            getForm().getDettaglioUtente().getId_sistema_versante().setDecodeMap(new DecodeMap());
        }

        getForm().getRichiestaWizard().getId_rich_gest_user().setValue(idRichGestUser.toPlainString());
        if (idAppartUserRich != null) {
            getForm().getRichiestaWizard().getId_appart_user_rich().setValue(idAppartUserRich.toPlainString());
        }
    }

    private void eseguiAzioneModificaAbilitazioni(LogParam param, BigDecimal idUserIam, BigDecimal idRichGestUser,
            BigDecimal idAppartUserRich, ConstUsrAppartUserRich.TiAzioneRich azioneEnum) throws EMFError {
        // Reindirizzo al wizard di modifica utente con i dati dell'utente
        param = SpagoliteLogUtil.getLogParam(null, getUser().getUsername(),
                Application.Publisher.DETTAGLIO_UTENTE_WIZARD, SpagoliteLogUtil.getToolbarUpdate());
        param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
        getRequest().setAttribute("fromRichiesta", true);
        getForm().getInserimentoWizard().getPassoRichiesta().setHidden(true);
        getForm().getInserimentoWizard().getPassoRuoliSpecifici().setHidden(true);
        if (azioneEnum.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ANAGRAFICA)) {
            getForm().getInserimentoWizard().getPasso1().setHidden(false);
            getForm().getInserimentoWizard().getPassoApplic().setHidden(true);
            getForm().getInserimentoWizard().getPassoIp().setHidden(true);
            getForm().getInserimentoWizard().getPasso2().setHidden(true);
        } else {
            getForm().getInserimentoWizard().getPasso1().setHidden(false);
            getForm().getInserimentoWizard().getPassoApplic().setHidden(false);
            getForm().getInserimentoWizard().getPassoIp().setHidden(false);
            getForm().getInserimentoWizard().getPasso2().setHidden(false);
        }

        setTableName(getForm().getListaUtenti().getName());
        setNavigationEvent(NE_DETTAGLIO_UPDATE);
        loadDettaglioUtente(idUserIam);
        dettaglioOnClick();
        updateListaUtenti();

        // Se sono in modifica ente di appartenenza devo CANCELLARE tutte le abilitazioni, a parte quelle sui ruoli
        if (azioneEnum.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ENTE_APPARTENENZA)) {
            azzeraListeDichiarazioniAbilitazioni();
        }

        getForm().getRichiestaWizard().getId_rich_gest_user().setValue(idRichGestUser.toPlainString());
        if (idAppartUserRich != null) {
            getForm().getRichiestaWizard().getId_appart_user_rich().setValue(idAppartUserRich.toPlainString());
        }

        manageAmbienteEnteAppartenenzaForUpdate(idRichGestUser);

        if (azioneEnum.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ANAGRAFICA)) {
            getForm().getDettaglioUtente().setViewMode();
            getForm().getDettaglioUtente().getNm_cognome_user().setEditMode();
            getForm().getDettaglioUtente().getNm_nome_user().setEditMode();
            getForm().getDettaglioUtente().getDs_email().setEditMode();
            getForm().getDettaglioUtente().getDs_email_secondaria().setEditMode();
            getForm().getDettaglioUtente().getCd_fisc().setEditMode();
        }
    }

    @Override
    public void richiestaCompletata() throws EMFError {
        BigDecimal idRichGestUser = getForm().getRichiestaDetail().getId_rich_gest_user().parse();

        if (getForm().getAzioniList().getTable() != null && !getForm().getAzioniList().getTable().isEmpty()) {
            /*
             * Codice aggiuntivo per il logging...
             */
            class Local {
            }
            ;
            String nomeMetodo = Local.class.getEnclosingMethod().getName();
            LogParam param = SpagoliteLogUtil.getLogParam(null, getUser().getUsername(),
                    SpagoliteLogUtil.getPageName(this), SpagoliteLogUtil.getButtonActionName(this.getForm(),
                            this.getForm().getRichiestaDetail(), nomeMetodo));
            param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
            amministrazioneUtentiEjb.updateTiStatoRichGestUser(idRichGestUser,
                    ConstUsrRichGestUser.TiStatoRichGestUser.DA_EVADERE);
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_RICHIESTA,
                    idRichGestUser, param.getNomePagina());
            getForm().getRichiestaDetail().getRichiestaCompletata().setHidden(true);
            loadDettaglioRichiesta(idRichGestUser);
            getMessageBox().addInfo("Lo stato della richiesta è diventato DA_EVADERE");
        } else {
            getMessageBox().addError(
                    "La richiesta può essere definita completata (e, quindi, da evadere) solo se è definita almeno un'azione");
        }
        forwardToPublisher(Application.Publisher.DETTAGLIO_RICHIESTA);
    }

    public void loadDettaglioLastRichGestUser() throws EMFError {
        String rigaParam = getRequest().getParameter("riga");
        int riga = Integer.parseInt(rigaParam);

        initRichiestaDetail();

        BigDecimal idRichGestUser = ((BaseRowInterface) getForm().getListaUtenti().getTable().getRow(riga))
                .getBigDecimal("id_last_rich_gest_user");
        loadDettaglioRichiesta(idRichGestUser);

        BaseTable table = new BaseTable();
        BaseRow row = new BaseRow();
        row.setBigDecimal("id_rich_gest_user", idRichGestUser);
        table.add(row);
        getForm().getRichiesteList().setTable(table);
        setTableName(getForm().getRichiesteList().getName());
        setRiga("" + getForm().getRichiesteList().getTable().getCurrentRowIndex());

        forwardToPublisher(Application.Publisher.DETTAGLIO_RICHIESTA);
    }

    @Override
    public void cessaUtente() throws EMFError {
        try {
            /*
             * Codice aggiuntivo per il logging...
             */
            class Local {
            }
            ;
            String nomeMetodo = Local.class.getEnclosingMethod().getName();
            LogParam param = SpagoliteLogUtil.getLogParam(null, getUser().getUsername(),
                    SpagoliteLogUtil.getPageName(this), SpagoliteLogUtil.getButtonActionName(this.getForm(),
                            this.getForm().getDettaglioUtente(), nomeMetodo));
            param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
            amministrazioneUtentiEjb.cessaUtente(param, getForm().getDettaglioUtente().getId_user_iam().parse());
            getForm().getDettaglioUtente().getFl_attivo().setValue("0");
            setVisibilityAbilitaDisabilitaUtente(getForm().getDettaglioUtente().getId_user_iam().parse());
            getMessageBox().addInfo("Utente cessato");
        } catch (ParerUserError e) {
            getMessageBox().addError(e.getDescription());
        }
        forwardToPublisher(Application.Publisher.DETTAGLIO_UTENTE);
    }

    @Override
    public JSONObject triggerRichiestaWizardId_ambiente_ente_convenz_richOnTrigger() throws EMFError {
        getForm().getRichiestaWizard().post(getRequest());
        BigDecimal idAmbienteEnteconvenz = getForm().getRichiestaWizard().getId_ambiente_ente_convenz_rich().parse();
        DecodeMap mappa = new DecodeMap();
        if (idAmbienteEnteconvenz != null) {
            OrgVRicEnteConvenzTableBean ricEnteConvenz = entiConvenzionatiEjb.getOrgVRicEnteConvenzAbilTableBean(
                    BigDecimal.valueOf(getUser().getIdUtente()), idAmbienteEnteconvenz, null);
            mappa.populatedMap(ricEnteConvenz, "id_ente_convenz", "nm_ente_convenz");
        } else {
            OrgVRicEnteNonConvenzTableBean ricEnteNonConvenz = entiConvenzionatiEjb
                    .getOrgVRicEnteNonConvenzAbilTableBean(BigDecimal.valueOf(getUser().getIdUtente()), null);
            mappa.populatedMap(ricEnteNonConvenz, "id_ente_siam", "nm_ente_siam");
        }
        getForm().getRichiestaWizard().getId_ente_convenz_rich().setDecodeMap(mappa);
        return getForm().getRichiestaWizard().asJSON();
    }

    @Override
    public void logEventiRichiesta() throws EMFError {
        GestioneLogEventiForm form = new GestioneLogEventiForm();
        form.getOggettoDetail().getNmApp().setValue(paramHelper.getParamApplicApplicationName());
        form.getOggettoDetail().getNm_tipo_oggetto().setValue(SacerLogConstants.TIPO_OGGETTO_RICHIESTA);
        form.getOggettoDetail().getIdOggetto()
                .setValue(getForm().getRichiestaDetail().getId_rich_gest_user().getValue());
        redirectToAction(it.eng.parer.sacerlog.slite.gen.Application.Actions.GESTIONE_LOG_EVENTI,
                "?operation=inizializzaLogEventi", form);
    }

    @Override
    public JSONObject triggerRichiestaDetailId_organiz_iamOnTrigger() throws EMFError {
        getForm().getRichiestaDetail().post(getRequest());
        BigDecimal idOrganizIam = getForm().getRichiestaDetail().getId_organiz_iam().parse();
        if (idOrganizIam != null) {
            BigDecimal idApplicSacer = new BigDecimal(userHelper.getAplApplic("SACER").getIdApplic());
            UsrVAbilDatiTableBean usrTipoDatoIam = amministrazioneUtentiEjb
                    .getUsrVAbilDatiTableBean(getUser().getIdUtente(), "REGISTRO", idApplicSacer, idOrganizIam);
            DecodeMap mappa = new DecodeMap();
            mappa.populatedMap(usrTipoDatoIam, "nm_tipo_dato", "nm_tipo_dato");
            getForm().getRichiestaDetail().getCd_registro_rich_gest_user().setDecodeMap(mappa);
        } else {
            getForm().getRichiestaDetail().getCd_registro_rich_gest_user().setDecodeMap(new DecodeMap());
        }
        return getForm().getRichiestaDetail().asJSON();
    }

    @Override
    public JSONObject triggerRichiestaDetailId_ambiente_ente_convenzOnTrigger() throws EMFError {
        getForm().getRichiestaDetail().post(getRequest());
        BigDecimal idAmbienteEnteconvenz = getForm().getRichiestaDetail().getId_ambiente_ente_convenz().parse();
        DecodeMap mappa = new DecodeMap();
        if (idAmbienteEnteconvenz != null) {
            OrgVRicEnteConvenzTableBean ricEnteConvenz = entiConvenzionatiEjb
                    .getOrgVRicEnteConvenzAbilAccordoValidoTableBean(BigDecimal.valueOf(getUser().getIdUtente()),
                            idAmbienteEnteconvenz);
            mappa.populatedMap(ricEnteConvenz, "id_ente_convenz", "nm_ente_convenz");
        } else {
            OrgVRicEnteNonConvenzTableBean ricEnteNonConvenz = entiConvenzionatiEjb
                    .getOrgVRicEnteNonConvenzAbilTableBean(BigDecimal.valueOf(getUser().getIdUtente()), null);
            mappa.populatedMap(ricEnteNonConvenz, "id_ente_siam", "nm_ente_siam");
        }
        getForm().getRichiestaDetail().getId_ente_rich().setDecodeMap(mappa);
        return getForm().getRichiestaDetail().asJSON();
    }

    @Override
    public JSONObject triggerFiltriRichiesteId_organiz_iamOnTrigger() throws EMFError {
        getForm().getFiltriRichieste().post(getRequest());
        BigDecimal idOrganizIam = getForm().getFiltriRichieste().getId_organiz_iam().parse();
        if (idOrganizIam != null) {
            BigDecimal idApplicSacer = new BigDecimal(userHelper.getAplApplic("SACER").getIdApplic());
            UsrVAbilDatiTableBean usrTipoDatoIam = amministrazioneUtentiEjb
                    .getUsrVAbilDatiTableBean(getUser().getIdUtente(), "REGISTRO", idApplicSacer, idOrganizIam);
            DecodeMap mappa = new DecodeMap();
            mappa.populatedMap(usrTipoDatoIam, "nm_tipo_dato", "nm_tipo_dato");
            getForm().getFiltriRichieste().getCd_registro_rich_gest_user().setDecodeMap(mappa);
        } else {
            getForm().getFiltriRichieste().getCd_registro_rich_gest_user().setDecodeMap(new DecodeMap());
        }
        return getForm().getFiltriRichieste().asJSON();
    }

    @Override
    public JSONObject triggerRichiestaWizardId_organiz_iam_richOnTrigger() throws EMFError {
        getForm().getRichiestaWizard().post(getRequest());
        BigDecimal idOrganizIamRich = getForm().getRichiestaWizard().getId_organiz_iam_rich().parse();
        if (idOrganizIamRich != null) {
            BigDecimal idApplicSacer = new BigDecimal(userHelper.getAplApplic("SACER").getIdApplic());
            UsrVAbilDatiTableBean usrTipoDatoIam = amministrazioneUtentiEjb
                    .getUsrVAbilDatiTableBean(getUser().getIdUtente(), "REGISTRO", idApplicSacer, idOrganizIamRich);
            DecodeMap mappa = new DecodeMap();
            mappa.populatedMap(usrTipoDatoIam, "nm_tipo_dato", "nm_tipo_dato");
            getForm().getRichiestaWizard().getCd_registro_rich_gest_user().setDecodeMap(mappa);
        } else {
            getForm().getRichiestaWizard().getCd_registro_rich_gest_user().setDecodeMap(new DecodeMap());
        }
        return getForm().getRichiestaWizard().asJSON();
    }

    @Override
    public JSONObject triggerAzioneDetailTi_azione_richOnTrigger() throws EMFError {
        getForm().getAzioneDetail().post(getRequest());
        String tiAzioneRich = getForm().getAzioneDetail().getTi_azione_rich().parse();
        BigDecimal idRichGestUser = getForm().getRichiestaDetail().getId_rich_gest_user().parse();
        BaseTable ambientiTB = new BaseTable();
        BaseTable entiTB = new BaseTable();
        populateAzioneAmbienteEnteAppart(tiAzioneRich, idRichGestUser, ambientiTB, entiTB);
        return getForm().getAzioneDetail().asJSON();
    }

    public void populateAzioneAmbienteEnteAppart(String tiAzioneRich, BigDecimal idRichGestUser, BaseTable ambientiTB,
            BaseTable entiTB) {
        // POPOLAMENTO AMBIENTE DI APPARTENENZA UTENTE
        if (tiAzioneRich != null) {
            if (tiAzioneRich.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_CREAZIONE.getDescrizione())) {
                ambientiTB = entiConvenzionatiEjb.getAmbientiDaUsrVLisEntiSiamCreaUserTableBean(idRichGestUser,
                        getUser().getIdUtente());
                entiTB = entiConvenzionatiEjb.getEntiDaUsrVLisEntiSiamCreaUserTableBean(idRichGestUser,
                        getUser().getIdUtente(), null);
            } else if (tiAzioneRich.equals(
                    ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ENTE_APPARTENENZA.getDescrizione())) {
                ambientiTB = entiConvenzionatiEjb.getAmbientiDaUsrVLisEntiSiamAppEnteTableBean(getUser().getIdUtente());
                // if (ambientiTB.getCurrentRow() == null) {
                entiTB = entiConvenzionatiEjb.getEntiDaUsrVLisEntiSiamAppEnteTableBean(getUser().getIdUtente(), null);
                // }
            } else if (!tiAzioneRich.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_CREAZIONE.getDescrizione())
                    && !tiAzioneRich.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ENTE_APPARTENENZA
                            .getDescrizione())) {
                ambientiTB = entiConvenzionatiEjb.getAmbientiDaUsrVLisEntiSiamPerAzioTableBean(idRichGestUser,
                        getUser().getIdUtente());
                // if (ambientiTB.getCurrentRow() == null) {
                entiTB = entiConvenzionatiEjb.getEntiDaUsrVLisEntiSiamPerAzioTableBean(idRichGestUser,
                        getUser().getIdUtente(), null);
                // }
            }
            getForm().getAzioneDetail().getId_ambiente_ente_user().setDecodeMap(
                    DecodeMap.Factory.newInstance(ambientiTB, "id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));
            getForm().getAzioneDetail().getId_ente_user()
                    .setDecodeMap(DecodeMap.Factory.newInstance(entiTB, "id_ente_siam", "nm_ente_siam"));
        } else {
            getForm().getAzioneDetail().getId_ambiente_ente_user().setDecodeMap(new DecodeMap());
            getForm().getAzioneDetail().getId_ente_user().setDecodeMap(new DecodeMap());
        }
    }

    @Override
    public JSONObject triggerAzioneDetailId_ambiente_ente_userOnTrigger() throws EMFError {
        getForm().getAzioneDetail().post(getRequest());
        String tiAzioneRich = getForm().getAzioneDetail().getTi_azione_rich().parse();
        BigDecimal idRichGestUser = getForm().getRichiestaDetail().getId_rich_gest_user().parse();
        BigDecimal idAmbienteEnteUser = getForm().getAzioneDetail().getId_ambiente_ente_user().parse();
        BaseTable entiTB = new BaseTable();
        populateAzioneEnteAppart(tiAzioneRich, idRichGestUser, idAmbienteEnteUser, entiTB);
        return getForm().getAzioneDetail().asJSON();
    }

    public void populateAzioneEnteAppart(String tiAzioneRich, BigDecimal idRichGestUser, BigDecimal idAmbienteEnteUser,
            BaseTable entiTB) {
        // POPOLAMENTO ENTE DI APPARTENENZA UTENTE
        if (tiAzioneRich != null) {
            if (tiAzioneRich.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_CREAZIONE.getDescrizione())) {
                entiTB = entiConvenzionatiEjb.getEntiDaUsrVLisEntiSiamCreaUserTableBean(idRichGestUser,
                        getUser().getIdUtente(), idAmbienteEnteUser);
            } else if (!tiAzioneRich.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_CREAZIONE.getDescrizione())
                    && !tiAzioneRich.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ENTE_APPARTENENZA
                            .getDescrizione())) {
                entiTB = entiConvenzionatiEjb.getEntiDaUsrVLisEntiSiamPerAzioTableBean(idRichGestUser,
                        getUser().getIdUtente(), idAmbienteEnteUser);
            } else if (tiAzioneRich.equals(
                    ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ENTE_APPARTENENZA.getDescrizione())) {
                entiTB = entiConvenzionatiEjb.getEntiDaUsrVLisEntiSiamAppEnteTableBean(getUser().getIdUtente(),
                        idAmbienteEnteUser);
            }
            getForm().getAzioneDetail().getId_ente_user()
                    .setDecodeMap(DecodeMap.Factory.newInstance(entiTB, "id_ente_siam", "nm_ente_siam"));
        } else {
            getForm().getAzioneDetail().getId_ente_user().setDecodeMap(new DecodeMap());
        }
    }

    @Override
    public void deleteRichiestaDetail() throws EMFError {
        deleteRichiesteList();
    }

    @Override
    public void updateRichiestaDetail() throws EMFError {
        updateRichiesteList();
    }

    public void aggiungiUtenteArchivista() throws EMFError {
        // Ricavo l'utente selezionato
        Integer riga = Integer.parseInt(getRequest().getParameter("riga"));
        UsrVLisUserRowBean utenteSelezionato = ((UsrVLisUserTableBean) getForm().getListaUtenti().getTable())
                .getRow(riga);
        getSession().setAttribute("idUserIamUtenteArk", utenteSelezionato.getIdUserIam().toString());
        getSession().setAttribute("nmUseridUtenteCodificatoArk", utenteSelezionato.getNmUserid());
        goBack();
    }

    public void aggiungiReferenteEnte() throws EMFError {
        // Ricavo l'utente selezionato
        Integer riga = Integer.parseInt(getRequest().getParameter("riga"));
        UsrVLisUserRowBean utenteSelezionato = ((UsrVLisUserTableBean) getForm().getListaUtenti().getTable())
                .getRow(riga);
        getSession().setAttribute("idUserIamReferenteEnte", utenteSelezionato.getIdUserIam().toString());
        getSession().setAttribute("nmUseridUtenteCodificatoReferenteEnte", utenteSelezionato.getNmUserid());
        goBack();
    }

    @Override
    protected void postLoad() {
        super.postLoad();
        Object ogg = getForm();
        if (ogg instanceof AmministrazioneUtentiForm) {
            if (getForm().getRichiestaDetail().getStatus() != null) {
                if (getForm().getRichiestaDetail().getStatus().equals(Status.view)) {
                    try {
                        if (getForm().getRichiestaDetail().getCd_registro_rich_gest_user().parse() != null
                                && getForm().getRichiestaDetail().getAa_rich_gest_user().parse() != null
                                && getForm().getRichiestaDetail().getCd_key_rich_gest_user().parse() != null) {
                            getForm().getUdButtonList().getScaricaCompFileUdRichiesta().setDisableHourGlass(true);
                            getForm().getUdButtonList().getScaricaCompFileUdRichiesta().setEditMode();
                        } else {
                            getForm().getUdButtonList().getScaricaCompFileUdRichiesta().setViewMode();
                        }
                    } catch (EMFError ex) {
                        getMessageBox()
                                .addError("Errore durante il caricamento del bottone Scarica file unità documentaria");
                    }
                } else {
                    getForm().getUdButtonList().getScaricaCompFileUdRichiesta().setViewMode();
                }
            }
        }
    }

    public void download() throws EMFError {
        log.debug(">>>DOWNLOAD");
        String filename = (String) getSession().getAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_FILENAME.name());
        String path = (String) getSession().getAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_FILEPATH.name());
        Boolean deleteFile = Boolean.parseBoolean(
                (String) getSession().getAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_DELETEFILE.name()));
        String contentType = (String) getSession()
                .getAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_CONTENTTYPE.name());
        if (path != null && filename != null) {
            File fileToDownload = new File(path);
            if (fileToDownload.exists()) {
                /*
                 * Definiamo l'output previsto che sarà  un file in formato zip di cui si occuperà  la servlet per fare
                 * il download
                 */
                OutputStream outUD = getServletOutputStream();
                getResponse().setContentType(
                        StringUtils.isBlank(contentType) ? WebConstants.MIME_TYPE_GENERIC : contentType);
                getResponse().setHeader("Content-Disposition", "attachment; filename=\"" + filename);

                FileInputStream inputStream = null;
                try {
                    getResponse().setHeader("Content-Length", String.valueOf(fileToDownload.length()));
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
                getMessageBox().addError("Errore durante il tentativo di download. File non trovato");
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

    public void scaricaCompFileUd(BigDecimal idOrganizIam, String registro, BigDecimal anno, String numero)
            throws EMFError {
        // Recupero da DB i parametri per la chiamata al ws
        // Map<String, String> paramsByType =
        // paramHelper.getParamsByType(ConstIamParamApplic.TiParamApplic.SACER_RECUP.name());
        String versione = paramHelper.getValoreParamApplic(
                ConstIamParamApplic.NmParamApplic.VERSIONE_XML_RECUP_UD.name(), null, null,
                Constants.TipoIamVGetValAppart.APPLIC);
        String loginname = paramHelper.getValoreParamApplic(ConstIamParamApplic.NmParamApplic.USERID_RECUP_UD.name(),
                null, null, Constants.TipoIamVGetValAppart.APPLIC);
        String password = paramHelper.getValoreParamApplic(ConstIamParamApplic.NmParamApplic.PSW_RECUP_UD.name(), null,
                null, Constants.TipoIamVGetValAppart.APPLIC);
        Integer timeout = Integer
                .parseInt(paramHelper.getValoreParamApplic(ConstIamParamApplic.NmParamApplic.TIMEOUT_RECUP_UD.name(),
                        null, null, Constants.TipoIamVGetValAppart.APPLIC));
        String url = paramHelper.getValoreParamApplic(ConstIamParamApplic.NmParamApplic.URL_RECUP_UD.name(), null, null,
                Constants.TipoIamVGetValAppart.APPLIC);
        BigDecimal idAmbienteEnteConvenz = entiConvenzionatiEjb
                .getOrgAmbienteEnteConvenzByEnteConvenz(getForm().getRichiestaDetail().getId_ente_rich().parse())
                .getIdAmbienteEnteConvenz();
        BigDecimal idEnteConvenz = getForm().getRichiestaDetail().getId_ente_rich().parse();
        UsrVTreeOrganizIamRowBean rb = amministrazioneUtentiEjb.getUsrVTreeOrganizIamPerVersatore(idOrganizIam);
        String dlPath = rb.getDlPathIdOrganizIam();
        // Creo il client di chiamata al ws rest impostando il timeout come parametro recuperato da DB
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
            multipartRequest.add("XMLSIP", getXmlSip(versione, loginname, registro, anno, numero, dlPath));
            // Creo la richiesta
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(multipartRequest, header);
            // Mi faccio restituire la risposta dalla chiamata al WS
            HttpEntity<Resource> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Resource.class);
            File temp = null;
            // Recupero l'inputStream col flusso del file da scaricare dal corpo della response
            InputStream is = response.getBody().getInputStream();
            // Header RAW Content-Disposition:" attachment; filename="UD_DELIBERE DI GIUNTA-2018-1.zip"
            // Dall'header recupero il Content-Disposition e successivamente da esso il filename
            String fileName = response.getHeaders().getFirst("Content-Disposition");
            MediaType contentType = response.getHeaders().getContentType();

            if (contentType != null && "zip".equals(contentType.getSubtype()) && StringUtils.isNotBlank(fileName)) {
                fileName = fileName.substring(fileName.indexOf("\"") + 1, fileName.length());
                // Creo il file temporaneo
                temp = File.createTempFile("prefisso", "suffisso");
                // Leggo dall'inputStream e "riepio" il file temporaneo tramite outputstream
                try (OutputStream outStream = new FileOutputStream(temp)) {
                    byte[] buffer = new byte[8 * 1024];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        outStream.write(buffer, 0, bytesRead);
                    }
                }

                if (response == null) {
                    // caso KO
                    getMessageBox().addError("Errore durante il tentativo di download del file");
                } else {
                    // caso OK
                    getRequest().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_ACTION.name(), getControllerName());
                    getSession().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_FILENAME.name(), fileName);
                    getSession().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_FILEPATH.name(),
                            temp.getAbsolutePath());
                    getSession().setAttribute(WebConstants.DOWNLOAD_ATTRS.DOWNLOAD_DELETEFILE.name(),
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
                    messaggioErrore = messaggioErrore + " - " + sb.substring(dsErroreStart + 17, dsErroreStop);
                }
                if (messaggioErrore == null) {
                    messaggioErrore = "Errore durante il tentativo di download del file: impossibile recuperare il nome file";
                }
                getMessageBox().addError(messaggioErrore);
                forwardToPublisher(getLastPublisher());
            }
        } catch (ResourceAccessException ex) {
            getMessageBox().addError("Errore durante il tentativo di download del file: timeout scaduto");
            forwardToPublisher(getLastPublisher());
        } catch (HttpClientErrorException ex) {
            getMessageBox().addError("Errore durante la chiamata al ws per il download del file");
            forwardToPublisher(getLastPublisher());
        } catch (EMFError | IOException | RestClientException ex) {
            getMessageBox().addError("Errore durante il tentativo di download del file");
            forwardToPublisher(getLastPublisher());
        }
    }

    private String getXmlSip(String versione, String nmUserid, String registro, BigDecimal anno, String numero,
            String dlPath) throws EMFError {
        Map<String, String> org = entiConvenzionatiEjb.getUsrOrganizIamMapByDlPath(dlPath);
        String xml = "<Recupero><Versione>" + versione + "</Versione><Versatore><Ambiente>" + org.get("AMBIENTE")
                + "</Ambiente><Ente>" + org.get("ENTE") + "</Ente><Struttura>" + org.get("STRUTTURA") + "</Struttura>"
                + "<UserID>" + nmUserid + "</UserID></Versatore><Chiave><Numero>" + numero + "</Numero><Anno>" + anno
                + "</Anno><TipoRegistro>" + registro + "</TipoRegistro></Chiave></Recupero>";
        return xml;
    }

    @Override
    public void scaricaCompFileUdRichiesta() throws Throwable {
        String registro = getForm().getRichiestaDetail().getCd_registro_rich_gest_user().parse();
        BigDecimal anno = getForm().getRichiestaDetail().getAa_rich_gest_user().parse();
        String numero = getForm().getRichiestaDetail().getCd_key_rich_gest_user().parse();
        BigDecimal idOrganizIam = getForm().getRichiestaDetail().getId_organiz_iam().parse();
        scaricaCompFileUd(idOrganizIam, registro, anno, numero);
    }

    @Override
    public JSONObject triggerDettaglioUtenteTipo_userOnTrigger() throws EMFError {
        getForm().getDettaglioUtente().post(getRequest());
        String tipoUser = getForm().getDettaglioUtente().getTipo_user().parse();
        BigDecimal idEnteUser = getForm().getDettaglioUtente().getId_ente_siam_appart().parse();
        getForm().getDettaglioUtente().getId_sistema_versante().setDecodeMap(new DecodeMap());
        if (tipoUser != null) {
            if (!tipoUser.equals(ApplEnum.TipoUser.NON_DI_SISTEMA.name())) {
                DecodeMap mappaSistemiVersanti = new DecodeMap();
                mappaSistemiVersanti.populatedMap(
                        sistemiVersantiEjb.getAplSistemaVersanteValidiTableBean(tipoUser, idEnteUser),
                        "id_sistema_versante", "nm_sistema_versante");
                getForm().getDettaglioUtente().getId_sistema_versante().setDecodeMap(mappaSistemiVersanti);
            }
        }

        try {
            if (idEnteUser != null) {
                OrgEnteSiamRowBean ente = entiConvenzionatiEjb.getOrgEnteConvenzRowBean(idEnteUser);
                setFlagUtente(ente.getTiEnte(), ente.getTiEnteNonConvenz(), tipoUser);
            }
        } catch (ParerUserError ex) {
            getMessageBox().addError(ex.getDescription());
        }

        return getForm().getDettaglioUtente().asJSON();
    }

    public void triggerFiltriUtentiId_amb_ente_convenz_appart() throws EMFError, JSONException, IOException {
        //
        String valore = (String) getRequest().getParameter("idAmbienteConvenzAppart");
        ObjectMapper mapper = new ObjectMapper();
        String[] ogg = mapper.readValue(valore, String[].class);

        int dimArray = 0;
        if (ogg != null) {
            dimArray = ogg.length;
        } else {
            ogg = new String[0];
        }

        String[] idAmbienteEnteConvenzTotalString = new String[dimArray];
        List<BigDecimal> idAmbienteEnteConvenzTotal = new ArrayList<>();
        for (int i = 0; i < ogg.length; i++) {
            idAmbienteEnteConvenzTotalString[i] = ogg[i];
            idAmbienteEnteConvenzTotal.add(new BigDecimal(ogg[i]));
        }

        getForm().getFiltriUtenti().getId_amb_ente_convenz_appart().setValues(idAmbienteEnteConvenzTotalString);

        DecodeMap mappa = new DecodeMap();

        // GESTIONE RICERCA UTENTI ARCHIVISTI DA SISTEMA VERSANTE (con la lista degli utenti selezionati)
        if (getForm().getUtentiArchivistiPerSistemaVersante().getId_sistema_versante().parse() != null
                || getForm().getUtenteArchivistaPerEnteConvenzionato().getId_ente_convenz().parse() != null) {
            if (idAmbienteEnteConvenzTotal != null && !idAmbienteEnteConvenzTotal.isEmpty()) {
                OrgVRicEnteConvenzTableBean ricEnteConvenz = entiConvenzionatiEjb
                        .getOrgVRicEnteConvenzCollegAbilTableBean(BigDecimal.valueOf(getUser().getIdUtente()),
                                idAmbienteEnteConvenzTotal, ConstOrgEnteSiam.TiEnteConvenz.CONSERVATORE,
                                ConstOrgEnteSiam.TiEnteConvenz.AMMINISTRATORE);
                mappa.populatedMap(ricEnteConvenz, "id_ente_convenz", "nm_ente_convenz");
            }
        } else {
            if (idAmbienteEnteConvenzTotal != null && !idAmbienteEnteConvenzTotal.isEmpty()) {
                OrgVRicEnteConvenzTableBean ricEnteConvenz = entiConvenzionatiEjb.getOrgVRicEnteConvenzAbilTableBean(
                        BigDecimal.valueOf(getUser().getIdUtente()), idAmbienteEnteConvenzTotal, null);
                mappa.populatedMap(ricEnteConvenz, "id_ente_convenz", "nm_ente_convenz");
            } else {
                OrgVRicEnteNonConvenzTableBean ricEnteNonConvenz = entiConvenzionatiEjb
                        .getOrgVRicEnteNonConvenzAbilTableBean(BigDecimal.valueOf(getUser().getIdUtente()), null);
                mappa.populatedMap(ricEnteNonConvenz, "id_ente_siam", "nm_ente_siam");
            }
        }
        getForm().getFiltriUtenti().getId_ente_convenz_appart().setDecodeMap(mappa);
        redirectToAjax(getForm().getFiltriUtenti().asJSON());
    }

    @Override
    public JSONObject triggerFiltriUtentiId_ambiente_ente_convenz_abilOnTrigger() throws EMFError {
        getForm().getFiltriUtenti().post(getRequest());
        BigDecimal idAmbienteEnteconvenz = getForm().getFiltriUtenti().getId_ambiente_ente_convenz_abil().parse();
        // Utilizzo la stessa vista sia che idAmbienteEnteConvenz sia valorizzato, sia che non lo sia
        OrgVRicEnteConvenzTableBean ricEnteConvenz = entiConvenzionatiEjb.getOrgVRicEnteConvenzAbilTableBean(
                BigDecimal.valueOf(getUser().getIdUtente()), idAmbienteEnteconvenz, null);
        DecodeMap mappa = new DecodeMap();
        mappa.populatedMap(ricEnteConvenz, "id_ente_convenz", "nm_ente_convenz");
        getForm().getFiltriUtenti().getId_ente_convenz_abil().setDecodeMap(mappa);
        return getForm().getFiltriUtenti().asJSON();
    }

    @Override
    public JSONObject triggerFiltriUtentiId_ambiente_ente_convenz_richOnTrigger() throws EMFError {
        getForm().getFiltriUtenti().post(getRequest());
        BigDecimal idAmbienteEnteconvenz = getForm().getFiltriUtenti().getId_ambiente_ente_convenz_rich().parse();
        DecodeMap mappa = new DecodeMap();
        if (idAmbienteEnteconvenz != null) {
            OrgVRicEnteConvenzTableBean ricEnteConvenz = entiConvenzionatiEjb.getOrgVRicEnteConvenzAbilTableBean(
                    BigDecimal.valueOf(getUser().getIdUtente()), idAmbienteEnteconvenz, null);
            mappa.populatedMap(ricEnteConvenz, "id_ente_convenz", "nm_ente_convenz");
        } else {
            OrgVRicEnteNonConvenzTableBean ricEnteNonConvenz = entiConvenzionatiEjb
                    .getOrgVRicEnteNonConvenzAbilTableBean(BigDecimal.valueOf(getUser().getIdUtente()), null);
            mappa.populatedMap(ricEnteNonConvenz, "id_ente_siam", "nm_ente_siam");
        }
        getForm().getFiltriUtenti().getId_ente_convenz_rich().setDecodeMap(mappa);
        return getForm().getFiltriUtenti().asJSON();
    }

    @Override
    public JSONObject triggerDettaglioUtenteId_amb_ente_convenz_appartOnTrigger() throws EMFError {
        getForm().getDettaglioUtente().post(getRequest());
        BigDecimal idAmbienteEnteConvenzAppart = getForm().getDettaglioUtente().getId_amb_ente_convenz_appart().parse();
        BigDecimal idUserIam = getForm().getDettaglioUtente().getId_user_iam().parse();
        BigDecimal idRichGestUser = getForm().getRichiestaWizard().getId_rich_gest_user().parse();
        /* SONO IN MODIFICA */
        if (idUserIam != null) {
            // MEV #19104: se non e’ definita una richiesta per modificare l’utente e se la tipologia utente =
            // NON_DI_SISTEMA e se tale utente e’ stato creato senza richiesta
            if (idRichGestUser == null
                    && getForm().getDettaglioUtente().getTipo_user().parse().equals("NON_DI_SISTEMA")) {
                // Combo ente
                DecodeMap mappa = new DecodeMap();
                if (idAmbienteEnteConvenzAppart != null) {
                    OrgVRicEnteConvenzTableBean ricEnteConvenz = entiConvenzionatiEjb
                            .getOrgVRicEnteConvenzAbilTableBean(BigDecimal.valueOf(getUser().getIdUtente()),
                                    idAmbienteEnteConvenzAppart, null);
                    mappa.populatedMap(ricEnteConvenz, "id_ente_convenz", "nm_ente_convenz");
                } else {
                    OrgVRicEnteNonConvenzTableBean ricEnteNonConvenz = entiConvenzionatiEjb
                            .getOrgVRicEnteNonConvenzAbilTableBean(BigDecimal.valueOf(getUser().getIdUtente()), null);
                    mappa.populatedMap(ricEnteNonConvenz, "id_ente_siam", "nm_ente_siam");
                }
                getForm().getDettaglioUtente().getId_ente_siam_appart().setDecodeMap(mappa);
            } // MEV #19324
            else if (idRichGestUser != null && amministrazioneUtentiEjb.existsRichiestaUtenteAzione(idRichGestUser,
                    idUserIam,
                    ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ENTE_APPARTENENZA.getDescrizione())) {
                BaseTable entiTB = new BaseTable();
                // POPOLAMENTO ENTE DI APPARTENENZA UTENTE
                entiTB = entiConvenzionatiEjb.getEntiDaUsrVLisEntiModifAppEnteTableBean(idRichGestUser,
                        getUser().getIdUtente(), idUserIam, idAmbienteEnteConvenzAppart);
                getForm().getDettaglioUtente().getId_ente_siam_appart()
                        .setDecodeMap(DecodeMap.Factory.newInstance(entiTB, "id_ente_siam", "nm_ente_siam"));
                getForm().getDettaglioUtente().getId_ente_siam_appart().setEditMode();
            }
        } /* SONO IN INSERIMENTO */ else {
            if (idAmbienteEnteConvenzAppart != null) {
                // Combo ente convenzionato
                OrgVRicEnteConvenzTableBean ricEnteConvenz = entiConvenzionatiEjb.getOrgVRicEnteConvenzAbilTableBean(
                        BigDecimal.valueOf(getUser().getIdUtente()), idAmbienteEnteConvenzAppart, null);
                getForm().getDettaglioUtente().getId_ente_siam_appart().setDecodeMap(
                        DecodeMap.Factory.newInstance(ricEnteConvenz, "id_ente_convenz", "nm_ente_convenz"));
            } else {
                // Combo ente non convenzionato
                OrgVRicEnteNonConvenzTableBean ricEnteNonConvenz = entiConvenzionatiEjb
                        .getOrgVRicEnteNonConvenzAbilTableBean(BigDecimal.valueOf(getUser().getIdUtente()), null);
                getForm().getDettaglioUtente().getId_ente_siam_appart()
                        .setDecodeMap(DecodeMap.Factory.newInstance(ricEnteNonConvenz, "id_ente_siam", "nm_ente_siam"));
            }
        }
        return getForm().getDettaglioUtente().asJSON();
    }

    @Override
    public JSONObject triggerDettaglioUtenteId_ente_siam_appartOnTrigger() throws EMFError {
        getForm().getDettaglioUtente().post(getRequest());
        BigDecimal idEnte = getForm().getDettaglioUtente().getId_ente_siam_appart().parse();
        String tipoUtente = getForm().getDettaglioUtente().getTipo_user().parse();
        try {
            if (idEnte != null) {
                OrgEnteSiamRowBean ente = entiConvenzionatiEjb.getOrgEnteConvenzRowBean(idEnte);
                setFlagUtente(ente.getTiEnte(), ente.getTiEnteNonConvenz(), tipoUtente);
            }
        } catch (ParerUserError ex) {
            getMessageBox().addError(ex.getDescription());
        }

        return getForm().getDettaglioUtente().asJSON();
    }

    private void azzeraListeDichiarazioniAbilitazioni() {
        // Azzero la lista delle abilitazioni alle organizzazioni
        if (getForm().getDichAbilOrgList().getTable() != null) {
            UsrDichAbilOrganizTableBean tabellaOrg = (UsrDichAbilOrganizTableBean) getForm().getDichAbilOrgList()
                    .getTable();
            for (UsrDichAbilOrganizRowBean currentRow : tabellaOrg) {
                int rowIndex = 0;
                Set<BigDecimal> organizDeleteList = getDichOrganizDeleteList();
                BigDecimal idApplic = currentRow.getBigDecimal("id_applic");
                String applicazione = currentRow.getString("nm_applic");
                if (currentRow.getIdDichAbilOrganiz() != null) {
                    organizDeleteList.add(currentRow.getIdDichAbilOrganiz());
                    getSession().setAttribute("dichOrganizDeleteList", organizDeleteList);

                    if (idApplic != null) {
                        Set<BigDecimal> applicationsEditList = getApplicationsEditList();
                        applicationsEditList.add(idApplic);
                        getSession().setAttribute("applicationsEditList", applicationsEditList);
                    }
                }
                // getForm().getDichAbilOrgList().getTable().remove(rowIndex);
                Set<PairAuth> scopoSet = getScopoOrgSet(applicazione);
                if (currentRow.getTiScopoDichAbilOrganiz().equals(ActionEnums.ScopoDichAbilOrganiz.ALL_ORG.name())) {
                    scopoSet.remove(new PairAuth(currentRow.getTiScopoDichAbilOrganiz(), BigDecimal.ZERO));
                } else {
                    scopoSet.remove(new PairAuth(currentRow.getTiScopoDichAbilOrganiz(), currentRow.getIdOrganizIam()));
                }
                getSession().setAttribute("scopoOrgSet_" + applicazione, scopoSet);
                rowIndex++;
            }
        }

        // Azzero la lista delle abilitazioni ai tipi dato
        if (getForm().getDichAbilTipiDatoList().getTable() != null) {
            UsrDichAbilDatiTableBean tabellaDati = (UsrDichAbilDatiTableBean) getForm().getDichAbilTipiDatoList()
                    .getTable();
            for (UsrDichAbilDatiRowBean currentRowDati : tabellaDati) {
                int rowIndex = 0;
                Set<BigDecimal> tipiDatoDeleteList = getDichTipiDatoDeleteList();
                BigDecimal idClasseTipoDato = currentRowDati.getIdClasseTipoDato();
                BigDecimal idApplic = currentRowDati.getBigDecimal("id_applic");
                String applicazione = currentRowDati.getString("nm_applic");
                if (currentRowDati.getIdDichAbilDati() != null) {
                    tipiDatoDeleteList.add(currentRowDati.getIdDichAbilDati());
                    getSession().setAttribute("dichTipiDatoDeleteList", tipiDatoDeleteList);

                    if (idApplic != null) {
                        Set<BigDecimal> applicationsEditList = getApplicationsEditList();
                        applicationsEditList.add(idApplic);
                        getSession().setAttribute("applicationsEditList", applicationsEditList);
                    }
                }
                // getForm().getDichAbilTipiDatoList().getTable().remove(rowIndex);
                Set<PairAuth> scopoSet = getScopoTipiDatoSet(applicazione);
                if (currentRowDati.getTiScopoDichAbilDati().equals(ActionEnums.ScopoDichAbilDato.ALL_ORG.name())) {
                    scopoSet.remove(new PairAuth(idClasseTipoDato,
                            new PairAuth(currentRowDati.getTiScopoDichAbilDati(), BigDecimal.ZERO)));
                } else {
                    scopoSet.remove(new PairAuth(idClasseTipoDato,
                            new PairAuth(currentRowDati.getTiScopoDichAbilDati(), currentRowDati.getIdOrganizIam())));
                }
                getSession().setAttribute("scopoTipiDatoSet_" + applicazione, scopoSet);
                rowIndex++;
            }
        }

        // Azzero la lista delle abilitazioni agli enti convenzionati
        if (getForm().getDichAbilEnteConvenzList().getTable() != null) {
            UsrDichAbilEnteConvenzTableBean tabellaEnte = (UsrDichAbilEnteConvenzTableBean) getForm()
                    .getDichAbilEnteConvenzList().getTable();
            for (UsrDichAbilEnteConvenzRowBean currentRowEnte : tabellaEnte) {
                int rowIndex = 0;
                BigDecimal idAmbienteEnteConvenz = currentRowEnte.getIdAmbienteEnteConvenz();
                BigDecimal idEnteConvenz = currentRowEnte.getIdEnteConvenz();
                Set<BigDecimal> enteConvenzDeleteList = getDichAbilEnteConvenzDeleteList();
                if (currentRowEnte.getIdDichAbilEnteConvenz() != null) {
                    enteConvenzDeleteList.add(currentRowEnte.getIdDichAbilEnteConvenz());
                    getSession().setAttribute("dichAbilEnteConvenzDeleteList", enteConvenzDeleteList);
                }
                // getForm().getDichAbilEnteConvenzList().getTable().remove(rowIndex);
                Set<PairAuth> scopoSet = getScopoEnteConvenzSet();
                if (currentRowEnte.getTiScopoDichAbilEnte()
                        .equals(ActionEnums.ScopoDichAbilEnteConvenz.ALL_AMBIENTI.name())) {
                    scopoSet.remove(new PairAuth(currentRowEnte.getTiScopoDichAbilEnte(),
                            new PairAuth(BigDecimal.ZERO, BigDecimal.ZERO)));
                } else if (currentRowEnte.getTiScopoDichAbilEnte()
                        .equals(ActionEnums.ScopoDichAbilEnteConvenz.UN_AMBIENTE.name())) {
                    scopoSet.remove(new PairAuth(currentRowEnte.getTiScopoDichAbilEnte(),
                            new PairAuth(idAmbienteEnteConvenz, BigDecimal.ZERO)));
                } else {
                    scopoSet.remove(new PairAuth(currentRowEnte.getTiScopoDichAbilEnte(),
                            new PairAuth(BigDecimal.ZERO, idEnteConvenz)));
                    scopoSet.remove(new PairAuth(currentRowEnte.getTiScopoDichAbilEnte(),
                            new PairAuth(idAmbienteEnteConvenz, idEnteConvenz)));
                }
                getSession().setAttribute("scopoEnteConvenzSet", scopoSet);
                rowIndex++;
            }
        }

        getForm().getDichAbilOrgList().getTable().clear();
        getForm().getDichAbilTipiDatoList().getTable().clear();
        getForm().getDichAbilEnteConvenzList().getTable().clear();
    }

    @Override
    public void scaricaIndirizziMail() throws EMFError {
        Set<String> data = new TreeSet<>();
        if (getForm().getListaUtenti().getTable() != null) {
            for (UsrVLisUserRowBean utente : (UsrVLisUserTableBean) getForm().getListaUtenti().getTable()) {
                String eMails = utente.getDsEmail();
                String[] eMailsArray = eMails.split(";");
                for (String eEmail : eMailsArray) {
                    data.add(eEmail.trim());
                }
            }
        }

        StringBuilder builder = new StringBuilder();
        for (String obj : data) {
            builder.append(obj);
            builder.append("\n");
        }

        if (builder.length() > 0) {
            try {
                getResponse().setContentType("text/csv");
                getResponse().setHeader("Content-Disposition", "attachment; filename=indirizziMailRicercaUtenti.csv");

                OutputStream out = getServletOutputStream();
                out.write(builder.toString().getBytes());

                out.flush();
                out.close();

            } catch (EMFError | IOException e) {
                throw new EMFError("Errore durante il recupero delle email utente", e);
            } finally {
                freeze();
            }
        } else {
            getMessageBox()
                    .addWarning("Nessun file prodotto in quanto dalla ricerca non sono stati ricavati indirizzi mail");
            forwardToPublisher(Application.Publisher.RICERCA_UTENTI);
        }
    }

    /*
     * @Override public JSONObject triggerDettaglioUtenteTipo_authOnTrigger() throws EMFError { DettaglioUtente d =
     * getForm().getDettaglioUtente(); d.post(getRequest()); String tipoAuth = d.getTipo_auth().parse(); if (tipoAuth !=
     * null && !tipoAuth.isEmpty() && tipoAuth.equals(ApplEnum.TipoAuth.AUTH_CERT.name())) {
     * d.getDt_ini_cert().setReadonly(false); d.getDt_fin_cert().setReadonly(false);
     * d.getDl_cert_client().setReadonly(false); } else { d.getDt_ini_cert().clear(); d.getDt_fin_cert().clear();
     * d.getDl_cert_client().clear(); d.getDt_ini_cert().setReadonly(true); d.getDt_fin_cert().setReadonly(true);
     * d.getDl_cert_client().setReadonly(true); } return getForm().getDettaglioUtente().asJSON(); }
     */
    @Override
    public void inviaMailAttivazione() throws EMFError {
        String username = getForm().getDettaglioUtente().getNm_userid().parse();
        AmministrazioneUtentiEjb.KeycloakMessageSent mex = amministrazioneUtentiEjb.inviaMailAttivazione(username);
        if (mex.getTipoMessaggio().equals(AmministrazioneUtentiEjb.KeycloakMessageSent.TIPO_INFO)) {
            getMessageBox().addMessage(MessageLevel.INF, mex.getMessaggio());
        } else {
            getMessageBox().addMessage(MessageLevel.WAR, mex.getMessaggio());
        }
        forwardToPublisher(getLastPublisher());
    }

    @Override
    public void ricercaJobSchedulati() throws EMFError {
        getForm().getFiltriJobSchedulati().getRicercaJobSchedulati().setDisableHourGlass(true);
        FiltriJobSchedulati filtri = getForm().getFiltriJobSchedulati();

        // Esegue la post dei filtri compilati
        if (getSession().getAttribute(FROM_GESTIONE_JOB) != null) {
            getSession().removeAttribute(FROM_GESTIONE_JOB);
        } else {
            filtri.post(getRequest());
        }

        // Valida i filtri per verificare quelli obbligatori
        if (filtri.validate(getMessageBox())) {
            // Valida in maniera piÃ¹ specifica i dati
            String nmJob = filtri.getNm_job().parse();
            Date datada = filtri.getDt_reg_log_job_da().parse();
            Date dataa = filtri.getDt_reg_log_job_a().parse();
            BigDecimal oreda = filtri.getOre_dt_reg_log_job_da().parse();
            BigDecimal orea = filtri.getOre_dt_reg_log_job_a().parse();
            BigDecimal minutida = filtri.getMinuti_dt_reg_log_job_da().parse();
            BigDecimal minutia = filtri.getMinuti_dt_reg_log_job_a().parse();
            String descrizioneDataDa = filtri.getDt_reg_log_job_da().getHtmlDescription();
            String descrizioneDataA = filtri.getDt_reg_log_job_a().getHtmlDescription();

            // Valida i campi di ricerca
            AmministrazioneUtentiValidator validator = new AmministrazioneUtentiValidator(getMessageBox());
            Date[] dateValidate = validator.validaDate(datada, oreda, minutida, dataa, orea, minutia, descrizioneDataDa,
                    descrizioneDataA);

            if (!getMessageBox().hasError()) {
                // Setta la lista dei job in base ai filtri di ricerca
                getForm().getJobSchedulatiList()
                        .setTable(amministrazioneUtentiHelper.getMonVLisSchedJobViewBean(filtri, dateValidate));

                getForm().getJobSchedulatiList().getTable().setPageSize(10);
                // Workaround in modo che la lista punti al primo record, non all'ultimo
                getForm().getJobSchedulatiList().getTable().first();

                // Setto i campi di "Stato Job"
                setStatoJob(nmJob);

            }
        }
        forwardToPublisher(Application.Publisher.SCHEDULAZIONI_JOB_LIST);
    }

    @Override
    public void pulisciJobSchedulati() throws EMFError {
        try {
            this.schedulazioniJob();
        } catch (Exception ex) {
            log.error(ECCEZIONE_MSG, ex);
        }
    }

    @Secure(action = "Menu.Monitoraggio.SchedulazioniJob")
    public void schedulazioniJob() throws EMFError {
        getUser().getMenu().reset();
        getUser().getMenu().select("Menu.Monitoraggio.SchedulazioniJob");
        getForm().getFiltriJobSchedulati().reset();
        getForm().getJobSchedulatiList().setTable(null);
        getForm().getStatoJob().reset();
        populateFiltriJob();
        getForm().getFiltriJobSchedulati().setEditMode();
        // Resetto i valori delle label
        getForm().getInformazioniJob().reset();
        getForm().getInformazioniJob().setViewMode();
        getForm().getInformazioniJob().post(getRequest());
        getForm().getFiltriJobSchedulati().post(getRequest());
        String nmJob = getForm().getFiltriJobSchedulati().getNm_job().parse();
        if (nmJob != null) {
            setStatoJob(nmJob);
        }

        forwardToPublisher(Application.Publisher.SCHEDULAZIONI_JOB_LIST);
    }

    private void populateFiltriJob() {
        getForm().getFiltriJobSchedulati().getNm_job().setDecodeMap(
                ComboGetter.getMappaSortedGenericEnum("nm_job", Constants.NomiJob.getComboSchedulazioniJob()));

        // Inserisco il valore di default nel campo data
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT);
        getForm().getFiltriJobSchedulati().getDt_reg_log_job_da().setValue(df.format(cal.getTime()));
    }

    public void ricercaJobSchedulatiDaGestioneJob() throws EMFError {
        // String nmJob = getForm().getFiltriJobSchedulati().getNm_job().parse();
        // triggerFiltriJobSchedulatiNm_jobOnTrigger();
        ricercaJobSchedulati();
    }

    @Override
    public void gestioneJobPage() throws EMFError {
        GestioneJobForm form = new GestioneJobForm();
        form.getGestioneJobRicercaFiltri().setEditMode();
        form.getGestioneJobRicercaFiltri().reset();
        form.getGestioneJobRicercaList().setTable(null);
        BaseTable ambitoTableBean = gestioneJobEjb.getAmbitoJob();
        form.getGestioneJobRicercaFiltri().getNm_ambito()
                .setDecodeMap(DecodeMap.Factory.newInstance(ambitoTableBean, "nm_ambito", "nm_ambito"));
        form.getGestioneJobRicercaFiltri().getTi_stato_job().setDecodeMap(ComboGetter.getMappaTiStatoJob());
        getForm().getFiltriJobSchedulati().post(getRequest());
        String nmJob = getForm().getFiltriJobSchedulati().getNm_job().parse();
        if (nmJob != null) {
            String dsJob = gestioneJobEjb.getDsJob(nmJob);
            form.getGestioneJobRicercaFiltri().getDs_job().setValue(dsJob);
        }
        getSession().setAttribute("backToSchedulazioniJob", true);
        form.getGestioneJobRicercaFiltri().setEditMode();
        redirectToAction(Application.Actions.GESTIONE_JOB, "?operation=gestioneJobDaRicercaJobSchedulatiAction", form);

    }

    @Override
    public void startJobSchedulati() throws EMFError {
        // Eseguo lo start del job interessato
        getForm().getFiltriJobSchedulati().post(getRequest());
        String nmJob = getForm().getFiltriJobSchedulati().getNm_job().parse();
        if (nmJob != null) {
            String dsJob = gestioneJobEjb.getDsJob(nmJob);
            startGestioneJobOperation(nmJob, dsJob);
        } else {
            getMessageBox().addWarning(WARNING_NO_JOB_SELEZIONATO);
            forwardToPublisher(getLastPublisher());
        }
    }

    @Override
    public void stopJobSchedulati() throws EMFError {
        // Eseguo lo start del job interessato
        getForm().getFiltriJobSchedulati().post(getRequest());
        String nmJob = getForm().getFiltriJobSchedulati().getNm_job().parse();
        if (nmJob != null) {
            String dsJob = gestioneJobEjb.getDsJob(nmJob);
            stopGestioneJobOperation(nmJob, dsJob);
        } else {
            getMessageBox().addWarning(WARNING_NO_JOB_SELEZIONATO);
            forwardToPublisher(getLastPublisher());
        }
    }

    @Override
    public void esecuzioneSingolaJobSchedulati() throws EMFError {
        // Eseguo lo start del job interessato
        getForm().getFiltriJobSchedulati().post(getRequest());
        String nmJob = getForm().getFiltriJobSchedulati().getNm_job().parse();
        if (nmJob != null) {
            String dsJob = gestioneJobEjb.getDsJob(nmJob);
            esecuzioneSingolaGestioneJobOperation(nmJob, dsJob);
        } else {
            getMessageBox().addWarning(WARNING_NO_JOB_SELEZIONATO);
            forwardToPublisher(getLastPublisher());
        }
    }

    public void startGestioneJobOperation(String nmJob, String dsJob) throws EMFError {
        // Se il JOB è di tipo NO_TIMER in ogni caso il tasto di START va inibito
        if (gestioneJobEjb.isNoTimerJob(nmJob)) {
            getMessageBox().addWarning(
                    "Attenzione: si sta tentando di schedulare un JOB di tipo NO_TIMER. Operazione non consentita");
            forwardToPublisher(getLastPublisher());
        } else {
            eseguiNuovo(nmJob, dsJob, null, OPERAZIONE.START);
            setStatoJob(nmJob);
        }
    }

    public void stopGestioneJobOperation(String nmJob, String dsJob) throws EMFError {
        // Se il JOB è di tipo NO_TIMER in ogni caso il tasto di STOP va inibito
        if (gestioneJobEjb.isNoTimerJob(nmJob)) {
            getMessageBox().addWarning(
                    "Attenzione: si sta tentando di stoppare un JOB di tipo NO_TIMER. Operazione non consentita");
            forwardToPublisher(getLastPublisher());
        } else {
            eseguiNuovo(nmJob, dsJob, null, OPERAZIONE.STOP);
            setStatoJob(nmJob);
        }
    }

    public void esecuzioneSingolaGestioneJobOperation(String nmJob, String dsJob) throws EMFError {
        eseguiNuovo(nmJob, dsJob, null, OPERAZIONE.ESECUZIONE_SINGOLA);
        setStatoJob(nmJob);
    }

    private Timestamp getActivationDateJob(String jobName) {
        Timestamp res = null;
        LogVVisLastSchedRowBean rb = gestioneJobHelper.getLogVVisLastSched(jobName);

        if (rb.getFlJobAttivo() != null) {
            if (rb.getFlJobAttivo().equals("1")) {
                res = rb.getDtRegLogJobIni();
            }
        }

        return res;
    }

    private void setStatoJob(String nmJob) {
        Timestamp dataAttivazioneJob = getActivationDateJob(nmJob);
        StatoJob job = new StatoJob(nmJob, getForm().getInformazioniJob().getFl_data_accurata(),
                getForm().getInformazioniJob().getStartJobSchedulati(),
                getForm().getInformazioniJob().getEsecuzioneSingolaJobSchedulati(),
                getForm().getInformazioniJob().getStopJobSchedulati(),
                getForm().getInformazioniJob().getDt_prossima_attivazione(), getForm().getInformazioniJob().getAttivo(),
                getForm().getInformazioniJob().getDt_reg_log_job_ini(), dataAttivazioneJob);

        gestisciStatoJobNuovo(job);
        forwardToPublisher(Application.Publisher.SCHEDULAZIONI_JOB_LIST);
    }

    private void gestisciStatoJobNuovo(StatoJob statoJob) {
        // se non è ancora passato un minuto da quando è stato premuto un pulsante non posso fare nulla
        boolean operazioneInCorso = jbossTimerEjb.isEsecuzioneInCorso(statoJob.getNomeJob());

        statoJob.getFlagDataAccurata().setViewMode();
        statoJob.getFlagDataAccurata().setValue("L'operazione richiesta verrà effettuata entro il prossimo minuto.");
        statoJob.getFlagDataAccurata().setHidden(!operazioneInCorso);

        // Posso operare sulla pagina
        Date nextActivation = jbossTimerEjb.getDataProssimaAttivazione(statoJob.getNomeJob());
        boolean dataAccurata = jbossTimerEjb.isDataProssimaAttivazioneAccurata(statoJob.getNomeJob());
        DateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        /*
         * Se il job è già schedulato o in esecuzione singola nascondo il pulsante Start/esecuzione singola, mostro Stop
         * e visualizzo la prossima attivazione. Viceversa se è fermo mostro Start e nascondo Stop
         */
        if (nextActivation != null) {
            statoJob.getStart().setViewMode();
            statoJob.getEsecuzioneSingola().setViewMode();
            statoJob.getStop().setEditMode();
            statoJob.getStart().setHidden(true);
            statoJob.getEsecuzioneSingola().setHidden(true);
            statoJob.getStop().setHidden(false);
            statoJob.getDataProssimaAttivazione().setValue(formato.format(nextActivation));
        } else {
            statoJob.getStart().setEditMode();
            statoJob.getEsecuzioneSingola().setEditMode();
            statoJob.getStop().setViewMode();
            statoJob.getStart().setHidden(false);
            statoJob.getEsecuzioneSingola().setHidden(false);
            statoJob.getStop().setHidden(true);
            statoJob.getDataProssimaAttivazione().setValue(null);
        }

        boolean flagHidden = nextActivation == null || dataAccurata;
        // se la data c'è ma non è accurata non visualizzare la "data prossima attivazione"
        statoJob.getDataProssimaAttivazione().setHidden(!flagHidden);

        if (statoJob.getDataAttivazione() != null) {
            statoJob.getCheckAttivo().setChecked(true);
            statoJob.getDataRegistrazioneJob()
                    .setValue(formato.format(new Date(statoJob.getDataAttivazione().getTime())));
        } else {
            statoJob.getCheckAttivo().setChecked(false);
            statoJob.getDataRegistrazioneJob().setValue(null);
        }

        // Se il JOB è di tipo NO_TIMER in ogni caso il tasto di START va inibito
        if (gestioneJobEjb.isNoTimerJob(statoJob.getNomeJob())) {
            statoJob.getStart().setViewMode();
            statoJob.getStart().setHidden(true);
        }

    }

    private enum OPERAZIONE {
        START("lancio il timer"), ESECUZIONE_SINGOLA("esecuzione singola"), STOP("stop");

        protected String desc;

        OPERAZIONE(String desc) {
            this.desc = desc;
        }

        public String description() {
            return desc;
        }
    }

    private void eseguiNuovo(String nomeJob, String descrizioneJob, String nomeApplicazione, OPERAZIONE operazione) {
        // Messaggio sul logger di sistema
        StringBuilder info = new StringBuilder(descrizioneJob);
        info.append(": ").append(operazione.description()).append(" [").append(nomeJob);
        if (nomeApplicazione != null) {
            info.append("_").append(nomeApplicazione);
        }
        info.append("]");
        log.info(info.toString());

        String message = "Errore durante la schedulazione del job";

        switch (operazione) {
        case START:
            jbossTimerEjb.start(nomeJob, null);
            message = descrizioneJob
                    + ": job correttamente schedulato. L'operazione richiesta verrà schedulata correttamente entro il prossimo minuto.";
            break;
        case ESECUZIONE_SINGOLA:
            jbossTimerEjb.esecuzioneSingola(nomeJob, null);
            message = descrizioneJob
                    + ": job correttamente schedulato per esecuzione singola. L'operazione richiesta verrà effettuata entro il prossimo minuto.";
            break;
        case STOP:
            jbossTimerEjb.stop(nomeJob);
            message = descrizioneJob
                    + ": schedulazione job annullata. L'operazione richiesta diventerà effettiva entro il prossimo minuto.";
            break;
        }

        // Segnalo l'avvenuta operazione sul job
        getMessageBox().addMessage(new Message(MessageLevel.INF, message));
        getMessageBox().setViewMode(ViewMode.plain);
    }

    // <editor-fold defaultstate="collapsed" desc="Classe che mappa lo stato dei job">
    /**
     * Astrazione dei componenti della pagina Schedulazioni Job
     *
     */
    public static final class StatoJob {

        private final String nomeJob;
        private final Input<String> flagDataAccurata;
        private final Button<String> start;
        private final Button<String> esecuzioneSingola;
        private final Button<String> stop;
        private final Input<Timestamp> dataProssimaAttivazione;
        private final CheckBox<String> checkAttivo;
        private final Input<Timestamp> dataRegistrazioneJob;
        private final Timestamp dataAttivazione;

        // Mi serve per evitare una null pointer Exception
        private static final Button<String> NULL_BUTTON = new Button<>(null, "EMPTY_BUTTON", "Pulsante vuoto", null,
                null, null, false, true, true, false);

        public StatoJob(String nomeJob, Input<String> flagDataAccurata, Button<String> start,
                Button<String> esecuzioneSingola, Button<String> stop, Input<Timestamp> dataProssimaAttivazione,
                CheckBox<String> checkAttivo, Input<Timestamp> dataRegistrazioneJob, Timestamp dataAttivazione) {
            this.nomeJob = nomeJob;
            this.flagDataAccurata = flagDataAccurata;
            this.start = start;
            this.esecuzioneSingola = esecuzioneSingola;
            this.stop = stop;
            this.dataProssimaAttivazione = dataProssimaAttivazione;
            this.checkAttivo = checkAttivo;
            this.dataRegistrazioneJob = dataRegistrazioneJob;
            this.dataAttivazione = dataAttivazione;
        }

        public String getNomeJob() {
            return nomeJob;
        }

        public Input<String> getFlagDataAccurata() {
            return flagDataAccurata;
        }

        public Button<String> getStart() {
            if (start == null) {
                return NULL_BUTTON;
            }
            return start;
        }

        public Button<String> getEsecuzioneSingola() {
            return esecuzioneSingola;
        }

        public Button<String> getStop() {
            if (stop == null) {
                return NULL_BUTTON;
            }
            return stop;
        }

        public Input<Timestamp> getDataProssimaAttivazione() {
            return dataProssimaAttivazione;
        }

        public CheckBox<String> getCheckAttivo() {
            return checkAttivo;
        }

        public Input<Timestamp> getDataRegistrazioneJob() {
            return dataRegistrazioneJob;
        }

        public Timestamp getDataAttivazione() {
            return dataAttivazione;
        }
    }
    // </editor-fold>
}
