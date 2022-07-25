/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.ws.rest.monitoraggio.ejb;

import it.eng.saceriam.ws.dto.IRispostaWS;
import it.eng.saceriam.ws.dto.RispostaControlli;
import it.eng.saceriam.ws.rest.ejb.ControlliRestWS;
import it.eng.saceriam.ws.rest.monitoraggio.dto.RispostaWSStatusMonitor;
import it.eng.saceriam.ws.rest.monitoraggio.dto.StatusMonExt;
import it.eng.saceriam.ws.rest.monitoraggio.dto.rmonitor.HostMonitor;
import it.eng.saceriam.ws.rest.monitoraggio.dto.rmonitor.MonitorAltro;
import it.eng.saceriam.ws.rest.monitoraggio.dto.rmonitor.MonitorJob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author fioravanti_f
 */
@Stateless(mappedName = "StatusMonitorGen")
@LocalBean
public class StatusMonitorGen {

    @EJB
    ControlliMonitor controlliMonitor;

    @EJB
    JobStatusMonitor jobStatusMonitor;

    @EJB
    AltriStatusMonitor altriStatusMonitor;

    @EJB
    ControlliRestWS controlliWS;

    public void calcolaStatusGlobale(RispostaWSStatusMonitor rispostaWs, StatusMonExt mon) {
        RispostaControlli rc;

        // determino il timestamp dell'ultima esecuzione oppure 24 ore da questo istante
        // se non è mai stato eseguito.
        // la data mi serve per verificare se dall'ultima verifica dello stato
        // si sono verificati degli allarmi su un job
        Date ultimaChiamataDelWs = null;
        rc = controlliMonitor.leggiUltimaChiamataWS();
        if (rc.isrBoolean()) {
            ultimaChiamataDelWs = rc.getrDate();
        } else {
            rispostaWs.setEsitoWsError(rc.getCodErr(), rc.getDsErr());
            return;
        }
        //
        HostMonitor myEsito = rispostaWs.getIstanzaEsito();
        // iniziamo con il monitoraggio dei job
        List<MonitorJob> tmpLstJob = new ArrayList<>();
        jobStatusMonitor.calcolaStatoJob(rispostaWs, tmpLstJob, ultimaChiamataDelWs);
        //
        // monitoraggio degli altri parametri
        List<MonitorAltro> tmpLstAltro = new ArrayList<>();
        if (rispostaWs.getSeverity() != IRispostaWS.SeverityEnum.ERROR) {
            altriStatusMonitor.calcolaStatoDatabase(tmpLstAltro);
        }
        if (rispostaWs.getSeverity() != IRispostaWS.SeverityEnum.ERROR) {
            altriStatusMonitor.calcolaErroriReplicheUtenti(rispostaWs, tmpLstAltro);
        }
        if (rispostaWs.getSeverity() != IRispostaWS.SeverityEnum.ERROR) {
            altriStatusMonitor.calcolaTimeoutReplicheUtenti(rispostaWs, tmpLstAltro);
        }
        if (rispostaWs.getSeverity() != IRispostaWS.SeverityEnum.ERROR) {
            altriStatusMonitor.calcolaReplicheBloccate(rispostaWs, tmpLstAltro);
        }
        //
        if (rispostaWs.getSeverity() != IRispostaWS.SeverityEnum.ERROR) {
            // tutto OK, aggancio all'oggetto di risposta i due array di parametri
            if (!tmpLstJob.isEmpty()) {
                myEsito.setJob(tmpLstJob);
            }
            if (!tmpLstAltro.isEmpty()) {
                myEsito.setAltri(tmpLstAltro);
            }
        }
    }

}
