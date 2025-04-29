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

package it.eng.saceriam.web.helper;

import static it.eng.paginator.util.HibernateUtils.bigDecimalFrom;
import static it.eng.paginator.util.HibernateUtils.longFrom;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.eng.saceriam.entity.AplSistemaVersArkRif;
import it.eng.saceriam.entity.AplSistemaVersante;
import it.eng.saceriam.entity.AplSistemaVersanteUserRef;
import it.eng.saceriam.entity.OrgEnteSiam;
import it.eng.saceriam.entity.OrgServizioErog;
import it.eng.saceriam.entity.UsrUser;
import it.eng.saceriam.helper.GenericHelper;
import it.eng.saceriam.slite.gen.form.AmministrazioneSistemiVersantiForm;
import it.eng.saceriam.slite.gen.form.AmministrazioneSistemiVersantiForm.FiltriSistemiVersanti;
import it.eng.saceriam.util.DateUtil;
import it.eng.saceriam.viewEntity.AplVLisOrganizUsoSisVers;
import it.eng.saceriam.viewEntity.AplVLisSistVersPerAutoma;
import it.eng.saceriam.viewEntity.AplVRicSistemaVersante;
import it.eng.saceriam.viewEntity.UsrVTreeOrganizIam;
import it.eng.spagoCore.error.EMFError;

@Stateless
@LocalBean
public class SistemiVersantiHelper extends GenericHelper {

    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager em;

    /**
     * Restituisce la lista dei sistemi versanti presenti nella vista AplVRicSistemaVersante in base
     * ai parametri di ricerca impostati e all'utente
     *
     * @param filtri    bean di tipo {@link FiltriSistemiVersanti}
     * @param idUserIam id user IAM
     *
     * @return lista elementi di tipo {@link AplVRicSistemaVersante}
     *
     * @throws EMFError errore generico
     */
    public List<AplVRicSistemaVersante> getAplVRicSistemaVersanteList(//
	    AmministrazioneSistemiVersantiForm.FiltriSistemiVersanti filtri, long idUserIam)
	    throws EMFError {
	return getAplVRicSistemaVersanteList(new FiltriSistemiVersantiPlain(idUserIam,
		filtri.getNm_sistema_versante().parse(), filtri.getDs_sistema_versante().parse(),
		filtri.getNm_produttore().parse(), filtri.getCd_versione().parse(),
		filtri.getId_organiz_applic().parse(), filtri.getFl_cessato_filtro().parse(),
		filtri.getFl_integrazione_filtro().parse(), filtri.getArchivista().parse(),
		filtri.getNo_archivista().parse(),
		filtri.getFl_associa_persona_fisica_filtro().parse()));
    }

