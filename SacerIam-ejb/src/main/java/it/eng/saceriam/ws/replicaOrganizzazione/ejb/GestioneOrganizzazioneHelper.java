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

package it.eng.saceriam.ws.replicaOrganizzazione.ejb;

import static it.eng.paginator.util.HibernateUtils.bigDecimalFrom;
import static it.eng.paginator.util.HibernateUtils.longFrom;
import it.eng.parer.sacerlog.ejb.SacerLogEjb;
import it.eng.saceriam.entity.*;
import it.eng.saceriam.exception.TransactionException;
import it.eng.saceriam.helper.GenericHelper;
import it.eng.saceriam.web.helper.UserHelper;
import it.eng.saceriam.web.util.ApplEnum;
import it.eng.saceriam.ws.dto.IRispostaWS;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.*;
import it.eng.saceriam.ws.utils.MessaggiWSBundle;
import java.math.BigDecimal;
import java.util.*;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Gilioli_P
 */
@Stateless(mappedName = "GestioneOrganizzazioneHelper")
@LocalBean
public class GestioneOrganizzazioneHelper extends GenericHelper {

    private static final Logger log = LoggerFactory.getLogger(GestioneOrganizzazioneHelper.class);

    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager entityManager;

    @EJB
    private UserHelper userHelper;
    @EJB
    private SacerLogEjb sacerLogEjb;

    /**
     * Metodo che restituisce l'entità UsrOrganizIam dati in ingresso i parametri che la
     * identificano univocamente
     *
     * @param idApplic        id applicazione
     * @param idOrganizApplic id organizzazione
     * @param nmTipoOrganiz   nome tipo organizzazione
     *
     * @return l'entità di tipo UsrOrganizIam
     *
     */
    public UsrOrganizIam getUsrOrganizIam(Long idApplic, Integer idOrganizApplic,
	    String nmTipoOrganiz) {
	String queryStr = "SELECT u FROM UsrOrganizIam u "
		+ "WHERE u.aplApplic.idApplic = :idApplic "
		+ "AND u.idOrganizApplic = :idOrganizApplic "
		+ "AND u.aplTipoOrganiz.nmTipoOrganiz = :nmTipoOrganiz";
	Query query = entityManager.createQuery(queryStr);
	query.setParameter("idApplic", idApplic);
	query.setParameter("idOrganizApplic", bigDecimalFrom(idOrganizApplic));
	query.setParameter("nmTipoOrganiz", nmTipoOrganiz);
	List<UsrOrganizIam> organizList = query.getResultList();
	if (!organizList.isEmpty()) {
	    return organizList.get(0);
	} else {
	    return null;
	}
    }

    /**
     * Ritorna la lista di enti convenzionati per l'organizzazione passata in input
     *
     * @param idOrganizIam id organizzazione, potrà restituire risultati nel caso in cui venga
     *                     passata una struttura
     *
     * @return la lista degli enti convenzionati
     */
    public List<OrgEnteConvenzOrg> retrieveOrgEnteConvenzOrg(long idOrganizIam) {
	Query query = getEntityManager().createQuery(
		"SELECT s FROM OrgEnteConvenzOrg s WHERE s.usrOrganizIam.idOrganizIam = :idOrganizIam");
	query.setParameter("idOrganizIam", idOrganizIam);
	List<OrgEnteConvenzOrg> list = query.getResultList();
	return list;
    }

