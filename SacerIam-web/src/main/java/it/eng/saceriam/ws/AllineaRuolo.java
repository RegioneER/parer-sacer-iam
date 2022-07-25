/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.ws;

import it.eng.parer.sacerlog.ejb.SacerLogEjb;
import it.eng.parer.sacerlog.util.LogParam;
import it.eng.parer.sacerlog.util.web.SpagoliteLogUtil;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.saceriam.util.SacerLogConstants;
import it.eng.saceriam.ws.allineaRuolo.dto.AllineaRuoloInput;
import it.eng.saceriam.ws.allineaRuolo.dto.AllineaRuoloRisposta;
import it.eng.saceriam.ws.allineaRuolo.dto.ListaApplic;
import it.eng.saceriam.ws.allineaRuolo.dto.ListaCategRuolo;
import it.eng.saceriam.ws.allineaRuolo.ejb.AllineaRuoloEjb;
import it.eng.saceriam.ws.utils.MessaggiWSBundle;
import it.eng.spagoLite.security.exception.AuthWSException;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.HandlerChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Bonora_L
 */
@WebService(serviceName = "AllineaRuolo")
@HandlerChain(file = "/ws_handler.xml")
public class AllineaRuolo {

    private static final Logger log = LoggerFactory.getLogger(AllineaRuolo.class);

    @EJB
    AllineaRuoloEjb allineaEjb;

    @EJB(mappedName = "java:app/SacerIam-ejb/ParamHelper")
    ParamHelper paramHelper;
    @EJB(mappedName = "java:app/sacerlog-ejb/SacerLogEjb")
    private SacerLogEjb sacerLogEjb;

    /**
     * WebService di allineamento ruoli tra ambienti
     *
     * @param nmSistemaChiamante
     *            nome sistema
     * @param nmUseridCor
     *            userid
     * @param nmRuolo
     *            nome ruolo
     * @param dsRuolo
     *            descrizione ruolo
     * @param tiRuolo
     *            tipo ruolo
     * @param listaCategRuolo
     *            lista categorie di tipo {@link ListaCategRuolo}
     * @param listaApplic
     *            lista applicazione di tipo {@link ListaApplic}
     *
     * @return Esito di risposta dell'allineamento
     *
     * @throws AuthWSException
     *             errore generico
     */
    @WebMethod(operationName = "allineaRuolo")
    public AllineaRuoloRisposta allineaRuolo(@WebParam(name = "nmSistemaChiamante") String nmSistemaChiamante,
            @WebParam(name = "nmUseridCor") String nmUseridCor, @WebParam(name = "nmRuolo") String nmRuolo,
            @WebParam(name = "dsRuolo") String dsRuolo, @WebParam(name = "tiRuolo") String tiRuolo,
            @WebParam(name = "listaCategRuolo") ListaCategRuolo listaCategRuolo,
            @WebParam(name = "listaApplic") ListaApplic listaApplic) throws AuthWSException {
        AllineaRuoloInput input = new AllineaRuoloInput(nmSistemaChiamante, nmUseridCor, nmRuolo, dsRuolo, tiRuolo,
                listaCategRuolo, listaApplic);
        log.debug("Chiamata all'operazione allineaRuolo del WS allineaRuolo con i parametri " + input.toString());

        LogParam param = SpagoliteLogUtil.getLogParam(paramHelper.getParamApplicApplicationName(),
                SacerLogConstants.AGENTE_SERVIZIO_ALLINEAMENTO_RUOLO);
        param.setNomeComponenteSoftware(SacerLogConstants.COMP_SW_ALLINEAMENTO_RUOLO);
        param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());

        return allineaEjb.allineaRuolo(param, input);

    }
}