    /**
     * Restituisce la lista dei sistemi versanti presenti nella vista AplVRicSistemaVersante in base
     * ai parametri di ricerca impostati e all'utente. Usa un oggetto wrapper di tutti i filtri.
     *
     *
     * @param filtri filtri
     *
     * @return lista di {@link AplVRicSistemaVersante}
     */
    public List<AplVRicSistemaVersante> getAplVRicSistemaVersanteList(
	    FiltriSistemiVersantiPlain filtri) {
	String whereWord = "WHERE ";
	StringBuilder queryStr = new StringBuilder(
		"SELECT DISTINCT new it.eng.saceriam.viewEntity.AplVRicSistemaVersante "
			+ "(sistemiVersanti.id.idSistemaVersante, sistemiVersanti.nmSistemaVersante, sistemiVersanti.dsSistemaVersante, "
			+ "sistemiVersanti.nmProduttore, sistemiVersanti.dsEmail, sistemiVersanti.flPec, sistemiVersanti.flCessato, sistemiVersanti.flIntegrazione, sistemiVersanti.flAssociaPersonaFisica, sistemiVersanti.archivista) "
			+ "FROM AplVRicSistemaVersante sistemiVersanti ");

	String denominazione = filtri.getDenominazione();
	if (denominazione != null) {
	    queryStr.append(whereWord)
		    .append("UPPER(sistemiVersanti.nmSistemaVersante) LIKE :denominazione ");
	    whereWord = " AND ";
	}
	String descrizione = filtri.getDescrizione();
	if (descrizione != null) {
	    queryStr.append(whereWord)
		    .append("UPPER(sistemiVersanti.dsSistemaVersante) LIKE :descrizione ");
	    whereWord = " AND ";
	}
	String produttore = filtri.getProduttore();
	if (produttore != null) {
	    queryStr.append(whereWord)
		    .append("UPPER(sistemiVersanti.nmProduttore) LIKE :produttore ");
	    whereWord = " AND ";
	}
	String versione = filtri.getVersione();
	if (versione != null) {
	    queryStr.append(whereWord).append("sistemiVersanti.cdVersione = :cdVersione ");
	    whereWord = " AND ";
	}
	BigDecimal idOrganizApplic = filtri.getIdOrganizApplic();
	if (idOrganizApplic != null) {
	    queryStr.append(whereWord)
		    .append("sistemiVersanti.idOrganizApplic = :idOrganizApplic ");
	    whereWord = " AND ";
	    queryStr.append(whereWord).append("sistemiVersanti.nmApplic = 'SACER' ");
	    whereWord = " AND ";
	}
	String flCessato = filtri.getFlCessato();
	if (flCessato != null) {
	    queryStr.append(whereWord).append("sistemiVersanti.flCessato = :flCessato ");
	    whereWord = " AND ";
	}
	String flIntegrazione = filtri.getFlIntegrazione();
	if (flIntegrazione != null) {
	    queryStr.append(whereWord).append("sistemiVersanti.flIntegrazione = :flIntegrazione ");
	    whereWord = " AND ";
	}

	String flAssociaPersonaFisica = filtri.getFlAssociaPersonaFisica();
	if (flAssociaPersonaFisica != null) {
	    queryStr.append(whereWord)
		    .append("sistemiVersanti.flAssociaPersonaFisica = :flAssociaPersonaFisica ");
	    whereWord = " AND ";
	}

	List<BigDecimal> idArchivista = filtri.getIdArchivista();
	String noArchivista = filtri.getNoArchivista();
	if (!idArchivista.isEmpty()) {
	    if (noArchivista.equals("1")) {
		queryStr.append(whereWord).append(
			"(sistemiVersanti.idUserIamArk IN (:idArchivista) OR sistemiVersanti.idUserIamArk IS NULL) ");
	    } else {
		queryStr.append(whereWord)
			.append("sistemiVersanti.idUserIamArk IN (:idArchivista)");
	    }
	    whereWord = " AND ";
	} else {
	    // Flag checkato
	    if (noArchivista.equals("1")) {
		queryStr.append(whereWord).append("sistemiVersanti.idUserIamArk IS NULL ");
		whereWord = " AND ";
	    }
	}

	queryStr.append(whereWord).append(
		"sistemiVersanti.idUserIamCor = :idUserIam ORDER BY sistemiVersanti.nmSistemaVersante ");

	Query query = em.createQuery(queryStr.toString());

	if (denominazione != null) {
	    query.setParameter("denominazione", "%" + denominazione.toUpperCase() + "%");
	}
	if (descrizione != null) {
	    query.setParameter("descrizione", "%" + descrizione.toUpperCase() + "%");
	}
	if (produttore != null) {
	    query.setParameter("produttore", "%" + produttore.toUpperCase() + "%");
	}
	if (versione != null) {
	    query.setParameter("cdVersione", versione);
	}
	if (idOrganizApplic != null) {
	    query.setParameter("idOrganizApplic", idOrganizApplic);
	}
	if (flCessato != null) {
	    query.setParameter("flCessato", flCessato);
	}
	if (flIntegrazione != null) {
	    query.setParameter("flIntegrazione", flIntegrazione);
	}
	if (flAssociaPersonaFisica != null) {
	    query.setParameter("flAssociaPersonaFisica", flAssociaPersonaFisica);
	}
	if (!idArchivista.isEmpty()) {
	    query.setParameter("idArchivista", idArchivista);
	}
	query.setParameter("idUserIam", bigDecimalFrom(filtri.getIdUserIam()));
	List<AplVRicSistemaVersante> listaSistemiVersanti = (List<AplVRicSistemaVersante>) query
		.getResultList();

	return listaSistemiVersanti;
    }

