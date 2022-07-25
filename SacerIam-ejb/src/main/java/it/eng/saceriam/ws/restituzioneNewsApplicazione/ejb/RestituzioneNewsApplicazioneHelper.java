package it.eng.saceriam.ws.restituzioneNewsApplicazione.ejb;

import it.eng.saceriam.entity.AplNews;
import it.eng.saceriam.ws.restituzioneNewsApplicazione.dto.ListaNews;
import it.eng.saceriam.ws.restituzioneNewsApplicazione.dto.News;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Gilioli_P
 */
@Stateless(mappedName = "RestituzioneNewsApplicazioneHelper")
@LocalBean
public class RestituzioneNewsApplicazioneHelper {

    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager entityManager;

    public ListaNews getListaNews(Long idApplic) {
        String queryStr = "SELECT news FROM AplNewsApplic newsApplic " + "JOIN newsApplic.aplNew news "
                + "WHERE newsApplic.aplApplic.idApplic = :idApplic "
                + "AND news.dtFinPubblic > :toDay AND news.dtIniPubblic < :toDay";

        Query query = entityManager.createQuery(queryStr);
        query.setParameter("idApplic", idApplic);
        query.setParameter("toDay", new Date());
        List<AplNews> newsList = query.getResultList();
        ListaNews listaNews = new ListaNews();
        List<News> listaTemp = new ArrayList();
        if (!newsList.isEmpty()) {
            for (AplNews news : newsList) {
                News n = new News();
                n.setDsOggetto(news.getDsOggetto());
                n.setDtIniPubblic(news.getDtIniPubblic());
                n.setDtFinPubblic(news.getDtFinPubblic());
                n.setDlTesto(news.getDlTesto());
                listaTemp.add(n);
            }
            listaNews.setNews(listaTemp);
            return listaNews;
        } else {
            return null;
        }
    }
}
