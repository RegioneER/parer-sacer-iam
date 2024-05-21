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

package it.eng.saceriam.ws.replicaOrganizzazione.utils;

import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.web.util.Constants;
import it.eng.saceriam.web.util.Constants.EsitoServizio;
import it.eng.saceriam.ws.dto.IRispostaWS.SeverityEnum;
import it.eng.saceriam.ws.dto.RispostaControlli;
import it.eng.saceriam.ws.ejb.ControlliWS;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.ModificaOrganizzazioneExt;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.RispostaWSModificaOrganizzazione;
import it.eng.saceriam.ws.utils.MessaggiWSBundle;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Gilioli_P
 */
public class ModificaOrganizzazioneCheck {

    private static final Logger log = LoggerFactory.getLogger(ModificaOrganizzazioneCheck.class);

    ModificaOrganizzazioneExt modificaOrganizzazioneExt;
    RispostaWSModificaOrganizzazione rispostaWs;
    private RispostaControlli rispostaControlli;
    ControlliWS controlliWS = null;

    public ModificaOrganizzazioneCheck(ModificaOrganizzazioneExt modificaOrganizzazioneExt,
            RispostaWSModificaOrganizzazione rispostaWs) {
        this.modificaOrganizzazioneExt = modificaOrganizzazioneExt;
        this.rispostaWs = rispostaWs;
        this.rispostaControlli = new RispostaControlli();

        try {
            controlliWS = (ControlliWS) new InitialContext().lookup("java:module/ControlliWS");
        } catch (NamingException ex) {
            rispostaWs.setSeverity(SeverityEnum.ERROR);
            rispostaWs.setErrorCode(MessaggiWSBundle.ERR_666);
            String msg = MessaggiWSBundle.getString(MessaggiWSBundle.ERR_666, ex.getMessage());
            rispostaWs.setErrorMessage(msg);
            rispostaWs.getModificaOrganizzazioneRisposta().setCdEsito(Constants.EsitoServizio.KO);
            rispostaWs.getModificaOrganizzazioneRisposta().setCdErr(MessaggiWSBundle.ERR_666);
            rispostaWs.getModificaOrganizzazioneRisposta().setDsErr(msg);
            log.error("Errore nel recupero dell'EJB dei controlli generici ", ex);
        }
    }

    public RispostaWSModificaOrganizzazione getRispostaWs() {
        return rispostaWs;
    }

    private void setRispostaWsError(SeverityEnum sev, EsitoServizio esito) {
        rispostaWs.setSeverity(sev);
        rispostaWs.setErrorCode(rispostaControlli.getCodErr());
        rispostaWs.setErrorMessage(rispostaControlli.getDsErr());
        rispostaWs.getModificaOrganizzazioneRisposta().setCdEsito(esito);
        rispostaWs.getModificaOrganizzazioneRisposta().setCdErr(rispostaControlli.getCodErr());
        rispostaWs.getModificaOrganizzazioneRisposta().setDsErr(rispostaControlli.getDsErr());
    }