    /**
     * Restituisce il sistema versante presente nella vista AplVRicSistemaVersante in base ai 3
     * campi passati in ingresso che formano la chiave
     *
     * @param idSistemaVersante id sistema versante
     * @param idUserIam         id user IAM
     * @param idOrganizIam      id organizzazione
     *
     * @return bean {@link AplVRicSistemaVersante}
     */
    public AplVRicSistemaVersante getAplVRicSistemaVersante(BigDecimal idSistemaVersante,
	    BigDecimal idUserIam, BigDecimal idOrganizIam) {
	AplVRicSistemaVersante sistemaVersante = null;
	if (idSistemaVersante != null && idUserIam != null && idOrganizIam != null) {
	    String queryStr = "SELECT sistemaVersante FROM AplVRicSistemaVersante sistemaVersante "
		    + "WHERE sistemaVersante.id.idSistemaVersante = :idSistemaVersante "
		    + "AND sistemaVersante.id.idUserIam = :idUserIam "
		    + "AND sistemaVersante.id.idOrganizIam = :idOrganizIam ";
	    Query query = em.createQuery(queryStr);
	    query.setParameter("idSistemaVersante", idSistemaVersante);
	    query.setParameter("idUserIam", idUserIam);
	    query.setParameter("idOrganizIam", idOrganizIam);
	    List<AplVRicSistemaVersante> listaSistemiVersanti = (List<AplVRicSistemaVersante>) query
		    .getResultList();
	    if (!listaSistemiVersanti.isEmpty()) {
		sistemaVersante = listaSistemiVersanti.get(0);
	    }
	}
	return sistemaVersante;
    }

    /**
     * Restituisce il sistema versante presente nella vista AplVRicSistemaVersante in base ai 2
     * campi passati in ingresso che formano la chiave
     *
     * @param idSistemaVersante id sistema versante
     * @param idUserIam         id user IAM
     *
     * @return bean {@link AplVRicSistemaVersante}
     */
    public AplVRicSistemaVersante getAplVRicSistemaVersante(BigDecimal idSistemaVersante,
	    long idUserIam) {
	AplVRicSistemaVersante sistemaVersante = null;
	String queryStr = "SELECT DISTINCT new it.eng.saceriam.viewEntity.AplVRicSistemaVersante("
		+ "sistemaVersante.id.idSistemaVersante, sistemaVersante.nmSistemaVersante, sistemaVersante.dsSistemaVersante, sistemaVersante.cdVersione, "
		+ "sistemaVersante.nmProduttore, sistemaVersante.flIntegrazione, sistemaVersante.flAssociaPersonaFisica, sistemaVersante.dsEmail, sistemaVersante.flPec, sistemaVersante.cdCapSedeLegale,"
		+ "sistemaVersante.dsCittaSedeLegale, sistemaVersante.dsViaSedeLegale, sistemaVersante.idEnteSiam, sistemaVersante.dtIniVal, sistemaVersante.dtFineVal, sistemaVersante.dsNote) "
		+ "FROM AplVRicSistemaVersante sistemaVersante "
		+ "WHERE sistemaVersante.id.idSistemaVersante = :idSistemaVersante "
		+ "AND sistemaVersante.idUserIamCor = :idUserIam ";
	Query query = em.createQuery(queryStr);
	query.setParameter("idSistemaVersante", idSistemaVersante);
	query.setParameter("idUserIam", bigDecimalFrom(idUserIam));
	List<AplVRicSistemaVersante> listaSistemiVersanti = (List<AplVRicSistemaVersante>) query
		.getResultList();
	if (listaSistemiVersanti.size() == 1) {
	    sistemaVersante = listaSistemiVersanti.get(0);
	}
	return sistemaVersante;
    }

