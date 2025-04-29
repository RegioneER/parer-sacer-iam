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

package it.eng.saceriam.spring;

import it.eng.spagoLite.spring.CustomSaml2AuthenticationSuccessHandler;
import it.eng.saceriam.entity.UsrUser;
import it.eng.saceriam.web.helper.UserHelper;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import org.springframework.stereotype.Component;

/**
 *
 * @author Marco Iacolucci
 */
@Component
public class SaceriamSaml2AuthenticationSuccessHandler
	extends CustomSaml2AuthenticationSuccessHandler {

    @EJB(mappedName = "java:app/SacerIam-ejb/UserHelper")
    private UserHelper userHelper;

    @Override
    protected List<UtenteDb> findUtentiPerCodiceFiscale(String codiceFiscale) {
	ArrayList<UtenteDb> al = new ArrayList<>();
	List<UsrUser> l = userHelper.findByCodiceFiscale(codiceFiscale);
	l.stream().map(usrUser -> {
	    UtenteDb u = new UtenteDb();
	    u.setId(usrUser.getIdUserIam());
	    u.setCodiceFiscale(usrUser.getCdFisc());
	    u.setDataScadenzaPassword(u.getDataScadenzaPassword());
	    u.setUsername(usrUser.getNmUserid());
	    return u;
	}).forEachOrdered(al::add);
	return al;
    }

    @Override
    protected UtenteDb getUtentePerUsername(String username) {
	UsrUser usrUser = userHelper.findUserByName(username);
	UtenteDb u = new UtenteDb();
	u.setId(usrUser.getIdUserIam());
	u.setCodiceFiscale(usrUser.getCdFisc());
	u.setDataScadenzaPassword(u.getDataScadenzaPassword());
	u.setUsername(usrUser.getNmUserid());
	return u;
    }

    @Override
    protected List<UtenteDb> findUtentiPerUsernameCaseInsensitive(String username) {
	List<UtenteDb> al = new ArrayList<>();
	List<UsrUser> l = userHelper.findUtentiPerUsernameCaseInsensitive(username);
	l.stream().map(usrUser -> {
	    UtenteDb u = new UtenteDb();
	    u.setId(usrUser.getIdUserIam());
	    u.setCodiceFiscale(usrUser.getCdFisc());
	    u.setDataScadenzaPassword(u.getDataScadenzaPassword());
	    u.setUsername(usrUser.getNmUserid());
	    return u;
	}).forEachOrdered(al::add);
	return al;
    }

}
