package it.eng.saceriam.ws;

import it.eng.saceriam.ws.restituzioneNewsApplicazione.dto.RestituzioneNewsApplicazioneRisposta;
import it.eng.saceriam.ws.restituzioneNewsApplicazione.ejb.RestituzioneNewsApplicazioneEjb;
import it.eng.spagoLite.security.exception.AuthWSException;
import javax.ejb.EJB;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Gilioli_P
 */
@WebService(serviceName = "RestituzioneNewsApplicazione")
@HandlerChain(file = "/ws_handler.xml")
public class RestituzioneNewsApplicazione {
    @EJB
    private RestituzioneNewsApplicazioneEjb ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Web Service > Add Operation"

    @WebMethod(operationName = "restituzioneNewsApplicazione")
    public RestituzioneNewsApplicazioneRisposta restituzioneNewsApplicazione(
            @WebParam(name = "nmApplic") String nmApplic) throws AuthWSException {
        return ejbRef.restituzioneNewsApplicazione(nmApplic);
    }

}
