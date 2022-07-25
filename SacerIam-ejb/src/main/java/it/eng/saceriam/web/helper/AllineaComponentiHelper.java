package it.eng.saceriam.web.helper;

import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.slite.gen.tablebean.AplApplicRowBean;
import it.eng.saceriam.slite.gen.tablebean.AplApplicTableBean;
import it.eng.saceriam.web.util.Transform;
import it.eng.saceriam.ws.rest.helper.RecuperoHelpHelper;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Session Bean implementation class AllineaComponentiHelper Contiene i metodi, per la gestione della persistenza su DB
 * per le operazioni CRUD
 *
 */
@Stateless
@LocalBean
public class AllineaComponentiHelper {

    public AllineaComponentiHelper() {
        /**
         * Default constructor.
         */
    }

    private static final Logger log = LoggerFactory.getLogger(AllineaComponentiHelper.class);

    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager em;

    public AplApplicTableBean getAplApplicTableBean() {
        String queryStr = "SELECT applic FROM AplApplic applic ORDER BY applic.nmApplic";
        Query q = em.createQuery(queryStr);
        List<AplApplic> applicList = q.getResultList();
        AplApplicTableBean applicTableBean = new AplApplicTableBean();
        try {
            if (applicList != null && !applicList.isEmpty()) {
                applicTableBean = (AplApplicTableBean) Transform.entities2TableBean(applicList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return applicTableBean;
    }

    public AplApplicRowBean getAplApplicRowBean(String nmApplic) {
        AplApplicRowBean row = null;
        Query q = em.createQuery("SELECT applic FROM AplApplic applic WHERE applic.nmApplic = :nmApplic");
        q.setParameter("nmApplic", nmApplic);
        List<AplApplic> applicList = q.getResultList();
        if (applicList != null && !applicList.isEmpty()) {
            try {
                row = (AplApplicRowBean) Transform.entity2RowBean(applicList.get(0));
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                throw new IllegalStateException("Impossibile recuperare l'applicazione");
            }
        }
        return row;
    }

}
