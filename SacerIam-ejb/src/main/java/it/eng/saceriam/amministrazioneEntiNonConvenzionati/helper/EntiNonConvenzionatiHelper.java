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

package it.eng.saceriam.amministrazioneEntiNonConvenzionati.helper;

import static it.eng.paginator.util.HibernateUtils.longFrom;
import static it.eng.paginator.util.HibernateUtils.longListFrom;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import it.eng.saceriam.entity.AplSistemaVersante;
import it.eng.saceriam.entity.OrgAccordoVigil;
import it.eng.saceriam.entity.OrgAmbitoTerrit;
import it.eng.saceriam.entity.OrgCategEnte;
import it.eng.saceriam.entity.OrgEnteArkRif;
import it.eng.saceriam.entity.OrgEnteConvenzOrg;
import it.eng.saceriam.entity.OrgEnteSiam;
import it.eng.saceriam.entity.OrgEnteUserRif;
import it.eng.saceriam.entity.OrgSuptEsternoEnteConvenz;
import it.eng.saceriam.entity.OrgVigilEnteProdut;
import it.eng.saceriam.entity.UsrOrganizIam;
import it.eng.saceriam.entity.UsrUser;
import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam.TiEnteConvenz;
import it.eng.saceriam.helper.GenericHelper;
import it.eng.saceriam.viewEntity.OrgVRicEnteConvenz;
import it.eng.saceriam.viewEntity.OrgVRicEnteNonConvenz;
import it.eng.saceriam.viewEntity.UsrVAbilAmbEnteConvenz;
import it.eng.saceriam.viewEntity.UsrVAbilAmbEnteXente;
import it.eng.saceriam.viewEntity.UsrVTreeOrganizIam;

/**
 * Session Bean implementation class AmministrazioneHelper Contiene i metodi, per la gestione della
 * persistenza su DB per le operazioni CRUD
 *
 */
@Stateless
@LocalBean
public class EntiNonConvenzionatiHelper extends GenericHelper {

    public List<OrgAmbitoTerrit> retrieveOrgAmbitoTerrit(String tiAmbitoTerrit) {
	Query query = getEntityManager().createQuery(
		"SELECT a FROM OrgAmbitoTerrit a WHERE a.tiAmbitoTerrit LIKE :tiAmbitoTerrit ORDER BY a.cdAmbitoTerrit");
	query.setParameter("tiAmbitoTerrit", "%" + tiAmbitoTerrit + "%");
	List<OrgAmbitoTerrit> list = query.getResultList();
	return list;
    }

    public OrgAmbitoTerrit getOrgAmbitoTerritByCode(String cdAmbitoTerritoriale) {
	// TODO: Bisogna chiedere a Sandro di fare un sinonimo per questa tabella, così non romperà
	// più le palle per
	// mettere sto alias a SACER
	Query query = getEntityManager().createQuery(
		"SELECT a FROM OrgAmbitoTerrit a WHERE a.cdAmbitoTerrit LIKE :cdAmbitoTerritoriale");
	query.setParameter("cdAmbitoTerritoriale", cdAmbitoTerritoriale);

	OrgAmbitoTerrit result = null;
	List<OrgAmbitoTerrit> list = query.getResultList();
	if (!list.isEmpty()) {
	    result = list.get(0);
	}
	return result;
    }

    public List<OrgAmbitoTerrit> retrieveOrgAmbitoTerritChildList(
	    List<BigDecimal> idAmbitoTerritoriale) {
	// TODO: Bisogna chiedere a Sandro di fare un sinonimo per questa tabella, così non romperà
	// più le palle per
	// mettere sto alias a SACER
	Query query = getEntityManager().createQuery(
		"SELECT a FROM OrgAmbitoTerrit a WHERE a.orgAmbitoTerrit.idAmbitoTerrit IN (:idAmbitoTerritoriale)");
	query.setParameter("idAmbitoTerritoriale", longListFrom(idAmbitoTerritoriale));

	List<OrgAmbitoTerrit> list = query.getResultList();
	return list;
    }

    public List<Long> retrieveIdAmbitoTerritChildList(BigDecimal idAmbitoTerrit) {
	// TODO: Bisogna chiedere a Sandro di fare un sinonimo per questa tabella, così non romperà
	// più le palle per
	// mettere sto alias a SACER
	String queryStr = "SELECT a.idAmbitoTerrit " + "FROM OrgAmbitoTerrit a "
		+ "WHERE a.orgAmbitoTerrit.idAmbitoTerrit = :idAmbitoTerrit";
	Query query = getEntityManager().createQuery(queryStr);
	query.setParameter("idAmbitoTerrit", longFrom(idAmbitoTerrit));
	List<Long> idAmbitoTerritList = query.getResultList();
	return idAmbitoTerritList;
    }

