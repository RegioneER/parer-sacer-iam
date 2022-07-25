/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.ws.rest.monitoraggio.ejb;

import it.eng.saceriam.ws.dto.RispostaControlli;
import it.eng.saceriam.ws.rest.monitoraggio.dto.RispostaWSStatusMonitor;
import it.eng.saceriam.ws.rest.monitoraggio.dto.rmonitor.MonitorAltro;
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
@Stateless(mappedName = "AltriStatusMonitor")
@LocalBean
public class AltriStatusMonitor {

    private enum MonitorAltri {
        STATO_CONNESSIONE_ORCL, STATO_ERRORI_REPLICHE_UTENTI, STATO_TIMEOUT_REPLICHE_UTENTI, PRESENZA_REPLICHE_BLOCCATE
    }

    private enum MonitorSondeGenEsiti {
        OK, ERROR, WARNING
    }

    private static final Logger log = LoggerFactory.getLogger(AltriStatusMonitor.class);

    @EJB
    ControlliMonitor controlliMonitor;

    public void calcolaStatoDatabase(List<MonitorAltro> listaMon) {
        MonitorAltro tmpAltro = new MonitorAltro();
        tmpAltro.setNome(MonitorAltri.STATO_CONNESSIONE_ORCL.name());
        if (controlliMonitor.controllaStatoDbOracle()) {
            tmpAltro.setStato(MonitorSondeGenEsiti.OK.name());
        } else {
            tmpAltro.setStato(MonitorSondeGenEsiti.ERROR.name() + "|Il database Oracle non è raggiungibile ");
            log.error("Errore: Il database Oracle non è raggiungibile");
        }
        listaMon.add(tmpAltro);
    }

    public void calcolaErroriReplicheUtenti(RispostaWSStatusMonitor rispostaWs, List<MonitorAltro> listaMon) {
        RispostaControlli rc;
        MonitorAltro tmpAltro = new MonitorAltro();
        tmpAltro.setNome(MonitorAltri.STATO_ERRORI_REPLICHE_UTENTI.name());
        rc = controlliMonitor.controllaPresenzaErroriReplica();
        if (rc.isrBoolean()) {
            if (rc.getrLong() == 0) {
                tmpAltro.setStato(MonitorSondeGenEsiti.OK.name());
            } else {
                tmpAltro.setStato(MonitorSondeGenEsiti.ERROR.name());
            }
            listaMon.add(tmpAltro);
        } else {
            rispostaWs.setEsitoWsError(rc.getCodErr(), rc.getDsErr());
            return;
        }
    }

    public void calcolaTimeoutReplicheUtenti(RispostaWSStatusMonitor rispostaWs, List<MonitorAltro> listaMon) {
        RispostaControlli rc;
        MonitorAltro tmpAltro = new MonitorAltro();
        tmpAltro.setNome(MonitorAltri.STATO_TIMEOUT_REPLICHE_UTENTI.name());
        rc = controlliMonitor.controllaPresenzaTimeoutReplica();
        if (rc.isrBoolean()) {
            if (rc.getrLong() == 0) {
                tmpAltro.setStato(MonitorSondeGenEsiti.OK.name());
            } else {
                tmpAltro.setStato(MonitorSondeGenEsiti.ERROR.name());
            }
            listaMon.add(tmpAltro);
        } else {
            rispostaWs.setEsitoWsError(rc.getCodErr(), rc.getDsErr());
            return;
        }
    }

    public void calcolaReplicheBloccate(RispostaWSStatusMonitor rispostaWs, List<MonitorAltro> listaMon) {
        RispostaControlli rc;
        MonitorAltro tmpAltro = new MonitorAltro();
        tmpAltro.setNome(MonitorAltri.PRESENZA_REPLICHE_BLOCCATE.name());
        rc = controlliMonitor.replicheBloccateUltimoMese();
        if (rc.isrBoolean()) {
            if (rc.getrLong() > 0) {
                tmpAltro.setStato(MonitorSondeGenEsiti.ERROR.name());
            } else {
                tmpAltro.setStato(MonitorSondeGenEsiti.OK.name());
            }
            listaMon.add(tmpAltro);
        } else {
            rispostaWs.setEsitoWsError(rc.getCodErr(), rc.getDsErr());
            return;
        }
    }

}
