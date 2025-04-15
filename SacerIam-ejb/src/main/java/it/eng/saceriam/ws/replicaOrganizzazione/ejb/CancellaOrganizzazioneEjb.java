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

package it.eng.saceriam.ws.replicaOrganizzazione.ejb;

import it.eng.parer.sacerlog.ejb.SacerLogEjb;
import it.eng.parer.sacerlog.util.LogParam;
import it.eng.saceriam.amministrazioneEntiConvenzionati.ejb.EntiConvenzionatiEjb;
import it.eng.saceriam.entity.UsrOrganizIam;
import it.eng.saceriam.exception.TransactionException;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.saceriam.web.util.ApplEnum;
import it.eng.saceriam.web.util.Constants;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.CancellaOrganizzazioneInput;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.CancellaOrganizzazioneRisposta;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.RispostaWSCancellaOrganizzazione;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.WSDescCancellaOrganizzazione;
import it.eng.saceriam.ws.replicaOrganizzazione.utils.CancellaOrganizzazioneCheck;
import it.eng.saceriam.ws.dto.IRispostaWS;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.CancellaOrganizzazioneExt;
import it.eng.saceriam.ws.utils.MessaggiWSBundle;
import it.eng.saceriam.ws.utils.WsTransactionManager;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Gilioli_P
 */
@Stateless(mappedName = "CancellaOrganizzazioneEjb")
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class CancellaOrganizzazioneEjb {

    @EJB
    private GestioneOrganizzazioneHelper goh;
    @Resource
    private UserTransaction utx;
    private static WsTransactionManager wtm;
    @EJB
    private ParamHelper paramHelper;
    @EJB
    private SacerLogEjb sacerLogEjb;

    @EJB
    private EntiConvenzionatiEjb ecEjb;

    public CancellaOrganizzazioneRisposta cancellaOrganizzazione(String nmApplic,
	    Integer idOrganizApplic, String nmTipoOrganiz) {

	// Istanzio il transaction manager
	wtm = new WsTransactionManager(utx);

	// Istanzio la risposta
	RispostaWSCancellaOrganizzazione rispostaWs = new RispostaWSCancellaOrganizzazione();
	rispostaWs.setCancellaOrganizzazioneRisposta(new CancellaOrganizzazioneRisposta());
	// Imposto l'esito della risposta di default OK
	rispostaWs.getCancellaOrganizzazioneRisposta().setCdEsito(Constants.EsitoServizio.OK);

	// Istanzio l'oggetto che contiene i parametri ricevuti in input
	CancellaOrganizzazioneInput coInput = new CancellaOrganizzazioneInput(nmApplic,
		idOrganizApplic, nmTipoOrganiz);

	// Istanzio l'Ext con l'oggetto creato e setto i parametri descrizione e quelli in input
	CancellaOrganizzazioneExt coExt = new CancellaOrganizzazioneExt();
	coExt.setDescrizione(new WSDescCancellaOrganizzazione());
	coExt.setCancellaOrganizzazioneInput(coInput);

	if (rispostaWs.getSeverity() != IRispostaWS.SeverityEnum.ERROR) {
	    // Chiamo la classe CancellaOrganizzazioneCheck che gestisce i controlli di oggetto
	    CancellaOrganizzazioneCheck checker = new CancellaOrganizzazioneCheck(coExt,
		    rispostaWs);
	    checker.check(nmApplic);
	}

	// Se i controlli sono andati a buon fine...
	if (rispostaWs.getSeverity() != IRispostaWS.SeverityEnum.ERROR) {
	    try {
		wtm.beginTrans(rispostaWs);

		// Registro gli utenti da replicare...
		Set<BigDecimal> usersToReply = goh.getUsersOnDich(coExt.getIdOrganizIam(),
			ApplEnum.TiOperReplic.CANC);
		for (BigDecimal idUserIam : usersToReply) {
		    goh.registraUtenteDaReplicare(idUserIam, coExt.getIdApplic(), rispostaWs);
		}

		/*
		 * Codice aggiuntivo per il logging...
		 */
		// MAC #19526
		Set<BigDecimal> usersSacer = new HashSet<>();
		if (nmApplic.equals("SACER")) {
		    CancellaOrganizzazioneInput parametriInput = coExt
			    .getCancellaOrganizzazioneInput();
		    UsrOrganizIam organiz = goh.getUsrOrganizIam(coExt.getIdApplic(),
			    parametriInput.getIdOrganizApplic(), parametriInput.getNmTipoOrganiz());
		    usersSacer = goh.getUsersSacer(organiz.getIdOrganizIam());
		}

		/* CANCELLO L'ORGANIZZAZIONE */
		goh.cancellaOrganizzazione(coExt.getIdApplic(), coInput, rispostaWs);

		for (BigDecimal idUserIam : usersSacer) {
		    goh.registraUtenteDaReplicare(idUserIam, coExt.getIdApplic(), rispostaWs);
		    LogParam param = new LogParam();
		    param.setNomeApplicazione(paramHelper.getParamApplicApplicationName());
		    param.setNomeUtente("Servizio Allinea organizzazione");
		    param.setNomeAzione("Allinea abilitazioni utente alle organizzazioni");
		    param.setNomeTipoOggetto("Utente");
		    param.setIdOggetto(idUserIam);
		    param.setNomeComponenteSoftware("ALLINEA_ORGANIZZAZIONE");
		    sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(),
			    param.getNomeUtente(), param.getNomeAzione(),
			    param.getNomeTipoOggetto(), param.getIdOggetto(),
			    param.getNomeComponenteSoftware());
		}

		// // ...e cancello l'organizzazione...
		// Popola la risposta
		rispostaWs.getCancellaOrganizzazioneRisposta().setNmApplic(coInput.getNmApplic());
		rispostaWs.getCancellaOrganizzazioneRisposta()
			.setIdOrganizApplic(coInput.getIdOrganizApplic());
		rispostaWs.getCancellaOrganizzazioneRisposta()
			.setNmTipoOrganiz(coInput.getNmTipoOrganiz());

		wtm.commit(rispostaWs);
	    } catch (TransactionException e) {
		rispostaWs.getCancellaOrganizzazioneRisposta()
			.setCdEsito(Constants.EsitoServizio.KO);
		rispostaWs.getCancellaOrganizzazioneRisposta().setCdErr(rispostaWs.getErrorCode());
		rispostaWs.getCancellaOrganizzazioneRisposta()
			.setDsErr(rispostaWs.getErrorMessage());
		wtm.rollback(rispostaWs);
	    } catch (Exception e) {
		rispostaWs.getCancellaOrganizzazioneRisposta()
			.setCdEsito(Constants.EsitoServizio.KO);
		rispostaWs.getCancellaOrganizzazioneRisposta().setCdErr(MessaggiWSBundle.ERR_666);
		rispostaWs.getCancellaOrganizzazioneRisposta()
			.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.ERR_666));
		wtm.rollback(rispostaWs);
	    }
	}
	// Ritorno la risposta
	return rispostaWs.getCancellaOrganizzazioneRisposta();
    }
}