    public OrgAmbitoTerrit retrieveOrgAmbitoTerritParent(BigDecimal idAmbitoTerrit) {
	// TODO: Bisogna chiedere a Sandro di fare un sinonimo per questa tabella, così non romperà
	// più le palle per
	// mettere sto alias a SACER
	String queryStr = "SELECT ambitoTerrit.orgAmbitoTerrit FROM OrgAmbitoTerrit ambitoTerrit "
		+ "WHERE ambitoTerrit.idAmbitoTerrit = :idAmbitoTerrit ";
	Query query = getEntityManager().createQuery(queryStr);
	query.setParameter("idAmbitoTerrit", longFrom(idAmbitoTerrit));
	List<OrgAmbitoTerrit> ambitoTerritList = query.getResultList();
	if (!ambitoTerritList.isEmpty()) {
	    return ambitoTerritList.get(0);
	}
	return null;
    }

    public List<OrgCategEnte> retrieveOrgCategEnteList() {
	// TODO: Bisogna chiedere a Sandro di fare un sinonimo per questa tabella, così non romperà
	// più le palle per
	// mettere sto alias a SACER
	Query query = getEntityManager().createQuery("SELECT categ FROM OrgCategEnte categ");
	List<OrgCategEnte> list = query.getResultList();
	return list;
    }

    /**
     * Ritorna l'oggetto ente non convenzionato se esiste
     *
     * @param nmEnteSiam nome ente
     *
     * @return l'entity richiesta se esiste, oppure <code>null</code>
     */
    public OrgEnteSiam getOrgEnteSiam(String nmEnteSiam) {
	OrgEnteSiam ente = null;
	if (StringUtils.isNotBlank(nmEnteSiam)) {
	    Query query = getEntityManager().createQuery(
		    "SELECT ente FROM OrgEnteSiam ente WHERE ente.nmEnteSiam = :nmEnteSiam");
	    query.setParameter("nmEnteSiam", nmEnteSiam);
	    List<OrgEnteSiam> list = query.getResultList();
	    if (!list.isEmpty()) {
		ente = list.get(0);
	    }
	} else {
	    throw new IllegalArgumentException("Parametro nmEnteSiam nullo");
	}
	return ente;
    }

    /**
     * Ritorna l'oggetto ente convenzionato Amministratore
     *
     * @param tiEnteConvenz tipo ente convenz
     *
     * @return l'entity richiesta se esiste, oppure <code>null</code>
     */
    public OrgEnteSiam getOrgEnteConvenzByTiEnteConvenz(TiEnteConvenz tiEnteConvenz) {
	OrgEnteSiam ente = null;
	if (tiEnteConvenz != null) {
	    Query query = getEntityManager().createQuery("SELECT ente FROM OrgEnteSiam ente "
		    + "WHERE ente.tiEnteConvenz = :tiEnteConvenz ");
	    query.setParameter("tiEnteConvenz", tiEnteConvenz);
	    List<OrgEnteSiam> list = query.getResultList();
	    if (!list.isEmpty()) {
		ente = list.get(0);
	    }
	} else {
	    throw new IllegalArgumentException("Parametro tiEnteConvenz nullo");
	}
	return ente;
    }

    /**
     * Ritorna la lista degli enti non convenzionati escluso quello passato in input
     *
     * @param idEnteNonConvenzDaEscludere id ente da escludere
     *
     * @return lista elementi di tipo {@link OrgEnteSiam}
     */
    public List<OrgEnteSiam> getOrgEnteNonConvenzList(BigDecimal idEnteNonConvenzDaEscludere) {
	String queryStr = "SELECT ente FROM OrgEnteSiam ente ";
	if (idEnteNonConvenzDaEscludere != null) {
	    queryStr = queryStr + "WHERE ente.idEnteSiam != :idEnteNonConvenzDaEscludere ";
	}
	queryStr = queryStr + "ORDER BY ente.nmEnteSiam";
	Query query = getEntityManager().createQuery(queryStr);
	if (idEnteNonConvenzDaEscludere != null) {
	    query.setParameter("idEnteNonConvenzDaEscludere",
		    longFrom(idEnteNonConvenzDaEscludere));
	}
	List<OrgEnteSiam> list = query.getResultList();
	return list;
    }