    public long insertAplSistemaVersante(String denominazione, String descrizione, String versione,
	    BigDecimal idEnteSiam, String dsEmail, String flPec, String flIntegrazione,
	    String flAssociaPersonaFisica, Date dtIniVal, Date dtFineVal, String dsNote) {
	AplSistemaVersante sistemaVersante = new AplSistemaVersante();
	sistemaVersante.setNmSistemaVersante(denominazione);
	sistemaVersante.setDsSistemaVersante(descrizione);
	sistemaVersante.setCdVersione(versione);
	sistemaVersante
		.setOrgEnteSiam(getEntityManager().find(OrgEnteSiam.class, idEnteSiam.longValue()));
	sistemaVersante.setDtIniVal(dtIniVal);
	sistemaVersante.setDtFineVal(dtFineVal);
	sistemaVersante.setDsEmail(dsEmail);
	sistemaVersante.setDsNote(dsNote);
	sistemaVersante.setFlPec(flPec);
	sistemaVersante.setFlIntegrazione(flIntegrazione);
	sistemaVersante.setFlAssociaPersonaFisica(flAssociaPersonaFisica);
	sistemaVersante.setUsrUsers(new ArrayList<UsrUser>());
	em.persist(sistemaVersante);
	em.flush();
	return sistemaVersante.getIdSistemaVersante();
    }

    /**
     * Controllo se il sistema versante passato in ingresso è eliminabile in base all'eventuale
     * legame con degli utenti Se non vi sono legami (FK dell'utente al sistema versante) significa
     * che l'utente è stato CESSATO e non aveva mai versato UD
     *
     * @param idSistemaVersante id sistema
     *
     * @return true se è eliminabile
     */
    public boolean canDeleteSistemaVersante(long idSistemaVersante) {
	Query query = em
		.createQuery("SELECT sistemaVersante FROM AplSistemaVersante sistemaVersante "
			+ "WHERE sistemaVersante.idSistemaVersante = :idSistemaVersante "
			+ "AND NOT EXISTS (SELECT user FROM UsrUser user WHERE user.aplSistemaVersante.idSistemaVersante = sistemaVersante.idSistemaVersante) ");
	query.setParameter("idSistemaVersante", longFrom(idSistemaVersante));
	return !query.getResultList().isEmpty();
    }

    /**
     * Elimina un sistema versante da DB. Se ci sono dei riferimenti nei servizi erogati, cancello
     * il legame con essi (ma non ovviamente il servizio erogato). La chiave esterna sui servizi
     * erogati verrà impostata a null Altrimenti, cancello direttamente il sistema versante.
     *
     * @param idSistemaVersante id sistema
     */
    public void deleteAplSistemaVersante(long idSistemaVersante) {
	TypedQuery<OrgServizioErog> query = em.createQuery(
		"SELECT servizioErog FROM OrgServizioErog servizioErog "
			+ "WHERE servizioErog.aplSistemaVersante.idSistemaVersante = :idSistemaVersante",
		OrgServizioErog.class);
	query.setParameter("idSistemaVersante", longFrom(idSistemaVersante));
	List<OrgServizioErog> resultList = query.getResultList();
	if (!resultList.isEmpty()) {
	    for (OrgServizioErog orgServizioErog : resultList) {
		orgServizioErog.setAplSistemaVersante(null);
	    }
	}
	em.remove(em.find(AplSistemaVersante.class, idSistemaVersante));
    }

