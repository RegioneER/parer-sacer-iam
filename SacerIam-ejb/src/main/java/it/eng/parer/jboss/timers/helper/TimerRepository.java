package it.eng.parer.jboss.timers.helper;

import it.eng.parer.jboss.timer.common.JbossJobTimer;
import it.eng.parer.sacerlog.job.SacerLogAllineamentoTimer;
import it.eng.parer.sacerlog.job.SacerLogTimer;
import it.eng.saceriam.common.Constants;
import it.eng.saceriam.job.timer.ReplicaUtentiTimer;
import it.eng.saceriam.job.timer.ScadenzaFattureTimer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

/**
 * Singleton utilizzato per censire tutti i timer di Sacer.
 *
 * @author Snidero_L
 */
@Singleton
public class TimerRepository {

    @EJB(mappedName = "java:app/sacerlog-ejb/SacerLogTimer")
    private SacerLogTimer sacerLogTimer;
    @EJB(mappedName = "java:app/sacerlog-ejb/SacerLogAllineamentoTimer")
    private SacerLogAllineamentoTimer sacerLogAllineamentoTimer;

    @EJB
    private ReplicaUtentiTimer replicaUtentiTimer;
    @EJB
    private ScadenzaFattureTimer scadenzaFattureTimer;

    private Map<String, JbossJobTimer> map;

    @PostConstruct
    public void initialize() {
        map = new HashMap<>();
        map.put(Constants.NomiJob.REPLICA_UTENTI.name(), replicaUtentiTimer);
        map.put(Constants.NomiJob.SCADENZA_FATTURE.name(), scadenzaFattureTimer);
        map.put(it.eng.parer.sacerlog.job.Constants.NomiJob.INIZIALIZZAZIONE_LOG.name(), sacerLogTimer);
        map.put(it.eng.parer.sacerlog.job.Constants.NomiJob.ALLINEAMENTO_LOG.name(), sacerLogAllineamentoTimer);
    }

    /**
     * Ottieni i nomi di tutti i timer configurati su Sacer.
     *
     * @return insieme dei nomi di tutti i timer.
     */
    @Lock(LockType.READ)
    public Set<String> getConfiguredTimersName() {
        return map.keySet();
    }

    /**
     * Ottieni l'implementazione del timer definito.
     *
     * @param jobName
     *            nome del job
     * 
     * @return istanza del timer oppure null
     */
    @Lock(LockType.READ)
    public JbossJobTimer getConfiguredTimer(String jobName) {
        return map.get(jobName);
    }
}
