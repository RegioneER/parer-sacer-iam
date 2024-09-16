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

package it.eng.saceriam.job.replicaUtenti.ejb;

import static it.eng.paginator.util.HibernateUtils.*;
import it.eng.saceriam.common.Constants;
import it.eng.saceriam.entity.LogJob;
import it.eng.saceriam.entity.LogUserDaReplic;
import it.eng.saceriam.entity.UsrUser;
import it.eng.saceriam.entity.UsrUsoUserApplic;
import it.eng.saceriam.helper.GenericHelper;
import it.eng.saceriam.job.util.Costanti;
import it.eng.saceriam.viewEntity.UsrVAbilDati;
import it.eng.saceriam.viewEntity.UsrVAbilOrganiz;
import it.eng.saceriam.viewEntity.UsrVAllAutor;
import it.eng.saceriam.web.util.ApplEnum;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Gilioli_P
 */
@Stateless(mappedName = "ReplicaUtentiHelper")
@LocalBean
public class ReplicaUtentiHelper extends GenericHelper {
    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager entityManager;

    /**
     * Determina l'insieme degli utenti da replicare sulla base delle repliche relative all'applicazione e agli utenti
     * passati in input
     *
     * @return la lista dei record della tabella LogUserDaReplic
     */
    public List<BigDecimal> getUsersDaReplicList() {
        List<Object[]> userDaReplicList;
        String queryStr = "SELECT DISTINCT log.idUserIam,log.dtLogUserDaReplic FROM LogUserDaReplic log "
                + "WHERE log.tiStatoReplic IN ('DA_REPLICARE', 'REPLICA_IN_TIMEOUT', 'REPLICA_IN_ERRORE') "
                + "AND NOT EXISTS (" + "SELECT logInCorso " + "FROM LogUserDaReplic logInCorso "
                + "WHERE logInCorso.aplApplic.idApplic = log.aplApplic.idApplic "
                + "AND logInCorso.tiStatoReplic = 'REPLICA_IN_CORSO'" + "AND logInCorso.idUserIam = log.idUserIam" + ")"
                + "ORDER BY log.dtLogUserDaReplic, log.idUserIam ";
        javax.persistence.Query query = entityManager.createQuery(queryStr);
        userDaReplicList = query.getResultList();
        return userDaReplicList.stream().map(o -> BigDecimal.class.cast(o[0])).collect(Collectors.toList());
    }

    /**
     * Restituisce una lista di indici di servizi asincroni non attivi
     *
     * @param asyncJob
     *            nome job
     * @param flJobAttivo
     *            flag 1/0 (true/false)
     *
     * @return List valori job
     */
    public List<String> getFreeAsyncJobs(String asyncJob, String flJobAttivo) {
        String queryStr = "SELECT u.nmJob FROM LogVVisLastSched u WHERE u.nmJob LIKE :asyncJob";
        if (flJobAttivo != null) {
            queryStr += " AND u.flJobAttivo = :flJobAttivo";
        }
        Query query = entityManager.createQuery(queryStr);
        query.setParameter("asyncJob", asyncJob + '%');
        if (flJobAttivo != null) {
            query.setParameter("flJobAttivo", flJobAttivo);
        }
        return query.getResultList();
    }

    /**
     * Aggiorna l'insieme delle registrazioni nel log degli utenti da replicare relative agli utenti passati in input
     * settando lo stato 'REPLICA_IN_CORSO' e settando logJob
     *
     * @param users
     *            lista di utenti
     * @param idLogJob
     *            id log job
     *
     * @return numero di record aggiornati
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int updateLogUserDaReplicFkLog(List<BigDecimal> users, Long idLogJob) {
        javax.persistence.Query query = entityManager.createQuery(
                "UPDATE LogUserDaReplic log SET log.logJob = :logJob, log.tiStatoReplic = 'REPLICA_IN_CORSO' WHERE log.tiStatoReplic IN ('DA_REPLICARE', 'REPLICA_IN_TIMEOUT', 'REPLICA_IN_ERRORE') AND log.logJob IS NULL AND log.idUserIam IN (:users) ");
        query.setParameter("users", users);
        if (idLogJob != null) {
            LogJob logJob = findById(LogJob.class, idLogJob);
            query.setParameter("logJob", logJob);
        }
        return query.executeUpdate();
    }

    /**
     * Determina l'insieme delle registrazioni nel log degli utenti da replicare relative all'applicazione e agli utenti
     * passati in input
     *
     * @param users
     *            lista di utenti
     * @param idLogJob
     *            id log job
     *
     * @return la lista dei record della tabella LogUserDaReplic
     */
    public List<LogUserDaReplic> getLogUserDaReplicList(Collection<BigDecimal> users, Long idLogJob) {
        String queryStr = "SELECT log FROM LogUserDaReplic log " + "WHERE log.tiStatoReplic = 'REPLICA_IN_CORSO' "
                + "AND log.idUserIam IN (:users) " + "AND log.logJob.idLogJob = :idLogJob "
                + "ORDER BY log.dtLogUserDaReplic, log.idUserIam ";
        javax.persistence.Query query = entityManager.createQuery(queryStr);
        query.setParameter("users", users);
        query.setParameter("idLogJob", idLogJob);
        return query.getResultList();
    }

