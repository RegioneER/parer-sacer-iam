package it.eng.saceriam.job.scadenzaFatture.ejb;

import it.eng.parer.sacerlog.ejb.SacerLogEjb;
import it.eng.parer.sacerlog.util.LogParam;
import it.eng.saceriam.amministrazioneEntiConvenzionati.ejb.EntiConvenzionatiEjb;
import it.eng.saceriam.amministrazioneEntiConvenzionati.helper.EntiConvenzionatiHelper;
import it.eng.saceriam.job.ejb.JobLogger;
import it.eng.saceriam.common.Constants;
import it.eng.saceriam.entity.OrgFatturaEnte;
import it.eng.saceriam.entity.constraint.ConstOrgStatoFatturaEnte;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.saceriam.util.SacerLogConstants;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Gilioli_P
 */
@Stateless(mappedName = "ScadenzaFattureEjb")
@LocalBean
@Interceptors({ it.eng.saceriam.aop.TransactionInterceptor.class })
public class ScadenzaFattureEjb {

    private static final Logger log = LoggerFactory.getLogger(ScadenzaFattureEjb.class);

    @EJB
    private JobLogger jobLoggerEjb;
    @EJB
    private EntiConvenzionatiHelper ecHelper;
    @EJB
    private EntiConvenzionatiEjb ecEjb;
    @EJB
    private ParamHelper paramHelper;
    @EJB
    private SacerLogEjb sacerLogEjb;
    @Resource
    private SessionContext context;

    public void scadenzaFatture() {
        log.info("Scadenza fatture - Recupero le fatture in stato EMESSA e in stato PAGATA_PARZIALMENTE");
        String[] statiFattura = { ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA.getDescrizione(),
                ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE.getDescrizione() };
        List<String> statiFatturaList = Arrays.asList(statiFattura);
        List<OrgFatturaEnte> fatturaEnteList = ecHelper.getOrgFatturaEnteByStatoList(statiFatturaList);
        log.info("Scadenza fatture - Ottenute " + fatturaEnteList.size()
                + " fatture in stato EMESSA o PAGATA_PARZIALMENTE da elaborare");
        /*
         * Codice aggiuntivo per il logging...
         */
        LogParam param = new LogParam();
        param.setNomeApplicazione(paramHelper.getParamApplicApplicationName());
        param.setNomeUtente("Job scadenza fatture");
        param.setNomeTipoOggetto(SacerLogConstants.TIPO_OGGETTO_FATTURA);
        param.setNomeComponenteSoftware("SCADENZA_FATTURE");
        param.setNomeAzione("Controllo scadenza fatture");

        /* Per ogni fattura */
        for (OrgFatturaEnte fatturaEnte : fatturaEnteList) {
            if (fatturaEnte.getDtScadFattura() != null && fatturaEnte.getDtScadFattura().before(new Date())) {
                // Pongo la fattura in stato INSOLUTA inserendo un nuovo record di stato
                ecEjb.insertStatoFatturaEnte(fatturaEnte,
                        ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA.getDescrizione());
                // Registro il log del cambio di stato della fattura
                // sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                // param.getNomeUtente(), param.getNomeAzione(), param.getNomeTipoOggetto(),
                // BigDecimal.valueOf(fatturaEnte.getIdFatturaEnte()), param.getNomePagina());
            }
        }

        /* Scrivo nel log del job l'esito finale */
        jobLoggerEjb.writeAtomicLog(Constants.NomiJob.SCADENZA_FATTURE, Constants.TipiRegLogJob.FINE_SCHEDULAZIONE,
                null);
    }

}
