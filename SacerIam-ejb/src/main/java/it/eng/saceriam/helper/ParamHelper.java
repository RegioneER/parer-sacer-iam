package it.eng.saceriam.helper;

import it.eng.saceriam.common.Constants;
import it.eng.saceriam.common.Constants.TipoIamVGetValAppart;
import it.eng.saceriam.entity.constraint.ConstAplValoreParamApplic.TiAppart;
import it.eng.saceriam.exception.ParamApplicNotFoundException;
import it.eng.saceriam.web.helper.dto.IamVGetValParamDto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static it.eng.paginator.util.HibernateUtils.*;

/**
 *
 * @author bonora_l
 */
@Stateless
@LocalBean
public class ParamHelper {

    private static final Logger log = LoggerFactory.getLogger(ParamHelper.class);

    public static final String URL_ASSOCIAZIONE_UTENTE_CF = "URL_ASSOCIAZIONE_UTENTE_CF";
    public static final String URL_BACK_ASSOCIAZIONE_UTENTE_CF = "URL_BACK_ASSOCIAZIONE_UTENTE_CF";

    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager entityManager;

    /*
     * Restituisce il nome dell'applicazione configurato sulla tabella dei parametri
     */
    public String getParamApplicApplicationName() {
        String queryStr = "SELECT valoreParamApplic.dsValoreParamApplic "
                + "FROM IamValoreParamApplic valoreParamApplic " + "JOIN valoreParamApplic.iamParamApplic paramApplic "
                + "WHERE paramApplic.nmParamApplic = 'NM_APPLIC' ";
        javax.persistence.Query query = entityManager.createQuery(queryStr);
        List<String> paramList = (List<String>) query.getResultList();
        if (paramList != null && !paramList.isEmpty()) {
            return paramList.get(0);
        } else {
            return null;
        }
    }

    private static final String IAMVGETVALPARAMBYCOL = "IamVGetvalParamByCol";
    private static final String IAMVGETVALPARAMBY = "IamVGetvalParamBy";
    private static final String FLAPLPARAMAPPLICAPPART = "flIamParamApplicAppart";
    private static final String IDAPLVGETVALPARAMBY = "idIamVGetvalParamBy";

