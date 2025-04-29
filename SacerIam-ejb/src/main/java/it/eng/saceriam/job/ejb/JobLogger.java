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

package it.eng.saceriam.job.ejb;

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

import it.eng.parer.sacerlog.ejb.common.AppServerInstance;
import it.eng.saceriam.common.Constants.NomiJob;
import it.eng.saceriam.common.Constants.TipiRegLogJob;
import it.eng.saceriam.entity.LogJob;

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
