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

import static it.eng.paginator.util.HibernateUtils.longFrom;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import it.eng.saceriam.entity.OrgAmbitoTerrit;
import it.eng.saceriam.helper.GenericHelper;

/**
 * Session Bean implementation class GestioneAmbitoTerritorialeHelper Contiene i metodi, per la gestione della
 * persistenza su DB per le operazioni CRUD
 *
 */
@Stateless
@LocalBean
public class GestioneAmbitoTerritorialeHelper extends GenericHelper {

    public List<OrgAmbitoTerrit> getOrgAmbitoTerritList(String tipo) {
        // Query nativa
        StringBuilder queryStr = new StringBuilder("SELECT * " + " FROM ORG_AMBITO_TERRIT a");

        if (tipo != null) {
            queryStr.append(" WHERE a.ti_ambito_territ LIKE '").append(tipo).append("'");
        }

        queryStr.append(" CONNECT BY PRIOR a.id_ambito_territ =  a.id_ambito_territ_padre"
                + " START WITH a.id_ambito_territ_padre is null " + " ORDER SIBLINGS BY a.cd_ambito_territ ASC");

        Query query = getEntityManager().createNativeQuery(queryStr.toString(), OrgAmbitoTerrit.class);

        List<OrgAmbitoTerrit> list = query.getResultList();

        if (list.isEmpty()) {
            return null;
        }

        return list;
    }

    public OrgAmbitoTerrit getOrgAmbitoTerritByCode(String cdAmbitoTerritoriale) {
        String queryStr = "SELECT a " + "FROM OrgAmbitoTerrit a " + "WHERE a.cdAmbitoTerrit = :cdAmbitoTerritoriale";

        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("cdAmbitoTerritoriale", cdAmbitoTerritoriale);
        List<OrgAmbitoTerrit> list = query.getResultList();

        if (list.isEmpty()) {

            return null;
        }
        return list.get(0);

    }

    public List<OrgAmbitoTerrit> getOrgAmbitoTerritChildList(BigDecimal idAmbitoTerritoriale) {

        String queryStr = "SELECT a " + "FROM OrgAmbitoTerrit a "
                + "WHERE a.orgAmbitoTerrit.idAmbitoTerrit = :idAmbitoTerritoriale";

        Query query = getEntityManager().createQuery(queryStr);

        query.setParameter("idAmbitoTerritoriale", longFrom(idAmbitoTerritoriale));

        List<OrgAmbitoTerrit> list = query.getResultList();

        if (list.isEmpty()) {
            return null;
        }
        return list;

    }

    /**
     * @deprecated
     *
     * @param idAmbitoTerrit
     *            id ambito territoriale
     *
     * @return elenco degli {@link OrgAmbitoTerrit}
     */
    @Deprecated
    public List<OrgAmbitoTerrit> getOrgAmbitoTerritChildList(List<BigDecimal> idAmbitoTerrit) {
        List<OrgAmbitoTerrit> list = null;
        if (!idAmbitoTerrit.isEmpty()) {
            String queryStr = "SELECT a " + "FROM OrgAmbitoTerrit a "
                    + "WHERE a.orgAmbitoTerrit.idAmbitoTerrit IN (:idAmbitoTerrit)";
            Query query = getEntityManager().createQuery(queryStr);
            query.setParameter("idAmbitoTerrit", idAmbitoTerrit);
            list = query.getResultList();
            if (list.isEmpty()) {
                return null;
            }
        }
        return list;
    }

    public Long countOrgEnteConvenzByAmbitoTerrit(BigDecimal idAmbitoTerrit) {
        String queryStr = "SELECT COUNT(enteSiam) FROM OrgEnteSiam enteSiam WHERE enteSiam.idAmbitoTerrit = :idAmbitoTerrit";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idAmbitoTerrit", idAmbitoTerrit);
        Long enti = (Long) query.getSingleResult();
        return enti;
    }

    public Long countOrgStoEnteConvenzByAmbitoTerrit(BigDecimal idAmbitoTerrit) {
        String queryStr = "SELECT COUNT(stoEnteConvenz) FROM OrgStoEnteConvenz stoEnteConvenz WHERE stoEnteConvenz.idAmbitoTerrit = :idAmbitoTerrit";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idAmbitoTerrit", idAmbitoTerrit);
        Long enti = (Long) query.getSingleResult();
        return enti;
    }
}