    /**
     *
     * @param nmParamApplic
     *            nome parametro applicativo
     * @param idAmbienteEnteConvenz
     *            id ambiente convenzionato
     * @param idEnteSiam
     *            id ente IAM
     * @param tipoIamVGetValAppart
     *            tipo valore {@link TipoIamVGetValAppart}
     * 
     * @return valore
     */
    @SuppressWarnings("unchecked")
    public String getValoreParamApplic(String nmParamApplic, BigDecimal idAmbienteEnteConvenz, BigDecimal idEnteSiam,
            TipoIamVGetValAppart tipoIamVGetValAppart) {

        long id = Integer.MIN_VALUE;// su questo id non troverò alcun elemento value sicuramente null
        List<IamVGetValParamDto> result = null;

        // base query (template)
        Map<String, String> queryData = new HashMap<>();
        String queryStr = null;

        // query template -> create DTO
        String queryStrTempl = "SELECT NEW it.eng.saceriam.web.helper.dto.IamVGetValParamDto (${" + IAMVGETVALPARAMBYCOL
                + "}) " + "FROM IamParamApplic paramApplic, ${" + IAMVGETVALPARAMBY + "} getvalParam  "
                + "WHERE paramApplic.nmParamApplic = :nmParamApplic "
                + "AND getvalParam.nmParamApplic = paramApplic.nmParamApplic " + "AND paramApplic.${"
                + FLAPLPARAMAPPLICAPPART + "} = :flAppart ${" + IDAPLVGETVALPARAMBY + "} ";

        // tipo appartenenza
        TiAppart tiAppart = null;

        switch (tipoIamVGetValAppart) {
        case ENTECONVENZ:
            //
            id = idEnteSiam != null ? idEnteSiam.longValue() : Integer.MIN_VALUE;
            //
            tiAppart = TiAppart.ENTE;
            //
            queryData.put(IAMVGETVALPARAMBYCOL, "getvalParam.dsValoreParamApplic, getvalParam.tiAppart");
            queryData.put(IAMVGETVALPARAMBY, "IamVGetvalParamByEnte");
            queryData.put(FLAPLPARAMAPPLICAPPART, "flApparteEnte");
            queryData.put(IDAPLVGETVALPARAMBY, "AND getvalParam.idEnteConvenz = :id");
            // replace
            queryStr = StrSubstitutor.replace(queryStrTempl, queryData);
            break;
        case AMBIENTEENTECONVENZ:
            //
            id = idAmbienteEnteConvenz != null ? idAmbienteEnteConvenz.longValue() : Integer.MIN_VALUE; // os.getIdStrut();
            //
            tiAppart = TiAppart.AMBIENTE;
            //
            queryData.put(IAMVGETVALPARAMBYCOL, "getvalParam.dsValoreParamApplic, getvalParam.tiAppart");
            queryData.put(IAMVGETVALPARAMBY, "IamVGetvalParamByAmb");
            queryData.put(FLAPLPARAMAPPLICAPPART, "flAppartAmbiente");
            queryData.put(IDAPLVGETVALPARAMBY, "AND getvalParam.idAmbienteEnteConvenz = :id");
            // replace
            queryStr = StrSubstitutor.replace(queryStrTempl, queryData);
            break;
        default:
            //
            tiAppart = TiAppart.APPLIC;
            //
            queryData.put(IAMVGETVALPARAMBYCOL, "getvalParam.dsValoreParamApplic");
            queryData.put(IAMVGETVALPARAMBY, "IamVGetvalParamByApl");
            queryData.put(FLAPLPARAMAPPLICAPPART, "flAppartApplic");
            queryData.put(IDAPLVGETVALPARAMBY, "");
            // replace
            queryStr = StrSubstitutor.replace(queryStrTempl, queryData);
            break;
        }

        try {
            TypedQuery<IamVGetValParamDto> query = entityManager.createQuery(queryStr, IamVGetValParamDto.class);
            query.setParameter("nmParamApplic", nmParamApplic);
            query.setParameter("flAppart", "1");// fixed
            // solo nel caso in cui contenga la condition sull'ID
            if (StringUtils.isNotBlank(queryData.get(IDAPLVGETVALPARAMBY))) {
                query.setParameter("id", bigDecimalFrom(id));
            }
            // get result
            result = query.getResultList();
        } catch (Exception e) {
            // throws Exception
            final String msg = "Errore nella lettura del parametro " + nmParamApplic;
            log.error(msg);
            throw new ParamApplicNotFoundException(msg, nmParamApplic);
        }

        if (result != null && !result.isEmpty()) {
            /*
             * if more than one ....
             */
            if (result.size() > 1) {
                /**
                 * Ordine / Priorità TiAppart idAaTipoFascicolo -> idTipoUnitaDoc -> idStrut -> idAmbiente ->
                 * applicazione
                 */
                // filter by getTiAppart
                return getDsValoreParamApplicByTiAppart(nmParamApplic, result, tiAppart);
            } else {
                return result.get(0).getDsValoreParamApplic(); // one is expected
            }
        } else if (Constants.TipoIamVGetValAppart.next(tipoIamVGetValAppart) != null) {
            /*
             * Ordine / Priorità Viste idEnteSiam -> idAmbienteEnteConvenz -> applicazione
             */
            return getValoreParamApplic(nmParamApplic, idAmbienteEnteConvenz, idEnteSiam,
                    Constants.TipoIamVGetValAppart.next(tipoIamVGetValAppart));
        } else {
            // thorws Exception
            final String msg = "Parametro " + nmParamApplic + " non definito o non valorizzato";
            log.error(msg);
            throw new ParamApplicNotFoundException(msg, nmParamApplic);
        }
    }

    @SuppressWarnings("unchecked")
    private String getDsValoreParamApplicByTiAppart(String nmParamApplic, List<IamVGetValParamDto> result,
            final TiAppart tiAppart) {
        // get entity from list

        // Con lambda expression
        // List<AplVGetValParamDto> resultFiltered = result.stream().filter(e -> e.getTiAppart()!=null &&
        // e.getTiAppart().equals(tiAppart.name())).collect(Collectors.toList());
        List<IamVGetValParamDto> resultFiltered = new ArrayList<>();
        for (IamVGetValParamDto valParam : result) {
            if (valParam.getTiAppart().equals(tiAppart.name())) {
                resultFiltered.add(valParam);
                break;
            }
        }

        /* questa condizione non dovrebbe mai verificarsi */
        if (tiAppart.name().equals(TiAppart.APPLIC.name()) && (resultFiltered == null || resultFiltered.isEmpty())) {
            // thorws Exception
            final String msg = "Parametro " + nmParamApplic + " non definito o non valorizzato";
            log.error(msg);
            throw new ParamApplicNotFoundException(msg, nmParamApplic);
        }

        if (resultFiltered == null || resultFiltered.isEmpty()) {
            TiAppart nextTiAppart = null;
            switch (tiAppart) {
            case ENTE:
                nextTiAppart = TiAppart.AMBIENTE;
                break;
            default:
                nextTiAppart = TiAppart.APPLIC;
                break;
            }
            return getDsValoreParamApplicByTiAppart(nmParamApplic, result, nextTiAppart);
        } else {
            return resultFiltered.get(0).getDsValoreParamApplic();// expected one
        }
    }

    public String getUrlAssociazioneUtenteCf() {
        return getValoreParamApplic(URL_ASSOCIAZIONE_UTENTE_CF, null, null, TipoIamVGetValAppart.APPLIC);
    }

    public String getUrlBackAssociazioneUtenteCf() {
        return getValoreParamApplic(URL_BACK_ASSOCIAZIONE_UTENTE_CF, null, null, TipoIamVGetValAppart.APPLIC);
    }

}
