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

package it.eng.saceriam.ws.replicaOrganizzazione.utils;

import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.web.util.Constants;
import it.eng.saceriam.web.util.Constants.EsitoServizio;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.CancellaOrganizzazioneExt;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.RispostaWSCancellaOrganizzazione;
import it.eng.saceriam.ws.dto.IRispostaWS.SeverityEnum;
import it.eng.saceriam.ws.dto.RispostaControlli;
import it.eng.saceriam.ws.ejb.ControlliWS;
import it.eng.saceriam.ws.utils.MessaggiWSBundle;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Gilioli_P
 */
public class CancellaOrganizzazioneCheck {

    private static final Logger log = LoggerFactory.getLogger(CancellaOrganizzazioneCheck.class);

    CancellaOrganizzazioneExt cancellaOrganizzazioneExt;
    RispostaWSCancellaOrganizzazione rispostaWs;
    private RispostaControlli rispostaControlli;
    ControlliWS controlliWS = null;
    // @EJB
    // private CancellaOrganizzazioneHelper coh;

    public CancellaOrganizzazioneCheck(CancellaOrganizzazioneExt cancellaOrganizzazioneExt,
	    RispostaWSCancellaOrganizzazione rispostaWs) {
	this.cancellaOrganizzazioneExt = cancellaOrganizzazioneExt;
	this.rispostaWs = rispostaWs;
	this.rispostaControlli = new RispostaControlli();

	try {
	    controlliWS = (ControlliWS) new InitialContext().lookup("java:module/ControlliWS");
	} catch (NamingException ex) {
	    rispostaWs.setSeverity(SeverityEnum.ERROR);
	    rispostaWs.setErrorCode(MessaggiWSBundle.ERR_666);
	    String msg = MessaggiWSBundle.getString(MessaggiWSBundle.ERR_666, ex.getMessage());
	    rispostaWs.setErrorMessage(msg);
	    rispostaWs.getCancellaOrganizzazioneRisposta().setCdEsito(Constants.EsitoServizio.KO);
	    rispostaWs.getCancellaOrganizzazioneRisposta().setCdErr(MessaggiWSBundle.ERR_666);
	    rispostaWs.getCancellaOrganizzazioneRisposta().setDsErr(msg);
	    log.error("Errore nel recupero dell'EJB dei controlli generici ", ex);
	}
    }

    public RispostaWSCancellaOrganizzazione getRispostaWs() {
	return rispostaWs;
    }

    private void setRispostaWsError(SeverityEnum sev, EsitoServizio esito) {
	rispostaWs.setSeverity(sev);
	rispostaWs.setErrorCode(rispostaControlli.getCodErr());
	rispostaWs.setErrorMessage(rispostaControlli.getDsErr());
	rispostaWs.getCancellaOrganizzazioneRisposta().setCdEsito(esito);
	rispostaWs.getCancellaOrganizzazioneRisposta().setCdErr(rispostaControlli.getCodErr());
	rispostaWs.getCancellaOrganizzazioneRisposta().setDsErr(rispostaControlli.getDsErr());
    }

    public void check(String nmApplic) {
	Long idApplic = null;

	// Verifica Applicazione
	if (rispostaWs.getSeverity() != SeverityEnum.ERROR) {
	    rispostaControlli.reset();
	    rispostaControlli = controlliWS.verificaApplicazione(nmApplic);
	    if (!rispostaControlli.isrBoolean()) {
		if (rispostaControlli.getCodErr() == null) {
		    rispostaControlli.setCodErr("Errore");
		    rispostaControlli.setDsErr(nmApplic + " inesistente!");
		    setRispostaWsError(SeverityEnum.ERROR, Constants.EsitoServizio.KO);
		} else {
		    // Errore 666
		    setRispostaWsError(SeverityEnum.ERROR, Constants.EsitoServizio.KO);
		}
	    } else {
		// Se l'applicazione esiste, ricavo l'id
		idApplic = ((AplApplic) rispostaControlli.getrObject()).getIdApplic();
		cancellaOrganizzazioneExt.setIdApplic(idApplic);
	    }
	}

	// Verifica Organizzazione
	if (rispostaWs.getSeverity() != SeverityEnum.ERROR) {
	    rispostaControlli.reset();
	    rispostaControlli = controlliWS.verificaEsistenzaOrganizzazione(
		    cancellaOrganizzazioneExt.getCancellaOrganizzazioneInput().getNmApplic(),
		    cancellaOrganizzazioneExt.getCancellaOrganizzazioneInput().getIdOrganizApplic(),
		    cancellaOrganizzazioneExt.getCancellaOrganizzazioneInput().getNmTipoOrganiz());
	    if (!rispostaControlli.isrBoolean()) {
		if (rispostaControlli.getCodErr() == null) {
		    rispostaControlli.setCodErr(MessaggiWSBundle.SERVIZI_ORG_005);
		    rispostaControlli.setDsErr(MessaggiWSBundle
			    .getString(MessaggiWSBundle.SERVIZI_ORG_005, cancellaOrganizzazioneExt
				    .getCancellaOrganizzazioneInput().getIdOrganizApplic()));
		    setRispostaWsError(SeverityEnum.ERROR, Constants.EsitoServizio.KO);
		} else {
		    // Errore 666
		    setRispostaWsError(SeverityEnum.ERROR, Constants.EsitoServizio.KO);
		}
	    } else {
		// Setto l'id dell'organizzazione presente
		cancellaOrganizzazioneExt.setIdOrganizIam(rispostaControlli.getrLong());
	    }
	}
    }
}
