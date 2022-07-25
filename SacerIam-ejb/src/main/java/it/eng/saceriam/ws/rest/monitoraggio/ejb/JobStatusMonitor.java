/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.ws.rest.monitoraggio.ejb;

import it.eng.saceriam.common.Constants;
import it.eng.saceriam.entity.DecJob;
import it.eng.saceriam.ws.dto.RispostaControlli;
import it.eng.saceriam.ws.rest.monitoraggio.dto.RispostaWSStatusMonitor;
import it.eng.saceriam.ws.rest.monitoraggio.dto.rmonitor.MonitorJob;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author fioravanti_f
 */
@Stateless(mappedName = "JobStatusMonitor")
@LocalBean
public class JobStatusMonitor {

    private static final Logger log = LoggerFactory.getLogger(JobStatusMonitor.class);

    @EJB
    ControlliMonitor controlliMonitor;

    public void calcolaStatoJob(RispostaWSStatusMonitor rispostaWs, List<MonitorJob> tmpLstJob,
            Date ultimaChiamataDelWs) {
        List<DecJob> lstDj = null;
        RispostaControlli rc = controlliMonitor.leggiElencoJob();
        if (rc.isrBoolean()) {
            lstDj = (List<DecJob>) rc.getrObject();
        } else {
            rispostaWs.setEsitoWsError(rc.getCodErr(), rc.getDsErr());
            return;
        }

        //
        for (DecJob dj : lstDj) {
            MonitorJob tmpJob = new MonitorJob();
            tmpJob.setNomeJob(dj.getNmJob());
            //
            if (dj.getTiSchedJob().equals("STANDARD")) {
                rc = controlliMonitor.recNuovaEsecuzioneTimer(dj.getNmJob());
                if (rc.isrBoolean()) {
                    if (rc.getrDate() != null) {
                        tmpJob.setStatoTimer(MonitorJob.StatiTimer.ON);
                    } else {
                        tmpJob.setStatoTimer(MonitorJob.StatiTimer.OFF);
                    }
                } else {
                    rispostaWs.setEsitoWsError(rc.getCodErr(), rc.getDsErr());
                    return;
                }
            } else {
                // se il job non è "STANDARD", allora è classificato come "NO_TIMER".
                // Non posso verificare lo stato di un timer che
                // non esiste, perciò rendo per default il valore "ON" che non produce
                // warning in sede di monitoraggio
                tmpJob.setStatoTimer(MonitorJob.StatiTimer.ON);
            }

            //
            Date ultimaAttivitaDelJob = null;
            rc = controlliMonitor.leggiUltimaRegistrazione(dj.getNmJob());
            if (rc.isrBoolean()) {
                ultimaAttivitaDelJob = rc.getrDate();
                tmpJob.settSUltimaAttivita(ultimaAttivitaDelJob);
                switch ((Constants.TipiRegLogJob) rc.getrObject()) {
                case INIZIO_SCHEDULAZIONE:
                    tmpJob.setStatoJob(MonitorJob.StatiJob.IN_CORSO);
                    break;
                case FINE_SCHEDULAZIONE:
                    tmpJob.setStatoJob(MonitorJob.StatiJob.CHIUSA_OK);
                    break;
                case ERRORE:
                    tmpJob.setStatoJob(MonitorJob.StatiJob.CHIUSA_ERR);
                    break;
                }
            } else {
                rispostaWs.setEsitoWsError(rc.getCodErr(), rc.getDsErr());
                return;
            }

            //
            rc = controlliMonitor.leggiAllarmiInIntervallo(dj.getNmJob(), ultimaChiamataDelWs, ultimaAttivitaDelJob);
            if (rc.isrBoolean()) {
                if (rc.getrLong() > 0) {
                    tmpJob.setAllarmiDaUltimaChiamata(MonitorJob.Allarmi.ERRORE_SCHEDULAZIONE);
                } else {
                    tmpJob.setAllarmiDaUltimaChiamata(MonitorJob.Allarmi.NESSUN_ALLARME);
                }
            } else {
                rispostaWs.setEsitoWsError(rc.getCodErr(), rc.getDsErr());
                return;
            }

            //
            tmpLstJob.add(tmpJob);
        }
    }
}
