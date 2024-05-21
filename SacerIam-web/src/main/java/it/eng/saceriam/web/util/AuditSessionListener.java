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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.web.util;

import javax.ejb.EJB;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.saceriam.web.helper.LoginLogHelper;
import it.eng.spagoLite.SessionManager;
import it.eng.spagoLite.security.User;

/**
 *
 * @author fioravanti_f
 */
public class AuditSessionListener implements HttpSessionListener {

    @EJB
    private LoginLogHelper loginLogHelper;

    private static final Logger log = LoggerFactory.getLogger(AuditSessionListener.class);

    public static final String CLIENT_IP_ADDRESS = "###_LOG#_CLIENT_IP_ADDRESS";

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // metodo deliberatamente vuoto.
        // non ho alcun interesse ad intercettare questo evento, per ora.
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession sessione = se.getSession();
        User tmpUser = (User) SessionManager.getUser(sessione);
        String ipVers = (String) sessione.getAttribute(CLIENT_IP_ADDRESS);

        // queste due variabili possono essere nulle, la fase di logout infatti
        // provoca lo scatenamento ripetuto di questo evento, ma in uno
        // solo dei casi (la vera fine della sessione applicativa) queste
        // variabili sono ancora in sessione. Ovviamente è questo il punto in
        // cui l'evento deve essere loggato sul db
        if (tmpUser != null && ipVers != null) {
            loginLogHelper.writeLogEvento(tmpUser, ipVers, LoginLogHelper.TipiEvento.LOGOUT);
        }
    }

}