    /**
     * Ritorna la lista degli enti produttore relativi all'ente passato in input
     *
     * @param idEnteFornitEst id ente fornitore esterno
     *
     * @return lista elementi di tipo {@link OrgSuptEsternoEnteConvenz}
     */
    public List<OrgSuptEsternoEnteConvenz> getOrgSuptEsternoEnteConvenzList(
	    BigDecimal idEnteFornitEst) {
	Query query = getEntityManager().createQuery("SELECT s FROM OrgSuptEsternoEnteConvenz s "
		+ "WHERE s.orgEnteSiamByIdEnteFornitEst.idEnteSiam = :idEnteFornitEst "
		+ "ORDER BY s.orgEnteSiamByIdEnteProdut.orgAmbienteEnteConvenz.nmAmbienteEnteConvenz, s.orgEnteSiamByIdEnteProdut.nmEnteSiam ");
	query.setParameter("idEnteFornitEst", longFrom(idEnteFornitEst));
	List<OrgSuptEsternoEnteConvenz> list = query.getResultList();
	return list;
    }

    /**
     * Ritorna la lista dei versatori associati relativi all'ente fornitore esterno passato in input
     *
     * @param idEnteFornitEst id fornitore esterno
     *
     * @return lista elementi di tipo {@link OrgEnteConvenzOrg}
     */
    public List<OrgEnteConvenzOrg> getOrgEnteConvenzOrgList(BigDecimal idEnteFornitEst) {
	Query query = getEntityManager().createQuery(
		"SELECT s FROM OrgEnteConvenzOrg s WHERE s.orgEnteSiam.idEnteSiam = :idEnteFornitEst ORDER BY s.dtIniVal DESC");
	query.setParameter("idEnteFornitEst", longFrom(idEnteFornitEst));
	List<OrgEnteConvenzOrg> list = query.getResultList();
	return list;
    }

    public List<OrgEnteArkRif> retrieveOrgEnteArkRif(BigDecimal idEnteConvenz) {
	String queryStr = "SELECT enteArkRif FROM OrgEnteArkRif enteArkRif "
		+ "WHERE enteArkRif.orgEnteSiam.idEnteSiam = :idEnteConvenz ";
	Query query = getEntityManager().createQuery(queryStr);
	query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
	return (List<OrgEnteArkRif>) query.getResultList();
    }

    public List<OrgEnteUserRif> retrieveOrgEnteUserRif(BigDecimal idEnteConvenz) {
	String queryStr = "SELECT enteUserRif FROM OrgEnteUserRif enteUserRif "
		+ "WHERE enteUserRif.orgEnteSiam.idEnteSiam = :idEnteConvenz "
		+ "ORDER BY enteUserRif.usrUser.nmCognomeUser, enteUserRif.usrUser.nmNomeUser ";
	Query query = getEntityManager().createQuery(queryStr);
	query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
	return (List<OrgEnteUserRif>) query.getResultList();
    }