    /**
     * Scrive nel log degli utenti da replicare in base all'esito della risposta del web service
     *
     * @param idLogUserDaReplic
     *            id log user
     * @param esitoServizio
     *            esito
     * @param cdErr
     *            codice di errore
     * @param dsErr
     *            descrizione errore
     * @param applicErroreSet
     *            lista distinta errori
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void writeAtomicLogUserDaReplic(Long idLogUserDaReplic, Constants.EsitoServizio esitoServizio, String cdErr,
            String dsErr, Set<String> applicErroreSet) {
        LogUserDaReplic userDaReplic = entityManager.find(LogUserDaReplic.class, idLogUserDaReplic);
        if (userDaReplic == null) {
            throw new NoResultException("Nessun LogUserDaReplic trovato con idLogUserDaReplic " + idLogUserDaReplic);
        }
        Date now = new Date();
        // MAC#16442 - Troncare messaggio di errore in LOG_USER_DA_REPLIC
        if (dsErr != null && dsErr.length() > 1024) {
            dsErr = dsErr.substring(0, 1024);
        }
        if (esitoServizio == Constants.EsitoServizio.OK) {
            userDaReplic.setTiStatoReplic(Constants.TiStatoReplic.REPLICA_OK.name());
            userDaReplic.setCdErr(null);
            userDaReplic.setDsMsgErr(null);
            userDaReplic.setDtErr(null);
            userDaReplic.setDtChiusuraReplica(now);
        } else if (esitoServizio == Constants.EsitoServizio.KO) {
            switch (cdErr) {
            case Costanti.SERVIZI_USR_001:
            case Costanti.SERVIZI_USR_005:
                userDaReplic.setTiStatoReplic(Constants.TiStatoReplic.REPLICA_IN_ERRORE.name());
                userDaReplic.setCdErr(cdErr);
                userDaReplic.setDsMsgErr(dsErr);
                userDaReplic.setDtErr(now);
                userDaReplic.setLogJob(null);
                userDaReplic.setDtChiusuraReplica(now);
                applicErroreSet.add(userDaReplic.getAplApplic().getNmApplic());
                break;
            case Costanti.SERVIZI_USR_002:
                userDaReplic.setTiStatoReplic(Constants.TiStatoReplic.REPLICA_OK.name());
                userDaReplic.setCdErr(null);
                userDaReplic.setDsMsgErr(null);
                userDaReplic.setDtErr(null);
                userDaReplic.setDtChiusuraReplica(now);
                break;
            case Costanti.SERVIZI_USR_003:
                userDaReplic.setTiStatoReplic(Constants.TiStatoReplic.REPLICA_NON_POSSIBILE.name());
                userDaReplic.setCdErr(cdErr);
                userDaReplic.setDsMsgErr(dsErr);
                userDaReplic.setDtErr(now);
                userDaReplic.setDtChiusuraReplica(now);
                applicErroreSet.add(userDaReplic.getAplApplic().getNmApplic());
                break;
            case Costanti.SERVIZI_USR_004:
                if (userDaReplic.getTiOperReplic().equals(Constants.TiOperReplic.CANC.name())) {
                    userDaReplic.setCdErr(null);
                    userDaReplic.setDsMsgErr(null);
                    userDaReplic.setDtErr(null);
                    userDaReplic.setDtChiusuraReplica(now);
                    userDaReplic.setTiStatoReplic(Constants.TiStatoReplic.REPLICA_OK.name());
                } else if (userDaReplic.getTiOperReplic().equals(Constants.TiOperReplic.MOD.name())) {
                    userDaReplic.setTiStatoReplic(Constants.TiStatoReplic.REPLICA_NON_POSSIBILE.name());
                    userDaReplic.setCdErr(cdErr);
                    userDaReplic.setDsMsgErr(dsErr);
                    userDaReplic.setDtErr(now);
                    userDaReplic.setDtChiusuraReplica(now);
                    applicErroreSet.add(userDaReplic.getAplApplic().getNmApplic());
                }
                break;
            default:
                userDaReplic.setTiStatoReplic(Constants.TiStatoReplic.REPLICA_IN_ERRORE.name());
                userDaReplic.setCdErr(cdErr);
                userDaReplic.setDsMsgErr(dsErr);
                userDaReplic.setDtErr(now);
                userDaReplic.setLogJob(null);
                userDaReplic.setDtChiusuraReplica(now);
                applicErroreSet.add(userDaReplic.getAplApplic().getNmApplic());
            }
        } else if (esitoServizio == Constants.EsitoServizio.NO_RISPOSTA) {
            userDaReplic.setTiStatoReplic(Constants.TiStatoReplic.REPLICA_IN_TIMEOUT.name());
            userDaReplic.setCdErr(Costanti.REPLICA_UTENTE_001);
            userDaReplic.setDsMsgErr("Per l'applicazione " + userDaReplic.getAplApplic().getNmApplic()
                    + " il servizio di replica non risponde");
            userDaReplic.setDtErr(new Date());
            userDaReplic.setLogJob(null);
            userDaReplic.setDtChiusuraReplica(now);
            applicErroreSet.add(userDaReplic.getAplApplic().getNmApplic());
        }
    }

    /**
     * Determina l’insieme dei servizi web dell’applicazione corrente a cui l’utente e’ abilitato in funzione dei suoi
     * ruoli di default e l’insieme dei servizi web dell’applicazione corrente a cui l’utente e’ abilitato in funzione
     * dei suoi ruoli definiti a fronte delle dichiarazioni di abilitazione alle organizzazioni
     *
     * @param idUserIam
     *            id user IAM
     * @param idApplic
     *            id applicazione
     *
     * @return la lista di entity
     */
    public List<UsrVAllAutor> getUsrVAllAutorServiziWeb(Long idUserIam, Long idApplic) {
        String queryStr = "SELECT DISTINCT new it.eng.saceriam.viewEntity.UsrVAllAutor(u.id.tiDichAutor, "
                + "u.nmPaginaWeb, u.nmAutor, u.dsAutor, u.idOrganizApplic, u.nmTipoOrganizApplic, u.id.tiUsoRuo) "
                + "FROM UsrVAllAutor u " + "WHERE u.id.idUserIam = :idUserIam " + "AND u.id.idApplic = :idApplic "
                + "AND u.id.tiDichAutor = 'SERVIZIO_WEB' ";

        javax.persistence.Query query = entityManager.createQuery(queryStr);
        query.setParameter("idUserIam", bigDecimalFrom(idUserIam));
        query.setParameter("idApplic", bigDecimalFrom(idApplic));
        return query.getResultList();
    }

