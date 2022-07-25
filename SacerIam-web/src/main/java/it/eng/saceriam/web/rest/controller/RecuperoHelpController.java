/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.web.rest.controller;

import it.eng.saceriam.viewEntity.AplVVisHelpOnLine;
import it.eng.saceriam.web.util.Constants;
import it.eng.saceriam.ws.rest.ejb.RecuperoHelpEjb;
import it.eng.saceriam.ws.utils.MessaggiWSBundle;
import it.eng.spagoLite.security.exception.AuthWSException;
import it.eng.spagoLite.security.exception.AuthWSException.CodiceErrore;
import javax.ejb.EJB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Iacolucci_M
 */
@Controller
@RequestMapping("/rest/recuperoHelp.json")
public class RecuperoHelpController {

    private static Logger log = LoggerFactory.getLogger(RecuperoHelpController.class);

    @EJB(mappedName = "java:app/SacerIam-ejb/RecuperoHelpEjb")
    private RecuperoHelpEjb recuperoHelpEjb;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody RecuperoHelpRisposta getHelpInJSON(@RequestParam String nmUserId, @RequestParam String cdPwd,
            @RequestParam String nmApplic, @RequestParam String tiHelpOnLine, @RequestParam String nmPaginaWeb,
            @RequestParam(required = false) String nmEntryMenu) {

        RecuperoHelpRisposta r = new RecuperoHelpRisposta();
        r.setNmApplic(nmApplic);
        r.setNmPaginaWeb(nmPaginaWeb);
        r.setNmEntryMenu(nmEntryMenu);
        r.setNmUserId(nmUserId);
        r.setCdPsw(cdPwd);

        try {

            if (recuperoHelpEjb.appExists(nmApplic)) {
                try {
                    recuperoHelpEjb.loginAndAuth(nmUserId, cdPwd, "RecuperoHelp");
                    AplVVisHelpOnLine help = recuperoHelpEjb.recuperoHelp(nmApplic, tiHelpOnLine, nmPaginaWeb,
                            nmEntryMenu);
                    if (help == null) {
                        r.setCdEsito("KO");
                        if (nmEntryMenu == null || nmEntryMenu.trim().equals("")) {
                            r.setCdErr(MessaggiWSBundle.RECUP_005);
                            r.setDlErr(MessaggiWSBundle.getString(MessaggiWSBundle.RECUP_005));
                        } else {
                            r.setCdErr(MessaggiWSBundle.RECUP_006);
                            r.setDlErr(MessaggiWSBundle.getString(MessaggiWSBundle.RECUP_006));
                        }
                    } else {
                        r.setCdEsito("OK");
                        r.setBlHelp(help.getBlHelpOnLine());
                    }
                } catch (AuthWSException ex) {
                    CodiceErrore err = ex.getCodiceErrore();
                    log.info("Errore Login HelpOnline, codice errore={}", err.name(), ex);
                    r.setCdEsito(Constants.Esito.KO.name());
                    r.setCdErr(MessaggiWSBundle.RECUP_004);
                    r.setDlErr(MessaggiWSBundle.getString(MessaggiWSBundle.RECUP_004));
                }

            } else {
                log.info("Applicazione non censita nel DB.");
                r.setCdEsito(Constants.Esito.KO.name());
                r.setCdErr(MessaggiWSBundle.RECUP_002);
                r.setDlErr(MessaggiWSBundle.getString(MessaggiWSBundle.RECUP_002));
            }

        } catch (Throwable ex) {
            log.error("Errore nel recupero dei dati dell'help", ex);
            r.setCdEsito(Constants.Esito.KO.name());
            r.setCdErr(MessaggiWSBundle.ERR_666);
            r.setDlErr(MessaggiWSBundle.getString(MessaggiWSBundle.ERR_666, ex.getMessage()));
        }

        return r;
    }
}
