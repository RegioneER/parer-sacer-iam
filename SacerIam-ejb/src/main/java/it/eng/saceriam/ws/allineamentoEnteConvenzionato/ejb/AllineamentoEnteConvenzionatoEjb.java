package it.eng.saceriam.ws.allineamentoEnteConvenzionato.ejb;

import it.eng.parer.sacerlog.ejb.SacerLogEjb;
import it.eng.parer.sacerlog.util.LogParam;
import it.eng.saceriam.amministrazioneEntiConvenzionati.ejb.EntiConvenzionatiEjb;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.saceriam.util.SacerLogConstants;
import it.eng.saceriam.ws.allineamentoEnteConvenzionato.dto.RispostaWSAllineamentoEnteConvenzionato;
import it.eng.saceriam.ws.utils.MessaggiWSBundle;
import java.math.BigDecimal;
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
@Stateless(mappedName = "AllineamentoEnteConvenzionatoEjb")
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AllineamentoEnteConvenzionatoEjb {

    private static final Logger log = LoggerFactory.getLogger(AllineamentoEnteConvenzionatoEjb.class);

    @EJB
    private EntiConvenzionatiEjb ecEjb;

    @EJB
    private SacerLogEjb sacerLogEjb;

    @EJB
    private ParamHelper paramHelper;

    public RispostaWSAllineamentoEnteConvenzionato ricalcoloServiziErogati(Integer idEnteConvenz) {

        // Istanzio la risposta
        RispostaWSAllineamentoEnteConvenzionato rispostaWsAec = new RispostaWSAllineamentoEnteConvenzionato();

        try {
            /*
             * Codice aggiuntivo per il logging...
             */
            LogParam param = new LogParam();
            param.setNomeApplicazione(paramHelper.getParamApplicApplicationName());
            param.setNomeUtente("Servizio Allinea ente convenzionato");
            param.setNomeTipoOggetto(SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO);
            param.setNomeComponenteSoftware("ALLINEA_ENTE_CONVENZIONATO");
            param.setNomeAzione("Allinea Ente convenzionato per organizzazione");
            param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());

            log.info(
                    "Allineamento Ente Convenzionato - Ricalcolo dei servizi erogati sull'ultimo accordo per l'ente convenzionato "
                            + idEnteConvenz);
            ecEjb.calcolaServiziErogatiSuUltimoAccordo(BigDecimal.valueOf(idEnteConvenz));

            // ecEjb.ricalcolaTotaliListaFatture(param, idFattureProvvisorie);
            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    BigDecimal.valueOf(idEnteConvenz), param.getNomeComponenteSoftware());

            log.info("Allineamento Ente Convenzionato - Esecuzione del servizio per l'ente convenzionato "
                    + idEnteConvenz + " terminato con successo");

        } catch (ParerUserError e) {
            log.error("Errore durante l'esecuzione del servizio di allineamento ente convenzionato per l'ente "
                    + idEnteConvenz, e);
            rispostaWsAec.setEsito(RispostaWSAllineamentoEnteConvenzionato.EsitoEnum.ERROR);
            rispostaWsAec.setErrorCode(MessaggiWSBundle.ERR_666);
            rispostaWsAec.setErrorMessage(MessaggiWSBundle.getString(MessaggiWSBundle.ERR_666));
        }

        // Ritorno la risposta
        return rispostaWsAec;
    }
}
