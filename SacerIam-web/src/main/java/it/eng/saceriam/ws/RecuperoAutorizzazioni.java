package it.eng.saceriam.ws;

import it.eng.saceriam.ws.dto.RecuperoAutorizzazioniRisposta;
import it.eng.saceriam.ws.ejb.RecuperoAutorizzazioniEjb;
import it.eng.spagoLite.security.exception.AuthWSException;
import javax.ejb.EJB;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(serviceName = "RecuperoAutorizzazioni")
@HandlerChain(file = "/ws_handler.xml")
public class RecuperoAutorizzazioni {

    private static final Logger log = LoggerFactory.getLogger(RecuperoAutorizzazioni.class);

    @EJB
    private RecuperoAutorizzazioniEjb ejbRef;

    @WebMethod(operationName = "recuperoAutorizzazioniPerNome")
    public RecuperoAutorizzazioniRisposta recuperoAutorizzazioniPerNome(@WebParam(name = "nmUserIam") String nmUserIam,
            @WebParam(name = "nmApplic") String nmApplic, @WebParam(name = "idOrganiz") Integer idOrganiz)
            throws AuthWSException {
        log.debug(
                "Chiamata all'operazione recuperoAutorizzazioniPerNome del WS RecuperoAutorizzazioni con i parametri nmUserIam "
                        + nmUserIam + " nmApplic " + nmApplic + " idOrganiz" + idOrganiz);
        return ejbRef.recuperoAutorizzazioniPerNome(nmUserIam, nmApplic, idOrganiz);
    }

    @WebMethod(operationName = "recuperoAutorizzazioniPerID")
    public RecuperoAutorizzazioniRisposta recuperoAutorizzazioniPerID(@WebParam(name = "idUserIam") Integer idUserIam,
            @WebParam(name = "nmApplic") String nmApplic, @WebParam(name = "idOrganiz") Integer idOrganiz)
            throws AuthWSException {
        log.debug(
                "Chiamata all'operazione recuperoAutorizzazioniPerID del WS RecuperoAutorizzazioni con i parametri idUserIam "
                        + idUserIam + " nmApplic " + nmApplic + " idOrganiz" + idOrganiz);
        return ejbRef.recuperoAutorizzazioniPerId(idUserIam, nmApplic, idOrganiz);
    }

}
