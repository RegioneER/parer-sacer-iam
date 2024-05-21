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

import java.nio.charset.StandardCharsets;

import javax.ejb.EJB;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.parer.sacerlog.ejb.SacerLogEjb;
import it.eng.parer.sacerlog.util.LogParam;
import it.eng.parer.sacerlog.util.web.SpagoliteLogUtil;
import it.eng.saceriam.exception.ParerWarningException;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.saceriam.slite.gen.Application;
import it.eng.saceriam.web.ejb.AuthEjb;
import it.eng.saceriam.web.ejb.ModificaPswEjb;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.actions.ActionBase;
import it.eng.spagoLite.message.MessageBox;
import it.eng.spagoLite.message.MessageUtil;
import it.eng.util.EncryptionUtil;

public class AssociazioneUtenteAction extends ActionBase {

    private static final long serialVersionUID = 1L;
    public static final String SESSION_CF_USER_ATTEMPTS = "TENTATIVI_CF_UTENTE";
    public static final int MAX_CF_USER_ATTEMPTS = 3;

    private static final Logger logger = LoggerFactory.getLogger(AssociazioneUtenteAction.class);

    @EJB(mappedName = "java:app/SacerIam-ejb/AuthEjb")
    private AuthEjb authEjb;
    @EJB(mappedName = "java:app/SacerIam-ejb/ModificaPswEjb")
    private ModificaPswEjb modificaPswEjb;
    @EJB(mappedName = "java:app/sacerlog-ejb/SacerLogEjb")
    private SacerLogEjb sacerLogEjb;
    @EJB(mappedName = "java:app/SacerIam-ejb/ParamHelper")
    private ParamHelper paramHelper;

    public void associa() throws EMFError {
        int tentativi = (Integer) getSession().getAttribute(SESSION_CF_USER_ATTEMPTS);
        if (tentativi <= 0) {
            getMessageBox().addError("Chiudere il Browser e tentare di nuovo il login.");
            return;
        }
        getMessageBox().clear();
        getMessageBox().setViewMode(MessageBox.ViewMode.plain);
        String userid = getRequest().getParameter("u");
        String returnurl = getRequest().getParameter("r");
        String returnUrlDecoded = new String(Base64.decodeBase64(returnurl));
        String hmac = getRequest().getParameter("h");
        String salt = getRequest().getParameter("s");
        String codiceFiscale = getRequest().getParameter("f");
        String uname = getRequest().getParameter("uname");
        String password = getRequest().getParameter("password");
        String c = getRequest().getParameter("c");
        String n = getRequest().getParameter("n");
        // MEV#27568 - Inserimento controllo nella associazione utente SPID con anagrafica utenti
        c = new String(EncryptionUtil.aesDecrypt(Base64.decodeBase64(c), EncryptionUtil.Aes.BIT_256),
                StandardCharsets.UTF_8);
        n = new String(EncryptionUtil.aesDecrypt(Base64.decodeBase64(n), EncryptionUtil.Aes.BIT_256),
                StandardCharsets.UTF_8);
        //
        String cf = null;
        if (uname != null && uname.length() == 0) {
            getMessageBox().addError("Il campo utente è obbligatorio<br/>");
        }
        if (returnurl == null || hmac == null || salt == null || codiceFiscale == null) {
            getMessageBox().addError("Richiesta HTTP non corretta<br/>");
        } else {
            cf = new String(EncryptionUtil.aesDecrypt(Base64.decodeBase64(codiceFiscale), EncryptionUtil.Aes.BIT_256),
                    StandardCharsets.UTF_8);
            String calc = ((userid != null && userid.length() > 0) ? (userid + ":") : ("")) + returnUrlDecoded + ":"
                    + cf + ":" + salt;
            String res = EncryptionUtil.getHMAC(calc);
            if (!res.equals(hmac)) {
                getMessageBox().addError("Richiesta HTTP non corretta<br/>");
            }
        }
        if (!getMessageBox().hasError()) {
            try {
                if (uname != null && uname.length() > 0) {
                    userid = uname;
                }
                // Codice aggiuntivo per il logging...
                LogParam param = SpagoliteLogUtil.getLogParam(paramHelper.getParamApplicApplicationName(), userid,
                        "/associazioneUtente", "button/AssociazioneUtenteForm#Bottoni/associa");
                param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
                authEjb.associaUtenteConCodiceFiscale(param, uname, password, cf, c, n);
                /* Per ogni applicazione dell'utente, registra nel log degli utenti da replicare */
                modificaPswEjb.registraLogUserDaReplic(userid);
                this.getResponse().sendRedirect(returnUrlDecoded);
            } catch (Exception e) {
                tentativi = (Integer) getSession().getAttribute(SESSION_CF_USER_ATTEMPTS);
                tentativi -= 1;
                if (tentativi <= 0) {
                    getMessageBox().setViewMode(MessageBox.ViewMode.plain);
                    String saluto = MessageUtil.getSalutoPerOrario();
                    getMessageBox().addWarning(saluto
                            + ", non ci è stato possibile associare la sua utenza SPID al suo account Parer per troppi tentativi falliti di inserimento di username e password.\n"
                            + "Per l’attivazione dell’utenza SPID la preghiamo di rivolgersi al service desk, scrivendo a helpdeskparer@regione.emilia-romagna.it per chiedere l’attivazione dell’utenza SPID, e seguendo le indicazioni che verranno fornite.");
                    getSession().setAttribute(SESSION_CF_USER_ATTEMPTS, tentativi);
                    authEjb.scriviLogEventoLoginUserAssociazioneFallita(userid);
                } else {
                    getMessageBox().setViewMode(MessageBox.ViewMode.plain);
                    // Si tratta di un utente disattivato ma presente in archivio, quindi lo segnalo all'utente
                    // MEV#26484 - Modificare la gestione dell'accesso SPID quando l'utente non è attivo su SIAM
                    if (e instanceof ParerWarningException) {
                        getMessageBox().addInfo(((ParerWarningException) e).getDescription() + "<BR/>");
                    }
                    if (tentativi > 1) {
                        getMessageBox()
                                .addInfo("Restano ancora " + tentativi + " tentativi di associazione con utenza Parer");
                    } else {
                        getMessageBox()
                                .addInfo("Resta ancora " + tentativi + " tentativo di associazione con utenza Parer");

                    }
                    // ---
                    getSession().setAttribute(SESSION_CF_USER_ATTEMPTS, tentativi);
                }
            }
        }
    }

