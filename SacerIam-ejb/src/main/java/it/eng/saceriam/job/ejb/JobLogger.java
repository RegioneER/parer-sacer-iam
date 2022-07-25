package it.eng.saceriam.job.ejb;

import it.eng.parer.sacerlog.ejb.common.AppServerInstance;
import it.eng.saceriam.common.Constants.NomiJob;
import it.eng.saceriam.common.Constants.TipiRegLogJob;
import it.eng.saceriam.entity.LogJob;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Gilioli_P
 */
@Stateless(mappedName = "JobLogger")
@LocalBean
public class JobLogger {

    private static final Logger log = LoggerFactory.getLogger(JobLogger.class);

    @EJB
    JobLogger me;
    //
    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager entityManager;

    @EJB
    private AppServerInstance appServerInstance;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long writeAtomicLog(NomiJob nomeJob, TipiRegLogJob tipoReg, String messaggioErr) {
        return me.writeLog(nomeJob.name(), tipoReg, messaggioErr);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long writeAtomicLog(String nomeJob, TipiRegLogJob tipoReg, String messaggioErr) {
        return me.writeLog(nomeJob, tipoReg, messaggioErr);
    }

    public Long writeLog(String nomeJob, TipiRegLogJob tipoReg, String messaggioErr) {
        LogJob tmpLogJob = new LogJob();
        tmpLogJob.setNmJob(nomeJob);
        tmpLogJob.setTiRegLogJob(tipoReg.toString());
        tmpLogJob.setDtRegLogJob(new Date());
        tmpLogJob.setDlMsgErr(messaggioErr);
        tmpLogJob.setCdIndServer(appServerInstance.getName());

        entityManager.persist(tmpLogJob);

        return tmpLogJob.getIdLogJob();
    }
}
