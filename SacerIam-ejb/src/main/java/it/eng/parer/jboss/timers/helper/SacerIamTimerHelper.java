package it.eng.parer.jboss.timers.helper;

import it.eng.parer.jboss.timer.common.JbossJobTimer;
import it.eng.parer.jboss.timer.common.JobTable;
import it.eng.parer.jboss.timer.exception.TimerNotFoundException;
import it.eng.parer.jboss.timer.helper.AbstractJbossTimerHelper;
import it.eng.parer.jboss.timer.helper.JbossTimerHelper;
import it.eng.saceriam.helper.ParamHelper;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Helper per la gestione dei job su jboss. Nota: può estendere la classe astratta ma <strong>deve</strong> implementare
 * {@link JbossTimerHelper}
 *
 * @author Snidero_L
 */
@Stateless
public class SacerIamTimerHelper extends AbstractJbossTimerHelper implements JbossTimerHelper {

    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager em;

    @EJB
    private ParamHelper paramHelper;

    @EJB
    private TimerRepository timerRepository;

    @Override
    public String getApplicationName() {
        return paramHelper.getParamApplicApplicationName();
    }

    @Override
    public List<JobTable> getJobs() {
        List<JobTable> resultList = em.createQuery("Select d From DecJob d", JobTable.class).getResultList();
        return resultList;
    }

    @Override
    public JobTable getJob(String jobName) throws TimerNotFoundException {
        TypedQuery<JobTable> query = em.createQuery("Select d From DecJob d Where d.nmJob = :nmJob", JobTable.class)
                .setParameter("nmJob", jobName);
        List<JobTable> list = query.setMaxResults(1).getResultList();

        if (list.size() < 1) {
            throw new TimerNotFoundException(jobName);
        }
        return list.get(0);

    }

    @Override
    public JbossJobTimer getTimer(String jobName) throws TimerNotFoundException {
        JbossJobTimer job = timerRepository.getConfiguredTimer(jobName);
        if (job != null) {
            return job;
        }

        throw new TimerNotFoundException(jobName);
    }

    @Override
    public Set<String> getApplicationTimerNames() {
        return timerRepository.getConfiguredTimersName();
    }
}
