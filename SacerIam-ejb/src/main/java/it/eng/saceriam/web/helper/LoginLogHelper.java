package it.eng.saceriam.web.helper;

import it.eng.parer.sacerlog.ejb.common.AppServerInstance;
import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.entity.SLLogLoginUser;
import it.eng.saceriam.web.util.Constants;
import it.eng.spagoLite.security.IUser;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author fioravanti_f
 */
@Stateless(mappedName = "LoginLogHelper")
@LocalBean
public class LoginLogHelper {

    private static final Logger log = LoggerFactory.getLogger(LoginLogHelper.class);

    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager entityManager;
    @EJB
    private AppServerInstance appServerInstance;

    public enum TipiEvento {
        LOGIN, LOGOUT
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void writeLogEvento(IUser user, String indIpClient, TipiEvento tipoEvento) {

        try {
            AplApplic tmpAplApplic;
            String queryStr = "select t from AplApplic t " + "where t.nmApplic = :nmApplic ";
            javax.persistence.Query query = entityManager.createQuery(queryStr, AplApplic.class);
            query.setParameter("nmApplic", Constants.SACERIAM);
            tmpAplApplic = (AplApplic) query.getSingleResult();

            String localServerName = appServerInstance.getName();

            SLLogLoginUser tmpLLogLoginUser = new SLLogLoginUser();
            tmpLLogLoginUser.setAplApplic(tmpAplApplic);
            tmpLLogLoginUser.setNmUserid(user.getUsername());
            tmpLLogLoginUser.setCdIndIpClient(indIpClient);
            tmpLLogLoginUser.setCdIndServer(localServerName);
            tmpLLogLoginUser.setDtEvento(new Date());
            tmpLLogLoginUser.setTipoEvento(tipoEvento.name());
            // Modifica per lo SPID
            if (user.getUserType() != null) {
                tmpLLogLoginUser.setTipoUtenteAuth(user.getUserType().name());
                tmpLLogLoginUser.setCdIdEsterno(user.getExternalId());
            }
            // ---
            entityManager.persist(tmpLLogLoginUser);
            entityManager.flush();

        } catch (Exception e) {
            log.error("Eccezione nel log dell'evento login/logout (writeLogEvento) ", e);
            throw new RuntimeException(e);
        }
    }
}