    @Override
    protected String getDefaultPublsherName() {
        return Application.Publisher.ASSOCIAZIONE_UTENTE;
    }

    @Override
    public void process() throws EMFError {
        String returnurl = getRequest().getParameter("r");
        String hmac = getRequest().getParameter("h");
        String salt = getRequest().getParameter("s");
        String f = getRequest().getParameter("f");
        if (returnurl == null || hmac == null || salt == null || f == null) {
            getMessageBox().addError("Richiesta HTTP non corretta<br/>");
        } else {
            String retUrlDecoded = new String(Base64.decodeBase64(returnurl));
            String cfDecoded = new String(EncryptionUtil.aesDecrypt(Base64.decodeBase64(f), EncryptionUtil.Aes.BIT_256),
                    StandardCharsets.UTF_8);
            String calc = retUrlDecoded + ":" + cfDecoded + ":" + salt;
            String res = EncryptionUtil.getHMAC(calc);
            if (!res.equals(hmac)) {
                getMessageBox().addError("Richiesta HTTP non corretta<br/>");
            } else {
                Object tentativi = getSession().getAttribute(SESSION_CF_USER_ATTEMPTS);
                if (tentativi != null && Integer.parseInt(tentativi.toString()) <= 0) {
                    getMessageBox().addError("Chiudere il Browser e tentare di nuovo il login.");
                } else {
                    getSession().setAttribute(SESSION_CF_USER_ATTEMPTS, MAX_CF_USER_ATTEMPTS);
                    getMessageBox().setViewMode(MessageBox.ViewMode.plain);
                    String c = getRequest().getParameter("c");
                    String n = getRequest().getParameter("n");
                    String cognome = new String(
                            EncryptionUtil.aesDecrypt(Base64.decodeBase64(c), EncryptionUtil.Aes.BIT_256),
                            StandardCharsets.UTF_8);
                    String nome = new String(
                            EncryptionUtil.aesDecrypt(Base64.decodeBase64(n), EncryptionUtil.Aes.BIT_256),
                            StandardCharsets.UTF_8);
                    String saluto = MessageUtil.getSalutoPerOrario();
                    getMessageBox().addWarning(saluto + " " + nome + " " + cognome
                            + ", al suo utente SPID non sono associati diritti di accesso al sistema."
                            + " Se è già in possesso di un account valido per l’accesso al sistema può associare l’utenza SPID a quell’account, ottenendo i diritti di accesso corrispondenti anche nel caso di accesso con SPID. È necessario conoscere username e password dell’account esistente. "
                            + "Se ritiene di dover accedere al sistema ma non ha ancora un account, oppure ha dimenticato lo username o la password che le sono state fornite, è possibile contattare il nostro service desk, scrivendo a helpdeskparer@regione.emilia-romagna.it per chiedere l’attivazione dell’utenza SPID, e seguendo le indicazioni che verranno fornite.");
                }
            }
        }
        forwardToPublisher(Application.Publisher.ASSOCIAZIONE_UTENTE);
    }

    @Override
    public void reloadAfterGoBack(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getControllerName() {
        return Application.Actions.ASSOCIAZIONE_UTENTE;
    }

    @Override
    public boolean isAuthorized(String destination) {
        return true;
    }

}
