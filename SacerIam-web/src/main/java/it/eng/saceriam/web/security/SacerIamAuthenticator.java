package it.eng.saceriam.web.security;

import it.eng.saceriam.web.util.Constants;
import it.eng.saceriam.ws.dto.RecuperoAutorizzazioniRisposta;
import it.eng.saceriam.ws.ejb.RecuperoAutorizzazioniEjb;
import it.eng.spagoLite.SessionManager;
import it.eng.spagoLite.security.User;
import it.eng.spagoLite.security.auth.Authenticator;
import javax.ejb.EJB;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Quaranta_M
 */
public class SacerIamAuthenticator extends Authenticator {

    // private static final Logger log = LoggerFactory.getLogger(SacerIamAuthenticator.class);

    @EJB(mappedName = "java:app/SacerIam-ejb/RecuperoAutorizzazioniEjb")
    private RecuperoAutorizzazioniEjb ejbRef;
    // @EJB(mappedName = "java:app/SacerIam-ejb/UserHelper")
    // private UserHelper userHelper;

    @Override
    public User recuperoAutorizzazioni(HttpSession httpSession) {
        User utente = (User) SessionManager.getUser(httpSession);
        // recupero l'id dell'utente e lo setto nell'oggetto utente e lo metto in sessione.
        // Modifica fatta perché idp generici non conoscono l'id dell'utente del db di iam.
        /*
         * UsrUser usrUser = userHelper.findUserByName(utente.getUsername());
         * utente.setIdUtente(usrUser.getIdUserIam()); utente.setScadenzaPwd(usrUser.getDtScadPsw());
         */
        RecuperoAutorizzazioniRisposta resp = ejbRef.recuperoAutorizzazioniPerNome(utente.getUsername(),
                Constants.SACERIAM, null);
        UserUtil.fillComponenti(utente, resp);
        SessionManager.setUser(httpSession, utente);
        return utente;
    }

    @Override
    protected String getAppName() {
        return Constants.SACERIAM;
    }

}