    public AplSistemaVersante getAplSistemaVersanteByName(String denominazione) {
	AplSistemaVersante sistemaVersante = null;
	if (denominazione != null) {
	    String queryStr = "SELECT sistemaVersante FROM AplSistemaVersante sistemaVersante "
		    + "WHERE sistemaVersante.nmSistemaVersante = :denominazione ";
	    Query query = em.createQuery(queryStr);
	    query.setParameter("denominazione", denominazione);
	    List<AplSistemaVersante> ListaSistemiVersanti = (List<AplSistemaVersante>) query
		    .getResultList();
	    if (!ListaSistemiVersanti.isEmpty()) {
		sistemaVersante = ListaSistemiVersanti.get(0);
	    }
	}
	return sistemaVersante;
    }

    public List<AplSistemaVersante> getAplSistemaVersanteValidiList(String tipoUser,
	    BigDecimal idEnteUtente) {
	String queryStr = "";
	List<AplSistemaVersante> risultato = new ArrayList<>();
	List<AplVLisSistVersPerAutoma> risultatoVista = new ArrayList<>();
	Query query = null;

	if (StringUtils.isNotBlank(tipoUser)) {
	    switch (tipoUser) {
	    case "PERSONA_FISICA":
		queryStr = "SELECT sistemaVersante FROM AplSistemaVersante sistemaVersante WHERE sistemaVersante.flAssociaPersonaFisica = '1' AND sistemaVersante.dtIniVal <= :dataOdierna AND sistemaVersante.dtFineVal >= :dataOdierna ORDER BY sistemaVersante.nmSistemaVersante ";
		query = em.createQuery(queryStr);
		query.setParameter("dataOdierna", DateUtil.getDataOdiernaNoTime());
		risultato = (List<AplSistemaVersante>) query.getResultList();
		break;
	    case "AUTOMA":
		queryStr = "SELECT sistVersPerAutoma FROM AplVLisSistVersPerAutoma sistVersPerAutoma WHERE sistVersPerAutoma.idEnteUtente = :idEnteUtente ORDER BY sistVersPerAutoma.nmSistemaVersante ";
		query = em.createQuery(queryStr);
		query.setParameter("idEnteUtente", idEnteUtente);
		risultatoVista = (List<AplVLisSistVersPerAutoma>) query.getResultList();
		for (AplVLisSistVersPerAutoma recordVista : risultatoVista) {
		    AplSistemaVersante sisVers = new AplSistemaVersante();
		    sisVers.setIdSistemaVersante(recordVista.getIdSistemaVersante().longValue());
		    sisVers.setNmSistemaVersante(recordVista.getNmSistemaVersante());
		    risultato.add(sisVers);
		}
		break;
	    default:
		queryStr = "SELECT sistemaVersante FROM AplSistemaVersante sistemaVersante WHERE sistemaVersante.dtIniVal <= :dataOdierna AND sistemaVersante.dtFineVal >= :dataOdierna";
		query = em.createQuery(queryStr);
		query.setParameter("dataOdierna", DateUtil.getDataOdiernaNoTime());
		risultato = (List<AplSistemaVersante>) query.getResultList();
		break;
	    }
	}

	return risultato;
    }

    public List<AplSistemaVersante> getAplSistemaVersanteList() {
	String queryStr = "SELECT sistemaVersante FROM AplSistemaVersante sistemaVersante "
		+ "ORDER BY sistemaVersante.nmSistemaVersante ";
	Query query = em.createQuery(queryStr);
	return (List<AplSistemaVersante>) query.getResultList();
    }

    public boolean checkSistemaVersantePerAutoma(BigDecimal idSistemaVersante,
	    BigDecimal idEnteUtente) {
	String queryStr = "SELECT sistVersPerAutoma FROM AplVLisSistVersPerAutoma sistVersPerAutoma "
		+ "WHERE sistVersPerAutoma.idEnteUtente = :idEnteUtente "
		+ "AND sistVersPerAutoma.idSistemaVersante = :idSistemaVersante ";
	Query query = em.createQuery(queryStr);
	query.setParameter("idSistemaVersante", idSistemaVersante);
	query.setParameter("idEnteUtente", idEnteUtente);
	return !query.getResultList().isEmpty();
    }

