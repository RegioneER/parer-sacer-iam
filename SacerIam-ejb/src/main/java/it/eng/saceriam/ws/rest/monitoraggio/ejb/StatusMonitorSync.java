/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.ws.rest.monitoraggio.ejb;

import it.eng.saceriam.ws.dto.IRispostaWS;
import it.eng.saceriam.ws.dto.RispostaControlli;
import it.eng.saceriam.ws.rest.ejb.ControlliRestWS;
import it.eng.saceriam.ws.rest.monitoraggio.dto.RispostaWSStatusMonitor;
import it.eng.saceriam.ws.rest.monitoraggio.dto.StatusMonExt;
import it.eng.saceriam.ws.rest.monitoraggio.dto.rmonitor.HostMonitor;
import it.eng.saceriam.ws.utils.MessaggiWSBundle;
import it.eng.spagoLite.security.User;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author fioravanti_f
 */
@Stateless(mappedName = "StatusMonitorSync")
@LocalBean
public class StatusMonitorSync {

    @EJB
    ControlliRestWS myControlliWs;

    @EJB
    StatusMonitorGen statusMonitorGen;

    private static final Logger log = LoggerFactory.getLogger(StatusMonitorSync.class);

    public void initRispostaWs(RispostaWSStatusMonitor rispostaWs, StatusMonExt mon) {
        log.debug("sono nel metodo init");
        HostMonitor myEsito = new HostMonitor();

        rispostaWs.setSeverity(IRispostaWS.SeverityEnum.OK);
        rispostaWs.setErrorCode("");
        rispostaWs.setErrorMessage("");

        rispostaWs.setIstanzaEsito(myEsito);
        myEsito.setVersione(mon.getDescrizione().getVersione());

        // questo codice è identico al suo omologo in SACER
    }

    public void verificaCredenziali(String loginName, String password, String indirizzoIp,
            RispostaWSStatusMonitor rispostaWs, StatusMonExt mon) {
        RispostaControlli tmpRispostaControlli = null;

        // verifica le credenziali e se sono abilitato a chiamare questa funzione
        tmpRispostaControlli = myControlliWs.checkCredenzialiEAuth(loginName, password, indirizzoIp,
                mon.getDescrizione());
        if (!tmpRispostaControlli.isrBoolean()) {
            rispostaWs.setSeverity(IRispostaWS.SeverityEnum.ERROR);
            rispostaWs.setEsitoWsError(tmpRispostaControlli.getCodErr(), tmpRispostaControlli.getDsErr());
        }

        mon.setLoginName(loginName);
        mon.setUtente((User) tmpRispostaControlli.getrObject());
    }

    public void recuperaStatusGlobale(RispostaWSStatusMonitor rispostaWs, StatusMonExt mon) {

        if (mon.getUtente() == null) {
            rispostaWs.setSeverity(IRispostaWS.SeverityEnum.ERROR);
            rispostaWs.setEsitoWsErrBundle(MessaggiWSBundle.ERR_666, "Errore: l'utente non è autenticato.");
            return;
        }

        try {
            statusMonitorGen.calcolaStatusGlobale(rispostaWs, mon);
        } catch (Exception e) {
            rispostaWs.setSeverity(IRispostaWS.SeverityEnum.ERROR);
            rispostaWs.setEsitoWsErrBundle(MessaggiWSBundle.ERR_666,
                    "Errore dell'EJB nella fase di generazione dello status " + e.getMessage());
            log.error("Errore dell'EJB nella fase di generazione dello status", e);
        }
    }

}
