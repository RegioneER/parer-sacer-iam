package it.eng.saceriam.ws.ejb;

import it.eng.paginator.util.HibernateUtils;
import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.entity.AplTipoOrganiz;
import it.eng.saceriam.entity.UsrOrganizIam;
import it.eng.saceriam.entity.UsrTipoDatoIam;
import it.eng.saceriam.ws.dto.RispostaControlli;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.ListaTipiDato;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.TipoDato;
import it.eng.saceriam.ws.utils.MessaggiWSBundle;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Gilioli_P
 */
@Stateless(mappedName = "ControlliWS")
@LocalBean
public class ControlliWS {

    private static final Logger log = LoggerFactory.getLogger(ControlliWS.class);

    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager entityManager;

    /**
     * Verifica su IAM l'esistenza di una organizzazione appartenente all'applicazione in input con l'identificazione in
     * input e il suo tipo
     *
     * @param nmApplic
     *            nome applicazione
     * @param idOrganizApplic
     *            l'id associato all'organizzazione all'applicazione
     * @param nmTipoOrganiz
     *            nome tipo organizzazione
     * 
     * @return RispostaControlli.isrBoolean() == true se esiste l'organizzazione
     *
     */
    public RispostaControlli verificaEsistenzaOrganizzazione(String nmApplic, Integer idOrganizApplic,
            String nmTipoOrganiz) {
        RispostaControlli rispostaControlli = new RispostaControlli();
        rispostaControlli.setrBoolean(false);
        try {
            if (idOrganizApplic != null) {
                String queryStr = "SELECT u FROM UsrOrganizIam u WHERE u.aplApplic.nmApplic = :nmApplic "
                        + "AND u.idOrganizApplic = :idOrganizApplic "
                        + "AND u.aplTipoOrganiz.nmTipoOrganiz = :nmTipoOrganiz ";
                Query query = entityManager.createQuery(queryStr);
                query.setParameter("nmApplic", nmApplic);
                query.setParameter("idOrganizApplic", HibernateUtils.bigDecimalFrom(idOrganizApplic));
                query.setParameter("nmTipoOrganiz", nmTipoOrganiz);
                List<UsrOrganizIam> organizList = query.getResultList();
                if (!organizList.isEmpty()) {
                    rispostaControlli.setrBoolean(true);
                    rispostaControlli.setrLong(organizList.get(0).getIdOrganizIam());
                }
            }
        } catch (Exception e) {
            rispostaControlli.setCodErr(MessaggiWSBundle.ERR_666);
            rispostaControlli.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.ERR_666, e.getMessage()));
            log.error("Eccezione nella lettura della tabella delle organizzazioni ", e);
        }
        return rispostaControlli;
    }

    /**
     * Verifica il tipo di organizzazione per l'applicazione
     *
     * @param nmApplic
     *            nome applicazione
     * @param nmTipoOrganiz
     *            nome tipo organizzazione
     * 
     * @return RispostaControlli.isrBoolean() == true in caso la verifica dia esito positivo (tipo organizzazione
     *         correttamente presente nell'organizzazione)
     */
    public RispostaControlli verificaNomeTipoOrganizzazione(String nmApplic, String nmTipoOrganiz) {
        RispostaControlli rispostaControlli = new RispostaControlli();
        rispostaControlli.setrBoolean(false);
        try {
            String queryStr = "SELECT u FROM AplTipoOrganiz u " + "WHERE u.nmTipoOrganiz = :nmTipoOrganiz "
                    + "AND u.aplApplic.nmApplic = :nmApplic ";
            Query query = entityManager.createQuery(queryStr);
            query.setParameter("nmTipoOrganiz", nmTipoOrganiz);
            query.setParameter("nmApplic", nmApplic);

            List<AplTipoOrganiz> pvList = query.getResultList();
            if (!pvList.isEmpty()) {
                rispostaControlli.setrBoolean(true);
            }
        } catch (Exception e) {
            rispostaControlli.setCodErr(MessaggiWSBundle.ERR_666);
            rispostaControlli.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.ERR_666, e.getMessage()));
            log.error("Eccezione nella lettura  della tabella dei tipi organizzazione ", e);
        }
        return rispostaControlli;
    }

    /**
     * Verifica ogni classe tipo di dato per l'applicazione
     *
     * @param listaTipiDato
     *            lista tipi dato {@link ListaTipiDato}
     * @param nmApplic
     *            nome applicazione
     * 
     * @return RispostaControlli.isrBoolean() == true in caso la verifica dia esito positivo
     */
    public RispostaControlli verificaNomeTipoDato(ListaTipiDato listaTipiDato, String nmApplic) {
        RispostaControlli rispostaControlli = new RispostaControlli();
        rispostaControlli.setrBoolean(true);
        try {
            if (listaTipiDato.getTipoDato() != null) {
                for (TipoDato tipoDato : listaTipiDato.getTipoDato()) {
                    String queryStr = "SELECT u FROM AplClasseTipoDato u " + "WHERE u.aplApplic.nmApplic = :nmApplic "
                            + "AND u.nmClasseTipoDato = :nmClasseTipoDato ";
                    Query query = entityManager.createQuery(queryStr);
                    query.setParameter("nmApplic", nmApplic);
                    query.setParameter("nmClasseTipoDato", tipoDato.getNmClasseTipoDato());

                    List<UsrTipoDatoIam> tipoDatoList = query.getResultList();
                    if (tipoDatoList.isEmpty()) {
                        rispostaControlli.setrBoolean(false);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            rispostaControlli.setCodErr(MessaggiWSBundle.ERR_666);
            rispostaControlli.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.ERR_666, e.getMessage()));
            log.error("Eccezione nella lettura  della tabella dei tipi organizzazione ", e);
        }
        return rispostaControlli;
    }

    /**
     * Verifica l'esistenza dell'applicazione passata come parametro
     *
     * @param nmApplic
     *            il nome dell'applicazione da verificare
     * 
     * @return RispostaControlli contenente l'esito del controllo e, in caso positivo, l'applicazione
     */
    public RispostaControlli verificaApplicazione(String nmApplic) {
        RispostaControlli rispostaControlli = new RispostaControlli();
        rispostaControlli.setrBoolean(true);
        String queryStr = "SELECT applic FROM AplApplic applic WHERE applic.nmApplic = :nmApplic ";
        Query q = entityManager.createQuery(queryStr);
        q.setParameter("nmApplic", nmApplic);
        List<AplApplic> applicList = q.getResultList();
        if (!applicList.isEmpty()) {
            rispostaControlli.setrObject(applicList.get(0));
        } else {
            rispostaControlli.setrBoolean(false);
        }
        return rispostaControlli;
    }
}