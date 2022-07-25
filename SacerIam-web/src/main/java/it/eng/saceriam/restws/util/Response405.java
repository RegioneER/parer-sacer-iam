/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.restws.util;

import java.io.IOException;
import java.text.MessageFormat;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Fioravanti_F
 */
public class Response405 {

    public enum NomeWebServiceRest {

        WS_STATUS_MONITOR("StatusMonitor");

        private final String valore;

        private NomeWebServiceRest(String val) {
            this.valore = val;
        }

        @Override
        public String toString() {
            return valore;
        }
    }

    private final static String messaggio = "<html><body><h1>{0}</h1>\n" + "<p><strong>Congratulazioni!</strong></p>\n"
            + "<ul>\n" + "<li><p>Avete appena raggiunto l''entry point del web service, "
            + "la vostra connessione sembra funzionare.</p>\n" + "</li>\n"
            + "<li><p>Sfortunatamente avete raggiunto questo indirizzo tramite un browser "
            + "e non potete effettuare altre operazioni.</p>\n" + "</li>\n"
            + "<li><p><strong>Per gli utenti</strong>: per utilizzare questo servizio "
            + "dovrete usare il vostro programma client oppure un form web dedicato.</p>\n" + "</li>\n"
            + "<li><p><strong>Per gli sviluppatori</strong>: le specifiche di "
            + "chiamata ed i documenti <em>XML Schema</em> "
            + "relativi a questo web service sono disponibili contattando "
            + "gli amministratori del servizio.</p></body></html>";

    public static void fancy405(HttpServletResponse resp, NomeWebServiceRest nomeServizio) throws IOException {
        resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        resp.getWriter().println(MessageFormat.format(messaggio, nomeServizio));
    }

}
