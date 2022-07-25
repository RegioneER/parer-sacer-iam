/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.ws.rest.monitoraggio.ejb;

import it.eng.parer.jboss.timer.service.JbossTimerEjb;
import it.eng.saceriam.common.Constants;
import it.eng.saceriam.entity.DecJob;
import it.eng.saceriam.entity.LogJob;
import it.eng.saceriam.viewEntity.UsrVLisSched;
import it.eng.saceriam.ws.dto.RispostaControlli;
import it.eng.saceriam.ws.utils.MessaggiWSBundle;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author fioravanti_f
 */
@Stateless(mappedName = "ControlliMonitor")
@LocalBean
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
public class ControlliMonitor {

    @EJB(mappedName = "java:app/JbossTimerWrapper-ejb/JbossTimerEjb")
    private JbossTimerEjb jbossTimerEjb;

    private static final Logger log = LoggerFactory.getLogger(ControlliMonitor.class);

    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager entityManager;

    public RispostaControlli leggiUltimaChiamataWS() {
        RispostaControlli rispostaControlli;
        rispostaControlli = new RispostaControlli();
        rispostaControlli.setrBoolean(false);
        List<Date> lstDate = null;

        try {
            String queryStr = "select max(lj.dtRegLogJob) " + "from LogJob lj " + "where lj.nmJob = :nmJob "
                    + "and lj.tiRegLogJob = :tiRegLogJob ";

            javax.persistence.Query query = entityManager.createQuery(queryStr);
            query.setParameter("nmJob", Constants.NomiJob.WS_MONITORAGGIO_STATUS.name());
            query.setParameter("tiRegLogJob", Constants.TipiRegLogJob.FINE_SCHEDULAZIONE.name());
            lstDate = query.getResultList();
            if (lstDate != null && lstDate.size() > 0 && lstDate.get(0) != null) {
                rispostaControlli.setrDate(lstDate.get(0));
            } else {
                rispostaControlli.setrDate(this.sottraiUnGiorno(new Date()));
            }
            rispostaControlli.setrBoolean(true);
        } catch (Exception e) {
            rispostaControlli.setCodErr(MessaggiWSBundle.ERR_666);
            rispostaControlli.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.ERR_666,
                    "Eccezione ControlliMonitor.leggiUltimaChiamataWS " + e.getMessage()));
            log.error("Eccezione nella lettura della tabella dei log dei job", e);
        }
        return rispostaControlli;
    }

    public RispostaControlli leggiElencoJob() {
        RispostaControlli rispostaControlli;
        rispostaControlli = new RispostaControlli();
        rispostaControlli.setrBoolean(false);
        List<DecJob> lstJobs = null;

        try {
            String queryStr = "select t from DecJob t " + "where t.tiSchedJob = 'STANDARD' "
                    + "or t.tiSchedJob = 'NO_TIMER'";
            javax.persistence.Query query = entityManager.createQuery(queryStr);
            lstJobs = query.getResultList();
            rispostaControlli.setrObject(lstJobs);
            rispostaControlli.setrBoolean(true);
        } catch (Exception e) {
            rispostaControlli.setCodErr(MessaggiWSBundle.ERR_666);
            rispostaControlli.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.ERR_666,
                    "Eccezione ControlliMonitor.leggiElencoJob " + e.getMessage()));
            log.error("Eccezione nella lettura  della tabella dei job", e);
        }

        return rispostaControlli;
    }

    public RispostaControlli recNuovaEsecuzioneTimer(String jobName) {
        RispostaControlli rispostaControlli;
        rispostaControlli = new RispostaControlli();
        rispostaControlli.setrBoolean(false);

        try {
            Date tmpdaDate = jbossTimerEjb.getDataProssimaAttivazione(jobName);
            rispostaControlli.setrDate(tmpdaDate);
            rispostaControlli.setrBoolean(true);
        } catch (Exception e) {
            rispostaControlli.setCodErr(MessaggiWSBundle.ERR_666);
            rispostaControlli.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.ERR_666,
                    "Eccezione ControlliMonitor.recNuovaEsecuzioneTimer " + e.getMessage()));
            log.error("Eccezione nell'accesso al manager dei job", e);
        }

        return rispostaControlli;
    }

    public RispostaControlli leggiUltimaRegistrazione(String jobName) {
        RispostaControlli rispostaControlli;
        rispostaControlli = new RispostaControlli();
        rispostaControlli.setrBoolean(false);
        List<LogJob> lstJob;
        List<Date> lstDate = null;
        try {
            // leggo la data dell'ultimo inizio di attività del job
            String queryStr = "select max(lj.dtRegLogJob) " + "from LogJob lj " + "where lj.nmJob = :nmJob "
                    + "and lj.tiRegLogJob = :tiRegLogJob ";

            javax.persistence.Query query = entityManager.createQuery(queryStr);
            query.setParameter("nmJob", jobName);
            query.setParameter("tiRegLogJob", Constants.TipiRegLogJob.INIZIO_SCHEDULAZIONE.name());
            lstDate = query.getResultList();
            if (lstDate != null && lstDate.size() > 0 && lstDate.get(0) != null) {
                Date dataInizio = lstDate.get(0);
                // cerco la data fine job (o errore nel job)
                // successiva o contemporanea all'inizio
                queryStr = "select j from LogJob j " + "where j.dtRegLogJob >= :dataInizio "
                        + "and (j.tiRegLogJob = :tiRegLogJob1 " + "or j.tiRegLogJob = :tiRegLogJob2) "
                        + "and j.nmJob = :nmJob ";

                query = entityManager.createQuery(queryStr);
                query.setParameter("nmJob", jobName);
                query.setParameter("tiRegLogJob1", Constants.TipiRegLogJob.FINE_SCHEDULAZIONE.name());
                query.setParameter("tiRegLogJob2", Constants.TipiRegLogJob.ERRORE.name());
                query.setParameter("dataInizio", dataInizio);
                lstJob = query.getResultList();
                if (lstJob != null && lstJob.size() > 0) {
                    // se l'ho trovata, rendo la data e il tipo di schedulazione
                    rispostaControlli.setrDate(lstJob.get(0).getDtRegLogJob());
                    rispostaControlli.setrObject(Constants.TipiRegLogJob.valueOf(lstJob.get(0).getTiRegLogJob()));
                } else {
                    // altrimenti, rendo la data dell'inizio schedulazione (il job è in corso)
                    rispostaControlli.setrDate(dataInizio);
                    rispostaControlli.setrObject(Constants.TipiRegLogJob.INIZIO_SCHEDULAZIONE);
                }
            } else {
                // se il job non ha mai "girato", rendo una condizione
                // di job terminato correttamente, che non
                // produrrà allarmi in sede di valutazione del
                // monitoraggio
                rispostaControlli.setrDate(new Date(0));
                rispostaControlli.setrObject(Constants.TipiRegLogJob.FINE_SCHEDULAZIONE);
            }
            rispostaControlli.setrBoolean(true);
        } catch (Exception e) {
            rispostaControlli.setCodErr(MessaggiWSBundle.ERR_666);
            rispostaControlli.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.ERR_666,
                    "Eccezione ControlliMonitor.leggiUltimaRegistrazione " + e.getMessage()));
            log.error("Eccezione nella lettura della tabella dei log dei job", e);
        }

        return rispostaControlli;
    }

    public RispostaControlli leggiAllarmiInIntervallo(String jobName, Date dataInizio, Date ultimaAttivitaDelJob) {
        RispostaControlli rispostaControlli;
        rispostaControlli = new RispostaControlli();
        rispostaControlli.setrBoolean(false);
        Long numAllarmi;

        try {
            String queryStr = "select count(j) from LogJob j " + "where j.dtRegLogJob >= :dataInizio "
                    + "and j.dtRegLogJob < :ultimaAttivita " + "and j.tiRegLogJob = :tiRegLogJob "
                    + "and j.nmJob = :nmJob ";

            javax.persistence.Query query = entityManager.createQuery(queryStr);
            query.setParameter("nmJob", jobName);
            query.setParameter("tiRegLogJob", Constants.TipiRegLogJob.ERRORE.name());
            query.setParameter("dataInizio", dataInizio);
            query.setParameter("ultimaAttivita", ultimaAttivitaDelJob);
            numAllarmi = (Long) query.getSingleResult();
            rispostaControlli.setrLong(numAllarmi);
            rispostaControlli.setrBoolean(true);
        } catch (Exception e) {
            rispostaControlli.setCodErr(MessaggiWSBundle.ERR_666);
            rispostaControlli.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.ERR_666,
                    "Eccezione ControlliMonitor.leggiAllarmiInIntervallo " + e.getMessage()));
            log.error("Eccezione nella lettura della tabella dei log dei job ", e);
        }

        return rispostaControlli;
    }

    public boolean controllaStatoDbOracle() {
        boolean resp = false;
        try {
            String queryStr = "select 1 from dual";
            javax.persistence.Query query = entityManager.createNativeQuery(queryStr);
            BigDecimal r = (BigDecimal) query.getSingleResult();
            if (r.longValue() == 1L) {
                resp = true;
            }
        } catch (Exception e) {
            log.error("Problema nella connessione al db Oracle: ", e);
        }
        return resp;
    }

    public RispostaControlli controllaPresenzaErroriReplica() {
        RispostaControlli rispostaControlli;
        rispostaControlli = new RispostaControlli();
        rispostaControlli.setrBoolean(false);
        long numErrori;

        try {
            String queryStr = "select count(u) from LogUserDaReplic u " + "where u.tiStatoReplic = 'REPLICA_IN_ERRORE'";
            javax.persistence.Query query = entityManager.createQuery(queryStr);
            numErrori = (Long) query.getSingleResult();
            rispostaControlli.setrLong(numErrori);
            rispostaControlli.setrBoolean(true);
        } catch (Exception e) {
            rispostaControlli.setCodErr(MessaggiWSBundle.ERR_666);
            rispostaControlli.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.ERR_666,
                    "Eccezione ControlliMonitor.controllaPresenzaErroriReplica " + e.getMessage()));
            log.error("Eccezione nella lettura della tabella LogUserDaReplic ", e);
        }

        return rispostaControlli;
    }

    public RispostaControlli controllaPresenzaTimeoutReplica() {
        RispostaControlli rispostaControlli;
        rispostaControlli = new RispostaControlli();
        rispostaControlli.setrBoolean(false);
        long numErrori;

        try {
            String queryStr = "select count(u) from LogUserDaReplic u "
                    + "where u.tiStatoReplic = 'REPLICA_IN_TIMEOUT'";
            javax.persistence.Query query = entityManager.createQuery(queryStr);
            numErrori = (Long) query.getSingleResult();
            rispostaControlli.setrLong(numErrori);
            rispostaControlli.setrBoolean(true);
        } catch (Exception e) {
            rispostaControlli.setCodErr(MessaggiWSBundle.ERR_666);
            rispostaControlli.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.ERR_666,
                    "Eccezione ControlliMonitor.controllaPresenzaTimeoutReplica " + e.getMessage()));
            log.error("Eccezione nella lettura della tabella LogUserDaReplic ", e);
        }

        return rispostaControlli;
    }

    public RispostaControlli replicheBloccateUltimoMese() {
        RispostaControlli rispostaControlli;
        rispostaControlli = new RispostaControlli();
        rispostaControlli.setrBoolean(false);
        try {
            Date dataOrarioDa = this.sottraiUnMese(new Date());
            List<UsrVLisSched> uvls;
            String queryStr = "select s from UsrVLisSched s " + "where s.nmJob like 'REPLICA_UTENTI_%' "
                    + "AND s.tiBlocco IS NOT NULL " + "AND s.dtRegLogJobIni >= :datada " + "AND (s.dlMsgErr IS NULL "
                    + "OR s.dlMsgErr != 'Job terminato forzatamente per sblocco repliche in corso "
                    + "non eseguite entro tempo limite')";
            javax.persistence.Query query = entityManager.createQuery(queryStr);
            query.setParameter("datada", dataOrarioDa, TemporalType.TIMESTAMP);
            uvls = query.getResultList();
            rispostaControlli.setrLong(uvls.size());
            rispostaControlli.setrBoolean(true);
        } catch (Exception e) {
            rispostaControlli.setCodErr(MessaggiWSBundle.ERR_666);
            rispostaControlli.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.ERR_666,
                    "Eccezione ControlliMonitor.replicheBloccateUltimoMese " + e.getMessage()));
            log.error("Eccezione nella lettura della vista UsrVLisSched ", e);
        }
        return rispostaControlli;
    }

    public Date sottraiUnGiorno(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    public Date sottraiUnMese(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }
}
