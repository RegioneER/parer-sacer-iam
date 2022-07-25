package it.eng.saceriam.ws.replicaOrganizzazione.utils;

import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.web.util.Constants;
import it.eng.saceriam.web.util.Constants.EsitoServizio;
import it.eng.saceriam.ws.dto.IRispostaWS.SeverityEnum;
import it.eng.saceriam.ws.dto.RispostaControlli;
import it.eng.saceriam.ws.ejb.ControlliWS;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.InserimentoOrganizzazioneExt;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.RispostaWSInserimentoOrganizzazione;
import it.eng.saceriam.ws.utils.MessaggiWSBundle;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Gilioli_P
 */
public class InserimentoOrganizzazioneCheck {

    private static final Logger log = LoggerFactory.getLogger(InserimentoOrganizzazioneCheck.class);

    InserimentoOrganizzazioneExt inserimentoOrganizzazioneExt;
    RispostaWSInserimentoOrganizzazione rispostaWs;
    private RispostaControlli rispostaControlli;
    ControlliWS controlliWS = null;

    public InserimentoOrganizzazioneCheck(InserimentoOrganizzazioneExt inserimentoOrganizzazioneExt,
            RispostaWSInserimentoOrganizzazione rispostaWs) {
        this.inserimentoOrganizzazioneExt = inserimentoOrganizzazioneExt;
        this.rispostaWs = rispostaWs;
        this.rispostaControlli = new RispostaControlli();

        try {
            controlliWS = (ControlliWS) new InitialContext().lookup("java:module/ControlliWS");
        } catch (NamingException ex) {
            rispostaWs.setSeverity(SeverityEnum.ERROR);
            rispostaWs.setErrorCode(MessaggiWSBundle.ERR_666);
            String msg = MessaggiWSBundle.getString(MessaggiWSBundle.ERR_666, ex.getMessage());
            rispostaWs.setErrorMessage(msg);
            rispostaWs.getInserimentoOrganizzazioneRisposta().setCdEsito(Constants.EsitoServizio.KO);
            rispostaWs.getInserimentoOrganizzazioneRisposta().setCdErr(MessaggiWSBundle.ERR_666);
            rispostaWs.getInserimentoOrganizzazioneRisposta().setDsErr(msg);
            log.error("Errore nel recupero dell'EJB dei controlli generici ", ex);
        }
    }

    public RispostaWSInserimentoOrganizzazione getRispostaWs() {
        return rispostaWs;
    }

    private void setRispostaWsError(SeverityEnum sev, EsitoServizio esito) {
        rispostaWs.setSeverity(sev);
        rispostaWs.setErrorCode(rispostaControlli.getCodErr());
        rispostaWs.setErrorMessage(rispostaControlli.getDsErr());
        rispostaWs.getInserimentoOrganizzazioneRisposta().setCdEsito(esito);
        rispostaWs.getInserimentoOrganizzazioneRisposta().setCdErr(rispostaControlli.getCodErr());
        rispostaWs.getInserimentoOrganizzazioneRisposta().setDsErr(rispostaControlli.getDsErr());
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
                inserimentoOrganizzazioneExt.setIdApplic(idApplic);
            }
        }

        // Verifica Organizzazione
        if (rispostaWs.getSeverity() != SeverityEnum.ERROR) {
            rispostaControlli.reset();
            rispostaControlli = controlliWS.verificaEsistenzaOrganizzazione(
                    inserimentoOrganizzazioneExt.getInserimentoOrganizzazioneInput().getNmApplic(),
                    inserimentoOrganizzazioneExt.getInserimentoOrganizzazioneInput().getIdOrganizApplic(),
                    inserimentoOrganizzazioneExt.getInserimentoOrganizzazioneInput().getNmTipoOrganiz());
            if (rispostaControlli.isrBoolean()) {
                if (rispostaControlli.getCodErr() == null) {
                    rispostaControlli.setCodErr(MessaggiWSBundle.SERVIZI_ORG_002);
                    rispostaControlli.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.SERVIZI_ORG_002,
                            inserimentoOrganizzazioneExt.getInserimentoOrganizzazioneInput().getIdOrganizApplic()));
                    setRispostaWsError(SeverityEnum.ERROR, Constants.EsitoServizio.KO);
                } else {
                    // Errore 666
                    setRispostaWsError(SeverityEnum.ERROR, Constants.EsitoServizio.KO);
                }
            }
        }

        // Verifica Organizzazione Padre
        if (rispostaWs.getSeverity() != SeverityEnum.ERROR) {
            // Se l'idOrganizApplicPadre (non obbligatorio) è != null, allora significa che deve avere il papi
            // (perchè se ad esempio fosse un AMBIENTE non servirebbe), dunque verifico
            if (inserimentoOrganizzazioneExt.getInserimentoOrganizzazioneInput().getIdOrganizApplicPadre() != null) {
                rispostaControlli.reset();
                rispostaControlli = controlliWS.verificaEsistenzaOrganizzazione(
                        inserimentoOrganizzazioneExt.getInserimentoOrganizzazioneInput().getNmApplic(),
                        inserimentoOrganizzazioneExt.getInserimentoOrganizzazioneInput().getIdOrganizApplicPadre(),
                        inserimentoOrganizzazioneExt.getInserimentoOrganizzazioneInput().getNmTipoOrganizPadre());
                if (!rispostaControlli.isrBoolean()) {
                    if (rispostaControlli.getCodErr() == null) {
                        rispostaControlli.setCodErr(MessaggiWSBundle.SERVIZI_ORG_003);
                        rispostaControlli.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.SERVIZI_ORG_003,
                                inserimentoOrganizzazioneExt.getInserimentoOrganizzazioneInput()
                                        .getIdOrganizApplicPadre()));
                        setRispostaWsError(SeverityEnum.ERROR, Constants.EsitoServizio.KO);
                    } else {
                        // Errore 666
                        setRispostaWsError(SeverityEnum.ERROR, Constants.EsitoServizio.KO);
                    }
                }
            }
        }

        // Verifica tipo di organizzazione
        if (rispostaWs.getSeverity() != SeverityEnum.ERROR) {
            rispostaControlli.reset();
            rispostaControlli = controlliWS.verificaNomeTipoOrganizzazione(
                    inserimentoOrganizzazioneExt.getInserimentoOrganizzazioneInput().getNmApplic(),
                    inserimentoOrganizzazioneExt.getInserimentoOrganizzazioneInput().getNmTipoOrganiz());
            if (!rispostaControlli.isrBoolean()) {
                if (rispostaControlli.getCodErr() == null) {
                    rispostaControlli.setCodErr(MessaggiWSBundle.SERVIZI_ORG_004);
                    rispostaControlli.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.SERVIZI_ORG_004));
                    setRispostaWsError(SeverityEnum.ERROR, Constants.EsitoServizio.KO);
                } else {
                    // Errore 666
                    setRispostaWsError(SeverityEnum.ERROR, Constants.EsitoServizio.KO);
                }
            }
        }

        // Verifica tipo di dato
        if (rispostaWs.getSeverity() != SeverityEnum.ERROR) {
            rispostaControlli.reset();
            rispostaControlli = controlliWS.verificaNomeTipoDato(
                    inserimentoOrganizzazioneExt.getInserimentoOrganizzazioneInput().getListaTipiDato(),
                    inserimentoOrganizzazioneExt.getInserimentoOrganizzazioneInput().getNmApplic());
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
