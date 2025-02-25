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

package it.eng.saceriam.web.helper;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import it.eng.saceriam.entity.AplApplic;

/**
 * Session Bean implementation class ComboHelper Contiene i metodi, per la gestione della persistenza su DB per le
 * operazioni CRUD su oggetti di
 */
@Stateless
@LocalBean
public class ComboHelper {

    /*
     * Default constructor.
     */
    public ComboHelper() {
    }

    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager em;

    /**
     * Metodo che ritorna la lista delle applicazioni ordinate per nome in base alle abilitazioni dell'utente
     * "amministratore" (quello corrente)
     *
     * @deprecated il parametro idUserAdmin non viene mai usato, richiamare il metodo senza questo parametro
     *
     * @param idUserIamCorrente
     *            id user IAM
     * @param isUserAdmin
     *            id user Admin
     *
     * @return la lista delle applicazioni
     */
    @Deprecated
    public List<AplApplic> getAplApplicAbilitateList(long idUserIamCorrente, boolean isUserAdmin) {
        return getAplApplicAbilitateList(idUserIamCorrente);
    }

    /**
     * Metodo che ritorna la lista delle applicazioni ordinate per nome in base alle abilitazioni dell'utente
     * "amministratore" (quello corrente)
     *
     * @param idUserIamCorrente
     *            id user IAM
     *
     * @return la lista delle applicazioni
     */
    public List<AplApplic> getAplApplicAbilitateList(long idUserIamCorrente) {
        String queryStr = "SELECT usoApplic.aplApplic " + "FROM UsrUsoUserApplic usoApplic "
                + "WHERE usoApplic.usrUser.idUserIam = :idUserIamCorrente ";

        queryStr += "ORDER BY usoApplic.aplApplic.nmApplic ";
        Query query = em.createQuery(queryStr);
        query.setParameter("idUserIamCorrente", idUserIamCorrente);
        List<AplApplic> list = query.getResultList();
        return list;
    }

    /**
     * Ritorna la lista di applicazioni per le quali l'utente corrente è abilitato e che mettano a disposizione la
     * pagina di informativa sulla privacy
     *
     * @param idUserIamCorrente
     *            id user iam corrente
     *
     * @return la lista di applicazioni
     */
    public List<AplApplic> getAplApplicAbilitateInfoPrivacyList(long idUserIamCorrente) {
        String queryStr = "SELECT page.aplApplic FROM UsrUsoUserApplic usoApplic JOIN usoApplic.aplApplic applic "
                + "JOIN applic.aplPaginaWebs page "
                + "WHERE usoApplic.usrUser.idUserIam = :idUserIamCorrente AND page.nmPaginaWeb like '%infoPrivacy%' ";
        Query q = em.createQuery(queryStr);
        q.setParameter("idUserIamCorrente", idUserIamCorrente);
        return q.getResultList();
    }

    public List<AplApplic> getAplApplicAbilitateRicercaReplicheList(long idUserIamCorrente) {
        String queryStr = "SELECT usoApplic.aplApplic " + "FROM UsrUsoUserApplic usoApplic "
                + "WHERE usoApplic.usrUser.idUserIam = :idUserIamCorrente "
                + "AND usoApplic.aplApplic.nmApplic NOT LIKE :nmApplic " + "ORDER BY usoApplic.aplApplic.nmApplic ";
        Query query = em.createQuery(queryStr);
        query.setParameter("idUserIamCorrente", idUserIamCorrente);
        query.setParameter("nmApplic", "DPI_%");
        List<AplApplic> list = query.getResultList();
        return list;
    }

    public String getFederationMetadata() {
        String queryStr = "SELECT asm.blSamlMetadati " + "FROM AplSamlMetadati asm ";
        Query query = em.createQuery(queryStr);
        return (String) query.getSingleResult();

    }

    /**
     * Metodo che ritorna la lista delle applicazioni ordinate per nome in base all'ente a cui appartiene l'utente
     *
     * @deprecated il parametro isEnteFornitoreEsterno non è mai usato
     *
     * @param idUserIamCorrente
     *            id user IAM
     * @param isAppartEnteConvenzAdmin
     *            id ente di appartenenza
     * @param isEnteOrganoVigilanza
     *            true se ente organo vigilanza
     * @param isEnteFornitoreEsterno
     *            true se ente fornitore esterno
     *
     * @return la lista delle applicazioni
     *
     */
    @Deprecated
    public List<AplApplic> getAplApplicAbilitateUtenteList(long idUserIamCorrente, boolean isAppartEnteConvenzAdmin,
            boolean isEnteOrganoVigilanza, boolean isEnteFornitoreEsterno) {
        return getAplApplicAbilitateUtenteList(idUserIamCorrente, isAppartEnteConvenzAdmin, isEnteOrganoVigilanza);
    }

    /**
     * Metodo che ritorna la lista delle applicazioni ordinate per nome in base all'ente a cui appartiene l'utente
     *
     * @param idUserIamCorrente
     *            id user IAM
     * @param isAppartEnteConvenzAdmin
     *            id ente di appartenenza
     * @param isEnteOrganoVigilanza
     *            true se ente organo vigilanza
     *
     * @return la lista delle applicazioni
     *
     */
    public List<AplApplic> getAplApplicAbilitateUtenteList(long idUserIamCorrente, boolean isAppartEnteConvenzAdmin,
            boolean isEnteOrganoVigilanza) {

        String queryStr = "SELECT usoApplic.aplApplic " + "FROM UsrUsoUserApplic usoApplic "
                + "WHERE usoApplic.usrUser.idUserIam = :idUserIamCorrente ";

        if (!isAppartEnteConvenzAdmin) {
            queryStr += "AND usoApplic.aplApplic.nmApplic NOT IN ('TPI') ";
        }

        if (isEnteOrganoVigilanza) {
            queryStr += "AND usoApplic.aplApplic.nmApplic IN ('SACER') ";
        }

        queryStr += "ORDER BY usoApplic.aplApplic.nmApplic ";
        Query query = em.createQuery(queryStr);
        query.setParameter("idUserIamCorrente", idUserIamCorrente);
        List<AplApplic> list = query.getResultList();
        return list;
    }

}
