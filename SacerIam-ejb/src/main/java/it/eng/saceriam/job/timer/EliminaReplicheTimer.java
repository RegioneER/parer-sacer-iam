/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna
 * <p/>
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.job.timer;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.parer.jboss.timer.common.CronSchedule;
import it.eng.saceriam.common.Constants;
import it.eng.saceriam.job.eliminaRepliche.ejb.EliminaReplicheEjb;

@Singleton(mappedName = "EliminaReplicheTimer")
@Lock(LockType.READ)
@LocalBean
public class EliminaReplicheTimer extends JobTimer {

    private static final Logger log = LoggerFactory.getLogger(EliminaReplicheTimer.class);

    @EJB
    private EliminaReplicheTimer thisTimer;
    @EJB
    private EliminaReplicheEjb eliminaReplicheEjb;

    public EliminaReplicheTimer() {
        super(Constants.NomiJob.ELIMINA_REPLICHE_UTENTI);
        log.debug(EliminaReplicheTimer.class.getName() + " creato");
    }

    @Override
    @Lock(LockType.WRITE)
    public void startSingleAction(String applicationName) {
        if (!isActive()) {
            timerService.createTimer(TIME_DURATION, jobName);
        }
    }

    @Override
    @Lock(LockType.WRITE)
    public void startCronScheduled(CronSchedule sched, String applicationName) {
        ScheduleExpression tmpScheduleExpression;

        if (!isActive()) {
            log.info("Schedulazione: Ore: " + sched.getHour());
            log.info("Schedulazione: Minuti: " + sched.getMinute());
            log.info("Schedulazione: DOW: " + sched.getDayOfWeek());
            log.info("Schedulazione: Mese: " + sched.getMonth());
            log.info("Schedulazione: DOM: " + sched.getDayOfMonth());

            tmpScheduleExpression = new ScheduleExpression();
            tmpScheduleExpression.hour(sched.getHour());
            tmpScheduleExpression.minute(sched.getMinute());
            tmpScheduleExpression.dayOfWeek(sched.getDayOfWeek());
            tmpScheduleExpression.month(sched.getMonth());
            tmpScheduleExpression.dayOfMonth(sched.getDayOfMonth());
            log.info("lancio il timer EliminaReplicheTimer...");
            timerService.createCalendarTimer(tmpScheduleExpression, new TimerConfig(jobName, false));
        }
    }

    @Override
    @Lock(LockType.WRITE)
    public void stop(String applicationName) {
        for (Object obj : timerService.getTimers()) {
            Timer timer = (Timer) obj;
            String scheduled = (String) timer.getInfo();
            if (scheduled.equals(jobName)) {
                timer.cancel();
            }
        }
    }

    @Timeout
    public void doJob(Timer timer) {
        if (timer.getInfo().equals(Constants.NomiJob.ELIMINA_REPLICHE_UTENTI.name())) {
            thisTimer.startProcess(timer);
        }
    }

    @Override
    public void startProcess(Timer timer) {
        try {
            jobLogger.writeAtomicLog(Constants.NomiJob.ELIMINA_REPLICHE_UTENTI,
                    Constants.TipiRegLogJob.INIZIO_SCHEDULAZIONE, null);
            eliminaReplicheEjb.eliminaRepliche();

        } catch (Exception e) {
            // questo log viene scritto solo in caso di errore.
            String message = ExceptionUtils.getRootCauseMessage(e);
            jobLogger.writeAtomicLog(Constants.NomiJob.ELIMINA_REPLICHE_UTENTI, Constants.TipiRegLogJob.ERRORE,
                    message);
            log.error("Errore nell'esecuzione del job di elimina repliche", e);
        }
    }
}
