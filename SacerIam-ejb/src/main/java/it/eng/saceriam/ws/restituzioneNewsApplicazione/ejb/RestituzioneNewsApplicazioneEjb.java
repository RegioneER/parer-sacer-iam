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

package it.eng.saceriam.ws.restituzioneNewsApplicazione.ejb;

import it.eng.saceriam.web.util.Constants;
import it.eng.saceriam.ws.dto.IRispostaWS;
import it.eng.saceriam.ws.restituzioneNewsApplicazione.dto.ListaNews;
import it.eng.saceriam.ws.restituzioneNewsApplicazione.dto.RestituzioneNewsApplicazioneExt;
import it.eng.saceriam.ws.restituzioneNewsApplicazione.dto.RestituzioneNewsApplicazioneRisposta;
import it.eng.saceriam.ws.restituzioneNewsApplicazione.dto.RispostaWSRestituzioneNewsApplicazione;
import it.eng.saceriam.ws.restituzioneNewsApplicazione.dto.WSDescRestituzioneNewsApplicazione;
import it.eng.saceriam.ws.restituzioneNewsApplicazione.utils.RestituzioneNewsApplicazioneCheck;
import it.eng.saceriam.ws.utils.MessaggiWSBundle;
import it.eng.spagoCore.error.EMFError;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Gilioli_P
 */
@Stateless(mappedName = "RestituzioneNewsApplicazioneEjb")
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class RestituzioneNewsApplicazioneEjb {

    private static final Logger log = LoggerFactory.getLogger(RestituzioneNewsApplicazioneEjb.class);

    @EJB
    private RestituzioneNewsApplicazioneHelper rnaHelper;

    public RestituzioneNewsApplicazioneRisposta restituzioneNewsApplicazione(String nmApplic) {

        // Istanzio la risposta
        RispostaWSRestituzioneNewsApplicazione rispostaWs = new RispostaWSRestituzioneNewsApplicazione();
        rispostaWs.setRestituzioneNewsApplicazioneRisposta(new RestituzioneNewsApplicazioneRisposta());
        // Imposto l'esito della risposta di default OK
        rispostaWs.getRestituzioneNewsApplicazioneRisposta().setCdEsito(Constants.EsitoServizio.OK);

        // Istanzio l'Ext con l'oggetto creato e setto i parametri descrizione e quelli in input
        RestituzioneNewsApplicazioneExt rnaExt = new RestituzioneNewsApplicazioneExt();
        rnaExt.setDescrizione(new WSDescRestituzioneNewsApplicazione());
        rnaExt.setNmApplic(nmApplic);

        // Chiamo la classe InserimentoOrganizzazioneCheck che gestisce i controlli di oggetto
        RestituzioneNewsApplicazioneCheck checker = new RestituzioneNewsApplicazioneCheck(rnaExt, rispostaWs);
        checker.check(nmApplic);

        if (rispostaWs.getSeverity() != IRispostaWS.SeverityEnum.ERROR) {
            ListaNews listaNews = null;
            /* try { */
            // Se i controlli sono andati a buon fine ricavo e restituisco le news
            listaNews = rnaHelper.getListaNews(rnaExt.getIdApplic());

            // Popola la risposta
            rispostaWs.getRestituzioneNewsApplicazioneRisposta().setNmApplic(nmApplic);
            rispostaWs.getRestituzioneNewsApplicazioneRisposta().setListaNews(listaNews);
            /*
             * } catch (EMFError e) {
             * rispostaWs.getRestituzioneNewsApplicazioneRisposta().setCdEsito(Constants.EsitoServizio.KO);
             * rispostaWs.getRestituzioneNewsApplicazioneRisposta().setCdErr(MessaggiWSBundle.ERR_666);
             * rispostaWs.getRestituzioneNewsApplicazioneRisposta()
             * .setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.ERR_666)); }
             */
        }

        // Ritorno la risposta
        return rispostaWs.getRestituzioneNewsApplicazioneRisposta();
    }
}
