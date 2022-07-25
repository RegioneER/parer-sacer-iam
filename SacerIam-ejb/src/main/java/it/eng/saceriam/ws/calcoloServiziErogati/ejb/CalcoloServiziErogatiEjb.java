package it.eng.saceriam.ws.calcoloServiziErogati.ejb;

import it.eng.parer.sacerlog.ejb.SacerLogEjb;
import it.eng.parer.sacerlog.util.LogParam;
import it.eng.saceriam.amministrazioneEntiConvenzionati.ejb.EntiConvenzionatiEjb;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.saceriam.util.SacerLogConstants;
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
@Stateless(mappedName = "CalcoloServiziErogatiEjb")
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CalcoloServiziErogatiEjb {

    private static final Logger log = LoggerFactory.getLogger(CalcoloServiziErogatiEjb.class);

    @EJB
    private EntiConvenzionatiEjb ecEjb;

    @EJB
    private SacerLogEjb sacerLogEjb;

    @EJB
    private ParamHelper paramHelper;

    public void calcoloServiziErogati(Integer idEnteConvenz) {

        try {
            /*
             * Codice aggiuntivo per il logging...
             */
            LogParam param = new LogParam();
            param.setNomeApplicazione(paramHelper.getParamApplicApplicationName());
            param.setNomeUtente("Servizio Calcolo servizi erogati");
            param.setNomeTipoOggetto(SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO);
            param.setNomeComponenteSoftware("CALCOLO_SERVIZI_EROGATI");
            param.setNomeAzione("Calcola servizi erogati");
            param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());

            log.info(
                    "Calcolo servizi erogati - Ricalcolo dei servizi erogati sull'ultimo accordo per l'ente convenzionato "
                            + idEnteConvenz);
            ecEjb.calcolaServiziErogatiSuUltimoAccordo(BigDecimal.valueOf(idEnteConvenz));

            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    BigDecimal.valueOf(idEnteConvenz), param.getNomeComponenteSoftware());

            log.info("Calcolo servizi erogati - Esecuzione del servizio per l'ente convenzionato " + idEnteConvenz
                    + " terminato con successo");

        } catch (ParerUserError e) {
            log.error("Errore durante l'esecuzione del servizio di calcolo servizi erogati per l'ente " + idEnteConvenz,
                    e);

        }

    }
}
