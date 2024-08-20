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

package it.eng.saceriam.informazioni.noteRilascio.helper;

import static it.eng.paginator.util.HibernateUtils.longFrom;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections4.CollectionUtils;

import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.entity.AplNotaRilascio;

/**
 *
 * @author DiLorenzo_F
 */
@Stateless
@LocalBean
public class NoteRilascioHelper {

    public NoteRilascioHelper() {
    }

    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager entityManager;

    /**
     * @deprecated
     *
     * @param nmApplic
     *            nome applicazione
     *
     * @return l'applicazione {@link AplApplic}
     */
    @Deprecated
    public AplApplic getAplApplicByName(String nmApplic) {
        String queryStr = "SELECT applic FROM AplApplic applic " + "WHERE applic.nmApplic = :nmApplic ";
        Query query = entityManager.createQuery(queryStr);
        query.setParameter("nmApplic", nmApplic);
        List<AplApplic> applic = query.getResultList();
        if (applic != null && applic.size() == 1) {
            return applic.get(0);
        } else {
            return null;
        }
    }

    public AplApplic getAplApplic(String name) {
        String queryStr = "SELECT applic FROM AplApplic applic WHERE applic.nmApplic = :nomeappl";
        Query q = entityManager.createQuery(queryStr);
        q.setParameter("nomeappl", name);
        AplApplic applic = (AplApplic) q.getSingleResult();
        return applic;
    }

    public List<AplNotaRilascio> getAplNoteRilascioList(BigDecimal idApplic) {
        String queryStr = "SELECT notaRilascio FROM AplNotaRilascio notaRilascio "
                + "WHERE notaRilascio.aplApplic.idApplic = :idApplic " + "ORDER BY notaRilascio.dtVersione DESC";

        Query query = entityManager.createQuery(queryStr);

        if (idApplic != null) {
            query.setParameter("idApplic", longFrom(idApplic));
        }

        List<AplNotaRilascio> list = query.getResultList();

        return list;
    }

    /**
     * @deprecated
     *
     * @param cdVersione
     *            codice versione
     *
     * @return la nota di rilascio {@link AplNotaRilascio}
     */
    @Deprecated
    public AplNotaRilascio getAplNotaRilascioByVersione(String cdVersione) {

        StringBuilder queryStr = new StringBuilder("SELECT notaRilascio FROM AplNotaRilascio notaRilascio");
        if (cdVersione != null) {
            queryStr.append(" WHERE notaRilascio.cdVersione = :cdVersione");
        }

        Query query = entityManager.createQuery(queryStr.toString());

        if (cdVersione != null) {
            query.setParameter("cdVersione", cdVersione);
        }

        List<AplNotaRilascio> lista = query.getResultList();

        if (lista.isEmpty()) {
            return null;
        }

        return lista.get(0);
    }

    public AplNotaRilascio getAplNotaRilascioById(BigDecimal idNotaRilascio) {
        return entityManager.find(AplNotaRilascio.class, idNotaRilascio.longValue());
    }

    public AplApplic getAplApplicById(BigDecimal idApplic) {
        return entityManager.find(AplApplic.class, idApplic.longValue());
    }

    public AplApplic getAplApplic(long idApplic) {
        AplApplic applic = entityManager.find(AplApplic.class, idApplic);
        return applic;
    }

    public List<AplNotaRilascio> getAplNoteRilascioPrecList(BigDecimal idApplic, BigDecimal idNotaRilascio,
            Date dtVersione) {
        String queryStr = "SELECT notaRilascio FROM AplNotaRilascio notaRilascio "
                + "JOIN notaRilascio.aplApplic applic " + "WHERE notaRilascio.idNotaRilascio != :idNotaRilascio "
                + "AND applic.idApplic = :idApplic ";
        Query query = entityManager.createQuery(queryStr);
        query.setParameter("idNotaRilascio", longFrom(idNotaRilascio));
        query.setParameter("idApplic", longFrom(idApplic));
        List<AplNotaRilascio> list = query.getResultList();
        CollectionUtils.filter(list, object -> ((AplNotaRilascio) object).getDtVersione().compareTo(dtVersione) < 0);
        return list;
    }
}
