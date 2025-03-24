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
import it.eng.saceriam.web.helper.UserHelper;
import it.eng.saceriam.web.util.ApplEnum;
import it.eng.saceriam.web.util.Constants;
import it.eng.saceriam.ws.dto.IRispostaWS;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.InserimentoOrganizzazioneExt;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.InserimentoOrganizzazioneInput;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.InserimentoOrganizzazioneRisposta;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.ListaTipiDato;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.RispostaWSInserimentoOrganizzazione;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.WSDescInserimentoOrganizzazione;
import it.eng.saceriam.ws.replicaOrganizzazione.utils.InserimentoOrganizzazioneCheck;
import it.eng.saceriam.ws.utils.MessaggiWSBundle;
import it.eng.saceriam.ws.utils.WsTransactionManager;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;

/**
 *
 * @author Gilioli_P
 */
@Stateless(mappedName = "InserimentoOrganizzazioneEjb")
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class InserimentoOrganizzazioneEjb {

    @EJB
    private GestioneOrganizzazioneHelper goh;
    @EJB
    private UserHelper uh;
    @Resource
    private UserTransaction utx;
    private static WsTransactionManager wtm;

    @EJB
    private EntiConvenzionatiEjb ecEjb;
    @EJB
    private ParamHelper paramHelper;
    @EJB
    private SacerLogEjb sacerLogEjb;

    public InserimentoOrganizzazioneRisposta inserimentoOrganizzazione(String nmApplic, Integer idOrganizApplic,
            String nmTipoOrganiz, Integer idEnteConserv, Integer idEnteGestore, Integer idOrganizApplicPadre,
            String nmTipoOrganizPadre, String nmOrganiz, String dsOrganiz, Integer idEnteConvenz, Date dtIniVal,
            Date dtFineVal, ListaTipiDato listaTipiDato) {

        // Istanzio il transaction manager
        wtm = new WsTransactionManager(utx);

        // Istanzio la risposta
        RispostaWSInserimentoOrganizzazione rispostaWs = new RispostaWSInserimentoOrganizzazione();
        rispostaWs.setInserimentoOrganizzazioneRisposta(new InserimentoOrganizzazioneRisposta());
        // Imposto l'esito della risposta di default OK
        rispostaWs.getInserimentoOrganizzazioneRisposta().setCdEsito(Constants.EsitoServizio.OK);

        // Istanzio l'oggetto che contiene i parametri ricevuti in input
        InserimentoOrganizzazioneInput ioInput = new InserimentoOrganizzazioneInput(nmApplic, idOrganizApplic,
                nmTipoOrganiz, idEnteConserv, idEnteGestore, idOrganizApplicPadre, nmTipoOrganizPadre, nmOrganiz,
                dsOrganiz, idEnteConvenz, dtIniVal, dtFineVal, listaTipiDato);

        // Istanzio l'Ext con l'oggetto creato e setto i parametri descrizione e quelli in input
        InserimentoOrganizzazioneExt ioExt = new InserimentoOrganizzazioneExt();
        ioExt.setDescrizione(new WSDescInserimentoOrganizzazione());
        ioExt.setInserimentoOrganizzazioneInput(ioInput);

        if (rispostaWs.getSeverity() != IRispostaWS.SeverityEnum.ERROR) {
            // Chiamo la classe InserimentoOrganizzazioneCheck che gestisce i controlli di oggetto
            InserimentoOrganizzazioneCheck checker = new InserimentoOrganizzazioneCheck(ioExt, rispostaWs);
            checker.check(nmApplic);
        }

        if (rispostaWs.getSeverity() != IRispostaWS.SeverityEnum.ERROR) {
            UsrOrganizIam organiz;
            try {
                wtm.beginTrans(rispostaWs);
                // Se i controlli sono andati a buon fine inserisco l'organizzazione...
                organiz = goh.insertOrganizIam(ioExt, rispostaWs);
                // ...e scrivo nel log degli utenti da replicare
                boolean ultimoLivello = goh.isLastLevel(new BigDecimal(ioExt.getIdApplic()),
                        organiz.getAplTipoOrganiz().getNmTipoOrganiz());
                if (ultimoLivello) {
                    Set<BigDecimal> usersToReply = goh.getUsersOnDich(organiz.getIdOrganizIam(),
                            ApplEnum.TiOperReplic.INS);
                    // Per ogni utente determinato
                    for (BigDecimal idUserIam : usersToReply) {
                        goh.registraUtenteDaReplicare(idUserIam, ioExt.getIdApplic(), rispostaWs);
                        uh.aggiungiAbilOrganizToAdd(idUserIam.longValue(), ioExt.getIdApplic());
                        uh.aggiungiAbilDatiToAdd(idUserIam.longValue(), ioExt.getIdApplic(), null);
                    }
                    goh.getEntityManager().flush();
                }

                if (idEnteConvenz != null && dtIniVal != null && dtFineVal != null) {
                    /*
                     * Codice aggiuntivo per il logging...
                     */
                    LogParam param = new LogParam();
                    // LogParam param = null;

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

                    /*
                     * Scrivo nel log dell'ente convenzionato l'inserimento dell'**********UNICA********** nuova
                     * associazione struttura - ente convenzionato
                     */
                    ecEjb.saveAssociazioneEnteStrutturaReq(param, new BigDecimal(idEnteConvenz),
                            new BigDecimal(organiz.getIdOrganizIam()), dtIniVal, dtFineVal);
                }

                // MAC #19526
                if (nmApplic.equals("SACER") && ultimoLivello) {
                    Set<BigDecimal> usersSacer = goh.getUsersSacer(organiz.getIdOrganizIam());
                    for (BigDecimal idUserIam : usersSacer) {
                        goh.registraUtenteDaReplicare(idUserIam, ioExt.getIdApplic(), rispostaWs);
                        List<UsrUser> user = uh.getUsrUserByNmUserid("admin_generale");
                        uh.aggiungiAbilOrganizVigil(user.get(0).getIdUserIam(), idUserIam.longValue());
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

                // Popola la risposta
                rispostaWs.getInserimentoOrganizzazioneRisposta().setNmApplic(ioInput.getNmApplic());
                rispostaWs.getInserimentoOrganizzazioneRisposta().setIdOrganizApplic(ioInput.getIdOrganizApplic());
                rispostaWs.getInserimentoOrganizzazioneRisposta().setNmTipoOrganiz(ioInput.getNmTipoOrganiz());
                rispostaWs.getInserimentoOrganizzazioneRisposta()
                        .setIdOrganizApplicPadre(ioInput.getIdOrganizApplicPadre());
                rispostaWs.getInserimentoOrganizzazioneRisposta()
                        .setNmTipoOrganizPadre(ioInput.getNmTipoOrganizPadre());
                rispostaWs.getInserimentoOrganizzazioneRisposta().setNmOrganiz(ioInput.getNmOrganiz());
                rispostaWs.getInserimentoOrganizzazioneRisposta().setDsOrganiz(ioInput.getDsOrganiz());
                rispostaWs.getInserimentoOrganizzazioneRisposta().setIdEnteConvenz(ioInput.getIdEnteConvenz());
                rispostaWs.getInserimentoOrganizzazioneRisposta().setDtIniVal(ioInput.getDtIniVal());
                rispostaWs.getInserimentoOrganizzazioneRisposta().setDtFineVal(ioInput.getDtFineVal());
                rispostaWs.getInserimentoOrganizzazioneRisposta().setListaTipiDato(ioInput.getListaTipiDato());

                wtm.commit(rispostaWs);
            } catch (TransactionException e) {
                rispostaWs.getInserimentoOrganizzazioneRisposta().setCdEsito(Constants.EsitoServizio.KO);
                rispostaWs.getInserimentoOrganizzazioneRisposta().setCdErr(rispostaWs.getErrorCode());
                rispostaWs.getInserimentoOrganizzazioneRisposta().setDsErr(rispostaWs.getErrorMessage());
                wtm.rollback(rispostaWs);
            } catch (Exception e) {
                rispostaWs.getInserimentoOrganizzazioneRisposta().setCdEsito(Constants.EsitoServizio.KO);
                rispostaWs.getInserimentoOrganizzazioneRisposta().setCdErr(MessaggiWSBundle.ERR_666);
                rispostaWs.getInserimentoOrganizzazioneRisposta()
                        .setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.ERR_666));
                wtm.rollback(rispostaWs);
            }
        }

        // Ritorno la risposta
        return rispostaWs.getInserimentoOrganizzazioneRisposta();
    }
}
