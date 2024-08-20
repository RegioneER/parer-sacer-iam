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

import java.io.IOException;
import java.net.URLEncoder;

import javax.ejb.EJB;

import it.eng.parer.sacerlog.ejb.SacerLogEjb;
import it.eng.parer.sacerlog.util.LogParam;
import it.eng.parer.sacerlog.util.web.SpagoliteLogUtil;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.slite.gen.Application;
import it.eng.saceriam.web.ejb.AuthEjb;
import it.eng.saceriam.web.ejb.ModificaPswEjb;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.actions.ActionBase;
import it.eng.spagoLite.message.MessageBox;
import it.eng.util.EncryptionUtil;

public class ModificaPswAction extends ActionBase {

    private static final long serialVersionUID = 1L;

    // Accetta tutte le stringhe che contengono almeno una lettera, almeno un numero ed almeno un carattere speciale tra
    // quelli riportati (tabella ASCII)
    private static final String PASSWORD_REGEX = "(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!-/:-@\\[-`{-~])[A-Za-z\\d!-/:-@\\[-`{-~]{8,}$";

    @EJB(mappedName = "java:app/SacerIam-ejb/AuthEjb")
    private AuthEjb authEjb;
    @EJB(mappedName = "java:app/SacerIam-ejb/ModificaPswEjb")
    private ModificaPswEjb modificaPswEjb;
    @EJB(mappedName = "java:app/sacerlog-ejb/SacerLogEjb")
    private SacerLogEjb sacerLogEjb;

    public void modifica() throws EMFError {
        getMessageBox().clear();
        getMessageBox().setViewMode(MessageBox.ViewMode.plain);
        String userid = getRequest().getParameter("u");
        String returnurl = getRequest().getParameter("r");
        String hmac = getRequest().getParameter("h");
        String salt = getRequest().getParameter("s");
        String uname = getRequest().getParameter("uname");
        String oldpass = getRequest().getParameter("oldpass");
        String newpass = getRequest().getParameter("newpass");
        String confnewpass = getRequest().getParameter("confnewpass");
        String kcc = getRequest().getParameter("kcc");
        String kct = getRequest().getParameter("kct");
        if (oldpass.length() == 0) {
            getMessageBox().addError("Il campo vecchia password è obbligatorio<br/>");
        }
        if (newpass.length() == 0) {
            getMessageBox().addError("Il campo nuova password è obbligatorio<br/>");
        }
        if (confnewpass.length() == 0) {
            getMessageBox().addError("Il campo conferma nuova password è obbligatorio<br/>");
        }
        if (!newpass.equals(confnewpass)) {
            getMessageBox().addError("I campi nuova password e conferma nuova password non coincidono<br/>");
        }
        if (!newpass.matches(PASSWORD_REGEX)) {
            getMessageBox().addError("La password non rispetta i requisiti di sicurezza <br/>");
        }
        if (uname != null && uname.length() == 0) {
            getMessageBox().addError("Il campo utente è obbligatorio<br/>");
        }
        if (returnurl == null || hmac == null || salt == null) {
            getMessageBox().addError("Richiesta HTTP non corretta<br/>");
        } else {
            String calc = ((userid != null && userid.length() > 0) ? (userid + ":") : ("")) + returnurl + ":" + salt;
            String res = EncryptionUtil.getHMAC(calc);
            if (!res.equals(hmac)) {
                getMessageBox().addError("Richiesta HTTP non corretta<br/>");
            }
        }

        if (!getMessageBox().hasError()) {
            try {
                /* Modifica la password su DB */
                if (uname != null && uname.length() > 0) {
                    userid = uname;
                }
                /*
                 * Codice aggiuntivo per il logging...
                 */
                LogParam param = SpagoliteLogUtil.getLogParam(null, userid);
                if (authEjb.userHasToChangePasswordFirstTime(param.getNomeUtente())) {
                    param.setNomePagina("/amministrazioneUtenti/dettaglioUtenteInserisciPassword");
                    param.setNomeAzione("button/AmministrazioneUtentiForm#EditPasswordUtente/salva_password");
                } else {
                    param.setNomePagina("/modificaPassword");
                    param.setNomeAzione("button/ModificaPasswordForm#Bottoni/modificaPassword");
                }
                param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
                authEjb.changePwd(param, userid, newpass, oldpass);

                /* Per ogni applicazione dell'utente, registra nel log degli utenti da replicare */
                modificaPswEjb.registraLogUserDaReplic(userid);

                /* RISPONDI CON ESITO OK */
                getMessageBox().setViewMode(MessageBox.ViewMode.plain);
                getMessageBox().addInfo("Cambio password effettuato correttamente");
                // findNews();
                if (kcc != null && !kcc.trim().equals("")) {
                    returnurl += "?client_id=" + URLEncoder.encode(kcc, "UTF-8") + "&tab_id="
                            + URLEncoder.encode(kct, "UTF-8");
                }
                this.getResponse().sendRedirect(returnurl);
            } catch (ParerUserError e) {
                getMessageBox().addError(e.getDescription());
            } catch (Exception e) {
                getMessageBox().addError("Errore durante il salvataggio della password", e);
            }
        } else {
            forwardToPublisher(Application.Publisher.CAMBIO_PASSWORD);
        }
    }

    public void annulla() throws EMFError {
        String userid = getRequest().getParameter("u");
        String returnurl = getRequest().getParameter("r");
        String hmac = getRequest().getParameter("h");
        String salt = getRequest().getParameter("s");
        if (returnurl == null || hmac == null || salt == null) {
            getMessageBox().addError("Richiesta HTTP non corretta<br/>");
        } else {
            String calc = ((userid != null && userid.length() > 0) ? (userid + ":") : ("")) + returnurl + ":" + salt;
            String res = EncryptionUtil.getHMAC(calc);
            if (!res.equals(hmac)) {
                getMessageBox().addError("Richiesta HTTP non corretta<br/>");
            } else {
                try {
                    this.getResponse().sendRedirect(returnurl);
                } catch (IOException ex) {
                    getMessageBox().addError("Errore durante il redirect alla pagina di origine");
                }
            }
        }
    }

    @Override
    protected String getDefaultPublsherName() {
        return Application.Publisher.CAMBIO_PASSWORD;
    }

    @Override
    public void process() throws EMFError {
        String returnurl = getRequest().getParameter("r");
        String hmac = getRequest().getParameter("h");
        String salt = getRequest().getParameter("s");
        if (returnurl == null || hmac == null || salt == null) {
            getMessageBox().addError("Richiesta HTTP non corretta<br/>");
        } else {
            String calc = returnurl + ":" + salt;
            String res = EncryptionUtil.getHMAC(calc);
            if (!res.equals(hmac)) {
                getMessageBox().addError("Richiesta HTTP non corretta<br/>");
            }
        }
        forwardToPublisher(Application.Publisher.CAMBIO_PASSWORD);
    }

    @Override
    public void reloadAfterGoBack(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getControllerName() {
        return Application.Actions.MODIFICA_PSW;
    }

    @Override
    protected boolean isAuthorized(String destination) {
        return true;
    }

}