    /**
     * Determina l’insieme delle organizzazioni abilitate per l’utente e l’applicazione corrente
     *
     * @param idUserIam
     *            id user IAM
     * @param idApplic
     *            id applicazione
     *
     * @return la lista di entity
     */
    public List<UsrVAbilOrganiz> getUsrVAbilOrganizList(Long idUserIam, Long idApplic) {
        String queryStr = "SELECT DISTINCT new it.eng.saceriam.viewEntity.UsrVAbilOrganiz(u.id.idOrganizIam, u.idOrganizApplic) FROM UsrVAbilOrganiz u "
                + "WHERE u.id.idUserIam = :idUserIam " + "AND u.idApplic = :idApplic ORDER BY u.idOrganizApplic";
        javax.persistence.Query query = entityManager.createQuery(queryStr);
        query.setParameter("idUserIam", bigDecimalFrom(idUserIam));
        query.setParameter("idApplic", bigDecimalFrom(idApplic));
        return query.getResultList();
    }

    /**
     * Determina l'insieme dei tipi di dato a cui l'utente dato in input è abilitato per quella determinata applicazione
     *
     * @param idUserIam
     *            id user IAM
     * @param idApplic
     *            id applicazione
     *
     * @return la lista dei tipi dato
     */
    public List<UsrVAbilDati> getUsrVAbilDatiList(Long idUserIam, Long idApplic) {
        javax.persistence.Query query = entityManager.createQuery(
                "SELECT DISTINCT new it.eng.saceriam.viewEntity.UsrVAbilDati(u.idOrganizIam, u.idTipoDatoApplic, u.nmClasseTipoDato) FROM UsrVAbilDati u WHERE u.id.idUserIam = :idUserIam AND u.idApplic = :idApplic ORDER BY u.idOrganizIam");
        query.setParameter("idUserIam", bigDecimalFrom(idUserIam));
        query.setParameter("idApplic", bigDecimalFrom(idApplic));
        return query.getResultList();
    }

