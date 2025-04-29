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

package it.eng.parer.jboss.timers.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import it.eng.parer.jboss.timer.common.JbossJobTimer;
import it.eng.parer.sacerlog.job.SacerLogAllineamentoTimer;
import it.eng.parer.sacerlog.job.SacerLogTimer;
import it.eng.saceriam.common.Constants;
import it.eng.saceriam.job.timer.EliminaReplicheTimer;
import it.eng.saceriam.job.timer.ReplicaUtentiTimer;
import it.eng.saceriam.job.timer.ScadenzaFattureTimer;

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
    @EJB
    private EliminaReplicheTimer eliminaReplicheTimer;

    private Map<String, JbossJobTimer> map;

    @PostConstruct
    public void initialize() {
	map = new HashMap<>();
	map.put(Constants.NomiJob.REPLICA_UTENTI.name(), replicaUtentiTimer);
	map.put(Constants.NomiJob.SCADENZA_FATTURE.name(), scadenzaFattureTimer);
	map.put(it.eng.parer.sacerlog.job.Constants.NomiJob.INIZIALIZZAZIONE_LOG.name(),
		sacerLogTimer);
	map.put(it.eng.parer.sacerlog.job.Constants.NomiJob.ALLINEAMENTO_LOG.name(),
		sacerLogAllineamentoTimer);
	map.put(Constants.NomiJob.ELIMINA_REPLICHE_UTENTI.name(), eliminaReplicheTimer);
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
     * @param jobName nome del job
     *
     * @return istanza del timer oppure null
     */
    @Lock(LockType.READ)
    public JbossJobTimer getConfiguredTimer(String jobName) {
	return map.get(jobName);
    }
}
