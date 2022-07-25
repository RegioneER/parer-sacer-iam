package it.eng.saceriam.web.ejb;

import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.entity.AplNews;
import it.eng.saceriam.entity.AplNewsApplic;
import it.eng.saceriam.exception.IncoherenceException;
import it.eng.saceriam.slite.gen.form.GestioneNewsForm;
import it.eng.saceriam.slite.gen.tablebean.AplApplicRowBean;
import it.eng.saceriam.slite.gen.tablebean.AplApplicTableBean;
import it.eng.saceriam.slite.gen.tablebean.AplNewsRowBean;
import it.eng.saceriam.slite.gen.tablebean.AplNewsTableBean;
import it.eng.saceriam.web.helper.GestioneNewsHelper;
import it.eng.saceriam.web.util.Transform;
import it.eng.spagoCore.error.EMFError;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ejb Gestione News di SacerIam
 *
 * @author Gilioli_P
 */
@Stateless
@LocalBean
public class GestioneNewsEjb {

    public GestioneNewsEjb() {
    }

    @EJB
    private GestioneNewsHelper gestioneNewsHelper;

    private static final Logger log = LoggerFactory.getLogger(GestioneNewsEjb.class);

    public AplNewsTableBean getAplNewsTableBean(GestioneNewsForm.FiltriNews filtriNews) throws EMFError {
        AplNewsTableBean newsTableBean = new AplNewsTableBean();
        List<AplNews> list = gestioneNewsHelper.getAplNewsList(filtriNews);
        try {
            if (!list.isEmpty()) {
                newsTableBean = (AplNewsTableBean) Transform.entities2TableBean(list);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return newsTableBean;
    }

    public void insertAplNews(AplNewsRowBean newsRowBean, AplApplicTableBean applicTableBean)
            throws IncoherenceException {
        AplNews news = new AplNews();
        List<AplNewsApplic> newsApplicList = new ArrayList();
        List<AplApplic> applicList = new ArrayList();
        try {
            news = (AplNews) Transform.rowBean2Entity(newsRowBean);
            applicList = (List<AplApplic>) Transform.tableBean2Entities(applicTableBean, AplApplic.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        for (AplApplic applic : applicList) {
            AplNewsApplic aplNewsApplic = new AplNewsApplic();
            aplNewsApplic.setAplApplic(applic);
            aplNewsApplic.setAplNew(news);
            newsApplicList.add(aplNewsApplic);
        }
        news.setAplNewsApplics(newsApplicList);
        gestioneNewsHelper.insert(news);
    }

    public void updateAplNews(BigDecimal idNews, AplNewsRowBean newsRowBean, AplApplicTableBean applicTableBean)
            throws IncoherenceException, EMFError {
        AplNews news = new AplNews();
        List<AplNewsApplic> newsApplicList = new ArrayList();
        List<AplApplic> applicList = new ArrayList();
        try {
            news = (AplNews) Transform.rowBean2Entity(newsRowBean);
            news.setIdNews(idNews.longValue());
            applicList = (List<AplApplic>) Transform.tableBean2Entities(applicTableBean, AplApplic.class);
        } catch (Exception e) {
            throw new EMFError(EMFError.ERROR, e);
        }
        for (AplApplic applic : applicList) {
            AplNewsApplic aplNewsApplic = new AplNewsApplic();
            aplNewsApplic.setAplApplic(applic);
            aplNewsApplic.setAplNew(news);
            newsApplicList.add(aplNewsApplic);
        }

        // Cancello la vecchia lista di AplNewsApplic associata a questa news
        gestioneNewsHelper.deleteAplNewsApplicList(news.getIdNews());
        // Inserisco quelle aggiornate
        news.setAplNewsApplics(newsApplicList);
        gestioneNewsHelper.update(news);
    }

    public void deleteNews(AplNewsRowBean newsRowBean) throws IncoherenceException {
        AplNews news = gestioneNewsHelper.getAplNewsById(newsRowBean.getIdNews());
        if (news != null) {
            gestioneNewsHelper.remove(news);
        }
    }

    public void deleteAplNewsApplic(long idNews, long idApplic) throws EMFError {
        gestioneNewsHelper.deleteAplNewsApplic(idNews, idApplic);
    }

    public AplApplicRowBean getAplApplicRowBean(BigDecimal idApplic) {
        AplApplicRowBean applicRowBean = new AplApplicRowBean();
        AplApplic applic = gestioneNewsHelper.getAplApplicById(idApplic);
        try {
            if (applic != null) {
                applicRowBean = (AplApplicRowBean) Transform.entity2RowBean(applic);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return applicRowBean;
    }

    public AplApplicTableBean getAplApplicTableBean() throws EMFError {
        AplApplicTableBean applicTableBean = new AplApplicTableBean();
        List<AplApplic> applicList = gestioneNewsHelper.getAplApplicList();
        try {
            if (applicList != null && !applicList.isEmpty()) {
                applicTableBean = (AplApplicTableBean) Transform.entities2TableBean(applicList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return applicTableBean;
    }

    public AplApplicTableBean getAplApplicByIdNewsTableBean(BigDecimal idNews) throws EMFError {
        AplApplicTableBean applicTableBean = new AplApplicTableBean();
        List<AplApplic> applicList = gestioneNewsHelper.getAplApplicList(idNews);
        try {
            if (applicList != null && !applicList.isEmpty()) {
                applicTableBean = (AplApplicTableBean) Transform.entities2TableBean(applicList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return applicTableBean;
    }
}
