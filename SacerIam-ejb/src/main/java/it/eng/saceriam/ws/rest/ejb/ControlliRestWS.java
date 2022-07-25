/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.ws.rest.ejb;

import it.eng.saceriam.entity.UsrUser;
import it.eng.saceriam.ws.dto.IWSDesc;
import it.eng.saceriam.ws.dto.RispostaControlli;
import it.eng.saceriam.ws.utils.MessaggiWSBundle;
import it.eng.spagoLite.security.User;
import it.eng.spagoLite.security.auth.WSLoginHandler;
import it.eng.spagoLite.security.exception.AuthWSException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Questa classe è simile alla classe ControlliWS di SACER; intergrare autenticazione ed autorizzazione mi fa schifo, ma
 * il metodo del framework disponibile era quello...
 *
 * @author fioravanti_f
 */
@Stateless(mappedName = "ControlliRestWS")
@LocalBean
@TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
public class ControlliRestWS {

    private static final Logger log = LoggerFactory.getLogger(ControlliRestWS.class);

    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager entityManager;

    public RispostaControlli checkCredenzialiEAuth(String loginName, String password, String indirizzoIP,
            IWSDesc descrizione) {
        User utente = null;
        RispostaControlli rispostaControlli;
        rispostaControlli = new RispostaControlli();
        rispostaControlli.setrBoolean(false);

        // log.info("Indirizzo IP del chiamante: " + indirizzoIP);
        log.info("Indirizzo IP del chiamante - access: ws - IP: " + indirizzoIP);

        if (loginName == null || loginName.isEmpty()) {
            rispostaControlli.setCodErr(MessaggiWSBundle.MON_AUTH_001);
            rispostaControlli.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.MON_AUTH_001));
            return rispostaControlli;
        }

        try {
            WSLoginHandler.loginAndCheckAuthzIAM(loginName, password, descrizione.getNomeWs(), indirizzoIP,
                    entityManager);
            // se l'autenticazione riesce, non va in eccezione.
            // passo quindi a leggere i dati dell'utente dal db
            UsrUser iamUser;
            String queryStr = "select iu from UsrUser iu where iu.nmUserid = :nmUseridIn";
            javax.persistence.Query query = entityManager.createQuery(queryStr, UsrUser.class);
            query.setParameter("nmUseridIn", loginName);
            iamUser = (UsrUser) query.getSingleResult();
            //
            utente = new User();
            utente.setUsername(loginName);
            utente.setIdUtente(iamUser.getIdUserIam());
            rispostaControlli.setrObject(utente);
            rispostaControlli.setrBoolean(true);
        } catch (AuthWSException e) {
            if (e.getCodiceErrore().equals(AuthWSException.CodiceErrore.UTENTE_SCADUTO)) {
                rispostaControlli.setCodErr(MessaggiWSBundle.MON_AUTH_002);
                rispostaControlli.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.MON_AUTH_002, loginName));
            } else if (e.getCodiceErrore().equals(AuthWSException.CodiceErrore.UTENTE_NON_ATTIVO)) {
                rispostaControlli.setCodErr(MessaggiWSBundle.MON_AUTH_003);
                rispostaControlli.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.MON_AUTH_003, loginName));
            } else if (e.getCodiceErrore().equals(AuthWSException.CodiceErrore.LOGIN_FALLITO)) {
                rispostaControlli.setCodErr(MessaggiWSBundle.MON_AUTH_005);
                rispostaControlli
                        .setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.MON_AUTH_005, e.getDescrizioneErrore()));
            } else if (e.getCodiceErrore().equals(AuthWSException.CodiceErrore.UTENTE_NON_AUTORIZZATO)) {
                rispostaControlli.setCodErr(MessaggiWSBundle.MON_AUTH_004);
                rispostaControlli.setDsErr(
                        MessaggiWSBundle.getString(MessaggiWSBundle.MON_AUTH_004, loginName, descrizione.getNomeWs()));
            }
        } catch (Exception e) {
            rispostaControlli.setCodErr(MessaggiWSBundle.ERR_666);
            rispostaControlli.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.ERR_666,
                    "Eccezione nella fase di autenticazione del EJB " + e.getMessage()));
            log.error("Eccezione nella fase di autenticazione del EJB ", e);
        }

        return rispostaControlli;
    }

}