    /**
     * @deprecated
     *
     * @param idSistemaVersante id sistema versante
     *
     * @return lista di {@link String}
     */
    @Deprecated
    public List<String> getOrganizUltimoLivelloSacerSistemaVersante(BigDecimal idSistemaVersante) {
	String queryStr = "SELECT sistemiVersanti.dlCompositoOrganiz FROM AplVRicSistemaVersante sistemiVersanti "
		+ "WHERE sistemiVersanti.nmApplic = 'SACER' "
		+ "AND sistemiVersanti.id.idSistemaVersante = :idSistemaVersante "
		+ "ORDER BY sistemiVersanti.dlCompositoOrganiz ";
	Query query = em.createQuery(queryStr);
	query.setParameter("idSistemaVersante", idSistemaVersante);
	return (List<String>) query.getResultList();
    }

    public List<UsrVTreeOrganizIam> getOrganizUltimoLivelloSacer() {
	String queryStr = "SELECT treeOrganiz FROM UsrVTreeOrganizIam treeOrganiz "
		+ "WHERE treeOrganiz.nmApplic = 'SACER' " + "AND treeOrganiz.flLastLivello = '1' "
		+ "ORDER BY treeOrganiz.dlCompositoOrganiz ";
	Query query = em.createQuery(queryStr);
	return (List<UsrVTreeOrganizIam>) query.getResultList();
    }

    public List<AplSistemaVersArkRif> getAplSistemaVersArkRifList(BigDecimal idSistemaVersante) {
	String queryStr = "SELECT sistemaVersArkRif FROM AplSistemaVersArkRif sistemaVersArkRif "
		+ "WHERE sistemaVersArkRif.aplSistemaVersante.idSistemaVersante = :idSistemaVersante "
		+ "ORDER BY sistemaVersArkRif.usrUser.nmUserid ";
	Query query = getEntityManager().createQuery(queryStr);
	query.setParameter("idSistemaVersante", longFrom(idSistemaVersante));
	return (List<AplSistemaVersArkRif>) query.getResultList();
    }

    public List<AplSistemaVersanteUserRef> getAplSistemaVersanteUserRefList(
	    BigDecimal idSistemaVersante) {
	String queryStr = "SELECT sistemaVersanteUserRef FROM AplSistemaVersanteUserRef sistemaVersanteUserRef "
		+ "WHERE sistemaVersanteUserRef.aplSistemaVersante.idSistemaVersante = :idSistemaVersante ";
	Query query = getEntityManager().createQuery(queryStr);
	query.setParameter("idSistemaVersante", longFrom(idSistemaVersante));
	return (List<AplSistemaVersanteUserRef>) query.getResultList();
    }

    public boolean isUtenteArchivistaInSistemaVersante(BigDecimal idSistemaVersante,
	    BigDecimal idUserIam) {
	Query query = getEntityManager()
		.createQuery("SELECT sistemaVersArkRif FROM AplSistemaVersArkRif sistemaVersArkRif "
			+ "WHERE sistemaVersArkRif.aplSistemaVersante.idSistemaVersante = :idSistemaVersante "
			+ "AND sistemaVersArkRif.usrUser.idUserIam = :idUserIam ");
	query.setParameter("idSistemaVersante", longFrom(idSistemaVersante));
	query.setParameter("idUserIam", longFrom(idUserIam));
	List<AplSistemaVersArkRif> list = query.getResultList();
	return !list.isEmpty();
    }

    public boolean isReferenteDittaProduttriceInSistemaVersante(BigDecimal idSistemaVersante,
	    BigDecimal idUserIam) {
	Query query = getEntityManager().createQuery(
		"SELECT sistemaVersUserRef FROM AplSistemaVersanteUserRef sistemaVersUserRef "
			+ "WHERE sistemaVersUserRef.aplSistemaVersante.idSistemaVersante = :idSistemaVersante "
			+ "AND sistemaVersUserRef.usrUser.idUserIam = :idUserIam ");
	query.setParameter("idSistemaVersante", longFrom(idSistemaVersante));
	query.setParameter("idUserIam", longFrom(idUserIam));
	List<AplSistemaVersArkRif> list = query.getResultList();
	return !list.isEmpty();
    }

