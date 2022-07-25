package it.eng.saceriam.ws.replicaOrganizzazione.ejb;

import it.eng.parer.sacerlog.ejb.SacerLogEjb;
import it.eng.parer.sacerlog.util.LogParam;
import it.eng.saceriam.amministrazioneEntiConvenzionati.ejb.EntiConvenzionatiEjb;
import it.eng.saceriam.entity.OrgEnteSiam;
import it.eng.saceriam.entity.UsrOrganizIam;
import it.eng.saceriam.entity.UsrUser;
import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam;
import it.eng.saceriam.exception.TransactionException;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.saceriam.util.SacerLogConstants;
import it.eng.saceriam.viewEntity.UsrVAbilDatiToAdd;
import it.eng.saceriam.viewEntity.UsrVAbilDatiVigilToAdd;
import it.eng.saceriam.viewEntity.UsrVAbilOrgVigilToAdd;
import it.eng.saceriam.viewEntity.UsrVAbilOrganizToAdd;
import it.eng.saceriam.web.helper.UserHelper;
import it.eng.saceriam.web.util.ApplEnum;
import it.eng.saceriam.web.util.Constants;
import it.eng.saceriam.ws.dto.IRispostaWS;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.ListaTipiDato;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.ModificaOrganizzazioneExt;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.ModificaOrganizzazioneInput;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.ModificaOrganizzazioneRisposta;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.RispostaWSModificaOrganizzazione;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.TipoDato;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.WSDescModificaOrganizzazione;
import it.eng.saceriam.ws.replicaOrganizzazione.utils.ModificaOrganizzazioneCheck;
import it.eng.saceriam.ws.utils.MessaggiWSBundle;
import it.eng.saceriam.ws.utils.WsTransactionManager;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Gilioli_P
 */