    public void check(String nmApplic) {
        Long idApplic = null;
        // Verifica Applicazione
        if (rispostaWs.getSeverity() != SeverityEnum.ERROR) {
            rispostaControlli.reset();
            rispostaControlli = controlliWS.verificaApplicazione(nmApplic);
            if (!rispostaControlli.isrBoolean()) {
                if (rispostaControlli.getCodErr() == null) {
                    rispostaControlli.setCodErr("Errore");
                    rispostaControlli.setDsErr(nmApplic + " inesistente!");
                    setRispostaWsError(SeverityEnum.ERROR, Constants.EsitoServizio.KO);
                } else {
                    // Errore 666
                    setRispostaWsError(SeverityEnum.ERROR, Constants.EsitoServizio.KO);
                }
            } else {
                // Se l'applicazione esiste, ricavo l'id
                idApplic = ((AplApplic) rispostaControlli.getrObject()).getIdApplic();
                modificaOrganizzazioneExt.setIdApplic(idApplic);
            }
        }

        // Verifica Organizzazione
        if (rispostaWs.getSeverity() != SeverityEnum.ERROR) {
            rispostaControlli.reset();
            rispostaControlli = controlliWS.verificaEsistenzaOrganizzazione(
                    modificaOrganizzazioneExt.getModificaOrganizzazioneInput().getNmApplic(),
                    modificaOrganizzazioneExt.getModificaOrganizzazioneInput().getIdOrganizApplic(),
                    modificaOrganizzazioneExt.getModificaOrganizzazioneInput().getNmTipoOrganiz());
            if (!rispostaControlli.isrBoolean()) {
                if (rispostaControlli.getCodErr() == null) {
                    rispostaControlli.setCodErr(MessaggiWSBundle.SERVIZI_ORG_005);
                    rispostaControlli.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.SERVIZI_ORG_005,
                            modificaOrganizzazioneExt.getModificaOrganizzazioneInput().getIdOrganizApplic()));
                    setRispostaWsError(SeverityEnum.ERROR, Constants.EsitoServizio.KO);
                } else {
                    // Errore 666
                    setRispostaWsError(SeverityEnum.ERROR, Constants.EsitoServizio.KO);
                }
            } else {
                // Setto l'id dell'organizzazione presente
                modificaOrganizzazioneExt.setIdOrganizIam(rispostaControlli.getrLong());
            }
        }

        // Verifica Organizzazione Padre
        if (rispostaWs.getSeverity() != SeverityEnum.ERROR) {
            // Se l'idOrganizApplicPadre (non obbligatorio) è != null, allora significa che deve avere il papi
            // (perchè se ad esempio fosse un AMBIENTE non servirebbe), dunque verifico
            if (modificaOrganizzazioneExt.getModificaOrganizzazioneInput().getIdOrganizApplicPadre() != null) {
                rispostaControlli.reset();
                rispostaControlli = controlliWS.verificaEsistenzaOrganizzazione(
                        modificaOrganizzazioneExt.getModificaOrganizzazioneInput().getNmApplic(),
                        modificaOrganizzazioneExt.getModificaOrganizzazioneInput().getIdOrganizApplicPadre(),
                        modificaOrganizzazioneExt.getModificaOrganizzazioneInput().getNmTipoOrganizPadre());
                if (!rispostaControlli.isrBoolean()) {
                    if (rispostaControlli.getCodErr() == null) {
                        rispostaControlli.setCodErr(MessaggiWSBundle.SERVIZI_ORG_003);
                        rispostaControlli.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.SERVIZI_ORG_003,
                                modificaOrganizzazioneExt.getModificaOrganizzazioneInput().getIdOrganizApplicPadre()));
                        setRispostaWsError(SeverityEnum.ERROR, Constants.EsitoServizio.KO);
                    } else {
                        // Errore 666
                        setRispostaWsError(SeverityEnum.ERROR, Constants.EsitoServizio.KO);
                    }
                } else {
                    // Setto l'id dell'organizzazione padre presente
                    modificaOrganizzazioneExt.setIdOrganizIamPadre(rispostaControlli.getrLong());
                }
            }
        }

        // Verifica tipo di dato
        if (rispostaWs.getSeverity() != SeverityEnum.ERROR) {
            rispostaControlli.reset();
            rispostaControlli = controlliWS.verificaNomeTipoDato(
                    modificaOrganizzazioneExt.getModificaOrganizzazioneInput().getListaTipiDato(),
                    modificaOrganizzazioneExt.getModificaOrganizzazioneInput().getNmApplic());
            if (!rispostaControlli.isrBoolean()) {
                if (rispostaControlli.getCodErr() == null) {
                    rispostaControlli.setCodErr(MessaggiWSBundle.SERVIZI_ORG_006);
                    rispostaControlli.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.SERVIZI_ORG_006));
                    setRispostaWsError(SeverityEnum.ERROR, Constants.EsitoServizio.KO);
                } else {
                    // Errore 666
                    setRispostaWsError(SeverityEnum.ERROR, Constants.EsitoServizio.KO);
                }
            }
        }
    }
}
