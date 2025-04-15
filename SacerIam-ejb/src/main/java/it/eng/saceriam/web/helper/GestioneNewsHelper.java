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

import static it.eng.paginator.util.HibernateUtils.longFrom;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.entity.AplNews;
import it.eng.saceriam.slite.gen.form.GestioneNewsForm;
import it.eng.spagoCore.error.EMFError;

/**
 *
 * @author Gilioli_P
 */
@Stateless
@LocalBean
public class GestioneNewsHelper {

    public GestioneNewsHelper() {
    }

    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager entityManager;

    public List<AplNews> getAplNewsList(GestioneNewsForm.FiltriNews filtriNews) throws EMFError {
	return getAplNewsList(filtriNews.getDs_oggetto_filter().parse(),
		filtriNews.getDt_ini_pubblic_filter().parse(),
		filtriNews.getDt_fin_pubblic_filter().parse());
    }

    /**
     *
     * @param dsOggetto  descrizione oggetto
     * @param dataInizio data inizio
     * @param dataFine   data fine
     *
     * @return lista delle news {@link AplNews}
     */
    public List<AplNews> getAplNewsList(String dsOggetto, Date dataInizio, Date dataFine) {
	StringBuilder queryStr = new StringBuilder("SELECT news FROM AplNews news ");
	String whereWord = " WHERE ";

	if (dsOggetto != null) {
	    queryStr.append(whereWord);
	    queryStr.append(" UPPER (news.dsOggetto) LIKE :dsOggetto ESCAPE :char");
	    whereWord = " AND ";
	}

	if (dataInizio != null) {
	    queryStr.append(whereWord);
	    queryStr.append(" news.dtIniPubblic >= :dataInizio");
	    whereWord = " AND ";
	}

	if (dataFine != null) {
	    queryStr.append(whereWord);
	    queryStr.append(" news.dtFinPubblic <= :dataFine");
	}

	queryStr.append(" ORDER BY news.idNews DESC");

	Query query = entityManager.createQuery(queryStr.toString());

	if (dsOggetto != null) {
	    query.setParameter("dsOggetto", "%" + dsOggetto.toUpperCase() + "%");
	    query.setParameter("char", '\\');
	}
	if (dataInizio != null) {
	    query.setParameter("dataInizio", dataInizio);
	}
	if (dataFine != null) {
	    query.setParameter("dataFine", dataFine);
	}

	List<AplNews> list = query.getResultList();

	return list;
    }

    /**
     * @deprecated
     *
     * @param dsOggetto descrizione oggetto
     *
     * @return lista di news {@link AplNews}
     */
    @Deprecated
    public AplNews getAplNewsByDs(String dsOggetto) {

	StringBuilder queryStr = new StringBuilder("SELECT news FROM AplNews news");
	if (dsOggetto != null) {
	    queryStr.append(" WHERE news.dsOggetto = :dsOggetto");
	}

	Query query = entityManager.createQuery(queryStr.toString());

	if (dsOggetto != null) {
	    query.setParameter("dsOggetto", dsOggetto);
	}

	List<AplNews> lista = query.getResultList();

	if (lista.isEmpty()) {
	    return null;
	}

	return lista.get(0);
    }

    public AplNews getAplNewsById(BigDecimal idNews) {
	return entityManager.find(AplNews.class, idNews.longValue());
    }

    public AplApplic getAplApplicById(BigDecimal idApplic) {
	return entityManager.find(AplApplic.class, idApplic.longValue());
    }

    public void insert(AplNews o) {
	entityManager.persist(o);
	entityManager.flush();
    }

    public void update(AplNews o) {
	o = entityManager.merge(o);
	entityManager.flush();
	entityManager.refresh(o);
    }

    public void remove(AplNews o) {
	entityManager.remove(o);
	entityManager.flush();
    }

    public List<AplApplic> getAplApplicList() {
	String queryStr = "SELECT applic FROM AplApplic applic ";
	Query query = entityManager.createQuery(queryStr);
	List<AplApplic> list = query.getResultList();
	return list;
    }

    public List<AplApplic> getAplApplicList(BigDecimal idNews) {
	String queryStr = "SELECT newsApplic.aplApplic FROM AplNewsApplic newsApplic "
		+ "WHERE newsApplic.aplNew.idNews = :idNews ";
	Query query = entityManager.createQuery(queryStr);
	query.setParameter("idNews", longFrom(idNews));
	List<AplApplic> list = query.getResultList();
	return list;
    }

    public void deleteAplNewsApplicList(long idNews) {
	String queryStr = "DELETE FROM AplNewsApplic aplNewsApplic "
		+ "WHERE aplNewsApplic.aplNew.idNews = :idNews ";
	Query query = entityManager.createQuery(queryStr);
	query.setParameter("idNews", idNews);
	query.executeUpdate();
	entityManager.flush();
    }

    public void deleteAplNewsApplic(long idNews, long idApplic) {
	String queryStr = "DELETE FROM AplNewsApplic aplNewsApplic "
		+ "WHERE aplNewsApplic.aplNew.idNews = :idNews "
		+ "AND aplNewsApplic.aplApplic.idApplic = :idApplic ";
	Query query = entityManager.createQuery(queryStr);
	query.setParameter("idNews", idNews);
	query.setParameter("idApplic", idApplic);
	query.executeUpdate();
	entityManager.flush();
    }
}
