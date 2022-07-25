package it.eng.saceriam.job.replicaUtenti.ejb;

import it.eng.integriam.client.ws.IAMSoapClients;
import it.eng.integriam.client.ws.reputente.EsitoServizio;
import it.eng.integriam.client.ws.reputente.ListaIndIp;
import it.eng.integriam.client.ws.reputente.ListaOrganizAbil;
import it.eng.integriam.client.ws.reputente.ListaServiziAutor;
import it.eng.integriam.client.ws.reputente.ListaTipiDatoAbil;
import it.eng.integriam.client.ws.reputente.OrganizAbil;
import it.eng.integriam.client.ws.reputente.ReplicaUtente;
import it.eng.integriam.client.ws.reputente.ReplicaUtenteRispostaAbstract;
import it.eng.integriam.client.ws.reputente.TipoDatoAbil;
import it.eng.integriam.client.ws.reputente.Utente;
import it.eng.parer.sacerlog.ejb.SacerLogEjb;
import it.eng.parer.sacerlog.util.LogParam;
import it.eng.saceriam.common.Constants;
import static it.eng.saceriam.common.Constants.NmParamApplic.MAX_USERS_PER_LIST_ON_DB;
import it.eng.saceriam.entity.LogUserDaReplic;
import it.eng.saceriam.entity.UsrUser;
import it.eng.saceriam.entity.UsrUsoUserApplic;
import it.eng.saceriam.entity.constraint.ConstIamParamApplic;
import it.eng.saceriam.exception.ParerInternalError;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.saceriam.job.ejb.JobLogger;
import it.eng.saceriam.job.replicaUtenti.util.UserApplic;
import it.eng.saceriam.job.util.Costanti;
import it.eng.saceriam.util.SacerLogConstants;
import it.eng.saceriam.viewEntity.UsrVAbilDati;
import it.eng.saceriam.viewEntity.UsrVAbilOrganiz;
import it.eng.saceriam.viewEntity.UsrVAllAutor;
import it.eng.saceriam.web.ejb.AmministrazioneUtentiEjb;
import it.eng.saceriam.web.ejb.AuthEjb;
import it.eng.saceriam.web.helper.UserHelper;
import it.eng.saceriam.web.util.ApplEnum;
import it.eng.spagoLite.security.auth.PwdUtil;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Gilioli_P
 */
@Stateless(mappedName = "ReplicaUtentiEjb")
@LocalBean
@Interceptors({ it.eng.saceriam.aop.TransactionInterceptor.class })
public class ReplicaUtentiEjb {

    private static final Logger log = LoggerFactory.getLogger(ReplicaUtentiEjb.class);

    @EJB
    private JobLogger jobLoggerEjb;
    @EJB
    private ReplicaUtentiHelper repHelper;
    @EJB
    private ParamHelper paramHelper;
    @EJB
    private SacerLogEjb sacerLogEjb;
    @EJB
    private AmministrazioneUtentiEjb amministrazioneUtentiEjb;
    @EJB
    private UserHelper userHelper;
    @EJB
    private AuthEjb authEjb;
    @Resource
    private SessionContext context;

