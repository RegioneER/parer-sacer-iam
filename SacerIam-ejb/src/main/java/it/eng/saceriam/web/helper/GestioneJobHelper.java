package it.eng.saceriam.web.helper;

import it.eng.saceriam.slite.gen.viewbean.LogVVisLastSchedRowBean;
import it.eng.saceriam.viewEntity.LogVVisLastSched;
import it.eng.saceriam.web.util.Transform;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Gilioli_P
 */
@Stateless
@LocalBean
public class GestioneJobHelper {

    public GestioneJobHelper() {
    }

    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager entityManager;

    private static final Logger log = LoggerFactory.getLogger(GestioneJobHelper.class);

    /**
     * Restituisce un rowbean contenente le informazioni sull'ultima schedulazione di un determinato job
     *
     * @param nmJob
     *            nome job
     * 
     * @return row bean {@link LogVVisLastSchedRowBean}
     *
     */
    public LogVVisLastSchedRowBean getLogVVisLastSched(String nmJob) {
        String queryStr = "SELECT u FROM LogVVisLastSched u WHERE u.nmJob = :nmJob";
        Query query = entityManager.createQuery(queryStr);
        query.setParameter("nmJob", nmJob);
        List<LogVVisLastSched> listaLog = query.getResultList();
        LogVVisLastSchedRowBean logRowBean = new LogVVisLastSchedRowBean();
        try {
            if (listaLog != null && !listaLog.isEmpty()) {
                logRowBean = (LogVVisLastSchedRowBean) Transform.entity2RowBean(listaLog.get(0));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return logRowBean;
    }
}
