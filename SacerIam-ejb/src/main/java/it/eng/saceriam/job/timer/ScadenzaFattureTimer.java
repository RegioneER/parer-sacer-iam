package it.eng.saceriam.job.timer;

import it.eng.parer.jboss.timer.common.CronSchedule;
import it.eng.saceriam.common.Constants;
import it.eng.saceriam.job.scadenzaFatture.ejb.ScadenzaFattureEjb;
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

/**
 *
 * @author Gilioli_P
 */
@Singleton(mappedName = "ScadenzaFattureTimer")
@Lock(LockType.READ)
@LocalBean
public class ScadenzaFattureTimer extends JobTimer {

    private static final Logger log = LoggerFactory.getLogger(ScadenzaFattureTimer.class);

    @EJB
    private ScadenzaFattureTimer thisTimer;
    @EJB
    private ScadenzaFattureEjb scadenzaFattureEjb;

    public ScadenzaFattureTimer() {
        super(Constants.NomiJob.SCADENZA_FATTURE);
        log.debug(ScadenzaFattureTimer.class.getName() + " creato");
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
            log.info("lancio il timer ScadenzaFattureTimer...");
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
        if (timer.getInfo().equals(Constants.NomiJob.SCADENZA_FATTURE.name())) {
            thisTimer.startProcess(timer);
        }
    }

    @Override
    public void startProcess(Timer timer) {
        try {
            jobLogger.writeAtomicLog(Constants.NomiJob.SCADENZA_FATTURE, Constants.TipiRegLogJob.INIZIO_SCHEDULAZIONE,
                    null);
            scadenzaFattureEjb.scadenzaFatture();
            ;
            // } catch (ParerInternalError e) {
            // //questo log viene scritto solo in caso di errore.
            // String message = null;
            // Exception nativeExcp = e.getNativeException();
            // if (nativeExcp != null) {
            // message = nativeExcp.getMessage();
            // }
            // if (e.getCause() != null) {
            // message = e.getCause().getMessage();
            // }
            // if (message == null) {
            // message = e.getDescription();
            // }
            // jobLogger.writeAtomicLog(Constants.NomiJob.SCADENZA_FATTURE, Constants.TipiRegLogJob.ERRORE, message);
            // log.error("Errore nell'esecuzione del job di scadenza fatture", e);
        } catch (Exception e) {
            // questo log viene scritto solo in caso di errore.
            String message = ExceptionUtils.getRootCauseMessage(e);
            jobLogger.writeAtomicLog(Constants.NomiJob.SCADENZA_FATTURE, Constants.TipiRegLogJob.ERRORE, message);
            log.error("Errore nell'esecuzione del job di scadenza fatture", e);
        }
    }
}