    /**
     * Metodo che ritorna l'informazione se l'organizzazione è di ultimo livello
     *
     * @param idApplic      id applicazione
     * @param nmTipoOrganiz nome tipo organizzazione
     *
     * @return true, se l'organizzazione è di ultimo livello
     */
    public boolean isLastLevel(BigDecimal idApplic, String nmTipoOrganiz) {
	String queryStr = "SELECT u.flLastLivello FROM AplTipoOrganiz u "
		+ "WHERE u.aplApplic.idApplic = :idApplic "
		+ "AND u.nmTipoOrganiz = :nmTipoOrganiz ";
	Query query = entityManager.createQuery(queryStr);
	query.setParameter("idApplic", longFrom(idApplic));
	query.setParameter("nmTipoOrganiz", nmTipoOrganiz);
	String flLastLevel = (String) query.getSingleResult();
	if (flLastLevel.equals("1")) {
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * Metodo che ritorna l'insieme degli utenti per i quali è definita una dichiarazione relativa
     * ad una organizzazione "padre" di quella passata come parametro di ingresso, oppure è definita
     * una dichiarazione relativa a tutte le organizzazioni, oppure è definita una dichiarazione
     * relativa all'organizzazione passata come parametro di ingresso.
     *
     * @param idOrganizIam id organizzazione
     * @param tiOperReplic tipo operazione replica
     *
     * @return l'insieme degli utenti
     */
    public Set<BigDecimal> getUsersOnDich(Long idOrganizIam, ApplEnum.TiOperReplic tiOperReplic) {
	StringBuilder queryStr = new StringBuilder("SELECT DISTINCT u.id.idUserIam ");
	switch (tiOperReplic) {
	case INS:
	    queryStr.append("FROM UsrVAbilOrganizToAdd u ");
	    break;
	case MOD:
	case CANC:
	    queryStr.append("FROM UsrVAbilOrganiz u ");
	    break;
	}
	queryStr.append(", UsrUser user, UsrStatoUser statoUser "
		+ "WHERE u.id.idOrganizIam = :idOrganizIam "
		+ "AND u.id.idUserIam = user.idUserIam "
		+ "AND user.idStatoUserCor = statoUser.idStatoUser "
		+ "AND statoUser.tiStatoUser != 'CESSATO' ");
	Query query = entityManager.createQuery(queryStr.toString());
	query.setParameter("idOrganizIam", bigDecimalFrom(idOrganizIam));
	Set<BigDecimal> set = new HashSet<>(query.getResultList());
	return set;
    }

    /**
     * Metodo che restituisce l'insieme degli utenti per i quali è definita una dichiarazione
     * relativaad una organizzazione "padre" di quella passata come parametro di ingresso
     *
     * @param idOrganizIam id organizzazione
     *
     * @return la lista utenti (idUserIam)
     */
    public Set<BigDecimal> getUsersWithOrganizDadDich(Long idOrganizIam) {
	String queryStr = "SELECT DISTINCT u.id.idUserIam "
		+ "FROM UsrVAbilOrganiz u, UsrDichAbilOrganiz v, UsrUser user, UsrStatoUser statoUser "
		+ "WHERE u.dlPathIdOrganizIam LIKE :idOrganizIam "
		+ "AND u.idDichAbilOrganiz = v.idDichAbilOrganiz "
		+ "AND v.tiScopoDichAbilOrganiz = 'ALL_ORG_CHILD' "
		+ "AND u.id.idUserIam = user.idUserIam "
		+ "AND user.idStatoUserCor = statoUser.idStatoUser "
		+ "AND statoUser.tiStatoUser != 'CESSATO' ";
	Query query = entityManager.createQuery(queryStr);
	query.setParameter("idOrganizIam", "%" + idOrganizIam);
	Set<BigDecimal> set = new HashSet<>(query.getResultList());
	return set;
    }

    /**
     * Inserimento su DB di una nuova organizzazione
     *
     * @param ioExt      contenente i valori da inserire
     * @param rispostaWs la risposta in cui tenere traccia dell'esito dell'operazione
     *
     * @return l'organizzazione inserita
     *
     * @throws TransactionException errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public UsrOrganizIam insertOrganizIam(InserimentoOrganizzazioneExt ioExt,
	    RispostaWSInserimentoOrganizzazione rispostaWs) throws TransactionException {
	InserimentoOrganizzazioneInput parametriInput = ioExt.getInserimentoOrganizzazioneInput();
	UsrOrganizIam organiz = new UsrOrganizIam();
	try {
	    organiz.setDsOrganiz(parametriInput.getDsOrganiz());
	    organiz.setIdOrganizApplic(new BigDecimal(parametriInput.getIdOrganizApplic()));
	    organiz.setNmOrganiz(parametriInput.getNmOrganiz());
	    // Inserisco l'applicazione
	    AplApplic aplApplic = getAplApplic(ioExt.getIdApplic());
	    organiz.setAplApplic(aplApplic);
	    // Inserisco il tipo di organizzazione
	    organiz.setAplTipoOrganiz(
		    getAplTipoOrganiz(aplApplic.getIdApplic(), parametriInput.getNmTipoOrganiz()));
	    // Inserisco il padre
	    UsrOrganizIam papi = getUsrOrganizIam(aplApplic.getIdApplic(),
		    parametriInput.getIdOrganizApplicPadre(),
		    parametriInput.getNmTipoOrganizPadre());
	    organiz.setUsrOrganizIam(papi);

	    if (organiz.getAplTipoOrganiz().getNmTipoOrganiz().equals("AMBIENTE")
		    && parametriInput.getIdEnteConserv() != null
		    && parametriInput.getIdEnteGestore() != null) {
		organiz.setOrgEnteSiamConserv(getEntityManager().find(OrgEnteSiam.class,
			parametriInput.getIdEnteConserv().longValue()));
		organiz.setOrgEnteSiamGestore(getEntityManager().find(OrgEnteSiam.class,
			parametriInput.getIdEnteGestore().longValue()));
	    }

	    // Inserisco i vari tipi dato gestiti dall'organizzazione
	    ListaTipiDato listaTipiDato = parametriInput.getListaTipiDato();
	    List<UsrTipoDatoIam> usrTipoDatoIamList = new ArrayList<>();
	    if (listaTipiDato.getTipoDato() != null) {
		for (TipoDato tipoDato : listaTipiDato.getTipoDato()) {
		    UsrTipoDatoIam td = new UsrTipoDatoIam();
		    td.setUsrOrganizIam(organiz);
		    td.setNmTipoDato(tipoDato.getNmTipoDato());
		    td.setDsTipoDato(tipoDato.getDsTipoDato());
		    td.setIdTipoDatoApplic(new BigDecimal(tipoDato.getIdTipoDatoApplic()));
		    // Recupero la classe tipo dato
		    AplClasseTipoDato actd = getAplClasseTipoDato(aplApplic.getIdApplic(),
			    tipoDato.getNmClasseTipoDato());
		    td.setAplClasseTipoDato(actd);
		    usrTipoDatoIamList.add(td);
		}
	    }
	    organiz.setUsrTipoDatoIams(usrTipoDatoIamList);
	    // Persisto
	    organiz = entityManager.merge(organiz);
	    entityManager.flush();
	} catch (Exception ex) {
	    log.error(ex.getMessage(), ex);
	    rispostaWs.setSeverity(IRispostaWS.SeverityEnum.ERROR);
	    rispostaWs.setErrorCode(MessaggiWSBundle.SERVIZI_ORG_001);
	    rispostaWs.setErrorMessage(MessaggiWSBundle.getString(MessaggiWSBundle.SERVIZI_ORG_001,
		    ExceptionUtils.getRootCauseMessage(ex)));
	    throw new TransactionException(
		    "Errore nel salvataggio dell'organizzazione " + ex.getMessage(), ex);
	}
	return organiz;
    }

    /**
     * Restituisce l'entity della classe tipo dato attraverso i parametri che la identificano
     * univocamente
     *
     * @param idApplic         id applicazione
     * @param nmClasseTipoDato nome classe tipo dato
     *
     * @return entity AplClasseTipoDato
     */
    public AplClasseTipoDato getAplClasseTipoDato(Long idApplic, String nmClasseTipoDato) {
	Query ctdQuery = entityManager.createQuery("SELECT u FROM AplClasseTipoDato u "
		+ "WHERE u.nmClasseTipoDato = :nmClasseTipoDato "
		+ "AND u.aplApplic.idApplic = :idApplic ");
	ctdQuery.setParameter("idApplic", idApplic);
	ctdQuery.setParameter("nmClasseTipoDato", nmClasseTipoDato);
	AplClasseTipoDato actd = (AplClasseTipoDato) ctdQuery.getSingleResult();
	return actd;
    }

    /**
     * Restituisce l'entity della classe tipo organizzazione attraverso i parametri che la
     * identificano univocamente
     *
     * @param idApplic      id applicazione
     * @param nmTipoOrganiz nome tipo organizzazione
     *
     * @return entity AplTipoOrganiz
     */
    public AplTipoOrganiz getAplTipoOrganiz(Long idApplic, String nmTipoOrganiz) {
	Query atoQuery = entityManager.createQuery(
		"SELECT u FROM AplTipoOrganiz u " + "WHERE u.nmTipoOrganiz = :nmTipoOrganiz "
			+ "AND u.aplApplic.idApplic = :idApplic ");
	atoQuery.setParameter("idApplic", idApplic);
	atoQuery.setParameter("nmTipoOrganiz", nmTipoOrganiz);
	AplTipoOrganiz ato = (AplTipoOrganiz) atoQuery.getSingleResult();
	return ato;
    }

    /**
     * Registra nella tabella del log utenti
     *
     * @param idUserIam  id user IAM
     * @param idApplic   id applicazione
     * @param rispostaWs riposta ws
     *
     * @throws TransactionException errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void registraUtenteDaReplicare(BigDecimal idUserIam, Long idApplic,
	    IRispostaWS rispostaWs) throws TransactionException {
	try {
	    AplApplic applic = getAplApplic(idApplic);
	    LogUserDaReplic ur = new LogUserDaReplic();
	    UsrUser user = entityManager.find(UsrUser.class, idUserIam.longValue());
	    if (user == null) {
		throw new NoResultException("Nessun UsrUser trovato con idUserIam " + idUserIam);
	    }
	    ur.setAplApplic(applic);
	    ur.setIdUserIam(new BigDecimal(user.getIdUserIam()));
	    ur.setNmUserid(user.getNmUserid());
	    ur.setTiOperReplic(ApplEnum.TiOperReplic.MOD.name());
	    ur.setTiStatoReplic(ApplEnum.TiStatoReplic.DA_REPLICARE.name());
	    ur.setDtLogUserDaReplic(new Date());
	    entityManager.persist(ur);
	    entityManager.flush();
	} catch (Exception ex) {
	    log.error(ex.getMessage(), ex);
	    rispostaWs.setSeverity(IRispostaWS.SeverityEnum.ERROR);
	    rispostaWs.setErrorCode(MessaggiWSBundle.SERVIZI_ORG_001);
	    rispostaWs.setErrorMessage("Errore nella scrittura nel log utenti da replicare "
		    + ExceptionUtils.getRootCauseMessage(ex));
	    throw new TransactionException(
		    "Errore nella scrittura nel log utenti da replicare " + ex.getMessage(), ex);
	}
    }

    public AplApplic getAplApplic(Long idApplic) {
	return entityManager.getReference(AplApplic.class, idApplic);
    }

    /**
     * Cancella una organizzazione da DB
     *
     * @param idApplic       id applicazione
     * @param parametriInput bean {@link CancellaOrganizzazioneInput}
     * @param rispostaWs     risposta ws
     *
     * @throws TransactionException errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void cancellaOrganizzazione(Long idApplic, CancellaOrganizzazioneInput parametriInput,
	    RispostaWSCancellaOrganizzazione rispostaWs) throws TransactionException {
	try {
	    // Recupero l'organizzazione da cancellare
	    UsrOrganizIam organiz = getUsrOrganizIam(idApplic, parametriInput.getIdOrganizApplic(),
		    parametriInput.getNmTipoOrganiz());
	    // La elimino
	    deleteUsrOrganizIam(organiz);
	} catch (Exception ex) {
	    log.error(ex.getMessage(), ex);
	    rispostaWs.setSeverity(IRispostaWS.SeverityEnum.ERROR);
	    rispostaWs.setErrorCode(MessaggiWSBundle.SERVIZI_ORG_001);
	    rispostaWs.setErrorMessage("Errore nella cancellazione dell'organizzazione "
		    + ExceptionUtils.getRootCauseMessage(ex));
	    throw new TransactionException(
		    "Errore nella cancellazione dell'organizzazione " + ex.getMessage(), ex);
	}
    }

    public void deleteUsrOrganizIam(UsrOrganizIam organiz) {
	if (organiz != null) {
	    entityManager.remove(organiz);
	    entityManager.flush();
	}
    }

    /**
     * Aggiorno un'organizzazione presente su DB
     *
     * @param moExt      bean {@link ModificaOrganizzazioneExt}
     * @param rispostaWs risposta ws
     *
     * @return array pk
     *
     * @throws TransactionException errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int[] updateOrganizIam(ModificaOrganizzazioneExt moExt,
	    RispostaWSModificaOrganizzazione rispostaWs) throws TransactionException {
	ModificaOrganizzazioneInput parametriInput = moExt.getModificaOrganizzazioneInput();
	try {
	    // Ricavo l'organizzazione da modificare
	    UsrOrganizIam organiz = getUsrOrganizIam(moExt.getIdApplic(),
		    parametriInput.getIdOrganizApplic(), parametriInput.getNmTipoOrganiz());
	    // Ricavo l'informazione se si tratta di una struttura, e di tipo template, verificando
	    // la presenza di
	    // associazioni struttura - ente convenzionato
	    moExt.setTemplate(retrieveOrgEnteConvenzOrg(organiz.getIdOrganizIam()).isEmpty());
	    // La modifico
	    organiz.setDsOrganiz(parametriInput.getDsOrganiz());
	    organiz.setIdOrganizApplic(new BigDecimal(parametriInput.getIdOrganizApplic()));
	    organiz.setNmOrganiz(parametriInput.getNmOrganiz());
	    // Inserisco il padre
	    UsrOrganizIam papi = getUsrOrganizIam(moExt.getIdApplic(),
		    parametriInput.getIdOrganizApplicPadre(),
		    parametriInput.getNmTipoOrganizPadre());
	    organiz.setUsrOrganizIam(papi);

	    if (organiz.getAplTipoOrganiz().getNmTipoOrganiz().equals("AMBIENTE")
		    && parametriInput.getIdEnteConserv() != null
		    && parametriInput.getIdEnteGestore() != null) {
		organiz.setOrgEnteSiamConserv(getEntityManager().find(OrgEnteSiam.class,
			parametriInput.getIdEnteConserv().longValue()));
		organiz.setOrgEnteSiamGestore(getEntityManager().find(OrgEnteSiam.class,
			parametriInput.getIdEnteGestore().longValue()));
	    }

	    // Modifico i TIPI DATO relativi all'organizzazione
	    List<UsrTipoDatoIam> listaTipiDatoIam = organiz.getUsrTipoDatoIams();
	    ListaTipiDato listaTipiDatoInput = parametriInput.getListaTipiDato();
	    int numeroRecordInseriti = 0;

	    for (TipoDato tipoDato : listaTipiDatoInput) {
		boolean presente = false;
		for (UsrTipoDatoIam tipoDatoIam : listaTipiDatoIam) {
		    if (tipoDato.getIdTipoDatoApplic() == tipoDatoIam.getIdTipoDatoApplic()
			    .longValue()
			    && tipoDato.getNmClasseTipoDato().equals(
				    tipoDatoIam.getAplClasseTipoDato().getNmClasseTipoDato())) {
			// b) Aggiorno i tipi di dato presenti nella lista in input e presenti in
			// IAM
			tipoDatoIam.setNmTipoDato(tipoDato.getNmTipoDato());
			tipoDatoIam.setDsTipoDato(tipoDato.getDsTipoDato());
			presente = true;
			break;
		    }
		}
		// a) Inserisco i tipi di dato presenti nella lista in input e non presenti in IAM;
		if (!presente) {
		    UsrTipoDatoIam td = new UsrTipoDatoIam();
		    td.setUsrOrganizIam(organiz);
		    td.setNmTipoDato(tipoDato.getNmTipoDato());
		    td.setDsTipoDato(tipoDato.getDsTipoDato());
		    td.setIdTipoDatoApplic(new BigDecimal(tipoDato.getIdTipoDatoApplic()));
		    // Recupero la classe tipo dato
		    AplClasseTipoDato actd = getAplClasseTipoDato(moExt.getIdApplic(),
			    tipoDato.getNmClasseTipoDato());
		    td.setAplClasseTipoDato(actd);
		    listaTipiDatoIam.add(td);
		    numeroRecordInseriti++;
		}
	    }

	    // c) eliminare i tipi di dato presenti in IAM e non presenti nella lista in input
	    Set<Long> tipiDatoDaEliminare = new HashSet<>();
	    for (UsrTipoDatoIam tipoDatoIam : listaTipiDatoIam) {
		boolean trovato = false;
		for (TipoDato tipoDato : listaTipiDatoInput) {
		    if (tipoDato.getNmTipoDato().equals(tipoDatoIam.getNmTipoDato())
			    && tipoDato.getNmClasseTipoDato().equals(
				    tipoDatoIam.getAplClasseTipoDato().getNmClasseTipoDato())) {
			trovato = true;
			break;
		    }
		}
		if (!trovato) {
		    tipiDatoDaEliminare.add(tipoDatoIam.getIdTipoDatoIam());
		}
	    }
	    // Elimino da DB gli elementi
	    int numeroRecordEliminati = deleteUsrTipoDatoIam(tipiDatoDaEliminare);

	    int[] numRecordInsMod = new int[2];
	    numRecordInsMod[0] = numeroRecordInseriti;
	    numRecordInsMod[1] = numeroRecordEliminati;

	    return numRecordInsMod;
	} catch (Exception ex) {
	    rispostaWs.setSeverity(IRispostaWS.SeverityEnum.ERROR);
	    rispostaWs.setErrorCode(MessaggiWSBundle.SERVIZI_ORG_001);
	    rispostaWs.setErrorMessage(MessaggiWSBundle.getString(MessaggiWSBundle.SERVIZI_ORG_001,
		    ExceptionUtils.getRootCauseMessage(ex)));
	    throw new TransactionException(
		    "Errore nel salvataggio dell'organizzazione " + ex.getMessage(), ex);
	}
    }

    /**
     * Elimino in maniera massiva i tipi dato da DB
     *
     * @param idUserTipoDatoIamSet l'insieme dei tipi dato da eliminare
     *
     * @return il numero di elementi cancellati
     */
    public int deleteUsrTipoDatoIam(Set<Long> idUserTipoDatoIamSet) {
	if (!idUserTipoDatoIamSet.isEmpty()) {
	    String queryStr = "DELETE FROM UsrTipoDatoIam u WHERE u.idTipoDatoIam IN (:idUserTipoDatoIamSet) ";
	    Query query = entityManager.createQuery(queryStr);
	    query.setParameter("idUserTipoDatoIamSet", idUserTipoDatoIamSet);
	    return query.executeUpdate();
	}
	return 0;
    }

    /**
     * Metodo che restituisce il record di UsrTipoDatoIam, dati in ingresso idOrganizIam e
     * nmTipoDato
     *
     * @param idOrganizIam id organizzazione
     * @param nmTipoDato   nome tipo dato
     *
     * @return il record dell'entity in questione
     *
     */
    public UsrTipoDatoIam getUsrTipoDatoIam(Long idOrganizIam, String nmTipoDato) {
	String queryStr = "SELECT u FROM UsrTipoDatoIam u "
		+ "WHERE u.usrOrganizIam.idOrganizIam = :idOrganizIam "
		+ "AND u.nmTipoDato = :nmTipoDato ";
	Query query = entityManager.createQuery(queryStr);
	query.setParameter("idOrganizIam", idOrganizIam);
	query.setParameter("nmTipoDato", nmTipoDato);
	UsrTipoDatoIam tipoDato = (UsrTipoDatoIam) query.getSingleResult();
	return tipoDato;
    }

    public boolean isDadModified(Long idApplic, Integer idOrganizApplic, String nmTipoOrganiz,
	    Integer idOrganizApplicPadreNew) throws Exception {
	// Ricavo l'idOrganizApplic del padre dell'entity da modificare
	Integer idOrganizApplicPadre = getUsrOrganizIam(idApplic, idOrganizApplic, nmTipoOrganiz)
		.getUsrOrganizIam().getIdOrganizApplic().intValue();
	// Confronto con quello nuovo
	if (idOrganizApplicPadre.compareTo(idOrganizApplicPadreNew) != 0) {
	    return true;
	}
	return false;
    }

    public Set<BigDecimal> getUsersSacer(Long idOrganizIam) {
	Set<BigDecimal> set = null;
	Query query = entityManager.createQuery(
		"SELECT DISTINCT u.id.idUserIam FROM UsrVLisUserVigilByStrut u WHERE u.id.idOrganizIam = :idOrganizIam ");
	query.setParameter("idOrganizIam", bigDecimalFrom(idOrganizIam));
	List<BigDecimal> bl = query.getResultList();
	if (bl != null && !bl.isEmpty()) {
	    set = new HashSet<>(bl);
	} else {
	    set = new HashSet<>();
	}
	return set;
    }
}