@Stateless(mappedName = "ModificaOrganizzazioneEjb")
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class ModificaOrganizzazioneEjb {

    private static final Logger log = LoggerFactory.getLogger(ModificaOrganizzazioneEjb.class);

    @EJB
    private GestioneOrganizzazioneHelper goh;
    @EJB
    private EntiConvenzionatiEjb ecEjb;
    @EJB
    private UserHelper uh;
    @Resource
    private UserTransaction utx;
    private static WsTransactionManager wtm;
    @EJB
    private ParamHelper paramHelper;
    @EJB
    private SacerLogEjb sacerLogEjb;

    public ModificaOrganizzazioneRisposta modificaOrganizzazione(String nmApplic, Integer idOrganizApplic,
            String nmTipoOrganiz, Integer idEnteConserv, Integer idEnteGestore, Integer idOrganizApplicPadre,
            String nmTipoOrganizPadre, String nmOrganiz, String dsOrganiz, Integer idEnteConvenz, Date dtIniVal,
            Date dtFineVal, ListaTipiDato listaTipiDato) {

        // Istanzio il transaction manager
        wtm = new WsTransactionManager(utx);

        // 1° LOG DEBUG
        log.debug("Modifica Organizzazione - Ricezione dei seguenti parametri da " + nmApplic
                + " per modificare l'organizzazione " + nmOrganiz + ": " + "nmApplic = " + nmApplic + ", "
                + "idOrganizApplic = " + idOrganizApplic + ", " + "nmTipoOrganiz = " + nmTipoOrganiz + ", "
                + "idEnteConserv = " + idEnteConserv + ", " + "idEnteGestore = " + idEnteGestore + ", "
                + "idOrganizApplicPadre =" + idOrganizApplicPadre + ", " + "nmTipoOrganizPadre = " + nmTipoOrganizPadre
                + ", " + "nmOrganiz = " + nmOrganiz + ", " + "dsOrganiz = " + dsOrganiz + ", " + "idEnteConvenz = "
                + idEnteConvenz + ", " + "dtIniVal = " + dtIniVal + ", " + "dtFineVal = " + dtFineVal);

        formattaListaTipiDato(listaTipiDato);

        // Istanzio la risposta
        RispostaWSModificaOrganizzazione rispostaWs = new RispostaWSModificaOrganizzazione();
        rispostaWs.setModificaOrganizzazioneRisposta(new ModificaOrganizzazioneRisposta());
        // Imposto l'esito della risposta di default OK
        rispostaWs.getModificaOrganizzazioneRisposta().setCdEsito(Constants.EsitoServizio.OK);

        // Istanzio l'oggetto che contiene i parametri ricevuti in input
        ModificaOrganizzazioneInput moInput = new ModificaOrganizzazioneInput(nmApplic, idOrganizApplic, nmTipoOrganiz,
                idEnteConserv, idEnteGestore, nmOrganiz, dsOrganiz, idOrganizApplicPadre, nmTipoOrganizPadre,
                idEnteConvenz, dtIniVal, dtFineVal, listaTipiDato);

        // Istanzio l'Ext con l'oggetto creato e setto i parametri descrizione e quelli in input
        ModificaOrganizzazioneExt moExt = new ModificaOrganizzazioneExt();
        moExt.setDescrizione(new WSDescModificaOrganizzazione());
        moExt.setModificaOrganizzazioneInput(moInput);

        // Chiamo la classe ModificaOrganizzazioneCheck che gestisce i controlli di oggetto
        if (rispostaWs.getSeverity() != IRispostaWS.SeverityEnum.ERROR) {
            ModificaOrganizzazioneCheck checker = new ModificaOrganizzazioneCheck(moExt, rispostaWs);
            checker.check(nmApplic);
        }

        // Se i controlli sono andati a buon fine...
        if (rispostaWs.getSeverity() != IRispostaWS.SeverityEnum.ERROR) {
            try {
                wtm.beginTrans(rispostaWs);

                Set<BigDecimal> usersToReply = new HashSet<>();
                // Controllo se devo modificare il "padre"
                // ed in caso ricavo gli utenti che hanno abilitazioni su di esso
                if (moExt.getModificaOrganizzazioneInput().getIdOrganizApplicPadre() != null) {
                    if (goh.isDadModified(moExt.getIdApplic(),
                            moExt.getModificaOrganizzazioneInput().getIdOrganizApplic(),
                            moExt.getModificaOrganizzazioneInput().getNmTipoOrganiz(),
                            moExt.getModificaOrganizzazioneInput().getIdOrganizApplicPadre())) {
                        usersToReply = goh.getUsersWithOrganizDadDich(moExt.getIdOrganizIam());
                    }
                }

                // 2° LOG DEBUG
                log.debug("Modifica Organizzazione - Se ho modificato il padre dell'organizzazione (es. la struttura"
                        + "è ora associata ad un altro ente) ricavo la lista degli utenti da replicare. In questo "
                        + "caso ho ricavato i seguenti utenti: " + usersToReply.toString());

                // Modifico l'organizzazione
                int[] numTipiDatoInsEli = goh.updateOrganizIam(moExt, rispostaWs);
                int numTipiDatoInseriti = numTipiDatoInsEli[0];
                int numTipiDatoEliminati = numTipiDatoInsEli[1];

                // 3° LOG DEBUG
                log.debug("Modifica Organizzazione - A seguito della chiamata al WS ho inserito " + numTipiDatoInseriti
                        + " ed eliminato " + numTipiDatoEliminati + " tipi di dato");

                // Se ho inserito/eliminato dei tipi dato
                // aggiungo gli altri eventuali utenti da replicare
                if (numTipiDatoInseriti != 0 || numTipiDatoEliminati != 0) {
                    usersToReply.addAll(goh.getUsersOnDich(moExt.getIdOrganizIam(), ApplEnum.TiOperReplic.MOD));
                }

                // 4° LOG DEBUG
                if (!usersToReply.isEmpty()) {
                    log.debug(
                            "Modifica Organizzazione - Se ho inserito/eliminato tipi dato il totale degli utenti da replicare aggiornato è il seguente: "
                                    + usersToReply.toString());
                }

                // Scrivo gli utenti da replicare nel log
                for (BigDecimal idUserIam : usersToReply) {
                    // 5° LOG DEBUG
                    log.debug("Modifica Organizzazione - Procedo ad elaborare l'utente " + idUserIam);
                    goh.registraUtenteDaReplicare(idUserIam, moExt.getIdApplic(), rispostaWs);
                    log.debug("Modifica Organizzazione - Utente " + idUserIam
                            + " inserito tra gli utenti da replicare in LOG_USER_DA_REPLIC");
                    log.debug("Modifica Organizzazione - Utente " + idUserIam
                            + " procedo con l'inserimento delle abilitazioni alle organizzazioni. Per l'utente sono state ricavate le seguenti abilitazioni da inserire: ");
                    // 6° LOG DEBUG
                    formattaOutputOrganiz(idUserIam.longValue(), moExt.getIdApplic());
                    uh.aggiungiAbilOrganizToAdd(idUserIam.longValue(), moExt.getIdApplic());
                    log.debug("Modifica Organizzazione - Utente " + idUserIam
                            + " procedo con l'inserimento delle abilitazioni ai dati. Per l'utente sono state ricavate le seguenti abilitazioni da inserire: ");
                    // 7° LOG DEBUG
                    formattaOutputDati(idUserIam.longValue(), moExt.getIdApplic());
                    uh.aggiungiAbilDatiToAdd(idUserIam.longValue(), moExt.getIdApplic(), null);
                }

                // Se è un caso di importa/duplica standard (sovrascrittura di un template) devo inserire l'associazione
                // struttura-ente
                if (moExt.isTemplate() && idEnteConvenz != null && dtIniVal != null && dtFineVal != null) {
                    /*
                     * Codice aggiuntivo per il logging...
                     */
                    LogParam param = new LogParam();
                    param.setNomeApplicazione(paramHelper.getParamApplicApplicationName());
                    OrgEnteSiam enteSiam = goh.findById(OrgEnteSiam.class, idEnteConvenz.longValue());
                    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
                    if (enteSiam.getTiEnte().equals(ConstOrgEnteSiam.TiEnteSiam.CONVENZIONATO)) {
                        param.setNomeUtente("Servizio Allinea ente convenzionato");
                        param.setNomeTipoOggetto(SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO);
                        param.setNomeComponenteSoftware("ALLINEA_ENTE_CONVENZIONATO");
                        param.setNomeAzione("Allinea Ente convenzionato per organizzazione");
                    } else {
                        if (enteSiam.getTiEnteNonConvenz().name().equals("FORNITORE_ESTERNO")) {
                            param.setNomeUtente("Servizio Allinea fornitore esterno");
                            param.setNomeTipoOggetto(SacerLogConstants.TIPO_OGGETTO_FORNITORE_ESTERNO);
                            param.setNomeComponenteSoftware("ALLINEA_FORNITORE_ESTERNO");
                            param.setNomeAzione("Allinea Fornitore esterno per organizzazione");
                        } else if (enteSiam.getTiEnteNonConvenz().name().equals("SOGGETTO_ATTUATORE")) {
                            param.setNomeUtente("Servizio Allinea soggetto attuatore");
                            param.setNomeTipoOggetto(SacerLogConstants.TIPO_OGGETTO_SOGGETTO_ATTUATORE);
                            param.setNomeComponenteSoftware("ALLINEA_SOGGETTO_ATTUATORE");
                            param.setNomeAzione("Allinea Soggetto attuatore per organizzazione");
                        }
                    }

                    ecEjb.saveAssociazioneEnteStrutturaReq(param, new BigDecimal(idEnteConvenz),
                            new BigDecimal(moExt.getIdOrganizIam()), dtIniVal, dtFineVal);
                }

                // MAC #19526
                ModificaOrganizzazioneInput parametriInput = moExt.getModificaOrganizzazioneInput();
                UsrOrganizIam organiz = goh.getUsrOrganizIam(moExt.getIdApplic(), parametriInput.getIdOrganizApplic(),
                        parametriInput.getNmTipoOrganiz());
                if (nmApplic.equals("SACER") && numTipiDatoInseriti > 0) {
                    // Ricavo l'organizzazione da modificare
                    Set<BigDecimal> usersSacer = goh.getUsersSacer(organiz.getIdOrganizIam());
                    if (!usersSacer.isEmpty()) {
                        // 8° LOG DEBUG
                        log.debug(
                                "Modifica Organizzazione - Ho ricavato i seguenti utenti per abilitazioni agli organi di vigilanza in quanto ho inserito dei tipi dato: "
                                        + usersSacer.toString());
                    }
                    for (BigDecimal idUserIam : usersSacer) {
                        // 9° LOG DEBUG
                        log.debug("Modifica Organizzazione - Procedo ad elaborare l'utente " + idUserIam);
                        goh.registraUtenteDaReplicare(idUserIam, moExt.getIdApplic(), rispostaWs);
                        log.debug("Modifica Organizzazione - Utente " + idUserIam
                                + " inserito tra gli utenti da replicare in LOG_USER_DA_REPLIC");
                        List<UsrUser> user = uh.getUsrUserByNmUserid("admin_generale");
                        log.debug(
                                "Modifica Organizzazione - Ho ricavato le seguenti abilitazioni agli organi di vigilanza da inserire: ");
                        // 10° LOG DEBUG
                        formattaOutputOrganizVigil(user.get(0).getIdUserIam(), idUserIam.longValue());
                        uh.aggiungiAbilOrganizVigil(user.get(0).getIdUserIam(), idUserIam.longValue());
                        log.debug(
                                "Modifica Organizzazione - Ho ricavato le seguenti abilitazioni ai dati di vigilanza da inserire: ");
                        // 11° LOG DEBUG
                        formattaOutputDatiVigil(user.get(0).getIdUserIam(), idUserIam.longValue());
                        uh.aggiungiAbilDatiVigil(user.get(0).getIdUserIam(), idUserIam.longValue());
                        LogParam param = new LogParam();
                        param.setNomeApplicazione(paramHelper.getParamApplicApplicationName());
                        param.setNomeUtente("Servizio Allinea organizzazione");
                        param.setNomeAzione("Allinea abilitazioni utente alle organizzazioni");
                        param.setNomeTipoOggetto("Utente");
                        param.setIdOggetto(idUserIam);
                        param.setNomeComponenteSoftware("ALLINEA_ORGANIZZAZIONE");
                        sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(),
                                param.getNomeUtente(), param.getNomeAzione(), param.getNomeTipoOggetto(),
                                param.getIdOggetto(), param.getNomeComponenteSoftware());
                    }
                }
                if (nmApplic.equals("SACER") && numTipiDatoEliminati > 0) {
                    // Ricavo l'organizzazione da modificare
                    Set<BigDecimal> usersSacer = goh.getUsersSacer(organiz.getIdOrganizIam());
                    if (!usersSacer.isEmpty()) {
                        // 12° LOG DEBUG
                        log.debug(
                                "Modifica Organizzazione - Ho ricavato i seguenti utenti DA_REPLICARE in quanto ho eliminato dei tipi dato: "
                                        + usersSacer.toString());
                    }
                    for (BigDecimal idUserIam : usersSacer) {
                        goh.registraUtenteDaReplicare(idUserIam, moExt.getIdApplic(), rispostaWs);
                        LogParam param = new LogParam();
                        param.setNomeApplicazione(paramHelper.getParamApplicApplicationName());
                        param.setNomeUtente("Servizio Allinea organizzazione");
                        param.setNomeAzione("Allinea abilitazioni utente alle organizzazioni");
                        param.setNomeTipoOggetto("Utente");
                        param.setIdOggetto(idUserIam);
                        param.setNomeComponenteSoftware("ALLINEA_ORGANIZZAZIONE");
                        sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(),
                                param.getNomeUtente(), param.getNomeAzione(), param.getNomeTipoOggetto(),
                                param.getIdOggetto(), param.getNomeComponenteSoftware());
                    }
                }

                // Popola la risposta
                rispostaWs.getModificaOrganizzazioneRisposta().setNmApplic(moInput.getNmApplic());
                rispostaWs.getModificaOrganizzazioneRisposta().setIdOrganizApplic(moInput.getIdOrganizApplic());
                rispostaWs.getModificaOrganizzazioneRisposta().setNmTipoOrganiz(moInput.getNmTipoOrganiz());
                rispostaWs.getModificaOrganizzazioneRisposta()
                        .setIdOrganizApplicPadre(moInput.getIdOrganizApplicPadre());
                rispostaWs.getModificaOrganizzazioneRisposta().setNmTipoOrganizPadre(moInput.getNmTipoOrganizPadre());
                rispostaWs.getModificaOrganizzazioneRisposta().setNmOrganiz(moInput.getNmOrganiz());
                rispostaWs.getModificaOrganizzazioneRisposta().setDsOrganiz(moInput.getDsOrganiz());
                rispostaWs.getModificaOrganizzazioneRisposta().setIdEnteConvenz(moInput.getIdEnteConvenz());
                rispostaWs.getModificaOrganizzazioneRisposta().setDtIniVal(moInput.getDtIniVal());
                rispostaWs.getModificaOrganizzazioneRisposta().setDtFineVal(moInput.getDtFineVal());

                rispostaWs.getModificaOrganizzazioneRisposta().setListaTipiDato(moInput.getListaTipiDato());

                wtm.commit(rispostaWs);
            } catch (TransactionException e) {
                log.error(e.getMessage(), e);
                rispostaWs.getModificaOrganizzazioneRisposta().setCdEsito(Constants.EsitoServizio.KO);
                rispostaWs.getModificaOrganizzazioneRisposta().setCdErr(rispostaWs.getErrorCode());
                rispostaWs.getModificaOrganizzazioneRisposta().setDsErr(rispostaWs.getErrorMessage());
                wtm.rollback(rispostaWs);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                rispostaWs.getModificaOrganizzazioneRisposta().setCdEsito(Constants.EsitoServizio.KO);
                rispostaWs.getModificaOrganizzazioneRisposta().setCdErr(MessaggiWSBundle.ERR_666);
                rispostaWs.getModificaOrganizzazioneRisposta()
                        .setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.ERR_666));
                wtm.rollback(rispostaWs);
            }
        }

        // Ritorno la risposta
        return rispostaWs.getModificaOrganizzazioneRisposta();
    }

    private void formattaOutputOrganiz(long idUserIam, long idApplic) {
        List<UsrVAbilOrganizToAdd> orgToAddList = uh.getAbilOrganizToAdd(idUserIam, idApplic);
        if (!orgToAddList.isEmpty()) {
            for (UsrVAbilOrganizToAdd orgToAdd : orgToAddList) {
                log.debug("idDichAbilOrganiz = " + orgToAdd.getIdDichAbilOrganiz() + ", " + "idUsoUserApplic = "
                        + orgToAdd.getIdUsoUserApplic() + ", " + "idOrganizIam = "
                        + orgToAdd.getUsrVAbilOrganizToAddId().getIdOrganizIam() + ", " + "nmApplic = "
                        + orgToAdd.getNmApplic() + " ");
            }
        } else {
            log.debug("Nessuna abilitazione alle organizzazioni da inserire");
        }
    }

    private void formattaOutputDati(long idUserIam, long idApplic) {
        List<UsrVAbilDatiToAdd> datiToAddList = uh.getAbilDatiToAdd(idUserIam, idApplic);
        if (!datiToAddList.isEmpty()) {
            for (UsrVAbilDatiToAdd datiToAdd : datiToAddList) {
                log.debug("idDichAbilOrganiz = " + datiToAdd.getIdDichAbilDati() + ", " + "idUsoUserApplic = "
                        + datiToAdd.getIdUsoUserApplic() + ", " + "idTipoDatoIam = "
                        + datiToAdd.getUsrVAbilDatiToAddId().getIdTipoDatoIam() + ", " + "idSuptEstEnteConvenz = "
                        + datiToAdd.getIdSuptEstEnteConvenz() + ", " + "idVigilEnteProdut = "
                        + datiToAdd.getIdVigilEnteProdut() + ", " + "nmApplic = " + datiToAdd.getNmApplic() + " ");
            }
        } else {
            log.debug("Nessuna abilitazione ai dati da inserire");
        }
    }

    private void formattaListaTipiDato(ListaTipiDato listaTipiDato) {
        log.debug("La lista dei tipi dato è invece la seguente: ");
        if (listaTipiDato != null && listaTipiDato.getTipoDato() != null && !listaTipiDato.getTipoDato().isEmpty()) {
            for (TipoDato tipoDato : listaTipiDato) {
                log.debug("idTipoDatoApplic = " + tipoDato.getIdTipoDatoApplic() + ", " + "nmClasseTipoDato = "
                        + tipoDato.getNmClasseTipoDato() + ", " + "nmTipoDato = " + tipoDato.getNmTipoDato());
            }
        }
    }

    private void formattaOutputOrganizVigil(long idUserIamCor, long idUserIamGestito) {
        List<UsrVAbilOrgVigilToAdd> orgVigilToAddList = uh.getAbilOrganizToVigil(idUserIamCor, idUserIamGestito);
        if (!orgVigilToAddList.isEmpty()) {
            for (UsrVAbilOrgVigilToAdd orgVigilToAdd : orgVigilToAddList) {
                log.debug("idUsoUserApplicGestito = " + orgVigilToAdd.getIdUsoUserApplicGestito() + ", "
                        + "idOrganizIamStrut = " + orgVigilToAdd.getUsrVAbilOrgVigilToAddId().getIdOrganizIamStrut()
                        + ", " + "idSuptEstEnteConvenz = " + "flAbilAutomatica = 1, " + "idVigilEnteProdut = "
                        + orgVigilToAdd.getIdVigilEnteProdut() + ", " + "dsCausaleDich = "
                        + orgVigilToAdd.getDsCausaleDich() + " ");
            }
        } else {
            log.debug("Nessuna abilitazione agli organi di vigilanza da inserire");
        }
    }

    private void formattaOutputDatiVigil(long idUserIamCor, long idUserIamGestito) {
        List<UsrVAbilDatiVigilToAdd> datiVigilToAddList = uh.getAbilDatiVigil(idUserIamCor, idUserIamGestito);
        if (!datiVigilToAddList.isEmpty()) {
            for (UsrVAbilDatiVigilToAdd datiVigilToAdd : datiVigilToAddList) {
                log.debug("idUsoUserApplicGestito = " + datiVigilToAdd.getIdUsoUserApplicGestito() + ", "
                        + "idTipoDatoIam = " + datiVigilToAdd.getUsrVAbilDatiVigilToAddId().getIdTipoDatoIam() + ", "
                        + "flAbilAutomatica = 1, " + "idVigilEnteProdut = " + datiVigilToAdd.getIdVigilEnteProdut()
                        + ", " + "dsCausaleDich = " + datiVigilToAdd.getDsCausaleDich() + " ");
            }
        } else {
            log.debug("Nessuna abilitazione ai dati di vigilanza da inserire");
        }
    }

}