    public List<OrgVRicEnteNonConvenz> retrieveOrgEnteNonConvenzList(String nmEnteSiam,
	    BigDecimal idUserIamCor,
	    // BigDecimal idAmbienteEnteConvenz,
	    String flEnteCessato, List<BigDecimal> idArchivista, String noArchivista,
	    Date dataOdierna, String tiEnteNonConvenz) {
	StringBuilder queryStr = new StringBuilder(
		"SELECT DISTINCT new it.eng.saceriam.viewEntity.OrgVRicEnteNonConvenz (ente.idEnteSiam, ente.nmEnteSiam, "
			+ "ente.dtIniVal, ente.dtCessazione, ente.archivista, ente.tiEnteNonConvenz) FROM OrgVRicEnteNonConvenz ente ");
	String clause = " WHERE ";
	if (StringUtils.isNotBlank(nmEnteSiam)) {
	    queryStr.append(clause).append("UPPER(ente.nmEnteSiam) like :nmEnteSiam");
	    clause = " AND ";
	}
	if (idUserIamCor != null) {
	    queryStr.append(clause).append("ente.idUserIamCor = :idUserIamCor");
	    clause = " AND ";
	}
	// if (idAmbienteEnteConvenz != null) {
	// queryStr.append(clause).append("ente.idAmbienteEnteConvenz = :idAmbienteEnteConvenz");
	// clause = " AND ";
	// }
	if (StringUtils.isNotBlank(flEnteCessato)) {
	    if (flEnteCessato.equals("1")) {
		queryStr.append(clause).append("ente.dtCessazione <= :dataOdierna");
	    } else {
		queryStr.append(clause)
			.append("(ente.dtCessazione IS NULL OR ente.dtCessazione > :dataOdierna)");
	    }
	    clause = " AND ";
	}
	if (!idArchivista.isEmpty()) {
	    if (noArchivista.equals("1")) {
		queryStr.append(clause).append(
			"(ente.idUserIamArk IN (:idArchivista) OR ente.idUserIamArk IS NULL)");
	    } else {
		queryStr.append(clause).append("ente.idUserIamArk IN (:idArchivista)");
	    }
	    clause = " AND ";
	} else {
	    // Flag checkato
	    if (noArchivista.equals("1")) {
		queryStr.append(clause).append("ente.idUserIamArk IS NULL");
		clause = " AND ";
	    }
	}
	if (StringUtils.isNotBlank(tiEnteNonConvenz)) {
	    queryStr.append(clause).append("UPPER(ente.tiEnteNonConvenz) = :tiEnteNonConvenz");
	}
	queryStr.append(" ORDER BY ente.nmEnteSiam");
	Query query = getEntityManager().createQuery(queryStr.toString());

	if (StringUtils.isNotBlank(nmEnteSiam)) {
	    query.setParameter("nmEnteSiam", "%" + nmEnteSiam.toUpperCase() + "%");
	}
	if (idUserIamCor != null) {
	    query.setParameter("idUserIamCor", idUserIamCor);
	}
	// if (idAmbienteEnteConvenz != null) {
	// query.setParameter("idAmbienteEnteConvenz",idAmbienteEnteConvenz);
	// }
	if (StringUtils.isNotBlank(flEnteCessato)) {
	    query.setParameter("dataOdierna", new Date());
	}
	if (StringUtils.isNotBlank(tiEnteNonConvenz)) {
	    query.setParameter("tiEnteNonConvenz", tiEnteNonConvenz);
	}
	if (!idArchivista.isEmpty()) {
	    query.setParameter("idArchivista", idArchivista);
	}
	List<OrgVRicEnteNonConvenz> list = query.getResultList();
	return list;
    }

    /**
     * Ritorna la lista di accordi dell'organo di vigilanza dato in input
     *
     * @param idEnteOrganoVigil id ente organo di vigilanza
     *
     * @return la lista di accordi
     */
    public List<OrgAccordoVigil> retrieveOrgAccordoVigil(BigDecimal idEnteOrganoVigil) {
	Query query = getEntityManager().createQuery(
		"SELECT s FROM OrgAccordoVigil s WHERE s.orgEnteSiamByIdEnteOrganoVigil.idEnteSiam = :idEnteOrganoVigil ORDER BY s.dtIniVal DESC");
	query.setParameter("idEnteOrganoVigil", longFrom(idEnteOrganoVigil));
	List<OrgAccordoVigil> list = query.getResultList();
	return list;
    }

    public boolean existsEnteConvenzOrgEnteCorrisp(BigDecimal idOrganizIam,
	    BigDecimal idEnteSiamProdutCorrisp) {
	String queryStr = "SELECT COUNT(enteConvenzOrg) FROM OrgEnteConvenzOrg enteConvenzOrg "
		+ "WHERE enteConvenzOrg.usrOrganizIam.idOrganizIam = :idOrganizIam "
		+ "AND enteConvenzOrg.dtIniVal <= :dataCorrente AND enteConvenzOrg.dtFineVal >= :dataCorrente "
		+ "AND enteConvenzOrg.orgEnteSiam.idEnteSiam = :idEnteSiamProdutCorrisp ";
	Query query = getEntityManager().createQuery(queryStr);
	query.setParameter("idOrganizIam", longFrom(idOrganizIam));
	query.setParameter("dataCorrente", new Date());
	query.setParameter("idEnteSiamProdutCorrisp", longFrom(idEnteSiamProdutCorrisp));

	return (Long) query.getSingleResult() > 0L;
    }

    public List<OrgVigilEnteProdut> retrieveOrgVigilEnteProdut(BigDecimal idAccordoVigil) {
	Query query = getEntityManager()
		.createQuery("SELECT vigilEnteProdut FROM OrgVigilEnteProdut vigilEnteProdut "
			+ "WHERE vigilEnteProdut.orgAccordoVigil.idAccordoVigil = :idAccordoVigil "
			+ "ORDER BY vigilEnteProdut.orgEnteSiam.orgAmbienteEnteConvenz.nmAmbienteEnteConvenz, vigilEnteProdut.orgEnteSiam.nmEnteSiam ");
	query.setParameter("idAccordoVigil", idAccordoVigil.longValue());
	List<OrgVigilEnteProdut> list = query.getResultList();
	return list;
    }