    /**
     * Ricava la lista di indirizzi IP associata all'utente
     *
     * @param idUserIam
     *            id user IAM
     *
     * @return la lista di indirizzi IP
     */
    public List<String> getUsrIndIpUserList(Long idUserIam) {
        String queryStr = "SELECT u.cdIndIpUser FROM UsrIndIpUser u " + "WHERE u.usrUser.idUserIam = :idUserIam ";

        Query query = entityManager.createQuery(queryStr);
        query.setParameter("idUserIam", idUserIam);
        return query.getResultList();
    }

    public List<String> getTiScopoDichAbilOrganiz(BigDecimal idUsoUserApplic, BigDecimal idOrganizIam) {
        List<String> scopi;
        String queryStr = "SELECT u.tiScopoDichAbilOrganiz FROM UsrDichAbilOrganiz u "
                + "WHERE u.usrUsoUserApplic.idUsoUserApplic = :idUsoUserApplic "
                + "AND u.usrOrganizIam.idOrganizIam = :idOrganizIam ";

        Query query = entityManager.createQuery(queryStr);
        query.setParameter("idUsoUserApplic", longFrom(idUsoUserApplic));
        query.setParameter("idOrganizIam", longFrom(idOrganizIam));
        scopi = query.getResultList();
        return scopi;
    }

    public BigDecimal getIdUsoUserApplic(Long idUserIam, Long idApplic) {
        BigDecimal idUsoUserApplic;
        String queryStr = "SELECT u.idUsoUserApplic FROM UsrUsoUserApplic u "
                + "WHERE u.usrUser.idUserIam = :idUserIam " + "AND u.aplApplic.idApplic = :idApplic ";

        Query query = entityManager.createQuery(queryStr);
        query.setParameter("idUserIam", idUserIam);
        query.setParameter("idApplic", idApplic);
        final List<Long> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            throw new NoResultException(
                    "Nessun UsrUsoUserApplic trovato per idUserIam " + idUserIam + " e idApplic " + idApplic);
        }
        idUsoUserApplic = new BigDecimal(resultList.get(0));
        return idUsoUserApplic;
    }

    /**
     * Determina l'insieme degli utenti da disattivare in quanto la password \u00E8 scaduta da pi\u00F9 di 3 mesi
     *
     * @return la lista dei record della tabella LogUserDaReplic
     */
    public List<Long> getUsersDaDisattivareList() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -3);
        javax.persistence.Query query = entityManager.createQuery(
                "SELECT u.idUserIam FROM UsrStatoUser stato JOIN stato.usrUser u WHERE stato.idStatoUser = u.idStatoUserCor AND u.dtScadPsw < :dtScad and u.tipoUser IN ('PERSONA_FISICA') AND stato.tiStatoUser = 'ATTIVO'");
        query.setParameter("dtScad", cal.getTime());
        return query.getResultList();
    }

    /**
     * Determina l'insieme delle associazioni utente - applicazione con servizio di replica attivo
     *
     * @param idUserIam
     *            id utente
     *
     * @return la lista dei record della tabella UsrUsoUserApplic
     */
    public List<UsrUsoUserApplic> getUsrUsoUserApplic(Long idUserIam) {
        javax.persistence.Query query = entityManager.createQuery(
                "SELECT u FROM UsrUsoUserApplic u WHERE u.usrUser.idUserIam = :idUserIam AND u.aplApplic.dsUrlReplicaUser IS NOT NULL");
        query.setParameter("idUserIam", idUserIam);
        return query.getResultList();
    }

    /**
     * Scrive nel log degli utenti da replicare la replica utente da disattivare
     *
     * @param uso
     *            entity {@link UsrUsoUserApplic}
     * @param idUserIam
     *            id user IAM
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void writeLogUserDaReplic(UsrUsoUserApplic uso, Long idUserIam) {
        UsrUser user = entityManager.find(UsrUser.class, idUserIam);
        if (user == null) {
            throw new NoResultException("Nessun UsrUser trovato con idUserIam " + idUserIam);
        }
        LogUserDaReplic ur = new LogUserDaReplic();
        ur.setAplApplic(uso.getAplApplic());
        ur.setIdUserIam(new BigDecimal(idUserIam));
        ur.setNmUserid(user.getNmUserid());
        ur.setTiOperReplic(ApplEnum.TiOperReplic.MOD.name());
        ur.setTiStatoReplic(ApplEnum.TiStatoReplic.DA_REPLICARE.name());
        ur.setDtLogUserDaReplic(new Date());

        entityManager.persist(ur);
    }

    public void flush() {
        entityManager.flush();
    }
}
