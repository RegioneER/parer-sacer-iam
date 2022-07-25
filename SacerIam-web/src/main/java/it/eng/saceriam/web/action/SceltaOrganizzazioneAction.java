package it.eng.saceriam.web.action;

import it.eng.saceriam.entity.UsrUser;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.saceriam.slite.gen.Application;
import it.eng.saceriam.web.helper.LoginLogHelper;
import it.eng.saceriam.web.helper.UserHelper;
import it.eng.saceriam.web.security.SacerIamAuthenticator;
import it.eng.saceriam.web.util.AuditSessionListener;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.actions.ActionBase;
import it.eng.spagoLite.message.MessageBox;
import java.util.List;
import it.eng.spagoLite.security.User;
import it.eng.spagoLite.security.auth.PwdUtil;
import it.eng.util.EncryptionUtil;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Quaranta_M
 */
public class SceltaOrganizzazioneAction extends ActionBase {

    private static final Logger logger = LoggerFactory.getLogger(SceltaOrganizzazioneAction.class);

    @EJB(mappedName = "java:app/SacerIam-ejb/LoginLogHelper")
    private LoginLogHelper loginLogHelper;
    @EJB(mappedName = "java:app/SacerIam-ejb/ParamHelper")
    private ParamHelper paramHelper;
    @EJB(mappedName = "java:app/SacerIam-ejb/UserHelper")
    private UserHelper userHelper;

    @Autowired
    private SacerIamAuthenticator authenticator;

    @Override
    public String getControllerName() {
        return Application.Actions.SCELTA_ORGANIZZAZIONE;
    }

    @Override
    protected String getDefaultPublsherName() {
        return null;
    }

    @Override
    public void process() throws EMFError {
        process(false); // Fa la vecchia redirectToAction
    }

    private void process(boolean forwardToPublisher) throws EMFError {
        User utente = (User) super.getUser();
        // MEV#23905 - Associazione utente SPID con anagrafica utenti
        if (utente.isUtenteDaAssociare()) {
            gestisciUtenteDaAssociare(utente);
        } else {
            /*
             * Se si era tentato 3 volte di associare un utente esterno SAML ad un utente PARER provenendo da altre
             * applicazioni parer allora si viene forzatamente buttati fuori da IAM e bisogna rifare il login.
             */
            Object attributo = getSession().getAttribute(AssociazioneUtenteAction.SESSION_CF_USER_ATTEMPTS);
            if (attributo != null) {
                int tentativi = (Integer) attributo;
                if (tentativi == 0) {
                    redirectToAction("Logout.html");
                    return;
                }
            }
            authenticator.recuperoAutorizzazioni(getSession());
            // User utente = (User) super.getUser();
            // loggo, se necessario, l'avvenuto login nell'applicativo
            this.loginLogger(utente);
            // carico la "home" applicativa, senza selezione di alcuna struttura
            if (forwardToPublisher) { // nuovo comportamento
                forwardToPublisher(Application.Publisher.HOME);
            } else { // Vecchio comportamento
                redirectToAction(Application.Actions.HOME + "?clearhistory=true");
            }
        }
    }

    @Override
    protected boolean isAuthorized(String destination) {
        return true;
    }

    @Override
    public void reloadAfterGoBack(String publisherName) {
    }

    private void loginLogger(User utente) {
        if (utente.getConfigurazione() == null && utente.getOrganizzazioneMap() == null) {
            // è un login iniziale e non un ritorno sulla form per un cambio struttura.
            // Se fosse un cambio di struttura, queste variabili sarebbero valorizzate
            // poiché riportano i dati relativi alla struttura su cui l'utente
            // sta operando.
            // Nota bene: questo codice è deliberatamente identico a quello presente
            // nella analoga "action" in Sacer ed in Preingest dove il cambio di struttura
            // è possibile. In questo caso si tratta di una preoccupazione inutile
            // poiché questa action esegue sempre un redirect appena viene raggiunta e
            // non è riferita da alcun link nell'interfaccia.

            HttpServletRequest request = getRequest();
            String ipVers = request.getHeader("X-FORWARDED-FOR");
            if (ipVers == null || ipVers.isEmpty()) {
                ipVers = request.getRemoteAddr();
            }
            // logger.debug("Indirizzo da cui l'utente si connette: " + ipVers);
            logger.info("Indirizzo IP del chiamante - access: web - IP: " + ipVers);
            loginLogHelper.writeLogEvento(utente, ipVers, LoginLogHelper.TipiEvento.LOGIN);
            getSession().setAttribute(AuditSessionListener.CLIENT_IP_ADDRESS, ipVers);
        }
    }

    // MEV#23905 - Associazione utente SPID con anagrafica utenti
    private void gestisciUtenteDaAssociare(User utente) throws EMFError {
        this.freeze();
        String retURL = paramHelper.getUrlBackAssociazioneUtenteCf();
        String salt = Base64.encodeBase64URLSafeString(PwdUtil.generateSalt());
        byte[] cfCriptato = EncryptionUtil.aesCrypt(utente.getCodiceFiscale(), EncryptionUtil.Aes.BIT_256);
        String f = Base64.encodeBase64URLSafeString(cfCriptato);
        byte[] cogCriptato = EncryptionUtil.aesCrypt(utente.getCognome(), EncryptionUtil.Aes.BIT_256);
        String c = Base64.encodeBase64URLSafeString(cogCriptato);
        byte[] nomeCriptato = EncryptionUtil.aesCrypt(utente.getNome(), EncryptionUtil.Aes.BIT_256);
        String n = Base64.encodeBase64URLSafeString(nomeCriptato);
        String hmac = EncryptionUtil.getHMAC(retURL + ":" + utente.getCodiceFiscale() + ":" + salt);
        try {
            this.getResponse()
                    .sendRedirect(paramHelper.getUrlAssociazioneUtenteCf() + "?r="
                            + Base64.encodeBase64URLSafeString(retURL.getBytes()) + "&h=" + hmac + "&s=" + salt + "&f="
                            + f + "&c=" + c + "&n=" + n);
        } catch (IOException ex) {
            throw new EMFError("ERROR", "Errore nella sendRedirect verso Iam");
        }

    }

    public void backFromAssociation() throws EMFError {
        User user = (User) getUser();
        if (user.getCodiceFiscale() != null && !user.getCodiceFiscale().isEmpty()) {
            List<UsrUser> l = userHelper.findByCodiceFiscale(user.getCodiceFiscale());
            if (l.size() == 1) {
                UsrUser us = l.iterator().next();
                user.setUtenteDaAssociare(false);
                user.setUsername(us.getNmUserid());
                user.setIdUtente(us.getIdUserIam());
                getMessageBox().setViewMode(MessageBox.ViewMode.alert);
                getMessageBox().addInfo("L'utente loggato è stato ricondotto con successo all'utenza Parer.");
                process(true); // Fa la forwardToPublisher altrimenti il messaggio non si vede!
                return;
            }
        }
        /* Per sicurezza se qualcuno forza l'accesso con la URL senza provenire da IAM lo butto fuori! */
        logger.error("Chiamata al metodo beckFromAssociation non autorizzata! Effettuo il logout forzato!");
        redirectToAction("/Logout.html");
    }

}