    /**
     * Prepara gli utenti da replicare presenti in IAM e chiama i servizi di replica dei diversi applicativi
     *
     * @throws ParerInternalError
     *             errore generico
     * @throws ParerUserError
     *             errore generico
     */
    public void replicaUtenti() throws ParerInternalError, ParerUserError {
        log.info("Eseguo la disattivazione degli utenti attivi la cui password \u00E8 scaduta da pi\u00F9 di 3 mesi");
        List<Long> users = repHelper.getUsersDaDisattivareList();

        /*
         * Codice aggiuntivo per il logging...
         */
        LogParam param = new LogParam();
        param.setNomeApplicazione(paramHelper.getParamApplicApplicationName());
        param.setNomeUtente("Job Replica utenti");
        param.setNomeTipoOggetto(SacerLogConstants.TIPO_OGGETTO_UTENTE);
        param.setNomeComponenteSoftware("REPLICA_UTENTI");
        // MEV #26627: Modifica alla replica utenti per non disattivare gli utenti che accedono utilizzando SPID
        param.setNomeAzione("Reset automatico password");

        for (Long idUserIam : users) {
            // MEV #26627: Modifica alla replica utenti per non disattivare gli utenti che accedono utilizzando SPID
            context.getBusinessObject(ReplicaUtentiEjb.class).resetPasswordAutomaticoUtente(param, idUserIam);
        }

        log.info("Ottengo il numero di metodi asincroni 'attivabili'");
        List<Integer> asyncIndexes = new ArrayList<>();

        Integer maxAsyncJobs = Integer
                .parseInt(paramHelper.getValoreParamApplic(ConstIamParamApplic.NmParamApplic.MAX_ASYNC_JOBS.name(),
                        null, null, Constants.TipoIamVGetValAppart.APPLIC));
        Integer minUsersPerList = Integer
                .parseInt(paramHelper.getValoreParamApplic(ConstIamParamApplic.NmParamApplic.MIN_USERS_PER_LIST.name(),
                        null, null, Constants.TipoIamVGetValAppart.APPLIC));
        Integer maxUsersPerListOnDB = Integer.parseInt(
                paramHelper.getValoreParamApplic(ConstIamParamApplic.NmParamApplic.MAX_USERS_PER_LIST_ON_DB.name(),
                        null, null, Constants.TipoIamVGetValAppart.APPLIC));

        // A priori attivo i metodi asincroni fino al numero massimo, poi vado a togliere quelli bloccati (se ce ne
        // sono)
        for (int index = 0; index < maxAsyncJobs; index++) {
            asyncIndexes.add(index);
        }
        // Ricavo i job attivi, quelli cioè che NON sono terminati con FINE_SCHEDULAZIONE o ERRORE
        List<String> asyncJobNames = repHelper.getFreeAsyncJobs(Constants.NomiJob.REPLICA_UTENTI.name() + "_", "1");
        if (!asyncJobNames.isEmpty()) {
            for (String asyncJob : asyncJobNames) {
                int index = Integer.parseInt(asyncJob.substring(asyncJob.lastIndexOf("_") + 1)) - 1;
                // Rimuovo i job ancora attivi tra quelli potenzialmente utilizzabili
                asyncIndexes.remove(Integer.valueOf(index));
            }
        }
        log.info("Sono attivabili " + asyncIndexes.size() + " metodi");
        List<BigDecimal> utentiDaReplicare = new ArrayList<>();
        if (!asyncIndexes.isEmpty()) {

            log.info("Replica Utenti - sblocco automatico degli utenti di repliche bloccate");
            // Prima di iniziare, sblocco automaticamente eventuali utenti "bloccati" in fase di repliche precedenti
            int replicheAggiornate = amministrazioneUtentiEjb.sbloccaReplicheBloccate(ApplEnum.NmJob.REPLICA_UTENTI,
                    true);
            log.info("Replica Utenti - sono stati sbloccati automaticamente " + replicheAggiornate
                    + " utenti da replicare nuovamente");

            log.info(
                    "Ottengo gli utenti da replicare per ogni applicazione, e ordino la collezione in base al numero di utenti per applicazione");
            utentiDaReplicare = repHelper.getUsersDaReplicList();
            log.info("Replica Utenti - ottenuti " + utentiDaReplicare.size() + " utenti da replicare");
            if (!utentiDaReplicare.isEmpty()) {
                log.info(
                        "Replica Utenti - gestisco fino a un numero di utenti = N. ASYNC * Max Users Per List per non sovraccaricare il db = "
                                + asyncIndexes.size() + "*" + maxUsersPerListOnDB);
                List<BigDecimal> tmp;
                if (utentiDaReplicare.size() > (maxUsersPerListOnDB * asyncIndexes.size())) {
                    tmp = utentiDaReplicare.subList(0, maxUsersPerListOnDB * asyncIndexes.size());
                    utentiDaReplicare = tmp;
                }
            }
        }
        String replicaFinalMsg = null;
        if (!utentiDaReplicare.isEmpty()) {
            if (!asyncIndexes.isEmpty()) {
                if (utentiDaReplicare.size() > minUsersPerList) {
                    int utentiPerAsync = (utentiDaReplicare.size() + asyncIndexes.size() - 1) / asyncIndexes.size();
                    if (utentiPerAsync > maxUsersPerListOnDB) {
                        utentiPerAsync = maxUsersPerListOnDB;
                        log.info("Replica Utenti - Dato che gli utenti sono pi\u00F9 di " + MAX_USERS_PER_LIST_ON_DB
                                + ", per ogni metodo asincrono gestir\u00F2 questo numero di utenti per non sovraccaricare il db");
                    }
                    for (int index = 0; index < asyncIndexes.size(); index++) {
                        int from = index * utentiPerAsync;
                        int to = (index + 1) * utentiPerAsync;
                        if (utentiDaReplicare.size() < to) {
                            to = utentiDaReplicare.size();
                        }
                        // Scrivo la riga di log e imposto i record a quella fk
                        if (from != to && from < utentiDaReplicare.size()) {
                            Long idLogJob = context.getBusinessObject(ReplicaUtentiEjb.class)
                                    .updateLogUserDaReplicFKeys(utentiDaReplicare.subList(from, to),
                                            maxUsersPerListOnDB, asyncIndexes.get(index) + 1);
                            if (idLogJob != null) {
                                context.getBusinessObject(ReplicaUtentiEjb.class).replicaUtentiAsync(
                                        asyncIndexes.get(index) + 1, utentiDaReplicare.subList(from, to),
                                        maxUsersPerListOnDB, idLogJob);
                            }
                        }
                    }
                } else {
                    Long idLogJob = context.getBusinessObject(ReplicaUtentiEjb.class).updateLogUserDaReplicFKeys(
                            utentiDaReplicare, maxUsersPerListOnDB, asyncIndexes.get(0) + 1);
                    if (idLogJob != null) {
                        context.getBusinessObject(ReplicaUtentiEjb.class).replicaUtentiAsync(asyncIndexes.get(0) + 1,
                                utentiDaReplicare, maxUsersPerListOnDB, idLogJob);
                    }
                }
            } else {
                replicaFinalMsg = "Job terminato in quanto non \u00E8 possibile avviare nuovi metodi asincroni";
            }
        }
        /* Scrivo nel log del job l'esito finale */
        jobLoggerEjb.writeAtomicLog(Constants.NomiJob.REPLICA_UTENTI, Constants.TipiRegLogJob.FINE_SCHEDULAZIONE,
                replicaFinalMsg);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long updateLogUserDaReplicFKeys(List<BigDecimal> users, int maxSize, int asyncId) {
        Long idLogJob = jobLoggerEjb.writeLog(Constants.NomiJob.REPLICA_UTENTI + "_" + asyncId,
                Constants.TipiRegLogJob.INIZIO_SCHEDULAZIONE, null);
        if (idLogJob == null) {
            log.error("REPLICA UTENTI --- ERRORE INASPETTATO - Impossibile scrivere la riga di log in LogJob");
        } else {
            int replicheAggiornate = updateLogUserDaReplic(users, maxSize, idLogJob);
            log.info(Constants.NomiJob.REPLICA_UTENTI + "_" + asyncId + " - Aggiornati " + replicheAggiornate
                    + " record di replica con l'idLogJob " + idLogJob.toString());
        }
        return idLogJob;
    }

    private int updateLogUserDaReplic(List<BigDecimal> users, int maxSize, Long idLogJob) {
        return recursiveLogUserDaReplicUpdate(users, maxSize, idLogJob);
    }

    private int recursiveLogUserDaReplicUpdate(List<BigDecimal> users, int maxSize, Long idLogJob) {
        int updatedLogs = 0;
        if (users.size() > maxSize) {
            updatedLogs += recursiveLogUserDaReplicUpdate(users.subList(0, maxSize), maxSize, idLogJob);
            updatedLogs += recursiveLogUserDaReplicUpdate(users.subList(maxSize, users.size()), maxSize, idLogJob);
        } else if (!users.isEmpty()) {
            updatedLogs = repHelper.updateLogUserDaReplicFkLog(users, idLogJob);
        }
        return updatedLogs;
    }

    @Asynchronous
    public void replicaUtentiAsync(int asyncId, List<BigDecimal> users, int maxUsersPerListOnDB, Long idLogJob) {
        if (users != null && !users.isEmpty()) {
            try {
                log.info(ReplicaUtentiEjb.class.getSimpleName()
                        + " --- INIZIO chiamata asincrona per replica utenti N. " + asyncId);
                Set<String> applicErroreSet = context.getBusinessObject(ReplicaUtentiEjb.class).gestioneRepliche(users,
                        idLogJob);

                /* Scrivo nel log del job l'esito finale */
                if (applicErroreSet.isEmpty()) {
                    jobLoggerEjb.writeAtomicLog(Constants.NomiJob.REPLICA_UTENTI + "_" + asyncId,
                            Constants.TipiRegLogJob.FINE_SCHEDULAZIONE, null);
                } else {
                    jobLoggerEjb.writeAtomicLog(Constants.NomiJob.REPLICA_UTENTI + "_" + asyncId,
                            Constants.TipiRegLogJob.ERRORE, applicErroreSet.toString());
                }
                log.info(ReplicaUtentiEjb.class.getSimpleName() + " --- FINE chiamata asincrona per replica utenti N. "
                        + asyncId);
            } catch (Exception e) {
                String messaggio = "Eccezione imprevista durante la fase di replica utenti asincrona:";
                messaggio += ExceptionUtils.getRootCauseMessage(e);
                log.error(messaggio, e);
                jobLoggerEjb.writeAtomicLog(Constants.NomiJob.REPLICA_UTENTI + "_" + asyncId,
                        Constants.TipiRegLogJob.ERRORE, messaggio);
            }
        }
    }

    /**
     * Permette di sovrascrivere i valori relativi al timeout della chiamata al ws SOAP. Attualmente sovrascrive tutti i
     * valori già sovrascritti su :
     * {@link IAMSoapClients#replicaUtenteClient(java.lang.String, java.lang.String, java.lang.String) } Al momento non
     * è utilizzata.
     *
     * @param wsClient
     *            client del WS SOAP
     * @param newTimeout
     *            nuovo valore del timeout
     */
    private void changeRequestTimeout(BindingProvider wsClient, int newTimeout) {
        final String[] timeoutProperties = { "com.sun.xml.internal.ws.connect.timeout",
                "com.sun.xml.internal.ws.request.timeout", "com.sun.xml.ws.request.timeout",
                "com.sun.xml.ws.connect.timeout", "javax.xml.ws.client.connectionTimeout",
                "javax.xml.ws.client.receiveTimeout" };

        Map<String, Object> requestContext = wsClient.getRequestContext();

        for (Entry<String, Object> entry : requestContext.entrySet()) {
            if (Arrays.asList(timeoutProperties).contains(entry.getKey())) {
                requestContext.replace(entry.getKey(), newTimeout);
            }
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Set<String> gestioneRepliche(List<BigDecimal> users, Long idLogJob) {
        /* Tengo traccia delle applicazioni sulle quali i servizi daranno esito negativo */
        Set<String> applicErroreSet = new HashSet<>();
        /* Tengo traccia degli utenti già replicati */
        Set<UserApplic> userGiaReplic = new HashSet<>();
        log.info("Replica Utenti - recupero l'insieme di registrazioni");
        List<LogUserDaReplic> userDaReplicList = repHelper.getLogUserDaReplicList(users, idLogJob);
        log.info("Replica Utenti - ottenute " + userDaReplicList.size() + " repliche per " + users.size()
                + " utenti da replicare");
        UUID uuid = UUID.randomUUID();
        log.info("Replica Utenti - DUMP MEMORY STATUS - UUID : " + uuid.toString() + "\r\n" + dumpMemoryStatus());
        for (LogUserDaReplic userDaReplic : userDaReplicList) {
            try {
                /* Creo l'oggetto contenente la risposta */
                ReplicaUtenteRispostaAbstract resp = new ReplicaUtenteRispostaAbstract();

                /* Classi bean dei parametri in input */
                Utente u = new Utente();
                ListaOrganizAbil listaOrganizAbil = new ListaOrganizAbil();
                ListaIndIp listaIndIp = new ListaIndIp();

                /* Ricavo l'utente */
                UsrUser user = repHelper.findById(UsrUser.class, userDaReplic.getIdUserIam());

                /* Creo il client che si occuperà di chiamare il WS */
                ReplicaUtente client = IAMSoapClients.replicaUtenteClient(
                        userDaReplic.getAplApplic().getNmUserReplicaUser(),
                        userDaReplic.getAplApplic().getCdPswReplicaUser(),
                        userDaReplic.getAplApplic().getDsUrlReplicaUser());

                if (client != null) {
                    /* PREPARAZIONE ATTIVAZIONE SERVIZIO */
                    log.info("Replica Utenti - Preparazione attivazione servizio per l'utente "
                            + userDaReplic.getNmUserid());

                    /* Se tipo operazione è INSERIMENTO o MODIFICA ed esiste l'utente */
                    if (userDaReplic.getTiOperReplic().equals(ApplEnum.TiOperReplic.INS.name())
                            || userDaReplic.getTiOperReplic().equals(ApplEnum.TiOperReplic.MOD.name())) {
                        /*
                         * Se l'utente c'è e non è presente nell'insieme degli utenti già replicati allora posso
                         * chiamare il WS per inserirlo/modificarlo
                         */
                        if (user != null
                                && !userGiaReplic.contains(new UserApplic(userDaReplic.getAplApplic().getIdApplic(),
                                        userDaReplic.getIdUserIam()))) {
                            /*
                             * Ricavo l'insieme dei servizi a cui l'utente è abilitato sia in funzione dei ruoli di
                             * default, sia in funzione dei ruoli dell'organizzazione specificati per la dichiarazione
                             */
                            log.info("Replica Utenti - Inizio recupero servizi abilitati");
                            List<UsrVAllAutor> allServiziWebAutListATutteLeOrg = repHelper.getUsrVAllAutorServiziWeb(
                                    user.getIdUserIam(), userDaReplic.getAplApplic().getIdApplic());
                            log.info("Replica Utenti - Fine recupero servizi abilitati");
                            log.debug("Replica Utenti - DUMP MEMORY STATUS - UUID : " + uuid.toString() + "\r\n"
                                    + dumpMemoryStatus());
                            /*
                             * Ricavo l'insieme delle organizzazioni abilitate con il flag su quale sia l'organizzazione
                             * di default per l'utente
                             */
                            log.info("Replica Utenti - Inizio recupero organizzazioni abilitate");
                            List<UsrVAbilOrganiz> abilOrganizList = repHelper.getUsrVAbilOrganizList(
                                    user.getIdUserIam(), userDaReplic.getAplApplic().getIdApplic());
                            log.info("Replica Utenti - Fine recupero organizzazioni abilitate - ABILITATE "
                                    + abilOrganizList.size() + " ORGANIZZAZIONI");
                            log.debug("Replica Utenti - DUMP MEMORY STATUS - UUID : " + uuid.toString() + "\r\n"
                                    + dumpMemoryStatus());
                            Set<UsrVAllAutor> autorDef = new HashSet<>();
                            Map<BigDecimal, Set<UsrVAllAutor>> autorDichMap = new HashMap<>();
                            // Ricavo tutte le autorizzazioni di tipo DEF
                            // Ricavo tutte le autorizzazioni di tipo DICH per ogni organizzazione
                            for (UsrVAllAutor autor : allServiziWebAutListATutteLeOrg) {
                                switch (autor.getUsrVAllAutorId().getTiUsoRuo()) {
                                case "DEF":
                                    autorDef.add(autor);
                                    break;
                                case "DICH":
                                    Set<UsrVAllAutor> autorSet = autorDichMap.get(autor.getIdOrganizApplic());
                                    if (autorSet == null) {
                                        autorSet = new HashSet<>();
                                        autorDichMap.put(autor.getIdOrganizApplic(), autorSet);
                                    }
                                    autorSet.add(autor);
                                    break;
                                }
                            }

                            /* PER OGNI ORGANIZZAZIONE ABILITATA */
                            log.debug("Replica Utenti - DUMP MEMORY STATUS - UUID : " + uuid.toString() + "\r\n"
                                    + dumpMemoryStatus());
                            log.info("Replica Utenti - Inizio ciclo recupero tipi dato abilitati");
                            /* Determino l'insieme dei tipi di dato abilitati per l'utente */
                            Map<BigDecimal, List<TipoDatoAbil>> abilDatiMap = organizeAbilDati(
                                    repHelper.getUsrVAbilDatiList(user.getIdUserIam(),
                                            userDaReplic.getAplApplic().getIdApplic()));
                            log.debug("Replica Utenti - DUMP MEMORY STATUS - UUID : " + uuid.toString() + "\r\n"
                                    + dumpMemoryStatus());
                            for (UsrVAbilOrganiz abilOrganiz : abilOrganizList) {
                                OrganizAbil oa = new OrganizAbil();
                                BigDecimal idUsoUserApplic = repHelper.getIdUsoUserApplic(user.getIdUserIam(),
                                        userDaReplic.getAplApplic().getIdApplic());
                                BigDecimal idOrganizIam = abilOrganiz.getUsrVAbilOrganizId().getIdOrganizIam();
                                BigDecimal idOrganizApplic = abilOrganiz.getIdOrganizApplic();
                                String tiScopo = null;
                                if (idOrganizIam != null) {
                                    List<String> scopi = repHelper.getTiScopoDichAbilOrganiz(idUsoUserApplic,
                                            idOrganizIam);
                                    if (scopi != null && !scopi.isEmpty()) {
                                        tiScopo = scopi.get(0);
                                    }
                                }

                                /*
                                 * Aggiungo l’organizzazione alla lista delle organizzazioni abilitate per l’utente,
                                 * riportando l’identificatore dell’organizzazione per l’applicazione
                                 */
                                oa.setIdOrganizApplicAbil(idOrganizApplic.intValue());

                                if (tiScopo != null && tiScopo.equals("ORG_DEFAULT")) {
                                    oa.setFlOrganizDefault(true);
                                } else {
                                    oa.setFlOrganizDefault(false);
                                }

                                Set<UsrVAllAutor> autorDich = autorDichMap.get(idOrganizApplic);
                                if (autorDich != null) {
                                    autorDef.addAll(autorDich);
                                }

                                /*
                                 * Inserisco l'insieme dei servizi a cui l'utente è abilitato in base ai ruoli DEF e
                                 * DICH
                                 */
                                ListaServiziAutor listaServiziAutor = new ListaServiziAutor();
                                for (UsrVAllAutor allServiziWebAut : autorDef) {
                                    listaServiziAutor.getNmServizioAutor().add(allServiziWebAut.getNmAutor());
                                }
                                oa.setListaServiziAutor(listaServiziAutor);

                                ListaTipiDatoAbil listaTipiDatoAbil = new ListaTipiDatoAbil();
                                /* Per ogni tipo dato determinato */
                                List<TipoDatoAbil> abilDatiList = abilDatiMap.remove(idOrganizIam);
                                if (abilDatiList != null) {
                                    listaTipiDatoAbil.getTipoDatoAbilList().addAll(abilDatiList);
                                }
                                oa.setListaTipiDatoAbil(listaTipiDatoAbil);

                                /* Inserisco il record */
                                listaOrganizAbil.getOrganizAbilList().add(oa);
                            }
                            log.info("Replica Utenti - Fine ciclo recupero tipi dato abilitati");

                            /* Copia tutti i parametri dell'utente */
                            u.setIdUserIam(user.getIdUserIam().intValue());
                            u.setNmUserid(user.getNmUserid());
                            u.setCdPsw(user.getCdPsw());
                            u.setNmCognomeUser(user.getNmCognomeUser());
                            u.setNmNomeUser(user.getNmNomeUser());
                            u.setFlAttivo(user.getFlAttivo());
                            u.setFlUserAdmin(user.getFlUserAdmin());
                            u.setFlContrIp(user.getFlContrIp());
                            u.setTipoUser(user.getTipoUser());
                            u.setTipoAuth(user.getTipoAuth());
                            GregorianCalendar c = new GregorianCalendar();
                            c.setTime(user.getDtRegPsw());
                            XMLGregorianCalendar dateXML = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
                            u.setDtRegPsw(dateXML);
                            c.setTime(user.getDtScadPsw());
                            dateXML = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
                            u.setDtScadPsw(dateXML);
                            u.setCdSalt(user.getCdSalt());
                            u.setCdFisc(user.getCdFisc());
                            u.setDsEmail(user.getDsEmail());
                            /* Ricavo l'eventuale lista di indirizzi IP dell'utente */
                            List<String> indIpList = repHelper.getUsrIndIpUserList(user.getIdUserIam());
                            listaIndIp.getIndIp().addAll(indIpList);
                            u.setListaIndIp(listaIndIp);
                            u.setListaOrganizAbil(listaOrganizAbil);

                            ////////////////////////////////////////////////////////
                            /* ATTIVAZIONE SERVIZIO ATTRAVERSO CREDENZIALI ED URL */
                            ////////////////////////////////////////////////////////
                            if (userDaReplic.getTiOperReplic().equals(ApplEnum.TiOperReplic.INS.name())) {
                                log.info("Replica Utenti - Chiamo il ws di Inserimento Utente");
                                resp = client.inserimentoUtente(u);
                            } else {
                                log.info("Replica Utenti - Chiamo il ws di Modifica Utente");
                                resp = client.modificaUtente(u);
                            }

                            // Aggiungo l'utente nella lista di quelli già replicati
                            userGiaReplic.add(new UserApplic(userDaReplic.getAplApplic().getIdApplic(),
                                    userDaReplic.getIdUserIam()));
                        } else {
                            // Se l'utente non esiste oppure è già stato replicato,
                            // la replica è come se fosse andata a buon fine
                            resp.setCdEsito(EsitoServizio.OK);
                        }
                    } /* Se tipo operazione è CANCELLAZIONE */ else {
                        log.info("Replica Utenti - Chiamo il ws di Cancellazione Utente");
                        resp = client.cancellaUtente(userDaReplic.getIdUserIam().intValue());
                    }

                    /* Il sistema verifica la risposta del servizio di replica utente */
                    //
                    /* Se la risposta ha esito positivo */
                    if (resp.getCdEsito().name().equals(Constants.EsitoServizio.OK.name())) {
                        repHelper.writeAtomicLogUserDaReplic(userDaReplic.getIdLogUserDaReplic(),
                                Constants.EsitoServizio.OK, null, null, applicErroreSet);
                        log.info("Replica Utenti - Risposta WS positiva per l'utente " + userDaReplic.getNmUserid());
                    } /* Se la risposta ha dato esito negativo */ else {
                        /* Mi annoto l'applicazione che ha dato problemi */
                        repHelper.writeAtomicLogUserDaReplic(userDaReplic.getIdLogUserDaReplic(),
                                Constants.EsitoServizio.KO, resp.getCdErr(), resp.getDsErr(), applicErroreSet);
                        log.error("Replica Utenti - Risposta WS negativa per l'utente " + userDaReplic.getNmUserid()
                                + " " + resp.getCdErr() + " - " + resp.getDsErr());
                    }

                } else {
                    /* Se il client è null, ci sono stati problemi */
                    repHelper.writeAtomicLogUserDaReplic(userDaReplic.getIdLogUserDaReplic(),
                            Constants.EsitoServizio.KO, Costanti.SERVIZI_USR_001,
                            "Errore nella creazione del client (lato saceriam-integration) per la chiamata al WS di ReplicaUtenti",
                            applicErroreSet);
                    log.error("Replica Utenti - Risposta WS negativa per l'utente " + userDaReplic.getNmUserid() + " "
                            + Costanti.SERVIZI_USR_001);
                    break;
                }

            } catch (SOAPFaultException e) {
                /* Se ricado in questo caso significa che ho avuto un problema sulle autorizzazioni */
                repHelper.writeAtomicLogUserDaReplic(userDaReplic.getIdLogUserDaReplic(), Constants.EsitoServizio.KO,
                        Costanti.SERVIZI_USR_005, e.getFault().getFaultCode() + ": " + e.getFault().getFaultString(),
                        applicErroreSet);
                log.error("Replica Utenti - Risposta WS negativa per l'utente " + userDaReplic.getNmUserid() + " "
                        + Costanti.SERVIZI_USR_005
                        + " - Utente che attiva il servizio non riconosciuto o non abilitato", e);
                break;
            } catch (WebServiceException e) {
                /* Se non risponde, mi annoto l'applicazione che ha dato problemi e scrivo nel log dell'utente */
                repHelper.writeAtomicLogUserDaReplic(userDaReplic.getIdLogUserDaReplic(),
                        Constants.EsitoServizio.NO_RISPOSTA, null, null, applicErroreSet);
                log.error(
                        "Replica Utenti - Risposta WS negativa per l'utente " + userDaReplic.getNmUserid() + " "
                                + Costanti.REPLICA_UTENTE_001 + " - Per l'applicazione "
                                + userDaReplic.getAplApplic().getNmApplic() + " il servizio di replica non risponde",
                        e);
                /* ...esco dal ciclo di userDaReplic e devo passare a considerare l'applicazione successiva */
                break;
            } catch (Exception e) {
                repHelper.writeAtomicLogUserDaReplic(userDaReplic.getIdLogUserDaReplic(), Constants.EsitoServizio.KO,
                        Costanti.SERVIZI_USR_001, ExceptionUtils.getRootCauseMessage(e), applicErroreSet);
                log.error("Replica Utenti - Risposta WS negativa per l'utente " + userDaReplic.getNmUserid() + " "
                        + Costanti.SERVIZI_USR_001, e);
                break;
            }
        } // End userDaReplic
        return applicErroreSet;
    }

    private Map<BigDecimal, List<TipoDatoAbil>> organizeAbilDati(List<UsrVAbilDati> abilDatiList) {
        Map<BigDecimal, List<TipoDatoAbil>> abilDatiMap = new HashMap<>();
        UsrVAbilDati dato;
        for (int index = 0; index < abilDatiList.size(); index++) {
            dato = abilDatiList.get(index);
            List<TipoDatoAbil> abil = abilDatiMap.get(dato.getIdOrganizIam());
            if (abil == null) {
                abil = new ArrayList<>();
                abilDatiMap.put(dato.getIdOrganizIam(), abil);
            }
            TipoDatoAbil tipoDatoAbil = new TipoDatoAbil();
            tipoDatoAbil.setIdTipoDatoApplic(dato.getIdTipoDatoApplic().intValue());
            tipoDatoAbil.setNmClasseTipoDato(dato.getNmClasseTipoDato());

            abil.add(tipoDatoAbil);
            abilDatiList.set(index, null);
        }
        return abilDatiMap;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void resetPasswordAutomaticoUtente(LogParam param, Long idUserIam) {
        UsrUser user = repHelper.findById(UsrUser.class, idUserIam);
        // inizio: MEV #26627: Modifica alla replica utenti per non disattivare gli utenti che accedono utilizzando SPID
        String randomPwd = RandomStringUtils.randomAlphabetic(8);
        byte[] binarySalt = PwdUtil.generateSalt();
        userHelper.resetPwd(idUserIam.longValue(), PwdUtil.encodePBKDF2Password(binarySalt, randomPwd),
                PwdUtil.encodeUFT8Base64String(binarySalt));
        // Registro la password dell'utente nella tabella USR_OLD_PSW
        authEjb.saveOldPsw(user.getNmUserid());
        // fine: MEV #26627: Modifica alla replica utenti per non disattivare gli utenti che accedono utilizzando SPID

        /*
         * Per ogni applicazione usata dall'utente per cui l'applicazione richiede la replica, registro una riga in
         * logUserDaReplic
         */
        List<UsrUsoUserApplic> usrUsoUserApplics = repHelper.getUsrUsoUserApplic(idUserIam);
        for (UsrUsoUserApplic uso : usrUsoUserApplics) {
            repHelper.writeLogUserDaReplic(uso, idUserIam);
        }
        repHelper.flush();
        // Codice aggiuntivo per il logging
        sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                param.getNomeAzione(), param.getNomeTipoOggetto(), new BigDecimal(idUserIam),
                param.getNomeComponenteSoftware());
    }

    /*
     * stampa lo stato della memoria
     *
     * @return
     */
    private static String dumpMemoryStatus() {
        int mb = 1024 * 1024;
        StringBuilder sb = new StringBuilder("");

        // Getting the runtime reference from system
        Runtime runtime = Runtime.getRuntime();

        sb.append("##### Heap utilization statistics [MB] #####");
        sb.append("\r\n");
        // Print used memory
        sb.append("Used Memory:" + (runtime.totalMemory() - runtime.freeMemory()) / mb);
        sb.append("\r\n");
        // Print free memory
        sb.append("Free Memory:" + runtime.freeMemory() / mb);
        sb.append("\r\n");
        // Print total available memory
        sb.append("Total Memory:" + runtime.totalMemory() / mb);
        sb.append("\r\n");
        // Print Maximum available memory
        sb.append("Max Memory:" + runtime.maxMemory() / mb);
        sb.append("\r\n");

        /* Total number of processors or cores available to the JVM */
        sb.append("Available processors (cores): " + Runtime.getRuntime().availableProcessors());
        sb.append("\r\n");

        /* Total amount of free memory available to the JVM */
        sb.append("Free memory (bytes): " + Runtime.getRuntime().freeMemory());
        sb.append("\r\n");

        /* This will return Long.MAX_VALUE if there is no preset limit */
        long maxMemory = Runtime.getRuntime().maxMemory();
        /* Maximum amount of memory the JVM will attempt to use */
        sb.append("Maximum memory (bytes): " + (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory));
        sb.append("\r\n");

        /* Total memory currently available to the JVM */
        sb.append("Total memory available to JVM (bytes): " + Runtime.getRuntime().totalMemory());
        sb.append("\r\n");

        /* Get a list of all filesystem roots on this system */
        File[] roots = File.listRoots();

        /* For each filesystem root, print some info */
        for (File root : roots) {
            sb.append("File system root: " + root.getAbsolutePath());
            sb.append("\r\n");
            sb.append("Total space (bytes): " + root.getTotalSpace());
            sb.append("\r\n");
            sb.append("Free space (bytes): " + root.getFreeSpace());
            sb.append("\r\n");
            sb.append("Usable space (bytes): " + root.getUsableSpace());
            sb.append("\r\n");
        }

        return sb.toString();
    }
}