    public UsrOrganizIam getUsrOrganizIam(String nmApplic, String nmTipoOrganiz,
	    BigDecimal idOrganizApplic) {
	Query query = getEntityManager()
		.createQuery("SELECT organizIam FROM UsrOrganizIam organizIam "
			+ "JOIN organizIam.aplApplic applic "
			+ "JOIN organizIam.aplTipoOrganiz tipoOrganiz "
			+ "WHERE applic.nmApplic = :nmApplic "
			+ "AND tipoOrganiz.nmTipoOrganiz = :nmTipoOrganiz "
			+ "AND tipoOrganiz.aplApplic = applic "
			+ "AND organizIam.idOrganizApplic = :idOrganizApplic");
	query.setParameter("nmApplic", nmApplic);
	query.setParameter("nmTipoOrganiz", nmTipoOrganiz);
	query.setParameter("idOrganizApplic", idOrganizApplic);
	List<UsrOrganizIam> list = query.getResultList();
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    public boolean isUtenteArchivistaInEnteConvenz(BigDecimal idEnteConvenz, BigDecimal idUserIam) {
	Query query = getEntityManager()
		.createQuery("SELECT enteArkRif FROM OrgEnteArkRif enteArkRif "
			+ "WHERE enteArkRif.orgEnteSiam.idEnteSiam = :idEnteConvenz "
			+ "AND enteArkRif.usrUser.idUserIam = :idUserIam ");
	query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
	query.setParameter("idUserIam", longFrom(idUserIam));
	List<OrgEnteArkRif> list = query.getResultList();
	return !list.isEmpty();
    }

    public boolean isReferenteEnteInEnteConvenz(BigDecimal idEnteConvenz, BigDecimal idUserIam) {
	Query query = getEntityManager()
		.createQuery("SELECT enteUserRif FROM OrgEnteUserRif enteUserRif "
			+ "WHERE enteUserRif.orgEnteSiam.idEnteSiam = :idEnteConvenz "
			+ "AND enteUserRif.usrUser.idUserIam = :idUserIam ");
	query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
	query.setParameter("idUserIam", longFrom(idUserIam));
	List<OrgEnteUserRif> list = query.getResultList();
	return !list.isEmpty();
    }

    public boolean isReferenteEnteCessato(BigDecimal idUserIam) {
	Query query = getEntityManager()
		.createQuery("SELECT user FROM UsrStatoUser stato JOIN stato.usrUser user "
			+ "WHERE stato.idStatoUser = user.idStatoUserCor AND user.idUserIam = :idUserIam AND stato.tiStatoUser = 'CESSATO'");
	query.setParameter("idUserIam", longFrom(idUserIam));
	List<UsrUser> list = query.getResultList();
	return !list.isEmpty();
    }

    public List<UsrUser> retrieveUtentiArchivisti() {
	String queryStr = "SELECT DISTINCT(enteArkRif.usrUser) FROM OrgEnteArkRif enteArkRif "
		+ "ORDER BY enteArkRif.usrUser.nmCognomeUser ";
	Query query = getEntityManager().createQuery(queryStr);
	return (List<UsrUser>) query.getResultList();
    }

    @Deprecated
    public List<OrgVRicEnteNonConvenz> retrieveEntiNonConvenzAbilitati(BigDecimal idUserIam) {
	Query query = getEntityManager().createQuery(
		"SELECT ricEnteNonConvenz FROM OrgVRicEnteNonConvenz ricEnteNonConvenz "
			+ "WHERE ricEnteNonConvenz.idUserIam = :idUserIam "
			+ "ORDER BY ricEnteNonConvenz.nmEnteConvenz ");
	query.setParameter("idUserIam", longFrom(idUserIam));
	return (List<OrgVRicEnteNonConvenz>) query.getResultList();
    }

    public List<UsrVAbilAmbEnteConvenz> retrieveAmbientiEntiConvenzAbilitati(BigDecimal idUserIam) {
	Query query = getEntityManager().createQuery(
		"SELECT abilAmbEnteConvenz FROM UsrVAbilAmbEnteConvenz abilAmbEnteConvenz "
			+ "WHERE abilAmbEnteConvenz.id.idUserIam = :idUserIam "
			+ "ORDER BY abilAmbEnteConvenz.nmAmbienteEnteConvenz ");
	query.setParameter("idUserIam", idUserIam);
	return (List<UsrVAbilAmbEnteConvenz>) query.getResultList();
    }

    public List<UsrVAbilAmbEnteXente> retrieveAmbientiEntiXenteAbilitati(BigDecimal idUserIam) {
	Query query = getEntityManager()
		.createQuery("SELECT abilAmbEnteXente FROM UsrVAbilAmbEnteXente abilAmbEnteXente "
			+ "WHERE abilAmbEnteXente.id.idUserIam = :idUserIam "
			+ "ORDER BY abilAmbEnteXente.nmAmbienteEnteConvenz ");
	query.setParameter("idUserIam", idUserIam);
	return (List<UsrVAbilAmbEnteXente>) query.getResultList();
    }

    public List<UsrVAbilAmbEnteXente> retrieveAbilAmbEnteXEnteValidAmbienti(BigDecimal idUserIam) {
	Query query = getEntityManager().createQuery("SELECT abilAmbEnteXEnte "
		+ "FROM OrgAmbienteEnteConvenz ambienteEnteConvenz, UsrVAbilAmbEnteXente abilAmbEnteXEnte "
		+ "WHERE abilAmbEnteXEnte.id.idUserIam = :idUserIam "
		+ "AND ambienteEnteConvenz.idAmbienteEnteConvenz = abilAmbEnteXEnte.id.idAmbienteEnteConvenz "
		+ "AND ambienteEnteConvenz.dtIniVal <= :dataOdierna AND ambienteEnteConvenz.dtFineVal >= :dataOdierna "
		+ "ORDER BY abilAmbEnteXEnte.nmAmbienteEnteConvenz ");
	query.setParameter("idUserIam", idUserIam);
	query.setParameter("dataOdierna", new Date());
	return (List<UsrVAbilAmbEnteXente>) query.getResultList();
    }

    public List<OrgVRicEnteConvenz> retrieveEntiConvenzAbilitatiAccordoValido(
	    BigDecimal idUserIamCor, BigDecimal idAmbienteEnteConvenz) {
	Query query = getEntityManager()
		.createQuery("SELECT ricEnteConvenz FROM OrgVRicEnteConvenz ricEnteConvenz "
			+ "WHERE ricEnteConvenz.idUserIamCor = :idUserIamCor AND ricEnteConvenz.idAmbienteEnteConvenz = :idAmbienteEnteConvenz "
			// MAC#22374
			+ "AND EXISTS (SELECT acc FROM OrgAccordoEnte acc WHERE acc.orgEnteSiam.idEnteSiam = ricEnteConvenz.idEnteConvenz "
			+ "AND acc.dtDecAccordo <= :dataCorrente AND acc.dtFineValidAccordo > :dataCorrente) "
			// end MAC#22374
			+ "ORDER BY ricEnteConvenz.nmEnteConvenz ");
	query.setParameter("idUserIamCor", idUserIamCor);
	query.setParameter("idAmbienteEnteConvenz", idAmbienteEnteConvenz);
	query.setParameter("dataCorrente", new Date());
	return (List<OrgVRicEnteConvenz>) query.getResultList();
    }

    /**
     * Ritorna la lista di enti non convenzionati per l'ambiente dato in input
     *
     * @param idAmbienteEnteConvenz id ambiente ente non convenzionato
     *
     * @return la lista di enti non convenzionati
     */
    @Deprecated
    public List<OrgVRicEnteNonConvenz> retrieveOrgVRicEnteNonConvenzByAmbiente(
	    BigDecimal idAmbienteEnteConvenz) {
	Query query = getEntityManager().createQuery(
		"SELECT DISTINCT new it.eng.saceriam.viewEntity.OrgVRicEnteNonConvenz (ricEnteNonConvenz.idEnteSiam, "
			+ "ricEnteNonConvenz.nmEnteSiam, ricEnteNonConvenz.dtIniVal, ricEnteNonConvenz.dtCessazione, ricEnteNonConvenz.archivista) "
			+ "FROM OrgVRicEnteNonConvenz ricEnteNonConvenz WHERE ricEnteNonconvenz.idAmbienteEnteConvenz = :idAmbienteEnteConvenz "
			+ "ORDER BY ricEnteNonConvenz.nmEnteSiam ");
	query.setParameter("idAmbienteEnteConvenz", longFrom(idAmbienteEnteConvenz));
	List<OrgVRicEnteNonConvenz> list = query.getResultList();
	return list;
    }

    public boolean existsEnteSuptPerEnteFornit(BigDecimal idEnteFornitEst, BigDecimal idEnteProdut,
	    Date dtIniValSupt, Date dtFinValSupt, BigDecimal idSuptEstEnteConvenzDaEscludere) {
	String queryStr = "SELECT 1 FROM OrgSuptEsternoEnteConvenz suptEstEnteConvenz "
		+ "WHERE suptEstEnteConvenz.orgEnteSiamByIdEnteFornitEst.idEnteSiam = :idEnteFornitEst "
		+ "AND suptEstEnteConvenz.orgEnteSiamByIdEnteProdut.idEnteSiam = :idEnteProdut "
		+ "AND ( "
		// Se la data di inizio validità o quella di fine validità, ricadono all'interno di
		// un intervallo già
		// esistente
		+ "(suptEstEnteConvenz.dtIniVal <= :dtIniValSupt AND suptEstEnteConvenz.dtFinVal >= :dtIniValSupt) "
		+ "OR (suptEstEnteConvenz.dtIniVal <= :dtFinValSupt AND suptEstEnteConvenz.dtFinVal >= :dtFinValSupt) "
		// oppure se l'intevallo del nuovo ente convenz supt si sovrappone totalmente ad un
		// intervallo già
		// esistente
		+ "OR (suptEstEnteConvenz.dtIniVal >= :dtIniValSupt AND suptEstEnteConvenz.dtFinVal <= :dtFinValSupt)) ";
	if (idSuptEstEnteConvenzDaEscludere != null) {
	    queryStr = queryStr
		    + "AND suptEstEnteConvenz.idSuptEstEnteConvenz != :idSuptEstEnteConvenzDaEscludere ";
	}
	Query query = getEntityManager().createQuery(queryStr);
	query.setParameter("idEnteFornitEst", longFrom(idEnteFornitEst));
	query.setParameter("idEnteProdut", longFrom(idEnteProdut));
	query.setParameter("dtIniValSupt", dtIniValSupt);
	query.setParameter("dtFinValSupt", dtFinValSupt);
	if (idSuptEstEnteConvenzDaEscludere != null) {
	    query.setParameter("idSuptEstEnteConvenzDaEscludere",
		    longFrom(idSuptEstEnteConvenzDaEscludere));
	}

	query.setMaxResults(1);
	return !query.getResultList().isEmpty();
    }

    public Long countEnteSuptPerEnteFornit(BigDecimal idEnteFornitEst, BigDecimal idEnteProdut) {
	String queryStr = "SELECT COUNT(suptEstEnteConvenz) FROM OrgSuptEsternoEnteConvenz suptEstEnteConvenz "
		+ "WHERE suptEstEnteConvenz.orgEnteSiamByIdEnteFornitEst.idEnteSiam = :idEnteFornitEst "
		+ "AND suptEstEnteConvenz.orgEnteSiamByIdEnteProdut.idEnteSiam = :idEnteProdut ";

	Query query = getEntityManager().createQuery(queryStr);
	query.setParameter("idEnteFornitEst", longFrom(idEnteFornitEst));
	query.setParameter("idEnteProdut", longFrom(idEnteProdut));

	return (Long) query.getSingleResult();
    }

    public List<AplSistemaVersante> getAplSistemaVersanteById(BigDecimal idEnteSiam) {
	List<AplSistemaVersante> listaSistemiVersanti = new ArrayList<>();
	if (idEnteSiam != null) {
	    String queryStr = "SELECT sistemaVersante FROM AplSistemaVersante sistemaVersante "
		    + "WHERE sistemaVersante.orgEnteSiam.idEnteSiam = :idEnteSiam ";
	    Query query = getEntityManager().createQuery(queryStr);
	    query.setParameter("idEnteSiam", longFrom(idEnteSiam));
	    listaSistemiVersanti = (List<AplSistemaVersante>) query.getResultList();
	}
	return listaSistemiVersanti;
    }

    public String[] getStrutUnitaDocAccordoOrganizMap(BigDecimal idOrganizApplic) {
	String queryStr = "SELECT treeOrganizIam FROM UsrVTreeOrganizIam treeOrganizIam "
		+ "WHERE treeOrganizIam.idOrganizApplic = :idOrganizApplic "
		+ "AND treeOrganizIam.nmTipoOrganiz = 'STRUTTURA' ";
	Query query = getEntityManager().createQuery(queryStr);
	query.setParameter("idOrganizApplic", idOrganizApplic);
	List<UsrVTreeOrganizIam> treeOrganizIamList = (List<UsrVTreeOrganizIam>) query
		.getResultList();
	UsrVTreeOrganizIam treeOrganizIam = treeOrganizIamList.get(0);
	return StringUtils.split(treeOrganizIam.getDlPathIdOrganizIam(), "/");
    }

    /**
     * Ritorna l'informazione sull'esistenza o meno di altri accordi di vigilanza con periodo di
     * validità sovrapposto a quello passato in input
     *
     * @param idEnteOrganoVigil    ente organo di vigilanza
     * @param dtIniVal             data di inizio validità dell'accordo che si vuole inserire
     * @param dtFinVal             data di fine validità dell'accordo che si vuole inserire
     * @param idAccordoDaEscludere accordo di vigilanza che si vuole escludere dal controllo
     *                             (l'accordo oggetto di inserimento/modifica)
     *
     * @return true o false a seconda che esistano già altri accordi di vigilanza con date
     *         sovrapposte
     */
    public boolean checkEsistenzaAccordiDtIniDtFineVal(BigDecimal idEnteOrganoVigil, Date dtIniVal,
	    Date dtFinVal, BigDecimal idAccordoDaEscludere) {
	String queryStr = "SELECT COUNT(accordoVigil) FROM OrgAccordoVigil accordoVigil "
		+ "WHERE accordoVigil.orgEnteSiamByIdEnteOrganoVigil.idEnteSiam = :idEnteOrganoVigil "
		+ "AND ("
		// Se la data di inizio validità o quella di fine validità accordo, ricadono
		// all'interno di un
		// intervallo già esistente
		+ "(accordoVigil.dtIniVal <= :dtIniVal AND accordoVigil.dtFinVal >= :dtIniVal) "
		+ "OR (accordoVigil.dtIniVal <= :dtFinVal AND accordoVigil.dtFinVal >= :dtFinVal) "
		// oppure se l'intevallo del nuovo accordo si sovrappone totalmente ad un intervallo
		// già esistente
		+ "OR (accordoVigil.dtIniVal >= :dtIniVal AND accordoVigil.dtFinVal <= :dtFinVal))";
	if (idAccordoDaEscludere != null) {
	    queryStr = queryStr + "AND accordoVigil.idAccordoVigil != :idAccordoDaEscludere ";
	}
	Query query = getEntityManager().createQuery(queryStr);
	query.setParameter("idEnteOrganoVigil", longFrom(idEnteOrganoVigil));
	query.setParameter("dtIniVal", dtIniVal);
	query.setParameter("dtFinVal", dtFinVal);
	if (idAccordoDaEscludere != null) {
	    query.setParameter("idAccordoDaEscludere", longFrom(idAccordoDaEscludere));
	}
	return (Long) query.getSingleResult() != 0;
    }

    public boolean existsEnteVigilatoPerAccordoVigil(BigDecimal idAccordoVigil,
	    BigDecimal idEnteProdut, Date dtIniValVigil, Date dtFinValVigil,
	    BigDecimal idVigilEnteProdutEscludere) {
	String queryStr = "SELECT 1 FROM OrgVigilEnteProdut vigilEnteProdut "
		+ "WHERE vigilEnteProdut.orgAccordoVigil.idAccordoVigil = :idAccordoVigil "
		+ "AND vigilEnteProdut.orgEnteSiam.idEnteSiam = :idEnteProdut " + "AND ( "
		// Se la data di inizio validità o quella di fine validità, ricadono all'interno di
		// un intervallo già
		// esistente
		+ "(vigilEnteProdut.dtIniVal <= :dtIniValVigil AND vigilEnteProdut.dtFinVal >= :dtIniValVigil) "
		+ "OR (vigilEnteProdut.dtIniVal <= :dtFinValVigil AND vigilEnteProdut.dtFinVal >= :dtFinValVigil) "
		// oppure se l'intevallo del nuovo ente convenz supt si sovrappone totalmente ad un
		// intervallo già
		// esistente
		+ "OR (vigilEnteProdut.dtIniVal >= :dtIniValVigil AND vigilEnteProdut.dtFinVal <= :dtFinValVigil)) ";
	if (idVigilEnteProdutEscludere != null) {
	    queryStr = queryStr
		    + "AND vigilEnteProdut.idVigilEnteProdut != :idVigilEnteProdutEscludere ";
	}
	Query query = getEntityManager().createQuery(queryStr);
	query.setParameter("idAccordoVigil", longFrom(idAccordoVigil));
	query.setParameter("idEnteProdut", longFrom(idEnteProdut));
	query.setParameter("dtIniValVigil", dtIniValVigil);
	query.setParameter("dtFinValVigil", dtFinValVigil);
	if (idVigilEnteProdutEscludere != null) {
	    query.setParameter("idVigilEnteProdutEscludere", longFrom(idVigilEnteProdutEscludere));
	}

	query.setMaxResults(1);
	return !query.getResultList().isEmpty();
    }
}