    public List<AplVLisOrganizUsoSisVers> getAplVLisOrganizUsoSisVers(
	    BigDecimal idSistemaVersante) {
	List<AplVLisOrganizUsoSisVers> list = new ArrayList<>();
	if (idSistemaVersante != null) {
	    Query query = getEntityManager().createQuery(
		    "SELECT organizUsoSisVers FROM AplVLisOrganizUsoSisVers organizUsoSisVers "
			    + "WHERE organizUsoSisVers.nmApplic = 'SACER' "
			    + "AND organizUsoSisVers.id.idSistemaVersante = :idSistemaVersante "
			    + "ORDER BY organizUsoSisVers.dlCompositoOrganiz ");
	    query.setParameter("idSistemaVersante", idSistemaVersante);
	    list = query.getResultList();
	}
	return list;
    }

    public boolean existsUtentiAssociatiSistemaVersante(BigDecimal idSistemaVersante,
	    String tipoUser) {
	Query query = getEntityManager().createQuery(
		"SELECT 1 FROM UsrUser user " + "JOIN user.aplSistemaVersante sistemaVersante "
			+ "WHERE user.tipoUser = :tipoUser "
			+ "AND sistemaVersante.idSistemaVersante = :idSistemaVersante ");
	query.setParameter("idSistemaVersante", longFrom(idSistemaVersante));
	query.setParameter("tipoUser", tipoUser);
	List<UsrUser> list = query.getResultList();
	return !list.isEmpty();
    }

    static class FiltriSistemiVersantiPlain {
	private final long idUserIam;
	private final String denominazione;
	private final String descrizione;
	private final String produttore;
	private final String versione;
	private final BigDecimal idOrganizApplic;
	private final String flCessato;
	private final String flIntegrazione;
	private final List<BigDecimal> idArchivista;
	private final String noArchivista;
	private final String flAssociaPersonaFisica;

	/**
	 * @param idUserIam
	 * @param denominazione
	 * @param descrizione
	 * @param produttore
	 * @param versione
	 * @param idOrganizApplic
	 * @param flCessato1
	 * @param flIntegrazione
	 * @param idArchivista
	 * @param noArchivista
	 * @param flAssociaPersonaFisica
	 */
	FiltriSistemiVersantiPlain(long idUserIam, String denominazione, String descrizione,
		String produttore, String versione, BigDecimal idOrganizApplic, String flCessato1,
		String flIntegrazione, List<BigDecimal> idArchivista, String noArchivista,
		String flAssociaPersonaFisica) {
	    this.idUserIam = idUserIam;
	    this.denominazione = denominazione;
	    this.descrizione = descrizione;
	    this.produttore = produttore;
	    this.versione = versione;
	    this.idOrganizApplic = idOrganizApplic;
	    this.flCessato = flCessato1;
	    this.flIntegrazione = flIntegrazione;
	    this.idArchivista = idArchivista;
	    this.noArchivista = noArchivista;
	    this.flAssociaPersonaFisica = flAssociaPersonaFisica;
	}

	private long getIdUserIam() {
	    return idUserIam;
	}

	private String getDenominazione() {
	    return denominazione;
	}

	private String getDescrizione() {
	    return descrizione;
	}

	private String getProduttore() {
	    return produttore;
	}

	private String getVersione() {
	    return versione;
	}

	private BigDecimal getIdOrganizApplic() {
	    return idOrganizApplic;
	}

	private String getFlCessato() {
	    return flCessato;
	}

	private String getFlIntegrazione() {
	    return flIntegrazione;
	}

	private List<BigDecimal> getIdArchivista() {
	    return idArchivista;
	}

	private String getNoArchivista() {
	    return noArchivista;
	}

	public String getFlAssociaPersonaFisica() {
	    return flAssociaPersonaFisica;
	}

    }
}
