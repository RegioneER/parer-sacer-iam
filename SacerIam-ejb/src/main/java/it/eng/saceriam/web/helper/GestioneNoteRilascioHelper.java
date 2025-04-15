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

import org.apache.commons.collections4.CollectionUtils;

import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.entity.AplNotaRilascio;
import it.eng.saceriam.slite.gen.form.GestioneNoteRilascioForm;
import it.eng.spagoCore.error.EMFError;

/**
 *
 * @author DiLorenzo_F
 */
@Stateless
@LocalBean
public class GestioneNoteRilascioHelper {

    public GestioneNoteRilascioHelper() {
    }

    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager entityManager;

    public List<AplNotaRilascio> getAplNoteRilascioList(
	    GestioneNoteRilascioForm.FiltriNoteRilascio filtriNoteRilascio) throws EMFError {
	return getAplNoteRilascioList(filtriNoteRilascio.getNm_applic().parse(),
		filtriNoteRilascio.getCd_versione().parse(),
		filtriNoteRilascio.getDt_versione_da().parse(),
		filtriNoteRilascio.getDt_versione_a().parse(),
		filtriNoteRilascio.getBl_nota().parse(),
		filtriNoteRilascio.getDs_evidenza().parse());
    }

    public List<AplNotaRilascio> getAplNoteRilascioList(BigDecimal idApplic, String cdVersione,
	    Date dataVersioneDa, Date dataVersioneA, String blNota, String dsEvidenza) {
	StringBuilder queryStr = new StringBuilder(
		"SELECT notaRilascio FROM AplNotaRilascio notaRilascio ");
	String whereWord = " WHERE ";

	if (idApplic != null) {
	    queryStr.append(whereWord);
	    queryStr.append(" notaRilascio.aplApplic.idApplic = :idApplic");
	    whereWord = " AND ";
	}

	if (cdVersione != null) {
	    queryStr.append(whereWord);
	    queryStr.append(" UPPER (notaRilascio.cdVersione) LIKE :cdVersione ESCAPE :char");
	    whereWord = " AND ";
	}

	if (dataVersioneDa != null) {
	    queryStr.append(whereWord);
	    queryStr.append(" notaRilascio.dtVersione >= :dataVersioneDa");
	    whereWord = " AND ";
	}

	if (dataVersioneA != null) {
	    queryStr.append(whereWord);
	    queryStr.append(" notaRilascio.dtVersione <= :dataVersioneA");
	    whereWord = " AND ";
	}

	if (blNota != null) {
	    queryStr.append(whereWord);
	    queryStr.append(" notaRilascio.blNota LIKE :blNota ESCAPE :char");
	    whereWord = " AND ";
	}

	if (dsEvidenza != null) {
	    queryStr.append(whereWord);
	    queryStr.append(" notaRilascio.dsEvidenza LIKE :dsEvidenza ESCAPE :char");
	}

	queryStr.append(" ORDER BY notaRilascio.idNotaRilascio DESC");

	Query query = entityManager.createQuery(queryStr.toString());

	if (idApplic != null) {
	    query.setParameter("idApplic", longFrom(idApplic));
	}
	if (cdVersione != null) {
	    query.setParameter("cdVersione", "%" + cdVersione.toUpperCase() + "%");
	    query.setParameter("char", '\\');
	}
	if (dataVersioneDa != null) {
	    query.setParameter("dataVersioneDa", dataVersioneDa);
	}
	if (dataVersioneA != null) {
	    query.setParameter("dataVersioneA", dataVersioneA);
	}
	if (blNota != null) {
	    query.setParameter("blNota", "%" + blNota + "%");
	    query.setParameter("char", '\\');
	}
	if (dsEvidenza != null) {
	    query.setParameter("dsEvidenza", "%" + dsEvidenza + "%");
	    query.setParameter("char", '\\');
	}

	List<AplNotaRilascio> list = query.getResultList();

	return list;
    }

    /**
     * @deprecated
     *
     * @param cdVersione codice versione
     *
     * @return la nota di rilascio {@link AplNotaRilascio}
     */
    @Deprecated
    public AplNotaRilascio getAplNotaRilascioByVersione(String cdVersione) {

	StringBuilder queryStr = new StringBuilder(
		"SELECT notaRilascio FROM AplNotaRilascio notaRilascio");
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

    public void insert(AplNotaRilascio o) {
	entityManager.persist(o);
	entityManager.flush();
    }

    public void update(AplNotaRilascio o) {
	o = entityManager.merge(o);
	entityManager.flush();
	entityManager.refresh(o);
    }

    public void remove(AplNotaRilascio o) {
	entityManager.remove(o);
	entityManager.flush();
    }

    public AplApplic getAplApplic(long idApplic) {
	AplApplic applic = entityManager.find(AplApplic.class, idApplic);
	return applic;
    }

    public List<AplNotaRilascio> getAplNoteRilascioPrecList(BigDecimal idApplic,
	    BigDecimal idNotaRilascio, Date dtVersione) {
	String queryStr = "SELECT notaRilascio FROM AplNotaRilascio notaRilascio "
		+ "JOIN notaRilascio.aplApplic applic "
		+ "WHERE notaRilascio.idNotaRilascio != :idNotaRilascio "
		+ "AND applic.idApplic = :idApplic ";
	Query query = entityManager.createQuery(queryStr);
	query.setParameter("idNotaRilascio", longFrom(idNotaRilascio));
	query.setParameter("idApplic", longFrom(idApplic));
	List<AplNotaRilascio> list = query.getResultList();
	CollectionUtils.filter(list,
		object -> ((AplNotaRilascio) object).getDtVersione().compareTo(dtVersione) < 0);
	return list;
    }

    /**
     * @deprecated
     *
     * @param idNotaRilascio id nota di rilascio
     */
    @Deprecated
    public void deleteAplNotaRilascioList(long idNotaRilascio) {
	String queryStr = "DELETE FROM AplNotaRilacio aplNotaRilascio "
		+ "WHERE aplNotaRilascio.idNotaRilascio = :idNotaRilascio ";
	Query query = entityManager.createQuery(queryStr);
	query.setParameter("idNotaRilascio", idNotaRilascio);
	query.executeUpdate();
	entityManager.flush();
    }

    /**
     * @deprecated
     *
     * @param idNotaRilascio id nota di rilascio
     * @param idApplic       id applicazione
     */
    @Deprecated
    public void deleteAplNotaRilascio(long idNotaRilascio, long idApplic) {
	String queryStr = "DELETE FROM AplNotaRilascio aplNotaRilascio "
		+ "WHERE aplNotaRilascio.idNotaRilascio = :idNotaRilascio "
		+ "AND aplNotaRilascio.aplApplic.idApplic = :idApplic ";
	Query query = entityManager.createQuery(queryStr);
	query.setParameter("idNotaRilascio", idNotaRilascio);
	query.setParameter("idApplic", idApplic);
	query.executeUpdate();
	entityManager.flush();
    }

}
