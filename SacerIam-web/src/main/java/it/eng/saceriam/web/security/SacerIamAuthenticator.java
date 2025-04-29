/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna <p/> This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version. <p/> This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Affero General Public License for more details. <p/> You should
 * have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.web.security;

import javax.ejb.EJB;
import javax.servlet.http.HttpSession;

import it.eng.saceriam.web.util.Constants;
import it.eng.saceriam.ws.dto.RecuperoAutorizzazioniRisposta;
import it.eng.saceriam.ws.ejb.RecuperoAutorizzazioniEjb;
import it.eng.spagoLite.SessionManager;
import it.eng.spagoLite.security.User;
import it.eng.spagoLite.security.auth.Authenticator;

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
	 * utente.setIdUtente(usrUser.getIdUserIam());
	 * utente.setScadenzaPwd(usrUser.getDtScadPsw());
	 */
	RecuperoAutorizzazioniRisposta resp = ejbRef
		.recuperoAutorizzazioniPerNome(utente.getUsername(), Constants.SACERIAM, null);
	UserUtil.fillComponenti(utente, resp);
	SessionManager.setUser(httpSession, utente);
	return utente;
    }

    @Override
    protected String getAppName() {
	return Constants.SACERIAM;
    }

}
