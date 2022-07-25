/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.restws.util;

import it.eng.saceriam.ws.dto.IRispostaWS;
import it.eng.saceriam.ws.rest.monitoraggio.dto.MonFakeSessn;
import it.eng.saceriam.ws.utils.MessaggiWSBundle;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author fioravanti_f
 */
public class SimplReqPrsr extends AbsRequestPrsr {

    public static final String LOGINAME_FLD = "username";
    public static final String PASSWORD_FLD = "password";

    public class ReqPrsrConfig {

        MonFakeSessn sessioneFinta;
        private HttpServletRequest request;

        public MonFakeSessn getSessioneFinta() {
            return sessioneFinta;
        }

        public void setSessioneFinta(MonFakeSessn sessioneFinta) {
            this.sessioneFinta = sessioneFinta;
        }

        public HttpServletRequest getRequest() {
            return request;
        }

        public void setRequest(HttpServletRequest request) {
            this.request = request;
        }

    }

    public void parse(IRispostaWS rispostaWs, ReqPrsrConfig configurazione) {
        // verifica della struttura: è un form e devo verificare che
        // i campi attesi ci siano e siano validi

        MonFakeSessn sessioneFinta = configurazione.getSessioneFinta();
        HttpServletRequest request = configurazione.getRequest();

        if (request.getParameter(LOGINAME_FLD) != null && !request.getParameter(LOGINAME_FLD).isEmpty()) {
            sessioneFinta.setLoginName(request.getParameter(LOGINAME_FLD));
        } else {
            rispostaWs.setSeverity(IRispostaWS.SeverityEnum.ERROR);
            rispostaWs.setErrorType(IRispostaWS.ErrorTypeEnum.WS_SIGNATURE);
            rispostaWs.setErrorMessage(
                    MessaggiWSBundle.getString(MessaggiWSBundle.ERR_WS_CHECK, "Manca il campo username"));
            rispostaWs.setErrorCode(MessaggiWSBundle.ERR_WS_CHECK);
        }

        if (rispostaWs.getSeverity() == IRispostaWS.SeverityEnum.OK) {
            if (request.getParameter(PASSWORD_FLD) != null && !request.getParameter(PASSWORD_FLD).isEmpty()) {
                sessioneFinta.setPassword(request.getParameter(PASSWORD_FLD));
            } else {
                rispostaWs.setSeverity(IRispostaWS.SeverityEnum.ERROR);
                rispostaWs.setErrorType(IRispostaWS.ErrorTypeEnum.WS_SIGNATURE);
                rispostaWs.setErrorMessage(
                        MessaggiWSBundle.getString(MessaggiWSBundle.ERR_WS_CHECK, "Manca il campo password"));
                rispostaWs.setErrorCode(MessaggiWSBundle.ERR_WS_CHECK);
            }
        }
    }

}
